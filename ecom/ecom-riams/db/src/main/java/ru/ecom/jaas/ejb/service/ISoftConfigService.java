package ru.ecom.jaas.ejb.service;

import javax.persistence.EntityManager;

public interface ISoftConfigService {
	void addConfigDefaults()  ;
	void saveContextHelp(String aUrl,String aContext) ;
	String getContextHelp(String aUrl) ;
	String getDefaultParameterByConfig( String code ,String defaultValue, EntityManager manager);
}
