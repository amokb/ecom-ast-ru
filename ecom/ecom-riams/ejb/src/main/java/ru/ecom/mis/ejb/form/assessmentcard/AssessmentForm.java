package ru.ecom.mis.ejb.form.assessmentcard;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.assessmentcard.AssessmentCard;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.validators.Required;

@EntityForm
@EntityFormPersistance(clazz= AssessmentCard.class)
@Comment("Итоговый балл")
@WebTrail(comment = "Итоговый балл", nameProperties= "id", view="entityParentView-mis_assessment.do" ,list = "entityParentList-mis_assessment.do")
@Parent(property = "assessmentCard", parentForm=AssessmentCardTemplateForm.class) 

@EntityFormSecurityPrefix("/Policy/Mis/AssessmentCard")
public class AssessmentForm extends IdEntityForm{

	/** Минимальное кол-во баллов */
	@Comment("Минимальное кол-во баллов")
	@Persist @Required
	public Integer getMinBall() {return theMinBall;}
	public void setMinBall(Integer aMinBall) {theMinBall = aMinBall;}
	/** Минимальное кол-во баллов */
	private Integer theMinBall;

	/** Максимальное количество баллов */
	@Comment("Максимальное количество баллов")
	@Persist @Required
	public Integer getMaxBall() {return theMaxBall;}
	public void setMaxBall(Integer aMaxBall) {theMaxBall = aMaxBall;}
	/** Максимальное количество баллов */
	private Integer theMaxBall;

	/** Тип карты оценки */
	@Comment("Тип карты оценки")
	@Persist
	public Long getAssessmentCard() {return theAssessmentCard;}
	public void setAssessmentCard(Long aAssessmentCard) {theAssessmentCard = aAssessmentCard;}
	/** Тип карты оценки */
	private Long theAssessmentCard;

}
