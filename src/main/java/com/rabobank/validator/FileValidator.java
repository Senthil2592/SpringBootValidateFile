package com.rabobank.validator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.rabobank.bean.AppBean;
import com.rabobank.bean.TransactionReportBean;
import com.rabobank.contants.AppConstants;
import com.rabobank.exception.ApplicationException;
import com.rabobank.mapper.FileValidatorMapper;

/**
 * @author Senthilkumar
 *
 */

public class FileValidator {

	
	
	Logger log = LoggerFactory.getLogger(FileValidator.class);
	
	@Autowired
	private FileValidatorMapper fileValidatorMapper;


	/**
	 * @param inputFile
	 * @return
	 * @throws IOException This method validates the file sent to this rest service
	 * @throws ApplicationException 
	 * @throws ParseException 
	 * This method receives the file input and does the validationS
	 */
	public JSONObject validateInputFile(MultipartFile inputFile) {

		String fileType = inputFile.getOriginalFilename().split(AppConstants.DOT)[1];
		
		JSONObject jsonResponse ;
		Set<TransactionReportBean> transactionReportSet = new HashSet<TransactionReportBean>();
		JSONParser parser = new JSONParser();

		if (fileType.equalsIgnoreCase(AppConstants.XML)) {
			validateXmlFile(inputFile, transactionReportSet);
			jsonResponse  = fileValidatorMapper.convertSetToJsonObject(parser, transactionReportSet);
			
		} else if (fileType.equalsIgnoreCase(AppConstants.CSV)) { 

			Set<String> transactionRecordSet = new HashSet<String>();
			Stream<String> streamedLines = null;
			
			try {
				streamedLines = new BufferedReader(new InputStreamReader(inputFile.getInputStream())).lines();
			} catch (IOException e) {
				log.error(AppConstants.INPUT_OUTPUT_EXCEPTION);
				throw new ApplicationException(AppConstants.INPUT_OUTPUT_EXCEPTION);
			}
		
			streamedLines.filter(data -> (data != null && !data.contains(AppConstants.HEADER_INFO))).forEach(line -> {

				String[] fieldData = line.split(AppConstants.COMMA);
				AppBean appBean = fileValidatorMapper.fetchAppBeanFromCsv(fieldData);
				validateInputsFromFile(appBean, transactionReportSet, transactionRecordSet);
			});
			jsonResponse  = fileValidatorMapper.convertSetToJsonObject(parser, transactionReportSet);			
			
		}else {
			log.error(AppConstants.INVALID_INPUT_FILE);
			throw new ApplicationException(AppConstants.INVALID_INPUT_FILE);
		}

		return jsonResponse;
	}
	

	//Validate XML file
	private void validateXmlFile(MultipartFile inputFile, Set<TransactionReportBean> transactionReportSet) {
		
		 Set<String> transactionRecordSet = new HashSet<String>();
		 DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	     DocumentBuilder dBuilder;

			              
	        try {
	        	dBuilder = dbFactory.newDocumentBuilder();
				Document doc = dBuilder.parse(inputFile.getInputStream());
		        NodeList nodeList = doc.getElementsByTagName(AppConstants.RECORD);
		        
		        Stream<Node> nodeStream = IntStream.range(0, nodeList.getLength()).mapToObj(nodeList::item);
		        
		        nodeStream.filter(node -> (node.getNodeType() == Node.ELEMENT_NODE)).forEach(node -> {
		        	
		        	  Element element = (Element) node;
		        	  AppBean appBean = fileValidatorMapper.fetchAppBeanFromXml(element);
		        	  validateInputsFromFile(appBean, transactionReportSet, transactionRecordSet);
		        });
				
			} catch (SAXException | ParserConfigurationException| IOException exception) {
				log.error(AppConstants.XML_PARSE_ERROR, exception);
				throw new ApplicationException(AppConstants.XML_PARSE_ERROR);

			}
	}
	
	
	//Common Validation logic for both csv and xml files
	private void validateInputsFromFile(AppBean appBean, Set<TransactionReportBean> transactionReportSet, Set<String> transactionRecordSet) {
		
		if (!transactionRecordSet.contains(appBean.getReference())) {
			BigDecimal totalSum = appBean.getStartBalance().add(appBean.getMutation());
			
			if (totalSum.compareTo(appBean.getEndBalance()) == 0) {
				transactionRecordSet.add(appBean.getReference());
			} else {
				fileValidatorMapper.fetchTransactionReport(transactionReportSet, appBean, AppConstants.END_BALANCE_ERROR_STATUS);
			}
		} else {
			fileValidatorMapper.fetchTransactionReport(transactionReportSet, appBean, AppConstants.DUPLICATE_RECORD_STATUS);

		}
	
	}
	
	
	@SuppressWarnings(AppConstants.UNCHECKED)
	public JSONObject returnJsonError() {
		JSONObject jsonResponse = new JSONObject();
		jsonResponse.put(AppConstants.STATUS, AppConstants.INVALID_INPUT_FILE);
		return jsonResponse;

	}

}
