package com.rabobank.ValidateReportFile;
import static org.junit.Assert.assertEquals;

import static org.mockito.Mockito.when;

import java.io.IOException;

import org.json.simple.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import com.rabobank.contants.AppConstants;
import com.rabobank.validator.FileValidator;

@RunWith(MockitoJUnitRunner.class)
public class ValidateControllerTest {

	@InjectMocks
	private ValidateController validateController;
	
	@Mock
	private FileValidator fileValidator;
	
	
	@Test
	public void testValidateReportFileSuccess() throws IOException {
		
		MockMultipartFile file = new MockMultipartFile("file", "records.csv", MediaType.TEXT_PLAIN_VALUE, "Testing".getBytes());
		JSONObject jsonResponse = returnJsonSuccess();
		when(fileValidator.validateInputFile(file)).thenReturn(jsonResponse);
	
		assertEquals(jsonResponse, validateController.validateReportFile(file));
	}
	
	@Test
	public void testValidateReportFileFailure() throws IOException {
		
		MockMultipartFile file = new MockMultipartFile("file", "records.html", MediaType.TEXT_PLAIN_VALUE, "<html> </html>".getBytes());
		JSONObject jsonResponse = returnJsonError();
		when(fileValidator.validateInputFile(file)).thenThrow(new IOException(AppConstants.INVALID_INPUT_MSG));
		when(fileValidator.returnJsonError()).thenReturn(jsonResponse);


		assertEquals(jsonResponse, validateController.validateReportFile(file));
	}
	
	@Test
	public void testCheckServiceStatus() {
		String returnResponse = AppConstants.WELCOME_TEXT;
		assertEquals(returnResponse, validateController.checkServiceStatus());
	}
	
	private JSONObject returnJsonSuccess() {
		JSONObject jsonResponse = new JSONObject();
		jsonResponse.put("status","Success");
		return jsonResponse;

	}
	
	private JSONObject returnJsonError() {
		JSONObject jsonResponse = new JSONObject();
		jsonResponse.put("status","Error: Invalid Input File");
		return jsonResponse;

	}
}
