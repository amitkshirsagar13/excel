package com.example.howtodoinjava.springcloudconsulstudent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

import com.example.howtodoinjava.springcloudconsulstudent.filter.RequestHeaderFilter;

@SpringBootApplication
@EnableDiscoveryClient
public class SpringCloudConsulStudentApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringCloudConsulStudentApplication.class, args);
	}

	@Bean
	public FilterRegistrationBean loggingFilter() {
		FilterRegistrationBean registrationBean = new FilterRegistrationBean();

		registrationBean.setFilter(new RequestHeaderFilter());
		registrationBean.addUrlPatterns("/*");

		return registrationBean;
	}
}
