/*+  File NAME 	: invoice_controller.js


+  PURPOSE		: 
+  CREATED BY	: 
+  CREATION DATE	: 
+  INITIAL VERSION : 1.0.0.0
+  **********************************************************************************************************************************
+  VERSION NO   UPDATED BY       UPDATED ON       REASON FOR CHANGE
+  1.0.0.1		Priyanka Soni	 09-Nov-2022	 CR:- User Logout on EOD
+  1.0.0.22		Ravi Shankar	 20-Mar-2024	 Invoice date restriction basis configuration of days from frontend
+  1.0.0.23		Sanchi Agarwal	 12-Nov-2026	 Disable manual invoice generation via config flag
 **********************************************************************************************************************************
*/
var app = angular.module('invoiceDetailsApp', []);
app.controller('invoiceDetailsCtrl', function($scope, $http,$filter, $window,$rootScope,$location) {
	$scope.changeFlag="N";
	$scope.quotationList= null;

	 $scope.chargeTypeObject = mastersListObj.chargeTypeMaster;
	 
	 $scope.dueTypeObject = mastersListObj.dueTypeMaster; 
	 
	 
	 $scope.schemeMasterObject = mastersListObj.schemeMaster;
	 
	 $scope.productDetailsMasterObject = mastersListObj.productDetails;
	 
	 $scope.branchObject = mastersListObj.leaseMaster;
	
	 $scope.batchNumberObject = mastersListObj.batchNumberMaster;
	 var onloadFlag="N";
	 
	 
	 var invoiceType='COVERNOTE';
	 var taxableFlg='';
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
		
		var cancelSingleInvoiceStr="";
		
		
		var screenId="";
		
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
	 	
	 	$scope.shownoRecordFoundDiv=false;
	 	$scope.shownoRecordFoundDiv1=false;
	 	$scope.validateFlag="Y";
	 	 
	 	  
	 	  
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
	
	
	
	$scope.invoiceList.INVOICE_X_COVER_NOTE_ID =!angular.isUndefined($scope.invoiceList.INVOICE_X_COVER_NOTE_ID)?$scope.invoiceList.INVOICE_X_COVER_NOTE_ID :"";
	$scope.batchData.BATCH_ID =!angular.isUndefined($scope.batchData.BATCH_ID)?$scope.batchData.BATCH_ID :"";
	$scope.searchData.batchCreatedBy =!angular.isUndefined($scope.searchData.batchCreatedBy)?$scope.searchData.batchCreatedBy :"0";
	
	
	
	
	$scope.searchData.consolidatedInvoiceNo=!angular.isUndefined($scope.searchData.consolidatedInvoiceNo)?$scope.searchData.consolidatedInvoiceNo :"";
	
	$scope.searchData.consolidatedInvoiceNo_TEMP=!angular.isUndefined($scope.searchData.consolidatedInvoiceNo_TEMP)?$scope.searchData.consolidatedInvoiceNo_TEMP :"";
	$scope.searchData.coverNoteNumber_TEMP =!angular.isUndefined($scope.searchData.coverNoteNumber_TEMP)?$scope.searchData.coverNoteNumber_TEMP :"";
	
	
	$scope.searchData.hidBatchNo =!angular.isUndefined($scope.searchData.hidBatchNo)?$scope.searchData.hidBatchNo :"";
	
	$scope.searchData.STATE =!angular.isUndefined($scope.searchData.STATE_TEMP)?$scope.searchData.STATE :"";
	
	$scope.searchData.hidInvoiceType =!angular.isUndefined($scope.searchData.hidInvoiceType)?$scope.searchData.hidInvoiceType :"COVERNOTE";
	
	
	$scope.searchData.CONS_INVOICE_GENERATED =!angular.isUndefined($scope.searchData.CONS_INVOICE_GENERATED)?$scope.searchData.CONS_INVOICE_GENERATED :"N";
	
	$scope.searchData.INVOICE_GENERATED =!angular.isUndefined($scope.searchData.INVOICE_GENERATED)?$scope.searchData.INVOICE_GENERATED :"";
	
	$scope.searchData.chargeType =!angular.isUndefined($scope.searchData.chargeType)?$scope.searchData.chargeType :"";
	
	$scope.searchData.taxableFlag =!angular.isUndefined($scope.searchData.taxableFlag)?$scope.searchData.taxableFlag :"BOTH";
	$scope.searchData.allInvFlag =!angular.isUndefined($scope.searchData.allInvFlag)?$scope.searchData.allInvFlag :"N";
	
	$scope.searchData.dueType =!angular.isUndefined($scope.searchData.dueType)?$scope.searchData.dueType :"0";
	$scope.searchData.productId =!angular.isUndefined($scope.searchData.productId)?$scope.searchData.productId :"";
	
	$scope.searchData.customerName=!angular.isUndefined($scope.searchData.customerName)?$scope.searchData.customerName :"";
	$scope.searchData.SEZZone=!angular.isUndefined($scope.searchData.SEZZone)?$scope.searchData.SEZZone :"";
	
	
	$scope.searchData.withheaderAndFooter=!angular.isUndefined($scope.searchData.withheaderAndFooter)?$scope.searchData.withheaderAndFooter :"Y";
	$scope.searchData.withcoverNote=!angular.isUndefined($scope.searchData.withcoverNote)?$scope.searchData.withcoverNote :"Y";
	
	$scope.searchData.printAndGenerate=!angular.isUndefined($scope.searchData.printAndGenerate)?$scope.searchData.printAndGenerate :"G";
	$scope.searchData.modeOfOperation =!angular.isUndefined($scope.searchData.modeOfOperation)?$scope.searchData.modeOfOperation :"G";
	//1.0.0.23 start
	//if manual invoice generation is disabled, force Print mode and disable Generate option
	if(manualInvGenDisable=='Y'){
		$scope.searchData.printAndGenerate="P";
		$scope.searchData.modeOfOperation="P";
		$scope.searchData.INVOICE_GENERATED="Y";
		$scope.isGenerateDisabled=true;//1.0.0.23
	} else {
		$scope.isGenerateDisabled=false;//1.0.0.23
	}
	//1.0.0.23 end
	
	
	$scope.searchData.INVOICE_GENERATED="";
	$scope.searchData.CONS_INVOICE_GENERATED="";
	
	$scope.select=false;
	 $scope.validateSearchCriteria=function(){
	
		 tempData.errorMessages="Errors:";
	 
	 if($scope.searchData.fromDate=="" || $scope.searchData.fromDate==null){
		 
		 
		
		 tempData.errorMessages=tempData.errorMessages+"From Date must be specified.";
		
		 
		 
	 }if($scope.searchData.toDate=="" || $scope.searchData.toDate==null){
		 
		 tempData.errorMessages=tempData.errorMessages+"\nTo Date must be specified.";
	 }
	
	 	var formDate=new Date($scope.searchData.fromDate);
	 	 formDate= Date.parse(formDate);
	    
	    var toDate = new Date($scope.searchData.toDate);
	     toDate = Date.parse(toDate);
	     
	     	     
	     //1.0.0.22 start
	     var businnessDate=new Date(hiddenBusinessDate);
	     if(invoiceDateNoOfDays==null){
	     invoiceDateNoOfDays=5;
	     }
	     
	     var daysStr="DAYS";
	     if(invoiceDateNoOfDays==0){
	     daysStr="DAY";
	     }
	     
	     var newBussinessDate=new Date(businnessDate.getTime()+invoiceDateNoOfDays*86400000);
	     
	     if($scope.searchData.fromDate!="" && hiddenBusinessDate!=null && formDate >businnessDate.getTime()){
			tempData.errorMessages=tempData.errorMessages+"\nFROM DATE CAN'T BE GREATER THAN BUSINESS DATE.";
			}

		if($scope.searchData.toDate!="" && hiddenBusinessDate!=null && toDate >newBussinessDate.getTime()){
		tempData.errorMessages=tempData.errorMessages+"\nTO DATE CAN'T BE GREATER THAN "+invoiceDateNoOfDays+" "+daysStr+" FROM THE BUSINESS DATE.";
		}//1.0.0.22 end
	  
	    if(formDate>toDate){
	    	
	    	tempData.errorMessages=tempData.errorMessages+"\nfrom date can't be greater than to date.";
	    }
	    	var differenceDate=toDate-formDate;
 
	    		var finalDays=differenceDate/86400000;
	    		if(finalDays>Number(diffreenceDay)){
	    			tempData.errorMessages=tempData.errorMessages+"\nDifference between from date and to date can't be greater than "+diffreenceDay+" Days.";
	    			
	    		}
	    	
	    		if(($scope.searchData.dueType!="" &&  $scope.searchData.dueType!="0" && $scope.searchData.dueType!=undefined)&& $scope.searchData.chargeType==""){
	    			
	    		}
                          
	    		if($scope.searchData.fromDate!="" && $scope.searchData.fromDate!=null && $scope.searchData.fromDate!=undefined){
	    		
	    		}
	    		if($scope.searchData.toDate!="" && $scope.searchData.toDate!=null && $scope.searchData.toDate!=undefined){
	    		
	    		}
	    		
	    		
 
	 if(tempData.errorMessages!="Errors:"){
	 alert(tempData.errorMessages.replace("Errors:","").toUpperCase());
	 $scope.invoiceList="";
	 $scope.coverNoteList="";
	 $scope.showPagination=true;
	 $scope.showPagination1=true; 
	 document.getElementById("coverNoteList").style.display="none";
	 $scope.validateFlag="N";
	
	 }
	 else{
		 $scope.validateFlag="Y";
	 }
	 };
	 	  
	
	 var glb_invoiceSearchStr="";
	 
	 $scope.getInvoiceListData = function(pageNoForInvoice,pageNoForCoverNote) 
	{
		 cancelSingleInvoiceStr="";
		 cancelInvoiceCoverNoteStr="";
		 if($scope.validateFlag=="N"){
			 return;
			 
		 }
     
		 
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
	
		
		
		if(invoiceType==""|| invoiceType==undefined){
			invoiceType='COVERNOTE';
		}
		
		
		if(invoiceType=='COVERNOTE'){
	invoiceSearchStr=$scope.searchData.customerId+"~"+$scope.searchData.dmId+"~"+$scope.searchData.productId+"~"+$scope.searchData.fromDate+"~"+$scope.searchData.toDate+"~"+$scope.searchData.branch+"~"+$scope.searchData.chargeType+"~"+$scope.searchData.invoiceNumber+"~"+$scope.searchData.coverNoteNumber_TEMP+"~"+$scope.searchData.hidBatchNo+"~"+$scope.searchData.dueType+"~"+$scope.batchId+"~"+pageNoForInvoice+"~"+pageNoForCoverNote+"~"+$scope.searchData.STATE+"~"+$scope.searchData.INVOICE_GENERATED+"~"+$scope.searchData.INVOICE_GENERATED+"~"+$scope.searchData.customerName+"~"+$scope.searchData.SEZZone+"~"+$scope.searchData.printAndGenerate+"~"+"END_STRING_FLAG"; 
		}
		
		if(invoiceType=='CONSOLIDATED INV'){
			invoiceSearchStr=$scope.searchData.customerId+"~"+$scope.searchData.dmId+"~"+$scope.searchData.productId+"~"+$scope.searchData.fromDate+"~"+$scope.searchData.toDate+"~"+$scope.searchData.branch+"~"+$scope.searchData.chargeType+"~"+$scope.searchData.invoiceNumber+"~"+$scope.searchData.coverNoteNumber_TEMP+"~"+$scope.searchData.hidBatchNo+"~"+$scope.searchData.dueType+"~"+$scope.batchId+"~"+pageNoForInvoice+"~"+pageNoForCoverNote+"~"+$scope.searchData.STATE+"~"+$scope.searchData.INVOICE_GENERATED+"~"+$scope.searchData.INVOICE_GENERATED+"~"+$scope.searchData.customerName+"~"+$scope.searchData.SEZZone+"~"+$scope.searchData.printAndGenerate+"~"+"END_STRING_FLAG";
			
		}
		
		
	
	$.ajax({
			  		 url: "invoiceListData_Trf.sprg?isAjaxCalling=Y", //1.0.0.1 added  isAjaxCalling=Y
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
			  			invoiceType:invoiceType,
			  			taxableFlg:$scope.searchData.taxableFlag,
			  			invoiceSearchStr: invoiceSearchStr,
			  			onLoadFlag:onLoadFlag,
			  			  },
			  			
			  			success: function(response) {
			  			if(response!=null && response!="") 
			  					{  
			  					 var jsonStr = JSON.parse(response);
			  					if(jsonStr.Status=="SessionExpired") //1.0.0.1
		  						{
		  							window.location.href = 'llm/ReLogin.jsp?unknownError=Your session has been killed. Please login again.';
		  							return;
		  						}
			  					 
			  					 if(jsonStr != null && jsonStr.PC_OUT_STATUS=="S"){ 
			  					
			  			  $scope.paginationDetailsForInvoice(jsonStr.totalNoOfPages,pageNoForInvoice);
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
			  					
			  					$scope.isaddToBatchDisabled=false;
			  					document.getElementById("generate").disabled=true;
		  						document.getElementById("cancelBatch").disabled=true;
		  						
		  						document.getElementById("selectAll").checked=false;
		  						$scope.shownoRecordFoundDiv=false;
		  						$scope.shownoRecordFoundDiv1=false;
		  						document.getElementById("pageneation").style.display='block';
								document.getElementById("pageneation1").style.display='block';
			  					
								if($scope.invoiceList=="" || $scope.invoiceList==undefined){
									
									$scope.shownoRecordFoundDiv=true;
									document.getElementById("coverNoteList").style.display='none';
									
									document.getElementById("pageneation").style.display='none';
									document.getElementById("pageneation1").style.display='none';
									document.getElementById("batchSummary").style.display='none';
										}
								
								if($scope.batchId!="" && $scope.batchId!=undefined){
									document.getElementById("batchSummary").style.display='block';
									document.getElementById("generate").disabled=false;
			  						document.getElementById("cancelBatch").disabled=false;
								}
								
								
								if($scope.coverNoteList==""){
									$scope.shownoRecordFoundDiv1=true;
									}
								if(onloadFlag=="Y"){
			  						document.getElementById("addToBatch").disabled=false;
			  					}onloadFlag="Y";
			  					if($scope.invoiceList!=""){
			  					$scope.selectAllCheckBoxSelected();
			  					}
								$scope.currentBatchGeneration();
			  					
			  					
			  					}
			  					 
			  					else{
			  						
			  						$scope.shownoRecordFoundDiv=true;
			  						
			  						
			  					 }
			  					$scope.$apply();
			  					}
			  			
			  			}});  
						
	
					};
					
					
					$scope.setDataForInvoiceTypegererated= function(val){
						
						if(val=="COVERNOTE"){
							if(document.getElementById("invoiceNoGenerated").checked==false){
								
								
								$scope.searchData.INVOICE_GENERATED="N";
								
								
							}
							else{
								if($scope.searchData.hidInvoiceType!="COVERNOTE"){
								
								
								}
								$scope.searchData.INVOICE_GENERATED="Y";
								
							}
						}else{
							
								
								
								if(document.getElementById("invoiceNoGenerated").checked== false){
									
									$scope.searchData.INVOICE_GENERATED="N";
								}
								else{
									
									
									
									$scope.searchData.INVOICE_GENERATED="Y";
								}
								
								
								
							}
							
						
					};
					
					$scope.enableDueType= function(val){
						  $scope.invoiceList="";
						  $scope.coverNoteList="";
						  $scope.currentCoverNoteList="";
						  document.getElementById("pageneation").style.display='none';
							document.getElementById("coverNoteList").style.display='none';
							 $scope.searchData.dueType="0";
							 $scope.searchData.chargeType="";
							 $scope.resetSearchCriteria();
							 
							 if($scope.searchData.modeOfOperation=="P" || $scope.searchData.printAndGenerate=="P"){
									$scope.searchData.INVOICE_GENERATED="Y";
									}
									else{
										$scope.searchData.INVOICE_GENERATED="";
									}
							 
						if(val=='CONSOLIDATED INV'){
							invoiceType=val;
							$scope.searchData.hidInvoiceType=val;
							isDueTypeDisabled="false";
							document.getElementById("dueType").disabled=false;
							document.getElementById("consolidatedInvoiceNo").disabled=false;
							
							document.getElementById("stateDiv").disabled=false;
							
							document.getElementById("coverNoteNumber").disabled=false;
							
							
							document.getElementById("consolidateInvoiceDiv").disabled=false;
							document.getElementById("coverNoteDiv").disabled=false;
							document.getElementById("taxableFlag").disabled=false;
							
							$scope.searchData.coverNoteNumber_TEMP="";
							$scope.searchData.coverNoteNumber="";
							document.getElementById("taxableFlagDiv").style.display="block";
							
							
							
						}else{
                                                         
							invoiceType=val;
							$scope.searchData.hidInvoiceType=val;
							document.getElementById("dueType").disabled=false; 
							document.getElementById("consolidatedInvoiceNo").disabled=false;
							
							$scope.searchData.consolidatedInvoiceNo='';
							document.getElementById("taxableFlag").disabled=false;
							document.getElementById("taxableFlagDiv").style.display="block";
							document.getElementById("taxableFlag").value="BOTH";
							
							document.getElementById("stateDiv").disabled=false;
							
							$scope.searchData.STATE="";
							$scope.searchData.STATE_TEMP="";
							 
							$scope.searchData.dueType="0";
							
							
							
							
							taxableFlg="";
							
							
							document.getElementById("coverNoteNumber").disabled=false;
							
							
							
							document.getElementById("consolidateInvoiceDiv").disabled=true;
							document.getElementById("coverNoteDiv").disabled=false;
							
							$scope.searchData.consolidatedInvoiceNo_TEMP="";
							$scope.searchData.consolidatedInvoiceNo="";
							
						}
						
						
					};
					
					
					
					
					
					$scope.setTaxableDropDown= function(val){
						if(val=="1200000048" || val=="1200000105"){
							$scope.searchData.taxableFlag="TAXABLE";
							document.getElementById("taxableFlag").disabled=true;
							
						}else{
							$scope.searchData.taxableFlag="BOTH";
							document.getElementById("taxableFlag").disabled=false;
						}
						
						
					};
					
					
					
					
					$scope.setChargeName= function(){
						$scope.searchData.chargeType="";
						
						
						
						};
					
					 $scope.getInvoiceListDataOnLoad = function(pageNoForInvoice,pageNoForCoverNote) 
						{
						 cancelSingleInvoiceStr="";
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
						
							
							 var onLoadFlag="Y";
							
							
							
						var invoiceSearchStr=""+"~"+"~"+"~"+"~"+"~"+"~"+"~"+"~"+"~"+"~"+"~"+"~"+"1"+"~"+"1"+"~"+""+"~"+""+"~"+""+"~"+""+"~"+""+"~"+$scope.searchData.modeOfOperation+"~"+"END_STRING_FLAG";
						
						$.ajax({
								  		 url: "invoiceListData_Trf.sprg?isAjaxCalling=Y", //1.0.0.1 added  isAjaxCalling=Y
								  		 type: "POST", 
								  		 data:{
								  			invoiceSearchStr: invoiceSearchStr,
								  			invoiceType:invoiceType,
								  			taxableFlg:taxableFlg,
								  			onLoadFlag:onLoadFlag,
								  			  },
								  			
								  			success: function(response) {
								  			if(response!=null && response!="") 
								  					{  
								  					 var jsonStr = JSON.parse(response);
								  					if(jsonStr.Status=="SessionExpired") //1.0.0.1
							  						{
							  							window.location.href = 'llm/ReLogin.jsp?unknownError=Your session has been killed. Please login again.';
							  							return;
							  						}
							  					
								  					 if(jsonStr.PC_OUT_STATUS=="S"){
								  					
								  			  $scope.paginationDetailsForInvoice(jsonStr.totalNoOfPages,pageNoForInvoice);
								  			 $scope.paginationDetailsForCoverNote(jsonStr.totalNoOfPagesForCoverNote,pageNoForCoverNote);
								  			  totalpageNo=jsonStr.totalNoOfPages;
								  			 $scope.batchId=jsonStr.BATCH_ID!=null?jsonStr.BATCH_ID:"";
								  			$scope.searchData.hidInvoiceType=jsonStr.INVOICE_TYPE;
								  			invoiceType=jsonStr.INVOICE_TYPE;
								  			$scope.searchData.modeOfOperation=jsonStr.MODE_OF_OPERATION;
								  			$scope.searchData.printAndGenerate=jsonStr.MODE_OF_OPERATION;
								  			
								  			if($scope.searchData.modeOfOperation=="P")
								  			{
								  				$scope.searchData.INVOICE_GENERATED="Y";
								  			}
								  			
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
								  					
								  					$scope.isaddToBatchDisabled=false;
								  					document.getElementById("generate").disabled=true;
							  						document.getElementById("cancelBatch").disabled=true;
							  						
							  						document.getElementById("selectAll").checked=false;
							  						$scope.shownoRecordFoundDiv=false;
							  						$scope.shownoRecordFoundDiv1=false;
							  						document.getElementById("pageneation").style.display='block';
													document.getElementById("pageneation1").style.display='block';
								  					
													if($scope.invoiceList=="" ||  $scope.invoiceList==undefined){
														
														$scope.shownoRecordFoundDiv=true;
														document.getElementById("coverNoteList").style.display='none';
														
														document.getElementById("pageneation").style.display='none';
														document.getElementById("pageneation1").style.display='none';
														document.getElementById("batchSummary").style.display='none';
														document.getElementById("selectAll").disabled=true;
															}
													
													if($scope.coverNoteList==""){
														$scope.shownoRecordFoundDiv1=true;
														document.getElementById("pageneation1").style.display='none';
														
													}
													if($scope.invoiceList!=""){
														document.getElementById("generate").disabled=false;
								  						document.getElementById("cancelBatch").disabled=false;
													}
													
													if($scope.invoiceList=="" || $scope.invoiceList== undefined){
														document.getElementById("generate").disabled=true;
								  						document.getElementById("cancelBatch").disabled=true;
													}
													
													$scope.getBatchSummay($scope.batchId);
													
													if($scope.batchNO!=""){
													document.getElementById("batchSummary").style.display='block';
													$scope.isaddToBatchDisabled=false;
								  					onloadFlag="Y";
							  						
													}
													
													if($scope.batchId=="" || $scope.batchId==undefined){
														document.getElementById("batchSummary").style.display='none';
													}
													
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
										 		 $scope.searchData.INVOICE_GENERATED="";

										 		 
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
												$scope.searchData.INVOICE_GENERATED="";
											}
										}
					


					$scope.batchNoChooser = function(KeyName,obj,rowId)
					{
					 var ParamValue = "";
					 if($scope.searchData.fromDate=="" || $scope.searchData.toDate==""){
						 alert(("from date and to date can't be blank").toUpperCase());
						 return;
						 
					 }
					 var formDateforBatchChooser=$scope.searchData.fromDate;
					 var toDateforBatchChooser=$scope.searchData.toDate;
					 var batchType=$scope.searchData.hidInvoiceType;
					 var modeOfOperation=$scope.searchData.modeOfOperation;
					  window.open("quotationMaster_Trf.sprg?KeyName="+KeyName+"&ParamValue="+ParamValue+"&label=batchNoChooser&moduleType=LMS&dependent=invoiceDtlId&objName="+obj+"&formDateforBatchChooser="+formDateforBatchChooser+"&toDateforBatchChooser="+toDateforBatchChooser+"&rowId="+rowId+"&batchType="+batchType+"&modeOfOperation="+modeOfOperation,"popDealer","width=500, height=490,top=100,left=100, location=no, maximize=yes, menubar=no, status=no, toolbar=no, scrollbars=no, resizable=no","modal=yes" );

					};
					
					
					
					
					$scope.stateChooser= function(key)
					{
						window.open("populateMaster.do?qString="+key+"&label=ST&dependant=STATE&rowNum=1&moduleType=quotation&objectNmae=searchData&controllerNameId=invoiceDtlId","popState","width=500, height=490,top=100,left=100, location=no, maximize=yes, menubar=no, status=no, toolbar=no, scrollbars=no, resizable=no","modal=yes" );
					};
					
					
					
					$scope.coverNoteNumberChooser= function(key)
					{
					
						 window.open("quotationMaster_Trf.sprg?KeyName="+key+"&ParamValue"+""+"&label=COVERNOTE&moduleType=LMS&dependent=invoiceDtlId&objName=coverNote","popDealer","width=500, height=490,top=100,left=100, location=no, maximize=yes, menubar=no, status=no, toolbar=no, scrollbars=no, resizable=no","modal=yes" );
					
					};
					
					
					$scope.consolidateInvoiceChooser= function(key)
					{
					
						 window.open("quotationMaster_Trf.sprg?KeyName="+key+"&ParamValue"+""+"&label=CONSOLIDATEINVOICE&moduleType=LMS&dependent=invoiceDtlId&objName=consolidateInvoiceLov","popDealer","width=500, height=490,top=100,left=100, location=no, maximize=yes, menubar=no, status=no, toolbar=no, scrollbars=no, resizable=no","modal=yes" );
					
					};
					
					
					
					
			
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
							
							document.getElementById("selectAll").checked=true;
						}
						}
					
					$scope.selectAllCheckBoxSelected();
					
		
		$scope.generateErrorReport = function(){
			var fromDate = (!angular.isUndefined($scope.searchData.fromDate) && $scope.searchData.fromDate!=null)?$scope.searchData.fromDate:"";
			var toDate = (!angular.isUndefined($scope.searchData.toDate) && $scope.searchData.toDate!=null)?$scope.searchData.toDate:"";
			if( fromDate == "" || toDate == ""){
				alert("FROMDATE AND TODATE MUST BE SPECIFIED");
			}
			else{
				window.open("generateErrorReport_Trf.sprg?fromDate="+fromDate+"&toDate="+toDate);
			}
		};				
		
					
		$scope.addToBatch= function(){
			
		var status="";
		var checkBoxckecked="N";
		var checkedString="";

		var glb_invoiceSearchStr = $scope.searchData.customerId +"~"+$scope.searchData.customerName + "~"+$scope.searchData.dmId + "~"+$scope.searchData.branch + "~"+$scope.searchData.productId + "~"+$scope.searchData.invoiceNumber +  "~"+$scope.searchData.fromDate + "~"+$scope.searchData.toDate + "~"+$scope.searchData.hidBatchNo + "~"+$scope.searchData.coverNoteNumber + "~"+$scope.searchData.INVOICE_GENERATED +"~"+$scope.searchData.STATE+"~"+$scope.searchData.SEZZone + "~"+$scope.searchData.dueType + "~"+$scope.searchData.chargeType + "~"+$scope.searchData.taxableFlag + "~"+$scope.searchData.allInvFlag ;
		batchStr="";  
		for(var i=0;i<$scope.invoiceList.length;i++){
		if($scope.invoiceList[i].INVOICE_SELECTED_FLG!="D" && document.getElementById("select"+i).checked==true){
		 status="A";
		 checkBoxckecked="Y";
		
		}else{
		 status="I";
		}
		
		checkedString=checkedString+$scope.invoiceList[i].INVOICE_ID+",";
		$scope.invoiceList[i].INVOICE_X_COVER_NOTE_ID =!angular.isUndefined($scope.invoiceList[i].INVOICE_X_COVER_NOTE_ID)?$scope.invoiceList[i].INVOICE_X_COVER_NOTE_ID :"";
		
		
		batchStr=batchStr+$scope.batchId+"~"+$scope.invoiceList[i].INVOICE_ID+"~"+status;
		
		batchStr=batchStr+"^";
		}
		batchStr=batchStr.slice(0, -1);
		checkedString=checkedString.slice(0, -1);
		
		if(checkBoxckecked=="Y"){
			
			if(responseFlag=="Y"){
				responseFlag="N";
				
			$.ajax({
				  		 url: "addToBatch_Trf.sprg?isAjaxCalling=Y", //1.0.0.1 added  isAjaxCalling=Y
				  		 type: "POST", 
				  		 data:{
				  			batchStr: batchStr,
				  			invoiceType: invoiceType,
				  			checkedString:checkedString,
				  			modeOfOperation:$scope.searchData.printAndGenerate,
				  			withCoverNoteFlg:$scope.searchData.withcoverNote,
				  			withHeaderFlg:$scope.searchData.withheaderAndFooter,
				  			temp_invoiceSearchStr:glb_invoiceSearchStr,
				  			  },
				  			
				  			success: function(response) {
				  			if(response!=null && response!="") 
				  					{  
				  				
				  					var jsonStr = JSON.parse(response);
				  					if(jsonStr.Status=="SessionExpired") //1.0.0.1
			  						{
			  							window.location.href = 'llm/ReLogin.jsp?unknownError=Your session has been killed. Please login again.';
			  							return;
			  						}
		  					alert( jsonStr.PC_OUT_MESSAGE);
				  					 if(jsonStr.PC_OUT_STATUS=="S"){
				  				   $scope.batchId = jsonStr.BATCH_ID!=null?jsonStr.BATCH_ID:"";
				  				
				  				  
	                                                                  
				  				if($scope.batchId!="" && $scope.batchId!=undefined)
				  					 {
				  					$scope.getBatchSummay($scope.batchId);
				  					
				  					batchStr="";
				  					$scope.isGenerateDisable=false;
				  					document.getElementById("batchSummary").style.display='block';
				  					document.getElementById("generate").disabled=false;
			  						document.getElementById("cancelBatch").disabled=false;
				  					 }
				  					
			  						
			  						if($scope.batchId!="" && $scope.batchId!=undefined){
			  							document.getElementById("consolidatedInv").disabled=true;
			  							document.getElementById("covernoteRadio").disabled=true;
			  							document.getElementById("printRadio").disabled=true;
										document.getElementById("generateRadio").disabled=true;
			  						}
			  						responseFlag="Y";
			  						
				  					}
				  					 $scope.$apply();
				  					}}});  
			$scope.changeFlag="N";
			
			}else{
				alert(("Already in Process...").toUpperCase());
	     		  return;
			}
		}
			else{
				
				alert("ATLEAST ONE INVOICE MUST BE SELECTED BEFORE CLICKING ON ADD TO BATCH.");
		}
		
		};

		
	$scope.getBatchSummay= function(batchId){


	$.ajax({
			  		 url: "getBatchSummary_Trf.sprg?isAjaxCalling=Y", //1.0.0.1 added  isAjaxCalling=Y
			  		 type: "POST", 
			  		 data:{
			  			batchId: batchId,
			  			  },
			  			
			  			success: function(response) {
			  			if(response!=null && response!="") 
			  					{  
			  					var jsonStr = JSON.parse(response);
			  					if(jsonStr.Status=="SessionExpired") //1.0.0.1
		  						{
		  							window.location.href = 'llm/ReLogin.jsp?unknownError=Your session has been killed. Please login again.';
		  							return;
		  						}
			  					 if(jsonStr.PC_OUT_STATUS=="S"){  
			  				
			  				 
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
			  		 url: "generateCoverNote_Trf.sprg?isAjaxCalling=Y", //1.0.0.1 added  isAjaxCalling=Y
			  		 type: "POST", 
			  		 data:{
			  			batchId: $scope.batchId,
			  			invoiceType: invoiceType,
			  			  },
			  			
			  			success: function(response) {
			  			if(response!=null && response!="") 
			  					{  
			  					var jsonStr = JSON.parse(response);
			  					if(jsonStr.Status=="SessionExpired") //1.0.0.1
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
			  						
			  						
			  						
			  						
			  						
			  						
			  						
			  						$('#SearchResultDivId *').prop('disabled',true);
			  						
			  					}
			  				 
			  				 
			  					
			  					
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
			  		 url: "cancelBatch_Trf.sprg?isAjaxCalling=Y", //1.0.0.1 added  isAjaxCalling=Y
			  		 type: "POST", 
			  		 data:{
			  			batchId: $scope.batchId,
			  			  },
			  			
			  			success: function(response) {
			  			if(response!=null && response!="") 
			  					{  
			  					var jsonStr = JSON.parse(response);
			  					if(jsonStr.Status=="SessionExpired") //1.0.0.1
		  						{
		  							window.location.href = 'llm/ReLogin.jsp?unknownError=Your session has been killed. Please login again.';
		  							return;
		  						}
			  					alert(jsonStr.PC_OUT_MESSAGE);
			  					if(jsonStr.PC_OUT_STATUS=="S"){ 
			  				      
			  					  $scope.batchId="";
			  					  $scope.getBatchSummay($scope.batchId);
			  					  document.getElementById("batchSummary").style.display="none";
                                  document.getElementById("consolidatedInv").disabled=false;
			  					  document.getElementById("covernoteRadio").disabled=false; 
			  					  document.getElementById("printRadio").disabled=false;
			  				
											
			  					}
			  					}}});


	    }
		
	};

	$scope.currentBatchGeneration= function(){

	$.ajax({
			  		 url: "currentBatchGeneration_Trf.sprg?isAjaxCalling=Y", //1.0.0.1 added  isAjaxCalling=Y
			  		 type: "POST", 
			  		 data:{
			  			batchId: $scope.batchId,
			  			  },
			  			
			  			success: function(response) {
			  			if(response!=null && response!="") 
			  					{  
			  					var jsonStr = JSON.parse(response);
			  					if(jsonStr.Status=="SessionExpired") //1.0.0.1
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
				  						document.getElementById("consolidatedInv").disabled=false;
				  						document.getElementById("printRadio").disabled=false;
				  						
				  						
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
				  						document.getElementById("consolidatedInv").disabled=false;
				  						document.getElementById("printRadio").disabled=false;
				  						
				  						
				  						$scope.batchId="";
				  						$scope.batchNo="";
			  						}
			  						
			  						else{
			  							
			  						}
			  						
			  					}
			  					onloadFlag="N";
			  					}
			  					}}});


	};


$scope.cancelInvoiceCoverNote = function()
{
	 var newDataList=[];
	 
	 var batchType=$scope.searchData.hidInvoiceType;
	 var bool = confirm(("Do you want to continue?").toUpperCase());
		
		if(bool)
	    {
	 
     angular.forEach($scope.coverNoteList, function(selected){
     	
         if(!selected.selected){
             
            
         }
         else {
        	 
	        	 if(cancelInvoiceCoverNoteStr!=""){
	        		 cancelInvoiceCoverNoteStr=cancelInvoiceCoverNoteStr + ","+ selected.INVOICE_COVER_NOTE_ID;
	        	 }
	        	 else {
	        		 cancelInvoiceCoverNoteStr = selected.INVOICE_COVER_NOTE_ID;
	        	 }
	        	 
	        	
	        		 
         }
         newDataList.push(selected);
     }); 
     $scope.coverNoteList = newDataList;
     if(cancelInvoiceCoverNoteStr=="" || cancelInvoiceCoverNoteStr==null){
    	 alert("PLEASE SELECT ATLEAST ONE INVOICE TO CANCEL.");
    	 return;
     }
     
     
     $.ajax({
  		 url: "deleteCoverNotes_Trf.sprg?isAjaxCalling=Y", //1.0.0.1 added  isAjaxCalling=Y
  		 type: "POST", 
  		 data:{
  			cancelInvoiceCoverNoteStr: cancelInvoiceCoverNoteStr,
  			batchType:batchType
  			  },
  			
  			success: function(response) {
  			if(response!=null && response!="") 
  					{  
  					 var jsonStr = JSON.parse(response);
  					if(jsonStr.Status=="SessionExpired") //1.0.0.1
						{
							window.location.href = 'llm/ReLogin.jsp?unknownError=Your session has been killed. Please login again.';
							return;
						}
  					cancelInvoiceCoverNoteStr="";
  			      alert((jsonStr.PC_OUT_MESSAGE).toUpperCase());
  					return;
								
								
  					
  				}}});
  
	    }
      else{
			
			return;
		}
}






$scope.cancelSingleInvoice = function()
{
	 var newDataList=[];
  
	 var bool = confirm(("Do you want to continue?").toUpperCase());
		
		if(bool)
	    {
			
	   	
    	 
    		   angular.forEach($scope.invoiceList, function(selected,rowId){
    			if(document.getElementById("select"+rowId).checked==false){
             
            
         }
    	
         else {
        	 
	        	 if(cancelSingleInvoiceStr!=""){
	        		 cancelSingleInvoiceStr=cancelSingleInvoiceStr + ","+ selected.INVOICE_ID;
	        	 }
	        	 else {
	        		 cancelSingleInvoiceStr = selected.INVOICE_ID;
	        	 }
	        	 
	        	 
	        	 
	        		 
         }
    	 
         newDataList.push(selected);
     }); 
     
     $scope.invoiceList = newDataList;
     if(cancelSingleInvoiceStr=="" || cancelSingleInvoiceStr==null){
    	 alert("PLEASE SELECT ATLEAST ONE INVOICE TO CANCEL.");
    	 return;
     }
     
     
     $.ajax({
  		 url: "cancelSingleInvoice_Trf.sprg?isAjaxCalling=Y", //1.0.0.1 added  isAjaxCalling=Y
  		 type: "POST", 
  		 data:{
  			cancelSingleInvoiceStr: cancelSingleInvoiceStr
  			
  			  },
  			
  			success: function(response) {
  			if(response!=null && response!="") 
  					{  
  				cancelSingleInvoiceStr="";
  					 var jsonStr = JSON.parse(response);
  					if(jsonStr.Status=="SessionExpired") //1.0.0.1
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











	$scope.updateCoverNotes= function(){
			var coverNoteIdStr="";
	$scope.coverNoteList =!angular.isUndefined($scope.coverNoteList)?$scope.coverNoteList :"";
	for(var i=0;i<$scope.coverNoteList.length;i++){
		if(document.getElementById("coverSelect"+i).checked==true){
		 
		
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
			  		 url: "updateCoverNotes_Trf.sprg?isAjaxCalling=Y", //1.0.0.1 added  isAjaxCalling=Y
			  		 type: "POST", 
			  		 data:{
			  			coverNoteStr: coverNoteStr,
			  			coverNoteIdStr:coverNoteIdStr,
			  			  },
			  			
			  			success: function(response) {
			  			if(response!=null && response!="") 
			  					{  
			  					var jsonStr = JSON.parse(response);
			  					if(jsonStr.Status=="SessionExpired") //1.0.0.1
		  						{
		  							window.location.href = 'llm/ReLogin.jsp?unknownError=Your session has been killed. Please login again.';
		  							return;
		  						}
			  					  
			  			
			  					$scope.upadateRemarksMessage=jsonStr.PC_OUT_MESSAGE;
			  					
			  					$scope.upadateRemarksMessage =!angular.isUndefined($scope.upadateRemarksMessage)?$scope.upadateRemarksMessage :"";
			  					if($scope.upadateRemarksMessage!=""){
			  					alert($scope.upadateRemarksMessage);
			  					}
			  				 
			  					$scope.$apply();
			  					coverNoteStr="";
			  					coverNoteIdStr="";
			  				
			  					
			  					
								
	       						
								
											
											
			  					
			  					}}});
	coverNoteStr="";
	coverNoteIdStr="";

	};
	

	
	$scope.paginationDetailsForInvoice = function(totalPageNumber,currentpage){
		
		var totalPages=totalPageNumber;
		var startIndex=document.forms[0].minPage.value;
		var totalPages=totalPageNumber;
		var endIndex=document.forms[0].maxPage.value;
		
		var windowSize=5;
		var currentPage=document.forms[0].currentPage.value;
		document.forms[0].currentPage.value=document.forms[0].currentPage.value;
		document.forms[0].minPage.value=document.forms[0].minPage.value;
		document.forms[0].maxPage.value=document.forms[0].maxPage.value;
		
		
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
			document.getElementById("remarks"+val).value ="";
			
			}
	 	     		
			};
                          
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
	 	        
	 	$scope.changeFlagEnable= function(index){
	 		
	 		if(document.getElementById("select"+index).checked==true)
	 		{
	 		$scope.changeFlag="Y";
	 		$scope.selectAllCheckBoxSel();
	 		}
	 		else{
	 			$scope.changeFlag="N";
	 			document.getElementById("selectAll").checked=false;
	 		}
		 	
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
	 	      if(invoiceType=="COVERNOTE"){
			
				if(browser.indexOf("chrome")>0)
				{	
				var targetWin = window.open("fileFunction_Trf.sprg?coverNoteNo="+coverNoteNo+"&fileName="+fileName+"&invoiceType="+invoiceType+"&batchNo="+batchNo+"&actionId=1200106855&viewHeaderFlag=Y","fileFunction","width=1000px,height=600p,top=400px,left=200px");
					targetWin.focus();
				}else{
					window.showModalDialog("fileFunction_Trf.sprg?coverNoteNo="+coverNoteNo+"&fileName="+fileName+"&invoiceType="+invoiceType+"&batchNo="+batchNo+"&actionId=1200106855&viewHeaderFlag=Y","fileFunction","width=1000px,height=600p,top=400px,left=200px");
				}
	 	      }
	 	      
	 	      if(invoiceType=="CONSOLIDATED INV"){
	 	    	 if(browser.indexOf("chrome")>0)
					{	
					var targetWin = window.open("fileFunction_Trf.sprg?coverNoteNo="+coverNoteNo+"&fileName="+fileName+"&invoiceType="+invoiceType+"&batchNo="+batchNo+"&actionId=1200108933&viewHeaderFlag=Y","fileFunction","width=1000px,height=600p,top=400px,left=200px");
						targetWin.focus();
					}else{
						window.showModalDialog("fileFunction_Trf.sprg?coverNoteNo="+coverNoteNo+"&fileName="+fileName+"&invoiceType="+invoiceType+"&batchNo="+batchNo+"&actionId=1200108933&viewHeaderFlag=Y","fileFunction","width=1000px,height=600p,top=400px,left=200px");
					} 
	 	    	  
	 	    	 
	 	      }

	 		};
	
	 $scope.downloadReconReport = function(batchId,batchNo)
	 {
		window.open("downloadReconReport_Trf.sprg?batchId="+batchId+"&batchNo="+batchNo);
	 }
	
	 		
               $scope.fileFunctionForBulkPrinting= function(fileName,invoiceType,modeOfOperation,fileId,path)
               {
            	   
            	 
            	   var a = document.createElement('a');
            	   a.href ="downloadReportServlet?fileName="+fileName+"&path="+path+"&lable=BULK_INVOICE";
            	   document.body.appendChild(a);
            	   a.click();
            	   document.body.removeChild(a);
               
               };
	 		
	 		
	 	
               
	 	$scope.checkAll= function(){
	 		

	 		    angular.forEach($scope.invoiceList, function (obj,i)
	 		    {
			 		  
			 		  
				 		       obj.selected = $scope.select;
				 		       if($scope.select==false)
				 		       {
				 		    	  document.getElementById("select"+i).checked=false;
				 		    	  $scope.changeFlag="N";
				 		       }
				 		       else{
				 		    	   if(obj.INVOICE_SELECTED_FLG!="D")
				 		    	   {
				 		    	    $scope.changeFlag="Y";
				 		    	   }
				 		       }
			 		 
	 		    
	 		    });
	 		   
	 		  };
	 		  
	 	
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
				var targetWin = window.open("fileFunction_Trf.sprg?batchNo="+batchNo+"&actionId=1200106857&viewHeaderFlag=Y","fileFunction","width=1000px,height=600p,top=400px,left=200px");
					targetWin.focus();
				}else{
					window.showModalDialog("fileFunction_Trf.sprg?batchNo="+batchNo+"&actionId=1200106857&viewHeaderFlag=Y","fileFunction","width=1000px,height=600p,top=400px,left=200px");
				}


	 		}
	
	$scope.getInvoiceListDataOnLoad("1","1");
		
	
});


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

