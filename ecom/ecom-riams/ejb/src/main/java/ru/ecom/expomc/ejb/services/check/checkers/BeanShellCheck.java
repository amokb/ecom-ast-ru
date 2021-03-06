package ru.ecom.expomc.ejb.services.check.checkers;

import java.util.Collection;
import java.util.LinkedList;

import javax.persistence.Transient;

import ru.ecom.expomc.ejb.services.check.CheckException;
import ru.ecom.expomc.ejb.services.check.CheckResult;
import ru.ecom.expomc.ejb.services.check.ICheck;
import ru.ecom.expomc.ejb.services.check.ICheckContext;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.util.StringUtil;
import bsh.Interpreter;

/**
 * Проверка на языке BeanShell
 */
@Comment("Проверка на языке BeanShell")
public class BeanShellCheck implements ICheck {

	/**
	 * 
	 */
    public CheckResult check(ICheckContext aContext) throws CheckException {
    	Interpreter interpreter = new Interpreter() ;
    	try {
    		interpreter.set("ctx", aContext) ;
    		interpreter.set("util", util) ;
    		interpreter.eval(source);
    		CheckResult result = (CheckResult) interpreter.get("result") ;
    		if(result==null) {
    			Boolean accepted = (Boolean) interpreter.get("accepted") ;
    			if(accepted!=null) {
    				result = CheckResult.createAccepted(accepted);
    			}
    		}
    		return result ;
    	} catch (Exception e) {
    		throw new CheckException("Ошибка BeanShell\n"+source,e) ;
    	}
    }

    public static class Util {
    	public CheckResult createAccepted(boolean aAccepted) {
    		return CheckResult.createAccepted(aAccepted) ;
    	}
    	public boolean isEmpty(Object aObject) {
    		if(aObject==null) {
    			return true ;
    		} else {
    			if(aObject instanceof String) {
    				return StringUtil.isNullOrEmpty((String)aObject) ;
    			} else {
    				return false ;
    			}
    		}
    	}
    	public boolean isNotEmpty(Object aObject) {
    		return !isEmpty(aObject);
    	}
    }

    public Collection<String> getBadProperties() {
    	return new LinkedList<>();
	}
    
    /** Свойство */
    @Comment("Свойство")
    public String getSource() { return source ; }
    public void setSource(String aSource) { source = aSource ; }

    /** Свойство */
    private String source ;
    
    @Transient
    private final Util util = new Util() ;
}
