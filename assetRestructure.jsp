<!--
		Version				Date				Updated By				Reason
		1.0.0.1				21-Mar-2020			Sunny Pathak			effective date to be remain same as it is on default loaded page
        1.0.0.2             24-Mar-2020         Ravi Kumar              code changes for ad new master security deposit
        1.0.0.3  	    	09-Apr-2020	 		Ravinder Kaur           Disable cancel button when restructure Type is asset Extension for OPS user.
        1.0.0.4  	    	28-Apr-2020	 		Ravinder Kaur           Disable effective date field when rsd flag is checked.
  		1.0.0.5				17-May-2020			Sunny Pathak			Lease billing state new disabled permanently
  		1.0.0.6				22-Jun-2020			Sunny Pathak			CR - Scheme change on asset restructuring
  		1.0.0.7				11-Mar-2024			Hemant Kumar			Bug 130541 - Getting issue at the time of Restructuring: Principal outstanding or balance tenor is not correct
 		1.0.0.8				24-Jan-2025			Nihkil Gupta			143033 Advance Search button is not appearing on Asset screen
 		1.0.0.9             27-Apr-2026         Nalin Kumar Jena      Tabs to be added in restructuring module
 		1.0.0.10            15-Oct-2026         Sanchi Agarwal      Conditional mandatory field validation (Req 3.1, 3.2) & matrix logic for Documents Required (Req 3.3)
  -->

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"    pageEncoding="ISO-8859-1" isELIgnored="false" %>
<%@ include file="../include/includeTld.inc"%> 
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sform"%> 
  <head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
	<title>miFIN</title>	
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
	<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
	<META HTTP-EQUIV="Expires" CONTENT="0">
	<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<link href="css/newlos.css" rel="stylesheet" type="text/css">
	<link href="css/bootstrap.css" rel="stylesheet" type="text/css">
	<link href="css/font-awesome.css" rel="stylesheet">
	<link href="css/responsive.css" rel="stylesheet">
	<script type="text/javascript" src="js/validation.js"></script>
	 <script type="text/javascript" src="js/jquery.js"></script>
	<script type="text/javascript" src="js/checkSessionAvailability.js"></script>
	<script type="text/javascript" src="js/genericValidationlos.js"></script>
	<script type="text/javascript" src="js/CalendarPopup.js"></script>
	<script type="text/javascript" src="js/dateValidation.js"></script>
	<script type="text/javascript" src="js/PopupWindow.js"></script>
	<script type="text/javascript" src="js/date.js"></script>
	<script type="text/javascript" src="js/AnchorPosition.js"></script>
	<script type="text/javascript" src="js/angular.min.js"></script>
	<script type="text/javascript" src="js/json2.js"></script>
	<script type="text/javascript" src="js/angular-filter.min.js"></script>

	<script type="text/javascript" src="js/controller/assetRestructure_Controller.js"></script>
	
	<script type="text/javascript">document.write(getCalendarStyles());</script>
	<script type="text/javascript" ID="js19">
	var cal19 = new CalendarPopup();
	cal19.showYearNavigation();
	cal19.showYearNavigationInput();
	var mastersListStr='${mastersListGson}';
	//var dmHistoryDtlObj='${dmHistoryDtlObj}';
	var businessDate='<bean:write name="llmSessionUserInformationDTO" property="businessDate"/>';
//	var assetRestructureDtlsObj=${assetRestructureDtls};
	var screenId='${screenId}';
	function numberWithCommas(x) {
    var parts = x.toString().split(".");
    parts[0] = parts[0].replace(/\B(?=(\d{3})+(?!\d))/g, ",");
    return parts.join(".");
	}
</script>
  </head>
  <%@include file="../common/header.jsp"%>
<%-- <%@include file="./quotationConstSaveHeader.jsp"%> --%>
<div class="container-fluid subheaderSection">
	<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
		<div class="col-lg-4 col-md-4 col-sm-4 col-xs-6" id="screenName">
			<%-- <logic:notEmpty name="screenModes">
					  	<bean:write name ="screenModes" property="screenName"/>
					 </logic:notEmpty>
					 	<logic:empty name="screenModes" > --%>
			<b><%=request.getParameter("screenName") %></b>
			<%-- </logic:empty> --%>
		
		</div>
		<div class="col-lg-8 col-md-8 col-sm-8 col-xs-6 text-right secndDivSubhdr">
				<a href="llmDashboard.do?actionPerformed=getDashboard">
				<bean:message bundle="llmHomeApplicationResource" key = "llm.subHeader.mydashboard"/></a>
			             
				<a ng-href="#" ng-click="save('S')" id="saveID" ng-show="DISABLE_ALL_FIELDS_FLG==true?false:true">
				<bean:message bundle="losApplicationResource" key = "los.common.save"/></a>  
		</div>
	</div>
</div>
<body class="menuHavingBody"  ng-app="assetRestructureApp" ng-controller="assetRestructureCtrl" id="assetRestructureAppId">
	 <div   id="vetiTd" >
		<%@ include file="../vmenu/menu.jsp" %>
	</div>
	<div  class="toggling">
		<%@ include file="../vmenu/vmenuTgl.jsp" %>
	</div>
    
    <sform:form method="get" action="save">
      <input type="hidden"  name="applicationDate" value='<bean:write name="llmSessionUserInformationDTO" property="businessDate"/>'>
     <input type="hidden" ng-model="user.RESTRUCTURE_ID" id="RESTRUCTURE_ID">
      <div class="container-fluid">
      <!-- Restructuring Details section start -->
	    <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12" ng-show="DISABLE_ALL_FIELDS_FLG" >
		       <div class="col-lg-4 col-md-4 col-sm-6 col-xs-12" >
		       <b> ACTIVITY IN PROGRESS.</b>
		       </div>
		       
		       <div class="col-lg-2 col-md-2 col-sm-3 col-xs-6" >
						 <bean:message bundle="quotationApplicationResources" key="qt.asset.newDm"/>
					</div>
		       <div class="col-lg-2 col-md-2 col-sm-3 col-xs-6" >
						<b>{{user.NEW_DM}}</b>
				</div>
				<div class="col-lg-2 col-md-2 col-sm-3 col-xs-6" >
					 <bean:message bundle="quotationApplicationResources" key="qt.asset.newQuotation"/>
				</div>
				<div class="col-lg-2 col-md-2 col-sm-3 col-xs-6" >
					<b>{{user.NEW_QUOTATION}}</b>
				</div> 
		</div>
				  
		       <div class="legend col-lg-12 col-md-12 col-sm-12 col-xs-12" >
		        <bean:message bundle="quotationApplicationResources" key="qt.asset.restructDtls"/>	
		       </div>
		   		<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
		   			<div class="col-lg-2 col-md-2 col-sm-3 col-xs-6"><bean:message bundle="quotationApplicationResources" key="quotation.chassis.dmCode"/></div>
					<div class="col-lg-2 col-md-2 col-sm-3 col-xs-6">
						<input type="hidden" id="DM_CODE" ng-model="user.DM_CODE" /> 
						<input type="text" id="DM_CODE_TEMP" ng-model="user.DM_CODE_TEMP" ng-attr-title="{{DM_CODE_TEMP}}" style="background-color:#ebf3ff;"   size= "16"   readonly="true" /> 
						<input type="button" class="btn btn-primary btn-sm" name="btnDmCode"  value="..."   ng-click="dmCodeChooser('DM LOV');">
					</div>	
					<!-- <input type="button"   style="float:right" class="btn btn-primary btn-sm" name="dmViewer" value="DM VIEWER" ng-click="showDetail(user.DM_CODE,user.DM_CODE_TEMP)" ng-disabled="user.DM_CODE==''?true:false"> -->
					</div>
				<fieldset ng-disabled="DISABLE_ALL_FIELDS_FLG">
					<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
						<div class="col-lg-2 col-md-2 col-sm-3 col-xs-6" ><bean:message bundle="quotationApplicationResources" key="qt.asset.restructType"/></div>
						<div class="col-lg-2 col-md-2 col-sm-3 col-xs-6" >
							<!-- <select class = 'text_header_cost' id="RESTRUCTURE_TYPE"   ng-model="user.RESTRUCTURE_TYPE" ng-change="setTooltip();setDefaultValues('');autoSelectDocumentsRequired();" ng-disabled="user.DM_CODE==''?true:false" style="background-color:#ebf3ff;"  > --> <!-- 1.0.0.9 Commented -->
							<select class = 'text_header_cost' id="RESTRUCTURE_TYPE"   ng-model="user.RESTRUCTURE_TYPE" ng-change="setTooltip();setDefaultValues('');autoSelectDocumentsRequired(); agreementSigningTypeSelectOnExtensionPeriod()" ng-disabled="user.DM_CODE==''?true:false" style="background-color:#ebf3ff;"  > <!-- 1.0.0.9 -->
							  <option value="0"><bean:message bundle="quotationApplicationResources" key="quotation.common.select"/></option>
							  <option  ng-repeat="value in RESTRUCTURE_TYPE" value="{{value.ID}}">{{value.VALUE}}</option>
							</select>
						</div>	
						<div ng-if="(user.RESTRUCTURE_TYPE==='0' || user.RESTRUCTURE_TYPE==='1000000003')" class="col-lg-2 col-md-2 col-sm-3 col-xs-6" ><bean:message bundle="quotationApplicationResources" key="qt.asset.effectiveDt"/></div><!-- 1.0.0.1 -->
						<%-- <div ng-if="(user.RESTRUCTURE_TYPE==='1000000003')" class="col-lg-2 col-md-2 col-sm-3 col-xs-6" ><bean:message bundle="quotationApplicationResources" key="qt.asset.effectiveExtDt"/></div> --%><!-- commented by 1.0.0.1 -->
						<div ng-if="(user.RESTRUCTURE_TYPE==='1000000001' || user.RESTRUCTURE_TYPE==='1000000002')" class="col-lg-2 col-md-2 col-sm-3 col-xs-6" ><bean:message bundle="quotationApplicationResources" key="qt.asset.effectiveTransDt"/></div>
						<div class="col-lg-2 col-md-2 col-sm-3 col-xs-6">
							  <input maxlength="11" ng-model="user.EFF_DATE" type="text" ng-blur="getPosDetail(user.EFF_DATE,'EFFECTIVE TRANSFER DATE');" ng-change="getPosDetail(user.EFF_DATE,'EFFECTIVE TRANSFER DATE');" style="background-color:#ebf3ff;"  ng-disabled="(user.DM_CODE=='' || user.RESTRUCTURE_TYPE==='1000000003' || user.USER_TYPE == 'OPS') ?true:false"
								id="EFF_DATE" maxlength="15" style="width:130px"  /> <!--1.0.0.4 changed for RSD_CHECKED_FLG  -->
								<a href='#' style="text-decoration: none" onClick="cal19.select(document.getElementById('EFF_DATE'),'EFF_DATE_Link~assetRestructureAppId~EFF_DATE~user~','dd-NNN-yyyy'); callPosDtlFun(); return false; "
									NAME="EFF_DATE_Link" ID="EFF_DATE_Link" title='Calendar'  >
									<i class="fa fa-calendar" aria-hidden="true" ></i>
								</a> 
						</div> 
					
						<div class="col-lg-2 col-md-2 col-sm-3 col-xs-6" ng-show=" user.RESTRUCTURE_TYPE==='1000000001'"><bean:message bundle="quotationApplicationResources" key="qt.asset.restructRsn"/></div>
						<div class="col-lg-2 col-md-2 col-sm-3 col-xs-6" ng-show=" user.RESTRUCTURE_TYPE==='1000000001'">
							<select class = 'text_header_cost' id="RESTRUCTURE_REASON"   ng-model="user.RESTRUCTURE_REASON" ng-options="value.ID as value.NAME for value in RESTRUCTURE_REASON" style="background-color:#ebf3ff;"  >
							
							</select>
						</div>
					</div>
					
					<!-- New Marketing Restructuring Fields - Start 1.0.0.9 -->
					<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12" ng-show="(user.RESTRUCTURE_TYPE==='1000000001' || user.RESTRUCTURE_TYPE==='1000000002' || user.RESTRUCTURE_TYPE==='1000000003') && user.USER_TYPE=='MKT'">

						<div class="col-lg-2 col-md-2 col-sm-3 col-xs-6"><bean:message bundle="quotationApplicationResources" key="qt.asset.reqRcvdFromCust"/></div>
						<div class="col-lg-2 col-md-2 col-sm-3 col-xs-6">
							<input maxlength="11" ng-model="user.REQ_RECEIVED_FROM_CUST" type="text" style="background-color:#ebf3ff;" id="REQ_RECEIVED_FROM_CUST" maxlength="15" style="width:130px" ng-disabled="user.RSD_FLG"/>
							<a href='#' style="text-decoration: none" onClick="cal19.select(document.getElementById('REQ_RECEIVED_FROM_CUST'),'REQ_RECEIVED_FROM_CUST_Link~assetRestructureAppId~REQ_RECEIVED_FROM_CUST~user~','dd-NNN-yyyy'); return false;"
								NAME="REQ_RECEIVED_FROM_CUST_Link" ID="REQ_RECEIVED_FROM_CUST_Link" title='Calendar' ng-disabled="user.RSD_FLG || user.USER_TYPE=='OPS'">
								<i class="fa fa-calendar" aria-hidden="true"></i>
							</a>
						</div>

						<div class="col-lg-2 col-md-2 col-sm-3 col-xs-6"><bean:message bundle="quotationApplicationResources" key="qt.asset.reqRcvdFromBC"/></div>
						<div class="col-lg-2 col-md-2 col-sm-3 col-xs-6">
							<input maxlength="11" ng-model="user.REQ_RECEIVED_FROM_BC" type="text" style="background-color:#ebf3ff;" id="REQ_RECEIVED_FROM_BC" maxlength="15" style="width:130px" ng-disabled="user.RSD_FLG"/>
							<a href='#' style="text-decoration: none" onClick="cal19.select(document.getElementById('REQ_RECEIVED_FROM_BC'),'REQ_RECEIVED_FROM_BC_Link~assetRestructureAppId~REQ_RECEIVED_FROM_BC~user~','dd-NNN-yyyy'); return false;"
								NAME="REQ_RECEIVED_FROM_BC_Link" ID="REQ_RECEIVED_FROM_BC_Link" title='Calendar' ng-disabled="user.RSD_FLG || user.USER_TYPE=='OPS'">
								<i class="fa fa-calendar" aria-hidden="true"></i>
							</a>
						</div>
						
						<div class="col-lg-2 col-md-2 col-sm-3 col-xs-6"><bean:message bundle="quotationApplicationResources" key="qt.asset.extensionPeriod"/></div>
						<div class="col-lg-2 col-md-2 col-sm-3 col-xs-6">
							<select class = 'text_header_cost' id="EXTENSION_PERIOD" ng-model="user.EXTENSION_PERIOD" ng-change="autoSelectDocumentsRequired(); agreementSigningTypeSelectOnExtensionPeriod()" ng-options="value.ID as value.NAME for value in EXTENSION_PERIOD_MASTER" style="background-color:#ebf3ff;" ng-disabled="user.RESTRUCTURE_TYPE!='1000000003'">
								<option value="0"><bean:message bundle="quotationApplicationResources" key="quotation.common.select"/></option>
							</select>
						</div>

						<div class="col-lg-2 col-md-2 col-sm-3 col-xs-6"><bean:message bundle="quotationApplicationResources" key="qt.asset.agreementSigningType"/></div>
						<div class="col-lg-2 col-md-2 col-sm-3 col-xs-6">
							<select class = 'text_header_cost' id="AGREEMENT_SIGNING_TYPE" ng-model="user.AGREEMENT_SIGNING_TYPE" ng-change="autoSelectDocumentsRequired()" ng-options="value.ID as value.NAME for value in AGREEMENT_SIGNING_TYPE_MASTER" style="background-color:#ebf3ff;" ng-disabled="user.RSD_FLG">
								<option value="0"><bean:message bundle="quotationApplicationResources" key="quotation.common.select"/></option>
							</select>
						</div>
						
						<div class="col-lg-2 col-md-2 col-sm-3 col-xs-6"><bean:message bundle="quotationApplicationResources" key="qt.asset.documentsRequired"/></div>
						<div class="col-lg-2 col-md-2 col-sm-3 col-xs-6">
							<!-- 1.0.0.10 -->
							<select class = 'text_header_cost' id="DOCUMENTS_REQUIRED" ng-model="user.DOCUMENTS_REQUIRED" ng-options="value.ID as value.NAME for value in DOCUMENTS_REQUIRED_MASTER" style="background-color:#ebf3ff;" ng-disabled="disableDocumentsRequired || user.RSD_FLG">
							</select>
						</div>

				   </div>
				<!-- 1.0.0.9 End -->

					<br><br><br>
					<!-- restructure category flags start -->
					<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
						<div class="col-lg-2 col-md-2 col-sm-3 col-xs-6" ng-show="user.RESTRUCTURE_TYPE==='1000000002'" ><bean:message bundle="quotationApplicationResources" key="qt.asset.rsdChangeFlg" />&nbsp;&nbsp;
						  <input type="checkbox" ng-model="user.RSD_FLG" ng-checked="user.RSD_FLG_VAL=='Y'?true:false" ng-click="enableDisableFields();" ng-disabled="!DISB_RSD_FLAG">
						</div>	
						<div class="col-lg-2 col-md-2 col-sm-3 col-xs-6" ng-show="(user.RESTRUCTURE_TYPE==='1000000002' && user.AGREEMENT_TYPE== '1000000003')?true:false"><bean:message bundle="quotationApplicationResources" key="qt.asset.autoTransferFlg" />&nbsp;&nbsp;
						  <input type="checkbox" ng-model="user.AUTO_TRANSFER_FLG" ng-checked="user.AUTO_TRANSFER_FLG_VAL=='Y'?true:false" ng-click="enableDisableFields()" ng-disabled="DISB_RSD_FLAG || RSD_CHECKED_FLG">
						</div>	
						
						<div class="col-lg-2 col-md-2 col-sm-3 col-xs-6" ng-show="user.RESTRUCTURE_TYPE==='1000000001'"><bean:message bundle="quotationApplicationResources" key="qt.asset.sezChangeFlg" />&nbsp;&nbsp;
						  <input type="checkbox" ng-model="user.SEZ_CHANGE_FLG" ng-checked="user.SEZ_CHANGE_FLG_VAL=='Y'?true:false" ng-click="enableDisableFields()" ng-disabled="DISB_RSD_FLAG || RSD_CHECKED_FLG">
						</div>
						<div class="col-lg-2 col-md-2 col-sm-3 col-xs-6" ng-show="user.RESTRUCTURE_TYPE==='1000000001'"><bean:message bundle="quotationApplicationResources" key="qt.asset.rentalDtlsChangeFlg" />&nbsp;&nbsp;
						  <input type="checkbox" ng-model="user.RENTAL_DTLS_CHANGE_FLG" ng-checked="user.RENTAL_DTLS_CHANGE_FLG_VAL=='Y'?true:false" ng-click="enableDisableFields()" ng-disabled="DISB_RSD_FLAG || RSD_CHECKED_FLG">
						</div>
						
						<div class="col-lg-2 col-md-2 col-sm-3 col-xs-6" ng-show="(user.RESTRUCTURE_TYPE==='1000000002' || user.RESTRUCTURE_TYPE==='1000000001')"><bean:message bundle="quotationApplicationResources" key="qt.asset.lesseChngFlg" />&nbsp;&nbsp;
						  <input type="checkbox" ng-model="user.LESSE_CHANGE_FLG" ng-checked="user.LESSE_CHANGE_FLG_VAL=='Y'?true:false" ng-change="enableDisableFields()" ng-disabled="DISB_RSD_FLAG || RSD_CHECKED_FLG">
						</div>	
						<div class="col-lg-2 col-md-2 col-sm-3 col-xs-6" ng-show="user.RESTRUCTURE_TYPE==='1000000002'"><bean:message bundle="quotationApplicationResources" key="qt.asset.placeOfSuppChngFlg" />&nbsp;&nbsp;
						  <input type="checkbox" ng-model="user.PLACE_OF_SUPP_FLG" ng-checked="user.PLACE_OF_SUPP_FLG_VAL=='Y'?true:false" ng-change="enableDisableFields()" ng-disabled="DISB_RSD_FLAG || RSD_CHECKED_FLG">
						</div>	
						<div class="col-lg-2 col-md-2 col-sm-3 col-xs-6" ng-show="user.RESTRUCTURE_TYPE==='1000000002'" ><bean:message bundle="quotationApplicationResources" key="qt.asset.agreementTypeFlg" />&nbsp;&nbsp;
						  <input type="checkbox" ng-model="user.AGREEMENT_TYPE_FLG" ng-checked="user.AGREEMENT_TYPE_FLG_VAL=='Y'?true:false" ng-change="enableDisableFields()" ng-disabled="DISB_RSD_FLAG || RSD_CHECKED_FLG">
						</div>	
						<div class="col-lg-2 col-md-2 col-sm-3 col-xs-6" ng-show="false"><bean:message bundle="quotationApplicationResources" key="qt.asset.secDepositFlg" />&nbsp;&nbsp;
						  <input type="checkbox" ng-model="user.SEC_DEPOSIT_FLG" ng-checked="user.SEC_DEPOSIT_FLG_VAL=='Y'?true:false" ng-change="enableDisableFields()">
						</div>
						<div class="col-lg-2 col-md-2 col-sm-3 col-xs-6" ng-show="(user.RESTRUCTURE_TYPE==='1000000002' && (user.AGREEMENT_TYPE== '1000000001'|| user.AGREEMENT_TYPE == '1000000003'))?true:false"><bean:message bundle="quotationApplicationResources" key="qt.asset.userChangeFlg" />&nbsp;&nbsp;
						  <input type="checkbox" ng-model="user.USER_CHANGE_FLG" ng-checked="user.USER_CHANGE_FLG_VAL=='Y'?true:false" ng-change="enableDisableFields()" ng-disabled="DISB_RSD_FLAG || RSD_CHECKED_FLG">
						</div>	
						<div class="col-lg-2 col-md-2 col-sm-3 col-xs-6" ng-show="user.RESTRUCTURE_TYPE==='1000000002'"><bean:message bundle="quotationApplicationResources" key="qt.asset.OtherFlag" />&nbsp;&nbsp;
						  <input type="checkbox" ng-model="user.OTHER_FLAG" ng-checked="user.OTHER_FLAG_VAL=='Y'?true:false" ng-change="enableDisableFields()" ng-disabled="DISB_RSD_FLAG || RSD_CHECKED_FLG">
						</div>
						<!-- 1.0.0.6 start -->
						<div class="col-lg-2 col-md-2 col-sm-3 col-xs-6" ng-show="user.RESTRUCTURE_TYPE==='1000000001'"><bean:message bundle="quotationApplicationResources" key="qt.asset.schemeChngFlg" />&nbsp;&nbsp;
						  <input type="checkbox" ng-model="user.CHANGE_IN_SCHEME" ng-checked="user.CHANGE_IN_SCHEME_FLG=='Y'?true:false" ng-change="enableDisableFields()" ng-disabled="DISB_RSD_FLAG || RSD_CHECKED_FLG">
						</div>
						<!-- 1.0.0.6 end -->
					</div>
			         <!-- restructure category flags end -->
					    	
 		 <div class=" col-lg-12 col-md-12 col-sm-12 col-xs-12" ng-show="user.RESTRUCTURE_TYPE==='1000000001' || user.RESTRUCTURE_TYPE==='1000000002' || user.RESTRUCTURE_TYPE==='1000000003'"> 
				  
		   		<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12" >
					<div class="col-lg-2 col-md-2 col-sm-3 col-xs-6" ><bean:message bundle="quotationApplicationResources" key="qt.asset.rv"/></div>
					<div class="col-lg-2 col-md-2 col-sm-3 col-xs-6" >
						<input type="text" readonly="true" ng-model="user.RV" >
					</div><!-- 1.0.0.2 start -->
					<div class="col-lg-2 col-md-2 col-sm-3 col-xs-6" ng-show="user.RESTRUCTURE_TYPE==='1000000002' || user.RESTRUCTURE_TYPE==='1000000001'"><bean:message bundle="quotationApplicationResources" key="qt.asset.balTenor"/></div>
					<div class="col-lg-2 col-md-2 col-sm-3 col-xs-6" ng-show="user.RESTRUCTURE_TYPE==='1000000002' || user.RESTRUCTURE_TYPE==='1000000001'">
						<input type="text" readonly="true" ng-model="user.BAL_TENOR" >
					</div><!-- 1.0.0.2 end -->
					<div class="col-lg-2 col-md-2 col-sm-3 col-xs-6" ><bean:message bundle="quotationApplicationResources" key="qt.asset.princOutstanding"/></div>
					<div class="col-lg-2 col-md-2 col-sm-3 col-xs-6"  >
						<input type="text" readonly="true" ng-model="user.PRINCIPAL_OUTSTANDING">
					</div>
					<!-- 1.0.0.2 start -->
						<div class="col-lg-2 col-md-2 col-sm-3 col-xs-6" ng-show="user.RESTRUCTURE_TYPE==='1000000003' || user.RESTRUCTURE_TYPE==='1000000001'"><bean:message bundle="quotationApplicationResources" key="qt.asset.SecurityDepositAmt"/></div>
						<div class="col-lg-2 col-md-2 col-sm-3 col-xs-6" ng-show="user.RESTRUCTURE_TYPE==='1000000003' || user.RESTRUCTURE_TYPE==='1000000001'">
							<input type="text" id="SECURITY_DEPOSIT_AMT" ng-model="user.SECURITY_DEPOSIT" readonly="true" /> 
						</div>	
						<div class="col-lg-2 col-md-2 col-sm-3 col-xs-6" ng-show="user.RESTRUCTURE_TYPE==='1000000003' || user.RESTRUCTURE_TYPE==='1000000001'"><bean:message bundle="quotationApplicationResources" key="qt.asset.SecurityDeposit"/></div>
						<div class="col-lg-2 col-md-2 col-sm-3 col-xs-6" ng-show="user.RESTRUCTURE_TYPE==='1000000003' || user.RESTRUCTURE_TYPE==='1000000001'">
							<select id="SECURITY_DEPOSIT" ng-model="user.SD_OPTIONS" style="background-color:#ebf3ff;"  ng-options="value.ID as value.NAME for value in SECURITY_DEPOSIT_MASTER" ng-disabled="securityDepositFlag"> 
							</select>
						</div>		 <!-- 1.0.0.2 end  -->
				</div>
         </div>
  <!-- Restructuring Details section End -->
  <!--DM History section start  -->
  <div class="legend TglDivParent legendFloat col-lg-12 col-md-12 col-sm-12 col-xs-12">
	<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12"><bean:message bundle="quotationApplicationResources" key="qt.asset.dmHistory" /></div>
	<div class="tglDIv tglDivSlide" style="top: 31%;z-index: 10;"><i class="fa fa-plus"></i></div>
	<div class="col-lg-8 col-md-8 col-sm-8 col-xs-8 main_body text-right">
	&nbsp;
	</div>
	</div>
  <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 tglTable" >
	<div width="100%" style ="overflow:auto;border-color:white">
		<table style="width: 100% !important;overflow:auto;">
			<tr valign="top" class="list_header">
									
				<th width="7%" style="text-align:center;"><bean:message bundle="quotationApplicationResources" key="qt.asset.hist.sno" /></th>   
				<th width="10%" ><bean:message bundle="quotationApplicationResources" key="qt.asset.hist.quotationCode" /></th>
				<th width="10%" ><bean:message bundle="quotationApplicationResources" key="qt.asset.hist.dmCode" /></th>
				<th width="10%" ><bean:message bundle="quotationApplicationResources" key="qt.asset.hist.capDate" /></th>
				<th width="7%" ><bean:message bundle="quotationApplicationResources" key="qt.asset.hist.tenure" /></th>

				<th width="12%" ><bean:message bundle="quotationApplicationResources" key="qt.asset.hist.restructDate" /></th>
				<th width="15%" ><bean:message bundle="quotationApplicationResources" key="qt.asset.hist.restructType" /></th>
				<th width="15%" ><bean:message bundle="quotationApplicationResources" key="qt.asset.hist.restructCategory" /></th>
				
			</tr>
			<tr ng-repeat="dmHistory in dmHistoryDtlObj">
				<td width="7%" style="text-align:center;">{{dmHistory.SR_NO}}</td> 
				<td width="10%" ><a ng-href="#" ng-click="showDetail(dmHistory.QUOTATION_ID,dmHistory.QUOTATION_CODE,'QUOTATION','1200003020')">{{ dmHistory.QUOTATION_CODE }} </a></td>
				<td width="10%" ><a ng-href="#" ng-click="showDetail(dmHistory.DM_ID,dmHistory.DM_CODE,'DM','1200004012')">{{ dmHistory.DM_CODE }} </a></td>
				<td width="10%" >{{dmHistory.CAP_DATE}}</td> 
				<td width="7%" >{{dmHistory.TENURE}}</td> 
			
				<td width="12%" >{{dmHistory.RESTRUCTURE_DATE}}</td> 
				<td width="15%" >{{dmHistory.RESTRUCTURE_TYPE}}</td> 
				<td width="15%" >{{dmHistory.RESTRUCTURE_CATEGORY}}</td> 
				
		
			</tr>
		</table>
		</div>
   </div>
<!--Dm History section end  -->
  <!-- old values start -->
   <div class=" col-lg-12 col-md-12 col-sm-12 col-xs-12"> 
		       <div class="legend col-lg-12 col-md-12 col-sm-12 col-xs-12" ng-show="user.RESTRUCTURE_TYPE==='1000000002' || user.RESTRUCTURE_TYPE==='1000000001'"><bean:message bundle="quotationApplicationResources" key="qt.asset.oldValues"/></div>
		   		<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
		   		  
					<div class="col-lg-2 col-md-2 col-sm-3 col-xs-6" ng-show="user.RESTRUCTURE_TYPE==='1000000002'"><bean:message bundle="quotationApplicationResources" key="qt.asset.rsd"/></div>
					<div class="col-lg-2 col-md-2 col-sm-3 col-xs-6" ng-show="user.RESTRUCTURE_TYPE==='1000000002'" >
						<input type="text" readonly="true" ng-model="user.RSD">
					</div>
					 <div class="col-lg-2 col-md-2 col-sm-3 col-xs-6" ng-show="user.RESTRUCTURE_TYPE==='1000000002' || user.RESTRUCTURE_TYPE==='1000000001'"><bean:message bundle="quotationApplicationResources" key="qt.asset.lessee"/></div>
					<div class="col-lg-2 col-md-2 col-sm-3 col-xs-6" ng-show="user.RESTRUCTURE_TYPE==='1000000002' || user.RESTRUCTURE_TYPE==='1000000001'">
						<input type="text" readonly="true" ng-model="user.LESSEE">
					</div>
					<div class="col-lg-2 col-md-2 col-sm-3 col-xs-6" ng-show="user.RESTRUCTURE_TYPE==='1000000002'"><bean:message bundle="quotationApplicationResources" key = "quotation.dmApplicant.limitCode" /></div>
					<div class="col-lg-2 col-md-2 col-sm-3 col-xs-6"  ng-show="user.RESTRUCTURE_TYPE==='1000000002'">
						<input type="text" id="LIMIT_CODE_TEMP" ng-model="user.LIMIT_CODE_TEMP" readonly="true"/>
				    </div>
					<div class="col-lg-2 col-md-2 col-sm-3 col-xs-6" ng-show="user.RESTRUCTURE_TYPE==='1000000002'"><bean:message bundle="quotationApplicationResources" key="qt.asset.agreementType"/></div>
					<div class="col-lg-2 col-md-2 col-sm-3 col-xs-6" ng-show="user.RESTRUCTURE_TYPE==='1000000002'">
						<select id="AGREEMENT_TYPE" ng-model="user.AGREEMENT_TYPE" ng-disabled="true" ng-options="value.ID as value.NAME for value in AGREEMENT_TYPE" style="background-color:#ebf3ff;"> 
						</select>
					</div>

					<div class="col-lg-2 col-md-2 col-sm-3 col-xs-6" ng-show="user.RESTRUCTURE_TYPE==='1000000002'"><bean:message bundle="quotationApplicationResources" key="qt.asset.repaymentMode"/></div>
					<div class="col-lg-2 col-md-2 col-sm-3 col-xs-6" ng-show="user.RESTRUCTURE_TYPE==='1000000002'">
						<select id="REPAYMENT_MODE" ng-model="user.REPAYMENT_MODE" ng-options="value.ID as value.NAME for value in LEASE_REPAYMENT_MODE" ng-disabled="true" style="background-color:#ebf3ff;"> 
						</select>
					</div>
					<div class="col-lg-2 col-md-2 col-sm-3 col-xs-6" ng-show="user.RESTRUCTURE_TYPE==='1000000001'"><bean:message bundle="quotationApplicationResources" key="quotation.list.sezZone"/></div>
					<div class="col-lg-2 col-md-2 col-sm-3 col-xs-6" ng-show="user.RESTRUCTURE_TYPE==='1000000001'">
						<input type="text" readonly="true" ng-model="user.SEZ_ZONE">
					</div>
					<div class="col-lg-2 col-md-2 col-sm-3 col-xs-6" ng-show="user.RESTRUCTURE_TYPE==='1000000002' || user.RESTRUCTURE_TYPE==='1000000001'"><bean:message bundle="quotationApplicationResources" key="qt.asset.plOfSupplState"/></div>
					<div class="col-lg-2 col-md-2 col-sm-3 col-xs-6" ng-show="user.RESTRUCTURE_TYPE==='1000000002' || user.RESTRUCTURE_TYPE==='1000000001'">
						<select id="PLACE_OF_SUPP_STATE" ng-model="user.PLACE_OF_SUPP_STATE" style="background-color:#ebf3ff;" ng-options="value.ID as value.VALUE for value in PLACE_OF_SUPPLY_STATE" ng-disabled="true" > 
						</select>
					</div>

					<div class="col-lg-2 col-md-2 col-sm-3 col-xs-6" ng-show="user.RESTRUCTURE_TYPE==='1000000002' || user.RESTRUCTURE_TYPE==='1000000001'"><bean:message bundle="quotationApplicationResources" key="qt.asset.plOfSupplAdd"/></div>
					<div class="col-lg-6 col-md-6 col-sm-6 col-xs-6" ng-show="user.RESTRUCTURE_TYPE==='1000000002' || user.RESTRUCTURE_TYPE==='1000000001'">
						<span class="textarea-span" style="position: relative;float: left;width: 100%;"><textarea cols="4" row="4" type="text" ng-model="user.PLACE_OF_SUPP_ADD_TEMP" ng-disabled="true" class="ng-pristine ng-untouched ng-valid" disabled="disabled"></textarea><span class="edit-textarea"></span></span> <!-- added by 1.0.0.8 -->
					</div>
					<div class="col-lg-2 col-md-2 col-sm-3 col-xs-6" ng-show="user.RESTRUCTURE_TYPE==='1000000002' || user.RESTRUCTURE_TYPE==='1000000001'"><bean:message bundle="quotationApplicationResources" key="qt.asset.billingState"/></div>
					<div class="col-lg-2 col-md-2 col-sm-3 col-xs-6" ng-show="user.RESTRUCTURE_TYPE==='1000000002' || user.RESTRUCTURE_TYPE==='1000000001'">
						<select id="BILLING_STATE" ng-model="user.BILLING_STATE" style="background-color:#ebf3ff;" ng-options="value.ID as value.VALUE for value in LEASE_BILLING_STATE" ng-disabled="true" > 
						</select>
					</div>
					<div class="col-lg-2 col-md-2 col-sm-3 col-xs-6" ng-show="user.RESTRUCTURE_TYPE==='1000000002' || user.RESTRUCTURE_TYPE==='1000000001'"><bean:message bundle="quotationApplicationResources" key="qt.asset.billAddress"/></div>
					<div class="col-lg-6 col-md-6 col-sm-6 col-xs-6" ng-show="user.RESTRUCTURE_TYPE==='1000000002' || user.RESTRUCTURE_TYPE==='1000000001'">
						<span class="textarea-span" style="position: relative;float: left;width: 100%;"><textarea cols="4" row="4" type="text" ng-model="user.BILLING_ADDRESS_TEMP" ng-disabled="true" class="ng-pristine ng-untouched ng-valid" disabled="disabled"></textarea><span class="edit-textarea"></span></span> <!-- added by 1.0.0.8 -->
					</div>
					<!-- 1.0.0.6 start -->
					<div class="col-lg-2 col-md-2 col-sm-3 col-xs-6" ng-show="user.RESTRUCTURE_TYPE==='1000000001'"><bean:message bundle="quotationApplicationResources" key="qt.asset.scheme"/></div>
					<div class="col-lg-2 col-md-2 col-sm-3 col-xs-6" ng-show="user.RESTRUCTURE_TYPE==='1000000001'">
						<select id="SCHEME" ng-model="user.SCHEME" style="background-color:#ebf3ff;" ng-disabled="true">
						 <option value="0"><bean:message bundle="quotationApplicationResources" key="quotation.common.select"/></option>
					     <option  ng-repeat="value in SCHEME_MASTER" value="{{value.VALUE}}">{{ value.NAME }}</option> 
						</select>
					</div>
					<!-- 1.0.0.6 end -->
					
				
	</div>
  </div>
  <!-- old values section end -->		
  
  <!-- New values start -->
   <div class=" col-lg-12 col-md-12 col-sm-12 col-xs-12"> 
      
		       <div class="legend col-lg-12 col-md-12 col-sm-12 col-xs-12" ng-show="user.RESTRUCTURE_TYPE==='1000000002' || user.RESTRUCTURE_TYPE==='1000000001'"><bean:message bundle="quotationApplicationResources" key="qt.asset.newValues"/></div>
		   		<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
		          <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
		   		   <div class="col-lg-2 col-md-2 col-sm-3 col-xs-6" ng-show="user.RESTRUCTURE_TYPE==='1000000002'"><bean:message bundle="quotationApplicationResources" key="qt.asset.rsd"/></div>
					<div class="col-lg-2 col-md-2 col-sm-3 col-xs-6" ng-show="user.RESTRUCTURE_TYPE==='1000000002'">
						<input type="text" id="RSD_NEW" ng-model="user.RSD_NEW" ng-disabled="!RSD_CHECKED_FLG" ng-blur="getPosDetail(user.RSD,'OLD RSD');">
						<a href='#' style="text-decoration: none" onClick="cal19.select(document.getElementById('RSD_NEW'),'RSD_NEW_Link~assetRestructureAppId~RSD_NEW~user','dd-NNN-yyyy'); return false;" ng-disabled="!RSD_CHECKED_FLG"
								NAME="RSD_NEW_Link" ID="RSD_NEW_Link" title='Calendar' style="text-decoration: none" >
								<i class="fa fa-calendar" aria-hidden="true" ></i>
							</a>
					</div>
		   		   	<div class="col-lg-2 col-md-2 col-sm-3 col-xs-6" ng-show="user.RESTRUCTURE_TYPE==='1000000002' || user.RESTRUCTURE_TYPE==='1000000001'"><bean:message bundle="quotationApplicationResources" key="qt.asset.lessee"/></div>
					<div class="col-lg-2 col-md-2 col-sm-3 col-xs-6" ng-show="user.RESTRUCTURE_TYPE==='1000000002' || user.RESTRUCTURE_TYPE==='1000000001'">
						<input type="hidden" ng-model="user.LESSE_NEW_ID" id="hid_entityTypeId">
						<input type="hidden" ng-model="user.LESSE_CODE_NEW" id="hid_entityCodeId">
			 			<input type="text" name="asa" ng-model="user.LESSE_NEW" ng-readonly="true" style="width:62%!important;text-transform:uppercase; background-color: #ebf3ff;" maxlength="20" placeholder="Click on Search" title={{user.LESSE_NEW}} />
						<input type="button" class="btn btn-primary btn-sm" ng-disabled="DISB_LESSEE_NEW" ng-show="user.RESTRUCTURE_TYPE==='1000000002' ||user.RESTRUCTURE_TYPE==='1000000001' " value="SEARCH" ng-click="searchWindow('CUSTOMER','N');"  />													   
					</div>
					
					<div class="col-lg-2 col-md-2 col-sm-3 col-xs-6" ng-show="user.RESTRUCTURE_TYPE==='1000000002'"><bean:message bundle="quotationApplicationResources" key = "quotation.dmApplicant.limitCode" /></div>
					<div class="col-lg-2 col-md-2 col-sm-3 col-xs-6"  ng-show="user.RESTRUCTURE_TYPE==='1000000002'">
						<input type="hidden" id="LIMIT_CODE_NEW" ng-model="user.LIMIT_CODE_NEW" /> 
						<input type="text" id="limitCodeNew" ng-model="user.LIMIT_CODE_NEW_TEMP" ng-attr-title="{{limitCodeNew}}" style="background-color:#ebf3ff;" maxlength="15"  size= "16"   readonly="true" /> 
						<input type="button" class="btn btn-primary btn-sm" name="btnlimitCode"  value="..."   ng-click="limitChooser('CUSTOMER LIMIT');" ng-disabled="DISB_LIMIT_CODE">
					</div>
					</div>			
					<div class="col-lg-2 col-md-2 col-sm-3 col-xs-6" ng-show="user.RESTRUCTURE_TYPE==='1000000002'"><bean:message bundle="quotationApplicationResources" key="qt.asset.agreementType"/></div>
					<div class="col-lg-2 col-md-2 col-sm-3 col-xs-6" ng-show="user.RESTRUCTURE_TYPE==='1000000002'">
						<select id="AGREEMENT_TYPE_NEW" ng-model="user.AGREEMENT_TYPE_NEW" ng-disabled="DISB_AGREEMENT_TYPE_NEW" ng-options="value.ID as value.NAME for value in AGREEMENT_TYPE" style="background-color:#ebf3ff;"> 
						</select>
					</div>

					<div class="col-lg-2 col-md-2 col-sm-3 col-xs-6" ng-show="user.RESTRUCTURE_TYPE==='1000000002'"><bean:message bundle="quotationApplicationResources" key="qt.asset.repaymentMode"/></div>
					<div class="col-lg-2 col-md-2 col-sm-3 col-xs-6" ng-show="user.RESTRUCTURE_TYPE==='1000000002'">
						<select id="REPAYMENT_MODE_NEW" ng-model="user.REPAYMENT_MODE_NEW" ng-options="value.ID as value.NAME for value in LEASE_REPAYMENT_MODE" style="background-color:#ebf3ff;" ng-disabled="DISB_REPAYMENT_MODE"> 
						</select>
					</div>
					
					<!-- 1.0.0.6 start -->
					<div class="col-lg-2 col-md-2 col-sm-3 col-xs-6" ng-show="user.RESTRUCTURE_TYPE==='1000000001'"><bean:message bundle="quotationApplicationResources" key="qt.asset.scheme"/></div>
					<div class="col-lg-2 col-md-2 col-sm-3 col-xs-6" ng-show="user.RESTRUCTURE_TYPE==='1000000001'">
						<select id="SCHEME_NEW" ng-model="user.SCHEME_NEW" style="background-color:#ebf3ff;" ng-disabled="DISB_SCHEME"> 
						 <option value="0"><bean:message bundle="quotationApplicationResources" key="quotation.common.select"/></option>
					     <option  ng-repeat="value in SCHEME_MASTER" value="{{value.VALUE}}">{{ value.NAME }}</option>
						</select>
					</div>
					<!-- 1.0.0.6 end -->
	
					<div class="col-lg-2 col-md-2 col-sm-3 col-xs-6" ng-show="user.RESTRUCTURE_TYPE==='1000000002'"><bean:message bundle="quotationApplicationResources" key="qt.asset.plOfSupplState"/></div>
					<div class="col-lg-2 col-md-2 col-sm-3 col-xs-6" ng-show="user.RESTRUCTURE_TYPE==='1000000002'">
						<select id="PLACE_OF_SUPP_STATE_NEW" ng-model="user.PLACE_OF_SUPP_STATE_NEW" style="background-color:#ebf3ff;" ng-options="value.ID as value.VALUE for value in PLACE_OF_SUPPLY_STATE" ng-disabled="DISB_PLACE_OF_SUPP_STATE_NEW" ng-change="clearPlaceOfSupplyAddress()"> 
						</select>
					</div>
					
					<div class="col-lg-2 col-md-2 col-sm-3 col-xs-6" ng-show="user.RESTRUCTURE_TYPE==='1000000002'"><bean:message bundle="quotationApplicationResources" key="qt.asset.plOfSupplAdd"/></div>
					<div class="col-lg-6 col-md-6 col-sm-6 col-xs-6" style="position: relative; min-height: 24px;" ng-show="user.RESTRUCTURE_TYPE==='1000000002'"><!-- added by 1.0.0.8 -->
						 <!-- <textarea cols="4" row="4" type="text"  ng-model="user.PLACE_OF_SUPP_ADD_NEW_TEMP" readonly="true" style="background-color:#e3e3e3 !important;;" ></textarea> -->
						 <span class="textarea-span" style="position: relative;float: left;width: 100%;"><textarea cols="4" row="4" type="text" ng-model="user.PLACE_OF_SUPP_ADD_NEW_TEMP" readonly="true" style="background-color:#e3e3e3 !important;;" class="ng-pristine ng-untouched ng-valid"></textarea></span>
						 <input type="hidden" id="PLACE_OF_SUPP_ADD_NEW" ng-model="user.PLACE_OF_SUPP_ADD_NEW" /> 
						 <input type="button" class="btn btn-primary btn-sm" name="btnPlaceOfSupply"  value="..." style="right:0%;"  ng-click="placeOfSupplyChooser('PLACE OF SUPPLY ADDRESS LOV');" ng-disabled="DISB_PLACE_OF_SUPP_ADD_NEW">  
					</div>
					<div class="col-lg-2 col-md-2 col-sm-3 col-xs-6" ng-show="user.RESTRUCTURE_TYPE==='1000000002'"><bean:message bundle="quotationApplicationResources" key="qt.asset.billingState"/></div>
					<div class="col-lg-2 col-md-2 col-sm-3 col-xs-6" ng-show="user.RESTRUCTURE_TYPE==='1000000002'">
						<select id="BILLING_STATE_NEW" ng-model="user.BILLING_STATE_NEW" style="background-color:#ebf3ff;" ng-options="value.ID as value.VALUE for value in LEASE_BILLING_STATE" ng-disabled="true" > <!-- 1.0.0.5 changed -->
						</select>
					</div>
					<div class="col-lg-2 col-md-2 col-sm-3 col-xs-6" ng-show="user.RESTRUCTURE_TYPE==='1000000002'"><bean:message bundle="quotationApplicationResources" key="qt.asset.billAddress"/></div>
					<div class="col-lg-6 col-md-6 col-sm-6 col-xs-6" style="position: relative; min-height: 24px;" ng-show="user.RESTRUCTURE_TYPE==='1000000002'"><!-- added by 1.0.0.8 -->
						<!-- <textarea cols="4" row="4" type="text"  ng-model="user.BILLING_ADDRESS_NEW_TEMP" readonly="true" style="background-color:#e3e3e3 !important;;" ></textarea> -->
						<span class="textarea-span" style="position: relative;float: left;width: 100%;"><textarea cols="4" row="4" type="text" ng-model="user.BILLING_ADDRESS_NEW_TEMP" readonly="true" style="background-color:#e3e3e3 !important;;" class="ng-pristine ng-untouched ng-valid"></textarea></span>
						<input type="hidden" id="BILLING_ADDRESS_NEW" ng-model="user.BILLING_ADDRESS_NEW" /> 
						<input type="button" class="btn btn-primary btn-sm" name="btnBillingAddress"  value="..." style="right:0%;"  ng-click="billingAddressChooser('BILLING ADDRESS LOV');" ng-disabled="DISB_BILLING_ADDRESS_NEW">
					</div>
				</div>
				</div>
	</fieldset>
	<div ng-show="(DISABLE_ALL_FIELDS_FLG && user.AUTH_FLG != 'A')?true:false">
	<center><input type="button"    class="btn btn-primary btn-sm" name="cancel" value="CANCEL" ng-disabled="disbCancelBtn" ng-click="save('CANCEL');"/></center><!-- 1.0.0.3 -->
	</div>
  
  
  <!-- New values section end -->		 
		      
      </div>
     
      
    </sform:form>
    
</body>
<jsp:include page="../common/footer.jsp" />

 <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
 
  <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
  <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
 <script src="vmenu/js/toggleMenu.js"></script>
<script src="js/bootstrap.min.js"></script>
<script src="js/slimscroll.js"></script>
<script>
  
  $(".tglDIv").click(function(){
  	$(this).toggleClass("tglDivSlide");
  	$(this).find("i").toggleClass("fa-minus");
  	//$(this).parent("legend").next("table").toggleClass("tglDivSlideTbl");
  	$(this).parent("div").next(".tglTable").slideToggle( "slow", function() { left: "0" });
  	//	document.getElementById("accesor_div_id").style.overflow = "auto";
  });
  
  //Start 1.0.0.7
  var oldDate = "";
  function callPosDtlFun(){
	  oldDate = document.getElementById("EFF_DATE").value;
	  var intervalId = setInterval(calPosDtl, 1000);
  }
 
  
  function calPosDtl() {
	  var newDate = document.getElementById("EFF_DATE").value;
	  if(oldDate != newDate)
	  	angular.element(document.getElementById('assetRestructureAppId')).scope().getPosDetail(document.getElementById("EFF_DATE").value,'EFFECTIVE TRANSFER DATE');
	  
	  oldDate = newDate;
	}
  //End 1.0.0.7
  
  </script>
