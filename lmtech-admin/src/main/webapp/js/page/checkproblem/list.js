$(function () {
    $('#tbCheckboxAll').change(function () {
        if (this.checked) {
            $('.tbCheckbox').each(function () {
                this.checked = true;
            });
        } else {
            $('.tbCheckbox').each(function () {
                this.checked = false;
            });
        }
    });

});

function setStatus(elem) {
    $("#status").val($(elem).attr("status"));
    $("form").submit();
}
function invalidProblem(problemId) {
    top.requestData({
        url: URL_BASE + "/checkproblem/invalid.do?id=" + problemId,
        type: "POST",
        success: function (exeResult) {
            if (exeResult.success) {
                top.showShortMessage({
                    text: "作废成功",
                    callback: function () {
                        //window.location.reload();
                        $("form").submit();
                    }
                });
            } else {
                top.alertMessage(exeResult.message);
            }
        }
    });
}
function syncProblemData() {
    showLoading("问题数据同步中，请稍候···");
    top.requestData({
        url: URL_BASE + "/checkproblem/syncproblem.do",
        success: function (exeResult) {
            hideLoading();
            if (exeResult.success) {
                top.showShortMessage({
                    text: exeResult.message,
                    callback: function () {
                        //window.location.reload();
                        $("form").submit();
                    }
                });
            } else {
                top.alertMessage(exeResult.message);
            }
        },
        error: function () {
            hideLoading();
        }
    });
}
function syncProjProblemData() {
    var projectId = $("#projectId").val();
    if (!projectId) {
        top.showShortMessage({text: "请选择项目"});
        return;
    }
    showLoading("项目问题数据同步中，请稍候···");
    top.requestData({
        url: URL_BASE + "/checkproblem/syncprojproblem.do?projectId=" + projectId,
        success: function (exeResult) {
            hideLoading();
            if (exeResult.success) {
                top.showShortMessage({
                    text: exeResult.message,
                    callback: function () {
                        $("form").submit();
                    }
                });
            } else {
                top.alertMessage(exeResult.message);
            }
        },
        error: function () {
            hideLoading();
        }
    });
}
//同步所选问题
function syncCheckedProblemData() {
    var ids = '';
    $('input[class="tbCheckbox"]:checked').each(function () {
        ids += $(this).val() + ",";
    });
    if (ids == '') {
        layer.alert(ids == '' ? '你还没有选择任何内容！' : ids);
        return ;
    }else{
        ids = ids.substring(0, ids.length - 1);
    }

    // var projectId = $("#projectId").val();
    // if (!projectId) {
    //     top.showShortMessage({ text: "请选择项目" });
    //     return;
    // }
    showLoading("问题数据同步中，请稍候···");
    top.requestData({
        url: URL_BASE + "/checkproblem/synccheckedproblem.do?ids=" + ids,
        success: function (exeResult) {
            hideLoading();
            if (exeResult.success) {
                top.showShortMessage({
                    text: exeResult.message,
                    callback: function () {
                        $("form").submit();
                    }
                });
            } else {
                top.alertMessage(exeResult.message);
            }
        },
        error: function () {
            hideLoading();
        }
    });
}
function viewProblem(id) {
    top.showModal({
        title: "问题详情",
        width: 900,
        height: 620,
        url: URL_BASE + "/checkproblem/edit.do?id=" + id,
        showFooter: false
    });
}

$(function () {
    //设置状态
    var status = $("#status").val();
    $("button[status='" + status + "']").addClass("curr");

    initAutoComplete({
        url: URL_BASE + "/project/projectphaselist.do",
        selectId: "projectNameInput",
        onSelected: function (id, text) {
            var ids = id.split("|");
            $("#projectId").val(ids[0]);
            $("#phaseId").val(ids[1]);
            $('#batchId').val("");
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
        $('#queryMark').val('unit');
        $("form").submit();
    });

    /**
     $('#synStatus').change(function(){
            	$("form").submit();
            });**/

    $(".date").datetimepicker({
        format: "yyyy-mm-dd hh:ii",
        autoclose: true,
        todayBtn: true,
        startDate: "2016-09-01 10:00",
        minuteStep: 10
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
});

function setProjectPharse(projectId, phaseId, name) {
    $("#projectId").val(projectId);
    $("#phaseId").val(phaseId);
    $('#projectName').val(name);
    $('#projectNameInput').val(name);
    $("form").submit();
}
function exportExcel() {
    var _url = URL_BASE + "/checkproblem/getExportCheckProblemData.do?time=" + new Date().getTime();
    _url = _url + "&" + $('form').serialize();
    window.location.href = _url;

}