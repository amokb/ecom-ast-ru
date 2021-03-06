<%@ page import="ru.ecom.web.util.ActionUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true" >

    <tiles:put name='title' type='string'>
        <msh:title mainMenu="StacJournal">Поиск госпитализаций, в которых нет <u><i>формы оценки тяжести COVID-19</i></u></msh:title>
    </tiles:put>
    <tiles:put name='side' type='string'>
    </tiles:put>
    <tiles:put name="body" type="string">
        <%
            String typeType = ActionUtil.updateParameter("typeType","typeType","2", request) ;
            String department = request.getParameter("department") ;
            if (department!=null && !department.equals("")) request.setAttribute("department"," and dep.id="+department);
            String filterAdd = request.getParameter("filterAdd") ;
            if (filterAdd!=null && !filterAdd.equals("")) request.setAttribute("filterAdd"," and vs.id="+filterAdd);
            String date = request.getParameter("dateBegin"), dateEnd="";
            if (date!=null && !date.equals("")) {
                dateEnd = request.getParameter("dateEnd");

                if (dateEnd == null || dateEnd.equals("")) {
                    dateEnd = date;
                }
            }
            request.setAttribute("dateBegin",date) ;
            request.setAttribute("dateEnd", dateEnd) ;
            if (request.getParameter("short")==null) {
        %>
        <msh:form action="/journal_covidForm.do" defaultField="department" method="GET">
            <msh:panel>
                <msh:row>
                    <msh:autoComplete property="department" fieldColSpan="16" horizontalFill="true" label="Отделение" vocName="vocLpuHospOtdAll"/>
                </msh:row>
                <msh:row>
                    <msh:autoComplete property="filterAdd" fieldColSpan="16" horizontalFill="true" label="Степень тяжести" vocName="vocCovidSost"/>
                </msh:row>
                <msh:row>
                    <msh:separator label="Параметры поиска" colSpan="7" />
                </msh:row>
                <msh:row>
                    <msh:textField property="dateBegin" label="Период с" />
                    <msh:textField property="dateEnd" label="по" />
                </msh:row>
                <msh:row>
                    <td class="label" title="Поиск по  (typeType)" colspan="1"><label for="typeTypeName" id="typeTypeLabel">Группировать:</label></td>
                    <td onclick="this.childNodes[1].checked='checked';" colspan="2">
                        <input type="radio" name="typeType" value="1">  реестр пациентов
                    </td>
                    <td onclick="this.childNodes[1].checked='checked';" colspan="3">
                        <input type="radio" name="typeType" value="2">  свод по отделениям
                    </td>
                </msh:row>
                <msh:row>
                    <td>
                        <input type="submit" value="Найти" />
                    </td>
                </msh:row>
            </msh:panel>
        </msh:form>
        <%
            }
            if (request.getParameter("dateBegin")!=null &&  !request.getParameter("dateBegin").equals("")) {
                if ("2".equals(typeType) && request.getParameter("short")==null) {

        %>
        <msh:section>
            <msh:sectionTitle>Результаты поиска за период с ${dateBegin} по ${dateEnd}.</msh:sectionTitle>
        </msh:section>
        <msh:section>
            <msh:sectionContent>
                <ecom:webQuery name="journal_covidForm" nativeSql="
                select distinct t.depname as depname
                ,sum(t.total) as total
                ,sum(t.cntCard) as cntCard
                ,sum(t.cntNotCard) as cntNotCard
                ,t.str as str from (
                select dep.name as depname, count (distinct sls.id) as total
                ,(select count(distinct slsinner.id)  from medCase m
                left join MedCase as slsinner on slsinner.id = m.parent_id
                left join CovidMark c on slsinner.id=c.medcase_id
                where slsinner.id=sls.id
                and c.id is not null
                ) as cntCard
                ,(select count(distinct slsinner.id)  from medCase m
                left join MedCase as slsinner on slsinner.id = m.parent_id
                left join CovidMark c on slsinner.id=c.medcase_id
                where slsinner.id=sls.id
                and c.id is null
                ) as cntNotCard
                ,'&depId='||coalesce(dep.id,0)||'&depname='||coalesce(dep.name,'') as str
                from medCase sls
                left join CovidMark c on sls.id=c.medcase_id
                left join MedCase sloa on sloa.parent_id=sls.id
                left join Medcase prevmc on prevmc.id=sloa.prevmedcase_id
                left join mislpu dep on case when sloa.department_id=501 then dep.id=prevmc.department_id else dep.id=sloa.department_id end
                left join MedCase as m on sls.id = m.parent_id
                left join diagnosis diag on diag.medcase_id=sls.id or diag.medcase_id=m.id
                left join vocidc10 mkb on mkb.id=diag.idc10_id
                left join VocDiagnosisRegistrationType vdrt on vdrt.id=diag.registrationType_id
                left join VocPriorityDiagnosis vpd on vpd.id=diag.priority_id
                left join ReportSetTYpeParameterType rspt on mkb.code between rspt.codefrom and rspt.codeto
                left join VocReportSetParameterType vrspt on rspt.parameterType_id=vrspt.id
                left join ReportSetTYpeParameterType rspt1 on mkb.code between rspt1.codefrom and rspt1.codeto
                left join VocReportSetParameterType vrspt1 on rspt1.parameterType_id=vrspt1.id
                left join vocsost vs on vs.id=c.sost_id
                where m.DTYPE='DepartmentMedCase'
                and vrspt.id='523' and vdrt.id='3' and vpd.id='1'
                and sls.datefinish between to_date('${dateBegin}','dd.mm.yyyy')  and to_date('${dateEnd}','dd.mm.yyyy')
           		${department}
           		${filterAdd}
           		and sloa.datefinish is not null and sloa.DTYPE='DepartmentMedCase'
                group by dep.id,dep.name, sls.id
                order by dep.name) as t
                group by t.depname,t.str
                order by t.depname" />
                <msh:table printToExcelButton="Сохранить в Excel" name="journal_covidForm"  noDataMessage="Нет данных"
                           action="journal_covidForm.do?&short=Short&typeType=1&dateBegin=${param.dateBegin}&dateEnd=${param.dateEnd}&filterAdd=${param.filterAdd}"
                           idField="5" cellFunction="true">
                    <msh:tableColumn property="sn" columnName="#" />
                    <msh:tableColumn property="1" columnName="Отделение" addParam="&type=total"/>
                    <msh:tableColumn property="2" isCalcAmount="true" columnName="Всего пациентов" addParam="&type=total"/>
                    <msh:tableColumn property="3" isCalcAmount="true" columnName="Форм создано" addParam="&type=create"/>
                    <msh:tableColumn property="4" isCalcAmount="true" columnName="Форм не создано" addParam="&type=notCreate"/>
                </msh:table>
            </msh:sectionContent>
        </msh:section>
        <%    }
        else if (request.getParameter("short")==null || "1".equals(typeType)) {
            String type = request.getParameter("type");
            String sqlAdd="";
            if (type!=null || "1".equals(typeType)) {
                if ("create".equals(type))
                    sqlAdd = " and c.id is not null";
                else if ("notCreate".equals(type))
                    sqlAdd = " and c.id is null";
                request.setAttribute("sqlAdd",sqlAdd);
                String depId = request.getParameter("depId");
                String depSql = depId!=null?
                        "and dep.id = " + depId : "";
                    request.setAttribute("depSql",depSql);
        %>
        <msh:section>
            <msh:sectionTitle>Результаты поиска за период с ${dateBegin} по ${dateEnd}.</msh:sectionTitle>
        </msh:section>
        <msh:section>
            <msh:sectionContent>
                <ecom:webQuery name="journal_covidFormPat" nativeSql="
                select distinct sls.id,dep.name as depname, st.code as stc,pat.patientinfo as info
                , vs.name as vsname, vhr.name as vhrname
                ,getChosenCovidFormPars(c.id) as pars
                 from medCase m
                left join MedCase as sls on sls.id = m.parent_id
                left join Patient pat on m.patient_id = pat.id
                left join CovidMark c on sls.id=c.medcase_id
                left join MedCase sloa on sloa.parent_id=sls.id
                left join Medcase prevmc on prevmc.id=sloa.prevmedcase_id
                left join mislpu dep on case when sloa.department_id=501 then dep.id=prevmc.department_id else dep.id=sloa.department_id end
                left join diagnosis diag on diag.medcase_id=sls.id or diag.medcase_id=m.id
                left join vocidc10 mkb on mkb.id=diag.idc10_id
                left join VocDiagnosisRegistrationType vdrt on vdrt.id=diag.registrationType_id
                left join VocPriorityDiagnosis vpd on vpd.id=diag.priority_id
                left join ReportSetTYpeParameterType rspt on mkb.code between rspt.codefrom and rspt.codeto
                left join VocReportSetParameterType vrspt on rspt.parameterType_id=vrspt.id
                left join ReportSetTYpeParameterType rspt1 on mkb.code between rspt1.codefrom and rspt1.codeto
                left join VocReportSetParameterType vrspt1 on rspt1.parameterType_id=vrspt1.id
                left join statisticstub st on st.medcase_id=sls.id
                left join vocsost vs on vs.id=c.sost_id
                left join vochospitalizationresult vhr on vhr.id=sls.result_id
                where m.DTYPE='DepartmentMedCase'
                and vrspt.id='523' and vdrt.id='3' and vpd.id='1'
                and sls.datefinish between to_date('${dateBegin}','dd.mm.yyyy')  and to_date('${dateEnd}','dd.mm.yyyy')
                ${department}
                ${filterAdd}
                ${sqlAdd}
                ${depSql}
                and sloa.datefinish is not null and sloa.DTYPE='DepartmentMedCase'
                group by sls.id,dep.name, st.code,pat.patientinfo, vs.name, mkb.code, vhr.name, c.id
                order by dep.name,pat.patientinfo" />
                <msh:table printToExcelButton="Сохранить в Excel" name="journal_covidFormPat"  noDataMessage="Нет данных"
                           action="entityParentView-stac_ssl.do" idField="1" openNewWindow="true">
                    <msh:tableColumn property="sn" columnName="#" />
                    <msh:tableColumn property="4" columnName="Пациент"/>
                    <msh:tableColumn property="3" columnName="ИБ"/>
                    <msh:tableColumn property="2" columnName="Отделение"/>
                    <msh:tableColumn property="5" columnName="Степень тяжести COVID-19"/>
                    <msh:tableColumn property="6" columnName="Результат госпитализации"/>
                    <msh:tableColumn property="7" columnName="Выбранные параметры"/>
                </msh:table>
            </msh:sectionContent>
        </msh:section>
        <%} } }%>
        <script type='text/javascript'>
            checkFieldUpdate('typeType','${typeType}',2) ;
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