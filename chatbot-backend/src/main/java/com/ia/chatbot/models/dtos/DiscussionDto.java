package com.ia.chatbot.models.dtos;


import lombok.Builder;
import java.time.LocalDateTime;

@Builder
public class DiscussionDto {
    private Long id;
    private LocalDateTime dateCreation;
    private Long userId;
}
