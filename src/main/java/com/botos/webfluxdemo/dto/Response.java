package com.botos.webfluxdemo.dto;

import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;

@Data
@ToString
public class Response {

	private LocalDate date = LocalDate.now();
	private int output;

	public Response(int output) {
		this.output = output;
	}
}
