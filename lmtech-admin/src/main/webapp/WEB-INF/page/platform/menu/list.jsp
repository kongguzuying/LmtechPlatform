<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="h" uri="/WEB-INF/tags/tags.tld" %>

<!DOCTYPE html>
<html>
<head>
    <jsp:include page="../../common/header.jsp"/>
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/js/jquery-treeTable/jquery.treeTable.css"/>
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
        function addMenu() {
            var curId = $("#hidCurrentId").val();
            var url = "${pageContext.request.contextPath}/platform/menu/edit.do?id=&parentId=" + curId;
            window.location.href = url;
        }
    </script>
</head>

<body class="body">
<input type="hidden" id="hidCurrentId" value="0"/>
<div class="row">
    <div class="col-lg-12">
        <section class="panel">
            <div class="adv-table">
                <div class="row-fluid">
                    <div class="span3">
                        <div class="dataTables_filter" id="dynamic-table_filter">
                            <%--<h:hasPermission resId="Menu:Add">--%>
                                <a href="javascript:void(0)" class="btn btn-primary tb-btn" onclick="addMenu()">添加</a>
                            <%--</h:hasPermission>--%>
                        </div>
                    </div>
                </div>
                <table id="treeTable" cellspacing=0 class="table treeTable table-bordered">
                    <thead>
                    <tr>
                        <th width="20%">菜单名称</th>
                    <!--<th width="30%">菜单地址</th> -->  
                        <th width="10%">菜单类型</th>
                        <th width="10%">是否可见</th>
                        <th width="10%">排序号</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${entitys}" var="entity">
                        <tr id="${entity.id}" <c:if test="${!entity.root}"> class="child-of-${entity.parentId}"</c:if>
                            onclick="selectTr(this,'${entity.id}','${entity.name}')">
                            <td class="left-td">${entity.name}</td>
                            <!--  <td>${entity.href}</td> -->  
                            <td>${entity.menuType}</td>
                            <td>${entity.visible}</td>
                            <td>${entity.sortNo}</td>
                            <td>
                                <div class="btn-group">
                                    <%--<h:hasPermission resId="Menu:Edit">--%>
                                        <a href="${pageContext.request.contextPath}/platform/menu/edit.do?id=${entity.id}"
                                           class="btn btn-default btn-xs">编辑</a>
                                    <%--</h:hasPermission>--%>
                                    <%--<h:hasPermission resId="Menu:Remove">--%>
                                        <a href="javascript:listRemove('${pageContext.request.contextPath}/platform/menu/remove.do?id=${entity.id}')"
                                           class="btn btn-default btn-xs">删除</a>
                                    <%--</h:hasPermission>--%>
                                </div>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </section>
    </div>
</div>
</body>
</html>
