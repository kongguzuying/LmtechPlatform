<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
  <head>
    <title></title>
    <jsp:include page="../../common/header.jsp"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/jquery-treeTable/jquery.treeTable.css"/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-treeTable/jquery.treeTable.js"></script>
	<style type="text/css">
	  .tinyImg {
		  width: 100px;
		  height: 40px;
		  vertical-align: middle;
	  }
	  </style>
	<script type="text/javascript">
		$(function () {;
			$(".treeTable").treeTable({initialState: "expanded"});

			var parentId = $("#parentId").val();
			$("input:radio").each(function () {
				if (parentId == $(this).val()) {
					$(this).attr("checked", "checked");
				}
			});
		});
		function selParent(elem) {;
			var parentId = $(elem).val();
			var parentName = $(elem).attr("parentName");
			$("#parentId").val(parentId);
			$("#parentName").val(parentName);
		}
	</script>
  </head>

  <body class="body">
  	<form>
  	<input type="hidden" name="parentId" id="parentId" value="${parentId}"/>
	<input type="hidden" id="parentName"/>
    <table id="treeTable" cellspacing=0 class="table treeTable table-bordered">
		<thead>
			<tr>
				<th width="50%">标题</th>
				<th>FTP卡面</th>
			</tr>
		</thead>
		<tbody>
            <c:forEach items="${entitys}" var="entity">
				<tr id="${entity.id}" <c:if test="${!entity.root}"> class="child-of-${entity.parentId}"</c:if>>
					<td class="left-td"><input type="radio" name="ids" onclick="selParent(this)" value="${entity.id}" parentName="${entity.name}" />${entity.name}</td>
					<td><c:if test="${!(empty entity.background)}"><img src="${entity.background}" class="tinyImg"/></c:if></td>
				</tr>
            </c:forEach>
		</tbody>
	</table>
	</form>
  </body>
</html>
