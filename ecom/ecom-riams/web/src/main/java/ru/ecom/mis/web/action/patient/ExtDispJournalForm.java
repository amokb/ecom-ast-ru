package ru.ecom.mis.web.action.patient;

import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.forms.validator.BaseValidatorForm;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.Required;

public class ExtDispJournalForm extends BaseValidatorForm {

	/** Участок */
	@Comment("Участок")
	public Long getArea() {return theArea;}
	public void setArea(Long aArea) {theArea = aArea;}

	/** Участок */
	private Long theArea;
	
	/** Дата начала периода */
	@Comment("Дата начала периода")
	@DateString @DoDateString @Required
	public String getBeginDate() {return theBeginDate;}
	public void setBeginDate(String aBeginDate) {theBeginDate = aBeginDate;}

	/** Дата окончания периода */
	@Comment("Дата окончания периода")
	@DateString @DoDateString @Required
	public String getFinishDate() {return theFinishDate;}
	public void setFinishDate(String aFinishDate) {theFinishDate = aFinishDate;}

	
	/** Рабочая функция */
	@Comment("Рабочая функция")
	public Long getWorkFunction() {return theWorkFunction;}
	public void setWorkFunction(Long aWorkFunction) {theWorkFunction = aWorkFunction;}

	/** ЛПУ */
	@Comment("ЛПУ")
	public Long getLpu() {return theLpu;}
	public void setLpu(Long aLpu) {theLpu = aLpu;}

	/** ЛПУ */
	private Long theLpu;
	/** Рабочая функция */
	private Long theWorkFunction;
	/** Дата окончания периода */
	private String theFinishDate;
	/** Дата начала периода */
	private String theBeginDate;
	
	/** Социальный статус */
	@Comment("Социальный статус")
	public Long getSocialStatus() {return theSocialStatus;}
	public void setSocialStatus(Long aSocialStatus) {theSocialStatus = aSocialStatus;}

	/** Социальный статус */
	private Long theSocialStatus;
	
	/** Тип диспасеризации */
	@Comment("Тип диспасеризации")
	public Long getDispType() {return theDispType;}
	public void setDispType(Long aDispType) {theDispType = aDispType;}

	/** Тип диспасеризации */
	private Long theDispType;
	
	/** Возрастная категория */
	@Comment("Возрастная категория")
	public Long getAgeGroup() {return theAgeGroup;}
	public void setAgeGroup(Long aAgeGroup) {theAgeGroup = aAgeGroup;}

	/** Возрастная категория */
	private Long theAgeGroup;
	
	/** Группа здоровья */
	@Comment("Группа здоровья")
	public Long getHealthGroup() {return theHealthGroup;}
	public void setHealthGroup(Long aHealthGroup) {theHealthGroup = aHealthGroup;}

	/** Фактор риска */
	@Comment("Фактор риска")
	public Long getRisk() {return theRisk;}
	public void setRisk(Long aRisk) {theRisk = aRisk;}

	/** Фактор риска */
	private Long theRisk;
	/** Группа здоровья */
	private Long theHealthGroup;
	
	/** Услуга */
	@Comment("Услуга")
	public Long getService() {return theService;}
	public void setService(Long aService) {theService = aService;}

	/** Услуга */
	private Long theService;
	/** dateBeginYear */
	@Comment("dateBeginYear")
	public String getDateBeginYear() {return theDateBeginYear;}
	public void setDateBeginYear(String aDateBeginYear) {theDateBeginYear = aDateBeginYear;}

	/** dateBeginYear */
	private String theDateBeginYear;
	
	/** Файл для экспорта */
	@Comment("Файл для экспорта")
	public String getFilename() {
		return theFilename;
	}

	public void setFilename(String aFilename) {
		theFilename = aFilename;
	}

	/** Файл для экспорта */
	private String theFilename;

/** Группа для занятия физ. культурой (для экспорта) */
@Comment("Группа для занятия физ. культурой (для экспорта)")
public int getExpFizGroup() {
	return theExpFizGroup;
}

public void setExpFizGroup(int aExpFizGroup) {
	theExpFizGroup = aExpFizGroup;
}

/** Группа для занятия физ. культурой (для экспорта) */
private int theExpFizGroup;
	
	/** Рост (в см) (для экспорта) */
	@Comment("Рост (в см) (для экспорта)")
	public int getExpHeight() {
		return theExpHeight;
	}

	public void setExpHeight(int aExpHeight) {
		theExpHeight = aExpHeight;
	}

	/** Рост (в см) (для экспорта) */
	private int theExpHeight;
	
	/** Вес (в кг) (для экспорта) */
	@Comment("Вес (в кг) (для экспорта)")
	public int getExpWeight() {
		return theExpWeight;
	}

	public void setExpWeight(int aExpWeight) {
		theExpWeight = aExpWeight;
	}

	/** Вес (в кг) (для экспорта) */
	private int theExpWeight;
	
	/** Окружность головы (в см) (для экспорта) */
	@Comment("Окружность головы (в см) (для экспорта)")
	public int getExpHeadsize() {
		return theExpHeadsize;
	}

	public void setExpHeadsize(int aExpHeadsize) {
		theExpHeadsize = aExpHeadsize;
	}

	/** Окружность головы (в см) (для экспорта) */
	private int theExpHeadsize;
	
	/** Результат анализов (для экспорта) */
	@Comment("Результат анализов (для экспорта)")
	public String getExpResearchText() {
		return theExpResearchText;
	}

	public void setExpResearchText(String aExpResearchText) {
		theExpResearchText = aExpResearchText;
	}

	/** Результат анализов (для экспорта) */
	private String theExpResearchText;
	
	/** Рекомендации ЗОЖ (для экспорта) */
	@Comment("Рекомендации ЗОЖ (для экспорта)")
	public String getExpZOJRecommend() {
		return theExpZOJRecommend;
	}

	public void setExpZOJRecommend(String aExpZOJRecommend) {
		theExpZOJRecommend = aExpZOJRecommend;
	}

	/** Рекомендации ЗОЖ (для экспорта) */
	private String theExpZOJRecommend;
	
	/** Рекомендации по дисп. наблюдению, лечению (для экспорта) */
	@Comment("Рекомендации по дисп. наблюдению, лечению (для экспорта)")
	public String getExpRecommend() {
		return theExpRecommend;
	}
	
	public void setExpRecommend(String aExpRecommend) {
		theExpRecommend = aExpRecommend;
	}
	
	/** Рекомендации по дисп. наблюдению, лечению (для экспорта) */
	private String theExpRecommend;
	
	/** Кол-во записей в файле */
	@Comment("Кол-во записей в файле")
	public String getExpDivideNum() {
		return theExpDivideNum;
	}
	
	public void setExpDivideNum(String aExpDivideNum) {
		theExpDivideNum = aExpDivideNum;
	}
	
	/** Кол-во записей в файле */
	private String theExpDivideNum;

	/** vocworkfunction */
	@Comment("vocworkfunction")
	public Long getVocWorkFunction() {
		return theVocWorkFunction;
	}
	
	public void setVocWorkFunction(Long aVocWorkFunction) {
		theVocWorkFunction = aVocWorkFunction;
	}
	
	/** vocworkfunction */
	private Long theVocWorkFunction;
	
	/** Дата создания карты с */
	@Comment("Дата создания карты с")
	@DateString @DoDateString
	public String getCreateFrom() {return theCreateFrom;}
	public void setCreateFrom(String aCreateFrom) {theCreateFrom = aCreateFrom;}
	/** Дата создания карты с */
	private String theCreateFrom;
	
	/** Дата создания карты по */
	@Comment("Дата создания карты по")
	@DateString @DoDateString
	public String getCreateTo() {return theCreateTo;}
	public void setCreateTo(String aCreateTo) {theCreateTo = aCreateTo;}
	/** Дата создания карты по */
	private String theCreateTo;
	
	/** Список карт */
	@Comment("Список карт")
	public String getListCards() {
		return theListCards;
	}

	public void setListCards(String aListCards) {
		theListCards = aListCards;
	}

	/** Список карт */
	private String theListCards;
}


