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
min-height: 40px;
margin: 10px 40px 0px 40px;
border: solid 1px #E6E6E6;
}
.margen10{
margin: 2px 5px;
float: left;
}
.tree-actions i{
	margin-right: 5px;
}
.scoller{
	overflow-y:scroll;
}
.form-control-temp {
	display:inline !important;
}

</style>
</head>
<body class="fuelux">
	<div class="col-xs-5">
		<div class="panel">
			<header class="panel-heading">
				组织机构
			</header>
			<div class="panel-body scoller">
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
	<div class="col-xs-7">
		<div class="panel">
			<header class="panel-heading">
			<label style="font-weight:normal">用户列表</label>			
			<label style="font-weight:normal">
			<input type="text" class="form-control form-control-temp" style="width:auto" id="userSearchKey">
			</label>
			 <span class="tools pull-right">
					<button class="btn btn-success" onclick="addUser()">添加</button>
				</span>
			</header>
			<div class="panel-body scoller" id="userInfoContainer">
				请选择组织机构
			</div>
			<div id="tableContainer" class="hide">
				<table class="table table-hover">
                       <thead>
                       <tr>
                           <th width="7%">序号</th>
                           <th width="25%">姓名</th>
                           <%--<th width="10%">账号</th>--%>
                           <th width="10%">状态</th>
                           <th width="48%">操作</th>
                       </tr>
                       </thead>
                       <tbody>
                       <tr>
                           <td>{0}</td>
                           <td>{1}</td>
                           <%--<td>{2}</td>--%>
                           <td>{5}</td>
                           <td>
                           <a class="btn btn-default btn-xs" href="javascript:updateUser('{3}',{4});">修改</a>
                           <a class="btn btn-default btn-xs" href="javascript:disableUser('{3}',{4});">{6}</a>
                           <a class="btn btn-default btn-xs" href="javascript:removeUser('{3}',{4});">删除</a>
                           <a class="btn btn-default btn-xs" href="javascript:moveUser('{3}',{4});">移动</a>
                           <a class="btn btn-default btn-xs" href="javascript:setPw('{3}',{4});">设置密码</a>
                           <a class="btn btn-default btn-xs" href="javascript:resetPw('{3}',{4});">重置密码</a>
                           <a class="btn btn-default btn-xs" title="设置用户角色" href="javascript:setUserRole('{3}');">角色</a>
                            </td>
                       </tr>
                       </tbody>
                   </table>
			</div>
		</div>
	</div>
	<script src="${pageContext.request.contextPath}/js/fuelux/js/fuelux.js?v=${version}"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap-typeahead.js?v=${version}"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/page/user/user-autocomplete.js?v=${version}"></script>
 	<script type="text/javascript">
	//部门id
	//var parentId = '${parentId}';
	var dsJson =  jQuery.parseJSON('${dsJson}');
	
	</script>
	<script src="${pageContext.request.contextPath}/js/page/group/list.js?v=${version}"></script>
</body>
</html>
