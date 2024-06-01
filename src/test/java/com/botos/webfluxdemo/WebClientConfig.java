package com.botos.webfluxdemo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Configuration
public class WebClientConfig {

	@Bean
	public WebClient webClient() {
		return WebClient.builder()
		                .baseUrl("http://localhost:8080")
//		                .defaultHeaders(headers -> headers.set("someKey", "someValue"))
//		                .defaultHeaders(headers -> headers.setBasicAuth("username", "password"))
//		                .defaultHeaders(headers -> headers.setBearerAuth("jwt"))
//		                .filter(this::sessionToken) // Alternative to headers.setBearerAuth() if token needs to checked or/and generated
                        .build();
	}

	private Mono<ClientResponse> sessionToken(ClientRequest request, ExchangeFunction function) {
		System.out.println("generating token ");
		ClientRequest.from(request)
		             .headers(headers -> headers.setBearerAuth("some lengthy jwt"));
		return function.exchange(request);
	}

	private Mono<ClientResponse> setAuthorisation(ClientRequest request, ExchangeFunction function) {
		//authorisation --> basic or oauth
		ClientRequest clrientRequest = request.attribute("authorisation")
		                                      .map(value -> value.equals("basic")
		                                                    ? withBasicAuth(request)
		                                                    : withOAuth(request))
		                                      .orElse(request);
		return function.exchange(request);
	}

	private ClientRequest withOAuth(ClientRequest request) {
		return ClientRequest.from(request)
		                    .headers(headers -> headers.setBearerAuth("jwt"))
		                    .build();
	}

	private ClientRequest withBasicAuth(ClientRequest request) {
		return ClientRequest.from(request)
		                    .headers(headers -> headers.setBasicAuth("username", "password"))
		                    .build();
	}
}
