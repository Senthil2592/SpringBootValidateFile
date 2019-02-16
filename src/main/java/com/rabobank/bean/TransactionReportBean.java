package com.rabobank.bean;

public class TransactionReportBean {

	private String referenceNumber;
	private String description;
	private String transactionStatus;
	private String reason;
	
	 
	public String getReferenceNumber() {
		return referenceNumber;
	}
	public void setReferenceNumber(String referenceNumber) {
		this.referenceNumber = referenceNumber;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getTransactionResult() {
		return transactionStatus;
	}
	public void setTransactionResult(String transactionResult) {
		this.transactionStatus  = transactionResult;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	
	
	@Override
    public boolean equals(Object obj) 
    { 
	 TransactionReportBean reportBean = (TransactionReportBean) obj; 
     return (reportBean.referenceNumber.equalsIgnoreCase(this.referenceNumber)); 

    }
 
	@Override
    public int hashCode() 
    { 
       return Integer.parseInt(this.referenceNumber); 
    }
	
}
