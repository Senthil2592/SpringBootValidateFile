package com.rabobank.contants;

public class AppConstants {

	public static final String WELCOME_TEXT= "Hello! This is a rest Service and the Service is Up and running.";
	public static final String VALIDATE_FILE ="/validateFile";
	public static final String APPLICATION_EXE_ERROR ="Application execution error";
	public static final String INVALID_INPUT_FILE ="Invalid Input File";
	public static final String INPUT_OUTPUT_EXCEPTION ="Error occurred while reading file";
	public static final String BASE_PACKAGE_SCAN = "com.rabobank.controller";

	public static final String CONTEXT_ROOT ="/rabobank";
	public static final String HEADER_INFO  = "Reference,AccountNumber,Description,Start Balance,Mutation,End Balance";
	public static final String XML  = "xml";
	public static final String CSV  = "csv";
	public static final String RECORD  = "record";
	public static final String REFERENCE  = "reference";
	public static final String START_BALANCE  = "startBalance";
	public static final String ACCOUNT_NUMBER  = "accountNumber";
	public static final String DESCRIPTION  = "description";
	public static final String END_BALANCE  = "endBalance";
	public static final String MUTATION  = "mutation";
	public static final String UNCHECKED  = "unchecked";
	public static final String STATUS  = "status";
	public static final String SUCCESS  = "Success";
	public static final String ERROR  = "Error";
	public static final String DUPLICATE_ENTRIES = "duplicateEntries";
	public static final String END_BALANCE_ERROR = "endBalanceError";
	public static final String ZERO = "0";
	public static final String XML_PARSE_ERROR = "Error in parsing xml file";
	public static final String DOT = "\\.";
	public static final String COMMA = ",";
	public static final String END_BALANCE_ERROR_STATUS = "End Balance Error";
	public static final String DUPLICATE_RECORD_STATUS = "Duplicate Record";
	public static final String JSON_PARSE_ERROR = "Json Parse Error";
	public static final String FAILED_TRANSACTIONS ="failedTransactions";

	
	
	
	//Test File Constants
	
	public static final String CSV_FILE = "records.csv";
	public static final String HTML_FILE = "records.html";
	public static final String XML_FILE = "records.xml";

	public static final String TEST_TEXT = "testing";
	public static final String TEST_HTML_TEXT = "<html> </html>";
	public static final String TEST_NUMBER = "123";



	public static final String TEST = "test";
	public static final String FILE = "file";
	public static final String CSV_SAMPLE_DATA_ERR  = "194261, NL91RABO0315273637, Clothes from Jan Bakker, 21.6, -41.83, 65.65";
	public static final String XML_SAMPLE_DATA_ERR ="<record reference=\"194261\">\r\n" + 
			"    <accountNumber>NL69ABNA0433647324</accountNumber>\r\n" + 
			"    <description>Tickets for Peter Theuß</description>\r\n" + 
			"    <startBalance>26.9</startBalance>\r\n" + 
			"    <mutation>-18.78</mutation>\r\n" + 
			"    <endBalance>88.12</endBalance>\r\n" + 
			"  </record>";
	public static final String HTML_SAMPLE_DATA = "<html> </html>";
	
	public static final String CSV_SAMPLE_DATA  = "194261, NL91RABO0315273637, Clothes from Jan Bakker, 21.6, -41.83, -20.23";
	public static final String XML_SAMPLE_DATA ="<record reference=\"194261\">\r\n" + 
			"    <accountNumber>NL69ABNA0433647324</accountNumber>\r\n" + 
			"    <description>Tickets for Peter Theuß</description>\r\n" + 
			"    <startBalance>26.9</startBalance>\r\n" + 
			"    <mutation>-18.78</mutation>\r\n" + 
			"    <endBalance>8.12</endBalance>\r\n" + 
			"  </record>";
}
