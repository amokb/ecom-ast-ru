package ru.ecom.mis.ejb.form.patient.interceptors;

import java.util.List;

import ru.ecom.address.ejb.domain.address.Address;
import ru.ecom.ejb.services.entityform.IEntityForm;
import ru.ecom.ejb.services.entityform.IParentEntityFormService;
import ru.ecom.ejb.services.entityform.interceptors.IFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.InterceptorContext;
import ru.ecom.ejb.util.injection.EjbInjection;
import ru.ecom.expomc.ejb.domain.registry.RegInsuranceCompany;
import ru.ecom.mis.ejb.domain.patient.MedPolicy;
import ru.ecom.mis.ejb.domain.patient.MedPolicyOmc;
import ru.ecom.mis.ejb.domain.patient.Patient;
import ru.ecom.mis.ejb.domain.patient.PatientListener;
import ru.ecom.mis.ejb.form.patient.MedPolicyOmcForm;
import ru.ecom.mis.ejb.form.patient.PatientForm;

public class PatientSaveInterceptor implements IFormInterceptor {

	public void intercept(IEntityForm aForm, Object aEntity, InterceptorContext aContext) {
		PatientForm form = (PatientForm) aForm ;
		Patient patient = (Patient) aEntity ;
		patient.setEditUsername(aContext.getSessionContext().getCallerPrincipal().toString()) ;
		patient.setEditDate(new java.sql.Date(new java.util.Date().getTime())) ;

		if(form.isAttachedByPolicy()) {
			if(form.getCreateNewOmcPolicy()) {
				// новый полис
				MedPolicyOmcForm polForm = form.getPolicyOmcForm() ;
				polForm.setSeries(polForm.getSeries().toUpperCase().trim());
				polForm.setPolNumber(polForm.getPolNumber().toUpperCase().trim());
				polForm.setPatient(patient.getId());
				polForm.setLastname(form.getLastname());
				polForm.setFirstname(form.getFirstname());
				polForm.setMiddlename(form.getMiddlename());
				try {
					/*
					RegInsuranceCompany company = aManager.find(RegInsuranceCompany.class, polForm.getCompany()) ;
					List<MedPolicy> medPolicies = aManager.createQuery("from MedPolicy where"
							+" series=:series and polnumber=:number and company_id=:company"
						).setParameter("series",polForm.getSeries())
						.setParameter("number",polForm.getPolNumber())
						.setParameter("company",company!=null?company.getOmcCode():"")
						.getResultList() ;
					if (medPolicies.size()>0) {
						StringBuilder error = new StringBuilder().append("В базе уже существует полис с такой серией, номером и страховой компанией :");
						for(MedPolicy policy:medPolicies) {
							error.append(" <br/><a href='entitySubclassView-mis_medPolicy.do?id= ")
									.append(policy.getId()).append("'>").append(" ПЕРСОНА:")
									.append(policy.getPatient().getFio()).append(" ПОЛИС: " )
									.append(policy.getText()).append("</a><br/>") ;
						}
						throw new IllegalStateException( error.toString()) ;
					}
					*/
					long policyId = EjbInjection.getInstance().getLocalService(IParentEntityFormService.class)
						.create(polForm);
					
					MedPolicyOmc medPolicyOmc = aContext.getEntityManager().find(MedPolicyOmc.class, policyId) ;
					//System.out.println("medPolicyOmc="+medPolicyOmc);
					patient.setAttachedOmcPolicy(medPolicyOmc);
					if(patient.getMedPolicies()!=null) {
						patient.getMedPolicies().add(medPolicyOmc);
					}
				} catch (Exception e) {
					throw new IllegalStateException(e.getMessage());
				}
			}			
			
		} else {
			// прикреплен по адресу
			try {
				patient.setAddress(aContext.getEntityManager().find(Address.class, form.getAddress())) ;
				patient.setHouseNumber(form.getHouseNumber());
				patient.setHouseBuilding(form.getHouseBuilding());
				patient.setFlatNumber(form.getFlatNumber());
				new PatientListener().onUpdate(patient) ;
			} catch (Exception e) {
				throw new IllegalStateException(e);
			}
		}
		//System.out.println("save manager = "+aManager);
		//System.out.println(" address = "+patient.getAddressInfo());
		//if (CAN_DEBUG)
		//	LOG.debug("intercept: form.getPolicyOmcForm().getSeries() = " + form.getPolicyOmcForm().getSeries());
	}
}
