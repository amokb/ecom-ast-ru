package ru.ecom.mis.ejb.form.medcase.hospital.interceptors;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import ru.ecom.ejb.services.entityform.IEntityForm;
import ru.ecom.ejb.services.entityform.interceptors.IParentFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.InterceptorContext;
import ru.ecom.mis.ejb.domain.medcase.MedCase;
import ru.ecom.mis.ejb.domain.worker.WorkFunction;
import ru.ecom.mis.ejb.form.medcase.hospital.DiagnosisForm;
import ru.nuzmsh.util.format.DateFormat;

public class DiagnosisPreCreateInterceptor implements IParentFormInterceptor {
    public void intercept(IEntityForm aForm, Object aEntity, Object aParentId, InterceptorContext aContext) {
    	EntityManager manager = aContext.getEntityManager();
    	DiagnosisForm form = (DiagnosisForm)aForm ;
    	String username = aContext.getSessionContext().getCallerPrincipal().toString() ;
    	form.setUsername(username);
    	MedCase parent = manager.find(MedCase.class, aParentId) ;
    	if (parent!=null && parent.getPatient()!=null) form.setPatient(parent.getPatient().getId()) ;
        if (aContext.getSessionContext().isCallerInRole("/Policy/Mis/MedCase/Stac/Ssl/Diagnosis/EditWorker")) {
            
        } else {
        	form.addDisabledField("medicalWorker");
        	List<WorkFunction> listwf =  manager.createQuery("from WorkFunction where secUser.login = :login")
				.setParameter("login", username).getResultList() ;
	    	if (listwf.size()==0) {
	    		throw new IllegalArgumentException(
	    				"Обратитесь к администратору системы. Ваш профиль настроен неправильно. Нет соответсвия между рабочей функцией и пользователем (WorkFunction и SecUser)"
	    				);
	    	}
	    	WorkFunction wf = listwf.get(0) ;
	    	form.setMedicalWorker(wf.getId()) ;        	
        }
    	
    }

}
