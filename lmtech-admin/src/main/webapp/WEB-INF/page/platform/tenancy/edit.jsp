<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="/WEB-INF/tags/tags.tld" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="../../common/header.jsp"></jsp:include>

    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.validate.min.js"></script>

    <script type="text/javascript">
        function validateForm() {
            $("form").validate({
                rules: {
                    "name":  {
                        required: true,
                        maxlength: 50
                    }
                },
                messages: {
                    "name": {
                        required: "请输入租户名称",
                        maxlength: "最多输入50个字符"
                    }
                }
            });
            return $("form").valid();
        }
    </script>
</head>
<body class="body">
<div class="col-xs-12">
    <div class="form">
        <form class="cmxform form-horizontal" method="post" action="${pageContext.request.contextPath}/platform/tenancy/syncedit.do" novalidate="novalidate">
            <input type="hidden" name="id" value="${entity.id}"/>
            <div class="form-group ">
                <label for="name" class="control-label col-xs-2 text-right">租户名称<span class="required">*</span></label>
                <div class="col-xs-8">
                    <input class="form-control" api="trim" id="name" name="name" value="${entity.name}" type="text">
                </div>
            </div>
            <div class="form-group ">
                <label for="code" class="control-label col-xs-2 text-right">租户代码<span class="required">*</span></label>
                <div class="col-xs-8">
                    <input class="form-control" api="trim" id="code" name="code" value="${entity.code}" type="text">
                </div>
            </div>
            <div class="form-group ">
                <label for="info" class="control-label col-xs-2 text-right">租户手机</label>
                <div class="col-xs-8">
                    <input class="form-control" id="mobile" name="mobile" value="${entity.mobile}">
                </div>
            </div>
            <div class="form-group ">
                <label for="info" class="control-label col-xs-2 text-right">租户QQ</label>
                <div class="col-xs-8">
                    <input class="form-control" id="qq" name="qq" value="${entity.qq}">
                </div>
            </div>
            <div class="form-group ">
                <label for="weixin" class="control-label col-xs-2 text-right">租户微信</label>
                <div class="col-xs-8">
                    <input class="form-control" id="weixin" name="weixin" value="${entity.weixin}"></input>
                </div>
            </div>
            <div class="form-group ">
                <label for="status" class="control-label col-xs-2 text-right">租户状态</label>
                <div class="col-xs-8">
                    <h:codeDropDownList id="status" name="status" codeType="tenancyStatus" value="${entity.status}"></h:codeDropDownList>
                </div>
            </div>
            <div class="form-group ">
                <label for="appId" class="control-label col-xs-2 text-right">公众号appId</label>
                <div class="col-xs-8">
                    <input class="form-control" id="appId" name="appId" value="${entity.appId}"></input>
                </div>
            </div>
            <div class="form-group ">
                <label for="secret" class="control-label col-xs-2 text-right">公众号secret</label>
                <div class="col-xs-8">
                    <input class="form-control" id="secret" name="secret" value="${entity.secret}"></input>
                </div>
            </div>
            <div class="form-group ">
                <label for="cardId" class="control-label col-xs-2 text-right">公众号卡片Id</label>
                <div class="col-xs-8">
                    <input class="form-control" id="cardId" name="cardId" value="${entity.cardId}"></input>
                </div>
            </div>
            <div class="form-group ">
                <label for="payApiKey" class="control-label col-xs-2 text-right">公众号支付Id</label>
                <div class="col-xs-8">
                    <input class="form-control" id="payApiKey" name="payApiKey" value="${entity.payApiKey}"></input>
                </div>
            </div>
            <div class="form-group ">
                <label for="appletAppId" class="control-label col-xs-2 text-right">小程序appId</label>
                <div class="col-xs-8">
                    <input class="form-control" id="appletAppId" name="appletAppId" value="${entity.appletAppId}"></input>
                </div>
            </div>
            <div class="form-group ">
                <label for="appletSecret" class="control-label col-xs-2 text-right">小程序secret</label>
                <div class="col-xs-8">
                    <input class="form-control" id="appletSecret" name="appletSecret" value="${entity.appletSecret}"></input>
                </div>
            </div>
            <div class="form-group ">
                <label for="appletCardId" class="control-label col-xs-2 text-right">小程序卡片Id</label>
                <div class="col-xs-8">
                    <input class="form-control" id="appletCardId" name="appletCardId" value="${entity.appletCardId}"></input>
                </div>
            </div>
            <div class="form-group ">
                <label for="appletPayApiKey" class="control-label col-xs-2 text-right">小程序支付Id</label>
                <div class="col-xs-8">
                    <input class="form-control" id="appletPayApiKey" name="appletPayApiKey" value="${entity.appletPayApiKey}"></input>
                </div>
            </div>
            <div class="form-group ">
                <label for="info" class="control-label col-xs-2 text-right">租户信息</label>
                <div class="col-xs-8">
                    <textarea class="form-control" id="info" rows="5" name="info">${entity.info}</textarea>
                </div>
            </div>
            <div class="form-group ">
                <label for="info" class="control-label col-xs-2 text-right">租户地址</label>
                <div class="col-xs-8">
                    <textarea class="form-control" id="address" rows="5" name="address">${entity.address}</textarea>
                </div>
            </div>
        </form>
    </div>
</div>
</body>
</html>
