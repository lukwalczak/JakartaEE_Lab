function initInputs() {
    document.querySelectorAll('.form-outline').forEach((formOutline) => {
        new mdb.Input(formOutline).init();
    });
}

function onReset() {
    setTimeout(initInputs, 0);
}

document.addEventListener("DOMContentLoaded", function(){
    initInputs();
});
