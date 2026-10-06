/*<!-- #  HEADER INFO
#  
#  **********************************************************************************************************************************
#  VERSION NO   	UPDATED BY       UPDATED ON      REASON FOR CHANGE
#  1.0.0.1	   	Priyanka Soni 	 17-Jan-2022	 Implementation of Karza API,
#  1.0.0.2		Priyanka Soni 	 11-Mar-2022	 SEZ value at the address level
#  1.0.0.3		Priyanka Soni 	 26-Apr-2022	 Address parking incorrect from API (Also add Populating address in address string)
#  1.0.0.4		Hemant Kumar 	 26-Jul-2022	 Vahan API Integration CR
#  1.0.0.5		Priyanka Soni 	 23-Jan-2023	 Aadhar OTP Verification: Bad request (Need to change request json structure)
#  1.0.0.6      Nalin Kumar Jena      22-FEB-2024             CR-Passport No varification
#  1.0.0.7      Narottam Biswal       22-FEB-2024             CR-DL varification
#  1.0.0.8      Tanisha Agarwal       22-FEB-2024             CR-Voter ID varification
#  1.0.0.9      Ravi Shankar      28-FEB-2024    CR-Udyam Registration varification
#  1.0.0.10      Ravi Shankar      06-MAY-2025    Address Split API calling multiple times.
#  1.0.0.11      Narottam Biswal   26-Nov-2025    UCIC changes
#  1.0.0.12      Narottam Biswal   05-Jan-2026    UCIC constitition added in json request
#  1.0.0.13      Ravi Shankar		12-05-2026		HPT vahan integration
#  1.0.0.14      Nalin Kumar Jena  14-Apr-2026        UCIC for guarantor, Authorised signatory and Beneficiary Owner
#  1.0.0.15      Sanchi Agarwal    05-Oct-2026        Vehicle RC Authentication scheduler (branch 6793)
#  ********************************************************************************************************************************** -->*/

package qc.common.controller.action;

import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.json.JSONArray;
import org.json.JSONObject;

import com.google.gson.Gson;

import qc.collection.util.Utils;
import qc.common.util.ServiceCallAction;
import qc.customer.model.bo.CustomerBO;
import qc.los.model.bo.DisbursalActionBO;
import qc.quotation.model.bo.InvoiceDetailsBO;
import qc.vahan.bo.VahanBO;

public class IBSCallAction extends AuthAction {
	static Logger log = Logger.getLogger(IBSCallAction.class);
	Locale locale = new Locale("en", "US");
	ResourceBundle commonApplicationResource = ResourceBundle.getBundle("resourceProperties.commonApplicationResource",
			locale);
	CustomerBO customerBO = new CustomerBO();
	Map dbConnectionMap = new HashMap();
	private final static int ADDRESS_LENGTH = 51;
	private final static int FULL_ADDRESS_LENGTH = 251;
	
	public ActionForward processUserRequest(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		log.info("Start");
		ActionForward forward = null;
		String actionPerformed = request.getParameter("actionPerformed");
		boolean invalidAction = false;
		invalidAction = Utils.nullOrBlank(actionPerformed);
		if (!Utils.nullOrBlank(actionPerformed)) {
			if (actionPerformed.equalsIgnoreCase("panVerifier")) {
				forward = panVerifier(mapping, form, request, response);
			} else if (actionPerformed.equalsIgnoreCase("gstFromPan")) {
				forward = gstFromPan(mapping, form, request, response);
			} 
			else if (actionPerformed.equalsIgnoreCase("generateAadhaarOTP")) {
				forward = generateAadhaarOTP(mapping, form, request, response);
			}
			else if (actionPerformed.equalsIgnoreCase("generateAadhaarFile")) {
				forward = generateAadhaarFile(mapping, form, request, response);
			}
			//1.0.0.6 Start
			else if (actionPerformed.equalsIgnoreCase("passportNoVerifier")) {
				forward = passportNoVerifier(mapping, form, request, response);
			}
			//1.0.0.6 End
			//start 1.0.0.7
			else if (actionPerformed.equalsIgnoreCase("dlVerifier")) {
				forward = dlVerifier(mapping, form, request, response);
			}
			//end 1.0.0.7
			
			//1.0.0.8 Start
			else if (actionPerformed.equalsIgnoreCase("verifyVoterId")) {
				forward = verifyVoterId(mapping, form, request, response);
			}
			//1.0.0.8 End
			else if(actionPerformed.equalsIgnoreCase("validateUdyamNo")) {//1.0.0.9
				forward = validateUdyamNo(mapping, form, request, response);
			}//1.0.0.9


			else {
				invalidAction = true;
			}
		}
		return forward;
	}

	public ActionForward panVerifier(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		List list = new ArrayList();
		URL url = null;
		HttpURLConnection httpsConnection = null;
		HttpSession session = request.getSession();
		ServletContext ctx = session.getServletContext();
		dbConnectionMap = (HashMap) ctx.getAttribute("dbConnectionMapLOS");
		LinkedHashMap requestMap = new LinkedHashMap();
		String correlationId = "";
		String reqType = "KARZA_PAN";
		String panNo = "";
		try {
			panNo = request.getParameter("panNoValue");
			log.debug("panNo:" + panNo);

			LinkedHashMap requestHeaderMap = new LinkedHashMap();
			correlationId = reqType + "_" + Math.random();
			log.debug("PANCorrelationId:" + correlationId);
			requestHeaderMap.put("msgVersion", commonApplicationResource.getString("MSG_VERSION"));
			requestHeaderMap.put("appId", commonApplicationResource.getString("APP_ID"));
			requestHeaderMap.put("correlationId", correlationId);
			requestHeaderMap.put("token", commonApplicationResource.getString("TOKEN"));

			LinkedHashMap payloadMap = new LinkedHashMap();
			payloadMap.put("pan", panNo);
			payloadMap.put("consent", "Y");

			requestMap.put("header", requestHeaderMap);
			requestMap.put("payload", payloadMap);

		} catch (Exception ex) {
			log.error(ex.getMessage());
		}
		String requestUrl = commonApplicationResource.getString("PAN_VERI_URL");
		int timeOut = Integer.parseInt(commonApplicationResource.getString("API_SERVICE_LOGOUT_IN_SEC"));// 100;
		HashMap responseMap = new HashMap();
		try {
			 String serviceOutput = ServiceCallAction.getResultString(requestMap, requestUrl, timeOut);
			 //serviceOutput="{\"header\":{\"msgVersion\":\"1.0\",\"appId\":\"KARZA\",\"correlationId\":\"KARZA_PAN_0.6738534285540991\",\"token\":\"37f21104-ec7b-4c64-b7d7-600550597d9b\"},\"msgInfo\":{\"code\":\"200\",\"status\":\"SUCCESS\",\"message\":\"Valid Authentication\"},\"payload\":{\"result\":{\"name\":\"TATA CONSULTANCY SERVICES LIMITED\"},\"byteArray\":null,\"pdfPath\":null,\"status_code\":101,\"request_id\":\"704e3fb6-c8fc-4754-8f51-6b91406b5763\",\"status_msg\":\"Valid Authentication\"}}"; 
			 if (serviceOutput != null) {
				// Insert into qt_karza_integration_log table
				Gson gson = new Gson();
				String requestJson = gson.toJson(requestMap);
				String responseJson = serviceOutput;
				log.debug(responseJson);
				String status = "";
				String message = "";
				JSONObject jsonObjLogTable = new JSONObject(serviceOutput.toString());
				JSONObject infoObjLogTable = jsonObjLogTable.has("msgInfo") ? jsonObjLogTable.getJSONObject("msgInfo")
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
						status = infoObjLogTable.getString("message");
					}
					
					//status = infoObjLogTable.getString("status") != null ? infoObjLogTable.getString("status") : null;
					
					//message = infoObjLogTable.getString("message") != null ? infoObjLogTable.getString("message") : null;
				}

				String insertStatus = customerBO.saveKarzaReqLog(panNo, reqType, requestJson, responseJson, status,
					message, dbConnectionMap);
				log.debug("insertStatus:" + insertStatus);
				if (!serviceOutput.isEmpty() && !serviceOutput.equalsIgnoreCase(null)
						&& !serviceOutput.equalsIgnoreCase("") && !serviceOutput.startsWith("Error,")) {
					JSONObject jsonObj = new JSONObject(serviceOutput.toString());
					JSONObject errorInfoObj = jsonObj.has("msgInfo") ? jsonObj.getJSONObject("msgInfo") : null;

					if (errorInfoObj != null && errorInfoObj.getString("status") != null
							&& errorInfoObj.getString("status").equalsIgnoreCase("SUCCESS")) {
						JSONObject payloadObj = jsonObj.getJSONObject("payload"); // datainfo=
						JSONObject headerObj = jsonObj.getJSONObject("header");
						JSONObject resultObj = payloadObj.has("result") ? payloadObj.getJSONObject("result") : null;

						String responseStatus = errorInfoObj.getString("status");
						correlationId = headerObj.getString("correlationId");

						String name = resultObj.getString("name");

						responseMap.put("correlationId", correlationId);
						responseMap.put("requestType", reqType);
						responseMap.put("responseStatus", responseStatus);

						responseMap.put("name", name);

					} else {

						responseMap.put("transId", correlationId);
						responseMap.put("requestType", reqType);
						responseMap.put("responseStatus", "Failure");
						responseMap.put("responseMsg", errorInfoObj.getString("message"));
					}

				} else {
					responseMap.put("transId", correlationId);
					responseMap.put("requestType", reqType);
					responseMap.put("responseStatus", "Failure");
					responseMap.put("responseMsg", "Response not generated, Please try again.");
				}
			}

		} catch (Exception ex) {
			responseMap.put("responseStatus", "Failure");
			responseMap.put("responseMsg", "Please try again, Connect to Admin Team");
			log.info("Exception:" + ex.getMessage());
		}
		try {
			Gson gson = new Gson();
			PrintWriter out = response.getWriter();
			out.print(gson.toJson(responseMap));
			out.flush();
			out.close();
		} catch (Exception ex) {

		}
		return null;
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward gstFromPan(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		List list = new ArrayList();
		URL url = null;
		HttpURLConnection httpsConnection = null;
		HttpSession session = request.getSession();
		ServletContext ctx = session.getServletContext();
		dbConnectionMap = (HashMap) ctx.getAttribute("dbConnectionMapLOS");
		LinkedHashMap requestMap = new LinkedHashMap();
		String correlationId = "";
		String reqType = "KARZA_GST";
		String panNo = "";
		try {
			panNo = request.getParameter("panNoValue");
			log.debug("gstFromPan --> panNo:" + panNo);

			LinkedHashMap requestHeaderMap = new LinkedHashMap();
			correlationId = reqType + "_" + Math.random();
			log.debug("GSTCorrelationId:" + correlationId);
			requestHeaderMap.put("msgVersion", commonApplicationResource.getString("MSG_VERSION"));
			requestHeaderMap.put("appId", commonApplicationResource.getString("APP_ID"));
			requestHeaderMap.put("correlationId", correlationId);
			requestHeaderMap.put("token", commonApplicationResource.getString("TOKEN"));

			LinkedHashMap payloadMap = new LinkedHashMap();
			payloadMap.put("pan", panNo);
			payloadMap.put("consent", "Y");

			requestMap.put("header", requestHeaderMap);
			requestMap.put("payload", payloadMap);

		} catch (Exception ex) {
			log.error(ex.getMessage());
		}
		String requestUrl = commonApplicationResource.getString("GST_FROM_PAN_URL");
		int timeOut = Integer.parseInt(commonApplicationResource.getString("API_SERVICE_LOGOUT_IN_SEC"));// 100;
		HashMap<String, Object> responseMap = new HashMap<>();

		String locality = "";
		String building = "";
		String city = "";
		long pin = 0;
		String district = "";
		String floor = "";
		String house = "";
		String state = "";
		String complex = "";
		String landmark = "";
		String untagged = "";
		String careOf = "";
		String street = "";
		String gstAddrFlag = "N";
		String sez_value = "";
		String sez_flag = "";
		boolean isSplitAddressAPICalled=false;//1.0.0.10
		try {
			String serviceOutput = ServiceCallAction.getResultString(requestMap, requestUrl, timeOut);
		//	serviceOutput="{\"header\":{\"msgVersion\":\"1.0\",\"appId\":\"KARZA\",\"correlationId\":\"KARZA_GST_0.06534438706668044\",\"token\":\"37f21104-ec7b-4c64-b7d7-600550597d9b\"},\"msgInfo\":{\"code\":\"200\",\"status\":\"SUCCESS\",\"message\":\"Valid Authentication\"},\"payload\":{\"result\":[{\"emailId\":\"\",\"applicationStatus\":\"\",\"mobNum\":\"\",\"pan\":\"AAACR4849R\",\"gstinRefId\":\"\",\"regType\":\"\",\"authStatus\":\"Active\",\"gstinId\":\"09AAACR4849R1ZJ\",\"registrationName\":\"\",\"tinNumber\":\"\"},{\"emailId\":\"\",\"applicationStatus\":\"\",\"mobNum\":\"\",\"pan\":\"AAACR4849R\",\"gstinRefId\":\"\",\"regType\":\"\",\"authStatus\":\"Active\",\"gstinId\":\"33AAACR4849R4ZP\",\"registrationName\":\"\",\"tinNumber\":\"\"},{\"emailId\":\"\",\"applicationStatus\":\"\",\"mobNum\":\"\",\"pan\":\"AAACR4849R\",\"gstinRefId\":\"\",\"regType\":\"\",\"authStatus\":\"Active\",\"gstinId\":\"24AAACR4849R4ZO\",\"registrationName\":\"\",\"tinNumber\":\"\"},{\"emailId\":\"\",\"applicationStatus\":\"\",\"mobNum\":\"\",\"pan\":\"AAACR4849R\",\"gstinRefId\":\"\",\"regType\":\"\",\"authStatus\":\"Active\",\"gstinId\":\"04AAACR4849R2ZS\",\"registrationName\":\"\",\"tinNumber\":\"\"},{\"emailId\":\"\",\"applicationStatus\":\"\",\"mobNum\":\"\",\"pan\":\"AAACR4849R\",\"gstinRefId\":\"\",\"regType\":\"\",\"authStatus\":\"Active\",\"gstinId\":\"06AAACR4849R2ZO\",\"registrationName\":\"\",\"tinNumber\":\"\"},{\"emailId\":\"\",\"applicationStatus\":\"\",\"mobNum\":\"\",\"pan\":\"AAACR4849R\",\"gstinRefId\":\"\",\"regType\":\"\",\"authStatus\":\"Active\",\"gstinId\":\"24AAACR4849R3ZP\",\"registrationName\":\"\",\"tinNumber\":\"\"},{\"emailId\":\"\",\"applicationStatus\":\"\",\"mobNum\":\"\",\"pan\":\"AAACR4849R\",\"gstinRefId\":\"\",\"regType\":\"\",\"authStatus\":\"Active\",\"gstinId\":\"21AAACR4849R1ZX\",\"registrationName\":\"\",\"tinNumber\":\"\"},{\"emailId\":\"\",\"applicationStatus\":\"\",\"mobNum\":\"\",\"pan\":\"AAACR4849R\",\"gstinRefId\":\"\",\"regType\":\"\",\"authStatus\":\"Active\",\"gstinId\":\"04AAACR4849R3ZR\",\"registrationName\":\"\",\"tinNumber\":\"\"},{\"emailId\":\"\",\"applicationStatus\":\"\",\"mobNum\":\"\",\"pan\":\"AAACR4849R\",\"gstinRefId\":\"\",\"regType\":\"\",\"authStatus\":\"Active\",\"gstinId\":\"35AAACR4849R1ZO\",\"registrationName\":\"\",\"tinNumber\":\"\"},{\"emailId\":\"\",\"applicationStatus\":\"\",\"mobNum\":\"\",\"pan\":\"AAACR4849R\",\"gstinRefId\":\"\",\"regType\":\"\",\"authStatus\":\"Active\",\"gstinId\":\"23AAACR4849R2ZS\",\"registrationName\":\"\",\"tinNumber\":\"\"},{\"emailId\":\"\",\"applicationStatus\":\"\",\"mobNum\":\"\",\"pan\":\"AAACR4849R\",\"gstinRefId\":\"\",\"regType\":\"\",\"authStatus\":\"Active\",\"gstinId\":\"22AAACR4849R1ZV\",\"registrationName\":\"\",\"tinNumber\":\"\"},{\"emailId\":\"\",\"applicationStatus\":\"\",\"mobNum\":\"\",\"pan\":\"AAACR4849R\",\"gstinRefId\":\"\",\"regType\":\"\",\"authStatus\":\"Active\",\"gstinId\":\"29AAACR4849R2ZG\",\"registrationName\":\"\",\"tinNumber\":\"\"},{\"emailId\":\"\",\"applicationStatus\":\"\",\"mobNum\":\"\",\"pan\":\"AAACR4849R\",\"gstinRefId\":\"\",\"regType\":\"\",\"authStatus\":\"Active\",\"gstinId\":\"03AAACR4849R2ZU\",\"registrationName\":\"\",\"tinNumber\":\"\"},{\"emailId\":\"\",\"applicationStatus\":\"\",\"mobNum\":\"\",\"pan\":\"AAACR4849R\",\"gstinRefId\":\"\",\"regType\":\"\",\"authStatus\":\"Active\",\"gstinId\":\"23AAACR4849R4ZQ\",\"registrationName\":\"\",\"tinNumber\":\"\"},{\"emailId\":\"\",\"applicationStatus\":\"\",\"mobNum\":\"\",\"pan\":\"AAACR4849R\",\"gstinRefId\":\"\",\"regType\":\"\",\"authStatus\":\"Active\",\"gstinId\":\"02AAACR4849R2ZW\",\"registrationName\":\"\",\"tinNumber\":\"\"},{\"emailId\":\"\",\"applicationStatus\":\"\",\"mobNum\":\"\",\"pan\":\"AAACR4849R\",\"gstinRefId\":\"\",\"regType\":\"\",\"authStatus\":\"Inactive\",\"gstinId\":\"04AAACR4849R1ZT\",\"registrationName\":\"\",\"tinNumber\":\"\"},{\"emailId\":\"\",\"applicationStatus\":\"\",\"mobNum\":\"\",\"pan\":\"AAACR4849R\",\"gstinRefId\":\"\",\"regType\":\"\",\"authStatus\":\"Active\",\"gstinId\":\"19AAACR4849R3ZG\",\"registrationName\":\"\",\"tinNumber\":\"\"},{\"emailId\":\"\",\"applicationStatus\":\"\",\"mobNum\":\"\",\"pan\":\"AAACR4849R\",\"gstinRefId\":\"\",\"regType\":\"\",\"authStatus\":\"Active\",\"gstinId\":\"13AAACR4849R1ZU\",\"registrationName\":\"\",\"tinNumber\":\"\"},{\"emailId\":\"\",\"applicationStatus\":\"\",\"mobNum\":\"\",\"pan\":\"AAACR4849R\",\"gstinRefId\":\"\",\"regType\":\"\",\"authStatus\":\"Active\",\"gstinId\":\"12AAACR4849R1ZW\",\"registrationName\":\"\",\"tinNumber\":\"\"},{\"emailId\":\"\",\"applicationStatus\":\"\",\"mobNum\":\"\",\"pan\":\"AAACR4849R\",\"gstinRefId\":\"\",\"regType\":\"\",\"authStatus\":\"Active\",\"gstinId\":\"36AAACR4849R4ZJ\",\"registrationName\":\"\",\"tinNumber\":\"\"},{\"emailId\":\"\",\"applicationStatus\":\"\",\"mobNum\":\"\",\"pan\":\"AAACR4849R\",\"gstinRefId\":\"\",\"regType\":\"\",\"authStatus\":\"Active\",\"gstinId\":\"27AAACR4849R1CW\",\"registrationName\":\"\",\"tinNumber\":\"\"},{\"emailId\":\"\",\"applicationStatus\":\"\",\"mobNum\":\"\",\"pan\":\"AAACR4849R\",\"gstinRefId\":\"\",\"regType\":\"\",\"authStatus\":\"Active\",\"gstinId\":\"18AAACR4849R1ZK\",\"registrationName\":\"\",\"tinNumber\":\"\"},{\"emailId\":\"\",\"applicationStatus\":\"\",\"mobNum\":\"\",\"pan\":\"AAACR4849R\",\"gstinRefId\":\"\",\"regType\":\"\",\"authStatus\":\"Active\",\"gstinId\":\"33AAACR4849R3ZQ\",\"registrationName\":\"\",\"tinNumber\":\"\"},{\"emailId\":\"\",\"applicationStatus\":\"\",\"mobNum\":\"\",\"pan\":\"AAACR4849R\",\"gstinRefId\":\"\",\"regType\":\"\",\"authStatus\":\"Active\",\"gstinId\":\"21AAACR4849R2ZW\",\"registrationName\":\"\",\"tinNumber\":\"\"},{\"emailId\":\"\",\"applicationStatus\":\"\",\"mobNum\":\"\",\"pan\":\"AAACR4849R\",\"gstinRefId\":\"\",\"regType\":\"\",\"authStatus\":\"Inactive\",\"gstinId\":\"10AAACR4849R2ZZ\",\"registrationName\":\"\",\"tinNumber\":\"\"},{\"emailId\":\"\",\"applicationStatus\":\"\",\"mobNum\":\"\",\"pan\":\"AAACR4849R\",\"gstinRefId\":\"\",\"regType\":\"\",\"authStatus\":\"Active\",\"gstinId\":\"32AAACR4849R4ZR\",\"registrationName\":\"\",\"tinNumber\":\"\"},{\"emailId\":\"\",\"applicationStatus\":\"\",\"mobNum\":\"\",\"pan\":\"AAACR4849R\",\"gstinRefId\":\"\",\"regType\":\"\",\"authStatus\":\"Active\",\"gstinId\":\"23AAACR4849R3ZR\",\"registrationName\":\"\",\"tinNumber\":\"\"},{\"emailId\":\"\",\"applicationStatus\":\"\",\"mobNum\":\"\",\"pan\":\"AAACR4849R\",\"gstinRefId\":\"\",\"regType\":\"\",\"authStatus\":\"Active\",\"gstinId\":\"36AAACR4849R1ZM\",\"registrationName\":\"\",\"tinNumber\":\"\"},{\"emailId\":\"\",\"applicationStatus\":\"\",\"mobNum\":\"\",\"pan\":\"AAACR4849R\",\"gstinRefId\":\"\",\"regType\":\"\",\"authStatus\":\"Active\",\"gstinId\":\"09AAACR4849R4ZG\",\"registrationName\":\"\",\"tinNumber\":\"\"},{\"emailId\":\"\",\"applicationStatus\":\"\",\"mobNum\":\"\",\"pan\":\"AAACR4849R\",\"gstinRefId\":\"\",\"regType\":\"\",\"authStatus\":\"Active\",\"gstinId\":\"21AAACR4849R3ZV\",\"registrationName\":\"\",\"tinNumber\":\"\"},{\"emailId\":\"\",\"applicationStatus\":\"\",\"mobNum\":\"\",\"pan\":\"AAACR4849R\",\"gstinRefId\":\"\",\"regType\":\"\",\"authStatus\":\"Inactive\",\"gstinId\":\"32AAACR4849R5ZQ\",\"registrationName\":\"\",\"tinNumber\":\"\"},{\"emailId\":\"\",\"applicationStatus\":\"\",\"mobNum\":\"\",\"pan\":\"AAACR4849R\",\"gstinRefId\":\"\",\"regType\":\"\",\"authStatus\":\"Active\",\"gstinId\":\"36AAACR4849R7ZG\",\"registrationName\":\"\",\"tinNumber\":\"\"},{\"emailId\":\"\",\"applicationStatus\":\"\",\"mobNum\":\"\",\"pan\":\"AAACR4849R\",\"gstinRefId\":\"\",\"regType\":\"\",\"authStatus\":\"Active\",\"gstinId\":\"32AAACR4849R3ZS\",\"registrationName\":\"\",\"tinNumber\":\"\"},{\"emailId\":\"\",\"applicationStatus\":\"\",\"mobNum\":\"\",\"pan\":\"AAACR4849R\",\"gstinRefId\":\"\",\"regType\":\"\",\"authStatus\":\"Active\",\"gstinId\":\"27AAACR4849R3ZJ\",\"registrationName\":\"\",\"tinNumber\":\"\"},{\"emailId\":\"\",\"applicationStatus\":\"\",\"mobNum\":\"\",\"pan\":\"AAACR4849R\",\"gstinRefId\":\"\",\"regType\":\"\",\"authStatus\":\"Active\",\"gstinId\":\"36AAACR4849R2ZL\",\"registrationName\":\"\",\"tinNumber\":\"\"},{\"emailId\":\"\",\"applicationStatus\":\"\",\"mobNum\":\"\",\"pan\":\"AAACR4849R\",\"gstinRefId\":\"\",\"regType\":\"\",\"authStatus\":\"Inactive\",\"gstinId\":\"03AAACR4849R1ZV\",\"registrationName\":\"\",\"tinNumber\":\"\"},{\"emailId\":\"\",\"applicationStatus\":\"\",\"mobNum\":\"\",\"pan\":\"AAACR4849R\",\"gstinRefId\":\"\",\"regType\":\"\",\"authStatus\":\"Active\",\"gstinId\":\"17AAACR4849R1ZM\",\"registrationName\":\"\",\"tinNumber\":\"\"},{\"emailId\":\"\",\"applicationStatus\":\"\",\"mobNum\":\"\",\"pan\":\"AAACR4849R\",\"gstinRefId\":\"\",\"regType\":\"\",\"authStatus\":\"Active\",\"gstinId\":\"24AAACR4849R2ZQ\",\"registrationName\":\"\",\"tinNumber\":\"\"},{\"emailId\":\"\",\"applicationStatus\":\"\",\"mobNum\":\"\",\"pan\":\"AAACR4849R\",\"gstinRefId\":\"\",\"regType\":\"\",\"authStatus\":\"Active\",\"gstinId\":\"11AAACR4849R1ZY\",\"registrationName\":\"\",\"tinNumber\":\"\"},{\"emailId\":\"\",\"applicationStatus\":\"\",\"mobNum\":\"\",\"pan\":\"AAACR4849R\",\"gstinRefId\":\"\",\"regType\":\"\",\"authStatus\":\"Active\",\"gstinId\":\"32AAACR4849R2ZT\",\"registrationName\":\"\",\"tinNumber\":\"\"},{\"emailId\":\"\",\"applicationStatus\":\"\",\"mobNum\":\"\",\"pan\":\"AAACR4849R\",\"gstinRefId\":\"\",\"regType\":\"\",\"authStatus\":\"Active\",\"gstinId\":\"08AAACR4849R1ZL\",\"registrationName\":\"\",\"tinNumber\":\"\"},{\"emailId\":\"\",\"applicationStatus\":\"\",\"mobNum\":\"\",\"pan\":\"AAACR4849R\",\"gstinRefId\":\"\",\"regType\":\"\",\"authStatus\":\"Inactive\",\"gstinId\":\"36AAACR4849R5ZI\",\"registrationName\":\"\",\"tinNumber\":\"\"},{\"emailId\":\"\",\"applicationStatus\":\"\",\"mobNum\":\"\",\"pan\":\"AAACR4849R\",\"gstinRefId\":\"\",\"regType\":\"\",\"authStatus\":\"Active\",\"gstinId\":\"36AAACR4849R1CX\",\"registrationName\":\"\",\"tinNumber\":\"\"},{\"emailId\":\"\",\"applicationStatus\":\"\",\"mobNum\":\"\",\"pan\":\"AAACR4849R\",\"gstinRefId\":\"\",\"regType\":\"\",\"authStatus\":\"Inactive\",\"gstinId\":\"37AAACR4849R1ZK\",\"registrationName\":\"\",\"tinNumber\":\"\"},{\"emailId\":\"\",\"applicationStatus\":\"\",\"mobNum\":\"\",\"pan\":\"AAACR4849R\",\"gstinRefId\":\"\",\"regType\":\"\",\"authStatus\":\"Active\",\"gstinId\":\"06AAACR4849R1ZP\",\"registrationName\":\"\",\"tinNumber\":\"\"},{\"emailId\":\"\",\"applicationStatus\":\"\",\"mobNum\":\"\",\"pan\":\"AAACR4849R\",\"gstinRefId\":\"\",\"regType\":\"\",\"authStatus\":\"Active\",\"gstinId\":\"27AAACR4849R4ZI\",\"registrationName\":\"\",\"tinNumber\":\"\"},{\"emailId\":\"\",\"applicationStatus\":\"\",\"mobNum\":\"\",\"pan\":\"AAACR4849R\",\"gstinRefId\":\"\",\"regType\":\"\",\"authStatus\":\"Active\",\"gstinId\":\"33AAACR4849R6ZN\",\"registrationName\":\"\",\"tinNumber\":\"\"},{\"emailId\":\"\",\"applicationStatus\":\"\",\"mobNum\":\"\",\"pan\":\"AAACR4849R\",\"gstinRefId\":\"\",\"regType\":\"\",\"authStatus\":\"Active\",\"gstinId\":\"09AAACR4849R3ZH\",\"registrationName\":\"\",\"tinNumber\":\"\"},{\"emailId\":\"\",\"applicationStatus\":\"\",\"mobNum\":\"\",\"pan\":\"AAACR4849R\",\"gstinRefId\":\"\",\"regType\":\"\",\"authStatus\":\"Active\",\"gstinId\":\"20AAACR4849R1CA\",\"registrationName\":\"\",\"tinNumber\":\"\"},{\"emailId\":\"\",\"applicationStatus\":\"\",\"mobNum\":\"\",\"pan\":\"AAACR4849R\",\"gstinRefId\":\"\",\"regType\":\"\",\"authStatus\":\"Active\",\"gstinId\":\"37AAACR4849R1CV\",\"registrationName\":\"\",\"tinNumber\":\"\"},{\"emailId\":\"\",\"applicationStatus\":\"\",\"mobNum\":\"\",\"pan\":\"AAACR4849R\",\"gstinRefId\":\"\",\"regType\":\"\",\"authStatus\":\"Active\",\"gstinId\":\"09AAACR4849R1CU\",\"registrationName\":\"\",\"tinNumber\":\"\"},{\"emailId\":\"\",\"applicationStatus\":\"\",\"mobNum\":\"\",\"pan\":\"AAACR4849R\",\"gstinRefId\":\"\",\"regType\":\"\",\"authStatus\":\"Active\",\"gstinId\":\"29AAACR4849R1ZH\",\"registrationName\":\"\",\"tinNumber\":\"\"},{\"emailId\":\"\",\"applicationStatus\":\"\",\"mobNum\":\"\",\"pan\":\"AAACR4849R\",\"gstinRefId\":\"\",\"regType\":\"\",\"authStatus\":\"Active\",\"gstinId\":\"30AAACR4849R3ZW\",\"registrationName\":\"\",\"tinNumber\":\"\"},{\"emailId\":\"\",\"applicationStatus\":\"\",\"mobNum\":\"\",\"pan\":\"AAACR4849R\",\"gstinRefId\":\"\",\"regType\":\"\",\"authStatus\":\"Active\",\"gstinId\":\"14AAACR4849R1ZS\",\"registrationName\":\"\",\"tinNumber\":\"\"},{\"emailId\":\"\",\"applicationStatus\":\"\",\"mobNum\":\"\",\"pan\":\"AAACR4849R\",\"gstinRefId\":\"\",\"regType\":\"\",\"authStatus\":\"Inactive\",\"gstinId\":\"02AAACR4849R1ZX\",\"registrationName\":\"\",\"tinNumber\":\"\"},{\"emailId\":\"\",\"applicationStatus\":\"\",\"mobNum\":\"\",\"pan\":\"AAACR4849R\",\"gstinRefId\":\"\",\"regType\":\"\",\"authStatus\":\"Active\",\"gstinId\":\"27AAACR4849R1ZL\",\"registrationName\":\"\",\"tinNumber\":\"\"},{\"emailId\":\"\",\"applicationStatus\":\"\",\"mobNum\":\"\",\"pan\":\"AAACR4849R\",\"gstinRefId\":\"\",\"regType\":\"\",\"authStatus\":\"Active\",\"gstinId\":\"34AAACR4849R1ZQ\",\"registrationName\":\"\",\"tinNumber\":\"\"},{\"emailId\":\"\",\"applicationStatus\":\"\",\"mobNum\":\"\",\"pan\":\"AAACR4849R\",\"gstinRefId\":\"\",\"regType\":\"\",\"authStatus\":\"Active\",\"gstinId\":\"09AAACR4849R2ZI\",\"registrationName\":\"\",\"tinNumber\":\"\"},{\"emailId\":\"\",\"applicationStatus\":\"\",\"mobNum\":\"\",\"pan\":\"AAACR4849R\",\"gstinRefId\":\"\",\"regType\":\"\",\"authStatus\":\"Active\",\"gstinId\":\"15AAACR4849R1ZQ\",\"registrationName\":\"\",\"tinNumber\":\"\"},{\"emailId\":\"\",\"applicationStatus\":\"\",\"mobNum\":\"\",\"pan\":\"AAACR4849R\",\"gstinRefId\":\"\",\"regType\":\"\",\"authStatus\":\"Active\",\"gstinId\":\"37AAACR4849R2ZJ\",\"registrationName\":\"\",\"tinNumber\":\"\"},{\"emailId\":\"\",\"applicationStatus\":\"\",\"mobNum\":\"\",\"pan\":\"AAACR4849R\",\"gstinRefId\":\"\",\"regType\":\"\",\"authStatus\":\"Active\",\"gstinId\":\"33AAACR4849R1C3\",\"registrationName\":\"\",\"tinNumber\":\"\"},{\"emailId\":\"\",\"applicationStatus\":\"\",\"mobNum\":\"\",\"pan\":\"AAACR4849R\",\"gstinRefId\":\"\",\"regType\":\"\",\"authStatus\":\"Active\",\"gstinId\":\"29AAACR4849R4ZE\",\"registrationName\":\"\",\"tinNumber\":\"\"},{\"emailId\":\"\",\"applicationStatus\":\"\",\"mobNum\":\"\",\"pan\":\"AAACR4849R\",\"gstinRefId\":\"\",\"regType\":\"\",\"authStatus\":\"Active\",\"gstinId\":\"27AAACR4849R2ZK\",\"registrationName\":\"\",\"tinNumber\":\"\"},{\"emailId\":\"\",\"applicationStatus\":\"\",\"mobNum\":\"\",\"pan\":\"AAACR4849R\",\"gstinRefId\":\"\",\"regType\":\"\",\"authStatus\":\"Active\",\"gstinId\":\"07AAACR4849R1ZN\",\"registrationName\":\"\",\"tinNumber\":\"\"},{\"emailId\":\"\",\"applicationStatus\":\"\",\"mobNum\":\"\",\"pan\":\"AAACR4849R\",\"gstinRefId\":\"\",\"regType\":\"\",\"authStatus\":\"Inactive\",\"gstinId\":\"19AAACR4849R4ZF\",\"registrationName\":\"\",\"tinNumber\":\"\"},{\"emailId\":\"\",\"applicationStatus\":\"\",\"mobNum\":\"\",\"pan\":\"AAACR4849R\",\"gstinRefId\":\"\",\"regType\":\"\",\"authStatus\":\"Active\",\"gstinId\":\"06AAACR4849R3ZN\",\"registrationName\":\"\",\"tinNumber\":\"\"},{\"emailId\":\"\",\"applicationStatus\":\"\",\"mobNum\":\"\",\"pan\":\"AAACR4849R\",\"gstinRefId\":\"\",\"regType\":\"\",\"authStatus\":\"Inactive\",\"gstinId\":\"33AAACR4849R1ZS\",\"registrationName\":\"\",\"tinNumber\":\"\"},{\"emailId\":\"\",\"applicationStatus\":\"\",\"mobNum\":\"\",\"pan\":\"AAACR4849R\",\"gstinRefId\":\"\",\"regType\":\"\",\"authStatus\":\"Active\",\"gstinId\":\"33AAACR4849R5ZO\",\"registrationName\":\"\",\"tinNumber\":\"\"},{\"emailId\":\"\",\"applicationStatus\":\"\",\"mobNum\":\"\",\"pan\":\"AAACR4849R\",\"gstinRefId\":\"\",\"regType\":\"\",\"authStatus\":\"Active\",\"gstinId\":\"19AAACR4849R1ZI\",\"registrationName\":\"\",\"tinNumber\":\"\"},{\"emailId\":\"\",\"applicationStatus\":\"\",\"mobNum\":\"\",\"pan\":\"AAACR4849R\",\"gstinRefId\":\"\",\"regType\":\"\",\"authStatus\":\"Active\",\"gstinId\":\"20AAACR4849R1ZZ\",\"registrationName\":\"\",\"tinNumber\":\"\"},{\"emailId\":\"\",\"applicationStatus\":\"\",\"mobNum\":\"\",\"pan\":\"AAACR4849R\",\"gstinRefId\":\"\",\"regType\":\"\",\"authStatus\":\"Active\",\"gstinId\":\"29AAACR4849R1CS\",\"registrationName\":\"\",\"tinNumber\":\"\"},{\"emailId\":\"\",\"applicationStatus\":\"\",\"mobNum\":\"\",\"pan\":\"AAACR4849R\",\"gstinRefId\":\"\",\"regType\":\"\",\"authStatus\":\"Active\",\"gstinId\":\"19AAACR4849R1CT\",\"registrationName\":\"\",\"tinNumber\":\"\"},{\"emailId\":\"\",\"applicationStatus\":\"\",\"mobNum\":\"\",\"pan\":\"AAACR4849R\",\"gstinRefId\":\"\",\"regType\":\"\",\"authStatus\":\"Inactive\",\"gstinId\":\"29AAACR4849R3ZF\",\"registrationName\":\"\",\"tinNumber\":\"\"},{\"emailId\":\"\",\"applicationStatus\":\"\",\"mobNum\":\"\",\"pan\":\"AAACR4849R\",\"gstinRefId\":\"\",\"regType\":\"\",\"authStatus\":\"Active\",\"gstinId\":\"19AAACR4849R2ZH\",\"registrationName\":\"\",\"tinNumber\":\"\"},{\"emailId\":\"\",\"applicationStatus\":\"\",\"mobNum\":\"\",\"pan\":\"AAACR4849R\",\"gstinRefId\":\"\",\"regType\":\"\",\"authStatus\":\"Active\",\"gstinId\":\"30AAACR4849R2ZX\",\"registrationName\":\"\",\"tinNumber\":\"\"},{\"emailId\":\"\",\"applicationStatus\":\"\",\"mobNum\":\"\",\"pan\":\"AAACR4849R\",\"gstinRefId\":\"\",\"regType\":\"\",\"authStatus\":\"Active\",\"gstinId\":\"19AAACR4849R5ZE\",\"registrationName\":\"\",\"tinNumber\":\"\"},{\"emailId\":\"\",\"applicationStatus\":\"\",\"mobNum\":\"\",\"pan\":\"AAACR4849R\",\"gstinRefId\":\"\",\"regType\":\"\",\"authStatus\":\"Inactive\",\"gstinId\":\"01AAACR4849R2ZY\",\"registrationName\":\"\",\"tinNumber\":\"\"},{\"emailId\":\"\",\"applicationStatus\":\"\",\"mobNum\":\"\",\"pan\":\"AAACR4849R\",\"gstinRefId\":\"\",\"regType\":\"\",\"authStatus\":\"Active\",\"gstinId\":\"24AAACR4849R1ZR\",\"registrationName\":\"\",\"tinNumber\":\"\"},{\"emailId\":\"\",\"applicationStatus\":\"\",\"mobNum\":\"\",\"pan\":\"AAACR4849R\",\"gstinRefId\":\"\",\"regType\":\"\",\"authStatus\":\"Active\",\"gstinId\":\"23AAACR4849R1ZT\",\"registrationName\":\"\",\"tinNumber\":\"\"},{\"emailId\":\"\",\"applicationStatus\":\"\",\"mobNum\":\"\",\"pan\":\"AAACR4849R\",\"gstinRefId\":\"\",\"regType\":\"\",\"authStatus\":\"Active\",\"gstinId\":\"05AAACR4849R1ZR\",\"registrationName\":\"\",\"tinNumber\":\"\"},{\"emailId\":\"\",\"applicationStatus\":\"\",\"mobNum\":\"\",\"pan\":\"AAACR4849R\",\"gstinRefId\":\"\",\"regType\":\"\",\"authStatus\":\"Active\",\"gstinId\":\"10AAACR4849R1Z0\",\"registrationName\":\"\",\"tinNumber\":\"\"},{\"emailId\":\"\",\"applicationStatus\":\"\",\"mobNum\":\"\",\"pan\":\"AAACR4849R\",\"gstinRefId\":\"\",\"regType\":\"\",\"authStatus\":\"Inactive\",\"gstinId\":\"36AAACR4849R6ZH\",\"registrationName\":\"\",\"tinNumber\":\"\"},{\"emailId\":\"\",\"applicationStatus\":\"\",\"mobNum\":\"\",\"pan\":\"AAACR4849R\",\"gstinRefId\":\"\",\"regType\":\"\",\"authStatus\":\"Active\",\"gstinId\":\"21AAACR4849R4ZU\",\"registrationName\":\"\",\"tinNumber\":\"\"},{\"emailId\":\"\",\"applicationStatus\":\"\",\"mobNum\":\"\",\"pan\":\"AAACR4849R\",\"gstinRefId\":\"\",\"regType\":\"\",\"authStatus\":\"Active\",\"gstinId\":\"32AAACR4849R1C5\",\"registrationName\":\"\",\"tinNumber\":\"\"},{\"emailId\":\"\",\"applicationStatus\":\"\",\"mobNum\":\"\",\"pan\":\"AAACR4849R\",\"gstinRefId\":\"\",\"regType\":\"\",\"authStatus\":\"Active\",\"gstinId\":\"01AAACR4849R1ZZ\",\"registrationName\":\"\",\"tinNumber\":\"\"},{\"emailId\":\"\",\"applicationStatus\":\"\",\"mobNum\":\"\",\"pan\":\"AAACR4849R\",\"gstinRefId\":\"\",\"regType\":\"\",\"authStatus\":\"Active\",\"gstinId\":\"36AAACR4849R3ZK\",\"registrationName\":\"\",\"tinNumber\":\"\"},{\"emailId\":\"\",\"applicationStatus\":\"\",\"mobNum\":\"\",\"pan\":\"AAACR4849R\",\"gstinRefId\":\"\",\"regType\":\"\",\"authStatus\":\"Active\",\"gstinId\":\"07AAACR4849R1CY\",\"registrationName\":\"\",\"tinNumber\":\"\"},{\"emailId\":\"\",\"applicationStatus\":\"\",\"mobNum\":\"\",\"pan\":\"AAACR4849R\",\"gstinRefId\":\"\",\"regType\":\"\",\"authStatus\":\"Active\",\"gstinId\":\"32AAACR4849R1ZU\",\"registrationName\":\"\",\"tinNumber\":\"\"},{\"emailId\":\"\",\"applicationStatus\":\"\",\"mobNum\":\"\",\"pan\":\"AAACR4849R\",\"gstinRefId\":\"\",\"regType\":\"\",\"authStatus\":\"Active\",\"gstinId\":\"07AAACR4849R2ZM\",\"registrationName\":\"\",\"tinNumber\":\"\"},{\"emailId\":\"\",\"applicationStatus\":\"\",\"mobNum\":\"\",\"pan\":\"AAACR4849R\",\"gstinRefId\":\"\",\"regType\":\"\",\"authStatus\":\"Inactive\",\"gstinId\":\"18AAACR4849R3ZI\",\"registrationName\":\"\",\"tinNumber\":\"\"},{\"emailId\":\"\",\"applicationStatus\":\"\",\"mobNum\":\"\",\"pan\":\"AAACR4849R\",\"gstinRefId\":\"\",\"regType\":\"\",\"authStatus\":\"Active\",\"gstinId\":\"16AAACR4849R1ZO\",\"registrationName\":\"\",\"tinNumber\":\"\"},{\"emailId\":\"\",\"applicationStatus\":\"\",\"mobNum\":\"\",\"pan\":\"AAACR4849R\",\"gstinRefId\":\"\",\"regType\":\"\",\"authStatus\":\"Inactive\",\"gstinId\":\"32AAACR4849R6ZP\",\"registrationName\":\"\",\"tinNumber\":\"\"},{\"emailId\":\"\",\"applicationStatus\":\"\",\"mobNum\":\"\",\"pan\":\"AAACR4849R\",\"gstinRefId\":\"\",\"regType\":\"\",\"authStatus\":\"Active\",\"gstinId\":\"37AAACR4849R3ZI\",\"registrationName\":\"\",\"tinNumber\":\"\"},{\"emailId\":\"\",\"applicationStatus\":\"\",\"mobNum\":\"\",\"pan\":\"AAACR4849R\",\"gstinRefId\":\"\",\"regType\":\"\",\"authStatus\":\"Active\",\"gstinId\":\"32AAACR4849R7ZO\",\"registrationName\":\"\",\"tinNumber\":\"\"},{\"emailId\":\"\",\"applicationStatus\":\"\",\"mobNum\":\"\",\"pan\":\"AAACR4849R\",\"gstinRefId\":\"\",\"regType\":\"\",\"authStatus\":\"Active\",\"gstinId\":\"33AAACR4849R2ZR\",\"registrationName\":\"\",\"tinNumber\":\"\"},{\"emailId\":\"\",\"applicationStatus\":\"\",\"mobNum\":\"\",\"pan\":\"AAACR4849R\",\"gstinRefId\":\"\",\"regType\":\"\",\"authStatus\":\"Active\",\"gstinId\":\"30AAACR4849R1ZY\",\"registrationName\":\"\",\"tinNumber\":\"\"},{\"emailId\":\"\",\"applicationStatus\":\"\",\"mobNum\":\"\",\"pan\":\"AAACR4849R\",\"gstinRefId\":\"\",\"regType\":\"\",\"authStatus\":\"Active\",\"gstinId\":\"06AAACR4849R1C0\",\"registrationName\":\"\",\"tinNumber\":\"\"}],\"byteArray\":null,\"pdfPath\":null,\"status_code\":101,\"request_id\":null,\"status_msg\":\"Valid Authentication\"}}";
			
			if (serviceOutput != null) {
				// Insert into qt_karza_integration_log table
				Gson gson = new Gson();
				String requestJson = gson.toJson(requestMap);
				String responseJson = serviceOutput;
				String status = "";
				String message = "";
				JSONObject jsonObjLogTable = new JSONObject(serviceOutput.toString());
				JSONObject infoObjLogTable = jsonObjLogTable.has("msgInfo") ? jsonObjLogTable.getJSONObject("msgInfo")
						: null;
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
						status = infoObjLogTable.getString("message");
					}
					//status = infoObjLogTable.getString("status");
					//message = infoObjLogTable.getString("message");
				}

				String insertStatus = customerBO.saveKarzaReqLog(panNo, reqType, requestJson, responseJson, status,
						message, dbConnectionMap);
				log.debug("insertStatus:" + insertStatus);
				if (!serviceOutput.isEmpty() && !serviceOutput.equalsIgnoreCase(null)
						&& !serviceOutput.equalsIgnoreCase("") && !serviceOutput.startsWith("Error,")) {
					JSONObject jsonObj = new JSONObject(serviceOutput.toString());
					JSONObject errorInfoObj = jsonObj.has("msgInfo") ? jsonObj.getJSONObject("msgInfo") : null;

					if (errorInfoObj != null && errorInfoObj.getString("status") != null
							&& errorInfoObj.getString("status").equalsIgnoreCase("SUCCESS")) {
						JSONObject payloadObj = jsonObj.getJSONObject("payload"); // datainfo=
						log.debug("payloadObj" + payloadObj);
						JSONObject headerObj = jsonObj.getJSONObject("header");

						String responseStatus = errorInfoObj.getString("status");
						correlationId = headerObj.getString("correlationId");

						JSONArray resultArray = (JSONArray) payloadObj.get("result");
						JSONObject resultObj = null;
						StringBuffer addressString = new StringBuffer();
						log.debug("resultArray size" + resultArray.length());
						if (resultArray.length() > 0) {
							//for (int i = 0; i < resultArray.length(); i++) // for (int i = 0; i < resultArray.length();
							// i++)
						//	{//1.0.0.10 fetching multipe gstinId from api approx 100 considering first one as gstinId
								resultObj = resultArray.getJSONObject(0);
								log.debug("i=" + 0 + ",resultObj:" + resultObj);//1.0.0.10
								String gstinNo = resultObj.getString("gstinId");

								log.debug("gstinNo:" + gstinNo);
								JSONObject addressObj = initiateGstAddressApi(gstinNo,panNo);
								log.debug("addressObj:" + addressObj);

								if (addressObj != null) {
									
									JSONObject prAddObj = addressObj.has("pradr") ? addressObj.getJSONObject("pradr")
											: null;
									
									sez_value = addressObj.getString("dty"); //1.0.0.2
									if(sez_value != "" && sez_value.equals("SEZ Unit"))
									{
										sez_flag = "Y";
									}
									else
									{
										sez_flag = "N";
									}
									log.debug("sez_flag:" + sez_flag);
									if (!isSplitAddressAPICalled ) {//1.0.0.10
										String prGstAddress = prAddObj.isNull("adr") ? "" : prAddObj.getString("adr");
										String emailId = prAddObj.isNull("em") ? "" : prAddObj.getString("em");
										String mobNo = prAddObj.isNull("mb") ? "" : prAddObj.getString("mb");
										log.debug("prGstAddress:" + prGstAddress);
										if(prGstAddress != "") {
										JSONObject prAddSplitObj = initiateAddressSplitApi(prGstAddress);
										isSplitAddressAPICalled=true;//1.0.0.10
										 log.debug("prAddSplitObj:" + prAddSplitObj);
										if (prAddSplitObj != null) {
											house = prAddSplitObj.getString("House").trim();
											floor = prAddSplitObj.getString("Floor").trim();
											building = prAddSplitObj.getString("Building").trim();
											complex = prAddSplitObj.getString("Complex").trim();
											street = prAddSplitObj.getString("Street").trim();
											untagged = prAddSplitObj.getString("Untagged").trim();
											locality = prAddSplitObj.getString("locality").trim();
											landmark = prAddSplitObj.getString("Landmark").trim();										
											city = prAddSplitObj.getString("City").trim();
											pin = prAddSplitObj.getLong("Pin");
											district = prAddSplitObj.getString("District").trim();											
											state = prAddSplitObj.getString("State").trim();										
											careOf = prAddSplitObj.getString("C/O").trim();											
											
											
											Map stateCityPinIdMap = customerBO.fetchStateCityPincodeId(gstinNo, city,
													"" + pin,state, dbConnectionMap);
											log.debug(stateCityPinIdMap);
											String stateId = (String) stateCityPinIdMap.get("STATE_ID");
											String cityId = (String) stateCityPinIdMap.get("CITY_ID");
											String pincodeId = (String) stateCityPinIdMap.get("PIN_ID");

											String address1 = house + " " + floor;
											if (isEmptyOrBlank(stateId) && !isEmptyOrBlank(state)) {
												address1 = address1 + " State " + state;
											}
											String address2 = building + " " + complex + " " + street;
											if (isEmptyOrBlank(cityId) && !isEmptyOrBlank(city)) {
												address2 = address2 + " City " + city;
											}
											String address3 = locality+" "+district;
											if (isEmptyOrBlank(pincodeId) && !isEmptyOrBlank(""+pin)) {
												address3 = address3 + " Pin " + pin;
											}
											String address4 = landmark;
											String address5 = untagged;
											
											String[] addresses = {address1,address2,address3,address4,address5};
											List<String> addressList = getAddressList(addresses);
												address1 = addressList.get(0).trim();
												address2 = addressList.get(1).trim();
												address3 = addressList.get(2).trim();
												address4 = addressList.get(3).trim();
												address5 = addressList.get(4).trim();
												
											responseMap.put("stateCityMap", stateCityPinIdMap);
											responseMap.put("ADDRESS1", address1);
											responseMap.put("ADDRESS2", address2);
											responseMap.put("ADDRESS3", address3);
											responseMap.put("ADDRESS4", address4);
											responseMap.put("ADDRESS5", address5);	
											responseMap.put("CITY", city);
											responseMap.put("PIN", pin);
											responseMap.put("STATE", state);												
											responseMap.put("email", emailId);
											responseMap.put("mobile", mobNo);
											responseMap.put("SEZ_FLAG", sez_flag);
											//responseMap.put("prAddSplitObj", prAddSplitObj);
											gstAddrFlag = "Y";
											
											//1.0.0.3
											addressString.append("~").append("~").append("~").append("~").append("~")
											.append(address1 + "~")
											.append(address2 + "~")
											.append(address3 + "~").append(address4 + "~")
											.append(address5 + "~").append(city + "~").append(state + "~")
											.append(pin + "~").append("~").append("~").append("~").append("~")
											.append("~").append("~").append("~").append("~").append("~")
											.append("~").append("~").append("~").append("~").append("~")
											.append(1000000003 + "~").append("~").append("~").append("~")
											.append("~").append(gstinNo + "~").append(sez_flag+"^");
									//log.debug("praddressString:" + addressString);


										}
										else {

											responseMap.put("transId", correlationId);
											responseMap.put("requestType", reqType);
											responseMap.put("responseStatus", "Failure");
											responseMap.put("responseMsg", errorInfoObj.getString("message"));
										}
									}//gst address is null

									} else {
										String prGstAddStr = prAddObj.isNull("adr") ? "" : prAddObj.getString("adr");
										log.debug("prGstAddStr:" + prGstAddStr);
										if(!isSplitAddressAPICalled && prGstAddStr != "") {//1.0.0.10
										JSONObject prAddSplitObjStr = initiateAddressSplitApi(prGstAddStr);
										isSplitAddressAPICalled=true;//1.0.0.10
										log.debug("prAddSplitObjStr:" + prAddSplitObjStr);
										if (prAddSplitObjStr != null) {
											
											house = prAddSplitObjStr.getString("House").trim();
											floor = prAddSplitObjStr.getString("Floor").trim();
											building = prAddSplitObjStr.getString("Building").trim();
											complex = prAddSplitObjStr.getString("Complex").trim();
											street = prAddSplitObjStr.getString("Street").trim();
											untagged = prAddSplitObjStr.getString("Untagged").trim();
											locality = prAddSplitObjStr.getString("locality").trim();
											landmark = prAddSplitObjStr.getString("Landmark").trim();										
											city = prAddSplitObjStr.getString("City").trim();
											pin = prAddSplitObjStr.getLong("Pin");
											district = prAddSplitObjStr.getString("District").trim();											
											state = prAddSplitObjStr.getString("State").trim();										
											careOf = prAddSplitObjStr.getString("C/O").trim();
											
											String address1 = house + " " + floor;											
											String address2 = building + " " + complex + " " + street;											
											String address3 = locality+" "+district;											
											String address4 = landmark;
											String address5 = untagged;
											
											String[] addresses = {address1,address2,address3,address4,address5};
											List<String> addressList = getAddressList(addresses);
											address1 = addressList.get(0).trim();
											address2 = addressList.get(1).trim();
											address3 = addressList.get(2).trim();
											address4 = addressList.get(3).trim();
											address5 = addressList.get(4).trim();
											
											addressString.append("~").append("~").append("~").append("~").append("~")
													.append(address1 + "~")
													.append(address2 + "~")
													.append(address3 + "~").append(address4 + "~")
													.append(address5 + "~").append(city + "~").append(state + "~")
													.append(pin + "~").append("~").append("~").append("~").append("~")
													.append("~").append("~").append("~").append("~").append("~")
													.append("~").append("~").append("~").append("~").append("~")
													.append(1000000003 + "~").append("~").append("~").append("~")
													.append("~").append(gstinNo + "~").append(sez_flag+"^");
											//log.debug("praddressString:" + addressString);

										}
										else {

											responseMap.put("transId", correlationId);
											responseMap.put("requestType", reqType);
											responseMap.put("responseStatus", "Failure");
											responseMap.put("responseMsg", errorInfoObj.getString("message"));
										}
									}//gst address is null

									}

									JSONArray adAddArray = (JSONArray) addressObj.get("adadr");
									JSONObject adAddObj = null;
									log.debug("adAddArray size:" + adAddArray.length());
									if (adAddArray.length() > 0) {
										for (int j = 0; j < adAddArray.length(); j++) // for (int j = 0; j <
																	// adAddArray.length(); j++)
										{
											if(!isSplitAddressAPICalled) {//1.0.0.10
											log.debug("j=" + j);
											adAddObj = adAddArray.getJSONObject(j);
											String adGstAddress = adAddObj.isNull("adr") ? "" : adAddObj.getString("adr");
											log.debug("adGstAddress:" + adGstAddress);
											if(adGstAddress != "") {
											JSONObject adAddSplitObj = initiateAddressSplitApi(adGstAddress);
											isSplitAddressAPICalled=true;//1.0.0.10
											log.debug("adAddSplitObj:" + adAddSplitObj);

											if (adAddSplitObj != null) {
												locality = adAddSplitObj.getString("locality").trim();
												building = adAddSplitObj.getString("Building").trim();
												city = adAddSplitObj.getString("City").trim();
												pin = adAddSplitObj.getLong("Pin");
												district = adAddSplitObj.getString("District").trim();
												floor = adAddSplitObj.getString("Floor").trim();
												house = adAddSplitObj.getString("House").trim();
												state = adAddSplitObj.getString("State").trim();
												complex = adAddSplitObj.getString("Complex").trim();
												landmark = adAddSplitObj.getString("Landmark").trim();
												untagged = adAddSplitObj.getString("Untagged").trim();
												careOf = adAddSplitObj.getString("C/O").trim();
												street = adAddSplitObj.getString("Street").trim();
												
												String address1 = house + " " + floor;											
												String address2 = building + " " + complex + " " + street;											
												String address3 = locality+" "+district;											
												String address4 = landmark;
												String address5 = untagged;
												
												String[] addresses = {address1,address2,address3,address4,address5};
												List<String> addressList = getAddressList(addresses);
												address1 = addressList.get(0).trim();
												address2 = addressList.get(1).trim();
												address3 = addressList.get(2).trim();
												address4 = addressList.get(3).trim();
												address5 = addressList.get(4).trim();

												addressString.append("~").append("~").append("~").append("~")
														.append("~").append(address1 + "~")
														.append(address2 + "~")
														.append(address3 + "~").append(address4 + "~")
														.append(address5 + "~").append(city + "~").append(state + "~")
														.append(pin + "~").append("~").append("~").append("~")
														.append("~").append("~").append("~").append("~").append("~")
														.append("~").append("~").append("~").append("~").append("~")
														.append("~").append(1000000003 + "~").append("~").append("~")
														.append("~").append("~").append(gstinNo + "~").append(sez_flag+"^");
												//log.debug("adaddressString:" + addressString);

											}
											else {

												responseMap.put("transId", correlationId);
												responseMap.put("requestType", reqType);
												responseMap.put("responseStatus", "Failure");
												responseMap.put("responseMsg", errorInfoObj.getString("message"));
											}
											
										}//gst address is null
											
										}
									}
									}//1.0.0.10

								}
								else {

									responseMap.put("transId", correlationId);
									responseMap.put("requestType", reqType);
									responseMap.put("responseStatus", "Failure");
									responseMap.put("responseMsg", errorInfoObj.getString("message"));
								}

							//} //1.0.0.10
						}

						log.debug("addressString size:" + addressString.length() + ",addressString:" + addressString);
						responseMap.put("ADDRESS_STRING",
								(addressString.length() > 0 ? addressString.substring(0, addressString.length() - 1)
										: ""));
						responseMap.put("correlationId", correlationId);
						responseMap.put("requestType", reqType);
						responseMap.put("responseStatus", responseStatus);
						log.debug("gstAddrFlag:" + gstAddrFlag);
						responseMap.put("GST_ADDR_FLAG", gstAddrFlag);

					} else {

						responseMap.put("transId", correlationId);
						responseMap.put("requestType", reqType);
						responseMap.put("responseStatus", "Failure");
						responseMap.put("responseMsg", errorInfoObj.getString("message"));
					}

				} else {
					responseMap.put("transId", correlationId);
					responseMap.put("requestType", reqType);
					responseMap.put("responseStatus", "Failure");
					responseMap.put("responseMsg", "Response not generated, Please try again.");
				}
			}

		} catch (Exception ex) {
			responseMap.put("responseStatus", "Failure");
			responseMap.put("responseMsg", "Please try again, Connect to Admin Team");
			log.info("Exception:" + ex.getMessage());
		}
		try {
			Gson gson = new Gson();
			PrintWriter out = response.getWriter();
			out.print(gson.toJson(responseMap));
			out.flush();
			out.close();
		} catch (Exception ex) {

		}
		return null;
	}

	public JSONObject initiateGstAddressApi(String gstinNo,String panNo) {

		List list = new ArrayList();
		URL url = null;
		HttpURLConnection httpsConnection = null;

		LinkedHashMap requestMap = new LinkedHashMap();
		String correlationId = "";
		String reqType = "GST_ADDRESS";
		try {

			LinkedHashMap requestHeaderMap = new LinkedHashMap();
			correlationId = reqType + "_" + Math.random();
			log.debug("GST_ADDRESS CorrelationId: " + correlationId);
			requestHeaderMap.put("msgVersion", commonApplicationResource.getString("MSG_VERSION"));
			requestHeaderMap.put("appId", commonApplicationResource.getString("APP_ID"));
			requestHeaderMap.put("correlationId", correlationId);
			requestHeaderMap.put("token", commonApplicationResource.getString("TOKEN"));

			log.debug("gstin: " + gstinNo);
			LinkedHashMap payloadMap = new LinkedHashMap();
			payloadMap.put("gstin", gstinNo);
			payloadMap.put("consent", "Y");

			requestMap.put("header", requestHeaderMap);
			requestMap.put("payload", payloadMap);

		} catch (Exception ex) {
			log.error(ex.getMessage());
		}
		String requestUrl = commonApplicationResource.getString("GST_ADDRESS_REQ_URL");
		int timeOut = Integer.parseInt(commonApplicationResource.getString("API_SERVICE_LOGOUT_IN_SEC"));// 100;
		HashMap responseMap = new HashMap();
		JSONObject resultObj = null;
		try {
			String serviceOutput = ServiceCallAction.getResultString(requestMap, requestUrl, timeOut);
			//serviceOutput="{\"header\":{\"msgVersion\":\"1.0\",\"appId\":\"KARZA\",\"correlationId\":\"GST_ADDRESS_0.14259777175442512\",\"token\":\"37f21104-ec7b-4c64-b7d7-600550597d9b\"},\"msgInfo\":{\"code\":\"200\",\"status\":\"SUCCESS\",\"message\":\"Valid Authentication\"},\"payload\":{\"result\":{\"mbr\":[],\"canFlag\":\"NA\",\"pradr\":{\"em\":\"\",\"adr\":\"D - 4 , Sector - 3, Noida, Noida, Noida, Gautambuddha Nagar, Uttar Pradesh, pin: 201301\",\"addr\":\"NA\",\"mb\":\"\",\"ntr\":\"Input Service Distributor (ISD), Service Provision, Works Contract, EOU / STP / EHTP, Recipient of Goods or Services, Office / Sale Office, SEZ\",\"lastUpdatedDate\":\"NA\"},\"tradeNam\":\"M/S TATA CONSULTANCY SERVICES LTD.\",\"lstupdt\":\"27/03/2025\",\"contacted\":{\"email\":null,\"mobNum\":null,\"name\":null},\"rgdt\":\"01/07/2017\",\"stjCd\":\"UP333\",\"stj\":\"Corporate Circle, Noida\",\"ctjCd\":\"YC0102\",\"ppr\":\"NA\",\"dty\":\"Regular\",\"cmpRt\":\"NA\",\"cxdt\":\"\",\"ctb\":\"Public Limited Company\",\"sts\":\"Active\",\"gstin\":\"09AAACR4849R1ZJ\",\"lgnm\":\"TATA CONSULTANCY SERVICES LIMITED\",\"nba\":[\"Input Service Distributor (ISD)\",\"Service Provision\",\"Works Contract\",\"EOU / STP / EHTP\",\"Recipient of Goods or Services\",\"Office / Sale Office\",\"SEZ\",\"Supplier of Services\",\"Import\",\"Leasing Business\",\"Retail Business\",\"Export\",\"Factory / Manufacturing\",\"Warehouse / Depot\",\"Wholesale Business\"],\"ctj\":\"RANGE - 2\",\"adadr\":[{\"em\":\"\",\"adr\":\"Plot No 154-B, BLOCK A SECTOR-63, NOIDA, Gautambuddha Nagar, Uttar Pradesh, pin: 201301\",\"addr\":\"NA\",\"mb\":\"\",\"ntr\":\"Input Service Distributor (ISD), Service Provision, Works Contract, EOU / STP / EHTP, Recipient of Goods or Services, Office / Sale Office, SEZ\",\"lastUpdatedDate\":\"NA\"},{\"em\":\"\",\"adr\":\"PLOT NO 5 BLOCK B, SECTOR 62, NOIDA, Gautambuddha Nagar, Uttar Pradesh, pin: 201309\",\"addr\":\"NA\",\"mb\":\"\",\"ntr\":\"Input Service Distributor (ISD), Service Provision, Works Contract, EOU / STP / EHTP, Recipient of Goods or Services, Office / Sale Office, SEZ\",\"lastUpdatedDate\":\"NA\"},{\"em\":\"\",\"adr\":\"PLOT NO TCG 1/1, VIBHUTI KHAND GOMTI NAGAR, LUCKNOW, Lucknow, Uttar Pradesh, pin: 226010\",\"addr\":\"NA\",\"mb\":\"\",\"ntr\":\"Input Service Distributor (ISD), Service Provision, Works Contract, EOU / STP / EHTP, Recipient of Goods or Services, Office / Sale Office, SEZ\",\"lastUpdatedDate\":\"NA\"},{\"em\":\"\",\"adr\":\"PLOT NO 4 and 5, NSEZ, NOIDA, Gautambuddha Nagar, Uttar Pradesh, pin: 201305\",\"addr\":\"NA\",\"mb\":\"\",\"ntr\":\"Input Service Distributor (ISD), Service Provision, Works Contract, EOU / STP / EHTP, Recipient of Goods or Services, Office / Sale Office, SEZ\",\"lastUpdatedDate\":\"NA\"},{\"em\":\"\",\"adr\":\"A 44 AND 45, SECTOR 62, NOIDA, Gautambuddha Nagar, Uttar Pradesh, pin: 201309\",\"addr\":\"NA\",\"mb\":\"\",\"ntr\":\"Input Service Distributor (ISD), Service Provision, Works Contract, EOU / STP / EHTP, Recipient of Goods or Services, Office / Sale Office, SEZ\",\"lastUpdatedDate\":\"NA\"},{\"em\":\"\",\"adr\":\"C 30/7A, SECTOR 62, NOIDA, Gautambuddha Nagar, Uttar Pradesh, pin: 201309\",\"addr\":\"NA\",\"mb\":\"\",\"ntr\":\"Input Service Distributor (ISD), Service Provision, Works Contract, EOU / STP / EHTP, Recipient of Goods or Services, Office / Sale Office, SEZ\",\"lastUpdatedDate\":\"NA\"},{\"em\":\"\",\"adr\":\"Radha Krishna Tower,CC 146 A,Plot no.317/ 1, Jagatpur Lala Begum, Peelibheet Bypass Road, Bareilly, Bareilly, Uttar Pradesh, pin: 243006\",\"addr\":\"NA\",\"mb\":\"\",\"ntr\":\"EOU / STP / EHTP, Office / Sale Office, Recipient of Goods or Services, Works Contract\",\"lastUpdatedDate\":\"NA\"},{\"em\":\"\",\"adr\":\"A-1, A-12, Bhu-tal Pacific Business Park,, Plot no.37/1,Site-IV, Sahibabad, Ghaziabad, Ghaziabad, Uttar Pradesh, pin: 201010\",\"addr\":\"NA\",\"mb\":\"\",\"ntr\":\"EOU / STP / EHTP, Office / Sale Office, Works Contract, Recipient of Goods or Services\",\"lastUpdatedDate\":\"NA\"},{\"em\":\"\",\"adr\":\"111/18-A, Anand Vaibhav Building, Harshnagar, G T Road, Kanpur, Kanpur Nagar, Uttar Pradesh, pin: 208012\",\"addr\":\"NA\",\"mb\":\"\",\"ntr\":\"EOU / STP / EHTP, Office / Sale Office, Recipient of Goods or Services, Works Contract\",\"lastUpdatedDate\":\"NA\"},{\"em\":\"\",\"adr\":\"B 38/ 67, Satyam Apartment, Arazil Settlement, No.136 Mouja, Tulsipur, Varanasi, Varanasi, Uttar Pradesh, pin: 221010\",\"addr\":\"NA\",\"mb\":\"\",\"ntr\":\"EOU / STP / EHTP, Office / Sale Office, Recipient of Goods or Services, Works Contract\",\"lastUpdatedDate\":\"NA\"},{\"em\":\"\",\"adr\":\"Santosh Arcade, Medical College Road,Bashratpur, Gorakhpur, Gorakhpur, Uttar Pradesh, pin: 273004\",\"addr\":\"NA\",\"mb\":\"\",\"ntr\":\"EOU / STP / EHTP, Office / Sale Office, Recipient of Goods or Services, Works Contract\",\"lastUpdatedDate\":\"NA\"},{\"em\":\"\",\"adr\":\"20-A, Ratan sqaure, Vidhan Sabha Marg, Lucknow, Lucknow, Uttar Pradesh, pin: 226001\",\"addr\":\"NA\",\"mb\":\"\",\"ntr\":\"EOU / STP / EHTP, Office / Sale Office, Recipient of Goods or Services, Works Contract\",\"lastUpdatedDate\":\"NA\"},{\"em\":\"\",\"adr\":\"D-63/12B, Plot No.173/2, Siddha Complex, Mahmoorganj, Mahmoorganj, Varanasi, Uttar Pradesh, pin: 221010\",\"addr\":\"NA\",\"mb\":\"\",\"ntr\":\"Office / Sale Office, Recipient of Goods or Services, Works Contract, Supplier of Services, Import\",\"lastUpdatedDate\":\"NA\"},{\"em\":\"\",\"adr\":\"C- 56, Phase - 2, Noida, Noida, Gautam Buddha Nagar, Gautambuddha Nagar, Uttar Pradesh, pin: 201305\",\"addr\":\"NA\",\"mb\":\"\",\"ntr\":\"EOU / STP / EHTP, Recipient of Goods or Services, Office / Sale Office, Works Contract\",\"lastUpdatedDate\":\"NA\"},{\"em\":\"\",\"adr\":\"Lucerna Tower, A-2B, Sector - 125, Noida, Gautambuddha Nagar, Uttar Pradesh, pin: 201301\",\"addr\":\"NA\",\"mb\":\"\",\"ntr\":\"Works Contract, EOU / STP / EHTP, Office / Sale Office, Recipient of Goods or Services\",\"lastUpdatedDate\":\"NA\"},{\"em\":\"\",\"adr\":\"4 to 7 of T-3, 8 to 14 of T-5 and Ground to 15 of T-6, Assotech Business Cresterra, Tower 3,5 and 6, Plot no -22, Bhada Express, Sector 135, Noida, Gautambuddha Nagar, Uttar Pradesh, pin: 201304\",\"addr\":\"NA\",\"mb\":\"\",\"ntr\":\"Leasing Business, Retail Business, Works Contract, EOU / STP / EHTP, Import, Office / Sale Office, Export, Supplier of Services, Recipient of Goods or Services\",\"lastUpdatedDate\":\"NA\"},{\"em\":\"\",\"adr\":\"Khasara No 128-126, Tehsil  Mohanlalganj, Sisendi Branch Post Office, Sisandi, Lucknow, Uttar Pradesh, pin: 226301\",\"addr\":\"NA\",\"mb\":\"\",\"ntr\":\"Factory / Manufacturing, Works Contract, Import, Office / Sale Office, Warehouse / Depot, Export, Supplier of Services, Recipient of Goods or Services\",\"lastUpdatedDate\":\"NA\"},{\"em\":\"\",\"adr\":\"The Galleria Square, 26, Harsh Commercial Park, Harsh Commercial Park, Garh Road, Meerut Book Centre, Meerut, Meerut, Uttar Pradesh, pin: 250002\",\"addr\":\"NA\",\"mb\":\"\",\"ntr\":\"Works Contract, Retail Business, Office / Sale Office, Import, Export, Supplier of Services, Recipient of Goods or Services\",\"lastUpdatedDate\":\"NA\"},{\"em\":\"\",\"adr\":\"Khasara No.114/115, V- Khasarwara, Tehsil- Sarojini Nagar, Khasarwara, Lucknow, Uttar Pradesh, pin: 226401\",\"addr\":\"NA\",\"mb\":\"\",\"ntr\":\"Wholesale Business, Supplier of Services, Export, Import, Office / Sale Office, Works Contract, Leasing Business, Recipient of Goods or Services\",\"lastUpdatedDate\":\"NA\"},{\"em\":\"\",\"adr\":\"Plot No-8, SECTOR 144, Noida, Gautambuddha Nagar, Uttar Pradesh, pin: 201301\",\"addr\":\"NA\",\"mb\":\"\",\"ntr\":\"Export, Supplier of Services, Recipient of Goods or Services, Office / Sale Office, Import, Leasing Business, Retail Business, Works Contract\",\"lastUpdatedDate\":\"NA\"}]},\"byteArray\":null,\"pdfPath\":null,\"status_code\":101,\"request_id\":null,\"status_msg\":\"Valid Authentication\"}}";
			if (serviceOutput != null) {
				// Insert into qt_karza_integration_log table
				Gson gson = new Gson();
				String requestJson = gson.toJson(requestMap);
				String responseJson = serviceOutput;
				String status = "";
				String message = "";
				JSONObject jsonObjLogTable = new JSONObject(serviceOutput.toString());
				JSONObject infoObjLogTable = jsonObjLogTable.has("msgInfo") ? jsonObjLogTable.getJSONObject("msgInfo")
						: null;
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
						status = infoObjLogTable.getString("message");
					}
					//status = infoObjLogTable.getString("status");
					//message = infoObjLogTable.getString("message");
				}
				log.debug("pan no:" + panNo);
				String insertStatus = customerBO.saveKarzaReqLog(panNo, reqType, requestJson, responseJson, status,
						message, dbConnectionMap);
				log.debug("insertStatus:" + insertStatus);

				if (!serviceOutput.isEmpty() && !serviceOutput.equalsIgnoreCase(null)
						&& !serviceOutput.equalsIgnoreCase("") && !serviceOutput.startsWith("Error,")) {
					JSONObject jsonObj = new JSONObject(serviceOutput.toString());
					JSONObject errorInfoObj = jsonObj.has("msgInfo") ? jsonObj.getJSONObject("msgInfo") : null;
					log.debug("errorInfoObj.getString(\"status\"):" + errorInfoObj.getString("status"));
					if (errorInfoObj != null && errorInfoObj.getString("status") != null
							&& errorInfoObj.getString("status").equalsIgnoreCase("SUCCESS")) {
						JSONObject payloadObj = jsonObj.getJSONObject("payload"); // datainfo=
						JSONObject headerObj = jsonObj.getJSONObject("header");
						resultObj = payloadObj.has("result") ? payloadObj.getJSONObject("result") : null;

						String responseStatus = errorInfoObj.getString("status");
						correlationId = headerObj.getString("correlationId");

						responseMap.put("correlationId", correlationId);
						responseMap.put("requestType", reqType);
						responseMap.put("responseStatus", responseStatus);

					} else {

						responseMap.put("transId", correlationId);
						responseMap.put("requestType", reqType);
						responseMap.put("responseStatus", "Failure");
						responseMap.put("responseMsg", errorInfoObj.getString("message"));
					}

				} else {
					responseMap.put("transId", correlationId);
					responseMap.put("requestType", reqType);
					responseMap.put("responseStatus", "Failure");
					responseMap.put("responseMsg", "Response not generated, Please try again.");
				}
			}

		} catch (Exception ex) {
			responseMap.put("responseStatus", "Failure");
			responseMap.put("responseMsg", "Please try again, Connect to Admin Team");
			log.info("Exception:" + ex.getMessage());
		}
		return resultObj;

	}

	public JSONObject initiateAddressSplitApi(String gstAddress) {

		List list = new ArrayList();
		URL url = null;
		HttpURLConnection httpsConnection = null;

		LinkedHashMap requestMap = new LinkedHashMap();
		String correlationId = "";
		String reqType = "KARZA_ADDSPLIT";
		try {

			LinkedHashMap requestHeaderMap = new LinkedHashMap();
			correlationId = reqType + "_" + Math.random();
			log.debug("KARZA_ADDSPLIT CorrelationId: " + correlationId);
			requestHeaderMap.put("msgVersion", commonApplicationResource.getString("MSG_VERSION"));
			requestHeaderMap.put("appId", commonApplicationResource.getString("APP_ID"));
			requestHeaderMap.put("correlationId", correlationId);
			requestHeaderMap.put("token", commonApplicationResource.getString("TOKEN"));

			log.debug("gstAddress: " + gstAddress);
			LinkedHashMap payloadMap = new LinkedHashMap();
			LinkedHashMap configMap = new LinkedHashMap();
			payloadMap.put("address", gstAddress);
			configMap.put("get_state_by_brute_force", false);
			payloadMap.put("config", configMap);
			payloadMap.put("version", "2.1");

			requestMap.put("header", requestHeaderMap);
			requestMap.put("payload", payloadMap);

		} catch (Exception ex) {
			log.error(ex.getMessage());
		}
		String requestUrl = commonApplicationResource.getString("GST_ADDRESS_SPLIT_URL");
		int timeOut = Integer.parseInt(commonApplicationResource.getString("API_SERVICE_LOGOUT_IN_SEC"));// 100;
		HashMap responseMap = new HashMap();
		JSONObject resultObj = null;
		try {
			String serviceOutput = ServiceCallAction.getResultString(requestMap, requestUrl, timeOut);
			//serviceOutput="{\"header\":{\"msgVersion\":\"1.0\",\"appId\":\"KARZA\",\"correlationId\":\"KARZA_ADDSPLIT_0.8973446713254333\",\"token\":\"37f21104-ec7b-4c64-b7d7-600550597d9b\"},\"msgInfo\":{\"code\":\"200\",\"status\":\"SUCCESS\",\"message\":\"Valid Authentication\"},\"payload\":{\"result\":{\"locality\":\"\",\"Building\":\"\",\"City\":\"NOIDA\",\"Pin\":201301,\"District\":\"GAUTAM BUDDHA NAGAR\",\"Floor\":\"\",\"House\":\"SECTOR - 3 , D - 4\",\"State\":\"UTTAR PRADESH\",\"Complex\":\"\",\"Landmark\":\"\",\"Untagged\":\"\",\"C/O\":\"\",\"Street\":\"\"},\"byteArray\":null,\"pdfPath\":null,\"status_msg\":\"Valid Authentication\",\"status-code\":101,\"request_id\":\"32461cf5-60fa-446d-a575-f67b2fc259eb\"}}";
			if (serviceOutput != null) {
				if (!serviceOutput.isEmpty() && !serviceOutput.equalsIgnoreCase(null)
						&& !serviceOutput.equalsIgnoreCase("") && !serviceOutput.startsWith("Error,")) {
					JSONObject jsonObj = new JSONObject(serviceOutput.toString());
					JSONObject errorInfoObj = jsonObj.has("msgInfo") ? jsonObj.getJSONObject("msgInfo") : null;
					log.debug("errorInfoObj.getString(status):" + errorInfoObj.getString("status"));
					if (errorInfoObj != null && errorInfoObj.getString("status") != null
							&& errorInfoObj.getString("status").equalsIgnoreCase("SUCCESS")) {
						JSONObject payloadObj = jsonObj.getJSONObject("payload"); // datainfo=
						JSONObject headerObj = jsonObj.getJSONObject("header");
						resultObj = payloadObj.has("result") ? payloadObj.getJSONObject("result") : null;
						log.debug("resultObj:" + resultObj);

						String responseStatus = errorInfoObj.getString("status");
						correlationId = headerObj.getString("correlationId");

						responseMap.put("correlationId", correlationId);
						responseMap.put("requestType", reqType);
						responseMap.put("responseStatus", responseStatus);

					} else {

						responseMap.put("transId", correlationId);
						responseMap.put("requestType", reqType);
						responseMap.put("responseStatus", "Failure");
						responseMap.put("responseMsg", errorInfoObj.getString("message"));
					}

				} else {
					responseMap.put("transId", correlationId);
					responseMap.put("requestType", reqType);
					responseMap.put("responseStatus", "Failure");
					responseMap.put("responseMsg", "Response not generated, Please try again.");
				}

			}
		} catch (Exception ex) {
			responseMap.put("responseStatus", "Failure");
			responseMap.put("responseMsg", "Please try again, Connect to Admin Team");
			log.info("Exception:" + ex.getMessage());
		}

		return resultObj;

	}
	
	public ActionForward generateAadhaarOTP(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		List list = new ArrayList();
		URL url = null;
		HttpURLConnection httpsConnection = null;
		HttpSession session = request.getSession();
		ServletContext ctx = session.getServletContext();
		dbConnectionMap = (HashMap) ctx.getAttribute("dbConnectionMapLOS");
		LinkedHashMap requestMap = new LinkedHashMap();
		String correlationId = "";
		String reqType = "AADHAAR_OTP";
		String aadhaarNo = "";
		try {
			aadhaarNo = request.getParameter("aadhaarNoValue");
			log.debug("aadhaarNo:" + aadhaarNo);

			LinkedHashMap requestHeaderMap = new LinkedHashMap();
			correlationId = reqType + "_" + Math.random();
			log.debug("AadhaarOtpCorrelationId:" + correlationId);
			requestHeaderMap.put("msgVersion", commonApplicationResource.getString("MSG_VERSION"));
			requestHeaderMap.put("appId", commonApplicationResource.getString("APP_ID"));
			requestHeaderMap.put("correlationId", correlationId);//correlationId); //"KARZA_PAN_0.876253295804383889912899u6876"
			requestHeaderMap.put("token", commonApplicationResource.getString("TOKEN"));

			LinkedHashMap payloadMap = new LinkedHashMap();
			payloadMap.put("aadhaarNo", aadhaarNo);
			payloadMap.put("consent", "Y");

			requestMap.put("header", requestHeaderMap);
			requestMap.put("payload", payloadMap);

		} catch (Exception ex) {
			log.error(ex.getMessage());
		}
		String requestUrl = commonApplicationResource.getString("AADHAAR_GENERATE_OTP_URL");
		int timeOut = Integer.parseInt(commonApplicationResource.getString("API_SERVICE_LOGOUT_IN_SEC"));// 100;
		HashMap responseMap = new HashMap();
		try {
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
				JSONObject infoObjLogTable = jsonObjLogTable.has("msgInfo") ? jsonObjLogTable.getJSONObject("msgInfo")
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
						status = infoObjLogTable.getString("message");
					}
					
					//status = infoObjLogTable.getString("status") != null ? infoObjLogTable.getString("status") : null;
					
					//message = infoObjLogTable.getString("message") != null ? infoObjLogTable.getString("message") : null;
				}

				String insertStatus = customerBO.saveKarzaReqLog(aadhaarNo, reqType, requestJson, responseJson, status,
					message, dbConnectionMap);
				log.debug("insertStatus:" + insertStatus);
				if (!serviceOutput.isEmpty() && !serviceOutput.equalsIgnoreCase(null)
						&& !serviceOutput.equalsIgnoreCase("") && !serviceOutput.startsWith("Error,")) {
					JSONObject jsonObj = new JSONObject(serviceOutput.toString());
					JSONObject errorInfoObj = jsonObj.has("msgInfo") ? jsonObj.getJSONObject("msgInfo") : null;

					if (errorInfoObj != null && errorInfoObj.getString("status") != null
							&& errorInfoObj.getString("status").equalsIgnoreCase("SUCCESS")) {
						JSONObject payloadObj = jsonObj.getJSONObject("payload"); // datainfo=
						JSONObject headerObj = jsonObj.getJSONObject("header");
						JSONObject resultObj = payloadObj.has("result") ? payloadObj.getJSONObject("result") : null;

						String responseStatus = errorInfoObj.getString("status");
						correlationId = headerObj.getString("correlationId");

						String otpMessage = resultObj.getString("message");
						String aadharReqId = payloadObj.getString("requestId");

						responseMap.put("correlationId", correlationId);
						responseMap.put("requestType", reqType);
						responseMap.put("responseStatus", responseStatus);

						responseMap.put("otpMessage", otpMessage);
						responseMap.put("aadharReqId", aadharReqId);

					} else {

						responseMap.put("transId", correlationId);
						responseMap.put("requestType", reqType);
						responseMap.put("responseStatus", "Failure");
						responseMap.put("responseMsg", errorInfoObj.getString("message"));
					}

				} else {
					responseMap.put("transId", correlationId);
					responseMap.put("requestType", reqType);
					responseMap.put("responseStatus", "Failure");
					responseMap.put("responseMsg", "Response not generated, Please try again.");
				}
			}

		} catch (Exception ex) {
			responseMap.put("responseStatus", "Failure");
			responseMap.put("responseMsg", "Please try again, Connect to Admin Team");
			log.info("Exception:" + ex.getMessage());
		}
		try {
			Gson gson = new Gson();
			PrintWriter out = response.getWriter();
			out.print(gson.toJson(responseMap));
			out.flush();
			out.close();
		} catch (Exception ex) {

		}
		return null;
	}
	
	public ActionForward generateAadhaarFile(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		List list = new ArrayList();
		URL url = null;
		HttpURLConnection httpsConnection = null;
		HttpSession session = request.getSession();
		ServletContext ctx = session.getServletContext();
		dbConnectionMap = (HashMap) ctx.getAttribute("dbConnectionMapLOS");
		LinkedHashMap requestMap = new LinkedHashMap();
		String correlationId = "";
		String reqType = "AADHAAR_FILE";
		String aadhaarNo = "";
		String apiRequestId = "";
		String aadhaarOTP = "";
		try {
			aadhaarNo = request.getParameter("aadhaarNoValue");
			apiRequestId = request.getParameter("aadharReqIdValue");
			aadhaarOTP = request.getParameter("aadharOtpValue");
			log.debug("aadhaarNo:" + aadhaarNo +" ,apiRequestId:"+apiRequestId +" ,aadhaarOTP:"+aadhaarOTP );

			LinkedHashMap requestHeaderMap = new LinkedHashMap();
			correlationId = reqType + "_" + Math.random();
			log.debug("AadhaarFileCorrelationId:" + correlationId);
			requestHeaderMap.put("msgVersion", commonApplicationResource.getString("MSG_VERSION"));
			requestHeaderMap.put("appId", commonApplicationResource.getString("APP_ID"));
			requestHeaderMap.put("correlationId", correlationId);//correlationId);  //"KARZA_PAN_0.876253295804383889912899u6876"
			requestHeaderMap.put("token", commonApplicationResource.getString("TOKEN"));

			LinkedHashMap payloadMap = new LinkedHashMap();
			
			payloadMap.put("otp", aadhaarOTP);
			payloadMap.put("aadhaarNo", aadhaarNo); //1.0.0.5
			payloadMap.put("requestId", apiRequestId);			
			payloadMap.put("consent", "Y");

			requestMap.put("header", requestHeaderMap);
			requestMap.put("payload", payloadMap);

		} catch (Exception ex) {
			log.error(ex.getMessage());
		}
		String requestUrl = commonApplicationResource.getString("AADHAAR_GENERATE_FILE_URL");
		int timeOut = Integer.parseInt(commonApplicationResource.getString("API_SERVICE_LOGOUT_IN_SEC"));// 100;
		HashMap responseMap = new HashMap();
		try {
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
				JSONObject infoObjLogTable = jsonObjLogTable.has("msgInfo") ? jsonObjLogTable.getJSONObject("msgInfo")
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
						status = infoObjLogTable.getString("message");
					}
					
					//status = infoObjLogTable.getString("status") != null ? infoObjLogTable.getString("status") : null;
					
					//message = infoObjLogTable.getString("message") != null ? infoObjLogTable.getString("message") : null;
				}

				String insertStatus = customerBO.saveKarzaReqLog(aadhaarNo, reqType, requestJson, responseJson, status,
					message, dbConnectionMap);
				log.debug("insertStatus:" + insertStatus);
				if (!serviceOutput.isEmpty() && !serviceOutput.equalsIgnoreCase(null)
						&& !serviceOutput.equalsIgnoreCase("") && !serviceOutput.startsWith("Error,")) {
					JSONObject jsonObj = new JSONObject(serviceOutput.toString());
					JSONObject errorInfoObj = jsonObj.has("msgInfo") ? jsonObj.getJSONObject("msgInfo") : null;

					if (errorInfoObj != null && errorInfoObj.getString("status") != null
							&& errorInfoObj.getString("status").equalsIgnoreCase("SUCCESS")) {
						JSONObject payloadObj = jsonObj.getJSONObject("payload"); // datainfo=
						JSONObject headerObj = jsonObj.getJSONObject("header");
						JSONObject resultObj = payloadObj.has("result") ? payloadObj.getJSONObject("result") : null;
						JSONObject dataFromAadharObj = resultObj.has("dataFromAadhaar") ? resultObj.getJSONObject("dataFromAadhaar") : null;
						JSONObject addressObj = dataFromAadharObj.has("address") ? dataFromAadharObj.getJSONObject("address") : null;
						JSONObject splitAddressObj = addressObj.has("splitAddress") ? addressObj.getJSONObject("splitAddress") : null;

						String responseStatus = errorInfoObj.getString("status");
						correlationId = headerObj.getString("correlationId");

						String name = dataFromAadharObj.isNull("name") ? "" : dataFromAadharObj.getString("name");
						String dob 	= dataFromAadharObj.isNull("dob") ? "" : dataFromAadharObj.getString("dob");
						String formattedDob = "";
						SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
						try {
							Date date = formatter.parse(dob);
							formattedDob = new SimpleDateFormat("dd-MMM-yyyy").format(date);
							log.debug("formattedDob : "+formattedDob);
						}
						catch(Exception e)
						{
							log.info("Date Exception:" + e.getMessage());
						}
						String fatherName = dataFromAadharObj.isNull("fatherName") ? "" : dataFromAadharObj.getString("fatherName");
						
						log.debug("fatherName: "+fatherName);
						
						String gender = dataFromAadharObj.isNull("gender") ? "" : dataFromAadharObj.getString("gender");
						String mobileHash = dataFromAadharObj.isNull("mobileHash") ? "" : dataFromAadharObj.getString("mobileHash");
						String emailHash = dataFromAadharObj.isNull("emailHash") ? "" : dataFromAadharObj.getString("emailHash");
						
						String houseNumber = splitAddressObj.isNull("houseNumber")? "": splitAddressObj.getString("houseNumber");
						String street = splitAddressObj.isNull("street")? "": splitAddressObj.getString("street");
						String landmark = splitAddressObj.isNull("landmark")? "": splitAddressObj.getString("landmark");
						String subdistrict = splitAddressObj.isNull("subdistrict")? "": splitAddressObj.getString("subdistrict"); 
						String district = splitAddressObj.isNull("district")? "": splitAddressObj.getString("district");
						String vtcName = splitAddressObj.isNull("vtcName")? "": splitAddressObj.getString("vtcName");
						String location = splitAddressObj.isNull("location")? "": splitAddressObj.getString("location");
						String postOffice = splitAddressObj.isNull("postOffice")? "": splitAddressObj.getString("postOffice");
						String state = splitAddressObj.isNull("state")? "": splitAddressObj.getString("state");
						String country = splitAddressObj.isNull("country")? "": splitAddressObj.getString("country");
						String pincode = splitAddressObj.isNull("pincode")? "": splitAddressObj.getString("pincode");				
																		
						Map stateCityPinIdMap = customerBO.fetchStateCityPincodeId("", vtcName,
								pincode,state, dbConnectionMap);
						log.debug(stateCityPinIdMap);
						String stateId = (String) stateCityPinIdMap.get("STATE_ID");
						String cityId = (String) stateCityPinIdMap.get("CITY_ID");
						String pincodeId = (String) stateCityPinIdMap.get("PIN_ID");

						String address1 = houseNumber;
						if (isEmptyOrBlank(stateId) && !isEmptyOrBlank(state)) {
							address1 = address1 + " State " + state;
						}
						String address2 = street;
						if (isEmptyOrBlank(cityId) && !isEmptyOrBlank(vtcName)) {
							address2 = address2 + " City " + vtcName;
						}
						String address3 = location+" "+district;
						if (isEmptyOrBlank(pincodeId) && !isEmptyOrBlank(pincode)) {
							address3 = address3 + " Pin " + pincode;
						}
						String address4 = landmark + " "+subdistrict;
						String address5 = postOffice +" "+country;
						
						String[] addresses = {address1,address2,address3,address4,address5};
						List<String> addressList = getAddressList(addresses);
							address1 = addressList.get(0).trim();
							address2 = addressList.get(1).trim();
							address3 = addressList.get(2).trim();
							address4 = addressList.get(3).trim();
							address5 = addressList.get(4).trim();
						
						
						responseMap.put("correlationId", correlationId);
						responseMap.put("requestType", reqType);
						responseMap.put("responseStatus", responseStatus);

						responseMap.put("name", name);
						responseMap.put("dob", formattedDob);
						responseMap.put("fatherName", fatherName);
						responseMap.put("gender", gender);
						responseMap.put("mobileHash", mobileHash);
						responseMap.put("emailHash", emailHash);
						
						responseMap.put("address1", address1);
						responseMap.put("address2", address2);
						responseMap.put("address3", address3);
						responseMap.put("address4", address4);
						responseMap.put("address5", address5);
						responseMap.put("state", state);
						responseMap.put("vtcName", vtcName);
						responseMap.put("pincode", pincode);
						responseMap.put("stateId", stateId);
						responseMap.put("cityId", cityId);
						responseMap.put("pincodeId", pincodeId);
						
					} else {

						responseMap.put("transId", correlationId);
						responseMap.put("requestType", reqType);
						responseMap.put("responseStatus", "Failure");
						responseMap.put("responseMsg", errorInfoObj.getString("message"));
					}

				} else {
					responseMap.put("transId", correlationId);
					responseMap.put("requestType", reqType);
					responseMap.put("responseStatus", "Failure");
					responseMap.put("responseMsg", "Response not generated, Please try again.");
				}
			}

		} catch (Exception ex) {
			responseMap.put("responseStatus", "Failure");
			responseMap.put("responseMsg", "Please try again, Connect to Admin Team");
			log.info("Exception:" + ex.getMessage());
		}
		try {
			Gson gson = new Gson();
			PrintWriter out = response.getWriter();
			out.print(gson.toJson(responseMap));
			out.flush();
			out.close();
		} catch (Exception ex) {

		}
		return null;
	}
	
	public List<String> getAddressList(String[] addresses)
	{

		List<String> addressList = new ArrayList<>();
		if (isValidAddress(addresses)) {
			addressList = Arrays.asList(addresses);

		} else {

			String fullAddress = buidAdress(addresses);
			if (fullAddress.length() > 250) {
				fullAddress = fullAddress.substring(0, FULL_ADDRESS_LENGTH);
				fullAddress = getCompleteAddress(fullAddress);
			}
			String address1 = fullAddress;
			if (fullAddress.length() > 50) {
				address1 = fullAddress.substring(0, ADDRESS_LENGTH);
				address1 = getCompleteAddress(address1);
			}

			fullAddress = fullAddress.substring(address1.length(), fullAddress.length());
			String address2 = fullAddress;
			if (fullAddress.length() > 50) {
				address2 = fullAddress.substring(0, ADDRESS_LENGTH);
				address2 = getCompleteAddress(address2);
			}

			fullAddress = fullAddress.substring(address2.length(), fullAddress.length());
			String address3 = fullAddress;
			if (fullAddress.length() > 50) {
				address3 = fullAddress.substring(0, ADDRESS_LENGTH);
				address3 = getCompleteAddress(address3);
			}

			fullAddress = fullAddress.substring(address3.length(), fullAddress.length());
			String address4 = fullAddress;
			if (fullAddress.length() > 50) {
				address4 = fullAddress.substring(0, ADDRESS_LENGTH);
				address4 = getCompleteAddress(address4);
			}

			fullAddress = fullAddress.substring(address4.length(), fullAddress.length());
			String address5 = fullAddress;
			if (fullAddress.length() > 50) {
				address5 = fullAddress.substring(0, ADDRESS_LENGTH);
				address5 = getCompleteAddress(address5);
			}
			addressList.add(address1);
			addressList.add(address2);
			addressList.add(address3);
			addressList.add(address4);
			addressList.add(address5);
		}
		return addressList;

	}
	
	public String getCompleteAddress(String fullAddress) {
		Character[] chars = new Character[]{' ', ',', ';'};
		List<Character> charList = Arrays.asList(chars);
		int n = -99;
		char lastChar = fullAddress.charAt(fullAddress.length() - 1);
		if(!charList.contains(lastChar)) {
			for(char ch : charList) {
				n = fullAddress.lastIndexOf(ch);
				if(n != -1) {
					return fullAddress.substring(0, n);
				}
			}
		}
		n = fullAddress.lastIndexOf(lastChar);
		if (n != -1) {
			fullAddress = fullAddress.substring(0, n);
		}
		return fullAddress;
		
	}
	
	private boolean isValidAddress(String[] addresses) {
		for(String address: addresses) {
			if(address != null && address.length() > 50) {
				return false;
			}
		}
		return true;
	}
	
	private String buidAdress(String[] addresses) {
		StringBuilder fullAdress = new StringBuilder();
		for(String address: addresses) {
			fullAdress.append(address);	
		}
		return fullAdress.toString();
	}
	
	private boolean isEmptyOrBlank(String value) {
		if(value == null || value.trim().length() <= 0)
			return true;
		return false;
	}

	
	//1.0.0.4 Start 
	public HashMap vahanApiCall(String vahanReqDataJson,String reqType,String requestId, Map dbConnectionMapLOS) {
		List list = new ArrayList();
		URL url = null;
		HttpURLConnection httpsConnection = null;
		//HttpSession session = request.getSession();
		//ServletContext ctx = session.getServletContext();
		dbConnectionMap = (HashMap) dbConnectionMapLOS;
		LinkedHashMap requestMap = new LinkedHashMap();
		String correlationId="";
		HashMap responseMap = new HashMap();
		try {
			
			//log.debug("chasiNo:" + chasiNo +" ,eng_no:"+engNo +" ,hpa_from:"+hpaFrom+" ,hpa_upto:"+hpaUpTo );
			
			JSONObject reqJson = new JSONObject(vahanReqDataJson);
			
			LinkedHashMap requestHeaderMap = new LinkedHashMap();
			correlationId = "VAHAN_"+reqType + "_" + Math.random();
			log.debug("VahanHPACorrelationId:" + correlationId);
			requestHeaderMap.put("msgVersion", commonApplicationResource.getString("MSG_VERSION"));
			requestHeaderMap.put("appId", commonApplicationResource.getString("IBS_VAHAN_APP_ID"));
			requestHeaderMap.put("correlationId", correlationId);
			requestHeaderMap.put("token", commonApplicationResource.getString("IBS_VAHAN_TOKEN"));

			LinkedHashMap payloadMap = new LinkedHashMap();
			if(reqType.equalsIgnoreCase("HPA")) {
				payloadMap.put("chasiNo", reqJson.get("chasiNo"));
				payloadMap.put("eng_no", reqJson.get("eng_no"));			
				payloadMap.put("hpa_from", reqJson.get("hpa_from"));
				payloadMap.put("hpa_upto", reqJson.get("hpa_upto"));
				payloadMap.put("docurl", reqJson.get("docurl"));
				payloadMap.put("reqType", reqJson.get("reqType"));
				payloadMap.put("companyName", reqJson.get("companyName"));
			}else if(reqType.equalsIgnoreCase("HPC")) {
				payloadMap.put("regnNo", reqJson.get("regnNo"));
				payloadMap.put("chasiNo", reqJson.get("chassisNo"));			
				payloadMap.put("hpa_from", reqJson.get("hpa_from"));
				payloadMap.put("hpa_upto", reqJson.get("hpa_upto"));
				payloadMap.put("docurl", reqJson.get("docurl"));
				payloadMap.put("reqType", reqJson.get("reqType"));
				payloadMap.put("companyName", reqJson.get("companyName"));
			}else if(reqType.equalsIgnoreCase("HPT")) {
				payloadMap.put("regnNo", reqJson.get("regnNo"));
				payloadMap.put("chassisNo", reqJson.get("chassisNo"));			
				payloadMap.put("terminationDt", reqJson.get("terminationDt"));
				payloadMap.put("docUrl", reqJson.get("docUrl"));
				payloadMap.put("reqType", reqJson.get("reqType"));
				payloadMap.put("companyName", reqJson.get("companyName"));
			}

			requestMap.put("header", requestHeaderMap);
			requestMap.put("payload", payloadMap);

		} catch (Exception ex) {
			log.error(ex.getMessage());
		}
		
		String requestUrl="";
		
		if(reqType.equalsIgnoreCase("HPA"))
			requestUrl = commonApplicationResource.getString("VAHAN_HPA_URL");
		else if(reqType.equalsIgnoreCase("HPC"))
			requestUrl = commonApplicationResource.getString("VAHAN_HPC_URL");
		else if(reqType.equalsIgnoreCase("HPT"))
			requestUrl = commonApplicationResource.getString("VAHAN_HPT_URL");
			
		int timeOut = Integer.parseInt(commonApplicationResource.getString("API_SERVICE_LOGOUT_IN_SEC"));// 100;
		JSONObject jsonObject = new JSONObject(requestMap);
		try {
			 String serviceOutput = ServiceCallAction.getResultString(requestMap, requestUrl, timeOut);
			 VahanBO vahanBO=new VahanBO();
			 vahanBO.logApiRequestResponse(requestId, reqType, jsonObject.toString(), serviceOutput, dbConnectionMapLOS);
			 if (serviceOutput != null && serviceOutput.startsWith("{")) {//1.0.0.13 start
				// Insert into qt_karza_integration_log table
				Gson gson = new Gson();
				String requestJson = gson.toJson(requestMap);
				String responseJson = serviceOutput;
				log.debug(responseJson);
				String status = "";
				String message = "";
				JSONObject jsonObjLogTable = new JSONObject(serviceOutput.toString());
				JSONObject infoObjLogTable = jsonObjLogTable.has("errorInfo") ? jsonObjLogTable.getJSONObject("errorInfo")//1.0.0.13
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
						message = infoObjLogTable.getString("message");//1.0.0.13
					}
					
					//status = infoObjLogTable.getString("status") != null ? infoObjLogTable.getString("status") : null;
					//
					//message = infoObjLogTable.getString("message") != null ? infoObjLogTable.getString("message") : null;
				}

				//String insertStatus = customerBO.saveKarzaReqLog(aadhaarNo, reqType, requestJson, responseJson, status,
				//	message, dbConnectionMap);
				//log.debug("insertStatus:" + insertStatus);
				if (!serviceOutput.isEmpty() && !serviceOutput.equalsIgnoreCase(null)
						&& !serviceOutput.equalsIgnoreCase("") && !serviceOutput.startsWith("Error,")) {
					JSONObject jsonObj = new JSONObject(serviceOutput.toString());
					JSONObject errorInfoObj = jsonObj.has("errorInfo") ? jsonObj.getJSONObject("errorInfo") : null;

					if (errorInfoObj != null && errorInfoObj.getString("status") != null
							&& errorInfoObj.getString("status").equalsIgnoreCase("SUCCESS")) {
						message="";
						JSONObject payloadObj = jsonObj.getJSONObject("payload"); 
						JSONObject headerObj = jsonObj.getJSONObject("header");
						//JSONObject resultObj = payloadObj.has("result") ? payloadObj.getJSONObject("result") : null;
						//JSONObject dataFromVahanObj = resultObj.has("encData") ? resultObj.getJSONObject("encData") : null;
						
						String responseCode = payloadObj.getString("responseCode");
						String refrenceNumber = payloadObj.getString("refrenceNumber");
						String responseMessage = payloadObj.getString("responseMessage");
						correlationId = headerObj.getString("correlationId");

						
						 responseMap.put("responseCode", responseCode);
						 responseMap.put("refrenceNumber", refrenceNumber);
						 responseMap.put("responseMessage", responseMessage);
						 responseMap.put("correlationId", correlationId);
						 responseMap.put("responseMsg", responseMessage);
						
					} else {

						responseMap.put("transId", correlationId);
						responseMap.put("requestType", reqType);
						responseMap.put("responseStatus", "Failure");
						responseMap.put("responseMsg", errorInfoObj.getString("message"));
					}

				} else {
					responseMap.put("transId", correlationId);
					responseMap.put("requestType", reqType);
					responseMap.put("responseStatus", "Failure");
					responseMap.put("responseMsg", message!=null&&!message.equals("")?message:"Response not generated, Please try again.");
				}
			}else {
				responseMap.put("responseStatus", "Failure");
				responseMap.put("responseMsg", serviceOutput);
			}

		} catch (Exception ex) {
			responseMap.put("responseStatus", "Failure");
			responseMap.put("responseMsg", "Error:"+ex.getMessage());
			log.info("Exception:" + ex.getMessage());
		}
		
		return responseMap;
	}//1.0.0.13 end
	
	// 1.0.0.4 End
	
	//1.0.0.6 Start
	public ActionForward passportNoVerifier(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		List list = new ArrayList();
		URL url = null;
		HttpURLConnection httpsConnection = null;
		HttpSession session = request.getSession();
		ServletContext ctx = session.getServletContext();
		dbConnectionMap = (HashMap) ctx.getAttribute("dbConnectionMapLOS");
		LinkedHashMap requestMap = new LinkedHashMap();
		String correlationId = "";
		String reqType = "KARZA_PASSPORT";
		String passportNo = "";
		String dob = "";
		String name = "";
		String dateOfBirth = "";
		String doi = "";
		String fileNo = "";
		SimpleDateFormat inputFormat = new SimpleDateFormat("dd-MMM-yyyy");
		SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy");
		try {
			dob = request.getParameter("dob");
			doi = request.getParameter("doi");
			Date date = inputFormat.parse(dob);// Parse the input date string to a Date object
            dob = outputFormat.format(date);
			Date date1 = inputFormat.parse(doi);// Parse the input date string to a Date object
			doi = outputFormat.format(date1);
            
        } catch (ParseException e) {
        	log.error(e.getMessage());
        }

		try {
			passportNo = request.getParameter("passportNoValue");
			name = request.getParameter("name");
			//dob = request.getParameter("dob");
			fileNo = request.getParameter("fileNo");
			log.debug("passportNo:" + passportNo);
			LinkedHashMap requestHeaderMap = new LinkedHashMap();
			correlationId = reqType + "_" + Math.random();
			//correlationId = "KARZA";
			log.debug("PassportCorrelationId:" + correlationId);
			requestHeaderMap.put("msgVersion", commonApplicationResource.getString("MSG_VERSION"));
			requestHeaderMap.put("appId", commonApplicationResource.getString("APP_ID"));
			requestHeaderMap.put("correlationId", correlationId);
			requestHeaderMap.put("token", commonApplicationResource.getString("TOKEN"));

			LinkedHashMap payloadMap = new LinkedHashMap();
			payloadMap.put("passportNo", passportNo);
			payloadMap.put("consent", "Y");
			payloadMap.put("doi", doi);
			payloadMap.put("fileNo", fileNo);
			payloadMap.put("name", name);
			payloadMap.put("dob", dob);

			requestMap.put("header", requestHeaderMap);
			requestMap.put("payload", payloadMap);

		} catch (Exception ex) {
			log.error(ex.getMessage());
		}
		String requestUrl = commonApplicationResource.getString("PASSPORT_VERIFY_URL");
		//String requestUrl = "http://10.1.2.117:8480/ibs/ib/karza/api/v3/requestKarzaPassport";
		int timeOut = Integer.parseInt(commonApplicationResource.getString("API_SERVICE_LOGOUT_IN_SEC"));// 100;
		HashMap responseMap = new HashMap();
		try {
			 String serviceOutput = ServiceCallAction.getResultString(requestMap, requestUrl, timeOut);
			//String serviceOutput ="";
			
			 if (serviceOutput != null) {
				// Insert into qt_karza_integration_log table
				Gson gson = new Gson();
				String requestJson = gson.toJson(requestMap);
				String responseJson = serviceOutput;
				log.debug(responseJson);
				String status = "";
				String message = "";
				JSONObject jsonObjLogTable = new JSONObject(serviceOutput.toString());
				JSONObject infoObjLogTable = jsonObjLogTable.has("msgInfo") ? jsonObjLogTable.getJSONObject("msgInfo")
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
						status = infoObjLogTable.getString("message");
					}
					
					//status = infoObjLogTable.getString("status") != null ? infoObjLogTable.getString("status") : null;
					
					//message = infoObjLogTable.getString("message") != null ? infoObjLogTable.getString("message") : null;
				}

				String insertStatus = customerBO.saveKarzaReqLog(passportNo, reqType, requestJson, responseJson, status,
					message, dbConnectionMap);
				log.debug("insertStatus:" + insertStatus);
				if (!serviceOutput.isEmpty() && !serviceOutput.equalsIgnoreCase(null)
						&& !serviceOutput.equalsIgnoreCase("") && !serviceOutput.startsWith("Error,")) {
					JSONObject jsonObj = new JSONObject(serviceOutput.toString());
					JSONObject errorInfoObj = jsonObj.has("msgInfo") ? jsonObj.getJSONObject("msgInfo") : null;

					if (errorInfoObj != null && errorInfoObj.getString("status") != null
							&& errorInfoObj.getString("status").equalsIgnoreCase("SUCCESS")) {
						JSONObject payloadObj = jsonObj.getJSONObject("payload"); // datainfo=
						JSONObject headerObj = jsonObj.getJSONObject("header");
						JSONObject resultObj = payloadObj.has("result") ? payloadObj.getJSONObject("result") : null;
						
						JSONObject PassportObj = resultObj.has("passportNumber") ? resultObj.getJSONObject("passportNumber") : null;
						JSONObject dateOfIssueObj = resultObj.has("dateOfIssue") ? resultObj.getJSONObject("dateOfIssue") : null;
						JSONObject nameObj = resultObj.has("name") ? resultObj.getJSONObject("name") : null;
						
						String passportNumberMatch = PassportObj.getString("passportNumberMatch");
						String dateOfIssueMatch = dateOfIssueObj.getString("dateOfIssueMatch");
						String nameMatch = nameObj.getString("nameMatch");

						String responseStatus = errorInfoObj.getString("status");
						correlationId = headerObj.getString("correlationId");

						//String name1 = resultObj.getString("name");

						responseMap.put("correlationId", correlationId);
						responseMap.put("requestType", reqType);
						responseMap.put("responseStatus", responseStatus);
						
						responseMap.put("passportNumberMatch", passportNumberMatch.toUpperCase());
						responseMap.put("dateOfIssueMatch", dateOfIssueMatch.toUpperCase());
						responseMap.put("nameMatch", nameMatch.toUpperCase());

						//responseMap.put("name", name);
						//responseMap.put("payloadObj", payloadObj);
						responseMap.put("resultObj", resultObj);

					} else {

						responseMap.put("transId", correlationId);
						responseMap.put("requestType", reqType);
						responseMap.put("responseStatus", "Failure");
						responseMap.put("responseMsg", errorInfoObj.getString("message"));
					}

				} else {
					responseMap.put("transId", correlationId);
					responseMap.put("requestType", reqType);
					responseMap.put("responseStatus", "Failure");
					responseMap.put("responseMsg", "Response not generated, Please try again.");
				}
			}

		} catch (Exception ex) {
			responseMap.put("responseStatus", "Failure");
			responseMap.put("responseMsg", "Please try again, Connect to Admin Team");
			log.info("Exception:" + ex.getMessage());
		}
		try {
			Gson gson = new Gson();
			PrintWriter out = response.getWriter();
			out.print(gson.toJson(responseMap));
			out.flush();
			out.close();
		} catch (Exception ex) {

		}
		return null;
	}
	//1.0.0.6 End
	
	
	
	//1.0.0.7 start
		public ActionForward dlVerifier(ActionMapping mapping, ActionForm form, HttpServletRequest request,
				HttpServletResponse response) {
			List list = new ArrayList();
			URL url = null;
			HttpURLConnection httpsConnection = null;
			HttpSession session = request.getSession();
			ServletContext ctx = session.getServletContext();
			dbConnectionMap = (HashMap) ctx.getAttribute("dbConnectionMapLOS");
			LinkedHashMap requestMap = new LinkedHashMap();
			String correlationId = "";
			String reqType = "KARZA_DL";
			//String panNo = "";
			String dl_no="";
			String dateOfbirth="";
			try {
				//panNo = request.getParameter("panNoValue");
				//log.debug("panNo:" + panNo);
				dl_no=request.getParameter("drivingLicence");
				dateOfbirth=request.getParameter("dob");
				correlationId = reqType + "_" + Math.random();
				//correlationId="KARZA";
				SimpleDateFormat inputFormat = new SimpleDateFormat("dd-MMM-yyyy");
		        SimpleDateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy");
	             Date date = inputFormat.parse(dateOfbirth);
	            
	            // Format the parsed date to the desired output format
	            String dob = outputFormat.format(date);

				LinkedHashMap requestHeaderMap = new LinkedHashMap();
				//correlationId = reqType + "_" + Math.random();
				//log.debug("PANCorrelationId:" + correlationId);
				requestHeaderMap.put("msgVersion", commonApplicationResource.getString("MSG_VERSION"));
				requestHeaderMap.put("appId", commonApplicationResource.getString("APP_ID"));
				requestHeaderMap.put("correlationId", correlationId);
				requestHeaderMap.put("token", commonApplicationResource.getString("TOKEN"));

				LinkedHashMap payloadMap = new LinkedHashMap();
				payloadMap.put("dl_no", dl_no);
				payloadMap.put("dob", dob);
				payloadMap.put("consent", "Y");

				requestMap.put("header", requestHeaderMap);
				requestMap.put("payload", payloadMap);

			} catch (Exception ex) {
				log.error(ex.getMessage());
			}
			String requestUrl = commonApplicationResource.getString("DL_VERI_URL");
			int timeOut = Integer.parseInt(commonApplicationResource.getString("API_SERVICE_LOGOUT_IN_SEC"));// 100;
			HashMap responseMap = new HashMap();
			try {
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
					JSONObject infoObjLogTable = jsonObjLogTable.has("msgInfo") ? jsonObjLogTable.getJSONObject("msgInfo")
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
							status = infoObjLogTable.getString("message");
						}
						
						//status = infoObjLogTable.getString("status") != null ? infoObjLogTable.getString("status") : null;
						
						//message = infoObjLogTable.getString("message") != null ? infoObjLogTable.getString("message") : null;
					}

					String insertStatus = customerBO.saveKarzaReqLog(dl_no, reqType, requestJson, responseJson, status,
						message, dbConnectionMap);
					log.debug("insertStatus:" + insertStatus);
					if (!serviceOutput.isEmpty() && !serviceOutput.equalsIgnoreCase(null)
							&& !serviceOutput.equalsIgnoreCase("") && !serviceOutput.startsWith("Error,")) {
						JSONObject jsonObj = new JSONObject(serviceOutput.toString());
						JSONObject errorInfoObj = jsonObj.has("msgInfo") ? jsonObj.getJSONObject("msgInfo") : null;

						if (errorInfoObj != null && errorInfoObj.getString("status") != null
								&& errorInfoObj.getString("status").equalsIgnoreCase("SUCCESS")) {
							JSONObject payloadObj = jsonObj.getJSONObject("payload"); // datainfo=
							JSONObject headerObj = jsonObj.getJSONObject("header");
							JSONObject resultObj = payloadObj.has("result") ? payloadObj.getJSONObject("result") : null;

							String responseStatus = errorInfoObj.getString("status");
							correlationId = headerObj.getString("correlationId");

							String name = resultObj.getString("name");

							responseMap.put("correlationId", correlationId);
							responseMap.put("requestType", reqType);
							responseMap.put("responseStatus", responseStatus);

							responseMap.put("name", name);

						} else {

							responseMap.put("transId", correlationId);
							responseMap.put("requestType", reqType);
							responseMap.put("responseStatus", "Failure");
							responseMap.put("responseMsg", errorInfoObj.getString("message"));
						}

					} else {
						responseMap.put("transId", correlationId);
						responseMap.put("requestType", reqType);
						responseMap.put("responseStatus", "Failure");
						responseMap.put("responseMsg", "Response not generated, Please try again.");
					}
				}

			} catch (Exception ex) {
				responseMap.put("responseStatus", "Failure");
				responseMap.put("responseMsg", "Please try again, Connect to Admin Team");
				log.info("Exception:" + ex.getMessage());
			}
			try {
				Gson gson = new Gson();
				PrintWriter out = response.getWriter();
				out.print(gson.toJson(responseMap));
				out.flush();
				out.close();
			} catch (Exception ex) {

			}
			return null;
		}
		//1.0.0.7 end
		
		
		
		//1.0.0.8 Start
		public ActionForward verifyVoterId(ActionMapping mapping, ActionForm form, HttpServletRequest request,
				HttpServletResponse response) {
			List list = new ArrayList();
			URL url = null;
			HttpURLConnection httpsConnection = null;
			HttpSession session = request.getSession();
			ServletContext ctx = session.getServletContext();
			dbConnectionMap = (HashMap) ctx.getAttribute("dbConnectionMapLOS");
			LinkedHashMap requestMap = new LinkedHashMap();
			String correlationId = "";
			String reqType = "KARZA_VOTER";
			String epic_no = "";
			try {
				epic_no = request.getParameter("voterIDValue");
				log.debug("epic_no:" + epic_no);

				LinkedHashMap requestHeaderMap = new LinkedHashMap();
				correlationId = reqType + "_" + Math.random();
				//correlationId ="KARZA";
				log.debug("VOTERCorrelationId:" + correlationId);
				requestHeaderMap.put("msgVersion", commonApplicationResource.getString("MSG_VERSION"));
				requestHeaderMap.put("appId", commonApplicationResource.getString("APP_ID"));
				requestHeaderMap.put("correlationId", correlationId);
				requestHeaderMap.put("token", commonApplicationResource.getString("TOKEN"));

				LinkedHashMap payloadMap = new LinkedHashMap();
				payloadMap.put("epic_no", epic_no);
				payloadMap.put("consent", "Y");

				requestMap.put("header", requestHeaderMap);
				requestMap.put("payload", payloadMap);

			} catch (Exception ex) {
				log.error(ex.getMessage());
			}
			String requestUrl = commonApplicationResource.getString("VERIFY_VOTER_URL");
			int timeOut = Integer.parseInt(commonApplicationResource.getString("API_SERVICE_LOGOUT_IN_SEC"));// 100;
			HashMap responseMap = new HashMap();
			try {
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
					JSONObject infoObjLogTable = jsonObjLogTable.has("msgInfo") ? jsonObjLogTable.getJSONObject("msgInfo")
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
							status = infoObjLogTable.getString("message");
						}
						
						//status = infoObjLogTable.getString("status") != null ? infoObjLogTable.getString("status") : null;
						
						//message = infoObjLogTable.getString("message") != null ? infoObjLogTable.getString("message") : null;
					}

					String insertStatus = customerBO.saveKarzaReqLog(epic_no, reqType, requestJson, responseJson, status,
						message, dbConnectionMap);
					log.debug("insertStatus:" + insertStatus);
					if (!serviceOutput.isEmpty() && !serviceOutput.equalsIgnoreCase(null)
							&& !serviceOutput.equalsIgnoreCase("") && !serviceOutput.startsWith("Error,")) {
						JSONObject jsonObj = new JSONObject(serviceOutput.toString());
						JSONObject errorInfoObj = jsonObj.has("msgInfo") ? jsonObj.getJSONObject("msgInfo") : null;

						if (errorInfoObj != null && errorInfoObj.getString("status") != null
								&& errorInfoObj.getString("status").equalsIgnoreCase("SUCCESS")) {
							JSONObject payloadObj = jsonObj.getJSONObject("payload"); // datainfo=
							JSONObject headerObj = jsonObj.getJSONObject("header");
							JSONObject resultObj = payloadObj.has("result") ? payloadObj.getJSONObject("result") : null;

							String responseStatus = errorInfoObj.getString("status");
							correlationId = headerObj.getString("correlationId");

							String name = resultObj.getString("name");

							responseMap.put("correlationId", correlationId);
							responseMap.put("requestType", reqType);
							responseMap.put("responseStatus", responseStatus);

							responseMap.put("name", name);

						} else {

							responseMap.put("transId", correlationId);
							responseMap.put("requestType", reqType);
							responseMap.put("responseStatus", "Failure");
							responseMap.put("responseMsg", errorInfoObj.getString("message"));
						}

					} else {
						responseMap.put("transId", correlationId);
						responseMap.put("requestType", reqType);
						responseMap.put("responseStatus", "Failure");
						responseMap.put("responseMsg", "Response not generated, Please try again.");
					}
				}

			} catch (Exception ex) {
				responseMap.put("responseStatus", "Failure");
				responseMap.put("responseMsg", "Please try again, Connect to Admin Team");
				log.info("Exception:" + ex.getMessage());
			}
			try {
				Gson gson = new Gson();
				PrintWriter out = response.getWriter();
				out.print(gson.toJson(responseMap));
				out.flush();
				out.close();
			} catch (Exception ex) {

			}
			return null;
		}
		
		//1.0.0.8 End


		//1.0.0.9 start
				public ActionForward validateUdyamNo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
						HttpServletResponse response) {
					List list = new ArrayList();
					URL url = null;
					HttpURLConnection httpsConnection = null;
					HttpSession session = request.getSession();
					ServletContext ctx = session.getServletContext();
					dbConnectionMap = (HashMap) ctx.getAttribute("dbConnectionMapLOS");
					LinkedHashMap requestMap = new LinkedHashMap();
					String correlationId = "";
					String reqType = "KARZA_UDYAM";
					String udyamNo = "";
					try {
						udyamNo = request.getParameter("udyamNumber");
						log.debug("udyamNo:" + udyamNo);

						LinkedHashMap requestHeaderMap = new LinkedHashMap();
						correlationId = reqType + "_" + Math.random();
						String caseId=RandomStringUtils.random(6, 0, 10, false, true, "0123456789".toCharArray());
						log.debug("UDYAMCorrelationId:" + correlationId);
						requestHeaderMap.put("msgVersion", commonApplicationResource.getString("MSG_VERSION"));
						requestHeaderMap.put("appId", commonApplicationResource.getString("APP_ID"));
						requestHeaderMap.put("correlationId", correlationId);
						requestHeaderMap.put("token", commonApplicationResource.getString("TOKEN"));

						LinkedHashMap payloadMap = new LinkedHashMap();
				
						payloadMap.put("consent", "Y");
						payloadMap.put("udyamRegistrationNo", udyamNo);
						payloadMap.put("isPDFRequired", "N");
						payloadMap.put("getEnterpriseDetails", "Y");
						
						LinkedHashMap clientDataMap = new LinkedHashMap();
						clientDataMap.put("caseId", caseId);
						
						payloadMap.put("clientData", clientDataMap);

						requestMap.put("header", requestHeaderMap);
						requestMap.put("payload", payloadMap);

					} catch (Exception ex) {
						log.error(ex.getMessage());
					}
					String requestUrl = commonApplicationResource.getString("UDYAM_VERI_URL");
					int timeOut = Integer.parseInt(commonApplicationResource.getString("API_SERVICE_LOGOUT_IN_SEC"));
					HashMap responseMap = new HashMap();
					try {
						 String serviceOutput = ServiceCallAction.getResultString(requestMap, requestUrl, timeOut);
						
						 if (serviceOutput != null) {
							Gson gson = new Gson();
							String requestJson = gson.toJson(requestMap);
							String responseJson = serviceOutput;
							log.debug(responseJson);
							String status = "";
							String message = "";
							JSONObject jsonObjLogTable = new JSONObject(serviceOutput.toString());
							JSONObject infoObjLogTable = jsonObjLogTable.has("msgInfo") ? jsonObjLogTable.getJSONObject("msgInfo")
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
									status = infoObjLogTable.getString("message");
								}
								
							}

							String insertStatus = customerBO.saveKarzaReqLog(udyamNo, reqType, requestJson, responseJson, status,
								message, dbConnectionMap);
							log.debug("insertStatus:" + insertStatus);
							if (!serviceOutput.isEmpty() && !serviceOutput.equalsIgnoreCase(null)
									&& !serviceOutput.equalsIgnoreCase("") && !serviceOutput.startsWith("Error,")) {
								JSONObject jsonObj = new JSONObject(serviceOutput.toString());
								JSONObject errorInfoObj = jsonObj.has("msgInfo") ? jsonObj.getJSONObject("msgInfo") : null;

								if (errorInfoObj != null && errorInfoObj.getString("status") != null
										&& errorInfoObj.getString("status").equalsIgnoreCase("SUCCESS")) {
									JSONObject payloadObj = jsonObj.getJSONObject("payload");
									JSONObject headerObj = jsonObj.getJSONObject("header");
									JSONObject resultObj = payloadObj.has("result") ? payloadObj.getJSONObject("result") : null;

									String responseStatus = errorInfoObj.getString("status");
									correlationId = headerObj.getString("correlationId");
									responseMap.put("correlationId", correlationId);
									responseMap.put("requestType", reqType);
									responseMap.put("responseStatus", responseStatus);


								} else {

									responseMap.put("transId", correlationId);
									responseMap.put("requestType", reqType);
									responseMap.put("responseStatus", "Failure");
									responseMap.put("responseMsg", errorInfoObj.getString("message"));
								}

							} else {
								responseMap.put("transId", correlationId);
								responseMap.put("requestType", reqType);
								responseMap.put("responseStatus", "Failure");
								responseMap.put("responseMsg", "Response not generated, Please try again.");
							}
						}

					} catch (Exception ex) {
						responseMap.put("responseStatus", "Failure");
						responseMap.put("responseMsg", "Please try again, Connect to Admin Team");
						log.info("Exception:" + ex.getMessage());
					}
					try {
						Gson gson = new Gson();
						PrintWriter out = response.getWriter();
						out.print(gson.toJson(responseMap));
						out.flush();
						out.close();
					} catch (Exception ex) {

					}
					return null;
				}//1.0.0.9 end
				
				//start 1.0.0.11
				public HashMap createUpdateUcic(ActionMapping mapping, ActionForm form, HttpServletRequest request,
						String prospectId) {
					log.info("createUpdateUcic - start");
					List list = new ArrayList();
					URL url = null;
					HttpURLConnection httpsConnection = null;
					HttpSession session = request.getSession();
					ServletContext ctx = session.getServletContext();
					dbConnectionMap = (HashMap) ctx.getAttribute("dbConnectionMapLOS");
					
					//fetch data from db start
					LinkedHashMap payloadMap = new LinkedHashMap();
					String applicantTypeId="";
					//String applicantId="";
					String responseStatus="";
					String responseDescription="";
					String userIdExp="";
					DisbursalActionBO disbursalActionBO = new DisbursalActionBO();
					//applicantId=request.getParameter("applicantId");
				String email_id="";
				String country="";
				String entity_name="";
				String city="";
				String hashed_ckyc_id="";
				String product_account_id="";
				String product_name="";
				String full_name="";
				String entity_code="2";
				String dob="";
				String address_line_1="";
				String pan_type="";
				String customer_pin_code="";
				String state="";
				String address_line_2="";
				String product_lead_id="";
				String mobile_number="";
				String product_start_date="";
				String product_end_date="";
				String hashed_pan_number="";
				String applicant_type="";
				String product_amount="";
				String customerId="";
				String UCIC_EXIST_FLAG="";
				String CAPITALIZED_DM_EXISTS_FLAG="";
				String isApiCalled="N";
				String constitution="";//1.0.0.12
				String id="";//1.0.0.14
				HashMap responseMap = new HashMap();
					Long bureauScore=null;
					String UserMessageText="";
				//	Map getPersonalData= personalInfoBO.getApplicantDataExperian(Integer.parseInt(applicantId),Integer.parseInt(applicantTypeId),dbConnectionMap);
					
				//	customerListDTO =(CustomerListDTO) getPersonalData.get("customerListDTO");
					HashMap getDataForUcic=disbursalActionBO.getUcicRequiredData(prospectId,dbConnectionMap);
					List<Map<String, Object>> ucicList = 
					        (List<Map<String, Object>>) getDataForUcic.get("PCUR_OUT_UCIC_DTL");

					// Check list is not null or empty
					if (ucicList != null && !ucicList.isEmpty()) {

					    for (Map<String, Object> row : ucicList) {

					        if (row != null && !row.isEmpty()) {
			            
					            email_id=(String) row.get("EMAIL_ID");
								 country=(String) row.get("");
								 entity_name=(String) row.get("ENTITY_NAME");
								city=(String) row.get("CITY_NAME");
								 //hashed_ckyc_id=(String) row.get("EMAIL_ID");
								 product_account_id=(String) row.get("DM_CODE");
								product_name=(String) row.get("PRODUCT");
								full_name=(String) row.get("CUSTOMER_NAME");
								//Long entity_code="";
								 dob=(String) row.get("DOB");
								 address_line_1=(String) row.get("ADDRESS");
								//pan_type=(String) row.get("EMAIL_ID");
								 customer_pin_code=(String) row.get("PINCODE");
								 state=(String) row.get("STATE_NAME");
								 address_line_2=(String) row.get("ADDRESS");
								 product_lead_id=(String) row.get("QUOTATION_ID");
								mobile_number=(String) row.get("MOBILE_NO");
								 product_start_date=(String) row.get("LEASE_START_DATE");
								 product_end_date=(String) row.get("LEASE_END_DATE");
								 hashed_pan_number=(String) row.get("PAN_NO");
								applicant_type=(String) row.get("APPLICANT_TYPE");
								 product_amount=(String) row.get("PO_AMOUNT");
								 customerId=(String) row.get("CUSTOMER_ID");
								 UCIC_EXIST_FLAG=(String) row.get("UCIC_EXIST_FLAG");
								 CAPITALIZED_DM_EXISTS_FLAG=(String) row.get("CAPITALIZED_DM_EXISTS_FLAG");
								 constitution=(String) row.get("CONSTITUTION"); //1.0.0.12
								 id = (String) row.get("ID"); //1.0.0.14
								 log.info("UCIC_EXIST_FLAG"+UCIC_EXIST_FLAG);
								 log.info("CAPITALIZED_DM_EXISTS_FLAG"+CAPITALIZED_DM_EXISTS_FLAG);
								if("N".equals(UCIC_EXIST_FLAG) && "N".equals(CAPITALIZED_DM_EXISTS_FLAG)) { //&& "N".equals(CAPITALIZED_DM_EXISTS_FLAG)
									if(isApiCalled.equals("N")){
										isApiCalled="Y";
									}
									
								 //add code
								 Map saveUcicData=null;
									JSONObject payloadObj=null;
									LinkedHashMap requestMap = new LinkedHashMap();
									String correlationId = "";
									String reqType = "UCIC";
									String pdfData="";
									LinkedHashMap reqPayload = new LinkedHashMap();//1.0.0.11
								//	String dateOfbirth="";
									try {
										correlationId = reqType + "_" + Math.random();
										//correlationId="KARZA";
									//	SimpleDateFormat inputFormat = new SimpleDateFormat("dd-MMM-yyyy");
								     //   SimpleDateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy");
							          //   Date date = inputFormat.parse(dateOfbirth);
							            
							            // Format the parsed date to the desired output format
							          //  String dob = outputFormat.format(date);
										String experian="SUREPASS";//"EXPERIAN";//1.0.0.11

										LinkedHashMap requestHeaderMap = new LinkedHashMap();

										correlationId = reqType + "_" + Math.random();
										//correlationId ="KARZA";
										log.debug("VOTERCorrelationId:" + correlationId);
										requestHeaderMap.put("msgVersion", commonApplicationResource.getString("MSG_VERSION"));
										requestHeaderMap.put("appId", commonApplicationResource.getString("UCIC_APP_ID"));
										requestHeaderMap.put("correlationId", correlationId);
										requestHeaderMap.put("token", commonApplicationResource.getString("UCIC_TOKEN"));

										
										payloadMap.put("email_id", email_id);
										payloadMap.put("country",country);
										payloadMap.put("entity_name", entity_name);
										payloadMap.put("city",city);
										payloadMap.put("hashed_ckyc_id", "N/A");
										payloadMap.put("product_account_id", product_account_id);
										payloadMap.put("product_name", product_name);
										payloadMap.put("full_name", full_name);
										payloadMap.put("entity_code", 2);
										payloadMap.put("dob", dob);
										payloadMap.put("address_line_1",address_line_1);
										payloadMap.put("pan_type", pan_type);
										payloadMap.put("customer_pin_code", customer_pin_code);
										payloadMap.put("state",state);
										payloadMap.put("address_line_2",address_line_2);
										payloadMap.put("product_lead_id", product_lead_id);
										payloadMap.put("mobile_number", mobile_number);
										payloadMap.put("product_start_date", product_start_date);
										payloadMap.put("product_end_date", product_end_date);
										payloadMap.put("hashed_pan_number", hashed_pan_number);
										payloadMap.put("applicant_type", applicant_type);
										payloadMap.put("product_amount", product_amount);
										payloadMap.put("constitution", constitution);//1.0.0.12
										

										requestMap.put("header", requestHeaderMap);
										requestMap.put("payload", payloadMap);

									} catch (Exception ex) {
										log.error(ex.getMessage());
									}
									String requestUrl = commonApplicationResource.getString("UCIC_URL");
									int timeOut = Integer.parseInt(commonApplicationResource.getString("API_SERVICE_LOGOUT_IN_SEC"));// 100;
									
									try {
										 
					//					String serviceOutput = "{\n  \"header\": {\n    \"appId\": \"SUREPASS\",\n    \"correlationId\": \"10000021832406142136\",\n    \"msgVersion\": \"1.0\",\n    \"token\": \"bfbc9935-7c1a-42c9-baca-97a7c435270e\"\n  },\n  \"payload\": {\n    \"mobile\": \"9560822035\",\n    \"pan\": \"CMKPM0270L\",\n    \"name\": \"Ashutosh Mishra\",\n    \"gender\": \"male\",\n    \"consent\": \"Y\"\n}\n}\nIB-RESPONSE\n{\n    \"header\": {\n        \"appId\": \"SUREPASS\",\n        \"correlationId\": \"10000021832406142136\",\n        \"msgVersion\": \"1.0\",\n        \"token\": \"bfbc9935-7c1a-42c9-baca-97a7c435270e\"\n    },\n    \"errorInfo\": {\n        \"code\": \"200\",\n        \"status\": \"SUCCESS\",\n        \"message\": \"Response generated successfully\",\n        \"description\": \"Response generated successfully\"\n    },\n    \"payload\": {\n        \"data\": {\n            \"name\": \"ASHUTOSH MISHRA\",\n            \"mobile\": \"9560822035\",\n            \"pan\": \"CMKPM0270L\",\n            \"gender\": \"male\",\n            \"pdfByteArray\": \"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YmoKMiAwIG9iago8PC9UeXBlIC9QYWdlCi9SZXNvdXJjZXMgPDwvUHJvY1NldCBbL1BERiAvVGV4dCAvSW1hZ2VCIC9JbWFnZUMgL0ltYWdlSV0KL0V4dEdTdGF0ZSA8PC9HMyAzIDAgUj4+Ci9YT2JqZWN0IDw8L1gxNCAxNCAwIFI+PgovRm9udCA8PC9GNCA0IDAgUgovRjUgNSAwIFIKL0Y2IDYgMCBSCi9GNyA3IDAgUgovRjggOCAwIFIKL0Y5IDkgMCBSCi9GMTAgMTAgMCBSCi9GMTEgMTEgMCBSCi9GMTIgMTIgMCBSCi9GMTMgMTMgMCBSPj4+PgovTWVkaWFCb3ggWzAgMCA1OTguMDgwMDIgODQyLjg4XQovQ29udGVudHMgMTYgMCBSCi9TdHJ1Y3RQYXJlbnRzIDAKL1BhcmVudCAxOSAwIFI+PgplbmRvYmoKMTcgMCBvYmoKPDwvVHlwZSAvUGFnZQovUmVzb3VyY2VzIDw8L1Byb2NTZXQgWy9QREYgL1RleHQgL0ltYWdlQiAvSW1hZ2VDIC9JbWFnZUldCi9FeHRHU3RhdGUgPDwvRzMgMyAwIFI+PgovRm9udCA8PC9GNSA1IDAgUgovRjYgNiAwIFIKL0Y3IDcgMCBSCi9GMTAgMTAgMCBSCi9GMTMgMTMgMCBSPj4+PgovTWVkaWFCb3ggWzAgMCA1OTguMDgwMDIgODQyLjg4XQovQ29udGVudHMgMTggMCBSCi9TdHJ1Y3RQYXJlbnRzIDEKL1BhcmVudCAxOSAwIFI+PgplbmRvYmoKMTkgMCBvYmoKPDwvVHlwZSAvUGFnZXMKL0NvdW50IDIKL0tpZHMgWzIgMCBSIDE3IDAgUl0+PgplbmRvYmoKMjUgMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvRmlndXJlCi9BbHQgKExvZ28pCi9QIDI0IDAgUgovSyBbPDwvVHlwZSAvTUNSCi9QZyAyIDAgUgovTUNJRCAxMTg+Pl0KL0lEIChub2RlMDAwMDAwMDIpPj4KZW5kb2JqCjI0IDAgb2JqCjw8L1R5cGUgL1N0cnVjdEVsZW0KL1MgL1RECi9QIDIzIDAgUgovSyBbMjUgMCBSXQovQSBbPDwvTyAvVGFibGUKL0hlYWRlcnMgW10+PiA8PC9PIC9UYWJsZQovUm93U3BhbiAxPj4gPDwvTyAvVGFibGUKL0NvbFNwYW4gMT4+XQovSUQgKG5vZGUwMDAwMDAwOCk+PgplbmRvYmoKMjMgMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvVFIKL1AgMjIgMCBSCi9LIFsyNCAwIFJdCi9JRCAobm9kZTAwMDAwMDA3KT4+CmVuZG9iagozMSAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9Ob25TdHJ1Y3QKL1AgMzAgMCBSCi9LIFs8PC9UeXBlIC9NQ1IKL1BnIDIgMCBSCi9NQ0lEIDA+Pl0KL0lEIChub2RlMDAwMDAwMTYpPj4KZW5kb2JqCjMwIDAgb2JqCjw8L1R5cGUgL1N0cnVjdEVsZW0KL1MgL05vblN0cnVjdAovUCAyOSAwIFIKL0sgWzMxIDAgUl0KL0lEIChub2RlMDAwMDAwMTUpPj4KZW5kb2JqCjI5IDAgb2JqCjw8L1R5cGUgL1N0cnVjdEVsZW0KL1MgL05vblN0cnVjdAovUCAyOCAwIFIKL0sgWzMwIDAgUl0KL0lEIChub2RlMDAwMDAwMTQpPj4KZW5kb2JqCjM3IDAgb2JqCjw8L1R5cGUgL1N0cnVjdEVsZW0KL1MgL05vblN0cnVjdAovUCAzNiAwIFIKL0sgWzw8L1R5cGUgL01DUgovUGcgMiAwIFIKL01DSUQgMT4+XQovSUQgKG5vZGUwMDAwMDAyNSk+PgplbmRvYmoKMzYgMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvTm9uU3RydWN0Ci9QIDM1IDAgUgovSyBbMzcgMCBSXQovSUQgKG5vZGUwMDAwMDAyNCk+PgplbmRvYmoKMzkgMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvTm9uU3RydWN0Ci9QIDM4IDAgUgovSyBbPDwvVHlwZSAvTUNSCi9QZyAyIDAgUgovTUNJRCAyPj5dCi9JRCAobm9kZTAwMDAwMDI3KT4+CmVuZG9iagozOCAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9Ob25TdHJ1Y3QKL1AgMzUgMCBSCi9LIFszOSAwIFJdCi9JRCAobm9kZTAwMDAwMDI2KT4+CmVuZG9iagozNSAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9Ob25TdHJ1Y3QKL1AgMzQgMCBSCi9LIFszNiAwIFIgMzggMCBSXQovSUQgKG5vZGUwMDAwMDAyMyk+PgplbmRvYmoKNDIgMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvTm9uU3RydWN0Ci9QIDQxIDAgUgovSyBbPDwvVHlwZSAvTUNSCi9QZyAyIDAgUgovTUNJRCAzPj5dCi9JRCAobm9kZTAwMDAwMDMwKT4+CmVuZG9iago0MSAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9Ob25TdHJ1Y3QKL1AgNDAgMCBSCi9LIFs0MiAwIFJdCi9JRCAobm9kZTAwMDAwMDI5KT4+CmVuZG9iago0MCAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9Ob25TdHJ1Y3QKL1AgMzQgMCBSCi9LIFs0MSAwIFJdCi9JRCAobm9kZTAwMDAwMDI4KT4+CmVuZG9iago0NSAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9Ob25TdHJ1Y3QKL1AgNDQgMCBSCi9LIFs8PC9UeXBlIC9NQ1IKL1BnIDIgMCBSCi9NQ0lEIDQ+Pl0KL0lEIChub2RlMDAwMDAwMzQpPj4KZW5kb2JqCjQ0IDAgb2JqCjw8L1R5cGUgL1N0cnVjdEVsZW0KL1MgL05vblN0cnVjdAovUCA0MyAwIFIKL0sgWzQ1IDAgUl0KL0lEIChub2RlMDAwMDAwMzMpPj4KZW5kb2JqCjQzIDAgb2JqCjw8L1R5cGUgL1N0cnVjdEVsZW0KL1MgL05vblN0cnVjdAovUCAzNCAwIFIKL0sgWzQ0IDAgUl0KL0lEIChub2RlMDAwMDAwMzIpPj4KZW5kb2JqCjM0IDAgb2JqCjw8L1R5cGUgL1N0cnVjdEVsZW0KL1MgL05vblN0cnVjdAovUCAzMyAwIFIKL0sgWzM1IDAgUiA0MCAwIFIgNDMgMCBSXQovSUQgKG5vZGUwMDAwMDAyMSk+PgplbmRvYmoKMzMgMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvTm9uU3RydWN0Ci9QIDMyIDAgUgovSyBbMzQgMCBSXQovSUQgKG5vZGUwMDAwMDAyMCk+PgplbmRvYmoKNTAgMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvTm9uU3RydWN0Ci9QIDQ5IDAgUgovSyBbPDwvVHlwZSAvTUNSCi9QZyAyIDAgUgovTUNJRCA1Pj5dCi9JRCAobm9kZTAwMDAwMDQyKT4+CmVuZG9iago0OSAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9Ob25TdHJ1Y3QKL1AgNDggMCBSCi9LIFs1MCAwIFJdCi9JRCAobm9kZTAwMDAwMDQxKT4+CmVuZG9iago1MiAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9Ob25TdHJ1Y3QKL1AgNTEgMCBSCi9LIFs8PC9UeXBlIC9NQ1IKL1BnIDIgMCBSCi9NQ0lEIDY+Pl0KL0lEIChub2RlMDAwMDAwNDQpPj4KZW5kb2JqCjUxIDAgb2JqCjw8L1R5cGUgL1N0cnVjdEVsZW0KL1MgL05vblN0cnVjdAovUCA0OCAwIFIKL0sgWzUyIDAgUl0KL0lEIChub2RlMDAwMDAwNDMpPj4KZW5kb2JqCjQ4IDAgb2JqCjw8L1R5cGUgL1N0cnVjdEVsZW0KL1MgL05vblN0cnVjdAovUCA0NyAwIFIKL0sgWzQ5IDAgUiA1MSAwIFJdCi9JRCAobm9kZTAwMDAwMDQwKT4+CmVuZG9iago1NSAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9Ob25TdHJ1Y3QKL1AgNTQgMCBSCi9LIFs8PC9UeXBlIC9NQ1IKL1BnIDIgMCBSCi9NQ0lEIDc+Pl0KL0lEIChub2RlMDAwMDAwNDcpPj4KZW5kb2JqCjU0IDAgb2JqCjw8L1R5cGUgL1N0cnVjdEVsZW0KL1MgL05vblN0cnVjdAovUCA1MyAwIFIKL0sgWzU1IDAgUl0KL0lEIChub2RlMDAwMDAwNDYpPj4KZW5kb2JqCjU3IDAgb2JqCjw8L1R5cGUgL1N0cnVjdEVsZW0KL1MgL05vblN0cnVjdAovUCA1NiAwIFIKL0sgWzw8L1R5cGUgL01DUgovUGcgMiAwIFIKL01DSUQgOD4+XQovSUQgKG5vZGUwMDAwMDA0OSk+PgplbmRvYmoKNTYgMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvTm9uU3RydWN0Ci9QIDUzIDAgUgovSyBbNTcgMCBSXQovSUQgKG5vZGUwMDAwMDA0OCk+PgplbmRvYmoKNTMgMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvTm9uU3RydWN0Ci9QIDQ3IDAgUgovSyBbNTQgMCBSIDU2IDAgUl0KL0lEIChub2RlMDAwMDAwNDUpPj4KZW5kb2JqCjYwIDAgb2JqCjw8L1R5cGUgL1N0cnVjdEVsZW0KL1MgL05vblN0cnVjdAovUCA1OSAwIFIKL0sgWzw8L1R5cGUgL01DUgovUGcgMiAwIFIKL01DSUQgOT4+XQovSUQgKG5vZGUwMDAwMDA1Mik+PgplbmRvYmoKNTkgMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvTm9uU3RydWN0Ci9QIDU4IDAgUgovSyBbNjAgMCBSXQovSUQgKG5vZGUwMDAwMDA1MSk+PgplbmRvYmoKNTggMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvTm9uU3RydWN0Ci9QIDQ3IDAgUgovSyBbNTkgMCBSXQovSUQgKG5vZGUwMDAwMDA1MCk+PgplbmRvYmoKNDcgMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvTm9uU3RydWN0Ci9QIDQ2IDAgUgovSyBbNDggMCBSIDUzIDAgUiA1OCAwIFJdCi9JRCAobm9kZTAwMDAwMDM4KT4+CmVuZG9iago0NiAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9Ob25TdHJ1Y3QKL1AgMzIgMCBSCi9LIFs0NyAwIFJdCi9JRCAobm9kZTAwMDAwMDM3KT4+CmVuZG9iagozMiAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9Ob25TdHJ1Y3QKL1AgMjggMCBSCi9LIFszMyAwIFIgNDYgMCBSXQovSUQgKG5vZGUwMDAwMDAxOSk+PgplbmRvYmoKMjggMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvTm9uU3RydWN0Ci9QIDI3IDAgUgovSyBbMjkgMCBSIDMyIDAgUl0KL0lEIChub2RlMDAwMDAwMTIpPj4KZW5kb2JqCjI3IDAgb2JqCjw8L1R5cGUgL1N0cnVjdEVsZW0KL1MgL1RECi9QIDI2IDAgUgovSyBbMjggMCBSXQovQSBbPDwvTyAvVGFibGUKL0hlYWRlcnMgW10+PiA8PC9PIC9UYWJsZQovUm93U3BhbiAxPj4gPDwvTyAvVGFibGUKL0NvbFNwYW4gMT4+XQovSUQgKG5vZGUwMDAwMDAxMSk+PgplbmRvYmoKMjYgMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvVFIKL1AgMjIgMCBSCi9LIFsyNyAwIFJdCi9JRCAobm9kZTAwMDAwMDEwKT4+CmVuZG9iago2NiAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9Ob25TdHJ1Y3QKL1AgNjUgMCBSCi9LIFs8PC9UeXBlIC9NQ1IKL1BnIDIgMCBSCi9NQ0lEIDEwPj5dCi9JRCAobm9kZTAwMDAwMDY0KT4+CmVuZG9iago2NSAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9Ob25TdHJ1Y3QKL1AgNjQgMCBSCi9LIFs2NiAwIFJdCi9JRCAobm9kZTAwMDAwMDYzKT4+CmVuZG9iago2NCAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9Ob25TdHJ1Y3QKL1AgNjMgMCBSCi9LIFs2NSAwIFJdCi9JRCAobm9kZTAwMDAwMDYyKT4+CmVuZG9iago3MiAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9Ob25TdHJ1Y3QKL1AgNzEgMCBSCi9LIFs8PC9UeXBlIC9NQ1IKL1BnIDIgMCBSCi9NQ0lEIDExPj5dCi9JRCAobm9kZTAwMDAwMDczKT4+CmVuZG9iago3MSAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9Ob25TdHJ1Y3QKL1AgNzAgMCBSCi9LIFs3MiAwIFJdCi9JRCAobm9kZTAwMDAwMDcyKT4+CmVuZG9iago3NCAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9Ob25TdHJ1Y3QKL1AgNzMgMCBSCi9LIFs8PC9UeXBlIC9NQ1IKL1BnIDIgMCBSCi9NQ0lEIDEyPj5dCi9JRCAobm9kZTAwMDAwMDc1KT4+CmVuZG9iago3MyAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9Ob25TdHJ1Y3QKL1AgNzAgMCBSCi9LIFs3NCAwIFJdCi9JRCAobm9kZTAwMDAwMDc0KT4+CmVuZG9iago3MCAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9Ob25TdHJ1Y3QKL1AgNjkgMCBSCi9LIFs3MSAwIFIgNzMgMCBSXQovSUQgKG5vZGUwMDAwMDA3MSk+PgplbmRvYmoKNzcgMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvTm9uU3RydWN0Ci9QIDc2IDAgUgovSyBbPDwvVHlwZSAvTUNSCi9QZyAyIDAgUgovTUNJRCAxMz4+XQovSUQgKG5vZGUwMDAwMDA3OCk+PgplbmRvYmoKNzYgMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvTm9uU3RydWN0Ci9QIDc1IDAgUgovSyBbNzcgMCBSXQovSUQgKG5vZGUwMDAwMDA3Nyk+PgplbmRvYmoKNzkgMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvTm9uU3RydWN0Ci9QIDc4IDAgUgovSyBbPDwvVHlwZSAvTUNSCi9QZyAyIDAgUgovTUNJRCAxND4+XQovSUQgKG5vZGUwMDAwMDA4MCk+PgplbmRvYmoKNzggMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvTm9uU3RydWN0Ci9QIDc1IDAgUgovSyBbNzkgMCBSXQovSUQgKG5vZGUwMDAwMDA3OSk+PgplbmRvYmoKNzUgMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvTm9uU3RydWN0Ci9QIDY5IDAgUgovSyBbNzYgMCBSIDc4IDAgUl0KL0lEIChub2RlMDAwMDAwNzYpPj4KZW5kb2JqCjY5IDAgb2JqCjw8L1R5cGUgL1N0cnVjdEVsZW0KL1MgL05vblN0cnVjdAovUCA2OCAwIFIKL0sgWzcwIDAgUiA3NSAwIFJdCi9JRCAobm9kZTAwMDAwMDY5KT4+CmVuZG9iago2OCAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9Ob25TdHJ1Y3QKL1AgNjcgMCBSCi9LIFs2OSAwIFJdCi9JRCAobm9kZTAwMDAwMDY4KT4+CmVuZG9iago4NCAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9Ob25TdHJ1Y3QKL1AgODMgMCBSCi9LIFs8PC9UeXBlIC9NQ1IKL1BnIDIgMCBSCi9NQ0lEIDE1Pj5dCi9JRCAobm9kZTAwMDAwMDg4KT4+CmVuZG9iago4MyAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9URAovUCA4MiAwIFIKL0sgWzg0IDAgUl0KL0EgWzw8L08gL1RhYmxlCi9IZWFkZXJzIFtdPj4gPDwvTyAvVGFibGUKL1Jvd1NwYW4gMT4+IDw8L08gL1RhYmxlCi9Db2xTcGFuIDE+Pl0KL0lEIChub2RlMDAwMDAwODcpPj4KZW5kb2JqCjg2IDAgb2JqCjw8L1R5cGUgL1N0cnVjdEVsZW0KL1MgL05vblN0cnVjdAovUCA4NSAwIFIKL0sgWzw8L1R5cGUgL01DUgovUGcgMiAwIFIKL01DSUQgMTY+Pl0KL0lEIChub2RlMDAwMDAwOTApPj4KZW5kb2JqCjg1IDAgb2JqCjw8L1R5cGUgL1N0cnVjdEVsZW0KL1MgL1RECi9QIDgyIDAgUgovSyBbODYgMCBSXQovQSBbPDwvTyAvVGFibGUKL0hlYWRlcnMgW10+PiA8PC9PIC9UYWJsZQovUm93U3BhbiAxPj4gPDwvTyAvVGFibGUKL0NvbFNwYW4gMT4+XQovSUQgKG5vZGUwMDAwMDA4OSk+PgplbmRvYmoKODIgMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvVFIKL1AgODEgMCBSCi9LIFs4MyAwIFIgODUgMCBSXQovSUQgKG5vZGUwMDAwMDA4Nik+PgplbmRvYmoKODEgMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvVGFibGUKL1AgODAgMCBSCi9LIFs4MiAwIFJdCi9JRCAobm9kZTAwMDAwMDgyKT4+CmVuZG9iago4MCAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9Ob25TdHJ1Y3QKL1AgNjcgMCBSCi9LIFs4MSAwIFJdCi9JRCAobm9kZTAwMDAwMDgxKT4+CmVuZG9iago2NyAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9Ob25TdHJ1Y3QKL1AgNjMgMCBSCi9LIFs2OCAwIFIgODAgMCBSXQovSUQgKG5vZGUwMDAwMDA2Nyk+PgplbmRvYmoKNjMgMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvTm9uU3RydWN0Ci9QIDYyIDAgUgovSyBbNjQgMCBSIDY3IDAgUl0KL0lEIChub2RlMDAwMDAwNjApPj4KZW5kb2JqCjYyIDAgb2JqCjw8L1R5cGUgL1N0cnVjdEVsZW0KL1MgL1RECi9QIDYxIDAgUgovSyBbNjMgMCBSXQovQSBbPDwvTyAvVGFibGUKL0hlYWRlcnMgW10+PiA8PC9PIC9UYWJsZQovUm93U3BhbiAxPj4gPDwvTyAvVGFibGUKL0NvbFNwYW4gMT4+XQovSUQgKG5vZGUwMDAwMDA1OSk+PgplbmRvYmoKNjEgMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvVFIKL1AgMjIgMCBSCi9LIFs2MiAwIFJdCi9JRCAobm9kZTAwMDAwMDU4KT4+CmVuZG9iago5MiAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9Ob25TdHJ1Y3QKL1AgOTEgMCBSCi9LIFs8PC9UeXBlIC9NQ1IKL1BnIDIgMCBSCi9NQ0lEIDE3Pj5dCi9JRCAobm9kZTAwMDAwMTAxKT4+CmVuZG9iago5MSAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9Ob25TdHJ1Y3QKL1AgOTAgMCBSCi9LIFs5MiAwIFJdCi9JRCAobm9kZTAwMDAwMTAwKT4+CmVuZG9iago5MCAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9Ob25TdHJ1Y3QKL1AgODkgMCBSCi9LIFs5MSAwIFJdCi9JRCAobm9kZTAwMDAwMDk5KT4+CmVuZG9iago5NSAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9Ob25TdHJ1Y3QKL1AgOTQgMCBSCi9LIFs8PC9UeXBlIC9NQ1IKL1BnIDIgMCBSCi9NQ0lEIDE4Pj5dCi9JRCAobm9kZTAwMDAwMTA2KT4+CmVuZG9iago5NCAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9Ob25TdHJ1Y3QKL1AgOTMgMCBSCi9LIFs5NSAwIFJdCi9JRCAobm9kZTAwMDAwMTA1KT4+CmVuZG9iago5NyAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9Ob25TdHJ1Y3QKL1AgOTYgMCBSCi9LIFs8PC9UeXBlIC9NQ1IKL1BnIDIgMCBSCi9NQ0lEIDE5Pj5dCi9JRCAobm9kZTAwMDAwMTA4KT4+CmVuZG9iago5NiAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9Ob25TdHJ1Y3QKL1AgOTMgMCBSCi9LIFs5NyAwIFJdCi9JRCAobm9kZTAwMDAwMTA3KT4+CmVuZG9iago5OSAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9Ob25TdHJ1Y3QKL1AgOTggMCBSCi9LIFs8PC9UeXBlIC9NQ1IKL1BnIDIgMCBSCi9NQ0lEIDIwPj5dCi9JRCAobm9kZTAwMDAwMTEwKT4+CmVuZG9iago5OCAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9Ob25TdHJ1Y3QKL1AgOTMgMCBSCi9LIFs5OSAwIFJdCi9JRCAobm9kZTAwMDAwMTA5KT4+CmVuZG9iago5MyAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9Ob25TdHJ1Y3QKL1AgODkgMCBSCi9LIFs5NCAwIFIgOTYgMCBSIDk4IDAgUl0KL0lEIChub2RlMDAwMDAxMDQpPj4KZW5kb2JqCjg5IDAgb2JqCjw8L1R5cGUgL1N0cnVjdEVsZW0KL1MgL05vblN0cnVjdAovUCA4OCAwIFIKL0sgWzkwIDAgUiA5MyAwIFJdCi9JRCAobm9kZTAwMDAwMDk3KT4+CmVuZG9iago4OCAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9URAovUCA4NyAwIFIKL0sgWzg5IDAgUl0KL0EgWzw8L08gL1RhYmxlCi9IZWFkZXJzIFtdPj4gPDwvTyAvVGFibGUKL1Jvd1NwYW4gMT4+IDw8L08gL1RhYmxlCi9Db2xTcGFuIDE+Pl0KL0lEIChub2RlMDAwMDAwOTYpPj4KZW5kb2JqCjg3IDAgb2JqCjw8L1R5cGUgL1N0cnVjdEVsZW0KL1MgL1RSCi9QIDIyIDAgUgovSyBbODggMCBSXQovSUQgKG5vZGUwMDAwMDA5NSk+PgplbmRvYmoKMTA1IDAgb2JqCjw8L1R5cGUgL1N0cnVjdEVsZW0KL1MgL05vblN0cnVjdAovUCAxMDQgMCBSCi9LIFs8PC9UeXBlIC9NQ1IKL1BnIDIgMCBSCi9NQ0lEIDIxPj5dCi9JRCAobm9kZTAwMDAwMTE3KT4+CmVuZG9iagoxMDQgMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvTm9uU3RydWN0Ci9QIDEwMyAwIFIKL0sgWzEwNSAwIFJdCi9JRCAobm9kZTAwMDAwMTE2KT4+CmVuZG9iagoxMDcgMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvTm9uU3RydWN0Ci9QIDEwNiAwIFIKL0sgWzw8L1R5cGUgL01DUgovUGcgMiAwIFIKL01DSUQgMjI+Pl0KL0lEIChub2RlMDAwMDAxMTkpPj4KZW5kb2JqCjEwNiAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9Ob25TdHJ1Y3QKL1AgMTAzIDAgUgovSyBbMTA3IDAgUl0KL0lEIChub2RlMDAwMDAxMTgpPj4KZW5kb2JqCjExMSAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9Ob25TdHJ1Y3QKL1AgMTEwIDAgUgovSyBbPDwvVHlwZSAvTUNSCi9QZyAyIDAgUgovTUNJRCAyMz4+XQovSUQgKG5vZGUwMDAwMDEyNCk+PgplbmRvYmoKMTEwIDAgb2JqCjw8L1R5cGUgL1N0cnVjdEVsZW0KL1MgL0xJCi9QIDEwOSAwIFIKL0sgWzExMSAwIFJdCi9JRCAobm9kZTAwMDAwMTIyKT4+CmVuZG9iagoxMTMgMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvTm9uU3RydWN0Ci9QIDExMiAwIFIKL0sgWzw8L1R5cGUgL01DUgovUGcgMiAwIFIKL01DSUQgMjQ+Pl0KL0lEIChub2RlMDAwMDAxMjcpPj4KZW5kb2JqCjExMiAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9MSQovUCAxMDkgMCBSCi9LIFsxMTMgMCBSXQovSUQgKG5vZGUwMDAwMDEyNSk+PgplbmRvYmoKMTA5IDAgb2JqCjw8L1R5cGUgL1N0cnVjdEVsZW0KL1MgL0wKL1AgMTA4IDAgUgovSyBbMTEwIDAgUiAxMTIgMCBSXQovSUQgKG5vZGUwMDAwMDEyMSk+PgplbmRvYmoKMTA4IDAgb2JqCjw8L1R5cGUgL1N0cnVjdEVsZW0KL1MgL05vblN0cnVjdAovUCAxMDMgMCBSCi9LIFsxMDkgMCBSXQovSUQgKG5vZGUwMDAwMDEyMCk+PgplbmRvYmoKMTAzIDAgb2JqCjw8L1R5cGUgL1N0cnVjdEVsZW0KL1MgL05vblN0cnVjdAovUCAxMDIgMCBSCi9LIFsxMDQgMCBSIDEwNiAwIFIgMTA4IDAgUl0KL0lEIChub2RlMDAwMDAxMTUpPj4KZW5kb2JqCjEwMiAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9Ob25TdHJ1Y3QKL1AgMTAxIDAgUgovSyBbMTAzIDAgUl0KL0lEIChub2RlMDAwMDAxMTMpPj4KZW5kb2JqCjEwMSAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9URAovUCAxMDAgMCBSCi9LIFsxMDIgMCBSXQovQSBbPDwvTyAvVGFibGUKL0hlYWRlcnMgW10+PiA8PC9PIC9UYWJsZQovUm93U3BhbiAxPj4gPDwvTyAvVGFibGUKL0NvbFNwYW4gMT4+XQovSUQgKG5vZGUwMDAwMDExMik+PgplbmRvYmoKMTAwIDAgb2JqCjw8L1R5cGUgL1N0cnVjdEVsZW0KL1MgL1RSCi9QIDIyIDAgUgovSyBbMTAxIDAgUl0KL0lEIChub2RlMDAwMDAxMTEpPj4KZW5kb2JqCjExOSAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9Ob25TdHJ1Y3QKL1AgMTE4IDAgUgovSyBbPDwvVHlwZSAvTUNSCi9QZyAyIDAgUgovTUNJRCAyNT4+XQovSUQgKG5vZGUwMDAwMDEzOCk+PgplbmRvYmoKMTE4IDAgb2JqCjw8L1R5cGUgL1N0cnVjdEVsZW0KL1MgL05vblN0cnVjdAovUCAxMTcgMCBSCi9LIFsxMTkgMCBSXQovSUQgKG5vZGUwMDAwMDEzNyk+PgplbmRvYmoKMTE3IDAgb2JqCjw8L1R5cGUgL1N0cnVjdEVsZW0KL1MgL05vblN0cnVjdAovUCAxMTYgMCBSCi9LIFsxMTggMCBSXQovSUQgKG5vZGUwMDAwMDEzNik+PgplbmRvYmoKMTIyIDAgb2JqCjw8L1R5cGUgL1N0cnVjdEVsZW0KL1MgL05vblN0cnVjdAovUCAxMjEgMCBSCi9LIFs8PC9UeXBlIC9NQ1IKL1BnIDIgMCBSCi9NQ0lEIDI2Pj5dCi9JRCAobm9kZTAwMDAwMTQyKT4+CmVuZG9iagoxMjEgMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvTm9uU3RydWN0Ci9QIDEyMCAwIFIKL0sgWzEyMiAwIFJdCi9JRCAobm9kZTAwMDAwMTQxKT4+CmVuZG9iagoxMjQgMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvTm9uU3RydWN0Ci9QIDEyMyAwIFIKL0sgWzw8L1R5cGUgL01DUgovUGcgMiAwIFIKL01DSUQgMjc+Pl0KL0lEIChub2RlMDAwMDAxNDQpPj4KZW5kb2JqCjEyMyAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9Ob25TdHJ1Y3QKL1AgMTIwIDAgUgovSyBbMTI0IDAgUl0KL0lEIChub2RlMDAwMDAxNDMpPj4KZW5kb2JqCjEyMCAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9Ob25TdHJ1Y3QKL1AgMTE2IDAgUgovSyBbMTIxIDAgUiAxMjMgMCBSXQovSUQgKG5vZGUwMDAwMDE0MCk+PgplbmRvYmoKMTI3IDAgb2JqCjw8L1R5cGUgL1N0cnVjdEVsZW0KL1MgL05vblN0cnVjdAovUCAxMjYgMCBSCi9LIFs8PC9UeXBlIC9NQ1IKL1BnIDIgMCBSCi9NQ0lEIDI4Pj5dCi9JRCAobm9kZTAwMDAwMTQ3KT4+CmVuZG9iagoxMjYgMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvTm9uU3RydWN0Ci9QIDEyNSAwIFIKL0sgWzEyNyAwIFJdCi9JRCAobm9kZTAwMDAwMTQ2KT4+CmVuZG9iagoxMjkgMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvTm9uU3RydWN0Ci9QIDEyOCAwIFIKL0sgWzw8L1R5cGUgL01DUgovUGcgMiAwIFIKL01DSUQgMjk+Pl0KL0lEIChub2RlMDAwMDAxNDkpPj4KZW5kb2JqCjEyOCAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9Ob25TdHJ1Y3QKL1AgMTI1IDAgUgovSyBbMTI5IDAgUl0KL0lEIChub2RlMDAwMDAxNDgpPj4KZW5kb2JqCjEyNSAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9Ob25TdHJ1Y3QKL1AgMTE2IDAgUgovSyBbMTI2IDAgUiAxMjggMCBSXQovSUQgKG5vZGUwMDAwMDE0NSk+PgplbmRvYmoKMTMyIDAgb2JqCjw8L1R5cGUgL1N0cnVjdEVsZW0KL1MgL05vblN0cnVjdAovUCAxMzEgMCBSCi9LIFs8PC9UeXBlIC9NQ1IKL1BnIDIgMCBSCi9NQ0lEIDMwPj5dCi9JRCAobm9kZTAwMDAwMTU2KT4+CmVuZG9iagoxMzEgMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvTm9uU3RydWN0Ci9QIDEzMCAwIFIKL0sgWzEzMiAwIFJdCi9JRCAobm9kZTAwMDAwMTU1KT4+CmVuZG9iagoxMzAgMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvTm9uU3RydWN0Ci9QIDExNiAwIFIKL0sgWzEzMSAwIFJdCi9JRCAobm9kZTAwMDAwMTU0KT4+CmVuZG9iagoxMTYgMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvTm9uU3RydWN0Ci9QIDExNSAwIFIKL0sgWzExNyAwIFIgMTIwIDAgUiAxMjUgMCBSIDEzMCAwIFJdCi9JRCAobm9kZTAwMDAwMTM0KT4+CmVuZG9iagoxMTUgMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvVEQKL1AgMTE0IDAgUgovSyBbMTE2IDAgUl0KL0EgWzw8L08gL1RhYmxlCi9IZWFkZXJzIFtdPj4gPDwvTyAvVGFibGUKL1Jvd1NwYW4gMT4+IDw8L08gL1RhYmxlCi9Db2xTcGFuIDE+Pl0KL0lEIChub2RlMDAwMDAxMzMpPj4KZW5kb2JqCjExNCAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9UUgovUCAyMiAwIFIKL0sgWzExNSAwIFJdCi9JRCAobm9kZTAwMDAwMTMyKT4+CmVuZG9iagoxMzggMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvTm9uU3RydWN0Ci9QIDEzNyAwIFIKL0sgWzw8L1R5cGUgL01DUgovUGcgMiAwIFIKL01DSUQgMzE+Pl0KL0lEIChub2RlMDAwMDAxNjcpPj4KZW5kb2JqCjEzNyAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9Ob25TdHJ1Y3QKL1AgMTM2IDAgUgovSyBbMTM4IDAgUl0KL0lEIChub2RlMDAwMDAxNjYpPj4KZW5kb2JqCjEzNiAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9Ob25TdHJ1Y3QKL1AgMTM1IDAgUgovSyBbMTM3IDAgUl0KL0lEIChub2RlMDAwMDAxNjUpPj4KZW5kb2JqCjE0MSAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9Ob25TdHJ1Y3QKL1AgMTQwIDAgUgovSyBbPDwvVHlwZSAvTUNSCi9QZyAyIDAgUgovTUNJRCAzMj4+XQovSUQgKG5vZGUwMDAwMDE3Mik+PgplbmRvYmoKMTQwIDAgb2JqCjw8L1R5cGUgL1N0cnVjdEVsZW0KL1MgL05vblN0cnVjdAovUCAxMzkgMCBSCi9LIFsxNDEgMCBSXQovSUQgKG5vZGUwMDAwMDE3MSk+PgplbmRvYmoKMTQzIDAgb2JqCjw8L1R5cGUgL1N0cnVjdEVsZW0KL1MgL05vblN0cnVjdAovUCAxNDIgMCBSCi9LIFs8PC9UeXBlIC9NQ1IKL1BnIDIgMCBSCi9NQ0lEIDMzPj5dCi9JRCAobm9kZTAwMDAwMTc0KT4+CmVuZG9iagoxNDIgMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvTm9uU3RydWN0Ci9QIDEzOSAwIFIKL0sgWzE0MyAwIFJdCi9JRCAobm9kZTAwMDAwMTczKT4+CmVuZG9iagoxNDUgMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvTm9uU3RydWN0Ci9QIDE0NCAwIFIKL0sgWzw8L1R5cGUgL01DUgovUGcgMiAwIFIKL01DSUQgMzQ+Pl0KL0lEIChub2RlMDAwMDAxNzYpPj4KZW5kb2JqCjE0NCAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9Ob25TdHJ1Y3QKL1AgMTM5IDAgUgovSyBbMTQ1IDAgUl0KL0lEIChub2RlMDAwMDAxNzUpPj4KZW5kb2JqCjE0NyAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9Ob25TdHJ1Y3QKL1AgMTQ2IDAgUgovSyBbPDwvVHlwZSAvTUNSCi9QZyAyIDAgUgovTUNJRCAzNT4+XQovSUQgKG5vZGUwMDAwMDE3OCk+PgplbmRvYmoKMTQ2IDAgb2JqCjw8L1R5cGUgL1N0cnVjdEVsZW0KL1MgL05vblN0cnVjdAovUCAxMzkgMCBSCi9LIFsxNDcgMCBSXQovSUQgKG5vZGUwMDAwMDE3Nyk+PgplbmRvYmoKMTM5IDAgb2JqCjw8L1R5cGUgL1N0cnVjdEVsZW0KL1MgL05vblN0cnVjdAovUCAxMzUgMCBSCi9LIFsxNDAgMCBSIDE0MiAwIFIgMTQ0IDAgUiAxNDYgMCBSXQovSUQgKG5vZGUwMDAwMDE3MCk+PgplbmRvYmoKMTUwIDAgb2JqCjw8L1R5cGUgL1N0cnVjdEVsZW0KL1MgL05vblN0cnVjdAovUCAxNDkgMCBSCi9LIFs8PC9UeXBlIC9NQ1IKL1BnIDIgMCBSCi9NQ0lEIDM2Pj5dCi9JRCAobm9kZTAwMDAwMTgxKT4+CmVuZG9iagoxNDkgMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvTm9uU3RydWN0Ci9QIDE0OCAwIFIKL0sgWzE1MCAwIFJdCi9JRCAobm9kZTAwMDAwMTgwKT4+CmVuZG9iagoxNTIgMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvTm9uU3RydWN0Ci9QIDE1MSAwIFIKL0sgWzw8L1R5cGUgL01DUgovUGcgMiAwIFIKL01DSUQgMzc+Pl0KL0lEIChub2RlMDAwMDAxODMpPj4KZW5kb2JqCjE1MSAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9Ob25TdHJ1Y3QKL1AgMTQ4IDAgUgovSyBbMTUyIDAgUl0KL0lEIChub2RlMDAwMDAxODIpPj4KZW5kb2JqCjE0OCAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9Ob25TdHJ1Y3QKL1AgMTM1IDAgUgovSyBbMTQ5IDAgUiAxNTEgMCBSXQovSUQgKG5vZGUwMDAwMDE3OSk+PgplbmRvYmoKMTU1IDAgb2JqCjw8L1R5cGUgL1N0cnVjdEVsZW0KL1MgL05vblN0cnVjdAovUCAxNTQgMCBSCi9LIFs8PC9UeXBlIC9NQ1IKL1BnIDIgMCBSCi9NQ0lEIDM4Pj5dCi9JRCAobm9kZTAwMDAwMTg4KT4+CmVuZG9iagoxNTQgMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvTm9uU3RydWN0Ci9QIDE1MyAwIFIKL0sgWzE1NSAwIFJdCi9JRCAobm9kZTAwMDAwMTg3KT4+CmVuZG9iagoxNTcgMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvTm9uU3RydWN0Ci9QIDE1NiAwIFIKL0sgWzw8L1R5cGUgL01DUgovUGcgMiAwIFIKL01DSUQgMzk+Pl0KL0lEIChub2RlMDAwMDAxOTApPj4KZW5kb2JqCjE1NiAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9Ob25TdHJ1Y3QKL1AgMTUzIDAgUgovSyBbMTU3IDAgUl0KL0lEIChub2RlMDAwMDAxODkpPj4KZW5kb2JqCjE1MyAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9Ob25TdHJ1Y3QKL1AgMTM1IDAgUgovSyBbMTU0IDAgUiAxNTYgMCBSXQovSUQgKG5vZGUwMDAwMDE4Nik+PgplbmRvYmoKMTM1IDAgb2JqCjw8L1R5cGUgL1N0cnVjdEVsZW0KL1MgL05vblN0cnVjdAovUCAxMzQgMCBSCi9LIFsxMzYgMCBSIDEzOSAwIFIgMTQ4IDAgUiAxNTMgMCBSXQovSUQgKG5vZGUwMDAwMDE2Myk+PgplbmRvYmoKMTM0IDAgb2JqCjw8L1R5cGUgL1N0cnVjdEVsZW0KL1MgL1RECi9QIDEzMyAwIFIKL0sgWzEzNSAwIFJdCi9BIFs8PC9PIC9UYWJsZQovSGVhZGVycyBbXT4+IDw8L08gL1RhYmxlCi9Sb3dTcGFuIDE+PiA8PC9PIC9UYWJsZQovQ29sU3BhbiAxPj5dCi9JRCAobm9kZTAwMDAwMTYyKT4+CmVuZG9iagoxMzMgMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvVFIKL1AgMjIgMCBSCi9LIFsxMzQgMCBSXQovSUQgKG5vZGUwMDAwMDE2MSk+PgplbmRvYmoKMTYzIDAgb2JqCjw8L1R5cGUgL1N0cnVjdEVsZW0KL1MgL05vblN0cnVjdAovUCAxNjIgMCBSCi9LIFs8PC9UeXBlIC9NQ1IKL1BnIDIgMCBSCi9NQ0lEIDQwPj5dCi9JRCAobm9kZTAwMDAwMjAzKT4+CmVuZG9iagoxNjIgMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvTm9uU3RydWN0Ci9QIDE2MSAwIFIKL0sgWzE2MyAwIFJdCi9JRCAobm9kZTAwMDAwMjAyKT4+CmVuZG9iagoxNjEgMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvTm9uU3RydWN0Ci9QIDE2MCAwIFIKL0sgWzE2MiAwIFJdCi9JRCAobm9kZTAwMDAwMjAxKT4+CmVuZG9iagoxNjYgMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvTm9uU3RydWN0Ci9QIDE2NSAwIFIKL0sgWzw8L1R5cGUgL01DUgovUGcgMiAwIFIKL01DSUQgNDE+Pl0KL0lEIChub2RlMDAwMDAyMDgpPj4KZW5kb2JqCjE2NSAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9Ob25TdHJ1Y3QKL1AgMTY0IDAgUgovSyBbMTY2IDAgUl0KL0lEIChub2RlMDAwMDAyMDcpPj4KZW5kb2JqCjE2OCAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9Ob25TdHJ1Y3QKL1AgMTY3IDAgUgovSyBbPDwvVHlwZSAvTUNSCi9QZyAyIDAgUgovTUNJRCA0Mj4+XQovSUQgKG5vZGUwMDAwMDIxMCk+PgplbmRvYmoKMTY3IDAgb2JqCjw8L1R5cGUgL1N0cnVjdEVsZW0KL1MgL05vblN0cnVjdAovUCAxNjQgMCBSCi9LIFsxNjggMCBSXQovSUQgKG5vZGUwMDAwMDIwOSk+PgplbmRvYmoKMTcwIDAgb2JqCjw8L1R5cGUgL1N0cnVjdEVsZW0KL1MgL05vblN0cnVjdAovUCAxNjkgMCBSCi9LIFs8PC9UeXBlIC9NQ1IKL1BnIDIgMCBSCi9NQ0lEIDQzPj5dCi9JRCAobm9kZTAwMDAwMjEyKT4+CmVuZG9iagoxNjkgMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvTm9uU3RydWN0Ci9QIDE2NCAwIFIKL0sgWzE3MCAwIFJdCi9JRCAobm9kZTAwMDAwMjExKT4+CmVuZG9iagoxNjQgMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvTm9uU3RydWN0Ci9QIDE2MCAwIFIKL0sgWzE2NSAwIFIgMTY3IDAgUiAxNjkgMCBSXQovSUQgKG5vZGUwMDAwMDIwNik+PgplbmRvYmoKMTczIDAgb2JqCjw8L1R5cGUgL1N0cnVjdEVsZW0KL1MgL05vblN0cnVjdAovUCAxNzIgMCBSCi9LIFs8PC9UeXBlIC9NQ1IKL1BnIDIgMCBSCi9NQ0lEIDQ0Pj5dCi9JRCAobm9kZTAwMDAwMjE1KT4+CmVuZG9iagoxNzIgMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvTm9uU3RydWN0Ci9QIDE3MSAwIFIKL0sgWzE3MyAwIFJdCi9JRCAobm9kZTAwMDAwMjE0KT4+CmVuZG9iagoxNzUgMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvTm9uU3RydWN0Ci9QIDE3NCAwIFIKL0sgWzw8L1R5cGUgL01DUgovUGcgMiAwIFIKL01DSUQgNDU+Pl0KL0lEIChub2RlMDAwMDAyMTcpPj4KZW5kb2JqCjE3NCAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9Ob25TdHJ1Y3QKL1AgMTcxIDAgUgovSyBbMTc1IDAgUl0KL0lEIChub2RlMDAwMDAyMTYpPj4KZW5kb2JqCjE3MSAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9Ob25TdHJ1Y3QKL1AgMTYwIDAgUgovSyBbMTcyIDAgUiAxNzQgMCBSXQovSUQgKG5vZGUwMDAwMDIxMyk+PgplbmRvYmoKMTc4IDAgb2JqCjw8L1R5cGUgL1N0cnVjdEVsZW0KL1MgL05vblN0cnVjdAovUCAxNzcgMCBSCi9LIFs8PC9UeXBlIC9NQ1IKL1BnIDIgMCBSCi9NQ0lEIDQ2Pj5dCi9JRCAobm9kZTAwMDAwMjIxKT4+CmVuZG9iagoxNzcgMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvTm9uU3RydWN0Ci9QIDE3NiAwIFIKL0sgWzE3OCAwIFJdCi9JRCAobm9kZTAwMDAwMjIwKT4+CmVuZG9iagoxODAgMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvTm9uU3RydWN0Ci9QIDE3OSAwIFIKL0sgWzw8L1R5cGUgL01DUgovUGcgMiAwIFIKL01DSUQgNDc+Pl0KL0lEIChub2RlMDAwMDAyMjMpPj4KZW5kb2JqCjE3OSAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9Ob25TdHJ1Y3QKL1AgMTc2IDAgUgovSyBbMTgwIDAgUl0KL0lEIChub2RlMDAwMDAyMjIpPj4KZW5kb2JqCjE3NiAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9Ob25TdHJ1Y3QKL1AgMTYwIDAgUgovSyBbMTc3IDAgUiAxNzkgMCBSXQovSUQgKG5vZGUwMDAwMDIxOSk+PgplbmRvYmoKMTgzIDAgb2JqCjw8L1R5cGUgL1N0cnVjdEVsZW0KL1MgL05vblN0cnVjdAovUCAxODIgMCBSCi9LIFs8PC9UeXBlIC9NQ1IKL1BnIDIgMCBSCi9NQ0lEIDQ4Pj5dCi9JRCAobm9kZTAwMDAwMjI3KT4+CmVuZG9iagoxODIgMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvTm9uU3RydWN0Ci9QIDE4MSAwIFIKL0sgWzE4MyAwIFJdCi9JRCAobm9kZTAwMDAwMjI2KT4+CmVuZG9iagoxODUgMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvTm9uU3RydWN0Ci9QIDE4NCAwIFIKL0sgWzw8L1R5cGUgL01DUgovUGcgMiAwIFIKL01DSUQgNDk+Pl0KL0lEIChub2RlMDAwMDAyMjkpPj4KZW5kb2JqCjE4NCAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9Ob25TdHJ1Y3QKL1AgMTgxIDAgUgovSyBbMTg1IDAgUl0KL0lEIChub2RlMDAwMDAyMjgpPj4KZW5kb2JqCjE4MSAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9Ob25TdHJ1Y3QKL1AgMTYwIDAgUgovSyBbMTgyIDAgUiAxODQgMCBSXQovSUQgKG5vZGUwMDAwMDIyNSk+PgplbmRvYmoKMTYwIDAgb2JqCjw8L1R5cGUgL1N0cnVjdEVsZW0KL1MgL05vblN0cnVjdAovUCAxNTkgMCBSCi9LIFsxNjEgMCBSIDE2NCAwIFIgMTcxIDAgUiAxNzYgMCBSIDE4MSAwIFJdCi9JRCAobm9kZTAwMDAwMTk5KT4+CmVuZG9iagoxNTkgMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvVEQKL1AgMTU4IDAgUgovSyBbMTYwIDAgUl0KL0EgWzw8L08gL1RhYmxlCi9IZWFkZXJzIFtdPj4gPDwvTyAvVGFibGUKL1Jvd1NwYW4gMT4+IDw8L08gL1RhYmxlCi9Db2xTcGFuIDE+Pl0KL0lEIChub2RlMDAwMDAxOTgpPj4KZW5kb2JqCjE1OCAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9UUgovUCAyMiAwIFIKL0sgWzE1OSAwIFJdCi9JRCAobm9kZTAwMDAwMTk3KT4+CmVuZG9iagoxOTEgMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvTm9uU3RydWN0Ci9QIDE5MCAwIFIKL0sgWzw8L1R5cGUgL01DUgovUGcgMiAwIFIKL01DSUQgNTA+Pl0KL0lEIChub2RlMDAwMDAyNDEpPj4KZW5kb2JqCjE5MCAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9Ob25TdHJ1Y3QKL1AgMTg5IDAgUgovSyBbMTkxIDAgUl0KL0lEIChub2RlMDAwMDAyNDApPj4KZW5kb2JqCjE4OSAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9Ob25TdHJ1Y3QKL1AgMTg4IDAgUgovSyBbMTkwIDAgUl0KL0lEIChub2RlMDAwMDAyMzkpPj4KZW5kb2JqCjE5NCAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9Ob25TdHJ1Y3QKL1AgMTkzIDAgUgovSyBbPDwvVHlwZSAvTUNSCi9QZyAyIDAgUgovTUNJRCA1MT4+XQovSUQgKG5vZGUwMDAwMDI0Nik+PgplbmRvYmoKMTkzIDAgb2JqCjw8L1R5cGUgL1N0cnVjdEVsZW0KL1MgL05vblN0cnVjdAovUCAxOTIgMCBSCi9LIFsxOTQgMCBSXQovSUQgKG5vZGUwMDAwMDI0NSk+PgplbmRvYmoKMTkyIDAgb2JqCjw8L1R5cGUgL1N0cnVjdEVsZW0KL1MgL05vblN0cnVjdAovUCAxODggMCBSCi9LIFsxOTMgMCBSXQovSUQgKG5vZGUwMDAwMDI0NCk+PgplbmRvYmoKMTk3IDAgb2JqCjw8L1R5cGUgL1N0cnVjdEVsZW0KL1MgL05vblN0cnVjdAovUCAxOTYgMCBSCi9LIFs8PC9UeXBlIC9NQ1IKL1BnIDIgMCBSCi9NQ0lEIDUyPj5dCi9JRCAobm9kZTAwMDAwMjUxKT4+CmVuZG9iagoxOTYgMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvTm9uU3RydWN0Ci9QIDE5NSAwIFIKL0sgWzE5NyAwIFJdCi9JRCAobm9kZTAwMDAwMjUwKT4+CmVuZG9iagoxOTUgMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvTm9uU3RydWN0Ci9QIDE4OCAwIFIKL0sgWzE5NiAwIFJdCi9JRCAobm9kZTAwMDAwMjQ5KT4+CmVuZG9iagoyMDAgMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvTm9uU3RydWN0Ci9QIDE5OSAwIFIKL0sgWzw8L1R5cGUgL01DUgovUGcgMiAwIFIKL01DSUQgNTM+Pl0KL0lEIChub2RlMDAwMDAyNTYpPj4KZW5kb2JqCjE5OSAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9Ob25TdHJ1Y3QKL1AgMTk4IDAgUgovSyBbMjAwIDAgUl0KL0lEIChub2RlMDAwMDAyNTUpPj4KZW5kb2JqCjE5OCAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9Ob25TdHJ1Y3QKL1AgMTg4IDAgUgovSyBbMTk5IDAgUl0KL0lEIChub2RlMDAwMDAyNTQpPj4KZW5kb2JqCjIwMyAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9Ob25TdHJ1Y3QKL1AgMjAyIDAgUgovSyBbPDwvVHlwZSAvTUNSCi9QZyAyIDAgUgovTUNJRCA1ND4+XQovSUQgKG5vZGUwMDAwMDI2MSk+PgplbmRvYmoKMjAyIDAgb2JqCjw8L1R5cGUgL1N0cnVjdEVsZW0KL1MgL05vblN0cnVjdAovUCAyMDEgMCBSCi9LIFsyMDMgMCBSXQovSUQgKG5vZGUwMDAwMDI2MCk+PgplbmRvYmoKMjAxIDAgb2JqCjw8L1R5cGUgL1N0cnVjdEVsZW0KL1MgL05vblN0cnVjdAovUCAxODggMCBSCi9LIFsyMDIgMCBSXQovSUQgKG5vZGUwMDAwMDI1OSk+PgplbmRvYmoKMjA2IDAgb2JqCjw8L1R5cGUgL1N0cnVjdEVsZW0KL1MgL05vblN0cnVjdAovUCAyMDUgMCBSCi9LIFs8PC9UeXBlIC9NQ1IKL1BnIDIgMCBSCi9NQ0lEIDU1Pj5dCi9JRCAobm9kZTAwMDAwMjY2KT4+CmVuZG9iagoyMDUgMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvTm9uU3RydWN0Ci9QIDIwNCAwIFIKL0sgWzIwNiAwIFJdCi9JRCAobm9kZTAwMDAwMjY1KT4+CmVuZG9iagoyMDQgMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvTm9uU3RydWN0Ci9QIDE4OCAwIFIKL0sgWzIwNSAwIFJdCi9JRCAobm9kZTAwMDAwMjY0KT4+CmVuZG9iagoxODggMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvTm9uU3RydWN0Ci9QIDE4NyAwIFIKL0sgWzE4OSAwIFIgMTkyIDAgUiAxOTUgMCBSIDE5OCAwIFIgMjAxIDAgUiAyMDQgMCBSXQovSUQgKG5vZGUwMDAwMDIzNyk+PgplbmRvYmoKMTg3IDAgb2JqCjw8L1R5cGUgL1N0cnVjdEVsZW0KL1MgL1RECi9QIDE4NiAwIFIKL0sgWzE4OCAwIFJdCi9BIFs8PC9PIC9UYWJsZQovSGVhZGVycyBbXT4+IDw8L08gL1RhYmxlCi9Sb3dTcGFuIDE+PiA8PC9PIC9UYWJsZQovQ29sU3BhbiAxPj5dCi9JRCAobm9kZTAwMDAwMjM2KT4+CmVuZG9iagoxODYgMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvVFIKL1AgMjIgMCBSCi9LIFsxODcgMCBSXQovSUQgKG5vZGUwMDAwMDIzNSk+PgplbmRvYmoKMjEyIDAgb2JqCjw8L1R5cGUgL1N0cnVjdEVsZW0KL1MgL05vblN0cnVjdAovUCAyMTEgMCBSCi9LIFs8PC9UeXBlIC9NQ1IKL1BnIDIgMCBSCi9NQ0lEIDU2Pj5dCi9JRCAobm9kZTAwMDAwMjc5KT4+CmVuZG9iagoyMTEgMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvTm9uU3RydWN0Ci9QIDIxMCAwIFIKL0sgWzIxMiAwIFJdCi9JRCAobm9kZTAwMDAwMjc4KT4+CmVuZG9iagoyMTAgMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvTm9uU3RydWN0Ci9QIDIwOSAwIFIKL0sgWzIxMSAwIFJdCi9JRCAobm9kZTAwMDAwMjc3KT4+CmVuZG9iagoyMTUgMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvTm9uU3RydWN0Ci9QIDIxNCAwIFIKL0sgWzw8L1R5cGUgL01DUgovUGcgMiAwIFIKL01DSUQgNTc+Pl0KL0lEIChub2RlMDAwMDAyODQpPj4KZW5kb2JqCjIxNiAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9Ob25TdHJ1Y3QKL1AgMjE0IDAgUgovSyBbPDwvVHlwZSAvTUNSCi9QZyAyIDAgUgovTUNJRCA1OD4+XQovSUQgKG5vZGUwMDAwMDI4Nik+PgplbmRvYmoKMjE0IDAgb2JqCjw8L1R5cGUgL1N0cnVjdEVsZW0KL1MgL05vblN0cnVjdAovUCAyMTMgMCBSCi9LIFsyMTUgMCBSIDIxNiAwIFJdCi9JRCAobm9kZTAwMDAwMjgzKT4+CmVuZG9iagoyMTMgMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvTm9uU3RydWN0Ci9QIDIwOSAwIFIKL0sgWzIxNCAwIFJdCi9JRCAobm9kZTAwMDAwMjgyKT4+CmVuZG9iagoyMTkgMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvTm9uU3RydWN0Ci9QIDIxOCAwIFIKL0sgWzw8L1R5cGUgL01DUgovUGcgMiAwIFIKL01DSUQgNTk+Pl0KL0lEIChub2RlMDAwMDAyOTEpPj4KZW5kb2JqCjIyMCAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9Ob25TdHJ1Y3QKL1AgMjE4IDAgUgovSyBbPDwvVHlwZSAvTUNSCi9QZyAyIDAgUgovTUNJRCA2MD4+XQovSUQgKG5vZGUwMDAwMDI5Mik+PgplbmRvYmoKMjE4IDAgb2JqCjw8L1R5cGUgL1N0cnVjdEVsZW0KL1MgL05vblN0cnVjdAovUCAyMTcgMCBSCi9LIFsyMTkgMCBSIDIyMCAwIFJdCi9JRCAobm9kZTAwMDAwMjg5KT4+CmVuZG9iagoyMjIgMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvTm9uU3RydWN0Ci9QIDIyMSAwIFIKL0sgWzw8L1R5cGUgL01DUgovUGcgMiAwIFIKL01DSUQgNjE+Pl0KL0lEIChub2RlMDAwMDAyOTUpPj4KZW5kb2JqCjIyMSAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9Ob25TdHJ1Y3QKL1AgMjE3IDAgUgovSyBbMjIyIDAgUl0KL0lEIChub2RlMDAwMDAyOTMpPj4KZW5kb2JqCjIyNCAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9Ob25TdHJ1Y3QKL1AgMjIzIDAgUgovSyBbPDwvVHlwZSAvTUNSCi9QZyAyIDAgUgovTUNJRCA2Mj4+XQovSUQgKG5vZGUwMDAwMDI5OSk+PgplbmRvYmoKMjI1IDAgb2JqCjw8L1R5cGUgL1N0cnVjdEVsZW0KL1MgL05vblN0cnVjdAovUCAyMjMgMCBSCi9LIFs8PC9UeXBlIC9NQ1IKL1BnIDIgMCBSCi9NQ0lEIDYzPj5dCi9JRCAobm9kZTAwMDAwMzAxKT4+CmVuZG9iagoyMjMgMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvTm9uU3RydWN0Ci9QIDIxNyAwIFIKL0sgWzIyNCAwIFIgMjI1IDAgUl0KL0lEIChub2RlMDAwMDAyOTcpPj4KZW5kb2JqCjIxNyAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9Ob25TdHJ1Y3QKL1AgMjA5IDAgUgovSyBbMjE4IDAgUiAyMjEgMCBSIDIyMyAwIFJdCi9JRCAobm9kZTAwMDAwMjg4KT4+CmVuZG9iagoyMjggMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvTm9uU3RydWN0Ci9QIDIyNyAwIFIKL0sgWzw8L1R5cGUgL01DUgovUGcgMiAwIFIKL01DSUQgNjQ+Pl0KL0lEIChub2RlMDAwMDAzMDUpPj4KZW5kb2JqCjIyOSAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9Ob25TdHJ1Y3QKL1AgMjI3IDAgUgovSyBbPDwvVHlwZSAvTUNSCi9QZyAyIDAgUgovTUNJRCA2NT4+XQovSUQgKG5vZGUwMDAwMDMwNyk+PgplbmRvYmoKMjI3IDAgb2JqCjw8L1R5cGUgL1N0cnVjdEVsZW0KL1MgL05vblN0cnVjdAovUCAyMjYgMCBSCi9LIFsyMjggMCBSIDIyOSAwIFJdCi9JRCAobm9kZTAwMDAwMzA0KT4+CmVuZG9iagoyMjYgMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvTm9uU3RydWN0Ci9QIDIwOSAwIFIKL0sgWzIyNyAwIFJdCi9JRCAobm9kZTAwMDAwMzAzKT4+CmVuZG9iagoyMzIgMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvTm9uU3RydWN0Ci9QIDIzMSAwIFIKL0sgWzw8L1R5cGUgL01DUgovUGcgMiAwIFIKL01DSUQgNjY+Pl0KL0lEIChub2RlMDAwMDAzMTIpPj4KZW5kb2JqCjIzMyAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9Ob25TdHJ1Y3QKL1AgMjMxIDAgUgovSyBbPDwvVHlwZSAvTUNSCi9QZyAyIDAgUgovTUNJRCA2Nz4+XQovSUQgKG5vZGUwMDAwMDMxMyk+PgplbmRvYmoKMjMxIDAgb2JqCjw8L1R5cGUgL1N0cnVjdEVsZW0KL1MgL05vblN0cnVjdAovUCAyMzAgMCBSCi9LIFsyMzIgMCBSIDIzMyAwIFJdCi9JRCAobm9kZTAwMDAwMzEwKT4+CmVuZG9iagoyMzUgMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvTm9uU3RydWN0Ci9QIDIzNCAwIFIKL0sgWzw8L1R5cGUgL01DUgovUGcgMiAwIFIKL01DSUQgNjg+Pl0KL0lEIChub2RlMDAwMDAzMTYpPj4KZW5kb2JqCjIzNCAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9Ob25TdHJ1Y3QKL1AgMjMwIDAgUgovSyBbMjM1IDAgUl0KL0lEIChub2RlMDAwMDAzMTQpPj4KZW5kb2JqCjIzNyAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9Ob25TdHJ1Y3QKL1AgMjM2IDAgUgovSyBbPDwvVHlwZSAvTUNSCi9QZyAyIDAgUgovTUNJRCA2OT4+XQovSUQgKG5vZGUwMDAwMDMyMCk+PgplbmRvYmoKMjM4IDAgb2JqCjw8L1R5cGUgL1N0cnVjdEVsZW0KL1MgL05vblN0cnVjdAovUCAyMzYgMCBSCi9LIFs8PC9UeXBlIC9NQ1IKL1BnIDIgMCBSCi9NQ0lEIDcwPj5dCi9JRCAobm9kZTAwMDAwMzIyKT4+CmVuZG9iagoyMzYgMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvTm9uU3RydWN0Ci9QIDIzMCAwIFIKL0sgWzIzNyAwIFIgMjM4IDAgUl0KL0lEIChub2RlMDAwMDAzMTgpPj4KZW5kb2JqCjIzMCAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9Ob25TdHJ1Y3QKL1AgMjA5IDAgUgovSyBbMjMxIDAgUiAyMzQgMCBSIDIzNiAwIFJdCi9JRCAobm9kZTAwMDAwMzA5KT4+CmVuZG9iagoyNDEgMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvTm9uU3RydWN0Ci9QIDI0MCAwIFIKL0sgWzw8L1R5cGUgL01DUgovUGcgMiAwIFIKL01DSUQgNzE+Pl0KL0lEIChub2RlMDAwMDAzMjYpPj4KZW5kb2JqCjI0MiAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9Ob25TdHJ1Y3QKL1AgMjQwIDAgUgovSyBbPDwvVHlwZSAvTUNSCi9QZyAyIDAgUgovTUNJRCA3Mj4+XQovSUQgKG5vZGUwMDAwMDMyOCk+PgplbmRvYmoKMjQwIDAgb2JqCjw8L1R5cGUgL1N0cnVjdEVsZW0KL1MgL05vblN0cnVjdAovUCAyMzkgMCBSCi9LIFsyNDEgMCBSIDI0MiAwIFJdCi9JRCAobm9kZTAwMDAwMzI1KT4+CmVuZG9iagoyMzkgMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvTm9uU3RydWN0Ci9QIDIwOSAwIFIKL0sgWzI0MCAwIFJdCi9JRCAobm9kZTAwMDAwMzI0KT4+CmVuZG9iagoyNDUgMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvTm9uU3RydWN0Ci9QIDI0NCAwIFIKL0sgWzw8L1R5cGUgL01DUgovUGcgMiAwIFIKL01DSUQgNzM+Pl0KL0lEIChub2RlMDAwMDAzMzMpPj4KZW5kb2JqCjI0NiAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9Ob25TdHJ1Y3QKL1AgMjQ0IDAgUgovSyBbPDwvVHlwZSAvTUNSCi9QZyAyIDAgUgovTUNJRCA3ND4+XQovSUQgKG5vZGUwMDAwMDMzNCk+PgplbmRvYmoKMjQ0IDAgb2JqCjw8L1R5cGUgL1N0cnVjdEVsZW0KL1MgL05vblN0cnVjdAovUCAyNDMgMCBSCi9LIFsyNDUgMCBSIDI0NiAwIFJdCi9JRCAobm9kZTAwMDAwMzMxKT4+CmVuZG9iagoyNDggMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvTm9uU3RydWN0Ci9QIDI0NyAwIFIKL0sgWzw8L1R5cGUgL01DUgovUGcgMiAwIFIKL01DSUQgNzU+Pl0KL0lEIChub2RlMDAwMDAzMzcpPj4KZW5kb2JqCjI0NyAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9Ob25TdHJ1Y3QKL1AgMjQzIDAgUgovSyBbMjQ4IDAgUl0KL0lEIChub2RlMDAwMDAzMzUpPj4KZW5kb2JqCjI1MCAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9Ob25TdHJ1Y3QKL1AgMjQ5IDAgUgovSyBbPDwvVHlwZSAvTUNSCi9QZyAyIDAgUgovTUNJRCA3Nj4+XQovSUQgKG5vZGUwMDAwMDM0MSk+PgplbmRvYmoKMjUxIDAgb2JqCjw8L1R5cGUgL1N0cnVjdEVsZW0KL1MgL05vblN0cnVjdAovUCAyNDkgMCBSCi9LIFs8PC9UeXBlIC9NQ1IKL1BnIDIgMCBSCi9NQ0lEIDc3Pj5dCi9JRCAobm9kZTAwMDAwMzQzKT4+CmVuZG9iagoyNDkgMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvTm9uU3RydWN0Ci9QIDI0MyAwIFIKL0sgWzI1MCAwIFIgMjUxIDAgUl0KL0lEIChub2RlMDAwMDAzMzkpPj4KZW5kb2JqCjI0MyAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9Ob25TdHJ1Y3QKL1AgMjA5IDAgUgovSyBbMjQ0IDAgUiAyNDcgMCBSIDI0OSAwIFJdCi9JRCAobm9kZTAwMDAwMzMwKT4+CmVuZG9iagoyMDkgMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvTm9uU3RydWN0Ci9QIDIwOCAwIFIKL0sgWzIxMCAwIFIgMjEzIDAgUiAyMTcgMCBSIDIyNiAwIFIgMjMwIDAgUiAyMzkgMCBSIDI0MyAwIFJdCi9JRCAobm9kZTAwMDAwMjc1KT4+CmVuZG9iagoyMDggMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvVEQKL1AgMjA3IDAgUgovSyBbMjA5IDAgUl0KL0EgWzw8L08gL1RhYmxlCi9IZWFkZXJzIFtdPj4gPDwvTyAvVGFibGUKL1Jvd1NwYW4gMT4+IDw8L08gL1RhYmxlCi9Db2xTcGFuIDE+Pl0KL0lEIChub2RlMDAwMDAyNzQpPj4KZW5kb2JqCjIwNyAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9UUgovUCAyMiAwIFIKL0sgWzIwOCAwIFJdCi9JRCAobm9kZTAwMDAwMjczKT4+CmVuZG9iagoyNTcgMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvTm9uU3RydWN0Ci9QIDI1NiAwIFIKL0sgWzw8L1R5cGUgL01DUgovUGcgMiAwIFIKL01DSUQgNzg+Pl0KL0lEIChub2RlMDAwMDAzNTUpPj4KZW5kb2JqCjI1NiAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9Ob25TdHJ1Y3QKL1AgMjU1IDAgUgovSyBbMjU3IDAgUl0KL0lEIChub2RlMDAwMDAzNTQpPj4KZW5kb2JqCjI1NSAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9Ob25TdHJ1Y3QKL1AgMjU0IDAgUgovSyBbMjU2IDAgUl0KL0lEIChub2RlMDAwMDAzNTMpPj4KZW5kb2JqCjI2MCAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9Ob25TdHJ1Y3QKL1AgMjU5IDAgUgovSyBbPDwvVHlwZSAvTUNSCi9QZyAyIDAgUgovTUNJRCA3OT4+XQovSUQgKG5vZGUwMDAwMDM2MCk+PgplbmRvYmoKMjU5IDAgb2JqCjw8L1R5cGUgL1N0cnVjdEVsZW0KL1MgL05vblN0cnVjdAovUCAyNTggMCBSCi9LIFsyNjAgMCBSXQovSUQgKG5vZGUwMDAwMDM1OSk+PgplbmRvYmoKMjYyIDAgb2JqCjw8L1R5cGUgL1N0cnVjdEVsZW0KL1MgL05vblN0cnVjdAovUCAyNjEgMCBSCi9LIFs8PC9UeXBlIC9NQ1IKL1BnIDIgMCBSCi9NQ0lEIDgwPj5dCi9JRCAobm9kZTAwMDAwMzYyKT4+CmVuZG9iagoyNjEgMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvTm9uU3RydWN0Ci9QIDI1OCAwIFIKL0sgWzI2MiAwIFJdCi9JRCAobm9kZTAwMDAwMzYxKT4+CmVuZG9iagoyNjQgMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvTm9uU3RydWN0Ci9QIDI2MyAwIFIKL0sgWzw8L1R5cGUgL01DUgovUGcgMiAwIFIKL01DSUQgODE+Pl0KL0lEIChub2RlMDAwMDAzNjQpPj4KZW5kb2JqCjI2MyAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9Ob25TdHJ1Y3QKL1AgMjU4IDAgUgovSyBbMjY0IDAgUl0KL0lEIChub2RlMDAwMDAzNjMpPj4KZW5kb2JqCjI2NiAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9Ob25TdHJ1Y3QKL1AgMjY1IDAgUgovSyBbPDwvVHlwZSAvTUNSCi9QZyAyIDAgUgovTUNJRCA4Mj4+XQovSUQgKG5vZGUwMDAwMDM2Nik+PgplbmRvYmoKMjY1IDAgb2JqCjw8L1R5cGUgL1N0cnVjdEVsZW0KL1MgL05vblN0cnVjdAovUCAyNTggMCBSCi9LIFsyNjYgMCBSXQovSUQgKG5vZGUwMDAwMDM2NSk+PgplbmRvYmoKMjY4IDAgb2JqCjw8L1R5cGUgL1N0cnVjdEVsZW0KL1MgL05vblN0cnVjdAovUCAyNjcgMCBSCi9LIFs8PC9UeXBlIC9NQ1IKL1BnIDIgMCBSCi9NQ0lEIDgzPj4gPDwvVHlwZSAvTUNSCi9QZyAyIDAgUgovTUNJRCA4ND4+XQovSUQgKG5vZGUwMDAwMDM2OCk+PgplbmRvYmoKMjY3IDAgb2JqCjw8L1R5cGUgL1N0cnVjdEVsZW0KL1MgL05vblN0cnVjdAovUCAyNTggMCBSCi9LIFsyNjggMCBSXQovSUQgKG5vZGUwMDAwMDM2Nyk+PgplbmRvYmoKMjcwIDAgb2JqCjw8L1R5cGUgL1N0cnVjdEVsZW0KL1MgL05vblN0cnVjdAovUCAyNjkgMCBSCi9LIFs8PC9UeXBlIC9NQ1IKL1BnIDIgMCBSCi9NQ0lEIDg1Pj4gPDwvVHlwZSAvTUNSCi9QZyAyIDAgUgovTUNJRCA4Nj4+XQovSUQgKG5vZGUwMDAwMDM3MCk+PgplbmRvYmoKMjY5IDAgb2JqCjw8L1R5cGUgL1N0cnVjdEVsZW0KL1MgL05vblN0cnVjdAovUCAyNTggMCBSCi9LIFsyNzAgMCBSXQovSUQgKG5vZGUwMDAwMDM2OSk+PgplbmRvYmoKMjU4IDAgb2JqCjw8L1R5cGUgL1N0cnVjdEVsZW0KL1MgL05vblN0cnVjdAovUCAyNTQgMCBSCi9LIFsyNTkgMCBSIDI2MSAwIFIgMjYzIDAgUiAyNjUgMCBSIDI2NyAwIFIgMjY5IDAgUl0KL0lEIChub2RlMDAwMDAzNTgpPj4KZW5kb2JqCjI3MyAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9Ob25TdHJ1Y3QKL1AgMjcyIDAgUgovSyBbPDwvVHlwZSAvTUNSCi9QZyAyIDAgUgovTUNJRCA4Nz4+XQovSUQgKG5vZGUwMDAwMDM3Myk+PgplbmRvYmoKMjcyIDAgb2JqCjw8L1R5cGUgL1N0cnVjdEVsZW0KL1MgL05vblN0cnVjdAovUCAyNzEgMCBSCi9LIFsyNzMgMCBSXQovSUQgKG5vZGUwMDAwMDM3Mik+PgplbmRvYmoKMjc1IDAgb2JqCjw8L1R5cGUgL1N0cnVjdEVsZW0KL1MgL05vblN0cnVjdAovUCAyNzQgMCBSCi9LIFs8PC9UeXBlIC9NQ1IKL1BnIDIgMCBSCi9NQ0lEIDg4Pj5dCi9JRCAobm9kZTAwMDAwMzc1KT4+CmVuZG9iagoyNzQgMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvTm9uU3RydWN0Ci9QIDI3MSAwIFIKL0sgWzI3NSAwIFJdCi9JRCAobm9kZTAwMDAwMzc0KT4+CmVuZG9iagoyNzcgMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvTm9uU3RydWN0Ci9QIDI3NiAwIFIKL0sgWzw8L1R5cGUgL01DUgovUGcgMiAwIFIKL01DSUQgODk+Pl0KL0lEIChub2RlMDAwMDAzNzcpPj4KZW5kb2JqCjI3NiAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9Ob25TdHJ1Y3QKL1AgMjcxIDAgUgovSyBbMjc3IDAgUl0KL0lEIChub2RlMDAwMDAzNzYpPj4KZW5kb2JqCjI3OSAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9Ob25TdHJ1Y3QKL1AgMjc4IDAgUgovSyBbPDwvVHlwZSAvTUNSCi9QZyAyIDAgUgovTUNJRCA5MD4+XQovSUQgKG5vZGUwMDAwMDM4MCk+PgplbmRvYmoKMjc4IDAgb2JqCjw8L1R5cGUgL1N0cnVjdEVsZW0KL1MgL05vblN0cnVjdAovUCAyNzEgMCBSCi9LIFsyNzkgMCBSXQovSUQgKG5vZGUwMDAwMDM3OSk+PgplbmRvYmoKMjgxIDAgb2JqCjw8L1R5cGUgL1N0cnVjdEVsZW0KL1MgL05vblN0cnVjdAovUCAyODAgMCBSCi9LIFs8PC9UeXBlIC9NQ1IKL1BnIDIgMCBSCi9NQ0lEIDkxPj5dCi9JRCAobm9kZTAwMDAwMzgyKT4+CmVuZG9iagoyODAgMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvTm9uU3RydWN0Ci9QIDI3MSAwIFIKL0sgWzI4MSAwIFJdCi9JRCAobm9kZTAwMDAwMzgxKT4+CmVuZG9iagoyNzEgMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvTm9uU3RydWN0Ci9QIDI1NCAwIFIKL0sgWzI3MiAwIFIgMjc0IDAgUiAyNzYgMCBSIDI3OCAwIFIgMjgwIDAgUl0KL0lEIChub2RlMDAwMDAzNzEpPj4KZW5kb2JqCjI1NCAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9Ob25TdHJ1Y3QKL1AgMjUzIDAgUgovSyBbMjU1IDAgUiAyNTggMCBSIDI3MSAwIFJdCi9JRCAobm9kZTAwMDAwMzUxKT4+CmVuZG9iagoyNTMgMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvVEQKL1AgMjUyIDAgUgovSyBbMjU0IDAgUl0KL0EgWzw8L08gL1RhYmxlCi9IZWFkZXJzIFtdPj4gPDwvTyAvVGFibGUKL1Jvd1NwYW4gMT4+IDw8L08gL1RhYmxlCi9Db2xTcGFuIDE+Pl0KL0lEIChub2RlMDAwMDAzNTApPj4KZW5kb2JqCjI1MiAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9UUgovUCAyMiAwIFIKL0sgWzI1MyAwIFJdCi9JRCAobm9kZTAwMDAwMzQ5KT4+CmVuZG9iagoyODcgMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvTm9uU3RydWN0Ci9QIDI4NiAwIFIKL0sgWzw8L1R5cGUgL01DUgovUGcgMiAwIFIKL01DSUQgOTI+Pl0KL0lEIChub2RlMDAwMDAzOTMpPj4KZW5kb2JqCjI4NiAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9Ob25TdHJ1Y3QKL1AgMjg1IDAgUgovSyBbMjg3IDAgUl0KL0lEIChub2RlMDAwMDAzOTIpPj4KZW5kb2JqCjI4NSAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9Ob25TdHJ1Y3QKL1AgMjg0IDAgUgovSyBbMjg2IDAgUl0KL0lEIChub2RlMDAwMDAzOTEpPj4KZW5kb2JqCjI5MCAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9Ob25TdHJ1Y3QKL1AgMjg5IDAgUgovSyBbPDwvVHlwZSAvTUNSCi9QZyAyIDAgUgovTUNJRCA5Mz4+XQovSUQgKG5vZGUwMDAwMDM5OCk+PgplbmRvYmoKMjg5IDAgb2JqCjw8L1R5cGUgL1N0cnVjdEVsZW0KL1MgL05vblN0cnVjdAovUCAyODggMCBSCi9LIFsyOTAgMCBSXQovSUQgKG5vZGUwMDAwMDM5Nyk+PgplbmRvYmoKMjg4IDAgb2JqCjw8L1R5cGUgL1N0cnVjdEVsZW0KL1MgL05vblN0cnVjdAovUCAyODQgMCBSCi9LIFsyODkgMCBSXQovSUQgKG5vZGUwMDAwMDM5Nik+PgplbmRvYmoKMjkzIDAgb2JqCjw8L1R5cGUgL1N0cnVjdEVsZW0KL1MgL05vblN0cnVjdAovUCAyOTIgMCBSCi9LIFs8PC9UeXBlIC9NQ1IKL1BnIDIgMCBSCi9NQ0lEIDk0Pj5dCi9JRCAobm9kZTAwMDAwNDAzKT4+CmVuZG9iagoyOTIgMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvTm9uU3RydWN0Ci9QIDI5MSAwIFIKL0sgWzI5MyAwIFJdCi9JRCAobm9kZTAwMDAwNDAyKT4+CmVuZG9iagoyOTUgMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvTm9uU3RydWN0Ci9QIDI5NCAwIFIKL0sgWzw8L1R5cGUgL01DUgovUGcgMiAwIFIKL01DSUQgOTU+Pl0KL0lEIChub2RlMDAwMDA0MDUpPj4KZW5kb2JqCjI5NCAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9Ob25TdHJ1Y3QKL1AgMjkxIDAgUgovSyBbMjk1IDAgUl0KL0lEIChub2RlMDAwMDA0MDQpPj4KZW5kb2JqCjI5NyAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9Ob25TdHJ1Y3QKL1AgMjk2IDAgUgovSyBbPDwvVHlwZSAvTUNSCi9QZyAyIDAgUgovTUNJRCA5Nj4+XQovSUQgKG5vZGUwMDAwMDQwNyk+PgplbmRvYmoKMjk2IDAgb2JqCjw8L1R5cGUgL1N0cnVjdEVsZW0KL1MgL05vblN0cnVjdAovUCAyOTEgMCBSCi9LIFsyOTcgMCBSXQovSUQgKG5vZGUwMDAwMDQwNik+PgplbmRvYmoKMjk5IDAgb2JqCjw8L1R5cGUgL1N0cnVjdEVsZW0KL1MgL05vblN0cnVjdAovUCAyOTggMCBSCi9LIFs8PC9UeXBlIC9NQ1IKL1BnIDIgMCBSCi9NQ0lEIDk3Pj5dCi9JRCAobm9kZTAwMDAwNDA5KT4+CmVuZG9iagoyOTggMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvTm9uU3RydWN0Ci9QIDI5MSAwIFIKL0sgWzI5OSAwIFJdCi9JRCAobm9kZTAwMDAwNDA4KT4+CmVuZG9iagozMDEgMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvTm9uU3RydWN0Ci9QIDMwMCAwIFIKL0sgWzw8L1R5cGUgL01DUgovUGcgMiAwIFIKL01DSUQgOTg+Pl0KL0lEIChub2RlMDAwMDA0MTEpPj4KZW5kb2JqCjMwMCAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9Ob25TdHJ1Y3QKL1AgMjkxIDAgUgovSyBbMzAxIDAgUl0KL0lEIChub2RlMDAwMDA0MTApPj4KZW5kb2JqCjI5MSAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9Ob25TdHJ1Y3QKL1AgMjg0IDAgUgovSyBbMjkyIDAgUiAyOTQgMCBSIDI5NiAwIFIgMjk4IDAgUiAzMDAgMCBSXQovSUQgKG5vZGUwMDAwMDQwMSk+PgplbmRvYmoKMzA0IDAgb2JqCjw8L1R5cGUgL1N0cnVjdEVsZW0KL1MgL05vblN0cnVjdAovUCAzMDMgMCBSCi9LIFs8PC9UeXBlIC9NQ1IKL1BnIDIgMCBSCi9NQ0lEIDk5Pj5dCi9JRCAobm9kZTAwMDAwNDE0KT4+CmVuZG9iagozMDMgMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvTm9uU3RydWN0Ci9QIDMwMiAwIFIKL0sgWzMwNCAwIFJdCi9JRCAobm9kZTAwMDAwNDEzKT4+CmVuZG9iagozMDYgMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvTm9uU3RydWN0Ci9QIDMwNSAwIFIKL0sgWzw8L1R5cGUgL01DUgovUGcgMiAwIFIKL01DSUQgMTAwPj5dCi9JRCAobm9kZTAwMDAwNDE3KT4+CmVuZG9iagozMDcgMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvTm9uU3RydWN0Ci9QIDMwNSAwIFIKL0sgWzw8L1R5cGUgL01DUgovUGcgMiAwIFIKL01DSUQgMTAxPj5dCi9JRCAobm9kZTAwMDAwNDE4KT4+CmVuZG9iagozMDUgMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvTm9uU3RydWN0Ci9QIDMwMiAwIFIKL0sgWzMwNiAwIFIgMzA3IDAgUl0KL0lEIChub2RlMDAwMDA0MTUpPj4KZW5kb2JqCjMwOSAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9Ob25TdHJ1Y3QKL1AgMzA4IDAgUgovSyBbPDwvVHlwZSAvTUNSCi9QZyAyIDAgUgovTUNJRCAxMDI+Pl0KL0lEIChub2RlMDAwMDA0MjEpPj4KZW5kb2JqCjMwOCAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9Ob25TdHJ1Y3QKL1AgMzAyIDAgUgovSyBbMzA5IDAgUl0KL0lEIChub2RlMDAwMDA0MTkpPj4KZW5kb2JqCjMxMSAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9Ob25TdHJ1Y3QKL1AgMzEwIDAgUgovSyBbPDwvVHlwZSAvTUNSCi9QZyAyIDAgUgovTUNJRCAxMDM+Pl0KL0lEIChub2RlMDAwMDA0MjUpPj4KZW5kb2JqCjMxMCAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9Ob25TdHJ1Y3QKL1AgMzAyIDAgUgovSyBbMzExIDAgUl0KL0lEIChub2RlMDAwMDA0MjMpPj4KZW5kb2JqCjMxMyAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9Ob25TdHJ1Y3QKL1AgMzEyIDAgUgovSyBbPDwvVHlwZSAvTUNSCi9QZyAyIDAgUgovTUNJRCAxMDQ+Pl0KL0lEIChub2RlMDAwMDA0MjgpPj4KZW5kb2JqCjMxMiAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9Ob25TdHJ1Y3QKL1AgMzAyIDAgUgovSyBbMzEzIDAgUl0KL0lEIChub2RlMDAwMDA0MjYpPj4KZW5kb2JqCjMwMiAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9Ob25TdHJ1Y3QKL1AgMjg0IDAgUgovSyBbMzAzIDAgUiAzMDUgMCBSIDMwOCAwIFIgMzEwIDAgUiAzMTIgMCBSXQovSUQgKG5vZGUwMDAwMDQxMik+PgplbmRvYmoKMzE2IDAgb2JqCjw8L1R5cGUgL1N0cnVjdEVsZW0KL1MgL05vblN0cnVjdAovUCAzMTUgMCBSCi9LIFs8PC9UeXBlIC9NQ1IKL1BnIDIgMCBSCi9NQ0lEIDEwNT4+XQovSUQgKG5vZGUwMDAwMDQzNik+PgplbmRvYmoKMzE1IDAgb2JqCjw8L1R5cGUgL1N0cnVjdEVsZW0KL1MgL05vblN0cnVjdAovUCAzMTQgMCBSCi9LIFszMTYgMCBSXQovSUQgKG5vZGUwMDAwMDQzNCk+PgplbmRvYmoKMzE4IDAgb2JqCjw8L1R5cGUgL1N0cnVjdEVsZW0KL1MgL05vblN0cnVjdAovUCAzMTcgMCBSCi9LIFs8PC9UeXBlIC9NQ1IKL1BnIDIgMCBSCi9NQ0lEIDEwNj4+XQovSUQgKG5vZGUwMDAwMDQ0MCk+PgplbmRvYmoKMzE3IDAgb2JqCjw8L1R5cGUgL1N0cnVjdEVsZW0KL1MgL05vblN0cnVjdAovUCAzMTQgMCBSCi9LIFszMTggMCBSXQovSUQgKG5vZGUwMDAwMDQzOCk+PgplbmRvYmoKMzIwIDAgb2JqCjw8L1R5cGUgL1N0cnVjdEVsZW0KL1MgL05vblN0cnVjdAovUCAzMTkgMCBSCi9LIFs8PC9UeXBlIC9NQ1IKL1BnIDIgMCBSCi9NQ0lEIDEwNz4+XQovSUQgKG5vZGUwMDAwMDQ0Myk+PgplbmRvYmoKMzE5IDAgb2JqCjw8L1R5cGUgL1N0cnVjdEVsZW0KL1MgL05vblN0cnVjdAovUCAzMTQgMCBSCi9LIFszMjAgMCBSXQovSUQgKG5vZGUwMDAwMDQ0MSk+PgplbmRvYmoKMzE0IDAgb2JqCjw8L1R5cGUgL1N0cnVjdEVsZW0KL1MgL05vblN0cnVjdAovUCAyODQgMCBSCi9LIFszMTUgMCBSIDMxNyAwIFIgMzE5IDAgUl0KL0lEIChub2RlMDAwMDA0MzIpPj4KZW5kb2JqCjMyMyAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9Ob25TdHJ1Y3QKL1AgMzIyIDAgUgovSyBbPDwvVHlwZSAvTUNSCi9QZyAyIDAgUgovTUNJRCAxMDg+Pl0KL0lEIChub2RlMDAwMDA0NTEpPj4KZW5kb2JqCjMyMiAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9Ob25TdHJ1Y3QKL1AgMzIxIDAgUgovSyBbMzIzIDAgUl0KL0lEIChub2RlMDAwMDA0NDkpPj4KZW5kb2JqCjMyMSAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9Ob25TdHJ1Y3QKL1AgMjg0IDAgUgovSyBbMzIyIDAgUl0KL0lEIChub2RlMDAwMDA0NDcpPj4KZW5kb2JqCjMyNiAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9Ob25TdHJ1Y3QKL1AgMzI1IDAgUgovSyBbPDwvVHlwZSAvTUNSCi9QZyAyIDAgUgovTUNJRCAxMDk+Pl0KL0lEIChub2RlMDAwMDA0NTgpPj4KZW5kb2JqCjMyNSAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9Ob25TdHJ1Y3QKL1AgMzI0IDAgUgovSyBbMzI2IDAgUl0KL0lEIChub2RlMDAwMDA0NTcpPj4KZW5kb2JqCjMyNCAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9Ob25TdHJ1Y3QKL1AgMjg0IDAgUgovSyBbMzI1IDAgUl0KL0lEIChub2RlMDAwMDA0NTYpPj4KZW5kb2JqCjMyOSAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9Ob25TdHJ1Y3QKL1AgMzI4IDAgUgovSyBbPDwvVHlwZSAvTUNSCi9QZyAyIDAgUgovTUNJRCAxMTA+Pl0KL0lEIChub2RlMDAwMDA0NjMpPj4KZW5kb2JqCjMyOCAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9Ob25TdHJ1Y3QKL1AgMzI3IDAgUgovSyBbMzI5IDAgUl0KL0lEIChub2RlMDAwMDA0NjIpPj4KZW5kb2JqCjMzMSAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9Ob25TdHJ1Y3QKL1AgMzMwIDAgUgovSyBbPDwvVHlwZSAvTUNSCi9QZyAyIDAgUgovTUNJRCAxMTE+Pl0KL0lEIChub2RlMDAwMDA0NjUpPj4KZW5kb2JqCjMzMCAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9Ob25TdHJ1Y3QKL1AgMzI3IDAgUgovSyBbMzMxIDAgUl0KL0lEIChub2RlMDAwMDA0NjQpPj4KZW5kb2JqCjMzMyAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9Ob25TdHJ1Y3QKL1AgMzMyIDAgUgovSyBbPDwvVHlwZSAvTUNSCi9QZyAyIDAgUgovTUNJRCAxMTI+Pl0KL0lEIChub2RlMDAwMDA0NjcpPj4KZW5kb2JqCjMzMiAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9Ob25TdHJ1Y3QKL1AgMzI3IDAgUgovSyBbMzMzIDAgUl0KL0lEIChub2RlMDAwMDA0NjYpPj4KZW5kb2JqCjMzNSAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9Ob25TdHJ1Y3QKL1AgMzM0IDAgUgovSyBbPDwvVHlwZSAvTUNSCi9QZyAyIDAgUgovTUNJRCAxMTM+Pl0KL0lEIChub2RlMDAwMDA0NjkpPj4KZW5kb2JqCjMzNCAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9Ob25TdHJ1Y3QKL1AgMzI3IDAgUgovSyBbMzM1IDAgUl0KL0lEIChub2RlMDAwMDA0NjgpPj4KZW5kb2JqCjMzNyAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9Ob25TdHJ1Y3QKL1AgMzM2IDAgUgovSyBbPDwvVHlwZSAvTUNSCi9QZyAyIDAgUgovTUNJRCAxMTQ+Pl0KL0lEIChub2RlMDAwMDA0NzEpPj4KZW5kb2JqCjMzNiAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9Ob25TdHJ1Y3QKL1AgMzI3IDAgUgovSyBbMzM3IDAgUl0KL0lEIChub2RlMDAwMDA0NzApPj4KZW5kb2JqCjMzOSAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9Ob25TdHJ1Y3QKL1AgMzM4IDAgUgovSyBbPDwvVHlwZSAvTUNSCi9QZyAyIDAgUgovTUNJRCAxMTU+Pl0KL0lEIChub2RlMDAwMDA0NzMpPj4KZW5kb2JqCjMzOCAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9Ob25TdHJ1Y3QKL1AgMzI3IDAgUgovSyBbMzM5IDAgUl0KL0lEIChub2RlMDAwMDA0NzIpPj4KZW5kb2JqCjMyNyAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9Ob25TdHJ1Y3QKL1AgMjg0IDAgUgovSyBbMzI4IDAgUiAzMzAgMCBSIDMzMiAwIFIgMzM0IDAgUiAzMzYgMCBSIDMzOCAwIFJdCi9JRCAobm9kZTAwMDAwNDYxKT4+CmVuZG9iagozNDIgMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvTm9uU3RydWN0Ci9QIDM0MSAwIFIKL0sgWzw8L1R5cGUgL01DUgovUGcgMiAwIFIKL01DSUQgMTE2Pj5dCi9JRCAobm9kZTAwMDAwNDc2KT4+CmVuZG9iagozNDEgMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvTm9uU3RydWN0Ci9QIDM0MCAwIFIKL0sgWzM0MiAwIFJdCi9JRCAobm9kZTAwMDAwNDc1KT4+CmVuZG9iagozNDQgMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvTm9uU3RydWN0Ci9QIDM0MyAwIFIKL0sgWzw8L1R5cGUgL01DUgovUGcgMiAwIFIKL01DSUQgMTE3Pj5dCi9JRCAobm9kZTAwMDAwNDc4KT4+CmVuZG9iagozNDMgMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvTm9uU3RydWN0Ci9QIDM0MCAwIFIKL0sgWzM0NCAwIFJdCi9JRCAobm9kZTAwMDAwNDc3KT4+CmVuZG9iagozNDAgMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvTm9uU3RydWN0Ci9QIDI4NCAwIFIKL0sgWzM0MSAwIFIgMzQzIDAgUl0KL0lEIChub2RlMDAwMDA0NzQpPj4KZW5kb2JqCjI4NCAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9Ob25TdHJ1Y3QKL1AgMjgzIDAgUgovSyBbMjg1IDAgUiAyODggMCBSIDI5MSAwIFIgMzAyIDAgUiAzMTQgMCBSIDMyMSAwIFIgMzI0IDAgUiAzMjcgMCBSIDM0MCAwIFJdCi9JRCAobm9kZTAwMDAwMzg5KT4+CmVuZG9iagoyODMgMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvVEQKL1AgMjgyIDAgUgovSyBbMjg0IDAgUl0KL0EgWzw8L08gL1RhYmxlCi9IZWFkZXJzIFtdPj4gPDwvTyAvVGFibGUKL1Jvd1NwYW4gMT4+IDw8L08gL1RhYmxlCi9Db2xTcGFuIDE+Pl0KL0lEIChub2RlMDAwMDAzODgpPj4KZW5kb2JqCjI4MiAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9UUgovUCAyMiAwIFIKL0sgWzI4MyAwIFJdCi9JRCAobm9kZTAwMDAwMzg3KT4+CmVuZG9iagozNDcgMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvTm9uU3RydWN0Ci9QIDM0NiAwIFIKL0sgWzw8L1R5cGUgL01DUgovUGcgMiAwIFIKL01DSUQgMTE5Pj5dCi9JRCAobm9kZTAwMDAwNDkxKT4+CmVuZG9iagozNDYgMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvVEQKL1AgMzQ1IDAgUgovSyBbMzQ3IDAgUl0KL0EgWzw8L08gL1RhYmxlCi9IZWFkZXJzIFtdPj4gPDwvTyAvVGFibGUKL1Jvd1NwYW4gMT4+IDw8L08gL1RhYmxlCi9Db2xTcGFuIDE+Pl0KL0lEIChub2RlMDAwMDA0OTApPj4KZW5kb2JqCjM0NSAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9UUgovUCAyMiAwIFIKL0sgWzM0NiAwIFJdCi9JRCAobm9kZTAwMDAwNDg5KT4+CmVuZG9iagozNTMgMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvTm9uU3RydWN0Ci9QIDM1MiAwIFIKL0sgWzw8L1R5cGUgL01DUgovUGcgMTcgMCBSCi9NQ0lEIDA+Pl0KL0lEIChub2RlMDAwMDA1MDApPj4KZW5kb2JqCjM1MiAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9Ob25TdHJ1Y3QKL1AgMzUxIDAgUgovSyBbMzUzIDAgUl0KL0lEIChub2RlMDAwMDA0OTkpPj4KZW5kb2JqCjM1NSAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9Ob25TdHJ1Y3QKL1AgMzU0IDAgUgovSyBbPDwvVHlwZSAvTUNSCi9QZyAxNyAwIFIKL01DSUQgMT4+XQovSUQgKG5vZGUwMDAwMDUwMik+PgplbmRvYmoKMzU0IDAgb2JqCjw8L1R5cGUgL1N0cnVjdEVsZW0KL1MgL05vblN0cnVjdAovUCAzNTEgMCBSCi9LIFszNTUgMCBSXQovSUQgKG5vZGUwMDAwMDUwMSk+PgplbmRvYmoKMzU3IDAgb2JqCjw8L1R5cGUgL1N0cnVjdEVsZW0KL1MgL05vblN0cnVjdAovUCAzNTYgMCBSCi9LIFs8PC9UeXBlIC9NQ1IKL1BnIDE3IDAgUgovTUNJRCAyPj5dCi9JRCAobm9kZTAwMDAwNTA0KT4+CmVuZG9iagozNTYgMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvTm9uU3RydWN0Ci9QIDM1MSAwIFIKL0sgWzM1NyAwIFJdCi9JRCAobm9kZTAwMDAwNTAzKT4+CmVuZG9iagozNTkgMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvTm9uU3RydWN0Ci9QIDM1OCAwIFIKL0sgWzw8L1R5cGUgL01DUgovUGcgMTcgMCBSCi9NQ0lEIDM+Pl0KL0lEIChub2RlMDAwMDA1MDYpPj4KZW5kb2JqCjM1OCAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9Ob25TdHJ1Y3QKL1AgMzUxIDAgUgovSyBbMzU5IDAgUl0KL0lEIChub2RlMDAwMDA1MDUpPj4KZW5kb2JqCjM1MSAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9Ob25TdHJ1Y3QKL1AgMzUwIDAgUgovSyBbMzUyIDAgUiAzNTQgMCBSIDM1NiAwIFIgMzU4IDAgUl0KL0lEIChub2RlMDAwMDA0OTgpPj4KZW5kb2JqCjM2NSAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9Ob25TdHJ1Y3QKL1AgMzY0IDAgUgovSyBbPDwvVHlwZSAvTUNSCi9QZyAxNyAwIFIKL01DSUQgND4+XQovSUQgKG5vZGUwMDAwMDUxNik+PgplbmRvYmoKMzY2IDAgb2JqCjw8L1R5cGUgL1N0cnVjdEVsZW0KL1MgL05vblN0cnVjdAovUCAzNjQgMCBSCi9LIFs8PC9UeXBlIC9NQ1IKL1BnIDE3IDAgUgovTUNJRCA1Pj5dCi9JRCAobm9kZTAwMDAwNTE3KT4+CmVuZG9iagozNjQgMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvTm9uU3RydWN0Ci9QIDM2MyAwIFIKL0sgWzM2NSAwIFIgMzY2IDAgUl0KL0lEIChub2RlMDAwMDA1MTQpPj4KZW5kb2JqCjM2MyAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9Ob25TdHJ1Y3QKL1AgMzYyIDAgUgovSyBbMzY0IDAgUl0KL0lEIChub2RlMDAwMDA1MTMpPj4KZW5kb2JqCjM2OSAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9Ob25TdHJ1Y3QKL1AgMzY4IDAgUgovSyBbPDwvVHlwZSAvTUNSCi9QZyAxNyAwIFIKL01DSUQgNj4+XQovSUQgKG5vZGUwMDAwMDUyMSk+PgplbmRvYmoKMzcwIDAgb2JqCjw8L1R5cGUgL1N0cnVjdEVsZW0KL1MgL05vblN0cnVjdAovUCAzNjggMCBSCi9LIFs8PC9UeXBlIC9NQ1IKL1BnIDE3IDAgUgovTUNJRCA3Pj5dCi9JRCAobm9kZTAwMDAwNTIyKT4+CmVuZG9iagozNjggMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvTm9uU3RydWN0Ci9QIDM2NyAwIFIKL0sgWzM2OSAwIFIgMzcwIDAgUl0KL0lEIChub2RlMDAwMDA1MTkpPj4KZW5kb2JqCjM2NyAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9Ob25TdHJ1Y3QKL1AgMzYyIDAgUgovSyBbMzY4IDAgUl0KL0lEIChub2RlMDAwMDA1MTgpPj4KZW5kb2JqCjM3MyAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9Ob25TdHJ1Y3QKL1AgMzcyIDAgUgovSyBbPDwvVHlwZSAvTUNSCi9QZyAxNyAwIFIKL01DSUQgOD4+XQovSUQgKG5vZGUwMDAwMDUyNik+PgplbmRvYmoKMzc0IDAgb2JqCjw8L1R5cGUgL1N0cnVjdEVsZW0KL1MgL05vblN0cnVjdAovUCAzNzIgMCBSCi9LIFs8PC9UeXBlIC9NQ1IKL1BnIDE3IDAgUgovTUNJRCA5Pj5dCi9JRCAobm9kZTAwMDAwNTI3KT4+CmVuZG9iagozNzIgMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvTm9uU3RydWN0Ci9QIDM3MSAwIFIKL0sgWzM3MyAwIFIgMzc0IDAgUl0KL0lEIChub2RlMDAwMDA1MjQpPj4KZW5kb2JqCjM3MSAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9Ob25TdHJ1Y3QKL1AgMzYyIDAgUgovSyBbMzcyIDAgUl0KL0lEIChub2RlMDAwMDA1MjMpPj4KZW5kb2JqCjM3NyAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9Ob25TdHJ1Y3QKL1AgMzc2IDAgUgovSyBbPDwvVHlwZSAvTUNSCi9QZyAxNyAwIFIKL01DSUQgMTA+Pl0KL0lEIChub2RlMDAwMDA1MzEpPj4KZW5kb2JqCjM3OCAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9Ob25TdHJ1Y3QKL1AgMzc2IDAgUgovSyBbPDwvVHlwZSAvTUNSCi9QZyAxNyAwIFIKL01DSUQgMTE+Pl0KL0lEIChub2RlMDAwMDA1MzIpPj4KZW5kb2JqCjM3NiAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9Ob25TdHJ1Y3QKL1AgMzc1IDAgUgovSyBbMzc3IDAgUiAzNzggMCBSXQovSUQgKG5vZGUwMDAwMDUyOSk+PgplbmRvYmoKMzc1IDAgb2JqCjw8L1R5cGUgL1N0cnVjdEVsZW0KL1MgL05vblN0cnVjdAovUCAzNjIgMCBSCi9LIFszNzYgMCBSXQovSUQgKG5vZGUwMDAwMDUyOCk+PgplbmRvYmoKMzgxIDAgb2JqCjw8L1R5cGUgL1N0cnVjdEVsZW0KL1MgL05vblN0cnVjdAovUCAzODAgMCBSCi9LIFs8PC9UeXBlIC9NQ1IKL1BnIDE3IDAgUgovTUNJRCAxMj4+XQovSUQgKG5vZGUwMDAwMDUzNik+PgplbmRvYmoKMzgyIDAgb2JqCjw8L1R5cGUgL1N0cnVjdEVsZW0KL1MgL05vblN0cnVjdAovUCAzODAgMCBSCi9LIFs8PC9UeXBlIC9NQ1IKL1BnIDE3IDAgUgovTUNJRCAxMz4+XQovSUQgKG5vZGUwMDAwMDUzNyk+PgplbmRvYmoKMzgwIDAgb2JqCjw8L1R5cGUgL1N0cnVjdEVsZW0KL1MgL05vblN0cnVjdAovUCAzNzkgMCBSCi9LIFszODEgMCBSIDM4MiAwIFJdCi9JRCAobm9kZTAwMDAwNTM0KT4+CmVuZG9iagozNzkgMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvTm9uU3RydWN0Ci9QIDM2MiAwIFIKL0sgWzM4MCAwIFJdCi9JRCAobm9kZTAwMDAwNTMzKT4+CmVuZG9iagozNjIgMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvTm9uU3RydWN0Ci9QIDM2MSAwIFIKL0sgWzM2MyAwIFIgMzY3IDAgUiAzNzEgMCBSIDM3NSAwIFIgMzc5IDAgUl0KL0lEIChub2RlMDAwMDA1MTEpPj4KZW5kb2JqCjM2MSAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9Ob25TdHJ1Y3QKL1AgMzYwIDAgUgovSyBbMzYyIDAgUl0KL0lEIChub2RlMDAwMDA1MTApPj4KZW5kb2JqCjM4NyAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9Ob25TdHJ1Y3QKL1AgMzg2IDAgUgovSyBbPDwvVHlwZSAvTUNSCi9QZyAxNyAwIFIKL01DSUQgMTQ+Pl0KL0lEIChub2RlMDAwMDA1NDQpPj4KZW5kb2JqCjM4OCAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9Ob25TdHJ1Y3QKL1AgMzg2IDAgUgovSyBbPDwvVHlwZSAvTUNSCi9QZyAxNyAwIFIKL01DSUQgMTU+Pl0KL0lEIChub2RlMDAwMDA1NDUpPj4KZW5kb2JqCjM4NiAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9Ob25TdHJ1Y3QKL1AgMzg1IDAgUgovSyBbMzg3IDAgUiAzODggMCBSXQovSUQgKG5vZGUwMDAwMDU0Mik+PgplbmRvYmoKMzg1IDAgb2JqCjw8L1R5cGUgL1N0cnVjdEVsZW0KL1MgL05vblN0cnVjdAovUCAzODQgMCBSCi9LIFszODYgMCBSXQovSUQgKG5vZGUwMDAwMDU0MSk+PgplbmRvYmoKMzkxIDAgb2JqCjw8L1R5cGUgL1N0cnVjdEVsZW0KL1MgL05vblN0cnVjdAovUCAzOTAgMCBSCi9LIFs8PC9UeXBlIC9NQ1IKL1BnIDE3IDAgUgovTUNJRCAxNj4+XQovSUQgKG5vZGUwMDAwMDU0OSk+PgplbmRvYmoKMzkyIDAgb2JqCjw8L1R5cGUgL1N0cnVjdEVsZW0KL1MgL05vblN0cnVjdAovUCAzOTAgMCBSCi9LIFs8PC9UeXBlIC9NQ1IKL1BnIDE3IDAgUgovTUNJRCAxNz4+XQovSUQgKG5vZGUwMDAwMDU1MCk+PgplbmRvYmoKMzkwIDAgb2JqCjw8L1R5cGUgL1N0cnVjdEVsZW0KL1MgL05vblN0cnVjdAovUCAzODkgMCBSCi9LIFszOTEgMCBSIDM5MiAwIFJdCi9JRCAobm9kZTAwMDAwNTQ3KT4+CmVuZG9iagozODkgMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvTm9uU3RydWN0Ci9QIDM4NCAwIFIKL0sgWzM5MCAwIFJdCi9JRCAobm9kZTAwMDAwNTQ2KT4+CmVuZG9iagozOTUgMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvTm9uU3RydWN0Ci9QIDM5NCAwIFIKL0sgWzw8L1R5cGUgL01DUgovUGcgMTcgMCBSCi9NQ0lEIDE4Pj5dCi9JRCAobm9kZTAwMDAwNTU0KT4+CmVuZG9iagozOTYgMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvTm9uU3RydWN0Ci9QIDM5NCAwIFIKL0sgWzw8L1R5cGUgL01DUgovUGcgMTcgMCBSCi9NQ0lEIDE5Pj5dCi9JRCAobm9kZTAwMDAwNTU1KT4+CmVuZG9iagozOTQgMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvTm9uU3RydWN0Ci9QIDM5MyAwIFIKL0sgWzM5NSAwIFIgMzk2IDAgUl0KL0lEIChub2RlMDAwMDA1NTIpPj4KZW5kb2JqCjM5MyAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9Ob25TdHJ1Y3QKL1AgMzg0IDAgUgovSyBbMzk0IDAgUl0KL0lEIChub2RlMDAwMDA1NTEpPj4KZW5kb2JqCjM5OSAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9Ob25TdHJ1Y3QKL1AgMzk4IDAgUgovSyBbPDwvVHlwZSAvTUNSCi9QZyAxNyAwIFIKL01DSUQgMjA+Pl0KL0lEIChub2RlMDAwMDA1NTkpPj4KZW5kb2JqCjQwMCAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9Ob25TdHJ1Y3QKL1AgMzk4IDAgUgovSyBbPDwvVHlwZSAvTUNSCi9QZyAxNyAwIFIKL01DSUQgMjE+Pl0KL0lEIChub2RlMDAwMDA1NjApPj4KZW5kb2JqCjM5OCAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9Ob25TdHJ1Y3QKL1AgMzk3IDAgUgovSyBbMzk5IDAgUiA0MDAgMCBSXQovSUQgKG5vZGUwMDAwMDU1Nyk+PgplbmRvYmoKMzk3IDAgb2JqCjw8L1R5cGUgL1N0cnVjdEVsZW0KL1MgL05vblN0cnVjdAovUCAzODQgMCBSCi9LIFszOTggMCBSXQovSUQgKG5vZGUwMDAwMDU1Nik+PgplbmRvYmoKNDAzIDAgb2JqCjw8L1R5cGUgL1N0cnVjdEVsZW0KL1MgL05vblN0cnVjdAovUCA0MDIgMCBSCi9LIFs8PC9UeXBlIC9NQ1IKL1BnIDE3IDAgUgovTUNJRCAyMj4+XQovSUQgKG5vZGUwMDAwMDU2NCk+PgplbmRvYmoKNDA0IDAgb2JqCjw8L1R5cGUgL1N0cnVjdEVsZW0KL1MgL05vblN0cnVjdAovUCA0MDIgMCBSCi9LIFs8PC9UeXBlIC9NQ1IKL1BnIDE3IDAgUgovTUNJRCAyMz4+XQovSUQgKG5vZGUwMDAwMDU2NSk+PgplbmRvYmoKNDAyIDAgb2JqCjw8L1R5cGUgL1N0cnVjdEVsZW0KL1MgL05vblN0cnVjdAovUCA0MDEgMCBSCi9LIFs0MDMgMCBSIDQwNCAwIFJdCi9JRCAobm9kZTAwMDAwNTYyKT4+CmVuZG9iago0MDEgMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvTm9uU3RydWN0Ci9QIDM4NCAwIFIKL0sgWzQwMiAwIFJdCi9JRCAobm9kZTAwMDAwNTYxKT4+CmVuZG9iagozODQgMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvTm9uU3RydWN0Ci9QIDM4MyAwIFIKL0sgWzM4NSAwIFIgMzg5IDAgUiAzOTMgMCBSIDM5NyAwIFIgNDAxIDAgUl0KL0lEIChub2RlMDAwMDA1MzkpPj4KZW5kb2JqCjM4MyAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9Ob25TdHJ1Y3QKL1AgMzYwIDAgUgovSyBbMzg0IDAgUl0KL0lEIChub2RlMDAwMDA1MzgpPj4KZW5kb2JqCjQwOSAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9Ob25TdHJ1Y3QKL1AgNDA4IDAgUgovSyBbPDwvVHlwZSAvTUNSCi9QZyAxNyAwIFIKL01DSUQgMjQ+Pl0KL0lEIChub2RlMDAwMDA1NzQpPj4KZW5kb2JqCjQxMCAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9Ob25TdHJ1Y3QKL1AgNDA4IDAgUgovSyBbPDwvVHlwZSAvTUNSCi9QZyAxNyAwIFIKL01DSUQgMjU+Pl0KL0lEIChub2RlMDAwMDA1NzUpPj4KZW5kb2JqCjQwOCAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9Ob25TdHJ1Y3QKL1AgNDA3IDAgUgovSyBbNDA5IDAgUiA0MTAgMCBSXQovSUQgKG5vZGUwMDAwMDU3Mik+PgplbmRvYmoKNDA3IDAgb2JqCjw8L1R5cGUgL1N0cnVjdEVsZW0KL1MgL05vblN0cnVjdAovUCA0MDYgMCBSCi9LIFs0MDggMCBSXQovSUQgKG5vZGUwMDAwMDU3MSk+PgplbmRvYmoKNDEzIDAgb2JqCjw8L1R5cGUgL1N0cnVjdEVsZW0KL1MgL05vblN0cnVjdAovUCA0MTIgMCBSCi9LIFs8PC9UeXBlIC9NQ1IKL1BnIDE3IDAgUgovTUNJRCAyNj4+XQovSUQgKG5vZGUwMDAwMDU3OSk+PgplbmRvYmoKNDE0IDAgb2JqCjw8L1R5cGUgL1N0cnVjdEVsZW0KL1MgL05vblN0cnVjdAovUCA0MTIgMCBSCi9LIFs8PC9UeXBlIC9NQ1IKL1BnIDE3IDAgUgovTUNJRCAyNz4+XQovSUQgKG5vZGUwMDAwMDU4MCk+PgplbmRvYmoKNDEyIDAgb2JqCjw8L1R5cGUgL1N0cnVjdEVsZW0KL1MgL05vblN0cnVjdAovUCA0MTEgMCBSCi9LIFs0MTMgMCBSIDQxNCAwIFJdCi9JRCAobm9kZTAwMDAwNTc3KT4+CmVuZG9iago0MTEgMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvTm9uU3RydWN0Ci9QIDQwNiAwIFIKL0sgWzQxMiAwIFJdCi9JRCAobm9kZTAwMDAwNTc2KT4+CmVuZG9iago0MTcgMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvTm9uU3RydWN0Ci9QIDQxNiAwIFIKL0sgWzw8L1R5cGUgL01DUgovUGcgMTcgMCBSCi9NQ0lEIDI4Pj5dCi9JRCAobm9kZTAwMDAwNTg0KT4+CmVuZG9iago0MTYgMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvTm9uU3RydWN0Ci9QIDQxNSAwIFIKL0sgWzQxNyAwIFJdCi9JRCAobm9kZTAwMDAwNTgyKT4+CmVuZG9iago0MTUgMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvTm9uU3RydWN0Ci9QIDQwNiAwIFIKL0sgWzQxNiAwIFJdCi9JRCAobm9kZTAwMDAwNTgxKT4+CmVuZG9iago0MjAgMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvTm9uU3RydWN0Ci9QIDQxOSAwIFIKL0sgWzw8L1R5cGUgL01DUgovUGcgMTcgMCBSCi9NQ0lEIDI5Pj5dCi9JRCAobm9kZTAwMDAwNTg5KT4+CmVuZG9iago0MjEgMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvTm9uU3RydWN0Ci9QIDQxOSAwIFIKL0sgWzw8L1R5cGUgL01DUgovUGcgMTcgMCBSCi9NQ0lEIDMwPj5dCi9JRCAobm9kZTAwMDAwNTkwKT4+CmVuZG9iago0MTkgMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvTm9uU3RydWN0Ci9QIDQxOCAwIFIKL0sgWzQyMCAwIFIgNDIxIDAgUl0KL0lEIChub2RlMDAwMDA1ODcpPj4KZW5kb2JqCjQxOCAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9Ob25TdHJ1Y3QKL1AgNDA2IDAgUgovSyBbNDE5IDAgUl0KL0lEIChub2RlMDAwMDA1ODYpPj4KZW5kb2JqCjQyNCAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9Ob25TdHJ1Y3QKL1AgNDIzIDAgUgovSyBbPDwvVHlwZSAvTUNSCi9QZyAxNyAwIFIKL01DSUQgMzE+Pl0KL0lEIChub2RlMDAwMDA1OTQpPj4KZW5kb2JqCjQyMyAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9Ob25TdHJ1Y3QKL1AgNDIyIDAgUgovSyBbNDI0IDAgUl0KL0lEIChub2RlMDAwMDA1OTIpPj4KZW5kb2JqCjQyMiAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9Ob25TdHJ1Y3QKL1AgNDA2IDAgUgovSyBbNDIzIDAgUl0KL0lEIChub2RlMDAwMDA1OTEpPj4KZW5kb2JqCjQwNiAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9Ob25TdHJ1Y3QKL1AgNDA1IDAgUgovSyBbNDA3IDAgUiA0MTEgMCBSIDQxNSAwIFIgNDE4IDAgUiA0MjIgMCBSXQovSUQgKG5vZGUwMDAwMDU2OSk+PgplbmRvYmoKNDA1IDAgb2JqCjw8L1R5cGUgL1N0cnVjdEVsZW0KL1MgL05vblN0cnVjdAovUCAzNjAgMCBSCi9LIFs0MDYgMCBSXQovSUQgKG5vZGUwMDAwMDU2OCk+PgplbmRvYmoKMzYwIDAgb2JqCjw8L1R5cGUgL1N0cnVjdEVsZW0KL1MgL05vblN0cnVjdAovUCAzNTAgMCBSCi9LIFszNjEgMCBSIDM4MyAwIFIgNDA1IDAgUl0KL0lEIChub2RlMDAwMDA1MDkpPj4KZW5kb2JqCjQyNyAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9Ob25TdHJ1Y3QKL1AgNDI2IDAgUgovSyBbPDwvVHlwZSAvTUNSCi9QZyAxNyAwIFIKL01DSUQgMzI+Pl0KL0lEIChub2RlMDAwMDA2MDUpPj4KZW5kb2JqCjQyNiAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9Ob25TdHJ1Y3QKL1AgNDI1IDAgUgovSyBbNDI3IDAgUl0KL0lEIChub2RlMDAwMDA2MDQpPj4KZW5kb2JqCjQyNSAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9Ob25TdHJ1Y3QKL1AgMzUwIDAgUgovSyBbNDI2IDAgUl0KL0lEIChub2RlMDAwMDA2MDMpPj4KZW5kb2JqCjQzMSAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9Ob25TdHJ1Y3QKL1AgNDMwIDAgUgovSyBbPDwvVHlwZSAvTUNSCi9QZyAxNyAwIFIKL01DSUQgMzM+Pl0KL0lEIChub2RlMDAwMDA2MTMpPj4KZW5kb2JqCjQzMiAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9Ob25TdHJ1Y3QKL1AgNDMwIDAgUgovSyBbPDwvVHlwZSAvTUNSCi9QZyAxNyAwIFIKL01DSUQgMzQ+Pl0KL0lEIChub2RlMDAwMDA2MTUpPj4KZW5kb2JqCjQzMCAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9EaXYKL1AgNDI5IDAgUgovSyBbNDMxIDAgUiA0MzIgMCBSXQovSUQgKG5vZGUwMDAwMDYxMik+PgplbmRvYmoKNDM0IDAgb2JqCjw8L1R5cGUgL1N0cnVjdEVsZW0KL1MgL05vblN0cnVjdAovUCA0MzMgMCBSCi9LIFs8PC9UeXBlIC9NQ1IKL1BnIDE3IDAgUgovTUNJRCAzNT4+XQovSUQgKG5vZGUwMDAwMDYxNyk+PgplbmRvYmoKNDM1IDAgb2JqCjw8L1R5cGUgL1N0cnVjdEVsZW0KL1MgL05vblN0cnVjdAovUCA0MzMgMCBSCi9LIFs8PC9UeXBlIC9NQ1IKL1BnIDE3IDAgUgovTUNJRCAzNj4+XQovSUQgKG5vZGUwMDAwMDYxOSk+PgplbmRvYmoKNDMzIDAgb2JqCjw8L1R5cGUgL1N0cnVjdEVsZW0KL1MgL0RpdgovUCA0MjkgMCBSCi9LIFs0MzQgMCBSIDQzNSAwIFJdCi9JRCAobm9kZTAwMDAwNjE2KT4+CmVuZG9iago0MzcgMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvTm9uU3RydWN0Ci9QIDQzNiAwIFIKL0sgWzw8L1R5cGUgL01DUgovUGcgMTcgMCBSCi9NQ0lEIDM3Pj5dCi9JRCAobm9kZTAwMDAwNjIxKT4+CmVuZG9iago0MzggMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvTm9uU3RydWN0Ci9QIDQzNiAwIFIKL0sgWzw8L1R5cGUgL01DUgovUGcgMTcgMCBSCi9NQ0lEIDM4Pj5dCi9JRCAobm9kZTAwMDAwNjIzKT4+CmVuZG9iago0MzYgMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvRGl2Ci9QIDQyOSAwIFIKL0sgWzQzNyAwIFIgNDM4IDAgUl0KL0lEIChub2RlMDAwMDA2MjApPj4KZW5kb2JqCjQ0MCAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9Ob25TdHJ1Y3QKL1AgNDM5IDAgUgovSyBbPDwvVHlwZSAvTUNSCi9QZyAxNyAwIFIKL01DSUQgMzk+Pl0KL0lEIChub2RlMDAwMDA2MjUpPj4KZW5kb2JqCjQ0MSAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9Ob25TdHJ1Y3QKL1AgNDM5IDAgUgovSyBbPDwvVHlwZSAvTUNSCi9QZyAxNyAwIFIKL01DSUQgNDA+Pl0KL0lEIChub2RlMDAwMDA2MjcpPj4KZW5kb2JqCjQzOSAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9EaXYKL1AgNDI5IDAgUgovSyBbNDQwIDAgUiA0NDEgMCBSXQovSUQgKG5vZGUwMDAwMDYyNCk+PgplbmRvYmoKNDQzIDAgb2JqCjw8L1R5cGUgL1N0cnVjdEVsZW0KL1MgL05vblN0cnVjdAovUCA0NDIgMCBSCi9LIFs8PC9UeXBlIC9NQ1IKL1BnIDE3IDAgUgovTUNJRCA0MT4+XQovSUQgKG5vZGUwMDAwMDYyOSk+PgplbmRvYmoKNDQ0IDAgb2JqCjw8L1R5cGUgL1N0cnVjdEVsZW0KL1MgL05vblN0cnVjdAovUCA0NDIgMCBSCi9LIFs8PC9UeXBlIC9NQ1IKL1BnIDE3IDAgUgovTUNJRCA0Mj4+XQovSUQgKG5vZGUwMDAwMDYzMSk+PgplbmRvYmoKNDQyIDAgb2JqCjw8L1R5cGUgL1N0cnVjdEVsZW0KL1MgL0RpdgovUCA0MjkgMCBSCi9LIFs0NDMgMCBSIDQ0NCAwIFJdCi9JRCAobm9kZTAwMDAwNjI4KT4+CmVuZG9iago0NDYgMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvTm9uU3RydWN0Ci9QIDQ0NSAwIFIKL0sgWzw8L1R5cGUgL01DUgovUGcgMTcgMCBSCi9NQ0lEIDQzPj5dCi9JRCAobm9kZTAwMDAwNjMzKT4+CmVuZG9iago0NDcgMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvTm9uU3RydWN0Ci9QIDQ0NSAwIFIKL0sgWzw8L1R5cGUgL01DUgovUGcgMTcgMCBSCi9NQ0lEIDQ0Pj5dCi9JRCAobm9kZTAwMDAwNjM1KT4+CmVuZG9iago0NDUgMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvRGl2Ci9QIDQyOSAwIFIKL0sgWzQ0NiAwIFIgNDQ3IDAgUl0KL0lEIChub2RlMDAwMDA2MzIpPj4KZW5kb2JqCjQ0OSAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9Ob25TdHJ1Y3QKL1AgNDQ4IDAgUgovSyBbPDwvVHlwZSAvTUNSCi9QZyAxNyAwIFIKL01DSUQgNDU+Pl0KL0lEIChub2RlMDAwMDA2MzcpPj4KZW5kb2JqCjQ1MCAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9Ob25TdHJ1Y3QKL1AgNDQ4IDAgUgovSyBbPDwvVHlwZSAvTUNSCi9QZyAxNyAwIFIKL01DSUQgNDY+Pl0KL0lEIChub2RlMDAwMDA2MzkpPj4KZW5kb2JqCjQ0OCAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9EaXYKL1AgNDI5IDAgUgovSyBbNDQ5IDAgUiA0NTAgMCBSXQovSUQgKG5vZGUwMDAwMDYzNik+PgplbmRvYmoKNDUyIDAgb2JqCjw8L1R5cGUgL1N0cnVjdEVsZW0KL1MgL05vblN0cnVjdAovUCA0NTEgMCBSCi9LIFs8PC9UeXBlIC9NQ1IKL1BnIDE3IDAgUgovTUNJRCA0Nz4+XQovSUQgKG5vZGUwMDAwMDY0MSk+PgplbmRvYmoKNDUzIDAgb2JqCjw8L1R5cGUgL1N0cnVjdEVsZW0KL1MgL05vblN0cnVjdAovUCA0NTEgMCBSCi9LIFs8PC9UeXBlIC9NQ1IKL1BnIDE3IDAgUgovTUNJRCA0OD4+XQovSUQgKG5vZGUwMDAwMDY0Myk+PgplbmRvYmoKNDUxIDAgb2JqCjw8L1R5cGUgL1N0cnVjdEVsZW0KL1MgL0RpdgovUCA0MjkgMCBSCi9LIFs0NTIgMCBSIDQ1MyAwIFJdCi9JRCAobm9kZTAwMDAwNjQwKT4+CmVuZG9iago0NTUgMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvTm9uU3RydWN0Ci9QIDQ1NCAwIFIKL0sgWzw8L1R5cGUgL01DUgovUGcgMTcgMCBSCi9NQ0lEIDQ5Pj5dCi9JRCAobm9kZTAwMDAwNjQ1KT4+CmVuZG9iago0NTYgMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvTm9uU3RydWN0Ci9QIDQ1NCAwIFIKL0sgWzw8L1R5cGUgL01DUgovUGcgMTcgMCBSCi9NQ0lEIDUwPj5dCi9JRCAobm9kZTAwMDAwNjQ3KT4+CmVuZG9iago0NTQgMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvRGl2Ci9QIDQyOSAwIFIKL0sgWzQ1NSAwIFIgNDU2IDAgUl0KL0lEIChub2RlMDAwMDA2NDQpPj4KZW5kb2JqCjQ1OCAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9Ob25TdHJ1Y3QKL1AgNDU3IDAgUgovSyBbPDwvVHlwZSAvTUNSCi9QZyAxNyAwIFIKL01DSUQgNTE+Pl0KL0lEIChub2RlMDAwMDA2NDkpPj4KZW5kb2JqCjQ1OSAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9Ob25TdHJ1Y3QKL1AgNDU3IDAgUgovSyBbPDwvVHlwZSAvTUNSCi9QZyAxNyAwIFIKL01DSUQgNTI+Pl0KL0lEIChub2RlMDAwMDA2NTEpPj4KZW5kb2JqCjQ1NyAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9EaXYKL1AgNDI5IDAgUgovSyBbNDU4IDAgUiA0NTkgMCBSXQovSUQgKG5vZGUwMDAwMDY0OCk+PgplbmRvYmoKNDYxIDAgb2JqCjw8L1R5cGUgL1N0cnVjdEVsZW0KL1MgL05vblN0cnVjdAovUCA0NjAgMCBSCi9LIFs8PC9UeXBlIC9NQ1IKL1BnIDE3IDAgUgovTUNJRCA1Mz4+XQovSUQgKG5vZGUwMDAwMDY1Myk+PgplbmRvYmoKNDYyIDAgb2JqCjw8L1R5cGUgL1N0cnVjdEVsZW0KL1MgL05vblN0cnVjdAovUCA0NjAgMCBSCi9LIFs8PC9UeXBlIC9NQ1IKL1BnIDE3IDAgUgovTUNJRCA1ND4+XQovSUQgKG5vZGUwMDAwMDY1NSk+PgplbmRvYmoKNDYwIDAgb2JqCjw8L1R5cGUgL1N0cnVjdEVsZW0KL1MgL0RpdgovUCA0MjkgMCBSCi9LIFs0NjEgMCBSIDQ2MiAwIFJdCi9JRCAobm9kZTAwMDAwNjUyKT4+CmVuZG9iago0NjQgMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvTm9uU3RydWN0Ci9QIDQ2MyAwIFIKL0sgWzw8L1R5cGUgL01DUgovUGcgMTcgMCBSCi9NQ0lEIDU1Pj5dCi9JRCAobm9kZTAwMDAwNjU3KT4+CmVuZG9iago0NjUgMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvTm9uU3RydWN0Ci9QIDQ2MyAwIFIKL0sgWzw8L1R5cGUgL01DUgovUGcgMTcgMCBSCi9NQ0lEIDU2Pj5dCi9JRCAobm9kZTAwMDAwNjU5KT4+CmVuZG9iago0NjMgMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvRGl2Ci9QIDQyOSAwIFIKL0sgWzQ2NCAwIFIgNDY1IDAgUl0KL0lEIChub2RlMDAwMDA2NTYpPj4KZW5kb2JqCjQ2NyAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9Ob25TdHJ1Y3QKL1AgNDY2IDAgUgovSyBbPDwvVHlwZSAvTUNSCi9QZyAxNyAwIFIKL01DSUQgNTc+Pl0KL0lEIChub2RlMDAwMDA2NjEpPj4KZW5kb2JqCjQ2OCAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9Ob25TdHJ1Y3QKL1AgNDY2IDAgUgovSyBbPDwvVHlwZSAvTUNSCi9QZyAxNyAwIFIKL01DSUQgNTg+Pl0KL0lEIChub2RlMDAwMDA2NjMpPj4KZW5kb2JqCjQ2NiAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9EaXYKL1AgNDI5IDAgUgovSyBbNDY3IDAgUiA0NjggMCBSXQovSUQgKG5vZGUwMDAwMDY2MCk+PgplbmRvYmoKNDcwIDAgb2JqCjw8L1R5cGUgL1N0cnVjdEVsZW0KL1MgL05vblN0cnVjdAovUCA0NjkgMCBSCi9LIFs8PC9UeXBlIC9NQ1IKL1BnIDE3IDAgUgovTUNJRCA1OT4+XQovSUQgKG5vZGUwMDAwMDY2NSk+PgplbmRvYmoKNDcxIDAgb2JqCjw8L1R5cGUgL1N0cnVjdEVsZW0KL1MgL05vblN0cnVjdAovUCA0NjkgMCBSCi9LIFs8PC9UeXBlIC9NQ1IKL1BnIDE3IDAgUgovTUNJRCA2MD4+XQovSUQgKG5vZGUwMDAwMDY2Nyk+PgplbmRvYmoKNDY5IDAgb2JqCjw8L1R5cGUgL1N0cnVjdEVsZW0KL1MgL0RpdgovUCA0MjkgMCBSCi9LIFs0NzAgMCBSIDQ3MSAwIFJdCi9JRCAobm9kZTAwMDAwNjY0KT4+CmVuZG9iago0NzMgMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvTm9uU3RydWN0Ci9QIDQ3MiAwIFIKL0sgWzw8L1R5cGUgL01DUgovUGcgMTcgMCBSCi9NQ0lEIDYxPj5dCi9JRCAobm9kZTAwMDAwNjY5KT4+CmVuZG9iago0NzQgMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvTm9uU3RydWN0Ci9QIDQ3MiAwIFIKL0sgWzw8L1R5cGUgL01DUgovUGcgMTcgMCBSCi9NQ0lEIDYyPj5dCi9JRCAobm9kZTAwMDAwNjcxKT4+CmVuZG9iago0NzIgMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvRGl2Ci9QIDQyOSAwIFIKL0sgWzQ3MyAwIFIgNDc0IDAgUl0KL0lEIChub2RlMDAwMDA2NjgpPj4KZW5kb2JqCjQ3NiAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9Ob25TdHJ1Y3QKL1AgNDc1IDAgUgovSyBbPDwvVHlwZSAvTUNSCi9QZyAxNyAwIFIKL01DSUQgNjM+Pl0KL0lEIChub2RlMDAwMDA2NzMpPj4KZW5kb2JqCjQ3NyAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9Ob25TdHJ1Y3QKL1AgNDc1IDAgUgovSyBbPDwvVHlwZSAvTUNSCi9QZyAxNyAwIFIKL01DSUQgNjQ+Pl0KL0lEIChub2RlMDAwMDA2NzUpPj4KZW5kb2JqCjQ3NSAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9EaXYKL1AgNDI5IDAgUgovSyBbNDc2IDAgUiA0NzcgMCBSXQovSUQgKG5vZGUwMDAwMDY3Mik+PgplbmRvYmoKNDc5IDAgb2JqCjw8L1R5cGUgL1N0cnVjdEVsZW0KL1MgL05vblN0cnVjdAovUCA0NzggMCBSCi9LIFs8PC9UeXBlIC9NQ1IKL1BnIDE3IDAgUgovTUNJRCA2NT4+XQovSUQgKG5vZGUwMDAwMDY3Nyk+PgplbmRvYmoKNDgwIDAgb2JqCjw8L1R5cGUgL1N0cnVjdEVsZW0KL1MgL05vblN0cnVjdAovUCA0NzggMCBSCi9LIFs8PC9UeXBlIC9NQ1IKL1BnIDE3IDAgUgovTUNJRCA2Nj4+XQovSUQgKG5vZGUwMDAwMDY3OSk+PgplbmRvYmoKNDc4IDAgb2JqCjw8L1R5cGUgL1N0cnVjdEVsZW0KL1MgL0RpdgovUCA0MjkgMCBSCi9LIFs0NzkgMCBSIDQ4MCAwIFJdCi9JRCAobm9kZTAwMDAwNjc2KT4+CmVuZG9iago0ODIgMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvTm9uU3RydWN0Ci9QIDQ4MSAwIFIKL0sgWzw8L1R5cGUgL01DUgovUGcgMTcgMCBSCi9NQ0lEIDY3Pj5dCi9JRCAobm9kZTAwMDAwNjgxKT4+CmVuZG9iago0ODMgMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvTm9uU3RydWN0Ci9QIDQ4MSAwIFIKL0sgWzw8L1R5cGUgL01DUgovUGcgMTcgMCBSCi9NQ0lEIDY4Pj5dCi9JRCAobm9kZTAwMDAwNjgzKT4+CmVuZG9iago0ODEgMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvRGl2Ci9QIDQyOSAwIFIKL0sgWzQ4MiAwIFIgNDgzIDAgUl0KL0lEIChub2RlMDAwMDA2ODApPj4KZW5kb2JqCjQ4NSAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9Ob25TdHJ1Y3QKL1AgNDg0IDAgUgovSyBbPDwvVHlwZSAvTUNSCi9QZyAxNyAwIFIKL01DSUQgNjk+Pl0KL0lEIChub2RlMDAwMDA2ODUpPj4KZW5kb2JqCjQ4NiAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9Ob25TdHJ1Y3QKL1AgNDg0IDAgUgovSyBbPDwvVHlwZSAvTUNSCi9QZyAxNyAwIFIKL01DSUQgNzA+Pl0KL0lEIChub2RlMDAwMDA2ODcpPj4KZW5kb2JqCjQ4NCAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9EaXYKL1AgNDI5IDAgUgovSyBbNDg1IDAgUiA0ODYgMCBSXQovSUQgKG5vZGUwMDAwMDY4NCk+PgplbmRvYmoKNDg4IDAgb2JqCjw8L1R5cGUgL1N0cnVjdEVsZW0KL1MgL05vblN0cnVjdAovUCA0ODcgMCBSCi9LIFs8PC9UeXBlIC9NQ1IKL1BnIDE3IDAgUgovTUNJRCA3MT4+XQovSUQgKG5vZGUwMDAwMDY4OSk+PgplbmRvYmoKNDg5IDAgb2JqCjw8L1R5cGUgL1N0cnVjdEVsZW0KL1MgL05vblN0cnVjdAovUCA0ODcgMCBSCi9LIFs8PC9UeXBlIC9NQ1IKL1BnIDE3IDAgUgovTUNJRCA3Mj4+XQovSUQgKG5vZGUwMDAwMDY5MSk+PgplbmRvYmoKNDg3IDAgb2JqCjw8L1R5cGUgL1N0cnVjdEVsZW0KL1MgL0RpdgovUCA0MjkgMCBSCi9LIFs0ODggMCBSIDQ4OSAwIFJdCi9JRCAobm9kZTAwMDAwNjg4KT4+CmVuZG9iago0OTEgMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvTm9uU3RydWN0Ci9QIDQ5MCAwIFIKL0sgWzw8L1R5cGUgL01DUgovUGcgMTcgMCBSCi9NQ0lEIDczPj5dCi9JRCAobm9kZTAwMDAwNjkzKT4+CmVuZG9iago0OTIgMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvTm9uU3RydWN0Ci9QIDQ5MCAwIFIKL0sgWzw8L1R5cGUgL01DUgovUGcgMTcgMCBSCi9NQ0lEIDc0Pj5dCi9JRCAobm9kZTAwMDAwNjk1KT4+CmVuZG9iago0OTAgMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvRGl2Ci9QIDQyOSAwIFIKL0sgWzQ5MSAwIFIgNDkyIDAgUl0KL0lEIChub2RlMDAwMDA2OTIpPj4KZW5kb2JqCjQyOSAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9Ob25TdHJ1Y3QKL1AgNDI4IDAgUgovSyBbNDMwIDAgUiA0MzMgMCBSIDQzNiAwIFIgNDM5IDAgUiA0NDIgMCBSIDQ0NSAwIFIgNDQ4IDAgUiA0NTEgMCBSIDQ1NCAwIFIgNDU3IDAgUiA0NjAgMCBSIDQ2MyAwIFIgNDY2IDAgUiA0NjkgMCBSIDQ3MiAwIFIgNDc1IDAgUiA0NzggMCBSIDQ4MSAwIFIgNDg0IDAgUiA0ODcgMCBSIDQ5MCAwIFJdCi9JRCAobm9kZTAwMDAwNjEwKT4+CmVuZG9iago0MjggMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvTm9uU3RydWN0Ci9QIDM1MCAwIFIKL0sgWzQyOSAwIFJdCi9JRCAobm9kZTAwMDAwNjA5KT4+CmVuZG9iagozNTAgMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvTm9uU3RydWN0Ci9QIDM0OSAwIFIKL0sgWzM1MSAwIFIgMzYwIDAgUiA0MjUgMCBSIDQyOCAwIFJdCi9JRCAobm9kZTAwMDAwNDk2KT4+CmVuZG9iagozNDkgMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvVEQKL1AgMzQ4IDAgUgovSyBbMzUwIDAgUl0KL0EgWzw8L08gL1RhYmxlCi9IZWFkZXJzIFtdPj4gPDwvTyAvVGFibGUKL1Jvd1NwYW4gMT4+IDw8L08gL1RhYmxlCi9Db2xTcGFuIDE+Pl0KL0lEIChub2RlMDAwMDA0OTUpPj4KZW5kb2JqCjM0OCAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9UUgovUCAyMiAwIFIKL0sgWzM0OSAwIFJdCi9JRCAobm9kZTAwMDAwNDk0KT4+CmVuZG9iago0OTggMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvTm9uU3RydWN0Ci9QIDQ5NyAwIFIKL0sgWzw8L1R5cGUgL01DUgovUGcgMTcgMCBSCi9NQ0lEIDc2Pj5dCi9JRCAobm9kZTAwMDAwNzA0KT4+CmVuZG9iago0OTcgMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvTm9uU3RydWN0Ci9QIDQ5NiAwIFIKL0sgWzQ5OCAwIFJdCi9JRCAobm9kZTAwMDAwNzAzKT4+CmVuZG9iago0OTYgMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvTm9uU3RydWN0Ci9QIDQ5NSAwIFIKL0sgWzQ5NyAwIFJdCi9JRCAobm9kZTAwMDAwNzAyKT4+CmVuZG9iago1MDEgMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvTm9uU3RydWN0Ci9QIDUwMCAwIFIKL0sgWzw8L1R5cGUgL01DUgovUGcgMTcgMCBSCi9NQ0lEIDc3Pj5dCi9JRCAobm9kZTAwMDAwNzA5KT4+CmVuZG9iago1MDAgMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvTm9uU3RydWN0Ci9QIDQ5OSAwIFIKL0sgWzUwMSAwIFJdCi9JRCAobm9kZTAwMDAwNzA4KT4+CmVuZG9iago1MDMgMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvTm9uU3RydWN0Ci9QIDUwMiAwIFIKL0sgWzw8L1R5cGUgL01DUgovUGcgMTcgMCBSCi9NQ0lEIDc4Pj5dCi9JRCAobm9kZTAwMDAwNzExKT4+CmVuZG9iago1MDIgMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvTm9uU3RydWN0Ci9QIDQ5OSAwIFIKL0sgWzUwMyAwIFJdCi9JRCAobm9kZTAwMDAwNzEwKT4+CmVuZG9iago1MDUgMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvTm9uU3RydWN0Ci9QIDUwNCAwIFIKL0sgWzw8L1R5cGUgL01DUgovUGcgMTcgMCBSCi9NQ0lEIDc5Pj5dCi9JRCAobm9kZTAwMDAwNzEzKT4+CmVuZG9iago1MDQgMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvTm9uU3RydWN0Ci9QIDQ5OSAwIFIKL0sgWzUwNSAwIFJdCi9JRCAobm9kZTAwMDAwNzEyKT4+CmVuZG9iago1MDcgMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvTm9uU3RydWN0Ci9QIDUwNiAwIFIKL0sgWzw8L1R5cGUgL01DUgovUGcgMTcgMCBSCi9NQ0lEIDgwPj5dCi9JRCAobm9kZTAwMDAwNzE1KT4+CmVuZG9iago1MDYgMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvTm9uU3RydWN0Ci9QIDQ5OSAwIFIKL0sgWzUwNyAwIFJdCi9JRCAobm9kZTAwMDAwNzE0KT4+CmVuZG9iago0OTkgMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvTm9uU3RydWN0Ci9QIDQ5NSAwIFIKL0sgWzUwMCAwIFIgNTAyIDAgUiA1MDQgMCBSIDUwNiAwIFJdCi9JRCAobm9kZTAwMDAwNzA3KT4+CmVuZG9iago1MTAgMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvTm9uU3RydWN0Ci9QIDUwOSAwIFIKL0sgWzw8L1R5cGUgL01DUgovUGcgMTcgMCBSCi9NQ0lEIDgxPj5dCi9JRCAobm9kZTAwMDAwNzE4KT4+CmVuZG9iago1MDkgMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvTm9uU3RydWN0Ci9QIDUwOCAwIFIKL0sgWzUxMCAwIFJdCi9JRCAobm9kZTAwMDAwNzE3KT4+CmVuZG9iago1MTIgMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvTm9uU3RydWN0Ci9QIDUxMSAwIFIKL0sgWzw8L1R5cGUgL01DUgovUGcgMTcgMCBSCi9NQ0lEIDgyPj5dCi9JRCAobm9kZTAwMDAwNzIwKT4+CmVuZG9iago1MTEgMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvTm9uU3RydWN0Ci9QIDUwOCAwIFIKL0sgWzUxMiAwIFJdCi9JRCAobm9kZTAwMDAwNzE5KT4+CmVuZG9iago1MTQgMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvTm9uU3RydWN0Ci9QIDUxMyAwIFIKL0sgWzw8L1R5cGUgL01DUgovUGcgMTcgMCBSCi9NQ0lEIDgzPj5dCi9JRCAobm9kZTAwMDAwNzIyKT4+CmVuZG9iago1MTMgMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvTm9uU3RydWN0Ci9QIDUwOCAwIFIKL0sgWzUxNCAwIFJdCi9JRCAobm9kZTAwMDAwNzIxKT4+CmVuZG9iago1MTYgMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvTm9uU3RydWN0Ci9QIDUxNSAwIFIKL0sgWzw8L1R5cGUgL01DUgovUGcgMTcgMCBSCi9NQ0lEIDg0Pj5dCi9JRCAobm9kZTAwMDAwNzI0KT4+CmVuZG9iago1MTUgMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvTm9uU3RydWN0Ci9QIDUwOCAwIFIKL0sgWzUxNiAwIFJdCi9JRCAobm9kZTAwMDAwNzIzKT4+CmVuZG9iago1MDggMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvTm9uU3RydWN0Ci9QIDQ5NSAwIFIKL0sgWzUwOSAwIFIgNTExIDAgUiA1MTMgMCBSIDUxNSAwIFJdCi9JRCAobm9kZTAwMDAwNzE2KT4+CmVuZG9iago1MTkgMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvTm9uU3RydWN0Ci9QIDUxOCAwIFIKL0sgWzw8L1R5cGUgL01DUgovUGcgMTcgMCBSCi9NQ0lEIDg1Pj5dCi9JRCAobm9kZTAwMDAwNzI3KT4+CmVuZG9iago1MTggMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvTm9uU3RydWN0Ci9QIDUxNyAwIFIKL0sgWzUxOSAwIFJdCi9JRCAobm9kZTAwMDAwNzI2KT4+CmVuZG9iago1MjEgMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvTm9uU3RydWN0Ci9QIDUyMCAwIFIKL0sgWzw8L1R5cGUgL01DUgovUGcgMTcgMCBSCi9NQ0lEIDg2Pj5dCi9JRCAobm9kZTAwMDAwNzI5KT4+CmVuZG9iago1MjAgMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvTm9uU3RydWN0Ci9QIDUxNyAwIFIKL0sgWzUyMSAwIFJdCi9JRCAobm9kZTAwMDAwNzI4KT4+CmVuZG9iago1MjMgMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvTm9uU3RydWN0Ci9QIDUyMiAwIFIKL0sgWzw8L1R5cGUgL01DUgovUGcgMTcgMCBSCi9NQ0lEIDg3Pj5dCi9JRCAobm9kZTAwMDAwNzMxKT4+CmVuZG9iago1MjIgMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvTm9uU3RydWN0Ci9QIDUxNyAwIFIKL0sgWzUyMyAwIFJdCi9JRCAobm9kZTAwMDAwNzMwKT4+CmVuZG9iago1MjUgMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvTm9uU3RydWN0Ci9QIDUyNCAwIFIKL0sgWzw8L1R5cGUgL01DUgovUGcgMTcgMCBSCi9NQ0lEIDg4Pj5dCi9JRCAobm9kZTAwMDAwNzMzKT4+CmVuZG9iago1MjQgMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvTm9uU3RydWN0Ci9QIDUxNyAwIFIKL0sgWzUyNSAwIFJdCi9JRCAobm9kZTAwMDAwNzMyKT4+CmVuZG9iago1MTcgMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvTm9uU3RydWN0Ci9QIDQ5NSAwIFIKL0sgWzUxOCAwIFIgNTIwIDAgUiA1MjIgMCBSIDUyNCAwIFJdCi9JRCAobm9kZTAwMDAwNzI1KT4+CmVuZG9iago1MjggMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvTm9uU3RydWN0Ci9QIDUyNyAwIFIKL0sgWzw8L1R5cGUgL01DUgovUGcgMTcgMCBSCi9NQ0lEIDg5Pj5dCi9JRCAobm9kZTAwMDAwNzM2KT4+CmVuZG9iago1MjcgMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvTm9uU3RydWN0Ci9QIDUyNiAwIFIKL0sgWzUyOCAwIFJdCi9JRCAobm9kZTAwMDAwNzM1KT4+CmVuZG9iago1MzAgMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvTm9uU3RydWN0Ci9QIDUyOSAwIFIKL0sgWzw8L1R5cGUgL01DUgovUGcgMTcgMCBSCi9NQ0lEIDkwPj5dCi9JRCAobm9kZTAwMDAwNzM4KT4+CmVuZG9iago1MjkgMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvTm9uU3RydWN0Ci9QIDUyNiAwIFIKL0sgWzUzMCAwIFJdCi9JRCAobm9kZTAwMDAwNzM3KT4+CmVuZG9iago1MzIgMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvTm9uU3RydWN0Ci9QIDUzMSAwIFIKL0sgWzw8L1R5cGUgL01DUgovUGcgMTcgMCBSCi9NQ0lEIDkxPj5dCi9JRCAobm9kZTAwMDAwNzQwKT4+CmVuZG9iago1MzEgMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvTm9uU3RydWN0Ci9QIDUyNiAwIFIKL0sgWzUzMiAwIFJdCi9JRCAobm9kZTAwMDAwNzM5KT4+CmVuZG9iago1MzQgMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvTm9uU3RydWN0Ci9QIDUzMyAwIFIKL0sgWzw8L1R5cGUgL01DUgovUGcgMTcgMCBSCi9NQ0lEIDkyPj5dCi9JRCAobm9kZTAwMDAwNzQyKT4+CmVuZG9iago1MzMgMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvTm9uU3RydWN0Ci9QIDUyNiAwIFIKL0sgWzUzNCAwIFJdCi9JRCAobm9kZTAwMDAwNzQxKT4+CmVuZG9iago1MjYgMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvTm9uU3RydWN0Ci9QIDQ5NSAwIFIKL0sgWzUyNyAwIFIgNTI5IDAgUiA1MzEgMCBSIDUzMyAwIFJdCi9JRCAobm9kZTAwMDAwNzM0KT4+CmVuZG9iago0OTUgMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvTm9uU3RydWN0Ci9QIDQ5NCAwIFIKL0sgWzQ5NiAwIFIgNDk5IDAgUiA1MDggMCBSIDUxNyAwIFIgNTI2IDAgUl0KL0lEIChub2RlMDAwMDA3MDApPj4KZW5kb2JqCjQ5NCAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9URAovUCA0OTMgMCBSCi9LIFs0OTUgMCBSXQovQSBbPDwvTyAvVGFibGUKL0hlYWRlcnMgW10+PiA8PC9PIC9UYWJsZQovUm93U3BhbiAxPj4gPDwvTyAvVGFibGUKL0NvbFNwYW4gMT4+XQovSUQgKG5vZGUwMDAwMDY5OSk+PgplbmRvYmoKNDkzIDAgb2JqCjw8L1R5cGUgL1N0cnVjdEVsZW0KL1MgL1RSCi9QIDIyIDAgUgovSyBbNDk0IDAgUl0KL0lEIChub2RlMDAwMDA2OTgpPj4KZW5kb2JqCjU0MCAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9Ob25TdHJ1Y3QKL1AgNTM5IDAgUgovSyBbPDwvVHlwZSAvTUNSCi9QZyAxNyAwIFIKL01DSUQgNzU+Pl0KL0lEIChub2RlMDAwMDA3NTMpPj4KZW5kb2JqCjUzOSAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9Ob25TdHJ1Y3QKL1AgNTM4IDAgUgovSyBbNTQwIDAgUl0KL0lEIChub2RlMDAwMDA3NTIpPj4KZW5kb2JqCjUzOCAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9Ob25TdHJ1Y3QKL1AgNTM3IDAgUgovSyBbNTM5IDAgUl0KL0lEIChub2RlMDAwMDA3NTEpPj4KZW5kb2JqCjUzNyAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9Ob25TdHJ1Y3QKL1AgNTM2IDAgUgovSyBbNTM4IDAgUl0KL0lEIChub2RlMDAwMDA3NDkpPj4KZW5kb2JqCjUzNiAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9URAovUCA1MzUgMCBSCi9LIFs1MzcgMCBSXQovQSBbPDwvTyAvVGFibGUKL0hlYWRlcnMgW10+PiA8PC9PIC9UYWJsZQovUm93U3BhbiAxPj4gPDwvTyAvVGFibGUKL0NvbFNwYW4gMT4+XQovSUQgKG5vZGUwMDAwMDc0OCk+PgplbmRvYmoKNTM1IDAgb2JqCjw8L1R5cGUgL1N0cnVjdEVsZW0KL1MgL1RSCi9QIDIyIDAgUgovSyBbNTM2IDAgUl0KL0lEIChub2RlMDAwMDA3NDcpPj4KZW5kb2JqCjU0OCAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9Ob25TdHJ1Y3QKL1AgNTQ3IDAgUgovSyBbPDwvVHlwZSAvTUNSCi9QZyAxNyAwIFIKL01DSUQgOTM+PiA8PC9UeXBlIC9NQ1IKL1BnIDE3IDAgUgovTUNJRCA5ND4+IDw8L1R5cGUgL01DUgovUGcgMTcgMCBSCi9NQ0lEIDk1Pj4gPDwvVHlwZSAvTUNSCi9QZyAxNyAwIFIKL01DSUQgOTY+Pl0KL0lEIChub2RlMDAwMDA3NzApPj4KZW5kb2JqCjU0NyAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9Ob25TdHJ1Y3QKL1AgNTQ2IDAgUgovSyBbNTQ4IDAgUl0KL0lEIChub2RlMDAwMDA3NjkpPj4KZW5kb2JqCjU0NiAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9QCi9QIDU0NSAwIFIKL0sgWzU0NyAwIFJdCi9JRCAobm9kZTAwMDAwNzY4KT4+CmVuZG9iago1NDUgMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvTm9uU3RydWN0Ci9QIDU0NCAwIFIKL0sgWzU0NiAwIFJdCi9JRCAobm9kZTAwMDAwNzY3KT4+CmVuZG9iago1NDQgMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvTm9uU3RydWN0Ci9QIDU0MyAwIFIKL0sgWzU0NSAwIFJdCi9JRCAobm9kZTAwMDAwNzY2KT4+CmVuZG9iago1NDMgMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvTm9uU3RydWN0Ci9QIDU0MiAwIFIKL0sgWzU0NCAwIFJdCi9JRCAobm9kZTAwMDAwNzY0KT4+CmVuZG9iago1NDIgMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvVEQKL1AgNTQxIDAgUgovSyBbNTQzIDAgUl0KL0EgWzw8L08gL1RhYmxlCi9IZWFkZXJzIFtdPj4gPDwvTyAvVGFibGUKL1Jvd1NwYW4gMT4+IDw8L08gL1RhYmxlCi9Db2xTcGFuIDI+Pl0KL0lEIChub2RlMDAwMDA3NjMpPj4KZW5kb2JqCjU0MSAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9UUgovUCAyMiAwIFIKL0sgWzU0MiAwIFJdCi9JRCAobm9kZTAwMDAwNzYyKT4+CmVuZG9iago1NTQgMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvTm9uU3RydWN0Ci9QIDU1MyAwIFIKL0sgWzw8L1R5cGUgL01DUgovUGcgMTcgMCBSCi9NQ0lEIDk3Pj5dCi9JRCAobm9kZTAwMDAwNzc3KT4+CmVuZG9iago1NTMgMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvTm9uU3RydWN0Ci9QIDU1MiAwIFIKL0sgWzU1NCAwIFJdCi9JRCAobm9kZTAwMDAwNzc2KT4+CmVuZG9iago1NTIgMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvTm9uU3RydWN0Ci9QIDU1MSAwIFIKL0sgWzU1MyAwIFJdCi9JRCAobm9kZTAwMDAwNzc1KT4+CmVuZG9iago1NTcgMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvTm9uU3RydWN0Ci9QIDU1NiAwIFIKL0sgWzw8L1R5cGUgL01DUgovUGcgMTcgMCBSCi9NQ0lEIDk4Pj5dCi9JRCAobm9kZTAwMDAwNzgyKT4+CmVuZG9iago1NTYgMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvTm9uU3RydWN0Ci9QIDU1NSAwIFIKL0sgWzU1NyAwIFJdCi9JRCAobm9kZTAwMDAwNzgxKT4+CmVuZG9iago1NTUgMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvTm9uU3RydWN0Ci9QIDU1MSAwIFIKL0sgWzU1NiAwIFJdCi9JRCAobm9kZTAwMDAwNzgwKT4+CmVuZG9iago1NTEgMCBvYmoKPDwvVHlwZSAvU3RydWN0RWxlbQovUyAvTm9uU3RydWN0Ci9QIDU1MCAwIFIKL0sgWzU1MiAwIFIgNTU1IDAgUl0KL0lEIChub2RlMDAwMDA3NzMpPj4KZW5kb2JqCjU1MCAwIG9iago8PC9UeXBlIC9TdHJ1Y3RFbGVtCi9TIC9URAovUCA1NDkgMCBSCi9LIFs1NTEgMCBSXQovQSBbPDwvTyAvVGFibGUKL0hlYWRlcnMgW10+PiA8PC9PIC9UYWJsZQovUm93U3BhbiAxPj4gPDwvTyAvVGFibGUKL0NvbFNwYW4gMj4+XQovSUQgKG5vZGUwMDAwMDc3Mik+PgplbmRvYmoKNTQ5IDAgb2JqCjw8L1R5cGUgL1N0cnVjdEVsZW0KL1MgL1RSCi9QIDIyIDAgUgovSyBbNTUwIDAgUl0KL0lEIChub2RlMDAwMDA3NzEpPj4KZW5kb2JqCjIyIDAgb2JqCjw8L1R5cGUgL1N0cnVjdEVsZW0KL1MgL1RhYmxlCi9QIDIxIDAgUgovSyBbMjMgMCBSIDI2IDAgUiA2MSAwIFIgODcgMCBSIDEwMCAwIFIgMTE0IDAgUiAxMzMgMCBSIDE1OCAwIFIgMTg2IDAgUiAyMDcgMCBSIDI1MiAwIFIgMjgyIDAgUiAzNDUgMCBSIDM0OCAwIFIgNDkzIDAgUiA1MzUgMCBSIDU0MSAwIFIgNTQ5IDAgUl0KL0lEIChub2RlMDAwMDAwMDUpPj4KZW5kb2JqCjIxIDAgb2JqCjw8L1R5cGUgL1N0cnVjdEVsZW0KL1MgL0RvY3VtZW50Ci9MYW5nIChlbikKL1AgMjAgMCBSCi9LIFsyMiAwIFJdCi9JRCAobm9kZTAwMDAwMDAxKT4+CmVuZG9iago1NTggMCBvYmoKWzMxIDAgUiAzNyAwIFIgMzkgMCBSIDQyIDAgUiA0NSAwIFIgNTAgMCBSIDUyIDAgUiA1NSAwIFIgNTcgMCBSIDYwIDAgUiA2NiAwIFIgNzIgMCBSIDc0IDAgUiA3NyAwIFIgNzkgMCBSIDg0IDAgUiA4NiAwIFIgOTIgMCBSIDk1IDAgUiA5NyAwIFIgOTkgMCBSIDEwNSAwIFIgMTA3IDAgUiAxMTEgMCBSIDExMyAwIFIgMTE5IDAgUiAxMjIgMCBSIDEyNCAwIFIgMTI3IDAgUiAxMjkgMCBSIDEzMiAwIFIgMTM4IDAgUiAxNDEgMCBSIDE0MyAwIFIgMTQ1IDAgUiAxNDcgMCBSIDE1MCAwIFIgMTUyIDAgUiAxNTUgMCBSIDE1NyAwIFIgMTYzIDAgUiAxNjYgMCBSIDE2OCAwIFIgMTcwIDAgUiAxNzMgMCBSIDE3NSAwIFIgMTc4IDAgUiAxODAgMCBSIDE4MyAwIFIgMTg1IDAgUiAxOTEgMCBSIDE5NCAwIFIgMTk3IDAgUiAyMDAgMCBSIDIwMyAwIFIgMjA2IDAgUiAyMTIgMCBSIDIxNSAwIFIgMjE2IDAgUiAyMTkgMCBSIDIyMCAwIFIgMjIyIDAgUiAyMjQgMCBSIDIyNSAwIFIgMjI4IDAgUiAyMjkgMCBSIDIzMiAwIFIgMjMzIDAgUiAyMzUgMCBSIDIzNyAwIFIgMjM4IDAgUiAyNDEgMCBSIDI0MiAwIFIgMjQ1IDAgUiAyNDYgMCBSIDI0OCAwIFIgMjUwIDAgUiAyNTEgMCBSIDI1NyAwIFIgMjYwIDAgUiAyNjIgMCBSIDI2NCAwIFIgMjY2IDAgUiAyNjggMCBSIDI2OCAwIFIgMjcwIDAgUiAyNzAgMCBSIDI3MyAwIFIgMjc1IDAgUiAyNzcgMCBSIDI3OSAwIFIgMjgxIDAgUiAyODcgMCBSIDI5MCAwIFIgMjkzIDAgUiAyOTUgMCBSIDI5NyAwIFIgMjk5IDAgUiAzMDEgMCBSIDMwNCAwIFIgMzA2IDAgUiAzMDcgMCBSIDMwOSAwIFIgMzExIDAgUiAzMTMgMCBSIDMxNiAwIFIgMzE4IDAgUiAzMjAgMCBSIDMyMyAwIFIgMzI2IDAgUiAzMjkgMCBSIDMzMSAwIFIgMzMzIDAgUiAzMzUgMCBSIDMzNyAwIFIgMzM5IDAgUiAzNDIgMCBSIDM0NCAwIFIgMjUgMCBSIDM0NyAwIFJdCmVuZG9iago1NTkgMCBvYmoKWzM1MyAwIFIgMzU1IDAgUiAzNTcgMCBSIDM1OSAwIFIgMzY1IDAgUiAzNjYgMCBSIDM2OSAwIFIgMzcwIDAgUiAzNzMgMCBSIDM3NCAwIFIgMzc3IDAgUiAzNzggMCBSIDM4MSAwIFIgMzgyIDAgUiAzODcgMCBSIDM4OCAwIFIgMzkxIDAgUiAzOTIgMCBSIDM5NSAwIFIgMzk2IDAgUiAzOTkgMCBSIDQwMCAwIFIgNDAzIDAgUiA0MDQgMCBSIDQwOSAwIFIgNDEwIDAgUiA0MTMgMCBSIDQxNCAwIFIgNDE3IDAgUiA0MjAgMCBSIDQyMSAwIFIgNDI0IDAgUiA0MjcgMCBSIDQzMSAwIFIgNDMyIDAgUiA0MzQgMCBSIDQzNSAwIFIgNDM3IDAgUiA0MzggMCBSIDQ0MCAwIFIgNDQxIDAgUiA0NDMgMCBSIDQ0NCAwIFIgNDQ2IDAgUiA0NDcgMCBSIDQ0OSAwIFIgNDUwIDAgUiA0NTIgMCBSIDQ1MyAwIFIgNDU1IDAgUiA0NTYgMCBSIDQ1OCAwIFIgNDU5IDAgUiA0NjEgMCBSIDQ2MiAwIFIgNDY0IDAgUiA0NjUgMCBSIDQ2NyAwIFIgNDY4IDAgUiA0NzAgMCBSIDQ3MSAwIFIgNDczIDAgUiA0NzQgMCBSIDQ3NiAwIFIgNDc3IDAgUiA0NzkgMCBSIDQ4MCAwIFIgNDgyIDAgUiA0ODMgMCBSIDQ4NSAwIFIgNDg2IDAgUiA0ODggMCBSIDQ4OSAwIFIgNDkxIDAgUiA0OTIgMCBSIDU0MCAwIFIgNDk4IDAgUiA1MDEgMCBSIDUwMyAwIFIgNTA1IDAgUiA1MDcgMCBSIDUxMCAwIFIgNTEyIDAgUiA1MTQgMCBSIDUxNiAwIFIgNTE5IDAgUiA1MjEgMCBSIDUyMyAwIFIgNTI1IDAgUiA1MjggMCBSIDUzMCAwIFIgNTMyIDAgUiA1MzQgMCBSIDU0OCAwIFIgNTQ4IDAgUiA1NDggMCBSIDU0OCAwIFIgNTU0IDAgUiA1NTcgMCBSXQplbmRvYmoKNTYwIDAgb2JqCjw8L1R5cGUgL1BhcmVudFRyZWUKL051bXMgWzAgNTU4IDAgUiAxIDU1OSAwIFJdPj4KZW5kb2JqCjU2MSAwIG9iago8PC9MaW1pdHMgWyhub2RlMDAwMDAwMDEpIChub2RlMDAwMDA3ODIpXQovTmFtZXMgWyhub2RlMDAwMDAwMDEpIDIxIDAgUiAobm9kZTAwMDAwMDAyKSAyNSAwIFIgKG5vZGUwMDAwMDAwNSkgMjIgMCBSIChub2RlMDAwMDAwMDcpIDIzIDAgUiAobm9kZTAwMDAwMDA4KSAyNCAwIFIgKG5vZGUwMDAwMDAxMCkgMjYgMCBSIChub2RlMDAwMDAwMTEpIDI3IDAgUiAobm9kZTAwMDAwMDEyKSAyOCAwIFIgKG5vZGUwMDAwMDAxNCkgMjkgMCBSIChub2RlMDAwMDAwMTUpIDMwIDAgUiAobm9kZTAwMDAwMDE2KSAzMSAwIFIgKG5vZGUwMDAwMDAxOSkgMzIgMCBSIChub2RlMDAwMDAwMjApIDMzIDAgUiAobm9kZTAwMDAwMDIxKSAzNCAwIFIgKG5vZGUwMDAwMDAyMykgMzUgMCBSIChub2RlMDAwMDAwMjQpIDM2IDAgUiAobm9kZTAwMDAwMDI1KSAzNyAwIFIgKG5vZGUwMDAwMDAyNikgMzggMCBSIChub2RlMDAwMDAwMjcpIDM5IDAgUiAobm9kZTAwMDAwMDI4KSA0MCAwIFIgKG5vZGUwMDAwMDAyOSkgNDEgMCBSIChub2RlMDAwMDAwMzApIDQyIDAgUiAobm9kZTAwMDAwMDMyKSA0MyAwIFIgKG5vZGUwMDAwMDAzMykgNDQgMCBSIChub2RlMDAwMDAwMzQpIDQ1IDAgUiAobm9kZTAwMDAwMDM3KSA0NiAwIFIgKG5vZGUwMDAwMDAzOCkgNDcgMCBSIChub2RlMDAwMDAwNDApIDQ4IDAgUiAobm9kZTAwMDAwMDQxKSA0OSAwIFIgKG5vZGUwMDAwMDA0MikgNTAgMCBSIChub2RlMDAwMDAwNDMpIDUxIDAgUiAobm9kZTAwMDAwMDQ0KSA1MiAwIFIgKG5vZGUwMDAwMDA0NSkgNTMgMCBSIChub2RlMDAwMDAwNDYpIDU0IDAgUiAobm9kZTAwMDAwMDQ3KSA1NSAwIFIgKG5vZGUwMDAwMDA0OCkgNTYgMCBSIChub2RlMDAwMDAwNDkpIDU3IDAgUiAobm9kZTAwMDAwMDUwKSA1OCAwIFIgKG5vZGUwMDAwMDA1MSkgNTkgMCBSIChub2RlMDAwMDAwNTIpIDYwIDAgUiAobm9kZTAwMDAwMDU4KSA2MSAwIFIgKG5vZGUwMDAwMDA1OSkgNjIgMCBSIChub2RlMDAwMDAwNjApIDYzIDAgUiAobm9kZTAwMDAwMDYyKSA2NCAwIFIgKG5vZGUwMDAwMDA2MykgNjUgMCBSIChub2RlMDAwMDAwNjQpIDY2IDAgUiAobm9kZTAwMDAwMDY3KSA2NyAwIFIgKG5vZGUwMDAwMDA2OCkgNjggMCBSIChub2RlMDAwMDAwNjkpIDY5IDAgUiAobm9kZTAwMDAwMDcxKSA3MCAwIFIgKG5vZGUwMDAwMDA3MikgNzEgMCBSIChub2RlMDAwMDAwNzMpIDcyIDAgUiAobm9kZTAwMDAwMDc0KSA3MyAwIFIgKG5vZGUwMDAwMDA3NSkgNzQgMCBSIChub2RlMDAwMDAwNzYpIDc1IDAgUiAobm9kZTAwMDAwMDc3KSA3NiAwIFIgKG5vZGUwMDAwMDA3OCkgNzcgMCBSIChub2RlMDAwMDAwNzkpIDc4IDAgUiAobm9kZTAwMDAwMDgwKSA3OSAwIFIgKG5vZGUwMDAwMDA4MSkgODAgMCBSIChub2RlMDAwMDAwODIpIDgxIDAgUiAobm9kZTAwMDAwMDg2KSA4MiAwIFIgKG5vZGUwMDAwMDA4NykgODMgMCBSIChub2RlMDAwMDAwODgpIDg0IDAgUiAobm9kZTAwMDAwMDg5KSA4NSAwIFIgKG5vZGUwMDAwMDA5MCkgODYgMCBSIChub2RlMDAwMDAwOTUpIDg3IDAgUiAobm9kZTAwMDAwMDk2KSA4OCAwIFIgKG5vZGUwMDAwMDA5NykgODkgMCBSIChub2RlMDAwMDAwOTkpIDkwIDAgUiAobm9kZTAwMDAwMTAwKSA5MSAwIFIgKG5vZGUwMDAwMDEwMSkgOTIgMCBSIChub2RlMDAwMDAxMDQpIDkzIDAgUiAobm9kZTAwMDAwMTA1KSA5NCAwIFIgKG5vZGUwMDAwMDEwNikgOTUgMCBSIChub2RlMDAwMDAxMDcpIDk2IDAgUiAobm9kZTAwMDAwMTA4KSA5NyAwIFIgKG5vZGUwMDAwMDEwOSkgOTggMCBSIChub2RlMDAwMDAxMTApIDk5IDAgUiAobm9kZTAwMDAwMTExKSAxMDAgMCBSIChub2RlMDAwMDAxMTIpIDEwMSAwIFIgKG5vZGUwMDAwMDExMykgMTAyIDAgUiAobm9kZTAwMDAwMTE1KSAxMDMgMCBSIChub2RlMDAwMDAxMTYpIDEwNCAwIFIgKG5vZGUwMDAwMDExNykgMTA1IDAgUiAobm9kZTAwMDAwMTE4KSAxMDYgMCBSIChub2RlMDAwMDAxMTkpIDEwNyAwIFIgKG5vZGUwMDAwMDEyMCkgMTA4IDAgUiAobm9kZTAwMDAwMTIxKSAxMDkgMCBSIChub2RlMDAwMDAxMjIpIDExMCAwIFIgKG5vZGUwMDAwMDEyNCkgMTExIDAgUiAobm9kZTAwMDAwMTI1KSAxMTIgMCBSIChub2RlMDAwMDAxMjcpIDExMyAwIFIgKG5vZGUwMDAwMDEzMikgMTE0IDAgUiAobm9kZTAwMDAwMTMzKSAxMTUgMCBSIChub2RlMDAwMDAxMzQpIDExNiAwIFIgKG5vZGUwMDAwMDEzNikgMTE3IDAgUiAobm9kZTAwMDAwMTM3KSAxMTggMCBSIChub2RlMDAwMDAxMzgpIDExOSAwIFIgKG5vZGUwMDAwMDE0MCkgMTIwIDAgUiAobm9kZTAwMDAwMTQxKSAxMjEgMCBSIChub2RlMDAwMDAxNDIpIDEyMiAwIFIgKG5vZGUwMDAwMDE0MykgMTIzIDAgUiAobm9kZTAwMDAwMTQ0KSAxMjQgMCBSIChub2RlMDAwMDAxNDUpIDEyNSAwIFIgKG5vZGUwMDAwMDE0NikgMTI2IDAgUiAobm9kZTAwMDAwMTQ3KSAxMjcgMCBSIChub2RlMDAwMDAxNDgpIDEyOCAwIFIgKG5vZGUwMDAwMDE0OSkgMTI5IDAgUiAobm9kZTAwMDAwMTU0KSAxMzAgMCBSIChub2RlMDAwMDAxNTUpIDEzMSAwIFIgKG5vZGUwMDAwMDE1NikgMTMyIDAgUiAobm9kZTAwMDAwMTYxKSAxMzMgMCBSIChub2RlMDAwMDAxNjIpIDEzNCAwIFIgKG5vZGUwMDAwMDE2MykgMTM1IDAgUiAobm9kZTAwMDAwMTY1KSAxMzYgMCBSIChub2RlMDAwMDAxNjYpIDEzNyAwIFIgKG5vZGUwMDAwMDE2NykgMTM4IDAgUiAobm9kZTAwMDAwMTcwKSAxMzkgMCBSIChub2RlMDAwMDAxNzEpIDE0MCAwIFIgKG5vZGUwMDAwMDE3MikgMTQxIDAgUiAobm9kZTAwMDAwMTczKSAxNDIgMCBSIChub2RlMDAwMDAxNzQpIDE0MyAwIFIgKG5vZGUwMDAwMDE3NSkgMTQ0IDAgUiAobm9kZTAwMDAwMTc2KSAxNDUgMCBSIChub2RlMDAwMDAxNzcpIDE0NiAwIFIgKG5vZGUwMDAwMDE3OCkgMTQ3IDAgUiAobm9kZTAwMDAwMTc5KSAxNDggMCBSIChub2RlMDAwMDAxODApIDE0OSAwIFIgKG5vZGUwMDAwMDE4MSkgMTUwIDAgUiAobm9kZTAwMDAwMTgyKSAxNTEgMCBSIChub2RlMDAwMDAxODMpIDE1MiAwIFIgKG5vZGUwMDAwMDE4NikgMTUzIDAgUiAobm9kZTAwMDAwMTg3KSAxNTQgMCBSIChub2RlMDAwMDAxODgpIDE1NSAwIFIgKG5vZGUwMDAwMDE4OSkgMTU2IDAgUiAobm9kZTAwMDAwMTkwKSAxNTcgMCBSIChub2RlMDAwMDAxOTcpIDE1OCAwIFIgKG5vZGUwMDAwMDE5OCkgMTU5IDAgUiAobm9kZTAwMDAwMTk5KSAxNjAgMCBSIChub2RlMDAwMDAyMDEpIDE2MSAwIFIgKG5vZGUwMDAwMDIwMikgMTYyIDAgUiAobm9kZTAwMDAwMjAzKSAxNjMgMCBSIChub2RlMDAwMDAyMDYpIDE2NCAwIFIgKG5vZGUwMDAwMDIwNykgMTY1IDAgUiAobm9kZTAwMDAwMjA4KSAxNjYgMCBSIChub2RlMDAwMDAyMDkpIDE2NyAwIFIgKG5vZGUwMDAwMDIxMCkgMTY4IDAgUiAobm9kZTAwMDAwMjExKSAxNjkgMCBSIChub2RlMDAwMDAyMTIpIDE3MCAwIFIgKG5vZGUwMDAwMDIxMykgMTcxIDAgUiAobm9kZTAwMDAwMjE0KSAxNzIgMCBSIChub2RlMDAwMDAyMTUpIDE3MyAwIFIgKG5vZGUwMDAwMDIxNikgMTc0IDAgUiAobm9kZTAwMDAwMjE3KSAxNzUgMCBSIChub2RlMDAwMDAyMTkpIDE3NiAwIFIgKG5vZGUwMDAwMDIyMCkgMTc3IDAgUiAobm9kZTAwMDAwMjIxKSAxNzggMCBSIChub2RlMDAwMDAyMjIpIDE3OSAwIFIgKG5vZGUwMDAwMDIyMykgMTgwIDAgUiAobm9kZTAwMDAwMjI1KSAxODEgMCBSIChub2RlMDAwMDAyMjYpIDE4MiAwIFIgKG5vZGUwMDAwMDIyNykgMTgzIDAgUiAobm9kZTAwMDAwMjI4KSAxODQgMCBSIChub2RlMDAwMDAyMjkpIDE4NSAwIFIgKG5vZGUwMDAwMDIzNSkgMTg2IDAgUiAobm9kZTAwMDAwMjM2KSAxODcgMCBSIChub2RlMDAwMDAyMzcpIDE4OCAwIFIgKG5vZGUwMDAwMDIzOSkgMTg5IDAgUiAobm9kZTAwMDAwMjQwKSAxOTAgMCBSIChub2RlMDAwMDAyNDEpIDE5MSAwIFIgKG5vZGUwMDAwMDI0NCkgMTkyIDAgUiAobm9kZTAwMDAwMjQ1KSAxOTMgMCBSIChub2RlMDAwMDAyNDYpIDE5NCAwIFIgKG5vZGUwMDAwMDI0OSkgMTk1IDAgUiAobm9kZTAwMDAwMjUwKSAxOTYgMCBSIChub2RlMDAwMDAyNTEpIDE5NyAwIFIgKG5vZGUwMDAwMDI1NCkgMTk4IDAgUiAobm9kZTAwMDAwMjU1KSAxOTkgMCBSIChub2RlMDAwMDAyNTYpIDIwMCAwIFIgKG5vZGUwMDAwMDI1OSkgMjAxIDAgUiAobm9kZTAwMDAwMjYwKSAyMDIgMCBSIChub2RlMDAwMDAyNjEpIDIwMyAwIFIgKG5vZGUwMDAwMDI2NCkgMjA0IDAgUiAobm9kZTAwMDAwMjY1KSAyMDUgMCBSIChub2RlMDAwMDAyNjYpIDIwNiAwIFIgKG5vZGUwMDAwMDI3MykgMjA3IDAgUiAobm9kZTAwMDAwMjc0KSAyMDggMCBSIChub2RlMDAwMDAyNzUpIDIwOSAwIFIgKG5vZGUwMDAwMDI3NykgMjEwIDAgUiAobm9kZTAwMDAwMjc4KSAyMTEgMCBSIChub2RlMDAwMDAyNzkpIDIxMiAwIFIgKG5vZGUwMDAwMDI4MikgMjEzIDAgUiAobm9kZTAwMDAwMjgzKSAyMTQgMCBSIChub2RlMDAwMDAyODQpIDIxNSAwIFIgKG5vZGUwMDAwMDI4NikgMjE2IDAgUiAobm9kZTAwMDAwMjg4KSAyMTcgMCBSIChub2RlMDAwMDAyODkpIDIxOCAwIFIgKG5vZGUwMDAwMDI5MSkgMjE5IDAgUiAobm9kZTAwMDAwMjkyKSAyMjAgMCBSIChub2RlMDAwMDAyOTMpIDIyMSAwIFIgKG5vZGUwMDAwMDI5NSkgMjIyIDAgUiAobm9kZTAwMDAwMjk3KSAyMjMgMCBSIChub2RlMDAwMDAyOTkpIDIyNCAwIFIgKG5vZGUwMDAwMDMwMSkgMjI1IDAgUiAobm9kZTAwMDAwMzAzKSAyMjYgMCBSIChub2RlMDAwMDAzMDQpIDIyNyAwIFIgKG5vZGUwMDAwMDMwNSkgMjI4IDAgUiAobm9kZTAwMDAwMzA3KSAyMjkgMCBSIChub2RlMDAwMDAzMDkpIDIzMCAwIFIgKG5vZGUwMDAwMDMxMCkgMjMxIDAgUiAobm9kZTAwMDAwMzEyKSAyMzIgMCBSIChub2RlMDAwMDAzMTMpIDIzMyAwIFIgKG5vZGUwMDAwMDMxNCkgMjM0IDAgUiAobm9kZTAwMDAwMzE2KSAyMzUgMCBSIChub2RlMDAwMDAzMTgpIDIzNiAwIFIgKG5vZGUwMDAwMDMyMCkgMjM3IDAgUiAobm9kZTAwMDAwMzIyKSAyMzggMCBSIChub2RlMDAwMDAzMjQpIDIzOSAwIFIgKG5vZGUwMDAwMDMyNSkgMjQwIDAgUiAobm9kZTAwMDAwMzI2KSAyNDEgMCBSIChub2RlMDAwMDAzMjgpIDI0MiAwIFIgKG5vZGUwMDAwMDMzMCkgMjQzIDAgUiAobm9kZTAwMDAwMzMxKSAyNDQgMCBSIChub2RlMDAwMDAzMzMpIDI0NSAwIFIgKG5vZGUwMDAwMDMzNCkgMjQ2IDAgUiAobm9kZTAwMDAwMzM1KSAyNDcgMCBSIChub2RlMDAwMDAzMzcpIDI0OCAwIFIgKG5vZGUwMDAwMDMzOSkgMjQ5IDAgUiAobm9kZTAwMDAwMzQxKSAyNTAgMCBSIChub2RlMDAwMDAzNDMpIDI1MSAwIFIgKG5vZGUwMDAwMDM0OSkgMjUyIDAgUiAobm9kZTAwMDAwMzUwKSAyNTMgMCBSIChub2RlMDAwMDAzNTEpIDI1NCAwIFIgKG5vZGUwMDAwMDM1MykgMjU1IDAgUiAobm9kZTAwMDAwMzU0KSAyNTYgMCBSIChub2RlMDAwMDAzNTUpIDI1NyAwIFIgKG5vZGUwMDAwMDM1OCkgMjU4IDAgUiAobm9kZTAwMDAwMzU5KSAyNTkgMCBSIChub2RlMDAwMDAzNjApIDI2MCAwIFIgKG5vZGUwMDAwMDM2MSkgMjYxIDAgUiAobm9kZTAwMDAwMzYyKSAyNjIgMCBSIChub2RlMDAwMDAzNjMpIDI2MyAwIFIgKG5vZGUwMDAwMDM2NCkgMjY0IDAgUiAobm9kZTAwMDAwMzY1KSAyNjUgMCBSIChub2RlMDAwMDAzNjYpIDI2NiAwIFIgKG5vZGUwMDAwMDM2NykgMjY3IDAgUiAobm9kZTAwMDAwMzY4KSAyNjggMCBSIChub2RlMDAwMDAzNjkpIDI2OSAwIFIgKG5vZGUwMDAwMDM3MCkgMjcwIDAgUiAobm9kZTAwMDAwMzcxKSAyNzEgMCBSIChub2RlMDAwMDAzNzIpIDI3MiAwIFIgKG5vZGUwMDAwMDM3MykgMjczIDAgUiAobm9kZTAwMDAwMzc0KSAyNzQgMCBSIChub2RlMDAwMDAzNzUpIDI3NSAwIFIgKG5vZGUwMDAwMDM3NikgMjc2IDAgUiAobm9kZTAwMDAwMzc3KSAyNzcgMCBSIChub2RlMDAwMDAzNzkpIDI3OCAwIFIgKG5vZGUwMDAwMDM4MCkgMjc5IDAgUiAobm9kZTAwMDAwMzgxKSAyODAgMCBSIChub2RlMDAwMDAzODIpIDI4MSAwIFIgKG5vZGUwMDAwMDM4NykgMjgyIDAgUiAobm9kZTAwMDAwMzg4KSAyODMgMCBSIChub2RlMDAwMDAzODkpIDI4NCAwIFIgKG5vZGUwMDAwMDM5MSkgMjg1IDAgUiAobm9kZTAwMDAwMzkyKSAyODYgMCBSIChub2RlMDAwMDAzOTMpIDI4NyAwIFIgKG5vZGUwMDAwMDM5NikgMjg4IDAgUiAobm9kZTAwMDAwMzk3KSAyODkgMCBSIChub2RlMDAwMDAzOTgpIDI5MCAwIFIgKG5vZGUwMDAwMDQwMSkgMjkxIDAgUiAobm9kZTAwMDAwNDAyKSAyOTIgMCBSIChub2RlMDAwMDA0MDMpIDI5MyAwIFIgKG5vZGUwMDAwMDQwNCkgMjk0IDAgUiAobm9kZTAwMDAwNDA1KSAyOTUgMCBSIChub2RlMDAwMDA0MDYpIDI5NiAwIFIgKG5vZGUwMDAwMDQwNykgMjk3IDAgUiAobm9kZTAwMDAwNDA4KSAyOTggMCBSIChub2RlMDAwMDA0MDkpIDI5OSAwIFIgKG5vZGUwMDAwMDQxMCkgMzAwIDAgUiAobm9kZTAwMDAwNDExKSAzMDEgMCBSIChub2RlMDAwMDA0MTIpIDMwMiAwIFIgKG5vZGUwMDAwMDQxMykgMzAzIDAgUiAobm9kZTAwMDAwNDE0KSAzMDQgMCBSIChub2RlMDAwMDA0MTUpIDMwNSAwIFIgKG5vZGUwMDAwMDQxNykgMzA2IDAgUiAobm9kZTAwMDAwNDE4KSAzMDcgMCBSIChub2RlMDAwMDA0MTkpIDMwOCAwIFIgKG5vZGUwMDAwMDQyMSkgMzA5IDAgUiAobm9kZTAwMDAwNDIzKSAzMTAgMCBSIChub2RlMDAwMDA0MjUpIDMxMSAwIFIgKG5vZGUwMDAwMDQyNikgMzEyIDAgUiAobm9kZTAwMDAwNDI4KSAzMTMgMCBSIChub2RlMDAwMDA0MzIpIDMxNCAwIFIgKG5vZGUwMDAwMDQzNCkgMzE1IDAgUiAobm9kZTAwMDAwNDM2KSAzMTYgMCBSIChub2RlMDAwMDA0MzgpIDMxNyAwIFIgKG5vZGUwMDAwMDQ0MCkgMzE4IDAgUiAobm9kZTAwMDAwNDQxKSAzMTkgMCBSIChub2RlMDAwMDA0NDMpIDMyMCAwIFIgKG5vZGUwMDAwMDQ0NykgMzIxIDAgUiAobm9kZTAwMDAwNDQ5KSAzMjIgMCBSIChub2RlMDAwMDA0NTEpIDMyMyAwIFIgKG5vZGUwMDAwMDQ1NikgMzI0IDAgUiAobm9kZTAwMDAwNDU3KSAzMjUgMCBSIChub2RlMDAwMDA0NTgpIDMyNiAwIFIgKG5vZGUwMDAwMDQ2MSkgMzI3IDAgUiAobm9kZTAwMDAwNDYyKSAzMjggMCBSIChub2RlMDAwMDA0NjMpIDMyOSAwIFIgKG5vZGUwMDAwMDQ2NCkgMzMwIDAgUiAobm9kZTAwMDAwNDY1KSAzMzEgMCBSIChub2RlMDAwMDA0NjYpIDMzMiAwIFIgKG5vZGUwMDAwMDQ2NykgMzMzIDAgUiAobm9kZTAwMDAwNDY4KSAzMzQgMCBSIChub2RlMDAwMDA0NjkpIDMzNSAwIFIgKG5vZGUwMDAwMDQ3MCkgMzM2IDAgUiAobm9kZTAwMDAwNDcxKSAzMzcgMCBSIChub2RlMDAwMDA0NzIpIDMzOCAwIFIgKG5vZGUwMDAwMDQ3MykgMzM5IDAgUiAobm9kZTAwMDAwNDc0KSAzNDAgMCBSIChub2RlMDAwMDA0NzUpIDM0MSAwIFIgKG5vZGUwMDAwMDQ3NikgMzQyIDAgUiAobm9kZTAwMDAwNDc3KSAzNDMgMCBSIChub2RlMDAwMDA0NzgpIDM0NCAwIFIgKG5vZGUwMDAwMDQ4OSkgMzQ1IDAgUiAobm9kZTAwMDAwNDkwKSAzNDYgMCBSIChub2RlMDAwMDA0OTEpIDM0NyAwIFIgKG5vZGUwMDAwMDQ5NCkgMzQ4IDAgUiAobm9kZTAwMDAwNDk1KSAzNDkgMCBSIChub2RlMDAwMDA0OTYpIDM1MCAwIFIgKG5vZGUwMDAwMDQ5OCkgMzUxIDAgUiAobm9kZTAwMDAwNDk5KSAzNTIgMCBSIChub2RlMDAwMDA1MDApIDM1MyAwIFIgKG5vZGUwMDAwMDUwMSkgMzU0IDAgUiAobm9kZTAwMDAwNTAyKSAzNTUgMCBSIChub2RlMDAwMDA1MDMpIDM1NiAwIFIgKG5vZGUwMDAwMDUwNCkgMzU3IDAgUiAobm9kZTAwMDAwNTA1KSAzNTggMCBSIChub2RlMDAwMDA1MDYpIDM1OSAwIFIgKG5vZGUwMDAwMDUwOSkgMzYwIDAgUiAobm9kZTAwMDAwNTEwKSAzNjEgMCBSIChub2RlMDAwMDA1MTEpIDM2MiAwIFIgKG5vZGUwMDAwMDUxMykgMzYzIDAgUiAobm9kZTAwMDAwNTE0KSAzNjQgMCBSIChub2RlMDAwMDA1MTYpIDM2NSAwIFIgKG5vZGUwMDAwMDUxNykgMzY2IDAgUiAobm9kZTAwMDAwNTE4KSAzNjcgMCBSIChub2RlMDAwMDA1MTkpIDM2OCAwIFIgKG5vZGUwMDAwMDUyMSkgMzY5IDAgUiAobm9kZTAwMDAwNTIyKSAzNzAgMCBSIChub2RlMDAwMDA1MjMpIDM3MSAwIFIgKG5vZGUwMDAwMDUyNCkgMzcyIDAgUiAobm9kZTAwMDAwNTI2KSAzNzMgMCBSIChub2RlMDAwMDA1MjcpIDM3NCAwIFIgKG5vZGUwMDAwMDUyOCkgMzc1IDAgUiAobm9kZTAwMDAwNTI5KSAzNzYgMCBSIChub2RlMDAwMDA1MzEpIDM3NyAwIFIgKG5vZGUwMDAwMDUzMikgMzc4IDAgUiAobm9kZTAwMDAwNTMzKSAzNzkgMCBSIChub2RlMDAwMDA1MzQpIDM4MCAwIFIgKG5vZGUwMDAwMDUzNikgMzgxIDAgUiAobm9kZTAwMDAwNTM3KSAzODIgMCBSIChub2RlMDAwMDA1MzgpIDM4MyAwIFIgKG5vZGUwMDAwMDUzOSkgMzg0IDAgUiAobm9kZTAwMDAwNTQxKSAzODUgMCBSIChub2RlMDAwMDA1NDIpIDM4NiAwIFIgKG5vZGUwMDAwMDU0NCkgMzg3IDAgUiAobm9kZTAwMDAwNTQ1KSAzODggMCBSIChub2RlMDAwMDA1NDYpIDM4OSAwIFIgKG5vZGUwMDAwMDU0NykgMzkwIDAgUiAobm9kZTAwMDAwNTQ5KSAzOTEgMCBSIChub2RlMDAwMDA1NTApIDM5MiAwIFIgKG5vZGUwMDAwMDU1MSkgMzkzIDAgUiAobm9kZTAwMDAwNTUyKSAzOTQgMCBSIChub2RlMDAwMDA1NTQpIDM5NSAwIFIgKG5vZGUwMDAwMDU1NSkgMzk2IDAgUiAobm9kZTAwMDAwNTU2KSAzOTcgMCBSIChub2RlMDAwMDA1NTcpIDM5OCAwIFIgKG5vZGUwMDAwMDU1OSkgMzk5IDAgUiAobm9kZTAwMDAwNTYwKSA0MDAgMCBSIChub2RlMDAwMDA1NjEpIDQwMSAwIFIgKG5vZGUwMDAwMDU2MikgNDAyIDAgUiAobm9kZTAwMDAwNTY0KSA0MDMgMCBSIChub2RlMDAwMDA1NjUpIDQwNCAwIFIgKG5vZGUwMDAwMDU2OCkgNDA1IDAgUiAobm9kZTAwMDAwNTY5KSA0MDYgMCBSIChub2RlMDAwMDA1NzEpIDQwNyAwIFIgKG5vZGUwMDAwMDU3MikgNDA4IDAgUiAobm9kZTAwMDAwNTc0KSA0MDkgMCBSIChub2RlMDAwMDA1NzUpIDQxMCAwIFIgKG5vZGUwMDAwMDU3NikgNDExIDAgUiAobm9kZTAwMDAwNTc3KSA0MTIgMCBSIChub2RlMDAwMDA1NzkpIDQxMyAwIFIgKG5vZGUwMDAwMDU4MCkgNDE0IDAgUiAobm9kZTAwMDAwNTgxKSA0MTUgMCBSIChub2RlMDAwMDA1ODIpIDQxNiAwIFIgKG5vZGUwMDAwMDU4NCkgNDE3IDAgUiAobm9kZTAwMDAwNTg2KSA0MTggMCBSIChub2RlMDAwMDA1ODcpIDQxOSAwIFIgKG5vZGUwMDAwMDU4OSkgNDIwIDAgUiAobm9kZTAwMDAwNTkwKSA0MjEgMCBSIChub2RlMDAwMDA1OTEpIDQyMiAwIFIgKG5vZGUwMDAwMDU5MikgNDIzIDAgUiAobm9kZTAwMDAwNTk0KSA0MjQgMCBSIChub2RlMDAwMDA2MDMpIDQyNSAwIFIgKG5vZGUwMDAwMDYwNCkgNDI2IDAgUiAobm9kZTAwMDAwNjA1KSA0MjcgMCBSIChub2RlMDAwMDA2MDkpIDQyOCAwIFIgKG5vZGUwMDAwMDYxMCkgNDI5IDAgUiAobm9kZTAwMDAwNjEyKSA0MzAgMCBSIChub2RlMDAwMDA2MTMpIDQzMSAwIFIgKG5vZGUwMDAwMDYxNSkgNDMyIDAgUiAobm9kZTAwMDAwNjE2KSA0MzMgMCBSIChub2RlMDAwMDA2MTcpIDQzNCAwIFIgKG5vZGUwMDAwMDYxOSkgNDM1IDAgUiAobm9kZTAwMDAwNjIwKSA0MzYgMCBSIChub2RlMDAwMDA2MjEpIDQzNyAwIFIgKG5vZGUwMDAwMDYyMykgNDM4IDAgUiAobm9kZTAwMDAwNjI0KSA0MzkgMCBSIChub2RlMDAwMDA2MjUpIDQ0MCAwIFIgKG5vZGUwMDAwMDYyNykgNDQxIDAgUiAobm9kZTAwMDAwNjI4KSA0NDIgMCBSIChub2RlMDAwMDA2MjkpIDQ0MyAwIFIgKG5vZGUwMDAwMDYzMSkgNDQ0IDAgUiAobm9kZTAwMDAwNjMyKSA0NDUgMCBSIChub2RlMDAwMDA2MzMpIDQ0NiAwIFIgKG5vZGUwMDAwMDYzNSkgNDQ3IDAgUiAobm9kZTAwMDAwNjM2KSA0NDggMCBSIChub2RlMDAwMDA2MzcpIDQ0OSAwIFIgKG5vZGUwMDAwMDYzOSkgNDUwIDAgUiAobm9kZTAwMDAwNjQwKSA0NTEgMCBSIChub2RlMDAwMDA2NDEpIDQ1MiAwIFIgKG5vZGUwMDAwMDY0MykgNDUzIDAgUiAobm9kZTAwMDAwNjQ0KSA0NTQgMCBSIChub2RlMDAwMDA2NDUpIDQ1NSAwIFIgKG5vZGUwMDAwMDY0NykgNDU2IDAgUiAobm9kZTAwMDAwNjQ4KSA0NTcgMCBSIChub2RlMDAwMDA2NDkpIDQ1OCAwIFIgKG5vZGUwMDAwMDY1MSkgNDU5IDAgUiAobm9kZTAwMDAwNjUyKSA0NjAgMCBSIChub2RlMDAwMDA2NTMpIDQ2MSAwIFIgKG5vZGUwMDAwMDY1NSkgNDYyIDAgUiAobm9kZTAwMDAwNjU2KSA0NjMgMCBSIChub2RlMDAwMDA2NTcpIDQ2NCAwIFIgKG5vZGUwMDAwMDY1OSkgNDY1IDAgUiAobm9kZTAwMDAwNjYwKSA0NjYgMCBSIChub2RlMDAwMDA2NjEpIDQ2NyAwIFIgKG5vZGUwMDAwMDY2MykgNDY4IDAgUiAobm9kZTAwMDAwNjY0KSA0NjkgMCBSIChub2RlMDAwMDA2NjUpIDQ3MCAwIFIgKG5vZGUwMDAwMDY2NykgNDcxIDAgUiAobm9kZTAwMDAwNjY4KSA0NzIgMCBSIChub2RlMDAwMDA2NjkpIDQ3MyAwIFIgKG5vZGUwMDAwMDY3MSkgNDc0IDAgUiAobm9kZTAwMDAwNjcyKSA0NzUgMCBSIChub2RlMDAwMDA2NzMpIDQ3NiAwIFIgKG5vZGUwMDAwMDY3NSkgNDc3IDAgUiAobm9kZTAwMDAwNjc2KSA0NzggMCBSIChub2RlMDAwMDA2NzcpIDQ3OSAwIFIgKG5vZGUwMDAwMDY3OSkgNDgwIDAgUiAobm9kZTAwMDAwNjgwKSA0ODEgMCBSIChub2RlMDAwMDA2ODEpIDQ4MiAwIFIgKG5vZGUwMDAwMDY4MykgNDgzIDAgUiAobm9kZTAwMDAwNjg0KSA0ODQgMCBSIChub2RlMDAwMDA2ODUpIDQ4NSAwIFIgKG5vZGUwMDAwMDY4NykgNDg2IDAgUiAobm9kZTAwMDAwNjg4KSA0ODcgMCBSIChub2RlMDAwMDA2ODkpIDQ4OCAwIFIgKG5vZGUwMDAwMDY5MSkgNDg5IDAgUiAobm9kZTAwMDAwNjkyKSA0OTAgMCBSIChub2RlMDAwMDA2OTMpIDQ5MSAwIFIgKG5vZGUwMDAwMDY5NSkgNDkyIDAgUiAobm9kZTAwMDAwNjk4KSA0OTMgMCBSIChub2RlMDAwMDA2OTkpIDQ5NCAwIFIgKG5vZGUwMDAwMDcwMCkgNDk1IDAgUiAobm9kZTAwMDAwNzAyKSA0OTYgMCBSIChub2RlMDAwMDA3MDMpIDQ5NyAwIFIgKG5vZGUwMDAwMDcwNCkgNDk4IDAgUiAobm9kZTAwMDAwNzA3KSA0OTkgMCBSIChub2RlMDAwMDA3MDgpIDUwMCAwIFIgKG5vZGUwMDAwMDcwOSkgNTAxIDAgUiAobm9kZTAwMDAwNzEwKSA1MDIgMCBSIChub2RlMDAwMDA3MTEpIDUwMyAwIFIgKG5vZGUwMDAwMDcxMikgNTA0IDAgUiAobm9kZTAwMDAwNzEzKSA1MDUgMCBSIChub2RlMDAwMDA3MTQpIDUwNiAwIFIgKG5vZGUwMDAwMDcxNSkgNTA3IDAgUiAobm9kZTAwMDAwNzE2KSA1MDggMCBSIChub2RlMDAwMDA3MTcpIDUwOSAwIFIgKG5vZGUwMDAwMDcxOCkgNTEwIDAgUiAobm9kZTAwMDAwNzE5KSA1MTEgMCBSIChub2RlMDAwMDA3MjApIDUxMiAwIFIgKG5vZGUwMDAwMDcyMSkgNTEzIDAgUiAobm9kZTAwMDAwNzIyKSA1MTQgMCBSIChub2RlMDAwMDA3MjMpIDUxNSAwIFIgKG5vZGUwMDAwMDcyNCkgNTE2IDAgUiAobm9kZTAwMDAwNzI1KSA1MTcgMCBSIChub2RlMDAwMDA3MjYpIDUxOCAwIFIgKG5vZGUwMDAwMDcyNykgNTE5IDAgUiAobm9kZTAwMDAwNzI4KSA1MjAgMCBSIChub2RlMDAwMDA3MjkpIDUyMSAwIFIgKG5vZGUwMDAwMDczMCkgNTIyIDAgUiAobm9kZTAwMDAwNzMxKSA1MjMgMCBSIChub2RlMDAwMDA3MzIpIDUyNCAwIFIgKG5vZGUwMDAwMDczMykgNTI1IDAgUiAobm9kZTAwMDAwNzM0KSA1MjYgMCBSIChub2RlMDAwMDA3MzUpIDUyNyAwIFIgKG5vZGUwMDAwMDczNikgNTI4IDAgUiAobm9kZTAwMDAwNzM3KSA1MjkgMCBSIChub2RlMDAwMDA3MzgpIDUzMCAwIFIgKG5vZGUwMDAwMDczOSkgNTMxIDAgUiAobm9kZTAwMDAwNzQwKSA1MzIgMCBSIChub2RlMDAwMDA3NDEpIDUzMyAwIFIgKG5vZGUwMDAwMDc0MikgNTM0IDAgUiAobm9kZTAwMDAwNzQ3KSA1MzUgMCBSIChub2RlMDAwMDA3NDgpIDUzNiAwIFIgKG5vZGUwMDAwMDc0OSkgNTM3IDAgUiAobm9kZTAwMDAwNzUxKSA1MzggMCBSIChub2RlMDAwMDA3NTIpIDUzOSAwIFIgKG5vZGUwMDAwMDc1MykgNTQwIDAgUiAobm9kZTAwMDAwNzYyKSA1NDEgMCBSIChub2RlMDAwMDA3NjMpIDU0MiAwIFIgKG5vZGUwMDAwMDc2NCkgNTQzIDAgUiAobm9kZTAwMDAwNzY2KSA1NDQgMCBSIChub2RlMDAwMDA3NjcpIDU0NSAwIFIgKG5vZGUwMDAwMDc2OCkgNTQ2IDAgUiAobm9kZTAwMDAwNzY5KSA1NDcgMCBSIChub2RlMDAwMDA3NzApIDU0OCAwIFIgKG5vZGUwMDAwMDc3MSkgNTQ5IDAgUiAobm9k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b2JqCjw8L1R5cGUgL0ZvbnQKL0ZvbnREZXNjcmlwdG9yIDU4OSAwIFIKL0Jhc2VGb250IC9DQUFBQUErQXJpYWwtQm9sZE1UCi9TdWJ0eXBlIC9DSURGb250VHlwZTIKL0NJRFRvR0lETWFwIC9JZGVudGl0eQovQ0lEU3lzdGVtSW5mbyA8PC9SZWdpc3RyeSAoQWRvYmUpCi9PcmRlcmluZyAoSWRlbnRpdHkpCi9TdXBwbGVtZW50IDA+PgovVyBbMyBbMjc3LjgzMjAzIDAgNDc0LjEyMTA5XSAxMSAxMiAzMzMuMDA3ODEgMTUgWzI3Ny44MzIwMyAzMzMuMDA3ODEgMjc3LjgzMjAzIDI3Ny44MzIwM10gMTkgMjggNTU2LjE1MjM0IDI5IFszMzMuMDA3ODFdIDM2IDM5IDcyMi4xNjc5NyA0MCBbNjY2Ljk5MjE5IDYxMC44Mzk4NCA3NzcuODMyMDMgNzIyLjE2Nzk3IDI3Ny44MzIwMyA1NTYuMTUyMzQgNzIyLjE2Nzk3IDYxMC44Mzk4NCA4MzMuMDA3ODEgNzIyLjE2Nzk3IDc3Ny44MzIwMyA2NjYuOTkyMTkgNzc3LjgzMjAzIDcyMi4xNjc5NyA2NjYuOTkyMTkgNjEwLjgzOTg0IDcyMi4xNjc5NyA2NjYuOTkyMTkgOTQzLjg0NzY2IDAgNjY2Ljk5MjE5XSA2OCBbNTU2LjE1MjM0IDYxMC44Mzk4NCA1NTYuMTUyMzQgNjEwLjgzOTg0IDU1Ni4xNTIzNCAzMzMuMDA3ODEgNjEwLjgzOTg0IDYxMC44Mzk4NCAyNzcuODMyMDMgMCAwIDI3Ny44MzIwMyA4ODkuMTYwMTZdIDgxIDg0IDYxMC44Mzk4NCA4NSBbMzg5LjE2MDE2IDU1Ni4xNTIzNCAzMzMuMDA3ODEgNjEwLjgzOTg0IDU1Ni4xNTIzNCAwIDAgNTU2LjE1MjM0XSAxMzkgWzczNi44MTY0MV1dCi9EVyA3NTA+PgplbmRvYmoKNTkxIDAgb2JqCjw8L0ZpbHRlciAvRmxhdGVEZWNvZGUKL0xlbmd0aCAzMDQ+PiBzdHJlYW0KeJxdkdtqwzAMhu/9FLrsLkoOTboWQqBLKeRiB5btAVJb6QyLYxz3Im8/R0pbmCEx3y/9spCiqj7WRnuIPtwgG/TQaaMcjsPV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km1bFfLaL9ug+WN8LhYhJZzwNHJSONteouvNBUUVh1NDdQqnFmjUv3zGqvMgv3tH7Cyw4ziN6xUlR0LZgVBRECpTqnTTlPcKj4ZJTLQk5UpH0qY5o4a+POHg6V6egg0FM6Zk3LrYUzAvmfJM3y7nebhRwY123Kgo7zqaNb/NytOtBqyLergrr84FY2mb5OjqpTb4WLid7Kpa7y+cn5rwCmVuZHN0cmVhbQplbmRvYmoKMTMgMCBvYmoKPDwvVHlwZSAvRm9udAovU3VidHlwZSAvVHlwZTAKL0Jhc2VGb250IC9FQUFBQUErQXJpYWwtQm9sZEl0YWxpY01UCi9FbmNvZGluZyAvSWRlbnRpdHktSAovRGVzY2VuZGFudEZvbnRzIFs2MDIgMCBSXQovVG9Vbmljb2RlIDYwMyAwIFI+PgplbmRvYmoKeHJlZgowIDYwNAowMDAwMDAwMDAwIDY1NTM1IGYgCjAwMDAwMDAwMTUgMDAwMDAgbiAKMDAwMDAxNDQwMyAwMDAwMCBuIAowMDAwMDAwMzA0IDAwMDAwIG4gCjAwMDAwOTkwNDIgMDAwMDAgbiAKMDAwMDExNTIzMiAwMDAwMCBuIAowMDAwMTM4Njk0IDAwMDAwIG4gCjAwMDAxNTIwMjIgMDAwMDAgbiAKMDAwMDE2NTI2NiAwMDAwMCBuIAowMDAwMTcyNjY2IDAwMDAwIG4gCjAwMDAxOTY0NTAgMDAwMDAgbiAKMDAwMDIwOTE5MSAwMDAwMCBuIAowMDAwMjIwNzYyIDAwMDAwIG4gCjAwMDAyMzg2NzcgMDAwMDAgbiAKMDAwMDAwMDcxMyAwMDAwMCBuIAowMDAwMDAwMzQxIDAwMDAwIG4gCjAwMDAwMDY4ODQgMDAwMDAgbiAKMDAwMDAxNDc0NSAwMDAwMCBuIAowMDAwMDEwOTEzIDAwMDAwIG4gCjAwMDAwMTUwMDkgMDAwMDAgbiAKMDAwMDA4OTMyNyAwMDAwMCBuIAowMDAwMDc1MDEwIDAwMDAwIG4gCjAwMDAwNzQ3ODggMDAwMDAgbiAKMDAwMDAxNTM2OSAwMDAwMCBuIAowMDAwMDE1MjAyIDAwMDAwIG4gCjAwMDAwMTUwNzIgMDAwMDAgbiAKMDAwMDAxOTAxNCAwMDAwMCBuIAowMDAwMDE4ODQ3IDAwMDAwIG4gCjAwMDAwMTg3NDcgMDAwMDAgbiAKMDAwMDAxNTY2NyAwMDAwMCBuIAowMDAwMDE1NTc0IDAwMDAwIG4gCjAwMDAwMTU0NTUgMDAwMDAgbiAKMDAwMDAxODY0NyAwMDAwMCBuIAowMDAwMDE3MDAxIDAwMDAwIG4gCjAwMDAwMTY4OTQgMDAwMDAgbiAKMDAwMDAxNjE4NCAwMDAwMCBuIAowMDAwMDE1ODc5IDAwMDAwIG4gCjAwMDAwMTU3NjAgMDAwMDAgbiAKMDAwMDAxNjA5MSAwMDAwMCBuIAowMDAwMDE1OTcyIDAwMDAwIG4gCjAwMDAwMTY0OTYgMDAwMDAgbiAKMDAwMDAxNjQwMyAwMDAwMCBuIAowMDAwMDE2Mjg0IDAwMDAwIG4gCjAwMDAwMTY4MDEgMDAwMDAgbiAKMDAwMDAxNjcwOCAwMDAwMCBuIAowMDAwMDE2NTg5IDAwMDAwIG4gCjAwMDAwMTg1NTQgMDAwMDAgbiAKMDAwMDAxODQ0NyAwMDAwMCBuIAowMDAwMDE3NTE4IDAwMDAwIG4gCjAwMDAwMTcyMTMgMDAwMDAgbiAKMDAwMDAxNzA5NCAwMDAwMCBuIAowMDAwMDE3NDI1IDAwMDAwIG4gCjAwMDAwMTczMDYgMDAwMDAgbiAKMDAwMDAxODA0MiAwMDAwMCBuIAowMDAwMDE3NzM3IDAwMDAwIG4gCjAwMDAwMTc2MTggMDAwMDAgbiAKMDAwMDAxNzk0OSAwMDAwMCBuIAowMDAwMDE3ODMwIDAwMDAwIG4gCjAwMDAwMTgzNTQgMDAwMDAgbiAKMDAwMDAxODI2MSAwMDAwMCBuIAowMDAwMDE4MTQyIDAwMDAwIG4gCjAwMDAwMjE4NjcgMDAwMDAgbiAKMDAwMDAyMTcwMCAwMDAwMCBuIAowMDAwMDIxNjAwIDAwMDAwIG4gCjAwMDAwMTkzMTMgMDAwMDAgbiAKMDAwMDAxOTIyMCAwMDAwMCBuIAowMDAwMDE5MTAwIDAwMDAwIG4gCjAwMDAwMjE1MDAgMDAwMDAgbiAKMDAwMDAyMDU1OCAwMDAwMCBuIAowMDAwMDIwNDU4IDAwMDAwIG4gCjAwMDAwMTk4MzIgMDAwMDAgbiAKMDAwMDAxOTUyNiAwMDAwMCBuIAowMDAwMDE5NDA2IDAwMDAwIG4gCjAwMDAwMTk3MzkgMDAwMDAgbiAKMDAwMDAxOTYxOSAwMDAwMCBuIAowMDAwMDIwMzU4IDAwMDAwIG4gCjAwMDAwMjAwNTIgMDAwMDAgbiAKMDAwMDAxOTkzMiAwMDAwMCBuIAowMDAwMDIwMjY1IDAwMDAwIG4gCjAwMDAwMjAxNDUgMDAwMDAgbiAKMDAwMDAyMTQwNyAwMDAwMCBuIAowMDAwMDIxMzE4IDAwMDAwIG4gCjAwMDAwMjEyMjUgMDAwMDAgbiAKMDAwMDAyMDc3MSAwMDAwMCBuIAowMDAwMDIwNjUxIDAwMDAwIG4gCjAwMDAwMjEwNTggMDAwMDAgbiAKMDAwMDAyMDkzOCAwMDAwMCBuIAowMDAwMDIzMjcyIDAwMDAwIG4gCjAwMDAwMjMxMDUgMDAwMDAgbiAKMDAwMDAyMzAwNSAwMDAwMCBuIAowMDAwMDIyMTY2IDAwMDAwIG4gCjAwMDAwMjIwNzMgMDAwMDAgbiAKMDAwMDAyMTk1MyAwMDAwMCBuIAowMDAwMDIyODk4IDAwMDAwIG4gCjAwMDAwMjIzNzkgMDAwMDAgbiAKMDAwMDAyMjI1OSAwMDAwMCBuIAowMDAwMDIyNTkyIDAwMDAwIG4gCjAwMDAwMjI0NzIgMDAwMDAgbiAKMDAwMDAyMjgwNSAwMDAwMCBuIAowMDAwMDIyNjg1IDAwMDAwIG4gCjAwMDAwMjQ3ODYgMDAwMDAgbiAKMDAwMDAyNDYxNiAwMDAwMCBuIAowMDAwMDI0NTIwIDAwMDAwIG4gCjAwMDAwMjQ0MDggMDAwMDAgbiAKMDAwMDAyMzQ4MCAwMDAwMCBuIAowMDAwMDIzMzU4IDAwMDAwIG4gCjAwMDAwMjM2OTggMDAwMDAgbiAKMDAwMDAyMzU3NiAwMDAwMCBuIAowMDAwMDI0MzEyIDAwMDAwIG4gCjAwMDAwMjQyMTYgMDAwMDAgbiAKMDAwMDAyMzkxNiAwMDAwMCBuIAowMDAwMDIzNzk0IDAwMDAwIG4gCjAwMDAwMjQxMjcgMDAwMDAgbiAKMDAwMDAyNDAwNSAwMDAwMCBuIAowMDAwMDI2ODcyIDAwMDAwIG4gCjAwMDAwMjY3MDIgMDAwMDAgbiAKMDAwMDAyNjU4MiAwMDAwMCBuIAowMDAwMDI1MDkyIDAwMDAwIG4gCjAwMDAwMjQ5OTYgMDAwMDAgbiAKMDAwMDAyNDg3NCAwMDAwMCBuIAowMDAwMDI1NjI0IDAwMDAwIG4gCjAwMDAwMjUzMTAgMDAwMDAgbiAKMDAwMDAyNTE4OCAwMDAwMCBuIAowMDAwMDI1NTI4IDAwMDAwIG4gCjAwMDAwMjU0MDYgMDAwMDAgbiAKMDAwMDAyNjE2NCAwMDAwMCBuIAowMDAwMDI1ODUwIDAwMDAwIG4gCjAwMDAwMjU3MjggMDAwMDAgbiAKMDAwMDAyNjA2OCAwMDAwMCBuIAowMDAwMDI1OTQ2IDAwMDAwIG4gCjAwMDAwMjY0ODYgMDAwMDAgbiAKMDAwMDAyNjM5MCAwMDAwMCBuIAowMDAwMDI2MjY4IDAwMDAwIG4gCjAwMDAwMjk2MzYgMDAwMDAgbiAKMDAwMDAyOTQ2NiAwMDAwMCBuIAowMDAwMDI5MzQ2IDAwMDAwIG4gCjAwMDAwMjcxNzggMDAwMDAgbiAKMDAwMDAyNzA4MiAwMDAwMCBuIAowMDAwMDI2OTYwIDAwMDAwIG4gCjAwMDAwMjgxNDYgMDAwMDAgbiAKMDAwMDAyNzM5NiAwMDAwMCBuIAowMDAwMDI3Mjc0IDAwMDAwIG4gCjAwMDAwMjc2MTQgMDAwMDAgbiAKMDAwMDAyNzQ5MiAwMDAwMCBuIAowMDAwMDI3ODMyIDAwMDAwIG4gCjAwMDAwMjc3MTAgMDAwMDAgbiAKMDAwMDAyODA1MCAwMDAwMCBuIAowMDAwMDI3OTI4IDAwMDAwIG4gCjAwMDAwMjg3MDIgMDAwMDAgbiAKMDAwMDAyODM4OCAwMDAwMCBuIAowMDAwMDI4MjY2IDAwMDAwIG4gCjAwMDAwMjg2MDYgMDAwMDAgbiAKMDAwMDAyODQ4NCAwMDAwMCBuIAowMDAwMDI5MjQyIDAwMDAwIG4gCjAwMDAwMjg5MjggMDAwMDAgbiAKMDAwMDAyODgwNiAwMDAwMCBuIAowMDAwMDI5MTQ2IDAwMDAwIG4gCjAwMDAwMjkwMjQgMDAwMDAgbiAKMDAwMDAzMjcyMiAwMDAwMCBuIAowMDAwMDMyNTUyIDAwMDAwIG4gCjAwMDAwMzI0MjQgMDAwMDAgbiAKMDAwMDAyOTk0MiAwMDAwMCBuIAowMDAwMDI5ODQ2IDAwMDAwIG4gCjAwMDAwMjk3MjQgMDAwMDAgbiAKMDAwMDAzMDY5MiAwMDAwMCBuIAowMDAwMDMwMTYwIDAwMDAwIG4gCjAwMDAwMzAwMzggMDAwMDAgbiAKMDAwMDAzMDM3OCAwMDAwMCBuIAowMDAwMDMwMjU2IDAwMDAwIG4gCjAwMDAwMzA1OTYgMDAwMDAgbiAKMDAwMDAzMDQ3NCAwMDAwMCBuIAowMDAwMDMxMjQwIDAwMDAwIG4gCjAwMDAwMzA5MjYgMDAwMDAgbiAKMDAwMDAzMDgwNCAwMDAwMCBuIAowMDAwMDMxMTQ0IDAwMDAwIG4gCjAwMDAwMzEwMjIgMDAwMDAgbiAKMDAwMDAzMTc4MCAwMDAwMCBuIAowMDAwMDMxNDY2IDAwMDAwIG4gCjAwMDAwMzEzNDQgMDAwMDAgbiAKMDAwMDAzMTY4NCAwMDAwMCBuIAowMDAwMDMxNTYyIDAwMDAwIG4gCjAwMDAwMzIzMjAgMDAwMDAgbiAKMDAwMDAzMjAwNiAwMDAwMCBuIAowMDAwMDMxODg0IDAwMDAwIG4gCjAwMDAwMzIyMjQgMDAwMDAgbiAKMDAwMDAzMjEwMiAwMDAwMCBuIAowMDAwMDM1MDAwIDAwMDAwIG4gCjAwMDAwMzQ4MzAgMDAwMDAgbiAKMDAwMDAzNDY5NCAwMDAwMCBuIAowMDAwMDMzMDI4IDAwMDAwIG4gCjAwMDAwMzI5MzIgMDAwMDAgbiAKMDAwMDAzMjgxMCAwMDAwMCBuIAowMDAwMDMzMzQyIDAwMDAwIG4gCjAwMDAwMzMyNDYgMDAwMDAgbiAKMDAwMDAzMzEyNCAwMDAwMCBuIAowMDAwMDMzNjU2IDAwMDAwIG4gCjAwMDAwMzM1NjAgMDAwMDAgbiAKMDAwMDAzMzQzOCAwMDAwMCBuIAowMDAwMDMzOTcwIDAwMDAwIG4gCjAwMDAwMzM4NzQgMDAwMDAgbiAKMDAwMDAzMzc1MiAwMDAwMCBuIAowMDAwMDM0Mjg0IDAwMDAwIG4gCjAwMDAwMzQxODggMDAwMDAgbiAKMDAwMDAzNDA2NiAwMDAwMCBuIAowMDAwMDM0NTk4IDAwMDAwIG4gCjAwMDAwMzQ1MDIgMDAwMDAgbiAKMDAwMDAzNDM4MCAwMDAwMCBuIAowMDAwMDQwMTI2IDAwMDAwIG4gCjAwMDAwMzk5NTYgMDAwMDAgbiAKMDAwMDAzOTgxMiAwMDAwMCBuIAowMDAwMDM1MzA2IDAwMDAwIG4gCjAwMDAwMzUyMTAgMDAwMDAgbiAKMDAwMDAzNTA4OCAwMDAwMCBuIAowMDAwMDM1NzUwIDAwMDAwIG4gCjAwMDAwMzU2NDYgMDAwMDAgbiAKMDAwMDAzNTQwMiAwMDAwMCBuIAowMDAwMDM1NTI0IDAwMDAwIG4gCjAwMDAwMzY3NjAgMDAwMDAgbiAKMDAwMDAzNjA5MCAwMDAwMCBuIAowMDAwMDM1ODQ2IDAwMDAwIG4gCjAwMDAwMzU5NjggMDAwMDAgbiAKMDAwMDAzNjMxNiAwMDAwMCBuIAowMDAwMDM2MTk0IDAwMDAwIG4gCjAwMDAwMzY2NTYgMDAwMDAgbiAKMDAwMDAzNjQxMiAwMDAwMCBuIAowMDAwMDM2NTM0IDAwMDAwIG4gCjAwMDAwMzcyMjAgMDAwMDAgbiAKMDAwMDAzNzExNiAwMDAwMCBuIAowMDAwMDM2ODcyIDAwMDAwIG4gCjAwMDAwMzY5OTQgMDAwMDAgbiAKMDAwMDAzODIzMCAwMDAwMCBuIAowMDAwMDM3NTYwIDAwMDAwIG4gCjAwMDAwMzczMTYgMDAwMDAgbiAKMDAwMDAzNzQzOCAwMDAwMCBuIAowMDAwMDM3Nzg2IDAwMDAwIG4gCjAwMDAwMzc2NjQgMDAwMDAgbiAKMDAwMDAzODEyNiAwMDAwMCBuIAowMDAwMDM3ODgyIDAwMDAwIG4gCjAwMDAwMzgwMDQgMDAwMDAgbiAKMDAwMDAzODY5MCAwMDAwMCBuIAowMDAwMDM4NTg2IDAwMDAwIG4gCjAwMDAwMzgzNDIgMDAwMDAgbiAKMDAwMDAzODQ2NCAwMDAwMCBuIAowMDAwMDM5NzAwIDAwMDAwIG4gCjAwMDAwMzkwMzAgMDAwMDAgbiAKMDAwMDAzODc4NiAwMDAwMCBuIAowMDAwMDM4OTA4IDAwMDAwIG4gCjAwMDAwMzkyNTYgMDAwMDAgbiAKMDAwMDAzOTEzNCAwMDAwMCBuIAowMDAwMDM5NTk2IDAwMDAwIG4gCjAwMDAwMzkzNTIgMDAwMDAgbiAKMDAwMDAzOTQ3NCAwMDAwMCBuIAowMDAwMDQzNTQwIDAwMDAwIG4gCjAwMDAwNDMzNzAgMDAwMDAgbiAKMDAwMDA0MzI1OCAwMDAwMCBuIAowMDAwMDQwNDMyIDAwMDAwIG4gCjAwMDAwNDAzMzYgMDAwMDAgbiAKMDAwMDA0MDIxNCAwMDAwMCBuIAowMDAwMDQxOTA0IDAwMDAwIG4gCjAwMDAwNDA2NTAgMDAwMDAgbiAKMDAwMDA0MDUyOCAwMDAwMCBuIAowMDAwMDQwODY4IDAwMDAwIG4gCjAwMDAwNDA3NDYgMDAwMDAgbiAKMDAwMDA0MTA4NiAwMDAwMCBuIAowMDAwMDQwOTY0IDAwMDAwIG4gCjAwMDAwNDEzMDQgMDAwMDAgbiAKMDAwMDA0MTE4MiAwMDAwMCBuIAowMDAwMDQxNTU2IDAwMDAwIG4gCjAwMDAwNDE0MDAgMDAwMDAgbiAKMDAwMDA0MTgwOCAwMDAwMCBuIAowMDAwMDQxNjUyIDAwMDAwIG4gCjAwMDAwNDMxMzAgMDAwMDAgbiAKMDAwMDA0MjE2MiAwMDAwMCBuIAowMDAwMDQyMDQwIDAwMDAwIG4gCjAwMDAwNDIzODAgMDAwMDAgbiAKMDAwMDA0MjI1OCAwMDAwMCBuIAowMDAwMDQyNTk4IDAwMDAwIG4gCjAwMDAwNDI0NzYgMDAwMDAgbiAKMDAwMDA0MjgxNiAwMDAwMCBuIAowMDAwMDQyNjk0IDAwMDAwIG4gCjAwMDAwNDMwMzQgMDAwMDAgbiAKMDAwMDA0MjkxMiAwMDAwMCBuIAowMDAwMDUwNTQ4IDAwMDAwIG4gCjAwMDAwNTAzNzggMDAwMDAgbiAKMDAwMDA1MDIxOCAwMDAwMCBuIAowMDAwMDQzODQ2IDAwMDAwIG4gCjAwMDAwNDM3NTAgMDAwMDAgbiAKMDAwMDA0MzYyOCAwMDAwMCBuIAowMDAwMDQ0MTYwIDAwMDAwIG4gCjAwMDAwNDQwNjQgMDAwMDAgbiAKMDAwMDA0Mzk0MiAwMDAwMCBuIAowMDAwMDQ1MzQ2IDAwMDAwIG4gCjAwMDAwNDQzNzggMDAwMDAgbiAKMDAwMDA0NDI1NiAwMDAwMCBuIAowMDAwMDQ0NTk2IDAwMDAwIG4gCjAwMDAwNDQ0NzQgMDAwMDAgbiAKMDAwMDA0NDgxNCAwMDAwMCBuIAowMDAwMDQ0NjkyIDAwMDAwIG4gCjAwMDAwNDUwMzIgMDAwMDAgbiAKMDAwMDA0NDkxMCAwMDAwMCBuIAowMDAwMDQ1MjUwIDAwMDAwIG4gCjAwMDAwNDUxMjggMDAwMDAgbiAKMDAwMDA0NjY5OSAwMDAwMCBuIAowMDAwMDQ1NTk2IDAwMDAwIG4gCjAwMDAwNDU0NzQgMDAwMDAgbiAKMDAwMDA0NTkzOCAwMDAwMCBuIAowMDAwMDQ1NjkyIDAwMDAwIG4gCjAwMDAwNDU4MTUgMDAwMDAgbiAKMDAwMDA0NjE2NSAwMDAwMCBuIAowMDAwMDQ2MDQyIDAwMDAwIG4gCjAwMDAwNDYzODQgMDAwMDAgbiAKMDAwMDA0NjI2MSAwMDAwMCBuIAowMDAwMDQ2NjAzIDAwMDAwIG4gCjAwMDAwNDY0ODAgMDAwMDAgbiAKMDAwMDA0NzQ4NCAwMDAwMCBuIAowMDAwMDQ2OTUwIDAwMDAwIG4gCjAwMDAwNDY4MjcgMDAwMDAgbiAKMDAwMDA0NzE2OSAwMDAwMCBuIAowMDAwMDQ3MDQ2IDAwMDAwIG4gCjAwMDAwNDczODggMDAwMDAgbiAKMDAwMDA0NzI2NSAwMDAwMCBuIAowMDAwMDQ3ODE1IDAwMDAwIG4gCjAwMDAwNDc3MTkgMDAwMDAgbiAKMDAwMDA0NzU5NiAwMDAwMCBuIAowMDAwMDQ4MTMwIDAwMDAwIG4gCjAwMDAwNDgwMzQgMDAwMDAgbiAKMDAwMDA0NzkxMSAwMDAwMCBuIAowMDAwMDQ5NTQwIDAwMDAwIG4gCjAwMDAwNDgzNDkgMDAwMDAgbiAKMDAwMDA0ODIyNiAwMDAwMCBuIAowMDAwMDQ4NTY4IDAwMDAwIG4gCjAwMDAwNDg0NDUgMDAwMDAgbiAKMDAwMDA0ODc4NyAwMDAwMCBuIAowMDAwMDQ4NjY0IDAwMDAwIG4gCjAwMDAwNDkwMDYgMDAwMDAgbiAKMDAwMDA0ODg4MyAwMDAwMCBuIAowMDAwMDQ5MjI1IDAwMDAwIG4gCjAwMDAwNDkxMDIgMDAwMDAgbiAKMDAwMDA0OTQ0NCAwMDAwMCBuIAowMDAwMDQ5MzIxIDAwMDAwIG4gCjAwMDAwNTAxMTQgMDAwMDAgbiAKMDAwMDA0OTc5OSAwMDAwMCBuIAowMDAwMDQ5Njc2IDAwMDAwIG4gCjAwMDAwNTAwMTggMDAwMDAgbiAKMDAwMDA0OTg5NSAwMDAwMCBuIAowMDAwMDUwOTI5IDAwMDAwIG4gCjAwMDAwNTA3NTkgMDAwMDAgbiAKMDAwMDA1MDYzNiAwMDAwMCBuIAowMDAwMDY3Mzk2IDAwMDAwIG4gCjAwMDAwNjcyMjYgMDAwMDAgbiAKMDAwMDA2NzEwNiAwMDAwMCBuIAowMDAwMDUxODg5IDAwMDAwIG4gCjAwMDAwNTExMzkgMDAwMDAgbiAKMDAwMDA1MTAxNyAwMDAwMCBuIAowMDAwMDUxMzU3IDAwMDAwIG4gCjAwMDAwNTEyMzUgMDAwMDAgbiAKMDAwMDA1MTU3NSAwMDAwMCBuIAowMDAwMDUxNDUzIDAwMDAwIG4gCjAwMDAwNTE3OTMgMDAwMDAgbiAKMDAwMDA1MTY3MSAwMDAwMCBuIAowMDAwMDU5MTAzIDAwMDAwIG4gCjAwMDAwNTQzNjEgMDAwMDAgbiAKMDAwMDA1NDIzMyAwMDAwMCBuIAowMDAwMDUyMzU3IDAwMDAwIG4gCjAwMDAwNTIyNTMgMDAwMDAgbiAKMDAwMDA1MjAwOSAwMDAwMCBuIAowMDAwMDUyMTMxIDAwMDAwIG4gCjAwMDAwNTI4MDEgMDAwMDAgbiAKMDAwMDA1MjY5NyAwMDAwMCBuIAowMDAwMDUyNDUzIDAwMDAwIG4gCjAwMDAwNTI1NzUgMDAwMDAgbiAKMDAwMDA1MzI0NSAwMDAwMCBuIAowMDAwMDUzMTQxIDAwMDAwIG4gCjAwMDAwNTI4OTcgMDAwMDAgbiAKMDAwMDA1MzAxOSAwMDAwMCBuIAowMDAwMDUzNjkxIDAwMDAwIG4gCjAwMDAwNTM1ODcgMDAwMDAgbiAKMDAwMDA1MzM0MSAwMDAwMCBuIAowMDAwMDUzNDY0IDAwMDAwIG4gCjAwMDAwNTQxMzcgMDAwMDAgbiAKMDAwMDA1NDAzMyAwMDAwMCBuIAowMDAwMDUzNzg3IDAwMDAwIG4gCjAwMDAwNTM5MTAgMDAwMDAgbiAKMDAwMDA1NjgxNSAwMDAwMCBuIAowMDAwMDU2Njg3IDAwMDAwIG4gCjAwMDAwNTQ4MDcgMDAwMDAgbiAKMDAwMDA1NDcwMyAwMDAwMCBuIAowMDAwMDU0NDU3IDAwMDAwIG4gCjAwMDAwNTQ1ODAgMDAwMDAgbiAKMDAwMDA1NTI1MyAwMDAwMCBuIAowMDAwMDU1MTQ5IDAwMDAwIG4gCjAwMDAwNTQ5MDMgMDAwMDAgbiAKMDAwMDA1NTAyNiAwMDAwMCBuIAowMDAwMDU1Njk5IDAwMDAwIG4gCjAwMDAwNTU1OTUgMDAwMDAgbiAKMDAwMDA1NTM0OSAwMDAwMCBuIAowMDAwMDU1NDcyIDAwMDAwIG4gCjAwMDAwNTYxNDUgMDAwMDAgbiAKMDAwMDA1NjA0MSAwMDAwMCBuIAowMDAwMDU1Nzk1IDAwMDAwIG4gCjAwMDAwNTU5MTggMDAwMDAgbiAKMDAwMDA1NjU5MSAwMDAwMCBuIAowMDAwMDU2NDg3IDAwMDAwIG4gCjAwMDAwNTYyNDEgMDAwMDAgbiAKMDAwMDA1NjM2NCAwMDAwMCBuIAowMDAwMDU5MDA3IDAwMDAwIG4gCjAwMDAwNTg4NzkgMDAwMDAgbiAKMDAwMDA1NzI2MSAwMDAwMCBuIAowMDAwMDU3MTU3IDAwMDAwIG4gCjAwMDAwNTY5MTEgMDAwMDAgbiAKMDAwMDA1NzAzNCAwMDAwMCBuIAowMDAwMDU3NzA3IDAwMDAwIG4gCjAwMDAwNTc2MDMgMDAwMDAgbiAKMDAwMDA1NzM1NyAwMDAwMCBuIAowMDAwMDU3NDgwIDAwMDAwIG4gCjAwMDAwNTgwMjIgMDAwMDAgbiAKMDAwMDA1NzkyNiAwMDAwMCBuIAowMDAwMDU3ODAzIDAwMDAwIG4gCjAwMDAwNTg0NjggMDAwMDAgbiAKMDAwMDA1ODM2NCAwMDAwMCBuIAowMDAwMDU4MTE4IDAwMDAwIG4gCjAwMDAwNTgyNDEgMDAwMDAgbiAKMDAwMDA1ODc4MyAwMDAwMCBuIAowMDAwMDU4Njg3IDAwMDAwIG4gCjAwMDAwNTg1NjQgMDAwMDAgbiAKMDAwMDA1OTQzNCAwMDAwMCBuIAowMDAwMDU5MzM4IDAwMDAwIG4gCjAwMDAwNTkyMTUgMDAwMDAgbiAKMDAwMDA2NzAxMCAwMDAwMCBuIAowMDAwMDY2NzU0IDAwMDAwIG4gCjAwMDAwNTk3NzYgMDAwMDAgbiAKMDAwMDA1OTUzMCAwMDAwMCBuIAowMDAwMDU5NjUzIDAwMDAwIG4gCjAwMDAwNjAxMjAgMDAwMDAgbiAKMDAwMDA1OTg3NCAwMDAwMCBuIAowMDAwMDU5OTk3IDAwMDAwIG4gCjAwMDAwNjA0NjQgMDAwMDAgbiAKMDAwMDA2MDIxOCAwMDAwMCBuIAowMDAwMDYwMzQxIDAwMDAwIG4gCjAwMDAwNjA4MDggMDAwMDAgbiAKMDAwMDA2MDU2MiAwMDAwMCBuIAowMDAwMDYwNjg1IDAwMDAwIG4gCjAwMDAwNjExNTIgMDAwMDAgbiAKMDAwMDA2MDkwNiAwMDAwMCBuIAowMDAwMDYxMDI5IDAwMDAwIG4gCjAwMDAwNjE0OTYgMDAwMDAgbiAKMDAwMDA2MTI1MCAwMDAwMCBuIAowMDAwMDYxMzczIDAwMDAwIG4gCjAwMDAwNjE4NDAgMDAwMDAgbiAKMDAwMDA2MTU5NCAwMDAwMCBuIAowMDAwMDYxNzE3IDAwMDAwIG4gCjAwMDAwNjIxODQgMDAwMDAgbiAKMDAwMDA2MTkzOCAwMDAwMCBuIAowMDAwMDYyMDYxIDAwMDAwIG4gCjAwMDAwNjI1MjggMDAwMDAgbiAKMDAwMDA2MjI4MiAwMDAwMCBuIAowMDAwMDYyNDA1IDAwMDAwIG4gCjAwMDAwNjI4NzIgMDAwMDAgbiAKMDAwMDA2MjYyNiAwMDAwMCBuIAowMDAwMDYyNzQ5IDAwMDAwIG4gCjAwMDAwNjMyMTYgMDAwMDAgbiAKMDAwMDA2Mjk3MCAwMDAwMCBuIAowMDAwMDYzMDkzIDAwMDAwIG4gCjAwMDAwNjM1NjAgMDAwMDAgbiAKMDAwMDA2MzMxNCAwMDAwMCBuIAowMDAwMDYzNDM3IDAwMDAwIG4gCjAwMDAwNjM5MDQgMDAwMDAgbiAKMDAwMDA2MzY1OCAwMDAwMCBuIAowMDAwMDYzNzgxIDAwMDAwIG4gCjAwMDAwNjQyNDggMDAwMDAgbiAKMDAwMDA2NDAwMiAwMDAwMCBuIAowMDAwMDY0MTI1IDAwMDAwIG4gCjAwMDAwNjQ1OTIgMDAwMDAgbiAKMDAwMDA2NDM0NiAwMDAwMCBuIAowMDAwMDY0NDY5IDAwMDAwIG4gCjAwMDAwNjQ5MzYgMDAwMDAgbiAKMDAwMDA2NDY5MCAwMDAwMCBuIAowMDAwMDY0ODEzIDAwMDAwIG4gCjAwMDAwNjUyODAgMDAwMDAgbiAKMDAwMDA2NTAzNCAwMDAwMCBuIAowMDAwMDY1MTU3IDAwMDAwIG4gCjAwMDAwNjU2MjQgMDAwMDAgbiAKMDAwMDA2NTM3OCAwMDAwMCBuIAowMDAwMDY1NTAxIDAwMDAwIG4gCjAwMDAwNjU5NjggMDAwMDAgbiAKMDAwMDA2NTcyMiAwMDAwMCBuIAowMDAwMDY1ODQ1IDAwMDAwIG4gCjAwMDAwNjYzMTIgMDAwMDAgbiAKMDAwMDA2NjA2NiAwMDAwMCBuIAowMDAwMDY2MTg5IDAwMDAwIG4gCjAwMDAwNjY2NTYgMDAwMDAgbiAKMDAwMDA2NjQxMCAwMDAwMCBuIAowMDAwMDY2NTMzIDAwMDAwIG4gCjAwMDAwNzIwODEgMDAwMDAgbiAKMDAwMDA3MTkxMSAwMDAwMCBuIAowMDAwMDcxNzgzIDAwMDAwIG4gCjAwMDAwNjc3MDMgMDAwMDAgbiAKMDAwMDA2NzYwNyAwMDAwMCBuIAowMDAwMDY3NDg0IDAwMDAwIG4gCjAwMDAwNjg2NzUgMDAwMDAgbiAKMDAwMDA2NzkyMiAwMDAwMCBuIAowMDAwMDY3Nzk5IDAwMDAwIG4gCjAwMDAwNjgxNDEgMDAwMDAgbiAKMDAwMDA2ODAxOCAwMDAwMCBuIAowMDAwMDY4MzYwIDAwMDAwIG4gCjAwMDAwNjgyMzcgMDAwMDAgbiAKMDAwMDA2ODU3OSAwMDAwMCBuIAowMDAwMDY4NDU2IDAwMDAwIG4gCjAwMDAwNjk2NzEgMDAwMDAgbiAKMDAwMDA2ODkxOCAwMDAwMCBuIAowMDAwMDY4Nzk1IDAwMDAwIG4gCjAwMDAwNjkxMzcgMDAwMDAgbiAKMDAwMDA2OTAxNCAwMDAwMCBuIAowMDAwMDY5MzU2IDAwMDAwIG4gCjAwMDAwNjkyMzMgMDAwMDAgbiAKMDAwMDA2OTU3NSAwMDAwMCBuIAowMDAwMDY5NDUyIDAwMDAwIG4gCjAwMDAwNzA2NjcgMDAwMDAgbiAKMDAwMDA2OTkxNCAwMDAwMCBuIAowMDAwMDY5NzkxIDAwMDAwIG4gCjAwMDAwNzAxMzMgMDAwMDAgbiAKMDAwMDA3MDAxMCAwMDAwMCBuIAowMDAwMDcwMzUyIDAwMDAwIG4gCjAwMDAwNzAyMjkgMDAwMDAgbiAKMDAwMDA3MDU3MSAwMDAwMCBuIAowMDAwMDcwNDQ4IDAwMDAwIG4gCjAwMDAwNzE2NjMgMDAwMDAgbiAKMDAwMDA3MDkxMCAwMDAwMCBuIAowMDAwMDcwNzg3IDAwMDAwIG4gCjAwMDAwNzExMjkgMDAwMDAgbiAKMDAwMDA3MTAwNiAwMDAwMCBuIAowMDAwMDcxMzQ4IDAwMDAwIG4gCjAwMDAwNzEyMjUgMDAwMDAgbiAKMDAwMDA3MTU2NyAwMDAwMCBuIAowMDAwMDcxNDQ0IDAwMDAwIG4gCjAwMDAwNzI3NTAgMDAwMDAgbiAKMDAwMDA3MjU4MCAwMDAwMCBuIAowMDAwMDcyNDg0IDAwMDAwIG4gCjAwMDAwNzIzODggMDAwMDAgbiAKMDAwMDA3MjI5MiAwMDAwMCBuIAowMDAwMDcyMTY5IDAwMDAwIG4gCjAwMDAwNzM3MDggMDAwMDAgbiAKMDAwMDA3MzUzOCAwMDAwMCBuIAowMDAwMDczNDQyIDAwMDAwIG4gCjAwMDAwNzMzNDYgMDAwMDAgbiAKMDAwMDA3MzI1MCAwMDAwMCBuIAowMDAwMDczMTYyIDAwMDAwIG4gCjAwMDAwNzMwNjYgMDAwMDAgbiAKMDAwMDA3MjgzOCAwMDAwMCBuIAowMDAwMDc0NzAwIDAwMDAwIG4gCjAwMDAwNzQ1MzAgMDAwMDAgbiAKMDAwMDA3NDQyNiAwMDAwMCBuIAowMDAwMDc0MDE1IDAwMDAwIG4gCjAwMDAwNzM5MTkgMDAwMDAgbiAKMDAwMDA3Mzc5NiAwMDAwMCBuIAowMDAwMDc0MzMwIDAwMDAwIG4gCjAwMDAwNzQyMzQgMDAwMDAgbiAKMDAwMDA3NDExMSAwMDAwMCBuIAowMDAwMDc1MTEzIDAwMDAwIG4gCjAwMDAwNzYwNzAgMDAwMDAgbiAKMDAwMDA3Njg4MSAwMDAwMCBuIAowMDAwMDc2OTQ4IDAwMDAwIG4gCjAwMDAwODkyOTAgMDAwMDAgbiAKMDAwMDA4OTQzNiAwMDAwMCBuIAowMDAwMDg5NjMzIDAwMDAwIG4gCjAwMDAwOTgxMTIgMDAwMDAgbiAKMDAwMDA5ODM1NSAwMDAwMCBuIAowMDAwMDk4Njk4IDAwMDAwIG4gCjAwMDAwOTkxODMgMDAwMDAgbiAKMDAwMDExNDEyNyAwMDAwMCBuIAowMDAwMTE0Mzc1IDAwMDAwIG4gCjAwMDAxMTQ4NzAgMDAwMDAgbiAKMDAwMDExNTM3OCAwMDAwMCBuIAowMDAwMTM3MzIzIDAwMDAwIG4gCjAwMDAxMzc1NzEgMDAwMDAgbiAKMDAwMDEzODMxNCAwMDAwMCBuIAowMDAwMTM4ODQwIDAwMDAwIG4gCjAwMDAxNTA5OTEgMDAwMDAgbiAKMDAwMDE1MTIzOSAwMDAwMCBuIAowMDAwMTUxNjcxIDAwMDAwIG4gCjAwMDAxNTIxNjggMDAwMDAgbiAKMDAwMDE2NDIzOSAwMDAwMCBuIAowMDAwMTY0NDg3IDAwMDAwIG4gCjAwMDAxNjQ5MTIgMDAwMDAgbiAKMDAwMDE2NTQxMiAwMDAwMCBuIAowMDAwMTcxODg5IDAwMDAwIG4gCjAwMDAxNzIxMzIgMDAwMDAgbiAKMDAwMDE3MjM2MiAwMDAwMCBuIAowMDAwMTcyODA3IDAwMDAwIG4gCjAwMDAxOTUwNDYgMDAwMDAgbiAKMDAwMDE5NTI5NCAwMDAwMCBuIAowMDAwMTk2MDc0IDAwMDAwIG4gCjAwMDAxOTY1OTcgMDAwMDAgbiAKMDAwMDIwODE3MSAwMDAwMCBuIAowMDAwMjA4NDE5IDAwMDAwIG4gCjAwMDAyMDg4MTQgMDAwMDAgbiAKMDAwMDIwOTMzOCAwMDAwMCBuIAowMDAwMjE5ODA4IDAwMDAwIG4gCjAwMDAyMjAwNTYgMDAwMDAgbiAKMDAwMDIyMDQxNSAwMDAwMCBuIAowMDAwMjIwOTA5IDAwMDAwIG4gCjAwMDAyMzc0MzUgMDAwMDAgbiAKMDAwMDIzNzY5NCAwMDAwMCBuIAowMDAwMjM4MzAyIDAwMDAwIG4gCnRyYWlsZXIKPDwvU2l6ZSA2MDQKL1Jvb3QgNTYzIDAgUgovSW5mbyAxIDAgUj4+CnN0YXJ0eHJlZgoyMzg4MzAKJSVFT0YK\",\n            \"pdfPath\": \"/opt/Logger/files/CibilReport_dcd8540c-58af-4485-b209-187ca9b7fb59.pdf\",\n            \"client_id\": \"credit_report_cibil_pdf_CoaOahjncpUvlvSudMuD\",\n            \"credit_score\": \"773\",\n            \"credit_report\": null,\n            \"credit_report_link\": \"https://aadhaar-kyc-docs.s3.amazonaws.com/altmobility/credit_report_cibil/credit_report_cibil_pdf_CoaOahjncpUvlvSudMuD/credit_report_1740552406836288.pdf?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=AKIAY5K3QRM5FYWPQJEB%2F20250226%2Fap-south-1%2Fs3%2Faws4_request&X-Amz-Date=20250226T064647Z&X-Amz-Expires=600&X-Amz-SignedHeaders=host&X-Amz-Signature=25027084dca4276c3db510e471719d69f93aed88a74d24b6640978bd063807f7\"\n        },\n        \"success\": \"true\",\n        \"message\": \"Success\",\n        \"status_code\": \"200\",\n        \"message_code\": \"success\"\n    }\n}";//ServiceCallAction.getResultString(requestMap, requestUrl, timeOut);//1.0.0.11
										String serviceOutput = ServiceCallAction.getResultString(requestMap, requestUrl, timeOut);
										 if (serviceOutput != null) {
											// Insert into qt_karza_integration_log table
											Gson gson = new Gson();
											String requestJson = gson.toJson(requestMap);
											String responseJson = serviceOutput;
											log.debug(responseJson);
											String status = "";
											String message = "";
											String ucic="";
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
													status = infoObjLogTable.getString("message");
												}
												
												//status = infoObjLogTable.getString("status") != null ? infoObjLogTable.getString("status") : null;
												
												//message = infoObjLogTable.getString("message") != null ? infoObjLogTable.getString("message") : null;
											}

											//String insertStatus = customerBO.saveKarzaReqLog(customerId, reqType, requestJson, responseJson, status,message, dbConnectionMap);
											//log.debug("insertStatus:" + insertStatus);
											if (!serviceOutput.isEmpty() && !serviceOutput.equalsIgnoreCase(null)
													&& !serviceOutput.equalsIgnoreCase("") && !serviceOutput.startsWith("Error,")) {
												JSONObject jsonObj = new JSONObject(serviceOutput.toString());
												JSONObject errorInfoObj = jsonObj.has("errorInfo") ? jsonObj.getJSONObject("errorInfo") : null;
												JSONObject UCICPayloadObj=jsonObj.has("payload") ? jsonObj.getJSONObject("payload") : null;
												
												String PhotoPath=/*"/App/images/;"*//*"C:\\Logger\\";*/(String)ctx.getAttribute("customerPhoto");
											
												
												 payloadObj = jsonObj.getJSONObject("payload");
												
												// UserMessageText = payloadObj.getString("message");//added by 1.0.0.11
												
												if (errorInfoObj != null && errorInfoObj.getString("status") != null
														&& errorInfoObj.getString("status").equalsIgnoreCase("SUCCESS")) {
													 payloadObj = jsonObj.getJSONObject("payload"); 
													JSONObject headerObj = jsonObj.getJSONObject("header");
												//	JSONObject resultObj = payloadObj.has("result") ? payloadObj.getJSONObject("result") : null;

													 responseStatus = errorInfoObj.getString("status");
													correlationId = headerObj.getString("correlationId");
													String abc="";
;													String responseMessage = errorInfoObj.getString("message");
													String description = errorInfoObj.getString("description");
													
													JSONObject bodyObj = payloadObj.getJSONObject("body");
                                                  if(bodyObj.has("UCIC")) {
													 ucic = bodyObj.getString("UCIC")!=null?bodyObj.getString("UCIC"):"";//bodyObj.getString("UCIC");
													 responseStatus=errorInfoObj.getString("status");
													 responseDescription=errorInfoObj.getString("description");
                                                  }else {
                                                	  responseStatus="FAILURE";
                                                	  responseDescription=errorInfoObj.getString("description");
                                                  }
													
											
													String fileName=null;
													JSONObject jsonObjectrequest = new JSONObject(requestMap);
										            String jsonRequestString = jsonObjectrequest.toString(4);//method pretty-prints the JSON with an indentation of 4 spaces.
										            
										            JSONObject jsonObjectresponse = payloadObj;
										            String jsonResponseString = jsonObjectresponse.toString(4);
										  //saveUcicData=disbursalActionBO.saveUcicData(customerId,reqType,jsonRequestString,jsonResponseString,responseStatus,ucic,responseDescription, dbConnectionMap); // 1.0.0.14 
										            saveUcicData=disbursalActionBO.saveUcicData(customerId,reqType,jsonRequestString,jsonResponseString,responseStatus,ucic,responseDescription, dbConnectionMap, applicant_type, id); // 1.0.0.14 
													 
													
													

													responseMap.put("correlationId", correlationId);
													responseMap.put("requestType", reqType);
													responseMap.put("responseStatus", responseStatus);
													responseMap.put("message", message);
													responseMap.put("description", description);

													//responseMap.put("name", name);
													

												} else {
													 payloadObj = jsonObj.getJSONObject("payload"); 
														JSONObject headerObj = jsonObj.getJSONObject("header");
													
														 responseStatus = errorInfoObj.getString("status");
														correlationId = headerObj.getString("correlationId");
														String responseMessage = errorInfoObj.getString("message");
														String description = errorInfoObj.getString("description");
														
														JSONObject bodyObj = payloadObj.getJSONObject("body");

														 ucic = bodyObj.getString("UCIC")!=null?bodyObj.getString("UCIC"):"";
														
														responseStatus="FAILURE";
														 responseDescription=errorInfoObj.getString("description");
													JSONObject jsonObjectrequest = new JSONObject(requestMap);
										            String jsonRequestString = jsonObjectrequest.toString(4);//method pretty-prints the JSON with an indentation of 4 spaces.
										            
										            JSONObject jsonObjectresponse = payloadObj;
										            String jsonResponseString = jsonObjectresponse.toString(4);
													//saveUcicData=disbursalActionBO.saveUcicData(customerId,reqType,jsonRequestString,jsonResponseString,responseStatus,ucic,responseDescription, dbConnectionMap); // 1.0.0.14 
													saveUcicData=disbursalActionBO.saveUcicData(customerId,reqType,jsonRequestString,jsonResponseString,responseStatus,ucic,responseDescription, dbConnectionMap,applicant_type,id); // 1.0.0.14
													responseMap.put("transId", correlationId);
													responseMap.put("requestType", reqType);
													responseMap.put("responseStatus", "Failure");
													responseMap.put("responseMsg", responseMessage);
													break;
												}
												

											} else {
												JSONObject jsonObjectrequest = new JSONObject(requestMap);
									            String jsonRequestString = jsonObjectrequest.toString(4);//method pretty-prints the JSON with an indentation of 4 spaces.
									            responseStatus="FAILURE";
												responseMap.put("transId", correlationId);
												responseMap.put("requestType", reqType);
												responseMap.put("responseStatus", "Failure");
												responseMap.put("responseMsg", "Response not generated, Please try again.");
												//saveUcicData=disbursalActionBO.saveUcicData(customerId,reqType,jsonRequestString,"",responseStatus,"",responseDescription, dbConnectionMap); // 1.0.0.14
												saveUcicData=disbursalActionBO.saveUcicData(customerId,reqType,jsonRequestString,"",responseStatus,"",responseDescription, dbConnectionMap,applicant_type,id); // 1.0.0.14
												log.info("UCIC Response not generated, Please try again.");
												break;
											}
										}

									} catch (Exception ex) {
										responseMap.put("responseStatus", "Failure");
										responseMap.put("responseMsg", "Please try again, Connect to Admin Team");
										log.info("Exception:" + ex.getMessage());
									}
									
									
					        }else {
					        	log.info("Api not called for customer:" +full_name);
					        }
								//API call end
					        }
					        responseMap.put("isApiCalled", isApiCalled);
					    }

					} 
					
					log.info("createUpdateUcic - end");
					return responseMap;
					
				}
				
				
				//end 1.0.0.11

	// 1.0.0.15 Start - Vehicle RC Authentication
	public void vehicleRCApiCall(Map dbConnectionMapLMS) {
		log.info("vehicleRCApiCall - Start");
		InvoiceDetailsBO invoiceDetailsBo = new InvoiceDetailsBO();
		try {
			Map pendingListMap = invoiceDetailsBo.getPendingVehicleRCList(dbConnectionMapLMS);
			ArrayList dataList = (ArrayList) pendingListMap.get("PCUR_VEHICLE_RC_DTLS");

			if (dataList == null || dataList.isEmpty()) {
				log.info("vehicleRCApiCall - No pending Vehicle RC requests.");
				return;
			}

			log.info("vehicleRCApiCall - Total pending records: " + dataList.size());

			String requestUrl   = commonApplicationResource.getString("VEHICLE_RC_URL");
			String msgVersion   = commonApplicationResource.getString("VEHICLE_RC_MSG_VERSION");
			String appId        = commonApplicationResource.getString("VEHICLE_RC_APP_ID");
			String token        = commonApplicationResource.getString("VEHICLE_RC_TOKEN");
			String consent      = commonApplicationResource.getString("VEHICLE_RC_CONSENT");
			String version      = commonApplicationResource.getString("VEHICLE_RC_VERSION");
			String corrIdPrefix = commonApplicationResource.getString("VEHICLE_RC_CORRELATION_ID_PREFIX");
			int timeOut         = Integer.parseInt(commonApplicationResource.getString("API_SERVICE_LOGOUT_IN_SEC"));

			for (int i = 0; i < dataList.size(); i++) {
				Map resultSetMap = (Map) dataList.get(i);
				invoiceDetailsBo.processVehicleRCRecord(resultSetMap, requestUrl, msgVersion, appId,
						token, consent, version, corrIdPrefix, timeOut, dbConnectionMapLMS);
			}

		} catch (Exception e) {
			log.error("vehicleRCApiCall - Exception: " + e.getMessage());
		}
		log.info("vehicleRCApiCall - End");
	}
	// 1.0.0.15 End - Vehicle RC Authentication

}
