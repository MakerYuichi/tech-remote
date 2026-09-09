<!--
  Version    Date           Changes made by       Reasons
  1.0.0.1	07-DEC-2018	    Apurva			      QA bugId 63100 LMS > Refund Viewer >" Status" Column is missing and also showing some unusual data
  1.0.0.2	22-APR-2019	    KAPIL MIDDHA	      pass INSTRUMENT_NAME in request of cashReceipt.do?actionPerformed=displayCashReceipt.....
  1.0.0.3	29-APR-2019	    Apurva                enable link only in case of otc
  1.0.0.4	02-Sep-2026	    Sanchi Agarwal         CR - H2H STATUS, POST APPROVAL REJECTION SOURCE and POST APPROVAL REJECTION USER columns will now display on Refund Viewer screen

  -->

<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@page import="qc.llmhome.dto.ActivityInfoDTO"%>
<%@page import="qc.lms.dto.MenuDTO"%>
<%@page import="qc.los.dto.ScreenModeDTO"%>
<%@page import="qc.lms.dto.ScreenStatusDTO"%>
<%@page import="qc.sso.dto.UserMenuDTO"%>
<%@ include file="../include/includeTld.inc"%>
<%
	//commented bcos not using
	//String path = request.getContextPath();
	//String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html:html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
	<title>miFIN</title>
	<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
	<link href="css/qcllm.css" rel="stylesheet" type="text/css">
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
	<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
	<META HTTP-EQUIV="Expires" CONTENT="0">
	<!--  ADDED ON 16-DEC-2008 -->
	<script type="text/javascript" src="js/checkSessionAvailability.js"></script>
	<script type="text/javascript">
		var prospectId=null;
		var selectedReciptIds ="";
		/* Code added by ankit gaur::28/02/2012 for OTC Recipet */
		function cashReceipt(INSTRUMENT_NAME,obj) /* changed for 1.0.0.2 */
		{	
		 	/* <logic:notEmpty name = "screenModes">
			    	
				    	if('Y'!= '<bean:write name = "screenModes" property="viewMode" />')
						{ 
						   window.open("cashReceipt.do?actionPerformed=displayCashReceipt"+"&rurl="+Math.random()+"&otcActId="+obj,"popState","titlebar=yes,scrollbars=yes,toolbar=no,maximize=yes,menubar=no,minimize=no,statusbar=no");						
						}
						
			</logic:notEmpty>	 */
			/* chang start of 1.0.0.2 */
			//window.open("cashReceipt.do?actionPerformed=displayCashReceipt"+"&rurl="+Math.random()+"&otcActId="+obj,"popState","titlebar=yes,scrollbars=yes,toolbar=no,maximize=yes,menubar=no,minimize=no,statusbar=no");
			window.open("cashReceipt.do?actionPerformed=displayCashReceipt"+"&INSTRUMENT_NAME="+INSTRUMENT_NAME+"&rurl="+Math.random()+"&otcActId="+obj,"popState","titlebar=yes,scrollbars=yes,toolbar=no,maximize=yes,menubar=no,minimize=no,statusbar=no");
			/* chang end of 1.0.0.2 */		 
		 		
		}
		/* Code end by ankit gaur::28/02/2012 for OTC Recipet */
		function onLoad()
		{
			setTime();
			var screenId; 
				<logic:notEmpty name="prospectListInfo">
		   		<logic:notEmpty name="prospectListInfo"  property="loanDetailProspectId">
   					prospectId='<bean:write name ="prospectListInfo" property="loanDetailProspectId"/>';
		   		</logic:notEmpty>
		   	</logic:notEmpty>
			<logic:notEmpty name="activityInfo">
		   		<logic:notEmpty name="activityInfo"  property="screenDTO">
		   			<bean:define id="screen" name="activityInfo" property="screenDTO"/>
		   				<logic:notEmpty name="screen"  property="menuId">
		   					screenId='<bean:write name ="screen" property="menuId"/>';
		   				</logic:notEmpty>
		   		</logic:notEmpty>
		   	</logic:notEmpty>
		   	
		  	 	
		  
		}
		function showDetail(screenId,activityId,url)
		{
			window.open(url+"&activityId="+activityId+"&rurl="+Math.random(),"remarks","width=1000, height=800,menubar=no");
		}
		
		function toggleReciptId(checkObj)
		{
			var reciptValue = checkObj.value;
			var flag = checkObj.checked;
			if(flag)
			{
				selectedReciptIds = selectedReciptIds + "," + reciptValue + ",";
			}
			else
			{
				selectedReciptIds = selectedReciptIds.replace("," + reciptValue + ",","");
			}
			
			var allCheckbox = document.getElementsByName("reciptCheckBox");
			for(var i = 0; i < allCheckbox.length; i++)
			{
				if(allCheckbox[i].value == reciptValue)
					allCheckbox[i].checked = flag;
			}
			//alert(selectedReciptIds);
		}
		
		function saveExit()
		{
			//Validate if any checkbox is checked
			//Validate if Auth Status Radio Button Is Checked 
			if (selectedReciptIds == "")
			{
				  alert(("Please select checkbox for canceling receipt").toUpperCase());
				  return ;
			}
			
			//Validate for blank Maker Remarks
			var remarks = document.forms[0].cancelRemarks.value;
			reWhiteSpace = new RegExp(/^\s+$/);
			if(remarks.length<1  || reWhiteSpace.test(remarks))
			{
				alert(("Remarks must be specified.").toUpperCase());
				document.forms[0].cancelRemarks.focus();
				return;
			}
			
			<logic:notEmpty name="receiptCancellation">
			<logic:equal name="receiptCancellation" value="A">
				var listRadioButton = document.forms[0].authStatus;
				var isItemChecked = false;
				for (var i=0; i<listRadioButton.length; i++)
				{
				  var listItem = listRadioButton[i];
				  if ( listItem.checked )
				  {
				   	isItemChecked = true;
				  }
				 }
				 
				if ( isItemChecked == false )
				{
					  alert(("Please select auth status").toUpperCase());
					  return ;
				}
			</logic:equal>
			</logic:notEmpty>
			
			document.forms[0].selectedRecipts.value = selectedReciptIds;
			<logic:notEmpty name="receiptCancellation">
				<logic:equal name="receiptCancellation" value="M">
					document.forms[0].action = "viewerAction.do?actionPerformed=cancelReciptMaker";
				</logic:equal>
				<logic:equal name="receiptCancellation" value="A">
					document.forms[0].action = "viewerAction.do?actionPerformed=cancelReciptAuthor";
				</logic:equal>
			</logic:notEmpty>
			document.forms[0].prospectId.value = '<bean:write name="prospectListDTO" property="loanDetailProspectId"/>';
		    document.forms[0].submit();
		  	return;
		}
		
		function printOption(obj)
		{
		    obj.disabled=true;
			var details = obj.id.split("~");
			var activityId=details[0];
			var amt=details[1].replace(/^\s+|\s+$/g, '');
			//Mayank Agrawal 03-11-2014 for print problem
			amt=amt.replace (/,/g, "");
			//end here
			var bankId=details[2];
			var insNumber=details[3];
			var instDate=details[4];
			var payTo='<bean:write name="StaticInfo" property="customerName"/>';
			var screenId='<%=(String)request.getAttribute("screenId")%>';
			
			window.open("losPrintCheque.do?payTo="+payTo+"&activityId="+activityId+"&bankId="+bankId+"&amt="+amt+"&insNumber="+insNumber+"&instDate="+instDate+"&screenId="+screenId,"popBank","width=728, height=1024,top=100,left=100, location=no, maximize=yes, menubar=no, status=no, toolbar=no, scrollbars=no, resizable=no","modal=yes" );	 
		
		}
		
	</script>
</head>
<body onload="onLoad()">

	<%@include file="../common/header.jsp"%>
	<%@include file="../common/subHeaderViewer.jsp"%>
	<div style="margin-top: 100px;">
	<jsp:include page="../common/staticinfo.jsp" flush="false" />
	</div>
	
<div   id="vetiTd" >
<%@ include file="../vmenu/menu.jsp" %>
</div>
<div  class="toggling">
	<%@ include file="../vmenu/vmenuTgl.jsp" %>
</div>
	
	<logic:notEmpty name="colActivities">

		<div width="100%" style="height: 425px; overflow: scroll">
			<html:form action="viewerAction.do"
				method="post">
				<html:hidden property="selectedRecipts"/>
				<html:hidden property="prospectId"/>
				<table border="0" width="100%" class="main_body">
					<tr class="list_header" id="viewerList">

						<%
							HashMap columnMap = (HashMap) request.getAttribute("keyMap");
							ArrayList columnList = (ArrayList) request
									.getAttribute("colSortedColumns");
							Iterator itrSortedColumn = columnList.iterator();
							while (itrSortedColumn.hasNext()) {
							
						%>
						
						<td align="left">
							&nbsp;<%=itrSortedColumn.next()%>
						</td>
						<%
							}
						%>
						<logic:notEmpty name="receiptCancellation">
							<td>
								Select Receipt
							</td>
						</logic:notEmpty>
					</tr>
					<%
						Collection colActivities = (ArrayList) request
								.getAttribute("colActivities");
						Iterator itrColActivities = colActivities.iterator();
						HashMap rowMap = null;
						String actId = "";
						String enable = "";
						String cssClass = "tr_list_Viewer";
						String screenId=(String)request.getAttribute("screenId");
						while (itrColActivities.hasNext()) {
							rowMap = (HashMap) itrColActivities.next();

							String reciptID = "";
							itrSortedColumn = columnList.iterator();
							int counter = 0;
							while (itrSortedColumn.hasNext()) {

								String colName = (String) itrSortedColumn.next();
								if (counter == 0) {
									if (actId.equalsIgnoreCase((String) rowMap
											.get(columnMap.get(colName)))) {
					%>
					<tr class=<%=cssClass%>>

						<%
							} else {
											actId = (String) rowMap.get(columnMap.get(colName));
											if ("tr_list_Viewer_Alter".equals(cssClass)) {
												cssClass = "tr_list_Viewer";
											} else {
												cssClass = "tr_list_Viewer_Alter";
											}
						%>
					
					<tr class=<%=cssClass%>>
						<%
							}
									}
						%>
						<!-- Code added by ankit gaur::28/02/2012 for OTC Recipet -->
						<logic:equal name="activityInfo" property="activityType" value="OTC">
						<td align="left" style="word-break: break-all;" nowrap>
							&nbsp;
							<%if((colName.equals("ACTIVITY ID")) && (rowMap.get(columnMap.get("STATUS")).equals("APPROVED")) && rowMap.get(columnMap.get("INSTRUMENT TYPE")).equals("OTC")) {%><!-- 1.0.0.3 -->
							<a href="#" onclick="cashReceipt('<%=rowMap.get(columnMap.get("INSTRUMENT NAME")) %>','<%=rowMap.get(columnMap.get(colName)) %>');">   <!--  changed for 1.0.0.2 --> 
							<%=rowMap.get(columnMap.get(colName)) != null ? rowMap.get(columnMap.get(colName)): ""%>
							</a>
							<% }else { %>
							<%=rowMap.get(columnMap.get(colName)) != null ? rowMap.get(columnMap.get(colName)) : ""%> <%}%> </td>
									</logic:equal>
									<logic:notEqual name="activityInfo" property="activityType" value="RECEIPT CANCELLATION">
									<logic:notEqual name="activityInfo" property="activityType" value="OTC">
									<logic:equal name="activityInfo" property="activityType" value="REFUND">
									<%
									
									if(!columnMap.get(colName).equals("BANKID~9")&&!columnMap.get(colName).equals("INSTRUMENT_NO~10")&&!columnMap.get(colName).equals("INSTRUMENT_DATE~11")&&!columnMap.get(colName).equals("PRINTED~12"))/* 1.0.0.1 */
									{%>
										<td align="left" style="word-break: break-all;" nowrap>
										&nbsp;<%=rowMap.get(columnMap.get(colName)) != null ? rowMap.get(columnMap.get(colName)): ""%></td><script>
									var row = document.getElementById("viewerList");
									//Below changes done by Ravikant
									if(row.cells[9]){/* 1.0.0.1 */
	    								row.deleteCell(9);/* 1.0.0.1 */
	// 									row.deleteCell(8);
	// 									row.deleteCell(8);
									}
									 </script>
									<%
									}
										if(columnMap.get(colName).equals("PRINTED~12"))
										{
											enable = (String)rowMap.get(columnMap.get(colName));
											System.out.println(enable);
										}
									
									 %>
									
									</logic:equal>
									<logic:notEqual name="activityInfo" property="activityType" value="REFUND">
									
										<td align="left" style="word-break: break-all;" nowrap>
										&nbsp;<%=rowMap.get(columnMap.get(colName)) != null ? rowMap
									.get(columnMap.get(colName))
									: ""%></td>
									
									</logic:notEqual>
									</logic:notEqual>
									</logic:notEqual>
									<!-- Code end by ankit gaur::28/02/2012 for OTC Recipet -->
									
									
							<logic:equal name="activityInfo" property="activityType" value="RECEIPT CANCELLATION">
							<td align="left" style="word-break: break-all;" nowrap>
								&nbsp;
								<%if ((colName.equals("ACTIVITY ID")) &&  (rowMap.get(columnMap.get("STATUS")).equals("CANCELLATION APPROVED"))) {%>
								<!-- <a href="#" onclick="cashReceipt('<%=rowMap.get(columnMap.get(colName)) %>');"> -->
								<%=rowMap.get(columnMap.get(colName)) != null ? rowMap.get(columnMap.get(colName)): ""%>
								<!-- </a> -->
								<% }else { %>
								<%=rowMap.get(columnMap.get(colName)) != null ? rowMap
										.get(columnMap.get(colName))
										: ""%> <%}%> </td>
									</logic:equal>
									
						<%
							if (counter == 0) {
										reciptID = (String) rowMap.get(columnMap.get(colName));
									}
									counter++;
								}
						%>
						<logic:notEmpty name="receiptCancellation">
							<td>
								<input type="checkbox" name="reciptCheckBox" value="<%=reciptID%>" onclick="toggleReciptId(this)">
							</td>
						</logic:notEmpty>
						<% if(screenId.equals("1000000043")){ %>
						<script>
							var row = document.getElementById("viewerList");
							newRow = row.insertCell(9);//1.0.0.1
						</script>
						
						
					 <%--   <script>
						 var a='<%=enable%>';
									  alert("enable ="+a+" STATUS ="+'<%=rowMap.get(columnMap.get("STATUS"))%>');   
					    </script> --%>
						
						<% if(enable.equals("N") && rowMap.get(columnMap.get("STATUS")).equals("APPROVED")){ %>
						<td><span style='float:left;width:50px;text-align:center;'><input style='width:60px' type='button' name='print' value='Print' id='<%=rowMap.get(columnMap.get("ACTIVITY ID"))+"~"+rowMap.get(columnMap.get("RECEIPT AMT"))+"~"+rowMap.get(columnMap.get("BANKID"))+"~"+rowMap.get(columnMap.get("INSTRUMENT_NO"))+"~"+rowMap.get(columnMap.get("INSTRUMENT_DATE"))%>'  maxlength='20' size='25' onclick='return printOption(this);'/></span></td>
						<%}
						else{%>
						<td><span style='float:left;width:50px;text-align:center;'><input style='width:60px' disabled="disabled" type='button' name='print' value='Print' id='<%=rowMap.get(columnMap.get("ACTIVITY ID"))+"~"+rowMap.get(columnMap.get("RECEIPT AMT"))+"~"+rowMap.get(columnMap.get("BANKID"))+"~"+rowMap.get(columnMap.get("INSTRUMENT_NO"))+"~"+rowMap.get(columnMap.get("INSTRUMENT_DATE"))%>'  maxlength='20' size='25' onclick='return printOption(this);'/></span></td>
						<%} }%>
					</tr>
					<%
						}
					%>
				</table>
				<br>
				<logic:notEmpty name="receiptCancellation">
					<table width="100%" class="main_body">
						<tr>
							<td colspan="2" width="100%">
								<fieldset>
									<legend>
										Remarks
									</legend>
									<table width="100%" border="0" class="main_body">
										<tr>
											<td width="1%">
												&nbsp;
											</td>
											<td width="10%">
												Remarks :
												<font color="red"> * </font>
											</td>
											<td width="42%">
												<html:textarea property="cancelRemarks" rows="3" cols="45"></html:textarea>
											</td>
											<td width="15%" align="right"></td>
											<td width="1%">
											</td>
											<td>
												<logic:equal name="receiptCancellation" value="A">
													Auth Status: 
													<html:radio property="authStatus" value="A" /> Accept
													<html:radio property="authStatus" value="R" /> Reject
												</logic:equal>
											</td>
										</tr>
									</table>
								</fieldset>
							</td>
						</tr>
					</table>
					</logic:notEmpty>
			</html:form>
		</div>
	</logic:notEmpty>
	<%@include file="../common/footer.jsp"%>
</body>
</html:html>

	<script src="vmenu/js/toggleMenu.js"></script>
<script src="js/bootstrap.min.js"></script>
<script src="js/slimscroll.js"></script>




