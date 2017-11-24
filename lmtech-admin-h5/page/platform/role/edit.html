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
                    },
                    "level":  {
                        required: true,
                        digits : true,
                        min: 1,
                        max: 254
                    },
                    "remark": {
                        maxlength: 500
                    }
                },
                messages: {
                    "name": {
                        required: "请输入角色名称",
                        maxlength: "最多输入50个字符"
                    },
                    "level":  {
                        required: "请输入级别",
                        digits : "必须输入正整数",
                        min: "不能小于1",
                        max: "不能大于255"
                    },
                    "remark": {
                        maxlength: "最多输入500个字符"
                    }
                }
            });
            return $("form").valid();
        }

        /**
         * 处理键盘事件 禁止后退键（Backspace）密码或单行、多行文本框除外
         */
        function banBackSpace(e){   
            var ev = e || window.event;//获取event对象   
            var obj = ev.target || ev.srcElement;//获取事件源   
            if(ev.keyCode == 8){
                var t = obj.type || obj.getAttribute('type');//获取事件源类型  
                //获取作为判断条件的事件类型
                var vReadOnly = obj.getAttribute('readonly');
                //处理null值情况
                vReadOnly = (vReadOnly == "") ? false : vReadOnly;
                if((t=="password" || t=="text" || t=="textarea")&&!vReadOnly){
                	return true;
                }else{
                	e.preventdefault;
                	return false;
                }
            }
        }

        window.onload=function(){
            //禁止后退键 作用于Firefox、Opera
            document.onkeypress=banBackSpace;
            //禁止后退键  作用于IE、Chrome
            document.onkeydown=banBackSpace;
        }
    </script>
</head>
<body class="body">
<div class="col-xs-12">
    <div class="form">
        <form class="cmxform form-horizontal" method="post" action="${pageContext.request.contextPath}/platform/role/syncedit.do" novalidate="novalidate">
            <input type="hidden" name="id" value="${entity.id}"/>
            <div class="form-group ">
                <label for="name" class="control-label col-xs-2 text-right">角色名称<span class="required">*</span></label>
                <div class="col-xs-8">
                    <input class="form-control" api="trim" id="name" name="name" value="${entity.name}" type="text">
                </div>
            </div>
            <div class="form-group ">
                <label for="remark" class="control-label col-xs-2 text-right">级别<span class="required">*</span></label>
                <div class="col-xs-8">
                    <input class="form-control" api="trim" id="level" name="level" value="${entity.level}" type="text">
                </div>
            </div>
            <div class="form-group ">
                <label for="remark" class="control-label col-xs-2 text-right">角色描述</label>
                <div class="col-xs-8">
                    <textarea class="form-control" id="remark" rows="5" name="remark">${entity.remark}</textarea>
                </div>
            </div>
             <div class="form-group ">
                <label for="remark" class="control-label col-xs-2 text-right">是否可用</label>
                <div class="col-xs-8 text-left">
                    <input type="checkbox" name="enable" <c:if test="${entity.enable || entity.id==null}">checked</c:if>  >
                </div>
            </div>
        </form>
    </div>
</div>
</body>
</html>
