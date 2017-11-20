<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="/WEB-INF/tags/tags.tld" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="../../common/header.jsp"/>
    <meta name="viewport" content="initial-scale=1.0, minimum-scale=1.0, user-scalable=0, width=device-width"/>
    <style>
    	@media screen and (min-width:420px) and (max-width:640px){html{font-size:110%;}}
		@media screen and (max-width:420px){html{font-size:85%;}}
    </style>
</head>

<body class="body" style="background-color:#f7f7f8">
<div class="container-fluid">
	<div class="row">
	  <div class="form-group input-group">
	  <input id="inputKey"type="text" class="form-control" placeholder="请输入关键字" style="width:80%" value="" autocomplete="off">
       <span class="input-group-btn" style="display:table">
         <button type="button" class="btn btn-default" id="projectBtn"><i class="fa fa-search"></i></button>
       </span>
      </div>
   </div>
	<div class="row">
	  <div class="col-md-12 col-xs-12">分户验收查验内容:</div>
   </div>
<c:forEach var="pair" items="${pairList}" varStatus="status">
   <div class="row dataRow" style="margin-top:8px;">
	  <div class="col-md-12 col-xs-12" style="word-break:break-all;">
		  	<label class="serialNumber">(${status.index +1 })</label>&nbsp;<span><font color="#f54d31">${pair.key}</font>:&nbsp;${pair.value}</span>
	  </div>
   </div>
</c:forEach>
</div>
<script type="text/javascript">
$(function(){
	$("#inputKey").bind('input propertychange',function(){
		var val = $(this).val();
		//console.error(val);
		show(val);
	});
});

function show(val){
	if(val){
		$(".dataRow").hide();
		var index = 1;
		$(".dataRow").find("span:contains('"+val+"')").each(function(i,o){
			$(o).parents(".dataRow").show().find(".serialNumber").text("("+index+")");
			index ++;
		});
	}else{
		var index = 1;
		$(".dataRow").show().each(function(i,o){
			$(this).find(".serialNumber").text("("+index+")");
			index ++;
		});
	}
}
</script>
</body>
</html>
