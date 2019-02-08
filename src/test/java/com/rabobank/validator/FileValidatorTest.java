package com.rabobank.validator;

import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import static org.junit.Assert.assertEquals;


@RunWith(MockitoJUnitRunner.class)

public class FileValidatorTest {

	
	@InjectMocks
	private FileValidator validator;
	
	
	public static final String SAMPLE_DATA  = "194261, NL91RABO0315273637, Clothes from Jan Bakker, 21.6, -41.83, 65.65";


	@Test
	public void testValidateInputFileCsv() throws IOException {
		MockMultipartFile file = new MockMultipartFile("file", "records.csv", MediaType.MULTIPART_FORM_DATA_VALUE, SAMPLE_DATA.getBytes());
		assertEquals(returnSuccessResponse(), validator.validateInputFile(file));

	}
	
	@Test (expected = IOException.class)
	public void testValidateInvalidInput() throws IOException {
		MockMultipartFile file = new MockMultipartFile("file", "records.html", MediaType.MULTIPART_FORM_DATA_VALUE, SAMPLE_DATA.getBytes());
		assertEquals(returnJsonError(),validator.validateInputFile(file));

	}
	
	public JSONObject returnJsonError() {
		JSONObject jsonResponse = new JSONObject();
		new JSONObject().put("status","Error: Invalid Input File");
		return jsonResponse;

	}
	
	
	public JSONObject returnSuccessResponse() {
		JSONObject jsonResponse = new JSONObject();
		JSONArray balanceMistakeRecord = new JSONArray();
		balanceMistakeRecord.add("194261");
		
		jsonResponse.put("duplicateEntries","0");
		jsonResponse.put("endBalanceError",balanceMistakeRecord);
		jsonResponse.put("status","Error");
		return jsonResponse;

	}
}
