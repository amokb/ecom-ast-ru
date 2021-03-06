package ru.ecom.expomc.ejb.services.exportformat.driver;

import org.apache.log4j.Logger;
import ru.ecom.expomc.ejb.services.exportformat.IExportFomatDriver;

import javax.persistence.EntityManager;

/**
 * @author ikouzmin 14.03.2007 11:06:07
 */
public class DriverManager {


    public static IExportFomatDriver getDriver(String driverString, EntityManager manager, boolean aNative, String query) throws Exception {

        String[] driverParams = (driverString+"::~").split(":");
        String driverName = driverParams[0];
        String driverConfig = driverParams[1];
        if (driverName.equals("")) driverName = "hibernate";
        
        if (driverName.equals("hibernate")) {
            return new DefaultExportDriver(manager,false,query,driverConfig);
        } else if (driverName.equals("sql")) {
            return new DefaultExportDriver(manager,true,query,driverConfig);
        } else if (driverName.equals("voc")) {
            return new VocExportDriver(manager,query);
        } else if (driverName.equals("voc-check")) {
            return new VocCheckExportDriver(manager,query);
        }

        throw new Exception("Driver not found");
    }
}
