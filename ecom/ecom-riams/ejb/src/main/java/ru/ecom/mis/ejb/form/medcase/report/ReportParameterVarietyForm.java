package ru.ecom.mis.ejb.form.medcase.report;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.report.voc.ReportSetTypeParameterType;
import ru.nuzmsh.commons.formpersistence.annotation.*;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;

@EntityForm
@EntityFormPersistance(clazz= ReportSetTypeParameterType.class)
@Comment("Диагноз")
@WebTrail(comment = "Диапазон", nameProperties= "id", view="entityView-rep_parameterVariety.do")
@Parent(property="parameterType", parentForm= ReportParameterForm.class)
@EntityFormSecurityPrefix("/Policy/Voc/ReportConfig")
public class ReportParameterVarietyForm  extends IdEntityForm {

	/** Начальный параметр */
	@Comment("Начальный параметр")
	@Persist
	public String getCodeFrom() {return theCodeFrom;}
	public void setCodeFrom(String aCodeFrom) {theCodeFrom = aCodeFrom;}

	/** Последний параметер */
	@Comment("Последний параметр")
	@Persist
	public String getCodeTo() {return theCodeTo;}
	public void setCodeTo(String aCodeTo) {theCodeTo = aCodeTo;}

	/** Пол код */
	@Comment("Пол код")
	@Persist
	public String getSexCode() {return theSexCode;}
	public void setSexCode(String aSexCode) {theSexCode = aSexCode;}

	/** Пол */
	@Comment("Пол")
	@Persist
	public Long getSex() {return theSex;}
	public void setSex(Long aSex) {theSex = aSex;}

	/** Пол */
	private Long theSex;
	/** Пол код */
	private String theSexCode;
	/** Последний параметр */
	private String theCodeTo;
	/** Начальный параметр */
	private String theCodeFrom;
}
