var num = document.getElementById("num");

function addHtmlNum() {
    num.innerHTML++;
}

function decHtmlNum() {
    num.innerHTML--;
}

function addNativeNum() {
    if (Android != null)
        Android.addNativeNum();
}

function decNativeNum() {
    if (Android != null)
        Android.decNativeNum();
}