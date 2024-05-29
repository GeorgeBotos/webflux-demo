package com.botos.webfluxdemo.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicate;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@RequiredArgsConstructor
@Configuration
public class CalculatorRouter {

	private final CalculatorHandler calculator;

	@Bean
	public RouterFunction<ServerResponse> routeCalculator() {
		return RouterFunctions.route()
		                      .GET("calculator/{firstInput}/{secondInput}", isOperation("+"), calculator::handleAddition)
		                      .GET("calculator/{firstInput}/{secondInput}", isOperation("-"), calculator::handleSubstruction)
		                      .GET("calculator/{firstInput}/{secondInput}", isOperation("*"), calculator::handleMultiplication)
		                      .GET("calculator/{firstInput}/{secondInput}", isOperation("/"), calculator::handleDivision)
		                      .GET("calculator/{firstInput}/{secondInput}", request -> ServerResponse.badRequest().bodyValue("OP header should be +, -, * or /"))
		                      .build();
	}

	private RequestPredicate isOperation(String operation) {
		return RequestPredicates.headers(headers -> headers.asHttpHeaders()
		                                                   .toSingleValueMap()
		                                                   .get("OP")
		                                                   .equals(operation));
	}
}
