<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/main${param.infomat}Layout.jsp" flush="true">

    <tiles:put name="side" type="string">  
    <tags:sideGosgarant curUrl="5"/>  	
    </tiles:put>
    <tiles:put name='title' type='string'>
        <msh:title mainMenu="Lpu">Программа гос. гарантий бесплатного оказания гражданам мед. помощи на территории АО на 2015 год</msh:title>
    </tiles:put>
    <tiles:put name="style" type="string">
    <style type="text/css">
    </style>
    </tiles:put>

    <tiles:put name='body' type='string'>
    <h2 align="right">
    Приложение N 5<br/>
к
<a href="step_gosgarant_0.do?infomat=${param.infomat}">Программе</a></h2>
<h4>
Объем медицинской помощи, оказываемой в рамках Программы в соответствии с законодательством Российской Федерации об обязательном медицинском страховании
</h4>
<p>
Объемы медицинской помощи по видам, условиям и формам ее оказания в целом по ТП ОМС составляют:</p>
<ul>
<li>для скорой медицинской помощи вне медицинской организации, включая медицинскую эвакуацию, - 321 241 вызов;</li>
<li>для медицинской помощи в амбулаторных условиях, оказываемой с профилактическими и иными целями (включая посещения центров здоровья, посещения в связи с диспансеризацией, посещения среднего медицинского персонала) - 2 325 122 посещения;</li>
<li>для медицинской помощи в амбулаторных условиях, оказываемой в связи с заболеваниями, - 1 965 911 обращений;</li>
<li>для медицинской помощи в амбулаторных условиях, оказываемой в неотложной форме, - 500 126 посещения;</li>
<li>для медицинской помощи в условиях дневных стационаров - 565 630 пациенто-дня;</li>
<li>для специализированной медицинской помощи в стационарных условиях - 174 236 случаев госпитализации, в том числе для медицинской реабилитации в специализированных больницах и центрах, оказывающих медицинскую помощь по профилю "Медицинская реабилитация", и реабилитационных отделениях медицинских - 33 660 койко-дней.</li>
</ul>
<tags:timerGoMain interval="600000"/>
</tiles:put>
</tiles:insert>