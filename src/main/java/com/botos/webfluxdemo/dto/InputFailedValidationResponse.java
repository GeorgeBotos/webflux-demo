package com.botos.webfluxdemo.dto;

public record InputFailedValidationResponse(int errorCode,
                                            int input,
                                            String message) {}
