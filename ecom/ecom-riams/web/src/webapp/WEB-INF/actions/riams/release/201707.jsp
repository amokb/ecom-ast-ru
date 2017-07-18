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
				<h1>Июль 2017 года</h1>
				
				<ul>
					<li>Убрано двоение анастезии в цене случая.</li>
					<li>В отчет по поликлинике (платные услуги) добавлен фильтр по специалисту</li>
					<li>Параметр "оплата терминалом" передается при аннулировании чека</li>
					<li>При удалении услуги из визита удаляется информация об услуге в дневнике (если она есть)</li>
					<li>Реализован функционал по получению номеров ЭЛН</li>
					<li>В отчет по услугам выходит информация об обслуживаемой персоне, а не заказчике</li>
					<li>Добавлен справочник по псих. картам по сообщениям о суицидальных попытках</li>
					<li>Доработано отображение БЛ при печати стат.карты + добавлен кл.диагноз при печати протоколов</li>
					<li>Доработано ограничение на дату выписки. При просмотре ошибки отображается кол-во заданное в параметрах (а не по умолчанию 24)</li>
					<li>Доработано ограничение на дату выписки (задается кол-во часов в параметрах)</li>
					<li>Отображение протоколов для поликлинических врачей, только при отказе от госпитализации</li>
					<li>Теперь в федеральную систему не выгружаются новорожденные дети</li>
					<li>Реализован запрет на перевод из одной реанимации в другую
						<msh:ifInRole roles="/Policy/Config/HelpAdmin">
							<br>
							Политика для пользователей:  "/Policy/Mis/MedCase/Stac/Ssl/CantTransferReanimationToReanimation"
						</msh:ifInRole>
					</li>
					<li>Добавлена возможность редактирования типа заявки для работников ТП</li>
					<li>Теперь автоматически сохраняется текст ещё не сохранённых протоколов и выписок, т.е. при сбое проделанная работа (текст протокола/выписки) не будет потеряна.</li>
					<li>Добавлена таблица для работы с журналом отправки ЭЛН</li>
					<li>Полю "имя пользователь" в шаблоне странице присвоен идентификатор "current_username_li"</li>
					<li>Доработан просмотр информации о назначения - отображается дата выполнения и подтверждения анализа</li>
					<li>При создании дневника в стационаре и в поликлинике используются разные справочники услуг (список услуг варьируется от типа случая мед. обслуживания)</li>
					<li>Исправлена ошибка при аннулировании биоматериала при приеме его в лабораторию (при наличии нескольких назначений)</li>
					<li>Исправлена проверка на обязательность диагноза при создании дневника в госпитализации</li>
					<li>Отправка запроса на экспорт ФСС выполняет МедОС, а не машина пользователя.</li>
					<li></li>
					<li>Личный кабинет пациента:</li>
					<li>Добавлена регистрация пациента, возможность выгрузки всей истории болезни пациента. Изменен алгоритм выгрузки случая амб. лечение - выгружается документ "выписка из амб. карты"</li>
					<li>Добавлены проверки на формат номера телефона и адреса электронной почты, проверки на дубли адреса и почты. Увеличено поле "телефон", "электронная почта"</li>
					<li>Работа с личным кабинетом пациента: Изменение статуса согласия при отправке запроса на сервер, получение кода пациента с сервера</li>
					<li>Убраны лишние вывода отладочной информации на экран, подправлена функция аннулирования учетной записи пациента в личном кабинете. В форме пациента отображается номер телефона, привязанный к личному кабинету.</li>
					<li></li>
				</ul>
				<h2>В рамках движения "Свободное ПО" Ткачевой Светланой сделано:</h2>
				<ul>
					<li>В отчете по 263 приказу доработана таблица по свободным койкам</li>
					<li>Доработано добавление диагноза в протоколе в зависимости от поля "Добавить диагноз"</li>
					<li>Доработана обработка сохранения протокола</li>
					
					
				</ul>
					</div>
			</tr>

			
		</table>
	</tiles:put>
</tiles:insert>