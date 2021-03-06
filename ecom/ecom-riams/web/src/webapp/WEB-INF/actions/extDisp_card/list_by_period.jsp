<%@page import="ru.ecom.web.util.ActionUtil"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true" >
	<tiles:put name='title' type='string'>
		<msh:title mainMenu="Journals" >Диспансеризация</msh:title>
	</tiles:put>
	<tiles:put name='side' type='string'>
	</tiles:put>
	<tiles:put name='body' type='string' >
  <%
  	String typeGroup =ActionUtil.updateParameter("ExtDispAction","typeGroup","1", request) ;
  	String typePaid =ActionUtil.updateParameter("ExtDispAction","typePaid","3", request) ;
%>
		<msh:form action="extDisp_journal_card.do" defaultField="beginDate">
			<msh:panel>
			<msh:row>
				<msh:textField property="beginDate" label="c"/>
				<msh:textField property="finishDate" label="по"/>
			</msh:row>
			<msh:row>
				<msh:autoComplete property="dispType" label="Тип доп. диспансеризации" vocName="vocExtDispAll" horizontalFill="true" fieldColSpan="3"/>
			</msh:row>
			<msh:row>
				<msh:autoComplete property="ageGroup" label="Возрастная группа" vocName="vocExtDispAgeGroupByDispType" parentAutocomplete="dispType" horizontalFill="true" fieldColSpan="3"/>
			</msh:row>
			<msh:row>
				<msh:autoComplete property="healthGroup" label="Группа здоровья" parentAutocomplete="dispType" vocName="vocExtDispHealthGroupByDispType" horizontalFill="true" fieldColSpan="3"/>
			</msh:row>
			<msh:row>
				<msh:autoComplete property="risk" label="Риск" vocName="vocExtDispRisk" fieldColSpan="3" horizontalFill="true"/>
			</msh:row>
			<msh:row>
				<msh:autoComplete property="service" label="Услуга" vocName="vocExtDispService" fieldColSpan="3" horizontalFill="true"/>
			</msh:row>
			<msh:row>
				<msh:autoComplete property="vocWorkFunction" label="Рабочая функция" vocName="vocWorkFunction" fieldColSpan="3" horizontalFill="true"/>
			</msh:row>
			<msh:row>
				<msh:autoComplete property="workFunction" label="Специалист" vocName="workFunction" fieldColSpan="3" horizontalFill="true"/>
			</msh:row>
			<msh:row>
				<msh:autoComplete property="lpu" label="ЛПУ" vocName="lpu" fieldColSpan="3" horizontalFill="true"/>
			</msh:row>
        <msh:row>
	        <td class="label" title="Оплата (typePaid)" colspan="1"><label for="typeGroupName" id="typeGroupLabel">Группировки общие:</label></td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typePaid" value="1"> оплаченные
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typePaid" value="2"> не оплаченные
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typePaid" value="3"> все
	        </td>
        </msh:row>
        <msh:row>
	        <td class="label" title="Группировка (typePatient)" colspan="1"><label for="typeGroupName" id="typeGroupLabel">Группировки общие:</label></td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeGroup" value="1"> реестр по типу доп.дисп.
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeGroup" value="2"> доп. реестр
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeGroup" value="3"> реестр по услугам
	        </td>
        </msh:row>
        <msh:row>
        	<td></td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeGroup" value="4"> общий свод
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeGroup" value="5"> свод по возрастной периодам
	        </td>

        </msh:row>			
        <msh:row>
        	<td></td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeGroup" value="6"> свод по факторам риска
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeGroup" value="7"> свод по группам здоровья
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeGroup" value="8"> свод по услугам
	        </td>

        </msh:row>			
        <msh:row>
        	<td></td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeGroup" value="9"> свод по заболеваниям
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeGroup" value="10"> свод по раб.функции
	        </td>        
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeGroup" value="11"> свод по ЛПУ
	        </td>        
        </msh:row>	
        <msh:row>
        	<td>Группировки по отчету</td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeGroup" value="12"> свод по возрастным категориям
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeGroup" value="13"> свод по факторам риска
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeGroup" value="14"> свод по группам здоровья
	        </td>
        </msh:row>			
        <msh:row>
        	<td></td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeGroup" value="15"> свод по заболеваниям
	        </td>     
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeGroup" value="16"> свод по специалистам и услугам 
	        </td>     
        </msh:row>
       <msh:row>
       	<msh:hidden property="filename"/>
       	<td colspan="4">
       		Файл <span id='aView'></span>
       	</td>
       	</msh:row>
        <msh:row>
        	<msh:submitCancelButtonsRow labelSave="Сформировать" doNotDisableButtons="cancel" labelSaving="Формирование..." colSpan="4"/>
        </msh:row>
        <msh:row>
       <td>
       	<input type="button" onclick="showForm();" value = "Экспорт для сайта orph.rosminzdarav.ru">
       </td>
       </msh:row>
			</msh:panel>
			<div id="formOrphDiv" style="display: none">
			<msh:panel styleId="formOrph">
			<msh:row>
				<td>Экспорт карт в систему Минздрава(orph.rosminzdarav.ru)</td>
			</msh:row>
			<msh:row>
				<td colspan="10" >
					<p>Рекомендуется выгружать данные небольшими порциями (до 200 записей), иначе сайт Минздрава может не принять файл.</p>
				</td>
			</msh:row>
			<msh:row>
				<td> Тип диспансеризации:
				</td> 
				<td>
					<input type="checkbox" name="expDispType" value="1" checked="checked" >Профилактические осмотры
				</td>
				<%-- <td>
					<input type="checkbox" name="expDispType" value="2" >Предварительные осмотры
				</td>
				<td>
					<input type="checkbox" name="expDispType" value="3" >Периодические осмотры
				</td> --%>
			</msh:row>
			<msh:row>
				<td>Возраст ДД: 
				</td>
				<td onclick="this.childNodes[1].checked='checked';">
					<input type="radio" name="expDispAge" value="1">Все возраста
				</td>
				<td onclick="this.childNodes[1].checked='checked';">
					<input type="radio" name="expDispAge" value="2" checked="checked" >Только полные года
				</td>
			</msh:row>
			<msh:row>
				<td>Выгружать: 
				</td>
				<td onclick="this.childNodes[1].checked='checked';">
					<input type="radio" name="typeExport" value="1">Все карты
				</td>
				<td onclick="this.childNodes[1].checked='checked';">
					<input type="radio" name="typeExport" value="2" checked="checked" >Только невыгруженные
				</td>
			</msh:row>
			<msh:row>
				<msh:textField property="createFrom" label="созданные c"/>
				<msh:textField property="createTo" label="созданные по"/>
			</msh:row>
			<msh:row>
				<td>
					<span>Значения по умолчанию: </span>
				</td>
			</msh:row>
			<msh:row>
				<msh:textField label="Группа для занятия физ. культурой (цифра)" property="expFizGroup" fieldColSpan="10" horizontalFill="true" />
			</msh:row>
			<msh:row>
				<msh:textField label="Рост (в см)" property="expHeight" fieldColSpan="10" horizontalFill="true" />
			</msh:row>
			<msh:row>
				<msh:textField label="Вес (в кг)" property="expWeight" fieldColSpan="10" horizontalFill="true" />
			</msh:row>
			<msh:row>
				<msh:textField label="Окружность головы (в см)" property="expHeadsize" fieldColSpan="10" horizontalFill="true" />
			</msh:row>
			<msh:row>
				<msh:textField label="Результат анализов (текст)" property="expResearchText" fieldColSpan="10" horizontalFill="true" />
			</msh:row>
			<msh:row>
				<msh:textField label="Рекомендации по ЗОЖ" property="expZOJRecommend" fieldColSpan="10" horizontalFill="true" />
			</msh:row>
			<msh:row>
				<msh:textField label="Рекомендации по лечению" property="expRecommend" fieldColSpan="10" horizontalFill="true" />
			</msh:row>
			<msh:row>
				<msh:textField label="Число записей в файле " property="expDivideNum" fieldColSpan="10" horizontalFill="true" />
			</msh:row>
			
			<msh:row>
				<td>
	       			<input type="button" onclick="prepareForm30();" value="Экспортировать"/>
	       		</td>
				<td>
	       			<input type="button" onclick="showExpHelp();" value="Подсказка по экспорту"/>
	       		</td>
			</msh:row>
				<table id="exportTable" border="1" style="padding: 15px; display: none">
       <tr style="color: red">
        	<td colspan="4">Внимание! Следующие карты не выгружены!</td>
       </tr>
       <tr>
        	<td>Номер карты</td>
        	<td>Пациент</td>
        	<td>Диагноз</td>
        	<td>Ошибка</td>
        </tr>
         <tbody id="exportElements" >
        </tbody>
        </table>
			</msh:panel>
			</div>
		</msh:form>
<%
	String beginDate = request.getParameter("beginDate") ;
	if (beginDate!=null && !beginDate.equals("")) {
		String finishDate = request.getParameter("finishDate") ;
		String dispType = request.getParameter("dispType") ;
		if (finishDate==null || finishDate.equals("")) {
			finishDate=beginDate ;
		}
		request.setAttribute("beginDate", beginDate) ;
		request.setAttribute("finishDate", finishDate) ;
		if (!typeGroup.equals("4") && (dispType==null || dispType.equals("")||dispType.equals("0"))) {
			typeGroup = "4" ;
		}
		/*
		if (typeGroup.equals("1")) {
			// Группировка по дате
			request.setAttribute("groupSql", "to_char(CAo.operationdate,'dd.mm.yyyy')") ;
       		request.setAttribute("groupSqlId", "'&beginDate='||to_char(CAo.operationdate,'dd.mm.yyyy')||'&finishDate='||to_char(CAo.operationdate,'dd.mm.yyyy')") ;
       		request.setAttribute("groupName", "Дате операции") ;
       		request.setAttribute("groupGroup", "CAo.operationdate") ;
       		request.setAttribute("groupGroupNext", "2") ;
       		request.setAttribute("groupOrder", "CAo.operationdate") ;
		} else if (typeGroup.equals("2")) {
			// Группировка по операторам
   			request.setAttribute("groupSql", "wp.lastname||' '||wp.firstname||' '||wp.middlename") ;
   			request.setAttribute("groupSqlId", "'&operator='||wf.id") ;
   			request.setAttribute("groupName", "Оператор") ;
   			request.setAttribute("groupGroup", "wf.id,vwf.name,wp.lastname,wp.firstname,wp.middlename") ;
       		request.setAttribute("groupGroupNext", "4") ;
   			request.setAttribute("groupOrder", "wp.lastname") ;
		} else if (typeGroup.equals("3")) {
			// Группировка по услугам 
   			request.setAttribute("groupSql", "pp.code||' '||pp.name") ;
   			request.setAttribute("groupSqlId", "'&medService='||pms.id") ;
   			request.setAttribute("groupName", "Услуга") ;
       		request.setAttribute("groupGroupNext", "4") ;
   			request.setAttribute("groupGroup", "pms.id,pp.code,pp.name") ;
   			request.setAttribute("groupOrder", "pp.code") ;
		} else {
			//Реестр
   			request.setAttribute("groupSql", "pms.name") ;
   			request.setAttribute("groupSqlId", "'&medService='||pms.id") ;
   			request.setAttribute("groupName", "Сотрудник") ;
   			request.setAttribute("groupGroup", "pms.id,pms.code,pms.name") ;
   			request.setAttribute("groupOrder", "pms.code") ;
		}
		ActionUtil.setParameterFilterSql("operator","cao.workFunction_id", request) ;
		ActionUtil.setParameterFilterSql("medService","pms.id", request) ;
		*/
		StringBuilder sqlAdd = new StringBuilder() ;
		sqlAdd.append(ActionUtil.setParameterFilterSql("dispType","edc.dispType_id", request)) ;
		sqlAdd.append(ActionUtil.setParameterFilterSql("workFunction","edc.workFunction_id", request)) ;
		sqlAdd.append(ActionUtil.setParameterFilterSql("vocWorkFunction","wf.workFunction_id", request)) ;
		sqlAdd.append(ActionUtil.setParameterFilterSql("lpu","lpu.id", request)) ;
		sqlAdd.append(ActionUtil.setParameterFilterSql("ageGroup","edc.ageGroup_id", request)) ;
		sqlAdd.append(ActionUtil.setParameterFilterSql("healthGroup","edc.healthGroup_id", request)) ;
		sqlAdd.append(ActionUtil.setParameterFilterSql("socialGroup","edc.socialGroup_id", request)) ;
		sqlAdd.append(ActionUtil.setParameterFilterSql("risk","edr.dispRisk_id", request)) ;
		sqlAdd.append(ActionUtil.setParameterFilterSql("service","eds.serviceType_id", request)) ;
		if (typePaid!=null &&typePaid.equals("1")) {
			sqlAdd.append(" and (edc.notPaid is null or edc.notPaid='0')") ;
		} else if (typePaid!=null &&typePaid.equals("2")) {
			sqlAdd.append(" and edc.notPaid='1'") ;
		}
		request.setAttribute("sqlAppend", sqlAdd.toString()) ;
		%>
		<% if (typeGroup!=null && typeGroup.equals("1") ) {%>
			<msh:section>
			<ecom:webQuery name="reestrExtDispCard" nameFldSql="reestrExtDispCard_sql" nativeSql="
select edc.id,p.lastname||' '||p.firstname||' '||
p.middlename||' '||to_char(p.birthday,'dd.mm.yyyy') as birthday
,to_char(edc.startDate,'dd.mm.yyyy') as edcBeginDate,to_char(edc.finishDate,'dd.mm.yyyy') as edcFinishDate
,mkb.code as mkbcode
,vedag.name as vedagname
,vedsg.name as vedsgname
,vedhg.name as vedhgname
,list(distinct vedr.name) as vrisks
, edc.isObservation as cntDispM
,edc.isTreatment as cntLechM
,edc.isDiagnostics as cntDiagM
,edc.isSpecializedCare as cntSpecCareM
,edc.isSanatorium as cntSanatM
,edc.isServiceIndication as cntIsServiceIndication
from ExtDispCard edc
left join WorkFunction wf on wf.id=edc.workFunction_id
left join Worker w on w.id=wf.worker_id
left join MisLpu lpu on lpu.id=w.lpu_id
left join Patient p on p.id=edc.patient_id
left join VocExtDisp ved on ved.id=edc.dispType_id
left join VocExtDispHealthGroup vedhg on vedhg.id=edc.healthGroup_id
left join VocExtDispSocialGroup vedsg on vedsg.id=edc.socialGroup_id
left join VocExtDispAgeGroup vedag on vedag.id=edc.ageGroup_id
left join ExtDispRisk edr on edr.card_id=edc.id
left join VocExtDispRisk vedr on vedr.id=edr.dispRisk_id
left join VocIdc10 mkb on mkb.id=edc.idcMain_id
left join ExtDispService eds on eds.card_id=edc.id and eds.serviceDate is not null
where edc.finishDate between to_date('${beginDate}','dd.mm.yyyy') and to_date('${finishDate}','dd.mm.yyyy')
${sqlAppend} 
group by edc.id,p.lastname,p.firstname,
p.middlename,p.birthday,edc.startDate ,edc.finishDate 
,vedag.name,vedhg.name,vedsg.name
, edc.isObservation ,edc.isTreatment ,edc.isDiagnostics ,edc.isSpecializedCare,edc.isSanatorium 
,mkb.code,edc.isServiceIndication
order by p.lastname,p.firstname,p.middlename
			"/>
<msh:sectionTitle>
    <form action="print-extDisp_journal_period_reestr.do" method="post" target="_blank">
Реестр карт по доп.диспансеризации за период ${param.beginDate}-${param.finishDate}
    <input type='hidden' name="sqlText" id="sqlText" value="${reestrExtDispCard_sql}"> 
    <input type='hidden' name="sqlInfo" id="sqlInfo" value="Реестр карт по доп.диспансеризации за период с ${param.beginDate} по ${param.finishDate}.">
    <input type='hidden' name="sqlColumn" id="sqlColumn" value="">
    <input type='hidden' name="s" id="s" value="PrintService">
    <input type='hidden' name="m" id="m" value="printNativeQuery">
    <input type="submit" value="Печать"> 
    </form>

</msh:sectionTitle>
<msh:sectionContent>
				<msh:table name="reestrExtDispCard" printToExcelButton="Сохранить в excel"
				action="entityView-extDisp_card.do" viewUrl="entityView-extDisp_card.do?short=Short"
				idField="1">
					<msh:tableColumn columnName="ФИО пациента" property="2" />
					<msh:tableColumn columnName="Дата начала" property="3" />
					<msh:tableColumn columnName="Дата окончания" property="4" />
					<msh:tableColumn columnName="Код МКБ" property="5" />
					<msh:tableColumn columnName="Возрастная категория" property="6" />
					<msh:tableColumn columnName="Социальная группа" property="7" />
					<msh:tableColumn columnName="Группа здоровья" property="8" />
					<msh:tableColumn columnName="Факторы риска" property="9" />
					<msh:tableColumn columnName="Установлено дисп.наблюдение" property="10" />
					<msh:tableColumn columnName="Назначено лечение"  property="11" />
					<msh:tableColumn columnName="Направлено доп. диаг. исследование"  property="12" />
					<msh:tableColumn columnName="Направлено доп. спец., в том числе ВПМ"  property="13" />
					<msh:tableColumn columnName="Направлено на сан-кур лечение" property="14" />
				</msh:table>
				</msh:sectionContent>
			</msh:section>
		<% } else if (typeGroup!=null && typeGroup.equals("2") ) {%>
			<msh:section>
			<ecom:webQuery name="reestrExtDispCard" nameFldSql="reestrExtDispCard_sql" nativeSql="
select edc.id,p.lastname||' '||p.firstname||' '||p.middlename as fio
, to_char(p.birthday,'dd.mm.yyyy') as birthday
,vedag.name as vedagname
,vedhg.name as vedhgname
,mkb.code as mkbcode
,list(distinct vedr.name) as vrisks
,coalesce(a.fullname)||' ' || case when p.houseNumber is not null and p.houseNumber!='' then ' д.'||p.houseNumber else '' end 
	 ||case when p.houseBuilding is not null and p.houseBuilding!='' then ' корп.'|| p.houseBuilding else '' end 
	||case when p.flatNumber is not null and p.flatNumber!='' then ' кв. '|| p.flatNumber else '' end 
	
	as address
,vwf.name||' '||wp.lastname||' '||wp.firstname||' '||wp.middlename as doctor
,to_char(edc.startDate,'dd.mm.yyyy')||'-'||to_char(edc.finishDate,'dd.mm.yyyy') as edcDate
from ExtDispCard edc
left join WorkFunction wf on wf.id=edc.workFunction_id
left join VocWorkFunction vwf on vwf.id=wf.workFunction_id
left join Worker w on w.id=wf.worker_id
left join MisLpu lpu on lpu.id=w.lpu_id
left join Patient wp on wp.id=w.person_id
left join Patient p on p.id=edc.patient_id
left join Address2 a on a.addressid=p.address_addressid
left join VocExtDisp ved on ved.id=edc.dispType_id
left join VocExtDispHealthGroup vedhg on vedhg.id=edc.healthGroup_id
left join VocExtDispSocialGroup vedsg on vedsg.id=edc.socialGroup_id
left join VocExtDispAgeGroup vedag on vedag.id=edc.ageGroup_id
left join ExtDispRisk edr on edr.card_id=edc.id
left join VocExtDispRisk vedr on vedr.id=edr.dispRisk_id
left join VocIdc10 mkb on mkb.id=edc.idcMain_id
left join ExtDispService eds on eds.card_id=edc.id and eds.serviceDate is not null
where edc.finishDate between to_date('${beginDate}','dd.mm.yyyy') and to_date('${finishDate}','dd.mm.yyyy')
${sqlAppend} 
group by edc.id,p.lastname,p.firstname,
p.middlename,p.birthday,edc.startDate ,edc.finishDate 
,vedag.name,vedhg.name,vedsg.name
, edc.isObservation ,edc.isTreatment ,edc.isDiagnostics ,edc.isSpecializedCare,edc.isSanatorium 
,mkb.code,edc.isServiceIndication,a.fullname,p.houseNumber,p.houseBuilding
,p.flatNumber,vwf.name,wp.lastname,wp.firstname,wp.middlename
order by p.lastname,p.firstname,p.middlename
			"/>
<msh:sectionTitle>
    <form action="print-extDisp_journal_period_reestr2.do" method="post" target="_blank">
Реестр карт по доп.диспансеризации за период ${param.beginDate}-${param.finishDate}
    <input type='hidden' name="sqlText" id="sqlText" value="${reestrExtDispCard_sql}"> 
    <input type='hidden' name="sqlInfo" id="sqlInfo" value="Реестр карт по доп.диспансеризации за период с ${param.beginDate} по ${param.finishDate}.">
    <input type='hidden' name="sqlColumn" id="sqlColumn" value="">
    <input type='hidden' name="s" id="s" value="PrintService">
    <input type='hidden' name="m" id="m" value="printNativeQuery">
    <input type="submit" value="Печать"> 
    </form>

</msh:sectionTitle>
<msh:sectionContent>
				<msh:table name="reestrExtDispCard" printToExcelButton="Сохранить в excel"
				action="entityView-extDisp_card.do" viewUrl="entityView-extDisp_card.do?short=Short"
				idField="1">
					<msh:tableColumn columnName="ФИО пациента" property="2" />
					<msh:tableColumn columnName="Год рождения" property="3" />
					<msh:tableColumn columnName="Возрастная группа" property="4" />
					<msh:tableColumn columnName="Группа здоровья" property="5" />
					<msh:tableColumn columnName="Диагноз" property="6" />
					<msh:tableColumn columnName="Факторы риска" property="7" />
					<msh:tableColumn columnName="Адрес" property="8" />
					<msh:tableColumn columnName="Фамилия врача" property="9" />
					<msh:tableColumn columnName="Дата дисп." property="10" />
				</msh:table>
				</msh:sectionContent>
			</msh:section>
		<% } else if (typeGroup!=null && typeGroup.equals("3") ) {%>
						<msh:section>
			<ecom:webQuery name="reestrExtDispCard" nameFldSql="reestrExtDispCard_sql" nativeSql="
select edc.id,p.lastname||' '||p.firstname||' '||p.middlename as fio
, to_char(p.birthday,'dd.mm.yyyy') as birthday
,vedag.name as vedagname
,vedhg.name as vedhgname
,mkb.code as mkbcode
,list(distinct vedr.name) as vrisks
,coalesce(a.fullname)||' ' || case when p.houseNumber is not null and p.houseNumber!='' then ' д.'||p.houseNumber else '' end 
	 ||case when p.houseBuilding is not null and p.houseBuilding!='' then ' корп.'|| p.houseBuilding else '' end 
	||case when p.flatNumber is not null and p.flatNumber!='' then ' кв. '|| p.flatNumber else '' end 
	
	as address
,vwf.name||' '||wp.lastname||' '||wp.firstname||' '||wp.middlename as doctor
,to_char(edc.startDate,'dd.mm.yyyy')||'-'||to_char(edc.finishDate,'dd.mm.yyyy') as edcDate
,list(veds.code||' '||veds.name||' - '||to_char(eds.serviceDate,'dd.mm.yyyy')) as service
from ExtDispCard edc
left join WorkFunction wf on wf.id=edc.workFunction_id
left join VocWorkFunction vwf on vwf.id=wf.workFunction_id
left join Worker w on w.id=wf.worker_id
left join MisLpu lpu on lpu.id=w.lpu_id
left join Patient wp on wp.id=w.person_id
left join Patient p on p.id=edc.patient_id
left join Address2 a on a.addressid=p.address_addressid
left join VocExtDisp ved on ved.id=edc.dispType_id
left join VocExtDispHealthGroup vedhg on vedhg.id=edc.healthGroup_id
left join VocExtDispSocialGroup vedsg on vedsg.id=edc.socialGroup_id
left join VocExtDispAgeGroup vedag on vedag.id=edc.ageGroup_id
left join ExtDispRisk edr on edr.card_id=edc.id
left join VocExtDispRisk vedr on vedr.id=edr.dispRisk_id
left join ExtDispService eds on eds.card_id=edc.id and eds.serviceDate is not null
left join VocExtDispService veds on eds.serviceType_id=veds.id
left join VocIdc10 mkb on mkb.id=edc.idcMain_id
where edc.finishDate between to_date('${beginDate}','dd.mm.yyyy') and to_date('${finishDate}','dd.mm.yyyy')
${sqlAppend} 
group by edc.id,p.lastname,p.firstname,
p.middlename,p.birthday,edc.startDate ,edc.finishDate 
,vedag.name,vedhg.name,vedsg.name
, edc.isObservation ,edc.isTreatment ,edc.isDiagnostics ,edc.isSpecializedCare,edc.isSanatorium 
,mkb.code,edc.isServiceIndication,a.fullname,p.houseNumber,p.houseBuilding
,p.flatNumber,vwf.name,wp.lastname,wp.firstname,wp.middlename
order by p.lastname,p.firstname,p.middlename
			"/>
<msh:sectionTitle>
    <form action="print-extDisp_journal_period_reestr2.do" method="post" target="_blank">
Реестр карт по доп.диспансеризации за период ${param.beginDate}-${param.finishDate}
    <input type='hidden' name="sqlText" id="sqlText" value="${reestrExtDispCard_sql}"> 
    <input type='hidden' name="sqlInfo" id="sqlInfo" value="Реестр карт по доп.диспансеризации за период с ${param.beginDate} по ${param.finishDate}.">
    <input type='hidden' name="sqlColumn" id="sqlColumn" value="">
    <input type='hidden' name="s" id="s" value="PrintService">
    <input type='hidden' name="m" id="m" value="printNativeQuery">
    <input type="submit" value="Печать"> 
    </form>

</msh:sectionTitle>
<msh:sectionContent>
				<msh:table name="reestrExtDispCard" printToExcelButton="Сохранить в excel"
				action="entityView-extDisp_card.do" viewUrl="entityView-extDisp_card.do?short=Short"
				idField="1">
					<msh:tableColumn columnName="ФИО пациента" property="2" />
					<msh:tableColumn columnName="Год рождения" property="3" />
					<msh:tableColumn columnName="Возрастная группа" property="4" />
					<msh:tableColumn columnName="Группа здоровья" property="5" />
					<msh:tableColumn columnName="Диагноз" property="6" />
					<msh:tableColumn columnName="Факторы риска" property="7" />
					<msh:tableColumn columnName="Адрес" property="8" />
					<msh:tableColumn columnName="Фамилия врача" property="9" />
					<msh:tableColumn columnName="Дата дисп." property="10" />
					<msh:tableColumn columnName="Услуги" property="11" />
				</msh:table>
				</msh:sectionContent>
			</msh:section>
	<%} else if (typeGroup!=null&& typeGroup.equals("4")) {%>
			<msh:section title="Свод за ${beginDate}-${finishDate} ">
			<ecom:webQuery name="extDispSwod" nativeSql="
select '&dispType='||ved.id,ved.name,ved.code,count(distinct edc.id) as cntAll
,count(distinct case when edc.isServiceIndication='1' then edc.id else null end) as cntAllDirect

 from ExtDispCard edc
left join WorkFunction wf on wf.id=edc.workFunction_id
left join Worker w on w.id=wf.worker_id
left join MisLpu lpu on lpu.id=w.lpu_id
left join VocExtDisp ved on ved.id=edc.dispType_id
left join VocExtDispHealthGroup vedhg on vedhg.id=edc.healthGroup_id
left join VocExtDispSocialGroup vedsg on vedsg.id=edc.socialGroup_id
left join VocExtDispAgeGroup vedag on vedag.id=edc.ageGroup_id
left join ExtDispRisk edr on edr.card_id=edc.id
left join VocExtDispRisk vedr on vedr.id=edr.dispRisk_id
left join ExtDispService eds on eds.card_id=edc.id and eds.serviceDate is not null
where edc.finishDate between to_date('${beginDate}','dd.mm.yyyy') and to_date('${finishDate}','dd.mm.yyyy')
${sqlAppend} 
group by ved.id,ved.name,ved.code
order by ved.code
			"/>

				<msh:table name="extDispSwod" printToExcelButton="Сохранить в excel"
				action="extDisp_journal_card.do?beginDate=${beginDate}&vocWorkFunction=${params.vocWorkFunction}&finishDate=${finishDate}" 
				idField="1">
					<msh:tableColumn columnName="Тип доп.диспансеризации" property="2" />
					<msh:tableColumn isCalcAmount="true" columnName="Код" property="3" />
					<msh:tableColumn isCalcAmount="true" columnName="Кол-во оформленных карт" property="4" />
					<msh:tableColumn isCalcAmount="true" columnName="Кол-во, направ. на след. этап" property="5" />
				</msh:table>

			</msh:section>


	<%} else if (typeGroup!=null&& typeGroup.equals("5")) {%>
			<msh:section title="Свод по возрастным категориям за ${beginDate}-${finishDate} ">
			<ecom:webQuery name="extDispAgeSwod" nativeSql="
select '&dispType='||ved.id||'&ageGroup='||vedag.id as id
,ved.name as vedname
,ved.code as vedcode,vedag.name as vedagname
,count(distinct case when vs.omcCode='1' then edc.id else null end) as cntM
,count(distinct case when vs.omcCode='2' then edc.id else null end) as cntW
,count(distinct edc.id) as cntAll
from ExtDispCard edc
left join WorkFunction wf on wf.id=edc.workFunction_id
left join Worker w on w.id=wf.worker_id
left join MisLpu lpu on lpu.id=w.lpu_id
left join Patient p on p.id=edc.patient_id
left join VocSex vs on vs.id=p.sex_id
left join VocExtDisp ved on ved.id=edc.dispType_id
left join VocExtDispHealthGroup vedhg on vedhg.id=edc.healthGroup_id
left join VocExtDispSocialGroup vedsg on vedsg.id=edc.socialGroup_id
left join VocExtDispAgeGroup vedag on vedag.id=edc.ageGroup_id
left join ExtDispRisk edr on edr.card_id=edc.id
left join VocExtDispRisk vedr on vedr.id=edr.dispRisk_id
left join ExtDispService eds on eds.card_id=edc.id and eds.serviceDate is not null
where edc.finishDate between to_date('${beginDate}','dd.mm.yyyy') and to_date('${finishDate}','dd.mm.yyyy')
${sqlAppend} 
group by ved.id,ved.name,ved.code,vedag.id,vedag.name
order by vedag.name
			"/>

				<msh:table name="extDispAgeSwod" printToExcelButton="Сохранить в excel"
				action="extDisp_journal_card.do?beginDate=${beginDate}&vocWorkFunction=${params.vocWorkFunction}&finishDate=${finishDate}" 
				idField="1">
					<msh:tableColumn columnName="Возрастная группа" property="4" />
					<msh:tableColumn columnName="Прошли диспансеризацию мужчин" isCalcAmount="true" property="5" />
					<msh:tableColumn columnName="Прошли диспансеризацию женщин" isCalcAmount="true" property="6" />
					<msh:tableColumn columnName="Всего" isCalcAmount="true" property="7" />
				</msh:table>

			</msh:section>
	<%} else if (typeGroup!=null&& typeGroup.equals("6")) {%>
			<msh:section title="Свод по факторам риска за ${beginDate}-${finishDate} ">
			<ecom:webQuery name="extDispSwod" nativeSql="
select '&dispType='||ved.id||'&ageGroup='||vedag.id||'&dispRisk='||vedr.id as id,ved.name as vedname
,ved.code as vedcode,vedag.name as vedagname
,vedr.name as vedrname
,count(distinct case when vs.omcCode='1' then edc.id else null end) as cntM
,count(distinct case when vs.omcCode='2' then edc.id else null end) as cntW
,count(distinct edc.id) as cntAll
from ExtDispRisk edr
left join ExtDispCard edc on edr.card_id=edc.id
left join WorkFunction wf on wf.id=edc.workFunction_id
left join Worker w on w.id=wf.worker_id
left join MisLpu lpu on lpu.id=w.lpu_id
left join Patient p on p.id=edc.patient_id
left join VocSex vs on vs.id=p.sex_id
left join VocExtDisp ved on ved.id=edc.dispType_id
left join VocExtDispHealthGroup vedhg on vedhg.id=edc.healthGroup_id
left join VocExtDispSocialGroup vedsg on vedsg.id=edc.socialGroup_id
left join VocExtDispAgeGroup vedag on vedag.id=edc.ageGroup_id
left join VocExtDispRisk vedr on vedr.id=edr.dispRisk_id
left join ExtDispService eds on eds.card_id=edc.id and eds.serviceDate is not null
where edc.finishDate between to_date('${beginDate}','dd.mm.yyyy') and to_date('${finishDate}','dd.mm.yyyy')
${sqlAppend} and vedr.id is not null
group by ved.id,ved.name,ved.code,vedag.id,vedag.name,vedr.id,vedr.name
order by vedr.id,vedag.name
			"/>

				<msh:table name="extDispSwod" printToExcelButton="Сохранить в excel"
				action="extDisp_journal_card.do?beginDate=${beginDate}&vocWorkFunction=${params.vocWorkFunction}&finishDate=${finishDate}" 
				idField="1">
					<msh:tableColumn columnName="Фактор риска" property="5" />
					<msh:tableColumn columnName="Возрастная группа" property="4" />
					<msh:tableColumn columnName="Выявлено у мужчин"  property="6" />
					<msh:tableColumn columnName="Выявлено у женщин"  property="7" />
					<msh:tableColumn columnName="Выявлено всего"  property="8" />
				</msh:table>

			</msh:section>

	<%} else if (typeGroup!=null&& typeGroup.equals("7")) {%>
			<msh:section title="Свод по группам здоровья за ${beginDate}-${finishDate} ">
			<ecom:webQuery name="extDispSwod" nativeSql="
select '&dispType='||ved.id||'&ageGroup='||vedag.id||'&healthGroup='||coalesce(vedhg.id,'-1') as id,ved.name as vedname
,ved.code as vedcode,vedag.name as vedagname
,vedhg.name as vedrname
,count(distinct case when vs.omcCode='1' then edc.id else null end) as cntM
,count(distinct case when vs.omcCode='1' and edc.isObservation='1' then edc.id else null end) as cntDispM
,count(distinct case when vs.omcCode='1' and edc.isTreatment='1' then edc.id else null end) as cntLechM
,count(distinct case when vs.omcCode='1' and edc.isDiagnostics='1' then edc.id else null end) as cntDiagM
,count(distinct case when vs.omcCode='1' and edc.isSpecializedCare='1' then edc.id else null end) as cntSpecCareM
,count(distinct case when vs.omcCode='1' and edc.isSanatorium='1' then edc.id else null end) as cntSanatM
,count(distinct case when vs.omcCode='2' then edc.id else null end) as cntW
,count(distinct case when vs.omcCode='2' and edc.isObservation='1' then edc.id else null end) as cntDispW
,count(distinct case when vs.omcCode='2' and edc.isTreatment='1' then edc.id else null end) as cntLechW
,count(distinct case when vs.omcCode='2' and edc.isDiagnostics='1' then edc.id else null end) as cntDiagW
,count(distinct case when vs.omcCode='2' and edc.isSpecializedCare='1' then edc.id else null end) as cntSpecCareW
,count(distinct case when vs.omcCode='2' and edc.isSanatorium='1' then edc.id else null end) as cntSanatW
,count(distinct edc.id) as cntAll
from ExtDispCard edc
left join WorkFunction wf on wf.id=edc.workFunction_id
left join Worker w on w.id=wf.worker_id
left join MisLpu lpu on lpu.id=w.lpu_id
left join ExtDispRisk edr on edr.card_id=edc.id
left join Patient p on p.id=edc.patient_id
left join VocSex vs on vs.id=p.sex_id
left join VocExtDisp ved on ved.id=edc.dispType_id
left join VocExtDispHealthGroup vedhg on vedhg.id=edc.healthGroup_id
left join VocExtDispSocialGroup vedsg on vedsg.id=edc.socialGroup_id
left join VocExtDispAgeGroup vedag on vedag.id=edc.ageGroup_id
left join VocExtDispRisk vedr on vedr.id=edr.dispRisk_id
left join ExtDispService eds on eds.card_id=edc.id and eds.serviceDate is not null
where edc.finishDate between to_date('${beginDate}','dd.mm.yyyy') and to_date('${finishDate}','dd.mm.yyyy')
${sqlAppend} 
group by ved.id,ved.name,ved.code,vedag.id,vedag.name,vedhg.id,vedhg.name
order by vedhg.name,vedag.name
			"/>

				<msh:table name="extDispSwod" printToExcelButton="Сохранить в excel"
				action="extDisp_journal_card.do?beginDate=${beginDate}&vocWorkFunction=${params.vocWorkFunction}&finishDate=${finishDate}" 
				idField="1">
				<msh:tableNotEmpty>
					<tr>
						<th></th>
						<th></th>
						<th colspan="6">Мужчины</th>
						<th colspan="6">Женщины</th>
						<th></th>
					</tr>
				</msh:tableNotEmpty>
					<msh:tableColumn columnName="Группа здоровья" property="5" />
					<msh:tableColumn columnName="Возрастная группа" property="4" />
					<msh:tableColumn columnName="Кол-во" isCalcAmount="true" property="6" />
					<msh:tableColumn columnName="Установлено дисп.наблюдение" isCalcAmount="true" property="7" />
					<msh:tableColumn columnName="Назначено лечение" isCalcAmount="true" property="8" />
					<msh:tableColumn columnName="Направлено доп. диаг. исследование" isCalcAmount="true" property="9" />
					<msh:tableColumn columnName="Направлено доп. спец., в том числе ВПМ" isCalcAmount="true" property="10" />
					<msh:tableColumn columnName="Направлено на сан-кур лечение" isCalcAmount="true" property="11" />

					<msh:tableColumn columnName="Кол-во" isCalcAmount="true" property="12" />
					<msh:tableColumn columnName="Установлено дисп.наблюдение" isCalcAmount="true" property="13" />
					<msh:tableColumn columnName="Назначено лечение" isCalcAmount="true" property="14" />
					<msh:tableColumn columnName="Направлено доп. диаг. исследование" isCalcAmount="true" property="15" />
					<msh:tableColumn columnName="Направлено доп. спец., в том числе ВПМ" isCalcAmount="true" property="16" />
					<msh:tableColumn columnName="Направлено на сан-кур лечение" isCalcAmount="true" property="17" />
					
					<msh:tableColumn columnName="Всего" isCalcAmount="true" property="18" />
				</msh:table>

			</msh:section>
	<%} else if (typeGroup!=null&& typeGroup.equals("8")) {%>
			<msh:section title="Свод по услугам за ${beginDate}-${finishDate} ">
			<ecom:webQuery name="extDispSwod" nativeSql="
select '&dispType='||ved.id||'&service='||veds.id as id
,ved.name as vedname
,ved.code as vedcode
,veds.name as vedrname
,count(distinct case when vs.omcCode='1' then edc.id else null end) as cntM1
,count(distinct case when vs.omcCode='1' and (eds.dtype='ExtDispExam' and eds.isPathology='1' or eds.dtype='ExtDispVisit' and eds.recommendation!='')  then edc.id else null end) as cntM2
,count(distinct case when vs.omcCode='2' then edc.id else null end) as cntW1
,count(distinct case when vs.omcCode='1' and (eds.dtype='ExtDispExam' and eds.isPathology='1' or eds.dtype='ExtDispVisit' and eds.recommendation!='')  then edc.id else null end) as cntW2
,count(distinct edc.id) as cntAll1
,count(distinct case when (eds.dtype='ExtDispExam' and eds.isPathology='1' or eds.dtype='ExtDispVisit' and eds.recommendation!='')  then edc.id else null end) as cntAll2
from ExtDispCard edc
left join WorkFunction wf on wf.id=edc.workFunction_id
left join Worker w on w.id=wf.worker_id
left join MisLpu lpu on lpu.id=w.lpu_id
left join VocIdc10 mkb on mkb.id=edc.idcMain_id
left join ExtDispService eds on eds.card_id=edc.id
left join VocExtDispService veds on eds.serviceType_id=veds.id
left join ExtDispRisk edr on edr.card_id=edc.id
left join Patient p on p.id=edc.patient_id
left join VocSex vs on vs.id=p.sex_id
left join VocExtDisp ved on ved.id=edc.dispType_id
left join VocExtDispHealthGroup vedhg on vedhg.id=edc.healthGroup_id
left join VocExtDispSocialGroup vedsg on vedsg.id=edc.socialGroup_id
left join VocExtDispAgeGroup vedag on vedag.id=edc.ageGroup_id
left join VocExtDispRisk vedr on vedr.id=edr.dispRisk_id
where edc.finishDate between to_date('${beginDate}','dd.mm.yyyy') and to_date('${finishDate}','dd.mm.yyyy')
${sqlAppend}  and eds.serviceDate is not null
group by ved.id,ved.name,ved.code,veds.id,veds.name,veds.code
order by veds.id
			"/>

				<msh:table name="extDispSwod" printToExcelButton="Сохранить в excel"
				action="extDisp_journal_card.do?beginDate=${beginDate}&vocWorkFunction=${params.vocWorkFunction}&finishDate=${finishDate}" 
				idField="1">
				<msh:tableNotEmpty>
					<tr>
						<th></th>
						<th colspan="2">Мужчины</th>
						<th colspan="2">Женщины</th>
						<th colspan="2">Всего</th>
						<th></th>
					</tr>
				</msh:tableNotEmpty>
					<msh:tableColumn columnName="Услуга" property="4" />
					<msh:tableColumn columnName="Прошли" isCalcAmount="true" property="5" />
					<msh:tableColumn columnName="Выявлено заболевания" isCalcAmount="true" property="6" />
					<msh:tableColumn columnName="Прошли" isCalcAmount="true" property="7" />
					<msh:tableColumn columnName="Выявлено заболевания" isCalcAmount="true" property="8" />
					<msh:tableColumn columnName="Прошли" isCalcAmount="true" property="9" />
					<msh:tableColumn columnName="Выявлено заболевания" isCalcAmount="true" property="10" />
				</msh:table>

			</msh:section>
	<%} else if (typeGroup!=null&& typeGroup.equals("9")) {
	%>
			<msh:section title="Свод по заболеваниям за ${beginDate}-${finishDate} ">
			<ecom:webQuery name="extDispSwod" nativeSql="
select '&dispType='||ved.id||'&ageGroup='||vedag.id||'&mkb='||substring(mkb.code,1,3) as id,ved.name as vedname
,ved.code as vedcode,vedag.name as vedagname
,substring(mkb.code,1,3) as vedrname
,count(distinct case when vs.omcCode='1' then edc.id else null end) as cntM
,count(distinct case when vs.omcCode='2' then edc.id else null end) as cntW
,count(distinct edc.id) as cntAll
from ExtDispCard edc
left join WorkFunction wf on wf.id=edc.workFunction_id
left join Worker w on w.id=wf.worker_id
left join MisLpu lpu on lpu.id=w.lpu_id
left join VocIdc10 mkb on mkb.id=edc.idcMain_id
left join ExtDispRisk edr on edr.card_id=edc.id
left join Patient p on p.id=edc.patient_id
left join VocSex vs on vs.id=p.sex_id
left join VocExtDisp ved on ved.id=edc.dispType_id
left join VocExtDispHealthGroup vedhg on vedhg.id=edc.healthGroup_id
left join VocExtDispSocialGroup vedsg on vedsg.id=edc.socialGroup_id
left join VocExtDispAgeGroup vedag on vedag.id=edc.ageGroup_id
left join VocExtDispRisk vedr on vedr.id=edr.dispRisk_id
left join ExtDispService eds on eds.card_id=edc.id and eds.serviceDate is not null
where edc.finishDate between to_date('${beginDate}','dd.mm.yyyy') and to_date('${finishDate}','dd.mm.yyyy')
${sqlAppend} 
group by ved.id,ved.name,ved.code,vedag.id,vedag.name,substring(mkb.code,1,3)
order by substring(mkb.code,1,3),vedag.name
			"/>

				<msh:table name="extDispSwod" printToExcelButton="Сохранить в excel"
				action="extDisp_journal_card.do?beginDate=${beginDate}&vocWorkFunction=${params.vocWorkFunction}&finishDate=${finishDate}" 
				idField="1">
					<msh:tableColumn columnName="Класс МКБ" property="5" />
					<msh:tableColumn columnName="Возрастная группа" property="4" />
					<msh:tableColumn columnName="Мужчины" isCalcAmount="true" property="6" />
					<msh:tableColumn columnName="Женщины" isCalcAmount="true" property="7" />
					<msh:tableColumn columnName="Всего" isCalcAmount="true" property="8" />
				</msh:table>

			</msh:section>

	<%} else if (typeGroup!=null&& typeGroup.equals("10")) {%>
			<msh:section title="Свод по раб.функциям за ${beginDate}-${finishDate} ">
			<ecom:webQuery name="extDispSwod" nativeSql="
select '&dispType='||ved.id||'&workFunction='||wf.id as id
,vwf.name||' '||wp.lastname||' '||wp.firstname||' '||coalesce(wp.middlename) as vedrname
,count(distinct case when vs.omcCode='1' then edc.id else null end) as cntM
,count(distinct case when vs.omcCode='2' then edc.id else null end) as cntW
,count(distinct edc.id) as cntAll
from ExtDispCard edc
left join ExtDispRisk edr on edr.card_id=edc.id
left join WorkFunction wf on wf.id=edc.workFunction_id
left join VocWorkFunction vwf on vwf.id=wf.workFunction_id
left join Worker w on w.id=wf.worker_id
left join MisLpu lpu on lpu.id=w.lpu_id
left join Patient wp on wp.id=w.person_id
left join Patient p on p.id=edc.patient_id
left join VocSex vs on vs.id=p.sex_id
left join VocExtDisp ved on ved.id=edc.dispType_id
left join VocExtDispHealthGroup vedhg on vedhg.id=edc.healthGroup_id
left join VocExtDispSocialGroup vedsg on vedsg.id=edc.socialGroup_id
left join VocExtDispAgeGroup vedag on vedag.id=edc.ageGroup_id
left join VocExtDispRisk vedr on vedr.id=edr.dispRisk_id
left join ExtDispService eds on eds.card_id=edc.id and eds.serviceDate is not null
where edc.finishDate between to_date('${beginDate}','dd.mm.yyyy') and to_date('${finishDate}','dd.mm.yyyy')
${sqlAppend}
group by ved.id,wf.id,vwf.name,wp.lastname,wp.firstname,wp.middlename
order by wp.lastname
			"/>

				<msh:table name="extDispSwod" printToExcelButton="Сохранить в excel"
				action="extDisp_journal_card.do?beginDate=${beginDate}&vocWorkFunction=${params.vocWorkFunction}&finishDate=${finishDate}" 
				idField="1">
					<msh:tableColumn columnName="Раб.функция" property="2" />
					<msh:tableColumn columnName="Кол-во мужчин" isCalcAmount="true" property="3" />
					<msh:tableColumn columnName="Кол-во женщин" isCalcAmount="true" property="4" />
					<msh:tableColumn columnName="Всего" isCalcAmount="true" property="5" />
				</msh:table>

			</msh:section>

	<%} else if (typeGroup!=null&& typeGroup.equals("11")) {%>
			<msh:section title="Свод по ЛПУ за ${beginDate}-${finishDate} ">
			<ecom:webQuery name="extDispSwod" nativeSql="
select '&dispType='||ved.id||'&lpu='||lpu.id as id
,lpu.name as vedrname
,count(distinct case when vs.omcCode='1' then edc.id else null end) as cntM
,count(distinct case when vs.omcCode='2' then edc.id else null end) as cntW
,count(distinct edc.id) as cntAll
from ExtDispCard edc
left join ExtDispRisk edr on edr.card_id=edc.id
left join WorkFunction wf on wf.id=edc.workFunction_id
left join VocWorkFunction vwf on vwf.id=wf.workFunction_id
left join Worker w on w.id=wf.worker_id
left join MisLpu lpu on lpu.id=w.lpu_id
left join Patient wp on wp.id=w.person_id
left join Patient p on p.id=edc.patient_id
left join VocSex vs on vs.id=p.sex_id
left join VocExtDisp ved on ved.id=edc.dispType_id
left join VocExtDispHealthGroup vedhg on vedhg.id=edc.healthGroup_id
left join VocExtDispSocialGroup vedsg on vedsg.id=edc.socialGroup_id
left join VocExtDispAgeGroup vedag on vedag.id=edc.ageGroup_id
left join VocExtDispRisk vedr on vedr.id=edr.dispRisk_id
left join ExtDispService eds on eds.card_id=edc.id and eds.serviceDate is not null
where edc.finishDate between to_date('${beginDate}','dd.mm.yyyy') and to_date('${finishDate}','dd.mm.yyyy')
${sqlAppend}
group by ved.id,lpu.id,lpu.name
order by lpu.name
			"/>

				<msh:table name="extDispSwod" printToExcelButton="Сохранить в excel"
				action="extDisp_journal_card.do?beginDate=${beginDate}&vocWorkFunction=${params.vocWorkFunction}&finishDate=${finishDate}" 
				idField="1">
					<msh:tableColumn columnName="Раб.функция" property="2" />
					<msh:tableColumn columnName="Кол-во мужчин" isCalcAmount="true" property="3" />
					<msh:tableColumn columnName="Кол-во женщин" isCalcAmount="true" property="4" />
					<msh:tableColumn columnName="Всего" isCalcAmount="true" property="5" />
				</msh:table>

			</msh:section>







	<%} else if (typeGroup!=null&& typeGroup.equals("12")) {%>
			<msh:section title="Свод по группам возрастов за ${beginDate}-${finishDate} ">
			<ecom:webQuery name="extDispAgeSwod" nativeSql="
select '&dispType='||ved.id||'&ageReportGroup='||vedarg.id as id
,ved.name as vedname
,ved.code as vedcode,vedarg.name as vedagname
,count(distinct case when vs.omcCode='1' then edc.id else null end) as cntM
,count(distinct case when vs.omcCode='2' then edc.id else null end) as cntW
,count(distinct edc.id) as cntAll
from ExtDispCard edc
left join WorkFunction wf on wf.id=edc.workFunction_id
left join Worker w on w.id=wf.worker_id
left join MisLpu lpu on lpu.id=w.lpu_id
left join Patient p on p.id=edc.patient_id
left join VocSex vs on vs.id=p.sex_id
left join VocExtDisp ved on ved.id=edc.dispType_id
left join VocExtDispHealthGroup vedhg on vedhg.id=edc.healthGroup_id
left join VocExtDispSocialGroup vedsg on vedsg.id=edc.socialGroup_id
left join VocExtDispAgeGroup vedag on vedag.id=edc.ageGroup_id
left join VocExtDispAgeReportGroup vedarg on vedarg.id=vedag.reportGroup_id
left join ExtDispRisk edr on edr.card_id=edc.id
left join VocExtDispRisk vedr on vedr.id=edr.dispRisk_id
left join ExtDispService eds on eds.card_id=edc.id and eds.serviceDate is not null
where edc.finishDate between to_date('${beginDate}','dd.mm.yyyy') and to_date('${finishDate}','dd.mm.yyyy')
${sqlAppend} 
group by ved.id,ved.name,ved.code,vedarg.id,vedarg.code,vedarg.name
order by vedarg.code
			"/>

				<msh:table name="extDispAgeSwod" printToExcelButton="Сохранить в excel"
				action="extDisp_journal_card.do?beginDate=${beginDate}&vocWorkFunction=${params.vocWorkFunction}&finishDate=${finishDate}" 
				idField="1">
					<msh:tableColumn columnName="Возрастная группа" property="4" />
					<msh:tableColumn columnName="Прошли диспансеризацию мужчин" isCalcAmount="true" property="5" />
					<msh:tableColumn columnName="Прошли диспансеризацию женщин" isCalcAmount="true" property="6" />
					<msh:tableColumn columnName="Всего" isCalcAmount="true" property="7" />
				</msh:table>

			</msh:section>
	<%} else if (typeGroup!=null&& typeGroup.equals("13")) {%>
			<msh:section title="Свод по факторам риска за ${beginDate}-${finishDate} ">
			<ecom:webQuery name="extDispSwod" nativeSql="
select '&dispType='||ved.id||'&ageReportGroup='||vedarg.id||'&dispRisk='||vedr.id as id,ved.name as vedname
,ved.code as vedcode,vedarg.name as vedagname
,vedr.name as vedrname
,count(distinct case when vs.omcCode='1' then edc.id else null end) as cntM
,count(distinct case when vs.omcCode='2' then edc.id else null end) as cntW
,count(distinct edc.id) as cntAll
from ExtDispRisk edr
left join ExtDispCard edc on edr.card_id=edc.id
left join ExtDispService eds on eds.card_id=edc.id and eds.serviceDate is not null
left join WorkFunction wf on wf.id=edc.workFunction_id
left join Worker w on w.id=wf.worker_id
left join MisLpu lpu on lpu.id=w.lpu_id
left join Patient p on p.id=edc.patient_id
left join VocSex vs on vs.id=p.sex_id
left join VocExtDisp ved on ved.id=edc.dispType_id
left join VocExtDispHealthGroup vedhg on vedhg.id=edc.healthGroup_id
left join VocExtDispSocialGroup vedsg on vedsg.id=edc.socialGroup_id
left join VocExtDispAgeGroup vedag on vedag.id=edc.ageGroup_id
left join VocExtDispAgeReportGroup vedarg on vedarg.id=vedag.reportGroup_id
left join VocExtDispRisk vedr on vedr.id=edr.dispRisk_id
where edc.finishDate between to_date('${beginDate}','dd.mm.yyyy') and to_date('${finishDate}','dd.mm.yyyy')
${sqlAppend} and vedr.id is not null
group by ved.id,ved.name,ved.code,vedarg.id,vedarg.name,vedarg.code,vedr.id,vedr.name
order by vedr.id,vedarg.code
			"/>

				<msh:table name="extDispSwod" printToExcelButton="Сохранить в excel"
				action="extDisp_journal_card.do?beginDate=${beginDate}&vocWorkFunction=${params.vocWorkFunction}&finishDate=${finishDate}" 
				idField="1">
					<msh:tableColumn columnName="Фактор риска" property="5" />
					<msh:tableColumn columnName="Возрасная группа" property="4" />
					<msh:tableColumn columnName="Выявлено у мужчин" property="6" />
					<msh:tableColumn columnName="Выявлено у женщин"  property="7" />
					<msh:tableColumn columnName="Выявлено всего" property="8" />
				</msh:table>

			</msh:section>

	<%} else if (typeGroup!=null&& typeGroup.equals("14")) {%>
			<msh:section title="Свод по группам здоровья за ${beginDate}-${finishDate} ">
			<ecom:webQuery name="extDispSwod" nativeSql="
select '&dispType='||ved.id||'&ageReportGroup='||vedarg.id||'&healthGroup='||coalesce(vedhg.id,'-1') as id,ved.name as vedname
,ved.code as vedcode,vedarg.name as vedagname
,vedhg.name as vedrname
,count(distinct case when vs.omcCode='1' then edc.id else null end) as cntM
,count(distinct case when vs.omcCode='1' and edc.isObservation='1' then edc.id else null end) as cntDispM
,count(distinct case when vs.omcCode='1' and edc.isTreatment='1' then edc.id else null end) as cntLechM
,count(distinct case when vs.omcCode='1' and edc.isDiagnostics='1' then edc.id else null end) as cntDiagM
,count(distinct case when vs.omcCode='1' and edc.isSpecializedCare='1' then edc.id else null end) as cntSpecCareM
,count(distinct case when vs.omcCode='1' and edc.isSanatorium='1' then edc.id else null end) as cntSanatM
,count(distinct case when vs.omcCode='2' then edc.id else null end) as cntW
,count(distinct case when vs.omcCode='2' and edc.isObservation='1' then edc.id else null end) as cntDispW
,count(distinct case when vs.omcCode='2' and edc.isTreatment='1' then edc.id else null end) as cntLechW
,count(distinct case when vs.omcCode='2' and edc.isDiagnostics='1' then edc.id else null end) as cntDiagW
,count(distinct case when vs.omcCode='2' and edc.isSpecializedCare='1' then edc.id else null end) as cntSpecCareW
,count(distinct case when vs.omcCode='2' and edc.isSanatorium='1' then edc.id else null end) as cntSanatW
,count(distinct edc.id) as cntAll
from ExtDispCard edc
left join ExtDispService eds on eds.card_id=edc.id and eds.serviceDate is not null
left join WorkFunction wf on wf.id=edc.workFunction_id
left join Worker w on w.id=wf.worker_id
left join MisLpu lpu on lpu.id=w.lpu_id
left join ExtDispRisk edr on edr.card_id=edc.id
left join Patient p on p.id=edc.patient_id
left join VocSex vs on vs.id=p.sex_id
left join VocExtDisp ved on ved.id=edc.dispType_id
left join VocExtDispHealthGroup vedhg on vedhg.id=edc.healthGroup_id
left join VocExtDispSocialGroup vedsg on vedsg.id=edc.socialGroup_id
left join VocExtDispAgeGroup vedag on vedag.id=edc.ageGroup_id
left join VocExtDispAgeReportGroup vedarg on vedarg.id=vedag.reportGroup_id
left join VocExtDispRisk vedr on vedr.id=edr.dispRisk_id
where edc.finishDate between to_date('${beginDate}','dd.mm.yyyy') and to_date('${finishDate}','dd.mm.yyyy')
${sqlAppend} 
group by ved.id,ved.name,ved.code,vedarg.id,vedarg.name,vedarg.code,vedhg.id,vedhg.name
order by vedhg.name,vedarg.code
			"/>

				<msh:table name="extDispSwod" printToExcelButton="Сохранить в excel"
				action="extDisp_journal_card.do?beginDate=${beginDate}&vocWorkFunction=${params.vocWorkFunction}&finishDate=${finishDate}" 
				idField="1">
				<msh:tableNotEmpty>
					<tr>
						<th></th>
						<th></th>
						<th colspan="6">Мужчины</th>
						<th colspan="6">Женщины</th>
						<th></th>
					</tr>
				</msh:tableNotEmpty>
					<msh:tableColumn columnName="Группа здоровья" property="5" />
					<msh:tableColumn columnName="Возрастная группа" property="4" />
					<msh:tableColumn columnName="Кол-во" isCalcAmount="true" property="6" />
					<msh:tableColumn columnName="Установлено дисп.наблюдение" isCalcAmount="true" property="7" />
					<msh:tableColumn columnName="Назначено лечение" isCalcAmount="true" property="8" />
					<msh:tableColumn columnName="Направлено доп. диаг. исследование" isCalcAmount="true" property="9" />
					<msh:tableColumn columnName="Направлено доп. спец., в том числе ВПМ" isCalcAmount="true" property="10" />
					<msh:tableColumn columnName="Направлено на сан-кур лечение" isCalcAmount="true" property="11" />

					<msh:tableColumn columnName="Кол-во" isCalcAmount="true" property="12" />
					<msh:tableColumn columnName="Установлено дисп.наблюдение" isCalcAmount="true" property="13" />
					<msh:tableColumn columnName="Назначено лечение" isCalcAmount="true" property="14" />
					<msh:tableColumn columnName="Направлено доп. диаг. исследование" isCalcAmount="true" property="15" />
					<msh:tableColumn columnName="Направлено доп. спец., в том числе ВПМ" isCalcAmount="true" property="16" />
					<msh:tableColumn columnName="Направлено на сан-кур лечение" isCalcAmount="true" property="17" />
					
					<msh:tableColumn columnName="Всего" isCalcAmount="true" property="18" />
				</msh:table>

			</msh:section>

	<%} else if (typeGroup!=null&& typeGroup.equals("15")) {%>
			<msh:section title="Свод по заболеваниям за ${beginDate}-${finishDate} ">
			<ecom:webQuery name="extDispSwod" nativeSql="
select '&dispType='||ved.id||'&ageReportGroup='||vedarg.id||'&mkb='||substring(mkb.code,1,3) as id,ved.name as vedname
,ved.code as vedcode,vedarg.name as vedagname
,substring(mkb.code,1,3) as vedrname
,count(distinct case when vs.omcCode='1' then edc.id else null end) as cntM
,count(distinct case when vs.omcCode='2' then edc.id else null end) as cntW
,count(distinct edc.id) as cntAll
from ExtDispCard edc
left join ExtDispService eds on eds.card_id=edc.id and eds.serviceDate is not null
left join WorkFunction wf on wf.id=edc.workFunction_id
left join Worker w on w.id=wf.worker_id
left join MisLpu lpu on lpu.id=w.lpu_id
left join VocIdc10 mkb on mkb.id=edc.idcMain_id
left join ExtDispRisk edr on edr.card_id=edc.id
left join Patient p on p.id=edc.patient_id
left join VocSex vs on vs.id=p.sex_id
left join VocExtDisp ved on ved.id=edc.dispType_id
left join VocExtDispHealthGroup vedhg on vedhg.id=edc.healthGroup_id
left join VocExtDispSocialGroup vedsg on vedsg.id=edc.socialGroup_id
left join VocExtDispAgeGroup vedag on vedag.id=edc.ageGroup_id
left join VocExtDispAgeReportGroup vedarg on vedarg.id=vedag.reportGroup_id
left join VocExtDispRisk vedr on vedr.id=edr.dispRisk_id
where edc.finishDate between to_date('${beginDate}','dd.mm.yyyy') and to_date('${finishDate}','dd.mm.yyyy')
${sqlAppend} 
group by ved.id,ved.name,ved.code,vedarg.id,vedarg.name,vedarg.code,substring(mkb.code,1,3)
order by substring(mkb.code,1,3),vedarg.name
			"/>

				<msh:table name="extDispSwod" printToExcelButton="Сохранить в excel"
				action="extDisp_journal_card.do?beginDate=${beginDate}&vocWorkFunction=${params.vocWorkFunction}&finishDate=${finishDate}" 
				idField="1">
					<msh:tableColumn columnName="Класс МКБ" property="5" />
					<msh:tableColumn columnName="Возрастная группа" property="4" />
					<msh:tableColumn columnName="Мужчины" isCalcAmount="true" property="6" />
					<msh:tableColumn columnName="Женщины" isCalcAmount="true" property="7" />
					<msh:tableColumn columnName="Всего" isCalcAmount="true" property="8" />
				</msh:table>

			</msh:section>

	<%} else if (typeGroup!=null&& typeGroup.equals("16")) {%>
			<msh:section title="Свод по услугам за ${beginDate}-${finishDate} ">
			<ecom:webQuery name="extDispSwod" nativeSql="
select '&dispType='||ved.id||'&workFunction='||wf.id||'&service='||veds.id as id
,ved.name as vedname
,vwf.name||' '||wp.lastname||' '||wp.firstname||' '||wp.middlename as wfinfo
,veds.name as vedrname
,count(distinct case when vs.omcCode='1' then edc.id else null end) as cntM1
,count(distinct case when vs.omcCode='1' and (eds.dtype='ExtDispExam' and eds.isPathology='1' or eds.dtype='ExtDispVisit' and eds.recommendation!='')  then edc.id else null end) as cntM2
,count(distinct case when vs.omcCode='2' then edc.id else null end) as cntW1
,count(distinct case when vs.omcCode='1' and (eds.dtype='ExtDispExam' and eds.isPathology='1' or eds.dtype='ExtDispVisit' and eds.recommendation!='')  then edc.id else null end) as cntW2
,count(distinct edc.id) as cntAll1
,count(distinct case when (eds.dtype='ExtDispExam' and eds.isPathology='1' or eds.dtype='ExtDispVisit' and eds.recommendation!='')  then edc.id else null end) as cntAll2
from ExtDispCard edc
left join WorkFunction wf on wf.id=edc.workFunction_id
left join VocWorkFunction vwf on vwf.id=wf.workFunction_id
left join Worker w on w.id=wf.worker_id
left join MisLpu lpu on lpu.id=w.lpu_id
left join Patient wp on wp.id=w.person_id
left join VocIdc10 mkb on mkb.id=edc.idcMain_id
left join ExtDispService eds on eds.card_id=edc.id
left join VocExtDispService veds on eds.serviceType_id=veds.id
left join ExtDispRisk edr on edr.card_id=edc.id
left join Patient p on p.id=edc.patient_id
left join VocSex vs on vs.id=p.sex_id
left join VocExtDisp ved on ved.id=edc.dispType_id
left join VocExtDispHealthGroup vedhg on vedhg.id=edc.healthGroup_id
left join VocExtDispSocialGroup vedsg on vedsg.id=edc.socialGroup_id
left join VocExtDispAgeGroup vedag on vedag.id=edc.ageGroup_id
left join VocExtDispRisk vedr on vedr.id=edr.dispRisk_id
where edc.finishDate between to_date('${beginDate}','dd.mm.yyyy') and to_date('${finishDate}','dd.mm.yyyy')
${sqlAppend}  and eds.serviceDate is not null
group by wf.id,ved.id,ved.name,ved.code,veds.id,veds.name,veds.code,vwf.name,wp.lastname,wp.firstname,wp.middlename
order by vwf.name,wp.lastname,wf.id,veds.id
			"/>

				<msh:table name="extDispSwod" printToExcelButton="Сохранить в excel"
				action="extDisp_journal_card.do?beginDate=${beginDate}&vocWorkFunction=${params.vocWorkFunction}&finishDate=${finishDate}" 
				idField="1">
				<msh:tableNotEmpty>
					<tr>
						<th></th>
						<th colspan="2">Мужчины</th>
						<th colspan="2">Женщины</th>
						<th colspan="2">Всего</th>
						<th></th>
					</tr>
				</msh:tableNotEmpty>
					<msh:tableColumn columnName="Услуга" property="4" />
					<msh:tableColumn columnName="Прошли" isCalcAmount="true" property="5" />
					<msh:tableColumn columnName="Выявлено заболевания" isCalcAmount="true" property="6" />
					<msh:tableColumn columnName="Прошли" isCalcAmount="true" property="7" />
					<msh:tableColumn columnName="Выявлено заболевания" isCalcAmount="true" property="8" />
					<msh:tableColumn columnName="Прошли" isCalcAmount="true" property="9" />
					<msh:tableColumn columnName="Выявлено заболевания" isCalcAmount="true" property="10" />
				</msh:table>

			</msh:section>
	<%} %>
	<%} %>

	</tiles:put>
  <tiles:put name="javascript" type="string">
  	<script type="text/javascript" src="./dwr/interface/ExtDispService.js"></script>
  	<script type="text/javascript">

    checkFieldUpdate('typeGroup','${typeGroup}',1) ;
    checkFieldUpdate('typePaid','${typePaid}',1) ;
 //   checkFieldUpdate('typeDtype','${typeDtype}',3) ;
  //  checkFieldUpdate('typeDate','${typeDate}',2) ;
   var sqlAdd = ""; 
  
   function createSqlField (aField, aSqlField) {
	   if ($(aField)) {
		   if ($(aField).value!=null&&$(aField).value!='') {
			   return " and "+aSqlField + " = "+$(aField).value;
			   
		   }
	   }
	   return '';
   }
  function showForm() {
	
	  if ($('formOrphDiv').style.display=='block') {
		  $('formOrphDiv').style.display='none';
	  }else {
		  $('formOrphDiv').style.display='block';
	  }
  }  
  function test() {
	  alert ($('expZOJRecommend').value);
  }
  function showExpHelp() {
	  alert ("Выгружаются карты детей, которым на дату осмотра педиатра не исполнилось 18 лет. Выгружаются все исследования, в которых указана дат осмотра\n"
			  +"Если у ребенка не указан RZ, тип документа (также если тип документа НЕ паспорт или НЕ св-во о рождении), нет актуального полиса ОМС, карта выгружена не будет.\n"
			  +"Поле \"Группа для занятий физ. культурой\" - обязательное\nРезультат анализов - как пример \"Без патологий\"");
  }
  function prepareForm30() {
	  sqlAdd="";
	  sqlAdd += createSqlField ('workFunction', 'edc.workfunction_id');
	  sqlAdd += createSqlField ('dispType', 'edc.dispType_id');
	  sqlAdd += createSqlField ('ageGroup', 'edc.ageGroup_id');
	  sqlAdd += createSqlField ('healthGroup', 'edc.healthGroup_id');
	  
	  $('exportTable').style.display = 'none' ;
	 if ($('createFrom').value!=null&&$('createFrom').value!='') {
		 if ($('createTo').value!=null&&$('createTo').value!='') {
			 sqlAdd += " and edc.createdate between to_date('"+$('createFrom').value+ "','dd.MM.yyyy')"+
			 " and to_date('"+$('createTo').value+"','dd.MM.yyyy')";
		 } else {
			 sqlAdd +=" and edc.createdate >= to_date('"+$('createFrom').value+"','dd.MM.yyyy')"
		 }
	 }
	  for (var i=0; i<document.getElementsByName("typeExport").length;i++) {
		  if (document.getElementsByName("typeExport")[i].checked){
			  if (document.getElementsByName("typeExport")[i].value=="2") {
				  sqlAdd+=" and edc.exportDate is null ";
			  }  
		  }		
	  }
	  for (var i=0; i<document.getElementsByName("expDispAge").length;i++) {
		  if (document.getElementsByName("expDispAge")[i].checked){
			  if (document.getElementsByName("expDispAge")[i].value=="2") {
				  sqlAdd+=" and (vedag.code not like '%.%' and vedag.code!='0') ";
			  }  
		  }		
	  }
	  var dispType="";
	  for (var i=0;i< document.getElementsByName("expDispType").length;i++) {
		  if (document.getElementsByName("expDispType")[i].checked) {
			  if (document.getElementsByName("expDispType")[i].value=="1") dispType+="'4',";
			  if (document.getElementsByName("expDispType")[i].value=="2") dispType+="'5',";
			  if (document.getElementsByName("expDispType")[i].value=="3") dispType+="'6',";
		  }
	  }
	  if (dispType=="") {
		  alert ("Укажите дип диспансеризации!");
		  return;
	  }
	  
	  
	  sqlAdd +=" and vedsg.code in ("+dispType.substring(0,dispType.length-1)+") ";
	  createForm30();
  }
  
  function createForm30() {
  	if ($('beginDate').value=='' || $('finishDate').value=='') {
    	alert ("Заполните дату начала и окончания!!") ;
    	return;
    }
	 	$('aView').innerHTML="Подождите...";
     ExtDispService.exportOrph($('beginDate').value, $('finishDate').value,"mis_",sqlAdd, 
    		$('expFizGroup').value,$('expHeight').value,$('expWeight').value,
    		$('expHeadsize').value,$('expResearchText').value,$('expZOJRecommend').value,$('expRecommend').value!=""?$('expRecommend').value:"_",$('expDivideNum').value,$('lpu').value, {
    	callback: function(aResult) {
            if (aResult==null) {
                $('aView').innerHTML="Ошибка, обратитесь к разработчикам" ;
                return;
            }
    	    aResult = JSON.parse(aResult);
            $('aView').innerHTML="<a href='../rtf/"+(aResult.archiveName?aResult.archiveName:'noFile')+"'>"+(aResult.archiveName?aResult.archiveName:'Нечего выгружать')+"</a>" ;
    		if (aResult.errorCards) {
                $('exportTable').style.display = 'block' ;
                flushTable();
    		    for (var i=0;i<aResult.errorCards.length;i++) {
                    addRow (aResult.errorCards[i]);
				}
			}
    	}
    });	 
 
	
    }
  
  function flushTable() {
      jQuery('#exportElements').children().remove();
  }
  var firstRow=1;
  function addRow (json) {
	   // ID:fullname:Diagnosis:Comment
	var tbody = document.getElementById('exportElements');
    var row = document.createElement("TR");
	//row.id = type+"Element"+num;
    tbody.appendChild(row);
    var td1 = document.createElement("TD");
    var td2 = document.createElement("TD");
    var td3 = document.createElement("TD");
    var td4 = document.createElement("TD");
    
	 row.appendChild(td1);
	 row.appendChild(td2);
	 row.appendChild(td3);
	 row.appendChild(td4);
   
    // Наполняем ячейки  
    td1.innerHTML = "<a href='/riams/entityView-extDisp_card.do?id="+json.cardId+"' target='_blank'><span>\t"+json.cardId+"</span></a>";
    td2.innerHTML = "<span> "+json.patientInfo+"</span>";
  	td3.innerHTML = "<span> "+json.diagnosis+"</span>";
   	td4.innerHTML = "<span> "+json.errorName+"</span>";
   
  }
    function checkFieldUpdate(aField,aValue,aDefault) {
    	
    	eval('var chk =  document.forms[0].'+aField) ;
    	var max = chk.length ;
    	if ((+aValue)>max) {
    		chk[+aDefault-1].checked='checked' ;
    	} else {
    		chk[+aValue-1].checked='checked' ;
    	}
    }
    </script>
    </tiles:put>
</tiles:insert>