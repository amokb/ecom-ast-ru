package ru.ecom.mis.web.action.medcase;

import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.forms.validator.BaseValidatorForm;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.validators.DateString;

public class PrescriptionForm extends BaseValidatorForm {
	/** Дата начала */
	@Comment("Дата начала")
	@DateString @DoDateString
	public String getBeginDate() {return theBeginDate;}
	public void setBeginDate(String aBeginDate) {theBeginDate = aBeginDate;}

	/** Дата окончания */
	@Comment("Дата окончания")
	@DateString @DoDateString
	public String getEndDate() {return theEndDate;}
	public void setEndDate(String aEndDate) {theEndDate = aEndDate;}

	/** Отделение */
	@Comment("Отделение")
	public Long getDepartment() {return theDepartment;}
	public void setDepartment(Long aDepartment) {theDepartment = aDepartment;}

	/** Врач */
	@Comment("Врач")
	public Long getSpecialist() {return theSpecialist;}
	public void setSpecialist(Long aSpecialist) {theSpecialist = aSpecialist;}

	
	/** Тип назначения */
	@Comment("Тип назначения")
	public Long getPrescriptType() {return thePrescriptType;}
	public void setPrescriptType(Long aPrescriptType) {thePrescriptType = aPrescriptType;}

	/** Услуга */
	@Comment("Услуга")
	public Long getService() {return theService;}
	public void setService(Long aService) {theService = aService;}

	/** Подтип услуги */
	@Comment("Подтип услуги")
	public Long getServiceSubType() {return theServiceSubType;}
	public void setServiceSubType(Long aServiceSubType) {theServiceSubType = aServiceSubType;}

	/** Подтип услуги */
	private Long theServiceSubType;
	/** Услуга */
	private Long theService;
	/** Тип назначения */
	private Long thePrescriptType;
	/** Врач */
	private Long theSpecialist;
	/** Отделение */
	private Long theDepartment;
	/** Дата окончания */
	private String theEndDate;
	/** Дата начала */
	private String theBeginDate;
	
	/** Номер */
	@Comment("Номер")
	public String getNumber() {return theNumber;}
	public void setNumber(String aNumber) {theNumber = aNumber;}

	/** Номер */
	private String theNumber;
	
	/** Пациент */
	@Comment("Пациент")
	public Long getPatient() {return thePatient;}
	public void setPatient(Long aPatient) {thePatient = aPatient;}

	/** Пациент */
	private Long thePatient;
	
	/** Диагноз */
	@Comment("Диагноз")
	public Long getDiagnosis() {return theDiagnosis;}
	public void setDiagnosis(Long aDiagnosis) {theDiagnosis = aDiagnosis;}
	/** Диагноз */
	private Long theDiagnosis;

	/** Причина отмены */
	@Comment("Причина отмены")
	public Long getCancelReason() {return theCancelReason;}
	public void setCancelReason(Long aCancelReason) {theCancelReason = aCancelReason;}
	/** Причина отмены */
	private Long theCancelReason;
}
