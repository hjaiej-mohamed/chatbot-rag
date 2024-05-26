package com.ia.chatbot.models.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class UserQuestionRequest {
    private String question;
    private Long discussionId;
    private Long userId;
}
