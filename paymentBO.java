/*<!-- #  HEADER INFO
#  File NAME 	: paymentBO.java
#  PURPOSE		: 
#  INITIAL VERSION : 1.0.0
#  **********************************************************************************************************************************
#  VERSION NO   	UPDATED BY       UPDATED ON      REASON FOR CHANGE
#  1.0.0.0.1	   	Mack tyagi		 1-April-2016	 Pagination
#	1.0.2				jitender		17 oct 2017      instrument date check according to disbursal date and business date
#  1.0.0.2	   		Ravi		     04-Jun-2018	 CR:-Add field for advance payment and remarks for
#  1.0.0.3	   		Ravinder		 29-Aug-2019	     Add parameter in save proc for entityId.
#  1.0.0.4			Sunny			 28-Aug-2020	 Code Changes to add payment date filter, select all functionality and reject functionality on maker
#  1.0.0.5			Sunny			 13-Oct-2020	 Code changes for passing product in bank LOV.
#  1.0.0.6			Hemant			 01-Nov-2021	CR - Add TDS_Amount field
#  1.0.0.7			Hemant			 04-Jan-2022	104884 - Error in payment maker stage||DMFL02000034382 -Inst No Special char
#  1.0.0.8			Hemant			 30-May-2023	CR - OPS enhancement on vendor payments screen
#  1.0.0.9			Sanchi Agarwal	 02-Sep-2026	CR - Added H2H Status, Post Approval Rejection Source and Post Approval Rejection User columns on Vendor Payment Viewer screen
#  ********************************************************************************************************************************** -->*/
package qc.los.model.bo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import qc.common.dto.ReturnParamDTO;
import qc.common.util.PrintDBConnectionMap;
import qc.dal.DAL;
import qc.dal.dto.ParameterDTO;
import qc.dal.exception.DBConnectionException;
import qc.dal.util.DBConnection;
import qc.dal.util.PrintParameterList;
import qc.llmhome.dto.ActivityInfoDTO;
import qc.lms.dto.RefundDTO;
import qc.los.dto.DisbursalCancellationDTO;
import qc.los.dto.DisbursalInfoDto;
import qc.los.dto.PaymentDTO;
import qc.sso.dto.UserInfoDTO;
/**
 * @author sanjay_kumar
 *
 */
public class PaymentBO
{
	protected static Logger log = Logger.getLogger(PaymentBO.class);
	Locale locale = new Locale("en","US");
	ResourceBundle resource = ResourceBundle.getBundle("resourceProperties.losConstants", locale);
	static String disAmt ;
	
	
	public ReturnParamDTO  save(String entityId,PaymentDTO payDto,ActivityInfoDTO activityInfo,UserInfoDTO userInfo,Map dbConnectionMap ) throws DBConnectionException
	{
		log.info("Start"); 
		DAL dal=new DAL();
		int parameterCount=0;
		ArrayList arrParameter=new ArrayList();
		
		DisbursalCancellationDTO disbursalDto=null;
		String procName=null;
		ReturnParamDTO returnDto=new ReturnParamDTO();
		ParameterDTO parameterDtoStatus =null;
		ParameterDTO parameterDtoMsg =null;
		DecimalFormat df=new DecimalFormat("############");
		
		
		Connection connection=null;
		if(dbConnectionMap.get("DB_SERVER").toString().equals("ORACLE"))
		{   
			procName="{call PKG_LOS_DISBURSAL_PAYMENT.PR_SAVE_PAYMENT_DATA(?,?,?,?,?,?,?)}"; //1.0.0.3
			
			arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,(!"".equals(activityInfo.getActivityMode()))&&activityInfo.getActivityMode()!=null?activityInfo.getActivityMode():""));
			arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,(!"".equals(payDto.getArApprovalDecision()))&&payDto.getArApprovalDecision()!=null?payDto.getArApprovalDecision():""));
			arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,(!"".equals(payDto.getXmlData()))&&payDto.getXmlData()!=null?payDto.getXmlData():""));
			arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,(!"".equals(userInfo.getUserId()))&&userInfo.getUserId()!=null?userInfo.getUserId():""));
			arrParameter.add(new ParameterDTO(++parameterCount,"OUT",oracle.jdbc.OracleTypes.VARCHAR,null));
			arrParameter.add(new ParameterDTO(++parameterCount,"OUT",oracle.jdbc.OracleTypes.VARCHAR,null));
			arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,entityId));//CONST_LOAN_ENTITY_ID //1.0.0.3
		} 
		
		try
		{
			connection=DBConnection.getConnection(dbConnectionMap);
			connection.setAutoCommit(false);
			dal.processSPROC(procName, arrParameter,dbConnectionMap,connection);
			ParameterDTO parameterDto1 = new ParameterDTO();
			ParameterDTO parameterDto2 = new ParameterDTO();
			
			// parameterDto2 = (ParameterDTO)arrParameter.get(9);
			
			
			parameterDtoStatus=(ParameterDTO)arrParameter.get(4);
			parameterDtoMsg=(ParameterDTO)arrParameter.get(5);
			returnDto.setMessage((String)parameterDtoMsg.getParameterValue());
			returnDto.setStatus((String)parameterDtoStatus.getParameterValue());
			
			
			if("S".equalsIgnoreCase((String)parameterDtoStatus.getParameterValue()))
			{
			    DBConnection.commitTransaction(connection);
			}
			else
			{
				log.error("processSPROC - FAIL:");
				log.error("processSPROC - Parameter : \r\n1. procName= " + procName + "; \r\n2. colParameter=( " + PrintParameterList.printParameterList(arrParameter) + " );");
				DBConnection.rollbackTransaction(connection);
			}
		}			
		catch(Exception e)
		{
			DBConnection.rollbackTransaction(connection);
			StackTraceElement[] elements = new Throwable().getStackTrace();
        	String calleeMethod = elements[0].getMethodName();	        	        
        	log.error("Exception - FAIL:");
			log.error("Exception - Parameter : \r\n1. procName= " + procName + "; \r\n2. colParameter=( " + PrintParameterList.printParameterList(arrParameter) + " );");
        	log.error("Exception - "+e.getMessage());
		}
		finally
		{
			DBConnection.closeConnection(connection);
		}
		
		log.info("End"); 
		return returnDto;
	}
	
	
	
	
	public  Collection getSearchData(String userId,String entityId,PaymentDTO payment, ActivityInfoDTO activityDto, Map dbConnectionMap)
	{
		log.info("Start" ); 
		ResultSet rs = null;
		Collection searchData= new ArrayList();
		PaymentDTO paymentDto = null;
		ArrayList arrParameter=new ArrayList();	
		int parameterCount=0;
		DAL dal=new DAL();
		ParameterDTO parameterDto = null;
		ParameterDTO parameterDto1=null;
		ParameterDTO parameterDto2=null;	//1.0.0.0.1
		ParameterDTO parameterDto3=null;	//1.0.0.0.1
		ParameterDTO parameterDto4=null;	//1.0.0.0.8
		ParameterDTO parameterDto5=null;	//1.0.0.0.8
		String procName=null;
		
		if(dbConnectionMap.get("DB_SERVER").toString().equals("ORACLE"))
		  {
			    procName="{call PKG_LOS_DISBURSAL_PAYMENT.PR_GET_PAYMENT_DATA(?,?,?,?,? ,?,?,?,?,? ,?,?,?,?,?, ?,?,?,?,?)}";//1.0.0.8
			 	arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR, ((!"".equals(payment.getColProspectCode())) && !(payment.getColProspectCode()==null)) ? payment.getColProspectCode():""));
			 	arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR, ((!"".equals(payment.getColBranchId())) && payment.getColBranchId()!=null) ? payment.getColBranchId() : ""));
			 	arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR, ((!"".equals(payment.getColCustCode())) && payment.getColCustCode()!=null) ? payment.getColCustCode() : ""));
			 	arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR, ((!"".equals(payment.getColEntityName())) && payment.getColEntityName()!=null) ? payment.getColEntityName() : ""));
			 	arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR, ((!"".equals(payment.getColPanNo()))&&payment.getColPanNo()!=null) ? payment.getColPanNo() : ""));
			 	arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,""+activityDto.getActivityMode()));
			 	arrParameter.add(new ParameterDTO(++parameterCount,"OUT",oracle.jdbc.OracleTypes.VARCHAR,null));
			 	arrParameter.add(new ParameterDTO(++parameterCount,"OUT",oracle.jdbc.OracleTypes.VARCHAR,null));
			 	arrParameter.add(new ParameterDTO(++parameterCount,"OUT",oracle.jdbc.OracleTypes.CURSOR,null));
			 	arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,userId));
			 	arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,payment.getPageNo()));	//1.0.0.0.1
			 	arrParameter.add(new ParameterDTO(++parameterCount,"OUT",oracle.jdbc.OracleTypes.VARCHAR,null));
			 	arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,entityId));//1.0.0.0.1	
			 	//1.0.0.4 add start
			 	arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR, ((!"".equals(payment.getPaymentFrom()))&&payment.getPaymentFrom()!=null) ? payment.getPaymentFrom() : ""));
			 	arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR, ((!"".equals(payment.getPaymentTo()))&&payment.getPaymentTo()!=null) ? payment.getPaymentTo() : ""));
			 	//Start 1.0.0.8
			 	arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR, ((!"".equals(payment.getVendorId()))&&payment.getVendorId()!=null) ? payment.getVendorId() : ""));
			 	arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR, ((!"".equals(payment.getProduct()))&&payment.getProduct()!=null) ? payment.getProduct() : ""));
			 	arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR, ((!"".equals(payment.getOrixBank()))&&payment.getOrixBank()!=null) ? payment.getOrixBank() : ""));
			 	arrParameter.add(new ParameterDTO(++parameterCount,"OUT",oracle.jdbc.OracleTypes.VARCHAR,null));//Instrument PN_DAYS_FUTURE_INSTR_DATE
				arrParameter.add(new ParameterDTO(++parameterCount,"OUT",oracle.jdbc.OracleTypes.VARCHAR,null));//Instrument PN_DAYS_PREV_INSTR_DATE
			 	//End 1.0.0.8
			 	//1.0.0.4 add end
		  }
		 
		try
		{
			dal.processSPROC(procName, arrParameter,dbConnectionMap);
			parameterDto = (ParameterDTO)arrParameter.get(6);
			parameterDto2 = (ParameterDTO)arrParameter.get(7);
			parameterDto1 = (ParameterDTO)arrParameter.get(8);
			parameterDto3 = (ParameterDTO)arrParameter.get(11);	//1.0.0.0.1
			parameterDto4 = (ParameterDTO)arrParameter.get(18);//1.0.0.0.8
			parameterDto5 = (ParameterDTO)arrParameter.get(19);//1.0.0.0.8
			String maxPage=(String)parameterDto3.getParameterValue();	//1.0.0.0.1
			String futureNoOfDays=(String)parameterDto4.getParameterValue();//1.0.0.0.8
			String prevNoOfDays=(String)parameterDto5.getParameterValue();//1.0.0.0.8
			
			ArrayList list=(ArrayList)parameterDto1.getParameterValue();
			Iterator itr = list.iterator();
			Map resultSetMap = null;
			
			int count = 1;
			if(itr!=null)
			{
				while(itr.hasNext())
				{
					paymentDto = new PaymentDTO();
					resultSetMap=(Map)itr.next();
					paymentDto.setMaxPage(maxPage);		//1.0.0.0.1
					paymentDto.setNoOfDays(futureNoOfDays);	//1.0.0.0.8
					paymentDto.setPrevNoOfDays(prevNoOfDays);	//1.0.0.0.8
					if(resultSetMap.get("DISBURSAL_ID") != null)
					{
						paymentDto.setDisbursalId(resultSetMap.get("DISBURSAL_ID").toString());
						
					}
					if(resultSetMap.get("AUTH_DATE") != null)//1.0.2
					{
						paymentDto.setDisbDate(resultSetMap.get("AUTH_DATE").toString());
						
					}
					
					if(resultSetMap.get("DISBURSALDTL_ID") != null)
					{
						paymentDto.setDisbursalDtlId(resultSetMap.get("DISBURSALDTL_ID").toString());
						
					}
					
					if(resultSetMap.get("PROSPECTID") != null)
					{
						paymentDto.setProspectId(resultSetMap.get("PROSPECTID").toString());
						
					}
					if(resultSetMap.get("PROSPECTCODE") != null)
					{
						paymentDto.setProspectCode(resultSetMap.get("PROSPECTCODE").toString());
						
					}
					if(resultSetMap.get("ENTITY_TYPE") != null)
					{
						paymentDto.setEntityType(resultSetMap.get("ENTITY_TYPE").toString());
					}
					if(resultSetMap.get("ENTITY_NAME") != null)
					{
						paymentDto.setEntityName(resultSetMap.get("ENTITY_NAME").toString());
					}
					if(resultSetMap.get("DISBURSAL_AMT") != null)
					{
						paymentDto.setDisbAmount(resultSetMap.get("DISBURSAL_AMT").toString());
					}
					if(resultSetMap.get("INSTRUMENT_TYPE") != null)
					{
						paymentDto.setInstrumentType(resultSetMap.get("INSTRUMENT_TYPE").toString());
					}
					if(resultSetMap.get("FAVOUROF") != null)
					{
						paymentDto.setFavourOf(resultSetMap.get("FAVOUROF").toString());
					}
					
					if(resultSetMap.get("INSTRUMENTNO") != null)
					{
						//Start1.0.0.7
						String instrNo = resultSetMap.get("INSTRUMENTNO").toString();
						instrNo=instrNo.replace("\\", "\\\\");
						paymentDto.setInstrumentNo(instrNo);
						//End 1.0.0.7
					}
					if(resultSetMap.get("INSTRUMENT_DATE") != null)
					{
						paymentDto.setInstrumentDate(resultSetMap.get("INSTRUMENT_DATE").toString());
					}
					if(resultSetMap.get("BANK_ID") != null)
					{
						paymentDto.setInstrumentBankId(resultSetMap.get("BANK_ID").toString());
					}
					if(resultSetMap.get("BANKID_TEMP") != null)
					{
						paymentDto.setInstrumentBankId_temp(resultSetMap.get("BANKID_TEMP").toString());
					}
					if(resultSetMap.get("BRANCH_ID") != null)
					{
						paymentDto.setInstrumentBranchId(resultSetMap.get("BRANCH_ID").toString());
					}
					if(resultSetMap.get("BRANCHID_TEMP") != null)
					{
						String branchTemp = resultSetMap.get("BRANCHID_TEMP").toString().replaceAll("\\n", " ");//1.0.0.8
						paymentDto.setInstrumentBranchId_temp(branchTemp);//1.0.0.8
					}
					
					if(resultSetMap.get("ACCOUNTNO") != null)
					{
						paymentDto.setInstrumentAccountNumber(resultSetMap.get("ACCOUNTNO").toString());
					}
					if(resultSetMap.get("ACCOUNTNO_TEMP") != null)
					{
						paymentDto.setInstrumentAccountNumber_temp(resultSetMap.get("ACCOUNTNO_TEMP").toString());
					}
					if(resultSetMap.get("IFSCCODE") != null)
					{
						paymentDto.setIfscCode(resultSetMap.get("IFSCCODE").toString());
					}
					if(resultSetMap.get("MICRCODE") != null)
					{
						paymentDto.setMicrCode(resultSetMap.get("MICRCODE").toString());
					}
					if(resultSetMap.get("BRANCH") != null)
					{
						paymentDto.setBranch(resultSetMap.get("BRANCH").toString());
					}
					if(resultSetMap.get("MAKER_REMARKS") != null)
					{
						paymentDto.setmRemarks(resultSetMap.get("MAKER_REMARKS").toString());
					}
					if(resultSetMap.get("AUTH_REMARKS") != null)
					{
						paymentDto.setaRemarks(resultSetMap.get("AUTH_REMARKS").toString());
					}
					if(resultSetMap.get("AUTH_STATUS") != null)
					{
						paymentDto.setArApprovalDecision(resultSetMap.get("AUTH_STATUS").toString());
					}
					if(resultSetMap.get("SUPPLIER_BANK") != null)
					{
						paymentDto.setSupplier_Bank(resultSetMap.get("SUPPLIER_BANK").toString());
					}
					if(resultSetMap.get("SUPPLIER_BRANCH") != null)
					{
						String branchSupplier = resultSetMap.get("SUPPLIER_BRANCH").toString().replaceAll("\\n", " ");//1.0.0.8
						paymentDto.setSupplier_Branch(branchSupplier);//1.0.0.8
					}
					if(resultSetMap.get("SUPPLIER_ACCOUNT") != null)
					{
						paymentDto.setSupplier_Account(resultSetMap.get("SUPPLIER_ACCOUNT").toString());
					}
					if(resultSetMap.get("SUPPLIER_IFSC") != null)
					{
						paymentDto.setSupplier_IFSC(resultSetMap.get("SUPPLIER_IFSC").toString());
					}
					if(resultSetMap.get("SUPPLIER_MICR") != null)
					{
						paymentDto.setSupplier_MICR(resultSetMap.get("SUPPLIER_MICR").toString());
					}
					if(resultSetMap.get("PRINTED") != null)
					{
						paymentDto.setPrintFlag(resultSetMap.get("PRINTED").toString());
					}
					if(resultSetMap.get("EVENT_NAME") != null)
					{
						paymentDto.setEventName(resultSetMap.get("EVENT_NAME").toString());
					}// 1.0.0.2 start
					if(resultSetMap.get("ADVANCE_PAYMENT_FLAG") != null)
					{
						paymentDto.setAdvancePaymentFlag(resultSetMap.get("ADVANCE_PAYMENT_FLAG").toString());
					}
					if(resultSetMap.get("ADVANCE_PAYMENT_REMARKS") != null)
					{
						paymentDto.setAdvancePaymentRemarks(resultSetMap.get("ADVANCE_PAYMENT_REMARKS").toString());
					}
					if(resultSetMap.get("TAX_INVOICE_NUMBER") != null)
					{
						paymentDto.setTaxInvoiceNo(resultSetMap.get("TAX_INVOICE_NUMBER").toString());
					}
					if(resultSetMap.get("TAX_INVOICE_DATE") != null)
					{
						paymentDto.setTaxInvoiceDate(resultSetMap.get("TAX_INVOICE_DATE").toString());
					}
					// 1.0.0.2 end
					//1.0.0.4 add start
					if(resultSetMap.get("PAYMENT_DATE") != null)
					{
						paymentDto.setPaymentDate(resultSetMap.get("PAYMENT_DATE").toString());
					}
					//1.0.0.4 add end
					//1.0.0.5 add start
					if(resultSetMap.get("PRODUCT") != null){
						paymentDto.setProduct(resultSetMap.get("PRODUCT").toString());
					}
					//1.0.0.5 add end
					
					//1.0.0.6 start
					if(resultSetMap.get("TDS_AMOUNT") != null){
						paymentDto.setTdsAmount(resultSetMap.get("TDS_AMOUNT").toString());
					}
					//1.0.0.6 end
					//1.0.0.9 add start
					if(resultSetMap.get("H2H_STATUS") != null){
						paymentDto.setH2hStatus(resultSetMap.get("H2H_STATUS").toString());
					}
					if(resultSetMap.get("POST_APPROVAL_REJECTION_SOURCE") != null){
						paymentDto.setPostApprRejSource(resultSetMap.get("POST_APPROVAL_REJECTION_SOURCE").toString());
					}
					if(resultSetMap.get("POST_APPROVAL_REJECTION_USER") != null){
						paymentDto.setPostApprRejUser(resultSetMap.get("POST_APPROVAL_REJECTION_USER").toString());
					}
					//1.0.0.9 add end
					searchData.add(paymentDto);
				}
			}
			
      }
	catch (DBConnectionException dce) 
	{
		log.error("DBConnectionException - FAIL:");
		log.error("DBConnectionException - Parameter : \r\n1. procName= " + procName + "; \r\n2. colParameter=( " + PrintParameterList.printParameterList(arrParameter) + " );");
		log.error("DBConnectionException - "+dce.getMessage());
	}
	catch (Exception e) 
	{
		log.error("Exception - FAIL:");
		log.error("Exception - Parameter : \r\n1. procName= " + procName + "; \r\n2. colParameter=( " + PrintParameterList.printParameterList(arrParameter) + " );");
		log.error("Exception - "+e.getMessage());
	}
	  log.info("End" );
	  return  searchData;
	}	
	
	//added by stuti bajpai
	
	public Collection getBankDetails(String chequeId,String chequeName, Map dbConnectionMap) throws DBConnectionException
	{
		log.info("Start");
		Map resultMap= new HashMap();
		DAL dal=new DAL();
		int parameterCount=0;
		String procName =null;
		ArrayList arrParameter=new ArrayList();
		ParameterDTO parameterDto =null;
		ParameterDTO parameterDtoStatus=null;
		ParameterDTO parameterDtoMsg=null;
		Collection returnCol = null;
		PaymentDTO paymentDto = null;
		ReturnParamDTO returnDto=new ReturnParamDTO();
		Connection connection=null;
		int check = Integer.parseInt(chequeId);
		if(dbConnectionMap.get("DB_SERVER").toString().equals("ORACLE"))
		{
			procName="{call PKG_STATIONARY_MANAGEMENT.PR_GET_BANK_DETAILS(?, ?, ?, ?)}";
			arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,chequeId));
			arrParameter.add(new ParameterDTO(++parameterCount,"OUT",oracle.jdbc.OracleTypes.CURSOR,null));
    		arrParameter.add(new ParameterDTO(++parameterCount,"OUT",oracle.jdbc.OracleTypes.VARCHAR,null));
			arrParameter.add(new ParameterDTO(++parameterCount,"OUT",oracle.jdbc.OracleTypes.VARCHAR,null));
		}
		
		try
		{
			connection=DBConnection.getConnection(dbConnectionMap);
			connection.setAutoCommit(false);
			dal.processSPROC(procName, arrParameter,dbConnectionMap,connection);
			parameterDto = new ParameterDTO();
			parameterDto = (ParameterDTO)arrParameter.get(1);
			
			ArrayList list = (ArrayList)parameterDto.getParameterValue();
			
				
			Iterator itr=null;
			if(list!=null)
			{
				itr = list.iterator();
				Map resultSetMap = null;
				while(itr.hasNext())
				{
					paymentDto=new PaymentDTO();
			  		
					resultSetMap=(Map)itr.next();
					
					if(resultSetMap.get("BRANCH_NAME") != null)
					{
						paymentDto.setInstrumentBranchId_temp(resultSetMap.get("BRANCH_NAME").toString());
					}
					if(resultSetMap.get("BRANCH_ID") != null)
					{
						paymentDto.setInstrumentBranchId(resultSetMap.get("BRANCH_ID").toString());
					}
					if(resultSetMap.get("ACCOUNT_ID") != null)
					{
						paymentDto.setInstrumentAccountNumber(resultSetMap.get("ACCOUNT_ID").toString());
					}
					if(resultSetMap.get("ACCOUNT_NO") != null)
					{
						paymentDto.setInstrumentAccountNumber_temp(resultSetMap.get("ACCOUNT_NO").toString());
					}
					if(resultSetMap.get("BANK_NAME") != null)
					{
						paymentDto.setInstrumentBankId_temp(resultSetMap.get("BANK_NAME").toString());
					}
					if(resultSetMap.get("BANK_ID") != null)
					{
						paymentDto.setInstrumentBankId(resultSetMap.get("BANK_ID").toString());
					}
					
					paymentDto.setInstrumentNo_temp(chequeName);
					
					returnCol.add(paymentDto);
				
				}
			}
			
			parameterDto = (ParameterDTO)arrParameter.get(2);
			
			
			if("S".equalsIgnoreCase((String) parameterDto.getParameterValue()))
			{
				DBConnection.commitTransaction(connection);
			}
			else
			{
				DBConnection.rollbackTransaction(connection);
				log.error("processSPROC - FAIL:");
				log.error("processSPROC - Parameter : \r\n1. procName= " + procName + "; \r\n2. colParameter=( " + PrintParameterList.printParameterList(arrParameter) + " );");
			}
			
			parameterDtoMsg=(ParameterDTO)arrParameter.get(3);
			
			
			log.info("End");
		}
		catch(Exception e)
		{
			DBConnection.rollbackTransaction(connection);
			log.error("Exception - "+e.getMessage());
		}
		finally
		{
			DBConnection.closeConnection(connection);
		}
		log.info("End");
		return returnCol;
	}
		
	
	//Start Hemant
	public  Map getVendorPaymentDtlForCSVReport(String fromDate,String toDate, Map dbConnectionMap)
	{
		log.info("Start" ); 
		ResultSet rs = null;
		Map vendorPaymentData= new HashMap();
		PaymentDTO paymentDto = null;
		ArrayList arrParameter=new ArrayList();	
		int parameterCount=0;
		DAL dal=new DAL();
		ParameterDTO parameterDto = null;
		ParameterDTO parameterDto1=null;
		ParameterDTO parameterDto2=null;	
		ParameterDTO parameterDto3=null;	
		ParameterDTO parameterDto4=null;	
		
		String procName=null;
		
		if(dbConnectionMap.get("DB_SERVER").toString().equals("ORACLE"))
		  {
			    procName="{call QC_LOS.PKG_LOS_DISBURSAL_PAYMENT.PR_GET_VENDOR_PAYMENT_DOWNLOAD_REPORT(?,?,?,?,?,?,?)}";//1.0.0.8
			    arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,fromDate));
			 	arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,toDate));
			    arrParameter.add(new ParameterDTO(++parameterCount,"OUT",oracle.jdbc.OracleTypes.VARCHAR,null));//PC_VENDOR_PAYMENT_NAME_HDR
			    arrParameter.add(new ParameterDTO(++parameterCount,"OUT",oracle.jdbc.OracleTypes.VARCHAR,null));//PC_VENDOR_PAYMENT_HDR
			    arrParameter.add(new ParameterDTO(++parameterCount,"OUT",oracle.jdbc.OracleTypes.CURSOR,null));//PC_VENDOR_PAYMENT_DATA
			 	arrParameter.add(new ParameterDTO(++parameterCount,"OUT",oracle.jdbc.OracleTypes.VARCHAR,null));//PC_STATUS
			 	arrParameter.add(new ParameterDTO(++parameterCount,"OUT",oracle.jdbc.OracleTypes.VARCHAR,null));//PC_MESSAGE

		  }
		 
		try
		{
			dal.processSPROC(procName, arrParameter,dbConnectionMap);
			parameterDto = (ParameterDTO)arrParameter.get(2);
			parameterDto1 = (ParameterDTO)arrParameter.get(3);
			parameterDto2 = (ParameterDTO)arrParameter.get(4);
			parameterDto3 = (ParameterDTO)arrParameter.get(5);
			parameterDto4 = (ParameterDTO)arrParameter.get(6);
			
			vendorPaymentData.put("PC_VENDOR_PAYMENT_NAME_HDR",parameterDto.getParameterValue());
			vendorPaymentData.put("PC_VENDOR_PAYMENT_HDR",parameterDto1.getParameterValue());
			vendorPaymentData.put("PC_VENDOR_PAYMENT_DATA",parameterDto2.getParameterValue());
			vendorPaymentData.put("PC_STATUS",parameterDto3.getParameterValue());
			vendorPaymentData.put("PC_MESSAGE",parameterDto4.getParameterValue());
			
      }
	catch (DBConnectionException dce) 
	{
		log.error("DBConnectionException - FAIL:");
		log.error("DBConnectionException - Parameter : \r\n1. procName= " + procName + "; \r\n2. colParameter=( " + PrintParameterList.printParameterList(arrParameter) + " );");
		log.error("DBConnectionException - "+dce.getMessage());
	}
	catch (Exception e) 
	{
		log.error("Exception - FAIL:");
		log.error("Exception - Parameter : \r\n1. procName= " + procName + "; \r\n2. colParameter=( " + PrintParameterList.printParameterList(arrParameter) + " );");
		log.error("Exception - "+e.getMessage());
	}
	  log.info("End" );
	  return  vendorPaymentData;
	}
	//End Hemant
}
	
