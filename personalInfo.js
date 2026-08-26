/* HEADER INFO


+  File NAME 	: views/js/personalinfo.js
+  PURPOSE		: 
+  CREATED BY	: 
+  CREATION DATE	: 
+  INITIAL VERSION : 1.0.0.0
+  **********************************************************************************************************************************
+  VERSION NO   UPDATED BY       UPDATED ON      REASON FOR CHANGE
+  1.0.0.0.1        Ravi		  12- Oct          bug. 36416
+  1.0.0.0.2        Rahul		  12-Jun-2017      CR(GST)
+  1.0.0.0.3        Vibhash       06-july-2017     Need to make GSTIN No. non mandatory in GST Address
+  2.0.0.0.0      Apurva Shukla   15-jan-2018     GSTIN NO. is mandatory in Dm module billing address when destination checkbox is checked
+  2.0.0.0.1      Apurva Shukla   18-jan-2018     showing sez zone details on clicking on view button in billing address in dm module (Regarding GST changes )
+  2.0.0.0.2      Apurva Shukla   18-jan-2018     for gst cr sez zone
+  1.0.0.0        Apurva Shukla     06th-jun-2018   product dependent on lob
+  1.0.0.4        Apurva Shukla     03-july-2018   UAT CR regarding pincode
+  1.0.0.5        Ravi            23-july-2018     UAT  issue for pan card validation
+  1.0.0.6        Ravi            31-july-2018     CR for add validation incase for changing in IGST per.
   1.0.0.7		  Apurva		 29-Nov-2019		changes made for response flag(click again on save give popup)
+  1.0.0.8       apurva shukla   05 feburary 2019   changes regarding GST/HSN/SEZ changes
   1.0.0.9		 Nishant			18-Feb-2019		code commentted regarding disable button on edit in case of dm pde.
+  1.0.0.10        Ravi            19-Oct-2019     code change for city pin code validation
+  1.0.0.11        Ravi            07-Apr-2020     code change for disabled  same as field
+  1.0.0.12		Himanshu			09-Jun-2021		Special character validation in address field
+  1.0.0.13		Hemant Kumar		03/DEC/2021		Customer >> key contact module mendatory validation CR
+  1.0.0.14		Priyanka Soni 	 11-Mar-2022	 SEZ value at the address level
+  1.0.0.15		Priyanka Soni 	 11-Apr-2022	 Bug 108872 Showing wrong message while entering special character in the key Contact field.
+  1.0.0.16		Priyanka Soni 	 26-Apr-2022	 The first row of KEY CONTACTS should not be freeze.
+  1.0.0.17     Ahtesham Husain  30-09-2022      Key contact will still be mandatory but some of the field of key contact will become non mandatory
+  1.0.0.18     Hemant Kumar	  22-DEC-2022      KYC Complaince CR
+  1.0.0.19     Narottam Biswal   06-OCT-2023    Bug 125536: Error in Initiating Dedupe(validation added on address of AUTHORIZED SIGNATORY and BENEFICIARY OWNER)
+  1.0.0.20     Narottam Biswal   09-JAN-2023    CR- LPI details to be added in applicant details screen
+  1.0.0.21     Nalin Kumar Jena  08-FEB-2024    CR-Udyam No. - Customer level attribute for non-individual.
+  1.0.0.22     Narottam Biswal     22-FEB-2023         CR-DL varification
+  1.0.0.23     Nalin Kumar Jena    22-FEB-2024         CR-Passport No varification
+  1.0.0.24     Tanisha Agarwal     22-FEB-2024         CR-Voter ID varification
+  1.0.0.25     Ravi Shankar      26-FEB-2024         CR-Udyam Registration varification
+  1.0.0.26     Tanisha Agarwal   25-APR-2024         Integration API'S Response Data
+  1.0.0.27          Tanisha Agarwal    26-Jun-2024    CR-Udyam Registration varification on individual
+   1.0.0.28      Tanisha Agarwal       17-Dec-2024            CIBIL NEW CHANGES
+  1.0.0.29     Ahtesham Husain    20-Mar-2025   Mandatory validation for Designation in Authorised signatory.
+  1.0.0.30      Ravi Shankar          09-Apr-2026            GST Registration Status
+  1.0.0.31     Ahtesham Husain    17-Apr-2025   161618 - Lease Authorized signatory Details are Mandatory.
+  1.0.0.32     Sanchi Agarwal     26-Aug-2026   Added DOB, Gender and Pincode fields under Authorised Signatory in Personal Info


 +  **********************************************************************************************************************************
 */// 2.0.0.0.0 Start 
      function postPanRequest(strURL,obj)
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
         xmlHttp.open('POST', strURL, false);
         xmlHttp.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
         xmlHttp.onreadystatechange = function()
                                      {
                                          if (xmlHttp.readyState == 4)
                                          {    
                                                  updatePan(xmlHttp.responseText,obj);
                                          }
                                     };
                                    
          xmlHttp.send(strURL);

    }
        function updatePan(str,obj)
      { 
      
      	var info = str.split("~");
              
        if(info[0]!="NA")
        { 
              if(info[0]!=document.forms[0].applicantCode.value)
        	{ 
       		  alert(("Another customer \""+info[1]+"\" with user id \""+info[0]+"\", already exists with the same PAN number.").toUpperCase());
              obj.value="";
              obj.focus();
             }
        }
     }
   
   function populateOffCompanyName()
	{	
	 document.forms[0].offCompanyName.disabled = false;
	 document.forms[0].offCompanyName.value =document.forms[0].nameOfCompany.value;
	 document.forms[0].offCompanyName.disabled = true;
	
	}

	function validateAuthorisedSignotoryRows()
	{
		var vResponseSplit = availableauthSignRows.split("~");
		//start 1.0.0.31
		if(document.forms[0].custEntityType.value=='1000000002'){
			if(authSignRowCount <=0){
				tempData.errorMessages = tempData.errorMessages +"\nAuthorized Signatory Row must be specified";
			}
		}
		//end 1.0.0.31
		if(document.forms[0].custEntityType.value=='1000000002'){
				var vauthSignFName;
		     var vauthSignLName;
		     var vauthSignMName;
		     var vauthSignDesignation;         
		     var vauthSignDinNo;
		     var vauthSignMailId;
		     var vauthSignContactNo;
		     var vauthSignDelegation;
		     var vauthSignGender; //1.0.0.32 
		     var vauthSignDob; //1.0.0.32 
		     var vauthSignPincode; //1.0.0.32 
		     var sernum = 0;
		     for(var i=0; i < vResponseSplit.length; i++)
				{		
					
					
					if(vResponseSplit[i].length > 0)
					{
		    		 sernum++;
					//tempauthSignIdStr=document.getElementById(('authSignId'+vResponseSplit[i]));
		    		 	vauthSignFName=document.getElementById(('authSignFName'+vResponseSplit[i]));	
						vauthSignLName = document.getElementById(('authSignLName'+vResponseSplit[i]));					
						vauthSignMName = document.getElementById(('authSignMName'+vResponseSplit[i]));
						vauthSignDesignation = document.getElementById(('authSignDesignation'+vResponseSplit[i]));
						vauthSignDinNo = document.getElementById(('authSignDinNo'+vResponseSplit[i]));
						vauthSignMailId = document.getElementById(('authSignMailId'+vResponseSplit[i]));			
						vauthSignContactNo=document.getElementById(('authSignContactNo'+vResponseSplit[i]));
						vauthSignDelegation=document.getElementById(('authSignDelegation'+vResponseSplit[i]));
						vauthSignGender = document.getElementById(('authSignGender'+vResponseSplit[i])); //1.0.0.32 
						vauthSignDob = document.getElementById(('authSignDob'+vResponseSplit[i])); //1.0.0.32 
						vauthSignPincode = document.getElementById(('authSignPinCode'+vResponseSplit[i])); //1.0.0.32 
						
						//Start 1.0.0.18
						/*if(document.forms[0].custEntityType.value=='1000000002'){
							validate(document.getElementById(('authSignFName'+vResponseSplit[i])),"TMV",'Signatory First Name');
							validate(document.getElementById(('authSignLName'+vResponseSplit[i])),"TMV",'Signatory Last Name');
							validate(document.getElementById(('authSignPan'+vResponseSplit[i])),"TMV",'Signatory Pan');
							validate(document.getElementById(('authSignAddress'+vResponseSplit[i])),"TMV",'Signatory Address');
							validate(document.getElementById(('authSignState'+vResponseSplit[i])),"TMV",'Signatory State');
							validate(document.getElementById(('authSignCity'+vResponseSplit[i])),"TMV",'Signatory City');
						}*/
						//End 1.0.0.18
						
						if(i>1)
						{
							validate(vauthSignFName,"TMV",'Signatory First Name at row no '+sernum);
							validate(vauthSignLName,"TMV",'Signatory Last Name at row no '+sernum);
							//validate(vauthSignContactNo,"TMV",'Signatory Contact No at row no '+sernum);
							validate(vauthSignFName,"NV",'Signatory First Name at row no '+sernum);
							validate(vauthSignLName,"NV",'Signatory Last Name at row no '+sernum);
							validate(vauthSignMName,"NV",'Signatory Middle Name at row no '+sernum);
							validate(vauthSignDesignation,"DDMV",'Signatory Designation at row no '+sernum); //Add 1.0.0.29
							validate(vauthSignGender,"TMV",'Signatory Gender at row no '+sernum); //1.0.0.32 
							validate(vauthSignDob,"TMV",'Signatory DOB at row no '+sernum); //1.0.0.32 
							validate(vauthSignMailId,"EV",'Signatory Email at row no '+sernum);
							validate(vauthSignDinNo,"TNV",'Signatory DIN No at row no '+sernum);
						 	//validate(vauthSignContactNo,"TNV,NZV",'Signatory Contact No at row no '+sernum);
							validate(vauthSignDelegation,"NV",'Signatory Delegation at row no '+sernum);
							validate(document.getElementById(('authSignPan'+vResponseSplit[i])),"TMV",'Signatory Pan at row no '+sernum);
							validate(document.getElementById(('authSignAddress'+vResponseSplit[i])),"TMV",'Signatory Address at row no '+sernum);
							validate(document.getElementById(('authSignState'+vResponseSplit[i])),"TMV",'Signatory State at row no '+sernum);
							validate(document.getElementById(('authSignCity'+vResponseSplit[i])),"TMV",'Signatory City at row no '+sernum);
							validate(vauthSignPincode,"TMV",'Signatory Pincode at row no '+sernum); //1.0.0.32 
						
						 if(tempData.errorMessages=="Errors:")
							{
								if(vauthSignDinNo.value.length!=0  && vauthSignDinNo.value.length!=8 )
								{
								tempData.errorMessages=tempData.errorMessages+"\n Signatory DIN No.at row no "+sernum+" should be 8 digit numeric value.";
								}
								
								if(vauthSignContactNo!="")
								{
								  if(!(validMobile(vauthSignContactNo)))
								  {	 break; }
								}
								
								
							}
						}
						else
						{
							validate(vauthSignFName,"NV,TMV",'Signatory First Name');
							validate(vauthSignLName,"NV,TMV",'Signatory Last Name');
							validate(vauthSignMName,"NV",'Signatory Middle Name');
							validate(vauthSignDesignation,"DDMV",'Signatory Designation'); //Add 1.0.0.29
							validate(vauthSignGender,"DDMV",'Signatory Gender'); //1.0.0.32
							validate(vauthSignDob,"TMV",'Signatory DOB'); //1.0.0.32
							validate(vauthSignMailId,"EV",'Signatory Email');
							validate(vauthSignDinNo,"TNV",'Signatory DIN No');
						 	//validate(vauthSignContactNo,"TNV,NZV",'Signatory Contact No');
							validate(vauthSignDelegation,"NV",'Signatory Delegation');
							validate(document.getElementById(('authSignPan'+vResponseSplit[i])),"TMV",'Signatory Pan');
							validate(document.getElementById(('authSignAddress'+vResponseSplit[i])),"TMV",'Signatory Address');
							validate(document.getElementById(('authSignState'+vResponseSplit[i])),"TMV",'Signatory State');
							validate(document.getElementById(('authSignCity'+vResponseSplit[i])),"TMV",'Signatory City');
							validate(vauthSignPincode,"TMV",'Signatory Pincode'); //1.0.0.32
						
						 if(tempData.errorMessages=="Errors:")
							{
								if(vauthSignDinNo.value.length!=0  && vauthSignDinNo.value.length!=8 )
								{
								tempData.errorMessages=tempData.errorMessages+"\n Signatory DIN No. should be 8 digit numeric value.";
								break;
								}
								
								if(vauthSignContactNo!="")
								{
								  if(!(validMobile(vauthSignContactNo)))
								  {	 break; }
								}
								
								
							}
						}
						
						}
				}
		}
	}


function saveAuthorisedSignotoryRows()
	{
		var responseSplit = availableauthSignRows.split("~");
		
		var authSignIdStr="";
		var authSignFNameStr="";
     var authSignLNameStr="";
     var authSignMNameStr="";
     var authSignDesignationStr="";         
     var authSignDinNoStr="";
     var authSignMailIdIdStr="";
     var authSignContactNoStr="";
     var authSignDelegationStr="";
     
     //Start 1.0.0.18
     var authSignPanStr="";
     var authSignAddressStr="";
     var authSignStateStr="";
     var authSignCityStr="";
     var authSignGenderStr=""; //1.0.0.32
     var authSignDobStr=""; //1.0.0.32
     var authSignPincodeStr=""; //1.0.0.32
     //End 1.0.0.18
		
		var tempauthSignIdStr="";
		var tempauthSignFNametr;
		var tempauthSignLNamestr;
		var tempauthSignMNamestr;
		var tempauthSignDesignationstr;
		var tempauthSignDinNoStr;
		var temauthSignMailIdIdStr;
		var tempauthSignContactNoStr;
		var tempauthSignDelegationStr;
		//Start 1.0.0.18
		var tempauthSignPanStr;
		var tempauthSignAddressStr;
		var tempauthSignStateStr;
		var tempauthSignCityStr;
		var tempauthSignGenderStr; //1.0.0.32
		var tempauthSignDobStr; //1.0.0.32
		var tempauthSignPincodeStr; //1.0.0.32
		//End 1.0.0.18
		var finalAuthSignRowString="";
		
		for(var i=0; i < responseSplit.length; i++)
		{
			var authSignRowString="";
		
			if(responseSplit[i].length > 0)
			{
				tempauthSignIdStr=document.getElementById(('authSignId'+responseSplit[i]));
				tempauthSignFNametr=document.getElementById(('authSignFName'+responseSplit[i]));	
				tempauthSignLNamestr = document.getElementById(('authSignLName'+responseSplit[i]));					
				tempauthSignMNamestr = document.getElementById(('authSignMName'+responseSplit[i]));
				tempauthSignDesignationstr = document.getElementById(('authSignDesignation'+responseSplit[i]));
				tempauthSignDinNoStr = document.getElementById(('authSignDinNo'+responseSplit[i]));
				temauthSignMailIdIdStr = document.getElementById(('authSignMailId'+responseSplit[i]));			
				tempauthSignContactNoStr=document.getElementById(('authSignContactNo'+responseSplit[i]));
				tempauthSignDelegationStr=document.getElementById(('authSignDelegation'+responseSplit[i]));
				
				//Start 1.0.0.18
				tempauthSignPanStr = document.getElementById(('authSignPan'+responseSplit[i]));
				tempauthSignAddressStr = document.getElementById(('authSignAddress'+responseSplit[i]));			
				tempauthSignStateStr=document.getElementById(('authSignState'+responseSplit[i]));
				tempauthSignCityStr=document.getElementById(('authSignCity'+responseSplit[i]));
				tempauthSignGenderStr=document.getElementById(('authSignGender'+responseSplit[i])); //1.0.0.32
				tempauthSignDobStr=document.getElementById(('authSignDob'+responseSplit[i])); //1.0.0.32
				tempauthSignPincodeStr=document.getElementById(('authSignPinCode'+responseSplit[i])); //1.0.0.32
				//End 1.0.0.18
			
				authSignRowString=tempauthSignIdStr.value+'~'+tempauthSignFNametr.value+'~'
							+tempauthSignMNamestr.value+'~'+tempauthSignLNamestr.value+'~'
							+tempauthSignDesignationstr.value+'~'+tempauthSignDinNoStr.value+'~'								
							+temauthSignMailIdIdStr.value+'~'+tempauthSignContactNoStr.value+'~'+tempauthSignDelegationStr.value+'~'
							+tempauthSignAddressStr.value+'~'+tempauthSignPanStr.value+'~'+tempauthSignStateStr.value+'~'+tempauthSignCityStr.value+'~'
							+tempauthSignGenderStr.value+'~'+tempauthSignDobStr.value+'~'+tempauthSignPincodeStr.value; //1.0.0.32
			
			
		if(authSignRowString!="")
		{
			finalAuthSignRowString=finalAuthSignRowString+authSignRowString+'^';
		}
		
		
			
			}
		}
			document.forms[0].authSignDtataStr.value = finalAuthSignRowString;
	
    
		return true;
	}
	
function validMobile(obj)

{

   if(obj.value != "")
   { 

   var regEx = /^[1-9]{1}[0-9]{9}$/;

   if(!(regEx.test(obj.value)))

   {

       tempData.errorMessages=tempData.errorMessages+"\n Signatory Contact No must be of 10 digits and should not start with 0.";

       obj.value="";

       return false;

   }

   else

   {

    return true;

   }
   }
   else
   {
     return true;
   }

   

}  

function worklist()
{
	window.location.href="prospectListFrmNavAction.do?actionPerformed=displayProspectListInfo&screenId=1000000022&screenName=MAKER";
}
function populateDesignation(obj)
			{
			
				for(var i=0;i<designationTypeArr.length;i++)
				{
					obj.options[i+1]=new Option(designationTypeArr[i][1],designationTypeArr[i][0]);
				}
				
			}

//////////// Changes By Apurva ////////////////


function populateKeyContactType(obj)
{

	for(var i=0;i<contactTypeArr.length;i++)
	{
		
		obj.options[i+1]=new Option(contactTypeArr[i][1],contactTypeArr[i][0]);
	}
	
}
function populateLob(obj)
{

	for(var i=0;i<lobArr.length;i++)
	{
		obj.options[i+1]=new Option(lobArr[i][1],lobArr[i][0]);
	}
	
}
///////////////////////////////////////////
			
function populateApplicantType()		//1.0.0.0.5
			{
		
				for(var i=0;i<applicantTypeListArr.length;i++)
				{	
					if(applicantTypeListArr[i][0]==Number(1000000004))
						referenceFlag = applicantTypeListArr[i][2];
				}
				
			}			
			
function designationChange(authSignRowIndex)
{
	if(document.getElementById('authSignDesignation'+authSignRowIndex).value=="1000000001"  )
	{
		
		document.getElementById('authSignDinNo'+authSignRowIndex).disabled=false;
	}
	else
	{
		document.getElementById('authSignDinNo'+authSignRowIndex).value='';
		document.getElementById('authSignDinNo'+authSignRowIndex).disabled=true;
	}

}


function shareholdInfo(){
	var appId = document.forms[0].applicantID.value;
	window.open('shareholderInfo.do?method=display&appId='+appId+'','shareholderinfo','width=950,height=500,toolbar=0,resizable=0,status=1');

}
function votingRightsDetail()
{	    	
		var appId = document.forms[0].applicantCode.value;
	window.open('votingRightsInfo.do?method=displayVotingRights&appId='+appId+'','shareholderinfo','width=950px,height=700px,scrollbars=yes,menubar=no,toolbar=no,resizable=no,status=no');
}

function viewLegalDocument(){
	var appId = document.forms[0].applicantID.value;
	document.forms[0].action='viewLegalDocument.do?method=display&appId='+appId+'';
	document.forms[0].submit();

}	




var selectedAuthSignRows = "";
var selectedAuthSignRowCount=0;

function toggleAuthSignRow(checkboxObj)
	{
		if(checkboxObj.checked)
		{				
			selectedAuthSignRows = selectedAuthSignRows + "~" + checkboxObj.value + "~";
			selectedAuthSignRowCount++;
		}
		else
		{			
			selectedAuthSignRows = selectedAuthSignRows.replace("~" + checkboxObj.value + "~","");
			selectedAuthSignRowCount--;
		}
//		if(selectedAuthSignRowCount == authSignRowCount)
//		{
//			alert(("All rows cannot be removed.").toUpperCase());
//			checkboxObj.checked = false;
//			selectedAuthSignRows = selectedAuthSignRows.replace("~" + checkboxObj.value + "~","");
//			selectedAuthSignRowCount--;
//			//toggleChargeRow(checkboxObj);
//		}
	}
	
	function removeAuthorisedSignatory()
	{
	var rowsArr = selectedAuthSignRows.split("~");
	var removeAuthSignDataStr="";//1.0.0.18
		
		if(rowsArr=='')
		{
			alert(('Select At least One Row.').toUpperCase());
			return false;
		}
		
		
		for (var i = 0; i < rowsArr.length; i++)
		{
			if(rowsArr[i].length > 0)
			{
				//Start 1.0.0.18
				if(document.getElementById("authSignId"+rowsArr[i]).value){
					removeAuthSignDataStr=removeAuthSignDataStr+document.getElementById("authSignId"+rowsArr[i]).value+"~";
				}//End 1.0.0.18
				
				document.getElementById("authorisedSignrowtable"+rowsArr[i]).innerHTML = "";
				document.getElementById("authorisedSignrowtable"+rowsArr[i]).style.display="none";
				availableauthSignRows = availableauthSignRows.replace("~" + rowsArr[i] + "~","");
				authSignRowCount--;
				selectedAuthSignRowCount--;
			}
		}
		selectedAuthSignRows="";
		removeAuthSignDataStr=removeAuthSignDataStr.slice(0, -1);
		document.forms[0].removeAuthSignDataStr.value = removeAuthSignDataStr;
		
	}
	//////Apurva changes///
	
	function changeNumberCommaSeparatedNumber(obj)
	{
		if(fnNumeric(obj.value))
		{
			alert("AUTHORIZED CAPITAL SHOULD BE A NUMERIC VALUE");
			obj.value = "";
		}
		else
		{
			changeNumberCommaSeparated(obj);
		}
	}
	
	function editPersonalInfo()
	{
	  if(document.forms[0].editPersonalDetails.checked==true)
	  {
	        savePersonalInfo="Y";
	  }
	  else
	  {
	        savePersonalInfo="N";
	  }
	} 
	
    function clearcombo(elem)
	{
	//alert("elem : "+elem.value);
	if(!elem)
	return;
		var i;
		for (i = elem.options.length - 1; i >= 0; i--) 
		{
		       elem.options[i] = null;
		}
		elem.selectedIndex = -1;
		elem.options[elem.options.length]= new Option("SELECT" ,"0" );
	}
	
	
  
	 function getAssetCategory(obj) {
		 clearcombo(document.forms[0].competition);//1.0.0.0
		   clearcombo(document.forms[0].assetCategory);
		   var productId = obj[obj.selectedIndex].value;
		   $.ajax({
			type : "post",
			url : "personalInfo.do?actionPerformed=getAssetCategory",
			context : document.body,
			data : "productId="+productId,
			success : function(data,status) {
			var json = JSON.parse(data);	
			
			var  assetCategoryData=json.assetCategoryData;
			var  competitionData=json.competitionData;
			document.getElementById("assetCategory").options[0]=new Option("SELECT", "0");
			 if(assetCategoryData.length >1){
	              		for(i=0; i<assetCategoryData.length; i++)
						{
	              			document.getElementById("assetCategory").options[i+1]=new Option(assetCategoryData[i].masterName, assetCategoryData[i].masterId);
						}
	                 }else{
	                	
	                	if(assetCategoryData.length!=0 ){
	                		document.getElementById("assetCategory").options[1]=new Option(assetCategoryData[0].masterName, assetCategoryData[0].masterId);
	                	}
	                	if(assetCategoryData.length==0){//1.0.0.0
	                		clearcombo(document.forms[0].assetCategory);
	                	}
	                 }  
				document.getElementById("competition").options[0]=new Option("SELECT", "0");
				 if(competitionData.length >1){
	              		for(i=0; i<competitionData.length; i++)
						{
	              			document.getElementById("competition").options[i+1]=new Option(competitionData[i].masterName, competitionData[i].masterId);
						}
	                 }else{
	                	
	                	if(competitionData.length!=0 ){
	                		document.getElementById("competition").options[1]=new Option(competitionData[0].masterName, competitionData[0].masterId);
	                	}
	                	
	                	if(competitionData.length==0){//1.0.0.0
	                		clearcombo(document.forms[0].competition);
	                	}
	                	
	                 }  
			
			}
		   
		   });
		   }
	 
	 
	 ////////////////////////////////////////////////////////////////////////////////////
	 
	 
	 function getProductIntrestedIn(obj) { /* //1.0.0.0 Start */
			
		   var lobId = obj[obj.selectedIndex].value;
		   clearcombo(document.forms[0].productInstrestedIn);
		   clearcombo(document.forms[0].competition);
		   clearcombo(document.forms[0].assetCategory);
		   $.ajax({
			type : "post",
			url : "personalInfo.do?actionPerformed=getProductIntrestedIn",
			context : document.body,
			data : "lobId="+lobId,
			success : function(data,status) {
			var json = JSON.parse(data);	
			
			var  productData=json.productIntrestedInListMaster;
			document.getElementById("productInstrestedIn").options[0]=new Option("SELECT", "0");
			 if(productData.length >1){
	              		for(i=0; i<productData.length; i++)
						{
	              			document.getElementById("productInstrestedIn").options[i+1]=new Option(productData[i].masterName, productData[i].masterId);
						}
	                 }else{
	                	
	                	if(productData.length!=0 ){
	                		document.getElementById("productInstrestedIn").options[1]=new Option(productData[0].masterName, productData[0].masterId);
	                	}

	                	if(productData.length==0){//1.0.0.0
	                		clearcombo(document.forms[0].productInstrestedIn);
	                	}
	                 }  
					
			}
		   
		   });
		   }/* 1.0.0.0 End */
	 
	 
	 //////////////////////////////////////////////////////////////////////////////////////
	
	
	////////////////
	
	////-----Added By Apurva-----
	
	var selectedKeyContactsRows = "";
	var selectedKeyContactsRowCount=0;

	function toggleKeyContactsRow(checkbox1Obj)
		{
			if(checkbox1Obj.checked)
			{				
				selectedKeyContactsRows = selectedKeyContactsRows + "~" + checkbox1Obj.value + "~";
				selectedKeyContactsRowCount++;
			}
			else
			{			
				selectedKeyContactsRows = selectedKeyContactsRows.replace("~" + checkbox1Obj.value + "~","");
				selectedKeyContactsRowCount--;
			}
			//Commented by 1.0.0.17
			/*if(selectedKeyContactsRowCount == keyContactsRowCount)
			{
				alert(("All rows cannot be removed.").toUpperCase());
				checkbox1Obj.checked = false;
				selectedKeyContactsRows = selectedKeyContactsRows.replace("~" + checkbox1Obj.value + "~","");
				selectedKeyContactsRowCount--;
				//toggleChargeRow(checkboxObj);
			}*/
		}
	
	function  cancelNewAddress()
    {
       saveAddressInfo="N";
       emptyAddressField(); 
	   enableReadOnlyAddressField();	
    }
	
	function checkidentityfication(){	
		if(document.forms[0].adharNo.value == ""  &&  document.forms[0].voterId.value == ""  && document.forms[0].panNo.value=="" && document.forms[0].passportNo.value=="" && document.forms[0].drivingLicenseNo.value=="")
		{
			tempData.errorMessages = tempData.errorMessages +"\n"+"please enter at least one Identification Information";
			return;
		} 
	}

	function checkPanNoUniqueness(obj)
	{
		 tempData.errorMessages="Errors:";
	     validate(obj,"PANV",'PAN No');  // 1.0.0.5
		 	 if(tempData.errorMessages!="Errors:")
				{
					alert((tempData.errorMessages).replace("Errors:","").toUpperCase());
					obj.value="";
					return;
				}	 
		var panNo = obj.value;
		
	    if(panNo!="" && panNo!=null){
			    var url;
	            url="NewCustomerDetails.do?actionPerformed=checkPanNoUniqueness&panNo="+panNo;
	            postPanRequest(url,obj);
	    }
		
	}
	///////Apurva Changes//////
	
	
	
	function saveKeyContactDetail()
	{
		var responseSplit1 = availablekeyContactsRows.split("~");
		var keyContactsIdStr="";
		var keyContactsCaseIdStr="";
		var keyContactslobStr="";
		var keyContactsContactTypeStr="";
		var keyContactsNameStr="";
		var keyContactsFirmNameStr="";         
		var keyContactsMobileStr="";
		var keyContactsMailIdStr="";
		var keyContactsAddressStr="";
		
		
		var tempkeyContactsIdStr="";
		var tempkeyContactsCaseIdStr="";
		var tempkeyContactslobStr;
		var tempkeyContactsContactTypestr;
		var tempkeyContactsNamestr;
		var tempkeyContactsFirmNamestr;
		var tempkeyContactsMobileStr;
		var temakeyContactsMailIdStr;
		var tempkeyContactsAddressStr;
		var finalKeyContactsRowString="";
		var caseId=document.forms[0].caseId.value;
		
		
		for(var i=0; i < responseSplit1.length; i++)
		{
			var KeyContactRowString="";
		
			if(responseSplit1[i].length > 0)
			{
				tempkeyContactsIdStr=document.getElementById(('keyContactsId'+responseSplit1[i]));
				tempkeyContactsCaseIdStr=document.getElementById(('caseId'+responseSplit1[i]));
				tempkeyContactslobStr=document.getElementById(('keyContactslob'+responseSplit1[i]));	
				tempkeyContactsContactTypestr = document.getElementById(('keyContactsContactType'+responseSplit1[i]));					
				tempkeyContactsNamestr = document.getElementById(('keyContactsName'+responseSplit1[i]));
				tempkeyContactsFirmNamestr = document.getElementById(('keyContactsFirmName'+responseSplit1[i]));
				tempkeyContactsMobileStr = document.getElementById(('keyContactsMobile'+responseSplit1[i]));
				temakeyContactsMailIdStr = document.getElementById(('keyContactsMailId'+responseSplit1[i]));			
				tempkeyContactsAddressStr=document.getElementById(('keyContactsAddress'+responseSplit1[i]));
				
			
				KeyContactRowString=tempkeyContactsIdStr.value+'~'+tempkeyContactsCaseIdStr.value.trim()+'~'+tempkeyContactslobStr.value.trim()+'~'
							+tempkeyContactsContactTypestr.value.trim()+'~'+tempkeyContactsNamestr.value.trim()+'~'
							+tempkeyContactsFirmNamestr.value.trim()+'~'+tempkeyContactsMobileStr.value.trim()+'~'								
							+temakeyContactsMailIdStr.value.trim()+'~'+tempkeyContactsAddressStr.value.trim();
			
			
		if(KeyContactRowString!="")
		{
			  finalKeyContactsRowString=finalKeyContactsRowString+KeyContactRowString+'^';
		}
		
		//finalKeyContactsRowString = finalKeyContactsRowString.substring(finalKeyContactsRowString.lastIndexOf("^") + 1, finalKeyContactsRowString.length);
		
			
			}
		}
		if (finalKeyContactsRowString != null  || finalKeyContactsRowString !="") {
			finalKeyContactsRowString=finalKeyContactsRowString.slice(0, -1);
		}
		//	finalKeyContactsRowString = finalKeyContactsRowString.substring(0,finalKeyContactsRowString.length()-1);
		//}
			document.forms[0].keyContactDataStr.value = finalKeyContactsRowString;
	
    
		return true;
	}
		///////////////////////////////////////
	
	    /*var obj= document.getElementById("keyContactsRowsDiv");
	    var assetCat="";
	    var prodcut="";
	    var saveSytr="";
	    for(var i=0;i<obj.length;i++)
	    {
	      assetCat= document.getElementById("assetCat"+i).value;
	      prodcut= document.getElementById("prodcut"+i).value;
	      
	      saveSytr=saveSytr+"~"+assetCat+"~"+prodcut;
	    }
	    
	    document.forms[0].saveKeyContaDtlStr=saveSytr;
	}*/
	
	///////////////////
	
	
    
	
	function removeKeyContacts()
	{
	var rowsArr1 = selectedKeyContactsRows.split("~");
	var removeKeyContactDataStr="";
		
		if(rowsArr1=='')
		{
			alert(('Select At least One Row.').toUpperCase());
			return false;
		}
		
		
		for (var i = 0; i < rowsArr1.length; i++)
		{
			if(rowsArr1[i].length > 0)
			{
				if(document.getElementById("keyContactsId"+rowsArr1[i]).value){
					removeKeyContactDataStr=removeKeyContactDataStr+document.getElementById("keyContactsId"+rowsArr1[i]).value+"~";
				}
				
				document.getElementById("keyContactsrowtable"+rowsArr1[i]).innerHTML = "";
				document.getElementById("keyContactsrowtable"+rowsArr1[i]).style.display="none";
				availablekeyContactsRows = availablekeyContactsRows.replace("~" + rowsArr1[i] + "~","");
				keyContactsRowCount--;
				selectedKeyContactsRowCount--;
			}
		}
		selectedKeyContactsRows="";
		removeKeyContactDataStr=removeKeyContactDataStr.slice(0, -1);
		document.forms[0].removeKeyContactDataStr.value = removeKeyContactDataStr;
		
	
	}
	
	/////////////
	
	function validateDINNo(vauthSignDinNo)
	{
		if(vauthSignDinNo.length!=0  && vauthSignDinNo.length!=8 )
		{
		alert(("DIN No. should be 8 digit numeric value.").toUpperCase());
		return false;
		}
		
	}

   
  function checkforAppType(obj)
  {
    
    if(document.forms[0].applicantType.value=="0")
    {
     obj.checked=false;
     document.forms[0].isExisting[0].checked=true;
      toggleExistingDetails(false,document.forms[0].isExisting[0]);
       alert(("Please Select Applicant type.").toUpperCase());
    return;
    }
    if(document.forms[0].custEntityType.value=="0")
    {
      obj.checked=false;
      alert(("Please Select Customer Entity type.").toUpperCase());
      return;
    }
    }
   function checkforOfficeAddress(obj)
    {
      if(obj.value == "1000000002" )
       {
         document.getElementById('addFlag').value='Y';
         return;
       }
        else
         document.getElementById('addFlag').value='N';
    }
	

	  function toggleExistingDetails(existing, radioBox)
	{  
		  
		//radioBox.checked = true;
		clearIndividualDivision(false);
		clearAllAdd();
		if(existing)
		{  
		
		
		  // 	togglePersonalAndAddressBoxes(true);
	  	
	   
	      
			var existingEntityType = document.forms[0].custEntityType.value;
			
			if(existingEntityType == "0" || existingEntityType == "")
			{
				
			}
			document.getElementById("existingDetailsTD").style.display = "block";
			document.getElementById("existingDetailsTD_Alt").style.display = "none";
		}
		else
		{  
		
			//togglePersonalAndAddressBoxes(false);
			document.forms[0].existingApplicantId.value = "";
			
			document.getElementById("existingDetailsTD").style.display = "none";
			document.getElementById("existingDetailsTD_Alt").style.display = "block";
		}
		
		if(document.forms[0].gender)
		document.forms[0].gender.value="";
		
	}
 	function clearGroupDivision(disable)
 	{
	  document.forms[0].nameOfGroup.value = "";
	  if(document.forms[0].recruitedByGroup)
      document.forms[0].recruitedByGroup.value = "0";
    
      if(document.forms[0].trained)
      document.forms[0].trained.value = "";
      document.forms[0].trainedDate.value = "";
      if(disable)
      {
		  document.forms[0].nameOfGroup.disabled=true;
	     
	       if(document.forms[0].trained)
	      document.forms[0].trained.disabled=true;
	      document.forms[0].trainedDate.disabled=true;
      }
 	}

function changeCombo(obj)
		{
		// alert("hii");
		// added by ankit gaur for saving records without selecting address type
		// ankit gaur 30/11/11
		   var sameAsId=document.forms[0].addressType.value;
					if(document.forms[0].addressType.selectedIndex==0)
					{
					    document.forms[0].sameAs.disabled=true;
					}
					else{
					     document.forms[0].sameAs.disabled=false;
					}
		  // end here
			
			if(obj.value=="1000000002")
			{
				 document.getElementById("nameOfCompanyNew").style.display="";
			}
			else
			{
				 document.getElementById("nameOfCompanyNew").style.display="none";
			}
			if(obj.value=="1000000001"|| obj.value=="1000000003")
			{
				if(document.forms[0].applicantType.value=="1000000001" || document.forms[0].applicantType.value=="1000000002" || document.forms[0].applicantType.value=="1000000003" || document.forms[0].applicantType.value=="1000000004") 
				{
					document.forms[0].residenceStatus.style.backgroundColor="#ebf3ff";
					document.forms[0].noOfYearAtResidence.style.backgroundColor="#ebf3ff";
					document.forms[0].noOfMonthAtResidence.style.backgroundColor="#ebf3ff";
				}
				else
				{
					document.forms[0].residenceStatus.style.backgroundColor="#ebf3ff";
					document.forms[0].noOfYearAtResidence.style.backgroundColor="#ebf3ff";
					document.forms[0].noOfMonthAtResidence.style.backgroundColor="#ebf3ff";
				}
			}
			else
			{
				document.forms[0].residenceStatus.style.backgroundColor="#ebf3ff";
				document.forms[0].noOfYearAtResidence.style.backgroundColor="#ebf3ff";
				document.forms[0].noOfMonthAtResidence.style.backgroundColor="#ebf3ff";
			}
			// 1.0.0.0.2 start //1.0.0.0.3 start
			/*if(document.forms[0].addressType.value==1000000010){
		  		document.forms[0].gstinNo.style.backgroundColor="#ebf3ff";
		  		}
			else
			{
				document.forms[0].gstinNo.style.backgroundColor="#ffffff";
			}*/// 1.0.0.0.2 end //1.0.0.0.3 end
			//document.forms[0].residenceStatus.style.backgroundColor="#ebf3ff";
		}

function printCustomerPhotoCapture()
	{
		document.forms[0].btnPrint.disabled=true;
	document.forms[0].btnCapture.disabled=true; 
		window.open("CaptureCustomerPhoto.do?actionPerformed=captureCustomerPhoto"+"&rurl="+Math.random(),"Photo", features);
 }

function financialDetail()
{	    	
  window.open('impairmentAction.do?actionPerformed=displayImpairment&screenId=1000002010&callFrom=personalInfo ','financialinfo','width=950px,height=590px,scrollbars=yes,menubar=no,toolbar=no,resizable=no,status=no');
}
function editPersonalInfo()
{
  if(document.forms[0].editPersonalDetails.checked==true)
  {
        savePersonalInfo="Y";
  }
  else
  {
        savePersonalInfo="N";
  }
} 


function disabledAddressField()
{
       // alert("disabledAddressField 1");
       document.forms[0].offCompanyName.disabled=true;
        document.forms[0].addressType.disabled=true;	
      //  document.forms[0].accomodationType.disabled=true;	
      	document.forms[0].mailingAddressCheck.disabled=true;//updated by sakib 06/09/2011 Bug Id:4705
		document.forms[0].flatHouseNo.disabled=true;
		document.forms[0].destinationAdd.disabled=true;  //1.0.0.0.2
		document.forms[0].gstinNo.disabled=true;  
		
		if(entityId=="1000000003"){
			if(document.forms[0].addressType=="1000000010"){
				
				document.forms[0].gstinNo.disabled=false;
			}	
				}
		//1.0.0.0.2
		document.forms[0].floorNo.disabled=true;
		document.forms[0].buildingName.disabled=true;
		document.forms[0].locality.disabled=true;
		document.forms[0].landMark.disabled=true;
		document.forms[0].city.disabled=true;
		document.forms[0].state.disabled=true;
		document.forms[0].city_temp.disabled=true;
		document.forms[0].state_temp.disabled=true;
		document.forms[0].zipPin_temp.disabled=true;
		document.forms[0].extention1.disabled=true;
		document.forms[0].phoneNo1.disabled=true;
		//document.forms[0].extension2.disabled=true;
		//document.forms[0].phone2.disabled=true;
		document.forms[0].mobile.disabled=true;
		//document.forms[0].stdIsd.disabled=true;
		document.forms[0].email.disabled=true;
		document.forms[0].fax.disabled=true;
		document.forms[0].btnCity.disabled=true;
			document.forms[0].btnState.disabled=true;
		    document.forms[0].btnzipPin.disabled=true;
			
			document.forms[0].phone1Std.disabled=true;
		document.forms[0].phone1LandLine.disabled=true;
		//document.forms[0].phone2Std.disabled=true;
		//document.forms[0].phone2LandLine.disabled=true;
		document.forms[0].noOfYearAtResidence.disabled=true;
		document.forms[0].noOfMonthAtResidence.disabled=true;
		//alert("disabledAddressField 2");
}

function changeMailingAddress(applicantAddressId)
{
    document.forms[0].mailingAddress.value=applicantAddressId;
}
function editApplicantAddress(offCompanyName,applicantAddressId,applicantId,addressType,addressTypeName,sameAs,accomodationType,mailingAddress,flatHouseNo,floorNo,buildingName,locality,landMark,city,state,zipPin,zipPin_temp,extention1,phoneNo1,extension2,phone2,mobile,stdIsd,email,fax,stateName,cityName,noOfYears,noOfMonths,ownerShip,destinationAddTemp,gstinNoTemp,residenceStatusTemp,sez,igstApplicable,igstPercent,lutNo,loa,gstType,sameAsAddressId,moduleType)//2.0.0.0.1 adding three parameter sez,igstApplicable,igstPercent)
{  

	

// Code added by Seema Pandey 05-April for Name of office address
// 	if(addressTypeName=="OFFICE ADDRESS")
	if( addressType == "1000000002" )
	{  
	
		document.getElementById("nameOfCompanyNew").style.display="";
		// document.forms[0].offCompanyName.disabled=false;
		if(document.forms[0].custEntityType.value!="1000000002")
		{
			 document.forms[0].offCompanyName.disabled=false;
		}
		else{			
		 	document.forms[0].offCompanyName.disabled=true;
		 }
	}
	else
	{
		document.getElementById("nameOfCompanyNew").style.display="none";
	}
	
	if(addressType=="1000000001")
	{   
		if(document.forms[0].applicantType.value=="1000000001" || document.forms[0].applicantType.value=="1000000002")
		{  
			///document.forms[0].residenceStatus.style.backgroundColor="#ebf3ff";
			///document.forms[0].noOfYearAtResidence.style.backgroundColor="#ebf3ff";
			///document.forms[0].noOfMonthAtResidence.style.backgroundColor="#ebf3ff";
		}
		else
		{
			document.forms[0].residenceStatus.style.backgroundColor="#ebf3ff";
			document.forms[0].noOfYearAtResidence.style.backgroundColor="#ffffff";
			document.forms[0].noOfMonthAtResidence.style.backgroundColor="#ffffff";
		}
	}
	else
	{
		document.forms[0].residenceStatus.style.backgroundColor="#ebf3ff";
		document.forms[0].noOfYearAtResidence.style.backgroundColor="#ffffff";
		document.forms[0].noOfMonthAtResidence.style.backgroundColor="#ffffff";
	}
	//Changes made for bug id 55692 on 30 march 2018 start
	document.forms[0].mobile.style.backgroundColor="#ebf3ff";
	document.forms[0].noOfYearAtResidence.style.backgroundColor="#ebf3ff";
	document.forms[0].noOfMonthAtResidence.style.backgroundColor="#ebf3ff";
	
	if(addressType=="1200000007"){
		document.forms[0].residenceStatus.style.backgroundColor="#ebf3ff";
		
	}
    var phone1Full = phoneNo1.split("-");
    var phone2Full = phone2.split("-");
   // alert("1");
    saveAddressInfo="Y";
    document.forms[0].flag.value="U";
    document.forms[0].applicantAddressId.value=applicantAddressId;
	document.forms[0].addrApplicantId.value=applicantId;
	//alert("1");
	document.forms[0].addressType.options[0].text=addressTypeName;
	document.forms[0].addressType.options[0].value=""+addressType;
	document.forms[0].addressType.selectedIndex=0;
	document.forms[0].sameAs.value=0;
	/*if(sameAs=='')
	{
		document.forms[0].sameAs.value=0;
	}
	else
	{
		document.forms[0].sameAs.value=sameAs;
	}*/
	//document.forms[0].accomodationType.value=accomodationType;
	if(mailingAddress=="Y")
	{
	  	document.forms[0].mailingAddressCheck.checked=true;
	}
	else
	{
		document.forms[0].mailingAddressCheck.checked=false;
	}// 1.0.0.0.2 start
	if(destinationAddTemp=="Y")
	{
	  	document.forms[0].destinationAdd.checked=true;
	}
	else
	{
		document.forms[0].destinationAdd.checked=false;
	}
	document.forms[0].destinationAdd.disabled=false;
	//1.0.0.0.2 end
	if(document.forms[0].custEntityType.value!="1000000002")
		{
			 document.forms[0].offCompanyName.value=offCompanyName;
		}
		else{			
		 	document.forms[0].offCompanyName.value=document.forms[0].nameOfCompany.value;
		 }
		 
	document.forms[0].gstinNo.value=gstinNoTemp;  //1.0.0.0.2
	document.forms[0].flatHouseNo.value=flatHouseNo;
	document.forms[0].floorNo.value=floorNo;
	document.forms[0].buildingName.value=buildingName;
	document.forms[0].locality.value=locality;
	document.forms[0].landMark.value=landMark;
	document.forms[0].city_temp.value=cityName;
	document.forms[0].state_temp.value=stateName;
	document.forms[0].city.value=city;
	document.forms[0].state.value=state;
	document.forms[0].zipPin.value=zipPin;
	document.forms[0].zipPin_temp.value=zipPin_temp;
	document.forms[0].extention1.value=extention1;
	document.forms[0].phoneNo1.value=phoneNo1;
	//document.forms[0].extension2.value=extension2;
	//document.forms[0].phone2.value=phone2;
	document.forms[0].mobile.value=mobile;
	//document.forms[0].stdIsd.value=stdIsd;
	document.forms[0].residenceStatus=residenceStatusTemp;
	document.forms[0].email.value=email;
	document.forms[0].fax.value=fax;
	
	
	//2.0.0.0.1 start
	document.forms[0].sezZone.disabled=false; // 1.0.0.14
	if(sez=="Y"){
		document.forms[0].sezZone.checked=true;
	}
	else{
		document.forms[0].sezZone.checked=false;
	}
	document.forms[0].sezZone.disabled=true; 
	
	if(sez!=null && sez!=""){
	document.forms[0].sez.value=sez;
	}else{
		if(document.forms[0].sez)
		{
		document.forms[0].sez.value="N";
		}}
	if(igstApplicable!=null && igstApplicable!=""){
	document.forms[0].igstApplicable.value=igstApplicable;
	}else{document.forms[0].igstApplicable.value="0"}
	if(igstPercent!=null && igstPercent!=""){
	document.forms[0].igstpercent.value=igstPercent;
	}else{document.forms[0].igstpercent.value=""}
	
	if(lutNo!=null && lutNo!=""){
		document.forms[0].lutNo.value=lutNo;
		}else{document.forms[0].lutNo.value=""}
	
	if(loa!=null && loa!="" && loa=="Y"){
		
		document.forms[0].temp_loaCheckBox.checked=true;
	}
	if(document.forms[0].sez)
	{
	document.forms[0].sezZone.disabled=false;
	}
	if(sez=="Y"){
		document.forms[0].igstApplicable.disabled=false;
		document.forms[0].lutNo.disabled=false;
		document.forms[0].temp_loaCheckBox.disabled=false;
		//document.forms[0].temp_loaCheckBox.checked=false;
		
	}
	
		//document.forms[0].loaCheckBox.value=loa;
		//}else{document.forms[0].loaCheckBox.value=""}
	//2.0.0.0.1 End
	
	
	//if(ownerShip!="")
	//{
		document.forms[0].residenceStatus.value=accomodationType;
	//}
	//else
	//{
		//document.forms[0].residenceStatus.value=residenceStatusTemp;
	//	document.forms[0].residenceStatus.value="0";
	//}
	if(phone1Full[0]=="" || phone1Full[0]==undefined)
	{
		document.forms[0].phone1Std.value = "";
	}
	else
	{
		document.forms[0].phone1Std.value = phone1Full[0];
	}
	if(phone1Full[1]=="" || phone1Full[1]==undefined)
	{
		document.forms[0].phone1LandLine.value = "";
	}
	else
	{
		document.forms[0].phone1LandLine.value = phone1Full[1];
	}
	if(phone2Full[0]=="" || phone2Full[0]==undefined)
	{
		//document.forms[0].phone2Std.value = "";
	}
	else
	{
		//document.forms[0].phone2Std.value = phone2Full[0];
	}
	if(phone2Full[1]=="" || phone2Full[1]==undefined)
	{
		//document.forms[0].phone2LandLine.value = "";	
	}
	else
	{
		//document.forms[0].phone2LandLine.value = phone2Full[1];
	}
	document.forms[0].addressType.disabled=true;
	
	
	
	//if((document.forms[0].addressType.value == document.forms[0].sameAs.value) || (document.forms[0].addressType.value!="0" && document.forms[0].sameAs.value=="0"))
	if(document.forms[0].sameAs.value=="0")
	{
		if (document.forms[0].applicantType.value!="1000000001" && entityId=="1000000008")
	    {
	    disableReadOnlyAddressField();
	    }
		else if(entityId!="1000000008")
		{
			disableReadOnlyAddressField();
		}
	//  1.0.0.9 start  
	   /* document.forms[0].btnState.disabled=false;
	    document.forms[0].btnCity.disabled=false;
	    document.forms[0].btnzipPin.disabled=false;*/
	//  1.0.0.9 end 
	}
	else
	{
	    enableReadOnlyAddressField();
	//  1.0.0.9 start redundant code already in above function.
	  /*  document.forms[0].btnState.disabled=true;
	    document.forms[0].btnCity.disabled=true;
	    document.forms[0].btnzipPin.disabled=true;*/
	//  1.0.0.9 end 
	}
	document.forms[0].addressType.disabled=true;
	
	document.forms[0].sameAs.disabled=false; // added by ankit gaur 30/11/11
	document.forms[0].noOfYearAtResidence.value=noOfYears;
	document.forms[0].noOfMonthAtResidence.value=noOfMonths;
	
	
	
	
	//// Changes By Apurva For Dm module ///
	if(document.forms[0].entityIdForDm.value=="1000000008"){
		if(temp_View_Mode=="Y") // 1.0.0.11 start
			{
			  document.forms[0].sameAs.disabled=true;
			}
		//1.0.0.11 end
		//$('#mailingAddrDiv *').prop('disabled',true);
		document.forms[0].mailingAddressCheck.disabled=true;
		//document.getElementById("mailingAddrDiv").disabled=true;
		document.forms[0].destinationAdd.disabled=true;
	}
////Changes By Apurva For Dm module ///
	
	if(addressType=="1200000007" || addressType=="1200000008"){
		document.getElementById("gstType").style.display="none";//1.0.0.8
		document.forms[0].lessorGstType.disabled=false;
		document.forms[0].lessorGstType.value=gstType;
	}else{
		document.getElementById("gstType").style.display="none";
	}
	
	if(addressType=="1200000008"){
		document.getElementById("gstId").style.display="none";//1.0.0.8
		document.getElementById("sezZoneDivId").style.display="none";
		document.getElementById("noteId").style.display="none";//1.0.0.8
	}else{
		document.getElementById("gstId").style.display="none";
		document.getElementById("sezZoneDivId").style.display="none";
		document.getElementById("noteId").style.display="none";
	}
	
	if(entityId=="1000000003")
	{
		if(addressType=="1000000010")
		{
			document.getElementById("sezZoneDivId").style.display="none";
			document.forms[0].sez.style.backgroundColor="#ebf3ff";
			document.forms[0].sezZone.disabled=false;
		}
		else
		{
			document.getElementById("sezZoneDivId").style.display="none";
			document.forms[0].sezZone.disabled=true;
		}
	}
	if(entityId=="1000000008"){
		document.forms[0].sezZone.disabled=true;
	
	}
	setSameAsDropDown(document.forms[0].addressType);
	if(entityId=="1000000008"){
	document.forms[0].sameAs.value=sameAsAddressId+"-"+sameAs+"-C";
	}
	if(entityId=="1000000003"){
		//document.forms[0].sameAs.value="0";
		}
	
	if (document.forms[0].applicantType.value!="1000000001" && entityId=="1000000008")
    {
		
	document.forms[0].zipPin_temp.readOnly=false;//1.0.0.5
	document.forms[0].zipPin_temp.disabled=false;
    }
	else if(entityId!="1000000008"){
		document.forms[0].zipPin_temp.readOnly=false;
		document.forms[0].zipPin_temp.disabled=false;
	}
	
	
	setTitle(document);
}

function viewApplicantAddress(offCompanyName,applicantAddressId,applicantId,addressType,addressTypeName,sameAs,accomodationType,mailingAddress,flatHouseNo,floorNo,buildingName,locality,landMark,city,state,zipPin,zipPin_temp,extention1,phoneNo1,extension2,phone2,mobile,stdIsd,email,fax,stateName,cityName,noOfYears,noOfMonths,ownerShip,destinationAddTemp,gstinNoTemp,residenceStatusTemp,sez,igstApplicable,igstPercent,lutNo,loa,gstType,sameAsAddressId,moduleType)//2.0.0.0.1 adding three parameter sez,igstApplicable,igstPercent
{

	// Code changed by Seema Pandey 05-April for the name of office address
	// if(addressTypeName=="OFFICE ADDRESS")
	if( addressType == "1000000002" )
	{ 
		document.getElementById("nameOfCompanyNew").style.display="";
		if(document.forms[0].custEntityType.value!="1000000002")
		{
			 // document.forms[0].offCompanyName.readOnly=true;
		}
		else{			
		 	document.forms[0].offCompanyName.disabled=true;
		 }
		 
		//document.forms[0].offCompanyName.disabled=true;
	}
	else
	{
		document.getElementById("nameOfCompanyNew").style.display="none";
	}
	if(addressType=="1000000001")
	{ 
		if(document.forms[0].applicantType.value=="1000000001" || document.forms[0].applicantType.value=="1000000002")
		{
			document.forms[0].residenceStatus.style.backgroundColor="#ebf3ff";
			document.forms[0].noOfYearAtResidence.style.backgroundColor="#ebf3ff";
			document.forms[0].noOfMonthAtResidence.style.backgroundColor="#ebf3ff";
		}
		else
		{
			document.forms[0].residenceStatus.style.backgroundColor="#ffffff";
			document.forms[0].noOfYearAtResidence.style.backgroundColor="#ffffff";
			document.forms[0].noOfMonthAtResidence.style.backgroundColor="#ffffff";
		}
	}
	else
	{
		document.forms[0].residenceStatus.style.backgroundColor="#ffffff";
		document.forms[0].noOfYearAtResidence.style.backgroundColor="#ffffff";
		document.forms[0].noOfMonthAtResidence.style.backgroundColor="#ffffff";
	}
	
    var phone1Full = phoneNo1.split("-");
    var phone2Full = phone2.split("-");
    saveAddressInfo="N";
    document.forms[0].flag.value="U";
    document.forms[0].applicantAddressId.value=applicantAddressId;
	document.forms[0].addrApplicantId.value=applicantId;
	document.forms[0].addressType.options[0].text=addressTypeName;
	document.forms[0].addressType.options[0].value=""+addressType;
	document.forms[0].addressType.selectedIndex=0;
	if(sameAs=='')
	{
		document.forms[0].sameAs.value=0;
	}
	else
	{
		document.forms[0].sameAs.value=sameAs;
	}
	//document.forms[0].accomodationType.value=accomodationType;
	
	if(mailingAddress=="Y")
	{
	  	document.forms[0].mailingAddressCheck.checked=true; 
	}
	else
	{
		document.forms[0].mailingAddressCheck.checked=false;
	}
	// 1.0.0.0.2 start
	if(destinationAddTemp=="Y")
	{
	  	document.forms[0].destinationAdd.checked=true; 
	}
	else
	{
		document.forms[0].destinationAdd.checked=false;
	}// 1.0.0.0.2 end
	if(document.forms[0].custEntityType.value!="1000000002")
		{
			 document.forms[0].offCompanyName.value=offCompanyName;
		}
		else{			
		 	document.forms[0].offCompanyName.value=document.forms[0].nameOfCompany.value;
		 }
		 
	//document.forms[0].offCompanyName.value=document.forms[0].nameOfCompany.value;
	document.forms[0].gstinNo.value=gstinNoTemp;   //1.0.0.0.2
	document.forms[0].flatHouseNo.value=flatHouseNo;
	document.forms[0].floorNo.value=floorNo;
	document.forms[0].buildingName.value=buildingName;
	document.forms[0].locality.value=locality;
	document.forms[0].landMark.value=landMark;
	document.forms[0].city_temp.value=cityName;
	document.forms[0].state_temp.value=stateName;
	document.forms[0].city.value=city;
	document.forms[0].state.value=state;
	document.forms[0].zipPin.value=zipPin;
	document.forms[0].zipPin_temp.value=zipPin_temp;
	document.forms[0].extention1.value=extention1;
	document.forms[0].phoneNo1.value=phoneNo1;
	//document.forms[0].extension2.value=extension2;
	//document.forms[0].phone2.value=phone2;
	document.forms[0].mobile.value=mobile;
	//document.forms[0].stdIsd.value=stdIsd;
	document.forms[0].email.value=email;
	document.forms[0].fax.value=fax;
	//2.0.0.0.1 start
	if(sez!=null && sez!=""){
	document.forms[0].sez.value=sez;
	}else{document.forms[0].sez.value="N"}
	if(igstApplicable!=null && igstApplicable!=""){
	document.forms[0].igstApplicable.value=igstApplicable;
	}else{document.forms[0].igstApplicable.value="0"}
	if(igstPercent!=null && igstPercent!=""){
	document.forms[0].igstpercent.value=igstPercent;
	}else{document.forms[0].igstpercent.value=""}
	
	if(lutNo!=null && lutNo!=""){
		document.forms[0].lutNo.value=lutNo;
		}else{document.forms[0].lutNo.value=""}
	
	if(loa!=null && loa!="" && loa=="Y"){
		
			document.forms[0].temp_loaCheckBox.checked=true;
		}
	document.forms[0].temp_loaCheckBox.disabled=true;
		//}else{document.forms[0].loaCheckBox.value=""}
	//2.0.0.0.1 End
	if(phone1Full[0]=="" || phone1Full[0]==undefined)
	{
		document.forms[0].phone1Std.value = "";
	}
	else
	{
		document.forms[0].phone1Std.value = phone1Full[0];
	}
	if(phone1Full[1]=="" || phone1Full[1]==undefined)
	{
		document.forms[0].phone1LandLine.value = "";
	}
	else
	{
		document.forms[0].phone1LandLine.value = phone1Full[1];
	}
	if(phone2Full[0]=="" || phone2Full[0]==undefined)
	{
		//document.forms[0].phone2Std.value = "";
	}
	else
	{
		//document.forms[0].phone2Std.value = phone2Full[0];
	}
	if(phone2Full[1]=="" || phone2Full[1]==undefined)
	{
		//document.forms[0].phone2LandLine.value = "";	
	}
	else
	{
		//document.forms[0].phone2LandLine.value = phone2Full[1];
	}
	document.forms[0].addressType.disabled=true;
	
	document.forms[0].noOfYearAtResidence.value=noOfYears;
	document.forms[0].noOfMonthAtResidence.value=noOfMonths;
	//if(ownerShip!="")
	//{
		document.forms[0].residenceStatus.value=accomodationType;
	//}
	//else
	//{
		//document.forms[0].residenceStatus.value=residenceStatusTemp;
		//document.forms[0].residenceStatus.value="0";
	//}
	
		if(addressType=="1200000007" || addressType=="1200000008"){
			document.getElementById("gstType").style.display="none";//1.0.0.8
			document.forms[0].lessorGstType.disabled=false;
			document.forms[0].lessorGstType.value=gstType;
		}else{
			document.getElementById("gstType").style.display="none";
		}
		if(addressType=="1200000008"){
			document.getElementById("gstId").style.display="none";//1.0.0.8
			document.getElementById("sezZoneDivId").style.display="none";
			document.getElementById("noteId").style.display="none";//1.0.0.8
		}else{
			document.getElementById("gstId").style.display="none";
			document.getElementById("sezZoneDivId").style.display="none";
			document.getElementById("noteId").style.display="none";
		}
		if(entityId=="1000000003")
		{
			if(addressType=="1000000010")
			{
				document.forms[0].sezZone.disabled=false;
				document.getElementById("sezZoneDivId").style.display="none";
			}
			else
			{
				document.getElementById("sezZoneDivId").style.display="none";
			}
		}
		else{
			document.forms[0].sezZone.disabled=true;
		}
	enableReadOnlyAddressField();
	document.forms[0].zipPin_temp.readOnly=true;//1.0.0.4
	document.forms[0].sezZone.disabled=false; // 1.0.0.14
	if(sez=="Y"){
		document.forms[0].sezZone.checked=true;
	}
	else{
		document.forms[0].sezZone.checked=false;
	}
	setSameAsDropDown(document.forms[0].addressType);////////,sameAsAddressId,moduleType
	if(entityId=="1000000008"){
		document.forms[0].sameAs.value=sameAsAddressId+"-"+sameAs+"-C";
		}
		if(entityId=="1000000003"){
			//document.forms[0].sameAs.value="0";
			}
	 document.forms[0].sezZone.disabled=true;
	setTitle(document);
}
function disableClosedForeClosedCase()
  {
	    for(count=0; count < document.forms[0].elements.length; count+=1)
	        {
	        	theelement = document.forms[0].elements[count];
	            if(theelement.name != null && theelement.name != "btn_one")
	            {
	            	theelement.disabled = true;
	            }
	        }
	         document.getElementById("SaveLink").disabled = true;												
			 document.getElementById("SaveLink").removeAttribute('href');
			 document.getElementById("SaveLink").removeAttribute('onclick');
	         document.getElementById("SaveExitLink").disabled = true;
			 document.getElementById("SaveExitLink").removeAttribute('href');
			 document.getElementById("SaveExitLink").removeAttribute('onclick');
  }

	function enableAllElementsOnSave()
    {
    	        for(count=0; count < document.forms[0].elements.length; count+=1)
		        {
		        	theelement = document.forms[0].elements[count];
		        	
		            if(theelement.name != null || theelement=="addressType")
		            {
		            	theelement.disabled = false;
		            }
		        }
	}
 	function enableAllElements()
    {
    	        for(count=0; count < document.forms[0].elements.length; count+=1)
		        {
		        	theelement = document.forms[0].elements[count];
		        	
		            if(theelement.name != null)
		            {
		            	theelement.disabled = false;
		            }
		        }
	}
 	
 	
 	function enableDNS()
	{
		if(document.forms[0].dnsRequired.checked)
		{
			document.forms[0].dnsReasonTemp.value="";
			document.forms[0].dnsRequired.value="Y";
			document.forms[0].dnsReason.disabled=false;
			return true;
		}
		else
		{
		    document.forms[0].dnsReasonTemp.value=document.forms[0].dnsReason.value;
			document.forms[0].dnsRequired.value="N";
			document.forms[0].dnsReason.value="";
			document.forms[0].dnsReason.disabled=true;
			return true;
		}
	}
	function enterSpouse()
	{	
		document.forms[0].maritalStatus.readOnly=true;
		if(document.forms[0].maritalStatus[0].checked)
				{
					if(document.forms[0].applicantType.value==1000000001 || document.forms[0].applicantType.value==1000000002)
					{	
						
					}
					else
					{
					
					}
				}
				else
				{
					
				}
	}
	
	
	function roundit(which)
	{
		return Math.round(which*100)/100;
	}
	
	function calcCms()
	{
		var feet = document.forms[0].heightFeet.value;
		var inches = document.forms[0].heightInches.value;
		if(feet == "")
		{
			feet =0;
		}
		if(inches == "")
		{
			inches =0;
		}
		var cms = ((parseInt(feet)*12)+parseInt(inches))*2.54;
		document.forms[0].heightCms.value=roundit(parseFloat(cms));
	}
	
	function calcFeet()
	{
		var cms = parseInt(document.forms[0].heightCms.value);
		var feet = (cms*0.3937008)/12;
		var inches = (cms*0.3937008)%12;
		document.forms[0].heightFeet.value = roundit(parseInt(feet));
		document.forms[0].heightInches.value = roundit(parseFloat(inches));
	}
	
	
	   function alphanumeric(s1)
		{
		var allowed='abcdefghijklmnopqrstuvwxyzABCDEFGHIJJKLMNOPQRSTUVWXYZ0123456789';
	   str=Trim(s1.value);
	   var i;
	   var checkLength = str.length;
		if(checkLength>0)
		{
		   for(i=0;i<str.length;i++)
		   {
		      if(allowed.indexOf(str.charAt(i)) == -1)
		      {
			      if(tempData.objToFocus == null)
			     {
			     	tempData.objToFocus = s1;
			     }
			                      s1.focus();
	              return true;
		      }
		      else
		      {
		     }
		      
		   }
		}
	   return false; 
		
	}
	   
	 //added by 1.0.0.19
       function AuthSignAddressVal()
       {
    	   for(var i=0; i<authSignRowIndex; i++)
    		   {
    		   if(document.getElementById("authSignId"+i))
   			{
    			   if(document.getElementById("authSignAddress"+i).value.length>50)
    				   {
    				   alert(("\nauthorized signatory address length must not exceed above 50 character.").toUpperCase());
	                  return true;
    				   }
   			}
    		   }
       }
      function beneficiaryAddress()
      {
    	  for(var i=0; i<beneficiaryOwnerRowIndex; i++)
  		{
  			if(document.getElementById("beneficiaryOwnerId"+i))
  			{
  				if(document.getElementById("beneficiaryOwnerAddress"+i).value.length>50)
  					{
  					alert(("\nbeneficiary owner address length must not exceed above 50 character.").toUpperCase());
	                  return true;
  					}
  			}
  			
  			}
      }
       //end by 1.0.0.19
      //start 1.0.0.20
      function setLpiDefaultValue(){
    	  if(document.forms[0].lpiRateType.value=="VARIABLE" || document.forms[0].lpiRateType.value=="FIXED"){				
				document.forms[0].lpiRate.value="0";
    	  }
      }
      
      function allowDecimal(event) {
        
          const inputValue = event.target.value;
          const dotIndex = inputValue.indexOf('.');
          
          if (dotIndex !== -1 && inputValue.length - dotIndex > 2) {
             
              event.preventDefault();
              return false;
          }

         
          return (
              (event.charCode >= 48 && event.charCode <= 57) ||  // 0-9
              event.charCode === 46 ||  // decimal point
              event.charCode === 8      // backspace
          );
      }
      //end 1.0.0.20
      //Added by Nalin 1.0.0.21 start 
      function allowTypeUdm(event) {
    	    const char = String.fromCharCode(event.charCode).toUpperCase(); // Convert the character to uppercase
    	    // converted character is allowed
    	    return (
    	        (char >= '0' && char <= '9') ||
    	        char === '-' ||
    	        (char >= 'A' && char <= 'Z')
    	    );
    	}
      //1.0.0.21 end

	   
	   function disableAllColors()
	 	{
	 		for(count=0; count < document.forms[0].elements.length; count+=1)
		        {
		        	theelement = document.forms[0].elements[count];
		            if(theelement.name != null)
		            {
		            	theelement.style.backgroundColor="#ffffff";
		            }
		        }
	 	}

	   var responseFlag="Y";//1.0.0.7
	   
		function saveExit()
		{	
			saveData("saveExitPersonalInfo");
		}
		
		function save()
		{
			
			saveData("savePersonalInfo");
		}
		
		
		function saveData(saveType)
		{      
			
			
					var objToFocus=null;
					//var boolVal=true; // 1.0.0.6 , 1.0.0.8 start
					tempData.errorMessages="Errors:";
					//alert("saveAddressInfo ="+saveAddressInfo+" savePersonalInfo ="+savePersonalInfo);
			         if(savePersonalInfo=="Y")
			          {
				            if(document.forms[0].custEntityType.value=="0")
			                {
			                   alert(("Please select customer entity type.").toUpperCase());
			                   return;
			                }
			                else if(document.forms[0].applicantType.value=="0")
			                {
			                   alert(("Please select applicant type.").toUpperCase());
			                   return;
			                }
			          } 

		            if(checkForApplicantType==0)
		            {
		               if(savePersonalInfo=="N")
		               {
		                 alert(("For new customer personal information is mandatory.").toUpperCase());
		                 return;
		               }
		            }

		            if(savePersonalInfo=="N" && saveAddressInfo=="N")
		            {
		            	
		              alert(("For saving data either personal or address should be editable.").toUpperCase());
		              return;
		            }
		            if(saveAddressInfo=="N")
		            {
		                disabledAddressField();
		            }
					 if(savePersonalInfo=="Y")
		            {
		             							    
							 checkForm(); 
						
					}
					
					
					 
					if(document.forms[0].custEntityType.value=="0" && document.forms[0].applicantType.value=="0")
					{
						if(document.forms[0].isExisting[0].checked==false && document.forms[0].isExisting[1].checked==false)
						{
							alert("PLEASE SELECT CUSTOMER CATEGORY.");
							return;
						}
					}
					
					/*validationOfCustomer();
					validationOfPermanent();
					validationOfOffice();*/
		         validateAuthorisedSignotoryRows();			       
				 saveAuthorisedSignotoryRows();
				 //added by 1.0.0.19
				if(AuthSignAddressVal()==true)
					{
					return;
					}
				if(beneficiaryAddress()==true)
				{
				return;
				}
				//end by 1.0.0.19
				//start by 1.0.0.20
				if(document.forms[0].lpiRateType.value=="VARIABLE")
					{
					if(document.forms[0].lpiRate.value>100)
						{
						alert("THE LPI RATE CANNOT EXCEED 100.00%. PLEASE ENTER A VALID RATE.");
						return;
						}
					}
				//end by 1.0.0.20

					if(saveAddressInfo=="Y")
		            {  
		                
		                addressValidateForm();
		                
		               
		                
		              if(!checkMailingAddressStatus())
		              {
		              	return;
		              }//1.0.0.30 start
		             }else{
		             var gstRegistrationAddFlag="";
	    		
			    		if(document.forms[0].custEntityType.value=="1000000001"){
			    			gstRegistrationAddFlag=document.forms[0].gstRegistrationStatus.value;
			    		}else{
			    			gstRegistrationAddFlag=document.forms[0].gstRegistrationStatusCorp.value;
				    		}
		             
		             if(disableCustomerRegField!=null && disableCustomerRegField!="" && disableCustomerRegField=="Y"){
		             if(entityId=="1000000003" && gstRegistrationAddFlag=="Y" && gstAddressAdded!="Y"){
		             tempData.errorMessages = tempData.errorMessages +"\n"+"AT LEAST ONE ADDRESS MUST CONTAIN A GSTIN No. WHEN GST REGISTRATION STATUS IS REGISTERED.";
		             }
		             }//end

		             }
		            
					if(document.forms[0].heightInches.value > 11)
					{
						alert(("Maximum value in Inches can be 11.").toUpperCase());
						document.forms[0].heightInches.focus();
						return;
					}
				
				
		            if(tempData.errorMessages!="Errors:")
					{
						alert((tempData.errorMessages).replace("Errors:","").toUpperCase());
						return;
					}
					
					  if(document.forms[0].zipPin_temp.value!="" && document.forms[0].zipPin_temp.value!=null)
				   {
				   		if(document.forms[0].zipPin.value==""||document.forms[0].zipPin.value==null)
				   		{
				   			alert(("entered pincode is invalid.").toUpperCase());
				   			document.forms[0].zipPin_temp.value = "";
				   			document.forms[0].btnzipPin.disabled = false;
				   			return;
				   		}
				   }
				 	if(legalFormName=="")
				 	{ 
				 	  legalFormName="llaa.doc";
				 	}
	                document.forms[0].offCompanyName.disabled = false;
	                // 1.0.0.6 , 1.0.0.8 start
	               /* if(document.forms[0].entityIdForDm.value=="1000000008"){
						if(document.forms[0].addressType.value=="1200000008"){
					        
							var tempTotalIgst =Number(document.forms[0].quotation_cgstPer.value) + Number(document.forms[0].quotation_sgst_ugstPer.value) + Number(document.forms[0].quotation_igstPer.value);
							
							if((document.forms[0].quotation_sez_zone.value=="N" && document.forms[0].sez.value=="Y" && (Number(document.forms[0].igstpercent.value)!= tempTotalIgst)) ||((document.forms[0].quotation_sez_zone.value=="Y" && document.forms[0].sez.value=="Y") && (document.forms[0].state.value!=document.forms[0].quotation_registrationState.value) && (Number(document.forms[0].igstpercent.value)!= Number(document.forms[0].quotation_igstPer.value)))||((document.forms[0].quotation_sez_zone.value=="Y" && document.forms[0].sez.value=="Y") && (document.forms[0].state.value==document.forms[0].quotation_registrationState.value) && (Number(document.forms[0].igstpercent.value)!= (Number(document.forms[0].quotation_cgstPer.value) + Number(document.forms[0].quotation_sgst_ugstPer.value)))))
							{
								 boolVal = confirm(("The GST % has now changed from old value to new value due to change in Place of Supply. Kindly create new quotation. Thus, DM will be marked Rejected. Click Ok to continue else click Cancel").toUpperCase());
							}
						   
						}
	                    
					}
	                
	               if(!boolVal)
					{
					  window.location.reload(true);
					  return;
					}
	               */
	            	   // 1.0.0.6 , 1.0.0.8end
	               
	            
					var bool = confirm(("Proceeding to save changes.").toUpperCase());
					if(!bool)
					{
					    changeNumberCommaSeparated(document.forms[0].authCapital);
						 enableAddressField();
						 document.forms[0].addressType.disabled=true;
						
						 
						return;
					}
					else
					{  
					   
					  
						enableAllElements();	
						 
						if(document.getElementById("existing").style.display=="none")
						{ 
						   exisitingValue="";
						   document.forms[0].existingApplicantId.value = document.forms[0].applicantCode.value;
						   document.forms[0].isExisting.value = "Y";
						}	
						 else
						 {
						    if(document.forms[0].isExisting[0].checked==true)
						     {
						       exisitingValue="N";
						     }
						      else
						      {
						        exisitingValue="Y";
						       } 
						   	
						
						   	}
						  
						   		if(document.forms[0].psl.value=="0")
						   	{
						   		document.forms[0].psl.value="N";
						   	}
						   		

						checkidentityfication();
						
						if(responseFlag=="Y")//1.0.0.7
						{
						  responseFlag="N";//1.0.0.7
						  document.forms[0].action = "personalInfo.do?actionPerformed="+saveType+"&savePersonalInfo="+ savePersonalInfo +"&saveAddressInfo="+saveAddressInfo+"&FN="+legalFormName+"&exisitingValue="+exisitingValue+"&screenId="+screenId_temp+"&mode="+mode_temp;							
						  document.forms[0].method = "POST";
			  			  document.forms[0].submit();
			  			  document.forms[0].addressType.disabled=true;
			  			  document.forms[0].mailingAddressCheck.disabled=true;
						}
						else{//1.0.0.7
							alert(("Already in Process...").toUpperCase());//1.0.0.7
							return;
						}
			  			
			  			
			  		}
	          //  } // 1.0.0.6 ,1.0.0.8
					
		}

		
		function shareholdInfo(){
			var appId = document.forms[0].applicantID.value;
			window.open('shareholderInfo.do?method=display&appId='+appId+'','shareholderinfo','width=950,height=500,toolbar=0,resizable=0,status=1');

		}
		function votingRightsDetail()
	    {	    	
	   		var appId = document.forms[0].applicantCode.value;
			window.open('votingRightsInfo.do?method=displayVotingRights&appId='+appId+'','shareholderinfo','width=950px,height=700px,scrollbars=yes,menubar=no,toolbar=no,resizable=no,status=no');
		}

		function viewLegalDocument(){
			var appId = document.forms[0].applicantID.value;
			document.forms[0].action='viewLegalDocument.do?method=display&appId='+appId+'';
			document.forms[0].submit();

		}
		
		function worklist()
		{
			window.location.href="prospectListFrmNavAction.do?actionPerformed=displayProspectListInfo&screenId=1000000022&screenName=MAKER";
		}
		function populateDesignation(obj)
					{
					
						for(var i=0;i<designationTypeArr.length;i++)
						{
							obj.options[i+1]=new Option(designationTypeArr[i][1],designationTypeArr[i][0]);
						}
						
					}
					
		function populateApplicantType()		//1.0.0.0.5
					{
				
						for(var i=0;i<applicantTypeListArr.length;i++)
						{	
							if(applicantTypeListArr[i][0]==Number(1000000004))
								referenceFlag = applicantTypeListArr[i][2];
						}
						
					}			
					
		function designationChange(authSignRowIndex)
		{
			//1.0.0.28
			if(document.getElementById('authSignDesignation'+authSignRowIndex).value=="1000000001" 
				|| document.getElementById('authSignDesignation'+authSignRowIndex).value=="1000000056" || 
				document.getElementById('authSignDesignation'+authSignRowIndex).value=="1000000063" || 
				document.getElementById('authSignDesignation'+authSignRowIndex).value=="1000000057" || 
				document.getElementById('authSignDesignation'+authSignRowIndex).value=="1000000064" || 
				document.getElementById('authSignDesignation'+authSignRowIndex).value=="1000000065" || 
				document.getElementById('authSignDesignation'+authSignRowIndex).value=="1000000058" || 
				document.getElementById('authSignDesignation'+authSignRowIndex).value=="1000000059" || 
				document.getElementById('authSignDesignation'+authSignRowIndex).value=="1000000060" ||
				document.getElementById('authSignDesignation'+authSignRowIndex).value=="1000000017" ||
				document.getElementById('authSignDesignation'+authSignRowIndex).value=="1000000061"||
				document.getElementById('authSignDesignation'+authSignRowIndex).value=="1000000062")
			{
				document.getElementById('authSignDinNo'+authSignRowIndex).disabled=false;
			}
			else
			{
				document.getElementById('authSignDinNo'+authSignRowIndex).value='';
				document.getElementById('authSignDinNo'+authSignRowIndex).disabled=true;
			}
		
		}
		
		 var features = 'resizable= yes; status= no; scroll= no; help= no; toolbar=no; center= yes; statusbar=no; directories=no;location=no;';
			var vURL="CustomerPhoto.do?actionPerformed=displayCustomerPhoto"+"&rurl="+Math.random();
			function printCustomerPhotoUpload()
		     {
		  
		     		if(document.forms[0].btnPrint)
		      		document.forms[0].btnPrint.disabled=true;
		      		if(document.forms[0].btnCapture)
					document.forms[0].btnCapture.disabled=true;
		  		    window.open("CustomerPhoto.do?actionPerformed=displayCustomerPhoto"+"&rurl="+Math.random(),"Photo", features);
			     }

			function getXMLDOMFromStreamXX(data)
			{
			     data=((((data.replace(/&lt;/g,"<")).replace(/&gt;/g,">")).replace(/&#39;/g,"\"")).replace(/&apos;/g,"'")).replace(/&#47;/g,"/");
				 data="<?xml version='1.0' encoding='ISO-8859-1' ?> \n "+data;
				  try //Internet Explorer
				  {
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
							  alert((e.message).toUpperCase());
						 }
				  }
				return null; 
			}
				  
				  function pinChooser(tName)
			{
				var cityId = document.forms[0].city.value;
				
			    var paramList = tName+"~"+cityId;
				var sessionStatus = checkinterval(sessionTimeout);
				if(sessionStatus=="Y")
				{
					 window.open("populateMaster.do?qString="+paramList+"&label=Pincode&dependant=zipPin&rowNum=1","popzipPin","width=500, height=490,top=100,left=100, location=no, menubar=no, status=no, toolbar=no, scrollbars=no, resizable=no");
				}
				else
				{
					
					document.forms[0].action = "userAuthAction.do?dispatchMethod=logout";
			 		document.forms[0].method = "post";	
			  		document.forms[0].submit();
				}
			      
				 
			} 
				  

				  function clickBranchForPersonalInfo(obj)
					{   
					  var branchID = obj[obj.selectedIndex].value;
					 // var branchID = document.forms[0].branch.value;
					clearcombo(document.forms[0].relationShipManager);
					
					for (a=0; a< relationshipManagerArr.length; a++)
				    {
				    	if (branchID == relationshipManagerArr[a][2])
				    	{  
				    		document.forms[0].relationShipManager.options[document.forms[0].relationShipManager.options.length]= new Option(relationshipManagerArr[a][0] ,relationshipManagerArr[a][1]);
				    	   	    	}
				    }
				    }
					    
					     function clickChannelType(obj)
						{   
						var channelTypeID = obj[obj.selectedIndex].value;
						clearcombo(document.forms[0].channelName);
						
						for (d=0; d< channelNameArr.length; d++)
					    {
					    	if (channelTypeID == channelNameArr[d][2])
					    	{  
					    		document.forms[0].channelName.options[document.forms[0].channelName.options.length]= new Option(channelNameArr[d][0] ,channelNameArr[d][1]);
					    	   
					    	}
					    }
					    }
					     
					     function validateKeyContact(){
					   	  
					     //var table =availablekeyContactsRows.split("~");
					    	 //Add by 1.0.0.17
					    	if(keyContactsRowCount <=0){
			 						tempData.errorMessages = tempData.errorMessages +"\nKey Contacts must be specified";
			 					}
					   	
					   	for(var i=0; i<keyContactsRowCount; i++)
					     //for(var i=0; i < table.length; i++)
					   	{
					    	validate(document.getElementById("keyContactsMobile"+i),"MBV",'Mobile Number Of Key Contact');
						   	validate(document.getElementById("keyContactsMailId"+i),"EV",'Email Id Of Key Contact');
						   	
						   	//Start 1.0.0.13
						   //validate(document.getElementById("keyContactslob"+i),"DDMV",'LOB Of Key Contact');   //Comment by 1.0.0.17
						  //validate(document.getElementById("keyContactsContactType"+i),"DDMV",'Contact Type Of Key Contact'); //Comment by 1.0.0.17
						   	//if(document.getElementById("keyContactsName"+i).disabled == false) // 1.0.0.16
						   	//{			
						    	validate(document.getElementById("keyContactsName"+i),"NV,TMV",'Name Of Key Contact');  //1.0.0.15
						    	//validate(document.getElementById("keyContactsFirmName"+i),"ANVWSN,TMV",'Firm Name Of Key Contact'); //1.0.0.15    //Comment by 1.0.0.17
						    	//validate(document.getElementById("keyContactsAddress"+i),"AVNL,TMV",'Address Of Key Contact'); //1.0.0.15     //Comment by 1.0.0.17
						   	//}
						   	
						   	validate(document.getElementById("keyContactsMobile"+i),"TMV",'Mobile Number Of Key Contact');
						   	validate(document.getElementById("keyContactsMailId"+i),"TMV",'Email Id Of Key Contact');
						   	
						   	//End 1.0.0.13
					   								   	

					   	}
					   
					        
					   	  } 
					     function validationOfCustomer()
					 	{
					 		if(document.forms[0].custEntityType.value=="1000000001" || document.forms[0].custEntityType.value=="1000000002")
					 					{
					 			
					 					if(document.forms[0].addressType.value!="1000000001"){
					 					tempData.errorMessages = tempData.errorMessages +"\n"+"Please select Address type as Residence address";
							 			}
					 									
					 						atLeastOneFieldBetweenThree(document.forms[0].flatHouseNo,document.forms[0].floorNo,document.forms[0].buildingName,'Address1 of  address','Address2 of  address','Address3 of  address');			
					 						validate(document.forms[0].state_temp,"TMV",'State');
					 						validate(document.forms[0].city_temp,"TMV",'City');
					 						validate(document.forms[0].zipPin_temp,"TMV",'Pincode');
					 						//validate(document.forms[0].mobile,"TMV",'Mobile');
					 						validate(document.forms[0].residenceStatus,"CMV",'Occupancy Status');
					 						validate(document.forms[0].mobile,"TMV",'Mobile');
					 						validate(document.forms[0].noOfYearAtResidence,"TMV",'No of years');
											validate(document.forms[0].noOfMonthAtResidence,"TMV",'No of months');
											checkValueBetween(document.forms[0].noOfMonthAtResidence,0,11,"No of months in address");
					 							}	
					 	}
					  
					 // Validation on save Only function	
					 	function validationOfPermanent()
					 	{	
					 	//alert("fff")
					 		atLeastOneFieldBetweenThree(document.forms[0].permaAddFlatNo,document.forms[0].permaAddBuildingName,document.forms[0].permaAddLocality,'Address1 of permanent address','Address2 of permanent address','Address3 of permanent address');
					 		validate(document.forms[0].permaAddFlatNo,"AVNL,NZV",'Address1 of permanent address');
					 	 	validate(document.forms[0].permaAddBuildingName,"AVNL,NZV",'Address2 of permanent address');	 			 	
					 	 	validate(document.forms[0].permaAddLocality,"AVNL,NZV",'Address3 of permanent address');	 		
					 	 	validate(document.forms[0].permaAddLandMark,"AVNL",'Land Mark of permanent address');
					 	 	validate(document.forms[0].permaAddFloorNo,"AVNL,NZV",'Address3 of permanent address');
					 	 		
					 		validate(document.forms[0].state_temp,"TMV",'State of permanent address');
					 		validate(document.forms[0].city_temp,"TMV",'City of permanent address');
					 		validate(document.forms[0].zipOrPin_temp,"TMV",'Pincode of permanent address');
					 		validate(document.forms[0].permaAddMobile,"MBV,TMV",'Mobile of permanent address');
					 		
					 		 // add my manoj for bug id 12953
					 		//validate(document.forms[0].permaAddEmail,"TMV",'Email of permanent address');	// add my manoj for bug id 12953
					 		validate(document.forms[0].locationCategory,"CMV","Occupancy Status of permanent address");
					  		validate(document.forms[0].permaNoOfYearAtResidence,"TMV","No of year at residence of permanent address");
					 	 	validate(document.forms[0].permaNoOfMonthAtResidence,"TMV","No of months at residence of permanent address");
					 	}
					 	function validationOfOffice()
					 	 {
					 	    /*  if(!IsAlphaNumericWithSpecialSymbolNew(document.forms[0].offCompanyName,'Name Of Company of office address'))
					 			{
					 			  //alert("sdfsdf");
					 			  return true;
					 			} */
					 		
					 		if(document.forms[0].addressType.value!="1000000002"){
					 		tempData.errorMessages = tempData.errorMessages +"\n"+"Please select Address type as office address";
					 		}
					 		validate(document.forms[0].offCompanyName,"TMV,ANVWSN",'Name Of Company of office address');
					 		
					 		atLeastOneFieldBetweenThree(document.forms[0].flatHouseNo,document.forms[0].floorNo,document.forms[0].builingName,'Address1 of  address','Address2 of  address','Address3 of  address');			
	 						validate(document.forms[0].state_temp,"TMV",'State');
	 						validate(document.forms[0].city_temp,"TMV",'City');
	 						validate(document.forms[0].zipPin_temp,"TMV",'Pincode');
	 						validate(document.forms[0].mobile,"TMV",'Mobile');
	 						validate(document.forms[0].residenceStatus,"CMV",'Occupancy Status');
	 						validate(document.forms[0].noOfYearAtResidence,"TMV",'No of years');
							validate(document.forms[0].noOfMonthAtResidence,"TMV",'No of months');
							checkValueBetween(document.forms[0].noOfMonthAtResidence,0,11,"No of months in address");
					 	}
					     
					 	function clickSezZone(sezZoneCheckBox)/* 2.0.0.0.2 */ 
						{
					 		
					 		document.forms[0].igstApplicable.value="0";
					 		document.forms[0].lutNo.value="";
					 		document.forms[0].igstpercent.value="";
					 		document.forms[0].temp_loaCheckBox.checked=false;
							if(sezZoneCheckBox.value=='Y') 
							{
				               document.forms[0].igstApplicable.disabled=false; 
				               document.forms[0].lutNo.disabled=false;
				               document.forms[0].temp_loaCheckBox.disabled=false;
							}else
								{
				           			document.forms[0].igstApplicable.disabled=true; 
				           			document.forms[0].lutNo.disabled=true;
				           			document.forms[0].temp_loaCheckBox.disabled=true;
				           			
								}
						}

					     
					 	//1.0.0.4 start
					 	function fetchStateCity(obj)
					 	{
					 				if(obj.value!=null)
					 				{
					 				
					 						pinCode =obj.value;
					 			    
					 			    $.ajax({
					 					 url: "getStateCity.do?actionPerformed=getStateCity",
					 					 type: "post",
					 					 data:{
					 						 pinCode:pinCode
					 						  }, 
					 					 ontext: document.body,
					 					 success: function(response) {
					 						if(response!=null && response!="") 
					 								{ //alert(response)
					 								  var data = response.split("~");
					 								  
					 								   document.forms[0].state_temp.value=data[1];
					 								  document.forms[0].state.value=data[0];
					 								  document.forms[0].city_temp.value=data[3];
					 								  document.forms[0].city.value=data[2];
					 								  document.forms[0]. zipPin.value=data[4];	
					 								  
					 								 
					 								}}});
					 			    
					 			    
					 				
					 		
					 	}

					 	}
					 	// 1.0.0.4 end
					 	//add by 1.0.0.12					    
					 	function onlyValidSpecialCharacter(obj,DisplayName)
					 	{
					 		var allowed='() -/.,abcdefghijklmnopqrstuvwxyzABCDEFGHIJJKLMNOPQRSTUVWXYZ0123456789';
					 		var  str=Trim(obj.value);
					 		   var i;
					 		   for(i=0;i<str.length;i++)
					 		   {
					 		      if(allowed.indexOf(str.charAt(i)) == -1)
					 		      {
					 			      if(tempData.objToFocus == null)
					 			     {
					 			     	tempData.objToFocus = obj;
					 			     }
					 		         
					 		    	  tempData.errorMessages = tempData.errorMessages+"\n"+DisplayName+" "+"can contain only alphanumeric and special character like () -/.,"; //Now storing an error message 
					 		          return true;
					 		      } 
					 		   }
					 		   return false;
					 		

					 	}
					 	//end by 1.0.0.12
					 	
					 	
					 	
	//Start 1.0.0.18
					 	
					 	function saveBeneficiaryOwnerDetail()
						{
							var responseSplit1 = availableBeneficiaryOwnerRows.split("~");
							var beneficiaryOwnerIdStr="";
							var beneficiaryOwnerNameStr="";
							var beneficiaryOwnerPanStr="";
							var beneficiaryOwnerEmailStr="";
							var beneficiaryOwnerMobileStr="";
							var beneficiaryOwnerAddressStr="";         
							var beneficiaryOwnerStateStr="";
							var beneficiaryOwnerCityStr="";
							
							
							var tempBeneficiaryOwnerIdStr="";
							var tempBeneficiaryOwnerNameStr="";
							var tempBeneficiaryOwnerPanStr;
							var tempBeneficiaryOwnerEmailStr;
							var tempBeneficiaryOwnerMobileStr;
							var tempBeneficiaryOwnerAddressStr;
							var tempBeneficiaryOwnerStateStr;
							var temaBeneficiaryOwnerCityStr;
							
							var finalBeneficiaryOwnerString="";
							
							for(var i=0; i < responseSplit1.length; i++)
							{
								var beneficiaryOwnerRowString="";
							
								if(responseSplit1[i].length > 0)
								{
									tempBeneficiaryOwnerIdStr=document.getElementById(('beneficiaryOwnerId'+responseSplit1[i]));
									tempBeneficiaryOwnerNameStr=document.getElementById(('beneficiaryOwnerName'+responseSplit1[i]));
									tempBeneficiaryOwnerPanStr=document.getElementById(('beneficiaryOwnerPan'+responseSplit1[i]));	
									tempBeneficiaryOwnerEmailStr = document.getElementById(('beneficiaryOwnerEmail'+responseSplit1[i]));					
									tempBeneficiaryOwnerMobileStr = document.getElementById(('beneficiaryOwnerMobile'+responseSplit1[i]));
									tempBeneficiaryOwnerAddressStr = document.getElementById(('beneficiaryOwnerAddress'+responseSplit1[i]));
									tempBeneficiaryOwnerStateStr = document.getElementById(('beneficiaryOwnerState'+responseSplit1[i]));
									temaBeneficiaryOwnerCityStr = document.getElementById(('beneficiaryOwnerCity'+responseSplit1[i]));			
									
								
									beneficiaryOwnerRowString=tempBeneficiaryOwnerIdStr.value+'~'+tempBeneficiaryOwnerNameStr.value.trim()+'~'+tempBeneficiaryOwnerPanStr.value.trim()+'~'
												+tempBeneficiaryOwnerEmailStr.value.trim()+'~'+tempBeneficiaryOwnerMobileStr.value.trim()+'~'
												+tempBeneficiaryOwnerAddressStr.value.trim()+'~'+tempBeneficiaryOwnerStateStr.value.trim()+'~'								
												+temaBeneficiaryOwnerCityStr.value.trim();
								
								
							if(beneficiaryOwnerRowString!="")
							{
								finalBeneficiaryOwnerString=finalBeneficiaryOwnerString+beneficiaryOwnerRowString+'^';
							}
							
							//finalKeyContactsRowString = finalKeyContactsRowString.substring(finalKeyContactsRowString.lastIndexOf("^") + 1, finalKeyContactsRowString.length);
							
								
								}
							}
							if (finalBeneficiaryOwnerString != null  || finalBeneficiaryOwnerString !="") {
								finalBeneficiaryOwnerString=finalBeneficiaryOwnerString.slice(0, -1);
							}
							//	finalKeyContactsRowString = finalKeyContactsRowString.substring(0,finalKeyContactsRowString.length()-1);
							//}
								document.forms[0].beneficiaryOwnerDataStr.value = finalBeneficiaryOwnerString;
						
					    
							return true;
						}
					 	
					 	
					 	function validateBeneficiaryOwner(){
						   	for(var i=0; i<beneficiaryOwnerRowCount; i++)
						   	{
							    	validate(document.getElementById("beneficiaryOwnerName"+i),"NV,TMV",'Name Of Beneficiary Owner');
							    	validate(document.getElementById("beneficiaryOwnerEmail"+i),"EV",'E-mail Of Beneficiary Owner');
							    	if(document.getElementById("beneficiaryOwnerAddress"+i)){
								    	var benfOwnAddr = document.getElementById("beneficiaryOwnerAddress"+i).value;
								    	if(benfOwnAddr !=null && benfOwnAddr != "null" && benfOwnAddr != "" && benfOwnAddr != undefined){
								    		validate(document.getElementById("beneficiaryOwnerState"+i),"TMV",'State Of Beneficiary Owner');
								    		validate(document.getElementById("beneficiaryOwnerCity"+i),"TMV",'City Of Beneficiary Owner');
								    	}
							    	}
						   	}
						 } 
					 	
					 	var selectedBeneficiaryOwnerRows = "";
						var selectedBeneficiaryOwnerRowCount=0;

						function toggleBeneficiaryOwnerRow(checkbox1Obj)
							{
								if(checkbox1Obj.checked)
								{				
									selectedBeneficiaryOwnerRows = selectedBeneficiaryOwnerRows + "~" + checkbox1Obj.value + "~";
									selectedBeneficiaryOwnerRowCount++;
								}
								else
								{			
									selectedBeneficiaryOwnerRows = selectedBeneficiaryOwnerRows.replace("~" + checkbox1Obj.value + "~","");
									selectedBeneficiaryOwnerRowCount--;
								}
							}
					 	
					 	function removeBeneficiaryOwnerRows()
						{
						var rowsArr1 = selectedBeneficiaryOwnerRows.split("~");
						var removeBeneficiaryOwnerDataStr="";
							
							if(rowsArr1=='')
							{
								alert(('Select At least One Row.').toUpperCase());
								return false;
							}
							
							
							for (var i = 0; i < rowsArr1.length; i++)
							{
								if(rowsArr1[i].length > 0)
								{
									if(document.getElementById("beneficiaryOwnerId"+rowsArr1[i]).value){
										removeBeneficiaryOwnerDataStr=removeBeneficiaryOwnerDataStr+document.getElementById("beneficiaryOwnerId"+rowsArr1[i]).value+"~";
									}
									
									document.getElementById("beneficiaryOwnerRowtable"+rowsArr1[i]).innerHTML = "";
									document.getElementById("beneficiaryOwnerRowtable"+rowsArr1[i]).style.display="none";
									availableBeneficiaryOwnerRows = availableBeneficiaryOwnerRows.replace("~" + rowsArr1[i] + "~","");
									beneficiaryOwnerRowCount--;
									selectedBeneficiaryOwnerRowCount--;
								}
							}
							selectedBeneficiaryOwnerRows="";
							removeBeneficiaryOwnerDataStr=removeBeneficiaryOwnerDataStr.slice(0, -1);
							document.forms[0].removeBeneficiaryOwnerDataStr.value = removeBeneficiaryOwnerDataStr;
							
						
						}	 
					 	
					 	function selectAllBeneficiaryRow(obj){
							var rowCount = $("#beneficiaryOwnerRowsDiv tr").length
							  if(obj.checked){
								  for(var i=0;i<rowCount;i++){
									    if(document.getElementById("benCheckbox"+i).checked==false){
										    	document.getElementById("benCheckbox"+i).checked=true;
										    	toggleBeneficiaryOwnerRow(document.getElementById("benCheckbox"+i));
									    	}
									  }
							  }
							  else{
								  for(var i=0;i<rowCount;i++){
									    if(document.getElementById("benCheckbox"+i).checked==true){
										    	document.getElementById("benCheckbox"+i).checked=false;
										    	toggleBeneficiaryOwnerRow(document.getElementById("benCheckbox"+i));
									    }
								}
								  
							}
						}
					 	
					 	function selectAllAuthSignRow(obj){
							var rowCount = $("#authorisedSignatoryRowsDiv tr").length
							  if(obj.checked){
								  for(var i=0;i<rowCount;i++){
									    if(document.getElementById("AuthSignCheckbox"+i).checked==false){
										    	document.getElementById("AuthSignCheckbox"+i).checked=true;
										    	toggleAuthSignRow(document.getElementById("AuthSignCheckbox"+i));
									    	}
									  }
							  }
							  else{
								  for(var i=0;i<rowCount;i++){
									    if(document.getElementById("AuthSignCheckbox"+i).checked==true){
										    	document.getElementById("AuthSignCheckbox"+i).checked=false;
										    	toggleAuthSignRow(document.getElementById("AuthSignCheckbox"+i));
									    }
								}
								  
							}
						}
					 	
					 	
					 	function appDupeInitiate(objType) {
					 		var entityType = "";
					 		var dedRowIdList = "";
					 		var dedupeRowIdListStr = "";
					 		var applicantIdVal = document.forms[0].applicantID.value;
							if(objType=="AUTH SIGNATORY"){
								entityType = 'AUTH SIGNATORY';
								var rowsArr1 = selectedAuthSignRows.split("~");
									if(rowsArr1==''){
										alert(('Select At least One Saved Row.').toUpperCase());
										return false;
									}
									
									for (var i = 0; i < rowsArr1.length; i++){
										if(rowsArr1[i].length > 0){
											if(document.getElementById("authSignId"+rowsArr1[i]).value){
												dedupeRowIdListStr=dedupeRowIdListStr+document.getElementById("authSignId"+rowsArr1[i]).value+",";
											}
										}
									}
									if(dedupeRowIdListStr==''){
										alert(('Select At least One Saved Row.').toUpperCase());
										return false;
									}
									dedupeRowIdListStr=dedupeRowIdListStr.slice(0, -1);
									
							}else if(objType=="BENEFICIARY"){
								entityType = 'BENEFICIARY';
								var rowsArr1 = selectedBeneficiaryOwnerRows.split("~");
									if(rowsArr1==''){
										alert(('Select At least One Saved Row.').toUpperCase());
										return false;
									}
									for (var i = 0; i < rowsArr1.length; i++){
										if(rowsArr1[i].length > 0){
											if(document.getElementById("beneficiaryOwnerId"+rowsArr1[i]).value){
												dedupeRowIdListStr=dedupeRowIdListStr+document.getElementById("beneficiaryOwnerId"+rowsArr1[i]).value+",";
											}
										}
									}
									if(dedupeRowIdListStr==''){
										alert(('Select At least One Saved Row.').toUpperCase());
										return false;
									}
									dedupeRowIdListStr=dedupeRowIdListStr.slice(0, -1);
							}
							var bool = confirm(("Please ensure the data for "+objType+" is saved before Initiating a Dedupe.").toUpperCase());
					 		if(!bool){
					 			return false;
					 		}
							 $.ajax({
								type : "post",
								url : "personalInfo.do?actionPerformed=appDedupeInit",
								context : document.body,
								data : {
										entityType:entityType,
										dedRowIdList:dedupeRowIdListStr,	
										applicantIdVal:applicantIdVal
								},
								success : function(response) {
									var json = JSON.parse(response);	
									//if(json.PC_STATUS=='F'){
										alert(json.PC_MESSAGE);
										
									//}
								}
							   
							 });
					 	}
					 	
					 	function appDedupeInitView(entityType,rowNo) {
					 		var entityId = "";
					 		var entType = "";
					 			if(entityType=="AUTH SIGNATORY"){
					 				entType = 'AUTH SIGNATORY';
					 				entityId = document.getElementById("authSignId"+rowNo).value;
								}else if(entityType=="BENEFICIARY"){
									entType = 'BENEFICIARY';
									entityId = document.getElementById("beneficiaryOwnerId"+rowNo).value;
								}
					 			
							 $.ajax({
								type : "post",
								url : "personalInfo.do?actionPerformed=appDedupeInitView",
								context : document.body,
								data : {
										entityType:entType,
										entityId:entityId	
								},
								success : function(response) {
									var json = JSON.parse(response);	
									if(json.PC_STATUS=='S'){
										//alert(json.PC_MESSAGE+" CUST_ID: "+json.PC_CUST_ID+" SEARCH_ID: "+json.PC_SEARCH_ID);
										window.open("dedupe.do?actionPerformed=displayDedupeCustomerMatchData&screenId=1000001015&modeV&custId="+json.PC_CUST_ID+"&searchId="+json.PC_SEARCH_ID+"&ajaxCall=null&lastRun=Y&dispatchMethod=view&custCode=undefined&custCode=undefined&dedupResultViewFlag=Y","Target","height=570,width=520,scrollbars=yes,status=yes");
									}else{
										alert(json.PC_MESSAGE);
										//window.open("dedupe.do?actionPerformed=displayDedupeCustomerMatchData&screenId=1000001015&modeV&custId=95166&searchId=95026&ajaxCall=null&lastRun=Y&dispatchMethod=view&custCode=undefined&dedupResultViewFlag=Y","Target","height=570,width=520,scrollbars=yes,status=yes");
										
									}
								}
							   
							 });
					 	}
	//End 1.0.0.18
					 	
					 	//1.0.0.23 start
					 	
					 	function openPassportValidationModal() {
					 		document.getElementById("passportValidationModal").style.display = "block";
					 		document.getElementById("passportNoSpan").style.display="none";
							document.getElementById("dateOfIssueSpan").style.display="none";
					 		event.preventDefault();
					 	}

					 	function closePassportValidationModal() {
					 		document.getElementById("passportValidationModal").style.display = "none";
					 		document.forms[1].dateOfissue.value="";
					 		document.forms[1].fileNo.value="";
					 	}
					 	
					 	
					 	
					 	function checkPassportNoUniqueness(obj){
					 		var passportNo = obj.value;
					 		if(obj.value!="") {
					 			
					 			document.getElementById("passporVerifiedIcon").style.display="none";
								document.getElementById("fetchPassportDtlsBtn").style.display="inline";
								document.getElementById("fetchPassportDtlsBtn").disabled = false;
					 		}
					 		else{
					 			//alert(('Passport No should not be .').toUpperCase());
								//obj.value="";
								document.getElementById("fetchPassportDtlsBtn").style.display="none";
								document.getElementById("passporVerifiedIcon").style.display="none";
								document.getElementById("passportVerifiedPendingIcon").style.display="none";
					 		}
					 	}
					 	
					 	
					 	function fileNoAlert(fileNoObj) {
					 		var passFileNo=fileNoObj.value;
					 		if(passFileNo =="" || passFileNo==undefined){
					 			document.getElementById("passportNoSpan").style.display="block";
					 			
					 		}
					 		else{
					 			document.getElementById("passportNoSpan").style.display="none";
					 		}
				    	   
				    	}
					 	
					 	function doiAlert(doiObj) {
					 		var passDoi=doiObj.value;
					 		if(passDoi=="" || passDoi==undefined){
					 			document.getElementById("dateOfIssueSpan").style.display="block";
					 			
					 		}
					 		else{
					 			document.getElementById("dateOfIssueSpan").style.display="none";
					 		}
				    	   
				    	}
					 	
					 	
					 	
			function fetchPassportDtlsApiCall(){
				var passportNoValue;
				passportNoValue=document.forms[0].passportNo.value;
				var customerFullName;
				if(document.forms[0].firstName.value!="") {
					customerFullName=document.forms[0].firstName.value;
				}
				if(document.forms[0].middleName.value!="") {
					customerFullName=customerFullName+" "+document.forms[0].middleName.value;
				}
				if(document.forms[0].lastName.value!="") {
					customerFullName=customerFullName+" "+document.forms[0].lastName.value;
				}
				
				var dateOfBirth=document.forms[0].dateOfBirth.value;
				
				//document.forms[0].dateOfBirth.focus();
				//var inputDate = new Date(dateOfBirth);
				// Extract day, month, and year components from the Date object
				//var day = inputDate.getDate();
				//var month = inputDate.getMonth() + 1; // Months are zero-based, so we add 1
				//var year = inputDate.getFullYear();
				//day = day < 10 ? "0" + day : day; // Format the date components with leading zeros if necessary
				//month = month < 10 ? "0" + month : month; // Format the month components with leading zeros if necessary
				// Format the date components as "dd/mm/yyyy"
				//var dob = day+"/"+month+"/"+year;
				document.getElementById("passportNoSpan").style.display="none";
				document.getElementById("dateOfIssueSpan").style.display="none";
                var fileNo=document.forms[1].fileNo.value;
                var doi=document.forms[1].dateOfissue.value;
                if((fileNo==undefined || fileNo=="")&&(doi==undefined || doi=="")){
                	document.getElementById("passportNoSpan").style.display="block";
                	document.getElementById("dateOfIssueSpan").style.display="block";
                	return false;
                }
				
				if(fileNo==undefined || fileNo==""){
					document.getElementById("passportNoSpan").style.display="block";
					document.forms[1].fileNo.focus();
					return false;
				}
				
				if(doi==undefined || doi==""){
					document.getElementById("dateOfIssueSpan").style.display="block";
					document.forms[1].dateOfissue.focus();
					return false;
				}
				
			    closePassportValidationModal();
			    
				if(passportNoValue!="") {
					$(document)
					.ready(
							function() {
								$('#loading').show();
					 			
					 		$.ajax({
								type : "POST",
								url : "ibsCallAction.do?actionPerformed=passportNoVerifier",
								context : document.body,
								data : {
									    passportNoValue:passportNoValue,
									    name:customerFullName,
									    dob:dateOfBirth,
									    doi:doi,
									    fileNo:fileNo
								},
								success : function(response) {
									$('#loading').hide();
									var jsonRes = JSON.parse(response);
									//var payloadObj = jsonRes.payloadObj;
									//var resultObj = jsonRes.resultObj;
									if (jsonRes.responseStatus=="SUCCESS"){
										if ((jsonRes.passportNumberMatch=="TRUE")&&(jsonRes.dateOfIssueMatch=="TRUE")&&(jsonRes.nameMatch=="TRUE")){
										//alert((jsonRes.responseStatus).toUpperCase());
										document.getElementById("fetchPassportDtlsBtn").style.display="none";
										document.getElementById("passporVerifiedIcon").style.display="inline";
										document.getElementById("passporVerifiedIcon").disabled = false;
										document.getElementById("passportVerifiedPendingIcon").style.display="none";
										document.forms[0].passportNoVerifiedFlag.value="Y";
										}
										else{
											alert(("MATCHING FAILED FOR NAME OR DOI OR PASSPORT NO. ").toUpperCase());
											document.getElementById("passporVerifiedIcon").style.display="none";
											document.getElementById("passportVerifiedPendingIcon").style.display="inline";
											document.getElementById("passportVerifiedPendingIcon").disabled = false;
											document.forms[0].passportNoVerifiedFlag.value="N";
										}
									}
									else {
										alert((jsonRes.responseMsg).toUpperCase());
										document.getElementById("passporVerifiedIcon").style.display="none";
										document.getElementById("passportVerifiedPendingIcon").style.display="inline";
										document.getElementById("passportVerifiedPendingIcon").disabled = false;
										document.forms[0].passportNoVerifiedFlag.value="N";
									}
									
								}
							   
							 });
						 });
					 }
					 		
				}
					 	
			//1.0.0.23 end
			
			
			
			 //start 1.0.0.22
			   function checkDlUniqueness()
			   {
			 
				   var drivingLicenseNo = document.forms[0].drivingLicenseNo.value;
			        var validateDlButton = document.getElementById("validateDlButton");

			        if (drivingLicenseNo.trim() !== "" && drivingLicenseNo.length >14) {
			            validateDlButton.style.display = "inline";
			            validateDlButton.style.disabled = false ;
			            document.getElementById("DLVerifiedIcon").style.display="none";
			            document.getElementById("DLVerifiedPendingIcon").style.display="none";
			            
			        } else {
			        	alert("DRIVING LICENCE SHOULD BE MORE THAN FOURTEEN CHARACTERS");
			        	document.forms[0].drivingLicenseNo.value="";
			            validateDlButton.style.display = "none";
			            document.getElementById("DLVerifiedIcon").style.display="none";
			            document.getElementById("DLVerifiedPendingIcon").style.display="none";
			        }
				   
				
			    
					
				

			   }
			   
			   function DLVerifierApiCall()
			   {
				   var drivingLicence=document.forms[0].drivingLicenseNo.value;
				  var dob= document.forms[0].dateOfBirth.value;
				   if(drivingLicence!=null)
					{
					   $(document)
						.ready(
								function() {
									// Get the form data. This serializes the entire form. pritty easy huh!
									$('#loading').show();
				   
				   $.ajax({
						url : 'ibsCallAction.do?actionPerformed=dlVerifier',
						type : 'POST',
						data : {
							drivingLicence : drivingLicence,
							dob:dob
						},
						success : function(response) {
							$('#loading').hide();
							
							if (response != null
									&& response != "") {
								var resJson = JSON
										.parse(response);
								//alert("IBS response"+resJson);
								
								if (resJson.responseStatus=="SUCCESS"){
									var confirmationMsg="Please verify the additional details received from API while validating the Driving Licence.\nName : "+resJson.name+"\nDOB : "+resJson.dob;//1.0.0.26
									if (confirm(confirmationMsg.toUpperCase())){
									
									//alert((resJson.responseStatus).toUpperCase());
									 validateDlButton.style.display = "none";
									 
									 document.getElementById("DLVerifiedIcon").style.display="inline";
										document.getElementById("DLVerifiedIcon").disabled = false;
										 document.getElementById("DLVerifiedPendingIcon").style.display="none";
											document.getElementById("DLVerifiedPendingIcon").disabled = true;
											document.forms[0].dlVarifyFlag.value="Y";
								}
								else {    //1.0.0.26
									
									 validateDlButton.style.display = "inline";
									 validateDlButton.style.disabled = false ;
									 document.getElementById("DLVerifiedPendingIcon").style.display="inline";
										document.getElementById("DLVerifiedPendingIcon").disabled = false;
										 document.getElementById("DLVerifiedIcon").style.display="none";
										 document.getElementById("DLVerifiedIcon").disabled = true;
										 document.forms[0].dlVarifyFlag.value="N";
								}
								}else{
									alert((resJson.responseMsg).toUpperCase());
									
									 validateDlButton.style.display = "inline";
									 validateDlButton.style.disabled = false ;
									 document.getElementById("DLVerifiedPendingIcon").style.display="inline";
										document.getElementById("DLVerifiedPendingIcon").disabled = false;
										 document.getElementById("DLVerifiedIcon").style.display="none";
										 document.getElementById("DLVerifiedIcon").disabled = true;
										 document.forms[0].dlVarifyFlag.value="N";
									
								}
								
							} else {
								alert(("Empty response generated.")
										.toUpperCase());
							}
							

						}
					});
				   
								});
					}
								
			   }
			   //end 1.0.0.22
			   
			  
			   
			   
			   
			   
			   
			   
			 //1.0.0.24 Start  
			   function validateVoterIDFromIbs()
			   {
				   var voterIDValue;
					voterIDValue = document.forms[0].voterId.value;
				   if(voterIDValue!=null)
					{
					   $(document)
						.ready(
								function() {
									// Get the form data. This serializes the entire form. pritty easy huh!
									$('#loading').show();
				   
				   $.ajax({
						url : 'ibsCallAction.do?actionPerformed=verifyVoterId',
						type : 'POST',
						data : {
							voterIDValue : voterIDValue
						},
						success : function(response) {
							$('#loading').hide();
							
							if (response != null
									&& response != "") {
								var resJson = JSON.parse(response);
								if (resJson.responseStatus=="SUCCESS"){
									var confirmationMsg="Please verify the additional details received from API while validating the Voter ID.\nName : "+resJson.name;//1.0.0.26
									if (confirm(confirmationMsg.toUpperCase())){
									//alert((resJson.responseStatus).toUpperCase());
									document.getElementById("verifyVoterBtn").style.display = "none";
									 //document.getElementById("voterVerifiedIcon").style.display="block";
									 document.getElementById("voterVerifiedIcon").style.display="inline";
									 document.getElementById("voterVerifiedIcon").disabled = false;
									 document.getElementById("voterVerifiedPendingIcon").style.display="none";
									 document.forms[0].voterIdFlg.value="Y";
								}
								else { //1.0.0.26
									
									//document.getElementById("verifyVoterBtn").style.display = "block";
									document.getElementById("voterVerifiedIcon").style.display="none";
									document.getElementById("verifyVoterBtn").style.display="inline";
									document.getElementById("verifyVoterBtn").disabled = false;
									document.getElementById("voterVerifiedPendingIcon").style.display="inline";
									document.getElementById("voterVerifiedPendingIcon").disabled = false;
									document.forms[0].voterIdFlg.value="N";
								}
								}
								else {
									alert((resJson.responseMsg).toUpperCase());
									//document.getElementById("verifyVoterBtn").style.display = "block";
									document.getElementById("voterVerifiedIcon").style.display="none";
									document.getElementById("verifyVoterBtn").style.display="inline";
									document.getElementById("verifyVoterBtn").disabled = false;
									document.getElementById("voterVerifiedPendingIcon").style.display="inline";
									document.getElementById("voterVerifiedPendingIcon").disabled = false;
									document.forms[0].voterIdFlg.value="N";
								}
								
							} else {
								alert(("Empty response generated.").toUpperCase());
								document.getElementById("voterVerifiedIcon").style.display="none";
								document.getElementById("voterVerifiedPendingIcon").style.display="inline";
								document.getElementById("voterVerifiedPendingIcon").disabled = false;
							}
							

						}
					});
				  
				   
				});
			  }
								
			}
			   
			   

			   
			   		  
			   function checkVoterIDUniqueness(obj) {
				    if (obj.value != "") {
				        var voterID = obj.value;
				        var voterIDRegex = /^[A-Z]{3}[0-9]{7}$/; // Example regex for voter ID format: Three alphabets followed by seven digits
				        if (!voterIDRegex.test(voterID)) {
				            alert("Invalid Voter ID format. Please enter a valid Voter ID.");
				            obj.value = "";
				            obj.focus();
				            //document.getElementById("verifyVoterBtn").style.display="block";
				            document.getElementById("verifyVoterBtn").style.display = "none";
				            document.getElementById("voterVerifiedIcon").style.display="none"; 
				        }
				        else{
				        	document.getElementById("voterVerifiedIcon").style.display="none";
				        	document.getElementById("verifyVoterBtn").style.display="inline";
							document.getElementById("verifyVoterBtn").disabled = false;
				        }
				    }
				};

			
			
				////end 1.0.0.24
				//1.0.0.25 Start
				
				 function checkUdyamField(obj) {
			 	document.getElementById("udyamVerifiedPendingIcon").style.display = "none";
			    document.getElementById("udyamVerifiedIcon").style.display="none";
			    document.forms[0].udyamIdFlg.value="";
			    if (obj.value== "") {
			            obj.focus();
			            document.getElementById("verifyUdyamBtn").style.display = "none";
			        }
			        else{
			        	document.getElementById("verifyUdyamBtn").style.display="inline";
						document.getElementById("verifyUdyamBtn").disabled = false;
			        }
			};
			//1.0.0.27
			function checkUdyamField1(obj) {
			 	document.getElementById("udyamVerifiedPendingIcon1").style.display = "none";
			    document.getElementById("udyamVerifiedIcon1").style.display="none";
			    document.forms[0].udyamIdFlg.value="";
			    if (obj.value== "") {
			            obj.focus();
			            document.getElementById("verifyUdyamBtn1").style.display = "none";
			        }
			        else{
			        	document.getElementById("verifyUdyamBtn1").style.display="inline";
						document.getElementById("verifyUdyamBtn1").disabled = false;
			        }
			};
			
			   
		   function validateUdyamNo()
		   {
			   var udyamNo;
				udyamNo = document.forms[0].udyamNo.value;
				//var doi =document.forms[0].dateOfIncorporation.value;
			   if(udyamNo!=null)
				{
				   $(document)
					.ready(
							function() {
								$('#loading').show();
			   
			   $.ajax({
					url : 'ibsCallAction.do?actionPerformed=validateUdyamNo',
					type : 'POST',
					data : {
						udyamNumber : udyamNo
					},
					success : function(response) {
						$('#loading').hide();
						
						if (response != null
								&& response != "") {
							var resJson = JSON.parse(response);
							if (resJson.responseStatus=="SUCCESS"){
								
								var confirmationMsg = "Please verify the additional details received from API while validating the Udyam Number.\nCompany Name : " + resJson.name ;//+ "\nDOI : " + resJson.doi//1.0.0.26
								if (confirm(confirmationMsg.toUpperCase())){
								//alert((resJson.responseStatus).toUpperCase());
								document.getElementById("verifyUdyamBtn").style.display = "none";
								 document.getElementById("udyamVerifiedIcon").style.display="inline";
								 //document.getElementById("udyamVerifiedIcon").disabled = false;
								 document.getElementById("udyamVerifiedPendingIcon").style.display="none";
								 document.forms[0].udyamIdFlg.value="Y";
							}
							else {//1.0.0.26
								
								document.getElementById("udyamVerifiedIcon").style.display="none";
								document.getElementById("verifyUdyamBtn").style.display="inline";
								document.getElementById("verifyUdyamBtn").disabled = false;
								document.getElementById("udyamVerifiedPendingIcon").style.display="inline";
								//document.getElementById("voterVerifiedPendingIcon").disabled = false;
								document.forms[0].udyamIdFlg.value="N";
							}
							}
							else {
								alert((resJson.responseMsg).toUpperCase());
								document.getElementById("udyamVerifiedIcon").style.display="none";
								document.getElementById("verifyUdyamBtn").style.display="inline";
								document.getElementById("verifyUdyamBtn").disabled = false;
								document.getElementById("udyamVerifiedPendingIcon").style.display="inline";
								//document.getElementById("voterVerifiedPendingIcon").disabled = false;
								document.forms[0].udyamIdFlg.value="N";
							}
							
						} else {
							alert(("Empty response generated.").toUpperCase());
							document.getElementById("udyamVerifiedIcon").style.display="none";
							document.getElementById("udyamVerifiedPendingIcon").style.display="inline";
							//document.getElementById("voterVerifiedPendingIcon").disabled = false;
							document.forms[0].udyamIdFlg.value="N";
						}

					}
				});
			   
			});
		  }
							
		};//1.0.0.25 end
		
		//1.0.0.27
		function validateUdyamNo1()
		   {
			   var udyamNo;
				udyamNo = document.forms[0].udyamNoInd.value;
			   if(udyamNo!=null)
				{
				   $(document)
					.ready(
							function() {
								$('#loading').show();
			   
			   $.ajax({
					url : 'ibsCallAction.do?actionPerformed=validateUdyamNo',
					type : 'POST',
					data : {
						udyamNumber : udyamNo
					},
					success : function(response) {
						$('#loading').hide();
						
						if (response != null
								&& response != "") {
							var resJson = JSON.parse(response);
							if (resJson.responseStatus=="SUCCESS"){
								var confirmationMsg="Please verify the additional details received from API while validating the Udyam Number.\nCompany Name : "+resJson.name;//+"\n DOI :"+resJson.doi//1.0.0.22
								if (confirm(confirmationMsg.toUpperCase())){
								//alert((resJson.responseStatus).toUpperCase());
								document.getElementById("verifyUdyamBtn1").style.display = "none";
								 document.getElementById("udyamVerifiedIcon1").style.display="inline";
								 //document.getElementById("udyamVerifiedIcon").disabled = false;
								 document.getElementById("udyamVerifiedPendingIcon1").style.display="none";
								 document.forms[0].udyamIdFlg.value="Y";
							}
							else { //1.0.0.22
								
								document.getElementById("udyamVerifiedIcon1").style.display="none";
								document.getElementById("verifyUdyamBtn1").style.display="inline";
								document.getElementById("verifyUdyamBtn1").disabled = false;
								document.getElementById("udyamVerifiedPendingIcon1").style.display="inline";
								//document.getElementById("voterVerifiedPendingIcon").disabled = false;
								document.forms[0].udyamIdFlg.value="N";
							}
							}
							else {
								alert((resJson.responseMsg).toUpperCase());
								document.getElementById("udyamVerifiedIcon1").style.display="none";
								document.getElementById("verifyUdyamBtn1").style.display="inline";
								document.getElementById("verifyUdyamBtn1").disabled = false;
								document.getElementById("udyamVerifiedPendingIcon1").style.display="inline";
								//document.getElementById("voterVerifiedPendingIcon").disabled = false;
								document.forms[0].udyamIdFlg.value="N";
							}
							
						} else {
							alert(("Empty response generated.").toUpperCase());
							document.getElementById("udyamVerifiedIcon1").style.display="none";
							document.getElementById("udyamVerifiedPendingIcon1").style.display="inline";
							//document.getElementById("voterVerifiedPendingIcon").disabled = false;
							document.forms[0].udyamIdFlg.value="N";
						}
						

					}
				});
			   
			});
		  }
							
		};////1.0.0.21 end
		
		//1.0.0.28 start
		 function fetchDesignation(){
			 var constitutionId = document.getElementById('legalFormTypeCorp').value;

				$.ajax({
					url : 'personalInfo.do?actionPerformed=getDesignationMaster',
					type : 'POST',
					data : {
						constitutionId : constitutionId
					},
					success : function(response) {

						if (response != null && response != "") {
							var resJson = JSON.parse(response);
							designationTypeArr=new Array();
							for(let i=0;i<resJson.length;i++){
								designationTypeArr[i]=new Array(2);
								designationTypeArr[i][0]=resJson[i].masterId;
						    	designationTypeArr[i][1]=resJson[i].masterName;
								
							}
							
							for(var i=0; i<authSignRowIndex; i++)
							{
								
								var select = document.getElementById("authSignDesignation"+i);
								var length = select.options.length;
								for (k = length-1; k >= 1; k--) {
								  select.options[k] = null;
								}
								populateDesignation(document.getElementById("authSignDesignation"+i));
							}

						} else {
							//alert(("Empty response generated.").toUpperCase());
						}

					}
				});
				 };
		
		
	//1.0.0.28 end		
