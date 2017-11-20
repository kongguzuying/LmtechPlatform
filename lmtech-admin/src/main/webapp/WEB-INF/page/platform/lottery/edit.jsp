<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="/WEB-INF/tags/tags.tld" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="../../common/header.jsp"></jsp:include>

    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.validate.min.js"></script>

    <script type="text/javascript">
        function validateForm() {
            $("form").validate({
                rules: {
                    "name":  {
                        required: true,
                        maxlength: 50
                    },
                    "imageUrl":  {
                        required: true,
                        maxlength: 50
                    },
                    "sortNo":  {
                        required: true,
                            //digits : true,
                            number : true,
                            min: 0,
                            max: 100
                    },
                    "type":  {
                        required: true,
                        //digits : true,
                        number : true,
                        min: 0,
                        max: 100
                    },
                    "prize":  {
                        required: true,
                        //digits : true,
                        number : true,
                        min: 0,
                        max: 1
                    },
                    "join":  {
                        required: true,
                        //digits : true,
                        number : true,
                        min: 0,
                        max: 1
                    }
                },
                messages: {
                    "name": {
                        required: "请输入商品名称",
                        maxlength: "最多输入50个字符"
                    },
                    "imageUrl":  {
                        required: "请输入商品图片",
                        maxlength: "最多输入50个字符"
                    },
                    "sortNo":  {
                        required: "请输入排序号",
                        digits : "必须输入正整数",
                        min: "不能小于0",
                        max: "不能大于100"
                    },
                    "type":  {
                        required: "请输入奖品类型",
                        digits : "必须输入正整数",
                        min: "不能小于0",
                        max: "不能大于100"
                    },
                    "prize":  {
                        required: "请输入是否中奖",
                        digits : "必须输入正整数",
                        min: "不能小于0",
                        max: "不能大于1"
                    },
                    "join":  {
                        required: "请输入是否参与中奖",
                        digits : "必须输入正整数",
                        min: "不能小于0",
                        max: "不能大于1"
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
                //$("#name").prop("readonly",true);
                var li = $('<li style="float:left;list-style-type: none;"></li>');
                li.html(
                    '<input type="hidden" id="imageUrl" name="imageUrl" value="${entity.imageUrl}" />' +
                    '<c:if test="${!(empty entity.imageUrl)}"><table><tr><td style="position:relative;"><img width="80" height="80" style="margin-top: 10px;" src="${entity.imageUrl}" />'+
                    '<span class="imgSpan"><a href="javascript:;" onclick="del_image(this);" class="delete">删除</a></span></td></tr></table></c:if>'
                );
                $('#swfu_images_1 > li').remove();
                $('#swfu_images_1').append(li);
            }
        }

        var uploader;
        $(function(){
            uploader = ea.imageTool.initPlupload({
                upload_tip_obj:$("#swfu_progress_1"),
                browse_button: 'swfu_placeholder_1',
                url: '${pageContext.request.contextPath}/image/uploadToFTP.json',//上传文件路径
                multipart_params: {
                    img_path:"img_product"//对应ftp的路径
                },
                FileUploadedSuccess : function(up, swfupload, info) {//文件上传完毕触发
                    var data = $.parseJSON(info.response);
                    $('.swfu_thumbnails').find('input:hidden').each(function () {
                        if (this.value == data.basename) {
                            $(this).parent().remove();
                        }
                    });
                    var li = $('<li style="float:left;list-style-type: none;"></li>');
                    li.html(
                        '<input type="hidden" id="imageUrl" name="imageUrl" value="' + data.basename + '" />' +
                        '<table><tr><td style="position:relative;"><img width="80" height="80" style="margin-top: 10px;" src="' + data.thumb + '" />'+
                        '<span class="imgSpan"><a href="javascript:;" onclick="del_image(this);" class="delete">删除</a></span></td></tr></table>'
                    );
                    $('#swfu_images_1 > li').remove();
                    $('#swfu_images_1').append(li);
                }
            });
        });

        function del_image(obj) {
            var index = layer.confirm("确认要删除这张图片吗？", function () {
                $(obj).parent().parent().parent().parent().parent().parent().remove();
                uploader.disableBrowse(false);
                layer.close(index);
                // TODO 删除服务器上对应的文件.....
                $("#imageUrl").val(null);
            });
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
        <form class="cmxform form-horizontal" method="post" action="${pageContext.request.contextPath}/platform/lottery/syncedit.do" novalidate="novalidate">
            <input type="hidden" name="id" value="${entity.id}"/>
            <div class="form-group ">
                <label for="name" class="control-label col-xs-2 text-right">商品名称<span class="required">*</span></label>
                <div class="col-xs-8">
                    <input class="form-control" api="trim" id="name" name="name" value="${entity.name}" type="text">
                </div>
            </div>
            <div class="form-group ">
                <label for="sortNo" class="control-label col-xs-2 text-right">排序号<span class="required">*</span></label>
                <div class="col-xs-8">
                    <input class="form-control" api="trim" id="sortNo" name="sortNo" value="${(empty entity.sortNo)?0:entity.sortNo}" type="text">
                </div>
            </div>
            <div class="form-group ">
                <label for="type" class="control-label col-xs-2 text-right">奖品类型<span class="required">*</span></label>
                <div class="col-xs-8">
                    <input class="form-control" api="trim" id="type" name="type" value="${(empty entity.type)?0:entity.type}" type="text">
                </div>
            </div>
            <div class="form-group ">
                <label for="prize" class="control-label col-xs-2 text-right">是否中奖<span class="required">*</span></label>
                <div class="col-xs-8">
                    <input class="form-control" api="trim" id="prize" name="prize" value="${entity.prize ? 1 : 0}" type="text">
                </div>
            </div>
            <div class="form-group ">
                <label for="join" class="control-label col-xs-2 text-right">是否参与中奖<span class="required">*</span></label>
                <div class="col-xs-8">
                    <input class="form-control" api="trim" id="join" name="join" value="${entity.join ? 1 : 0}" type="text">
                </div>
            </div>
            <div class="form-group ">
                <label class="control-label col-xs-2 text-right">商品图片<span class="required">*</span></label>
                <div class="col-xs-8" style="position: relative;">
                    <li class="swfu_thumbnails" style="float:left; list-style-type:none;position: relative;">
                        <input type="button" id="swfu_placeholder_1" class="btn1 pl-upload-btn" value=""/>
                        <div id="swfu_progress_1"></div>
                        <ul id="swfu_images_1" style="-webkit-padding-start:0px;"></ul>
                    </li>
                    <span style="position:absolute; top:10px; left:160px;width:250px; color: red;">
                        图片限制1MB，仅支持JPG、PNG格式。
                    </span>
                </div>
            </div>
        </form>
    </div>
</div>
</body>
</html>
