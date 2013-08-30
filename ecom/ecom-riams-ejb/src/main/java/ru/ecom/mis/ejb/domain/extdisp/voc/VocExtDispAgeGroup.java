package ru.ecom.mis.ejb.domain.extdisp.voc;

import javax.persistence.Entity;
import javax.persistence.Table;
import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

	/**
	 * Справочник возрастных групп дополнительной диспансеризации
	 */
	@Comment("Справочник возрастных групп дополнительной диспансеризации")
@Entity
@Table(schema="SQLUser")
public class VocExtDispAgeGroup extends VocBaseEntity{
}
