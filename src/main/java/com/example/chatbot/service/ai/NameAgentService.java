package com.example.chatbot.service.ai;

import java.time.Duration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.ChatClient.PromptUserSpec;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;

@Slf4j
@Service
@RequiredArgsConstructor
public class NameAgentService {

    @Value("classpath:/react.st")
    private Resource prompt;

    private final ChatClient chatClient;
    private final Pattern actionInputPattern = Pattern.compile("\\*\\*Action Input.*?\\n");

    public String askName(String message) {

        // 사용자 질문에 대한 Action을 가져옵니다. -> getName 함수를 호출해야 한다는 정보
        final String action = getActionResponse(message);
        log.info(action);

        // Action에 대한 LLM response를 바탕으로 Observation 프롬프트를 만들고, 
        // Final Answer를 받아옵니다. -> 사용자 질문에 대한 LLM의 최종 답변
        final String finalAnswer = getFinalAnswerReponse(message, action);
        log.info(finalAnswer);

        return finalAnswer
            .replaceAll("\\*\\*Final Answer.*?\\*\\*", "")
            .trim();
    }

    private String getActionResponse(String message) {
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

    private String getFinalAnswerReponse(String message, String actionResponse) {
        return chatClient.prompt()   
            .user(userSpec -> userSpec
                .text(prompt)
                .param("query", buildObservationQuery(message, actionResponse))
            )            
            .call()
            .content();
    }

    private String buildObservationQuery(String message, String actionQuery) {
        return message + "\n\n" + actionQuery + "\n\n" + "**Observation:** My name is " + getName() + ".";
    }

    private String getName() {
        return "gemma:2b";
    }
}
