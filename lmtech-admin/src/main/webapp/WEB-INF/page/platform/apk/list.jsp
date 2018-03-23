<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="/WEB-INF/tags/tags.tld" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="../common/header.jsp"/>

    <!--dynamic table-->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/js/data-tables/DT_bootstrap.css"/>

    <script type="text/javascript">
        function edit(id) {
            top.showModal({
                url: "${pageContext.request.contextPath}/apk/edit.do?id=" + (id ? id : ""),
                title: "Apk - 添加",
                opener: window,
                width: 750,
                height: 500
            });
        }
        function del(id) {
            window.location.href = "${pageContext.request.contextPath}/apk/remove.do?id=" + id;
        }
    </script>
</head>

<body class="body">

<div class="row">
    <div class="col-lg-12">
        <section class="panel">
            <form class="form-inline" action="${pageContext.request.contextPath}/apk/list.do" role="form"
                  method="post">
                <div class="adv-table">
                    <div class="row-fluid">
                        <div class="span3">
                            <div id="dynamic-table_length" class="dataTables_length">

                            </div>
                        </div>
                        <div class="span3">
                            <div class="dataTables_filter" id="dynamic-table_filter">
                                <a href="javascript:edit()"
                                   class="btn btn-primary">添加</a>
                            </div>
                        </div>
                    </div>

                    <table class="display table table-bordered" id="dynamic-table">
                        <thead>
                        <tr>
                            <th width="10%">APK编号</th>
                            <th width="10%">APK名字</th>
                            <th width="10%">APK版本</th>
                            <th width="10%">APK文件名</th>
                            <th width="10%">APK文件大小</th>
                            <th width="10%">是否最新版本</th>
                            <th width="20%">APK描述</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${pageData.items}" var="item">
                            <tr class="gradeU">
                                <td>${item.apkCode}</td>
                                <td>${item.apkName}</td>
                                <td>${item.apkVersion}</td>
                                <td>${item.apkFileName}</td>
                                <td>${item.apkSize}</td>
                                <td>
	                                <c:if test="${item.haveBestNew == true}">是</c:if>
	                                <c:if test="${item.haveBestNew == false}">否</c:if>
                                </td>
                                <td>${item.apkDesc}</td>
                                <td>
                                    <div class="btn-group">
                                        <a class="btn btn-default btn-xs"
                                           href="javascript:edit('${item.id}')">修改</a>
                                        <a class="btn btn-default btn-xs"
                                           href="javascript:listRemove('${pageContext.request.contextPath}/apk/remove.do?id=${item.id}')">删除</a>
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
