package ru.ecom.web.dwr;

import java.util.Collection;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;

import ru.ecom.ejb.services.login.ILoginService;
import ru.ecom.ejb.services.query.IWebQueryService;
import ru.ecom.ejb.services.query.WebQueryResult;
import ru.ecom.web.login.LoginInfo;
import ru.ecom.web.util.EntityInjection;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.util.PropertyUtil;
import ru.nuzmsh.util.voc.VocAdditional;
import ru.nuzmsh.util.voc.VocServiceException;
import ru.nuzmsh.web.messages.ClaimMessage;
import ru.nuzmsh.web.messages.UserMessage;

/**
 * Для aucotomplete
 */
public class VocServiceJs {
    public String getNameById(HttpServletRequest aRequest, String aVocName, String aId, String aParentId) throws NamingException, VocServiceException {
        return EntityInjection.find(aRequest).getVocService().getNameById(aId, aVocName, new VocAdditional(aParentId)) ;
    }
    public String getCodeById(String aVocName, String aId, String aFldCode, HttpServletRequest aRequest) throws NamingException {
    	IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
    	Collection<WebQueryResult> list = service.executeNativeSql("select "+aFldCode+" from "+aVocName+" where id='"+aId+"'") ;
    		
    	return list.isEmpty()?"":""+list.iterator().next().get1() ; 
    }
    public boolean checkClaimMessage (HttpServletRequest aRequest, Long aClaimId, Long aStatus, String aComment) throws NamingException {
    	String commentSql = "";
    	if (aComment!=null&&!aComment.trim().equals("")) {
    		commentSql = " , creatorComment = '"+aComment+"' ";
    	}
    	String sql = "update Claim set completeConfirmed = '"+aStatus+"' "+commentSql+" where id = "+aClaimId;
    	IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
    	service.executeUpdateNativeSql(sql) ;
    	ClaimMessage.removeFromSession(aRequest,aClaimId) ;
    	return true;
  
    }
    
    public boolean checkMessage(HttpServletRequest aRequest, Long aIdMessage) throws NamingException {
    	ILoginService serviceLogin = Injection.find(aRequest).getService(ILoginService.class) ;
    	serviceLogin.checkMessage(aIdMessage) ;
    	UserMessage.removeFromSession(aRequest,aIdMessage) ;
    	return true ;
    }
    public boolean checkEmergencyMessages(String aIds,String aTxt,HttpServletRequest aRequest) throws NamingException {
    	StringBuilder sqlA = new StringBuilder() ;
    	sqlA.append("update CustomMessage set readDate=current_date,readtime=current_time") ;
		sqlA.append(" where id in (").append(aIds).append(")") ;
    	IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
    	service.executeUpdateNativeSql(sqlA.toString()) ;
    	return true ;
    }
    public boolean hiddenMessage(HttpServletRequest aRequest, Long aIdMessage) throws NamingException {
    	ILoginService serviceLogin = Injection.find(aRequest).getService(ILoginService.class) ;
    	serviceLogin.hideMessage(aIdMessage) ;
    	return true ;
    }
    public String getEmergencyMessages(HttpServletRequest aRequest) throws NamingException {
    	IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
    	StringBuilder sqlA = new StringBuilder() ;
    	java.util.Date date = new java.util.Date() ;
    	java.sql.Time dispatchTime = new java.sql.Time(date.getTime()) ;
    	String username = LoginInfo.find(aRequest.getSession(true)).getUsername() ;
		sqlA.append("select id,messagetitle,messageText as messageText,to_char(dispatchdate,'dd.mm.yyyy')||' '||cast(dispatchtime as varchar(5)) as inforeceipt,messageUrl from CustomMessage") ;
		sqlA.append(" where recipient='").append(username).append("'") ;
		sqlA.append(" and readDate is null");
		sqlA.append(" and isEmergency='1' and (validitydate>current_date or validitydate=current_date and validitytime>=cast('")
		.append(dispatchTime)
		.append("' as time)) ");
    	Collection<WebQueryResult> list = service.executeNativeSql(sqlA.toString(),10);
    	StringBuilder sb = new StringBuilder() ;
    	sb.append("{");
		sb.append("\"params\":[") ;
		boolean firstPassed = false ;
		String[][] props = {{"1","id"},{"2","messageTitle"},{"3","messageText"},{"4","infoReceipt"},{"5","messageUrl"}} ;
		for(WebQueryResult wqr : list) {
			StringBuilder par = new StringBuilder() ;
			par.append("{") ;
			boolean isFirtMethod = false ;
			try {
				for(String[] prop : props) {
					Object value = PropertyUtil.getPropertyValue(wqr, prop[0]) ;
					String strValue = value!=null?value.toString():"";
					if(isFirtMethod) par.append(", ") ;else isFirtMethod=true;
					par.append("\"").append(prop[1]).append("\":\"").append(str(strValue)).append("\"") ;
				}
			} catch (Exception e) {
				throw new IllegalStateException(e);
			}
			par.append("}") ;
			if(firstPassed) sb.append(", ") ;else firstPassed=true;
			sb.append(par) ;
		}
		sb.append("]") ;
		sb.append("}") ;
    	return sb.toString() ;
    }
    private String str(String aValue) {
    	if (aValue.indexOf("\"")!=-1) {
    		aValue = aValue.replaceAll("\"", "\\\"") ;
    	}
    	if (aValue.indexOf("\n")!=-1) {
    		aValue = aValue.replaceAll("\n", "\\\\n") ;
    	}
    	if (aValue.indexOf("\r")!=-1) {
    		aValue = aValue.replaceAll("\r", "\\\\r") ;
    	}
    	if (aValue.indexOf("\t")!=-1) {
    		aValue = aValue.replaceAll("\t", "\\\\t") ;
    	}
    	return aValue ;
    }
    
    public String getAllValueByVocs(String aVocs,HttpServletRequest aRequest) throws NamingException {
    	StringBuilder sb = new StringBuilder() ;
    	IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
    	sb.append("{");
		sb.append("\"vocs\":[") ;
		
		String[][] props = {{"1","id"},{"2","name"}} ;
		String[] vocs = aVocs.split(",") ;
		boolean isFistrVoc = true ;
		for(String voc : vocs) {
			//System.out.println("voc"+voc) ;
			Collection<WebQueryResult> list= service.executeNativeSql("select id,name from "+voc) ;
			if (isFistrVoc) {isFistrVoc=false ;} else {sb.append(", ");}
			sb.append("{\"name\":\"").append(voc).append("\",\"values\":[");
			boolean firstPassed = true ;
			for (WebQueryResult wqr:list) {
				
				StringBuilder par = new StringBuilder() ;
				par.append("{") ;
				boolean isFirtMethod = true ;
				try {
					for(String[] prop : props) {
						Object value = PropertyUtil.getPropertyValue(wqr, prop[0]) ;
						String strValue = value!=null?value.toString():"";
						if(!isFirtMethod) par.append(", ") ;else isFirtMethod=false;
						par.append("\"").append(prop[1]).append("\":\"").append(str(strValue)).append("\"") ;
						//System.out.println("value="+value) ;
					}
				} catch (Exception e) {
					throw new IllegalStateException(e);
				}
				par.append("}") ;
				if(!firstPassed) sb.append(", ") ;else firstPassed=false;
				sb.append(par) ;
			}
			sb.append("]}") ;
		}
		sb.append("]}") ;
    	return sb.toString();
    }
}
