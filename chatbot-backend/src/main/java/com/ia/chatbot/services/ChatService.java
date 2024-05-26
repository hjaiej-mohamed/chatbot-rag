package com.ia.chatbot.services;

import com.ia.chatbot.models.entities.Discussion;
import com.ia.chatbot.models.entities.QuestionAnswer;
import com.ia.chatbot.models.requests.Answer;
import com.ia.chatbot.models.requests.UserQuestionRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final DiscussionService discussionService;
    private final QuestionAnswerService questionAnswerService;
    private static final  String iaServiceUrl="http://agent-pgv:8000/chat/send";
    public Answer getAnswer(UserQuestionRequest chatQuestionRequest) {
        try {

            //Get discussion
            Discussion discussion=null;
            if(chatQuestionRequest.getDiscussionId()==-1){
                discussion = discussionService.create(chatQuestionRequest.getUserId());
                chatQuestionRequest.setDiscussionId(discussion.getId());
            }
            else{
                discussion = discussionService.getById(chatQuestionRequest.getDiscussionId());
            }
            // Get answer
            RestTemplate restTemplate = new RestTemplate();
            Map<String, String> data = new HashMap<>();
            data.put("question", chatQuestionRequest.getQuestion());
            System.out.println("in chat service");
            String response = restTemplate.postForEntity(iaServiceUrl, data, String.class).getBody();
            System.out.println(chatQuestionRequest.getQuestion());
            System.out.println(response);
            //save the QA
            questionAnswerService.create(QuestionAnswer
                    .builder()
                            .question(chatQuestionRequest.getQuestion())
                            .answer(response)
                            .discussion(discussion)
                    .build());
            //return answer.
            Answer answer=Answer.builder().answer(response).discussionId(chatQuestionRequest.getDiscussionId()).build();
            System.out.println("answer:"+answer.getAnswer());
            return answer;
        } catch (Exception ex) {
            System.out.println("in exception");
            System.out.println(ex);
        }
        return null;
    }
}
