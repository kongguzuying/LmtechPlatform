<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="/WEB-INF/tags/tags.tld" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="../common/header.jsp"/>

    <!--dynamic table-->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/js/data-tables/DT_bootstrap.css"/>

</head>
<script type="text/javascript">
        function info(id) {
            top.showModal({
                url: "${pageContext.request.contextPath}/appfeedback/info.do?id="+id,
                title: "反馈-详情",
                opener: window,
                width: 680,
                showFooter: false,
                height: 380
            });
        }
 </script>
<body class="body">

<div class="row">
    <div class="col-lg-12">
        <section class="panel">
            <form class="form-inline" action="${pageContext.request.contextPath}/appfeedback/list.do" role="form"
                  method="post">
                <div class="adv-table">
                    <div class="row-fluid">
                        <div class="span3">
                            <div id="dynamic-table_length" class="dataTables_length">
                                <div class="form-group">
                                    <input type="text" class="form-control" name="filter_LIKES_content"
                                           value="${filter_LIKES_content}" placeholder="请输入反馈内容">
                                </div>
                                <button type="button" onclick="submitSearch()" class="btn btn-primary">搜索</button>
                            </div>
                        </div>
                    </div>

                    <table class="display table table-bordered" id="dynamic-table">
                        <thead>
                        <tr>
                            <th width="15%">用户名称</th>
                            <th width="20%">部门</th>
                            <th width="15%">反馈时间</th>
                            <th width="50%">反馈内容</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${pageData.items}" var="item">
                            <tr class="gradeU">
                                <td>${item.userName}</td>
                                <td>${item.departmentName}</td>
                                 <td><fmt:formatDate  value="${item.date}" pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate></td>
                                <td style="word-break:break-all">${item.content}</td>
                                 <td>
                                    <div class="btn-group">
                                        <a class="btn btn-default btn-xs" href="javascript:info('${item.id}')" >详情</a>
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
