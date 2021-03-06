<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib uri="/WEB-INF/mis.tld" prefix="mis" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

    <tiles:put name="title" type="string">
        <ecom:titleTrail beginForm="mis_medCaseForm" mainMenu="Patient" title="Температурные листы"/>
    </tiles:put>
    <tiles:put name="side" type="string">
        <tags:temperatureCurve name="New"/>
        <msh:sideMenu title="Добавить">
            <msh:sideLink params="" action="/javascript:showNewCurve(${param.id})"
                          name="Новые показатели температурного листа"
                          title="Добавить новые показатели температурного листа"
                          roles="/Policy/Mis/MedCase/Stac/Ssl/TemperatureCurve/Create"/>
        </msh:sideMenu>
        <msh:sideMenu title="Перейти">
            <msh:sideLink params="id" action="/js-stac_temperatureCurve-graph" name="График температурного листа"
                          title="Показать график температурного листа"
                          roles="/Policy/Mis/MedCase/Stac/Ssl/TemperatureCurve/Graph"/>
        </msh:sideMenu>
    </tiles:put>
    <tiles:put name="body" type="string">
        <msh:table name="list" action="entityParentView-stac_temperatureCurve.do" idField="id">
            <msh:tableColumn columnName="Дата" property="takingDate"/>
            <msh:tableColumn columnName="Время суток" property="dayTimeText"/>
            <msh:tableColumn columnName="Температура" property="degree"/>
        </msh:table>
    </tiles:put>
    <script type="text/javascript" src="./dwr/interface/HospitalMedCaseService.js">/**/</script>
</tiles:insert>