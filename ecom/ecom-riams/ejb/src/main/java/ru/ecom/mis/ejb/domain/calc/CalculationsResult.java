package ru.ecom.mis.ejb.domain.calc;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.live.DeleteListener;
import ru.ecom.mis.ejb.domain.medcase.DepartmentMedCase;
import ru.ecom.mis.ejb.domain.medcase.MedCase;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;


/** Результаты калькуляции */
@Comment("Результаты калькуляции")
@Entity
@Table(schema="SQLUser")
@EntityListeners(DeleteListener.class)
public class CalculationsResult extends BaseEntity{
    
    	
        /** СЛО */
	@Comment("СЛО")
	@OneToOne
	public DepartmentMedCase getDepartmentMedCase(){return theDepartmentMedCase;}
	public void setDepartmentMedCase(DepartmentMedCase aDepartmentMedCase){theDepartmentMedCase = aDepartmentMedCase;}
	private DepartmentMedCase theDepartmentMedCase;
    
        /** Калькулятор */
        @Comment("Калькулятор")
        @OneToOne
        public Calculator getCalculator() {return theCalculator;}
        public void setCalculator(Calculator aCalculator){theCalculator = aCalculator;}
        private Calculator theCalculator;

        /** Результат */
	@Comment("Результат")
	public String getResult(){return theResult;}
	public void setResult(String aResult){theResult = aResult;}
	private String theResult;
    
    
}
