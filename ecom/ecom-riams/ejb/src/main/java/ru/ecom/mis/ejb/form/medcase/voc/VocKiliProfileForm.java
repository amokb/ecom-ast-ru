package ru.ecom.mis.ejb.form.medcase.voc;

import lombok.Setter;
import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.medcase.voc.VocKiliProfile;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.validators.Required;

@EntityForm
@EntityFormPersistance(clazz = VocKiliProfile.class)
@Comment("Справочник профилей КИЛИ")
@WebTrail(comment = "Справочник профилей КИЛИ", nameProperties= "id", view="entityView-voc_kiliProfile.do")
@EntityFormSecurityPrefix("/Policy/Voc/VocKiliProfile")
@Setter
public class VocKiliProfileForm extends IdEntityForm{
	 /** Название */
    @Comment("Наименование")
    @Persist @Required
    public String getName() { return name ; }
    private String name;
}