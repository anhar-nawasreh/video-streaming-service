package com.stream.video.streaming.webapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class VideoStreamingWebappApplication {

	public static void main(String[] args) {
		SpringApplication.run(VideoStreamingWebappApplication.class, args);
	}
	@Bean
	RestTemplate getRestTemplate()
	{
		return new RestTemplate();
	}

	@Bean
	WebClient.Builder getWebClient()
	{
		return WebClient.builder();
	}



}
