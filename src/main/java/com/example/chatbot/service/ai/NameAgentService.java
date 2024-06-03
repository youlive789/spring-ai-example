package com.example.chatbot.service.ai;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NameAgentService {
    @Value("classpath:/function.st")
    private Resource systemPrompt;

    @Value("classpath:/question.st")
    private Resource userPrompt;

    private final ChatClient chatClient;

    public String askName(String message) {
        final String function = chatClient.prompt()
            .user(userSpec -> userSpec
                .text(systemPrompt)
                .param("question", message)
            )
            .call()
            .content();
        
        if (!"getName".equals(function)) {
            return function;
        }

        return chatClient.prompt()
            .user(userSpec -> userSpec
                .text(userPrompt)
                .param("context", "name: " + getName())
                .param("question", message)
            )
            .call()
            .content();
    }

    private String getName() {
        return "gemma:2b";
    }
}
