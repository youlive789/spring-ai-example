package com.example.chatbot.service.ai;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NameAgentService {
    @Value("classpath:/react.st")
    private Resource prompt;

    private final ChatClient chatClient;

    public String askName(String message) {
        return chatClient.prompt()
            .user(userSpec -> userSpec
                .text(prompt)
                .param("query", message)
            )
            .call()
            .content();
    }

    private String getName() {
        return "gemma:2b";
    }
}
