<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="/WEB-INF/tags/tags.tld" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="../../common/header.jsp"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/js/data-tables/DT_bootstrap.css"/>
    <style type="text/css">
    .padding_r{
    padding-right: 0px!important;
    }
    .padding_l{
    padding-left: 0px!important;
    }
    </style>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/checklist.js"></script>
</head>

<body class="body">
<div class="row">
    <div class="col-lg-12">
        <section class="panel">
            <form action="${pageContext.request.contextPath}/platform/ruser/check.do" method="post">
                <div class="adv-table">
                	<div class="row-fluid">
                    	<div class="span3 col-md-5">
                             <div id="dynamic-table_length" class="dataTables_length">
                                 <div class="form-group" >
				                 	<div class="col-md-3 padding_r">
				                 		<input type="text" class="form-control  padding_l" id="userName" maxlength="64" name="userName"
				                             value="${userName}" placeholder="用户姓名">
				                 		</div>
				                 		<div class="col-md-3 padding_r">
				                 		 <input type="text" class="form-control  padding_l" id="loginName" maxlength="64" name="loginName"
				                             value="${loginName}" placeholder="用户帐户">
				                 		</div>
				                 		<div class="col-md-4 padding_r">
				                 		 <input type="text" class="form-control  padding_l" id="mobile" maxlength="22" name="mobile"
				                             value="${mobile}" placeholder="用户手机号码">
				                 		</div>
				                 		<div class="col-md-2 padding_r">
				                 		  <button type="button" id="searchBtn" class="btn btn-primary">搜索</button>
				                 		</div>
				                </div>
                            </div>
                        </div>
                     </div>  
                    <div class="row-fluid">
                        <div class="span3 col-md-7">
                            <div class="dataTables_length">
                                <input type="hidden" id="id" name="id" value="${id}"/>
                                <input type="hidden" id="viewSelIds" name="viewSelIds" value="${viewSelIds}"/>
                                <input type="hidden" id="unSelectedIds" name="unSelectedIds" value="${unSelectedIds}"/>
                                <input type="hidden" id="type" name="type" value="${type}"/>
                                
                              
                                <div class="btn-group">
                                   	<c:if test="${type == 0 || type == 2}">
                                      <a href="javascript:void(0)" class="btn btn-primary_gray tb-btn"
                                       onclick="submitAjaxForm({ message: '授权成功！' })">授权</a>
                                	</c:if>
                                	<c:if test="${type == 1}">
                                      <a href="javascript:void(0)" class="btn btn-primary_gray tb-btn"
                                       onclick="submitAjaxForm({ message: '取消授权成功！' })">取消授权</a>
                                	</c:if>
                                    <a href="${pageContext.request.contextPath}/platform/ruser/checklist.do?id=${id}&type=0"
                                       class="btn <c:choose><c:when test="${type==0}">btn-success</c:when><c:otherwise>btn-default</c:otherwise></c:choose> tb-btn">所有用户</a>
                                    <a href="${pageContext.request.contextPath}/platform/ruser/checklist.do?id=${id}&type=1"
                                       class="btn <c:choose><c:when test="${type==1}">btn-success</c:when><c:otherwise>btn-default</c:otherwise></c:choose> tb-btn">已授权用户</a>
                                    <a href="${pageContext.request.contextPath}/platform/ruser/checklist.do?id=${id}&type=2"
                                       class="btn <c:choose><c:when test="${type==2}">btn-success</c:when><c:otherwise>btn-default</c:otherwise></c:choose> tb-btn">未授权用户</a>
                                    <a href="${pageContext.request.contextPath}/platform/role/list.do"
                                       class="btn btn-default tb-btn">返回</a>
                                </div>
                                <%--<div class="btn-group">
                                	<a href="javascript: exportExcel('${id}')"
                                    class="btn btn-primary">导出</a>
                                </div>--%>
                            </div>
                        </div>
                    </div>
                    <table cellspacing=0 class="display table table-bordered" id="dynamic-table">
                        <thead>
                        <tr>
                            <th class="left-th" width="10%"><input id="allBtn" type="checkbox" onclick="checkAll(this)"/>全选</th>
                            <th width="25%">姓名</th>
                            <th>手机号码</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${datas}" var="data">
                            <tr>
                                <td class="left-td"><input type="checkbox" name="selectedIds"
                                           value="${data.id}"/>
                                </td>
                                <td>${data.name}</td>
                                <td>${data.mobile}</td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                    <h:pager pager="${pageData}"
                             url="${pageContext.request.contextPath}/platform/ruser/checklist.do?type=${type}&id=${id}"></h:pager>
                </div>
            </form>
        </section>
    </div>
</div>
<script type="text/javascript">
$(function(){
	$('#searchBtn').click(function(){
		 var action = $("form").attr("action");
	     action = action.replace("check.do", "checklist.do");
	     $("form").attr("action", action);
	     $("form").submit();
	//     submitAjaxForm({ message: '操作成功！' });
	});
});

function exportExcel(rid) {
	if(rid){
		var _url = URL_BASE+"/platform/ruser/export.do?rid="+rid;
		window.location.href = _url;
	}else{
		top.alertMessage("请选择角色");
	}	
}

</script>
</body>
</html>
