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
                    "code":  {
                        required: true,
                        maxlength: 50
                    },
                    "name":  {
                        required: true,
                        maxlength: 50
                    },
                    /*"typeCode":  {
                        required: true,
                        maxlength: 50
                    },*/
                    "sortNo":  {
                        required: true,
                        number : true,
                        min: 0,
                        max: 100
                    },
                    "value":{
                        required: true,
                        number : true,
                        min: 0,
                        max: 100
                    }
                },
                messages: {
                    "code": {
                        required: "请输入编码",
                        maxlength: "最多输入50个字符"
                    },
                    "name":  {
                        required: "请输入编码名",
                        maxlength: "最多输入50个字符"
                    },
                    /*"typeCode":  {
                        required: "请输入代码类型",
                        maxlength: "最多输入50个字符"
                    },*/
                    "sortNo":  {
                        required: "请输入排序",
                        digits : "必须输入正整数",
                        min: "不能小于0",
                        max: "不能大于100"
                    },
                    "value":  {
                        required: "请输入编码值",
                        digits : "必须输入正整数",
                        min: "不能小于0",
                        max: "不能大于100"
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
            if(!$.trim($("input[name='id']").val())){//新增
                $("#typeCode").val($("#code2").val());
            }
        }

    </script>
</head>
<body class="body">
<div class="col-xs-12">
    <div class="form">
        <form class="cmxform form-horizontal" method="post" action="${pageContext.request.contextPath}/platform/code/syncEditCommit.do" novalidate="novalidate">
            <input type="hidden" name="id" value="${entity.id}"/>
            <input type="hidden" id="code2" value="${code2}"/>
            <div class="form-group ">
                <label for="code" class="control-label col-xs-2 text-right">编码<span class="required">*</span></label>
                <div class="col-xs-8">
                    <input class="form-control" api="trim" id="code" name="code" value="${entity.code}" type="text">
                </div>
            </div>
            <div class="form-group ">
                <label for="name" class="control-label col-xs-2 text-right">编码名<span class="required">*</span></label>
                <div class="col-xs-8">
                    <input class="form-control" api="trim" id="name" name="name" value="${entity.name}" type="text">
                </div>
            </div>
            <div class="form-group ">
                <label for="value" class="control-label col-xs-2 text-right">编码值</label>
                <div class="col-xs-8">
                    <input class="form-control" api="trim" id="value" name="value" value="${(empty entity.value)?0:entity.value}" type="text">
                </div>
            </div>
            <div class="form-group ">
                <label for="typeCode" class="control-label col-xs-2 text-right">代码类型</label>
                <div class="col-xs-8">
                    <input class="form-control" api="trim" readonly id="typeCode" name="typeCode" value="${entity.typeCode}" type="text">
                </div>
            </div>
            <div class="form-group ">
                <label for="sortNo" class="control-label col-xs-2 text-right">排序</label>
                <div class="col-xs-8">
                    <input class="form-control" api="trim" id="sortNo" name="sortNo" value="${(empty entity.sortNo)?0:entity.sortNo}" type="text">
                </div>
            </div>
        </form>
    </div>
</div>
</body>
</html>
