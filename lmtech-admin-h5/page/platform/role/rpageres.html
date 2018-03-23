<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="/WEB-INF/tags/tags.tld" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="../../common/header.jsp"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/js/data-tables/DT_bootstrap.css"/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/checklist.js"></script>
</head>

<body class="body">
<div class="row">
    <div class="col-lg-12">
        <section class="panel">
            <form action="${pageContext.request.contextPath}/rpageres/check.do" method="post">
                <div class="adv-table">
                    <div class="row-fluid">
                        <div class="span3">
                            <div id="dynamic-table_length" class="dataTables_length">
                                <input type="hidden" id="id" name="id" value="${id}"/>
                                <input type="hidden" id="viewSelIds" name="viewSelIds" value="${viewSelIds}"/>
                                <input type="hidden" id="unSelectedIds" name="unSelectedIds" value="${unSelectedIds}"/>
                                <input type="hidden" id="type" name="type" value="${type}"/>
                                <div class="btn-group">
                                    <a href="javascript:void(0)" class="btn btn-primary_gray tb-btn"
                                       onclick="submitAjaxForm({ message: '授权成功！' })">授权</a>
                                    <a href="${pageContext.request.contextPath}/rpageres/checklist.do?id=${id}&type=0"
                                       class="btn <c:choose><c:when test="${type==0}">btn-success</c:when><c:otherwise>btn-default</c:otherwise></c:choose> tb-btn">所有页面资源</a>
                                    <a href="${pageContext.request.contextPath}/rpageres/checklist.do?id=${id}&type=1"
                                       class="btn <c:choose><c:when test="${type==1}">btn-success</c:when><c:otherwise>btn-default</c:otherwise></c:choose> tb-btn">已授权页面资源</a>
                                    <a href="${pageContext.request.contextPath}/rpageres/checklist.do?id=${id}&type=2"
                                       class="btn <c:choose><c:when test="${type==2}">btn-success</c:when><c:otherwise>btn-default</c:otherwise></c:choose> tb-btn">未授权页面资源</a>
                                    <a href="${pageContext.request.contextPath}/role/list.do"
                                       class="btn btn-default tb-btn">返回</a>
                                </div>
                            </div>
                        </div>
                    </div>
                    <table cellspacing=0 class="display table table-bordered" id="dynamic-table">
                        <thead>
                        <tr>
                            <th class="left-th" width="20%"><input id="allBtn" type="checkbox" onclick="checkAll(this)"/>全选&nbsp;资源编号</th>
                            <th width="20%">资源名称</th>
                            <th width="20%">所属模块</th>
                            <th width="20%">资源类型</th>
                            <th width="20%">是否启用</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${datas}" var="data">
                            <tr>
                                <td class="left-td"><input type="checkbox" name="selectedIds"
                                           value="${data.id}"/>&nbsp;&nbsp;${data.id}
                                </td>
                                <td>${data.name}</td>
                                <td>${data.moduleName}</td>
                                <td>${data.type}</td>
                                <td>${data.enable}</td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                    <h:pager pager="${pageData}"
                             url="${pageContext.request.contextPath}/rpageres/checklist.do?type=${type}&id=${id}"></h:pager>
                </div>
            </form>
        </section>
    </div>
</div>
</body>
</html>
