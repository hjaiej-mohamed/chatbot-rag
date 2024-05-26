package com.ia.chatbot.services;

import com.ia.chatbot.models.entities.QuestionAnswer;
import com.ia.chatbot.repositories.QuestionsAnswerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionAnswerService {
    private final QuestionsAnswerRepository questionsAnswerRepository;
    
    public QuestionAnswer create(QuestionAnswer qa){
        return  this.questionsAnswerRepository.save(qa);
    }

    public QuestionAnswer get(Long qaId) throws Exception{
        return questionsAnswerRepository.findById(qaId)
                .orElseThrow(()-> new Exception("question not found with id :"+qaId));
    }

    public List<QuestionAnswer> getQAsByDiscussionId(Long discussionId) {
        return questionsAnswerRepository.findQuestionAnswerByDiscussion_Id(discussionId);
    }
}
