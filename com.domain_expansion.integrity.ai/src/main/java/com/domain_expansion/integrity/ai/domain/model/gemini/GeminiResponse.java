package com.domain_expansion.integrity.ai.domain.model.gemini;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class GeminiResponse {

    private List<Candidate> candidates;

    @Getter
    public static class Candidate {

        List<SafetyRating> safetyRatings;
        private Content content;
        private String finishReason;
        private int index;
    }

    @Getter
    public static class Content {

        private List<Part> parts;
        private String role;
    }

    @Getter
    public static class Part {

        private String text;
    }

    @Getter
    public static class SafetyRating {

        private String category;
        private String probability;
    }


    @Getter
    public static class usageMetadata {

        private Long promptTokenCount;
        private Long candidatesTokenCount;
        private Long totalTokenCount;

    }
}
