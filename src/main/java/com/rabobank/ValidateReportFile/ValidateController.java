package com.rabobank.ValidateReportFile;

import java.io.IOException;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.rabobank.validator.FileValidator;

/**
 * @author Senthilkumar
 *
 */
@RestController
@RequestMapping("/rabobank")
public class ValidateController {

	@Autowired
	FileValidator fileValidator;
	
	Logger log = LoggerFactory.getLogger(ValidateController.class);

	/**
	 * @param file
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/validateFile", method = RequestMethod.POST)
	public @ResponseBody JSONObject validateReportFile(@RequestBody MultipartFile file) {
		JSONObject jsonResponse = null;
		try {
			jsonResponse = fileValidator.validateInputFile(file);
		} catch (IOException exception) {
			jsonResponse = fileValidator.returnJsonError();
			log.error("Invalid Input Error", exception);   //for Splunk logging
		}finally {
			return jsonResponse;
		} 
	}
	
	@RequestMapping(value = "/validateFile", method = RequestMethod.GET)
	public @ResponseBody String checkServiceStatus() {
		return "Hello! This is a rest Service and the Service is Up and running.";
	}

}
