<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <msh:title mainMenu="StacJournal" title="Просмотр данных о выписанных в другое ЛПУ" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <tags:stac_journal currentAction="stac_journalByMoveToAnotherLpu" />
  </tiles:put>
  <tiles:put name="body" type="string">
  <msh:section>
    <msh:sectionTitle>Просмотр данных о выписанных в другое ЛПУ ${info} ${infoTypePat}. 
    <a  href="stac_groupByMoveToAnotherLpuPrint.do?id=${param.id}"  >Печать</a> &nbsp; <a href="stac_groupByMoveToAnotherLpuList.do">Выбрать другую дату</a>
    </msh:sectionTitle>
    <msh:sectionContent>
  	<ecom:webQuery name="list" nativeSql="select m.id,ss.code,m.dateStart,m.dateFinish
  	,p.lastname||' '||p.firstname||' '||p.middlename,m.username,d.name as dname
  	,case when m.emergency='1' then 'Да' else 'Нет' end  as memergency,vdh.name as vdhname
  	,m.dateStart,m.dateFinish from MedCase as m 
  	left join patient p on p.id=m.patient_id 
    left join VocSocialStatus pvss on pvss.id=p.socialStatus_id
  	left join vocdeniedhospitalizating vdh on vdh.id = m.deniedhospitalizating_id 
  	left join mislpu d on d.id=m.department_id 
  	left join StatisticStub ss on ss.id=m.statisticStub_id 
  	left join Omc_Oksm ok on p.nationality_id=ok.id
  	where m.DTYPE='HospitalMedCase' and m.dateFinish between to_date('${dateBegin }','dd.mm.yyyy') and to_date('${dateEnd }','dd.mm.yyyy') 
    and m.moveToAnotherLpu_id=${anotherLpu} ${addStatus} ${add}"/>
    <msh:table name="list" action="entityParentView-stac_ssl.do" idField="1" noDataMessage="Не найдено">
      <msh:tableColumn columnName="Стат.карта" property="2" />
      <msh:tableColumn columnName="Дата начала" property="3" />
      <msh:tableColumn columnName="Дата окончания" property="4" />
      <msh:tableColumn columnName="Пациент" property="5" />
      <msh:tableColumn columnName="Отделение" property="7" />
      <msh:tableColumn columnName="Экстренность" property="8" />
    </msh:table>
    </msh:sectionContent>
    </msh:section>
  </tiles:put>
</tiles:insert>

