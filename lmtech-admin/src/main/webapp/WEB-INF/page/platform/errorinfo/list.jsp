<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/WEB-INF/tags/tags.tld" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html>
<head>
    <jsp:include page="../common/header.jsp"/>

    <!--dynamic table-->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/js/data-tables/DT_bootstrap.css"/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap-typeahead.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/autocomplete.js?version=${version}"></script>
     <!--pickers css-->
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/bootstrap-datepicker/css/datepicker-custom.css" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/bootstrap-timepicker/css/timepicker.css" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/bootstrap-colorpicker/css/colorpicker.css" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/bootstrap-daterangepicker/daterangepicker-bs3.css" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/bootstrap-datetimepicker/css/datetimepicker-custom.css" />
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap-datepicker/js/bootstrap-datepicker.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap-datetimepicker/js/bootstrap-datetimepicker.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap-daterangepicker/moment.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap-daterangepicker/daterangepicker.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap-colorpicker/js/bootstrap-colorpicker.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap-timepicker/js/bootstrap-timepicker.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/page/errorinfo/list.js?${version}"></script>
	
</head>

<body class="body">

<div class="row">
    <div class="col-lg-12">
        <section class="panel">
            <form class="form-inline" action="${pageContext.request.contextPath}/household/errorinfo/list.do" role="form"
                  method="post">
                <div class="adv-table">
                  <div class="row-fluid">
                        <div class="span3">
                            <div id="dynamic-table_length" class="dataTables_length">
                                <div class="form-group">
                                	<input type="text" id="code" name="code" class="form-control" value="${code}" placeholder="请输入系统代码"/>
                                </div>
                                 <div class="form-group">
                                	<input type="text" id="service" name="service" class="form-control" value="${service}" placeholder="请输入业务接口"/>
                                </div>
                                <button type="button" onclick="submitSearch()" class="btn btn-primary">搜索</button>
                            </div>
                        </div>
                       <%--
                        <div class="span3">
                            <div class="dataTables_filter" id="dynamic-table_filter">
                                <h:isAdmin><a class="btn btn-success" onclick="syncProblemData()">同步问题</a></h:isAdmin>
                                <a href="javascript:exportExcel()" class="btn btn-success">导出</a>
                            </div>
                        </div> --%>
                    </div>
                    <input type="hidden" id="status" name="status" value="${status}"/>
                    <div class="table-header-status" role="group">
                        <div class="btn-group " style="float:left;">
                            <button type="button" status="" onclick="setStatus(this)" class="btn btn-default">所有</button>
                            <button type="button" status="0" onclick="setStatus(this)" class="btn btn-default">待处理</button>
                            <button type="button" status="1" onclick="setStatus(this)" class="btn btn-default">已处理</button>
                        </div>
                        <!-- 
                        <a href="javascript:exportExcel()" class="btn btn-success" style="float:right;">导出</a>
                        <h:isSuperAdmin>
                            <a class="btn btn-success" onclick="syncProblemData()" style="float:right; margin-right:20px;">同步所有问题</a>
                            <a class="btn btn-success" onclick="syncProjProblemData()" style="float:right; margin-right:20px;">同步项目问题</a>
                        </h:isSuperAdmin>
                         -->
                    </div>
                    <table class="display table table-bordered" id="dynamic-table">
                        <thead>
                        <tr>
                            <th width="2%">序号</th>
                            <th width="8%">系统代码</th>
                            <th width="8%">业务接口</th>
                            <th width="8%">业务参数</th>
                            <th width="10%">异常内容</th>
                            <th width="8%">处理状态</th>
                            <th width="8%">创建日期</th>
                            <th width="10%">操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${pageData.items}" var="item" varStatus="status">
                            <tr class="gradeU">
                                <td>${status.index + 1}</td>
                                <td>${item.code}</td>
                                <%-- <h:codeText codeType="sourceCode" itemValue="${item.sourceCode}"></h:codeText>--%>
                                <td>${item.service}</td>
                                <td>${item.param}</td>
                                <td style="word-wrap:break-word;word-break:break-all;">${fn:substring(item.content, 0, 12)}...</td>
                                <td><c:if test='${item.status eq "0"}'>待处理</c:if><c:if test='${item.status eq "1"}'>已处理</c:if></td>
                               <td> <fmt:formatDate  value="${item.createDate}" pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate></td>
                                <td>
                                    <a href="javascript:view('${item.id}')">详情</a>&nbsp;
                                    <c:if test='${item.status eq "0"}'><a href="javascript:handuse('${item.id}')">回调</a>&nbsp;</c:if>
                                    <c:if test='${item.status eq "1"}'></c:if>
                                    <a href="javascript:remove('${item.id}')">删除</a>&nbsp;
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
