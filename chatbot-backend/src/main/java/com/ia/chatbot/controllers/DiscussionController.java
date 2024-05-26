package com.ia.chatbot.controllers;

import com.ia.chatbot.models.dtos.DiscussionDto;
import com.ia.chatbot.models.entities.Discussion;
import com.ia.chatbot.services.DiscussionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/discussion")
@RequiredArgsConstructor
public class DiscussionController {
    private final DiscussionService discussionService;

    @GetMapping("/{id}")
    public List<Discussion> getDiscussionsByUserId(@PathVariable("id") Long userId){
        System.out.println("get discussions for user with id:"+userId);
        return discussionService.getDiscussionsByUserId(userId);
    }

    @GetMapping()
    public List<DiscussionDto> getDiscussions(){
        System.out.println("get all discussions ");
        return discussionService.getDiscussions();
    }
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id){
        this.discussionService.deleteById(id);
    }

}
