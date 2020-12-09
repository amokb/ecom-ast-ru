package ru.ecom.mis.ejb.service.vocabulary;

import java.util.Collection;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import ru.ecom.ejb.services.query.WebQueryResult;

/**
 * Интерфейс сервиса для работы со справочниками
 * @author stkacheva
 */
public interface IVocabularyService {
	Collection<VocEntityInfo> listVocEntities();
	int getCount(String clazz) ;
	String exportVocExtDisp(long[] aVocExpDisps) throws TransformerException, ParserConfigurationException ;
	void importVocExtDisp(long aMonitorId,boolean aClear, List<WebQueryResult> aService, List<WebQueryResult> aRisks, List<WebQueryResult> aExtDisps) ;
}
