<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="h" uri="/WEB-INF/tags/tags.tld"%>

<!DOCTYPE html>
<html>
<head>
<jsp:include page="../../common/header.jsp" />
	<link href="${pageContext.request.contextPath}/js/fuelux/css/fuelux.min.css?v=${version}" rel="stylesheet">
<style type="text/css">
</style>
</head>
<body class="fuelux">
	<div class="row">
		<div class="col-xs-12">
			<div class="panel">
				<header class="panel-heading">
					组织机构
				</header>
				<div class="panel-body pre-scrollable">
					<ul class="tree tree-folder-select" role="tree" id="departmentTree">
							  <li class="tree-branch hide" data-template="treebranch" role="treeitem" aria-expanded="false">
							    <div class="tree-branch-header">
							      <!-- <button type="button" class="glyphicon icon-caret glyphicon-play"><span class="sr-only">Open</span></button> -->
							      <button type="button" class="fa fa-caret-right glyphicon icon-caret glyphicon-play"><span class="sr-only">Open</span></button>
							      <button type="button" class="tree-branch-name">
							        <span class="fa fa-folder glyphicon icon-folder glyphicon-folder-close" style="width: 0px;font-size: 0px;"></span>
							        <span class="tree-label"></span>
							      </button>
							    </div>
							    <ul class="tree-branch-children" role="group"></ul>
							    <div class="tree-loader" role="alert">Loading...</div>
							  </li>
							  <li class="tree-item hide" data-template="treeitem" role="treeitem">
							    <button type="button" class="tree-item-name">
							      <!-- <span class="glyphicon icon-item fueluxicon-bullet"></span> -->
							      <span class="tree-label"></span>
							    </button>
							  </li>
						</ul>
				</div>
			</div>
		</div>		
	</div>
	<script src="${pageContext.request.contextPath}/js/fuelux/js/fuelux.js?v=${version}"></script>
	<script src="${pageContext.request.contextPath}/js/scripts.js?v=${version}"></script>
	<script type="text/javascript">
		var dsJson =  jQuery.parseJSON('${dsJson}');
	</script>
	<script src="${pageContext.request.contextPath}/js/page/group/selGroupList.js?v=${version}"></script>
</body>
</html>
