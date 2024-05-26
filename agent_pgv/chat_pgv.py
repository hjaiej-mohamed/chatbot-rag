from fastapi import FastAPI,UploadFile
from pydantic import BaseModel
from PyPDF2 import PdfReader
from langchain.text_splitter import RecursiveCharacterTextSplitter
import os
from langchain_google_genai import GoogleGenerativeAIEmbeddings
import google.generativeai as genai
from langchain_community.vectorstores import FAISS
from langchain_google_genai import ChatGoogleGenerativeAI
from langchain.chains.question_answering import load_qa_chain
from langchain.prompts import PromptTemplate
from dotenv import load_dotenv
from typing import List
from langchain_community.vectorstores import PGVector
from langchain_community.vectorstores.pgvector import _get_embedding_collection_store

from langchain_core.documents import Document
from sqlalchemy.orm import Session
from sqlalchemy import create_engine

CONNECTION_STRING="postgresql://postgres:postgres@pgvector:5432/ttchatbot"
EmbeddingStore = _get_embedding_collection_store()[0]


def save_vectors(pdf_file,collection_name):
    embeddings = GoogleGenerativeAIEmbeddings(model = "models/embedding-001")
    text=get_pdf_text(pdf_file)
    text_chunks = get_text_chunks(text)
    print(text_chunks)
    db = PGVector.from_texts(
            embedding=embeddings,
            texts=text_chunks,
            collection_name=collection_name,
            connection_string=CONNECTION_STRING,
            pre_delete_collection=False,
        )
    print(text_chunks)
    return len(text_chunks)
##
load_dotenv()
os.getenv("GOOGLE_API_KEY")
genai.configure(api_key=os.getenv("GOOGLE_API_KEY"))
app = FastAPI()


def get_pdf_text(pdf_docs):
    text=""
    for pdf in pdf_docs:
        pdf_reader= PdfReader(pdf)
        for page in pdf_reader.pages:
            text+= page.extract_text()
            print(text)
    return  text

def get_text_chunks(text):
    text_splitter = RecursiveCharacterTextSplitter(chunk_size=10000, chunk_overlap=1000)
    chunks = text_splitter.split_text(text)
    print('chunks:',len(chunks))
    return chunks


def get_vector_store(text_chunks):
    embeddings = GoogleGenerativeAIEmbeddings(model = "models/embedding-001")
    vector_store = FAISS.from_texts(text_chunks, embedding=embeddings)
    vector_store.save_local("faiss_index2")


def get_conversational_chain():

    prompt_template = """
    Answer the question as detailed as possible from the provided context, make sure to provide all the details, stay proffessional and answer to greetings and don't provide the wrong answer,and don't give answer ou of the question ,and answer in french\n\n
    Context:\n {context}?\n
    Question: \n{question}\n

    Answer:
    """

    model = ChatGoogleGenerativeAI(model="gemini-pro",
                             temperature=0.3)

    prompt = PromptTemplate(template = prompt_template, input_variables = ["context", "question"])
    chain = load_qa_chain(model, chain_type="stuff", prompt=prompt)
    return chain

def get_vdb(collection_name):
    embeddings = GoogleGenerativeAIEmbeddings(model = "models/embedding-001")
    pgvector_docsearch = PGVector(
            collection_name=collection_name,
            connection_string=CONNECTION_STRING,
            embedding=embeddings,
        )
    return pgvector_docsearch

def main(user_question):
    if user_question:
        return user_input(user_question)
    
def user_input(user_question):
    docs=custom_similarity_search_with_scores(user_question)
    print("my:",docs)
    chain = get_conversational_chain()
    
    response = chain(
        {"input_documents":docs, "question": user_question}
        , return_only_outputs=True)
    print(response)
    return response["output_text"]

def get_pdf_text(pdf_docs):
    text=""
    for pdf in pdf_docs:
        pdf_reader= PdfReader(pdf.file)
        for page in pdf_reader.pages:
            text+= page.extract_text()
            # print(text)
    return  text

        
def delete_document(collection_name):
    embeddings = GoogleGenerativeAIEmbeddings(model = "models/embedding-001")
    pgvector = PGVector(
                collection_name=collection_name,
                connection_string=CONNECTION_STRING,
                embedding_function=embeddings,
            )
    pgvector.delete_collection()

def update_collection_by_name(pdf,collection_name):
    embeddings = GoogleGenerativeAIEmbeddings(model = "models/embedding-001")
    text=get_pdf_text(pdf)
    text_chunks=get_text_chunks(text)
    pgvector = PGVector.from_texts(
                collection_name=collection_name,
                connection_string=CONNECTION_STRING,
                embedding=embeddings,
                texts=text_chunks,
                pre_delete_collection=True
            )



def get_vector(text):
        embeddings = GoogleGenerativeAIEmbeddings(model = "models/embedding-001")
        return embeddings.embed_query(text)

def custom_similarity_search_with_scores(query, k=2):
        query_vector = get_vector(query)
        engine=create_engine(CONNECTION_STRING)
        with Session(engine) as session:
            # Using cosine similarity for the vector comparison
            cosine_distance = EmbeddingStore.embedding.cosine_distance(
                query_vector
            ).label("distance")

            # Querying the EmbeddingStore table
            results = (
                session.query(
                    EmbeddingStore.document,
                    EmbeddingStore.custom_id,
                    cosine_distance,
                )
                .order_by(cosine_distance.asc())
                .limit(k)
                .all()
            )
        # Calculate the similarity score by subtracting the cosine distance from 1 (_cosine_relevance_score_fn)
        docs = [Document(page_content=result[0]) for result in results]
        print("all docs:",docs)
        return docs

class Question(BaseModel):
    question:str

@app.post("/chat/send")
def send(question:Question):
    return main(question.question)

@app.post("/chat/add-document")
def create_document(file:List[UploadFile]):
    nb_chucks=save_vectors(file,file[0].filename)
    return f"{nb_chucks} have been saved"

@app.put('/chat/update')
def update_collection(file:List[UploadFile],collection_name):
    update_collection_by_name(file,collection_name)

@app.delete('/chat/document')
def delete_document_by_name(collection_name:str):
    try:
        # db = get_vdb()
        delete_document(collection_name)
    except Exception as e:
        print('Error has been occurred :',e)
        return 'error'