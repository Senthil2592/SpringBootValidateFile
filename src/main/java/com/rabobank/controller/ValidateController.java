package com.rabobank.controller;

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

import com.rabobank.contants.AppConstants;
import com.rabobank.exception.ApplicationException;
import com.rabobank.validator.FileValidator;

/**
 * @author Senthilkumar
 *
 */
@RestController
@RequestMapping(AppConstants.CONTEXT_ROOT)
public class ValidateController {

	@Autowired
	FileValidator fileValidator;
	
	Logger log = LoggerFactory.getLogger(ValidateController.class);

	/**
	 * @param file
	 * @return
	 * @throws IOException
	 */
	
	@RequestMapping(value = AppConstants.VALIDATE_FILE, method = RequestMethod.POST)
	public @ResponseBody JSONObject validateReportFile(@RequestBody MultipartFile file) {
		JSONObject jsonResponse = null;
		try {
			jsonResponse = fileValidator.validateInputFile(file);
		} catch (IOException | ApplicationException exception ) {
			jsonResponse = fileValidator.returnJsonError();
			log.error(AppConstants.APPLICATION_EXE_ERROR, exception);   
		}
		return jsonResponse;
		 
	}
	
	@RequestMapping(value = AppConstants.VALIDATE_FILE, method = RequestMethod.GET)
	public @ResponseBody String checkServiceStatus() {
		return AppConstants.WELCOME_TEXT;
	}

}
