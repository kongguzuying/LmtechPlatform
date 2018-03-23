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
                url: "${pageContext.request.contextPath}/platform/lottery/edit.do?id=" + (id ? id : ""),
                title: !id ? "积分抽奖奖品-添加" : "积分抽奖奖品-编辑",
                opener: window,
                refreshOpener: false,
                width: 600,
                height: 500,
                onFinished: function (exeResult) {
                    $("form").submit();
                }
            });
        }

        function submitSearch() {
            $("form")[0].submit();
        }
    </script>
    <style type="text/css">
        .tinyImg {
            width: 40px;
            height: 40px;
            vertical-align: middle;
        }
    </style>
</head>

<body class="body">

<div class="row">
    <div class="col-lg-12">
        <section class="panel">
            <form class="form-inline" action="${pageContext.request.contextPath}/platform/lottery/list.do" role="form"
                  method="post">
                <div class="adv-table">
                    <div class="row-fluid">
                        <div class="span3">
                            <div id="dynamic-table_length" class="dataTables_length">
                                <div class="form-group">
                                    <input type="text" class="form-control" name="filter_LIKES_name"
                                           value="${filter_LIKES_name}" placeholder="请输入商品名称">
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
                            <th width="5%">序号</th>
                            <th width="20%">商品名称</th>
                            <th width="15%">商品图片</th>
                            <th width="5%">排序号</th>
                            <th width="5%">奖品类型</th>
                            <th width="5%">是否中奖</th>
                            <th width="10%">是否参与中奖</th>
                            <%--<th width="8%">创建人</th>
                            <th width="15%">创建时间</th>
                            <th width="8%">更新人</th>
                            <th width="15%">更新时间</th>--%>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${pageData.items}" var="item" varStatus="status">
                            <tr class="gradeU">
                                <td>${status.index + 1}</td>
                                <td>${item.name}</td>
                                <td>
                                    <c:if test="${!(empty item.imageUrl)}"><img src="${item.imageUrl}" class="tinyImg"/></c:if>
                                </td>
                                <td>${item.sortNo}</td>
                                <td>${item.type}</td>
                                <td>${item.prize ? '是' : '否'}</td>
                                <td>${item.join ? '是' : '否'}</td>
                                <%--<td>${item.createUser}</td>
                                <td><fmt:formatDate value="${item.createDate}" pattern="yyyy-MM-dd  HH:mm:ss" /></td>
                                <td>${item.updateUser}</td>
                                <td><fmt:formatDate value="${item.updateDate}" pattern="yyyy-MM-dd  HH:mm:ss" /></td>--%>
                                <td>
                                    <div class="btn-group">
                                        <a class="btn btn-default btn-xs" onclick="edit('${item.id}');return false;">编辑</a>
                                        <a href="javascript:listRemove('${pageContext.request.contextPath}/platform/lottery/remove.do?id=${item.id}')"
                                           class="btn btn-default btn-xs">删除</a>
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
