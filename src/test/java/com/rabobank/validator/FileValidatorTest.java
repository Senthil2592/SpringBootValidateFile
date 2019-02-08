package com.rabobank.validator;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import com.rabobank.contants.AppConstants;


@RunWith(MockitoJUnitRunner.class)

public class FileValidatorTest {

	
	@InjectMocks
	private FileValidator validator;
	
	// Mock data are loaded from AppConstants file
	

	@Test
	public void testValidateInputFileCsvErr() throws IOException {
		MockMultipartFile file = new MockMultipartFile("file", "records.csv", MediaType.MULTIPART_FORM_DATA_VALUE, AppConstants.CSV_SAMPLE_DATA_ERR.getBytes());
		assertEquals(returnErrorResponse(), validator.validateInputFile(file));

	}
	
	@Test
	public void testValidateInputFileXmlErr() throws IOException {
		MockMultipartFile file = new MockMultipartFile("file", "records.xml", MediaType.MULTIPART_FORM_DATA_VALUE, AppConstants.XML_SAMPLE_DATA_ERR.getBytes());
		assertEquals(returnErrorResponse(), validator.validateInputFile(file));

	}
	
	@Test (expected = IOException.class)
	public void testValidateInvalidInput() throws IOException {
		MockMultipartFile file = new MockMultipartFile("file", "records.html", MediaType.MULTIPART_FORM_DATA_VALUE, AppConstants.HTML_SAMPLE_DATA.getBytes());
		assertEquals(returnJsonError(),validator.validateInputFile(file));

	}
	
	@Test
	public void testValidateInputFileCsvSuccess() throws IOException {
		MockMultipartFile file = new MockMultipartFile("file", "records.csv", MediaType.MULTIPART_FORM_DATA_VALUE, AppConstants.CSV_SAMPLE_DATA.getBytes());
		assertEquals(returnSuccessResponse(), validator.validateInputFile(file));

	}
	
	@Test
	public void testValidateInputFileXmlSuccess() throws IOException {
		MockMultipartFile file = new MockMultipartFile("file", "records.xml", MediaType.MULTIPART_FORM_DATA_VALUE, AppConstants.XML_SAMPLE_DATA.getBytes());
		assertEquals(returnSuccessResponse(), validator.validateInputFile(file));

	}
	
	public JSONObject returnJsonError() {
		JSONObject jsonResponse = new JSONObject();
		new JSONObject().put("status","Error: Invalid Input File");
		return jsonResponse;

	}
	
	
	public JSONObject returnErrorResponse() {
		JSONObject jsonResponse = new JSONObject();
		JSONArray balanceMistakeRecord = new JSONArray();
		balanceMistakeRecord.add("194261");
		
		jsonResponse.put("duplicateEntries","0");
		jsonResponse.put("endBalanceError",balanceMistakeRecord);
		jsonResponse.put("status","Error");
		return jsonResponse;

	}
	
	public JSONObject returnSuccessResponse() {
		JSONObject jsonResponse = new JSONObject();
		
		jsonResponse.put("duplicateEntries","0");
		jsonResponse.put("endBalanceError","0");
		jsonResponse.put("status","Success");
		return jsonResponse;

	}
}
