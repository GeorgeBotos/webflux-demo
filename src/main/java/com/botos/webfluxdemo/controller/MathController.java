package com.botos.webfluxdemo.controller;

import com.botos.webfluxdemo.dto.Response;
import com.botos.webfluxdemo.service.MathService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("math")
public class MathController {

	private final MathService mathService;

	@GetMapping("square/{input}")
	public ResponseEntity<Response> findSquare(@PathVariable int input) {
		return ResponseEntity.ok()
		                     .body(mathService.findSquare(input));
	}

	@GetMapping("table/{input}")
	public ResponseEntity<List<Response>> findTimesTable(@PathVariable int input) {
		return ResponseEntity.ok()
		                     .body(mathService.findTimesTable(input));
	}
}
