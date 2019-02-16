package com.rabobank.validator;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.w3c.dom.Element;

import com.rabobank.bean.AppBean;
import com.rabobank.contants.AppConstants;
import com.rabobank.exception.ApplicationException;
import com.rabobank.mapper.FileValidatorMapper;



@RunWith(MockitoJUnitRunner.class)
public class FileValidatorTest {

	
	@InjectMocks
	private FileValidator validator;
	
	@Mock
	private FileValidatorMapper fileValidatorMapper;

	@Test
	@SuppressWarnings(AppConstants.UNCHECKED)
	public void testValidateInputFileCsvErr() throws IOException, ApplicationException {
		
		MockMultipartFile file = new MockMultipartFile(AppConstants.FILE, AppConstants.CSV_FILE, MediaType.MULTIPART_FORM_DATA_VALUE, AppConstants.CSV_SAMPLE_DATA_ERR.getBytes());
		JSONObject jsonResponse = returnErrorResponse();
		when(fileValidatorMapper.fetchAppBeanFromCsv(isA(String[].class))).thenReturn(buildAppBean());

		when(fileValidatorMapper.convertJsonObjectToList(isA(JSONParser.class), isA(Set.class))).thenReturn(jsonResponse);
		assertEquals(jsonResponse, validator.validateInputFile(file));

	}

	@Test
	@SuppressWarnings(AppConstants.UNCHECKED)
	public void testValidateInputFileXmlErr() throws IOException, ApplicationException {
		
		MockMultipartFile file = new MockMultipartFile(AppConstants.FILE, AppConstants.XML_FILE, MediaType.MULTIPART_FORM_DATA_VALUE, AppConstants.XML_SAMPLE_DATA_ERR.getBytes());
		
		JSONObject jsonResponse = returnErrorResponse();
		when(fileValidatorMapper.fetchAppBeanFromXml(isA(Element.class))).thenReturn(buildAppBean());

		when(fileValidatorMapper.convertJsonObjectToList(isA(JSONParser.class), isA(Set.class))).thenReturn(jsonResponse);
		assertEquals(jsonResponse, validator.validateInputFile(file));

	}
	
	@Test (expected = ApplicationException.class)
	public void testValidateInvalidInput() throws IOException, ApplicationException {
		MockMultipartFile file = new MockMultipartFile(AppConstants.FILE, AppConstants.HTML_FILE, MediaType.MULTIPART_FORM_DATA_VALUE, AppConstants.HTML_SAMPLE_DATA.getBytes());
		assertEquals(returnJsonError(),validator.validateInputFile(file));

	}
	
	@Test
	@SuppressWarnings(AppConstants.UNCHECKED)
	public void testValidateInputFileCsvSuccess() throws IOException, ApplicationException {
		
		MockMultipartFile file = new MockMultipartFile(AppConstants.FILE, AppConstants.CSV_FILE, MediaType.MULTIPART_FORM_DATA_VALUE, AppConstants.CSV_SAMPLE_DATA.getBytes());
		JSONObject jsonResponse = returnSuccessResponse();
		when(fileValidatorMapper.fetchAppBeanFromCsv(isA(String[].class))).thenReturn(buildAppBean());

		when(fileValidatorMapper.convertJsonObjectToList(isA(JSONParser.class), isA(Set.class))).thenReturn(jsonResponse);

		assertEquals(returnSuccessResponse(), validator.validateInputFile(file));

	}
	
	@Test
	@SuppressWarnings(AppConstants.UNCHECKED)
	public void testValidateInputFileXmlSuccess() throws IOException, ApplicationException {

		MockMultipartFile file = new MockMultipartFile(AppConstants.FILE, AppConstants.XML_FILE, MediaType.MULTIPART_FORM_DATA_VALUE, AppConstants.XML_SAMPLE_DATA.getBytes());
		JSONObject jsonResponse = returnSuccessResponse();
		when(fileValidatorMapper.fetchAppBeanFromXml(isA(Element.class))).thenReturn(buildAppBean());

		when(fileValidatorMapper.convertJsonObjectToList(isA(JSONParser.class), isA(Set.class))).thenReturn(jsonResponse);
		assertEquals(jsonResponse, validator.validateInputFile(file));

	}
	
	@SuppressWarnings(AppConstants.UNCHECKED)
	private JSONObject returnJsonError() {
		JSONObject jsonResponse = new JSONObject();
		new JSONObject().put(AppConstants.STATUS, AppConstants.INVALID_INPUT_FILE);
		return jsonResponse;

	}
	
	
	private AppBean buildAppBean() {
		AppBean appBean = new AppBean();
		appBean.setReference(AppConstants.TEST_NUMBER);
		appBean.setAccountNumber(AppConstants.TEST_TEXT);
		appBean.setDescription(AppConstants.TEST_TEXT);
		appBean.setEndBalance(new BigDecimal(123));
		appBean.setMutation(new BigDecimal(23));
		appBean.setStartBalance(new BigDecimal(-100));
		return appBean;
	}
	
	
	@SuppressWarnings(AppConstants.UNCHECKED)
	private JSONObject returnErrorResponse() {
		JSONObject jsonResponse = new JSONObject();
		JSONArray balanceMistakeRecord = new JSONArray();
		balanceMistakeRecord.add(AppConstants.TEST_NUMBER);
		
		jsonResponse.put(AppConstants.DUPLICATE_ENTRIES, AppConstants.ZERO);
		jsonResponse.put(AppConstants.END_BALANCE_ERROR,balanceMistakeRecord);
		jsonResponse.put(AppConstants.STATUS, AppConstants.ERROR);
		return jsonResponse;

	}
	
	@SuppressWarnings(AppConstants.UNCHECKED)
	private JSONObject returnSuccessResponse() {
		JSONObject jsonResponse = new JSONObject();
		
		jsonResponse.put(AppConstants.DUPLICATE_ENTRIES, AppConstants.ZERO);
		jsonResponse.put(AppConstants.END_BALANCE_ERROR, AppConstants.ZERO);
		jsonResponse.put(AppConstants.STATUS, AppConstants.SUCCESS);
		return jsonResponse;

	}
}
