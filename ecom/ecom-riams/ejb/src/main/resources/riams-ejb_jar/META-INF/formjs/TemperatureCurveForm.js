
function onPreSave(aForm, aEntity, aCtx) {
    var date = new java.util.Date();
    aForm.setEditDate(Packages.ru.nuzmsh.util.format.DateFormat.formatToDate(date));
    aForm.setEditTime(new java.sql.Time(date.getTime()));
    aForm.setEditUsername(aCtx.getSessionContext().getCallerPrincipal().toString());
}

function onPreCreate(aForm, aCtx) {
    var date = new java.util.Date();
    aForm.setDate(Packages.ru.nuzmsh.util.format.DateFormat.formatToDate(date));
    aForm.setTime(new java.sql.Time(date.getTime()));
    aForm.setUsername(aCtx.getSessionContext().getCallerPrincipal().toString());
}