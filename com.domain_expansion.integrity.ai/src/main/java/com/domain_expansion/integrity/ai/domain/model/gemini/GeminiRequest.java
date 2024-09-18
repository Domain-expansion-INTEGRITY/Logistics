package com.domain_expansion.integrity.ai.domain.model.gemini;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class GeminiRequest {

    private List<Content> contents;

    public GeminiRequest(String requestString) {
        contents = List.of(
            new Content(List.of(new Part(requestString)))
        );
    }

    @AllArgsConstructor
    @Getter
    private static class Content {

        private List<Part> parts;
    }

    @AllArgsConstructor
    @Getter
    private static class Part {

        private String text;

    }

}
