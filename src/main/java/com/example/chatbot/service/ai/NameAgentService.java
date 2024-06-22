package com.example.chatbot.service.ai;

import java.time.Duration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class NameAgentService {
    @Value("classpath:/react.st")
    private Resource prompt;

    private final ChatClient chatClient;

    public String askName(String message) {
        final List<String> action = chatClient.prompt()
            .user(userSpec -> userSpec
                .text(prompt)
                .param("query", message)
            )
            .stream()
            .content()
            .takeUntil(res -> res.contains("getName"))
            .collectList()
            .block();

        System.out.println(action.toString());

        return null;
    }

    private String getName() {
        return "gemma:2b";
    }
}
