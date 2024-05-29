package com.botos.webfluxdemo.config;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.function.BiFunction;

@Service
public class CalculatorHandler {

	public Mono<ServerResponse> handleAddition(ServerRequest request) {
		return process(request, (a, b) -> ServerResponse.ok().bodyValue(a + b));
	}

	public Mono<ServerResponse> handleSubstruction(ServerRequest request) {
		return process(request, (a, b) -> ServerResponse.ok().bodyValue(a - b));
	}

	public Mono<ServerResponse> handleMultiplication(ServerRequest request) {
		return process(request, (a, b) -> ServerResponse.ok().bodyValue(a * b));
	}

	public Mono<ServerResponse> handleDivision(ServerRequest request) {
		return process(request, (a, b) -> b == 0
		                                  ? ServerResponse.badRequest()
		                                                  .bodyValue("second value cannot be 0")
		                                  : ServerResponse.ok()
		                                                  .bodyValue(a / b));
	}

	private Mono<ServerResponse> process(ServerRequest request, BiFunction<Integer, Integer, Mono<ServerResponse>> operationLogic) {
		var a = getValue(request, "firstInput");
		var b = getValue(request, "secondInput");
		return operationLogic.apply(a, b);
	}

	private int getValue(ServerRequest request, String key) {
		return Integer.parseInt(request.pathVariable(key));
	}
}
