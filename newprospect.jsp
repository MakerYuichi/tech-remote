<%-- 
  HEADER INFO
  File NAME      : newProspect.jsp
  PURPOSE        : Prospect Management System
  INITIAL VERSION : 1.0.0.0
  **********************************************************************************************************************************
  VERSION NO      UPDATED BY          UPDATED ON      REASON FOR CHANGE
  1.0.0.1         Sanchi Agarwal      03-Nov-2026     Bug: Session kill on source/allocated-to dropdowns not detected - added checkSessionStatus in clickBranch, clickSrcAgencyType, saveData
  **********************************************************************************************************************************
--%>

<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@page import="qc.sso.dto.UserInfoDTO"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>


<!DOCTYPE html>
<html:html>

<head>           
	<title>PROSPECT MANAGEMENT SYSTEM</title>
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
	<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
	<META HTTP-EQUIV="Expires" CONTENT="0">
	<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
	<link href="css/newlos.css" rel="stylesheet" type="text/css">
	<script type="text/javascript" src="js/validation.js"></script>
	<script type="text/javascript" src="js/checkSessionAvailability.js"></script>
	<script type="text/javascript" src="js/genericValidationlos.js"></script>
	<script type="text/javascript" src="js/CalendarPopup.js"></script>
	<script type="text/javascript" src="js/PopupWindow.js"></script>
	<script type="text/javascript" src="js/date.js"></script>
	<script type="text/javascript" src="js/AnchorPosition.js"></script>
	<script type="text/javascript">document.write(getCalendarStyles());</script>
	<script type="text/javascript" ID="js19">
	var cal19 = new CalendarPopup();
	cal19.showYearNavigation();
	cal19.showYearNavigationInput();
</script>
<%@include file="../common/screenFieldsXRules.jsp" %>
<%@include file="../common/screenFieldsXValidation.jsp" %>

	<script type="text/javascript">
	//this functionality is used to disable right click and backkey 
		document.onkeydown=checkKeys;
		document.oncontextmenu = new Function ("return false");
		var sessionTimeout = <%=session.getMaxInactiveInterval()%>
		//This functionality is used to logout from application on click on close button
		/*window.onbeforeunload = unLoad;

		function unLoad()
		{
			document.forms[0].action = "userAuthAction.do?dispatchMethod=logout";
			document.forms[0].method = "post";
		 	document.forms[0].submit();
		}*/		

		
    
	
	//Function to call on form load
	
	//var pId;
	//var sId;
	//var min_tenure;
	//var max_tenure;
	
	
	
	
	
	
	
	var ageMin;
	var ageMax;
	var activityProcess=0;
	var ProdArr = new Array();
    var SchemeArr = new Array();

    var EntityTypeArr = new Array();
    var LoanCenterArr = new Array();
    var MfinGroupsArr = new Array();

    var AgencyTypeArr = new Array();
	var AgencyNameArr = new Array();
	var AgentArr = new Array();
	var promoArr = new Array();
	
	var loanpurposeArr=new Array();
	var loanOfficerArr=new Array();
	
	var businessDate ="<bean:write name ="llmSessionUserInformationDTO" property="businessDate"/>";
	var mfinType = false;
	var isApplicant=true;
	
	isApplicant=true;
	 
		<% int i =0;%>
	<logic:notEmpty name="llmSessionUserInformationDTO" property="productCollection">
	 <bean:define id="colProducts" name="llmSessionUserInformationDTO" property="productCollection"/>
      <logic:iterate id="itrProduct" name="colProducts" indexId="idxProduct">
        
    	ProdArr[<%=i%>]=new Array(3);
    	
    	ProdArr[<%=i%>][0]="<bean:write name="itrProduct" property="productId"/>";
		ProdArr[<%=i%>][1]="<bean:write name="itrProduct" property="productCode"/>";
    	ProdArr[<%=i++%>][2]="<bean:write name="itrProduct" property="productName" filter="false" />";
    	
    </logic:iterate>
    </logic:notEmpty>  
    
    <% i =0;%>
    <logic:notEmpty name ="colLoanOfficer">
    <logic:iterate id="itrLoanOfficer" name="colLoanOfficer">
      	loanOfficerArr[<%=i%>]=new Array(4);
       	loanOfficerArr[<%=i%>][0]="<bean:write name="itrLoanOfficer" property="masterId"/>";         
		loanOfficerArr[<%=i%>][1]="<bean:write name="itrLoanOfficer" property="masterCode"/>";
    	loanOfficerArr[<%=i%>][2]="<bean:write name="itrLoanOfficer" property="masterName"/>";    	
    	loanOfficerArr[<%=i++%>][3]="<bean:write name="itrLoanOfficer" property="masterParentId" filter="false" />";
    </logic:iterate>
    </logic:notEmpty> 
    
    <% i =0;%>
    <logic:notEmpty name ="colPurposeOfLoanMaster">
    <logic:iterate id="itrPurpose" name="colPurposeOfLoanMaster">
      loanpurposeArr[<%=i%>]=new Array(4);
       loanpurposeArr[<%=i%>][0]="<bean:write name="itrPurpose" property="masterId"/>";         
		loanpurposeArr[<%=i%>][1]="<bean:write name="itrPurpose" property="masterCode"/>";
    	loanpurposeArr[<%=i%>][2]="<bean:write name="itrPurpose" property="masterName"/>";    	
    	loanpurposeArr[<%=i++%>][3]="<bean:write name="itrPurpose" property="masterParentId" filter="false" />";
    </logic:iterate>
    </logic:notEmpty>
    
    <%i =0;%>
	<logic:iterate id="itrScheme" name="colProdSchemeMaster">
    	SchemeArr[<%=i%>]=new Array(11);
    	SchemeArr[<%=i%>][0]='<bean:write name="itrScheme" property="masterId"/>';
		SchemeArr[<%=i%>][1]='<bean:write name="itrScheme" property="masterSchemeId"/>';
    	SchemeArr[<%=i%>][2]='<bean:write name="itrScheme" property="masterSchemeName" />';
    	SchemeArr[<%=i%>][3]='<bean:write name="itrScheme" property="masterMinLoanTenure"/>';
    	SchemeArr[<%=i%>][4]='<bean:write name="itrScheme" property="masterMaxLoanTenure"/>';
    	SchemeArr[<%=i%>][5]='<bean:write name="itrScheme" property="masterMinLoanValue"/>';
    	SchemeArr[<%=i%>][6]='<bean:write name="itrScheme" property="masterMaxLoanValue"/>';
    	SchemeArr[<%=i%>][7]='<bean:write name="itrScheme" property="minAge"/>';
    	SchemeArr[<%=i%>][8]='<bean:write name="itrScheme" property="maxAge"/>';
    	SchemeArr[<%=i%>][9]='<bean:write name="itrScheme" property="productCategoryId"/>';
    	SchemeArr[<%=i++%>][10]='<bean:write name="itrScheme" property="productCategoryCode"/>';
    </logic:iterate>
    
    <%i =0;%>
	<logic:notEmpty name="colSchemeEntityListMaster">
	<logic:iterate id="itrEntityType" name="colSchemeEntityListMaster">
    	EntityTypeArr[<%=i%>]=new Array(3);
    	EntityTypeArr[<%=i%>][0]='<bean:write name="itrEntityType" property="masterId"/>';
		EntityTypeArr[<%=i%>][1]='<bean:write name="itrEntityType" property="entityTypeId"/>';
		EntityTypeArr[<%=i++%>][2]='<bean:write name="itrEntityType" property="entityTypeName"/>';
    </logic:iterate>
    </logic:notEmpty>
    <%i =0;%>
	<logic:notEmpty name="colLoanCenterListMaster">
	<logic:iterate id="itrLoanCenter" name="colLoanCenterListMaster">
    	LoanCenterArr[<%=i%>]=new Array(3);
    	LoanCenterArr[<%=i%>][0]='<bean:write name="itrLoanCenter" property="masterId"/>';
		LoanCenterArr[<%=i%>][1]='<bean:write name="itrLoanCenter" property="loanCenterId"/>';
		LoanCenterArr[<%=i++%>][2]='<bean:write name="itrLoanCenter" property="loanCenterName"/>';
    </logic:iterate>
    </logic:notEmpty>
    
    <%i=0;%>
	<logic:notEmpty name="colMfinGroupsListMaster">
	<logic:iterate id="itrMfinGroup" name="colMfinGroupsListMaster">
    	MfinGroupsArr[<%=i%>]=new Array(2);
    	MfinGroupsArr[<%=i%>][0]='<bean:write name="itrMfinGroup" property="masterId"/>';
		MfinGroupsArr[<%=i++%>][1]='<bean:write name="itrMfinGroup" property="masterName"/>';
    </logic:iterate>
    </logic:notEmpty>

    <% i =0;%>
    <logic:notEmpty name ="colSourceTypeMaster">
   	<logic:iterate id="itrAgencyType" name="colSourceTypeMaster">
    	AgencyTypeArr[<%=i%>]=new Array(3);
    	AgencyTypeArr[<%=i%>][0]="<bean:write name="itrAgencyType" property="masterId"/>";
		AgencyTypeArr[<%=i%>][1]="<bean:write name="itrAgencyType" property="masterCode"/>";
    	AgencyTypeArr[<%=i++%>][2]="<bean:write name="itrAgencyType" property="masterName" filter="false" />";
    </logic:iterate>
    </logic:notEmpty>
    
    <%i =0;%>
    <logic:notEmpty name ="colSourceNameMaster">
    <logic:iterate id="itrAgencyName" name="colSourceNameMaster" indexId="idxAgencyName">
    	AgencyNameArr[<%=i%>]=new Array(4);
    	AgencyNameArr[<%=i%>][0]="<bean:write name="itrAgencyName" property="masterId"/>";
		AgencyNameArr[<%=i%>][1]="<bean:write name="itrAgencyName" property="masterCode"/>";
    	AgencyNameArr[<%=i%>][2]="<bean:write name="itrAgencyName" property="masterName"/>";
    	AgencyNameArr[<%=i++%>][3]="<bean:write name="itrAgencyName" property="masterParentId" filter="false" />";
    </logic:iterate>
    </logic:notEmpty>
    
    <%i =0;%>
    <logic:notEmpty name ="colSourceAgentMaster">
    <logic:iterate id="itrAgent" name="colSourceAgentMaster" indexId="idxAgent">
    	AgentArr[<%=i%>]=new Array(4);
    	AgentArr[<%=i%>][0]="<bean:write name="itrAgent" property="masterId"/>";
		AgentArr[<%=i%>][1]="<bean:write name="itrAgent" property="masterCode"/>";
    	AgentArr[<%=i%>][2]="<bean:write name="itrAgent" property="masterName"/>";
    	AgentArr[<%=i++%>][3]="<bean:write name="itrAgent" property="masterParentId" filter="false" />";
    </logic:iterate>
    </logic:notEmpty>
   
	
	<%i =0;%>
    <logic:notEmpty name ="colPromotionSchemeMaster">
    <logic:iterate id="itrPromotion" name="colPromotionSchemeMaster" indexId="idxPromotion">
    	promoArr[<%=i%>]=new Array(4);
    	promoArr[<%=i%>][0]="<bean:write name="itrPromotion" property="masterId"/>";
		promoArr[<%=i%>][1]="<bean:write name="itrPromotion" property="masterCode"/>";
    	promoArr[<%=i%>][2]="<bean:write name="itrPromotion" property="masterName"/>";
    	promoArr[<%=i++%>][3]="<bean:write name="itrPromotion" property="masterParentId" filter="false" />";
    </logic:iterate>
    </logic:notEmpty>
    function printCustomerFootUpload()
		{
			window.open("CustomerFoot.do?actionPerformed=displayCustomerFoot");
		}
		
    
	function stateChooser(tName)
	{
		
		
		var sessionStatus = checkinterval(sessionTimeout);
		if(sessionStatus=="Y")
		{
			window.open("populateMaster.do?qString="+tName+"&label=State&dependant=state&cleanDependentItems=T~city_temp&rowNum=1","popState","width=500, height=490,top=100,left=100, location=no, maximize=yes, menubar=no, status=no, toolbar=no, scrollbars=no, resizable=no","modal=yes" );
		}
		else
		{
			<%request.setAttribute("sessionExpiry", "Your session has expired!! Please login again.");%>
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
			 window.open("populateMaster.do?qString="+paramList+"&label=City&dependant=city&rowNum=1","popCity","width=500, height=490,top=100,left=100, location=no, menubar=no, status=no, toolbar=no, scrollbars=no, resizable=no");
		}
		else
		{
			<%request.setAttribute("sessionExpiry", "Your session has expired!! Please login again.");%>
			document.forms[0].action = "userAuthAction.do?dispatchMethod=logout";
	 		document.forms[0].method = "post";	
	  		document.forms[0].submit();
		}
	      
		 
	}
	
	function permaStateChooser(tName)
	{
		var sessionStatus = checkinterval(sessionTimeout);
		if(sessionStatus=="Y")
		{
			 window.open("populateMaster.do?qString="+tName+"&label=State&dependant=permaAddState&cleanDependentItems=T~permaAddCity_temp&rowNum=1","popState","width=500, height=490,top=100,left=100, location=no, maximize=yes, menubar=no, status=no, toolbar=no, scrollbars=no, resizable=no","modal=yes" );
		}
		else
		{
			<%request.setAttribute("sessionExpiry", "Your session has expired!! Please login again.");%>
			document.forms[0].action = "userAuthAction.do?dispatchMethod=logout";
	 		document.forms[0].method = "post";	
	  		document.forms[0].submit();
		}
	    
	}
	function offStateChooser(tName)
	{
		var sessionStatus = checkinterval(sessionTimeout);
	    if(sessionStatus=="Y")
		{
			 window.open("populateMaster.do?qString="+tName+"&label=State&dependant=offAddState&cleanDependentItems=T~offAddCity_temp&rowNum=1","popState","width=500, height=490,top=100,left=100, location=no, maximize=yes, menubar=no, status=no, toolbar=no, scrollbars=no, resizable=no","modal=yes" );
		}
		else
		{
			<%request.setAttribute("sessionExpiry", "Your session has expired!! Please login again.");%>
			document.forms[0].action = "userAuthAction.do?dispatchMethod=logout";
	 		document.forms[0].method = "post";	
	  		document.forms[0].submit();
		}
	    
	}
	
	function permaCityChooser(tName)
	{
	      var stateId = document.forms[0].permaAddState.value;
	      var paramList = tName+"~"+stateId;
	      var sessionStatus = checkinterval(sessionTimeout);
	     if(sessionStatus=="Y")
		{
			 window.open("populateMaster.do?qString="+paramList+"&label=City&dependant=permaAddCity&rowNum=1","popCity","width=500, height=490,top=100,left=100, location=no, menubar=no, status=no, toolbar=no, scrollbars=no, resizable=no");
		}
		else
		{
			<%request.setAttribute("sessionExpiry", "Your session has expired!! Please login again.");%>
			document.forms[0].action = "userAuthAction.do?dispatchMethod=logout";
	 		document.forms[0].method = "post";	
	  		document.forms[0].submit();
		}
		  
	}
	function offCityChooser(tName)
	{
	    var stateId = document.forms[0].offAddState.value;
	    var paramList = tName+"~"+stateId;
	    var sessionStatus = checkinterval(sessionTimeout);
	    if(sessionStatus=="Y")
		{
			 window.open("populateMaster.do?qString="+paramList+"&label=City&dependant=offAddCity&rowNum=1","popCity","width=500, height=490,top=100,left=100, location=no, menubar=no, status=no, toolbar=no, scrollbars=no, resizable=no");
		}
		else
		{
			<%request.setAttribute("sessionExpiry", "Your session has expired!! Please login again.");%>
			document.forms[0].action = "userAuthAction.do?dispatchMethod=logout";
	 		document.forms[0].method = "post";	
	  		document.forms[0].submit();
		}
	}
	
	    
	function displayPopup()
	{
		setTime();
		<logic:notEmpty name="strMsgForError">
	       alert("<bean:write name="strMsgForError"/>");
	     </logic:notEmpty> 
	      document.getElementById("divCustEntityTypeInd").style.display="";
	      document.getElementById("divCustEntityTypeCorp").style.display="none";
	      document.getElementById("divCustEntityTypeGroup").style.display="none";
	      document.forms[0].sameAsResidenceAddress.checked = false;
	      document.forms[0].sameAsPermaAddress.checked = false;
		  for(var i=0; i<document.forms[0].rdbtn_mailAdd.length;i++)
	      {
	      	if(document.forms[0].rdbtn_mailAdd[i].value=='R')
	      	{
	      		document.forms[0].rdbtn_mailAdd[i].checked=true;
	      		break;
	      	}
	      }
	      
		applyReadOnlyRules(document);
		<logic:notEmpty name="formNumberMatched">
			
				
 				var landLine = (document.forms[0].telephone.value).split("-");
 				var permanent = (document.forms[0].permaAddTelephone.value).split("-");
 				
			    if(document.forms[0].sameAs.value="on")
			    {
			    	document.forms[0].sameAs.checked=true;
			    }
				for (i=0; i< ProdArr.length; i++)
			    {
				  	document.forms[0].product.options[document.forms[0].product.options.length]= new Option(ProdArr[i][2] ,ProdArr[i][0] );
			    }
			    for (i=0; i< AgencyTypeArr.length; i++)
			    {
				  	document.forms[0].sourcingChannelType.options[document.forms[0].sourcingChannelType.options.length]= new Option(AgencyTypeArr[i][2] ,AgencyTypeArr[i][0] );
			    }
			    for (i=0; i< AgencyTypeArr.length; i++)
			    {
				  	document.forms[0].fulfillmentChannelType.options[document.forms[0].fulfillmentChannelType.options.length]= new Option(AgencyTypeArr[i][2] ,AgencyTypeArr[i][0] );
			    }
			    if(document.forms[0].insurance.value=="Y")
			    {
			    	document.forms[0].hasInsurance.checked=true;
			    }
			    else
			    {
			    	document.forms[0].hasInsurance.checked=false;
			    }
			    document.forms[0].product.value = document.forms[0].hid_product.value;

			    
			    document.forms[0].scheme.value = document.forms[0].hid_scheme.value;
			    

			    document.forms[0].promotionScheme.value=document.forms[0].hid_promotionScheme.value;
			    document.forms[0].sourcingChannelType.value=document.forms[0].hid_sourcingChannelType.value;
			    document.forms[0].fulfillmentChannelType.value=document.forms[0].hid_fulfillmentChannelType.value;
			    
			    clickSrcAgencyType(document.forms[0].sourcingChannelType);
			    clickFflAgencyType(document.forms[0].fulfillmentChannelType);
			    
			   document.forms[0].sourcingChannelName.value = document.forms[0].hid_sourcingChannelName.value;
			   document.forms[0].fulfillmentChannelName.value = document.forms[0].hid_fulfillmentChannelName.value;
			   
			   clickSrcAgency(document.forms[0].sourcingChannelName); 
			   clickFflAgency(document.forms[0].fulfillmentChannelName);
			    
			    document.forms[0].sourcingChannelAgent.value = document.forms[0].hid_sourcingChannelAgent.value;
			    document.forms[0].fulfillmentChannelAgent.value = document.forms[0].hid_fulfillmentChannelAgent.value;
			    document.forms[0].residenceIs.value = document.forms[0].hid_residenceIs.value;
			    document.forms[0].locationCategory.value = document.forms[0].hid_locationCategory.value;
			    document.forms[0].offLocationCategory.value =  document.forms[0].hid_offLocationCategory.value;
			    
			    if('<bean:write name="appDateEnable" scope="application"/>'=='N');
				{
					document.forms[0].applicationDate.disabled=true;
				}
				
			    document.forms[0].std.value = landLine[0];
			    document.forms[0].landLine.value = landLine[1];
			    document.forms[0].permaAddStd.value = permanent[0];
			    document.forms[0].permaAddLandLine.value = permanent[1];
			    sameAsResiAdd();
			    permanentCheck();
			    sameAsResiAdd();
			    alert("App Form Number already Exist");
		</logic:notEmpty>
		<logic:empty name="formNumberMatched">
				
				
				
				//document.forms[0].insurance.value='Y';
				//document.forms[0].hasInsurance.checked=true;
				//alert('<bean:write name="insuranceEnabled" />');
				<logic:equal name="insuranceEnabled" value="Y">
				   document.getElementById("insuranceDiv").style.display="";
				</logic:equal>
				
				document.forms[0].noOfYearAtResidence.readOnly=true;
				document.forms[0].noOfMonthAtResidence.readOnly=true;
				
				
				document.forms[0].product.value="0";
				document.forms[0].scheme.value="0";
				document.forms[0].branch.value="0";
				document.forms[0].loanAmount.value="";
				document.forms[0].tenure.value="";
				
				document.forms[0].sourcingChannelType.value="0";
				document.forms[0].sourcingChannelName.value="0";
				document.forms[0].sourcingChannelAgent.value="0";
				document.forms[0].fulfillmentChannelType.value="0";
				document.forms[0].fulfillmentChannelName.value="0";
				document.forms[0].fulfillmentChannelAgent.value="0";
				document.forms[0].custEntityType.value="0";
				
				document.forms[0].nameOfCompany.value="";
				document.forms[0].authSignatoryFirstName.value="";
				document.forms[0].authSignatoryMiddleName.value="";
				document.forms[0].authSignatoryLastName.value="";
				document.forms[0].dateOfIncorporation.value="";
				document.forms[0].companyPan.value="";
				
				document.forms[0].firstName.value="";
				document.forms[0].middleName.value="";
				document.forms[0].lastName.value="";
				document.forms[0].fatherIstName.value="";
				document.forms[0].fatherIIndName.value="";
				document.forms[0].fatherLastName.value="";
				document.forms[0].motherIstName.value="";
				//document.forms[0].motherMiddleName.value="";
				//document.forms[0].motherLastName.value="";
				
				document.forms[0].promotionScheme.value="0";
				document.forms[0].maritalStatus.value="0";
				document.forms[0].gender.value="0";
				//document.forms[0].status.value="0";
				//document.forms[0].educationQualification.value="0";
				document.forms[0].customerCategory.value="0";
				document.forms[0].panNo.value="";
				document.forms[0].noOfDependent.value="";
				
				//document.forms[0].religion.value="0";
				document.forms[0].residenceIs.value="0";
				document.forms[0].locationCategory.value="0";
				
				document.forms[0].purposeOfLoan.value="0";
				document.forms[0].dob.value="";
				document.forms[0].flatNo.value="";
				document.forms[0].floorNo.value="";
				document.forms[0].builingName.value="";
				document.forms[0].locality.value="";
				document.forms[0].landMark.value="";
				
				document.forms[0].state.value="0";
				document.forms[0].city.value="0";
				document.forms[0].state_temp.value="";
				document.forms[0].city_temp.value="";
				document.forms[0].permaAddState.value="";
				document.forms[0].permaAddCity.value="";
				document.forms[0].permaAddState_temp.value="";
				document.forms[0].permaAddCity_temp.value="";
				document.forms[0].zipOrPin.value="";
				document.forms[0].mobile.value="";
				document.forms[0].telephone.value="";
				document.forms[0].email.value="";
				document.forms[0].noOfYearAtResidence.value="";
				document.forms[0].noOfMonthAtResidence.value="";
				document.forms[0].sameAs.value="";
				document.forms[0].permaAddFlatNo.value="";
				document.forms[0].permaAddFloorNo.value="";
				document.forms[0].permaAddBuildingName.value="";
				document.forms[0].permaAddLocality.value="";
				document.forms[0].permaAddLandMark.value="";
				document.forms[0].permaAddZipORPin.value="";
				document.forms[0].permaAddMobile.value="";
				document.forms[0].permaAddTelephone.value="";
				document.forms[0].permaAddEmail.value="";				
				document.forms[0].permaNoOfYearAtResidence.value="";
				document.forms[0].permaNoOfMonthAtResidence.value="";
				document.forms[0].formNumber.value="";
				 document.forms[0].std.value = "";
			    document.forms[0].landLine.value = "";
			    document.forms[0].permaAddStd.value = "";
			    document.forms[0].permaAddLandLine.value = "";
			    
			    document.forms[0].offAddFlatNo.value = "";
			    document.forms[0].offAddFloorNo.value = "";
			    document.forms[0].offAddBuildingName.value = "";
			    document.forms[0].offAddLocality.value = "";
			    document.forms[0].offAddLandMark.value = "";
			    document.forms[0].offAddState_temp.value = "";
			    document.forms[0].offAddCity_temp.value = "";
			    document.forms[0].offAddZipORPin.value = "";
			    document.forms[0].offAddMobile.value = "";
			    document.forms[0].offAddStd.value = "";
			    document.forms[0].offAddLandLine.value = "";
			    document.forms[0].offAddEmail.value = "";
			    document.forms[0].offLocationCategory.value = "0";
			    document.forms[0].offCompanyName.value = "";
			    document.forms[0].offNoOfYearAtResidence.value = "";
			    document.forms[0].offNoOfMonthAtResidence.value = "";
			    document.forms[0].salesManager.value="";
				document.forms[0].salesManager_temp.value="";
				//document.forms[0].branchManager.value="";
				//document.forms[0].branchManager_temp.value="";
				document.forms[0].fax.value="";
				document.forms[0].extention1.value="";
			    document.forms[0].codeOfGroup.value="";
			    document.forms[0].memberCodeOfGroup.value="";
			    
			    
				document.forms[0].businessDate.value =businessDate;
				document.forms[0].applicationDate.value=document.forms[0].businessDate.value;
				<logic:equal name="appDateEnable" value="N">
					document.forms[0].applicationDate.disabled=true;
				</logic:equal>
				<logic:equal name="appDateEnable" value="Y">
					document.forms[0].applicationDate.disabled=false;
				</logic:equal>
				//alert("document.forms[0].businessDate.value=== "+document.forms[0].businessDate.value);
				for (i=0; i< ProdArr.length; i++)
			    {
				  	document.forms[0].product.options[document.forms[0].product.options.length]= new Option(ProdArr[i][2] ,ProdArr[i][0] );
			    }
			    clickProduct(document.forms[0].product);
			    
			    for (i=0; i< AgencyTypeArr.length; i++)
			    {
				  	document.forms[0].sourcingChannelType.options[document.forms[0].sourcingChannelType.options.length]= new Option(AgencyTypeArr[i][2] ,AgencyTypeArr[i][0] );
			    }
			    
			    for (i=0; i< AgencyTypeArr.length; i++)
			    {
				  	document.forms[0].fulfillmentChannelType.options[document.forms[0].fulfillmentChannelType.options.length]= new Option(AgencyTypeArr[i][2] ,AgencyTypeArr[i][0] );
			    }
			    
			    clickSrcAgencyType(document.forms[0].sourcingChannelType);
			    clickFflAgencyType(document.forms[0].fulfillmentChannelType);
			      <% 
					//HttpSession sess = request.getSession();
					if( request.getAttribute("prospectNo") != null)
					{
						String prospectNo = request.getAttribute("prospectNo").toString();
						//System.out.println(prospectNo);
						if(prospectNo.length() > 0)
						{
				%>
							alert("Prospect saved with Prospect No # " + "<%=prospectNo%>");
				<%
							request.removeAttribute("prospectNo");
						}
					}
				%>
			</logic:empty>
			
			//the following function is used to set tootip of text box in case it is disabled
				setTitle(document); 
				changeCustomerEntityType();
				setMailingAddress();
				
			
	}
	
	function clickFflAgencyType(obj)
	{
	    var agencyTypeID = obj[obj.selectedIndex].value;
		clearcombo(document.forms[0].fulfillmentChannelName);
		for (i=0; i< AgencyNameArr.length; i++)
	    {
	    	
	    	if (agencyTypeID == AgencyNameArr[i][3])
	    	{
		  		document.forms[0].fulfillmentChannelName.options[document.forms[0].fulfillmentChannelName.options.length]= new Option(AgencyNameArr[i][2] ,AgencyNameArr[i][0] );
		  	}
	    }
	    clickFflAgency(document.forms[0].fulfillmentChannelName);
	}
	
	function clickFflAgency(obj)
	{
		var agencyID = obj[obj.selectedIndex].value;
		clearcombo(document.forms[0].fulfillmentChannelAgent);
		for (i=0; i< AgentArr.length; i++)
	    {
	    	
	    	if (agencyID == AgentArr[i][3])
	    	{
		  		document.forms[0].fulfillmentChannelAgent.options[document.forms[0].fulfillmentChannelAgent.options.length]= new Option(AgentArr[i][2] ,AgentArr[i][0] );
		  	}
	    }
	}
	
	function toggleLoanCenter(productId)
	{
		mfinType = false;
		for (i=0; i<SchemeArr.length; i++)
	    {
	    	if(productId == SchemeArr[i][1] && SchemeArr[i][10] == "MICROFIN" && SchemeArr[i][9] == "1000000002")
	    	{
		  		mfinType = true;
		  		break;
		  	}
	    }
	    document.getElementById("mfinloancenterrow").style.display = (mfinType?"block":"none");
	    document.getElementById("mfingroupsdiv").style.display = (mfinType?"block":"none");
	    document.getElementById("divSourcingTypeInd").style.display=(mfinType?"none":"block");
	    document.getElementById("divManagerType").style.display=(mfinType?"none":"block");
	   	document.getElementById("officeid").style.display=(mfinType?"none":"block");
	   	document.getElementById("permanentid").style.display=(mfinType?"none":"block");
	}
	
	
	
	
	
	
	
	
	/*function schemeCenter(productId)
	 {
	 alert("pre");
	 //var category="";
	  var scheme=false;
	 
	 for (i=0; i<SchemeArr.length; i++)
	  {
	     //category=SchemeArr[i][9]
	      //prid="";
	 	 	//if(category=="1000000002"&&)
	 	 	if(productId == SchemeArr[i][1] && SchemeArr[i][9] == "1000000002")
	   			{
	   				alert("category"+category)
	    			//document.getElementById("divSourcingTypeInd").style.display="none";
	   			 	//document.getElementById("divManagerType").style.display="none";
	   			 	scheme=true;
	   			 	break;
	   			}
	   		}	
	   		document.getElementById("divSourcingTypeInd").style.display=(scheme?"block":"none");
	   		document.getElementById("divManagerType").style.display=(scheme?"block":"none");
	   }*/
	  
	  
	
	
	function clickScheme(obj)
	{
		var schemeID = obj[obj.selectedIndex].value;
		clearcombo(document.forms[0].promotionScheme);
		for (i=0; i< promoArr.length; i++)
	    {
	    	if (schemeID == promoArr[i][3])
	    	{
		  		document.forms[0].promotionScheme.options[document.forms[0].promotionScheme.options.length]= new Option(promoArr[i][2] ,promoArr[i][0] );
		  	}
	    }

	//var PurofloanID=obj[obj.selectedIndex].value;
	 clearcombo(document.forms[0].purposeOfLoan);
	 //alert("len- "+loanpurposeArr.length)
	 for (i=0; i< loanpurposeArr.length; i++)
	    {
	    //alert(schemeID+ " -  "+ loanpurposeArr[i][3] +"  --  "+ loanpurposeArr[i][2]+  " -- " +loanpurposeArr[i][0]);
	    	if (schemeID == loanpurposeArr[i][3])
	    	{
		  		document.forms[0].purposeOfLoan.options[document.forms[0].purposeOfLoan.options.length]= new Option(loanpurposeArr[i][2] ,loanpurposeArr[i][0] );
		  	}
	    }

		//clearcombo(document.forms[0].custEntityType);
	    /*
	    changeCustomerEntityType();
		for (i=0; i<EntityTypeArr.length; i++)
	    {
	    	if (schemeID == EntityTypeArr[i][0])
	    	{
		  		document.forms[0].custEntityType.options[document.forms[0].custEntityType.options.length]= new Option(EntityTypeArr[i][2] ,EntityTypeArr[i][1] );
		  	}
	    }
	    document.forms[0].custEntityType.options[1].selected = true;
	    changeCustomerEntityType();
	    toggleLoanCenter(schemeID);
	    */
	}
	
	function clickBranch(obj)
	{
		//1.0.0.1 Start By Sanchi Agarwal : Check server-side session before clearing/repopulating dropdowns
		var sessionStatus = checkSessionStatus(sessionTimeout);
		if(sessionStatus!="Y")
		{
			<%request.setAttribute("sessionExpiry", "Your session has expired!! Please login again.");%>
			document.forms[0].action = "userAuthAction.do?dispatchMethod=logout";
	 		document.forms[0].method = "post";	
	  		document.forms[0].submit();
	  		return;
		}
		//1.0.0.1 End
		var branchID = obj[obj.selectedIndex].value;
		
		clearcombo(document.forms[0].loanCenterId);
		
		for(i=0; i< LoanCenterArr.length; i++)
	    {
	    	if (branchID == LoanCenterArr[i][0])
	    	{
		  		document.forms[0].loanCenterId.options[document.forms[0].loanCenterId.options.length]= new Option(LoanCenterArr[i][2],LoanCenterArr[i][1]);
		  	}
	    }
	    
	    clearcombo(document.forms[0].recruitedByGroup);
		
		for(i=0; i< loanOfficerArr.length; i++)
	    {
	    	if (branchID == loanOfficerArr[i][0])
	    	{
		  		document.forms[0].recruitedByGroup.options[document.forms[0].recruitedByGroup.options.length]= new Option(loanOfficerArr[i][2],loanOfficerArr[i][0]);
		  	}
	    }
	     clearcombo(document.forms[0].recruitedByGroupMember);
		
		for(i=0; i< loanOfficerArr.length; i++)
	    {
	    	if (branchID == loanOfficerArr[i][0])
	    	{
		  		document.forms[0].recruitedByGroupMember.options[document.forms[0].recruitedByGroupMember.options.length]= new Option(loanOfficerArr[i][2],loanOfficerArr[i][0]);
		  	}
	    }
	}
	

	function clickSrcAgencyType(obj)
	{
		//1.0.0.1 Start By Sanchi Agarwal : Check server-side session before clearing/repopulating dropdowns
		var sessionStatus = checkSessionStatus(sessionTimeout);
		if(sessionStatus!="Y")
		{
			<%request.setAttribute("sessionExpiry", "Your session has expired!! Please login again.");%>
			document.forms[0].action = "userAuthAction.do?dispatchMethod=logout";
	 		document.forms[0].method = "post";	
	  		document.forms[0].submit();
	  		return;
		}
		//1.0.0.1 End
		var agencyTypeID = obj[obj.selectedIndex].value;
		clearcombo(document.forms[0].sourcingChannelName);
		
		for (i=0; i< AgencyNameArr.length; i++)
	    {
	    	if (agencyTypeID == AgencyNameArr[i][3])
	    	{
		  		document.forms[0].sourcingChannelName.options[document.forms[0].sourcingChannelName.options.length]= new Option(AgencyNameArr[i][2] ,AgencyNameArr[i][0] );
		  	}
	    }
	    clickSrcAgency(document.forms[0].sourcingChannelName);
	}
	
	function clickSrcAgency(obj)
	{
		var agencyID = obj[obj.selectedIndex].value;
		clearcombo(document.forms[0].sourcingChannelAgent);
		for (i=0; i< AgentArr.length; i++)
	    {
	    	
	    	if (agencyID == AgentArr[i][3])
	    	{
		  		document.forms[0].sourcingChannelAgent.options[document.forms[0].sourcingChannelAgent.options.length]= new Option(AgentArr[i][2] ,AgentArr[i][0]);
		  	}
	    }
	}
	
	function clickProduct(obj)
	{
		var prodID = obj[obj.selectedIndex].value;
		
		clearcombo(document.forms[0].scheme);
		
		for (i=0; i< SchemeArr.length; i++)
	    {
	    	if (prodID == SchemeArr[i][0])
	    	{
	    		document.forms[0].scheme.options[document.forms[0].scheme.options.length]= new Option(SchemeArr[i][2] ,SchemeArr[i][1] );
	    	}
	    }
	    
	    //toggleLoanCenter(prodID);
	    
	     
	    
	}
	
	function clearcombo(elem)
	{
		var i;
		for (i = elem.options.length - 1; i >= 0; i--) 
		{
		       elem.options[i] = null;
		}
		elem.selectedIndex = -1;
		elem.options[elem.options.length]= new Option("--Select--" ,"0" );
	}
	
	function enableAllElements()
    {
    	
	   for(count=0; count < document.forms[0].elements.length; count+=1)
       {
       	theelement = document.forms[0].elements[count]
           if(theelement.name != null)
           {
           	theelement.disabled = false;
           }
       }
		  
	   
    }
	
//function to enable  noOfYearAtResidence/noOfMonthAtResidence fields if we are selecting residance is combo box
	function enableFields()
	{
		if(document.forms[0].residenceIs.selectedIndex!=0)
		{
			document.forms[0].noOfYearAtResidence.readOnly=false;
			document.forms[0].noOfMonthAtResidence.readOnly=false;
			document.forms[0].noOfYearAtResidence.value="";
			document.forms[0].noOfMonthAtResidence.value="";
		}
		else
		{
			document.forms[0].noOfYearAtResidence.readOnly=true;
			document.forms[0].noOfMonthAtResidence.readOnly=true;
			document.forms[0].noOfYearAtResidence.value="";
			document.forms[0].noOfMonthAtResidence.value="";
		}
	}

 
// Validation on save Only function	
	function validationOfPermanent()
	{
		atLeastOneFieldBetweenThree(document.forms[0].permaAddFlatNo,document.forms[0].permaAddBuildingName,document.forms[0].permaAddLocality,'Flat No of permanent address','Builing Name of permanent address','Locality of permanent address');
		validate(document.forms[0].permaAddState_temp,"TMV",'State of permanent address');
		validate(document.forms[0].permaAddCity_temp,"TMV",'City of permanent address');
		validate(document.forms[0].permaAddZipORPin,"TMV",'Pincode of permanent address');
		validate(document.forms[0].locationCategory,"CMV","Occupation Status of permanent address");
 		validate(document.forms[0].permaNoOfYearAtResidence,"TMV","No of year at residence of permanent address");
	 	validate(document.forms[0].permaNoOfMonthAtResidence,"TMV","No of months at residence of permanent address");
	}
	function validationOfOffice()
	{
		validate(document.forms[0].offCompanyName,"TMV",'Name Of Company of office address');
		atLeastOneFieldBetweenThree(document.forms[0].offAddFlatNo,document.forms[0].offAddBuildingName,document.forms[0].offAddLocality,'Flat No of office address','Builing Name of office address','Locality of office address');
		validate(document.forms[0].offAddState_temp,"TMV",'State of office address');
		validate(document.forms[0].offAddCity_temp,"TMV",'City of office address');
		validate(document.forms[0].offAddZipORPin,"TMV",'Pincode of office address');
		validate(document.forms[0].offLocationCategory,"CMV","Occupation Status of office address");
 		validate(document.forms[0].offNoOfYearAtResidence,"TMV","No of year at residence of office address");
	 	validate(document.forms[0].offNoOfMonthAtResidence,"TMV","No of months at residence of office address");
	}
	function setMandatoryPermanentAddress()
	{
		
		if(document.forms[0].hdn_mailAdd.value=="P" ||(document.forms[0].permaAddFlatNo.value!=""||document.forms[0].permaAddBuildingName.value!="" ||document.forms[0].permaAddFloorNo.value!=""||document.forms[0].permaAddLandMark.value!=""||document.forms[0].permaAddLocality.value!=""||document.forms[0].permaAddState_temp.value!=""||document.forms[0].permaAddCity_temp.value!=""||document.forms[0].permaAddZipORPin.value!=""||document.forms[0].permaAddMobile.value!=""||document.forms[0].permaAddStd.value!=""||document.forms[0].permaAddLandLine.value!=""||document.forms[0].permaAddEmail.value!=""||document.forms[0].locationCategory.selectedIndex!=0||document.forms[0].permaNoOfYearAtResidence.value!=""||document.forms[0].permaNoOfMonthAtResidence.value!=""))
		{
			validationOfPermanent();
		}
		if(document.forms[0].hdn_mailAdd.value=="O" ||(document.forms[0].offCompanyName.value!=""||document.forms[0].offAddFlatNo.value!=""||document.forms[0].offAddBuildingName.value!="" ||document.forms[0].offAddFloorNo.value!=""||document.forms[0].offAddLandMark.value!=""||document.forms[0].offAddLocality.value!=""||document.forms[0].offAddState_temp.value!=""||document.forms[0].offAddCity_temp.value!=""||document.forms[0].offAddZipORPin.value!=""||document.forms[0].offAddMobile.value!=""||document.forms[0].offAddStd.value!=""||document.forms[0].offAddLandLine.value!=""||document.forms[0].offAddEmail.value!=""||document.forms[0].offLocationCategory.selectedIndex!=0||document.forms[0].offNoOfYearAtResidence.value!=""||document.forms[0].offNoOfMonthAtResidence.value!=""))
		{
			validationOfOffice();
		}
		
	}
	
 	function checkForm()
 	{
 		var objToFocus=null;
		tempData.errorMessages="Errors:";
 		
 		if(activityProcess==1)
    	{
    	   alert("Please wait activity is in process");
    	   return false;
    	}
 		applyValidation(document);
 		setMandatoryPermanentAddress();
 		//Validation on product detail fieldset
		
 		//validation on application date
 		validate(document.forms[0].formNumber,"ANV,NZV",'<bean:message bundle="losApplicationResource" key="los.newProspect.formNumber"/>');
 		CompareTwoDatesGreater(document.forms[0].applicationDate,document.forms[0].businessDate,'<bean:message bundle="losApplicationResource" key="los.newProspect.appDate"/>','Business Date'); 		
 		
 		if(mfinType)
 		{
 			validate(document.forms[0].loanCenterId,"CMV",'Loan Center');
 		}
 		
 		//Validation for loan Amount field 		
     	validate(document.forms[0].loanAmount,"TNV,NZV",'<bean:message bundle="losApplicationResource" key="los.newProspect.loanAmount"/>');
 		//Validation on Loan Range by Sanjay-Kumar////////////
		var sch_Value=document.forms[0].scheme.value;
		
		for(var i=0;i<SchemeArr.length;i++)
		{
			if(sch_Value==SchemeArr[i][1])
			{
				var minLoan=SchemeArr[i][5];
				var maxLoan=SchemeArr[i][6];
				 ageMin = Number(SchemeArr[i][7]);
				 ageMax  = Number(SchemeArr[i][8]);
				break;
			}
		}
		if(!checkBlankWithoutMessage(document.forms[0].loanAmount))
		{
			if(minLoan==""||maxLoan=="")
			{
				if(maxLoan!="")
				{
					if(Number(maxLoan)<Number(document.forms[0].loanAmount.value))
					{
						tempData.errorMessages=tempData.errorMessages+"\n"+"Loan Amount can't be greater than"+maxLoan;
					}
				}
				else if(minLoan!="")
				{
					if(Number(minLoan)>Number(document.forms[0].loanAmount.value))
					{
						tempData.errorMessages=tempData.errorMessages +"\n"+"Loan Amount can't be less than"+minLoan;
					}
				}
			}
			else
			{
				checkValueBetween(document.forms[0].loanAmount,minLoan,maxLoan,'<bean:message bundle="losApplicationResource" key="los.newProspect.loanAmount"/>');
			}
		}
 	
 		//This code is used for dericve min and max loan
 		var schemeId=document.forms[0].scheme.value;
 		
		var tenureValue=document.forms[0].tenure.value;
		var maxTenure;
		var minTenure;
		for(var i=0;i<SchemeArr.length;i++)
		{
		   
			if(schemeId==SchemeArr[i][1])
			{
				minTenure=SchemeArr[i][3];
				maxTenure=SchemeArr[i][4];
				break;
			}
		}
		if(!checkBlankWithoutMessage(document.forms[0].tenure))
		{
			validate(document.forms[0].tenure,"TNV",'<bean:message bundle="losApplicationResource" key="los.newProspect.tenor"/>');
			checkValueBetween(document.forms[0].tenure,minTenure,maxTenure,'<bean:message bundle="losApplicationResource" key="los.newProspect.tenor"/>');
		}
		
		if(document.forms[0].custEntityType.value=="1000000002")
		{
			if(document.forms[0].dateOfIncorporation.value!="")
			{
				CompareTwoDatesGreater(document.forms[0].dateOfIncorporation,document.forms[0].businessDate,'Date Of Incorporation','Business Date');
			}
			
			validate(document.forms[0].nameOfCompany,"AVN,CNO",'<bean:message bundle="losApplicationResource" key="los.newProspect.nameOfCompany"/>');
			validate(document.forms[0].authSignatoryFirstName,"NV",'<bean:message bundle="losApplicationResource" key="los.newProspect.authorizedSignatoryFName"/>');
			validate(document.forms[0].authSignatoryMiddleName,"NV",'<bean:message bundle="losApplicationResource" key="los.common.middleName"/>');
			validate(document.forms[0].authSignatoryLastName,"NV",'<bean:message bundle="losApplicationResource" key="los.common.lastName"/>');
			validate(document.forms[0].companyPan,"PANV",'<bean:message bundle="losApplicationResource" key="los.newProspect.companyPAN"/>');
		}

 		if(document.forms[0].custEntityType.value=="1000000004" && mfinType)
 		{
 			validate(document.forms[0].groupId,"CMV",'Group');
 		}
 		if(document.forms[0].scheme.value=="1000000044" && document.forms[0].custEntityType.value=="1000000004")
 		{
 			validate(document.forms[0].recruitedByGroupMember,"CMV",'Recruited By');
 			validate(document.forms[0].codeOfGroup,"TMV",'Group Code');
 			validate(document.forms[0].codeOfGroup,"ANV",'Group Code');
 		}
 		if(document.forms[0].scheme.value=="1000000045" && document.forms[0].custEntityType.value=="1000000003")
 		{
 			validate(document.forms[0].recruitedByGroup,"CMV",'Recruited By');
 			validate(document.forms[0].memberCodeOfGroup,"TMV",'Group Member Code');
 			validate(document.forms[0].memberCodeOfGroup,"ANV",'Group Member Code');
 			
 		}
 		validate(document.forms[0].firstName,"NV",'<bean:message bundle="losApplicationResource" key="los.common.firstName"/>');
 		validate(document.forms[0].middleName,"NV",'<bean:message bundle="losApplicationResource" key="los.common.middleName"/>');
 		validate(document.forms[0].lastName,"NV",'<bean:message bundle="losApplicationResource" key="los.common.lastName"/>');
 		validate(document.forms[0].fatherIstName,"NV",'<bean:message bundle="losApplicationResource" key="los.newProspect.fatherFirstName"/>');
 		validate(document.forms[0].fatherIIndName,"NV",'<bean:message bundle="losApplicationResource" key="los.common.middleName"/>');
 		validate(document.forms[0].fatherLastName,"NV",'<bean:message bundle="losApplicationResource" key="los.common.lastName"/>');
 		validate(document.forms[0].motherIstName,"NV",'<bean:message bundle="losApplicationResource" key="los.newProspect.motherMaiden"/>');
 		//validate(document.forms[0].motherMiddleName,"NV",'<bean:message bundle="losApplicationResource" key="los.common.middleName"/>');
 		//validate(document.forms[0].motherLastName,"NV",'<bean:message bundle="losApplicationResource" key="los.common.lastName"/>');
 	 		
		//Validation for Date of birth field
 		if(!checkBlankWithoutMessage(document.forms[0].dob))
		{
			 
			CheckDateOfBirthNew(document.forms[0].dob,document.forms[0].businessDate.value,ageMin,ageMax,'<bean:message bundle="losApplicationResource" key="los.newProspect.dateOfBirth"/>');
		}
		validate(document.forms[0].noOfDependent,"TNV",'<bean:message bundle="losApplicationResource" key="los.personalInfo.NoOfDependents"/>');
 	 	validate(document.forms[0].panNo,"PANV",'<bean:message bundle="losApplicationResource" key="los.personalInfo.PanNo"/>');

 		//Validation on residence address fieldset
			
		//Validation for Flat Number/Building Number/Locality Fields		
 		atLeastOneFieldBetweenThree(document.forms[0].flatNo,document.forms[0].builingName,document.forms[0].locality,'<bean:message bundle="losApplicationResource" key="los.newProspect.flatHouseNo"/>','<bean:message bundle="losApplicationResource" key="los.newProspect.buildingName"/>','<bean:message bundle="losApplicationResource" key="los.newProspect.locality"/>');
		validate(document.forms[0].flatNo,"AVN",'<bean:message bundle="losApplicationResource" key="los.newProspect.flatHouseNo"/>');
 		validate(document.forms[0].floorNo,"AVN",'<bean:message bundle="losApplicationResource" key="los.newProspect.floorNo"/>');
 		validate(document.forms[0].builingName,"AV",'<bean:message bundle="losApplicationResource" key="los.newProspect.buildingName"/>');
 		validate(document.forms[0].locality,"AV",'<bean:message bundle="losApplicationResource" key="los.newProspect.locality"/>');
 		validate(document.forms[0].zipOrPin,"TNV,NZV",'<bean:message bundle="losApplicationResource" key="los.newProspect.zipPin"/>');
		checkMinLength(document.forms[0].zipOrPin,'<bean:message bundle="losApplicationResource" key="los.newProspect.zipPin"/>',6);
		if(!checkBlankWithoutMessage(document.forms[0].zipOrPin))
		{
		 var resiPinValue =document.forms[0].zipOrPin.value;
		 //alert(resiPinValue);
		 //alert(resiPinValue.substring(3,6));
		 if(resiPinValue.substring(3,6)=="000")
		 {
		   tempData.errorMessages = tempData.errorMessages +"\n"+"Pincode should not contain last three consecutive Zero";
		   
		 }
		}
 		validate(document.forms[0].mobile,"MBV",'<bean:message bundle="losApplicationResource" key="los.newProspect.mobile"/>');
 		validate(document.forms[0].std,"TNV,NZV","STD");
 		validate(document.forms[0].landLine,"TNV,NZV",'<bean:message bundle="losApplicationResource" key="los.newProspect.telephone"/>');
 		if(document.forms[0].std.value=="" && document.forms[0].landLine.value!="")
 		{
 			validate(document.forms[0].std,"TMV","STD");
 		}
 		else if(document.forms[0].std.value!="" && document.forms[0].landLine.value=="")
 		{
 			validate(document.forms[0].landLine,"TMV",'<bean:message bundle="losApplicationResource" key="los.newProspect.telephone"/>');
 		}
 		checkMinLength(document.forms[0].std,"STD",3);
 		checkMinLength(document.forms[0].landLine,'<bean:message bundle="losApplicationResource" key="los.newProspect.telephone"/>',6);
		//Validation for E-Mail Id	
		validate(document.forms[0].email,"EV",'<bean:message bundle="losApplicationResource" key="los.newProspect.email"/>');
		//Validation for year field	
 		validate(document.forms[0].noOfYearAtResidence,"TNV","No of year at residence");
 		validate(document.forms[0].noOfMonthAtResidence,"TNV","No of months at residence");
 		
 		twoCanNotBeZero(document.forms[0].noOfYearAtResidence,document.forms[0].noOfMonthAtResidence,"No of year at residence","No of months at residence");
 		
 		checkValueBetween(document.forms[0].noOfMonthAtResidence,0,11,"No of months at residence");
 		/*var noOfMonth = Number((document.forms[0].noOfYearAtResidence.value)*12)+Number(document.forms[0].noOfMonthAtResidence.value);
 		alert("no of months"+noOfMonth);
 		if(document.forms[0].dob.value!="")
 		{
 			notGreaterThanAge(document.forms[0].dob,noOfMonth,'<bean:message bundle="losApplicationResource" key="los.newProspect.residenceStatus"/>');
 		}*/
	 			
		//Validation on permanent address fieldset
 		validate(document.forms[0].permaAddFlatNo,"AVN",'Flat No of permanent address');
 		validate(document.forms[0].permaAddFloorNo,"AVN",'Street Name of permanent address');
 		validate(document.forms[0].permaAddBuildingName,"AV",'Builing Name of permanent address');
 		validate(document.forms[0].permaAddLocality,"AV",'Locality of permanent address');

		//Validation for Zip/Pin	
		validate(document.forms[0].permaAddZipORPin,"TNV,NZV",'Pincode of permanent address');
		checkMinLength(document.forms[0].permaAddZipORPin,'pincode of permanent address',6);	
		if(!checkBlankWithoutMessage(document.forms[0].permaAddZipORPin))
		{
		 var permaPinValue =document.forms[0].permaAddZipORPin.value;
		 if(permaPinValue.substring(3,6)=="000")
		 {
		   tempData.errorMessages = tempData.errorMessages +"\n"+"Pincode of permanent address should not contain last three consecutive Zero";
		   
		 }
		}
		validate(document.forms[0].permaAddMobile,"MBV",'Mobile of permanent address');
		
 		validate(document.forms[0].permaAddStd,"TNV,NZV","STD of permanent address");
 		validate(document.forms[0].permaAddLandLine,"TNV,NZV",'Landline of permanent address');
 		if(document.forms[0].permaAddStd.value=="" && document.forms[0].permaAddLandLine.value!="")
 		{
 			validate(document.forms[0].permaAddStd,"TMV","STD of permanent address");
 		}
 		else if(document.forms[0].permaAddStd.value!="" && document.forms[0].permaAddLandLine.value=="")
 		{
 			validate(document.forms[0].permaAddLandLine,"TMV",'Landline of permanent address');
 		}
 		checkMinLength(document.forms[0].permaAddStd,"STD of permanent address",3);
 		checkMinLength(document.forms[0].permaAddLandLine,'Landline of permanent address',6);
 		validate(document.forms[0].permaAddEmail,"EV",'E-Mail of permanent address');
 		validate(document.forms[0].permaNoOfYearAtResidence,"TNV","No of year at residence of permanent address");
 		validate(document.forms[0].permaNoOfMonthAtResidence,"TNV","No of months at residence of permanent address");
 		
 		twoCanNotBeZero(document.forms[0].permaNoOfYearAtResidence,document.forms[0].permaNoOfMonthAtResidence,"No of year at residence of permanent address","No of months at residence of permanent address");
 		
 		checkValueBetween(document.forms[0].permaNoOfMonthAtResidence,0,11,"No of months at residence of permanent address");
 		validate(document.forms[0].offCompanyName,"AVN,CNO",'Name Of Company of office address');
 		validate(document.forms[0].offAddFlatNo,"AVN",'Flat No of office address');
 		validate(document.forms[0].offAddFloorNo,"AVN",'Street Name of office address');
 		validate(document.forms[0].offAddBuildingName,"AV",'Builing Name of office address');
 		validate(document.forms[0].offAddLocality,"AV",'Locality of office address');

		//Validation for Zip/Pin	
		validate(document.forms[0].offAddZipORPin,"TNV,NZV",'Pincode of office address');
		checkMinLength(document.forms[0].offAddZipORPin,'Pincode of office address',6);	
		if(!checkBlankWithoutMessage(document.forms[0].offAddZipORPin))
		{
		 var offPinValue =document.forms[0].offAddZipORPin.value;
		 if(offPinValue.substring(3,6)=="000")
		 {
		   tempData.errorMessages = tempData.errorMessages +"\n"+"Pincode of office address should not contain last three consecutive Zero";
		   
		 }
		}
		validate(document.forms[0].offAddMobile,"MBV",'Mobile of office address');
 		validate(document.forms[0].offAddStd,"TNV,NZV","STD of office address");
 		validate(document.forms[0].offAddLandLine,"TNV,NZV",'Landline of office address');
 		if(document.forms[0].offAddStd.value=="" && document.forms[0].offAddLandLine.value!="")
 		{
 			validate(document.forms[0].offAddStd,"TMV","STD of office address");
 		}
 		else if(document.forms[0].offAddStd.value!="" && document.forms[0].offAddLandLine.value=="")
 		{
 			validate(document.forms[0].offAddLandLine,"TMV",'Landline of office address');
 		}
 		checkMinLength(document.forms[0].offAddStd,"STD of office address",3);
 		checkMinLength(document.forms[0].offAddLandLine,'Landline of office address',6);
 		validate(document.forms[0].extention1,"TNV,NZV",'Extension of office address');
 		validate(document.forms[0].offAddEmail,"EV",'E-Mail of office address');
 		validate(document.forms[0].fax,"TNV,NZV",'Fax of office address');
 		validate(document.forms[0].offNoOfYearAtResidence,"TNV","No of year at residence of office address");
	 	validate(document.forms[0].offNoOfMonthAtResidence,"TNV","No of months at residence of office address");
	 	twoCanNotBeZero(document.forms[0].offNoOfYearAtResidence,document.forms[0].offNoOfMonthAtResidence,"No of year at residence of office address","No of months at residence of office address");
	 	checkValueBetween(document.forms[0].offNoOfMonthAtResidence,0,11,"No of months at residence of office address");
	 
 		document.forms[0].telephone.value=document.forms[0].std.value+"-"+document.forms[0].landLine.value;	
 		document.forms[0].permaAddTelephone.value=document.forms[0].permaAddStd.value+"-"+document.forms[0].permaAddLandLine.value;
 		document.forms[0].offAddTelephone.value=document.forms[0].offAddStd.value+"-"+document.forms[0].offAddLandLine.value;
 		
 		if((document.forms[0].extention1.value).length>0)
 		{
 			if((document.forms[0].offAddLandLine.value).length==0)
 		    {
 			   tempData.errorMessages = tempData.errorMessages +"\n"+"Landline should not be blank.";
 		    }
 		}
		setMailingAddress();
 	}

	function setMailingAddress()
 	{
 		var mailingAddStatus=false;
 		for(var i=0; i<document.forms[0].rdbtn_mailAdd.length;i++)
 		{
 			if(document.forms[0].rdbtn_mailAdd[i].checked)
 			{
 				mailingAddStatus=true;
 				document.forms[0].hdn_mailAdd.value=document.forms[0].rdbtn_mailAdd[i].value
 				break;
 			}
 		}
 		if(!mailingAddStatus)
 		{
 			alert("Please Select atleast one address as mailing address");
 			return ; 
 		}
 	}

	function saveDataAndExit()
	{
		//alert("saveDataAndExit");
		document.forms[0].submitType.value = "saveAndExit";
		saveData();
	}
	
	function saveDataOnly()
	{
		//alert("saveDataOnly");
		document.forms[0].submitType.value = "saveOnly";
		saveData();
	}

 	function saveData()
 	{
 		//1.0.0.1 Start By Sanchi Agarwal : Check server-side session before saving
 		var sessionStatus = checkSessionStatus(sessionTimeout);
 		if(sessionStatus!="Y")
 		{
 			<%request.setAttribute("sessionExpiry", "Your session has expired!! Please login again.");%>
 			document.forms[0].action = "userAuthAction.do?dispatchMethod=logout";
	 		document.forms[0].method = "post";	
	  		document.forms[0].submit();
	  		return;
 		}
 		//1.0.0.1 End
 		checkForm();
 		if(tempData.errorMessages!="Errors:")
		{
			alert(tempData.errorMessages);
			return;
		}
		var bool = confirm("Do you want to save Data?");
	    if(!bool)
	    {
	    	return;
	    }
	    else
	    {
		    
		    document.forms[0].applicationDate.disabled=false;
		    ///enableAllElements();
			document.forms[0].action = "prospectCRUD.do?actionPerformed=save";
	 		document.forms[0].method = "post";	
	  		document.forms[0].submit();
	  		activityProcess=1;
	  		return; 
	    }
 	}
 	
//This function is used to validate Tenure Field
function validateTenure()
{
	var schemeId=document.forms[0].scheme.value;
	var tenureValue=document.forms[0].tenure.value;
	var minTenure;
	var maxTenure;
	for(var i=0;i<SchemeArr.length;i++)
	{
	   
		if(schemeId==SchemeArr[i][1])
		{
			minTenure=SchemeArr[i][3];
			maxTenure=SchemeArr[i][4];
			break;
		}
	}
	
	
	if(tenureValue < eval(minTenure))
	{
	   
		alert("Please enter Tenure between "+minTenure+ " and " +maxTenure);
		return;
	}
	else if(tenureValue > eval(maxTenure))
	{
		alert("Please enter Tenure between "+minTenure+ " and " +maxTenure);
		return;
	}
	else
	{
		return true;
	}
	
}
 
 function isInsuranceChecked()
 	{
 		
 		if(document.forms[0].hasInsurance.checked)
 		{
 		    
 		    document.forms[0].insurance.value='Y';			
 		}
 		else
 		{
 		    	document.forms[0].insurance.value='N';
 		}
 		return;
 	}
 
 
 	function onSrcAgencyChange(combo1)
 	{
 		var combo1_value = combo1.value;
 	}
 	
 	
 	
 	
 	function permanentCheck()
 	{		
 		if (document.forms[0].sameAs.checked==true)
 		{ 		
 			
 			document.forms[0].permaAddFlatNo.value=document.forms[0].flatNo.value;
 			document.forms[0].permaAddFloorNo.value=document.forms[0].floorNo.value;
 			document.forms[0].permaAddBuildingName.value=document.forms[0].builingName.value;
 			document.forms[0].permaAddLocality.value=document.forms[0].locality.value;
 			document.forms[0].permaAddLandMark.value=document.forms[0].landMark.value;
 			document.forms[0].permaAddCity.value=document.forms[0].city.value;
 			document.forms[0].permaAddState.value=document.forms[0].state.value;
 			
 			document.forms[0].permaAddCity_temp.value=document.forms[0].city_temp.value;
 			document.forms[0].permaAddState_temp.value=document.forms[0].state_temp.value;
 			
 			document.forms[0].permaAddZipORPin.value=document.forms[0].zipOrPin.value;
 			document.forms[0].permaAddMobile.value=document.forms[0].mobile.value;
 			document.forms[0].permaAddTelephone.value=document.forms[0].telephone.value;
 			document.forms[0].permaAddEmail.value=document.forms[0].email.value;
 			document.forms[0].permaAddStd.value = document.forms[0].std.value;
 			document.forms[0].permaAddLandLine.value = document.forms[0].landLine.value;
 			document.forms[0].permaNoOfYearAtResidence.value = document.forms[0].noOfYearAtResidence.value;
 			document.forms[0].permaNoOfMonthAtResidence.value = document.forms[0].noOfMonthAtResidence.value;
 			document.forms[0].locationCategory.value = document.forms[0].residenceIs.value;
 			
 			//Disable all the elements of pemanent address in the case of same as
 			
 			document.forms[0].permaAddFlatNo.readOnly=true;
 			
 			document.forms[0].permaAddFloorNo.readOnly=true;
 			document.forms[0].permaAddBuildingName.readOnly=true;
 			document.forms[0].permaAddLocality.readOnly=true;
 			document.forms[0].permaAddLandMark.readOnly=true;
 			document.forms[0].permaAddCity.readOnly=true;
 			document.forms[0].permaAddState.readOnly=true;
 			
 			document.forms[0].btnPermaCity.disabled=true;
 			document.forms[0].btnPermaState.disabled=true;
 			
 			document.forms[0].permaAddZipORPin.readOnly=true;
 			document.forms[0].permaAddMobile.readOnly=true;
 			document.forms[0].permaAddTelephone.readOnly=true;
 			document.forms[0].std.readOnly=true;
 			document.forms[0].landLine.readOnly=true;
 			document.forms[0].permaAddEmail.readOnly=true;
 			document.forms[0].permaNoOfYearAtResidence.readOnly=true;
 			document.forms[0].permaNoOfMonthAtResidence.readOnly=true;
 			document.forms[0].locationCategory.disabled = true;
 			document.forms[0].residenceIs.disabled = true;
 			//Disable all the fields of residence address in case of same as
 			document.forms[0].flatNo.readOnly=true;
 			document.forms[0].floorNo.readOnly=true;
 			document.forms[0].builingName.readOnly=true;
 			document.forms[0].locality.readOnly=true;
 			document.forms[0].landMark.readOnly=true;
 			document.forms[0].city.readOnly=true;
 			document.forms[0].state.readOnly=true;
 			document.forms[0].zipOrPin.readOnly=true;
 			document.forms[0].mobile.readOnly=true;
 			document.forms[0].telephone.readOnly=true;
 			document.forms[0].email.readOnly=true;
 			document.forms[0].permaAddStd.readOnly=true;
 			document.forms[0].permaAddLandLine.readOnly=true;
 			document.forms[0].btnCity.disabled=true;
 			document.forms[0].btnState.disabled=true;
 			document.forms[0].noOfYearAtResidence.readOnly=true;
 			document.forms[0].noOfMonthAtResidence.readOnly=true;
 			
 		}
 		else
 		{ 		
 			document.forms[0].permaAddFlatNo.value="";
 			document.forms[0].permaAddFloorNo.value="";
 			document.forms[0].permaAddBuildingName.value="";
 			document.forms[0].permaAddLocality.value="";
 			document.forms[0].permaAddLandMark.value="";
 			document.forms[0].permaAddCity.value="";
 			document.forms[0].permaAddState.value="";
 			
 			
 			document.forms[0].btnPermaCity.disabled=false;
 			document.forms[0].btnPermaState.disabled=false;
 			document.forms[0].locationCategory.value = "0";
 			document.forms[0].permaAddCity_temp.value="";
 			document.forms[0].permaAddState_temp.value="";
 			
 			document.forms[0].permaAddZipORPin.value="";
 			document.forms[0].permaAddMobile.value="";
 			document.forms[0].permaAddTelephone.value="";
 			document.forms[0].permaAddEmail.value="";
 			document.forms[0].permaAddStd.value="";
 			document.forms[0].permaAddLandLine.value="";
 			document.forms[0].permaNoOfYearAtResidence.value="";
 			document.forms[0].permaNoOfMonthAtResidence.value="";
 			//alert("notchecked");
 			document.forms[0].flatNo.readOnly=false;
 			document.forms[0].floorNo.readOnly=false;
 			document.forms[0].builingName.readOnly=false;
 			document.forms[0].locality.readOnly=false;
 			document.forms[0].landMark.readOnly=false;
 			document.forms[0].city.readOnly=false;
 			document.forms[0].state.readOnly=false;
 			document.forms[0].zipOrPin.readOnly=false;
 			document.forms[0].mobile.readOnly=false;
 			document.forms[0].telephone.readOnly=false;
 			document.forms[0].email.readOnly=false;
 			document.forms[0].std.readOnly=false;
 			document.forms[0].landLine.readOnly=false;
 			document.forms[0].btnCity.disabled=false;
 			document.forms[0].btnState.disabled=false;
 			document.forms[0].noOfYearAtResidence.readOnly=false;
 			document.forms[0].noOfMonthAtResidence.readOnly=false;
 			//Enable all the fields of pemenent address at the time of uncheck the same as checkbox 
 			document.forms[0].locationCategory.disabled = false;
 			document.forms[0].residenceIs.disabled = false;
 			document.forms[0].permaAddFlatNo.readOnly=false;
 			
 			document.forms[0].permaAddFloorNo.readOnly=false;
 			document.forms[0].permaAddBuildingName.readOnly=false;
 			document.forms[0].permaAddLocality.readOnly=false;
 			document.forms[0].permaAddLandMark.readOnly=false;
 			document.forms[0].permaAddCity.readOnly=false;
 			document.forms[0].permaAddState.readOnly=false;
 			document.forms[0].permaAddZipORPin.readOnly=false;
 			document.forms[0].permaAddMobile.readOnly=false;
 			document.forms[0].permaAddTelephone.readOnly=false;
 			document.forms[0].permaAddEmail.readOnly=false;
 			document.forms[0].permaAddStd.readOnly=false;
 			document.forms[0].permaAddLandLine.readOnly=false;
 			document.forms[0].permaNoOfYearAtResidence.readOnly=false;
 			document.forms[0].permaNoOfMonthAtResidence.readOnly=false;
 			
 			if (document.forms[0].sameAsResidenceAddress.checked==true)
 			{
 			 sameAsResiAdd();
 			}
 			
 			if (document.forms[0].sameAsPermaAddress.checked==true)
 			{
 			 document.forms[0].sameAsPermaAddress.checked=false;
 			 sameAsPermaAdd();
 			}
 		}
 	}	
 	
 	function sameAsResiAdd()
 	{
	 	if (document.forms[0].sameAsResidenceAddress.checked==true)
		{
		    
			document.forms[0].sameAsPermaAddress.checked =false;
			document.forms[0].sameAsPermaAddress.disabled =true;
			
			document.forms[0].offAddFlatNo.value=document.forms[0].flatNo.value;
			document.forms[0].offAddFloorNo.value=document.forms[0].floorNo.value;
			document.forms[0].offAddBuildingName.value=document.forms[0].builingName.value;
			document.forms[0].offAddLocality.value=document.forms[0].locality.value;
			document.forms[0].offAddLandMark.value=document.forms[0].landMark.value;
			document.forms[0].offAddCity.value=document.forms[0].city.value;
			document.forms[0].offAddState.value=document.forms[0].state.value;
			
			document.forms[0].offAddCity_temp.value=document.forms[0].city_temp.value;
			document.forms[0].offAddState_temp.value=document.forms[0].state_temp.value;
			
			document.forms[0].offAddZipORPin.value=document.forms[0].zipOrPin.value;
			document.forms[0].offAddMobile.value=document.forms[0].mobile.value;
			document.forms[0].offAddTelephone.value=document.forms[0].telephone.value;
			document.forms[0].offAddEmail.value=document.forms[0].email.value;
			document.forms[0].offAddStd.value = document.forms[0].std.value;
			document.forms[0].offAddLandLine.value = document.forms[0].landLine.value;
			document.forms[0].offNoOfYearAtResidence.value = document.forms[0].noOfYearAtResidence.value;
			document.forms[0].offNoOfMonthAtResidence.value = document.forms[0].noOfMonthAtResidence.value;
			
			document.forms[0].offLocationCategory.value = document.forms[0].residenceIs.value;
			//Disable all the elements of Office address in the case of same as
			
			document.forms[0].offAddFlatNo.readOnly=true;
			
			document.forms[0].offAddFloorNo.readOnly=true;
			document.forms[0].offAddBuildingName.readOnly=true;
			document.forms[0].offAddLocality.readOnly=true;
			document.forms[0].offAddLandMark.readOnly=true;
			document.forms[0].offAddCity.readOnly=true;
			document.forms[0].offAddState.readOnly=true;
			
			document.forms[0].btnOffCity.disabled=true;
			document.forms[0].btnOffState.disabled=true;
			
			document.forms[0].offAddZipORPin.readOnly=true;
			document.forms[0].offAddMobile.readOnly=true;
			document.forms[0].offAddTelephone.readOnly=true;
			document.forms[0].offAddStd.readOnly=true;
			document.forms[0].offAddLandLine.readOnly=true;
			document.forms[0].offAddEmail.readOnly=true;
			document.forms[0].offNoOfYearAtResidence.readOnly=true;
			document.forms[0].offNoOfMonthAtResidence.readOnly=true;
			document.forms[0].offLocationCategory.disabled = true;
			document.forms[0].residenceIs.disabled = true;
			//Disable all the fields of residence address in case of same as
			document.forms[0].flatNo.readOnly=true;
			document.forms[0].floorNo.readOnly=true;
			document.forms[0].builingName.readOnly=true;
			document.forms[0].locality.readOnly=true;
			document.forms[0].landMark.readOnly=true;
			document.forms[0].city.readOnly=true;
			document.forms[0].state.readOnly=true;
			document.forms[0].zipOrPin.readOnly=true;
			document.forms[0].mobile.readOnly=true;
			document.forms[0].telephone.readOnly=true;
			document.forms[0].email.readOnly=true;
			document.forms[0].std.readOnly=true;
			document.forms[0].landLine.readOnly=true;
			document.forms[0].btnCity.disabled=true;
			document.forms[0].btnState.disabled=true;
			document.forms[0].noOfYearAtResidence.readOnly=true;
			document.forms[0].noOfMonthAtResidence.readOnly=true;
			
			if(document.forms[0].sameAs.checked==true)
		    {		    
		     permanentCheck();
		    }
			
		}
 		else
 		{
 		   
 		    document.forms[0].sameAsPermaAddress.disabled =false;
 		    
 			document.forms[0].offAddFlatNo.value="";
 			document.forms[0].offAddFloorNo.value="";
 			document.forms[0].offAddBuildingName.value="";
 			document.forms[0].offAddLocality.value="";
 			document.forms[0].offAddLandMark.value="";
 			document.forms[0].offAddCity.value="";
 			document.forms[0].offAddState.value="";	
 			
 			document.forms[0].btnOffCity.disabled=false;
 			document.forms[0].btnOffState.disabled=false;
 			
 			document.forms[0].offAddCity_temp.value="";
 			document.forms[0].offAddState_temp.value="";
 			
 			document.forms[0].offAddZipORPin.value="";
 			document.forms[0].offAddMobile.value="";
 			document.forms[0].offAddTelephone.value="";
 			document.forms[0].offAddEmail.value="";
 			document.forms[0].offAddStd.value="";
 			document.forms[0].offAddLandLine.value="";
 			document.forms[0].offNoOfYearAtResidence.value="";
 			document.forms[0].offNoOfMonthAtResidence.value="";
 			document.forms[0].offLocationCategory.value = "0";
			
 			//alert("notchecked");
 			document.forms[0].offLocationCategory.disabled = false;
			document.forms[0].residenceIs.disabled = false;
 			document.forms[0].flatNo.readOnly=false;
 			document.forms[0].floorNo.readOnly=false;
 			document.forms[0].builingName.readOnly=false;
 			document.forms[0].locality.readOnly=false;
 			document.forms[0].landMark.readOnly=false;
 			document.forms[0].city.readOnly=false;
 			document.forms[0].state.readOnly=false;
 			document.forms[0].zipOrPin.readOnly=false;
 			document.forms[0].mobile.readOnly=false;
 			document.forms[0].telephone.readOnly=false;
 			document.forms[0].email.readOnly=false;
 			document.forms[0].std.readOnly=false;
 			document.forms[0].landLine.readOnly=false;
 			document.forms[0].btnCity.disabled=false;
 			document.forms[0].btnState.disabled=false;
 			document.forms[0].noOfYearAtResidence.readOnly=false;
 			document.forms[0].noOfMonthAtResidence.readOnly=false;
 			
 			//Enable all the fields of office address at the time of uncheck the same as checkbox 
 			
 			document.forms[0].offAddFlatNo.readOnly=false; 			
 			document.forms[0].offAddFloorNo.readOnly=false;
 			document.forms[0].offAddBuildingName.readOnly=false;
 			document.forms[0].offAddLocality.readOnly=false;
 			document.forms[0].offAddLandMark.readOnly=false;
 			document.forms[0].offAddCity.readOnly=false;
 			document.forms[0].offAddState.readOnly=false;
 			document.forms[0].offAddZipORPin.readOnly=false;
 			document.forms[0].offAddMobile.readOnly=false;
 			document.forms[0].offAddTelephone.readOnly=false;
 			document.forms[0].offAddEmail.readOnly=false;
 			document.forms[0].offAddStd.readOnly=false;
 			document.forms[0].offAddLandLine.readOnly=false;
 			document.forms[0].offNoOfYearAtResidence.readOnly=false;
 			document.forms[0].offNoOfMonthAtResidence.readOnly=false;
 			
 			if(document.forms[0].sameAs.checked==true)
		    {		    
		     permanentCheck();
		    }
 		} 	
 	
 	}	
 	
 	function sameAsPermaAdd()
 	{
 	
		if (document.forms[0].sameAsPermaAddress.checked==true)
		{
		    if(document.forms[0].sameAs.checked==false)
		    {
		     document.forms[0].sameAs.disabled =true;
		    }
			document.forms[0].sameAsResidenceAddress.checked =false;
			document.forms[0].sameAsResidenceAddress.disabled =true;
			
			document.forms[0].offAddFlatNo.value=document.forms[0].permaAddFlatNo.value;
			document.forms[0].offAddFloorNo.value=document.forms[0].permaAddFloorNo.value;
			document.forms[0].offAddBuildingName.value=document.forms[0].permaAddBuildingName.value;
			document.forms[0].offAddLocality.value=document.forms[0].permaAddLocality.value;
			document.forms[0].offAddLandMark.value=document.forms[0].permaAddLandMark.value;
			document.forms[0].offAddCity.value=document.forms[0].permaAddCity.value;
			document.forms[0].offAddState.value=document.forms[0].permaAddState.value;
			
			document.forms[0].offAddCity_temp.value=document.forms[0].permaAddCity_temp.value;
			document.forms[0].offAddState_temp.value=document.forms[0].permaAddState_temp.value;
			
			document.forms[0].offAddZipORPin.value=document.forms[0].permaAddZipORPin.value;
			document.forms[0].offAddMobile.value=document.forms[0].permaAddMobile.value;
			document.forms[0].offAddTelephone.value=document.forms[0].permaAddTelephone.value;
			document.forms[0].offAddEmail.value=document.forms[0].permaAddEmail.value;
			document.forms[0].offAddStd.value = document.forms[0].permaAddStd.value;
			document.forms[0].offAddLandLine.value = document.forms[0].permaAddLandLine.value;
			document.forms[0].offNoOfYearAtResidence.value = document.forms[0].permaNoOfYearAtResidence.value;
			document.forms[0].offNoOfMonthAtResidence.value = document.forms[0].permaNoOfMonthAtResidence.value;
			document.forms[0].offLocationCategory.value = document.forms[0].locationCategory.value;
			
			//Disable all the elements of Office address in the case of same as
			
			document.forms[0].offAddFlatNo.readOnly=true;
			
			document.forms[0].offAddFloorNo.readOnly=true;
			document.forms[0].offAddBuildingName.readOnly=true;
			document.forms[0].offAddLocality.readOnly=true;
			document.forms[0].offAddLandMark.readOnly=true;
			document.forms[0].offAddCity.readOnly=true;
			document.forms[0].offAddState.readOnly=true;
			
			document.forms[0].btnOffCity.disabled=true;
			document.forms[0].btnOffState.disabled=true;
			
			document.forms[0].offAddZipORPin.readOnly=true;
			document.forms[0].offAddMobile.readOnly=true;
			document.forms[0].offAddTelephone.readOnly=true;
			document.forms[0].offAddStd.readOnly=true;
			document.forms[0].offAddLandLine.readOnly=true;
			document.forms[0].offAddEmail.readOnly=true;
			document.forms[0].offNoOfYearAtResidence.readOnly=true;
			document.forms[0].offNoOfMonthAtResidence.readOnly=true;
			document.forms[0].offLocationCategory.disabled =true;
			//Disable all the fields of residence address in case of same as
			document.forms[0].permaAddFlatNo.readOnly=true;
			document.forms[0].permaAddFloorNo.readOnly=true;
			document.forms[0].permaAddBuildingName.readOnly=true;
			document.forms[0].permaAddLocality.readOnly=true;
			document.forms[0].permaAddLandMark.readOnly=true;
			document.forms[0].permaAddCity.readOnly=true;
			document.forms[0].permaAddState.readOnly=true;
			document.forms[0].permaAddZipORPin.readOnly=true;
			document.forms[0].permaAddMobile.readOnly=true;
			document.forms[0].permaAddTelephone.readOnly=true;
			document.forms[0].permaAddEmail.readOnly=true;
			document.forms[0].permaAddStd.readOnly=true;
			document.forms[0].permaAddLandLine.readOnly=true;
			document.forms[0].btnPermaCity.disabled=true;
			document.forms[0].btnPermaState.disabled=true;
			document.forms[0].permaNoOfYearAtResidence.readOnly=true;
			document.forms[0].permaNoOfMonthAtResidence.readOnly=true;
			document.forms[0].locationCategory.disabled=true;
			
			if(document.forms[0].sameAs.checked==true)
		    {		   
		     permanentCheck();
		    }
			
		}
			else
			{
			    
			    document.forms[0].sameAsResidenceAddress.disabled =false;
			    document.forms[0].sameAs.disabled =false;
				document.forms[0].offAddFlatNo.value="";
				document.forms[0].offAddFloorNo.value="";
				document.forms[0].offAddBuildingName.value="";
				document.forms[0].offAddLocality.value="";
				document.forms[0].offAddLandMark.value="";
				document.forms[0].offAddCity.value="";
				document.forms[0].offAddState.value="";	
				
				document.forms[0].btnOffCity.disabled=false;
				document.forms[0].btnOffState.disabled=false;
				
				document.forms[0].offAddCity_temp.value="";
				document.forms[0].offAddState_temp.value="";
				
				document.forms[0].offAddZipORPin.value="";
				document.forms[0].offAddMobile.value="";
				document.forms[0].offAddTelephone.value="";
				document.forms[0].offAddEmail.value="";
				document.forms[0].offAddStd.value="";
				document.forms[0].offAddLandLine.value="";
				document.forms[0].offNoOfYearAtResidence.value="";
				document.forms[0].offNoOfMonthAtResidence.value="";
				document.forms[0].offLocationCategory.value="0";
				//alert("notchecked");
				document.forms[0].permaAddFlatNo.readOnly=false;
				document.forms[0].permaAddFloorNo.readOnly=false;
				document.forms[0].permaAddBuildingName.readOnly=false;
				document.forms[0].permaAddLocality.readOnly=false;
				document.forms[0].permaAddLandMark.readOnly=false;
				document.forms[0].permaAddCity.readOnly=false;
				document.forms[0].permaAddState.readOnly=false;
				document.forms[0].permaAddZipORPin.readOnly=false;
				document.forms[0].permaAddMobile.readOnly=false;
				document.forms[0].permaAddTelephone.readOnly=false;
				document.forms[0].permaAddEmail.readOnly=false;
				document.forms[0].permaAddStd.readOnly=false;
				document.forms[0].permaAddLandLine.readOnly=false;
				document.forms[0].btnPermaCity.disabled=false;
				document.forms[0].btnPermaState.disabled=false;
				document.forms[0].permaNoOfYearAtResidence.readOnly=false;
				document.forms[0].permaNoOfMonthAtResidence.readOnly=false;
				document.forms[0].locationCategory.disabled=false;
				//Enable all the fields of office address at the time of uncheck the same as checkbox 
				
				document.forms[0].offAddFlatNo.readOnly=false; 			
				document.forms[0].offAddFloorNo.readOnly=false;
				document.forms[0].offAddBuildingName.readOnly=false;
				document.forms[0].offAddLocality.readOnly=false;
				document.forms[0].offAddLandMark.readOnly=false;
				document.forms[0].offAddCity.readOnly=false;
				document.forms[0].offAddState.readOnly=false;
				document.forms[0].offAddZipORPin.readOnly=false;
				document.forms[0].offAddMobile.readOnly=false;
				document.forms[0].offAddTelephone.readOnly=false;
				document.forms[0].offAddEmail.readOnly=false;
				document.forms[0].offAddStd.readOnly=false;
				document.forms[0].offAddLandLine.readOnly=false;
				document.forms[0].offNoOfYearAtResidence.readOnly=false;
				document.forms[0].offNoOfMonthAtResidence.readOnly=false;
				document.forms[0].offLocationCategory.disabled=false;				
				
				if(document.forms[0].sameAs.checked==true)
			    {			     
			     permanentCheck();
			    }
			} 	

 	}
 	
 	function clearGroupDivision(disable)
 	{
	  document.forms[0].nameOfGroup.value = "";
      document.forms[0].recruitedByGroup.value = "0";
      //document.forms[0].recruitedByGroupMember.value = "0";
      //document.forms[0].recruitedByMiddleName.value = "";
      //document.forms[0].recruitedByLastName.value = "";
      document.forms[0].trained.value = "";
      document.forms[0].trainedDate.value = "";
      if(disable)
      {
		  document.forms[0].nameOfGroup.disabled=true;
	      //document.forms[0].recruitedByGroup.disabled=true;
	      //document.forms[0].recruitedByGroupMember.disabled = true;
	      //document.forms[0].recruitedByMiddleName.disabled=true;
	      //document.forms[0].recruitedByLastName.disabled=true;
	      document.forms[0].trained.disabled=true;
	      document.forms[0].trainedDate.disabled=true;
      }
 	}
 	
 	function clearIndividualDivision(disable)
 	{
	      document.forms[0].firstName.value="";
	      document.forms[0].middleName.value="";
	      document.forms[0].lastName.value="";
	      document.forms[0].fatherIstName.value="";
	      document.forms[0].fatherIIndName.value="";
	      document.forms[0].fatherLastName.value="";
	      document.forms[0].motherIstName.value="";
	      document.forms[0].maritalStatus.value="0";
	      document.forms[0].dob.value="";
	      document.forms[0].gender.value="0";
	      document.forms[0].customerCategory.value="0";
	      document.forms[0].panNo.value="";
	      document.forms[0].noOfDependent.value="";
	      
	      if(disable)
	      {
		      document.forms[0].firstName.disabled=true;
		      document.forms[0].middleName.disabled=true;
		      document.forms[0].lastName.disabled=true;
		      document.forms[0].fatherIstName.disabled=true;
		      document.forms[0].fatherIIndName.disabled=true;
		      document.forms[0].fatherLastName.disabled=true;
		      document.forms[0].motherIstName.disabled=true;
		      document.forms[0].maritalStatus.disabled=true;
		      document.forms[0].dob.disabled=true;
		      document.forms[0].gender.disabled=true;
		      document.forms[0].customerCategory.disabled=true;
		      document.forms[0].panNo.disabled=true;
		      document.forms[0].noOfDependent.disabled=true;
	      }
 	}
 	
 	function clearCorporateDivision(disable, companyName)
 	{
	      document.forms[0].nameOfCompany.value="";
	      document.forms[0].authSignatoryFirstName.value="";
	      document.forms[0].authSignatoryMiddleName.value="";
	      document.forms[0].authSignatoryLastName.value="";
	      document.forms[0].dateOfIncorporation.value="";
	      document.forms[0].companyPan.value="";
	      if(!companyName)
	      {
	      	document.forms[0].offCompanyName.value ="";
	 	  	document.forms[0].offCompanyName.readOnly =false;
	 	  }
	 	  
	      if(disable)
	      {
		      document.forms[0].nameOfCompany.disabled=true;
		      document.forms[0].authSignatoryFirstName.disabled=true;
		      document.forms[0].authSignatoryMiddleName.disabled=true;
		      document.forms[0].authSignatoryLastName.disabled=true;
		      document.forms[0].dateOfIncorporation.disabled=true;
		      document.forms[0].companyPan.disabled=true;
	      }
 	}
	
	function toggleExistingDetails(existing, radioBox)
	{
		radioBox.checked = true;
		
		if(existing)
		{
			var existingEntityType = document.forms[0].custEntityType.value;
			
			if(existingEntityType == "0" || existingEntityType == "")
			{
				alert("Please select a customer entity type");
				radioBox.checked = false;
				return;
			}
			
			document.getElementById("existingDetailsTD").style.display = "block";
			document.getElementById("existingDetailsTD_Alt").style.display = "none";
		}
		else
		{
			document.forms[0].existingApplicantId.value = "";
			
			
			document.getElementById("existingDetailsTD").style.display = "none";
			document.getElementById("existingDetailsTD_Alt").style.display = "block";
		}
	}
 	
 	function changeCustomerEntityType()
	{
		resetExistingApplicantDetails();
		if(document.forms[0].custEntityType.value=="1000000001" || document.forms[0].custEntityType.value=="1000000004")
	   {
	   	  document.getElementById("divCustEntityTypeInd").style.display="";
	      document.getElementById("divCustEntityTypeCorp").style.display="none";
	      document.getElementById("divCustEntityTypeGroup").style.display="none";
	      if(document.forms[0].custEntityType.value=="1000000001")
	      {
	      	document.getElementById('mfingroupsdiv').style.display="none";
	      }
	      if(document.forms[0].custEntityType.value=="1000000004")
	      {
	      	document.getElementById('mfingroupsdiv').style.display="";
	      }
	      clearGroupDivision(true);
	      clearCorporateDivision(true);
	      
	      //enabling individual
	      document.forms[0].firstName.disabled=false;
	      document.forms[0].middleName.disabled=false;
	      document.forms[0].lastName.disabled=false;
	      document.forms[0].fatherIstName.disabled=false;
	      document.forms[0].fatherIIndName.disabled=false;
	      document.forms[0].fatherLastName.disabled=false;
	      document.forms[0].motherIstName.disabled=false;
	      document.forms[0].maritalStatus.disabled=false;
	      document.forms[0].dob.disabled=false;
	      document.forms[0].gender.disabled=false;
	      document.forms[0].customerCategory.disabled=false;
	      document.forms[0].panNo.disabled=false;
	      document.forms[0].noOfDependent.disabled = false;
	     
	   }
	   else if(document.forms[0].custEntityType.value=="1000000002")
	   { 
	   	  document.getElementById("divCustEntityTypeCorp").style.display="";
	      document.getElementById("divCustEntityTypeInd").style.display="none";
	      document.getElementById("divCustEntityTypeGroup").style.display="none";
	      
	      clearGroupDivision(true);
	      clearIndividualDivision(true);

		  //enabling corporate
	      document.forms[0].nameOfCompany.disabled=false;
	      document.forms[0].authSignatoryFirstName.disabled=false;
	      document.forms[0].authSignatoryMiddleName.disabled=false;
	      document.forms[0].authSignatoryLastName.disabled=false;
	      document.forms[0].dateOfIncorporation.disabled=false;
	      document.forms[0].companyPan.disabled=false;
	      
	   }
	   else if(document.forms[0].custEntityType.value=="1000000003")
	   { 
	      document.getElementById("divCustEntityTypeGroup").style.display="";
	      document.getElementById("divCustEntityTypeInd").style.display="none";
	      document.getElementById("divCustEntityTypeCorp").style.display="none";
	      document.getElementById("trainedDateLable").style.visibility="hidden";
		  document.getElementById("trainedDateVal").style.visibility="hidden";
	      clearCorporateDivision(true);
	      clearIndividualDivision(true);

	      //enabling group
	      document.forms[0].nameOfGroup.disabled=false;
	      document.forms[0].recruitedByGroup.disabled=false;
	      document.forms[0].recruitedByGroupMember.disabled = false;
	      //document.forms[0].recruitedByMiddleName.disabled=false;
	      //document.forms[0].recruitedByLastName.disabled=false;
	      document.forms[0].trained.disabled=false;
	      document.forms[0].trainedDate.disabled=false;
	   }
	   else
	   {
	      document.getElementById("divCustEntityTypeInd").style.display="none";
	      document.getElementById("divCustEntityTypeCorp").style.display="none";
	      document.getElementById("divCustEntityTypeGroup").style.display="none";

	      clearGroupDivision(false);
	      clearCorporateDivision(false);
	      clearIndividualDivision(false);
	   }
	}
	
	function populateOffCompanyName()
	{	
	 document.forms[0].offCompanyName.value =document.forms[0].nameOfCompany.value;
	 document.forms[0].offCompanyName.readOnly =true;
	}
	function salesManagerChooser(tName)
	{
	      var branch = document.forms[0].branch.value;
	      var paramList = tName+"~"+branch;
	      var rowId = 1;
	      var sessionStatus = checkinterval(sessionTimeout);
			if(sessionStatus=="Y")
			{
				 window.open("populateMaster.do?qString="+paramList+"&label=Relationship Manager&dependant=salesManager&rowNum="+rowId,"popCity","width=500, height=490,top=100,left=100, location=no, menubar=no, status=no, toolbar=no, scrollbars=no, resizable=no");
			}
			else
			{
				<%request.setAttribute("sessionExpiry", "Your session has expired!! Please login again.");%>
				document.forms[0].action = "userAuthAction.do?dispatchMethod=logout";
		 		document.forms[0].method = "post";	
		  		document.forms[0].submit();
			}
	      
	}
	
	
	
	function branchManagerChooser(tName)
	{
	      var branch = document.forms[0].branch.value;
	      var paramList = tName+"~"+branch;
	      var rowId = 1;
	      var sessionStatus = checkinterval(sessionTimeout);
		if(sessionStatus=="Y")
		{
			 window.open("populateMaster.do?qString="+paramList+"&label=Branch Manager&dependant=branchManager&rowNum="+rowId,"popCity","width=500, height=490,top=100,left=100, location=no, menubar=no, status=no, toolbar=no, scrollbars=no, resizable=no");
		}
		else
		{
			<%
				request.setAttribute("sessionExpiry", "Your session has expired!! Please login again.");
			%>
			document.forms[0].action = "userAuthAction.do?dispatchMethod=logout";
	 		document.forms[0].method = "post";	
	  		document.forms[0].submit();
		}
		  
	}
	
	function gatFormNumberAvailable(obj)
	{
		var formNumber = obj.value;
		if(formNumber!="")
		{
			var url;
		    url="pmsCheckFormAction.do?formNumber="+formNumber;
		    postRequest(url);
		}
	}

	function updatepage(str)
	{
		
		if(str!="")
		{
        	alert(str);
        	document.forms[0].formNumber.value="";
        	document.forms[0].formNumber.focus();
        	
        }
        
     }
	
	function postRequest(strURL) 
	 {
	     var xmlHttp;
         if (window.XMLHttpRequest) 
         {
             var xmlHttp = new XMLHttpRequest();
         } 
         else if (window.ActiveXObject) 
         {
             var xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
         }
         xmlHttp.open('POST', strURL, true);
         xmlHttp.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
         xmlHttp.onreadystatechange = function() 
                                      {
                                          if (xmlHttp.readyState == 4) 
                                          {
                                          		updatepage(xmlHttp.responseText);
                                          }
                                      }
          xmlHttp.send(strURL);

        }
        
        function resetExistingApplicantDetails()
        {
        	togglePersonalAndAddressBoxes(false);
        	//personal details
        	document.forms[0].authSignatoryFirstName.value = "";
			document.forms[0].authSignatoryLastName.value = "";
			document.forms[0].authSignatoryMiddleName.value = "";
			document.forms[0].companyPan.value = "";
			document.forms[0].customerCategory.value = "0";
			document.forms[0].groupId.value = "0";
			document.forms[0].dateOfIncorporation.value = "";
			document.forms[0].dob.value = "";
			document.forms[0].fatherIIndName.value = "";
			document.forms[0].fatherIstName.value = "";
			document.forms[0].fatherLastName.value = "";
			document.forms[0].firstName.value = "";
			document.forms[0].gender.value = "0";
			document.forms[0].lastName.value = "";
			document.forms[0].maritalStatus.value = "0";
			document.forms[0].middleName.value = "";
			document.forms[0].motherIstName.value = "";
			document.forms[0].nameOfCompany.value = "";
			document.forms[0].nameOfGroup.value = "";
			document.forms[0].codeOfGroup.value = "";
			document.forms[0].noOfDependent.value = "";
			document.forms[0].panNo.value = "";
			document.forms[0].recruitedByGroup.value = "0";
			document.forms[0].recruitedByGroupMember.value="0";
			//document.forms[0].recruitedByLastName.value = "";
			//document.forms[0].recruitedByMiddleName.value = "";
			document.forms[0].trained.value = "";
			document.forms[0].trainedDate.value = "";
			//residence address
			document.forms[0].mobile.value = "";
			document.forms[0].city.value = "";
			document.forms[0].city_temp.value = "";
			document.forms[0].state.value = "";
			document.forms[0].state_temp.value = "";
			document.forms[0].telephone.value = "";
			document.forms[0].builingName.value = "";
			document.forms[0].email.value = "";
			document.forms[0].flatNo.value = "";
			document.forms[0].floorNo.value = "";
			document.forms[0].locality.value = "";
			document.forms[0].zipOrPin.value = "";
			document.forms[0].noOfMonthAtResidence.value = "";
			document.forms[0].noOfYearAtResidence.value = "";
			document.forms[0].residenceIs.value = "0";
			document.forms[0].landMark.value = "";
			//permanent address
			document.forms[0].permaAddMobile.value = "";
			document.forms[0].permaAddCity.value = "";
			document.forms[0].permaAddCity_temp.value = "";
			document.forms[0].permaAddState.value = "";
			document.forms[0].permaAddState_temp.value = "";
			document.forms[0].permaAddTelephone.value = "";
			document.forms[0].permaAddBuildingName.value = "";
			document.forms[0].permaAddEmail.value = "";
			document.forms[0].permaAddFlatNo.value = "";
			document.forms[0].permaAddFloorNo.value = "";
			document.forms[0].permaAddLocality.value = "";
			document.forms[0].permaAddZipORPin.value = "";
			document.forms[0].permaNoOfMonthAtResidence.value = "";
			document.forms[0].permaNoOfYearAtResidence.value = "";
			document.forms[0].locationCategory.value = "0";
			document.forms[0].permaAddLandMark.value = "";
			//office address
			document.forms[0].offAddMobile.value = "";
			document.forms[0].offAddCity.value = "";
			document.forms[0].offAddCity_temp.value = "";
			document.forms[0].offAddState.value = "";
			document.forms[0].offAddState_temp.value = "";
			document.forms[0].offAddTelephone.value = "";
			document.forms[0].offAddBuildingName.value = "";
			document.forms[0].offAddEmail.value = "";
			document.forms[0].offAddFlatNo.value = "";
			document.forms[0].offAddFloorNo.value = "";
			document.forms[0].offAddLocality.value = "";
			document.forms[0].offAddZipORPin.value = "";
			document.forms[0].offNoOfMonthAtResidence.value = "";
			document.forms[0].offNoOfYearAtResidence.value = "";
			document.forms[0].offLocationCategory.value = "0";
			document.forms[0].offAddLandMark.value = "";
			document.forms[0].offCompanyName.value = "";
			document.forms[0].extention1.value = "";
			document.forms[0].fax.value = "";
			
        	document.forms[0].isExisting[0].disabled = false;
			document.forms[0].existingApplicantId.readOnly = false;
			
			
        	toggleExistingDetails(false, document.forms[0].isExisting[0]);
        }
	
		function checkNumericValue(txtValue,displayName)
		{
			var allowed='abcdefghijklmnopqrstuvwxyzABCDEFGHIJJKLMNOPQRSTUVWXYZ0123456789_';
			str = Trim(txtValue);
			var i;
			
			for(i=0;i<str.length;i++)
			{
				if(allowed.indexOf(str.charAt(i)) == -1)
				{
					alert(displayName+" should contain only alpha-numeric value");
					return false;
				} 
			}
			return true; 
		}
		function getExistingApplicantDetails()
		{
		
			var isExisting = document.forms[0].isExisting[1].checked;
			var existingApplicantId = document.forms[0].existingApplicantId.value;			
			var customerEntityId = document.forms[0].custEntityType.value;
			
			if(isExisting)
			{
				if(existingApplicantId == "")
				{
					if(isApplicant)
				   	{
						alert("Please specify the applicant code of the existing customer.");
						return;
					}
					else
					{
						alert("Please specify existing Prospect Code");
						return;					
					}
				}
				
				 if(!checkNumericValue(existingApplicantId,'Existing Applicant Code'))
				   {				   
				     return;
				   }  
			}
			else
			{
				alert("Please select existing applicant option");
				return;
			}
			
			var url="pmsExistingApplicantDetail";
			var params="custEntityId="+customerEntityId+"&applicantId="+existingApplicantId+"&random="+Math.random()+"&isApplicant="+isApplicant;
			
			getExistingApplicantXML(url,params);
		}
		var xmlhttpexistingdetails;
		function getExistingApplicantXML(url,params)
		{
			if (window.XMLHttpRequest)
			{
				xmlhttpexistingdetails = new XMLHttpRequest();
			}
			else
			{
				xmlhttpexistingdetails = new ActiveXObject("Microsoft.XMLHTTP");
			}
			
			xmlhttpexistingdetails.onreadystatechange=processResponseText;
			xmlhttpexistingdetails.open("GET",url+"?"+params,false);
			xmlhttpexistingdetails.setRequestHeader("Content-type","application/x-www-form-urlencoded");
			xmlhttpexistingdetails.send(null);
		}
		
		function processResponseText()
		{
			if(xmlhttpexistingdetails.readyState==4)
		  	{
				var existingApplicantDetails = xmlhttpexistingdetails.responseText;
				fillExistingApplicantDetails(existingApplicantDetails);
			}
		}
		
		function getXMLDOMFromStream(data)
		{
		     data=((((data.replace(/&lt;/g,"<")).replace(/&gt;/g,">")).replace(/&#39;/g,"\"")).replace(/&apos;/g,"'")).replace(/&#47;/g,"/");
	 		  try //Internet Explorer
			  {
			  		  data="<?xml version='1.0' encoding='ISO-8859-1' ?> \n "+data;
			          xmlDoc=new ActiveXObject("Microsoft.XMLDOM");
					  xmlDoc.async="false";
					  xmlDoc.loadXML(data);
					  return xmlDoc;
			  }
			 catch(e)
			  {
					 try //Firefox, Mozilla, Opera, etc.
					 {
					 	    parser=new DOMParser();
							xmlDoc=parser.parseFromString(data,"text/xml");
							return xmlDoc;
					  }
					  catch(e) 
					 {
						  alert(e.message)
					 }
			  }
			return null;
		}

		function fillExistingApplicantDetails(detailXML)
		{
		  
			if(detailXML == "" || detailXML == "NODATA")
			{
			  if(isApplicant)
		        {
				  alert("No record found for the specified Applicant Code");
				  document.forms[0].existingApplicantId.value = "";				
				//resetExistingApplicantDetails();
				  return;
				 } 
				else
				{
				  alert("No record found for the specified Prospect Code");
				  return;
				}
				  return; 
			}	
		
			else if(detailXML.substring(0,5).indexOf("ERR") >=0)
			{
				alert(detailXML);
				document.forms[0].existingApplicantId.value = "";
				resetExistingApplicantDetails();
				return;
			}
			
			//alert(detailXML);
			
			var xmlDom = getXMLDOMFromStream(detailXML);
			
			var personalDetails = xmlDom.getElementsByTagName("APPLICANT_DETAILS/PERSONAL_DETAILS/ROWSET/ROW");
			var resAddress = xmlDom.getElementsByTagName("APPLICANT_DETAILS/RES_ADDRESS_DETAILS/ROWSET/ROW");
			var permAddress = xmlDom.getElementsByTagName("APPLICANT_DETAILS/PERM_ADDRESS_DETAILS/ROWSET/ROW");
			var offAddress = xmlDom.getElementsByTagName("APPLICANT_DETAILS/OFF_ADDRESS_DETAILS/ROWSET/ROW");
			
			var nodeName = "";
			var a = 0;
			var z = 0;
			for(a=0; a < personalDetails.length; a++)
	 		{
				for(z=0; z < personalDetails[a].childNodes.length; z++)
				{
					nodeName = personalDetails[a].childNodes[z].nodeName;

					if("FNAME" == nodeName)
					{
						if(personalDetails[a].childNodes[z].childNodes[0])
						{
							document.forms[0].firstName.value = personalDetails[a].childNodes[z].childNodes[0].nodeValue;
							document.forms[0].nameOfCompany.value = personalDetails[a].childNodes[z].childNodes[0].nodeValue;
							document.forms[0].nameOfGroup.value = personalDetails[a].childNodes[z].childNodes[0].nodeValue;
						}
					}
					else if("MNAME" == nodeName)
					{
						if(personalDetails[a].childNodes[z].childNodes[0])
						{
							document.forms[0].middleName.value  =  personalDetails[a].childNodes[z].childNodes[0].nodeValue;
						}
					}
					else if("LNAME" == nodeName)
					{
						if(personalDetails[a].childNodes[z].childNodes[0])
						{
							document.forms[0].lastName.value  =  personalDetails[a].childNodes[z].childNodes[0].nodeValue;
						}
					}
					else if("FATHERFNAME" == nodeName)
					{
						if(personalDetails[a].childNodes[z].childNodes[0])
						{
							document.forms[0].fatherIstName.value = personalDetails[a].childNodes[z].childNodes[0].nodeValue;
							document.forms[0].authSignatoryFirstName.value = personalDetails[a].childNodes[z].childNodes[0].nodeValue;
						}
					}
					else if("FATHERMNAME" == nodeName)
					{
						if(personalDetails[a].childNodes[z].childNodes[0])
						{
							document.forms[0].fatherIIndName.value = personalDetails[a].childNodes[z].childNodes[0].nodeValue;
							//document.forms[0].recruitedByMiddleName.value  =  personalDetails[a].childNodes[z].childNodes[0].nodeValue;
							document.forms[0].authSignatoryMiddleName.value = personalDetails[a].childNodes[z].childNodes[0].nodeValue;
						}
					}
					else if("FATHERLNAME" == nodeName)
					{
						if(personalDetails[a].childNodes[z].childNodes[0])
						{
							document.forms[0].fatherLastName.value = personalDetails[a].childNodes[z].childNodes[0].nodeValue;
							//document.forms[0].recruitedByLastName.value  =  personalDetails[a].childNodes[z].childNodes[0].nodeValue;
							document.forms[0].authSignatoryLastName.value = personalDetails[a].childNodes[z].childNodes[0].nodeValue;
						}
					}
					else if("DOB" == nodeName)
					{
						if(personalDetails[a].childNodes[z].childNodes[0])
						{
							document.forms[0].dob.value = personalDetails[a].childNodes[z].childNodes[0].nodeValue;
							document.forms[0].trainedDate.value  =  personalDetails[a].childNodes[z].childNodes[0].nodeValue;
							document.forms[0].dateOfIncorporation.value = personalDetails[a].childNodes[z].childNodes[0].nodeValue;
						}
					}
					else if("PANNO" == nodeName)
					{
						if(personalDetails[a].childNodes[z].childNodes[0])
						{
							document.forms[0].panNo.value = personalDetails[a].childNodes[z].childNodes[0].nodeValue;
							document.forms[0].companyPan.value = personalDetails[a].childNodes[z].childNodes[0].nodeValue;
						}
					}
					else if("MOTHERFNAME" == nodeName)
					{
						if(personalDetails[a].childNodes[z].childNodes[0])
						{
							document.forms[0].motherIstName.value = personalDetails[a].childNodes[z].childNodes[0].nodeValue;
						}
					}
					else if("NOOFDEPENDENTS" == nodeName)
					{
						if(personalDetails[a].childNodes[z].childNodes[0])
						{
							document.forms[0].noOfDependent.value = personalDetails[a].childNodes[z].childNodes[0].nodeValue;
						}
					}
					else if("MARTIALSTATUS" == nodeName)
					{
						if(personalDetails[a].childNodes[z].childNodes[0])
						{
							document.forms[0].maritalStatus.value = personalDetails[a].childNodes[z].childNodes[0].nodeValue;
						}
					}
					else if("GENDER" == nodeName)
					{
						if(personalDetails[a].childNodes[z].childNodes[0])
						{
							document.forms[0].gender.value = personalDetails[a].childNodes[z].childNodes[0].nodeValue;
						}
					}
					else if("CUSTCATEGORY" == nodeName)
					{
						if(personalDetails[a].childNodes[z].childNodes[0])
						{
							document.forms[0].customerCategory.value = personalDetails[a].childNodes[z].childNodes[0].nodeValue;
						}
					}
					else if("GROUP_ID" == nodeName)
					{
						if(personalDetails[a].childNodes[z].childNodes[0])
						{
							document.forms[0].groupId.value = personalDetails[a].childNodes[z].childNodes[0].nodeValue;
						}
					}
					else if("APPLICANTCODE" == nodeName)
					{
						if(personalDetails[a].childNodes[z].childNodes[0])
						{
							document.forms[0].codeOfGroup.value = personalDetails[a].childNodes[z].childNodes[0].nodeValue;
							document.forms[0].memberCodeOfGroup.value = personalDetails[a].childNodes[z].childNodes[0].nodeValue;
						}
					}
					else if( "RECRUITEDBY"== nodeName)
					{
							
							if(personalDetails[a].childNodes[z].childNodes[0]!=null)
							{
								document.forms[0].recruitedByGroup.value  =  personalDetails[a].childNodes[z].childNodes[0].nodeValue;
								document.forms[0].recruitedByGroupMember.value  =  personalDetails[a].childNodes[z].childNodes[0].nodeValue;
								document.forms[0].recruitedBy.value=personalDetails[a].childNodes[z].childNodes[0].nodeValue;
							}
							
					}
				}
				
				document.forms[0].isExisting[0].disabled = true;
				document.forms[0].existingApplicantId.readOnly = true;
				
				/*				
				if(document.forms[0].custEntityType.value=="1000000001" || document.forms[0].custEntityType.value=="1000000004")
				{
					clearGroupDivision(true);
		      		clearCorporateDivision(true);
				}
				else if(document.forms[0].custEntityType.value=="1000000002")
				{
					clearGroupDivision(true);
	      			clearIndividualDivision(true);
				}
				else if(document.forms[0].custEntityType.value=="1000000003")
				{
		      		clearCorporateDivision(true);
	      			clearIndividualDivision(true);
				}
				*/
		 	}
		 	
		 	//residence address
		 	if(document.forms[0].trainedDate.value!="")
		 	{
		 		document.forms[0].trained.checked=true;
		 		callTrainingDate()
		 	}
		 	
		 	///populateVariousAddresses(resAddress);
			document.forms[0].mobile.value = existing_mobile;
			document.forms[0].city.value = existing_city;
			document.forms[0].city_temp.value = existing_cityname;
			document.forms[0].state.value = existing_state;
			document.forms[0].state_temp.value = existing_statename;
			document.forms[0].telephone.value = existing_phone1;
			document.forms[0].builingName.value = existing_buildingname;
			document.forms[0].email.value = existing_email;
			document.forms[0].flatNo.value = existing_flatno;
			document.forms[0].floorNo.value = existing_floorno;
			document.forms[0].locality.value = existing_locality;
			document.forms[0].zipOrPin.value = existing_zipcode;
			document.forms[0].noOfMonthAtResidence.value = existing_noofmonths;
			document.forms[0].noOfYearAtResidence.value = existing_noofyears;
			document.forms[0].residenceIs.value = existing_ownership;
			document.forms[0].landMark.value = existing_landmark;
			
			//permanent address			
		 	///populateVariousAddresses(permAddress);
			document.forms[0].permaAddMobile.value = existing_mobile;
			document.forms[0].permaAddCity.value = existing_city;
			document.forms[0].permaAddCity_temp.value = existing_cityname;
			document.forms[0].permaAddState.value = existing_state;
			document.forms[0].permaAddState_temp.value = existing_statename;
			document.forms[0].permaAddTelephone.value = existing_phone1;
			document.forms[0].permaAddBuildingName.value = existing_buildingname;
			document.forms[0].permaAddEmail.value = existing_email;
			document.forms[0].permaAddFlatNo.value = existing_flatno;
			document.forms[0].permaAddFloorNo.value = existing_floorno;
			document.forms[0].permaAddLocality.value = existing_locality;
			document.forms[0].permaAddZipORPin.value = existing_zipcode;
			document.forms[0].permaNoOfMonthAtResidence.value = existing_noofmonths;
			document.forms[0].permaNoOfYearAtResidence.value = existing_noofyears;
			document.forms[0].locationCategory.value = existing_ownership;
			document.forms[0].permaAddLandMark.value = existing_landmark;
			
			//office address
		 	///populateVariousAddresses(offAddress);
			document.forms[0].offAddMobile.value = existing_mobile;
			document.forms[0].offAddCity.value = existing_city;
			document.forms[0].offAddCity_temp.value = existing_cityname;
			document.forms[0].offAddState.value = existing_state;
			document.forms[0].offAddState_temp.value = existing_statename;
			document.forms[0].offAddTelephone.value = existing_phone1;
			document.forms[0].offAddBuildingName.value = existing_buildingname;
			document.forms[0].offAddEmail.value = existing_email;
			document.forms[0].offAddFlatNo.value = existing_flatno;
			document.forms[0].offAddFloorNo.value = existing_floorno;
			document.forms[0].offAddLocality.value = existing_locality;
			document.forms[0].offAddZipORPin.value = existing_zipcode;
			document.forms[0].offNoOfMonthAtResidence.value = existing_noofmonths;
			document.forms[0].offNoOfYearAtResidence.value = existing_noofyears;
			document.forms[0].offLocationCategory.value = existing_ownership;
			document.forms[0].offAddLandMark.value = existing_landmark;
			document.forms[0].offCompanyName.value = existing_company_name;
			document.forms[0].extention1.value = existing_ext1;
			document.forms[0].fax.value = existing_fax;
			
			togglePersonalAndAddressBoxes(true);
		}
		
		function togglePersonalAndAddressBoxes(flag)
		{
			var display = flag?"none":"block";
			
			document.forms[0].firstName.readOnly = flag;
			document.forms[0].nameOfCompany.readOnly = flag;
			document.forms[0].nameOfGroup.readOnly = flag;
			document.forms[0].middleName.readOnly = flag;
			document.forms[0].lastName.readOnly = flag;
			document.forms[0].fatherIstName.readOnly = flag;
			document.forms[0].recruitedByGroup.disabled = flag;
			document.forms[0].recruitedByGroupMember.disabled = flag;
			
			document.forms[0].authSignatoryFirstName.readOnly = flag;
			document.forms[0].fatherIIndName.readOnly = flag;
			//document.forms[0].recruitedByMiddleName.readOnly = flag;
			document.forms[0].authSignatoryMiddleName.readOnly = flag;
			document.forms[0].fatherLastName.readOnly = flag;
			//document.forms[0].recruitedByLastName.readOnly = flag;
			document.forms[0].authSignatoryLastName.readOnly = flag;
			document.forms[0].codeOfGroup.readOnly=flag;
			document.forms[0].trained.disabled=flag;
			document.forms[0].memberCodeOfGroup.readOnly=flag;
			document.getElementById("dob_link").style.display = display;
			document.getElementById("trainedDate_link").style.display = display;
			document.getElementById("dateOfIncorporation_link").style.display = display;
			document.forms[0].panNo.readOnly = flag;
			document.forms[0].companyPan.disabled = flag;
			document.forms[0].motherIstName.readOnly = flag;
			document.forms[0].noOfDependent.readOnly = flag;
			document.forms[0].maritalStatus.disabled = flag;
			document.forms[0].gender.disabled = flag;
			document.forms[0].customerCategory.disabled = flag;
			//document.forms[0].groupId.disabled = flag;
			

			//document.forms[0].rdbtn_mailAdd[0].disabled = flag;
			//document.forms[0].rdbtn_mailAdd[1].disabled = flag;
			//document.forms[0].rdbtn_mailAdd[2].disabled = flag;
			//document.forms[0].sameAs.disabled = flag;
			//document.forms[0].sameAsResidenceAddress.disabled = flag;
			//document.forms[0].sameAsPermaAddress.disabled = flag;
			
			//document.forms[0].mobile.readOnly = flag;
			//document.forms[0].city.readOnly = flag;
			//document.forms[0].btnCity.disabled = flag;
			//document.forms[0].state.readOnly = flag;
			//document.forms[0].btnState.disabled = flag;
			//document.forms[0].telephone.readOnly = flag;
			//document.forms[0].landLine.readOnly = flag;
			///document.forms[0].builingName.readOnly = flag;
			//document.forms[0].email.readOnly = flag;
			//document.forms[0].flatNo.readOnly = flag;
			//document.forms[0].floorNo.readOnly = flag;
			//document.forms[0].locality.readOnly = flag;
			//document.forms[0].zipOrPin.readOnly = flag;
			//document.forms[0].noOfMonthAtResidence.readOnly = flag;
			//document.forms[0].noOfYearAtResidence.readOnly = flag;
			///document.forms[0].residenceIs.disabled = flag;
			///document.forms[0].landMark.readOnly = flag;
			
			///document.forms[0].permaAddMobile.readOnly = flag;
			//document.forms[0].permaAddCity.readOnly = flag;
			//document.forms[0].btnPermaCity.disabled = flag;
			//document.forms[0].permaAddState.readOnly = flag;
			//document.forms[0].btnPermaState.disabled = flag;
			//document.forms[0].permaAddTelephone.readOnly = flag;
			//document.forms[0].permaAddBuildingName.readOnly = flag;
			//document.forms[0].permaAddEmail.readOnly = flag;
			//document.forms[0].permaAddFlatNo.readOnly = flag;
			///document.forms[0].permaAddFloorNo.readOnly = flag;
			//document.forms[0].permaAddLocality.readOnly = flag;
			//document.forms[0].permaAddZipORPin.readOnly = flag;
			//document.forms[0].permaNoOfMonthAtResidence.readOnly = flag;
			//document.forms[0].permaNoOfYearAtResidence.readOnly = flag;
			//document.forms[0].locationCategory.disabled = flag;
			//document.forms[0].permaAddLandMark.readOnly = flag;
			
			///document.forms[0].offAddMobile.readOnly = flag;
			//document.forms[0].offAddCity.readOnly = flag;
			///document.forms[0].btnOffCity.disabled = flag;
			//document.forms[0].offAddState.readOnly = flag;
			//document.forms[0].btnOffState.disabled = flag;
			//document.forms[0].offAddTelephone.readOnly = flag;
			//document.forms[0].offAddBuildingName.readOnly = flag;
			//document.forms[0].offAddEmail.readOnly = flag;
			//document.forms[0].offAddFlatNo.readOnly = flag;
			//document.forms[0].offAddFloorNo.readOnly = flag;
			//document.forms[0].offAddLocality.readOnly = flag;
			//document.forms[0].offAddZipORPin.readOnly = flag;
			//document.forms[0].offNoOfMonthAtResidence.readOnly = flag;
			//document.forms[0].offNoOfYearAtResidence.readOnly = flag;
			//document.forms[0].offLocationCategory.disabled = flag;
			//document.forms[0].offAddLandMark.readOnly = flag;
			//document.forms[0].offCompanyName.readOnly = flag;
			//document.forms[0].extention1.readOnly = flag;
			//document.forms[0].fax.readOnly = flag;
			
			if(document.forms[0].custEntityType.value=="1000000001" || document.forms[0].custEntityType.value=="1000000004")
			{
				clearGroupDivision(true);
	      		clearCorporateDivision(true, true);
			}
			else if(document.forms[0].custEntityType.value=="1000000002")
			{
				clearGroupDivision(true);
      			clearIndividualDivision(true);
			}
			else if(document.forms[0].custEntityType.value=="1000000003")
			{
	      		clearCorporateDivision(true, true);
      			clearIndividualDivision(true);
			}
			else
			{
				clearGroupDivision(false);
				clearCorporateDivision(false);
				clearIndividualDivision(false);
			}
		}
		
		var existing_addressid = "";
		var existing_applicantid = "";
		var existing_addresstype = "";
		var existing_flatno = "";
		var existing_floorno = "";
		var existing_buildingname = "";
		var existing_locality = "";
		var existing_landmark = "";
		var existing_city = "";
		var existing_cityname = "";
		var existing_state = "";
		var existing_statename = "";
		var existing_zipcode = "";
		var existing_mobile = "";
		var existing_phone1 = "";
		var existing_email = "";
		var existing_noofyears = "";
		var existing_noofmonths = "";
		var existing_createdby = "";
		var existing_created_datetime = "";
		var existing_active = "";
		var existing_create_date = "";
		var existing_update_date = "";
		var existing_applicanttype = "";
		var existing_company_name = "";
		var existing_fax = "";
		var existing_ext1 = "";
		var existing_ownership = "0";
		
		function populateVariousAddresses(addressNodeList)
		{
			existing_addressid = "";
			existing_applicantid = "";
			existing_addresstype = "";
			existing_flatno = "";
			existing_floorno = "";
			existing_buildingname = "";
			existing_locality = "";
			existing_landmark = "";
			existing_city = "";
			existing_cityname = "";
			existing_state = "";
			existing_statename = "";
			existing_zipcode = "";
			existing_mobile = "";
			existing_phone1 = "";
			existing_email = "";
			existing_noofyears = "";
			existing_noofmonths = "";
			existing_createdby = "";
			existing_created_datetime = "";
			existing_active = "";
			existing_create_date = "";
			existing_update_date = "";
			existing_applicanttype = "";
			existing_company_name = "";
			existing_fax = "";
			existing_ext1 = "";
			existing_ownership = "0";
			
			var nodeName = "";
			
			for(var a=0; a < addressNodeList.length; a++)
	 		{
				for(var z=0; z < addressNodeList[a].childNodes.length; z++)
				{
					nodeName = addressNodeList[a].childNodes[z].nodeName;

					if("ADDRESSID" == nodeName)
					{
					if(addressNodeList[a].childNodes[z].childNodes[0])
					existing_addressid = addressNodeList[a].childNodes[z].childNodes[0].nodeValue;
					}
					else if("APPLICANTID" == nodeName)
					{
					if(addressNodeList[a].childNodes[z].childNodes[0])
					existing_applicantid = addressNodeList[a].childNodes[z].childNodes[0].nodeValue;
					}
					else if("ADDRESSTYPE" == nodeName)
					{
					if(addressNodeList[a].childNodes[z].childNodes[0])
					existing_addresstype = addressNodeList[a].childNodes[z].childNodes[0].nodeValue;
					}
					else if("FLATNO" == nodeName)
					{
					if(addressNodeList[a].childNodes[z].childNodes[0])
					existing_flatno = addressNodeList[a].childNodes[z].childNodes[0].nodeValue;
					}
					else if("FLOORNO" == nodeName)
					{
					if(addressNodeList[a].childNodes[z].childNodes[0])
					existing_floorno = addressNodeList[a].childNodes[z].childNodes[0].nodeValue;
					}
					else if("BUILDINGNAME" == nodeName)
					{
					if(addressNodeList[a].childNodes[z].childNodes[0])
					existing_buildingname = addressNodeList[a].childNodes[z].childNodes[0].nodeValue;
					}
					else if("LOCALITY" == nodeName)
					{
					if(addressNodeList[a].childNodes[z].childNodes[0])
					existing_locality = addressNodeList[a].childNodes[z].childNodes[0].nodeValue;
					}
					else if("LANDMARK" == nodeName)
					{
					if(addressNodeList[a].childNodes[z].childNodes[0])
					existing_landmark = addressNodeList[a].childNodes[z].childNodes[0].nodeValue;
					}
					else if("CITY" == nodeName)
					{
					if(addressNodeList[a].childNodes[z].childNodes[0])
					existing_city = addressNodeList[a].childNodes[z].childNodes[0].nodeValue;
					}
					else if("CITYNAME" == nodeName)
					{
					if(addressNodeList[a].childNodes[z].childNodes[0])
					existing_cityname = addressNodeList[a].childNodes[z].childNodes[0].nodeValue;
					}
					else if("STATE" == nodeName)
					{
					if(addressNodeList[a].childNodes[z].childNodes[0])
					existing_state = addressNodeList[a].childNodes[z].childNodes[0].nodeValue;
					}
					else if("STATENAME" == nodeName)
					{
					if(addressNodeList[a].childNodes[z].childNodes[0])
					existing_statename = addressNodeList[a].childNodes[z].childNodes[0].nodeValue;
					}
					else if("ZIPCODE" == nodeName)
					{
					if(addressNodeList[a].childNodes[z].childNodes[0])
					existing_zipcode = addressNodeList[a].childNodes[z].childNodes[0].nodeValue;
					}
					else if("MOBILE" == nodeName)
					{
					if(addressNodeList[a].childNodes[z].childNodes[0])
					existing_mobile = addressNodeList[a].childNodes[z].childNodes[0].nodeValue;
					}
					else if("PHONE1" == nodeName)
					{
					if(addressNodeList[a].childNodes[z].childNodes[0])
					existing_phone1 = addressNodeList[a].childNodes[z].childNodes[0].nodeValue;
					}
					else if("EMAIL" == nodeName)
					{
					if(addressNodeList[a].childNodes[z].childNodes[0])
					existing_email = addressNodeList[a].childNodes[z].childNodes[0].nodeValue;
					}
					else if("NOOFYEARS" == nodeName)
					{
					if(addressNodeList[a].childNodes[z].childNodes[0])
					existing_noofyears = addressNodeList[a].childNodes[z].childNodes[0].nodeValue;
					}
					else if("NOOFMONTHS" == nodeName)
					{
					if(addressNodeList[a].childNodes[z].childNodes[0])
					existing_noofmonths = addressNodeList[a].childNodes[z].childNodes[0].nodeValue;
					}
					else if("CREATEDBY" == nodeName)
					{
					if(addressNodeList[a].childNodes[z].childNodes[0])
					existing_createdby = addressNodeList[a].childNodes[z].childNodes[0].nodeValue;
					}
					else if("CREATED_DATETIME" == nodeName)
					{
					if(addressNodeList[a].childNodes[z].childNodes[0])
					existing_created_datetime = addressNodeList[a].childNodes[z].childNodes[0].nodeValue;
					}
					else if("ACTIVE" == nodeName)
					{
					if(addressNodeList[a].childNodes[z].childNodes[0])
					existing_active = addressNodeList[a].childNodes[z].childNodes[0].nodeValue;
					}
					else if("CREATE_DATE" == nodeName)
					{
					if(addressNodeList[a].childNodes[z].childNodes[0])
					existing_create_date = addressNodeList[a].childNodes[z].childNodes[0].nodeValue;
					}
					else if("UPDATE_DATE" == nodeName)
					{
					if(addressNodeList[a].childNodes[z].childNodes[0])
					existing_update_date = addressNodeList[a].childNodes[z].childNodes[0].nodeValue;
					}
					else if("APPLICANTTYPE" == nodeName)
					{
					if(addressNodeList[a].childNodes[z].childNodes[0])
					existing_applicanttype = addressNodeList[a].childNodes[z].childNodes[0].nodeValue;
					}
					else if("COMPANY_NAME" == nodeName)
					{
					if(addressNodeList[a].childNodes[z].childNodes[0])
					existing_company_name = addressNodeList[a].childNodes[z].childNodes[0].nodeValue;
					}
					else if("FAX" == nodeName)
					{
					if(addressNodeList[a].childNodes[z].childNodes[0])
					existing_fax = addressNodeList[a].childNodes[z].childNodes[0].nodeValue;
					}
					else if("EXT1" == nodeName)
					{
					if(addressNodeList[a].childNodes[z].childNodes[0])
					existing_ext1 = addressNodeList[a].childNodes[z].childNodes[0].nodeValue;
					}
					else if("OWNERSHIP" == nodeName)
					{
					if(addressNodeList[a].childNodes[z].childNodes[0])
					existing_ownership = addressNodeList[a].childNodes[z].childNodes[0].nodeValue;
					}
				}
			}
		}
		
		function callTrainingDate()
		{
			if(document.forms[0].trained.checked==true)
			{
				document.getElementById("trainedDateLable").style.visibility="visible";
				document.getElementById("trainedDateVal").style.visibility="visible";
			}
			else
			{
				document.getElementById("trainedDateLable").style.visibility="hidden";
				document.getElementById("trainedDateVal").style.visibility="hidden";
			}
		
		}
		
	
		function fillValuetoRecruitedBy(obj)
		{
			document.forms[0].recruitedBy.value=obj.value;
		}
		
 </script>
 
 


</head>

<body onLoad="javascript:displayPopup();">
	<%@include file="../common/header.jsp" %>
	<%@include file="../common/subHeaderPropect.jsp" %>

	<html:form action="prospectCRUD.do?actionPerformed=newProspectPage" method = "post">
		<html:hidden property="businessDate"/>
		<html:hidden property="recruitedBy"/>
		<html:hidden property="submitType"/>
		
		<input type="hidden" name="dateBusiness" >
		<html:hidden property="hdn_mailAdd" />
			
		<table width="100%">
			<tr height="360px">
				<td width="100%" valign="top">

					<table width="100%">
						<tr>
							<td width="100%">
								<table width="100%">
									<tr>
										<td width="100%">

											<table width="100%" class="main_body">
												<tr>
													<td>
														<fieldset>
															<legend>
																<bean:message bundle="losApplicationResource" key="los.newProspect.frameSet.productDetails"/>
															</legend>
															<table width="100%"  class="main_body">
																<tr>
																	<td width="1%">
																		&nbsp;
																		
																	</td>
																	<td width="12%">
																		<bean:message bundle="losApplicationResource" key="los.newProspect.formNumber"/>
																	</td>
																	<td width="2%">
																		&nbsp;
																	</td>
																	<td width="19%">
																		<html:text property="formNumber" style="text-transform: uppercase;" maxlength="20" onblur="gatFormNumberAvailable(this);"/>
																	</td>
																	<td width="12%">
																		<bean:message bundle="losApplicationResource" key="los.newProspect.product"/>
																	</td>
																	<td width="2%">
																		&nbsp;
																	</td>
																	<td width="19%">
																		<html:hidden property="hid_product"/>
																		<html:select property="product" style="width:130px" onchange="return(clickProduct(this))">
																			<html:option value="0"><bean:message bundle="losApplicationResource" key="los.common.select"/></html:option>
																		</html:select>
																	</td>
																	<td width="12%">
																		<bean:message bundle="losApplicationResource" key="los.newProspect.scheme"/>
																	</td>
																	<td width="2%">
																		&nbsp;
																	</td>
																	<td width="19%">
																		<html:hidden property="hid_scheme"/>
																		<html:select property="scheme"  style="width:130px" onchange="clickScheme(this);">
																			<html:option value="0"><bean:message bundle="losApplicationResource" key="los.common.select"/></html:option>
																		</html:select>
																	</td>
																</tr>
																<tr>
																	<td width="1%">
																		&nbsp;
																	</td>
																	<td width="12%"><bean:message bundle="losApplicationResource" key = "los.prospectEntry.PromotionScheme"/></td>
																	<td width="2%">&nbsp;</td>
																	<td width="19%">
																		<html:hidden property="hid_promotionScheme"/>
																		<html:select property="promotionScheme" style="width:130px">
																			<html:option value="0"><bean:message bundle="losApplicationResource" key = "los.common.select" /></html:option>
																		</html:select>
																	</td>
																	<td width="12%">
																		<bean:message bundle="losApplicationResource" key = "los.prospectEntry.Branch"/>
																	</td>
																	<td width="2%">
																		&nbsp;
																	</td>
																	<td width="19%">
																		<html:select property="branch" style="width:130px" onchange="clickBranch(this);">
																			<html:option value="0"><bean:message bundle="losApplicationResource" key="los.common.select"/></html:option>
																			<logic:notEmpty name="colLocationMaster" >
																			<html:options collection="colLocationMaster" property="masterId" labelProperty="masterName" />
																		    </logic:notEmpty>
																		</html:select>
																	</td>
																	
																	<td width="12%">
																		<bean:message bundle="losApplicationResource" key="los.newProspect.appDate"/>
																	</td>
																	<td width="2%">
																		&nbsp;
																	</td>
																	<td width="19%">
																	<html:text property="applicationDate" readonly="true"></html:text>
																		<a href='#' style="text-decoration: none"
																			onClick="cal19.select(document.forms[0].applicationDate,'application_link','dd-NNN-yyyy'); return false;"
																			NAME="application_link" ID="application_link"
																			title='Calendar'> <img src='images/calendar.gif'
																			alt='Calendar' border='0' />
																		</a>
																	</td>
																</tr>
																<tr id="mfinloancenterrow" style="display:none">
																	<td width="1%">
																		&nbsp;
																	</td>
																	<td width="12%"><bean:message bundle="losApplicationResource" key="los.newProspect.loanCenter"/></td>
																	<td width="2%">&nbsp;</td>
																	<td width="19%">
																		<html:select property="loanCenterId" style="width:130px; background-color:#ebf3ff;">
																			<html:option value="0"><bean:message bundle="losApplicationResource" key = "los.common.select"/></html:option>
																		</html:select>
																	</td>
																	<td width="12%">
																		&nbsp;
																	</td>
																	<td width="2%">
																		&nbsp;
																	</td>
																	<td width="19%">
																		&nbsp;
																	</td>
																	<td width="12%">
																		&nbsp;
																	</td>
																	<td width="2%">
																		&nbsp;
																	</td>
																	<td width="19%">
																		&nbsp;
																	</td>
																</tr>
															</table>
														</fieldset>
													
													</td>
												</tr>
											</table>
											
										</td>
									</tr>
									<tr>
										<td>
											<fieldset>
												<legend>
													<bean:message bundle="losApplicationResource" key="los.newProspect.frameSet.detailsOfLoanRequired"/>
												</legend>
												<table width="100%" class="main_body">
													<tr>
														<td width="1%">
															
														</td>
														<td width="12%">
															<bean:message bundle="losApplicationResource" key="los.newProspect.loanAmount"/>
															
															
														</td>
														<td width="2%">
															&nbsp;
															
														</td>
														<td width="19%">
															<html:text property="loanAmount" maxlength="13" />
															
														</td>
														<td width="12%">
															<bean:message bundle="losApplicationResource" key="los.newProspect.tenor"/>
															
															
														</td>
														<td width="2%">
															&nbsp;
															
														</td>
														<td width="19%">
															<html:text property="tenure" size="2" maxlength="3"/>
															&nbsp;
															
														</td>
														<td width="12%">
															<bean:message bundle="losApplicationResource" key="los.newProspect.purposeOfLoan"/>
															
															
														</td>
														<td width="2%">
															&nbsp;
															
														</td>
														<td width="19%">
															<html:select property="purposeOfLoan"  style="width:130px">
																<html:option value="0"><bean:message bundle="losApplicationResource" key="los.common.select"/></html:option>
																<logic:notEmpty name ="colPurposeOfLoanMaster"> 
																<html:options collection="colPurposeOfLoanMaster" property="masterId" labelProperty="masterName" />
															    </logic:notEmpty>
															</html:select>
															
														</td>
													</tr>
												</table>	
											</fieldset>
										</td>
									</tr>		
									<tr>
										<td>
											<div id="insuranceDiv" style="display: none;">
											    <fieldset>
														<legend>
															Insurance
														</legend>
												         <table width="100%" class="main_body">
													
														    <tr>
																<td width="1%">
																	&nbsp;
																	
																</td>
																<td width="12%">
																	<bean:message bundle="losApplicationResource" key="los.newProspect.insuranceRequired"/>
																	
																</td>
																<td width="2%">
																	&nbsp;
																	
																</td>
																<td width="19%">
																	<html:hidden property="insurance" />
																	<input type="checkbox" name="hasInsurance"
																		onclick="javascript:isInsuranceChecked();" />
																	
																</td>
		
															</tr>
														
												</table>
												</fieldset>	
											 </div>
											
										</td>
									</tr>
									<tr>
										<td colspan="7">
										<div id="divSourcingTypeInd" style=" display:block">
											<table width="100%">
												<tr>
													<td width="48%">
														<fieldset>
															<legend>
																<bean:message bundle="losApplicationResource" key="los.newProspect.frameSet.sourcingChannel"/>
															</legend>
															<table width="100%" class="main_body">
																<tr>
																	<td width="8%">
																		<bean:message bundle="losApplicationResource" key="los.newProspect.channelType"/>
																		
																		
																	</td>
																	<td width="1%">
																		
																	</td>
																	<td width="20%">
																		<html:hidden property="hid_sourcingChannelType"/>
																		<html:select property="sourcingChannelType" style="width:255px" onchange="return(clickSrcAgencyType(this))">
																			<html:option value="0"><bean:message bundle="losApplicationResource" key="los.common.select"/></html:option>
																		</html:select>
																		
																	</td>
																</tr>
																<tr>

																	<td width="8%">
																		<bean:message bundle="losApplicationResource" key="los.newProspect.channelName"/>
																		
																		
																	</td>
																	<td width="1%">
																		
																	</td>
																	<td width="20%">
																		<html:hidden property="hid_sourcingChannelName"/>
																		<html:select property="sourcingChannelName" style="width:255px" onchange="return(clickSrcAgency(this))">
																			<html:option value="0"><bean:message bundle="losApplicationResource" key="los.common.select"/></html:option>
																		</html:select>
																		
																	</td>
																</tr>
																<tr>

																	<td width="8%">
																	<bean:message bundle="losApplicationResource" key="los.newProspect.agentName"/>	
																		
																	</td>
																	<td width="1%">
																		
																	</td>
																	<td width="19%">
																		<html:hidden property="hid_sourcingChannelAgent" />
																		<html:select property="sourcingChannelAgent" style="width:255px">
																			<html:option value="0"><bean:message bundle="losApplicationResource" key="los.common.select"/></html:option>
																		</html:select>																		
																	</td>
																</tr>
															</table>
														</fieldset>
														
													</td>
													<td width="52%">
														<fieldset>
															<legend>
																<bean:message bundle="losApplicationResource" key="los.newProspect.fieldSet.fullfillmentChannel"/>
															</legend>
															<table width="100%" class="main_body">
																<tr>
																	<td width="20%">
																		<bean:message bundle="losApplicationResource" key="los.newProspect.channelType"/>
																		
																		
																	</td>
																	<td width="17%">
																		&nbsp;
																		
																	</td>
																	<td width="63%">
																		<html:hidden property="hid_fulfillmentChannelType"/>
																		<html:select property="fulfillmentChannelType" style="width:260px" onchange="return(clickFflAgencyType(this))">
																			<html:option value="0"><bean:message bundle="losApplicationResource" key="los.common.select"/></html:option>
																		</html:select>
																		
																	</td>
																</tr>
																<tr>
																	<td>
																		<bean:message bundle="losApplicationResource" key="los.newProspect.channelName"/>
																		
																		
																	</td>
																	<td>
																		&nbsp;
																		
																	</td>
																	<td>
																		<html:hidden property="hid_fulfillmentChannelName"/>
																		<html:select property="fulfillmentChannelName" style="width:260px" onchange="return(clickFflAgency(this))">
																			<html:option value="0"><bean:message bundle="losApplicationResource" key="los.common.select"/></html:option>
																		</html:select>
																		
																	</td>
																</tr>
																<tr>
																	<td>
																		<bean:message bundle="losApplicationResource" key="los.newProspect.agentName"/>
																		
																	</td>
																	<td>
																		&nbsp;
																		
																	</td>
																	<td>
																		<html:hidden property="hid_fulfillmentChannelAgent"/>
																		<html:select property="fulfillmentChannelAgent" style="width:260px">
																			<html:option value="0"><bean:message bundle="losApplicationResource" key="los.common.select"/></html:option>
																		</html:select>
																		
																	</td>
																</tr>
															</table>
															
														</fieldset>
														
													</td>
												</tr>
											</table>
											</div>											
										</td>
									</tr>
								</table>
                            </td>
						</tr>
						<tr>
							<td>
							<div id="divManagerType" style=" display:block">
								<table width="100%" class="main_body">
									<tr>
										<td width="100%">
											<fieldset>
												<legend><bean:message bundle="losApplicationResource" key = "los.prospectEntry.ManagerDetails" /></legend>
												<table width="100%" class="main_body" border="0">
													<tr>
														<td width="1%">
														</td>
														<td width="18%" align="left"><bean:message bundle="losApplicationResource" key = "los.prospectEntry.SalesManager" /></td>
														
														<td width="17%" align="left">
															<html:hidden property="salesManager" />
															<html:text property="salesManager_temp" maxlength="15" size="16" readonly="true" />
															<input type="button" class="blueBotton" name="btnSalesManager" value="..." onclick="javascript:salesManagerChooser('QM_SALESMANAGER');">
														</td>
														<td width="19%">
															&nbsp;
														</td>
														<td width="18%">
															&nbsp;
														</td>
														<td width="17%">
															&nbsp;
														</td>
														
														<!-- <td width="18%" align="left">
															&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<bean:message bundle="losApplicationResource" key = "los.prospectEntry.BranchManager" />
														</td>
														<td width="15%" align="left">
															<html:hidden property="branchManager" />
															<html:text property="branchManager_temp" maxlength="15" size="16" readonly="true" />
															<input type="button" name="btnBranchManager" value="..." onclick="javascript:branchManagerChooser('QM_BRANCHMANAGER');">
														</td> -->
													</tr>
												</table>
											</fieldset>
										</td>
									</tr>
									<tr><td>&nbsp;</td></tr>
								</table>
								</div>
							</td>
						</tr>
						<tr>
							<td>
								<fieldset>
									<legend>
										<bean:message bundle="losApplicationResource" key="los.newProspect.fieldSet.personalDetails"/>
									</legend>
									<table class="main_body" width="100%">
								    <tr>
										<td width="1%">
										</td>
										<td width="15%">
										<bean:message bundle="losApplicationResource" key="los.common.custEntityType"/>
										</td>
										<td width="15%">
											<html:select property="custEntityType" style="width:130px" onchange="changeCustomerEntityType();">																									
												<html:option value="0"><bean:message bundle="losApplicationResource" key="los.common.select"/></html:option>
												<html:option value="1000000001">INDIVIDUAL</html:option>
												<html:option value="1000000002">CORPORATE</html:option>
											</html:select>
										</td>
										<td width="1%">
										</td>
										<td width="3%">
											<html:radio property="isExisting" value="N" onclick="toggleExistingDetails(false, this)"/>
										</td>
										<td width="4%">
											<bean:message bundle="losApplicationResource" key="los.newProspect.new"/>
										</td>
										<td width="3%">
											<html:radio property="isExisting" value="Y" onclick="toggleExistingDetails(true, this)"/>
										</td>
										<td width="6%">
											<bean:message bundle="losApplicationResource" key="los.newProspect.existing"/>
										</td>
										<td width="1%">
										</td>
										<td width="50%" id="existingDetailsTD">
											<table class="main_body" width="100%">
												<tr height="22">
													<td width="38%">																						
													 <bean:message bundle="losApplicationResource" key="los.newProspect.applicantCode"/> 
													&nbsp; 
													<html:text property="existingApplicantId" style="width:75px;text-transform:uppercase;" maxlength="10"/>
													</td>
													<td width="1%">
													</td>
													<td width="28%">
														<input type="button" value="GET" class="blueBotton" onclick="getExistingApplicantDetails();"/>
														&nbsp;
														<input type="button" value="RESET" class="blueBotton" onclick="resetExistingApplicantDetails();"/>
													</td>
													<td width="32%" style="display:block;">
														&nbsp;
													</td>
												</tr>
											</table>
										</td>
										<td width="50%" id="existingDetailsTD_Alt">
											<table class="main_body" width="100%">
												<tr height="22">
													<td>
														&nbsp;
													</td>
												</tr>
											</table>
										</td>
										<td width="1%">
										</td>
								    </tr>
								    </table>
									    
									    <table width="100%" class="main_body">	
									    <tr>
									      <td>							    
									    <div id="divCustEntityTypeInd" name="divCustEntityTypeInd">
							            <table width="100%" class="main_body" border="0">
										<tr id="mfingroupsdiv" style="display:none">
											<td width="1%">
												
											</td>
											<td width="12%">
												<bean:message bundle="losApplicationResource" key="los.newProspect.groupName"/>
											</td>
											<td width="2%">
												&nbsp;
											</td>
											<td width="19%">
												<html:select property="groupId" style="width:130px; background-color:#ebf3ff;" >																									
													<html:option value="0"><bean:message bundle="losApplicationResource" key="los.common.select"/></html:option>
													<logic:notEmpty name="colMfinGroupsListMaster">
														<html:options collection="colMfinGroupsListMaster" property="masterId" labelProperty="masterName" />
													</logic:notEmpty>
												</html:select>
											</td>											
											<td width="12%">
												<bean:message bundle="losApplicationResource" key="los.common.groupMemberCode"/>
											</td>
											<td width="2%">
												&nbsp;
											</td>
											<td width="19%">
												<html:text property="codeOfGroup" maxlength="10" style="background-color:#ebf3ff;"/>
											</td>
											<td width="12%">
												<bean:message bundle="losApplicationResource" key="los.common.recruitedBy"/>
											</td>
											<td width="2%">
												&nbsp;
											
											</td>
											<td width="19%">
												<select name="recruitedByGroupMember" style="width:134px; background-color:#ebf3ff;" onchange="fillValuetoRecruitedBy(this)">
												<option value="0"><bean:message bundle="losApplicationResource" key="los.common.select"/></option>
												</select>
											</td>
										</tr>
										<tr>
											<td width="1%">
												
											</td>
											<td width="12%">
											<bean:message bundle="losApplicationResource" key="los.common.firstName"/>
												
												
											</td>
											<td width="2%">
												&nbsp;
												
											</td>
											<td width="19%">
												<html:text property="firstName" maxlength="35" />
												
											</td>
											<td width="12%">
												<bean:message bundle="losApplicationResource" key="los.common.middleName"/>
												
											</td>
											<td width="2%">
												&nbsp;
												
											</td>
											<td width="19%">
												<html:text property="middleName" maxlength="35" />
												
											</td>
											<td width="12%">
												<bean:message bundle="losApplicationResource" key="los.common.lastName"/>
												
												
											</td>
											<td width="2%">
												&nbsp;
											
											</td>
											<td width="19%">
												<html:text property="lastName" maxlength="35" />
											
											</td>
										</tr>
										<tr>
											<td width="1%">
												
											</td>
											<td width="12%">
												<bean:message bundle="losApplicationResource" key="los.newProspect.fatherFirstName"/>
												
												
											</td>
											<td width="2%">
												&nbsp;
											
											</td>
											<td width="19%">
												<html:text property="fatherIstName" maxlength="35" />
												
											</td>
											<td width="12%">
												<bean:message bundle="losApplicationResource" key="los.common.middleName"/>
												
											</td>
											<td width="2%">
												&nbsp;
												
											</td>
											<td width="19%">
												<html:text property="fatherIIndName" maxlength="35" />
												
											</td>
											<td width="12%">
												<bean:message bundle="losApplicationResource" key="los.common.lastName"/>
												
												
											</td>
											<td width="2%">
												&nbsp;
												
											</td>
											<td width="19%">
												<html:text property="fatherLastName" maxlength="35" />
												
											</td>
										</tr>
										<tr>
											<td width="1%">
												
											</td>
											<td width="12%">
												<bean:message bundle="losApplicationResource" key="los.newProspect.motherMaiden"/>
												
												
											</td>
											<td width="2%">
												&nbsp;
												
											</td>
											<td width="19%">
												<html:text property="motherIstName" maxlength="35" />
												
											</td>
											
											
											
											<td width="12%">
												<bean:message bundle="losApplicationResource" key="los.newProspect.maritalStatus"/>
												
												
											</td>
											<td width="2%">
												&nbsp;
												
											</td>
											
											<td width="19%">
												<html:select property="maritalStatus"  style="width:130px">
													<html:option value="0"><bean:message bundle="losApplicationResource" key="los.common.select"/></html:option>
												<logic:notEmpty name ="colMaritalListMaster">
													<html:options collection="colMaritalListMaster" property="masterId" labelProperty="masterName" />
												</logic:notEmpty>
												</html:select>
												
											</td>
											<td width="12%">
											<bean:message bundle="losApplicationResource" key="los.newProspect.dateOfBirth"/>
											</td>
											<td width="2%">
												&nbsp;
											</td>
											<td width="19%">
												<html:text property="dob" maxlength="10" readonly="true"/>
												<a href='#' style="text-decoration: none" 
													onClick="cal19.select(document.forms[0].dob,'dob_link','dd-NNN-yyyy'); return false;"
													NAME="dob_link" ID="dob_link" title='Calendar' style="float:right; margin-top:-22px;margin-right:7px;"> 
													<img src='images/calendar.gif' alt='Calendar' border='0' />
												</a>
											</td>
											
											<%-- 
											<td width="19%">
												<html:text property="motherMiddleName" maxlength="35" />
												
											</td>
											<td width="12%">
												<bean:message bundle="losApplicationResource" key="los.common.lastName"/>
												
												
											</td>
											<td width="2%">
												&nbsp;
												
											</td>
											<td width="19%">
												<html:text property="motherLastName" maxlength="35" />
												
											</td>
											--%>
										</tr>
										<tr>
											<td width="1%">
												
											</td>
											
											
											<td width="12%">
												<bean:message bundle="losApplicationResource" key="los.newProspect.gender"/>
												
												
											</td>
											<td width="2%">
												&nbsp;
												
											</td>
											<td width="19%">
												<html:select property="gender"  style="width:130px">
													<html:option value="0"><bean:message bundle="losApplicationResource" key="los.common.select"/></html:option>
												<logic:notEmpty name ="colGenderListMaster">
													<html:options collection="colGenderListMaster" property="masterId" labelProperty="masterName" />
												</logic:notEmpty>
												</html:select>
												
											</td>
											<td width="12%">
												<bean:message bundle="losApplicationResource" key="los.personalInfo.NoOfDependents"/>
											</td>
											<td width="2%">
												&nbsp;
											</td>
											<td width="19%">
												<html:text property="noOfDependent" maxlength="2"/>
											</td>
											<td width="12%">
												<bean:message bundle="losApplicationResource" key="los.newProspect.customerCategory"/>
												
												
											</td>
											<td width="2%">
												&nbsp;
												
											</td>
											<td width="19%">
												<html:select property="customerCategory" style="width:130px">
													<html:option value="0"><bean:message bundle="losApplicationResource" key="los.common.select"/></html:option>
												<logic:notEmpty name ="colCustCategoryMaster">
													<html:options collection="colCustCategoryMaster" property="masterId" labelProperty="masterName" />
												</logic:notEmpty>
												</html:select>
												
											</td>
											
										</tr>
										<tr>
											
											
											<td width="0%">
												&nbsp;
												
											</td>
											
											
											<td width="12%">
												<bean:message bundle="losApplicationResource" key="los.personalInfo.PanNo"/>
												
											</td>
											<td width="2%">
												&nbsp;
												
											</td>
											<td width="19%">
												<html:text property="panNo" maxlength="10"/>
											</td> 
											
											
											
										</tr>
										
										</table>
										</div>
										
										<div id="divCustEntityTypeCorp">
							            <table width="100%" class="main_body">
										<tr>
											<td width="1%" >
												
											</td>
											<td width="12%">
											<bean:message bundle="losApplicationResource" key="los.newProspect.nameOfCompany"/>
												
												
											</td>
											<td width="2%">
												&nbsp;
												
											</td>
											<td width="19%" colspan="4">
												<html:text property="nameOfCompany" onblur="populateOffCompanyName();" maxlength="199" size="83"/>
												
											</td>
											</tr>
											
										<tr>
											<td width="1%">
												
											</td>
											<td width="12%">
												<bean:message bundle="losApplicationResource" key="los.newProspect.authorizedSignatoryFName"/>
												
											</td>
											<td width="2%">
												&nbsp;
												
											</td>
											<td width="19%">
												<html:text property="authSignatoryFirstName" maxlength="35" />
												
											</td>
											<td width="12%">
												<bean:message bundle="losApplicationResource" key="los.common.middleName"/>
												
												
											</td>
											<td width="2%">
												&nbsp;
											
											</td>
											<td width="19%">
												<html:text property="authSignatoryMiddleName" maxlength="35" />
											
											</td>
											<td width="12%">
												<bean:message bundle="losApplicationResource" key="los.common.lastName"/>
												
												
											</td>
											<td width="2%">
												&nbsp;
											
											</td>
											<td width="19%">
												<html:text property="authSignatoryLastName" maxlength="35" />
												
											</td>
											
										</tr>
										<tr>
										<td width="1%">
												
											</td>
										<td width="12%">
												<bean:message bundle="losApplicationResource" key="los.newProspect.dateOfIncorporation"/>
												
											</td>
											<td width="2%">
												&nbsp;
												
											</td>
											<td width="19%">
											<html:text property="dateOfIncorporation" maxlength="11"  />
												<a href='#' style="text-decoration: none"
													onClick="cal19.select(document.forms[0].dateOfIncorporation,'dateOfIncorporation_link','dd-NNN-yyyy'); return false;"
													NAME="dateOfIncorporation_link" ID="dateOfIncorporation_link" title='Calendar' style="text-decoration: none"> 
													<img src='images/calendar.gif' alt='Calendar' border='0' />
												</a>												
											</td>
											<td width="12%">
												<bean:message bundle="losApplicationResource" key="los.newProspect.companyPAN"/>
												
												
											</td>
											<td width="2%">
												&nbsp;
												
											</td>
											<td width="19%">
												<html:text property="companyPan" maxlength="10" />
												
											</td>
											</tr>
										
										</table>
										</div>
										
										<div id="divCustEntityTypeGroup">
							            <table width="100%" border="0" class="main_body">
										<tr>
											<td width="1%" >&nbsp;</td>
											<td width="12%"><bean:message bundle="losApplicationResource" key="los.common.groupCode"/></td>
											<td width="2%">&nbsp;</td>
											<td width="19%"><html:text property="memberCodeOfGroup" maxlength="10" style="background-color:#ebf3ff;"/></td>
											<td width="12%"><bean:message bundle="losApplicationResource" key="los.common.nameOfGroup"/></td>
											<td width="2%">&nbsp;</td>
											<td width="52%" colspan="4"><html:text property="nameOfGroup" maxlength="200" size="69"/></td>
										</tr>
										
										
										<tr>
												<td width="1%">&nbsp;</td>
												<td width="12%"><bean:message bundle="losApplicationResource" key="los.common.recruitedBy"/></td>
												<td width="2%">&nbsp;</td>
												<td width="19%">
													<select name="recruitedByGroup" style="width:134px; background-color:#ebf3ff;" onchange="fillValuetoRecruitedBy(this)">
														<option value="0"><bean:message bundle="losApplicationResource" key="los.common.select"/></option>
													</select>
												</td>
												<td width="12%"><bean:message bundle="losApplicationResource" key="los.common.trained"/></td>
												<td width="2%">&nbsp;</td>
												<td width="19%"><input type="checkbox" name="trained" onclick="callTrainingDate();"></td>
												<td width="12%" id="trainedDateLable"><bean:message bundle="losApplicationResource" key="los.common.dateOfTraining"/></td>
												<td width="2%">&nbsp;</td>
												<td width="19%" id="trainedDateVal">
												<html:text property="trainedDate" maxlength="10" size="14" readonly="true" /><a href='#' 
														onClick="cal19.select(document.forms[0].trainedDate,'trainedDate_link','dd-NNN-yyyy'); return false;" 
														NAME="trainedDate_link" ID="trainedDate_link" 
														title='Calendar'><img src='images/calendar.gif' alt='Calendar' border='0' /></a>
												</td>
											</tr>
										
										</table>
										</div>

										<tr>
											<td colspan="10">
												<fieldset>
													<legend>
														<bean:message bundle="losApplicationResource" key="los.newProspect.fieldSet.residenceAddress"/><font
															color="red">*</font> 
													</legend>
													<table width="100%" class="main_body">
														<tr><font color="red">Flat/House No or Building Name or Locality should be entered</font></tr>
														<tr>
															<td width="1%">
																
															</td>
															<td width="12%">
																&nbsp;
																
															</td>
															<td width="2%">
																&nbsp;
																
															</td>
															<td width="19%">
																&nbsp;
																
															</td>
															<td width="12%">
																&nbsp;
																
															</td>
															<td width="2%">
																&nbsp;
																
															</td>
															<td width="19%">
																&nbsp;
																
															</td>
															<td width="12%">
																<bean:message bundle="losApplicationResource" key="los.newProspect.mailingAdd"/>
																
															</td>
															<td width="2%">
																&nbsp;
																
															</td>
															<td colspan="7"> <input type="radio" name="rdbtn_mailAdd" value="R" onclick="setMailingAddress();"></td>
														</tr>
														<tr>
															<td width="1%">
																
															</td>
															<td width="12%">
																<bean:message bundle="losApplicationResource" key="los.newProspect.flatHouseNo"/>
																
															</td>
															<td width="2%">
																&nbsp;
																
															</td>
															<td width="19%">
																<html:text property="flatNo" maxlength="50" />
																
															</td>
															<td width="12%">
																<bean:message bundle="losApplicationResource" key="los.newProspect.buildingName"/>
																
															</td>
															<td width="2%">
																&nbsp;
																
															</td>
															<td width="19%">
																<html:text  property="builingName" maxlength="50"/>
																
															</td>
															<td width="12%">
																<bean:message bundle="losApplicationResource" key="los.newProspect.floorNo"/>
																
															</td>
															<td width="2%">
																&nbsp;
																
															</td>
															<td width="19%">
																<html:text property="floorNo" maxlength="50" />
																
															</td>
															
														</tr>
														<tr>
															<td width="1%">
																
															</td>
															<td width="12%">
																<bean:message bundle="losApplicationResource" key="los.newProspect.landmark"/>
																
															</td>
															<td width="2%">
																&nbsp;
																
															</td>
															<td width="19%">
																<html:text property="landMark" maxlength="50" />
																
															</td>
															<td width="12%">
																<bean:message bundle="losApplicationResource" key="los.newProspect.locality"/>
																
															</td>
															<td width="2%">
																&nbsp;
																
															</td>
															<td width="19%">
																<html:text property="locality" maxlength="50" />
																
															</td>
															
															<td width="12%">
																<bean:message bundle="losApplicationResource" key="los.newProspect.state"/>
																
																
															</td>
															<td width="2%">
																&nbsp;
															</td>
															<td width="19%">
																<html:hidden property="state" /> 
													            <html:text property="state_temp"  maxlength="15"  size= "16"   readonly="true" /> 
											                    <input type="button" class="blueBotton" name="btnState"  value="..."   onclick="javascript:stateChooser('QM_STATEMASTER');">
															</td>	
															
														</tr>
														<tr>
															<td width="1%">
																
															</td>
															<td width="12%">
																<bean:message bundle="losApplicationResource" key="los.newProspect.city"/>
																
																
															</td>
															<td width="2%">
																&nbsp;
															</td>
															<td width="19%">
																<html:hidden property="city" /> 
													            <html:text property="city_temp" maxlength="15"  size= "16"   readonly="true" /> 
											                    <input type="button" class="blueBotton" name="btnCity"  value="..."   onclick="javascript:cityChooser('QM_CITYMASTER');">
															</td>
															<td width="12%">
																<bean:message bundle="losApplicationResource" key="los.newProspect.zipPin"/>
																
																
															</td>
															<td width="2%">
																&nbsp;
																
															</td>
															<td width="19%">
																<html:text property="zipOrPin" maxlength="6" />
																
															</td>
															<td width="12%">
																<bean:message bundle="losApplicationResource" key="los.newProspect.mobile"/>
																
																
															</td>
															<td width="2%">
																&nbsp;
																
															</td>
															<td width="19%">
																+91 <html:text property="mobile" maxlength="10" size="15"/>
																
															</td>
														</tr>
														<tr>
															<td width="1%">
																
															</td>
															<td width="12%">
															<bean:message bundle="losApplicationResource" key="los.newProspect.telephone"/>
															
															

																
															</td>
															<td width="2%">
																&nbsp;
															
															</td>
															<td width="19%">
																<html:hidden property="telephone" />
																<input type="text" name="std" size="4" maxlength="5"/>-<input type="text" name="landLine" size="10"  maxlength="8">
																
																
															
															</td>
															<td width="12%">
																<bean:message bundle="losApplicationResource" key="los.newProspect.email"/>
																
															</td>
															<td width="2%">
																&nbsp;
															
															</td>
															<td width="19%">
																<html:text property="email" maxlength="100" />
																
															</td>
															<td width="12%">
																<bean:message bundle="losApplicationResource" key="los.newProspect.residenceStatus"/>
																
																
															</td>
															<td width="2%">
																&nbsp;
																
															</td>
															<td width="19%">
																<html:hidden property="hid_residenceIs"/>
																<html:select property="residenceIs" style="width:130px"
																	onchange="javascript:enableFields();">
																	<html:option value="0"><bean:message bundle="losApplicationResource" key="los.common.select"/></html:option>
																<logic:notEmpty name ="colResidenceListMaster">
																	<html:options collection="colResidenceListMaster" property="masterId" labelProperty="masterName" />
																</logic:notEmpty>
																</html:select>
																
															</td>
														</tr>
														<tr>
															<td width="1%">
																
															</td>
															<td width="12%">
																<bean:message bundle="losApplicationResource" key="los.newProspect.NoOfYearsAtAboveAddress"/>
																
																
															</td>
															<td width="2%">
																&nbsp;
																
															</td>
															<td width="19%">
																<html:text property="noOfYearAtResidence" size="3"
																	maxlength="2" />
																&nbsp;<bean:message bundle="losApplicationResource" key="los.newProspect.years"/>
																<html:text property="noOfMonthAtResidence" size="3"
																	maxlength="2" />
																&nbsp;<bean:message bundle="losApplicationResource" key="los.newProspect.month"/>
																
															</td>

														</tr>
														
													</table>
												</fieldset>
												
											</td>
										</tr>
																			
											<tr>
											<td colspan="10">
											<div id="permanentid" style=" display:block">
											<table width="100%" class="main_body">
											<tr>
											<td width="100%">
												<fieldset>
													<legend>
														<bean:message bundle="losApplicationResource" key="los.newProspect.fieldSet.permanentAddress"/>
													</legend>
													<table width="100%" class="main_body">
														<tr><font color="red">Flat/House No or Building Name or Locality should be entered</font></tr>
														<tr>
															<td width="1%">
																
															</td>
															<td width="12%">
																<bean:message bundle="losApplicationResource" key="los.newProspect.sameAsResidenceAddress"/>
																
															</td>
															<td width="2%">
																&nbsp;
																
															</td>
															<td width="19%">
																<html:checkbox property="sameAs" onclick="permanentCheck()" />
															</td>
															
															
															
															<td width="12%">
																&nbsp;
																
															</td>
															<td width="2%">
																&nbsp;
																
															</td>
															<td width="19%">
																&nbsp;
																
															</td>
															
															<td width="12%">
																<bean:message bundle="losApplicationResource" key="los.newProspect.mailingAdd"/>
																
															</td>
															<td width="2%">
																&nbsp;
																
															</td>
															<td width="19%">
																<input type="radio" name="rdbtn_mailAdd" value="P" onclick="setMailingAddress();">
																
															</td>
														</tr>
														<tr>
															<td width="1%">
																
															</td>
															<td width="12%">
																<bean:message bundle="losApplicationResource" key="los.newProspect.flatHouseNo"/>
															</td>
															<td width="2%">
																&nbsp;
																
															</td>
															<td width="19%">
																<html:text property="permaAddFlatNo" maxlength="50" />
																
															</td>
															<td width="12%">
																<bean:message bundle="losApplicationResource" key="los.newProspect.buildingName"/>
																
															</td>
															<td width="2%">
																&nbsp;
																
															</td>
															<td width="19%">
																<html:text property="permaAddBuildingName"
																	maxlength="50" />
																
															</td>
															<td width="12%">
																<bean:message bundle="losApplicationResource" key="los.newProspect.floorNo"/>
																
															</td>
															<td width="2%">
																&nbsp;
																
															</td>
															<td width="19%">
																<html:text property="permaAddFloorNo" maxlength="50" />
																
															</td>
															
														</tr>
														<tr>
															<td width="1%">
																
															</td>
															<td width="12%">
																<bean:message bundle="losApplicationResource" key="los.newProspect.landmark"/>
																
															</td>
															<td width="2%">
																&nbsp;
																
															</td>
															<td width="19%">
																<input type="text" name="permaAddLandMark"
																	maxlength="50"/ >
																
															</td>
															<td width="12%">
																<bean:message bundle="losApplicationResource" key="los.newProspect.locality"/>
																
															</td>
															<td width="2%">
																&nbsp;
																
															</td>
															<td width="19%">
																<html:text property="permaAddLocality"
																	maxlength="50" />
																
															</td>
															
															<td width="12%">
																<bean:message bundle="losApplicationResource" key="los.newProspect.state"/>
																
															</td>
															<td width="2%">
																&nbsp;
																
															</td>
															<td width="19%">
																<html:hidden property="permaAddState" /> 
													            <html:text property="permaAddState_temp"  maxlength="15"  size= "16"   readonly="true" /> 
											                    <input type="button" class="blueBotton" name="btnPermaState"  value="..."   onclick="javascript:permaStateChooser('QM_STATEMASTER');">
															</td>
															
														</tr>
														<tr>
															<td width="1%">
																
															</td>
															<td width="12%">
																<bean:message bundle="losApplicationResource" key="los.newProspect.city"/>
																
															</td>
															<td width="2%">
																&nbsp;
																
															</td>
															<td width="19%">
																<html:hidden property="permaAddCity" /> 
													            <html:text property="permaAddCity_temp"  maxlength="15"  size= "16"   readonly="true" /> 
											                    <input type="button" class="blueBotton" name="btnPermaCity"  value="..."   onclick="javascript:permaCityChooser('QM_CITYMASTER');">
															</td>
															<td width="12%">
																<bean:message bundle="losApplicationResource" key="los.newProspect.zipPin"/>

																
															</td>
															<td width="2%">
																&nbsp;
																
															</td>
															<td width="19%">
																<html:text property="permaAddZipORPin"
																	maxlength="6" />
																
															</td>
															<td width="12%">
																 <bean:message bundle="losApplicationResource" key="los.newProspect.mobile"/>
																
															</td>
															<td width="2%">
																&nbsp;
																
															</td>
															<td width="19%">
																+91 <html:text property="permaAddMobile" maxlength="10" size="15" />
																
															</td>
														</tr>
														<tr>
															<td width="1%">
																
															</td>
															<td width="12%">
																<bean:message bundle="losApplicationResource" key="los.newProspect.telephone"/>
															</td>
															<td width="2%">&nbsp;
																
																
															</td>
															<td width="19%">
																<html:hidden property="permaAddTelephone"/>
																<input type="text" name="permaAddStd" size="4" maxlength="5"/>-<input type="text" name="permaAddLandLine" size="10" maxlength="8">
																
															</td>
															<td width="12%">
																<bean:message bundle="losApplicationResource" key="los.newProspect.email"/>
																
															</td>
															<td width="2%">&nbsp;
																
																
															</td>
															<td width="19%">
																<html:text property="permaAddEmail" maxlength="100" />
																
															</td>
															<td width="12%">
																<bean:message bundle="losApplicationResource" key="los.newProspect.residenceStatus"/>
																
															</td>
															<td width="2%">&nbsp;
																
																
															</td>
															<td width="19%">
																<html:hidden property="hid_locationCategory"/>
																<html:select property="locationCategory" style="width:130px">
																	<html:option value="0">
																		<bean:message bundle="losApplicationResource" key="los.common.select"/>
																	</html:option>
																	<logic:notEmpty name ="colResidenceListMaster">
																		<html:options collection="colResidenceListMaster" property="masterId" labelProperty="masterName" />
																	</logic:notEmpty>
																</html:select>
																
															</td>
														</tr>
														<tr>
															<td width="1%">
																
															</td>
															<td width="12%">
																<bean:message bundle="losApplicationResource" key="los.newProspect.NoOfYearsAtAboveAddress"/>
																
																
															</td>
															<td width="2%">
																&nbsp;
																
															</td>
															<td width="19%">
																<html:text property="permaNoOfYearAtResidence" size="3"
																	maxlength="2" />
																&nbsp;<bean:message bundle="losApplicationResource" key="los.newProspect.years"/>
																<html:text property="permaNoOfMonthAtResidence" size="3"
																	maxlength="2" />
																&nbsp;<bean:message bundle="losApplicationResource" key="los.newProspect.month"/>
																
															</td>

														</tr>
														
													</table>
												</fieldset>
												</td>
												</tr>
												</table>
												</div>
												</td>
												</tr>
												
											
																	
												
										
										<tr>										
											<td colspan="10">
											<div id="officeid" style=" display:block">
											<table width="100%" class="main_body">
											<tr>
											 <td width="100%">											  
												<fieldset>
													<legend>
														<bean:message bundle="losApplicationResource" key="los.newProspect.fieldSet.OfficeAddress"/>
													</legend>
													<table width="100%" class="main_body">
														<tr><font color="red">Flat/House No or Building Name or Locality should be entered</font></tr>
														
														<tr>
															<td width="1%">
																
															</td>
															 <td width="12%">
																<bean:message bundle="losApplicationResource" key="los.newProspect.sameAsResidenceAddress"/>
																
															</td>
															<td width="2%">
																&nbsp;
																
															</td>
															<td width="19%">
																<html:checkbox property="sameAsResidenceAddress" onclick="sameAsResiAdd()" />
																
															</td>
															
															<td width="12%">
																<bean:message bundle="losApplicationResource" key="los.newProspect.sameAsPermanetAddress"/>
															</td>
															<td width="2%">
																&nbsp;
															</td>
															<td width="19%">
																<html:checkbox property="sameAsPermaAddress" onclick="sameAsPermaAdd()" />
															</td>
															<td width="12%">
																<bean:message bundle="losApplicationResource" key="los.newProspect.mailingAdd"/>
																
															</td>
															<td width="2%">
																&nbsp;
																
															</td>
															<td width="19%">
																<input type="radio" name="rdbtn_mailAdd" value="O" onclick="setMailingAddress();">
																
															</td>
															
														</tr>
														<tr>
															<td width="1%" >
																
															</td>
															<td width="12%">
															<bean:message bundle="losApplicationResource" key="los.newProspect.nameOfCompany"/>
																
																
															</td>
															<td width="2%">
																&nbsp;
																
															</td>
															<td width="19%" colspan="4">
																<html:text property="offCompanyName" maxlength="200" size="83"/>
																
															</td>
														</tr>
														

														<tr>
															<td width="1%">
																
															</td>
															
															<td width="12%">
																<bean:message bundle="losApplicationResource" key="los.newProspect.flatHouseNo"/>
															</td>
															<td width="2%">
																&nbsp;
																
															</td>
															<td width="19%">
																<html:text property="offAddFlatNo" maxlength="50" />
																
															</td>
															<td width="12%">
																<bean:message bundle="losApplicationResource" key="los.newProspect.buildingName"/>
																
															</td>
															<td width="2%">
																&nbsp;
																
															</td>
															<td width="19%">
																<html:text property="offAddBuildingName" maxlength="50" />
																
															</td>
															<td width="12%">
																<bean:message bundle="losApplicationResource" key="los.newProspect.floorNo"/>
																
															</td>
															<td width="2%">
																&nbsp;
																
															</td>
															<td width="19%">
																<html:text property="offAddFloorNo" maxlength="50" />
																
															</td>
															
															
															
														</tr>
														<tr>
															<td width="1%">
																
															</td>
															
															<td width="12%">
																<bean:message bundle="losApplicationResource" key="los.newProspect.landmark"/>
																
															</td>
															<td width="2%">
																&nbsp;
																
															</td>
															<td width="19%">
																<input type="text" name="offAddLandMark"
																	maxlength="50"/>
																
															</td>
															<td width="12%">
																<bean:message bundle="losApplicationResource" key="los.newProspect.locality"/>
																
															</td>
															<td width="2%">
																&nbsp;
																
															</td>
															<td width="19%">
																<html:text property="offAddLocality"
																	maxlength="50" />
																
															</td>
															
															<td width="12%">
																<bean:message bundle="losApplicationResource" key="los.newProspect.state"/>
																
															</td>
															<td width="2%">
																&nbsp;
																
															</td>
															<td width="19%">
																<html:hidden property="offAddState" /> 
													            <html:text property="offAddState_temp"  maxlength="15"  size= "16"   readonly="true" /> 
											                    <input type="button" class="blueBotton" name="btnOffState"  value="..."   onclick="javascript:offStateChooser('QM_STATEMASTER');">
															</td>
															
															
															
														</tr>
														<tr>
															<td width="1%">
																
															</td>
															
															<td width="12%">
																<bean:message bundle="losApplicationResource" key="los.newProspect.city"/>
																
															</td>
															<td width="2%">
																&nbsp;
																
															</td>
															<td width="19%">
																<html:hidden property="offAddCity" /> 
													            <html:text property="offAddCity_temp"  maxlength="15"  size= "16"   readonly="true" /> 
											                    <input type="button" class="blueBotton" name="btnOffCity"  value="..."   onclick="javascript:offCityChooser('QM_CITYMASTER');">
															</td>
															<td width="12%">
																<bean:message bundle="losApplicationResource" key="los.newProspect.zipPin"/>

																
															</td>
															<td width="2%">
																&nbsp;
																
															</td>
															<td width="19%">
																<html:text property="offAddZipORPin"
																	maxlength="6" />
																
															</td>
															
															<td width="12%">
																 <bean:message bundle="losApplicationResource" key="los.newProspect.mobile"/>
																
															</td>
															<td width="2%">
																&nbsp;
																
															</td>
															<td width="19%">
																+91 <html:text property="offAddMobile" maxlength="10" size="15" />
																
															</td>
															
														</tr>
														<tr>
															<td width="1%">
																
															</td>
															
															
															<td width="12%">
																<bean:message bundle="losApplicationResource" key="los.newProspect.telephone"/>
															</td>
															<td width="2%">&nbsp;
																
																
															</td>
															<td width="19%">
																<html:hidden property="offAddTelephone"/>
																<input type="text" name="offAddStd" size="4" maxlength="5"/>-<input type="text" name="offAddLandLine" size="10" maxlength="8">
																
															</td>
															<td width="12%"><bean:message bundle="losApplicationResource" key="los.addressInfo.ext1"/></td>
																<td width="2%">&nbsp;</td>
																<td width="19%"><html:text property="extention1" maxlength="4"/></td>
																<td width="12%">
																<bean:message bundle="losApplicationResource" key="los.newProspect.email"/>
																
															</td>
															<td width="2%">&nbsp;
																
																
															</td>
															<td width="19%">
																<html:text property="offAddEmail" maxlength="100" />
																
															</td>
															
																												
															
														</tr>
														<tr>
														<td width="1%">
																
														</td>
															<td width="12%"><bean:message bundle="losApplicationResource" key="los.addressInfo.fax"/></td>
															<td width="2%">&nbsp;</td>
															<td width="19%"><html:text property="fax" maxlength="15"/></td>
															<td width="12%">
																<bean:message bundle="losApplicationResource" key="los.newProspect.residenceStatus"/>																
														</td>
															<td width="2%">&nbsp;															
															</td>
															<td width="19%">
																<html:hidden property="hid_offLocationCategory"/>
																<html:select property="offLocationCategory" style="width:130px">
																	<html:option value="0">
																		<bean:message bundle="losApplicationResource" key="los.common.select"/>
																	</html:option>
																	<logic:notEmpty name ="colResidenceListMaster">
																	<html:options collection="colResidenceListMaster" property="masterId" labelProperty="masterName" />
																</logic:notEmpty>
																</html:select>
																
															</td>
															<td width="12%">
																<bean:message bundle="losApplicationResource" key="los.newProspect.NoOfYearsAtAboveAddress"/>
															</td>
															<td width="2%">
																&nbsp;																
															</td>
															
															<td width="19%">
																<html:text property="offNoOfYearAtResidence" size="3"
																	maxlength="2" />
																&nbsp;<bean:message bundle="losApplicationResource" key="los.newProspect.years"/>
																<html:text property="offNoOfMonthAtResidence" size="3"
																	maxlength="2" />
																&nbsp;<bean:message bundle="losApplicationResource" key="los.newProspect.month"/>																
															</td>								
														</tr>
													</table>
												</fieldset>
												</td>
												</tr>
												</table></div>      
							</td>
						</tr>
					</table>
					</fieldset>
				</td>
			</tr>
		</table>
		
	</html:form>
	<jsp:include page="../common/footer.jsp" />
</body>
</html:html>
