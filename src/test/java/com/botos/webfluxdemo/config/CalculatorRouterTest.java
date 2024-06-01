package com.botos.webfluxdemo.config;

import com.botos.webfluxdemo.dto.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.test.StepVerifier;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class CalculatorRouterTest {

	@Autowired
	private WebClient webClient;

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
}