<%@page import="ru.ecom.mis.ejb.service.patient.HospitalLibrary"%>
<%@page import="ru.ecom.web.util.ActionUtil"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<%@page import="ru.ecom.poly.web.action.ticket.JournalBySpecialistForm"%>
<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

    <tiles:put name='title' type='string'>
        <msh:title mainMenu="Report">Просмотр отчета по гражданству(Приказ №47р от 19.01.2017) </msh:title>
    </tiles:put>

    <tiles:put name='side' type='string'>
    </tiles:put>
    
  <tiles:put name="body" type="string">
  <%
	String typeEmergency =ActionUtil.updateParameter("Report_nationality","typeEmergency","3", request) ;
	String typePatient =ActionUtil.updateParameter("Report_nationality","typePatient","1", request) ;
	String typeGroup =ActionUtil.updateParameter("Report_nationality","typeGroup","2", request) ;
	String typeView =ActionUtil.updateParameter("Report_nationality","typeView","3", request) ;
  %>
    <msh:form action="/journal_nationality_new.do" defaultField="beginDate" disableFormDataConfirm="true" method="GET" guid="d7b31bc2-38f0-42cc-8d6d-19395273168f">
    <input type="hidden" name="m" id="m" value="categoryForeignNationals"/>
    <input type="hidden" name="s" id="s" value="VisitPrintService"/>
    <input type="hidden" name="id" id="id"/>
    <msh:panel>
      <msh:row guid="53627d05-8914-48a0-b2ec-792eba5b07d9">
        <msh:separator label="Параметры поиска" colSpan="7" guid="15c6c628-8aab-4c82-b3d8-ac77b7b3f700" />
      </msh:row>
      <msh:row guid="7d80be13-710c-46b8-8503-ce0413686b69">
        <td class="label" title="Поиск по пациентам (typePatient)" colspan="1"><label for="typePatientName" id="typePatientLabel">Пациенты:</label></td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typePatient" value="1">  иностранцы
        </td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typePatient" value="2">  иногородние
        </td>
      </msh:row>
   
      <msh:row>
        	<msh:textField property="beginDate"  label="Период с" guid="8d7ef035-1273-4839-a4d8-1551c623caf1" />
        	<msh:textField property="finishDate" fieldColSpan="7" label="по" guid="f54568f6-b5b8-4d48-a045-ba7b9f875245" />
      </msh:row>
        <msh:row>
        <td colspan="1" class="buttons">
			<input type="button" title="Найти" onclick="this.value=&quot;Поиск...&quot;;  this.form.action=&quot;journal_nationality_new.do&quot;;this.form.target=&quot;&quot; ; this.form.submit(); return true ;" value="Найти" class="default" id="submitButton" autocomplete="off">
 		</td>
        </msh:row>

    </msh:panel>
    </msh:form>
    <% if (typePatient.equals("1")) {
    	request.setAttribute("groupSql", "coalesce(p.nationality_id,0),vn.name");
    	request.setAttribute("change", "and (vn.id is not null and vn.voc_code!='643')");
    	request.setAttribute("change2", " ");
    	request.setAttribute("change3", "vn.name as vnname");
    	request.setAttribute("change4", "vn.name");
    	request.setAttribute("names", "Страна, где зарегистрирован гражданин");
    	
	} else if (typePatient.equals("2")) {
	
		request.setAttribute("groupSql", "coalesce(a.region_addressid,0),ar.name");
		request.setAttribute("change", "and ar.name != 'Астраханская'");
		request.setAttribute("change2", "left join Address2 ar on ar.addressid=a.region_addressid");
		request.setAttribute("change3", "ar.name as vnname");
		request.setAttribute("change4", "ar.name");
		request.setAttribute("names", "Cубъект РФ, где зарегистрирован гражданин");
		
	}
    	%>
    
    <msh:section>
<ecom:webQuery nameFldSql="sql_journal_swod" name="journal_swod" nativeSql="
select ${change3}
,count(distinct case when spo.dateStart=spo.dateFinish and vss.code='OBLIGATORYINSURANCE' then spo.id else null end) as visitOMC
,count(distinct case when spo.dateStart=spo.dateFinish and vss.code='BUDGET' then spo.id else null end) as visitBUDGET
,count(distinct case when spo.dateStart=spo.dateFinish and vss.code='CHARGED' then spo.id else null end) as visitPATIENT
,count(distinct case when spo.dateStart!=spo.dateFinish and vss.code='OBLIGATORYINSURANCE' then spo.id else null end) as treatmentOMC
,count(distinct case when spo.dateStart!=spo.dateFinish and vss.code='BUDGET' then spo.id else null end) as treatmentBUDGET
,count(distinct case when spo.dateStart!=spo.dateFinish and vss.code='CHARGED' then spo.id else null end) as treatmentPATIENT
,count(distinct case when m.dtype='DepartmentMedCase' and vbs.code='1' and m.deniedHospitalizating_id is null and vss.code='OBLIGATORYINSURANCE' then m.id else null end) as hospOMC
,count(distinct case when m.dtype='DepartmentMedCase' and vbs.code='1' and m.deniedHospitalizating_id is null and vss.code='BUDGET' then m.id else null end) as hospBUDGET
,count(distinct case when m.dtype='DepartmentMedCase' and vbs.code='1' and m.deniedHospitalizating_id is null and vss.code='CHARGED' then m.id else null end) as hospPATIENT
,count(distinct case when m.dtype='DepartmentMedCase' and vbs.code='2' and m.deniedHospitalizating_id is null and vss.code='OBLIGATORYINSURANCE' then m.id else null end) as hospOMC
,count(distinct case when m.dtype='DepartmentMedCase' and vbs.code='2' and m.deniedHospitalizating_id is null and vss.code='BUDGET' then m.id else null end) as hospBUDGET
,count(distinct case when m.dtype='DepartmentMedCase' and vbs.code='2' and m.deniedHospitalizating_id is null and vss.code='CHARGED' then m.id else null end) as hospPATIENT
,0,0
from medcase m
left join MedCase spo on spo.id=m.parent_id
left join VocServiceStream vss on vss.id=spo.serviceStream_id
left join patient p on p.id=m.patient_id
left join address2 a on a.addressid=p.address_addressid
${change2}
left join bedfund bf on bf.id = m.bedfund_id
left join vocbedsubtype vbs on vbs.id = bf.bedsubtype_id
left join Omc_Oksm vn on vn.id=p.nationality_id
where m.dateStart between to_date('${param.beginDate}','dd.mm.yyyy') and to_date('${param.finishDate}','dd.mm.yyyy')
${change}
and (m.noActuality is null or m.noActuality='0') 
group by ${groupSql}
order by ${change4}"
/> 

    
    <msh:sectionContent>
        <msh:table
         name="journal_swod" action="journal_nationality_new.do?beginDate=${param.beginDate}&finishDate=${param.finishDate}&typeView=1&typeGroup=${typeGroup}&typePatient=${typePatient}&typeEmergency=${typeEmergency}" idField="1" noDataMessage="Не найдено">
            <msh:tableNotEmpty>
              <tr>
                <th colspan="2" rowspan="2" />
                <th colspan="6" class="rightBold">Количество пациентов, получивших медицинскую помощь в амбулаторных условиях</th>
                <th colspan="3" rowspan="2" class="rightBold">Количество пациентов, получивших медицинскую помощь в стационарных условиях</th>
                <th colspan="3" rowspan="2" class="rightBold">Количество пациентов, получивших медицинскую помощь в условиях дневного стационара</th>
                <th colspan="2" rowspan="2" class="rightBold">Количество па-циентов, полу-чивших скорую медицинскую помощь</th>
              </tr>
              <tr>
              <th colspan="3" class="rightBold">Количество посещений</th>
              <th colspan="3" class="rightBold">Количество обращений</th>
              </tr>
            </msh:tableNotEmpty>            
            <msh:tableColumn columnName="#" property="sn"/>
            <msh:tableColumn columnName="${names}" property="1"/>            
            <msh:tableColumn columnName="за счет ОМС" property="2" isCalcAmount="true"/>
            <msh:tableColumn columnName="за счет бюджета" property="3" isCalcAmount="true"/>
            <msh:tableColumn columnName="за счет личных средств граждан" property="4" isCalcAmount="true"/>
            <msh:tableColumn columnName="за счет ОМС" property="5" isCalcAmount="true"/>
            <msh:tableColumn columnName="за счет бюджета" property="6" isCalcAmount="true"/>
            <msh:tableColumn columnName="за счет личных средств граждан" property="7" isCalcAmount="true"/>
            <msh:tableColumn columnName="за счет ОМС" property="8" isCalcAmount="true"/>
            <msh:tableColumn columnName="за счет бюджета" property="9" isCalcAmount="true"/>
            <msh:tableColumn columnName="в т.ч. платно" property="10" isCalcAmount="true"/>
            <msh:tableColumn columnName="за счет ОМС" property="11" isCalcAmount="true"/>
            <msh:tableColumn columnName="за счет бюджета" property="12" isCalcAmount="true"/>
            <msh:tableColumn columnName="за счет личных средств граждан" property="13" isCalcAmount="true"/>
            <msh:tableColumn columnName="за счет ОМС" property="14" isCalcAmount="true"/>
            <msh:tableColumn columnName="за счет бюджета" property="15" isCalcAmount="true"/>
        </msh:table>
    </msh:sectionContent>

    </msh:section>    	
    	
  </tiles:put>
  <tiles:put name="javascript" type="string">
  	<script type="text/javascript">
  	
    checkFieldUpdate('typePatient','${typePatient}',1) ;

  	function checkFieldUpdate(aField,aValue,aDefault) {
  		aValue=+aValue ;
    	eval('var chk =  document.forms[0].'+aField) ;
    	max = chk.length ;
    	if (aValue<1) aValue=+aDefault ;
    	if (aValue>max) {
    		if (aDefault>max) {
    			chk[max-1].checked='checked' ;
    		} else {
    			chk[aDefault-1].checked='checked' ;
    		}
    	} else {
    		chk[aValue-1].checked='checked' ;
    	}
    }
    
  	function getId(aBis) {
		 
		
	}
  	function getCheckedValue(radioGrp) {
  		var radioValue ;
  		for(i=0; i < radioGrp.length; i++){
  		  if (radioGrp[i].checked == true){
  		    radioValue = radioGrp[i].value;
  		    break ;
  		  }
  		} 
  		return radioValue ;
  	}
  		
  	</script>
  </tiles:put>

</tiles:insert>