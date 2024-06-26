package com.example.chatbot.service.ai;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.example.chatbot.model.repository.PostRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class WriterAgentService {
    @Value("classpath:/react.st")
    private Resource prompt;

    private final ChatClient chatClient;
    private final PostRepository postRepository;
    private final Pattern actionInputPattern = Pattern.compile("\\*\\*Action Input.*?\\n");

    public String askLatestPostOfWriter(String message) {
        final String postAction = getPostActionResponse(message);
        log.info(postAction);

        final String finalAnswer = getFinalAnswerReponse(message, postAction, getWriterId());
        log.info(finalAnswer);

        return finalAnswer
            .replaceAll("\\*\\*Final Answer.*?\\*\\*", "")
            .trim();
    }

    private String getPostActionResponse(String message) {
        return chatClient.prompt()
            .user(userSpec -> userSpec
                .text(prompt)
                .param("query", message)
            )
            .stream()
            .content()
            .scan("", (accumulated, current) -> accumulated + current)
            .takeWhile(accumulated -> {
                Matcher matcher = actionInputPattern.matcher(accumulated);
                return !matcher.find();
            })
            .last()
            .block();
    }


    private String getFinalAnswerReponse(String message, String postAction, Long userId) {
        return chatClient.prompt()   
            .user(userSpec -> userSpec
                .text(prompt)
                .param("query", message + "\n\n" + postAction + "\n\n" + "**Observation:** The latest post is " + getLatestPostOfWriter(userId) + ".")
            )            
            .call()
            .content();
    }

    private Long getWriterId() {
        return 1L;
    }

    private String getLatestPostOfWriter(Long userId) {
        return postRepository.findFirstByUserIdOrderByIdDesc(userId).getContent();
    }
}
