/**
 * 初始化原图
 */
function initSourceImage(url) {
    $sourceImg = new jQuery('<img class="j_source_img" src="' + url + '" style="display:none;" >');
    $drawContainer.append($sourceImg);
    //原图加载完成后处理div背景图片
    $sourceImg.on('load', function () {
        var _sourceImgSize = {width: $sourceImg.width(), height: $sourceImg.height()};
        console.error(_sourceImgSize);
    });
}


//获取图片大小
function getImgNaturalStyle(img, callback) {
	var nWidth, nHeight
	if (img.naturalWidth) { // 现代浏览器
		nWidth = img.naturalWidth;
		nHeight = img.naturalHeight;
	} else { // IE6/7/8
		var imgae = new Image();
		image.src = img.src;
		image.onload = function() {
			callback(image.width, image.height)
		}
	}
	return [ nWidth, nHeight ]
}
//缩放比例
var WH_RATE = 0;
function getWH(){
	var img = document.getElementsByTagName('img')[0], 
	imgNatural = getImgNaturalStyle(img);
	//原始图片宽度
	var imgW = imgNatural[0];
	var imgH = imgNatural[1];
	//容器宽度
	var containerW = $("#container").width();
	var containerH = $("#container").height();
	WH_RATE = containerW / imgW;
	//如果宽度
	if(WH_RATE > (containerH  / imgH)){
		WH_RATE = (containerH / imgH);
	}
	return {w:(imgW * WH_RATE),h:(imgH * WH_RATE)};
}


//旋转图片
function transformImg(num) {
	if (points && points.length > 3) {
		$("#graphiContainer").css("transform", "rotate(" + num + "deg)");
	} else if (points != null) {
		if(isRange()){
			showMsg("最少选择4个点,才能旋转图片");
		}
	}
}

function showMsg(msg){
	$("#resultMsg").text(msg);
}

function cleanMsg(){
	$("#resultMsg").text("");
}
function initMouseEvent(){
	$('#graphiContainer')
    .mousewheel(function(event, delta) {
        loghandle(event, delta);
        var num = (delta > 0)?(delta + 4):(delta - 4)
        		console.error(num);
        $this = $(this);
        var w = $this.width() + num;
        $(this).width(w);
        event.stopPropagation();
        event.preventDefault();
    });
}
function loghandle(event, delta) {
    var o = '', id = event.currentTarget.id || event.currentTarget.nodeName;

    o = '#' + id + ':';

    if (delta > 0)
        o += ' up (' + delta + ')';
    else if (delta < 0)
        o += ' down (' + delta + ')';

    if (event.deltaY > 0)
        o += ' north (' + event.deltaY + ')';
    else if (event.deltaY < 0)
        o += ' south (' + event.deltaY + ')';

    if (event.deltaX > 0)
        o += ' east (' + event.deltaX + ')';
    else if (event.deltaX < 0)
        o += ' west (' + event.deltaX + ')';

    o += ' deltaFactor (' + event.deltaFactor + ')';
	console.error(o);
    //log( o );
}

var svg, points = null, line, width, height, dragged, selected;
$(function() {
	btnSaveEvent();
	initMouseEvent();
	$("#range").ionRangeSlider({
		min : -180,
		max : 180,
		from : 0,
		type : 'single',
		step : 1,
		postfix : "°",
		prettify : false,
		hasGrid : true,
		onChange : function(obj) {
			transformImg(obj.fromNumber);
		},
		onLoad : function(obj) {
			//
		}
	});
	points = [];
	var img = document.getElementsByTagName('img')[0], imgNatural = getImgNaturalStyle(img);

	var wh = getWH();
	width = wh.w;
	height = wh.h;
	/* width = imgNatural[0];
	height = imgNatural[1]; */

	var dragged = null, selected = points[0];

	line = d3.svg.line();

	line.interpolate("linear-closed");

	var url = '${url}';
	svg = d3
			.select(".graphiContainer")
			.append("svg")
			.attr("width", width)
			.attr("height", height)
			.attr("tabindex", 1)
			.attr(
					"style",
					"background-image:url("+url+");background-size: 100% 100%;");

	svg.append("rect").attr("width", width).attr("height", height).on(
			"mousedown", mousedown);

	svg.append("path").datum(points).attr("class", "line").call(redraw);

	d3.select(window).on("mousemove", mousemove).on("mouseup", mouseup).on(
			"keydown", keydown);

	d3.select("#interpolate").on("change", change).selectAll("option")
			.data(
					[ "linear", "linear-closed", "step-before",
							"step-after", "basis", "basis-open",
							"basis-closed", "cardinal", "cardinal-open",
							"cardinal-closed", "monotone" ]).enter()
			.append("option").attr("value", function(d) {
				return d;
			}).text(function(d) {
				return d;
			});

	svg.node().focus();

	$("#graphiContainer").width(width);
	$("#graphiContainer").height(height);
});
function redraw() {
	svg.select("path").attr("d", line);

	var circle = svg.selectAll("circle").data(points, function(d) {
		return d;
	});

	circle.enter().append("circle").attr("r", 1e-6).on("mousedown",
			function(d) {
				selected = dragged = d;
				redraw();
			}).transition().duration(750).ease("elastic").attr("r", 6.5);

	circle.classed("selected", function(d) {
		return d === selected;
	}).attr("cx", function(d) {
		return d[0];
	}).attr("cy", function(d) {
		return d[1];
	});

	circle.exit().remove();

	if (d3.event) {
		d3.event.preventDefault();
		d3.event.stopPropagation();
	}
}

function change() {
	line.interpolate(this.value);
	redraw();
}

//旋转角度
function rangeNum(num){
	$("#range").ionRangeSlider("update",{
		from:num
	});
}

function mousedown() {
	if (isRange()) {
		$("#graphiContainer").css("transform", "rotate(0deg)");
		rangeNum(0);
		cleanMsg();
	}else{
		points.push(selected = dragged = d3.mouse(svg.node()));
		redraw();
	}
}
/**
 * 是否已经旋转
 **/
function isRange() {
	var val = $("#range").val();
	return (val != 0);
}

function mousemove() {
	if (!dragged)
		return;
	var m = d3.mouse(svg.node());
	dragged[0] = Math.max(0, Math.min(width, m[0]));
	dragged[1] = Math.max(0, Math.min(height, m[1]));
	redraw();
}

function mouseup() {
	if (!dragged)
		return;
	mousemove();
	dragged = null;
}

function keydown() {
	//console.error(d3.event.keyCode);
	if (!selected)
		return;
	switch (d3.event.keyCode) {
		case 27: // backspace
		case 46: { // delete
			var i = points.indexOf(selected);
			points.splice(i, 1);
			selected = points.length ? points[i > 0 ? i - 1 : 0] : null;
			redraw();
			break;
		}
		case 37:{
			//左旋转角度
			var angle = parseInt($("#range").val());
			if(angle > -180 ){
				rangeNum((angle-1));
			}
			break;
		}
		case 39:{
			//右旋转角度
			var angle = parseInt($("#range").val());
			if(angle < 180 ){
				rangeNum((angle+1));
			}
			break;
		}
	}
}

function btnSaveEvent(){
	$("#btnSave").click(function(){
		if (!points || points.length < 4) {
			showMsg("最少选择4个点,才能保存截图");
			return false;
		}
		var url = "${pageContext.request.contextPath}/graphi/doGraphi.do";
		//图片bucket
		var imageUrl = $("#url").val();
		//guid
		var guid = $("#guid").val();
		//旋转角度
		var angle = parseInt($("#range").val());
		var data = buildXY(points);
		data.angle = angle;
		//留白
		data.margin = 10;
		data.url = imageUrl;
		data.guid = guid;
		//console.error(data);
		 $.ajax({
                url: url,
                method: "POST",
                data: JSON.stringify(data),
                dataType: "json",
                contentType: "application/json",
                success: function (res) {
                	callback(res);
                },
                error:function(XMLHttpRequest, textStatus, errorThrown){
                	console.error(textStatus+":"+errorThrown);
                }	               
            });
	});
}

/**
	调用主页面的回调函数
**/
function callback(res){
	var resultMsg;
	if(res.flag){
		//location.reload();
		resultMsg = "截图保存成功";
	}else{
		resultMsg = res.msg;
	}
	$("#resultMsg").text(resultMsg);
}

function buildXY(points){
	var data = {pointXs:[], pointYs:[]};
	for(var i = 0 ; i < points.length ; i ++){
		var arr = points[i];
		//console.error(arr);
		//处理偏移量
		var x = arr[0] / WH_RATE;
		var y = arr[1] / WH_RATE;
		data.pointXs.push(x);
		data.pointYs.push(y);
	}
	//console.error(data);
	return data;
}