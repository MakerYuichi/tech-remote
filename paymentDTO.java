/*<!-- #  HEADER INFO
#  File NAME 	: paymentDTO.java
#  PURPOSE		: 
#  INITIAL VERSION : 1.0.0
#  **********************************************************************************************************************************
#  VERSION NO   	UPDATED BY       UPDATED ON      REASON FOR CHANGE
#  1.0.0.0.1	   	Mack tyagi		 1-April-2016	 Pagination
#  1.0.0.2  	   	Ravi		     04-Jun-2018	 CR:-Add field for advance payment and remarks for
#  1.0.0.3			Sunny			 28-Aug-2020	 Code Changes to add payment date filter, select all functionality and reject functionality on maker
#  1.0.0.4			Sunny			 13-Oct-2020	 Code changes for passing product in bank LOV.
#  1.0.0.5			Hemant			 01-NOV-2021	CR - TDS Amt field added
#  1.0.0.6			Hemant Kumar	 30-May-2023	CR - OPS enhancement on vendor payments screen
#  1.0.0.7			Sanchi Agarwal	 02-Sep-2026	CR - Added H2H Status, Post Approval Rejection Source and Post Approval Rejection User
#  ********************************************************************************************************************************** -->*/

package qc.los.dto;

import java.util.Collection;

import org.apache.struts.action.ActionForm;

public class PaymentDTO extends ActionForm
{
	
	private String disbDate;
	public String getDisbDate() {
		return disbDate;
	}
	public void setDisbDate(String disbDate) {
		this.disbDate = disbDate;
	}
	private String disbursalId;
	private String disbursalDtlId;
	private String prospectId;
	private String prospectCode;
	
	private String amount;
	private String disbursalDate;	
	private String instrumentType;	
	private String instrumentNo;
	private String instrumentNo_temp;
	
	private String instrumentDate;
	
	private String entityType;
    private String entityName;
    
    private String favourOf;
    private String instNo;
    private String instDate;
	
	
	private String customerId;
	
	
	private String instrumentBankId;
	private String instrumentBranchId;
	private String instrumentAccountNumber;
	private String instrumentBankId_temp;
	private String instrumentBranchId_temp;
	private String instrumentAccountNumber_temp;

	
	private String mrRemarks;
	private String arRemarks;
	private String arApprove;
	private String arReject;
	private String mrSendToAuthor;
	private String arApprovalDecision;
	
	private String disbAmount;
	
	private String ifscCode;
	
	
	private String colProspectCode;
	private String colCustCode;
	private String colBranchId;
	private String colPanNo;
    private String colEntityName;
    
    private String branch;
    
    private String xmlData;
    
    private String supplier_Bank;
    private String supplier_Branch;
    private String supplier_Account;
    private String supplier_IFSC;
    private String supplier_MICR;
    private String printFlag;
    
    //Added by Rinky Arora :
    private String eventName;
    
    //Added by Stuti Bajpai
    private String micrCode;
    
    private String pageNo;			// 1.0.0.0.1
    private String maxPage;			// 1.0.0.0.1
    private String advancePaymentFlag; //1.0.0.2 START
    private String advancePaymentRemarks;  
    private String taxInvoiceNo;  
    private String taxInvoiceDate;  //1.0.0.2 END
    
    //1.0.0.3 add start
    private String PaymentFrom;
    private String paymentTo;
    private String paymentDate;
    //1.0.0.4 add start
    private String product;
    private String tdsAmount;//1.0.0.5
  //1.0.0.6 start
    private String noOfDays;
    private String prevNoOfDays;
    private String orixBank;
    private String vendorId;
    private String vendorName;
    private String branchId;
    private String accountNo;
  //1.0.0.6 end
    //1.0.0.7 add start
    private String h2hStatus;
    private String postApprRejSource;
    private String postApprRejUser;
    //1.0.0.7 add end
    
	public String getBranchId() {
		return branchId;
	}
	public String getPrevNoOfDays() {
		return prevNoOfDays;
	}
	public void setPrevNoOfDays(String prevNoOfDays) {
		this.prevNoOfDays = prevNoOfDays;
	}
	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}
	public String getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
    
	public String getNoOfDays() {
		return noOfDays;
	}
	public String getOrixBank() {
		return orixBank;
	}
	public void setOrixBank(String orixBank) {
		this.orixBank = orixBank;
	}
	public String getVendorId() {
		return vendorId;
	}
	public void setVendorId(String vendorId) {
		this.vendorId = vendorId;
	}
	public String getVendorName() {
		return vendorName;
	}
	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}
	public void setNoOfDays(String noOfDays) {
		this.noOfDays = noOfDays;
	}
	public String getTdsAmount() {
		return tdsAmount;
	}
	public void setTdsAmount(String tdsAmount) {
		this.tdsAmount = tdsAmount;
	}
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	//1.0.0.4 add end
	public String getPaymentDate() {
		return paymentDate;
	}
	public void setPaymentDate(String paymentDate) {
		this.paymentDate = paymentDate;
	}
	public String getPaymentFrom() {
		return PaymentFrom;
	}
	public void setPaymentFrom(String paymentFrom) {
		PaymentFrom = paymentFrom;
	}
	public String getPaymentTo() {
		return paymentTo;
	}
	public void setPaymentTo(String paymentTo) {
		this.paymentTo = paymentTo;
	}
	//1.0.0.3 add end
	public String getMaxPage() {
		return maxPage;
	}
	public void setMaxPage(String maxPage) {
		this.maxPage = maxPage;
	}
	public String getPageNo() {
		return pageNo;
	}
	public void setPageNo(String pageNo) {
		this.pageNo = pageNo;
	}
	public String getSupplier_MICR() {
		return supplier_MICR;
	}
	public void setSupplier_MICR(String supplier_MICR) {
		this.supplier_MICR = supplier_MICR;
	}
	public String getMicrCode() {
		return micrCode;
	}
	public void setMicrCode(String micrCode) {
		this.micrCode = micrCode;
	}
	public String getInstrumentNo_temp() {
		return instrumentNo_temp;
	}
	public void setInstrumentNo_temp(String instrumentNo_temp) {
		this.instrumentNo_temp = instrumentNo_temp;
	}
    
    public String getPrintFlag() {
		return printFlag;
	}
	public void setPrintFlag(String printFlag) {
		this.printFlag = printFlag;
	}
	public String getmRemarks() {
		return mRemarks;
	}
	public void setmRemarks(String mRemarks) {
		this.mRemarks = mRemarks;
	}
	public String getaRemarks() {
		return aRemarks;
	}
	public void setaRemarks(String aRemarks) {
		this.aRemarks = aRemarks;
	}
	private String mRemarks;
    private String aRemarks;
    
    
	public String getXmlData() {
		return xmlData;
	}
	public void setXmlData(String xmlData) {
		this.xmlData = xmlData;
	}
	public String getBranch() {
		return branch;
	}
	public void setBranch(String branch) {
		this.branch = branch;
	}
	public String getProspectCode() {
		return prospectCode;
	}
	public void setProspectCode(String prospectCode) {
		this.prospectCode = prospectCode;
	}
	public String getDisbursalId() {
		return disbursalId;
	}
	public void setDisbursalId(String disbursalId) {
		this.disbursalId = disbursalId;
	}
	public String getDisbursalDtlId() {
		return disbursalDtlId;
	}
	public void setDisbursalDtlId(String disbursalDtlId) {
		this.disbursalDtlId = disbursalDtlId;
	}
	public String getProspectId() {
		return prospectId;
	}
	public void setProspectId(String prospectId) {
		this.prospectId = prospectId;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getDisbursalDate() {
		return disbursalDate;
	}
	public void setDisbursalDate(String disbursalDate) {
		this.disbursalDate = disbursalDate;
	}
	public String getInstrumentType() {
		return instrumentType;
	}
	public void setInstrumentType(String instrumentType) {
		this.instrumentType = instrumentType;
	}
	public String getInstrumentNo() {
		return instrumentNo;
	}
	public void setInstrumentNo(String instrumentNo) {
		this.instrumentNo = instrumentNo;
	}
	public String getInstrumentDate() {
		return instrumentDate;
	}
	public void setInstrumentDate(String instrumentDate) {
		this.instrumentDate = instrumentDate;
	}
	public String getEntityType() {
		return entityType;
	}
	public void setEntityType(String entityType) {
		this.entityType = entityType;
	}
	public String getEntityName() {
		return entityName;
	}
	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}
	public String getFavourOf() {
		return favourOf;
	}
	public void setFavourOf(String favourOf) {
		this.favourOf = favourOf;
	}
	public String getInstNo() {
		return instNo;
	}
	public void setInstNo(String instNo) {
		this.instNo = instNo;
	}
	public String getInstDate() {
		return instDate;
	}
	public void setInstDate(String instDate) {
		this.instDate = instDate;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getInstrumentBankId() {
		return instrumentBankId;
	}
	public void setInstrumentBankId(String instrumentBankId) {
		this.instrumentBankId = instrumentBankId;
	}
	public String getInstrumentBranchId() {
		return instrumentBranchId;
	}
	public void setInstrumentBranchId(String instrumentBranchId) {
		this.instrumentBranchId = instrumentBranchId;
	}
	public String getInstrumentAccountNumber() {
		return instrumentAccountNumber;
	}
	public void setInstrumentAccountNumber(String instrumentAccountNumber) {
		this.instrumentAccountNumber = instrumentAccountNumber;
	}
	public String getInstrumentBankId_temp() {
		return instrumentBankId_temp;
	}
	public void setInstrumentBankId_temp(String instrumentBankId_temp) {
		this.instrumentBankId_temp = instrumentBankId_temp;
	}
	public String getInstrumentBranchId_temp() {
		return instrumentBranchId_temp;
	}
	public void setInstrumentBranchId_temp(String instrumentBranchId_temp) {
		this.instrumentBranchId_temp = instrumentBranchId_temp;
	}
	public String getInstrumentAccountNumber_temp() {
		return instrumentAccountNumber_temp;
	}
	public void setInstrumentAccountNumber_temp(String instrumentAccountNumber_temp) {
		this.instrumentAccountNumber_temp = instrumentAccountNumber_temp;
	}
	public String getMrRemarks() {
		return mrRemarks;
	}
	public void setMrRemarks(String mrRemarks) {
		this.mrRemarks = mrRemarks;
	}
	public String getArRemarks() {
		return arRemarks;
	}
	public void setArRemarks(String arRemarks) {
		this.arRemarks = arRemarks;
	}
	public String getArApprove() {
		return arApprove;
	}
	public void setArApprove(String arApprove) {
		this.arApprove = arApprove;
	}
	public String getArReject() {
		return arReject;
	}
	public void setArReject(String arReject) {
		this.arReject = arReject;
	}
	public String getMrSendToAuthor() {
		return mrSendToAuthor;
	}
	public void setMrSendToAuthor(String mrSendToAuthor) {
		this.mrSendToAuthor = mrSendToAuthor;
	}
	public String getArApprovalDecision() {
		return arApprovalDecision;
	}
	public void setArApprovalDecision(String arApprovalDecision) {
		this.arApprovalDecision = arApprovalDecision;
	}
	public String getDisbAmount() {
		return disbAmount;
	}
	public void setDisbAmount(String disbAmount) {
		this.disbAmount = disbAmount;
	}
	public String getIfscCode() {
		return ifscCode;
	}
	public void setIfscCode(String ifscCode) {
		this.ifscCode = ifscCode;
	}
	public String getColProspectCode() {
		return colProspectCode;
	}
	public void setColProspectCode(String colProspectCode) {
		this.colProspectCode = colProspectCode;
	}
	public String getColCustCode() {
		return colCustCode;
	}
	public void setColCustCode(String colCustCode) {
		this.colCustCode = colCustCode;
	}
	public String getColBranchId() {
		return colBranchId;
	}
	public void setColBranchId(String colBranchId) {
		this.colBranchId = colBranchId;
	}
	public String getColPanNo() {
		return colPanNo;
	}
	public void setColPanNo(String colPanNo) {
		this.colPanNo = colPanNo;
	}
	public String getColEntityName() {
		return colEntityName;
	}
	public void setColEntityName(String colEntityName) {
		this.colEntityName = colEntityName;
	}
	public String getSupplier_Bank() {
		return supplier_Bank;
	}
	public void setSupplier_Bank(String supplier_Bank) {
		this.supplier_Bank = supplier_Bank;
	}
	public String getSupplier_Branch() {
		return supplier_Branch;
	}
	public void setSupplier_Branch(String supplier_Branch) {
		this.supplier_Branch = supplier_Branch;
	}
	public String getSupplier_Account() {
		return supplier_Account;
	}
	public void setSupplier_Account(String supplier_Account) {
		this.supplier_Account = supplier_Account;
	}
	public String getSupplier_IFSC() {
		return supplier_IFSC;
	}
	public void setSupplier_IFSC(String supplier_IFSC) {
		this.supplier_IFSC = supplier_IFSC;
	}
	public String getEventName() {
		return eventName;
	}
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}
	public String getAdvancePaymentFlag() {
		return advancePaymentFlag;
	}
	public void setAdvancePaymentFlag(String advancePaymentFlag) {
		this.advancePaymentFlag = advancePaymentFlag;
	}
	public String getAdvancePaymentRemarks() {
		return advancePaymentRemarks;
	}
	public void setAdvancePaymentRemarks(String advancePaymentRemarks) {
		this.advancePaymentRemarks = advancePaymentRemarks;
	}
	public String getTaxInvoiceNo() {
		return taxInvoiceNo;
	}
	public void setTaxInvoiceNo(String taxInvoiceNo) {
		this.taxInvoiceNo = taxInvoiceNo;
	}
	public String getTaxInvoiceDate() {
		return taxInvoiceDate;
	}
	public void setTaxInvoiceDate(String taxInvoiceDate) {
		this.taxInvoiceDate = taxInvoiceDate;
	}
	
	
	
	

	
	//1.0.0.7 add start
	public String getH2hStatus() {
		return h2hStatus;
	}
	public void setH2hStatus(String h2hStatus) {
		this.h2hStatus = h2hStatus;
	}
	public String getPostApprRejSource() {
		return postApprRejSource;
	}
	public void setPostApprRejSource(String postApprRejSource) {
		this.postApprRejSource = postApprRejSource;
	}
	public String getPostApprRejUser() {
		return postApprRejUser;
	}
	public void setPostApprRejUser(String postApprRejUser) {
		this.postApprRejUser = postApprRejUser;
	}
	//1.0.0.7 add end
	
	
	
	
	 
	
		
}
