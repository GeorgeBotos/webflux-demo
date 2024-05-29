package com.botos.webfluxdemo.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class ParametersControllerTest {


	@Autowired
	private WebClient webClient;

	public static final String BASE_URL = "http://localhost:8080/jobs/search";
	public static final String URL = "http://localhost:8080/jobs/search?count={count}&page={page}";

	@Test
	public void queryParametersTest() {
//		var uri = UriComponentsBuilder.fromUriString(URL)
//		                              .build(10, 20);
		var uri = UriComponentsBuilder.fromHttpUrl(BASE_URL)
		                              .queryParam("count", 10)
		                              .queryParam("page", 20)
		                              .toUriString();

		StepVerifier.create(webClient.get()
		                             .uri(uri)
		                             .retrieve()
		                             .bodyToFlux(Integer.class))
		            .expectNext(10)
		            .expectNext(20)
		            .verifyComplete();
	}

	@Test
	public void queryParametersTestWithUriBuilder() {
		StepVerifier.create(webClient.get()
		                             .uri(uriBuilder -> uriBuilder.path("jobs/search")
		                                                          .queryParam("count", 33)
		                                                          .queryParam("page", 55)
		                                                          .build())
		                             .retrieve()
		                             .bodyToFlux(Integer.class))
		            .expectNext(33)
		            .expectNext(55)
		            .verifyComplete();
	}
}