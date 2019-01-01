package com.rabobank.ValidateReportFile;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

import com.rabobank.validator.FileValidator;


@SpringBootApplication
public class ValidateReportFileApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(ValidateReportFileApplication.class, args);
	}
	
	@Bean
	public FileValidator fileValidator() {
		return new FileValidator();
	}

}

