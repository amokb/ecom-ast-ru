package ru.ecom.mis.ejb.form.patient;

import lombok.Setter;
import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.patient.voc.VocColorIdentityPatient;
import ru.ecom.mis.ejb.form.lpu.MisLpuForm;
import ru.nuzmsh.commons.formpersistence.annotation.*;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.validators.Required;

/**
 * Created by Milamesher on 30.04.2019.
 */

@EntityForm
@EntityFormPersistance(clazz= VocColorIdentityPatient.class)
@Comment("Доп. информация о пациенте (для браслета)")
@WebTrail(comment = "Доп. информация о пациенте (для браслета)", nameProperties="name", view="entityView-mis_colorIdentity.do", list="entityParentList-mis_colorIdentity.do")
@Parent(property="lpu", parentForm= MisLpuForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/ColorIdentityEdit")
@Setter
public class VocColorIdentityPatientForm  extends IdEntityForm {
    /** Примечание/болезнь, о которой должен сообщить браслет */
    @Comment("Примечание/болезнь, о которой должен сообщить браслет ")
    @Persist
    @Required
    public String getName() {return name;}
    /** Примечание/болезнь, о которой должен сообщить браслет  */
    private String name ;

    /** Может ли заполняться в родах? */
    @Comment("Может ли заполняться в родах?")
    @Persist
    public Boolean getIsForNewborn() {return isForNewborn;}
    /** Может ли заполняться в родах?  */
    private Boolean isForNewborn ;


    /** Цвет браслета*/
    @Persist
    public Long getColor() { return color ; }
    /** Цвет браслета*/
    private Long color;

    /** ЛПУ */
    @Comment("ЛПУ")
    @Persist
    public Long getLpu() {    return lpu ;}
    /** ЛПУ */
    private Long lpu ;

    /** Запрещено создавать вручную? */
    @Comment("Запрещено создавать вручную?")
    @Persist
    public Boolean getIsDeniedManual() {return isDeniedManual;}
    /** Запрещено создавать вручную?  */
    private Boolean isDeniedManual ;
}
