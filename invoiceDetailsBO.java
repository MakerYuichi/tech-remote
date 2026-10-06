 /* HEADER INFO
+  File NAME 	: invoiceDetailsBO.java
+  PURPOSE		: 
+  CREATED BY	: 
+  CREATION DATE	: 
+  INITIAL VERSION : 1.0.0.0
+  **********************************************************************************************************************************
+  VERSION NO   UPDATED BY       	UPDATED ON      REASON FOR CHANGE
+  1.0.0.1		Apurva Shukla 		16-AUG-2018	 	Consolidated invoice cr
+  1.0.0.2	    Apurva Shukla  	    01 oct 2018     changes made for consolidated invoice(new consolidated invoice cr)
+  1.0.0.3	    Apurva Shukla  	    15 oct 2018     Changes for invoice CR cancel single invoice and some other changes
   1.0.0.4		Nishant Bansal		31-Oct-2018		Reconcilation Report
   1.0.0.5		Sunny Pathak		08-Jan-2020		CR:- Batch Error Report
   1.0.0.6      Ravi Kumar          04-Sep-2020     changes to add evoicing  details
   1.0.0.7      Viplou Dhali        07-Jul-2022     VAlidation on DueType
   1.0.0.8      Priyanka Soni       25-May-2023     Bug 121384: INC-78551: If invoice registration is failed then some of the invoices are not getting processed for re-attempt AND Invoices getting stuck at registration started status
   1.0.0.9      Narottam Biswal     08-Jan-2025     CR: Auto emailing of invoices
   1.0.0.10     Sanchi Agarwal      05-Oct-2026     Vehicle RC Authentication - getPendingVehicleRCList, processVehicleRCRecord, saveVehicleRCApiResponse (branch 6793)
   **********************************************************************************************************************************
 */ 

package qc.quotation.model.bo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import qc.common.bean.CommonBean;
import qc.common.util.ServiceCallAction;
import qc.dal.DAL;
import qc.dal.dto.ParameterDTO;
import qc.dal.exception.DBConnectionException;
import qc.dal.util.DBConnection;
import qc.dal.util.PrintParameterList;
import qc.grf.model.bo.GRFBO;
import qc.llm.dto.LLMSessionInformationDTO;
import qc.llm.util.LLMConstant;
import qc.quotation.controller.form.QuotationSearchForm;
import qc.sso.dto.UserInfoDTO;
import qc.webService.util.Base64;

@Component
public class InvoiceDetailsBO {

	protected static Logger log = Logger.getLogger(InvoiceDetailsBO.class);
	Locale locale = new Locale("en","US");
	ResourceBundle resource = ResourceBundle.getBundle("resourceProperties.losConstants", locale);
	
	public Map getInvoiceList(String  invoiceSearchStrr,String userId,String invoiceType,String taxableFlg,String activityName,Map dbConnectionMap)// 1.0.0.1
	{
		HashMap returnMap=null;
		DAL dal=new DAL();
    	int parameterCount=0;
		ArrayList arrParameter=new ArrayList();
		String procName=null;
		ParameterDTO parameterDTO = new ParameterDTO();
		
		String[] invoiceSearchStr=invoiceSearchStrr.split("~");
		
		
		if(dbConnectionMap.get("DB_SERVER").toString().equals("ORACLE"))
		  {
			    procName="{call QC_LMS.PKG_INVOICE.PR_SEARCH_INVOICES(?,?,?,?,?, ?,?,?,?,? ,?,?,?,?,?,?,?,?,?,?,? ,?,?,?,?,?  ,?,?,?,?,? ,?,?,?)}";// 1.0.0.1 // 1.0.0.2 //1.0.0.9
			 	arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,invoiceSearchStr[0])); 
			 	arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,invoiceSearchStr[3])); 
			 	arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,invoiceSearchStr[4])); 
			 	arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,invoiceSearchStr[1])); 
			 	arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,invoiceSearchStr[5])); 
			 	
			 	arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,invoiceSearchStr[7])); 
			 	arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,invoiceSearchStr[8])); 
			 	arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,invoiceSearchStr[6])); 
			 	arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,invoiceSearchStr[2])); 
			 	arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,invoiceSearchStr[9])); 			 	
			 	
			 	arrParameter.add(new ParameterDTO(++parameterCount,"IN OUT",oracle.jdbc.OracleTypes.VARCHAR,invoiceSearchStr[11]));
			 	
			 	arrParameter.add(new ParameterDTO(++parameterCount,"IN OUT",oracle.jdbc.OracleTypes.VARCHAR,invoiceSearchStr[10])); //1.0.0.7
			 	arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,userId));
			 	
			 	arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,invoiceSearchStr[12]));
			 	arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,invoiceSearchStr[13]));
			 	
			 	arrParameter.add(new ParameterDTO(++parameterCount,"OUT",oracle.jdbc.OracleTypes.VARCHAR,null)); 
			 	arrParameter.add(new ParameterDTO(++parameterCount,"OUT",oracle.jdbc.OracleTypes.VARCHAR,null)); 
			 	arrParameter.add(new ParameterDTO(++parameterCount,"OUT",oracle.jdbc.OracleTypes.CURSOR,null)); 
			 	arrParameter.add(new ParameterDTO(++parameterCount,"OUT",oracle.jdbc.OracleTypes.CURSOR,null));
			 	arrParameter.add(new ParameterDTO(++parameterCount,"OUT",oracle.jdbc.OracleTypes.VARCHAR,null)); 			 	
			 	arrParameter.add(new ParameterDTO(++parameterCount,"OUT",oracle.jdbc.OracleTypes.VARCHAR,null)); 
			 	// 1.0.0.1 start
			 	arrParameter.add(new ParameterDTO(++parameterCount,"IN OUT",oracle.jdbc.OracleTypes.VARCHAR,invoiceType));
			 	arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,taxableFlg));
			 	
			 	arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,invoiceSearchStr[15]));
			 	arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,invoiceSearchStr[16]));
			 	arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,invoiceSearchStr[14]));
			 	// 1.0.0.2
			 	arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,invoiceSearchStr[17]));
			 	arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,invoiceSearchStr[18]));
                               // 1.0.0.2 end
                       // 1.0.0.1 end
			 	
				arrParameter.add(new ParameterDTO(++parameterCount,"IN OUT",oracle.jdbc.OracleTypes.VARCHAR,invoiceSearchStr[19]));
				arrParameter.add(new ParameterDTO(++parameterCount,"OUT",oracle.jdbc.OracleTypes.CLOB,null));   // changes to add default parameter
				arrParameter.add(new ParameterDTO(++parameterCount,"OUT",oracle.jdbc.OracleTypes.VARCHAR,null)); 			 //1.0.0.6 	
			 	arrParameter.add(new ParameterDTO(++parameterCount,"OUT",oracle.jdbc.OracleTypes.VARCHAR,null)); //1.0.0.6
			 	arrParameter.add(new ParameterDTO(++parameterCount,"OUT",oracle.jdbc.OracleTypes.VARCHAR,null)); //1.0.0.9
			 	arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,activityName));//sunny
		  }
		try
		{
			dal.processSPROC(procName, arrParameter,dbConnectionMap);
			ArrayList list=null;
			ArrayList list1=null;
			String status="";
			String totalNoOfPagesForInvoice="";
			String totalNoOfPagesForCoverNote="";
			returnMap=new HashMap();
						
			parameterDTO = (ParameterDTO)arrParameter.get(19);			
			status=(String)parameterDTO.getParameterValue();
			parameterDTO = (ParameterDTO)arrParameter.get(20);			
			String message=(String)parameterDTO.getParameterValue();
			
			returnMap.put("PC_OUT_STATUS", status);		
			returnMap.put("PC_OUT_MESSAGE", message);
			if(status.equalsIgnoreCase("S"))
			{
				parameterDTO = (ParameterDTO)arrParameter.get(15);			
				totalNoOfPagesForInvoice=(String)parameterDTO.getParameterValue();
				
				parameterDTO = (ParameterDTO)arrParameter.get(16);			
				totalNoOfPagesForCoverNote=(String)parameterDTO.getParameterValue();
				
				parameterDTO = (ParameterDTO)arrParameter.get(17);			
				list=(ArrayList)parameterDTO.getParameterValue();
				returnMap.put("INVOICE_LIST", list);
				
				parameterDTO = (ParameterDTO)arrParameter.get(18);			
				list=(ArrayList)parameterDTO.getParameterValue();
				returnMap.put("CONER_NOTE_LIST", list);
				
				
				parameterDTO = (ParameterDTO)arrParameter.get(10);			
				String batchId=(String)parameterDTO.getParameterValue();
				
				parameterDTO = (ParameterDTO)arrParameter.get(11);			//10.0.0.7
				String dueType=(String)parameterDTO.getParameterValue();	//10.0.0.7
				
				parameterDTO = (ParameterDTO)arrParameter.get(21);			
				String invoiceBatchType=(String)parameterDTO.getParameterValue();
				

				parameterDTO = (ParameterDTO)arrParameter.get(28);			
				String modeOfOperation=(String)parameterDTO.getParameterValue();
				//1.0.0.6 start
				parameterDTO = (ParameterDTO)arrParameter.get(30);			
				String enableGenerateEInvoiceBtn=(String)parameterDTO.getParameterValue();
				
				parameterDTO = (ParameterDTO)arrParameter.get(31);			
				String eInvoiceProgressStatus=(String)parameterDTO.getParameterValue();
				// 1.0.0.6 end
				//start 1.0.0.9
				parameterDTO = (ParameterDTO)arrParameter.get(32);
				String autoEmailFlagValue=(String)parameterDTO.getParameterValue();
				//end 1.0.0.9
				
				returnMap.put("totalNoOfPages", totalNoOfPagesForInvoice);
				returnMap.put("totalNoOfPagesForCoverNote", totalNoOfPagesForCoverNote);
				returnMap.put("BATCH_ID", batchId);
				returnMap.put("DUE_TYPE", dueType);    //10.0.0.7
				returnMap.put("INVOICE_TYPE", invoiceBatchType);// 1.0.0.1
				
				returnMap.put("MODE_OF_OPERATION", modeOfOperation);
				returnMap.put("ENABLE_E_INVOICE_GENERATE_BTN", enableGenerateEInvoiceBtn); //1.0.0.6
				returnMap.put("E_INVOICE_PROGRESS_STATUS", eInvoiceProgressStatus); //1.0.0.6
				returnMap.put("autoEmailFlagValue", autoEmailFlagValue);  //1.0.0.9
				
			}
			
	
		}
		catch (DBConnectionException dce) 
		{
			log.error("DBConnectionException - "+dce.getMessage());
		}
		catch (Exception e) 
		{
			log.error("Exception - "+e.getMessage());
		}
		log.info("End");
			
		return returnMap;
	}
	
	
	public Map addToBatch(String  batchStrr,String checkedString,String invoiceType,String userId,String modeOfOperation,String withCoverNoteFlg,String withHeaderFlg,String temp_invoiceSearchStr,String autoEmailFlag,Map dbConnectionMap)
	{
		HashMap returnMap=null;
		DAL dal=new DAL();
    	int parameterCount=0;
		ArrayList arrParameter=new ArrayList();
		String procName=null;
		ParameterDTO parameterDTO = new ParameterDTO();
		
		String[] batchStr=batchStrr.split("~");
		
		
		if(dbConnectionMap.get("DB_SERVER").toString().equals("ORACLE"))
		  {
			    procName="{call QC_LMS.PKG_INVOICE.PR_SAVE_INVOICE_BATCH(?,?,?,?,?, ?,?,? ,?,?,?,?,?, ?,?,?,?,?,?,?)}";// 1.0.0.1 //1.0.0.9
			 	arrParameter.add(new ParameterDTO(++parameterCount,"IN OUT",oracle.jdbc.OracleTypes.VARCHAR,batchStr[0])); 
			 	arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,batchStrr));
			 	arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,checkedString));
			 	
			 	arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,"^")); 
			 	arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,"~")); 
			 	arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,userId));
			 	
			 	arrParameter.add(new ParameterDTO(++parameterCount,"OUT",oracle.jdbc.OracleTypes.VARCHAR,null)); 
			 	arrParameter.add(new ParameterDTO(++parameterCount,"OUT",oracle.jdbc.OracleTypes.VARCHAR,null)); 
			 	arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,invoiceType));// 1.0.0.1
			 	
			 	arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,modeOfOperation));
			 	arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,withCoverNoteFlg));
			 	arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,withHeaderFlg));
			 	
			 	arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.CLOB,temp_invoiceSearchStr));
			 	//Start 1.0.0.9
			 	arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,null));
			 	arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,null));
			 	arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,null));
			 	arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,null));
			 	arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,null));
			 	arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,autoEmailFlag));
			 	arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,null));
			 	//End 1.0.0.9
			 	


		  }
		try
		{
			dal.processSPROC(procName, arrParameter,dbConnectionMap);
			ArrayList list=null;
			String status="";
			String totalNoOfPages="";
			returnMap=new HashMap();
						
			parameterDTO = (ParameterDTO)arrParameter.get(6);			
			status=(String)parameterDTO.getParameterValue();
							
				
				parameterDTO = (ParameterDTO)arrParameter.get(7);			
				String message=(String)parameterDTO.getParameterValue();
				
				parameterDTO = (ParameterDTO)arrParameter.get(0);
				String batchId=(String)parameterDTO.getParameterValue();			
				returnMap.put("PC_OUT_STATUS", status);		
				returnMap.put("PC_OUT_MESSAGE", message);
				returnMap.put("BATCH_ID", batchId);
				
				
				
				
		
			
	
		}
		catch (DBConnectionException dce) 
		{
			log.error("DBConnectionException - "+dce.getMessage());
		}
		catch (Exception e) 
		{
			log.error("Exception - "+e.getMessage());
		}
		log.info("End");
			
		return returnMap;
	}
	
	
	
	
	
	
	
	
	public Map getBatchSummary(String  batchId,String userId,Map dbConnectionMap)
	{
		HashMap returnMap=null;
		DAL dal=new DAL();
    	int parameterCount=0;
		ArrayList arrParameter=new ArrayList();
		String procName=null;
		ParameterDTO parameterDTO = new ParameterDTO();
		
		
		
		
		if(dbConnectionMap.get("DB_SERVER").toString().equals("ORACLE"))
		  {
			    procName="{call QC_LMS.PKG_INVOICE.PR_GET_BATCH_SUMMARY(?,?,?,?,?,?)}";
			 	arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,batchId)); 
			 	arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,userId)); 
			 	
			 	arrParameter.add(new ParameterDTO(++parameterCount,"OUT",oracle.jdbc.OracleTypes.VARCHAR,null)); 
			 	arrParameter.add(new ParameterDTO(++parameterCount,"OUT",oracle.jdbc.OracleTypes.CURSOR,null));		 	
			 	arrParameter.add(new ParameterDTO(++parameterCount,"OUT",oracle.jdbc.OracleTypes.VARCHAR,null)); 
			 	arrParameter.add(new ParameterDTO(++parameterCount,"OUT",oracle.jdbc.OracleTypes.VARCHAR,null)); 			 	


		  }
		try
		{
			dal.processSPROC(procName, arrParameter,dbConnectionMap);
			ArrayList list=null;
			String status="";
			String totalNoOfPages="";
			returnMap=new HashMap();
						
			parameterDTO = (ParameterDTO)arrParameter.get(4);			
			status=(String)parameterDTO.getParameterValue();
			parameterDTO = (ParameterDTO)arrParameter.get(5);			
			String message=(String)parameterDTO.getParameterValue();
			returnMap.put("PC_OUT_STATUS", status);		
			returnMap.put("PC_OUT_MESSAGE", message);
			if(status.equalsIgnoreCase("S"))
			{
				
				
				
				parameterDTO = (ParameterDTO)arrParameter.get(3);			
				list=(ArrayList)parameterDTO.getParameterValue();
				
				parameterDTO = (ParameterDTO)arrParameter.get(2);
				String batchNo=(String)parameterDTO.getParameterValue();
				
							
				
				returnMap.put("BATCH_LIST", list);
				returnMap.put("BATCH_NO", batchNo);
				
				
				
			}
			
	
		}
		catch (DBConnectionException dce) 
		{
			log.error("DBConnectionException - "+dce.getMessage());
		}
		catch (Exception e) 
		{
			log.error("Exception - "+e.getMessage());
		}
		log.info("End");
			
		return returnMap;
	}
	
	
	
	public Map generateCoverNote(String  batchId,String userId,String invoiceType,Map dbConnectionMap)
	{
		HashMap returnMap=null;
		DAL dal=new DAL();
    	int parameterCount=0;
		ArrayList arrParameter=new ArrayList();
		String procName=null;
		ParameterDTO parameterDTO = new ParameterDTO();
		
		
		
		
		if(dbConnectionMap.get("DB_SERVER").toString().equals("ORACLE"))
		  {
			    procName="{call QC_LMS.PKG_INVOICE.PR_START_BATCH_PROCESSING(?,?,?,? ,?)}";// 1.0.0.1
			 	arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,batchId)); 
			 	arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,userId)); 
			 	
			 	arrParameter.add(new ParameterDTO(++parameterCount,"OUT",oracle.jdbc.OracleTypes.VARCHAR,null)); 
			 	arrParameter.add(new ParameterDTO(++parameterCount,"OUT",oracle.jdbc.OracleTypes.VARCHAR,null)); 
			 	
			 	arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,invoiceType)); // 1.0.0.1


		  }
		try
		{
			dal.processSPROC(procName, arrParameter,dbConnectionMap);
			ArrayList list=null;
			String status="";
			String totalNoOfPages="";
			returnMap=new HashMap();
						
			parameterDTO = (ParameterDTO)arrParameter.get(2);			
			status=(String)parameterDTO.getParameterValue();
			parameterDTO = (ParameterDTO)arrParameter.get(3);			
			String message=(String)parameterDTO.getParameterValue();
			returnMap.put("PC_OUT_STATUS", status);		
			returnMap.put("PC_OUT_MESSAGE", message);
			
			
	
		}
		catch (DBConnectionException dce) 
		{
			log.error("DBConnectionException - "+dce.getMessage());
		}
		catch (Exception e) 
		{
			log.error("Exception - "+e.getMessage());
		}
		log.info("End");
			
		return returnMap;
	}
	
	

	public Map cancelBatch(String  batchId,String userId,Map dbConnectionMap)
	{
		HashMap returnMap=null;
		DAL dal=new DAL();
    	int parameterCount=0;
		ArrayList arrParameter=new ArrayList();
		String procName=null;
		ParameterDTO parameterDTO = new ParameterDTO();
		
		
		
		
		if(dbConnectionMap.get("DB_SERVER").toString().equals("ORACLE"))
		  {
			    procName="{call QC_LMS.PKG_INVOICE.PR_CANCEL_INVOICE_BATCH(?,?,?,?)}";
			 	arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,batchId)); 
			 	arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,userId));
			 	
			 	arrParameter.add(new ParameterDTO(++parameterCount,"OUT",oracle.jdbc.OracleTypes.VARCHAR,null)); 
			 	arrParameter.add(new ParameterDTO(++parameterCount,"OUT",oracle.jdbc.OracleTypes.VARCHAR,null)); 			 	


		  }
		try
		{
			dal.processSPROC(procName, arrParameter,dbConnectionMap);
			ArrayList list=null;
			String status="";
			String totalNoOfPages="";
			returnMap=new HashMap();
						
			parameterDTO = (ParameterDTO)arrParameter.get(2);			
			status=(String)parameterDTO.getParameterValue();
			parameterDTO = (ParameterDTO)arrParameter.get(3);			
			String message=(String)parameterDTO.getParameterValue();
						
			returnMap.put("PC_OUT_STATUS", status);		
			returnMap.put("PC_OUT_MESSAGE", message);
			
	
		}
		catch (DBConnectionException dce) 
		{
			log.error("DBConnectionException - "+dce.getMessage());
		}
		catch (Exception e) 
		{
			log.error("Exception - "+e.getMessage());
		}
		log.info("End");
			
		return returnMap;
	}
	
	
	public Map currentBatchGeneration(String  batchId,String userId,Map dbConnectionMap)
	{
		HashMap returnMap=null;
		DAL dal=new DAL();
    	int parameterCount=0;
		ArrayList arrParameter=new ArrayList();
		String procName=null;
		ParameterDTO parameterDTO = new ParameterDTO();
		
		
		
		
		if(dbConnectionMap.get("DB_SERVER").toString().equals("ORACLE"))
		  {
			    procName="{call QC_LMS.PKG_INVOICE.PR_GET_CURRENTBATCH_COVERNOTES(?,?,?,?,?,?)}";
			 	arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,batchId)); 
			 	arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,userId));
			 	arrParameter.add(new ParameterDTO(++parameterCount,"OUT",oracle.jdbc.OracleTypes.CURSOR,null));
			 	arrParameter.add(new ParameterDTO(++parameterCount,"OUT",oracle.jdbc.OracleTypes.VARCHAR,null)); 	
			 	arrParameter.add(new ParameterDTO(++parameterCount,"OUT",oracle.jdbc.OracleTypes.VARCHAR,null)); 
			 	arrParameter.add(new ParameterDTO(++parameterCount,"OUT",oracle.jdbc.OracleTypes.VARCHAR,null)); 			 	


		  }
		try
		{
			dal.processSPROC(procName, arrParameter,dbConnectionMap);
			ArrayList list=null;
			String status="";
			String totalNoOfPages="";
			returnMap=new HashMap();
						
			parameterDTO = (ParameterDTO)arrParameter.get(4);			
			status=(String)parameterDTO.getParameterValue();
			parameterDTO = (ParameterDTO)arrParameter.get(5);			
			String message=(String)parameterDTO.getParameterValue();
			returnMap.put("PC_OUT_STATUS", status);		
			returnMap.put("PC_OUT_MESSAGE", message);
			if(status.equalsIgnoreCase("S"))
			{
				
				
				
				parameterDTO = (ParameterDTO)arrParameter.get(3);			
				String batchCompletedFlag=(String)parameterDTO.getParameterValue();
				

				parameterDTO = (ParameterDTO)arrParameter.get(2);			
				list=(ArrayList)parameterDTO.getParameterValue();
							
				
				returnMap.put("CURRENT_COVER_NOTE_LIST", list);
				returnMap.put("BATCH_COMPLETED_FLAG", batchCompletedFlag);
				
			}
			
	
		}
		catch (DBConnectionException dce) 
		{
			log.error("DBConnectionException - "+dce.getMessage());
		}
		catch (Exception e) 
		{
			log.error("Exception - "+e.getMessage());
		}
		log.info("End");
			
		return returnMap;
	}
	
	public Map updateCoverNotes(String  coverNoteStr,String coverNoteIdStr,String userId,Map dbConnectionMap)
	{
		HashMap returnMap=null;
		DAL dal=new DAL();
    	int parameterCount=0;
		ArrayList arrParameter=new ArrayList();
		String procName=null;
		ParameterDTO parameterDTO = new ParameterDTO();
		
		
		
		
		if(dbConnectionMap.get("DB_SERVER").toString().equals("ORACLE"))
		  {
			    procName="{call QC_LMS.PKG_INVOICE.PR_SAVE_COVERNOTE_DTLS(?,?,?,?,?,?,?)}";
			 	arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,coverNoteStr)); 
			 	arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,coverNoteIdStr)); 
			 	arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,"^")); 
			 	arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,"~"));
			 	arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,userId));
			 	arrParameter.add(new ParameterDTO(++parameterCount,"OUT",oracle.jdbc.OracleTypes.VARCHAR,null)); 
			 	arrParameter.add(new ParameterDTO(++parameterCount,"OUT",oracle.jdbc.OracleTypes.VARCHAR,null)); 			 	


		  }
		try
		{
			dal.processSPROC(procName, arrParameter,dbConnectionMap);
			ArrayList list=null;
			String status="";
			String totalNoOfPages="";
			returnMap=new HashMap();
						
			parameterDTO = (ParameterDTO)arrParameter.get(5);			
			status=(String)parameterDTO.getParameterValue();
			//if(status.equalsIgnoreCase("S"))
			//{
				
				parameterDTO = (ParameterDTO)arrParameter.get(6);			
				String message=(String)parameterDTO.getParameterValue();
							
				returnMap.put("PC_OUT_STATUS", status);		
				returnMap.put("PC_OUT_MESSAGE", message);
				
				
			//}
			
	
		}
		catch (DBConnectionException dce) 
		{
			log.error("DBConnectionException - "+dce.getMessage());
		}
		catch (Exception e) 
		{
			log.error("Exception - "+e.getMessage());
		}
		log.info("End");
			
		return returnMap;
	}
	
	
	
	public Collection getBatchList(String KeyName,String batchCreatedBy,String formDate,String toDate,String userId,String batchType,String modeOfOperation,Map dbConnectionMap)
	{
		HashMap returnMap=null;
		DAL dal=new DAL();
    	int parameterCount=0;
    	Collection list=null;
		ArrayList arrParameter=new ArrayList();
		String procName=null;
		ParameterDTO parameterDto = null;
		
		
		
		if(dbConnectionMap.get("DB_SERVER").toString().equals("ORACLE"))
		  {
			    procName="{call QC_LMS.PKG_INVOICE.PR_GET_BATCH_NO_LIST(?,?,?,?,?,?,?)}";
			 	arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,batchCreatedBy)); 
			 	arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,formDate)); 
			 	arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,toDate)); 
			 	arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,userId)); 
			 				 	
			 	arrParameter.add(new ParameterDTO(++parameterCount,"OUT",oracle.jdbc.OracleTypes.CURSOR,null)); 
			 	arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,batchType)); 
			 	arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,modeOfOperation)); 



		  }
		try
		{

			dal.processSPROC(procName, arrParameter,dbConnectionMap);
			parameterDto = (ParameterDTO)arrParameter.get(4);
			
			list=(ArrayList)parameterDto.getParameterValue();
			
				
			}
			
		catch (Exception e) 
		{
			log.error("Exception - "+e.getMessage());
		}
		log.info("End");
			
		return list;
	}
	// 1.0.0.1 start
	
	public Map deleteCoverNotes(String  batchStrr,String batchType,String userId,Map dbConnectionMap)
	{
		HashMap returnMap=null;
		DAL dal=new DAL();
    	int parameterCount=0;
		ArrayList arrParameter=new ArrayList();
		String procName=null;
		ParameterDTO parameterDTO = new ParameterDTO();
		
		String[] batchStr=batchStrr.split("~");
		
		
		if(dbConnectionMap.get("DB_SERVER").toString().equals("ORACLE"))
		  {
			    procName="{call QC_LMS.PKG_INVOICE.PR_CANCEL_CONSOLIDATED_INVOICE(?,?,?,?,?)}";
			    arrParameter.add(new ParameterDTO(++parameterCount,"IN OUT",oracle.jdbc.OracleTypes.VARCHAR,batchType));
			 	arrParameter.add(new ParameterDTO(++parameterCount,"IN OUT",oracle.jdbc.OracleTypes.VARCHAR,batchStrr)); 
			 	//arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,",")); 
			 	arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,userId)); 
			 	arrParameter.add(new ParameterDTO(++parameterCount,"OUT",oracle.jdbc.OracleTypes.VARCHAR,null)); 
			 	arrParameter.add(new ParameterDTO(++parameterCount,"OUT",oracle.jdbc.OracleTypes.VARCHAR,null)); 
			 	
			

		  }
		try
		{
			dal.processSPROC(procName, arrParameter,dbConnectionMap);
			ArrayList list=null;
			String status="";
			String totalNoOfPages="";
			returnMap=new HashMap();
						
			parameterDTO = (ParameterDTO)arrParameter.get(3);			
			status=(String)parameterDTO.getParameterValue();
			//if(status.equalsIgnoreCase("S"))
			//{
				
				
				parameterDTO = (ParameterDTO)arrParameter.get(4);			
				String message=(String)parameterDTO.getParameterValue();
				
				returnMap.put("PC_OUT_STATUS", status);		
				returnMap.put("PC_OUT_MESSAGE", message);
			
			//}
			
	
		}
		catch (DBConnectionException dce) 
		{
			log.error("DBConnectionException - "+dce.getMessage());
		}
		catch (Exception e) 
		{
			log.error("Exception - "+e.getMessage());
		}
		log.info("End");
			
		return returnMap;
	}
	
	// 1.0.0.1 End
	
	
	// 1.0.0.3 start
	public Map cancelSingleInvoice(String  cancelSingleInvoiceStr,String userId,Map dbConnectionMap)
	{
		HashMap returnMap=null;
		DAL dal=new DAL();
    	int parameterCount=0;
		ArrayList arrParameter=new ArrayList();
		String procName=null;
		ParameterDTO parameterDTO = new ParameterDTO();
		
		String[] batchStr=cancelSingleInvoiceStr.split("~");
		
		
		if(dbConnectionMap.get("DB_SERVER").toString().equals("ORACLE"))
		  {
			    procName="{call QC_LMS.PKG_INVOICE.PR_CANCEL_SINGLE_INVOICES(?,?,?,?)}";
			 	arrParameter.add(new ParameterDTO(++parameterCount,"IN OUT",oracle.jdbc.OracleTypes.VARCHAR,cancelSingleInvoiceStr)); 
			 	//arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,",")); 
			 	arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,userId)); 
			 	arrParameter.add(new ParameterDTO(++parameterCount,"OUT",oracle.jdbc.OracleTypes.VARCHAR,null)); 
			 	arrParameter.add(new ParameterDTO(++parameterCount,"OUT",oracle.jdbc.OracleTypes.VARCHAR,null)); 
			 	
			

		  }
		try
		{
			dal.processSPROC(procName, arrParameter,dbConnectionMap);
			ArrayList list=null;
			String status="";
			String totalNoOfPages="";
			returnMap=new HashMap();
						
			parameterDTO = (ParameterDTO)arrParameter.get(2);			
			status=(String)parameterDTO.getParameterValue();
			
			parameterDTO = (ParameterDTO)arrParameter.get(3);			
			String message=(String)parameterDTO.getParameterValue();
			
			returnMap.put("PC_OUT_STATUS", status);		
			returnMap.put("PC_OUT_MESSAGE", message);
	
		}
		catch (DBConnectionException dce) 
		{
			log.error("DBConnectionException - "+dce.getMessage());
		}
		catch (Exception e) 
		{
			log.error("Exception - "+e.getMessage());
		}
		log.info("End");
			
		return returnMap;
	}
	
	
	
	// 1.0.0.3 end
	
	/////----------- Bulk Printing Changes(Scheduler)
	
	public ArrayList listOfprintFiles(String fileId,String flag,Map dbConnectionMap)
	{
		log.debug("invoiceDetailsBO | listOfPrintFiles() | start");
		HashMap returnMap=null;
		DAL dal=new DAL();
    	int parameterCount=0;
		ArrayList arrParameter=new ArrayList();
		String procName=null;
		ParameterDTO parameterDTO = new ParameterDTO();
		ArrayList list=null;
		
		if(dbConnectionMap.get("DB_SERVER").toString().equals("ORACLE"))
		  {
			    procName="{call QC_LMS.PKG_INVOICE.PR_GET_FILES_TO_BE_PRINTED(?,?,?)}";
			 	arrParameter.add(new ParameterDTO(++parameterCount,"OUT",oracle.jdbc.OracleTypes.CURSOR,null)); 
			 	arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,fileId));
			 	arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,flag));

		  }
		try
		{
			dal.processSPROC(procName, arrParameter,dbConnectionMap);
			
			String status="";
			String totalNoOfPages="";
			returnMap=new HashMap();
			
			parameterDTO = (ParameterDTO)arrParameter.get(0);			
			list=(ArrayList)parameterDTO.getParameterValue();
			
			
			
			
			
			returnMap.put("PRINT_FILE_LIST", list);
		
	
		}
		catch (DBConnectionException dce) 
		{
			log.error("DBConnectionException - "+dce.getMessage());
		}
		catch (Exception e) 
		{
			log.error("Exception - "+e.getMessage());
		}
		//log.info("End");
		log.debug("invoiceDetailsBO | listOfPrintFiles() | end");
		return list;
	}
	
	
	
	public Map updatePrintingStatus(String  fileId,String printStatus,String errorMsg,Map dbConnectionMap)
	{
		HashMap returnMap=null;
		DAL dal=new DAL();
    	int parameterCount=0;
		ArrayList arrParameter=new ArrayList();
		String procName=null;
		ParameterDTO parameterDTO = new ParameterDTO();
		
		
		
		
		if(dbConnectionMap.get("DB_SERVER").toString().equals("ORACLE"))
		  {
			    procName="{call QC_LMS.PKG_INVOICE.PR_UPDATE_PRINTING_STATUS(?,?,?)}";
			 	arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,fileId)); 
			 	arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,printStatus));
			 	arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,errorMsg));
		  }
		try
		{
		dal.processSPROC(procName, arrParameter,dbConnectionMap);
		}catch(Exception e){
			log.error("Exception - "+e.getMessage());
		}
		log.info("End");
			
		return returnMap;
	}
	
	public String getTimeStamp(Map dbConnectionMap)
	{
		String timeStamp=null;
		DAL dal=new DAL();
    	int parameterCount=0;
		ArrayList arrParameter=new ArrayList();
		String procName=null;
		ParameterDTO parameterDTO = new ParameterDTO();
		
		
		
		
		if(dbConnectionMap.get("DB_SERVER").toString().equals("ORACLE"))
		  {
			    procName="{call QC_LMS.PKG_INVOICE.PR_GET_TIMESTAMP_DEL_FILES(?)}";
			    arrParameter.add(new ParameterDTO(++parameterCount,"OUT",oracle.jdbc.OracleTypes.VARCHAR,null)); 
		  }
			
		try
		{
		dal.processSPROC(procName, arrParameter,dbConnectionMap);
		
		parameterDTO = (ParameterDTO)arrParameter.get(0);			
		timeStamp=(String)parameterDTO.getParameterValue();
		}catch(Exception e){
			log.error("Exception - "+e.getMessage());
		}
		log.info("End");
			
		return timeStamp;
	}
	
	
	public void updateDeleteStatus(String statusString,Map dbConnectionMap)
	{
		String timeStamp=null;
		DAL dal=new DAL();
    	int parameterCount=0;
		ArrayList arrParameter=new ArrayList();
		String procName=null;
		ParameterDTO parameterDTO = new ParameterDTO();
		
		
		
		
		if(dbConnectionMap.get("DB_SERVER").toString().equals("ORACLE"))
		  {
			    procName="{call QC_LMS.PKG_INVOICE.PR_UPDATE_DELETION_STATUS(?)}";
			    arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,statusString)); 
		  }
			
		try
		{
		dal.processSPROC(procName, arrParameter,dbConnectionMap);
		}catch(Exception e){
			log.error("Exception - "+e.getMessage());
		}	
		
	}
	
	//1.0.0.4 start
	public HashMap getReconReportDetails(String batchId, Map dbConnectionMap)
	{
		HashMap returnMap=null;
		DAL dal=new DAL();
		int parameterCount=0;
		ArrayList arrParameter=new ArrayList();
		String procName=null;
		ParameterDTO parameterDTO = new ParameterDTO();
		
		if(dbConnectionMap.get("DB_SERVER").toString().equals("ORACLE"))
		  {
			    procName="{call QC_LMS.PKG_INVOICE.PR_DOWNLOAD_PRINT_RECON_REPORT(?,?,?,?,?, ?,?,?,?)}";   
			    
			    arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,batchId)); //PN_BATCH_ID
			 	arrParameter.add(new ParameterDTO(++parameterCount,"OUT",oracle.jdbc.OracleTypes.VARCHAR,null)); //BATCH_SUMMARY_HEADER
			 	arrParameter.add(new ParameterDTO(++parameterCount,"OUT",oracle.jdbc.OracleTypes.CURSOR,null)); //BATCH_SUMMARY_DETAILS
			 	arrParameter.add(new ParameterDTO(++parameterCount,"OUT",oracle.jdbc.OracleTypes.VARCHAR,null)); //FILE_SUMMARY_HEADER
			 	arrParameter.add(new ParameterDTO(++parameterCount,"OUT",oracle.jdbc.OracleTypes.CURSOR,null)); //FILE_SUMMARY_DETAILS
			 	
			 	arrParameter.add(new ParameterDTO(++parameterCount,"OUT",oracle.jdbc.OracleTypes.VARCHAR,null)); //BATCH_DTLS_HEADER
			 	arrParameter.add(new ParameterDTO(++parameterCount,"OUT",oracle.jdbc.OracleTypes.CURSOR,null)); //BATCH_DETAILS
			    arrParameter.add(new ParameterDTO(++parameterCount,"OUT",oracle.jdbc.OracleTypes.VARCHAR,null)); //PC_STATUS
			 	arrParameter.add(new ParameterDTO(++parameterCount,"OUT",oracle.jdbc.OracleTypes.VARCHAR,null)); //PC_MESSAGE
			 	
		  }
		try
		{
			dal.processSPROC(procName, arrParameter,dbConnectionMap);
			ArrayList list=null;
			String headerString="";
			returnMap=new HashMap();
			
			parameterDTO = (ParameterDTO)arrParameter.get(1);			
			headerString=(String)parameterDTO.getParameterValue();
			returnMap.put("BATCH_SUMMARY_HEADER", headerString);  
			
			parameterDTO = (ParameterDTO)arrParameter.get(2);			
			list=(ArrayList)parameterDTO.getParameterValue();
			returnMap.put("BATCH_SUMMARY_DETAILS", list);

			parameterDTO = (ParameterDTO)arrParameter.get(3);			
			headerString=(String)parameterDTO.getParameterValue();
			returnMap.put("FILE_SUMMARY_HEADER", headerString); 
			
			parameterDTO = (ParameterDTO)arrParameter.get(4);			
			list=(ArrayList)parameterDTO.getParameterValue();
			returnMap.put("FILE_SUMMARY_DETAILS", list);
			
			parameterDTO = (ParameterDTO)arrParameter.get(5);			
			headerString=(String)parameterDTO.getParameterValue();
			returnMap.put("BATCH_DTLS_HEADER", headerString); 
			
			parameterDTO = (ParameterDTO)arrParameter.get(6);			
			list=(ArrayList)parameterDTO.getParameterValue();
			returnMap.put("BATCH_DETAILS", list);
			
			/*parameterDTO = (ParameterDTO)arrParameter.get(3);			
			status=(String)parameterDTO.getParameterValue();
			returnMap.put("PC_OUT_STATUS", status);
			
			parameterDTO = (ParameterDTO)arrParameter.get(4);			
			status=(String)parameterDTO.getParameterValue();
			returnMap.put("PC_OUT_MESSAGE", status);	*/		
			
		}
		catch (DBConnectionException dce) 
		{
			log.error("DBConnectionException - "+dce.getMessage());
		}
		catch (Exception e) 
		{
			log.error("Exception - "+e.getMessage());
		}
		log.info("End");
			
		return returnMap;
	}
	//1.0.0.4 end
	
	//1.0.0.5 start
	public HashMap getBatchErrReportDetails(String fromDate,String toDate, Map dbConnectionMap)
	{
		HashMap returnMap=null;
		DAL dal=new DAL();
		int parameterCount=0;
		ArrayList arrParameter=new ArrayList();
		String procName=null;
		ParameterDTO parameterDTO = new ParameterDTO();
		
		if(dbConnectionMap.get("DB_SERVER").toString().equals("ORACLE"))
		  {
			    procName="{call QC_LMS.PKG_INVOICE.PR_BATCH_ERROR_REPORT(?,?,?,?,?, ?)}";   
			    
			    arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,fromDate)); //PD_INV_DATE_FROM
			    arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,toDate)); //PD_INV_DATE_TO
			 	arrParameter.add(new ParameterDTO(++parameterCount,"OUT",oracle.jdbc.OracleTypes.VARCHAR,null)); //PC_BATCH_ERROR_HDR
			 	arrParameter.add(new ParameterDTO(++parameterCount,"OUT",oracle.jdbc.OracleTypes.CURSOR,null)); // PCUR_BATCH_ERROR_DATA
			    arrParameter.add(new ParameterDTO(++parameterCount,"OUT",oracle.jdbc.OracleTypes.VARCHAR,null)); //PC_STATUS

			    arrParameter.add(new ParameterDTO(++parameterCount,"OUT",oracle.jdbc.OracleTypes.VARCHAR,null)); //PC_MESSAGE
			 	
		  }
		try
		{
			dal.processSPROC(procName, arrParameter,dbConnectionMap);
			ArrayList list=null;
			String headerString="";
			String status="";
			String msg="";
			returnMap=new HashMap();
			
			parameterDTO = (ParameterDTO)arrParameter.get(2);			
			headerString=(String)parameterDTO.getParameterValue();
			returnMap.put("PC_BATCH_ERROR_HDR", headerString);  
			
			parameterDTO = (ParameterDTO)arrParameter.get(3);			
			list=(ArrayList)parameterDTO.getParameterValue();
			returnMap.put("PCUR_BATCH_ERROR_DATA", list);

			parameterDTO = (ParameterDTO)arrParameter.get(4);			
			status=(String)parameterDTO.getParameterValue();
			returnMap.put("PC_OUT_STATUS", status);
			
			parameterDTO = (ParameterDTO)arrParameter.get(5);			
			msg=(String)parameterDTO.getParameterValue();
			returnMap.put("PC_OUT_MESSAGE", msg);		
			
		}
		catch (DBConnectionException dce) 
		{
			log.error("DBConnectionException - "+dce.getMessage());
		}
		catch (Exception e) 
		{
			log.error("Exception - "+e.getMessage());
		}
		log.info("End");
			
		return returnMap;
	}
	//1.0.0.5 end
	
	// 1.0.0.6 start
	
	public Map getEInvoicingDtl(String requestType,String  transactionType,Map dbConnectionMap)
	{
		log.info("Start getEInvoicingDtl");
		HashMap returnMap=null;
		DAL dal=new DAL();
    	int parameterCount=0;
		ArrayList arrParameter=new ArrayList();
		String procName=null;
		ParameterDTO parameterDTO = new ParameterDTO();
		
		
		
		
		if(dbConnectionMap.get("DB_SERVER").toString().equals("ORACLE"))
		  {
			    procName="{call QC_LMS.PKG_E_INVOICE.PR_GET_EINV_DTLS(?,?,?,?,?,?)}";
			 	arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,requestType)); 
			 	arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,transactionType)); 
			 	arrParameter.add(new ParameterDTO(++parameterCount,"OUT",oracle.jdbc.OracleTypes.CURSOR,null));		
			 	arrParameter.add(new ParameterDTO(++parameterCount,"OUT",oracle.jdbc.OracleTypes.CURSOR,null));		
			 	arrParameter.add(new ParameterDTO(++parameterCount,"OUT",oracle.jdbc.OracleTypes.VARCHAR,null)); 
			 	arrParameter.add(new ParameterDTO(++parameterCount,"OUT",oracle.jdbc.OracleTypes.VARCHAR,null)); 			 	


		  }
		try
		{
			dal.processSPROC(procName, arrParameter,dbConnectionMap);
			ArrayList list=null;
			String status="";
			String totalNoOfPages="";
			returnMap=new HashMap();
						
			parameterDTO = (ParameterDTO)arrParameter.get(4);			
			status=(String)parameterDTO.getParameterValue();
			parameterDTO = (ParameterDTO)arrParameter.get(5);			
			String message=(String)parameterDTO.getParameterValue();
			returnMap.put("PC_OUT_STATUS", status);		
			returnMap.put("PC_OUT_MESSAGE", message);
			if(status.equalsIgnoreCase("S"))
			{
				parameterDTO = (ParameterDTO)arrParameter.get(2);			
				list=(ArrayList)parameterDTO.getParameterValue();
				returnMap.put("INVOICE_DTL_LIST", list);
				parameterDTO = (ParameterDTO)arrParameter.get(3);			
				list=(ArrayList)parameterDTO.getParameterValue();
				
				returnMap.put("INVOICE_ITEM_LIST", list);
			}
			
	
		}
		catch (DBConnectionException dce) 
		{
			log.error("DBConnectionException - "+dce.getMessage());
		}
		catch (Exception e) 
		{
			log.error("Exception - "+e.getMessage());
		}
		log.info("End getEInvoicingDtl");
			
		return returnMap;
	}
	
	public Map saveEInvoicingDtl(Map responceObj,Map dbConnectionMap)
	{
		log.info("Start getEInvoicingDtl");
		HashMap returnMap=null;
		DAL dal=new DAL();
    	int parameterCount=0;
		ArrayList arrParameter=new ArrayList();
		String procName=null;
		ParameterDTO parameterDTO = new ParameterDTO();
		Connection connection=null;
		File file=null;
		if(responceObj.get("PN_PROCESS_STATUS")!=null && responceObj.get("PN_PROCESS_STATUS").toString().equalsIgnoreCase("2000000003"))
		{
			 //HashMap<String, File> fileMap= (HashMap<String, File>) responceObj.get("E_INV_FILE_OBJ");
			 
			//file= fileMap.get("FILEDATA");
			
			if(responceObj.get("PC_QR_CODE")!=null && responceObj.get("PC_QR_CODE")!="")
			  {
				  try
				   {
				   String eInvFilePath=responceObj.get("E_INV_FILE_PATH")!=null?responceObj.get("E_INV_FILE_PATH").toString()+responceObj.get("E_INV_FILE_NAME").toString():"";
				   String fileFormat=responceObj.get("PC_QR_CODE_FORMAT")!=null?responceObj.get("PC_QR_CODE_FORMAT").toString():"jpg";
				   file = new File(eInvFilePath+"."+fileFormat); 
				    if(file.exists())
				    {
				    	file.delete();
				     }
				    file.createNewFile();
				    byte[] datainfo= Base64.decode(responceObj.get("PC_QR_CODE").toString());
				    OutputStream out = new FileOutputStream(file);
				    out.write(datainfo);
				    out.flush();
				    out.close();   
				   // HashMap<String, File> fileMap= new HashMap<String, File>();
				    //fileMap.put("FILEDATA", file);
				   // responseDataObj.put("E_INV_FILE_OBJ", fileMap);
				   }
				   catch (Exception e) {
					// TODO: handle exception
					   log.info("saveEInvoicingDtl  api qr code file writing  exception"+e.getStackTrace());
				}
				   
			  }
			else
			{
				String eInvFilePath=responceObj.get("E_INV_FILE_PATH")!=null?responceObj.get("E_INV_FILE_PATH").toString():"";
				file= new File(eInvFilePath+"failure.jpg");
				if(!file.exists())
				{
					try {
						file.createNewFile();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						log.info("saveEInvoicingDtl  api qr code file writing  exception"+e.getStackTrace());
					}
				}
				
			}
			
			
			
		}
		else
		{
			String eInvFilePath=responceObj.get("E_INV_FILE_PATH")!=null?responceObj.get("E_INV_FILE_PATH").toString():"";
			file= new File(eInvFilePath+"failure.jpg");
			if(!file.exists())
			{
				try {
					file.createNewFile();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					log.info("saveEInvoicingDtl  api qr code file writing  exception"+e.getStackTrace());
				}
			}
		}
		
		
		if(dbConnectionMap.get("DB_SERVER").toString().equals("ORACLE"))
		  {
			procName="{call QC_LMS.PKG_E_INVOICE.PR_SAVE_EINV_DTLS(?,?,?,?,?, ?,?,?,?,?, ?,?,?,?,?, ?,?,?,?)}";
		 	arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,responceObj.get("PC_REQUEST_TYPE")!=null?responceObj.get("PC_REQUEST_TYPE"):"")); 
		 	arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,responceObj.get("PC_REQ_TRANS_TYPE")!=null?responceObj.get("PC_REQ_TRANS_TYPE"):"")); 
		 	arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,responceObj.get("PC_EINV_CATEGORY")!=null?responceObj.get("PC_EINV_CATEGORY"):"")); 
		 	
		 	arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,responceObj.get("PN_TRANS_ID")!=null?responceObj.get("PN_TRANS_ID"):"")); 
		 	arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,responceObj.get("PC_TRANS_TYPE")!=null?responceObj.get("PC_TRANS_TYPE"):"")); 
		 	arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,responceObj.get("PC_IRN")!=null?responceObj.get("PC_IRN"):"")); 
		 	
		 	arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.CLOB,responceObj.get("PC_QR_CODE")!=null?responceObj.get("PC_QR_CODE"):"")); 
		 	arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.BLOB,file)); 
		 	arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,responceObj.get("PC_ACK_NUMBER")!=null?responceObj.get("PC_ACK_NUMBER"):"")); 
		 	
		 	arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,responceObj.get("PC_ACK_DATE")!=null?responceObj.get("PC_ACK_DATE"):"")); 
		 	arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,responceObj.get("PC_CAN_DATE")!=null?responceObj.get("PC_CAN_DATE"):"")); 
		 	arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,responceObj.get("PD_REQUEST_LOG_DATE")!=null?responceObj.get("PD_REQUEST_LOG_DATE"):"")); 
		 	arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,responceObj.get("PD_RESPONSE_DATE")!=null?responceObj.get("PD_RESPONSE_DATE"):"")); 
		 	
		 	arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,responceObj.get("PC_RESPONSE_STATUS")!=null?responceObj.get("PC_RESPONSE_STATUS"):"")); 
		 	arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.CLOB,responceObj.get("PC_ERROR_DESCRIPTION")!=null?responceObj.get("PC_ERROR_DESCRIPTION"):"")); 
		 	arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,responceObj.get("PN_PROCESS_STATUS")!=null?responceObj.get("PN_PROCESS_STATUS"):"")); 
		 	
		 	arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.CLOB,responceObj.get("PC_REQUEST_JSON")!=null?responceObj.get("PC_REQUEST_JSON"):""));
		 	arrParameter.add(new ParameterDTO(++parameterCount,"OUT",oracle.jdbc.OracleTypes.VARCHAR,null)); 
		 	arrParameter.add(new ParameterDTO(++parameterCount,"OUT",oracle.jdbc.OracleTypes.VARCHAR,null)); 	
		 	

		  }
		try
		{
			
			connection=DBConnection.getConnection(dbConnectionMap);
			connection.setAutoCommit(false);
			dal.processSPROC(procName, arrParameter,dbConnectionMap,connection);
			ArrayList list=null;
			
			returnMap=new HashMap();
						
			parameterDTO = (ParameterDTO)arrParameter.get(17);			
			String status=(String)parameterDTO.getParameterValue();
			parameterDTO = (ParameterDTO)arrParameter.get(18);			
			String message=(String)parameterDTO.getParameterValue();
			returnMap.put("PC_OUT_STATUS", status);		
			returnMap.put("PC_OUT_MESSAGE", message);
			if(status.equalsIgnoreCase("S"))
			{
				DBConnection.commitTransaction(connection);
				if(file.exists())
				{
					if(responceObj.get("PN_PROCESS_STATUS")!=null && responceObj.get("PN_PROCESS_STATUS").toString().equalsIgnoreCase("2000000003")) //1.0.0.8
					{
						file.delete();
					}				
					
				}
				
			}
			else
			{
				log.error("processSPROC - FAIL:");
				log.error("processSPROC - Parameter : \r\n1. procName= " + procName + "; \r\n2. colParameter=( " + PrintParameterList.printParameterList(arrParameter) + " );");
				DBConnection.rollbackTransaction(connection);
				
			}	
			
			
	
		}
		catch (DBConnectionException dce) 
		{
			log.error("DBConnectionException - "+dce.getMessage());
			DBConnection.rollbackTransaction(connection);
		}
		catch (Exception e) 
		{
			log.error("Exception - "+e.getMessage());
			DBConnection.rollbackTransaction(connection);
		}
		finally
		{
			DBConnection.closeConnection(connection);
		}
		log.info("End getEInvoicingDtl");
			
		return returnMap;
	}
	
	public Map registerEInvoicingProcessStatus(String  batchId,Map dbConnectionMap)
	{
		log.info("Start getEInvoicingDtl");
		HashMap returnMap=null;
		DAL dal=new DAL();
    	int parameterCount=0;
		ArrayList arrParameter=new ArrayList();
		String procName=null;
		ParameterDTO parameterDTO = new ParameterDTO();
		
		
		
		
		if(dbConnectionMap.get("DB_SERVER").toString().equals("ORACLE"))
		  {
			    procName="{call QC_LMS.PKG_E_INVOICE.PR_INIT_EINV_REG_PROCESS(?,?,?)}";
			 	arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,batchId)); 
			 	arrParameter.add(new ParameterDTO(++parameterCount,"OUT",oracle.jdbc.OracleTypes.VARCHAR,null)); 
			 	arrParameter.add(new ParameterDTO(++parameterCount,"OUT",oracle.jdbc.OracleTypes.VARCHAR,null)); 			 	


		  }
		try
		{
			dal.processSPROC(procName, arrParameter,dbConnectionMap);
			ArrayList list=null;
			String status="";
			String totalNoOfPages="";
			returnMap=new HashMap();
						
			parameterDTO = (ParameterDTO)arrParameter.get(1);			
			status=(String)parameterDTO.getParameterValue();
			parameterDTO = (ParameterDTO)arrParameter.get(2);			
			String message=(String)parameterDTO.getParameterValue();
			returnMap.put("PC_OUT_STATUS", status);		
			returnMap.put("PC_OUT_MESSAGE", message);
			
	
		}
		catch (DBConnectionException dce) 
		{
			log.error("DBConnectionException - "+dce.getMessage());
		}
		catch (Exception e) 
		{
			log.error("Exception - "+e.getMessage());
		}
		log.info("End getEInvoicingDtl");
			
		return returnMap;
	}
	
	
	
	public Map getEInvoicingProcessStatus(String  batchId,Map dbConnectionMap)
	{
		log.info("Start getEInvoicingDtl");
		HashMap returnMap=null;
		DAL dal=new DAL();
    	int parameterCount=0;
		ArrayList arrParameter=new ArrayList();
		String procName=null;
		ParameterDTO parameterDTO = new ParameterDTO();
		
		
		
		
		if(dbConnectionMap.get("DB_SERVER").toString().equals("ORACLE"))
		  {
			    procName="{call QC_LMS.PKG_INVOICE.PR_GET_EINV_REG_PROCESS_STATUS(?,?,?,?)}";
			 	arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,batchId)); 
			 	arrParameter.add(new ParameterDTO(++parameterCount,"OUT",oracle.jdbc.OracleTypes.VARCHAR,null));		 	
			 	arrParameter.add(new ParameterDTO(++parameterCount,"OUT",oracle.jdbc.OracleTypes.VARCHAR,null)); 
			 	arrParameter.add(new ParameterDTO(++parameterCount,"OUT",oracle.jdbc.OracleTypes.VARCHAR,null)); 			 	


		  }
		try
		{
			dal.processSPROC(procName, arrParameter,dbConnectionMap);
			ArrayList list=null;
			String status="";
			String totalNoOfPages="";
			returnMap=new HashMap();
						
			parameterDTO = (ParameterDTO)arrParameter.get(1);			
			String Status=(String)parameterDTO.getParameterValue();
			parameterDTO = (ParameterDTO)arrParameter.get(2);			
			status=(String)parameterDTO.getParameterValue();
			parameterDTO = (ParameterDTO)arrParameter.get(3);			
			String message=(String)parameterDTO.getParameterValue();
			
			returnMap.put("PC_OUT_STATUS", status);		
			returnMap.put("PC_OUT_MESSAGE", message);
			returnMap.put("PC_OUT_STATUS", Status);
			
			
	
		}
		catch (DBConnectionException dce) 
		{
			log.error("DBConnectionException - "+dce.getMessage());
		}
		catch (Exception e) 
		{
			log.error("Exception - "+e.getMessage());
		}
		log.info("End getEInvoicingDtl");
			
		return returnMap;
	}
	
	// 1.0.0.6 end

	// 1.0.0.10 Start - Vehicle RC Authentication
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map getPendingVehicleRCList(Map dbConnectionMap) {
		log.info("Start getPendingVehicleRCList");
		HashMap returnMap = null;
		DAL dal = new DAL();
		int parameterCount = 0;
		ArrayList arrParameter = new ArrayList();
		String procName = null;
		ParameterDTO parameterDTO = new ParameterDTO();

		if (dbConnectionMap.get("DB_SERVER").toString().equals("ORACLE")) {
			procName = "{call QC_QUOTATION.PKG_VEHICLE_RC_AUTH.PR_GET_PENDING_VEHICLE_RC(?,?,?)}";
			arrParameter.add(new ParameterDTO(++parameterCount, "OUT", oracle.jdbc.OracleTypes.CURSOR,  null));
			arrParameter.add(new ParameterDTO(++parameterCount, "OUT", oracle.jdbc.OracleTypes.VARCHAR, null));
			arrParameter.add(new ParameterDTO(++parameterCount, "OUT", oracle.jdbc.OracleTypes.VARCHAR, null));
		}
		try {
			dal.processSPROC(procName, arrParameter, dbConnectionMap);
			ArrayList list = null;
			returnMap = new HashMap();

			parameterDTO = (ParameterDTO) arrParameter.get(1);
			String status = (String) parameterDTO.getParameterValue();

			parameterDTO = (ParameterDTO) arrParameter.get(2);
			String message = (String) parameterDTO.getParameterValue();

			returnMap.put("PC_OUT_STATUS",  status);
			returnMap.put("PC_OUT_MESSAGE", message);

			if (status != null && status.equalsIgnoreCase("S")) {
				parameterDTO = (ParameterDTO) arrParameter.get(0);
				list = (ArrayList) parameterDTO.getParameterValue();
				returnMap.put("PCUR_VEHICLE_RC_DTLS", list);
			}
		} catch (DBConnectionException dce) {
			log.error("DBConnectionException - " + dce.getMessage());
		} catch (Exception e) {
			log.error("Exception - " + e.getMessage());
		}
		log.info("End getPendingVehicleRCList");
		return returnMap;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void processVehicleRCRecord(Map resultSetMap, String requestUrl, String msgVersion,
			String appId, String token, String consent, String version,
			String corrIdPrefix, int timeOut, Map dbConnectionMapLMS) {
		log.info("processVehicleRCRecord - Start");

		String requestId          = resultSetMap.get("REQUEST_ID") != null ? (String) resultSetMap.get("REQUEST_ID") : "";
		String registrationNumber = resultSetMap.get("REG_NO")     != null ? (String) resultSetMap.get("REG_NO")     : "";

		log.info("processVehicleRCRecord - requestId: " + requestId + " | regNo: " + registrationNumber);

		if (registrationNumber == null || registrationNumber.trim().isEmpty()) {
			log.warn("processVehicleRCRecord - Skipping, registration number blank for requestId: " + requestId);
			return;
		}

		String responseStatus    = "F";
		String responseMessage   = "FAIL";
		String payloadJsonString = "";

		try {
			String correlationId = corrIdPrefix + Math.random();
			LinkedHashMap requestHeaderMap = new LinkedHashMap();
			requestHeaderMap.put("correlationId", correlationId);
			requestHeaderMap.put("msgVersion",    msgVersion);
			requestHeaderMap.put("appId",         appId);
			requestHeaderMap.put("token",         token);

			LinkedHashMap payloadMap = new LinkedHashMap();
			payloadMap.put("registrationNumber", registrationNumber);
			payloadMap.put("consent",            consent);
			payloadMap.put("version",            Double.parseDouble(version));

			LinkedHashMap requestMap = new LinkedHashMap();
			requestMap.put("header",  requestHeaderMap);
			requestMap.put("payload", payloadMap);

			log.debug("processVehicleRCRecord requestMap: " + requestMap);
			String serviceOutput = ServiceCallAction.getResultString(requestMap, requestUrl, timeOut);
			log.debug("processVehicleRCRecord serviceOutput: " + serviceOutput);

			if (serviceOutput != null && !serviceOutput.isEmpty() && !serviceOutput.startsWith("Error,")) {
				org.json.JSONObject jsonObj      = new org.json.JSONObject(serviceOutput);
				org.json.JSONObject errorInfoObj = jsonObj.has("errorInfo") ? jsonObj.getJSONObject("errorInfo") : null;

				if (errorInfoObj != null && !errorInfoObj.isNull("status")
						&& errorInfoObj.getString("status").equalsIgnoreCase("SUCCESS")) {

					org.json.JSONObject headerObj  = jsonObj.has("header")  ? jsonObj.getJSONObject("header")  : null;
					org.json.JSONObject payloadObj = jsonObj.has("payload") ? jsonObj.getJSONObject("payload") : null;
					org.json.JSONObject resultObj  = (payloadObj != null && payloadObj.has("result"))
							? payloadObj.getJSONObject("result") : null;

					responseStatus  = "S";
					responseMessage = errorInfoObj.isNull("message") ? "SUCCESS" : errorInfoObj.getString("message");

					if (payloadObj != null) {
						payloadJsonString = payloadObj.toString(4);
					}
					log.info("processVehicleRCRecord - payloadObj extracted, resultObj null: " + (resultObj == null));

				} else {
					responseStatus  = "F";
					responseMessage = (errorInfoObj != null && !errorInfoObj.isNull("message"))
							? errorInfoObj.getString("message") : "FAIL";
				}
			} else {
				responseStatus  = "F";
				responseMessage = serviceOutput != null ? serviceOutput : "Response not generated";
			}

		} catch (Exception e) {
			responseStatus  = "F";
			responseMessage = "Exception: " + e.getMessage();
			log.error("processVehicleRCRecord - Exception: " + e.getMessage());
		}

		try {
			Map saveMap = saveVehicleRCApiResponse(requestId, responseStatus, responseMessage, payloadJsonString, dbConnectionMapLMS);
			log.info("processVehicleRCRecord - saveVehicleRCApiResponse : " + saveMap.get("PC_OUT_MESSAGE"));
		} catch (Exception e) {
			log.error("processVehicleRCRecord - saveVehicleRCApiResponse Exception: " + e.getMessage());
		}
		log.info("processVehicleRCRecord - End | requestId: " + requestId + " | status: " + responseStatus);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map saveVehicleRCApiResponse(String requestId, String responseStatus, String responseMessage,
			String payloadJsonString, Map dbConnectionMap) {
		log.info("Start saveVehicleRCApiResponse | requestId: " + requestId);
		HashMap returnMap = null;
		DAL dal = new DAL();
		int parameterCount = 0;
		ArrayList arrParameter = new ArrayList();
		String procName = null;
		ParameterDTO parameterDTO = new ParameterDTO();
		Connection connection = null;

		if (dbConnectionMap.get("DB_SERVER").toString().equals("ORACLE")) {
			procName = "{call QC_QUOTATION.PKG_VEHICLE_RC_AUTH.PR_SAVE_VEHICLE_RC_RESPONSE(?,?,?,?,?,?)}";
			arrParameter.add(new ParameterDTO(++parameterCount, "IN",  oracle.jdbc.OracleTypes.VARCHAR, requestId));
			arrParameter.add(new ParameterDTO(++parameterCount, "IN",  oracle.jdbc.OracleTypes.VARCHAR, responseStatus));
			arrParameter.add(new ParameterDTO(++parameterCount, "IN",  oracle.jdbc.OracleTypes.VARCHAR, responseMessage));
			arrParameter.add(new ParameterDTO(++parameterCount, "IN",  oracle.jdbc.OracleTypes.CLOB,    payloadJsonString));
			arrParameter.add(new ParameterDTO(++parameterCount, "OUT", oracle.jdbc.OracleTypes.VARCHAR, null));
			arrParameter.add(new ParameterDTO(++parameterCount, "OUT", oracle.jdbc.OracleTypes.VARCHAR, null));
		}
		try {
			connection = DBConnection.getConnection(dbConnectionMap);
			connection.setAutoCommit(false);
			dal.processSPROC(procName, arrParameter, dbConnectionMap, connection);
			returnMap = new HashMap();

			parameterDTO = (ParameterDTO) arrParameter.get(4);
			String status = (String) parameterDTO.getParameterValue();

			parameterDTO = (ParameterDTO) arrParameter.get(5);
			String message = (String) parameterDTO.getParameterValue();

			returnMap.put("PC_OUT_STATUS",  status);
			returnMap.put("PC_OUT_MESSAGE", message);
			log.info("saveVehicleRCApiResponse - status: " + status + " | message: " + message);

			if (status != null && status.equalsIgnoreCase("S")) {
				DBConnection.commitTransaction(connection);
			} else {
				log.error("processSPROC - FAIL:");
				log.error("processSPROC - Parameter : \r\n1. procName= " + procName
						+ "; \r\n2. colParameter=( " + PrintParameterList.printParameterList(arrParameter) + " );");
				DBConnection.rollbackTransaction(connection);
			}
		} catch (DBConnectionException dce) {
			log.error("DBConnectionException - " + dce.getMessage());
			DBConnection.rollbackTransaction(connection);
		} catch (Exception e) {
			log.error("Exception - " + e.getMessage());
			DBConnection.rollbackTransaction(connection);
		} finally {
			DBConnection.closeConnection(connection);
		}
		log.info("End saveVehicleRCApiResponse");
		return returnMap;
	}
	// 1.0.0.10 End - Vehicle RC Authentication
}
				DBConnection.rollbackTransaction(connection);
			}
		} catch (DBConnectionException dce) {
			log.error("DBConnectionException - " + dce.getMessage());
			DBConnection.rollbackTransaction(connection);
		} catch (Exception e) {
			log.error("Exception - " + e.getMessage());
			DBConnection.rollbackTransaction(connection);
		} finally {
			DBConnection.closeConnection(connection);
		}
		log.info("End saveVehicleRCApiResponse");
		return returnMap;
	}
	// 1.0.0.10 End - Vehicle RC Authentication
}
