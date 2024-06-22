package com.example.chatbot.service.ai;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class OllamaConfig {

    private final OllamaChatModel ollamaChatModel;

    @Bean
    public ChatClient client() {
        return ChatClient.create(ollamaChatModel);
    }
}
