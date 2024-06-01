package com.botos.webfluxdemo.config;

import com.botos.webfluxdemo.dto.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Objects;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class CalculatorRouterTest {

	@Autowired
	private WebClient webClient;

	public static final String FORMAT = "%d %s %d = %s";
	public static final int FIRST_VALUE = 10;

	@Test
	void testHeadersForAddition() {
		StepVerifier.create(webClient.get()
		                             .uri("calculator/{firstInput}/{secondInput}", 5, 2)
		                             .headers(httpHeaders -> httpHeaders.add("OP", "+"))
		                             .retrieve()
		                             .bodyToMono(Response.class))
		            .expectNextMatches(result -> result.getOutput() == 7)
		            .verifyComplete();
	}

	@Test
	void testForAuthentication() {
		StepVerifier.create(webClient.get()
		                             .uri("calculator/{firstInput}/{secondInput}", 5, 2)
		                             .headers(headers -> {
			                             headers.setBasicAuth("username", "password");
			                             headers.add("OP", "-");
		                             })
		                             .retrieve()
		                             .bodyToMono(Response.class))
		            .expectNextMatches(result -> result.getOutput() == 3)
		            .verifyComplete();
	}

	@Test
	public void testAll() {
		StepVerifier.create(Flux.range(1, 5)
		                        .flatMap(secondValue -> Flux.just("+", "-", "*", "/")
		                                                    .flatMap(operation -> send(secondValue, operation)))
		                        .doOnNext(System.out::println))
		            .expectNextCount(20)
		            .verifyComplete();
	}

	private Mono<String> send(int secondValue, String operation) {
		return webClient.get()
		                .uri("calculator/{firstValue}/{secondValue}", FIRST_VALUE, secondValue)
		                .headers(httpHeaders -> httpHeaders.set("OP", operation))
		                .retrieve()
		                .bodyToMono(String.class)
		                .map(result -> FORMAT.formatted(FIRST_VALUE, operation, secondValue, result));
	}
}