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
                url: "${pageContext.request.contextPath}/platform/code/edit.do?id=" + (id ? id : ""),
                title: !id ? "代码类型-添加" : "代码类型-编辑",
                opener: window,
                refreshOpener: false,
                width: 600,
                height: 400,
                onFinished: function (exeResult) {
                    $("form").submit();
                }
            });
        }

        function submitSearch() {
            $("form")[0].submit();
        }
    </script>
</head>

<body class="body">

<div class="row">
    <div class="col-lg-12">
        <section class="panel">
            <form class="form-inline" action="${pageContext.request.contextPath}/platform/code/list.do" role="form"
                  method="post">
                <div class="adv-table">
                    <div class="row-fluid">
                        <div class="span3">
                            <div id="dynamic-table_length" class="dataTables_length">
                                <div class="form-group">
                                    <input type="text" class="form-control" name="filter_LIKES_code"
                                           value="${filter_LIKES_code}" placeholder="请输入代码类型">
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
                            <th>序号</th>
                            <th>代码类型</th>
                            <th>代码名</th>
                            <th>父代码类型</th>
                            <th>备注</th>
                            <%--<th width="8%">创建人</th>
                            <th width="15%">创建时间</th>
                            <th width="8%">更新人</th>
                            <th width="15%">更新时间</th>--%>
                            <th width="20%">操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${pageData.items}" var="item" varStatus="status">
                            <tr class="gradeU">
                                <td>${status.index + 1}</td>
                                <td>${item.code}</td>
                                <td>${item.name}</td>
                                <td>${item.parentCode}</td>
                                <td>${item.remark}</td>
                                    <%--<td>${item.createUser}</td>
                                    <td><fmt:formatDate value="${item.createDate}" pattern="yyyy-MM-dd  HH:mm:ss" /></td>
                                    <td>${item.updateUser}</td>
                                    <td><fmt:formatDate value="${item.updateDate}" pattern="yyyy-MM-dd  HH:mm:ss" /></td>--%>
                                <td>
                                    <div class="btn-group">
                                        <a class="btn btn-default btn-xs" onclick="edit('${item.code}');return false;">编辑</a>
                                        <a href="javascript:listRemove('${pageContext.request.contextPath}/platform/code/remove.do?id=${item.code}')"
                                           class="btn btn-default btn-xs">删除</a>
                                        <a href="${pageContext.request.contextPath}/platform/code/itemList.do?code=${item.code}"
                                           class="btn btn-default btn-xs">编辑子项</a>
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
