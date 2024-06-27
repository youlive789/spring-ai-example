package com.example.chatbot.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.stringtemplate.v4.compiler.CodeGenerator.primary_return;

import com.example.chatbot.service.ai.RagAnswerService;

import lombok.RequiredArgsConstructor;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/api")
@RestController
@RequiredArgsConstructor
public class RagContoller {
    private final RagAnswerService ragAnswerService;

    @GetMapping("/rag/search")
    public String rag(@RequestParam String message) {
        return ragAnswerService.answer(message);
    }
}
