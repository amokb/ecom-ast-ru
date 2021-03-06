<%@ page import="ru.ecom.web.util.ActionUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true">

    <tiles:put name='title' type='string'>
        <msh:title mainMenu="StacJournal">Свод анализов ПЦР SARV-COV2</msh:title>
    </tiles:put>
    <tiles:put name='side' type='string'>
    </tiles:put>
    <tiles:put name="body" type="string">
        <%
            String typeDate = ActionUtil.updateParameter("typeDate", "typeDate", "1", request);
            String typeType = ActionUtil.updateParameter("typeType", "typeType", "1", request);

            String department = request.getParameter("department");
            if (department != null && !department.equals(""))
                request.setAttribute("department", " and dep.id=" + department);

            String date = request.getParameter("beginDate"), endDate = "";
            if (date != null && !date.equals("")) {
                endDate = request.getParameter("endDate");

                if (endDate == null || endDate.equals("")) {
                    endDate = date;
                }
            }
            request.setAttribute("beginDate", date);
            request.setAttribute("endDate", endDate);

            String dateTo = "";
            if (typeDate.equals("1")) {
                dateTo = "p.intakeDate";
            } else if (typeDate.equals("2")) {
                dateTo = "p.planStartDate";
            } else if (typeDate.equals("3")) {
                dateTo = "mc.datestart";
            }

            request.setAttribute("dateTo", dateTo);
            request.setAttribute("typeDate", typeDate);
            if (request.getParameter("short") == null) {
        %>
        <msh:form action="/journal_svodPCR.do" defaultField="beginDate" method="GET">
            <msh:panel>
                <msh:row>
                    <msh:separator label="Параметры поиска" colSpan="7"/>
                </msh:row>
                <msh:row>
                    <msh:textField property="beginDate" label="Дата"/>
                    <msh:textField property="endDate" label="по"/>
                </msh:row>
                <msh:row>
                    <msh:autoComplete property="department" fieldColSpan="16" horizontalFill="true" label="Отделение"
                                      vocName="vocLpuHospOtdAll"/>
                </msh:row>
                <msh:row>
                    <td class="label" title="Поиск по  (typeDate)" colspan="1"><label for="typeDateName"
                                                                                      id="typeDateLabel">Искать по
                        дате:</label></td>
                    <td onclick="this.childNodes[1].checked='checked';" colspan="2">
                        <input type="radio" name="typeDate" value="1"> по дате забора
                    </td>
                    <td onclick="this.childNodes[1].checked='checked';" colspan="3">
                        <input type="radio" name="typeDate" value="2"> по дате направления
                    </td>
                    <td onclick="this.childNodes[1].checked='checked';" colspan="3">
                        <input type="radio" name="typeDate" value="3"> по дате результата
                    </td>
                </msh:row>
                <msh:row>
                    <td class="label" title="Поиск по  (typeType)" colspan="1"><label for="typeTypeName"
                                                                                      id="typeTypeLabel">Группировать:</label>
                    </td>
                    <td onclick="this.childNodes[1].checked='checked';" colspan="2">
                        <input type="radio" name="typeType" value="1"> по отделению
                    </td>
                    <td onclick="this.childNodes[1].checked='checked';" colspan="3">
                        <input type="radio" name="typeType" value="2"> общий свод
                    </td>
                </msh:row>
                <msh:row>
                    <td>
                        <input type="submit" value="Найти"/>
                    </td>
                </msh:row>
            </msh:panel>
        </msh:form>
        <%
            }
            if (request.getParameter("beginDate") != null && !request.getParameter("beginDate").equals("")) {
                if (request.getParameter("short") == null) {
        %>
        <msh:section>
            <a id='noteH' href="#bottom" onclick="showNote();">Показать примечание</a><br>
            <div id="note" style="display:none;">
                <u>Печать реестра</u>
                <p>указать только <i>дату</i></p>
                <p>поиск по <i>дате направления</i> (не период, просто первая дата)</p>
                <p>передача в лабораторию <i>не была произведена</i></p>
                <p>передача в лабораторию <i>не была произведена</i></p>
                <p>вывод только <i>не отбракованных</i></p>
            </div>
            <form id="printForm" name="printForm" action="print-print-pres_lab_pcrCovid_by_department.do" method="post"
                  target="_blank">
                <input type='hidden' name="sqlText" id="sqlText" value="">
                <input type='hidden' name="sqlColumn" id="sqlColumn" value="${groupName}">
                <input type='hidden' name="s" id="s" value="PrintService">
                <input type='hidden' name="m" id="m" value="printGroupColumnNativeQuery">
                <input type='hidden' name="groupField" id="groupField" value="3">
                <input type='hidden' name="cntColumn" id="cntColumn" value="1">
                <input type='hidden' name="planStartDate" id="planStartDate" value="${beginDate}">
                <input type="button" value="Реестр приёма-передачи (инф. отд.)"
                       onclick="printPresLabCovidReestr(1,0,1)">
                <input type="button" value="Реестр направленных (инф. отд.)" onclick="printPresLabCovidReestr(0,0,1)">
                <input type="button" value="Реестр приёма-передачи (неинф. отд.)"
                       onclick="printPresLabCovidReestr(1,1,1)">
                <input type="button" value="Реестр направленных (неинф. отд.)" onclick="printPresLabCovidReestr(0,1,1)">
                <input type="button" value="Реестр приёма-передачи (поликлиника)"
                       onclick="printPresLabCovidReestr(1,1,0)">
                <input type="button" value="Реестр направленных (поликлиника)" onclick="printPresLabCovidReestr(0,1,0)">
                <script type="text/javascript">
                    function printPresLabCovidReestr(withoutIntake, isInf, isStac) {
                        $('sqlText').value = " select p.materialPCRId as f1material" +
                            " ,pat.patientinfo as f2patInfo" +
                            " ,to_char(p.intakeDate,'dd.mm.yyyy') as f3dtintake" +
                            " ,coalesce(dep.name,'Поликлиника') as f4depName" +
                            " ,p.materialPCRId as f5countTable" +
                            " from prescription p" +
                            " left join PrescriptionList pl on pl.id=p.prescriptionList_id" +
                            " left join MedCase mc on mc.id=pl.medCase_id" +
                            " left join Patient pat on pat.id=mc.patient_id" +
                            " left join MedService ms on ms.id=p.medService_id" +
                            " left join VocServiceType vst on vst.id=ms.serviceType_id" +
                            " left join MisLpu dep on dep.id=mc.department_id" +
                            " left join VocServiceSubType vsst on vsst.id=ms.serviceSubType_id" +
                            " where p.dtype='ServicePrescription'  " +
                            " and p.planStartDate=to_date('" + $('beginDate').value + "','dd.mm.yyyy')  " +
                            " and vst.code='LABSURVEY'  " +
                            " and p.cancelDate is null ${sqlAdd}";
                        $('sqlText').value += +withoutIntake == 1 ? " and p.intakeDate is not null" : "";
                        $('sqlText').value += +isInf == 1 ? " and (dep.isforcovid is null or dep.isforcovid='0')" : " and dep.isforcovid='1'";
                        $('sqlText').value += +isStac == 1 ? " and mc.dtype='DepartmentMedCase'" : " and mc.dtype='Visit'";
                        $('sqlText').value += " and vsst.code='COVID' " +
                            " group by dep.name,p.materialPCRId,pat.id,p.intakeDate" +
                            " order by dep.name,pat.patientinfo";
                        document.getElementById('printForm').action = 'print-pres_lab_pcrCovid_by_department.do';
                        document.getElementById('printForm').submit();
                    }
                </script>
            </form>
        </msh:section>
        <msh:section>
            <msh:sectionTitle>ПОЛИКЛИНИКА за период с ${beginDate} по ${endDate}.</msh:sectionTitle>
            <msh:sectionContent>
                <ecom:webQuery name="journal_svodPCRPat_pol" nativeSql="
                            select vis.id as f1vis,pat.patientinfo as f2patinfo , getprescPcrInfo(p.id) as f3pinfo ,p.id as f4exactccnt ,p.materialPCRid as f5num
                            from prescription p
                            left join MedService ms on ms.id=p.medService_id
                            left join VocServiceSubType vsst on vsst.id=ms.serviceSubType_id
                            left join MedCase mc on mc.id=p.medcase_id
                            left join Diary d on d.medcase_id=mc.id
                            left join templateprotocol t2 on t2.id=d.templateprotocol
                            left join forminputprotocol fiprRes on fiprRes.docprotocol_id=d.id and fiprRes.parameter_id=1284
                            left join PrescriptionList pl on pl.id=p.prescriptionList_id
                            left join MedCase vis on vis.id=pl.medCase_id
                            left join Patient pat on vis.patient_id = pat.id
                            left join WorkFunction wf on wf.id=p.prescriptSpecial_id
                            left join Worker w on w.id=wf.worker_id
                            left join MisLpu ml on ml.id=w.lpu_id
                            where ${dateTo} between to_date('${beginDate}','dd.mm.yyyy') and to_date('${endDate}','dd.mm.yyyy')
                            and vsst.code='COVID' and vis.dtype='Visit'
                            and ml.id not in (1,391)
                            group by vis.id,pat.patientinfo,p.id order by pat.patientinfo"/>
                <msh:table printToExcelButton="Сохранить в Excel" name="journal_svodPCRPat_pol"
                           noDataMessage="Нет данных"
                           action="entityParentView-smo_visit.do" idField="1" openNewWindow="true">
                    <msh:tableColumn property="sn" columnName="#"/>
                    <msh:tableColumn property="2" columnName="Пациент"/>
                    <msh:tableColumn property="5" columnName="Номер пробирки"/>
                    <msh:tableColumn property="3" columnName="Информация о назначении"/>
                </msh:table>
            </msh:sectionContent>
        </msh:section>
        <%
            }
            if (request.getParameter("short") == null && "1".equals(typeType)) {
        %>
        <msh:section>
            <msh:sectionTitle>СТАЦИОНАР за период с ${beginDate} по ${endDate}.</msh:sectionTitle>
            <msh:sectionContent>
                <ecom:webQuery name="journal_svodPCR" nativeSql="
                select
                dep.name as f1dep
                ,count(p.id) as f2total
                ,sum(case when p.intakedate is not null then 1 else 0 end) as f3intake
                ,sum(case when p.transferdate is not null then 1 else 0 end) as f4transfer
                ,sum(case when mc.datestart is not null then 1 else 0 end) as f5res
                ,sum(case when fiprRes.valuevoc_id=70 then 1 else 0 end) as f6pol
                ,sum(case when fiprRes.valuevoc_id=69 then 1 else 0 end) as f7neg
                ,sum(case when p.canceldate is not null then 1 else 0 end) as f8cancel
                ,'&depId='||coalesce(dep.id,0)||'&depname='||coalesce(dep.name,'') as f9str
                from prescription p
                left join MedService ms on ms.id=p.medService_id
                left join VocServiceSubType vsst on vsst.id=ms.serviceSubType_id
                left join MedCase mc on mc.id=p.medcase_id
                left join Diary d on d.medcase_id=mc.id
                left join templateprotocol t2 on t2.id=d.templateprotocol
                left join forminputprotocol fiprRes on fiprRes.docprotocol_id=d.id and fiprRes.parameter_id=1284
                left join PrescriptionList pl on pl.id=p.prescriptionList_id
                left join MedCase slo on slo.id=pl.medCase_id
                left join MisLpu dep on dep.id=slo.department_id
                left join WorkFunction wf on wf.id=p.prescriptSpecial_id
			    left join Worker w on w.id=wf.worker_id
			    left join MisLpu ml on ml.id=w.lpu_id
                where ${dateTo} between to_date('${beginDate}','dd.mm.yyyy') and to_date('${endDate}','dd.mm.yyyy')
                and vsst.code='COVID'
           		${department}
           		and ml.id not in (1,391) and slo.dtype='DepartmentMedCase'
                group by dep.id,dep.name
                order by dep.name
                "/>
                <msh:table printToExcelButton="Сохранить в Excel" name="journal_svodPCR" noDataMessage="Нет данных"
                           action="journal_svodPCR.do?&short=Short&typeType=${param.typeType}&beginDate=${param.beginDate}&endDate=${param.endDate}"
                           idField="9" cellFunction="true">
                    <msh:tableColumn property="sn" columnName="#"/>
                    <msh:tableColumn property="1" columnName="Подразделение" addParam="&type=total"/>
                    <msh:tableColumn property="2" isCalcAmount="true" columnName="Всего назначений"
                                     addParam="&type=total"/>
                    <msh:tableColumn property="3" isCalcAmount="true" columnName="Забор" addParam="&type=intake"/>
                    <msh:tableColumn property="4" isCalcAmount="true" columnName="Передача" addParam="&type=transfer"/>
                    <msh:tableColumn property="5" isCalcAmount="true" columnName="Результат" addParam="&type=res"/>
                    <msh:tableColumn property="6" isCalcAmount="true" columnName="Положительно" addParam="&type=plus"/>
                    <msh:tableColumn property="7" isCalcAmount="true" columnName="Отрицательно" addParam="&type=minus"/>
                    <msh:tableColumn property="8" isCalcAmount="true" columnName="Брак" addParam="&type=brak"/>
                </msh:table>
            </msh:sectionContent>
        </msh:section>
        <% } else {
            String type = request.getParameter("type");
            String sqlAdd = "";
            if (type != null) {
                if ("intake".equals(type))
                    sqlAdd = " and p.intakedate is not null";
                else if ("transfer".equals(type))
                    sqlAdd = " and p.transferdate is not null";
                else if ("res".equals(type))
                    sqlAdd = " and mc.datestart is not null";
                else if ("plus".equals(type))
                    sqlAdd = " and fiprRes.valuevoc_id=70";
                else if ("minus".equals(type))
                    sqlAdd = " and fiprRes.valuevoc_id=69";
                else if ("brak".equals(type))
                    sqlAdd = " and p.canceldate is not null";
            }
            request.setAttribute("sqlAdd", sqlAdd);
            String depId = request.getParameter("depId");
            String depSql = depId != null ?
                    "and dep.id = " + depId : "";
            request.setAttribute("depSql", depSql);
        %>
        <msh:section>
            <msh:sectionTitle>Результаты поиска за период с ${beginDate} по ${endDate}.</msh:sectionTitle>
        </msh:section>
        <msh:section>
            <msh:sectionContent>
                <ecom:webQuery name="journal_svodPCRPat" nativeSql="
                select sls.id f1sls,dep.name as f2depname, st.code as f3stc,pat.patientinfo as f4patinfo
                , getprescPcrInfo(p.id) as f5pinfo
                ,p.id as f6exactccnt
                ,p.materialPCRid as f7num
                 ,max(cov.epidnumber) as f8cepid
                  from prescription p
                left join MedService ms on ms.id=p.medService_id
                left join VocServiceSubType vsst on vsst.id=ms.serviceSubType_id
                left join MedCase mc on mc.id=p.medcase_id
                left join Diary d on d.medcase_id=mc.id
                left join templateprotocol t2 on t2.id=d.templateprotocol
                left join forminputprotocol fiprRes on fiprRes.docprotocol_id=d.id and fiprRes.parameter_id=1284
                left join PrescriptionList pl on pl.id=p.prescriptionList_id
                left join MedCase slo on slo.id=pl.medCase_id
                left join MisLpu dep on dep.id=slo.department_id
                left join Patient pat on slo.patient_id = pat.id
                left join MedCase sls on sls.id = slo.parent_id
			    left join covid19 cov on cov.medcase_id=sls.id and cov.epidnumber is not null
                left join statisticstub st on st.medcase_id=sls.id
                left join WorkFunction wf on wf.id=p.prescriptSpecial_id
			    left join Worker w on w.id=wf.worker_id
			    left join MisLpu ml on ml.id=w.lpu_id
                where ${dateTo} between to_date('${beginDate}','dd.mm.yyyy') and to_date('${endDate}','dd.mm.yyyy')
                and vsst.code='COVID'
                ${sqlAdd}
                ${depSql}
           		${department}
           		and ml.id not in (1,391)
           		group by sls.id,dep.name, st.code,pat.patientinfo,p.id
                order by dep.name,pat.patientinfo"/>
                <msh:table printToExcelButton="Сохранить в Excel" name="journal_svodPCRPat" noDataMessage="Нет данных"
                           action="entityParentView-stac_ssl.do" idField="1" openNewWindow="true">
                    <msh:tableColumn property="sn" columnName="#"/>
                    <msh:tableColumn property="2" columnName="Отделение"/>
                    <msh:tableColumn property="3" columnName="ИБ"/>
                    <msh:tableColumn property="4" columnName="Пациент"/>
                    <msh:tableColumn property="7" columnName="Номер пробирки"/>
                    <msh:tableColumn property="5" columnName="Информация о назначении"/>
                    <msh:tableColumn property="8" columnName="Эпид. номер"/>
                </msh:table>
            </msh:sectionContent>
        </msh:section>
        <%
                }
            }
        %>
        <script type='text/javascript'>
            checkFieldUpdate('typeDate', '${typeDate}', 1);
            checkFieldUpdate('typeType', '${typeType}', 1);

            function checkFieldUpdate(aField, aValue, aDefaultValue) {
                eval('var chk =  document.forms[0].' + aField);
                var aMax = chk.length;
                if ((+aValue) == 0 || (+aValue) > (+aMax)) {
                    chk[+aDefaultValue - 1].checked = 'checked';
                } else {
                    chk[+aValue - 1].checked = 'checked';
                }
            }

            //показать/скрыть примечание
            function showNote() {
                var note = document.getElementById("note");
                var href = document.getElementById("noteH");
                if (note.style.display == "none") {
                    note.style.display = "block";
                    jQuery(href).text("Скрыть примечание");
                } else {
                    note.style.display = "none";
                    jQuery(href).text("Показать примечание");
                }
            }
        </script>
    </tiles:put>
</tiles:insert>