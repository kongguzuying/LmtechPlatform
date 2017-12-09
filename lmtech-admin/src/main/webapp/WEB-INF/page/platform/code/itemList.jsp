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
        function edit(id,typeCode) {
            top.showModal({
                url: "${pageContext.request.contextPath}/platform/code/itemEdit.do?code="+typeCode+"&id=" + (id ? id : ""),
                title: !id ? "代码类型-添加" : "代码类型-编辑",
                opener: window,
                refreshOpener: false,
                width: 600,
                height: 400,
                onFinished: function (exeResult) {
                    //$("form").submit();
                    window.location.href = "${pageContext.request.contextPath}/platform/code/itemList.do?code=" + $("input[name='typeCode']").val();
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
            <form class="form-inline" action="" role="form"
                  method="get">
                <input type="hidden" name="typeCode" value="${code}"/>
                <div class="adv-table">
                    <div class="row-fluid">
                        <div class="span3">
                            <div class="dataTables_filter" id="dynamic-table_filter">
                            	<a class="btn btn-primary" onclick="edit('','${code}');return false;">添加</a>
                                <a href="${pageContext.request.contextPath}/platform/code/list.do" class="btn btn-primary">返回</a>
                            </div>
                        </div>
                    </div>

                    <table class="display table table-bordered" id="dynamic-table">
                        <thead>
                        <tr>
                            <th>序号</th>
                            <th>编码</th>
                            <th>编码名</th>
                            <th>编码值</th>
                            <th>代码类型</th>
                            <th>排序</th>
                            <%--<th width="8%">创建人</th>
                            <th width="15%">创建时间</th>
                            <th width="8%">更新人</th>
                            <th width="15%">更新时间</th>--%>
                            <th width="20%">操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${pageData}" var="item" varStatus="status">
                            <tr class="gradeU">
                                <td>${status.index + 1}</td>
                                <td>${item.code}</td>
                                <td>${item.name}</td>
                                <td>${item.value}</td>
                                <td>${item.typeCode}</td>
                                <td>${item.sortNo}</td>
                                    <%--<td>${item.createUser}</td>
                                    <td><fmt:formatDate value="${item.createDate}" pattern="yyyy-MM-dd  HH:mm:ss" /></td>
                                    <td>${item.updateUser}</td>
                                    <td><fmt:formatDate value="${item.updateDate}" pattern="yyyy-MM-dd  HH:mm:ss" /></td>--%>
                                <td>
                                    <div class="btn-group">
                                        <a class="btn btn-default btn-xs" onclick="edit('${item.id}','${item.typeCode}');return false;">编辑</a>
                                        <a href="javascript:listRemove('${pageContext.request.contextPath}/platform/code/itemRemove.do?code=${code}&id=${item.id}')"
                                           class="btn btn-default btn-xs">删除</a>
                                    </div>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </form>
        </section>
    </div>
</div>

</body>
</html>
