package com.example.chatbot.api;

import lombok.RequiredArgsConstructor;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api")
@RestController
@RequiredArgsConstructor
public class ChatController {
    
    @Value("classpath:/function.st")
    private Resource systemPrompt;

    @Value("classpath:/question.st")
    private Resource userPrompt;

    private final ChatClient chatClient;

    @GetMapping("path")
    public String getMethodName(@RequestParam String param) {
        return new String();
    }

    @GetMapping("/name/agent")
    public String chat(@RequestParam("message") String message) {
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
