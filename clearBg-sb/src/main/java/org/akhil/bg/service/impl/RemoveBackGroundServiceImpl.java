package org.akhil.bg.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.akhil.bg.service.RemoveBackGroundService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class RemoveBackGroundServiceImpl implements RemoveBackGroundService {

    private final WebClient webClient;

    @Override
    public byte[] removeBackground(MultipartFile file) {
        validateImageFile(file);

        try {
            return webClient.post()
                    .uri("/remove-background/v1")
                    .contentType(MediaType.MULTIPART_FORM_DATA)
                    .body(BodyInserters.fromMultipartData("image_file", file.getResource()))
                    .retrieve()
                    .onStatus(status -> !status.is2xxSuccessful(),
                            response -> response.bodyToMono(String.class)
                                    .flatMap(error -> {
                                        log.error("ClipDrop API returned error: {}", error);
                                        return Mono.error(new RuntimeException("Failed to remove background: " + error));
                                    }))
                    .bodyToMono(byte[].class)
                    .doOnSuccess(response -> log.info("Background removed successfully for file: {}", file.getOriginalFilename()))
                    .doOnError(error -> log.error("Error removing background for file: {}", file.getOriginalFilename(), error))
                    .block();

        } catch (Exception e) {
            log.error("Exception during background removal for file: {}", file.getOriginalFilename(), e);
            throw new RuntimeException("Failed to process image file", e);
        }
    }

    private void validateImageFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("Image file cannot be empty");
        }
        if (file.getContentType() == null || !file.getContentType().startsWith("image/")) {
            throw new IllegalArgumentException("File must be an image");
        }
    }
}
