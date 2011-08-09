package ru.ecom.mis.ejb.domain.prescription;

import java.sql.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.mis.ejb.domain.medcase.MedCase;
import ru.ecom.mis.ejb.domain.prescription.voc.VocPrescriptType;
import ru.ecom.mis.ejb.domain.worker.WorkFunction;
import ru.ecom.mis.ejb.domain.worker.Worker;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Абстрактный лист назначений
 * @author azviagin
 *
 */
@Comment("Лист назначений")
@Entity
@Table(name="PrescriptionList",schema="SQLUser")
public abstract class AbstractPrescriptionList extends BaseEntity{
	
	/** Назначения */
	@Comment("Назначения")
	@OneToMany(mappedBy="prescriptionList", cascade=CascadeType.ALL)
	public List<Prescription> getPrescriptions() {return thePrescriptions;	}
	public void setPrescriptions(List<Prescription> aPrescriptions) {thePrescriptions = aPrescriptions;	}

	/** Название  */
	@Comment("Название шаблона")
	public String getName() {return theName;}
	public void setName(String aName) {theName = aName;}

	/** Комментарии */
	@Comment("Комментарии")
	@Column(length = 32000)
	public String getComments() {return theComments;}
	public void setComments(String aComments) {theComments = aComments; }

	/** Дата создания */
	@Comment("Дата создания")
	public Date getDate() {return theDate;	}
	public void setDate(Date aDate) {theDate = aDate;}

	/** Пользователь, создавший шаблон */
	@Comment("Пользователь, создавший шаблон")
	public String getUsername() {return theUsername;}
	public void setUsername(String aUsername) {theUsername = aUsername;}

	/** Владелец */
	@Comment("Владелец")
	@OneToOne
	public Worker getOwner() {return theOwner;}
	public void setOwner(Worker aOwner) {theOwner = aOwner;}

	@Comment("Владелец (инфо)")
	@Transient
	public String getOwnerInfo(){
		return getOwner()!=null?getOwner().getDoctorInfo():"" ;
	}
	/** Случай медицинского обслуживания */
	@Comment("Случай медицинского обслуживания")
	@OneToOne
	public MedCase getMedCase() {
		return theMedCase;
	}

	public void setMedCase(MedCase aMedCase) {
		theMedCase = aMedCase;
	}

	/** Тип назначения */
	@Comment("Тип назначения")
	@OneToOne
	public VocPrescriptType getPrescriptType() {
		return thePrescriptType;
	}

	public void setPrescriptType(VocPrescriptType aPrescriptType) {
		thePrescriptType = aPrescriptType;
	}
	
	@Transient
	@Comment("Тип назначения")
	public String getPrescriptTypeInfo() {
		return thePrescriptType!=null?thePrescriptType.getName():"" ;
	}
	
	/** Рабочая функция */
	@Comment("Рабочая функция")
	@OneToOne
	public WorkFunction getWorkFunction() {return theWorkFunction;}
	public void setWorkFunction(WorkFunction aWorkFunction) {theWorkFunction = aWorkFunction;}

	@Transient
	public String getWorkFunctionInfo() {
		return theWorkFunction!=null ? theWorkFunction.getWorkFunctionInfo() : "" ;
	}
	/** Рабочая функция */
	private WorkFunction theWorkFunction;
	/** Тип назначения */
	private VocPrescriptType thePrescriptType;
	/** Случай медицинского обслуживания */
	private MedCase theMedCase;
	/** Владелец */
	private Worker theOwner;
	/** Пользователь, создавший шаблон */
	private String theUsername;
	/** Дата создания */
	private Date theDate;
	/** Комментарии */
	private String theComments;
	/** Название  */
	private String theName;
	/** Назначения */
	private List<Prescription> thePrescriptions;


}
