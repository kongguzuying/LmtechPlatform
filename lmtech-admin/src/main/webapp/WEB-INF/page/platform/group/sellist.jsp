<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="h" uri="/WEB-INF/tags/tags.tld"%>

<!DOCTYPE html>
<html>
<head>
<jsp:include page="../../common/header.jsp" />
	<link href="${pageContext.request.contextPath}/js/fuelux/css/fuelux.min.css?v=${version}" rel="stylesheet">
<style type="text/css">
.h_80{
min-height: 100px;
margin: 10px 40px 0px 40px;
border: solid 1px #E6E6E6;
}
.margen10{
margin: 2px 5px;
float: left;
}
.tag-bg{
background-color: #fff!important;
}
.scrollable-x{
    /* overflow-x: scroll; */
    overflow: auto;
    height: 200px;
}
.no-scroll{
	overflow: hidden!important;
	width: 500px;
}
#departmentTree{
border: 0px!important;
}
</style>
</head>
<body class="fuelux">
	<div class="row">
		<div class="col-xs-12 text-center" style="margin: 2px 15px 0px 0px;"><input placeholder="请输入用户名或者账号检索用户" type="text" id="searchKey" style="width:750px;"></div>
		<div class="col-xs-12 text-center"><span style="font-weight:bold;font-size:14px;color: #535351;">已选择用户<span id="userCount"></span></span></div>
		<div id="selectUserContainer" class="h_80 pre-scrollable"></div>
	</div>
	<div class="row">
		<div class="col-xs-6">
			<div class="panel">
				<header class="panel-heading">
					组织机构
				</header>
				<div class="panel-body scrollable-x">
					<ul class="tree tree-folder-select no-scroll" role="tree" id="departmentTree">
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
		<div class="col-xs-6">
			<div class="panel">
				<header class="panel-heading">用户列表
				</header>
				<div class="panel-body scrollable-x" id="userInfoContainer">
					请选择组织机构
				</div>
				<div id="tableContainer" class="hide">
					<table class="table table-hover">
	                       <thead>
	                       <tr>
	                           <th width="15%">序号</th>
	                           <th width="40%">姓名</th>
	                           <th width="35%">账号</th>
	                           <th width="10%"><input type="checkbox" id="chkAll"></th>
	                       </tr>
	                       </thead>
	                       <tbody>
	                       <tr>
	                           <td>{0}</td>
	                           <td>{1}</td>
	                           <td>{2}</td>
	                           <td><input type="checkbox" id="{3}" uname="{4}"></td>
	                       </tr>
	                       </tbody>
	                   </table>
				</div>
			</div>
		</div>
	</div>
	<script src="${pageContext.request.contextPath}/js/fuelux/js/fuelux.js?v=${version}"></script>
	<script src="${pageContext.request.contextPath}/js/scripts.js?v=${version}"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap-typeahead.js?v=${version}"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/page/user/user-autocomplete.js?v=${version}"></script>
 	<script type="text/javascript">
	 		var dsJson =  jQuery.parseJSON('${dsJson}');
	</script>
	<script src="${pageContext.request.contextPath}/js/page/department/sellist.js?v=${version}"></script>
</body>
</html>
