/*<!--+  File NAME 	: invoice_controller.js


+  PURPOSE		: 
+  CREATED BY	: 
+  CREATION DATE	: 
+  INITIAL VERSION : 1.0.0.0
+  **********************************************************************************************************************************
+  VERSION NO   UPDATED BY       UPDATED ON       REASON FOR CHANGE
+  1.0.0.1		Nishant 		 6-JUN-2018	 	  header change for report
+  1.0.0.2	    Apurva Shukla    21 june 2018     to make  invoice form and to date configurable 
+  1.0.0.3	    Ravi      	     27 jun 2018      Changes for invoice issue. bug id 59830 
+  1.0.0.4	    Apurva Shukla    02 july 2018     UAT bugId-59890 & 59972 
+  1.0.0.5	    Apurva Shukla  	 14 aug 2018      consolidate invoice CR
+  1.0.0.6	    Apurva Shukla  	 20 aug 2018      consolidate invoice report
+  1.0.0.7	    Apurva Shukla  	 27 aug 2018      null showing in dueType dependent
+  1.0.0.8	    Apurva Shukla  	 03 sep 2018      consolidated invoice radio enable after successfully generation of batch
+  1.0.0.9	    Apurva Shukla  	 01 oct 2018      changes made for consolidated invoice
+  1.0.0.10	    Apurva Shukla  	 15 oct 2018      Changes for invoice CR cancel single invoice and some other changes
   1.0.0.11		Nishant Bansal	 31-Oct-2018	  Reconcilation Report
   1.0.0.12	    Apurva shukla    12-Nov-2018	  chages for convert radio into combo
   1.0.0.13	    Apurva shukla    16-Nov-2018	  check box in check on check issue
   1.0.0.14	    Apurva shukla    20-Nov-2018	  adding batch id in search string as for rashmi
   1.0.0.15	    Apurva shukla    03-DEC-2018	  changes made for add to batch cross record status going 
   1.0.0.16		Sunny Pathak	 09-Dec-2019	  changes made for invoice search string ass add to batch was not working
   1.0.0.17		Sunny Pathak	 19-Dec-2019	  Added selAllInv flag in addToBatch search string
   1.0.0.18		Sunny Pathak	 08-Jan-2020	  CR :- Batch Error Report
   1.0.0.19     Ravi Kumar       07-Sep-2020      changes releated to e invoicing
   1.0.0.20     Viplou Dhali     07-Jul-2022     Validation on DueType
   1.0.0.21		Priyanka Soni	 08-Nov-2022	 CR:- User Logout on EOD
   1.0.0.22		Hemant Kumar	 30-Apr-2025	 Bug 147435: System is allowing user to click on invoice search multiple times which is impacting the system performance
   1.0.0.23		Sanchi Agarwal	 07-Oct-2026	 Batch upload buttons disabled based on BATCH_UPLOAD_ENABLE_FLAG when in Print mode
 **********************************************************************************************************************************
-->*/
var app = angular.module('invoiceDetailsApp', []);
app.controller('invoiceDetailsCtrl', function($scope, $http,$filter, $window,$rootScope,$location) {
	$scope.changeFlag="N";
	$scope.searchedDueType=""; //1.0.0.20
	$scope.quotationList= null;
	$scope.printCheckboxEnableFlag = printCheckboxEnableFlag;
	$scope.batchUploadEnableFlag = batchUploadEnableFlag;

	 $scope.chargeTypeObject = mastersListObj.chargeTypeMaster;
	 
	 $scope.dueTypeObject = mastersListObj.dueTypeMaster; //1.0.0.5
	 
	 
	 $scope.schemeMasterObject = mastersListObj.schemeMaster;
	 
	 $scope.productDetailsMasterObject = mastersListObj.productDetails;// 1.0.0.9
	 
	 $scope.branchObject = mastersListObj.leaseMaster;
	// console.log($scope.productDetailsMasterObject);// 1.0.0.9
	 $scope.batchNumberObject = mastersListObj.batchNumberMaster;
	 var onloadFlag="N";
	 
	 
	 var invoiceType='COVERNOTE';// 1.0.0.5
	 var taxableFlg='';// 1.0.0.5
	 var responseFlag="Y";
	 isCheckCoverNoteRadio=true;
	 
	 var gerenationProgressFlag="N";
	  $scope.searchData={"schemeId":"","branch":"","status":"0"};
	 var serializedData =  {
					"actionId" : "1000003005",
					"pageNo" : "1"
			};	
	 $scope.showDetail = function(quotationId,quotationCode)		
	{
	           
	  			
	  			window.location.href ="baseFrmNavAction.do?quotationId="+quotationId+"&prospectNo="+quotationCode+"&prospectId="+quotationId+"&entityId=1000000005&application=QUOTATION";
	  			
				
	  }

		var startIndex=1;
		var endIndex=5;
		var windowSize=5;
		var currentIndex=1;
		var minPage=1;
		var totalpageNo="";
		var cancelInvoiceCoverNoteStr="";
		var startIndexCoverNote=1;
		var endIndexCoverNote=5;
		var windowSizeCoverNote=5;
		var currentIndexCoverNote=1;
		var minPageCoverNote=1;
		var totalpageNoCoverNote="";
		
		var cancelSingleInvoiceStr="";//1.0.0.10
		
		
		var screenId="";
		/*if(<%=request.getParameter("screenId")%>!=null)
		{
		  screenId="<%=request.getParameter("screenId")%>";
		}
		else
		{
		    <logic:notEmpty name="screenIdProspect">
		    screenId= "<bean:write name="screenIdProspect"/>";
		    </logic:notEmpty>
		}*/
		document.forms[0].minPageCoverNote.value="1";
		document.forms[0].maxPageCoverNote.value="5";
		document.forms[0].currentPageCoverNote.value="1";
		var diffenceMaxMinCoverNote =  endIndexCoverNote- startIndexCoverNote;	
		
		document.forms[0].minPage.value="1";
		document.forms[0].maxPage.value="5";
		document.forms[0].currentPage.value="1";
		var diffenceMaxMin =  endIndex- startIndex;	
		
	 	$scope.totalNoOfPages="";
	 	$scope.totalNoOfPagesCoverNote="";
	 	$scope.batchCompletedFlag="";
	 	
	 	$scope.invoiceList="";
	 	$scope.batchSummaryList="";
	 	
	 	var batchStr="";
	 	var coverNoteStr="";
	 	$scope.batchData=[];
	 	  $scope.batchData.batchId="";
	 	  $scope.batchId="";
	 	  $scope.batchData.BATCH_ID="";
	 	  $scope.isDisabled = true;
	 	 $scope.isGenerateDisabled=true;
	 	 $scope.isCancelDisabled=true;
	 	$scope.isaddToBatchDisabled=true;
	 	$scope.generateEInvoiceEnableFlag=true; //1.0.0.19
	 	$scope.generateEInvoiceProcessStatus=""; //1.0.0.19
	 	
	 	$scope.shownoRecordFoundDiv=false;
	 	$scope.shownoRecordFoundDiv1=false;
	 	$scope.validateFlag="Y";
	 	 // $scope.changeFlag=false;
	 	  //$scope.coverNoteList="123";
	 	  
	 	  $scope.searchData.customerId =!angular.isUndefined($scope.searchData.customerId)?$scope.searchData.customerId :"";
	 	  $scope.searchData.batchId =!angular.isUndefined($scope.searchData.batchId)?$scope.searchData.batchId :"";

	$scope.searchData.dmId =!angular.isUndefined($scope.searchData.dmId)?$scope.searchData.dmId :"";
	$scope.searchData.schemeId =!angular.isUndefined($scope.searchData.schemeId)?$scope.searchData.schemeId :"";
	$scope.searchData.fromDate =!angular.isUndefined($scope.searchData.fromDate)?$scope.searchData.fromDate :"";
	$scope.searchData.toDate =!angular.isUndefined($scope.searchData.toDate)?$scope.searchData.toDate :"";
	$scope.searchData.branch =!angular.isUndefined($scope.searchData.branch)?$scope.searchData.branch :"";
	$scope.searchData.chargeType =!angular.isUndefined($scope.searchData.chargeType)?$scope.searchData.chargeType :"";
	$scope.searchData.invoiceNumber =!angular.isUndefined($scope.searchData.invoiceNumber)?$scope.searchData.invoiceNumber :"";
	$scope.searchData.coverNoteNumber =!angular.isUndefined($scope.searchData.coverNoteNumber)?$scope.searchData.coverNoteNumber :"";
	$scope.searchData.batchNo =!angular.isUndefined($scope.searchData.batchNo)?$scope.searchData.batchNo :"";
	
	//$scope.batchId =!angular.isUndefined($scope.batchId)?$scope.batchId :"";
	
	$scope.invoiceList.INVOICE_X_COVER_NOTE_ID =!angular.isUndefined($scope.invoiceList.INVOICE_X_COVER_NOTE_ID)?$scope.invoiceList.INVOICE_X_COVER_NOTE_ID :"";
	$scope.batchData.BATCH_ID =!angular.isUndefined($scope.batchData.BATCH_ID)?$scope.batchData.BATCH_ID :"";
	$scope.searchData.batchCreatedBy =!angular.isUndefined($scope.searchData.batchCreatedBy)?$scope.searchData.batchCreatedBy :"0";
	
	
	
	//$scope.searchData.covernoteRadioConsolidateInvoice=!angular.isUndefined($scope.searchData.covernoteRadioConsolidateInvoice)?$scope.searchData.covernoteRadioConsolidateInvoice :"COVERNOTE";
	$scope.searchData.consolidatedInvoiceNo=!angular.isUndefined($scope.searchData.consolidatedInvoiceNo)?$scope.searchData.consolidatedInvoiceNo :"";
	
	$scope.searchData.consolidatedInvoiceNo_TEMP=!angular.isUndefined($scope.searchData.consolidatedInvoiceNo_TEMP)?$scope.searchData.consolidatedInvoiceNo_TEMP :"";// 1.0.0.7
	$scope.searchData.coverNoteNumber_TEMP =!angular.isUndefined($scope.searchData.coverNoteNumber_TEMP)?$scope.searchData.coverNoteNumber_TEMP :"";// 1.0.0.7
	
	//$scope.batchNO =!angular.isUndefined($scope.batchNO)?$scope.batchNO :"";
	$scope.searchData.hidBatchNo =!angular.isUndefined($scope.searchData.hidBatchNo)?$scope.searchData.hidBatchNo :"";
	// 1.0.0.5 start
	$scope.searchData.STATE =!angular.isUndefined($scope.searchData.STATE_TEMP)?$scope.searchData.STATE :"";
	
	$scope.searchData.hidInvoiceType =!angular.isUndefined($scope.searchData.hidInvoiceType)?$scope.searchData.hidInvoiceType :"COVERNOTE";
	
	
	$scope.searchData.CONS_INVOICE_GENERATED =!angular.isUndefined($scope.searchData.CONS_INVOICE_GENERATED)?$scope.searchData.CONS_INVOICE_GENERATED :"N";
	
	$scope.searchData.INVOICE_GENERATED =!angular.isUndefined($scope.searchData.INVOICE_GENERATED)?$scope.searchData.INVOICE_GENERATED :"";//1.0.0.12
	
	$scope.searchData.chargeType =!angular.isUndefined($scope.searchData.chargeType)?$scope.searchData.chargeType :"";// 1.0.0.7
	
	$scope.searchData.taxableFlag =!angular.isUndefined($scope.searchData.taxableFlag)?$scope.searchData.taxableFlag :"BOTH";
	$scope.searchData.allInvFlag =!angular.isUndefined($scope.searchData.allInvFlag)?$scope.searchData.allInvFlag :"N";
	// 1.0.0.9 start
	$scope.searchData.dueType =!angular.isUndefined($scope.searchData.dueType)?$scope.searchData.dueType :"0";
	$scope.searchData.productId =!angular.isUndefined($scope.searchData.productId)?$scope.searchData.productId :"";
	
	$scope.searchData.customerName=!angular.isUndefined($scope.searchData.customerName)?$scope.searchData.customerName :"";
	$scope.searchData.SEZZone=!angular.isUndefined($scope.searchData.SEZZone)?$scope.searchData.SEZZone :"";
	
	
	$scope.searchData.withheaderAndFooter=!angular.isUndefined($scope.searchData.withheaderAndFooter)?$scope.searchData.withheaderAndFooter :"Y";
	$scope.searchData.withcoverNote=!angular.isUndefined($scope.searchData.withcoverNote)?$scope.searchData.withcoverNote :"Y";
	
	$scope.searchData.printAndGenerate=!angular.isUndefined($scope.searchData.printAndGenerate)?$scope.searchData.printAndGenerate :"G";
	$scope.searchData.modeOfOperation =!angular.isUndefined($scope.searchData.modeOfOperation)?$scope.searchData.modeOfOperation :"G";
	// 1.0.0.9 end
	//console.log($scope.searchData.printAndGenerate);
	$scope.searchData.INVOICE_GENERATED="";// 1.0.0.12
	$scope.searchData.CONS_INVOICE_GENERATED="";
	// 1.0.0.5 end
	$scope.select=false;
	 $scope.validateSearchCriteria=function(){
	
		 tempData.errorMessages="Errors:";
	 
	 if($scope.searchData.fromDate=="" || $scope.searchData.fromDate==null){
		 
		 //var dateValidationStr="fromDate:TMV";
		// $scope.getValidation(dateValidationStr,"searchData");
		 tempData.errorMessages=tempData.errorMessages+"From Date must be specified.";
		// alert(tempData.errorMessages.replace("Errors:","").toUpperCase());
		 
		 
	 }if($scope.searchData.toDate=="" || $scope.searchData.toDate==null){
		 
		 tempData.errorMessages=tempData.errorMessages+"\nTo Date must be specified.";
	 }
	
	 	var formDate=new Date($scope.searchData.fromDate);
	 	 formDate= Date.parse(formDate);
	    
	    var toDate = new Date($scope.searchData.toDate);
	     toDate = Date.parse(toDate);
	  
	    if(formDate>toDate){
	    	//alert(("from date can't be greater than to date").toUpperCase());
	    	tempData.errorMessages=tempData.errorMessages+"\nfrom date can't be greater than to date.";
	    }
	    	var differenceDate=toDate-formDate;
 
	    		var finalDays=differenceDate/86400000;
	    		if(finalDays>Number(diffreenceDay)){//1.0.0.2
	    			tempData.errorMessages=tempData.errorMessages+"\nDifference between from date and to date can't be greater than "+diffreenceDay+" Days.";//1.0.0.4// 1.0.0.9	
	    			
	    		}
	    	// 1.0.0.5 start
	    		if(($scope.searchData.dueType!="" &&  $scope.searchData.dueType!="0" && $scope.searchData.dueType!=undefined)&& $scope.searchData.chargeType==""){
	    			//tempData.errorMessages=tempData.errorMessages+"\nPlease select charge name is case of due type.";
	    		}
                          // 1.0.0.9 start
	    		if($scope.searchData.fromDate!="" && $scope.searchData.fromDate!=null && $scope.searchData.fromDate!=undefined){
	    		//validate(document.getElementById("fromDate"),"DTF",'FROM DATE');// 1.0.0.14
	    		}
	    		if($scope.searchData.toDate!="" && $scope.searchData.toDate!=null && $scope.searchData.toDate!=undefined){
	    		//validate(document.getElementById("toDate"),"DTF",'TO DATE');// 1.0.0.14
	    		}
	    		// 1.0.0.9 end
	    		// 1.0.0.5 end
 
	 if(tempData.errorMessages!="Errors:"){
	 alert(tempData.errorMessages.replace("Errors:","").toUpperCase());
	 $scope.invoiceList="";
	 $scope.coverNoteList="";
	 $scope.showPagination=true;
	 $scope.showPagination1=true; 
	 document.getElementById("coverNoteList").style.display="none";
	 $scope.validateFlag="N";
	// return;
	 }
	 else{
		 $scope.validateFlag="Y";
	 }
	 };
	 	  
	//1.0.0.16 added
	 var glb_invoiceSearchStr="";
	 
	 $scope.getInvoiceListData = function(pageNoForInvoice,pageNoForCoverNote) 
	{
		 cancelSingleInvoiceStr="";// 1.0.0.10
		 cancelInvoiceCoverNoteStr="";
		 if($scope.validateFlag=="N"){
			 return;
			 
		 }
     // change flag code start
		 /*tempData.errorMessages="Errors:";
		 
		 if($scope.searchData.fromDate=="" || $scope.searchData.fromDate==null){
			 
			 //var dateValidationStr="fromDate:TMV";
			// $scope.getValidation(dateValidationStr,"searchData");
			 tempData.errorMessages=tempData.errorMessages+"From Date must be specified.";
			// alert(tempData.errorMessages.replace("Errors:","").toUpperCase());
			 
			 
		 }if($scope.searchData.toDate=="" || $scope.searchData.toDate==null){
			 
			 tempData.errorMessages=tempData.errorMessages+"\nTo Date must be specified.";
		 }
		
		 if(tempData.errorMessages!="Errors:"){
			 
			 alert(tempData.errorMessages.replace("Errors:","").toUpperCase());
			 return;
		 }
		 
		 
		 	var formDate=new Date($scope.searchData.fromDate);
		    var formDateyear = formDate.getFullYear();
		    var formDatemonth = formDate.getMonth();
		    var formDateday = formDate.getDate();
		    
		    
		    var toDate = new Date($scope.searchData.toDate);

		    var toDateyear = toDate.getFullYear();
		    var toDatemonth = toDate.getMonth();
		    var toDateday = toDate.getDate();

		    var finalYear = toDateyear - formDateyear;
		    var finalMonth = toDatemonth - formDatemonth;
		    var finalDay = toDateday - formDateday;

		 
		 if (finalYear > 0 || finalMonth > 3) {
			 
			 tempData.errorMessages = tempData.errorMessages+"\n"+"difference between form date and to date should be only three months";
		 }
		 
		if(finalMonth==3 && finalDay>0){
			 tempData.errorMessages = tempData.errorMessages+"\n"+"difference between form date and to date should be only three months";
			 
		 }
		 
		
		 if(tempData.errorMessages!="Errors:"){
		 alert(tempData.errorMessages.replace("Errors:","").toUpperCase());
		 $scope.invoiceList="";
		 $scope.coverNoteList="";
		 $scope.showPagination=true;
		 $scope.showPagination1=true; 
		 document.getElementById("coverNoteList").style.display="none";
		 return;
		 }*/
		if(invoiceSearchEditFlag=="Y"){
		if($scope.changeFlag=="Y"){
			
			var bool = confirm(("Do you want to add selected invoices to batch?").toUpperCase());
			
			if(bool)
		    {
				$scope.addToBatch();
		    }	    
			$scope.changeFlag="N";
			return;
			
		}
		 }
		var onLoadFlag="N";
		var invoiceSearchStr='';
	// change flag code end
		//$scope.batchId =!angular.isUndefined($scope.batchId)?$scope.batchId :"";
		// 1.0.0.5 start
		if(invoiceType==""|| invoiceType==undefined){
			invoiceType='COVERNOTE';
		}
		
		if(invoiceType=='COVERNOTE'){
	invoiceSearchStr=$scope.searchData.customerId+"~"+$scope.searchData.dmId+"~"+$scope.searchData.productId+"~"+$scope.searchData.fromDate+"~"+$scope.searchData.toDate+"~"+$scope.searchData.branch+"~"+$scope.searchData.chargeType+"~"+$scope.searchData.invoiceNumber+"~"+$scope.searchData.coverNoteNumber_TEMP+"~"+$scope.searchData.hidBatchNo+"~"+$scope.searchData.dueType+"~"+$scope.batchId+"~"+pageNoForInvoice+"~"+pageNoForCoverNote+"~"+$scope.searchData.STATE+"~"+$scope.searchData.INVOICE_GENERATED+"~"+$scope.searchData.INVOICE_GENERATED+"~"+$scope.searchData.customerName+"~"+$scope.searchData.SEZZone+"~"+$scope.searchData.printAndGenerate+"~"+"END_STRING_FLAG"; // 1.0.0.7// 1.0.0.9//1.0.0.10//1.0.0.14
		}
		
		if(invoiceType=='CONSOLIDATED INV'){
			invoiceSearchStr=$scope.searchData.customerId+"~"+$scope.searchData.dmId+"~"+$scope.searchData.productId+"~"+$scope.searchData.fromDate+"~"+$scope.searchData.toDate+"~"+$scope.searchData.branch+"~"+$scope.searchData.chargeType+"~"+$scope.searchData.invoiceNumber+"~"+$scope.searchData.coverNoteNumber_TEMP+"~"+$scope.searchData.hidBatchNo+"~"+$scope.searchData.dueType+"~"+$scope.batchId+"~"+pageNoForInvoice+"~"+pageNoForCoverNote+"~"+$scope.searchData.STATE+"~"+$scope.searchData.INVOICE_GENERATED+"~"+$scope.searchData.INVOICE_GENERATED+"~"+$scope.searchData.customerName+"~"+$scope.searchData.SEZZone+"~"+$scope.searchData.printAndGenerate+"~"+"END_STRING_FLAG";// 1.0.0.7// 1.0.0.9//1.0.0.10//1.0.0.14
			
		}
		$("#loading").show();//1.0.0.22
		// 1.0.0.5 end
	//console.log(invoiceSearchStr);
	$.ajax({
			  		 url: "invoiceListData.sprg?isAjaxCalling=Y", //1.0.0.21 added  isAjaxCalling=Y
			  		 type: "POST", 
			  		 data:{
			  			actionId: screenId,
			  			customerCode: $scope.searchData.customerId,
			  			dmCode: $scope.searchData.dmId,
			  			schemeId: $scope.searchData.schemeId,
			  			fromDate: $scope.searchData.fromDate,
			  			toDate: $scope.searchData.toDate,
			  			branch: $scope.searchData.branch,
			  			chargeType: $scope.searchData.chargeType,
			  			invoiceNumber: $scope.searchData.invoiceNumber,
			  			coverNoteNumber: $scope.searchData.coverNoteNumber,
			  			batchNo: $scope.searchData.batchNo,
			  			invoiceType:invoiceType,// 1.0.0.5
			  			taxableFlg:$scope.searchData.taxableFlag,// 1.0.0.5
			  			invoiceSearchStr: invoiceSearchStr,
			  			onLoadFlag:onLoadFlag,
			  			  },
			  			
			  			success: function(response) {
			  				$("#loading").hide();//1.0.0.22
			  			if(response!=null && response!="") 
			  					{  //alert(response);
			  					 var jsonStr = JSON.parse(response);
			  					//if(jsonStr.Status=="SessionExpired") //1.0.0.21
								if (jsonStr.Status && jsonStr.Status === "SessionExpired")
		  						{
		  							window.location.href = 'llm/ReLogin.jsp?unknownError=Your session has been killed. Please login again.';
		  							return;
		  						}
			  					 if(jsonStr != null && jsonStr.PC_OUT_STATUS=="S"){ // added null check
			  			  $scope.searchedDueType=$scope.searchData.dueType; //1.0.0.20					 
			  			  $scope.paginationDetailsForInvoice(jsonStr.totalNoOfPages,pageNoForInvoice);// for pagination
			  			 $scope.paginationDetailsForCoverNote(jsonStr.totalNoOfPagesForCoverNote,pageNoForCoverNote);
			  			   totalpageNo=jsonStr.totalNoOfPages;
			  			   
			  			   if(invoiceSearchEditFlag=="Y"){
			  			   
			  					  $scope.invoiceList = jsonStr.INVOICE_LIST;
			  					invoiceSearchEditFlag="N";
			  			   }
			  			   else if(coverNoteSearchEditFlag=="Y"){
			  					  $scope.coverNoteList = jsonStr.CONER_NOTE_LIST;
			  					coverNoteSearchEditFlag="N";
			  					  }
			  			   else if(invoiceSearchEditFlag=="N" && coverNoteSearchEditFlag=="N"){
			  						  
			  						$scope.invoiceList = jsonStr.INVOICE_LIST;
			  						$scope.coverNoteList = jsonStr.CONER_NOTE_LIST;
			  					  }
			  			   			  			   
			  			 for(var i=0;i<$scope.invoiceList.length;i++)
	  					 {
	  					   if($scope.invoiceList[i].INVOICE_STATUS=="GENERATED")
	  					   {
	  						 $scope.invoiceList[i].INVOICE_STATUS_VAR="GEN";
	  					    }
	  					    if($scope.invoiceList[i].INVOICE_STATUS=="NOT GENERATED")
	  					   {
	  					    	$scope.invoiceList[i].INVOICE_STATUS_VAR="NGN";
	  					    }
	  					    if($scope.invoiceList[i].INVOICE_STATUS=="CANCELLED")
	  					   {
	  					    	$scope.invoiceList[i].INVOICE_STATUS_VAR="CAN";
	  					    }
	  					   //1.0.0.19 start
	  					  if($scope.invoiceList[i].EINV_IRN!= null && $scope.invoiceList[i].EINV_IRN!=undefined)
	  					   {
	  						  var tempEinvIrn=$scope.invoiceList[i].EINV_IRN;
	  						 $scope.invoiceList[i].EINV_IRN_VAR=tempEinvIrn.substring(0, 4)+"...";
	  					    }
	  					  
	  					if($scope.invoiceList[i].EINV_ACK_NO!= null && $scope.invoiceList[i].EINV_ACK_NO!=undefined)
	  					   {
	  						  var tempEinvAckNo=$scope.invoiceList[i].EINV_ACK_NO;
	  						 $scope.invoiceList[i].EINV_ACK_NO_VAR=tempEinvAckNo.substring(0, 4)+"...";
	  					    }
	  					
	  					if($scope.invoiceList[i].EINV_ACK_DATE!= null && $scope.invoiceList[i].EINV_ACK_DATE!=undefined)
	  					   {
	  						  var tempInvAckDate=$scope.invoiceList[i].EINV_ACK_DATE;
	  						 $scope.invoiceList[i].EINV_ACK_DATE_VAR=tempInvAckDate.substring(0, 4)+"...";
	  					    }
	  					
	  					if($scope.invoiceList[i].EINV_CURR_STATUS!= null && $scope.invoiceList[i].EINV_CURR_STATUS!=undefined)
	  					   {
	  						  var tempEInvStatus=$scope.invoiceList[i].EINV_CURR_STATUS;
	  						 $scope.invoiceList[i].EINV_CURR_STATUS_VAR=tempEInvStatus.substring(0, 4)+"...";
	  					    }// 1.0.0.19 end
	  					  if($scope.invoiceList[i].CHARGE_TYPE!= null && $scope.invoiceList[i].CHARGE_TYPE!=undefined)
	  					   {
	  						  var tempChargeType=$scope.invoiceList[i].CHARGE_TYPE;
	  						 $scope.invoiceList[i].CHARGE_TYPE_VAR=tempChargeType.substring(0, 4)+"...";
	  					    }
	  					  
	  					if($scope.invoiceList[i].CHARGE_NAME!= null && $scope.invoiceList[i].CHARGE_NAME!=undefined)
	  					   {
	  						  var tempChargeName=$scope.invoiceList[i].CHARGE_NAME;
	  						 $scope.invoiceList[i].CHARGE_NAME_VAR=tempChargeName.substring(0, 4)+"...";
	  					    }
	  					
	  					if($scope.invoiceList[i].CUSTOMER_NAME!= null && $scope.invoiceList[i].CUSTOMER_NAME!=undefined)
	  					   {
	  						  var tempCustomerName=$scope.invoiceList[i].CUSTOMER_NAME;
	  						 $scope.invoiceList[i].CUSTOMER_NAME_VAR=tempCustomerName.substring(0, 4)+"...";
	  					    }
	  					  	  					  
	  					 }
			  					  			   
			  			   	$scope.showPagination=false;
			  			   	$scope.showPagination1=false;
			  					$('#SearchResultDivId *').prop('disabled',false);
			  					document.getElementById("coverNoteList").style.display='block';
			  					//document.getElementById("existingTable1").style.display='block';
			  					$scope.isaddToBatchDisabled=false;
			  					document.getElementById("generate").disabled=true;
		  						document.getElementById("cancelBatch").disabled=true;
		  						//document.getElementById("addToBatch").disabled=true;
		  						document.getElementById("selectAll").checked=false;
		  						$scope.shownoRecordFoundDiv=false;
		  						$scope.shownoRecordFoundDiv1=false;
		  						document.getElementById("pageneation").style.display='block';
								document.getElementById("pageneation1").style.display='block';
			  					
								if($scope.invoiceList=="" || $scope.invoiceList==undefined){//1.0.0.4
									
									$scope.shownoRecordFoundDiv=true;
									document.getElementById("coverNoteList").style.display='none';
									//document.getElementById("existingTable1").style.display='none';
									document.getElementById("pageneation").style.display='none';
									document.getElementById("pageneation1").style.display='none';
									document.getElementById("batchSummary").style.display='none';
										}
								// 1.0.0.5 start
								if($scope.batchId!="" && $scope.batchId!=undefined){
									document.getElementById("batchSummary").style.display='block';
									document.getElementById("generate").disabled=false;
			  						//document.getElementById("cancelBatch").disabled=false; // 1.0.0.23 commented - handled by setBatchButtonsState
			  						$scope.setBatchButtonsState(true); // 1.0.0.23
								}
								// 1.0.0.5 end
								
								if($scope.coverNoteList==""){
									$scope.shownoRecordFoundDiv1=true;
									}
								if(onloadFlag=="Y"){
			  						//document.getElementById("addToBatch").disabled=false; // 1.0.0.23 commented - handled by setBatchButtonsState
			  						$scope.setBatchButtonsState(true); // 1.0.0.23
			  					}onloadFlag="Y";
			  					if($scope.invoiceList!=""){
			  					$scope.selectAllCheckBoxSelected();
			  					}
								$scope.currentBatchGeneration();
			  					
								// //1.0.0.19 start
		  						 if(!angular.isUndefined(jsonStr.ENABLE_E_INVOICE_GENERATE_BTN) && jsonStr.ENABLE_E_INVOICE_GENERATE_BTN=="Y")
		  							 {
		  							   $scope.generateEInvoiceEnableFlag=false;
		  							 document.getElementById("generateEInvoice").disabled=false;
		  							 }  
		  						 else
		  							 {
		  							    $scope.generateEInvoiceEnableFlag=true;
		  							     document.getElementById("generateEInvoice").disabled=true;
		  							 }
		  						$scope.generateEInvoiceProcessStatus="";
		  						$scope.generateEInvoiceProcessStatus=!angular.isUndefined(jsonStr.E_INVOICE_PROGRESS_STATUS)?jsonStr.E_INVOICE_PROGRESS_STATUS:"";
		  						////1.0.0.19 end
		  			
								
								
			  					
			  					}
			  					 
			  					else{
			  						
			  						$scope.shownoRecordFoundDiv=true;
			  						
			  						
			  					 }
			  					$scope.$apply();
			  					}
			  			
			  			}});  
						
	
					};
					
					// 1.0.0.5 start
					$scope.setDataForInvoiceTypegererated= function(val){
						
						if(val=="COVERNOTE"){//1.0.0.10 start
							if(document.getElementById("invoiceNoGenerated").checked==false){
								//document.getElementById("consolidatedInvoiceNoGenerated").disabled=true;
								//document.getElementById("consolidatedInvoiceNoGenerated").checked=false;
								$scope.searchData.INVOICE_GENERATED="N";
								//$scope.searchData.CONS_INVOICE_GENERATED="N";
								
							}
							else{
								if($scope.searchData.hidInvoiceType!="COVERNOTE"){
								//document.getElementById("consolidatedInvoiceNoGenerated").disabled=false;
								//document.getElementById("invoiceNoGenerated").checked=true;
								}
								$scope.searchData.INVOICE_GENERATED="Y";
								
							}
						}else{
							//if(val=="WITHCONSOLIDATE"){
								
								
								if(document.getElementById("invoiceNoGenerated").checked== false){
									//$scope.searchData.CONS_INVOICE_GENERATED="N";
									$scope.searchData.INVOICE_GENERATED="N";
								}
								else{
									//document.getElementById("consolidatedInvoiceNoGenerated").disabled=false;
									//document.getElementById("consolidatedInvoiceNoGenerated").checked=false
									//$scope.searchData.CONS_INVOICE_GENERATED="Y";
									$scope.searchData.INVOICE_GENERATED="Y";
								}
								// 1.0.0.10 end
								
								
							}
							
						//}						
					};
					
					$scope.enableDueType= function(val){
						  $scope.invoiceList="";
						  $scope.coverNoteList="";
						  $scope.currentCoverNoteList="";
						  document.getElementById("pageneation").style.display='none';
							document.getElementById("coverNoteList").style.display='none';
							 $scope.searchData.dueType="0";// 1.0.0.10
							 $scope.searchData.chargeType="";// 1.0.0.10
							 $scope.resetSearchCriteria();
							 // 1.0.0.12 start
							 if($scope.searchData.modeOfOperation=="P" || $scope.searchData.printAndGenerate=="P"){
									$scope.searchData.INVOICE_GENERATED="Y";
									}
									else{
										$scope.searchData.INVOICE_GENERATED="";
									}
							 // 1.0.0.12 end
						if(val=='CONSOLIDATED INV'){
							invoiceType=val;
							$scope.searchData.hidInvoiceType=val;
							isDueTypeDisabled="false";
							document.getElementById("dueType").disabled=false;
							document.getElementById("consolidatedInvoiceNo").disabled=false;
							
							document.getElementById("stateDiv").disabled=false;
							
							document.getElementById("coverNoteNumber").disabled=false;// 1.0.0.9
							
							//document.getElementById("consolidatedInvoiceNoGenerated").disabled=false;// 1.0.0.10
							document.getElementById("consolidateInvoiceDiv").disabled=false;
							document.getElementById("coverNoteDiv").disabled=false;// 1.0.0.9
							document.getElementById("taxableFlag").disabled=false;
							//document.getElementById("nonTaxable").disabled=false;
							$scope.searchData.coverNoteNumber_TEMP="";
							$scope.searchData.coverNoteNumber="";
							document.getElementById("taxableFlagDiv").style.display="block";// 1.0.0.9
							//Added by vindhyachal
							invoiceType=val;
							$scope.selectAllCheckBoxSelected();
							$scope.getInvoiceListDataOnLoad("1","1");
							
							
						}else{
                                                         // 1.0.0.9 start
							invoiceType=val;
							$scope.searchData.hidInvoiceType=val;
							document.getElementById("dueType").disabled=false; // 1.0.0.7
							document.getElementById("consolidatedInvoiceNo").disabled=false;
							//$scope.searchData.dueType='';
							$scope.searchData.consolidatedInvoiceNo='';
							document.getElementById("taxableFlag").disabled=false;
							document.getElementById("taxableFlagDiv").style.display="block";
							document.getElementById("taxableFlag").value="BOTH";
							
							document.getElementById("stateDiv").disabled=false;
							// 1.0.0.9 end
							$scope.searchData.STATE="";
							$scope.searchData.STATE_TEMP="";
							 // 1.0.0.9 start
							$scope.searchData.dueType="0";
							//$scope.searchData.chargeType="";
							//$scope.chargeTypeObject = mastersListObj.chargeTypeMaster;
							// 1.0.0.9 end
							//document.getElementById("nonTaxable").disabled=true;
							taxableFlg="";
							//document.getElementById("nonTaxable").checked=false;
							//document.getElementById("taxable").checked=false;
							document.getElementById("coverNoteNumber").disabled=false;
							//document.getElementById("consolidatedInvoiceNoGenerated").disabled=true;// 1.0.0.10
							//document.getElementById("consolidatedInvoiceNoGenerated").checked=false;//1.0.0.10
							
							document.getElementById("consolidateInvoiceDiv").disabled=true;
							document.getElementById("coverNoteDiv").disabled=false;
							
							$scope.searchData.consolidatedInvoiceNo_TEMP="";
							$scope.searchData.consolidatedInvoiceNo="";
							
						}
						
						
					};
					
					
					/*$scope.setDataForFlag= function(val){
						if(val=='TAXABLE'){
							if(document.getElementById("taxable").checked==true && document.getElementById("nonTaxable").checked!=true){
								taxableFlg='TAXABLE';
							}
							if(document.getElementById("nonTaxable").checked==true && document.getElementById("taxable").checked!=true){
								taxableFlg='NONTAXABLE';
							}
						
							
							 if(document.getElementById("taxable").checked==true && document.getElementById("nonTaxable").checked==true){
									
				            	 taxableFlg='BOTH'; 
				            	  
											}
							
					}
						else if(val=='NONTAXABLE'){
                   if(document.getElementById("taxable").checked==true && document.getElementById("nonTaxable").checked!=true){
					         
                	   taxableFlg='TAXABLE';
							
                   }
                   
                   if(document.getElementById("taxable").checked!=true && document.getElementById("nonTaxable").checked==true){
				         
                	   taxableFlg='NONTAXABLE';
							
                   }
                   if(document.getElementById("taxable").checked==true && document.getElementById("nonTaxable").checked==true){
						
                  	 taxableFlg='BOTH'; 
                  	  
      							}
                   
							
						}
						
					
						
					};*/
					
					
					$scope.setTaxableDropDown= function(val){
						if(val=="1200000048" || val=="1200000105"){
							$scope.searchData.taxableFlag="TAXABLE";
							document.getElementById("taxableFlag").disabled=true;
							
						}else{
							$scope.searchData.taxableFlag="BOTH";
							document.getElementById("taxableFlag").disabled=false;
						}
						
						
					};
					
					
					// 1.0.0.5 end
					// 1.0.0.7 start
					$scope.setChargeName= function(){
						$scope.searchData.chargeType="";
						
						
						
						};
					// 1.0.0.7 end
					 $scope.getInvoiceListDataOnLoad = function(pageNoForInvoice,pageNoForCoverNote) 
						{
						 cancelSingleInvoiceStr="";//1.0.0.10
						 cancelInvoiceCoverNoteStr="";
							if(invoiceSearchEditFlag=="Y"){
							if($scope.changeFlag=="Y"){
								
								var bool = confirm(("Do you want to add selected invoices to batch?").toUpperCase());
								
								if(bool)
							    {
									$scope.addToBatch();
							    }	    
								$scope.changeFlag="N";
								return;
								
							}
							 }
						// change flag code end
							
							 var onLoadFlag="Y";
							//$scope.batchId =!angular.isUndefined($scope.batchId)?$scope.batchId :"";
							//$scope.batchInvoiceType =!angular.isUndefined($scope.batchInvoiceType)?$scope.batchInvoiceType :"";
							
						var invoiceSearchStr=""+"~"+"~"+"~"+"~"+"~"+"~"+"~"+"~"+"~"+"~"+"~"+"~"+"1"+"~"+"1"+"~"+""+"~"+""+"~"+""+"~"+""+"~"+""+"~"+$scope.searchData.modeOfOperation+"~"+"END_STRING_FLAG";// 1.0.0.5// 1.0.0.9
						$("#loading").show();//1.0.0.22
						//console.log(invoiceSearchStr);
						$.ajax({
								  		 url: "invoiceListData.sprg?isAjaxCalling=Y", //1.0.0.21 added  isAjaxCalling=Y
								  		 type: "POST", 
								  		 data:{
								  			invoiceSearchStr: invoiceSearchStr,
								  			invoiceType:invoiceType,// 1.0.0.5
								  			taxableFlg:taxableFlg,//1.0.0.5
								  			onLoadFlag:onLoadFlag,
								  			  },
								  			
								  			success: function(response) {
								  				$("#loading").hide();//1.0.0.22
								  			if(response!=null && response!="") 
								  					{  //alert(response);
								  					 var jsonStr = JSON.parse(response);
								  					//if(jsonStr.Status=="SessionExpired") //1.0.0.21
													if (jsonStr.Status && jsonStr.Status === "SessionExpired")
							  						{
							  							window.location.href = 'llm/ReLogin.jsp?unknownError=Your session has been killed. Please login again.';
							  							return;
							  						}
								  					// console.log(jsonStr);
								  					 if(jsonStr.PC_OUT_STATUS=="S"){
								  					// alert(jsonStr.QUOTATION_LIST)
								  			  $scope.paginationDetailsForInvoice(jsonStr.totalNoOfPages,pageNoForInvoice);// for pagination
								  			 $scope.paginationDetailsForCoverNote(jsonStr.totalNoOfPagesForCoverNote,pageNoForCoverNote);
								  			  totalpageNo=jsonStr.totalNoOfPages;
								  			 $scope.batchId=jsonStr.BATCH_ID!=null?jsonStr.BATCH_ID:"";
											$scope.searchData.dueType=jsonStr.DUE_TYPE; //1.0.0.20
								  			$scope.searchData.hidInvoiceType=jsonStr.INVOICE_TYPE;
								  			invoiceType=jsonStr.INVOICE_TYPE;
								  			$scope.searchData.modeOfOperation=jsonStr.MODE_OF_OPERATION;
								  			$scope.searchData.printAndGenerate=jsonStr.MODE_OF_OPERATION;
								  			
								  			if($scope.searchData.modeOfOperation=="P")
								  			{
								  				$scope.searchData.INVOICE_GENERATED="Y";
								  			}
								  			//$scope.batchId =!angular.isUndefined($scope.batchId)?$scope.batchId :"";
								  			   if(invoiceSearchEditFlag=="Y"){
								  			   
								  					  $scope.invoiceList = jsonStr.INVOICE_LIST;
								  					invoiceSearchEditFlag="N";
								  			   }
								  			   else if(coverNoteSearchEditFlag=="Y"){
								  					  $scope.coverNoteList = jsonStr.CONER_NOTE_LIST;
								  					coverNoteSearchEditFlag="N";
								  					  }
								  			   else if(invoiceSearchEditFlag=="N" && coverNoteSearchEditFlag=="N"){
								  						  
								  						$scope.invoiceList = jsonStr.INVOICE_LIST;
								  						$scope.coverNoteList = jsonStr.CONER_NOTE_LIST;
								  					  }
								  			   
									  			for(var i=0;i<$scope.invoiceList.length;i++)
							  					 {
							  					   if($scope.invoiceList[i].INVOICE_STATUS=="GENERATED")
							  					   {
							  						 $scope.invoiceList[i].INVOICE_STATUS_VAR="GEN";
							  					    }
							  					    if($scope.invoiceList[i].INVOICE_STATUS=="NOT GENERATED")
							  					   {
							  					    	$scope.invoiceList[i].INVOICE_STATUS_VAR="NGN";
							  					    }
							  					    if($scope.invoiceList[i].INVOICE_STATUS=="CANCELLED")
							  					   {
							  					    	$scope.invoiceList[i].INVOICE_STATUS_VAR="CAN";
							  					    }
							  					    
							  					    // 1.0.0.19 start
							  					  if($scope.invoiceList[i].EINV_IRN!= null && $scope.invoiceList[i].EINV_IRN!=undefined)
							  					   {
							  						  var tempEinvIrn=$scope.invoiceList[i].EINV_IRN;
							  						 $scope.invoiceList[i].EINV_IRN_VAR=tempEinvIrn.substring(0, 4)+"...";
							  					    }
							  					  
							  					if($scope.invoiceList[i].EINV_ACK_NO!= null && $scope.invoiceList[i].EINV_ACK_NO!=undefined)
							  					   {
							  						  var tempEinvAckNo=$scope.invoiceList[i].EINV_ACK_NO;
							  						 $scope.invoiceList[i].EINV_ACK_NO_VAR=tempEinvAckNo.substring(0, 4)+"...";
							  					    }
							  					
							  					if($scope.invoiceList[i].EINV_ACK_DATE!= null && $scope.invoiceList[i].EINV_ACK_DATE!=undefined)
							  					   {
							  						  var tempInvAckDate=$scope.invoiceList[i].EINV_ACK_DATE;
							  						 $scope.invoiceList[i].EINV_ACK_DATE_VAR=tempInvAckDate.substring(0, 4)+"...";
							  					    }
							  					
							  					if($scope.invoiceList[i].EINV_CURR_STATUS!= null && $scope.invoiceList[i].EINV_CURR_STATUS!=undefined)
							  					   {
							  						  var tempEInvStatus=$scope.invoiceList[i].EINV_CURR_STATUS;
							  						 $scope.invoiceList[i].EINV_CURR_STATUS_VAR=tempEInvStatus.substring(0, 4)+"...";
							  					    }
							  					// 1.0.0.19 end
							  					if($scope.invoiceList[i].CHARGE_TYPE!= null && $scope.invoiceList[i].CHARGE_TYPE!=undefined)
							  					   {
							  						  var tempChargeType=$scope.invoiceList[i].CHARGE_TYPE;
							  						 $scope.invoiceList[i].CHARGE_TYPE_VAR=tempChargeType.substring(0, 4)+"...";
							  					    }
							  					  
							  					if($scope.invoiceList[i].CHARGE_NAME!= null && $scope.invoiceList[i].CHARGE_NAME!=undefined)
							  					   {
							  						  var tempChargeName=$scope.invoiceList[i].CHARGE_NAME;
							  						 $scope.invoiceList[i].CHARGE_NAME_VAR=tempChargeName.substring(0, 4)+"...";
							  					    }
							  					
							  					if($scope.invoiceList[i].CUSTOMER_NAME!= null && $scope.invoiceList[i].CUSTOMER_NAME!=undefined)
							  					   {
							  						  var tempCustomerName=$scope.invoiceList[i].CUSTOMER_NAME;
							  						 $scope.invoiceList[i].CUSTOMER_NAME_VAR=tempCustomerName.substring(0, 4)+"...";
							  					    }
							  					   
							  					 }
									  		
								  			   	$scope.showPagination=false;
								  			   	$scope.showPagination1=false;
								  					$('#SearchResultDivId *').prop('disabled',false);
								  					document.getElementById("coverNoteList").style.display='block';
								  					//document.getElementById("existingTable1").style.display='block';
								  					$scope.isaddToBatchDisabled=false;
								  					document.getElementById("generate").disabled=true;
							  						document.getElementById("cancelBatch").disabled=true;
							  						//document.getElementById("addToBatch").disabled=true;
							  						document.getElementById("selectAll").checked=false;
							  						$scope.shownoRecordFoundDiv=false;
							  						$scope.shownoRecordFoundDiv1=false;
							  						document.getElementById("pageneation").style.display='block';
													document.getElementById("pageneation1").style.display='block';
								  					
													if($scope.invoiceList=="" ||  $scope.invoiceList==undefined){//1.0.0.4
														
														$scope.shownoRecordFoundDiv=true;
														document.getElementById("coverNoteList").style.display='none';
														//document.getElementById("existingTable1").style.display='none';
														document.getElementById("pageneation").style.display='none';
														document.getElementById("pageneation1").style.display='none';
														document.getElementById("batchSummary").style.display='none';
														document.getElementById("selectAll").disabled=true;//1.0.0.4
															}
													
													if($scope.coverNoteList==""){
														$scope.shownoRecordFoundDiv1=true;
														document.getElementById("pageneation1").style.display='none';
														
													}
													if($scope.invoiceList!=""){
														document.getElementById("generate").disabled=false;
								  						//document.getElementById("cancelBatch").disabled=false; // 1.0.0.23 commented - handled by setBatchButtonsState
								  						$scope.setBatchButtonsState(true); // 1.0.0.23
													}
													//1.0.0.4 start
													if($scope.invoiceList=="" || $scope.invoiceList== undefined){
														document.getElementById("generate").disabled=true;
								  						//document.getElementById("cancelBatch").disabled=true; // 1.0.0.23 commented - handled by setBatchButtonsState
								  						$scope.setBatchButtonsState(false); // 1.0.0.23
													}
													// 1.0.0.4 end
													$scope.getBatchSummay($scope.batchId);
													
													if($scope.batchNO!=""){
													document.getElementById("batchSummary").style.display='block';
													//$scope.isaddToBatchDisabled=false; // 1.0.0.23 commented - handled by setBatchButtonsState
													$scope.setBatchButtonsState(true); // 1.0.0.23
								  					onloadFlag="Y";
							  						
													}
													// 1.0.0.4 start
													if($scope.batchId=="" || $scope.batchId==undefined){
														document.getElementById("batchSummary").style.display='none';
													}
													//1.0.0.4 end
													$scope.currentBatchGeneration();
													
													if($scope.batchId!="" && $scope.batchId!=undefined){
														document.getElementById("batchSummary").style.display='block';
														document.getElementById("consolidatedInv").disabled=true;
														document.getElementById("covernoteRadio").disabled=true;
														document.getElementById("printRadio").disabled=true;
														document.getElementById("generateRadio").disabled=true;
													}
													$scope.selectAllCheckBoxSelected();
																										
													
								  					 }
								  					 else{
								  						
								  						
								  						$scope.shownoRecordFoundDiv=true;
								  						
								  					 }
								  					$scope.$apply();
								  					}
								  			
								  			}});  
											
												
										};
					
					
										 $scope.resetSearchCriteria=function()
										 {
										 		 
										 		 $scope.searchData.customerId="";
										 		 $scope.searchData.customerName="";
										 		 $scope.searchData.dmId="";
										 		 $scope.searchData.branch="";
										 		 $scope.searchData.productId="";
										 		 $scope.searchData.invoiceNumber="";
										 		 $scope.searchData.fromDate="";
										 		 $scope.searchData.toDate="";
										 		 $scope.searchData.batchCreatedBy="0";
										 		 $scope.searchData.hidBatchNo="";
										 		 $scope.searchData.batchNo="";
										 		 $scope.searchData.coverNoteNumber="";
										 		 $scope.searchData.coverNoteNumber_TEMP="";
										 		 $scope.searchData.STATE="";
										 		 $scope.searchData.STATE_TEMP="";
										 		 $scope.searchData.SEZZone="";
										 		 $scope.searchData.dueType="0";
										 		 $scope.searchData.chargeType="";
										 		 $scope.searchData.taxableFlag="BOTH";
										 		 $scope.searchData.INVOICE_GENERATED="";// 1.0.0.12
										 		$scope.generateEInvoiceEnableFlag=true; // 1.0.0.19
										 		document.getElementById("generateEInvoice").disabled=true;
										 		$scope.generateEInvoiceProcessStatus=""; 
										 		 
										 	 };
										
										
										
					
										$scope.clickModeOfOperation = function(modeOfOperation){
											$scope.invoiceList="";
											$scope.coverNoteList="";
											$scope.searchData.printAndGenerate=modeOfOperation;
											$scope.searchData.modeOfOperation=modeOfOperation;
											$scope.currentCoverNoteList="";
											document.getElementById("pageneation").style.display='none';
											document.getElementById("coverNoteList").style.display='none';
											$scope.resetSearchCriteria();
											if(modeOfOperation=="P"){
											$scope.searchData.INVOICE_GENERATED="Y";
											}
											else{
												$scope.searchData.INVOICE_GENERATED="";// 1.0.0.12
											}
										}
					


					$scope.batchNoChooser = function(KeyName,obj,rowId)
					{
					 var ParamValue = "";
					 if($scope.searchData.fromDate=="" || $scope.searchData.toDate==""){
						 alert(("from date and to date can't be blank").toUpperCase());// 1.0.0.9
						 return;
						 
					 }
					 var formDateforBatchChooser=$scope.searchData.fromDate;
					 var toDateforBatchChooser=$scope.searchData.toDate;
					 var batchType=$scope.searchData.hidInvoiceType;
					 var modeOfOperation=$scope.searchData.modeOfOperation;
					  window.open("quotationMaster.sprg?KeyName="+KeyName+"&ParamValue="+ParamValue+"&label=batchNoChooser&moduleType=LMS&dependent=invoiceDtlId&objName="+obj+"&formDateforBatchChooser="+formDateforBatchChooser+"&toDateforBatchChooser="+toDateforBatchChooser+"&rowId="+rowId+"&batchType="+batchType+"&modeOfOperation="+modeOfOperation,"popDealer","width=500, height=490,top=100,left=100, location=no, maximize=yes, menubar=no, status=no, toolbar=no, scrollbars=no, resizable=no","modal=yes" );

					};
					
					
					
					// 1.0.0.5 start
					$scope.stateChooser= function(key)
					{
						window.open("populateMaster.do?qString="+key+"&label=ST&dependant=STATE&rowNum=1&moduleType=quotation&objectNmae=searchData&controllerNameId=invoiceDtlId","popState","width=500, height=490,top=100,left=100, location=no, maximize=yes, menubar=no, status=no, toolbar=no, scrollbars=no, resizable=no","modal=yes" );
					};
					
					
					
					$scope.coverNoteNumberChooser= function(key)
					{
					
						 window.open("quotationMaster.sprg?KeyName="+key+"&ParamValue"+""+"&label=COVERNOTE&moduleType=LMS&dependent=invoiceDtlId&objName=coverNote","popDealer","width=500, height=490,top=100,left=100, location=no, maximize=yes, menubar=no, status=no, toolbar=no, scrollbars=no, resizable=no","modal=yes" );
					
					};
					
					
					$scope.consolidateInvoiceChooser= function(key)
					{
					
						 window.open("quotationMaster.sprg?KeyName="+key+"&ParamValue"+""+"&label=CONSOLIDATEINVOICE&moduleType=LMS&dependent=invoiceDtlId&objName=consolidateInvoiceLov","popDealer","width=500, height=490,top=100,left=100, location=no, maximize=yes, menubar=no, status=no, toolbar=no, scrollbars=no, resizable=no","modal=yes" );
					
					};
					
					
					
					// 1.0.0.5 end
			
					var selectAllCheckBoxFlag= false;
					$scope.selectAllCheckBoxFlg="N";
					$scope.selectAllCheckBoxSelected= function(){
						selectAllCheckBoxFlag= false;
						if($scope.invoiceList!="")
						{
						for(var i=0;i<$scope.invoiceList.length;i++)
						{
							if($scope.invoiceList[i].INVOICE_SELECTED_FLG=="A")
							{
								selectAllCheckBoxFlag=true;
								
	                        }
							else if($scope.invoiceList[i].INVOICE_SELECTED_FLG=="D")
							{
								
	                        }
							else{
								selectAllCheckBoxFlag=false;
								break;
							}
						}
						}
						if(selectAllCheckBoxFlag){
							//$scope.selectAllCheckBoxFlg='Y';
							document.getElementById("selectAll").checked=true;
						}
						}
					
					$scope.selectAllCheckBoxSelected();
					
		//1.0.0.18 Start			
		$scope.generateErrorReport = function(){
			var fromDate = (!angular.isUndefined($scope.searchData.fromDate) && $scope.searchData.fromDate!=null)?$scope.searchData.fromDate:"";
			var toDate = (!angular.isUndefined($scope.searchData.toDate) && $scope.searchData.toDate!=null)?$scope.searchData.toDate:"";
			if( fromDate == "" || toDate == ""){
				alert("FROMDATE AND TODATE MUST BE SPECIFIED");
			}
			else{
				window.open("generateErrorReport.sprg?fromDate="+fromDate+"&toDate="+toDate);
			}
		};				
		//1.0.0.18 End			
				
		// Batch upload button state - enabled only when print is enabled // 1.0.0.23
		$scope.setBatchButtonsState = function(enableButtons) {
			var isPrintMode = ($scope.searchData.modeOfOperation == 'P');
			var disabled = !enableButtons || (isPrintMode && batchUploadEnableFlag != 'Y');
			$scope.isaddToBatchDisabled = disabled;
			document.getElementById("addToBatch").disabled = disabled;
			document.getElementById("cancelBatch").disabled = disabled;
		};
		
		$scope.addToBatch= function(){
		//1.0.0.20 Start
		tempData.errorMessages="Errors:";
 		if($scope.searchData.dueType=="0" || $scope.searchData.dueType==null)
 		{
			//tempData.errorMessages=tempData.errorMessages+"\nPlease select Due Type.";
			alert(("Please select Due Type.").toUpperCase());
			return;
 		}
		if($scope.searchData.dueType!=$scope.searchedDueType)
 		{
			//tempData.errorMessages=tempData.errorMessages+"\nPlease select Due Type.";
			alert(("THE DUE TYPE CURRENTLY SELECTED DOES NOT MATCH WITH THE DUE TYPE SELECTED FOR LAST SEARCH. KINDLY CLICK ON SEARCH AGAIN BEFORE STARTING TO ADD INVOICES.").toUpperCase());
			return;
 		}	
		//1.0.0.20 End
		
		var status="";
		var checkBoxckecked="N";
		var checkedString="";
//added by 1.0.0.16
		var glb_invoiceSearchStr = $scope.searchData.customerId +"~"+$scope.searchData.customerName + "~"+$scope.searchData.dmId + "~"+$scope.searchData.branch + "~"+$scope.searchData.productId + "~"+$scope.searchData.invoiceNumber +  "~"+$scope.searchData.fromDate + "~"+$scope.searchData.toDate + "~"+$scope.searchData.hidBatchNo + "~"+$scope.searchData.coverNoteNumber + "~"+$scope.searchData.INVOICE_GENERATED +"~"+$scope.searchData.STATE+"~"+$scope.searchData.SEZZone + "~"+$scope.searchData.dueType + "~"+$scope.searchData.chargeType + "~"+$scope.searchData.taxableFlag + "~"+$scope.searchData.allInvFlag ;//1.0.0.17 added selAllInvFlg 
		batchStr="";  // 1.0.0.3
		for(var i=0;i<$scope.invoiceList.length;i++){
		if($scope.invoiceList[i].INVOICE_SELECTED_FLG!="D" && document.getElementById("select"+i).checked==true){//1.0.0.15
		 status="A";
		 checkBoxckecked="Y";
		//INVOICE_X_COVER_NOTE_ID~LVN_BATCH_ID~LVN_INVOICE_ID~STATUS INVOICE_X_COVER_NOTE_ID
		}else{
		 status="I";
		}
		//batchStr=batchStr+$scope.invoiceList[i].INVOICE_X_COVER_NOTE_ID+"~"+$scope.batchData.BATCH_ID+"~"+$scope.invoiceList[i].INVOICE_ID+"~"+status;
		checkedString=checkedString+$scope.invoiceList[i].INVOICE_ID+",";
		$scope.invoiceList[i].INVOICE_X_COVER_NOTE_ID =!angular.isUndefined($scope.invoiceList[i].INVOICE_X_COVER_NOTE_ID)?$scope.invoiceList[i].INVOICE_X_COVER_NOTE_ID :"";
		
		//batchStr=batchStr+$scope.invoiceList[i].INVOICE_X_COVER_NOTE_ID+"~"+$scope.batchId+"~"+$scope.invoiceList[i].INVOICE_ID+"~"+status;
		batchStr=batchStr+$scope.batchId+"~"+$scope.invoiceList[i].INVOICE_ID+"~"+status;
		
		batchStr=batchStr+"^";
		}
		batchStr=batchStr.slice(0, -1);
		checkedString=checkedString.slice(0, -1);
		
		if(checkBoxckecked=="Y"){
			//1.0.0.17 add start
			if(responseFlag=="Y"){
				responseFlag="N";
				//1.0.0.17 add end
			$.ajax({
				  		 url: "addToBatch.sprg?isAjaxCalling=Y", //1.0.0.21 added  isAjaxCalling=Y
				  		 type: "POST", 
				  		 data:{
				  			batchStr: batchStr,
				  			invoiceType: invoiceType,
				  			checkedString:checkedString,
				  			modeOfOperation:$scope.searchData.printAndGenerate,
				  			withCoverNoteFlg:$scope.searchData.withcoverNote,
				  			withHeaderFlg:$scope.searchData.withheaderAndFooter,
				  			temp_invoiceSearchStr:glb_invoiceSearchStr,//added by 1.0.0.16
				  			  },
				  			
				  			success: function(response) {
				  			if(response!=null && response!="") 
				  					{  
				  				
				  					var jsonStr = JSON.parse(response);
				  					//if(jsonStr.Status=="SessionExpired") //1.0.0.21
									if (jsonStr.Status && jsonStr.Status === "SessionExpired")
			  						{
			  							window.location.href = 'llm/ReLogin.jsp?unknownError=Your session has been killed. Please login again.';
			  							return;
			  						}
				  					alert( jsonStr.PC_OUT_MESSAGE);
				  					responseFlag="Y";
				  					 if(jsonStr.PC_OUT_STATUS=="S"){
				  				   $scope.batchId = jsonStr.BATCH_ID!=null?jsonStr.BATCH_ID:"";
				  				//$scope.batchId =!angular.isUndefined($scope.batchId)?$scope.batchId :"";// 1.0.0.10
				  				  
	                                                                  // 1.0.0.10 start
				  				if($scope.batchId!="" && $scope.batchId!=undefined)
				  					 {
				  					$scope.getBatchSummay($scope.batchId);
				  					
				  					batchStr="";
				  					$scope.isGenerateDisable=false;
				  					document.getElementById("batchSummary").style.display='block';
				  					document.getElementById("generate").disabled=false;
			  						document.getElementById("cancelBatch").disabled=false;
				  					 }
				  					// 1.0.0.10 end
			  						// 1.0.0.5 start
			  						if($scope.batchId!="" && $scope.batchId!=undefined){
			  							document.getElementById("consolidatedInv").disabled=true;
			  							document.getElementById("covernoteRadio").disabled=true;
			  							document.getElementById("printRadio").disabled=true;
										document.getElementById("generateRadio").disabled=true;
			  						}
			  						
			  						// 1.0.0.5 end
				  					}
				  					 $scope.$apply();
				  					}}});  
			$scope.changeFlag="N";
			//1.0.0.17 add start
			}else{
				alert(("Already in Process...").toUpperCase());
	     		  return;
			}//1.0.0.17 add end
		}
			else{
				//alert("PLEASE SELECT INVOCES FIRST AND THEN CLICK ON ADD TO BATCH");
				alert("ATLEAST ONE INVOICE MUST BE SELECTED BEFORE CLICKING ON ADD TO BATCH.");
		}
		
		};

		
	$scope.getBatchSummay= function(batchId){


	$.ajax({
			  		 url: "getBatchSummary.sprg?isAjaxCalling=Y", //1.0.0.21 added  isAjaxCalling=Y
			  		 type: "POST", 
			  		 data:{
			  			batchId: batchId,
			  			  },
			  			
			  			success: function(response) {
			  			if(response!=null && response!="") 
			  					{  
			  					var jsonStr = JSON.parse(response);
			  					//if(jsonStr.Status=="SessionExpired") //1.0.0.21
								if (jsonStr.Status && jsonStr.Status === "SessionExpired")
		  						{
		  							window.location.href = 'llm/ReLogin.jsp?unknownError=Your session has been killed. Please login again.';
		  							return;
		  						}
			  					 if(jsonStr.PC_OUT_STATUS=="S"){  
			  				// $scope.addToBatchMessage = jsonStr.PC_OUT_MESSAGE;
			  				 // $scope.batchId = jsonStr.BATCH_ID;
			  				 $scope.batchNO=jsonStr.BATCH_NO;
			  				 
			  				 $scope.batchNO =!angular.isUndefined($scope.batchNO)?$scope.batchNO :"";
			  				 $scope.batchSummaryList=jsonStr.BATCH_LIST;
			  				 document.getElementById("batchId").value=$scope.batchNO;
			  				 $scope.$apply();
			  					 
			  					}}}}); 
	}


	$scope.generateCoverNote= function(){
		
		var bool = confirm(("This action cannot be undone or stopped during processing. Are you sure you want to continue?").toUpperCase());
		
		if(bool)
	    {

	$.ajax({
			  		 url: "generateCoverNote.sprg?isAjaxCalling=Y", //1.0.0.21 added  isAjaxCalling=Y
			  		 type: "POST", 
			  		 data:{
			  			batchId: $scope.batchId,
			  			invoiceType: invoiceType,
			  			  },
			  			
			  			success: function(response) {
			  			if(response!=null && response!="") 
			  					{  
			  					var jsonStr = JSON.parse(response);
			  				//	if(jsonStr.Status=="SessionExpired") //1.0.0.21
							if (jsonStr.Status && jsonStr.Status === "SessionExpired")
		  						{
		  							window.location.href = 'llm/ReLogin.jsp?unknownError=Your session has been killed. Please login again.';
		  							return;
		  						}
			  					 if(jsonStr.PC_OUT_STATUS=="S"){  
			  				$scope.addToBatchStatus = jsonStr.PC_OUT_STATUS;
			  				$scope.generateBatchMessage = jsonStr.PC_OUT_MESSAGE;
			  				
			  				alert($scope.generateBatchMessage);
			  				
			  					if($scope.addToBatchStatus=='S'){
			  						gerenationProgressFlag="Y";
			  						//document.getElementById("generate").disabled=true;
			  						//document.getElementById("cancelBatch").disabled=true;
			  						//document.getElementById("addToBatch").disabled=true;
			  						
			  						//$('input[type=checkbox]').each(function() 
			  						/*$('#select').each(function() 
											{ 
			       									 this.disabled = true; 
													}); */
			  						//$("#select").attr("disabled", true);
			  						$('#SearchResultDivId *').prop('disabled',true);
			  						
			  					}
			  				 // $scope.batchId = jsonStr.BATCH_ID;
			  				 //$scope.batchSummaryList=jsonStr.BATCH_LIST;
			  					// $scope.$apply();
			  					//$scope.getBatchSummay($scope.batchId);
			  					$scope.currentBatchGeneration();
			  					 }
			  					$scope.$apply();
			  					}}});
	    }

		onloadFlag="N";
	}


	$scope.cancelBatch= function(){
		
var bool = confirm(("This action cannot be undone. Are you sure you want to continue?").toUpperCase());
		
		if(bool)
	    {

	$.ajax({
			  		 url: "cancelBatch.sprg?isAjaxCalling=Y", //1.0.0.21 added  isAjaxCalling=Y
			  		 type: "POST", 
			  		 data:{
			  			batchId: $scope.batchId,
			  			  },
			  			
			  			success: function(response) {
			  			if(response!=null && response!="") 
			  					{  
			  					var jsonStr = JSON.parse(response);
			  				//	if(jsonStr.Status=="SessionExpired") //1.0.0.21
							if (jsonStr.Status && jsonStr.Status === "SessionExpired")
		  						{
		  							window.location.href = 'llm/ReLogin.jsp?unknownError=Your session has been killed. Please login again.';
		  							return;
		  						}
			  					alert(jsonStr.PC_OUT_MESSAGE);
			  					if(jsonStr.PC_OUT_STATUS=="S"){ 
			  				      
			  					  $scope.batchId="";
			  					  $scope.getBatchSummay($scope.batchId);
			  					  document.getElementById("batchSummary").style.display="none";
                                  document.getElementById("consolidatedInv").disabled=false;// 1.0.0.5// 1.0.0.9 
			  					  document.getElementById("covernoteRadio").disabled=false; // 1.0.0.5
			  					  document.getElementById("printRadio").disabled=false;
								  document.getElementById("generateRadio").disabled=false;
			  				
											
			  					}
			  					}}});


	    }
		
	};

	$scope.currentBatchGeneration= function(){

	$.ajax({
			  		 url: "currentBatchGeneration.sprg?isAjaxCalling=Y", //1.0.0.21 added  isAjaxCalling=Y
			  		 type: "POST", 
			  		 data:{
			  			batchId: $scope.batchId,
			  			  },
			  			
			  			success: function(response) {
			  			if(response!=null && response!="") 
			  					{  
			  					var jsonStr = JSON.parse(response);
			  				//	if(jsonStr.Status=="SessionExpired") //1.0.0.21
							if (jsonStr.Status && jsonStr.Status === "SessionExpired")
		  						{
		  							window.location.href = 'llm/ReLogin.jsp?unknownError=Your session has been killed. Please login again.';
		  							return;
		  						}
			  					if(jsonStr.PC_OUT_STATUS=="S"){ 
			  				
			  				 $scope.currentCoverNoteList=jsonStr.CURRENT_COVER_NOTE_LIST;
			  				$scope.batchCompletedFlag=jsonStr.BATCH_COMPLETED_FLAG;
			  					$scope.$apply();
			  				
			  								
			  					$scope.getBatchSummay($scope.batchId);
			  					
			  					
			  					if($scope.batchCompletedFlag!=null || $scope.batchCompletedFlag!=""){
			  						if($scope.batchCompletedFlag=="Y"){
			  							alert("BATCH "+$scope.batchNO+" COMPLETED SUCCESSFULLY.");
			  							document.getElementById("batchSummary").style.display="none";
			  							$('#SearchResultDivId *').prop('disabled',false);
			  							document.getElementById("generate").disabled=true;
				  						document.getElementById("cancelBatch").disabled=true;
				  						document.getElementById("covernoteRadio").disabled=false;
				  						document.getElementById("consolidatedInv").disabled=false;//1.0.0.8// 1.0.0.9
				  						document.getElementById("printRadio").disabled=false;
										document.getElementById("generateRadio").disabled=false;
				  						
				  						
				  						$scope.batchId="";
				  						$scope.batchNo="";
			  						}else if($scope.batchCompletedFlag=="P"){
			  							if(onloadFlag!="Y"){
			  							alert("BATCH "+$scope.batchNO+" IS IN PROGRESS.PLEASE WAIT...");
			  							}
			  							document.getElementById("batchSummary").style.display="block";
			  							$('#SearchResultDivId *').prop('disabled',true);
			  							document.getElementById("generate").disabled=true;
				  						document.getElementById("cancelBatch").disabled=true;
			  						}
			  						else if($scope.batchCompletedFlag=="E"){
			  							alert("AN ERROR OCCURRED DURING BATCH GENERATION.");
			  							document.getElementById("batchSummary").style.display="none";
			  							$('#SearchResultDivId *').prop('disabled',false);
			  							document.getElementById("generate").disabled=true;
				  						document.getElementById("cancelBatch").disabled=true;
				  						document.getElementById("covernoteRadio").disabled=false;
				  						document.getElementById("consolidatedInv").disabled=false;//1.0.0.8// 1.0.0.9
				  						document.getElementById("printRadio").disabled=false;
										document.getElementById("generateRadio").disabled=false;
				  						
				  						
				  						$scope.batchId="";
				  						$scope.batchNo="";
			  						}
			  						
			  						else{
			  							//document.getElementById("batchSummary").style.display="none";
			  						}
			  						
			  					}
			  					onloadFlag="N";
			  					$scope.generateEInvoiceEnableFlag=true; //1.0.0.19
								document.getElementById("generateEInvoice").disabled=true; //1.0.0.19
			  					}
			  					}}});


	};

// 1.0.0.5 start
$scope.cancelInvoiceCoverNote = function()
{
	 var newDataList=[];
	 // 1.0.0.10 start
	 var batchType=$scope.searchData.hidInvoiceType;
	 var bool = confirm(("Do you want to continue?").toUpperCase());
		
		if(bool)
	    {
	 // 1.0.0.10 end
     angular.forEach($scope.coverNoteList, function(selected){
     	
         if(!selected.selected){
             //newDataList.push(selected);
            
         }
         else {
        	 
	        	 if(cancelInvoiceCoverNoteStr!=""){
	        		 cancelInvoiceCoverNoteStr=cancelInvoiceCoverNoteStr + ","+ selected.INVOICE_COVER_NOTE_ID;
	        	 }
	        	 else {
	        		 cancelInvoiceCoverNoteStr = selected.INVOICE_COVER_NOTE_ID;
	        	 }
	        	 
	        	// selected.STATUS="CANCELLED";
	        		 
         }
         newDataList.push(selected);
     }); 
     $scope.coverNoteList = newDataList;
     if(cancelInvoiceCoverNoteStr=="" || cancelInvoiceCoverNoteStr==null){
    	 alert("PLEASE SELECT ATLEAST ONE INVOICE TO CANCEL.");
    	 return;
     }
     
     
     $.ajax({
  		 url: "deleteCoverNotes.sprg?isAjaxCalling=Y", //1.0.0.21 added  isAjaxCalling=Y
  		 type: "POST", 
  		 data:{
  			cancelInvoiceCoverNoteStr: cancelInvoiceCoverNoteStr,
  			batchType:batchType
  			  },
  			
  			success: function(response) {
  			if(response!=null && response!="") 
  					{  
  					 var jsonStr = JSON.parse(response);
  				//	if(jsonStr.Status=="SessionExpired") //1.0.0.21
				if (jsonStr.Status && jsonStr.Status === "SessionExpired")
						{
							window.location.href = 'llm/ReLogin.jsp?unknownError=Your session has been killed. Please login again.';
							return;
						}
  					cancelInvoiceCoverNoteStr="";
  			      alert((jsonStr.PC_OUT_MESSAGE).toUpperCase());
  					return;
								
								
  					
  				}}});
  // 1.0.0.10 start	
	    }
      else{
			
			return;
		}
}

// 1.0.0.5 end


/////////////////////////////////////////////////////////////////////

$scope.cancelSingleInvoice = function()
{
	 var newDataList=[];
  
	 var bool = confirm(("Do you want to continue?").toUpperCase());
		
		if(bool)
	    {
			
	   	
    	 
    		   angular.forEach($scope.invoiceList, function(selected,rowId){
    			if(document.getElementById("select"+rowId).checked==false){
             //newDataList.push(selected);
            
         }
    	
         else {
        	 
	        	 if(cancelSingleInvoiceStr!=""){
	        		 cancelSingleInvoiceStr=cancelSingleInvoiceStr + ","+ selected.INVOICE_ID;
	        	 }
	        	 else {
	        		 cancelSingleInvoiceStr = selected.INVOICE_ID;
	        	 }
	        	 
	        	 //selected.INVOICE_STATUS="NOT GENERATED";
	        	 //selected.INVOICE_NO="";
	        		 
         }
    	 
         newDataList.push(selected);
     }); 
     
     $scope.invoiceList = newDataList;
     if(cancelSingleInvoiceStr=="" || cancelSingleInvoiceStr==null){
    	 alert("PLEASE SELECT ATLEAST ONE INVOICE TO CANCEL.");
    	 return;
     }
     
     
     $.ajax({
  		 url: "cancelSingleInvoice.sprg?isAjaxCalling=Y", //1.0.0.21 added  isAjaxCalling=Y
  		 type: "POST", 
  		 data:{
  			cancelSingleInvoiceStr: cancelSingleInvoiceStr
  			
  			  },
  			
  			success: function(response) {
  			if(response!=null && response!="") 
  					{  
  				cancelSingleInvoiceStr="";
  					 var jsonStr = JSON.parse(response);
  				//	if(jsonStr.Status=="SessionExpired") //1.0.0.21
				if (jsonStr.Status && jsonStr.Status === "SessionExpired")
						{
							window.location.href = 'llm/ReLogin.jsp?unknownError=Your session has been killed. Please login again.';
							return;
						}
  			      alert((jsonStr.PC_OUT_MESSAGE).toUpperCase());
  					return;
								
								
  					
  					}}});
	    }
		else{
			
			return;
		}
     
}


//////////////////////////////////////////////////////////////////////////
// 1.0.0.10 end







	$scope.updateCoverNotes= function(){
			var coverNoteIdStr="";
	$scope.coverNoteList =!angular.isUndefined($scope.coverNoteList)?$scope.coverNoteList :"";
	for(var i=0;i<$scope.coverNoteList.length;i++){
		if(document.getElementById("coverSelect"+i).checked==true){
		 
		//batchStr=batchStr+$scope.invoiceList[i].INVOICE_X_COVER_NOTE_ID+"~"+$scope.batchData.BATCH_ID+"~"+$scope.invoiceList[i].INVOICE_ID+"~"+status;
		$scope.coverNoteList[i].REMARKS =!angular.isUndefined($scope.coverNoteList[i].REMARKS)?$scope.coverNoteList[i].REMARKS :"";
		$scope.coverNoteList[i].INVOICE_COVER_NOTE_ID =!angular.isUndefined($scope.coverNoteList[i].INVOICE_COVER_NOTE_ID)?$scope.coverNoteList[i].INVOICE_COVER_NOTE_ID :"";
		coverNoteStr=coverNoteStr+$scope.coverNoteList[i].INVOICE_COVER_NOTE_ID+"~"+$scope.coverNoteList[i].REMARKS;
		
		coverNoteStr=coverNoteStr+"^";
		coverNoteIdStr=coverNoteIdStr+$scope.coverNoteList[i].INVOICE_COVER_NOTE_ID+",";
		}
		}
		$scope.currentCoverNoteList =!angular.isUndefined($scope.currentCoverNoteList)?$scope.currentCoverNoteList :"";
		for(var i=0;i<$scope.currentCoverNoteList.length;i++){
		if(document.getElementById("currentCoverSelect"+i).checked==true){
		 
		//batchStr=batchStr+$scope.invoiceList[i].INVOICE_X_COVER_NOTE_ID+"~"+$scope.batchData.BATCH_ID+"~"+$scope.invoiceList[i].INVOICE_ID+"~"+status;
		$scope.currentCoverNoteList[i].REMARKS =!angular.isUndefined($scope.currentCoverNoteList[i].REMARKS)?$scope.currentCoverNoteList[i].REMARKS :"";
		
		coverNoteStr=coverNoteStr+$scope.currentCoverNoteList[i].INVOICE_COVER_NOTE_ID+"~"+$scope.currentCoverNoteList[i].REMARKS;
		
		coverNoteStr=coverNoteStr+"^";
		coverNoteIdStr=coverNoteIdStr+$scope.currentCoverNoteList[i].INVOICE_COVER_NOTE_ID+",";
		}
		} 
		
		
		
		coverNoteStr=coverNoteStr.slice(0, -1);
		coverNoteIdStr=coverNoteIdStr.slice(0, -1);
		
		if(coverNoteStr=="" || coverNoteStr==null){
	    	 alert("PLEASE SELECT ATLEAST ONE COVER NOTE TO UPDATE.");
	    	 return;
	     }
		

	$.ajax({
			  		 url: "updateCoverNotes.sprg?isAjaxCalling=Y", //1.0.0.21 added  isAjaxCalling=Y
			  		 type: "POST", 
			  		 data:{
			  			coverNoteStr: coverNoteStr,
			  			coverNoteIdStr:coverNoteIdStr,
			  			  },
			  			
			  			success: function(response) {
			  			if(response!=null && response!="") 
			  					{  
			  					var jsonStr = JSON.parse(response);
			  					  
			  				//	if(jsonStr.Status=="SessionExpired") //1.0.0.21
							if (jsonStr.Status && jsonStr.Status === "SessionExpired")
		  						{
		  							window.location.href = 'llm/ReLogin.jsp?unknownError=Your session has been killed. Please login again.';
		  							return;
		  						}
			  					$scope.upadateRemarksMessage=jsonStr.PC_OUT_MESSAGE;
			  					
			  					$scope.upadateRemarksMessage =!angular.isUndefined($scope.upadateRemarksMessage)?$scope.upadateRemarksMessage :"";
			  					if($scope.upadateRemarksMessage!=""){
			  					alert($scope.upadateRemarksMessage);
			  					}
			  				 //$scope.currentCoverNoteList=jsonStr.CURRENT_COVER_NOTE_LIST;
			  					$scope.$apply();
			  					coverNoteStr="";
			  					coverNoteIdStr="";
			  				//	$scope.batchId="";
			  					//$scope.getBatchSummay($scope.batchId);
			  					//$('input[type=checkbox]').each(function() 
								//	{ 
	       						//			 this.checked = false; 
								//			}); 
											
											
			  					
			  					}}});
	coverNoteStr="";
	coverNoteIdStr="";

	};
	

	
	$scope.paginationDetailsForInvoice = function(totalPageNumber,currentpage){
		
		var totalPages=totalPageNumber;
		var startIndex=document.forms[0].minPage.value;
		var totalPages=totalPageNumber;
		var endIndex=document.forms[0].maxPage.value;
		//var endIndex=totalPageNumber--can change
		var windowSize=5;
		var currentPage=document.forms[0].currentPage.value;
		document.forms[0].currentPage.value=document.forms[0].currentPage.value;
		document.forms[0].minPage.value=document.forms[0].minPage.value;
		document.forms[0].maxPage.value=document.forms[0].maxPage.value;
		//var previous =document.forms[0].previous.value;
		//var next =document.forms[0].next.value;
		var previous ="Y";
		var next ="N";
		var minPage=1;
		document.forms[0].totalPages.value=totalPageNumber;
	  var paginationRowHtml = "";
	  
	  	 if(currentpage==totalPageNumber)
	  	 {
	  			if(totalPageNumber>=windowSize)
	  			{
		 		startIndex=totalPageNumber-4;
				endIndex=totalPageNumber;
				document.forms[0].minPage.value=startIndex;
				document.forms[0].maxPage.value=endIndex;
				document.forms[0].currentPage.value=totalPageNumber;
				}
		} 
		if(currentpage==1)
		{
		startIndex=1;
		endIndex=windowSize;
		document.forms[0].minPage.value=startIndex;
		document.forms[0].maxPage.value=endIndex;
		document.forms[0].currentPage.value=currentpage;
		}
	  
	 	if(parseInt(endIndex)>=parseInt(totalPageNumber))
	 	{
	 		endIndex=totalPageNumber;
	 	
	 	if(parseInt(endIndex)==parseInt(totalPageNumber))
	 			{
	 			startIndex=endIndex-4;
	 			}
	 	}
	 	
	 	if(Number(startIndex)<=Number(0))
	 	{
	 		startIndex=1;
	 		
	 		endIndex=totalPageNumber;
		}
					
	  var diffenceMaxMin =  endIndex- startIndex;
	  paginationRowHtml = paginationRowHtml + "<table border='0' class='grey_pan' align='center'><tr class='grey_pan'>";
	  paginationRowHtml = paginationRowHtml + "<td colspan='9' align='center'>";
	  if(diffenceMaxMin>0)
		{
			if(currentpage!=1)
				{
	                   paginationRowHtml = paginationRowHtml + "<b><a href='javascript:getPageForInvoice(1)'>First</a></b>&nbsp;&nbsp;&nbsp;"; 
	             }
			else
		     {
	  
	                  paginationRowHtml = paginationRowHtml + "<b><font color='cc0000'>First</font></b></a>&nbsp;&nbsp;&nbsp;"; 
	       
	         }
	    }
		else
		{   
		}
		if("Y"===previous)
			{
	  if(parseInt(currentpage)!=1){
	  paginationRowHtml = paginationRowHtml + "<a href=javascript:moveToDirForInvoice('P')>Pre</a>&nbsp;&nbsp;&nbsp;";
	  }
	  else{
		  paginationRowHtml = paginationRowHtml + "<b><font color='cc0000'>Pre</b>&nbsp;&nbsp;&nbsp;";
	  }
	       } 
		if(totalPages<=windowSize)
		  {
				for(var count =startIndex;count<=totalPages;count++)
					 {
					    if(currentpage==count)
							{
					            paginationRowHtml = paginationRowHtml + "<a href='javascript:getPageForInvoice("+count+")'><b><font color='cc0000'>"+count+"</font></a>&nbsp;&nbsp;&nbsp;";
	                        }
				        else
							{
	   
	                            paginationRowHtml = paginationRowHtml + "<a href='javascript:getPageForInvoice("+count+")'>"+count+"</a>&nbsp;&nbsp;&nbsp;";
	                            
	                         }
					 }
			}
		else
		 {
		 	for(var count =parseInt(startIndex);count<=parseInt(endIndex);count++)
					{
					
					   if(currentpage==count)
							{
							      
				                paginationRowHtml = paginationRowHtml + "<b><font color='cc0000'>"+count+"</font></b>&nbsp;&nbsp;&nbsp;";
				                
				             }
						else
						{
						
						   
				               paginationRowHtml = paginationRowHtml + "<a href='javascript:getPageForInvoice("+count+")'>"+count+"</a>&nbsp;&nbsp;&nbsp;";
				        }
					}
			}
			if("N"===next)
				{
				if(currentpage!=totalPages){
				paginationRowHtml = paginationRowHtml + "<a href=javascript:moveToDirForInvoice('N')>Next</a>&nbsp;&nbsp;&nbsp;";
				}
				else{
					paginationRowHtml = paginationRowHtml + "<b><font color='cc0000'>Next</b>&nbsp;&nbsp;&nbsp;";
				}
				}
				 if(diffenceMaxMin>0)
					{
						if(parseInt(currentpage)!=parseInt(totalPages))
							{
				                paginationRowHtml = paginationRowHtml + "<a href='javascript:getPageForInvoice("+totalPages+")'><b>Last</b></a>&nbsp;&nbsp;&nbsp;";
				             }
						else
						  {
				               paginationRowHtml = paginationRowHtml + "<b><font color='cc0000'>Last</font></b>&nbsp;&nbsp;&nbsp;";
				               
				            }
					}
							
				 
				   paginationRowHtml = paginationRowHtml + "</td></tr></table>";             
				               
				               
				document.getElementById("pageneation").innerHTML = paginationRowHtml;
				//document.getElementById("pageneation1").innerHTML = paginationRowHtml;
				
			document.forms[0].minPage.value=startIndex;
			document.forms[0].maxPage.value=endIndex;
			document.forms[0].currentPage.value=currentpage;	

	};
	
	
	
	
	
$scope.paginationDetailsForCoverNote = function(totalPageNumberCoverNote,currentpageCoverNote){
		
		var totalPagesCoverNote=totalPageNumberCoverNote;
		var startIndexCoverNote=document.forms[0].minPageCoverNote.value;
		var totalPagesCoverNote=totalPageNumberCoverNote;
		var endIndexCoverNote=document.forms[0].maxPageCoverNote.value;
		var windowSizeCoverNote=5;
		var currentPageCoverNote=document.forms[0].currentPageCoverNote.value;
		document.forms[0].currentPageCoverNote.value=document.forms[0].currentPageCoverNote.value;
		document.forms[0].minPageCoverNote.value=document.forms[0].minPageCoverNote.value;
		document.forms[0].maxPageCoverNote.value=document.forms[0].maxPageCoverNote.value;
		//var previous =document.forms[0].previous.value;
		//var next =document.forms[0].next.value;
		var previousCoverNote ="Y";
		var nextCoverNote ="N";
		var minPageCoverNote=1;
		document.forms[0].totalPagesCoverNote.value=totalPageNumberCoverNote;
	  var paginationRowHtml = "";
	  
	  	 if(currentpageCoverNote==totalPageNumberCoverNote)
	  	 {
	  			if(totalPageNumberCoverNote>=windowSizeCoverNote)
	  			{
		 		startIndexCoverNote=totalPageNumberCoverNote-4;
				endIndexCoverNote=totalPageNumberCoverNote;
				document.forms[0].minPageCoverNote.value=startIndexCoverNote;
				document.forms[0].maxPageCoverNote.value=endIndexCoverNote;
				document.forms[0].currentPageCoverNote.value=totalPageNumberCoverNote;
				}
		} 
		if(currentpageCoverNote==1)
		{
		startIndexCoverNote=1;
		endIndexCoverNote=windowSizeCoverNote;
		document.forms[0].minPageCoverNote.value=startIndexCoverNote;
		document.forms[0].maxPageCoverNote.value=endIndexCoverNote;
		document.forms[0].currentPageCoverNote.value=currentpageCoverNote;
		}
	  
	 	if(parseInt(endIndexCoverNote)>=parseInt(totalPageNumberCoverNote))
	 	{
	 		endIndexCoverNote=totalPageNumberCoverNote;
	 	
	 	if(parseInt(endIndexCoverNote)==parseInt(totalPageNumberCoverNote))
	 			{
	 			startIndexCoverNote=endIndexCoverNote-4;
	 			}
	 	}
	 	
	 	if(startIndexCoverNote<=0)
	 	{
	 		startIndexCoverNote=1;
	 		
	 		endIndexCoverNote=totalPageNumberCoverNote;
		}
					
	  var diffenceMaxMinCoverNote =  endIndexCoverNote- startIndexCoverNote;
	  paginationRowHtml = paginationRowHtml + "<table border='0' class='grey_pan' align='center'><tr class='grey_pan'>";
	  paginationRowHtml = paginationRowHtml + "<td colspan='9' align='center'>";
	  if(diffenceMaxMinCoverNote>0)
		{
			if(parseInt(currentpageCoverNote)!=1)
				{
	                   paginationRowHtml = paginationRowHtml + "<b><a href='javascript:getPageForCoverNote(1)'>First</a></b>&nbsp;&nbsp;&nbsp;"; 
	             }
			else
		     {
	  
	                  paginationRowHtml = paginationRowHtml + "<b><font color='cc0000'>First</font></b></a>&nbsp;&nbsp;&nbsp;"; 
	       
	         }
	    }
		else
		{   
		}
		if("Y"===previousCoverNote)
			{
			if(parseInt(currentpageCoverNote)!=1)
			{
	  paginationRowHtml = paginationRowHtml + "<a href=javascript:moveToDirForCoverNote('P')>Pre</a>&nbsp;&nbsp;&nbsp;";
			}
			else{
				paginationRowHtml = paginationRowHtml + "<b><font color='cc0000'>Pre</b>&nbsp;&nbsp;&nbsp;";
			}
	       } 
		if(totalPagesCoverNote<=windowSizeCoverNote)
		  {
				for(var count =startIndexCoverNote;count<=totalPagesCoverNote;count++)
					 {
					    if(currentpageCoverNote==count)
							{
					            paginationRowHtml = paginationRowHtml + "<a href='javascript:getPageForCoverNote("+count+")'><b><font color='cc0000'>"+count+"</font></a>&nbsp;&nbsp;&nbsp;";
	                        }
				        else
							{
	   
	                            paginationRowHtml = paginationRowHtml + "<a href='javascript:getPageForCoverNote("+count+")'>"+count+"</a>&nbsp;&nbsp;&nbsp;";
	                            
	                         }
					 }
			}
		else
		 {
		 	for(var count =parseInt(startIndexCoverNote);count<=parseInt(endIndexCoverNote);count++)
					{
					
					   if(currentpageCoverNote==count)
							{
							      
				                paginationRowHtml = paginationRowHtml + "<b><font color='cc0000'>"+count+"</font></b>&nbsp;&nbsp;&nbsp;";
				                
				             }
						else
						{
						
						   
				               paginationRowHtml = paginationRowHtml + "<a href='javascript:getPageForCoverNote("+count+")'>"+count+"</a>&nbsp;&nbsp;&nbsp;";
				        }
					}
			}
			if("N"===nextCoverNote)
				{
				if(currentpageCoverNote!=totalPageNumberCoverNote)
				{
				paginationRowHtml = paginationRowHtml + "<a href=javascript:moveToDirForCoverNote('N')>Next</a>&nbsp;&nbsp;&nbsp;";
				}
				else{
					paginationRowHtml = paginationRowHtml + "<b><font color='cc0000'>Next</b>&nbsp;&nbsp;&nbsp;";
				}
				}
				 if(diffenceMaxMinCoverNote>0)
					{
						if(parseInt(currentpageCoverNote)!=parseInt(totalPagesCoverNote))
							{
				                paginationRowHtml = paginationRowHtml + "<a href='javascript:getPageForCoverNote("+totalPagesCoverNote+")'><b>Last</b></a>&nbsp;&nbsp;&nbsp;";
				             }
						else
						  {
				               paginationRowHtml = paginationRowHtml + "<b><font color='cc0000'>Last</font></b>&nbsp;&nbsp;&nbsp;";
				               
				            }
					}
							
				 
				   paginationRowHtml = paginationRowHtml + "</td></tr></table>";             
				               
				               
				//document.getElementById("pageneation").innerHTML = paginationRowHtml;
				document.getElementById("pageneation1").innerHTML = paginationRowHtml;
				
			document.forms[0].minPageCoverNote.value=startIndexCoverNote;
			document.forms[0].maxPageCoverNote.value=endIndexCoverNote;
			document.forms[0].currentPageCoverNote.value=currentpageCoverNote;	

	};
	
	
	
	 
	 	
	 	$scope.enableRemarks= function(val){
	 		
	 	if(document.getElementById("coverSelect"+val).checked==true){
			document.getElementById("remarks"+val).disabled = false;
			}else{
			document.getElementById("remarks"+val).disabled = true;
			document.getElementById("remarks"+val).value ="";// 1.0.0.9
			
			}
	 	     		
			};
                          // 1.0.0.15 start
			$scope.selectAllCheckBoxSel= function()
			{
				var flag="N";
				for(var i=0;i<$scope.invoiceList.length;i++)
				{
					if(document.getElementById("select"+i).checked==true)
					{
						flag="Y";
						$scope.changeFlag="Y";
					}
					else{
						flag="N";
						break;
					}
					
				}
				if(flag=="Y"){
					document.getElementById("selectAll").checked=true;
				}
			}
	 	        
	 	$scope.changeFlagEnable= function(index){//1.0.0.15
	 		
	 		if(document.getElementById("select"+index).checked==true)
	 		{
	 		$scope.changeFlag="Y";
	 		$scope.selectAllCheckBoxSel();
	 		}
	 		else{
	 			$scope.changeFlag="N";
	 			document.getElementById("selectAll").checked=false;
	 		}
		 	// 1.0.0.15 end
				};
	 	
	 	$scope.enableRemarksofCurrentCoverNote= function(val){
		 	if(document.getElementById("currentCoverSelect"+val).checked==true){
				document.getElementById("Remarks"+val).disabled = false;
				}else{
				document.getElementById("Remarks"+val).disabled = true;
				
				}
				};
	 	
	 	$scope.fileFunction= function(coverNoteNo,fileName,batchNo,invoiceType){

	 		var browser=(navigator.userAgent).toLowerCase();
	 	      if(invoiceType=="COVERNOTE"){// 1.0.0.6
			
				if(browser.indexOf("chrome")>0)
				{	
				var targetWin = window.open("fileFunction.sprg?coverNoteNo="+coverNoteNo+"&fileName="+fileName+"&invoiceType="+invoiceType+"&batchNo="+batchNo+"&actionId=1200106855&viewHeaderFlag=Y","fileFunction","width=1000px,height=600p,top=400px,left=200px");//1.0.0.1
					targetWin.focus();
				}else{
					window.showModalDialog("fileFunction.sprg?coverNoteNo="+coverNoteNo+"&fileName="+fileName+"&invoiceType="+invoiceType+"&batchNo="+batchNo+"&actionId=1200106855&viewHeaderFlag=Y","fileFunction","width=1000px,height=600p,top=400px,left=200px");//1.0.0.1
				}
	 	      }
	 	      // 1.0.0.6 start
	 	      if(invoiceType=="CONSOLIDATED INV"){
	 	    	 if(browser.indexOf("chrome")>0)
					{	
					var targetWin = window.open("fileFunction.sprg?coverNoteNo="+coverNoteNo+"&fileName="+fileName+"&invoiceType="+invoiceType+"&batchNo="+batchNo+"&actionId=1200108933&viewHeaderFlag=Y","fileFunction","width=1000px,height=600p,top=400px,left=200px");//1.0.0.1
						targetWin.focus();
					}else{
						window.showModalDialog("fileFunction.sprg?coverNoteNo="+coverNoteNo+"&fileName="+fileName+"&invoiceType="+invoiceType+"&batchNo="+batchNo+"&actionId=1200108933&viewHeaderFlag=Y","fileFunction","width=1000px,height=600p,top=400px,left=200px");//1.0.0.1
					} 
	 	    	  // 1.0.0.6 end
	 	    	 
	 	      }

	 		};
	//1.0.0.11 start
	 $scope.downloadReconReport = function(batchId,batchNo)
	 {
		window.open("downloadReconReport.sprg?batchId="+batchId+"&batchNo="+batchNo);
	 }
	//1.0.0.11 end 		
	 		
               $scope.fileFunctionForBulkPrinting= function(fileName,invoiceType,modeOfOperation,fileId,path)
               {
            	   
            	 // window.open("downloadReportServlet?fileName="+fileName+"&path="+path+"&lable=BULK_INVOICE");
            	   var a = document.createElement('a');
            	   a.href ="downloadReportServlet?fileName="+fileName+"&path="+path+"&lable=BULK_INVOICE";
            	   document.body.appendChild(a);
            	   a.click();
            	   document.body.removeChild(a);
               
               };
	 		
	 		
	 	// 1.0.0.5 start
               //1.0.0.14 start
	 	$scope.checkAll= function(){
	 		
// 1.0.0.13 start
	 		    angular.forEach($scope.invoiceList, function (obj,i)
	 		    {
			 		  //  if(obj.INVOICE_SELECTED_FLG=="A")
			 		  //  {
				 		       obj.selected = $scope.select;
				 		       if($scope.select==false)
				 		       {
				 		    	  document.getElementById("select"+i).checked=false;
				 		    	  $scope.changeFlag="N";// 1.0.0.15
				 		       }
				 		       else{//1.0.0.15
				 		    	   if(obj.INVOICE_SELECTED_FLG!="D")//1.0.0.15
				 		    	   {
				 		    	    $scope.changeFlag="Y";//1.0.0.15
				 		    	   }
				 		       }
			 		 //   }
	 		    // 1.0.0.13 end
	 		    });
	 		   
	 		  };
	 		  // 1.0.0.14 end
	 	// 1.0.0.5 end
	 		$scope.setBatchNoDropDown= function(value){
	 				
	 			document.getElementById("batchNo").value="";
	 			$scope.searchData.batchNo="";
	 			$scope.searchData.hidBatchNo="";
	 		 };
	 	
	$scope.showBatchDetailReport= function(batchNo){

	 		var browser=(navigator.userAgent).toLowerCase();
	 	    var batchNo =$scope.batchNO;  
				
				if(browser.indexOf("chrome")>0)
				{	
				var targetWin = window.open("fileFunction.sprg?batchNo="+batchNo+"&actionId=1200106857&viewHeaderFlag=Y","fileFunction","width=1000px,height=600p,top=400px,left=200px");//1.0.0.1
					targetWin.focus();
				}else{
					window.showModalDialog("fileFunction.sprg?batchNo="+batchNo+"&actionId=1200106857&viewHeaderFlag=Y","fileFunction","width=1000px,height=600p,top=400px,left=200px");//1.0.0.1
				}


	 		}
	
	// 1.0.0.19 start
	
	  $scope.generateEInvoice = function()
	  {
		
		 var bool = confirm(("Are you sure you want to start generating the E-Invoices? this action can't be undone.").toUpperCase());
				
				if(bool)
			    {		
					$scope.generateEInvoiceEnableFlag=true;
					document.getElementById("generateEInvoice").disabled=true;
				  $.ajax({
				  		 url: "registerEInvoicingProcess.sprg?isAjaxCalling=Y", //1.0.0.21 added  isAjaxCalling=Y
				  		 type: "POST", 
				  		 data:{
				  			batchId: $scope.searchData.hidBatchNo
				  			  },
				  			
				  			success: function(response) {
				  			if(response!=null && response!="") 
				  					{  
				  				
				  					var jsonStr = JSON.parse(response);
				  					//if(jsonStr.Status=="SessionExpired") //1.0.0.21
									if (jsonStr.Status && jsonStr.Status === "SessionExpired")
			  						{
			  							window.location.href = 'llm/ReLogin.jsp?unknownError=Your session has been killed. Please login again.';
			  							return;
			  						}
				  					alert( jsonStr.PC_OUT_MESSAGE);
				  					 if(jsonStr.PC_OUT_STATUS=="F"){
				  						$scope.generateEInvoiceEnableFlag=false;
				  						document.getElementById("generateEInvoice").disabled=false;
				  					}
				  					 $scope.$apply();
				  					}}}); 
			    }
			 
	  }
	  
	  $scope.getEInvoiceProcessStatus = function()
	  {
       if($scope.searchData.printAndGenerate=="G" && $scope.searchData.batchNo!="" && $scope.searchData.batchNo!=null)
			{
		  $.ajax({
		  		 url: "getEInvoicingProcessStatus.sprg?isAjaxCalling=Y", //1.0.0.21 added  isAjaxCalling=Y
		  		 type: "POST", 
		  		 data:{
		  			batchId: $scope.searchData.hidBatchNo
		  			  },
		  			
		  			success: function(response) {
		  			if(response!=null && response!="") 
		  					{  
		  				
		  					var jsonStr = JSON.parse(response);
		  				//	if(jsonStr.Status=="SessionExpired") //1.0.0.21
						if (jsonStr.Status && jsonStr.Status === "SessionExpired")
	  						{
	  							window.location.href = 'llm/ReLogin.jsp?unknownError=Your session has been killed. Please login again.';
	  							return;
	  						}
		  					alert( jsonStr.PC_OUT_MESSAGE);
		  					 if(jsonStr.PC_OUT_STATUS=="S"){
		  				     
		  					}
		  					 $scope.$apply();
		  					}}}); 
			}
	  }
	// 1.0.0.19 end
	
	
	$scope.getInvoiceListDataOnLoad("1","1");
		
	
});

// 1.0.0.5 start
app.filter('filter', function() {

	   return function( items, name) {
	    var filtered = [];
		
		angular.forEach(items, function(item) {
	if((name!='' && item.masterParentId==name) )
		{
		  filtered.push(item);
		}
		
		});
		
	     return filtered;
	  };
	  
	

});
// 1.0.0.5 end
