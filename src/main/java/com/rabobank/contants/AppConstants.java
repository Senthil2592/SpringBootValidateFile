package com.rabobank.contants;

public class AppConstants {

	public static final String WELCOME_TEXT= "Hello! This is a rest Service and the Service is Up and running.";
	public static final String VALIDATE_FILE ="/validateFile";
	public static final String INVALID_INPUT_MSG ="Invalid Input Error";
	public static final String CONTEXT_ROOT ="/rabobank";

	
	
	
	//Test File Constants
	
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
