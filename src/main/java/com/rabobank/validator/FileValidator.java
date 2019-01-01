package com.rabobank.validator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Senthilkumar
 *
 */

public class FileValidator {

	
	
	/**
	 * @param csvFile
	 * @return
	 * @throws IOException
	 * This method validates the file sent to this rest service
	 */
	public JSONObject validateInputFile(MultipartFile csvFile) throws IOException {
		String line;
		BufferedReader br;
		Map<String, Float> recordMap = new HashMap<String, Float>();
		Set<String> duplicateRecordSet = new HashSet<String>();
		Set<String> balanceMistSet = new HashSet<String>();
		JSONObject jsonResp = new JSONObject();

		InputStream is = csvFile.getInputStream();
		br = new BufferedReader(new InputStreamReader(is));

		if (br.readLine().equalsIgnoreCase("<records>")) {
			validateXmlFile(); // validate xml file
		} else { // validate csv file
			while ((line = br.readLine()) != null) {
				String[] fieldData = line.split(",");
				if (null == recordMap.get(fieldData[0])) {
					float balanceCheck = round(Float.parseFloat(fieldData[3]) + Float.parseFloat(fieldData[4]), 2);
					if (balanceCheck == Float.parseFloat(fieldData[5])) {
						recordMap.put(fieldData[0], balanceCheck);
					} else {
						balanceMistSet.add(fieldData[0]);
					}
				} else {
					duplicateRecordSet.add(fieldData[0]);
				}

			}

			JSONArray dupRecord = new JSONArray();
			JSONArray balMistRecord = new JSONArray();

			jsonResp.put("status",
					(duplicateRecordSet.size() == 0 && balanceMistSet.size() == 0) ? "Success" : "Error");

			setJsonArrayObject(dupRecord, duplicateRecordSet);
			setJsonArrayObject(balMistRecord, balanceMistSet);
			jsonResp.put("duplicateEntries", (duplicateRecordSet.size() == 0) ? "0" : dupRecord);
			jsonResp.put("endBalanceError", (balanceMistSet.size() == 0) ? "0" : balMistRecord);
		}

		return jsonResp;
	}

	private void validateXmlFile() {
		// TODO Auto-generated method stub

	}

	private float round(float number, int decimalPlace) {
		BigDecimal bd = new BigDecimal(number);
		bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
		return bd.floatValue();
	}

	private void setJsonArrayObject(JSONArray jsonArr, Set<String> set) {

		for (String str : set) {
			jsonArr.add(str);
		}
	}

}
