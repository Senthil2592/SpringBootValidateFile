package com.rabobank.mapper;

import java.math.BigDecimal;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;

import com.google.gson.Gson;
import com.rabobank.bean.AppBean;
import com.rabobank.bean.TransactionReportBean;
import com.rabobank.contants.AppConstants;
import com.rabobank.exception.ApplicationException;

public class FileValidatorMapper {
	
	Logger log = LoggerFactory.getLogger(FileValidatorMapper.class);


	/**
	 * @param element
	 * @return AppBean
	 * This method extracts the data from xml and sets in AppBean
	 */
	public AppBean fetchAppBeanFromXml(Element element) {

		AppBean appBean = new AppBean();
		appBean.setReference(element.getAttribute(AppConstants.REFERENCE));
		appBean.setAccountNumber(fetchValueFromXML(element, AppConstants.ACCOUNT_NUMBER));
		appBean.setDescription(fetchValueFromXML(element, AppConstants.DESCRIPTION));
		appBean.setMutation(new BigDecimal(fetchValueFromXML(element, AppConstants.MUTATION)));
		appBean.setStartBalance(new BigDecimal(fetchValueFromXML(element, AppConstants.START_BALANCE)));
		appBean.setEndBalance(new BigDecimal(fetchValueFromXML(element, AppConstants.END_BALANCE)));

		return appBean;
	}

	
	/**
	 * @param fieldData
	 * @return AppBean
	 * This method extracts the data from csv and sets in AppBean
	 */
	public AppBean fetchAppBeanFromCsv(String[] fieldData) {
		AppBean appBean = new AppBean();
		appBean.setReference(fieldData[0]);
		appBean.setAccountNumber(fieldData[1]);
		appBean.setDescription(fieldData[2]);
		appBean.setMutation(new BigDecimal(fieldData[4]));
		appBean.setStartBalance(new BigDecimal(fieldData[3]));
		appBean.setEndBalance(new BigDecimal(fieldData[5]));

		return appBean;
	}

	
	/**
	 * @param transactionReportSet
	 * @param appBean
	 * @param reason
	 * This method sets the transaction report in TransactionReport Bean
	 */
	public void fetchTransactionReport(Set<TransactionReportBean> transactionReportSet, AppBean appBean, String reason) {
		
		TransactionReportBean transactionBean = new TransactionReportBean();
		transactionBean.setReferenceNumber(appBean.getReference());
		transactionBean.setDescription(appBean.getDescription());
		transactionBean.setReason(reason);
		transactionReportSet.add(transactionBean);
	}
	
	
	/**
	 * @param parser
	 * @param transactionReportSet
	 * @return JSONObject
	 * @throws ApplicationException
	 * This method converts the Set into Json Object
	 */
	@SuppressWarnings(AppConstants.UNCHECKED)
	public JSONObject convertSetToJsonObject(JSONParser parser, Set<TransactionReportBean> transactionReportSet) throws ApplicationException {
		
		 JSONObject jsonResponse ;
		try {
			jsonResponse = new JSONObject();
			jsonResponse.put(AppConstants.FAILED_TRANSACTIONS, (JSONArray) parser.parse(new Gson().toJson(transactionReportSet)));
		} catch (ParseException e) {
			log.error(AppConstants.JSON_PARSE_ERROR);
			throw new ApplicationException(AppConstants.JSON_PARSE_ERROR);
		}
		 
		 return jsonResponse;
	}
	
	private String fetchValueFromXML(Element eElement, String attributeName) {
		return eElement.getElementsByTagName(attributeName).item(0).getTextContent();
	}

}
