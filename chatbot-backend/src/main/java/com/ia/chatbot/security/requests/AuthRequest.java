package com.ia.chatbot.security.requests;

import lombok.Data;

@Data
public class AuthRequest {
    private String email;
    private String password;
}
