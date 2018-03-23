<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="/WEB-INF/tags/tags.tld" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="lm" uri="/WEB-INF/tags/tags.tld" %>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="../../common/header.jsp"/>

    <!--dynamic table-->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/js/data-tables/DT_bootstrap.css"/>

    <script type="text/javascript">
        function edit(id) {
            top.showModal({
                url: "${pageContext.request.contextPath}/platform/tenancy/edit.do?id=" + (id ? id : ""),
                title: !id ? "租户-添加" : "租户-编辑",
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
            window.location.href = "${pageContext.request.contextPath}/platform/tenancy/remove.do?id=" + id;
        }
        function activeTenancy(code) {
            $.ajax({
               url: "${pageContext.request.contextPath}/platform/tenancy/activeTenancy.do?code=" + code,
               success: function (data) {
                   if (data.success) {
                       window.location.reload();
                   } else {
                       alert(data.message);
                   }
               }
            });
        }
        function disableTenancy(code) {
            $.ajax({
                url: "${pageContext.request.contextPath}/platform/tenancy/disableTenancy.do?code=" + code,
                success: function (data) {
                    if (data.success) {
                        window.location.reload();
                    } else {
                        alert(data.message);
                    }
                }
            });
        }
    </script>
</head>

<body class="body">

<div class="row">
    <div class="col-lg-12">
        <section class="panel">
            <form class="form-inline" action="${pageContext.request.contextPath}/platform/tenancy/list.do" role="form"
                  method="post">
                <div class="adv-table">
                    <div class="row-fluid">
                        <div class="span3">
                            <div id="dynamic-table_length" class="dataTables_length">
                                <div class="form-group">
                                    <input type="text" class="form-control" name="filter_LIKES_name"
                                           value="${filter_LIKES_name}" placeholder="请输入租户名称">
                                </div>
                                <input type="submit" class="btn btn-primary" value="搜索"/>
                            </div>
                        </div>
                        <div class="span3">
                            <div class="dataTables_filter" id="dynamic-table_filter">
                                <a class="btn btn-primary" onclick="edit('');return false;">添加</a>
                            </div>
                        </div>
                    </div>

                    <table class="display table table-bordered" id="dynamic-table">
                        <thead>
                        <tr>
                            <th width="12%">租户名称</th>
                            <th width="8%">租户代码</th>
                            <th width="20%">租户信息</th>
                            <th width="10%">状态</th>
                            <th width="10%">联系电话</th>
                            <th width="10%">联系地址</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${pageData.items}" var="item">
                            <tr class="gradeU">
                                <td>${item.name}</td>
                                <td>${item.code}</td>
                                <td>${item.info}</td>
                                <td><h:codeText codeType="tenancyStatus" itemValue="${item.status}"></h:codeText></td>
                                <td>${item.mobile}</td>
                                <td>${item.address}</td>
                                <td>
                                    <div class="btn-group">
                                        <a class="btn btn-default btn-xs" onclick="edit('${item.id}');return false;">编辑</a>
                                        <a class="btn btn-default btn-xs" onclick="activeTenancy('${item.code}');">正常营业</a>
                                        <a class="btn btn-default btn-xs" onclick="disableTenancy('${item.code}');">停止营业</a>
                                        <a class="btn btn-default btn-xs"
                                           href="javascript:listRemove('${pageContext.request.contextPath}/platform/tenancy/remove.do?id=${item.id}')">删除</a>
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
