package com.ia.chatbot.controllers;

import com.ia.chatbot.models.entities.QuestionAnswer;
import com.ia.chatbot.services.QuestionAnswerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/qa")
@RequiredArgsConstructor
public class QAController {
    private  final QuestionAnswerService questionAnswerService;

    @GetMapping("/{id}")
    public QuestionAnswer getQA(@PathVariable("id") Long qaId) throws Exception {
        return questionAnswerService.get(qaId);
    }
    @GetMapping("/discussion/{id}")
    public List<QuestionAnswer> getQAsByDiscussionId(@PathVariable("id") Long discussionId) throws Exception {
        return questionAnswerService.getQAsByDiscussionId(discussionId);
    }
}
