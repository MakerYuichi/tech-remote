/* HEADER INFO
+  File NAME 	: quotation_controller.jsp
+  PURPOSE		: 
+  CREATED BY	: 
+  CREATION DATE	: 
+  INITIAL VERSION : 1.0.0.0
+  **********************************************************************************************************************************
+  VERSION NO   UPDATED BY       UPDATED ON      REASON FOR CHANGE
+  1.0.0.1		Nishant 		 29-May-2018	 added validation regarding MORATORIUM and lease period
+  1.0.0.2		NIshant			 1-Jun-2018		 added validation regarding amc bug rows
+  1.0.0.3		Nishant			 7-Jun-2018		 added validation regading fms other charge and franking charge greater than 0 
+  1.0.0.4		Nishant			 12-Jun-2018	 enable add on service amount on-change for first time, monthly charge bug on scheme change
+  1.0.0.5		Ravi Kumar		 14-Jun-2018	 issue in amc date sequence
   1.0.0.6		Nishant			 2-Jul-2018		 UAT bug id: 60183 amount equivalent to default amount in monthly charges. 
   1.0.0.7		Ravi			 3-Jul-2018		 UAT bug id: 60014 market rv slab not getting refreshed first time.
   1.0.0.8		Nishant			 11-Jul-2018	 UAT bug id: 60856 change in validation regarding MORATORIUM and lease period
   1.0.0.9		Nishant			 19-Jul-2018	 UAT Bug Id: 60850 removing FMS other charge validation
   1.0.0.10		Nishant			 19-Jul-2018	 UAT Bug Id: 60903 amc section multiple row issue.
   1.0.0.11		Nishant			 30-Jul-2018	 UAT Bug Id: 60919 percent validation on forclosure section
   1.0.0.12		Apurva Shukla    7-Aug-2018		 Penalty percent Cr
   1.0.0.13		Nishant			 13-Aug-2018	 UAT bug: 61792 highlight flag change on onload
   1.0.0.14		Nishant			 13-Aug-2018	 UAT bug :62065 comma issue on cost fields.
+  1.0.0.15     Apurva Shukla 	 30-Aug-2018     Set monatorium 0 by default
   1.0.0.16		Nishant Bansal	 2-Sep-2018		 Disallowance franking change amount calulation on Load
   1.0.0.17		Nishant Bansal	 5-Sep-2018		 calling ajax on marketing rv % in case of rv_type others
   1.0.0.18		Nishant bansal	 11-Sep-2018	 QA Bug :57641 disable tyre and battery row in case of ol/oli
   1.0.0.19		Nishant Bansal	 20-Sep-2018	 QA Bug 63090: refresh value on select of maintainance.
   1.0.0.20		Nishant Bansal	 16-Oct-2018	 tenure = lease + moratorium for amc and ext section.
   1.0.0.21		Nishant Bansal	 17-Oct-2018	 Validation on lease period and moratorium and rental
   1.0.0.22		Nishant Bansal	 22-Oct-2018	 save of auto franking per.
   1.0.0.23		Apurva shukla	 12-Dec-2018	 interest amount is not getting round off
   1.0.0.24		Nishant bansal	 18-Dec-2018	 ust error 21 default value oin case of FL and FLI.
   1.0.0.25		Ravi Kumar  	 24-Dec-2018	 disabled the function to calculate interest in advance.
   1.0.0.26		Nishant			 26-Dec-2018	 Null handling of interest on adv.
   1.0.0.27		Apurva Shukla	 28-Dec-2018	 Lease ITC Avail Options CR 
   1.0.0.28		Nishant Bansal	 11-Jan-2019	 lease+tenure to get maintainance amount and residual value.
   1.0.0.29		Apurva Shukla	 12-FEB-2019	 regarding copy quotation CR
   1.0.0.30		Nishant Bansal	 26-Feb-2019	 Fixing copy quotation CR. reset issue of rows and calling method.
   1.0.0.31		Nishant Bansal	 11-Mar-2019	 Bug 68804: VAS should be non mandatory for ol/oli
   1.0.0.32		Ravi Kumar 	     03-Apr-2019	 code change for applicability validation and add monthly charges
   1.0.0.33		Apurva shukla 	 06-Apr-2019	 changes made for amc and extended warranty row update
   1.0.0.34		Apurva shukla 	 24-May-2019	 Exponantial in maintainance string
   1.0.0.35		Apurva shukla 	 31-May-2019	 RVValue CR its based on moratorium and annual usage
   1.0.0.36		Apurva shukla 	 19-july-2019	 no need to reset amount and Structure in AMC details so comment the code
   1.0.0.37		Ravinder Kaur 	 22-july-2019	 getDefaultValuesFlagForAddOnServices is set for Add On Services.
   1.0.0.38		Ravinder Kaur	 13-AUGUST-2019	changes regarding Masters of vendor(Dealer).
   1.0.0.39		Ravinder Kaur	 18-Sep-2019      Dealer lob changes for search.
   1.0.0.40		Sunny Pathak	 11-Oct-2019      UAT bug :foreclosuresection Should be disabled for all the roles except Marketing 
   1.0.0.41		Ravi        	 24-Oct-2019	 code changes for allowed to enter upto 4 decimal  places
   1.0.0.42		Ravi        	 22-Nov-2019      Code changes for save same data with multiple hits.
   1.0.0.43		Sunny Pathak	 12-Mar-2020	  Foreclosure remarks enabled for marketing user
   1.0.0.44		Ravinder Kaur		07-APR-2020		viewModeFlag passed to every master.
   1.0.0.45		Ravi        	 25-Apr-2020      Code changes for block validation in case of transter without terms & condition.
   1.0.0.46		Sunny Pathak	 21-May-2020		Blocked all validations for DM module
   1.0.0.47		Sunny Pathak	 10-Jul-2020		validation blocked at dm level	
   1.0.0.48		Sunny Pathak	 29-Sep-2020		Changes for DM workflow CR
   1.0.0.49		Sunny Pathak	 29-Oct-2020		Code changes to disable dm flow if user is other than marketing user.
   1.0.0.50		 Sunny Pathak	 14-Dec-2020	 	CR - Actual Date Billing Cycle.
   1.0.0.51		Sunny Pathak	 13-Jan-2021		Added changes for rv value calculation
   1.0.0.46.1	Ravi Kumar   	 21-Jan-2020		Blocked all validations for DM module
   1.0.0.53		Sunny Pathak	 19-Mar-2021		Added a new condition for disable flag of initiate button.
   1.0.0.54     Ahtesham Husain  10-OCT-2022       Lease ITC avail option should be yes/no on Quotation screen to 114435: Lease ITC avail option should be mandatory for selections (yes/no) on Quotation screen
   1.0.0.55		Priyanka Soni		28-Oct-2022		CR:- User Logout on EOD
   1.0.0.56	   Tanisha Agarwal 	05-Aug-2024	 	E Sign Document
   1.0.0.57    Narottam Biswal  11-Aug-2015     Maintenance Factoring without OTP
   1.0.0.58     Sanchi Agarwal   18-Sep-2026    RV Amount to be allowed to be entered when RV Type is fixed
   1.0.0.59     Sanchi Agarwal   15-Oct-2026    Added Documents Not Required option to Agreement Signing Type dropdown
   **********************************************************************************************************************************
 */

'use strict';
var app= angular.module("qtoApp",[]);
app.controller('QuotationController', function($scope, $http,$window,$filter,$rootScope ) {
// declarations
	

	var responseFlag = "Y";
	$scope.screenViewMode=screenViewMode;
	
	
	$scope. disabledAllFields=false;//  code  for view only mode start by ravi
	if(verticalMenuViewMode=="1200003020" || verticalMenuViewMode=="1200004012"){$scope. disabledAllFields=true; $scope.disableForeclosureSection=true;document.getElementById("START_DATE_Link").removeAttribute('href');document.getElementById("START_DATE_Link").removeAttribute('onclick');}else{if(screenViewMode=="V"){$scope.disabledAllFields=true;$scope.disableForeclosureSection=true;document.getElementById("START_DATE_Link").removeAttribute('href');document.getElementById("START_DATE_Link").removeAttribute('onclick');}else{$scope.disabledAllFields=false;$scope.disableForeclosureSection=false;}}
			  //  code  for view only mode end by ravi
	
	if(entityId == "1000000008" && staticInfo.PC_STATIC_INFO_DATA[0].TRANSFER_FLG == "WITHOUT")//added by 1.0.0.48
	{
		$scope.disabledAllFields=true;
		document.getElementById("START_DATE_Link").removeAttribute('href');
		document.getElementById("START_DATE_Link").removeAttribute('onclick');
		
		
	}	
	
	
	
	//1.0.0.48 start 
	$scope.is_dmScreen = false;
	if(entityId == "1000000008"){
		$scope.is_dmScreen = true;
	}
	//1.0.0.48 end
	
	//1.0.0.52 add start
	$scope.disableSdSection = false;

	if(entityId == "1000000008" && (staticInfo.PC_STATIC_INFO_DATA[0].DM_STATUS =="CLOSED" || staticInfo.PC_STATIC_INFO_DATA[0].DM_STATUS =="FORECLOSED" || staticInfo.PC_STATIC_INFO_DATA[0].DM_STATUS =="CANCELLED" || staticInfo.PC_STATIC_INFO_DATA[0].DM_STATUS =="REJECT")){
		$scope.disableSdSection = true;
	}
	
	if(entityId == '1000000008'){
		document.getElementById("securityDepositDiv").style.display = 'block';
	}else{
		document.getElementById("securityDepositDiv").style.display = 'none';
	}
	//1.0.0.52 add end
	
//  code for view only mode end by Nishant for DM
	var objects = $scope.JSONfromServer;
	
	$scope.isLeasePeriodDisable=true;
	$scope.isDisabledMarket=true;
	$scope.isDisabledFixed = true;
	$scope.isResidualValueEnabled = false; // 1.0.0.58
	$scope.isDisabledFixedPercentage = false;  // 1.0.0.58
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
	$scope.extDetails=[];
	$scope.valueAdded=[];
	$scope.applicability=[];
	//$scope.applicability0=[];
	$scope.SCHEME=[];
	$scope.CHARGE_TYPE=[];
	$scope.AGREEMENT_TYPE=[];
	$scope.AGREEMENT_SIGN_TYPE=[];//1.0.0.56
	$scope.LEASE_BILLING_TYPE=[];
	
	$scope.LEASE_ITC_AVAIL_OPTIONS_TYPE=[];//1.0.0.27
	$scope.RENTAL_LOGIC_MASTER=[];//1.0.0.50
	
	//1.0.0.52 add start
	$scope.ONLINE_PAYMENT_TYPE_MASTER=[];//1.0.0.52
	$scope.ONLINE_PAYMENT_TYPE="0";
	//1.0.0.52 add end
	
	$scope.TOTAL_MAINTENANCE="0.00";
	//staticInfoObject.LEASE_BRANCH
	$scope.ALLOW_LEASE_TYRE = "N";//1.0.0.18
    $scope.ALLOW_LEASE_BATTERY="N"; //1.0.0.18
	$scope.monthlyCharges=[];
	  $scope.amcDetails=[];
	 var jsonStr; 
	//$scope.addExtendedWarrentyRow=[];
	 
	 $scope.PRODUCT_TYPE=[];
	 
	 $scope.FORECLOSURE_METHOD_TYPE=[];
	 $scope.REPAYMENT_MODE_TYPE=[];
	
	var arrMaster = new Array;
	var arrAddOnServices = new Array;
	var arrRentalDtl = new Array;
	var arrValidationValues = new Array;
	var arrExtWarrenty = new Array;
	var arrMaintDtl = new Array;
	var arrMonthlyCharges = new Array;
	var arrAMCDtl = new Array;
	
	var branch_id = staticInfoBranchId1 ; //"1000000223"; //dummy data to fetch from static info
	var quotation_id = quotationId;
	var arrProductMaster = new Array;
	var arrLOCMaster = new Array;
	var dateDiffLogic;
	var valueAddedStr="";
	var removeChargeIdStr= "";
	var removeExtendedWarrentyIdStr="";
	var isAMCEnableFlag="";
	var isMaintEnableFlag="";
	var isFirstTimeMonthlyCharges = true;
	$scope.flag = 'N';
	var temp_autoFrankingPer ="";//1.0.0.22
	
	$scope.disableEquipFlag = false;
	
	$scope.getDefaultValuesFlagForAddOnServices = true; //1.0.0.37 added
	
	$scope.screenIsShow=temp_screenId; //1.0.0.57
	
	//1.0.0.52 add start
	$scope.disableInitiateBtn = !angular.isUndefined(staticInfo.PC_STATIC_INFO_DATA[0].DISABLE_ONLINE_PYMNT_FLG)?staticInfo.PC_STATIC_INFO_DATA[0].DISABLE_ONLINE_PYMNT_FLG:"N";//1.0.0.53
	$scope.disabeldInitiateBtn =function()
	{
		angular.forEach($scope.ONLINE_PAYMENT_TYPE_MASTER, function( obj)
				{
			        if($scope.ONLINE_PAYMENT_TYPE==obj.ID)
			        	{
			        	$scope.initiateDisabledFlag=($scope.disableInitiateBtn === "Y" || obj.ENABLE_PRE_SANC_PYMNT_LINK=="D")?true:false; //new condition of DISABLE_ONLINE_PYMNT_FLG added by 1.0.0.53
			        	   
			        	}
				});
		
	}

	$scope.initiatePayment = function()
	{
		$scope.initiateDisabledFlag=true;
		$.ajax({  
			type : "post",   
			url : "initiateSecurityDeposit.sprg?isAjaxCalling=Y", //1.0.0.55 added  isAjaxCalling=Y   
			data:{paymentType: $scope.ONLINE_PAYMENT_TYPE},
			success: function(response) 
				{
				var jsonStr = JSON.parse(response);
				if(jsonStr.Status=="SessionExpired") //1.0.0.55
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
	
	//start 1.0.0.57 
	$scope.getMaintenanceStatScreen = function()
	{
		var paramList=staticInfo.PC_STATIC_INFO_DATA[0].DM_ID;

		   var url =   "dynamicWindow.do?actionPerformed=dynamicWindow&windowId=1000000010&paramList="+paramList+"";
		   
		   var self=window.open(url,"dynamicWindow","width=800px,height=500px,left=400px,titlebar=yes,scrollbars=yes,toolbar=no,maximize=no,menubar=no,minimize=no,statusbar=no");
	}
	//end 1.0.0.57

	$scope.getPaymentDtl = function(){
		$.ajax({  
			type : "post",   
			url : "sdPaymentDtls.sprg?isAjaxCalling=Y", //1.0.0.55 added  isAjaxCalling=Y   
			success: function(response) 
				{
				var jsonStr = JSON.parse(response);
				if(jsonStr.Status=="SessionExpired") //1.0.0.55
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
			url : "updatePaymentStatus.sprg?isAjaxCalling=Y", //1.0.0.55 added  isAjaxCalling=Y   
			success: function(response) 
				{
				var jsonStr = JSON.parse(response);
				if(jsonStr.Status=="SessionExpired") //1.0.0.55
					{
						window.location.href = 'llm/ReLogin.jsp?unknownError=Your session has been killed. Please login again.';
						return;
					}
				alert((jsonStr.Message).toUpperCase());
				$scope.$apply();
					
				}
		});
	}
	//1.0.0.52 add end
	
	//1.0.0.41 add start
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
	//1.0.0.41 add end
	//(sessionStorage.getItem("setActionID"))
		//1.0.0.56
		$scope.screenIdFromRequest=entityId!=undefined && entityId=="1000000008"?true:false;
	//function working on onload of the Screen "Masters population"
	$scope.getJSONfromServer = function(){
		
		/*$.ajax({
	  		 url: "quotationDetailsValue.sprg?isAjaxCalling=Y", //1.0.0.55 added  isAjaxCalling=Y
	  		 type: "GET" ,
	  			success: function(response) 
	  			{
	  			if(response!=null && response!="") 
	  			{*/
	  				
	  					 jsonStr = JSON.parse(mastersListStr);
	  					$scope.businessDate=jsonStr.businessDate;//1.0.0.29
	  					//console.log("ravi")
	  					 //console.log(jsonStr)
	  					 arrLOCMaster = jsonStr.LOCMasterDetail;
	  					 if(jsonStr.quotationDetails != undefined)
	  						 {
	  						 	arrValidationValues = jsonStr.quotationDetails.GET_VALIDATION_VALUES;
	  						 	$scope.penalty= jsonStr.quotationDetails.GET_PENALTY_PERCENT; // 1.0.0.12
	  						 }
	  					
	  					
	  					
	  					arrMaster = !angular.isUndefined(jsonStr.rentalMaster )?jsonStr.rentalMaster[0]:"";
	  					$scope.ONLINE_PAYMENT_TYPE_MASTER = jsonStr.ONLINE_PAYMENT_TYPE_MASTER;//1.0.0.52
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
	  						   // apurva change 1.0.0.27 start
	  						else if (arrMaster[i].PARAM_NAME =="LEASE ITC AVAIL OPTIONS"){
	  							   $scope.LEASE_ITC_AVAIL_OPTIONS_TYPE.push ({
	  								 'ID':arrMaster[i].ID,
	  								 'NAME':arrMaster[i].NAME
	  							   });
	  						   } 
	  							//// apurva change 1.0.0.27 end 
	  						   //1.0.0.50 add start
	  						else if (arrMaster[i].PARAM_NAME =="RENTAL LOGIC"){
	  							   $scope.RENTAL_LOGIC_MASTER.push ({
	  								 'ID':arrMaster[i].ID,
	  								 'NAME':arrMaster[i].NAME
	  							   });
	  						   } 
	  						   //1.0.0.50 add end
	  						else if (arrMaster[i].PARAM_NAME =="AGREEMENT SIGNING TYPE"){
	  							   $scope.AGREEMENT_SIGN_TYPE.push ({
	  								 'ID':arrMaster[i].ID,
	  								 'NAME':arrMaster[i].NAME
	  							   });
	  						   } //1.0.0.56
					 }; //end of for
					 // 1.0.0.59 - Add new value "Documents Not Required" as per requirement 3.3.1
					 $scope.AGREEMENT_SIGN_TYPE.push({
						   'ID': 'DOCUMENTS_NOT_REQUIRED',
						   'NAME': 'Documents Not Required'
					 });
	  					 if(jsonStr.quotationDetails != undefined)
  						 {
	  						 arrAddOnServices = (!angular.isUndefined(jsonStr.quotationDetails.GET_ADD_ON_SERVICES) && jsonStr.quotationDetails.GET_ADD_ON_SERVICES!=0)? jsonStr.quotationDetails.GET_ADD_ON_SERVICES:"" ;
  						 }
	  					arrProductMaster = jsonStr.productDetails;
	  					//console.log("product master")
	  					//console.log(arrProductMaster)
	  					for(var i =0;i< arrProductMaster.length;i++){
	  						if(arrProductMaster[i].masterParentId == branch_id){
	  							//alert(arrProductMaster[i].productId + " value:: "+ arrProductMaster[i].productName);
	  							$scope.PRODUCT_TYPE.push({
	  								'ID':arrProductMaster[i].productId,
	  								'VALUE':arrProductMaster[i].productName
	  							});
	  						}
	  					} 
	  					/*console.log("product type")
	  					console.log($scope.PRODUCT_TYPE)*/
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
	  					//for Dm by nishant
	  					$scope.FORECLOSURE_DISC_PERC = arrRentalDtl.FORECLOSURE_DISC_PERC!=null ?arrRentalDtl.FORECLOSURE_DISC_PERC : "0.00";
	  					$scope.DISCOUNTING_PER_HIGHLIGHT_FLAG = !angular.isUndefined(arrRentalDtl.DISCOUNTING_PER_HIGHLIGHT_FLAG)?arrRentalDtl.DISCOUNTING_PER_HIGHLIGHT_FLAG:"N";
	  					$scope.FORECLOSURE_REMARKS = arrRentalDtl.FORECLOSURE_REMARKS!=null? arrRentalDtl.FORECLOSURE_REMARKS : "";
	  					$scope.FORECLOSURE_PENALTY_PERC = arrRentalDtl.FORECLOSURE_PENALTY_PERC!=null ? arrRentalDtl.FORECLOSURE_PENALTY_PERC : "0.00";
	  					$scope.LPI_PERCENT = arrRentalDtl.LPI_PERCENT!=null? arrRentalDtl.LPI_PERCENT : "0.00";
	  					$scope.INTEREST_ON_ADVN = arrRentalDtl.INTEREST_ON_ADVN!=null ? arrRentalDtl.INTEREST_ON_ADVN : "0.00";
	  					$scope.FORECLOSURE_METHOD = !angular.isUndefined(arrRentalDtl.FORECLOSURE_METHOD)?arrRentalDtl.FORECLOSURE_METHOD:"0";
	  					$scope.REPAYMENT_MODE = !angular.isUndefined(arrRentalDtl.REPAYMENT_MODE)?arrRentalDtl.REPAYMENT_MODE:"0";
	  					$scope.PROPOSED_CASHFLOW_IRR = !angular.isUndefined(arrRentalDtl.PROPOSED_CASHFLOW_IRR)?arrRentalDtl.PROPOSED_CASHFLOW_IRR:"0.00";
	  					
	  					$scope.disabeldInitiateBtn();//1.0.0.52
	  					//$scope.getPaymentDtl();//1.0.0.52
	  					
	  					//1.0.0.40 add start
	  					$scope.disableForeclosureRemarks = true;//added by 1.0.0.43
	  					$scope.USER_TYPE = !angular.isUndefined(arrRentalDtl.USER_TYPE)?arrRentalDtl.USER_TYPE:"";
	  					if(staticInfo.PC_STATIC_INFO_DATA[0].RECOMMEND_FLAG == "Y"){
	  						$scope.disableForeclosureSection = true;
	  						//1.0.0.49 add start
	  						if(entityId == "1000000008"){
	  							$scope.disabledAllFields=true;
	  						}
	  						else{
	  							$scope.disabledAllFields=false;
	  						}
	  						//1.0.0.49 add end
	  					}
	  					//1.0.0.49 add start
	  					else if($scope.USER_TYPE != "MKT"){
	  						if(entityId == "1000000008"){
	  							$scope.disabledAllFields=true;
	  							$scope.disableForeclosureSection = true;
	  						}
	  					}
	  					//1.0.0.49 add end
	  					//1.0.0.43 changes start
	  					else{
	  						$scope.disableForeclosureRemarks = false;
	  					}
	  					//1.0.0.43 changes end
	  					//1.0.0.40 add end
	  					//end dm
	  					
	  					$scope.ANNUAL_USAGE = arrRentalDtl.ANNUAL_USAGE_IN_KMS!=null ?arrRentalDtl.ANNUAL_USAGE_IN_KMS : "0.00";
	  					$scope.EXCESS_KM = arrRentalDtl.EXTRA_PER_KM_CHARGE!=null? arrRentalDtl.EXTRA_PER_KM_CHARGE : "0.00";
	  					$scope.MORATORIUM = arrRentalDtl.MORATORIOUM_IN_MNTHS!=null ? arrRentalDtl.MORATORIOUM_IN_MNTHS : "0";
	  					$scope.RSD = arrRentalDtl.RENTAL_START_DATE!=null ? arrRentalDtl.RENTAL_START_DATE : "";
	  					$scope.START_DATE = arrRentalDtl.QUOTE_START_DATE!=null?arrRentalDtl.QUOTE_START_DATE :jsonStr.businessDate; //take business date if not coming from DB
	  					//set all data from get
	  					$scope.PRODUCT_TYPE1 = !angular.isUndefined(arrRentalDtl.PRODUCT_ID)?arrRentalDtl.PRODUCT_ID:"0";
	  					if($scope.PRODUCT_TYPE1!="")
	  						$scope.getSchemeList();
	  					$scope.SCHEME1=  !angular.isUndefined(arrRentalDtl.SCHEME_ID)?arrRentalDtl.SCHEME_ID:"0";
	  					//$scope.rental_freq = !angular.isUndefined(arrRentalDtl.RENTAL_CYCLE_ID)?arrRentalDtl.RENTAL_CYCLE_ID:"0";
	  					if($scope.SCHEME1!="")
	  						$scope.calculateEndDate(arrRentalDtl); //not showing rental frequency.... not populating...
	  					//$scope.$apply();
	  					
	  				/*};//end of if
	  			},//end of success
	  			error : function(response) {
	  				//alert("error");
	  			}
		});*/
	  					
	};
	//1.0.0.56 start
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
	
	};
	//1.0.0.56 end
	//fetching data on get and Showing on screen after the save of the quotation
	$scope.ajaxResponseMethod = function(arrRentalDtl)
	{ 
			if(!angular.isUndefined(arrRentalDtl)){
    		$scope.EXPIRING_DATE = !angular.isUndefined(arrRentalDtl.QUOTATION_EXPIRY_DATE)?arrRentalDtl.QUOTATION_EXPIRY_DATE :"";
			$scope.LEASE_PERIOD = arrRentalDtl.LEASE_PERIOD_IN_MNTHS!=null?arrRentalDtl.LEASE_PERIOD_IN_MNTHS:"0";
			$scope.rental_cycle = !angular.isUndefined(arrRentalDtl.BILLING_CYCLE_ID)?arrRentalDtl.BILLING_CYCLE_ID:"0";
			if(Number($scope.RENTAL_FREQUENCY.length)>0)
				{$scope.rental_freq = !angular.isUndefined(arrRentalDtl.RENTAL_CYCLE_ID)?arrRentalDtl.RENTAL_CYCLE_ID:"0";}
			else{$scope.rental_freq = "0";}
			$scope.rental_type = !angular.isUndefined(arrRentalDtl.RENTAL_TYPE_ID)?arrRentalDtl.RENTAL_TYPE_ID:"0";
			$scope.RENTAL_MODE_VALUE = !angular.isUndefined(arrRentalDtl.RENTAL_MODE_ID)?arrRentalDtl.RENTAL_MODE_ID:"0";
			$scope.AGREEMENT_SIGNING_TYPE = !angular.isUndefined(arrRentalDtl.AGREEMENT_SIGNING_TYPE)?arrRentalDtl.AGREEMENT_SIGNING_TYPE:"0";//1.0.0.56
			
			$scope.setSelectBoxTitle();
			$scope.AGREEMENT_TYPE_VALUE = !angular.isUndefined(arrRentalDtl.AGREEMENT_TYPE_ID)?arrRentalDtl.AGREEMENT_TYPE_ID:"0";
			$scope.ANNUAL_USAGE = !angular.isUndefined(arrRentalDtl.ANNUAL_USAGE_IN_KMS)?arrRentalDtl.ANNUAL_USAGE_IN_KMS:"0.00";
			$scope.frnaking_type = !angular.isUndefined(arrRentalDtl.FRANKING_TYPE_ID)?arrRentalDtl.FRANKING_TYPE_ID:"0";
			$scope.FRANKING_CHARGE_AMOUNT= arrRentalDtl.FRANKING_CHRG_AMOUNT!=null?arrRentalDtl.FRANKING_CHRG_AMOUNT:"0.00";
			$scope.AUTO_FRANKING_PER= arrRentalDtl.AUTO_FRANKING_PER!=null?arrRentalDtl.AUTO_FRANKING_PER:"0.00";
			temp_autoFrankingPer = $scope.AUTO_FRANKING_PER;//1.0.0.22
			$scope.getFrankingAmount("autoPopulate");//1.0.0.16
			$scope.RV_TYPE  = !angular.isUndefined(arrRentalDtl.RESIDUAL_TYPE_ID)?arrRentalDtl.RESIDUAL_TYPE_ID:"0";
			$scope.getRVSlabType();
			//$scope.market = { RV_SLAB1: arrRentalDtl.MARKET_SLAB_ID};  //market.RV_SLAB1
			angular.element('#RV_SLAB1').val(!angular.isUndefined(arrRentalDtl.MARKET_SLAB_ID)?arrRentalDtl.MARKET_SLAB_ID:""); //market slab
			angular.element('#fixed_rv').val(!angular.isUndefined(arrRentalDtl.FIXED_PERCENTAGE)?arrRentalDtl.FIXED_PERCENTAGE:"");
			
			//angular.element('#rv_calculation').val(!angular.isUndefined(arrRentalDtl.FIXED_CALCULATION_ON_ID)?arrRentalDtl.FIXED_CALCULATION_ON_ID:"");
			
			$scope.market ={PERCENT:!angular.isUndefined(arrRentalDtl.MARKET_PERCENTAGE)?arrRentalDtl.MARKET_PERCENTAGE:"0.00",RV_SLAB1: !angular.isUndefined(arrRentalDtl.MARKET_SLAB_ID)?arrRentalDtl.MARKET_SLAB_ID:"0"};////1.0.0.7 ,1.0.0.41start
			$scope.fixed ={CALC:!angular.isUndefined(arrRentalDtl.FIXED_CALCULATION_ON_ID)?arrRentalDtl.FIXED_CALCULATION_ON_ID:"0"};
			
			// 1.0.0.58 start
			
			if($scope.fixed.CALC == "1000000008"){
			$scope.isDisabledFixedPercentage = true;
			$scope.isResidualValueEnabled = true;
			}else{
			}
			
			// 1.0.0.58 end
			
			$scope.hilightedResidualValueFlag = !angular.isUndefined(arrRentalDtl.HIGHLIGHT_AMOUNT)?arrRentalDtl.HIGHLIGHT_AMOUNT:'N';//1.0.0.13
			$scope.RESIDUAL_VALUE1 = arrRentalDtl.RESIDUAL_VALUE!=null?arrRentalDtl.RESIDUAL_VALUE:"0.00";
			$scope.PERCENT_VALUE = arrRentalDtl.FIXED_PERCENTAGE!=null?arrRentalDtl.FIXED_PERCENTAGE:"0.00";  // 1.0.0.58
			if($scope.hilightedResidualValueFlag=='Y'){//1.0.0.13
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
			$scope.CHARGE_TYPE_VALUE =  !angular.isUndefined(arrRentalDtl.INT_ON_ADV_CHRG_TYPE_ID)? arrRentalDtl.INT_ON_ADV_CHRG_TYPE_ID:"0" ;//charge_type
			$scope.NO_OF_DAYS = arrRentalDtl.INT_ON_ADV_DAYS!=null?arrRentalDtl.INT_ON_ADV_DAYS:"";
			$scope.INTEREST_RATE = arrRentalDtl.INT_RATE_OF_INT_ON_ADV!=null?arrRentalDtl.INT_RATE_OF_INT_ON_ADV:"0.00";
			$scope.AMOUNT_ADVANCE= arrRentalDtl.ADV_AMNT_OF_INT_ON_ADV;
			$scope.INTEREST_AMT = arrRentalDtl.INT_AMNT_OF_INT_ON_ADV!=null?arrRentalDtl.INT_AMNT_OF_INT_ON_ADV:"";//1.0.0.26
			 if(jsonStr.quotationDetails != undefined)
				 {
					arrMaintDtl = jsonStr.quotationDetails.GET_MAINTENANCE_DTL;
					arrMaintDetails = !angular.isUndefined(arrMaintDtl[0])?arrMaintDtl[0]:"";
					arrExtWarrenty = jsonStr.quotationDetails.GET_EXT_WARR_DTL;
					arrMonthlyCharges = jsonStr.quotationDetails.GET_MONTHLY_CHARGES;
					arrAMCDtl = jsonStr.quotationDetails.GET_AMC_DTL;
				 }
			 arrMaintDetails = !angular.isUndefined(arrMaintDtl[0])?arrMaintDtl[0]:"";
			$scope.populateData();
			//$scope.$apply();
			
			if(assetCatId == "1000000003")
				{
					$scope.CHARGE_TYPE_VALUE='1000000001';
					$scope.disableEquipFlag = true;
					$scope.ANNUAL_USAGE = 0;
					$scope.EXCESS_KM = 0;
					
				}

			$scope.isChargeType();
			$scope.BILLING_TYPE_ID = !angular.isUndefined(arrRentalDtl.BILLING_TYPE_ID)?arrRentalDtl.BILLING_TYPE_ID:"0";
			$scope.LEASE_ITC_AVAIL_OPTIONS = !angular.isUndefined(arrRentalDtl.LEASE_ITC_AVAIL_OPTIONS)?arrRentalDtl.LEASE_ITC_AVAIL_OPTIONS:"0";//1.0.0.27 //1.0.0.54
			$scope.LEASE_ITC_AVAIL_OPTIONS_FLG = !angular.isUndefined(arrRentalDtl.LEASE_ITC_AVAIL_OPTIONS_FLG)?arrRentalDtl.LEASE_ITC_AVAIL_OPTIONS_FLG:"N";//1.0.0.27
			$scope.RENTAL_LOGIC = !angular.isUndefined(arrRentalDtl.RENTAL_LOGIC)?arrRentalDtl.RENTAL_LOGIC:"1000000001";//1.0.0.501.0.0.50
			$scope.RENTAL_LOGIC_FLAG = !angular.isUndefined(arrRentalDtl.RENTAL_LOGIC_FLAG)?arrRentalDtl.RENTAL_LOGIC_FLAG:"N";//1.0.0.50
			$scope.enableFMSCharge();
			}
	};
	var arrMaintDetails ;
	// method for data popultaion on the screen after save of the quotation
	$scope.populateData = function(){
		
			//setting values on Maintenance detail
			if(!$scope.isMaintEnable){
					$scope.MAINTENANCE_SLAB = !angular.isUndefined(arrMaintDetails.MAINT_SLAB_ID) ?arrMaintDetails.MAINT_SLAB_ID:"0";
					$scope.TOTAL_MAINTENANCE = !angular.isUndefined(arrMaintDetails.AMOUNT) ?arrMaintDetails.AMOUNT:"0.00";
					$scope.POOL_CAR = !angular.isUndefined(arrMaintDetails.POOL_CAR_APPLICABLE) ?arrMaintDetails.POOL_CAR_APPLICABLE:"N";
			}
			if(!$scope.isAMCEnable){
				$scope.AMC_details = !angular.isUndefined(arrMaintDetails.AMC_APPLICABLE)?arrMaintDetails.AMC_APPLICABLE:"N";
				//$scope.getAMCTable();
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
				$("#MASTER_AMOUNT"+k).val(arrAddOnServices[k].MASTER_AMOUNT!=null && arrAddOnServices[k].MASTER_AMOUNT!=undefined?(arrAddOnServices[k].MASTER_AMOUNT!="0"?arrAddOnServices[k].MASTER_AMOUNT:"0.00"):"");//1.0.0.24
				
				if(arrAddOnServices[k].APPLICABILITY=='1000000001'){
					$("#QTY"+k).prop('disabled', true); 
					$("#SERVICE_AMOUNT"+k).prop('disabled', true); 
					 $("#REMARKS"+k).prop('disabled', true); 
				} else{
					
					$("#QTY"+k).val(arrAddOnServices[k].QTY!=null && arrAddOnServices[k].QTY!=undefined?arrAddOnServices[k].QTY:"");
					$("#SERVICE_AMOUNT"+k).val(arrAddOnServices[k].SERVICE_AMOUNT!=null && arrAddOnServices[k].SERVICE_AMOUNT!=undefined?(arrAddOnServices[k].SERVICE_AMOUNT!="0"?arrAddOnServices[k].SERVICE_AMOUNT:"0.00"):"");//1.0.0.24
					 $("#REMARKS"+k).val(arrAddOnServices[k].REMARKS!=null && arrAddOnServices[k].REMARKS!=undefined?arrAddOnServices[k].REMARKS:"");
				}
			}
			if(!angular.isUndefined(valueAddedStr)){
			//for(var i=0;i<valueAddedStr.length;i++){
				for(var k=0,i=0;k<arrAddOnServices.length;k++,i++)
				{
					if(valueAddedStr[i])
					{
						valueAddedStr[i].RENTAL_SERVICE_ID = arrAddOnServices[k].RENTAL_SERVICE_ID!=undefined && arrAddOnServices[k].RENTAL_SERVICE_ID!=null?arrAddOnServices[k].RENTAL_SERVICE_ID:"";
						valueAddedStr[i].SERVICE_ID = arrAddOnServices[k].SERVICE_ID!=undefined && arrAddOnServices[k].SERVICE_ID!=null?arrAddOnServices[k].SERVICE_ID:"";    
						valueAddedStr[i].SERVICE_NAME =arrAddOnServices[k].SERVICE_NAME!=undefined && arrAddOnServices[k].SERVICE_NAME!=null?arrAddOnServices[k].SERVICE_NAME:"";
						valueAddedStr[i].APPLICABILITY =arrAddOnServices[k].APPLICABILITY!=undefined && arrAddOnServices[k].APPLICABILITY!=null?arrAddOnServices[k].APPLICABILITY:"";
						valueAddedStr[i].QTY =arrAddOnServices[k].QTY!=undefined && arrAddOnServices[k].QTY!=null?arrAddOnServices[k].QTY:"";
						valueAddedStr[i].MASTER_AMOUNT =arrAddOnServices[k].MASTER_AMOUNT!=undefined && arrAddOnServices[k].MASTER_AMOUNT!=null?arrAddOnServices[k].MASTER_AMOUNT:"0.00";
						valueAddedStr[i].SERVICE_AMOUNT =arrAddOnServices[k].SERVICE_AMOUNT!=undefined && arrAddOnServices[k].SERVICE_AMOUNT!=null?arrAddOnServices[k].SERVICE_AMOUNT:"0.00";
						valueAddedStr[i].REMARKS =arrAddOnServices[k].REMARKS!=undefined && arrAddOnServices[k].REMARKS!=null?arrAddOnServices[k].REMARKS:"";
						
						if(valueAddedStr[i].APPLICABILITY=="1000000001")
							{
							  valueAddedStr[i].quantityEnable="N";
							  valueAddedStr[i].amountEnable="N";//1.0.0.4
							  valueAddedStr[i].remarksEnable="N";//1.0.0.4
							}
						/*else
						{
							  valueAddedStr[i].quantityEnable = "Y";	
						      valueAddedStr[i].amountEnable = 'Y';
						      valueAddedStr[i].remarksEnable = 'Y';
						}*/
					}
				}
			//}
			}
			if(valueAddedStr!="")
				{
				$scope.valueAdded = valueAddedStr;
						
				}
			
			//console.log(valueAddedStr);
			$scope.monthlyCharges = arrMonthlyCharges;
			//$scope.$apply();
	};
	
	/*
	 * Section Rental Details 
	 */
	//getting schemes for selected product
	$scope.getSchemeList = function(){
    	$scope.SCHEME=[];
    	//$scope.SCHEME1=  !angular.isUndefined(arrRentalDtl.SCHEME_ID)?arrRentalDtl.SCHEME_ID:"0";
    	$scope.SCHEME1 = "0";
    	//$scope.calculateEndDate();
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
	// retrieving all scheme related data
    
    var maxDaysInLease=0;	//1.0.0.8   
    
    $scope.calculateEndDate= function(arrRentalDtl){
    	//console.log(arrRentalDtl);
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
   			$scope.expiryDays=arrValidationValues[i].QUOTATION_EXPIRY_DAYS;//1.0.0.29
   			 dateDiffLogic = arrValidationValues[i].DAY_DIFF_LOGIC;
   			 maxDaysInLease = arrValidationValues[i].MAX_LEASE_PERIOD_IN_MNTHS; //1.0.0.8   
   			 
   			 $scope.ALLOW_LEASE_TYRE = arrValidationValues[i].ALLOW_LEASE_TYRE;//1.0.0.18
   			 $scope.ALLOW_LEASE_BATTERY = arrValidationValues[i].ALLOW_LEASE_BATTERY;//1.0.0.18
   			 
   		 }
   	 }
   	 if($scope.START_DATE !=null && $("#START_DATE")[0].value!="" ){
   		 var startDate = new Date($scope.START_DATE);
   		 var endDate = new Date(startDate.getTime() + noOfDays * 24 * 60 * 60 * 1000);
	    	 $scope.EXPIRING_DATE = $filter('date')(endDate, "dd-MMM-yyyy");
	    	 $scope.TEMP_EXPIRING_DATE=$scope.EXPIRING_DATE;
    	} else
   	 {
    		$scope.EXPIRING_DATE = null;
    		$scope.TEMP_EXPIRING_DATE=null;
   	 }
   	
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
	  		 url: "getSchemeDependentDetails.sprg?isAjaxCalling=Y", //1.0.0.55 added  isAjaxCalling=Y
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
		  				//alert("ravi  "+response)
		  				var jsonStr = JSON.parse(response);
		  				if(jsonStr.Status=="SessionExpired") //1.0.0.55
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
		  				
		  				//$scope.rental_freq = !angular.isUndefined(arrRentalDtl.RENTAL_CYCLE_ID)?arrRentalDtl.RENTAL_CYCLE_ID:"0";
		  				isAMCEnableFlag = schemeDependentInfo.PC_AMC_ENABLE;
		  				
		  				if(isAMCEnableFlag=="Y"){
		  					$scope.isAMCEnable = false;
		  					$scope.AMC_details='N';
		  					$scope.getAMCTable();//1.0.0.2
		  					
		  					
		  				} else {
		  					$scope.isAMCEnable = true;
		  					$scope.amcDetails=[];
		  					$scope.AMC_details='N';
		  					//clear table
		  				}
		  				isMaintEnableFlag = (!angular.isUndefined(schemeDependentInfo.PC_MAINTENANCE_ENABLE))?schemeDependentInfo.PC_MAINTENANCE_ENABLE:"N";
		  				//alert(isMaintEnableFlag)
		  				if(isMaintEnableFlag=="Y"){
		  					$scope.isMaintEnable = false;
		  					$scope.MAINTENANCE_SLAB = '0';
		  					$scope.POOL_CAR = 'N';
		  					$scope.TOTAL_MAINTENANCE = "0.00";
		  				}else{
		  					$scope.isMaintEnable = true;
		  					$scope.MAINTENANCE_SLAB="0";
		  					$scope.POOL_CAR="N";
		  					angular.element('#total_maintenence').val("0.00");
		  					//clear off the data
		  				}
		  				if(!angular.isUndefined(schemeDependentInfo.ValueAddedService)){
		  						valueAddedStr = schemeDependentInfo.ValueAddedService;
		  						
		  				}
		  				//remaining add PC_SERIVCES
		  				$scope.valueAddedServices(valueAddedStr);  //create value added table
		  				//$scope.getDefaultValuesFlagForAddOnServices = false; //1.0.0.37 Added	 
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
			        				//alert("414");
			        				$scope.allowAutomaticTransfer="Y";
			        				
			        			}
	    	  				}
		  					
		  					//1.0.0.4 start
		  					angular.forEach($scope.monthlyCharges, function(selected){
		  			        
		  			           	 if(removeChargeIdStr!=""){
		  			           		 if(selected.QUOTATION_CHARGE_ID!="")
		  			           		 removeChargeIdStr=removeChargeIdStr + "^"+ selected.QUOTATION_CHARGE_ID;
		  			           	 }
		  			           	 else {
		  			           		 removeChargeIdStr = selected.QUOTATION_CHARGE_ID;
		  			           	 }
		  			           
		  			        }); 
		  					//1.0.0.4 end
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
   	 }); //end of ajax
   	 }//condition scheme is not null 
   	 else {// if condition is null
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
      
   		// 1.0.0.24 start
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
		
   		//1.0.0.24 end		 
   	 }
   	// $scope.$apply();
   	 var valueAddedStr = $scope.valueAdded;
    };
	
 $scope.CheckLeasePeriod = function(){
 	   var schemeId = $scope.SCHEME1;
   	 
   	 for(var i =0;i<arrValidationValues.length;i++){
   		 if(arrValidationValues[i].SCHEMEID==schemeId){
   			maxDaysInLease=arrValidationValues[i].MAX_LEASE_PERIOD_IN_MNTHS;//1.0.0.8
   			// dateDiffLogic = arrValidationValues[i].DAY_DIFF_LOGIC;
   			
   		 if($scope.RV_TYPE == "1000000001" && $scope.market.RV_SLAB1 == "1000000005")	//market and others
		 {
   			
		 }
   		 else{
   			$scope.getRVValue();
   		 }
   		//	$scope.getRVValue1();
   		 }
   	 }
 	   
 	  var valueEntered =  $scope.LEASE_PERIOD;
 	   if(Number(valueEntered)>Number(maxDaysInLease)){
 		  window.alert("MAXIMUM VALUE OF LEASE PERIOD IN MONTHS CAN BE "+maxDaysInLease+".");//1.0.0.8
 		  $scope.LEASE_PERIOD="0";
 	  } 
 	//1.0.0.10 start  
 	  angular.forEach( $scope.amcDetails, function(selected,key){
      	if(selected.MAINTENANCE_DTL_ID != null && selected.MAINTENANCE_DTL_ID != "" && selected.MAINTENANCE_DTL_ID != undefined)//1.0.0.2
      		{
	        		if(removeExtendedWarrentyIdStr!=null && removeExtendedWarrentyIdStr!="" && removeExtendedWarrentyIdStr!=undefined)
          		{removeExtendedWarrentyIdStr = removeExtendedWarrentyIdStr + "^"+ selected.MAINTENANCE_DTL_ID;
          		}
	            	else{
	            		 removeExtendedWarrentyIdStr = selected.MAINTENANCE_DTL_ID;
	            	}
      		
      		}
	   }); 
 	//1.0.0.10 end
 		 $scope.getAMCTable();
 		 
 		 angular.forEach( $scope.extDetails, function(selected,key){
	        	if(selected.MAINTENANCE_DTL_ID != null && selected.MAINTENANCE_DTL_ID != "" && selected.MAINTENANCE_DTL_ID != undefined)//1.0.0.2
	        		{
		        		if(removeExtendedWarrentyIdStr!=null && removeExtendedWarrentyIdStr!="" && removeExtendedWarrentyIdStr!=undefined)
	            		{removeExtendedWarrentyIdStr = removeExtendedWarrentyIdStr + "^"+ selected.MAINTENANCE_DTL_ID;
	            		}
		            	else{
		            		 removeExtendedWarrentyIdStr = selected.MAINTENANCE_DTL_ID;
		            	}
	        		
	        		}
		   }); 
 		$scope.extDetails = [];
 		$scope.getExtendedWarrTable(); 
 		
 	// } 
 	 if(isMaintEnableFlag=="Y" && $scope.MAINTENANCE_SLAB!= '0' )
 	 { $scope.getMaintenanceAmount();}
 	 
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
    
    //modified by nishant
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
	    	/* else{
	    		 $scope.removeAllRowsOfMonthlyCharge();
	    	 }*/
	    	 
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
        	
          /*  if(selected.selected123){
                newDataList.push(selected);
            }
            else {
           	 */
           	 if(removeChargeIdStr!=""){
           		 if(selected.QUOTATION_CHARGE_ID!="")
           		 removeChargeIdStr=removeChargeIdStr + "^"+ selected.QUOTATION_CHARGE_ID;
           	 }
           	 else {
           		 removeChargeIdStr = selected.QUOTATION_CHARGE_ID;
           	 }
            //}
        }); 
        $scope.monthlyCharges = newDataList;
        $scope.monthlyCharges = [];
		    } 
	 };
	 //changed by nishant
	 
	 //1.0.0.16 START
	 
	$scope.getFrankingAmount = function(mode){
			if($scope.frnaking_type =="1000000002" )//manual rental freq
				{
					
					if(mode == "manual" || mode == "setZero")
					{
						$scope.FRANKING_CHARGE_AMOUNT = "0.00";
						
					}
					/*else if (mode == "setZero")
					{
						//var temp_frankingChargeAmt = $scope.FRANKING_CHARGE_AMOUNT;
						if($scope.LEASE_PERIOD != arrRentalDtl.LEASE_PERIOD_IN_MNTHS || $scope.rental_freq != arrRentalDtl.RENTAL_CYCLE_ID || $scope.RENTAL_MODE_VALUE != arrRentalDtl.RENTAL_MODE_ID || $scope.MORATORIUM != arrRentalDtl.MORATORIOUM_IN_MNTHS)
						{
							$scope.FRANKING_CHARGE_AMOUNT="0.00";
						}
						
					
					}*/
					else if (mode == "autoPopulate")
						{
						$scope.FRANKING_CHARGE_AMOUNT = !angular.isUndefined(arrRentalDtl.FRANKING_CHRG_AMOUNT)?arrRentalDtl.FRANKING_CHRG_AMOUNT:"";//1.0.0.16
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
									$scope.FRANKING_CHARGE_AMOUNT = !angular.isUndefined(arrRentalDtl.FRANKING_CHRG_AMOUNT)?arrRentalDtl.FRANKING_CHRG_AMOUNT:"";//1.0.0.16
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
									$scope.FRANKING_CHARGE_AMOUNT = !angular.isUndefined(arrRentalDtl.FRANKING_CHRG_AMOUNT)?arrRentalDtl.FRANKING_CHRG_AMOUNT:"";//1.0.0.16
								}
							}
					
					}
				else if (mode == "autoPopulate")
				{
				$scope.FRANKING_CHARGE_AMOUNT = !angular.isUndefined(arrRentalDtl.FRANKING_CHRG_AMOUNT)?arrRentalDtl.FRANKING_CHRG_AMOUNT:"";//1.0.0.16
				}
				$scope.isAuto=true;
			}
		};
	
//1.0.0.16 END
		
//added by nishant regarding quotation DM
	
		
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

	
	/*
	 * Section RV Details
	 */
		// 1.0.0.41 start
		 $scope.getRVSlabType = function(){
			   var selectedValue = $scope.RV_TYPE;
			   if(selectedValue == '1000000001'){ //market
				   $scope.isDisabledMarket=false;
				   //1.0.0.7 start
				   if($scope.market!=undefined)
					   {
					    $scope.market.RV_SLAB1="0"; 
					    $scope.market.PERCENT="0.00";
					   }
				   else
					   {
					    $scope.market= {RV_SLAB1:"0",PERCENT:"0.00"};
					   }
				   //1.0.0.7 end
				   $scope.isDisabledFixed = true;
				   //$scope.fixed = {PERCENT_VALUE:""};
				   $scope.PERCENT_VALUE="0.00";
				   //$scope.CALC="0";
				   $scope.fixed= {CALC:"0.00"};//rv_calculation
				   
				   $scope.RESIDUAL_VALUE1="0.00";
				  // $scope.$apply();
				   
				   
			   } else if (selectedValue == '1000000002'){ //fixed
				   $scope.isDisabledFixed = false;
				   $scope.isDisabledMarket=true;
				   $scope.fixed= {CALC:"0"};
				  // $scope.CALC="0";
				   //1.0.0.7 start
				   if($scope.market!=undefined)
				   {
				    $scope.market.RV_SLAB1="0"; 
				    $scope.market.PERCENT="0.00";
				   }
			   else
				   {
				    $scope.market= {RV_SLAB1:"0",PERCENT:"0.00"};
				   }
				   //1.0.0.7 end
				   $scope.RESIDUAL_VALUE1="0.00";
				//   $scope.$apply();
			   }
			   else{
					   $scope.isDisabledMarket=true;
					   //1.0.0.7 start
					   if($scope.market!=undefined)
					   {
					    $scope.market.RV_SLAB1="0"; 
					    $scope.market.PERCENT="0.00";
					   }
				   else
					   {
					    $scope.market= {RV_SLAB1:"0",PERCENT:"0.00"};
					   }
					   //1.0.0.7 end 
					   $scope.isDisabledFixed = true;
					  // $scope.fixed = {PERCENT_VALUE:" "};
					   $scope.PERCENT_VALUE="0.00";
					  $scope.fixed= {CALC:"0"};//rv_calculation
					 //  $scope.CALC="0";
					   $scope.RESIDUAL_VALUE1="0.00"; 
					   $scope.BColor = "black";
	 				   $scope.CustomStyle = {  color: $scope.BColor };
					  
				   
				   
			   }
			   
		   };  // 1.0.0.41 end
		   
		   // 1.0.0.58 start
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
		   
		   // 1.0.0.58 end
		   
		   
		     $scope.getRVValue = function(){
		    	 var rv_type = $scope.RV_TYPE;
		    	 var quotationId = staticInfo.PC_STATIC_INFO_DATA[0].QUOTATION_ID;//1.0.0.48
		    	 var moratorium=$scope.MORATORIUM;// 1.0.0.35
		    	 var annualUsage=$scope.ANNUAL_USAGE;//1.0.0.35
		    	 
		    	 var rv_slab = $scope.isDisabledMarket?"":angular.element('#RV_SLAB1').val();
		    		 //angular.element('#rv_slab').val()!=""?angular.element('#rv_slab').val():"";
		    	 var leasePeriod = Number(Number($scope.LEASE_PERIOD) +  Number($scope.MORATORIUM));//1.0.0.28
		    	 if(!angular.isUndefined($scope.LEASE_PERIOD)){
		        	 var lease_startDate = $scope.START_DATE;
		        	 var rv_fixed_percentage =  $scope.isDisabledFixed?"": (!angular.isUndefined(angular.element('#fixed_rv').val())?angular.element('#fixed_rv').val():"");
		        	 //rv_fixed_percentage = parseFloat($scope.removeCommaOfVariable(rv_fixed_percentage));
		        	 var rv_fixed_calc=  $scope.isDisabledFixed?"":angular.element('#rv_calculation').val();
		        	
		        	 if(rv_fixed_percentage >=100)
		        		 {
		        		 alert("RV FIXED PERCENT SHOULD BE LESS THAN 100.");
		        		 $scope.PERCENT_VALUE	="0.00";
		        		 return false;
		        		 }// 1.0.0.41 start
		        	 var decimalCheck = rv_fixed_percentage.split('.');

		                if(decimalCheck.length > 2) {
		                	alert("RV FIXED PERCENT CAN NOT CONTAIN MORE THAN ONE DECIMAL.");
			        		 $scope.PERCENT_VALUE	="0.00";
		                }
		                // 1.0.0.41 end
		        	 if(!$scope.isDisabledFixed && rv_fixed_calc=="0"){
		        		 return false;
		        	 }
		        	//alert("rv_fixed_calc" + $scope.isDisabledFixed);
		        	 //comment start by 1.0.0.51
		        	 /*//1.0.0.17 start
		        	 if(rv_type == "1000000001" && rv_slab == "1000000005")	//market and others
		        		 {
		        		  $scope.market.PERCENT = "0.00";
		        		  $scope.RESIDUAL_VALUE1 = "0.00";
		        		 }//1.0.0.17 end
		        	 else
		        		 { */
		        		 //comment end by 1.0.0.51
			        		 $.ajax({
				    	  		 url: "residualDetails.sprg?isAjaxCalling=Y", //1.0.0.55 added  isAjaxCalling=Y
				    	  		 data: {"rv_type": rv_type,
				    	  			    "rv_slab":rv_slab,
				    	  			    "leasePeriod" :leasePeriod,
				    	  			    "lease_startDate":lease_startDate,
				    	  			    "rv_fixed_percentage" :rv_fixed_percentage,
				    	  			    "rv_fixed_calc":rv_fixed_calc,
				    	  			    "moratorium":moratorium,//1.0.0.35
				    	  			    "annualUsage":annualUsage,//1.0.0.35
				    	  			    "marketPercent": $scope.market.PERCENT,//1.0.0.17
				    	  			    "quotationId":quotationId,
				    	  		},
				    	  		 type: "GET" ,
				    	  			success: function(response) 
				    	  			{
				    		  			if(response!=null && response!="") 
				    		  			{
				    		  				var jsonStr = JSON.parse(response);
				    		  				if(jsonStr.Status=="SessionExpired") //1.0.0.55
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
		        		 //}
				        	
		        	 
		        	 
		        	}
		    	  else{
		    		 alert("Lease period mandatory..");
		    		 $scope.EXT_WARR="N";
		    		 $scope.$apply();
		    	 }
		    	
		     };
		     
		
		     //1.0.0.17 start
		     $scope.getRVValue1 = function(){
		    	 var rv_type = $scope.RV_TYPE;
		    	 var quotationId = staticInfo.PC_STATIC_INFO_DATA[0].QUOTATION_ID;//1.0.0.48
		    	 var rv_slab = $scope.isDisabledMarket?"":angular.element('#RV_SLAB1').val();
		    		 //angular.element('#rv_slab').val()!=""?angular.element('#rv_slab').val():"";
		    	 var leasePeriod = Number(Number($scope.LEASE_PERIOD) +  Number($scope.MORATORIUM));//1.0.0.28
		    	 if(!angular.isUndefined($scope.LEASE_PERIOD)){
		        	 var lease_startDate = $scope.START_DATE;
		        	 var rv_fixed_percentage =  $scope.isDisabledFixed?"": (!angular.isUndefined(angular.element('#fixed_rv').val())?angular.element('#fixed_rv').val():"");
		        	 //rv_fixed_percentage = parseFloat($scope.removeCommaOfVariable(rv_fixed_percentage));
		        	 var rv_fixed_calc=  $scope.isDisabledFixed?"":angular.element('#rv_calculation').val();
		        	
		        	 if(rv_fixed_percentage >=100)
		        		 {
		        		 alert("RV FIXED PERCENT SHOULD BE LESS THAN 100.");
		        		 $scope.PERCENT_VALUE	="0.00";
		        		 return false;
		        		 }
		        	 if(!$scope.isDisabledFixed && rv_fixed_calc=="0"){
		        		 return false;
		        	 }
		        	 var market_per = $scope.market.PERCENT;
		        	 if(market_per >=100)
	        		 {
	        		 alert("MARKET RV PERCENT SHOULD BE LESS THAN 100.");
	        		 $scope.market.PERCENT ="0.00";
	        		 $scope.RESIDUAL_VALUE1 = "0.00";
	        		 return false;
	        		 }// 1.0.0.41 start
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
		        	 // 1.0.0.41 end
		        
			        		 $.ajax({
				    	  		 url: "residualDetails.sprg?isAjaxCalling=Y", //1.0.0.55 added  isAjaxCalling=Y
				    	  		 data: {"rv_type": rv_type,
				    	  			    "rv_slab":rv_slab,
				    	  			    "leasePeriod" :leasePeriod,
				    	  			    "lease_startDate":lease_startDate,
				    	  			    "rv_fixed_percentage" :rv_fixed_percentage,
				    	  			    "rv_fixed_calc":rv_fixed_calc,
				    	  			    "quotationId" :quotationId,
				    	  			    "marketPercent":market_per },
				    	  		 type: "GET" ,
				    	  			success: function(response) 
				    	  			{
				    		  			if(response!=null && response!="") 
				    		  			{
				    		  				var jsonStr = JSON.parse(response);
				    		  				if(jsonStr.Status=="SessionExpired") //1.0.0.55
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
		//1.0.0.17 end
	
	/*
	 * Section Interest on Advance
	 * change by nishant  $scope.INTEREST_AMT = Math.ceil(calculatedInterest/100);
	 */     // 1.0.0.25 start
		     /*$scope.calculateInterestAmount= function(){
		    	 //  alert("I am here.....");
		    	  var amount = ($scope.AMOUNT_ADVANCE!=""?$scope.AMOUNT_ADVANCE:0);
		    	  var roi = ($scope.INTEREST_RATE!=""?$scope.INTEREST_RATE:0);
		    	  var days = ($scope.NO_OF_DAYS!=""?$scope.NO_OF_DAYS:0);
		    	  if(amount!=0 && roi!=0 && days!=0 && dateDiffLogic!=0 && !angular.isUndefined(amount) && !angular.isUndefined(roi) && !angular.isUndefined(days) && !angular.isUndefined(dateDiffLogic)){
		    		  var calculatedInterest = ((parseFloat($scope.removeCommaOfVariable(amount))*roi*days)/dateDiffLogic).toFixed(2);//1.0.0.23
		    		  $scope.INTEREST_AMT = (calculatedInterest/100).toFixed(2);//1.0.0.23
		    	  } else {
		    		  //some data is missing error
		    		  $scope.INTEREST_AMT = "";
		    	  }
		    	 
		       };*/
		       //1.0.0.25 end
		       $scope.isChargeType = function(){
		    	 if($scope.CHARGE_TYPE_VALUE=='1000000001'){
		    		 $scope.isChargeDisable= true;
		    		 $scope.AMOUNT_ADVANCE="0.00";
		    		 $scope.INTEREST_RATE="0.00";
		    		 $scope.NO_OF_DAYS="0";
		    		 $scope.INTEREST_AMT="0.00";
		    		 //INTEREST_AMT  -- already disable
		    	 } else{
		    		 $scope.isChargeDisable= false;
		    	 }
		       };
		
	/*
	 * Section Maintenance Details
	 */
	
		       $scope.getMaintenanceAmount=function(){
		      
		  	   if($scope.MAINTENANCE_SLAB=="1000000006" || $scope.MAINTENANCE_SLAB=="1000000013" || $scope.MAINTENANCE_SLAB=="1000000012" || $scope.MAINTENANCE_SLAB == "0")//1.0.0.19
		 	   {
		    		   $scope.TOTAL_MAINTENANCE="0.00";
		    		  
		    		   //angular.element('#total_maintenence').val("0");
		    		  // $scope.$apply();
		    		  
		 	   }
		      else
		    	 {
		    	   if(Number($scope.LEASE_PERIOD)>0 && $scope.MAINTENANCE_SLAB !='0' && $scope.removeCommaOfVariable($scope.ANNUAL_USAGE) != "")
		    		   {
		    		   
		    			 var leasePeriod = Number(Number($scope.LEASE_PERIOD) +  Number($scope.MORATORIUM));//1.0.0.28
		    			 var quotationId = staticInfo.PC_STATIC_INFO_DATA[0].QUOTATION_ID;//1.0.0.48
				      	 $.ajax({
				  	  		 url: "getMaintenanceAmount.sprg?isAjaxCalling=Y", //1.0.0.55 added  isAjaxCalling=Y
				  	  		 data: {
				  	  			    "maintenance_slab":$scope.MAINTENANCE_SLAB,
				  	  			    "leasePeriod" : leasePeriod,//1.0.0.28
				  	  			    "lease_startDate":$scope.START_DATE,
				  	  			    "annual_usage" :$scope.removeCommaOfVariable($scope.ANNUAL_USAGE),
				  	  			    "quotationId":quotationId
				  	  			    },
				  	  		 type: "GET" ,
				  	  			success: function(response) 
				  	  			{
				  		  			if(response!=null && response!="") 
				  		  			{
				  		  				var jsonStr = JSON.parse(response);
				  		  			if(jsonStr.Status=="SessionExpired") //1.0.0.55
			  						{
			  							window.location.href = 'llm/ReLogin.jsp?unknownError=Your session has been killed. Please login again.';
			  							return;
			  						}
				  		  				//angular.element('#total_maintenence').val(jsonStr.maintenanceInfo.MAINT_AMOUNT);
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
		    		 
		    	   if(!angular.isUndefined($scope.LEASE_PERIOD) && $scope.isAMCEnable==false && $scope.AMC_details=="Y"){
		    		   var lease_period = Number(Number($scope.LEASE_PERIOD) +  Number($scope.MORATORIUM));//1.0.0.20
		    		   var noOfYrs = Math.ceil(lease_period/12);
		    		   var StartDate = $scope.START_DATE;
		    		   StartDate = new Date(StartDate);
		    		   var leaseMaturityDate =  $scope.maturityDate(StartDate,lease_period);
		    		   var tempEndDate= new Date(StartDate.getTime() + 364 * 24 * 60 * 60 * 1000)
		    		  // alert("amc :  "+leaseMaturityDate);
		    		   // dateDiffLogic 
		    		   //var end_date ;
		    		   $scope.amcDetails=[];
		    		   for(var i =0;i<noOfYrs;i++){
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
		    			   
		    			   StartDate = new Date(new Date(StartDate.getTime() + 365 * 24 * 60 * 60 * 1000) + 1 * 24 * 60 * 60 * 1000);
		    			   tempEndDate = new Date(tempEndDate.getTime() + 365 * 24 * 60 * 60 * 1000) ;
		    			  if(tempEndDate.getFullYear()%4==0)
		    				   {tempEndDate = new Date(tempEndDate.getTime() + 1 * 24 * 60 * 60 * 1000 );}   // 1.0.0.5
		    			   if(StartDate.getFullYear()%4==0)
	    				   {StartDate = new Date(StartDate.getTime() + 1 * 24 * 60 * 60 * 1000);}
		    		   }
		    		   //end date = leaseMaurityDate
		    		   
		    		// $scope.END_DATE = {id:leaseMaturityDate};
		    	   } else {
		    		   //added by nishant
		    		   angular.forEach($scope.amcDetails, function(selected){
				        
		    			   if(selected.MAINTENANCE_DTL_ID != null && selected.MAINTENANCE_DTL_ID != "" && selected.MAINTENANCE_DTL_ID != undefined)//1.0.0.2
			        		{
				            	if(removeExtendedWarrentyIdStr!=null && removeExtendedWarrentyIdStr!="" && removeExtendedWarrentyIdStr!=undefined)
				            		{removeExtendedWarrentyIdStr = removeExtendedWarrentyIdStr + "^"+ selected.MAINTENANCE_DTL_ID;
				            		}
				            	else{
				            		 removeExtendedWarrentyIdStr = selected.MAINTENANCE_DTL_ID;
				            	}
			        		}
			            
			        }); 
		    		   $scope.amcDetails=[];
		    	   }
		    	  /* if(noOfYrs>0)
		    		   {
		    		   $scope.$apply();   
		    		   }*/
		    	   
		       };
		       
// created by nishant for changing number comma seperated in angular js		       
 $scope.numberWithCommas=function(name)//name of field 
 {
	// alert(name)
	var y= $scope[name];
	var x = $scope.removeCommaOfVariable(y);
	var x = x.toString();
	var pattern = /(-?\d+)(\d{3})/;
	while (pattern.test(x))
	x = x.replace(pattern, "$1,$2");
	//alert(x)
	$scope[name]=x;
};	
		    	
		    	
	/*changed by nishant*/	       
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
				var extStartDate = $scope.maturityDate(startDate, Number(Number($scope.LEASE_PERIOD) +  Number($scope.MORATORIUM))); //1.0.0.20
				//extStartDate = new Date(new Date(extStartDate.getTime() +  24 * 60 * 60 * 1000) + 1 * 24 * 60 * 60 * 1000);
				
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
				
				//
				
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
					  // alert("no is selected");
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
		       
		/*
		 * Section AMC Detail
		 */       
			 $scope.getTotalAMCCost = function(list){  //to find out from where to call
				   //alert("inside getTotalAMC Cost");
				   if($scope.isAMCEnable==false && $scope.AMC_details=="Y"){
				   var totalAMCCost=0;
				   angular.forEach(list , function(item ){
					   if(item.YEARLY_COST=="" || item.YEARLY_COST==undefined){
						   item.YEARLY_COST=0;
					   }
					  totalAMCCost+= parseFloat($scope.removeCommaOfVariable(item.YEARLY_COST));
					  
					   
				   });
				   //alert(totalAMCCost);
				   }
				   $scope.totalAmcCost = totalAMCCost;
			   };     
			
		       
		/*
		 * Section Extended Warranty
		 */       
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
						//alert($scope.extDetails.indexOf(foundItem );
					//	alert("daszdczc"+index);
						var maint_id = "";
						
						if(index.length>0){
							maint_id = index[0].MAINTENANCE_ID;
						}
						
						var lease_period = Number(Number($scope.LEASE_PERIOD) +  Number($scope.MORATORIUM));//1.0.0.20
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
						//StartDate = new Date(new Date($scope.extDetails[0].END_DATE.getTime() + 365 * 24 * 60 * 60 * 1000) + 1 * 24 * 60 * 60 * 1000);
						
					};

	$scope.removeExtendedWarrentyRow= function(index){
						//alert("removed called" + index);
						var newDataList=[];
						if($scope.extDetails.length>1){
				        angular.forEach($scope.extDetails, function(selected){
				        	
				            if(!selected.selected){
				                newDataList.push(selected);
				            }else{ //error here
				            	if(removeExtendedWarrentyIdStr!=null && removeExtendedWarrentyIdStr!="" && removeExtendedWarrentyIdStr!=undefined)
				            		removeExtendedWarrentyIdStr = removeExtendedWarrentyIdStr +"^"+selected.MAINTENANCE_DTL_ID;
				            	else
				            		 removeExtendedWarrentyIdStr = selected.MAINTENANCE_DTL_ID;
				            }
				        }); 
				        $scope.extDetails = newDataList;
				        $scope.validationExtendedWaranty();
						} else{
							//alert("cannot delete...");
						}
					};
		
					
		$scope.validationExtendedWaranty = function()
					{
						var lease_period = Number(Number($scope.LEASE_PERIOD) +  Number($scope.MORATORIUM));//1.0.0.20
			    		var extendedStartDate = $scope.START_DATE;
			    		var leaseMaturityDate =  $scope.maturityDate(new Date(extendedStartDate),lease_period);
			    		//alert("lease_period "+lease_period+" extendedStartDate "+extendedStartDate+" leaseMaturityDate "+leaseMaturityDate)
						var currentEndDate;
			    		var currentStartDate;
						for(var i=0;i<$scope.extDetails.length;i++)
							{
							
							if(i==0)
								{
								$scope.extDetails[i].START_DATE=$filter('date')(new Date(new Date(leaseMaturityDate).getTime()), "dd-MMM-yyyy");
								//$scope.extDetails[i].END_DATE=$filter('date')(new Date(new Date(leaseMaturityDate).getTime() + 365 * 24 * 60 * 60 * 1000), "dd-MMM-yyyy");
								   
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
	/*
	 * Section Value added Services - Add On Services (dependent on scheme selected)
	 */
				      
				     $scope.getDefaultvaluesOfAddOnServices= function(){
				    	 var schemeId = $scope.SCHEME1;
				    	 var leaseStartDate = $scope.START_DATE;
				    	 var valueAddedStr = $scope.valueAdded;
				    	 var finalString = "";
				    	 var quantityError="";
				    	 var applicabilityError = "";
				    	 var quotationId = staticInfo.PC_STATIC_INFO_DATA[0].QUOTATION_ID;//1.0.0.48
				    	 for(var i=0;i<valueAddedStr.length; i++){
							var selectedValue =  $filter('filter')(valueAddedStr)[i];
							//console.log(selectedValue)
							var selectedQuantity = !angular.isUndefined(selectedValue.QTY)?selectedValue.QTY:"";
							if((selectedValue.SERVICE_ID == '1000000002' &&  $scope.ALLOW_LEASE_TYRE == 'Y') || (selectedValue.SERVICE_ID == '1000000003' && $scope.ALLOW_LEASE_BATTERY == 'Y'))
							{
							
							}
							else
								{
								if((document.getElementById("SERVICE_AMOUNT"+i).disabled==false) &&(selectedValue.quantityEnable=='Y' && selectedQuantity=="" && selectedValue.APPLICABILITY!="1000000001") || angular.isUndefined(selectedValue.APPLICABILITY) ){
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
				    	// alert("finalString "+ finalString);
				    	 $.ajax({
					  		 url: "getAddOnServicesMasterAmount.sprg?isAjaxCalling=Y", //1.0.0.55 added  isAjaxCalling=Y
					  		 data: {
					  			    "schemeId":schemeId,
					  			    "leaseStartDate" :leaseStartDate,
					  			    "tableString":finalString,
					  			    "quotationId":quotationId
					  			   },
					  		 type: "POST" ,
					  			success: function(response) 
					  			{
						  			if(response!=null && response!="") 
						  			{
						  				var jsonStr = JSON.parse(response);
						  				if(jsonStr.Status=="SessionExpired") //1.0.0.55
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
								  		    				//else
								  		    					//valueAddedStr[i].SERVICE_AMOUNT = outputRes[j].Amount;
								  		    			}
								  		    	 }
						  				}
						  				$scope.$apply();
						  				
						  			}
					  			}
				    	 });
				    	 $scope.getDefaultValuesFlagForAddOnServices = true; //1.0.0.37 Added
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
							//disable all fields.  valueAddedStr
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
						      $scope.getDefaultValuesFlagForAddOnServices = false;//1.0.0.37 Added
							}
						
					};
				     
			/*
			 * Section monthly charges
			 */	     
					
					 $scope.MonthlyChargeMaster=[];
				     $scope.addMonthlyCharges= function(){
				    	 var chargeIdVal="0"; // 1.0.0.32 start
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
								  				amount_Val =arrSchemeMaster[i].NAME;//1.0.0.6
								  				break;
								  			}
								  		}
				    	    	   }
				    	       // 1.0.0.32 end
				    		 }
				    	 
				    	 if(angular.element("#SCHEME1").val()!=0)
				    		 {
				    		 $scope.monthlyCharges.push({
								 
								 'QUOTATION_ID': quotationId, 
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
							 
							 'QUOTATION_ID': quotationId, 
							    'selected1234': "",
						   		'CHARGE' : "0",
						   		'MASTER_AMOUNT' :  "0.00",
						   		'AMOUNT':  "0.00",
						   		'QUOTATION_CHARGE_ID':""
						   		
						   	});
				    	//5000000010
				    	 var monthlyCharge = $scope.monthlyCharges;
				    	 //alert("921  "+ monthlyCharge);
				    	 for(var i=0;i< monthlyCharge.length;i++){
				    		 monthlyCharge[i].CHARGE = '1200000010';
				    		 //service_charge 
				    		 var arrSchemeMaster = $scope.masterChargeId;
						  		for(var j =0;j< arrSchemeMaster.length;j++){
						  			if(arrSchemeMaster[j].ID == monthlyCharge[i].CHARGE){
						  				monthlyCharge[i].MASTER_AMOUNT =arrSchemeMaster[j].NAME;
						  				monthlyCharge[i].AMOUNT =arrSchemeMaster[j].NAME;
						  			}
						  		}
				    		// $("#service_charge"+i).val(monthlyCharge[i].CHARGE);
								
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
				  				row.AMOUNT =arrSchemeMaster[i].NAME;//1.0.0.6
				  			}
				  		}
				 	
				      };
				      
				      
				      
//--------------------------------------------------------------End of section creation and value popultaion------------------------------------------------------------------------------------------------------				      
				      
		/*
		 * Saving all details to database, creating string for the same
		 */		
				      
				      //1.0.0.29 start
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
				    	  //$scope.calculateEndDate(); //1.0.0.30 start
				    	  
				    	 	$scope.expiryDays="";
				    	 	 var schemeId = $scope.SCHEME1;
				    	   	 var noOfDays=0;
				    	   
				    	   	 for(var i =0;i<arrValidationValues.length;i++){
				    	   		 if(arrValidationValues[i].SCHEMEID==schemeId){
				    	   			 noOfDays=arrValidationValues[i].QUOTATION_EXPIRY_DAYS;
				    	   			$scope.expiryDays=arrValidationValues[i].QUOTATION_EXPIRY_DAYS;//1.0.0.29
				    	   			 
				    	   		 }
				    	   	 }
				    	   	 if($scope.START_DATE !=null && $("#START_DATE")[0].value!="" ){
				    	   		 var startDate = new Date($scope.START_DATE);
				    	   		 var endDate = new Date(startDate.getTime() + noOfDays * 24 * 60 * 60 * 1000);
				    		    	 $scope.EXPIRING_DATE = $filter('date')(endDate, "dd-MMM-yyyy");
				    		    	 $scope.TEMP_EXPIRING_DATE=$scope.EXPIRING_DATE;
				    	    	} else
				    	   	 {
				    	    		$scope.EXPIRING_DATE = null;
				    	    		$scope.TEMP_EXPIRING_DATE=null;
				    	   	 }
				    	   //	$scope.CheckLeasePeriod();//1.0.0.33 //1.0.0.36
				      };
				    //1.0.0.30 end
				      // 1.0.0.29 end
				      
     var error="";
     var dm_error="";//1.0.0.46
     var rentalSaveStr= "";
     var maintenanceSaveStr="";
     var maintenaceExtenedSaveStr="";
     var valueAddedSaveStr="";
     var monthlyChargesSaveStr="";
     var maintenanceDtlSaveId="";
     var quotationChargeSaveId="";
     //check function
     $scope.checkForm = function()
     {
    	 // 1.0.0.29 start
    	 var expirydays=$scope.expiryDays;
    	 var businessDate = $scope.businessDate;
         var effectiveLeaseStartDate = $scope.START_DATE;
         var expiryDate=$scope.EXPIRING_DATE;
         var minimumStartDate= new Date();
         tempData.errorMessages="Errors:";
		dm_error="";
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
       
       /*  if((effectiveLeaseStartDate > businessDate))
         {
		  error = error+"Effective Lease Start Date can't be greater than business Date. \n";
		   
		   }*/
         
         if(minimumStartDate.getTime() >effectiveLeaseStartDate.getTime() || effectiveLeaseStartDate.getTime() > businessDate.getTime())
         {
        	if($rootScope.staticInfoObject.TRANSFER_FLG!="WITHOUT"){  //1.0.0.46.1
        	 error = error+"Effective Lease start date can fall within a range from Current Business Date-Quotation expiry days  configured in the system + 1. \n"; 
        	}
         }
            
    	 // 1.0.0.29 end
         var totalTenure = Number(Number($scope.LEASE_PERIOD) +  Number($scope.MORATORIUM));//1.0.0.21
    	 
         if($rootScope.staticInfoObject.TRANSFER_FLG!="WITHOUT") //1.0.0.45
 		 {
	    	 if($scope.PRODUCT_TYPE1==0){
	    		 error = error+"PRODUCT TYPE IS MANDATORY. \n";
	    	 }
	    	 if($scope.SCHEME1==0){
	    		 error = error+"SCHEME IS MANDATORY. \n";
	    	 }
	    	 if(angular.isUndefined($scope.START_DATE)||$scope.START_DATE==0 || $("#START_DATE")[0].value==""){//1.0.0.29
	    		 error = error+"START DATE IS MANDATORY. \n";
	    	 }
			if(angular.isUndefined($scope.EXPIRING_DATE)||$scope.EXPIRING_DATE==0 || $scope.EXPIRING_DATE==null ){//1.0.0.29
				error = error+"EXPIRING DATE IS MANDATORY. \n";
			    	 }
			if(angular.isUndefined($scope.LEASE_PERIOD)||$scope.LEASE_PERIOD==0){
				error = error+"LEASE PERIOD IS MANDATORY. \n";
			}
			if(angular.isUndefined($scope.MORATORIUM)){
				error = error+"MORATORIUM IS MANDATORY. \n";
			}
 		 }
		//1.0.0.21 start
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
		if($rootScope.staticInfoObject.TRANSFER_FLG!="WITHOUT") //1.0.0.45
		{
		if($scope.rental_cycle==0){
			error = error+"BILLING CYCLE IS MANDATORY. \n";
		}
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
		//1.0.0.21 end
	
		if($scope.rental_type==0){
			error = error+"RENTAL TYPE IS MANDATORY. \n";
		}
		if($scope.RENTAL_MODE_VALUE==0){
			error = error+"RENTAL MODE IS MANDATORY. \n";
		}
		if(angular.isUndefined($scope.RSD)||$scope.RSD==0){
			error = error+"RSD IS MANDATORY. \n";
		}
		if($scope.AGREEMENT_TYPE_VALUE==0){
			error = error+"AGREEMENT TYPE IS MANDATORY. \n";
		}
		//if(angular.isUndefined($scope.ANNUAL_USAGE)|| $scope.ANNUAL_USAGE==0){
		//	error = error+"ANNUAL USAGE IS MANDATORY. \n";
		//}
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
		}//1.0.0.56
		else if($scope.FRANKING_CHARGE_AMOUNT==""){
			error = error+"LEASE FRANKING CHARGE AMOUNT IS MANDATORY. \n";
		}
		
			 if($scope.CHARGE_TYPE_VALUE==0){
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
		}
		//Add by 1.0.0.54
		if($scope.LEASE_ITC_AVAIL_OPTIONS==0 || $scope.LEASE_ITC_AVAIL_OPTIONS=="" || $scope.LEASE_ITC_AVAIL_OPTIONS==undefined){
			error = error+"LEASE ITC AVAIL OPTIONS IS MANDATORY. \n";
		}
		
			//1.0.0.25 start
			/*if(angular.isUndefined($scope.INTEREST_AMT) || $scope.INTEREST_AMT==0){
				error = error+"INTEREST AMOUNT IS MANDATORY. \n";
			}*///1.0.0.25 end
		
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
				if($scope.FMS_FRANKING_CHRG_AMNT=="" || $scope.FMS_FRANKING_CHRG_AMNT==0)//1.0.0.3
				{
					error = error+"FMS FRANKING CHARGE IS MANDATORY. \n";
					}
				//1.0.0.9 start
				/*if($scope.FMS_OTHER_CHRG_AMNT=="" || $scope.FMS_OTHER_CHRG_AMNT==0 )//1.0.0.3
					{
					error = error+"FMS OTHER CHARGE IS MANDATORY. \n";
					}*/
				//1.0.0.9 end
			}
		}
	}
	//1.0.0.1 start
		if( (+($scope.removeCommaOfVariable($scope.MORATORIUM)) >= +($scope.removeCommaOfVariable($scope.LEASE_PERIOD))) && $scope.removeCommaOfVariable($scope.LEASE_PERIOD)>0 ){
			error = error+"MORATORIUM SHOULD BE LESS THAN LEASE PERIOD. \n";
		}
		
		if( (+($scope.removeCommaOfVariable($scope.MORATORIUM)) + +($scope.removeCommaOfVariable($scope.LEASE_PERIOD))) >maxDaysInLease ){//1.0.0.8
			error = error+"SUM OF MORATORIUM AND LEASE PERIOD CANNOT BE GREATER THAN "+maxDaysInLease+". \n";//1.0.0.8
		}
	//1.0.0.1 end	
		
	//1.0.0.11 start	
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
	//1.0.0.11 end	
		
		
		var fixed_rv ="";
		var rv_calculation ="";
		var market_percentage="";
		var rv_slab = $scope.isDisabledMarket?"":!angular.isUndefined(angular.element('#RV_SLAB1').val())?angular.element('#RV_SLAB1').val():"0";
		if(!$scope.isDisabledMarket && $rootScope.staticInfoObject.TRANSFER_FLG!="WITHOUT"){//1.0.0.45
			if(angular.isUndefined(angular.element('#RV_SLAB1').val()) || angular.element('#RV_SLAB1').val()==0)
				error = error+"MARKET RV SLAB IS MANDATORY. \n";
			if(angular.element('#market_percentage').val()==0 || angular.isUndefined(angular.element('#market_percentage').val()))
				error = error+"MARKET RV PERCENTAGE IS MANDATORY. \n";
			market_percentage =angular.element('#market_percentage').val();
			
		}

		if(!$scope.isDisabledFixed && $rootScope.staticInfoObject.TRANSFER_FLG!="WITHOUT"){//1.0.0.45
		//1.0.0.58 start
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
	    //1.0.0.58 end
		
		
	
		
		if(angular.isUndefined($scope.RESIDUAL_VALUE1) && $rootScope.staticInfoObject.TRANSFER_FLG!="WITHOUT"){//1.0.0.45
			error = error+"RESIDUAL_VALUE1 IS MANDATORY. \n";
		}
		
		
		//var market_percentage =angular.element('#market_percentage').val();
		
	    var residual_value = $scope.RESIDUAL_VALUE1;
	    
    	//1.0.0.22 start 						
	    if($scope.frnaking_type =="1000000001" )//auto rental freq
			{
	    	$scope.AUTO_FRANKING_PER = temp_autoFrankingPer;
			}
	    else
	    	{
	    	$scope.AUTO_FRANKING_PER = "";
	    	}
	    //1.0.0.22 end
	    
	    rentalSaveStr= quotationId+"~"+$scope.PRODUCT_TYPE1 + "~"+$scope.SCHEME1 + "~"+$scope.START_DATE + 
     	"~"+$scope.EXPIRING_DATE + "~"+$scope.LEASE_PERIOD+
     	"~"+$scope.MORATORIUM+"~"+$scope.rental_cycle+"~"+$scope.rental_freq+"~"+$scope.rental_type+
     	"~"+$scope.RENTAL_MODE_VALUE+"~"+$scope.RSD+"~"+$scope.AGREEMENT_TYPE_VALUE+"~"+$scope.removeCommaOfVariable($scope.ANNUAL_USAGE)+"~"+$scope.removeCommaOfVariable($scope.EXCESS_KM)+
     	"~"+$scope.frnaking_type+"~"+ $scope.removeCommaOfVariable($scope.FRANKING_CHARGE_AMOUNT)+"~"+$scope.CHARGE_TYPE_VALUE+"~"+$scope.NO_OF_DAYS+
     	"~"+$scope.INTEREST_RATE+"~"+$scope.removeCommaOfVariable($scope.AMOUNT_ADVANCE)+"~"+$scope.removeCommaOfVariable($scope.INTEREST_AMT)+"~"+$scope.RV_TYPE+//1.0.0.14
     	"~"+rv_slab+"~"+fixed_rv+"~"+market_percentage+
     	"~"+rv_calculation+"~"+$scope.removeCommaOfVariable(residual_value)+"~"+$scope.FORECLOSURE_METHOD+"~"+$scope.REPAYMENT_MODE+"~"+$scope.FORECLOSURE_REMARKS+"~"+$scope.FORECLOSURE_DISC_PERC+//1.0.0.14
     	"~"+$scope.FORECLOSURE_PENALTY_PERC+"~"+$scope.LPI_PERCENT+"~"+$scope.INTEREST_ON_ADVN+"~"+$scope.PROPOSED_CASHFLOW_IRR+"~"+$scope.BILLING_TYPE_ID+"~"+$scope.removeCommaOfVariable($scope.FMS_FRANKING_CHRG_AMNT)+
     	"~"+$scope.removeCommaOfVariable($scope.FMS_OTHER_CHRG_AMNT)+"~"+$scope.AUTO_FRANKING_PER+"~"+$scope.LEASE_ITC_AVAIL_OPTIONS+"~"+$scope.RENTAL_LOGIC+"~"+$scope.AGREEMENT_SIGNING_TYPE;//1.0.0.27 // 1.0.0.50//1.0.0.56
	    
	// alert(rentalSaveStr)
     	
	    var MAINENANCE_ID="";
     	
     	var extended_table_show = 	!angular.isUndefined($scope.EXT_WARR)?$scope.EXT_WARR:"";
     
     	
     	var amc_table_show =  !angular.isUndefined($scope.AMC_details)?$scope.AMC_details:"";
	    var totalMaint = parseFloat($scope.removeCommaOfVariable($scope.TOTAL_MAINTENANCE));//1.0.0.34
     	if($scope.isMaintEnable){ //maintenance section is disabled
     		$scope.MAINTENANCE_SLAB="0";
     		
     		$scope.TOTAL_MAINTENANCE="0.00";
     		totalMaint ="0.00";
     		$scope.POOL_CAR="N";
     	}else if($rootScope.staticInfoObject.TRANSFER_FLG!="WITHOUT") {
     		if(angular.isUndefined($scope.MAINTENANCE_SLAB) || $scope.MAINTENANCE_SLAB=="0")
     			error = error+"MAINTENANCE IS MANDATORY. \n";
     		//if(angular.isUndefined($scope.TOTAL_MAINTENANCE)|| $scope.TOTAL_MAINTENANCE==0) changed by nishant
     		if(angular.isUndefined($scope.TOTAL_MAINTENANCE))
     			error = error+"TOTAL MAINTENANCE IS MANDATORY. \n";
     		if(angular.isUndefined($scope.POOL_CAR)|| $scope.POOL_CAR==0)
     			error = error+"POOL CAR IS MANDATORY. \n";
     	}
     	
     						
     	maintenanceSaveStr =   MAINENANCE_ID+"~"+isMaintEnableFlag+"~"+$scope.MAINTENANCE_SLAB+"~"+amc_table_show+"~"+extended_table_show+"~"+$scope.POOL_CAR+"~"+totalMaint+"~"+$scope.EXTENDED_WARRANTY_IN_FMS; 
     						
     						
     	var amcKeyString ="MAINTENANCE_DTL_ID~MAINTENANCE_ID~MAINTENANCE_TYPE~START_DATE~END_DATE~YEARLY_COST~STRUCTURE_ACTIONABLE_ID~REMARKS~AMC_VENDOR_ID~AMC_VENDOR_GSTIN_ID";
     	var extKeyString ="MAINTENANCE_DTL_ID~MAINTENANCE_ID~MAINTENANCE_TYPE~START_DATE~END_DATE~YEARLY_COST~STRUCTURE_ACTIONABLE_ID~REMARKS~EXT_VENDOR_ID~EXT_VENDOR_GSTIN_ID";
     	
     	if($scope.AMC_details=="Y" && $scope.EXT_WARR=="N"){
     			maintenaceExtenedSaveStr=$scope.getSaveString(amcKeyString, "amcDetails");	
     	} else if($scope.EXT_WARR=="Y" && $scope.AMC_details=="N"){
     			maintenaceExtenedSaveStr = $scope.getSaveString(extKeyString, "extDetails");	
     	} else if($scope.EXT_WARR=="Y" && $scope.AMC_details=="Y"){
     			maintenaceExtenedSaveStr=$scope.getSaveString(amcKeyString, "amcDetails")+"^"+$scope.getSaveString(extKeyString, "extDetails");		
     	} else if($scope.EXT_WARR=="N" && $scope.AMC_details=="N"){
     			maintenaceExtenedSaveStr="";	
     	}
     	if($rootScope.staticInfoObject.TRANSFER_FLG!="WITHOUT" ) //1.0.0.45 //1.0.0.46 //&& entityId != "1000000008" removed by 1.0.0.48
		{
     	var amcDetailsValidationString="AMC_COST:TMV~AMC_DETAILS_STRUCTURE:DDMV" ;
		$scope.getValidation(amcDetailsValidationString,"amcDetails");
		
		var extWarrentyValidationString="EXTENDED_WARRANTY_COST:TMV~EXTENDED_WARRANTY_STRUCTURE:DDMV" ;
		$scope.getValidation(extWarrentyValidationString,"extDetails");
     	//	alert("AMc and Extened : "+ maintenaceExtenedSaveStr);
		
     		var valueAddedStr = $scope.valueAdded;
     		
     		if(!$scope.isMaintEnable){ //maintenance section is disabled//1.0.0.31
	     		for(var i=0;i<valueAddedStr.length; i++){// 1.0.0.32 start
		    		if((angular.isUndefined(valueAddedStr[i].MASTER_AMOUNT) || (Number(valueAddedStr[i].APPLICABILITY)!=0 && valueAddedStr[i].APPLICABILITY!="1000000001" && Number(valueAddedStr[i].MASTER_AMOUNT)==0)) && $scope.getDefaultValuesFlagForAddOnServices != true){ //changed by 1.0.0.37
		    			error = error+"PLEASE CLICK ON GET DEFAULT VAULES IN ADD ON SERVICES SECTION.\n";
		    			break;
		    		} if(angular.isUndefined(valueAddedStr[i].SERVICE_AMOUNT) || (document.getElementById("SERVICE_AMOUNT"+i).disabled==false && Number(valueAddedStr[i].SERVICE_AMOUNT)==0))
		    			error = error+"ADD ON SERVICES SECTION AMOUNT IS MANDATORY.\n";
		    	 }// 1.0.0.32 end
     		}
     		if(!$scope.isMaintEnable){ //maintenance section is disabled//1.0.0.31
    	    	var valueAddedValidationStr="APPLICABILITY:DDMV" ;
    			$scope.getValidation(valueAddedValidationStr,"valueAdded");
    			if(tempData.errorMessages!="Errors:")
    			{ 
    				alert((tempData.errorMessages).replace("Errors:","").toUpperCase());
    				tempData.errorMessages="Errors:";
    				return false ;
    			}
         	}
      }	
     	var valueAddedKeyString = "RENTAL_SERVICE_ID~SERVICE_ID~APPLICABILITY~QTY~SERVICE_AMOUNT:TMV~REMARKS~MASTER_AMOUNT";
     	valueAddedSaveStr = $scope.getSaveString(valueAddedKeyString,"valueAdded");
     	
     	
     	
     	//alert("valueAddedKeyString string : " +valueAddedSaveStr);
     	var monthlyChargeKeyString = "QUOTATION_CHARGE_ID~QUOTATION_ID~CHARGE:DDMV~AMOUNT:TMV~~~~~~~~~~~MASTER_AMOUNT~~~~~~~~~~~A";
     	monthlyChargesSaveStr = $scope.getSaveString(monthlyChargeKeyString,"monthlyCharges"); //monthyChargesSaveStr 
     	
     	
     	if($scope.AGREEMENT_TYPE_VALUE=="1000000003" && $rootScope.staticInfoObject.TRANSFER_FLG!="WITHOUT") //1.0.0.45
     		{
     		    if(monthlyChargesSaveStr=="")
     		    	{
     		    	   error = error+"MONTHLY CHARGES IS MANDATORY.\n";
     		    	}
     		}
     	//	alert("Monthly charges : " + monthlyChargesSaveStr );
     	
     	//1.0.0.46
     	if(dm_error !="" && entityId == "1000000008"){
     		alert(dm_error.toUpperCase());
     		dm_error="";
			return false;
     	}
     	//1.0.0.46
     	if(error!="" ) //1.0.0.46 // && entityId != "1000000008" removed by 1.0.0.48
		{ 
		alert(error.toUpperCase());
		error="";
			return false;
		}
     	// 1.0.0.32 start
     	if(tempData.errorMessages!="Errors:" ) //1.0.0.46 //&& entityId != "1000000008" removed by 1.0.0.48
		{ 
			alert((tempData.errorMessages).replace("Errors:","").toUpperCase());
			tempData.errorMessages="Errors:";
			return false ;
		}//1.0.0.32 end
     	return true;
     	} ;
     
     	//1.0.0.42 add start
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
     		//1.0.0.42 add end
		  error="";
		 if(!$scope.checkForm())
			 {
			  responseFlag="Y";//1.0.0.42
			  return;
			 }
		  if(error!="" )//1.0.0.46 //&& entityId != "1000000008" removed by 1.0.0.48
		   {
			  alert(error);
			  responseFlag="Y";//1.0.0.42
		      error="";
		    
		   }
		   else
		   {  
			   if($scope.saveInstallmentStrctureRows()){//  1.0.0.12
			   
			   var newLeaseStartDate= new Date($scope.START_DATE).toDateString();
			   var oldLeaseStartDate= new Date($rootScope.staticInfoObject.LEASE_START_DATE).toDateString();
			  if($rootScope.staticInfoObject.TRANSFER_FLG!="WITHOUT" && (($scope.LEASE_PERIOD!=$rootScope.staticInfoObject.TENURE)&&(Number($scope.LEASE_PERIOD)<60) &&($rootScope.staticInfoObject.REGISTRATION_TYPE=="COMMERCIAL"))||((newLeaseStartDate!=oldLeaseStartDate)&&($rootScope.staticInfoObject.REGISTRATION_TYPE=="COMMERCIAL")) )   //1.0.0.45
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
			 //  alert(removeChargeIdStr);
				 var bool = confirm(("Proceeding to save changes.").toUpperCase());
			    if(!bool)
			    {
			    	responseFlag = "Y";  
			    	return;
			    }	    
			    else
			    {	
							$.ajax({
					  		 url: "saveQuotationsQuotationDetail.sprg?isAjaxCalling=Y", //1.0.0.55 added  isAjaxCalling=Y
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
					  			   penaltyPercentStr:$scope.PENALTY_SAVE_STR,//  1.0.0.12
					  			   enquiryId:angular.element('#existingApplicantId').val()
					  			  }, 
					  		 ontext: document.body,
					  		 success: function(response) {
					  			if(response!=null && response!="") 
					  					{ var jsonStr = JSON.parse(response);
					  					if(jsonStr.Status=="SessionExpired") //1.0.0.55
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
					  						 {$window.location.href = "quotationDetails.sprg?screenId="+temp_screenId+"&mode="+temp_mode+"&screenName="+temp_screenName;}
					  					 }
					  					  else{
					  						responseFlag = "Y";//1.0.0.42 added
					  					  }
					  					}}});
						
			   }
		   	}else{
		   		responseFlag = "Y";//1.0.0.42 added
		   	}
		}
	};
	
	
	
	
	//  1.0.0.12 start
	/////////////////////////////////////////////////////////////////////////////
	
	$scope.penalty=[];
	var penaltyFrom=0;
	var maxtenure=$scope.LEASE_PERIOD;
	var penaltyTo=0;
	
	$scope.addPenalityRow= function(){
    	//alert(pos)
		
		var countPenalty= $scope.penalty.length;
		if(countPenalty>0){
			penaltyFrom=Number($scope.penalty[countPenalty-1].TENURE_TO)+1;
		}
		
		if($scope.penalty.length>0){
			if($scope.penalty[countPenalty-1].TENURE_TO==$scope.LEASE_PERIOD){
				//alert(("PENALTY % SCHEDULE CAN BE CREATED FOR TENURE ONLY.").toUpperCase());
				return;
				
			}
			
		}
		
		
	if(penaltyTo==$scope.LEASE_PERIOD){
		
		//alert(("PENALTY % SCHEDULE CAN BE CREATED FOR TENURE ONLY.").toUpperCase());
		return;
	}
	if(penaltyTo==0){
		penaltyTo=maxtenure;
	}
	if(penaltyFrom==0){
	penaltyFrom=1;
	}
	penaltyTo=$scope.LEASE_PERIOD;
	//var countPenalty= $scope.penalty.length;
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
        // tenureTo.value="";
       //  tenureTo.focus();
         x.TENURE_TO="";
        //$scope.penalty[index].TENURE_TO="";
         return;
         }
    	 
    	 penaltyTo=obj;
    	 penaltyFrom=Number(obj)+1;
    	 
    	 for(var i=0; i < $scope.penalty.length; i++)
  		{
         	$scope.penalty[i+1].TENURE_FROM=Number($scope.penalty[i].TENURE_TO)+1;
         	//penaltyFrom=Number($scope.penalty[i].TENURE_TO)+1;
  		}
    	 
    	 
     };
     
     
     
     
     
     
     
     
 	//function saveInstallmentStrctureRows()
 	$scope.saveInstallmentStrctureRows = function()
 	{
 		//if(document.forms[0].forecloserMethod.value=="1000000001"){
 		//var responseSplitInstallment = availableInstallmentStrRows.split("~");
 		var tempInstallmentFrom;
 		var tempInstallmentTo;
 		var tempInstallmentAmount;
 		var installmentSchStr="";
 		var templength;
 		
 		if($scope.FORECLOSURE_METHOD=="1000000001"){
 		for(var i=0; i < $scope.penalty.length; i++)
 		{
 			//if($scope.penalty[i].length > 0)
 			//{
 				 //alert("length=="+responseSplitInstallment[i].length)
 				tempInstallmentFrom = document.getElementById(('TENURE_FROM'+i));
 				tempInstallmentTo=document.getElementById(('TENURE_TO'+i));
 				tempInstallmentAmount = document.getElementById(('PENALTY_PERCENT'+i));
 				
 				removeCommaOfObject(tempInstallmentAmount);
 				
 				//alert("tempInstallmentFrom="+tempInstallmentFrom+" tempInstallmentTo="+tempInstallmentTo+" tempInstallmentAmount="+tempInstallmentAmount)
 			     installmentSchStr=installmentSchStr+tempInstallmentFrom.value + "~" + tempInstallmentTo.value + "~" + tempInstallmentAmount.value + "^";
 			      // alert("installmentSchStr=== "+installmentSchStr)
 			    
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
 			      
 			       /*if(tempInstallmentAmount.value=="" || tempInstallmentAmount.value==0.00 || tempInstallmentAmount.value==0 || tempInstallmentAmount.value=="0.00" || tempInstallmentAmount.value=="0" )
 			      {
 			            alert(("PENALTY PERCENT MUST BE SPECIFIED.").toUpperCase());
 				  		tempInstallmentAmount.select();
 			            tempInstallmentAmount.focus();
 			          return ;
 			      }*/
 			     
 			    //  if(IsBlankIns(tempInstallmentAmount,'Installment Amount'))
 				//  {
 				  		//alert(("INSTALLMENT AMOUNT MUST BE SPECIFIED IN INSTALLMENT DETAILS SECTION.").toUpperCase());
 				  		//tempInstallmentAmount.select();
 			       		//tempInstallmentAmount.focus();
 				   //	return ;	
 				 // } 
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
 				  		// alert("Charge Amount ("+i+"-ROW) should amount.");
 				  		tempInstallmentAmount.select();
 			       		tempInstallmentAmount.focus(); 
 				   	return ;	
 				  }
 				  } 

 			//}
 		
 		}	
 		if(installmentSchStr!=""){
 			if(Number(tempInstallmentTo.value)<Number($scope.LEASE_PERIOD))
 		{
 		 alert(("Last Installment To of Penalty Schedule Should be equal to LEASE TENURE "+Number($scope.LEASE_PERIOD)+".").toUpperCase())
 		 tempInstallmentTo.select();
 		 tempInstallmentTo.focus();
 		 return;
 		}
 		}
 		if(installmentSchStr==""){
 		alert("penalty block is mandatory.".toUpperCase())
 		return;
 		}
 		// alert("installmentSchStr=== "+installmentSchStr)
 		//document.forms[0].installmentDetailStr.value = installmentSchStr;
 		$scope.PENALTY_SAVE_STR = installmentSchStr;
 		
 				
 		return true;
 	}
 		
 		else{
 			$scope.PENALTY_SAVE_STR = "";	
 			return true;
 			
 		}
 	}
 //	}			
     // 1.0.0.15 start
 	$scope.setZero = function(val){
 		if(val=="" || val==undefined){
 			$scope.MORATORIUM="0";
 		}
 		
 	}
     // 1.0.0.15 end
 	
 	
 	
 	$scope.removePenaltyRow = function(index){
	    
   	 var newDataList=[];
        angular.forEach($scope.penalty, function(selected){
        	
            if(!selected.selectPenalty){
                newDataList.push(selected);
            }
            else {
           	 
           	// if(removeChargeIdStr!=""){
           	//	 removeChargeIdStr=removeChargeIdStr + "^"+ selected.QUOTATION_CHARGE_ID;
           	// }
           //	 else {
           	//	 removeChargeIdStr = selected.QUOTATION_CHARGE_ID;
           	// }
            }
        }); 
        $scope.penalty = newDataList;
        penaltyTo=Number($scope.penalty[$scope.penalty.length-1].TENURE_TO);
        for(var i=0; i < $scope.penalty.length; i++)
 		{
        	$scope.penalty[i+1].TENURE_FROM=Number($scope.penalty[i].TENURE_TO)+1;
        	//penaltyFrom=Number($scope.penalty[i].TENURE_TO)+1;
        	
 		}
       
        
        
    }; 
 	
 	
 	
 	
 	
 	
	
	//////////////////////////////////////////////////////////////////////////////////
	//  1.0.0.12 end
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
							
						if(validationKey!="" )//1.0.0.46 //&& entityId != "1000000008" removed by 1.0.0.48
						{
							lebalName=key.split("_").join(" ");
							//alert(key+validationKey+lebalName);
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
					 if( validationKey!=undefined && validationKey!=""  )//1.0.0.46 //&& entityId != "1000000008" removed by 1.0.0.48
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
    	//	alert(keyString);
    		var screenSaveString="";
    		var screenSaveStringWithKey="";
    		var value;
    		var validationMsg="";
    		var key="";
    		var lebalName="";
    		var validationKey=""; 
    		//alert($scope[objName].length)
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
    		
    		
    }//  end for validation method	
	
	$scope.removeCommaOfVariable = function(objValue)
	{
	//	alert(objValue);	
	try{
		if(objValue!="" && objValue!=0 && objValue!=undefined)  
		{
		objValue = objValue.replace(/,/g, "");
		//alert(objValue);
		}
	}
	catch(err)
	{}
		return objValue;
	}
	
	
	  $scope.dealerChooser = function(KeyName,obj,rowId)
	    {
	   // alert(rowId)
	     var ParamValue = "NA"+","+$rootScope.staticInfoObject.ASSET_CATEGORY_ID+","+"NA"+","+"NA"+","+"NA"; //changed by 1.0.0.39
	     //alert(ParamValue)
		  window.open("quotationMaster.sprg?KeyName="+KeyName+"&ParamValue="+ParamValue+"&label=DealerChooser&moduleType=quotation&dependent=quotationBodyId&objName="+obj+"&rowId="+rowId+"&defaultSearchFlag=N","popDealer","width=500, height=490,top=100,left=100, location=no, maximize=yes, menubar=no, status=no, toolbar=no, scrollbars=no, resizable=no","modal=yes" );//1.0.0.39 added defaultSearchFlag
		
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
                //(/[^\d|\-+|\.+]/g, '');
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
              // console.log(scope.item, item);
                
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
/*app.filter('IndianCurrency', function() {
return function(data) {
    if (null != data) {
        return Number(data).toLocaleString('en-IN', { style: 'currency', currency: 'INR'});
    } else {
        return Number(0).toLocaleString('en-IN', { style: 'currency',currency: 'INR'});
    }
};
});*/
