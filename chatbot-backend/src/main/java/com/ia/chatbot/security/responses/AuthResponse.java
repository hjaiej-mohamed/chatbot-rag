package com.ia.chatbot.security.responses;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class AuthResponse {
    private String accessToken;
    private String firstname;
    private String lastname;
    private Long userId;
    private Set<String> roles;

}
