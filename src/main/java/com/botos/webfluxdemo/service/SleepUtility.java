package com.botos.webfluxdemo.service;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class SleepUtility {

	public static void sleepSeconds(int seconds){
		try {
			Thread.sleep(seconds * 1_000L);
		} catch (InterruptedException e) {
			log.error(e.getMessage());
		}
	}
}
