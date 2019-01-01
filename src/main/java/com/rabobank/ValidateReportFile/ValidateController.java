package com.rabobank.ValidateReportFile;

import java.io.IOException;

import org.json.simple.JSONObject;
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
public class ValidateController {

	@Autowired
	FileValidator fileValidator;

	/**
	 * @param file
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/validateFile", method = RequestMethod.POST)
	public @ResponseBody JSONObject validateReportFile(@RequestBody MultipartFile file) throws IOException {
		return fileValidator.validateInputFile(file);
	}

}
