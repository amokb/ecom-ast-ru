<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh"%>
<%@ taglib uri="/WEB-INF/mis.tld" prefix="mis"%>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true">

	<tiles:put name='title' type='string'>
		<msh:title>ИНСТРУКЦИИ</msh:title>
	</tiles:put>

	<tiles:put name='body' type='string'>
	
		<table class='mainMenu'>
			<tr>
				<td class='col1'>
				<div class='menu'>
				<h2 style="font-size: large;" align="center">Лаборатория</h2>
				<ul style="font-size: large; list-style: none;" >
					<li><msh:link  action="js-riams-instruction.do?id=lab_pr_m_s" >
					Рабочее место процедурной мед.сестры
                        </msh:link></li>
					<li><msh:link  action="js-riams-instruction.do?id=lab_doctor">
					Рабочее место врача-лаборанта
                        </msh:link></li>
					<li><msh:link  action="js-riams-instruction.do?id=lab_tech">
					Рабочее место техника-лаборанта
                        </msh:link></li>
                    <li><msh:link  action="js-riams-instruction.do?id=setup_lab">
						Настройка модуля "Лаборатория"
                    </msh:link></li>
				</ul>
				<br>
				<h2 style="font-size: large;" align="center">Стационар</h2>
				<ul style="font-size: large; list-style: none;" >
					<li><msh:link  action="js-riams-instruction.do?id=template_change_info">
						Работа с шаблонами протоколов
                    </msh:link></li>
					<li><msh:link  action="js-riams-instruction.do?id=add_template_list_prescript">
						Добавление шаблона листа назначений
                    </msh:link></li>
					
					<li><msh:link  action="js-riams-instruction.do?id=zav_send_diary">
						Отправка дневника заведующим на редактирование (врачу)
                    </msh:link></li>
					<li><msh:link  action="js-riams-instruction.do?id=prescript_list">
						Создание назначений (в листе назначений)
                    </msh:link></li>
					<li><msh:link  action="js-riams-instruction.do?id=refusal_hospitalization">
						Оформление отказа от госпитализаций
                    </msh:link></li>
				</ul>
				<br>
				<h2 style="font-size: large;" align="center">Общее</h2>
				<ul style="font-size: large; list-style: none;" >

					
					<li><msh:link  action="js-riams-instruction.do?id=lpu_setting">
						Настройка ЛПУ (участки, коечный фонд)
                    </msh:link></li>
					<li><msh:link  action="js-riams-instruction.do?id=create_calendar">
						Создание календаря
                    </msh:link></li>
                    <li><msh:link  action="js-riams-instruction.do?id=contract_plat_patient">
						Работа с платными договорами по физическим лицам
                    </msh:link></li>
                    <li><msh:link  action="js-riams-instruction.do?id=attach_population">
						Работа с отчетом по прикрепленному населению
                    </msh:link></li>
				</ul>
				
				
				
				</div>
			</tr>

			
		</table>
	</tiles:put>
</tiles:insert>
