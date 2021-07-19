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
                        <% %>
                        <h1>Июль 2021 года</h1>
                        <ul>
                            <li></li>
                            <li>
                                - Функционал обновления кодов промеда в рабочей функции врача
                            </li>
                            <li>
                                - В персональную рабочую функцию добавлено поле "не может быть лечащим врачом". Если
                                отмечено, рабочая функция не будет отображаться в списке специалистов при создании СЛО
                            </li>
                            <li>
                                - Функционал настройки заведующих (начальников отделений):
                            </li>
                            <li></li>
                            <ul>
                                <li>
                                    - Добавлена кнопка Сделать зав. на страницу вывода рабочих функций (Персона ->
                                    Рабочие функции)
                                </li>
                                <li>
                                    - Добавлена кнопка Сделать зав. на страницу вывода рабочих функций (Настройки ->
                                    Пользователи -> Пользователь -> Рабочие функции)
                                </li>
                                <li>
                                    - Добавлен чекбокс Сделать зав. на страницу быстрого добавления рабочих функций
                                    (Персона -> Добавить раб. ф-ю)
                                </li>
                                <li>
                                    - Во всех выше перечисленных случаях отметка о Завед. у неактуального начальника
                                    снимается автоматически
                                </li>
                            </ul>
                            <li>
                                - Отметки о вакцинации против COVID-19 в ковидной карте:
                            </li>
                            <li></li>
                            <ul>
                                <li>
                                    - Добавлен отбор Да-Нет по признаку Была ли проведена вакцинация
                                </li>
                                <li>
                                    - Если была, то становятся доступными отбора по I-му компоненту, II-му компоненту и
                                    ревакцинации
                                </li>
                                <li>
                                    - Если выбрано "Да", то становится доступной для заполнения (но не обязательной)
                                    дата вакцинации
                                </li>
                                <li>
                                    - Если вакцинация отмечена, создаётся соответствующий браслет
                                </li>
                                <li>
                                    - Если уже сделан следующий этап вакцинации, браслеты предыдущих этапов не создаются
                                </li>
                            </ul>
                        </ul>
                    </div>
            </tr>
        </table>
    </tiles:put>
</tiles:insert>