<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh"%>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom"%>

<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >
    <%
        String type = request.getParameter("type");
        request.setAttribute("type",type);
    %>
    <tiles:put name='body' type='string'>
        <msh:form action="entityParentSaveGoView-edkcProtocol.do" defaultField="dateRegistration" guid="b55hb-b971-441e-9a90-5155c07"
                  fileTransferSupports="true">
            <msh:hidden property="id" />
            <msh:hidden property="saveType" />
            <msh:hidden property="username" />
            <msh:hidden property="date" />
            <msh:hidden property="time" />
            <msh:hidden property="editUsername" />
            <msh:hidden property="editDate" />
            <msh:hidden property="editTime" />
            <msh:hidden property="specialist" />
            <msh:hidden property="obsSheet" />
            <msh:hidden property="templateProtocol" />
            <msh:hidden property="params" />
            <msh:panel>
                <msh:row>
                    <msh:textField label="Дата" property="dateRegistration" fieldColSpan="1"/>
                    <msh:textField label="Время" property="timeRegistration" fieldColSpan="1"/>
                </msh:row>
                <msh:row>
                    <msh:autoComplete property="type" fieldColSpan="3" viewOnlyField="true"
                                      label="Тип протокола" horizontalFill="true"
                                      vocName="vocTypeProtocol"  />
                </msh:row>
                <msh:row>
                    <msh:ifFormTypeIsNotView formName="edkcProtocolForm">
                        <td colspan="3" align="center">
                        <input type="button" name="btnEditProt2" id="btnEditProt2"
                               value="Редактировать параметры" onClick="showTemplateForm($('templateProtocol').value);" />
                        <div name="btnEditProt1" id="btnEditProt1"></div>
                    </msh:ifFormTypeIsNotView>
                    </td>
                </msh:row>
                <msh:row>
                    <msh:textArea property="record" label="Текст:" size="100" rows="25"
                                  fieldColSpan="8" guid="b6ehb-b971-441e-9a90-519c07"/>

                </msh:row>
                <msh:ifFormTypeIsView formName="edkcProtocolForm">

                    <msh:row>
                        <msh:textField property="date" label="Дата создания"
                                       viewOnlyField="true" />
                        <msh:textField property="time" label="Время" viewOnlyField="true" />
                        <msh:textField property="username" label="Пользователь"
                                       viewOnlyField="true" />
                    </msh:row>
                    <msh:row>
                        <msh:label property="editDate" label="Дата редак." />
                        <msh:label property="editTime" label="Время редак." />
                        <msh:label property="editUsername" label="Пользователь" />
                    </msh:row>
                </msh:ifFormTypeIsView>
                <msh:row>
                    <msh:submitCancelButtonsRow colSpan="3" />
                </msh:row>
            </msh:panel>
        </msh:form>
        <tags:templateProtocol  property="record" name="tmp" voc="protocolVisitByPatient"/>
    </tiles:put>
    <tiles:put name='side' type='string'>
        <msh:sideMenu>
            <msh:ifFormTypeIsView formName="edkcProtocolForm">
                <tags:mis_protocolTemplateDocumentList name="Print" />
                <msh:sideLink roles="/Policy/Mis/MedCase/Protocol/Edit" key="ALT+2"
                              params="id" action="/entityParentEdit-edkcProtocol"
                              name="Редактировать" />

                <msh:sideLink roles="/Policy/Mis/MedCase/Protocol/Delete"
                              key='ALT+DEL' params="id"
                              action="/entityParentDeleteGoParentView-edkcProtocol"
                              name="Удалить" confirm="Вы действительно хотите удалить?" />
            </msh:ifFormTypeIsView>
        </msh:sideMenu>
    </tiles:put>
    <tiles:put name='title' type='string'>
        <ecom:titleTrail mainMenu="Patient" beginForm="edkcProtocolForm"/>
    </tiles:put>
    <tiles:put name='javascript' type='string'>
        <script type="text/javascript" src="./dwr/interface/PatientService.js">/**/</script>
        <msh:ifFormTypeIsCreate formName="edkcProtocolForm">
            <script type="text/javascript">
                window.onload = function() {
                    if ('${type}'!='') {
                        PatientService.getNameTypeProtocol('${type}',{
                            callback: function (res) {
                                if (res != null && res != '[]') {
                                    var Result = JSON.parse(res);
                                    if (typeof(Result.id) !=='undefined') $('type').value = Result.id;
                                    if (typeof(Result.name) !=='undefined') $('typeReadOnly').value = Result.name;
                                    if (typeof(Result.tmpl) !=='undefined') $('templateProtocol').value = Result.tmpl;
                                    if ($('templateProtocol').value!='0')
                                        showTemplateForm($('templateProtocol').value);
                                    else
                                        showToastMessage('Не найден шаблон для ЕДКЦ!',null,true);
                                }
                            }
                        });
                    }
                }
            </script>
        </msh:ifFormTypeIsCreate>
    </tiles:put>
</tiles:insert>