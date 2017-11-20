<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="/WEB-INF/tags/tags.tld" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="../../common/header.jsp"/>
</head>

<body class="body">
<div class="row" style="margin: 20px;">
<form>
<input type="hidden" id="id" name="id" value="${id }">
<input type="password" id="password" name="password">
</form>
</div>
</body>
</html>
