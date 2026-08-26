 <!--  HEADER INFO
+  File NAME 	: views/los/personalinfo.jsp
+  PURPOSE		: 
+  CREATED BY	: 
+  CREATION DATE	: 
+  INITIAL VERSION : 1.0.0.0
+  **********************************************************************************************************************************
+  VERSION NO   UPDATED BY       UPDATED ON      REASON FOR CHANGE
+  1.0.0.0.2	     Ravi		  29-Mar-2016		   Add co applicant 
+  1.0.0.1	        Ravi   		  20-Jul-2016	 CR:- Add customer service
+  1.0.0.0.3	    Vibhash   	  15-Sep-2016	   bug. 35828 (indian Should come by default in Nationality and it should be in read only mode)
+  1.0.0.0.4	    MAck   		  15-Sep-2016	   bug:- 35823 (In Mifin system age categories 18 to 75 but at the time current Applicant Age below 80)
+  1.0.0.0.5        Ravi		  16- sep          bug. 35822
+  1.0.0.0.6        Rahul		  22-Jun-2017      CR (GST)
+  1.0.0.0.7        Rahul		  22-Jun-2017      DOB validation disable on PDE
+  1.0.0.0.8        Vibhash       6-july-2017      Need to make GSTIN No. non mandatory in GST Address 
+  1.0.0.0.9       Apurva Shukla 10-jan-2018      Addres1,Addr2,Addr3 sequencing,and fname,mname,lname sequencing 
+  2.0.0.0.0       Apurva Shukla  17-jan-2018     Change made for gst showing igst% in Dm module 
+  2.0.0.0.1       Apurva Shukla  18-jan-2018     Change made for gst adding sez and igst Applicable tag in Dm module (Address)
+  2.0.0.0.2       Apurva Shukla  18-jan-2018     Change made for gst in dm module (  for Billing Address details) 
+  2.0.0.0.3       Apurva Shukla  30-jan-2018     for adding co-lesse functionality in dm module
+  2.0.0.0.4       Apurva Shukla  05-feb-2018     52344
+  2.0.0.0.5       Apurva Shukla  05-feb-2018    52352
+  2.0.0.0.6       Apurva Shukla  07-feb-2018    52349
+  1.0.0.7	       Apurva shukla  04 june 2018       58295 QA bug regarding cin,tin and tan length  
+  1.0.0.8	       Apurva shukla  06 june 2018     product Dependent on lob in customer module  
+  1.0.0.9         Apurva shukla  14th june 2018   54413 
+  1.0.0.10        Apurva shukla  19th june 2018   56749    
+  1.0.0.11        Apurva shukla  21th june 2018   bugid-58936 changes made for quotation module  
+  1.0.0.12        Apurva shukla  21th june 2018    bugId-56747 ( Co-lessee or gurantor can't be same as lessee handled from frontEnd)
+  1.0.0.13        Apurva shukla  03 july 2018       pinCode issue UAT CR  
+  1.0.0.14        Apurva shukla  03 july 2018       pinCode issue UAT CR  code comment because its not working in dm lesse
+  1.0.0.15        apurva Shukla  05 july 2018    QA bugId 54413 existing and get button shoul be same in line
+  1.0.0.16        apurva Shukla  25 july 2018    company PAN mandatory in case of non-indivisual
+  1.0.0.17        Ravi            31-july-2018     CR for add validation incase for changing in IGST per.
   1.0.0.18        Ravi      	        08 Nov  2019            code change to add kyc risk category
#  1.0.0.19	Ravinder Kaur	 18-Feb-2020	 Asset Restructuring CR: Changes for DM View Mode.
+  1.0.0.20        Ravi            07-Apr-2020     code change for disabled  same as field
+  1.0.0.21			Himanshu		04-Jun-2021		PINCODE MUST BE MANDATORY
+ 1.0.0.22			Himanshu		09-Jun-2021		Special character validation and field must be mandatory for STATE CITY PINCODE
+ 1.0.0.23   20-Aug-2021          Hemant Kumar		Added new condition to disable Save, Save&Exit link for dm quotation
+ 1.0.0.24   07-Feb-2022          Priyanka Soni		For GST address remove validation of Mobile,No. of years and months
+ 1.0.0.25   23-DEC-2022          Hemant Kumar		KYC compliance CR
+   1.0.0.26		Priyanka Soni 	 23-Jan-2023	 Aadhar OTP Verification: Bad request (Need to change request json structure)
+  1.0.0.27			Priyanka Soni 	 	08-Apr-2023	  	Dedupe of authorised signatory and beneficiary owner should be mandatory process
+  1.0.0.28			Ravi Shankar		29-DEC-2023		Addhar pan changes
+  1.0.0.29         Narottam Biswal     04-JAN-2024     CR- LPI details to be added in applicant details screen
+  1.0.0.30         Nalin Kumar Jena    22-FEB-2024     CR-Passport No varification.
+  1.0.0.31         Narottam Biswal     22-FEB-2024     CR-DL varification.
+  1.0.0.32         Tanisha Agarwal     22-FEB-2024     CR-Voter ID varification.
+  1.0.0.33         Ravi Shankar        27-FEB-2024          CR-Udyam Registrationvarification
+  1.0.0.34      Tanisha Agarwal       19-JUNE-2024     DM PDE applicant need to be revisited for validation
+  1.0.0.35      Tanisha Agarwal       08-JUL-2024          CR-Udyam Registrationvarification For invidual customer
+  1.0.0.36      Narottam Biswal       27-Nov-2025            PAN  verification geen/red tick
+  1.0.0.37      Ravi Shankar          09-Apr-2026            GST Registration Status
+  **********************************************************************************************************************************
 --> 
<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@page import="qc.sso.dto.UserInfoDTO"%>
<%@page import="qc.llm.dto.*"%>
<%@ include file="../include/includeTld.inc"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
	<title>miFIN </title>
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
		.col-lg-3.col-md-3.col-sm-6.col-xs-6.pdnglt{
			padding-left:107px !important;
		}
		
		/* 1.0.0.30 start for Passport popup screen */
		.form-popup {
		  display: none;
		  position: fixed;
		  bottom: 55%;
		  right: 15px;
		  left: 15px;
		  border: 3px solid #519dc0;
		  z-index: 9;
		  background-color: white;
		}
		/* 1.0.0.30 End */
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
	<script type="text/javascript" src="js/genericValidationlos.js"></script>
	
	<script src="js/angular.min.js"></script>
	<script type="text/javascript">document.write(getCalendarStyles());</script>
	<script type="text/javascript" ID="js19">
			var cal19 = new CalendarPopup();
			cal19.showYearNavigation();
			cal19.showYearNavigationInput();
			 var app = angular.module('applicantDetailApp', []);
		</script>

	<script type="text/javascript">
	var limitAppFlag="";
	var newFlagForLimit;
	var newflag='<%=request.getAttribute("newflag")%>'; 

	var disableCustomerRegField='<%=request.getAttribute("disableCustomerRegField")%>';//1.0.0.37
	var gstAddressAdded='<%=request.getAttribute("gstAddressAdded")%>';//1.0.0.37
	
	var exisitingValue="";
	var signatoryDataDetails = "";
	var product_ID_select="";
	var referenceFlag="";	//1.0.0.0.5
	var keyContactsDetails="";
	var entityId="";
	var temp_activityType="";
	var temp_View_Mode="N"; // 1.0.0.20 start
	var beneficiaryOwnerDetails = "";//1.0.0.25
	 <logic:notEmpty name = "screenModes">
		temp_View_Mode= '<bean:write name = "screenModes" property="viewMode" />';
	 </logic:notEmpty> //1.0.0.20
	<logic:notEmpty name="entityId" > 
	 entityId="<bean:write name="entityId"/>";
	 </logic:notEmpty>
	 <logic:notEmpty name="activityInfo" >
	    temp_activityType="<bean:write name="activityInfo" property="activityType"/>";
	  </logic:notEmpty>
	<% int k=0;%>
	var applicantTypeListArr = new Array();
	<logic:notEmpty name="APPLICANTTYPECOLLECTION" >
		<logic:iterate id="APPLICANTTYPECOLLECTIONID" name="APPLICANTTYPECOLLECTION">
			applicantTypeListArr[<%=k%>] = new Array(3);
			applicantTypeListArr[<%=k%>][0] = "<bean:write name="APPLICANTTYPECOLLECTIONID" property="masterId"/>" ;
			applicantTypeListArr[<%=k%>][1] = "<bean:write name="APPLICANTTYPECOLLECTIONID" property="masterName"/>" ;
			applicantTypeListArr[<%=k++%>][2] = "<bean:write name="APPLICANTTYPECOLLECTIONID" property="mandatoryFlag"/>" ;
		</logic:iterate>
	</logic:notEmpty>
	
	// change by viplou for drop down
	<%int j=0;%>
	
	<%int h=0;%>
	var genderTypeArr=new Array();
	<logic:notEmpty name="GENDERLISTCOLLECTION">
	<logic:iterate id="itrGenderType" name="GENDERLISTCOLLECTION" >
	genderTypeArr[<%=h%>]=new Array(2);
	genderTypeArr[<%=h%>][0]="<bean:write name="itrGenderType" property="masterId"/>";
	genderTypeArr[<%=h++%>][1]="<bean:write name="itrGenderType" property="masterName" />";
	</logic:iterate>
	</logic:notEmpty>

	var designationTypeArr=new Array();
	
			<logic:notEmpty name="colDesignationTypeDTOs">

			<logic:iterate id="itrDesignationType" name="colDesignationTypeDTOs" >
			    	designationTypeArr[<%=j%>]=new Array(2);
			    	designationTypeArr[<%=j%>][0]="<bean:write name="itrDesignationType" property="masterId"/>";
			    	designationTypeArr[<%=j++%>][1]="<bean:write name="itrDesignationType" property="masterName" />";
		    </logic:iterate>
		    </logic:notEmpty>
	<logic:notEmpty name = "authorisedSignatoryData">
		signatoryDataDetails = "<bean:write name="authorisedSignatoryData"/>";
	</logic:notEmpty>
	
	//Start 1.0.0.25
	<logic:notEmpty name = "beneficiaryOwnerData">
	beneficiaryOwnerDetails = "<bean:write name="beneficiaryOwnerData"/>";
</logic:notEmpty>//End 1.0.0.25
	
	/*------Apurva Changes------- */
	<logic:notEmpty name = "keyContactsData">
		keyContactsDetails = "<bean:write name="keyContactsData"/>";
	</logic:notEmpty>
	/* ------------------------------------ */
	var sessionTimeout = <%=session.getMaxInactiveInterval()%>
		var imgStatus=null;
		var imageFound = "F"; 
		/*************************************  start address  ***********************/
			var savePersonalInfo="N";
			var saveAddressInfo="N";
			var checkForFirstName;
			var checkForNameOfCompany;
			var checkForApplicantType;
			var validateAddress="N";// Apurva Changes on 20-feb-2018
			var ApplicantAddressArr=new Array();
	   //Added By Rinky 
	   var isApplicant=true;
	   <logic:equal   name="screenId" value="1000000005" scope="session">  
		isApplicant=true;
	  </logic:equal>
	  <logic:equal   name="screenId" value="1000001694" scope="session">  
		isApplicant=true;
	  </logic:equal>
	  <logic:equal   name="screenId" value="1000001693" scope="session">  
		isApplicant=false;
	  </logic:equal> 			
			<% int count=0;%>
			<logic:notEmpty name="colAddressInfo">
				<logic:iterate id="itrApplicantAddress" name="colAddressInfo" indexId="idxApplicantAddress">
			    	ApplicantAddressArr[<%=count%>]=new Array(35);
			    	var phone1STDLandLine = ("<bean:write name="itrApplicantAddress" property="phoneNo1"  />").split("-");
			    	var phone2STDLandLine = ("<bean:write name="itrApplicantAddress" property="phone2"  />").split("-");
			    	ApplicantAddressArr[<%=count%>][0]="<bean:write name="itrApplicantAddress" property="applicantAddressId"/>";
					ApplicantAddressArr[<%=count%>][1]="<bean:write name="itrApplicantAddress" property="addressType"/>";
			    	ApplicantAddressArr[<%=count%>][2]="<bean:write name="itrApplicantAddress" property="sameAs"/>";
			    	ApplicantAddressArr[<%=count%>][3]="<bean:write name="itrApplicantAddress" property="accomodationType"  />";
			    	ApplicantAddressArr[<%=count%>][4]="<bean:write name="itrApplicantAddress" property="mailingAddress"/>";
					ApplicantAddressArr[<%=count%>][5]="<bean:write name="itrApplicantAddress" property="flatHouseNo"/>";
			    	ApplicantAddressArr[<%=count%>][6]="<bean:write name="itrApplicantAddress" property="floorNo"/>";
			    	ApplicantAddressArr[<%=count%>][7]="<bean:write name="itrApplicantAddress" property="locality"  />";
			    	ApplicantAddressArr[<%=count%>][8]="<bean:write name="itrApplicantAddress" property="landMark"/>";
			    	ApplicantAddressArr[<%=count%>][9]="<bean:write name="itrApplicantAddress" property="city"/>";
			    	ApplicantAddressArr[<%=count%>][10]="<bean:write name="itrApplicantAddress" property="state"  />";
			    	ApplicantAddressArr[<%=count%>][11]="<bean:write name="itrApplicantAddress" property="zipPin"/>";
					ApplicantAddressArr[<%=count%>][12]="<bean:write name="itrApplicantAddress" property="extention1"/>";
			    	ApplicantAddressArr[<%=count%>][13]="<bean:write name="itrApplicantAddress" property="extension2"/>";
			    	ApplicantAddressArr[<%=count%>][14]="<bean:write name="itrApplicantAddress" property="phone2"  />";
			    	ApplicantAddressArr[<%=count%>][15]="<bean:write name="itrApplicantAddress" property="mobile"/>";
					ApplicantAddressArr[<%=count%>][16]="<bean:write name="itrApplicantAddress" property="email"/>";
			    	ApplicantAddressArr[<%=count%>][17]="<bean:write name="itrApplicantAddress" property="fax"/>";
			    	ApplicantAddressArr[<%=count%>][18]="<bean:write name="itrApplicantAddress" property="stdIsd"  />";
			    	ApplicantAddressArr[<%=count%>][19]="<bean:write name="itrApplicantAddress" property="buildingName"  />";
			    	ApplicantAddressArr[<%=count%>][20]="<bean:write name="itrApplicantAddress" property="phoneNo1"  />";
			    	ApplicantAddressArr[<%=count%>][21]="<bean:write name="itrApplicantAddress" property="stateName"  />";
			    	ApplicantAddressArr[<%=count%>][22]="<bean:write name="itrApplicantAddress" property="cityName"  />";
			    	ApplicantAddressArr[<%=count%>][23]=phone1STDLandLine[0];
			    	ApplicantAddressArr[<%=count%>][24]=phone1STDLandLine[1];
			    	ApplicantAddressArr[<%=count%>][25]=phone2STDLandLine[0];
			    	ApplicantAddressArr[<%=count%>][26]=phone2STDLandLine[1];
			    	ApplicantAddressArr[<%=count%>][27]="<bean:write name="itrApplicantAddress" property="noOfYears"  />";
			    	ApplicantAddressArr[<%=count%>][28]="<bean:write name="itrApplicantAddress" property="zipPin_temp"  />";
			    	ApplicantAddressArr[<%=count%>][29]="<bean:write name="itrApplicantAddress" property="noOfMonthAtResidence"  />";
			    	ApplicantAddressArr[<%=count%>][30]="<bean:write name='itrApplicantAddress' property='gstinNo'  />";   // 1.0.0.0.6
			    	ApplicantAddressArr[<%=count%>][31]="<bean:write name="itrApplicantAddress" property="placeOfSupplyFlag"  />";
			    	ApplicantAddressArr[<%=count%>][32]="<bean:write name="itrApplicantAddress" property="applicantAddressName"/>";
			    	ApplicantAddressArr[<%=count%>][33]="<bean:write name="itrApplicantAddress" property="moduleType"/>";
			    	ApplicantAddressArr[<%=count++%>][34]="<bean:write name="itrApplicantAddress" property="sez"/>";
			    	
			    </logic:iterate>
			</logic:notEmpty>
			
				/*------------ Changes By Apurva------------ */
		var branchArr = new Array();
   		var relationshipManagerArr = new Array();
   		var channelNameArr = new Array();
   		var lobArr= new Array();
   		var contactTypeArr= new Array();
   		var legalFormArr = new Array();
   		
   		
   		
   		<% count =0;%>
    <logic:notEmpty name ="colRelationshipManagerMaster">
    <logic:iterate id="itrRelationshipManagerMaster" name="colRelationshipManagerMaster">
      relationshipManagerArr[<%=count%>]=new Array(3);
       relationshipManagerArr[<%=count%>][0]="<bean:write name="itrRelationshipManagerMaster" property="masterName"/>"; 
      relationshipManagerArr[<%=count%>][1]="<bean:write name="itrRelationshipManagerMaster" property="masterId"/>";         
      relationshipManagerArr[<%=count++%>][2]="<bean:write name="itrRelationshipManagerMaster" property="masterParentId" filter="false" />";
    </logic:iterate>
    </logic:notEmpty>
    
    	<% count =0;%>
    <logic:notEmpty name ="collobMaster">
    <logic:iterate id="itrLobMaster" name="collobMaster">
     lobArr[<%=count%>]=new Array(2);
     lobArr[<%=count%>][0]="<bean:write name="itrLobMaster" property="masterId"/>"; 
     lobArr[<%=count++%>][1]="<bean:write name="itrLobMaster" property="masterName"/>"; 
    
    </logic:iterate>
    </logic:notEmpty>
    
    	<% count =0;%>
    <logic:notEmpty name ="colContactTypeMaster">
    <logic:iterate id="itrContactTypeMaster" name="colContactTypeMaster">
     contactTypeArr[<%=count%>]=new Array(3);
     contactTypeArr[<%=count%>][0]="<bean:write name="itrContactTypeMaster" property="masterId"/>";
     contactTypeArr[<%=count%>][1]="<bean:write name="itrContactTypeMaster" property="masterName"/>"; 
     contactTypeArr[<%=count++%>][2]="<bean:write name="itrContactTypeMaster" property="masterParentId" filter="false" />";
    </logic:iterate>
    </logic:notEmpty>
    
    
     	
     	<% count =0;%>
    <logic:notEmpty name ="colChannelNameMaster">
    <logic:iterate id="itrChannelNameMaster" name="colChannelNameMaster">
      channelNameArr[<%=count%>]=new Array(3);
      channelNameArr[<%=count%>][0]="<bean:write name="itrChannelNameMaster" property="masterName"/>";
      channelNameArr[<%=count%>][1]="<bean:write name="itrChannelNameMaster" property="masterId"/>";         
      channelNameArr[<%=count++%>][2]="<bean:write name="itrChannelNameMaster" property="masterParentId" filter="false" />";
      
    </logic:iterate>
    </logic:notEmpty> 
    
    
     <% int countt =0;%>
    <logic:notEmpty name ="colLegalFormType">
    <logic:iterate id="itrLegalFormType" name="colLegalFormType">
      legalFormArr[<%=countt%>]=new Array(3);
       legalFormArr[<%=countt%>][0]="<bean:write name="itrLegalFormType" property="masterName"/>"; 
      legalFormArr[<%=countt%>][1]="<bean:write name="itrLegalFormType" property="masterId"/>";         
      legalFormArr[<%=countt++%>][2]="<bean:write name="itrLegalFormType" property="masterParentId" filter="false" />";
    </logic:iterate>
    </logic:notEmpty>
    
    
    
     function clickCustEntityType(obj)
		{   
		
		var customerEntityId = obj;
		//alert(customerEntityId);
		clearcombo(document.forms[0].legalFormTypeCorp);
		clearcombo(document.forms[0].legalFormTypeInd);
		//alert("hi");
		for (i=0; i< legalFormArr.length; i++)
	    {
	    //alert(legalFormArr[i][2]);
	    	if (customerEntityId == legalFormArr[i][2])
	    	
	    	{  
	    	if(customerEntityId == '1000000002'){
	    		document.forms[0].legalFormTypeCorp.options[document.forms[0].legalFormTypeCorp.options.length]= new Option(legalFormArr[i][0] ,legalFormArr[i][1] );
	    	   // document.getElementById('relationshipManager').value=SchemeArr[i][3];
	    	}
	    	if(customerEntityId == '1000000001'){
	    		document.forms[0].legalFormTypeInd.options[document.forms[0].legalFormTypeInd.options.length]= new Option(legalFormArr[i][0] ,legalFormArr[i][1] );
	    	   // document.getElementById('relationshipManager').value=SchemeArr[i][3];
	    	}
	    	
	    	}
	    }
	    }
    
    
    
    
    
				
				function setTitleForDropDown(obj)//2.0.0.0.6
		{
		   
		   obj.title = obj.options[obj.selectedIndex].text; 
		}
										
			function clickIgstApplicable(IgstApplicableCheckBox)/* 2.0.0.0.0 */ 
			{
				if(IgstApplicableCheckBox.value=='Y')
				{	
				
					var quotationId='<%=request.getAttribute("quotationId")%>';
					var entityId=document.forms[0].entityIdForDm.value;
					var destinationStateId=document.forms[0].state.value;
					var finalInputString=destinationStateId +"~"+entityId+"~"+""+"~"+""+"~"+""+"~"+""+"~"+""+"~"+""+"~"+""+"~"+quotationId ;
										
					$.ajax({
											type : "post",
											url : "personalInfo.do?actionPerformed=getLeaseGstPercent",
											context : document.body,
											data : "inputString="+finalInputString,
											success : function(response) 
											{
												if(response!=null && response!="") 
		  										{
		  										var jsonStr = JSON.parse(response);
		  											//console.log(jsonStr);
		  											if(jsonStr.STATUS==="S")
		  											{
													document.forms[0].igstpercent.value=jsonStr.PN_IGST_PER;
													}									
												}
											}		   
						   });
		   		}else{
						   document.forms[0].igstpercent.value="0";
				   	 }	
				   	 if(IgstApplicableCheckBox.value=='0'){
				   	 document.forms[0].igstpercent.value="";
				   	 }
			}
						//// Added By Apurva on 16th jan 2018 end

			     function load(){
					 if(entityId!="1000000008"){
			    		 document.forms[0].dateOfIncorporation.style.backgroundColor="#ebf3ff";
			    	 }
			     
			      document.forms[0].sez.value="N";
			     
	if(entityId!="" && entityId!=null && (entityId=="1000000003" || entityId=="1000000005"))
	{
	document.forms[0].entityIdForDm.value=entityId;
	document.forms[0].nationality.disabled=false;  // 1.0.0.0.1	     Ravi
	/// Apurva Changes //
	document.getElementById("qual1").style.display = "block";
	document.getElementById("keyContactsDiv").style.display = "block";
	
	<logic:notEmpty name="StaticInfo">
		  <logic:equal name="StaticInfo" property="customerEntityType" value="NON-INDIVIDUAL">
		  
	document.getElementById("identificationId").style.display = "none";
	document.getElementById("custsegId").style.display = "none";
	</logic:equal>
	</logic:notEmpty>
	/* 1.0.0.11 start */
	if(entityId=="1000000005")
	{
	  if(document.forms[0].custEntityType.value=="1000000002")
	  {
			document.getElementById("identificationId").style.display = "none";
			document.getElementById("custsegId").style.display = "none";
	  }
	}
	/* 1.0.0.11 end */
	//document.getElementById("productDetailsDivId").disabled=true;
	if(entityId=="1000000003"){
	document.forms[0].dateOfBirth.style.backgroundColor="#ebf3ff";
	document.forms[0].maritalStatus.style.backgroundColor="#ebf3ff";
	document.forms[0].gender.style.backgroundColor="#ebf3ff";
	document.forms[0].fatherFirstName.style.backgroundColor="#ffffff";
	document.forms[0].fatherLastName.style.backgroundColor="#ffffff";
	
	}
	
	
	}	
	document.forms[0].panNo.disabled=false;
	 <logic:notEmpty name ="activityInfo" property ="viewMode" >
	<logic:equal name ="activityInfo" property="activityName" value ="VIEWER">
	document.forms[0].panNo.disabled=true;
	</logic:equal>
	</logic:notEmpty>
	
	if(entityId!="" && entityId!=null && (entityId!="1000000003" && entityId!="1000000005"))
	{
	document.getElementById("qual1").style.display = "none";
	document.getElementById("keyContactsDiv").style.display = "none";
	document.getElementById("branchId").style.display = "none";
	document.getElementById("relationshipManagerDivId").style.display = "none";
	document.getElementById("productDetailsDivId").style.display = "none";
	///document.getElementById("lobId").style.display = "none";
	//document.getElementById("lobIDNonInd").style.display = "none";
	document.getElementById("lobIdUpdated").style.display = "none";/* 1.0.0.8  */
	document.getElementById("divCommunicationInformation").style.display = "none";
	
	}
	
	////// Apurva changes for DM on 14 Nov /////
	
	if(entityId!="" && entityId!=null && entityId=="1000000008")
	{
	
	<logic:notEmpty name="colApplicantAddressInfoDm">
	//document.getElementById("addAddressButtonId").disabled =true;
	</logic:notEmpty>
	if(document.forms[0].applicantType.value!="1000000002"){
	
	document.forms[0].entityIdForDm.value='<bean:write name="entityId"/>';
	document.forms[0].panNo.disabled=true;
	document.forms[0].nationality.disabled=true;  // 1.0.0.0.1	     Ravi
	$('#productDetailsDivId *').prop('disabled',true);
	$('#divCustEntityTypeInd *').prop('disabled',true);
	$('#divCustEntityTypeCorp *').prop('disabled',true);
	$('#authorisedSignatoryDiv *').prop('disabled',true);
	$('#beneficiaryOwnerDiv *').prop('disabled',true);//1.0.0.25
	$('#keyContactsDiv *').prop('disabled',true);
	$('#qual1 *').prop('disabled',true);
	$('#custsegId *').prop('disabled',true);
	$('#custCategoryId *').prop('disabled',true);
	$('#identificationId *').prop('disabled',true);
	$('#destinationAddrDiv *').prop('disabled',true);
	$('#mailingAddrDiv *').prop('disabled',true);
	//$('#sameAsDivId *').prop('disabled',true);
	$('#qual *').prop('disabled',true);
	//$('#lobIdUpdated *').prop('disabled',true);
	}
	 
	document.getElementById("qual1").style.display = "block";
	document.getElementById("keyContactsDiv").style.display = "block";
	document.getElementById("branchId").style.display = "block";
	document.getElementById("relationshipManagerDivId").style.display = "block";
	document.getElementById("productDetailsDivId").style.display = "block";
	document.getElementById("authorisedSignatoryDiv").style.display = "block";
	//document.getElementById("lobId").style.display = "none";
	//document.getElementById("lobIDNonInd").style.display = "none";
	document.getElementById("lobIdUpdated").style.display = "none";/* 1.0.0.8  */
	
	if(document.forms[0].custEntityType.value=="1000000002"){
	document.getElementById("identificationId").style.display = "none";
	document.getElementById("legalFormTypeInd").style.display = "none";
	document.getElementById("constitutiondivInd").style.display = "none";
	}
	
	if(document.forms[0].applicantType.value=="1000000002"){
	document.forms[0].branch.disabled=false;
	document.forms[0].relationShipManager.disabled=false;
	document.getElementById("keyContactsDiv").style.display = "none";
	document.getElementById("authorisedSignatoryDiv").style.display = "none";
	if(document.forms[0].custEntityType.value=="1000000002"){
	document.getElementById("identificationId").style.display = "none";
	}
	}
	
	
	}
	<logic:equal name="entityId" value="1000000001">
	
	document.getElementById("qual1").style.display = "none";
	document.getElementById("keyContactsDiv").style.display = "none";
	document.getElementById("branchId").style.display = "none";
	document.getElementById("relationshipManagerDivId").style.display = "none";
	document.getElementById("productDetailsDivId").style.display = "none";
	//document.getElementById("lobId").style.display = "none";
	//document.getElementById("lobIDNonInd").style.display = "none";
	document.getElementById("lobIdUpdated").style.display = "none";/* 1.0.0.8  */
	document.getElementById("divCommunicationInformation").style.display = "none";
	</logic:equal>
	
	if(entityId!="1000000008"){
	document.getElementById("gstId").style.display ="none";
	document.getElementById("sezZoneDivId").style.display ="none";
	document.getElementById("new").style.display = "none";
	document.getElementById("newfield").style.display = "none";
	 
	}
	// 1.0.0.16 start
	if(document.forms[0].applicantType.value==1000000002)
	{
		document.forms[0].companyPan.style.backgroundColor="#ffffff";				
	}
	// 1.0.0.16 end
	
	//document.forms[0].zipPin_temp.readOnly=true;//1.0.0.13 & 1.0.0.14
	///////////////////////////////////////////////////////////////////////////////////
	
	///////
	
						///Apurva Changes on 22nd dec 2017 //
	                    disableAllElementsApp();
	//start 1.0.0.29
	let appActionId=sessionStorage.getItem("setActionID");
	 if(appActionId=="1200004005" || appActionId=="1200004002"){
	document.forms[0].lpiRateType.disabled=true;
		  document.forms[0].lpiRate.disabled=true;
	} 
	//end 1.0.0.29
	
	// Start 1.0.0.30
	if(document.forms[0].custEntityType.value=="1000000001")
		{
		if(document.forms[0].passportNoVerifiedFlag.value=="Y")
			{
			 document.getElementById("passporVerifiedIcon").style.display="inline";
			 document.getElementById("passporVerifiedIcon").disabled = false;
			}
		else if(document.forms[0].passportNoVerifiedFlag.value=="N")
			{
			 document.getElementById("passportVerifiedPendingIcon").style.display="inline";
			 document.getElementById("passportVerifiedPendingIcon").disabled = false;
			}
		}
	// End 1.0.0.30
	
	
	//Start 1.0.0.31
	if(document.forms[0].custEntityType.value=="1000000001")
		{
		if(document.forms[0].dlVarifyFlag.value=="Y")
			{
			 document.getElementById("DLVerifiedIcon").style.display="inline";
			 document.getElementById("DLVerifiedIcon").disabled = false;
				
			}
		else if(document.forms[0].dlVarifyFlag.value=="N")
			{
			  document.getElementById("DLVerifiedPendingIcon").style.display="inline";
			 document.getElementById("DLVerifiedPendingIcon").disabled = false;
			}
		}
	//End 1.0.0.31
	
	
	//start 1.0.0.32
	if(document.forms[0].custEntityType.value=="1000000001")
		{

		if(entityId=="1000000003"){
		if(disableCustomerRegField!=null && disableCustomerRegField!="" && disableCustomerRegField=="Y" 
			&& document.forms[0].gstRegistrationStatus && document.forms[0].gstRegistrationStatus.value=="Y"){
			document.forms[0].gstRegistrationStatus.disabled = true;
			}
		}//1.0.0.37
		
		if(document.forms[0].voterIdFlg.value=="Y")
			{
			 document.getElementById("voterVerifiedIcon").style.display="inline";
			 document.getElementById("voterVerifiedIcon").disabled = false;
				
			}
		else if(document.forms[0].voterIdFlg.value=="N")
			{
			 document.getElementById("voterVerifiedPendingIcon").style.display="inline";
			 document.getElementById("voterVerifiedPendingIcon").disabled = false;
			}
		}
	//End 1.0.0.32
	
	//start 1.0.0.33
	if(document.forms[0].custEntityType.value=="1000000002")
		{
		if(entityId=="1000000003"){
		if(disableCustomerRegField!=null && disableCustomerRegField!="" && disableCustomerRegField=="Y"
			&& document.forms[0].gstRegistrationStatusCorp && document.forms[0].gstRegistrationStatusCorp.value=="Y"){
			document.forms[0].gstRegistrationStatusCorp.disabled = true;
			}
		}//1.0.0.37
		
		if(document.forms[0].udyamIdFlg.value=="Y")
			{
			 document.getElementById("udyamVerifiedIcon").style.display="inline";
			 //document.getElementById("voterVerifiedIcon").disabled = false;
				
			}
		else if(document.forms[0].udyamIdFlg.value=="N")
			{
			 document.getElementById("udyamVerifiedPendingIcon").style.display="inline";
			 //document.getElementById("voterVerifiedPendingIcon").disabled = false;
			}
		}else{//1.0.0.35 start
			document.forms[0].udyamNoInd.value=document.forms[0].udyamNo.value;
			if(document.forms[0].udyamIdFlg.value=="Y")
			{
			 document.getElementById("udyamVerifiedIcon1").style.display="inline";
			 //document.getElementById("voterVerifiedIcon").disabled = false;
				
			}
		else if(document.forms[0].udyamIdFlg.value=="N")
			{
			 document.getElementById("udyamVerifiedPendingIcon1").style.display="inline";
			 //document.getElementById("voterVerifiedPendingIcon").disabled = false;
			}
		}//End 1.0.0.35
	//End1.0.0.33
	
	
	              
	                     ///Apurva Changes on 22nd dec 2017 //
	           //alert(document.forms[0].custEntityType.value);  
	                  
	     clickCustEntityType(document.forms[0].custEntityType.value); 
	     enableFunctionOfDmPde(); 
	     
	     <logic:notEmpty name="adharOkycCompFlag">
			<logic:equal name="adharOkycCompFlag" value="Y">
		    	 document.forms[0].adharNo.disabled="true"
		    	document.getElementById("aadhaarVerifiedIcon").style.display="none"
		     </logic:equal>
		    	 
		    <logic:equal name="adharOkycCompFlag" value="N">
		    	if(document.getElementById("adharNo").value == "")
					document.getElementById("aadhaarVerifiedPendingIcon").style.display="none"
				else
					document.getElementById("verifyAadhaarBtn").style.display="none"
			</logic:equal>		    	 
	     </logic:notEmpty>
	    //1.0.0.34
	     if( document.forms[0].custEntityType.value=="1000000001" && entityId =="1000000008"){
       	  document.getElementById('customerSegment').disabled=true;
       	document.getElementById('legalFormTypeInd').disabled=true;
       	document.forms[0].quotationCode.disabled=true;
       	document.forms[0].custCategory.disabled=true;  
       	document.forms[0].lpiRateType.disabled=true; 
    	document.forms[0].lpiRate.disabled=true;
    	document.getElementById('adharNo').disabled=true;   
    	document.forms[0].dnsRequired.disabled=true;  
    	document.forms[0].dnsReason.disabled=true;  
    	document.forms[0].preferredTimeToCall.disabled=true;  
    	document.forms[0].preferredModeOfCommunication.disabled=true;  
    	document.forms[0].religion.disabled=true; 
    	document.forms[0].qualificationId.disabled=true;
    	document.forms[0].caste.disabled=true;  
    	document.forms[0].psl.disabled=true; 
    	
    	       

             }
	     if( document.forms[0].custEntityType.value=="1000000002" && entityId =="1000000008"){
	    		document.forms[0].custCategory.disabled=true; 
	    		 document.getElementById('customerSegment').disabled=true; 
	    		 document.forms[0].corpTanNo.disabled=true; 
	    		 document.forms[0].quotationCode.disabled=true;
	    		 document.forms[0].lpiRateType.disabled=true; 
	    	    	document.forms[0].lpiRate.disabled=true;

		     }
	     //start 1.0.0.36
	     if(document.forms[0].custEntityType.value=="1000000001"){
	     if(document.forms[0].panVarifiedFlag.value=="Y"){
	    	 document.getElementById("PanVerifiedIcon").style.display="inline";
				document.getElementById("PanVerifiedIcon").disabled = false;
				document.getElementById("panVerifiedPendingIcon").style.display="none";
				document.getElementById("panVerifiedPendingIcon").disabled = true;
				document.forms[0].panNo.disabled=true;
		     }else{
		    	 document.getElementById("panVerifiedPendingIcon").style.display="inline";
					document.getElementById("panVerifiedPendingIcon").disabled = false;
					document.getElementById("PanVerifiedIcon").style.display="none";
					document.getElementById("PanVerifiedIcon").disabled = true;
		     }
	     }
	     if(document.forms[0].custEntityType.value=="1000000002"){
	     if(document.forms[0].panVarifiedFlagComp.value=="Y"){
	    	 document.getElementById("PanVerifiedIconComp").style.display="inline";
				document.getElementById("PanVerifiedIconComp").disabled = false;
				document.getElementById("panVerifiedPendingIconComp").style.display="none";
				document.getElementById("panVerifiedPendingIconComp").disabled = true;
				document.forms[0].companyPan.disabled=true;
		     }else{
		    	 document.getElementById("panVerifiedPendingIconComp").style.display="inline";
					document.getElementById("panVerifiedPendingIconComp").disabled = false;
					document.getElementById("PanVerifiedIconComp").style.display="none";
					document.getElementById("PanVerifiedIconComp").disabled = true;
		     }
	     }
	     
	     //end 1.0.0.36
	     
}
   	  
	
var validationData = new Array();

     <%
					  int i = 0;
				      java.util.HashMap map=(java.util.HashMap)application.getAttribute("mapScreenFieldsXRules");
				      System.out.println("Screen id "+request.getAttribute("screenId").toString());
				      java.util.ArrayList list=(java.util.ArrayList)map.get((String)request.getAttribute("screenId"));
				      if(list!=null)
				      {
				      		java.util.Iterator itr=list.iterator();
						     while(itr.hasNext())
						     {
						     	qc.common.dto.ScreenFieldsXRulesDTO  screenFieldsXRulesDTO=(qc.common.dto.ScreenFieldsXRulesDTO)itr.next();
						     	%>
						     	 	validationData[<%=i%>] =new Array(11);
						     	 	try
						     	 	{
						     	 		validationData[<%=i%>][0]      = "<%=screenFieldsXRulesDTO.getScreenId() %>";
	                                    validationData[<%=i%>][1]      = "<%=screenFieldsXRulesDTO.getJspObjectName() %>";
							     		validationData[<%=i%>][2]      = "<%=screenFieldsXRulesDTO.getViewMode() %>";
							     		validationData[<%=i%>][3]      = "<%=screenFieldsXRulesDTO.getCondition() %>";
							     		validationData[<%=i%>][4]      = "<%=screenFieldsXRulesDTO.getActionName() %>";
							     		validationData[<%=i%>][5]      = "<%=screenFieldsXRulesDTO.getActive() %>";
							     		validationData[<%=i%>][6]      = "<%=screenFieldsXRulesDTO.getJspObjectKey() %>";
							       		validationData[<%=i%>][7]      = "<%=screenFieldsXRulesDTO.getJspObjectResource() %>";
							     		validationData[<%=i%>][8]      = "<%=screenFieldsXRulesDTO.getValidationType() %>";
							     		validationData[<%=i%>][9]      = '<bean:message bundle="<%=screenFieldsXRulesDTO.getJspObjectResource()%>" key = "<%=screenFieldsXRulesDTO.getJspObjectKey()%>"/>';
							     		validationData[<%=i++%>][10]   = "<%=screenFieldsXRulesDTO.getScreenCondition() %>";
							     	}
							     	catch(err)
							     	{
							     	}
							     	
						     	<%
						     }
				      	
				      }
%>
	    applicationDate = '<bean:write name="losSessionInformationDTO" property="applicationDate"/>';
  	  		
	
		
				var ageMin;
				var ageMax;
				var SchemeArr = new Array();
				 <%i =0;%>
  <%i =0;%>
   <logic:notEmpty name="colProdSchemeMaster">
	<logic:iterate id="itrScheme" name="colProdSchemeMaster">
    	SchemeArr[<%=i%>]=new Array(4);    	
		SchemeArr[<%=i%>][0]='<bean:write name="itrScheme" property="masterSchemeId"/>';    	
    	SchemeArr[<%=i%>][1]='<bean:write name="itrScheme" property="minAge"/>';
    	SchemeArr[<%=i%>][2]='<bean:write name="itrScheme" property="maxAge"/>';    
    	SchemeArr[<%=i++%>][3]='<bean:write name="itrScheme" property="productCategoryCode"/>';
    </logic:iterate></logic:notEmpty>
    
  
//added by nishant	 	
function closeWindow()
{
	window.close();
}	 	

 	   
	   function addressValidateForm()
	   {
		  if(validateAddress!="Y"){//apurva changes on 20th feb 2018
	   	
	   	applyValidation(document,"A");
	   	
	   	
	   	if(document.forms[0].sameAs.selectedIndex==0)
	   	{
	   		
	   		
	   		atLeastOneFieldBetweenThree(document.forms[0].flatHouseNo,document.forms[0].buildingName,document.forms[0].floorNo,'Address 1','Address 2','Address 3');
	   		
	   		if(document.getElementById("nameOfCompanyNew").style.display!="none")
	   		{
	   		
	   			if(IsAlphaNumericWithSpecialSymbolNew(document.forms[0].offCompanyName,'Name Of Company of office address'))
	   			{
	   			  return false;
	   			}
	   		}
	   		
	   		//comented by 1.0.0.22
	    		/* newValidateAdd(document.forms[0].flatHouseNo,"NAV",'<bean:message bundle="losApplicationResource" key="los.addressInfo.flatNo"/>');
	    		newValidateAdd(document.forms[0].buildingName,"NAV",'<bean:message bundle="losApplicationResource" key="los.addressInfo.buildingName"/>');
	    		newValidateAdd(document.forms[0].floorNo,"NAV",'<bean:message bundle="losApplicationResource" key="los.addressInfo.floorNo"/>');
	    		newValidateAdd(document.forms[0].locality,"NAV",'<bean:message bundle="losApplicationResource" key="los.addressInfo.locality"/>');
	    		newValidateAdd(document.forms[0].landMark,"NAV",'Land Mark '); */
	    //comented end  by 1.0.0.22		
	    		//add by 1.0.0.22
	    		onlyValidSpecialCharacter(document.forms[0].flatHouseNo,'<bean:message bundle="losApplicationResource" key="los.addressInfo.flatNo"/>');
	    		onlyValidSpecialCharacter(document.forms[0].buildingName,'<bean:message bundle="losApplicationResource" key="los.addressInfo.buildingName"/>');
	    		onlyValidSpecialCharacter(document.forms[0].floorNo,'<bean:message bundle="losApplicationResource" key="los.addressInfo.floorNo"/>');
	    		onlyValidSpecialCharacter(document.forms[0].locality,'<bean:message bundle="losApplicationResource" key="los.addressInfo.locality"/>');
	    		onlyValidSpecialCharacter(document.forms[0].landMark,'Land Mark ');
	    		//end by 1.0.0.22
	    		//Changes made for bug id 55692 on 30 march 2018 start
	    		
	    		//1.0.0.24
	    		if(document.forms[0].addressType.value!="1000000010"){
	    		validate(document.forms[0].mobile,"TMV",'Mobile');
				validate(document.forms[0].noOfYearAtResidence,"TMV",'No of years');
				validate(document.forms[0].noOfMonthAtResidence,"TMV",'No of months');
	    		}
	    		//1.0.0.24 end
	    		
	    		//1.0.0.37
	    		var gstRegistrationAdd="";
	    		if(document.forms[0].custEntityType.value=="1000000001"){
	    			gstRegistrationAdd=document.forms[0].gstRegistrationStatus.value;
	    		}else{
	    			gstRegistrationAdd=document.forms[0].gstRegistrationStatusCorp.value;
		    		}
	    		
	    		if(entityId=="1000000003"){
				if(gstRegistrationAdd=="N"){
					if(document.forms[0].addressType.value=="1000000010" ){
						tempData.errorMessages = tempData.errorMessages +"\n"+"GST ADDRESS CANNOT BE ADDED BECAUSE THE GST REGISTRATION STATUS IS SET TO UNREGISTERED.";		
						}

					if(document.forms[0].gstinNo && document.forms[0].gstinNo.value.length>0){
						tempData.errorMessages = tempData.errorMessages +"\n"+"GSTIN No. CANNOT BE ADDED BECAUSE THE GST REGISTRATION STATUS IS SET TO UNREGISTERED.";
					  }
					}


				if(disableCustomerRegField!=null && disableCustomerRegField!="" && disableCustomerRegField=="Y"){
					if(gstRegistrationAdd=="Y" && gstAddressAdded!="Y" 
						&& document.forms[0].gstinNo && document.forms[0].gstinNo.value.length<=4){
						tempData.errorMessages = tempData.errorMessages +"\n"+"AT LEAST ONE ADDRESS MUST CONTAIN A GSTIN No. WHEN GST REGISTRATION STATUS IS REGISTERED.";
					  }
					}
	    		} //1.0.0.37
	    		
				//Changes made for bug id 55692 on 30 march 2018 end
				
				if(document.forms[0].entityIdForDm.value=="1000000008"){
					if(document.forms[0].addressType.value=="1200000008"){
					//validate(document.forms[0].sez,"CMV",'SEZ ZONE');
				  if(document.forms[0].sez.value=="Y"){
				  //validate(document.forms[0].igstApplicable,"CMV",'IGST PERCENT APPLICABLE');//1.0.0.8
				  //validate(document.forms[0].igstpercent,"TMV",'IGST PERCENT');//1.0.0.8
					}
				//validate(document.forms[0].lutNo,"TMV",'LUT NO.');
				//validate(document.forms[0].temp_loaCheckBox,"CMV",'LOA');
					}
				}
				/*if(entityId=="1000000003")
				{
					if(document.forms[0].addressType.value=="1000000010")
					{
						validate(document.forms[0].gstinNo,"TMV",'GSTIN No.');
						
					}
				}*/
	    		
	    		
	    		 // code changes for add city and pin code validation start 1.0.0.10
                
                if(document.forms[0].applicantType.value!="1000000001") // non mandatory only in case of lesse
            	{
                	//coment by 1.0.0.21
                	//validate(document.forms[0].city_temp,"TMV",'City');
					///validate(document.forms[0].zipPin_temp,"TMV",'Pincode');
					//coment end  by 1.0.0.21
            	}
				
				//add by 1.0.0.21
                	validate(document.forms[0].city_temp,"TMV",'City');
						validate(document.forms[0].zipPin_temp,"TMV",'Pincode');
				//end by 1.0.0.21
                // code changes for add city and pin code validation  end 1.0.0.10
				//1.0.0.24
	    		if(document.forms[0].addressType.value!="1000000010"){
	    			validate(document.forms[0].residenceStatus,"CMV",'Occupancy Status');
	    		}
	    		//1.0.0.24 end
	   		
	   		checkMinLength(document.forms[0].zipPin_temp,'<bean:message bundle="losApplicationResource" key="los.addressInfo.zip"/>',6);
	   		if(!checkBlankWithoutMessage(document.forms[0].zipPin_temp))
	   		{
	   		 var resiPinValue =document.forms[0].zipPin_temp.value;
	   		 
	   		 if(resiPinValue.substring(3,6)=="000")
	   		 {
	   		   tempData.errorMessages = tempData.errorMessages +"\n"+"Pincode should not contain last three consecutive Zero";
	   		   
	   		 }
	   		}
	   		
	   			if(document.forms[0].extention1.value!="" && document.forms[0].phone1LandLine.value=="" )
	    		{
	    			validate(document.forms[0].phone1LandLine,"TMV",'LANDLINE');
	    			
	   	 	}
	   	 	validate(document.forms[0].phone1Std,"TNV,NZV","Landline STD");
	   			validate(document.forms[0].phone1LandLine,"TNV,NZV",'Landline');
	   			if(document.forms[0].phone1Std.value=="" && document.forms[0].phone1LandLine.value!="")
	    		{
	    			validate(document.forms[0].phone1Std,"TMV","Landline STD");
	    		}
	    		else if(document.forms[0].phone1Std.value!="" && document.forms[0].phone1LandLine.value=="")
	    		{
	    			//validate(document.forms[0].phone1LandLine,"TMV",'Landline');
	    		}
	    		checkMinLength(document.forms[0].phone1Std,'Landline STD',3);
	    		checkMinLength(document.forms[0].phone1LandLine,'Landline',6);
	   	 	validate(document.forms[0].extention1,"TNV,NZV",'<bean:message bundle="losApplicationResource" key="los.addressInfo.ext1"/>');
	   	 	
	    		validate(document.forms[0].email,"EV",'<bean:message bundle="losApplicationResource" key="los.addressInfo.email"/>');
	   		validate(document.forms[0].fax,"TNV,NZV",'<bean:message bundle="losApplicationResource" key="los.addressInfo.fax"/>');
	   		
	   		//1.0.0.24
	   		if(document.forms[0].addressType.value!="1000000010"){
	   		validate(document.forms[0].mobile,"MBV",'Mobile');
	    		validate(document.forms[0].noOfYearAtResidence,"TNV",'No of year at above residence');
	    	validate(document.forms[0].noOfMonthAtResidence,"TNV","No of months at above residence");
	    	twoCanNotBeZero(document.forms[0].noOfYearAtResidence,document.forms[0].noOfMonthAtResidence,"No of year at above residence","No of months at above residence");
	    	checkValueBetween(document.forms[0].noOfMonthAtResidence,0,11,"No of months at above residence");
	   		}
	    	//1.0.0.24 end
	   	}
	   	//add by 1.0.0.22
		if(temp_activityType!="DM PDE")
		{
	   	if(document.forms[0].sameAs.selectedIndex!=0)
	   		{
	   			//validate(document.forms[0].state_temp,"TMV",'State');
	   			validate(document.forms[0].city_temp,"TMV",'City');
				validate(document.forms[0].zipPin_temp,"TMV",'Pincode');
	   		
	   		}
		}	
	   	//end by 1.0.0.22
	      	if(document.forms[0].addressType.value == "1000000002" )
	   		{//  1.0.0.0.1 start
	      		if(IsAlphaNumericWithSpecialSymbolNew(document.forms[0].offCompanyName,'Name Of Company of office address'))
	   			{
	   			  return false;
	   			}  // 1.0.0.0.1 end
	      	}
	   	 	
	   	if(document.forms[0].addressType.value=="1000000001")
	   	{
	   		if(document.forms[0].custEntityType.value!="1000000002")//document.forms[0].applicantType.value=="1000000001" || document.forms[0].applicantType.value=="1000000002")
	   		{
	   		}
	    		
	   	}
	   	
	   	if(document.forms[0].mailingAddressCheck.checked==true)
	   	{
	   	  document.forms[0].mailingAddress.value="Y";
	   	}
	   	else
	   	{
	   	    document.forms[0].mailingAddress.value="";
	   	}
	   	document.forms[0].phoneNo1.value = document.forms[0].phone1Std.value+"-"+document.forms[0].phone1LandLine.value;
	   	
		// 1.0.0.0.2 start
		if(document.forms[0].destinationAdd.checked==true)
		{
		  document.forms[0].destinationAddValue.value="Y";
		}
		else
		{
		    document.forms[0].destinationAddValue.value="";
		}
		// 1.0.0.0.2 end
		
		   if(document.forms[0].temp_loaCheckBox.checked)
	       	{
	       	document.forms[0].loaCheckBox.value="Y";
	       	}
				else
	       	{
	       	document.forms[0].loaCheckBox.value="N";
	       	}
		   // 2.0.0.0.0 Start 
	   		if(document.forms[0].entityIdForDm.value=="1000000008"){
	   			//if(document.forms[0].destinationAdd.checked==true){
					//validate(document.forms[0].gstinNo,"TMV",'GstIn  No.');
					
						if(document.forms[0].addressType.value=="1200000008"){
						//validate(document.forms[0].sez,"CMV",'SEZ ZONE');
					  if(document.forms[0].sez.value=="Y"){
					  //validate(document.forms[0].igstApplicable,"CMV",'IGST PERCENT APPLICABLE');//1.0.0.8
					  //validate(document.forms[0].igstpercent,"TMV",'IGST PERCENT');//1.0.0.8
						}
					//validate(document.forms[0].lutNo,"TMV",'LUT NO.');
					//validate(document.forms[0].temp_loaCheckBox,"CMV",'LOA');
						
					}
					//}
	   		 
	  		  // if(document.forms[0].sez.value=="Y"){
	  	   		//	validate(document.forms[0].igstApplicable,"CMV",'Igst Applicable');
	  	   	//	}
	  		 
	  		 /*if(document.forms[0].destinationAdd.checked!=true){
				   tempData.errorMessages = tempData.errorMessages +"\n"+"Destination address checkbox is mandatory in case of billing address";
	  				
	  			}*/
	  	   			
	  	   		
	   		}
	   		
	   		// 2.0.0.0.0 End 
		  }
		  
		  if(entityId=="1000000003")
			{
				if(document.forms[0].addressType.value=="1000000010")
				{
					validate(document.forms[0].gstinNo,"TMV",'GSTIN No.');
					
				}
			}
	   }
		 	
	
		
		
 </script>

	
</head>

<body onload="javascript:disableFieldsOnload1();javascript:load();" ng-app="applicantDetailApp">   


	<html:form method="post" action="personalInfo.do" enctype="multipart/form-data">
	  <html:hidden styleId="applicantID" property="applicantID"/>
	  <html:hidden property="legalFormType"/>
	  <html:hidden property="mailingAddressStatus"/>
	  <html:hidden property="applicantCode"/>	
	  <html:hidden property="businessDate"/>
	  <html:hidden property="quotation_igstPer"/>  <!-- 1.0.0.17 start -->
	  <html:hidden property="quotation_sgst_ugstPer"/>
	  <html:hidden property="quotation_cgstPer"/>
	  <html:hidden property="quotation_sez_zone"/>
	  <html:hidden property="quotation_registrationState"/>  <!-- 1.0.0.17 end -->
	 <!--  //code added by shashaank dubey for coapplicant search on 30-09-2014 -->
	  <input type="hidden" name="product"/>
	
	 <div class="container-fluid"> 
	  
	   <jsp:include page="../los/personalInfoInc.jsp" />
	   <logic:notEmpty name="quotationViewFlag" >
	  <%@include file="../common/historyHeader.jsp"%>
	  <div class="container-fluid subheaderSection">
			<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
				<div class="col-lg-4 col-md-4 col-sm-4 col-xs-6"><bean:write name ="screenModes" property="screenName"/>
				</div>
				<div class="col-lg-8 col-md-8 col-sm-8 col-xs-6 text-right secndDivSubhdr"><a href="#" onclick="closeWindow()">Close</a>
				</div>
			</div>
		</div>
	  </logic:notEmpty>
	  
	  
	  <logic:empty name="quotationViewFlag" >
	  <%@include file="../common/historyHeader.jsp"%>
	  <div class="container-fluid subheaderSection">
			<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
				<div class="col-lg-4 col-md-4 col-sm-4 col-xs-6"><bean:write name ="screenModes" property="screenName"/>
				</div>
				<div class="col-lg-8 col-md-8 col-sm-8 col-xs-6 text-right secndDivSubhdr"><a href="#" onclick="closeWindow()">Close</a>
				</div>
			</div>
		</div>
	  </logic:empty>
	  
	  
	  	
	  	 <logic:empty name="quotationViewFlag" >
		 <!--changes by 1.0.0.19 for childWindowFlg start  -->
	  	   <logic:notEmpty name="childWindowFlg">
				<logic:equal name="childWindowFlg" value="Y">
				   <%@include file="../common/historyHeader.jsp"%>
				</logic:equal>
			</logic:notEmpty>
			<logic:empty name="childWindowFlg" > 
			  	   <%@include file="../common/header.jsp"%>
			</logic:empty> 	
	  	<!--changes by 1.0.0.19 for childWindowFlg end  -->
		<!-- 1.0.0.1	 start -->
		<logic:notEmpty name="ajaxCall" >
	        <logic:equal name="ajaxCall" value="N">
	        <logic:notEqual   name="entityId" value="1000000005" >  
				<%@include file="../common/subHeaderViewer.jsp"%>
				<jsp:include page="../common/customerServiceMenu.jsp" />
				</logic:notEqual>
			</logic:equal>
		</logic:notEmpty>
		
	<logic:empty name="ajaxCall">
		<!--changes by 1.0.0.19 for childWindowFlg start  -->
		 <logic:notEmpty name="childWindowFlg">
				<logic:equal name="childWindowFlg" value="Y">
				    <%@include file="../quotation/childSubHeader.jsp"%>
				</logic:equal>
			</logic:notEmpty>
			<logic:empty name="childWindowFlg" > 
			  <%@include file="../common/subHeaderPrivs.jsp"%>
			</logic:empty> 	
		<!--changes by 1.0.0.19 for childWindowFlg end  -->
		<jsp:include page="menu.jsp" flush="false" />
		 <logic:notEmpty name="StaticInfo">
	           
				  <jsp:include page="../common/staticinfo.jsp" flush="false" /> 
	            </logic:notEmpty>
	            
	            <logic:notEmpty name="staticInfoDtl">
			 
			  <%@ include file="../quotation/quotationStaticInfo.jsp" %>
	  </logic:notEmpty>
	 
	   
	<!--changes by 1.0.0.19 for childWindowFlg start  -->
    </logic:empty>
 	<logic:empty name="childWindowFlg" > 
	<div   id="vetiTd" >
			<%@ include file="../vmenu/menu.jsp" %> 
			</div>
			<div  class="toggling">
			
				<%@ include file="../vmenu/vmenuTgl.jsp" %>
		</div>
	</logic:empty> 	
<!--changes by 1.0.0.19 for childWindowFlg end  -->
      <div id="msg" class="activityCompleted" >
                 <font color="#2578e8"><b> 
            	<%-- <logic:notEmpty name="activityCompleted"  >
					<bean:message bundle="losApplicationResource" key = "los.prospectEntry.ActivityCompleted" />
			    </logic:notEmpty> --%></b> </font>
		</div>  
	
	  </logic:empty>
	 	
		<div id="prospectEditByOtherUser">
			<font color="#990000"><b> 
				<logic:notEmpty name="prospectEditByOtherUser">
					<bean:write name="prospectEditByOtherUser" />
				</logic:notEmpty>
			 </b> </font>
		</div>

		<logic:notEmpty name="isAleadyExactMatchmsg">
			<div id="ifAlreadyExact">
				<font color="#990000"><b>
					<bean:write name="isAleadyExactMatchmsg" /> 
				</b> </font>
			</div>
		</logic:notEmpty>
		<div class=" col-lg-12 col-md-12 col-sm-12 col-xs-12">
			<jsp:include page="personalinfoApltDtlInc.jsp" flush="false" /> 
									
				</div>
						<script language="javascript">
		<%
			   if(request.getAttribute("newDetail")!=null)
			   { %>
			    //resetForm();
			    document.getElementById("msg").style.display="none";
			    //	fillCombo();
			    
			    enableAllElements();
			  <% }
			    else
			    {
			   %>
                
			   enableAllElements();
			 
			   disableAllElementsApp(); //1.0.0.0.2	     Ravi
			   <%}%> 
   
  </script>
  
			 <div id="authorisedSignatoryDiv" > 
				<div class=" col-lg-12 col-md-12 col-sm-12 col-xs-12">
					<div class="legend legendFloat col-lg-12 col-md-12 col-sm-12 col-xs-12">
						<div class="col-lg-8 col-md-8 col-sm-8 col-xs-8">AUTHORIZED SIGNATORY</div>
						
						<div class="col-lg-4 col-md-4 col-sm-4 col-xs-4 main_body text-right button_style">
							<logic:notEqual name="activityInfo" property="activityType" value="PDE">
								<input type="button" class="blueBotton" name="authSignDedupeInit" value="Dedupe Initiate" onclick="javascript:appDupeInitiate('AUTH SIGNATORY');" />
								<input type="button" class="blueBotton" name="add" value="Add New" onclick="javascript:addAuthorisedSignatoryRow('','','','','0','','','' ,'','','','','','','','','','','','','','','');" />
								<input type="button" class="blueBotton" name="remove" value="Remove" onclick="javascript:removeAuthorisedSignatory();" />
							</logic:notEqual>
						</div>	
							
					</div>
				<div style="width:100%;overflow:auto">
							<table width="1800px" class="main_body" id="authorisedSignatoryRowsDiv"> <!-- //1.0.0.27 -->
											
									<tr valign="top" class="list_header" >
									<logic:notEqual name="activityInfo" property="activityType" value="PDE">	
											<td width="36px"><input type="checkbox" onclick="selectAllAuthSignRow(this)"></td><!-- <td width="4%">Select</td> -->
									</logic:notEqual>	
										<td width="90px" id="authSignViewDedupTH" style="display:none;white-space:pre;" align="left">Dedupe Initiated</td>		<!-- //1.0.0.27 -->
										<td width="110px" align="left">First Name</td>
										<td width="110px" align="left">Middle Name</td>
										<td width="110px" align="left">Last Name</td>
										
										<td width="100px" align="left">Gender</td>
										<td width="120px" align="left">DOB</td>
										
										<td width="110px" align="left">Designation</td>
										<td width="100px" align="left">DIN No</td>
										<td width="120px" align="left">Email</td>
										<td width="100px" align="left">Pan</td><!-- 1.0.0.25 -->
										<td width="110px" align="left">Contact No</td>
										<!-- start <!-- 1.0.0.25 -->
										<td width="120px" align="left">Address</td>
										<td width="110px" align="left">State</td>
										<td width="110px" align="left">City</td>
										<td width="110px" align="left">Pincode</td>
										<!-- End 1.0.0.25 -->
										<td width="110px" align="left">Delegation</td>
								 </tr>
								 </table>
					
				</div>
				</div>
			</div>
			
			<!-- Start 1.0.0.25 -->
  <div id="beneficiaryOwnerDiv" style="width:100%;display:none;" > 
				<div class=" col-lg-12 col-md-12 col-sm-12 col-xs-12">
					<div class="legend legendFloat col-lg-12 col-md-12 col-sm-12 col-xs-12">
						<div class="col-lg-8 col-md-8 col-sm-8 col-xs-8">BENEFICIARY OWNER</div>
						
						<div class="col-lg-4 col-md-4 col-sm-4 col-xs-4 main_body text-right button_style">
							<logic:notEqual name="activityInfo" property="activityType" value="PDE">
								<input type="button" class="blueBotton" name="authSignDedupeInit" value="Dedupe Initiate" onclick="javascript:appDupeInitiate('BENEFICIARY');" />
								<input type="button" class="blueBotton" name="add" value="Add New" onclick="javascript:beneficiaryOwnerRow('','','','','','','','','','','');" />
								<input type="button" class="blueBotton" name="remove" value="Remove" onclick="javascript:removeBeneficiaryOwnerRows();" />
							</logic:notEqual>
						</div>	
							
					</div>
				
							<table width="100%" class="main_body" id="beneficiaryOwnerRowsDiv"> <!-- //1.0.0.27 -->
											
									<tr valign="top" class="list_header" >
									<logic:notEqual name="activityInfo" property="activityType" value="PDE">	
											<td width="2%"><input type="checkbox" onclick="selectAllBeneficiaryRow(this)"></td><!-- <td width="4%">Select</td> -->
									</logic:notEqual>	
										<td width="12%" id="benViewDedupTH" style="display:none;white-space:pre;" align="left">Dedupe Initiated</td>		<!-- //1.0.0.27 -->
										<td width="12%" align="left">Name</td>
										<td width="12%" align="left">PAN</td>
										<td width="12%" align="left">E-mail</td>
										
										<td width="12%" align="left">Mobile Number</td>
										<td width="12%" align="left">Address</td>
										<td width="12%" align="left">State</td>
										<td width="12%" align="left">City</td>
								 </tr>
								 </table>
					
				</div>
			</div>
  <!-- End 1.0.0.25 -->
			
			<div class=" col-lg-12 col-md-12 col-sm-12 col-xs-12">
				<div class="legend legendFloat col-lg-12 col-md-12 col-sm-12 col-xs-12">
					<div class="col-lg-8 col-md-8 col-sm-8 col-xs-8">ADDRESS DETAILS</div>
					<div class="col-lg-4 col-md-4 col-sm-4 col-xs-4 main_body text-right">
						<input type="button" class="blueBotton"  name="addAddressButton" value="Add New" onclick="javascript:addNewAddress();" id="addAddressButtonId" />
						<input type="button" class="blueBotton"  name="cancel" value="Reset" onclick="javascript:cancelNewAddress();" /><!-- 1.0.0.25 -->
					</div>		
				</div>
			</div>
			<div class=" col-lg-12 col-md-12 col-sm-12 col-xs-12">
				<!-- <div class=" col-lg-12 col-md-12 col-sm-12 col-xs-12 legend">
					Address Information
				</div>
 -->
				<div class=" col-lg-12 col-md-12 col-sm-12 col-xs-12">
					<div id="nameOfCompanyNew">
						<div class=" col-lg-12 col-md-12 col-sm-12 col-xs-12">
							<div class="col-lg-2 col-md-2 col-sm-3 col-xs-6" >
								<bean:message bundle="losApplicationResource" key="los.newProspect.nameOfCompany" />
							</div>
							<div class="col-lg-2 col-md-2 col-sm-3 col-xs-6" >
								<html:text property="offCompanyName" maxlength="200" size="70" />
							</div>
						</div>
					</div>
				</div>
				
				<div class=" col-lg-12 col-md-12 col-sm-12 col-xs-12">
					<div class=" col-lg-12 col-md-12 col-sm-12 col-xs-12">
						<div class="col-lg-2 col-md-2 col-sm-3 col-xs-6" id="mailingAddrDiv">
							<input type="checkbox" name="mailingAddressCheck" checked="checked" value="Y">
																&nbsp;&nbsp;
							<bean:message bundle="losApplicationResource" key="los.addressInfo.maililgAdd" />
						</div>
						<div class="col-lg-2 col-md-2 col-sm-3 col-xs-6" id="destinationAddrDiv">
							<input type="checkbox" name="destinationAdd" checked="checked" value="Y">
															&nbsp;&nbsp;
							<bean:message bundle="losApplicationResource" key="los.addressInfo.destinationAdd" />
						</div>
						
						<div class="col-lg-2 col-md-2 col-sm-3 col-xs-6" id="sezAddrDiv"  disabled="disabled">
							<input type="hidden" name="sez" />
							<input type="checkbox" name="sezZone" onclick="setSezValue();" value="N">
															&nbsp;&nbsp;
							SEZ Zone
						</div>
						
					</div>
					<html:hidden property="addrApplicantType" />
					<div class="col-lg-2 col-md-2 col-sm-3 col-xs-6" >
						<bean:message bundle="losApplicationResource" key="los.addressInfo.addType" />
					</div>
					<div class="col-lg-2 col-md-2 col-sm-3 col-xs-6" >
						<html:select property="addressType" style="width:140px" onchange="javascript:changeCombo(this); setSameAsDropDown(this); checkforOfficeAddress(this);destinationAddrCheck(this);">
							<html:option value="0">SELECT</html:option>
							<html:options collection="colProspectAddressList" property="addressTypeCode" labelProperty="addressTypeName" />
						</html:select>
					</div>
					<div id="sameAsDivId">
					<div class="col-lg-2 col-md-2 col-sm-3 col-xs-6"> 
						<bean:message bundle="losApplicationResource" key="los.addressInfo.sameAs" />
					</div>
					<div class="col-lg-2 col-md-2 col-sm-3 col-xs-6" >
						<html:select property="sameAs" style="width:140px" onchange="javascript:disableFieldsForSameAs();">
							<html:option value="0">SELECT</html:option>
							<logic:notEmpty name="colAddressInfo">
								 <html:options collection="colAddressInfo" property="applicantAddressId" labelProperty="applicantAddressName" /> 
							</logic:notEmpty>
						</html:select>
					</div>
						</div>
					<div class="col-lg-2 col-md-2 col-sm-3 col-xs-6"> 
						<bean:message bundle="losApplicationResource" key="los.addressInfo.flatNo" />
					</div>
					<div class="col-lg-2 col-md-2 col-sm-3 col-xs-6" >
						<html:text property="flatHouseNo" maxlength="50" style="width:140px"/>
					</div>
					
					<!-- changes made by apurva on 10th jan 2018 for sequence of address  -->
					<div class="col-lg-2 col-md-2 col-sm-3 col-xs-6"> 
						<bean:message bundle="losApplicationResource" key="los.addressInfo.floorNo" />
					</div>
					<div class="col-lg-2 col-md-2 col-sm-3 col-xs-6" >
						<html:text property="floorNo" maxlength="50" style="width:140px"/>
					</div>
					<!-- changes made by apurva on 10th jan 2018 for sequence of address  -->
					
					<div class="col-lg-2 col-md-2 col-sm-3 col-xs-6" >
						<bean:message bundle="losApplicationResource" key="los.addressInfo.buildingName" />
					</div>
					<div class="col-lg-2 col-md-2 col-sm-3 col-xs-6" >
						<html:text property="buildingName" maxlength="50" style="width:140px"/>
					</div>
					
					<div class="col-lg-2 col-md-2 col-sm-3 col-xs-6"> 
						<bean:message bundle="losApplicationResource" key="los.addressInfo.landMark" />
					</div>
					<div class="col-lg-2 col-md-2 col-sm-3 col-xs-6" >
						<html:text property="landMark" maxlength="50" style="width:140px"/>
					</div>
					<div class="col-lg-2 col-md-2 col-sm-3 col-xs-6" >
						<bean:message bundle="losApplicationResource" key="los.addressInfo.locality" />
					</div>
					<div class="col-lg-2 col-md-2 col-sm-3 col-xs-6" >
						<html:text property="locality" maxlength="50" style="width:140px"/>
					</div>
						
					<div class="col-lg-2 col-md-2 col-sm-3 col-xs-6"> 
						<bean:message bundle="losApplicationResource" key="los.addressInfo.state" />
					</div>
					<div class="col-lg-2 col-md-2 col-sm-3 col-xs-6" >
						<html:hidden property="state" />
						<html:text property="state_temp" maxlength="15" size="14" onmouseover="setToolTipForLov(this)" readonly="true" />
						<input type="button" class="blueBotton" name="btnState" value="..." onclick="javascript:stateChooser('QM_STATEMASTER');">
					</div>
						
					<div class="col-lg-2 col-md-2 col-sm-3 col-xs-6"> 
						<bean:message bundle="losApplicationResource" key="los.addressInfo.city" />
					</div>
					<div class="col-lg-2 col-md-2 col-sm-3 col-xs-6" >
						<html:hidden property="city" />
						<html:text property="city_temp" maxlength="15" size="16" readonly="true" onmouseover="setToolTipForLov(this)"/>
						<input type="button" class="blueBotton" name="btnCity" value="..." onclick="javascript:cityChooser('QM_CITYMASTER');">
					</div>
					
					<div class="col-lg-2 col-md-2 col-sm-3 col-xs-6" >
						<bean:message bundle="losApplicationResource" key="los.addressInfo.zip" />
					</div>
					<div class="col-lg-2 col-md-2 col-sm-3 col-xs-6" >
						<html:hidden property="zipPin"/>
						<html:text property="zipPin_temp" style="background-color:#ebf3ff;width:140px" onmouseover="setToolTipForLov(this)" onblur="fetchStateCity(this);" maxlength="6" /><!-- 1.0.0.13  -->
						<input type="button" class="blueBotton" name="btnzipPin"  value="..."   onclick="javascript:pinChooser('QM_PINCODEMASTER');">
					</div>
						
					<div class="col-lg-2 col-md-2 col-sm-3 col-xs-6"> 
						<bean:message bundle="losApplicationResource" key="los.addressInfo.mobile" />
					</div>
					<div class="col-lg-2 col-md-2 col-sm-3 col-xs-6" >
						+91
						<html:text property="mobile" maxlength="10" size="15" style="width:85% !important"/>
					</div>
						
					<div class="col-lg-2 col-md-2 col-sm-3 col-xs-6"> 
						<bean:message bundle="losApplicationResource" key="los.addressInfo.email" />
					</div>
					<div class="col-lg-2 col-md-2 col-sm-3 col-xs-6" >
						<html:text property="email" maxlength="100" style="width:140px" />
					</div>
					
					<div class="col-lg-2 col-md-2 col-sm-3 col-xs-6" >
						<bean:message bundle="losApplicationResource" key="los.addressInfo.phone1" />
					</div>
					<div class="col-lg-2 col-md-2 col-sm-3 col-xs-6" >
						<html:hidden property="phoneNo1" />
						<input type="text" name="phone1Std" size="5" maxlength="5" class="cstmWidth"/>					
						<input type="text" name="phone1LandLine" size="7" maxlength="8">
					</div>
					
					<div class="col-lg-2 col-md-2 col-sm-3 col-xs-6"> 
						<bean:message bundle="losApplicationResource" key="los.addressInfo.ext1" />
					</div>
					<div class="col-lg-2 col-md-2 col-sm-3 col-xs-6" >
						<html:text property="extention1" maxlength="4" />
					</div>
					
					<div class="col-lg-2 col-md-2 col-sm-3 col-xs-6"> 
						<bean:message bundle="losApplicationResource" key="los.addressInfo.fax" />
					</div>
					<div class="col-lg-2 col-md-2 col-sm-3 col-xs-6" >
						<html:text property="fax" maxlength="15" />
					</div>
					
					<div class="col-lg-2 col-md-2 col-sm-3 col-xs-6" >
						<bean:message bundle="losApplicationResource" key="los.newProspect.residenceStatus" />
					</div>
					<div class="col-lg-2 col-md-2 col-sm-3 col-xs-6" >
						<html:select property="residenceStatus" style="width:140px">
							<html:option value="0">
								<bean:message bundle="losApplicationResource" key="los.common.select" />
							</html:option>
							<logic:notEmpty name="colResidenceListMaster">
								<html:options collection="colResidenceListMaster" property="masterId" labelProperty="masterName" />
							</logic:notEmpty>
						</html:select>
					</div>
					
					<div class="col-lg-2 col-md-2 col-sm-3 col-xs-6 "> 
						<bean:message bundle="losApplicationResource" key="los.newProspect.NoOfYearsAtAboveAddress" />
						<span style="display:none;">
							
						</span>
					</div>
					<div class="col-lg-2 col-md-2 col-sm-3 col-xs-6 cstmWidthInput">
						<html:text property="noOfYearAtResidence" size="3" maxlength="2" style="width: 23% !important; text-align: right;" />
														&nbsp;
						<bean:message bundle="losApplicationResource" key="los.newProspect.years" />
						<html:text property="noOfMonthAtResidence" size="3" maxlength="2" style="width: 23% !important; text-align: right;" />
														&nbsp;
						<bean:message bundle="losApplicationResource" key="los.newProspect.month" />
					</div>
						
					<div class="col-lg-2 col-md-2 col-sm-3 col-xs-6 ">
							
						
							<bean:message bundle="losApplicationResource" key="los.addressInfo.gstinNo"/>
						
					</div>
					<div class="col-lg-2 col-md-2 col-sm-3 col-xs-6" >
						<html:text property="gstinNo" maxlength="15" />
					</div>
					<!-- Apurva /* 2.0.0.0.1 */ Start  -->
					  <div id="sezZoneDivId" style="display: none;">
						<div class="col-lg-2 col-md-2 col-sm-3 col-xs-6">
						SEZ Zone
					</div>
					<%-- <div class="col-lg-2 col-md-2 col-sm-3 col-xs-6" >
						<html:select property="sez" style="width:140px" onchange="clickSezZone(this);">
							<html:option value="0">
								<bean:message bundle="losApplicationResource" key="los.common.select" />
							</html:option>
							<html:option value="Y">YES</html:option>
							<html:option value="N">NO</html:option>
						</html:select>
					</div> --%>
					</div>
					<div id="gstId" style="display: none;">
					<div class="col-lg-2 col-md-2 col-sm-3 col-xs-6">
						IGST % Applicable
					</div>
					<div class="col-lg-2 col-md-2 col-sm-3 col-xs-6" >
						<html:select property="igstApplicable" style="width:140px" disabled="true" onchange="clickIgstApplicable(this);">
							<html:option value="0">
								<bean:message bundle="losApplicationResource" key="los.common.select" />
							</html:option>
							<html:option value="Y">YES</html:option>
							<html:option value="N">NO</html:option>
						</html:select>
					</div>
					<div class="col-lg-2 col-md-2 col-sm-3 col-xs-6">
						IGST %
					</div>
					<div class="col-lg-2 col-md-2 col-sm-3 col-xs-6">
						<html:text property="igstpercent" maxlength="15" disabled="true" />
					</div>
					<div class="col-lg-2 col-md-2 col-sm-3 col-xs-6">
						LUT (Letter Of Undertaking) No.
					</div>
					<div class="col-lg-2 col-md-2 col-sm-3 col-xs-6">
						<input type="text" name="lutNo" size="5"  disabled="true" maxlength="50" />
					</div>
					
					<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
					<div class="col-lg-2 col-md-2 col-sm-3 col-xs-6">
							<input type="checkbox" name="temp_loaCheckBox" disabled="true"  >
															&nbsp;&nbsp;
							LOA(Letter Of Authorization)
						</div>
						</div>
						</div>
						<div class="col-lg-2 col-md-2 col-sm-3 col-xs-6"></div>
						<div id="gstType" style="display: none;">
						<div class=" col-lg-12 col-md-12 col-sm-12 col-xs-12">
						<div class="col-lg-2 col-md-2 col-sm-3 col-xs-6">
						Lessor GST Type
					</div>
					<div class="col-lg-2 col-md-2 col-sm-3 col-xs-6">
						
						<html:select property="lessorGstType" style="width:140px">
							<%-- <html:option value="0">
								<bean:message bundle="losApplicationResource" key="los.common.select" />
							</html:option> --%>
							<logic:notEmpty name="colGstTypeMaster">
								<html:options collection="colGstTypeMaster" property="ID" labelProperty="NAME" />
							</logic:notEmpty>
						</html:select>
						
						</div>
					</div>
						</div>
						
							
				</div>											
					<!-- Apurva /* 2.0.0.0.1 */ end  -->
					</div>	
					<div id="noteId" style="display: none">
				<logic:equal name="entityId" value="1000000008">
			<b>NOTE:-</b> IGST % is zero when IGST is not applicable in SEZ zone<br>
			&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbspLUT & LOA is applicable in SEZ zone.Please upload the LUT & LOA document for reference.
				</logic:equal>
				</div>
				
				 <jsp:include page="../los/personalInfoDetailInfo.jsp" />
			</div>
			
<html:hidden property="dmLeasseCode"/>
<html:hidden property="removeKeyContactDataStr"/>
<html:hidden property="keyContactDataStr"/>
<!-- Start 1.0.0.25 -->
<html:hidden property="removeBeneficiaryOwnerDataStr"/>
<html:hidden property="removeAuthSignDataStr"/>
<html:hidden property="beneficiaryOwnerDataStr"/>
<!-- End 1.0.0.25 -->
<html:hidden property="caseId"/>
<html:hidden property="loaCheckBox"/>
<html:hidden property="temp_AssetCategory"/>
<html:hidden property="temp_Product"/><!-- /* 1.0.0.8  */ -->
<html:hidden property="temp_Competition"/>
<html:hidden property="entityIdForDm"/>

<html:hidden property="authSignDtataStr" />
	     <html:hidden property="authSignIdValueStr" />
		 <html:hidden property="authSignFNametr" />
         <html:hidden property="authSignLNamestr" />
         <html:hidden property="authSignMName" />
         <html:hidden property="authSignDesignationstr" />         
         <html:hidden property="authSignDinNoStr" />
         <html:hidden property="authSignMailIdIdStr"/>
         <html:hidden property="authSignContactNoStr"/>
         <html:hidden property="authSignDelegationStr"/>
         
		<html:hidden property="ownership" />
		<html:hidden property="applicantAddressId" />
		<html:hidden property="addrApplicantId" />
		<html:hidden property="noOfYears" />
		<html:hidden property="createdBy" />
		<html:hidden property="createdDateTime" />
		<html:hidden property="updatedBy" />
		<html:hidden property="updateDateTime" />
		<html:hidden property="status" />
		<html:hidden property="mailingAddress" />
		<html:hidden property="destinationAddValue" />   <!-- 1.0.0.0.6  -->
		<html:hidden property="flag" value="I"/>
		<html:hidden property="formFlag" />
			<input type="hidden" name="imgFlag" id="imgFlag" value="F" /> <!-- Added by sakib for Image Uplodation  Mandatory -->
			<html:hidden property="dnsRequired1"/>
			<!-- /* 1.0.0.10 start  */ -->
			<script>
		<%if(request.getAttribute("newDetail")!=null)
			   { %>
			    cancelNewAddress();
			    document.forms[0].existingApplicantId.value="";
			  <% }%>
			  /* 1.0.0.12  Start*/
			  document.forms[0].dmLeasseCode.value='<%=request.getAttribute("dmLesseCode")%>';
			  /* 1.0.0.12  End*/
			  
			  //Start 1.0.0.23
			  <logic:notEmpty name="entityId">
			  <logic:equal name="entityId" value="1000000008">
				<logic:notEmpty name="staticInfoDtl" >	
					var staticInfoDetail=${staticInfoDtl};
					var dmStatusVal = staticInfoDetail.PC_STATIC_INFO_DATA[0].DM_STATUS;
					if(dmStatusVal != "PENDING"){
						if(document.getElementById("SaveLink") != null){document.getElementById("SaveLink").style.display="none";}
						if(document.getElementById("SaveExitLink") != null){document.getElementById("SaveExitLink").style.display="none";}			
					}
				  </logic:notEmpty>
				</logic:equal>
			</logic:notEmpty>
			//End 1.0.0.23
		</script>
			<!-- /* 1.0.0.10 end  */ -->
			
		<script>
		
		 function validateAadhaarNo(obj){
		 		tempData.errorMessages="Errors:";
		 		validate(document.forms[0].adharNo,"",'Aadhaar No.');
		 		//checkAdharValidation(document.form[0].aadhaarNo,'Aadhaar No.'); 
				 
				 if(tempData.errorMessages!="Errors:")
					{
						alert((tempData.errorMessages).replace("Errors:","").toUpperCase());
						//obj.value="";
						document.getElementById("verifyAadhaarBtn").style.display = "none";
						tempData.errorMessages = "Errors:";
						return;
					}else{
							if(document.getElementById("adharNo").value == ""){
								document.getElementById("aadhaarVerifiedPendingIcon").style.display="block"//1.0.0.28
								document.getElementById("verifyAadhaarBtn").style.display="none"
							}
							else{
								document.getElementById("aadhaarVerifiedPendingIcon").style.display="none"
								document.getElementById("verifyAadhaarBtn").style.display = "block";//1.0.0.28
							}
						}
						
			 }
		
		 function AadhaarVerifierApiCall()
			{
				//alert("calling internally");
				//alert("pan is changed");   	
		    	
		    	
				var aadhaarNoValue;
				$(document).ready(function () { 																	        
			            // Get the form data. This serializes the entire form. pritty easy huh!
			            $('#loading').show();
			            	$('#responseAadhaar').html('');	      
			            	aadhaarNoValue=document.forms[0].adharNo.value;
			            
			           	//alert("aadhar value for ajax=="+aadhaarNoValue);										            	

			            // Make the ajax call
			            $.ajax({
			                url: 'ibsCallAction.do?actionPerformed=generateAadhaarOTP',
			                type: 'POST',
			                data: {aadhaarNoValue:aadhaarNoValue},          
			                success: function (response) {
			                	$('#loading').hide();
			                		$('#responseAadhaar').html(response);
			                		//document.forms[0].validPanFlagCust.value="Y";
			    	            if(response!=null && response!="") 
			  			    	{
				  					var resJson = JSON.parse(response);
				  					//alert("IBS response"+resJson);
				  					fetchAadharOTP(resJson);
			  			    	}
			                	else
			                		{
			                		alert(("Empty response generated.").toUpperCase());
			                		}
			                													                	
			                }
			            });
				    }); 
			}
			
			 function fetchAadharOTP(resJson)
			 {
				 //var correlationId=resJson.correlationId;
				 var responseStatus=resJson.responseStatus;
				 var responseMsg=resJson.responseMsg;
				// alert(responseStatus);
				 if(responseStatus=="SUCCESS")
				{
					 var otpMessage=resJson.otpMessage;
					 var aadharReqId=resJson.aadharReqId;
					 alert(otpMessage);
					// alert(aadharReqId);
					 document.getElementById("aadharOtp").style.display = "block";
					 document.getElementById("fetchAadhaarDtlsBtn").style.display = "block";
					 document.getElementById("aadharReqId").value = aadharReqId;
					 
				}
				 else
					 {
					  alert((responseMsg).toUpperCase());
					  document.getElementById("aadharOtp").style.display = "none";
					  document.getElementById("fetchAadhaarDtlsBtn").style.display = "none";
					  document.getElementById("aadharReqId").value = "";
					 return;
					 }
			 }
			 
			 function fetchAadharDtlsApiCall()
				{
					//alert("calling internally");
					//alert("pan is changed");   	
			    	//alert("aadhar value for ajax=="+document.forms[0].aadharOtp.value + " aadhar req id value for ajax== "+document.forms[0].aadharReqId.value);
			    	
			    	var aadharOtpValue;
	            	var aadharReqIdValue;
	            	var aadhaarNoValue; //1.0.0.26
					$(document).ready(function () { 																	        
				            // Get the form data. This serializes the entire form. pritty easy huh!
				            $('#loading').show();
				            	$('#responseAadhaar').html('');	      
				            	//alert("aadhar value for ajax=="+document.forms[0].aadharOtp.value + " aadhar req id value for ajax== "+document.forms[0].aadharReqId.value);
				            	aadharOtpValue=document.forms[0].aadharOtp.value;
				            	aadharReqIdValue = document.forms[0].aadharReqId.value;
				            	aadhaarNoValue=document.forms[0].adharNo.value; //1.0.0.26
				           //	alert("aadhar value for ajax=="+aadharOtpValue + " aadhar req id value for ajax== "+aadharReqIdValue);										            	

				            // Make the ajax call
				            $.ajax({
				                url: 'ibsCallAction.do?actionPerformed=generateAadhaarFile',
				                type: 'POST',
				                data: {aadharOtpValue:aadharOtpValue,
				                	   aadhaarNoValue:aadhaarNoValue, //1.0.0.26
				                	   aadharReqIdValue:aadharReqIdValue
				                	},          
				                success: function (response) {
				                	$('#loading').hide();
				                		$('#responseAadhaar').html(response);
				                		//document.forms[0].validPanFlagCust.value="Y";
				    	            if(response!=null && response!="") 
				  			    	{
					  					var resJson = JSON.parse(response);
					  					//alert("IBS response"+resJson);
					  					populateAadharDetails(resJson);
				  			    	}
				                	else
				                		{
				                		alert(("Empty response generated.").toUpperCase());
				                		}
				                													                	
				                }
				            });
					    }); 
				}
				
				 function populateAadharDetails(resJson)
				 {
					 //var correlationId=resJson.correlationId;
					 var responseStatus=resJson.responseStatus;
					 var responseMsg=resJson.responseMsg;
					// alert(responseStatus);
					 if(responseStatus=="SUCCESS")
					{
						 document.getElementById("adharOkycCompFlag").value="Y";
						 var name=resJson.name;
						 var dob = resJson.dob;
						 var fatherName = resJson.fatherName;
						 var gender = resJson.gender;
						 var mobileHash = resJson.mobileHash;
						 var emailHash = resJson.emailHash;
						 
						 var address1 = resJson.address1;
						 var address2 = resJson.address2;
						 var address3 = resJson.address3;
						 var address4 = resJson.address4;
						 var address5 = resJson.address5;
						 var state = resJson.state;
						 var vtcName = resJson.vtcName;
						 var pincode = resJson.pincode;
						 var stateId = resJson.stateId;
						 var cityId = resJson.cityId;
						 var pincodeId = resJson.pincodeId;
							
						 adharOkycCompFlagChangeApiCall();
						 
						 /* alert(name);
						 alert(dob);
						 alert(fatherName);
						 alert(gender);
						 alert(mobileHash);
						 alert(emailHash);
						 
						 alert(address1);
						 alert(address2);
						 alert(address3);
						 alert(address4);
						 alert(address5);
						 alert(state);
						 alert(vtcName);
						 alert(pincode);
						 alert(stateId);
						 alert(cityId);
						 alert(pincodeId); */
						 
							document.getElementById("aadhaarVerifiedIcon").style.display="none";
							document.getElementById("verifyAadhaarBtn").style.display = "none";
							document.getElementById("aadharOtp").style.display = "none";
							document.getElementById("fetchAadhaarDtlsBtn").style.display = "none";
							document.getElementById("adharNo").disabled = "true";
						 
						  /* name = name.replace(/\s+/g, ' ').trim();
						 nameArr =name.split(' ');
						 if(nameArr.length==3)
							{
				 				 $("[name='firstName']").val(nameArr[0]);
								 $("[name='middleName']").val(nameArr[1]);
								 $("[name='lastName']").val(nameArr[2]);											 
							}
				 			else if(nameArr.length==2)
							 {
				 				 $("[name='firstName']").val(nameArr[0]);					
								 $("[name='lastName']").val(nameArr[1]);
							 }
				 			else if(nameArr.length==1)
							 {
				 				$("[name='firstName']").val(nameArr[0]);
							 }
					 	 document.forms[0].dob.value = dob;
					 	  
					 	fatherName = fatherName.replace(/\s+/g, ' ').trim();
					 	fatherNameArr =fatherName.split(' ');
						 if(fatherNameArr.length==3)
							{
				 				/*  $("[fatherName='fatherIstName']").val(fatherNameArr[0]);
								 $("[fatherName='fatherIIndName']").val(fatherNameArr[1]);
								 $("[fatherName='fatherLastName']").val(fatherNameArr[2]); */	
							/*  document.forms[0].fatherIstName.value = fatherNameArr[0];
							 document.forms[0].fatherIIndName.value = fatherNameArr[1];
							 document.forms[0].fatherLastName.value = fatherNameArr[2];
							}
				 			else if(nameArr.length==2)
							 { */
				 				 /* $("[fatherName='fatherIstName']").val(fatherNameArr[0]);					
								 $("[fatherName='fatherLastName']").val(fatherNameArr[1]); */
				 				/* document.forms[0].fatherIstName.value = fatherNameArr[0];
								 document.forms[0].fatherLastName.value = fatherNameArr[2];
							 }
				 			else if(nameArr.length==1)
							 { */
				 				/* $("[fatherName='fatherIstName']").val(fatherNameArr[0]); */
				 				//document.forms[0].fatherIstName.value = fatherNameArr[0];
							// } 
						 /* if(gender == "M")
						 {
							 document.forms[0].gender.value="1000000001";
						 }
						 else if(gender == "F")
						 {
							 document.forms[0].gender.value="1000000002";
						 }
						 else
						 {
							 document.forms[0].gender.value="1000000003";
						 }
						   document.forms[0].permaAddFlatNo.value = address1;		
						 document.forms[0].permaAddBuildingName.value = address2;
						 document.forms[0].permaAddFloorNo.value = address3;						 					  
						
						 document.forms[0].permaAddLocality.value = address4;					  
						 document.forms[0].permaAddLandMark.value = address5;				 
						 if(cityId != ""){
						 	document.forms[0].permaAddCity_temp.value = vtcName;
	 					 	document.forms[0].permaAddCity.value = cityId;
						 }
						 if(pincodeId != ""){
						 	document.forms[0].permaAddZipORPin_temp.value = pincode;
	 					 	document.forms[0].permaAddZipORPin.value = pincodeId;
						 }
						 if(stateId != ""){
							 document.forms[0].permaAddState_temp.value  = state;
		 					 document.forms[0].permaAddState.value  = stateId;	
						 } */ 
						
					 	/* document.forms[0].permaAddEmail.value = "";
					 	document.forms[0].permaAddMobile.value = ""; */
					 	
					}
					 else
						 {
						  alert((responseMsg).toUpperCase());
						  return;
						 }
				 }
		
				 
				 function adharOkycCompFlagChangeApiCall()
					{
		            	var applicantIdVal = document.forms[0].applicantID.value;
						$(document).ready(function () { 																	        
					            
					            $.ajax({
					                url: 'personalInfo.do?actionPerformed=adharOkycCompFlagChange',
					                type: 'POST',
					                data: {applicantIdVal:applicantIdVal					                	   
					                	},          
					                success: function (response) {
					                	//alert("okycflg resp :"+response);
					    	            if(response!=null && response!="") 
					  			    	{
						  					//var resJson = JSON.parse(response);
						  					//alert("okyc flg proc response"+resJson);
						  					
					  			    	}
					                	else
					                		{
					                		//alert(("Empty response generated.").toUpperCase());
					                		}
					                													                	
					                }
					            });
						    }); 
					}
				 
				 
		</script>	
			
			
			
	</html:form>
	
		<!-- Passport popup modal 1.0.0.30 Start -->
	
	<div class="form-popup" id="passportValidationModal"
		style="max-width: 70%; margin-left: 15%; background-color: white; border-radius: 5px;">
		<form action="#" id="passportValidationForm" class="form-container"
			style="max-width: 100%; padding: 10px; background-color: white;">
			<div style="margin-left: 5%; margin-top: 1%; font-size: 19px;">
				<strong> <bean:message bundle="losApplicationResource" key="los.personalInfo.PassportHeader" /> </strong>
			</div>
			<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">&nbsp;</div>

			<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12"
				style="margin-left: 5%">

				<div class="col-lg-2 col-md-2 col-sm-6 col-xs-6">
				   <bean:message bundle="losApplicationResource" key="los.personalInfo.PassportFileNo" />
				</div>
				<div class="col-lg-3 col-md-3 col-sm-6 col-xs-6">
					<input type="text" name="fileNo" id="passportFileNoID" maxlength="15" onblur="javascript:fileNoAlert(this);" >
					<span id="passportNoSpan" style="display:none;color:red" > <bean:message bundle="losApplicationResource" key="los.personalInfo.PassportFileNoSpan" /> </span>
				</div>
				<div class="col-lg-2 col-md-2 col-sm-6 col-xs-6"> 
				   <bean:message bundle="losApplicationResource" key="los.personalInfo.PassportDateOfIssue" /> 
				</div>
				<div class="col-lg-3 col-md-3 col-sm-6 col-xs-6">
					<input type="text" name="dateOfissue" id="passportDoiID" maxlength="11" onblur="javascript:doiAlert(this);" > <a
						href='#'
						onClick="cal19.select(document.forms[1].dateOfissue,'dateOfissue_link','dd-NNN-yyyy'); return false;"
						NAME="dateOfissue_link" ID="dateOfissue_link" title='Calendar'>
						<i class='fa fa-calendar' aria-hidden='true'></i>
					</a>
					<span id="dateOfIssueSpan" style="display:none;color:red" > <bean:message bundle="losApplicationResource" key="los.personalInfo.PassportDateOfIssueSpan" /> </span>
				</div>
			</div>
			<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">&nbsp;</div>
			<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12"
				style="margin-left: 40%">
				<button type="button" onclick="fetchPassportDtlsApiCall()" class="btn"> <bean:message bundle="losApplicationResource" key="los.personalInfo.PassportProceed" /> </button>
				<button type="button" class="btn cancel"
					onclick="closePassportValidationModal()"> <bean:message bundle="losApplicationResource" key="los.personalInfo.PassportClose" /> </button>
			</div>

			<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">&nbsp;</div>
		</form>
	</div>

	<!-- Passport modal popup 1.0.0.30 end-->
	
	
	<%@include file="../common/footer.jsp"%><!--Navneet Yadav :: Added common footer instead of hardcoded footer -->
	<%@include file="../common/screenFieldsXRules.jsp" %>
	<%@include file="../common/screenFieldsXValidation.jsp" %>
	
<script src="vmenu/js/toggleMenu.js"></script>
<script src="js/bootstrap.min.js"></script>
<script src="js/slimscroll.js"></script>	


</body>
</html:html>

	
	
	<%@include file="../common/footer.jsp"%><!--Navneet Yadav :: Added common footer instead of hardcoded footer -->
	<%@include file="../common/screenFieldsXRules.jsp" %>
	<%@include file="../common/screenFieldsXValidation.jsp" %>
	
<script src="vmenu/js/toggleMenu.js"></script>
<script src="js/bootstrap.min.js"></script>
<script src="js/slimscroll.js"></script>	


</body>
</html:html>
