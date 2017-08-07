<%@ tag pageEncoding="utf8" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>

<%@ attribute name="name" required="true" description="Название" %>


<style type="text/css">
    #AnnulDisDocument {
        visibility: hidden ;
        display: none ;
        position: absolute ;
    }
</style>

<div id='${name}AnnulDisDocumentDialog' class='dialog'>
    <h2>Выберите причину аннулирования:</h2>
    <div class='rootPane'>

        <form action="javascript:void(0) ;" id="formId">
            <table width="100%" cellspacing="10" cellpadding="10" id="table1" border="1">
                <tr>
                    <th align="center" width="150">Код</th>
                    <th align="center" width="300">Причина</th>
                </tr>
                <tr><td></td></tr>
            </table>
            <table width="100%" cellspacing="10" cellpadding="10">
                <tr><td></td></tr>
                <tr>
                    <td></td>
                    <td align="center">Комментарий:</td>
                </tr>
            </table>
            <textarea rows="8" cols="55" class="area" required id="text"></textarea>
            <table width="100%" cellspacing="10" cellpadding="10">
                <tr><td></td></tr>
                <tr>
                    <td align="center"><input  type="button" value='Аннулировать' style="font-weight:bold" id="${name}nul" onclick='javascript:${name}Document()'/></td>
                    <td align="center"><input type="button" value='Закрыть' id="${name}Cancel" onclick='javascript:cancel${name}CloseDocument()'/></td>
                </tr>
                <tr><td></td></tr>
            </table>
            <div id="text2" hidden></div>
        </form>
    </div>
</div>
<script type="text/javascript">
    var ID;
    var theIs${name}AnnulDisDocumentDialogInitialized = false ;
    var the${name}AnnulDisDocumentDialog = new msh.widget.Dialog($('${name}AnnulDisDocumentDialog')) ;
    // Показать
    function show${name}CloseDocument(id) {
        ID=id;
        showPossibleReasons();
        theTableArrow = null ;
    }
    function showPossibleReasons() {
        DisabilityService.getReasonsfOfAnnulSheets({callback: function(res) {
            if (res!="##") {
                document.getElementById("text").value="";
                the${name}AnnulDisDocumentDialog.show() ;
                var all = res.split('!') ;
                var table = document.getElementById('table1');
                table.innerHTML=" <tr><th align=\"center\" width=\"150\">Код</th><th align=\"center\" width=\"300\">Причина</th></tr><tr>";
                for (var i=0; i<all.length-1; i++) {
                    var aResult=all[i].split('#');
                    var tr = document.createElement('tr');
                    var td1 = document.createElement('td');
                    if (aResult[0]!="null") td1.innerHTML = "<div class=\"radio\"> <label id="+i+"><input type=\"radio\" name=\"optradio\">"+aResult[0]+"</label> </div>";
                    var td2 = document.createElement('td');
                    if (aResult[1]!="null") td2.innerHTML = aResult[1];
                    td1.align="center";td2.align="center";
                    tr.appendChild(td1); tr.appendChild(td2);
                    table.appendChild(tr);
                }
            }
        }
        });
    }
    // Отмена
    function cancel${name}CloseDocument() {
        the${name}AnnulDisDocumentDialog.hide() ;
        document.getElementById('annulDisSheetReasonnul').removeAttribute('hidden');
        document.getElementById("text2").setAttribute('hidden',true);
        document.getElementById("text2").value="";
    }
    // Аннулирование
    function ${name}Document() {
        var text=document.getElementById("text").value;
        var radio=document.getElementById('table1').getElementsByTagName("input");
        var code="";
        for (var i=0; i<radio.length; i++)
            if (document.getElementById('table1').getElementsByTagName("input")[i].checked==true)
                code=document.getElementById(i).textContent;
        if (code=="" || text=="") alert("Должна быть выбрана причина и написан комментарий!");
        else {
            DisabilityService.setAnnulDisabilitySheet(ID,text,code,{callback: function(res) {
                if (res.indexOf('#')!=-1) {
                    var mas = res.split('#');
                    var number=mas[0]; var snils=mas[1];
                    DisabilityService.annulDisabilityDocument(number,code,text,snils, { callback: function(a) {
                        //alert("Электронный ЛН аннулирован.");
                        //alert(a);
                        document.getElementById('text2').removeAttribute('hidden');
                        document.getElementById('text2').innerHTML = a;
                        document.getElementById("annulDisSheetReasonnul").setAttribute('hidden',true);
                    }
                    });
                }
                else alert(res);
            }
            });
        }
    }
</script>