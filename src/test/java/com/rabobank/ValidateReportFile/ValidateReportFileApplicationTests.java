package com.rabobank.ValidateReportFile;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ValidateReportFileApplicationTests {

	@InjectMocks
	private ValidateReportFileApplication validateApplication;
	
	@Test
	public void testMainFunction() {
		String[] args = {"test"};
		validateApplication.main(args);
	}
	

}

