package ru.ecom.expomc.ejb.services.check.checkers;

import ru.ecom.expomc.ejb.services.check.CheckException;
import ru.ecom.expomc.ejb.services.check.CheckResult;
import ru.ecom.expomc.ejb.services.check.IChangeCheck;
import ru.ecom.expomc.ejb.services.check.ICheckContext;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import java.util.Collection;

/**
 * Изменение строк
 */
public abstract class AbstractChangeStringProperty implements IChangeCheck {

    public CheckResult check(ICheckContext aContext) throws CheckException {
        Object value = aContext.get(property);
        CheckResult ret = new CheckResult();
        if(value instanceof String) {
            String str = (String) value ;
            String newValue = transform(str) ;
            if(!str.equals(newValue)) {
                ret.setAccepted(true);
                ret.set(property, newValue);
            }
        }
        return ret ;
    }

    public Collection<String> getBadProperties() {
    	return BadPropertyUtil.create(getProperty()) ;
	}

    public abstract String transform(String aValue) ;

    /** Свойство */
    @Comment("Свойство")
    public String getProperty() { return property ; }
    public void setProperty(String aProperty) { property = aProperty ; }

    /** Свойство */
    private String property ;
}
