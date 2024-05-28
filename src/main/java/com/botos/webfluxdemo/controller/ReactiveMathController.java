package com.botos.webfluxdemo.controller;

import com.botos.webfluxdemo.dto.MultiplyRequestDto;
import com.botos.webfluxdemo.dto.Response;
import com.botos.webfluxdemo.service.ReactiveMathService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("reactive-math")
public class ReactiveMathController {

	private final ReactiveMathService mathService;

	@GetMapping("square/{input}")
	public Mono<Response> findSquare(@PathVariable int input) {
		return mathService.findSquare(input);
	}

	@GetMapping("table/{input}")
	public Flux<Response> findTimesTable(@PathVariable int input) {
		return mathService.findTimesTable(input);
	}

	@GetMapping(value = "table/{input}/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<Response> findTimesTableStream(@PathVariable int input) {
		return mathService.findTimesTable(input);
	}

	@PostMapping("multiply")
	public Mono<Response> multiply(@RequestBody Mono<MultiplyRequestDto> requestDtoMono,
	                               @RequestHeader Map<String, String> headers) {
		log.info(headers.toString());
		return mathService.multiply(requestDtoMono);
	}
}
