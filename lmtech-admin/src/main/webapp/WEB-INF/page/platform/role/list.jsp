<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="/WEB-INF/tags/tags.tld" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="../../common/header.jsp"/>

    <!--dynamic table-->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/js/data-tables/DT_bootstrap.css"/>

    <script type="text/javascript">
        function edit(id) {
            top.showModal({
                url: "${pageContext.request.contextPath}/platform/role/edit.do?id=" + (id ? id : ""),
                title: !id ? "角色-添加" : "角色-编辑",
                opener: window,
                refreshOpener: false,
                width: 600,
                height: 400,
                onFinished: function (exeResult) {
                    $("form").submit();
                }
            });
        }
        
        function del(id) {
            window.location.href = "${pageContext.request.contextPath}/platform/role/remove.do?id=" + id;
        }
        
    </script>
</head>

<body class="body">

<div class="row">
    <div class="col-lg-12">
        <section class="panel">
            <form class="form-inline" action="${pageContext.request.contextPath}/platform/role/list.do" role="form"
                  method="post">
                <div class="adv-table">
                    <div class="row-fluid">
                        <div class="span3">
                            <div id="dynamic-table_length" class="dataTables_length">
                                <div class="form-group">
                                    <input type="text" class="form-control" name="filter_LIKES_name"
                                           value="${filter_LIKES_name}" placeholder="请输入角色名称">
                                </div>
                                <input type="submit" class="btn btn-primary" value="搜索"/>
                            </div>
                        </div>
                        <div class="span3">
                            <div class="dataTables_filter" id="dynamic-table_filter">
                            	<%--<h:isSuperAdmin>--%>
                                    <a class="btn btn-primary" onclick="edit('');return false;">添加</a>
                                <%--</h:isSuperAdmin>--%>
                            </div>
                        </div>
                    </div>

                    <table class="display table table-bordered" id="dynamic-table">
                        <thead>
                        <tr>
                            <th width="12%">角色名称</th>
                            <th width="8%">级别</th>
                            <th width="20%">角色描述</th>
                            <th width="10%">是否可用</th>
                            <th width="10%">更新时间</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${pageData.items}" var="item">
                            <tr class="gradeU">
                                <td>${item.name}</td>
                                <td>${item.level}</td>
                                <td>${item.remark}</td>
                                <td><c:if test="${item.enable}">可用</c:if><c:if test="${!item.enable}">不可用</c:if></td>
                                <td><fmt:formatDate value="${item.updateDate}" pattern="yyyy-MM-dd  HH:mm:ss" /></td>
                                <td>
                                    <div class="btn-group">
                                        <%--<h:hasPermission resId="Role:Edit">--%>
                                            <a class="btn btn-default btn-xs" onclick="edit('${item.id}');return false;">编辑</a>
                                        <%--</h:hasPermission>--%>
                                        <c:if test="${item.enable}">
                                        <%-- <h:hasPermission resId="Role:UserPerm">--%>
                                            <a class="btn btn-default btn-xs"
                                               href="${pageContext.request.contextPath}/platform/ruser/checklist.do?id=${item.id}&type=0">授权用户</a>
                                        <%--</h:hasPermission>--%>
                                        </c:if>
                                        <%--<h:hasPermission resId="Role:MenuPerm">--%>
                                            <a class="btn btn-default btn-xs"
                                               href="${pageContext.request.contextPath}/platform/rmenu/checklist.do?id=${item.id}&type=0">授权菜单</a>
                                        <%--</h:hasPermission>--%>
                                        <%--<h:hasPermission resId="Role:MobileResPerm">--%>
                                            <%--<a class="btn btn-default btn-xs"
                                               href="${pageContext.request.contextPath}/platform/rmobileres/checklist.do?id=${item.id}&type=0">授权移动端资源</a>--%>
                                        <%--</h:hasPermission>--%>
                                        <%--<h:hasPermission resId="Role:PageResPerm">--%>
                                            <%--<a class="btn btn-default btn-xs"
                                               href="${pageContext.request.contextPath}/platform/rpageres/checklist.do?id=${item.id}&type=0">授权页面资源</a>--%>
                                        <%--</h:hasPermission>--%>
                                        <%--<h:isSuperAdmin>--%>
                                            <a class="btn btn-default btn-xs"
                                               href="javascript:listRemove('${pageContext.request.contextPath}/platform/role/remove.do?id=${item.id}')">删除</a>
                                         <%--</h:isSuperAdmin>--%>
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
