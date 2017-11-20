<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="/WEB-INF/tags/tags.tld" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="../../common/header.jsp"/>

    <script type="text/javascript">
        function edit(id,status) {
            top.showModal({
                url: "${pageContext.request.contextPath}/platform/integralRule/edit_rule.do?id=" + (id ? id : "")+"&status=" + (status ? status : ""),
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

        function _reset() {
            $("select").val(null);
            $("input[type='text']").val(null);
        }

        function getCodeVal(code,_val,dom_id){
            $("#"+dom_id).text(top.getCodeItemText(code,_val));
        }

        function getCodeItem(code,dom_id){
            $(dom_id).html(null).html(top.addOption(code));

            //下拉框初始值默认设置
            var _options = $(dom_id +" > option");
            var _val = $(dom_id).attr("val");
            $.each(_options,function(){
                _options.attr("selected",false);
                if($(this).val() == _val){
                    $(this).attr("selected",true);
                    return false;
                }
            });
        }

        $(function () {
            getCodeItem('merberType',"#filter_LIKEI_merberType");
            getCodeItem('type',"#filter_LIKEI_type");
            getCodeItem('status',"#filter_LIKEI_status");
        });
    </script>
    <style>
        label{
            margin-top:5px;
            text-align: right !important;
        }
        .form-inline .form-group{
            margin-bottom: 5px;
        }
        .form-group .width{
            width: 153px !important;
        }
    </style>
</head>

<body class="body">

<div class="row">
    <div class="col-lg-12">
        <section class="panel">
            <form class="form-inline" action="${pageContext.request.contextPath}/platform/integralRule/list.do" role="form"
                  method="post">
                <div class="adv-table">
                    <div class="row-fluid">
                        <div class="span3">
                            <div id="dynamic-table_length" class="dataTables_length">
                                <div class="form-group col-xs-4">
                                    <label class="text-right col-xs-3 control-label">活动名称</label>
                                    <input type="text" class="form-control col-xs-1 input-sm" name="filter_LIKES_name"
                                           value="${filter_LIKES_name}"/>
                                </div>

                                <div class="form-group col-xs-4">
                                    <label class="text-right col-xs-3 control-label">会员类型</label>
                                    <select class="form-control col-xs-1 input-sm width" id="filter_LIKEI_merberType" name="filter_LIKEI_merberType"
                                            val="${filter_LIKEI_merberType}"></select>
                                </div>

                                <div class="form-group col-xs-4">
                                    <label class="text-right col-xs-3 control-label">类型</label>
                                    <select class="form-control col-xs-1 input-sm width" id="filter_LIKEI_type" name="filter_LIKEI_type"
                                            val="${filter_LIKEI_type}"></select>
                                </div>

                                <div class="form-group col-xs-4">
                                    <label class="text-right col-xs-3 control-label">执行状态</label>
                                    <select class="form-control col-xs-1 input-sm width" id="filter_LIKEI_status" name="filter_LIKEI_status"
                                            val="${filter_LIKEI_status}"> </select>
                                </div>

                                <div class="form-group col-xs-4">
                                    <label class="text-right col-xs-3 control-label">起止时间</label>
                                    <div class="input-group input-large custom-date-range col-xs-8">
                                        <input type="text" class="form-control dpd1 input-sm" name="filter_LIKED_startDate"value="${filter_LIKED_startDate}">
                                        <span class="input-group-addon">-</span>
                                        <input type="text" class="form-control dpd2 input-sm" name="filter_LIKED_endDate"value="${filter_LIKED_endDate}">
                                    </div>
                                </div>

                                <div class="form-group col-sm-offset-5" style="margin-bottom:0px;margin-top:5px;">
                                    <div style="margin-left: 8px;">
                                        <input type="submit" class="btn btn-primary" value="查询"/>
                                        <input class="btn btn-primary" type="button" value="重置" onclick="_reset();"/>
                                    </div>
                                </div>
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
                            <th width="5%">活动名称</th>
                            <th width="5%">类型</th>
                            <th width="5%">会员类型</th>
                            <th width="5%">积分设置</th>
                            <th width="10%">开始时间</th>
                            <th width="10%">结束时间</th>
                            <th width="5%">执行状态</th>
                            <th width="10%">备注</th>
                            <th width="5%">操作人</th>
                            <th width="15%">操作时间</th>
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
                                <td id="${status.index + 100}"><script>getCodeVal("type",${item.type},${status.index + 100});</script></td>
                                <td id="${status.index + 200}"><script>getCodeVal("merberType",${item.merberType},${status.index + 200});</script></td>
                                <td>${item.rule}</td>
                                <td><fmt:formatDate value="${item.startDate}" pattern="yyyy-MM-dd  HH:mm:ss" /></td>
                                <td><fmt:formatDate value="${item.endDate}" pattern="yyyy-MM-dd  HH:mm:ss" /></td>
                                <td id="${status.index + 300}"><script>getCodeVal("status",${item.status},${status.index + 300});</script></td>
                                <td>${item.remark}</td>
                                <td>${item.updateUserName}</td>
                                <td><fmt:formatDate value="${item.updateDate}" pattern="yyyy-MM-dd  HH:mm:ss" /></td>
                                    <%--<td>${item.createUser}</td>
                                    <td><fmt:formatDate value="${item.createDate}" pattern="yyyy-MM-dd  HH:mm:ss" /></td>
                                    <td>${item.updateUser}</td>
                                    <td><fmt:formatDate value="${item.updateDate}" pattern="yyyy-MM-dd  HH:mm:ss" /></td>--%>
                                <td>
                                    <div class="btn-group">
                                        <c:if test="${item.status==1 || item.status==2}">
                                            <a class="btn btn-default btn-xs" onclick="edit('${item.id}','${item.status}');return false;">编辑</a>
                                        </c:if>

                                        <c:if test="${item.status==1 || item.status==2}">
                                            <a href="javascript:listRemove('${pageContext.request.contextPath}/platform/integralRule/cancle.do?id=${item.id}','取消')"
                                               class="btn btn-default btn-xs">取消</a>
                                        </c:if>
                                        <%--<a href="javascript:listRemove('${pageContext.request.contextPath}/platform/integralRule/remove.do?id=${item.id}')"
                                           class="btn btn-default btn-xs">删除</a>--%>
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
