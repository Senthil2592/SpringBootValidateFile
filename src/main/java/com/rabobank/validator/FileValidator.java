package com.rabobank.validator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * @author Senthilkumar
 *
 */

public class FileValidator {

	
	public static final String HEADER_INFO  = "Reference,AccountNumber,Description,Start Balance,Mutation,End Balance";
	Logger log = LoggerFactory.getLogger(FileValidator.class);


	/**
	 * @param inputFile
	 * @return
	 * @throws IOException This method validates the file sent to this rest service
	 */
	public JSONObject validateInputFile(MultipartFile inputFile) throws IOException {

		String fileType = inputFile.getOriginalFilename().split("\\.")[1];
		
		JSONObject jsonResponse = new JSONObject();
		Set<String> duplicateRecordSet = new HashSet<String>();
		Set<String> balanceMistakeSet = new HashSet<String>();

		if (fileType.equalsIgnoreCase("xml")) {
			validateXmlFile(inputFile, duplicateRecordSet, balanceMistakeSet);
			jsonResponse = mapJsonArrayToJsonObject(duplicateRecordSet, balanceMistakeSet);
			
		} else if (fileType.equalsIgnoreCase("csv")) { 

			Map<String, Float> recordMap = new HashMap<String, Float>();
			Stream<String> streamedLines = new BufferedReader(new InputStreamReader(inputFile.getInputStream())).lines();
		
			streamedLines.filter(data -> (data != null && !data.contains(HEADER_INFO))).forEach(line -> {

				String[] fieldData = line.split(",");
				validateInputsFromFile(fieldData[0].trim(), fieldData[3].trim(), fieldData[4].trim(), 
						fieldData[5].trim(), duplicateRecordSet, balanceMistakeSet, recordMap);
			});
			jsonResponse = mapJsonArrayToJsonObject(duplicateRecordSet, balanceMistakeSet);
			
			
		}else {
			throw new IOException("Invalid input file.");
		}

		return jsonResponse;
	}

	private void validateXmlFile(MultipartFile inputFile, Set<String> duplicateRecordSet, Set<String> balanceMistakeSet) {
		
		 Map<String, Float> recordMap = new HashMap<String, Float>();
		 DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	     DocumentBuilder dBuilder;

			              
	        try {
	        	dBuilder = dbFactory.newDocumentBuilder();
				Document doc = dBuilder.parse(inputFile.getInputStream());
		        NodeList nodeList = doc.getElementsByTagName("record");
		        
		        Stream<Node> nodeStream = IntStream.range(0, nodeList.getLength())
                        .mapToObj(nodeList::item);
		        
		        nodeStream.filter(node -> (node.getNodeType() == Node.ELEMENT_NODE)).forEach(node -> {
		        	    Element eElement = (Element) node;
		        	    
		        	   validateInputsFromFile(eElement.getAttribute("reference"), fetchValueFromXML(eElement, "startBalance"), 
		        	    		fetchValueFromXML(eElement, "mutation"), fetchValueFromXML(eElement, "endBalance"), duplicateRecordSet, balanceMistakeSet, recordMap);
		            	
		        });
				
			} catch (SAXException | ParserConfigurationException| IOException exception) {
				log.error("Error at parsing xml file", exception);
			}
	}
	
	
	private void setJsonArrayObject(JSONArray dupRecordArray, Set<String> duplicateRecordSet) {
		duplicateRecordSet.forEach(dupRecordArray::add);

	}
	
	private void validateInputsFromFile(String referenceNo, String startBalance, String mutation, String endBalance, Set<String> duplicateRecordSet,
			Set<String> balanceMistakeSet, Map<String, Float> recordMap) {
		
		if (null == recordMap.get(referenceNo)) {
			float totalSum = round(Float.parseFloat(startBalance) + Float.parseFloat(mutation));
			if (totalSum == Float.parseFloat(endBalance)) {
				recordMap.put(referenceNo, totalSum);
			} else {
				balanceMistakeSet.add(referenceNo);
			}
		} else {
			duplicateRecordSet.add(referenceNo);
		}
	
}
	private String fetchValueFromXML(Element eElement, String attributeName) {
		return eElement
                .getElementsByTagName(attributeName)
                .item(0)
                .getTextContent();
	}
	
	private JSONObject mapJsonArrayToJsonObject(Set<String> duplicateRecordSet, Set<String> balanceMistakeSet) {
		JSONObject jsonResponse = new JSONObject();
		JSONArray duplicateRecord = new JSONArray();
		JSONArray balanceMistakeRecord = new JSONArray();

		jsonResponse.put("status", (duplicateRecordSet.size() == 0 && balanceMistakeSet.size() == 0) ? "Success" : "Error");
		setJsonArrayObject(duplicateRecord, duplicateRecordSet);
		setJsonArrayObject(balanceMistakeRecord, balanceMistakeSet);
		jsonResponse.put("duplicateEntries", (duplicateRecordSet.size() == 0) ? "0" : duplicateRecord);
		jsonResponse.put("endBalanceError", (balanceMistakeSet.size() == 0) ? "0" : balanceMistakeRecord);
		return jsonResponse;
	}
	
	public JSONObject returnJsonError() {
		JSONObject jsonResponse = new JSONObject();
		jsonResponse.put("status","Error: Invalid Input File");
		return jsonResponse;

	}

	private float round(float balance) {
		BigDecimal balanceBigDecimal = new BigDecimal(balance);
		balanceBigDecimal = balanceBigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP);
		return balanceBigDecimal.floatValue();
	}

}
