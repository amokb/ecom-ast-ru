package ru.ecom.web.login;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ReLoginAction extends LoginExitAction {
	
    public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {
    	super.myExecute(aMapping, aForm, aRequest, aResponse);
    	
    	return new ActionForward(aRequest.getParameter("next"), true) ;
    }	

}
