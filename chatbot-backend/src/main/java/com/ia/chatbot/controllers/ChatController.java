package com.ia.chatbot.controllers;

import com.ia.chatbot.models.requests.Answer;
import com.ia.chatbot.models.requests.UserQuestionRequest;
import com.ia.chatbot.services.ChatService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/chat")
@AllArgsConstructor
public class ChatController {
    private final ChatService chatService;

    @PostMapping("/send")
    public Answer getAnswer(@RequestBody UserQuestionRequest userQuestionRequest){
        return chatService.getAnswer(userQuestionRequest);
    }
}
