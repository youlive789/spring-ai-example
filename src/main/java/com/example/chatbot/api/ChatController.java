package com.example.chatbot.api;

import lombok.RequiredArgsConstructor;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.chatbot.service.ai.NameAgentService;
import com.example.chatbot.service.ai.WriterAgentService;

@RequestMapping("/api")
@RestController
@RequiredArgsConstructor
public class ChatController {
    private final ChatClient chatClient;
    private final NameAgentService nameAgentService;
    private final WriterAgentService writerAgentService;

    @GetMapping("/chat")
    public String chat(@RequestParam("message") String message) {
        return chatClient.prompt().user(message).call().content();
    }
    

    @GetMapping("/name/agent")
    public String name(@RequestParam("message") String message) {
        return nameAgentService.askName(message);
    }

    @GetMapping("/writer/agent")
    public String writer(@RequestParam String message) {
        return writerAgentService.askLatestPostOfWriter(message);
    }
}
