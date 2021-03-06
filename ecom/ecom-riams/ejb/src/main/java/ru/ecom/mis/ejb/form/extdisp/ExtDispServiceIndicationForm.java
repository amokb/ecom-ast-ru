package ru.ecom.mis.ejb.form.extdisp;

import lombok.Setter;
import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.extdisp.ExtDispServiceIndication;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;

@EntityForm
@EntityFormPersistance(clazz = ExtDispServiceIndication.class)
@Comment("Показание к услуге дополнительной диспансеризации")
@WebTrail(comment = "Показание к услуге дополнительной диспансеризации", nameProperties= "id", list="entityParentList-extdisp_extDispServiceIndication.do", view="entityParentView-extdisp_extDispServiceIndication.do")
@Parent(property="visit", parentForm=ExtDispVisitForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/ExtDisp/Card/Service")
@Setter
public class ExtDispServiceIndicationForm extends IdEntityForm{
	/**
	 * Визит
	 */
	@Comment("Визит")
	@Persist
	public Long getVisit() {return visit;}
	/**
	 * Визит
	 */
	private Long visit;
	/**
	 * Тип услуги
	 */
	@Comment("Тип услуги")
	@Persist
	public Long getServiceType() {
		return serviceType;
	}
	/**
	 * Тип услуги
	 */
	private Long serviceType;
}
