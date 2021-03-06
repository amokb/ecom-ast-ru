<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">
    <tiles:put name="body" type="string">
        <msh:form action="/entitySaveGoView-rvk_aktVisit.do" defaultField="dateStart" editRoles="/Policy/Mis/MedCase/ActRVK/Edit" createRoles="/Policy/Mis/MedCase/ActRVK/Create">
            <msh:panel>
                <msh:hidden property="id"/>
                <msh:hidden property="saveType"/>
                <msh:hidden property="patient"/>
                <msh:hidden property="department"/>
                <msh:hidden property="medCase"/>
                <msh:hidden property="workFunctionStart"/>
                <msh:hidden property="workFunctionFinish"/>
                <msh:hidden property="numAct"/>
                <msh:hidden property="specName"/>
                <msh:ifFormTypeIsCreate formName="rvk_aktVisitForm">
                    <msh:hidden property="dateFinish"/>
                    <msh:hidden property="idc10"/>
                    <msh:hidden property="diagnosis"/>
                    <msh:hidden property="createDate"/>
                    <msh:hidden property="createTime"/>
                    <msh:hidden property="createUsername"/>
                    <msh:hidden property="editDate"/>
                    <msh:hidden property="editTime"/>
                    <msh:hidden property="editUsername"/>
                </msh:ifFormTypeIsCreate>
                <msh:row>
                    <msh:textField property="dateStart" label="Дата начала обследования (лечения)"/>
                </msh:row>
                <msh:row>
                    <msh:textArea property="comment" fieldColSpan="3" horizontalFill="true" label="Замечания"/>
                </msh:row>
                <msh:ifFormTypeAreViewOrEdit formName="rvk_aktVisitForm">
                    <msh:row>
                        <msh:textField property="dateFinish" label="Дата окончания обследования (лечения)"/>
                    </msh:row>
                    <msh:row>
                        <msh:autoComplete property="idc10" vocName="vocIdc10" label="Код МКБ" fieldColSpan="3" horizontalFill="true"/>
                    </msh:row>
                    <msh:row>
                        <msh:textField horizontalFill="true" property="diagnosis" fieldColSpan="3" label="Диагноз"/>
                    </msh:row>
                    <msh:row>
                        <msh:separator label="Дополнительная информация" colSpan="4"/>
                    </msh:row>
                    <msh:row>
                        <msh:label property="createDate" label="Дата создания"/>
                        <msh:label property="createTime" label="время"/>
                    </msh:row>
                    <msh:row>
                        <msh:label property="createUsername" label="пользователь"/>
                        <msh:ifFormTypeIsView formName="rvk_aktVisitForm">
                            <td id="specNameName"><b>Должность:</b></td>
                        </msh:ifFormTypeIsView>
                    </msh:row>
                    <msh:row>
                        <msh:label property="editDate" label="Дата редактирования"/>
                        <msh:label property="editTime" label="время"/>
                    </msh:row>
                    <msh:row>
                        <msh:label property="editUsername" label="пользователь"/>
                    </msh:row>
                </msh:ifFormTypeAreViewOrEdit>
                <msh:submitCancelButtonsRow colSpan="3" />
            </msh:panel>
        </msh:form>
    </tiles:put>
    <tiles:put name="title" type="string">
        <ecom:titleTrail mainMenu="Patient" beginForm="rvk_aktVisitForm"/>
    </tiles:put>
    <tiles:put name="side" type="string">
        <msh:ifFormTypeIsView formName="rvk_aktVisitForm">
            <msh:sideMenu title="Акт РВК">
                <msh:sideLink key="ALT+2" params="id" action="/entityEdit-rvk_aktVisit" name="Изменить" roles="/Policy/Mis/MedCase/ActRVK/Edit" />
                <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDeleteGoParentView-rvk_aktVisit" name="Удалить" roles="/Policy/Mis/MedCase/ActRVK/Delete"/>
            </msh:sideMenu>
        </msh:ifFormTypeIsView>
    </tiles:put>
    <script type="text/javascript" src="./dwr/interface/PatientService.js" >/**/</script>
    <script type="text/javascript">
        <msh:ifFormTypeAreViewOrEdit formName="rvk_aktVisitForm">
        <msh:ifFormTypeIsNotView formName="rvk_aktVisitForm">
        window.onload = function () {
            idc10Autocomplete.addOnChangeCallback(function () {
                setDiagnosisText('idc10', 'diagnosis');
            });
        }
        function setDiagnosisText(aFieldMkb,aFieldText) {
            var val = $(aFieldMkb+'Name').value ;
            var ind = val.indexOf(' ') ;
            if (ind!=-1) {
                if ($(aFieldText).value=="") $(aFieldText).value=val.substring(ind+1) ;
            }
        }
        </msh:ifFormTypeIsNotView>
        <msh:ifFormTypeIsView formName="rvk_aktVisitForm">
        window.onload = function () {
            PatientService.getVocWorkfunctionName($('specName').value, {
                callback: function (res) {
                    $('specNameName').innerHTML += res;
                }
            });
        }
        </msh:ifFormTypeIsView>
        </msh:ifFormTypeAreViewOrEdit>
        <msh:ifFormTypeIsCreate formName="rvk_aktVisitForm">
        window.onload = function () {
            $('dateStart').value=getCurrentDate();
        }
        </msh:ifFormTypeIsCreate>
    </script>
</tiles:insert>