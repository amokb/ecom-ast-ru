<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="side" type="string">
    <msh:ifFormTypeIsView formName="preg_pregHistoryForm">
      <msh:sideMenu title="История родов">
        <msh:sideLink key="ALT+2" params="id" action="/entityParentEdit-preg_pregHistory" name="Изменить" roles="/Policy/Mis/Pregnancy/Edit" />
        <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDeleteGoSubclassView-preg_pregHistory" name="Удалить" roles="/Policy/Mis/Pregnancy/Delete" />
      </msh:sideMenu>
      <msh:sideMenu title="Осмотры" />
      <msh:sideMenu title="Печать">
      	<msh:sideLink action="/print-preghistory.do?s=HospitalPrintService&amp;m=printPregHistory" params="id" name="История родов" title="Печать истории родов"/>
      </msh:sideMenu>
    </msh:ifFormTypeIsView>
  </tiles:put>
  <tiles:put name="body" type="string">
    <!-- 
    	  - История родов
    	  -->
    <msh:form action="/entityParentSaveGoView-preg_pregHistory.do" defaultField="pregnancy">
      <msh:hidden property="id" />
      <msh:hidden property="medCase" />
      <msh:hidden property="saveType" />
      <msh:panel>
        <msh:autoComplete property="pregnancy" label="Беременность" horizontalFill="true" vocName="pregnancyByWoman" viewAction="entityParentView-preg_pregnancy.do" size="150" />
        <msh:ifFormTypeIsCreate formName="preg_pregHistoryForm">
          <td>
            <a href="javascript:createNewPregnancy()" title="Завести новую беременность по пациентке">Новая беременность</a>
          </td>
        </msh:ifFormTypeIsCreate>
        <msh:submitCancelButtonsRow colSpan="" />
      </msh:panel>
    </msh:form>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Patient" beginForm="preg_pregHistoryForm" />
  </tiles:put>
  <tiles:put name="javascript" type="string">
    <msh:ifFormTypeIsCreate formName="preg_pregHistoryForm">
    <script type='text/javascript' src="./dwr/interface/HospitalMedCaseService.js" >
    /**/
    </script>
    <script type='text/javascript'>
    function createNewPregnancy() {
  		//alert('Создание новой беременности для СМО ${param.id}') ;
  		HospitalMedCaseService.getPatient('${param.id}',
  			{
  				callback: function(aIdPat){
  					//alert("Пациент="+aIdPat) ;
  					window.location.href = 'entityParentPrepareCreate-preg_pregnancy.do?medCase=${param.id}&id='+aIdPat ;
  				}
  			})
  		
  		//window.location.href = 'entityParentPrepareCreate-preg_pregnancy.do?medCase=${param.id}&id=' ;
  	}
  </script>
    </msh:ifFormTypeIsCreate>
    <msh:ifFormTypeIsNotView formName="preg_pregHistoryForm">
    <script type='text/javascript'>
    	//alert($('medCase').value) ;
	    pregnancyAutocomplete.setParentId($('medCase').value) ;
  </script>
  </msh:ifFormTypeIsNotView>
  </tiles:put>
</tiles:insert>

