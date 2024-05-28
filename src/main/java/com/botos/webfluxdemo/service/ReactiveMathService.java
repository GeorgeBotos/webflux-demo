package com.botos.webfluxdemo.service;

import com.botos.webfluxdemo.dto.MultiplyRequestDto;
import com.botos.webfluxdemo.dto.Response;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Log4j2
@Service
public class ReactiveMathService {

	public Mono<Response> findSquare(int input) {
		return Mono.fromSupplier(() -> input * input)
		           .map(Response::new);
	}

	public Flux<Response> findTimesTable(int input) {
		return Flux.range(1, 10)
		           .delayElements(Duration.ofSeconds(1))
		           .doOnNext(index -> log.info("Reactive math service processing: {}", index))
		           .map(index -> index * input)
		           .map(Response::new);
	}

	public Mono<Response> multiply(Mono<MultiplyRequestDto> dtoMono) {
		return dtoMono.map(dto -> dto.first() * dto.second())
		              .map(Response::new);
	}
}
