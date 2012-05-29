package ru.ecom.mis.ejb.form.licence;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.Subclasses;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.licence.Document;
import ru.ecom.mis.ejb.form.medcase.MedCaseForm;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.validators.DateString;

@EntityForm
@EntityFormPersistance(clazz = Document.class)
@Comment("Документы")
@WebTrail(comment = "Документы", nameProperties = "id",view = "entitySubclassView-doc_document.do"
		,shortView="entityShortSubclassView-doc_document.do")
@Parent(property = "medCase", parentForm = MedCaseForm.class)
@Subclasses(value = { DischargeDocumentForm.class
		,DirectionDocumentForm.class })
@EntityFormSecurityPrefix("/Policy/Mis/MedCase/Document")
public class DocumentForm extends IdEntityForm{

    /** Серия документа */
	@Persist
    public String getSeriaDoc() { return theSeriaDoc ; }
    public void setSeriaDoc(String aSeriaDoc) { theSeriaDoc = aSeriaDoc ; }

    /** Номер документа */
    @Persist
    public String getNumberDoc() { return theNumberDoc ; }
    public void setNumberDoc(String aNumberDoc) { theNumberDoc = aNumberDoc ; }
    
    /** Дата выдачи */
	@Comment("Дата выдачи")
	@Persist @DoDateString @DateString
	public String getDateIssued() {return theDateIssued;}
	public void setDateIssued(String aDateIssued) {theDateIssued = aDateIssued;}

	/** Кем выдан */
	@Comment("Кем выдан")
	@Persist
	public String getWhomIssued() {return theWhomIssued;}
	public void setWhomIssued(String aWhomIssued) {theWhomIssued = aWhomIssued;}

    /** Дата создания */
	@Comment("Дата создания")
	@Persist @DoDateString @DateString
	public String getCreateDate() {return theCreateDate;}
	public void setCreateDate(String aCreateDate) {theCreateDate = aCreateDate;}
	
	/** Пользователь, создавший запись */
	@Comment("Пользователь, создавший запись")
	@Persist
	public String getCreateUsername() {return theCreateUsername;}
	public void setCreateUsername(String aCreateUsername) {theCreateUsername = aCreateUsername;}
	
	/** Дата редактирования */
	@Comment("Дата редактирования")
	@Persist @DoDateString @DateString
	public String getEditDate() {return theEditDate;}
	public void setEditDate(String aEditDate) {theEditDate = aEditDate;}
	
	/** Пользователь, последний редактировавший запись */
	@Comment("Пользователь, последний редактировавший запись")
	@Persist
	public String getEditUsername() {return theEditUsername;}
	public void setEditUsername(String aEditUsername) {theEditUsername = aEditUsername;}

	/** Пользователь, последний редактировавший запись */
	private String theEditUsername;
	/** Дата редактирования */
	private String theEditDate;
	/** Пользователь, создавший запись */
	private String theCreateUsername;
	/** Дата создания */
	private String theCreateDate;
	/** Кем выдан */
	private String theWhomIssued;
	/** Дата выдачи */
	private String theDateIssued;
  
    /** Серия документа */
    private String theSeriaDoc ;
    /** Номер документа */
    private String theNumberDoc ;

}
