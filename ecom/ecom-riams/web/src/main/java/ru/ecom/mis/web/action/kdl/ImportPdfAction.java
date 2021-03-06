package ru.ecom.mis.web.action.kdl;


import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import ru.ecom.mis.ejb.service.prescription.IPrescriptionService;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.web.struts.BaseAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ImportPdfAction extends BaseAction {

	@Override
	public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm,
			HttpServletRequest aRequest, HttpServletResponse aResponse)
			throws Exception {
		IPrescriptionService theService = Injection.find(aRequest).getService(IPrescriptionService.class) ;
		try{
		 theService.checkXmlFiles();
		} catch (Exception e){}
		return aMapping.findForward(SUCCESS);
	}

}
