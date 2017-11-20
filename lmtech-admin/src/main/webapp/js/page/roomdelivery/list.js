function setStatus(elem) {
    $("#cpStatus").val($(elem).attr("status"));
    $("form").submit();
}
function view(id) {
    top.showModal({
        title: "交付详情",
        width: 900,
        height: 620,
        url: URL_BASE + "/roomdelivery/edit.do?id=" + id,
        showFooter: false
    });
}
function tempView(url, tempName) {
    top.showModal({
        title: tempName,
        width: 900,
        height: 620,
        url: url,
        showFooter: false
    });

}

$(function () {
    //设置状态
    var status = $("#cpStatus").val();
    $("button[status='" + status + "']").addClass("curr");

    initAutoComplete({
        url: URL_BASE + "/project/projectphaselist.do",
        selectId: "projectNameInput",
        onSelected: function (id, text) {
            var ids = id.split("|");
            $("#projectId").val(ids[0]);
            $("#phaseId").val(ids[1]);
            $('#projectName').val(text);
            $("form").submit();
        }
    });

    initAutoComplete({
        url: URL_BASE + "/room/roomautocomp.do",
        selectId: "roomName",
        data: {projectId: $("#projectId").val(), phaseId: $("#phaseId").val(), unitId: $("#unitId").val()},
        onSelected: function (id, text) {
            $("#roomId").val(id);
            $("form").submit();
        }
    });

    $('#roomName').blur(function () {
        this.value = "";
        $("#roomId").val("");
    });

    var _status = $("#status").val();

    var maxfilesize = 50;
    var _url_001 = URL_BASE + "/checkitem/getMaxFileSize.do?time=" + new Date().getTime();
    $.ajax({
        url: _url_001,
        async: false,
        success: function (data) {
            maxfilesize = data;
        }
    });

    var myDropzone = new Dropzone("div#fileUpload", {

        url: URL_BASE + "/roomdelivery/importRoomDeliveryData.do",
        method: "post",
        maxFilesize: maxfilesize, //MB
        dictFileTooBig: "文件过大超过最大限制" + maxfilesize + "MB!",
        acceptedFiles: ".xls,.xlsx", //上传的类型
        dictInvalidFileType: "导入数据的文件,文件类型必须是.xls或.xlsx!",
        clickable: true,
        sending: function () {
            $.isLoading({text: "交付房间数据导入中，请稍候......"});
        },
        success: function (data, exeResult) {
            if (exeResult.success) {
                top.showShortMessage({text: "导入交付房间数据成功！"});
                $("form").submit();
            } else {
                top.alertMessage(exeResult.message ? exeResult.message : "上传过程出现未知错误");
            }
        },
        complete: function () {
            hideLoading();
        },
        error: function (file, text) {
            top.alertMessage(text);
            $("form").submit();
        }
    });

    $('#batchId').change(function () {
        if ($(this).val()) {
            $('#queryMark').val('batch');
        } else {
            $('#queryMark').val('');
        }
        $('#unitId').val('');
        $("form").submit();
    });

    $('#unitId').change(function () {
        $("form").submit();
    });

    $('#projectBtn').click(function () {
        var frame = top.showModal({
            url: URL_BASE + "/project/projectphaselistpage.do?modal=setProjectPharse&projectName=",
            title: "项目分期查询",
            showFooter: false,
            opener: window,
            width: 800,
            height: 600,
            onFinished: function (exeResult) {

            }
        });

    });
    
    /*$(".date").datetimepicker({
        format: "yyyy-mm-dd",
        autoclose: true,
        todayBtn: true,
        startDate: "2016-09-01",
        minuteStep: 10
    });*/
    $(".date").datetimepicker({
		 format: "yyyy-mm-dd",
		    autoclose: true,
		    todayBtn: true, 
		    minView: "month",
		    language: 'zh-CN'
    });

});

function importExcel() {
    $("div#fileUpload").click();
}

function exportExcel() {
    var _status = $("#status").val();
    var _url = URL_BASE + "/roomdelivery/getExportRoomDeliveryData.do?time=" + new Date().getTime();
    _url = _url + "&" + $('form').serialize();
    window.location.href = _url;
}

function exportNcRoomStatus() {
    var valid = validateSelectProject();
    if (valid) {
        var _url = URL_BASE + "/roomdelivery/getExportNcRoomStatus.do?time=" + new Date().getTime();
        _url = _url + "&" + $('form').serialize();
        window.location.href = _url;
    } else {
        top.showShortMessage({
            text: "请选择项目分期"
        });
    }
}

function syncNc() {
    showLoading("房间数据同步中，请稍候···");
    top.requestData({
        url: URL_BASE + "/roomdelivery/syncNcRoomDelivery.do",
        success: function (data) {
            hideLoading();
            if (data.success) {
                top.showShortMessage({ text: data.message });
            } else {
                top.alertMessage(data.message);
            }
        },
        error: function () {
            hideLoading();
        }
    });
}

function setProjectPharse(projectId, phaseId, name) {
    $("#projectId").val(projectId);
    $("#phaseId").val(phaseId);
    $('#projectName').val(name);
    $('#projectNameInput').val(name);
    $("form").submit();
}

function validateSelectProject() {
    var phaseId = $("#phaseId").val();
    if (phaseId) {
        return true;
    } else {
        return false;
    }
}
