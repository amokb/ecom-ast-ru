package ru.ecom.diary.web.action.protocol.template;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import ru.ecom.diary.ejb.form.protocol.TemplateProtocolForm;
import ru.ecom.diary.ejb.service.protocol.IDiaryService;
import ru.ecom.diary.ejb.service.protocol.tree.CheckNode;
import ru.ecom.web.actions.entity.EditAction;
import ru.ecom.web.util.Injection;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TemplateViewAction extends EditAction {
	
    public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {
		super.myExecute(aMapping, aForm, aRequest, aResponse) ;
	    TemplateProtocolForm form = (TemplateProtocolForm) aForm ;
	    form.setTypeViewOnly();
	    return aMapping.findForward(SUCCESS) ;
    }
    public static void getParams(HttpServletRequest aRequest) throws NamingException {
    	IDiaryService service = (IDiaryService) Injection.find(aRequest).getService("DiaryService") ;
	    CheckNode root = service.loadParametersByMedService(getLongId(aRequest, "Идентификатор роли")) ;
	    CheckNodesUtil.removeUnchecked(root);
	    aRequest.setAttribute("params", root);
    }
}
