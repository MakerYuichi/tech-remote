/**
 * ************************************************CHANGE HISTORY****************************************************
Version          Updated by         Updated On                    Change																													bugno
1.0.0.1          Rahul	           21-Jun-2017                   CR (GST)
1.0.0.2          APURVA SHUKLA	   06-Jun-2018                  lob dependent product
1.0.0.3          APURVA SHUKLA	   21-Jun-2018                  bugId-56747 ( Co-lessee or gurantor can't be same as lessee handled from frontEnd)
1.0.0.4          Ravi              31-july-2018              CR for add validation incase for changing in IGST per.
1.0.0.5			Priyanka Soni 	 	10-Jun-2022	 			CR- Storing Grace Period for Customer Asset Category Wise.
1.0.0.6			Hemant Kumar 	 	22-Dec-2022	 			KYC Compliance CR
1.0.0.7         Narottam Biswal     29-Dec-2023             CR- LPI details to be added in applicant details screen
1.0.0.8         Nalin Kumar Jena    08-Feb-2024             CR-Udyam No. - Customer level attribute for non-individual.
1.0.0.9         Nalin Kumar Jena    22-Feb-2024             CR-Passport No varification
1.0.0.10        Narottam Biswal     22-Feb-2024             CR-DL No varification
1.0.0.11        Tanisha Agarwal     22-Feb-2024             CR-Voter ID varification
1.0.0.12	    Ravi Shankar		27-MAR-2024	 		 	CR- document upload/download on DMS
1.0.0.13	    Nalin Kumar Jena    23-APR-2024	 		 	CR- Credit Ratings And parameters 
1.0.0.14        Tanisha Agarwal      25-Jun-2024             CR-Udyam Registration varification on individual
1.0.0.15        Narottam Biswal      27-Nov-2025            PAN  verification geen/red tick
1.0.0.16        Ravi Shankar         09-Apr-2026            GST Registration Status
1.0.0.17        Nalin Kumar Jena     18-Jun-2026            TDS deduction in Processing Fee paid by customer
1.0.0.18        Sanchi Agarwal       26-Aug-2026            Added DOB, Gender and Pincode fields under Authorised Signatory in Personal Info
*************************************************END***********************************************
**/ 
package qc.los.controller.form;

import java.io.File;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import qc.customer.controller.form.CustomerForm;

public class PersonalInfoForm extends ActionForm {

	private static final long serialVersionUID = 1L;
	
	private String relation="";
	
	 private String relationWithApplicant;
	 private String applicationTypeId;
	 private String applicantID;
	 private String applicantCode;
	 private String applicantType;
	 private String relationAppList;
	 private String applicantTypeCode;
	 private String selApplicantType;
	 private String title;
	 private String religion;
	 private String caste;
	 private String psl;
	 private String firstName;
	 private String middleName;
	 private String lastName;
	 private String maritalStatus;
	 private String gender;
	 private String selGender;
	 private String spouseTitle;
	 private String spouseFirstName;
	 private String spouseMiddleName;
	 private String spouseLastName;
	 private String constitution;
	 private String dnsRequired;
	 private String dnsRequired1;// added by sakib hassan for updation on PDE module 
	 private String dnsReason;
	 private String preferredTimeToCall;
	 private String preferredModeOfCommunication;
	 private String target;
	 private String industry;
	 private String sector;
	 private String website;
	 private String product;
	 
	 private String kycRiskCategory;//ravinder
	 private String kycRiskCategory_Corp;//ravinder
	 
	 private String gstRegistrationStatus;//1.0.0.16
	 private String gstRegistrationStatusCorp;//1.0.0.16
	 
	 private String dmLeasseCode;//1.0.0.3
	 
	 
	private String dateOfBirth;
	
	
	private String cibilId;//added by shashaank dubey for cibil
	private String qualificationId; //added by stuti bajpai for ORIX 
	
	
	private String noOfDependents;
	private String fatherTitle;
	private String fatherFirstName;
	private String fatherMiddleName;
	private String fatherLastName;
	private String motherFirstName;
	private String motherMiddleName;
	private String motherLastName;
	private String spouseOccupation;
	private String spouseNameOfCompany;
	
	private String custCategory;
	private String eduQualification;
	private String highestQualification;
	private String degree;
	private String collegeLastAttended;
	private String nationality;
	private String nlFirstName;
	private String nlSecondName;
	private String nlLastName;
	private String voterId;
	private String isPanNo;
	private String panNo;
	private String declaration;
	private String drivingLicenseNo;
	private String passportNo;
	private String basic;
	private String da;
	private String hra;
	private String spAllowance;
	private String medicalLta;
	private String bonusIncentive;
	private String others;
	private String grossIncome;
	private String otherIncome;
	private String netIncome;
	private String dnsReasonTemp; 
	
	
	private String nameOfCompany;
	private String authSignatoryFirstName;
	private String authSignatoryMiddleName;
	private String authSignatoryLastName;
	private String dateOfIncorporation;
	private String companyPan;
	
	private String nameOfGroup;
	private String recruitedByFirstName;
	private String recruitedByMiddleName;
	private String recruitedByLastName;
	private String trainedDate;
	
	private String addrApplicationTypeId;
	private String addrApplicantType;
	private String addressType;
	private String sameAs;
	private String accomodationType;
	private String mailingAddress;
	private String flatHouseNo;
	private String floorNo;
	private String buildingName;
	private String locality;
	private String landMark;
	private String city;
	private String state;
	private String zipPin;
	private String zipPin_temp;
	private String extention1;
	private String phoneNo1;
	private String extension2;
	private String phone2;
	private String mobile;
	private String email;
	private String fax;
	private String stdIsd;
	private String ownership;
	private String applicantAddressId;
	private String addrApplicantId;
	private String noOfYears;
	private String createdBy;
	private String createdDateTime;
	private String updatedBy;
	private String updateDateTime;
	private String status;
	private String flag;
	private String state_temp;
	private String city_temp;
	private String offCompanyName;
	private String noOfYearAtResidence;
	private String noOfMonthAtResidence;
	private String residenceStatus;
	
	
	private String custEntityType;
	
	private String businessDate;
	
	private String mailingAddressStatus;
	private String vewPicId;
	// Apurva Changes
	private String lob;
	private String keyContactDataStr;
	 private String caseId;
	 private String removeKeyContactDataStr;
	 private String keyContactsId;
	 private String entityIdForDm;
	 private String lobCorp;
	///
	//// Apurva Added for gst in Dm module on 15 jan 2018
	 private String caseCode;
	 private String sez;
	 private String igstApplicable;
	 private String igstpercent;
	 private String lutNo;
	 private String loaCheckBox;
	 
	 
	/**
	 * New Properties added for Dia Vikas
	 */
	private String legalFormName;
	private String rbiRegNo;
	private FormFile legalForm;
	private String fileNameDms;//1.0.0.12
	private String keyContactPerson;
	private String authCapital;
	private String issuedCap;
	private String boardRepresentative;
	private byte[] fileData;
	private File theFile;
	private String salesManager;
	private String salesManager_temp;
	private String legalFormType;
	private String legalFormTypeInd;
	private String legalFormTypeCorp;
	
	// Added By Seema Pandey
	private String medicalFitness;
	private String heightFeet;
	private String heightInches;
	private String heightCms;
	private String weight;
	
	//Added By Rinky Arora
	private String existingApplicantId;
	private String isExisting;
	// 1.0.0.1 start
	private String gstinNo;
    private String destinationAddValue;
    // 1.0.0.1 end
	
	
	//Added by Stuti Bajpai
	private String adharNo;
	private String formFlag;
	
	// ravi start
	private String customerSegment;
	private String natureActivity;
	private String occupation;
	// ravi end
	private String groupCode;
	private String lessorGstType;
	
	
	 private String quotation_igstPer;// 1.0.0.4 start
	    private String quotation_sgst_ugstPer;
	    private String quotation_cgstPer;
	    private String quotation_sez_zone;
	    private String quotation_registrationState;  // 1.0.0.4 end
	    private String adharOkycCompFlag;
	    private String assetCatGpSaveStr; //1.0.0.5
	    //Start 1.0.0.6
	    private String removeBeneficiaryOwnerDataStr;
	    private String beneficiaryOwnerDataStr;
	    private String beneficiaryOwnerId;
	    private String beneficiaryOwnerName;
	    private String beneficiaryOwnerPan;
	    private String beneficiaryOwnerEmail;
	    private String beneficiaryOwnerMobile;
	    private String beneficiaryOwnerAddress;
	    private String beneficiaryOwnerStateId;
	    private String beneficiaryOwnerCityId;
	    private String beneficiaryOwnerStateName;
	    private String beneficiaryOwnerCityName;
	    private String beneficiaryOwnerDedCustId;
	    private String beneficiaryOwnerDedSearchId;
	    
	    private String authSignDedCustId;
	    private String authSignDedSearchId;
	    private String authSignPan;
	    private String authSignAddress;
	    private String authSignStateId;
	    private String authSignCityId;
	    private String authSignStateName;
	    private String authSignCityName;
	    private String removeAuthSignDataStr;
	    //End 1.0.0.6
	    
	    //start 1.0.0.6
	    private String sectorInd;
	    private String industryInd;
	    private String sectorCorp;
	    private String industryCorp;
	    //end 1.0.0.6
	    
	    //start 1.0.0.7 
	    private String lpiRateType;
	    private String lpiRate;
	    
	    //end 1.0.0.7 
	    
	    //Nalin start
	    private String fileNo;
	    private String dateOfissue;
	    private String passportNoVerifiedFlag; //1.0.0.9
	    
	    private String dlVarifyFlag;//1.0.0.10
	    private String voterIdFlg; //1.0.0.11
	    private String udyamIdFlg; //1.0.0.12
	    private String quotationCode;// 1.0.0.13
	    private String natureOfBusiness;// 1.0.0.13
	    private String natureOfBusinessCorp;// 1.0.0.13
	    private String natureOfBusinessComn;// 1.0.0.13
	    private String panVarifiedFlag; //1.0.0.15
	    private String panVarifiedFlagComp;//1.0.0.15

	    private String tdsApplicableCheck;//1.0.0.17


	    public String getTdsApplicableCheck() {
			return tdsApplicableCheck;
		}

		public void setTdsApplicableCheck(String tdsApplicableCheck) {
			this.tdsApplicableCheck = tdsApplicableCheck;
		}


	    public String getVoterIdFlg() {
			return voterIdFlg;
		}

		public void setVoterIdFlg(String voterIdFlg) {
			this.voterIdFlg = voterIdFlg;
		}

		public String getUdyamIdFlg() {
			return udyamIdFlg;
		}

		public void setUdyamIdFlg(String udyamIdFlg) {
			this.udyamIdFlg = udyamIdFlg;
		}

		public String getDlVarifyFlag() {
			return dlVarifyFlag;
		}

		public void setDlVarifyFlag(String dlVarifyFlag) {
			this.dlVarifyFlag = dlVarifyFlag;
		}

		public String getPassportNoVerifiedFlag() {
			return passportNoVerifiedFlag;
		}

		public void setPassportNoVerifiedFlag(String passportNoVerifiedFlag) {
			this.passportNoVerifiedFlag = passportNoVerifiedFlag;
		}

		public String getFileNo() {
			return fileNo;
		}

		public void setFileNo(String fileNo) {
			this.fileNo = fileNo;
		}

		public String getDateOfissue() {
			return dateOfissue;
		}

		public void setDateOfissue(String dateOfissue) {
			this.dateOfissue = dateOfissue;
		}
		
		//Nalin End
	    
	    
	    //Added by Nalin 1.0.0.8 Start
	    private String udyamNo;

		public String getUdyamNo() {
			return udyamNo;
		}

		public void setUdyamNo(String udyamNo) {
			this.udyamNo = udyamNo;
		}
		//1.0.0.8 End

		public String getSectorInd() {
			return sectorInd;
		}

		public String getLpiRateType() {
			return lpiRateType;
		}

		public void setLpiRateType(String lpiRateType) {
			this.lpiRateType = lpiRateType;
		}

		public String getLpiRate() {
			return lpiRate;
		}

		public void setLpiRate(String lpiRate) {
			this.lpiRate = lpiRate;
		}

		public void setSectorInd(String sectorInd) {
			this.sectorInd = sectorInd;
		}

		public String getIndustryInd() {
			return industryInd;
		}

		public void setIndustryInd(String industryInd) {
			this.industryInd = industryInd;
		}

		public String getSectorCorp() {
			return sectorCorp;
		}

		public void setSectorCorp(String sectorCorp) {
			this.sectorCorp = sectorCorp;
		}

		public String getIndustryCorp() {
			return industryCorp;
		}

		public void setIndustryCorp(String industryCorp) {
			this.industryCorp = industryCorp;
		}

		public String getRemoveBeneficiaryOwnerDataStr() {
			return removeBeneficiaryOwnerDataStr;
		}

		public String getRemoveAuthSignDataStr() {
			return removeAuthSignDataStr;
		}

		public void setRemoveAuthSignDataStr(String removeAuthSignDataStr) {
			this.removeAuthSignDataStr = removeAuthSignDataStr;
		}

		public String getBeneficiaryOwnerDedCustId() {
			return beneficiaryOwnerDedCustId;
		}

		public void setBeneficiaryOwnerDedCustId(String beneficiaryOwnerDedCustId) {
			this.beneficiaryOwnerDedCustId = beneficiaryOwnerDedCustId;
		}

		public String getBeneficiaryOwnerDedSearchId() {
			return beneficiaryOwnerDedSearchId;
		}

		public void setBeneficiaryOwnerDedSearchId(String beneficiaryOwnerDedSearchId) {
			this.beneficiaryOwnerDedSearchId = beneficiaryOwnerDedSearchId;
		}

		public String getAuthSignDedCustId() {
			return authSignDedCustId;
		}

		public void setAuthSignDedCustId(String authSignDedCustId) {
			this.authSignDedCustId = authSignDedCustId;
		}

		public String getAuthSignDedSearchId() {
			return authSignDedSearchId;
		}

		public void setAuthSignDedSearchId(String authSignDedSearchId) {
			this.authSignDedSearchId = authSignDedSearchId;
		}

		public String getAuthSignPan() {
			return authSignPan;
		}

		public void setAuthSignPan(String authSignPan) {
			this.authSignPan = authSignPan;
		}

		public String getAuthSignAddress() {
			return authSignAddress;
		}

		public void setAuthSignAddress(String authSignAddress) {
			this.authSignAddress = authSignAddress;
		}

		public String getAuthSignStateId() {
			return authSignStateId;
		}

		public void setAuthSignStateId(String authSignStateId) {
			this.authSignStateId = authSignStateId;
		}

		public String getAuthSignCityId() {
			return authSignCityId;
		}

		public void setAuthSignCityId(String authSignCityId) {
			this.authSignCityId = authSignCityId;
		}

		public String getAuthSignStateName() {
			return authSignStateName;
		}

		public void setAuthSignStateName(String authSignStateName) {
			this.authSignStateName = authSignStateName;
		}

		public String getAuthSignCityName() {
			return authSignCityName;
		}

		public void setAuthSignCityName(String authSignCityName) {
			this.authSignCityName = authSignCityName;
		}

		public String getBeneficiaryOwnerId() {
			return beneficiaryOwnerId;
		}

		public void setBeneficiaryOwnerId(String beneficiaryOwnerId) {
			this.beneficiaryOwnerId = beneficiaryOwnerId;
		}

		public String getBeneficiaryOwnerName() {
			return beneficiaryOwnerName;
		}

		public void setBeneficiaryOwnerName(String beneficiaryOwnerName) {
			this.beneficiaryOwnerName = beneficiaryOwnerName;
		}

		public String getBeneficiaryOwnerPan() {
			return beneficiaryOwnerPan;
		}

		public void setBeneficiaryOwnerPan(String beneficiaryOwnerPan) {
			this.beneficiaryOwnerPan = beneficiaryOwnerPan;
		}

		public String getBeneficiaryOwnerEmail() {
			return beneficiaryOwnerEmail;
		}

		public void setBeneficiaryOwnerEmail(String beneficiaryOwnerEmail) {
			this.beneficiaryOwnerEmail = beneficiaryOwnerEmail;
		}

		public String getBeneficiaryOwnerMobile() {
			return beneficiaryOwnerMobile;
		}

		public void setBeneficiaryOwnerMobile(String beneficiaryOwnerMobile) {
			this.beneficiaryOwnerMobile = beneficiaryOwnerMobile;
		}

		public String getBeneficiaryOwnerAddress() {
			return beneficiaryOwnerAddress;
		}

		public void setBeneficiaryOwnerAddress(String beneficiaryOwnerAddress) {
			this.beneficiaryOwnerAddress = beneficiaryOwnerAddress;
		}

		public String getBeneficiaryOwnerStateId() {
			return beneficiaryOwnerStateId;
		}

		public void setBeneficiaryOwnerStateId(String beneficiaryOwnerStateId) {
			this.beneficiaryOwnerStateId = beneficiaryOwnerStateId;
		}

		public String getBeneficiaryOwnerCityId() {
			return beneficiaryOwnerCityId;
		}

		public void setBeneficiaryOwnerCityId(String beneficiaryOwnerCityId) {
			this.beneficiaryOwnerCityId = beneficiaryOwnerCityId;
		}

		public String getBeneficiaryOwnerStateName() {
			return beneficiaryOwnerStateName;
		}

		public void setBeneficiaryOwnerStateName(String beneficiaryOwnerStateName) {
			this.beneficiaryOwnerStateName = beneficiaryOwnerStateName;
		}

		public String getBeneficiaryOwnerCityName() {
			return beneficiaryOwnerCityName;
		}

		public void setBeneficiaryOwnerCityName(String beneficiaryOwnerCityName) {
			this.beneficiaryOwnerCityName = beneficiaryOwnerCityName;
		}

		public void setRemoveBeneficiaryOwnerDataStr(String removeBeneficiaryOwnerDataStr) {
			this.removeBeneficiaryOwnerDataStr = removeBeneficiaryOwnerDataStr;
		}

		public String getBeneficiaryOwnerDataStr() {
			return beneficiaryOwnerDataStr;
		}

		public void setBeneficiaryOwnerDataStr(String beneficiaryOwnerDataStr) {
			this.beneficiaryOwnerDataStr = beneficiaryOwnerDataStr;
		}
	    
	    
	    
	
	public String getAdharOkycCompFlag() {
			return adharOkycCompFlag;
		}

		public void setAdharOkycCompFlag(String adharOkycCompFlag) {
			this.adharOkycCompFlag = adharOkycCompFlag;
		}

	public String toUppCase(String val)
	{if(val!=null){
		if(val.equals(""))
		return val;
	else{
		String caps=val.toUpperCase();
		return caps;
	}
	}
		return val;
	}
	
	public PersonalInfoForm toUppCaseAll(PersonalInfoForm obj)
	{
		 
		
		obj.relation=toUppCase(obj.relation);
		obj.applicationTypeId=toUppCase(obj.applicationTypeId);
		obj.applicantID=toUppCase(obj.applicantID);
		obj.applicantCode=toUppCase(obj.applicantCode);
		obj.applicantType=toUppCase(obj.applicantType);
		obj.relationAppList=toUppCase(obj.relationAppList);
				obj.applicantTypeCode=toUppCase(obj.applicantTypeCode);	
		obj.selApplicantType=toUppCase(obj.selApplicantType);
				obj.title=toUppCase(obj.title);	
				
		obj.religion=toUppCase(obj.religion);
		obj.caste=toUppCase(obj.caste);
		obj.psl=toUppCase(obj.psl);
		
		obj.firstName=toUppCase(obj.firstName);
				obj.middleName=toUppCase(obj.middleName);
				obj.lastName=toUppCase(obj.lastName);
						obj.maritalStatus=toUppCase(obj.maritalStatus);
		obj.gender=toUppCase(obj.gender);
		obj.selGender=toUppCase(obj.selGender);
		
		obj.spouseTitle=toUppCase(obj.spouseTitle);
		obj.spouseFirstName=toUppCase(obj.spouseFirstName);
		obj.spouseMiddleName=toUppCase(obj.spouseMiddleName);
		obj.spouseLastName=toUppCase(obj.spouseLastName);
		obj.constitution=toUppCase(obj.constitution);
		obj.dnsRequired=toUppCase(obj.dnsRequired);
		obj.dnsRequired1=toUppCase(obj.dnsRequired1);
		obj.dnsReason=toUppCase(obj.dnsReason);
				obj.preferredTimeToCall=toUppCase(obj.preferredTimeToCall);
				
		obj.preferredModeOfCommunication=toUppCase(obj.preferredModeOfCommunication);
		obj.target=toUppCase(obj.target);
		
		
		
		obj.dateOfBirth=toUppCase(obj.dateOfBirth); 
		obj.cibilId=toUppCase(obj.cibilId);
		obj.qualificationId=toUppCase(obj.qualificationId);
		
		obj.noOfDependents=toUppCase(obj.noOfDependents);
		
		obj.fatherTitle=toUppCase(obj.fatherTitle);
		obj.fatherFirstName=toUppCase(obj.fatherFirstName);
		
		obj.fatherMiddleName=toUppCase(obj.fatherMiddleName);
		
				obj.fatherLastName=toUppCase(obj.fatherLastName);
		obj.motherFirstName=toUppCase(obj.motherFirstName);
		obj.motherMiddleName=toUppCase(obj.motherMiddleName);
		obj.motherLastName=toUppCase(obj.motherLastName);
		obj.spouseOccupation=toUppCase(obj.spouseOccupation);
		obj.spouseNameOfCompany=toUppCase(obj.spouseNameOfCompany);
		obj.custCategory=toUppCase(obj.custCategory);
		obj.eduQualification=toUppCase(obj.eduQualification);
		obj.highestQualification=toUppCase(obj.highestQualification);
		obj.degree=toUppCase(obj.degree);
		obj.collegeLastAttended=toUppCase(obj.collegeLastAttended);
		obj.nationality=toUppCase(obj.nationality);
		obj.nlFirstName=toUppCase(obj.nlFirstName);
				obj.nlSecondName=toUppCase(obj.nlSecondName);
		obj.nlLastName=toUppCase(obj.nlLastName);
		obj.voterId=toUppCase(obj.voterId);
		obj.isPanNo=toUppCase(obj.isPanNo);
		obj.panNo=toUppCase(obj.panNo);
		obj.declaration=toUppCase(obj.declaration);
		obj.drivingLicenseNo=toUppCase(obj.drivingLicenseNo);
		obj.passportNo=toUppCase(obj.passportNo);
		obj.basic=toUppCase(obj.basic);
		obj.da=toUppCase(obj.da);
				obj.hra=toUppCase(obj.hra);
		obj.spAllowance=toUppCase(obj.spAllowance);
				obj.medicalLta=toUppCase(obj.medicalLta);
		obj.bonusIncentive=toUppCase(obj.bonusIncentive);
		obj.others=toUppCase(obj.others);
			obj.grossIncome=toUppCase(obj.grossIncome);
		obj.otherIncome=toUppCase(obj.otherIncome);
		obj.netIncome=toUppCase(obj.netIncome);
		obj.dnsReasonTemp=toUppCase(obj.dnsReasonTemp);
		
		
		/**
		 * New Properties added for Dia Vikas
		 */
		
		
		obj.nameOfCompany=toUppCase(obj.nameOfCompany);
		obj.authSignatoryFirstName=toUppCase(obj.authSignatoryFirstName);
		obj.authSignatoryMiddleName=toUppCase(obj.authSignatoryMiddleName);
		obj.authSignatoryLastName=toUppCase(obj.authSignatoryLastName);
		obj.dateOfIncorporation=toUppCase(obj.dateOfIncorporation);
		obj.companyPan=toUppCase(obj.companyPan);
				obj.nameOfGroup=toUppCase(obj.nameOfGroup);
		obj.recruitedByFirstName=toUppCase(obj.recruitedByFirstName);
		obj.recruitedByMiddleName=toUppCase(obj.recruitedByMiddleName);
		obj.recruitedByLastName=toUppCase(obj.recruitedByLastName);
						obj.trainedDate=toUppCase(obj.trainedDate);
				obj.addrApplicationTypeId=toUppCase(obj.addrApplicationTypeId);
				
				
		obj.addrApplicantType=toUppCase(obj.addrApplicantType);
		obj.addressType=toUppCase(obj.addressType);
		obj.sameAs=toUppCase(obj.sameAs);
		obj.accomodationType=toUppCase(obj.accomodationType);
		obj.flatHouseNo=toUppCase(obj.flatHouseNo);
		obj.floorNo=toUppCase(obj.floorNo);
				obj.buildingName=toUppCase(obj.buildingName);
		obj.locality=toUppCase(obj.locality);
		obj.landMark=toUppCase(obj.landMark);
		
		obj.city=toUppCase(obj.city);
		obj.state=toUppCase(obj.state);
		obj.zipPin=toUppCase(obj.zipPin);
					obj.zipPin_temp=toUppCase(obj.zipPin_temp);
	
		obj.extention1=toUppCase(obj.extention1);
		obj.phoneNo1=toUppCase(obj.phoneNo1);
		obj.extension2=toUppCase(obj.extension2);

				obj.phone2=toUppCase(obj.phone2);
		obj.mobile=toUppCase(obj.mobile);
		obj.fax=toUppCase(obj.fax);
		obj.stdIsd=toUppCase(obj.stdIsd);
		obj.ownership=toUppCase(obj.ownership);

		obj.applicantAddressId=toUppCase(obj.applicantAddressId);
		obj.addrApplicantId=toUppCase(obj.addrApplicantId);
		obj.noOfYears=toUppCase(obj.noOfYears);	
		obj.createdBy=toUppCase(obj.createdBy);
////////////////////
		obj.createdDateTime=toUppCase(obj.createdDateTime);
		obj.updatedBy=toUppCase(obj.updatedBy);
		obj.updateDateTime=toUppCase(obj.updateDateTime);
		obj.status=toUppCase(obj.status);
		obj.flag=toUppCase(obj.flag);
		obj.state_temp=toUppCase(obj.state_temp);
		obj.city_temp=toUppCase(obj.city_temp);
		obj.offCompanyName=toUppCase(obj.offCompanyName);
		obj.noOfYearAtResidence=toUppCase(obj.noOfYearAtResidence);
		obj.noOfMonthAtResidence=toUppCase(obj.noOfMonthAtResidence);
				obj.residenceStatus=toUppCase(obj.residenceStatus);
		obj.custEntityType=toUppCase(obj.custEntityType);
		obj.mailingAddressStatus=toUppCase(obj.mailingAddressStatus);
		obj.vewPicId=toUppCase(obj.vewPicId);
		
	
		
		
		obj.legalFormName=toUppCase(obj.legalFormName);
		obj.rbiRegNo=toUppCase(obj.rbiRegNo);
		obj.keyContactPerson=toUppCase(obj.keyContactPerson);	
		obj.authCapital=toUppCase(obj.authCapital);
		obj.issuedCap=toUppCase(obj.issuedCap);
		obj.boardRepresentative=toUppCase(obj.boardRepresentative);
		obj.salesManager=toUppCase(obj.salesManager);
		obj.salesManager_temp=toUppCase(obj.salesManager_temp);
		obj.legalFormType=toUppCase(obj.legalFormType);
		obj.medicalFitness=toUppCase(obj.medicalFitness);
		obj.heightFeet=toUppCase(obj.heightFeet);
		obj.heightInches=toUppCase(obj.heightInches);
////////////////////
		obj.heightCms=toUppCase(obj.heightCms);
		obj.weight=toUppCase(obj.weight);
				obj.existingApplicantId=toUppCase(obj.existingApplicantId);
		obj.isExisting=toUppCase(obj.isExisting);
		obj.adharNo=toUppCase(obj.adharNo);
		obj.formFlag=toUppCase(obj.formFlag);
		
		obj.groupCode=toUppCase(obj.groupCode);
		obj.occupation=toUppCase(obj.occupation);
		obj.natureActivity=toUppCase(obj.natureActivity);
		obj.customerSegment=toUppCase(obj.customerSegment);
		
		
		obj.authSignDelegationStr=toUppCase(obj.authSignDelegationStr);
		obj.occupation=toUppCase(obj.occupation);
		obj.authSignIdValueStr=toUppCase(obj.authSignIdValueStr);
		obj.authSignContactNoStr=toUppCase(obj.authSignContactNoStr);
		obj.authSignMailIdIdStr=toUppCase(obj.authSignMailIdIdStr);
		obj.authSignDinNoStr=toUppCase(obj.authSignDinNoStr);
		obj.authSignDesignationstr=toUppCase(obj.authSignDesignationstr);
		obj.authSignMName=toUppCase(obj.authSignMName);
		
		obj.authSignFNametr=toUppCase(obj.authSignDelegationStr);
		obj.authSignLNamestr=toUppCase(obj.authSignDtataStr);
		obj.authSignFNametr=toUppCase(obj.authSignFNametr);
		obj.authSignLNamestr=toUppCase(obj.authSignLNamestr);
		obj.kycRiskCategory = toUppCase(obj.kycRiskCategory);//ravinder
		return obj;
	}
	
	
	public String getGroupCode() {
		return groupCode;
	}
	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}
	
		
	
	public String getFormFlag() {
		return formFlag;
	}
	public void setFormFlag(String formFlag) {
		this.formFlag = formFlag;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getAdharNo() {
		return adharNo;
	}
	public void setAdharNo(String adharNo) {
		this.adharNo = adharNo;
	}
	
	public String getQualificationId() {
		return qualificationId;
	}

	public void setQualificationId(String qualificationId) {
		this.qualificationId = qualificationId;
	}
	
	
	public String getCibilId() {
		return cibilId;
	}

	public void setCibilId(String cibilId) {
		this.cibilId = cibilId;
	}
	
		public String getIsExisting() {
		return isExisting;
	}

	public void setIsExisting(String isExisting) {
		this.isExisting = isExisting;
	}

		public String getExistingApplicantId() {
		return existingApplicantId;
	}

	public void setExistingApplicantId(String existingApplicantId) {
		this.existingApplicantId = existingApplicantId;
	}
		private String entityIdForCustomer;
		
		private String custGroupType;
public String getEntityIdForCustomer() {
		return entityIdForCustomer;
	}

	public void setEntityIdForCustomer(String entityIdForCustomer) {
		this.entityIdForCustomer = entityIdForCustomer;
	}
	private String tin;
	private String tanNo;
	private String corpTanNo;
	
	
	public String getCorpTanNo() {
		return corpTanNo;
	}

	public void setCorpTanNo(String corpTanNo) {
		this.corpTanNo = corpTanNo;
	}

	public String getTanNo() {
		return tanNo;
	}

	public void setTanNo(String tanNo) {
		this.tanNo = tanNo;
	}
	// added by ankit for authorised signatory 
	private String authSignFNametr;
	private String authSignLNamestr;
	private String authSignMName;
	private String authSignDesignationstr;         
	private String authSignDinNoStr;
	private String authSignMailIdIdStr;
	private String authSignContactNoStr;
	private String authSignIdValueStr;
	private String authSignDelegationStr;
	private String authSignDtataStr;
	private String authSignGender; //1.0.0.18
	private String authSignDob; //1.0.0.18
	private String authSignPincodeId; //1.0.0.18
	private String authSignPincodeName; //1.0.0.18
	
	public String getAuthSignGender() { return authSignGender; }
	public void setAuthSignGender(String authSignGender) { this.authSignGender = authSignGender; }

	public String getAuthSignDob() { return authSignDob; }
	public void setAuthSignDob(String authSignDob) { this.authSignDob = authSignDob; }

	public String getAuthSignPincodeId() { return authSignPincodeId; }
	public void setAuthSignPincodeId(String authSignPincodeId) { this.authSignPincodeId = authSignPincodeId; }

	public String getAuthSignPincodeName() { return authSignPincodeName; }
	public void setAuthSignPincodeName(String authSignPincodeName) { this.authSignPincodeName = authSignPincodeName; }

	public String getAuthSignDtataStr() {
		return authSignDtataStr;
	}

	public void setAuthSignDtataStr(String authSignDtataStr) {
		this.authSignDtataStr = authSignDtataStr;
	}

	public String getAuthSignIdValueStr() {
		return authSignIdValueStr;
	}

	public void setAuthSignIdValueStr(String authSignIdValueStr) {
		this.authSignIdValueStr = authSignIdValueStr;
	}

	public String getHeightFeet() {
		return heightFeet;
	}

	public void setHeightFeet(String heightFeet) {
		this.heightFeet = heightFeet;
	}

	public String getHeightInches() {
		return heightInches;
	}

	public void setHeightInches(String heightInches) {
		this.heightInches = heightInches;
	}

	public String getHeightCms() {
		return heightCms;
	}

	public void setHeightCms(String heightCms) {
		this.heightCms = heightCms;
	}

	public String getMedicalFitness() {
		return medicalFitness;
	}

	public void setMedicalFitness(String medicalFitness) {
		this.medicalFitness = medicalFitness;
	}

	public String getSalesManager() {
		return salesManager;
	}

	public void setSalesManager(String salesManager) {
		this.salesManager = salesManager;
	}

	public String getSalesManager_temp() {
		return salesManager_temp;
	}

	public void setSalesManager_temp(String salesManager_temp) {
		this.salesManager_temp = salesManager_temp;
	}

	
	public File getTheFile() {
		return theFile;
	}

	public void setTheFile(File theFile) {
		this.theFile = theFile;
	}

	public byte[] getFileData() {
		return fileData;
	}

	public void setFileData(byte[] fileData) {
		this.fileData = fileData;
	}

	/**
	 * @return the businessDate
	 */
	public String getBusinessDate() {
		return businessDate;
	}

	/**
	 * @param businessDate the businessDate to set
	 */
	public void setBusinessDate(String businessDate) {
		this.businessDate = businessDate;
	}

	public String getCustEntityType() {
		return custEntityType;
	}

	public void setCustEntityType(String custEntityType) {
		this.custEntityType = custEntityType;
	}

	public String getNameOfCompany() {
		return nameOfCompany;
	}

	public void setNameOfCompany(String nameOfCompany) {
		this.nameOfCompany = nameOfCompany;
	}

	public String getAuthSignatoryFirstName() {
		return authSignatoryFirstName;
	}

	public void setAuthSignatoryFirstName(String authSignatoryFirstName) {
		this.authSignatoryFirstName = authSignatoryFirstName;
	}

	public String getAuthSignatoryMiddleName() {
		return authSignatoryMiddleName;
	}

	public void setAuthSignatoryMiddleName(String authSignatoryMiddleName) {
		this.authSignatoryMiddleName = authSignatoryMiddleName;
	}

	public String getAuthSignatoryLastName() {
		return authSignatoryLastName;
	}

	public void setAuthSignatoryLastName(String authSignatoryLastName) {
		this.authSignatoryLastName = authSignatoryLastName;
	}

	public String getDateOfIncorporation() {
		return dateOfIncorporation;
	}

	public void setDateOfIncorporation(String dateOfIncorporation) {
		this.dateOfIncorporation = dateOfIncorporation;
	}

	public String getCompanyPan() {
		return companyPan;
	}

	public void setCompanyPan(String companyPan) {
		this.companyPan = companyPan;
	}

	public String getOffCompanyName() {
		return offCompanyName;
	}

	public void setOffCompanyName(String offCompanyName) {
		this.offCompanyName = offCompanyName;
	}

	public String getDnsReasonTemp() {
		return dnsReasonTemp;
	}

	public void setDnsReasonTemp(String dnsReasonTemp) {
		this.dnsReasonTemp = dnsReasonTemp;
	}
	
	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	
	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getGender() {
		return gender;
	}

	/**
	 * @return the maritalStatus
	 */
	public String getMaritalStatus() {
		return maritalStatus;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getFatherFirstName() {
		return fatherFirstName;
	}

	public void setFatherFirstName(String fatherFirstName) {
		this.fatherFirstName = fatherFirstName;
	}

	public String getFatherMiddleName() {
		return fatherMiddleName;
	}

	public void setFatherMiddleName(String fatherMiddleName) {
		this.fatherMiddleName = fatherMiddleName;
	}

	public String getFatherLastName() {
		return fatherLastName;
	}

	public void setFatherLastName(String fatherLastName) {
		this.fatherLastName = fatherLastName;
	}

	public String getSpouseFirstName() {
		return spouseFirstName;
	}

	public void setSpouseFirstName(String spouseFirstName) {
		this.spouseFirstName = spouseFirstName;
	}

	public String getSpouseMiddleName() {
		return spouseMiddleName;
	}

	public void setSpouseMiddleName(String spouseMiddleName) {
		this.spouseMiddleName = spouseMiddleName;
	}

	public String getSpouseLastName() {
		return spouseLastName;
	}

	public void setSpouseLastName(String spouseLastName) {
		this.spouseLastName = spouseLastName;
	}

	

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public String getVoterId() {
		return voterId;
	}

	public void setVoterId(String voterId) {
		this.voterId = voterId;
	}

	public String getDrivingLicenseNo() {
		return drivingLicenseNo;
	}

	public void setDrivingLicenseNo(String drivingLicenseNo) {
		this.drivingLicenseNo = drivingLicenseNo;
	}

	public String getPanNo() {
		return panNo;
	}

	public void setPanNo(String panNo) {
		this.panNo = panNo;
	}

	public String getPassportNo() {
		return passportNo;
	}

	public void setPassportNo(String passportNo) {
		this.passportNo = passportNo;
	}

	public String getNlFirstName() {
		return nlFirstName;
	}

	public void setNlFirstName(String nlFirstName) {
		this.nlFirstName = nlFirstName;
	}

	public String getNlSecondName() {
		return nlSecondName;
	}

	public void setNlSecondName(String nlSecondName) {
		this.nlSecondName = nlSecondName;
	}

	public String getNlLastName() {
		return nlLastName;
	}

	public void setNlLastName(String nlLastName) {
		this.nlLastName = nlLastName;
	}

	public String getDnsRequired() {
		return dnsRequired;
	}

	public void setDnsRequired(String dnsRequired) {
		this.dnsRequired = dnsRequired;
	}

	public String getDnsReason() {
		return dnsReason;
	}

	public void setDnsReason(String dnsReason) {
		this.dnsReason = dnsReason;
	}

	public String getPreferredTimeToCall() {
		return preferredTimeToCall;
	}

	public void setPreferredTimeToCall(String preferredTimeToCall) {
		this.preferredTimeToCall = preferredTimeToCall;
	}

	public String getPreferredModeOfCommunication() {
		return preferredModeOfCommunication;
	}

	public void setPreferredModeOfCommunication(String preferredModeOfCommunication) {
		this.preferredModeOfCommunication = preferredModeOfCommunication;
	}

	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	/** 
	 * Method reset
	 * @param mapping
	 * @param request
	 */
	

	

	public String getApplicantCode() {
		return applicantCode;
	}

	public void setApplicantCode(String applicantCode) {
		this.applicantCode = applicantCode;
	}

	public String getMotherFirstName() {
		return motherFirstName;
	}

	public void setMotherFirstName(String motherFirstName) {
		this.motherFirstName = motherFirstName;
	}

	public String getMotherMiddleName() {
		return motherMiddleName;
	}

	public void setMotherMiddleName(String motherMiddleName) {
		this.motherMiddleName = motherMiddleName;
	}

	public String getMotherLastName() {
		return motherLastName;
	}

	public void setMotherLastName(String motherLastName) {
		this.motherLastName = motherLastName;
	}

	

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the religion
	 */
	public String getReligion() {
		return religion;
	}

	/**
	 * @param religion the religion to set
	 */
	public void setReligion(String religion) {
		this.religion = religion;
	}

	/**
	 * @return the spouseTitle
	 */
	public String getSpouseTitle() {
		return spouseTitle;
	}

	/**
	 * @param spouseTitle the spouseTitle to set
	 */
	public void setSpouseTitle(String spouseTitle) {
		this.spouseTitle = spouseTitle;
	}

	/**
	 * @return the fatherTitle
	 */
	public String getFatherTitle() {
		return fatherTitle;
	}

	/**
	 * @param fatherTitle the fatherTitle to set
	 */
	public void setFatherTitle(String fatherTitle) {
		this.fatherTitle = fatherTitle;
	}

	/**
	 * @return the spouseOccupation
	 */
	public String getSpouseOccupation() {
		return spouseOccupation;
	}

	/**
	 * @param spouseOccupation the spouseOccupation to set
	 */
	public void setSpouseOccupation(String spouseOccupation) {
		this.spouseOccupation = spouseOccupation;
	}

	/**
	 * @return the spouseNameOfCompany
	 */
	public String getSpouseNameOfCompany() {
		return spouseNameOfCompany;
	}

	/**
	 * @param spouseNameOfCompany the spouseNameOfCompany to set
	 */
	public void setSpouseNameOfCompany(String spouseNameOfCompany) {
		this.spouseNameOfCompany = spouseNameOfCompany;
	}

	/**
	 * @return the custCategory
	 */
	public String getCustCategory() {
		return custCategory;
	}

	/**
	 * @param custCategory the custCategory to set
	 */
	public void setCustCategory(String custCategory) {
		this.custCategory = custCategory;
	}

	/**
	 * @return the eduQualification
	 */
	

	/**
	 * @return the highestQualification
	 */

	/**
	 * @param highestQualification the highestQualification to set
	 */
	/**
	 * @return the degree
	 */
	public String getDegree() {
		return degree;
	}

	/**
	 * @param degree the degree to set
	 */
	public void setDegree(String degree) {
		this.degree = degree;
	}

	/**
	 * @return the collegeLastAttended
	 */
	public String getCollegeLastAttended() {
		return collegeLastAttended;
	}

	/**
	 * @param collegeLastAttended the collegeLastAttended to set
	 */
	public void setCollegeLastAttended(String collegeLastAttended) {
		this.collegeLastAttended = collegeLastAttended;
	}

	/**
	 * @return the isPanNo
	 */
	public String getIsPanNo() {
		return isPanNo;
	}

	/**
	 * @param isPanNo the isPanNo to set
	 */
	public void setIsPanNo(String isPanNo) {
		this.isPanNo = isPanNo;
	}

	/**
	 * @return the declaration
	 */
	public String getDeclaration() {
		return declaration;
	}

	/**
	 * @param declaration the declaration to set
	 */
	public void setDeclaration(String declaration) {
		this.declaration = declaration;
	}

	
	/**
	 * @return the applicantTypeCode
	 */
	public String getApplicantTypeCode() {
		return applicantTypeCode;
	}

	/**
	 * @param applicantTypeCode the applicantTypeCode to set
	 */
	public void setApplicantTypeCode(String applicantTypeCode) {
		this.applicantTypeCode = applicantTypeCode;
	}

	
	/**
	 * @return the selApplicantType
	 */
	
	/**
	 * @return the selGender
	 */
	public String getSelGender() {
		return selGender;
	}

	public String getApplicationTypeId() {
		return applicationTypeId;
	}

	public void setApplicationTypeId(String applicationTypeId) {
		this.applicationTypeId = applicationTypeId;
	}

	public String getApplicantID() {
		return applicantID;
	}

	public void setApplicantID(String applicantID) {
		this.applicantID = applicantID;
	}

	public String getApplicantType() {
		return applicantType;
	}

	public void setApplicantType(String applicantType) {
		this.applicantType = applicantType;
	}

	public String getSelApplicantType() {
		return selApplicantType;
	}

	public void setSelApplicantType(String selApplicantType) {
		this.selApplicantType = selApplicantType;
	}

	public String getConstitution() {
		return constitution;
	}

	public void setConstitution(String constitution) {
		this.constitution = constitution;
	}

	public String getNoOfDependents() {
		return noOfDependents;
	}

	public void setNoOfDependents(String noOfDependents) {
		this.noOfDependents = noOfDependents;
	}

	public String getEduQualification() {
		return eduQualification;
	}

	public void setEduQualification(String eduQualification) {
		this.eduQualification = eduQualification;
	}

	public String getHighestQualification() {
		return highestQualification;
	}

	public void setHighestQualification(String highestQualification) {
		this.highestQualification = highestQualification;
	}

	public String getBasic() {
		return basic;
	}

	public void setBasic(String basic) {
		this.basic = basic;
	}

	public String getDa() {
		return da;
	}

	public void setDa(String da) {
		this.da = da;
	}

	public String getHra() {
		return hra;
	}

	public void setHra(String hra) {
		this.hra = hra;
	}

	public String getSpAllowance() {
		return spAllowance;
	}

	public void setSpAllowance(String spAllowance) {
		this.spAllowance = spAllowance;
	}

	public String getMedicalLta() {
		return medicalLta;
	}

	public void setMedicalLta(String medicalLta) {
		this.medicalLta = medicalLta;
	}

	public String getBonusIncentive() {
		return bonusIncentive;
	}

	public void setBonusIncentive(String bonusIncentive) {
		this.bonusIncentive = bonusIncentive;
	}

	public String getOthers() {
		return others;
	}

	public void setOthers(String others) {
		this.others = others;
	}

	public String getGrossIncome() {
		return grossIncome;
	}

	public void setGrossIncome(String grossIncome) {
		this.grossIncome = grossIncome;
	}

	public String getOtherIncome() {
		return otherIncome;
	}

	public void setOtherIncome(String otherIncome) {
		this.otherIncome = otherIncome;
	}

	public String getNetIncome() {
		return netIncome;
	}

	public void setNetIncome(String netIncome) {
		this.netIncome = netIncome;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	/**
	 * @param selGender the selGender to set
	 */
	public void setSelGender(String selGender) {
		this.selGender = selGender;
	}

	/**
	 * @return the relation
	 */
	public String getRelation() {
		return relation;
	}

	/**
	 * @param relation the relation to set
	 */
	public void setRelation(String relation) {
		this.relation = relation;
	}

	public String getRelationAppList() {
		return relationAppList;
	}

	public void setRelationAppList(String relationAppList) {
		this.relationAppList = relationAppList;
	}

	public String getAddressType() {
		return addressType;
	}

	public void setAddressType(String addressType) {
		this.addressType = addressType;
	}

	public String getSameAs() {
		return sameAs;
	}

	public void setSameAs(String sameAs) {
		this.sameAs = sameAs;
	}

	public String getAccomodationType() {
		return accomodationType;
	}

	public void setAccomodationType(String accomodationType) {
		this.accomodationType = accomodationType;
	}

	public String getMailingAddress() {
		return mailingAddress;
	}

	public void setMailingAddress(String mailingAddress) {
		this.mailingAddress = mailingAddress;
	}

	public String getFlatHouseNo() {
		return flatHouseNo;
	}

	public void setFlatHouseNo(String flatHouseNo) {
		this.flatHouseNo = flatHouseNo;
	}

	public String getFloorNo() {
		return floorNo;
	}

	public void setFloorNo(String floorNo) {
		this.floorNo = floorNo;
	}

	public String getBuildingName() {
		return buildingName;
	}

	public void setBuildingName(String buildingName) {
		this.buildingName = buildingName;
	}

	public String getLocality() {
		return locality;
	}

	public void setLocality(String locality) {
		this.locality = locality;
	}

	public String getLandMark() {
		return landMark;
	}

	public void setLandMark(String landMark) {
		this.landMark = landMark;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZipPin() {
		return zipPin;
	}

	public void setZipPin(String zipPin) {
		this.zipPin = zipPin;
	}

	public String getExtention1() {
		return extention1;
	}

	public void setExtention1(String extention1) {
		this.extention1 = extention1;
	}

	public String getPhoneNo1() {
		return phoneNo1;
	}

	public void setPhoneNo1(String phoneNo1) {
		this.phoneNo1 = phoneNo1;
	}

	public String getExtension2() {
		return extension2;
	}

	public void setExtension2(String extension2) {
		this.extension2 = extension2;
	}

	public String getPhone2() {
		return phone2;
	}

	public void setPhone2(String phone2) {
		this.phone2 = phone2;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getStdIsd() {
		return stdIsd;
	}

	public void setStdIsd(String stdIsd) {
		this.stdIsd = stdIsd;
	}

	public String getOwnership() {
		return ownership;
	}

	public void setOwnership(String ownership) {
		this.ownership = ownership;
	}

	public String getApplicantAddressId() {
		return applicantAddressId;
	}

	public void setApplicantAddressId(String applicantAddressId) {
		this.applicantAddressId = applicantAddressId;
	}



	public String getAddrApplicationTypeId() {
		return addrApplicationTypeId;
	}

	public void setAddrApplicationTypeId(String addrApplicationTypeId) {
		this.addrApplicationTypeId = addrApplicationTypeId;
	}

	public String getAddrApplicantId() {
		return addrApplicantId;
	}

	public void setAddrApplicantId(String addrApplicantId) {
		this.addrApplicantId = addrApplicantId;
	}

	public String getNoOfYears() {
		return noOfYears;
	}

	public void setNoOfYears(String noOfYears) {
		this.noOfYears = noOfYears;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getCreatedDateTime() {
		return createdDateTime;
	}

	public void setCreatedDateTime(String createdDateTime) {
		this.createdDateTime = createdDateTime;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getUpdateDateTime() {
		return updateDateTime;
	}

	public void setUpdateDateTime(String updateDateTime) {
		this.updateDateTime = updateDateTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getState_temp() {
		return state_temp;
	}

	public void setState_temp(String state_temp) {
		this.state_temp = state_temp;
	}

	public String getCity_temp() {
		return city_temp;
	}

	public void setCity_temp(String city_temp) {
		this.city_temp = city_temp;
	}

	public String getAddrApplicantType() {
		return addrApplicantType;
	}

	public void setAddrApplicantType(String addrApplicantType) {
		this.addrApplicantType = addrApplicantType;
	}

	public String getNoOfYearAtResidence() {
		return noOfYearAtResidence;
	}

	public void setNoOfYearAtResidence(String noOfYearAtResidence) {
		this.noOfYearAtResidence = noOfYearAtResidence;
	}

	public String getNoOfMonthAtResidence() {
		return noOfMonthAtResidence;
	}

	public void setNoOfMonthAtResidence(String noOfMonthAtResidence) {
		this.noOfMonthAtResidence = noOfMonthAtResidence;
	}

	public String getResidenceStatus() {
		return residenceStatus;
	}

	public void setResidenceStatus(String residenceStatus) {
		this.residenceStatus = residenceStatus;
	}
	
	public String getMailingAddressStatus() {
		return mailingAddressStatus;
	}

	public void setMailingAddressStatus(String mailingAddressStatus) {
		this.mailingAddressStatus = mailingAddressStatus;
	}
	public String getAllProperty()
    {
    	String allPropertyNameAndvalue="";
    	
    	allPropertyNameAndvalue=allPropertyNameAndvalue+"                                                                                relation=  "+this.relation+"\r\n";
    	allPropertyNameAndvalue=allPropertyNameAndvalue+"                                                                                applicationTypeId=  "+this.applicationTypeId+"\r\n";
    	allPropertyNameAndvalue=allPropertyNameAndvalue+"                                                                                applicantID=  "+this.applicantID+"\r\n";
    	allPropertyNameAndvalue=allPropertyNameAndvalue+"                                                                                applicantCode=  "+this.applicantCode+"\r\n";
    	allPropertyNameAndvalue=allPropertyNameAndvalue+"                                                                                applicantType=  "+this.applicantType+"\r\n";
    	allPropertyNameAndvalue=allPropertyNameAndvalue+"                                                                                relationAppList=  "+this.relationAppList+"\r\n";
    	allPropertyNameAndvalue=allPropertyNameAndvalue+"                                                                                applicantTypeCode=  "+this.applicantTypeCode+"\r\n";
    	allPropertyNameAndvalue=allPropertyNameAndvalue+"                                                                                selApplicantType=  "+this.selApplicantType+"\r\n";
    	allPropertyNameAndvalue=allPropertyNameAndvalue+"                                                                                title=  "+this.title+"\r\n";
    	allPropertyNameAndvalue=allPropertyNameAndvalue+"                                                                                religion=  "+this.religion+"\r\n";
    	allPropertyNameAndvalue=allPropertyNameAndvalue+"                                                                                firstName=  "+this.firstName+"\r\n";
    	allPropertyNameAndvalue=allPropertyNameAndvalue+"                                                                                middleName=  "+this.middleName+"\r\n";
    	allPropertyNameAndvalue=allPropertyNameAndvalue+"                                                                                lastName=  "+this.lastName+"\r\n";
    	allPropertyNameAndvalue=allPropertyNameAndvalue+"                                                                                maritalStatus=  "+this.maritalStatus+"\r\n";
    	allPropertyNameAndvalue=allPropertyNameAndvalue+"                                                                                gender=  "+this.gender+"\r\n";
    	allPropertyNameAndvalue=allPropertyNameAndvalue+"                                                                                selGender=  "+this.selGender+"\r\n";
    	allPropertyNameAndvalue=allPropertyNameAndvalue+"                                                                                spouseTitle=  "+this.spouseTitle+"\r\n";
    	allPropertyNameAndvalue=allPropertyNameAndvalue+"                                                                                spouseFirstName=  "+this.spouseFirstName+"\r\n";
    	allPropertyNameAndvalue=allPropertyNameAndvalue+"                                                                                spouseMiddleName=  "+this.spouseMiddleName+"\r\n";
    	allPropertyNameAndvalue=allPropertyNameAndvalue+"                                                                                spouseLastName=  "+this.spouseLastName+"\r\n";
    	allPropertyNameAndvalue=allPropertyNameAndvalue+"                                                                                constitution=  "+this.constitution+"\r\n";
    	allPropertyNameAndvalue=allPropertyNameAndvalue+"                                                                                dnsRequired=  "+this.dnsRequired+"\r\n";
    	allPropertyNameAndvalue=allPropertyNameAndvalue+"                                                                                dnsReason=  "+this.dnsReason+"\r\n";
    	allPropertyNameAndvalue=allPropertyNameAndvalue+"                                                                                preferredTimeToCall=  "+this.preferredTimeToCall+"\r\n";
    	allPropertyNameAndvalue=allPropertyNameAndvalue+"                                                                                preferredModeOfCommunication=  "+this.preferredModeOfCommunication+"\r\n";
    	allPropertyNameAndvalue=allPropertyNameAndvalue+"                                                                                target=  "+this.target+"\r\n";
    	allPropertyNameAndvalue=allPropertyNameAndvalue+"                                                                                dateOfBirth=  "+this.dateOfBirth+"\r\n";
    	allPropertyNameAndvalue=allPropertyNameAndvalue+"                                                                                noOfDependents=  "+this.noOfDependents+"\r\n";
    	allPropertyNameAndvalue=allPropertyNameAndvalue+"                                                                                fatherTitle=  "+this.fatherTitle+"\r\n";
    	allPropertyNameAndvalue=allPropertyNameAndvalue+"                                                                                fatherFirstName=  "+this.fatherFirstName+"\r\n";
    	allPropertyNameAndvalue=allPropertyNameAndvalue+"                                                                                fatherMiddleName=  "+this.fatherMiddleName+"\r\n";
    	allPropertyNameAndvalue=allPropertyNameAndvalue+"                                                                                fatherLastName=  "+this.fatherLastName+"\r\n";
    	allPropertyNameAndvalue=allPropertyNameAndvalue+"                                                                                motherFirstName=  "+this.motherFirstName+"\r\n";
    	allPropertyNameAndvalue=allPropertyNameAndvalue+"                                                                                motherMiddleName=  "+this.motherMiddleName+"\r\n";
    	allPropertyNameAndvalue=allPropertyNameAndvalue+"                                                                                motherLastName=  "+this.motherLastName+"\r\n";
    	allPropertyNameAndvalue=allPropertyNameAndvalue+"                                                                                spouseOccupation=  "+this.spouseOccupation+"\r\n";
    	allPropertyNameAndvalue=allPropertyNameAndvalue+"                                                                                spouseNameOfCompany=  "+this.spouseNameOfCompany+"\r\n";
    	allPropertyNameAndvalue=allPropertyNameAndvalue+"                                                                                custCategory=  "+this.custCategory+"\r\n";
    	allPropertyNameAndvalue=allPropertyNameAndvalue+"                                                                                eduQualification=  "+this.eduQualification+"\r\n";
    	allPropertyNameAndvalue=allPropertyNameAndvalue+"                                                                                highestQualification=  "+this.highestQualification+"\r\n";
    	allPropertyNameAndvalue=allPropertyNameAndvalue+"                                                                                degree=  "+this.degree+"\r\n";
    	allPropertyNameAndvalue=allPropertyNameAndvalue+"                                                                                collegeLastAttended=  "+this.collegeLastAttended+"\r\n";
    	allPropertyNameAndvalue=allPropertyNameAndvalue+"                                                                                nationality=  "+this.nationality+"\r\n";
    	allPropertyNameAndvalue=allPropertyNameAndvalue+"                                                                                nlFirstName=  "+this.nlFirstName+"\r\n";
    	allPropertyNameAndvalue=allPropertyNameAndvalue+"                                                                                nlSecondName=  "+this.nlSecondName+"\r\n";
    	allPropertyNameAndvalue=allPropertyNameAndvalue+"                                                                                nlLastName=  "+this.nlLastName+"\r\n";
    	allPropertyNameAndvalue=allPropertyNameAndvalue+"                                                                                voterId=  "+this.voterId+"\r\n";
    	allPropertyNameAndvalue=allPropertyNameAndvalue+"                                                                                isPanNo=  "+this.isPanNo+"\r\n";
    	allPropertyNameAndvalue=allPropertyNameAndvalue+"                                                                                panNo=  "+this.panNo+"\r\n";
    	allPropertyNameAndvalue=allPropertyNameAndvalue+"                                                                                declaration=  "+this.declaration+"\r\n";
    	allPropertyNameAndvalue=allPropertyNameAndvalue+"                                                                                drivingLicenseNo=  "+this.drivingLicenseNo+"\r\n";
    	allPropertyNameAndvalue=allPropertyNameAndvalue+"                                                                                passportNo=  "+this.passportNo+"\r\n";
    	allPropertyNameAndvalue=allPropertyNameAndvalue+"                                                                                basic=  "+this.basic+"\r\n";
    	allPropertyNameAndvalue=allPropertyNameAndvalue+"                                                                                da=  "+this.da+"\r\n";
    	allPropertyNameAndvalue=allPropertyNameAndvalue+"                                                                                hra=  "+this.hra+"\r\n";
    	allPropertyNameAndvalue=allPropertyNameAndvalue+"                                                                                spAllowance=  "+this.spAllowance+"\r\n";
    	allPropertyNameAndvalue=allPropertyNameAndvalue+"                                                                                medicalLta=  "+this.medicalLta+"\r\n";
    	allPropertyNameAndvalue=allPropertyNameAndvalue+"                                                                                bonusIncentive=  "+this.bonusIncentive+"\r\n";
    	allPropertyNameAndvalue=allPropertyNameAndvalue+"                                                                                others=  "+this.others+"\r\n";
    	allPropertyNameAndvalue=allPropertyNameAndvalue+"                                                                                grossIncome=  "+this.grossIncome+"\r\n";
    	allPropertyNameAndvalue=allPropertyNameAndvalue+"                                                                                otherIncome=  "+this.otherIncome+"\r\n";
    	allPropertyNameAndvalue=allPropertyNameAndvalue+"                                                                                netIncome=  "+this.netIncome+"\r\n";
    	allPropertyNameAndvalue=allPropertyNameAndvalue+"                                                                                dnsReasonTemp =  "+this.dnsReasonTemp +"\r\n";
    	allPropertyNameAndvalue=allPropertyNameAndvalue+"                                                                                nameOfCompany=  "+this.nameOfCompany+"\r\n";
    	allPropertyNameAndvalue=allPropertyNameAndvalue+"                                                                                authSignatoryFirstName=  "+this.authSignatoryFirstName+"\r\n";
    	allPropertyNameAndvalue=allPropertyNameAndvalue+"                                                                                authSignatoryMiddleName=  "+this.authSignatoryMiddleName+"\r\n";
    	allPropertyNameAndvalue=allPropertyNameAndvalue+"                                                                                authSignatoryLastName=  "+this.authSignatoryLastName+"\r\n";
    	allPropertyNameAndvalue=allPropertyNameAndvalue+"                                                                                dateOfIncorporation=  "+this.dateOfIncorporation+"\r\n";
    	allPropertyNameAndvalue=allPropertyNameAndvalue+"                                                                                companyPan=  "+this.companyPan+"\r\n";
    	allPropertyNameAndvalue=allPropertyNameAndvalue+"                                                                                addrApplicationTypeId=  "+this.addrApplicationTypeId+"\r\n";
    	allPropertyNameAndvalue=allPropertyNameAndvalue+"                                                                                addrApplicantType=  "+this.addrApplicantType+"\r\n";
    	allPropertyNameAndvalue=allPropertyNameAndvalue+"                                                                                addressType=  "+this.addressType+"\r\n";
    	allPropertyNameAndvalue=allPropertyNameAndvalue+"                                                                                sameAs=  "+this.sameAs+"\r\n";
    	allPropertyNameAndvalue=allPropertyNameAndvalue+"                                                                                accomodationType=  "+this.accomodationType+"\r\n";
    	allPropertyNameAndvalue=allPropertyNameAndvalue+"                                                                                mailingAddress=  "+this.mailingAddress+"\r\n";
    	allPropertyNameAndvalue=allPropertyNameAndvalue+"                                                                                flatHouseNo=  "+this.flatHouseNo+"\r\n";
    	allPropertyNameAndvalue=allPropertyNameAndvalue+"                                                                                floorNo=  "+this.floorNo+"\r\n";
    	allPropertyNameAndvalue=allPropertyNameAndvalue+"                                                                                buildingName=  "+this.buildingName+"\r\n";
    	allPropertyNameAndvalue=allPropertyNameAndvalue+"                                                                                locality=  "+this.locality+"\r\n";
    	allPropertyNameAndvalue=allPropertyNameAndvalue+"                                                                                landMark=  "+this.landMark+"\r\n";
    	allPropertyNameAndvalue=allPropertyNameAndvalue+"                                                                                city=  "+this.city+"\r\n";
    	allPropertyNameAndvalue=allPropertyNameAndvalue+"                                                                                state=  "+this.state+"\r\n";
    	allPropertyNameAndvalue=allPropertyNameAndvalue+"                                                                                zipPin=  "+this.zipPin+"\r\n";
    	allPropertyNameAndvalue=allPropertyNameAndvalue+"                                                                                extention1=  "+this.extention1+"\r\n";
    	allPropertyNameAndvalue=allPropertyNameAndvalue+"                                                                                phoneNo1=  "+this.phoneNo1+"\r\n";
    	allPropertyNameAndvalue=allPropertyNameAndvalue+"                                                                                extension2=  "+this.extension2+"\r\n";
    	allPropertyNameAndvalue=allPropertyNameAndvalue+"                                                                                phone2=  "+this.phone2+"\r\n";
    	allPropertyNameAndvalue=allPropertyNameAndvalue+"                                                                                mobile=  "+this.mobile+"\r\n";
    	allPropertyNameAndvalue=allPropertyNameAndvalue+"                                                                                email=  "+this.email+"\r\n";
    	allPropertyNameAndvalue=allPropertyNameAndvalue+"                                                                                fax=  "+this.fax+"\r\n";
    	allPropertyNameAndvalue=allPropertyNameAndvalue+"                                                                                stdIsd=  "+this.stdIsd+"\r\n";
    	allPropertyNameAndvalue=allPropertyNameAndvalue+"                                                                                ownership=  "+this.ownership+"\r\n";
    	allPropertyNameAndvalue=allPropertyNameAndvalue+"                                                                                applicantAddressId=  "+this.applicantAddressId+"\r\n";
    	allPropertyNameAndvalue=allPropertyNameAndvalue+"                                                                                addrApplicantId=  "+this.addrApplicantId+"\r\n";
    	allPropertyNameAndvalue=allPropertyNameAndvalue+"                                                                                noOfYears=  "+this.noOfYears+"\r\n";
    	allPropertyNameAndvalue=allPropertyNameAndvalue+"                                                                                createdBy=  "+this.createdBy+"\r\n";
    	allPropertyNameAndvalue=allPropertyNameAndvalue+"                                                                                createdDateTime=  "+this.createdDateTime+"\r\n";
    	allPropertyNameAndvalue=allPropertyNameAndvalue+"                                                                                updatedBy=  "+this.updatedBy+"\r\n";
    	allPropertyNameAndvalue=allPropertyNameAndvalue+"                                                                                updateDateTime=  "+this.updateDateTime+"\r\n";
    	allPropertyNameAndvalue=allPropertyNameAndvalue+"                                                                                status=  "+this.status+"\r\n";
    	allPropertyNameAndvalue=allPropertyNameAndvalue+"                                                                                flag=  "+this.flag+"\r\n";
    	allPropertyNameAndvalue=allPropertyNameAndvalue+"                                                                                state_temp=  "+this.state_temp+"\r\n";
    	allPropertyNameAndvalue=allPropertyNameAndvalue+"                                                                                city_temp=  "+this.city_temp+"\r\n";
    	allPropertyNameAndvalue=allPropertyNameAndvalue+"                                                                                offCompanyName=  "+this.offCompanyName+"\r\n";
    	allPropertyNameAndvalue=allPropertyNameAndvalue+"                                                                                noOfYearAtResidence=  "+this.noOfYearAtResidence+"\r\n";
    	allPropertyNameAndvalue=allPropertyNameAndvalue+"                                                                                noOfMonthAtResidence=  "+this.noOfMonthAtResidence+"\r\n";
    	allPropertyNameAndvalue=allPropertyNameAndvalue+"                                                                                residenceStatus=  "+this.residenceStatus+"\r\n";
    	allPropertyNameAndvalue=allPropertyNameAndvalue+"                                                                                custEntityType=  "+this.custEntityType+"\r\n";
    	allPropertyNameAndvalue=allPropertyNameAndvalue+"                                                                                businessDate=  "+this.businessDate+"\r\n";
    	allPropertyNameAndvalue=allPropertyNameAndvalue+"                                                                                mailingAddressStatus=  "+this.mailingAddressStatus+"";

    	
    	return allPropertyNameAndvalue;
    }

	public String getNameOfGroup()
	{
		return nameOfGroup;
	}

	public void setNameOfGroup(String nameOfGroup)
	{
		this.nameOfGroup = nameOfGroup;
	}

	public String getRecruitedByFirstName()
	{
		return recruitedByFirstName;
	}

	public void setRecruitedByFirstName(String recruitedByFirstName)
	{
		this.recruitedByFirstName = recruitedByFirstName;
	}

	public String getRecruitedByMiddleName()
	{
		return recruitedByMiddleName;
	}

	public void setRecruitedByMiddleName(String recruitedByMiddleName)
	{
		this.recruitedByMiddleName = recruitedByMiddleName;
	}

	public String getRecruitedByLastName()
	{
		return recruitedByLastName;
	}

	public void setRecruitedByLastName(String recruitedByLastName)
	{
		this.recruitedByLastName = recruitedByLastName;
	}

	public String getTrainedDate()
	{
		return trainedDate;
	}

	public void setTrainedDate(String trainedDate)
	{
		this.trainedDate = trainedDate;
	}

	public String getRbiRegNo() {
		return rbiRegNo;
	}

	public void setRbiRegNo(String rbiRegNo) {
		this.rbiRegNo = rbiRegNo;
	}

	public FormFile getLegalForm() {
		return legalForm;
	}

	public void setLegalForm(FormFile legalForm) {
		this.legalForm = legalForm;
	}
        	public void setDnsRequired1(String dnsRequired1) {
		this.dnsRequired1 = dnsRequired1;
	}

	public String getDnsRequired1() {
		return dnsRequired1;
	}

	public String getKeyContactPerson() {
		return keyContactPerson;
	}

	public void setKeyContactPerson(String keyContactPerson) {
		this.keyContactPerson = keyContactPerson;
	}

	public String getAuthCapital() {
		return authCapital;
	}

	public void setAuthCapital(String authCapital) {
		this.authCapital = authCapital;
	}

	public String getIssuedCap() {
		return issuedCap;
	}

	public void setIssuedCap(String issuedCap) {
		this.issuedCap = issuedCap;
	}

	public String getBoardRepresentative() {
		return boardRepresentative;
	}

	public void setBoardRepresentative(String boardRepresentative) {
		this.boardRepresentative = boardRepresentative;
	}

	public String getLegalFormName() {
		return legalFormName;
	}

	public void setLegalFormName(String legalFormName) {
		this.legalFormName = legalFormName;
	}



	public String getVewPicId() {
		return vewPicId;
	}

	public void setVewPicId(String vewPicId) {
		this.vewPicId = vewPicId;
	}

	public String getLegalFormType() {
		return legalFormType;
	}

	public void setLegalFormType(String legalFormType) {
		this.legalFormType = legalFormType;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getTin() {
		return tin;
	}

	public void setTin(String tin) {
		this.tin = tin;
	}

	public String getAuthSignFNametr() {
		return authSignFNametr;
	}

	public void setAuthSignFNametr(String authSignFNametr) {
		this.authSignFNametr = authSignFNametr;
	}

	public String getAuthSignLNamestr() {
		return authSignLNamestr;
	}

	public void setAuthSignLNamestr(String authSignLNamestr) {
		this.authSignLNamestr = authSignLNamestr;
	}

	public String getAuthSignMName() {
		return authSignMName;
	}

	public void setAuthSignMName(String authSignMName) {
		this.authSignMName = authSignMName;
	}

	public String getAuthSignDesignationstr() {
		return authSignDesignationstr;
	}

	public void setAuthSignDesignationstr(String authSignDesignationstr) {
		this.authSignDesignationstr = authSignDesignationstr;
	}

	public String getAuthSignDinNoStr() {
		return authSignDinNoStr;
	}

	public void setAuthSignDinNoStr(String authSignDinNoStr) {
		this.authSignDinNoStr = authSignDinNoStr;
	}

	public String getAuthSignMailIdIdStr() {
		return authSignMailIdIdStr;
	}

	public void setAuthSignMailIdIdStr(String authSignMailIdIdStr) {
		this.authSignMailIdIdStr = authSignMailIdIdStr;
	}

	public String getAuthSignContactNoStr() {
		return authSignContactNoStr;
	}

	public void setAuthSignContactNoStr(String authSignContactNoStr) {
		this.authSignContactNoStr = authSignContactNoStr;
	}

	public String getAuthSignDelegationStr() {
		return authSignDelegationStr;
	}

	public void setAuthSignDelegationStr(String authSignDelegationStr) {
		this.authSignDelegationStr = authSignDelegationStr;
	}

	public String getCustGroupType() {
		return custGroupType;
	}

	public void setCustGroupType(String custGroupType) {
		this.custGroupType = custGroupType;
	}

	public String getZipPin_temp() {
		return zipPin_temp;
	}

	public void setZipPin_temp(String zipPin_temp) {
		this.zipPin_temp = zipPin_temp;
	}

	public String getCaste() {
		return caste;
	}

	public void setCaste(String caste) {
		this.caste = caste;
	}

	public String getPsl() {
		return psl;
	}

	public void setPsl(String psl) {
		this.psl = psl;
	}
	public String getCustomerSegment() {
		return customerSegment;
	}
	public void setCustomerSegment(String customerSegment) {
		this.customerSegment = customerSegment;
	}
	public String getNatureActivity() {
		return natureActivity;
	}
	public void setNatureActivity(String natureActivity) {
		this.natureActivity = natureActivity;
	}
	public String getOccupation() {
		return occupation;
	}
	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}
	//1.0.0.1 add start
	public String getGstinNo() {
		return gstinNo;
	}
	public void setGstinNo(String gstinNo) {
		this.gstinNo = gstinNo;
	}
	public String getDestinationAddValue() {
		return destinationAddValue;
	}
	public void setDestinationAddValue(String destinationAddValue) {
		this.destinationAddValue = destinationAddValue;
	}
	//1.0.0.1 add end
	
	///////---------Added By Apurva-------------
	
	 public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}
	public String getRelationShipManager() {
		return relationShipManager;
	}

	public void setRelationShipManager(String relationShipManager) {
		this.relationShipManager = relationShipManager;
	}
	public String getProductInstrestedIn() {
		return productInstrestedIn;
	}

	public void setProductInstrestedIn(String productInstrestedIn) {
		this.productInstrestedIn = productInstrestedIn;
	}
	public String getAssetCategory() {
		return assetCategory;
	}

	public void setAssetCategory(String assetCategory) {
		this.assetCategory = assetCategory;
	}
	public String getCompetition() {
		return competition;
	}

	public void setCompetition(String competition) {
		this.competition = competition;
	}
	public String getChannelType() {
		return channelType;
	}

	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}
	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}
	public String getKeyContactcontactType() {
		return keyContactcontactType;
	}

	public void setKeyContactcontactType(String keyContactcontactType) {
		this.keyContactcontactType = keyContactcontactType;
	}
	public String getKeyContactlob() {
		return keyContactlob;
	}

	public void setKeyContactlob(String keyContactlob) {
		this.keyContactlob = keyContactlob;
	}
	public String getKeyContactname() {
		return keyContactname;
	}

	public void setKeyContactname(String keyContactname) {
		this.keyContactname = keyContactname;
	}
	public String getKeyContactfirmName() {
		return keyContactfirmName;
	}

	public void setKeyContactfirmName(String keyContactfirmName) {
		this.keyContactfirmName = keyContactfirmName;
	}
	public String getKeyContactmobile() {
		return keyContactmobile;
	}

	public void setKeyContactmobile(String keyContactmobile) {
		this.keyContactmobile = keyContactmobile;
	}
	public String getKeyContactEmailId() {
		return keyContactEmailId;
	}

	public void setKeyContactEmailId(String keyContactEmailId) {
		this.keyContactEmailId = keyContactEmailId;
	}
	public String getKeyContactAddress() {
		return keyContactAddress;
	}

	public void setKeyContactAddress(String keyContactAddress) {
		this.keyContactAddress = keyContactAddress;
	}
	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}
	public String getSector() {
		return sector;
	}
    //ravinder add start
	public String getKycRiskCategory() {
		return kycRiskCategory;
	}

	public void setKycRiskCategory(String kycRiskCategory) {
		this.kycRiskCategory = kycRiskCategory;
	}
    //ravinder add end
    
    //1.0.0.16 start
	public String getGstRegistrationStatus() {
		return gstRegistrationStatus;
	}

	public void setGstRegistrationStatus(String gstRegistrationStatus) {
		this.gstRegistrationStatus = gstRegistrationStatus;
	}
    //1.0.0.16 end
	public void setSector(String sector) {
		this.sector = sector;
	}
	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}
	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}
	public String getLob() {
		return lob;
	}

	public void setLob(String lob) {
		this.lob = lob;
	}
	public String getKeyContactDataStr() {
		return keyContactDataStr;
	}

	public void setKeyContactDataStr(String keyContactDataStr) {
		this.keyContactDataStr = keyContactDataStr;
	}
	public String getCaseId() {
		return caseId;
	}

	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}
	public String getRemoveKeyContactDataStr() {
		return removeKeyContactDataStr;
	}
	

	public void setRemoveKeyContactDataStr(String removeKeyContactDataStr) {
		this.removeKeyContactDataStr = removeKeyContactDataStr;
	}
	
	public String getKeyContactsId() {
		return keyContactsId;
	}

	public void setKeyContactsId(String keyContactsId) {
		this.keyContactsId = keyContactsId;
	}
	public String getTemp_AssetCategory() {
		return temp_AssetCategory;
	}

	public void setTemp_AssetCategory(String temp_AssetCategory) {
		this.temp_AssetCategory = temp_AssetCategory;
	}
	public String getTemp_Competition() {
		return temp_Competition;
	}

	public void setTemp_Competition(String temp_Competition) {
		this.temp_Competition = temp_Competition;
	}
	public String getEntityIdForDm() {
		return entityIdForDm;
	}

	public void setEntityIdForDm(String entityIdForDm) {
		this.entityIdForDm = entityIdForDm;
	}
	public String getSez() {
		return sez;
	}

	public void setSez(String sez) {
		this.sez = sez;
	}
	public String getIgstApplicable() {
		return igstApplicable;
	}

	public void setIgstApplicable(String igstApplicable) {
		this.igstApplicable = igstApplicable;
	}
	public String getIgstpercent() {
		return igstpercent;
	}

	public void setIgstpercent(String igstpercent) {
		this.igstpercent = igstpercent;
	}
	public String getLutNo() {
		return lutNo;
	}

	public void setLutNo(String lutNo) {
		this.lutNo = lutNo;
	}

	
	public String getLoaCheckBox() {
		return loaCheckBox;
	}

	public void setLoaCheckBox(String loaCheckBox) {
		this.loaCheckBox = loaCheckBox;
	}
	public String getCaseCode() {
		return caseCode;
	}

	public void setCaseCode(String caseCode) {
		this.caseCode = caseCode;
	}
	public String getLobCorp() {
		return lobCorp;
	}

	public void setLobCorp(String lobCorp) {
		this.lobCorp = lobCorp;
	}
	public String getLegalFormTypeInd() {
		return legalFormTypeInd;
	}

	public void setLegalFormTypeInd(String legalFormTypeInd) {
		this.legalFormTypeInd = legalFormTypeInd;
	}
	public String getLegalFormTypeCorp() {
		return legalFormTypeCorp;
	}

	public void setLegalFormTypeCorp(String legalFormTypeCorp) {
		this.legalFormTypeCorp = legalFormTypeCorp;
	}
	public String getLessorGstType() {
		return lessorGstType;
	}

	public void setLessorGstType(String lessorGstType) {
		this.lessorGstType = lessorGstType;
	}
	public String getTemp_Product() {
		return temp_Product;
	}

	public void setTemp_Product(String temp_Product) {
		this.temp_Product = temp_Product;
	}
	public String getDmLeasseCode() {
		return dmLeasseCode;
	}

	public void setDmLeasseCode(String dmLeasseCode) {
		this.dmLeasseCode = dmLeasseCode;
	}
	private String branch;
	 private String relationShipManager;
	 private String productInstrestedIn;
	 private String assetCategory;
	 private String competition;
	 private String temp_AssetCategory;
	 private String temp_Competition;
	 private String channelType;
	 private String channelName;
	 private String agentName;
	 private String keyContactcontactType;
	 private String keyContactlob;
	 private String keyContactname;
	 private String keyContactfirmName;
	 private String keyContactmobile;
	 private String keyContactEmailId;
	 private String keyContactAddress;
	 private String udyamNoInd;//1.0.0.14
	 
	 
	 public String getUdyamNoInd() {
		return udyamNoInd;
	}

	public void setUdyamNoInd(String udyamNoInd) {
		this.udyamNoInd = udyamNoInd;
	}

	private String temp_Product;//1.0.0.2
	//////////////

	public String getQuotation_igstPer() {
		return quotation_igstPer;
	}

	public void setQuotation_igstPer(String quotation_igstPer) {
		this.quotation_igstPer = quotation_igstPer;
	}

	public String getQuotation_sgst_ugstPer() {
		return quotation_sgst_ugstPer;
	}

	public void setQuotation_sgst_ugstPer(String quotation_sgst_ugstPer) {
		this.quotation_sgst_ugstPer = quotation_sgst_ugstPer;
	}

	public String getQuotation_cgstPer() {
		return quotation_cgstPer;
	}

	public void setQuotation_cgstPer(String quotation_cgstPer) {
		this.quotation_cgstPer = quotation_cgstPer;
	}

	public String getQuotation_registrationState() {
		return quotation_registrationState;
	}

	public void setQuotation_registrationState(String quotation_registrationState) {
		this.quotation_registrationState = quotation_registrationState;
	}

	public String getQuotation_sez_zone() {
		return quotation_sez_zone;
	}

	public void setQuotation_sez_zone(String quotation_sez_zone) {
		this.quotation_sez_zone = quotation_sez_zone;
	}
	public String getRelationWithApplicant() {
		return relationWithApplicant;
	}

	public void setRelationWithApplicant(String relationWithApplicant) {
		this.relationWithApplicant = relationWithApplicant;
	}

	public String getKycRiskCategory_Corp() {
		return kycRiskCategory_Corp;
	}

	public void setKycRiskCategory_Corp(String kycRiskCategory_Corp) {
		this.kycRiskCategory_Corp = kycRiskCategory_Corp;
	}

	public String getAssetCatGpSaveStr() {
		return assetCatGpSaveStr;
	}

	public void setAssetCatGpSaveStr(String assetCatGpSaveStr) {
		this.assetCatGpSaveStr = assetCatGpSaveStr;
	}

	public String getFileNameDms() {
		return fileNameDms;
	}

	public void setFileNameDms(String fileNameDms) {
		this.fileNameDms = fileNameDms;
	}

	public String getQuotationCode() {
		return quotationCode;
	}

	public void setQuotationCode(String quotationCode) {
		this.quotationCode = quotationCode;
	}

	public String getNatureOfBusiness() {
		return natureOfBusiness;
	}

	public void setNatureOfBusiness(String natureOfBusiness) {
		this.natureOfBusiness = natureOfBusiness;
	}

	public String getNatureOfBusinessCorp() {
		return natureOfBusinessCorp;
	}

	public void setNatureOfBusinessCorp(String natureOfBusinessCorp) {
		this.natureOfBusinessCorp = natureOfBusinessCorp;
	}

	public String getNatureOfBusinessComn() {
		return natureOfBusinessComn;
	}

	public void setNatureOfBusinessComn(String natureOfBusinessComn) {
		this.natureOfBusinessComn = natureOfBusinessComn;
	}

	public String getPanVarifiedFlag() {
		return panVarifiedFlag;
	}

	public void setPanVarifiedFlag(String panVarifiedFlag) {
		this.panVarifiedFlag = panVarifiedFlag;
	}

	public String getPanVarifiedFlagComp() {
		return panVarifiedFlagComp;
	}

	public void setPanVarifiedFlagComp(String panVarifiedFlagComp) {
		this.panVarifiedFlagComp = panVarifiedFlagComp;
	}

	public String getGstRegistrationStatusCorp() {
		return gstRegistrationStatusCorp;
	}

	public void setGstRegistrationStatusCorp(String gstRegistrationStatusCorp) {
		this.gstRegistrationStatusCorp = gstRegistrationStatusCorp;
	}
	
	

}
