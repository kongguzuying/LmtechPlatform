<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="h" uri="/WEB-INF/tags/tags.tld"%>

<!DOCTYPE html>
<html>
<head>
<jsp:include page="../../common/header.jsp" />
<style type="text/css">
.margen{
	margin: 2px 4px;
	float: left;
}
.p_10{
	padding: 2px 2px 2px 20px;
}
#selectUserContainer{
margin: 10px;
}
</style>
</head>
<body style="font-family: Helvetica Neue, Luxi Sans, DejaVu Sans, Hiragino Sans GB, STHeiti, 'Microsoft YaHei'">
	<div class="row p_10" >
		<form>
		<input type="hidden" id="userId" name="userId" value="${uid }">
		<input type="hidden" id="departmentId" name="departmentId" value="${did }">
		<div id="selectUserContainer">
			<c:forEach items="${rs }" var="entity">
				<div class="margen">${entity.name }
				<input name="roleId" type="checkbox" id="${entity.id }" value="${entity.id }">
				</div>
			</c:forEach>
		</div>
		</form>
	</div>
	<script type="text/javascript">
	var selJson = $.parseJSON('${selJson}');
	$(function(){
		initSelect(selJson);
	});
	//初始化选中
	function initSelect(json){
		if(json && json.length > 0){
			for(var i = 0 ; i  < json.length ; i ++){
				var obj = json[i];
				$("#"+obj.roleId).attr("checked","checked");
			}
		}
	}
	
	function validateForm(){
		return true;
	}
	 		 	
	</script>
</body>
</html>
