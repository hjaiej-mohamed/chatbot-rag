import { QuestionAnswer } from "./QuestionAnswer";

export interface Discussion{
    id:number,
    dateCreation:Date,
    userId:number,
    questionsAnswers:QuestionAnswer[],
}