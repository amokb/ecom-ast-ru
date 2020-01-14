package ru.ecom.api.mindray;

import org.json.JSONException;
import org.json.JSONObject;
import ru.ecom.api.util.ApiUtil;
import ru.ecom.mis.ejb.service.prescription.IPrescriptionService;
import ru.ecom.web.util.Injection;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;

/**
 * Created by Milamesher on 11.12.2019.
 */

@Path("/mindray")
public class LabAnalysisResource {
    @POST
    @Path("/saveLabResult")
    public void saveLabResult(@Context HttpServletRequest aRequest, String jsonData) throws JSONException, NamingException {
        JSONObject root = new JSONObject(jsonData);
        String aToken=getJsonField(root,"token");
        if (aToken!=null) {ApiUtil.login(aToken,aRequest);}
        ApiUtil.init(aRequest,aToken);

        IPrescriptionService theService = Injection.find(aRequest).getService(IPrescriptionService.class) ;
        try {
            String data = getJsonField(root,"data").toString();
            if (data.startsWith("["))
                data = data.substring(1);
            if (data.endsWith("]"))
                data = data.substring(0,data.length()-1);
            theService.readJsonMindray(data);
        } catch (Exception e){
            System.out.println(e);
        }
    }

    private <T>T getJsonField(JSONObject obj, String aProperty) {
        if (obj.has(aProperty)) {
            try {
                Object o = obj.get(aProperty)!=null && !obj.get(aProperty).equals("") ? obj.get(aProperty) : null;
                return (T) o;
            } catch (JSONException e) {
                System.out.println(e);
            }
        }
        return null;
    }
}