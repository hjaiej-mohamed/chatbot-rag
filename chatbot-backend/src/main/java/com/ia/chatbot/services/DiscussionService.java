package com.ia.chatbot.services;

import com.ia.chatbot.models.dtos.DiscussionDto;
import com.ia.chatbot.models.entities.Discussion;
import com.ia.chatbot.repositories.DiscussionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DiscussionService {
    private final DiscussionRepository discussionRepository;
    private final UserService userService;

    public Discussion create(Long userId) throws Exception {
        return discussionRepository.save(Discussion
                .builder()
                        .user(userService.getUserById(userId))
                .build());
    }

    public Discussion getById(Long discussionId) throws Exception {
        return  discussionRepository.findById(discussionId).orElseThrow(()->new Exception("discussion not found with id :"+discussionId));
    }


    public List<Discussion> getDiscussionsByUserId(Long userId) {
        return discussionRepository.findByUserId(userId);
    }

    public List<DiscussionDto> getDiscussions() {
        List<DiscussionDto> discussionDtos= discussionRepository.findAll().stream().map((discussion -> {
            return DiscussionDto.builder()
                .id(discussion.getId())
                    .dateCreation(discussion.getDateCreation())
                    .userId(discussion.getUser().getId())
                    .build();
        })).collect(Collectors.toList());
        System.out.println("discussionsSize:"+discussionDtos.size());
        return discussionDtos;
    }

    public void deleteById(Long id) {
        this.discussionRepository.deleteById(id);
    }
}
