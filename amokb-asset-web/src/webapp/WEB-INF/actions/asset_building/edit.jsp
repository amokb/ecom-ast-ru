<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">
	<tiles:put name="body" type="string">
		<msh:form action="/entityParentSaveGoView-asset_building.do" defaultField="name">
			<msh:hidden property="id" />
			<msh:hidden property="saveType" />
			<msh:hidden property="territory" />
			<msh:hidden property="lpu" />
			<msh:panel>
				<msh:row>
					<msh:textField property="name" label="Название" fieldColSpan="3" horizontalFill="true"/>
				</msh:row>
				<msh:row>
					<msh:textField property="inventoryNumber" label="Инвентарный номер" fieldColSpan="3" horizontalFill="true"/>
				</msh:row>
				<msh:row>
					<msh:textField property="floors" label="Количество этажей"/>
					<msh:autoComplete property="securityMark" label="Метка безопасности" vocName="vocSecurityMark" horizontalFill="true"/>
				</msh:row>
				<msh:row>
					<msh:autoComplete property="responsiblePerson" label="Ответственное лицо" vocName="vocAssetResponsiblePerson" horizontalFill="true" fieldColSpan="3"/>
				</msh:row>
				<msh:row>
					<msh:textArea property="comment" label="Комментарии" fieldColSpan="3" horizontalFill="true"/>
				</msh:row>
			<msh:submitCancelButtonsRow colSpan="3" />
			</msh:panel>
		</msh:form>
		<msh:ifFormTypeIsView formName="asset_buildingForm">
			<msh:section title="Помещения">
			<ecom:parentEntityListAll formName="asset_roomForm" attribute="building" />
				<msh:table name="building" action="entityParentView-asset_room.do" idField="id">
					<msh:tableColumn columnName="#" property="sn"/>
					<msh:tableColumn columnName="Этаж" property="floor"/>
					<msh:tableColumn columnName="Номер помещения" property="roomNumber"/>
					<msh:tableColumn columnName="Местный номер" property="localPhone"/>
					<msh:tableColumn columnName="Городской номер" property="cityPhone"/>
				</msh:table>
			</msh:section>
		</msh:ifFormTypeIsView>
	</tiles:put>
	<tiles:put name="title" type="string">
		<ecom:titleTrail mainMenu="Patient" beginForm="asset_buildingForm" />
	</tiles:put>
	<tiles:put name="side" type="string">
		<msh:ifFormTypeAreViewOrEdit formName="asset_buildingForm">
			<msh:sideMenu>
				<msh:sideLink key="ALT+2" params="id" action="/entityParentEdit-asset_building" name="Изменить" title="Изменить" roles="/Policy/Mis/Asset/PermanentAsset/Territory/Building/Edit"/>
				<msh:sideLink key="ALT+DEL" params="id" action="/entityParentDelete-asset_building" name="Удалить" title="Удалить" roles="/Policy/Mis/Asset/PermanentAsset/Territory/Building/Delete"/>
			</msh:sideMenu>
			<msh:sideMenu title="Добавить" >
				<msh:sideLink key="ALT+N" params="id" action="/entityParentPrepareCreate-asset_room" name="Комнаты" title="Комнаты" roles="/Policy/Mis/Asset/PermanentAsset/Territory/Building/Room/Create"/>
			</msh:sideMenu>
		</msh:ifFormTypeAreViewOrEdit>
	</tiles:put>
</tiles:insert>
