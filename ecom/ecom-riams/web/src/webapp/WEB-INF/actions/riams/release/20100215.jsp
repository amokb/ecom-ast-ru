<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh"%>
<%@ taglib uri="/WEB-INF/mis.tld" prefix="mis"%>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

	<tiles:put name='title' type='string'>
	</tiles:put>

	<tiles:put name='body' type='string'>
		<table class='mainMenu'>
			<tr>
				<td class='col1'>
				<div class='menu'>
				<h1>Февраль 2010 года</h1>
				<h2>Новые возможности при работе с "Эком:МИС":</h2>
				<ul>
					<li>
						1. Добавлена возможность изменения у роли пользователей
					</li>
					<li>
						2. Добавлена возможность  у политики просмотра ролей, в которых она содержится
					</li>
					
				</ul>
				<h2>Новые возможности при работе с "Эком:Стационаром":</h2>
					<ul>
					<li>
						1. Добавлен период актуальности в медицинских услугах 
					</li><li>
						2. Добавлена возможность удалить данные выписки для администратора
					</li><li>
						3. Доработан СЛС поступление.
					</li><li>
						4. Доработано добавление нового СЛО при переводе, а также журнал по направленным пациентам в отделение
					</li><li>
						5. Доработан выбор мед.услуг в соответствии с категорией и периодом актуальности.
					</li>
					<li>6. В выписку СЛС добавлено поле "Редкий случай"</li>
					<li>7. В мед.услугу добавлено поле "Условная единица трудоемкости"</li>
					<li>8. В поступление СЛС добавлена информация о движение по отделениям, хирург. операции и дневниках специалистов</li>
					
					</ul>
					<h2>Новые возможности при работе с "Эком:Поликлиника":</h2>
					<ul>
						<li>1. При не явке пациента можно оформить не явку и освободить время приема</li>
						
					</ul>
				</div>
			</tr>

			
		</table>
	</tiles:put>
</tiles:insert>
