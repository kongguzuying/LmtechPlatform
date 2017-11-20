var modalOptions = null;
/**
 * 显示模态对话框
 * @param options
 */
function showModal(options) {
    var url = options.url;
    var title = options.title;
    var opener = options.opener ? options.opener : null;
    var width = options.width;
    var height = options.height;
    var showBtn = options.showBtn ? options.showBtn : true;
    var refreshFinish = options.refreshFinish ? options.refreshFinish : true;
    var okFunc = options.okFunc ? options.okFunc : null;
    var cancelFunc = options.cancelFunc ? options.cancelFunc : null;
    var shown = options.shown ? options.shown : null;
    var showFooter = typeof(options.showFooter) != "undefined" ? options.showFooter : true;
    var onFinished = options.onFinished ? options.onFinished : null;
    var onBeforeHide = options.onBeforeHide ? options.onBeforeHide : null;

    modalOptions = {
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

    $('#myModal iframe').attr("src", url);
    $('#myModal .modal-dialog').css("width", width);
    if (showFooter)  {
        $('#myModal .modal-footer').css("display", "");
    } else {
        $('#myModal .modal-footer').css("display", "none");
    }
    $('#myModal iframe').css("height", height - 60);
    $('#myModal .modal-title').text(title);
    $('#myModal').modal({
        keyboard: false,
        show: false,
        backdrop: 'static'
    });
    $('#myModal').modal('show');
    $('#myModal').on('hidden.bs.modal', function () {
        // 模态框隐藏事件
        $('#myModal iframe').attr("src", "");
    });
    $("#myModal button.close").on('click', hideModal);

//    $('#myModal').on("shown.bs.modal",modalOptions.shown);


    return {
        options: modalOptions,
        frame: modalFrame
    };
}
/**
 * 隐藏模态框
 */
function hideModal() {
    if (modalOptions.onBeforeHide) {
        modalOptions.onBeforeHide();
    }
    $('#myModal iframe').attr("src", "");
    $('#myModal').modal('hide');
}
/**
 * ok按钮单击事件
 */
function okClick() {
    if (modalOptions.okFunc) {
        modalOptions.okFunc();
    } else {
        modalSubmit();
    }
}
/**
 * calcel按钮单击事件
 */
function cancelClick() {
    if (modalOptions.calcelFunc) {
        modalOptions.calcelFunc();
    } else {
        hideModal();
    }
}
/**
 * 模态框内表单提交
 */
function modalSubmit() {
    var form = $("#modalFrame").contents().find("form");
    form.find('input[api]').each(function(){
        var api=$(this).attr('api');
        var value=$(this).val();
        if(api=='trim'){
            $(this).val($.trim(value));
        }
    });

    ajaxForm(form);

    if (modalFrame && typeof modalFrame.beforeSubmit != "undefined") {
        modalFrame.beforeSubmit();
    }

    var isValid = true;
    if (modalFrame && typeof modalFrame.validateForm != "undefined") {
        isValid = modalFrame.validateForm();
    }

    if (isValid) {
        form.submit();
    }
}

function ajaxForm(form) {
    form.ajaxForm({
        success: function (exeResult) {
            if (exeResult) {
                if (exeResult.success) {
                    top.showShortMessage({
                        text: (exeResult.message ? exeResult.message : "保存成功！"),
                        callback: function () {
                            //console.error(modalOptions);
                            if (modalOptions.refreshFinish && modalOptions.opener != null) {
                                hideModal();
                                var d = modalOptions.opener.document?modalOptions.opener.document:modalOptions.opener;
                                //var $form = $(modalOptions.opener.document).find("form");
                                if(d){
                                    //console.error(d.getElementsByTagName("form")[0]);
                                    var form=d.getElementsByTagName("form");
                                    if(form.length>0){
                                        d.getElementsByTagName("form")[0].submit();
                                    }else{
                                        //console.error("test.......");
                                        modalOptions.opener.location.reload();
                                    }
                                }else{
                                    modalOptions.opener.location.reload();
                                }
                            } else {
                                hideModal();
                            }
                            if (modalOptions.onFinished) {
                                modalOptions.onFinished(exeResult);
                            }
                        }
                    });
                } else {
                    top.alertMessage(exeResult.message ? exeResult.message : "保存失败！");
                }
            } else {
                top.alertMessage("提交过程中出现未知错误。");
            }
        }, error: function () {
            top.alertMessage("提交过程中出现未知错误。");
        }
    });
}
/**
 * 获取模态窗内容
 * @returns {*|jQuery}
 */
function getModalFrameContent() {
    return $("#modalFrame").contents();
}



/**
 * ViewModel
 */
var viewModalOptions = null;
/**
 * 显示模态对话框
 * @param options
 */
function showViewModal(options) {
    var url = options.url;
    var title = options.title;
    var opener = options.opener ? options.opener : null;
    var width = options.width;
    var height = options.height;
    var showBtn = options.showBtn ? options.showBtn : true;
    var refreshFinish = options.refreshFinish ? options.refreshFinish : true;
    var okFunc = options.okFunc ? options.okFunc : null;
    var cancelFunc = options.cancelFunc ? options.cancelFunc : null;
    var shown = options.shown ? options.shown : null;
    var showFooter = typeof(options.showFooter) != "undefined" ? options.showFooter : true;
    var onFinished = options.onFinished ? options.onFinished : null;
    var onBeforeHide = options.onBeforeHide ? options.onBeforeHide : null;

    viewModalOptions = {
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

    $('#viewModal iframe').attr("src", url);
    $('#viewModal .modal-dialog').css("width", width);
    if (showFooter)  {
        $('#viewModal .modal-footer').css("display", "");
    } else {
        $('#viewModal .modal-footer').css("display", "none");
    }
    $('#viewModal iframe').css("height", height - 60);
    $('#viewModal .modal-title').text(title);
    $('#viewModal').modal({
        keyboard: false,
        show: false,
        backdrop: 'static'
    });
    $('#viewModal').modal('show');
    $('#viewModal').on('hidden.bs.modal', function () {
        // 模态框隐藏事件
        $('#viewModal iframe').attr("src", "");
    });
    $("#viewModal button.close").on('click', hideViewModal);

    return {
        options: viewModalOptions,
        frame: viewModalFrame
    };
}
/**
 * 隐藏模态框
 */
function hideViewModal() {
    if (viewModalOptions.onBeforeHide) {
        viewModalOptions.onBeforeHide();
    }
    $('#viewModal iframe').attr("src", "");
    $('#viewModal').modal('hide');
}