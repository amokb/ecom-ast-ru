package ru.ecom.mis.ejb.service.medcase;

import ru.ecom.mis.ejb.form.patient.MedPolicyForm;

import java.text.ParseException;
import java.util.Collection;

/**
 * User: STkacheva
 * Date: 12.12.2007
 * Time: 10:23:02
 */
//TODO поудалял кучу методов. Если до 21 года не понадобятся - выпилить совсем
 public interface IHospitalMedCaseService {
	 String makeReportCostCase(String aDateFrom, String aDateTo, String aType, String aLpuCode, String aReportType);
	 String getAllServicesByMedCase(Long aMedcaseId);
     void finishMonitor(long aMonitorId) ;
     void startMonitor(long aMonitorId) ;
     void addMonitor(long aMonitorId, int aInt) ;

	 String getDischargeEpicrisis(long aMedCaseId) ;

	 void refreshReportByPeriod(String aEntranceDate,String aDischargeDate,long aIdMonitor) ;
	 void refreshCompTreatmentReportByPeriod(String aEntranceDate,String aDischargeDate,long aIdMonitor) ;
	 void createNewDiary(String aTitle, String aText, String aUsername) ;
	 void updateDataFromParameterConfig(Long aDepartment, boolean aIsLowerCase, String aIds, boolean aIsRemoveExist) ;
	 void removeDataFromParameterConfig(Long aDepartment, String aIds) ;
	 void changeServiceStreamBySmo(Long aSmo,Long aServiceStream) ;
	 void unionSloWithNextSlo(Long aSlo) ;
	 void deniedHospitalizatingSls(Long aMedCaseId, Long aDeniedHospitalizating) ;
	 void setPatientByExternalMedservice(String aDocNumber, String aOrderDate, String aPatient) ;
	 void preRecordDischarge(Long aMedCaseId, String aDischargeEpicrisis) ;
	 void updateDischargeDateByInformationBesk(String aIds, String aDate) throws ParseException;
	 void addressClear() ;
	 long addressUpdate(long id) ;
	//Получить данные диагноза по умолчанию для акушерства
	 String getTypeDiagByAccoucheur() ;
	//Удаление данных по выписке пациента
	 String deleteDataDischarge(Long aMedCaseId);
	//Получить номер пациента по ИД СЛС
     Long getPatient(long aIdSsl) ;
    //Изменить номер стат.карты
     String getChangeStatCardNumber(Long aMedCase, String aNewStatCardNumber, boolean aAlwaysCreate);
    
    //Получить список полисов по ИД СЛС
     Collection<MedPolicyForm> listPolicies(Long aMedCase) ;
     Collection<MedPolicyForm> listPoliciesToAdd(Long aMedCase) ;
     void removePolicies(long aMedCaseId, long[] aPolicies) ;
     void addPolicies(long aMedCaseId, long[] aPolicies) ;
     String getTemperatureCurve(long aMedCaseId) ;
     String isOpenningSlo(long aIdSls) ;
    
    //Поиск дублей
     String findDoubleServiceByPatient(Long aMedService, Long aPatient, Long aService, String aDate) throws ParseException; 
     String findDoubleOperationByPatient(Long aSurOperation, Long aPatient, Long aOperation, String aDate) throws ParseException; 
}
