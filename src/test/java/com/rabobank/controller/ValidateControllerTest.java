package com.rabobank.controller;
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
import com.rabobank.controller.ValidateController;
import com.rabobank.exception.ApplicationException;
import com.rabobank.validator.FileValidator;

@RunWith(MockitoJUnitRunner.class)
public class ValidateControllerTest {

	@InjectMocks
	private ValidateController validateController;
	
	@Mock
	private FileValidator fileValidator;
	
	
	@Test
	public void testValidateReportFileSuccess() throws IOException, ApplicationException {
		
		MockMultipartFile file = new MockMultipartFile(AppConstants.FILE, AppConstants.CSV_FILE, MediaType.TEXT_PLAIN_VALUE, AppConstants.TEST_TEXT.getBytes());
		JSONObject jsonResponse = returnJsonSuccess();
		when(fileValidator.validateInputFile(file)).thenReturn(jsonResponse);
	
		assertEquals(jsonResponse, validateController.validateReportFile(file));
	}
	
	@Test
	public void testValidateReportFileFailure() throws IOException, ApplicationException {
		
		MockMultipartFile file = new MockMultipartFile(AppConstants.FILE, AppConstants.HTML_FILE, MediaType.TEXT_PLAIN_VALUE, AppConstants.TEST_HTML_TEXT.getBytes());
		JSONObject jsonResponse = returnJsonError();
		when(fileValidator.validateInputFile(file)).thenThrow(new IOException(AppConstants.INVALID_INPUT_FILE));
		when(fileValidator.returnJsonError()).thenReturn(jsonResponse);


		assertEquals(jsonResponse, validateController.validateReportFile(file));
	}
	
	@Test
	public void testCheckServiceStatus() {
		String returnResponse = AppConstants.WELCOME_TEXT;
		assertEquals(returnResponse, validateController.checkServiceStatus());
	}
	
	
	@SuppressWarnings(AppConstants.UNCHECKED)
	private JSONObject returnJsonSuccess() {
		JSONObject jsonResponse = new JSONObject();
		jsonResponse.put(AppConstants.STATUS, AppConstants.SUCCESS);
		return jsonResponse;

	}
	
	@SuppressWarnings(AppConstants.UNCHECKED)
	private JSONObject returnJsonError() {
		JSONObject jsonResponse = new JSONObject();
		jsonResponse.put(AppConstants.STATUS, AppConstants.INVALID_INPUT_FILE);
		return jsonResponse;

	}
}
