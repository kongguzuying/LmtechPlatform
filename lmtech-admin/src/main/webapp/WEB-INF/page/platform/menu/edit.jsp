<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title></title>
    <jsp:include page="../../common/header.jsp"></jsp:include>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.validate.min.js"></script>
    <style>
        .icon_fa{
            width: 34px;
            float: left;
            height: 34px;
            line-height: 34px;
            font-size: 1.5em;
            margin-left: 3px;
        }
    </style>
    <script type="text/javascript">
        $(function () {
            var parentId = $("#parentId").val();
            if (parentId == "0") {
                $("#parentName").val("根菜单");
            }

            change_icon($("select[name='icon']"));
        });
        function setParentMenu(id, parentId) {
            var modal = top.showModal({
                width: 800,
                height: 500,
                title: "父菜单 - 设置",
                url: "${pageContext.request.contextPath}/platform/menu/selparent.do?id=" + (id ? id : "") + "&parentId=" + (parentId ? parentId : "0"),
                opener: window,
                okFunc: function () {
                    var parentId = top.getModalFrameContent().find("#parentId").val();
                    var parentName = top.getModalFrameContent().find("#parentName").val();

                    if (parentId) {
                        $("#parentId").val(parentId);
                        $("#parentName").val(parentName);
                    } else {
                        $("#parentId").val("0");
                        $("#parentName").val("根菜单");
                    }

                    top.hideModal();
                }
            });
        }
        function setRootMenu() {
            $("#parentId").val("0");
            $("#parentName").val("根菜单");
        }

        function change_icon(obj) {
            $("#icon_img").attr("class","").addClass("fa icon_fa").addClass($(obj).val());
        }
    </script>
</head>

<body class="body">
<div class="col-xs-12">
    <div class="form">
        <form class="cmxform form-horizontal" action="${pageContext.request.contextPath}/platform/menu/edit.do" method="post">
            <input type="hidden" name="id" value="${entity.id}"/>
            <div class="form-group">
                <label class="control-label col-xs-2">
                    菜单名称
                </label>
                <div class="col-xs-8">
                    <input class="form-control" type="text" name="name" value="${entity.name}"/>
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-xs-2">
                    菜单地址
                </label>
                <div class="col-xs-8">
                    <input class="form-control" type="text" name="href" value="${entity.href}"/>
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-xs-2">
                    父菜单
                </label>
                <div class="col-xs-8">

                    <input class="form-control" type="hidden" id="parentId" name="parentId" value="${not empty parentId ? parentId : entity.parentId}"/>
                    <input class="form-control" readonly="true" type="text" id="parentName" name="parentName"
                           value="${parentName}"/>
                    <a href="javascript:void(0)" class="btn btn-default tb-btn"
                       onclick="setParentMenu('${entity.id}','${entity.parentId}')">设置父菜单</a>
                    <a href="javascript:void(0)" class="btn btn-default tb-btn" onclick="setRootMenu()">设为根菜单</a>
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-xs-2">
                    菜单类型
                </label>
                <div class="col-xs-8">
                    <select name="menuType" class="form-control" style="width:250px">
                        <option value="category" <c:if test="${entity.menuType == 'category'}">selected</c:if>>目录菜单
                        </option>
                        <option value="item" <c:if test="${entity.menuType == 'item'}">selected</c:if>>项菜单</option>
                    </select>
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-xs-2">
                    菜单图标
                </label>
                <div class="col-xs-8">
                    <select name="icon" class="form-control" style="width:250px;float: left;" onchange="change_icon(this);">
                        <option value="fa-laptop" <c:if test="${entity.icon == 'fa-laptop'}">selected</c:if>>fa-laptop</option>
                        <option value="fa-bars" <c:if test="${entity.icon == 'fa-bars'}">selected</c:if>>fa-bars</option>
                        <option value="fa-book" <c:if test="${entity.icon == 'fa-book'}">selected</c:if>>fa-book</option>
                        <option value="fa-code-fork" <c:if test="${entity.icon == 'fa-code-fork'}">selected</c:if>>fa-code-fork</option>
                        <option value="fa-cogs" <c:if test="${entity.icon == 'fa-cogs'}">selected</c:if>>fa-cogs</option>
                        <option value="fa-dot-circle-o" <c:if test="${entity.icon == 'fa-dot-circle-o'}">selected</c:if>>fa-dot-circle-o</option>
                        <option value="fa-folder" <c:if test="${entity.icon == 'fa-folder'}">selected</c:if>>fa-folder</option>
                        <option value="fa-folder-o" <c:if test="${entity.icon == 'fa-folder-o'}">selected</c:if>>fa-folder-o</option>
                        <option value="fa-folder-open" <c:if test="${entity.icon == 'fa-folder-open'}">selected</c:if>>fa-folder-open</option>
                        <option value="fa-folder-open-o" <c:if test="${entity.icon == 'fa-folder-open-o'}">selected</c:if>>fa-folder-open-o</option>
                        <option value="fa-puzzle-piece" <c:if test="${entity.icon == 'fa-puzzle-piece'}">selected</c:if>>fa-puzzle-piece</option>
                        <option value="fa-qrcode" <c:if test="${entity.icon == 'fa-qrcode'}">selected</c:if>>fa-qrcode</option>
                        <option value="fa-suitcase" <c:if test="${entity.icon == 'fa-suitcase'}">selected</c:if>>fa-suitcase</option>
                        <option value="fa-tasks" <c:if test="${entity.icon == 'fa-tasks'}">selected</c:if>>fa-tasks</option>
                        <option value="fa-wrench" <c:if test="${entity.icon == 'fa-wrench'}">selected</c:if>>fa-wrench</option>
                        <option value="fa-plus-square-o" <c:if test="${entity.icon == 'fa-plus-square-o'}">selected</c:if>>fa-plus-square-o</option>
                        <option value="fa-arrows" <c:if test="${entity.icon == 'fa-arrows'}">selected</c:if>>fa-arrows</option>
                        <option value="fa-bar-chart-o" <c:if test="${entity.icon == 'fa-bar-chart-o'}">selected</c:if>>fa-bar-chart-o</option>
                        <option value="fa-calendar" <c:if test="${entity.icon == 'fa-calendar'}">selected</c:if>>fa-calendar</option>
                        <option value="fa-envelope" <c:if test="${entity.icon == 'fa-envelope'}">selected</c:if>>fa-envelope</option>
                    </select>
                    <samp class="fa icon_fa fa-laptop" id="icon_img"></samp>
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-xs-2">
                    排序号
                </label>
                <div class="col-xs-8">
                    <input class="form-control" type="text" name="sortNo" value="${(empty entity.sortNo)?0:entity.sortNo}"/>
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-xs-2">
                    是否可见
                </label>
                <div class="col-xs-8">
                    <input type="checkbox" name="visible"
                           <c:if test="${entity.visible}">checked</c:if> />
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-xs-2"></label>
                <div class="col-xs-8">
                    <button class="btn btn-primary" type="submit">保存</button>
                    <a href="${pageContext.request.contextPath}/platform/menu/list.do" class="btn btn-default">返回</a>
                </div>
            </div>
        </form>
    </div>
</div>
</body>
</body>
</html>