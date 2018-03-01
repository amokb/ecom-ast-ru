<%@page import="ru.ecom.web.util.ActionUtil"%>
<%@ page import="java.util.regex.Matcher" %>
<%@ page import="java.util.regex.Pattern" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%
    String nul = request.getParameter("nul") ;
    if (nul==null) {

%>
<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true" >
    <tiles:put name='title' type='string'>
        <msh:title mainMenu="StacJournal">Сводный отчёт по КДЛ</msh:title>
    </tiles:put>
    <tiles:put name='side' type='string'>
    </tiles:put>
    <tiles:put name="body" type="string">
        <%
            String deps=ActionUtil.updateParameter("ReportKDL","departments","", request) ;
            if (deps!=null && !deps.equals("")) {
                Matcher m = Pattern.compile("\"value\":[0-9]*,").matcher(deps);
                StringBuilder depRqst=new StringBuilder();
                depRqst.append(" and (dep.id= ");
                int cnt=0;
                while (m.find()) {
                    if (cnt!=0) depRqst.append(" or dep.id= ");
                    depRqst.append(m.group().replace("\"value\":","").replace(",",""));
                    cnt++;
                }
                if (cnt==0) {
                    m = Pattern.compile("\"value\":\"[0-9]*\"").matcher(deps);
                    depRqst=new StringBuilder();
                    depRqst.append(" and (dep.id= ");
                    cnt=0;
                    while (m.find()) {
                        if (cnt!=0) depRqst.append(" or dep.id= ");
                        depRqst.append(m.group().replace("\"value\":","").replace("\"",""));
                        cnt++;
                    }
                }
                if (!depRqst.toString().equals(" and (dep.id= ")) request.setAttribute("deps",depRqst.append(") ").toString());
                else request.setAttribute("deps","");
            }
            else request.setAttribute("deps","");
            String dateBegin = request.getParameter("dateBegin") ;
            if (dateBegin!=null && !dateBegin.equals("")) {
                request.setAttribute("dateBegin",dateBegin);
            }
            String dateEnd = request.getParameter("dateEnd") ;
            if (dateEnd==null || dateEnd.equals("")) dateEnd=dateBegin;
            if (dateEnd!=null && !dateEnd.equals("")) {
                request.setAttribute("dateEnd",dateEnd);
            }
            String typeVMPOrNot = ActionUtil.updateParameter("reportKDL","typeVMPOrNot","1",request);
            if (typeVMPOrNot!=null) {
                if (typeVMPOrNot.equals("2")) {
                    request.setAttribute("typeVMPOrNotValueLeftJoin", "left join hitechmedicalcase highmc on highmc.medcase_id=dmc.id");
                    request.setAttribute("typeVMPOrNotValueNotNull", " and highmc.medcase_id is not null ");
                    request.setAttribute("typeVMPOrNotValueTotalCnt", " case when highmc.medcase_id is not null then count(mc.id) else '0' end as totalCnt ");
                    request.setAttribute("typeVMPOrNotValueGroup", ",highmc.medcase_id");
                    request.setAttribute("typeVMPOrNotValueTotalCntStac", " case when highmc.medcase_id is not null then count(distinct mc.id) else '0' end as totalCnt ");
                    request.setAttribute("typeVMPOrNotValueJustWhere", " and highmc.medcase_id is not null");
                }
                else if(typeVMPOrNot.equals("1")) {
                    request.setAttribute("typeVMPOrNotValueLeftJoin","");
                    request.setAttribute("typeVMPOrNotValueWhere","");
                    request.setAttribute("typeVMPOrNotValueTotalCnt", "count(mc.id) as totalCnt");
                    request.setAttribute("typeVMPOrNotValueGroup", "");
                    request.setAttribute("typeVMPOrNotValueTotalCntStac","count(distinct mc.id) as totalCnt");
                }
            }
            else request.setAttribute("typeVMPOrNotValue","");

            String typeStacOrNot = ActionUtil.updateParameter("reportKDL","typeStacOrNot","1",request);
            if (typeStacOrNot==null) request.setAttribute("typeStacOrNotValue","");
            request.setAttribute("dateT"," mc.dateStart ");
            if (request.getParameter("short")!=null) {
                request.setAttribute("typeStacOrNot","1");
            }
            if (request.getParameter("short")==null) {
        %>
        <msh:form action="/reportKDL.do" defaultField="departments" disableFormDataConfirm="true" method="GET">
            <msh:panel>
                <msh:row>
                    <ecom:oneToManyOneAutocomplete label="Отделения" vocName="vocLpuHospOtdAll" property="departments" colSpan="10"/>
                </msh:row>
                <msh:row>
                    <msh:textField property="dateBegin" label="Период с" guid="8d7ef035-1273-4839-a4d8-1551c623caf1" />
                    <msh:textField property="dateEnd" label="по" guid="f54568f6-b5b8-4d48-a045-ba7b9f875245" />
                </msh:row>
                <msh:row guid="7d80be13-710c-46b8-8503-ce0413686b69">
                </msh:row>
                <msh:row guid="7d80be13-710c-46b8-8503-ce0413686b69">
                    <td class="label" title="Поиск по промежутку  (typeStacOrNot)" colspan="1"><label for="typeStacOrNotName" id="ttypeStacOrNotLabel">Группировать по:</label></td>
                    <td onclick="this.childNodes[1].checked='checked';" colspan="1">
                        <input type="radio" name="typeStacOrNot" value="1"> типу исследования
                    </td>
                    <td onclick="this.childNodes[1].checked='checked';" colspan="2">
                        <input type="radio" name="typeStacOrNot" value="2"> по отделениям, вывод по стационару
                    </td>
                    <td onclick="this.childNodes[1].checked='checked';" colspan="3">
                        <input type="radio" name="typeStacOrNot" value="3"> группировка по отделениям, вывод по типу исследования
                    </td>
                </msh:row>
                <msh:row guid="7d80be13-710c-46b8-8503-ce0413686b69">
                    <td class="label" title="Поиск по промежутку  (typeVMPOrNot)" colspan="1"><label for="typeVMPOrNotName" id="typeVMPOrNotLabel">Отобразить ВМП:</label></td>
                    <td onclick="this.childNodes[1].checked='checked';" colspan="1">
                        <input type="radio" name="typeVMPOrNot" value="1"> Всё
                    </td>
                    <td onclick="this.childNodes[1].checked='checked';" colspan="2">
                        <input type="radio" name="typeVMPOrNot" value="2"> Только ВМП
                    </td>
                </msh:row>
                <msh:row>
                    <td colspan="3">
                        <input type="button" onclick="find()" value="Найти" />
                    </td>
                </msh:row>
            </msh:panel>
        </msh:form>
        <%
            }
            if (request.getParameter("dateBegin")!=null &&  !request.getParameter("dateBegin").equals("")) {
                if (request.getParameter("typeStacOrNot").equals("1") || request.getParameter("short")!=null) {
                    if (request.getParameter("short")!=null) {
                        String depId = request.getParameter("depId");
                        if (depId!=null && !depId.equals("")) {
                            request.setAttribute("depId"," and dep.id="+depId);
                            request.setAttribute("deps","");
                        }
                    }
        %>
        <msh:section>
            <msh:sectionTitle>
                <ecom:webQuery name="total" nameFldSql="total_sql" nativeSql="
                select  name1 as name1,name2 as name2, adCode as adCode,name3 as name3, shname as shname,sum(totalCnt),sum(noPlanCnt) as noPlanCnt,sum(urgentCnt) as urgentCnt,sum(emCnt) as emCnt,sum(planCnt) as planCnt
                 from
                (
                 select ms.code as name1,msPr.name as name2,ms.additionCode as adCode,ms.name as name3,ms.shortname as shname
                ,count(mc.id) as totalCnt
                ,case when pt.code='NOPLAN' ${typeVMPOrNotValueNotNull}  then count(distinct mc.id) else '0' end as noPlanCnt
                ,case when pt.code='URGENT' ${typeVMPOrNotValueNotNull} then count(distinct mc.id) else '0' end as urgentCnt
                ,case when pt.code='EMERGENCY' ${typeVMPOrNotValueNotNull} then count(distinct mc.id) else '0' end as emCnt
                ,case when pt.code='PLAN' ${typeVMPOrNotValueNotNull} then count(distinct mc.id) else '0' end as planCnt
                from MedService ms
                left join prescription pr on pr.medservice_id=ms.id
                left join prescriptionlist pl on pr.prescriptionlist_id=pl.id
                left join medcase mc on mc.id=pr.medcase_id
                left join medcase dmc on dmc.id=pl.medcase_id
                left join workfunction wf on wf.id=pr.prescriptspecial_id
                left join worker w on w.id=wf.worker_id
                left join MisLpu dep on dep.id=pr.department_id or dep.id=w.lpu_id
                left join vocprescripttype pt on pt.id=pr.prescripttype_id
                left join vocservicetype vst on vst.id=ms.servicetype_id
                left join MedService msPr on msPr.id=ms.parent_id
                ${typeVMPOrNotValueLeftJoin}
                where ${dateT} between to_date('${dateBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
                and vst.code='LABSURVEY' ${deps} ${depId} ${typeVMPOrNotValueNotNull}
                group by ms.id,pt.code,msPr.id ${typeVMPOrNotValueGroup}
                order by ms.id
                 ) as t
                group by name1,name2,adCode,shname,name3
                order by name2
"/>
                <form action="javascript:void(0)" method="post" target="_blank">

                </form>
            </msh:sectionTitle>
            <msh:sectionContent>
                <msh:table printToExcelButton="Сохранить в excel" name="total"
                           viewUrl="reportKDL.do"
                           action="javascript:void(0)" idField="11" cellFunction="true" >
                    <msh:tableColumn columnName="#" property="sn" addParam="&nul=nul" />
                    <msh:tableColumn columnName="Код" property="1" addParam="&nul=nul" />
                    <msh:tableColumn columnName="Группа исследований" property="2" addParam="&nul=nul"/>
                    <msh:tableColumn columnName="Внутренний код" property="3" addParam="&nul=nul"/>
                    <msh:tableColumn columnName="Наименование" property="4" addParam="&nul=nul"/>
                    <msh:tableColumn columnName="Короткое имя" property="5" addParam="&nul=nul"/>
                    <msh:tableColumn columnName="Количество всего" property="6" isCalcAmount="true" addParam="&nul=nul" />
                    <msh:tableColumn columnName="В т.ч. до 1ч" property="7" isCalcAmount="true" addParam="&nul=nul" />
                    <msh:tableColumn columnName="В т.ч. до 3ч" property="8" isCalcAmount="true" addParam="&nul=nul" />
                    <msh:tableColumn columnName="В т.ч. до 4ч" property="9" isCalcAmount="true" addParam="&nul=nul" />
                    <msh:tableColumn columnName="24 часа +" property="10" isCalcAmount="true" addParam="&nul=nul" />
                </msh:table>
            </msh:sectionContent>
        </msh:section>
        <%
            if (request.getParameter("short")==null) {
        %>
        <msh:section>
            <msh:sectionTitle>
                <ecom:webQuery name="totalName" nameFldSql="totalName_sql" nativeSql="
               select name,sum(cnt1) as cnt1,sum(cnt2) as cnt2,sum(cnt3) as cnt3,sum(cnt4) as cnt4,sum(cnt5) as cnt5
                ,sum(cnt6) as cnt6,sum(cnt7) as cnt7 from
                (select dep.name
                ,case when msPr.code='Q01' ${typeVMPOrNotValueNotNull} then count(mc.id) else '0' end as cnt1
                ,case when msPr.code='Q02' ${typeVMPOrNotValueNotNull} then count(mc.id) else '0' end as cnt2
                ,case when msPr.code='Q03' ${typeVMPOrNotValueNotNull} then count(mc.id) else '0' end as cnt3
                ,case when msPr.code='Q04' ${typeVMPOrNotValueNotNull} then count(mc.id) else '0' end as cnt4
                ,case when msPr.code='Q05' ${typeVMPOrNotValueNotNull} then count(mc.id) else '0' end as cnt5
                ,case when msPr.code='Q06' ${typeVMPOrNotValueNotNull} then count(mc.id) else '0' end as cnt6
                ,case when vst.code='LABSURVEY' ${typeVMPOrNotValueNotNull} then count(mc.id) else '0' end as cnt7
                from MedService ms
		        left join prescription pr on pr.medservice_id=ms.id
                left join prescriptionlist pl on pr.prescriptionlist_id=pl.id
                left join medcase mc on mc.id=pr.medcase_id
                left join medcase dmc on dmc.id=pl.medcase_id
                left join workfunction wf on wf.id=pr.prescriptspecial_id
                left join worker w on w.id=wf.worker_id
                left join MisLpu dep on dep.id=pr.department_id or dep.id=w.lpu_id
                left join vocprescripttype pt on pt.id=pr.prescripttype_id
                left join vocservicetype vst on vst.id=ms.servicetype_id
                left join MedService msPr on msPr.id=ms.parent_id
                ${typeVMPOrNotValueLeftJoin}
                where ${dateT} between to_date('${dateBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
                ${deps}
                group by dep.id, mc.emergency,msPr.code,vst.code ${typeVMPOrNotValueGroup}
                order by dep.name
                ) as t
                group by name
"/>

                <form action="javascript:void(0)" method="post" target="_blank">
                </form>
            </msh:sectionTitle>
            <msh:sectionContent>
                <msh:table printToExcelButton="Сохранить в excel" name="totalName"
                           viewUrl="reportKDL.do"
                           action="javascript:void(0)" idField="11" cellFunction="true" >
                    <msh:tableColumn columnName="#" property="sn" addParam="&nul=nul" />
                    <msh:tableColumn columnName="Структурные подразделения" property="1" addParam="&nul=nul" />
                    <msh:tableColumn columnName="Гематологические" property="2" isCalcAmount="true" addParam="&nul=nul"/>
                    <msh:tableColumn columnName="Клинические" property="3" isCalcAmount="true" addParam="&nul=nul"/>
                    <msh:tableColumn columnName="Биохимические" property="4" isCalcAmount="true" addParam="&nul=nul"/>
                    <msh:tableColumn columnName="Гемостазиологические" property="5" isCalcAmount="true" addParam="&nul=nul" />
                    <msh:tableColumn columnName="Иммунологические" property="6" isCalcAmount="true" addParam="&nul=nul" />
                    <msh:tableColumn columnName="Микробиологические" property="7" isCalcAmount="true" addParam="&nul=nul" />
                    <msh:tableColumn columnName="ВСЕГО" property="8" isCalcAmount="true" addParam="&nul=nul" />
                </msh:table>
            </msh:sectionContent>
        </msh:section>
        <%
            }
            }
            else if (request.getParameter("typeStacOrNot").equals("2")) {
        %>
        <msh:section>
        <msh:sectionTitle>
        <ecom:webQuery name="total" nameFldSql="total_sql" nativeSql="
        select name,
        totalHosp as cnt1,emergCnt as cnt2,sum(totalLabCnt) as labCnt,sum(noPlanCnt) as cnt3,sum(urgentCnt) as cnt4,sum(emCnt) as cnt5,sum(planCnt) as cnt6
         ,depId from
        (select dep.name
        ,(
        select count(distinct hmc.id)
        from MedCase hmc
        left join MedCase dmc on dmc.parent_id=hmc.id
        ${typeVMPOrNotValueLeftJoin}
        where hmc.DTYPE='HospitalMedCase'
        and hmc.datestart between to_date('${dateBegin}','dd.mm.yyyy')
        and to_date('${dateEnd}','dd.mm.yyyy')
        and hmc.deniedHospitalizating_id is null
        and hmc.department_id=dep.id ${typeVMPOrNotValueJustWhere}
        ) as totalHosp
        ,(
        select count(distinct hmc.id)
        from MedCase hmc
        left join MedCase dmc on dmc.parent_id=hmc.id
        ${typeVMPOrNotValueLeftJoin}
        where hmc.DTYPE='HospitalMedCase'
        and hmc.datestart between to_date('${dateBegin}','dd.mm.yyyy')
        and to_date('${dateEnd}','dd.mm.yyyy')
        and hmc.deniedHospitalizating_id is null
        and hmc.department_id=dep.id ${typeVMPOrNotValueJustWhere}
        and hmc.emergency='1'
        ) as emergCnt
        ,case when vst.code='LABSURVEY' ${typeVMPOrNotValueNotNull} then count(mc.id) else '0' end as totalLabCnt
        ,case when pt.code='NOPLAN' and vst.code='LABSURVEY' ${typeVMPOrNotValueNotNull} then count(mc.id) else '0' end as noPlanCnt
        ,case when pt.code='URGENT' and vst.code='LABSURVEY' ${typeVMPOrNotValueNotNull} then count(mc.id) else '0' end as urgentCnt
        ,case when pt.code='EMERGENCY' and vst.code='LABSURVEY' ${typeVMPOrNotValueNotNull} then count(mc.id) else '0' end as emCnt
        ,case when pt.code='PLAN' and vst.code='LABSURVEY' ${typeVMPOrNotValueNotNull} then count(mc.id) else '0' end as planCnt
        ,'&depId='||coalesce(dep.id,0) as depId
        from MedService ms
        left join prescription pr on pr.medservice_id=ms.id
        left join prescriptionlist pl on pr.prescriptionlist_id=pl.id
        left join medcase mc on mc.id=pr.medcase_id
        left join medcase dmc on dmc.id=pl.medcase_id
        left join workfunction wf on wf.id=pr.prescriptspecial_id
        left join worker w on w.id=wf.worker_id
        left join MisLpu dep on dep.id=pr.department_id or dep.id=w.lpu_id
        left join vocprescripttype pt on pt.id=pr.prescripttype_id
        left join vocservicetype vst on vst.id=ms.servicetype_id
        left join MedService msPr on msPr.id=ms.parent_id
        ${typeVMPOrNotValueLeftJoin}
        where ${dateT} between to_date('${dateBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy') and dep.id<>0
        ${deps}
        group by dep.id, mc.emergency,pt.code,vst.code ${typeVMPOrNotValueGroup}
        order by dep.name
        ) as t
        group by name,depId,totalHosp,emergCnt
"/>
            <form action="javascript:void(0)" method="post" target="_blank"></form>
        </msh:sectionTitle>
            <msh:sectionContent>
                <msh:table printToExcelButton="Сохранить в excel" name="total"
                           viewUrl="reportKDL.do"
                           action="reportKDL.do" idField="9" cellFunction="true" >
                    <msh:tableColumn columnName="#" property="sn" addParam="&nul=nul" />
                    <msh:tableColumn columnName="Структурные подразделения" property="1" addParam="&short=Short&dateBegin=${param.dateBegin}&dateEnd=${param.dateEnd}&typeStacOrNot=1" />
                    <msh:tableColumn columnName="Поступило" property="2" isCalcAmount="true" addParam="&nul=nul"/>
                    <msh:tableColumn columnName="В т.ч. экстренно" property="3" isCalcAmount="true" addParam="&nul=nul"/>
                    <msh:tableColumn columnName="Кол-во выполненных исслед." property="4" isCalcAmount="true" addParam="&nul=nul"/>
                    <msh:tableColumn columnName="В т.ч. до 1ч" property="5" isCalcAmount="true" addParam="&nul=nul" />
                    <msh:tableColumn columnName="В т.ч. до 3ч" property="6" isCalcAmount="true" addParam="&nul=nul" />
                    <msh:tableColumn columnName="В т.ч. до 4ч" property="7" isCalcAmount="true" addParam="&nul=nul" />
                    <msh:tableColumn columnName="24 часа +" property="8" isCalcAmount="true" addParam="&nul=nul" />
                </msh:table>
            </msh:sectionContent>
        </msh:section>
        <%
            }
            else if (request.getParameter("typeStacOrNot").equals("3")) {
        %>
        <msh:section>
            <msh:sectionTitle>
                <ecom:webQuery name="total" nameFldSql="total_sql" nativeSql="
                select name,sum(cnt1) as cnt1,sum(cnt2) as cnt2,sum(cnt3) as cnt3,sum(cnt4) as cnt4,sum(cnt5) as cnt5
                ,sum(cnt6) as cnt6,sum(cnt7) as cnt7 from
                (select dep.name
                ,case when msPr.code='Q01' ${typeVMPOrNotValueNotNull} then count(mc.id) else '0' end as cnt1
                ,case when msPr.code='Q02' ${typeVMPOrNotValueNotNull} then count(mc.id) else '0' end as cnt2
                ,case when msPr.code='Q03' ${typeVMPOrNotValueNotNull} then count(mc.id) else '0' end as cnt3
                ,case when msPr.code='Q04' ${typeVMPOrNotValueNotNull} then count(mc.id) else '0' end as cnt4
                ,case when msPr.code='Q05' ${typeVMPOrNotValueNotNull} then count(mc.id) else '0' end as cnt5
                ,case when msPr.code='Q06' ${typeVMPOrNotValueNotNull} then count(mc.id) else '0' end as cnt6
                ,case when vst.code='LABSURVEY' ${typeVMPOrNotValueNotNull} then count(mc.id) else '0' end as cnt7
                from MedService ms
		        left join prescription pr on pr.medservice_id=ms.id
                left join prescriptionlist pl on pr.prescriptionlist_id=pl.id
                left join medcase mc on mc.id=pr.medcase_id
                left join medcase dmc on dmc.id=pl.medcase_id
                 left join workfunction wf on wf.id=pr.prescriptspecial_id
                left join worker w on w.id=wf.worker_id
                left join MisLpu dep on dep.id=pr.department_id or dep.id=w.lpu_id
                left join vocprescripttype pt on pt.id=pr.prescripttype_id
                left join vocservicetype vst on vst.id=ms.servicetype_id
                left join MedService msPr on msPr.id=ms.parent_id
                ${typeVMPOrNotValueLeftJoin}
                where mc.datestart between to_date('${dateBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
                ${deps}
                group by dep.id, mc.emergency,msPr.code,vst.code ${typeVMPOrNotValueGroup}
                order by dep.name
                ) as t
                group by name
"/>
                <form action="javascript:void(0)" method="post" target="_blank"></form>
            </msh:sectionTitle>
            <msh:sectionContent>
                <msh:table printToExcelButton="Сохранить в excel" name="total"
                           viewUrl="reportKDL.do"
                           action="javascript:void(0)" idField="11" cellFunction="true" >
                    <msh:tableColumn columnName="#" property="sn" addParam="&nul=nul" />
                    <msh:tableColumn columnName="Структурные подразделения" property="1" addParam="&nul=nul" />
                    <msh:tableColumn columnName="Гематологические" property="2" isCalcAmount="true" addParam="&nul=nul"/>
                    <msh:tableColumn columnName="Клинические" property="3" isCalcAmount="true" addParam="&nul=nul"/>
                    <msh:tableColumn columnName="Биохимические" property="4" isCalcAmount="true" addParam="&nul=nul"/>
                    <msh:tableColumn columnName="Гемостазиологические" property="5" isCalcAmount="true" addParam="&nul=nul" />
                    <msh:tableColumn columnName="Иммунологические" property="6" isCalcAmount="true" addParam="&nul=nul" />
                    <msh:tableColumn columnName="Микробиологические" property="7" isCalcAmount="true" addParam="&nul=nul" />
                    <msh:tableColumn columnName="ВСЕГО" property="8" isCalcAmount="true" addParam="&nul=nul" />
                </msh:table>
            </msh:sectionContent>
        </msh:section>
        <%
            }
            }
        %>
<script type="text/javascript">
    function find() {
        var frm = document.forms[0];
        frm.submit();
    }
    checkFieldUpdate('typeVMPOrNot','${typeVMPOrNot}',1) ;
    checkFieldUpdate('typeStacOrNot','${typeStacOrNot}',1) ;
    function checkFieldUpdate(aField,aValue,aDefaultValue) {
        eval('var chk =  document.forms[0].'+aField) ;
        var aMax=chk.length ;
        if ((+aValue)==0 || (+aValue)>(+aMax)) {
            chk[+aDefaultValue-1].checked='checked' ;
        } else {
            chk[+aValue-1].checked='checked' ;
        }
    }
</script>
    </tiles:put>
</tiles:insert>
<%
    }
%>