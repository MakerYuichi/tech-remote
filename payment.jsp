<!-- #  HEADER INFO
#  File NAME 	: payment.jsp
#  PURPOSE		: 
#  INITIAL VERSION : 1.0.0
#  **********************************************************************************************************************************
#  VERSION NO   	UPDATED BY       UPDATED ON      REASON FOR CHANGE
#  1.0.0.0.1	   	Mack tyagi		 1-April-2016	 Pagination and instrument Type Based enable disable Instrument no, Instrument Date
#  1.0.0.0.2	   	Ranjan Singh	 24-May-2016	 Bug -33730
#  1.0.0.3  	   	Ravi		     04-Jun-2018	 CR:-Add field for advance payment and remarks for
   1.0.0.4			Nishant			 06-Jun-2018	 Alignment correct 
   1.0.0.5			Nishant 		 6-JUN-2018	 	 header change for report
   1.0.0.6			Nishant			 30-Jul-2018	 UAT Bug Id: 60944 inst Id validation
   1.0.0.7		    Apurva		     29-Nov-2019	 changes made for response flag(click again on save give popup)
   1.0.0.8		   Apurva		     14-june-2019		CODE COMMENTED FOR SIT BUG 73306
   1.0.0.9  	   	Ravi		     18-Sep-2019	 add screenid for save and saveexit.
   1.0.0.10  	   	Ravi		     15-Oct-2019	 "EITHER ALL CHEQUES ARE USED.." popup is displaying multiple times.
   1.0.0.11  	   	Ravi		     22-Jan-2020	 Code change to add new decision making option 
   1.0.0.12			Sunny			 30-Mar-2020	 Tooltip added for customer Name and issue in favour of fields
   1.0.0.13			Sunny			 28-Aug-2020	 Code Changes to add payment date filter, select all functionality and reject functionality on maker
   1.0.0.14			Sunny			 13-Oct-2020	 Code changes for Prod bug : 90180
   1.0.0.15			Sunny			 13-Oct-2020	 Code changes for passing product in bank LOV.
   1.0.0.16			Sunny			 28-Oct-2020	 Mandatory condition for payment from and to date changed.
   1.0.0.17			Sunny			 28-Oct-2020	 Branch and PAN filter removed.
   1.0.0.18			Sunny			 20-Nov-2020	 Bug 92017 - OPCO/FINCO Bank Account number not reflecting correctly on payment maker screen.
   1.0.0.19			Krishna Ravi     29-Oct-2021     CR On Payment Maker Screen  to add TDS amount and values from backend.
	1.0.0.20		HEMANT KUMAR     04-jAN-2022     104884 - Error in payment maker stage||DMFL02000034382 -Inst No Special char
	1.0.0.21		Hemant Kumar	 17-Nov-2022	 Payment Author Screen Blank issue
	1.0.0.22		Hemant Kumar	 27-Jan-2023	 Bug 119097 - The branch/cashpoint button is coming over the username field at the login screen.
	1.0.0.23		Hemant Kumar	 30-May-2023	CR - OPS enhancement on vendor payments screen
	1.0.0.24        Tanisha Agarwal  21-Aug-2023    Payment Enhancement Observations
	1.0.0.25        Narottam Biswal  18-Sept-2023   OPS Enhancement New Changes
	1.0.0.26		Priyanka Soni		03-Jan-2024		Remove product condition from QM_GEO_X_BANK and branches
	1.0.0.27        Tanisha Agarwal     01-Feb-2024     Orix bank should be lessor bank
	1.0.0.28		Tanisha Agarwal		04-June-2024    Instrument no. updation on DM PDE
	1.0.0.29        Narottam Biswal     11-Nov-2024      coloumn name change and btn alignment
	1.0.0.30        Narottam Biswal     21-Feb-2025      Vendor Payment Integration(Disbale instrument number and instrument date in case of NEFT/RTGS)on Payment Maker and Refund screen.
	1.0.0.31        Sanchi Agarwal      02-Sep-2026      CR - Added H2H Status, Post Approval Rejection Source and Post Approval Rejection User columns on Vendor Payment Viewer screen.
#  ********************************************************************************************************************************** -->



<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ include file="../include/includeTld.inc"%>
<!DOCTYPE html>
<html:html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
	<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
	<META HTTP-EQUIV="Expires" CONTENT="0">
	<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<!--Below changes in two lines done by Ravikant -->
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	
	<link href="css/bootstrap.css" rel="stylesheet" type="text/css">
	<link href="css/font-awesome.css" rel="stylesheet">
	<link href="css/newlos.css" rel="stylesheet" type="text/css">
	<link href="css/responsive.css" rel="stylesheet">	
	<script type="text/javascript" src="js/jquery.js"></script>
  <style type="text/css">  
  form[name="paymentForm"] #listData table tr:nth-of-type(2) input[value="..."].blueBotton {
    top: 0px !important;
}
form[name="paymentForm"] #listData input[value="..."].blueBotton, form[name="paymentForm"] input[value="..."].blueBotton {
    top: 0px;
}
            .pg-normal {  
                text-decoration: none;  
                 cursor: pointer;
                 FONT-SIZE:11px;
				/*color:#8B0000;*/
				color:#2578e8;
				font-weight:BOLD;
				font-family:verdana;
				font: Verdana;  
            }  
             .pg-selected {  
               text-decoration: underline;  
                 cursor: pointer; 
                 FONT-SIZE:11px;
				/*color:#8B0000;*/
				color:#2578e8;
				font-weight:BOLD;
				font-family:verdana;
				font: Verdana; 
            }  
             .header  
            {  
                background-color:gray;  
            }  
        </style>  


	<script type="text/javascript" src="js/validation.js"></script>
	<script type="text/javascript" src="js/checkSessionAvailability.js"></script>
	<script type="text/javascript" src="js/genericValidationlos.js"></script>
	<script type="text/javascript" src="js/mapobject.js"></script>
	<script type="text/javascript" src="js/collection.js"></script>
	
	<script type="text/javascript" src="js/CalendarPopup.js"></script>
	<script type="text/javascript" src="js/PopupWindow.js"></script>
	<script type="text/javascript" src="js/date.js"></script>
	<script type="text/javascript" src="js/AnchorPosition.js"></script>
	<!-- <script type="text/javascript" src="js/paging.js"></script>  --> 			<!-- 1.0.0.0.1 -->
	<script type="text/javascript">document.write(getCalendarStyles());</script>
	<script type="text/javascript" ID="js19">
	
		var cal19 = new CalendarPopup();
		cal19.showYearNavigation();
		cal19.showYearNavigationInput();
	</script>
	<script type="text/javascript" src="js/dateValidation.js"></script>
	<script type="text/javascript" src="js/jquery.js"></script>
			
<script>
	  var statusAuth="";
	  var countNew=0;
	  var rowId = 0;
	  var listRowCount = 0;
	  var selectedRowCount = 0; 
	  var rowsDataXml = "";
	  var prospectArray = new Array();
	  var ino = parseInt("0");
	  var prospectString = "";
	  var flag="N";
	  var cleanupList = "";
	  var responseFlag="Y";//1.0.0.7
	    var instrumentTypeArr = new Array();
		var entityTypeArr = new Array();
		var entityNameArr = new Array();
		var rejectReasonArr = new Array();//1.0.0.13
		var gbl_Acc_Cheque_Flag = "N";//1.0.0.10 added
		var rejectreasonMasterObj = ${rejectReasonMasterGson};
		//rejectreasonMasterObj= JSON.parse(rejectreasonMasterObj);
		
		function instNoValueChooser(obj, no)
		{	
			var tName = "QM_CHEQUE_BOOK";
				var userId='<bean:write name="userId" ignore="true" />';
				tName = tName+"~"+userId;
				var depd=document.getElementById("instType"+no).value;
				window.open("populateMasterSearch.do?actionPerformed=display&qString="+tName+"&label=INSTRUMENT NO.&dependant=instNo"+no+"&rowNum=1","popBank","width=500, height=490,top=100,left=100, location=no, maximize=yes, menubar=no, status=no, toolbar=no, scrollbars=no, resizable=no","modal=yes" );
				document.getElementById("instNo"+no+"_temp").focus();
	   }
	   
	   function concatCleanupString(no)
	   {
	   			var prospect = document.getElementById("prospectId"+no).innerHTML;
				var  disbursal_id=document.getElementById("disbDtlId"+no).value;
				var cleanUpString = prospect+"~PAYMENT~"+disbursal_id;
				cleanupList = cleanupList + cleanUpString + "^";
				
				var xmlhttp;
					if (window.XMLHttpRequest)
					  {
					  		xmlhttp=new XMLHttpRequest();
					  }
					else
					  {
					  	xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
					  }
					
					xmlhttp.open("GET","BankDetailsServlet?concatCleanupString="+cleanupList+"&time="+Date()+"",false);
					xmlhttp.onreadystatechange  = function()
						  {
						 if (xmlhttp.readyState == 4) 
								{
								     if(xmlhttp.status == 200) 
								     {
								       	 var message = xmlhttp.responseText;
								       	 //alert(message);
								     }
								     else 
								     {
								        alert(("Error during AJAX call. Please try again: "+xmlhttp.status).toUpperCase());
								     }
								 }
						  };
					xmlhttp.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
					xmlhttp.send();
				
	  }
	   
	    function fetchBankDetails(no)
	   {
	        var obj = document.getElementById("instNo"+no);
	        if(obj.value!="")
	        {
	   			var xmlhttp;
					if (window.XMLHttpRequest)
					  {
					  		xmlhttp=new XMLHttpRequest();
					  }
					else
					  {
					  	xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
					  }
					
					xmlhttp.open("GET","BankDetailsServlet?chequeId="+obj.value,false);
					xmlhttp.onreadystatechange  = function()
						  {
						 if (xmlhttp.readyState == 4) 
								{
								     if(xmlhttp.status == 200) 
								     {
								       	var bankDetail = xmlhttp.responseText.split("~");
								      	 document.getElementById("bankId"+no).value = bankDetail[0];
								    	 document.getElementById("bankId"+no+"_temp").value = bankDetail[1];
								    	 document.getElementById("branchId"+no).value = bankDetail[2];
								    	 document.getElementById("branchId"+no+"_temp").value = bankDetail[3];
								    	 document.getElementById("accountNo"+no).value = bankDetail[4];
								    	 document.getElementById("accountNo"+no+"_temp").value = bankDetail[5];
								    	 document.getElementById("instNo"+no+"_temp").readonly = false;
								    	 document.getElementById("instNo"+no+"_temp").disabled = true;
								    
								     }
								     else 
								     {
								        alert(("Error during AJAX call. Please try again: "+xmlhttp.status).toUpperCase());
								     }
								 }
						  };
					xmlhttp.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
					xmlhttp.send();
	   		}
	   }
		
		function bankValueChooser(obj, no)
		{	
			var geoId =  document.getElementById("branch"+no).value;
			var instrumentType = document.getElementById("instType"+no).value;
			var product = document.getElementById("product"+no).value;//added by 1.0.0.15
			if(geoId == "" || geoId == "0")
			{				
				alert(("Please select Branch.").toUpperCase());
				return;
			}
			if(instrumentType == "" || instrumentType.length == 0 || instrumentType == "0")
			{
				alert(("Please select Instrument Type.").toUpperCase());
				return;
			}
			
			var bankType = 'BANK';
			if(instrumentType == "1000000002")
				bankType = 'CASH';
				
			var tName = "QM_GEO_X_BANK~"+geoId+" AND BK.BANK_TYPE = '"+bankType+"'";//1.0.0.26 +" AND NVL(ENTITY, 'X') = '"+product+"'";//changed by 1.0.0.15
			window.open("populateMaster.do?qString="+tName+"&label=Bank&dependant=bankId"+no+"&selectedName=&rowNum=1&cleanDependentItems="+"T~branchId"+no+",T~branchId"+no+"_temp,T~accountNo"+no+",T~accountNo"+no+"_temp","popBank","width=500, height=490,top=100,left=100, location=no, maximize=yes, menubar=no, status=no, toolbar=no, scrollbars=no, resizable=no","modal=yes" );
		}
		
		
		function branchValueChooser(obj, no)
		{				
			var geoId =  document.getElementById("branch"+no).value;
			var rowBankId = document.getElementById("bankId"+no).value;
			var product = document.getElementById("product"+no).value;//added by 1.0.0.18
			if(geoId == "" || geoId == "0" || rowBankId == "" || rowBankId == "0")
			{				
				alert(("Please select Bank Name.").toUpperCase());
				return;
			}
			var tName = 'QM_GEO_X_BANKBRANCH';	
			var searchCond = geoId+" AND MAPP.BANKID = '"+rowBankId+"'"; //1.0.0.4+" AND NVL(ENTITY, 'X') = '"+product+"'";//changed by 1.0.0.18
			var paramList = tName+"~"+searchCond;					 		 
			window.open("populateMaster.do?qString="+paramList+"&label=Branch&dependant=branchId"+no+"&rowNum=1&cleanDependentItems="+"T~accountNo"+no+",T~accountNo"+no+"_temp","popLocation","width=500, height=490,top=100,left=100, location=no, maximize=yes, menubar=no, status=yes, toolbar=no, scrollbars=yes, resizable=no","modal=yes" );
			document.getElementById("branchId"+no+"_temp").focus();
		
		}
		
		
		function accountValueChooser(obj, no)
		{		
			document.getElementById("accountNo"+no+"_temp").value = "";		
			var geoId =  document.getElementById("branch"+no).value;
			var rowBankId = document.getElementById("bankId"+no).value;
			var rowBranchId = document.getElementById("branchId"+no).value;
			var product = document.getElementById("product"+no).value;//added by 1.0.0.18
			if(geoId == "" || geoId == "0" || rowBankId == "" || rowBankId == "0" || rowBranchId == "" || rowBranchId == "0")
			{				
				alert(("Please select Bank Name and Branch Name.").toUpperCase());
				return;
			}
			var tName = 'QM_GEO_X_BANKBRANCH_ACC';	
			var searchCond = geoId+" AND MAPP.BANKID = '"+rowBankId+"' AND MAPP.BANKBRANCHID = '"+rowBranchId+"'";//1.0.0.4+" AND NVL(ENTITY, 'X') = '"+product+"'";//changed by 1.0.0.18
			var paramList = tName+"~"+searchCond;	
			gbl_Acc_Cheque_Flag="Y";	//1.0.0.10 added			 		 
			window.open("populateMaster.do?qString="+paramList+"&label=Account&dependant=accountNo"+no+"&rowNum=1","popLocation","width=500, height=490,top=100,left=100, location=no, maximize=yes, menubar=no, status=yes, toolbar=no, scrollbars=yes, resizable=no","modal=yes" );
			document.getElementById("accountNo"+no+"_temp").focus();
			//alert(document.getElementById("instType"+no).value);
			if(document.getElementById("instType"+no).value=='1000000001'){
			 //document.getElementById("instNo"+no+"_temp").disabled=true;
				 document.getElementById("instNo"+no+"_temp").disabled=false;//1.0.0.28
			}else{
			 document.getElementById("instNo"+no+"_temp").disabled=false;
			}
			//alert( "instNo"+no+"_temp ="+document.getElementById("instNo"+no+"_temp").disabled);
		}
		
		
		
		function filterAllocatedCheque(no){
	
	    var rowBankId = document.getElementById("bankId"+no).value;
		var rowBranchId = document.getElementById("branchId"+no).value;
		var accountNo = document.getElementById("accountNo"+no).value;
	    var ID=document.getElementById("payment_Row"+no).value;
	   // alert("testing"+"ID"+ID+"rowBankId"+rowBankId+"rowBranchId"+rowBranchId+"accountNo"+accountNo);
 	    
	 var paymentTable = document.getElementById("addRow");
		        var rowCount = paymentTable.rows.length;
		   
		  var instrumentNo='0';
		  
		    for(var i=1; i<=rowCount; i++)
		     {
				
			var  table_id=document.getElementById("payment_Row"+i).value;
		    var  table_inst_no=  document.getElementById("instNo"+i).value;
		    var  table_inst_no_int=parseInt(table_inst_no);
			var  table_bankId_no= document.getElementById("bankId"+i).value;
			var  table_branchId_no=document.getElementById("branchId"+i).value;
			var  table_acountNo = document.getElementById("accountNo"+i).value;
			//var  table_accountNo_no=document.getElementById("accountNo"+i).value;
					  // document.getElementById("ifscCode"+i).disabled = true;
			    //alert("table_id"+table_id+"table_inst_no"+table_inst_no+"table_bankId_no"+table_bankId_no+"table_branchId_no"+table_branchId_no+"table_acountNo"+table_acountNo);
				if(ID!=table_id){
				if((rowBankId==table_bankId_no) && (rowBranchId==table_branchId_no) && (accountNo==table_acountNo)){
				if((instrumentNo!="") || (instrumentNo!=null) (instrumentNo!='')){
				if((parseInt(instrumentNo))<table_inst_no_int){
				instrumentNo=table_inst_no;
				//alert("instrumentNo>>>>>>>>>>>>>>>>>>>>>"+instrumentNo);
				}
				}
				
				}else{
				
				//alert("test");
				}
			}else{
			//alert("ID!=table_id");
			}	  
				
				}
		//alert("instrumentNo"+instrumentNo);
		
		return instrumentNo;
		}
		function displayNextCheque(no){
		//alert("rafsfs");
		var  instNo=  document.getElementById("instNo"+no).value;
		
		if((instNo==null) || (instNo=="")){
		
		var recentMaxinstNumber=filterAllocatedCheque(no);
		//alert("recentMaxinstNumber"+recentMaxinstNumber);
		var rowBankId = document.getElementById("bankId"+no).value;
		var rowBranchId = document.getElementById("branchId"+no).value;
		var instType = document.getElementById("instType"+no).value;
		var accountNo = document.getElementById("accountNo"+no).value;
		var dispalyaccount=document.getElementById("accountNo"+no+"_temp").value;
	
		if(instType==="1000000001"){
		 var url="PaymentInfo";
			 
		 var params="bank="+rowBankId+"&branch="+rowBranchId+"&accNo="+dispalyaccount+"&nextCheq="+recentMaxinstNumber;
		   
		
	
		 getRateValueForRateReset(url,params,no);
		 }else{
		 
		 
		 }
		}
		}
		
		
		
		
		
		
		
		var xmlhttpexistingdetails;
		function getRateValueForRateReset(url,params,no)
		{
		// alert("getRateValueForRateReset"); 
			 if (window.XMLHttpRequest)
			    {  xmlhttpexistingdetails = new XMLHttpRequest(); }
			    else
			    {  
			    xmlhttpexistingdetails = new ActiveXObject("Microsoft.XMLHTTP"); 
			    }    
			    
			    xmlhttpexistingdetails.onreadystatechange=function() {
			    if(xmlhttpexistingdetails.readyState==4)
		  	{
				var allresponsetext = xmlhttpexistingdetails.responseText;	 
		        document.getElementById("instNo"+no).value=allresponsetext;
		        //alert("check"+document.forms[0].totalROI.value);
		    }
			    
			    
			    };	
			    xmlhttpexistingdetails.open("GET",url+"?"+params,false);
			    xmlhttpexistingdetails.setRequestHeader("Content-type","application/x-www-form-urlencoded");
			    xmlhttpexistingdetails.send(null);
			    //alert("xmlhttpexistingdetails.open");
			    //setMoratoriumDate();
		}
		
		function setcheque(no)
		 {  
			
		 if(xmlhttpexistingdetails.readyState==4)
		  	{
				var allresponsetext = xmlhttpexistingdetails.responseText;	 
		        document.getElementById("instNo"+no).value=allresponsetext;
		       
		    }
		  }
		
	
	
	
	
	
	
	
	
		
		
		
		
		
		
		
		<%int index=0;%>
		
		
		 
		<logic:notEmpty name="colEntityType" >
		<logic:iterate id="itrEntityType" name="colEntityType" >
       		 entityTypeArr[<%=index%>]=new Array(2);
   			 entityTypeArr[<%=index%>][0]='<bean:write name="itrEntityType" property="masterName" />';	        
			 entityTypeArr[<%=index++%>][1]='<bean:write name="itrEntityType" property="masterId" />';
  		</logic:iterate>
		</logic:notEmpty>
		
		 <%index=0;%>
		<logic:notEmpty name="colEntityName" >
		<logic:iterate id="itrEntityName" name="colEntityName" >
       		 entityNameArr[<%=index%>]=new Array(3);
   			 entityNameArr[<%=index%>][0]='<bean:write name="itrEntityName" property="masterId" />';	        
			 entityNameArr[<%=index%>][1]='<bean:write name="itrEntityName" property="masterSchemeId" />';
			 entityNameArr[<%=index++%>][2]='<bean:write name="itrEntityName" property="masterSchemeName" />';
  		</logic:iterate>
		</logic:notEmpty>
		
		<%index=0;%>
		<logic:notEmpty name="colInstrumentListDTOs" >
		<logic:iterate id="instrumentType" name="colInstrumentListDTOs" >
       		 instrumentTypeArr[<%=index%>]=new Array(2);
   			 instrumentTypeArr[<%=index%>][0]="<bean:write name="instrumentType" property="masterName" />";	        
			 instrumentTypeArr[<%=index++%>][1]="<bean:write name="instrumentType" property="masterId" />";
  		</logic:iterate>
		</logic:notEmpty>
		
		//add start 1.0.0.13

		for(var i = 1;i<rejectreasonMasterObj.rejectReasonMaster.length;i++){
			var obj = rejectreasonMasterObj.rejectReasonMaster[i];
			rejectReasonArr[i]=new Array(2);
			rejectReasonArr[i][0] = obj.NAME;
			rejectReasonArr[i][1] = obj.ID
		}
		
		<%-- <%index=0;%>
		<logic:notEmpty name="rejectReasonMaster" >
		<logic:iterate id="rejectReason" name="rejectReasonMaster" >
			 rejectReasonArr[<%=index%>]=new Array(2);
			 rejectReasonArr[<%=index%>][0]="<bean:write name="instrumentType" property="masterName" />";	        
			 rejectReasonArr[<%=index++%>][1]="<bean:write name="instrumentType" property="masterId" />";
  		</logic:iterate>
		</logic:notEmpty> --%>
		//add end 1.0.0.13
		
		function checkForm()
		{
			var objToFocus=null;
			tempData.errorMessages="Errors:";
			
			var paymentTable = document.getElementById("addRow");
		    var rowCount = parseInt(paymentTable.rows.length,10)-parseInt(1,10);
		   
		   //  alert("checkForm="+prospectArray);
		    for(var i=1; i<=rowCount; i++)
		     {
				
				
/* 				for(var z=0; z<prospectArray.length; z++)
				{ 
				  
				    if(prospectArray[z] == document.getElementById("prospectId"+i).innerHTML)
				    { 
				       if(!document.getElementById("payment_Row"+i).checked)
				       {
				          tempData.errorMessages=tempData.errorMessages + "\n" + prospectArray[z]+  " :all disbursal rows must be checked";
				          return;
				       }
				    }
				} */
				     if(document.getElementById("payment_Row"+i).checked )
				     {
				      
				     validate(document.getElementById("instType"+i),'CMV','Inst Type');
					   
					
					if(document.getElementById("instType"+i)[document.getElementById("instType"+i).selectedIndex].text != "CASH")
					{
					//	alert("instType :"+document.getElementById("instType"+i)[document.getElementById("instType"+i).selectedIndex].text );
						<logic:equal name ="activityInfo" property="activityMode" value ="M">
						if(document.getElementById("approveCheck").checked){
							//1.0.0.6 start	
							if(document.getElementById("instType"+i)[document.getElementById("instType"+i).selectedIndex].text == "CHEQUE")
							{
							//validate(document.getElementById("instNo"+i),'TMV,ANV','Inst No');//1.0.0.28
							   validate(document.getElementById("instNo"+i+ "_temp"),'TMV,ANV','Instrument No.');//1.0.0.28 
							}  
							//1.0.0.6 end
							if(document.getElementById("instType"+i).value !="1000000005" && document.getElementById("instType"+i).value!="1000000006"){//1.0.0.30
							 validate(document.getElementById("instDate"+i),'TMV','Inst date'); 
							}
							   validate(document.getElementById("favourOf"+i),'TMV','Favour Of');
							  
							   validate(document.getElementById("bankId"+i+"_temp"),'TMV','Bank');
							   validate(document.getElementById("branchId"+i+"_temp"),'TMV','Branch');
							   validate(document.getElementById("accountNo"+i+"_temp"),'TMV,ANV','Account No');  // ravi
							    validate(document.getElementById("ifscCode"+i),'TMV','IFSC code');
								validate(document.getElementById("micrCode"+i),'TMV','MICR code');
								//validate(document.getElementById("ifscCode"+i),'IFSV','IFSC code');
								validate(document.getElementById("micrCode"+i),'MICV','MICR code');
						}
					    </logic:equal>
					    
					    <logic:equal name ="activityInfo" property="activityMode" value ="V">					//1.0.0.0.1
					   		validate(document.getElementById("instNo"+i),'TMV,TNV','Inst No');
					   		validate(document.getElementById("instDate"+i),'TMV','Inst date');					
					    </logic:equal>
					  
					}
					   // validate(document.getElementById("ifscCode"+i),'TMV','Ifsc Code');
					    <logic:equal name ="activityInfo" property="activityMode" value="A">
					       validate(document.getElementById("aRemarks"+i),'TMV','Author Remarks');
					    </logic:equal>
					     <logic:equal name ="activityInfo" property="activityMode" value="M">
					     if(document.getElementById("approveCheck").checked){
					       validate(document.getElementById("mRemarks"+i),'TMV','Maker Remarks');
					     }
					     else if(document.getElementById("payment_Row"+i).checked && document.getElementById("rejectCheck").checked){
					    	 validate(document.getElementById("rejectReason"+i),'DDMV','Reject Reason');
					     }
					    </logic:equal>
					    
					    
					    
					    // Comparison for same bank ,branch, Account no, instrument no.in case of Cheque.
					    
					    var bank_type="";
					    var acc_type="";
					    var branch_type="";
					    var inst_no="";
					    
					    var bank_type_temp="";
					    var acc_type_temp="";
					    var branch_type_temp="";
					    var inst_no_temp="";
					    
					   /* 1.0.0.23 start */
					    /* 1.0.0.8 start */
					    <logic:equal name ="activityInfo" property="activityMode" value="A">
					    var rejCheckedVal = document.forms[0].arSendBack.checked;
					    var sendBackMkrCheckedVal = document.forms[0].arReject.checked;
	 					 var diff = dateDiff('<bean:write name ="llmSessionUserInformationDTO" property="businessDate"/>' , document.getElementById("instDate"+i).value );
	 					    if(diff && !rejCheckedVal && !sendBackMkrCheckedVal)
	 					    {
	 					    	tempData.errorMessages = tempData.errorMessages+"\nInstrument Date must be less than or equal to business date.";
						     	return;
	 					    } 
 					    </logic:equal>
						/* 1.0.0.8 END */
						
						//start by 1.0.0.25
						<logic:equal name ="activityInfo" property="activityMode" value="M">
						if (document.getElementById("approveCheck").checked) {
						var insDate=new Date(document.getElementById("instDate"+i).value);
 					    var payDate=new Date(document.getElementById("paymentDate"+i).value);
 					    if(payDate>insDate)
 					    	{
 					    	tempData.errorMessages = tempData.errorMessages+"\nInstrument Date should be equal to or greater than payment date.";
 					    	}
						}
						</logic:equal>
 					 //end by 1.0.0.25
						
						/* 1.0.0.8 start */
						/* 1.0.0.8 start */
					    <logic:equal name ="activityInfo" property="activityMode" value="M">
					    var rejCheckedVal2 = document.getElementById("rejectCheck").checked;
	 					 var diff = dateDiff('<bean:write name ="llmSessionUserInformationDTO" property="businessDate"/>' , document.getElementById("instDate"+i).value );
	 					     if(diff && !rejCheckedVal2)
	 					    {
	 					    	tempData.errorMessages = tempData.errorMessages+"\nINSTRUMENT DATE CAN AT MOST BE "+document.getElementById("prevNoOfDays").value+" DAYS BEFORE AND "+document.getElementById("noOfDays").value+" DAYS AHEAD OF BUSINESS DATE.";
						     	return;
	 					    }  
 					    </logic:equal>
 					    /* 1.0.0.8 end */
						/* 1.0.0.23 END */

						
					    if(document.getElementById("instType"+i).value=="1000000001")
					    {
					      bank_type=document.getElementById("bankId"+i).value;
					      acc_type=document.getElementById("accountNo"+i).value;
					      branch_type=document.getElementById("branchId"+i).value;
					      inst_no=document.getElementById("instNo"+i).value;
					      
					      for(var z=i+1; z<=rowCount;z++)
					      { 
					         bank_type_temp=document.getElementById("bankId"+z).value;
					     	 acc_type_temp=document.getElementById("accountNo"+z).value;
					     	 branch_type_temp=document.getElementById("branchId"+z).value;
					     	 inst_no_temp=document.getElementById("instNo"+z).value;
					     	 
					     	 if((bank_type==bank_type_temp) && (acc_type==acc_type_temp) && (branch_type==branch_type_temp) && (inst_no==inst_no_temp))
					     	 {
					     	   //flag="Y";
					     	   //alert(("Bank,Branch,Account No.,Instrument No.can't be same for two or more prospects.").toUpperCase());
					     	  // tempData.errorMessages = tempData.errorMessages +"\n"+"Bank,Branch,Account No.,Instrument No.can't be same for two or more prospects.";
					     	   //return;
					     	 }
					     	 
					      }
					    }
					    
					  
					    
					 
										  
		    }
				
		    }
			    
		 
		  
		  
	
		}
		
		// 1.0.0.9 start
			function save()
		    {
		        saveData("save");
		    }
		  function saveExit()
			{
			    saveData("saveExit");
			}
			
			 function saveData(saveType)
			{  // 1.0.0.9 end
		      if(checkRowChecked())
		      {
		      generateXml();
		      checkForm();
		      var decisionFlag = "M";
		      <logic:equal name ="activityInfo" property="activityMode" value="A">
							if(!document.forms[0].arReject.checked && !document.forms[0].arApprove.checked && !document.forms[0].arSendBack.checked) // 1.0.0.11
								{
								  alert(("Please Select Approval Decision.").toUpperCase());
								  return;
								
								}
					 if(document.forms[0].arApprove.checked)
					 {
					   decisionFlag = "A";
					 }	
					  if(document.forms[0].arReject.checked)
					 {
					   decisionFlag = "R";
					 }	
					 if(document.forms[0].arSendBack.checked) // 1.0.0.11
					 {
					   decisionFlag = "B";
					 }	
										
		      </logic:equal>
		      
		        <logic:equal name ="activityInfo" property="activityMode" value="M">
							if(!document.forms[0].mrSendToAuthor.checked && !document.forms[0].mrReject.checked )
								{
								   alert(("either send to author or reject must be checked.").toUpperCase());
								   return;
							
								}
							
							if(document.forms[0].mrReject.checked)
							 {
							   decisionFlag = "R";
							 }	
							  if(document.forms[0].mrSendToAuthor.checked)
							 {
							   decisionFlag = "P";
							 }
				
		      </logic:equal>
			
			   if(tempData.errorMessages!="Errors:")
				{
					alert((tempData.errorMessages).replace("Errors:","").toUpperCase());
					return;
				}
				var bool = confirm(("Proceeding to save changes.").toUpperCase());
				 
				 if(bool)
				 {
				    setApprovalDecision();
				    
				     for(i=1;i<=rowId;i++)
		              { 
		                removeCommaOfObject(document.getElementById('amount'+i));
		              }
				    
				    
				   <logic:notEmpty name ="activityInfo">
					<logic:notEmpty name ="activityInfo" property ="activityMode" >
						<logic:notEqual name ="activityInfo" property="activityMode" value ="AA">
							<logic:equal name ="activityInfo" property="activityMode" value ="A">
				     document.forms[0].arApprovalDecision.value=decisionFlag;
				  //  document.getElementById("arApprovalDecision").value=document.forms[0].arApprovalDecision.value;
				 	</logic:equal>
						</logic:notEqual>
					</logic:notEmpty>
				</logic:notEmpty>
				
				<logic:notEmpty name ="activityInfo">
				<logic:notEmpty name ="activityInfo" property ="activityMode" >
					<logic:notEqual name ="activityInfo" property="activityMode" value ="AA">
						<logic:equal name ="activityInfo" property="activityMode" value ="M">
			     document.forms[0].mrApprovalDecision.value=decisionFlag;
			 	</logic:equal>
					</logic:notEqual>
				</logic:notEmpty>
			</logic:notEmpty>
			
				   if(responseFlag=="Y"){//1.0.0.7
				   responseFlag="N";//1.0.0.7
				    document.forms[0].action="paymentAction.do?actionPerformed="+saveType+"&decisionFlag="+decisionFlag+"&screenId=<%=request.getParameter("screenId")%>";
			 	 	document.forms[0].method = "post";
			 	 	disabledSaveLink();
			  	 	document.forms[0].submit();
			  	 	}
			  	 	else{
			  	 	 alert(("Already in Process...").toUpperCase());
						return;
			  	 	}
				 }
				 else
				 {
				      for(i=1;i<=rowId;i++)
		              { 
		                changeNumberCommaSeparated(document.getElementById('amount'+i));
		              }
				   
					return;
				 }
				 }
			}
	
			function setApprovalDecision()
			{
			
				<logic:notEmpty name ="activityInfo">
					<logic:notEmpty name ="activityInfo" property ="activityMode" >
						<logic:notEqual name ="activityInfo" property="activityMode" value ="AA">
							<logic:equal name ="activityInfo" property="activityMode" value ="M">
								if(document.forms[0].mrSendToAuthor.checked)
								{
									document.forms[0].mrApprovalDecision.value="P";
								}
								else {
									document.forms[0].mrApprovalDecision.value="R";
								}
								
							</logic:equal>
							<logic:equal name ="activityInfo" property="activityMode" value ="A">
								if(document.forms[0].arApprove.checked)
								{
									document.forms[0].arApprovalDecision.value="A";
								}
								else if(document.forms[0].arReject.checked)
								{
									document.forms[0].arApprovalDecision.value="R";
								}
								
							</logic:equal>
						</logic:notEqual>
					</logic:notEmpty>
				</logic:notEmpty>
			}
			
		function fetchInstrumentNo(obj,no)
		{
		
			if(obj.value!="" && obj.value!=undefined && gbl_Acc_Cheque_Flag=="Y")//1.0.0.10
			{
				var acc = document.getElementById("accountNo"+no+"_temp").value;
				var bank = document.getElementById("bankId"+no).value;
				var branch = document.getElementById("branchId"+no).value;
				var prospect = document.getElementById("prospectId"+no).innerHTML;
				var  disbursal_id=document.getElementById("disbDtlId"+no).value;
	
					var xmlhttp;
					if (window.XMLHttpRequest)
					  {
					  		xmlhttp=new XMLHttpRequest();
					  }
					else
					  {
					  	xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
					  }
					
					xmlhttp.open("GET","BankDetailsServlet?accountNo="+acc+"&bankId="+bank+"&branchId="+branch+"&rownum="+Date()+"&prospect_Id="+prospect+"&disbursal_id="+disbursal_id+"&activityScreen=PAYMENT",false);
					xmlhttp.onreadystatechange  = function()
						  {
						 if (xmlhttp.readyState == 4) 
								{
								     if(xmlhttp.status == 200) 
								     {
								       gbl_Acc_Cheque_Flag="N";//1.0.0.10
								       	 var instrument = xmlhttp.responseText.split("~");
								       	 if(instrument[1]!=null)
								      	 	{
								      	 		document.getElementById("instNo"+no).value = instrument[0];
								      	 		document.getElementById("instNo"+no+"_temp").value = instrument[1];
								      	 		return;
								      		}
								      	 else
								      	 	{
								      	  		alert(instrument[0]);
								      	  		return;
								      	 	}
								    
								     }
								     else 
								     {
								        alert(("Error during AJAX call. Please try again: "+xmlhttp.status).toUpperCase());
								     }
								 }
						  };
					xmlhttp.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
					xmlhttp.send();
			}
		}
		
		function cleanupInstrumentNo(no)
		{
				var prospect = document.getElementById("prospectId"+no).innerHTML;
				var  disbursal_id=document.getElementById("disbDtlId"+no).value;
				var cleanUpString = prospect+"~PAYMENT~"+disbursal_id+"^";
	
					var xmlhttp;
					if (window.XMLHttpRequest)
					  {
					  		xmlhttp=new XMLHttpRequest();
					  }
					else
					  {
					  	xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
					  }
					
					xmlhttp.open("GET","BankDetailsServlet?cleanUpString="+cleanUpString+"&time="+Date()+"",false);
					xmlhttp.onreadystatechange  = function()
						  {
						 if (xmlhttp.readyState == 4) 
								{
								     if(xmlhttp.status == 200) 
								     {
								       	 var message = xmlhttp.responseText;
								       	 //alert(message);
								      
								     }
								     else 
								     {
								        alert(("Error during AJAX call. Please try again: "+xmlhttp.status).toUpperCase());
								     }
								 }
						  };
					xmlhttp.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
					xmlhttp.send();
			
		}
		
		//1.0.0.13 add start
		function selectAllRows(obj)
		{
			var paymentTable = document.getElementById("addRow");
			var rowCount = parseInt(paymentTable.rows.length,10)-parseInt(1,10);
			  if(obj.checked)
			  {
				  for(var i=1;i<=rowCount;i++)
					  {
					    if(document.getElementById("payment_Row"+i).checked==false)
					    	{
						    	document.getElementById("payment_Row"+i).checked=true;
						    	togglePaymentRow(document.getElementById("payment_Row"+i));
					    	}
					  }
			  }
			  else
				  {
				  for(var i=1;i<=rowCount;i++)
					  {
					    if(document.getElementById("payment_Row"+i).checked==true)
					    	{
						    	document.getElementById("payment_Row"+i).checked=false;
						    	togglePaymentRow(document.getElementById("payment_Row"+i));
					    	}
					  }
				  
				  }
		}
		//1.0.0.13 add end 
	 
	 function addNewRow(instTypeValue,printFlag,statusAuth)
			{   
		
				var x=document.getElementById('addRow').insertRow(eval(countNew+1));
				var instDateVar = "Inst Date";
				// 1.0.0.3 start
				var a=x.insertCell(0);
				var aa=x.insertCell(1);
				var pd =x.insertCell(2)
				<logic:equal name ="activityInfo" property="activityMode" value="V">
				var v=x.insertCell(3);
				var b=x.insertCell(4);
				var d=x.insertCell(5);
				var e=x.insertCell(6);
				var tdsAmt=x.insertCell(7); //1.0.0.19
				var f=x.insertCell(8);
				var g=x.insertCell(9);
				
				var h=x.insertCell(10);
				
				
				var i=x.insertCell(11);
				
				var j=x.insertCell(12);
				
				var k=x.insertCell(13);
				var l=x.insertCell(14);
				
				var m=x.insertCell(15);
				var mn=x.insertCell(16);
				var r=x.insertCell(17);
				
				var s=x.insertCell(18);
				var t=x.insertCell(19);
				var u=x.insertCell(20);
				var um=x.insertCell(21);
				var rr=x.insertCell(22)
				var n=x.insertCell(23);
				var o=x.insertCell(24);
				
				var ab=x.insertCell(25);
				
				// 1.0.0.31 add start
				var h2hStatus=x.insertCell(26);
				var postApprRejSource=x.insertCell(27);
				var postApprRejUser=x.insertCell(28);
				// 1.0.0.31 add end
				var q=x.insertCell(29);
				
				var c=x.insertCell(30);
				var cx=x.insertCell(31);//added by 1.0.0.15
				c.style.display="none";
				</logic:equal>
				
				<logic:notEqual name ="activityInfo" property="activityMode" value="V">
				var b=x.insertCell(3);
				var d=x.insertCell(4);
				var e=x.insertCell(5);
			
				var tdsAmt=x.insertCell(6); //1.0.0.19
				
				var f=x.insertCell(7);
				var g=x.insertCell(8);
				
				var h=x.insertCell(9);
				var i=x.insertCell(10);
				//var p=x.insertCell(8);
				
				var j=x.insertCell(11);
				var k=x.insertCell(12);
				
				var l=x.insertCell(13);
				var m=x.insertCell(14);
				var mn=x.insertCell(15);
				
				var r=x.insertCell(16);
				var s=x.insertCell(17);
				var t=x.insertCell(18);
				var u=x.insertCell(19);
				var um=x.insertCell(20);
				var rr=x.insertCell(21)
				var n=x.insertCell(22);
				var o=x.insertCell(23);
				
				var ab=x.insertCell(24);
				// 1.0.0.31 add start
				var h2hStatus=x.insertCell(25);
				var postApprRejSource=x.insertCell(26);
				var postApprRejUser=x.insertCell(27);
				// 1.0.0.31 add end
				var q=x.insertCell(28);
				var c=x.insertCell(29);
				c.style.display="none";
								
				var cx=x.insertCell(30);//added by 1.0.0.15
				
				</logic:notEqual>
				
				
				<logic:notEqual name ="entityId"  value="1000000008">
				aa.style.display="none";
				ab.style.display="none";
				</logic:notEqual>
				
				 
			
				rowId = countNew + 1;
				a.innerHTML="<input id='payment_Row"+rowId+"' name='payment_Row' type='checkbox' value='"+rowId+"' onclick='togglePaymentRow(this);' />&nbsp;";
				a.setAttribute('width','50');
				aa.innerHTML="<input id='advancePayment_Row"+rowId+"' name='advancePayment_Row' type='checkbox' disabled='true' /><input type='hidden' name='taxInvoiceNo' id='taxInvoiceNo"+rowId+"' ><input type='hidden' name='taxInvoiceDate' id='taxInvoiceDate"+rowId+"' >";
				aa.setAttribute('width','50');
				
				//1.0.0.13 add start
				pd.innerHTML="<input type='text' name='paymentDate' id='paymentDate"+rowId+"'  maxlength='16' size='26' disabled='true' />";
				pd.setAttribute('width','150');
				//1.0.0.13 add end
				
				b.innerHTML = "<font name='prospectCode' id='prospectCode"+rowId+"'></font><div style='display:none;' name='prospectId' id='prospectId"+rowId+"'></div>";
				b.setAttribute('width','150');
				
				//viplou
				
				// d.innerHTML="<select name='entityName' id='entityName"+rowId+"' onchange='setFavourOf(this)' style='width:150px' ><option value=''>SELECT</option></select>";
				// d.setAttribute('width','150');
				
				d.innerHTML="<input type='hidden' name='eventName"+rowId+"' id='eventName"+rowId+"' ><input type='text' name='entityName' id='entityName"+rowId+"' style='width:150px' maxlength='16' size='25' />";
				d.setAttribute('width','150');
				
				e.innerHTML="<input type='text' name='amount' id='amount"+rowId+"'  maxlength='16' size='25'  style='text-align:right;' onblur='decimalNoFix(this);' />";
				e.setAttribute('width','150');
				
				//1.0.0.19 Start
				tdsAmt.innerHTML="<input type='text' name='tdsAmount' id='tdsAmount"+rowId+"' readonly  maxlength='16' size='25' style='text-align:right;' onblur='decimalNoFix(this);'/>";
				tdsAmt.setAttribute('width','150');
				//1.0.0.19 End
				
				f.innerHTML="<select name='instType' id='instType"+rowId+"'  style='width:150px' ><option value=''>SELECT</option></select>";
				f.setAttribute('width','150');
				
				g.innerHTML="<input type='text' name='favourOf' id='favourOf"+rowId+"'  maxlength='150' size='25'/>";
				g.setAttribute('width','150');
				if(instTypeValue=="1000000001"){
				//h.innerHTML="<input type='hidden' name='instNo"+rowId+"' id='instNo"+rowId+"' ><input type='text' name='instNo"+rowId+"_temp' readonly='true' disabled='true' onfocus='fetchBankDetails("+rowId+");' id='instNo"+rowId+"_temp' maxlength='15'  size= '21'   readonly='true' style='background-color:rgb(235, 243, 255);'/><input type='button' class='blueBotton' name='btnInstNoName' id='btnInstNo"+rowId+"'  value='...'   onclick='javascript:instNoValueChooser(this,"+rowId+");'>";	
				h.innerHTML="<input type='hidden' name='instNo"+rowId+"' id='instNo"+rowId+"' ><input type='text' name='instNo"+rowId+"_temp'  disabled='true' id='instNo"+rowId+"_temp' maxlength='30'  size= '25'    style='background-color:rgb(235, 243, 255);'/>";	
				h.setAttribute('width','150');
				}
				else{
				h.innerHTML="<input type='text' name='instNo' id='instNo"+rowId+"' style='background-color:#ebf3ff;'  maxlength='30' size='25'/>";
				h.setAttribute('width','150');
				}
				// i.innerHTML="<input type='text' name='instDate' id='instDate"+rowId+"'  maxlength='16'/><a href='#'  return false;' onClick='openCalander("+rowId+");'	name='instDate_link' ID='instDate_link'	title='Calendar'><img src='images/calendar.gif' alt='Calendar' border='0' /></a>";
				i.innerHTML = "<table><tr><td><input type='text' name='instDate' id='instDate"+rowId+"' style='background-color:#ebf3ff;'  maxlength='11' size='26' onblur='dateCheck("+rowId+");' /></td><td><a href='#'  return false;' onClick='openCalander("+rowId+");'	name='instDate_link' ID='instDate_link'	title='Calendar'> <i class='fa fa-calendar' aria-hidden='true'></i></a></td></tr></table>";//1.0.0.23
				i.setAttribute('width','40');
				
				// j.innerHTML="<input type='text' name='bankId' id='bankId"+rowId+"'  maxlength='16' size='23'/>";
				
				j.innerHTML="<table><tr><td><input type='hidden' name='bankId"+rowId+"' id='bankId"+rowId+"' ><div onmouseover='setToolTipForDisabledLov(this)'><input type='text' name='bankId"+rowId+"_temp' id='bankId"+rowId+"_temp'  maxlength='15'  size= '15'   readonly='true' style='background-color:rgb(235, 243, 255);'/></div></td><td><input type='button' class='blueBotton' name='btnBankName' id='btnBankName"+rowId+"' value='...'   onclick='javascript:bankValueChooser(this,"+rowId+");'></td></tr></table>";	
				j.setAttribute('width','150');
				
				// k.innerHTML="<input type='text' name='branchId' id='branchId"+rowId+"'  maxlength='16' size='23'/>";
				k.innerHTML="<table><tr><td><input type='hidden' name='branchId"+rowId+"' id='branchId"+rowId+"' ><div onmouseover='setToolTipForDisabledLov(this)'><input type='text' name='branchId"+rowId+"_temp' id='branchId"+rowId+"_temp' onfocus='fetchIfscMicr(this,"+rowId+");'  maxlength='15'  size= '15'   readonly='true' style='background-color:rgb(235, 243, 255);'/></div></td><td><input type='button' class='blueBotton' name='btnBranchName'  id='btnBranchName"+rowId+"' value='...'   onclick='javascript:branchValueChooser(this,"+rowId+");'></td></tr></table>";	
				k.setAttribute('width','150');
				
				// l.innerHTML="<input type='text' name='accountNo' id='accountNo"+rowId+"'  maxlength='16' size='23'/>";
				if(instTypeValue=="1000000001"){
					l.innerHTML="<table><tr><td><input type='hidden' name='accountNo"+rowId+"' id='accountNo"+rowId+"' ><div onmouseover='setToolTipForDisabledLov(this)'><input type='text' name='accountNo"+rowId+"_temp' id='accountNo"+rowId+"_temp' maxlength='30'  size= '15'  onfocus='fetchInstrumentNo(this,"+rowId+");'   readonly='true' style='background-color:rgb(235, 243, 255);'/></div></td><td><input type='button' class='blueBotton' name='btnAccountName' id='btnAccountName"+rowId+"' value='...'   onclick='javascript:accountValueChooser(this,"+rowId+");'></td></tr></table>";	
					l.setAttribute('width','150');
				}
				else
				{
					l.innerHTML="<table><tr><td><input type='hidden' name='accountNo"+rowId+"' id='accountNo"+rowId+"' ><div onmouseover='setToolTipForDisabledLov(this)'><input type='text' name='accountNo"+rowId+"_temp' id='accountNo"+rowId+"_temp' maxlength='30'  size= '15' readonly='true' style='background-color:rgb(235, 243, 255);'/></div></td><td><input type='button' class='blueBotton' name='btnAccountName' id='btnAccountName"+rowId+"' value='...'   onclick='javascript:accountValueChooser(this,"+rowId+");'></td></tr></table>";	
					l.setAttribute('width','150');
				}
				
				m.innerHTML="<input type='text' name='ifscCode' id='ifscCode"+rowId+"'  maxlength='20' size='25'/><input type='hidden' name='disbId' id='disbId"+rowId+"'><input type='hidden' name='disbDtlId' id='disbDtlId"+rowId+"'><input type='hidden' name='branch' id='branch"+rowId+"'>";
				m.setAttribute('width','150');
				
				
				mn.innerHTML="<input type='text' name='micrCode' id='micrCode"+rowId+"'  maxlength='20' size='25'/>";
				mn.setAttribute('width','150');
				
				
				
				r.innerHTML="<table><tr><td><input type='hidden' name='sbankId"+rowId+"' id='sbankId"+rowId+"' ><div onmouseover='setToolTipForDisabledLov(this)'><input type='text' name='sbankId"+rowId+"_temp' id='sbankId"+rowId+"_temp' maxlength='15'  size= '15'   readonly='true' style='background-color:rgb(235, 243, 255);'/></div></td><td><input type='button' class='blueBotton' name='sbtnBankName'  value='...'   onclick=''></td></tr></table>";	
				r.setAttribute('width','150');
				
				// k.innerHTML="<input type='text' name='branchId' id='branchId"+rowId+"'  maxlength='16' size='23'/>";
				s.innerHTML="<table><tr><td><input type='hidden' name='sbranchId"+rowId+"' id='sbranchId"+rowId+"' ><div onmouseover='setToolTipForDisabledLov(this)'><input type='text' name='sbranchId"+rowId+"_temp' id='sbranchId"+rowId+"_temp' maxlength='15'  size= '15'   readonly='true' style='background-color:rgb(235, 243, 255);'/></div></td><td><input type='button' class='blueBotton' name='sbtnBranchName'  value='...'   onclick=''></td></tr></table>";	
				s.setAttribute('width','150');
				
				// l.innerHTML="<input type='text' name='accountNo' id='accountNo"+rowId+"'  maxlength='16' size='23'/>";
				// t.innerHTML="<input type='hidden' name='saccountNo"+rowId+"' id='saccountNo"+rowId+"' ><input type='text' name='saccountNo"+rowId+"_temp' id='saccountNo"+rowId+"_temp' maxlength='15'  size= '21'   readonly='true' style='background-color:rgb(235, 243, 255);'/><input type='button' class='blueBotton' name='sbtnAccountName'  value='...'   onclick=''>";	
				t.innerHTML = "<input type='text' name='saccountNo' id='saccountNo"+rowId+"'  maxlength='30' size='30'/>";
				t.setAttribute('width','150');
				
				u.innerHTML="<input type='text' name='sifscCode' id='sifscCode"+rowId+"'  maxlength='20' size='25'/>";
				u.setAttribute('width','150');
				
				um.innerHTML="<input type='text' name='smicrCode' id='smicrCode"+rowId+"'  maxlength='20' size='25'/>";
				um.setAttribute('width','150');
				//1.0.0.13 add start
				rr.innerHTML="<select name='rejectReason' disabled='true' id='rejectReason"+rowId+"'  style='width:150px' ><option value='0'>SELECT</option></select>";
				rr.setAttribute('width','150');
				//1.0.0.13 add end
				n.innerHTML="<input type='text' name='mRemarks' id='mRemarks"+rowId+"'  maxlength='16' size='25'/>";
				n.setAttribute('width','150');
				
				o.innerHTML="<input type='text' name='aRemarks' id='aRemarks"+rowId+"'  maxlength='16' size='25'/>";
				o.setAttribute('width','150');
				
				ab.innerHTML="<input type='text' name='paymentRemarks' id='paymentRemarks"+rowId+"'  size='25' disabled='true'/>";
				ab.setAttribute('width','150');
				
				q.innerHTML="<input type='text' name='aStatus' id='aStatus"+rowId+"'  size='25' style='display:none' />";
				q.setAttribute('width','150');
				
				c.innerHTML="<select name='entityType' id='entityType"+rowId+"'  onchange='clickEntityType(this,"+rowId+")' style='display:none' ><option value='0'>SELECT</option></select>";
				c.setAttribute('width','150');
				//1.0.0.15 add start
				
				
				
				cx.innerHTML="<input type='hidden' name='product' id='product"+rowId+"' style='display:none' />";
				/* cx.setAttribute('width','150'); */
				//1.0.0.15 add end
				// 1.0.0.31 add start
				h2hStatus.innerHTML="<input type='text' name='H2H_STATUS' id='H2H_STATUS"+rowId+"' readonly='true' size='25' style='background-color:rgb(235, 243, 255);'/>";
				h2hStatus.setAttribute('width','150');
				
				postApprRejSource.innerHTML="<input type='text' name='POST_APPR_REJ_SOURCE' id='POST_APPR_REJ_SOURCE"+rowId+"' readonly='true' size='25' style='background-color:rgb(235, 243, 255);'/>";
				postApprRejSource.setAttribute('width','150');
				
				postApprRejUser.innerHTML="<input type='text' name='POST_APPR_REJ_USER' id='POST_APPR_REJ_USER"+rowId+"' readonly='true' size='25' style='background-color:rgb(235, 243, 255);'/>";
				postApprRejUser.setAttribute('width','150');
				// 1.0.0.31 add end
				<logic:equal name ="activityInfo" property="activityMode" value="V">
                                   
						             if(printFlag=="N" && statusAuth=="Approved")
							           { //alert("rowId = "+rowId+"statusAuth = "+statusAuth+" printFlag = "+printFlag+" instTypeValue = "+instTypeValue);
											v.innerHTML="<input style='width:50px' type='button' name='print'  value='Print' id='print"+rowId+"'  maxlength='20' size='25' onclick='javascript:printOption("+rowId+");'/><input style='width:130px' type='button' name='advancePrint' disabled='true'  value='Adv Pay Voucher' id='advancePrint"+rowId+"'  maxlength='20' size='25' onclick='javascript:advancePrintOption("+rowId+");'/>";
							           } else{ 
							          // alert("rowId = "+rowId);
							          		v.innerHTML="<input style='width:50px' type='button' name='print' disabled='true' value='Print' id='print"+rowId+"'  maxlength='20' size='25' onclick='javascript:printOption("+rowId+");'/><input style='width:130px' type='button' name='advancePrint' disabled='true' value='Adv Pay Voucher' id='advancePrint"+rowId+"'  maxlength='20' size='25' onclick='javascript:advancePrintOption("+rowId+");'/>";
							           }
							          if(instTypeValue!="1000000001")
							           {  
							          		v.innerHTML="<input style='width:50px' type='button' name='print' disabled='true' value='Print' id='print"+rowId+"'  maxlength='20' size='25' onclick='javascript:printOption("+rowId+");'/><input style='width:130px' type='button' name='advancePrint' disabled='true' value='Adv Pay Voucher' id='advancePrint"+rowId+"'  maxlength='20' size='25' onclick='javascript:advancePrintOption("+rowId+");'/>";
							           } 
							            v.setAttribute('width','200');
				      
				 </logic:equal>
				countNew++;
				
				
				populateEntityType(document.getElementById("entityType"+rowId));
				populateInstrumentType(document.getElementById("instType"+rowId));
				populateRejectReason(document.getElementById("rejectReason"+rowId));//1.0.0.13
				
			}  	 
			// 1.0.0.3 end
			
		
			  var selectedDisbRows="";
		function toggleDisbRow(checkboxObj)
		{
			if(checkboxObj.checked)
			{			
				selectedDisbRows = selectedDisbRows + "~" + checkboxObj.value + "~";
				selectedRowCount++;
			}
			else
			{			
				selectedDisbRows = selectedDisbRows.replace("~" + checkboxObj.value + "~","");
				selectedRowCount--;
			}
			if(selectedRowCount == disbRowCount)
			{
				alert(("All rows cannot be removed.").toUpperCase());
				checkboxObj.checked = false;
				toggleDisbRow(checkboxObj);
			}
		}
		
		 	
			
	function clickEntityType(obj,no)
	 {   
		var entityId = obj[obj.selectedIndex].value;
		
		clearcombo(document.getElementById("entityName"+no));
		
		for (i=0; i< entityNameArr.length; i++)
	    {
	    	if (entityId == entityNameArr[i][0])
	    	{  
	    	if(entityId == "1000000003")
	    	{
	    	  document.getElementById("entityName"+no).options[document.getElementById("entityName"+no).options.length]= new Option(entityNameArr[i][2] ,entityNameArr[i][1] );
	    	  
	    	}
	    	else
	    	 {
	    		document.getElementById("entityName"+no).options[document.getElementById("entityName"+no).options.length]= new Option(entityNameArr[i][2] ,entityNameArr[i][1] );
	    	 }   
	    	}
	    }
	}
	
	function clearcombo(elem)
	{
		var i;
		for (i = elem.options.length - 1; i >= 0; i--) 
		{
		       elem.options[i] = null;
		}
		elem.selectedIndex = -1;
		elem.options[elem.options.length]= new Option("SELECT" ,"0" );
	}
		 		
		 		function populateEntityType(obj)
			{			
				for(i=0;i<entityTypeArr.length;i++)
				{	 
					obj.options[i+1]=new Option(entityTypeArr[i][0],entityTypeArr[i][1]);			
				}
			}
			
			
			
			function getSearchData()
		  {  
		   <logic:equal name ="activityInfo" property="activityMode" value="V">
		  if(document.forms[0].colCustCode.value == "" &&
		    document.forms[0].colProspectCode.value== "" &&
		    document.forms[0].paymentFrom.value == "" && // added by 1.0.0.14
		    document.forms[0].paymentTo.value == "" )//added by 1.0.0.14
          {
           alert("ENTER AT LEAST ONE VALUE FROM PROSPECT CODE/CUSTOMER CODE/BRANCH ID/PAN/PAYMENT FROM/PAYMENT TO");     
           return;
          }
           </logic:equal>
         //1.0.0.13 add start
 		  if(document.forms[0].paymentFrom.value == "" && document.forms[0].paymentTo.value != ""){ //changed by 1.0.0.16
 		 		alert("PAYMENT FROM IS MANDATORY");     
 	         	return;
 		  }
 		  if(document.forms[0].paymentTo.value == "" && document.forms[0].paymentFrom.value != ""){ //chagned by 1.0.0.16
 			 alert("PAYMENT TO IS MANDATORY");     
 	         return;
 		  }
 		  var toDate = Date.parse(document.getElementById("paymentTo").value);
 		  var fromDate = Date.parse(document.getElementById("paymentFrom").value);
 		  var diffInDates = (toDate-fromDate)/(86400000);
 		  if(diffInDates <0){
 			  alert("PAYMENT TO DATE CAN NOT BE LESS THAN FROM DATE.");	
 			  return false; 
 		  }
 		  
	 		  if(diffInDates > 7){ 
	 			  alert("DIFFERENCE BETWEEN PAYMENT FROM AND PAYMENT TO DATE CAN NOT BE GREATER THAN 7 DAYS.");
	 			  return false;
	 		  }
 		  //1.0.0.13 add end
 		  
 		  if((document.forms[0].colCustCode.value != null && document.forms[0].colCustCode.value != "") || (document.forms[0].vendorId.value != null && document.forms[0].vendorId.value != "") ||
 				 (document.forms[0].product.value != null && document.forms[0].product.value != "0" && document.forms[0].product.value != "") || (document.forms[0].orixBank.value != null && document.forms[0].orixBank.value != "0" && document.forms[0].orixBank.value != "")){
 			 if(document.forms[0].paymentFrom.value == "" || document.forms[0].paymentFrom.value == null || document.forms[0].paymentTo.value == "" || document.forms[0].paymentTo.value == null){
 			  	alert("PAYMENT FROM DATE AND PAYMENT TO DATE MUST BE SPECIFIED.".toUpperCase());
 			  	return;
 			 }
 		  }
 		 if((document.forms[0].colProspectCode.value == null || document.forms[0].colProspectCode.value == "") && (document.forms[0].colCustCode.value == null || document.forms[0].colCustCode.value == "") && (document.forms[0].vendorId.value == null || document.forms[0].vendorId.value == "") &&
 				 (document.forms[0].product.value == null || document.forms[0].product.value == "0" || document.forms[0].product.value == "") && (document.forms[0].orixBank.value == null || document.forms[0].orixBank.value == "0" || document.forms[0].orixBank.value == "")){
 			 if(document.forms[0].paymentFrom.value == "" || document.forms[0].paymentFrom.value == null || document.forms[0].paymentTo.value == "" || document.forms[0].paymentTo.value == null){
 			  	alert("PAYMENT FROM DATE AND PAYMENT TO DATE MUST BE SPECIFIED.".toUpperCase());
 			  	return;
 			 }
 		  }	
 		  //End 1.0.0.23
            document.forms[0].vendorName.disabled = false;
                	document.forms[0].action="paymentAction.do?actionPerformed=displayPaymentScreen&screenId=<%=request.getParameter("screenId")%>&searchFlag=Y";
					document.forms[0].submit();
	      }	 
		
			
		function populateInstrumentType(obj)
		{			
				for(i=0;i<instrumentTypeArr.length;i++)
				{	 
					obj.options[i+1]=new Option(instrumentTypeArr[i][0],instrumentTypeArr[i][1]);			
				}
		}
		
		//add start 1.0.0.13
		function populateRejectReason(obj){
			for(i=1;i<rejectReasonArr.length;i++)
			{	 
				obj.options[i]=new Option(rejectReasonArr[i][0],rejectReasonArr[i][1]);			
			}
		}
		//add end 1.0.0.13
		function fetchIfscMicr(obj,no)
		{
					if(obj.value!=null)
					{
					
					var bank = document.getElementById("bankId"+no).value;
				    var branch = document.getElementById("branchId"+no).value;
				
				
				 $.ajax({
				 url: "getIfscMicrCode.do?actionPerformed=getIfscMicrCode",
				 type: "post",
				 data:{
					 bankId:bank,
					 branchId:branch,
					 rownum:Date()
					   
					  }, 
				 ontext: document.body,
				 success: function(response) {
					if(response!=null && response!="") 
							{ //alert(response)
							
							  var data = response.split("~");
							    if(data[1]!="null")
													 {
													   document.getElementById("micrCode"+no).value =data[1];
													   document.getElementById("micrCode"+no).disabled=true;
													 }
													 else
													 {
													  	document.getElementById("micrCode"+no).value="";
													  	document.getElementById("micrCode"+no).disabled=false;
													  }
													 if(data[0]!="null")
													 {
													  document.getElementById("ifscCode"+no).value=data[0];
													  document.getElementById("ifscCode"+no).disabled=true;
													 }
													 else
													 {
													   document.getElementById("ifscCode"+no).value="";
													   document.getElementById("ifscCode"+no).disabled=false;
													  }
							}}});
		    
					
			
		}
		
		}
		
		
		function generateXml()
	{
	       
		 	rowsDataXml = "";
			prospectString = "";
			prospectArray = new Array();
			var paymentTable = document.getElementById("addRow");
		    var rowCount = parseInt(paymentTable.rows.length,10)-parseInt(1,10);
		   
		    //alert(rowCount);
		    for(var i=1; i<=rowCount; i++)
		     {
				//alert(i);
				//alert("1");
				// alert(document.getElementById("payment_Row"+i).checked);
				// alert("3");
				     if(document.getElementById("payment_Row"+i).checked)
				     {
				      
				       if(document.getElementById("instType"+i).value=="1000000001")
				       {
					        var instNumber_temp=document.getElementById("instNo"+i+"_temp").value;
						    var inst_number=instNumber_temp.split("-");
						    document.getElementById("instNo"+i+"_temp").value=inst_number[0].replace(/^\s+|\s+$/g,'');;
						}   
					  
					   
					             rowsDataXml =  rowsDataXml+"<ROW>"+
					  
					             "<DISBURSALDTLID>"+document.getElementById("disbDtlId"+i).value+"</DISBURSALDTLID>"+
				                 "<DISBURSALID>"+document.getElementById("disbId"+i).value+"</DISBURSALID>"+
				                 "<PROSPECTID>"+document.getElementById("prospectId"+i).innerHTML+"</PROSPECTID>"+
			                     "<INSTRUMENT_TYPE>"+document.getElementById("instType"+i).value+"</INSTRUMENT_TYPE>"+
								 "<ISSUE_IN_FAVOR>"+document.getElementById("favourOf"+i).value+"</ISSUE_IN_FAVOR>";
								
								if(document.getElementById("instNo"+i+"_temp") && document.getElementById("instType"+i).value=="1000000001")
								 rowsDataXml =  rowsDataXml+"<INSTRUMENTNO>"+document.getElementById("instNo"+i+"_temp").value+"</INSTRUMENTNO>";
								 else
								 rowsDataXml =  rowsDataXml+"<INSTRUMENTNO>"+document.getElementById("instNo"+i).value+"</INSTRUMENTNO>";
								 
								 rowsDataXml =  rowsDataXml+"<INSTRUMENT_DATE>"+document.getElementById("instDate"+i).value+"</INSTRUMENT_DATE>"+
								 "<BANK_ID>"+document.getElementById("bankId"+i).value+"</BANK_ID>"+
								 "<BRANCH_ID>"+document.getElementById("branchId"+i).value+"</BRANCH_ID>"+
								 "<ACCOUNTNO>"+document.getElementById("accountNo"+i).value+"</ACCOUNTNO>"+
								 "<IFSCCODE>"+document.getElementById("ifscCode"+i).value+"</IFSCCODE>"+
								 "<MICRCODE>"+document.getElementById("micrCode"+i).value+"</MICRCODE>"+
								 "<MAKER_REMARKS>"+document.getElementById("mRemarks"+i).value+"</MAKER_REMARKS>"+
								 "<AUTH_REMARKS>"+document.getElementById("aRemarks"+i).value+"</AUTH_REMARKS>";
							<logic:equal name ="activityInfo" property="activityMode" value="M">	
								if(document.getElementById("instNo"+i+"_temp") && document.getElementById("instType"+i).value==1000000001)
								rowsDataXml=rowsDataXml+"<INSTRUMENTID>"+document.getElementById("instNo"+i).value+"</INSTRUMENTID>";
								</logic:equal>
                            // 1.0.0.3 start
								<logic:equal name ="entityId"  value="1000000008">
								if(document.getElementById("advancePayment_Row"+i).checked==true){
								 rowsDataXml=rowsDataXml+  "<ADVANCE_PAYMENT_FLAG>Y</ADVANCE_PAYMENT_FLAG>";
								}
								else
								{ 
								 rowsDataXml=rowsDataXml+ "<ADVANCE_PAYMENT_FLAG>N</ADVANCE_PAYMENT_FLAG>";
								}
								 rowsDataXml=rowsDataXml+"<ADVANCE_PAYMENT_REMARKS>"+document.getElementById("paymentRemarks"+i).value+"</ADVANCE_PAYMENT_REMARKS>";
								 rowsDataXml=rowsDataXml+"<REJECT_REASON>"+document.getElementById("rejectReason"+i).value+"</REJECT_REASON>";//1.0.0.13
								
								</logic:equal>
                                // 1.0.0.3 end
								rowsDataXml=rowsDataXml+ "</ROW>";	
								 
										
								  // alert(document.getElementById("prospectId"+i).innerHTML);
								  
							 	   if(prospectString.indexOf(document.getElementById("prospectId"+i).innerHTML) == -1 )
		 					       {
		 					          prospectString = prospectString + "~" +document.getElementById("prospectId"+i).innerHTML;
		 					        
								      // alert("inside");
								      prospectArray[ino] =  document.getElementById("prospectId"+i).innerHTML;
								      ino = parseInt(ino) + parseInt("1");
								      //  alert(prospectArray);
								   }
										  
		          }
		    }
			    
		 
		  
		   document.forms[0].xmlData.value="<ROWSET>"+rowsDataXml+"</ROWSET>";
		  
	 }	
	 
	 function openCalander(no)
	 {
	   cal19.select(document.getElementById("instDate"+no),"instDate_link","dd-NNN-yyyy");
	 }
	 
	 function dateCheck(no)
	 {
	  checkDateFormat(document.getElementById("instDate"+no),"Inst Date");
	 }
	 
	 //added by stuti bajpai
	 function disableAllElements()
		    {
		    
		    	<logic:equal name ="activityInfo" property="activityMode" value="V">
				    	for(count=0; count < document.forms[0].elements.length; count+=1)
				        {
				        
				        	
				        	theelement = document.forms[0].elements[count];
				        	
				           	
				        	if(theelement.name != null && (theelement.name != "print" && theelement.name != "advancePrint"))
				            {
				            	theelement.disabled = true;
				            }
				            if (theelement.name == "colProspectCode"||theelement.name == "colCustCode"|| theelement.name == "searchBtn" ||theelement.name == "paymentFrom" ||theelement.name == "paymentFrom_link" ||theelement.name == "paymentTo" ||theelement.name == "paymentTo_link" ||theelement.name == "product" ||theelement.name == "orixBank" ||theelement.name == "vendorId" ||theelement.name == "btnVendorName" || theelement.name == "downloadCSVReprotBtn" || theelement.name == "resetBtn")//added by 1.0.0.14 //1.0.0.23
				            {
				            	theelement.disabled = false;
				            }
				        }
				  
			   </logic:equal>
		    }
	 
	 //1.0.0.13 add start
	 function toggleRejectReason(val){
		 var paymentTable = document.getElementById("addRow");
         var rowCount = 0;
         
         if(paymentTable)
         {
        		rowCount = parseInt(paymentTable.rows.length,10)-parseInt(1,10);
         }
         for(var i=1;i<=rowCount;i++){
        	 if( document.getElementById("payment_Row"+i).checked == true && val == "R"){
        		 document.getElementById("rejectReason"+i).disabled = false;
        		 if(document.getElementById("instNo"+i+"_temp")!=null){
			 		document.getElementById("instNo"+i+"_temp").disabled = true;
		 		 }
		 		 else{
		 			document.getElementById("instNo"+i).disabled = true;
		 		 }
        		 document.getElementById("instDate"+i).disabled = true;
        		 
        		 document.getElementById("btnBankName"+i).disabled = true;
			 		document.getElementById("btnBranchName"+i).disabled = true;
			 		document.getElementById("btnAccountName"+i).disabled = true;
			 		document.getElementById("ifscCode"+i).disabled = true;
			 		document.getElementById("micrCode"+i).disabled = true;
        	 }
        	 else if(document.getElementById("payment_Row"+i).checked == true && val == "P"){
        		 document.getElementById("rejectReason"+i).disabled = true;
        		 if(document.getElementById("instNo"+i+"_temp")!=null){
			 			document.getElementById("instNo"+i+"_temp").disabled = false;
		 		}
	 		 else{
	 			 
		 			document.getElementById("instNo"+i).disabled = false;
		 		 }
        		 if(document.getElementById("instType"+i).value =="1000000005" || document.getElementById("instType"+i).value=="1000000006"){
        			 document.getElementById("instNo"+i).disabled = true;
        			 document.getElementById("instDate"+i).disabled = true;
        		 } //1.0.0.30
        		 else{
        		 document.getElementById("instDate"+i).disabled = false;
        		 }//1.0.0.30
        		 document.getElementById("btnBankName"+i).disabled = false;
			 		document.getElementById("btnBranchName"+i).disabled = false;
			 		document.getElementById("btnAccountName"+i).disabled = false;
			 		document.getElementById("ifscCode"+i).disabled = false;
			 		document.getElementById("micrCode"+i).disabled = false;
        	 }
        	 else{
        		 document.getElementById("rejectReason"+i).disabled = true;
        	 }
         }
	 }
	 //1.0.0.13 add end
	 
	 function loadData()
	 {
	       
	            var paymentTable = document.getElementById("addRow");
	            var rowCount = 0;
	            
	            if(paymentTable)
	            {
	           		rowCount = parseInt(paymentTable.rows.length,10)-parseInt(1,10);
	           	}
               // 1.0.0.3 start
               <logic:notEmpty name="listData">
			       <logic:equal name ="entityId"  value="1000000008">
				     document.getElementById("advancePaymentHeader").style.display = "table-cell";
				     document.getElementById("advanceRemarksHeader").style.display = "table-cell";
				   </logic:equal>
			   </logic:notEmpty>
		             // 1.0.0.3 end
		    for(var i=1; i<=rowCount; i++)
		     {
				
				       document.getElementById("payment_Row"+i).checked =false;
				       document.getElementById("entityType"+i).disabled = true;
					   document.getElementById("entityName"+i).disabled = true;
					   document.getElementById("amount"+i).disabled = true;
					   document.getElementById("favourOf"+i).disabled = true;
					   
					  
					   document.getElementById("instType"+i).disabled = true;
					   document.getElementById("instNo"+i).disabled = true;
					   document.getElementById("instDate"+i).disabled = true;
					   document.getElementById("branchId"+i+"_temp").disabled = true;
					   document.getElementById("bankId"+i+"_temp").disabled = true;
					   document.getElementById("accountNo"+i+"_temp").disabled = true;
					   document.getElementById("ifscCode"+i).disabled = true;
					   document.getElementById("micrCode"+i).disabled = true;
					   document.getElementById("mRemarks"+i).disabled = true;
			
			
					   document.getElementById("aRemarks"+i).disabled = true;
					   
					   document.getElementById("sbranchId"+i+"_temp").disabled = true;
					   document.getElementById("sbankId"+i+"_temp").disabled = true;
					   document.getElementById("saccountNo"+i).disabled = true;
					   document.getElementById("sifscCode"+i).disabled = true;
				       document.getElementById("smicrCode"+i).disabled = true;
				    	
				       document.getElementById("btnBankName"+i).disabled=true;
					   document.getElementById("btnBranchName"+i).disabled=true;
					   document.getElementById("btnAccountName"+i).disabled=true;
				    	
					 <logic:equal name ="activityInfo" property="activityMode" value="A">
					    
					        document.getElementById("instType"+i).disabled = true;
					        document.getElementById("instNo"+i).disabled = true;
					        document.getElementById("instDate"+i).disabled = true;
					        document.getElementById("branchId"+i+"_temp").disabled = true;
					        document.getElementById("bankId"+i+"_temp").disabled = true;
					        document.getElementById("accountNo"+i+"_temp").disabled = true;
					        document.getElementById("ifscCode"+i).disabled = true;
					        document.getElementById("micrCode"+i).disabled = true;
					        document.getElementById("mRemarks"+i).disabled = true;
					        document.getElementById("aRemarks"+i).disabled = false;
					 </logic:equal>
					 
					  <logic:equal name ="activityInfo" property="activityMode" value="M">
					  document.getElementById("mRemarks"+i).style.background = "#ebf3ff";
					 </logic:equal>
					 
					   <logic:equal name ="activityInfo" property="activityMode" value="A">
					  document.getElementById("aRemarks"+i).style.background = "#ebf3ff";
					 </logic:equal>
					 
					  /* if(document.getElementById("instType"+i).value=="1000000001")
					 {
					      
					 	 document.getElementById("bankId"+i+"_temp").disabled=true;
					     document.getElementById("btnBankName"+i).disabled=true;
					     
					 	 
					 	 document.getElementById("branchId"+i+"_temp").disabled=true;
					     document.getElementById("btnBranchName"+i).disabled=true;
					 	 
					     document.getElementById("accountNo"+i+"_temp").disabled=true;
					     document.getElementById("btnAccountName"+i).disabled=true;
					     
					     
						 document.getElementById("ifscCode"+i).disabled=true;
					 
					    
					 } */
					 
				}
				
					   
				       
			<logic:equal name ="activityInfo" property="activityMode" value="V">
			
			for(var i=1; i<=rowCount; i++)
		     {         
		               document.getElementById("payment_Row"+i).disabled=false;			//1.0.0.0.1	    
				       document.getElementById("entityType"+i).disabled = true;
					   document.getElementById("entityName"+i).disabled = true;
					   document.getElementById("amount"+i).disabled = true;
					   document.getElementById("favourOf"+i).disabled = true;
					   
					   document.getElementById("aRemarks"+i).disabled = true;
					   document.getElementById("instType"+i).disabled = true;
					   document.getElementById("instNo"+i).disabled = true;
					   document.getElementById("instDate"+i).disabled = true;
					   document.getElementById("branchId"+i+"_temp").disabled = true;
					   document.getElementById("bankId"+i+"_temp").disabled = true;
					   document.getElementById("accountNo"+i+"_temp").disabled = true;
					   document.getElementById("ifscCode"+i).disabled = true;
					   document.getElementById("micrCode"+i).disabled = true;
					   document.getElementById("mRemarks"+i).disabled = true;
			
			
					   document.getElementById("aRemarks"+i).disabled = true;
					   //document.getElementById("aStatus"+i).disabled = true;
					   //document.getElementById("aStatus"+i).style.display = "block";
					   
		      }
			    //document.getElementById("status").style.display = "block";
		    </logic:equal> 
		    
		    //document.forms[0].colCustCode.value = "";//commented by 1.0.0.23
		    //document.forms[0].colPanNo.value = "";
		   // document.forms[0].colProspectCode.value = "";commented by 1.0.0.23
		    //document.forms[0].colBranchId.value = "";
		    
		    //1.0.0.13 add start
		   <logic:notEqual name="searchFlag" value="Y"> //added 1.0.0.23
			    document.forms[0].paymentFrom.value='<bean:write name ="llmSessionUserInformationDTO" property="businessDate"/>';//commented by 1.0.0.23
			    document.forms[0].paymentTo.value='<bean:write name ="llmSessionUserInformationDTO" property="businessDate"/>';//commented by 1.0.0.23
			</logic:notEqual>
			    //1.0.0.13 add end
		   
		    <logic:notEmpty name="successMsg">
		       alert(('<bean:write name="successMsg"/>').toUpperCase());
		    </logic:notEmpty>
		    
		    for(i=1;i<=rowId;i++)
		     { 
		        changeNumberCommaSeparated(document.getElementById('amount'+i));
		     }
			   
			   disableAllElements();
			   //Ravikant
			   document.getElementById("fieldsetId").disabled=false;
			   document.getElementById("fieldsetId1").disabled=false;
			   
			//Hemant 1.0.0.23	START		
			<logic:equal name="searchFlag" value="Y">
				for (var i = 0; i < document.getElementById("product").options.length; i++) {
				    if (document.getElementById("product").options[i].value == '<bean:write name ="productId"/>') {
				    	document.getElementById("product").selectedIndex = i;
				      break;
				    }
				}
				for (var i = 0; i < document.getElementById("orixBank").options.length; i++) {
				    if (document.getElementById("orixBank").options[i].value == '<bean:write name ="orixBankId"/>') {
				    	document.getElementById("orixBank").selectedIndex = i;
				      break;
				    }
				}
			</logic:equal>
			
			<logic:notEqual name="searchFlag" value="Y">
				document.getElementById("orixBank").selectedIndex = "0";
				document.getElementById("product").selectedIndex = "0";
				document.getElementById("vendorId").value = "";
				document.getElementById("vendorName").value = "";
			</logic:notEqual>
			
				<logic:notEqual name ="activityInfo" property="activityMode" value="M">	
					document.getElementById("orixBankDivTextId").style.display="block";
					document.getElementById("orixBankDivLabelId").style.display="block";
				</logic:notEqual>
			//Hemant 1.0.0.23	END
		}	
		   
			
			function togglePaymentRow(obj)
			{
			   
			   var i = obj.value;
			   if(obj.checked)
			   {
			   
			       <logic:equal name ="activityInfo" property="activityMode" value="M">
			       
			          //  document.getElementById("instType"+i).disabled = false;
					   document.getElementById("instNo"+i).disabled = false;
					   if(document.getElementById("instNo"+i+"_temp")!=null)
					   {
					   document.getElementById("instNo"+i+"_temp").disabled = false;
					   document.getElementById("instNo"+i+"_temp").readonly = false;
					   }
					   document.getElementById("instDate"+i).disabled = false;
					   document.getElementById("branchId"+i+"_temp").disabled = false;
					   document.getElementById("bankId"+i+"_temp").disabled = false;
					   document.getElementById("accountNo"+i+"_temp").disabled = false;
					 //1.0.0.13 add start
					 	if(document.forms[0].mrReject.checked){
					 		document.getElementById("rejectReason"+i).disabled = false;
					 		 if(document.getElementById("instNo"+i+"_temp")!=null){
					 			document.getElementById("instNo"+i+"_temp").disabled = true;
					 		 }
					 		 else{
					 			document.getElementById("instNo"+i).disabled = true;
					 		 }
					 		document.getElementById("instDate"+i).disabled = true;
					 		
					 		document.getElementById("btnBankName"+i).disabled = true;
					 		document.getElementById("btnBranchName"+i).disabled = true;
					 		document.getElementById("btnAccountName"+i).disabled = true;
					 		document.getElementById("ifscCode"+i).disabled = true;
					 		document.getElementById("micrCode"+i).disabled = true;
					 		
					 	}
					 	else{
					 		document.getElementById("rejectReason"+i).disabled = true;
					 		if(document.getElementById("instNo"+i+"_temp")!=null){
					 			document.getElementById("instNo"+i+"_temp").disabled = false;
					 		}
				 		 else{
				 			if(document.getElementById("instType"+i).value =="1000000005" || document.getElementById("instType"+i).value=="1000000006"){
					 			document.getElementById("instNo"+i).disabled = true; //1.0.0.30
					 			document.getElementById("instDate"+i).disabled = true;
				 			}
				 			else{
				 				document.getElementById("instNo"+i).disabled = false; //1.0.0.30
				 				document.getElementById("instDate"+i).disabled = false;
				 			}
				 			
					 		 }
					 		//document.getElementById("instDate"+i).disabled = false;//1.0.0.30
					 		
					 		document.getElementById("btnBankName"+i).disabled = false;
					 		document.getElementById("btnBranchName"+i).disabled = false;
					 		document.getElementById("btnAccountName"+i).disabled = false;
					 		document.getElementById("ifscCode"+i).disabled = false;
					 		document.getElementById("micrCode"+i).disabled = false;
					 	}
					   
					   if(document.getElementById("instType"+i).value!="1000000002")
					   {
					   document.getElementById("btnBankName"+i).disabled=false;
					   document.getElementById("btnBranchName"+i).disabled=false;
					   document.getElementById("btnAccountName"+i).disabled=false;
					   document.getElementById("ifscCode"+i).disabled=false;
					   document.getElementById("micrCode"+i).disabled=false;
					     $("#branchId"+i+"_temp").removeClass("readonly_text").addClass( "mandatory_readOnly_text" );
					    $("#bankId"+i+"_temp").removeClass("readonly_text").addClass( "mandatory_readOnly_text" );
					    $("#accountNo"+i+"_temp").removeClass("readonly_text").addClass( "mandatory_readOnly_text" );
					   }
					   else
					    {
					      document.getElementById("btnBankName"+i).disabled=true;
					  	  document.getElementById("btnBranchName"+i).disabled=true;
					  	  document.getElementById("btnAccountName"+i).disabled=true;
					  	  document.getElementById("ifscCode"+i).disabled=true;
					     document.getElementById("micrCode"+i).disabled=true
					    $("#branchId"+i+"_temp").removeClass("mandatory_readOnly_text").addClass( "readonly_text" );
					    $("#bankId"+i+"_temp").removeClass("mandatory_readOnly_text").addClass( "readonly_text" );
					    $("#accountNo"+i+"_temp").removeClass("mandatory_readOnly_text").addClass( "readonly_text" );
					    }
					   //document.getElementById("ifscCode"+i).disabled = false;
					   document.getElementById("mRemarks"+i).disabled = false;
					   document.getElementById("aRemarks"+i).disabled = true;
					    document.getElementById("aRemarks"+i).readOnly  = true;
					   
					   
					   
					 </logic:equal>
					 
					<logic:equal name ="activityInfo" property="activityMode" value="A">  
			         document.getElementById("aRemarks"+i).disabled = false;
			         document.getElementById("aRemarks"+i).readOnly = false;
			        </logic:equal>
			        	
			        
			        <logic:equal name ="activityInfo" property="activityMode" value="V">				//1.0.0.0.1
			        	enableDisable(i);
			        </logic:equal> 
			        
			        
			        
			        concatCleanupString(i);
			    
			   }
			   else
			   {
			     <logic:equal name ="activityInfo" property="activityMode" value="M">
			       
			           document.getElementById("instType"+i).disabled = true;
					   document.getElementById("instNo"+i).disabled = true;
					  // document.getElementById("instNo"+i+"_temp").disabled = true;
					   document.getElementById("instDate"+i).disabled = true;
					   document.getElementById("branchId"+i+"_temp").disabled = true;
					   document.getElementById("bankId"+i+"_temp").disabled = true;
					   document.getElementById("accountNo"+i+"_temp").disabled = true;
					   document.getElementById("ifscCode"+i).disabled = true;
					   document.getElementById("micrCode"+i).disabled = true;
					   document.getElementById("mRemarks"+i).disabled = true;
					   document.getElementById("rejectReason"+i).disabled = true;//1.0.0.13
					     // ravi start
					   document.getElementById("instNo"+i).value = "";
					   //document.getElementById("instNo"+i+"_temp").value = "";
					   document.getElementById("instDate"+i).value = "";
					   document.getElementById("branchId"+i+"_temp").value = "";
					   document.getElementById("bankId"+i+"_temp").value = "";
					   document.getElementById("accountNo"+i+"_temp").value = "";
					   document.getElementById("ifscCode"+i).value = "";
					   document.getElementById("micrCode"+i).value = "";
					   document.getElementById("mRemarks"+i).value = "";
					    // ravi end
					   document.getElementById("btnBankName"+i).disabled=true;
					   document.getElementById("btnBranchName"+i).disabled=true;
					   document.getElementById("btnAccountName"+i).disabled=true;
					  
			
					   document.getElementById("aRemarks"+i).disabled = true;
					   if(document.getElementById("instNo"+i+"_temp")!=null){
					   document.getElementById("instNo"+i+"_temp").disabled = true;
					   document.getElementById("instNo"+i+"_temp").value = "";
					   }//1.0.0.28 
					   
					 </logic:equal>
					 
					<logic:equal name ="activityInfo" property="activityMode" value="A">  
			         document.getElementById("aRemarks"+i).disabled = true;
			        </logic:equal>
			        
			        <logic:equal name ="activityInfo" property="activityMode" value="V">			//1.0.0.0.1
			        
			        	document.getElementById("instNo"+i).disabled = true;
					   document.getElementById("instDate"+i).disabled = true;
					   
			        </logic:equal> 
			        cleanupInstrumentNo(i);
			  }
			} 
			
			
			function checkRowChecked()
	{
	       var flag=false;
			
			var paymentTable = document.getElementById("addRow");
		    var rowCount = parseInt(paymentTable.rows.length,10)-parseInt(1,10);
		    for(var i=1; i<=rowCount; i++)
		     {
				
				     if(document.getElementById("payment_Row"+i).checked)
				     {
				      
				     
					     flag = true;      
										  
		             }
		     }
			    
		 
		  if(!flag)
		  {
		      alert(("At least one prospect must be selected.").toUpperCase());
		      return false;
		  }
		  return true;
		   
	 }	   
	 
	 
	 function approveRejectToggle(val)
			{
				<logic:notEmpty name ="activityInfo">
					<logic:notEmpty name ="activityInfo" property ="activityMode" >
						<logic:notEqual name ="activityInfo" property="activityMode" value ="AA">
							<logic:equal name ="activityInfo" property="activityMode" value ="A">
							
								if(val=="A")
								{
									document.forms[0].arApprove.checked=true;
									document.forms[0].arReject.checked=false;
									document.forms[0].arSendBack.checked=false;
								}
								else if(val=="R")
								{
									document.forms[0].arApprove.checked=false;
									document.forms[0].arReject.checked=true;
									document.forms[0].arSendBack.checked=false;
								}
								else if(val=="B")  // 1.0.0.11
								{
									document.forms[0].arApprove.checked=false;
									document.forms[0].arReject.checked=false;
									document.forms[0].arSendBack.checked=true;
								}
								else if(val=="BM")
								{
									document.forms[0].arApprove.checked=false;
									document.forms[0].arReject.checked=false;
									document.forms[0].arSendBack.checked=false;
								}
								else
								{
								    document.forms[0].arApprove.checked=false;
									document.forms[0].arReject.checked=false;
									document.forms[0].arSendBack.checked=false;
								}
								
								
							</logic:equal>
							//1.0.0.13 add start
							<logic:equal name ="activityInfo" property="activityMode" value ="M">
								if(val == "P"){
									document.forms[0].mrSendToAuthor.checked=true;
									document.forms[0].mrReject.checked=false;
								}
								else if(val == "R"){
									document.forms[0].mrSendToAuthor.checked=false;
									document.forms[0].mrReject.checked=true;
								}
								else{
									document.forms[0].mrSendToAuthor.checked=false;
									document.forms[0].mrReject.checked=false;
								}//1.0.0.13 add end
							</logic:equal>
						</logic:notEqual>
					</logic:notEmpty>
				</logic:notEmpty>
			}
			
			/* 1.0.0.0.2 start */
			function dateDiff(date1, date2)
			{
				  dArr = date1.split("-");
				  date1 = dArr[2]+ "/" +dArr[1]+ "/" +dArr[0];
					
				  dArr = date2.split("-");
				  date2 = dArr[2]+ "/" +dArr[1]+ "/" +dArr[0];
				    
				  var oneDay = 24*60*60*1000; 
				  var firstDate = new Date(date1);
				  var secondDate = new Date(date2);
				  //Start 1.0.0.23
				  <logic:equal name ="activityInfo" property="activityMode" value ="M">
					  var noOfDaysSet = document.forms[0].noOfDays.value;//Hemant
					  var prevNoOfDaysSet = document.forms[0].prevNoOfDays.value;//Hemant
					  var firstDate2 = new Date(date1);
					  var millisecondOffset = noOfDaysSet * 24 * 60 * 60 * 1000;
					  var millisecondOffset2 = prevNoOfDaysSet * 24 * 60 * 60 * 1000;//Hemant
					  firstDate.setTime(firstDate.getTime() + millisecondOffset); 
					  firstDate2.setTime(firstDate2.getTime() - millisecondOffset2); 
					  var diffDays = false;
					    if (secondDate > firstDate ||  secondDate < firstDate2) {
	            			 //tempData.errorMessages = tempData.errorMessages +"\n"+"Instrument Date must be less than business date.";
	            			 diffDays = true;
				        }else {
				        	diffDays = false;
				        }
				  </logic:equal>
				  //End 1.0.0.23
				  <logic:equal name ="activityInfo" property="activityMode" value ="A">
					  var diffDays = false;
					    if (firstDate < secondDate) {
	            			 //tempData.errorMessages = tempData.errorMessages +"\n"+"Instrument Date must be less than business date.";
	            			 diffDays = true;
				        }else {
				        	diffDays = false;
				        }
				    </logic:equal>
				  return diffDays;
	  		}
	  		/* 1.0.0.0.2 end */
	 				
	function printOption(no){
	
	//alert(no);
	var  disbursal_id=document.getElementById("disbDtlId"+no).value;
	var  prospectId=document.getElementById("prospectId"+no).innerHTML;
	
	//alert(disbursal_id);
	var  table_id=document.getElementById("payment_Row"+no).value;
	//alert(table_id);
	var  insNumber=  document.getElementById("instNo"+no).value;
	//alert("insNumber"+insNumber);
    var  payTo=  document.getElementById("entityName"+no).value;
   // alert("payTo"+payTo);
    removeCommaOfObject(document.getElementById("amount"+no));
    var  amt=  document.getElementById("amount"+no).value;
   // alert("amt"+amt);
    var  instDate=  document.getElementById("instDate"+no).value;
   // alert("instDate"+instDate);
   var bankId = document.getElementById("bankId"+no).value;
   //1.0.0.9 start
   var screenId="";
   var entityId ="";
   <logic:notEmpty name="screenId">
   		 screenId='<%=(String)request.getAttribute("screenId")%>';
   </logic:notEmpty>
   <logic:notEmpty name="entityId">
    	entityId='<%=(String)request.getAttribute("entityId")%>';
   </logic:notEmpty>
   //1.0.0.9 end
	document.getElementById("print"+no).disabled=true;
	
	var instType = document.getElementById("instType"+no).value;
	
	var eventName=document.getElementById("eventName"+no).value;
	
	//	alert("prospectId ="+prospectId);
		if(instType==="1000000001"){
			  window.open("losPrintCheque.do?actionPerformed=viewCheque&payTo="+payTo+"&disbursal_id="+disbursal_id+"&prospectId="+prospectId+"&screenId="+screenId+"&entityId="+entityId+"&amt="+amt+"&insNumber="+insNumber+"&instDate="+instDate+"&bankId="+bankId+"&eventName="+eventName,"ViewCheque","width=728, height=1024,top=100,left=100, location=no, maximize=yes, menubar=no, status=no, toolbar=no, scrollbars=no, resizable=yes","modal=yes" );
			 		
			setTimeout(function(){ 
			    window.open("losPrintCheque.do?actionPerformed=printCheque&payTo="+payTo+"&disbursal_id="+disbursal_id+"&prospectId="+prospectId+"&screenId="+screenId+"&entityId="+entityId+"&amt="+amt+"&insNumber="+insNumber+"&instDate="+instDate+"&bankId="+bankId+"&eventName="+eventName,"PrintCheque","width=728, height=1024,top=100,left=100, location=no, maximize=yes, menubar=no, status=no, toolbar=no, scrollbars=no, resizable=yes","modal=yes" );		
			
			 }, 500);				 
		 }
		else
		 {
		 	alert(("Print is applicable for cheques only.").toUpperCase());	 
		
		 }		
	}
	
	function advancePrintOption(no){
	
	var  prospectCode=document.getElementById("prospectCode"+no).innerHTML;
	var  taxInvoiceNo=  document.getElementById("taxInvoiceNo"+no).value;
	var  taxInvoiceDate=  document.getElementById("taxInvoiceDate"+no).value;
	var  disbDtlId=  document.getElementById("disbDtlId"+no).value;
    //document.getElementById("advancePrint"+no).disabled=true;
    
    var url =   "grfAction.do?actionPerformed=viewReportForProspect&screenId=1200108905&actionId=1200108905&prospectCode="+prospectCode+"&taxInvoiceDate="+taxInvoiceDate+"&taxInvoiceNo="+taxInvoiceNo+"&reportType=advancePayment&disbDtlId="+disbDtlId+"&viewHeaderFlag=Y";//1.0.0.5           
	var self=window.open(url,"Reports","titlebar=yes,scrollbars=yes,toolbar=no,maximize=yes,menubar=no,minimize=no,statusbar=no");
	}
	
function printCheque()
{
var payTo="Rahul Tripathi";
var payBy="0999999999";
window.open("losPrintCheque.do?payTo="+payTo+"&payBy="+payBy,"popBank","width=800, height=690,top=100,left=100, location=no, maximize=yes, menubar=no, status=no, toolbar=no, scrollbars=no, resizable=no","modal=yes" );
}
	
	
function enableDisable(i){																//1.0.0.0.1
	 <logic:equal name ="activityInfo" property="activityMode" value="V">
		
		var instrumentType = document.getElementById("instType"+ i).value;
		
		if(instrumentType==1000000005||instrumentType==1000000006){
			document.getElementById("instNo"+ i).disabled=false;
			document.getElementById("instDate"+ i).disabled=false;	
		}	
	
		if(instrumentType==1000000001)
			document.getElementById("instDate"+ i).disabled=false;
		
	</logic:equal>	
}	


</script>
	
</head>
<body onload="loadData();">
<html:form action="paymentAction.do?actionPerformed=displayPaymentScreen" method = "post">

<%@include file="../common/header.jsp" %>
<%@include file="../common/subHeader.jsp" %>
<html:hidden property="xmlData"/>
<input type="hidden" id="buisnessDate"/>


<div   id="vetiTd" >
<%@ include file="../vmenu/menu.jsp" %>
</div>
<div  class="toggling">
	<%@ include file="../vmenu/vmenuTgl.jsp" %>
</div>

<div  class="container-fluid">	
	<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
 	<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
					<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12" id="fieldsetId">
						<div class="legend col-lg-12 col-md-12 col-sm-12 col-xs-12">
							SEARCH CRITERIA 
						</div>
						<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
							
								  <div class="col-lg-2 col-md-2 col-sm-3 col-xs-6" >							   
									     Prospect Code									      
								     </div>
								     
								    <div class="col-lg-2 col-md-2 col-sm-3 col-xs-6" >	
								        <html:text property="colProspectCode" maxlength="20"/>
								     </div>
								    <div class="col-lg-1 col-lg-offset-1 col-md-1 col-md-offset-1  col-sm-3 col-xs-6" >								   
									    Cust Code
								     </div>
								    <div class="col-lg-2 col-md-2 col-sm-3 col-xs-6" >							  
								         <html:text property="colCustCode" maxlength="20"/>
								     </div>								     
								     
								     
							
							<!-- comment start by 1.0.0.17 -->
							        <%-- <div class="col-lg-1 col-lg-offset-1 col-md-1 col-md-offset-1  col-sm-3 col-xs-6" >								   
									    Branch Id
								     </div>
								     
								      <div class="col-lg-2 col-md-2 col-sm-3 col-xs-6" >		
								     <html:select property="colBranchId" style="width:130px" >
									 <html:option value=""><bean:message bundle="losApplicationResource" key="los.common.select"/></html:option>
									 <logic:notEmpty name="colLocationMaster" >
									 <html:options collection="colLocationMaster" property="masterId" labelProperty="masterName" />
									 </logic:notEmpty>
									 </html:select>
									 </div>
								     <div class="col-lg-2 col-md-2 col-sm-3 col-xs-6" >						   
									     PAN 								      
								     </div>
								     
								    <div class="col-lg-2 col-md-2 col-sm-3 col-xs-6" >
								      <html:text property="colPanNo" maxlength="10"/>	
								       
								     </div> --%>
								     <!-- comment end by 1.0.0.17 -->
								     <!-- add start 1.0.0.13 -->
								      <div class="col-lg-1 col-lg-offset-1 col-md-1 col-md-offset-1  col-sm-3 col-xs-6" >						   
									     Payment From 								      
								     </div>
								     
								     <div class="col-lg-2 col-md-2 col-sm-3 col-xs-6" >
										<html:text property="paymentFrom" styleId="paymentFrom" maxlength="11" size="18"  onblur="javascript:checkDateFormat(this,'Payment From');" />
										<a href='#' onClick="cal19.select(document.getElementById('paymentFrom'),'paymentFrom_link','dd-NNN-yyyy'); return false;"
						                   NAME="paymentFrom_link" ID="paymentFrom_link" title='Calendar' style="text-decoration: none">
						                   <i class="fa fa-calendar" aria-hidden="true" ></i> 
						                </a>
									</div>
									
									 <div class="col-lg-2 col-md-2 col-sm-3 col-xs-6" >							   
									     Payment To 								      
								     </div>
								     
								     <div class="col-lg-2 col-md-2 col-sm-3 col-xs-6" >
										<html:text property="paymentTo" styleId="paymentTo" maxlength="11" size="18"  onblur="javascript:checkDateFormat(this,'Payment To')" />
										<a href='#' onClick="cal19.select(document.getElementById('paymentTo'),'paymentTo_link','dd-NNN-yyyy'); return false;"
						                   NAME="paymentTo_link" ID="paymentTo_link" title='Calendar' style="text-decoration: none">
						                   <i class="fa fa-calendar" aria-hidden="true" "></i> 
						                </a>
									</div>
									<!-- add end 1.0.0.13 -->
								   
							<!-- Start Hemant -->
							<div class="col-lg-1 col-lg-offset-1 col-md-1 col-md-offset-1  col-sm-3 col-xs-6" >							   
									     Vendor Name 								      
								     </div>
								     <div class="col-lg-2 col-md-2 col-sm-3 col-xs-6" >
										 <html:hidden property="vendorId" styleId="vendorId"></html:hidden>
										 <html:text property="vendorName" styleId="vendorName" disabled="true" />
		      							 <input type="button" class="btn btn-primary btn-sm" name="btnVendorName"  value="..." style="top: 0px; right:0px;" id="paymentVendorBtn"  onclick='vendorChooser("VENDOR PAYMENT LOV");'>
				
									</div>
							
							<div class="col-lg-1 col-lg-offset-1 col-md-1 col-md-offset-1  col-sm-3 col-xs-6" >							   
									     Product 								      
								     </div>
								     
								     <div class="col-lg-2 col-md-2 col-sm-3 col-xs-6" >
										<html:select property="product" styleId="product" onchange="getOrixBankMster();">
											<html:option value="0"><bean:message bundle="quotationApplicationResources" key="quotation.common.select"/></html:option>
											<logic:notEmpty name="ProductListMaster">
												<logic:iterate id="itrProductListMaster" name="ProductListMaster">
													<logic:equal name="itrProductListMaster" property="masterParentId" value="1000000044">
														<option value=<bean:write name='itrProductListMaster' property='productId'/> >
															<bean:write name="itrProductListMaster"	property="productName" />
														</option>
													</logic:equal>
												</logic:iterate>
											</logic:notEmpty>
										</html:select>
									</div>
									
									<div class="col-lg-2 col-md-2 col-sm-3 col-xs-6" id="orixBankDivLabelId" style="display:none;">							   
									     Lessor Bank 								      
								     </div> <!--1.0.0.27  -->
								     
								     <div class="col-lg-2 col-md-2 col-sm-3 col-xs-6" id="orixBankDivTextId" style="display:none;">
										<html:select property="orixBank" styleId="orixBank" >
											<html:option value="0"><bean:message bundle="quotationApplicationResources" key="quotation.common.select"/></html:option>
											<logic:notEmpty name="OrixBankistMaster">
												<logic:iterate id="itrOrixBankListMaster" name="OrixBankistMaster">
														<option value='<bean:write name='itrOrixBankListMaster' property='masterId'/>' >
															<bean:write name="itrOrixBankListMaster"	property="masterName" />
														</option>
												</logic:iterate>
											</logic:notEmpty>
										</html:select>
									</div>
							<!-- End Hemant -->
							
							<div class="col-lg-2 col-md-2 col-sm-3 col-xs-6" >								   
									    
								     </div>
								     <div class="col-lg-2 col-md-2 col-sm-3 col-xs-6" >
								      <html:hidden property="colEntityName" />								  
								        <html:hidden property="noOfDays" styleId="noOfDays" />
								        <html:hidden property="prevNoOfDays" styleId="prevNoOfDays" />
								     </div>
								    <div class="col-lg-6 col-md-6 col-sm-6 col-xs-6 text-right" >						   

								     </div>
								     
								     
							<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 text-right" style="margin-top:4px;" >  
								<input type="button" name="searchBtn" value="Search" class="blueBotton" onclick="getSearchData(); " style="width:110px !important;;"/>	 
									  <input type="button" name="resetBtn" value="Reset" class="blueBotton" onclick="resetSearchCriteria(); " style="width:110px !important;;"/>
							</div>  <!-- 1.0.0.29 -->
									
								    <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 text-right" style="margin-top:4px;">						   
									   	<input type="button" name="downloadCSVReprotBtn" value="Vendor Payment Pending Report" class="blueBotton" onclick="downloadCSVReport(); " style="width:230px !important;;"/>
								     </div>

							     
										
						</div>
					</div>
		    
	</div>
	
	<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
				<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12" id="fieldsetId1">
						<div class="legend col-lg-12 col-md-12 col-sm-12 col-xs-12">
							PROSPECT LIST
						</div>
						
					<logic:empty name="listData">
						<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12"><font color="red" size="4">No Search Record Found.</font></div>
						
					</logic:empty>
	      
					<logic:notEmpty name="listData">	
						
						<!-- Multiple changes are done by Ravikant for browser compatibility -->
						<div id="listData" style="width:100%;height:325px;"> <!-- 1.0.0.4 -->
						<table id='addRow' class="main_body" align="center" border="0" style="width:3500px;"><!-- Changes Made By peeyush for alignment issue found in bug 14470  --><!-- 1.0.0.4 -->
		                <tr class="list_header" >
		                
		                						
						                        
												<td style="width:50px;" >
												    <input type="checkbox" onclick="selectAllRows(this)"><!-- 1.0.0.13 -->
													
												</td>
												<!-- // 1.0.0.3 start -->
												<td style="width:80px;display: none;" id="advancePaymentHeader" >
												    Advance Payment
													
												</td> <!--  // 1.0.0.3 end -->
												 <!-- 1.0.0.13 add start -->
												<td style="width:100px;">
												    Payment Date
													
												</td>
												<!-- 1.0.0.13 add end -->
												<logic:equal name ="activityInfo" property="activityMode" value="V">
						                        <td id="print" style="width:210px;">
												      Print
												</td>
						                        </logic:equal>
						                       
												<td style="width:100px;">
												    Prospect Code
													
												</td>
												<td style="display:none" >
													Entity Type
												</td>
												                  <!--added by 1.0.0.24  -->
												<td style="width:150px;">
												Vendor Name
												</td>
												                   <!--end by 1.0.0.24  -->
												<td style="width:150px;">
													Amount
												</td>
<!-- 												Added by Krishna Ravi -->
												<td style="width:150px;">
													TDS Amount
												</td>
<!-- 												//// -->
												
												<td style="width:150px;">
													Instrument Type
												</td>
												<td style="width:150px;">
													Issue In Favour Of
												</td>
												<td style="width:150px;">
													Instrument No.
												</td>
												
												<td   style="width:150px;">
													Instrument Date
												</td>
												
												
												
												<td style="width:150px;">
													Bank
												</td>
												<td style="width:150px;">
													Branch
												</td >
												<td style="width:150px;">
													Account No
												</td>
												<td style="width:150px;">
													IFSC Code
												</td>
												
												<td style="width:150px;">
													MICR Code
												</td>
												                                <!--1.0.0.25  -->
													<td style="width:150px;">
													Vendor Bank
												</td>
													<td style="width:150px;">
													Vendor Branch
												</td>
													<td style="width:150px;">
													Vendor Account
												</td>
												<td style="width:150px;">
													Vendor IFSC Code
												</td>
												<td style="width:150px;">
													Vendor MICR Code
												</td>                         <!-- 1.0.0.25 -->
												<!-- 1.0.0.13 add start -->
												<td style="width:150px;">
													Reject Reason
												</td>
												<!-- 1.0.0.13 add end -->
												<td style="width:150px;">
													Maker Remarks
												</td>
												<td style="width:150px;">
													Author Remarks
												</td>
                                               <!-- // 1.0.0.3 start -->
												<td style="width:50px;display: none;" id="advanceRemarksHeader">
												   Adv Payment Remarks
													
												</td>
                                             <!--  // 1.0.0.3 end -->
												<td id="status" style="width:150px;display:none">
												      Status
												</td>
												<!-- 1.0.0.31 add start -->
												<td style="width:150px;">
												      H2H Status
												</td>
												<td style="width:150px;">
												      Post Approval Rejection Source
												</td>
												<td style="width:150px;">
												      Post Approval Rejection User
												</td>
												<!-- 1.0.0.31 add end -->
												
											</tr>
											
											
						</table>
						<table  id ='addRow' border = '0' class="main_body">
								
	                    </table>
	                    </div>
	               	</logic:notEmpty>			
	
	
			 </div>
	</div>
	
	
	
	
	
	<logic:notEmpty name="listData">
				 <% int no = 1; %>
					<logic:iterate name ="listData" id="itrListData" >
					
					 <script>
					 
					 var flag = "<bean:write name='itrListData' property='printFlag'/>";
					 var status1= "<bean:write name='itrListData' property='arApprovalDecision'/>";
					 addNewRow("<bean:write name='itrListData' property='instrumentType'/>",flag,status1);
					
					 
					
					 // alert("added");
					 // alert("<bean:write name='itrListData' property='disbursalDtlId'/>");
					 // 1.0.0.3 start
                      <logic:equal name ="entityId"  value="1000000008">
					 if("<bean:write name='itrListData' property='advancePaymentFlag'/>"=="Y")
					 {
					   document.getElementById("advancePayment_Row"+<%=no%>).checked=true;
					   if(document.getElementById("advancePrint"+<%=no%>))
					   {
					     document.getElementById("advancePrint"+<%=no%>).disabled=false;
					   }
					   
					 }
					 document.getElementById("paymentDate"+<%=no%>).value = "<bean:write name='itrListData' property='paymentDate'/>";/* 1.0.0.13 */
					 document.getElementById("paymentRemarks"+<%=no%>).value = "<bean:write name='itrListData' property='advancePaymentRemarks'/>";
					 </logic:equal>
					 document.getElementById("taxInvoiceNo"+<%=no%>).value = "<bean:write name='itrListData' property='taxInvoiceNo'/>";
					 document.getElementById("taxInvoiceDate"+<%=no%>).value = "<bean:write name='itrListData' property='taxInvoiceDate'/>";
					 // 1.0.0.3 end
					 document.getElementById("prospectCode"+<%=no%>).innerHTML = "<bean:write name='itrListData' property='prospectCode'/>";
					 document.getElementById("prospectId"+<%=no%>).innerHTML = "<bean:write name='itrListData' property='prospectId'/>";
					 document.getElementById("prospectId"+<%=no%>).style.visibility = "hidden";
					 document.getElementById("disbId"+<%=no%>).value = "<bean:write name='itrListData' property='disbursalId'/>";
					 document.getElementById("disbDtlId"+<%=no%>).value = "<bean:write name='itrListData' property='disbursalDtlId'/>";
					 document.getElementById("branch"+<%=no%>).value = "<bean:write name='itrListData' property='branch'/>";
					 
					 document.getElementById("entityType"+<%=no%>).value = "<bean:write name='itrListData' property='entityType'/>";
					 <%--  clickEntityType(document.getElementById("entityType"+<%=no%>),<%=no%>);
					  document.getElementById("entityName"+<%=no%>).value = "<bean:write name='itrListData' property='entityName'/>";
 --%>					  
					  document.getElementById("entityName"+<%=no%>).value = "<bean:write name='itrListData' property='entityName'/>";
					 document.getElementById("entityName"+<%=no%>).title = "<bean:write name='itrListData' property='entityName'/>";//added by 1.0.0.12
					
					 document.getElementById("amount"+<%=no%>).value = "<bean:write name='itrListData' property='disbAmount'/>";
					 document.getElementById("instType"+<%=no%>).value = "<bean:write name='itrListData' property='instrumentType'/>";
					 document.getElementById("favourOf"+<%=no%>).value = "<bean:write name='itrListData' property='favourOf'/>";
					 document.getElementById("favourOf"+<%=no%>).title = "<bean:write name='itrListData' property='favourOf'/>";//added by 1.0.0.12
					 //Start 1.0.0.20
					 var instNo="<bean:write name='itrListData' property='instrumentNo'/>";
					 instNo=instNo.replace(/&quot;/g, '\\"');
					 instNo=instNo.replace("&#39;", "'");
					 document.getElementById("instNo"+<%=no%>).value =instNo;
					 //End 1.0.0.20
					//Mayank Agrawal 19-10-2014 
					 if(document.getElementById("instNo"+<%=no%>+"_temp"))
					{
					document.getElementById("instNo"+<%=no%>+"_temp").value = "<bean:write name='itrListData' property='instrumentNo'/>";
					 if(document.getElementById("btnInstNo"+<%=no%>)){
					 <logic:equal name ="activityInfo" property="activityMode" value="A">
					 document.getElementById("btnInstNo"+<%=no%>).disabled=true;
					 </logic:equal>
					 }
					 
					 }
					 document.getElementById("instDate"+<%=no%>).value = "<bean:write name='itrListData' property='instrumentDate'/>";
					 
					
					 document.getElementById("bankId"+<%=no%>).value = "<bean:write name='itrListData' property='instrumentBankId'/>";
					 document.getElementById("bankId"+<%=no%>+"_temp").value = "<bean:write name='itrListData' property='instrumentBankId_temp'/>";
					 
					 
					 document.getElementById("branchId"+<%=no%>).value = "<bean:write name='itrListData' property='instrumentBranchId'/>";
					 document.getElementById("branchId"+<%=no%>+"_temp").value = "<bean:write name='itrListData' property='instrumentBranchId_temp'/>";
					 
					 document.getElementById("accountNo"+<%=no%>).value = "<bean:write name='itrListData' property='instrumentAccountNumber'/>";
					 document.getElementById("accountNo"+<%=no%>+"_temp").value = "<bean:write name='itrListData' property='instrumentAccountNumber_temp'/>";
					 
					 
					 document.getElementById("ifscCode"+<%=no%>).value = "<bean:write name='itrListData' property='ifscCode'/>";
					  document.getElementById("micrCode"+<%=no%>).value = "<bean:write name='itrListData' property='micrCode'/>";
					
					 document.getElementById("mRemarks"+<%=no%>).value = "<bean:write name='itrListData' property='mRemarks'/>";
					 document.getElementById("aRemarks"+<%=no%>).value = "<bean:write name='itrListData' property='aRemarks'/>";
					 //document.getElementById("aStatus"+<%=no%>).value = "<bean:write name='itrListData' property='arApprovalDecision'/>";
					 
					
					 document.getElementById("sbankId"+<%=no%>+"_temp").value = "<bean:write name='itrListData' property='supplier_Bank'/>";
					 
					 document.getElementById("product"+<%=no%>).value = "<bean:write name='itrListData' property='product'/>";//added by 1.0.0.15
					
					 document.getElementById("sbranchId"+<%=no%>+"_temp").value = "<bean:write name='itrListData' property='supplier_Branch'/>";
					 
					
					 document.getElementById("saccountNo"+<%=no%>).value = "<bean:write name='itrListData' property='supplier_Account'/>";
					 
					 
					 document.getElementById("sifscCode"+<%=no%>).value = "<bean:write name='itrListData' property='supplier_IFSC'/>";
					
					 document.getElementById("smicrCode"+<%=no%>).value = "<bean:write name='itrListData' property='supplier_MICR'/>";
				  
					//Added by Rinky Arora : 10-FEB-2015
					document.getElementById("eventName"+<%=no%>).value = "<bean:write name='itrListData' property='eventName'/>";
					
					document.getElementById("tdsAmount"+<%=no%>).value = "<bean:write name='itrListData' property='tdsAmount'/>"; //Krishna Ravi 
					 
					// 1.0.0.31 add start
					document.getElementById("H2H_STATUS"+<%=no%>).value = "<bean:write name='itrListData' property='h2hStatus'/>";
					document.getElementById("POST_APPR_REJ_SOURCE"+<%=no%>).value = "<bean:write name='itrListData' property='postApprRejSource'/>";
					document.getElementById("POST_APPR_REJ_USER"+<%=no%>).value = "<bean:write name='itrListData' property='postApprRejUser'/>";
					// 1.0.0.31 add end
					 
					<%no++;%>
					 </script>
					
					</logic:iterate>
	</logic:notEmpty>
	<logic:notEmpty name="listData">
	 <div id="pageNavPosition"></div> 
	   <logic:notEmpty name="activityInfo">
				
					<logic:notEmpty name ="activityInfo" property="activityMode" >
						<logic:notEqual name ="activityInfo" property="activityMode" value="AA">
						<logic:equal name ="activityInfo" property="activityMode" value="M">
	<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
			
				<div class="legend col-lg-12 col-md-12 col-sm-12 col-xs-12">
					MAKER FIELDS
				</div> 
				<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
						<logic:notEqual name = "activityInfo" property ="activityType" value="BATCH_PRESENTATION">
							<logic:notEqual name = "activityInfo" property ="activityType" value="BATCH_CLEARANCE">
								<div class="col-lg-2 col-md-2 col-sm-3 col-xs-6" ><font size="2">
								<input type = "hidden" name ="mrApprovalDecision"><!-- 1.0.0.13 -->
								<bean:message bundle="lmsApplicationResource" key = "lms.makerRemark.sendToAuthor"/></font></div>
								<div class="col-lg-2 col-md-2 col-sm-3 col-xs-6" ><input type="checkbox" id="approveCheck" name="mrSendToAuthor" value="P" onclick="javascript:approveRejectToggle(document.forms[0].mrSendToAuthor.value);toggleRejectReason('P')" /></div>
								<!-- 1.0.0.13 add start -->
								<div class="col-lg-2 col-md-2 col-sm-3 col-xs-6" ><font size="2">
								<input type = "hidden" name ="mrApprovalDecision"><!-- 1.0.0.13 -->
								<bean:message bundle="lmsApplicationResource" key = "lms.authorRemark.reject"/></font></div>
								<div class="col-lg-2 col-md-2 col-sm-3 col-xs-6" ><input type="checkbox" id="rejectCheck" name="mrReject" value="R" onclick="javascript:approveRejectToggle(document.forms[0].mrReject.value);toggleRejectReason('R')" /></div>
								<!-- 1.0.0.13 add end  -->
							</logic:notEqual>
						</logic:notEqual>
				
				</div>
				<logic:notEmpty name ="remarksInfo">
				<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
					
						<div class="col-lg-2 col-md-2 col-sm-3 col-xs-6" >
							MakerID
						</div>
						
						
						
						<div class="col-lg-2 col-md-2 col-sm-3 col-xs-6" >
						<bean:write name="remarksInfo" property="makerId"/>
						</div>
						<div class="col-lg-2 col-md-2 col-sm-3 col-xs-6" >
							Last Updated Date
						</div>
						
						
					<div class="col-lg-2 col-md-2 col-sm-3 col-xs-6" >
						<bean:write name="remarksInfo" property="makerDate" />
						</div>
				</div>
				</logic:notEmpty>												
			
	</div>
	
				</logic:equal>
						<logic:equal name ="activityInfo" property="activityMode" value="A">
							


<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
				<div class="legend col-lg-12 col-md-12 col-sm-12 col-xs-12">
					AUTHOR FIELDS
				</div>
				<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
						<logic:notEqual name = "activityInfo" property ="activityType" value="BATCH_PRESENTATION">
							<logic:notEqual name = "activityInfo" property ="activityType" value="BATCH_CLEARANCE">
											
								<div class="col-lg-2 col-md-2 col-sm-3 col-xs-6" >
								<logic:equal name ="activityInfo" property="activityMode" value="A"> 
								<bean:message bundle="lmsApplicationResource" key = "lms.authorRemark.approvalDecision"/></logic:equal>
								</div>
								<div class="col-lg-2 col-md-2 col-sm-3 col-xs-6" ><logic:equal name ="activityInfo" property="activityMode" value="A">&nbsp;</logic:equal></div>
							</logic:notEqual>
						</logic:notEqual>
						<logic:notEqual name = "activityInfo" property ="activityType" value="BATCH_PRESENTATION">
							<logic:notEqual name = "activityInfo" property ="activityType" value="BATCH_CLEARANCE">
								<logic:notEqual name = "activityInfo" property ="activityType" value="FORECLOSURE">
									<logic:notEqual name = "activityInfo" property ="activityType" value="PDC_GENERATE">
										<logic:notEqual name = "activityInfo" property ="activityType" value="PDC_EDIT">
										<logic:notEqual name = "activityInfo" property ="activityType" value="OTC">
											<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
														
														<div class="col-lg-2 col-md-2 col-sm-3 col-xs-6" ><bean:message bundle="lmsApplicationResource" key = "lms.authorRemark.approve"/>
															<input type = "hidden" name ="arApprovalDecision">
															<input type="checkbox" name="arApprove" value="A" onclick="javascript:approveRejectToggle(document.forms[0].arApprove.value)">
														</div>
														<div class="col-lg-2 col-md-2 col-sm-3 col-xs-6" >
															<bean:message bundle="lmsApplicationResource" key = "lms.authorRemark.sendBackToMaker"/>
														 	<input type="checkbox" name="arReject" value="R" onclick="javascript:approveRejectToggle(document.forms[0].arReject.value)">
														<input type = "hidden" name ="arApprovalDecision">
														</div>
														
														<div class="col-lg-2 col-md-2 col-sm-3 col-xs-6" > <!-- 1.0.0.11 start -->
															<bean:message bundle="lmsApplicationResource" key = "lms.authorRemark.reject"/>
														 	<input type="checkbox" name="arSendBack" value="B" onclick="javascript:approveRejectToggle(document.forms[0].arSendBack.value)">
														<input type = "hidden" name ="arApprovalDecision">
														</div>  <!-- 1.0.0.11 end -->
											</div>
											</logic:notEqual>
										</logic:notEqual>
								 	 </logic:notEqual>
						 	 	</logic:notEqual>
						 	</logic:notEqual>
						 </logic:notEqual>
						 
						 
					</div>
				<logic:notEmpty name ="remarksInfo">
				<logic:notEmpty name ="remarksInfo" property="authorId">
					<div class="col-lg-6 col-md-6 col-sm-12 col-xs-12">
						<div class="col-lg-3 col-md-3 col-sm-3 col-xs-6" >
								AuthorID
						</div>
						<div class="col-lg-3 col-md-3 col-sm-3 col-xs-6" >
							<bean:write name="remarksInfo" property="authorId"/>
						</div>
						<div class="col-lg-3 col-md-3 col-sm-3 col-xs-6" >
								Last Updated Date
						</div>
						<div class="col-lg-3 col-md-3 col-sm-3 col-xs-6" >
							<bean:write name="remarksInfo" property="authorDate" />
						</div>
					</div>
				</logic:notEmpty>
				</logic:notEmpty>	
</div>


							</logic:equal>
						</logic:notEqual>
					</logic:notEmpty>
				
			</logic:notEmpty>
	</logic:notEmpty>

	<script type="text/javascript">  
	  
  		var rec=0;
 	 	var entry=1;
  		var entry1=1;
  		var entry2=1;
  		var pageNos;
  		var viewPages = 5;
        var pager = new Pager('addRow', 50);  
       
        pager.init();  
        pager.showPageNav('pager', 'pageNavPosition');  
        pager.showPage(1); 
         
        function Pager(tableName, itemsPerPage) {
    		this.tableName = tableName;
    		this.itemsPerPage = itemsPerPage;
    		this.currentPage = 1;
    		this.pages = 0;
   			this.inited = false;
    
    		this.showRecords = function(from, to) {    
		    	var rows = 0;
    	
    			if(document.getElementById(tableName))
    			{
    		 		rows = document.getElementById(tableName).rows;
            	}
    	
        		// i starts from 1 to skip table header row
        		for (var i = 1; i < rows.length; i++) {
            		if (i < from || i > to)  
                		rows[i].style.display = 'none';
            		else
                		rows[i].style.display = '';
        		}
    		};
 
    		this.showPage = function(pageNumber) {
    			if (! this.inited) {
    				return;
    			}
    			var oldPageAnchor;
    	
    			if(document.getElementById('pg'+this.currentPage))
    			{ 
		       		oldPageAnchor = document.getElementById('pg'+this.currentPage);
		        	oldPageAnchor.className = 'pg-normal';
    			}
    			
        		entry=this.currentPage;
        		entry1=pageNumber;
        		
       			this.currentPage = pageNumber;
         		pageNos=pageNumber;
       
         		if (entry != entry1 ||  entry2==3)
    			{
    				entry2=3;
    				next1(this.currentPage);
    			}
        		var newPageAnchor;
        		if(document.getElementById('pg'+this.currentPage))
        		{
         			newPageAnchor = document.getElementById('pg'+this.currentPage);
         			newPageAnchor.className = 'pg-selected';
        		}
         
        		var from = (pageNumber - 1) * itemsPerPage + 1;
        		var to = from + itemsPerPage - 1;
       
        		this.showRecords(from, to);
    		} ;  
    
    		this.prev = function() {
        		if (this.currentPage > 1)
           		{
            		this.showPage(this.currentPage - 1);
             		next1(this.currentPage);
            	}
            
            	if((this.currentPage)%viewPages==0)
            	{
        			nextPrevNav(this.currentPage-viewPages+1);
        			this.showPage(this.currentPage);
        		}
    		};
    
    		this.next = function() {
        		if (this.currentPage < this.pages) { 
            		this.showPage(this.currentPage + 1); 
            		next1(this.currentPage);
        		}
        		if((this.currentPage-1)%viewPages==0){
        			nextPrevNav(this.currentPage);
        			this.showPage(this.currentPage);
        		}
        		
    		} ;                       
    
    		this.init = function() {
    	
    			if(document.getElementById(tableName))
    			{
		        	var rows = document.getElementById(tableName).rows;
		     		<logic:notEmpty name="listData">
					<logic:iterate name ="listData" id="itrListData" >	
						 
					 	rec="<bean:write name='itrListData' property='maxPage'/>";
					 	
					 </logic:iterate>
					 </logic:notEmpty>
		        	this.pages = Math.ceil(rec);
    			}
        		this.inited = true;
    		};

    		this.showPageNav = function(pagerName, positionId) {
    			if (! this.inited) {
    				return;
    			}
    			if(document.getElementById(positionId))
    			{
    				var element = document.getElementById(positionId);
    				var endPage = 5;
    				if(rec<=endPage)
    				endPage = rec;
    				
    				var pagerHtml = '<span onclick="' + pagerName + '.first();" class="pg-normal">  First </span> |';
    					pagerHtml += '<span onclick="' + pagerName + '.prev();" class="pg-normal">  Prev </span> | ';
        			for (var page = 1; page <= endPage; page++) 
        			{
            			pagerHtml += '<span id="pg' + page + '" class="pg-normal" onclick="' + pagerName + '.showPage(' + page + ');">' + page + '</span> | ';
            		}	
        			pagerHtml += '<span onclick="'+pagerName+'.next();" class="pg-normal"> Next </span>|'; 
        			pagerHtml += '<span onclick="'+pagerName+'.last();" class="pg-normal"> Last </span>';
          
        			element.innerHTML = pagerHtml;
    			}
    		};
    		
    		
    		
    this.first = function() {
    
        		if(!(this.currentPage==1)){ 
        		 pager.showPageNav('pager', 'pageNavPosition');  
        		 pager.showPage(1);
        		}
    		} ; 
   
   
   this.last = function() {
 				
 				var value = parseInt(rec/viewPages);
 				var no = (value*viewPages);
 				value = (no == rec ? (value-1)*viewPages : (value*viewPages)) + 1;
 				if(rec>viewPages){
        		nextPrevNav(value);
        		pager.showPage(rec); 
        		}
        		
    		} ;  		
    		
	}
	
	function next1(pageNo)
	{
		//Start 1.0.0.21
		var toDate = '<%=session.getAttribute("PaymentToDt")%>';
		  var fromDate = '<%=session.getAttribute("PaymentFromDt")%>';
		  var screenIdV = '<%=request.getParameter("screenId")%>';
		$.post("paymentAction.do?actionPerformed=displayPaymentScreenAjax&pageNo="+pageNo+"&paymentToDate="+toDate+"&paymentFromDate="+fromDate+"&screenId="+screenIdV,{
			//End 1.0.0.21
			},function(data, status)
			{
				$('#listData').empty();
				$('#listData').append(data);	
				
				//Start 1.0.0.21
				   <logic:notEmpty name="listData">
			       		<logic:equal name ="entityId"  value="1000000008">
						     document.getElementById("advancePaymentHeader").style.display = "table-cell";
						     document.getElementById("advanceRemarksHeader").style.display = "table-cell";
				   		</logic:equal>
			   		</logic:notEmpty>
			   		//End 1.0.0.21
			});
		 
	}
         
  function nextPrevNav(currentPage){
  
    			var endPage = currentPage+viewPages-1;
    			if(rec<=endPage)
    				endPage = rec;    			
        		var element = document.getElementById("pageNavPosition");
    			var pagerHtml = '<span onclick="' + 'pager' + '.first();" class="pg-normal"> First </span> | ';
    				pagerHtml += '<span onclick="' + 'pager' + '.prev();" class="pg-normal"> Prev </span> | ';
        		for (var page = currentPage; page <= endPage; page++) 
        		{
            		pagerHtml += '<span id="pg' + page + '" class="pg-normal" onclick="' + 'pager' + '.showPage(' + page + ');">' + page + '</span> | ';
        		}
        		pagerHtml += '<span onclick="'+'pager'+'.next();" class="pg-normal"> Next </span>|'; 
        		pagerHtml += '<span onclick="'+'pager'+'.last();" class="pg-normal"> Last </span>';

        		//element.innerHTML="";           
        		element.innerHTML = pagerHtml;
        		
  }
  
  //start 1.0.0.22
  function decimalNoFix(amtVal){
	  var numval = removeComma(amtVal.value);
		 var strVal = numval.toString();
		 if(strVal.includes(".")){
		 	 var valNum = strVal.slice(0, (strVal.indexOf(".")) + 2 + 1);
	  		amtVal.value = Number(valNum);
		 }
  }
  //End 1.0.0.22
  
  
  //start Hemant
   function vendorChooser(KeyName)
    {
	  
     var ParamValue = "";
	  window.open("quotationMaster.sprg?KeyName="+KeyName+"&ParamValue="+ParamValue+"&label=Vendor Chooser&moduleType=quotation&dependent=vendorPayment&defaultSearchFlag=N","popDealer","width=500, height=490,top=100,left=100, location=no, maximize=yes, menubar=no, status=no, toolbar=no, scrollbars=no, resizable=no","modal=yes" );
    }
  
  function resetSearchCriteria(){
	  document.forms[0].colCustCode.value = "";
	  document.forms[0].colProspectCode.value = "";
	  document.forms[0].paymentFrom.value='<bean:write name ="llmSessionUserInformationDTO" property="businessDate"/>';
	  document.forms[0].paymentTo.value='<bean:write name ="llmSessionUserInformationDTO" property="businessDate"/>';
	  document.getElementById("product").options[0].selected=true;
	  document.getElementById("vendorId").value="";
	  document.getElementById("vendorName").value="";
	  document.getElementById("orixBank").options[0].selected=true;
  }
  
 </script>  
 
 
<script>
//start 1.0.0.23
function downloadCSVReport(){
	  
	  if(document.forms[0].paymentFrom.value == "" || document.forms[0].paymentFrom.value == null || document.forms[0].paymentTo.value == "" || document.forms[0].paymentTo.value == null){
		  	alert("PAYMENT FROM DATE AND PAYMENT TO DATE MUST BE SPECIFIED.".toUpperCase());
		  	return;
		}
	  window.open("paymentAction.do?actionPerformed=downloadVendorPaymentPendingReport&fromDate="+document.forms[0].paymentFrom.value+"&toDate="+document.forms[0].paymentTo.value);
}

function getOrixBankMster(){
	  var productId =  document.getElementById("product").value;
	  $.ajax({
        url: 'paymentAction.do?actionPerformed=getOrixBankMaster',
        type: 'POST',
        data: {
      	  productId:productId
			},          
        success: function (response) {
        	if(response!=null && response!="") 
		    	{
					var resJson = JSON.parse(response);
					
					var select = document.getElementById("orixBank"); 

					// Optional: Clear all existing options first:
					select.innerHTML = "";
					select.innerHTML += "<option value='0'>SELECT</option>";
					// Populate list with options:
					for(var i = 0; i < resJson.length; i++) {
					    select.innerHTML += "<option value=\"" + resJson[i].masterId + "\">" +  resJson[i].masterName + "</option>";
					}
		    	}
        	else
        		{
        		alert(("Empty response generated.").toUpperCase());
        		}
        													                	
        }
    });
	 
}
	var noOfdaysVal = "";
	var prevNoOfDays = "";
	
			<logic:notEmpty name="listData">
				<logic:iterate name ="listData" id="itrListData" >
						noOfdaysVal = "<bean:write name='itrListData' property='noOfDays'/>"; 
						prevNoOfDays = "<bean:write name='itrListData' property='prevNoOfDays'/>";
						
						document.getElementById("noOfDays").value = noOfdaysVal;
						document.getElementById("prevNoOfDays").value = prevNoOfDays;
				</logic:iterate>
			</logic:notEmpty>
			
	//End 1.0.0.23
	
</script>
	
	<!-----------------------------------------------//1.0.0.0.1--------------------------------------------------------------------------------------------  -->
	
	</div>
</div>
	<jsp:include page="../common/footer.jsp" />
	


</html:form>
</body>
</html:html>
<script src="vmenu/js/toggleMenu.js"></script>
<script src="js/bootstrap.min.js"></script>
<script src="js/slimscroll.js"></script>
