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
					<% // Версия от 21 ноября %>
				<h1>Ноябрь 2017 года</h1>
				
				<ul>
					<li>В журнал по ВМП добавлены поля: дата выдачи талона, дата предварительной госпитализации, номер талона ВМП</li>
					<li>в АРМ регистратора поликлиники добавлено отображение информации о пользователе, дате и времени создания предварительного направления</li>
					<li>Перед удаление СЛО добавлена проверка на наличие в нем информации о родах и новорожденных.</li>
					<li>Исправлена ошибка при выгрузке данных в личный кабинет пациента</li>
					<li>Добавлен отчет по оказанной мед. помощи иногородним и иностранцам (для выгрузки в МИАЦ)</li>
					<li>Направление на ВК: поле PatientHealthInfo сделано обязательным</li>
					<li>ЛН добавлен фильтр по ЭЛН/не ЭЛН листам</li>
					<li>При создании услуги по прейскуранту идет поиск по коду только действущей услуги</li>
					<li>При объединении СЛО переносится информация о новорожденных</li>
					<li>263 приказ. При формировании N1 по поступившим без направления генерируется номер направления</li>
					<li>Добавлен запрет на создание дубля услуги (по коду)</li>
					<li>Исправлена ошибка при создании рабочей функции с проставленной галочкаой "генерировать календарь"</li>
					<li>Исправлено добавление/изменение услуги при редактировании направления.</li>
					<li>Электронные больничные листы - исправлена ошибка при создании, доработано получение номера ЭЛН при отсутствии свободных номеров.</li>
					<li>Исправлена ошибка при удалении направления, если к направлению были привязаны услуги</li>



					<msh:ifInRole roles="/Policy/Config/HelpAdmin">
							<br>
						</msh:ifInRole>
				</ul>
					</div>
			</tr>
		</table>
	</tiles:put>
</tiles:insert>