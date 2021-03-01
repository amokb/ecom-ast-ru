<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="/WEB-INF/mis.tld" prefix="mis" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true">

    <tiles:put name='title' type='string'>
    </tiles:put>

    <tiles:put name='body' type='string'>
        <table class='mainMenu'>
            <tr>
                <td class='col1'>
                    <div class='menu'>
                        <% // Версия от 29 января %>
                        <h1>Февраль 2021 года</h1>
                        <ul>
                            <li></li>
                            <li>Изменения в выписке: можно выписывать текущим числом любых пациентов или вчерашней датой
                                умерших/пациентов не инфекционных отделений
                            </li>
                            <li>Печать адреса в направлениях на исследования</li>
                            <li>Назначение SARS-COV2 в НЕинфекционных отделениях:</li>
                            <ul>
                                <li> - номер пробирки обязателен</li>
                                <li> - планируемая дата выполнения может быть любой</li>
                                <li> - необходимо отмечать забор биоматериала</li>
                            </ul>
                            <li>Выписка и удаление данных выписки: настройка приложения по периоду, в рамках которого
                                можно выписывать и удалять выписку
                            </li>
                            <li>Перед выпиской и переводом из отделения необходимо закрывать браслеты ИВЛ</li>
                            <li>Вывод поликлинических пациентов в отчёте по прикреплённым полисам</li>
                            <li>Автоматическая проверка по базе фонда тех, у кого был приём в текущий день</li>
                            <li>Вывод в отчёт по прикрепленным полисам пациентов, у которых не прошла проверка по базе
                                фонда
                            </li>
                            <li>Вывод отделения в новую печать реестра</li>
                            <li>Регистрационный номер теперь необязателен при печати направления на ВИЧ</li>
                            <li>Механизм отметки направлений оплаченными для администраторов платных услуг</li>
                            <li>При пустых полях даты-времени окончания операции при редактировании снимается
                                отметка о закрытии прикреплённого к операции браслета (ИВЛ)</li>
                            <li>Отчёт по выполненным исследованиям для юридического отдела</li>
                            <li>Добавлена возможность настройки и создания
                                групповых рабочих функций для направления платных пациентов в лабораторию</li>
                        </ul>
                    </div>
            </tr>
        </table>
    </tiles:put>
</tiles:insert>