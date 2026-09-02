<!--
	Created By : Sunny Pathak
	Created on : 03-03-2021
		Version				Date				Updated By				Reason
		1.0.0.1				02-Sep-2026			Sanchi Agarwal			CR - Added Post Approval Rejection Source and Post Approval Rejection User columns on Vendor Refund Viewer screen
-->

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"    pageEncoding="ISO-8859-1" isELIgnored="false" %>
<%@ include file="../include/includeTld.inc"%> 
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sform"%> 
<!DOCTYPE html>
<html>
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

	<script type="text/javascript" src="js/controller/vendorRefund_controller.js"></script>
	
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
    var gbl_screenName= '${param.screenName}';
	function numberWithCommas(x) {
    var parts = x.toString().split(".");
    parts[0] = parts[0].replace(/\B(?=(\d{3})+(?!\d))/g, ",");
    return parts.join(".");
	}
</script>
<script type="text/javascript">
var gbl_staticInfo = "";
<logic:notEmpty name="staticInfoDtl">
	gbl_staticInfo = ${staticInfoDtl};
</logic:notEmpty>

var mastersListobj = '${mastersListGson}';
mastersListobj = JSON.parse(mastersListobj);

var vendorRecoveryObj = '${vendorRecoveryDtlStr}';
vendorRecoveryObj = JSON.parse(vendorRecoveryObj);
	
</script>

</head>

<%@include file="../common/header.jsp"%>

<%@include file="./quotationHeader.jsp"%>

<div   id="vetiTd" >
	<%@ include file="../vmenu/menu.jsp" %>
</div>
<div  class="toggling">
	<%@ include file="../vmenu/vmenuTgl.jsp" %>
</div>


<%@ include file="./quotationStaticInfo.jsp" %> 

<body class="menuHavingBody"  ng-app="vendorRefundApp" ng-controller="vendorRefundCtrl" id="vendorRefundAppId">
	<sform:form method="get" action="save">
	<fieldSet ng-disabled = "disabledAllFields">
	<div class="container-fluid">
	
		<div class=" col-lg-12 col-md-12 col-sm-12 col-xs-12">
		
			<div class=" col-lg-12 col-md-12 col-sm-12 col-xs-12">
						<div class="legend col-lg-12 col-md-12 col-sm-12 col-xs-12" style="line-height:22px">
							Recovery Details
						</div>	
						
						<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 " name="scrolltable" style="height: 320px; overflow: auto;">  <!-- 1.0.0.0.4 -->
						<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12"></div>
							<table style="width: 200% !important;">
								<tr valign="top" class="list_header">	
									<td align="center" style="width:1%;"><input type="checkbox" id = "SELECT_ALL" class="checkBoxes" name="SELECT_ALL" ng-checked="selectAllChecked == 'Y'?true:false" ng-model="SELECT_ALL" ng-click="selectAllRecords()" ng-disabled="authorScreen" /></td>
									<td style="width:4.5%;">Dealer Code</td>
									<td style="width:6%;">Dealer Name</td>
									<td style="width:4%;">Excess Payment</td>
									<td style="width:4%;">Amount</td>
									<td style="width:4%;">Instrument Type</td>
									<td style="width:4%;">Instrument No.</td>
									<td style="width:4%;">Instrument Date</td>
									<td style="width:1.5%;">OBD</td>
									<td style="width:4%;">Dealer Bank Details</td>
									<td style="width:4%;">Bank</td>
									<td style="width:4%;">Branch</td>
									<td style="width:3%;">Account No.</td>
									<td style="width:4.5%;">IFSC Code</td>
									<td style="width:4.5%;">MICR Code</td>
									<td style="width:4%;">Deposit Bank</td>
									<td style="width:4%;">Deposit Branch</td>
									<td style="width:4%;">Deposit Account</td>
									<td style="width:3.4%;">Deposit IFSC Code</td>
									<td style="width:3.4%;">Deposit MICR Code</td>
									<td ng-show="viewerScreen" style="width:3.5%;">Maker</td>
									<td ng-show="viewerScreen" style="width:3.5%;">Maker Date</td>
									<td style="width:3%;">Maker Remarks</td>
									<td ng-show="viewerScreen" style="width:2.5%;">Author</td>
									<td ng-show="viewerScreen" style="width:3.5%;">Author Date</td>
									<td ng-show="authorScreen" style="width:4.5%;">Author Decision</td>
									<td ng-show="authorScreen || viewerScreen " style="width:3%;">Author Remarks</td>
									<td ng-show="viewerScreen" style="width:2.5%;">Status</td>
								<!-- 1.0.0.1 add start -->
								<td ng-show="viewerScreen" style="width:4%;">Post Approval Rejection Source</td>
								<td ng-show="viewerScreen" style="width:4%;">Post Approval Rejection User</td>
								<!-- 1.0.0.1 add end -->
								</tr>
								
								<tr ng-repeat="itr_dealerObj in recoveryDtlObj">
								  <td align="center"><input type="checkbox"  id="ROW_SELECT{{$index}}" ng-model="itr_dealerObj.ROW_SELECT"  class="checkBoxes" ng-click="enableDisableRow(this.itr_dealerObj);" ng-checked = "itr_dealerObj.ROW_SELECT"  ng-disabled="authorScreen"/></td>
								  <td><input type="Text"  id="VENDOR_CODE{{$index}}" title="{{itr_dealerObj.VENDOR_CODE}}" ng-model="itr_dealerObj.VENDOR_CODE"  ng-disabled="true"/></td>
								  <td>
								  	<%-- <select id="ENTITY_NAME{{$index}}" ng-model="itr_dealerObj.ENTITY_NAME" ng-change="setVendorCode(itr_dealerObj);" ng-disabled="true">  <!-- 1.0.0.0.3  add setInsturmentType method-->
										<option value="0"><bean:message bundle="losApplicationResource" key="los.common.select" /></option>
										<option ng-repeat="value in ENTITY_NAME_MASTER_OBJ | unique : 'VENDOR_ID'" value="{{value.VENDOR_ID}}">{{value.VENDOR_NAME}}</option>
									</select> --%>
									<input type = "text" id="ENTITY_NAME{{$index}}" ng-model="itr_dealerObj.ENTITY_NAME" title="{{itr_dealerObj.ENTITY_NAME}}" ng-disabled="true" />
								  </td>
								  <td>
								  	<input type="text" id="EXCESS_PAYMENT{{$index}}" format="currency" ng-model="itr_dealerObj.EXCESS_PAYMENT" ng-disabled="true" style="text-align:right;" />
								  </td>
								  <td>
								  	<input type="text" id="AMOUNT{{$index}}" format="currency" ng-model="itr_dealerObj.AMOUNT" ng-disabled="itr_dealerObj.rowDisabled" ng-blur="checkAmountFormat(itr_dealerObj)" onkeypress="return (event.charCode >= 48 && event.charCode <= 57 ) || event.charCode == 46 " style="text-align:right;"/>
								  </td>
								  <td>
								  	<select id="INSTRUMENT_TYPE{{$index}}" ng-model="itr_dealerObj.INSTRUMENT_TYPE" ng-change="resetBankDetails(itr_dealerObj); enableDisableBankDtls(itr_dealerObj);"  ng-disabled="itr_dealerObj.rowDisabled" ng-change="changeInstType(itr_dealerObj)"> 
										<option value="0"><bean:message bundle="losApplicationResource" key="los.common.select" /></option>
										<option ng-repeat="value in INSTRUMENT_TYPE_MASTER_OBJ" value="{{value.ID}}">{{value.NAME}}</option>
									</select>
								  </td>
								  <td>
								  	<input type="text" id="INSTRUMENT_NO{{$index}}" ng-model="itr_dealerObj.INSTRUMENT_NO" ng-disabled="itr_dealerObj.rowDisabled"/>
								  </td>
								  <td>
								  	<input type="text" id="INSTRUMENT_DATE{{$index}}" title="{{itr_dealerObj.INSTRUMENT_DATE}}" ng-model="itr_dealerObj.INSTRUMENT_DATE"  maxlength = "20" onblur="javascript:checkDateFormat(this,'INSTRUMENT DATE');"  placeholder="DD-MON-YYYY" ng-disabled="itr_dealerObj.rowDisabled"/>  
									<a href='#' style="text-decoration: none" ng-click="getDateCalender('','','INSTRUMENT_DATE',$index)" ng-disabled="itr_dealerObj.rowDisabled"
									NAME="INSTRUMENT_DATE_link" ID="INSTRUMENT_DATE_link" title='Calendar' style="text-decoration: none"> <i class="fa fa-calendar" aria-hidden="true" style="margin-top: 6px;left: 113px;" ng-hide="itr_dealerObj.rowDisabled"></i> 
									</a>
								  </td>
								  
								  <td align="center"><input type="checkbox"  id="OBD{{$index}}"  title="OTHER BANK DETAILS" ng-click="setBeneficiary($index);" ng-model="itr_dealerObj.OBD" ng-checked="itr_dealerObj.OBD=='Y'?true:false"  ng-disabled="true"/></td> <!-- 1.0.0.10 --><!-- rowDisabled==true?true:(itr_dealerObj.INSTRUMENT_TYPE=='1000000002'||itr_dealerObj.INSTRUMENT_TYPE=='0')?true:false -->
			    
								  <td> 
							   		<input type="button" value="BANK DETAILS"  class="btn btn-primary btn-sm" ng-click="getBankDetails($index);" ng-disabled="itr_dealerObj.rowDisabled==true?true:(itr_dealerObj.INSTRUMENT_TYPE=='1000000002'||itr_dealerObj.INSTRUMENT_TYPE=='0'|| itr_dealerObj.OBD==true||itr_dealerObj.OBD=='Y')?true:false"/> <!-- 1.0.0.10 -->
							      </td>
								  
								  <td>
								  	<input type="hidden" id="BANK_NAME{{$index}}"  ng-model="itr_dealerObj.BANK_NAME"   /> 
									<input type="text" id="BANK_NAME_TEMP{{$index}}" title="{{itr_dealerObj.BANK_NAME_TEMP}}" ng-model="itr_dealerObj.BANK_NAME_TEMP"   readonly="true" /> 
									<input type= "button" class="btn btn-primary btn-sm " name="btnBank"  value="..."  id="bankLobId" ng-click="bankChooser($index,'QM_BANK','BANK_NAME');"  ng-disabled="itr_dealerObj.disableBankDtls === true?true:(itr_dealerObj.OBD==true||itr_dealerObj.OBD=='Y')?false:true" style="top:5px; "/>
								  </td>
								  <td>
								  	<input type="hidden" id="BRANCH_NAME{{$index}}"  ng-model="itr_dealerObj.BRANCH_NAME"   /> 
									<input type="text" id="BRANCH_NAME_TEMP{{$index}}" title="{{itr_dealerObj.BRANCH_NAME_TEMP}}" ng-model="itr_dealerObj.BRANCH_NAME_TEMP"   readonly="true" /> 
									<input type="button" class="btn btn-primary btn-sm " name="btnBranch"  value="..."  id="branchLobId" ng-click="branchChooser($index,'QM_BANKBRANCH','BRANCH_NAME');" ng-disabled="itr_dealerObj.disableBankDtls === true?true:(itr_dealerObj.OBD==true||itr_dealerObj.OBD=='Y')?false:true" style="top:5px; "/>
								  </td>
								  <td><input type="Text"  id="ACCOUNT_NO{{$index}}" title="{{itr_dealerObj.ACCOUNT_NO}}" ng-model="itr_dealerObj.ACCOUNT_NO"  ng-disabled="itr_dealerObj.rowDisabled==true?true:(itr_dealerObj.OBD==true||itr_dealerObj.OBD=='Y')?false:true"/></td>
								  <td>
								  	<input type="Text"  id="IFSC_CODE{{$index}}" title="{{itr_dealerObj.IFSC_CODE}}" ng-model="itr_dealerObj.IFSC_CODE"  ng-disabled="true"/>
								  </td>
								  <td>
								  	<input type="Text"  id="MICR_CODE{{$index}}" title="{{itr_dealerObj.MICR_CODE}}" ng-model="itr_dealerObj.MICR_CODE"  ng-disabled="true"/>
								  </td>
								  <td>
								  	<input type="hidden" id="DEPOSIT_BANK_NAME{{$index}}"  ng-model="itr_dealerObj.DEPOSIT_BANK_NAME"   /> 
									<input type="text" id="DEPOSIT_BANK_NAME_TEMP{{$index}}" title="{{itr_dealerObj.DEPOSIT_BANK_NAME_TEMP}}" ng-model="itr_dealerObj.DEPOSIT_BANK_NAME_TEMP"   readonly="true" /> 
									<input type="button" class="btn btn-primary btn-sm " name="btnBank"  value="..."  id="depositBankLobId" ng-click="bankValueChooser(itr_dealerObj,$index,'DEPOSIT_BANK_NAME');" ng-disabled="itr_dealerObj.rowDisabled" style="top:5px; " />
								  </td>
								  <td>
								  	<input type="hidden" id="DEPOSIT_BRANCH_NAME{{$index}}"  ng-model="itr_dealerObj.DEPOSIT_BRANCH_NAME"   /> 
									<input type="text" id="DEPOSIT_BRANCH_NAME_TEMP{{$index}}" title="{{itr_dealerObj.DEPOSIT_BRANCH_NAME_TEMP}}" ng-model="itr_dealerObj.DEPOSIT_BRANCH_NAME_TEMP"   readonly="true" /> 
									<input type="button" class="btn btn-primary btn-sm " name="btnBranch"  value="..."  id="depositBranchLobId" ng-click="branchValueChooser(itr_dealerObj,$index,'DEPOSIT_BRANCH_NAME');" ng-disabled="itr_dealerObj.rowDisabled" style="top:5px;" />
								  </td>
								  <td>
								  	<input type="hidden" id="DEPOSIT_ACCOUNT_NO{{$index}}"  ng-model="itr_dealerObj.DEPOSIT_ACCOUNT_NO"   /> 
									<input type="text" id="DEPOSIT_ACCOUNT_NO_TEMP{{$index}}" title="{{itr_dealerObj.DEPOSIT_ACCOUNT_NO_TEMP}}" ng-model="itr_dealerObj.DEPOSIT_ACCOUNT_NO_TEMP"   readonly="true" /> 
									<input type="button" class="btn btn-primary btn-sm " name="btnAccount"  value="..."  id="depositAccountLobId" ng-click="accountValueChooser(itr_dealerObj,$index,'DEPOSIT_ACCOUNT_NO');" ng-disabled="itr_dealerObj.rowDisabled" style="top:5px; " />
								  </td>
								  <td>
								  	<input type="Text"  id="DEPOSIT_IFSC_CODE{{$index}}" title="{{itr_dealerObj.DEPOSIT_IFSC_CODE}}" ng-model="itr_dealerObj.DEPOSIT_IFSC_CODE"  ng-disabled="true"/>
								  </td>
								  <td>
								  	<input type="Text"  id="DEPOSIT_MICR_CODE{{$index}}" title="{{itr_dealerObj.DEPOSIT_MICR_CODE}}" ng-model="itr_dealerObj.DEPOSIT_MICR_CODE"  ng-disabled="true"/>
								  </td>
								  <td ng-show="viewerScreen">
								  	<input type="Text" id="MAKER{{$index}}" title="{{itr_dealerObj.MAKER}}" ng-model="itr_dealerObj.MAKER"  />
								  </td>
								  <td ng-show="viewerScreen">
								  	<input type="Text" id="MAKER_DATE{{$index}}" title="{{itr_dealerObj.MAKER_DATE}}" ng-model="itr_dealerObj.MAKER_DATE"  />
								  </td>
								  <td>
								  	<input type="Text" id="MAKER_REMARKS{{$index}}" title="{{itr_dealerObj.MAKER_REMARKS}}" ng-model="itr_dealerObj.MAKER_REMARKS" ng-disabled="itr_dealerObj.rowDisabled" />
								  </td>
								  <td ng-show="viewerScreen">
								  	<input type="Text" id="AUTHOR{{$index}}" title="{{itr_dealerObj.AUTHOR}}" ng-model="itr_dealerObj.AUTHOR"  />
								  </td>
								  <td ng-show="viewerScreen">
								  	<input type="Text" id="AUTHOR_DATE{{$index}}" title="{{itr_dealerObj.AUTHOR_DATE}}" ng-model="itr_dealerObj.AUTHOR_DATE"  />
								  </td>
								  <td ng-show="authorScreen">
								  	<select id="AUTHOR_DECS{{$index}}" ng-model ="itr_dealerObj.AUTHOR_DECS" ng-change="resetAuthorDecision()">
								  		<option value="0"><bean:message bundle="losApplicationResource" key="los.common.select" /></option>
										<option value="A" >APPROVE</option>
										<option value="R" >REJECT</option>
								  	</select>
								  </td>
								  <td ng-show="authorScreen || viewerScreen">
								  	<input type="Text" id="AUTHOR_REMARKS{{$index}}" title="{{itr_dealerObj.AUTHOR_REMARKS}}" ng-model="itr_dealerObj.AUTHOR_REMARKS"  />
								  </td>
								  <td ng-show="viewerScreen">
								  	<input type="Text" id="STATUS{{$index}}" title="{{itr_dealerObj.STATUS}}" ng-model="itr_dealerObj.STATUS" ng-disable = "true"  />
								  </td>
								  <!-- 1.0.0.1 add start -->
								  <td ng-show="viewerScreen">
								  	<input type="Text" id="POST_APPR_REJ_SOURCE{{$index}}" title="{{itr_dealerObj.POST_APPR_REJ_SOURCE}}" ng-model="itr_dealerObj.POST_APPR_REJ_SOURCE" ng-disabled="true" />
								  </td>
								  <td ng-show="viewerScreen">
								  	<input type="Text" id="POST_APPR_REJ_USER{{$index}}" title="{{itr_dealerObj.POST_APPR_REJ_USER}}" ng-model="itr_dealerObj.POST_APPR_REJ_USER" ng-disabled="true" />
								  </td>
								  <!-- 1.0.0.1 add end -->
								</tr>
							</table>	
						</div>
					</div>
					
					<div class="legend TglDivParent legendFloat col-lg-12 col-md-12 col-sm-12 col-xs-12">
					<div class="col-lg-4 col-md-4 col-sm-4 col-xs-4 "><bean:message bundle="losApplicationResource" key="los.dmOffline.dealerDetails" /></div>
					<div class="tglDIv tglDivSlide" style="top: 31%;z-index: 10;"><i class="fa fa-minus" id="icon-btn"></i></div>
					<div class="col-lg-8 col-md-8 col-sm-8 col-xs-8 main_body text-right">
					&nbsp;
					</div>
					</div>
					<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 tglTable" style="display:block">
						<table style="width: 100% !important;">
							<tr valign="top" class="list_header">							
								<th width="30%" align="left"><bean:message bundle="losApplicationResource" key="los.twowheeler.frameset.dealerName" /></th>  
								<th width="15%" align="left"><bean:message bundle="losApplicationResource" key="los.dmWorkflow.dealerCode" /></th>
								<th width="20%" style="text-align: right;">Total recovery Amount (Rs.)</th>
								<th width="20%" style="text-align: right;padding-right: 5px;">Balance To Be recovered (Rs.)</th>									
							</tr>
							<tr ng-repeat="itr_dealerPaymentObj in dealerRecoveryDtls">
							<td >{{itr_dealerPaymentObj.DEALER_NAME}}</td>
							<td>{{itr_dealerPaymentObj.DEALER_CODE}}</td>
							<td style="text-align: right;">{{itr_dealerPaymentObj.TOTAL_RECOVERY}}</td>
							<td style="text-align: right;padding-right: 5px;">{{itr_dealerPaymentObj.BALANCE_RECOVERY}}</td>
							</tr>
						</table>
					 </div>
					 <br/>
					 <br/>
					 <br/>
					 <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12" ng-show="screenId === '1200109091'?true:false">
					 <div class="legend  col-lg-12 col-md-12 col-sm-12 col-xs-12">MAKER DECISION</div>
					  <div class="col-lg-2 col-md-2 col-sm-12 col-xs-12">Send To Author</div>
					  <div class="col-lg-3 col-md-3 col-sm-12 col-xs-12"><input type="checkbox" ng-model="SEND_TO_AUTH" ng-checked="SEND_TO_AUTH"/></div>
					 </div>
					 <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12" ng-show="authorScreen">
					 <div class="legend  col-lg-12 col-md-12 col-sm-12 col-xs-12">AUTHOR DECISION</div>
					  <div class="col-lg-3 col-md-3 col-sm-12 col-xs-12">Approve All&nbsp;&nbsp;&nbsp;<input type="checkbox" ng-model="APPROVE_ALL" ng-click="selectAuthDecs('A')" ng-checked="APPROVE_ALL"/></div>
					  
					  <div class="col-lg-3 col-md-3 col-sm-12 col-xs-12">Reject All&nbsp;&nbsp;&nbsp;<input type="checkbox" ng-model="REJECT_ALL" ng-click="selectAuthDecs('R')" ng-checked="REJECT_ALL"/></div>
					 </div>

		</div>
		</div>
		</fieldSet>
	</sform:form>
</body>
</html>

<script src="vmenu/js/toggleMenu.js"></script>
<script src="js/bootstrap.min.js"></script>
<script src="js/slimscroll.js"></script>
<script>
  
$(".tglDIv").click(function(){
  	$(this).toggleClass("tglDivSlide");
  	if($("#icon-btn").attr('class') === 'fa fa-minus'){
  		$("#icon-btn").removeClass("fa fa-minus");
  		$("#icon-btn").addClass("fa fa-plus");
  	}
  	else{
  		$("#icon-btn").removeClass("fa fa-plus");
  		$("#icon-btn").addClass("fa fa-minus");
  	}
  	$(this).parent("div").next(".tglTable").slideToggle( "slow", function() { left: "0" });
  });
  </script>
