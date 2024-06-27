package com.example.chatbot.service.ai;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class RagAnswerService {
    private final ChatClient chatClient;
    private final VectorStore vectorStore;

    @Value("classpath:/rag.st")
    private Resource prompt;

    public String answer(String message) {
        final String foundContext = vectorStore
            .similaritySearch(message)
            .get(0)
            .getContent();

        log.info(foundContext);

        return chatClient.prompt()
            .user(userSpec -> userSpec
                .text(prompt)
                .param("query", message)
                .param("context", foundContext)
            )
            .call()
            .content();
    }
}
