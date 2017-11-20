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
                url: "${pageContext.request.contextPath}/platform/integralSet/edit.do?id=" + (id ? id : ""),
                title: !id ? "积分配置-添加" : "积分配置-编辑",
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

        function bonusType(bonusType) {
            switch(bonusType){
                case 1://赠卡
                    return "赠卡";
                    break;
                case 2://签到
                    return "签到";
                    break;
                case 3://分享
                    return "";
                    break;
                default :
                    return "分享";
                    break;
            }
        }
    </script>
</head>

<body class="body">

<div class="row">
    <div class="col-lg-12">
        <section class="panel">
            <form class="form-inline" action="${pageContext.request.contextPath}/platform/integralSet/list.do" role="form"
                  method="post">
                <div class="adv-table">
                    <div class="row-fluid">
                        <div class="span3">
                            <div id="dynamic-table_length" class="dataTables_length">
                                <div class="form-group">
                                    <input type="text" class="form-control" name="filter_LIKEI_bonusType"
                                           value="${filter_LIKEI_bonusType}" placeholder="请输入积分类型">
                                </div>
                                <button type="button" onclick="submitSearch()" class="btn btn-primary">搜索</button>
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
                            <th width="5%">第几天</th>
                            <th width="5%">积分值</th>
                            <th width="10%">积分类型</th>
                            <th width="10%">日最大次数</th>
                            <th width="15%">主标题</th>
                            <th width="15%">副标题</th>
                            <th width="15%">完成后标题</th>
                            <th width="5%">显示顺序</th>
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
                                <td>${item.dayNo}</td>
                                <td>${item.bonus}</td>
                                <td>${item.bonusType}</td>
                                <td>${item.dayMax}</td>
                                <td>${item.mainTitle}</td>
                                <td>${item.subTitle}</td>
                                <td>${item.endTitle}</td>
                                <td>${item.orderNo}</td>
                                <%--<td>${item.createUser}</td>
                                <td><fmt:formatDate value="${item.createDate}" pattern="yyyy-MM-dd  HH:mm:ss" /></td>
                                <td>${item.updateUser}</td>
                                <td><fmt:formatDate value="${item.updateDate}" pattern="yyyy-MM-dd  HH:mm:ss" /></td>--%>
                                <td>
                                    <div class="btn-group">
                                        <a class="btn btn-default btn-xs" onclick="edit('${item.id}');return false;">编辑</a>
                                        <a href="javascript:listRemove('${pageContext.request.contextPath}/platform/integralSet/remove.do?id=${item.id}')"
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
