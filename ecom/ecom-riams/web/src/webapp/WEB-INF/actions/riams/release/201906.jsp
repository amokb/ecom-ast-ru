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
					<% // Версия от 4 июня %>
				<h1>Июнь 2019 года</h1>
				
				<ul>
					<h2>Стационар:</h2>
					<ul>
						<li> * Экстренность в СЛО идентична экстренности в СЛС, которую указывают при госпитализации </li>
						<li> * Создание онкологической формы при выписке с онкологическим диагнозом теперь будет осуществляться в текущем окне без открытия новой вкладки </li>
                        <li> * Отредактирован вывод в файл при выгрузке направлений на ВК </li>
                        <li> * Добавлен вывод типа врачебной комиссии в своде открытых направлений на ВК, добавлена группировка </li>
						<li> * Отчёт по 203 приказу: вывод результатов по ПЕРЕВОДу ИЗ ПАТОЛОГИИ БЕРЕМЕННОСТИ В РОДОВОЕ и разбивке по отделениям ОБСЕРВАЦИОННОЕ/РОДОВОЕ можно настраивать с помощью чекбоксов </li>

					</ul>
					<h2>Поликлиника:</h2>
					<ul>
						<li> * В рабочем календаре добавлена возможность сортировать пациентов по ФИО по алфавиту (помимо сортировки по умолчанию - по времени исполнения). Для этого нужно использовать "Cортировать по ФИО" </li>
					</ul>
					<msh:ifInRole roles="/Policy/Config/HelpAdmin">
							<br>
						</msh:ifInRole>
				</ul>
					</div>
			</tr>
		</table>
	</tiles:put>
</tiles:insert>