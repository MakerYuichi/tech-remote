/* HEADER INFO
+  File NAME 	: quotation_controller_trf.js
+  PURPOSE		: 
+  CREATED BY	: 
+  CREATION DATE	: 
+  INITIAL VERSION : 1.0.0.0
+  **********************************************************************************************************************************
+  VERSION NO   UPDATED BY       UPDATED ON      REASON FOR CHANGE
   1.0.0.1      Ravi             24-Feb-2020     Code changes for asset Transfer (added three new section.)
   1.0.0.2		Sunny Pathak	 27-Mar-2020	 Asset extensio/transfer flow bugs resolution.
   1.0.0.3		Ravinder Kaur		07-APR-2020		viewModeFlag passed to every master.
   1.0.0.4		Sunny Pathak	 26-APR-2020	 Bug :- AMC, Tyre, Maintanence and battery schedule distorting.
   1.0.0.5		Sunny Pathak	 09-MAY-2020	 100 Allowed to enter in market RV % and fixed RV %.
   1.0.0.6		Sunny Pathak	 21-May-2020	 Blocked all validations for DM module
   1.0.0.7		Sunny Pathak	 21-May-2020	FMs franking and others alert condition correction
   1.0.0.8		Sunny Pathak	 10-jul-2020	Validation blocked at dm level
   1.0.0.9		 Sunny Pathak	 14-Dec-2020	 	CR - Actual Date Billing Cycle.
   1.0.0.10		Sunny Pathak	 05-feb-2021	Cr : Paynimo Payment gateway integration
   1.0.0.11		Sunny Pathak	 19-Mar-2021	Added a new condition for disable flag of initiate button.
   1.0.0.12		Priyanka Soni		28-Oct-2022		CR:- User Logout on EOD
   1.0.0.13	   Tanisha Agarwal 	05-Aug-2024	 	E Sign Document
   1.0.0.14    Narottam Biswal  11-Aug-2015     Maintenance Factoring without OTP
   1.0.0.15    Nalin Kumar Jena  28-Apr-2026    Tabs to be added in restructuring module
   1.0.0.16     Sanchi Agarwal   18-Sep-2026    RV Amount to be allowed to be entered when RV Type is fixed
   1.0.0.17     Sanchi Agarwal   15-Oct-2026    Added Documents Not Required option to Agreement Signing Type dropdown
   **********************************************************************************************************************************
 */

'use strict';
var app= angular.module("qtoApp",[]);
app.controller('QuotationController', function($scope, $http,$window,$filter,$rootScope ) {

	

	var responseFlag = "Y";
	$scope.screenViewMode=screenViewMode;
	//add start by 1.0.0.2
	$scope.transCase = true;
	if(assetTrfFlag == "EXTENSION"){
		$scope.transCase = false;
	}
	//add end by 1.0.0.2
	$scope. disabledAllFields=false;
	if(verticalMenuViewMode=="1200003020" || verticalMenuViewMode=="1200004012"){$scope. disabledAllFields=true; $scope.disableForeclosureSection=true;document.getElementById("START_DATE_Link").removeAttribute('href');document.getElementById("START_DATE_Link").removeAttribute('onclick');}else{if(screenViewMode=="V"){$scope.disabledAllFields=true;$scope.disableForeclosureSection=true;document.getElementById("START_DATE_Link").removeAttribute('href');document.getElementById("START_DATE_Link").removeAttribute('onclick');}else{$scope.disabledAllFields=false;$scope.disableForeclosureSection=false;}}
			  
	
	if(entityId == "1000000008")
	{
		$scope.disabledAllFields=true;
		document.getElementById("START_DATE_Link").removeAttribute('href');
		document.getElementById("START_DATE_Link").removeAttribute('onclick');
		
		
	}	
	
	//1.0.0.10 add start
	$scope.disableSdSection = false;

	if(entityId == "1000000008" && (staticInfo.PC_STATIC_INFO_DATA[0].DM_STATUS =="CLOSED" || staticInfo.PC_STATIC_INFO_DATA[0].DM_STATUS =="FORECLOSED" || staticInfo.PC_STATIC_INFO_DATA[0].DM_STATUS =="CANCELLED" || staticInfo.PC_STATIC_INFO_DATA[0].DM_STATUS =="REJECT")){
		$scope.disableSdSection = true;
	}
	
	if(entityId == '1000000008'){
		document.getElementById("securityDepositDiv").style.display = 'block';
	}else{
		document.getElementById("securityDepositDiv").style.display = 'none';
	}
	//1.0.0.10 add end

	var objects = $scope.JSONfromServer;
	
	$scope.isLeasePeriodDisable=true;
	$scope.isDisabledMarket=true;
	$scope.isDisabledFixed = true;
	$scope.isResidualValueEnabled = false;  // 1.0.0.16
	$scope.isDisabledFixedPercentage = false;  // 1.0.0.16
	$scope.isAuto=false;
	$scope.isAMCEnable= true;
	$scope.isMaintEnable=true;
	$scope.isChargeDisable=false;
	$scope.RESIDUAL_VALUE_TYPE=[];
	$scope.RV_SLAB=[];
	$scope.RV_CALCULATION=[];
	$scope.RENTAL_TYPE=[];
	$scope.RENTAL_MODE=[];
	$scope.RENTAL_BILLING_CYCLE=[];
	$scope.MAINTENANCE_SLAB_TYPE=[];
	$scope.FRANKING_TYPE=[];
	$scope.EXT_WARRANTY_STRUCTURE_TYPE=[];
	$scope.AMC_STRUCTURE_TYPE=[];
	//1.0.0.1 start
	$scope.MAINTENANCE_SCHEDULE_COL=[];
	$scope.TYRE_SCHEDULE_COL=[];
	$scope.BATTERY_SCHEDULE_COL=[];
	// 1.0.0.1 end
	$scope.extDetails=[];
	$scope.valueAdded=[];
	$scope.applicability=[];
	
	$scope.SCHEME=[];
	$scope.CHARGE_TYPE=[];
	$scope.AGREEMENT_TYPE=[];
	$scope.AGREEMENT_SIGN_TYPE=[];//1.0.0.13
	$scope.LEASE_BILLING_TYPE=[];
	
	$scope.LEASE_ITC_AVAIL_OPTIONS_TYPE=[];
	$scope.RENTAL_LOGIC_MASTER=[];//1.0.0.9
	
	$scope.TOTAL_MAINTENANCE="0.00";
	
	$scope.ALLOW_LEASE_TYRE = "N";
    $scope.ALLOW_LEASE_BATTERY="N"; 
	$scope.monthlyCharges=[];
	  $scope.amcDetails=[];
	 var jsonStr; 
	
	 
	 $scope.PRODUCT_TYPE=[];
	 
	 $scope.FORECLOSURE_METHOD_TYPE=[];
	 $scope.REPAYMENT_MODE_TYPE=[];
	 
	 	//1.0.0.10 add start
		$scope.ONLINE_PAYMENT_TYPE_MASTER=[];//1.0.0.10
		$scope.ONLINE_PAYMENT_TYPE="0";
		//1.0.0.10 add end
	
	// Start 1.0.0.15 
	$scope.RSD_FLAG="N";
	$scope.RESTRUCTURE_TYPE="";
	$scope.EXTENSION_PERIOD="";
	$scope.DOC_REQUIRED="";
	// End 1.0.0.15 
	
	var arrMaster = new Array;
	var arrAddOnServices = new Array;
	var arrRentalDtl = new Array;
	var arrValidationValues = new Array;
	var arrExtWarrenty = new Array;
	var arrMaintDtl = new Array;
	var arrMonthlyCharges = new Array;
	var arrAMCDtl = new Array;
	
	var branch_id = staticInfoBranchId1 ; 
	var quotation_id = quotationId;
	var arrProductMaster = new Array;
	var arrLOCMaster = new Array;
	var dateDiffLogic;
	var valueAddedStr="";
	var removeChargeIdStr= "";
	var removeExtendedWarrentyIdStr="";
	var removeMaintTyreBatteryIdStr="";//1.0.0.1
	var isAMCEnableFlag="";
	var isMaintEnableFlag="";
	var isFirstTimeMonthlyCharges = true;
	$scope.flag = 'N';
	var temp_autoFrankingPer ="";
	
	$scope.disableEquipFlag = false;
	
	$scope.getDefaultValuesFlagForAddOnServices = true; 
	
	$scope.screenIsShow=temp_screenId; //1.0.0.14
	
	$scope.validateDecimalLength = function(obj){
		var text = "";
		if(obj=="PERCENT")
			{
			   text=$scope.market[obj];
			}
		else
			{
			   text = $scope[obj];
			}
		var arr = text.split('.');
		if(arr.length>1){
		if(arr[1].length> 4){
			arr[1] = arr[1].slice(0,4);
		}
		if(obj=="PERCENT")
		{
		   $scope.market[obj]=arr[0]+"."+arr[1];
		}
	else
		{
		   $scope[obj]=arr[0]+"."+arr[1];
		}
		
		
		}
	};
	
	//1.0.0.10 add start
	
	$scope.disableInitiateBtn = !angular.isUndefined(staticInfo.PC_STATIC_INFO_DATA[0].DISABLE_ONLINE_PYMNT_FLG)?staticInfo.PC_STATIC_INFO_DATA[0].DISABLE_ONLINE_PYMNT_FLG:"N";//1.0.0.11
	
	$scope.disabeldInitiateBtn =function()
	{
		angular.forEach($scope.ONLINE_PAYMENT_TYPE_MASTER, function( obj)
				{
			        if($scope.ONLINE_PAYMENT_TYPE==obj.ID)
			        	{
			        	$scope.initiateDisabledFlag=($scope.disableInitiateBtn === "Y" || obj.ENABLE_PRE_SANC_PYMNT_LINK=="D")?true:false; //new condition of DISABLE_ONLINE_PYMNT_FLG added by 1.0.0.11
			        	   
			        	}
				});
		
	}

	$scope.initiatePayment = function()
	{
		$scope.initiateDisabledFlag=true;
		$.ajax({  
			type : "post",   
			url : "initiateSecurityDeposit.sprg?isAjaxCalling=Y", //1.0.0.12 added  isAjaxCalling=Y   
			data:{paymentType: $scope.ONLINE_PAYMENT_TYPE},
			success: function(response) 
				{
				var jsonStr = JSON.parse(response);
				if(jsonStr.Status=="SessionExpired") //1.0.0.12
					{
						window.location.href = 'llm/ReLogin.jsp?unknownError=Your session has been killed. Please login again.';
						return;
					}
					// console.log(jsonStr)
				alert((jsonStr.Msg).toUpperCase());
				
				if(jsonStr.Status=="S")
					{
					  $scope.initiateDisabledFlag=true;
					}
				else
					{
					  $scope.initiateDisabledFlag=false;
					}
					
					
				}
		});
	}

	$scope.getHistScreen = function()
	{
		var paramList=staticInfo.PC_STATIC_INFO_DATA[0].DM_ID;

		   var url =   "dynamicWindow.do?actionPerformed=dynamicWindow&windowId=1000000002&paramList="+paramList+"";
		   
		   var self=window.open(url,"dynamicWindow","width=800px,height=500px,left=400px,titlebar=yes,scrollbars=yes,toolbar=no,maximize=no,menubar=no,minimize=no,statusbar=no");
	}
	
	//start 1.0.0.14 
	$scope.getMaintenanceStatScreen = function()
	{
		var paramList=staticInfo.PC_STATIC_INFO_DATA[0].DM_ID;

		   var url =   "dynamicWindow.do?actionPerformed=dynamicWindow&windowId=1000000010&paramList="+paramList+"";
		   
		   var self=window.open(url,"dynamicWindow","width=800px,height=500px,left=400px,titlebar=yes,scrollbars=yes,toolbar=no,maximize=no,menubar=no,minimize=no,statusbar=no");
	}
	//end 1.0.0.14

	$scope.getPaymentDtl = function(){
		$.ajax({  
			type : "post",   
			url : "sdPaymentDtls.sprg?isAjaxCalling=Y", //1.0.0.12 added  isAjaxCalling=Y   
			success: function(response) 
				{
				var jsonStr = JSON.parse(response);
				if(jsonStr.Status=="SessionExpired") //1.0.0.12
					{
						window.location.href = 'llm/ReLogin.jsp?unknownError=Your session has been killed. Please login again.';
						return;
					}
				
				if(jsonStr.Status=="S")
					{
					  $scope.ONLINE_PAYMEN_STATUS=jsonStr.PC_PAY_DETAILS.CURR_STATUS;
					}
					
				$scope.$apply();
					
				}
		});
	};
	
	$scope.updatePayment = function()
	{
		$.ajax({  
			type : "post",   
			url : "updatePaymentStatus.sprg?isAjaxCalling=Y", //1.0.0.12 added  isAjaxCalling=Y   
			success: function(response) 
				{
				var jsonStr = JSON.parse(response);
				if(jsonStr.Status=="SessionExpired") //1.0.0.12
					{
						window.location.href = 'llm/ReLogin.jsp?unknownError=Your session has been killed. Please login again.';
						return;
					}
				alert((jsonStr.Message).toUpperCase());
				$scope.$apply();
					
				}
		});
	}
	//1.0.0.10 add end
	
	
	$scope.screenIdFromRequest=entityId!=undefined && entityId=="1000000008"?true:false;
	$scope.getJSONfromServer = function(){
		
		
	  				
	  					 jsonStr = JSON.parse(mastersListStr);
	  					$scope.businessDate=jsonStr.businessDate;
	  					
	  					 
	  					 arrLOCMaster = jsonStr.LOCMasterDetail;
	  					 if(jsonStr.quotationDetails != undefined)
	  						 {
	  						 	arrValidationValues = jsonStr.quotationDetails.GET_VALIDATION_VALUES;
	  						 	$scope.penalty= jsonStr.quotationDetails.GET_PENALTY_PERCENT; 
	  						 }
	  					
	  					
	  					
	  					arrMaster = !angular.isUndefined(jsonStr.rentalMaster )?jsonStr.rentalMaster[0]:"";
	  					
	  					$scope.ONLINE_PAYMENT_TYPE_MASTER = jsonStr.ONLINE_PAYMENT_TYPE_MASTER;//1.0.0.10
	  					 for(var i=0;i<arrMaster.length;i++){
	  						   if(arrMaster[i].PARAM_NAME == "RESIDUAL VALUE TYPE"){
	  							 $scope.RESIDUAL_VALUE_TYPE.push ({
	  								 'ID':arrMaster[i].ID,
	  								 'NAME':arrMaster[i].NAME
	  							 });
	  						   } else if (arrMaster[i].PARAM_NAME =="RESIDUAL VALUE SLAB TYPE"){
	  							   $scope.RV_SLAB.push ({
	  								 'ID':arrMaster[i].ID,
	  								 'NAME':arrMaster[i].NAME
	  							   });
	  						   }
	  						   else if (arrMaster[i].PARAM_NAME =="RESIDUAL VALUE CALCULATION ON"){
	  							   $scope.RV_CALCULATION.push ({
	  								 'ID':arrMaster[i].ID,
	  								 'NAME':arrMaster[i].NAME
	  							   });
	  						   }
	  						 else if (arrMaster[i].PARAM_NAME =="RENTAL TYPE"){
	  							   $scope.RENTAL_TYPE.push ({
	  								 'ID':arrMaster[i].ID,
	  								 'NAME':arrMaster[i].NAME
	  							   });
	  						   }
	  						else if (arrMaster[i].PARAM_NAME =="RENTAL MODE"){
	  							   $scope.RENTAL_MODE.push ({
	  								 'ID':arrMaster[i].ID,
	  								 'NAME':arrMaster[i].NAME
	  							   });
	  						   }
	  						else if (arrMaster[i].PARAM_NAME =="RENTAL BILLING CYCLE"){
	  							   $scope.RENTAL_BILLING_CYCLE.push ({
	  								 'ID':arrMaster[i].ID,
	  								 'NAME':arrMaster[i].NAME
	  							   });
	  						   }
	  						else if (arrMaster[i].PARAM_NAME =="MAINTENANCE SLAB TYPE"){
	  							   $scope.MAINTENANCE_SLAB_TYPE.push ({
	  								 'ID':arrMaster[i].ID,
	  								 'NAME':arrMaster[i].NAME
	  							   });
	  						   }
	  						else if (arrMaster[i].PARAM_NAME =="FRANKING TYPE"){
	  							   $scope.FRANKING_TYPE.push ({
	  								 'ID':arrMaster[i].ID,
	  								 'NAME':arrMaster[i].NAME
	  							   });
	  						   }
	  						else if (arrMaster[i].PARAM_NAME =="EXT WARRANTY STRUCTURE TYPE"){
	  							   $scope.EXT_WARRANTY_STRUCTURE_TYPE.push ({
	  								 'ID':arrMaster[i].ID,
	  								 'NAME':arrMaster[i].NAME
	  							   });
	  						   }
	  						else if (arrMaster[i].PARAM_NAME =="AMC STRUCTURE TYPE"){
	  							   $scope.AMC_STRUCTURE_TYPE.push ({
	  								 'ID':arrMaster[i].ID,
	  								 'NAME':arrMaster[i].NAME
	  							   });
	  						   }
	  						else if (arrMaster[i].PARAM_NAME =="INTEREST ON ADVANCE CHARGE TYPE"){
	  							   $scope.CHARGE_TYPE.push ({
	  								 'ID':arrMaster[i].ID,
	  								 'NAME':arrMaster[i].NAME
	  							   });
	  						   }
	  						else if (arrMaster[i].PARAM_NAME =="RENTAL AGREEMENT TYPE"){
	  							   $scope.AGREEMENT_TYPE.push ({
	  								 'ID':arrMaster[i].ID,
	  								 'NAME':arrMaster[i].NAME
	  							   });
	  						   }
	  						 
	  						else if (arrMaster[i].PARAM_NAME =="LEASE FORECLOSURE METHOD"){
	  							   $scope.FORECLOSURE_METHOD_TYPE.push ({
	  								 'ID':arrMaster[i].ID,
	  								 'NAME':arrMaster[i].NAME
	  							   });
	  						   }
	  						  
	  						else if (arrMaster[i].PARAM_NAME =="LEASE REPAYMENT MODE"){
	  							   $scope.REPAYMENT_MODE_TYPE.push ({
	  								 'ID':arrMaster[i].ID,
	  								 'NAME':arrMaster[i].NAME
	  							   });
	  						   }
	  						else if (arrMaster[i].PARAM_NAME =="LEASE BILLING TYPE"){
	  							   $scope.LEASE_BILLING_TYPE.push ({
	  								 'ID':arrMaster[i].ID,
	  								 'NAME':arrMaster[i].NAME
	  							   });
	  						   } 
	  						   
	  						else if (arrMaster[i].PARAM_NAME =="LEASE ITC AVAIL OPTIONS"){
	  							   $scope.LEASE_ITC_AVAIL_OPTIONS_TYPE.push ({
	  								 'ID':arrMaster[i].ID,
	  								 'NAME':arrMaster[i].NAME
	  							   });
	  						   } 
	  						 //1.0.0.9 add start
	  						else if (arrMaster[i].PARAM_NAME =="RENTAL LOGIC"){
	  							   $scope.RENTAL_LOGIC_MASTER.push ({
	  								 'ID':arrMaster[i].ID,
	  								 'NAME':arrMaster[i].NAME
	  							   });
	  						   } 
	  						   //1.0.0.9 add end
	  						else if (arrMaster[i].PARAM_NAME =="AGREEMENT SIGNING TYPE"){
	  							   $scope.AGREEMENT_SIGN_TYPE.push ({
	  								 'ID':arrMaster[i].ID,
	  								 'NAME':arrMaster[i].NAME
	  							   });
	  						   } //1.0.0.13
					 }; //end of for}; 
	  					 if(jsonStr.quotationDetails != undefined)
  						 {
	  						 arrAddOnServices = (!angular.isUndefined(jsonStr.quotationDetails.GET_ADD_ON_SERVICES) && jsonStr.quotationDetails.GET_ADD_ON_SERVICES!=0)? jsonStr.quotationDetails.GET_ADD_ON_SERVICES:"" ;
  						 }
	  					arrProductMaster = jsonStr.productDetails;
	  					
	  					
	  					for(var i =0;i< arrProductMaster.length;i++){
	  						if(arrProductMaster[i].masterParentId == branch_id){
	  							
	  							$scope.PRODUCT_TYPE.push({
	  								'ID':arrProductMaster[i].productId,
	  								'VALUE':arrProductMaster[i].productName
	  							});
	  						}
	  					} 
	  					
	  					 if(jsonStr.quotationDetails != undefined)
	  						 {
		  					if(!angular.isUndefined(jsonStr.quotationDetails.GET_RENTAL_DTL))
		  						{
		  						arrRentalDtl = (jsonStr.quotationDetails.GET_RENTAL_DTL.length!=0 )?jsonStr.quotationDetails.GET_RENTAL_DTL[0]:"";	
		  						}
		  					else
		  						{
		  						arrRentalDtl="";
		  						}
	  						 }
	  					
	  					$scope.FORECLOSURE_DISC_PERC = arrRentalDtl.FORECLOSURE_DISC_PERC!=null ?arrRentalDtl.FORECLOSURE_DISC_PERC : "0.00";
	  					$scope.DISCOUNTING_PER_HIGHLIGHT_FLAG = !angular.isUndefined(arrRentalDtl.DISCOUNTING_PER_HIGHLIGHT_FLAG)?arrRentalDtl.DISCOUNTING_PER_HIGHLIGHT_FLAG:"N";
	  					$scope.FORECLOSURE_REMARKS = arrRentalDtl.FORECLOSURE_REMARKS!=null? arrRentalDtl.FORECLOSURE_REMARKS : "";
	  					$scope.FORECLOSURE_PENALTY_PERC = arrRentalDtl.FORECLOSURE_PENALTY_PERC!=null ? arrRentalDtl.FORECLOSURE_PENALTY_PERC : "0.00";
	  					$scope.LPI_PERCENT = arrRentalDtl.LPI_PERCENT!=null? arrRentalDtl.LPI_PERCENT : "0.00";
	  					$scope.INTEREST_ON_ADVN = arrRentalDtl.INTEREST_ON_ADVN!=null ? arrRentalDtl.INTEREST_ON_ADVN : "0.00";
	  					$scope.FORECLOSURE_METHOD = !angular.isUndefined(arrRentalDtl.FORECLOSURE_METHOD)?arrRentalDtl.FORECLOSURE_METHOD:"0";
	  					$scope.REPAYMENT_MODE = !angular.isUndefined(arrRentalDtl.REPAYMENT_MODE)?arrRentalDtl.REPAYMENT_MODE:"0";
	  					$scope.PROPOSED_CASHFLOW_IRR = !angular.isUndefined(arrRentalDtl.PROPOSED_CASHFLOW_IRR)?arrRentalDtl.PROPOSED_CASHFLOW_IRR:"0.00";
	  					//1.0.0.1 start
	  					$scope.TRANSFER_AMT_FMS_OTHER = !angular.isUndefined(arrRentalDtl.TRANSFER_AMT_FMS_OTHER)?arrRentalDtl.TRANSFER_AMT_FMS_OTHER:"0";
	  					$scope.TRANSFER_AMT_FMS_FRANK = !angular.isUndefined(arrRentalDtl.TRANSFER_AMT_FMS_FRANK)?arrRentalDtl.TRANSFER_AMT_FMS_FRANK:"0";
	  					//1.0.0.1 end
	  					
	  					$scope.USER_TYPE = !angular.isUndefined(arrRentalDtl.USER_TYPE)?arrRentalDtl.USER_TYPE:"";
	  					if($scope.USER_TYPE != "MKT"){
	  						$scope.disableForeclosureSection = true;
	  					}
	  					
	  					$scope.disabeldInitiateBtn();//1.0.0.10
	  					//$scope.getPaymentDtl();//1.0.0.10
	  					
	  					// Start 1.0.0.15
	  					$scope.RSD_FLAG = arrRentalDtl.RSD_FLAG!=null ? arrRentalDtl.RSD_FLAG : "";
	  					$scope.RESTRUCTURE_TYPE = arrRentalDtl.RESTRUCTURE_TYPE!=null ? arrRentalDtl.RESTRUCTURE_TYPE : "";
	  					$scope.EXTENSION_PERIOD = arrRentalDtl.EXTENSION_PERIOD!=null ? arrRentalDtl.EXTENSION_PERIOD : "";
	  					$scope.DOC_REQUIRED = arrRentalDtl.DOC_REQUIRED!=null ? arrRentalDtl.DOC_REQUIRED : "";
	  					// End 1.0.0.15 
	  					
	  					$scope.ANNUAL_USAGE = arrRentalDtl.ANNUAL_USAGE_IN_KMS!=null ?arrRentalDtl.ANNUAL_USAGE_IN_KMS : "0.00";
	  					$scope.EXCESS_KM = arrRentalDtl.EXTRA_PER_KM_CHARGE!=null? arrRentalDtl.EXTRA_PER_KM_CHARGE : "0.00";
	  					$scope.MORATORIUM = arrRentalDtl.MORATORIOUM_IN_MNTHS!=null ? arrRentalDtl.MORATORIOUM_IN_MNTHS : "0";
	  					
	  					$scope.MORATORIUM_WO_INT_IN_MNTHS = arrRentalDtl.MORATORIUM_WO_INT_IN_MNTHS!=null ? arrRentalDtl.MORATORIUM_WO_INT_IN_MNTHS : "0"; //added by sunny
	  					
	  					$scope.RSD = arrRentalDtl.RENTAL_START_DATE!=null ? arrRentalDtl.RENTAL_START_DATE : "";
	  					$scope.START_DATE = arrRentalDtl.QUOTE_START_DATE!=null?arrRentalDtl.QUOTE_START_DATE :jsonStr.businessDate; 
	  					
	  					$scope.PRODUCT_TYPE1 = !angular.isUndefined(arrRentalDtl.PRODUCT_ID)?arrRentalDtl.PRODUCT_ID:"0";
	  					if($scope.PRODUCT_TYPE1!="")
	  						$scope.getSchemeList();
	  					$scope.SCHEME1=  !angular.isUndefined(arrRentalDtl.SCHEME_ID)?arrRentalDtl.SCHEME_ID:"0";
	  					
	  					if($scope.SCHEME1!="")
	  						$scope.calculateEndDate(arrRentalDtl); 
	  					
	  					
	  				
	  					
	};
	//1.0.0.13 start
	$scope.setSelectBoxTitle=function(){
		if($scope.AGREEMENT_SIGNING_TYPE!="0"){
			angular.forEach($scope.AGREEMENT_SIGN_TYPE, function(objData){
				if(objData.ID==$scope.AGREEMENT_SIGNING_TYPE){
				document.getElementById('AGREEMENT_SIGNING_TYPE').title=objData.NAME;
				}
		   });
		}else{
			document.getElementById('AGREEMENT_SIGNING_TYPE').title="";
		}
	
	};//1.0.0.13 end
	
	// Start 1.0.0.15
	// Agreement Signing Type logic for Restructuring
	$scope.isAgreementSigningTypeDisabled = function(){
		// Transfer without changes in T&C (RESTRUCTURE_TYPE='1000000002')
		if(screenViewMode=="V"){
			return true;
		}
		if($scope.RESTRUCTURE_TYPE == '1000000001' ){
			return false; // enable
		}else if($scope.RESTRUCTURE_TYPE == '1000000003' &&  $scope.EXTENSION_PERIOD == '1000000001'){
			return false;
		}else{
			return true;
		}
	};
	
	$scope.updateAgreementSigningType = function(){
		// This function will handle changes to Agreement Signing Type
		// The updated value will be saved when the quotation is saved
		// The Restructuring screen will read this value from the database
		$scope.setSelectBoxTitle();
	};
	// End 1.0.0.15

	
	
	$scope.ajaxResponseMethod = function(arrRentalDtl)
	{ 
			if(!angular.isUndefined(arrRentalDtl)){
    		$scope.EXPIRING_DATE = !angular.isUndefined(arrRentalDtl.QUOTATION_EXPIRY_DATE)?arrRentalDtl.QUOTATION_EXPIRY_DATE :""; //1.0.0.1
			$scope.LEASE_PERIOD = arrRentalDtl.LEASE_PERIOD_IN_MNTHS!=null?arrRentalDtl.LEASE_PERIOD_IN_MNTHS:"0";
			$scope.rental_cycle = !angular.isUndefined(arrRentalDtl.BILLING_CYCLE_ID)?arrRentalDtl.BILLING_CYCLE_ID:"0";
			if(Number($scope.RENTAL_FREQUENCY.length)>0)
				{$scope.rental_freq = !angular.isUndefined(arrRentalDtl.RENTAL_CYCLE_ID)?arrRentalDtl.RENTAL_CYCLE_ID:"0";}
			else{$scope.rental_freq = "0";}
			$scope.rental_type = !angular.isUndefined(arrRentalDtl.RENTAL_TYPE_ID)?arrRentalDtl.RENTAL_TYPE_ID:"0";
			$scope.RENTAL_MODE_VALUE = !angular.isUndefined(arrRentalDtl.RENTAL_MODE_ID)?arrRentalDtl.RENTAL_MODE_ID:"0";
			$scope.AGREEMENT_TYPE_VALUE = !angular.isUndefined(arrRentalDtl.AGREEMENT_TYPE_ID)?arrRentalDtl.AGREEMENT_TYPE_ID:"0";
			$scope.AGREEMENT_SIGNING_TYPE = !angular.isUndefined(arrRentalDtl.AGREEMENT_SIGNING_TYPE)?arrRentalDtl.AGREEMENT_SIGNING_TYPE:"0";//1.0.0.13
			$scope.setSelectBoxTitle();//1.0.0.13
			$scope.ANNUAL_USAGE = !angular.isUndefined(arrRentalDtl.ANNUAL_USAGE_IN_KMS)?arrRentalDtl.ANNUAL_USAGE_IN_KMS:"0.00";
			$scope.frnaking_type = !angular.isUndefined(arrRentalDtl.FRANKING_TYPE_ID)?arrRentalDtl.FRANKING_TYPE_ID:"0";
			$scope.FRANKING_CHARGE_AMOUNT= arrRentalDtl.FRANKING_CHRG_AMOUNT!=null?arrRentalDtl.FRANKING_CHRG_AMOUNT:"0.00";
			$scope.AUTO_FRANKING_PER= arrRentalDtl.AUTO_FRANKING_PER!=null?arrRentalDtl.AUTO_FRANKING_PER:"0.00";
			temp_autoFrankingPer = $scope.AUTO_FRANKING_PER;
			$scope.getFrankingAmount("autoPopulate");
			$scope.RV_TYPE  = !angular.isUndefined(arrRentalDtl.RESIDUAL_TYPE_ID)?arrRentalDtl.RESIDUAL_TYPE_ID:"0";
			$scope.getRVSlabType();
			
			angular.element('#RV_SLAB1').val(!angular.isUndefined(arrRentalDtl.MARKET_SLAB_ID)?arrRentalDtl.MARKET_SLAB_ID:""); 
			angular.element('#fixed_rv').val(!angular.isUndefined(arrRentalDtl.FIXED_PERCENTAGE)?arrRentalDtl.FIXED_PERCENTAGE:"");
			
			
			
			$scope.market ={PERCENT:!angular.isUndefined(arrRentalDtl.MARKET_PERCENTAGE)?arrRentalDtl.MARKET_PERCENTAGE:"0.00",RV_SLAB1: !angular.isUndefined(arrRentalDtl.MARKET_SLAB_ID)?arrRentalDtl.MARKET_SLAB_ID:"0"};
			$scope.fixed ={CALC:!angular.isUndefined(arrRentalDtl.FIXED_CALCULATION_ON_ID)?arrRentalDtl.FIXED_CALCULATION_ON_ID:"0"};
			
			// 1.0.0.16 start
			
			if($scope.fixed.CALC == "1000000008"){
			$scope.isDisabledFixedPercentage = true;
			$scope.isResidualValueEnabled = true;
			}else{
			}
			
			// 1.0.0.16 end

			$scope.hilightedResidualValueFlag = !angular.isUndefined(arrRentalDtl.HIGHLIGHT_AMOUNT)?arrRentalDtl.HIGHLIGHT_AMOUNT:'N';
			$scope.RESIDUAL_VALUE1 = arrRentalDtl.RESIDUAL_VALUE!=null?arrRentalDtl.RESIDUAL_VALUE:"0.00";
			$scope.PERCENT_VALUE = arrRentalDtl.FIXED_PERCENTAGE!=null?arrRentalDtl.FIXED_PERCENTAGE:"0.00";  // 1.0.0.58
			if($scope.hilightedResidualValueFlag=='Y'){
				$scope.BColor = "red";
				$scope.CustomStyle = {
						color: $scope.BColor
		            };
			
			}
			else
				{
					$scope.BColor = "black";
					$scope.CustomStyle = {
			                color: $scope.BColor
					};
				}
			$scope.CHARGE_TYPE_VALUE =  !angular.isUndefined(arrRentalDtl.INT_ON_ADV_CHRG_TYPE_ID)? arrRentalDtl.INT_ON_ADV_CHRG_TYPE_ID:"0" ;
			$scope.NO_OF_DAYS = arrRentalDtl.INT_ON_ADV_DAYS!=null?arrRentalDtl.INT_ON_ADV_DAYS:"";
			$scope.INTEREST_RATE = arrRentalDtl.INT_RATE_OF_INT_ON_ADV!=null?arrRentalDtl.INT_RATE_OF_INT_ON_ADV:"0.00";
			$scope.AMOUNT_ADVANCE= arrRentalDtl.ADV_AMNT_OF_INT_ON_ADV!=null?arrRentalDtl.ADV_AMNT_OF_INT_ON_ADV:"0.00";
			$scope.INTEREST_AMT = arrRentalDtl.INT_AMNT_OF_INT_ON_ADV!=null?arrRentalDtl.INT_AMNT_OF_INT_ON_ADV:"";
			 if(jsonStr.quotationDetails != undefined)
				 {
					arrMaintDtl = jsonStr.quotationDetails.GET_MAINTENANCE_DTL;
					arrMaintDetails = !angular.isUndefined(arrMaintDtl[0])?arrMaintDtl[0]:"";
					arrExtWarrenty = jsonStr.quotationDetails.GET_EXT_WARR_DTL;
					arrMonthlyCharges = jsonStr.quotationDetails.GET_MONTHLY_CHARGES;
					arrAMCDtl = jsonStr.quotationDetails.GET_AMC_DTL;
					$scope.MAINTENANCE_SCHEDULE_COL=!angular.isUndefined(jsonStr.quotationDetails.GET_MAINTENANCE_SCHEDULE_COL)?jsonStr.quotationDetails.GET_MAINTENANCE_SCHEDULE_COL:[];//1.0.0.1
					$scope.TYRE_SCHEDULE_COL=!angular.isUndefined(jsonStr.quotationDetails.GET_TYRE_SCHEDULE_COL)?jsonStr.quotationDetails.GET_TYRE_SCHEDULE_COL:[]; //1.0.0.1
					$scope.BATTERY_SCHEDULE_COL=!angular.isUndefined(jsonStr.quotationDetails.GET_BATTERY_SCHEDULE_COL)?jsonStr.quotationDetails.GET_BATTERY_SCHEDULE_COL:[]; //1.0.0.1
				 }
			 arrMaintDetails = !angular.isUndefined(arrMaintDtl[0])?arrMaintDtl[0]:"";
			$scope.populateData();
			
			
			if(assetCatId == "1000000003")
				{
					$scope.CHARGE_TYPE_VALUE='1000000001';
					$scope.disableEquipFlag = true;
					$scope.ANNUAL_USAGE = 0;
					$scope.EXCESS_KM = 0;
					
				}

			$scope.isChargeType();
			$scope.BILLING_TYPE_ID = !angular.isUndefined(arrRentalDtl.BILLING_TYPE_ID)?arrRentalDtl.BILLING_TYPE_ID:"0";
			$scope.LEASE_ITC_AVAIL_OPTIONS = !angular.isUndefined(arrRentalDtl.LEASE_ITC_AVAIL_OPTIONS)?arrRentalDtl.LEASE_ITC_AVAIL_OPTIONS:"1000000001";
			$scope.LEASE_ITC_AVAIL_OPTIONS_FLG = !angular.isUndefined(arrRentalDtl.LEASE_ITC_AVAIL_OPTIONS_FLG)?arrRentalDtl.LEASE_ITC_AVAIL_OPTIONS_FLG:"N";
			$scope.RENTAL_LOGIC = !angular.isUndefined(arrRentalDtl.RENTAL_LOGIC)?arrRentalDtl.RENTAL_LOGIC:"1000000001";//1.0.0.9
			$scope.RENTAL_LOGIC_FLAG = !angular.isUndefined(arrRentalDtl.RENTAL_LOGIC_FLAG)?arrRentalDtl.RENTAL_LOGIC_FLAG:"N";//1.0.0.9
			$scope.enableFMSCharge();
			}
	};
	var arrMaintDetails ;
	
	$scope.populateData = function(){
		
			
			if(!$scope.isMaintEnable){
					$scope.MAINTENANCE_SLAB = "0";//!angular.isUndefined(arrMaintDetails.MAINT_SLAB_ID) ?arrMaintDetails.MAINT_SLAB_ID:"";  //1.0.0.1
					$scope.TOTAL_MAINTENANCE = "";//!angular.isUndefined(arrMaintDetails.AMOUNT) ?arrMaintDetails.AMOUNT:"0.00"; //1.0.0.1
					$scope.POOL_CAR = !angular.isUndefined(arrMaintDetails.POOL_CAR_APPLICABLE) ?arrMaintDetails.POOL_CAR_APPLICABLE:"N";
			}
			if(!$scope.isAMCEnable){
				$scope.AMC_details = !angular.isUndefined(arrMaintDetails.AMC_APPLICABLE)?arrMaintDetails.AMC_APPLICABLE:"N";
				
				$scope.amcDetails = arrAMCDtl;
			}
			$scope.EXTENDED_WARRANTY_IN_FMS = !angular.isUndefined(arrMaintDetails.EXTENDED_WARRANTY_IN_FMS)?arrMaintDetails.EXTENDED_WARRANTY_IN_FMS:"N";
			
			$scope.EXT_WARR = !angular.isUndefined(arrMaintDetails.EXTENDED_WARRANTY_APPLICABLE)?arrMaintDetails.EXTENDED_WARRANTY_APPLICABLE:"N";
			$scope.getExtendedWarrTable();
			$scope.extDetails = arrExtWarrenty;
			for(var k=0;k<arrAddOnServices.length;k++)
			{
				 $("#RENTAL_SERVICE_ID"+k).val(arrAddOnServices[k].RENTAL_SERVICE_ID!=null && arrAddOnServices[k].RENTAL_SERVICE_ID!=undefined?arrAddOnServices[k].RENTAL_SERVICE_ID:"");
				 $("#SERVICE_ID"+k).val(arrAddOnServices[k].SERVICE_ID!=null && arrAddOnServices[k].SERVICE_ID!=undefined?arrAddOnServices[k].SERVICE_ID:"");    
				$("#SERVICE_NAME"+k).val(arrAddOnServices[k].SERVICE_NAME!=null && arrAddOnServices[k].SERVICE_NAME!=undefined?arrAddOnServices[k].SERVICE_NAME:"");
				$("#APPLICABILITY"+k).val(arrAddOnServices[k].APPLICABILITY!=null && arrAddOnServices[k].APPLICABILITY!=undefined?arrAddOnServices[k].APPLICABILITY:"");
				$("#MASTER_AMOUNT"+k).val(arrAddOnServices[k].MASTER_AMOUNT!=null && arrAddOnServices[k].MASTER_AMOUNT!=undefined?(arrAddOnServices[k].MASTER_AMOUNT!="0"?arrAddOnServices[k].MASTER_AMOUNT:"0.00"):"");
				
				if(arrAddOnServices[k].APPLICABILITY=='1000000001'){
					$("#QTY"+k).prop('disabled', true); 
					$("#SERVICE_AMOUNT"+k).prop('disabled', true); 
					 $("#REMARKS"+k).prop('disabled', true); 
				} else{
					
					$("#QTY"+k).val(arrAddOnServices[k].QTY!=null && arrAddOnServices[k].QTY!=undefined?arrAddOnServices[k].QTY:"");
					$("#SERVICE_AMOUNT"+k).val(arrAddOnServices[k].SERVICE_AMOUNT!=null && arrAddOnServices[k].SERVICE_AMOUNT!=undefined?(arrAddOnServices[k].SERVICE_AMOUNT!="0"?arrAddOnServices[k].SERVICE_AMOUNT:"0.00"):"");
					 $("#REMARKS"+k).val(arrAddOnServices[k].REMARKS!=null && arrAddOnServices[k].REMARKS!=undefined?arrAddOnServices[k].REMARKS:"");
				}
			}
			if(!angular.isUndefined(valueAddedStr)){
			
				for(var k=0,i=0;k<arrAddOnServices.length;k++,i++)
				{
					if(valueAddedStr[i])
					{
						valueAddedStr[i].RENTAL_SERVICE_ID = arrAddOnServices[k].RENTAL_SERVICE_ID!=undefined && arrAddOnServices[k].RENTAL_SERVICE_ID!=null?arrAddOnServices[k].RENTAL_SERVICE_ID:"";
						valueAddedStr[i].SERVICE_ID = arrAddOnServices[k].SERVICE_ID!=undefined && arrAddOnServices[k].SERVICE_ID!=null?arrAddOnServices[k].SERVICE_ID:"";    
						valueAddedStr[i].SERVICE_NAME =arrAddOnServices[k].SERVICE_NAME!=undefined && arrAddOnServices[k].SERVICE_NAME!=null?arrAddOnServices[k].SERVICE_NAME:"";
						valueAddedStr[i].QTY =arrAddOnServices[k].QTY!=undefined && arrAddOnServices[k].QTY!=null?arrAddOnServices[k].QTY:"";
						valueAddedStr[i].MASTER_AMOUNT =arrAddOnServices[k].MASTER_AMOUNT!=undefined && arrAddOnServices[k].MASTER_AMOUNT!=null?arrAddOnServices[k].MASTER_AMOUNT:"0.00";
						valueAddedStr[i].SERVICE_AMOUNT =arrAddOnServices[k].SERVICE_AMOUNT!=undefined && arrAddOnServices[k].SERVICE_AMOUNT!=null?arrAddOnServices[k].SERVICE_AMOUNT:"0.00";
						valueAddedStr[i].REMARKS =arrAddOnServices[k].REMARKS!=undefined && arrAddOnServices[k].REMARKS!=null?arrAddOnServices[k].REMARKS:"";
						//1.0.0.1 start
							if(arrAddOnServices[k].SERVICE_ID!=undefined && arrAddOnServices[k].SERVICE_ID!=null && ((arrAddOnServices[k].SERVICE_ID=="1000000002"  && $scope.ALLOW_LEASE_TYRE=="N") || (arrAddOnServices[k].SERVICE_ID=="1000000003" && $scope.ALLOW_LEASE_BATTERY=="N")))
						{
						    valueAddedStr[i].APPLICABILITY ="1000000001";
						    valueAddedStr[i].QTY="";
							valueAddedStr[i].MASTER_AMOUNT="";
							valueAddedStr[i].SERVICE_AMOUNT="";
							valueAddedStr[i].REMARKS="";
						}
					else
						{
						  valueAddedStr[i].APPLICABILITY =arrAddOnServices[k].APPLICABILITY!=undefined && arrAddOnServices[k].APPLICABILITY!=null?arrAddOnServices[k].APPLICABILITY:"";
						}// 1.0.0.1 end
							if(valueAddedStr[i].APPLICABILITY=="1000000001")
							{
							  valueAddedStr[i].quantityEnable="N";
							  valueAddedStr[i].amountEnable="N";
							  valueAddedStr[i].remarksEnable="N";
							}
					     
						
					}
				}
			
			}
			if(valueAddedStr!="")
				{
				$scope.valueAdded = valueAddedStr;
						
				}
			
			
			$scope.monthlyCharges = arrMonthlyCharges;
			
	};
	
	
	
	$scope.getSchemeList = function(){
    	$scope.SCHEME=[];
    	
    	$scope.SCHEME1 = "0";
    	
    	var arrSchemeMaster = arrLOCMaster.schemeMaster;
			for(var i =0;i< arrSchemeMaster.length;i++){
				if(arrSchemeMaster[i].POS == $scope.PRODUCT_TYPE1){
					$scope.SCHEME.push({
							 'ID':arrSchemeMaster[i].SCHEMEID,
							 'VALUE':arrSchemeMaster[i].SCHEMENAME
					});
				}
			}
    };
    var isMonthlyChargeAddedForAgreementType=false;
	
    
    var maxDaysInLease=0;	
    
    $scope.calculateEndDate= function(arrRentalDtl){
    	
   	 $scope.RENTAL_FREQUENCY=[];
   	$scope.rental_freq = "0";
   	 $scope.SERVICE_NAMES=[];
   	 $scope.masterChargeId=[];    	 
   	$scope.allowAutomaticTransfer="N";
   	 var schemeId = $scope.SCHEME1;
   	 
   	$scope.expiryDays="";
   	 
   	 var noOfDays=0;
   
   	 for(var i =0;i<arrValidationValues.length;i++){
   		 if(arrValidationValues[i].SCHEMEID==schemeId){
   			 noOfDays=arrValidationValues[i].QUOTATION_EXPIRY_DAYS;
   			 $scope.expiryDays=arrValidationValues[i].QUOTATION_EXPIRY_DAYS;
   			 dateDiffLogic = arrValidationValues[i].DAY_DIFF_LOGIC;
   			 maxDaysInLease = arrValidationValues[i].MAX_LEASE_PERIOD_IN_MNTHS; 
   			 
   			 $scope.ALLOW_LEASE_TYRE = arrValidationValues[i].ALLOW_LEASE_TYRE;
   			 $scope.ALLOW_LEASE_BATTERY = arrValidationValues[i].ALLOW_LEASE_BATTERY;
   			 
   		 }
   	 }
   	 if(!angular.isUndefined($scope.START_DATE) && $scope.START_DATE !=null){//1.0.0.1
   		 var startDate = new Date($scope.START_DATE);
   		 var endDate = new Date(startDate.getTime() + noOfDays * 24 * 60 * 60 * 1000);
	    	 $scope.EXPIRING_DATE = $filter('date')(endDate, "dd-MMM-yyyy");
	    	 $scope.TEMP_EXPIRING_DATE=$scope.EXPIRING_DATE;
    	} else
   	 {
    		$scope.EXPIRING_DATE = null;
    		$scope.TEMP_EXPIRING_DATE=null;
   	 }
   	//1.0.0.1 start
   	 
	   	 if($scope.ALLOW_LEASE_TYRE=="Y")
	   		 {
		   		if($scope.TYRE_SCHEDULE_COL.length > 0){
		 			$scope.removeMain_Tyre_battery($scope.TYRE_SCHEDULE_COL);
		 		 }
	   		    $scope.getTyreTable();
	   		 }
	   	if($scope.ALLOW_LEASE_BATTERY=="Y")
			 {
		   		if($scope.BATTERY_SCHEDULE_COL.length>0){
					$scope.removeMain_Tyre_battery($scope.BATTERY_SCHEDULE_COL);
				}
	   		    $scope.getBatteryTable();
			 }
   	 
   	 //1.0.0.1 end
   	 if(schemeId!=0 && !angular.isUndefined(schemeId)){
   		 $scope.isLeasePeriodDisable = false;
   		 if(schemeId!="1200000303" && schemeId!="1200000302")
   			 {
   			 $scope.isAMCEnable=true;
   			 $scope.AMC_details='N';
   			 }
   		 if(!angular.isUndefined($scope.LEASE_PERIOD) && $scope.LEASE_PERIOD!=null){
   			$scope.CheckLeasePeriod();
   		 }
   	 $.ajax({
	  		 url: "getSchemeDependentDetails_Trf.sprg?isAjaxCalling=Y", //1.0.0.12 added  isAjaxCalling=Y
	  		 data: {"schemeId": schemeId,
	  			    "lease_startDate": $scope.START_DATE,
 			        "charge_list":"",
 			        "viewModeFlag" :screenViewMode!="V"?"N":"Y" //1.0.0.3
	  		 },
	  		 type: "GET" ,
	  			success: function(response) 
	  			{
		  			if(response!=null && response!="") 
		  			{
		  				
		  				var jsonStr = JSON.parse(response);
		  				if(jsonStr.Status=="SessionExpired") //1.0.0.12
  						{
  							window.location.href = 'llm/ReLogin.jsp?unknownError=Your session has been killed. Please login again.';
  							return;
  						}
		  				var schemeDependentInfo="";
		  				var arrRentalFreq = jsonStr.rentalFrequencyMaster;
		  				if(!angular.isUndefined(jsonStr.schemeDependentInfo))
		  						schemeDependentInfo = JSON.parse(jsonStr.schemeDependentInfo);
		  				var monthlyChargeStr = jsonStr.monthyChargeMaster.MONTHLY_CHARGES;
		  				for(var i=0; i< arrRentalFreq.length;i++){
		  					 $scope.RENTAL_FREQUENCY.push ({
 								 'ID': arrRentalFreq[i].ID,
 								 'VALUE': arrRentalFreq[i].DISPLAYNAME
 							 });
		  				}
		  				
		  				
		  				isAMCEnableFlag = schemeDependentInfo.PC_AMC_ENABLE;
		  				
		  				if(isAMCEnableFlag=="Y"){
		  					$scope.isAMCEnable = false;
		  					$scope.AMC_details='N';
		  					$scope.getAMCTable();
		  					
		  					
		  				} else {
		  					$scope.isAMCEnable = true;
		  					$scope.amcDetails=[];
		  					$scope.AMC_details='N';
		  					
		  				}
		  				isMaintEnableFlag = (!angular.isUndefined(schemeDependentInfo.PC_MAINTENANCE_ENABLE))?schemeDependentInfo.PC_MAINTENANCE_ENABLE:"N";
		  				
		  				if(isMaintEnableFlag=="Y"){
		  					$scope.isMaintEnable = false;
		  					$scope.MAINTENANCE_SLAB = '0';
		  					$scope.POOL_CAR = 'N';
		  					$scope.TOTAL_MAINTENANCE = "0.00";
		  					$scope.firstRowFlag = true;
		  					$scope.getMaintenanceTable(); //1.0.0.1 start
		  				}else{
		  					$scope.isMaintEnable = true;
		  					$scope.MAINTENANCE_SLAB="0";
		  					$scope.POOL_CAR="N";
		  					angular.element('#total_maintenence').val("0.00");
		  					
		  				}
		  				if(!angular.isUndefined(schemeDependentInfo.ValueAddedService)){
		  						valueAddedStr = schemeDependentInfo.ValueAddedService;
		  						
		  				}
		  				
		  				$scope.valueAddedServices(valueAddedStr);  
		  				
		  				if(monthlyChargeStr=="" || angular.isUndefined(monthlyChargeStr)){
		  					document.getElementById('message').style.display = 'block';
		  					$scope.message = "No Monthly Charges found for the scheme selected";
		  					document.getElementById('monthlyChargesAdd').disabled = true;
		  				} else {
		  					document.getElementById('message').style.display = 'none';
		  					document.getElementById('monthlyChargesAdd').disabled = false;
		  					$scope.SERVICE_NAMES.push ({
		        				'ID':"0",
		        				'NAME':"SELECT"
		        			});
	    	  				for(var i=0;i<monthlyChargeStr.length;i++){
			        			$scope.SERVICE_NAMES.push ({
			        				'ID':monthlyChargeStr[i].CHARGE_ID,
			        				'NAME':monthlyChargeStr[i].CHARGE_NAME
			        			});
			        			$scope.masterChargeId.push ({
			        				'ID':monthlyChargeStr[i].CHARGE_ID,
			        				'NAME':monthlyChargeStr[i].MASTER_DRIVEN_AMOUNT
			        			}); 	
			        			if(monthlyChargeStr[i].CHARGE_ID=='1200000010'){
			        				
			        				$scope.allowAutomaticTransfer="Y";
			        				
			        			}
	    	  				}
		  					
		  					
		  					angular.forEach($scope.monthlyCharges, function(selected){
		  			        
		  			           	 if(removeChargeIdStr!=""){
		  			           		 if(selected.QUOTATION_CHARGE_ID!="")
		  			           		 removeChargeIdStr=removeChargeIdStr + "^"+ selected.QUOTATION_CHARGE_ID;
		  			           	 }
		  			           	 else {
		  			           		 removeChargeIdStr = selected.QUOTATION_CHARGE_ID;
		  			           	 }
		  			           
		  			        }); 
		  					
		  					$scope.monthlyCharges = [];
		  				}
		  				
		  				$scope.$apply();
		  				$scope.ajaxResponseMethod(arrRentalDtl);
		  				$scope.createMonthlyCharges("N");
		  				$scope.$apply();
		  			}
	  			},
	  		 error: function(response){
	  			 
	  		 }
   	 }); 
   	 }
   	 else {
   		$scope.isLeasePeriodDisable = true;
   		$scope.valueAdded = [];
   		$scope.monthlyCharges=[];
   		$scope.ajaxResponseMethod(arrRentalDtl);
   		
   	 }
   	var arrRentalFrequency = arrLOCMaster.rentalFrequencyMaster;
   
    };
    
    $scope.valueAddedServices = function(serviceString){
   	 $scope.valueAdded = serviceString;
   	 for(var i=0; i <$scope.valueAdded.length;i++){
   		$scope.valueAdded[i].APPLICABILITY="0";
      
   		
   		if(i==0)
   			{
	   			if($scope.ALLOW_LEASE_TYRE == "N")
	      		{
		      		$scope.valueAdded[i].MASTER_AMOUNT="0.00";
		      		$scope.valueAdded[i].SERVICE_AMOUNT="0.00";
	      		}
   			}
   		else if(i==1)
			{
	   			if($scope.ALLOW_LEASE_BATTERY == "N")
		  		{
			  		$scope.valueAdded[i].MASTER_AMOUNT="0.00";
			  		$scope.valueAdded[i].SERVICE_AMOUNT="0.00";
		  		}
       		
			}
		
   		
   	 }
   	
   	 var valueAddedStr = $scope.valueAdded;
    };
	
 $scope.CheckLeasePeriod = function(){
 	   var schemeId = $scope.SCHEME1;
   	 
   	 for(var i =0;i<arrValidationValues.length;i++){
   		 if(arrValidationValues[i].SCHEMEID==schemeId){
   			maxDaysInLease=arrValidationValues[i].MAX_LEASE_PERIOD_IN_MNTHS;
   			
   			
   		 if($scope.RV_TYPE == "1000000001" && $scope.market.RV_SLAB1 == "1000000005")	
		 {
   			
		 }
   		 else{
   			$scope.getRVValue();
   		 }
   		
   		 }
   	 }
 	   
 	  var valueEntered =  $scope.LEASE_PERIOD;
 	   if(Number(valueEntered)>Number(maxDaysInLease)){
 		  window.alert("MAXIMUM VALUE OF LEASE PERIOD IN MONTHS CAN BE "+maxDaysInLease+".");
 		  $scope.LEASE_PERIOD="0";
 	  } 
 	
 	  angular.forEach( $scope.amcDetails, function(selected,index){
 		if(arrRentalDtl.BAL_PERIOD_TRANSFER_YR > 0){
      	if(index>0 && selected.MAINTENANCE_DTL_ID != null && selected.MAINTENANCE_DTL_ID != "" && selected.MAINTENANCE_DTL_ID != undefined)
      		{
	        		if(removeExtendedWarrentyIdStr!=null && removeExtendedWarrentyIdStr!="" && removeExtendedWarrentyIdStr!=undefined)
          		{removeExtendedWarrentyIdStr = removeExtendedWarrentyIdStr + "^"+ selected.MAINTENANCE_DTL_ID;
          		}
	            	else{
	            		 removeExtendedWarrentyIdStr = selected.MAINTENANCE_DTL_ID;
	            	}
      		
      		}
 		}
 		else{
 			if(selected.MAINTENANCE_DTL_ID != null && selected.MAINTENANCE_DTL_ID != "" && selected.MAINTENANCE_DTL_ID != undefined)
      		{
	        		if(removeExtendedWarrentyIdStr!=null && removeExtendedWarrentyIdStr!="" && removeExtendedWarrentyIdStr!=undefined)
          		{removeExtendedWarrentyIdStr = removeExtendedWarrentyIdStr + "^"+ selected.MAINTENANCE_DTL_ID;
          		}
	            	else{
	            		 removeExtendedWarrentyIdStr = selected.MAINTENANCE_DTL_ID;
	            	}
      		
      		}
 		}
	   }); 
 	
 		 $scope.getAMCTable();
 		 
 		 angular.forEach( $scope.extDetails, function(selected,index){
	        	if(index>0 && selected.MAINTENANCE_DTL_ID != null && selected.MAINTENANCE_DTL_ID != "" && selected.MAINTENANCE_DTL_ID != undefined)
	        		{
		        		if(removeExtendedWarrentyIdStr!=null && removeExtendedWarrentyIdStr!="" && removeExtendedWarrentyIdStr!=undefined)
	            		{removeExtendedWarrentyIdStr = removeExtendedWarrentyIdStr + "^"+ selected.MAINTENANCE_DTL_ID;
	            		}
		            	else{
		            		 removeExtendedWarrentyIdStr = selected.MAINTENANCE_DTL_ID;
		            	}
	        		
	        		}
		   }); //1.0.0.1 start
 		//$scope.extDetails = [];
 		//$scope.getExtendedWarrTable(); 
 		
 	
 	/* if(isMaintEnableFlag=="Y" && $scope.MAINTENANCE_SLAB!= '0' )
 	 { $scope.getMaintenanceAmount();}*/
 	
	 	if(isMaintEnableFlag=="Y")
		 {  
	 		$scope.removeMain_Tyre_battery($scope.MAINTENANCE_SCHEDULE_COL);
	 			$scope.getMaintenanceTable();
	 	 }
	 	else{
	 		if($scope.MAINTENANCE_SCHEDULE_COL.length > 0){
	 			$scope.removeMain_Tyre_battery($scope.MAINTENANCE_SCHEDULE_COL);
	 		}
	 	}
	 	
	 	
	 	 if($scope.ALLOW_LEASE_TYRE=="Y")
			 {
	 		    $scope.removeMain_Tyre_battery($scope.TYRE_SCHEDULE_COL);//added by 1.0.0.2
	 		    	$scope.getTyreTable();
			 }
	 	 //add start by 1.0.0.2
	 	 else{
	 		 if($scope.TYRE_SCHEDULE_COL.length > 0){
	 			$scope.removeMain_Tyre_battery($scope.TYRE_SCHEDULE_COL);
	 		 }
	 	 }
	 	 //add end by 1.0.0.2
	 	 
		if($scope.ALLOW_LEASE_BATTERY=="Y")
		 {
			 $scope.removeMain_Tyre_battery($scope.BATTERY_SCHEDULE_COL);//added by 1.0.0.2
			$scope.getBatteryTable();
		 }
		//add start by 1.0.0.2
		else{
			if($scope.BATTERY_SCHEDULE_COL.length>0){
				$scope.removeMain_Tyre_battery($scope.BATTERY_SCHEDULE_COL);
			}
		}
		//add end by 1.0.0.2
 	//1.0.0.1 end
 	 if($scope.EXT_WARR=="Y")
 		 {
 		  $scope.validationExtendedWaranty();
 		 }
    };

    
    $scope.recomputeMaintenanceSlab = function(){
    	if(isMaintEnableFlag=="Y" && $scope.MAINTENANCE_SLAB!= '0' ){
    		 $scope.getMaintenanceAmount();
       }
    };
    
    
$scope.createMonthlyCharges = function(removeMonthlyChargesFlag){
		 if(removeMonthlyChargesFlag=="Y")
			 {
			 if($scope.monthlyCharges!="")
			 $scope.removeAllRowsOfMonthlyCharge();
			 
			 }
	    	 if($scope.AGREEMENT_TYPE_VALUE == "1000000003"){
	    		
	    		 if($scope.allowAutomaticTransfer=="Y"){
	    			 if(removeMonthlyChargesFlag=="Y")
	    			 {
	    				 if($scope.monthlyCharges=="")
	    				 $scope.addMonthlyChargesWithAgreementtype();
	    				 }
	    			 
	    		 }
		    	 else{
		    		 if(entityId != "1000000008"){//1.0.0.8 added
		    			 alert("FOR THE SCHEME SELECTED, AUTOMATIC TRANSFER CHARGE IS NOT CONFIGURED.");
		    		 }
		    		 
		    	 }
	    	 }
	    	
	    	 
	 };
	 
	 $scope.removeAllRowsOfMonthlyCharge=function(index)
	 {
		 var bool = confirm(("DO YOU WANT TO REMOVE ALL ROWS OF MONTHLY CHARGES?").toUpperCase());
		    if(!bool)
		    {
		    	return;
		    }	
		    else{ var newDataList=[];
        angular.forEach($scope.monthlyCharges, function(selected){
        	
          
           	 if(removeChargeIdStr!=""){
           		 if(selected.QUOTATION_CHARGE_ID!="")
           		 removeChargeIdStr=removeChargeIdStr + "^"+ selected.QUOTATION_CHARGE_ID;
           	 }
           	 else {
           		 removeChargeIdStr = selected.QUOTATION_CHARGE_ID;
           	 }
            
        }); 
        $scope.monthlyCharges = newDataList;
        $scope.monthlyCharges = [];
		    } 
	 };
	 
	 
	 
	 
	$scope.getFrankingAmount = function(mode){
			if($scope.frnaking_type =="1000000002" )
				{
					
					if(mode == "manual" || mode == "setZero")
					{
						$scope.FRANKING_CHARGE_AMOUNT = "0.00";
						
					}
					
					else if (mode == "autoPopulate")
						{
						$scope.FRANKING_CHARGE_AMOUNT = !angular.isUndefined(arrRentalDtl.FRANKING_CHRG_AMOUNT)?arrRentalDtl.FRANKING_CHRG_AMOUNT:"";
						}
					$scope.isAuto=false;
				} 
			else 
			{
				if(mode == "manual" )
					{
						if($scope.frnaking_type != arrRentalDtl.FRANKING_TYPE_ID)
						{
							$scope.FRANKING_CHARGE_AMOUNT="0.00";
							
						}
						else
							{
								if($scope.LEASE_PERIOD == arrRentalDtl.LEASE_PERIOD_IN_MNTHS && $scope.rental_freq == arrRentalDtl.RENTAL_CYCLE_ID && $scope.RENTAL_MODE_VALUE == arrRentalDtl.RENTAL_MODE_ID && $scope.MORATORIUM == arrRentalDtl.MORATORIOUM_IN_MNTHS)
								{
									$scope.FRANKING_CHARGE_AMOUNT = !angular.isUndefined(arrRentalDtl.FRANKING_CHRG_AMOUNT)?arrRentalDtl.FRANKING_CHRG_AMOUNT:"";
								}
							}
						
					}
				else if (mode == "setZero")
					{
						if($scope.frnaking_type == arrRentalDtl.FRANKING_TYPE_ID)
						{
							$scope.FRANKING_CHARGE_AMOUNT="0.00";
						
								if($scope.LEASE_PERIOD == arrRentalDtl.LEASE_PERIOD_IN_MNTHS && $scope.rental_freq == arrRentalDtl.RENTAL_CYCLE_ID && $scope.RENTAL_MODE_VALUE == arrRentalDtl.RENTAL_MODE_ID && $scope.MORATORIUM == arrRentalDtl.MORATORIOUM_IN_MNTHS)
								{
									$scope.FRANKING_CHARGE_AMOUNT = !angular.isUndefined(arrRentalDtl.FRANKING_CHRG_AMOUNT)?arrRentalDtl.FRANKING_CHRG_AMOUNT:"";
								}
							}
					
					}
				else if (mode == "autoPopulate")
				{
				$scope.FRANKING_CHARGE_AMOUNT = !angular.isUndefined(arrRentalDtl.FRANKING_CHRG_AMOUNT)?arrRentalDtl.FRANKING_CHRG_AMOUNT:"";
				}
				$scope.isAuto=true;
			}
		};
	

		

	
		
$scope.enableFMSCharge = function()
{
	if($scope.BILLING_TYPE_ID == "1000000001")
		{
		$scope.FMS_FRANKING_CHRG_AMNT = !angular.isUndefined(arrRentalDtl.FMS_FRANKING_CHRG_AMNT)?arrRentalDtl.FMS_FRANKING_CHRG_AMNT:"0.00";
		$scope.FMS_OTHER_CHRG_AMNT = !angular.isUndefined(arrRentalDtl.FMS_OTHER_CHRG_AMNT)?arrRentalDtl.FMS_OTHER_CHRG_AMNT:"0.00";
		$scope.isFMSChargeEnable = false;
		
		}
	else
		{
		$scope.isFMSChargeEnable = true;
		$scope.FMS_FRANKING_CHRG_AMNT = "0.00";
		$scope.FMS_OTHER_CHRG_AMNT = "0.00";
		
		$scope.EXTENDED_WARRANTY_IN_FMS = "N";
		}
			
}

	
	
		
		 $scope.getRVSlabType = function(){
			   var selectedValue = $scope.RV_TYPE;
			   if(selectedValue == '1000000001'){ 
				   $scope.isDisabledMarket=false;
				   
				   if($scope.market!=undefined)
					   {
					    $scope.market.RV_SLAB1="0"; 
					    $scope.market.PERCENT="0.00";
					   }
				   else
					   {
					    $scope.market= {RV_SLAB1:"0",PERCENT:"0.00"};
					   }
				   
				   $scope.isDisabledFixed = true;
				   
				   $scope.PERCENT_VALUE="0.00";
				   
				   $scope.fixed= {CALC:"0.00"};
				   
				   $scope.RESIDUAL_VALUE1="0.00";
				  
				   
				   
			   } else if (selectedValue == '1000000002'){ 
				   $scope.isDisabledFixed = false;
				   $scope.isDisabledMarket=true;
				   $scope.fixed= {CALC:"0"};
				  
				   
				   if($scope.market!=undefined)
				   {
				    $scope.market.RV_SLAB1="0"; 
				    $scope.market.PERCENT="0.00";
				   }
			   else
				   {
				    $scope.market= {RV_SLAB1:"0",PERCENT:"0.00"};
				   }
				   
				   $scope.RESIDUAL_VALUE1="0.00";
				
			   }
			   else{
					   $scope.isDisabledMarket=true;
					   
					   if($scope.market!=undefined)
					   {
					    $scope.market.RV_SLAB1="0"; 
					    $scope.market.PERCENT="0.00";
					   }
				   else
					   {
					    $scope.market= {RV_SLAB1:"0",PERCENT:"0.00"};
					   }
					   
					   $scope.isDisabledFixed = true;
					  
					   $scope.PERCENT_VALUE="0.00";
					  $scope.fixed= {CALC:"0"};
					 
					   $scope.RESIDUAL_VALUE1="0.00"; 
					   $scope.BColor = "black";
	 				   $scope.CustomStyle = {  color: $scope.BColor };
					  
				   
				   
			   }
			   
		   };  
		   
		   // 1.0.0.16 start
		   $scope.handleRVCalculationChange = function(){
			   var rvCalcValue = $scope.fixed.CALC;
			   if(rvCalcValue == "1000000008"){ // Replace with actual ex-showroom ID
				   // Disable fixed RV % and clear its value
				   $scope.PERCENT_VALUE = "0.00";
				   $scope.isDisabledFixedPercentage = true;
				   // Enable residual value field
				   $scope.isResidualValueEnabled = true;
				   $scope.RESIDUAL_VALUE1 = "0.00";
			   } else {
				   // Enable fixed RV % when not ex-showroom
				   $scope.isResidualValueEnabled = false;
				   $scope.isDisabledFixedPercentage = false;
				   $scope.RESIDUAL_VALUE1 = "0.00";
			   }
		   };
		   
		   // 1.0.0.16 end


		     $scope.getRVValue = function(){
		    	 var rv_type = $scope.RV_TYPE;
		    	 
		    	 var moratorium=$scope.MORATORIUM;
		    	 var annualUsage=$scope.ANNUAL_USAGE;
		    	 
		    	 var rv_slab = $scope.isDisabledMarket?"":angular.element('#RV_SLAB1').val();
		    		 
		    	 var leasePeriod = Number(Number($scope.LEASE_PERIOD) +  Number($scope.MORATORIUM));
		    	 if(!angular.isUndefined($scope.LEASE_PERIOD)){
		        	 var lease_startDate = $scope.START_DATE;
		        	 var rv_fixed_percentage =  $scope.isDisabledFixed?"": (!angular.isUndefined(angular.element('#fixed_rv').val())?angular.element('#fixed_rv').val():"");
		        	 
		        	 var rv_fixed_calc=  $scope.isDisabledFixed?"":angular.element('#rv_calculation').val();
		        	
		        	 if(rv_fixed_percentage >100)//1.0.0.5 changes
		        		 {
		        		 alert("RV FIXED PERCENT SHOULD BE LESS THAN OR EQUAL TO 100.");//1.0.0.5 changes
		        		 $scope.PERCENT_VALUE	="0.00";
		        		 return false;
		        		 }
		        	 var decimalCheck = rv_fixed_percentage.split('.');

		                if(decimalCheck.length > 2) {
		                	alert("RV FIXED PERCENT CAN NOT CONTAIN MORE THAN ONE DECIMAL.");
			        		 $scope.PERCENT_VALUE	="0.00";
		                }
		                
		        	 if(!$scope.isDisabledFixed && rv_fixed_calc=="0"){
		        		 return false;
		        	 }
		        	
		        	 
		        	 
		        	 if(rv_type == "1000000001" && rv_slab == "1000000005")	
		        		 {
		        		  $scope.market.PERCENT = "0.00";
		        		  $scope.RESIDUAL_VALUE1 = "0.00";
		        		 }
		        	 else
		        		 {
			        		 $.ajax({
				    	  		 url: "residualDetails_Trf.sprg?isAjaxCalling=Y", //1.0.0.12 added  isAjaxCalling=Y
				    	  		 data: {"rv_type": rv_type,
				    	  			    "rv_slab":rv_slab,
				    	  			    "leasePeriod" :leasePeriod,
				    	  			    "lease_startDate":lease_startDate,
				    	  			    "rv_fixed_percentage" :rv_fixed_percentage,
				    	  			    "rv_fixed_calc":rv_fixed_calc,
				    	  			    "moratorium":moratorium,
				    	  			    "annualUsage":annualUsage,
				    	  			    "marketPercent": $scope.market.PERCENT},
				    	  		 type: "GET" ,
				    	  			success: function(response) 
				    	  			{
				    		  			if(response!=null && response!="") 
				    		  			{
				    		  				var jsonStr = JSON.parse(response);
				    		  				if(jsonStr.Status=="SessionExpired") //1.0.0.12
					  						{
					  							window.location.href = 'llm/ReLogin.jsp?unknownError=Your session has been killed. Please login again.';
					  							return;
					  						}
				    		  				if(jsonStr.schemeDependentInfo.PN_RESIDUAL_PERCENTAGE!=undefined){
				    		  				$scope.market.PERCENT = jsonStr.schemeDependentInfo.PN_RESIDUAL_PERCENTAGE;
				    		  				} if (jsonStr.schemeDependentInfo.PN_RESIDUAl_VALUE!=undefined){
				    		  				$scope.RESIDUAL_VALUE1 = jsonStr.schemeDependentInfo.PN_RESIDUAl_VALUE;
				    		  				}
				    		  				if (jsonStr.schemeDependentInfo.PC_HIGHLIGHT_VALUE!=undefined){
				    		  				$scope.hilightedResidualValueFlag = jsonStr.schemeDependentInfo.PC_HIGHLIGHT_VALUE!=""?jsonStr.schemeDependentInfo.PC_HIGHLIGHT_VALUE:"";
				    		  				
				    		  				setTimeout(function(){
					    		  					if($scope.hilightedResidualValueFlag == "Y")
					    		  					{
					    		  					alert(("FRV is higher than expected.").toUpperCase());
					    		  					}
				    		  					}, 350);
				    		  				
				    		  					
				    		  				}
				    		  				 $scope.$apply();
				    		  			}
				    	  			}
				        	 });
		        		 }
				        	
		        	 
		        	 
		        	}
		    	  else{
		    		 alert("Lease period mandatory..");
		    		 $scope.EXT_WARR="N";
		    		 $scope.$apply();
		    	 }
		    	
		     };
		     
		
		     
		     $scope.getRVValue1 = function(){
		    	 var rv_type = $scope.RV_TYPE;
		    	 var rv_slab = $scope.isDisabledMarket?"":angular.element('#RV_SLAB1').val();
		    		 
		    	 var leasePeriod = Number(Number($scope.LEASE_PERIOD) +  Number($scope.MORATORIUM));
		    	 if(!angular.isUndefined($scope.LEASE_PERIOD)){
		        	 var lease_startDate = $scope.START_DATE;
		        	 var rv_fixed_percentage =  $scope.isDisabledFixed?"": (!angular.isUndefined(angular.element('#fixed_rv').val())?angular.element('#fixed_rv').val():"");
		        	 
		        	 var rv_fixed_calc=  $scope.isDisabledFixed?"":angular.element('#rv_calculation').val();
		        	
		        	 if(rv_fixed_percentage >100)//1.0.0.5 changes
		        		 {
		        		 alert("RV FIXED PERCENT SHOULD BE LESS THAN OR EQUAL TO 100.");//1.0.0.5 changes
		        		 $scope.PERCENT_VALUE	="0.00";
		        		 return false;
		        		 }
		        	 if(!$scope.isDisabledFixed && rv_fixed_calc=="0"){
		        		 return false;
		        	 }
		        	 var market_per = $scope.market.PERCENT;
		        	 if(market_per >100)//1.0.0.5 changes
	        		 {
	        		 alert("MARKET RV PERCENT SHOULD BE LESS THAN OR EQUAL TO 100.");//1.0.0.5 changes
	        		 $scope.market.PERCENT ="0.00";
	        		 $scope.RESIDUAL_VALUE1 = "0.00";
	        		 return false;
	        		 }
		        	 var decimalCheck = rv_fixed_percentage.split('.');

		                if(decimalCheck.length > 2) {
		                	alert("RV FIXED PERCENT CAN NOT CONTAIN MORE THAN ONE DECIMAL.");
			        		 $scope.PERCENT_VALUE	="0.00";
		                }
		                
		                 decimalCheck = market_per.split('.');

		                if(decimalCheck.length > 2) {
		                	alert("MARKET RV PERCENT CAN NOT CONTAIN MORE THAN ONE DECIMAL.");
		                	$scope.market.PERCENT ="0.00";
		                }
		        	 
		        
			        		 $.ajax({
				    	  		 url: "residualDetails_Trf.sprg?isAjaxCalling=Y", //1.0.0.12 added  isAjaxCalling=Y
				    	  		 data: {"rv_type": rv_type,
				    	  			    "rv_slab":rv_slab,
				    	  			    "leasePeriod" :leasePeriod,
				    	  			    "lease_startDate":lease_startDate,
				    	  			    "rv_fixed_percentage" :rv_fixed_percentage,
				    	  			    "rv_fixed_calc":rv_fixed_calc,
				    	  			    "marketPercent":market_per },
				    	  		 type: "GET" ,
				    	  			success: function(response) 
				    	  			{
				    		  			if(response!=null && response!="") 
				    		  			{
				    		  				var jsonStr = JSON.parse(response);
				    		  				if(jsonStr.Status=="SessionExpired") //1.0.0.12
					  						{
					  							window.location.href = 'llm/ReLogin.jsp?unknownError=Your session has been killed. Please login again.';
					  							return;
					  						}
				    		  				if(jsonStr.schemeDependentInfo.PN_RESIDUAL_PERCENTAGE!=undefined){
				    		  				$scope.market.PERCENT = jsonStr.schemeDependentInfo.PN_RESIDUAL_PERCENTAGE;
				    		  				} if (jsonStr.schemeDependentInfo.PN_RESIDUAl_VALUE!=undefined){
				    		  				$scope.RESIDUAL_VALUE1 = jsonStr.schemeDependentInfo.PN_RESIDUAl_VALUE;
				    		  				}
				    		  				if (jsonStr.schemeDependentInfo.PC_HIGHLIGHT_VALUE!=undefined){
				    		  				$scope.hilightedResidualValueFlag = jsonStr.schemeDependentInfo.PC_HIGHLIGHT_VALUE!=""?jsonStr.schemeDependentInfo.PC_HIGHLIGHT_VALUE:"";
					    		  				setTimeout(function(){
					    		  					if($scope.hilightedResidualValueFlag == "Y")
					    		  					{
					    		  					alert(("FRV is higher than expected.").toUpperCase());
					    		  					}
				    		  					}, 350);
				    		  				}
				    		  				 $scope.$apply();
				    		  			}
				    	  			}
				        	 });
		        		 
				        	
		        	 
		        	 
		        	}
		    	  else{
		    		 alert("Lease period mandatory..");
		    		 $scope.EXT_WARR="N";
		    		 $scope.$apply();
		    	 }
		    	
		     };
		
	
	     
		     
		       
		       $scope.isChargeType = function(){
		    	 if($scope.CHARGE_TYPE_VALUE=='1000000001'){
		    		 $scope.isChargeDisable= true;
		    		 $scope.AMOUNT_ADVANCE="0.00";
		    		 $scope.INTEREST_RATE="0.00";
		    		 $scope.NO_OF_DAYS="0";
		    		 $scope.INTEREST_AMT="0.00";
		    		 
		    	 } else{
		    		 $scope.isChargeDisable= false;
		    	 }
		       };
		
	
	
		       $scope.getMaintenanceAmount=function(){
		      
		  	   if($scope.MAINTENANCE_SLAB=="1000000006" || $scope.MAINTENANCE_SLAB=="1000000013" || $scope.MAINTENANCE_SLAB=="1000000012" || $scope.MAINTENANCE_SLAB == "0")
		 	   {
		    		   $scope.TOTAL_MAINTENANCE="0.00";
		    		  
		    		   
		    		  
		    		  
		 	   }
		      else
		    	 {
		    	   if(Number($scope.LEASE_PERIOD)>0 && $scope.MAINTENANCE_SLAB !=0 && $scope.removeCommaOfVariable($scope.ANNUAL_USAGE) != "")
		    		   {
		    		   
		    			 var leasePeriod = Number(Number($scope.LEASE_PERIOD) +  Number($scope.MORATORIUM));
				      	 $.ajax({
				  	  		 url: "getMaintenanceAmount_Trf.sprg?isAjaxCalling=Y", //1.0.0.12 added  isAjaxCalling=Y
				  	  		 data: {
				  	  			    "maintenance_slab":$scope.MAINTENANCE_SLAB,
				  	  			    "leasePeriod" : leasePeriod,
				  	  			    "lease_startDate":$scope.START_DATE,
				  	  			    "annual_usage" :$scope.removeCommaOfVariable($scope.ANNUAL_USAGE)
				  	  			    },
				  	  		 type: "GET" ,
				  	  			success: function(response) 
				  	  			{
				  		  			if(response!=null && response!="") 
				  		  			{
				  		  				var jsonStr = JSON.parse(response);
				  		  			if(jsonStr.Status=="SessionExpired") //1.0.0.12
			  						{
			  							window.location.href = 'llm/ReLogin.jsp?unknownError=Your session has been killed. Please login again.';
			  							return;
			  						}
				  		  				
				  		  			  $scope.TOTAL_MAINTENANCE=jsonStr.maintenanceInfo.MAINT_AMOUNT;
				  		  			  
				  		  				var status = jsonStr.maintenanceInfo.PC_STATUS;
				  		  				var message = jsonStr.maintenanceInfo.PC_MESSAGES;
				  		  				if(status=='F')
				  		  					{
				  		  					alert(message);
				  		  					$scope.MAINTENANCE_SLAB="0";
				  		  					$scope.ANNUAL_USAGE="";
				  		  					}
				  		  					
				  		  			$scope.$apply();
				  		  			}
				  		  		
				  	  			}
				      	 });
		    		   }
		    	 }
		  	   
		       };
		       $scope.maturityDate = function (date, month) {
		    	   var temp = date;
		    	    temp = new Date(date.getFullYear(), date.getMonth(), 1);
		    	    temp.setMonth(parseInt(temp.getMonth()) + (parseInt(month) + 1));
		    	    temp.setDate(temp.getDate() - 1); 

		    	    if (date.getDate() < temp.getDate()) { 
		    	        temp.setDate(date.getDate()); 
		    	    }
		    	    return temp;    
		    	};
		       $scope.getAMCTable = function(){
		    		 
		    	   if(!angular.isUndefined($scope.LEASE_PERIOD) && $scope.isAMCEnable==false && $scope.AMC_details=="Y" ){
		    		   var lease_period = Number(Number($scope.LEASE_PERIOD) +  Number($scope.MORATORIUM) - Number(arrRentalDtl.BAL_PERIOD_TRANSFER_YR)); //1.0.0.1
		    		   var noOfYrs = Math.ceil(lease_period/12);
		    		   var StartDate = $scope.START_DATE;
		    		   StartDate = new Date(StartDate);
		    		   if($scope.amcDetails.length  == 0){
		    			   $scope.amcDetails=[];
   			 		     }
		    		   
		    			   
		    		   var tempEndDate= new Date(StartDate.getTime() + 364 * 24 * 60 * 60 * 1000);
		    		   StartDate= $scope.amcDetails.length>0?new Date($scope.amcDetails[0].START_DATE):new Date($scope.START_DATE);
				       tempEndDate= $scope.amcDetails.length>0?new Date($scope.amcDetails[0].END_DATE):new Date(tempEndDate); //1.0.0.1 start
				       var temp_lease_period = Number(Number($scope.LEASE_PERIOD) +  Number($scope.MORATORIUM));
				       var leaseMaturityDate =  $scope.maturityDate(StartDate,temp_lease_period);
				       var tempFirstRowObj=$scope.amcDetails.length>0?$scope.amcDetails[0]:"";
		    		 
				        if(!angular.isUndefined(tempFirstRowObj)&& tempFirstRowObj!= null && tempFirstRowObj != "" && Number(arrRentalDtl.BAL_PERIOD_TRANSFER_YR)>0){
				        	 $scope.amcDetails=[];
				      
			    		   for(var i =0;i<=noOfYrs;i++){
			    			   if(i==0)
			    				   {
			    				      $scope.amcDetails.push(tempFirstRowObj);
			    				   }
			    			   else if(i==noOfYrs)//1.0.0.1 end
			    				  {
			    				  $scope.amcDetails.push({
			    				   		'ID' :i+1,
			    				   		'MAINTENANCE_TYPE'	:"AMC",			   	
			    				   		'START_DATE' :  $filter('date')(StartDate, "dd-MMM-yyyy"),
			    				   		'END_DATE':  $filter('date')(new Date(leaseMaturityDate.getTime() - 1 * 24 * 60 * 60 * 1000),"dd-MMM-yyyy"),
			    				   		'YEARLY_COST':"0.00",
			    				   		'STRUCTURE_ACTIONABLE_ID': '0',
			    				   		'REMARKS': ""
			    				   	});
			    				  }
			    			  else
			    			  {
			    				  
			    				  $scope.amcDetails.push({
			    				   		'ID' :i+1,
			    				   		'MAINTENANCE_TYPE'	:"AMC",
			    				   		'START_DATE' :  $filter('date')(StartDate, "dd-MMM-yyyy"),
			    				   		'END_DATE':  $filter('date')(tempEndDate, "dd-MMM-yyyy") ,
			    				   		'YEARLY_COST':"0.00",
			    				   		'STRUCTURE_ACTIONABLE_ID': '0',
			    				   		'REMARKS': ""
			    				   	});
			    			  }
			    			   
			    			   
			    			   StartDate = new Date(tempEndDate.getTime() + 1 * 24 * 60 * 60 * 1000);
			    			   tempEndDate = new Date(tempEndDate.getTime() + 365 * 24 * 60 * 60 * 1000) ;
			    			  if(tempEndDate.getFullYear()%4==0)
			    				   {tempEndDate = new Date(tempEndDate.getTime() + 1 * 24 * 60 * 60 * 1000 );}   
			    			   /*if(StartDate.getFullYear()%4==0)
		    				   {StartDate = new Date(StartDate.getTime() + 1 * 24 * 60 * 60 * 1000);}*/
			    		   }
		    		   }
		    		   else{
		    			  
		    			   $scope.amcDetails=[];
		    			   //add start by 1.0.0.4
		    			   if(Number(arrRentalDtl.BAL_PERIOD_TRANSFER_YR)>0)
		    				   {
		    				     noOfYrs++;
		    				   }
		    			   //add end by 1.0.0.4
		    			   for(var i =0;i<noOfYrs;i++){
		    				   //add start by 1.0.0.4
		    				   if(Number(arrRentalDtl.BAL_PERIOD_TRANSFER_YR)>0){
		    					   tempEndDate =  $scope.maturityDate(StartDate,Number(arrRentalDtl.BAL_PERIOD_TRANSFER_YR));
		    					   tempEndDate=new Date(tempEndDate.getTime() - 1 * 24 * 60 * 60 * 1000)
		    				  }
		    				   //add end by 1.0.0.4
		    				    if(i==noOfYrs-1)
				    				  {
				    				  $scope.amcDetails.push({
				    				   		'ID' :i+1,
				    				   		'MAINTENANCE_TYPE'	:"AMC",			   	
				    				   		'START_DATE' :  $filter('date')(StartDate, "dd-MMM-yyyy"),
				    				   		'END_DATE':  $filter('date')(new Date(leaseMaturityDate.getTime() - 1 * 24 * 60 * 60 * 1000),"dd-MMM-yyyy"),
				    				   		'YEARLY_COST':"0.00",
				    				   		'STRUCTURE_ACTIONABLE_ID': '0',
				    				   		'REMARKS': ""
				    				   	});
				    				  }
				    			  else
				    			  {
				    				  
				    				  $scope.amcDetails.push({
				    				   		'ID' :i+1,
				    				   		'MAINTENANCE_TYPE'	:"AMC",
				    				   		'START_DATE' :  $filter('date')(StartDate, "dd-MMM-yyyy"),
				    				   		'END_DATE':  $filter('date')(tempEndDate, "dd-MMM-yyyy") ,
				    				   		'YEARLY_COST':"0.00",
				    				   		'STRUCTURE_ACTIONABLE_ID': '0',
				    				   		'REMARKS': ""
				    				   	});
				    			  }
				    			   
				    			   //StartDate = new Date(new Date(StartDate.getTime() + 365 * 24 * 60 * 60 * 1000) + 1 * 24 * 60 * 60 * 1000);
				    			   StartDate = new Date(tempEndDate.getTime() + 1 * 24 * 60 * 60 * 1000);
				    			   tempEndDate = new Date(tempEndDate.getTime() + 365 * 24 * 60 * 60 * 1000) ;
				    			  if(tempEndDate.getFullYear()%4==0)
				    				   {tempEndDate = new Date(tempEndDate.getTime() + 1 * 24 * 60 * 60 * 1000 );}   // 1.0.0.5
				    			   /*if(StartDate.getFullYear()%4==0)
			    				   {StartDate = new Date(StartDate.getTime() + 1 * 24 * 60 * 60 * 1000);}*/
				    		   }
		    		   }
		    		   
		    		   
		    		
		    	   } else {
		    		   
		    		   angular.forEach($scope.amcDetails, function(selected,index){
		    			   if(Number(arrRentalDtl.BAL_PERIOD_TRANSFER_YR) > 0){
		    			   if(index>0 && selected.MAINTENANCE_DTL_ID != null && selected.MAINTENANCE_DTL_ID != "" && selected.MAINTENANCE_DTL_ID != undefined)
			        		{
				            	if(removeExtendedWarrentyIdStr!=null && removeExtendedWarrentyIdStr!="" && removeExtendedWarrentyIdStr!=undefined)
				            		{removeExtendedWarrentyIdStr = removeExtendedWarrentyIdStr + "^"+ selected.MAINTENANCE_DTL_ID;
				            		}
				            	else{
				            		 removeExtendedWarrentyIdStr = selected.MAINTENANCE_DTL_ID;
				            	}
			        		}
		    			   }
		    			   else
		    				   {
		    				   if(selected.MAINTENANCE_DTL_ID != null && selected.MAINTENANCE_DTL_ID != "" && selected.MAINTENANCE_DTL_ID != undefined)
				        		{
					            	if(removeExtendedWarrentyIdStr!=null && removeExtendedWarrentyIdStr!="" && removeExtendedWarrentyIdStr!=undefined)
					            		{removeExtendedWarrentyIdStr = removeExtendedWarrentyIdStr + "^"+ selected.MAINTENANCE_DTL_ID;
					            		}
					            	else{
					            		 removeExtendedWarrentyIdStr = selected.MAINTENANCE_DTL_ID;
					            	}
				        		}
		    				   }
			            
			        });
		    		   
		    		  // $scope.amcDetails=[];
		    		  
		    		   
		    	   }
		    	  
		    	   
		       };
		       
//1.0.0.1 start
		       
$scope.removeMain_Tyre_battery =function(obj)
{
	 angular.forEach(obj, function(selected,index){
		 if(Number(arrRentalDtl.BAL_PERIOD_TRANSFER_YR) > 0){
		   if(index>0 && selected.MAINTENANCE_SCH_ID != null && selected.MAINTENANCE_SCH_ID != "" && selected.MAINTENANCE_SCH_ID != undefined)
		    {
          	if(removeMaintTyreBatteryIdStr!=null && removeMaintTyreBatteryIdStr!="" && removeMaintTyreBatteryIdStr!=undefined)
          		{removeMaintTyreBatteryIdStr = removeMaintTyreBatteryIdStr + "^"+ selected.MAINTENANCE_SCH_ID;
          		}
          	else{
          		removeMaintTyreBatteryIdStr = selected.MAINTENANCE_SCH_ID;
          	}
		    }
	      }
	 
	 else{
		 if(selected.MAINTENANCE_SCH_ID != null && selected.MAINTENANCE_SCH_ID != "" && selected.MAINTENANCE_SCH_ID != undefined)
		    {
			 if(removeMaintTyreBatteryIdStr!=null && removeMaintTyreBatteryIdStr!="" && removeMaintTyreBatteryIdStr!=undefined)
       		{
				 removeMaintTyreBatteryIdStr = removeMaintTyreBatteryIdStr + "^"+ selected.MAINTENANCE_SCH_ID;
       		}
	       	else{
	       		removeMaintTyreBatteryIdStr = selected.MAINTENANCE_SCH_ID;
	       	}
		    }
	      }
	}); 
}
 $scope.getMaintenanceTable=function()
 {
	 if(!angular.isUndefined($scope.LEASE_PERIOD) && isMaintEnableFlag=="Y" ){
		   var lease_period = Number(Number($scope.LEASE_PERIOD) +  Number($scope.MORATORIUM) - Number(arrRentalDtl.BAL_PERIOD_TRANSFER_YR));
		   var noOfYrs = Math.ceil(lease_period/12);
		   var StartDate = $scope.START_DATE;
		       StartDate = new Date(StartDate);
		       if(jsonStr.quotationDetails.GET_MAINTENANCE_SCHEDULE_COL.length  == 0){
		 			$scope.MAINTENANCE_SCHEDULE_COL=[];
		 		}
		   var tempEndDate= new Date(StartDate.getTime() + 364 * 24 * 60 * 60 * 1000);
		       StartDate= ($scope.MAINTENANCE_SCHEDULE_COL.length>0 && !angular.isUndefined($scope.MAINTENANCE_SCHEDULE_COL[0]))?new Date($scope.MAINTENANCE_SCHEDULE_COL[0].START_DATE):new Date($scope.START_DATE);
		       tempEndDate= ($scope.MAINTENANCE_SCHEDULE_COL.length>0 && !angular.isUndefined($scope.MAINTENANCE_SCHEDULE_COL[0]))?new Date($scope.MAINTENANCE_SCHEDULE_COL[0].END_DATE):new Date(tempEndDate);
		   var temp_lease_period = Number(Number($scope.LEASE_PERIOD) +  Number($scope.MORATORIUM));
		   var leaseMaturityDate =  $scope.maturityDate(StartDate,temp_lease_period);
		   var tempFirstRowObj=$scope.MAINTENANCE_SCHEDULE_COL[0];
		  
			   if(!angular.isUndefined(tempFirstRowObj) && tempFirstRowObj!= null && tempFirstRowObj!="" && arrRentalDtl.BAL_PERIOD_TRANSFER_YR>0){
				   $scope.MAINTENANCE_SCHEDULE_COL=[];
			   for(var i =0;i<=noOfYrs;i++){
				   if(i==0)
				   {
				     $scope.MAINTENANCE_SCHEDULE_COL.push(tempFirstRowObj);
				   }
			   else if(i==noOfYrs)
					  {
					  $scope.MAINTENANCE_SCHEDULE_COL.push({
					   		'ID' :i+1,
					   		'MAINTENANCE_SCH_TYPE'	:"MAINTENANCE",			   	
					   		'START_DATE' :  $filter('date')(StartDate, "dd-MMM-yyyy"),
					   		'END_DATE':  $filter('date')(new Date(leaseMaturityDate.getTime() - 1 * 24 * 60 * 60 * 1000),"dd-MMM-yyyy"),
					   		'YEARLY_COST':"0.00",
					   		'STRUCTURE_ACTIONABLE_ID': '1000000001',
					   		'REMARKS': ""
					   	});
					  }
				  else
				  {
					  
					  $scope.MAINTENANCE_SCHEDULE_COL.push({
					   		'ID' :i+1,
					   		'MAINTENANCE_SCH_TYPE'	:"MAINTENANCE",
					   		'START_DATE' :  $filter('date')(StartDate, "dd-MMM-yyyy"),
					   		'END_DATE':  $filter('date')(tempEndDate, "dd-MMM-yyyy") ,
					   		'YEARLY_COST':"0.00",
					   		'STRUCTURE_ACTIONABLE_ID': '1000000001',
					   		'REMARKS': ""
					   	});
				  }
				   
				   StartDate = new Date(tempEndDate.getTime() + 1 * 24 * 60 * 60 * 1000);
				   tempEndDate = new Date(tempEndDate.getTime() + 365 * 24 * 60 * 60 * 1000) ;
				  if(tempEndDate.getFullYear()%4==0)
					   {tempEndDate = new Date(tempEndDate.getTime() + 1 * 24 * 60 * 60 * 1000 );}   
				  /* if(StartDate.getFullYear()%4==0)
				   {StartDate = new Date(StartDate.getTime() + 1 * 24 * 60 * 60 * 1000);}*/
			   }
			 }
			   else{
				   $scope.MAINTENANCE_SCHEDULE_COL=[];
				   //add start by 1.0.0.4
    			   if(Number(arrRentalDtl.BAL_PERIOD_TRANSFER_YR)>0)
    				   {
    				     noOfYrs++;
    				   }
    			   //add end by 1.0.0.4
				   for(var i =0;i<noOfYrs;i++){
					 //add start by 1.0.0.4
					   if(Number(arrRentalDtl.BAL_PERIOD_TRANSFER_YR)>0){
    					   tempEndDate =  $scope.maturityDate(StartDate,Number(arrRentalDtl.BAL_PERIOD_TRANSFER_YR));
    					   tempEndDate=new Date(tempEndDate.getTime() - 1 * 24 * 60 * 60 * 1000)
    				  }
    				   //add end by 1.0.0.4
    				    if(i==(noOfYrs-1))
						  {
						  $scope.MAINTENANCE_SCHEDULE_COL.push({
						   		'ID' :i+1,
						   		'MAINTENANCE_SCH_TYPE'	:"MAINTENANCE",			   	
						   		'START_DATE' :  $filter('date')(StartDate, "dd-MMM-yyyy"),
						   		'END_DATE':  $filter('date')(new Date(leaseMaturityDate.getTime() - 1 * 24 * 60 * 60 * 1000),"dd-MMM-yyyy"),
						   		'YEARLY_COST':"0.00",
						   		'STRUCTURE_ACTIONABLE_ID': '1000000001',
						   		'REMARKS': ""
						   	});
						   break;
						  }
					  else
					  {
						  
						  $scope.MAINTENANCE_SCHEDULE_COL.push({
						   		'ID' :i+1,
						   		'MAINTENANCE_SCH_TYPE'	:"MAINTENANCE",
						   		'START_DATE' :  $filter('date')(StartDate, "dd-MMM-yyyy"),
						   		'END_DATE':  $filter('date')(tempEndDate, "dd-MMM-yyyy") ,
						   		'YEARLY_COST':"0.00",
						   		'STRUCTURE_ACTIONABLE_ID': '1000000001',
						   		'REMARKS': ""
						   	});
					  }
					   
					   StartDate = new Date(tempEndDate.getTime() + 1 * 24 * 60 * 60 * 1000);
					   tempEndDate = new Date(tempEndDate.getTime() + 365 * 24 * 60 * 60 * 1000) ;
					  if(tempEndDate.getFullYear()%4==0)
						   {tempEndDate = new Date(tempEndDate.getTime() + 1 * 24 * 60 * 60 * 1000 );}   
					  /* if(StartDate.getFullYear()%4==0)
					   {StartDate = new Date(StartDate.getTime() + 1 * 24 * 60 * 60 * 1000);}*/
				   }
			   }
		   
		
	   } else {
		   
		   $scope.removeMain_Tyre_battery($scope.MAINTENANCE_SCHEDULE_COL);
		   $scope.MAINTENANCE_SCHEDULE_COL=[];
	   }
 }
 
 $scope.getTyreTable=function()
 {
	 if(!angular.isUndefined($scope.LEASE_PERIOD) && $scope.ALLOW_LEASE_TYRE=="Y" ){
		 var lease_period = Number(Number($scope.LEASE_PERIOD) +  Number($scope.MORATORIUM) - Number(arrRentalDtl.BAL_PERIOD_TRANSFER_YR));
		   var noOfYrs = Math.ceil(lease_period/12);
		   var StartDate = $scope.START_DATE;
		   StartDate = new Date(StartDate);
		   var tempEndDate= new Date(StartDate.getTime() + 364 * 24 * 60 * 60 * 1000);
		   if(jsonStr.quotationDetails.GET_TYRE_SCHEDULE_COL.length == 0){
			   $scope.TYRE_SCHEDULE_COL=[];
		   }
		   StartDate= $scope.TYRE_SCHEDULE_COL.length>0?new Date($scope.TYRE_SCHEDULE_COL[0].START_DATE):new Date($scope.START_DATE);
	       tempEndDate= $scope.TYRE_SCHEDULE_COL.length>0?new Date($scope.TYRE_SCHEDULE_COL[0].END_DATE):new Date(tempEndDate);
	       var temp_lease_period = Number(Number($scope.LEASE_PERIOD) +  Number($scope.MORATORIUM));
	       var leaseMaturityDate =  $scope.maturityDate(StartDate,temp_lease_period);
	       var tempFirstRowObj=$scope.TYRE_SCHEDULE_COL[0];
		  
		if(!angular.isUndefined(tempFirstRowObj) && tempFirstRowObj!= null && tempFirstRowObj!=""  && arrRentalDtl.BAL_PERIOD_TRANSFER_YR>0){ //condition updated by 1.0.0.2 
			 $scope.TYRE_SCHEDULE_COL=[];
		   for(var i =0;i<=noOfYrs;i++){
			   if(i==0)
			   {
			   $scope.TYRE_SCHEDULE_COL.push(tempFirstRowObj);
			   }
		   else if(i==noOfYrs)
				  {
				  $scope.TYRE_SCHEDULE_COL.push({
				   		'ID' :i+1,
				   		'MAINTENANCE_SCH_TYPE'	:"TYRE",			   	
				   		'START_DATE' :  $filter('date')(StartDate, "dd-MMM-yyyy"),
				   		'END_DATE':  $filter('date')(new Date(leaseMaturityDate.getTime() - 1 * 24 * 60 * 60 * 1000),"dd-MMM-yyyy"),
				   		'YEARLY_COST':"0.00",
				   		'STRUCTURE_ACTIONABLE_ID': '1000000001',
				   		'REMARKS': ""
				   	});
				  }
			  else
			  {
				  
				  $scope.TYRE_SCHEDULE_COL.push({
				   		'ID' :i+1,
				   		'MAINTENANCE_SCH_TYPE'	:"TYRE",
				   		'START_DATE' :  $filter('date')(StartDate, "dd-MMM-yyyy"),
				   		'END_DATE':  $filter('date')(tempEndDate, "dd-MMM-yyyy") ,
				   		'YEARLY_COST':"0.00",
				   		'STRUCTURE_ACTIONABLE_ID': '1000000001',
				   		'REMARKS': ""
				   	});
			  }
			   
			   StartDate = new Date(tempEndDate.getTime() + 1 * 24 * 60 * 60 * 1000);
			   tempEndDate = new Date(tempEndDate.getTime() + 365 * 24 * 60 * 60 * 1000) ;
			  if(tempEndDate.getFullYear()%4==0)
				   {tempEndDate = new Date(tempEndDate.getTime() + 1 * 24 * 60 * 60 * 1000 );}   
			   /*if(StartDate.getFullYear()%4==0)
			   {StartDate = new Date(StartDate.getTime() + 1 * 24 * 60 * 60 * 1000);}*/
		   }
	 }
		//add start by 1.0.0.2
		else{
			 $scope.TYRE_SCHEDULE_COL=[];
			//add start by 1.0.0.4
			   if(Number(arrRentalDtl.BAL_PERIOD_TRANSFER_YR)>0)
				   {
				     noOfYrs++;
				   }
			   //add end by 1.0.0.4
			for(var i =0;i<noOfYrs;i++){
				   //add start by 1.0.0.4
				 if(Number(arrRentalDtl.BAL_PERIOD_TRANSFER_YR)>0){
					   tempEndDate =  $scope.maturityDate(StartDate,Number(arrRentalDtl.BAL_PERIOD_TRANSFER_YR));
					   tempEndDate=new Date(tempEndDate.getTime() - 1 * 24 * 60 * 60 * 1000)
				  }
				   //add end by 1.0.0.4
				    if(i==(noOfYrs-1))
					  {
					  $scope.TYRE_SCHEDULE_COL.push({
					   		'ID' :i+1,
					   		'MAINTENANCE_SCH_TYPE'	:"TYRE",			   	
					   		'START_DATE' :  $filter('date')(StartDate, "dd-MMM-yyyy"),
					   		'END_DATE':  $filter('date')(new Date(leaseMaturityDate.getTime() - 1 * 24 * 60 * 60 * 1000),"dd-MMM-yyyy"),
					   		'YEARLY_COST':"0.00",
					   		'STRUCTURE_ACTIONABLE_ID': '1000000001',
					   		'REMARKS': ""
					   	});
					  break;
					  }
				  else
				  {
					  
					  $scope.TYRE_SCHEDULE_COL.push({
					   		'ID' :i+1,
					   		'MAINTENANCE_SCH_TYPE'	:"TYRE",
					   		'START_DATE' :  $filter('date')(StartDate, "dd-MMM-yyyy"),
					   		'END_DATE':  $filter('date')(tempEndDate, "dd-MMM-yyyy") ,
					   		'YEARLY_COST':"0.00",
					   		'STRUCTURE_ACTIONABLE_ID': '1000000001',
					   		'REMARKS': ""
					   	});
				  }
				   
				   StartDate = new Date(tempEndDate.getTime() + 1 * 24 * 60 * 60 * 1000);
				   tempEndDate = new Date(tempEndDate.getTime() + 365 * 24 * 60 * 60 * 1000) ;
				  if(tempEndDate.getFullYear()%4==0)
					   {tempEndDate = new Date(tempEndDate.getTime() + 1 * 24 * 60 * 60 * 1000 );}   
				   /*if(StartDate.getFullYear()%4==0)
				   {StartDate = new Date(StartDate.getTime() + 1 * 24 * 60 * 60 * 1000);}*/
			   }
		}//add end by 1.0.0.2
		   
		
	   } else {
		   
		   $scope.removeMain_Tyre_battery($scope.TYRE_SCHEDULE_COL);
		   $scope.TYRE_SCHEDULE_COL=[];
	   }
 }
 
 
 $scope.getBatteryTable=function()
 {
	 if(!angular.isUndefined($scope.LEASE_PERIOD) && $scope.ALLOW_LEASE_BATTERY=="Y" ){
		 var lease_period = Number(Number($scope.LEASE_PERIOD) +  Number($scope.MORATORIUM) - Number(arrRentalDtl.BAL_PERIOD_TRANSFER_YR));
		   var noOfYrs = Math.ceil(lease_period/12);
		   var StartDate = $scope.START_DATE;
		   StartDate = new Date(StartDate);
		   var tempEndDate= new Date(StartDate.getTime() + 364 * 24 * 60 * 60 * 1000);
		   if(jsonStr.quotationDetails.GET_BATTERY_SCHEDULE_COL.length == 0){
			   $scope.BATTERY_SCHEDULE_COL = [];
		   }
		   StartDate= $scope.BATTERY_SCHEDULE_COL.length>0?new Date($scope.BATTERY_SCHEDULE_COL[0].START_DATE):new Date($scope.START_DATE);
	       tempEndDate= $scope.BATTERY_SCHEDULE_COL.length>0?new Date($scope.BATTERY_SCHEDULE_COL[0].END_DATE):new Date(tempEndDate);
	       var temp_lease_period = Number(Number($scope.LEASE_PERIOD) +  Number($scope.MORATORIUM));
	       var leaseMaturityDate =  $scope.maturityDate(StartDate,temp_lease_period);
	       var tempFirstRowObj=$scope.BATTERY_SCHEDULE_COL[0];
		   
		if(!angular.isUndefined(tempFirstRowObj) && tempFirstRowObj!= null && tempFirstRowObj!=""   && arrRentalDtl.BAL_PERIOD_TRANSFER_YR >0){//condition updated by 1.0.0.2   
			$scope.BATTERY_SCHEDULE_COL=[];
			for(var i =0;i<=noOfYrs;i++){
			   if(i==0)
			   {
			     $scope.BATTERY_SCHEDULE_COL.push(tempFirstRowObj);
			   }
		   else if(i==noOfYrs)
				  {
				  $scope.BATTERY_SCHEDULE_COL.push({
				   		'ID' :i+1,
				   		'MAINTENANCE_SCH_TYPE'	:"BATTERY",			   	
				   		'START_DATE' :  $filter('date')(StartDate, "dd-MMM-yyyy"),
				   		'END_DATE':  $filter('date')(new Date(leaseMaturityDate.getTime() - 1 * 24 * 60 * 60 * 1000),"dd-MMM-yyyy"),
				   		'YEARLY_COST':"0.00",
				   		'STRUCTURE_ACTIONABLE_ID': '1000000001',
				   		'REMARKS': ""
				   	});
				  }
			  else
			  {
				  
				  $scope.BATTERY_SCHEDULE_COL.push({
				   		'ID' :i+1,
				   		'MAINTENANCE_SCH_TYPE'	:"BATTERY",
				   		'START_DATE' :  $filter('date')(StartDate, "dd-MMM-yyyy"),
				   		'END_DATE':  $filter('date')(tempEndDate, "dd-MMM-yyyy") ,
				   		'YEARLY_COST':"0.00",
				   		'STRUCTURE_ACTIONABLE_ID': '1000000001',
				   		'REMARKS': ""
				   	});
			  }
			   
			   StartDate = new Date(tempEndDate.getTime() + 1 * 24 * 60 * 60 * 1000);
			   tempEndDate = new Date(tempEndDate.getTime() + 365 * 24 * 60 * 60 * 1000) ;
			  if(tempEndDate.getFullYear()%4==0)
				   {tempEndDate = new Date(tempEndDate.getTime() + 1 * 24 * 60 * 60 * 1000 );}   
			   /*if(StartDate.getFullYear()%4==0)
			   {StartDate = new Date(StartDate.getTime() + 1 * 24 * 60 * 60 * 1000);}*/
		   }
	 	}
		//add start by 1.0.0.2
		else{
			$scope.BATTERY_SCHEDULE_COL=[];
			//add start by 1.0.0.4
		    if(Number(arrRentalDtl.BAL_PERIOD_TRANSFER_YR)>0)
			   {
			     noOfYrs++;
			   }
		    //add end by 1.0.0.4
			for(var i =0;i<noOfYrs;i++){
				  //add start by 1.0.0.4
				 if(Number(arrRentalDtl.BAL_PERIOD_TRANSFER_YR)>0){
					   tempEndDate =  $scope.maturityDate(StartDate,Number(arrRentalDtl.BAL_PERIOD_TRANSFER_YR));
					   tempEndDate=new Date(tempEndDate.getTime() - 1 * 24 * 60 * 60 * 1000)
				  }
				   //add end by 1.0.0.4
				    if(i==(noOfYrs-1))
					  {
					  $scope.BATTERY_SCHEDULE_COL.push({
					   		'ID' :i+1,
					   		'MAINTENANCE_SCH_TYPE'	:"BATTERY",			   	
					   		'START_DATE' :  $filter('date')(StartDate, "dd-MMM-yyyy"),
					   		'END_DATE':  $filter('date')(new Date(leaseMaturityDate.getTime() - 1 * 24 * 60 * 60 * 1000),"dd-MMM-yyyy"),
					   		'YEARLY_COST':"0.00",
					   		'STRUCTURE_ACTIONABLE_ID': '1000000001',
					   		'REMARKS': ""
					   	});
					  break;
					  }
				  else
				  {
					  
					  $scope.BATTERY_SCHEDULE_COL.push({
					   		'ID' :i+1,
					   		'MAINTENANCE_SCH_TYPE'	:"BATTERY",
					   		'START_DATE' :  $filter('date')(StartDate, "dd-MMM-yyyy"),
					   		'END_DATE':  $filter('date')(tempEndDate, "dd-MMM-yyyy") ,
					   		'YEARLY_COST':"0.00",
					   		'STRUCTURE_ACTIONABLE_ID': '1000000001',
					   		'REMARKS': ""
					   	});
				  }
				   
				   StartDate = new Date(tempEndDate.getTime() + 1 * 24 * 60 * 60 * 1000);
				   tempEndDate = new Date(tempEndDate.getTime() + 365 * 24 * 60 * 60 * 1000) ;
				  if(tempEndDate.getFullYear()%4==0)
					   {tempEndDate = new Date(tempEndDate.getTime() + 1 * 24 * 60 * 60 * 1000 );}   
				   /*if(StartDate.getFullYear()%4==0)
				   {StartDate = new Date(StartDate.getTime() + 1 * 24 * 60 * 60 * 1000);}*/
			   }
		}//add end by 1.0.0.2
		   
		
	   } else {
		   
		   $scope.removeMain_Tyre_battery($scope.BATTERY_SCHEDULE_COL);
		   $scope.BATTERY_SCHEDULE_COL=[];
	   }
 }
 //1.0.0.1 end
$scope.numberWithCommas=function(name)
 {
	
	var y= $scope[name];
	var x = $scope.removeCommaOfVariable(y);
	var x = x.toString();
	var pattern = /(-?\d+)(\d{3})/;
	while (pattern.test(x))
	x = x.replace(pattern, "$1,$2");
	
	$scope[name]=x;
};	
		    	
		    	
		       
		   	$scope.getExtendedWarrTable = function(){
		   		
		   		if($scope.EXT_WARR=="N")
		   			{
		   			$scope.EXTENDED_WARRANTY_IN_FMS = "N";
		   			}
		   	
		   	
				var startDate = new Date($scope.START_DATE);
				if(angular.isUndefined($scope.LEASE_PERIOD) || $scope.LEASE_PERIOD=="" && $scope.EXT_WARR=="Y"){
					alert("LEASE PERIOD IS MANDATORY.");
					$scope.EXT_WARR="N";
					return false;
				}
				var extStartDate = $scope.maturityDate(startDate, Number(Number($scope.LEASE_PERIOD) +  Number($scope.MORATORIUM))); 
				
				
				   var currentStartDate= new Date(extStartDate.getTime());
				    var currentEndDate= new Date(currentStartDate.getTime() + 364 * 24 * 60 * 60 * 1000);
				    
				    	if(currentStartDate.getFullYear()%4==0 && currentStartDate.getMonth()<=2)
				          {
				    		if((currentStartDate.getMonth()==2 && currentStartDate.getDate()<29) ||(currentStartDate.getMonth()==1))
				    	      {currentEndDate = new Date(currentEndDate.getTime() + 1 * 24 * 60 * 60 * 1000);
				    	      }
				    	 }
				    	if(currentEndDate.getFullYear()%4==0 && currentEndDate.getMonth()>2)
				          {
				    		currentEndDate = new Date(currentEndDate.getTime() + 1 * 24 * 60 * 60 * 1000);
				    	    
				    	 }
				
				
				
				  var maint_type = "EXTENDED WARRANTY";
				   if($scope.EXT_WARR=="Y"){
						   $scope.extDetails.push({
							    'checkbox':"",
						   		'ID' :"",
						   		'MAINTENANCE_TYPE':maint_type,
						   		'START_DATE' : $filter('date')(new Date(currentStartDate),"dd-MMM-yyyy"),
						   		'END_DATE':   $filter('date')(new Date(currentEndDate), "dd-MMM-yyyy") ,
						   		'YEARLY_COST':"",
						   		'STRUCTURE_ACTIONABLE_ID': "0",
						   		'REMARKS': ""
						   	});
					   
				   }
				   else{
					  
					   angular.forEach( $scope.extDetails, function(selected){
				        	if(selected.MAINTENANCE_DTL_ID != null && selected.MAINTENANCE_DTL_ID != "")
				        		{
					        		if(removeExtendedWarrentyIdStr!=null && removeExtendedWarrentyIdStr!="" && removeExtendedWarrentyIdStr!=undefined)
				            		{removeExtendedWarrentyIdStr = removeExtendedWarrentyIdStr + "^"+ selected.MAINTENANCE_DTL_ID;
				            		}
					            	else{
					            		 removeExtendedWarrentyIdStr = selected.MAINTENANCE_DTL_ID;
					            	}
				        		
				        		}
					   }); 
					   $scope.extDetails=[];
					   
				   }
			};    
		       
		       
			 $scope.getTotalCost = function(list,fieldName){  
				   
				   if(($scope.isAMCEnable==false && $scope.AMC_details=="Y") || ($scope.ALLOW_LEASE_TYRE == 'Y') || ($scope.ALLOW_LEASE_BATTERY == 'Y') || ($scope.isMaintEnable==false)){
				   var totalAMCCost=0;
				   angular.forEach(list , function(item ){
					   if(item.YEARLY_COST=="" || item.YEARLY_COST==undefined){
						   item.YEARLY_COST=0;
					   }
					  totalAMCCost+= parseFloat($scope.removeCommaOfVariable(item.YEARLY_COST));
					  
					   
				   });
				   
				   }
				   $scope[fieldName] = totalAMCCost;
			   };     
			
		       
		       
			  $scope.getExtDendedTotal = function(list){
			    	 var totalChargeAmount=0;
				     	 angular.forEach(list , function(item )
				     			  {  	
				     			  	if(item.YEARLY_COST=="" || item.YEARLY_COST==undefined){item.YEARLY_COST=0;}
				     			  	if(item.YEARLY_COST!='0' && (item.YEARLY_COST).charAt(0)=='0')
				     			   	item.YEARLY_COST=(item.YEARLY_COST).substring(1);
				     			  	totalChargeAmount+= parseFloat($scope.removeCommaOfVariable(item.YEARLY_COST));
				     			  	  
				     			  });
				      $scope.totalExtCost = totalChargeAmount;
				      };      
				  	
				      
	$scope.addExtendedWarrentyRow = function(index){
						
					
						var maint_id = "";
						
						if(index.length>0){
							maint_id = index[0].MAINTENANCE_ID;
						}
						
						var lease_period = Number(Number($scope.LEASE_PERIOD) +  Number($scope.MORATORIUM));
			    		var extendedStartDate = $scope.START_DATE;
			    		var leaseMaturityDate =  $scope.maturityDate(new Date(extendedStartDate),lease_period);
			    		
						var maint_type = "EXTENDED WARRANTY";
						var extDetailLength=$scope.extDetails.length;
						var startDate ;
						var endDate;
						if(extDetailLength>=1){
						
							startDate = new Date(new Date($scope.extDetails[extDetailLength-1].END_DATE).getTime()+ 1 * 24 * 60 * 60 * 1000);
							endDate = new Date(new Date(startDate).getTime() + 364 * 24 * 60 * 60 * 1000);
							if(new Date(endDate).getFullYear()%4==0)
								{
								endDate = new Date(new Date(endDate).getTime() +  24 * 60 * 60 * 1000);
								}
						 $scope.extDetails.push({
							 	
						   		'ID' :"",
						   		'MAINTENANCE_TYPE':maint_type,
						   		'MAINTENANCE_ID': maint_id,
						   		'START_DATE' :    $filter('date')(new Date(startDate),"dd-MMM-yyyy"),
						   		'END_DATE':  $filter('date')(new Date(endDate), "dd-MMM-yyyy") ,
						   		'YEARLY_COST':"0.00",
						   		'STRUCTURE_ACTIONABLE_ID': "0",
						   		'REMARKS': ""
						   	});
						}
						else
							{
								$scope.extDetails.push({
							 	
						   		'ID' :"",
						   		'MAINTENANCE_TYPE':maint_type,
						   		'MAINTENANCE_ID': maint_id,
						   		'START_DATE' :    $filter('date')(new Date(leaseMaturityDate),"dd-MMM-yyyy"),
						   		'END_DATE':  $filter('date')(new Date(new Date(leaseMaturityDate).getTime() + 365 * 24 * 60 * 60 * 1000), "dd-MMM-yyyy"),
						   		'YEARLY_COST':"0.00",
						   		'STRUCTURE_ACTIONABLE_ID': "0",
						   		'REMARKS': ""
						   	});
							}
						
						
					};

	$scope.removeExtendedWarrentyRow= function(index){
						
						var newDataList=[];
						if($scope.extDetails.length>1){
				        angular.forEach($scope.extDetails, function(selected){
				        	
				            if(!selected.selected){
				                newDataList.push(selected);
				            }else{ 
				            	if(removeExtendedWarrentyIdStr!=null && removeExtendedWarrentyIdStr!="" && removeExtendedWarrentyIdStr!=undefined)
				            		removeExtendedWarrentyIdStr = removeExtendedWarrentyIdStr +"^"+selected.MAINTENANCE_DTL_ID;
				            	else
				            		 removeExtendedWarrentyIdStr = selected.MAINTENANCE_DTL_ID;
				            }
				        }); 
				        $scope.extDetails = newDataList;
				        $scope.validationExtendedWaranty();
						} else{
							
						}
					};
		
					
		$scope.validationExtendedWaranty = function()
					{
						var lease_period = Number(Number($scope.LEASE_PERIOD) +  Number($scope.MORATORIUM));
			    		var extendedStartDate = $scope.START_DATE;
			    		var leaseMaturityDate =  $scope.maturityDate(new Date(extendedStartDate),lease_period);
			    		
						var currentEndDate;
			    		var currentStartDate;
						for(var i=0;i<$scope.extDetails.length;i++)
							{
							
							if(i==0)
								{
								$scope.extDetails[i].START_DATE=$filter('date')(new Date(new Date(leaseMaturityDate).getTime()), "dd-MMM-yyyy");
								
								   
								}
							else
							  {
								   var previousEndDate=new Date($scope.extDetails[i-1].END_DATE);
								   $scope.extDetails[i].START_DATE=$filter('date')(new Date(previousEndDate.getTime() + 1 * 24 * 60 * 60 * 1000), "dd-MMM-yyyy");
							  }   
								   currentStartDate= new Date($scope.extDetails[i].START_DATE);
								    currentEndDate= new Date(currentStartDate.getTime() + 364 * 24 * 60 * 60 * 1000);
								    
								    	if(currentStartDate.getFullYear()%4==0 && currentStartDate.getMonth()<=2)
			    				          {
								    		if((currentStartDate.getMonth()==2 && currentStartDate.getDate()<29) ||(currentStartDate.getMonth()==1))
								    	      {currentEndDate = new Date(currentEndDate.getTime() + 1 * 24 * 60 * 60 * 1000);
								    	      }
								    	 }
								    	if(currentEndDate.getFullYear()%4==0 && currentEndDate.getMonth()>2)
			    				          {
								    		currentEndDate = new Date(currentEndDate.getTime() + 1 * 24 * 60 * 60 * 1000);
								    	    
								    	 }
								   $scope.extDetails[i].END_DATE=$filter('date')(new Date(currentEndDate), "dd-MMM-yyyy");
									
									  
			    			   
								
							}
					}
	
				      
				     $scope.getDefaultvaluesOfAddOnServices= function(){
				    	 var schemeId = $scope.SCHEME1;
				    	 var leaseStartDate = $scope.START_DATE;
				    	 var valueAddedStr = $scope.valueAdded;
				    	 var finalString = "";
				    	 var quantityError="";
				    	 var applicabilityError = "";
				    	 for(var i=0;i<valueAddedStr.length; i++){
							var selectedValue =  $filter('filter')(valueAddedStr)[i];
							
							var selectedQuantity = !angular.isUndefined(selectedValue.QTY)?selectedValue.QTY:"";
							if((selectedValue.SERVICE_ID == '1000000002' &&  $scope.ALLOW_LEASE_TYRE == 'Y') || (selectedValue.SERVICE_ID == '1000000003' && $scope.ALLOW_LEASE_BATTERY == 'Y'))
							{
							
							}
							else
								{
								if(((document.getElementById("SERVICE_AMOUNT"+i).disabled==false) && selectedValue.quantityEnable=='Y' && selectedQuantity=="" && selectedValue.APPLICABILITY!="1000000001") || angular.isUndefined(selectedValue.APPLICABILITY) ){
									alert("QUANTITY AND APPLICABILITY BOTH ARE MANDATORY.");
									return;
								}
								}
							if(finalString==""){
								finalString=selectedValue.SERVICE_ID +"~"+ selectedValue.APPLICABILITY+"~"+ selectedQuantity;
							} else{
							    finalString = finalString+"^"+selectedValue.SERVICE_ID +"~"+ selectedValue.APPLICABILITY+"~"+ selectedQuantity;
							}
				    	 }
				    	
				    	 $.ajax({
					  		 url: "getAddOnServicesMasterAmount_Trf.sprg?isAjaxCalling=Y", //1.0.0.12 added  isAjaxCalling=Y
					  		 data: {
					  			    "schemeId":schemeId,
					  			    "leaseStartDate" :leaseStartDate,
					  			    "tableString":finalString
					  			   },
					  		 type: "POST" ,
					  			success: function(response) 
					  			{
						  			if(response!=null && response!="") 
						  			{
						  				var jsonStr = JSON.parse(response);
						  				if(jsonStr.Status=="SessionExpired") //1.0.0.12
				  						{
				  							window.location.href = 'llm/ReLogin.jsp?unknownError=Your session has been killed. Please login again.';
				  							return;
				  						}
						  				var output = JSON.parse(jsonStr); 				
						  				var valueAddedStr = $scope.valueAdded;
						  				var outputRes = output.MasterAmount;
						  				for(var j=0; j<outputRes.length;j++){
								  		    	 for(var i=0;i<valueAddedStr.length; i++){
								  		    		if(outputRes[j].ServiceId == $filter('filter')(valueAddedStr)[i].SERVICE_ID){
								  		    				valueAddedStr[i].MASTER_AMOUNT = outputRes[j].Amount;
								  		    				if(valueAddedStr[i].SERVICE_AMOUNT==0|| valueAddedStr[i].SERVICE_AMOUNT=="" || angular.isUndefined(valueAddedStr[i].SERVICE_AMOUNT))
								  		    					valueAddedStr[i].SERVICE_AMOUNT = outputRes[j].Amount;
								  		    				
								  		    					
								  		    			}
								  		    	 }
						  				}
						  				$scope.$apply();
						  				
						  			}
					  			}
				    	 });
				    	 $scope.getDefaultValuesFlagForAddOnServices = true; 
				     };
				     
				     $scope.setToDefaultAddOnServices = function(){
				    	 
				    	 var value_added =  $scope.valueAdded;
				    	for(var i=0;i<value_added.length; i++){
				    		value_added[i].SERVICE_AMOUNT = value_added[i].MASTER_AMOUNT;
				    	}
				     };
				 	
					$scope.isValidApplicability= function(list,x){
						var qty;
						
						if(list.x.APPLICABILITY=="1000000001" || list.x.APPLICABILITY=="0"){
							
							list.x.quantityEnable = 'N';
							list.x.QTY="";
							list.x.amountEnable = 'N';
							list.x.SERVICE_AMOUNT="0.00";
							list.x.remarksEnable = 'N';
							list.x.REMARKS="";
							list.x.MASTER_AMOUNT="0.00";
						}
						else{
							  list.x.quantityEnable = list.x.quantityEnableFlag;	
						      list.x.amountEnable = 'Y';
						      list.x.remarksEnable = 'Y';
						      $scope.getDefaultValuesFlagForAddOnServices = false;
							}
						
					};
				     
				     
					
					 $scope.MonthlyChargeMaster=[];
				     $scope.addMonthlyCharges= function(){
				    	 var chargeIdVal="0"; 
				    	 var masterAmount_Val="0.00";
				    	 var amount_Val="0.00";
				    	 if($scope.monthlyCharges.length==0)
				    		 {
				    	       $scope.flag = "N";	
				    	       if($scope.AGREEMENT_TYPE_VALUE=='1000000003')
				    	    	   {
				    	    	      chargeIdVal="1200000010";
				    	    	      var arrSchemeMaster = $scope.masterChargeId;
				    	    	      for(var i =0;i< arrSchemeMaster.length;i++){
								  			if(arrSchemeMaster[i].ID == chargeIdVal){
								  				masterAmount_Val =arrSchemeMaster[i].NAME;
								  				amount_Val =arrSchemeMaster[i].NAME;
								  				break;
								  			}
								  		}
				    	    	   }
				    	       
				    		 }
				    	 
				    	 if(angular.element("#SCHEME1").val()!=0)
				    		 {
				    		 $scope.monthlyCharges.push({
								 
								 'quotation_id': quotationId, 
								    'selected123': "",
							   		'CHARGE' :chargeIdVal,
							   		'MASTER_AMOUNT' :  masterAmount_Val,
							   		'AMOUNT':  amount_Val,
							   		'QUOTATION_CHARGE_ID':""
							   		
							   	});	 
				    		 }
				    	 else
				    		 {
				    		  alert("PLEASE SELECT SCHEME FIRST.");
				    		 }
				    	 
				    	 
				    	
				     };
				     $scope.addMonthlyChargesWithAgreementtype= function(){
				    	 $scope.flag = "Y";	
				    	 isMonthlyChargeAddedForAgreementType=true;			
				    	 $scope.monthlyCharges.push({
							 
							 'quotation_id': quotationId, 
							    'selected1234': "",
						   		'CHARGE' : "0",
						   		'MASTER_AMOUNT' :  "0.00",
						   		'AMOUNT':  "0.00",
						   		'QUOTATION_CHARGE_ID':""
						   		
						   	});
				    	
				    	 var monthlyCharge = $scope.monthlyCharges;
				    	 
				    	 for(var i=0;i< monthlyCharge.length;i++){
				    		 monthlyCharge[i].CHARGE = '1200000010';
				    		 
				    		 var arrSchemeMaster = $scope.masterChargeId;
						  		for(var j =0;j< arrSchemeMaster.length;j++){
						  			if(arrSchemeMaster[j].ID == monthlyCharge[i].CHARGE){
						  				monthlyCharge[i].MASTER_AMOUNT =arrSchemeMaster[j].NAME;
						  				monthlyCharge[i].AMOUNT =arrSchemeMaster[j].NAME;
						  			}
						  		}
				    		
								
				    	 }
				    		    	
				    	
				     };
				     $scope.removeMonthlyCharges = function(index){
				    	    
				    	 var newDataList=[];
				         angular.forEach($scope.monthlyCharges, function(selected){
				         	
				             if(!selected.selected123){
				                 newDataList.push(selected);
				             }
				             else {
				            	 
				            	 if(removeChargeIdStr!=""){
				            		 removeChargeIdStr=removeChargeIdStr + "^"+ selected.QUOTATION_CHARGE_ID;
				            	 }
				            	 else {
				            		 removeChargeIdStr = selected.QUOTATION_CHARGE_ID;
				            	 }
				             }
				         }); 
				         $scope.monthlyCharges = newDataList;
				 		
				     }; 
				     $scope.setToDefault = function(){
				    	 
				    	 var monthlyCharges =  $scope.monthlyCharges;
				    	for(var i=0;i<monthlyCharges.length; i++){
				    		monthlyCharges[i].AMOUNT = monthlyCharges[i].MASTER_AMOUNT;
				    	}
				     }; 
				     $scope.getMasterAmount= function(list, row){
				      	var arrSchemeMaster = $scope.masterChargeId;
				  		for(var i =0;i< arrSchemeMaster.length;i++){
				  			if(arrSchemeMaster[i].ID == list.x.CHARGE){
				  				row.MASTER_AMOUNT =arrSchemeMaster[i].NAME;
				  				row.AMOUNT =arrSchemeMaster[i].NAME;
				  			}
				  		}
				 	
				      };
				      
				      
				      

				      
				
				      
				      
				      $scope.setRsd = function()
				      {
				    	  
				    	  var startDate=new Date($scope.START_DATE)
				    	  if($("#START_DATE")[0].value!="")
				    	  {
				    	  $scope.RSD = $scope.START_DATE;
				    	  }
				    	  else
				    	  {
				    		  $scope.RSD ="";
				    	  }
				    	  
				    	  
				    	 	$scope.expiryDays="";
				    	 	 var schemeId = $scope.SCHEME1;
				    	   	 var noOfDays=0;
				    	   
				    	   	 for(var i =0;i<arrValidationValues.length;i++){
				    	   		 if(arrValidationValues[i].SCHEMEID==schemeId){
				    	   			 noOfDays=arrValidationValues[i].QUOTATION_EXPIRY_DAYS;
				    	   			$scope.expiryDays=arrValidationValues[i].QUOTATION_EXPIRY_DAYS;
				    	   			 
				    	   		 }
				    	   	 }
				    	   	 if(!angular.isUndefined($scope.START_DATE) && $scope.START_DATE !=null ){//1.0.0.1
				    	   		 var startDate = new Date($scope.START_DATE);
				    	   		 var endDate = new Date(startDate.getTime() + noOfDays * 24 * 60 * 60 * 1000);
				    		    	 $scope.EXPIRING_DATE = $filter('date')(endDate, "dd-MMM-yyyy");
				    		    	 $scope.TEMP_EXPIRING_DATE=$scope.EXPIRING_DATE;
				    	    	} else
				    	   	 {
				    	    		$scope.EXPIRING_DATE = null;
				    	    		$scope.TEMP_EXPIRING_DATE=null;
				    	   	 }
				    	   
				      };
				    
				      
				      
     var error="";
     var dm_error="";
     var rentalSaveStr= "";
     var maintenanceSaveStr="";
     var maintenaceExtenedSaveStr="";
     var valueAddedSaveStr="";
     var monthlyChargesSaveStr="";
     var maintenanceDtlSaveId="";
     var quotationChargeSaveId="";
   //1.0.0.1 start
     var maintenaceSaveStr="";
     var tyreSaveStr="";
     var batterySaveStr="";
     var maint_TyreBatterySaveStr="";
   //1.0.0.1 end
     
     $scope.checkForm = function()
     {
    	 
    	 var expirydays=$scope.expiryDays;
    	 var businessDate = $scope.businessDate;
         var effectiveLeaseStartDate = $scope.START_DATE;
         var expiryDate=$scope.EXPIRING_DATE;
         var minimumStartDate= new Date();
         tempData.errorMessages="Errors:";
         businessDate=new Date (businessDate);
         effectiveLeaseStartDate=new Date (effectiveLeaseStartDate);
         minimumStartDate.setTime(businessDate.getTime()-((Number(expirydays)-1) * 24*60*60*1000));
         
         
         if($rootScope.staticInfoObject.COPIED_QUOTATION!=undefined && $rootScope.staticInfoObject.COPIED_QUOTATION!=null && $rootScope.staticInfoObject.COPIED_QUOTATION!="")
  	    {
  	   if($rootScope.staticInfoObject.COPIED_QUOTATION=="Y")
  		   {
  	   if($rootScope.staticInfoObject.PRODUCT_ID!=$scope.PRODUCT_TYPE1)
  		   {
  		 error = error+"EITHER THIS QUOTATION IS COPIED OR ANOTHER QUOTATION HAS BEEN COPIED USING THIS ONE. THEREFORE CANNOT ALLOW PRODUCT CHANGE.\n";
  		   }
  	   
  		   }
  	   }
       
       
         
         /*if(minimumStartDate.getTime() >effectiveLeaseStartDate.getTime() || effectiveLeaseStartDate.getTime() > businessDate.getTime())
         {
        	if(entityId!="1000000008"){
        	 error = error+"Effective Lease start date can fall within a range from Current Business Date-Quotation expiry days  configured in the system + 1. \n"; 
        	}
         }commented by 1.0.0.2 as told by shubhangi
        */    
    	 
         var totalTenure = Number(Number($scope.LEASE_PERIOD) +  Number($scope.MORATORIUM));
    	 					
    	 /*if($scope.PRODUCT_TYPE1==0){
    		 //error = error+"PRODUCT TYPE IS MANDATORY. \n"; 1.0.0.9
    	 }*/
    	 if($scope.SCHEME1==0){
    		 error = error+"SCHEME IS MANDATORY. \n";
    	 }
    	 /*if(angular.isUndefined($scope.START_DATE)||$scope.START_DATE==0 || $("#START_DATE")[0].value==""){
    		 //error = error+"START DATE IS MANDATORY. \n"; 1.0.0.9
    	 }*/
		/*if(angular.isUndefined($scope.EXPIRING_DATE)||$scope.EXPIRING_DATE==0 || $scope.EXPIRING_DATE==null ){
			error = error+"EXPIRY DATE IS MANDATORY. \n";
		    	 }*/

    	 // Start 1.0.0.15
 		/*if(angular.isUndefined($scope.LEASE_PERIOD)||$scope.LEASE_PERIOD==0){
			error = error+"LEASE PERIOD IS MANDATORY. \n";
		}*/
		if(angular.isUndefined($scope.LEASE_PERIOD)||$scope.LEASE_PERIOD==0){
			error = error+"LEASE PERIOD IS MANDATORY. \n";
		}else if($scope.LEASE_PERIOD>0 && $scope.RESTRUCTURE_TYPE=="1000000003"){
			if($scope.EXTENSION_PERIOD=="1000000001" && $scope.LEASE_PERIOD>12 ){
				error = error+"LEASE PERIOD IS NOT ALLOWED FOR THE CHOSEN EXTENSION PERIOD. \n";
			}else if($scope.EXTENSION_PERIOD=="1000000002" && $scope.LEASE_PERIOD<=12 ){
				error = error+"LEASE PERIOD IS NOT ALLOWED FOR THE CHOSEN EXTENSION PERIOD. \n";
			}
		}
		// End 1.0.0.15

		if(angular.isUndefined($scope.MORATORIUM)){
			error = error+"MORATORIUM IS MANDATORY. \n";
		}
		
		if(totalTenure < 3)
			{
			if($scope.rental_freq!="1000000004")
				{
				error = error+"RENTAL FREQUENCY CAN BE MONTHLY ONLY. \n";
				}
			}
		else if(totalTenure < 6)
			{
			if($scope.rental_freq=="1000000006" || $scope.rental_freq=="1000000007")
				{
				error = error+"RENTAL FREQUENCY CANNOT BE HALF YEARLY OR ANNUALLY. \n";
				}
			}
		else if(totalTenure < 12)
			{
			if($scope.rental_freq=="1000000007")
				{
				error = error+"RENTAL FREQUENCY CANNOT BE ANNUALLY. \n";
				}
			}
		
		/*commented by 1.0.0.2
		 * if($scope.rental_cycle==0){
			error = error+"BILLING CYCLE IS MANDATORY. \n";
		}*/
		if($scope.rental_freq==0)
		{
			error = error+"RENTAL FREQUENCY IS MANDATORY. \n"; 
		}
		else if($scope.rental_freq=="1000000008")
		{
			if($scope.rental_cycle != "1000000003")
				{
				error = error+"BILLING CYCLE CAN ONLY BE PRORATED. \n";
				}
		}
		
		
		if($scope.rental_type==0){
			error = error+"RENTAL TYPE IS MANDATORY. \n";
		}
		if($scope.RENTAL_MODE_VALUE==0){
			error = error+"RENTAL MODE IS MANDATORY. \n";
		}
		/*commented by 1.0.0.9
		 * if(angular.isUndefined($scope.RSD)||$scope.RSD==0){
			//error = error+"RSD IS MANDATORY. \n"; 
		}*/
		if($scope.AGREEMENT_TYPE_VALUE==0){
			error = error+"AGREEMENT TYPE IS MANDATORY. \n";
		}
		
		
		
		if(angular.isUndefined($scope.EXCESS_KM)){
			error = error+"EXCESS KM IS MANDATORY. \n";
		}
		if($scope.frnaking_type==0){
			error = error+"LEASE FRANKING CHARGE IS MANDATORY. \n";
		}
		if($scope.screenIdFromRequest){
		if($scope.AGREEMENT_SIGNING_TYPE=="0"){
			error = error+"AGREEMENT SIGNING TYPE IS MANDATORY. \n";
		}
		}//1.0.0.13
		else if($scope.FRANKING_CHARGE_AMOUNT==""){
			error = error+"LEASE FRANKING CHARGE AMOUNT IS MANDATORY. \n";
		}
		//comment start by 1.0.0.2
		/*if($scope.CHARGE_TYPE_VALUE==0){
			error = error+"CHARGE TYPE IS MANDATORY. \n";
		}
		if( $scope.isChargeDisable== false){
			if(angular.isUndefined($scope.NO_OF_DAYS) || $scope.NO_OF_DAYS==0){
				error = error+"NUMBER OF DAYS IS MANDATORY. \n";
			}
			if(angular.isUndefined($scope.INTEREST_RATE) || $scope.INTEREST_RATE==0){
				error = error+"INTEREST RATE IS MANDATORY. \n";
			}
			if(angular.isUndefined($scope.AMOUNT_ADVANCE) || $scope.AMOUNT_ADVANCE==0){
				error = error+"ADVANCE AMOUNT IS MANDATORY. \n";
			}
			
			
		}*/
		//comment end by 1.0.0.2
		if($scope.RV_TYPE==0){
			error = error+"RV TYPE IS MANDATORY. \n";
		}
		
		
		if($scope.BILLING_TYPE_ID==0){
			error = error+"BILLING TYPE IS MANDATORY. \n";
		}
		else 
		{
			if($scope.BILLING_TYPE_ID=="1000000001")
			{
				if($scope.FMS_FRANKING_CHRG_AMNT=="" || $scope.FMS_FRANKING_CHRG_AMNT==0)
				{
					error = error+"FMS FRANKING CHARGE IS MANDATORY. \n";
				}// 1.0.0.1 START
				else if(parseFloat($scope.removeCommaOfVariable($scope.FMS_FRANKING_CHRG_AMNT)) < parseFloat($scope.removeCommaOfVariable($scope.TRANSFER_AMT_FMS_FRANK)))//1.0.0.7 added parseFloat
					{
					error = error+"'FMS FRANKING CHARGE' CANNOT BE LESS THAN 'TRANSFER FMS FRANKING CHARGE' "+$scope.TRANSFER_AMT_FMS_FRANK+". \n";
					}
				
				if(parseFloat($scope.removeCommaOfVariable($scope.FMS_OTHER_CHRG_AMNT)) < parseFloat($scope.removeCommaOfVariable($scope.TRANSFER_AMT_FMS_OTHER)))//1.0.0.7 added parsefloat
				{
				    error = error+"'FMS OTHER CHARGE' CANNOT BE LESS THAN 'TRANSFER FMS OTHER CHARGE' "+$scope.TRANSFER_AMT_FMS_OTHER+". \n";
				}
				// 1.0.0.1 END
				
			}
		}
	
		if(((+($scope.removeCommaOfVariable($scope.MORATORIUM)) >= +($scope.removeCommaOfVariable($scope.LEASE_PERIOD)) )) && $scope.removeCommaOfVariable($scope.LEASE_PERIOD)>0){
			error = error+"MORATORIUM SHOULD BE LESS THAN LEASE PERIOD. \n";
		}
		
		if( (+($scope.removeCommaOfVariable($scope.MORATORIUM)) + +($scope.removeCommaOfVariable($scope.LEASE_PERIOD))) >maxDaysInLease ){
			error = error+"SUM OF MORATORIUM AND LEASE PERIOD CANNOT BE GREATER THAN "+maxDaysInLease+". \n";
		}
	
		//add start by 1.0.0.2
			if($scope.amcDetails!=null && $scope.amcDetails.length>0 && $scope.amcDetails[0].YEARLY_COST != "" && $scope.amcDetails[0].YEARLY_COST!=null && (parseFloat($scope.removeCommaOfVariable($scope.amcDetails[0].YEARLY_COST)) < (parseFloat($scope.removeCommaOfVariable($scope.amcDetails[0].TRANSFER_AMNT))))){
				error = error+"1ST YEAR COST OF AMC CAN'T BE LESS THAN "+$scope.amcDetails[0].TRANSFER_AMNT+". \n";
			}
		
			if($scope.extDetails!=null && $scope.extDetails.length>0 && $scope.extDetails[0].YEARLY_COST != "" && $scope.extDetails[0].YEARLY_COST!=null && (parseFloat($scope.removeCommaOfVariable($scope.extDetails[0].YEARLY_COST)) < (parseFloat($scope.removeCommaOfVariable($scope.extDetails[0].TRANSFER_AMNT))))){
				error = error+"1ST YEAR COST OF EXTENDED WARRANTY CAN'T BE LESS THAN "+$scope.extDetails[0].TRANSFER_AMNT+". \n";
			}
			
			if($scope.MAINTENANCE_SCHEDULE_COL!=null && $scope.MAINTENANCE_SCHEDULE_COL.length>0 && $scope.MAINTENANCE_SCHEDULE_COL[0].YEARLY_COST != "" && $scope.MAINTENANCE_SCHEDULE_COL[0].YEARLY_COST!=null && (parseFloat($scope.removeCommaOfVariable($scope.MAINTENANCE_SCHEDULE_COL[0].YEARLY_COST)) < (parseFloat($scope.removeCommaOfVariable($scope.MAINTENANCE_SCHEDULE_COL[0].TRANSFER_AMNT))))){
				error = error+"1ST YEAR COST OF MAINTENANCE SCHEDULE CAN'T BE LESS THAN "+$scope.MAINTENANCE_SCHEDULE_COL[0].TRANSFER_AMNT+". \n";
			}
		
		
			if($scope.TYRE_SCHEDULE_COL!=null && $scope.TYRE_SCHEDULE_COL.length>0 && $scope.TYRE_SCHEDULE_COL[0].YEARLY_COST != "" && $scope.TYRE_SCHEDULE_COL[0].YEARLY_COST!=null && (parseFloat($scope.removeCommaOfVariable($scope.TYRE_SCHEDULE_COL[0].YEARLY_COST)) < (parseFloat($scope.removeCommaOfVariable($scope.TYRE_SCHEDULE_COL[0].TRANSFER_AMNT))))){
				error = error+"1ST YEAR COST OF TYRE SCHEDULE CAN'T BE LESS THAN "+$scope.TYRE_SCHEDULE_COL[0].TRANSFER_AMNT+". \n";
			}
		
			if($scope.BATTERY_SCHEDULE_COL!=null && $scope.BATTERY_SCHEDULE_COL.length>0 && $scope.BATTERY_SCHEDULE_COL[0].YEARLY_COST != "" && $scope.BATTERY_SCHEDULE_COL[0].YEARLY_COST!=null && (parseFloat($scope.removeCommaOfVariable($scope.BATTERY_SCHEDULE_COL[0].YEARLY_COST)) < (parseFloat($scope.removeCommaOfVariable($scope.BATTERY_SCHEDULE_COL[0].TRANSFER_AMNT))))){
				error = error+"1ST YEAR COST OF BATTERY SCHEDULE CAN'T BE LESS THAN "+$scope.BATTERY_SCHEDULE_COL[0].TRANSFER_AMNT+". \n";
			}
		//add end by 1.0.0.2
	
		if(entityId == "1000000008")
		{
			if(Number($scope.FORECLOSURE_DISC_PERC) > 100)
				dm_error = dm_error+"FORECLOSURE DISCOUNTING PERCENT CANNOT BE GREATER THAN 100. \n";
			
			if(Number($scope.FORECLOSURE_PENALTY_PERC) > 100)
				dm_error = dm_error+"FORECLOSURE PENALTY PERCENT CANNOT BE GREATER THAN 100. \n";
			
			if(Number($scope.LPI_PERCENT) > 100)
				dm_error = dm_error+"LPI PERCENT CANNOT BE GREATER THAN 100. \n";
			
			if(Number($scope.INTEREST_ON_ADVN) > 100)
				dm_error = dm_error+"INTEREST ON ADVANCE PERCENT CANNOT BE GREATER THAN 100. \n";
			
			if(Number($scope.PROPOSED_CASHFLOW_IRR) > 100)
				dm_error = dm_error+"PROPOSED_CASHFLOW_IRR PERCENT CANNOT BE GREATER THAN 100. \n";
			
		}
	
		
		
		var fixed_rv ="";
		var rv_calculation ="";
		var market_percentage="";
		var rv_slab = $scope.isDisabledMarket?"":!angular.isUndefined(angular.element('#RV_SLAB1').val())?angular.element('#RV_SLAB1').val():"0";
		if(!$scope.isDisabledMarket){
			if(angular.isUndefined(angular.element('#RV_SLAB1').val()) || angular.element('#RV_SLAB1').val()==0)
				error = error+"MARKET RV SLAB IS MANDATORY. \n";
			if(angular.element('#market_percentage').val()==0 || angular.isUndefined(angular.element('#market_percentage').val()))
				error = error+"MARKET RV PERCENTAGE IS MANDATORY. \n";
			market_percentage =angular.element('#market_percentage').val();
			
		}
		if(!$scope.isDisabledFixed){
		//1.0.0.16 start
			// Skip fixed RV % validation if ex-showroom is selected
            var rvCalcValue = $scope.fixed.CALC;
            if(rvCalcValue != "1000000008"){ 
			    if(angular.isUndefined($scope.PERCENT_VALUE) || $scope.PERCENT_VALUE==0 )
                error = error+"FIXED RV PERCENTAGE IS MANDATORY. \n";
            } else {
              if(angular.isUndefined($scope.RESIDUAL_VALUE1) || $scope.RESIDUAL_VALUE1===null || $scope.RESIDUAL_VALUE1=="" )
                error = error+"RESIDUAL VALUE IS MANDATORY. \n";
            
            }
		    if(angular.element('#rv_calculation').val()==0 || angular.isUndefined(angular.element('#rv_calculation').val()))
			    error = error+"FIXED RV CALCULATION IS MANDATORY. \n";
            fixed_rv = angular.element('#fixed_rv').val();
           rv_calculation = angular.element('#rv_calculation').val();
		}
		
		//1.0.0.16 end
		
		if(angular.isUndefined($scope.RESIDUAL_VALUE1)){
			error = error+"RESIDUAL_VALUE1 IS MANDATORY. \n";
		}
		
		
		
		
	    var residual_value = $scope.RESIDUAL_VALUE1;
	    
    	
	    if($scope.frnaking_type =="1000000001" )
			{
	    	$scope.AUTO_FRANKING_PER = temp_autoFrankingPer;
			}
	    else
	    	{
	    	$scope.AUTO_FRANKING_PER = "";
	    	}
	    
	    
	    rentalSaveStr= quotationId+"~"+$scope.PRODUCT_TYPE1 + "~"+$scope.SCHEME1 + "~"+$scope.START_DATE + 
     	"~"+$scope.EXPIRING_DATE + "~"+$scope.LEASE_PERIOD+
     	"~"+$scope.MORATORIUM+"~"+$scope.rental_cycle+"~"+$scope.rental_freq+"~"+$scope.rental_type+
     	"~"+$scope.RENTAL_MODE_VALUE+"~"+$scope.RSD+"~"+$scope.AGREEMENT_TYPE_VALUE+"~"+$scope.removeCommaOfVariable($scope.ANNUAL_USAGE)+"~"+$scope.removeCommaOfVariable($scope.EXCESS_KM)+
     	"~"+$scope.frnaking_type+"~"+ $scope.removeCommaOfVariable($scope.FRANKING_CHARGE_AMOUNT)+"~"+$scope.CHARGE_TYPE_VALUE+"~"+$scope.NO_OF_DAYS+
     	"~"+$scope.INTEREST_RATE+"~"+$scope.removeCommaOfVariable($scope.AMOUNT_ADVANCE)+"~"+$scope.removeCommaOfVariable($scope.INTEREST_AMT)+"~"+$scope.RV_TYPE+
     	"~"+rv_slab+"~"+fixed_rv+"~"+market_percentage+
     	"~"+rv_calculation+"~"+$scope.removeCommaOfVariable(residual_value)+"~"+$scope.FORECLOSURE_METHOD+"~"+$scope.REPAYMENT_MODE+"~"+$scope.FORECLOSURE_REMARKS+"~"+$scope.FORECLOSURE_DISC_PERC+
     	"~"+$scope.FORECLOSURE_PENALTY_PERC+"~"+$scope.LPI_PERCENT+"~"+$scope.INTEREST_ON_ADVN+"~"+$scope.PROPOSED_CASHFLOW_IRR+"~"+$scope.BILLING_TYPE_ID+"~"+$scope.removeCommaOfVariable($scope.FMS_FRANKING_CHRG_AMNT)+
     	"~"+$scope.removeCommaOfVariable($scope.FMS_OTHER_CHRG_AMNT)+"~"+$scope.AUTO_FRANKING_PER+"~"+$scope.LEASE_ITC_AVAIL_OPTIONS+"~"+$scope.RENTAL_LOGIC+"~"+$scope.AGREEMENT_SIGNING_TYPE;//1.0.0.9
	    
	
     	
	    var MAINENANCE_ID="";
     	
     	var extended_table_show = 	!angular.isUndefined($scope.EXT_WARR)?$scope.EXT_WARR:"";
     
     	
     	var amc_table_show =  !angular.isUndefined($scope.AMC_details)?$scope.AMC_details:"";
	    var totalMaint = "0.00";//parseFloat($scope.removeCommaOfVariable($scope.TOTAL_MAINTENANCE));//1.0.0.1
     	if($scope.isMaintEnable){ 
     		$scope.MAINTENANCE_SLAB="";
     		
     		$scope.TOTAL_MAINTENANCE="0.00";
     		totalMaint ="0.00";
     		$scope.POOL_CAR="N";
     	}else {
     		/*if(angular.isUndefined($scope.MAINTENANCE_SLAB) || $scope.MAINTENANCE_SLAB==0)
     			error = error+"MAINTENANCE IS MANDATORY. \n";
     		
     		if(angular.isUndefined($scope.TOTAL_MAINTENANCE))
     			error = error+"TOTAL MAINTENANCE IS MANDATORY. \n";*/
     		if(angular.isUndefined($scope.POOL_CAR)|| $scope.POOL_CAR==0)
     			error = error+"POOL CAR IS MANDATORY. \n";
     	}
     					
     		maintenanceSaveStr =   MAINENANCE_ID+"~"+isMaintEnableFlag+"~"+$scope.MAINTENANCE_SLAB+"~"+amc_table_show+"~"+extended_table_show+"~"+$scope.POOL_CAR+"~"+totalMaint+"~"+$scope.EXTENDED_WARRANTY_IN_FMS; 
     	
     	//1.0.0.1 added transfer_amnt					
     	var amcKeyString ="MAINTENANCE_DTL_ID~MAINTENANCE_ID~MAINTENANCE_TYPE~START_DATE~END_DATE~YEARLY_COST~STRUCTURE_ACTIONABLE_ID~REMARKS~AMC_VENDOR_ID~AMC_VENDOR_GSTIN_ID~TRANSFER_AMNT";
     	var extKeyString ="MAINTENANCE_DTL_ID~MAINTENANCE_ID~MAINTENANCE_TYPE~START_DATE~END_DATE~YEARLY_COST~STRUCTURE_ACTIONABLE_ID~REMARKS~EXT_VENDOR_ID~EXT_VENDOR_GSTIN_ID~TRANSFER_AMNT";
     	var maintenanceKeyString ="MAINTENANCE_SCH_ID~MAINTENANCE_SCH_TYPE~START_DATE~END_DATE~YEARLY_COST~STRUCTURE_ACTIONABLE_ID~REMARKS~VENDOR_ID~VENDOR_GSTIN_ID~TRANSFER_AMNT";//1.0.0.1
     	var tyreKeyString ="MAINTENANCE_SCH_ID~MAINTENANCE_SCH_TYPE~START_DATE~END_DATE~YEARLY_COST~STRUCTURE_ACTIONABLE_ID~REMARKS~VENDOR_ID~VENDOR_GSTIN_ID~TRANSFER_AMNT";//1.0.0.1
     	var batteryKeyString ="MAINTENANCE_SCH_ID~MAINTENANCE_SCH_TYPE~START_DATE~END_DATE~YEARLY_COST~STRUCTURE_ACTIONABLE_ID~REMARKS~VENDOR_ID~VENDOR_GSTIN_ID~TRANSFER_AMNT";//1.0.0.1
     	//1.0.0.1 added transfer_amnt
     	if($scope.AMC_details=="Y" && $scope.EXT_WARR=="N"){
     			maintenaceExtenedSaveStr=$scope.getSaveString(amcKeyString, "amcDetails");	
     	} else if($scope.EXT_WARR=="Y" && $scope.AMC_details=="N"){
     			maintenaceExtenedSaveStr = $scope.getSaveString(extKeyString, "extDetails");	
     	} else if($scope.EXT_WARR=="Y" && $scope.AMC_details=="Y"){
     			maintenaceExtenedSaveStr=$scope.getSaveString(amcKeyString, "amcDetails")+"^"+$scope.getSaveString(extKeyString, "extDetails");		
     	} else if($scope.EXT_WARR=="N" && $scope.AMC_details=="N"){
     			maintenaceExtenedSaveStr="";	
     	}
     		
     	if(entityId != "1000000008"){
	     	var amcDetailsValidationString="AMC_COST:TMV~AMC_DETAILS_STRUCTURE:DDMV" ;
			$scope.getValidation(amcDetailsValidationString,"amcDetails");
			
			var extWarrentyValidationString="EXTENDED_WARRANTY_COST:TMV~EXTENDED_WARRANTY_STRUCTURE:DDMV" ;
			$scope.getValidation(extWarrentyValidationString,"extDetails");
     	}
		
	     //1.0.0.1 start
	     if($scope.isMaintEnable==false){
	    	 maintenaceSaveStr=$scope.getSaveString(maintenanceKeyString, "MAINTENANCE_SCHEDULE_COL");	
	    	 var maintenanceValidationString="MAINTENANCE_COST:TMV~MAINTENANCE_STRUCTURE:DDMV" ;
			 if(entityId != "1000000008"){//1.0.0.6
	    	 	$scope.getValidation(maintenanceValidationString,"MAINTENANCE_SCHEDULE_COL");
			 }
  	      }
	     else
	    	 {
	    	   maintenaceSaveStr="";
	    	 }
	     if($scope.ALLOW_LEASE_TYRE=="Y"){
	    	 tyreSaveStr=$scope.getSaveString(tyreKeyString, "TYRE_SCHEDULE_COL");
	    	 var tyreValidationString="TYRE_COST:TMV~TYRE_STRUCTURE:DDMV" ;
	    	 if(entityId != "1000000008"){
			 	$scope.getValidation(tyreValidationString,"TYRE_SCHEDULE_COL");
	    	 }
  	      }
	     else
	    	 {
	    	   tyreSaveStr="";
	    	 }
	     if($scope.ALLOW_LEASE_BATTERY=="Y"){
	    	 batterySaveStr=$scope.getSaveString(maintenanceKeyString, "BATTERY_SCHEDULE_COL");	
	    	 var batteryValidationString="BATTERY_COST:TMV~BATTERY_STRUCTURE:DDMV" ;
			 if(entityId != "1000000008"){//1.0.0.6
	    	 	$scope.getValidation(batteryValidationString,"BATTERY_SCHEDULE_COL");
			 }
		    
  	      }
	     else
	    	 {
	    	    batterySaveStr="";
	    	 }
	     
	     //add start by 1.0.0.2
               maint_TyreBatterySaveStr="";
	     if(maint_TyreBatterySaveStr == ""){
		     if(maintenaceSaveStr.length >0){
		    	 maint_TyreBatterySaveStr =  maintenaceSaveStr;
		     }
		     
		     if(tyreSaveStr.length>0){
		    	 if(maint_TyreBatterySaveStr.length > 0){
		    		 maint_TyreBatterySaveStr = maint_TyreBatterySaveStr + "^" + tyreSaveStr;
		    	 }
		    	 else{
		    		 maint_TyreBatterySaveStr =  tyreSaveStr;
		    	 }
		     }
		     if(batterySaveStr.length > 0){
		    	 if(maint_TyreBatterySaveStr.length >0){
		    		 maint_TyreBatterySaveStr = maint_TyreBatterySaveStr + "^" + batterySaveStr;
		    	 }
		    	 else{
		    		 maint_TyreBatterySaveStr = batterySaveStr;
		    	 }
		     }
	     }
	     //add end by 1.0.0.2
	     /*if(maintenaceSaveStr == "" &&tyreSaveStr =="" && batterySaveStr==""){
	    	 maint_TyreBatterySaveStr=null;
	     }
	     else{
	    	 maint_TyreBatterySaveStr=maintenaceSaveStr+"^"+tyreSaveStr+"^"+batterySaveStr; 
	     }
	    */	
	     
	     
		  //1.0.0.1 end
     		var valueAddedStr = $scope.valueAdded;
     		
     		if(!$scope.isMaintEnable){ 
	     		for(var i=0;i<valueAddedStr.length; i++){
		    		if((angular.isUndefined(valueAddedStr[i].MASTER_AMOUNT) || (Number(valueAddedStr[i].APPLICABILITY)!=0 && valueAddedStr[i].APPLICABILITY!="1000000001" && Number(valueAddedStr[i].MASTER_AMOUNT)==0)) && $scope.getDefaultValuesFlagForAddOnServices != true){ 
		    			error = error+"PLEASE CLICK ON GET DEFAULT VAULES IN ADD ON SERVICES SECTION.\n";
		    			break;
		    		} if((document.getElementById("SERVICE_AMOUNT"+i).disabled==false) && ( angular.isUndefined(valueAddedStr[i].SERVICE_AMOUNT) || Number(valueAddedStr[i].SERVICE_AMOUNT)==0))
		    			error = error+"ADD ON SERVICES SECTION AMOUNT IS MANDATORY.\n";
		    	 }
     		}
     		
     	var valueAddedKeyString = "RENTAL_SERVICE_ID~SERVICE_ID~APPLICABILITY~QTY~SERVICE_AMOUNT:TMV~REMARKS~MASTER_AMOUNT~TRANSFER_AMNT";//1.0.0.1 added transfer_amnt
     	valueAddedSaveStr = $scope.getSaveString(valueAddedKeyString,"valueAdded");
     	
     	if(!$scope.isMaintEnable && entityId != "1000000008"){ //1.0.0.6
	    	var valueAddedValidationStr="APPLICABILITY:DDMV" ;
			$scope.getValidation(valueAddedValidationStr,"valueAdded");
			if(tempData.errorMessages!="Errors:")
			{ 
				alert((tempData.errorMessages).replace("Errors:","").toUpperCase());
				tempData.errorMessages="Errors:";
				return false ;
			}
     	}
     	
     	
     	var monthlyChargeKeyString = "QUOTATION_CHARGE_ID~quotation_id~CHARGE:DDMV~AMOUNT:TMV~~~~~~~~~~~MASTER_AMOUNT~~~~~~~~~~~A";
     	monthlyChargesSaveStr = $scope.getSaveString(monthlyChargeKeyString,"monthlyCharges"); 
     	
     	if($scope.AGREEMENT_TYPE_VALUE=="1000000003")
     		{
     		    if(monthlyChargesSaveStr=="")
     		    	{
     		    	   error = error+"MONTHLY CHARGES IS MANDATORY.\n";
     		    	}
     		}
     	
     	//add start by 1.0.0.1
     		if(!angular.isUndefined(arrRentalDtl.BAL_PERIOD_TRANSFER_YR) && (Number($scope.LEASE_PERIOD) < Number(arrRentalDtl.BAL_PERIOD_TRANSFER_YR))){
     			error = error+"LEASE TENURE CANNOT BE LESS THAN THE BALANCE MONTH OF TRANSFER YEAR.\n";
     		}
     		
     		if($scope.BILLING_TYPE_ID=="1000000001"){
	     		if(!angular.isUndefined(arrRentalDtl.TRANSFER_AMNT_FMS_OTHER) && (parseFloat($scope.FMS_OTHER_CHRG_AMNT) < parseFloat(arrRentalDtl.TRANSFER_AMNT_FMS_OTHER))){
	     			error = error+"FMS OTHER CANNOT BE LESS THAN TRANSFER FMS OTHER AMOUNT.\n";
	     		}
	     		if(!angular.isUndefined(arrRentalDtl.TRANSFER_AMNT_FMS_FRANKING) && (parseFloat($scope.FMS_FRANKING_CHRG_AMNT)<parseFloat(arrRentalDtl.TRANSFER_AMNT_FMS_FRANKING))){
	     			error = error+"FMS FRANKING IS NOW LESS THAN IT'S TRANSFERRED VALUE.\n";
	     		}
     		}
     		
     	//add end by 1.0.0.1
     	//1.0.0.6 add start
     		if(dm_error!=""){
     			alert(dm_error.toUpperCase());
    			return false;
     		}
     		//1.0.0.6 add end 
     		
     	if(error!="" && entityId != "1000000008") //1.0.0.6
		{ 
		alert(error.toUpperCase());
			return false;
		}
     	
     	if(tempData.errorMessages!="Errors:" && entityId != "1000000008")//1.0.0.6
		{ 
			alert((tempData.errorMessages).replace("Errors:","").toUpperCase());
			tempData.errorMessages="Errors:";
			return false ;
		}
     	return true;
     	} ;
     
     	
     	$scope.save= function(saveType)
     	{
     		if(responseFlag=="Y")
     		{
     		   responseFlag = "N";
     		   $scope.saveData(saveType);
     	    }
     	  else
     		{
     		  alert(("Already in Process...").toUpperCase());
     		  return;
     		}

     	}

     	$scope.saveData = function(saveType)   
     	{
     		
		  error="";
		 if(!$scope.checkForm())
			 {
			  responseFlag="Y";
			  return;
			 }
		  if(error!="" && entityId != "1000000008")//1.0.0.6
		   {
			  alert(error);
			  responseFlag="Y";
		      error="";
		    
		   }
		   else
		   {  
			   if($scope.saveInstallmentStrctureRows()){
			   
			   var newLeaseStartDate= new Date($scope.START_DATE).toDateString();
			   var oldLeaseStartDate= new Date($rootScope.staticInfoObject.LEASE_START_DATE).toDateString();
			  if((($scope.LEASE_PERIOD!=$rootScope.staticInfoObject.TENURE)&&(Number($scope.LEASE_PERIOD)<60) &&($rootScope.staticInfoObject.REGISTRATION_TYPE=="COMMERCIAL"))||((newLeaseStartDate!=oldLeaseStartDate)&&($rootScope.staticInfoObject.REGISTRATION_TYPE=="COMMERCIAL")) )
				{
				  var boolFlag=true;
				   if($rootScope.staticInfoObject.INSURANCE_SCREEN_STATUS=="2")
					   {
					   boolFlag = confirm(("change in lease tenure/lease start date may impact the asset and insurance screen calculations. click 'ok' to continue else click 'cancel'.").toUpperCase());
						        
					   }
				   else
					   {
					   boolFlag = confirm(("change in lease tenure/lease start date may impact the asset screen calculations. click 'ok' to continue else click 'cancel'.").toUpperCase());
						  
					   }
				   if(!boolFlag)
				  {
					   window.location.reload(true);
					   return;
			    	}  
				}
			 
				 var bool = confirm(("Proceeding to save changes.").toUpperCase());
			    if(!bool)
			    {
			    	responseFlag = "Y";  
			    	return;
			    }	    
			    else
			    {	
							$.ajax({
					  		 url: "saveQuotationsQuotationDetail_Trf.sprg?isAjaxCalling=Y", //1.0.0.12 added  isAjaxCalling=Y",
					  		 type: "post",
					  		 data:{
					  			   rentalDtsSaveStr:rentalSaveStr,
					  			   maintenanceDtsSaveStr: maintenanceSaveStr,
					  			   maintenaceExtenedSaveStr:maintenaceExtenedSaveStr,
					  			   valueAddedSaveStr: valueAddedSaveStr,
					  			   monthlyChargesSaveStr:monthlyChargesSaveStr,
					  			   maintenanceDtlSaveId:maintenanceDtlSaveId,
					  			   quotationChargeSaveId:quotationChargeSaveId,
					  			   removeChargeIdStr:removeChargeIdStr,
					  			   removeExtendedWarrentyIdStr:removeExtendedWarrentyIdStr,
					  			   penaltyPercentStr:$scope.PENALTY_SAVE_STR,
					  			   enquiryId:angular.element('#existingApplicantId').val(),
					  			 maint_TyreBatterySaveStr:maint_TyreBatterySaveStr,//1.0.0.1
					  			removeMaintTyreBatteryIdStr:removeMaintTyreBatteryIdStr//1.0.0.1
					  			  }, 
					  		 ontext: document.body,
					  		 success: function(response) {
					  			if(response!=null && response!="") 
					  					{ var jsonStr = JSON.parse(response);
					  					if(jsonStr.Status=="SessionExpired") //1.0.0.12
				  						{
				  							window.location.href = 'llm/ReLogin.jsp?unknownError=Your session has been killed. Please login again.';
				  							return;
				  						}
					  					  alert(jsonStr.PC_OUT_MESSAGE);
					  					  
					  					  if(jsonStr.status=="S")
					  					 {
					  						  if(saveType=="E" )
					  					 	  {$window.location.href = 'llmDashboard.do?actionPerformed=getDashboard';}
				  						  else
					  						 {$window.location.href = "quotationDetails_Trf.sprg?screenId="+temp_screenId+"&mode="+temp_mode+"&screenName="+temp_screenName;}
					  					 }
					  					  else{
					  						responseFlag = "Y";
					  					  }
					  					}}});
						
			   }
		   	}
		   else{
		   		responseFlag = "Y";//1.0.0.42 added
		   	}
		}
	};
	
	
	
	
	
	
	
	$scope.penalty=[];
	var penaltyFrom=0;
	var maxtenure=$scope.LEASE_PERIOD;
	var penaltyTo=0;
	
	$scope.addPenalityRow= function(){
    	
		
		var countPenalty= $scope.penalty.length;
		if(countPenalty>0){
			penaltyFrom=Number($scope.penalty[countPenalty-1].TENURE_TO)+1;
		}
		
		if($scope.penalty.length>0){
			if($scope.penalty[countPenalty-1].TENURE_TO==$scope.LEASE_PERIOD){
				
				return;
				
			}
			
		}
		
		
	if(penaltyTo==$scope.LEASE_PERIOD){
		
		
		return;
	}
	if(penaltyTo==0){
		penaltyTo=maxtenure;
	}
	if(penaltyFrom==0){
	penaltyFrom=1;
	}
	penaltyTo=$scope.LEASE_PERIOD;
	
    	 $scope.penalty.push({
			 'selectPenalty':"",
			 'TENURE_FROM':penaltyFrom,
			 'TENURE_TO':penaltyTo,
			 'PENALTY_PERCENTAGE': ""
		   		
		   	});
    	 penaltyFrom=penaltyTo+1;	 
    	
     };  
	
     $scope.checkValidPercent= function(x){
    	 if(x.PENALTY_PERCENTAGE>100){
    		alert("PENALTY PERCENT CAN'T BE GREATER THAN 100."); 
    		x.PENALTY_PERCENTAGE=0.00;
    	 }
     }
     
     $scope.setPenaltyTo= function(x){
    	 var obj=x.TENURE_TO;
    	 if(Number($scope.LEASE_PERIOD) < Number(obj))
  		   
         {
         alert("NO. OF INSTALLMENT CANNOT GREATER THAN LEASE TENURE "+Number($scope.LEASE_PERIOD)+".");
        
       
         x.TENURE_TO="";
        
         return;
         }
    	 
    	 penaltyTo=obj;
    	 penaltyFrom=Number(obj)+1;
    	 
    	 for(var i=0; i < $scope.penalty.length; i++)
  		{
         	$scope.penalty[i+1].TENURE_FROM=Number($scope.penalty[i].TENURE_TO)+1;
         	
  		}
    	 
    	 
     };
     
     
     
     
     
     
     
     
 	
 	$scope.saveInstallmentStrctureRows = function()
 	{
 		
 		
 		var tempInstallmentFrom;
 		var tempInstallmentTo;
 		var tempInstallmentAmount;
 		var installmentSchStr="";
 		var templength;
 		
 		if($scope.FORECLOSURE_METHOD=="1000000001"){
 		for(var i=0; i < $scope.penalty.length; i++)
 		{
 			
 			
 				 
 				tempInstallmentFrom = document.getElementById(('TENURE_FROM'+i));
 				tempInstallmentTo=document.getElementById(('TENURE_TO'+i));
 				tempInstallmentAmount = document.getElementById(('PENALTY_PERCENT'+i));
 				
 				removeCommaOfObject(tempInstallmentAmount);
 				
 				
 			     installmentSchStr=installmentSchStr+tempInstallmentFrom.value + "~" + tempInstallmentTo.value + "~" + tempInstallmentAmount.value + "^";
 			      
 			    
 			      if(Number(tempInstallmentFrom.value)>Number(tempInstallmentTo.value))
 			      {
 			            alert(("TENURE TO can't be less than TENURE from.").toUpperCase());
 				  		tempInstallmentTo.select();
 			       	     tempInstallmentTo.focus();
 			          return ;
 			      }
 			      if(Number($scope.LEASE_PERIOD)<Number(tempInstallmentTo.value))
 			      {
 			            alert(("TENURE TO can't be more than LEASE TENURE "+Number($scope.LEASE_PERIOD)+".").toUpperCase());
 				  		tempInstallmentTo.select();
 			            tempInstallmentTo.focus();
 			          return ;
 			      }
 			      
 			       
 			     
 			    
 				
 				  		
 				  		
 			       		
 				   
 				 
 				  if(Number(tempInstallmentAmount.value)<0)
 				  {
 				  		alert(("Penalty Percent CANNOT BE NEGATIVE IN penalty % SCHEDULE.").toUpperCase());
 				  		tempInstallmentAmount.select();
 			       		tempInstallmentAmount.focus();
 				   	return ;	
 				  } 
 				  else if(Number(tempInstallmentAmount.value)>0)
 				   { 
 				   
 				   if(tempInstallmentAmount.value.search(/^\d+(\.\d{1,2})?$/)==-1)
 				  {
 				  alert(("Penalty Percent should be numeric with at most two digits after the decimal place.").toUpperCase());					 
 				  		
 				  		tempInstallmentAmount.select();
 			       		tempInstallmentAmount.focus(); 
 				   	return ;	
 				  }
 				  } 

 			
 		
 		}	
 		if(installmentSchStr!=""){
 			if(Number(tempInstallmentTo.value)<Number($scope.LEASE_PERIOD))
 		{
 		 alert(("Last Installment To Should be equal to LEASE TENURE "+Number($scope.LEASE_PERIOD)+".").toUpperCase())
 		 tempInstallmentTo.select();
 		 tempInstallmentTo.focus();
 		 return;
 		}
 		}
 		if(installmentSchStr==""){
 		alert("penalty block is mandatory.".toUpperCase())
 		return;
 		}
 		
 		
 		$scope.PENALTY_SAVE_STR = installmentSchStr;
 		
 				
 		return true;
 	}
 		
 		else{
 			$scope.PENALTY_SAVE_STR = "";	
 			return true;
 			
 		}
 	}
 
     
 	$scope.setZero = function(val){
 		if(val=="" || val==undefined){
 			$scope.MORATORIUM="0";
 		}
 		
 	}
     
 	
 	
 	
 	$scope.removePenaltyRow = function(index){
	    
   	 var newDataList=[];
        angular.forEach($scope.penalty, function(selected){
        	
            if(!selected.selectPenalty){
                newDataList.push(selected);
            }
            else {
           	 
           	
           	
           	
           
           	
           	
            }
        }); 
        $scope.penalty = newDataList;
        penaltyTo=Number($scope.penalty[$scope.penalty.length-1].TENURE_TO);
        for(var i=0; i < $scope.penalty.length; i++)
 		{
        	$scope.penalty[i+1].TENURE_FROM=Number($scope.penalty[i].TENURE_TO)+1;
        	
        	
 		}
       
        
        
    }; 
 	
 	
 	
 	
 	
 	
	
	
	
	$scope.getSaveString = function(keyString,objName)
	{
		var screenKeyStringArray=keyString.split("~");
			var screenSaveString="";
			var screenSaveStringWithKey="";
			var value;
			var validationMsg="";
			
			var key;
			var lebalName;
			if($scope[objName].length==undefined)
			{
				for(var i=0; i<screenKeyStringArray.length; i++)
				{   
					value="" ;   
					var validationKey=""; 
		     		var singleKeyString=screenKeyStringArray[i] ;
		     		var keyArray=singleKeyString.split(":");
		     		key=keyArray[0];
		     		if(keyArray.length==2)
		     		{
		     			validationKey=keyArray[1];	     			
		     		}
		     		
		     		if( $scope[objName][key ]!=undefined)
					{ 					
						value=$scope[objName][key].replace (/,/g, "");
							
						if(validationKey!="")
						{
							lebalName=key.split("_").join(" ");
							
							validate(document.getElementById(key),validationKey,lebalName);						
						}
					}	
					
					screenSaveString+=value+"~";
				}
				screenSaveString+="^";
			}
			else
			{
				for(var j=0; j<$scope[objName].length; j++)
				{
					for(var i=0; i<screenKeyStringArray.length; i++)
					{   
					value="" ;    
		     		var singleKeyString=screenKeyStringArray[i] ;
		     		var keyArray=singleKeyString.split(":");
		     		key=keyArray[0];
		     		if(keyArray.length==2)
		     		{
		     			validationKey=keyArray[1];	     			
		     		}
		     		else
		     		{validationKey="";}
		     		
		     		if( $scope[objName][j][key ]!=undefined)
		     		{
					 value=$scope[objName][j][key ];	
					 if( validationKey!=undefined && validationKey!="" )
						{
							lebalName=key.split("_").join(" ");
							validate(document.getElementById(key+j),validationKey,lebalName);						
						}	
					}
		     		if(key=="A")
		     			{
		     			 value="A";
		     			}
		     		else
		     			{
		     			 value=$scope.removeCommaOfVariable(value);	
		     			}
		     		
					screenSaveString+=value+"~";
					}
					screenSaveString = screenSaveString.substring(0, screenSaveString.length-1);
					screenSaveString+="^";
				}
			
			
			}
			
			screenSaveString=screenSaveString.substring(0,screenSaveString.length-1);
			return screenSaveString;
	};
	
	$scope.getValidation = function(keyString,objName)
    {
    	var screenKeyStringArray=keyString.split("~");
    	
    		var screenSaveString="";
    		var screenSaveStringWithKey="";
    		var value;
    		var validationMsg="";
    		var key="";
    		var lebalName="";
    		var validationKey=""; 
    		
    		if($scope[objName].length==undefined)
    		{
    			for(var i=0; i<screenKeyStringArray.length; i++)
    			{   
    				value="" ;   
    				
    	     		var singleKeyString=screenKeyStringArray[i] ;
    	     		var keyArray=singleKeyString.split(":");
    	     		key=keyArray[0];
    	     		if(keyArray.length==2)
    	     		{
    	     			validationKey=keyArray[1];	     			
    	     		}
    	     		if(validationKey!="")
    					{
    						lebalName=key.split("_").join(" ");
    						if(!document.getElementById(key).disabled)
    						{
    						validate(document.getElementById(key),validationKey,lebalName);	
    						}
    					}
    			}
    			
    		}
    		else
    		{
    			for(var j=0; j<$scope[objName].length; j++)
    			{
    				for(var i=0; i<screenKeyStringArray.length; i++)
    				{   
    				value="" ;    
    	     		var singleKeyString=screenKeyStringArray[i] ;
    	     		var keyArray=singleKeyString.split(":");
    	     		key=keyArray[0];
    	     		if(keyArray.length==2)
    	     		{
    	     			validationKey=keyArray[1];	     			
    	     		}
    	     		else
    	     		{validationKey="";}
    	     		
    	     		if( validationKey!=undefined && validationKey!="" )
    					{
    						lebalName=key.split("_").join(" ");
    						if(!document.getElementById(key+j).disabled)
    						{
    						validate(document.getElementById(key+j),validationKey,lebalName);	
    						}					
    					}	
    				
    				
    				}
    				
    			}
    		
    		
    		}
    		
    		
    }
	
	$scope.removeCommaOfVariable = function(objValue)
	{
	
	try{
		if(objValue!="" && objValue!=0 && objValue!=undefined)  
		{
		objValue = objValue.replace(/,/g, "");
		
		}
	}
	catch(err)
	{}
		return objValue;
	}
	
	
	  $scope.dealerChooser = function(KeyName,obj,rowId)
	    {
	   
	     var ParamValue = "NA"+","+$rootScope.staticInfoObject.ASSET_CATEGORY_ID+","+"NA"+","+"NA"+","+"NA"; 
	     
		  window.open("quotationMaster_Trf.sprg?KeyName="+KeyName+"&ParamValue="+ParamValue+"&label=DealerChooser&moduleType=quotation&dependent=quotationBodyId&objName="+obj+"&rowId="+rowId+"&defaultSearchFlag=N","popDealer","width=500, height=490,top=100,left=100, location=no, maximize=yes, menubar=no, status=no, toolbar=no, scrollbars=no, resizable=no","modal=yes" );
		
	    }

$scope.displayToolTip = function()	 
{
	  angular.forEach( $scope.amcDetails, function(selected,key){
	      	
	        if(selected.AMC_DEALER_NAME_HIDDEN != "" && selected.AMC_DEALER_NAME_HIDDEN !=null && selected.AMC_DEALER_NAME_HIDDEN != undefined)
		    	{
		    	document.getElementById("AMC_DEALER_NAME_"+key).title = selected.AMC_DEALER_NAME_HIDDEN;
		    	}
	        else if(selected.AMC_DEALER_NAME != "" && selected.AMC_DEALER_NAME !=null && selected.AMC_DEALER_NAME != undefined)
		    	{
		    	document.getElementById("AMC_DEALER_NAME_"+key).title = selected.AMC_DEALER_NAME;
		    	}
	      	
		   }); 
	 	
	 		 
	 		 angular.forEach( $scope.extDetails, function(selected,key){
		        
		        	
		        	if(selected.EXT_DEALER_NAME_HIDDEN != "" && selected.EXT_DEALER_NAME_HIDDEN !=null && selected.EXT_DEALER_NAME_HIDDEN != undefined)
			         	{
			         	document.getElementById("EXT_DEALER_NAME_"+key).title = selected.EXT_DEALER_NAME_HIDDEN;
			         	}
		             else if(selected.EXT_DEALER_NAME != "" && selected.EXT_DEALER_NAME !=null && selected.EXT_DEALER_NAME != undefined)
			         	{
			         	document.getElementById("EXT_DEALER_NAME_"+key).title = selected.EXT_DEALER_NAME;
			         	}
		        	
			   }); 	 
}	 		 
	
setTimeout(function(){
	$scope.displayToolTip();
	}, 1000);

});   	
   
	

app.$inject = ['$scope'];
app.directive('format', ['$filter', function ($filter) {
    return {
        require: '?ngModel',
        link: function (scope, elem, attrs, ctrl) {
            if (!ctrl) return;

            ctrl.$formatters.unshift(function (a) {
                return $filter(attrs.format)(ctrl.$modelValue,"");
            });
            
            ctrl.$parsers.push(function(val) {
                if (angular.isUndefined(val)) {
                    var val = '';
                }
                var clean = val.replace(/[^0-9\.]/g, '');
                var decimalCheck = clean.split('.');

                if(decimalCheck.length > 2) {
                    decimalCheck[2] = decimalCheck[2].slice(0,2);
                    
                    if(decimalCheck[2].substring(decimalCheck[2].length-1,decimalCheck[2].length) == "" || decimalCheck[2].substring(decimalCheck[2].length-2,decimalCheck[2].length-1) == "")
                    	{
                    	clean = decimalCheck[0]+ '.' + decimalCheck[1]  + decimalCheck[2];
                    	}
                    else
                    	{
                    	clean = decimalCheck[0] + decimalCheck[1] + '.' + decimalCheck[2];
                    	}
                }
                else if(decimalCheck.length == 2){
                    decimalCheck[1] = decimalCheck[1].slice(0,2);
                    clean =decimalCheck[0] + '.' + decimalCheck[1];
                }
                else
                	{
                	clean =decimalCheck[0];
                	}

                if (val !== clean) {
                	ctrl.$setViewValue(clean);
                	ctrl.$render();
                }
                return clean;
              });

            elem.bind('blur', function(event) {
                var plainNumber = elem.val().replace(/[^\d|\-+|\.+]/g, '');
                
                elem.val($filter(attrs.format)(plainNumber, ""));
            });
        }
    };
}])
.directive('popover', function () {
    return {
        restrict:'A',
        scope: { item: '=popover' },
        link: function(scope, element, attrs)
        {
            scope.$watch('item', function(item) {
              
                
                $(element).popover({ 
                    title: item.name,
                    content: item.tooltip
                });
                
                item.newProp = "done " + item.name;
            });
        }
    };
})
.directive('decimalPlaces',function(){
    return {
    	
        link:function(scope,ele,attrs){
            ele.bind('keypress',function(e){
            	
                var newVal=$(this).val()+(e.charCode!==0?String.fromCharCode(e.charCode):'');
                if($(this).val().search(/(.*)\.[0-9][0-9]/)===0 && newVal.length>$(this).val().length){
                    e.preventDefault();
                }
            });
        }
    };
});
app.filter('percent', function () {
    return function (input,scope, elem, attrs, ctrl) {
        if (!input || isNaN(input)) {
            return;
        } else {
            return input;
        }
    };
});
