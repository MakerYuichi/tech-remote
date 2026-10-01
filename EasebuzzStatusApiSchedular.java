 /* HEADER INFO
+  File NAME 	: EasebuzzStatusApiSchedular.java
+  PURPOSE		: 
+  CREATED BY	: Narottam Biswal
+  CREATION DATE	: 
+  INITIAL VERSION : 1.0.0.0
+  **********************************************************************************************************************************
+  VERSION NO   UPDATED BY       	UPDATED ON      REASON FOR CHANGE
+  1.0.0.1		Sanchi Agarwal		1-Oct-2026		Initial Version
   **********************************************************************************************************************************
 */

package qc.common.servlet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TimerTask;

import org.apache.log4j.Logger;
import org.json.JSONObject;

import com.google.gson.Gson;

import qc.common.bean.CommonBean;
import qc.common.util.ServiceCallAction;
import qc.customer.model.bo.CustomerBO;
import qc.quotation.model.bo.InvoiceDetailsBO;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;


@SuppressWarnings("rawtypes")
public class EasebuzzStatusApiSchedular implements Job {

	protected static Logger log = Logger.getLogger(EasebuzzStatusApiSchedular.class);
	 static Locale locale = new Locale("en","US");
	 InvoiceDetailsBO invoiceDetailsBo=new InvoiceDetailsBO();
	 Map dbConnectionMap = new HashMap();
	 ResourceBundle commonApplicationResource = ResourceBundle.getBundle("resourceProperties.commonApplicationResource",locale);
	 CustomerBO customerBO = new CustomerBO();
	 
	 @SuppressWarnings("unchecked")
	 @Override
     public void execute(JobExecutionContext context) throws JobExecutionException {
		 log.info("Start - EasebuzzStatusApiSchedular");
		 try {
			 Map dbConnectionMapLMS =  CommonBean.getDBConnectionMap("dbConnection/DBConnectionMapLMS", locale);
 			Map returnMap=invoiceDetailsBo.getEaseBuzzStatusApi(dbConnectionMapLMS);
 			
 			String fromDate= returnMap.get("PC_FROM_DATE")!=null ?(String)returnMap.get("PC_FROM_DATE"):"";
 			String toDate= returnMap.get("PC_TO_DATE")!=null ?(String)returnMap.get("PC_TO_DATE"):"";
 			String pageSize= returnMap.get("PC_PAGE_SIZE")!=null ?(String)returnMap.get("PC_PAGE_SIZE"):"";
 			String ebuzzStatus= returnMap.get("PC_OUT_STATUS")!=null ?(String)returnMap.get("PC_OUT_STATUS"):"";
 			String statusMsg= returnMap.get("PC_OUT_MESSAGE")!=null ?(String)returnMap.get("PC_OUT_MESSAGE"):"";
 			log.info("startDate : "+fromDate+", "+"endDate : "+toDate);
 			LinkedHashMap requestMap = new LinkedHashMap();
 			String correlationId = "";
 			int pageNumber = 1;
 			boolean hasNext = true;
 			String reqType = "KARZA_EASEBUZZ_STATUS";
 			String requestUrl = commonApplicationResource.getString("EASEBUZZ_STATUS_URL");
 
 			while (hasNext) {
 				hasNext = false;
 			correlationId = reqType + "_" + Math.random();
 			log.debug("pageSize:" + pageSize + ", Page: " + pageNumber);
 			
 			LinkedHashMap requestHeaderMap = new LinkedHashMap();
 			requestHeaderMap.put("appId", commonApplicationResource.getString("EASEBUZZ_STATUS_APP_ID"));
 			requestHeaderMap.put("correlationId", correlationId);
 			requestHeaderMap.put("msgVersion", commonApplicationResource.getString("EASEBUZZ_STATUS_MSG_VERSION"));
			requestHeaderMap.put("token", commonApplicationResource.getString("EASEBUZZ_STATUS_TOKEN"));

			LinkedHashMap payloadMap = new LinkedHashMap();
			payloadMap.put("key","" );
			payloadMap.put("created_at", "");
			payloadMap.put("created_at_start", fromDate);//"2025-06-01"
			payloadMap.put("created_at_end", toDate);//"2025-07-14"
			payloadMap.put("current", ""+pageNumber); //1
			payloadMap.put("order_id", "");
			payloadMap.put("pageSize", pageSize);
			payloadMap.put("status", "");
			payloadMap.put("unique_transaction_reference", "");
			payloadMap.put("virtual_account_id", "");

			requestMap.put("header", requestHeaderMap);
			requestMap.put("payload", payloadMap);
			 
			//String requestUrl = commonApplicationResource.getString("EASEBUZZ_STATUS_URL");
			int timeOut = Integer.parseInt(commonApplicationResource.getString("API_SERVICE_LOGOUT_IN_SEC"));// 100;
			HashMap responseMap = new HashMap();
			
			String serviceOutput = ServiceCallAction.getResultString(requestMap, requestUrl, timeOut);
			
			if (serviceOutput != null) {
				// Insert into qt_karza_integration_log table
				Gson gson = new Gson();
				String requestJson = gson.toJson(requestMap);
				String responseJson = serviceOutput;
				log.debug(responseJson);
				String status = "";
				String message = "";
				JSONObject jsonObjLogTable = new JSONObject(serviceOutput.toString());
				JSONObject infoObjLogTable = jsonObjLogTable.has("errorInfo") ? jsonObjLogTable.getJSONObject("errorInfo")
						: null;
				log.debug(infoObjLogTable);
				if (infoObjLogTable != null) {
					if(infoObjLogTable.isNull("status"))
					{
						status = null;
						log.debug("inside null status: "+status);
					}
					else
					{
						status = infoObjLogTable.getString("status");
					}
					if(infoObjLogTable.isNull("message"))
					{
						message = null;
						log.debug("inside null message: "+message);
					}
					else
					{
						message = infoObjLogTable.getString("message");
					}
					log.info("EasebuzzStatusApiSchedular API responseJson status: "+status+" message: "+message);
					//status = infoObjLogTable.getString("status") != null ? infoObjLogTable.getString("status") : null;
					
					//message = infoObjLogTable.getString("message") != null ? infoObjLogTable.getString("message") : null;
				}

				//String insertStatus = customerBO.saveKarzaReqLog(pageSize, reqType, requestJson, responseJson, status,message, dbConnectionMap);
				//log.debug("insertStatus:" + insertStatus);
				if (!serviceOutput.isEmpty() && !serviceOutput.equalsIgnoreCase(null)
						&& !serviceOutput.equalsIgnoreCase("") && !serviceOutput.startsWith("Error,")) {
					JSONObject jsonObj = new JSONObject(serviceOutput.toString());
					JSONObject errorInfoObj = jsonObj.has("errorInfo") ? jsonObj.getJSONObject("errorInfo") : null;

					if (errorInfoObj != null && errorInfoObj.getString("status") != null
							&& errorInfoObj.getString("status").equalsIgnoreCase("SUCCESS")) {
						JSONObject payloadObj = jsonObj.getJSONObject("payload"); // 
						JSONObject headerObj = jsonObj.getJSONObject("header");
						JSONObject resultObj = payloadObj.has("result") ? payloadObj.getJSONObject("result") : null;
						JSONObject dataObj = payloadObj.getJSONObject("data");
						JSONObject paginationObj = dataObj.getJSONObject("pagination");
						 hasNext = paginationObj.getBoolean("hasNext");
						String hasNextFlag = hasNext ? "Y" : "N";

						String responseStatus = errorInfoObj.getString("status");
                        
						JSONObject jsonObjectresponse = payloadObj;
			            String jsonResponseString = jsonObjectresponse.toString(4);//method pretty-prints the JSON with an indentation of 4 spaces.
						Map saveStatus=	invoiceDetailsBo.saveEaseBuzzApiStatus(jsonResponseString,hasNextFlag, dbConnectionMapLMS);

						log.info("saveStatus : "+saveStatus.get("PC_OUT_MESSAGE"));
						responseMap.put("requestType", reqType);
						responseMap.put("responseStatus", responseStatus);

						 //pageNumber++; // Next page
					} else {

						responseMap.put("requestType", reqType);
						responseMap.put("responseStatus", "Failure");
						responseMap.put("responseMsg", errorInfoObj.getString("message"));
						hasNext=false;
						break;
					}

				} else {
					responseMap.put("requestType", reqType);
					responseMap.put("responseStatus", "Failure");
					responseMap.put("responseMsg", "Response not generated, Please try again.");
				}
			}
			
			pageNumber++; // Next page
			log.info("page number count :" + pageNumber);
		 }
		 }
		 catch (Exception e) {
				log.info(" EasebuzzInvoiceSchedulerINV exception"+e.getMessage()); 
			}
		 log.info("end - EasebuzzStatusApiSchedular");
		     }
	 
}
