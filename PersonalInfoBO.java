/*  HEADER INFO
+  File NAME 	: PersonalInfoBO.java
+  PURPOSE		: 
+  CREATED BY	: 
+  CREATION DATE	: 
+  INITIAL VERSION : 1.0.0.0
+  **********************************************************************************************************************************
+  VERSION NO   UPDATED BY        UPDATED ON      		REASON FOR CHANGE
+  1.0.0.1	    APURVA		      06 -june-2018	 		 	changes made for lob changes
+  1.0.0.2        Ravi            31-july-2018          CR for add validation incase for changing in IGST per.
+  1.0.0.3        Ravinder        7-Jun-2019            One parameter added for RelationshipId.
+  1.0.0.4        Ravi      	  08 Nov  2019           code change to add kyc risk category
+  1.0.0.5		 Sunny 			  12-Aug-2020			Production Bug - 87708 constitution not getting updates
+  1.0.0.6		Priyanka Soni 	  10-Jun-2022	       CR- Storing Grace Period for Customer Asset Category Wise.
+  1.0.0.7		Hemant Kumar 	  22-DEC-2022	       KYC Compliance CR
+  1.0.0.8		Priyanka Soni 	  08-Apr-2023	  	   Dedupe of authorised signatory and beneficiary owner should be mandatory process
+  1.0.0.9      Narottam Biswal   29-DEC-2023          CR- LPI details to be added in applicant details screen
+  1.0.0.10     Nalin Kumar Jena  08-FEB-2024          CR-Udyam No. - Customer level attribute for non-individual.
+  1.0.0.11     Nalin Kumar Jena      22-FEB-2024      CR-Passport No varification
+  1.0.0.12     Narottam Biswal       22-FEB-2024      CR-DL varification
+  1.0.0.13     Tanisha Agarwal       22-FEB-2024      CR-Voter ID varification
+  1.0.0.14      Ravi Shankar       27-FEB-2024      CR-Udyam Registration varification
+  1.0.0.15	    Ravi Shankar	  27-MAR-2024	 	   CR- document upload/download on DMS
+  1.0.0.16	    Nalin Kumar Jena  23-APR-2024	 	   CR- Credit Ratings And parameters
   1.0.0.17		Priyanka Soni 	    08-Jul-2024	            Self option should be available in Contact Type field under Key contacts section.
#  1.0.0.18     Narottam Biswal     04-Feb-2025          CR-add Credit Days field and tds in GRACE PERIOD DETAILS section in customer module
#  1.0.0.19     Narottam Biswal         14-Nov-2023      CR - LEI Requirement
+  1.0.0.20     Narottam biswal     22-JAN-2025        CR-Experian Integration
+  1.0.0.21      Ravi Shankar          09-Apr-2026            GST Registration Status
+  1.0.0.22     Nalin Kumar Jena  22-May-2026          UCIC for guarantor, Authorised signatory and Beneficiary Owner and Keycontact
+  1.0.0.23     Ravi Shankar        14-AUG-2026        Lower TDS Rate Specification section
+  1.0.0.24     Sanchi Agarwal      02-Nov-2026        Added Terms & Conditions (PC_PAYMENT_TERMS) field under Payment Instruction
*/
package qc.los.model.bo;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;
import org.apache.struts.upload.FormFile;

import qc.common.dto.ReturnParamDTO;
import qc.common.util.PrintDBConnectionMap;
import qc.dal.DAL;
import qc.dal.dto.ParameterDTO;
import qc.dal.exception.DBConnectionException;
import qc.dal.util.DBConnection;
import qc.dal.util.PrintParameterList;
import qc.lms.util.Utils;
import qc.los.controller.form.PersonalInfoForm;
import qc.los.dto.CustomerListDTO;
import qc.los.dto.PersonalInfoDTO;
import oracle.sql.*; 

public class PersonalInfoBO
{
	protected static Logger log = Logger.getLogger(PersonalInfoBO.class);
	Locale locale = new Locale("en","US");
	ResourceBundle resource = ResourceBundle.getBundle("resourceProperties.losConstants", locale);
	public String isAlreadyExactMatch(int oldApplicantId,Map dbConnectionMap)
	{
		log.info("Start");	
		//log.debug("isAlreadyExactMatch - Parameter : \r\n                                                                            1. oldApplicantId = "+oldApplicantId+"; \r\n                                                                            2. dbConnectionMap = ("+PrintDBConnectionMap.printMap(dbConnectionMap)+"); \r\n");
		DAL dal=new DAL();
    	int parameterCount=0;
		ArrayList arrParameter=new ArrayList();
		String procName=null;
		ParameterDTO parameterDtoStatus = null;
		ParameterDTO parameterDtomessage = null;
		
		String message = null;
		String status = null;
		String messageAndStatus = null;
		  if(dbConnectionMap.get("DB_SERVER").toString().equals("ORACLE"))
		  {
			    procName="{call Pkg_Los_Dedupe.PR_APPLICANT_IS_EXACT_MATCH(?,?,?,?)}";
			 	arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.INTEGER,oldApplicantId));
			 	arrParameter.add(new ParameterDTO(++parameterCount,"OUT",oracle.jdbc.OracleTypes.VARCHAR,null));
				arrParameter.add(new ParameterDTO(++parameterCount,"OUT",oracle.jdbc.OracleTypes.VARCHAR,null));
				arrParameter.add(new ParameterDTO(++parameterCount,"OUT",oracle.jdbc.OracleTypes.VARCHAR,null));
		  }
		  else if(dbConnectionMap.get("DB_SERVER").toString().equals("MSSQLSERVER"))
		  {
			    procName="{call QC_LOS_FINAL.DBO.LosDedupe_PrApplicantIsExactMatch(?,?,?,?)}";
			 	arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.INTEGER,oldApplicantId));
			 	arrParameter.add(new ParameterDTO(++parameterCount,"OUTPUT",java.sql.Types.VARCHAR,null));
				arrParameter.add(new ParameterDTO(++parameterCount,"OUTPUT",java.sql.Types.VARCHAR,null));
				arrParameter.add(new ParameterDTO(++parameterCount,"OUTPUT",java.sql.Types.VARCHAR,null));
		  }
		  else if(dbConnectionMap.get("DB_SERVER").toString().equals("MYSQL"))
		  {
			  procName="{call QC_LOS_FINAL.DBO.LosDedupe_PrApplicantIsExactMatch(?,?,?,?)}";
			 	arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.INTEGER,oldApplicantId));
			 	arrParameter.add(new ParameterDTO(++parameterCount,"OUTPUT",java.sql.Types.VARCHAR,null));
				arrParameter.add(new ParameterDTO(++parameterCount,"OUTPUT",java.sql.Types.VARCHAR,null));
				arrParameter.add(new ParameterDTO(++parameterCount,"OUTPUT",java.sql.Types.VARCHAR,null));
		  }
		
		  try 
		  {
			 	dal.processSPROC(procName, arrParameter, dbConnectionMap);
				parameterDtoStatus = (ParameterDTO)arrParameter.get(1);
				parameterDtomessage = (ParameterDTO)arrParameter.get(2);
				 status = parameterDtoStatus.getParameterValue().toString();
				 message = parameterDtomessage.getParameterValue().toString();
				 messageAndStatus= status+"~"+message;
				
		  }
		  catch (DBConnectionException dce) 
		  {
			  log.error("DBConnectionException - FAIL:");
				log.error("DBConnectionException - Parameter : \r\n1. procName= " + procName + "; \r\n2. colParameter=( " + PrintParameterList.printParameterList(arrParameter) + " );");
		  	log.error("DBConnectionException - "+dce.getMessage());
		  }
		  catch (Exception e) 
		  {
			  log.error("Exception - FAIL:");
				log.error("Exception - Parameter : \r\n1. procName= " + procName + "; \r\n2. colParameter=( " + PrintParameterList.printParameterList(arrParameter) + " );");
		  	log.error("Exception - "+e.getMessage());
		  }
		  log.info("End");
		
		return messageAndStatus;
	}
	
	
	/**
	 * 
	 * @param ProspectId
	 * @param ApplicantType
	 * @param applicantId
	 * @param dbConnectionMap
	 * @return
	 */
	public Map GetPersonalInfo(int ProspectId,int ApplicantType,int applicantId,String entityId,Map dbConnectionMap) {
		log.info("Start");
		//log.debug("GetPersonalInfo - Parameter : \r\n                                                                            1. ProspectId = "+ProspectId+"; \r\n                                                                            2. ApplicantType = "+ApplicantType+"; \r\n                                                                            3. applicantId = "+applicantId+"; \r\n                                                                            4. dbConnectionMap = ("+ PrintDBConnectionMap.printMap(dbConnectionMap) + "); \r\n");
		DAL dal=new DAL();
    	int parameterCount=0;
		ArrayList arrParameter=new ArrayList();
		String procName=null;
		ParameterDTO parameterDto= null;
		ParameterDTO parameterDto2= null;
		ParameterDTO parameterDto3= null;//1.0.0.7
		ParameterDTO parameterDto5=null;//1.0.0.19
		ParameterDTO parameterDto6=null;//
		ParameterDTO parameterDto4= null;//1.0.0.17
		PersonalInfoDTO personalInfoDTO = new PersonalInfoDTO();
		PersonalInfoDTO personalInfoKeyContactDTO= null;
		PersonalInfoDTO personalInfoBeneficiaryOwnerDTO = null;//1.0.0.7
		PersonalInfoDTO personalInfoKeyContactSelfDTO = null;//1.0.0.17
		//ArrayList keyContactData=new ArrayList();
		Map map = new HashMap();
		
		
		
		String message = null;
		String status = null;
		String messageAndStatus = null;
		  if(dbConnectionMap.get("DB_SERVER").toString().equals("ORACLE"))
		  {
			    procName="{call Pkg_Los_Trn_Applicant_Master.GETSINGLEAPPLICANTMASTER(?,?,?,?,?,?,?,?,?,?,?)}"; //1.0.0.17//1.0.0.23
			 	arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,""+ProspectId));
			 	arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,""+ApplicantType));
				arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,""+applicantId));
				arrParameter.add(new ParameterDTO(++parameterCount,"OUT",oracle.jdbc.OracleTypes.CURSOR,null));
				arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,""+entityId));
				arrParameter.add(new ParameterDTO(++parameterCount,"OUT",oracle.jdbc.OracleTypes.VARCHAR,null));
				arrParameter.add(new ParameterDTO(++parameterCount,"OUT",oracle.jdbc.OracleTypes.CURSOR,null));
				arrParameter.add(new ParameterDTO(++parameterCount,"OUT",oracle.jdbc.OracleTypes.CURSOR,null));//1.0.0.7
				arrParameter.add(new ParameterDTO(++parameterCount,"OUT",oracle.jdbc.OracleTypes.CURSOR,null));//1.0.0.17
				arrParameter.add(new ParameterDTO(++parameterCount,"OUT",oracle.jdbc.OracleTypes.CURSOR,null));//1.0.0.19 Lei 
				arrParameter.add(new ParameterDTO(++parameterCount,"OUT",oracle.jdbc.OracleTypes.CURSOR,null));//1.0.0.23
		 }
		  else if(dbConnectionMap.get("DB_SERVER").toString().equals("MSSQLSERVER"))
		  {
			    procName="{call QC_LOS_FINAL.DBO.LosTrnApplicantMaster_GetSingleApplicantMaster(?,?,?,?)}";
			 	arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,""+ProspectId));
			 	arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,""+ApplicantType));
				arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,""+applicantId));
				arrParameter.add(new ParameterDTO(++parameterCount,"RESULTSET",java.sql.Types.VARCHAR,"4"));
		  }
		  else if(dbConnectionMap.get("DB_SERVER").toString().equals("MYSQL"))
		  {
			  	procName="{call QC_LOS_FINAL.DBO.LosTrnApplicantMaster_GetSingleApplicantMaster(?,?,?,?)}";
			 	arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.INTEGER,ProspectId));
			 	arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.INTEGER,ApplicantType));
				arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.INTEGER,applicantId));
				arrParameter.add(new ParameterDTO(++parameterCount,"RESULTSET",java.sql.Types.VARCHAR,"4"));
		  }
		  try
		  {
			  dal.processSPROC(procName, arrParameter,dbConnectionMap);
			  parameterDto = (ParameterDTO)arrParameter.get(3);
			  parameterDto2 = (ParameterDTO)arrParameter.get(6);
			  parameterDto3 = (ParameterDTO)arrParameter.get(7);
			  parameterDto4 = (ParameterDTO)arrParameter.get(8);
			  parameterDto5= (ParameterDTO)arrParameter.get(9); //1.0.0.19
			  parameterDto6= (ParameterDTO)arrParameter.get(10); //1.0.0.23
			ArrayList list=(ArrayList)parameterDto.getParameterValue();
			
			if(list != null && list.size()>0){
			Iterator itr = list.iterator();
			
			Map resultSetMap = null;
			
			if(itr!=null)
			{
				while(itr.hasNext())
					
					
					
				{										
					resultSetMap=(Map)itr.next();
					
					if(resultSetMap.get("PERSONAL_COMPLETED") != null)
					{
						personalInfoDTO.setPersonalCompleted(resultSetMap.get("PERSONAL_COMPLETED").toString());					
					}	
					
					///// Apurva Changes ///////
					
					if(resultSetMap.get("CASE_ID") != null)
					{
						personalInfoDTO.setCaseId(resultSetMap.get("CASE_ID").toString());					
					}
					
					//////////////////////////
					if(resultSetMap.get("APPLICANTID") != null)
					{
						personalInfoDTO.setApplicantID(resultSetMap.get("APPLICANTID").toString());						
					}
					if(resultSetMap.get("APPLICANTCODE") != null)
					{
						personalInfoDTO.setApplicantCode(resultSetMap.get("APPLICANTCODE").toString());
					}
					if(resultSetMap.get("APPLICANTTYPE") != null)
					{
						personalInfoDTO.setApplicantType(resultSetMap.get("APPLICANTTYPE").toString());
					}
					if(resultSetMap.get("TITLE") != null)
					{
						personalInfoDTO.setTitle(resultSetMap.get("TITLE").toString());					
					}
					if(resultSetMap.get("FNAME") != null)
					{
						personalInfoDTO.setFirstName(resultSetMap.get("FNAME").toString());						
					}
					if(resultSetMap.get("MNAME") != null)
					{
						personalInfoDTO.setMiddleName(resultSetMap.get("MNAME").toString());						
					}
					if(resultSetMap.get("LNAME") != null)
					{
						personalInfoDTO.setLastName(resultSetMap.get("LNAME").toString());						
					}
					if(resultSetMap.get("RELIGION") != null)
					{
						personalInfoDTO.setReligion(resultSetMap.get("RELIGION").toString());						
					}
					if(resultSetMap.get("MARTIALSTATUS") != null)
					{
						personalInfoDTO.setMaritalStatus(resultSetMap.get("MARTIALSTATUS").toString());
					}
					if(resultSetMap.get("DOB") != null)
					{
						personalInfoDTO.setDateOfBirth(resultSetMap.get("DOB").toString());						
					}
					if(resultSetMap.get("GENDER") != null)
					{
						personalInfoDTO.setGender(resultSetMap.get("GENDER").toString());						
					}
					if(resultSetMap.get("NOOFDEPENDENTS") != null)
					{
						personalInfoDTO.setNoOfDependents(resultSetMap.get("NOOFDEPENDENTS").toString());						
					}
					if(resultSetMap.get("FATHERTITLE") != null)
					{
						personalInfoDTO.setFatherTitle(resultSetMap.get("FATHERTITLE").toString());						
					}
					if(resultSetMap.get("FATHERFNAME") != null)
					{
						personalInfoDTO.setFatherFirstName(resultSetMap.get("FATHERFNAME").toString());						
					}
					if(resultSetMap.get("FATHERMNAME") != null)
					{
						personalInfoDTO.setFatherMiddleName(resultSetMap.get("FATHERMNAME").toString());						
					}
					if(resultSetMap.get("FATHERLNAME") != null)
					{
						personalInfoDTO.setFatherLastName(resultSetMap.get("FATHERLNAME").toString());						
					}
					if(resultSetMap.get("MOTHERFNAME") != null)
					{
						personalInfoDTO.setMotherFirstName(resultSetMap.get("MOTHERFNAME").toString());
						
					}
					if(resultSetMap.get("MOTHERMNAME") != null)
					{
						personalInfoDTO.setMotherMiddleName(resultSetMap.get("MOTHERMNAME").toString());
					}
					if(resultSetMap.get("MOTHERLNAME") != null)
					{
						personalInfoDTO.setMotherLastName(resultSetMap.get("MOTHERLNAME").toString());
					}
					if(resultSetMap.get("SPOUSEFNAME") != null)
					{
						personalInfoDTO.setSpouseFirstName(resultSetMap.get("SPOUSEFNAME").toString());
						
					}
					if(resultSetMap.get("SPOUSEMNAME") != null)
					{
						personalInfoDTO.setSpouseMiddleName(resultSetMap.get("SPOUSEMNAME").toString());
						
					}
					if(resultSetMap.get("SPOUSELNAME") != null)
					{
						personalInfoDTO.setSpouseLastName(resultSetMap.get("SPOUSELNAME").toString());
						
					}
					if(resultSetMap.get("SPOUSEOCCUPATION") != null)
					{
						personalInfoDTO.setSpouseOccupation(resultSetMap.get("SPOUSEOCCUPATION").toString());
						
					}
					if(resultSetMap.get("SPOUSENAMEOFCOMPANY") != null)
					{
						personalInfoDTO.setSpouseNameOfCompany(resultSetMap.get("SPOUSENAMEOFCOMPANY").toString());
						
					}
					if(resultSetMap.get("CONSTITUTION") != null)
					{
						personalInfoDTO.setConstitution(resultSetMap.get("CONSTITUTION").toString());
						
					}
					if(resultSetMap.get("CUSTCATEGORY") != null)
					{
						personalInfoDTO.setCustCategory(resultSetMap.get("CUSTCATEGORY").toString());
						
					}
					if(resultSetMap.get("EDUQUALIFICATION") != null)
					{
						personalInfoDTO.setEduQualification(resultSetMap.get("EDUQUALIFICATION").toString());
						
					}
					if(resultSetMap.get("HIGHESTQUALIFICATION") != null)
					{
						personalInfoDTO.setHighestQualification(resultSetMap.get("HIGHESTQUALIFICATION").toString());
						
					}
					if(resultSetMap.get("DEGREE") != null)
					{
						personalInfoDTO.setDegree(resultSetMap.get("DEGREE").toString());
						
					}
					if(resultSetMap.get("COLLEGELASTATTENDED") != null)
					{
						personalInfoDTO.setCollegeLastAttended(resultSetMap.get("COLLEGELASTATTENDED").toString());
						
					}
					if(resultSetMap.get("NATIONALITY") != null)
					{
						personalInfoDTO.setNationality(resultSetMap.get("NATIONALITY").toString());
						
					}
					if(resultSetMap.get("NLFNAME") != null)
					{
						personalInfoDTO.setNlFirstName(resultSetMap.get("NLFNAME").toString());
						
					}
					if(resultSetMap.get("NLMNAME") != null)
					{
						personalInfoDTO.setNlSecondName(resultSetMap.get("NLMNAME").toString());
						
					}
					if(resultSetMap.get("NLLNAME") != null)
					{
						personalInfoDTO.setNlLastName(resultSetMap.get("NLLNAME").toString());
						
					}
					if(resultSetMap.get("VOTERSID") != null)
					{
						personalInfoDTO.setVoterId(resultSetMap.get("VOTERSID").toString());
						
					}
					if(resultSetMap.get("ISPANNO") != null)
					{
						personalInfoDTO.setIsPanNo(resultSetMap.get("ISPANNO").toString());
						
					}
					if(resultSetMap.get("PANNO") != null)
					{
						personalInfoDTO.setPanNo(resultSetMap.get("PANNO").toString());
						
					}
					if(resultSetMap.get("DECLARATION") != null)
					{
						personalInfoDTO.setDeclaration(resultSetMap.get("DECLARATION").toString());
						
					}
					if(resultSetMap.get("DRIVINGLICENSENO") != null)
					{
						personalInfoDTO.setDrivingLicenseNo(resultSetMap.get("DRIVINGLICENSENO").toString());
						
					}
					if(resultSetMap.get("PASSPORTNO") != null)
					{
						personalInfoDTO.setPassportNo(resultSetMap.get("PASSPORTNO").toString());
					}
					if(resultSetMap.get("BASIC") != null)
					{
						personalInfoDTO.setBasic(resultSetMap.get("BASIC").toString());						
					}
					if(resultSetMap.get("DA") != null)
					{
						personalInfoDTO.setDa(resultSetMap.get("DA").toString());
					}
					if(resultSetMap.get("HRA") != null)
					{
						personalInfoDTO.setHra(resultSetMap.get("HRA").toString());
					}
					if(resultSetMap.get("SPALLOWANCES") != null)
					{
						personalInfoDTO.setSpAllowance(resultSetMap.get("SPALLOWANCES").toString());					
					}
					if(resultSetMap.get("MEDICAL_LTA") != null)
					{
						personalInfoDTO.setMedicalLta(resultSetMap.get("MEDICAL_LTA").toString());
						
					}
					if(resultSetMap.get("BONUS_INCENTIVE") != null)
					{
						personalInfoDTO.setBonusIncentive(resultSetMap.get("BONUS_INCENTIVE").toString());
					}
					if(resultSetMap.get("OTHERS") != null)
					{
						personalInfoDTO.setOthers(resultSetMap.get("OTHERS").toString());						
					}
					if(resultSetMap.get("GROSSINCOME") != null)
					{
						personalInfoDTO.setGrossIncome(resultSetMap.get("GROSSINCOME").toString());		
						
					}
					if(resultSetMap.get("OTHERINCOME") != null)
					{
						personalInfoDTO.setOtherIncome(resultSetMap.get("OTHERINCOME").toString());		
						
					}
					if(resultSetMap.get("NETINCOME") != null)
					{
						personalInfoDTO.setNetIncome(resultSetMap.get("NETINCOME").toString());		
						
					}
					if(resultSetMap.get("DNSREQUIRED") != null)
					{
						personalInfoDTO.setDnsRequired(resultSetMap.get("DNSREQUIRED").toString());
						
					}
					if(resultSetMap.get("DNSREASON") != null)
					{
						personalInfoDTO.setDnsReason(resultSetMap.get("DNSREASON").toString());
						
					}
					if(resultSetMap.get("PREFERREDTIMETOCALL") != null)
					{
						personalInfoDTO.setPreferredTimeToCall(resultSetMap.get("PREFERREDTIMETOCALL").toString());
						
					}
					if(resultSetMap.get("PREFERREDMODEOFCOMM") != null)
					{
						personalInfoDTO.setPreferredModeOfCommunication(resultSetMap.get("PREFERREDMODEOFCOMM").toString());
						
					}
					if(resultSetMap.get("RELATION") != null)
					{
						personalInfoDTO.setRelation(resultSetMap.get("RELATION").toString());
						
					}
					if(resultSetMap.get("CUSTOMER_ENTITYTYPE") != null)
					{
						personalInfoDTO.setCustEntityType(resultSetMap.get("CUSTOMER_ENTITYTYPE").toString());
						
					}	
						// code added by ankit gaur :: 14/12/2011 :: photo validation
					if((resultSetMap.get("FOID") != null) && (resultSetMap.get("PHOTOOBJ").equals("IMAGE") || resultSetMap.get("PHOTOOBJ")=="IMAGE"))
					{
						personalInfoDTO.setFileObjectId(resultSetMap.get("FOID").toString());
						
					}
					// Added for Dia Vikas
					
					if(resultSetMap.get("RBI_REGISTRATION_NO") != null)
					{
						personalInfoDTO.setRbiRegNo(resultSetMap.get("RBI_REGISTRATION_NO").toString());
						
					}
					
					if(resultSetMap.get("LEGAL_FORM_NAME") != null)
					{
						personalInfoDTO.setLegalFormName(resultSetMap.get("LEGAL_FORM_NAME").toString());
					}
					
					if(resultSetMap.get("KEY_CONTACT_PERSON") != null)
					{
						personalInfoDTO.setKeyContactPerson(resultSetMap.get("KEY_CONTACT_PERSON").toString());
						
					}
					
					if(resultSetMap.get("AUTHORIZED_CAPITAL") != null)
					{
						personalInfoDTO.setAuthCapital(resultSetMap.get("AUTHORIZED_CAPITAL").toString());
						
					}
					
					if(resultSetMap.get("ISSUED_CAPITAL") != null)
					{
						personalInfoDTO.setIssuedCap(resultSetMap.get("ISSUED_CAPITAL").toString());
						
					}
					
					if(resultSetMap.get("DIAS_BOARD_REPRESENTATIVE") != null)
					{
						personalInfoDTO.setBoardRepresentative(resultSetMap.get("DIAS_BOARD_REPRESENTATIVE").toString());
						
					}
					//Start 1.0.0.7
						/*
						 * if(resultSetMap.get("LEGAL_FORM_TYPE") != null) {
						 * personalInfoDTO.setLegalFormType(resultSetMap.get("LEGAL_FORM_TYPE").toString
						 * ());
						 * 
						 * if(personalInfoDTO.getCustEntityType().equals("1000000001")) {
						 * personalInfoDTO.setLegalFormTypeInd(resultSetMap.get("LEGAL_FORM_TYPE").
						 * toString()); } else
						 * if(personalInfoDTO.getCustEntityType().equals("1000000002")) {
						 * personalInfoDTO.setLegalFormTypeCorp(resultSetMap.get("LEGAL_FORM_TYPE").
						 * toString()); }
						 * 
						 * }
						 */
					if(resultSetMap.get("CONSTITUTION") != null)
					{
						personalInfoDTO.setLegalFormType(resultSetMap.get("CONSTITUTION").toString());
						
						if(personalInfoDTO.getCustEntityType().equals("1000000001"))
						{						
							personalInfoDTO.setLegalFormTypeInd(resultSetMap.get("CONSTITUTION").toString());
						}
						else if(personalInfoDTO.getCustEntityType().equals("1000000002"))
						{						
							personalInfoDTO.setLegalFormTypeCorp(resultSetMap.get("CONSTITUTION").toString());
						}
						
					}
					//End 1.0.0.7
					//comment start by 1.0.0.5
				//// Apurva changes on 20th dec 2017 //
						/*
						 * if(resultSetMap.get("CONSTITUTION") != null) {
						 * personalInfoDTO.setLegalFormType(resultSetMap.get("CONSTITUTION").toString())
						 * ;
						 * 
						 * }
						 */
									//// Apurva changes on 20th dec 2017     //
					//comment end by 1.0.0.5
					if(resultSetMap.get("SALES_MANAGER") != null)
					{
						personalInfoDTO.setSalesManager(resultSetMap.get("SALES_MANAGER").toString());
						
					}
					if(resultSetMap.get("SALES_MANAGER_ID") != null)
					{
						personalInfoDTO.setSalesManagerId(resultSetMap.get("SALES_MANAGER_ID").toString());
						
					}					
					// Added for Dia Vikas
                                 // code added by ankit gaur :: 14/12/2011 :: photo validation
					if((resultSetMap.get("FOID") != null) && (resultSetMap.get("PHOTOOBJ").equals("IMAGE") || resultSetMap.get("PHOTOOBJ")=="IMAGE"))
					{
						personalInfoDTO.setFileObjectId(resultSetMap.get("FOID").toString());
						
					}
					if((resultSetMap.get("TINNO") != null) )
					{
						personalInfoDTO.setTin(resultSetMap.get("TINNO").toString());
						
					}
					//Added By Rinky Arora
					if((resultSetMap.get("IS_EXISTING") != null) )
					{
						personalInfoDTO.setFlag(resultSetMap.get("IS_EXISTING").toString());
						
					}
					if((resultSetMap.get("TANNO") != null) )
					{
						personalInfoDTO.setTanNo(resultSetMap.get("TANNO").toString());
						
					}
					
					if(resultSetMap.get("PSL_TYPE") != null)
					{
						personalInfoDTO.setPsl(resultSetMap.get("PSL_TYPE").toString());
						
					}
					if(resultSetMap.get("CASTE_CATEGORY") != null)
					{
						personalInfoDTO.setCaste(resultSetMap.get("CASTE_CATEGORY").toString());
						
					}
					
					if(resultSetMap.get("ADHAAR_CARD_NO") != null)
					{
						personalInfoDTO.setAdharNo(resultSetMap.get("ADHAAR_CARD_NO").toString());
					}
					// ravi start
					
					if(resultSetMap.get("CUSTOMER_SEGMENT") != null)
					{
						personalInfoDTO.setCustomerSegment(resultSetMap.get("CUSTOMER_SEGMENT").toString());
					}
					/*if(resultSetMap.get("ADHAAR_CARD_NO") != null)
					{
						personalInfoDTO.setNatureActivity(resultSetMap.get("ADHAAR_CARD_NO").toString());
					}
					if(resultSetMap.get("ADHAAR_CARD_NO") != null)
					{
						personalInfoDTO.setOccupation(resultSetMap.get("ADHAAR_CARD_NO").toString());
					}*/
					// ravi end
					//, 

					if(resultSetMap.get("GROUP_NAME") != null)
					{
						personalInfoDTO.setGroupName(resultSetMap.get("GROUP_NAME").toString());
					}

					if(resultSetMap.get("GROUP_CODE") != null)
					{
						personalInfoDTO.setGroupCode(resultSetMap.get("GROUP_CODE").toString());
					}
					//1.0.0.4 start
					if(resultSetMap.get("KYC_RISK_CATEGORY") != null)
					{
						personalInfoDTO.setKycRiskCategory(resultSetMap.get("KYC_RISK_CATEGORY").toString());
					}
					//1.0.0.4 end
					//////////// Apurva Changes ////////
					
					//1.0.0.21
					if(resultSetMap.get("GST_REGISTERED") != null)
					{
						personalInfoDTO.setGstRegistrationStatus(resultSetMap.get("GST_REGISTERED").toString());
					}
					//1.0.0.21
					//1.0.0.24
					if(resultSetMap.get("PAYMENT_TERMS") != null)
					{
						personalInfoDTO.setPaymentTerms(resultSetMap.get("PAYMENT_TERMS").toString());
					}
					//1.0.0.24
					
					if(resultSetMap.get("LOB") != null)
					{
						personalInfoDTO.setLob(resultSetMap.get("LOB").toString());
					}
					
					if(resultSetMap.get("BRANCH") != null)
					{
						personalInfoDTO.setBranch(resultSetMap.get("BRANCH").toString());
					}
					if(resultSetMap.get("SALES_MANAGER_ID") != null)
					{
						personalInfoDTO.setRelationShipManager(resultSetMap.get("SALES_MANAGER_ID").toString());
					}
					if(resultSetMap.get("PRODUCT_ID") != null)
					{
						personalInfoDTO.setProductInstrestedIn(resultSetMap.get("PRODUCT_ID").toString());
					}
					if(resultSetMap.get("ASSET_CATEGORY_ID") != null)
					{
						personalInfoDTO.setAssetCategory(resultSetMap.get("ASSET_CATEGORY_ID").toString());
					}
					if(resultSetMap.get("COMPETITION_ID") != null)
					{
						personalInfoDTO.setCompetition(resultSetMap.get("COMPETITION_ID").toString());
					}
					if(resultSetMap.get("SOURCE_TYPE_ID") != null)
					{
						personalInfoDTO.setChannelType(resultSetMap.get("SOURCE_TYPE_ID").toString());
					}
					if(resultSetMap.get("SOURCE_ID") != null)
					{
						personalInfoDTO.setChannelName(resultSetMap.get("SOURCE_ID").toString());
					}
					if(resultSetMap.get("AGENT_ID") != null)
					{
						personalInfoDTO.setAgentName(resultSetMap.get("AGENT_ID").toString());
					}
					
					
					if(resultSetMap.get("FATHERFNAME") != null)
					{
						personalInfoDTO.setAuthSignatoryFirstName(resultSetMap.get("FATHERFNAME").toString());						
					}
					if(resultSetMap.get("FATHERMNAME") != null)
					{
						personalInfoDTO.setAuthSignatoryMiddleName(resultSetMap.get("FATHERMNAME").toString());						
					}
					if(resultSetMap.get("FATHERLNAME") != null)
					{
						personalInfoDTO.setAuthSignatoryLastName(resultSetMap.get("FATHERLNAME").toString());						
					}
					
					// changes for validation start ravi 1.0.0.2
					
					if(resultSetMap.get("QUOTATION_IGST_PER") != null)
					{
						personalInfoDTO.setQuotation_igstPer(resultSetMap.get("QUOTATION_IGST_PER").toString());						
					}
					if(resultSetMap.get("QUOTATION_SGST_UTGST_PER") != null)
					{
						personalInfoDTO.setQuotation_sgst_ugstPer(resultSetMap.get("QUOTATION_SGST_UTGST_PER").toString());						
					}
					if(resultSetMap.get("QUOTATION_CGST_PER") != null)
					{
						personalInfoDTO.setQuotation_cgstPer(resultSetMap.get("QUOTATION_CGST_PER").toString());						
					}
					if(resultSetMap.get("QUOTATION_REGISTRATION_STATE") != null)
					{
						personalInfoDTO.setQuotation_registrationState(resultSetMap.get("QUOTATION_REGISTRATION_STATE").toString());						
					}
					if(resultSetMap.get("QUOTATION_SEZ_ZONE_FLAG") != null)
					{
						personalInfoDTO.setQuotation_sez_zone(resultSetMap.get("QUOTATION_SEZ_ZONE_FLAG").toString());						
					}
					if(resultSetMap.get("COMPANY_ID") != null)
					{
						personalInfoDTO.setCustomerForCompany(resultSetMap.get("COMPANY_ID").toString());						
					}
					
					if(resultSetMap.get("WEBSITE") != null) { //heamnt
						personalInfoDTO.setWebsite(resultSetMap.get("WEBSITE").toString());						
					}
					if(resultSetMap.get("INDUSTRY_ID") != null) { //heamnt
						personalInfoDTO.setIndustryType(resultSetMap.get("INDUSTRY_ID").toString());						
					}
					if(resultSetMap.get("INDUSTRY_ID") != null) { //heamnt
						personalInfoDTO.setIndustryType(resultSetMap.get("INDUSTRY_ID").toString());						
					}
					if(resultSetMap.get("SECTOR_ID") != null) { //heamnt
						personalInfoDTO.setSector(resultSetMap.get("SECTOR_ID").toString());						
					}
					if(resultSetMap.get("AADHAAR_OKYC_COMPLETED") != null) { //heamnt
						personalInfoDTO.setAdharOkycCompFlag(resultSetMap.get("AADHAAR_OKYC_COMPLETED").toString());						
					}
					///////////////////////////////////  1.0.0.2 end
					//start by 1.0.0.9
					if(resultSetMap.get("LPI_RATE_TYPE") != null) { 
						personalInfoDTO.setLpiRateType(resultSetMap.get("LPI_RATE_TYPE").toString());						
					}
					if(resultSetMap.get("LPI_RATE") != null) { 
						personalInfoDTO.setLpiRate(resultSetMap.get("LPI_RATE").toString());						
					}
					//end by 1.0.0.9
					//1.0.0.18 start 
					
					if(resultSetMap.get("TDS_APPLICABLE") != null) { 
						personalInfoDTO.setTdsApplicableCheck(resultSetMap.get("TDS_APPLICABLE").toString());						
					}
					//1.0.0.18 end
					//Added by Nalin 1.0.0.10 start
					if(resultSetMap.get("UDYAM_NO") != null) { 
						personalInfoDTO.setUdyamNo(resultSetMap.get("UDYAM_NO").toString());						
					}
					//1.0.0.10 End
					
					//start 1.0.0.11
					if(resultSetMap.get("PASSPORT_NO_VERIFIED") != null) { 
						personalInfoDTO.setPassportNoVerifiedFlag(resultSetMap.get("PASSPORT_NO_VERIFIED").toString());						
					}
					//1.0.0.11 End
					//start 1.0.0.12
					if(resultSetMap.get("DL_NO_VERIFIED") != null) { 
						personalInfoDTO.setDlVarifyFlag(resultSetMap.get("DL_NO_VERIFIED").toString());						
					}
					//end 1.0.0.12
					
					//start 1.0.0.13
					if(resultSetMap.get("VOTER_ID_VERIFIED") != null) { 
						personalInfoDTO.setVoterIdFlg(resultSetMap.get("VOTER_ID_VERIFIED").toString());						
					}
					//end 1.0.0.13
					//1.0.0.14 Start
					if(resultSetMap.get("UDYAM_NO_VERIFIED") != null) { 
						personalInfoDTO.setUdyamIdFlg(resultSetMap.get("UDYAM_NO_VERIFIED").toString());						
					}//1.0.0.16
					if(resultSetMap.get("QUOTATION_CODE") != null) { 
						personalInfoDTO.setQuotationCode(resultSetMap.get("QUOTATION_CODE").toString());
					}//1.0.0.16
					// 1.0.0.16 Start
					if(resultSetMap.get("NATURE_OF_BUSINESS") != null) { 
						personalInfoDTO.setNatureOfBusiness(resultSetMap.get("NATURE_OF_BUSINESS").toString());
					}// 1.0.0.16 End
					if(resultSetMap.get("CUSTOMER_APPROVED") != null) { 
						personalInfoDTO.setCustomerApproved(resultSetMap.get("CUSTOMER_APPROVED").toString());
					}


					
				}
			}
			}
			//personalInfoDTO.setGroupName("ABC");
			//personalInfoDTO.setGroupCode("ABC123");
			parameterDto = (ParameterDTO)arrParameter.get(5);
			String flag = (String)parameterDto.getParameterValue();
			personalInfoDTO.setEditFlag(flag); 
			
			ArrayList keyContactDatalist=(ArrayList)parameterDto2.getParameterValue();
			
			
			map.put("personalInfoDTO", personalInfoDTO);
			if(keyContactDatalist!=null)
			{
			Map resultSetMap2 = null;
			Iterator itr2 = keyContactDatalist.iterator();
			ArrayList keyContactList = new ArrayList();
			if(itr2!=null)
			{
				while(itr2.hasNext())
					
				{										
					resultSetMap2=(Map)itr2.next();
					personalInfoKeyContactDTO= new PersonalInfoDTO();
					
					if(resultSetMap2.get("KEY_CONTACT_ID") != null)
					{
						personalInfoKeyContactDTO.setKeyContactsId(resultSetMap2.get("KEY_CONTACT_ID").toString());					
					}	
					if(resultSetMap2.get("CASE_ID") != null)
					{
						personalInfoKeyContactDTO.setCaseId(resultSetMap2.get("CASE_ID").toString());					
					}
					
					if(resultSetMap2.get("CASE_CODE") != null)
					{
						personalInfoKeyContactDTO.setCaseCode(resultSetMap2.get("CASE_CODE").toString());					
					}
					
					if(resultSetMap2.get("LOB_ID") != null)
					{
						personalInfoKeyContactDTO.setKeyContactlob(resultSetMap2.get("LOB_ID").toString());					
					}	
					if(resultSetMap2.get("CASE_CONTACT_TYPE_ID") != null)
					{
						personalInfoKeyContactDTO.setKeyContactcontactType(resultSetMap2.get("CASE_CONTACT_TYPE_ID").toString());					
					}	
					if(resultSetMap2.get("NAME") != null)
					{
						personalInfoKeyContactDTO.setKeyContactname(resultSetMap2.get("NAME").toString());					
					}	
					if(resultSetMap2.get("FIRM_NAME") != null)
					{
						personalInfoKeyContactDTO.setKeyContactfirmName(resultSetMap2.get("FIRM_NAME").toString());					
					}	
					if(resultSetMap2.get("MOBILE") != null)
					{
						personalInfoKeyContactDTO.setKeyContactmobile(resultSetMap2.get("MOBILE").toString());				
					}	
					if(resultSetMap2.get("EMAIL") != null)
					{
						personalInfoKeyContactDTO.setKeyContactEmailId(resultSetMap2.get("EMAIL").toString());					
					}	
					if(resultSetMap2.get("ADDRESS") != null)
					{
						personalInfoKeyContactDTO.setKeyContactAddress(resultSetMap2.get("ADDRESS").toString());					
					}	
					// Start 1.0.0.22
					if(resultSetMap2.get("UCIC_CODE") != null)
					{
						personalInfoKeyContactDTO.setKeyContactUcicCode(resultSetMap2.get("UCIC_CODE").toString());
					}
					if(resultSetMap2.get("PANNO") != null)
					{
						personalInfoKeyContactDTO.setKeyContactPan(resultSetMap2.get("PANNO").toString());
					}
					// End 1.0.0.22
					
					keyContactList.add(personalInfoKeyContactDTO);
				}
				
			}
			
			map.put("keyContactDeatail", keyContactList);
			
			}
			
			
			//Start 1.0.0.7
			ArrayList beneficiaryOwnerDatalist=(ArrayList)parameterDto3.getParameterValue();
			if(beneficiaryOwnerDatalist!=null)
			{
			Map resultSetMap3 = null;
			Iterator itr3 = beneficiaryOwnerDatalist.iterator();
			ArrayList beneficiaryOwnerList = new ArrayList();
				if(itr3!=null)
				{
					while(itr3.hasNext())
						
					{										
						resultSetMap3=(Map)itr3.next();
						personalInfoBeneficiaryOwnerDTO= new PersonalInfoDTO();
						
						if(resultSetMap3.get("APP_BENEFICIARY_ID") != null)
						{
							personalInfoBeneficiaryOwnerDTO.setBeneficiaryOwnerId(resultSetMap3.get("APP_BENEFICIARY_ID").toString());					
						}	
						if(resultSetMap3.get("FNAME") != null)
						{
							personalInfoBeneficiaryOwnerDTO.setBeneficiaryOwnerName(resultSetMap3.get("FNAME").toString());					
						}
						
						if(resultSetMap3.get("PANNO") != null)
						{
							personalInfoBeneficiaryOwnerDTO.setBeneficiaryOwnerPan(resultSetMap3.get("PANNO").toString());					
						}
						
						if(resultSetMap3.get("EMAIL") != null)
						{
							personalInfoBeneficiaryOwnerDTO.setBeneficiaryOwnerEmail(resultSetMap3.get("EMAIL").toString());					
						}	
						if(resultSetMap3.get("CONTACT_NO") != null)
						{
							personalInfoBeneficiaryOwnerDTO.setBeneficiaryOwnerMobile(resultSetMap3.get("CONTACT_NO").toString());					
						}	
						if(resultSetMap3.get("ADDRESS") != null)
						{
							personalInfoBeneficiaryOwnerDTO.setBeneficiaryOwnerAddress(resultSetMap3.get("ADDRESS").toString());					
						}	
						if(resultSetMap3.get("STATE") != null)
						{
							personalInfoBeneficiaryOwnerDTO.setBeneficiaryOwnerStateId(resultSetMap3.get("STATE").toString());					
						}	
						if(resultSetMap3.get("CITY") != null)
						{
							personalInfoBeneficiaryOwnerDTO.setBeneficiaryOwnerCityId(resultSetMap3.get("CITY").toString());					
						}	
						if(resultSetMap3.get("STATE_NAME") != null)
						{
							personalInfoBeneficiaryOwnerDTO.setBeneficiaryOwnerStateName(resultSetMap3.get("STATE_NAME").toString());					
						}	
						if(resultSetMap3.get("CITY_NAME") != null)
						{
							personalInfoBeneficiaryOwnerDTO.setBeneficiaryOwnerCityName(resultSetMap3.get("CITY_NAME").toString());					
						}	
						
						//1.0.0.8
						if(resultSetMap3.get("DEDUPE_INITIATED_FLAG") != null)
						{
							personalInfoBeneficiaryOwnerDTO.setBeneficiaryOwnerDedInitFlag(resultSetMap3.get("DEDUPE_INITIATED_FLAG").toString());
						}
						//1.0.0.8

						// Start 1.0.0.22
						if(resultSetMap3.get("UCIC_CODE") != null)
						{
							personalInfoBeneficiaryOwnerDTO.setBeneficiaryOwnerUcicCode(resultSetMap3.get("UCIC_CODE").toString());
						}
						if(resultSetMap3.get("DOB") != null)
						{
							personalInfoBeneficiaryOwnerDTO.setBeneficiaryOwnerDob(resultSetMap3.get("DOB").toString());
						}
						// End 1.0.0.22

						beneficiaryOwnerList.add(personalInfoBeneficiaryOwnerDTO);
					}
					
					map.put("beneficiaryOwnerDeatail", beneficiaryOwnerList);
					
				}
			}
			//End 1.0.0.7
			//1.0.0.17 start
			ArrayList keyContactSelfDatalist=(ArrayList)parameterDto4.getParameterValue();
			map.put("keyContactSelfDetail", keyContactSelfDatalist);
			
			//1.0.0.17 end
			
			//start by 1.0.0.19
			ArrayList leiDetails=(ArrayList)parameterDto5.getParameterValue();
			
			if(leiDetails!=null)
			{
			Map resultSetMap4 = null;
			Iterator itr3 = leiDetails.iterator();
			//ArrayList leiDetailsList = new ArrayList();
				if(itr3!=null)
				{
					while(itr3.hasNext())
						
					{										
						resultSetMap4=(Map)itr3.next();
						//leiDetailsDTO= new PersonalInfoDTO();
						
						/*if(resultSetMap4.get("LEI_ID") != null)
						{
							personalInfoDTO.setLeiId(resultSetMap4.get("LEI_ID").toString());					
						}	*/
						if(resultSetMap4.get("LEI_CODE_AVAILABLE") != null)
						{
							personalInfoDTO.setLeiCodeAvailable(resultSetMap4.get("LEI_CODE_AVAILABLE").toString());					
						}
						
						if(resultSetMap4.get("LEI_CODE") != null)
						{
							personalInfoDTO.setLeiCode(resultSetMap4.get("LEI_CODE").toString());					
						}
						
						if(resultSetMap4.get("LEI_VALIDITY") != null)
						{
							personalInfoDTO.setLeiValidity(resultSetMap4.get("LEI_VALIDITY").toString());					
						}
					
						if(resultSetMap4.get("AMOUNT_DECLARATION") != null)
						{
							personalInfoDTO.setAmountOfDeclaration(resultSetMap4.get("AMOUNT_DECLARATION").toString());					
						}
						if(resultSetMap4.get("DECLARATION_VALIDITY") != null)
						{
							personalInfoDTO.setDeclarationValidity(resultSetMap4.get("DECLARATION_VALIDITY").toString());					
						}
						//personalInfoDTO.add(leiDetailsDTO);
                         }
					
					map.put("personalInfoDTO", personalInfoDTO);
					
				}
			}
						
			//end by 1.0.0.19
			
			
		//1.0.0.23 start	
          ArrayList tdsRateSpec=(ArrayList)parameterDto6.getParameterValue();
			
			if(tdsRateSpec!=null)
			{
			Map resultSetMap4 = null;
			Iterator itr3 = tdsRateSpec.iterator();
				if(itr3!=null)
				{
					while(itr3.hasNext())
						
					{										
						resultSetMap4=(Map)itr3.next();
						
						if(resultSetMap4.get("LOWER_TDS_RATE") != null) { 
							personalInfoDTO.setLowerTdsPercent(resultSetMap4.get("LOWER_TDS_RATE").toString());						
						}
						if(resultSetMap4.get("TDS_VALID_FROM") != null) { 
							personalInfoDTO.setLowerTdsRateFrom(resultSetMap4.get("TDS_VALID_FROM").toString());						
						}
						if(resultSetMap4.get("TDS_VALID_TO") != null) { 
							personalInfoDTO.setLowerTdsRateTo(resultSetMap4.get("TDS_VALID_TO").toString());						
						}
                       }
					
					map.put("personalInfoDTO", personalInfoDTO);
					
				}
			}
						
			//1.0.0.23 end
			
			
			

		}
		  catch (DBConnectionException dce) 
		  {
			  log.error("DBConnectionException - FAIL:");
				log.error("DBConnectionException - Parameter : \r\n1. procName= " + procName + "; \r\n2. colParameter=( " + PrintParameterList.printParameterList(arrParameter) + " );");
		  	log.error("DBConnectionException - "+dce.getMessage());
		  }
		  catch (Exception e) 
		  {
			  log.error("Exception - FAIL:");
				log.error("Exception - Parameter : \r\n1. procName= " + procName + "; \r\n2. colParameter=( " + PrintParameterList.printParameterList(arrParameter) + " );");
		  	log.error("Exception - "+e.getMessage());
		  }
		  log.info("End");

		return map;
	}
	
	
	
	/**
	 * 
	 * @param personalInfoForm
	 * @param prospectNo
	 * @param userId
	 * @param dbConnectionMap
	 * @return
	 */
	public ReturnParamDTO savePersonalInfo(PersonalInfoForm personalInfoForm, String existingValue,String prospectNo,String userId,Map dbConnectionMap)
	{
			  log.info("Start");
			  log.info("tds flag:"+personalInfoForm.getTdsApplicableCheck());
			 // log.debug("Parameter : \r\n                                                                            1. personalInfoForm = ("+personalInfoForm.getAllProperty()+"); \r\n                                                                            2. prospectNo = "+prospectNo+"; \r\n                                                                            3. userId = "+userId+"; \r\n                                                                            4. dbConnectionMap = ("+ PrintDBConnectionMap.printMap(dbConnectionMap) + "); \r\n");
			  ReturnParamDTO returnDto=new ReturnParamDTO();
			  String dnsReason = "";
			  Map map = new HashMap();
			  if(personalInfoForm.getDnsReasonTemp()==null || personalInfoForm.getDnsReasonTemp().equals("")  )
			  {
			      dnsReason="";
			  }
			  else
			  {
				  dnsReason=personalInfoForm.getDnsReasonTemp();
			  }

			if(personalInfoForm.getCustEntityType().equals("1000000002"))
			{
				try{
					  personalInfoForm.setFirstName(personalInfoForm.getNameOfCompany());				  
					  personalInfoForm.setFatherFirstName(personalInfoForm.getAuthSignatoryFirstName());
					  personalInfoForm.setFatherMiddleName(personalInfoForm.getAuthSignatoryMiddleName());
					  personalInfoForm.setFatherLastName(personalInfoForm.getAuthSignatoryLastName());
					  personalInfoForm.setDateOfBirth(personalInfoForm.getDateOfIncorporation());
					  personalInfoForm.setPanNo(personalInfoForm.getCompanyPan());
					 
					  personalInfoForm.setLegalFormType(personalInfoForm.getLegalFormTypeCorp());
					  
					  /**
					   * Changes For Dia Vikas
					   * 
					   */
					  //FormFile myFile = personalInfoForm.getLegalForm();
					  personalInfoForm.setRbiRegNo(personalInfoForm.getRbiRegNo());
					  personalInfoForm.setKeyContactPerson(personalInfoForm.getKeyContactPerson());
					  personalInfoForm.setAuthCapital(personalInfoForm.getAuthCapital());
					  personalInfoForm.setIssuedCap(personalInfoForm.getIssuedCap());
					  personalInfoForm.setBoardRepresentative(personalInfoForm.getBoardRepresentative());
					  //System.out.println("==== "+personalInfoForm.getAuthCapital());
					  //System.out.println("==== "+personalInfoForm.getIssuedCap());
					  /**
					   * Changes For Dia Vikas End ------
					   * 
					   */
					  personalInfoForm.setMiddleName("");
					  personalInfoForm.setLastName("");
					  personalInfoForm.setMaritalStatus("");
					  personalInfoForm.setGender("");
					  personalInfoForm.setSelGender("");
					  personalInfoForm.setMotherFirstName("");
					  personalInfoForm.setMotherMiddleName("");
					  personalInfoForm.setMotherLastName("");
					  personalInfoForm.setSpouseFirstName("");
					  personalInfoForm.setSpouseMiddleName("");
					  personalInfoForm.setSpouseLastName("");
					  personalInfoForm.setNoOfDependents("");
					  personalInfoForm.setKycRiskCategory(personalInfoForm.getKycRiskCategory_Corp());// 1.0.0.4
					  personalInfoForm.setGstRegistrationStatus(personalInfoForm.getGstRegistrationStatus());//1.0.0.21
					 // personalInfoForm.setCustCategory("");
				}catch (Exception e) {
					e.printStackTrace();
					log.error("Exception -"+e.getMessage());
				}
			}
			else if(personalInfoForm.getCustEntityType().equals("1000000003"))
			{
			  personalInfoForm.setFirstName(personalInfoForm.getNameOfGroup());				  
			  personalInfoForm.setFatherFirstName(personalInfoForm.getRecruitedByFirstName());
			  personalInfoForm.setFatherMiddleName(personalInfoForm.getRecruitedByMiddleName());
			  personalInfoForm.setFatherLastName(personalInfoForm.getRecruitedByLastName());
			  personalInfoForm.setDateOfBirth(personalInfoForm.getTrainedDate());
			  
			  personalInfoForm.setMiddleName("");
			  personalInfoForm.setLastName("");
			  personalInfoForm.setMaritalStatus("");
			  personalInfoForm.setGender("");
			  personalInfoForm.setSelGender("");
			  personalInfoForm.setMotherFirstName("");
			  personalInfoForm.setMotherMiddleName("");
			  personalInfoForm.setMotherLastName("");
			  personalInfoForm.setSpouseFirstName("");
			  personalInfoForm.setSpouseMiddleName("");
			  personalInfoForm.setSpouseLastName("");
			  personalInfoForm.setNoOfDependents("");
			 // personalInfoForm.setCustCategory("");
			  personalInfoForm.setPanNo("");
			  /**
			   * Changes For Dia Vikas Start ---- 
			   * 
			   */
			  personalInfoForm.setRbiRegNo("");
			  personalInfoForm.setLegalForm(null);
			  personalInfoForm.setKeyContactPerson("");
			  personalInfoForm.setAuthCapital("");
			  personalInfoForm.setIssuedCap("");
			  personalInfoForm.setBoardRepresentative("");
			  personalInfoForm.setFileData(null);
			  /**
			   * Changes For Dia Vikas Ended ---
			   * 
			   */
			}
			else if(personalInfoForm.getCustEntityType().equals("1000000001"))
			{
				
				personalInfoForm.setLegalFormType(personalInfoForm.getLegalFormTypeInd());
			}
			
			  	DAL dal=new DAL();
		    	int parameterCount=0;
				ArrayList arrParameter=new ArrayList();
				String procName=null;

				ParameterDTO parameterDtoMessage= null;
				ParameterDTO parameterDtoResult= null;
				// This code is written for handling transaction management
				Connection connection=DBConnection.getConnection(dbConnectionMap);
				try
				{
					connection.setAutoCommit(false);
				}
				catch (SQLException sqle) 
				{
					log.error("SQLException - "+sqle.getMessage());
				}
				catch (Exception e) 
				{
					log.error("Exception - "+e.getMessage());
				}
				  if(dbConnectionMap.get("DB_SERVER").toString().equals("ORACLE"))
				  { 
					    procName="{call Pkg_Los_Trn_Applicant_Master.PR_APP_MASTER_TRANSACTION(?,?,?,?,?, ?,?,?,?,?, ?,?,?,?,?, ?,?,?,?,?, ?,?,?,?,?, ?,?,?,?,?, ?,?,?,?,?, "
					    		+ "?,?,?,?,?, ?,?,?,?,?, ?,?,?,?,?, ?,?,?,?,?, ?,?,?,?,?, ?,?,?,?,?, ?,?,?,?,?, ?,?,?,?,?, ?,?,?,?,?, ?,?,?,?,?, ?,?,?,?,?, ?,?,?,?,? ,?"
					    		+ ",?,?,?,?, ?,?,?,?, ?,? ,? ,?,?,?,?, ?,?,?,?,?,?,?)}"; //1.0.0.3  //1.0.0.6added//1.0.0.9 //1.0.0.10 //1.0.0.11//1.0.0.12//1.0.0.13 //1.0.0.16 //1.0.0.16//1.0.0.18//1.0.0.19//1.0.0.22
					 	arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,personalInfoForm.getApplicantID()));
					 	arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,personalInfoForm.getApplicantCode()));
						arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,personalInfoForm.getApplicantType()));
						arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,personalInfoForm.getTitle()));
					 	arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,personalInfoForm.getFirstName()));
					 	
						arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,personalInfoForm.getMiddleName()));
						arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,personalInfoForm.getLastName()));
					 	arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,personalInfoForm.getReligion())); 
						arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,personalInfoForm.getMaritalStatus()));
						arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,personalInfoForm.getDateOfBirth()));
						
					 	arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,personalInfoForm.getGender()));
						arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,personalInfoForm.getNoOfDependents()!=null&&!"".equalsIgnoreCase(personalInfoForm.getNoOfDependents())?personalInfoForm.getNoOfDependents():"0"));
						arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,personalInfoForm.getFatherTitle()));
					 	arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,personalInfoForm.getFatherFirstName()));
						arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,personalInfoForm.getFatherMiddleName()));
						
						arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,personalInfoForm.getFatherLastName()));
					 	arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,personalInfoForm.getMotherFirstName()));
						arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,personalInfoForm.getMotherMiddleName()));
						arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,personalInfoForm.getMotherLastName()));
					 	arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,personalInfoForm.getSpouseTitle()));
					 	
						arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,personalInfoForm.getSpouseFirstName()));
						arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,personalInfoForm.getSpouseMiddleName()));
						arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,personalInfoForm.getSpouseLastName()));
						arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,personalInfoForm.getSpouseOccupation()));
					 	arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,personalInfoForm.getSpouseNameOfCompany()));
					 	
						arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,personalInfoForm.getConstitution()));
						arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,personalInfoForm.getCustCategory()));
					 	arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,personalInfoForm.getQualificationId()));
					 	arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,personalInfoForm.getHighestQualification()));
						arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,personalInfoForm.getDegree()));
						
					 	arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,personalInfoForm.getCollegeLastAttended()));
						arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,personalInfoForm.getNationality()));
						arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,personalInfoForm.getNlFirstName()));
						arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,personalInfoForm.getNlSecondName()));
						arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,personalInfoForm.getNlLastName()));
						
					 	arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,personalInfoForm.getVoterId()));
						arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,personalInfoForm.getIsPanNo()));
						arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,personalInfoForm.getPanNo()));
					 	arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,personalInfoForm.getDeclaration()));
						arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,personalInfoForm.getDrivingLicenseNo()));
						
						arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,personalInfoForm.getPassportNo()));
					 	arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,personalInfoForm.getBasic()));
						arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,personalInfoForm.getDa()));
						arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,personalInfoForm.getHra()));
					 	arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,personalInfoForm.getSpAllowance()));
					 	
						arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,personalInfoForm.getMedicalLta()));
						arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,personalInfoForm.getBonusIncentive()));
						arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,personalInfoForm.getOthers()));
						arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,personalInfoForm.getGrossIncome()));
					 	arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,personalInfoForm.getOtherIncome()));
					 	
						arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,personalInfoForm.getNetIncome()));
						arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,personalInfoForm.getDnsRequired1()));
					 	arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,dnsReason));
						arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,personalInfoForm.getPreferredTimeToCall()));
						arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,personalInfoForm.getPreferredModeOfCommunication()));
						
					 	arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,userId+""));
						arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,"20-FEB-08"));
					 	arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,""+userId));
						arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,"20-FEB-08"));
						arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,"A"));
						
						arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,prospectNo));
						arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,personalInfoForm.getRelation()));
						arrParameter.add(new ParameterDTO(++parameterCount,"OUT",oracle.jdbc.OracleTypes.VARCHAR,null));
					 	arrParameter.add(new ParameterDTO(++parameterCount,"OUT",oracle.jdbc.OracleTypes.VARCHAR,null));
					 	arrParameter.add(new ParameterDTO(++parameterCount,"OUT",oracle.jdbc.OracleTypes.INTEGER,null));
					 	
						arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,personalInfoForm.getCustEntityType()));
						// Added for Dia Vikas 
					 	arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,personalInfoForm.getRbiRegNo()));
					
					if(personalInfoForm.getLegalForm()==null || personalInfoForm.getLegalForm().getFileName().trim().length()<1)
					{	
					 	arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.BLOB,personalInfoForm.getTheFile()));
					 	arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,null));
					}
					else
					{
						arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.BLOB,personalInfoForm.getTheFile()));
					 	arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,personalInfoForm.getFileNameDms()!=null
					 			?personalInfoForm.getFileNameDms():personalInfoForm.getLegalForm().getFileName()));//1.0.0.15
					}
					 	arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,personalInfoForm.getKeyContactPerson()));
					 	arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,personalInfoForm.getAuthCapital()));
					 	arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,personalInfoForm.getIssuedCap()));
					 	arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,personalInfoForm.getBoardRepresentative()));
					 	arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,personalInfoForm.getLegalFormType()));
					 	arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,personalInfoForm.getRelationShipManager()));
					 	// Added for Dia Vikas	entityId
					 	arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,personalInfoForm.getEntityIdForCustomer()));
						arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,personalInfoForm.getTin()));
						// Added By Rinky Arora
						arrParameter.add(new ParameterDTO(++parameterCount,"IN OUT",oracle.jdbc.OracleTypes.VARCHAR,("Y".equalsIgnoreCase(personalInfoForm.getIsExisting())?personalInfoForm.getExistingApplicantId():null)));
						arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,existingValue));
						if(personalInfoForm.getCustEntityType().equalsIgnoreCase("1000000002"))
					 	{
					 	 arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,personalInfoForm.getCorpTanNo()));
					 	personalInfoForm.setGstRegistrationStatus(personalInfoForm.getGstRegistrationStatusCorp());//1.0.0.21
					 	}
					 	else
					 	{
					 		arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,personalInfoForm.getTanNo()));
					 	}
						arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,null));
						arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,personalInfoForm.getPsl()));
						arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,personalInfoForm.getCaste()));
						arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,personalInfoForm.getAdharNo()));
						arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,personalInfoForm.getCustomerSegment())); // ravi
				  
						arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,null)); //P_RELATIONSHIPID  //1.0.0.3 added
						 ///// Changes By apurva////////////
		                
		                arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,personalInfoForm.getIndustry()));
		                arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,personalInfoForm.getSector()));
		                arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,personalInfoForm.getWebsite()));
		                arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,personalInfoForm.getProductInstrestedIn()));
		                arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,personalInfoForm.getAssetCategory()));
		                arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,personalInfoForm.getCompetition()));
		                arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,personalInfoForm.getChannelType()));
		                arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,personalInfoForm.getChannelName()));
		                arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,personalInfoForm.getAgentName()));
		                arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,personalInfoForm.getBranch()));
		                arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,personalInfoForm.getLob()));//1.0.0.1
		                if(personalInfoForm.getEntityIdForCustomer().equalsIgnoreCase("1000000008")){
		                	if(personalInfoForm.getApplicantType().equalsIgnoreCase("1000000002")){
		                arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,null));//keyContactDetails
		                	}
		                	else{
				                arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,personalInfoForm.getKeyContactDataStr()));
				                }
		                }else{
		                	arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,personalInfoForm.getKeyContactDataStr()));
		                }
		                arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,personalInfoForm.getRemoveKeyContactDataStr()));
		                arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,null));//keyContactDetails
		                arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,personalInfoForm.getKycRiskCategory())); // 1.0.0.4
		                arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,personalInfoForm.getAssetCatGpSaveStr())); //1.0.0.6
		                arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,personalInfoForm.getBeneficiaryOwnerDataStr())); // 1.0.0.7 PC_APPLICANT_BENEFICIARY
		                arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,personalInfoForm.getRemoveBeneficiaryOwnerDataStr())); //1.0.0.7 PC_DELETE_APP_BENEFICIARY
		                arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,personalInfoForm.getLpiRateType())); //1.0.0.9
		                arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,personalInfoForm.getLpiRate())); //1.0.0.9
		                String tdsRateSpec=personalInfoForm.getLowerTdsPercent()+"~"+personalInfoForm.getLowerTdsRateFrom()+"~"+personalInfoForm.getLowerTdsRateTo();//1.0.0.23
		                if(personalInfoForm.getTdsApplicableCheck()==null || "".equals(personalInfoForm.getTdsApplicableCheck())) {
		                	personalInfoForm.setTdsApplicableCheck("N");
		                }
		                
		                if(personalInfoForm.getTdsApplicableCheck().equals("N")) {
		                	tdsRateSpec="";
		                }//1.0.0.23
		                arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,personalInfoForm.getTdsApplicableCheck()));//1.0.0.18
		               // arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,personalInfoForm.getUdyamNo().toUpperCase())); //Added by Nalin 1.0.0.10
		                if(personalInfoForm.getUdyamNo()!=null && !(personalInfoForm.getUdyamNo().equalsIgnoreCase(""))) {
			                arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,personalInfoForm.getUdyamNo().toUpperCase())); //Added by Nalin 1.0.0.10
			                }
			                else {
			                	arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,null));
			                }//1.0.0.18

		                arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,personalInfoForm.getVoterIdFlg())); //Added by 1.0.0.13
		                arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,personalInfoForm.getDlVarifyFlag())); //Added 1.0.0.12
		                arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,personalInfoForm.getPassportNoVerifiedFlag())); //Added 1.0.0.11
		                arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,personalInfoForm.getUdyamIdFlg())); //1.0.0.14
		                arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,personalInfoForm.getQuotationCode())); //1.0.0.16
		                arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,personalInfoForm.getNatureOfBusinessComn())); //1.0.0.16
		                arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,personalInfoForm.getLeiDetailstr()));//1.0.0.19
		                arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,personalInfoForm.getGstRegistrationStatus())); // 1.0.0.21
		                arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,
		                		tdsRateSpec)); //1.0.0.23
		                arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.CLOB,personalInfoForm.getPaymentTerms())); //1.0.0.24
				  }
				  else if(dbConnectionMap.get("DB_SERVER").toString().equals("MSSQLSERVER"))
				  {
					  procName="{call QC_LOS_FINAL.DBO.lostrnapplicantmaster_prappmastertransaction(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
					  arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getApplicantID()));
					 	arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getApplicantCode()));
						arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getApplicantType()));
						arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getTitle()));
					 	arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getFirstName()));
						arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getMiddleName()));
						arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getLastName()));
					 	arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getReligion()));
						arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getMaritalStatus()));
						arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getDateOfBirth()));
					 	arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getGender()));
						arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getNoOfDependents()!=null&&!"".equalsIgnoreCase(personalInfoForm.getNoOfDependents())?personalInfoForm.getNoOfDependents():"0"));
						arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getFatherTitle()));
					 	arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getFatherFirstName()));
						arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getFatherMiddleName()));
						arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getFatherLastName()));
					 	arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getMotherFirstName()));
						arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getMotherMiddleName()));
						arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getMotherLastName()));
					 	arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getSpouseTitle()));
						arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getSpouseFirstName()));
						arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getSpouseMiddleName()));
						arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getSpouseLastName()));
						arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getSpouseOccupation()));
					 	arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getSpouseNameOfCompany()));
						arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getConstitution()));
						arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getCustCategory()));
					 	arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getEduQualification()));
					 	arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getHighestQualification()));
						arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getDegree()));
					 	arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getCollegeLastAttended()));
						arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getNationality()));
						arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getNlFirstName()));
						arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getNlSecondName()));
						arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getNlLastName()));
					 	arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getVoterId()));
						arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getIsPanNo()));
						arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getPanNo()));
					 	arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getDeclaration()));
						arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getDrivingLicenseNo()));
						arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getPassportNo()));
					 	arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getBasic()));
						arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getDa()));
						arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getHra()));
					 	arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getSpAllowance()));
						arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getMedicalLta()));
						arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getBonusIncentive()));
						arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getOthers()));
						arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getGrossIncome()));
					 	arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getOtherIncome()));
						arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getNetIncome()));
						arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getDnsRequired()));
					 	arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,dnsReason));
						arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getPreferredTimeToCall()));
						arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getPreferredModeOfCommunication()));
					 	arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,userId+""));
						arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,"20-FEB-08"));
					 	arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,""+userId));
						arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,"20-FEB-08"));
						arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,"A"));
						arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,prospectNo));
						arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getRelation()));
						arrParameter.add(new ParameterDTO(++parameterCount,"OUTPUT",java.sql.Types.VARCHAR,null));
					 	arrParameter.add(new ParameterDTO(++parameterCount,"OUTPUT",java.sql.Types.VARCHAR,null));
					 	arrParameter.add(new ParameterDTO(++parameterCount,"OUTPUT",java.sql.Types.VARCHAR,null));
						arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getCustEntityType()));
						// Added for Dia Vikas
					 	arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getRbiRegNo()));
					 	arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.BLOB,personalInfoForm.getFileData()));
					 	arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getKeyContactPerson()));
					 	arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.DOUBLE,personalInfoForm.getAuthCapital()));
					 	arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.DOUBLE,personalInfoForm.getIssuedCap()));
					 	arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getBoardRepresentative()));
					 // Added for Dia Vikas
						
				  }
				  else if(dbConnectionMap.get("DB_SERVER").toString().equals("MYSQL"))
				  {
					  procName="{call QC_LOS_FINAL.DBO.lostrnapplicantmaster_prappmastertransaction(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
					  arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.INTEGER,personalInfoForm.getApplicantID()!=null&&!"".equals(personalInfoForm.getApplicantID())?Integer.parseInt(personalInfoForm.getApplicantID()):0));
					  arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getApplicantCode()));
					  arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.INTEGER,personalInfoForm.getApplicantType()!=null&&!"".equals(personalInfoForm.getApplicantType())?Integer.parseInt(personalInfoForm.getApplicantType()):0));
					  arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getTitle()));
					  arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getFirstName()));
					  arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getMiddleName()));
					  arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getLastName()));
					  arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getReligion()));
					  arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.INTEGER,personalInfoForm.getMaritalStatus()!=null&&!"".equals(personalInfoForm.getMaritalStatus())?Integer.parseInt(personalInfoForm.getMaritalStatus()):0));
					  arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getDateOfBirth()));
					  arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getGender()));
					  arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.INTEGER,personalInfoForm.getNoOfDependents()!=null&&!"".equals(personalInfoForm.getNoOfDependents())?Integer.parseInt(personalInfoForm.getNoOfDependents()):0));
					  arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getFatherTitle()));
					  arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getFatherFirstName()));
					  arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getFatherMiddleName()));
					  arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getFatherLastName()));
					  arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getMotherFirstName()));
					  arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getMotherMiddleName()));
					  arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getMotherLastName()));
					  arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getSpouseTitle()));
					  arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getSpouseFirstName()));
					  arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getSpouseMiddleName()));
					  arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getSpouseLastName()));
					  arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getSpouseOccupation()));
					  arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getSpouseNameOfCompany()));
					  arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.INTEGER,personalInfoForm.getConstitution()!=null&&!"".equals(personalInfoForm.getConstitution())?Integer.parseInt(personalInfoForm.getConstitution()):0));
					  arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getCustCategory()));
					  arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.INTEGER,personalInfoForm.getEduQualification()!=null&&!"".equals(personalInfoForm.getEduQualification())?Integer.parseInt(personalInfoForm.getEduQualification()):0));
					  arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.INTEGER,personalInfoForm.getHighestQualification()!=null&&!"".equals(personalInfoForm.getHighestQualification())?Integer.parseInt(personalInfoForm.getHighestQualification()):0));
					  arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getDegree()));
					  arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getCollegeLastAttended()));
					  arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getNationality()));
					  arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getNlFirstName()));
					  arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getNlSecondName()));
					  arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getNlLastName()));
					  arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getVoterId()));
					  arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getIsPanNo()));
					  arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getPanNo()));
					  arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getDeclaration()));
					  arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getDrivingLicenseNo()));
					  arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getPassportNo()));
					  arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.INTEGER,personalInfoForm.getBasic()!=null&&!"".equals(personalInfoForm.getBasic())?Integer.parseInt(personalInfoForm.getBasic()):0));
					  arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.INTEGER,personalInfoForm.getDa()!=null&&!"".equals(personalInfoForm.getDa())?Integer.parseInt(personalInfoForm.getDa()):0));
					  arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.INTEGER,personalInfoForm.getHra()!=null&&!"".equals(personalInfoForm.getHra())?Integer.parseInt(personalInfoForm.getHra()):0));
					  arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.INTEGER,personalInfoForm.getSpAllowance()!=null&&!"".equals(personalInfoForm.getSpAllowance())?Integer.parseInt(personalInfoForm.getSpAllowance()):0));
					  arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.INTEGER,personalInfoForm.getMedicalLta()!=null&&!"".equals(personalInfoForm.getMedicalLta())?Integer.parseInt(personalInfoForm.getMedicalLta()):0));
					  arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.INTEGER,personalInfoForm.getBonusIncentive()!=null&&!"".equals(personalInfoForm.getBonusIncentive())?Integer.parseInt(personalInfoForm.getBonusIncentive()):0));
					  arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.INTEGER,personalInfoForm.getOthers()!=null&&!"".equals(personalInfoForm.getOthers())?Integer.parseInt(personalInfoForm.getOthers()):0));
					  arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.INTEGER,personalInfoForm.getGrossIncome()!=null&&!"".equals(personalInfoForm.getGrossIncome())?Integer.parseInt(personalInfoForm.getGrossIncome()):0));
					  arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.INTEGER,personalInfoForm.getOtherIncome()!=null&&!"".equals(personalInfoForm.getOtherIncome())?Integer.parseInt(personalInfoForm.getOtherIncome()):0));
					  arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.INTEGER,personalInfoForm.getNetIncome()!=null&&!"".equals(personalInfoForm.getNetIncome())?Integer.parseInt(personalInfoForm.getNetIncome()):0));
					  arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getDnsRequired()));
					  arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getDnsReason()));
					  arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getPreferredTimeToCall()));
					  arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getPreferredModeOfCommunication()));
					  arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,Integer.parseInt(userId)));
					  arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,"20-FEB-08"));
					  arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.INTEGER,Integer.parseInt(userId)));
					  arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,"20-FEB-08"));
					  arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,"A"));
					  arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,prospectNo));
					  arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.INTEGER,personalInfoForm.getRelation()!=null&&!"".equals(personalInfoForm.getRelation())?Integer.parseInt(personalInfoForm.getRelation()):0));
					  arrParameter.add(new ParameterDTO(++parameterCount,"OUTPUT",java.sql.Types.VARCHAR,null));
					  arrParameter.add(new ParameterDTO(++parameterCount,"OUTPUT",java.sql.Types.VARCHAR,null));
					  arrParameter.add(new ParameterDTO(++parameterCount,"OUTPUT",java.sql.Types.INTEGER,null));
					  arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getRbiRegNo()));
				 	  arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.BLOB,personalInfoForm.getFileData()));
				 	  arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getKeyContactPerson()));
				 	  arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.DOUBLE,personalInfoForm.getAuthCapital()));
				 	  arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.DOUBLE,personalInfoForm.getIssuedCap()));
				 	  arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getBoardRepresentative()));

					  
					  
				  }
			 try
			 {
				 int applicantId;
				 //log.error("processSPROC - Parameter : \r\n1. procName= " + procName + "; \r\n2. colParameter=( " + PrintParameterList.printParameterList(arrParameter) + " );");
				 dal.processSPROC(procName, arrParameter,dbConnectionMap,connection);
				 
				 
				parameterDtoResult = (ParameterDTO)arrParameter.get(62);
				parameterDtoMessage = (ParameterDTO)arrParameter.get(63);	
				ParameterDTO parameterDtoId = (ParameterDTO)arrParameter.get(64);
				ParameterDTO parameterDtoId1 = (ParameterDTO)arrParameter.get(77);
				map.put("msg",(String)parameterDtoMessage.getParameterValue()); 
				if("S".equalsIgnoreCase((String)parameterDtoResult.getParameterValue()))
				{
					DBConnection.commitTransaction(connection);
				}
				else
				{
					log.error("processSPROC - FAIL:");
					log.error("processSPROC - Parameter : \r\n1. procName= " + procName + "; \r\n2. colParameter=( " + PrintParameterList.printParameterList(arrParameter) + " );");
					DBConnection.rollbackTransaction(connection);
				}
				
				
				/* if(!personalInfoForm.getApplicantID().equals("0"))
				 {
					 applicantId=Integer.parseInt(personalInfoForm.getApplicantID());
					 
				 }
				 else
				 {
					 parameterDtoId = (ParameterDTO)arrParameter.get(64);
					 applicantId = Integer.parseInt(parameterDtoId.getParameterValue().toString());
				 }
				 map.put("applicantId", applicantId); */

				 if(parameterDtoId.getParameterValue()!=null)
				 {
					  applicantId = Integer.parseInt(parameterDtoId.getParameterValue().toString());
					  map.put("applicantId", applicantId);
				 }
				 else if(parameterDtoId1.getParameterValue()!=null)
				 {
					  applicantId = Integer.parseInt(parameterDtoId1.getParameterValue().toString());
					  map.put("applicantId", applicantId);
				 }
				 else
				 {
					 map.put("applicantId", personalInfoForm.getApplicantID());
				 }
				String result = (String)parameterDtoResult.getParameterValue();
				map.put("result", result);
				returnDto.setMessage((String)parameterDtoMessage.getParameterValue());
				returnDto.setStatus((String)parameterDtoResult.getParameterValue());
				returnDto.setReturnParameter(map);
			 }
			 catch (DBConnectionException dce) 
			 {
				log.error("DBConnectionException - FAIL:");
				log.error("DBConnectionException - Parameter : \r\n1. procName= " + procName + "; \r\n2. colParameter=( " + PrintParameterList.printParameterList(arrParameter) + " );");
			 	log.error("DBConnectionException - "+dce.getMessage());
			 	DBConnection.rollbackTransaction(connection);
			 }
			/* catch (SQLException sqle) 
			 {
				 log.error("processSPROC - FAIL:");
					log.error("processSPROC - Parameter : \r\n1. procName= " + procName + "; \r\n2. colParameter=( " + PrintParameterList.printParameterList(arrParameter) + " );");
					log.error("savePersonalInfo - SQLException - "+sqle.getMessage());
			 	DBConnection.rollbackTransaction(connection);
			 }*/
			 catch (Exception e) 
			 {
				log.error("Exception - FAIL:");
				log.error("Parameter : \r\n1. procName= " + procName + "; \r\n2. colParameter=( " + PrintParameterList.printParameterList(arrParameter) + " );");
			 	log.error("Exception - "+e.getMessage());
			 	DBConnection.rollbackTransaction(connection);
			 }
			finally
			{
				DBConnection.closeConnection(connection);
			}
			log.info("End");
			return returnDto;
	}
	
	
	
	
	/**
	 * 
	 * @param ProspectId
	 * @param ApplicantType
	 * @param applicantId
	 * @param authMode
	 * @param dbConnectionMap
	 * @return
	 */
	public Map GetPdePersonalInfo(int ProspectId,int ApplicantType,String entityId,int applicantId,String authMode,Map dbConnectionMap) 
	{
		log.info("Start");
		//log.debug("GetPdePersonalInfo - Parameter : \r\n                                                                            1. ProspectId = "+ProspectId+"; \r\n                                                                            2. ApplicantType = "+ApplicantType+"; \r\n                                                                            3. applicantId = "+applicantId+"; \r\n                                                                            4. authMode = "+authMode+"; \r\n                                                                            5. dbConnectionMap = ("+ PrintDBConnectionMap.printMap(dbConnectionMap) + "); \r\n");
		DAL dal=new DAL();
    	int parameterCount=0;
		ArrayList arrParameter=new ArrayList();
		String procName=null;
		ParameterDTO parameterDto= null;
		PersonalInfoDTO personalInfoDTO = new PersonalInfoDTO();
		String message = null;
		String status = null;
		String messageAndStatus = null;
		PersonalInfoDTO personalInfoKeyContactDTO= null;
		ParameterDTO parameterDto2= null;
		ParameterDTO parameterDto3= null; //1.0.0.19
		
		ParameterDTO parameterDto6= null;//1.0.0.23
		
		Map map = new HashMap();
		
		  if(dbConnectionMap.get("DB_SERVER").toString().equals("ORACLE"))
		  {
			    procName="{call Pkg_LMS_APPLICANT_MASTER.GETSINGLEAPPLICANTMASTER(?,?,?,?,? ,?,?,?,?,?,?,?)}";
			 	arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,""+ProspectId));
			 	arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,""+ApplicantType));
				arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,""+applicantId));
				arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,authMode!=null&&(!("".equalsIgnoreCase(authMode)))?authMode:""));
				arrParameter.add(new ParameterDTO(++parameterCount,"OUT",oracle.jdbc.OracleTypes.CURSOR,null));
				arrParameter.add(new ParameterDTO(++parameterCount,"OUT",oracle.jdbc.OracleTypes.VARCHAR,null));
				arrParameter.add(new ParameterDTO(++parameterCount,"OUT",oracle.jdbc.OracleTypes.VARCHAR,null));
				arrParameter.add(new ParameterDTO(++parameterCount,"IN OUT",oracle.jdbc.OracleTypes.VARCHAR,""));
				arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,entityId));
				arrParameter.add(new ParameterDTO(++parameterCount,"OUT",oracle.jdbc.OracleTypes.CURSOR,null));
				arrParameter.add(new ParameterDTO(++parameterCount,"OUT",oracle.jdbc.OracleTypes.CURSOR,null)); //1.0.0.19
				arrParameter.add(new ParameterDTO(++parameterCount,"OUT",oracle.jdbc.OracleTypes.CURSOR,null));//1.0.0.23
		  }
		  else if(dbConnectionMap.get("DB_SERVER").toString().equals("MSSQLSERVER"))
		  {
			  procName="{call QC_LMS_FINAL.DBO.LmsApplicantMaster_PrGetSingleApplicantMaster(?,?,?,?,? ,?,?,?)}";
			  arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,""+ProspectId));
			  arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,""+ApplicantType));
			  arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,""+applicantId));
			  arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,authMode!=null&&(!("".equalsIgnoreCase(authMode)))?authMode:""));
			  arrParameter.add(new ParameterDTO(++parameterCount,"RESULTSET",java.sql.Types.VARCHAR,"5"));
			  arrParameter.add(new ParameterDTO(++parameterCount,"OUTPUT",java.sql.Types.VARCHAR,null));
			  arrParameter.add(new ParameterDTO(++parameterCount,"OUTPUT",java.sql.Types.VARCHAR,null));
			  arrParameter.add(new ParameterDTO(++parameterCount,"OUTPUT",java.sql.Types.VARCHAR,null));
		  }
		  else if(dbConnectionMap.get("DB_SERVER").toString().equals("MYSQL"))
		  {
			  procName="{call Pkg_LMS_APPLICANT_MASTER.GETSINGLEAPPLICANTMASTER(?,?,?,?,?,?,?,?)}";
			  arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,""+ProspectId));
			  arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,""+ApplicantType));
			  arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,""+applicantId));
			  arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,authMode!=null&&(!("".equalsIgnoreCase(authMode)))?authMode:""));
			  arrParameter.add(new ParameterDTO(++parameterCount,"RESULTSET",java.sql.Types.VARCHAR,"5"));
			  arrParameter.add(new ParameterDTO(++parameterCount,"OUTPUT",java.sql.Types.VARCHAR,null));
			  arrParameter.add(new ParameterDTO(++parameterCount,"OUTPUT",java.sql.Types.VARCHAR,null));
			  arrParameter.add(new ParameterDTO(++parameterCount,"OUTPUT",java.sql.Types.VARCHAR,""));
		  }
		  try
		  {
			  dal.processSPROC(procName, arrParameter,dbConnectionMap);
			  parameterDto = (ParameterDTO)arrParameter.get(4);
			  ArrayList list=(ArrayList)parameterDto.getParameterValue();
			  parameterDto2 = (ParameterDTO)arrParameter.get(9);
			  parameterDto3= (ParameterDTO)arrParameter.get(10); //1.0.0.19
			  parameterDto6= (ParameterDTO)arrParameter.get(11); //1.0.0.23
			  Iterator itr = list.iterator();
			  Map resultSetMap = null;
			  if(itr!=null)
			  {
				  while(itr.hasNext())
				  {
					  resultSetMap=(Map)itr.next();
					  if(resultSetMap.get("APPLICANTID") != null)
					  {
						  personalInfoDTO.setApplicantID(resultSetMap.get("APPLICANTID").toString());
					  }
					  if(resultSetMap.get("APPLICANTCODE") != null)
					  {
						  personalInfoDTO.setApplicantCode(resultSetMap.get("APPLICANTCODE").toString());
					  }
					  if(resultSetMap.get("APPLICANTTYPE") != null)
					  {
						  personalInfoDTO.setApplicantType(resultSetMap.get("APPLICANTTYPE").toString());
					  }
					  if(resultSetMap.get("TITLE") != null)
					  {
						  personalInfoDTO.setTitle(resultSetMap.get("TITLE").toString());
					  }
					  if(resultSetMap.get("FNAME") != null)
					  {
						  personalInfoDTO.setFirstName(resultSetMap.get("FNAME").toString());
					  }
					  if(resultSetMap.get("MNAME") != null)
					  {
						  personalInfoDTO.setMiddleName(resultSetMap.get("MNAME").toString());
					  }
					  if(resultSetMap.get("LNAME") != null)
					  {
						  personalInfoDTO.setLastName(resultSetMap.get("LNAME").toString());
					  }
					  if(resultSetMap.get("RELIGION") != null)
					  {
						  personalInfoDTO.setReligion(resultSetMap.get("RELIGION").toString());
					  }
					  if(resultSetMap.get("MARTIALSTATUS") != null)
					  {
						  personalInfoDTO.setMaritalStatus(resultSetMap.get("MARTIALSTATUS").toString());
					  }
					  if(resultSetMap.get("DOB") != null)
					  {
						  personalInfoDTO.setDateOfBirth(resultSetMap.get("DOB").toString());
					  }
					  if(resultSetMap.get("GENDER") != null)
					  {
						  personalInfoDTO.setGender(resultSetMap.get("GENDER").toString());
					  }
					  if(resultSetMap.get("NOOFDEPENDENTS") != null)
					  {
						  personalInfoDTO.setNoOfDependents(resultSetMap.get("NOOFDEPENDENTS").toString());
					  }
					  if(resultSetMap.get("FATHERTITLE") != null)
					  {
						  personalInfoDTO.setFatherTitle(resultSetMap.get("FATHERTITLE").toString());
					  }
					  if(resultSetMap.get("FATHERFNAME") != null)
					  {
						  personalInfoDTO.setFatherFirstName(resultSetMap.get("FATHERFNAME").toString());
					  }
					  if(resultSetMap.get("FATHERMNAME") != null)
					  {
						  personalInfoDTO.setFatherMiddleName(resultSetMap.get("FATHERMNAME").toString());
					  }
					  if(resultSetMap.get("FATHERLNAME") != null)
					  {
						  personalInfoDTO.setFatherLastName(resultSetMap.get("FATHERLNAME").toString());
					  }
					  if(resultSetMap.get("MOTHERFNAME") != null)
					  {
						  personalInfoDTO.setMotherFirstName(resultSetMap.get("MOTHERFNAME").toString());
					  }
					  if(resultSetMap.get("MOTHERMNAME") != null)
					  {
						  personalInfoDTO.setMotherMiddleName(resultSetMap.get("MOTHERMNAME").toString());
					  }
					  if(resultSetMap.get("MOTHERLNAME") != null)
					  {
						  personalInfoDTO.setMotherLastName(resultSetMap.get("MOTHERLNAME").toString());
					  }
					  if(resultSetMap.get("SPOUSEFNAME") != null)
					  {
						  personalInfoDTO.setSpouseFirstName(resultSetMap.get("SPOUSEFNAME").toString());
					  }
					  if(resultSetMap.get("SPOUSEMNAME") != null)
					  {
						  personalInfoDTO.setSpouseMiddleName(resultSetMap.get("SPOUSEMNAME").toString());
					  }
					  if(resultSetMap.get("SPOUSELNAME") != null)
					  {
						  personalInfoDTO.setSpouseLastName(resultSetMap.get("SPOUSELNAME").toString());
					  }
					  if(resultSetMap.get("SPOUSEOCCUPATION") != null)
					  {
						  personalInfoDTO.setSpouseOccupation(resultSetMap.get("SPOUSEOCCUPATION").toString());
					  }
					  if(resultSetMap.get("SPOUSENAMEOFCOMPANY") != null)
					  {
						  personalInfoDTO.setSpouseNameOfCompany(resultSetMap.get("SPOUSENAMEOFCOMPANY").toString());
					  }
					  if(resultSetMap.get("CONSTITUTION") != null)
					  {
						  personalInfoDTO.setConstitution(resultSetMap.get("CONSTITUTION").toString());
					  }
					  if(resultSetMap.get("CUSTCATEGORY") != null)
					  {
						  personalInfoDTO.setCustCategory(resultSetMap.get("CUSTCATEGORY").toString());
					  }
					  if(resultSetMap.get("EDUQUALIFICATION") != null)
					  {
						  personalInfoDTO.setEduQualification(resultSetMap.get("EDUQUALIFICATION").toString());
					  }
					  if(resultSetMap.get("HIGHESTQUALIFICATION") != null)
					  {
						  personalInfoDTO.setHighestQualification(resultSetMap.get("HIGHESTQUALIFICATION").toString());
					  }
					  if(resultSetMap.get("DEGREE") != null)
					  {
						  personalInfoDTO.setDegree(resultSetMap.get("DEGREE").toString());
					  }
					  if(resultSetMap.get("COLLEGELASTATTENDED") != null)
					  {
						  personalInfoDTO.setCollegeLastAttended(resultSetMap.get("COLLEGELASTATTENDED").toString());
					  }
					  if(resultSetMap.get("NATIONALITY") != null)
					  {
						  personalInfoDTO.setNationality(resultSetMap.get("NATIONALITY").toString());
					  }
					  if(resultSetMap.get("NLFNAME") != null)
					  {
						  personalInfoDTO.setNlFirstName(resultSetMap.get("NLFNAME").toString());
					  }
					  if(resultSetMap.get("NLMNAME") != null)
					  {
						  personalInfoDTO.setNlSecondName(resultSetMap.get("NLMNAME").toString());
					  }
					  if(resultSetMap.get("NLLNAME") != null)
					  {
						  personalInfoDTO.setNlLastName(resultSetMap.get("NLLNAME").toString());
					  }
					  if(resultSetMap.get("VOTERSID") != null)
					  {
						  personalInfoDTO.setVoterId(resultSetMap.get("VOTERSID").toString());
					  }
					  if(resultSetMap.get("ISPANNO") != null)
					  {
						  personalInfoDTO.setIsPanNo(resultSetMap.get("ISPANNO").toString());
					  }
						if(resultSetMap.get("PANNO") != null)
						{
							personalInfoDTO.setPanNo(resultSetMap.get("PANNO").toString());
							
						}
						if(resultSetMap.get("DECLARATION") != null)
						{
							personalInfoDTO.setDeclaration(resultSetMap.get("DECLARATION").toString());
							
						}
						if(resultSetMap.get("DRIVINGLICENSENO") != null)
						{
							personalInfoDTO.setDrivingLicenseNo(resultSetMap.get("DRIVINGLICENSENO").toString());
							
						}
						if(resultSetMap.get("PASSPORTNO") != null)
						{
							personalInfoDTO.setPassportNo(resultSetMap.get("PASSPORTNO").toString());
							
						}
						if(resultSetMap.get("BASIC") != null)
						{
							personalInfoDTO.setBasic(resultSetMap.get("BASIC").toString());						
						}
						if(resultSetMap.get("DA") != null)
						{
							personalInfoDTO.setDa(resultSetMap.get("DA").toString());
							
						}
						if(resultSetMap.get("HRA") != null)
						{
							personalInfoDTO.setHra(resultSetMap.get("HRA").toString());
							
						}
						if(resultSetMap.get("SPALLOWANCES") != null)
						{
							personalInfoDTO.setSpAllowance(resultSetMap.get("SPALLOWANCES").toString());					
						}
						if(resultSetMap.get("MEDICAL_LTA") != null)
						{
							personalInfoDTO.setMedicalLta(resultSetMap.get("MEDICAL_LTA").toString());
							
						}
						if(resultSetMap.get("BONUS_INCENTIVE") != null)
						{
							personalInfoDTO.setBonusIncentive(resultSetMap.get("BONUS_INCENTIVE").toString());
							
						}
						if(resultSetMap.get("OTHERS") != null)
						{
							personalInfoDTO.setOthers(resultSetMap.get("OTHERS").toString());						
						}
						if(resultSetMap.get("GROSSINCOME") != null)
						{
							personalInfoDTO.setGrossIncome(resultSetMap.get("GROSSINCOME").toString());		
							
						}
						if(resultSetMap.get("OTHERINCOME") != null)
						{
							personalInfoDTO.setOtherIncome(resultSetMap.get("OTHERINCOME").toString());		
							
						}
						if(resultSetMap.get("NETINCOME") != null)
						{
							personalInfoDTO.setNetIncome(resultSetMap.get("NETINCOME").toString());		
							
						}
						if(resultSetMap.get("DNSREQUIRED") != null)
						{
							personalInfoDTO.setDnsRequired1(resultSetMap.get("DNSREQUIRED").toString());
							
						}
						if(resultSetMap.get("DNSREASON") != null)
						{
							personalInfoDTO.setDnsReason(resultSetMap.get("DNSREASON").toString());
							
						}
						if(resultSetMap.get("PREFERREDTIMETOCALL") != null)
						{
							personalInfoDTO.setPreferredTimeToCall(resultSetMap.get("PREFERREDTIMETOCALL").toString());
							
						}
						if(resultSetMap.get("PREFERREDMODEOFCOMM") != null)
						{
							personalInfoDTO.setPreferredModeOfCommunication(resultSetMap.get("PREFERREDMODEOFCOMM").toString());
							
						}
						if(resultSetMap.get("RELATION") != null)
						{
							personalInfoDTO.setRelation(resultSetMap.get("RELATION").toString());
							
						}
						if(resultSetMap.get("CUSTOMER_ENTITYTYPE") != null)
						{
							personalInfoDTO.setCustEntityType(resultSetMap.get("CUSTOMER_ENTITYTYPE").toString());
						
						}	
						
						// Added for Dia Vikas
						
						if(resultSetMap.get("RBI_REGISTRATION_NO") != null)
						{
							personalInfoDTO.setRbiRegNo(resultSetMap.get("RBI_REGISTRATION_NO").toString());
							
						}
						
						if(resultSetMap.get("LEGAL_FORM_NAME") != null)
						{
							personalInfoDTO.setLegalFormName(resultSetMap.get("LEGAL_FORM_NAME").toString());
							
						}
						
						if(resultSetMap.get("KEY_CONTACT_PERSON") != null)
						{
							personalInfoDTO.setKeyContactPerson(resultSetMap.get("KEY_CONTACT_PERSON").toString());
							
						}
						
						if(resultSetMap.get("AUTHORIZED_CAPITAL") != null)
						{
							personalInfoDTO.setAuthCapital(resultSetMap.get("AUTHORIZED_CAPITAL").toString());
							
						}
						
						if(resultSetMap.get("ISSUED_CAPITAL") != null)
						{
							personalInfoDTO.setIssuedCap(resultSetMap.get("ISSUED_CAPITAL").toString());
							
						}
						
						if(resultSetMap.get("DIAS_BOARD_REPRESENTATIVE") != null)
						{
							personalInfoDTO.setBoardRepresentative(resultSetMap.get("DIAS_BOARD_REPRESENTATIVE").toString());
							
						}
						if(resultSetMap.get("LEGAL_FORM_TYPE") != null)
						{
							personalInfoDTO.setLegalFormType(resultSetMap.get("LEGAL_FORM_TYPE").toString());
							
						}
						if(resultSetMap.get("SALES_MANAGER") != null)
						{
							personalInfoDTO.setSalesManager(resultSetMap.get("SALES_MANAGER").toString());
							
						}
						if(resultSetMap.get("SALES_MANAGER_ID") != null)
						{
							personalInfoDTO.setSalesManagerId(resultSetMap.get("SALES_MANAGER_ID").toString());
							
						}	
						// Added for Dia Vikas
						if((resultSetMap.get("FOID") != null) && (resultSetMap.get("PHOTOOBJ").equals("IMAGE") || resultSetMap.get("PHOTOOBJ")=="IMAGE"))
						{
							personalInfoDTO.setFileObjectId(resultSetMap.get("FOID").toString());
							
						}
						
						if(resultSetMap.get("PSL_TYPE") != null)
						{
							personalInfoDTO.setPsl(resultSetMap.get("PSL_TYPE").toString());
							
						}
						if(resultSetMap.get("CASTE_CATEGORY") != null)
						{
							personalInfoDTO.setCaste(resultSetMap.get("CASTE_CATEGORY").toString());
							
						}
						if(resultSetMap.get("AADHAARCARDNO") != null)
						{
							personalInfoDTO.setAdharNo(resultSetMap.get("AADHAARCARDNO").toString());
						}
						
						if(resultSetMap.get("TANNO") != null)
						{
							personalInfoDTO.setTanNo(resultSetMap.get("TANNO").toString());
						}
						
						//1.0.0.4 start
						if(resultSetMap.get("KYC_RISK_CATEGORY") != null)
						{
							personalInfoDTO.setKycRiskCategory(resultSetMap.get("KYC_RISK_CATEGORY").toString());
						}
						//1.0.0.4 end
						
						//1.0.0.21
						if(resultSetMap.get("GST_REGISTERED") != null)
						{
							personalInfoDTO.setGstRegistrationStatus(resultSetMap.get("GST_REGISTERED").toString());
						}
						//1.0.0.21
						//1.0.0.24
						if(resultSetMap.get("PAYMENT_TERMS") != null)
						{
							personalInfoDTO.setPaymentTerms(resultSetMap.get("PAYMENT_TERMS").toString());
						}
						//1.0.0.24
						
						
						if(resultSetMap.get("CUSTOMER_SEGMENT") != null)
						{
							personalInfoDTO.setCustomerSegment(resultSetMap.get("CUSTOMER_SEGMENT").toString());
						}
						/*if(resultSetMap.get("ADHAAR_CARD_NO") != null)
						{
							personalInfoDTO.setNatureActivity(resultSetMap.get("ADHAAR_CARD_NO").toString());
						}
						if(resultSetMap.get("ADHAAR_CARD_NO") != null)
						{
							personalInfoDTO.setOccupation(resultSetMap.get("ADHAAR_CARD_NO").toString());
						}*/
						// ravi end
						//, 

						if(resultSetMap.get("GROUP_NAME") != null)
						{
							personalInfoDTO.setGroupName(resultSetMap.get("GROUP_NAME").toString());
						}

						if(resultSetMap.get("GROUP_CODE") != null)
						{
							personalInfoDTO.setGroupCode(resultSetMap.get("GROUP_CODE").toString());
						}
						
						
						
						
						
						if(resultSetMap.get("LOB") != null)
						{
							personalInfoDTO.setLob(resultSetMap.get("LOB").toString());
						}
						
						if(resultSetMap.get("BRANCH") != null)
						{
							personalInfoDTO.setBranch(resultSetMap.get("BRANCH").toString());
						}
						if(resultSetMap.get("SALES_MANAGER_ID") != null)
						{
							personalInfoDTO.setRelationShipManager(resultSetMap.get("SALES_MANAGER_ID").toString());
						}
						if(resultSetMap.get("PRODUCT_ID") != null)
						{
							personalInfoDTO.setProductInstrestedIn(resultSetMap.get("PRODUCT_ID").toString());
						}
						if(resultSetMap.get("ASSET_CATEGORY_ID") != null)
						{
							personalInfoDTO.setAssetCategory(resultSetMap.get("ASSET_CATEGORY_ID").toString());
						}
						if(resultSetMap.get("COMPETITION_ID") != null)
						{
							personalInfoDTO.setCompetition(resultSetMap.get("COMPETITION_ID").toString());
						}
						if(resultSetMap.get("SOURCE_TYPE_ID") != null)
						{
							personalInfoDTO.setChannelType(resultSetMap.get("SOURCE_TYPE_ID").toString());
						}
						if(resultSetMap.get("SOURCE_ID") != null)
						{
							personalInfoDTO.setChannelName(resultSetMap.get("SOURCE_ID").toString());
						}
						if(resultSetMap.get("AGENT_ID") != null)
						{
							personalInfoDTO.setAgentName(resultSetMap.get("AGENT_ID").toString());
						}
						
						
						if(resultSetMap.get("FATHERFNAME") != null)
						{
							personalInfoDTO.setAuthSignatoryFirstName(resultSetMap.get("FATHERFNAME").toString());						
						}
						if(resultSetMap.get("FATHERMNAME") != null)
						{
							personalInfoDTO.setAuthSignatoryMiddleName(resultSetMap.get("FATHERMNAME").toString());						
						}
						if(resultSetMap.get("FATHERLNAME") != null)
						{
							personalInfoDTO.setAuthSignatoryLastName(resultSetMap.get("FATHERLNAME").toString());						
						}
						
						//start by 1.0.0.9
						if(resultSetMap.get("LPI_RATE_TYPE") != null) { 
							personalInfoDTO.setLpiRateType(resultSetMap.get("LPI_RATE_TYPE").toString());						
						}
						if(resultSetMap.get("LPI_RATE") != null) { 
							personalInfoDTO.setLpiRate(resultSetMap.get("LPI_RATE").toString());						
						}
						//end by 1.0.0.9
						//Added by Nalin 1.0.0.10 start
						if(resultSetMap.get("UDYAM_NO") != null) { 
							personalInfoDTO.setUdyamNo(resultSetMap.get("UDYAM_NO").toString());
						}
						//1.0.0.10 End
					
			}
			}
			  
			  
			  
			  
			  
			  
			  
			  //parameterDto = (ParameterDTO)arrParameter.get(5);
				//String flag = (String)parameterDto.getParameterValue();
				//personalInfoDTO.setEditFlag(flag); 
				
				ArrayList keyContactDatalist=(ArrayList)parameterDto2.getParameterValue();
				
				
				map.put("personalInfoDTO", personalInfoDTO);
				if(keyContactDatalist!=null)
				{
				Map resultSetMap2 = null;
				Iterator itr2 = keyContactDatalist.iterator();
				ArrayList keyContactList = new ArrayList();
				if(itr2!=null)
				{
					while(itr2.hasNext())
						
					{										
						resultSetMap2=(Map)itr2.next();
						personalInfoKeyContactDTO= new PersonalInfoDTO();
						
						if(resultSetMap2.get("KEY_CONTACT_ID") != null)
						{
							personalInfoKeyContactDTO.setKeyContactsId(resultSetMap2.get("KEY_CONTACT_ID").toString());					
						}	
						if(resultSetMap2.get("CASE_ID") != null)
						{
							personalInfoKeyContactDTO.setCaseId(resultSetMap2.get("CASE_ID").toString());					
						}
						
						if(resultSetMap2.get("CASE_CODE") != null)
						{
							personalInfoKeyContactDTO.setCaseCode(resultSetMap2.get("CASE_CODE").toString());					
						}
						
						if(resultSetMap2.get("LOB_ID") != null)
						{
							personalInfoKeyContactDTO.setKeyContactlob(resultSetMap2.get("LOB_ID").toString());					
						}	
						if(resultSetMap2.get("CASE_CONTACT_TYPE_ID") != null)
						{
							personalInfoKeyContactDTO.setKeyContactcontactType(resultSetMap2.get("CASE_CONTACT_TYPE_ID").toString());					
						}	
						if(resultSetMap2.get("NAME") != null)
						{
							personalInfoKeyContactDTO.setKeyContactname(resultSetMap2.get("NAME").toString());					
						}	
						if(resultSetMap2.get("FIRM_NAME") != null)
						{
							personalInfoKeyContactDTO.setKeyContactfirmName(resultSetMap2.get("FIRM_NAME").toString());					
						}	
						if(resultSetMap2.get("MOBILE") != null)
						{
							personalInfoKeyContactDTO.setKeyContactmobile(resultSetMap2.get("MOBILE").toString());				
						}	
						if(resultSetMap2.get("EMAIL") != null)
						{
							personalInfoKeyContactDTO.setKeyContactEmailId(resultSetMap2.get("EMAIL").toString());					
						}	
						if(resultSetMap2.get("ADDRESS") != null)
						{
							personalInfoKeyContactDTO.setKeyContactAddress(resultSetMap2.get("ADDRESS").toString());					
						}
						if(resultSetMap.get("INDUSTRY_ID") != null) { 
							personalInfoDTO.setIndustryType(resultSetMap.get("INDUSTRY_ID").toString());						
						}
						
						// Start 1.0.0.22
						if(resultSetMap2.get("UCIC_CODE") != null)
						{
							personalInfoKeyContactDTO.setKeyContactUcicCode(resultSetMap2.get("UCIC_CODE").toString());
						}
						if(resultSetMap2.get("PANNO") != null)
						{
							personalInfoKeyContactDTO.setKeyContactPan(resultSetMap2.get("PANNO").toString());
						}
						// End 1.0.0.22
				
						
						keyContactList.add(personalInfoKeyContactDTO);
					}
					
			}
				
				map.put("keyContactDeatail", keyContactList);
				}
			  
			  
			  
			  
				//start by 1.0.0.19
				ArrayList leiDetails=(ArrayList)parameterDto3.getParameterValue();
				
				if(leiDetails!=null)
				{
				Map resultSetMap4 = null;
				Iterator itr3 = leiDetails.iterator();
				//ArrayList leiDetailsList = new ArrayList();
					if(itr3!=null)
					{
						while(itr3.hasNext())
							
						{										
							resultSetMap4=(Map)itr3.next();
							//leiDetailsDTO= new PersonalInfoDTO();
							
							/*if(resultSetMap4.get("LEI_ID") != null)
							{
								personalInfoDTO.setLeiId(resultSetMap4.get("LEI_ID").toString());					
							}	*/
							if(resultSetMap4.get("LEI_CODE_AVAILABLE") != null)
							{
								personalInfoDTO.setLeiCodeAvailable(resultSetMap4.get("LEI_CODE_AVAILABLE").toString());					
							}
							
							if(resultSetMap4.get("LEI_CODE") != null)
							{
								personalInfoDTO.setLeiCode(resultSetMap4.get("LEI_CODE").toString());					
							}
							
							if(resultSetMap4.get("LEI_VALIDITY") != null)
							{
								personalInfoDTO.setLeiValidity(resultSetMap4.get("LEI_VALIDITY").toString());					
							}
						
							if(resultSetMap4.get("AMOUNT_DECLARATION") != null)
							{
								personalInfoDTO.setAmountOfDeclaration(resultSetMap4.get("AMOUNT_DECLARATION").toString());					
							}
							if(resultSetMap4.get("DECLARATION_VALIDITY") != null)
							{
								personalInfoDTO.setDeclarationValidity(resultSetMap4.get("DECLARATION_VALIDITY").toString());					
							}
							//personalInfoDTO.add(leiDetailsDTO);
	                         }
						
						map.put("personalInfoDTO", personalInfoDTO);
						
					}
				}
							
				//end by 1.0.0.19
				
				//1.0.0.23 start	
		          ArrayList tdsRateSpec=(ArrayList)parameterDto6.getParameterValue();
					
					if(tdsRateSpec!=null)
					{
					Map resultSetMap4 = null;
					Iterator itr3 = tdsRateSpec.iterator();
						if(itr3!=null)
						{
							while(itr3.hasNext())
								
							{										
								resultSetMap4=(Map)itr3.next();
								
								if(resultSetMap4.get("LOWER_TDS_RATE") != null) { 
									personalInfoDTO.setLowerTdsPercent(resultSetMap4.get("LOWER_TDS_RATE").toString());						
								}
								if(resultSetMap4.get("TDS_VALID_FROM") != null) { 
									personalInfoDTO.setLowerTdsRateFrom(resultSetMap4.get("TDS_VALID_FROM").toString());						
								}
								if(resultSetMap4.get("TDS_VALID_TO") != null) { 
									personalInfoDTO.setLowerTdsRateTo(resultSetMap4.get("TDS_VALID_TO").toString());						
								}
		                       }
							
							map.put("personalInfoDTO", personalInfoDTO);
							
						}
					}
								
					//1.0.0.23 end

			  
			  
			  
		}
		  catch (DBConnectionException dce) 
		  {
			  log.error("DBConnectionException - FAIL:");
				log.error("DBConnectionException - Parameter : \r\n1. procName= " + procName + "; \r\n2. colParameter=( " + PrintParameterList.printParameterList(arrParameter) + " );");
		  	log.error("DBConnectionException - "+dce.getMessage());
		  }
		  catch (Exception e) 
		  {
			  log.error("Exception - FAIL:");
				log.error("Exception - Parameter : \r\n1. procName= " + procName + "; \r\n2. colParameter=( " + PrintParameterList.printParameterList(arrParameter) + " );");
		  	log.error("Exception - "+e.getMessage());
		  }
		  log.info("End");
		  return map;
	}
	
	
	
	
	
	public Map savePdePersonalInfo(PersonalInfoForm personalInfoForm,String entityId,String prospectId,String userId,String authMode,Map dbConnectionMap)
	{
		log.info("Start");
		//log.debug("savePdePersonalInfo - Parameter : \r\n                                                                            1. personalInfoForm = ("+personalInfoForm.getAllProperty()+"); \r\n                                                                            2. prospectId = "+prospectId+"; \r\n                                                                            3. userId = "+userId+"; \r\n                                                                            4. authMode = "+authMode+"; \r\n                                                                            5. dbConnectionMap = ("+ PrintDBConnectionMap.printMap(dbConnectionMap) + "); \r\n");
		String dnsReason = "";
		Map map = new HashMap();
		if(personalInfoForm.getDnsReasonTemp().equals(""))
		{
			dnsReason="";
		}
		else
		{
			dnsReason=personalInfoForm.getDnsReasonTemp();
		}

		if(personalInfoForm.getCustEntityType().equals("1000000002"))
		{
			  personalInfoForm.setFirstName(personalInfoForm.getNameOfCompany());				  
			  personalInfoForm.setFatherFirstName(personalInfoForm.getAuthSignatoryFirstName());
			  personalInfoForm.setFatherMiddleName(personalInfoForm.getAuthSignatoryMiddleName());
			  personalInfoForm.setFatherLastName(personalInfoForm.getAuthSignatoryLastName());
			  personalInfoForm.setDateOfBirth(personalInfoForm.getDateOfIncorporation());
			  personalInfoForm.setPanNo(personalInfoForm.getCompanyPan());
			  personalInfoForm.setKycRiskCategory(personalInfoForm.getKycRiskCategory_Corp()); // 1.0.0.4
			  personalInfoForm.setGstRegistrationStatus(personalInfoForm.getGstRegistrationStatus()); //1.0.0.21
					
					personalInfoForm.setLegalFormType(personalInfoForm.getLegalFormTypeCorp());
				
			  
			  /**
			   * Changes For Dia Vikas
			   * 
			   */
			  //FormFile myFile = personalInfoForm.getLegalForm();
			  personalInfoForm.setRbiRegNo(personalInfoForm.getRbiRegNo());
			  personalInfoForm.setKeyContactPerson(personalInfoForm.getKeyContactPerson());
			  personalInfoForm.setAuthCapital(personalInfoForm.getAuthCapital());
			  personalInfoForm.setIssuedCap(personalInfoForm.getIssuedCap());
			  personalInfoForm.setBoardRepresentative(personalInfoForm.getBoardRepresentative());
			  //System.out.println("==== "+personalInfoForm.getAuthCapital());
			  //System.out.println("==== "+personalInfoForm.getIssuedCap());
			  /**
			   * Changes For Dia Vikas End ------
			   * 
			   */
			  
			  personalInfoForm.setMiddleName("");
			  personalInfoForm.setLastName("");
			  personalInfoForm.setMaritalStatus("");
			  personalInfoForm.setGender("");
			  personalInfoForm.setSelGender("");
			  personalInfoForm.setMotherFirstName("");
			  personalInfoForm.setMotherMiddleName("");
			  personalInfoForm.setMotherLastName("");
			  personalInfoForm.setSpouseFirstName("");
			  personalInfoForm.setSpouseMiddleName("");
			  personalInfoForm.setSpouseLastName("");
			  personalInfoForm.setNoOfDependents("");
			  if(!entityId.equalsIgnoreCase("1000000008"))
			  {
				  personalInfoForm.setCustCategory("");  
			  }
			  
		}
			  
			  else if(personalInfoForm.getCustEntityType().equals("1000000001"))
				{
					
					personalInfoForm.setLegalFormType(personalInfoForm.getLegalFormTypeInd());
				}
			
		
		DAL dal=new DAL();
		int parameterCount=0;
		ArrayList arrParameter=new ArrayList();
		String procName=null;
		ParameterDTO parameterDtoMessage= null;
		ParameterDTO parameterDtoResult= null;
		// This code is written for handling transaction management
		Connection connection=null;
		
		
		if(dbConnectionMap.get("DB_SERVER").toString().equals("ORACLE"))
		{
			procName="{call Pkg_LMS_APPLICANT_MASTER.Pr_App_Master_Transaction(?,?,?,?,?,?,?, ?,?,?,?,?, ?,?,?,?,?, ?,?,?,?,?, ?,?,?,?,?, ?,?,?,?,?, ?,?,?,?,?, ?,?,?,?,?, ?,?,?,?,?, ?,?,?,?,?, ?,?,?,?,?, ?,?,?,?,?, ?,?,?,?,?, ?,?,?,?,?,?, ?,?,?,?,?,? ,?,?,? ,?,?,?,?,? ,?,?,?,?,?,?,?, ?,?)}";//1.0.0.9
			arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,personalInfoForm.getApplicantID()));
			arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,personalInfoForm.getApplicantCode()));
			arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,personalInfoForm.getApplicantType()));
			arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,personalInfoForm.getTitle()));
			arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,personalInfoForm.getFirstName()));
			arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,personalInfoForm.getMiddleName()));
			arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,personalInfoForm.getLastName()));
			arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,personalInfoForm.getReligion()));
			arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,personalInfoForm.getMaritalStatus()));
			arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,personalInfoForm.getDateOfBirth()));
			arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,personalInfoForm.getGender()));
			arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,personalInfoForm.getNoOfDependents()!=null&&!"".equalsIgnoreCase(personalInfoForm.getNoOfDependents())?personalInfoForm.getNoOfDependents():"0"));
			arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,personalInfoForm.getFatherTitle()));
			arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,personalInfoForm.getFatherFirstName()));
			arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,personalInfoForm.getFatherMiddleName()));
			arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,personalInfoForm.getFatherLastName()));
			arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,personalInfoForm.getMotherFirstName()));
			arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,personalInfoForm.getMotherMiddleName()));
			arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,personalInfoForm.getMotherLastName()));
			arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,personalInfoForm.getSpouseTitle()));
			arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,personalInfoForm.getSpouseFirstName()));
			arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,personalInfoForm.getSpouseMiddleName()));
			arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,personalInfoForm.getSpouseLastName()));
			arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,personalInfoForm.getSpouseOccupation()));
			arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,personalInfoForm.getSpouseNameOfCompany()));
			arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,personalInfoForm.getConstitution()));
			arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,personalInfoForm.getCustCategory()));
			arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,personalInfoForm.getQualificationId()));
			arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,personalInfoForm.getHighestQualification()));
			arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,personalInfoForm.getDegree()));
			arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,personalInfoForm.getCollegeLastAttended()));
			arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,personalInfoForm.getNationality()));
			arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,personalInfoForm.getNlFirstName()));
			arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,personalInfoForm.getNlSecondName()));
			arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,personalInfoForm.getNlLastName()));
			arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,personalInfoForm.getVoterId()));
			arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,personalInfoForm.getIsPanNo()));
			arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,personalInfoForm.getPanNo()));
			arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,personalInfoForm.getDeclaration()));
			arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,personalInfoForm.getDrivingLicenseNo()));
			arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,personalInfoForm.getPassportNo()));
			arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,personalInfoForm.getBasic()));
			arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,personalInfoForm.getDa()));
			arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,personalInfoForm.getHra()));
			arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,personalInfoForm.getSpAllowance()));
			arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,personalInfoForm.getMedicalLta()));
			arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,personalInfoForm.getBonusIncentive()));
			arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,personalInfoForm.getOthers()));
			arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,personalInfoForm.getGrossIncome()));
			arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,personalInfoForm.getOtherIncome()));
			arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,personalInfoForm.getNetIncome()));
			arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,personalInfoForm.getDnsRequired1()));
			arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,dnsReason));
			arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,personalInfoForm.getPreferredTimeToCall()));
			arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,personalInfoForm.getPreferredModeOfCommunication()));
			arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,""));
			arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,userId+""));
			arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,authMode!=null&&(!("".equalsIgnoreCase(authMode)))?authMode:""));
			arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,prospectId));
			arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,personalInfoForm.getRelation()));
			arrParameter.add(new ParameterDTO(++parameterCount,"OUT",oracle.jdbc.OracleTypes.VARCHAR,null));
			arrParameter.add(new ParameterDTO(++parameterCount,"OUT",oracle.jdbc.OracleTypes.VARCHAR,null));
			arrParameter.add(new ParameterDTO(++parameterCount,"IN OUT",oracle.jdbc.OracleTypes.VARCHAR,""));
			arrParameter.add(new ParameterDTO(++parameterCount,"OUT",oracle.jdbc.OracleTypes.VARCHAR,null));
			arrParameter.add(new ParameterDTO(++parameterCount,"OUT",oracle.jdbc.OracleTypes.VARCHAR,null));
			arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,personalInfoForm.getCustEntityType()));
			
			// Added for Dia Vikas
		 	arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,personalInfoForm.getRbiRegNo()));
			
			if(personalInfoForm.getLegalForm().getFileName().trim().length()<1)
			{
			 	arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.BLOB,personalInfoForm.getTheFile()));
			 	arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,null));
			}
			else
			{
				arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.BLOB,personalInfoForm.getTheFile()));
			 	arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,personalInfoForm.getLegalForm().getFileName()));
			}
		 	arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,personalInfoForm.getKeyContactPerson()));
		 	arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,personalInfoForm.getAuthCapital()));
		 	arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,personalInfoForm.getIssuedCap()));
		 	arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,personalInfoForm.getBoardRepresentative()));
		 	arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,personalInfoForm.getLegalFormType()));
		 	arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,personalInfoForm.getSalesManager()));
			// Added for Dia Vikas			
		 	arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,personalInfoForm.getPsl()));
		 	arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,personalInfoForm.getCaste()));
		 	arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,personalInfoForm.getAdharNo()));
		 	
		 	
		 	/// dm pde changes
		 	arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,entityId));
		 	
		   arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,personalInfoForm.getCustomerSegment())); // ravi
           arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,personalInfoForm.getIndustry()));
           arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,personalInfoForm.getSector()));
           arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,personalInfoForm.getWebsite()));
           arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,personalInfoForm.getProductInstrestedIn()));
           arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,personalInfoForm.getAssetCategory()));
           arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,personalInfoForm.getCompetition()));
           arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,personalInfoForm.getChannelType()));
           arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,personalInfoForm.getChannelName()));
           arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,personalInfoForm.getAgentName()));
           arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,personalInfoForm.getBranch()));
           
           if(personalInfoForm.getCustEntityType().equals("1000000001")){
               
               arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,personalInfoForm.getLob()));
               }
               else if(personalInfoForm.getCustEntityType().equals("1000000002")){
               arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,personalInfoForm.getLobCorp()));
               }
           if(personalInfoForm.getApplicantType().equalsIgnoreCase("1000000002")){
           
           arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,null));
              }else{
            	  arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,personalInfoForm.getKeyContactDataStr()));  
              }
              if(personalInfoForm.getCustEntityType().equalsIgnoreCase("1000000002"))
			 	{
			 	 arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,personalInfoForm.getCorpTanNo()));
			 	}
			 	else
			 	{
			 		arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,personalInfoForm.getTanNo()));
			 	}
              arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,personalInfoForm.getKycRiskCategory())); // 1.0.0.4
		 	
              arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,personalInfoForm.getLpiRateType())); //1.0.0.9
              arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,personalInfoForm.getLpiRate())); //1.0.0.9
		}
		else if(dbConnectionMap.get("DB_SERVER").toString().equals("MSSQLSERVER"))
		{
			procName="{call QC_LMS_FINAL.DBO.LmsApplicantMaster_PrAppMasterTransaction(?,?,?,?,?,?, ?,?,?,?,?, ?,?,?,?,?, ?,?,?,?,?, ?,?,?,?,?, ?,?,?,?,?, ?,?,?,?,?, ?,?,?,?,?, ?,?,?,?,?, ?,?,?,?,?, ?,?,?,?,?, ?,?,?,?,?, ?,?,?,?,?)}";
			arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getApplicantID()));
			arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getApplicantCode()));
			arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getApplicantType()));
			arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getTitle()));
			arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getFirstName()));
			arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getMiddleName()));
			arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getLastName()));
			arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getReligion()));
			arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getMaritalStatus()));
			arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getDateOfBirth()));
			arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getGender()));
			arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getNoOfDependents()!=null&&!"".equalsIgnoreCase(personalInfoForm.getNoOfDependents())?personalInfoForm.getNoOfDependents():"0"));
			arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getFatherTitle()));
			arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getFatherFirstName()));
			arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getFatherMiddleName()));
			arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getFatherLastName()));
			arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getMotherFirstName()));
			arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getMotherMiddleName()));
			arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getMotherLastName()));
			arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getSpouseTitle()));
			arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getSpouseFirstName()));
			arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getSpouseMiddleName()));
			arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getSpouseLastName()));
			arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getSpouseOccupation()));
			arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getSpouseNameOfCompany()));
			arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getConstitution()));
			arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getCustCategory()));
			arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getEduQualification()));
			arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getHighestQualification()));
			arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getDegree()));
			arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getCollegeLastAttended()));
			arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getNationality()));
			arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getNlFirstName()));
			arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getNlSecondName()));
			arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getNlLastName()));
			arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getVoterId()));
			arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getIsPanNo()));
			arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getPanNo()));
			arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getDeclaration()));
			arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getDrivingLicenseNo()));
			arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getPassportNo()));
			arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getBasic()));
			arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getDa()));
			arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getHra()));
			arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getSpAllowance()));
			arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getMedicalLta()));
			arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getBonusIncentive()));
			arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getOthers()));
			arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getGrossIncome()));
			arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getOtherIncome()));
			arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getNetIncome()));
			arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getDnsRequired()));
			arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,dnsReason));
			arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getPreferredTimeToCall()));
			arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getPreferredModeOfCommunication()));
			arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,""));
			arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,userId+""));
			arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,authMode!=null&&(!("".equalsIgnoreCase(authMode)))?authMode:""));
			arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,prospectId));
			arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getRelation()));
			arrParameter.add(new ParameterDTO(++parameterCount,"OUTPUT",java.sql.Types.VARCHAR,null));
			arrParameter.add(new ParameterDTO(++parameterCount,"OUTPUT",java.sql.Types.VARCHAR,null));
			arrParameter.add(new ParameterDTO(++parameterCount,"OUTPUT",java.sql.Types.VARCHAR,""));
			arrParameter.add(new ParameterDTO(++parameterCount,"OUTPUT",java.sql.Types.VARCHAR,null));
			arrParameter.add(new ParameterDTO(++parameterCount,"OUTPUT",java.sql.Types.VARCHAR,null));
			arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getCustEntityType()));
		}
		else if(dbConnectionMap.get("DB_SERVER").toString().equals("MYSQL"))
		{
			procName="{call Pkg_LMS_APPLICANT_MASTER.Pr_App_Master_Transaction(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
			  arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.INTEGER,personalInfoForm.getApplicantID()!=null&&!"".equals(personalInfoForm.getApplicantID())?Integer.parseInt(personalInfoForm.getApplicantID()):0));
			  arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getApplicantCode()));
			  arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.INTEGER,personalInfoForm.getApplicantType()!=null&&!"".equals(personalInfoForm.getApplicantType())?Integer.parseInt(personalInfoForm.getApplicantType()):0));
			  arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getTitle()));
			  arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getFirstName()));
			  arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getMiddleName()));
			  arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getLastName()));
			  arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getReligion()));
			  arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.INTEGER,personalInfoForm.getMaritalStatus()!=null&&!"".equals(personalInfoForm.getMaritalStatus())?Integer.parseInt(personalInfoForm.getMaritalStatus()):0));
			  arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getDateOfBirth()));
			  arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getGender()));
			  arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.INTEGER,personalInfoForm.getNoOfDependents()!=null&&!"".equals(personalInfoForm.getNoOfDependents())?Integer.parseInt(personalInfoForm.getNoOfDependents()):0));
			  arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getFatherTitle()));
			  arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getFatherFirstName()));
			  arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getFatherMiddleName()));
			  arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getFatherLastName()));
			  arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getMotherFirstName()));
			  arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getMotherMiddleName()));
			  arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getMotherLastName()));
			  arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getSpouseTitle()));
			  arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getSpouseFirstName()));
			  arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getSpouseMiddleName()));
			  arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getSpouseLastName()));
			  arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getSpouseOccupation()));
			  arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getSpouseNameOfCompany()));
			  arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.INTEGER,personalInfoForm.getConstitution()!=null&&!"".equals(personalInfoForm.getConstitution())?Integer.parseInt(personalInfoForm.getConstitution()):0));
			  arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getCustCategory()));
			  arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.INTEGER,personalInfoForm.getEduQualification()!=null&&!"".equals(personalInfoForm.getEduQualification())?Integer.parseInt(personalInfoForm.getEduQualification()):0));
			  arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.INTEGER,personalInfoForm.getHighestQualification()!=null&&!"".equals(personalInfoForm.getHighestQualification())?Integer.parseInt(personalInfoForm.getHighestQualification()):0));
			  arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getDegree()));
			  arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getCollegeLastAttended()));
			  arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getNationality()));
			  arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getNlFirstName()));
			  arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getNlSecondName()));
			  arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getNlLastName()));
			  arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getVoterId()));
			  arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getIsPanNo()));
			  arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getPanNo()));
			  arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getDeclaration()));
			  arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getDrivingLicenseNo()));
			  arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getPassportNo()));
			  arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.INTEGER,personalInfoForm.getBasic()!=null&&!"".equals(personalInfoForm.getBasic())?Integer.parseInt(personalInfoForm.getBasic()):0));
			  arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.INTEGER,personalInfoForm.getDa()!=null&&!"".equals(personalInfoForm.getDa())?Integer.parseInt(personalInfoForm.getDa()):0));
			  arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.INTEGER,personalInfoForm.getHra()!=null&&!"".equals(personalInfoForm.getHra())?Integer.parseInt(personalInfoForm.getHra()):0));
			  arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.INTEGER,personalInfoForm.getSpAllowance()!=null&&!"".equals(personalInfoForm.getSpAllowance())?Integer.parseInt(personalInfoForm.getSpAllowance()):0));
			  arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.INTEGER,personalInfoForm.getMedicalLta()!=null&&!"".equals(personalInfoForm.getMedicalLta())?Integer.parseInt(personalInfoForm.getMedicalLta()):0));
			  arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.INTEGER,personalInfoForm.getBonusIncentive()!=null&&!"".equals(personalInfoForm.getBonusIncentive())?Integer.parseInt(personalInfoForm.getBonusIncentive()):0));
			  arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.INTEGER,personalInfoForm.getOthers()!=null&&!"".equals(personalInfoForm.getOthers())?Integer.parseInt(personalInfoForm.getOthers()):0));
			  arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.INTEGER,personalInfoForm.getGrossIncome()!=null&&!"".equals(personalInfoForm.getGrossIncome())?Integer.parseInt(personalInfoForm.getGrossIncome()):0));
			  arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.INTEGER,personalInfoForm.getOtherIncome()!=null&&!"".equals(personalInfoForm.getOtherIncome())?Integer.parseInt(personalInfoForm.getOtherIncome()):0));
			  arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.INTEGER,personalInfoForm.getNetIncome()!=null&&!"".equals(personalInfoForm.getNetIncome())?Integer.parseInt(personalInfoForm.getNetIncome()):0));
			  arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getDnsRequired()));
			  arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getDnsReason()));
			  arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getPreferredTimeToCall()));
			  arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",java.sql.Types.VARCHAR,personalInfoForm.getPreferredModeOfCommunication()));
			  arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",oracle.jdbc.OracleTypes.VARCHAR,""));
			  arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",oracle.jdbc.OracleTypes.VARCHAR,userId+""));
			  arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",oracle.jdbc.OracleTypes.VARCHAR,authMode!=null&&(!("".equalsIgnoreCase(authMode)))?authMode:""));
			  arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",oracle.jdbc.OracleTypes.VARCHAR,prospectId));
			  arrParameter.add(new ParameterDTO(++parameterCount,"INPUT",oracle.jdbc.OracleTypes.VARCHAR,personalInfoForm.getRelation()));
			  arrParameter.add(new ParameterDTO(++parameterCount,"OUTPUT",oracle.jdbc.OracleTypes.VARCHAR,null));
			  arrParameter.add(new ParameterDTO(++parameterCount,"OUTPUT",oracle.jdbc.OracleTypes.VARCHAR,null));
			  arrParameter.add(new ParameterDTO(++parameterCount,"OUTPUT",oracle.jdbc.OracleTypes.VARCHAR,""));
			  arrParameter.add(new ParameterDTO(++parameterCount,"OUTPUT",oracle.jdbc.OracleTypes.VARCHAR,null));

		}
		 try
		 {
			 int applicantId;
			 connection=DBConnection.getConnection(dbConnectionMap);
			 connection.setAutoCommit(false);
			 dal.processSPROC(procName, arrParameter,dbConnectionMap,connection);
			 parameterDtoResult = (ParameterDTO)arrParameter.get(62);
			 parameterDtoMessage = (ParameterDTO)arrParameter.get(63);
			 map.put("msg",(String)parameterDtoMessage.getParameterValue()); 
			 if("S".equalsIgnoreCase((String)parameterDtoResult.getParameterValue()))
			 {
				 DBConnection.commitTransaction(connection);
			 }
			 else
			 {
				 log.error("processSPROC - FAIL:");
				 log.error("processSPROC - Parameter : \r\n1. procName= " + procName + "; \r\n2. colParameter=( " + PrintParameterList.printParameterList(arrParameter) + " );");
				 DBConnection.rollbackTransaction(connection);
			 }
			
			 if(personalInfoForm.getApplicantID().equals("0"))
			 {
				 applicantId=Integer.parseInt(personalInfoForm.getApplicantID());
				 
			 }
			 else
			 {
				 ParameterDTO parameterDtoId = (ParameterDTO)arrParameter.get(64);
				 applicantId = Integer.parseInt(parameterDtoId.getParameterValue().toString());
			 }
			 map.put("applicantId", applicantId); 
			 
			 
			 //ParameterDTO parameterDtoId = (ParameterDTO)arrParameter.get(64);
			 
//			 if(parameterDtoId.getParameterValue()!=null)
//			 {
				  //applicantId = Integer.parseInt(parameterDtoId.getParameterValue().toString());
				 
//			 }
			 
			
			String result = (String)parameterDtoResult.getParameterValue();
			
			map.put("result", result);
			
		 }	
		 catch (DBConnectionException dce) 
		 {
			 log.error("DBConnectionException - FAIL:");
				log.error("DBConnectionException - Parameter : \r\n1. procName= " + procName + "; \r\n2. colParameter=( " + PrintParameterList.printParameterList(arrParameter) + " );");
		 	log.error("DBConnectionException - "+dce.getMessage());
		 }
		 catch (SQLException sqle) 
		 {
			 log.error("SQLException - FAIL:");
				log.error("SQLException - Parameter : \r\n1. procName= " + procName + "; \r\n2. colParameter=( " + PrintParameterList.printParameterList(arrParameter) + " );");
				log.error("SQLException - "+sqle.getMessage());
		 }
		 catch (Exception e) 
		 {
			 log.error("Exception - FAIL:");
				log.error("Exception - Parameter : \r\n1. procName= " + procName + "; \r\n2. colParameter=( " + PrintParameterList.printParameterList(arrParameter) + " );");
		 	log.error("Exception - "+e.getMessage());
		 }
		 
		finally
		{
			DBConnection.closeConnection(connection);
		}
		log.info("End");
		return map;
	}
	
	
	// code added by ankit gaur::5-dec-2012:: Authorised Signatory 
	
	public Map saveAuthorisedSignatoryDetails(String removeAuthSingDataStr,String userId,String custId,String strData, Map dbConnectionMap)
	{
		log.info("Start");
		Map returnMap = null;
		int parameterCount = 0;
		String procName = null;
		ParameterDTO parameterDto = null;

		String status = "";
		String code = "";
		String message = "";
		String caseInfo = "";
		String tranchInfo = "";
		DAL dal=new DAL();
		ArrayList arrParameter = new ArrayList();
		Connection connection = null;
		if (dbConnectionMap.get("DB_SERVER").toString().equals("ORACLE"))
		{
			procName = "{call PKG_LOS_AUTHORIZED_SIGNATORY.PR_SAVE_SIGNATORY_DET(?,?,?,?,?,?)}";
			arrParameter.add(new ParameterDTO(++parameterCount, "IN", oracle.jdbc.OracleTypes.VARCHAR, strData));
			arrParameter.add(new ParameterDTO(++parameterCount, "IN", oracle.jdbc.OracleTypes.VARCHAR, custId));	
			//arrParameter.add(new ParameterDTO(++parameterCount, "IN", oracle.jdbc.OracleTypes.VARCHAR, "^"));
			//arrParameter.add(new ParameterDTO(++parameterCount, "IN", oracle.jdbc.OracleTypes.VARCHAR, "~"));
			arrParameter.add(new ParameterDTO(++parameterCount, "OUT", oracle.jdbc.OracleTypes.VARCHAR, null));			
			arrParameter.add(new ParameterDTO(++parameterCount, "OUT", oracle.jdbc.OracleTypes.VARCHAR, null));
			arrParameter.add(new ParameterDTO(++parameterCount, "IN", oracle.jdbc.OracleTypes.VARCHAR, removeAuthSingDataStr));//1.0.0.7
			arrParameter.add(new ParameterDTO(++parameterCount, "IN", oracle.jdbc.OracleTypes.VARCHAR, userId));//1.0.0.7
		}
		
		try
		{
			connection=DBConnection.getConnection(dbConnectionMap);
			connection.setAutoCommit(false);
			//new DAL().processSPROC(procName, arrParameter, dbConnectionMap);
			dal.processSPROC(procName, arrParameter,dbConnectionMap,connection);
			status =  ((ParameterDTO) arrParameter.get(2)).getParameterValue().toString();
			//code =    ((ParameterDTO) arrParameter.get(4)).getParameterValue().toString();
			message = ((ParameterDTO) arrParameter.get(3)).getParameterValue().toString();
			
			
			if ("S".equalsIgnoreCase(status))
			{
				DBConnection.commitTransaction(connection);
				status = "S";
				code = "SAVED";
				message = "Data Insert Successfully";
			}
			else
			{
				status = "F";
				code = "FAILURE";
				message = "Data Insertion for Authorised Signatory Failed";
				log.error("processSPROC - FAIL:");
				log.error("processSPROC - Parameter : \r\n1. procName= " + procName + "; \r\n2. colParameter=( " + PrintParameterList.printParameterList(arrParameter) + " );");
			}
		}
		catch (DBConnectionException dce)
		{
			DBConnection.rollbackTransaction(connection);
			log.error("DBConnectionException  -  " + dce.getMessage());
			StackTraceElement[] elements = new Throwable().getStackTrace();
        	String calleeMethod = elements[0].getMethodName();	        	        
        	log.error("DBConnectionException - FAIL:");
			log.error("DBConnectionException - Parameter : \r\n1. procName= " + procName + "; \r\n2. colParameter=( " + PrintParameterList.printParameterList(arrParameter) + " );");
		
		}
		catch (Exception e)
		{
			status = "F";
			code = "FAILURE";
			message = e.getMessage();
			StackTraceElement[] elements = new Throwable().getStackTrace();
        	String calleeMethod = elements[0].getMethodName();	        	        
        	log.error("Exception - FAIL:");
			log.error("Exception - Parameter : \r\n1. procName= " + procName + "; \r\n2. colParameter=( " + PrintParameterList.printParameterList(arrParameter) + " );");
		log.error("Exception - "+e.getMessage());
		}
		finally
		{
			DBConnection.closeConnection(connection);
		}

		returnMap = new HashMap();
		returnMap.put("BO_STATUS", status);
		returnMap.put("BO_CODE", code);
		returnMap.put("BO_MESSAGE", message);
		log.info("End");
		return returnMap;
	}
	
	
	public Map saveAuthorisedSignatoryDetailsPDE(String custId,String entityId,String xmlString, Map dbConnectionMap)
	{
		log.info("Start");
		Map returnMap = null;
		int parameterCount = 0;
		String procName = null;
		ParameterDTO parameterDto = null;

		String status = "";
		String code = "";
		String message = "";
		String caseInfo = "";
		String tranchInfo = "";
		DAL dal=new DAL();
		ArrayList arrParameter = new ArrayList();
		Connection connection = null;
		if (dbConnectionMap.get("DB_SERVER").toString().equals("ORACLE"))
		{
			procName = "{call PKG_LOS_AUTHORIZED_SIGNATORY.PR_SAVE_PDE_SIGNATORY_DET(?,?,?,?,?)}";
			arrParameter.add(new ParameterDTO(++parameterCount, "IN", oracle.jdbc.OracleTypes.CLOB, xmlString));
			arrParameter.add(new ParameterDTO(++parameterCount, "IN", oracle.jdbc.OracleTypes.VARCHAR, custId));			
			arrParameter.add(new ParameterDTO(++parameterCount, "OUT", oracle.jdbc.OracleTypes.VARCHAR, null));			
			arrParameter.add(new ParameterDTO(++parameterCount, "OUT", oracle.jdbc.OracleTypes.VARCHAR, null));
			arrParameter.add(new ParameterDTO(++parameterCount, "OUT", oracle.jdbc.OracleTypes.VARCHAR, entityId));
		}
		
		try
		{
			connection=DBConnection.getConnection(dbConnectionMap);
			connection.setAutoCommit(false);
			//new DAL().processSPROC(procName, arrParameter, dbConnectionMap);
			dal.processSPROC(procName, arrParameter,dbConnectionMap,connection);
			status =  ((ParameterDTO) arrParameter.get(2)).getParameterValue().toString();
			//code =    ((ParameterDTO) arrParameter.get(4)).getParameterValue().toString();
			message = ((ParameterDTO) arrParameter.get(3)).getParameterValue().toString();
			
			
			if ("S".equalsIgnoreCase(status))
			{
				DBConnection.commitTransaction(connection);
				status = "S";
				code = "SAVED";
				message = "Data Insert Successfully";
			}
			else
			{
				status = "F";
				code = "FAILURE";
				message = "Data Insertion for Authorised Signatory Failed";
				log.error("processSPROC - FAIL:");
				log.error("processSPROC - Parameter : \r\n1. procName= " + procName + "; \r\n2. colParameter=( " + PrintParameterList.printParameterList(arrParameter) + " );");
			}
		}
		catch (DBConnectionException dce)
		{
			DBConnection.rollbackTransaction(connection);
			log.error("DBConnectionException  -  " + dce.getMessage());
			StackTraceElement[] elements = new Throwable().getStackTrace();
        	String calleeMethod = elements[0].getMethodName();	        	        
        	log.error("DBConnectionException - FAIL:");
			log.error("DBConnectionException - Parameter : \r\n1. procName= " + procName + "; \r\n2. colParameter=( " + PrintParameterList.printParameterList(arrParameter) + " );");
		
		}
		catch (Exception e)
		{
			status = "F";
			code = "FAILURE";
			message = e.getMessage();
			StackTraceElement[] elements = new Throwable().getStackTrace();
        	String calleeMethod = elements[0].getMethodName();	        	        
        	log.error("Exception - FAIL:");
			log.error("Exception - Parameter : \r\n1. procName= " + procName + "; \r\n2. colParameter=( " + PrintParameterList.printParameterList(arrParameter) + " );");
		log.error(" Exception - "+e.getMessage());
		}
		finally
		{
			DBConnection.closeConnection(connection);
		}

		returnMap = new HashMap();
		returnMap.put("BO_STATUS", status);
		returnMap.put("BO_CODE", code);
		returnMap.put("BO_MESSAGE", message);
		log.info("End");
		return returnMap;
	}
///////////////////////////////////   Display charge details
	
public String getAuthorisedSignatoryDetails(String custId,Map dbConnectionMap)
{
	log.info("Start");
int parameterCount = 0;
String procName = null;
ParameterDTO parameterDto = null;

String signatoryDetail = "";

String testXML = "";
String status = "";
String code = "";
String message = "";

				ArrayList arrParameter = new ArrayList();
				if (dbConnectionMap.get("DB_SERVER").toString().equals("ORACLE"))
				{
					procName = "{call PKG_LOS_AUTHORIZED_SIGNATORY.PR_GET_SIGNATORY_DET(?,?,?,?)}";
					
					arrParameter.add(new ParameterDTO(++parameterCount, "IN", oracle.jdbc.OracleTypes.VARCHAR, custId));
					arrParameter.add(new ParameterDTO(++parameterCount, "OUT", oracle.jdbc.OracleTypes.CLOB, null));
					arrParameter.add(new ParameterDTO(++parameterCount, "OUT", oracle.jdbc.OracleTypes.VARCHAR, null));
					arrParameter.add(new ParameterDTO(++parameterCount, "OUT", oracle.jdbc.OracleTypes.VARCHAR, null));
				}
			

				try
				{
					new DAL().processSPROC(procName, arrParameter, dbConnectionMap);
					
					status = (String)((ParameterDTO) arrParameter.get(2)).getParameterValue();
					message = (String)((ParameterDTO) arrParameter.get(3)).getParameterValue();
					
					if ("F".equalsIgnoreCase(status))
					{
					}
					else if ("S".equalsIgnoreCase(status))
					{
					parameterDto = (ParameterDTO) arrParameter.get(1);
					if (!Utils.nullOrBlank(parameterDto.getParameterValue()))
					{
						signatoryDetail = parameterDto.getParameterValue().toString();
						signatoryDetail = Utils.removeXMLHeader(signatoryDetail);
					}
					}
					else
					{
						status = "F";
						code = "NODATA";
						message = "No Data Found";
						log.error("processSPROC - FAIL:");
						log.error("processSPROC - Parameter : \r\n1. procName= " + procName + "; \r\n2. colParameter=( " + PrintParameterList.printParameterList(arrParameter) + " );");
					}
				}catch (Exception e){
					
					StackTraceElement[] elements = new Throwable().getStackTrace();
					String calleeMethod = elements[0].getMethodName();
					//String callerMethodName = elements[1].getMethodName(); 	        
					log.error(" Exception - "+e.getMessage());
					status = "F";
					code = "UNKNOWN";
					message = e.getMessage();
					log.error("Exception - FAIL:");
					log.error("Exception - Parameter : \r\n1. procName= " + procName + "; \r\n2. colParameter=( " + PrintParameterList.printParameterList(arrParameter) + " );");
				}
				return signatoryDetail;
	}
public String checkPanNoUniqueness(String panNo,Map dbConnectionMap)
{
    log.info("Start");
    //log.debug("checkReceiptNumber - Parameter : \r\n                                                                            1. receiptNumber = "+receiptNumber+"; \r\n                                                                            2. dbConnectionMap = ("+PrintDBConnectionMap.printMap(dbConnectionMap)+"); \r\n");
    DAL dal=new DAL();
    int parameterCount=0;
    String customerId = "";
    ArrayList arrParameter=new ArrayList();
    String procName=null;
    String value="NA";
    ParameterDTO parameterDto = null;
    ParameterDTO parameterDtoStatus  = null;
    if(dbConnectionMap.get("DB_SERVER").toString().equals("ORACLE"))
    {
            procName="{call QC_LOS.PKG_LOS_TRN_APPLICANT_MASTER.PR_VALIDATE_APPLICANT_PAN(?,?,?)}";
             arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,Utils.nullOrBlank(panNo)?"":panNo.toUpperCase()));
             arrParameter.add(new ParameterDTO(++parameterCount,"OUT",oracle.jdbc.OracleTypes.VARCHAR,null));
             arrParameter.add(new ParameterDTO(++parameterCount,"OUT",oracle.jdbc.OracleTypes.VARCHAR,null));
    }
    try
    {
        dal.processSPROC(procName, arrParameter,dbConnectionMap);
        parameterDto = (ParameterDTO)arrParameter.get(1);
        parameterDtoStatus = (ParameterDTO)arrParameter.get(2);
        customerId = (String)parameterDto.getParameterValue();
        String status=(String)parameterDtoStatus.getParameterValue();
      
    }
    catch (DBConnectionException dce)
    {
        log.error("DBConnectionException - FAIL:");
        log.error("DBConnectionException - Parameter : \r\n1. procName= " + procName + "; \r\n2. colParameter=( " + PrintParameterList.printParameterList(arrParameter) + " );");
        log.error("DBConnectionException - "+dce.getMessage());
    }
    catch (Exception e)
    {
        log.error("Exception - FAIL:");
        log.error("Exception - Parameter : \r\n1. procName= " + procName + "; \r\n2. colParameter=( " + PrintParameterList.printParameterList(arrParameter) + " );");
        log.error("Exception - "+e.getMessage());
    }
    log.info("End");
   
   
     return customerId;
    
}

public Map adharOkycCompFlagChange(String applicantId,Map dbConnectionMap)
{
    log.info("Start");
    //log.debug("checkReceiptNumber - Parameter : \r\n                                                                            1. receiptNumber = "+receiptNumber+"; \r\n                                                                            2. dbConnectionMap = ("+PrintDBConnectionMap.printMap(dbConnectionMap)+"); \r\n");
    DAL dal=new DAL();
    Map returnMap=null;
    int parameterCount=0;
    String status = "";
    String message = "";
    ArrayList arrParameter=new ArrayList();
    String procName=null;
    ParameterDTO parameterDtoMsg = null;
    ParameterDTO parameterDtoStatus  = null;
    if(dbConnectionMap.get("DB_SERVER").toString().equals("ORACLE"))
    {
            procName="{call QC_LOS.PKG_LOS_TRN_APPLICANT_MASTER. PR_UPDATE_AADHAAR_OKYC_COMPLETION(?,?,?)}";
             arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,applicantId));
             arrParameter.add(new ParameterDTO(++parameterCount,"OUT",oracle.jdbc.OracleTypes.VARCHAR,null));
             arrParameter.add(new ParameterDTO(++parameterCount,"OUT",oracle.jdbc.OracleTypes.VARCHAR,null));
    }
    try
    {
        dal.processSPROC(procName, arrParameter,dbConnectionMap);
        parameterDtoMsg = (ParameterDTO)arrParameter.get(1);
        parameterDtoStatus = (ParameterDTO)arrParameter.get(2);
        status = (String)parameterDtoMsg.getParameterValue();
        message=(String)parameterDtoStatus.getParameterValue();
      
    }
    catch (DBConnectionException dce)
    {
        log.error("DBConnectionException - FAIL:");
        log.error("DBConnectionException - Parameter : \r\n1. procName= " + procName + "; \r\n2. colParameter=( " + PrintParameterList.printParameterList(arrParameter) + " );");
        log.error("DBConnectionException - "+dce.getMessage());
    }
    catch (Exception e)
    {
        log.error("Exception - FAIL:");
        log.error("Exception - Parameter : \r\n1. procName= " + procName + "; \r\n2. colParameter=( " + PrintParameterList.printParameterList(arrParameter) + " );");
        log.error("Exception - "+e.getMessage());
    }
    log.info("End");
   
    returnMap.put("BO_STATUS",status!=null?status:"");
    returnMap.put("BO_STATUS",message!=null?message:"");
    
     return returnMap;
    
}

//Start 1.0.0.7
public Map authSignDedupeInit(String entityType,String entityIdListStr,String customerId,String userId ,Map dbConnectionMap)
{
    log.info("Start");
    //log.debug("checkReceiptNumber - Parameter : \r\n                                                                            1. receiptNumber = "+receiptNumber+"; \r\n                                                                            2. dbConnectionMap = ("+PrintDBConnectionMap.printMap(dbConnectionMap)+"); \r\n");
    DAL dal=new DAL();
    Map returnMap=new HashMap();
    int parameterCount=0;
    String status = "";
    String message = "";
    ArrayList arrParameter=new ArrayList();
    String procName=null;
    ParameterDTO parameterDtoMsg = null;
    ParameterDTO parameterDtoStatus  = null;
    ParameterDTO parameterDtoCustId = null;
    ParameterDTO parameterDtoSearchId = null;
    if(dbConnectionMap.get("DB_SERVER").toString().equals("ORACLE"))
    {
            procName="{call QC_LOS.PKG_LOS_TRN_APPLICANT_MASTER.PR_INIT_NON_CUST_DEDUPE(?,?,?,?,?,?)}";
             arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,entityType));
             arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,entityIdListStr));
             arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,customerId));
             arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,userId));
             arrParameter.add(new ParameterDTO(++parameterCount,"OUT",oracle.jdbc.OracleTypes.VARCHAR,null));
             arrParameter.add(new ParameterDTO(++parameterCount,"OUT",oracle.jdbc.OracleTypes.VARCHAR,null));
    }
    try
    {
        dal.processSPROC(procName, arrParameter,dbConnectionMap);
        parameterDtoMsg = (ParameterDTO)arrParameter.get(5);
        parameterDtoStatus = (ParameterDTO)arrParameter.get(4);
        
        status = (String)parameterDtoStatus.getParameterValue();
        message=(String)parameterDtoMsg.getParameterValue();
      
    }
    catch (DBConnectionException dce)
    {
        log.error("DBConnectionException - FAIL:");
        log.error("DBConnectionException - Parameter : \r\n1. procName= " + procName + "; \r\n2. colParameter=( " + PrintParameterList.printParameterList(arrParameter) + " );");
        log.error("DBConnectionException - "+dce.getMessage());
    }
    catch (Exception e)
    {
        log.error("Exception - FAIL:");
        log.error("Exception - Parameter : \r\n1. procName= " + procName + "; \r\n2. colParameter=( " + PrintParameterList.printParameterList(arrParameter) + " );");
        log.error("Exception - "+e.getMessage());
    }
    log.info("End");
   
    returnMap.put("PC_STATUS",status!=null?status:"");
    returnMap.put("PC_MESSAGE",message!=null?message:"");
    
     return returnMap;
    
}


public Map dedupeInitView(String entityType,String entityId,Map dbConnectionMap)
{
    log.info("Start");
    //log.debug("checkReceiptNumber - Parameter : \r\n                                                                            1. receiptNumber = "+receiptNumber+"; \r\n                                                                            2. dbConnectionMap = ("+PrintDBConnectionMap.printMap(dbConnectionMap)+"); \r\n");
    DAL dal=new DAL();
    Map returnMap=new HashMap();
    int parameterCount=0;
    String status = "";
    String message = "";
    String custId = "";
    String searchId = "";
    ArrayList arrParameter=new ArrayList();
    String procName=null;
    ParameterDTO parameterDtoMsg = null;
    ParameterDTO parameterDtoStatus  = null;
    ParameterDTO parameterDtoCustId = null;
    ParameterDTO parameterDtoSearchId = null;
    if(dbConnectionMap.get("DB_SERVER").toString().equals("ORACLE"))
    {
            procName="{call QC_LOS.PKG_LOS_TRN_APPLICANT_MASTER.PR_LAST_NON_CUST_DEDUPE(?,?,?,?,?,?)}";
             arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,entityType));
             arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,entityId));
             arrParameter.add(new ParameterDTO(++parameterCount,"OUT",oracle.jdbc.OracleTypes.VARCHAR,null));//PN_CUST_ID
             arrParameter.add(new ParameterDTO(++parameterCount,"OUT",oracle.jdbc.OracleTypes.VARCHAR,null));//PN_SEARCH_ID
             arrParameter.add(new ParameterDTO(++parameterCount,"OUT",oracle.jdbc.OracleTypes.VARCHAR,null));
             arrParameter.add(new ParameterDTO(++parameterCount,"OUT",oracle.jdbc.OracleTypes.VARCHAR,null));
    }
    try
    {
        dal.processSPROC(procName, arrParameter,dbConnectionMap);
        
        parameterDtoCustId = (ParameterDTO)arrParameter.get(2);
        parameterDtoSearchId = (ParameterDTO)arrParameter.get(3);
        parameterDtoMsg = (ParameterDTO)arrParameter.get(5);
        parameterDtoStatus = (ParameterDTO)arrParameter.get(4);
        
        custId = (String)parameterDtoCustId.getParameterValue();
        searchId=(String)parameterDtoSearchId.getParameterValue();
        status = (String)parameterDtoStatus.getParameterValue();
        message=(String)parameterDtoMsg.getParameterValue();
      
    }
    catch (DBConnectionException dce)
    {
        log.error("DBConnectionException - FAIL:");
        log.error("DBConnectionException - Parameter : \r\n1. procName= " + procName + "; \r\n2. colParameter=( " + PrintParameterList.printParameterList(arrParameter) + " );");
        log.error("DBConnectionException - "+dce.getMessage());
    }
    catch (Exception e)
    {
        log.error("Exception - FAIL:");
        log.error("Exception - Parameter : \r\n1. procName= " + procName + "; \r\n2. colParameter=( " + PrintParameterList.printParameterList(arrParameter) + " );");
        log.error("Exception - "+e.getMessage());
    }
    log.info("End");
   
    returnMap.put("PC_CUST_ID",custId!=null?custId:"");
    returnMap.put("PC_SEARCH_ID",searchId!=null?searchId:"");
    returnMap.put("PC_STATUS",status!=null?status:"");
    returnMap.put("PC_MESSAGE",message!=null?message:"");
    
     return returnMap;
    
}
//End 1.0.0.7
//start 1.0.0.19
public Map getApplicantDataExperian(int applicantId,int applicantTypeId,Map dbConnectionMap) { 
	log.info("Start");
	//log.debug("GetPersonalInfo - Parameter : \r\n                                                                            1. ProspectId = "+ProspectId+"; \r\n                                                                            2. ApplicantType = "+ApplicantType+"; \r\n                                                                            3. applicantId = "+applicantId+"; \r\n                                                                            4. dbConnectionMap = ("+ PrintDBConnectionMap.printMap(dbConnectionMap) + "); \r\n");
	DAL dal=new DAL();
	int parameterCount=0;
	ArrayList arrParameter=new ArrayList();
	String procName=null;
	ParameterDTO parameterDto= null;
	ParameterDTO parameterDto2= null;
	ParameterDTO parameterDto3= null;//1.0.0.7
	ParameterDTO parameterDto4= null;//1.0.0.17
	//PersonalInfoDTO personalInfoDTO = new PersonalInfoDTO();
	CustomerListDTO customerListDTO = new CustomerListDTO();
	//ArrayList keyContactData=new ArrayList();
	Map map = new HashMap();
	
	
	
	String message = null;
	String status = null;
	String messageAndStatus = null;
	String name="";//1.0.0.20
	  if(dbConnectionMap.get("DB_SERVER").toString().equals("ORACLE"))
	  {
		    procName="{call QC_LOS.PKG_EXPERIAN_INTEGRATION.PR_GET_APPLICANT_DATA(?,?,?)}"; 
		 	arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,""+applicantId));
		 	arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,""+applicantTypeId));
			arrParameter.add(new ParameterDTO(++parameterCount,"OUT",oracle.jdbc.OracleTypes.CURSOR,null));
	 }
	  else if(dbConnectionMap.get("DB_SERVER").toString().equals("MSSQLSERVER"))
	  {
		    procName="{call QC_LOS.PKG_EXPERIAN_INTEGRATION.PR_GET_APPLICANT_DATA(?,?,?)}";
		    arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,""+applicantId));
		 	arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,""+applicantTypeId));
			arrParameter.add(new ParameterDTO(++parameterCount,"OUT",oracle.jdbc.OracleTypes.CURSOR,null));
	  }
	  else if(dbConnectionMap.get("DB_SERVER").toString().equals("MYSQL"))
	  {
		  	procName="{call PR_GET_APPLICANT_DATA(?,?,?)}";
		  	arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,""+applicantId));
		 	arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.VARCHAR,""+applicantTypeId));
			arrParameter.add(new ParameterDTO(++parameterCount,"OUT",oracle.jdbc.OracleTypes.CURSOR,null));
	  }
	  try
	  {
		  dal.processSPROC(procName, arrParameter,dbConnectionMap);
		  parameterDto = (ParameterDTO)arrParameter.get(2);
		ArrayList list=(ArrayList)parameterDto.getParameterValue();
		
		if(list != null && list.size()>0){
		Iterator itr = list.iterator();
		
		Map resultSetMap = null;
		
		if(itr!=null)
		{
			while(itr.hasNext())
				
				
				
			{										
				resultSetMap=(Map)itr.next();
				
				if(resultSetMap.get("FNAME") != null)
				{
					customerListDTO.setExperianFirstName(resultSetMap.get("FNAME").toString());		
					name=resultSetMap.get("FNAME").toString();//1.0.0.20
				}	
				
				if(resultSetMap.get("MNAME") != null)
				{
					customerListDTO.setExperianMiddleName(resultSetMap.get("MNAME").toString());
					name=name+" "+resultSetMap.get("MNAME").toString();//1.0.0.20
				}
				if(resultSetMap.get("LNAME") != null)
				{
					customerListDTO.setExperianLastName(resultSetMap.get("LNAME").toString());
					name=name+" "+resultSetMap.get("LNAME").toString();//1.0.0.20
				}
				if(resultSetMap.get("APPLICANTID") != null)
				{
					customerListDTO.setExperianApplicantId(resultSetMap.get("APPLICANTID").toString());
					
				}
				if(resultSetMap.get("PANNO") != null)
				{
					customerListDTO.setExperianPanNo(resultSetMap.get("PANNO").toString());
					
				}
				if(resultSetMap.get("DOB") != null)
				{
					customerListDTO.setExperianDob(resultSetMap.get("DOB").toString());
					
				}
				if(resultSetMap.get("GENDER") != null) {
					
					if("1000000001".equals(resultSetMap.get("GENDER"))) {
						customerListDTO.setGenderCode("1");
					}
					else if("1000000002".equals(resultSetMap.get("GENDER"))) {
						customerListDTO.setGenderCode("2");
					}
					else if("1000000003".equals(resultSetMap.get("GENDER"))) {
						customerListDTO.setGenderCode("3");
					}
				}
			//common address details start
				if(resultSetMap.get("FLATNO") != null){
					customerListDTO.setFlatNo(resultSetMap.get("FLATNO").toString());					
				}
				if(resultSetMap.get("BUILDINGNAME") != null){
					customerListDTO.setBuildingName(resultSetMap.get("BUILDINGNAME").toString());					
				}
				if(resultSetMap.get("LOCALITY") != null){
					customerListDTO.setLocality(resultSetMap.get("LOCALITY").toString());					
				}
				if(resultSetMap.get("CITYNAME") != null){
					customerListDTO.setExperianCity(resultSetMap.get("CITYNAME").toString());					
				}
				if(resultSetMap.get("GST_STATE_CODE") != null){
					customerListDTO.setExperianState(resultSetMap.get("GST_STATE_CODE").toString());					
				}
				if(resultSetMap.get("PINCODE") != null){
					customerListDTO.setExperianPincode(resultSetMap.get("PINCODE").toString());					
				}
				
				//common address end
				//start //1.0.0.20
				if(resultSetMap.get("MOBILE_NO") != null)
				{
					customerListDTO.setMobile(resultSetMap.get("MOBILE_NO").toString());
					
				}
				
				customerListDTO.setName(name);//end 1.0.0.20
			}
		}
		}
		map.put("customerListDTO", customerListDTO);

	}
	  catch (DBConnectionException dce) 
	  {
		  log.error("DBConnectionException - FAIL:");
			log.error("DBConnectionException - Parameter : \r\n1. procName= " + procName + "; \r\n2. colParameter=( " + PrintParameterList.printParameterList(arrParameter) + " );");
	  	log.error("DBConnectionException - "+dce.getMessage());
	  }
	  catch (Exception e) 
	  {
		  log.error("Exception - FAIL:");
			log.error("Exception - Parameter : \r\n1. procName= " + procName + "; \r\n2. colParameter=( " + PrintParameterList.printParameterList(arrParameter) + " );");
	  	log.error("Exception - "+e.getMessage());
	  }
	  log.info("End");

	return map;
}


public Map saveExperianData(String panNo,int applicantId,String reqType,String jsonRequestString,String jsonResponseString,String responseStatus,Long bureauScore,String responseDescription,String fileName,String userIdExp,File pdfData,Map dbConnectionMap)
{
	log.info("Start");
	Map returnMap = null;
	int parameterCount = 0;
	String procName = null;
	ParameterDTO parameterDto = null;
	Map map = new HashMap();
	String status = "";
	String code = "";
	String message = "";
	String caseInfo = "";
	String tranchInfo = "";
	DAL dal=new DAL();
	ArrayList arrParameter = new ArrayList();
	Connection connection = null;
	if (dbConnectionMap.get("DB_SERVER").toString().equals("ORACLE"))
	{
		procName = "{call QC_LOS.PKG_EXPERIAN_INTEGRATION.PR_SAVE_EXPERIAN_REQ_LOG(?,?,?,?,?, ?,?,?,?,?,?, ?,?,?)}";
		arrParameter.add(new ParameterDTO(++parameterCount, "IN", oracle.jdbc.OracleTypes.VARCHAR, panNo));
		arrParameter.add(new ParameterDTO(++parameterCount, "IN", oracle.jdbc.OracleTypes.VARCHAR, ""+applicantId));
		arrParameter.add(new ParameterDTO(++parameterCount, "IN", oracle.jdbc.OracleTypes.VARCHAR,reqType ));	
		arrParameter.add(new ParameterDTO(++parameterCount, "IN", oracle.jdbc.OracleTypes.VARCHAR, jsonRequestString));
		arrParameter.add(new ParameterDTO(++parameterCount, "IN", oracle.jdbc.OracleTypes.CLOB,jsonResponseString ));
		arrParameter.add(new ParameterDTO(++parameterCount, "IN", oracle.jdbc.OracleTypes.CLOB,responseStatus ));
		arrParameter.add(new ParameterDTO(++parameterCount, "IN", oracle.jdbc.OracleTypes.VARCHAR,responseDescription ));
		arrParameter.add(new ParameterDTO(++parameterCount, "IN", oracle.jdbc.OracleTypes.CLOB, fileName));
		
		arrParameter.add(new ParameterDTO(++parameterCount,"IN OUT",oracle.jdbc.OracleTypes.VARCHAR, null));  //1.0.0.21
		arrParameter.add(new ParameterDTO(++parameterCount,"IN",oracle.jdbc.OracleTypes.BLOB, pdfData)); //1.0.0.21
		arrParameter.add(new ParameterDTO(++parameterCount, "IN", oracle.jdbc.OracleTypes.VARCHAR, userIdExp)); //1.0.0.21
		
		arrParameter.add(new ParameterDTO(++parameterCount, "IN", oracle.jdbc.OracleTypes.VARCHAR, ""+bureauScore));

		arrParameter.add(new ParameterDTO(++parameterCount, "OUT", oracle.jdbc.OracleTypes.VARCHAR, null));	//status		
		arrParameter.add(new ParameterDTO(++parameterCount, "OUT", oracle.jdbc.OracleTypes.VARCHAR, null)); //msg
	}
	
	try
	{
		connection=DBConnection.getConnection(dbConnectionMap);
		connection.setAutoCommit(false);
		//new DAL().processSPROC(procName, arrParameter, dbConnectionMap);
		log.info("proc calling start");
		dal.processSPROC(procName, arrParameter,dbConnectionMap,connection);
		log.info("response received");
		status =  ((ParameterDTO) arrParameter.get(12)).getParameterValue().toString();
		message = ((ParameterDTO) arrParameter.get(13)).getParameterValue().toString();
		
		
		if ("S".equalsIgnoreCase(status))
		{
			DBConnection.commitTransaction(connection);
			status = "S";
			code = "SAVED";
			message = "Data Insert Successfully";
		}
		else
		{
			status = "F";
			code = "FAILURE";
			message = "Data Insertion for Authorised Signatory Failed";
			log.error("processSPROC - FAIL:");
			log.error("processSPROC - Parameter : \r\n1. procName= " + procName + "; \r\n2. colParameter=( " + PrintParameterList.printParameterList(arrParameter) + " );");
		}
	}
	catch (DBConnectionException dce)
	{
		DBConnection.rollbackTransaction(connection);
		log.error("DBConnectionException  -  " + dce.getMessage());
		StackTraceElement[] elements = new Throwable().getStackTrace();
	String calleeMethod = elements[0].getMethodName();	        	        
	log.error("DBConnectionException - FAIL:");
		log.error("DBConnectionException - Parameter : \r\n1. procName= " + procName + "; \r\n2. colParameter=( " + PrintParameterList.printParameterList(arrParameter) + " );");
	
	}
	catch (Exception e)
	{
		status = "F";
		code = "FAILURE";
		message = e.getMessage();
		StackTraceElement[] elements = new Throwable().getStackTrace();
	String calleeMethod = elements[0].getMethodName();	        	        
	log.error("Exception - FAIL:");
		log.error("Exception - Parameter : \r\n1. procName= " + procName + "; \r\n2. colParameter=( " + PrintParameterList.printParameterList(arrParameter) + " );");
	log.error("Exception - "+e.getMessage());
	}
	finally
	{
		DBConnection.closeConnection(connection);
	}

	returnMap = new HashMap();
	map.put("status", status);
	map.put("message", message);
	log.info("End");
	return map;
}
//end 1.0.0.19

       
}
