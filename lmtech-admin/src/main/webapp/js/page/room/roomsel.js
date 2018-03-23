var _floorSelClass = "floor-sel";
var _currClass = "curr";
var _emptyRoomClass = "empty-room";
var _tableContainer = $("table.table-roomsel");

function setRoomType(roomTypeId, roomIds, callback) {
    top.requestData({
        url: _baseUrl + "/room/setroomtype.do",
        method: "post",
        data: JSON.stringify({ roomTypeId: roomTypeId, roomIds: roomIds }),
        dataType: "json",
        contentType: "application/json",
        success: callback,
        error: function (e) {
            top.alertMessage("设置房间户型出现未知异常。");
        }
    });
}

function isFloorSelect(tr) {
    var result = true;
    tr.find("td").each(function() {
        var td = $(this);
        if (!td.hasClass(_floorSelClass) && !td.hasClass(_currClass) && !td.hasClass(_emptyRoomClass)) {
            result = false;
            //退出循环
            return false;
        }
    });
    return result;
}

function isRoomSelect(unit_idx, room_idx) {
    var result = true;
    getRoomsByIndex(unit_idx, room_idx).each(function() {
        var td = $(this);
        if (!td.hasClass(_currClass)) {
            result = false;
            //退出循环
            return false;
        }
    });
    return result;
}

function getRoomsByIndex(unit_idx, room_idx) {
    return _tableContainer.find("tr td[room_idx='" + room_idx + "'][unit_idx='" + unit_idx + "']").not(".empty-room");
}

function disableInit() {
    _tableContainer.on('selectstart.disableTextSelect', function () {
        return false;
    });
}

disableInit();

//绑定行选择事件
_tableContainer.find("tr td").on("click", function() {
    var td = $(this);
    if (td.hasClass(_floorSelClass)) {
        //选择行
        if (isFloorSelect(td.parent())) {
            td.parent().find("td").not("."  + _floorSelClass).removeClass(_currClass);
        } else {
            td.parent().find("td").not("." + _floorSelClass).addClass(_currClass);
        }
        td.parent().find("." + _emptyRoomClass).removeClass(_currClass);
    } else {
        //选择单个
        if (td.hasClass(_currClass)) {
            td.removeClass(_currClass);
        } else {
            $(this).addClass(_currClass);
        }

        if (td.hasClass(_emptyRoomClass)) {
            td.removeClass(_currClass);
        }
    }
});

//绑定列选择事件
_tableContainer.find("tr th.room-sel").on("click", function() {
    var th = $(this);
    var room_idx = th.attr("room_idx");
    var unit_idx = th.attr("unit_idx");
    if (isRoomSelect(unit_idx, room_idx)) {
        getRoomsByIndex(unit_idx, room_idx).removeClass(_currClass);
    } else {
        getRoomsByIndex(unit_idx, room_idx).addClass(_currClass);
    }
});

_tableContainer.contextMenu({
    menuId: 'roomsel_contextMenu',
    onContextMenuItemSelected: function (menuItemId, $triggerElement, $target) {
        var id = $target.attr("pid");
        var name = $target.text();

        var roomIds = [];
        _tableContainer.find("tr td." + _currClass).each(function () {
            var rid = $(this).attr("rid");
            roomIds[roomIds.length] = rid;
        });

        setRoomType(id, roomIds, function (exeResult) {
            if (exeResult.success) {
                _tableContainer.find("tr td." + _currClass).text(name);
                _tableContainer.find("tr td." + _currClass).removeClass(_currClass);
                top.showShortMessage({
                    text: "设置户型成功！"
                });
            } else {
                top.alertMessage(exeResult.message ? exeResult.message : "设置户型失败！");
            }
        });
    },
    onContextMenuShow: function ($triggerElement, $target) {
        return $target.hasClass(_currClass);
    },
    showRangeIsWindow: true
});