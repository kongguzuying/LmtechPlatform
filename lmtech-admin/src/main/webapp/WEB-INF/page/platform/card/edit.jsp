<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title></title>
    <jsp:include page="../../common/header.jsp"></jsp:include>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.validate.min.js"></script>

    <script type="text/javascript">
        function validateForm() {
            $("form").validate({
                rules: {
                    "title":  {
                        required: true,
                        maxlength: 50
                    },
                    "category":  {
                        required: true
                    },
                    "remark":  {
                        maxlength: 50
                    },
                    "sortNo":  {
                        required: true,
                        //digits : true,
                        number : true,
                        min: 1,
                        max: 100
                    }
                },
                messages: {
                    "title": {
                        required: "请输入标题",
                        maxlength: "最多输入50个字符"
                    },
                    "category": {
                        required: "请选择类型"
                    },
                    "remark":  {
                        maxlength: "最多输入50个字符"
                    },
                    "sortNo":  {
                        required: "请输入排序号",
                        digits : "必须输入正整数",
                        min: "不能小于1",
                        max: "不能大于100"
                    }
                }
            });
            return $("form").valid();
        }

        /**
         * 处理键盘事件 禁止后退键（Backspace）密码或单行、多行文本框除外
         */
        function banBackSpace(e){
            var ev = e || window.event;//获取event对象
            var obj = ev.target || ev.srcElement;//获取事件源
            if(ev.keyCode == 8){
                var t = obj.type || obj.getAttribute('type');//获取事件源类型
                //获取作为判断条件的事件类型
                var vReadOnly = obj.getAttribute('readonly');
                //处理null值情况
                vReadOnly = (vReadOnly == "") ? false : vReadOnly;
                if((t=="password" || t=="text" || t=="textarea")&&!vReadOnly){
                    return true;
                }else{
                    e.preventdefault;
                    return false;
                }
            }
        }

        window.onload=function(){
            //禁止后退键 作用于Firefox、Opera
            document.onkeypress=banBackSpace;
            //禁止后退键  作用于IE、Chrome
            document.onkeydown=banBackSpace;

            if($.trim($("input[name='id']").val())){//编辑
                //$("#title").prop("readonly",true);

                //卡面背景
                var li_card = $('<li style="float:left;list-style-type: none;"></li>');
                li_card.html(
                    '<input type="hidden" id="background" name="background" value="${entity.background}" />' +
                    '<c:if test="${!(empty entity.background)}"><table><tr><td style="position:relative;"><img width="80" height="80" style="margin-top: 10px;" src="${entity.background}" />'+
                    '<span class="imgSpan"><a href="javascript:;" onclick="del_image(this);" class="delete">删除</a></span></td></tr></table></c:if>'
                );
                $('#swfu_images_card > li').remove();
                $('#swfu_images_card').append(li_card);

                //微信卡面
                var li_wx = $('<li style="float:left;list-style-type: none;"></li>');
                li_wx.html(
                    '<input type="hidden" id="wx_background" name="wx_background" value="${entity.wx_background}" />' +
                    '<c:if test="${!(empty entity.wx_background)}"><table><tr><td style="position:relative;"><img width="80" height="80" style="margin-top: 10px;" src="${entity.wx_background}" />'+
                    '<span class="imgSpan"><a href="javascript:;" onclick="wx_image(this);" class="delete">删除</a></span></td></tr></table></c:if>'
                );
                $('#swfu_images_wx > li').remove();
                $('#swfu_images_wx').append(li_wx);

                //分享卡面
                var li_fx = $('<li style="float:left;list-style-type: none;"></li>');
                li_fx.html(
                    '<input type="hidden" id="fx" name="fx" value="${entity.background}" />' +
                    '<c:if test="${!(empty entity.background)}"><table><tr><td style="position:relative;"><img width="80" height="80" style="margin-top: 10px;" src="'+ addBlank("${entity.background}") +'" />'+
                    '<span class="imgSpan"><a href="javascript:;" onclick="del_image(this);" class="delete">删除</a></span></td></tr></table></c:if>'
                );
                $('#swfu_images_fx > li').remove();
                $('#swfu_images_fx').append(li_fx);
            }

            $.each($("#category > option"),function(){//下拉框初始值默认设置
                $("#category > option").attr("selected",false);
                if($(this).val() == $("#h_category").val()){
                    $(this).attr("selected",true);
                    return false;
                }
            });
        }

        var cardUploader;
        var fxUploader;
        var cardUploaderToWX;
        $(function () {
            var parentId = $("#parentId").val();
            if (parentId == "0") {
                $("#parentName").val("父标题");
            }

            cardUploader = ea.imageTool.initPlupload({
                upload_tip_obj:$("#swfu_progress_card"),
                browse_button: 'swfu_placeholder_card',
                url: '${pageContext.request.contextPath}/image/uploadToFTP.json',//上传文件路径
                multipart_params: {
                    img_path:"img_card"//对应ftp的路径
                },
                FileUploadedSuccess : function(up, swfupload, info) {//文件上传完毕触发
                    var data = $.parseJSON(info.response);
                    $("#md5Name").val(data.md5Name);
                    fxUploader.settings.multipart_params.oldMd5Name = data.md5Name;
                    $('.swfu_thumbnails').find('input:hidden').each(function () {
                        if (this.value == data.basename) {
                            $(this).parent().remove();
                        }
                    });
                    var li_card = $('<li style="float:left;list-style-type: none;"></li>');
                    li_card.html(
                        '<input type="hidden" id="background" name="background" value="' + data.basename + '" />' +
                        '<table><tr><td style="position:relative;"><img width="80" height="80" style="margin-top: 10px;" src="' + data.thumb + '" />'+
                        '<span class="imgSpan"><a href="javascript:;" onclick="del_image(this);" class="delete">删除</a></span></td></tr></table>'
                    );
                    $('#swfu_images_card > li').remove();
                    $('#swfu_images_card').append(li_card);
                }
            });

            cardUploaderToWX = ea.imageTool.initPlupload({
                upload_tip_obj:$("#swfu_progress_wx"),
                browse_button: 'swfu_placeholder_wx',
                url: '${pageContext.request.contextPath}/image/uploadToWX.json',//上传文件路径
                FileUploadedSuccess : function(up, swfupload, info) {//文件上传完毕触发
                    var data = $.parseJSON(info.response);
                    $('.swfu_thumbnails_wx').find('input:hidden').each(function () {
                        if (this.value == data.wx_background) {
                            $(this).parent().remove();
                        }
                    });
                    var li_wx = $('<li style="float:left;list-style-type: none;"></li>');
                    li_wx.html(
                        '<input type="hidden" id="wx_background" name="wx_background" value="' + data.wx_background + '" />' +
                        '<table><tr><td style="position:relative;"><img width="80" height="80" style="margin-top: 10px;" src="' + data.wx_background + '" />'+
                        '<span class="imgSpan"><a href="javascript:;" onclick="wx_image(this);" class="delete">删除</a></span></td></tr></table>'
                    );
                    $('#swfu_images_wx > li').remove();
                    $('#swfu_images_wx').append(li_wx);
                }
            });

            fxUploader = ea.imageTool.initPlupload({
                upload_tip_obj:$("#swfu_progress_fx"),
                browse_button: 'swfu_placeholder_fx',
                url: '${pageContext.request.contextPath}/image/uploadToFTP.json',//上传文件路径
                multipart_params: {
                    img_path:"img_card"//对应ftp的路径
                },
                FileUploadedSuccess : function(up, swfupload, info) {//文件上传完毕触发
                    var data = $.parseJSON(info.response);
                    $('.swfu_thumbnails_fx').find('input:hidden').each(function () {
                        if (this.value == data.basename) {
                            $(this).parent().remove();
                        }
                    });
                    var li_fx = $('<li style="float:left;list-style-type: none;"></li>');
                    li_fx.html(
                        '<input type="hidden" id="fx_background" name="fx_background" value="' + data.basename + '" />' +
                        '<table><tr><td style="position:relative;"><img width="80" height="80" style="margin-top: 10px;" src="' + data.thumb + '" />'+
                        '<span class="imgSpan"><a href="javascript:;" onclick="fx_image(this);" class="delete">删除</a></span></td></tr></table>'
                    );
                    $('#swfu_images_fx > li').remove();
                    $('#swfu_images_fx').append(li_fx);
                }
            });
        });

        function del_image(obj) {
            var index = layer.confirm("确认要删除这张图片吗？", function () {
                $(obj).parent().parent().parent().parent().parent().parent().remove();
                cardUploader.disableBrowse(false);
                layer.close(index);
                // TODO 删除服务器上对应的文件.....
                $("#background").val(null);
            });
        }

        function wx_image(obj) {
            var index = layer.confirm("确认要删除这张图片吗？", function () {
                $(obj).parent().parent().parent().parent().parent().parent().remove();
                cardUploaderToWX.disableBrowse(false);
                layer.close(index);
                // TODO 删除服务器上对应的文件.....
                $("#wx_background").val(null);
            });
        }

        function fx_image(obj) {
            var index = layer.confirm("确认要删除这张图片吗？", function () {
                $(obj).parent().parent().parent().parent().parent().parent().remove();
                fxUploader.disableBrowse(false);
                layer.close(index);
                // TODO 删除服务器上对应的文件.....
                $("#fx_background").val(null);
            });
        }

        function setParentMenu(id, parentId) {;
            var modal = top.showModal({
                width: 800,
                height: 500,
                title: "父标题 - 设置",
                url: "${pageContext.request.contextPath}/platform/card/selparent.do?id=" + (id ? id : "") + "&parentId=" + (parentId ? parentId : "0"),
                opener: window,
                okFunc: function () {;
                    var parentId = top.getModalFrameContent().find("#parentId").val();
                    var parentName = top.getModalFrameContent().find("#parentName").val();

                    if (parentId) {
                        $("#parentId").val(parentId);
                        $("#parentName").val(parentName);
                    } else {
                        $("#parentId").val("0");
                        $("#parentName").val("父标题");
                    }

                    top.hideModal();
                }
            });
        }

        function setRootMenu() {
            $("#parentId").val("0");
            $("#parentName").val("父标题");
        }

        function checkMd5() {
            if(!$("#md5Name").val()){
                top.alertMessage("卡背景图没有上传导致分享背景图上传失败!");
            }else {
                $("#swfu_placeholder_fx").trigger("click");
            }
        }

        function addBlank(url) {
            if(url && (url.lastIndexOf(".png") >= 0)){
                return url.substr(0,url.lastIndexOf(".png")) + "_blank.png";
            }else{
                return url;
            }
        }
    </script>
    <style type="text/css">
        .pl-upload-btn {
            background-image: url(${pageContext.request.contextPath}/images/btn_138x36.png);
            width: 139px;
            height: 37px;
        }
        .btn1 {
            border-radius: 3px;
            -webkit-box-shadow: none;
            box-shadow: none;
            border: 1px solid transparent;
        }
        .imgSpan {
            position: absolute;
            left: 0px;
            bottom: 0px;
            right: 0px;
            width: 100%;
            height: 24px;
            background-color: #fff;
            filter: alpha(opacity = 80);
            opacity: 0.8;
            text-align: center;
            font-size: 12px;
            line-height: 24px;
        }
    </style>
</head>

<body class="body">
<div class="col-xs-12">
    <div class="form">
        <form class="cmxform form-horizontal" action="${pageContext.request.contextPath}/platform/card/edit.do" method="post">
            <input type="hidden" name="id" value="${entity.id}"/>
            <div class="form-group">
                <label class="control-label col-xs-2">
                    标题<span class="required">*</span>
                </label>
                <div class="col-xs-8">
                    <input class="form-control" api="trim" type="text" id="title" name="title" value="${entity.title}"/>
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-xs-2">
                    类型<span class="required">*</span>
                </label>
                <div class="col-xs-8">
                    <input type="hidden" id="h_category" value="${entity.category}"/>
                    <select name="category" id="category" class="form-control">
                        <option value="父标题">父标题</option>
                        <option value="子标题">子标题</option>
                        <option value="子背景">子背景</option>
                    </select>
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-xs-2">
                    备注
                </label>
                <div class="col-xs-8">
                    <input class="form-control" api="trim" type="text" id="remark" name="remark" value="${entity.remark}"/>
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-xs-2">
                    父标题<span class="required">*</span>
                </label>
                <div class="col-xs-8">

                    <input class="form-control" type="hidden" id="parentId" name="parentId" value="${not empty parentId ? parentId : entity.parentId}"/>
                    <input class="form-control" readonly="true" type="text" id="parentName" name="parentName"
                           value="${parentName}"/>
                    <a href="javascript:void(0)" class="btn btn-default tb-btn"
                       onclick="setParentMenu('${entity.id}','${entity.parentId}')">设置父标题</a>
                    <a href="javascript:void(0)" class="btn btn-default tb-btn" onclick="setRootMenu()">设为父标题</a>
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-xs-2">
                    排序号<span class="required">*</span>
                </label>
                <div class="col-xs-8">
                    <input class="form-control" api="trim" type="text" id="sortNo" name="sortNo" value="${(empty entity.sortNo)?1:entity.sortNo}"/>
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-xs-2">
                    FTP卡面
                </label>
                <div class="col-xs-8" style="position: relative;">
                    <li class="swfu_thumbnails" style="float:left; list-style-type:none;position: relative;">
                        <input type="button" id="swfu_placeholder_card" class="btn1 pl-upload-btn" value=""/>
                        <div id="swfu_progress_card"></div>
                        <ul id="swfu_images_card" style="-webkit-padding-start:0px;"></ul>
                    </li>
                    <span style="position:absolute; top:10px; left:160px;color: red;">
                        图片限制1MB，仅支持JPG、PNG格式；FTP卡面/FTP分享卡面需同时上传/更新。
                    </span>
                </div>
            </div>

            <div class="form-group">
                <label class="control-label col-xs-2">
                    FTP分享卡面
                </label>
                <div class="col-xs-8" style="position: relative;">
                    <li class="swfu_thumbnails_fx" style="float:left; list-style-type:none;position: relative;">
                        <input type="hidden" id="md5Name" name="md5Name"/>
                        <input type="button" class="btn1 pl-upload-btn" value="" onclick="checkMd5()"/>
                        <input type="hidden" id="swfu_placeholder_fx" class="btn1 pl-upload-btn" value=""/>
                        <div id="swfu_progress_fx"></div>
                        <ul id="swfu_images_fx" style="-webkit-padding-start:0px;"></ul>
                    </li>
                    <span style="position:absolute; top:10px; left:160px; color: red;">
                        图片限制1MB，仅支持JPG、PNG格式；FTP卡面/FTP分享卡面需同时上传/更新。
                    </span>
                </div>
            </div>

            <div class="form-group">
                <label class="control-label col-xs-2">
                    微信卡面
                </label>
                <div class="col-xs-8" style="position: relative;">
                    <li class="swfu_thumbnails_wx" style="float:left; list-style-type:none;position: relative;">
                        <input type="button" id="swfu_placeholder_wx" class="btn1 pl-upload-btn" value=""/>
                        <div id="swfu_progress_wx"></div>
                        <ul id="swfu_images_wx" style="-webkit-padding-start:0px;"></ul>
                    </li>
                    <span style="position:absolute; top:10px; left:160px;color: red;">
                        图片限制1MB，仅支持JPG、PNG格式。
                    </span>
                </div>
            </div>

            <div class="form-group">
                <label class="control-label col-xs-2"></label>
                <div class="col-xs-8">
                    <button class="btn btn-primary" type="submit">保存</button>
                    <a href="${pageContext.request.contextPath}/platform/card/list.do" class="btn btn-default">返回</a>
                </div>
            </div>
        </form>
    </div>
</div>
</body>
</body>
</html>