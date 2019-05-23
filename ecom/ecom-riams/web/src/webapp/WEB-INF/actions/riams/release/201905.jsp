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
					<% // Версия от 23 мая %>
				<h1>Май 2019 года</h1>
					<h2>Стационар:</h2>
					<ul>
						<li>1. Реализована возможность редактировать браслеты пациентов в СЛО (случай лечения в отделении -> Браслеты) </li>
						<li>2. Появилась возможность создавать предварительную госпитализацию для врачей стационара, возможность создававать госпитализацию из предварительной госпитализации</li>
					</ul>
					<h2>Поликлиника:</h2>
					<ul>
						<li>1. Реализован отчёт, формирующий время работы специалистов на текущий день (Отчёты -> Расписание специалистов консультативной поликлиники) </li>
						<li>2. Реализована возможность редактирования кабинетов в рабочей функции </li>
					</ul>
					<h2>Интерфейс:</h2>
					<ul>
						<li>1. Кнопки вертикальной навигации перемещены в правую часть экрана </li>
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