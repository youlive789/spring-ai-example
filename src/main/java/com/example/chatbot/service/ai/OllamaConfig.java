package com.example.chatbot.service.ai;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.chatbot.common.constant.Constant;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class OllamaConfig {
    private final OllamaChatModel ollamaChatModel;
    private final EmbeddingModel ollamaEmbeddingModel;

    @Bean
    public ChatClient client() {
        return ChatClient.create(ollamaChatModel);
    }

    @Bean
    VectorStore simpleVectorStore() {
        VectorStore vectorstore = new SimpleVectorStore(ollamaEmbeddingModel);
        Document document1 = new Document(Constant.DOCUMENT_EXAMPLE_COUPANG);
        Document document2 = new Document(Constant.DOCUMENT_EXAMPLE_LG);
        Document document3 = new Document(Constant.DOCUMENT_EXAMPLE_SAMSUNG);
        Document document4 = new Document(Constant.DOCUMENT_EXAMPLE_SK);
        vectorstore.add(List.of(document1, document2, document3, document4));
        return vectorstore;
    }
}
