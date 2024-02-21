package com.stream.video.streaming.webapp.controller;


import com.stream.video.streaming.webapp.Model.VideoDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/videos")
@RequiredArgsConstructor
public class StreamVideoController {

    final private RestTemplate restTemplate;
    final private WebClient.Builder webClientBuilder;

    @GetMapping
    public Mono<ResponseEntity<?>> makeCall(@RequestHeader("Authorization") String token) {
        return webClientBuilder.build().get().uri("http://authentication-service:8082/api/v1/authentication")
                .header("Authorization", token)

                .exchangeToMono(response -> {
                    if (response.statusCode().equals(HttpStatus.UNAUTHORIZED)) {
                        return Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized"));
                    } else if (response.statusCode().is2xxSuccessful()) {
                        return fetchVideo( "http://video-storage-service:8084/api/v1/videos");

                    } else {
                        return response.createException()
                                .flatMap(Mono::error);
                    }
                });
    }

    private Mono<ResponseEntity<?>> fetchVideo(String videoEndpoint) {
        return webClientBuilder.build().get().uri(videoEndpoint)
                .retrieve()
                .bodyToFlux(VideoDTO.class)
                .flatMap(video -> {
                    String videoPath = video.getPath();
                    String videoName = video.getName();
                    return webClientBuilder.exchangeStrategies(ExchangeStrategies.builder()
                                    .codecs(codecs -> codecs.defaultCodecs().maxInMemorySize(16 * 1024 * 1024))
                                    .build())
                            .build().get().uri("http://file-storage-service:8083/api/v1/files/" + videoPath + "/" + videoName)
                            .retrieve()
                            .toEntity(byte[].class)
                            .map(ResponseEntity::getBody)
                            .onErrorResume(e -> {
                                System.err.println("Error fetching video: " + videoName + ", error: " + e.getMessage());
                                return Mono.empty();
                            });
                })
                .collectList()
                .map(ResponseEntity::ok);
    }







}
