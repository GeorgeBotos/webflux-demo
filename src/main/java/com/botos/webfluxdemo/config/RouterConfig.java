package com.botos.webfluxdemo.config;

import com.botos.webfluxdemo.dto.InputFailedValidationResponse;
import com.botos.webfluxdemo.exception.InputValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.function.BiFunction;

@RequiredArgsConstructor
@Configuration
public class RouterConfig {

	private final RequestHandler requestHandler;

	@Bean
	public RouterFunction<ServerResponse> routeRoot() {
		return RouterFunctions.route()
		                      .path("router", this::routeServerResponse)
		                      .build();
	}

	@Bean
	public RouterFunction<ServerResponse> routeServerResponse() {
		return RouterFunctions.route()
		                      .GET("square/{input}", requestHandler::handleSquare)
		                      .GET("table/{input}", requestHandler::handleTable)
		                      .GET("table/{input}/stream", requestHandler::handleTableStream)
		                      .POST("multiply", requestHandler::handleMultiply)
		                      .GET("square/{input}/validation", requestHandler::handleSquareWithValidation)
		                      .onError(InputValidationException.class, handleException())
		                      .build();
	}

	private BiFunction<Throwable, ServerRequest, Mono<ServerResponse>> handleException() {
		return (err, req) -> {
			var exception = (InputValidationException) err;
			var response = new InputFailedValidationResponse(InputValidationException.getErrorCode(), exception.getInput(), exception.getMessage());
			return ServerResponse.badRequest()
			                     .bodyValue(response);
		};
	}
}
