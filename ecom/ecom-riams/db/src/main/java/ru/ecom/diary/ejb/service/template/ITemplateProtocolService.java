package ru.ecom.diary.ejb.service.template;

import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: STkacheva
 * Date: 22.12.2006
 * Time: 12:26:15
 * To change this template use File | Settings | File Templates.
 */

public interface ITemplateProtocolService {
    String getTextTemplate(long aId);

    String getTextByProtocol(long aProtocolId);

    String getNameVoc(String aClassif, long aId);

    Long getCountSymbolsInProtocol(long aVisit);

    void makePOSTRequest(String data, String address, String aMethod, Map<String, String> params);
}
