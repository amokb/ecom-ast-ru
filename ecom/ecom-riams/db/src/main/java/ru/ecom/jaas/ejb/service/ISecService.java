package ru.ecom.jaas.ejb.service;

import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import ru.ecom.jaas.ejb.form.SecRoleForm;

public interface ISecService {
    String exportPolicy(long[] aPolicies) throws ParserConfigurationException, TransformerException;

    String exportRoles(long[] aRoles) throws ParserConfigurationException, TransformerException;

    void importPolicies(long aMonitorId, List<PolicyForm> aPolicies);

    //public void importPoliciesByRole(long aMonitorId,boolean aClearRole, PolicyForm aRole, List<PolicyForm> aPolicies) ;
    List<SecRoleForm> listRoles();

    void importRoles(long aMonitorId, boolean aClearRole, List<ImportRoles> aListRoles);

    Long findRole(PolicyForm aRole);
}
