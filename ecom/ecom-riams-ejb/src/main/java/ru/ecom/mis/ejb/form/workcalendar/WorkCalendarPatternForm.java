package ru.ecom.mis.ejb.form.workcalendar;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.Subclasses;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.workcalendar.WorkCalendarPattern;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoUpperCase;
import ru.nuzmsh.forms.validator.validators.Required;

@EntityForm
@EntityFormPersistance(clazz= WorkCalendarPattern.class)
@Comment("Шаблон рабочего календаря")
@WebTrail(comment = "Шаблон рабочего календаря", nameProperties= "name", view="entitySublassView-cal_pattern.do",list= "entityList-cal_pattern.do")
//@Parent(property="workFunction", parentForm=WorkFunctionForm.class)
@Subclasses(value = { WorkCalendarPatternByOperatingRoomForm.class,WorkCalendarPatternBySpecialistForm.class })
@EntityFormSecurityPrefix("/Policy/Mis/Worker/WorkCalendar/Pattern")
public class WorkCalendarPatternForm extends IdEntityForm {
	/**
	 * Тип календаря
	 */
	@Comment("Тип календаря")
	@Persist @Required
	public Long getCalendarType() {return theCalendarType;}
	public void setCalendarType(Long aCalendarType) {theCalendarType = aCalendarType;}
	/**
	 * Тип календаря
	 */
	private Long theCalendarType;
	/**
	 * Рабочая функция
	 */
	@Comment("Рабочая функция")
	@Persist
	public Long getWorkFunction() {return theWorkFunction;}
	public void setWorkFunction(Long aWorkFunction) {theWorkFunction = aWorkFunction;}
	/**
	 * Рабочая функция
	 */
	private Long theWorkFunction;
	/**
	 * Тип занятости
	 */
	@Comment("Тип занятости")
	@Persist 
	public Long getWorkBusy() {return theWorkBusy;}
	public void setWorkBusy(Long aWorkBusy) {theWorkBusy = aWorkBusy;}
	/**
	 * Тип занятости
	 */
	private Long theWorkBusy;
	/**
	 * Название
	 */
	@Comment("Название")
	@Persist @DoUpperCase @Required
	public String getName() {return theName;}
	public void setName(String aName) {theName = aName;}
	
	/**
	 * Название
	 */
	private String theName;
	
	/** ЛПУ */
	@Comment("ЛПУ")
	@Persist
	public Long getLpu() {return theLpu;}
	public void setLpu(Long aLpu) {theLpu = aLpu;}

	/** ЛПУ */
	private Long theLpu;
	
	/** Проф.день */
	@Comment("Проф.день")
	public WorkCalendarProphDayAlgorithmForm getProphDayAlgorithmForm() {return theProphDayAlgorithmForm;}
	public void setProphDayAlgorithmForm(WorkCalendarProphDayAlgorithmForm aProphDayAlgorithmForm) {theProphDayAlgorithmForm = aProphDayAlgorithmForm;}

	/** Проф.день */
	private WorkCalendarProphDayAlgorithmForm theProphDayAlgorithmForm = new WorkCalendarProphDayAlgorithmForm();
	
	/** Алгоритм на определенные дни */
	@Comment("Алгоритм на определенные дни")
	public WorkCalendarDatesAlgorithmForm getDatesAlgorithmForm() {return theDatesAlgorithmForm;}
	public void setDatesAlgorithmForm(WorkCalendarDatesAlgorithmForm aDatesAlgorithmForm) {theDatesAlgorithmForm = aDatesAlgorithmForm;}

	/** Алгоритм на определенные дни */
	private WorkCalendarDatesAlgorithmForm theDatesAlgorithmForm = new WorkCalendarDatesAlgorithmForm();
	
	/** Алгоритм на недели */
	@Comment("Алгоритм на недели")
	public WorkCalendarWeekAlgorithmForm getWeekAlgorithmForm() {return theWeekAlgorithmForm;}
	public void setWeekAlgorithmForm(WorkCalendarWeekAlgorithmForm aWeekAlgorithmForm) {theWeekAlgorithmForm = aWeekAlgorithmForm;}

	/** Алгоритм на недели */
	private WorkCalendarWeekAlgorithmForm theWeekAlgorithmForm = new WorkCalendarWeekAlgorithmForm() ;
	
	/** Алгоритм по дням недели */
	@Comment("Алгоритм по дням недели")
	public WorkCalendarWeekDaysAlgorithmForm getWeekDaysAlgorithmForm() {return theWeekDaysAlgorithmForm;}
	public void setWeekDaysAlgorithmForm(WorkCalendarWeekDaysAlgorithmForm aWeekDaysAlgorithmForm) {theWeekDaysAlgorithmForm = aWeekDaysAlgorithmForm;}

	/** Алгоритм по дням недели */
	private WorkCalendarWeekDaysAlgorithmForm theWeekDaysAlgorithmForm = new WorkCalendarWeekDaysAlgorithmForm();
	
	/** Рабочий день1 */
	@Comment("Рабочий день1")
	public WorkCalendarDayPatternForm getDayPattern1Form() {return theDayPattern1Form;}
	public void setDayPattern1Form(WorkCalendarDayPatternForm aDayPattern1Form) {theDayPattern1Form = aDayPattern1Form;}

	/** Рабочий день1 */
	private WorkCalendarDayPatternForm theDayPattern1Form = new WorkCalendarDayPatternForm();
	
	/** Рабочий день 2 */
	@Comment("Рабочий день 2")
	public WorkCalendarDayPatternForm getDayPattern2Form() {return theDayPattern2Form;}
	public void setDayPattern2Form(WorkCalendarDayPatternForm aDayPattern2Form) {theDayPattern2Form = aDayPattern2Form;}

	/** Рабочий день 2 */
	private WorkCalendarDayPatternForm theDayPattern2Form = new WorkCalendarDayPatternForm();
	
	/** Рабочий день 3 */
	@Comment("Рабочий день 3")
	public WorkCalendarDayPatternForm getDayPattern3Form() {return theDayPattern3Form;}
	public void setDayPattern3Form(WorkCalendarDayPatternForm aDayPattern3Form) {theDayPattern3Form = aDayPattern3Form;}

	/** Рабочий день 3 */
	private WorkCalendarDayPatternForm theDayPattern3Form = new WorkCalendarDayPatternForm();
	
	/** Рабочий день 4 */
	@Comment("Рабочий день 4")
	public WorkCalendarDayPatternForm getDayPattern4Form() {return theDayPattern4Form;}
	public void setDayPattern4Form(WorkCalendarDayPatternForm aDayPattern4Form) {theDayPattern4Form = aDayPattern4Form;}

	/** Рабочий день 4 */
	private WorkCalendarDayPatternForm theDayPattern4Form = new WorkCalendarDayPatternForm();
	
	/** Рабочий день 5 */
	@Comment("Рабочий день 5")
	public WorkCalendarDayPatternForm getDayPattern5Form() {return theDayPattern5Form;}
	public void setDayPattern5Form(WorkCalendarDayPatternForm aDayPattern5Form) {theDayPattern5Form = aDayPattern5Form;}

	/** Рабочий день 5 */
	private WorkCalendarDayPatternForm theDayPattern5Form = new WorkCalendarDayPatternForm();
	
	/** Рабочий день 6 */
	@Comment("Рабочий день 6")
	public WorkCalendarDayPatternForm getDayPattern6Form() {return theDayPattern6Form;}
	public void setDayPattern6Form(WorkCalendarDayPatternForm aDayPattern6Form) {theDayPattern6Form = aDayPattern6Form;}

	/** Рабочий день 6 */
	private WorkCalendarDayPatternForm theDayPattern6Form = new WorkCalendarDayPatternForm();
	
	/** Рабочий день 7 */
	@Comment("Рабочий день 7")
	public WorkCalendarDayPatternForm getDayPattern7Form() {return theDayPattern7Form;}
	public void setDayPattern7Form(WorkCalendarDayPatternForm aDayPattern7Form) {theDayPattern7Form = aDayPattern7Form;}

	/** Рабочий день 7 */
	private WorkCalendarDayPatternForm theDayPattern7Form = new WorkCalendarDayPatternForm();
	
	/** Рабочий день 8 */
	@Comment("Рабочий день 8")
	public WorkCalendarDayPatternForm getDayPattern8Form() {return theDayPattern8Form;}
	public void setDayPattern8Form(WorkCalendarDayPatternForm aDayPattern8Form) {theDayPattern8Form = aDayPattern8Form;}

	/** Рабочий день 8 */
	private WorkCalendarDayPatternForm theDayPattern8Form = new WorkCalendarDayPatternForm();
	
	/** Рабочий день 9 */
	@Comment("Рабочий день 9")
	public WorkCalendarDayPatternForm getDayPattern9Form() {return theDayPattern9Form;}
	public void setDayPattern9Form(WorkCalendarDayPatternForm aDayPattern9Form) {theDayPattern9Form = aDayPattern9Form;}

	/** Рабочий день 9 */
	private WorkCalendarDayPatternForm theDayPattern9Form = new WorkCalendarDayPatternForm();
}
