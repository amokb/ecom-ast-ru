<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

    <tiles:put name='title' type='string'>
        <msh:title mainMenu="Poly" property="worker" title="Повторные посещения">Просмотр дублей по специалистам</msh:title>
    </tiles:put>

    <tiles:put name='side' type='string'>
        <tags:visit_finds currentAction="journalDouble"/>
    </tiles:put>
    
  <tiles:put name="body" type="string">
    <msh:form action="/poly_doubleTickets_list.do" defaultField="dateBegin" disableFormDataConfirm="true" method="GET" guid="d7b31bc2-38f0-42cc-8d6d-19395273168f">
    <msh:panel guid="6ae283c8-7035-450a-8eb4-6f0f7da8a8ff">
      <msh:row guid="53627d05-8914-48a0-b2ec-792eba5b07d9">
        <msh:separator label="Параметры поиска" colSpan="7" guid="15c6c628-8aab-4c82-b3d8-ac77b7b3f700" />
      </msh:row>
        <msh:row>
        <msh:textField property="dateBegin" label="Период с" guid="8d7ef035-1273-4839-a4d8-1551c623caf1" />
        <msh:textField property="dateEnd" label="по" guid="f54568f6-b5b8-4d48-a045-ba7b9f875245" />
        </msh:row>
        <msh:row>
        <td class="label" title="Длительность (period)" colspan="1"><label for="periodName" id="peroidLabel">Длительность:</label></td>
        <td onclick="this.childNodes[1].checked='checked';changePeriod()">
        	<input type="radio" name="period" value="1"> Неделя
        </td>
        <td onclick="this.childNodes[1].checked='checked';changePeriod()">
        	<input type="radio" name="period" value="2"> Месяц
        </td>
           <td>
            <input type="submit" onclick="find()" value="Найти" />
          </td>

      </msh:row>
    </msh:panel>
    </msh:form>
    
    <%
    String date = (String)request.getParameter("dateBegin") ;
    if (date!=null && !date.equals(""))  {
    	%>
    
    <msh:section>
    <msh:sectionContent>
    <ecom:webQuery name="journal_double_visit" nativeSql="select t.patient_id||':'||$piece(to_char(t.dateStart,'YYYY-MM-DD'),' ')||':'||t.workFunctionExecute_id, t.id as tid, t.patient_id as person,t.dateStart
    				,(select count(*) from Medcase t2 where t2.patient_id=t.patient_id and t2.dateStart=t.dateStart and t2.DTYPE='Visit' and t2.workFunctionExecute_id=t.workFunctionExecute_id)
    				,vwf.name||' '|| p.lastname||' '||p.firstname||' '||p.middlename,pm.lastname||' '||pm.firstname||' '||pm.middlename 
    				from Medcase t 
    				left join Medcase t1 on  t.patient_id =t1.patient_id and t1.dateStart=t.dateStart and t1.workFunctionExecute_id=t.workFunctionExecute_id and t1.DTYPE='Visit'
    				left join WorkFunction as wf on wf.id=t.workFunctionExecute_id 
    				left join Worker as w on w.id=wf.worker_id 
    				left join Patient as p on p.id=w.person_id 
    				
    				 left join Patient as pm on pm.id=t.patient_id 
    				 inner join VocWorkFunction vwf on vwf.id=wf.workFunction_id 
    				 where t.DTYPE='Visit' and t.dateStart  between '${param.dateBegin}'  and '${param.dateEnd}' and t.id>t1.id group by t.patient_id,t.dateStart,t.workFunctionExecute_id"/>
        <msh:table name="journal_double_visit" action="smo_doubleVisits_data.do" idField="1" noDataMessage="Не найдено">
            <msh:tableColumn columnName="#" property="sn"/>
            <msh:tableColumn columnName="Дата" property="4"/>
            <msh:tableColumn columnName="Пациент" property="7"/>
            <msh:tableColumn columnName="Специалист" property="6"/>
            <msh:tableColumn columnName="Кол-во" property="5"/>
        </msh:table>
    </msh:sectionContent>


    </msh:section>
    <% } else {%>
    	<i>Выберите параметры поиска и нажмите "Найти" </i>
    	<% }   %>
    
    <script type='text/javascript' src='/skin/ext/jscalendar/calendar.js'></script> 
    <script type='text/javascript' src='/skin/ext/jscalendar/calendar-setup.js'></script> 
    <script type='text/javascript' src='/skin/ext/jscalendar/calendar-ru.js'></script> 
    <style type="text/css">@import url(/skin/ext/jscalendar/css/calendar-blue.css);</style>
    <script type='text/javascript'>
   // var typePatient = document.forms[0].typePatient ;
     var period = document.forms[0].period ;
    
    
    if ((+'${period}')==1) {
    	period[0].checked='checked' ;
    } else {
    	period[1].checked='checked' ;
    }   
    /*if ((+'${typePatient}')==1) {
    	typePatient[0].checked='checked' ;
    } else if ((+'${typePatient}')==2) {
    	typePatient[1].checked='checked' ;
    } else {
    	typePatient[2].checked='checked' ;
    }*/
    function find() {
    	var frm = document.forms[0] ;
    	frm.target='' ;
    	frm.action='smo_doubleVisits_list.do' ;
    }
    function print() {
    	var frm = document.forms[0] ;
    	frm.target='_blank' ;
    	frm.action='smo_doubleVisits_print.do' ;
    }
    function getPeriod() {
    	//var period = document.forms[0].period ;
    	for (i=0;i<period.length;i++) {
    		if (period[i].checked) return +period[i].value ;
    	}
    	return 1 ;
    }
    function changePeriod() {
    	
    	var field1 = document.getElementById("dateBegin").value;
    	var field2 = document.getElementById("dateEnd");
    	var date2 ;
    	var date = new Date(field1.substring(0,4),(+field1.substring(5,7)-1),field1.substring(8)) ;
    	if (getPeriod()==1) {
		 	date2 = new Date(date.getFullYear(),date.getMonth(),date.getDate()+6) ;
		 	//time += Date.WEEK; // substract one week
		 } else {
		 	date2=new Date(date.getFullYear(),date.getMonth()+1,date.getDate()-1) ;
		 }
		field2.value = date2.print("%Y-%m-%d");
    }
    function catcalc(cal) {
			var date = cal.date;
			var time = date.getTime() ;
			 // use the _other_ field
			 var field = document.getElementById("dateEnd");
			 var date2 ;
			 if (field == cal.params.inputField) {
				 field = document.getElementById("dateBegin");
				 if (getPeriod()==1) {
				 	date2 = new Date(date.getFullYear(),date.getMonth(),date.getDate()-6) ;
				 	//date2 = new Date(time) ;
				 } else {
				 	date2=new Date(date.getFullYear(),date.getMonth()-1,date.getDate()+1) ;
				 	
				 }
			 } else {
				 if (getPeriod()==1) {
				 	date2 = new Date(date.getFullYear(),date.getMonth(),date.getDate()+6) ;
				 	//time += Date.WEEK; // substract one week
				 } else {
				 	date2=new Date(date.getFullYear(),date.getMonth()+1,date.getDate()-1) ;
				 }
			 }
			 //var date2 = new Date(time);
			 field.value = date2.print("%Y-%m-%d");
	}
			 Calendar.setup({
				 inputField : "dateBegin", // id of the input field
				 ifFormat : "%Y-%m-%d", // format of the input field
				 showsTime : false,
				 timeFormat : "24",
				 eventName: "focus",
				 onUpdate : catcalc
			 });
			 Calendar.setup({
				 inputField : "dateEnd",
				 ifFormat : "%Y-%m-%d",
				 showsTime : false,
				 timeFormat : "24",
				 eventName: "focus",
				 onUpdate : catcalc
 			});
    </script>
  </tiles:put>
</tiles:insert>