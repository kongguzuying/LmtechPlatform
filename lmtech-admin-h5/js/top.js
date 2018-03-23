bootbox.addLocale("local", {
    OK : '确定',
    CANCEL : '取消',
    CONFIRM : '确定'
});
bootbox.setLocale("local");
/**
 * 弹出顶层消息框
 * @param text
 */
function alertMessage(text, callback) {
	text = ($.trim(text))?$.trim(text):"未知异常";
    bootbox.alert(text, callback);
}
/**
 * 弹出顶层交互消息
 * @param text
 */
function prompt(text, callback) {
	bootbox.prompt(text, callback);
}
/**
 * 弹出顶层确认对话框
 * @param text
 * @param callback
 */
function confirmMessage(text, callback) {
    bootbox.confirm(text, callback);
}
/**
 * 显示一个对话框
 */
function dialog(opts){
	bootbox.dialog(opts);
}
/**
 * 显示简短消息，经过一定延时后会消失
 * @param options
 */
function showShortMessage(options) {
    var delay = options.delay ? options.delay : 1500;
    var text = options.text;
    var callback = options.callback;
    var type = options.type;

    var typeClass = "alert-success";
    if (type == "info") {
        typeClass = "alert-success";
    } else if (type == "warn") {
        typeClass = "alert-warning";
    } else if (type == "error") {
        typeClass = "alert-danger";
    }

    $.isLoading({
        text: "<div class='short-message-text'><span class='glyphicon glyphicon-info-sign'></span>" + text + "</div>",
        tpl: '<div class="alert ' + typeClass + ' short-message %wrapper%">%text%</div>'
    });
    $(".short-message").parent().css("background", "none");

    setTimeout(function () {
        $.isLoading("hide");
        if (callback) {
            callback();
        }
    }, delay);
}
/**
 * 显示对话框
 * @param options
 * @returns {*}
 */
function showDialog(options) {
    var url = options.url ? options.url : '';
    var title = options.title;
    var opener = options.opener ? options.opener : null;
    var width = options.width ? options.width : 400;
    var height = options.height ? options.height : 300;
    var showBtn = options.showBtn ? options.showBtn : true;
    var refreshFinish = options.refreshFinish ? options.refreshFinish : true;
    var okFunc = options.okFunc ? options.okFunc : null;
    var cancelFunc = options.cancelFunc ? options.cancelFunc : null;
    var shown = options.shown ? options.shown : null;
    var showFooter = typeof(options.showFooter) != "undefined" ? options.showFooter : true;
    var onFinished = options.onFinished ? options.onFinished : null;
    var onBeforeHide = options.onBeforeHide ? options.onBeforeHide : null;

    var modalOptions = {
        url: url,
        title: title,
        opener: opener,
        width: width,
        height: height,
        showBtn: showBtn,
        refreshFinish: refreshFinish,
        okFunc: okFunc,
        cancelFunc: cancelFunc,
        shown: shown,
        showFooter: showFooter,
        onFinished: onFinished,
        onBeforeHide: onBeforeHide
    };

    $("#edit-dialog").dialog("open");
}