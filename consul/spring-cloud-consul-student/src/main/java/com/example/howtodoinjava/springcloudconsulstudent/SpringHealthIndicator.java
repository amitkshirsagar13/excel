package com.example.howtodoinjava.springcloudconsulstudent;

import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;

public class SpringHealthIndicator extends AbstractHealthIndicator {
	@Override
	protected void doHealthCheck(Health.Builder builder) throws Exception {
		builder.up();
	}
}
