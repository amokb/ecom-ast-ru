package ru.ecom.mis.ejb.form.medcase.hospital.interceptors;

import ru.ecom.ejb.services.entityform.IEntityForm;
import ru.ecom.ejb.services.entityform.interceptors.IFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.InterceptorContext;
import ru.ecom.ejb.util.injection.EjbInjection;
import ru.ecom.mis.ejb.domain.contract.ContractAccountOperationByService;
import ru.ecom.mis.ejb.domain.medcase.MedCase;
import ru.ecom.mis.ejb.domain.medcase.MedService;
import ru.ecom.mis.ejb.domain.medcase.SurgicalOperation;
import ru.ecom.mis.ejb.form.medcase.hospital.SurgicalOperationForm;
import ru.ecom.mis.ejb.service.contract.IContractService;

import javax.persistence.EntityManager;
import java.util.List;

public class SurgicalOperationSaveInterceptor implements IFormInterceptor {
    public void intercept(IEntityForm aForm, Object aEntity, InterceptorContext aContext) {
    	EntityManager manager = aContext.getEntityManager();
		SurgicalOperationForm form = (SurgicalOperationForm)aForm;
    	MedCase parentMedCase = manager.find(MedCase.class, form.getMedCase()) ;
    	boolean needPaidConfirmation = parentMedCase.getServiceStream().getIsPaidConfirmation() !=null && parentMedCase.getServiceStream().getIsPaidConfirmation();
		if (needPaidConfirmation) {
			long patientId = parentMedCase.getPatient().getId();
			MedService medService = manager.find(MedService.class, form.getMedService());
			String medServiceCode = medService.getCode();
			String operation = "OPERATION";
			List<Object[]> paidServiceList = EjbInjection.getInstance().getRemoteService(IContractService.class)
					.getPaidMedServicesByPatient(patientId,medServiceCode,parentMedCase.getId(), operation, form.getId(), null);
			if (paidServiceList.isEmpty()) {
				if (!aContext.getSessionContext().isCallerInRole("/Policy/Mis/Contract/MakeUnpaidServices")) {
					throw new IllegalStateException("Операция "+medServiceCode+" "+medService.getName()+" не оплачена пациентом!");
				}
			} else { //Нашли услугу, проставляем соответствие
				SurgicalOperation oper = (SurgicalOperation) aEntity;
				List<ContractAccountOperationByService> services = manager.createQuery("from ContractAccountOperationByService where serviceType=:serviceType and serviceId=:serviceId")
						.setParameter("serviceType",operation).setParameter("serviceId",oper.getId()).getResultList();
				ContractAccountOperationByService payCaos = manager.find(ContractAccountOperationByService.class, Long.valueOf(paidServiceList.get(0)[1].toString()));
				if (!services.isEmpty()) {
					ContractAccountOperationByService realCaos = services.get(0);
					if (realCaos.getId()!=payCaos.getId()){ //при изменение услуги - удаляем информацию в caos о старой услуге
						realCaos.setServiceId(null);
						realCaos.setServiceType(null);
						manager.persist(realCaos);
					}
				}
				payCaos.setMedcase(parentMedCase);
				payCaos.setServiceType(operation);
				payCaos.setServiceId(oper.getId());
				manager.persist(payCaos);
			}
		}
    }
}