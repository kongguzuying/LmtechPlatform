<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/WEB-INF/tags/tags.tld" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="../common/header.jsp"/>
    <!--dynamic table-->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/js/data-tables/DT_bootstrap.css"/>
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
</head>

<body class="body">
<div class="row">
    <div class="col-lg-12">
        <section class="panel">
            <form class="form-inline" action="${pageContext.request.contextPath}/logs/list.do" role="form"
                  method="post">
                <div class="adv-table">
                    <div class="row-fluid">
                        <div class="span3">
                            <div id="dynamic-table_length" class="dataTables_length"  style="padding:0px;">                                 
                                 <div class="col-md-3">
	                                <div class="form-group btn-group" role="group">
	                                	<button type="button" class="btn btn-default" disabled="disabled">日志类型</button>
	                                	<select class="form-control btn btn-default" name="type" id=type>
	                                		<option value="" <c:if test="${!type}">selected</c:if>>全部</option>
	                                		<option value="1" <c:if test="${type==1}">selected</c:if>>系统日志</option>
	                                		<option value="2" <c:if test="${type==2}">selected</c:if>>接口日志</option>
	                                	</select>
	                                </div>
                                </div>
                                <div class="col-md-4">
                                  <div class="input-group input-large custom-date-range" role="group" data-date="2013-07-13" data-date-format="yyyy-mm-dd">
                                      <input type="text" class="form-control date" id="createDateBegin" name="createDateBegin" value="${createDateBegin}" placeholder="操作起始时间">
                                      <span class="input-group-addon">To</span>
                                      <input type="text" class="form-control date" id="createDateEnd" name="createDateEnd" value="${createDateEnd}" placeholder="操作结束时间">
                                  </div>
                                </div>
                                
                                <div class="col-md-3">
                                  <div class="input-group input-large" role="group">
                                      <input type="text" class="form-control" name="createUser" value="${createUser}" placeholder="请输入操作者">
                                    </div>
                                </div>  
                                
                                <button type="button" onclick="submitSearch()" style="margin-top:5px;" class="btn btn-success"><i
                                        class="fa fa-search"></i>搜索</button>
                            </div>
                        </div>
                    </div>

                    <table class="display table table-bordered" id="dynamic-table">
                        <thead>
                        <tr>
                            <th width="5%">类型</th>
                            <th width="10%">描述</th>
                            <th width="5%">状态</th>
                            <th width="10%">操作时间</th>
                            <th width="5%">操作人</th>
                           <%-- <th width="60%">备注</th>--%> 
                            <th width="5%">操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${pageData.items}" var="item">
                            <tr class="gradeU">
                            	<c:choose>
	                            	<c:when test="${item.type eq 1 || item.type eq 2}">
	                            		<c:if test="${item.type eq 1}">
	                            			<td>系统日志</td>
	                            		</c:if>
	                            		<c:if test="${item.type eq 2}">
	                            			<td>接口日志</td>
	                            		</c:if>
	                            	</c:when>
	                            	<c:otherwise>
	                            		<td>未知类型</td>
	                            	</c:otherwise>
                            	</c:choose>
                                <td>${item.description}</td>
                                <c:choose>
	                            	<c:when test="${item.status eq 0 || item.status eq 1}">
	                            		<c:if test="${item.status eq 0}">
	                            			<td>失败</td>
	                            		</c:if>
	                            		<c:if test="${item.status eq 1}">
	                            			<td>成功</td>
	                            		</c:if>
	                            	</c:when>
	                            	<c:otherwise>
	                            		<td>未知状态</td>
	                            	</c:otherwise>
                            	</c:choose>
                            	<td><fmt:formatDate value="${item.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                                <td>${item.updateUser}(${item.createUser})</td>
                                <%-- <td  style="word-break:break-all;">${item.memo}</td>--%>
                                <td><a onclick="detail('${item.id}');return false;" style="cursor:pointer">详情</a></td>
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
<script type="text/javascript">
$(function(){
	initDatepicker();
});
function initDatepicker(){
	 $(".date").datetimepicker({
		 format: "yyyy-mm-dd",
		    autoclose: true,
		    todayBtn: true, 
		    minView: "month",
		    language: 'zh-CN'
     });
}
function detail(logsId){
	  top.showModal({
	        url: "${pageContext.request.contextPath}/logs/edit.do?id=" + logsId,
	        title: "日志详情",
	        opener: window,
	        showFooter: false,
	        width: 800,
	        height: 600
	    });
}
</script>
</body>
</html>
