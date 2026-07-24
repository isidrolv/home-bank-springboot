package com.homebank.api.campaign;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "openai")
public record OpenAiProperties(
        String apiKey,
        String baseUrl,
        String textModel,
        String imageModel,
        String imageQuality,
        String imageSize
) {
}
