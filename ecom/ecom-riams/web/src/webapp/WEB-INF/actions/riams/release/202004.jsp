<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh"%>
<%@ taglib uri="/WEB-INF/mis.tld" prefix="mis"%>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true">

	<tiles:put name='title' type='string'>
	</tiles:put>

	<tiles:put name='body' type='string'>
		<table class='mainMenu'>
			<tr>
				<td class='col1'>
				<div class='menu'>
					<% // Версия от 7 апреля %>
				<h1>Апрель 2020 года</h1>
				
				<ul>
					<li></li>
					<li>Отчёт по браслетам пациентов (на текущий момент и за период). Выборка по датам, отделению, браслету</li>
					<li>Отчёт для ЦБРФ</li>
					<li>Печать с проверкой непечатных на матричном принтере символов. Дополнительный функционал, который позволит перед печатью проверить
						дневник и предложит заменить недопустимые символы на допустимые (чтобы не было знаков вопроса при печати) </li>
					<li>Карта коронавируса</li>
					<li>Рефакторинг браслетов пациентов</li>
					<li>Назначения лекарственных препаратов</li>
					<li>Механизм создания браслета при добавлении операции ИВЛ и автоматическое снятие при вводе даты-времени окончания операции
					(для услуг с браслетами дата и время окончания в операции - необязательные поля)</li>
					<li>В журнал по браслетам добавлена возможность поиска пациента с одним браслетов из множества
						, либо пациента, у которого несколько выбранных браслетов</li>
					<li></li>



					<msh:ifInRole roles="/Policy/Config/HelpAdmin">
							<br>
						</msh:ifInRole>
				</ul>
					</div>
			</tr>
		</table>
	</tiles:put>
</tiles:insert>