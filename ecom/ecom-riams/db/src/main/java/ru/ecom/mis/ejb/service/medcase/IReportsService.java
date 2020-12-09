package ru.ecom.mis.ejb.service.medcase;

import javax.persistence.EntityManager;

public interface IReportsService {
	String getFilterInfo(boolean aIsTicket, Long aSpecialist
			, Long aWorkFunction, Long aLpu, Long aServiceStream, Long aWorkPlaceType);
	String getFilterInfoByOrder(Long aSpecialist
			, Long aWorkFunction, Long aLpu, Long aServiceStream, Long aWorkPlaceType
			, Long aOrderLpu, Long aOrderWF) ;
	String getTextQueryBegin(boolean aIsTicket,String aGroupBy,String aStartDate, String aFinishDate
			, Long aSpecialist, Long aWorkFunction, Long aLpu, Long aServiceStream, Long aWorkPlaceType) ;
	String getTextQueryEnd(boolean aIsTicket,String aGroupBy,String aStartDate, String aFinishDate
			, Long aSpecialist, Long aWorkFunction, Long aLpu, Long aServiceStream, Long aWorkPlaceType) ;
	String getFilterId(Boolean aIsTicket, Long aSpecialist
			, Long aWorkFunction, Long aLpu, Long aServiceStream, Long aWorkPlaceType) ;
	String getFilterInfo(EntityManager aManager, boolean aIsTicket, Long aSpecialist
			, Long aWorkFunction, Long aLpu, Long aServiceStream, Long aWorkPlaceType) ;
	String getTitle(String aGroupBy) ;
	String getFilter(Boolean aIsTicket, Long aSpecialist
			, Long aWorkFunction, Long aLpu, Long aServiceStream, Long aWorkPlaceType) ;
}
