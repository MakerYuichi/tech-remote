<!-- 
+  **********************************************************************************************************************************
+  VERSION NO   UPDATED BY       UPDATED ON      REASON FOR CHANGE
+  2.0.0.0.1       Apurva Shukla  30-jan-2018     for adding co-lesse functionality in dm module
+  2.0.0.0.2       Apurva Shukla  31-jan-2018     bugId 52214
+  2.0.0.0.3      Apurva Shukla   2 feb 2018     new radio is not required in dm
+  2.0.0.0.4      Apurva Shukla   19 feb 2018   mandatory to check existing checkbox in dm when adding co-lesse
+  2.0.0.0.5      Apurva Shukla   8th march 2018  bugid 53949
+  1.0.0.0      Apurva Shukla      06th june 2018  making product dependent on lob in customer module
+   1.0.0.1      Apurva Shukla     12th june 2018  57061
+  1.0.0.2      Apurva shukla      27 june 2018    identification information should be hide in case of none indivisual
+  1.0.0.3      Apurva shukla      04 july 2018     pincode disabled on page load
+  1.0.0.4      Apurva shukla      25 july 2018     companyPan mandatory in case of non indivisual
+	1.0.0.5		Sunny Pathak		10-july-2020	changes done for apostrophe issue
+	1.0.0.6		Shubham Sharma		15-mar-2021		Same as made mandatory for POS and Billing Address.
+	1.0.0.7		Priyanka Soni		26-Apr-2022		The first row of KEY CONTACTS should not be freeze.
+  1.0.0.8		Priyanka Soni 	 10-Jun-2022	 CR- Storing Grace Period for Customer Asset Category Wise.
+  1.0.0.9		Priyanka Soni 	 23-Jun-2022	 CR- DM and DM PDE applicant screen grace period should be disabled
+  1.0.0.10		Hemant Kumar 	 24-DEC-2022	 CR- kyc compliance
+  1.0.0.11			Priyanka Soni 	 	08-Apr-2023	  	Dedupe of authorised signatory and beneficiary owner should be mandatory process
+  1.0.0.12		Hemant Kumar 	 10-APR-2023	 Email ID of BO issue
+  1.0.0.13     Narottam Biswal  06-OCT-2023     Bug 125536: Error in Initiating Dedupe(auth Signatory address length restricted to 50)
+  1.0.0.14   Tanisha Agarwal    18-Jan-2024      Auto populated drop down in LOB of key contacts
+  1.0.0.15   Tanisha Agarwal    08-Feb-2024       Tan no. must be specified in non individual
+  1.0.0.16	  Nalin Kumar Jena    24-APR-2024	 	CR- Credit Ratings And parameters 
+  1.0.0.17      Tanisha Agarwal 19-JUNE-2024     DM PDE applicant need to be revisited for validation
+   1.0.0.18      Tanisha Agarwal       17-Dec-2024            CIBIL NEW CHANGES
+  1.0.0.19   Tanisha Agarwal    14-APR-2025       DATE OF INCORP. must be specified in non individual
+   1.0.0.20	  Narottam Biswal		04-Apr-2025	           Cibil Integration
+   1.0.0.21	Ravi Shankar          10-Apr-2026            GST Registration Status
+  1.0.0.22      Nalin Kumar Jena      18-Jun-2026            TDS deduction in Processing Fee paid by customer
+  1.0.0.23	    Ahtesham Husain       10-Aug-2026	         165920 After hit on Registration Form screen is blank.
 -->
 

<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@page import="qc.sso.dto.UserInfoDTO"%>
<%@page import="qc.llmhome.dto.ActivityInfoDTO"%>
<%@page import="qc.llm.dto.*"%>
<%@ include file="../include/includeTld.inc"%>
<%ArrayList assetCatGpDtls = request.getAttribute("assetCatGpList") != null ? (ArrayList)request.getAttribute("assetCatGpList") : null; //1.0.0.8
String activityType = ""+((ActivityInfoDTO)session.getAttribute("activityInfo")).getActivityType();
//out.print(activityType); 
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<title>miFIN</title>
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link href="css/bootstrap.css" rel="stylesheet" type="text/css">
<link href="css/font-awesome.css" rel="stylesheet">
<link href="css/newlos.css" rel="stylesheet" type="text/css">
<link href="css/responsive.css" rel="stylesheet">
<style>
.col-lg-3.col-md-3.col-sm-6.col-xs-6.pdnglt {
	padding-left: 107px !important;
}
</style>

<!--[if lt IE 9]>
		<link rel="stylesheet" type="text/css" href="css/ie8.css" media="screen">
		<![endif]-->
<!--[if lt IE 8]>
		<link rel="stylesheet" type="text/css" href="css/ie7.css" media="screen">
		<![endif]-->
<!--[if lt IE 9]>
		<script src="js/html5shiv.min.js"></script>
		<![endif]-->


<script type="text/javascript" src="js/validation.js"></script>
<script type="text/javascript" src="js/CalendarPopup.js"></script>
<script type="text/javascript" src="js/checkSessionAvailability.js"></script>
<script type="text/javascript" src="js/PopupWindow.js"></script>
<script type="text/javascript" src="js/date.js"></script>
<script type="text/javascript" src="js/AnchorPosition.js"></script>
<script type="text/javascript" src="js/genericValidation.js"></script>
<script type="text/javascript" src="js/jsFunction.js"></script>
<script type="text/javascript" src="js/personalInfo.js"></script>
<script type="text/javascript" src="js/newProspect.js"></script>
<script type="text/javascript" src="js/dateValidation.js"></script>
<script type="text/javascript" src="js/personalinfoNew.js"></script>
<script type="text/javascript" src="js/genericValidationlos.js"></script>


<script type="text/javascript">
	
		 var screenId_temp='<%=request.getParameter("screenId")%>';
                        var mode_temp='<%=request.getParameter("mode")%>';
		 	
		   function disableElements()
		   {
		       
		   		var applicantType = document.forms[0].applicantType.value;
		   		emptyPersonalData();
		   		
		   		enableAllElements();
		   		
		   		disableAllColors();
		   		  if(document.forms[0].custEntityType.value=="1000000001")
		   		  {
		   		     document.forms[0].firstName.style.backgroundColor="#ebf3ff";
   		  			 document.forms[0].lastName.style.backgroundColor="#ebf3ff";
   		  		  }
		   		document.forms[0].applicantType.value = applicantType; 
		   		changeCustomerEntityType();
		   		for(var i=0;i<validationData.length;i++)
		   		{
		   			var applicantType = document.forms[0].applicantType.value;
		   			if(Number(applicantType) == Number(validationData[i][3]))
		   			{
		   				if(validationData[i][4]=="VIEW")
		   				{
		   					if(validationData[i][5]=="A")
		   					{
		   						try
		   						{
		   						
		   							key = validationData[i][1];
		   							screenCheck = validationData[i][10];
		   							if(screenCheck=="P")
								       {
			   							 document.getElementById(key).disabled=true;
			   							 <logic:notEmpty name="newDetail">
			   							 	document.forms[0].applicantType.disabled=false;
			   							 </logic:notEmpty>
			   							}
		   						}
		   						catch(err)
		   						{
		   						
		   						}
		   					}
		   					
		   				}
		   				else if(validationData[i][4]=="MANDATORY")
		   				{
		   					if(validationData[i][5]=="A")
		   					{
		   						try
		   						{
		   							key = validationData[i][1];
		   							screenCheck = validationData[i][10];
		   							if(screenCheck=="P")
								    {
		   							 document.getElementById(key).style.backgroundColor="#ebf3ff";
		   							}
		   						}
		   						catch(err)
		   						{
		   						
		   						}
		   					}
		   				}
		   				
		   			}
		   			else
		   			{
		   			   if(document.forms[0].custEntityType.value=="1000000002")
		   		       {
		   		           if(validationData[i][4]=="VIEW")
		   				{
		   					if(validationData[i][5]=="A")
		   					{
		   						try
		   						{
		   						
		   							key = validationData[i][1];
		   							screenCheck = validationData[i][10];
		   							if(screenCheck=="P")
								       {
				   							 document.getElementById(key).disabled=true;
				   							 <logic:notEmpty name="newDetail">
				   							 	document.forms[0].applicantType.disabled=false;
				   							 </logic:notEmpty>
				   					}
		   						}
		   						catch(err)
		   						{
		   						
		   						}
		   					}
		   					
		   				}
		   				else if(validationData[i][4]=="MANDATORY")
		   				{
		   					if(validationData[i][5]=="A")
		   					{
		   						try
		   						{
		   							key = validationData[i][1];
		   							screenCheck = validationData[i][10];
		   							if(screenCheck=="P")
								     {
		   							 	document.getElementById(key).style.backgroundColor="#ebf3ff";
		   							 }
		   							
		   						}
		   						catch(err)
		   						{
		   						
		   						}
		   					}
		   				}
		   		       
		   		       
		   		       }
		   			   
		   			}
		   			<logic:equal name="applicantInfo" property="applicantId" value="0">
					if(document.forms[0].btnPrint)
					document.forms[0].btnPrint.disabled=true;
			         </logic:equal>
		   		}
		   		
		    	document.forms[0].applicantType.style.backgroundColor="#ebf3ff";
		    	document.forms[0].custEntityType.style.backgroundColor="#ebf3ff";
		    	document.forms[0].add.style.backgroundColor="#1a6fe1";
		    	document.forms[0].addAddressButton.style.backgroundColor="#1a6fe1";
		    	if(document.forms[0].addSign)
		    	document.forms[0].addSign.style.backgroundColor="#1a6fe1";
		    	document.forms[0].btnState.style.backgroundColor="#1a6fe1";
		    	document.forms[0].get.style.backgroundColor="#1a6fe1";
		    	document.forms[0].reset.style.backgroundColor="#1a6fe1";
		    	document.forms[0].search.style.backgroundColor="#1a6fe1";
		    	document.forms[0].btnCity.style.backgroundColor="#1a6fe1";
		    	document.forms[0].btnzipPin.style.backgroundColor="#1a6fe1";
		    	document.forms[0].remove.style.backgroundColor="#1a6fe1";
		    	document.forms[0].cancel.style.backgroundColor="#1a6fe1";
		    	if(document.forms[0].btnPrint)
		    	document.forms[0].btnPrint.style.backgroundColor="#1a6fe1";
		    	if(document.forms[0].btnCapture)
		    	document.forms[0].btnCapture.style.backgroundColor="#1a6fe1";
		    	
		    	document.forms[0].relation.disabled=false;
		    	applyReadOnlyRules(document,"A");
		    	if(document.forms[0].applicantType.value=="1000000001" || document.forms[0].applicantType.value=="1000000002")
				{
					if(document.forms[0].addressType.value=="1000000001")
					{
						
						document.forms[0].residenceStatus.style.backgroundColor="#ebf3ff";
						document.forms[0].noOfYearAtResidence.style.backgroundColor="#ebf3ff";
						document.forms[0].noOfMonthAtResidence.style.backgroundColor="#ebf3ff";
					}
					else
					{
						document.forms[0].residenceStatus.style.backgroundColor="#ffffff";
						document.forms[0].noOfYearAtResidence.style.backgroundColor="#ffffff";
						document.forms[0].noOfMonthAtResidence.style.backgroundColor="#ffffff";
					}
				}
				<logic:equal name="applicantInfo" property="applicantId" value="0">
					if(document.forms[0].btnPrint)
					document.forms[0].btnPrint.disabled=true;
					if(document.forms[0].btnCapture)
					document.forms[0].btnCapture.disabled=true;
			</logic:equal>
				cancelNewAddress();
				
				
		    }
  
	
	function changeCustomerEntityType()
	{
	
			
       if(document.forms[0].custEntityType.value=="1000000001" || document.forms[0].custEntityType.value=="1000000004")
	   {	
	   		var btn = document.forms[0].addAddressButton;
	   		btn.click();
	   		saveAddressInfo="Y";
	      document.getElementById("divCustEntityTypeInd").style.display="";
	      document.getElementById("divCustEntityTypeCorp").style.display="none";
	      document.getElementById("divCustEntityTypeGroup").style.display="none";
	      document.getElementById("authorisedSignatoryDiv").style.display="none";
	      
	      document.getElementById("divCommunicationInformation").style.display="block";
	      
	      
	      /*  <logic:notEmpty name="relationFlag">
		 <logic:equal name="relationFlag" value="Y">
	      document.getElementById("td1").style.display="block";
	      document.getElementById("td2").style.display="block";
	      document.getElementById("td3").style.display="block";
	     
	      </logic:equal>
	      </logic:notEmpty> */
			
			
	      disablePersonalFieldCorp();
	      disablePersonalFieldGroup();
	      
	      <logic:notEqual name="activityInfo" property="activityType" value="PDE">
	      	enablePersonalFieldInd();
	      </logic:notEqual>
	      
	           
   		  document.forms[0].firstName.style.backgroundColor="#ebf3ff";
   		  document.forms[0].lastName.style.backgroundColor="#ebf3ff";
   		  
	      
	      enableReadonlyPersonalFieldInd();
	      document.getElementById("qual").style.display="block";
	   }
	   else if(document.forms[0].custEntityType.value=="1000000002")
	   { 
	   		var btn = document.forms[0].addAddressButton;
	   		btn.click();
	   		saveAddressInfo="Y";
	   		
	   		document.getElementById("identificationId").style.display="none";/* 1.0.0.2 */
	      document.getElementById("divCustEntityTypeCorp").style.display="";
	      document.getElementById("divCustEntityTypeInd").style.display="none";
	      document.getElementById("divCustEntityTypeGroup").style.display="none";
	      document.getElementById("divCommunicationInformation").style.display="none";
	     /*  <logic:notEmpty name="relationFlag">
			<logic:equal name="relationFlag" value="Y">
	     
	       document.getElementById("td1").style.display="block";
	       document.getElementById("td2").style.display="none";
	       document.getElementById("td3").style.display="none";
	      
	      </logic:equal>
	      </logic:notEmpty> */
	      <logic:notEqual name="activityInfo" property="activityType" value="PDE">
	     	 enablePersonalFieldCorp();
	     	 document.forms[0].nameOfCompany.disabled=false;
	      </logic:notEqual>
	      
	    
	      disablePersonalFieldInd();
	
	      disablePersonalFieldGroup();
	     
	    
	      <logic:equal name="activityInfo" property="activityType" value="PDE">
	     	document.forms[0].rbiRegNo.disabled=true;
	     	document.forms[0].keyContactPerson.disabled=true;
	     	document.forms[0].authCapital.disabled=true;
	     	document.forms[0].issuedCap.disabled=true;
	     	document.forms[0].legalFormType.disabled=true;
	     	document.forms[0].legalForm.disabled=true;
	     	document.forms[0].boardRepresentative.disabled=true;
	     	document.forms[0].tin.disabled=true;
	     		     	
	     	document.forms[0].nameOfCompany.disabled=false;
	      </logic:equal>
	    
	      
   		  document.forms[0].nameOfCompany.style.backgroundColor="#ebf3ff";
   		      document.getElementById("qual").style.display="none";
	 
	   }
	   else if(document.forms[0].custEntityType.value=="1000000003")
	   { 
	      document.getElementById("divCustEntityTypeGroup").style.display="";
	      document.getElementById("divCustEntityTypeCorp").style.display="none";
	      document.getElementById("divCustEntityTypeInd").style.display="none";
	      document.getElementById("divCommunicationInformation").style.display="none";
	      
	      <logic:notEqual name="activityInfo" property="activityType" value="PDE">
	     	 enablePersonalFieldGroup();
	      </logic:notEqual>
	      
	      disablePersonalFieldInd();
	      disablePersonalFieldCorp();
	     
   		  document.getElementById("nameOfGroup").style.backgroundColor="#ebf3ff";
   		 	      document.getElementById("qual").style.display="none";
	 
	   }
	
	}
	var authSignRowIndex = 0;
var authSignRowCount = 0;
var availableauthSignRows="";
var authDedupViewFlg = "N";
function addAuthorisedSignatoryRow(objId,objfName,objmName,objlName,objDesignation,objDinNo,objMAilId,objContactNo,objDelegation,objDedCustId,objDedSearchId,objPan,objAddress,objStateId,objStateName,objCityId,objCityName,objsignDedInitFlag,objGender,objDob,objPincodeId,objPincodeName) //1.0.0.11
{

		var obidarr = new Array();
		var fnamearr = new Array();
		var mnamearr = new Array();
		var lnamearr = new Array();
		var desigarr = new Array();
		var dinarr = new Array();
		var emailarr = new Array();
		var cnumarr = new Array();
		var delegarr = new Array();
		//start 1.0.0.10
		var dedCustIdArr = new Array();
		var dedSearchIdArr = new Array();
		var panNoArr = new Array();
		var addressArr = new Array();
		var stateIdArr = new Array();
		var cityIdArr = new Array();
		var stateNameArr = new Array();
		var cityNameArr = new Array();
		//End 1.0.0.10
		var signDedInitFlagArr = new Array(); //1.0.0.11
		var GenderArr = new Array(); //1.0.0.21
		var DobArr = new Array(); //1.0.0.21
		var PincodeArr = new Array(); //1.0.0.21
		var PincodeIdArr = new Array(); //1.0.0.21
		var authorisedRowRowHtml = "";
		//authorisedRowRowHtml = authorisedRowRowHtml + "<div id='authorisedSignrowtable"+authSignRowIndex+"' style='border:none; text-align:left;'>"; //1.0.0.11 commented
		//authorisedRowRowHtml = authorisedRowRowHtml + "<table  width='100%' class='main_body'>"; //1.0.0.11 commented
		authorisedRowRowHtml = authorisedRowRowHtml + "<tr id='authorisedSignrowtable"+authSignRowIndex+"' >"; //1.0.0.11
		
	<logic:notEqual name="activityInfo" property="activityType" value="PDE">	
		authorisedRowRowHtml = authorisedRowRowHtml + "<td  width='2%' align='left'>";
			authorisedRowRowHtml = authorisedRowRowHtml + "<input type='checkbox' id='AuthSignCheckbox"+authSignRowIndex+"' value='"+authSignRowIndex+"' onclick='toggleAuthSignRow(this);'/>";
			
			authorisedRowRowHtml = authorisedRowRowHtml + "</td>";
			
	</logic:notEqual>	
		


	authorisedRowRowHtml = authorisedRowRowHtml + "<td width='8%' align='left' id='authSignViewDedupTd"+authSignRowIndex+"'><span id='authSignViewDedupTdSpanId"+authSignRowIndex+"'></span>";
	//1.0.0.11
	authorisedRowRowHtml = authorisedRowRowHtml + "<span align='left' id='authSignDedupInitFlagTd"+authSignRowIndex+"'>";
	authorisedRowRowHtml = authorisedRowRowHtml + "&nbsp;&nbsp;"+objsignDedInitFlag+"&nbsp;&nbsp;";
	authorisedRowRowHtml = authorisedRowRowHtml + "</span>";
	//1.0.0.11
	authorisedRowRowHtml = authorisedRowRowHtml + "<input type='button' class='blueBotton' name='authSignDedupeInitViewBtn"+authSignRowIndex+"' id='authSignDedupeInitViewBtn"+authSignRowIndex+"' value='View' onclick=\"javascript:appDedupeInitView('AUTH SIGNATORY',"+authSignRowIndex+");\" />";
	authorisedRowRowHtml = authorisedRowRowHtml + "</td>";
	
		
		authorisedRowRowHtml = authorisedRowRowHtml + "<td width='8%' align='left'>";
		authorisedRowRowHtml = authorisedRowRowHtml + "<input type='hidden' id='authSignId"+authSignRowIndex+"' value='"+objId+"' /><input type='hidden' id='authSignDedCustId"+authSignRowIndex+"' value='"+objDedCustId+"' /><input type='hidden' id='authSignDedSearchId"+authSignRowIndex+"' value='"+objDedSearchId+"' />";
		authorisedRowRowHtml = authorisedRowRowHtml + "<input type='text' size='14' maxlength='50' id='authSignFName"+authSignRowIndex+"' style='width:110 px;' value='"+objfName+"'  />";//1.0.0.12
		authorisedRowRowHtml = authorisedRowRowHtml + "</td>";
		
		authorisedRowRowHtml = authorisedRowRowHtml + "<td width='8%' align='left'>";
		authorisedRowRowHtml = authorisedRowRowHtml + "<input type='text' size='14' maxlength='50' id='authSignMName"+authSignRowIndex+"' style='width:110 px;' value='"+objmName+"'  />";//1.0.0.12
		authorisedRowRowHtml = authorisedRowRowHtml + "</td>";
		
		
		authorisedRowRowHtml = authorisedRowRowHtml + "<td width='8%' align='left'>";
		authorisedRowRowHtml = authorisedRowRowHtml + "<input type='text' size='14' maxlength='50' id='authSignLName"+authSignRowIndex+"' style='width:110 px;' value='"+objlName+"'  />";//1.0.0.12
		authorisedRowRowHtml = authorisedRowRowHtml + "</td>";
		
		authorisedRowRowHtml = authorisedRowRowHtml + "<td width='5%' align='left'>";
		authorisedRowRowHtml = authorisedRowRowHtml + "<select id='authSignGender"+authSignRowIndex+"' style='width:110 px;' value='"+objGender+"' ><option value='0'>SELECT</option>";
		for (var i=0; i<genderTypeArr.length; i++) {
			authorisedRowRowHtml = authorisedRowRowHtml + "<option value='"+genderTypeArr[i][0]+"'>"+genderTypeArr[i][1]+"</option>";
		}
		authorisedRowRowHtml = authorisedRowRowHtml + "</select></td>";//1.0.0.21
		
		authorisedRowRowHtml = authorisedRowRowHtml + "<td width='6%' align='left'>";
		authorisedRowRowHtml = authorisedRowRowHtml + "<input type='text' name='authDate' id='authSignDob"+authSignRowIndex+"' style='width:90px; vertical-align: middle; background: rgb(235, 243, 255);' maxlength='11' onblur='checkDateFormat(this,\"Auth Date\");' /><a href='#' style='text-decoration: none' onClick='cal19.select(document.getElementById(\"authSignDob"+authSignRowIndex+"\"),\"authSignDob_link\",\"dd-NNN-yyyy\"); return false;' NAME='authSignDob_link' ID='authSignDob_link' title='Calendar'> <i class='fa fa-calendar' aria-hidden='true'></i> </a>";
		authorisedRowRowHtml = authorisedRowRowHtml + "</td>";//1.0.0.21
		
		
		
		
		authorisedRowRowHtml = authorisedRowRowHtml + "<td width='8%' align='left'>";
		authorisedRowRowHtml = authorisedRowRowHtml + "<select   id='authSignDesignation"+authSignRowIndex+"' style='width:110 px;' onchange=designationChange('"+authSignRowIndex+"') /><option value='0'>SELECT</option>";
		authorisedRowRowHtml = authorisedRowRowHtml + "</td>";
		
		authorisedRowRowHtml = authorisedRowRowHtml + "<td width='8%' align='left'>";
		authorisedRowRowHtml = authorisedRowRowHtml + "<input type='text' size='14' maxlength='20' id='authSignDinNo"+authSignRowIndex+"' style='width:110 px;' value='"+objDinNo+"' disabled='true'  />";
		authorisedRowRowHtml = authorisedRowRowHtml + "</td>";
		
		authorisedRowRowHtml = authorisedRowRowHtml + "<td width='8%' align='left'>";
		authorisedRowRowHtml = authorisedRowRowHtml + "<input type='text' size='14' maxlength='50' id='authSignMailId"+authSignRowIndex+"' style='width:110 px;' value='"+objMAilId+"' />";
		authorisedRowRowHtml = authorisedRowRowHtml + "</td>";
		
		authorisedRowRowHtml = authorisedRowRowHtml + "<td width='8%' align='left'>";
		authorisedRowRowHtml = authorisedRowRowHtml + "<input type='text' size='14' maxlength='10' id='authSignPan"+authSignRowIndex+"' style='width:110 px;' value='"+objPan+"' />";
		authorisedRowRowHtml = authorisedRowRowHtml + "</td>";
		
		
		authorisedRowRowHtml = authorisedRowRowHtml + "<td width='8%' align='left'>";
		authorisedRowRowHtml = authorisedRowRowHtml + "<input type='text' size='14' maxlength='10' id='authSignContactNo"+authSignRowIndex+"' style='width:110 px;' value='"+objContactNo+"'  />";
		authorisedRowRowHtml = authorisedRowRowHtml + "</td>";
		
		//Start 1.0.0.10
		authorisedRowRowHtml = authorisedRowRowHtml + "<td width='8%' align='left'>";
		authorisedRowRowHtml = authorisedRowRowHtml + "<input type='text' maxlength='50' id='authSignAddress"+authSignRowIndex+"' style='width:110 px;' value='"+objAddress+"'  />"; //1.0.0.13
		authorisedRowRowHtml = authorisedRowRowHtml + "</td>";
		
		authorisedRowRowHtml = authorisedRowRowHtml + "<td width='8%' align='left'>";
		authorisedRowRowHtml = authorisedRowRowHtml + "<input type='hidden' id='authSignState"+authSignRowIndex+"' name='authSignState"+authSignRowIndex+"' value='"+objStateId+"' /><input type='text' size='14' maxlength='50' id='authSignState"+authSignRowIndex+"_temp' name='authSignState"+authSignRowIndex+"_temp' style='width:110 px;' value='"+objStateName+"' disabled='true' /><input type='button' class='blueBotton' name='btnState' value='...' onclick=\"javascript:authSignStateChooser('QM_STATEMASTER','"+authSignRowIndex+"');\">";
		authorisedRowRowHtml = authorisedRowRowHtml + "</td>";
		
		authorisedRowRowHtml = authorisedRowRowHtml + "<td width='8%' align='left'>";
		authorisedRowRowHtml = authorisedRowRowHtml + "<input type='hidden' id='authSignCity"+authSignRowIndex+"' name='authSignCity"+authSignRowIndex+"' value='"+objCityId+"' /><input type='text' size='14' maxlength='50' id='authSignCity"+authSignRowIndex+"_temp' name='authSignCity"+authSignRowIndex+"_temp' style='width:110 px;' value='"+objCityName+"' disabled='true' /><input type='button' class='blueBotton' name='btnCity' value='...' onclick=\"javascript:authSignCityChooser('QM_CITYMASTER','"+authSignRowIndex+"');\">";
		authorisedRowRowHtml = authorisedRowRowHtml + "</td>";

		//End 1.0.0.10
		
		authorisedRowRowHtml = authorisedRowRowHtml + "<td width='5%' align='left'>";//1.0.0.21
		authorisedRowRowHtml = authorisedRowRowHtml + "<input type='hidden' id='authSignPinCode"+authSignRowIndex+"' name='authSignPinCode"+authSignRowIndex+"' value='"+objPincodeId+"' /><input type='text' size='14' maxlength='50' id='authSignPinCode"+authSignRowIndex+"_temp' name='authSignPinCode"+authSignRowIndex+"_temp' style='width:110 px;' value='"+objPincodeName+"' disabled='true' /><input type='button' class='blueBotton' name='btnPinCode' value='...' onclick=\"javascript:authSignPincodeChooser('QM_PINCODEMASTER','"+authSignRowIndex+"');\">";
		authorisedRowRowHtml = authorisedRowRowHtml + "</td>";//1.0.0.21
		
		authorisedRowRowHtml = authorisedRowRowHtml + "<td width='8%' align='left'>";
		authorisedRowRowHtml = authorisedRowRowHtml + "<input type='text' size='14' maxlength='50' id='authSignDelegation"+authSignRowIndex+"' style='width:110 px;' value='"+objDelegation+"'  />";
		authorisedRowRowHtml = authorisedRowRowHtml + "</td>";

		authorisedRowRowHtml = authorisedRowRowHtml + "</tr>";
		//authorisedRowRowHtml = authorisedRowRowHtml + "</table>"; //1.0.0.11 commented
		//authorisedRowRowHtml = authorisedRowRowHtml + "</div>"; //1.0.0.11 commented
		
		
		for(var i=0; i<authSignRowIndex; i++)
		{
			if(document.getElementById("authSignId"+i))
			{
			obidarr[i] = document.getElementById("authSignId"+i).value;
		 	fnamearr[i] = document.getElementById("authSignFName"+i).value;
		  	mnamearr[i] = document.getElementById("authSignMName"+i).value;
		 	lnamearr[i] = document.getElementById("authSignLName"+i).value;
		 	desigarr[i] = document.getElementById("authSignDesignation"+i).value;
		 	dinarr[i] = document.getElementById("authSignDinNo"+i).value;
		 	emailarr[i] = document.getElementById("authSignMailId"+i).value;
		 	cnumarr[i] = document.getElementById("authSignContactNo"+i).value;
		 	delegarr[i] = document.getElementById("authSignDelegation"+i).value;
		 	
		 	//Start 1.0.0.10
		 	dedCustIdArr[i] = document.getElementById("authSignDedCustId"+i).value;
		 	dedSearchIdArr[i] = document.getElementById("authSignDedSearchId"+i).value;
		 	panNoArr[i] = document.getElementById("authSignPan"+i).value;
		 	addressArr[i] = document.getElementById("authSignAddress"+i).value;
		 	stateIdArr[i] = document.getElementById("authSignState"+i).value;
		 	cityIdArr[i] = document.getElementById("authSignCity"+i).value;
		 	stateNameArr[i] = document.getElementById("authSignState"+i+"_temp").value;
		 	cityNameArr[i] = document.getElementById("authSignCity"+i+"_temp").value;
		 	//End 1.0.0.10
		 	GenderArr[i] = document.getElementById("authSignGender"+i).value; //1.0.0.21
		 	DobArr[i] = document.getElementById("authSignDob"+i).value; //1.0.0.21
		 	PincodeIdArr[i] = document.getElementById("authSignPinCode"+i).value; //1.0.0.21
		 	PincodeArr[i] = document.getElementById("authSignPinCode"+i+"_temp").value; //1.0.0.21
		 	signDedInitFlagArr[i] = document.getElementById("authSignDedupInitFlagTd"+i).value; //1.0.0.11
		 	}
		}
		
	
		
		document.getElementById("authorisedSignatoryRowsDiv").innerHTML = document.getElementById("authorisedSignatoryRowsDiv").innerHTML + authorisedRowRowHtml;
		
		for(var i=0; i<authSignRowIndex; i++)
		{
		    if(obidarr[i]!=undefined)
		   	{
		   	document.getElementById("authSignId"+i).value = obidarr[i];
		 	document.getElementById("authSignFName"+i).value = fnamearr[i];
		  	document.getElementById("authSignMName"+i).value = mnamearr[i];
		 	document.getElementById("authSignLName"+i).value = lnamearr[i];
		 	document.getElementById("authSignDesignation"+i).value = desigarr[i];
		 	document.getElementById("authSignDinNo"+i).value = dinarr[i];
		 	document.getElementById("authSignMailId"+i).value = emailarr[i];
		 	document.getElementById("authSignContactNo"+i).value = cnumarr[i];
		 	document.getElementById("authSignDelegation"+i).value = delegarr[i];
		 	
		 	//Start 1.0.0.10
		 	document.getElementById("authSignDedCustId"+i).value = dedCustIdArr[i];
		 	document.getElementById("authSignDedSearchId"+i).value = dedSearchIdArr[i];
		 	document.getElementById("authSignPan"+i).value = panNoArr[i];
		 	document.getElementById("authSignAddress"+i).value = addressArr[i];
		 	document.getElementById("authSignState"+i).value = stateIdArr[i];
		 	document.getElementById("authSignCity"+i).value = cityIdArr[i];
		 	document.getElementById("authSignState"+i+"_temp").value = stateNameArr[i];
		 	document.getElementById("authSignCity"+i+"_temp").value = cityNameArr[i];
		 	//End 1.0.0.10
		 	document.getElementById("authSignGender"+i).value = GenderArr[i]; //1.0.0.21
		 	document.getElementById("authSignDob"+i).value = DobArr[i]; //1.0.0.21
		 	document.getElementById("authSignPinCode"+i).value = PincodeIdArr[i]; //1.0.0.21
		 	document.getElementById("authSignPinCode"+i+"_temp").value = PincodeArr[i]; //1.0.0.21
		 	document.getElementById("authSignDedupInitFlagTd"+i).value = signDedInitFlagArr[i]; //1.0.0.11
		 	}
		}
		populateDesignation(document.getElementById("authSignDesignation"+authSignRowIndex));
		document.getElementById("authSignDesignation"+authSignRowIndex).value=objDesignation;
		document.getElementById("authSignGender"+authSignRowIndex).value=objGender; //1.0.0.21
		document.getElementById("authSignDob"+authSignRowIndex).value=objDob; //1.0.0.21
				
			
		if(objId!="null" && objId!="" && objId!=null){
	    	document.getElementById("authSignViewDedupTH").style.display="block";
	    	document.getElementById("authSignDedupInitFlagTd"+authSignRowIndex).style.display="inline"; //1.0.0.11
     		//document.getElementById("authSignViewDedupTd"+authSignRowIndex).style.display="inline";//commented by Hemant 1.0.0.11
     		authDedupViewFlg = "Y";
     	}else{
     		if(authDedupViewFlg == "Y"){
     			document.getElementById("authSignViewDedupTH").style.display="block";
     			document.getElementById("authSignDedupInitFlagTd"+authSignRowIndex).style.display="inline"; //1.0.0.11
         		//document.getElementById("authSignViewDedupTd"+authSignRowIndex).style.display="inline"//commented by Hemant 1.0.0.11
	     		document.getElementById("authSignDedupeInitViewBtn"+authSignRowIndex).style.display="none";
         		$("#authSignViewDedupTdSpanId"+authSignRowIndex).html("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
     		}else{
     			document.getElementById("authSignViewDedupTd"+authSignRowIndex).style.display="none";
     			document.getElementById("authSignDedupeInitViewBtn"+authSignRowIndex).style.display="none";
     			document.getElementById("authSignViewDedupTH").style.display="none";
     		}
	    }
		
		
		availableauthSignRows = availableauthSignRows + "~" + authSignRowIndex + "~";	
		authSignRowIndex++;
		authSignRowCount++;

}


//////////////////////////////// Changes made By Apurva ////////////////////////

var keyContactsRowIndex = 0;
var keyContactsRowCount = 0;
var availablekeyContactsRows="";
function addKeyContactsRow(objjId,objCaseId,objCaseCode,objlob,objcontactType,objname,objfirmName,objmobile,objEmailID,objAddress)
{
	
		var objjIdarr = new Array();
		var objCaseIdarr = new Array();
		var objCaseCodearr = new Array();
		var objlobarr = new Array();
		var objcontactTypearr = new Array();
		var objnamearr = new Array();
		var objfirmNamearr = new Array();
		var objmobilearr = new Array();
		var objEmailIDarr = new Array();
		var objAddressarr = new Array();
		 
		
		
		var keyContactsRowRowHtml = "";
		keyContactsRowRowHtml = keyContactsRowRowHtml + "<div id='keyContactsrowtable"+keyContactsRowIndex+"' style='border:none; text-align:left;'>";
		keyContactsRowRowHtml = keyContactsRowRowHtml + "<table  width='100%' class='main_body' id='keyContactTableRow'>";
		keyContactsRowRowHtml = keyContactsRowRowHtml + "<tr>";
		
	<logic:notEqual name="activityInfo" property="activityType" value="PDE">	
		keyContactsRowRowHtml = keyContactsRowRowHtml + "<td  width='2%' align='left'>";
			keyContactsRowRowHtml = keyContactsRowRowHtml + "<input type='checkbox' id='selectkc"+keyContactsRowIndex+"' value='"+keyContactsRowIndex+"' onclick='toggleKeyContactsRow(this);'/>";
			
			keyContactsRowRowHtml = keyContactsRowRowHtml + "</td>";
			
	</logic:notEqual>	
		
		
		/* Apurva Changes on 21st dec 2017  */
		keyContactsRowRowHtml = keyContactsRowRowHtml + "<td width='12%' align='left'>";
		keyContactsRowRowHtml = keyContactsRowRowHtml + "<input type='text' size='14' maxlength='20' disabled='true' id='caseCode"+keyContactsRowIndex+"' style='width:110 px;' value='"+objCaseCode+"'  />";
		keyContactsRowRowHtml = keyContactsRowRowHtml + "</td>";
		
		
		keyContactsRowRowHtml = keyContactsRowRowHtml + "<td width='12%' align='left'>";
		keyContactsRowRowHtml = keyContactsRowRowHtml + "<input type='hidden' id='keyContactsId"+keyContactsRowIndex+"' value='"+objjId+"' />";
		keyContactsRowRowHtml = keyContactsRowRowHtml + "<input type='hidden' id='caseId"+keyContactsRowIndex+"' value='"+objCaseId+"'  />";
		keyContactsRowRowHtml = keyContactsRowRowHtml + "<select   id='keyContactslob"+keyContactsRowIndex+"' style='width:110 px;'  /><option value='0'>SELECT</option>";
		keyContactsRowRowHtml = keyContactsRowRowHtml + "</td>";
		
		
		
		
		
		keyContactsRowRowHtml = keyContactsRowRowHtml + "<td width='12%' align='left'>";
		keyContactsRowRowHtml = keyContactsRowRowHtml + "<select   id='keyContactsContactType"+keyContactsRowIndex+"' style='width:110 px;'  /><option value='0'>SELECT</option>";
		keyContactsRowRowHtml = keyContactsRowRowHtml + "</td>";
		
		
		keyContactsRowRowHtml = keyContactsRowRowHtml + "<td width='12%' align='left'>";
		keyContactsRowRowHtml = keyContactsRowRowHtml + "<input type='text' size='14' maxlength='20' id='keyContactsName"+keyContactsRowIndex+"' style='width:110 px;' value='"+objname.trim()+"'  />";
		keyContactsRowRowHtml = keyContactsRowRowHtml + "</td>";
		
		
		
		
		keyContactsRowRowHtml = keyContactsRowRowHtml + "<td width='12%' align='left'>";
		keyContactsRowRowHtml = keyContactsRowRowHtml + "<input type='text' size='14' maxlength='20' id='keyContactsFirmName"+keyContactsRowIndex+"' style='width:110 px;' value='"+objfirmName+"'  />";
		keyContactsRowRowHtml = keyContactsRowRowHtml + "</td>";
		
		keyContactsRowRowHtml = keyContactsRowRowHtml + "<td width='12%' align='left'>";
		keyContactsRowRowHtml = keyContactsRowRowHtml + "<input type='text' size='14' maxlength='10' id='keyContactsMobile"+keyContactsRowIndex+"' onkeypress='return ( event.charCode >= 48 && event.charCode <= 57 )' style='width:110 px;' value='"+objmobile+"'   />";/* 1.0.0.1 */
		keyContactsRowRowHtml = keyContactsRowRowHtml + "</td>";
		
		keyContactsRowRowHtml = keyContactsRowRowHtml + "<td width='12%' align='left'>";
		keyContactsRowRowHtml = keyContactsRowRowHtml + "<input type='text' size='14' maxlength='50' id='keyContactsMailId"+keyContactsRowIndex+"' style='width:110 px;' value='"+objEmailID+"' />";
		keyContactsRowRowHtml = keyContactsRowRowHtml + "</td>";
		
		
		keyContactsRowRowHtml = keyContactsRowRowHtml + "<td width='14%' align='left'>";
		keyContactsRowRowHtml = keyContactsRowRowHtml + "<input type='text' size='14' maxlength='50' id='keyContactsAddress"+keyContactsRowIndex+"' style='width:110 px;' value='"+objAddress+"'  />";
		keyContactsRowRowHtml = keyContactsRowRowHtml + "</td>";
		
		

		keyContactsRowRowHtml = keyContactsRowRowHtml + "</tr>";
		keyContactsRowRowHtml = keyContactsRowRowHtml + "</table>";
		keyContactsRowRowHtml = keyContactsRowRowHtml + "</div>";
		
		
		for(var i=0; i<keyContactsRowIndex; i++)
		{
			if(document.getElementById("keyContactsId"+i))
			{
			objjIdarr[i] = document.getElementById("keyContactsId"+i).value;
			objCaseIdarr[i] = document.getElementById("caseId"+i).value;
			objCaseCodearr[i] = document.getElementById("caseCode"+i).value;
		 	objlobarr[i] = document.getElementById("keyContactslob"+i).value;
		  	objcontactTypearr[i] = document.getElementById("keyContactsContactType"+i).value;
		 	objnamearr[i] = document.getElementById("keyContactsName"+i).value;
		 	objfirmNamearr[i] = document.getElementById("keyContactsFirmName"+i).value;
		 	objmobilearr[i] = document.getElementById("keyContactsMobile"+i).value;
		 	objEmailIDarr[i] = document.getElementById("keyContactsMailId"+i).value;
		 	objAddressarr[i] = document.getElementById("keyContactsAddress"+i).value;
		 	
		 	}
		}
		
	
		
		document.getElementById("keyContactsRowsDiv").innerHTML = document.getElementById("keyContactsRowsDiv").innerHTML + keyContactsRowRowHtml;
		
		for(var i=0; i<keyContactsRowIndex; i++)
		{
		    if(objjIdarr[i]!=undefined)
		   	{
		   	document.getElementById("keyContactsId"+i).value = objjIdarr[i];
		   	document.getElementById("caseId"+i).value = objCaseIdarr[i];
		   	document.getElementById("caseCode"+i).value = objCaseCodearr[i];
		 	document.getElementById("keyContactslob"+i).value = objlobarr[i];
		  	document.getElementById("keyContactsContactType"+i).value = objcontactTypearr[i];
		 	document.getElementById("keyContactsName"+i).value = objnamearr[i];
		 	document.getElementById("keyContactsFirmName"+i).value = objfirmNamearr[i];
		 	document.getElementById("keyContactsMobile"+i).value = objmobilearr[i];
		 	document.getElementById("keyContactsMailId"+i).value = objEmailIDarr[i];
		 	document.getElementById("keyContactsAddress"+i).value = objAddressarr[i];
		 	
		 	}
		}
		populateKeyContactType(document.getElementById("keyContactsContactType"+keyContactsRowIndex));
		document.getElementById("keyContactsContactType"+keyContactsRowIndex).value=objcontactType;
		populateLob(document.getElementById("keyContactslob"+keyContactsRowIndex));
		document.getElementById("keyContactslob"+keyContactsRowIndex).value=objlob;
		document.getElementById("keyContactslob"+keyContactsRowIndex).value="1000000003";   //1.0.0.14
				
			
		availablekeyContactsRows = availablekeyContactsRows + "~" + keyContactsRowIndex + "~";	
		keyContactsRowIndex++;
		keyContactsRowCount++;

}



///////////////////////////////////////
	 // ravi for search for coapplicant start
       
        function searchWindowForCoapplicant()
		{ //1.0.0.0.2	     Ravi
		   var limitAppFlagStatus;
		    <logic:notEmpty name ="StaticInfo">
                       var limitSearchId="<bean:write name="StaticInfo" property="limitId"/>";
                       var limitProductId="<bean:write name="StaticInfo" property="productId"/>";
                       product_ID_select="<bean:write name="StaticInfo" property="productId"/>";
                       //alert("limitSearchId"+limitSearchId)
                       if(limitSearchId!=null && limitSearchId!="")
                       limitAppFlagStatus="Y";
                       else
                       limitAppFlagStatus="N";
                </logic:notEmpty>
               <logic:empty name ="StaticInfo">/* 2.0.0.0.1 start  */
                       var limitSearchId="";
                       var limitProductId="";
                       product_ID_select="";
                       //alert("limitSearchId"+limitSearchId)
                       if(limitSearchId!=null && limitSearchId!="")
                       limitAppFlagStatus="DM";
                       else
                       limitAppFlagStatus="DM";
              </logic:empty>
                newFlagForLimit="yes"; 
                /* 2.0.0.0.1 end  */
		 if(newFlagForLimit=="yes")
			{
		         
		 var t="applicantSearchAction.do?actionPerformed=displaySearchScreen&productId="+limitProductId+"&limitId="+limitSearchId+"&limitAppFlag="+limitAppFlagStatus;
			
		//1.0.0.0.2	     Ravi
			window.open(t,"target","height=700,width=1100,resizable=yes,scrollbars=auto,top=150,left=150");
			}
		} 
		
		
			function checkForm()
			{
				var legalFormName="";
				var entityIdForCust='<bean:write name ="entityId"/>';   //1.0.0.0.5
				/* add start by 1.0.0.6 */
				//1.0.0.8 start
				var saveAssetCatGpStr = "";
 		for(var i = 0 ; i < <%=assetCatGpDtls.size()%>; i++)
				{
					//alert("i"+i);
					var assetCatId = document.getElementById("assetCategoryid"+i).value;
					var gpValue = document.getElementById("gracePeriodVal"+i).value;
					//alert("assetCatId"+assetCatId);
					//alert("gpValue"+gpValue);
					validate(document.getElementById("gracePeriodVal"+i),"TMV","Grace Period");
					if(gpValue < 0 && gpValue > 9999) 
						{
							alert(("Grace Period Value cannot be Less Than 0 and Greater Than 9999").toUpperCase());
							return;
						}
					
					
					if(i == <%=assetCatGpDtls.size() - 1%>)
						{
							saveAssetCatGpStr += assetCatId+"~"+gpValue;
						}
					else
						{
							saveAssetCatGpStr += assetCatId+"~"+gpValue+"^";
						}
					
				}
 		document.getElementById("assetCatGpSaveStr").value = saveAssetCatGpStr;
				
 		//1.0.0.8 end
				if(entityIdForCust === "1000000008"){
					if(document.forms[0].addressType.value === '1200000007' || document.forms[0].addressType.value === '1200000008'){
						 validate(document.forms[0].sameAs,"DDMV","Same AS");
					}
					/* add end by 1.0.0.6 */
				}
				<logic:notEmpty name="newDetail">
					
					//checkMandatory();
					if(document.forms[0].isExisting.value!="Y"){//2.0.0.0.4
					tempData.errorMessages = tempData.errorMessages +"\n"+"Existing radio should be checked";
					}if(document.forms[0].isExisting.value=="Y"){
					if(document.forms[0].existingApplicantId.value==""){
					tempData.errorMessages = tempData.errorMessages +"\n"+"Please search or enter any existing approved customer";
					}
					}
					
				</logic:notEmpty>
				
				<logic:empty name="newDetail">
					//Changes made by Apurva
				//applyValidation(document,"P");
					
					///
					
				</logic:empty>
				//alert("custEntityType = "+document.forms[0].custEntityType.value);
				var businessDate='<bean:write name ="llmSessionUserInformationDTO" property="businessDate"/>';
				if(document.forms[0].custEntityType.value=="1000000001")
					{
					 <logic:equal name="entityId" value="1000000008">//2.0.0.0.5 start
					 if(document.forms[0].applicantType.value!="1000000001" && document.forms[0].applicantType.value!="1000000002"){
						validate(document.forms[0].title,"CMV",'Salutation');
						}
						</logic:equal>
						<logic:notEqual name="entityId" value="1000000008">
						validate(document.forms[0].title,"CMV",'Salutation');
						</logic:notEqual>
						
						<logic:equal name="entityId" value="1000000008">
						 if(document.forms[0].applicantType.value!="1000000001" && document.forms[0].applicantType.value!="1000000002"){
						<logic:notEmpty name="relationFlag">
							<logic:equal name="relationFlag" value="Y">
						validate(document.forms[0].relation,"CMV",'Relation');
						  </logic:equal>
	     				</logic:notEmpty>//2.0.0.0.5 end
						
						}
						</logic:equal>
						
						<logic:notEqual name="entityId" value="1000000008">
						if(document.forms[0].applicantType.value!="1000000001")
						 <logic:notEmpty name="relationFlag">
							<logic:equal name="relationFlag" value="Y">
						validate(document.forms[0].relation,"CMV",'Relation');
						  </logic:equal>
	     				</logic:notEmpty>  // 1.0.0.0.5 start
	     				</logic:notEqual>
						if(document.forms[0].applicantType.value=="1000000004" && referenceFlag=="N")
							{
							 
							 validate(document.forms[0].firstName,"NV,TMV",'<bean:message bundle="customerResource" key="customer.common.firstName"/>');
				             <logic:notEqual name="activityInfo" property="activityType" value="DM PDE">
				             validate(document.forms[0].lastName,"NV,TMV",'<bean:message bundle="customerResource" key="customer.common.lastName"/>');
				             </logic:notEqual>
							} //  1.0.0.0.5 end
						/* else{	
							validate(document.forms[0].fatherFirstName,"NV,TMV",'Father/Spouse First Name');
							validate(document.forms[0].fatherLastName,"NV,TMV",'Father/Spouse Last Name');
						
						validate(document.forms[0].firstName,"NV,TMV",'<bean:message bundle="losApplicationResource" key="los.personalInfo.FirstName"/>');
						validate(document.forms[0].lastName,"NV,TMV",'<bean:message bundle="losApplicationResource" key="los.personalInfo.LastName"/>');
					   } */
					    validate(document.forms[0].fatherFirstName,"NV",'Father/Spouse First Name');
						validate(document.forms[0].fatherLastName,"NV",'Father/Spouse Last Name');
						
						validate(document.forms[0].firstName,"NV,TMV",'<bean:message bundle="losApplicationResource" key="los.personalInfo.FirstName"/>');
						validate(document.forms[0].legalFormTypeInd,"DDMV",'Constitution');//1.0.0.18
						 <logic:notEqual name="activityInfo" property="activityType" value="DM PDE">
						validate(document.forms[0].lastName,"NV,TMV",'<bean:message bundle="losApplicationResource" key="los.personalInfo.LastName"/>');
					 </logic:notEqual>
					    validate(document.forms[0].fatherMiddleName,"NV",'Father/Spouse Middle Name');
						validate(document.forms[0].middleName,"NV",'<bean:message bundle="losApplicationResource" key="los.personalInfo.MiddleName"/>');
						
						
					<logic:equal name="entityId" value="1000000003">
					validate(document.forms[0].dateOfBirth,"TMV",'Date Of Birth');
					validate(document.forms[0].gender,"CMV",'Gender');
					validate(document.forms[0].maritalStatus,"CMV",'Marital Status');
					</logic:equal>
						//1.0.0.0.6 start //1.0.0.0.8 start
				  		/* if(document.forms[0].addressType.value=="1000000010"){
				  		validate(document.forms[0].gstinNo,"TMV",'gstinNo');
				  		} */
						 // 1.0.0.0.6 end		//1.0.0.0.8 end					
						
						//validate(document.forms[0].maritalStatus,"CMV",'<bean:message bundle="losApplicationResource" key="los.personalInfo.MaritalStatus" />');
						
	                    
					   //validate(document.forms[0].customerSegment,"CMV",'<bean:message bundle="losApplicationResource" key="los.personalInfo.CustomerSegment" />');
					
				 if(!checkBlankWithoutMessage(document.forms[0].dateOfBirth))
					{
							var sch_Value="0";
							 <logic:notEmpty name="StaticInfo">					
									sch_Value='<bean:write name="StaticInfo" property="schemeId"/>';
							</logic:notEmpty>
						/* for(var i=0;i<SchemeArr.length;i++)
						{
						    
							if(sch_Value==SchemeArr[i][0])
							{
								 ageMin = Number(SchemeArr[i][1]);
								 ageMax  = Number(SchemeArr[i][2]);
								 
								break;
							}
						} */
						          ageMin = '18';
								  ageMax  = '100';  // 1.0.0.0.4
								  <logic:notEqual name="activityInfo" property="activityType" value="PDE"> // 1.0.0.0.7 add 
							CheckDateOfBirthNew(document.forms[0].dateOfBirth,businessDate,ageMin,ageMax,'<bean:message bundle="losApplicationResource" key="los.newProspect.dateOfBirth"/>');
									</logic:notEqual> // 1.0.0.0.7 add
					}
					
					
			 			if(document.forms[0].maritalStatus[0].checked)
							{
									
							}
							if(document.forms[0].applicantType.value==1000000001 || document.forms[0].applicantType.value==1000000002 || document.forms[0].applicantType.value==1000000003 ||document.forms[0].applicantType.value==1000000009 || document.forms[0].applicantType.value==1000000010 || document.forms[0].applicantType.value==1000000011)
							{
								<logic:notEmpty name="StaticInfo">
									<logic:notEmpty name="StaticInfo" property="productCategory">
							 			<logic:notEqual name="StaticInfo" property="productCategory" value="1000000002">
							 			if(document.forms[0].applicantType.disabled==false)
										{
										   if(document.forms[0].custEntityType.value=='1000000001')
										  { 
										 
											if(document.forms[0].firstName.value=="")
											 {
										 	  tempData.errorMessages = tempData.errorMessages +"\n"+"First Name must be specified";
											 }
											 	<logic:notEqual name="activityInfo" property="activityType" value="DM PDE">
											 if(document.forms[0].lastName.value=="")
											 {
											   tempData.errorMessages = tempData.errorMessages +"\n"+"Last Name must be specified";
											 }
											</logic:notEqual>
										   } 
										 }
									   else if(document.forms[0].applicantType.disabled==true)
										{
										   if(document.forms[0].custEntityType.value=='1000000001')
										  { 
										    if(document.forms[0].panNo.value==""  && document.forms[0].applicantType.value=='1000000001')
										     {
											   tempData.errorMessages = tempData.errorMessages +"\n"+"PAN Number must be specified";
										     } 
										     
										     }
										}
							 			</logic:notEqual>
							 		</logic:notEmpty>
						 		</logic:notEmpty>

								<logic:notEmpty name="StaticInfo">
							 		<logic:empty name="StaticInfo" property="productCategory">
							 		  if(entityIdForCust!="1000000003"){   // 1.0.0.0.5 start
							 			if(document.forms[0].adharNo.value == ""  && document.forms[0].voterId.value == ""  && document.forms[0].panNo.value=="" && document.forms[0].passportNo.value=="" && document.forms[0].drivingLicenseNo.value=="")
										{
											tempData.errorMessages = tempData.errorMessages +"\n"+"please enter at least one from Voter ID,PAN number,Passport No,Driving Licence No,Adhar No";
										} 
									 }  // 1.0.0.0.5 end
							 		</logic:empty>
						 		</logic:notEmpty>
							}

							//Start 1.0.0.22
							<logic:notEqual name="entityId" value="1000000008">
							if(document.forms[0].tdsApplicableCheck.value ==null || document.forms[0].tdsApplicableCheck.value =="" || document.forms[0].tdsApplicableCheck.value =="0"){
								tempData.errorMessages = tempData.errorMessages +"\n"+"TDS APPLICABLE MUST BE SELECTED.";
							}
							</logic:notEqual>
						//End 1.0.0.22

			   
			   validate(document.forms[0].industryInd,"DDMV",'INDUSTRY');//1.0.0.16
			   validate(document.forms[0].natureOfBusiness,"DDMV",'NATURE OF BUSINESS');//1.0.0.16//1.0.0.20
			   validate(document.forms[0].panNo,"TMV,PANV",'<bean:message bundle="losApplicationResource" key="los.personalInfo.PanNo"/>');
			   validate(document.forms[0].adharNo,"ADV",'<bean:message bundle="losApplicationResource" key="los.personalInfo.AdharNo"/>');

				//1.0.0.21
				if(entityId=="1000000003"){
				validate(document.forms[0].gstRegistrationStatus,"DDMV",'GST Registration Status');
				}
			   
			   if(document.forms[0].passportNo && document.forms[0].passportNo.value!="")
			   {
			   if(alphanumeric(document.forms[0].passportNo)) // Added by peeyush for bug 14389
											 {
											  tempData.errorMessages = tempData.errorMessages +"\n"+"Passport No. should contain only alphabets and numbers.";
											 }
					}
					
				}	
				 	legalFormName='<%=request.getAttribute("legalFormName")%>';
					 	
				    if(document.forms[0].custEntityType.value=="1000000002")
					{	
						if(document.forms[0].nameOfCompany.disabled == false){ //added 1.0.0.5
							if(IsAlphaNumericWithSpecialSymbolNew(document.forms[0].nameOfCompany,'<bean:message bundle="losApplicationResource" key="los.newProspect.nameOfCompany"/>'))
								{
								  return false;
								}
						}
						
						
					
						validate(document.forms[0].authSignatoryFirstName,"NV",'<bean:message bundle="losApplicationResource" key="los.newProspect.authorizedSignatoryFName"/>');
					 	validate(document.forms[0].authSignatoryMiddleName,"NV",'<bean:message bundle="losApplicationResource" key="los.common.middleName"/>');
						validate(document.forms[0].authSignatoryLastName,"NV",'<bean:message bundle="losApplicationResource" key="los.common.lastName"/>');
					  
						
						if(document.forms[0].dateOfIncorporation.value!="")
							{
								CompareTwoDatesGreater(document.forms[0].dateOfIncorporation,document.forms[0].businessDate,'Date Of Incorporation','Business Date');
							}
						
						removeCommaOfObject(document.forms[0].authCapital);
						
							if(document.forms[0].applicantType.value==1000000001 || document.forms[0].applicantType.value==1000000002 || document.forms[0].applicantType.value==1000000003 ||document.forms[0].applicantType.value==1000000009 || document.forms[0].applicantType.value==1000000010 || document.forms[0].applicantType.value==1000000011)
							{
							 			/* if(document.forms[0].companyPan.value == "")
										{
											tempData.errorMessages = tempData.errorMessages +"\n"+"PAN number must be specified";
										}  */
						
						    }
						    // 1.0.0.4 start
						    <logic:equal name="entityId" value="1000000008">
						    if(document.forms[0].applicantType.value!=1000000002){
						   validate(document.forms[0].companyPan,"TMV,PANV",'<bean:message bundle="losApplicationResource" key="los.personalInfo.PanNo"/>');
			                 }
			                 </logic:equal>
			                 <logic:notEqual name="entityId" value="1000000008">
			                 validate(document.forms[0].companyPan,"TMV,PANV",'<bean:message bundle="losApplicationResource" key="los.personalInfo.PanNo"/>');
			                 </logic:notEqual>
			       // 1.0.0.4 end
						validate(document.forms[0].authCapital,"AUTHCAPV",'<bean:message bundle="losApplicationResource" key="los.newProspect.authorizedCapital"/>');
						validate(document.forms[0].issuedCap,"ANV",'<bean:message bundle="customerResource" key="customer.newCustomer.paidUpCapital"/>');
						validate(document.forms[0].boardRepresentative,"ANVWS",'<bean:message bundle="losApplicationResource" key="los.newProspect.diaBoardRepresentative"/>');
					 /// Added By Apurva on 07-dec-2017//
					 //1.0.0.16
					 <logic:notEqual  name="entityId" value="1000000008">//1.0.0.17 start
					 //validate(document.forms[0].corpTanNo,"ANV,TMV","TAN No."); // 1.0.0.22
				    	</logic:notEqual>
				    	<logic:notEqual  name="entityId" value="1000000008">//1.0.0.19 start
						 validate(document.forms[0].dateOfIncorporation,"TMV","Date Of Incorp.");
					    	</logic:notEqual>
						//validate(document.forms[0].corpTanNo,"ANV,TMV","TAN No.");// 1.0.0.15
						<logic:notEqual  name="entityId" value="1000000008">//1.0.0.17 start
				    	validate(document.forms[0].industryCorp,"DDMV",'INDUSTRY');
				    	</logic:notEqual>
				    	validate(document.forms[0].legalFormTypeCorp,"DDMV",'Constitution')//1.0.0.18
				    	
						//validate(document.forms[0].industryCorp,"DDMV",'INDUSTRY');//1.0.0.16 
						validate(document.forms[0].natureOfBusinessCorp,"DDMV",'NATURE OF BUSINESS');//1.0.0.16 //1.0.0.20

						//1.0.0.21
						if(entityId=="1000000003"){
						validate(document.forms[0].gstRegistrationStatusCorp,"DDMV",'GST Registration Status');
						}

						//start 1.0.0.22
						<logic:notEqual name="entityId" value="1000000008">
						if(document.forms[0].tdsApplicableCheck.value ==null || document.forms[0].tdsApplicableCheck.value =="" || document.forms[0].tdsApplicableCheck.value =="0"){
							tempData.errorMessages = tempData.errorMessages +"\n"+"TDS APPLICABLE MUST BE SELECTED.";
						}
						if(document.forms[0].tdsApplicableCheck.value !== null && document.forms[0].tdsApplicableCheck.value == "Y"){
							 validate(document.forms[0].corpTanNo,"ANV,TMV","TAN No.");
							}
						</logic:notEqual>
					//end 1.0.0.22
					
				  }  
					
					validate(document.forms[0].noOfDependents,"TNV",'<bean:message bundle="losApplicationResource" key="los.personalInfo.NoOfDependents"/>');
					validate(document.forms[0].voterId,"ANV,NZV",'<bean:message bundle="losApplicationResource" key="los.personalInfo.VoterId"/>');
	
			  if(document.forms[0].drivingLicenseNo.value!="")
				 	{
			
				 	if(document.forms[0].drivingLicenseNo.value.search(/^[a-zA-Z0-9]+$/)==-1)
					  {
					  tempData.errorMessages+="\n";
					  tempData.errorMessages+="Driving Licence No. must be alphanumeric.";
					  	document.forms[0].drivingLicenseNo.select();
				       	document.forms[0].drivingLicenseNo.focus(); 
					  }
					}
				
				if(document.forms[0].drivingLicenseNo.value!="")
				 	{
					if(/\\/.test(document.forms[0].drivingLicenseNo.value)) 
					{
							tempData.errorMessages+="\n";
						  tempData.errorMessages+="Driving Licence should not contain backslash('\\').";
					}		
				}
				
				if(document.forms[0].voterId.value!="")
				 	{
					if(/\\/.test(document.forms[0].voterId.value)) 
					{
							tempData.errorMessages+="\n";
						  tempData.errorMessages+="Voter-Id should not contain backslash('\\').";
					}		
				}
				 	
				if(document.forms[0].passportNo.value!="")
				 	{
					if(/\\/.test(document.forms[0].passportNo.value)) 
					{
							tempData.errorMessages+="\n";
						  tempData.errorMessages+="Passport No should not contain backslash('\\').";
					}		
				}
				
			if(document.forms[0].dnsRequired.checked==true)
					{
						validate(document.forms[0].dnsReason,"TMV,NV",'<bean:message bundle="losApplicationResource" key="los.personalInfo.DNSReason"/>');
						
					}
					if(document.forms[0].dnsRequired.checked)
					{
						document.forms[0].dnsReasonTemp.value="";
						document.forms[0].dnsRequired.value="Y";
						document.forms[0].dnsReason.disabled=false;
					}
					else
					{
						document.forms[0].dnsReasonTemp.value=document.forms[0].dnsReason.value;
						document.forms[0].dnsRequired.value="N";
						document.forms[0].dnsReason.disabled=true;
					}
						
					
					
				
						document.forms[0].selGender.value=document.forms[0].gender.value;
						
						if(document.forms[0].dnsRequired.value=="N")
						{
						    document.forms[0].dnsReasonTemp.value="";
						}
						if(document.forms[0].dnsRequired.value=="Y")
						{
						    document.forms[0].dnsReasonTemp.value=document.forms[0].dnsReason.value;
						}
						//1.0.0.0.6 start //1.0.0.0.8 start
					    /* if(document.forms[0].gstinNo.value!=null && document.forms[0].gstinNo.value!="")
					    {
					     checkGSTINNo(document.forms[0].gstinNo.value);  
					    } */
						//1.0.0.0.6 end //1.0.0.0.8
						var officeAddressFlag=0;
						var officeAddressHasFlag=0;
						 <logic:equal name="entityId" value="1000000003">//2.0.0.0.2 start
					 	<logic:empty name="colAddressInfo">
					 	if(document.forms[0].custEntityType.value=='1000000002'){
					 			validationOfOffice();
					 			validateAddress="Y";
					 	
						     }if(document.forms[0].custEntityType.value=='1000000001'){
						     validationOfCustomer();
					 			validateAddress="Y";
						     }
					 	//saveAddressInfo="Y";
					 	</logic:empty>
					 	 <logic:notEmpty name="colAddressInfo">
					 	if(document.forms[0].custEntityType.value=='1000000002'){
					 	
					 	<logic:iterate id="iteApplicantAddressListDTOs"	name="colAddressInfo">
					 	if('<bean:write name="iteApplicantAddressListDTOs" property="addressType" />'!='1000000002'){
					 	officeAddressFlag=officeAddressFlag+1;
					 	}else{
					 	officeAddressHasFlag=1;
					 	}
					 	
					 	 </logic:iterate>
					 	// alert(officeAddressFlag);
					 	 //alert(officeAddressHasFlag);
					 	 if(officeAddressFlag!=0 && officeAddressHasFlag!=1){
					 		validationOfOffice();
					 		validateAddress="Y";
					 	
					 	}
					 	}if(document.forms[0].custEntityType.value=='1000000001'){
					 	<logic:iterate id="iteApplicantAddressListDTOs"	name="colAddressInfo">
					 	if('<bean:write name="iteApplicantAddressListDTOs" property="addressType" />'!='1000000001'){
					 	officeAddressFlag=officeAddressFlag+1;
					 	}else{
					 	officeAddressHasFlag=1;
					 	}
					 	
					 	 </logic:iterate>
					 	 //alert(officeAddressFlag);
					 	 //alert(officeAddressHasFlag);
					 	 if(officeAddressFlag!=0 && officeAddressHasFlag!=1){
					 		validationOfCustomer();
					 		validateAddress="Y";
					 	
					 	}
					 	
					 	}
					 	
					 	</logic:notEmpty> 					 	
					 	
					 	</logic:equal>//2.0.0.0.2 end
						
						
						validateKeyContact();
						saveKeyContactDetail();
						
						if(document.forms[0].custEntityType.value=='1000000002'){
							<logic:notEqual  name="entityId" value="1000000008">//1.0.0.17 start
							validateBeneficiaryOwner();//1.0.0.10
					    	</logic:notEqual>
							//validateBeneficiaryOwner();//1.0.0.10
							saveBeneficiaryOwnerDetail();//1.0.0.10
						}
			}

			function checkMailingAddressStatus()
			{  var countY=0;
				<logic:notEmpty name ="activityInfo">
					<logic:notEmpty name ="activityInfo" property="activityType">
						<logic:equal name = "activityInfo" property ="activityType" value="PDE">
						     <logic:iterate id="iteApplicantAddressListDTOs"	name="colAddressInfo">
                                   if('<bean:write name="iteApplicantAddressListDTOs" property="mailingAddress" />'=='Y')
                                   {
                                      countY=Number(countY)+Number(1);
                                   }
						     </logic:iterate>
						  
	                	
	                		if(document.forms[0].mailingAddress.value==""&&document.forms[0].mailingAddressStatus.value=="N")
	                		{
	                			alert(("One mailing address is mandatory.").toUpperCase());
	                			return false;
	                		}
	                		else
	                		{
	                			return true;
	                		}
                		</logic:equal>
                		<logic:notEqual name = "activityInfo" property ="activityType" value="PDE">
                			return true;
                		</logic:notEqual>
					</logic:notEmpty>
              	</logic:notEmpty>
			}
			function validateImageUpload()
			{  
	            var returnValue = true;	            
	            if(document.forms[0].custEntityType.value==1000000001)
	            {  
   				<logic:notEqual name="activityInfo" property="activityType" value="PDE">
   					
   				<%if (application.getAttribute("photoUpload").equals("Y")) {%>				   
					if(document.getElementById("imgFlag").value!="T")
					{  
						document.getElementById("imgFlag").value=imageFound;
					}	
   					
					if(document.getElementById("imgFlag").value!="T")
					{
						
					}
					
				   <%}%> 
				</logic:notEqual>
				}
				else
				{
				 <%if (application.getAttribute("photoUpload").equals("Y")) {%>  
				document.getElementById("applicantImage").complete==true;
				document.getElementById("imgFlag").value="T";
				<%}%>
				
			   }
			  return returnValue;
			}
			 function salesManagerChooser(tName)
					     {
					           var branch = "1000000070";//document.forms[0].branch.value;
					           var paramList = tName+"~"+branch;
					           var rowId = 1;
					           var sessionStatus = checkinterval(sessionTimeout);
					     		if(sessionStatus=="Y")
					     		{
					     			 window.open("populateMaster.do?qString="+paramList+"&label=Relationship Manager&dependant=salesManager&rowNum="+rowId,"popCity","width=500, height=490,top=100,left=100, location=no, menubar=no, status=no, toolbar=no, scrollbars=no, resizable=no");
					     		}
					     		else
					     		{
					     			<%request.setAttribute("sessionExpiry", "Your sesion has been expired .Please relogin again.");%>
					     			document.forms[0].action = "userAuthAction.do?dispatchMethod=logout";
					     	 		document.forms[0].method = "post";	
					     	  		document.forms[0].submit();
					     		}	      
					     }
					     function cityChooser(tName)
					 	{
					 	      var stateId = document.forms[0].state.value;
					 	      var paramList = tName+"~"+stateId;
					 	      var sessionStatus = checkinterval(sessionTimeout);
					 		if(sessionStatus=="Y")
					 		{
					 			window.open("populateMaster.do?qString="+paramList+"&label=City&dependant=city&cleanDependentItems=T~zipPin_temp&rowNum=1","popCity","width=500, height=490,top=100,left=100, location=no, menubar=no, status=no, toolbar=no, scrollbars=no, resizable=no");
					 		}
					 		else
					 		{
					 			<%request.setAttribute("sessionExpiry", "Your session has expired!! Please login again.");%>
					 			document.forms[0].action = "userAuthAction.do?dispatchMethod=logout";
					 	 		document.forms[0].method = "post";	
					 	  		document.forms[0].submit();
					 		}
					 		  
					 	}
					     

							function stateChooser(tName)
				{
				var sessionStatus = checkinterval(sessionTimeout);
				if(sessionStatus=="Y")
				{
				window.open("populateMaster.do?qString="+tName+"&label=State&dependant=state&cleanDependentItems=T~city_temp,T~zipPin_temp&rowNum=1","popState","width=500, height=490,top=100,left=100, location=no, maximize=yes, menubar=no, status=no, toolbar=no, scrollbars=no, resizable=no","modal=yes" );
				}
				else
				{
				<%request.setAttribute("sessionExpiry",
						"Your session has expired!! Please login again.");%>
				document.forms[0].action = "userAuthAction.do?dispatchMethod=logout";
					document.forms[0].method = "post";	
					document.forms[0].submit();
				}

				}
							
							
						 	function checkMandatory()
						 	{
						 		for(var i=0;i<validationData.length;i++)
						   		{
						   			var applicantType = document.forms[0].applicantType.value;
						   			if(Number(applicantType) == Number(validationData[i][3]))
						   			{
						   				
						   				if(validationData[i][4]=="MANDATORY")
						   				{
						   					if(validationData[i][5]=="A")
						   					{
						   						
						   						try
						   						{
						   							name = validationData[i][1];
						   							validation = validationData[i][8];
												    message = validationData[i][9];
												    screenCheck = validationData[i][10];
										
												    if(document.getElementById(name).disabled==false)
												    {
												       if(screenCheck=="P")
												       {
												       	
												       	validate(document.getElementById(name),validation,message);
												       }
												    }
												    
												}
						   						catch(err)
						   						{
						   						}
						   					}
						   				}
						   				
						   			}
						   			else
						   			{
						   			   if(document.forms[0].custEntityType.value=="1000000002")
						   			   {
						   			      if(validationData[i][4]=="MANDATORY")
							   				{
							   					if(validationData[i][5]=="A")
							   					{
							   						
							   						try
							   						{
							   							name = validationData[i][1];
							   							validation = validationData[i][8];
													    message = validationData[i][9];
													    if(document.getElementById(name).disabled==false)
													    {
													      
													       if(name!="relation")
													       {
													       		if(screenCheck=="P")
															       {
															       	
															       	validate(document.getElementById(name),validation,message);
															       }
													       }
													       
													    }
													    
													}
							   						catch(err)
							   						{
							   						}
							   					}
							   				}
						   			   }
						   			}
						   			
						   		}
						   		
						   		
						 	}
						 	
						 		
	/////////////////----Apurva changes---------///////
	
	 function populateKeyContactsStr()
	  {
	  
	    // for disabled the pre emi as charge start
	     //  document.getElementById('preEmiChargeId').style.display="none";
	      //  document.getElementById('preEmiChargeI').style.display="none";
	       //document.getElementById('insrAccIrrId').style.display="block";
	      var keyContactsId="";
	     var caseId="";
	     var caseCode="";
	     var keyContactcontactType=0;
	     var keyContactlob=0;
	     var keyContactname="";
	     var keyContactfirmName="";
	     var keyContactmobile="";
	     var keyContactEmailId="";
	     var keyContactAddress="";
	     var keyContactId="";
	      
	     // for installment
	    <logic:notEmpty name ="keyContactDatalist">
	    
	     var instFlag="Y"; // for iterate data
	     //document.getElementById("keyContactsRowsDiv").disabled=true;
	     <logic:iterate id="itrkeyContact" name="keyContactDatalist">
	     keyContactsId="<bean:write name="itrkeyContact" property="keyContactsId"/>";
	     caseId="<bean:write name="itrkeyContact" property="caseId"/>";
	     caseCode="<bean:write name="itrkeyContact" property="caseCode"/>";
	     keyContactcontactType="<bean:write name="itrkeyContact" property="keyContactcontactType"/>";
	     keyContactlob="<bean:write name="itrkeyContact" property="keyContactlob"/>";
	     keyContactname="<bean:write name="itrkeyContact" property="keyContactname"/>";
	     keyContactfirmName="<bean:write name="itrkeyContact" property="keyContactfirmName"/>";
	     keyContactmobile="<bean:write name="itrkeyContact" property="keyContactmobile"/>";
	     keyContactEmailId="<bean:write name="itrkeyContact" property="keyContactEmailId"/>";
	     keyContactAddress="<bean:write name="itrkeyContact" property="keyContactAddress"/>";
	    
	    
      	addKeyContactsRow(keyContactsId,caseId,caseCode,keyContactlob,keyContactcontactType,keyContactname,keyContactfirmName,keyContactmobile,keyContactEmailId,keyContactAddress);
		//$('#keyContactsRowsDiv *').prop('disabled',true); //1.0.0.7 
	 </logic:iterate>   
	     
	   </logic:notEmpty>
	   <logic:empty name ="keyContactDatalist">
	   addKeyContactsRow(keyContactsId,caseId,caseCode,keyContactlob,keyContactcontactType,keyContactname,keyContactfirmName,keyContactmobile,keyContactEmailId,keyContactAddress);
	   </logic:empty>
	    
	    // insurance
	   
	      
	  }
	  
	 
	  /////////////////
		   
		    	function getAllInfomationForSignatory(populateXml)
   			{ 
   		
   		var signatoryId;
   		var signatoryFname;
   		var signatoryMname;
   		var signatoryLname;
   		var signatoryDesignation;
   		var signatoryDinNO;
   		var signatoryMailId;
   		var signatoryContactNo;
   		var signatoryDelegation;
   		var signatoryDedInitFlag; //1.0.0.11
   		var signatoryGender; //1.0.0.21
   		var signatoryDob; //1.0.0.21
   		var signatoryPincodeId; //1.0.0.21
   		var signatoryPincodeName; //1.0.0.21
   		var personalInfoXMLDOM=getXMLDOMFromStreamXX(populateXml);
   		//Changed by Ravikant for browser compatibility
   		var browser=(navigator.userAgent).toLowerCase();
   		var rowlength="";
   		try{
	   		if(browser.indexOf("msie")>0){
				rowlength= personalInfoXMLDOM.getElementsByTagName("ROWSET/ROW");
			}
			else{
				var str=(new XMLSerializer()).serializeToString(personalInfoXMLDOM);
				var str1=str.substring(str.indexOf("<ROWSET>"),(str.indexOf("</ROWSET>")+9));
				dataUI=getXMLDOMFromStream(str1);
				rowlength=dataUI.getElementsByTagName("ROW");
			}
		}catch(err){
			rowlength= personalInfoXMLDOM.getElementsByTagName("ROWSET/ROW");
		}
		//START 1.0.0.10-COMMENTED
		/* <logic:notEqual name="activityInfo" property="activityType" value="PDE">	
		if(rowlength.length==0)
		{
			addAuthorisedSignatoryRow('','','','','0','','','','', '','','','','','','','');//Hemant
		}
		</logic:notEqual> */
		//END 1.0.0.10
		for(var i=0;i<rowlength.length;i++)
		{
		signatoryId="";
   		signatoryFname="";
   		signatoryMname="";
   		signatoryLname="";
   		signatoryDesignation="";
   		signatoryDinNO="";
   		signatoryMailId="";
   		signatoryContactNo="";
   		signatoryDelegation="";
   		//Start 1.0.0.10
   		signatoryPan="";
   		signatoryAddress="";
   		signatoryDedCustId="";
   		signatoryDedSearchId="";
   		signatoryStateId="";
   		signatoryStateName="";
   		signatoryCityId="";
   		signatoryCityName="";
   		//End 1.0.0.10
   		signatoryDedInitFlag=""; //1.0.0.11
   		signatoryGender=""; //1.0.0.21
   		signatoryDob=""; //1.0.0.21
   		signatoryPincodeId=""; //1.0.0.21
   		signatoryPincodeName=""; //1.0.0.21
			
   			for( var z=0; z<=rowlength[i].childNodes.length; z++)
			{
				if(rowlength[i].childNodes[z])
				{
					nodeName = rowlength[i].childNodes[z].nodeName;
					
					if("ID" == nodeName)
					{
						if(rowlength[i].childNodes[z].childNodes[0])
						signatoryId = rowlength[i].childNodes[z].childNodes[0].nodeValue;
					}
					else if("SIGNATORYFIRSTNAME" == nodeName)
					{
						if(rowlength[i].childNodes[z].childNodes[0])
						signatoryFname = rowlength[i].childNodes[z].childNodes[0].nodeValue;
					}
					else if("SIGNATORYMIDDLENAME" == nodeName)
					{
						if(rowlength[i].childNodes[z].childNodes[0])
						signatoryMname = rowlength[i].childNodes[z].childNodes[0].nodeValue;
					}
					else if("SIGNATORYLASTNAME" == nodeName)
					{
						if(rowlength[i].childNodes[z].childNodes[0])
						signatoryLname = rowlength[i].childNodes[z].childNodes[0].nodeValue;
					}
					else if("DESIGNATION" == nodeName)
					{
						if(rowlength[i].childNodes[z].childNodes[0])
						signatoryDesignation = rowlength[i].childNodes[z].childNodes[0].nodeValue;
					}
					else if("DIN_NO" == nodeName)
					{
						if(rowlength[i].childNodes[z].childNodes[0])
						signatoryDinNO = rowlength[i].childNodes[z].childNodes[0].nodeValue;
					}
					else if("EMAIL" == nodeName)
					{
						if(rowlength[i].childNodes[z].childNodes[0])
						signatoryMailId = rowlength[i].childNodes[z].childNodes[0].nodeValue;
					}
					else if("CONTACT_NO" == nodeName)
					{
						if(rowlength[i].childNodes[z].childNodes[0])
						signatoryContactNo = rowlength[i].childNodes[z].childNodes[0].nodeValue;
					}					
					else if("DELEGATION" == nodeName)
					{
						if(rowlength[i].childNodes[z].childNodes[0])
						signatoryDelegation = rowlength[i].childNodes[z].childNodes[0].nodeValue;
					}
					//Start 1.0.0.10
					else if("DED_CUST_ID" == nodeName)
					{
						if(rowlength[i].childNodes[z].childNodes[0])
						signatoryDedCustId = rowlength[i].childNodes[z].childNodes[0].nodeValue;
					}else if("DED_SEARCH_ID" == nodeName)
					{
						if(rowlength[i].childNodes[z].childNodes[0])
						signatoryDedSearchId = rowlength[i].childNodes[z].childNodes[0].nodeValue;
					}else if("PANNO" == nodeName)
					{
						if(rowlength[i].childNodes[z].childNodes[0])
						signatoryPan = rowlength[i].childNodes[z].childNodes[0].nodeValue;
					}else if("ADDRESS" == nodeName)
					{
						if(rowlength[i].childNodes[z].childNodes[0])
						signatoryAddress = rowlength[i].childNodes[z].childNodes[0].nodeValue;
					}else if("STATE" == nodeName)
					{
						if(rowlength[i].childNodes[z].childNodes[0])
						signatoryStateId = rowlength[i].childNodes[z].childNodes[0].nodeValue;
					}else if("CITY" == nodeName)
					{
						if(rowlength[i].childNodes[z].childNodes[0])
						signatoryCityId = rowlength[i].childNodes[z].childNodes[0].nodeValue;
					}else if("STATE_NAME" == nodeName)
					{
						if(rowlength[i].childNodes[z].childNodes[0])
						signatoryStateName = rowlength[i].childNodes[z].childNodes[0].nodeValue;
					}else if("CITY_NAME" == nodeName)
					{
						if(rowlength[i].childNodes[z].childNodes[0])
						signatoryCityName = rowlength[i].childNodes[z].childNodes[0].nodeValue;
					}
					//End 1.0.0.10
					//1.0.0.11
					else if("DEDUPE_INITIATED_FLAG" == nodeName)
					{
						if(rowlength[i].childNodes[z].childNodes[0])
							signatoryDedInitFlag = rowlength[i].childNodes[z].childNodes[0].nodeValue;
					}
					//1.0.0.21 start
					else if("GENDER" == nodeName)
					{
						if(rowlength[i].childNodes[z].childNodes[0])
							signatoryGender = rowlength[i].childNodes[z].childNodes[0].nodeValue;
					}
					else if("DOB" == nodeName)
					{
						if(rowlength[i].childNodes[z].childNodes[0])
							signatoryDob = rowlength[i].childNodes[z].childNodes[0].nodeValue;
					}
					else if("PINCODEID" == nodeName)
					{
						if(rowlength[i].childNodes[z].childNodes[0])
							signatoryPincodeId = rowlength[i].childNodes[z].childNodes[0].nodeValue;
					}
					else if("PINCODE_TEMP" == nodeName)
					{
						if(rowlength[i].childNodes[z].childNodes[0])
							signatoryPincodeName = rowlength[i].childNodes[z].childNodes[0].nodeValue;
					}
					//1.0.0.21 end
					//1.0.0.11
				}
			}
			
				addAuthorisedSignatoryRow(signatoryId,signatoryFname,signatoryMname,signatoryLname,signatoryDesignation,signatoryDinNO,signatoryMailId,signatoryContactNo,signatoryDelegation,signatoryDedCustId,signatoryDedSearchId,signatoryPan,signatoryAddress,signatoryStateId,signatoryStateName,signatoryCityId,signatoryCityName,signatoryDedInitFlag,signatoryGender,signatoryDob,signatoryPincodeId,signatoryPincodeName); //1.0.0.21
	   	 }
	  }
	/*   function load(){
	document.forms[0].nationality.disabled=false;
	document.forms[0].panNo.disabled=false;
}

function checkidentityfication(){	
	if(document.forms[0].adharNo.value == ""  &&  document.forms[0].voterId.value == ""  && document.forms[0].panNo.value=="" && document.forms[0].passportNo.value=="" && document.forms[0].drivingLicenseNo.value=="")
	{
		tempData.errorMessages = tempData.errorMessages +"\n"+"please enter at least one Identification Information";
		return;
	} 
} */
         function disableFieldsOnload1()
	 	    {
	 	     <logic:notEmpty name="entityId">///2.0.0.0.3 start
		 		 <logic:equal name="entityId" value="1000000008">
	 	    document.getElementById("new").style.display = "none";
			   document.getElementById("newfield").style.display = "none";
			   </logic:equal>
			   </logic:notEmpty>// 2.0.0.0.3 end
		    populateApplicantType();
	 	    	setTime();
	 	    var  flagsaveandexit ="";
			 	 flagsaveandexit ='<%=request.getParameter("newflag")%>'; 
		 	    // alert("flagsave ="+flagsaveandexit);
		 	     if(flagsaveandexit=="yes"){
		 	         // document.getElementById("SaveExitLink").style.display="none"; 1.0.0.0.1
		 	          //document.getElementById("SaveLink").style.display="none"; 1.0.0.0.1
		 	          }
 	 	      <logic:notEmpty name="applicantTypeVal">
 	 	    			//document.getElementById("applicantType").value = '<bean:write name="applicantTypeVal"/>';  // 1.0.0.0.2
	 	      </logic:notEmpty>
 	 	    //Start by Ahtesham
	 	     if(document.forms[0].custEntityType.value=='1000000001'){
		 	     document.forms[0].kycRiskCategory.disabled="true";
		 	     }
		 	    if(document.forms[0].custEntityType.value=='1000000002'){
		 	    document.forms[0].kycRiskCategory_Corp.disabled="true";
		 	    }
		 	    //End by Ahtesham
	 	      document.getElementById("groupCode").disabled="true";
	 	      document.getElementById("isExisting").checked = true;
	 	      document.forms[0].product.value="<bean:write name="StaticInfo" property="productId" ignore="true"/>";
              <logic:notEmpty name="errorMsgDueToFileSize"> 
			       	alert(("<bean:write name="errorMsgDueToFileSize"/>").toUpperCase());
					   <%session.removeAttribute("errorMsgDueToFileSize");%> //Add 1.0.0.23
			  </logic:notEmpty>
	 	      <logic:notEmpty name="tinNo">
	 	            document.forms[0].tin.value='<bean:write name="tinNo"/>';
	 	      </logic:notEmpty>
	 	   if(document.forms[0].custEntityType.value!="0"){
	 	   getAllInfomationForSignatory(signatoryDataDetails);
	 	   
	 	    /*--------Apurva changes-----  */
	 	   populateKeyContactsStr();
	 	  populateBeneficiaryOwnerRowStr();
	 	   }
	 	   else{
	 	   getAllInfomationForSignatory("");
	 	   populateKeyContactsStr();
	 	  populateBeneficiaryOwnerRowStr("");
	 	   }
	 	    document.forms[0].editPersonalDetails.checked=true;
	   <%if (application.getAttribute("photoUpload").equals("Y")) {%>  
		    <logic:equal name="custEntityType"  value="INDIVIDUAL">
	 	   var picStr=document.getElementById("applicantImage").src;
		    picStr=picStr.substring(picStr.indexOf('ObjectID=')+9);
		    if(picStr==0)
		    {
		    document.getElementById("applicantImage").src="";
		    }
	 	    	if(document.getElementById("applicantImage").complete)
	 	    		imageFound = "T";
	 	    	else
	 	    		imageFound = "F";
	 	    	document.forms[0].editPersonalDetails.checked=true;
	 	    	
	 	   </logic:equal>
		   <%}%>  	
	 	    	editPersonalInfo();
	 	    	setTime();
	 	    	<logic:notEmpty name="strMsgForError">
			       alert(("<bean:write name="strMsgForError"/>").toUpperCase());
			    </logic:notEmpty>
			    
			    <logic:notEmpty name="strMsgForSuccess">
			       alert(("<bean:write name="strMsgForSuccess"/>").toUpperCase());
			    </logic:notEmpty>
			    <%session.removeAttribute("strMsgForSuccess");
				session.removeAttribute("strMsgForError");%>
			    
		     	if(document.forms[0].nationality.value==null || document.forms[0].nationality.value=="")
 	    		{
 	    	 		document.forms[0].nationality.value="INDIAN";      //1.0.0.0.3
 	    		}
			   // document.forms[0].nationality.disabled=false;   //1.0.0.0.3
			    
			    if(document.forms[0].formFlag.value=="Y")
			    {
			    	if(document.forms[0].panNo.value!="")
 					{
 						document.forms[0].panNo.disabled=false;
 					}
 					if(document.forms[0].adharNo.value!="")
 					{	
 						document.forms[0].adharNo.disabled=false;
 					}	 	        
 					if(document.forms[0].nationality.value!="")
	 	    		{
	 	    	 		document.forms[0].nationality.disabled=false;
	 	    		}
			    }
			    else
			    {
			    	if(document.forms[0].panNo.value!="")
 					{
 						document.forms[0].panNo.disabled=true;
 					}
 					//document.forms[0].adharNo.disabled=true;
 	        
 					if(document.forms[0].nationality.value!="")
	 	    		{
	 	    	 	//	document.forms[0].nationality.disabled=true;
	 	    		}
			    }
                        if(document.forms[0].vewPicId)
                        {
			    	       var vewPicOb = document.forms[0].vewPicId;
						   if(vewPicOb !=null && vewPicOb!=undefined)
						   vewPicOb.style.display = "none";
						}    
		    	document.getElementById("nameOfCompanyNew").style.display="none";
		    	//new change noor
	            document.forms[0].businessDate.value ='<bean:write name ="llmSessionUserInformationDTO" property="businessDate"/>';
	 	    	checkForFirstName=document.forms[0].firstName.value;
		 		checkForApplicantType=document.forms[0].applicantType.value;
		 		checkForNameOfCompany=document.forms[0].nameOfCompany.value;
		 		checkForNameOfGroup=document.forms[0].nameOfGroup.value;
		 		checkForCustEntityType=document.forms[0].custEntityType.value;
		 		
		 		
	 			applyReadOnlyRules(document,"P");
	 			applyReadOnlyRules(document,"A");
	 			
	 			
	 			if(checkForApplicantType!=null)
	 		    { 
	 		       document.forms[0].applicantType.disabled=true;   
	 		    }

	 		    if(checkForCustEntityType=="1000000001" || checkForCustEntityType=="1000000004") 
	 		    {
	 		        if(document.forms[0].applicantType.value=="1000000001")
			 		{
 						if(document.forms[0].formFlag.value=="Y")
 						{
 							document.forms[0].dateOfBirth.disabled=false;
 						}
 						else
 						{
 							document.forms[0].dateOfBirth.disabled=true;
 						}
			 		}
			 		
			 		document.getElementById("qual").style.display="block";
			 	}	
		 		<logic:notEmpty name="newDetail">
		 		    document.forms[0].editPersonalDetails.checked=true;
		 		    document.forms[0].zipPin_temp.readOnly=true;  //1.0.0.3
	                savePersonalInfo="Y";
	                document.forms[0].editPersonalDetails.disabled=true;
		 			disableElements();
		 		</logic:notEmpty>
	 		    if(checkForCustEntityType=="1000000001" || checkForCustEntityType=="1000000004")
	 		    {
	 		        if(checkForFirstName==null || checkForFirstName=="")
			 		{
			 		    document.forms[0].editPersonalDetails.checked=true;
		                savePersonalInfo="Y";
		                document.forms[0].editPersonalDetails.disabled=true;
		                document.forms[0].tanNo.disabled=true;
		            }
	 		    }
		 		if(checkForCustEntityType=="1000000002")
	 		    {
	 		        if(checkForNameOfCompany==null || checkForNameOfCompany=="")
			 		{
			 		    document.forms[0].editPersonalDetails.checked=true;
		                savePersonalInfo="Y";
		                document.forms[0].editPersonalDetails.disabled=true;
		                
		            }
		             //   savePersonalInfo="Y";
	 		    }
		 		if(checkForCustEntityType=="1000000003")
	 		    {
	 		        if(checkForNameOfGroup==null || checkForNameOfGroup=="")
			 		{
			 		    document.forms[0].editPersonalDetails.checked=true;
		                savePersonalInfo="Y";
		                document.forms[0].editPersonalDetails.disabled=true;
		            }
	 		    }
	 		    
	 		  	<%if (request.getAttribute("newDetail") == null) {%>
		 		    <logic:notEmpty name ="screenModes">
						<logic:equal name="screenModes" property = "loanSubStatus" value = "Y">
						if(document.getElementById("msg"))
							{document.getElementById("msg").style.display="block";}
						</logic:equal>
						<logic:equal name="screenModes" property = "loanSubStatus" value = "N">
						 if(document.getElementById("msg"))
							{document.getElementById("msg").style.display="none";}
						</logic:equal>
					</logic:notEmpty>
					<logic:empty name ="screenModes">
						document.getElementById("msg").style.display="none";
					</logic:empty>
					<logic:empty name ="prospectEditByOtherUser">
						document.getElementById("prospectEditByOtherUser").style.display="none";
				    </logic:empty>
		   			<logic:notEqual name="activityInfo" property="activityType" value="PDE">
 					</logic:notEqual>	
 					<logic:equal name="activityInfo" property="activityType" value="PDE">
 						
 						if(document.forms[0].formFlag.value=="Y")
 						{
 							document.forms[0].firstName.disabled=false;
 							document.forms[0].dateOfBirth.disabled=false;
 							document.forms[0].panNo.disabled=false;
 							document.forms[0].voterId.disabled=false;
 							document.forms[0].passportNo.disabled=false;
 							document.forms[0].drivingLicenseNo.disabled=false;
 						}
 						else
 						{
 							document.forms[0].firstName.disabled=true;
 							document.forms[0].dateOfBirth.disabled=true;
 							document.forms[0].panNo.disabled=true;
 							document.forms[0].voterId.disabled=true;
 							document.forms[0].passportNo.disabled=true;
 							document.forms[0].drivingLicenseNo.disabled=true;
 						}

 						if(document.forms[0].companyPan.value!="")
 						{
 							document.forms[0].companyPan.disabled=true;
 						}
 					</logic:equal>
 					
 				 
 					if(document.forms[0].formFlag.value=="Y")
 					{ 
 					  
 						document.forms[0].firstName.readOnly=false;
						document.forms[0].middleName.readOnly=false;
						document.forms[0].lastName.readOnly=false;
						document.forms[0].fatherFirstName.readOnly=false;
						document.forms[0].fatherMiddleName.readOnly=false;
						document.forms[0].fatherLastName.readOnly=false;
						document.forms[0].motherFirstName.readOnly=false;
						document.forms[0].title.disabled=false;
 		    	    	document.forms[0].maritalStatus.disabled=false;
 					}
 					else
 					{
 						document.forms[0].firstName.readOnly=true;
						document.forms[0].middleName.readOnly=true;
						document.forms[0].lastName.readOnly=true;
						document.forms[0].fatherFirstName.readOnly=true;
						document.forms[0].fatherMiddleName.readOnly=true;
						document.forms[0].fatherLastName.readOnly=true;
						document.forms[0].motherFirstName.readOnly=true;
 		    	    	document.forms[0].maritalStatus.disabled=true;
 					}

 		    		if(document.forms[0].maritalStatus[0].checked)
					{
						document.forms[0].maritalStatus.readOnly=true;
					}
					else
					{
					}
 		
			 		if(document.forms[0].dnsReason.value!="")
			 		{
				 		 document.forms[0].dnsRequired.checked=true;
				 		 document.forms[0].dnsReason.disabled=false;
			 		}
			 		else
			 		{
				 		 document.forms[0].dnsRequired.checked=false;
				 		 document.forms[0].dnsReason.disabled=true;
			 		}
			 		//// Changes By Apurva on 30 oct ////
			 		if(document.forms[0].branch.value!="")
			 		{
				 		document.forms[0].branch.disabled=true;
				 		document.forms[0].relationShipManager.disabled=true;
			 		}
			 		////
	 		    <%}%>
				setTitle(document);
				if(document.forms[0].nameOfGroup.value.length>0 || document.forms[0].nameOfCompany.value.length>0  || document.forms[0].firstName.value.length>0)
				{
				    document.forms[0].custEntityType.disabled=true;
				}
				//document.forms[0].branch.disabled=true;
                  
			
	 			changeCustomerEntityType();
		
				document.forms[0].applicantType.style.backgroundColor="#ebf3ff";
				document.forms[0].relation.style.backgroundColor="#ebf3ff";
				
 				if(checkForApplicantType=="0" && checkForCustEntityType=="0")
		 		{
		 		  document.forms[0].relation.disabled=false;
	              document.getElementById("divCustEntityTypeInd").style.display="none";
		 		}
		 		else if(checkForApplicantType!="0" && checkForCustEntityType=="0")
		 		{
		 		  document.forms[0].relation.disabled=false;
		 		  document.getElementById("divCustEntityTypeCorp").style.display="none";
	              document.getElementById("divCustEntityTypeInd").style.display="none";
		 		}

		 		 if(checkForApplicantType != "1000000001")
		 		{			
				 
				
				 document.getElementById("td1").style.display="block";
			      //document.getElementById("td2").style.display="block";
			      document.getElementById("td3").style.display="block";
			      		 		}
		 		else
		 		{   		
				  document.getElementById("td1").style.display="none";
				
			     // document.getElementById("td2").style.display="none";
			      document.getElementById("td3").style.display="none";
			      
		 		} 		 		
				
	 			enterSpouse();
		 		document.getElementById("medicalInfo").style.display = "none";
		 		if(document.forms[0].custGroupType) 
		 		document.forms[0].custGroupType.value = document.forms[0].boardRepresentative.value;
		 		
		 		 if(document.forms[0].isExisting[0].checked==true)
		 		 { 
		 		   document.getElementById("existingDetailsTD").style.display = "none";
		 		 }
		 		 else
		 		 document.getElementById("existingDetailsTD").style.display = "block";
		 		 
		 		 document.forms[0].existingApplicantId.value = "";
		 		 
		 		 
		 		  if(document.forms[0].applicantType.value=="0"){
		 		    document.forms[0].applicantType.disabled=false;
		 		 }//noor change 
		 		 
		 		 if(document.forms[0].applicantType.disabled==true)
		 		 { 
		 		 <logic:notEmpty name="entityId">
		 		 <logic:equal name="entityId" value="1000000001">
		 		 
		 		    document.forms[0].isExisting[0].disabled=true;
		 		    document.forms[0].isExisting[1].disabled=true;
		 		    document.forms[0].existingApplicantId.readOnly=true;
		 		    document.getElementById("get").disabled=true;
		 		    document.getElementById("reset").disabled=true;
		 		    document.getElementById("search").disabled=true;		 		   
 					if(document.forms[0].formFlag.value=="Y")
 					{  //1.0.0.0.2  Ravi start
 					
		 		    	document.forms[0].panNo.readOnly=false;
		     	        document.forms[0].firstName.readOnly=false;
						document.forms[0].middleName.readOnly=false;
						document.forms[0].lastName.readOnly=false;
						document.forms[0].fatherFirstName.readOnly=false;
						document.forms[0].fatherMiddleName.readOnly=false;
						document.forms[0].fatherLastName.readOnly=false;
						document.forms[0].motherFirstName.readOnly=false;
				        document.forms[0].maritalStatus.disabled=false;
 		    	    	document.forms[0].title.disabled=false;
 		    	    	document.forms[0].gender.disabled=false;
 		    	    	document.forms[0].dateOfBirth.disabled=false;
 		    	    	document.forms[0].noOfDependents.readOnly=false;
	 		            document.forms[0].voterId.readOnly=false;
	 		            document.forms[0].passportNo.readOnly=false;
	 		            document.forms[0].panNo.readOnly=false;
	 		            document.forms[0].drivingLicenseNo.readOnly=false;
		 		    	document.forms[0].tanNo.disabled=false; 
		 		    	
		 		    	  //1.0.0.0.2  Ravi end
 					}
 					else
 					{
		 		    	document.forms[0].panNo.readOnly=true;
		     	        document.forms[0].firstName.readOnly=true;
						document.forms[0].middleName.readOnly=true;
						document.forms[0].lastName.readOnly=true;
						document.forms[0].fatherFirstName.readOnly=true;
						document.forms[0].fatherMiddleName.readOnly=true;
						document.forms[0].fatherLastName.readOnly=true;
						document.forms[0].motherFirstName.readOnly=true;
				        document.forms[0].maritalStatus.disabled=true;
	 		    	    document.forms[0].title.disabled=true;
	 		    	    document.forms[0].gender.disabled=true;
	 		    	    document.forms[0].dateOfBirth.disabled=true;
	 		    	    document.forms[0].noOfDependents.readOnly=true;
	 		            document.forms[0].voterId.readOnly=true;
	 		            document.forms[0].passportNo.readOnly=true;
	 		            document.forms[0].drivingLicenseNo.readOnly=true;
		 		    	document.forms[0].tanNo.disabled=true;
 					}
	     	        document.forms[0].boardRepresentative.disabled=true;
		 		    document.forms[0].btnzipPin.disabled=true;
		 		    
		 		    if(document.forms[0].dateOfBirth_link!=undefined)
		 		     document.forms[0].dateOfBirth_link.disabled=true;
 		            </logic:equal>
 		            </logic:notEmpty> 		           
		 		 }	
	        <logic:notEmpty name="applicantInfo" property="applicantId">
	 	    <logic:notEqual name="applicantInfo" property="applicantId" value="null" >	
	 	      <logic:notEqual name="applicantInfo" property="applicantId" value="0" >	
	 	          
	 	           document.getElementById("new").style.display = "none";
		 		   document.getElementById("newfield").style.display = "none";
		 		   document.getElementById("existingfield").style.display = "none";
		 		   document.getElementById("existing").style.display = "none"; 	  
	 	           document.getElementById("existingDetailsTD").style.display = "none";
	 	    </logic:notEqual>
	 	   </logic:notEqual>
	 	   </logic:notEmpty>
	 	     <logic:empty name="applicantInfo" property="applicantId" >	 	 
	 	   </logic:empty>
	 	   
	 	    <logic:notEmpty name="applicantInfo" property="applicantId">
	 	    <logic:equal name="applicantInfo" property="applicantId" value="null">	
	 	   </logic:equal>
	 	   </logic:notEmpty>
	 	   
	 	   <logic:notEmpty name="applicantInfo" property="applicantId">
	 	   <logic:equal name="applicantInfo" property="applicantId" value="0" >	
	 	   </logic:equal>
	 	   </logic:notEmpty>
	 	   changeNumberCommaSeparated(document.forms[0].authCapital);
	 	  cancelNewAddress(); 
	 	  document.forms[0].zipPin_temp.style.backgroundColor="#ebf3ff"; 

	 newFlagForLimit= "<%=request.getParameter("newflag")%>";
	 <logic:notEmpty name="llmSessionUserInformationDTO" property="productCollection">
	 <bean:define id="colProducts" name="llmSessionUserInformationDTO" property="productCollection"/>
	 // below code commented By ravi on 30 oct////
     /*   <logic:iterate id="itrProduct" name="colProducts" indexId="idxProduct">
      limitAppFlag="<bean:write name="itrProduct" property="limitApp"/>";
       if(limitAppFlag=="Y")
       {
        
        document.getElementById("new").disabled= true;
        if(document.getElementById("isExisting").value=='Y')
        document.getElementById("isExisting").checked= true;
        else
        document.getElementById("isExisting").checked= false;
       }
     </logic:iterate>  */
    </logic:notEmpty>     
   // alert("checkForCustEntityType ="+checkForCustEntityType+"  savePersonalInfo ="+savePersonalInfo);
   // 1.0.0.0.2 ravi start
    if(newFlagForLimit=="yes")
    {
      document.forms[0].isExisting[1].checked=true;
      toggleExistingDetails(true, document.forms[0].isExisting[1]);
       
    }
  //  Changes By Apurva on 31st oct
   //alert("temp_AssetCategory  "+document.forms[0].temp_AssetCategory.value+" temp_Competition  "+document.forms[0].temp_Competition.value)
    //document.getElementById("productInstrestedIn").value=document.forms[0].temp_Product.value;
   // getAssetCategory(document.forms[0].productInstrestedIn);//1.0.0.0
  
  
    // 1.0.0.0.2 ravi end
    // 1.0.0.1	 start
        <logic:notEmpty name="ajaxCall" >
	                  <logic:equal name="ajaxCall" value="N">
	                   disableAllElements();
				        </logic:equal>
	                  </logic:notEmpty>    // 1.0.0.1	 end
	                  
	                   setTimeout(populateassetCategory, 1000);
	                  document.forms[0].zipPin_temp.readOnly=true;//1.0.0.3  
	                  
	                  //1.0.0.8 start
	                  for(var i = 0 ; i < <%=assetCatGpDtls.size()%>; i++)
	  				{
	                	  document.getElementById("assetCategoryName"+i).disabled = true;
	  					if(<%=activityType.equalsIgnoreCase("APPLICANT DETAIL")%> || <%=activityType.equalsIgnoreCase("CUSTOMER")%>) //1.0.0.9
	  						{
	  							document.getElementById("gracePeriodVal"+i).disabled = false;
	  						}
	  					else 
	  						{
	  							document.getElementById("gracePeriodVal"+i).disabled = true;
	  						}
	  				}
	                  //1.0.0.8 end
	                  
	                  if(document.forms[0].custEntityType.value=='1000000002'){
	      		 	    	document.getElementById("beneficiaryOwnerDiv").style.display='block';
	      		 	  }else{
	      		 	    	document.getElementById("beneficiaryOwnerDiv").style.display='none';	
	      		 	  }
    }
    
    
    
    
    
     function enableFunctionOfDmPde(){
    if(temp_activityType=="DM PDE")
 						{
 						if(document.forms[0].custEntityType.value=="1000000001"){
 						$('#productDetailsDivId *').prop('disabled',true);
			$('#divCustEntityTypeInd *').prop('disabled',true);
			$('#divCustEntityTypeCorp *').prop('disabled',true);
			//$('#authorisedSignatoryDiv *').prop('disabled',false);//commented 1.0.0.10
				$('#keyContactsDiv *').prop('disabled',true);
			$('#qual1 *').prop('disabled',true);
			$('#custsegId *').prop('disabled',false);
			$('#custCategoryId *').prop('disabled',false);
			$('#identificationId *').prop('disabled',true);
			document.getElementById("divCommunicationInformation").style.display = "block";
			$('#divCommunicationInformation *').prop('disabled',false);
			//$('#destinationAddrDiv *').prop('disabled',true);
				//$('#mailingAddrDiv *').prop('disabled',true);
	//$('#sameAsDivId *').prop('disabled',true);
			$('#qual *').prop('disabled',false);
			document.getElementById("authorisedSignatoryDiv").style.display = "none";
				document.forms[0].adharNo.disabled=false;
				
 					}	
 					
 				if(document.forms[0].custEntityType.value=="1000000002"){
 				$('#productDetailsDivId *').prop('disabled',true);
			   $('#divCustEntityTypeInd *').prop('disabled',true);
			$('#divCustEntityTypeCorp *').prop('disabled',true);
			//$('#authorisedSignatoryDiv *').prop('disabled',false);//commented 1.0.0.10
				$('#keyContactsDiv *').prop('disabled',true);
			$('#qual1 *').prop('disabled',true);
			$('#custsegId *').prop('disabled',false);
			$('#custCategoryId *').prop('disabled',false);
			$('#identificationId *').prop('disabled',true);
			document.getElementById("divCommunicationInformation").style.display = "none";
			$('#divCommunicationInformation *').prop('disabled',false);
			//$('#destinationAddrDiv *').prop('disabled',true);
				//$('#mailingAddrDiv *').prop('disabled',true);
	//$('#sameAsDivId *').prop('disabled',true);
			$('#qual *').prop('disabled',false);
			document.getElementById("authorisedSignatoryDiv").style.display = "block";
				document.forms[0].tanNo.disabled=false;
				document.forms[0].corpTanNo.disabled=false;
				
 					}
 					
 					
 					
 					}
    
    
    }
    
    
    
    
	    function populateassetCategory()
	    {
	    document.getElementById("productInstrestedIn").value=document.forms[0].temp_Product.value;//1.0.0.0
	      document.getElementById("assetCategory").value=document.forms[0].temp_AssetCategory.value;
	      document.getElementById("competition").value=document.forms[0].temp_Competition.value;
	       
document.getElementById("legalFormTypeInd").value=document.forms[0].legalFormType.value;
document.getElementById("legalFormTypeCorp").value=document.forms[0].legalFormType.value;

    
	    }
	
    function fillCombo()
	{
	 	var i=1; 
	 	var length=document.forms[0].applicantType.length;
 		<logic:present name="applicantListColl" >
			<logic:notEmpty name="applicantListColl">
				<logic:iterate id="appList" name="applicantListColl">
    				for(var k=0 ; k<document.forms[0].applicantType.options.length; k++)
    				{
     					if(document.forms[0].applicantType.options[k].text=='<bean:write name="appList" property="applicantTypeName"/>')
	   					{
							document.forms[0].applicantType.options[k]=null;
	   					}
	  				}
					i++;
				</logic:iterate>
			</logic:notEmpty>
		</logic:present>
	}
	
	function disableAllElements()
	{
	     <logic:notEmpty name = "screenModes">
		    	if(('Y'== '<bean:write name = "screenModes" property="viewMode" />'))
			 {  for(count=0; count < document.forms[0].elements.length; count+=1)
			        {  	theelement = document.forms[0].elements[count];
			            if(theelement.name != null && theelement.name != "btn_one")
			            {
			            	theelement.disabled = true;
			            } 
			        }
   
			            
			      }
			    </logic:notEmpty> 
	}
   
  	   function disableAllElementsApp() //1.0.0.0.2	     Ravi
	    {
	    <%int ii=0;%>
	    	 <logic:notEmpty name = "screenModes">
		    	if(('Y'== '<bean:write name = "screenModes" property="viewMode" />'))
				{  for(count=0; count < document.forms[0].elements.length; count+=1)
			        {  	theelement = document.forms[0].elements[count];
			            if(theelement.name != null && theelement.name != "btn_one")
			            {
			            	theelement.disabled = true;
			            } } 
			            disabledSaveLink();  //1.0.0.0.2	     Ravi
			            }
			            
			  </logic:notEmpty>
			  <logic:notEmpty name = "StaticInfo">
			   <logic:notEqual name="StaticInfo" property="parentFlag" value="" >
			   for(count=0; count < document.forms[0].elements.length; count+=1)
			        {  	theelement = document.forms[0].elements[count];
			            if(theelement.name != null && theelement.name != "btn_one")
			            {
			            	theelement.disabled = true;
			            } 
			        }
   
			            
			       </logic:notEqual>
			    </logic:notEmpty>   
			      
		   	<logic:equal name="activityInfo" property="activityType" value="PDE">
		   	    <%if (application.getAttribute("photoUpload").equals("Y")) {%>  
				   	<logic:notEmpty name="StaticInfo"> 
                    <logic:equal name="StaticInfo" property="custEntityType" value="INDIVIDUAL">
					if(document.forms[0].btnPrint)
					document.forms[0].btnPrint.disabled=true;
					if(document.forms[0].btnCapture)
					document.forms[0].btnCapture.disabled=true;
				</logic:equal>
				</logic:notEmpty>
			   <%}%> 	
		<logic:notEmpty name="StaticInfo">
	   	<logic:equal name="StaticInfo" property="loanStatus" value="FORCLOSED">
			disableClosedForeClosedCase();
		</logic:equal>
		
		<logic:equal name="StaticInfo" property="loanStatus" value="CLOSED">
			disableClosedForeClosedCase();
		</logic:equal>		
		</logic:notEmpty>
			</logic:equal>
			<logic:equal name="applicantInfo" property="applicantId" value="0">
					if(document.forms[0].btnPrint)
					document.forms[0].btnPrint.disabled=true;
					if(document.forms[0].btnCapture)
					document.forms[0].btnCapture.disabled=true;					
			</logic:equal>
	    }
	 	

//Function to enable dnsReason text box if user clicked on dnsRequired checkbox	
	 	
		
	function checkFormCorp()
		{	<logic:notEmpty name="newDetail">
					checkMandatory();
					
				</logic:notEmpty>
				<logic:empty name="newDetail">
					
					applyValidation(document,"P");
				</logic:empty>
		
		}
						 	
	
		/* function saveExit()
		{	
			saveData("saveExitPersonalInfo");
		}
		
		function save()
		{
			
			saveData("savePersonalInfo");
		}
		 */
		
		<%-- function saveData(saveType)
		{      
			
			
					var objToFocus=null;
					tempData.errorMessages="Errors:";
					//alert("saveAddressInfo ="+saveAddressInfo+" savePersonalInfo ="+savePersonalInfo);
			         if(savePersonalInfo=="Y")
			          {
				            if(document.forms[0].custEntityType.value=="0")
			                {
			                   alert(("Please select customer entity type.").toUpperCase());
			                   return;
			                }
			                else if(document.forms[0].applicantType.value=="0")
			                {
			                   alert(("Please select applicant type.").toUpperCase());
			                   return;
			                }
			          } 

		            if(checkForApplicantType==0)
		            {
		               if(savePersonalInfo=="N")
		               {
		                 alert(("For new customer personal information is mandatory.").toUpperCase());
		                 return;
		               }
		            }

		            if(savePersonalInfo=="N" && saveAddressInfo=="N")
		            {
		            	
		              alert(("For saving data either personal or address should be editable.").toUpperCase());
		              return;
		            }
		            if(saveAddressInfo=="N")
		            {
		                disabledAddressField();
		            }
					 if(savePersonalInfo=="Y")
		            {
		             							    
							 checkForm(); 
						
					}
					
					
					 
					if(document.forms[0].custEntityType.value=="0" && document.forms[0].applicantType.value=="0")
					{
						if(document.forms[0].isExisting[0].checked==false && document.forms[0].isExisting[1].checked==false)
						{
							alert("PLEASE SELECT CUSTOMER CATEGORY.");
							return;
						}
					}
					
					/*validationOfCustomer();
					validationOfPermanent();
					validationOfOffice();*/
		         validateAuthorisedSignotoryRows();			       
				 saveAuthorisedSignotoryRows();
		
        
					if(saveAddressInfo=="Y")
		            {  
		                
		                addressValidateForm();
		              if(!checkMailingAddressStatus())
		              {
		              	return;
		              }
		             }
		            
					if(document.forms[0].heightInches.value > 11)
					{
						alert(("Maximum value in Inches can be 11.").toUpperCase());
						document.forms[0].heightInches.focus();
						return;
					}
				
				
		            if(tempData.errorMessages!="Errors:")
					{
						alert((tempData.errorMessages).replace("Errors:","").toUpperCase());
						return;
					}
					
					  if(document.forms[0].zipPin_temp.value!="" && document.forms[0].zipPin_temp.value!=null)
				   {
				   		if(document.forms[0].zipPin.value==""||document.forms[0].zipPin.value==null)
				   		{
				   			alert(("entered pincode is invalid.").toUpperCase());
				   			document.forms[0].zipPin_temp.value = "";
				   			document.forms[0].btnzipPin.disabled = false;
				   			return;
				   		}
				   }
				 	if(legalFormName=="")
				 	{ 
				 	  legalFormName="llaa.doc";
				 	}
	                document.forms[0].offCompanyName.disabled = false;
	                
	                
					var bool = confirm(("Proceeding to save changes.").toUpperCase());
					if(!bool)
					{
					    changeNumberCommaSeparated(document.forms[0].authCapital);
						 enableAddressField();
						 document.forms[0].addressType.disabled=true;
						
						 
						return;
					}
					else
					{  
					   
					  
						enableAllElements();	
						 
						if(document.getElementById("existing").style.display=="none")
						{ 
						   exisitingValue="";
						   document.forms[0].existingApplicantId.value = document.forms[0].applicantCode.value;
						   document.forms[0].isExisting.value = "Y";
						}	
						 else
						 {
						    if(document.forms[0].isExisting[0].checked==true)
						     {
						       exisitingValue="N";
						     }
						      else
						      {
						        exisitingValue="Y";
						       } 
						   	
						
						   	}
						  
						   		if(document.forms[0].psl.value=="0")
						   	{
						   		document.forms[0].psl.value="N";
						   	}
						   		
                        var screenId_temp='<%=request.getParameter("screenId")%>';
                        var mode_temp='<%=request.getParameter("mode")%>';
                       
						checkidentityfication();
						if(saveType=="savePersonalInfo"){
						document.forms[0].action = "personalInfo.do?actionPerformed=savePersonalInfo&savePersonalInfo="+ savePersonalInfo +"&saveAddressInfo="+saveAddressInfo+"&FN="+legalFormName+"&exisitingValue="+exisitingValue+"&screenId="+screenId_temp+"&mode="+mode_temp;							
						document.forms[0].method = "POST";
			  			document.forms[0].submit();	
			  			
			  			}else if(saveType=="saveExitPersonalInfo"){
			  			
			  			document.forms[0].action = "personalInfo.do?actionPerformed=saveExitPersonalInfo&savePersonalInfo="+ savePersonalInfo +"&saveAddressInfo="+saveAddressInfo+"&FN="+legalFormName+"&exisitingValue="+exisitingValue+"&screenId="+screenId_temp+"&mode="+mode_temp;
			  			document.forms[0].method = "POST";
			  			document.forms[0].submit();	
						}
			  						  					
			  			document.forms[0].addressType.disabled=true;
			  			document.forms[0].mailingAddressCheck.disabled=true;
			  			
			  		}
					
		} --%>
		
		
		
		
//Start 1.0.0.10
var beneficiaryOwnerRowIndex = 0;
var beneficiaryOwnerRowCount = 0;
var availableBeneficiaryOwnerRows="";
var dedupViewFlg = "N"; 
function beneficiaryOwnerRow(objId,objName,objPan,objEmail,objMobile,objAddress,objStateId,objCityId,objStateName,objCityName,objDedCustId,objDedSearchId,objDedInitFlag) //1.0.0.11
{

		var objIdArr = new Array();
		var nameArr = new Array();
		var panArr = new Array();
		var emailArr = new Array();
		var mobileArr = new Array();
		var addressArr = new Array();
		var stateIdArr = new Array();
		var cityIdArr = new Array();
		var stateNameArr = new Array();
		var cityNameArr = new Array();
		var dedCustIdArr = new Array();
		var dedSearchIdArr = new Array();
		
		var dedupeInitFlagArr = new Array(); //1.0.0.11
		
		var beneficiaryOwnerRowRowHtml = "";
		//beneficiaryOwnerRowRowHtml = beneficiaryOwnerRowRowHtml + "<div id='beneficiaryOwnerRowtable"+beneficiaryOwnerRowIndex+"' style='border:none; text-align:left;'>"; //1.0.0.11 commented
		//beneficiaryOwnerRowRowHtml = beneficiaryOwnerRowRowHtml + "<table  width='100%' class='main_body'>"; //1.0.0.11 commented
		beneficiaryOwnerRowRowHtml = beneficiaryOwnerRowRowHtml + "<tr id='beneficiaryOwnerRowtable"+beneficiaryOwnerRowIndex+"'>"; //1.0.0.11
		
	<logic:notEqual name="activityInfo" property="activityType" value="PDE">	
			beneficiaryOwnerRowRowHtml = beneficiaryOwnerRowRowHtml + "<td  width='2%' align='left'>";
			beneficiaryOwnerRowRowHtml = beneficiaryOwnerRowRowHtml + "<input type='checkbox' id='benCheckbox"+beneficiaryOwnerRowIndex+"' value='"+beneficiaryOwnerRowIndex+"' onclick='toggleBeneficiaryOwnerRow(this);'/>";
			
			beneficiaryOwnerRowRowHtml = beneficiaryOwnerRowRowHtml + "</td>";
			
	</logic:notEqual>	
		
	
	beneficiaryOwnerRowRowHtml = beneficiaryOwnerRowRowHtml + "<td width='12%' align='left' id='benViewDedupTd"+beneficiaryOwnerRowIndex+"' style='display:none;'>";
	//1.0.0.11
	beneficiaryOwnerRowRowHtml = beneficiaryOwnerRowRowHtml + "<span' align='left' id='benDedupeInitFlagTd"+beneficiaryOwnerRowIndex+"' style='display:none;'>";
	beneficiaryOwnerRowRowHtml = beneficiaryOwnerRowRowHtml + "&nbsp;&nbsp;"+objDedInitFlag+"&nbsp;&nbsp;";
	beneficiaryOwnerRowRowHtml = beneficiaryOwnerRowRowHtml + "</span>";
	//1.0.0.11
	beneficiaryOwnerRowRowHtml = beneficiaryOwnerRowRowHtml + "<input type='button' class='blueBotton' name='benDedupeInitViewBtn"+beneficiaryOwnerRowIndex+"' id='benDedupeInitViewBtn"+beneficiaryOwnerRowIndex+"' value='View' onclick=\"javascript:appDedupeInitView('BENEFICIARY',"+beneficiaryOwnerRowIndex+");\" />";
	beneficiaryOwnerRowRowHtml = beneficiaryOwnerRowRowHtml + "</td>";
		
		beneficiaryOwnerRowRowHtml = beneficiaryOwnerRowRowHtml + "<td width='12%' align='left'>";
		beneficiaryOwnerRowRowHtml = beneficiaryOwnerRowRowHtml + "<input type='hidden' id='beneficiaryOwnerId"+beneficiaryOwnerRowIndex+"' value='"+objId+"' /><input type='hidden' id='beneficiaryOwnerDedCustId"+beneficiaryOwnerRowIndex+"' value='"+objDedCustId+"' /><input type='hidden' id='beneficiaryOwnerDedSearchId"+beneficiaryOwnerRowIndex+"' value='"+objDedSearchId+"' />";
		beneficiaryOwnerRowRowHtml = beneficiaryOwnerRowRowHtml + "<input type='text' size='14' maxlength='50' id='beneficiaryOwnerName"+beneficiaryOwnerRowIndex+"' style='width:110 px;' value='"+objName+"'  />";//1.0.0.12
		beneficiaryOwnerRowRowHtml = beneficiaryOwnerRowRowHtml + "</td>";
		
		
		beneficiaryOwnerRowRowHtml = beneficiaryOwnerRowRowHtml + "<td width='12%' align='left'>";
		beneficiaryOwnerRowRowHtml = beneficiaryOwnerRowRowHtml + "<input type='text' size='14' maxlength='20' id='beneficiaryOwnerPan"+beneficiaryOwnerRowIndex+"' style='width:110 px;' value='"+objPan+"'  />";
		beneficiaryOwnerRowRowHtml = beneficiaryOwnerRowRowHtml + "</td>";
		
		
		beneficiaryOwnerRowRowHtml = beneficiaryOwnerRowRowHtml + "<td width='12%' align='left'>";
		beneficiaryOwnerRowRowHtml = beneficiaryOwnerRowRowHtml + "<input type='text' size='14' maxlength='50' id='beneficiaryOwnerEmail"+beneficiaryOwnerRowIndex+"' style='width:110 px;' value='"+objEmail+"'  />";//1.0.0.12
		beneficiaryOwnerRowRowHtml = beneficiaryOwnerRowRowHtml + "</td>";
		
		
		
		
		beneficiaryOwnerRowRowHtml = beneficiaryOwnerRowRowHtml + "<td width='12%' align='left'>";
		beneficiaryOwnerRowRowHtml = beneficiaryOwnerRowRowHtml + "<input type='text' size='14' maxlength='10' id='beneficiaryOwnerMobile"+beneficiaryOwnerRowIndex+"' style='width:110 px;' value='"+objMobile+"' onkeypress='return ( event.charCode >= 48 && event.charCode <= 57 )' />";
		beneficiaryOwnerRowRowHtml = beneficiaryOwnerRowRowHtml + "</td>";
		
		beneficiaryOwnerRowRowHtml = beneficiaryOwnerRowRowHtml + "<td width='12%' align='left'>";
		beneficiaryOwnerRowRowHtml = beneficiaryOwnerRowRowHtml + "<input type='text' size='14' maxlength='50' id='beneficiaryOwnerAddress"+beneficiaryOwnerRowIndex+"' style='width:110 px;' value='"+objAddress+"' />";
		beneficiaryOwnerRowRowHtml = beneficiaryOwnerRowRowHtml + "</td>";
		
		
		beneficiaryOwnerRowRowHtml = beneficiaryOwnerRowRowHtml + "<td width='12%' align='left'>";
		beneficiaryOwnerRowRowHtml = beneficiaryOwnerRowRowHtml + "<input type='hidden' id='beneficiaryOwnerState"+beneficiaryOwnerRowIndex+"' name='beneficiaryOwnerState"+beneficiaryOwnerRowIndex+"' value='"+objStateId+"' /><input type='text' size='14' maxlength='10' id='beneficiaryOwnerState"+beneficiaryOwnerRowIndex+"_temp' name='beneficiaryOwnerState"+beneficiaryOwnerRowIndex+"_temp' style='width:110 px;' value='"+objStateName+"' disabled='true' /><input type='button' class='blueBotton' name='btnState' value='...' onclick=\"javascript:beneficiaryOwnStateChooser('QM_STATEMASTER','"+beneficiaryOwnerRowIndex+"');\">";
		beneficiaryOwnerRowRowHtml = beneficiaryOwnerRowRowHtml + "</td>";
		
		beneficiaryOwnerRowRowHtml = beneficiaryOwnerRowRowHtml + "<td width='12%' align='left'>";
		beneficiaryOwnerRowRowHtml = beneficiaryOwnerRowRowHtml + "<input type='hidden' id='beneficiaryOwnerCity"+beneficiaryOwnerRowIndex+"' name='beneficiaryOwnerCity"+beneficiaryOwnerRowIndex+"' value='"+objCityId+"' /><input type='text' size='14' maxlength='50' id='beneficiaryOwnerCity"+beneficiaryOwnerRowIndex+"_temp' name='beneficiaryOwnerCity"+beneficiaryOwnerRowIndex+"_temp' style='width:110 px;' value='"+objCityName+"' disabled='true' /><input type='button' class='blueBotton' name='btnCity' value='...' onclick=\"javascript:beneficiaryOwnCityChooser('QM_CITYMASTER','"+beneficiaryOwnerRowIndex+"');\">";
		beneficiaryOwnerRowRowHtml = beneficiaryOwnerRowRowHtml + "</td>";

		beneficiaryOwnerRowRowHtml = beneficiaryOwnerRowRowHtml + "</tr>";
		//beneficiaryOwnerRowRowHtml = beneficiaryOwnerRowRowHtml + "</table>"; //1.0.0.11 commented
		//beneficiaryOwnerRowRowHtml = beneficiaryOwnerRowRowHtml + "</div>"; //1.0.0.11 commented
		
		
		for(var i=0; i<beneficiaryOwnerRowIndex; i++)
		{
			if(document.getElementById("beneficiaryOwnerId"+i))
			{
				objIdArr[i] = document.getElementById("beneficiaryOwnerId"+i).value;
				nameArr[i] = document.getElementById("beneficiaryOwnerName"+i).value;
				panArr[i] = document.getElementById("beneficiaryOwnerPan"+i).value;
				emailArr[i] = document.getElementById("beneficiaryOwnerEmail"+i).value;
				mobileArr[i] = document.getElementById("beneficiaryOwnerMobile"+i).value;
				addressArr[i] = document.getElementById("beneficiaryOwnerAddress"+i).value;
				stateIdArr[i] = document.getElementById("beneficiaryOwnerState"+i).value;
				cityIdArr[i] = document.getElementById("beneficiaryOwnerCity"+i).value;
				stateNameArr[i] = document.getElementById("beneficiaryOwnerState"+i+"_temp").value;
				cityNameArr[i] = document.getElementById("beneficiaryOwnerCity"+i+"_temp").value;
				dedCustIdArr[i] = document.getElementById("beneficiaryOwnerDedCustId"+i).value;
				dedSearchIdArr[i] = document.getElementById("beneficiaryOwnerDedSearchId"+i).value;
				dedupeInitFlagArr[i]= document.getElementById("benDedupeInitFlagTd"+i).value; //1.0.0.11
		 	}
		}
		
		document.getElementById("beneficiaryOwnerRowsDiv").innerHTML = document.getElementById("beneficiaryOwnerRowsDiv").innerHTML + beneficiaryOwnerRowRowHtml;
		
		for(var i=0; i<beneficiaryOwnerRowIndex; i++)
		{
		    if(objIdArr[i]!=undefined)
		   	{
		   	document.getElementById("beneficiaryOwnerId"+i).value = objIdArr[i];
		 	document.getElementById("beneficiaryOwnerName"+i).value = nameArr[i];
		  	document.getElementById("beneficiaryOwnerPan"+i).value = panArr[i];
		 	document.getElementById("beneficiaryOwnerEmail"+i).value = emailArr[i];
		 	document.getElementById("beneficiaryOwnerMobile"+i).value = mobileArr[i];
		 	document.getElementById("beneficiaryOwnerAddress"+i).value = addressArr[i];
		 	document.getElementById("beneficiaryOwnerState"+i).value = stateIdArr[i];
		 	document.getElementById("beneficiaryOwnerCity"+i).value = cityIdArr[i];
		 	document.getElementById("beneficiaryOwnerState"+i+"_temp").value = stateNameArr[i];
		 	document.getElementById("beneficiaryOwnerCity"+i+"_temp").value = cityNameArr[i];
		 	document.getElementById("beneficiaryOwnerDedCustId"+i).value = dedCustIdArr[i];
		 	document.getElementById("beneficiaryOwnerDedSearchId"+i).value = dedSearchIdArr[i];
		 	document.getElementById("benDedupeInitFlagTd"+i).value = dedupeInitFlagArr[i]; //1.0.0.11
		 	}
		}
		//populateDesignation(document.getElementById("authSignDesignation"+authSignRowIndex));
		//document.getElementById("authSignDesignation"+authSignRowIndex).value=objDesignation;
				
	    if(objId!="null" && objId!="" && objId!=null){
	    	document.getElementById("benViewDedupTH").style.display="block";
	    	document.getElementById("benDedupeInitFlagTd"+beneficiaryOwnerRowIndex).style.display="block"; //1.0.0.11
     		document.getElementById("benViewDedupTd"+beneficiaryOwnerRowIndex).style.display="inline";
     		dedupViewFlg = "Y";
     	}else{
     		if(dedupViewFlg == "Y"){
	     		document.getElementById("benViewDedupTH").style.display="block";
	     		//document.getElementById("benDedupeInitFlagTd"+beneficiaryOwnerRowIndex).style.display="block"; //1.0.0.11
	     		document.getElementById("benViewDedupTd"+beneficiaryOwnerRowIndex).style.display="block";
	     		document.getElementById("benDedupeInitViewBtn"+beneficiaryOwnerRowIndex).style.display="none";
     		}
     	}
			
		availableBeneficiaryOwnerRows = availableBeneficiaryOwnerRows + "~" + beneficiaryOwnerRowIndex + "~";	
		beneficiaryOwnerRowIndex++;
		beneficiaryOwnerRowCount++;

}
		
		
		function beneficiaryOwnStateChooser(tName,rowNo){
			var sessionStatus = checkinterval(sessionTimeout);
			if(sessionStatus=="Y"){
				window.open("populateMaster.do?qString="+tName+"&label=State&dependant=beneficiaryOwnerState"+rowNo+"&rowNo="+rowNo+"&cleanDependentItems=T~city_temp,&rowNum=1","popState","width=500, height=490,top=100,left=100, location=no, maximize=yes, menubar=no, status=no, toolbar=no, scrollbars=no, resizable=no","modal=yes" );
			}else{
			<%request.setAttribute("sessionExpiry",
				"Your session has expired!! Please login again.");%>
				document.forms[0].action = "userAuthAction.do?dispatchMethod=logout";
				document.forms[0].method = "post";	
				document.forms[0].submit();
			}
		}
		
		 function beneficiaryOwnCityChooser(tName,rowNo){
		 	      var stateId = document.getElementById("beneficiaryOwnerState"+rowNo).value;
		 	      var paramList = tName+"~"+stateId;
		 	      var sessionStatus = checkinterval(sessionTimeout);
		 		if(sessionStatus=="Y"){
		 			window.open("populateMaster.do?qString="+paramList+"&label=City&dependant=beneficiaryOwnerCity"+rowNo+"&rowNo="+rowNo+"&rowNum=1","popCity","width=500, height=490,top=100,left=100, location=no, menubar=no, status=no, toolbar=no, scrollbars=no, resizable=no");
		 		}else{
		 			<%request.setAttribute("sessionExpiry", "Your session has expired!! Please login again.");%>
		 			document.forms[0].action = "userAuthAction.do?dispatchMethod=logout";
		 	 		document.forms[0].method = "post";	
		 	  		document.forms[0].submit();
		 		}
		 }
		 
		 function authSignStateChooser(tName,rowNo){
				var sessionStatus = checkinterval(sessionTimeout);
				if(sessionStatus=="Y"){
					window.open("populateMaster.do?qString="+tName+"&label=State&dependant=authSignState"+rowNo+"&rowNo="+rowNo+"&cleanDependentItems=T~authSignCity"+rowNo+"_temp,T~authSignPinCode"+rowNo+"_temp&rowNum=1","popState","width=500, height=490,top=100,left=100, location=no, maximize=yes, menubar=no, status=no, toolbar=no, scrollbars=no, resizable=no","modal=yes" );
					clearCityBeforeSubmit(rowNo); //1.0.0.21
					clearPincodeBeforeSubmit(rowNo); //1.0.0.21
				}else{
				<%request.setAttribute("sessionExpiry",
					"Your session has expired!! Please login again.");%>
					document.forms[0].action = "userAuthAction.do?dispatchMethod=logout";
					document.forms[0].method = "post";	
					document.forms[0].submit();
				}
			}
			
		 function clearCityBeforeSubmit(rowNo) { //1.0.0.21
			    var cityField = document.getElementById("authSignCity" + rowNo);
			    if (cityField) { cityField.value = ""; }
			}
			 function clearPincodeBeforeSubmit(rowNo) { //1.0.0.21
			    var pincodeField = document.getElementById("authSignPinCode" + rowNo);
			    if (pincodeField) { pincodeField.value = ""; }
			}
			 function authSignCityChooser(tName,rowNo){
			 	      var stateId = document.getElementById("authSignState"+rowNo).value;
			 	      var paramList = tName+"~"+stateId;
			 	      var sessionStatus = checkinterval(sessionTimeout);
			 		if(sessionStatus=="Y"){
			 			window.open("populateMaster.do?qString="+paramList+"&label=City&dependant=authSignCity"+rowNo+"&rowNo="+rowNo+"&cleanDependentItems=T~authSignPinCode"+rowNo+"_temp&rowNum=1","popCity","width=500, height=490,top=100,left=100, location=no, menubar=no, status=no, toolbar=no, scrollbars=no, resizable=no"); //1.0.0.21
			 		}else{
			 			<%request.setAttribute("sessionExpiry", "Your session has expired!! Please login again.");%>
			 			document.forms[0].action = "userAuthAction.do?dispatchMethod=logout";
			 	 		document.forms[0].method = "post";	
			 	  		document.forms[0].submit();
			 		}
			 }

			 function authSignPincodeChooser(tName,rowNo){ //1.0.0.21
			 	     var cityId = document.getElementById("authSignCity"+rowNo).value;
			 	      var paramList = tName+"~"+cityId;
			 	      var sessionStatus = checkinterval(sessionTimeout);
			 		if(sessionStatus=="Y"){
			 			window.open("populateMaster.do?qString="+paramList+"&label=Pincode&dependant=authSignPinCode"+rowNo+"&rowNo="+rowNo+"&rowNum=1","popCity","width=500, height=490,top=100,left=100, location=no, menubar=no, status=no, toolbar=no, scrollbars=no, resizable=no");
			 		}else{
			 			<%request.setAttribute("sessionExpiry", "Your session has expired!! Please login again.");%>
			 			document.forms[0].action = "userAuthAction.do?dispatchMethod=logout";
			 	 		document.forms[0].method = "post";	
			 	  		document.forms[0].submit();
			 		} 
			 }
		 
		 function populateBeneficiaryOwnerRowStr()
		  {
		    var beneficiaryOwnerId = "";
		    var beneficiaryOwnerName = "";
		    var beneficiaryOwnerPan = "";
		    var beneficiaryOwnerEmail = "";
		    var beneficiaryOwnerMobile = "";
		    var beneficiaryOwnerAddress = "";
		    var beneficiaryOwnerStateId = "";
		    var beneficiaryOwnerCityId = "";
		    var beneficiaryOwnerStateName = "";
		    var beneficiaryOwnerCityName = "";
		    var beneficiaryOwnerDedCustId = "";
		    var beneficiaryOwnerDedSearchId = "";
		    var beneficiaryOwnerDedInitFlag=""; //1.0.0.11
		    
		    <logic:notEmpty name ="beneficiaryOwnerDatalist">
			     <logic:iterate id="itrkeyContact" name="beneficiaryOwnerDatalist">
			     	 beneficiaryOwnerId="<bean:write name="itrkeyContact" property="beneficiaryOwnerId"/>";
				     beneficiaryOwnerName="<bean:write name="itrkeyContact" property="beneficiaryOwnerName"/>";
				     beneficiaryOwnerPan="<bean:write name="itrkeyContact" property="beneficiaryOwnerPan"/>";
				     beneficiaryOwnerEmail="<bean:write name="itrkeyContact" property="beneficiaryOwnerEmail"/>";
				     beneficiaryOwnerMobile="<bean:write name="itrkeyContact" property="beneficiaryOwnerMobile"/>";
				     beneficiaryOwnerAddress="<bean:write name="itrkeyContact" property="beneficiaryOwnerAddress"/>";
				     beneficiaryOwnerStateId="<bean:write name="itrkeyContact" property="beneficiaryOwnerStateId"/>";
				     beneficiaryOwnerCityId="<bean:write name="itrkeyContact" property="beneficiaryOwnerCityId"/>";
				     beneficiaryOwnerStateName="<bean:write name="itrkeyContact" property="beneficiaryOwnerStateName"/>";
				     beneficiaryOwnerCityName="<bean:write name="itrkeyContact" property="beneficiaryOwnerCityName"/>";
				     beneficiaryOwnerDedCustId="<bean:write name="itrkeyContact" property="beneficiaryOwnerDedCustId"/>";
				     beneficiaryOwnerDedSearchId="<bean:write name="itrkeyContact" property="beneficiaryOwnerDedSearchId"/>";
				     beneficiaryOwnerDedInitFlag="<bean:write name="itrkeyContact" property="beneficiaryOwnerDedInitFlag"/>"; // dedupe
				      
				     beneficiaryOwnerRow(beneficiaryOwnerId,beneficiaryOwnerName,beneficiaryOwnerPan,beneficiaryOwnerEmail,beneficiaryOwnerMobile,beneficiaryOwnerAddress,beneficiaryOwnerStateId,beneficiaryOwnerCityId,beneficiaryOwnerStateName,beneficiaryOwnerCityName,beneficiaryOwnerDedCustId,beneficiaryOwnerDedSearchId,beneficiaryOwnerDedInitFlag); //1.0.0.11
		 		
				  </logic:iterate>   
		   </logic:notEmpty>
		   
		   // <logic:empty name ="beneficiaryOwnerDatalist">
		   	//beneficiaryOwnerRow(beneficiaryOwnerId,beneficiaryOwnerName,beneficiaryOwnerPan,beneficiaryOwnerEmail,beneficiaryOwnerMobile,beneficiaryOwnerAddress,beneficiaryOwnerStateId,beneficiaryOwnerCityId,beneficiaryOwnerStateName,beneficiaryOwnerCityName,beneficiaryOwnerDedCustId,beneficiaryOwnerDedSearchId);
		  // </logic:empty> 
		    
		  }
		  
		 
		 
		//End 1.0.0.10
		
	 </script>

</head>
</html:html>
