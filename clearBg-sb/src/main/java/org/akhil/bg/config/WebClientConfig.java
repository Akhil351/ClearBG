package org.akhil.bg.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value("${clipdrop.api-key}")
    private String apiKey;

    @Bean
    public WebClient webClient(WebClient.Builder webClientBuilder) {
        ExchangeStrategies strategies = ExchangeStrategies.builder()
                .codecs(configurer -> configurer
                        .defaultCodecs()
                        .maxInMemorySize(10 * 1024 * 1024))  // 10 MB
                .build();
        return webClientBuilder
                .baseUrl("https://clipdrop-api.co")
                .defaultHeader("x-api-key", apiKey)
                .exchangeStrategies(strategies)
                .build();
    }
}
