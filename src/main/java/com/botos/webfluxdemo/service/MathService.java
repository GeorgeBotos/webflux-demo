package com.botos.webfluxdemo.service;

import com.botos.webfluxdemo.dto.Response;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.IntStream;

import static com.botos.webfluxdemo.service.SleepUtility.sleepSeconds;

@Log4j2
@Service
public class MathService {

	public Response findSquare(int input) {
		return new Response(input * input);
	}

	public List<Response> findTimesTable(int input) {
		return IntStream.rangeClosed(1, 10)
		                .peek(index -> sleepSeconds(1))
		                .peek(index -> log.info("Math service processing: {}", index))
		                .mapToObj(index -> new Response(index * input))
		                .toList();
	}
}
