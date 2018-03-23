<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/WEB-INF/tags/tags.tld" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="../common/header.jsp"/>
    <!--dynamic table-->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/js/data-tables/DT_bootstrap.css"/>
</head>

<body class="body">
<div class="row">
    <div class="col-lg-12">
        <section class="panel">
            <form class="form-inline" action="${pageContext.request.contextPath}/logconfig/list.do" role="form"
                  method="post">
                <div class="adv-table">
                     <div class="row-fluid">
                        <div class="span3">
                            <div id="dynamic-table_length" class="dataTables_length">
                                <div class="form-group">
                                    <input type="text" class="form-control" name="filter_LIKES_targetDesc"
                                           value="${filter_LIKES_targetDesc}" placeholder="请输业务类名称">
                                </div>
                                <button type="button" onclick="submitSearch()" class="btn btn-primary">搜索</button>
                            </div>
                        </div>
                        <div class="span3">
                            <div class="dataTables_filter" id="dynamic-table_filter">
                                <%--<h:hasPermission resId="Role:Add">--%>
                                    <a class="btn btn-primary" onclick="edit('');return false;">添加</a>
                                <%--</h:hasPermission>--%>
                            </div>
                        </div>
                    </div>
                    <table class="display table table-bordered" id="dynamic-table">
                        <thead>
                        <tr>
                            <th width="20%">业务类名</th>
                            <th width="15%">业务类</th>
                            <th width="10%">方法名</th>
                            <th width="10%">方法</th>
                            <th width="15%">操作人</th>
                            <th width="20%">操作时间</th>
                            <th width="20%">操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${pageData.items}" var="item">
                            <tr class="gradeU">
	                            <td>${item.targetDesc}</td>
                                <td>${item.target}</td>
                                <td>${item.methodDesc}</td>
                            	<td>${item.method}</td>
                                <td>${item.createUser}</td>
                                <td><fmt:formatDate value="${item.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                                <td>
                                <div class="btn-group">
                                      <a class="btn btn-default btn-xs" onclick="edit('${item.id}');return false;">编辑</a>
                                      <a class="btn btn-default btn-xs"
                                               href="javascript:listRemove('${pageContext.request.contextPath}/logconfig/remove.do?id=${item.id}')">删除</a>
                                 </div>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
                <h:pager pager="${pageData}"></h:pager>
            </form>
        </section>
    </div>
</div>
<script type="text/javascript">
        function edit(id) {
        	var title = (id)?"修改":"添加"
            top.showModal({
                url: "${pageContext.request.contextPath}/logconfig/edit.do?id=" + (id ? id : ""),
                title: "日志配置-"+title,
                opener: window,
                refreshOpener: false,
                width: 600,
                height: 400,
                onFinished: function (exeResult) {
                    //window.location.reload();
                    $("form").submit();
                }
            });
        }
        
        function del(id) {
            window.location.href = "${pageContext.request.contextPath}/logconfig/remove.do?id=" + id;
        }
        
</script>
</body>
</html>
