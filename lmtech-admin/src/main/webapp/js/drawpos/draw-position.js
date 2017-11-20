var _initData = null;//初始数据
var _validateData = true;
var $drawContainer = $('#drawPositionContainer');
var $drawPositionArea = $('#drawPositionArea');
var $position_contextMenu_Container = $('#position_contextMenu_Container');
var _drawContainerSize = {width: $drawPositionArea.width(), height: $drawPositionArea.height()};
var $sourceImg = null;
var _sourceImgSize = {};//原图大小，在图片加载完成后处理
var $backgroundImg = null;
var _backgroundImgSize = {};//背景图大小
var _backgroundImgPosition = null;
var _isZoom = false;
var _zoomPercent = 1;
var _jPositionClass = 'j_rectangle';//绘制矩形JS使用样式
var _positionClass = 'draw-rect';
var _positionCurrClass = 'curr';
var _jPositionRemoveClass = 'j_rectangle_remove';
var _jPositionTextClass = 'j_p_text';
var _positionTextClass = 'draw-text';
var _isMozilla = /firefox/.test(navigator.userAgent.toLowerCase());//判断浏览器是否为火狐
var _isMSIE = /msie/.test(navigator.userAgent.toLowerCase());//判断浏览器是否为IE
var _rectangle = null;//当前绘制的矩形
var _startPos = {x: 0, y: 0};//鼠标起始坐标 在onmousedown事件中记录
var $curRectangle = null;

/**
 * 绑定事件
 */
function bindEvent() {
    initSourceImage();
    //绑定右键菜单
    $drawPositionArea.contextMenu({
        menuId: 'position_contextMenu',
        onContextMenuItemSelected: function (menuItemId, $triggerElement, $target) {
            var mode = $target.attr('mode');
            if (mode === 'set') {
                $curRectangle.attr('pid', $target.attr('pid'));
                var $tempText = $curRectangle.find('.' + _jPositionTextClass + ':first');
                if ($tempText.length > 0) {
                    $tempText.text($target.text());
                } else {
                    $curRectangle.append('<p class="' + _jPositionTextClass + ' ' + _positionTextClass + '">' + $target.text() + '</p>')
                }
            }
        },
        onContextMenuShow: function ($triggerElement, $target) {
            var tempJRoom = $target.closest('.' + _jPositionClass);
            var isShow = tempJRoom.length > 0;
            $curRectangle = isShow ? tempJRoom : null;
            if ($curRectangle !== null) {
                $curRectangle.addClass(_positionCurrClass).siblings('.' + _positionCurrClass).removeClass(_positionCurrClass);
            }
            return isShow;
        },
        showRangeIsWindow: true
    });
    $drawContainer.on('click', '.' + _jPositionRemoveClass, function () {
        $(this).parent().remove();
    });
}
/**
 * 禁用相关事件及属性
 */
function disableInit() {
    if (_isMozilla) {
        $drawContainer.css({'-moz-user-select': 'none'});
    } else if (_isMSIE) {
        $drawContainer.on('selectstart.disableTextSelect', function () {
            return false;
        });
        //disable dragstart
        $('img').on('dragstart', function () {
            return false;
        });
    } else {
        $drawContainer.on('mousedown.disableTextSelect', function () {
            return false;
        });
    }
}
/**
 * 初始化原图
 */
function initSourceImage() {
    $sourceImg = new jQuery('<img class="j_source_img" src="' + $drawContainer.attr('img_src') + '" style="display:none;" >');
    $drawContainer.append($sourceImg);
    //原图加载完成后处理div背景图片
    $sourceImg.on('load', function () {
        _sourceImgSize = {width: $sourceImg.width(), height: $sourceImg.height()};
        _backgroundImgSize = {width: _sourceImgSize.width, height: _sourceImgSize.height};
        _isZoom = _sourceImgSize.width > _drawContainerSize.width || _sourceImgSize.height > _drawContainerSize.height;
        initBackgroundImage();
    });
}
/**
 * 初始化背景图
 */
function initBackgroundImage() {
    $backgroundImg = new jQuery('<img src="' + $drawContainer.attr('img_src') + '" >');
    //style="z-index:0;position:absolute;top:0;left:0;"
    if (_sourceImgSize.width >= _drawContainerSize.width || _sourceImgSize.height > _drawContainerSize.height) {
        var x_scale = _sourceImgSize.width / _drawContainerSize.width;
        var y_scale = _sourceImgSize.height / _drawContainerSize.height;
        _zoomPercent = x_scale >= y_scale ? x_scale : y_scale;
        _backgroundImgSize.width = _sourceImgSize.width / _zoomPercent;
        _backgroundImgSize.height = _sourceImgSize.height / _zoomPercent;
        $backgroundImg.css({
            'width': _backgroundImgSize.width + 'px',
            'height': _backgroundImgSize.height + 'px'
        });
    }
    $backgroundImg.on('load', function () {
        initDrawControl();
        loadDefaultPosition();
    });
    $drawContainer.append($backgroundImg);
}
/**
 * 初始化绘制控件
 */
function initDrawControl() {
    var tempOffset = $backgroundImg.offset();
    _backgroundImgPosition = {
        x: Math.ceil(tempOffset.left),
        y: Math.ceil(tempOffset.top),
        w: _backgroundImgSize.width,
        h: _backgroundImgSize.height
    };
    //禁用浏览器相关事件
    disableInit();
    $drawPositionArea.on('mousedown', function (e) {
        if (e.which !== 3) {
            $drawPositionArea.data('contextMenu').hideMenu();
            $drawPositionArea.find('.' + _jPositionClass).removeClass(_positionCurrClass);
        }
        if (_rectangle != null) {
            return false;
        }
        if (e.which === 1) {
            $drawPositionArea.css({'cursor': 'crosshair'});
            //create rectangle
            _rectangle = new jQuery('<div class="' + _positionClass + '"><i></i></div>');
            _rectangle.css({'top': e.pageY, 'left': e.pageX});
            _startPos.x = e.pageX;
            _startPos.y = e.pageY;
            _rectangle.appendTo($drawPositionArea);
        }
        return true;
    });
    $drawPositionArea.on('mousemove', function (e) {
        if (_rectangle === null)
            return false;
        var top = e.pageY - _startPos.y;
        var left = e.pageX - _startPos.x;
        _rectangle.css({
            'height': Math.abs(top),
            'top': top < 0 ? e.pageY : _startPos.y,
            'width': Math.abs(left),
            'left': left < 0 ? e.pageX : _startPos.x
        });
    });
    $drawPositionArea.on('mouseup', function (e) {
        $drawPositionArea.css({'cursor': 'default'});
        if (_rectangle === null)
            return true;
        var result = true;
        var p1 = getRectanglePosition(_rectangle);
        //超出图片范围，则取消矩形
        if (p1.x < _backgroundImgPosition.x || p1.x + p1.w > _backgroundImgPosition.x + _backgroundImgPosition.w || p1.w < 15 || p1.h < 15) {
            _rectangle.remove();
            _rectangle = null;
            return true;
        }
        $drawPositionArea.find('.' + _jPositionClass).each(function (i, item) {
            var p2 = getRectanglePosition(item);
            if (compareRectanglePosition(p1, p2) === false || compareRectanglePosition(p2, p1) === false) {
                result = false;
                return result;
            }
        });
        if (result) {
            _rectangle.addClass(_jPositionClass);
            _rectangle.append('<span class="fonticon fonticon-remove fa fa-times ' + _jPositionRemoveClass + '"></span>');
        } else {
            _rectangle.remove();
        }
        _rectangle = null;
    });
}

/**
 * 比较位置
 * @param p1
 * @param p2
 * @returns {boolean}
 */
function compareRectanglePosition(p1, p2) {
    var leftTopPoint = p1.x >= p2.x && p1.x <= p2.x + p2.w && p1.y >= p2.y && p1.y <= p2.y + p2.h;
    var rigthTopPoint = p1.x + p1.w >= p2.x && p1.x + p1.w <= p2.x + p2.w && p1.y >= p2.y && p1.y <= p2.y + p2.h;
    var leftButtomPoint = p1.x >= p2.x && p1.x <= p2.x + p2.w && p1.y + p1.h >= p2.y && p1.y + p1.h <= p2.y + p2.h;
    var rightButtomPoint = p1.x + p1.w >= p2.x && p1.x + p1.w <= p2.x + p2.w && p1.y + p1.h >= p2.y && p1.y + p1.h <= p2.y + p2.h;
    var cover = p1.x >= p2.x && p1.x + p1.w >= p2.x && p1.x <= p2.x + p2.w && p1.x + p1.w <= p2.x + p2.w && p1.y <= p2.y && p1.y + p1.h >= p2.y;
    if (leftTopPoint || rigthTopPoint || leftButtomPoint || rightButtomPoint || cover) {
        return false;
    }
    return true;
}

/**
 * 获取矩形框位置
 * @param rectangle
 * @returns {{x: *, y: Window, w: *, h: *}}
 */
function getRectanglePosition(rectangle) {
    var _this = $(rectangle);
    return {x: _this.position().left, y: _this.position().top, w: _this.outerWidth(), h: _this.outerHeight()};
}

function getRectangleInSourcePosition(rectangle) {
    var _this = $(rectangle);
    var tp = getRectanglePosition(_this);
    tp.x = (tp.x - _backgroundImgPosition.x) * _zoomPercent;
    tp.y = (tp.y - _backgroundImgPosition.y) * _zoomPercent;
    tp.w = tp.w * _zoomPercent;
    tp.h = tp.h * _zoomPercent;

    tp.x = tp.x < 0 ? 0 : tp.x;
    tp.y = tp.y < 0 ? 0 : tp.y;
    return tp;
}

/**
 * 校验部位设置
 */
function validatePositionSet() {
    var invelid_1 = $drawContainer.find('.' + _jPositionClass + ':not([pid])');
    var invelid_2 = $drawContainer.find('.' + _jPositionClass + '[pid=""]');
    var invalid = false;
    if (invelid_1.length > 0) {
        invalid = true;
        $(invelid_1.eq(0)).addClass(_positionCurrClass);
    } else if (invelid_2.length > 0) {
        $(invelid_2.eq(0)).addClass(_positionCurrClass);
        invalid = true;
    }
    invalid && showMessage('请在红框内鼠标右键选择部位', false);
    return !invalid;
}

/**
 * 消息提示
 * @param message
 * @param isNormal
 */
function showMessage(message, isNormal) {
    top.alertMessage(message);
}

/**
 * 获取相对原图的点
 */
function getSourceImgPosition() {
    var result = [];
    $drawContainer.find('.' + _jPositionClass).each(function (i, item) {
        var _this = $(item);
        var data = getRectangleInSourcePosition(_this);
        data.pid = _this.attr('pid') ? _this.attr('pid') : '';
        data.id = _this.attr('did') ? _this.attr('did') : '';
        result.push(data);
    });
    return result;
}

/**
 * 保存部位信息
 * @returns {boolean}
 */
function savePosition() {
    $drawContainer.find('.' + _jPositionClass).removeClass(_positionCurrClass);
    if (_validateData === false) {
        return false;
    }
    if (validatePositionSet() === false) {
        return false;
    }
    var postData = {
        roomtype_id: _initData.roomtype_id,
        diagram_id: _initData.diagram_id,
        roomTypeFloorId: _initData.roomTypeFloorId,
        pos: getSourceImgPosition()
    };
    $.ajax({
        type: 'post',
        url: _savePositionURL,
        async: false,
        data: JSON.stringify(postData),
        dataType: "json",
        contentType: "application/json",
        success: function (exeResult) {
            if (exeResult.success) {
                showMessage(exeResult.message ? exeResult.message : "保存成功！");
                top.hideModal();
            } else {
                showMessage(exeResult.message ? exeResult.message : "保存失败！");
            }

        },
        error: function (e) {
            showMessage("提交过程中出现未知错误。");
        }
    });
}

/**
 * 加载部位菜单
 */
function loadPositionMenu() {
    if (_validateData && _initData.pos.roomTypePosition) {
        var menuArray = [];
        $(_initData.pos.roomTypePosition).each(function (i, item) {
            menuArray.push('<li><a pid="' + item.id + '" href="javascript:void(0);" mode="set">' + item.position + '</a></li>')
        });
        $position_contextMenu_Container.html('');
        $position_contextMenu_Container.append(menuArray.join(''));
    }
}

function loadDefaultPosition() {
    if (_validateData && _initData.pos.diagramPosition) {
        var tempArray = [];
        $(_initData.pos.diagramPosition).each(function (i, item) {
            var y = (item.coordinate.y / _zoomPercent) + (_backgroundImgPosition.y);
            var x = (item.coordinate.x / _zoomPercent) + (_backgroundImgPosition.x);
            var w = (item.coordinate.width / _zoomPercent);
            var h = (item.coordinate.height / _zoomPercent);
            tempArray.push('<div style=" top: ' + y + 'px; left: ' + x + 'px; height: ' + h + 'px; width: ' + w + 'px;" class="' + _jPositionClass + ' ' + _positionClass + '" pid="' + item.position_id + '" did="' + item.id + '"><i></i><p class="' + _jPositionTextClass + ' ' + _positionTextClass + '">' + item.position_name + '</p><span class="fonticon fonticon-remove fa fa-times ' + _jPositionRemoveClass + '"></span></div>');
        });
        $drawPositionArea.append(tempArray.join(''));
    }
}

//初始化
function init(initData) {
    _initData = initData;
    validateInitData();
    loadPositionMenu();
    bindEvent();
}

/**
 * 校验初始数据
 */
function validateInitData() {
    var invalidate = _initData === null;
    invalidate && showMessage('参数无效', false);
    invalidate = _initData.img_url === null;
    invalidate && showMessage('请先上传户型图', false);
    invalidate = _initData.pos === null;
    invalidate && showMessage('请先设置检查部位', false);
    _validateData = !invalidate;
}