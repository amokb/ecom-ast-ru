package ru.ecom.mis.ejb.form.medcase;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.ejb.services.entityform.interceptors.ACreateInterceptors;
import ru.ecom.ejb.services.entityform.interceptors.AEntityFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.ASaveInterceptors;
import ru.ecom.mis.ejb.domain.medcase.MedServiceComplexLink;
import ru.ecom.mis.ejb.form.medcase.interceptor.MedServiceComplexLinkSaveInterceptor;
import ru.nuzmsh.commons.formpersistence.annotation.*;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.validators.Required;

/**
 * Связь комплексной услуги и услуги, входящей в эту комплексную
 * @author Milamesher
 */
@EntityForm
@EntityFormPersistance(clazz=MedServiceComplexLink.class)
@Comment("Связь с комплексной услугой")
@WebTrail(comment = "Связь с комплексной услугой"
	, nameProperties= "innerMedService"
		, view="entityParentView-mis_medServiceComplexLink.do"
				)
@Parent(property="complexMedService", parentForm= MedServiceForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/MedService")
@ACreateInterceptors(
		@AEntityFormInterceptor(MedServiceComplexLinkSaveInterceptor.class)
)
@ASaveInterceptors(
		@AEntityFormInterceptor(MedServiceComplexLinkSaveInterceptor.class)
)
public class MedServiceComplexLinkForm extends IdEntityForm  {

	/** Комплексная мед. услуга */
	@Comment("Комплексная мед. услуга")
	@Persist
	@Required
	public Long getComplexMedService() {return theComplexMedService;}
	public void setComplexMedService(Long aComplexMedService) {theComplexMedService = aComplexMedService;}

	/** Комплексная мед. услуга */
	private Long theComplexMedService;

	/** Мед. услуга в программе комплексной*/
	@Comment("Мед. услуга в программе комплексной")
	@Persist
	@Required
	public Long getInnerMedService() {return theInnerMedService;}
	public void setInnerMedService(Long aInnerMedService) {theInnerMedService = aInnerMedService;}

	/** Мед. услуга в программе комплексной */
	private Long theInnerMedService;

	/** Количество */
	@Comment("Количество")
	@Persist
	@Required
	public Integer getCountInnerMedService() {
		return theCountInnerMedService;
	}
	public void setCountInnerMedService(Integer aCountInnerMedService) {
		theCountInnerMedService = aCountInnerMedService;
	}

	/** Количество */
	private Integer theCountInnerMedService;

}