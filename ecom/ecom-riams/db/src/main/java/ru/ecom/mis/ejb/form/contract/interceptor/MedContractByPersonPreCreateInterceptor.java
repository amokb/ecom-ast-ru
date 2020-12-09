package ru.ecom.mis.ejb.form.contract.interceptor;

import ru.ecom.ejb.sequence.service.ISequenceService;
import ru.ecom.ejb.services.entityform.IEntityForm;
import ru.ecom.ejb.services.entityform.interceptors.IParentFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.InterceptorContext;
import ru.ecom.ejb.util.injection.EjbInjection;
import ru.ecom.mis.ejb.domain.contract.NaturalPerson;
import ru.ecom.mis.ejb.form.contract.MedContractByPersonForm;
import ru.nuzmsh.util.format.DateFormat;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MedContractByPersonPreCreateInterceptor implements IParentFormInterceptor {
    public void intercept(IEntityForm aForm, Object aEntity, Object aParentId, InterceptorContext aContext) {

        MedContractByPersonForm form = (MedContractByPersonForm) aForm;
        if (aParentId != null) {
            NaturalPerson parent = aContext.getEntityManager().find(NaturalPerson.class, aParentId);
            String next = EjbInjection.getInstance().getLocalService(ISequenceService.class).startUseNextValue("MedContract", "contractNumber");
            form.setContractNumber(next);
            if (parent != null) {
                form.setServedPerson(parent.getId());
            }

        }

        form.setCreateUsername(aContext.getSessionContext().getCallerPrincipal().toString());
        Date date = new Date();
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        form.setCreateDate(DateFormat.formatToDate(date));
        form.setCreateTime(timeFormat.format(date));
        form.setDateFrom(DateFormat.formatToDate(date));
    }

}