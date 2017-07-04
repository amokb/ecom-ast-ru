package ru.ecom.mis.web.action.contract;

import ru.nuzmsh.forms.validator.BaseValidatorForm;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.validators.DateString;

public class ContractReportsForm extends BaseValidatorForm{
	
	/** Рабочая функция исполнителя */
	public Long getWorkFunction() {return theWorkFunction;}
	public void setWorkFunction(Long aWorkFunction) {theWorkFunction = aWorkFunction;}
	/** Рабочая функция исполнителя */
	private Long theWorkFunction ;
	
	/** Гарантийное письмо (номер */
	public String getGuaranteeNumber() {return theGuaranteeNumber;}
	public void setGuaranteeNumber(String aGuaranteeNumber) {theGuaranteeNumber = aGuaranteeNumber;}
	/** Гарантийное письмо (номер */
	private String theGuaranteeNumber;

	/** dateto */
	@DateString @DoDateString
	public String getDateTo() {return theDateTo;}
	public void setDateTo(String aDateTo) {theDateTo = aDateTo;}

	
	/** DateFrom */
	@DateString @DoDateString
	public String getDateFrom() {return theDateFrom;}
	public void setDateFrom(String aDateFrom) {theDateFrom = aDateFrom;}

	/** Оператор */
	public Long getOperator() {return theOperator;}
	public void setOperator(Long aOperator) {theOperator = aOperator;}

	/** Прейскурант */
	public Long getPriceList() {return thePriceList;}
	public void setPriceList(Long aPriceList) {thePriceList = aPriceList;}

	/** Фильтр по наименованию */
	public String getFilterByName() {return theFilterByName;}
	public void setFilterByName(String aFilterByName) {theFilterByName = aFilterByName;}

	/** Фильтр по коду */
	public String getFilterByCode() {return theFilterByCode;}
	public void setFilterByCode(String aFilterByCode) {theFilterByCode = aFilterByCode;}

	/** Услуга по прейскуранту */
	public Long getPriceMedService() {return thePriceMedService;}
	public void setPriceMedService(Long aPriceMedService) {thePriceMedService = aPriceMedService;}

	/** Гражданство */
	public Long getNationality() {return theNationality;}
	public void setNationality(Long aNationality) {theNationality = aNationality;}

	/** Отделение */
	public Long getDepartment() {return theDepartment;}
	public void setDepartment(Long aDepartment) {theDepartment = aDepartment;}

	/** Отделение */
	private Long theDepartment;
	/** Гражданство */
	private Long theNationality;
	/** Услуга по прейскуранту */
	private Long thePriceMedService;
	/** Фильтр по коду */
	private String theFilterByCode;
	/** Фильтр по наименованию */
	private String theFilterByName;
	/** Прейскурант */
	private Long thePriceList;
	/** Оператор */
	private Long theOperator;
	/** dateto */
	private String theDateTo;
	/** DateFrom */
	private String theDateFrom;
	
	/** Тип позиции */
	public Long getPositionType() {return thePositionType;}
	public void setPositionType(Long aPositionType) {thePositionType = aPositionType;}

	/** Тип позиции */
	private Long thePositionType;
	
	/** Тип отделения */
	public Long getDepartmentType() {return theDepartmentType;}
	public void setDepartmentType(Long aDepartmentType) {theDepartmentType = aDepartmentType;}

	/** Тип отделения */
	private Long theDepartmentType;
	/** Номер контракта */
	public String getContractNumber() {return theContractNumber;}
	public void setContractNumber(String aContractNumber) {theContractNumber = aContractNumber;}

	/** Номер контракта */
	private String theContractNumber;
	
	/** Тип юрид. персоны */
	public Long getJuridicalPersonType() {return theJuridicalPersonType;}
	public void setJuridicalPersonType(Long aJuridicalPersonType) {theJuridicalPersonType = aJuridicalPersonType;}

	/** Тип юрид. персоны */
	private Long theJuridicalPersonType;
	
	/** contractLabel */
	public Long getContractLabel() {return theContractLabel;}
	public void setContractLabel(Long aContractLabel) {theContractLabel = aContractLabel;}

	/** contractLabel */
	private Long theContractLabel;
	
	/** Номер счета */
	public String getAccountNumber() {
		return theAccountNumber;
	}

	public void setAccountNumber(String aAccountNumber) {
		theAccountNumber = aAccountNumber;
	}

	/** Номер счета */
	private String theAccountNumber;
}
