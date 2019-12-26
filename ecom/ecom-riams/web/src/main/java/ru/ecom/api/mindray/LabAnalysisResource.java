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
        System.out.println("data " + getJsonField(root,"data"));

        /*IPrescriptionService theService = Injection.find(aRequest).getService(IPrescriptionService.class) ;
        try {
            theService.readJsonMindray(getJsonField(root,"data"));
        } catch (Exception e){
            System.out.println(e);
        }*/
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