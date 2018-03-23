//var AOTUCOMPLETE_HTML = "<div class=\"autocomp-item\" id=\"{0}\">{1}</div>";
var AOTUCOMPLETE_HTML = '<ul class="typeahead dropdown-menu history-cookie" style="top: {0}px; left: {1}px;width: {2}px"><li id="{3}">{4}</li></ul>';

/**
 * 分户验收
 * @param options
 */
function initAutoCompleteHH(options) {
	if(!options.cookie){
		//console.error(top.CURRENT_USER_ID);
		options.cookie = top.CURRENT_USER_ID + "_HH_PROJECT_DATA";
	}
	initAutoComplete(options);
}

/**
 * 公共部位
 * @param options
 */
function initAutoCompletePP(options) {
	if(!options.cookie){
		//console.error(top.CURRENT_USER_ID);
		options.cookie = top.CURRENT_USER_ID + "_PP_PROJECT_DATA";
	}
	initAutoComplete(options);
}

function initAutoComplete(options) {
	//console.error(top.CURRENT_USER_ID);
    var _url = options.url;
    var _selectId = options.selectId;
    var _idAttr = options.idAttr ? options.idAttr: "id";
    var _textAttr = options.textAttr ? options.textAttr: "text";
    var _onSelected = options.onSelected ? options.onSelected: null;
    var _data = options.data ? options.data : {};
    var _cookie = options.cookie?options.cookie:top.CURRENT_USER_ID +"_PROJECT_DATA";

    var elem = $("#" + _selectId);
    buildElement(elem);
    history(elem,_onSelected,_cookie);
	elem.typeahead({
        items: 100,
        minLength: 2,
        source: function (query, process) {
        	$(".history-cookie").remove();
            _data.key = query;
            $.ajax({
                url: _url,
                method: "POST",
                data: _data,
                dataType: "json",
                success: function (exeResult) {
                    if (exeResult.success) {
                        var results = [];
                        var data = exeResult.data;
                        for (var i = 0; i < data.length; i++) {
                            results[i] = "<div class=\"autocomp-item\" id=\"" + data[i][_idAttr] + "\">" + data[i][_textAttr] + "</div>";
                        }
                        process(results);
                    } else {
                        alert(exeResult.message ? exeResult.message : "加载数据过程中出现错误");
                    }
                }
            });

        },
        highlighter: function (item) {
            return item;
        },
        updater: function (item) {
            var itemElem = $(item);
            var id= itemElem.attr("id");
            var text = itemElem.text();
            /*console.error(id);
            console.error(text);
            console.error(itemElem);*/
            if(id && id.indexOf("|") > 0){
            //	if($("#"+id).attr("isCookie")){
                setProjectData(_cookie,id,text);
            }
            elem.val(text);
            if (_onSelected) {
                _onSelected(id, text);
            }
            $(".typeahead").hide();
            return text;
        }
    });
    
}
function buildElement(elem) {
    elem.attr("autocomplete", "off");
}

function setProjectData(_cookie,id,text){
	var PROJECT_DATA = id + "__" + text;
    $.cookie(_cookie, PROJECT_DATA,{expires:365,path: '/' });
    //console.error("设置cookie" + PROJECT_DATA);
}
function getProjectData(_cookie){
	var dataStr = $.cookie(_cookie);
	if(!dataStr) return null;
	return dataStr.split("__");
}
//处理历史记录(cookie)
function history(elem,_onSelected,_cookie){
	//console.error("history.......");
	elem.focus(function(){
		//console.error("focus.......");
    	var val = $(this).val();
    	if(val || $.trim(val)){
    		return;
    	}
    	$(".typeahead").remove();
    	
    	var of = $(this).offset();
    	var h = $(this).height()-8;
    	var w = $(this).width() + 40;
    	var num= parseInt($(this).css('marginLeft'));
//    	console.error(num);
    	var data = getProjectData(_cookie);
    	if(data && data.length == 2){
        	var domHtml = AOTUCOMPLETE_HTML.format((of.top + h),(of.left-10 + num),w,data[0],data[1]);
        	//console.error(of);
        	//console.error(h);
        	$(this).after(domHtml);
        	$(".typeahead").css("display","block").find("li").click(function(){
        		//console.error(data);
        		$(this).css("display","none").hide();
        		$(this).off();
        		$(this).die();
        		$(this).remove();
        		if (_onSelected) {
                    _onSelected(data[0],data[1]);
                }
        	});
    	}
    });
}