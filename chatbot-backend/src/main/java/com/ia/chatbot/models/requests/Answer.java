package com.ia.chatbot.models.requests;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Data
public class Answer {
    private String answer;
    private Long discussionId;
}
