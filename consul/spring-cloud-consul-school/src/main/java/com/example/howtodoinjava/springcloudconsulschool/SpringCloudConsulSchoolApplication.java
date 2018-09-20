package com.example.howtodoinjava.springcloudconsulschool;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

import com.example.howtodoinjava.springcloudconsulschool.filter.RequestHeaderFilter;

@EnableDiscoveryClient
@SpringBootApplication
public class SpringCloudConsulSchoolApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringCloudConsulSchoolApplication.class, args);
	}

	@Bean
	public FilterRegistrationBean loggingFilter() {
		FilterRegistrationBean registrationBean = new FilterRegistrationBean();

		registrationBean.setFilter(new RequestHeaderFilter());
		registrationBean.addUrlPatterns("/*");

		return registrationBean;
	}
}
