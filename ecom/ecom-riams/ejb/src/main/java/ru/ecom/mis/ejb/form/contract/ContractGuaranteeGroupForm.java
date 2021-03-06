package ru.ecom.mis.ejb.form.contract;
import lombok.Setter;
import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.contract.ContractGuaranteeGroup;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.validators.Required;
@EntityForm
@EntityFormPersistance(clazz = ContractGuaranteeGroup.class)
@Comment("Группа гарантийных документов по договору")
@WebTrail(comment = "Группа гарантийных документов по договору", nameProperties= "id", list="entityParentList-contract_contractGuaranteeGroup.do", view="entityParentView-contract_contractGuaranteeGroup.do")
@EntityFormSecurityPrefix("/Policy/Mis/Contract/GroupRules/ContractGuaranteeGroup")
@Setter
public class ContractGuaranteeGroupForm extends IdEntityForm{
	/**
	 * Название
	 */
	@Comment("Название")
	@Persist @Required
	public String getName() {
		return name;
	}
	/**
	 * Название
	 */
	private String name;
}
