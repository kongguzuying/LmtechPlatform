<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="h" uri="/WEB-INF/tags/tags.tld" %>

<!DOCTYPE html>
<html>
<head>
    <jsp:include page="../../common/header.jsp"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/js/jquery-treeTable/jquery.treeTable.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/js/data-tables/DT_bootstrap.css"/>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/js/jquery-treeTable/jquery.treeTable.js"></script>
    <style type="text/css">
        .selectedTr {
            background-color: #eeeeee;
        }
    </style>
    <script type="text/javascript">
        $(function () {
            $(".treeTable").treeTable({initialState: "expanded"});
        });
        function selectTr(tr, id, name) {
            if ($(tr).hasClass("selectedTr")) {
                $(tr).removeClass("selectedTr");
                $("#hidCurrentId").val("0");
            } else {
                $(".treeTable tr").removeClass("selectedTr");
                $(tr).addClass("selectedTr");
                $("#hidCurrentId").val(id);
            }
        }

        function submitSearch() {
            $("form")[0].submit();
        }

        function addMenu() {
            var curId = $("#hidCurrentId").val();
            var url = "${pageContext.request.contextPath}/platform/card/edit.do?id=&parentId=" + curId;
            window.location.href = url;
        }
    </script>
    <style type="text/css">
        .tinyImg {
            width: 100px;
            height: 40px;
            vertical-align: middle;
        }
    </style>
</head>

<body class="body">
<input type="hidden" id="hidCurrentId" value="0"/>
<div class="row">
    <div class="col-lg-12">
        <section class="panel">
            <form class="form-inline" action="${pageContext.request.contextPath}/platform/card/list.do" role="form"
                  method="post">
            <div class="adv-table">
                <div class="row-fluid">
                    <%--<div class="span3">
                        <div id="dynamic-table_length" class="dataTables_length">
                            <div class="form-group">
                                <input type="text" class="form-control" name="filter_LIKES_title"
                                       value="${filter_LIKES_title}" placeholder="请输入标题">
                            </div>
                            <input type="submit" class="btn btn-primary" value="搜索"/>
                        </div>
                    </div>--%>
                    <div class="span3">
                        <div class="dataTables_filter" id="dynamic-table_filter">
                            <a href="javascript:void(0)" class="btn btn-primary tb-btn" onclick="addMenu()">添加</a>
                        </div>
                    </div>
                </div>
                <table id="treeTable" cellspacing=0 class="table treeTable table-bordered">
                    <thead>
                    <tr>
                        <th width="5%">序号</th>
                        <th width="20%">标题</th>
                        <th width="5%">排序号</th>
                        <th width="20%">FTP卡面</th>
                        <th width="10%">类型</th>
                        <th width="10%">备注</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${entitys}" var="entity" varStatus="status">
                        <tr id="${entity.id}" <c:if test="${!entity.root}"> class="child-of-${entity.parentId}"</c:if>
                            onclick="selectTr(this,'${entity.id}','${entity.title}')">
                            <td>${status.index + 1}</td>
                            <td class="left-td">${entity.title}</td>
                            <td>${entity.sortNo}</td>
                            <td>
                                <c:if test="${!(empty entity.background)}"><img src="${entity.background}" class="tinyImg"/></c:if>
                            </td>
                            <td>${entity.category}</td>
                            <td>${entity.remark}</td>
                            <td>
                                <div class="btn-group">
                                        <a href="${pageContext.request.contextPath}/platform/card/edit.do?id=${entity.id}"
                                           class="btn btn-default btn-xs">编辑</a>
                                        <a href="javascript:listRemove('${pageContext.request.contextPath}/platform/card/remove.do?id=${entity.id}')"
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
