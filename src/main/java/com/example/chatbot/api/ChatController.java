package com.example.chatbot.api;

import lombok.RequiredArgsConstructor;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.chatbot.service.ai.NameAgentService;
import com.example.chatbot.service.ai.SqlAgentService;

@RequestMapping("/api")
@RestController
@RequiredArgsConstructor
public class ChatController {
    
    private final ChatClient chatClient;
    private final SqlAgentService sqlAgentService;
    private final NameAgentService nameAgentService;

    @GetMapping("/chat")
    public String getMethodName(@RequestParam("message") String message) {
        return chatClient.prompt().user(message).call().content();
    }
    

    @GetMapping("/name/agent")
    public String chat(@RequestParam("message") String message) {
        return nameAgentService.askName(message);
    }

    @GetMapping("/sql/agent")
    public String post(@RequestParam("message") String message) {
        return sqlAgentService.askPost(message);
    }
    

}
