package com.botos.webfluxdemo.config;

import com.botos.webfluxdemo.dto.MultiplyRequestDto;
import com.botos.webfluxdemo.dto.Response;
import com.botos.webfluxdemo.exception.InputValidationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class RouterConfigTest {

	@Autowired
	private WebClient webClient;

	@Test
	void blockingTest() {
		var response = webClient.get()
		                        .uri("reactive-math/square/{number}", 5)
		                        .retrieve()
		                        .bodyToMono(Response.class)
		                        .block();

		System.out.println(response);
	}

	@Test
	void nonBlockingTest_Mono() {
		StepVerifier.create(webClient.get()
		                             .uri("reactive-math/square/{number}", 5)
		                             .retrieve()
		                             .bodyToMono(Response.class))
		            .expectNextMatches(response -> response.getOutput() == 25)
		            .verifyComplete();
	}

	@Test
	void nonBlockingTest_Flux() {
		StepVerifier.create(webClient.get()
		                             .uri("reactive-math/table/{number}", 5)
		                             .retrieve()
		                             .bodyToFlux(Response.class)
		                             .doOnNext(System.out::println))
		            .expectNextCount(10)
		            .verifyComplete();
	}

	@Test
	void testPostMethod() {
		StepVerifier.create(webClient.post()
		                             .uri("multiply")
		                             .bodyValue(new MultiplyRequestDto(5, 2))
		                             .retrieve()
		                             .bodyToMono(Response.class))
		            .expectNextMatches(response -> response.getOutput() == 10)
		            .verifyComplete();
	}

	@Test
	void testErrorMessage() {
		StepVerifier.create(webClient.get()
		                             .uri("square/{input}/validation", 22)
		                             .retrieve()
		                             .bodyToMono(Response.class))
		            .expectErrorMessage("400 Bad Request from GET http://localhost:8080/square/22/validation")
		            .verify();
	}

	@Test
	void testError() {
		StepVerifier.create(webClient.get()
		                             .uri("square/{input}/validation", 22)
		                             .retrieve()
		                             .bodyToMono(Response.class))
		            .verifyError(WebClientResponseException.BadRequest.class);
	}

//	@Test
//	void testErrorWithExchange() {
//		StepVerifier.create(webClient.get()
//		                             .uri("square/{input}/validation", 22)
//		                             .exchangeToMono(response -> response.statusCode()
//		                                                                 .is4xxClientError()
//		                                                         ? response.bodyToMono(InputValidationException.class)
//		                                                         : response.bodyToMono(Response.class)))
//		            .expectError(InputValidationException.class)
//		            .verify();
//	}
}
