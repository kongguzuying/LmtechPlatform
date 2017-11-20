<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="/WEB-INF/tags/tags.tld" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="../../common/header.jsp"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/js/data-tables/DT_bootstrap.css"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/jquery-treeTable/jquery.treeTable.css"/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-treeTable/jquery.treeTable.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/checklist.js"></script>
    <script type="text/javascript">
        $(function () {
            $(".treeTable").treeTable({initialState: "expanded"});
        });
        function checkClick(elem) {
            var checked = $(elem).is(':checked');
            var mid = $(elem).attr("mid");
            var mpid = $(elem).attr("mpid");
            if (checked) {
                //选中子集
                $("input[mpid='" + mid + "']").attr("checked", "checked");
                //选中父级
                $("input[mid='" + mpid + "']").attr("checked", "checked");
            } else {
                //取消选中子集
                $("input[mpid='" + mid + "']").attr("checked", false);
                if (isChildUnChecked(mpid)) {
                    //取消选中父级
                    $("input[mid='" + mpid + "']").attr("checked", false);
                }
            }
        }
        function isChildUnChecked(mpid) {
            var result = true;
            $("input[mpid='" + mpid + "']").each(function () {
                var isChecked = $(this).is(":checked");
                if (isChecked) {
                    result = false;
                    return;
                }
            });
            return result;
        }
    </script>
</head>

<body class="body">
<div class="row">
    <div class="col-lg-12">
        <section class="panel">
            <form action="${pageContext.request.contextPath}/platform/rmenu/check.do" method="post">
                <div class="adv-table">
                    <div class="row-fluid">
                        <div class="span3">
                            <div id="dynamic-table_length" class="dataTables_length">
                                <input type="hidden" id="id" name="id" value="${id}"/>
                                <input type="hidden" id="viewSelIds" name="viewSelIds" value="${viewSelIds}"/>
                                <input type="hidden" id="unSelectedIds" name="unSelectedIds" value="${unSelectedIds}"/>
                                <input type="hidden" id="type" name="type" value="${type}"/>
                                <div class="btn-group">
                                	<c:if test="${type == 0 || type == 2}">
                                      <a href="javascript:void(0)" class="btn btn-primary_gray tb-btn"
                                       onclick="submitAjaxForm({ message: '授权成功！' })">授权</a>
                                	</c:if>
                                	<c:if test="${type == 1}">
                                      <a href="javascript:void(0)" class="btn btn-primary_gray tb-btn"
                                       onclick="submitAjaxForm({ message: '取消授权成功！' })">取消授权</a>
                                	</c:if>
                                    <a href="${pageContext.request.contextPath}/platform/rmenu/checklist.do?id=${id}&type=0"
                                       class="btn <c:choose><c:when test="${type==0}">btn-success</c:when><c:otherwise>btn-default</c:otherwise></c:choose> tb-btn">所有菜单</a>
                                    <a href="${pageContext.request.contextPath}/platform/rmenu/checklist.do?id=${id}&type=1"
                                       class="btn <c:choose><c:when test="${type==1}">btn-success</c:when><c:otherwise>btn-default</c:otherwise></c:choose> tb-btn">已授权菜单</a>
                                    <a href="${pageContext.request.contextPath}/platform/rmenu/checklist.do?id=${id}&type=2"
                                       class="btn <c:choose><c:when test="${type==2}">btn-success</c:when><c:otherwise>btn-default</c:otherwise></c:choose> tb-btn">未授权菜单</a>
                                    <a href="${pageContext.request.contextPath}/platform/role/list.do"
                                       class="btn btn-default tb-btn">返回</a>
                                </div>
                            </div>
                        </div>
                    </div>
                    <table id="treeTable" cellspacing=0 class="table treeTable table-bordered">
                        <thead>
                        <tr>
                            <th class="left-th" width="30%"><input id="allBtn" type="checkbox" onclick="checkAll(this)" />全选&nbsp;&nbsp;菜单名称</th>
                            <th width="30%">排序号</th>
                            <th width="30%">是否可见</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${datas}" var="data">
                            <tr id="${data.id}" <c:if test="${!data.root}"> class="child-of-${data.parentId}"</c:if>>
                                <td class="left-td"><input type="checkbox" mid="${data.id}" mpid="${data.parentId}" onclick="checkClick(this)" name="selectedIds" value="${data.id}"/>&nbsp;&nbsp;${data.name}</td>
                                <td>${data.sortNo}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${data.visible}">是</c:when>
                                        <c:otherwise>否</c:otherwise>
                                    </c:choose>
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
