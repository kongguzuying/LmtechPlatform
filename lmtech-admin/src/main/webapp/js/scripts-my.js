$(function () {
    //$("html").niceScroll({styler:"fb",cursorcolor:"#65cea7", cursorwidth: '6', cursorborderradius: '0px', background: '#424f63', spacebarenabled:false, cursorborder: '0',  zindex: '1000'});
});

/**
 * 分页点击事件
 * @param pageIndex
 */
function onPageClick(pageIndex) {
    $("#pageIndex").val(pageIndex);

    if (typeof onPageIndexClick != 'undefined') {
        onPageIndexClick(pageIndex);
    } else {
        $("form").submit();
    }
}

/**
 * 点击搜索按钮
 */
function submitSearch() {
    $("#pageIndex").val(1);

    $("form").submit();
}

/**
 * 提交表单
 */
function submitForm() {
    if (typeof beforeSubmit != "undefined") {
        beforeSubmit();
    }
    $("form").submit();
}

/**
 * 异步提交表单
 * @param options
 */
function submitAjaxForm(options) {
    var message = options && options.message ? options.message : "提交成功！";
    var refreshFinish = options && options.refreshFinish ? options.refreshFinish : true;

    $("form").ajaxForm(function (exeResult) {
        if (exeResult) {
            if (exeResult.success) {
                alert(message);
                if (refreshFinish) {
                   window.location.reload();
                }
            } else {
                if (exeResult.errMsg) {
                    alert(exeResult.errMsg);
                } else {
                    alert("提交出错！");
                }
            }
        } else {
            alert("提交过程中出现未知错误。");
        }
    });

    if (typeof beforeSubmit != "undefined") {
        beforeSubmit();
    }
    $("form").submit();
}

/**
 * 列表删除
 * @param url
 * @param opener
 */
function listRemove(url,text) {
	if(top.confirmMessage){
		top.confirmMessage("确定"+ (text ? text : "删除") + "该条数据？",function(re){
			if(re){
				window.location.href = url;
			}
		});
	}else if(confirmMessage){
		confirmMessage("确定"+ (text ? text : "删除") + "该条数据？",function(re){
			if(re){
				window.location.href = url;
			}
		});
	}else if (window.confirm("确定"+ (text ? text : "删除") + "该条数据？")) {
        window.location.href = url;
    }
}
/**
 * 显示全局加载框
 * @param text
 */
function showLoading(text) {
    $.isLoading({ text: text ? text : "Loading..." });
}
/**
 * 显示带遮罩的加载框
 * @param elemId
 * @param text
 */
function showLoadingOverlay(selector, text) {
    $(selector).isLoading({
        text: text ? text : "",
        position:   "overlay"
    });
}
/**
 * 显示外部展示的加载框
 * @param elemId
 * @param text
 * @param disableOthers
 */
function showLoadingOutside(selector, text, disableOthers) {
    $(selector).isLoading({
        text: text ? text : "",
        disableOthers: disableOthers ? disableOthers : []
    });
}
/**
 * 显示内部展示的加载框
 * @param elemId
 * @param text
 * @param disableOthers
 */
function showLoadingInside(selector, text, disableOthers) {
    $(selector).isLoading({
        text: text ? text : "",
        position:   "inside",
        disableOthers: disableOthers ? disableOthers : []
    });
}
/**
 * 隐藏加载框
 * @param selector
 */
function hideLoading(selector) {
    if (selector) {
        $(selector).isLoading("hide");
    } else {
        $.isLoading("hide");
    }
}
/**
 * String 类型占位符
 * @returns {String}
 */
String.prototype.format = function(){  
  if(arguments.length==0) return this;
  for(var s=this, i=0; i<arguments.length; i++){  
    s=s.replace(new RegExp("\\{"+i+"\\}","g"), arguments[i]);  
  }
  return s;  
}

/**
 * 判断id
 */
function isIE() { //ie?  
    if (!!window.ActiveXObject || "ActiveXObject" in window){
        return true;    	
    }else{
    	return false;
    }
}
/**
 * 数组内 json属性排序
 * @param order 排序方式
 * @param sortBy json对象属性
 * @returns {Function}
 */
function getSortFun(order, sortBy) {
    var ordAlpah = (order == 'asc') ? '>' : '<';
    var sortFun = new Function('a', 'b', 'return a.' + sortBy + ordAlpah + 'b.' + sortBy + '?1:-1');
    return sortFun;
}