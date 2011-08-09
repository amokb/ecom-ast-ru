package ru.ecom.mis.ejb.form.prescription;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.Subclasses;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.prescription.Prescription;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.transforms.DoTimeString;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.Required;
import ru.nuzmsh.forms.validator.validators.TimeString;

/**
 * Назначение
 * @author oegorova
 *
 */

@EntityForm
@EntityFormPersistance(clazz = Prescription.class)
@Comment("Назначение")
@WebTrail(comment = "Назначение", nameProperties= "descriptionInfo",list="entityParentList-pres_prescription.do", view="entitySubclassView-pres_prescription.do")
@Parent(property="prescriptionList", parentForm=AbstractPrescriptionListForm.class)
@Subclasses({DrugPrescriptionForm.class, DietPrescriptionForm.class, ServicePrescriptionForm.class})
@EntityFormSecurityPrefix("/Policy/Mis/Prescription")
public class PrescriptionForm extends IdEntityForm{
	
	/** Лист назначений */
	@Comment("Лист назначений")
	@Persist
	public Long getPrescriptionList() {return thePrescriptionList;}
	public void setPrescriptionList(Long aPrescriptionList) {thePrescriptionList = aPrescriptionList;}

	/** Регистратор */
	@Comment("Регистратор")
	@Persist
	public Long getRegistrator() {return theRegistrator;}
	public void setRegistrator(Long aRegistrator) {theRegistrator = aRegistrator;}
	
	/** Плановая дата начала */
	@Comment("Плановая дата начала")
	@Persist @Required
	@DateString @DoDateString
	public String getPlanStartDate() {return thePlanStartDate;}
	public void setPlanStartDate(String aPlanStartDate) {thePlanStartDate = aPlanStartDate;}
	
	/** Плановое время начала */
	@Comment("Плановое время начала")
	@Persist @TimeString @DoTimeString @Required
	public String getPlanStartTime() {return thePlanStartTime;}
	public void setPlanStartTime(String aPlanStartTime) {thePlanStartTime = aPlanStartTime;}

	/** Плановая дата окончания */
	@Comment("Плановая дата окончания")
	@Persist @DateString @DoDateString
	public String getPlanEndDate() {return thePlanEndDate;}
	public void setPlanEndDate(String aPlanEndDate) {thePlanEndDate = aPlanEndDate;}

	/** Плановое время окончания */
	@Comment("Плановое время окончания")
	@Persist @TimeString @DoTimeString
	public String getPlanEndTime() {return thePlanEndTime;}
	public void setPlanEndTime(String aPlanEndTime) {thePlanEndTime = aPlanEndTime;}

	/** Дата отмены */
	@Comment("Дата отмены")
	@Persist @DateString @DoDateString
	public String getCancelDate() {return theCancelDate;}
	public void setCancelDate(String aCancelDate) {theCancelDate = aCancelDate;}

	/** Время отмены */
	@Comment("Время отмены")
	@Persist @TimeString @DoTimeString
	public String getCancelTime() {return theCancelTime;}
	public void setCancelTime(String aCancelTime) {theCancelTime = aCancelTime;}

	/** Причина отмены */
	@Comment("Причина отмены")
	@Persist
	public Long getCancelReason() {return theCancelReason;}
	public void setCancelReason(Long aCancelReason) {theCancelReason = aCancelReason;}

	/** Отменивший */
	@Comment("Отменивший")
	@Persist
	public Long getCancelSpecial() {return theCancelSpecial;}
	public void setCancelSpecial(Long aCancelWorker) {theCancelSpecial = aCancelWorker;}

	/** Отменивший (text) */
	@Comment("Отменивший (text)")
	@Persist
	public String getCancelWorkerInfo() {return theCancelWorkerInfo;}
	public void setCancelWorkerInfo (String aCancelWorkerInfo) {}

	/** Назначивший */
	@Comment("Назначивший")
	@Persist @Required
	public Long getPrescriptSpecial() {return thePrescriptSpecial;}
	public void setPrescriptSpecial(Long aPrescriptor) {thePrescriptSpecial = aPrescriptor;	}

	/** Назначивший (text) */
	@Comment("Назначивший (text)")
	@Persist
	public String getPrescriptorInfo() {return thePrescriptorInfo;}
	public void setPrescriptorInfo(String aPrescriptorInfo) {}
	
	/** Комментарии */
	@Comment("Комментарии")
	@Persist
	public String getComments() {return theComments;}
	public void setComments(String aComments) {theComments = aComments;}

	/** Описание */
	@Comment("Описание")
	@Persist
	public String getDescription() {return "";}
	public void setDescription(String aDescription) {}
	
	/** Состояние исполнения */
	@Comment("Состояние исполнения")
	@Persist
	public Long getFulfilmentState() {return theFulfilmentState;}
	public void setFulfilmentState(Long aFulfilmentState) {theFulfilmentState = aFulfilmentState;}
	 
	/** Регистратор (text) */
	@Comment("Регистратор (text)")
	@Persist
	public String getRegistratorInfo() {return theRegistratorInfo;}
	public void setRegistratorInfo(String aRegistratorInfo) {}

	/** Описание назначения */
	@Comment("Описание назначения")
	@Persist
	public String getDescriptionInfo() {return theDescriptionInfo;}
	public void setDescriptionInfo(String aDescriptionInfo) {theDescriptionInfo = aDescriptionInfo;}
	
	/** Дата и время назначения */
	@Comment("Дата и время назначения")
	@Persist
	public String getPrescriptTimeStamp() {return thePrescriptTimeStamp;}
	public void setPrescriptTimeStamp(String aPrescriptTimeStamp) {thePrescriptTimeStamp = aPrescriptTimeStamp;}
	
	/** Дата и время отмены назначения */
	@Comment("Дата и время отмены назначения")
	public String getPrescriptCancelTimeStamp() {return thePrescriptCancelTimeStamp;}
	public void setPrescriptCancelTimeStamp(String aPrescriptCancelTimeStamp) {thePrescriptCancelTimeStamp = aPrescriptCancelTimeStamp;}

	/** Подпись */
	@Comment("Подпись")
	@Persist
	public String getSignature() {return theSignature;}
	public void setSignature(String aSignature) {theSignature = aSignature;}

	/** Подпись */
	private String theSignature;
	/** Дата и время отмены назначения */
	private String thePrescriptCancelTimeStamp;
	/** Дата и время назначения */
	private String thePrescriptTimeStamp;
	/** Лист назначений */
	private Long thePrescriptionList;
	/** Регистратор */
	private Long theRegistrator;
	/** Плановая дата начала */
	private String thePlanStartDate;
	/** Плановое время начала */
	private String thePlanStartTime;
	/** Плановая дата окончания */
	private String thePlanEndDate;
	/** Плановое время окончания */
	private String thePlanEndTime;
	/** Дата отмены */
	private String theCancelDate;
	/** Время отмены */
	private String theCancelTime;
	/** Причина отмены */
	private Long theCancelReason;
	/** Отменивший */
	private Long theCancelSpecial;
	/** Отменивший (text) */
	private String theCancelWorkerInfo;
	/** Назначивший */
	private Long thePrescriptSpecial;
	/** Назначивший (text) */
	private String thePrescriptorInfo;
	/** Комментарии */
	private String theComments;
	/** Состояние исполнения */
	private Long theFulfilmentState;
	/** Регистратор (text) */
	private String theRegistratorInfo;
	/** Описание назначения */
	private String theDescriptionInfo;
	
//	 /** Стационарный? */
//	@Comment("Стационарный?")
//	@Persist
//	public boolean isInHospitalMedCase() {
//		return theInHospitalMedCase;
//	}
//
//	public void setInHospitalMedCase(boolean aInHospitalMedCase) {
//		theInHospitalMedCase = aInHospitalMedCase;
//	}
//
//	/** Стационарный? */
//	private boolean theInHospitalMedCase;
}

