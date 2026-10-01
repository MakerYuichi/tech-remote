package qc.common.servlet;
/* HEADER INFO
+  File NAME 	: EasebuzzInvoiceSchedulerINV.java
+  PURPOSE		: 
+  CREATED BY	: 
+  CREATION DATE	: 
+  INITIAL VERSION : 1.0.0.0
+  **********************************************************************************************************************************
+  VERSION NO   UPDATED BY       	UPDATED ON      REASON FOR CHANGE
+  1.0.0.1		Sanchi Agarwal		1-Oct-2026		QR CODE 
  **********************************************************************************************************************************
*/ 
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.TimerTask;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;

import qc.common.bean.CommonBean;
import qc.quotation.model.bo.InvoiceDetailsBO;
import qc.webService.controller.action.ServiceCallAction;

@SuppressWarnings("rawtypes")
public class EasebuzzInvoiceSchedulerINV extends TimerTask {

	
	protected static Logger log = Logger.getLogger(EasebuzzInvoiceSchedulerINV.class);
	 static Locale locale = new Locale("en","US");
	 InvoiceDetailsBO invoiceDetailsBo=new InvoiceDetailsBO();
	 ResourceBundle commonApplicationResource = ResourceBundle.getBundle("resourceProperties.commonApplicationResource",locale);
	 
	@SuppressWarnings("unchecked")
	public void run()
	     { 
	    	 log.info("Start - EasebuzzInvoiceSchedulerINV");
	    		try
	    		{ 
	    			Map dbConnectionMapLMS =  CommonBean.getDBConnectionMap("dbConnection/DBConnectionMapLMS", locale);
	    			Map returnMap=invoiceDetailsBo.getEaseBuzzDtls(dbConnectionMapLMS);
	    	    	if(returnMap!=null&& returnMap.get("PCUR_EB_DTLS")!=null)
	    	    	{
	    	    		HashMap requestMap=null;
	    	    		Map resultSetMap=null;
	    				HashMap requestHeaderMap= null;
	    				
	    				ArrayList pendingList= returnMap.get("PCUR_EB_DTLS")!=null ?(ArrayList)returnMap.get("PCUR_EB_DTLS"):null;
	    				if(pendingList!=null && pendingList.size()>0)
	    				{
	    				  Iterator itr = pendingList.iterator();
	    					if(itr!=null)
	    					{
	    						int timeOut=Integer.parseInt(commonApplicationResource.getString("API_SERVICE_LOGOUT_IN_SEC"));
	    						String urlPath= commonApplicationResource.getString("EASEBUZZ_QR_CODE_URL");
	    						while(itr.hasNext())
	    						{
	    							try {
	    								requestMap = new LinkedHashMap();
		    							resultSetMap=(Map)itr.next();
		    							requestHeaderMap=new HashMap();
		    							LinkedHashMap payloadMap= new LinkedHashMap();
		    							LinkedHashMap responseMap= new LinkedHashMap();
		    							LinkedHashMap authRemitters= new LinkedHashMap();
		    							LinkedHashMap txnMap= new LinkedHashMap();
		    							//String txnId=resultSetMap.get("TXNID").toString();
		    							String uniqueRequestID=resultSetMap.get("UNIQUE_REQUEST_NUMBER").toString()+new SimpleDateFormat("MMddHHmmss").format(Calendar.getInstance().getTime())
		    									+String.format("%04d", new Random().nextInt(9999));
	;
		    							String requestId=resultSetMap.get("UNIQUE_REQUEST_NUMBER").toString();
		    							
		    							requestHeaderMap.put("msgVersion", commonApplicationResource.getString("EASEBUZZ_MSG_VERSION"));
		    						    requestHeaderMap.put("appId", commonApplicationResource.getString("EASEBUZZ_APP_ID"));
		    						    requestHeaderMap.put("token", commonApplicationResource.getString("EASEBUZZ_TOKEN"));
		    							requestHeaderMap.remove("correlationId");
		    							String correlationId = "EASEBUZZ" + "_" + Math.random();
		    							requestHeaderMap.put("correlationId", correlationId);
		    							
		    							payloadMap.put("key", "");
		    							payloadMap.put("label", "");
		    							
		    							payloadMap.put("description", resultSetMap.get("description")!=null?resultSetMap.get("description").toString():"");
		    							payloadMap.put("unique_request_number", uniqueRequestID);
		    							payloadMap.put("virtual_account_number", resultSetMap.get("VIRTUAL_ACCOUNT_NUMBER")!=null
		    									?resultSetMap.get("VIRTUAL_ACCOUNT_NUMBER").toString():"");
		    							payloadMap.put("virtual_payment_address", resultSetMap.get("VIRTUAL_PAYMENT_ADDRESS")!=null
		    									?resultSetMap.get("VIRTUAL_PAYMENT_ADDRESS").toString():"");
		    						    payloadMap.put("auto_deactivate_at", commonApplicationResource.getString("AUTO_DEACTIVATE_AT"));
		    						    
		    						    //ArrayList remitter=new ArrayList<>();
		    						    
		    						    //authRemitters.put("account_number", resultSetMap.get("ACCOUNT_NUMBER")!=null?resultSetMap.get("ACCOUNT_NUMBER").toString():"");
		    						    //authRemitters.put("account_ifsc", resultSetMap.get("ACCOUNT_IFSC")!=null?resultSetMap.get("ACCOUNT_IFSC").toString():"");
		    						    //remitter.add(authRemitters);
		    						    
		    						    //payloadMap.put("authorized_remitters", remitter);
		    						    
		    						    txnMap.put("imps", commonApplicationResource.getString("AMOUNT_LIMIT_IMPS"));
		    						    txnMap.put("neft", commonApplicationResource.getString("AMOUNT_LIMIT_NEFT"));
		    						    txnMap.put("rtgs", commonApplicationResource.getString("AMOUNT_LIMIT_RTGS"));
		    						    payloadMap.put("transaction_amount_limit", txnMap);
		    							
		    						    requestMap.put("header", requestHeaderMap);
		    						    requestMap.put("payload", payloadMap);
		    							
		    							responseMap.put("PN_TRANS_ID",resultSetMap.get("TRANS_ID")!=null?resultSetMap.get("TRANS_ID").toString():"");
		    							responseMap.put("PC_TRANS_STAGE","UPDATE STATUS");
		    							responseMap.put("PD_RESPONSE_DATE","");
		    							 
	    							  Date requestDate=new Date();
	    							  SimpleDateFormat dateFormat=new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
	    							  String requestTime=dateFormat.format(requestDate);
	    							  String responseTime=null;
	    							  JSONObject jsonObj=new JSONObject();
	    							  Gson gson = new Gson();
	    							  String requestParam=gson.toJson(requestMap);
	    							  String virtualAcID="";
	    							  String responseStatus="FAILURE";
	    							  invoiceDetailsBo.saveEaseBuzzDtls("",requestId,requestParam,jsonObj,
		    						    			requestTime,responseTime,"2000000002",null,virtualAcID,"","", dbConnectionMapLMS);
		    						    String serviceOutput = ServiceCallAction.getResultString(requestMap,urlPath,timeOut);
		    							//String serviceOutput="{\"header\":{\"msgVersion\":\"1.0\",\"appId\":\"EASEBUZZ\",\"correlationId\":\"SBFC_AP00692352_A5\",\"token\":\"3e700c07-6fe8-504f-b6b1-8b0f39b5d1ca\"},\"errorInfo\":{\"code\":\"200\",\"status\":\"SUCCESS\",\"message\":\"Response generated successfully\",\"description\":\"Response generated successfully\"},\"payload\":{\"success\":false,\"message\":\"Authorized Remitters :: Account IFSC is mandatory.\"}}";
		    						    responseTime=dateFormat.format(new Date());
		    						    
		    						    if(!serviceOutput.isEmpty() && !serviceOutput.equalsIgnoreCase(null) &&!serviceOutput.equalsIgnoreCase("") && !serviceOutput.startsWith("Error,"))
		    							  {
		    								  jsonObj = new  JSONObject(serviceOutput.toString()); 
		    								  JSONObject errorInfoObj = jsonObj.has("errorInfo")? jsonObj.getJSONObject("errorInfo"):null;
		    							  
		    								  if(errorInfoObj!=null && errorInfoObj.getString("status")!=null && errorInfoObj.getString("status").equalsIgnoreCase("SUCCESS")) 
		    								  { 
		    									  JSONObject payloadObjObj = jsonObj.getJSONObject("payload"); 
		    									  if(payloadObjObj.getBoolean("success")) {
		    										  JSONObject data = payloadObjObj.getJSONObject("data");
		    										  JSONObject virtual_account = data.getJSONObject("virtual_account");
		    										  String url=virtual_account.getString("upi_qrcode_remote_file_location");
		    										  virtualAcID=virtual_account.getString("virtual_account_number");//1.0.0.1
		    										  responseStatus="SUCCESS";
		    										  invoiceDetailsBo.saveEaseBuzzDtls("",requestId,serviceOutput,jsonObj,
				  	    						    			requestTime,responseTime,"2000000003",getImageFromUrl(url),virtualAcID,"",responseStatus, dbConnectionMapLMS);
		    										  
		    									  }else {
			    									  invoiceDetailsBo.saveEaseBuzzDtls("",requestId,serviceOutput,jsonObj,
			  	    						    			requestTime,responseTime,"2000000004",null,virtualAcID,payloadObjObj.getString("message"),responseStatus, dbConnectionMapLMS);
		    									  }
		    								  }
		    								  else
		    								  {   
		    									  invoiceDetailsBo.saveEaseBuzzDtls("",requestId,serviceOutput,jsonObj,
		  	    						    			requestTime,responseTime,"2000000004",null,virtualAcID,"SERVICE FAILURE",responseStatus, dbConnectionMapLMS);
		    								  }
		    							  }
		    						    else
		    						    {  
		    						    	jsonObj.put("PC_ERR_CODE","SERVICE FAILURE");
		    						    	jsonObj.put("PC_ERROR_DESC",!serviceOutput.isEmpty()?serviceOutput:"ERROR OCCURED FROM SERVER. PLEASE TRY AGAIN LATER.");
		    						    	
		    						    	invoiceDetailsBo.saveEaseBuzzDtls("",requestId,serviceOutput,jsonObj,
		    						    			requestTime,responseTime,"2000000004",null,virtualAcID,"SERVICE FAILURE",responseStatus, dbConnectionMapLMS);
		    						    }
	    							}catch(Exception e) {
	    								log.info("Error occured in EasebuzzInvoiceSchedulerINV ::"+e.getMessage()+Arrays.toString(e.getStackTrace()));
	    							}
	    							
	    						}
	    					}	
	    				}
	    	    	 
	    	    	}
	    			
	    		}
	    			catch (Exception e) {
	    				log.info(" EasebuzzInvoiceSchedulerINV exception"+e.getMessage()); 
	    			}
	    			log.info(" EasebuzzInvoiceSchedulerINV End"); 
		 }
	

	private String getImageFromUrl(String url) {
		RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Resource> response = restTemplate.getForEntity(url, Resource.class);

        Resource resource = response.getBody();
        
        try (InputStream inputStream = resource.getInputStream();
                ByteArrayOutputStream buffer = new ByteArrayOutputStream()) {

               byte[] data = new byte[4096];
               int nRead;

               while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
                   buffer.write(data, 0, nRead);
               }

               byte [] bytecd= buffer.toByteArray();
        
              return new String (Base64.encodeBase64(bytecd));
	}catch(Exception e) {
		log.error("Error occured in EasebuzzInvoiceSchedulerINV  while calling ::"+url+"::"+e.getMessage()+Arrays.toString(e.getStackTrace()));
	}
        return "";
	}

}
