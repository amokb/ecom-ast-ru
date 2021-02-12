package ru.ecom.mis.ejb.service.addresspoint.dto;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvDate;

import java.sql.Date;

public class PatientAttachmentDto {
    @CsvBindByName(column = "Тип_ДПФС")
    @CsvBindByPosition(position = 0)
    String medPolicyType;
    @CsvBindByName(column = "ИД_полиса")
    @CsvBindByPosition(position = 1)
    String medPolicyNumber; //Серия и номер полиса ОМС старого образца (серия отделяется от номера последовательностью знаков «пробел», «№», «пробел») или номер временного свидетельства.
    @CsvBindByName(column = "ЕНП")
    @CsvBindByPosition(position = 2)
    String commonNumber;
    @CsvBindByName(column = "Фамилия")
    @CsvBindByPosition(position = 3)
    String lastname;
    @CsvBindByName(column = "Имя")
    @CsvBindByPosition(position = 4)
    String firstname;
    @CsvBindByName(column = "Отчество")
    @CsvBindByPosition(position = 5)
    String middlename;
    @CsvBindByName(column = "Дата_рождения")
    @CsvBindByPosition(position = 6)
    @CsvDate("yyyyMMdd")
    Date birthDate;
    @CsvBindByName(column = "Место_рождения")
    @CsvBindByPosition(position = 7)
    String birthPlace = null;
    @CsvBindByName(column = "Тип_УДЛ")
    @CsvBindByPosition(position = 8)
    String passportType;
    @CsvBindByName()
    String passportNumber = null;
    @CsvBindByName(column = " Дата_УДЛ")
    @CsvBindByPosition(position = 9)
    @CsvDate("yyyyMMdd")
    Date passportDate = null;
    @CsvBindByName(column = "Орган_УДЛ")
    @CsvBindByPosition(position = 10)
    String passportWhom = null;
    @CsvBindByName(column = "СНИЛС")
    @CsvBindByPosition(position = 11)
    String snils = null;
    @CsvBindByName(column = "ИД_МО")
    @CsvBindByPosition(position = 12)
    String lpuCode;
    @CsvBindByName(column = "Способ_прикрепления")
    @CsvBindByPosition(position = 13)
    String attachedMetod;
    @CsvBindByName(column = "Тип_прикрепления")
    @CsvBindByPosition(position = 14)
    String attachedType;
    @CsvBindByName(column = "Дата_прикрепления")
    @CsvBindByPosition(position = 15)
    @CsvDate("yyyyMMdd")
    Date attachedDate;
    @CsvBindByName(column = "Дата_открепления")
    @CsvBindByPosition(position = 16)
    @CsvDate("yyyyMMdd")
    Date dettachedDate;
    @CsvBindByName(column = "ОИД_МО")
    @CsvBindByPosition(position = 17)
    String lpuOid;
    @CsvBindByName(column = "Код_подразделения")
    @CsvBindByPosition(position = 18)
    String departmentCode;
    @CsvBindByName(column = "Код_участка")
    @CsvBindByPosition(position = 19)
    String areaNumber;
    @CsvBindByName(column = "СНИЛС_врача")
    @CsvBindByPosition(position = 20)
    String doctorSnils;
    @CsvBindByName(column = "Категория_врача")
    @CsvBindByPosition(position = 21)
    String doctorType = "1"; //врач

    public String getMedPolicyType() {
        return medPolicyType;
    }

    public void setMedPolicyType(String medPolicyType) {
        this.medPolicyType = medPolicyType;
    }

    public String getMedPolicyNumber() {
        return medPolicyNumber;
    }

    public void setMedPolicyNumber(String medPolicyNumber) {
        this.medPolicyNumber = medPolicyNumber;
    }

    public String getCommonNumber() {
        return commonNumber;
    }

    public void setCommonNumber(String commonNumber) {
        this.commonNumber = commonNumber;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getMiddlename() {
        return middlename;
    }

    public void setMiddlename(String middlename) {
        this.middlename = middlename;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getBirthPlace() {
        return birthPlace;
    }

    public void setBirthPlace(String birthPlace) {
        this.birthPlace = birthPlace;
    }

    public String getPassportType() {
        return passportType;
    }

    public void setPassportType(String passportType) {
        this.passportType = passportType;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
    }

    public Date getPassportDate() {
        return passportDate;
    }

    public void setPassportDate(Date passportDate) {
        this.passportDate = passportDate;
    }

    public String getPassportWhom() {
        return passportWhom;
    }

    public void setPassportWhom(String passportWhom) {
        this.passportWhom = passportWhom;
    }

    public String getSnils() {
        return snils;
    }

    public void setSnils(String snils) {
        this.snils = snils;
    }

    public String getLpuCode() {
        return lpuCode;
    }

    public void setLpuCode(String lpuCode) {
        this.lpuCode = lpuCode;
    }

    public String getAttachedMetod() {
        return attachedMetod;
    }

    public void setAttachedMetod(String attachedMetod) {
        this.attachedMetod = attachedMetod;
    }

    public String getAttachedType() {
        return attachedType;
    }

    public void setAttachedType(String attachedType) {
        this.attachedType = attachedType;
    }

    public Date getAttachedDate() {
        return attachedDate;
    }

    public void setAttachedDate(Date attachedDate) {
        this.attachedDate = attachedDate;
    }

    public Date getDettachedDate() {
        return dettachedDate;
    }

    public void setDettachedDate(Date dettachedDate) {
        this.dettachedDate = dettachedDate;
    }

    public String getLpuOid() {
        return lpuOid;
    }

    public void setLpuOid(String lpuOid) {
        this.lpuOid = lpuOid;
    }

    public String getDepartmentCode() {
        return departmentCode;
    }

    public void setDepartmentCode(String departmentCode) {
        this.departmentCode = departmentCode;
    }

    public String getAreaNumber() {
        return areaNumber;
    }

    public void setAreaNumber(String areaNumber) {
        this.areaNumber = areaNumber;
    }

    public String getDoctorSnils() {
        return doctorSnils;
    }

    public void setDoctorSnils(String doctorSnils) {
        this.doctorSnils = doctorSnils;
    }

    public String getDoctorType() {
        return doctorType;
    }

    public void setDoctorType(String doctorType) {
        this.doctorType = doctorType;
    }
}
