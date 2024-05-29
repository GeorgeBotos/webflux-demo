package com.botos.webfluxdemo.config;

import com.botos.webfluxdemo.dto.InputFailedValidationResponse;
import com.botos.webfluxdemo.dto.MultiplyRequestDto;
import com.botos.webfluxdemo.dto.Response;
import com.botos.webfluxdemo.exception.InputValidationException;
import com.botos.webfluxdemo.service.ReactiveMathService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class RequestHandler {

	private final ReactiveMathService mathService;

	public Mono<ServerResponse> handleSquare(ServerRequest serverRequest) {
		var input = Integer.parseInt(serverRequest.pathVariable("input"));
		Mono<Response> responseMono = mathService.findSquare(input);
		return ServerResponse.ok()
		                     .body(responseMono, Response.class);
	}

	public Mono<ServerResponse> handleTable(ServerRequest serverRequest) {
		var input = Integer.parseInt(serverRequest.pathVariable("input"));
		Flux<Response> responseFlux = mathService.findTimesTable(input);
		return ServerResponse.ok()
		                     .body(responseFlux, Response.class);
	}

	public Mono<ServerResponse> handleTableStream(ServerRequest serverRequest) {
		var input = Integer.parseInt(serverRequest.pathVariable("input"));
		Flux<Response> responseFlux = mathService.findTimesTable(input);
		return ServerResponse.ok()
		                     .contentType(MediaType.TEXT_EVENT_STREAM)
		                     .body(responseFlux, Response.class);
	}

	public Mono<ServerResponse> handleMultiply(ServerRequest serverRequest) {
		var requestDtoMono = serverRequest.bodyToMono(MultiplyRequestDto.class);
		var responseMono = mathService.multiply(requestDtoMono);
		return ServerResponse.ok()
		                     .body(responseMono, Response.class);
	}

	public Mono<ServerResponse> handleSquareWithValidation(ServerRequest serverRequest) {
		var input = Integer.parseInt(serverRequest.pathVariable("input"));
		if(input < 10 || input > 20) {
			return Mono.error(new InputValidationException(input));
		}
		Mono<Response> responseMono = mathService.findSquare(input);
		return ServerResponse.ok()
		                     .body(responseMono, Response.class);

	}
}
