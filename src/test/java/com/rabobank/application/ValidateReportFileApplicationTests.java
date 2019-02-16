package com.rabobank.application;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import com.rabobank.contants.AppConstants;


@RunWith(MockitoJUnitRunner.class)
public class ValidateReportFileApplicationTests {

	
	@Test
	public void testMainFunction() {
		String[] args = {AppConstants.TEST};
		ValidateReportFileApplication.main(args);
	}
	

}

