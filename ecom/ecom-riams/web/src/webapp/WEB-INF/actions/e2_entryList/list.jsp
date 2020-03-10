<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib prefix="ecom" uri="http://www.ecom-ast.ru/tags/ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

    <tiles:put name='title' type='string'>
        <msh:title mainMenu="Expert2">Реестры омс</msh:title>
    </tiles:put>

    <tiles:put name='side' type='string'>
        <msh:sideMenu title="Добавить">
            <msh:sideLink key="ALT+2" action="/entityPrepareCreate-e2_entryList" name="Сформировать новое" roles="/Policy/E2/Create" />
        </msh:sideMenu>
        <tags:expertvoc_menu currentAction="main"/>
    </tiles:put>

    <tiles:put name='body' type='string'>
        <msh:hideException>
            <msh:textField label="Номер карты" property="historyNumber"/><input type="button" value="Найти по ИБ" onclick="findByNumber()">
            <ecom:webQuery name="entryList" nativeSql="select id, name, startDate, finishDate, to_char(createDate,'dd.MM.yyyy')||' '|| cast(createTime as varchar(5))
            ,case when isClosed='1' then 'color:blue' else '' end as color
             from e2listentry where isDeleted='0' or isDeleted is null order by finishDate desc, createDate desc,createtime desc "/>
            <msh:section title='Результат поиска'>
                <msh:table  name="entryList" action="entityView-e2_entryList.do" idField="1" disableKeySupport="true" styleRow="6">
                    <msh:tableColumn columnName="Название" property="2" />
                    <msh:tableColumn columnName="Дата начала" identificator="false" property="3" />
                    <msh:tableColumn columnName="Дата окончания" identificator="false" property="4" />
                    <msh:tableColumn columnName="Дата формирования" identificator="false" property="5" />
                </msh:table>
            </msh:section>
        </msh:hideException>
        <script type="text/javascript">
        function findByNumber() {
            var val = jQuery('#historyNumber').val();
            if (val) window.document.location="entityList-e2_entry.do?id=&orderBy=firstNew&filter=historyNumber:"+val;
        }
        let result = '${importResult}';
        if (result) {
            showToastMessage(result);
        }
        </script>
    </tiles:put>
</tiles:insert>