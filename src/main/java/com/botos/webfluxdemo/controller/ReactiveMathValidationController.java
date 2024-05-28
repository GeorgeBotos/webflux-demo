package com.botos.webfluxdemo.controller;

import com.botos.webfluxdemo.dto.Response;
import com.botos.webfluxdemo.exception.InputValidationException;
import com.botos.webfluxdemo.service.ReactiveMathService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
@RequestMapping("reactive-math")
public class ReactiveMathValidationController {

	private final ReactiveMathService mathService;

	@GetMapping("square/{input}/throw")
	public Mono<Response> findSquare(@PathVariable int input) {
		if (input < 10 || input > 20) {
			throw new InputValidationException(input);
		}
		return mathService.findSquare(input);
	}

	@GetMapping("square/{input}/mono-error")
	public Mono<Response> monoError(@PathVariable int input) {
		return Mono.just(input)
		           .handle((integer, sink) -> {
			           if (integer >= 10 && integer <= 20) {
				           sink.next(integer);
			           } else {
				           sink.error(new InputValidationException(integer));
			           }
		           })
		           .cast(Integer.class)
		           .flatMap(mathService::findSquare);
	}
}
