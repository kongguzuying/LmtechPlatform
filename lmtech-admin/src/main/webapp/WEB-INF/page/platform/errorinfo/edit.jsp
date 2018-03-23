<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/tags/tags.tld" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="../common/header.jsp"></jsp:include>

<link type="text/css" rel="stylesheet"
	href="${pageContext.request.contextPath}/js/bootstrap-editable/css/bootstrap-editable.css" />
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery.validate.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/bootstrap-editable/js/bootstrap-editable.js"></script>
<script type="text/javascript">
function editContent(id,content){
	top.requestData({
        url: URL_BASE+"/household/errorinfo/editContent.do?id=" + id+"&content="+content,
        type: "POST",
        success: function (exeResult) {
            if (exeResult.success) {
                top.showShortMessage({
                    text: "操作成功",
                    callback: function () {
                    	$('#cancleBtn').click();
                    }
                });
            } else {
                top.alertMessage(exeResult.message);
            }
        }
    });
}

$(function(){
	var modifyBtn=$('#modifyBtn');
	var submitBtn=$('#submitBtn');
	var cancleBtn=$('#cancleBtn');
	var contenSpan=$('#contenSpan');
	var textareaContent=$('#textareaContent');
	modifyBtn.click(function(){
		modifyBtn.attr("style","display:none");
		submitBtn.attr("style","display:inline");
		cancleBtn.attr("style","display:inline");
		contenSpan.attr("style","display:none");
		textareaContent.attr("style","display:inline");
		textareaContent.val(contenSpan.html());
	});
	cancleBtn.click(function(){
		modifyBtn.attr("style","display:inline");
		submitBtn.attr("style","display:none");
		cancleBtn.attr("style","display:none");
		contenSpan.attr("style","display:inline");
		textareaContent.attr("style","display:none");
		contenSpan.html(textareaContent.val());
	});
	var id=$('#id').val();
	submitBtn.click(function(){
		editContent(id,textareaContent.val());
	});
	
});

</script>	
</head>
<body class="body">
	<input type="hidden" id="id" value="${entity.id}" />
	<table class="table table-condensed table-bordered"
		style="border: 0; frame: void;">
		<tbody>
			<tr>
				<td style="text-align: left; width: 50%">系统代码：${entity.code}</td>
				<td style="text-align: left; width: 50%">业务接口：${entity.service}</td>
			</tr>
			<tr>
				<td style="text-align: left">业务参数：${entity.param}</td>
				<td style="text-align: left">创建日期：<fmt:formatDate  value="${entity.createDate}" pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate></td>
			</tr>
			<tr>
				<td style="text-align: left" colspan="2" style="word-wrap:break-word;word-break:break-all;">异常内容：<br/><span id="contenSpan">${entity.content}</span>
				<textarea id="textareaContent" cols="100" rows="20" style="display:none;"></textarea>
				<br/><button id="modifyBtn" >修改</button><button id="submitBtn" style="display:none;">完成</button><button id="cancleBtn" style="display:none;">取消</button>
				</td>
			</tr>
			<tr>
				<td style="text-align: left" colspan="2">处理状态：<c:if test='${entity.status eq "0"}'>待处理</c:if><c:if test='${entity.status eq "1"}'>已处理</c:if></td>
			</tr>
			
		</tbody>
	</table>

</body>
</html>
