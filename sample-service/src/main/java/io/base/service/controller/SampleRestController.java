package io.base.service.controller;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.base.service.data.Message;

@RestController
public class SampleRestController {
	@Autowired
	Environment environment;

	private static final String template = "Hello, %s!";
	private final AtomicLong counter = new AtomicLong();

	@RequestMapping(method = { RequestMethod.GET }, path = "/message")
	public Message greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
		try {
			return new Message(counter.incrementAndGet(),
					String.format(template,
							name + " from " + InetAddress.getLocalHost().getHostName() + ":"
									+ InetAddress.getLocalHost().getHostAddress() + ":"
									+ environment.getProperty("server.port")));
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new Message(counter.incrementAndGet(), String.format(template, name));
	}
}
