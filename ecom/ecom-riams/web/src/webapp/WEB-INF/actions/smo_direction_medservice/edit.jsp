<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true">
	<tiles:put name="body" type="string">
		<msh:form action="/entityParentSaveGoParentView-smo_direction_medservice.do" defaultField="medServiceName">
			<msh:hidden property="id" />
			<msh:hidden property="saveType" />
			<msh:hidden property="parent" />
			<msh:hidden property="additionMedService" />
			<msh:panel>
				<msh:row>
					<msh:autoComplete property="medService" parentId="smo_direction_medserviceForm.parent" label="Услуга" vocName="medServiceForDirect" size="50" />
					<msh:textField property="medServiceAmount" label="Кол-во"/>
				</msh:row>
				<msh:ifFormTypeIsCreate formName="smo_direction_medserviceForm">
				<msh:row>
					<msh:autoComplete property="medService1" parentId="smo_direction_medserviceForm.parent" label="Услуга" vocName="medServiceForDirect" size="50" />
					<msh:textField property="medServiceAmount1" label="Кол-во"/>
				</msh:row>
				<msh:row>
					<msh:autoComplete property="medService2" parentId="smo_direction_medserviceForm.parent" label="Услуга" vocName="medServiceForDirect" size="50" />
					<msh:textField property="medServiceAmount2" label="Кол-во"/>
				</msh:row>
				<msh:row>
					<msh:autoComplete property="medService3" parentId="smo_direction_medserviceForm.parent" label="Услуга" vocName="medServiceForDirect" size="50" />
					<msh:textField property="medServiceAmount3" label="Кол-во"/>
				</msh:row>
				<msh:row>
					<msh:autoComplete property="medService4" parentId="smo_direction_medserviceForm.parent" label="Услуга" vocName="medServiceForDirect" size="50" />
					<msh:textField property="medServiceAmount4" label="Кол-во"/>
				</msh:row>
				<msh:row>
					<msh:autoComplete property="medService5" parentId="smo_direction_medserviceForm.parent" label="Услуга" vocName="medServiceForDirect" size="50" />
					<msh:textField property="medServiceAmount5" label="Кол-во"/>
				</msh:row>
				<msh:row>
					<msh:autoComplete property="medService6" parentId="smo_direction_medserviceForm.parent" label="Услуга" vocName="medServiceForDirect" size="50" />
					<msh:textField property="medServiceAmount6" label="Кол-во"/>
				</msh:row>
				<msh:row>
					<msh:autoComplete property="medService7" parentId="smo_direction_medserviceForm.parent" label="Услуга" vocName="medServiceForDirect" size="50" />
					<msh:textField property="medServiceAmount7" label="Кол-во"/>
				</msh:row>
				<msh:row>
					<msh:autoComplete property="medService8" parentId="smo_direction_medserviceForm.parent" label="Услуга" vocName="medServiceForDirect" size="50" />
					<msh:textField property="medServiceAmount8" label="Кол-во"/>
				</msh:row>
				</msh:ifFormTypeIsCreate>
			<msh:submitCancelButtonsRow colSpan="4" />
			</msh:panel>
		</msh:form>
		<msh:ifFormTypeIsView formName="smo_direction_medserviceForm">
		</msh:ifFormTypeIsView>
	</tiles:put>
	<tiles:put name="title" type="string">
		<ecom:titleTrail mainMenu="Contract" beginForm="smo_direction_medserviceForm" />
	</tiles:put>
	<tiles:put name="side" type="string">
		<msh:ifFormTypeAreViewOrEdit formName="smo_direction_medserviceForm">
			<msh:sideMenu>
				<msh:sideLink key="ALT+2" params="id" action="/entityParentEdit-contract_contractAcmedServiceAmount" name="Изменить" title="Изменить" roles="/Policy/Mis/Contract/MedContract/ServedPerson/ContractAccount/ContractAcmedServiceAmount/Edit"/>
				<msh:sideLink key="ALT+DEL" params="id" action="/entityParentDelete-contract_contractAcmedServiceAmount" name="Удалить" title="Удалить" roles="/Policy/Mis/Contract/MedContract/ServedPerson/ContractAccount/ContractAcmedServiceAmount/Delete"/>
			</msh:sideMenu>
			<tags:contractMenu currentAction="medContract"/>
		</msh:ifFormTypeAreViewOrEdit>
	</tiles:put>
	<tiles:put name="javascript" type="string">
	<msh:ifFormTypeIsCreate formName="smo_direction_medserviceForm">
		<script type="text/javascript" src="./dwr/interface/ContractService.js"></script>
		<script type="text/javascript">
		theFields = new Array() ;
		function setUpdate() {
            for (var i=0;i<=8;i++) {
            	if (i==0){
            		j="" ;
            	} else {
            		j=i ;
            	}
            	eval("theFields[i] = medService"+j+"Autocomplete") ; 
            	eval("theFields[i].addOnChangeCallback(function() {addMedService() ;});");
            	//eval("eventutil.addEventListener($('medServiceAmount"+j+"'),'keyup',function(){checkSum() ;})");
            	eval("eventutil.addEventListener($('medServiceAmount"+j+"'),'change',function(){addMedService() ;})");
            }
		}	
		
		setUpdate() ;
		//checkSum() ;
		
			function addMedService() {
				var allMedService ="";
				
				for (var i=1; i<theFields.length;i++) {
					if (+$('medServiceAmount'+i).value>0) {
						allMedService = ""+allMedService +"#"
							+ $('medService'+i).value
							+":"+ $('medServiceAmount'+i).value+":" 
						;
					}
				}
				$('additionMedService').value = allMedService.substring(1) ;
				//
			}
		</script>
		</msh:ifFormTypeIsCreate>
	</tiles:put>
</tiles:insert>