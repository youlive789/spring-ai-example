package com.example.chatbot.service.ai;
import com.example.chatbot.model.entity.Post;
import com.example.chatbot.model.repository.PostRepository;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SqlAgentService {
    @Value("classpath:/function.st")
    private Resource systemPrompt;

    @Value("classpath:/question.st")
    private Resource userPrompt;

    private final ChatClient chatClient;
    private final PostRepository postRepository;

    public String askPost(String message) {
        final String function = chatClient.prompt()
            .user(userSpec -> userSpec
                .text(systemPrompt)
                .param("question", message)
            )
            .call()
            .content();
        
        if (!"getPostTitleByUserId".startsWith(function)) {
            return function;
        }

        Long userId = Long.valueOf(function);

        return chatClient.prompt()
            .user(userSpec -> userSpec
                .text(userPrompt)
                .param("context", "post title: " + getLastPostTitleByUser(userId))
                .param("question", message)
            )
            .call()
            .content();
    }

    private String getLastPostTitleByUser(Long userId) {
        Post result = postRepository.findFirstByUserIdOrderByIdDesc(userId);
        if (result != null) {
            return result.getContent();
        }
        return "not found";
    }
}
