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
 * 请求服务器数据
 * @param options
 */
function requestData(options) {
    var ajaxOptions = $.extend(true,{"contentType":"application/json","type":"POST","dataType":"json"}, options);
    ajaxOptions.success = function (data) {
        if (options.success) {
            options.success(data);
        }
    };
    ajaxOptions.error = function (e) {
        if (options.error) {
            options.error(e);
        } else {
            top.alertMessage("请求过程中出现未知错误。");
        }
    };

    $.ajax(ajaxOptions);
}
/**
 * 获取代码项文本
 * @param typeCode
 * @param codeItemValue
 */
function getCodeItemText(typeCode, codeItemValue) {
    for (var i = 0; i < top.codes.length; i++) {
        var code = top.codes[i];
        if (code.code == typeCode) {
            var codeItems = code.codeItems;
            for (var j = 0; j < codeItems.length; j++) {
                var codeItem = codeItems[j];
                if (codeItem.value == codeItemValue) {
                    return codeItem.name;
                }
            }
        }
    }
    return "";
}

function getCodeItemList(typeCode) {
    for (var i = 0; i < top.codes.length; i++) {
        var code = top.codes[i];
        if (code.code == typeCode) {
            return code.codeItems;
        }
    }
    return [];
}

function addOption(code) {
    var _option = "<option value=''></option>";
    $.each(top.getCodeItemList(code),function(){
        _option += "<option value="+ this.value +">"+ this.name +"</option>";
    });
    return _option;
}