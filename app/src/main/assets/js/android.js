var num = document.getElementById("num");

/* 原javascriptInterface方式 */
function addHtmlNum() {
    num.innerHTML++;
}

function addNativeNum() {
    if (Android != null)
        Android.addNativeNum();
}

/* webViewJavascriptBridge方式 */
function decNativeNum() {
    document.getElementById("show").innerHTML = "";
    window.WebViewJavascriptBridge.callHandler( // 调用native方法
        'decNativeNum'
        , {'param': '啊啊'}
        , function (responseData) {
            document.getElementById("show").innerHTML = "native方法调用成功:" + responseData;
        }
    );
}

function connectWebViewJavascriptBridge(callback) { // 注册事件监听
    if (window.WebViewJavascriptBridge) {
        callback(WebViewJavascriptBridge)
    } else {
        document.addEventListener(
            'WebViewJavascriptBridgeReady'
            , function () {
                callback(WebViewJavascriptBridge)
            },
            false
        );
    }
}

connectWebViewJavascriptBridge(function (bridge) { // 第一次连接时调用
    bridge.init(function (message, responseCallback) { // 初始化函数
        if (responseCallback)
            responseCallback('Javascript Responds');
    });

    bridge.registerHandler("decHtmlNum", function (data, responseCallback) { // 注册native调用方法
        if (data == '哦哦')
            num.innerHTML--;
        responseCallback("js方法调用成功:" + data);
    });
})