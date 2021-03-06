package ru.ecom.mis.web.dwr.medcase;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.log4j.Logger;
import org.jdom.IllegalDataException;
import org.json.JSONArray;
import org.json.JSONObject;
import ru.ecom.ejb.services.query.IWebQueryService;
import ru.ecom.ejb.services.query.WebQueryResult;
import ru.ecom.ejb.services.util.ConvertSql;
import ru.ecom.mis.ejb.service.disability.IDisabilityService;
import ru.ecom.mis.ejb.service.medcase.IPolyclinicMedCaseService;
import ru.ecom.mis.ejb.service.worker.IWorkCalendarService;
import ru.ecom.mis.ejb.service.worker.IWorkerService;
import ru.ecom.mis.ejb.service.worker.TableTimeBySpecialists;
import ru.ecom.web.login.LoginInfo;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.util.format.DateFormat;
import ru.nuzmsh.web.tags.helper.RolesHelper;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import java.sql.Date;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static ru.ecom.api.util.ApiUtil.createGetRequest;

public class WorkCalendarServiceJs {

    private static final Logger LOG = Logger.getLogger(WorkCalendarServiceJs.class);

    public String getActualReserves(HttpServletRequest aRequest) throws NamingException {
        String sql = "select id, name, code from VocServiceReserveType";
        return Injection.find(aRequest).getService(IWebQueryService.class).executeNativeSqlGetJSON(new String[]{"id", "name", "code"}, sql, 50);
    }

    /**
     * Возвращаем/создаем первое свободное время по рабочей функции и дню
     */
    public String getFreeCalendarTimeForWorkFunction(Long aWorkFunctionId, String aCalendarDay, HttpServletRequest aRequest) throws NamingException, ParseException {
        String username = LoginInfo.find(aRequest.getSession(true)).getUsername();
        return Injection.find(aRequest).getService(IWorkCalendarService.class).getFreeCalendarTimeForWorkFunction(aWorkFunctionId, aCalendarDay, username);
    }

    /**
     * Изменяем тип резерва для времени по его id
     */
    public String changeScheduleElementReserve(Long wcdId, Long reserveTypeId, HttpServletRequest aRequest) throws NamingException {
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        service.executeUpdateNativeSql("update workcalendartime set reservetype_id=" + (reserveTypeId > 0 ? reserveTypeId + "" : "null") + " where id=" + wcdId);
        return "0";
    }

    /**
     * Помечаем время как удаленное по его id
     */
    public String setScheduleElementIsDelete(String wctId, HttpServletRequest aRequest) throws NamingException {

        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        int i = service.executeUpdateNativeSql("update workcalendartime set isdeleted=true where id=" + wctId);
        return i + "";
    }

    /**
     * Помечаем день как удаленный по его id
     */
    public String setScheduleDayIsDelete(String wcdId, HttpServletRequest aRequest) throws NamingException {

        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        service.executeUpdateNativeSql("update workcalendartime set isdeleted=true where workcalendarday_id=" + wcdId);
        int i = service.executeUpdateNativeSql("update workcalendarday set isdeleted=true where id=" + wcdId);
        return i + "";
    }

    /**
     * Строим таблицу с расписанием врача по раб.функции на нужную неделю
     */
    public String buildSheduleTable(String workFunctionId, String weekplus, HttpServletRequest aRequest) throws NamingException {

        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);

        String workcalendarId;
        Collection<WebQueryResult> list = service.executeNativeSql("select id from workcalendar where workfunction_id=" + workFunctionId);
        if (list.isEmpty()) {
            LOG.error("Нет календаря у рабочей функции " + workFunctionId);
            return "Нет календаря у рабочей функции" + workFunctionId;
        } else {
            WebQueryResult w = list.iterator().next();
            workcalendarId = w.get1().toString();
        }

        int wek = Integer.parseInt(weekplus);
        String date = "", mondey = "";
        list = service.executeNativeSql("select EXTRACT(day  FROM getFirstDay()+" + wek + ")||' '||getMonthByDate(getFirstDay()+" + wek + ")||" +
                "' - '|| EXTRACT(day  FROM getFirstDay()+6+" + (wek) + ")||' '||getMonthByDate(getFirstDay()+6+" + (wek) + "),getFirstDay()");
        if (!list.isEmpty()) {
            WebQueryResult w = list.iterator().next();
            date = w.get1().toString();
            mondey = w.get2().toString();
        }
        //добавлены чекбоксы для выделения и изменения резерва нескольких времён
        String sql = "select " +
                " getWeekbyDate (wcd.calendardate)," +
                " prettyDate(wcd.calendardate,wcd.id),  " +
                " wcd.id," +
                " getList('select ''<td contextmenu=\"cell\" id=\"''||id||''\" class=\"r''||coalesce(reservetype_id,0)||''\" ><input type=\"checkbox\" id=\"ch''||id||''\">''|| to_char(timefrom,''HH24:MI'')" +
                " ||(case when (prepatient_id is not null or prepatientinfo is not null or medcase_id is not null) then '' x'' else ''&nbsp;&nbsp;&nbsp;'' end)" +
                " ||''</td>'' from workcalendartime " +
                " where workcalendarday_id = '||wcd.id||' and (isDeleted is null or isDeleted = false) order by timefrom','')" +
                " from workcalendarday  wcd" +
                " where wcd.workcalendar_id  = " + workcalendarId + " and wcd.calendardate between (date'" + mondey + "'+" + wek + ") and (date'" + mondey + "'+6+" + (wek) +
                " ) and (isdeleted is null or isdeleted = false)" +
                " group by wcd.id,wcd.calendardate" +
                " order by wcd.calendardate";

        list = service.executeNativeSql(sql);

        List<String> st = new ArrayList<>();
        for (WebQueryResult t : list) {
            st.add(t.get2().toString() + t.get4().toString());
        }

        StringBuilder html = new StringBuilder("<div id=\"head-cont\">" +
                "<a href=\"#\" id=\"alink\" onClick=\"prevWeek()\" >&#8666; </a>" + date + "<a href=\"#\" id=\"alink\" onClick=\"nextWeek()\"> &#8667;</a></div>" +
                "<div id=\"body-cont\"><table id=\"table\">" +
                "<tbody>");
        for (String ss : st) {
            html.append("<tr>").append(ss).append("</tr>");
        }
        return html + "</tbody></table></div>";
    }


    /**
     * Создать даты и времена по заданному количеству визитов или по длительности визита с учётом чётности и дней недели.
     *
     * @param dateFrom       String - дата с
     * @param dateTo         String - дата по
     * @param workFunctionId Long - рабочая функция
     * @param timeFrom       String - время с
     * @param timeTo         String - время по
     * @param countVis       String  - длительность визитов/кол-во необходимых визитов
     * @param type           String  - тип (1 - по длительности визитов/2 - по кол-ву визитов)
     * @param reserveType    String - тип резерва
     * @param evenodd        String - чётные/нечётные
     *                       Дни недели
     * @param aRequest       HttpServletRequest
     * @return String результат с инфо
     */
    public String createDateTimes(String dateFrom, String dateTo
            , Long workFunctionId, String timeFrom, String timeTo
            , String countVis, String type, String reserveType, String evenodd, Boolean all, Boolean mon, Boolean tue, Boolean wed
            , Boolean thu, Boolean fri, Boolean sat, Boolean sun, HttpServletRequest aRequest) throws NamingException {

        if (reserveType.equals("")) {
            reserveType = null;
        }
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);

        String workcalendarId = "";
        Collection<WebQueryResult> wc = service.executeNativeSql("select id from workcalendar where workfunction_id=" + workFunctionId);
        if (!wc.isEmpty()) {
            WebQueryResult w = wc.iterator().next();
            workcalendarId = w.get1().toString();
        }
        String username = LoginInfo.find(aRequest.getSession(true)).getUsername();
        if (type.equals("1")) {
            service.executeNativeSql("select createSheduleByContinueVis('" + dateFrom + "','" + dateTo + "'," + workcalendarId + "" +
                    ",'" + timeFrom + "','" + timeTo + "','" + countVis + "m'," + reserveType + "," + evenodd + ",'" + username + "'," + all + "," + mon + "," +
                    tue + "," + wed + "," + thu + "," + fri + "," + sat + "," + sun + ")");
        } else {
            service.executeNativeSql("select createSheduleByCountVis('" + dateFrom + "','" + dateTo + "'," + workcalendarId + "" +
                    ",'" + timeFrom + "','" + timeTo + "','" + countVis + "'," + reserveType + "," + evenodd + ",'" + username + "'," + all + "," + mon + "," +
                    tue + "," + wed + "," + thu + "," + fri + "," + sat + "," + sun + ")");
        }
        return "yep." + dateFrom + ">>" + dateTo + ">>" + workcalendarId;
    }

    public String setAutogenerateByWorkCalendar(Long aWcId, Long aVal, HttpServletRequest aRequest) throws NamingException {
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        service.executeUpdateNativeSql("update WorkCalendar set autoGenerate='" + aVal + "' where id='" + aWcId + "'");
        return "1";
    }

    public static String getIsServiceStreamEnabled(String aPatientId, String aServiceStreamId, HttpServletRequest aRequest) throws NamingException {
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        StringBuilder sql = new StringBuilder();
        sql.append("select  case ")
                .append(" when (select code from vocservicestream where id='").append(aServiceStreamId).append("')='CHARGED' then '0'")
                .append(" else '1' end ")
                .append(" from medcase mc ")
                .append(" left join vocservicestream vss on vss.id=mc.serviceStream_id")
                .append(" where mc.patient_id='").append(aPatientId).append("' and mc.dtype='HospitalMedCase'")
                .append(" and mc.deniedHospitalizating_id is null and mc.datefinish is null");
        Collection<WebQueryResult> l = service.executeNativeSql(sql.toString());
        return l.isEmpty() ? "0" : l.iterator().next().get1().toString();
    }

    public static String getChargedServiceStream(HttpServletRequest aRequest) throws NamingException {
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        Collection<WebQueryResult> wqr = service.executeNativeSql("select id, name from vocservicestream where code='CHARGED' and (deprecated is null or deprecated='0')");
        JSONObject ret = new JSONObject();
        if (!wqr.isEmpty()) {
            WebQueryResult w = wqr.iterator().next();
            ret.put("id", w.get1().toString()).put("name", w.get2().toString());
        } else {
            ret.put("id", "0").put("name", "ОШИБКА");
        }
        return ret.toString();
    }


    public static boolean remoteUser(IWebQueryService service, HttpServletRequest aRequest) {
        String username = LoginInfo.find(aRequest.getSession(true)).getUsername();
        Collection<WebQueryResult> list = service.executeNativeSql("select case when su.isRemoteUser='1' then 1 else null end as remote,w.lpu_id from secUser su left join WorkFunction wf on wf.secUser_id=su.id left join Worker w on w.id=wf.worker_id where su.login='" + username + "'", 1);
        WebQueryResult wqr = list.iterator().next();
        if (wqr != null && wqr.get1() != null) {
            theLpuRemoteUser = ConvertSql.parseLong(wqr.get2());
            return true;
        }
        theLpuRemoteUser = null;
        return false;
    }

    private static Long theLpuRemoteUser;

    public String checkPolicyByPatient(Long aPatientId, String aDatePlan, Long aServiceStream, HttpServletRequest aRequest) throws NamingException {
        aDatePlan = aDatePlan.trim();
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        String sql = "select id,code from VocServiceStream where id='" + aServiceStream + "' and code='OBLIGATORYINSURANCE'";
        Collection<WebQueryResult> list1 = service.executeNativeSql(sql, 1);
        if (!list1.isEmpty()) {
            sql = "SELECT id,dtype "
                    + "FROM MedPolicy where patient_id='" + aPatientId + "' "
                    + "AND actualDateFrom<=to_date('" + aDatePlan + "','dd.mm.yyyy') and (actualDateTo is null or actualDateTo>=to_date('" + aDatePlan + "','dd.mm.yyyy')) "
                    + "and DTYPE like 'MedPolicyOmc%'";
            Collection<WebQueryResult> list = service.executeNativeSql(sql, 1);
            if (list.isEmpty()) return "1";
            if (list.size() > 1) return "2";
        }
        return "0";
    }

    public String getInfoByWorkFunction(Long aWorkFunction, HttpServletRequest aRequest) throws NamingException {
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        String sql = "select wp.lastname,wp.firstname,wp.middlename,to_char(wp.birthday,'ddMMyyyy'),vwf.name as vwfname,lpu.name as lpuname" +
                " from WorkFunction wf left join VocWorkFunction vwf on vwf.id=wf.workFunction_id left join Worker w on w.id=wf.worker_id left join MisLpu lpu on lpu.id=w.lpu_id left join Patient wp on wp.id=w.person_id where wf.id='" + aWorkFunction + "'";
        Collection<WebQueryResult> list = service.executeNativeSql(sql, 1);
        if (!list.isEmpty()) {
            WebQueryResult res = list.iterator().next();
            String lastname = "" + res.get1();
            String firstname = "" + res.get2();
            String user = ConvertSql.translate(firstname.substring(0, 1) + lastname);
            return user + "#" + res.get1() + " " + res.get2() + " " + res.get3() + "#" + res.get4() + "#" + res.get5() + " " + res.get6();
        }
        return null;
    }

    public String deleteEmptyCalendarDays(String aDate1, String aDate2, Long aSpecialist, HttpServletRequest aRequest) throws NamingException, ParseException {
        IWorkCalendarService service = Injection.find(aRequest).getService(IWorkCalendarService.class);
        Date beginDate = DateFormat.parseSqlDate(aDate1);
        if (aDate2 == null || aDate2.equals("")) aDate2 = aDate1;
        Date finishDate = DateFormat.parseSqlDate(aDate2);
        java.sql.Date dateCur = new java.sql.Date(new java.util.Date().getTime());
        if (dateCur.getTime() > beginDate.getTime())
            throw new IllegalArgumentException("дата начала должна быть больше текущей");
        if (dateCur.getTime() > finishDate.getTime())
            throw new IllegalArgumentException("дата окончания должна быть больше текущей");
        service.deleteEmptyCalendarDays(aSpecialist, beginDate, finishDate);
        return "Удалено";
    }

    public String moveDatePlanBySpec(String aDate1, String aDate2, Long aSpecialist, HttpServletRequest aRequest) throws NamingException, ParseException {
        IWorkCalendarService service = Injection.find(aRequest).getService(IWorkCalendarService.class);
        Date beginDate = DateFormat.parseSqlDate(aDate1);
        Date finishDate = DateFormat.parseSqlDate(aDate2);
        java.sql.Date dateCur = new java.sql.Date(new java.util.Date().getTime());
        if (dateCur.getTime() > beginDate.getTime())
            throw new IllegalArgumentException("дата начала должна быть больше текущей");
        if (dateCur.getTime() > finishDate.getTime())
            throw new IllegalArgumentException("дата окончания должна быть больше текущей");
        service.moveDate(aSpecialist, beginDate, finishDate);
        return "Изменено";
    }

    public String moveSpecialistPlanByDate(String aDateFrom, String aDateTo, Long aSpecialist1, Long aSpecialist2, HttpServletRequest aRequest) throws NamingException, ParseException {
        IWorkCalendarService service = Injection.find(aRequest).getService(IWorkCalendarService.class);
        if (aDateTo == null || aDateTo.equals("")) aDateTo = aDateFrom;
        Date dateTo = DateFormat.parseSqlDate(aDateTo);
        Date dateFrom = DateFormat.parseSqlDate(aDateFrom);
        java.sql.Date dateCur = new java.sql.Date(new java.util.Date().getTime());
        if (dateCur.getTime() > dateTo.getTime())
            throw new IllegalArgumentException("дата начала должна быть больше текущей");
        if (dateCur.getTime() > dateFrom.getTime())
            throw new IllegalArgumentException("дата окончания должна быть больше текущей");
        service.moveSpecialist(aSpecialist1, aSpecialist2, dateFrom, dateTo);
        return "Изменено";
    }

    public int deleteWorkCalendarTime(Long aTimeId, HttpServletRequest aRequest) throws NamingException {
        IWorkCalendarService service = Injection.find(aRequest).getService(IWorkCalendarService.class);
        service.deleteWorkCalendarTime(aTimeId);
        return 1;
    }

    public String findDoubleBySpecAndDate(Long aId, Long aPatient, Long aSpec, String aDate, HttpServletRequest aRequest) throws Exception {
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        StringBuilder sql = new StringBuilder();
        sql.append("select m.id,p.lastname|| ' ' || p.firstname|| ' ' || p.middlename||' '||to_char(p.birthday,'dd.mm.yyyy'),to_char(wcd.calendardate,'dd.mm.yyyy'),cast(wct.timeFrom as varchar(5)),vwf.name|| ' ' || wp.lastname|| ' ' || wp.firstname|| ' ' || wp.middlename")
                .append(" from MedCase as m ")
                .append(" left join WorkCalendarDay wcd on wcd.id=m.datePlan_id")
                .append(" left join WorkCalendarTime wct on wct.id=m.timePlan_id")
                .append(" left join workfunction as wf on wf.id=m.workfunctionPlan_id")
                .append(" left join VocWorkFunction as vwf on vwf.id=wf.workFunction_id")
                .append(" left join worker as w on wf.worker_id=w.id")
                .append(" left join patient as wp on wp.id = w.person_id")
                .append(" left join patient as p on p.id = m.patient_id")
                .append(" where m.patient_id='").append(aPatient)
                .append("' and m.dtype='Visit' and m.workFunctionPlan_id='")
                .append(aSpec).append("' and m.datePlan_id='").append(aDate).append("' ");
        if (aId != null && aId > 0L) sql.append(" and m.id!=").append(aId);

        Collection<WebQueryResult> doubles = service.executeNativeSql(sql.toString());


        if (doubles.size() > 0) {
            StringBuilder ret = new StringBuilder();
            ret.append("<br/><ol>");
            for (WebQueryResult res : doubles) {
                ret.append("<li>")
                        .append("<a href='entitySubclassView-mis_medCase.do?id=").append(res.get1())
                        .append("'>пациент: ")
                        .append(res.get2())
                        .append(" дата приема ").append(res.get3()).append(" ").append(res.get4()).append(" ")
                        .append(" специал. ").append(res.get5())
                        .append("</a>")
                        .append("</li>");
            }
            ret.append("</ol><br/>");
            return ret.toString();
        }
        return null;
    }

    public String getWorkFunctionByUsername(Long aWorkFunctionPlan, HttpServletRequest aRequest) throws NamingException, JspException {
        boolean anyWFadd = RolesHelper.checkRoles("/Policy/Mis/MedCase/Direction/CreateNewTimeAllFunction", aRequest);
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        String username = LoginInfo.find(aRequest.getSession(true)).getUsername();
        StringBuilder sql = new StringBuilder();
        Collection<WebQueryResult> list;
        //Все раб. функции пользователя с календарем
        sql.append("select wf1.id as wf1id,vwf.name ||' '||coalesce(wp.lastname||' '||wp.firstname||' '||wp.middlename,wf.groupName) as workFunction ");
        sql.append(" from WorkFunction wf")
                .append(" left join SecUser su on su.id=wf.secUser_id ")
                .append(" left join Worker w on w.id=wf.worker_id")
                .append(" left join worker w1 on w1.person_id=w.person_id")
                .append(" left join WorkFunction wf1 on wf1.worker_id=w1.id")
                .append(" left join WorkCalendar wc1 on wf1.id=wc1.workFunction_id")
                .append(" left join VocWorkFunction vwf on vwf.id=wf1.workFunction_id")
                .append(" left join patient wp on wp.id=w1.person_id ")
                .append("where su.login='").append(username).append("' and wc1.id is not null ");

        list = service.executeNativeSql(sql.toString());
        sql = new StringBuilder(); //Отображаем групповые раб. функции
        sql.append("select wfGr.id as wf1id,vwf.name ||' '||wfGr.groupName as workFunction ");
        sql.append(" from WorkFunction wf")
                .append(" left join SecUser su on su.id=wf.secUser_id ")
                .append(" left join Worker w on w.id=wf.worker_id")
                .append(" left join worker w1 on w1.person_id=w.person_id")
                .append(" left join WorkFunction wf1 on wf1.worker_id=w1.id")
                .append(" left join WorkFunction wfGr on wfGr.id=wf1.group_id")
                .append(" left join WorkCalendar wc1 on wfGr.id=wc1.workFunction_id")
                .append(" left join VocWorkFunction vwf on vwf.id=wf1.workFunction_id")
                .append(" left join patient wp on wp.id=w1.person_id ")
                .append("where su.login='").append(username).append("' and wc1.id is not null ")
                .append(" and (wfgr.IsCreateDIrectionWithoutService is null or wfgr.IsCreateDIrectionWithoutService=false)"); //платная лаб.
        Collection<WebQueryResult> list1 = service.executeNativeSql(sql.toString());
        if (!list1.isEmpty()) list.addAll(list1);
        if (anyWFadd) {
            sql = new StringBuilder(); //Отображаем календарь врача, к которому направляем
            sql.append("select wf1.id as wf1id,vwf.name ||' '||coalesce(wp.lastname||' '||wp.firstname||' '||wp.middlename,wf.groupName) as workFunction ");
            sql.append(" from WorkFunction wf")
                    .append(" left join SecUser su on su.id=wf.secUser_id ")
                    .append(" left join Worker w on w.id=wf.worker_id")
                    .append(" left join worker w1 on w1.person_id=w.person_id")
                    .append(" left join WorkFunction wf1 on wf1.worker_id=w1.id")
                    .append(" left join WorkCalendar wc1 on wf1.id=wc1.workFunction_id")
                    .append(" left join VocWorkFunction vwf on vwf.id=wf1.workFunction_id")
                    .append(" left join patient wp on wp.id=w1.person_id ")
                    .append("where wf1.id='").append(aWorkFunctionPlan).append("' and wc1.id is not null ")
                    .append(" group by wf1.id ,vwf.name ||' '||coalesce(wp.lastname||' '||wp.firstname||' '||wp.middlename,wf.groupName)");
            list1 = service.executeNativeSql(sql.toString());
            if (!list1.isEmpty()) list.addAll(list1);
        }
        StringBuilder res = new StringBuilder();
        res.append("<ul>");
        for (WebQueryResult wqr : list) {

            res.append("<li onclick=\"this.childNodes[1].checked='checked';get10DaysByWorkFunction('")
                    .append(wqr.get1()).append("','")
                    .append(wqr.get2()).append("')\">");
            res.append(" <input class='radio' type='radio' name='rdFunc' id='rdFunc' ");

            res.append(" value='")
                    .append(wqr.get1()).append("#").append(wqr.get2()).append("#").append(wqr.get3())
                    .append("'>");
            res.append(wqr.get2());
            res.append("</li>");
        }
        res.append("</ul><b>Выберите день:</b><div id='divDayByWorkFunction'><i>Выберите рабочую функцию</i></div>");
        return res.toString();
    }

    public String get10DaysByWorkFunction(Long aWorkFunction, HttpServletRequest aRequest) throws NamingException {
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        StringBuilder sql = new StringBuilder();
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        sql.append("select wcd.id as wcdid, to_char(wcd.calendarDate,'dd.mm.yyyy') as wcdcalendardate ");
        sql.append(" from WorkFunction wf left join WorkCalendar wc on wc.workfunction_id=coalesce(wf.group_id, wf.id) left join WorkCalendarDay wcd on wcd.workCalendar_id =wc.id where wf.id='").append(aWorkFunction).append("' and wcd.calendarDate between to_date('")
                .append(format.format(cal.getTime()))
                .append("','dd.mm.yyyy') and to_date('");
        cal.add(Calendar.DATE, 14);
        sql.append(format.format(cal.getTime()))
                .append("','dd.mm.yyyy') order by wcd.calendarDate");
        Collection<WebQueryResult> list = service.executeNativeSql(sql.toString());
        StringBuilder res = new StringBuilder();
        res.append("<ul>");
        for (WebQueryResult wqr : list) {

            res.append("<li onclick=\"this.childNodes[1].checked='checked';getTimeByDayAndWorkFunction('")
                    .append(aWorkFunction).append("','").append(wqr.get1()).append("')\">");
            res.append(" <input class='radio' type='radio' name='rdDay' id='rdDay' ");
            res.append(" value='").append(wqr.get1()).append("#").append(wqr.get2()).append("#").append(wqr.get3()).append("'>");
            res.append(wqr.get2());
            res.append("</li>");
        }
        res.append("</ul><b>Добавить после времени:</b><div id='divTimeByDayAndWorkFunction'><i>Выберите день</i></div>");
        return res.toString();
    }

    public String getTimeByDayAndWorkFunction(Long aWorkFunction, Long aWorkCalendarDay, HttpServletRequest aRequest) throws NamingException {
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        StringBuilder sql = new StringBuilder();

        sql.append("select to_char(wcd.calendarDate,'dd.mm.yyyy') as wcdid, cast(wct.timeFrom as varchar(5)) as wcttimeFrom,cast(min(wct1.timeFrom) as varchar(5)) as wct1timeFrom ");
        sql.append(",vwf.name as vwfname,coalesce(wp.lastname||' '||wp.firstname||' '||coalesce(wp.middlename,''),wf.groupName) as wfInfo")
                .append(" from WorkCalendarTime wct ")
                .append(" left join WorkCalendarTime wct1 on wct1.workCalendarDay_id=wct.workCalendarDay_id")
                .append(" left join WorkCalendarDay wcd on wcd.id=wct.workCalendarDay_id")
                .append(" left join WorkCalendar wc on wc.id=wcd.workCalendar_id")
                .append(" left join WorkFunction wf on wf.id=wc.workFunction_id")
                .append(" left join VocWorkFunction vwf on vwf.id=wf.workFunction_id")
                .append(" left join Worker w on w.id=wf.worker_id")
                .append(" left join Patient wp on wp.id=w.person_id")
                .append(" where wc.workFunction_id='").append(aWorkFunction)
                .append("' and wcd.id = '").append(aWorkCalendarDay)
                .append("' and (wct.isDeleted is null or wct.isDeleted='0') and wct1.timeFrom>wct.timeFrom")
                .append(" group by wct.id,wcd.calendarDate,wct.timeFrom,vwf.name,wp.lastname,wp.firstname,wp.middlename,wf.groupName order by wct.timeFrom")
        ;
        Collection<WebQueryResult> list = service.executeNativeSql(sql.toString());
        StringBuilder res = new StringBuilder();
        res.append("<ul>");
        for (WebQueryResult wqr : list) {
            res.append("<li ondblclick=\"this.childNodes[1].checked='checked';addNewTimeBySpecialist('")
                    .append(aWorkCalendarDay).append("',0,'")
                    .append(wqr.get1()).append("','")
                    .append(aWorkFunction).append("','")
                    .append(wqr.get2()).append("','")
                    .append(wqr.get3()).append("','")
                    .append(wqr.get4()).append(" ").append(wqr.get5())
                    .append("')\">");
            res.append(" <input style='display:none' class='radio' type='radio' name='rdTime' id='rdTime' ");
            res.append(" value='").append(wqr.get1()).append("#").append(wqr.get2()).append("#").append(wqr.get3()).append("'>");
            res.append(wqr.get2()).append("-").append(wqr.get3());
            res.append("<input type='button' onclick=\"addNewTimeBySpecialist('")
                    .append(aWorkCalendarDay).append("',0,'")
                    .append(wqr.get1()).append("','")
                    .append(aWorkFunction).append("','")
                    .append(wqr.get2()).append("','")
                    .append(wqr.get3()).append("','")
                    .append(wqr.get4()).append(" ").append(wqr.get5())
                    .append("')\" value='Добавить'/>");
            res.append("</li>");
        }
        res.append("</ul>");
        return res.toString();
    }


    public String getDataByTime(Long aTime, HttpServletRequest aRequest) throws NamingException {
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        String sql = "select wf.id as wfid,vwf.name ||' '||coalesce(wp.lastname||' '||wp.firstname||' '||wp.middlename,wf.groupName) as workFunction, wcd.id as wcdid, to_char(wcd.calendarDate,'dd.mm.yyyy') as wcdcaldate,wct.id as wctid,  cast(wct.timeFrom as varchar(5)) as timefrom " +
                " from WorkCalendarTime wct left join WorkCalendarDay wcd on wcd.id=wct.workCalendarDay_id left join WorkCalendar wc on wc.id=wcd.workCalendar_id left join WorkFunction wf on wf.id=wc.workFunction_id left join VocWorkFunction vwf on vwf.id=wf.workFunction_id left join Worker w on w.id=wf.worker_id left join patient wp on wp.id=w.person_id where wct.id='" + aTime + "' ";
        Collection<WebQueryResult> list = service.executeNativeSql(sql, 1);
        StringBuilder res = new StringBuilder();
        if (!list.isEmpty()) {
            WebQueryResult wqr = list.iterator().next();
            res.append(wqr.get1()).append("#");
            res.append(wqr.get2()).append("#");
            res.append(wqr.get3()).append("#");
            res.append(wqr.get4()).append("#");
            res.append(wqr.get5()).append("#");
            res.append(wqr.get6()).append("#");

        }
        return res.toString();
    }

    public String getInfoSpoAndWorkFunction(Long aSpo, Long aWorkFunctionPlan
            , Long aServiceStream, Long aVisitReason
            , HttpServletRequest aRequest) throws NamingException {
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        StringBuilder sql = new StringBuilder();
        StringBuilder res = new StringBuilder();
        sql.append("select spo.id as spoid,coalesce(to_char(spo.dateStart,'dd.mm.yyyy'),'нет даты начала ')||coalesce('-'||to_char(spo.dateFinish,'dd.mm.yyyy'),'')||' '||vwf.name ||' '||coalesce(wp.lastname||' '||wp.firstname||' '||wp.middlename,wf.groupName) as workFunction ");
        sql.append(" from MedCase spo left join WorkFunction wf on wf.id=spo.startFunction_id left join VocWorkFunction vwf on vwf.id=wf.workFunction_id left join Worker w on w.id=wf.worker_id left join patient wp on wp.id=w.person_id where spo.id='").append(aSpo).append("' and spo.dtype='PolyclinicMedCase'");
        Collection<WebQueryResult> list = service.executeNativeSql(sql.toString(), 1);
        if (!list.isEmpty()) {
            WebQueryResult wqr = list.iterator().next();
            res.append(wqr.get1()).append("#");
            res.append(wqr.get2()).append("#");

            sql = new StringBuilder();
            sql.append("select wf.id as wfid,vwf.name ||' '||coalesce(wp.lastname||' '||wp.firstname||' '||wp.middlename,wf.groupName) as workFunction ");
            sql.append(" from WorkFunction wf left join VocWorkFunction vwf on vwf.id=wf.workFunction_id left join Worker w on w.id=wf.worker_id left join patient wp on wp.id=w.person_id where wf.id='").append(aWorkFunctionPlan).append("' ");
            list = service.executeNativeSql(sql.toString(), 1);
            if (!list.isEmpty()) {
                wqr = list.iterator().next();
                res.append(wqr.get1()).append("#");
                res.append(wqr.get2()).append("#");

                sql = new StringBuilder();
                sql.append("select vss.id as vssid,vss.name from VocServiceStream vss where vss.id='").append(aServiceStream).append("' ");
                list = service.executeNativeSql(sql.toString(), 1);
                if (!list.isEmpty()) {
                    wqr = list.iterator().next();
                    res.append(wqr.get1()).append("#");
                    res.append(wqr.get2()).append("#");
                    sql = new StringBuilder();
                    sql.append("select vss.id as vssid,vss.name from VocReason vss where vss.id='").append(aVisitReason).append("' ");
                    list = service.executeNativeSql(sql.toString(), 1);
                    if (!list.isEmpty()) {
                        wqr = list.iterator().next();
                        res.append(wqr.get1()).append("#");
                        res.append(wqr.get2()).append("#");
                    } else {
                        res.append("#");
                        res.append("#");
                    }
                } else {
                    res = new StringBuilder();
                }
            } else {
                res = new StringBuilder();
            }

        }
        return res.toString();
    }

    public String getReserveByDateAndServiceByPrescriptionList(Long aWorkCalendarDay, Long aPrescriptList
            , HttpServletRequest aRequest) throws NamingException {
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        Collection<WebQueryResult> col = service.executeNativeSql("select case when mc.dtype='DepartmentMedCase' or mc.dtype='HospitalMedCase' then (select min(vss.id) from vocservicestream vss where vss.code='HOSPITAL') else mc.servicestream_id end" +
                ",mc.patient_id from PrescriptionList pl left join MedCase mc on mc.id=pl.medcase_id where pl.id=" + aPrescriptList);
        if (col.isEmpty()) return "";
        WebQueryResult wqr = col.iterator().next();
        return getReserveByDateAndService(aWorkCalendarDay, ConvertSql.parseLong(wqr.get1())
                , ConvertSql.parseLong(wqr.get2()), aRequest);
    }

    public String getReserveByDateAndService(Long aWorkCalendarDay, Long aServiceStream
            , Long aPatient, HttpServletRequest aRequest) throws NamingException {
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        StringBuilder sql = new StringBuilder();
        StringBuilder res = new StringBuilder();
        String username = LoginInfo.find(aRequest.getSession(true)).getUsername();
        sql.append("select w.lpu_id,w.id from SecUser su left join workfunction wf on wf.secuser_id=su.id left join worker w on w.id=wf.worker_id where su.login='").append(username).append("'");
        Collection<WebQueryResult> list = service.executeNativeSql(sql.toString());
        if (list.isEmpty()) return "";
        String dep = "" + list.iterator().next().get1();
        res.append("<ul>");
        list.clear();
        sql = new StringBuilder();
        sql.append("select wct.id, cast(wct.timeFrom as varchar(5)) as tnp, vsrt.background,vsrt.colorText,vsrt.name from WorkCalendarTime wct ")
                .append(" left join VocServiceReserveType vsrt on vsrt.id=wct.reserveType_id ")
                .append(" where wct.workCalendarDay_id='").append(aWorkCalendarDay).append("' ");
        sql.append(" and (wct.isDeleted is null or wct.isDeleted='0') and wct.medCase_id is null and (wct.prepatient_id is null and (wct.prepatientinfo is null or wct.prepatientinfo='')) and (vsrt.serviceStreams like '%,").append(aServiceStream).append(",%' or vsrt.serviceStreams = '' or vsrt.serviceStreams is null) and (vsrt.departments is null or vsrt.departments='' or vsrt.departments  like '%,").append(dep).append(",%') and vsrt.id is not null order by wct.timefrom");

        list = service.executeNativeSql(sql.toString(), 50);

        for (WebQueryResult wqr : list) {
            res.append("<li style='padding-left:10px;list-style: none ");
            if (wqr.get3() != null) {
                res.append("; background:").append(wqr.get3());
            }
            if (wqr.get4() != null) {
                res.append(";color:").append(wqr.get4());
            }
            res.append("' ");
            res.append("onclick=\"this.childNodes[1].checked='checked';checkRecord('")
                    .append(wqr.get1()).append("','")
                    .append(wqr.get2()).append("','").append(wqr.get6()).append("')\">");
            res.append(" <input class='radio' type='radio' name='rdTime' id='rdTime' ");

            res.append(" value='")
                    .append(wqr.get1()).append("#").append(wqr.get2())
                    .append("'>");
            res.append(wqr.get2()).append(" ").append(wqr.get5() != null ? wqr.get5() : "");
            res.append("</li>");
        }
        res.append("</ul>");
        return res.toString();
    }

    /*Получаем список свободных времен, включая резервы по дню*/
    public String getTimesByCalendarDay(Long aWorkCalendarDay, Integer aDuration, HttpServletRequest aRequest) throws NamingException {
        String sql = "select case when wct.rest='1' then 0 else wct.id end as id, cast(wct.timeFrom as varchar(5))" +
                ", vsrt.background,vsrt.colorText,vss.id as vssid,vss.name as vssname " +
                ",vsrt.name as reserveName , coalesce(wct.rest,'0') as isrest" +
                "" + //prepatient? medcase? nado li?
                " from WorkCalendarTime wct" +
                " left join Patient prepat on prepat.id=wct.prepatient_id" +
                " left join VocServiceReserveType vsrt on vsrt.id=wct.reserveType_id" +
                " left join VocServiceStream vss on vss.id=wct.serviceStream_id" +
                " where wct.workCalendarDay_id=" + aWorkCalendarDay +
                " and (wct.isDeleted is null or wct.isDeleted='0') order by wct.timeFrom";
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        return service.executeSqlGetJson(sql);

    }

    public String getPreRecord(Long aWorkCalendarDay, HttpServletRequest aRequest) throws NamingException {
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        StringBuilder sql = new StringBuilder();
        sql.append("select wct.id, cast(wct.timeFrom as varchar(5)), coalesce(");
        sql.append(" prepat.lastname ||coalesce(' ('||prepat.patientSync||')',' ('||prepat.id||')')");
        sql.append(",wct.prepatientInfo)");
        sql.append(" as fio, vsrt.background,vsrt.colorText,vss.id as vssid,vss.name as vssname from WorkCalendarTime wct ")
                .append(" left join Patient prepat on prepat.id=wct.prepatient_id ")
                .append(" left join VocServiceReserveType vsrt on vsrt.id=wct.reserveType_id ")
                .append(" left join VocServiceStream vss on vss.id=wct.serviceStream_id ")
                .append(" where wct.workCalendarDay_id='").append(aWorkCalendarDay).append("' ");
        sql.append(" and (wct.isDeleted is null or wct.isDeleted='0') and wct.medCase_id is null and (wct.rest is null or wct.rest='0') and (wct.prepatient_id is not null or (wct.prepatientinfo is not null and wct.prepatientinfo!='')) order by wct.timeFrom");
        Collection<WebQueryResult> list = service.executeNativeSql(sql.toString(), 50);
        StringBuilder res = new StringBuilder();
        res.append("<table border=1><tr><th>Предварительно записанные к специалисту</th><th>Оформленные вместо других пациентов</th><th>Резервы</th></tr><tr><td><ul>");
        for (WebQueryResult wqr : list) {

            res.append("<li style='padding-left:10px;list-style: none ");
            if (wqr.get4() != null) {
                res.append(";background:").append(wqr.get4());
            }
            if (wqr.get5() != null) {
                res.append("; color:").append(wqr.get5());
            }
            res.append("' ");
            res.append("onclick=\"this.childNodes[1].checked='checked';checkRecord('")
                    .append(wqr.get1()).append("','")
                    .append(wqr.get2()).append("','").append(wqr.get6() != null ? wqr.get6() : "").append("','").append(wqr.get7() != null ? wqr.get7() : "").append("')\">");
            res.append(" <input class='radio' type='radio' name='rdTime' id='rdTime' ");

            res.append(" value='")
                    .append(wqr.get1()).append("#").append(wqr.get2()).append("#").append(wqr.get3())
                    .append("#").append(wqr.get6() != null ? wqr.get6() : "").append("#").append(wqr.get7() != null ? wqr.get7() : "").append("#").append(wqr.get8() != null ? wqr.get8() : "")
                    .append("'>");
            res.append(wqr.get2()).append(". ").append(wqr.get3()).append(" ").append(" ").append(wqr.get7() != null ? wqr.get7() : "");
            res.append("</li>");
        }
        res.append("</ul></td><td><ul>");
        list.clear();
        sql = new StringBuilder();
        sql.append("select wct.id, cast(wct.timeFrom as varchar(5)), coalesce(");
        sql.append(" prepat.lastname ||coalesce(' ('||prepat.patientSync||')',' ('||prepat.id||')'),wct.prepatientInfo) as prepat");
        sql.append(",pc.lastname ||coalesce(' ('||pc.patientSync||')',' ('||pc.id||')') as camepat");
        sql.append(",vsrt.background,vsrt.colorText");
        sql.append(" from WorkCalendarTime wct left join VocServiceReserveType vsrt on vsrt.id=wct.reserveType_id left join MedCase m on m.id=wct.medCase_id")
                .append(" left join Patient pc on pc.id=m.patient_id left join Patient prepat on prepat.id=wct.prepatient_id where wct.workCalendarDay_id='").append(aWorkCalendarDay).append("' ")
                .append(" and (wct.isDeleted is null or wct.isDeleted='0') and wct.medCase_id is not null and (wct.prepatient_id is not null and m.patient_id!=wct.prepatient_id")
                .append(" or (wct.prepatientinfo is not null and wct.prepatientinfo!='' and wct.prepatientinfo not like pc.lastname||' %'))  order by wct.timeFrom");

        list = service.executeNativeSql(sql.toString(), 50);

        for (WebQueryResult wqr : list) {

            res.append("<li style='padding-left:10px;list-style: none ");
            if (wqr.get5() != null) {
                res.append("; background:").append(wqr.get5());
            }
            if (wqr.get6() != null) {
                res.append(";color:").append(wqr.get6());
            }
            res.append("'><i>");
            res.append(" <input class='radio' type='radio' name='rdTime' id='rdTime' ");

            res.append(" value='")
                    .append(wqr.get1()).append("#").append(wqr.get2()).append("#").append(wqr.get3())
                    .append("'>");
            res.append(wqr.get4()).append(" вместо ").append(wqr.get3());
            res.append("</i></li>");
        }
        res.append("</ul></td><td><ul>");
        list.clear();
        sql = new StringBuilder();
        sql.append("select wct.id, cast(wct.timeFrom as varchar(5)) as tnp, vsrt.background,vsrt.colorText,vsrt.name , coalesce(wct.rest,'0') as f6_isBusy from WorkCalendarTime wct ")
                .append(" left join VocServiceReserveType vsrt on vsrt.id=wct.reserveType_id ")
                .append(" where wct.workCalendarDay_id='").append(aWorkCalendarDay).append("' ");
        sql.append(" and (wct.isDeleted is null or wct.isDeleted='0') and wct.medCase_id is null and (wct.prepatient_id is null")
                .append(" and (wct.prepatientinfo is null or wct.prepatientinfo='')) and wct.reserveType_id is not null  order by wct.timeFrom");

        list = service.executeNativeSql(sql.toString(), 50);

        for (WebQueryResult wqr : list) {
            res.append("<li style='padding-left:10px;list-style: none ");
            if (wqr.get3() != null) {
                res.append("; background:").append(wqr.get3());
            }
            if (wqr.get4() != null) {
                res.append(";color:").append(wqr.get4());
            }
            res.append("' ");
            boolean isBusy = "true".equals(wqr.get6().toString());
            if (!isBusy) {
                res.append("onclick=\"this.childNodes[1].checked='checked';checkRecord('")
                        .append(wqr.get1()).append("','")
                        .append(wqr.get2()).append("','").append(wqr.get6()).append("')\">");
                res.append(" <input class='radio' type='radio' name='rdTime' id='rdTime' ");

                res.append(" value='")
                        .append(wqr.get1()).append("#").append(wqr.get2());
            }
            res.append("'>");
            res.append(wqr.get2()).append(" ").append(wqr.get5() != null ? wqr.get5() : "").append(isBusy ? " (ЗАНЯТО)" : "");
            res.append("</li>");
        }
        res.append("</ul></td></tr></table>");
        return res.toString();

    }

    public String getDirectByPatient(String aLastname, String aFirstname, String aMiddlename
            , String aBirthday, String aPolicySeries, String aPolicyNumber
            , Long aRayon, String aRayonName, HttpServletRequest aRequest) throws NamingException {
        if (aLastname == null
                || (aLastname + (aFirstname != null ? aFirstname : "") + (aMiddlename != null ? aMiddlename : "")).length() < 4
                && ((aPolicySeries != null ? aPolicySeries : "") + (aPolicyNumber != null ? aPolicyNumber : "")).length() < 4)
            return "введите данные для поиска";
        StringBuilder sql = new StringBuilder();
        StringBuilder preInfo = new StringBuilder();
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        sql.append("select wct.id as wctid,wct.prePatient_id, to_char(wcd.calendarDate,'dd.mm.yyyy'), cast(wct.timeFrom as varchar(5)), vwf.name, wp.lastname as wplastname,wp.firstname as wpfirstname,wp.middlename as wpmiddlename ");
        sql.append(" ,coalesce(");
        sql.append(" p.lastname||' '||p.firstname||' '||p.middlename||' '||to_char(p.birthday,'dd.mm.yyyy') ||coalesce(' ('||p.patientSync||')','')");
        sql.append(",wct.prepatientInfo) as fio")
                .append(", case when su.isRemoteUser='1' then 'preDirectRemoteUsername' when su1.isRemoteUser='1' then 'directRemoteUsername' else '' end as fontDirect");
        sql.append(" from WorkCalendarTime wct ")
                .append(" left join medcase vis on vis.id=wct.medcase_id ")
                .append(" left join SecUser su on su.login=wct.createPreRecord ")
                .append(" left join SecUser su1 on su1.login=vis.username ")
                .append(" left join WorkCalendarDay wcd on wcd.id=wct.workCalendarDay_id ")
                .append(" left join WorkCalendar wc on wc.id=wcd.workCalendar_id ")
                .append(" left join WorkFunction wf on wf.id=wc.workFunction_id ")
                .append(" left join VocWorkFunction vwf on vwf.id=wf.workFunction_id ")
                .append(" left join Worker w on w.id=wf.worker_id ")
                .append(" left join patient wp on wp.id=w.person_id ")
                .append("left join Patient p on p.id=wct.prePatient_id ")
                .append("left join medpolicy mp on mp.patient_id=p.id ")
                .append("where wcd.calendarDate>=CURRENT_DATE and (p.lastname like '").append(aLastname.toUpperCase()).append("%' and wct.medCase_id is null and (wct.isDeleted is null or wct.isDeleted='0') ");
        preInfo.append(aLastname).append(" ");
        if (aFirstname != null && !aFirstname.equals("")) {
            sql.append(" and p.firstname like '").append(aFirstname.toUpperCase()).append("%' ");
            preInfo.append(aFirstname).append(" ");
        } else {
            preInfo.append("% ");
        }
        if (aMiddlename != null && !aMiddlename.equals("")) {
            sql.append(" and p.middlename like '").append(aMiddlename.toUpperCase()).append("%' ");
            preInfo.append(aMiddlename).append(" ");
        } else {
            preInfo.append("% ");
        }
        if (aBirthday != null && !aBirthday.equals("")) {
            sql.append(" and p.birthday=to_date('").append(aBirthday).append("','dd.mm.yyyy') ");
            preInfo.append(aBirthday).append(" ");
        } else {
            preInfo.append("% ");
        }
        if (aPolicySeries != null && !aPolicySeries.equals("")) {
            sql.append(" and mp.series='").append(aPolicySeries.toUpperCase()).append("' ");
            preInfo.append(aPolicySeries.toUpperCase()).append(" ");
        } else {
            preInfo.append("% ");
        }
        if (aPolicyNumber != null && !aPolicyNumber.equals("")) {
            sql.append(" and mp.polNumber='").append(aPolicyNumber.toUpperCase()).append("' ");
            preInfo.append(aPolicyNumber.toUpperCase()).append(" ");
        } else {
            preInfo.append("% ");
        }
        if (aRayon != null && aRayon > 0L) {
            sql.append(" and p.rayon_id='").append(aRayon).append("' ");
            preInfo.append(aRayonName);
        } else {
            preInfo.append("%");
        }
        sql.append("or wct.prePatientInfo like '").append(preInfo).append("') ");
        sql.append(" group by wct.id,wct.prePatient_id, wcd.calendarDate, wct.timeFrom, vwf.name, wp.lastname,wp.middlename,wp.firstname ,p.id,p.patientSync,p.lastname,p.firstname,p.middlename,p.birthday,wct.prepatientInfo,su.isremoteuser,su1.isremoteuser");
        sql.append(" order by p.lastname,p.firstname,p.middlename,p.birthday");
        Collection<WebQueryResult> list = service.executeNativeSql(sql.toString(), 20);
        StringBuilder res = new StringBuilder();
        res.append("<form name='frmDirect' id='frmDirect' action='javascript:void(0) ;'><ul id='listDirects'>");
        for (WebQueryResult wqr : list) {

            res.append("<li class='liTimePre ").append(wqr.get10() != null ? wqr.get10() : "").append("'>");
            res.append("<a href=\"javascript:patientCame('")
                    .append(wqr.get1()).append("', '")
                    .append(wqr.get9()).append("', '")
                    .append(wqr.get2()).append("')\">");
            res.append(wqr.get3());
            res.append(" ").append(wqr.get4());
            res.append("</a>");
            res.append(" ").append(wqr.get5());
            res.append(" ").append(wqr.get6());
            res.append(" ").append(wqr.get7());
            res.append(" ").append(wqr.get8());
            res.append(" - <b>").append(wqr.get9());
            res.append("</b>");
            if (wqr.get2() != null) {
                res.append("<a onclick='getDefinition(\"entityShortView-mis_patient.do?id=")
                        .append(wqr.get2()).append("\", event); return false ;' ondblclick='javascript:goToPage(\"entityView-mis_patient.do\",\"")
                        .append(wqr.get2()).append("\")'><img src=\"/skin/images/main/view1.png\" alt=\"Просмотр записи\" title=\"Просмотр записи\" height=\"16\" width=\"16\"></a>");
            }

            res.append(" <a href=\"javascript:deleteTime('").append(wqr.get1()).append("',1)\">У</a>");
            res.append("</li>");
        }
        sql = new StringBuilder();
        sql.append("select wct.medCase_id as wctmedcaseid, to_char(wcd.calendarDate,'dd.mm.yyyy'), cast(wct.timeFrom as varchar(5)), vwf.name, wp.lastname as wplastname,wp.firstname as wpfirstname,wp.middlename as wpmiddlename ");
        sql.append(" ,");
        sql.append(" p.lastname||' '||p.firstname||' '||p.middlename||' '||to_char(p.birthday,'dd.mm.yyyy') ||coalesce(' ('||p.patientSync||')','')");
        sql.append(" as fio")
                .append(", case when su.isRemoteUser='1' then 'preDirectRemoteUsername' when su1.isRemoteUser='1' then 'directRemoteUsername' else '' end as fontDirect");
        sql.append(" from WorkCalendarTime wct ")
                .append(" left join WorkCalendarDay wcd on wcd.id=wct.workCalendarDay_id")
                .append(" left join WorkCalendar wc on wc.id=wcd.workCalendar_id ")
                .append(" left join WorkFunction wf on wf.id=wc.workFunction_id ")
                .append(" left join VocWorkFunction vwf on vwf.id=wf.workFunction_id ")
                .append(" left join Worker w on w.id=wf.worker_id ")
                .append(" left join patient wp on wp.id=w.person_id ")
                .append(" left join MedCase m on m.id=wct.medCase_id ")
                .append(" left join SecUser su on su.login=wct.createPreRecord ")
                .append(" left join SecUser su1 on su1.login=m.username ")
                .append(" left join Patient p on p.id=m.patient_id ")
                .append(" left join medpolicy mp on mp.patient_id=p.id ")
                .append(" where wcd.calendarDate>=CURRENT_DATE and (p.lastname like '").append(aLastname.toUpperCase()).append("%' and (wct.isDeleted is null or wct.isDeleted='0') ");
        if (aFirstname != null && !aFirstname.equals("")) {
            sql.append(" and p.firstname like '").append(aFirstname.toUpperCase()).append("%' ");
        }
        if (aMiddlename != null && !aMiddlename.equals("")) {
            sql.append(" and p.middlename like '").append(aMiddlename.toUpperCase()).append("%' ");
        }
        if (aBirthday != null && !aBirthday.equals("")) {
            sql.append(" and p.birthday=to_date('").append(aBirthday).append("','dd.mm.yyyy') ");
        }
        if (aPolicySeries != null && !aPolicySeries.equals("")) {
            sql.append(" and mp.series='").append(aPolicySeries.toUpperCase()).append("' ");
        }
        if (aPolicyNumber != null && !aPolicyNumber.equals("")) {
            sql.append(" and mp.polNumber='").append(aPolicyNumber.toUpperCase()).append("' ");
        }
        if (aRayon != null && aRayon > 0L) {
            sql.append(" and p.rayon_id='").append(aRayon).append("' ");
        }
        sql.append(") and m.dateStart is null ");
        sql.append(" group by wct.id,wct.medCase_id, wcd.calendarDate, wct.timeFrom, vwf.name, wp.lastname,wp.middlename,wp.firstname ,p.id,p.patientSync,p.lastname,p.firstname,p.middlename,p.birthday,su.isremoteuser,su1.isremoteuser");
        sql.append(" order by p.lastname,p.firstname,p.middlename,p.birthday");
        list.clear();
        list = service.executeNativeSql(sql.toString(), 20);
        for (WebQueryResult wqr : list) {

            res.append("<li class='liTimeDirect ").append(wqr.get9() != null ? wqr.get9() : "").append("'><b>");
            res.append(wqr.get2());
            res.append(" ").append(wqr.get3());
            res.append("</b> ").append(wqr.get4());
            res.append(" ").append(wqr.get5());
            res.append(" ").append(wqr.get6());
            res.append(" ").append(wqr.get7());
            res.append(" - <b>").append(wqr.get8());
            res.append("</b>");

            res.append("<a onclick='getDefinition(\"entityShortView-smo_direction.do?id=")
                    .append(wqr.get1()).append("\", event); return false ;' ondblclick='javascript:goToPage(\"entityView-smo_direction.do\",\"")
                    .append(wqr.get1()).append("\")'><img src=\"/skin/images/main/view1.png\" alt=\"Просмотр записи\" title=\"Просмотр записи\" height=\"16\" width=\"16\"></a>");
            res.append("</li>");
        }
        res.append("</ul></form>");

        return res.toString();
    }

    public String getPatients(String aLastname, String aFirstname, String aMiddlename
            , String aBirthday, String aPolicySeries, String aPolicyNumber
            , Long aRayon, HttpServletRequest aRequest) throws NamingException {
        if (aLastname == null
                || (aLastname + (aFirstname != null ? aFirstname : "") + (aMiddlename != null ? aMiddlename : "")).length() < 4
                && ((aPolicySeries != null ? aPolicySeries : "") + (aPolicyNumber != null ? aPolicyNumber : "")).length() < 4)
            return "введите данные для поиска";
        StringBuilder sql = new StringBuilder();
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        sql.append("select p.id,p.patientSync,p.lastname,p.firstname,p.middlename,to_char(p.birthday,'dd.mm.yyyy') from patient p left join medpolicy mp on mp.patient_id=p.id where p.lastname like '").append(aLastname.toUpperCase()).append("%' ");
        if (aFirstname != null && !aFirstname.equals("")) {
            sql.append(" and p.firstname like '").append(aFirstname.toUpperCase()).append("%' ");
        }
        if (aMiddlename != null && !aMiddlename.equals("")) {
            sql.append(" and p.middlename like '").append(aMiddlename.toUpperCase()).append("%' ");
        }
        if (aBirthday != null && !aBirthday.equals("")) {
            sql.append(" and p.birthday=to_date('").append(aBirthday).append("','dd.mm.yyyy') ");
        }
        if (aPolicySeries != null && !aPolicySeries.equals("")) {
            sql.append(" and mp.series='").append(aPolicySeries.toUpperCase()).append("' ");
        }
        if (aPolicyNumber != null && !aPolicyNumber.equals("")) {
            sql.append(" and mp.polNumber='").append(aPolicyNumber.toUpperCase()).append("' ");
        }
        if (aRayon != null && aRayon > 0L) {
            sql.append(" and p.rayon_id='").append(aRayon).append("' ");
        }
        sql.append(" group by p.id,p.patientSync,p.lastname,p.firstname,p.middlename,p.birthday");
        sql.append(" order by p.lastname,p.firstname,p.middlename,p.birthday");
        Collection<WebQueryResult> list = service.executeNativeSql(sql.toString(), 20);
        StringBuilder res = new StringBuilder();
        res.append("<form name='frmPatient' id='frmPatient' action='javascript:step6()'><ul id='listPatients'>");
        boolean onlyOneFoud = list.size() == 1;
        for (WebQueryResult wqr : list) {

            res.append("<li ondblclick=\"this.childNodes[1].checked='checked';document.forms['frmPatient'].action='javascript:step6Finish()';document.forms['frmPatient'].submit();\" onclick=\"this.childNodes[1].checked='checked';document.forms['frmPatient'].action='javascript:step6()';document.forms['frmPatient'].submit()\">");
            res.append(" <input class='radio' type='radio' name='rdPatient' id='rdPatient' ");
            if (onlyOneFoud) {
                res.append(" checked='true' ");
            }
            res.append(" value='")
                    .append(wqr.get1()).append("#").append(wqr.get2()).append("#").append(wqr.get3())
                    .append("#").append(wqr.get4()).append("#").append(wqr.get5()).append("'>");
            res.append(wqr.get2());
            res.append(" ").append(wqr.get3());
            res.append(" ").append(wqr.get4());
            res.append(" ").append(wqr.get5());
            res.append(" ").append(wqr.get6());
            if (wqr.get1() != null) {
                res.append("<a onclick='getDefinition(\"entityShortView-mis_patient.do?id=")
                        .append(wqr.get1()).append("\", event); return false ;' ondblclick='javascript:goToPage(\"entityView-mis_patient.do\",\"")
                        .append(wqr.get1()).append("\")'><img src=\"/skin/images/main/view1.png\" alt=\"Просмотр записи\" title=\"Просмотр записи\" height=\"16\" width=\"16\"></a>");
                res.append(" <a target='_blank' href=\"print-begunok.do?s=SmoVisitService&m=printDirectionByPatient&patientId=").append(wqr.get1()).append("\"").append(wqr.get1()).append("'\">ПЕЧАТЬ</a> ");

            }
            res.append("</li>");
        }
        res.append("<li ondblclick=\"this.childNodes[1].checked='checked';document.forms['frmPatient'].action='javascript:step6Finish()';document.forms['frmPatient'].submit();\" onclick=\"this.childNodes[1].checked='checked';document.forms['frmPatient'].submit()\"> <input type='radio' name='rdPatient' id='rdPatient' value='0'/>НЕТ В БАЗЕ</li>");
        res.append("</ul></form>");

        return res.toString();
    }

    public String getVocWorkFunction(boolean aManyIs, HttpServletRequest aRequest) throws NamingException {
        StringBuilder sql = new StringBuilder();
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        boolean remoteUser = remoteUser(service, aRequest);
        sql.append("select vwf.id as vwfid,vwf.code as vwfcode,vwf.name as vwfname");
        sql.append(" from VocWorkFunction vwf ");
        sql.append(" left join WorkFunction wf on vwf.id=wf.workFunction_id");
        sql.append(" left join MisLpu m1 on m1.id=wf.lpu_id");
        sql.append(" left join Worker w on w.id=wf.worker_id");
        sql.append(" left join MisLpu m2 on m2.id=w.lpu_id");
        sql.append(" left join WorkCalendar wc on wc.workFunction_id=wf.id");
        sql.append(" left join WorkCalendarDay wcd on wcd.workCalendar_id=wc.id");
        sql.append(" left join WorkCalendarTime wct on wct.workCalendarDay_id=wcd.id");
        sql.append(" where wcd.calendarDate>=Current_date and (wcd.isDeleted is null or wcd.isDeleted='0')");
        sql.append(" and wct.medcase_id is null and (wct.isDeleted is null or wct.isDeleted='0')");
        if (remoteUser)
            sql.append(" and (wf.DTYPE='PersonalWorkFunction' and (m2.isNoViewRemoteUser is null or m2.isNoViewRemoteUser='0') or wf.dtype='GroupWorkFunction' and (m1.isNoViewRemoteUser is null or m1.isNoViewRemoteUser='0')) and (wf.isNoViewRemoteUser is null or wf.isNoViewRemoteUser='0')");
        sql.append(" group by vwf.id,vwf.code,vwf.name");
        sql.append(" order by vwf.name");

        StringBuilder res = new StringBuilder();
        Collection<WebQueryResult> list = service.executeNativeSql(sql.toString());
        res.append("<form name='frmFunctions' id='frmFunctions' action='javascript:step3()'><ul id='listFunctions'>");
        res.append("<li class='title'>Специалисты</li>");
        for (WebQueryResult wqr : list) {
            if (aManyIs) {
                res.append("<li onclick=\"if (this.childNodes[1].checked) {this.childNodes[1].checked=false;}else{this.childNodes[1].checked=true} step3('")
                        .append(wqr.get1()).append("#").append(wqr.get3()).append("')\">");
                res.append(" <input style='display:none' readOnly='true' class='");
                res.append("radio");
            } else {
                res.append("<li onclick=\"this.childNodes[1].checked='checked';step3('")
                        .append(wqr.get1()).append("#").append(wqr.get3()).append("')\">");
                res.append(" <input class='");
                res.append("radio");
            }
            res.append("' type='");
            if (aManyIs) {
                res.append("checkbox");
            } else {
                res.append("radio");
            }
            res.append("' name='rdFunction' id='rdFunction' value='")
                    .append(wqr.get1()).append("#").append(wqr.get3()).append("'>");
            res.append(wqr.get3());
            res.append("</li>");
        }
        res.append("</ul></form>");
        return res.toString();
    }

    public String getSpecialistsByWorkFunctions(String aVocWorkFunctions, HttpServletRequest aRequest) throws NamingException {
        StringBuilder res = new StringBuilder();
        String[] ids = aVocWorkFunctions.split(";");
        res.append("<table border=1><tr>");
        for (String par : ids) {
            String[] id = par.split("#");
            res.append("<th>")
                    .append(id[1]).append("    <a href=\"javascript:uncheckedVocWorkFunction('").append(par).append("')\"> Х ").append("</a>")
                    .append("</th>");
        }
        res.append("</tr><tr>");
        for (String par : ids) {
            String[] id = par.split("#");
            res.append("<td>")
                    .append(getSpecialistsByWorkFunction(true, Long.valueOf(id[0]), aRequest))
                    .append("</td>");
        }
        res.append("</tr><tr>");
        for (String par : ids) {
            String[] id = par.split("#");
            res.append("<td valign='top' align='center'>")
                    .append("<div id='rowStep5Date_").append(id[0]).append("'>").append("</div>")
                    .append("</td>");
        }
        res.append("</tr></table>");
        return res.toString();
    }


    public String getSpecialistsByWorkFunction(boolean aIsMany, Long aVocWorkFunction, HttpServletRequest aRequest) throws NamingException {
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        boolean remoteUser = remoteUser(service, aRequest);
        StringBuilder sql = new StringBuilder();
        StringBuilder res = new StringBuilder();
        String frmName = "frmSpecialist";
        if (aIsMany) frmName = frmName + "_" + aVocWorkFunction;
        if (!aIsMany) {
            Collection<WebQueryResult> l = service.executeNativeSql("select name, id from vocworkfunction where id='" + aVocWorkFunction + "'", 1);
            WebQueryResult ll = l.iterator().next();
            res.append("<p><b><font color='MidnightBlue'>").append(ll != null ? ll.get1() : "-").append("</font></b></p>");
        }
        sql.append("select case when wf.dtype='PersonalWorkFunction' then m2.id else m1.id end as lpuid");
        sql.append(" ,case when wf.dtype='PersonalWorkFunction' then m2.name else m1.name end as lpuname");
        sql.append(" from WorkFunction wf");
        sql.append(" left join Worker w on w.id=wf.worker_id");
        sql.append(" left join Patient wp on wp.id=w.person_id");
        sql.append(" left join WorkCalendar wc on wc.workFunction_id=wf.id");
        sql.append(" left join WorkCalendarDay wcd on wcd.workCalendar_id=wc.id");
        sql.append(" left join WorkCalendarTime wct on wct.workCalendarDay_id=wcd.id");
        sql.append(" left join VocServiceReserveType vsrt on vsrt.id=wct.reserveType_id");
        sql.append(" left join MisLpu m1 on m1.id=wf.lpu_id");
        sql.append(" left join MisLpu m2 on m2.id=w.lpu_id");
        sql.append(" where wf.workFunction_id='").append(aVocWorkFunction).append("'");
        sql.append(" and wcd.calendarDate>=CURRENT_DATE");
        sql.append(" and wct.medCase_id is null and (wct.isDeleted is null or wct.isDeleted='0') ");
        if (remoteUser) {
            sql.append(" and (wf.DTYPE='PersonalWorkFunction' and (m2.isNoViewRemoteUser is null or m2.isNoViewRemoteUser='0') and (wf.isNoViewRemoteUser is null or wf.isNoViewRemoteUser='0') or (wf.dtype='GroupWorkFunction' or wf.dtype='OperatingRoom') and (m1.isNoViewRemoteUser is null or m1.isNoViewRemoteUser='0') and (wf.isNoViewRemoteUser is null or wf.isNoViewRemoteUser='0'))");
            sql.append(" and (vsrt.isViewRemoteUser is null or vsrt.isViewRemoteUser='0')");
        }
        sql.append(" group by case when wf.dtype='PersonalWorkFunction' then m2.id else m1.id end,case when wf.dtype='PersonalWorkFunction' then m2.name else m1.name end");
        Collection<WebQueryResult> listLpu = service.executeNativeSql(sql.toString(), 50);

        res.append("<form name='").append(frmName).append("' id='").append(frmName).append("' action='javascript:step4()'><ul id='listSpecialists'>");
        for (WebQueryResult wqrLpu : listLpu) {
            sql = new StringBuilder();
            sql.append("select wc.id as wcid").append(" , coalesce(wp.lastname||' '||wp.firstname||' '||coalesce(wp.middlename,''),wf.groupName) || coalesce(' (<font color=red>'||upper(wf.comment)||')</font>','') as wfInfo");
            sql.append(" ,to_char(min(wcd.calendarDate),'dd.mm.yyyy') as CDdate");
            sql.append(" ,to_char(min(wcd.calendarDate),'yyyy') as CDyear");
            sql.append(" ,to_char(min(wcd.calendarDate),'mm') as CDmonth ");
            sql.append(" from WorkFunction wf");
            sql.append(" left join Worker w on w.id=wf.worker_id");
            sql.append(" left join Patient wp on wp.id=w.person_id");
            sql.append(" left join WorkCalendar wc on wc.workFunction_id=wf.id");
            sql.append(" left join WorkCalendarDay wcd on wcd.workCalendar_id=wc.id");
            sql.append(" left join WorkCalendarTime wct on wct.workCalendarDay_id=wcd.id ");
            sql.append(" left join VocServiceReserveType vsrt on vsrt.id=wct.reserveType_id");
            sql.append(" left join MisLpu m1 on m1.id=wf.lpu_id");
            sql.append(" left join MisLpu m2 on m2.id=w.lpu_id");

            sql.append(" where wf.workFunction_id='").append(aVocWorkFunction).append("'");
            sql.append(" and wcd.calendarDate>=CURRENT_DATE");
            sql.append(" and wct.medCase_id is null and (wct.isDeleted is null or wct.isDeleted='0') ");
            if (remoteUser) {
                sql.append(" and (wf.DTYPE='PersonalWorkFunction' and m2.id='").append(wqrLpu.get1()).append("' and (m2.isNoViewRemoteUser is null or m2.isNoViewRemoteUser='0') and (wf.isNoViewRemoteUser is null or wf.isNoViewRemoteUser='0') or wf.dtype='GroupWorkFunction' and m1.id='").append(wqrLpu.get1()).append("' and (m1.isNoViewRemoteUser is null or m1.isNoViewRemoteUser='0') and (wf.isNoViewRemoteUser is null or wf.isNoViewRemoteUser='0'))");
                sql.append(" and (vsrt.isViewRemoteUser is null or vsrt.isViewRemoteUser='0')");
            }
            if (!remoteUser)
                sql.append(" and (wf.DTYPE='PersonalWorkFunction' and m2.id='").append(wqrLpu.get1()).append("' or (wf.dtype='GroupWorkFunction' or wf.dtype='OperatingRoom') and m1.id='").append(wqrLpu.get1()).append("' )");
            sql.append(" group by wc.id,wp.lastname,wp.firstname,wp.middlename,wf.groupName,wf.comment");
            sql.append(" order by wf.groupName,wp.lastname,wp.firstname,wp.middlename");
            Collection<WebQueryResult> list = service.executeNativeSql(sql.toString(), 50);
            res.append("<li><u>").append(wqrLpu.get2()).append("</u></li>");
            for (WebQueryResult wqr : list) {
                res.append("<li onclick=\"this.childNodes[1].checked='checked';step4('")
                        .append(wqr.get1()).append("','").append(wqr.get5()).append("','").append(wqr.get4());
                if (aIsMany) res.append("','").append(aVocWorkFunction);
                res.append("')\">");
                res.append(" <input class='radio' type='radio' name='rdSpecialist' id='rdSpecialist' value='")
                        .append(wqr.get1()).append("#").append(wqr.get5()).append("#").append(wqr.get4())
                        .append("#").append(wqr.get2()).append("#").append(wqr.get3()).append("'>");
                res.append(wqr.get2());
                if (!aIsMany) res.append(" (").append(wqr.get3()).append(")");
                res.append("</li>");
            }
        }
        res.append("</ul></form>");
        return res.toString();
    }

    public String getDatesBySpecialist(Long aWorkCalendar, String aMonth, String aYear, Long aVocWorkFunction, HttpServletRequest aRequest) throws NamingException {
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        boolean isRemoteUser = remoteUser(service, aRequest);
        StringBuilder sql = new StringBuilder();
        sql.append(" select  wcd.id as wcdid, to_char(wcd.calendardate,'dd.mm.yyyy') as wcdcalendardate");
        sql.append(" ,to_char(wcd.calendardate,'dd') as CDday");
        sql.append(" ,count(case when wct.medCase_id is null and wct.prepatient_id is null and (wct.prepatientinfo is null or wct.prepatientinfo='') then 1 else null end) as cntFree");
        if (!isRemoteUser) {
            sql.append(" ,count(case when wct.medCase_id is not null or wct.prepatient_id is not null or (wct.prepatientinfo is not null and wct.prepatientinfo!='') then 1 else null end) as cntBasy");
        } else {
            sql.append(" ,count(case when (vsrt.id is not null and (vsrt.isViewRemoteUser='0' or vsrt.isViewRemoteUser is null)) or (vsrt.id is null and wct.medCase_id is not null or wct.prepatient_id is not null or (wct.prepatientinfo is not null and wct.prepatientinfo!='')) then 1 else null end) as cntBusy");
        }
        sql.append(" ,count(wct.id) as cntAll");
        sql.append(" from workCalendar wc");
        sql.append(" left join workcalendarday wcd on wcd.workcalendar_id=wc.id");
        sql.append(" left join workcalendartime wct on wct.workcalendarday_id=wcd.id");
        sql.append(" left join VocServiceReserveType vsrt on vsrt.id=wct.reserveType_id");
        sql.append(" where wc.id='").append(aWorkCalendar).append("'");
        sql.append(" and (wcd.isDeleted is null or wcd.isDeleted='0') and (wct.isDeleted is null or wct.isDeleted='0') and to_char(wcd.calendardate,'mm.yyyy')='")
                .append(aMonth).append(".").append(aYear).append("'");
        if (isRemoteUser) sql.append(" and (vsrt.isViewRemoteUser is null or vsrt.isViewRemoteUser='0')");
        sql.append(" group by wcd.id,wcd.calendardate");
        sql.append(" order by wcd.calendardate");
        Collection<WebQueryResult> list = service.executeNativeSql(sql.toString(), 50);
        StringBuilder res = new StringBuilder();
        res.append("<form name='frmDate' id='frmDate' action='javascript:step5()'>");
        int month = Integer.parseInt(aMonth);
        res.append("<span class = 'spanNavigMonth'>");
        if (month == 1) {
            res.append("<a href=\"javascript:step4(")
                    .append(aWorkCalendar).append(",'").append(getMonth(12, false))
                    .append("','").append(Integer.parseInt(aYear) - 1);
            if (aVocWorkFunction != null) res.append("','").append(aVocWorkFunction);
            res.append("');\">")
                    .append("<-")
                    .append("</a> ");
        } else {
            res.append("<a href=\"javascript:step4(")
                    .append(aWorkCalendar).append(",'").append(getMonth(month - 1, false))
                    .append("','").append(aYear);
            if (aVocWorkFunction != null) res.append("','").append(aVocWorkFunction);
            res.append("');\">").append("<-")
                    .append("</a> ");
        }
        res.append(" ").append(getMonth(month, true).toUpperCase()).append(" ").append(aYear);
        if (month == 12) {
            res.append(" <a href=\"javascript:step4(")
                    .append(aWorkCalendar).append(",'").append(getMonth(1, false))
                    .append("','").append(Integer.parseInt(aYear) + 1);
            if (aVocWorkFunction != null) res.append("','").append(aVocWorkFunction);
            res.append("');\">")
                    .append("-></a>");
        } else {
            res.append("<a href=\"javascript:step4(")
                    .append(aWorkCalendar).append(",'").append(getMonth(month + 1, false))
                    .append("','").append(Integer.valueOf(aYear));
            if (aVocWorkFunction != null) res.append("','").append(aVocWorkFunction);
            res.append("');\">")
                    .append("-></a> ");
        }
        res.append("</span>");
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, Integer.parseInt(aYear));
        month--;
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DATE, 1);
        int day = 1;
        int oldday = 0;
        int week = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (week == 0) {
            week = 7;
        }
        week--;

        res.append("<table class='listDates'>");
        res.append("<tr>")
                .append("<th>Пон</th>")
                .append("<th>Вт</th>")
                .append("<th>Ср</th>")
                .append("<th>Чет</th>")
                .append("<th>Пят</th>")
                .append("<th>Суб</th>")
                .append("<th>Вос</th>")
                .append("</tr>");

        res.append("<tr>");
        res.append(getFreeDay(0, week, false, 1));
        for (WebQueryResult wqr : list) {
            oldday = Integer.parseInt("" + wqr.get3());
            res.append(getFreeDay(day, oldday, true, week));
            week = (week + oldday - day) % 7;
            if (week == 0) week = 7;
            week++;
            if (week > 7) {
                res.append("</tr><tr>");
            }
            boolean isBusy = Integer.parseInt("" + wqr.get4()) == 0;
            res.append("<td id='tdDay").append(wqr.get3()).append("'");
            res.append("onclick=\"step5(this,'").append(aWorkCalendar).append("','").append(wqr.get1())
                    .append("','").append(wqr.get2());
            if (aVocWorkFunction != null) {
                res.append("','").append(aVocWorkFunction);
            }
            res.append("')\"");
            res.append(" class='").append(isBusy ? "busyDay" : "visitDay").append("'>");
            res.append(isBusy ? "" : "<b>").append(Integer.valueOf("" + wqr.get3()));
            res.append(" <br>(").append(wqr.get5()).append("/").append(wqr.get6()).append(")");
            res.append(isBusy ? "" : "</b>").append("</td>");
            day = oldday + 1;
        }
        int max = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        res.append(getFreeDay(day, max + 1, true, week));
        if (oldday > 0) {
            week = (week + max - day) % 7;
            if (week == 0) week = 7;
        } else {
            week = (week + max) % 7;
            if (week == 0) week = 7;
        }
        week++;
        res.append(getFreeDay(week, 7, false, 1));
        res.append("</tr>");
        if (aVocWorkFunction != null) {
            res.append("<tr>");
            res.append("<td colspan='7' valign='top'><div id='rowStep6Time_").append(aVocWorkFunction).append("' >Выберите дату</div></td>");
            res.append("</tr>");
        }
        res.append("</table></form>");
        return res.toString();
    }

    private String getMonth(int aMonth, boolean aFullname) {
        String month;
        switch (aMonth) {
            case 1:
                month = aFullname ? "Январь" : "01";
                break;
            case 2:
                month = aFullname ? "Февраль" : "02";
                break;
            case 3:
                month = aFullname ? "Март" : "03";
                break;
            case 4:
                month = aFullname ? "Апрель" : "04";
                break;
            case 5:
                month = aFullname ? "Май" : "05";
                break;
            case 6:
                month = aFullname ? "Июнь" : "06";
                break;
            case 7:
                month = aFullname ? "Июль" : "07";
                break;
            case 8:
                month = aFullname ? "Август" : "08";
                break;
            case 9:
                month = aFullname ? "Сентябрь" : "09";
                break;
            case 10:
                month = aFullname ? "Октябрь" : "10";
                break;
            case 11:
                month = aFullname ? "Ноябрь" : "11";
                break;
            case 12:
                month = aFullname ? "Декабрь" : "12";
                break;
            default:
                month = aFullname ? "" : "" + aMonth;
                break;
        }
        return month;
    }

    private StringBuilder getFreeDay(int aFrom, int aTo, boolean aView, int aWeek) {

        StringBuilder res = new StringBuilder();
        for (int i = aFrom; i < aTo; i++) {

            aWeek = aWeek % 7;
            if (aWeek == 0) aWeek = 7;
            aWeek++;

            if (aWeek > 7) {
                res.append("</tr><tr>");
                aWeek = 1;
            }
            if (aView) {
                res.append("<td id='tdDay").append(getMonth(i, false)).append("'").append("class='freeDay'").append(">").append(i).append("</td>");
            } else {
                res.append("<td>&nbsp;</td>");
            }
        }
        return res;
    }

    //TODO доделать
    public String getTimesByWorkCalendarDay(Long aWorkCalendarDay, Long aVocWorkFunction, HttpServletRequest aRequest) throws NamingException, JspException {
        StringBuilder sql = new StringBuilder();
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        boolean isRemoteUser = remoteUser(service, aRequest);
        sql.append(" select wct.id,cast(wct.timeFrom as varchar(5)) as wcttimeFrom")
                .append(" ,case when wct.medCase_id is null and wct.prescription is null and wct.prepatient_id is null and (wct.prepatientinfo is null or wct.prepatientinfo='') then 0 when wct.prepatient_id is not null or (wct.prepatientinfo is not null and wct.prepatientinfo!='') then 2 else 1 end")
                .append(" ,wct.medCase_id");
        sql.append(" ,coalesce(pat.lastname||' '||pat.firstname||' '||coalesce(pat.middlename,'Х')||coalesce(' '||pat.phone,'')||coalesce(' ('||pat.patientSync||')','')");
        sql.append(", prepat.lastname ||' '||prepat.firstname||' '||coalesce(prepat.middlename,'Х')||coalesce(' тел. '||wct.phone,' тел. '||prepat.phone,'')||coalesce(' ('||prepat.patientSync||')','')");
        sql.append(",wct.prepatientInfo||' '||coalesce('тел. '||wct.phone,'')) ||' '||case when vis.datestart is not null then '' when wct.service is not null then coalesce(ms.shortname,ms.name) else (select list(coalesce(ms.shortname,ms.name)) from medcase servMc left join medservice ms on ms.id=servMc.medservice_id where servMc.parent_id=vis.id ) end as f7_fio");
        sql.append(", prepat.id as prepatid,vis.dateStart as visdateStart");
        sql.append(",coalesce(prepat.lastname,wct.prepatientInfo) as prepatLast");
        sql.append(",pat.lastname as patLast,coalesce(pat.id,prepat.id) as f12_patid")
                .append(", case when su.isRemoteUser='1' then 'preDirectRemoteUsername' when su1.isRemoteUser='1' then 'directRemoteUsername' else '' end as fontDirect");
        if (isRemoteUser) {
            sql.append(", case when vsrt.id is not null and (vsrt.isViewRemoteUser='0' or vsrt.isViewRemoteUser is null) then 'ЗАНЯТО' when vsrt.isRemoteRayon='1' then 'ЗАНЯТО' when vsrt.isViewOnlyMineDoctor='1' then 'ЗАНЯТО'  when vsrt.isViewOnlyDoctor='1' then 'ЗАНЯТО' else null end as reserve");
        } else {
            sql.append(",  vsrt.name  as reserve");
        }
        sql.append(",vss.name as v13ssname ");
        sql.append(",vsrt.name as v14srtname ");
        sql.append(",vsrt.colortext as v15srtcolor ");
        sql.append(",vsrt.background as v16srtbackground ");

        if (isRemoteUser) {
            sql.append(", case when (wct.createPreRecord is not null and sw.lpu_id is null) or sw.lpu_id!='").append(theLpuRemoteUser != null ? theLpuRemoteUser : "").append("' then 1 else null end as notViewRetomeUser1");
            sql.append(", case when w.lpu_id!='").append(theLpuRemoteUser != null ? theLpuRemoteUser : "").append("' then 1 else null end as notViewRetomeUser2");
        } else {
            sql.append(",cast('-' as varchar(1)) as emp1,cast('-' as varchar(1)) as emp2");
        }

        sql.append(" ,(case when vis.username is not null then case when wct.createdateprerecord is not null then wct.createprerecord||' '||to_char(wct.createdateprerecord,'dd.MM.yyyy')||' '||cast(wct.createtimePrerecord as varchar(5))||cast(' (предв. зап.)' as char(15)) else '' end");
        sql.append(" ||' '||vis.username||' '||to_char(vis.createdate,'dd.MM.yyyy')||' '||cast(vis.createtime as varchar(5))||cast(' (напр.)' as char(9)) ");
        sql.append(" else case when wct.createdateprerecord is not null then wct.createprerecord||' '||to_char(wct.createdateprerecord,'dd.MM.yyyy')||' '||cast(wct.createtimePrerecord as varchar(5))||cast(' (предв. зап.)' as char(15)) end end)||(case when way.id is not null then ' ('||way.name||')' else '' end) as f19_prerecord_info ");
        sql.append(" from WorkCalendarTime wct");
        sql.append(" left join VocServiceStream vss on vss.id=wct.serviceStream_id");
        sql.append(" left join MedCase vis on vis.id=wct.medCase_id");
        sql.append(" left join MedService ms on wct.service=ms.id");
        sql.append(" left join VocServiceReserveType vsrt on vsrt.id=wct.reserveType_id")
                .append(" left join SecUser su on su.login=wct.createPreRecord ")
                .append(" left join SecUser su1 on su1.login=vis.username ");
        sql.append(" left join WorkCalendarDay wcd on wcd.id=wct.workCalendarDay_id");
        if (isRemoteUser) {
            sql.append(" left join WorkCalendar wc on wc.id=wcd.workCalendar_id");
            sql.append(" left join WorkFunction wf on wf.id=wc.workFunction_id");
            sql.append(" left join Worker w on w.id=wf.worker_id");
            sql.append(" left join WorkFunction swf on swf.secUser_id=su.id");
            sql.append(" left join Worker sw on sw.id=swf.worker_id");
            sql.append(" left join Prescription p on p.id=wct.prescription");
            sql.append(" left join PrescriptionList pl on pl.id=p.prescriptionlist_id");
            sql.append(" left join Medcase dep on dep.id=pl.medcase_id");
            sql.append(" left join Patient pat2 on pat2.id=dep.patient_id");

        }
        sql.append(" left join patient pat on pat.id=vis.patient_id");
        sql.append(" left join patient prepat on prepat.id=wct.prepatient_id");
        sql.append(" left join vocwayofrecord way on way.id=wct.wayofrecord_id");
        sql.append(" where wct.workCalendarDay_id='").append(aWorkCalendarDay).append("' and (wct.isDeleted is null or wct.isDeleted='0') ");
        if (isRemoteUser) {
            sql.append(" and (vsrt.isViewRemoteUser is null or vsrt.isViewRemoteUser='0') ");
        }
        sql.append(" order by wct.timeFrom");
        StringBuilder res = new StringBuilder();
        Collection<WebQueryResult> list = service.executeNativeSql(sql.toString());
        String frmName = "frmTime";
        if (aVocWorkFunction != null) frmName = frmName + "_" + aVocWorkFunction;
        res.append("<form name='").append(frmName).append("' id='").append(frmName).append("' action='javascript:step6()'><ul class='listTimes'>");
        int cntLi = 1;

        int i = 0;

        for (WebQueryResult wqr : list) {
            i++;
            if (i == 1) res.append("<li class='liList'><ul class='ulTime'>");
            boolean info = true;
            boolean reserve = false;
            int pre = Integer.parseInt("" + wqr.get3());
            if (pre == 1) {
                if (isRemoteUser && wqr.get18() != null && !("" + wqr.get18()).equals("")) info = false;
            } else {
                if (isRemoteUser && wqr.get17() != null && !("" + wqr.get17()).equals("")) info = false;
            }

            if (wqr.get12() != null && !("" + wqr.get12()).equals("")) reserve = true;
            if (isRemoteUser && info && reserve) info = false;
            if (pre == 1) {  //Есть направление

                if (!info) {
                    res.append("<li id='liTimeBusyForRemoteUser' >")
                            .append(wqr.get2()).append(" ");
                    res.append("-ЗАНЯТО");
                } else {
                    if (wqr.get7() != null) {
                        res.append("<li id='liTimeDirect' class='").append(wqr.get11() != null ? wqr.get11() : "").append("' ><strike>")
                                .append(wqr.get2()).append(" ");
                        res.append(wqr.get5()).append("</strike>");
                    } else {
                        res.append("<li id='liTimeDirect' class='").append(wqr.get11() != null ? wqr.get11() : "").append("'>")
                                .append(wqr.get2()).append(" ");
                        res.append(wqr.get5());
                    }
                }
            } else if (pre == 2) { //Предварительная запись
                if (!info) {
                    res.append("<li id='liTimeBusyForRemoteUser' >")
                            .append(wqr.get2()).append(" ");
                    res.append("ЗАНЯТО");
                } else {
                    if (wqr.get4() != null) { // Если визит уже оформлен
                        String prelastname = "" + wqr.get8();
                        String lastname = "" + wqr.get9();
                        res.append("<li id='liTimePre' class='").append(wqr.get11() != null ? wqr.get11() : "").append("'").append(wqr.get19() != null ? " title='" + wqr.get19().toString() + "' " : "").append(">");
                        String add = "";
                        if (!prelastname.startsWith(lastname)) {
                            add = " <i> вместо " + prelastname + "</i> ";
                        }
                        if (wqr.get13() != null) add = add + " (" + wqr.get13() + ")";
                        if (wqr.get7() != null) {
                            res.append(" <strike><u>")
                                    .append(wqr.get2())
                                    .append(" ");
                            res.append(wqr.get5()).append(add).append("</strike></u>");
                        } else {
                            res.append(" <u>");
                            res.append(" <a target='_blank' href=\"print-begunok.do?s=SmoVisitService&m=printDirectionByTime&wct=").append(wqr.get1()).append("\"").append(wqr.get1()).append("'\">ПЕЧАТЬ</a> ");
                            res.append(wqr.get2())
                                    .append(" ");
                            res.append(wqr.get5()).append(add).append("</u>");
                        }
                    } else {
                        res.append(" <a target='_blank' href=\"print-begunok.do?s=SmoVisitService&m=printDirectionByTime&wct=").append(wqr.get1()).append("\"").append(wqr.get1()).append("'\">ПЕЧАТЬ</a> ");
                        res.append("<li id='liTimePre' class='").append(wqr.get11() != null ? wqr.get11() : "").append("'").append(wqr.get19() != null ? " title='" + wqr.get19().toString() + "' " : "").append(">").append(" <a href=\"javascript:patientCame('")
                                .append(wqr.get1()).append("','").append(wqr.get5())
                                .append("','").append(wqr.get6()).append("')\">")
                                .append(wqr.get2()).append("</a>")
                                .append(" ");
                        res.append(wqr.get5()).append(" ").append(wqr.get13() != null ? "(" + wqr.get13() + "" : "").append(" <a href=\"javascript:deleteTime('").append(wqr.get1()).append("')\">У</a>");
                    }
                }
            } else {
                if (!info) {
                    res.append("<li id='liTimeBusyForRemoteUser' >")
                            .append(wqr.get2()).append(" ");
                } else {
                    if (reserve) {
                        res.append("<li id='liTime' style='color:").append(wqr.get15()).append("; background:").append(wqr.get16()).append("' ondblclick=\"this.childNodes[1].checked='checked';step6Finish('").append(wqr.get1()).append("')\" onclick=\"this.childNodes[1].checked='checked';step6();\">");

                        res.append(" <input class='radio' type='radio' name='rdTime' id='rdTime' ");
                        res.append(" value='").append(aWorkCalendarDay).append("#").append(wqr.get1()).append("#").append(wqr.get2()).append("'>");
                        if (RolesHelper.checkRoles("/Policy/Mis/Worker/WorkCalendar/DeleteTime", aRequest)) {
                            res.append("<a href=\"javascript:deleteWCTime('").append(wqr.get1()).append("')\">DEL</a>");
                        }
                        res.append(" ").append(wqr.get2()).append(" ");
                        res.append(wqr.get12());
                    } else {
                        res.append("<li id='liTime' ondblclick=\"this.childNodes[1].checked='checked';step6Finish('").append(wqr.get1()).append("')\" onclick=\"this.childNodes[1].checked='checked';step6();\">");

                        res.append(" <input class='radio' type='radio' name='rdTime' id='rdTime' ");
                        res.append(" value='").append(aWorkCalendarDay).append("#").append(wqr.get1()).append("#").append(wqr.get2()).append("'>");
                        if (RolesHelper.checkRoles("/Policy/Mis/Worker/WorkCalendar/DeleteTime", aRequest)) {
                            res.append("<a href=\"javascript:deleteWCTime('").append(wqr.get1()).append("')\">DEL</a>");
                        }
                        res.append(wqr.get2());
                    }
                }
            }
            if (wqr.get10() != null && info) {
                res.append("<a onclick='getDefinition(\"entityShortView-mis_patient.do?id=")
                        .append(wqr.get10()).append("\", event); return false ;' ondblclick='javascript:goToPage(\"entityView-mis_patient.do\",\"")
                        .append(wqr.get10()).append("\")'><img src=\"/skin/images/main/view1.png\" alt=\"Просмотр записи\" title=\"Просмотр записи\" height=\"16\" width=\"16\"></a>");
            }
            String prelastname = "";
            String lastname = "";
            if (wqr.get4() != null) {
                prelastname = wqr.get8() != null ? "" + wqr.get8() : "";
                lastname = wqr.get9() != null ? "" + wqr.get9() : "";
            }
            if (!isRemoteUser) {
                String msg = (wqr.get19() != null ? wqr.get19().toString() : "") + (!prelastname.startsWith(lastname) && !prelastname.equals("") ? " вместо " + prelastname : "");
                res.append("<input type=\"button\" value=\"...\" onclick=\"showToastMessage('").append(msg.equals("") ? "Нет данных." : msg).append("',null,true);\"/>");
            }

            res.append("</li>");
            if (i >= cntLi) {
                res.append("</ul></li>");
                i = 0;
            }
        }
        res.append("</ul></li></ul>");
        return res.toString();
    }

    public String preRecordByPatient(String aPatInfo, Long aPatientId
            , Long aFunction, Long aSpec, Long aDay, Long aTime
            , Long aServiceStream, String aPhone, Long aService, Long preWayOfRecord, HttpServletRequest aRequest
    ) throws NamingException {
        IWorkCalendarService service = Injection.find(aRequest).getService(IWorkCalendarService.class);
        String username = LoginInfo.find(aRequest.getSession(true)).getUsername();
        service.preRecordByPatient(username, aFunction, aSpec, aDay, aTime, aPatInfo, aPatientId, aServiceStream,
                aPhone, aService, preWayOfRecord);
        return "Сохранено";
    }

    public String preRecordByTimeAndPatient(String aPatInfo, Long aPatientId
            , Long aTime, Long preWayOfRecord
            , HttpServletRequest aRequest
    ) throws NamingException {
        IWorkCalendarService service = Injection.find(aRequest).getService(IWorkCalendarService.class);
        String username = LoginInfo.find(aRequest.getSession(true)).getUsername();
        return service.preRecordByPatient(username, aTime, aPatInfo, aPatientId, preWayOfRecord);
    }

    public String deletePreRecord(Long aTime, HttpServletRequest aRequest) throws NamingException {
        IWorkCalendarService service = Injection.find(aRequest).getService(IWorkCalendarService.class);
        String username = LoginInfo.find(aRequest.getSession(true)).getUsername();
        return service.deletePreRecord(username, aTime);
    }

    // Получить минимальное и максимальное время приема за день по специалисту
    public String getIntervalBySpecAndDate(String aDate
            , Long aSpecialist, HttpServletRequest aRequest) throws ParseException, NamingException {
        IWorkCalendarService service = Injection.find(aRequest).getService(IWorkCalendarService.class);
        return service.getIntervalBySpecAndDate(aDate, aSpecialist);
    }

    // Получить новые времена по специалисту за определенное число
    public String getTimesBySpecAndDate(String aDate
            , Long aSpecialist, Long aCountVisits
            , String aBeginTime, String aEndTime
            , HttpServletRequest aRequest) throws NamingException, ParseException {
        IWorkCalendarService service = Injection.find(aRequest).getService(IWorkCalendarService.class);
        return service.getTimesBySpecAndDate(aDate, aSpecialist, aCountVisits, aBeginTime, aEndTime);
    }

    public String addNewTimeBySpecialist(String aDate
            , Long aReserve
            , Long aSpecialist
            , String aBeginTime, String aEndTime, HttpServletRequest aRequest) throws ParseException, NamingException {
        java.sql.Time timeFrom = DateFormat.parseSqlTime(aBeginTime);
        java.sql.Time timeTo = DateFormat.parseSqlTime(aEndTime);
        Calendar cal1 = java.util.Calendar.getInstance();
        Calendar cal2 = java.util.Calendar.getInstance();
        cal1.setTime(timeFrom);
        cal2.setTime(timeTo);
        int hour1 = cal1.get(Calendar.HOUR_OF_DAY);
        int hour2 = cal2.get(Calendar.HOUR_OF_DAY);
        int min1 = cal1.get(Calendar.MINUTE);
        int min2 = cal2.get(Calendar.MINUTE);
        int dif = (hour2 - hour1) * 60 + min2 - min1;
        if (dif == 0) throw new IllegalDataException("Разница между временами должна быть больше 1 минуты");
        int interval = dif / 2;
        cal1.add(java.util.Calendar.MINUTE, interval);
        java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("HH:mm");
        IWorkCalendarService service = Injection.find(aRequest).getService(IWorkCalendarService.class);
        return service.addCreateNewTimeBySpecAndDate(aDate, aSpecialist, format.format(cal1.getTime()), aReserve);

    }

    // Создать новые времена по специалисту за определенное число
    public String getCreateNewTimesBySpecAndDate(String aDateFrom
            , String aDateTo
            , Long aReserve
            , Long aSpecialist, String aTimes
            , HttpServletRequest aRequest) throws NamingException, ParseException {
        IWorkCalendarService service = Injection.find(aRequest).getService(IWorkCalendarService.class);
        if (aDateTo.equals("")) aDateTo = aDateFrom;
        java.util.Date dFrom = DateFormat.parseDate(aDateFrom);
        java.util.Date dTo = DateFormat.parseDate(aDateTo);
        Calendar calFrom = Calendar.getInstance();
        calFrom.setTime(dFrom);
        Calendar calTo = Calendar.getInstance();
        calTo.setTime(dTo);
        calTo.add(Calendar.DAY_OF_MONTH, 1);
        while (calTo.after(calFrom)) {
            service.getCreateNewTimesBySpecAndDate(new java.sql.Date(calFrom.getTime().getTime()), aSpecialist, aTimes, aReserve);
            calFrom.add(Calendar.DAY_OF_MONTH, 1);
        }
        return "Созданы";
    }

    public String generateBySpecialist(Long aWorkFunction, HttpServletRequest aRequest) throws NamingException {
        IWorkCalendarService service = Injection.find(aRequest).getService(IWorkCalendarService.class);
        java.util.Date date = new java.util.Date();
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, 50);
        service.generateCalendarByWorkFunction(aWorkFunction, new Date(date.getTime()), new Date(cal.getTime().getTime()));
        return "Сгенерировано";

    }

    public String addBusyPattern(Long aWorkFunction, String aDateFrom,
                                 String aDateTo, Long aPattern, HttpServletRequest aRequest) throws NamingException, ParseException {
        IWorkCalendarService service = Injection.find(aRequest).getService(IWorkCalendarService.class);
        java.sql.Date dateFrom = DateFormat.parseSqlDate(aDateFrom);
        java.sql.Date dateTo = DateFormat.parseSqlDate(aDateTo);
        service.addBusyPatternByWorkFunction(aWorkFunction, dateFrom, dateTo, aPattern);
        return "Добавлено";
    }

    public Long getSecUser(HttpServletRequest aRequest) throws NamingException {
        IPolyclinicMedCaseService service = Injection.find(aRequest).getService(IPolyclinicMedCaseService.class);
        return service.getSecUser();
    }

    public Long getWorkFunction(HttpServletRequest aRequest) throws NamingException {
        IPolyclinicMedCaseService service = Injection.find(aRequest).getService(IPolyclinicMedCaseService.class);
        return service.getWorkFunction();
    }

    public Long getWorkCalendar(Long aWorkFunction, HttpServletRequest aRequest) throws NamingException {
        IPolyclinicMedCaseService service = Injection.find(aRequest).getService(IPolyclinicMedCaseService.class);
        return service.getWorkCalendar(aWorkFunction);

    }

    public String getWorkCalendarDay(Long aWorkCalendar, Long aWorkFucntion, String aCalendarDate, HttpServletRequest aRequest) throws NamingException, ParseException {
        IPolyclinicMedCaseService service = Injection.find(aRequest).getService(IPolyclinicMedCaseService.class);
        return service.getWorkCalendarDay(aWorkCalendar, aWorkFucntion, aCalendarDate);

    }


    public String getTableHeaderByDayAndFunction(String aDateStart, String aDateFinish, Long aWidthSpec, Long aWidthDate, HttpServletRequest aRequest) throws Exception {
        Date dateStart = DateFormat.parseSqlDate(aDateStart);
        Date dateFinish = DateFormat.parseSqlDate(aDateFinish);
        StringBuilder tr = new StringBuilder();
        SimpleDateFormat FORMAT_1 = new SimpleDateFormat("dd.MM.yyyy");
        Calendar calnext = Calendar.getInstance();
        calnext.setTime(dateStart);
        Calendar calstop = Calendar.getInstance();
        calstop.setTime(dateFinish);
        calstop.add(Calendar.DATE, 1);
        StringBuilder div = new StringBuilder();
        tr.append("<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\"><tr class=\"x-grid-hd-row x-grid-header\">");
        tr.append("<td class=\"x-grid-hd grid-td-id\"><div class='x-grid-hd-spec'>");
        tr.append("Специалист");
        tr.append("</div></td>");
        Long width = aWidthSpec + 3;
        div.append("<div class='x-grid-split x-grid-split-spec' unselectable='on' style='left:")
                .append(width)
                .append("px'> </div>");
        calnext.setTime(dateStart);
        while (
                calnext.getTime().getTime() < calstop.getTime().getTime()
        ) {
            width = width + aWidthDate + 6;
            tr.append("<td class=\"x-grid-hd \"><div class='x-grid-hd-date'>");
            tr.append(FORMAT_1.format(calnext.getTime()));
            tr.append("</div></td>");
            div.append("<div class='x-grid-split x-grid-split-date' unselectable='on' style='left:")
                    .append(width)
                    .append("px'> </div>");
            calnext.add(Calendar.DATE, 1);
        }
        tr.append("</tr></table>");
        tr.append(div);
        return tr.toString();
    }

    @SuppressWarnings("deprecation")
    public String getTableBodyByDayAndFunction(String aDateStart, String aDateFinish, Long aVocWorkFunctionId, String aMethodName, HttpServletRequest aRequest) throws Exception {
        IWorkerService service = Injection.find(aRequest).getService(IWorkerService.class);
        Date dateStart = DateFormat.parseSqlDate(aDateStart);
        Date dateFinish = DateFormat.parseSqlDate(aDateFinish);
        List<TableTimeBySpecialists> listSpec = service.getTableByDayAndFunction(dateStart, dateFinish, aVocWorkFunctionId);
        Calendar calnext = Calendar.getInstance();
        calnext.setTime(dateStart);
        Calendar calstop = Calendar.getInstance();
        calstop.setTime(dateFinish);
        calstop.add(Calendar.DATE, 1);
        Calendar calcurrent = Calendar.getInstance();
        Long idspec = null;
        String spec = "";
        StringBuilder tr = new StringBuilder();
        SimpleDateFormat FORMAT_2 = new SimpleDateFormat("dd.MM.yyyy");
        calnext.setTime(dateStart);
        int j = 0;

        for (TableTimeBySpecialists row : listSpec) {
            if (idspec == null) {
                spec = service.getWorkFunctionInfoById(row.getSpecialistId());
                calnext.setTime(dateStart);
                tr.append("<tr");
                tr.append(" class=\"x-grid-row x-grid-row-alt\">");
                tr.append("<td class=\"x-grid-col \"><div class='x-grid-hd-spec'>");
                tr.append(spec);
                tr.append("</div></td>");
                j = 1;
            }
            if (idspec != null && !row.getSpecialistId().equals(idspec)) {
                spec = service.getWorkFunctionInfoById(row.getSpecialistId());
                // новая строка
                while (
                        calnext.getTime().getTime() < calstop.getTime().getTime()
                ) {
                    tr.append("<td >")
                            .append("<div class='x-grid-col x-grid-cell-inner x-grid-hd-date'>")
                            .append("-")
                            .append("</div>")
                            .append("</td>");

                    calnext.add(Calendar.DATE, 1);
                }

                if (j == 1) {
                    j = 2;
                } else {
                    j = 1;
                }


                calnext.setTime(dateStart);
                tr.append("</tr><tr");
                if (j == 1) {
                    tr.append(" class=\"x-grid-row x-grid-row-alt\"");
                } else {
                    tr.append(" class=\"x-grid-row\"");
                }
                tr.append(">");
                tr.append("<td class=\"x-grid-col \"><div class='x-grid-hd-spec'>");
                tr.append(spec);
                tr.append("</div></td>");

            }
            calcurrent.setTime(row.getDate());

            if (calcurrent.getTime().getTime() == calnext.getTime().getTime()) {
                tr.append("<td >")
                        .append("<div class='x-grid-col x-grid-cell-inner x-grid-hd-date'>")
                        .append("<a href='javascript:")
                        .append(aMethodName)
                        .append("(\"").append(row.getSpecialistId()).append("\"")
                        .append(",\"").append(row.getCalendarDayId()).append("\"")
                        .append(",\"").append(row.getTimeMin()).append("\"")
                        .append(",\"").append(spec).append("\"")
                        .append(",\"").append(FORMAT_2.format(row.getDate().getTime())).append("\"")
                        .append(",\"").append(1).append("\"")
                        .append(")")
                        .append("'>")
                        .append(row.getTimeMin())
                        .append("</a>-")
                        .append("<a href='javascript:")
                        .append(aMethodName)
                        .append("(\"").append(row.getSpecialistId()).append("\"")
                        .append(",\"").append(row.getCalendarDayId()).append("\"")
                        .append(",\"").append(row.getTimeMax()).append("\"")
                        .append(",\"").append(spec).append("\"")
                        .append(",\"").append(FORMAT_2.format(row.getDate().getTime())).append("\"")
                        .append(",\"").append(0).append("\"")
                        .append(")")
                        .append("'>")
                        .append(row.getTimeMax())
                        .append("</a>")
                        .append("</div>")
                        .append("</td>");
            } else {
                while (
                        calcurrent.getTime().getTime() != calnext.getTime().getTime()
                                && calcurrent.getTime().getTime() < calstop.getTime().getTime()
                ) {
                    tr.append("<td >")
                            .append("<div class='x-grid-col x-grid-cell-inner x-grid-hd-date'>")
                            .append("-")
                            .append("</div>")
                            .append("</td>");
                    calnext.add(Calendar.DATE, 1);
                }
                if (calcurrent.getTime().getTime() == calnext.getTime().getTime()) {
                    tr.append("<td><div class='x-grid-col x-grid-col-id x-grid-cell-inner x-grid-hd-date'>")
                            .append("<a href='javascript:")
                            .append(aMethodName)
                            .append("(\"").append(row.getSpecialistId()).append("\"")
                            .append(",\"").append(row.getCalendarDayId()).append("\"")
                            .append(",\"").append(row.getTimeMin()).append("\"")
                            .append(",\"").append(spec).append("\"")
                            .append(",\"").append(FORMAT_2.format(row.getDate().getTime())).append("\"")
                            .append(",\"").append(1).append("\"")
                            .append(")")
                            .append("'>")
                            .append(row.getTimeMin())
                            .append("</a>- ")
                            .append("<a href='javascript:")
                            .append(aMethodName)
                            .append("(\"").append(row.getSpecialistId()).append("\"")
                            .append(",\"").append(row.getCalendarDayId()).append("\"")
                            .append(",\"").append(row.getTimeMax()).append("\"")
                            .append(",\"").append(spec).append("\"")
                            .append(",\"").append(FORMAT_2.format(row.getDate().getTime())).append("\"")
                            .append(",\"").append(0).append("\"")
                            .append(")")
                            .append("'>")
                            .append(row.getTimeMax())
                            .append("</a>")
                            .append("</div></td>");
                }
            }

            calnext.add(Calendar.DATE, 1);
            idspec = row.getSpecialistId();
        }
        if (listSpec.size() > 0) {

            while (
                    calnext.getTime().getTime() < calstop.getTime().getTime()
            ) {
                tr.append("<td >")
                        .append("<div class='x-grid-col x-grid-cell-inner x-grid-hd-date'>")
                        .append("-")
                        .append("</div>")
                        .append("</td>");

                calnext.add(Calendar.DATE, 1);
            }
        } else {
            tr.append("<td>Нет данных</td>");
        }
        return tr.toString();
    }

    public String getCalendarTimeId(Long aCalendarDay, String aCalendarTime, Long aMinIs, HttpServletRequest aRequest) throws Exception {
        IWorkerService service = Injection.find(aRequest).getService(IWorkerService.class);
        Time time = DateFormat.parseSqlTime(aCalendarTime);
        String ret = service.getCalendarTimeId(aCalendarDay, time, aMinIs);
        if (ret == null) {
            throw new IllegalArgumentException("На это число нет свободного времени");
        }
        return ret;
    }

    public String getDefaultDate(Long aFuncId, HttpServletRequest aRequest) throws Exception {
        IWorkerService service = Injection.find(aRequest).getService(IWorkerService.class);
        return service.getDayBySpec(aFuncId);

    }

    /**
     * Копировать день.
     *
     * @param aCalendarDay Long - день, расписание которого скопировать на преиод:
     * @param date         String  - дата начала периода
     * @param date2        String 0 дата окончания периода
     * @param date2        String 0 дата окончания периода
     * @param aRequest     HttpServletRequest
     * @return String сообщение пользователю
     */
    public String copyDay(Long aCalendarDay, String date, String date2, HttpServletRequest aRequest) throws Exception {
        String username = LoginInfo.find(aRequest.getSession(true)).getUsername();
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        Date beginDate = DateFormat.parseSqlDate(date);
        Date endDate = DateFormat.parseSqlDate(date2);

        do {
            String sql = "select id from workcalendarday where calendardate=to_date('" + beginDate + "','yyyy-MM-dd') and workcalendar_id=(select workcalendar_id from workcalendarday where id=" + aCalendarDay + ")";
            Collection<WebQueryResult> list = service.executeNativeSql(sql);
            String id = "";
            if (!list.isEmpty()) for (WebQueryResult wqr : list)
                id = wqr.get1().toString(); //день уже создан - добавлеяем времена к существующим
            else { //день ещё не создан - создаём
                Collection<WebQueryResult> res = service.executeNativeSql("insert into workcalendarday(calendardate,holiday,workcalendar_id,insteadofday_id,isdeleted)\n" +
                        "select to_date('" + new SimpleDateFormat("dd.MM.yyyy").format(beginDate) + "','dd.mm.yyyy'),holiday,workcalendar_id,insteadofday_id,isdeleted from workcalendarday where id=" + aCalendarDay + "  returning id");
                for (WebQueryResult wqr : res) id = wqr.get1().toString();
            }
            //если день был удалён, снимаем отметку
            service.executeUpdateNativeSql("update workcalendarday set isdeleted=false where id=" + id);
            //добавляем времена, проверка на повторы в триггера на стороне бд
            service.executeUpdateNativeSql("insert into workcalendartime(timefrom,additional,rest,workcalendarday_id,createprerecord,\n" +
                    "createdateprerecord,createtimeprerecord,reservetype_id,createdate,createtime)\n" +
                    "select timefrom,additional,rest," + id + ",'" + username + "',current_date,current_time,\n" +
                    "reservetype_id,current_date as createdate,current_time\n" +
                    " from workcalendartime where workcalendarday_id=" + aCalendarDay + " and (isdeleted is null or isdeleted='0')");

            if (new SimpleDateFormat("dd.MM.yyyy").format(beginDate).equals(new SimpleDateFormat("dd.MM.yyyy").format(endDate)))
                break;
            beginDate = new java.sql.Date(beginDate.getTime() + 24 * 60 * 60 * 1000); //следующий день
        }
        while (!new SimpleDateFormat("dd.MM.yyyy").format(beginDate).equals(new SimpleDateFormat("dd.MM.yyyy").format(endDate)));
        return "Скопировано.";
    }


    /**
     * Помечаем период дней удалёнными
     *
     * @param aCalendarDay Long - день, расписание которого скопировать на преиод:
     * @param date         String  - дата начала периода
     * @param date2        String 0 дата окончания периода
     * @param date2        String 0 дата окончания периода
     * @param aRequest     HttpServletRequest
     * @return String сообщение пользователю
     */
    public String deletePeriodDay(Long aCalendarDay, String date, String date2, HttpServletRequest aRequest) throws NamingException {
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        StringBuilder sql = new StringBuilder();
        sql.append("(select wcd.id from workcalendarday wcd where")
                .append(" wcd.workcalendar_id = (select workcalendar_id from workcalendarday where id=")
                .append(aCalendarDay)
                .append(") and (wcd.isDeleted is null or wcd.isDeleted = false) and wcd.calendardate between to_date('")
                .append(date).append("','dd.mm.yyyy')")
                .append("and to_date('").append(date2).append("','dd.mm.yyyy'))");

        service.executeUpdateNativeSql("update workcalendartime set isdeleted=true where workcalendarday_id=ANY" + sql.toString());
        service.executeUpdateNativeSql("update workcalendarday set isdeleted=true where id=ANY" + sql.toString());
        return "Удалено";
    }

    /**
     * Изменить резерв у массива.
     *
     * @param array         Long[] - массив
     * @param reserveTypeId Long  - новый резерв
     * @param aRequest      HttpServletRequest
     * @return String "0" - всё ок
     */
    public String changeScheduleArrayReserve(Long[] array, Long reserveTypeId, HttpServletRequest aRequest) throws NamingException {
        for (Long aLong : array) changeScheduleElementReserve(aLong, reserveTypeId, aRequest);
        return "0";
    }

    /**
     * Добавить время после.
     *
     * @param aTime    Long - время, после которого добавить
     * @param mins     String  - интервал
     * @param aRequest HttpServletRequest
     * @return String путь
     */
    public String addTime(Long aTime, String mins, HttpServletRequest aRequest) throws Exception {
        String username = LoginInfo.find(aRequest.getSession(true)).getUsername();
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        Collection<WebQueryResult> list = service.executeNativeSql("select timefrom from workcalendartime where id=" + aTime);
        String timefrom = "";
        if (!list.isEmpty()) for (WebQueryResult wqr : list) timefrom = wqr.get1().toString();
        if (!timefrom.equals("") && timefrom.length() > 2) {
            timefrom = timefrom.substring(0, 2) + ":" + mins + ":00";
        }
        service.executeUpdateNativeSql("insert into workcalendartime(timefrom,additional,rest,workcalendarday_id,createprerecord,\n" +
                "createdateprerecord,createtimeprerecord,reservetype_id,createdate,createtime)\n" +
                "select to_timestamp('" + timefrom + "','HH24:MI:SS'),additional,rest,workcalendarday_id,'" + username + "',current_date,current_time,\n" +
                "reservetype_id,current_date,current_time from workcalendartime where id=" + aTime);
        return "Добавлено.";
    }

    /**
     * Получить возможные способы предварительной записи пациентов (выбирает регистратор при создании предварительной записи) #145
     *
     * @param aRequest HttpServletRequest
     * @return String json Способы предварительной записи пациентов
     */
    public String getWaysOfPreRecords(HttpServletRequest aRequest) throws NamingException {
        JSONArray res = new JSONArray();
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        Collection<WebQueryResult> list = service.executeNativeSql("select  id,name from vocwayofrecord  where isavailableforrecorder =true");
        for (WebQueryResult w : list) {
            JSONObject o = new JSONObject();
            o.put("id", w.get1())
                    .put("name", w.get2());
            res.put(o);
        }
        return res.toString();
    }

    /**
     * Получить кабинет, связанный с раб. функцией #152
     *
     * @param wfId     String WorkFunction.id
     * @param aRequest HttpServletRequest
     * @return String Кабинет
     */
    public String getWfCabinet(String wfId, HttpServletRequest aRequest) throws NamingException {
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        Collection<WebQueryResult> list = service.executeNativeSql("select wpl.name from workplace wpl\n" +
                "left join workplace_workfunction wpwf on wpwf.workplace_id=wpl.id\n" +
                "where wpwf.workfunctions_id=" + wfId);
        return list.isEmpty() ? "" : list.iterator().next().get1().toString();
    }

    /**
     * Обновить коды промеда #262
     *
     * @param wfId     String WorkFunction.id
     * @param aRequest HttpServletRequest
     * @return String Сообщение
     */
    public String updatePromedCodes(String wfId, HttpServletRequest aRequest) throws NamingException {
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        Collection<WebQueryResult> list = service.executeNativeSql("select pat.snils from patient pat" +
                " left join worker w on w.person_id=pat.id" +
                " left join workfunction wf on wf.worker_id=w.id" +
                " where wf.id=" + wfId);
        if (!list.isEmpty() && list.iterator().next().get1() != null) {
            String snils = list.iterator().next().get1().toString().replace(" ", "");
            if (!"".equals(snils)) {
                IDisabilityService service1 = Injection.find(aRequest).getService(IDisabilityService.class);
                String endpoint = service1.getSoftConfigValue("PROMEDATOR", "null");
                Map<String, String> params = new HashMap<>();
                params.put("snils", snils);
                String json = createGetRequest(endpoint, "update/lpuAndWorkStaff", params);
                JsonParser parser = new JsonParser();
                JsonObject obj = parser.parse(json).getAsJsonObject();
                String lpuSectionId = obj.get("lpuSectionId").getAsString();
                String medstaffId = obj.get("medstaffId").getAsString();
                service.executeUpdateNativeSql("update workfunction set promedcode_lpusection='" + lpuSectionId +
                        "', promedcode_workstaff='" + medstaffId + "' where id=" + wfId);
                return "Коды обновлены! lpuSectionId = " + lpuSectionId + " , medstaffId = " + medstaffId;
            }
        }
        return "Не удалось обновить коды!";
    }

    /**
     * Сделать заведующим отделения #264
     *
     * @param wfId     String WorkFunction.id
     * @param aRequest HttpServletRequest
     */
    public void makeZav(String wfId, HttpServletRequest aRequest) throws NamingException {
        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
        service.executeUpdateNativeSql("update workfunction set isadministrator = true where id = " + wfId);
        service.executeUpdateNativeSql("update workfunction set isadministrator=false" +
                " where id in (select distinct wf.id from workfunction wf" +
                " left join worker w on wf.worker_id=w.id" +
                " left join worker wnow on wnow.lpu_id=(" +
                " select distinct w.lpu_id from worker w" +
                " left join workfunction wf on wf.worker_id=w.id" +
                " where wf.id=" + wfId + " limit 1)" +
                " where w.lpu_id =wnow.lpu_id and w.id<>wnow.id and wf.id<>" + wfId + ")");
    }
}