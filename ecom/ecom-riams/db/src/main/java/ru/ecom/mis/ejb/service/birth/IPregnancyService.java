package ru.ecom.mis.ejb.service.birth;

/**
 * Сервис для работы с беременностью и тд
 * @author stkacheva
 *
 */
public interface IPregnancyService {
	Long getPregnanExchangeCard(Long aPregId) ;
	Long getConfinedExchangeCard(Long aPregId);
	Long getConfinementCertificate(Long aPregId);
	Long getPregHistoryByMedCase(Long aMedCaseId);
	Long getNewBornHistoryByMedCase(Long aMedCaseId);
	boolean isWomanByPatient(Long aPatient) ;
	boolean isWomanByMedCase(Long aMedCase) ;
	Long calcApgarEstimation(Long aMuscleTone, Long aPalpitation
  			, Long aReflexes, Long aRespiration
  			, Long aSkinColor) ;
	String calcDownesEstimation(Long aRespirationRate, Long aCyanosis
  			, Long aIntercostalRetraction, Long aDifficultExhalation
  			, Long aAuscultation) ;
	String calcInfRiskEstimation(Long aWaterlessDuration, Long aMotherTemperature
  			, Long aWaterNature, Long aApgar
  			, Long aNewBornWeight, Long aMotherInfectiousDiseases);
}
