package io.base.service.controller;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.base.service.data.Message;

@RestController
public class SampleRestController {

	private static final String template = "Hello, %s!";
	private final AtomicLong counter = new AtomicLong();

	@RequestMapping(method = { RequestMethod.GET }, path = "/message")
	public Message greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
		return new Message(counter.incrementAndGet(), String.format(template, name));
	}
}
