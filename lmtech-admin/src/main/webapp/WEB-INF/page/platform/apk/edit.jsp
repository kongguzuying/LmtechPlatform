<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="/WEB-INF/tags/tags.tld" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="../common/header.jsp"></jsp:include>

    <link href="${pageContext.request.contextPath}/js/dropzone/css/dropzone.css" rel="stylesheet"/>
    <link href="${pageContext.request.contextPath}/css/font-glyphicon.css" rel="stylesheet"/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.validate.min.js"></script>

    <script type="text/javascript">
        function validateForm() {
            $("form").validate({
                rules: {
                    "apkCode": { maxlength: 20,required: true},
                    "apkName": { maxlength: 30,required: true},
                    "apkDesc": { maxlength: 500 },
                    "apkVersion": { maxlength: 10 },
                    "apkImageDownLoad": { required: true }
                },
                messages: {
                    "apkCode": { required: "请输入APK编号", maxlength: "最多输入20个字符" },
                    "apkName": { required: "请输入APK名字", maxlength: "最多输入30个字符" },
                    "apkDesc": { maxlength: "最多输入500个字符" },
                    "apkVersion": { maxlength: "最多输入10个字符" },
                    "apkImageDownLoad": { required: "请输入二维码地址" }
                }
            });
            var valid = $("form").valid();
            /*if (valid) {
                if ($("#apkSize").val()) {
                    return true;
                } else {
                    top.alertMessage("请先上传打包文件。");
                    return false;
                }
            } else {
                return valid;
            }*/
            return valid;
        }
    </script>
</head>
<body class="body">
<div class="col-xs-12">
    <div class="form">
        <form class="cmxform form-horizontal" method="post" action="${pageContext.request.contextPath}/apk/syncedit.do" novalidate="novalidate">
            <input type="hidden" id="id" name="id" value="${entity.id}"/>
            <%--<input type="hidden" id="apkSize" name="apkSize" value="${entity.apkSize}"/>--%>
            <div class="form-group ">
                <label class="control-label col-xs-2">APK编号</label>
                <div class="col-xs-10">
                    <input class=" form-control" id="apkCode" name="apkCode" value="${entity.apkCode}" type="text">
                </div>
            </div>
            <div class="form-group ">
                <label class="control-label col-xs-2">APK名字</label>
                <div class="col-xs-10">
                    <input class=" form-control" id="apkName" name="apkName" value="${entity.apkName}" type="text">
                </div>
            </div>
            <div class="form-group ">
                <label class="control-label col-xs-2">二维码地址</label>
                <div class="col-xs-10">
                    <input class=" form-control" id="apkImageDownLoad" name="apkImageDownLoad" value="${entity.apkImageDownLoad}" type="text">
                </div>
            </div>
            <%--<div class="form-group ">
                <label for="fileUpload" class="control-label col-xs-2">APK文件</label>
                <div class="col-xs-4">
                    <input class=" form-control" id="apkFileName" name="apkFileName" value="${entity.apkFileName}" type="text">
                </div>
                <div class="col-xs-2">
                    <button id="btnUploadFile" type="button" class="btn btn-default" onclick="uploadFile()">上传</button>
                </div>
                <div class="col-xs-4">
                    <div id="fileUpload" class="fileupload"></div>
                    <input type="hidden" id="apkDownLoad" name="apkDownLoad" value="${entity.apkDownLoad}"/>
                    <input type="hidden" id="apkLocalPath" name="apkLocalPath" value="${entity.apkLocalPath}"/>
                </div>
            </div>--%>
            <div class="form-group ">
                <label class="control-label col-xs-2">APK类型</label>
                <div class="col-xs-10">
                    <input name="apkType" value="Android" <c:if test="${entity.apkType == 'Android'}">checked</c:if> type="radio">Android
                    <input name="apkType" value="IOS" <c:if test="${entity.apkType == 'IOS'}">checked</c:if> type="radio">IOS
                </div>
            </div>
            <div class="form-group ">
                <label class="control-label col-xs-2">APK版本</label>
                <div class="col-xs-10">
                    <input class=" form-control" id="apkVersion" name="apkVersion" value="${entity.apkVersion}" type="text">
                </div>
            </div>
            <div class="form-group ">
                <label class="control-label col-xs-2">是否删除db</label>
                <div class="col-xs-10">
                    <input id="deleteDb" name="deleteDb" <c:if test="${entity.deleteDb}">checked</c:if> type="checkbox">
                </div>
            </div>
            <div class="form-group ">
                <label class="control-label col-xs-2">是否强制更新</label>
                <div class="col-xs-10">
                    <input name="updateType" value="S" <c:if test="${entity.updateType == 'S'}">checked</c:if> type="radio">必须更新
                    <input name="updateType" value="G" <c:if test="${entity.updateType == 'G'}">checked</c:if> type="radio">建议更新
                    <input name="updateType" value="N" <c:if test="${entity.updateType == 'N'}">checked</c:if> type="radio">无更新
                </div>
            </div>
            <div class="form-group ">
                <label class="control-label col-xs-2">APK描述</label>
                <div class="col-xs-10">
                    <textarea class=" form-control" id="apkDesc" name="apkDesc" >${entity.apkDesc}</textarea>
                </div>
            </div>
        </form>
    </div>
</div>
<div id="previewTemplate" style="display:none;" class="dz-preview dz-file-preview">
    <div class="dz-details">
        <div class="dz-filename"><span data-dz-name></span>&nbsp;<span data-dz-size></span></div>
        <div class="dz-action">
            <a href="javascript:clearFiles()">删除</a>
        </div>
    </div>
    <div class="dz-progress"><span class="dz-upload" data-dz-uploadprogress></span></div>
</div>
<script src="${pageContext.request.contextPath}/js/dropzone/dropzone.js"></script>
<script type="text/javascript">
    var myDropzone = null;
    function uploadFile() {
        var detail = $("div#fileUpload .dz-details");
        var canUpload = (detail.length <= 0);
        if (!canUpload) {
            top.alertMessage("只允许上传一次，请删除后再上传！");
            return;
        } else {
            $("#fileUpload").click();
        }
    }
    function clearFiles() {
        myDropzone.removeAllFiles(true);
        $("div#fileUpload").empty();
        $("#apkDownLoad").val("");
        $("#apkLocalPath").val("");
        /*$("#apkSize").val("");*/
        $("#apkfilename").val("");
    }
    $(function () {
        //初始化上传组件
        myDropzone = new Dropzone("div#fileUpload", {
            url: "${pageContext.request.contextPath}/file/uploadoss.do",
            method: "post",
            maxFilesiz: 10,
            uploadMultiple: false,
            addRemoveLinks: false,
            clickable: true,
            previewTemplate: document.getElementById("previewTemplate").innerHTML,
            renameFilename: function (name) {
                $("#apkFileName").val(name);
                return name;
//                 return encodeURIComponent(name);
            },
            sending: function () {
                showLoadingOutside("#btnUploadFile");
            },
            success: function (data, exeResult) {
                if (!exeResult.success) {
                    top.alertMessage(exeResult.message ? exeResult.message : "上传过程出现未知错误");
                } else {
                    var data = exeResult.data;
                    $("#apkDownLoad").val(data.id);
                    $("#apkLocalPath").val(data.localPath);
                    /*$("#apkSize").val(data.size);*/
                }
            },
            complete: function () {
                hideLoading("#btnUploadFile");
            }
        });
    })
</script>
</body>
</html>
