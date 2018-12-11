package ru.ecom.web.util.voc;

import javax.naming.Context;
import javax.naming.Name;
import javax.naming.spi.ObjectFactory;
import java.util.Hashtable;

/**
 * @author esinev
 * Date: 18.08.2006
 * Time: 9:30:25
 */
public class VocHelperFactory implements ObjectFactory {


    public Object getObjectInstance(Object obj, Name name, Context nameCtx, Hashtable<?, ?> environment) throws Exception {
        return theVocHelper ;
    }

    VocHelper theVocHelper = new VocHelper();
}
