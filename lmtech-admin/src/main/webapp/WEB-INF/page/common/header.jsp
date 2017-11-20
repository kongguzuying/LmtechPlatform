<%@ page import="java.util.Random"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
<meta name="description" content="">
<%--<% request.setAttribute("version", new Random().nextInt(100)); %>--%>
<% request.setAttribute("version", "1.0.0.3.10"); %>
<link href="${pageContext.request.contextPath}/css/style.css?v=${version}" rel="stylesheet">
<link href="${pageContext.request.contextPath}/css/style-responsive.css?v=${version}" rel="stylesheet">
<link href="${pageContext.request.contextPath}/css/style-my.css?v=${version}" rel="stylesheet">
<link href="${pageContext.request.contextPath}/css/font-glyphicon.css" rel="stylesheet"/>

<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
<script src="${pageContext.request.contextPath}/js/html5shiv.js"></script>
<script src="${pageContext.request.contextPath}/js/respond.min.js"></script>
<![endif]-->

<!-- Placed js at the end of the document so the pages load faster -->
<script src="${pageContext.request.contextPath}/js/jquery-1.10.2.min.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery-ui-1.9.2.custom.min.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery-migrate-1.2.1.min.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/js/modernizr.min.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery.nicescroll.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery.form.js?v=${version}"></script>
<script src="${pageContext.request.contextPath}/js/jquery.isloading.min.js?v=${version}"></script>
<script src="${pageContext.request.contextPath}/js/jquery.cookies.js?v=${version}"></script>
<script src="${pageContext.request.contextPath}/js/modal.js?v=${version}"></script>
<script src="${pageContext.request.contextPath}/js/layer/layer.js"></script>

<!--pickers css-->
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/bootstrap-datepicker/css/datepicker-custom.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/bootstrap-timepicker/css/timepicker.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/bootstrap-colorpicker/css/colorpicker.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/bootstrap-daterangepicker/daterangepicker-bs3.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/bootstrap-datetimepicker/css/datetimepicker-custom.css" />
<!--dynamic table-->
<link rel="stylesheet" href="${pageContext.request.contextPath}/js/data-tables/DT_bootstrap.css"/>
<!--pickers plugins-->
<script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap-datepicker/js/bootstrap-datepicker.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap-datetimepicker/js/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap-daterangepicker/moment.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap-daterangepicker/daterangepicker.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap-colorpicker/js/bootstrap-colorpicker.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap-timepicker/js/bootstrap-timepicker.js"></script>
<!--pickers initialization-->
<script src="${pageContext.request.contextPath}/js/pickers-init.js"></script>

<!--common scripts for all pages-->
<script src="${pageContext.request.contextPath}/js/scripts-my.js?v=${version}"></script>

<script src="${pageContext.request.contextPath}/plupload/plupload.full.min.js"></script>

<script type="text/javascript">
var URL_BASE = "${pageContext.request.contextPath}";

var ea = window.ea || {};
ea.layer=new function() {
    var self=this;
    self.successMsg=function(msg) {
        layer.msg(msg,{icon:1});
    };
    //显示感叹号图标
    self.warnMsg=function(msg) {
        layer.msg(msg,{icon:0});
    };
    self.failMsg=function(msg) {
        layer.msg(msg,{icon:5});
    };
    //显示叉图标
    self.notAllowMsg=function(msg) {
        layer.msg(msg,{icon:2});
    };
    //无图标
    self.msg=function(msg) {
        layer.msg(msg);
    };
};

ea.imageTool = new function () {
    var self = this;
    self.initPlupload = function (params) {
        var defaultConfs = {
            runtimes: 'html5,flash,silverlight',//设置运行环境，会按设置的顺序，可以选择的值有html5,gears,flash,silverlight,browserplus,html
            flash_swf_url: URL_BASE + '/plupload/Moxie.swf',
            silverlight_xap_url: URL_BASE + '/plupload/Moxie.xap',
            //url: URL_BASE + '/image/uploadToFTP.json',//上传文件路径
            max_file_size: '2mb',//100b, 10kb, 10mb, 1gb
            chunk_size: '1mb',//分块大小，小于这个大小的不分块
            unique_names: true,//生成唯一文件名
//                resize: { width: 320, height: 240, quality: 90 },// 是否生成缩略图（仅对图片文件有效）
            file_data_name: "swfupload",
            filters: [{
                title: 'Image files',
                extensions: 'jpg,png'
            }],
            //自定义属性
            //upload_tip_obj: $("#plupload_processer"),
            limit_number: 1,
            button_disabled: false,
            init: {
                Init: function (uploader) {
                    uploader.disableBrowse(defaultConfs.button_disabled);
                },
                FilesAdded: function (uploader, files) {

                    if (files.length > defaultConfs.limit_number) { // 最多上传1个文件
                        ea.layer.warnMsg("只能上传" + defaultConfs.limit_number + "个文件，请删除多余文件！");
                        uploader.splice();
                        return false;
                    }

                    uploader.start();
                },
                FileUploaded: function (up, swfupload, info) {//文件上传完毕触发
                    var data = $.parseJSON(info.response);
                    if (data.state != 0) {
                        if (defaultConfs.upload_tip_obj) {
                            defaultConfs.upload_tip_obj.html(data.sourceFileName + '&nbsp;<font color="red">上传失败：' + data.msg + '</font> ');
                            setTimeout(function () {
                                defaultConfs.upload_tip_obj.html("");
                            }, 1500);
                            return;
                        } else {

                            ea.layer.failMsg(data.sourceFileName + '上传失败：' + data.msg);
                            return;
                        }
                    }
                    if (params && params.FileUploadedSuccess) {
                        params.FileUploadedSuccess(up, swfupload, info);
                        if (defaultConfs.upload_tip_obj) {
                            defaultConfs.upload_tip_obj.html(data.sourceFileName + '&nbsp;<font color="green">(上传成功!)</font>');
                            setTimeout(function () {
                                defaultConfs.upload_tip_obj.html("");
                            }, 1500);
                        }
                    }
                },
                UploadComplete: function (uploader, files) {
//                        if(defaultConfs.upload_tip_obj){
//                            defaultConfs.upload_tip_obj.html(files[0].name + ' &nbsp; <font color="green">(上传成功！)</font>');
//                            setTimeout(function() {
//                                defaultConfs.upload_tip_obj.html("");
//                            },1500);
//                        }
                },
                UploadProgress: function (uploader, swfupload) {
                    if (defaultConfs.upload_tip_obj) {
                        defaultConfs.upload_tip_obj.html("上传进度为：" + swfupload.percent + "%");
                    }
                },
                Error: function (uploader, errObject) {
                    if (defaultConfs.upload_tip_obj) {
                        defaultConfs.upload_tip_obj.html('<font color="red">' + errObject.message + '</font>');
                        setTimeout(function () {
                            defaultConfs.upload_tip_obj.html("");
                        }, 1500);
                    }
                }
            }
        };
        var conf = $.extend(defaultConfs, params);
        var uploader = new plupload.Uploader(conf);
        uploader.init();
        return uploader;
    };
}
</script>
