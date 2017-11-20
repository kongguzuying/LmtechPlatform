<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="/WEB-INF/tags/tags.tld" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
            <form class="form-inline" action="${pageContext.request.contextPath}/mobileres/list.do" pageres="form"
                  method="post">
                <div class="adv-table">
                    <div class="row-fluid">
                        <div class="span3">
                            <div id="dynamic-table_length" class="dataTables_length">
                                <div class="form-group">
                                    <input type="text" class="form-control" name="filter_LIKES_code"
                                           value="${filter_LIKES_code}" placeholder="请输入资源编号">
                                </div>
                                <div class="form-group">
                                    <input type="text" class="form-control" name="filter_LIKES_name"
                                           value="${filter_LIKES_name}" placeholder="请输入资源名称">
                                </div>
                                <button type="button" onclick="submitSearch()" class="btn btn-primary">搜索</button>
                            </div>
                        </div>
                        <div class="span3">
                            <div class="dataTables_filter" id="dynamic-table_filter">
                                <%--<h:hasPermission resId="PageResource:Add">--%>
                                    <a href="${pageContext.request.contextPath}/mobileres/edit.do?id="
                                       class="btn btn-primary">添加</a>
                                <%--</h:hasPermission>--%>
                            </div>
                        </div>
                    </div>

                    <table class="display table table-bordered" id="dynamic-table">
                        <thead>
                        <tr>
                            <th width="15%">资源编号</th>
                            <th width="15%">资源名称</th>
                            <th width="10%">分类编号</th>
                            <th width="10%">分类名称</th>
                            <th width="15%">资源描述</th>
                            <th width="6%">是否启用</th>
                            <th width="10%">操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${pageData.items}" var="item">
                            <tr class="gradeU">
                                <td>${item.code}</td>
                                <td>${item.name}</td>
                                <td>${item.categoryCode}</td>
                                <td>${item.categoryName}</td>
                                <td>${item.remark}</td>
                                <td>${item.enable}</td>
                                <td>
                                    <div class="btn-group">
                                        <a class="btn btn-default btn-xs"
                                           href="${pageContext.request.contextPath}/mobileres/edit.do?id=${item.code}">编辑</a>
                                        <a class="btn btn-default btn-xs"
                                               href="javascript:listRemove('${pageContext.request.contextPath}/mobileres/remove.do?id=${item.code}')">删除</a>
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

</body>
</html>
