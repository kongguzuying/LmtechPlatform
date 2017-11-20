<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="/WEB-INF/tags/tags.tld" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="../common/header.jsp"></jsp:include>
   	<script src="${pageContext.request.contextPath}/js/jquery-fileupload/js/vendor/jquery.ui.widget.js"></script>
	<script src="${pageContext.request.contextPath}/js/jquery-fileupload/js/tmpl.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/jquery-fileupload/js/load-image.all.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/jquery-fileupload/js/canvas-to-blob.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/jquery-fileupload/js/jquery.iframe-transport.js"></script>
	<script src="${pageContext.request.contextPath}/js/jquery-fileupload/js/jquery.fileupload.js"></script>
	
	<script src="${pageContext.request.contextPath}/js/jquery-fileupload/js/jquery.fileupload-process.js"></script>
	<script src="${pageContext.request.contextPath}/js/jquery-fileupload/js/jquery.fileupload-image.js"></script>
	<script src="${pageContext.request.contextPath}/js/jquery-fileupload/js/jquery.fileupload-audio.js"></script>
	<script src="${pageContext.request.contextPath}/js/jquery-fileupload/js/jquery.fileupload-video.js"></script>
	<script src="${pageContext.request.contextPath}/js/jquery-fileupload/js/jquery.fileupload-validate.js"></script>
	<script src="${pageContext.request.contextPath}/js/jquery-fileupload/js/jquery.fileupload-ui.js"></script>
	<script src="${pageContext.request.contextPath}/js/jquery-fileupload/js/qunit-1.15.0.js"></script>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/js/jquery-fileupload/css/jquery.fileupload.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/js/jquery-fileupload/css/jquery.fileupload-ui.css">
<script type="text/javascript">
			//是否需要填写附件说明。
			var hasNote = "${hasNote}";
			//上传控件自定义参数
			var thisFormData = {bussinessId:"${bussinessId}",bussiness:"${bussiness}",isOss:"${isOss}"};
			
			$(document).ready(function() {
				'use strict';
				$('#fileupload').fileupload({
					url : '${fileServerUrl}/file/sapupload.do',
					sequentialUploads : true,
					submit:function(e,data){
						console.log(thisFormData);
						$('#fileupload').fileupload({formData:thisFormData});
						return true;
					}
				});
				//重置页面大小
				$(window).resize(resizeWindows);
				resizeWindows() ;
			});
	
			function resizeWindows() {
				var winHeight = $(window).height();
				$("#fileListTable").height(winHeight - $(".nav-tabs").height() - $("#toolbar").height()-60);
			}
			
		</script>

		<style type="text/css">
			#toolbar {
				height: 40px;
			}
			
			.progress {
				margin-bottom: 0px;
			}
			
			#fileListTable {
				margin: 0px;
				padding: 0px;
				overflow-y: auto;
				z-index: 0;
				width: 100%;
			}
			
			#searchForm,#toolbar,.nav-tabs{
				padding:2px;
				margin:2px;
			}
		</style>
	</head>
	<body style="overflow-x: hidden;overflow-y: hidden;">
		<div class="row">
			<div class="col-md-12" style="margin-left: 15px;margin-right: 15px;width: 98%;height: 98%;">
				<!-- The file upload form used as target for the file upload widget -->
				<div id="fileupload" >
					<!-- The fileupload-buttonbar contains buttons to add/delete files and start/cancel the upload -->
					<div class="row fileupload-buttonbar" id="toolbar">
						<div class="col-md-7">
							<!-- The fileinput-button span is used to style the file input field as button -->
							<span class="btn btn-success fileinput-button"> 
								<i class="glyphicon glyphicon-plus"></i>
								<span>选择文件...</span>
								<input type="file" name="files[]" multiple>
							</span>
							<button type="submit" class="btn btn-primary start">
								<i class="glyphicon glyphicon-upload"></i> 
								<span>开始上传</span>
							</button>
							<button type="reset" class="btn btn-warning cancel">
								<i class="glyphicon glyphicon-ban-circle"></i> 
								<span>全部取消</span>
							</button>
							<!-- <button type="button" class="btn btn-danger delete">
								<i class="glyphicon glyphicon-trash"></i> <span>删除</span>
							</button>
							<input type="checkbox" class="toggle"> -->
							<!-- The global file processing state -->
							<span class="fileupload-process"></span>
						</div>
						<!-- The global progress state -->
						<div class="col-md-5 fileupload-progress fade">
							<!-- The global progress bar -->
							<div class="progress progress-striped active" role="progressbar" aria-valuemin="0" aria-valuemax="100">
								<div class="progress-bar progress-bar-success" style="width:0%;"></div>
							</div>
							<!-- The extended global progress state -->
							<div class="progress-extended">&nbsp;</div>
						</div>
					</div>
					<div id="fileListTable">
						<!-- The table listing the files available for upload/download -->
						<table role="presentation" class="table" >
							<tbody class="files"></tbody>
						</table>
					</div>
					
				</div>
				<div class="footFixToolbar">
					<%--<button class="btn btn-default closeNowLayer" type="button" id="cancel"><i class="glyphicon glyphicon-remove-circle"></i>&nbsp;完成</button>--%>
				<c:if test="${help}">
				 参数详解： bussiness:业务,bussinessId:业务编号(注：oss上文件路径对应),isOss:是否同时上传到阿里云,默认true上传 <br/>
				例 户型图上传： bussiness=roomtype&bussinessId=projectId/pharseId 
				</c:if>
				</div>
			</div>
		</div>
	</body>


	<!-- The template to display files available for upload -->
	<script id="template-upload" type="text/x-tmpl">
	{% for (var i=0, file; file=o.files[i]; i++) { %}
    <tr class="template-upload fade">
        <td colspan="4">
		<table style="width:100%;">
			<tr>
				<td width="250px">
            		<span class="preview"></span>
        		</td>
       			 <td>
            		<span class="name">{%=file.name%}</span>
            		<strong class="error text-danger"></strong>
        		</td>
        		<td width="100px">
            		<p class="size">Processing...</p>
        		</td>
				<td width="300px" style="margin:5px;padding:5px;">
					<div class="progress progress-striped active" role="progressbar" aria-valuemin="0" aria-valuemax="100" aria-valuenow="0"><div class="progress-bar progress-bar-success" style="width:0%;"></div></div>
				</td>
        		<td width="200px">
            	{% if (!i && !o.options.autoUpload) { %}
                	<button class="btn btn-primary start">
                    	<i class="glyphicon glyphicon-upload"></i>
                    	<span>开始</span>
                	</button>
            	{% } %}
            	{% if (!i) { %}
                	<button class="btn btn-warning cancel">
                    	<i class="glyphicon glyphicon-ban-circle"></i>
                    	<span>取消</span>
                	</button>
            	{% } %}
        		</td>
			</tr>
		</table>
		</td>
    </tr>
	
	{% } %}
	</script>
	<!-- The template to display files available for download -->
	<script id="template-download" type="text/x-tmpl">
	{% for (var i=0, file; file=o.files[i]; i++) { %}
    <tr class="template-download fade">
        <td colspan="3">
            {% if (file.error) { %}
                <div><span class="label label-danger">Error</span> {%=file.error%}</div>
            {% }else{ %}
				<p class="name">{%=file.isOss%}
				 {% if (file.oss) { %}
                    {%=file.fileName%}
                    <a href="{%=file.ossUrl%}" target="_blank" title="{%=file.fileName%}"  >查看</a>
                    <a href="{%=file.ossUrl%}" target="_blank" title="{%=file.fileName%}" download="{%=file.fileName%}" >下载</a>
                {% }else{ %}
	                {%=file.fileName%}
                    <a href="{%=file.savePath%}" target="_blank" title="{%=file.fileName%}" >查看</a>
                    <a href="{%=file.savePath%}" target="_blank" title="{%=file.fileName%}" download="{%=file.fileName%}" >下载</a>
                {% } %}
            	</p>
			{% } %}
        </td>
        <td>
            <span class="size">{%=o.formatFileSize(file.fileSize)%}</span>
        </td>
    </tr>
	{% } %}
	</script>
</html>

