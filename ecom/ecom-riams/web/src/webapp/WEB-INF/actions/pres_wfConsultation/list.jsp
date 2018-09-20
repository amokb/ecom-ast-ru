<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

    <tiles:put name="title" type="string">
        <ecom:titleTrail beginForm="pres_prescriptListForm" mainMenu="Patient" title="Консультация" guid="610fe86e-69f6-4ad0-a1dd-146453" />
    </tiles:put>
    <tiles:put name="side" type="string">
        <msh:sideMenu title="Добавить" guid="helloSideMenu-123">
            <msh:sideLink params="id" action="/entityParentPrepareCreate-pres_wfConsultation" name="Консультация специалиста" title="Добавить консультацию специалиста" guid="2209b5f9-4b4f-4ed5-b825-b66f2ac57e87" roles="/Policy/Mis/Prescription/ServicePrescription/Create" key="ALT+N" />
        </msh:sideMenu>
    </tiles:put>
    <tiles:put name="body" type="string">
        <msh:section title="Консультации специалистов. 
        <a href='entityParentPrepareCreate-pres_wfConsultation.do?id=${param.id }'> Добавить новую консультацию специалиста</a>
        " guid="1f21294-8ea0-4b66-a0f3-62713c1">
            <ecom:webQuery name="consultations"  nativeSql="
     select scg.id,vtype.code||' '||vtype.name as f00,vwf.name||' '||wp.lastname||' '||wp.firstname||' '||wp.middlename as f01,scg.createusername as f1,scg.createdate as f2,scg.editusername as f3,scg.editdate as f4, scg.transferusername as f5,scg.transferdate as f6
from prescription scg
left join PrescriptionList pl on pl.id=scg.prescriptionList_id
left join workfunction wf on wf.id=scg.prescriptcabinet_id
left join vocworkFunction vwf on vwf.id=wf.workFunction_id
left join worker w on w.id = wf.worker_id
left join patient wp on wp.id=w.person_id
left join vocconsultingtype vtype on vtype.id=scg.vocconsultingtype_id
where scg.dtype='WfConsultation' and scg.prescriptionlist_id='${param.id}'"/>

            <msh:table hideTitle="false" idField="1" name="consultations" action="entityParentView-pres_wfConsultation.do" guid="d0267-9aec-4ee0-b20a-4f26b37">
                <msh:tableColumn columnName="#" property="sn"/>
                <msh:tableColumn columnName="Тип" property="2"/>
                <msh:tableColumn columnName="Специалист" property="3"/>
                <msh:tableColumn columnName="Пользователь, который создал" property="4" cssClass="preCell"/>
                <msh:tableColumn columnName="Дата создания" property="5"/>
                <msh:tableColumn columnName="Пользователь, который отредактировал" property="6" cssClass="preCell"/>
                <msh:tableColumn columnName="Дата редактирования" property="7"/>
                <msh:tableColumn columnName="Пользователь, который передал" property="8" cssClass="preCell"/>
                <msh:tableColumn columnName="Дата передачи" property="9"/>
            </msh:table>

        </msh:section>
    </tiles:put>
</tiles:insert>