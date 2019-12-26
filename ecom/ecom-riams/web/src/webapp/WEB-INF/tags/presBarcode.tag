<%@ tag pageEncoding="utf8" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>

<%@ attribute name="name" required="true" description="Название" %>


    <style type="text/css">
        #${name}IntakeInfoDialog {
            visibility: hidden ;
            display: none ;
            position: absolute ;
        }
    </style>

    <div id='${name}IntakeInfoDialog' class='dialog'>
        <h2>Сгенерированный штрих-код</h2>
        <div class='rootPane'>
            <form action="javascript:void(0)">
                <msh:row>
                    <svg id="${name}BarCode"></svg>
                </msh:row>
                <msh:row>
                    <input type="button" value='Закрыть' onclick='the${name}IntakeInfoDialog.hide()'/>
                </msh:row>
            </form>
        </div>
    </div>

<script type="text/javascript" src="./dwr/interface/PrescriptionService.js"></script>
    <script type="text/javascript">
        var theIs${name}IntakeInfoDialogInitialized = false ;
        var the${name}IntakeInfoDialog = new msh.widget.Dialog($('${name}IntakeInfoDialog')) ;


        function show${name}IntakeInfoDialog(aPrescriptId) {
            PrescriptionService.getPrBarCodeInfo(aPrescriptId, {
                callback : function(aResult) {
                    if (aResult!='') {
                        the${name}IntakeInfoDialog.show() ;
                        JsBarcode("#${name}BarCode", aResult, {
                            displayValue: false
                        });
                    }
                    else
                        showToastMessage('Для формирования штрих-кода необходимо, чтобы дата и время забора были непустые!',null,true,true);
                }
            });

        }
    </script>