package ru.ecom.mis.web.action.patient;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import ru.nuzmsh.web.struts.BaseAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class VocDrugUNSearchAction extends BaseAction{
	public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {
		VocOrgSearchForm form = (VocOrgSearchForm) aForm;
	    form.validate(aMapping, aRequest) ;
	     //IPatientService service = Injection.find(aRequest).getService(IPatientService.class);
//	     IEntityFormService entityService = EntityInjection.find(aRequest).getEntityFormService();
	    aRequest.setAttribute("name",form.getName()) ;
        return aMapping.findForward(SUCCESS);
    }
}