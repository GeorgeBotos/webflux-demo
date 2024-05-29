package com.botos.webfluxdemo.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@ToString
@NoArgsConstructor
public class Response {

	private LocalDateTime date = LocalDateTime.now();
	private int output;

	public Response(int output) {
		this.output = output;
	}
}
