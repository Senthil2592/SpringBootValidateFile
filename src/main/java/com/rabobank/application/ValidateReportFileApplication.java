package com.rabobank.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import com.rabobank.mapper.FileValidatorMapper;
import com.rabobank.validator.FileValidator;


@SpringBootApplication
@ComponentScan(basePackages="com.rabobank.controller")
public class ValidateReportFileApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(ValidateReportFileApplication.class, args);
	}
	
	@Bean
	public FileValidator fileValidator() {
		return new FileValidator();
	}
	
	@Bean
	public FileValidatorMapper fileValidatorMapper() {
		return new FileValidatorMapper();
	}

}

