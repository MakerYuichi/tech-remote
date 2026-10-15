/*
+  File NAME 	: dmDisbursal.js
+  PURPOSE		: 
+  CREATED BY	: 
+  CREATION DATE	: 
+  INITIAL VERSION : 1.0.0.0
+  **********************************************************************************************************************************
+  VERSION NO   UPDATED BY       UPDATED ON      REASON FOR CHANGE
+  1.0.0.1  	 Ravi   		 06-Mar-2020	 Code change to remove all validation in case of cancel functionality
+  1.0.0.2  	 Ravinder   	 18-Mar-2020	 RSD validations.
+  1.0.0.3  	 Ravi   		 23-Mar-2020	 Code change for add new master security deposit
+  1.0.0.4  	 Ravi   		 28-Mar-2020	 Code change for effective date
+  1.0.0.5		 Sunny 			 02-Apr-2020	 Asset Transfer Bug resolution
+  1.0.0.6  	 Ravi   		 05-Apr-2020	 Code change for effective datenew lesse validation
+  1.0.0.7  	 Ravinder   	 09-Apr-2020	 disable cancel button when restructure Type is asset Extension for OPS user.
+  1.0.0.8		 Sunny 			 23-Apr-2020	 Condition of others checkbox added for repayment mode
+  1.0.0.9		 Ravinder 		 28-Apr-2020     Do not calculate Pos and Balance tenure for restructured dm's.
+  1.0.0.10  	 Ravi   		 10-Apr-2020	 Code change for disabled all save and cancled functionality for other users
+  1.0.0.11	 	 Sunny 			 07-may-2020	 Mkt user will not be able to cancel restructuring initiated by ops
+  1.0.0.12		 Sunny 			 29-May-2020	 Reestructure type passed in get POS details program
+  1.0.0.13		 Sunny			 22-Jun-2020	 CR - Scheme change on asset restructuring
+  1.0.0.14		 Sunny			 01-Aug-2020	 Change to handle null RSD and eff. transfer date.
+  1.0.0.15		 Sunny			 16-Sep-2020	 Changes for Bug-89097
+  1.0.0.16		 Hemant Kumar	 21-Feb-2022	 change security type in dropdown box by Default selection is “TRANSFER
+  1.0.0.17		Priyanka Soni	 28-Oct-2022		CR:- User Logout on EOD
+  1.0.0.18     Narottam Biswal   05-Jul-2023     validation added to select restructure(if change in lessee selected change in place of supply must be selected) 
+  1.0.0.19     Ravi Shankar     01-Dec-2023     PC_CHANGE_IN_RSD_FLG
+  1.0.0.20     Nalin Kumar Jena 27-Apr-2026     Tabs to be added in restructuring module
 ***********************************************************************************************************************************/
var app = angular.module('assetRestructureApp', []);
app.controller('assetRestructureCtrl', function($scope,$http,$window,$rootScope) {
	
	$scope.user=[];
	$scope.dmHistoryDtlObj=[];
	 var responseFlag = "Y";	
	 $scope.AGREEMENT_TYPE=[];
	 $scope.LEASE_REPAYMENT_MODE=[];
	 $scope.PLACE_OF_SUPPLY_STATE=[];
	 $scope.RESTRUCTURE_TYPE=[];
	 $scope.RESTRUCTURE_REASON=[];
	 $scope.LEASE_ITC_AVAIL_OPTIONS_TYPE=[];
	 $scope.LEASE_BILLING_STATE=[];
	 $scope.DISABLE_ALL_FIELDS_FLG=false;
	 $scope.DISB_RSD_FLAG=false;
	 $scope.disbCancelBtn=false;//1.0.0.7
	$scope.securityDepositFlag=true;
	$scope.SCHEME_MASTER = [];//1.0.0.13
	$scope.emptyDateFlg = false;
	$scope.emptyDateName = "";
	// Start 1.0.0.20
	// New Marketing Restructuring Fields
	$scope.PC_DOC_REQIRED_DTLS = [];
	$scope.EXTENSION_PERIOD_MASTER = [];
	$scope.AGREEMENT_SIGNING_TYPE_MASTER = [];
	$scope.AGREEMENT_SIGNING_TYPE_MASTER_ORIGINAL = [];
	$scope.DOCUMENTS_REQUIRED_MASTER = [];
	$scope.disableDocumentsRequired = false; // 1.0.0.21
	// End 1.0.0.20

	jsonStr = JSON.parse(mastersListStr);
	
	var arrPlaceOfSupply = jsonStr.PLACE_OF_SUPPLY_STATE_MASTER;
	for(var i =0;i< arrPlaceOfSupply.length;i++){
			$scope.PLACE_OF_SUPPLY_STATE.push({
					 'ID':arrPlaceOfSupply[i].ID,
					 'VALUE':arrPlaceOfSupply[i].NAME
			});
	}
	
	var arrBillingState = jsonStr.LEASE_BILLING_STATE_MASTER[0];
	for(var i =0;i< arrBillingState.length;i++){
			$scope.LEASE_BILLING_STATE.push({
					 'ID':arrBillingState[i].ID,
					 'VALUE':arrBillingState[i].NAME
			});
	}
	var arrLOCMaster = jsonStr.LOCMasterDetail;
	$scope.SECURITY_DEPOSIT_MASTER=jsonStr.SECURITY_DEPOSIT_MASTER[0];
	/*var arrSchemeMaster = arrLOCMaster.schemeMaster;
	for(var i =0;i< arrSchemeMaster.length;i++){
			$scope.SCHEME.push({
					 'ID':arrSchemeMaster[i].SCHEMEID,
					 'VALUE':arrSchemeMaster[i].SCHEMENAME
			});
	}*/
	if(arrLOCMaster.restructureTypeMaster)
		{
			var arrRestructureMaster = arrLOCMaster.restructureTypeMaster;
			for(var i =0;i< arrRestructureMaster.length;i++){
					$scope.RESTRUCTURE_TYPE.push({
							 'ID':arrRestructureMaster[i].id,
							 'VALUE':arrRestructureMaster[i].name
					});
			}
		}
	arrMaster = !angular.isUndefined(jsonStr.restructureMasters )?jsonStr.restructureMasters[0]:"";
	for(var i=0;i<arrMaster.length;i++){
		if(arrMaster[i].PARAM_NAME == "RENTAL AGREEMENT TYPE"){
				 $scope.AGREEMENT_TYPE.push ({
					 'ID':arrMaster[i].ID,
					 'NAME':arrMaster[i].NAME
				 });
		} 
		else if(arrMaster[i].PARAM_NAME == "LEASE REPAYMENT MODE"){
			 $scope.LEASE_REPAYMENT_MODE.push ({
				 'ID':arrMaster[i].ID,
				 'NAME':arrMaster[i].NAME
			 });
		} 
		else if (arrMaster[i].PARAM_NAME =="RESTRUCTURING REASON TYPE"){
			   $scope.RESTRUCTURE_REASON.push ({
				 'ID':arrMaster[i].ID,
				 'NAME':arrMaster[i].NAME
			   });
		   } 
		}

	// Start 1.0.0.20
	// Populate Extension Period Master
	var arrExtensionPeriodMaster = jsonStr.EXTENSION_PERIOD[0];
	for(var i =0;i< arrExtensionPeriodMaster.length;i++){
			$scope.EXTENSION_PERIOD_MASTER.push({
					 'ID':arrExtensionPeriodMaster[i].ID,
					 'NAME':arrExtensionPeriodMaster[i].NAME
			});
	}
	
	// Populate Agreement Signing Type Master
	var arrAgreementSigningTypeMaster = jsonStr.AGREEMENT_SIGNING_TYPE_MASTER[0];
	for(var i =0;i< arrAgreementSigningTypeMaster.length;i++){
			$scope.AGREEMENT_SIGNING_TYPE_MASTER.push({
					 'ID':arrAgreementSigningTypeMaster[i].ID,
					 'NAME':arrAgreementSigningTypeMaster[i].NAME
			});
	}
	$scope.AGREEMENT_SIGNING_TYPE_MASTER_ORIGINAL = angular.copy($scope.AGREEMENT_SIGNING_TYPE_MASTER);

	// Populate Documents Required Master
	$scope.DOCUMENTS_REQUIRED_MASTER.push({'ID': '0', 'NAME': 'SELECT'});
	$scope.DOCUMENTS_REQUIRED_MASTER.push({'ID': 'Y', 'NAME': 'Yes'});
	$scope.DOCUMENTS_REQUIRED_MASTER.push({'ID': 'N', 'NAME': 'No'});
	// End 1.0.0.20
  
	$scope.DISB_LESSEE_NEW = true;
	$scope.DISB_SCHEME_NEW = true;
	$scope.DISB_AGREEMENT_TYPE_NEW = true;
	$scope.DISB_PLACE_OF_SUPP_STATE_NEW = true;
	$scope.DISB_PLACE_OF_SUPP_ADD_NEW = true;
	$scope.DISB_SEZ_ZONE=true;
	$scope.DISB_AMT_CHARGES_NEW=true;
	$scope.DISB_BILLING_STATE_NEW = true;
	$scope.DISB_LIMIT_CODE = true;
	$scope.DISB_REPAYMENT_MODE = true;
	$scope.DISB_BILLING_ADDRESS_NEW = true;
	$scope.RSD_CHECKED_FLG=false;
	$scope.DISB_SCHEME=true;//1.0.0.13
		
	$scope.enableDisableFields = function(){
		// start changes for enable and disable
		if($scope.user.RSD_FLG == true ) //1
			{
			   $scope.RSD_CHECKED_FLG = true;
			   $scope.user.RSD_NEW=$scope.user.EFF_DATE;
			   $scope.user.AUTO_TRANSFER_FLG=false;
				$scope.user.SEZ_CHANGE_FLG=false;
				$scope.user.RENTAL_DTLS_CHANGE_FLG=false;
				$scope.user.LESSE_CHANGE_FLG=false;
				$scope.user.PLACE_OF_SUPP_FLG=false;
				$scope.user.AGREEMENT_TYPE_FLG=false;
				$scope.user.USER_CHANGE_FLG=false;
				$scope.user.OTHER_FLAG=false;
				$scope.getPosDetail($scope.user.RSD,"OLD RSD");//1.0.0.9//1.0.0.14
			}
		else
			{
			   $scope.RSD_CHECKED_FLG = false;
			   $scope.user.RSD_NEW=$scope.user.EFF_DATE;
			   $scope.getPosDetail($scope.user.EFF_DATE,"EFFECTIVE TRANSFER DATE");//1.0.0.9//1.0.0.14
			}
	if($scope.user.AUTO_TRANSFER_FLG == true  || $scope.user.LESSE_CHANGE_FLG == true || $scope.user.OTHER_FLAG == true  ){//2
			$scope.DISB_LESSEE_NEW = false;
		}
	else
		{
			$scope.DISB_LESSEE_NEW = true;
			$scope.user.LESSE_NEW = $scope.user.LESSEE;
			$scope.user.LESSE_CODE_NEW = $scope.user.LESSEE_CODE;
			$scope.user.LESSE_NEW_ID = $scope.user.LESSEE_ID;
	   }
	if($scope.user.AUTO_TRANSFER_FLG == true  || $scope.user.LESSE_CHANGE_FLG == true || $scope.user.OTHER_FLAG == true)//3
		{
		   $scope.DISB_LIMIT_CODE = false;
		}
	else
		{
		   $scope.DISB_LIMIT_CODE = true;
		   $scope.user.LIMIT_CODE_NEW_TEMP= $scope.user.LIMIT_CODE_TEMP;
			$scope.user.LIMIT_CODE_NEW=	$scope.user.LIMIT_CODE;
		}
	if($scope.user.AGREEMENT_TYPE_FLG == true || $scope.user.OTHER_FLAG == true ){ //4
		$scope.DISB_AGREEMENT_TYPE_NEW = false;
	}else{
		$scope.user.AGREEMENT_TYPE_NEW = $scope.user.AGREEMENT_TYPE;
		$scope.DISB_AGREEMENT_TYPE_NEW = true;
	}
	
	if($scope.user.LESSE_CHANGE_FLG == true || $scope.user.OTHER_FLAG == true )//5
		{
		   $scope.DISB_REPAYMENT_MODE = false;
		   $scope.DISB_BILLING_ADDRESS_NEW = false;
			$scope.DISB_BILLING_STATE_NEW = false;
		}
	else
		{
		  $scope.DISB_REPAYMENT_MODE = true;
		  $scope.DISB_BILLING_ADDRESS_NEW = true;
		  $scope.DISB_BILLING_STATE_NEW = true;
		  $scope.user.REPAYMENT_MODE_NEW = $scope.user.REPAYMENT_MODE;
		  $scope.user.BILLING_STATE_NEW = $scope.user.BILLING_STATE;
		  $scope.user.BILLING_ADDRESS_NEW = $scope.user.BILLING_ADDRESS;
		  $scope.user.BILLING_ADDRESS_NEW_TEMP = $scope.user.BILLING_ADDRESS_TEMP;
		}
	
	if($scope.user.PLACE_OF_SUPP_FLG == true || $scope.user.OTHER_FLAG == true ){ //6
		$scope.DISB_PLACE_OF_SUPP_STATE_NEW = false;
		$scope.DISB_PLACE_OF_SUPP_ADD_NEW = false;
	}else{
		$scope.user.PLACE_OF_SUPP_STATE_NEW = $scope.user.PLACE_OF_SUPP_STATE;
		$scope.user.PLACE_OF_SUPP_ADD_NEW = $scope.user.PLACE_OF_SUPP_ADD;
		$scope.user.PLACE_OF_SUPP_ADD_NEW_TEMP = $scope.user.PLACE_OF_SUPP_ADD_TEMP;
		$scope.DISB_PLACE_OF_SUPP_STATE_NEW = true;
		$scope.DISB_PLACE_OF_SUPP_ADD_NEW = true;
	}
	
	//1.0.0.13 start
	if($scope.user.RESTRUCTURE_TYPE == "1000000001"){
		if($scope.user.CHANGE_IN_SCHEME == true ){ 
			$scope.DISB_SCHEME = false;
		}else{
			$scope.user.SCHEME_NEW = $scope.user.SCHEME;
			$scope.DISB_SCHEME = true;
		}
	}
	//1.0.0.13 end
	
		// end changes for enable and disable
	
	 }
	 
	 // 1.0.0.21 - Documents Required auto-fill & disable logic
	 $scope.autoSelectDocumentsRequired = function(){
		 var restructureType = $scope.user.RESTRUCTURE_TYPE;
		 var agreementSigningType = $scope.user.AGREEMENT_SIGNING_TYPE;
		 var extensionPeriod = $scope.user.EXTENSION_PERIOD;
		 
		 if(!restructureType || !agreementSigningType || agreementSigningType == '0' || $scope.user.USER_TYPE !="MKT"){
			 $scope.user.DOCUMENTS_REQUIRED = '0';
			 $scope.disableDocumentsRequired = false;
			 return;
		 }
		 
		 if(restructureType == "1000000001" || restructureType == "1000000002") {
			 if(agreementSigningType == "1000000001") {
				 $scope.user.DOCUMENTS_REQUIRED = 'Y';
				 $scope.disableDocumentsRequired = true;
			 } else if(agreementSigningType == "1000000003" || agreementSigningType == "1000000002") {
				 $scope.disableDocumentsRequired = false;
			 } else if(agreementSigningType == "1000000004") {
				 $scope.user.DOCUMENTS_REQUIRED = 'N';
				 $scope.disableDocumentsRequired = true;
			 }
		 }
		 else if(restructureType == "1000000003") {
			 if(agreementSigningType == "1000000001") {
				 if(extensionPeriod == "1000000001") {
					 $scope.user.DOCUMENTS_REQUIRED = 'N';
					 $scope.disableDocumentsRequired = true;
				 } else {
					 $scope.user.DOCUMENTS_REQUIRED = 'Y';
					 $scope.disableDocumentsRequired = true;
				 }
			 } else if(agreementSigningType == "1000000003" || agreementSigningType == "1000000002") {
				 $scope.disableDocumentsRequired = false;
			 } else if(agreementSigningType == "1000000004") {
				 $scope.user.DOCUMENTS_REQUIRED = 'N';
				 $scope.disableDocumentsRequired = true;
			 }
		 }

	 }
	 

	 $scope.agreementSigningTypeSelectOnExtensionPeriod = function () {

		    // Null / Undefined check
		    if ($scope.user == null || 
		        $scope.user == undefined || 
		        $scope.user.EXTENSION_PERIOD == null || 
		        $scope.user.EXTENSION_PERIOD == undefined || 
		        $scope.user.EXTENSION_PERIOD == '') {

		        $scope.AGREEMENT_SIGNING_TYPE_MASTER = 
		            angular.copy($scope.AGREEMENT_SIGNING_TYPE_MASTER_ORIGINAL);

		        //$scope.user.AGREEMENT_SIGNING_TYPE = '0';
		        return;
		    }
		    $scope.user.AGREEMENT_SIGNING_TYPE = '0';
		    // If EXTENSION_PERIOD = 1000000002
		    if ($scope.user.EXTENSION_PERIOD == '1000000002') {

		        $scope.AGREEMENT_SIGNING_TYPE_MASTER =
		            $scope.AGREEMENT_SIGNING_TYPE_MASTER_ORIGINAL.filter(function(item) {

		                return item != null &&
		                       item != undefined &&
		                       item.ID != null &&
		                       item.ID != undefined &&
		                       (item.ID == '0' || item.ID == '1000000001');
		            });

		        // Auto select
		        //$scope.user.AGREEMENT_SIGNING_TYPE = '1000000001';

		    } else {

		        // Restore original dropdown
		        $scope.AGREEMENT_SIGNING_TYPE_MASTER =
		            angular.copy($scope.AGREEMENT_SIGNING_TYPE_MASTER_ORIGINAL);

		        // Reset selection
		        $scope.user.AGREEMENT_SIGNING_TYPE = '0';
		    }
		};
		
   // End 1.0.0.20

	 $scope.getPosDetail =function (effectiveDate,alias){//1.0.0.14
		 let isRsdChecked="";//1.0.0.19
		 if( $scope.user.RSD_FLG == false){
		    	$scope.user.RSD_NEW=$scope.user.EFF_DATE;
		    	isRsdChecked='N';//1.0.0.19
		    }else{
		    	isRsdChecked='Y';//1.0.0.19
		    }
		 var dmId = $scope.user.DM_CODE!=null?$scope.user.DM_CODE:"";
		 var restructureType = $scope.user.RESTRUCTURE_TYPE != null ?$scope.user.RESTRUCTURE_TYPE:"0";//1.0.0.12
		 
		 if(effectiveDate != null && effectiveDate != ""){//added by 1.0.0.14
		    $.ajax({  
				type : "post",   
				url : "getPosDetail.sprg?isAjaxCalling=Y", //1.0.0.17 added  isAjaxCalling=Y   
				data:{
					dmId: dmId,
					effectiveDate:effectiveDate,
					restructureType:restructureType ,//1.0.0.12
					isRsdChecked : isRsdChecked //1.0.0.19
			  		 },
				ontext: document.body,			
				success : function(response) {	
					    var jsonStr = JSON.parse(response);
					    if(jsonStr.Status=="SessionExpired") //1.0.0.17
  						{
  							window.location.href = 'llm/ReLogin.jsp?unknownError=Your session has been killed. Please login again.';
  							return;
  						}
					    if(jsonStr != null && jsonStr.PCUR_POS_DTL != null && jsonStr.PCUR_POS_DTL.length>0)
				    	{
				    		$scope.user.BAL_TENOR = jsonStr.PCUR_POS_DTL[0].BAL_TENOR;
				    		$scope.user.PRINCIPAL_OUTSTANDING = jsonStr.PCUR_POS_DTL[0].PRINCIPAL_OUTSTANDING;
				    	}
					    $scope.$apply();
		  	     	}
			});
		   
		}//add start by 1.0.0.14
		 else{
			 //alert(alias+" IS MANADATORY.");
			 $scope.emptyDateFlg = true;
			 $scope.emptyDateName = alias;
		 }
		 //add end by 1.0.0.14
	 };

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
		
		$scope.yearsDiff = function (d1, d2) {
		    var date1 = new Date(d1);
		    var date2 = new Date(d2);
		    var yearsDiff =  date2.getFullYear() - date1.getFullYear();
		    return yearsDiff;
		}
		
		//1.0.0.2 start
		$scope.validRsdDate = function (rentDate,deliveryDate1) {
			 
			    var rentDate = new Date(rentDate);
			    var deliveryDate = new Date(deliveryDate1);
			    
			    var deliveryDay = deliveryDate.getDate();
			    var deliveryMon = deliveryDate.getMonth();
			    var deliveryYear = deliveryDate.getFullYear();
			    
			    var deliveryDayLess = deliveryDate.getDate()-1;
			    var deliveryMonLess = deliveryDate.getMonth()-1;
			    var deliveryYearLess = deliveryDate.getFullYear()-1;
			    
			    var deliveryDayMore = deliveryDate.getDate()+1;
			    var deliveryMonMore = deliveryDate.getMonth()+1;
			    var deliveryYearMore = deliveryDate.getFullYear()+1;
			    
			    var rentDay = rentDate.getDate();
			    var rentMon = rentDate.getMonth();
			    var rentYear = rentDate.getFullYear();
			    
			    if(deliveryMon == "0" )
		    	{
			    	 if( (rentMon <= "1" && deliveryYear == rentYear) || (rentMon == "11" && deliveryYearLess == rentYear))
		    		 {
		    		 }
			    	 else
		    		 {
			    		 tempData.errorMessages = tempData.errorMessages + "\nRSD should always be +1 or -1 month from Delivery Date month("+deliveryDate1+").";
		    		 }
		    	}
			    else if(deliveryMon == "11" )
		    	{
			    	 if( (rentMon >= "10" && deliveryYear == rentYear) || (rentMon == "0" && deliveryYearMore == rentYear))
		    		 {
		    		 }
			    	 else
		    		 {
			    		 tempData.errorMessages = tempData.errorMessages + "\nRSD should always be +1 or -1 month from Delivery Date month("+deliveryDate1+").";
		    		 }
		    	}
			    else
		    	{
				     if(rentMon > deliveryMonMore || rentMon < deliveryMonLess || deliveryYear != rentYear)
	    			 {
				    	 tempData.errorMessages = tempData.errorMessages + "\nRSD should always be +1 or -1 month from Delivery Date month("+deliveryDate1+").";
	    			 }
		    	}
		 }
		//1.0.0.2 end
	$scope.setUserValues = function(obj){
		$scope.user.NEW_DM=!angular.isUndefined(obj.NEW_DM)?obj.NEW_DM :"";
		$scope.user.NEW_QUOTATION=!angular.isUndefined(obj.NEW_QUOTATION)?obj.NEW_QUOTATION :"";
		$scope.user.DM_CODE=!angular.isUndefined(obj.DM_CODE)?obj.DM_CODE :"";
		$scope.user.DM_CODE_TEMP=!angular.isUndefined(obj.DM_CODE_TEMP)?obj.DM_CODE_TEMP :"";
		$scope.user.LIMIT_CODE=!angular.isUndefined(obj.LIMIT_CODE)?obj.LIMIT_CODE :"";
		$scope.user.EFF_DATE=!angular.isUndefined(obj.EFF_DATE)?obj.EFF_DATE :businessDate;
		$scope.user.RESTRUCTURE_TYPE=!angular.isUndefined(obj.RESTRUCTURE_TYPE)?obj.RESTRUCTURE_TYPE :"0";
		$scope.user.RESTRUCTURE_REASON=!angular.isUndefined(obj.RESTRUCTURE_REASON)?obj.RESTRUCTURE_REASON :"0";
		$scope.user.RV=!angular.isUndefined(obj.RV)?obj.RV :"";
		$scope.user.BAL_TENOR=!angular.isUndefined(obj.BAL_TENOR)?obj.BAL_TENOR :"";
		$scope.user.MATURITY_DATE=!angular.isUndefined(obj.MATURITY_DATE)?obj.MATURITY_DATE :"";
		$scope.user.PRINCIPAL_OUTSTANDING=!angular.isUndefined(obj.PRINCIPAL_OUTSTANDING)?obj.PRINCIPAL_OUTSTANDING :"";
		$scope.user.RSD=!angular.isUndefined(obj.RSD)?obj.RSD :"";
		$scope.user.LESSEE=!angular.isUndefined(obj.LESSEE)?obj.LESSEE :"";
		$scope.user.LESSEE_CODE=!angular.isUndefined(obj.LESSEE_CODE)?obj.LESSEE_CODE :"";
		$scope.user.LESSEE_ID=!angular.isUndefined(obj.LESSEE_ID)?obj.LESSEE_ID :"";
		$scope.user.SEZ_ZONE=!angular.isUndefined(obj.SEZ_ZONE)?obj.SEZ_ZONE :"";
		$scope.user.AGREEMENT_TYPE=!angular.isUndefined(obj.AGREEMENT_TYPE)?obj.AGREEMENT_TYPE :"0";
		$scope.user.REPAYMENT_MODE=!angular.isUndefined(obj.REPAYMENT_MODE)?obj.REPAYMENT_MODE :"0";
		$scope.user.PLACE_OF_SUPP_STATE=!angular.isUndefined(obj.PLACE_OF_SUPP_STATE)?obj.PLACE_OF_SUPP_STATE :"0";
		$scope.user.PLACE_OF_SUPP_ADD=!angular.isUndefined(obj.PLACE_OF_SUPP_ADD)?obj.PLACE_OF_SUPP_ADD :"";
		$scope.user.BILLING_STATE=!angular.isUndefined(obj.BILLING_STATE)?obj.BILLING_STATE :"0";
		$scope.user.BILLING_ADDRESS=!angular.isUndefined(obj.BILLING_ADDRESS)?obj.BILLING_ADDRESS :"";
		$scope.user.RSD_NEW=!angular.isUndefined(obj.RSD_NEW)?obj.RSD_NEW :"";
		$scope.user.LESSE_NEW=!angular.isUndefined(obj.LESSE_NEW)?obj.LESSE_NEW :"";
		$scope.user.LESSE_CODE_NEW=!angular.isUndefined(obj.LESSE_CODE_NEW)?obj.LESSE_CODE_NEW :"";
		$scope.user.LESSE_NEW_ID=!angular.isUndefined(obj.LESSE_NEW_ID)?obj.LESSE_NEW_ID :"";
		$scope.user.AGREEMENT_TYPE_NEW=!angular.isUndefined(obj.AGREEMENT_TYPE_NEW)?obj.AGREEMENT_TYPE_NEW :"0";
		$scope.user.REPAYMENT_MODE_NEW=!angular.isUndefined(obj.REPAYMENT_MODE_NEW)?obj.REPAYMENT_MODE_NEW :"0";
		$scope.user.PLACE_OF_SUPP_STATE_NEW=!angular.isUndefined(obj.PLACE_OF_SUPP_STATE_NEW)?obj.PLACE_OF_SUPP_STATE_NEW :"0";
		$scope.user.PLACE_OF_SUPP_ADD_NEW=!angular.isUndefined(obj.PLACE_OF_SUPP_ADD_NEW)?obj.PLACE_OF_SUPP_ADD_NEW :"";
		$scope.user.PLACE_OF_SUPP_ADD_NEW_TEMP=!angular.isUndefined(obj.PLACE_OF_SUPP_ADD_NEW_TEMP)?obj.PLACE_OF_SUPP_ADD_NEW_TEMP :"";
		$scope.user.BILLING_STATE_NEW=!angular.isUndefined(obj.BILLING_STATE_NEW)?obj.BILLING_STATE_NEW :"0";
		$scope.user.BILLING_ADDRESS_NEW=!angular.isUndefined(obj.BILLING_ADDRESS_NEW)?obj.BILLING_ADDRESS_NEW :"";
		$scope.user.BILLING_ADDRESS_NEW_TEMP=!angular.isUndefined(obj.BILLING_ADDRESS_NEW_TEMP)?obj.BILLING_ADDRESS_NEW_TEMP :"";
		$scope.user.LIMIT_CODE_NEW=!angular.isUndefined(obj.LIMIT_CODE_NEW)?obj.LIMIT_CODE_NEW :"";
		$scope.user.LIMIT_CODE_NEW_TEMP=!angular.isUndefined(obj.LIMIT_CODE_NEW_TEMP)?obj.LIMIT_CODE_NEW_TEMP :"";
		$scope.user.AUTH_FLG=!angular.isUndefined(obj.AUTH_FLG)?obj.AUTH_FLG :"";
		$scope.user.RESTRUCTURE_ID=!angular.isUndefined(obj.RESTRUCTURE_ID)?obj.RESTRUCTURE_ID :"";
		$scope.user.DELIVERY_DATE=!angular.isUndefined(obj.DELIVERY_DATE)?obj.DELIVERY_DATE :"";//1.0.0.2
		$scope.user.SD_OPTIONS=!angular.isUndefined(obj.SD_OPTIONS)?obj.SD_OPTIONS :"0"; //1.0.0.3 start
		$scope.user.SECURITY_DEPOSIT=!angular.isUndefined(obj.SECURITY_DEPOSIT)?obj.SECURITY_DEPOSIT :"0"; 
		
		$scope.user.SCHEME_NEW=!angular.isUndefined(obj.SCHEME_NEW)?obj.SCHEME_NEW :"0";//1.0.0.13

		// Start 1.0.0.20
		// New Marketing Restructuring Fields initialization
		$scope.user.REQ_RECEIVED_FROM_CUST=!angular.isUndefined(obj.REQ_RECEIVED_FROM_CUST)?obj.REQ_RECEIVED_FROM_CUST :"";
		$scope.user.REQ_RECEIVED_FROM_BC=!angular.isUndefined(obj.REQ_RECEIVED_FROM_BC)?obj.REQ_RECEIVED_FROM_BC :"";
		$scope.user.EXTENSION_PERIOD=!angular.isUndefined(obj.EXTENSION_PERIOD)?obj.EXTENSION_PERIOD :"0";
		$scope.user.AGREEMENT_SIGNING_TYPE=!angular.isUndefined(obj.AGREEMENT_SIGNING_TYPE)?obj.AGREEMENT_SIGNING_TYPE :"0";
		$scope.user.DOCUMENTS_REQUIRED=!angular.isUndefined(obj.DOCUMENTS_REQUIRED)?obj.DOCUMENTS_REQUIRED :"0";
		// End 1.0.0.20
		
		if(!angular.isUndefined($scope.user.SECURITY_DEPOSIT) && $scope.user.SECURITY_DEPOSIT!="" && $scope.user.SECURITY_DEPOSIT!=null && parseFloat($scope.removeCommaOfVariable($scope.user.SECURITY_DEPOSIT))>0)
			{
			  $scope.securityDepositFlag= false;
			}
		//1.0.0.3 end
		/*$scope.user.RSD_FLG_VAL=!angular.isUndefined(obj.RSD_FLG_VAL)?obj.RSD_FLG_VAL :"N";
		$scope.user.AUTO_TRANSFER_FLG_VAL=!angular.isUndefined(obj.AUTO_TRANSFER_FLG_VAL)?obj.AUTO_TRANSFER_FLG_VAL :"N";
		$scope.user.SEZ_CHANGE_FLG_VAL=!angular.isUndefined(obj.SEZ_CHANGE_FLG_VAL)?obj.SEZ_CHANGE_FLG_VAL :"N";
		$scope.user.RENTAL_DTLS_CHANGE_FLG_VAL=!angular.isUndefined(obj.RENTAL_DTLS_CHANGE_FLG_VAL)?obj.RENTAL_DTLS_CHANGE_FLG_VAL :"N";
		$scope.user.LESSE_CHANGE_FLG_VAL=!angular.isUndefined(obj.LESSE_CHANGE_FLG_VAL)?obj.LESSE_CHANGE_FLG_VAL :"N";
		$scope.user.PLACE_OF_SUPP_FLG_VAL=!angular.isUndefined(obj.PLACE_OF_SUPP_FLG_VAL)?obj.PLACE_OF_SUPP_FLG_VAL :"N";
		$scope.user.AGREEMENT_TYPE_FLG=(!angular.isUndefined(obj.AGREEMENT_TYPE_FLG_VAL) && obj.AGREEMENT_TYPE_FLG_VAL=="Y")?true :false;
		$scope.user.USER_CHANGE_FLG_VAL=!angular.isUndefined(obj.USER_CHANGE_FLG_VAL)?obj.USER_CHANGE_FLG_VAL :"N";
		$scope.user.OTHER_FLAG_VAL=!angular.isUndefined(obj.OTHER_FLAG_VAL)?obj.OTHER_FLAG_VAL :"N";
		$scope.user.SEC_DEPOSIT_FLG_VAL=!angular.isUndefined(obj.SEC_DEPOSIT_FLG_VAL)?obj.SEC_DEPOSIT_FLG_VAL :"N";*/
		
		$scope.user.RSD_FLG=(!angular.isUndefined(obj.RSD_FLG_VAL) && obj.RSD_FLG_VAL=="Y")?true :false;
		$scope.user.AUTO_TRANSFER_FLG=(!angular.isUndefined(obj.AUTO_TRANSFER_FLG_VAL) && obj.AUTO_TRANSFER_FLG_VAL=="Y")?true :false;
		$scope.user.SEZ_CHANGE_FLG=(!angular.isUndefined(obj.SEZ_CHANGE_FLG_VAL) && obj.SEZ_CHANGE_FLG_VAL=="Y")?true :false;
		$scope.user.RENTAL_DTLS_CHANGE_FLG=(!angular.isUndefined(obj.RENTAL_DTLS_CHANGE_FLG_VAL) && obj.RENTAL_DTLS_CHANGE_FLG_VAL=="Y")?true :false;
		$scope.user.LESSE_CHANGE_FLG=(!angular.isUndefined(obj.LESSE_CHANGE_FLG_VAL) && obj.LESSE_CHANGE_FLG_VAL=="Y")?true :false;
		$scope.user.PLACE_OF_SUPP_FLG=(!angular.isUndefined(obj.PLACE_OF_SUPP_FLG_VAL) && obj.PLACE_OF_SUPP_FLG_VAL=="Y")?true :false;
		$scope.user.AGREEMENT_TYPE_FLG=(!angular.isUndefined(obj.AGREEMENT_TYPE_FLG_VAL) && obj.AGREEMENT_TYPE_FLG_VAL=="Y")?true :false;
		$scope.user.USER_CHANGE_FLG=(!angular.isUndefined(obj.USER_CHANGE_FLG_VAL) && obj.USER_CHANGE_FLG_VAL=="Y")?true :false;
		$scope.user.OTHER_FLAG=(!angular.isUndefined(obj.OTHER_FLAG_VAL) && obj.OTHER_FLAG_VAL=="Y")?true :false;
		$scope.user.SEC_DEPOSIT_FLG=(!angular.isUndefined(obj.SEC_DEPOSIT_FLG_VAL) && obj.SEC_DEPOSIT_FLG_VAL=="Y")?true :false;
		$scope.user.CHANGE_IN_SCHEME=(!angular.isUndefined(obj.CHANGE_IN_SCHEME_FLG) && obj.CHANGE_IN_SCHEME_FLG=="Y")?true :false;
		//CHANGE_IN_SCHEME
		if($scope.user.DM_CODE != "" && $scope.user.RESTRUCTURE_TYPE == '0'){//1.0.0.9
			/* var tempEffectiveDate="";// 1.0.0.4 start
			if($scope.user.RESTRUCTURE_TYPE=="1000000003"){
				tempEffectiveDate=$scope.user.MATURITY_DATE;
			}else{
				tempEffectiveDate=$scope.user.EFF_DATE;
			}
			$scope.getPosDetail(tempEffectiveDate);// 1.0.0.4 end
			*/
			$scope.getPosDetail($scope.user.EFF_DATE,"EFFECTIVE TRANSFER DATE");//1.0.0.91.0.0.14
		}
		if($scope.user.USER_TYPE != null && $scope.user.USER_TYPE == "OPS"){
			$scope.DISB_RSD_FLAG=true;
		//	$scope.RSD_CHECKED_FLG = true; //1.0.0.9 commented
		}
	};
	$scope.reset = function(){
		$scope.setUserValues($scope.user);
		
	};
	$scope.reset();
	
	$scope.getDetails = function(){
			
			var dmId = $scope.user.DM_CODE;
		    $.ajax({  
				type : "post",   
				url : "displayRestructuringDetails.sprg?isAjaxCalling=Y", //1.0.0.17 added  isAjaxCalling=Y   
				data:{
					dmId: dmId,
					screenId:screenId
			  		 },
				ontext: document.body,			
				success : function(response) {	
					    var jsonStr1 = JSON.parse(response);
					    if(jsonStr1.Status=="SessionExpired") //1.0.0.17
  						{
  							window.location.href = 'llm/ReLogin.jsp?unknownError=Your session has been killed. Please login again.';
  							return;
  						}
					    if(jsonStr1 != null && jsonStr1.PC_ASSET_RESTRUCTURE_DATA != null && jsonStr1.PC_ASSET_RESTRUCTURE_DATA.length>0)
				    	{
				    		$scope.user = jsonStr1.PC_ASSET_RESTRUCTURE_DATA[0];
				    		$scope.dmHistoryDtlObj = jsonStr1.PCUR_RESTRUCTURING_HST;
				    		$scope.PC_DOC_REQIRED_DTLS = jsonStr1.PC_DOC_REQIRED_DTLS;// 1.0.0.20
				    	}
					    else
					    {
					    	$scope.user = [];
					    	$scope.dmHistoryDtlObj=[];
					    	$scope.PC_DOC_REQIRED_DTLS=[];// 1.0.0.20
					    }
					    $scope.setUserValues($scope.user);
					    $scope.disbCancelBtn=false; // 1.0.0.10
					  //1.0.0.13 start
			    		var arrScheme = jsonStr1.SCHEME_MASTER;
			    		$scope.SCHEME_MASTER = angular.copy(jsonStr1.SCHEME_MASTER);
			    		//1.0.0.13 End
					    
					    if($scope.user.RESTRUCTURE_TYPE != '0'){
					    	//1.0.0.7 start
					    	$scope.RESTRUCTURE_TYPE=[];
					    	if(arrLOCMaster.restructureTypeMaster)
							{
								var arrRestructureMaster = arrLOCMaster.restructureTypeMaster;
								for(var i =0;i< arrRestructureMaster.length;i++){
										$scope.RESTRUCTURE_TYPE.push({
												 'ID':arrRestructureMaster[i].id,
												 'VALUE':arrRestructureMaster[i].name
										});
								}
							}
					    	//1.0.0.7 end
					    	
					    	$scope.DISABLE_ALL_FIELDS_FLG=true;
					    	if($scope.user.USER_TYPE != "OPS" && $scope.user.USER_TYPE != "MKT") // 1.0.0.10 start
				    		{
					    		$scope.disbCancelBtn=true; 
				    		} //1.0.0.10 end
					    }else{
					    	if($scope.user.USER_TYPE == "OPS" || $scope.user.USER_TYPE == "MKT") // 1.0.0.10 start
					    		{
					    		    $scope.DISABLE_ALL_FIELDS_FLG=false;
					    		}
					    	else
					    		{
					    		   $scope.DISABLE_ALL_FIELDS_FLG=true;
					    		   $scope.disbCancelBtn=true;
					    		}
					    	// 1.0.0.10 end 
					    	  //1.0.0.7 start
					    	//$scope.disbCancelBtn=false;
					    	if($scope.user.USER_TYPE == "OPS"){
						    	for(var i=0; i<$scope.RESTRUCTURE_TYPE.length; i++)
						 		{
						   			if($scope.RESTRUCTURE_TYPE[i].ID=="1000000003" || $scope.RESTRUCTURE_TYPE[i].ID=="1000000001" )
						   			{
						   			 $scope.RESTRUCTURE_TYPE.splice( i, 1 );   
						   			}
						    	}
						    	 //1.0.0.7 end
					    	}
					    }
					    
					    //Start 1.0.0.16
					    if($scope.user.SD_OPTIONS == "0" && $scope.user.SECURITY_DEPOSIT > 0) // 1.0.0.10 start
			    		{
					    	$scope.user.SD_OPTIONS = "1000000003"; 
			    		}
					    //End 1.0.0.16
					    
						$scope.$apply();
		  	     	}
			});
		}
	$scope.setDefaultValues = function(saveType){
		
		if(saveType=="CANCEL"){
		  $scope.user.RESTRUCTURE_TYPE="0";
		}
		if($scope.user.RESTRUCTURE_TYPE=="1000000003"){
			$scope.user.EFF_DATE=$scope.user.MATURITY_DATE;
			$scope.user.PRINCIPAL_OUTSTANDING = $scope.user.RV;//added by 1.0.0.5
		}else{
			$scope.user.EFF_DATE=businessDate;
		}
		/*if($scope.user.RESTRUCTURE_TYPE=="1000000002"){//1.0.0.3 start //commented by 1.0.0.16
			$scope.user.SD_OPTIONS="0";
		}*/// 1.0.0.3 end
		$scope.user.RSD_FLG_VAL="N";
		$scope.user.AUTO_TRANSFER_FLG_VAL="N";
		$scope.user.SEZ_CHANGE_FLG_VAL="N";
		$scope.user.RENTAL_DTLS_CHANGE_FLG_VAL="N";
		$scope.user.LESSE_CHANGE_FLG_VAL="N";
		$scope.user.PLACE_OF_SUPP_FLG_VAL="N";
		$scope.user.AGREEMENT_TYPE_FLG_VAL="N";
		$scope.user.SEC_DEPOSIT_FLG_VAL="N";
		$scope.user.USER_CHANGE_FLG_VAL="N";
		$scope.user.OTHER_FLAG_VAL="N";
		$scope.user.RSD_FLG=false;
		$scope.user.AUTO_TRANSFER_FLG=false;
		$scope.user.SEZ_CHANGE_FLG=false;
		$scope.user.RENTAL_DTLS_CHANGE_FLG=false;
		$scope.user.LESSE_CHANGE_FLG=false;
		$scope.user.PLACE_OF_SUPP_FLG=false;
		$scope.user.AGREEMENT_TYPE_FLG=false;
		$scope.user.SEC_DEPOSIT_FLG=false;
		$scope.user.USER_CHANGE_FLG=false;
		$scope.user.OTHER_FLAG=false;
		$scope.user.RESTRUCTURE_REASON="0";
		$scope.user.RESTRUCTURE_ID="";
		
		// Start 1.0.0.20
		if($scope.user.RESTRUCTURE_TYPE!="1000000003"){
			$scope.user.EXTENSION_PERIOD="0";
		}
		// 1.0.0.20
		$scope.enableDisableFields();
	}
	$scope.clearPlaceOfSupplyAddress=function(){
		$scope.user.PLACE_OF_SUPP_ADD_NEW = "";
		$scope.user.PLACE_OF_SUPP_ADD_NEW_TEMP = "";
	}
	$scope.showDetail = function(dmId,dmCode,application,sId)		
	{
		//window.open("baseFrmNavAction.do?dmProspectId="+dmId+"&prospectNo="+dmCode+"&prospectId="+dmId+"&entityId="+entityId+"&application="+application+"&childWindowFlg=Y","target","height=600,width=1100,resizable=yes,scrollbars=auto,top=70,left=150");
		var url ="historyViewer.sprg?prospectId="+dmId+"&prospectCode="+dmCode+"&application="+application+"&screenId="+sId+"&childWindowFlg=Y";
		window.open(url,"target","height=600,width=1100,resizable=yes,scrollbars=auto,top=70,left=150");
	}
	
	$scope.searchWindow = function(searchType,screenFlag)
	{
		$scope.user.LIMIT_CODE_NEW_TEMP="";
		$scope.user.LIMIT_CODE_NEW="";
	 var t="quotationSearchAction.do?actionPerformed=displaySearchScreen&searchType="+searchType+"&screenFlag="+screenFlag+"&parentBodyId=assetRestructureAppId";
	 window.open(t,"target","height=700,width=1000,resizable=yes,scrollbars=auto,top=100,left=200");
	
	}
	
	$scope.dmCodeChooser= function(key)
	{
		var paramValue="LIVE"; // can be LIVE/CLOSED/ALL 
		window.open("quotationMaster.sprg?KeyName="+key+"&ParamValue="+paramValue+"&label=DM_Code&moduleType=quotation&dependent=assetRestructureAppId&objName=user&rowId=1&defaultSearchFlag=N","popDealer","width=500, height=490,top=100,left=100, location=no, maximize=yes, menubar=no, status=no, toolbar=no, scrollbars=no, resizable=no","modal=yes" );
	
	};
	
	$scope.placeOfSupplyChooser= function(key)
	{
		var paramValue = ($scope.user.LESSE_NEW_ID!=null?$scope.user.LESSE_NEW_ID:"")+","+($scope.user.PLACE_OF_SUPP_STATE_NEW!=null?$scope.user.PLACE_OF_SUPP_STATE_NEW:"0")+","+($scope.user.DM_CODE!=null?$scope.user.DM_CODE:""); 
		window.open("quotationMaster.sprg?KeyName="+key+"&ParamValue="+paramValue+"&label=Place_of_Supply_Address&moduleType=quotation&dependent=assetRestructureAppId&objName=user&rowId=1&defaultSearchFlag=Y","popDealer","width=500, height=490,top=100,left=100, location=no, maximize=yes, menubar=no, status=no, toolbar=no, scrollbars=no, resizable=no","modal=yes" );
	
	};
	$scope.setTooltip=function(){
		$("#RESTRUCTURE_TYPE").attr("title",$("#RESTRUCTURE_TYPE option:selected").text())	;	
	}
	$scope.limitChooser= function(KeyName)
	{
		var paramValue = ($scope.user.LESSE_NEW_ID!=null?$scope.user.LESSE_NEW_ID:"")+","+($scope.user.QUOTATION_ID!=null?$scope.user.QUOTATION_ID:"")+",Y,"+($scope.user.PRINCIPAL_OUTSTANDING != null?$scope.user.PRINCIPAL_OUTSTANDING:"0");//1.0.0.15  
		window.open("quotationMaster.sprg?KeyName="+KeyName+"&ParamValue="+paramValue+"&label=Limit&moduleType=quotation&dependent=assetRestructureAppId&objName=user&rowId=1&defaultSearchFlag=Y","popDealer","width=500, height=490,top=100,left=100, location=no, maximize=yes, menubar=no, status=no, toolbar=no, scrollbars=no, resizable=no","modal=yes" );
	};
	
	$scope.billingAddressChooser= function(key)
	{
		var paramValue = ($scope.user.LESSE_NEW_ID!=null?$scope.user.LESSE_NEW_ID:""); 
		window.open("quotationMaster.sprg?KeyName="+key+"&ParamValue="+paramValue+"&label=Billing_Address&moduleType=quotation&dependent=assetRestructureAppId&objName=user&rowId=1&defaultSearchFlag=Y","popDealer","width=500, height=490,top=100,left=100, location=no, maximize=yes, menubar=no, status=no, toolbar=no, scrollbars=no, resizable=no","modal=yes" );
	
	};
	$scope.checkFormData=function(){
		
		// Start 1.0.0.20
		// New Marketing Restructuring Fields Validation - Applicable for all restructure types except RSD Change
		if($scope.user.USER_TYPE != null && $scope.user.USER_TYPE == "MKT" ){
			if($scope.user.RSD_FLG != true) {

			// 1.0.0.21 - Requirement 3.1
			var reqCustMandatory = false;
			if($scope.user.RESTRUCTURE_TYPE == "1000000001" || $scope.user.RESTRUCTURE_TYPE == "1000000002") {
				var reasonObj = $scope.RESTRUCTURE_REASON.find(function(item) { return item.ID == $scope.user.RESTRUCTURE_REASON; });
				var reasonName = reasonObj ? reasonObj.NAME : "";
				if(reasonName == "GST CHANGE/LOCATION CHANGE" || reasonName == "PLACE OF SUPPLY") {
					reqCustMandatory = true;
				}
			} else if($scope.user.RESTRUCTURE_TYPE == "1000000003") {
				reqCustMandatory = true;
			}
			if(reqCustMandatory && ($scope.user.REQ_RECEIVED_FROM_CUST == null || $scope.user.REQ_RECEIVED_FROM_CUST == "")){
				tempData.errorMessages = tempData.errorMessages +"\n"+"REQUEST RECEIVED FROM CUSTOMER MUST BE SPECIFIED.";
			}
			// 1.0.0.21 - Requirement 3.2
			var reqBcMandatory = false;
			if($scope.user.RESTRUCTURE_TYPE == "1000000001" || $scope.user.RESTRUCTURE_TYPE == "1000000002") {
				var reasonObj = $scope.RESTRUCTURE_REASON.find(function(item) { return item.ID == $scope.user.RESTRUCTURE_REASON; });
				var reasonName = reasonObj ? reasonObj.NAME : "";
				if(reasonName == "GST CHANGE/LOCATION CHANGE" || reasonName == "PLACE OF SUPPLY" || reasonName == "SEZ") {
					reqBcMandatory = true;
				}
			}
			if(reqBcMandatory && ($scope.user.REQ_RECEIVED_FROM_BC == null || $scope.user.REQ_RECEIVED_FROM_BC == "")){
				tempData.errorMessages = tempData.errorMessages +"\n"+"REQUEST RECEIVED FROM B&C MUST BE SPECIFIED.";
			}

			// Agreement Signing Type - Mandatory for all except RSD Change
			if($scope.user.AGREEMENT_SIGNING_TYPE == null || $scope.user.AGREEMENT_SIGNING_TYPE == "0"){
				tempData.errorMessages = tempData.errorMessages +"\n"+"AGREEMENT SIGNING TYPE MUST BE SPECIFIED.";
			}
			// Documents Required - Mandatory for all except RSD Change
			if($scope.user.DOCUMENTS_REQUIRED == null || $scope.user.DOCUMENTS_REQUIRED == "0" || $scope.user.DOCUMENTS_REQUIRED == ""){
				tempData.errorMessages = tempData.errorMessages +"\n"+"DOCUMENTS REQUIRED MUST BE SPECIFIED.";
			}
			// Extension Period - Mandatory for Extension type
			if($scope.user.RESTRUCTURE_TYPE == "1000000003"){
				if($scope.user.EXTENSION_PERIOD == null || $scope.user.EXTENSION_PERIOD == "0"){
					tempData.errorMessages = tempData.errorMessages +"\n"+"EXTENSION PERIOD MUST BE SPECIFIED.";
				}
			}

		  }
		}
		// End 1.0.0.20
		
		if($scope.user.RESTRUCTURE_TYPE == "1000000001"){
			
			if($scope.user.RESTRUCTURE_REASON == null || $scope.user.RESTRUCTURE_REASON=="0"){
				tempData.errorMessages = tempData.errorMessages +"\n"+"RESTRUCTURE REASON MUST BE SPECIFIED.";
			}
			/*if($scope.user.RESTRUCTURE_REASON == null || $scope.user.RESTRUCTURE_REASON=="0"){
				tempData.errorMessages = tempData.errorMessages +"\n"+"RESTRUCTURE REASON MUST BE SPECIFIED.";
			}duplicate validation*/
			if($scope.user.USER_TYPE!=null && $scope.user.USER_TYPE!="OPS" && $scope.user.RENTAL_DTLS_CHANGE_FLG_VAL =="N" && $scope.user.LESSE_CHANGE_FLG_VAL =="N" && $scope.user.SEZ_CHANGE_FLG_VAL =="N" && $scope.user.SEC_DEPOSIT_FLG_VAL == "N" && $scope.user.CHANGE_IN_SCHEME_FLG == "N"){
				tempData.errorMessages = tempData.errorMessages +"\n"+"At Least one Restructure Category must be checked.";
				
			}
			if($scope.user.LESSE_CHANGE_FLG_VAL =="Y"){
				if($scope.user.LESSE_NEW == null || $scope.user.LESSE_NEW == ""){
					tempData.errorMessages = tempData.errorMessages +"\n"+"LESSEE MUST BE SPECIFIED.";
				}
				//1.0.0.6 start
				if($scope.user.LESSEE != null && $scope.user.LESSEE!="" && $scope.user.LESSEE_CODE !=null && $scope.user.LESSEE_CODE != "" && $scope.user.LESSE_NEW != null &&  $scope.user.LESSE_NEW != "" && $scope.user.LESSE_CODE_NEW != null && $scope.user.LESSE_CODE_NEW !=""){
					if(($scope.user.LESSEE).trim() == ($scope.user.LESSE_NEW).trim() && ($scope.user.LESSEE_CODE).trim() == ($scope.user.LESSE_CODE_NEW).trim() ){
						tempData.errorMessages = tempData.errorMessages +"\n"+"OLD AND NEW VALUE OF LESSEE CAN'T BE SAME.";
					}
				}// 1.0.0.6 end
			}
			
			//1.0.0.13 start
			if($scope.user.CHANGE_IN_SCHEME_FLG =="Y"){
				if($scope.user.SCHEME != null && $scope.user.SCHEME!="" && $scope.user.SCHEME_NEW!=null && $scope.user.SCHEME_NEW !="0" ){
					if(($scope.user.SCHEME).toUpperCase() == ($scope.user.SCHEME_NEW).toUpperCase()){
						tempData.errorMessages = tempData.errorMessages +"\n"+"OLD AND NEW VALUE OF SCHEME CAN'T BE SAME.";
					}
				}
			}
			//1.0.0.13 end
			
		}
		else if($scope.user.RESTRUCTURE_TYPE == "1000000002"){
			
			
			
			
			if($scope.user.RSD_FLG_VAL =="N" && $scope.user.AUTO_TRANSFER_FLG_VAL =="N" && $scope.user.LESSE_CHANGE_FLG_VAL =="N" && $scope.user.PLACE_OF_SUPP_FLG_VAL =="N" && $scope.user.AGREEMENT_TYPE_FLG_VAL =="N" && $scope.user.OTHER_FLAG_VAL == "N" && $scope.user.USER_CHANGE_FLG_VAL=="N" && $scope.user.SEC_DEPOSIT_FLG_VAL == "N"){
				tempData.errorMessages = tempData.errorMessages +"\n"+"At Least one Restructure Category must be checked.";
				
			}
			if($scope.user.RSD_FLG_VAL =="N" && $scope.user.AUTO_TRANSFER_FLG_VAL =="N" && $scope.user.LESSE_CHANGE_FLG_VAL =="N" && $scope.user.PLACE_OF_SUPP_FLG_VAL =="N" && $scope.user.AGREEMENT_TYPE_FLG_VAL =="N" && $scope.user.OTHER_FLAG_VAL == "Y" && $scope.user.USER_CHANGE_FLG_VAL=="N" && $scope.user.SEC_DEPOSIT_FLG_VAL == "N"){
				if((($scope.user.AGREEMENT_TYPE).toUpperCase() == ($scope.user.AGREEMENT_TYPE_NEW).toUpperCase()) && 
						(($scope.user.LESSEE).trim() == ($scope.user.LESSE_NEW).trim() && ($scope.user.LESSEE_CODE).trim() == ($scope.user.LESSE_CODE_NEW).trim()) && 
						((($scope.user.BILLING_ADDRESS).trim() == ($scope.user.BILLING_ADDRESS_NEW).trim()) && (($scope.user.BILLING_ADDRESS_TEMP).trim() == ($scope.user.BILLING_ADDRESS_NEW_TEMP).trim())) && 
						((($scope.user.PLACE_OF_SUPP_ADD).toUpperCase() == ($scope.user.PLACE_OF_SUPP_ADD_NEW).toUpperCase()) && (($scope.user.PLACE_OF_SUPP_ADD_TEMP).toUpperCase() == ($scope.user.PLACE_OF_SUPP_ADD_NEW_TEMP).toUpperCase())) && 
						(($scope.user.LIMIT_CODE_TEMP).trim() == ($scope.user.LIMIT_CODE_NEW_TEMP).trim()) && 
						(($scope.user.REPAYMENT_MODE).trim() == ($scope.user.REPAYMENT_MODE_NEW).trim()) && 
						(($scope.user.PLACE_OF_SUPP_STATE).trim() == ($scope.user.PLACE_OF_SUPP_STATE_NEW).trim()) && 
						(($scope.user.BILLING_STATE).trim() == ($scope.user.BILLING_STATE_NEW).trim()))
					{
					   tempData.errorMessages = tempData.errorMessages +"\n"+"AT LEAST ONE OF THE RESTRUCTURING PARAMETER MUST BE MODIFIED.";
					}
				
				
			}
			if($scope.user.RSD_FLG_VAL =="Y"){
				if($scope.user.RSD_NEW == null || $scope.user.RSD_NEW == ""){
					tempData.errorMessages = tempData.errorMessages +"\n"+"NEW RSD DATE MUST BE SPECIFIED.";
				}else{/*
					if($scope.user.DELIVERY_DATE != "" && $scope.user.RSD_NEW != ""){
						$scope.validRsdDate($scope.user.RSD_NEW,$scope.user.DELIVERY_DATE);//1.0.0.2
					}rsd validation removed by ravinder
					*/
				}
				
			}
			if($scope.user.AGREEMENT_TYPE_FLG_VAL =="Y" || $scope.user.OTHER_FLAG_VAL =="Y"){
				if($scope.user.AGREEMENT_TYPE_NEW == null || $scope.user.AGREEMENT_TYPE_NEW == "0"){
					tempData.errorMessages = tempData.errorMessages +"\n"+"NEW AGREEMENT TYPE MUST BE SELECTED.";
				}
			}
			if($scope.user.AGREEMENT_TYPE_FLG_VAL =="Y"){
				if($scope.user.AGREEMENT_TYPE != null && $scope.user.AGREEMENT_TYPE!="" && $scope.user.AGREEMENT_TYPE_NEW!=null && $scope.user.AGREEMENT_TYPE_NEW !="0" ){
					if(($scope.user.AGREEMENT_TYPE).toUpperCase() == ($scope.user.AGREEMENT_TYPE_NEW).toUpperCase()){
						tempData.errorMessages = tempData.errorMessages +"\n"+"OLD AND NEW VALUE OF AGREEMENT TYPE CAN'T BE SAME.";
					}
				}
			}
			if($scope.user.LESSE_CHANGE_FLG_VAL =="Y" ||$scope.user.AUTO_TRANSFER_FLG_VAL =="Y" || $scope.user.OTHER_FLAG_VAL =="Y"){
				if($scope.user.LESSE_NEW == null || $scope.user.LESSE_NEW == ""){
					tempData.errorMessages = tempData.errorMessages +"\n"+"NEW LESSEE MUST BE SPECIFIED.";
				}
			}
			//added by 1.0.0.18
			if($scope.user.LESSE_CHANGE_FLG_VAL =="Y" && $scope.user.PLACE_OF_SUPP_FLG_VAL !="Y")
				{
				tempData.errorMessages = tempData.errorMessages +"\n"+"CHANGE IN PLACE OF SUPPLY NEED TO BE SELECTED";
				}
			//ended by 1.0.0.18
			if($scope.user.LESSE_CHANGE_FLG_VAL =="Y" ||$scope.user.AUTO_TRANSFER_FLG_VAL =="Y"){
				if($scope.user.LESSEE != null && $scope.user.LESSEE!="" && $scope.user.LESSEE_CODE !=null && $scope.user.LESSEE_CODE != "" && $scope.user.LESSE_NEW != null &&  $scope.user.LESSE_NEW != "" && $scope.user.LESSE_CODE_NEW != null && $scope.user.LESSE_CODE_NEW !=""){
					if(($scope.user.LESSEE).trim() == ($scope.user.LESSE_NEW).trim() && ($scope.user.LESSEE_CODE).trim() == ($scope.user.LESSE_CODE_NEW).trim() ){
						tempData.errorMessages = tempData.errorMessages +"\n"+"OLD AND NEW VALUE OF LESSEE CAN'T BE SAME.";
					}
					if((($scope.user.LESSEE).trim() != ($scope.user.LESSE_NEW).trim()) && (($scope.user.LESSEE_CODE).trim() != ($scope.user.LESSE_CODE_NEW).trim()) && (($scope.user.BILLING_ADDRESS).trim() == ($scope.user.BILLING_ADDRESS_NEW).trim()) && (($scope.user.BILLING_ADDRESS_TEMP).trim() == ($scope.user.BILLING_ADDRESS_NEW_TEMP).trim())){
						tempData.errorMessages = tempData.errorMessages +"\n"+"OLD AND NEW VALUE OF BILLING ADDRESS CAN'T BE SAME.";
					}
				}
				if($scope.user.LIMIT_CODE_NEW == null || $scope.user.LIMIT_CODE_NEW==""){
					tempData.errorMessages = tempData.errorMessages +"\n"+"LIMIT CODE MUST BE SPECIFIED.";
				}
				
			}
			if($scope.user.OTHER_FLAG_VAL =="Y"){ //added by 1.0.0.8
				if($scope.user.REPAYMENT_MODE_NEW == null || $scope.user.REPAYMENT_MODE_NEW == "0"){
					tempData.errorMessages = tempData.errorMessages +"\n"+"NEW REPAYMENT MODE MUST BE SELECTED.";
				}
			}
			
			if($scope.user.PLACE_OF_SUPP_FLG_VAL =="Y" || $scope.user.OTHER_FLAG_VAL =="Y"){
				if($scope.user.PLACE_OF_SUPP_STATE_NEW == null || $scope.user.PLACE_OF_SUPP_STATE_NEW == "0"){
					tempData.errorMessages = tempData.errorMessages +"\n"+"NEW PLACE OF SUPPLY STATE MUST BE SELECTED.";
				}
				if($scope.user.PLACE_OF_SUPP_ADD_NEW == null || $scope.user.PLACE_OF_SUPP_ADD_NEW == "" || $scope.user.PLACE_OF_SUPP_ADD_NEW == "0"){
					tempData.errorMessages = tempData.errorMessages +"\n"+"NEW PLACE OF SUPPLY ADDRESS MUST BE SELECTED.";
				}
				
			}
				if($scope.user.PLACE_OF_SUPP_FLG_VAL =="Y"){
				if($scope.user.PLACE_OF_SUPP_ADD != null && $scope.user.PLACE_OF_SUPP_ADD!="" && $scope.user.PLACE_OF_SUPP_ADD_TEMP != null && $scope.user.PLACE_OF_SUPP_ADD_TEMP!="" && $scope.user.PLACE_OF_SUPP_ADD_NEW!=null && $scope.user.PLACE_OF_SUPP_ADD_NEW !="" &&  $scope.user.PLACE_OF_SUPP_ADD_NEW_TEMP!=null && $scope.user.PLACE_OF_SUPP_ADD_NEW_TEMP !="" ){
					if(($scope.user.PLACE_OF_SUPP_ADD).toUpperCase() == ($scope.user.PLACE_OF_SUPP_ADD_NEW).toUpperCase() && ($scope.user.PLACE_OF_SUPP_ADD_TEMP).toUpperCase() == ($scope.user.PLACE_OF_SUPP_ADD_NEW_TEMP).toUpperCase()){
						tempData.errorMessages = tempData.errorMessages +"\n"+"OLD AND NEW VALUE OF PLACE OF SUPPLY ADDRESS CAN'T BE SAME.";
					}
				}
			}
			/*if($scope.user.REPAYMENT_MODE != null && $scope.user.REPAYMENT_MODE!="" && $scope.user.REPAYMENT_MODE_NEW!=null && $scope.user.REPAYMENT_MODE_NEW !="0" ){
				if(($scope.user.REPAYMENT_MODE).toUpperCase() == ($scope.user.REPAYMENT_MODE_NEW).toUpperCase()){
					tempData.errorMessages = tempData.errorMessages +"\n"+"OLD AND NEW VALUE OF REPAYMENT MODE CAN'T BE SAME.";
				}
			}*/
			
		}// 1.0.0.3 start
		if(($scope.user.RESTRUCTURE_TYPE=="1000000001" || $scope.user.RESTRUCTURE_TYPE=="1000000003") && (!angular.isUndefined($scope.user.SECURITY_DEPOSIT) && $scope.user.SECURITY_DEPOSIT!="" && $scope.user.SECURITY_DEPOSIT!=null && parseFloat($scope.removeCommaOfVariable($scope.user.SECURITY_DEPOSIT))>0)&&(!angular.isUndefined($scope.user.SD_OPTIONS) && $scope.user.SD_OPTIONS=="0")){
			tempData.errorMessages = tempData.errorMessages +"\n"+"SECURITY DEPOSIT MUST BE SELECTED.";
		}// 1.0.0.3 end
	
	}

	
	$scope.save = function(saveType){
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
	};
	
	var assetRestructureSaveStr="";
	
	$scope.saveData = function(saveType){
		
		var cancelFlag = "N";
		
		tempData.errorMessages="";
		
			if(!angular.isUndefined($scope.user.RSD_FLG) && $scope.user.RSD_FLG==true )
	 		{
		 			$scope.user.RSD_FLG_VAL = "Y";
	 		}
	    	else
	    	{
	    		    $scope.user.RSD_FLG_VAL = "N";
	    	}
			if(!angular.isUndefined($scope.user.AUTO_TRANSFER_FLG) && $scope.user.AUTO_TRANSFER_FLG==true)
	 		{
		 			$scope.user.AUTO_TRANSFER_FLG_VAL = "Y";
	 		}
	    	else
	    	{
	    		    $scope.user.AUTO_TRANSFER_FLG_VAL = "N";
	    	}
			if(!angular.isUndefined($scope.user.LESSE_CHANGE_FLG) && $scope.user.LESSE_CHANGE_FLG==true)
	 		{
		 			$scope.user.LESSE_CHANGE_FLG_VAL = "Y";
	 		}
	    	else
	    	{
	    		    $scope.user.LESSE_CHANGE_FLG_VAL = "N";
	    	}
			if(!angular.isUndefined($scope.user.PLACE_OF_SUPP_FLG) && $scope.user.PLACE_OF_SUPP_FLG==true)
	 		{
		 			$scope.user.PLACE_OF_SUPP_FLG_VAL = "Y";
	 		}
	    	else
	    	{
	    		    $scope.user.PLACE_OF_SUPP_FLG_VAL = "N";
	    	}
			if(!angular.isUndefined($scope.user.AGREEMENT_TYPE_FLG) && $scope.user.AGREEMENT_TYPE_FLG==true)
	 		{
		 			$scope.user.AGREEMENT_TYPE_FLG_VAL = "Y";
	 		}
	    	else
	    	{
	    		    $scope.user.AGREEMENT_TYPE_FLG_VAL = "N";
	    	}
			if(!angular.isUndefined($scope.user.SEC_DEPOSIT_FLG) && $scope.user.SEC_DEPOSIT_FLG==true)
	 		{
		 			$scope.user.SEC_DEPOSIT_FLG_VAL = "Y";
	 		}
	    	else
	    	{
	    		    $scope.user.SEC_DEPOSIT_FLG_VAL = "N";
	    	}
			if(!angular.isUndefined($scope.user.USER_CHANGE_FLG) && $scope.user.USER_CHANGE_FLG==true)
	 		{
		 			$scope.user.USER_CHANGE_FLG_VAL = "Y";
	 		}
	    	else
	    	{
	    		    $scope.user.USER_CHANGE_FLG_VAL = "N";
	    	}
			if(!angular.isUndefined($scope.user.SEZ_CHANGE_FLG) && $scope.user.SEZ_CHANGE_FLG==true)
	 		{
		 			$scope.user.SEZ_CHANGE_FLG_VAL = "Y";
	 		}
	    	else
	    	{
	    		    $scope.user.SEZ_CHANGE_FLG_VAL = "N";
	    	}
			if(!angular.isUndefined($scope.user.OTHER_FLAG) && $scope.user.OTHER_FLAG==true)
	 		{
		 			$scope.user.OTHER_FLAG_VAL = "Y";
	 		}
	    	else
	    	{
	    		    $scope.user.OTHER_FLAG_VAL = "N";
	    	}
			if(!angular.isUndefined($scope.user.RENTAL_DTLS_CHANGE_FLG) && $scope.user.RENTAL_DTLS_CHANGE_FLG==true)
	 		{
		 			$scope.user.RENTAL_DTLS_CHANGE_FLG_VAL = "Y";
	 		}
	    	else
	    	{
	    		    $scope.user.RENTAL_DTLS_CHANGE_FLG_VAL = "N";
	    	}
			//1.0.0.13 start
			if(!angular.isUndefined($scope.user.CHANGE_IN_SCHEME) && $scope.user.CHANGE_IN_SCHEME==true)
	 		{
		 			$scope.user.CHANGE_IN_SCHEME_FLG = "Y";
	 		}
	    	else
	    	{
	    		    $scope.user.CHANGE_IN_SCHEME_FLG = "N";
	    	}
			//1.0.0.13 end
			if($scope.user.DM_CODE == null || $scope.user.DM_CODE ==""){
				tempData.errorMessages = tempData.errorMessages +"\n"+"DM CODE MUST BE SPECIFIED.";
			}
			
			//add start by 1.0.0.14
			if($scope.emptyDateFlg){
				tempData.errorMessages = tempData.errorMessages +"\n"+$scope.emptyDateName+" IS MANDATORY.";
			}
			//add end by 1.0.0.14
				$scope.checkFormData();

			// Start 1.0.0.20
				if($scope.user.DOCUMENTS_REQUIRED == null || $scope.user.DOCUMENTS_REQUIRED == "0"){
					$scope.user.DOCUMENTS_REQUIRED = "";
				}
			// End 1.0.0.20 
			//var assetRestructureDtls ="RESTRUCTURE_ID~RESTRUCTURE_TYPE:DDMV~DM_CODE~EFF_DATE:TMV~RSD_FLG_VAL~AUTO_TRANSFER_FLG_VAL~LESSE_CHANGE_FLG_VAL~PLACE_OF_SUPP_FLG_VAL~AGREEMENT_TYPE_FLG_VAL~SEC_DEPOSIT_FLG_VAL~USER_CHANGE_FLG_VAL~OTHER_FLAG_VAL~SEZ_CHANGE_FLG_VAL~RENTAL_DTLS_CHANGE_FLG_VAL~RSD_NEW~LESSE_NEW_ID~SCHEME_NEW~AGREEMENT_TYPE_NEW~REPAYMENT_MODE_NEW~PLACE_OF_SUPP_STATE_NEW~PLACE_OF_SUPP_ADD_NEW~RESTRUCTURE_REASON~LIMIT_CODE_NEW~BILLING_STATE_NEW~BILLING_ADDRESS_NEW~RV~PRINCIPAL_OUTSTANDING~BAL_TENOR~SD_OPTIONS~SECURITY_DEPOSIT~CHANGE_IN_SCHEME_FLG";// 1.0.0.3//1.0.0.13  // Commented by 1.0.0.20
			var assetRestructureDtls ="RESTRUCTURE_ID~RESTRUCTURE_TYPE:DDMV~DM_CODE~EFF_DATE:TMV~RSD_FLG_VAL~AUTO_TRANSFER_FLG_VAL~LESSE_CHANGE_FLG_VAL~PLACE_OF_SUPP_FLG_VAL~AGREEMENT_TYPE_FLG_VAL~SEC_DEPOSIT_FLG_VAL~USER_CHANGE_FLG_VAL~OTHER_FLAG_VAL~SEZ_CHANGE_FLG_VAL~RENTAL_DTLS_CHANGE_FLG_VAL~RSD_NEW~LESSE_NEW_ID~SCHEME_NEW~AGREEMENT_TYPE_NEW~REPAYMENT_MODE_NEW~PLACE_OF_SUPP_STATE_NEW~PLACE_OF_SUPP_ADD_NEW~RESTRUCTURE_REASON~LIMIT_CODE_NEW~BILLING_STATE_NEW~BILLING_ADDRESS_NEW~RV~PRINCIPAL_OUTSTANDING~BAL_TENOR~SD_OPTIONS~SECURITY_DEPOSIT~CHANGE_IN_SCHEME_FLG~REQ_RECEIVED_FROM_BC~REQ_RECEIVED_FROM_CUST~EXTENSION_PERIOD~AGREEMENT_SIGNING_TYPE~DOCUMENTS_REQUIRED";//  1.0.0.20 
			assetRestructureSaveStr = $scope.getSaveString(assetRestructureDtls, "user");
			
				
			//1.0.0.1 start
			if(saveType!="CANCEL"){
				if(tempData.errorMessages !=null && tempData.errorMessages!=""){
					alert((tempData.errorMessages).toUpperCase());
					responseFlag = "Y";  
					return;
				}
			}//1.0.0.1 end
		var bool=false;
		if(saveType=="CANCEL"){
			//1.0.0.7 start
			if($scope.user.USER_TYPE == "OPS"){
	    		
	    		if(!($scope.user.RESTRUCTURE_TYPE=="1000000002"&& $scope.user.RSD_FLG_VAL =="Y"))
	    		{	
	    			alert("You are not authorised to cancel this restructure activity.".toUpperCase());
	    			responseFlag = "Y";  
	    	    	return;
	    		}
	    	}//1.0.0.7 end
			//1.0.0.11 start
			if($scope.user.USER_TYPE == "MKT"){
	    		
	    		if(($scope.user.RESTRUCTURE_TYPE=="1000000002"&& $scope.user.RSD_FLG_VAL =="Y"))
	    		{	
	    			alert("You are not authorised to cancel this restructure activity.".toUpperCase());
	    			responseFlag = "Y";  
	    	    	return;
	    		}
	    	}
			//1.0.0.11 end
			cancelFlag = "Y";
		    bool = confirm(("Processing cancel request.").toUpperCase());
		}
		else
		{
		bool = confirm(("Proceeding to save changes.").toUpperCase());
		}
	    if(!bool)
	    {
	    	responseFlag = "Y";  
	    	return;
	    }	    
	    else
        {	
				$.ajax({
		  		 url: "saveRestructuringDetails.sprg?isAjaxCalling=Y", //1.0.0.17 added  isAjaxCalling=Y",
		  		 type: "post",
		  		 data:{
		  			assetRestructureSaveStr:assetRestructureSaveStr,
		  			cancelFlag:cancelFlag,
		  			dmId:$scope.user.DM_CODE
		  			  }, 
		  		 ontext: document.body,
		  		 success: function(response) {
		  			if(response!=null && response!="") 
		  			{
		  				var jsonStr = JSON.parse(response);
		  				if(jsonStr.Status=="SessionExpired") //1.0.0.17
  						{
  							window.location.href = 'llm/ReLogin.jsp?unknownError=Your session has been killed. Please login again.';
  							return;
  						}
		  				var msg =jsonStr.PC_OUT_MESSAGE;
		  				alert(msg.toUpperCase());
		  			    if(saveType!="CANCEL") {
		  			        if(jsonStr.PC_STATUS == "S"){
		  			        	// code to set value start
		  			        	if(jsonStr != null && jsonStr.PC_ASSET_RESTRUCTURE_DATA != null && jsonStr.PC_ASSET_RESTRUCTURE_DATA.length>0)
						    	{
						    		$scope.user = jsonStr.PC_ASSET_RESTRUCTURE_DATA[0];
						    		$scope.dmHistoryDtlObj = jsonStr.PCUR_RESTRUCTURING_HST;
						    		$scope.setUserValues($scope.user);
						    		$scope.PC_DOC_REQIRED_DTLS = jsonStr.PC_DOC_REQIRED_DTLS;// 1.0.0.20
						    	}
							    // code to set value end
		  			        	$scope.DISABLE_ALL_FIELDS_FLG=true;
		  			        }
		  			    }else{
		  			    	 if(jsonStr.PC_STATUS == "S"){//1.0.0.7
		  			    		 $scope.DISABLE_ALL_FIELDS_FLG=false;
		  			    		 $scope.setDefaultValues(saveType);
		  			    		//1.0.0.7 start
		  			    		if($scope.user.USER_TYPE == "OPS"){
							    	for(var i=0; i<$scope.RESTRUCTURE_TYPE.length; i++)
							 		{
							   			if($scope.RESTRUCTURE_TYPE[i].ID=="1000000003" || $scope.RESTRUCTURE_TYPE[i].ID=="1000000001" )
							   			{
							   			 $scope.RESTRUCTURE_TYPE.splice( i, 1 );   
							   			}
							    	}
							    	 //1.0.0.7 end
						    	}
		  			    	 }
		  			    }
		  			    responseFlag = "Y"; 
		  			    $scope.$apply();
					}
				}
				});
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
			if($scope[objName].length==undefined || $scope[objName].length == 0)
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
						value=value.trim();	
						if(validationKey!="")
						{
							lebalName=key.split("_").join(" ");
							if(!document.getElementById(key).disabled)
	 						{
	 						validate(document.getElementById(key),validationKey,lebalName);	
	 						}						
						}
					}	
		     		value=$scope.removeCommaOfVariable(value);	
					screenSaveString+=value+"~";
				}
				screenSaveString = screenSaveString.substring(0, screenSaveString.length-1);
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
							if(!document.getElementById(key).disabled)
	 						{
	 						validate(document.getElementById(key),validationKey,lebalName);	
	 						}						
						}	
					}
		     		value=$scope.removeCommaOfVariable(value);	
					screenSaveString+=value+"~";
					}
					screenSaveString = screenSaveString.substring(0, screenSaveString.length-1);
					screenSaveString+="^";
				}
			}
			screenSaveString=screenSaveString.substring(0,screenSaveString.length-1);
			
			return screenSaveString;
	};
	/*$scope.removeCommaOfVariable = function(objValue)
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
	}*/
	$scope.getValidation = function(keyString,objName)
    {
    	var screenKeyStringArray=keyString.split("~");
    		var screenSaveString="";
    		var screenSaveStringWithKey="";
    		var value;
    		var validationMsg="";
    		if($scope[objName].length==undefined || $scope[objName].length == 0)
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
    						if(!document.getElementById(key).disabled)
    						{
    						validate(document.getElementById(key),validationKey,lebalName);	
    						}					
    					}	
    				}
    			}
    		}
    };

});
