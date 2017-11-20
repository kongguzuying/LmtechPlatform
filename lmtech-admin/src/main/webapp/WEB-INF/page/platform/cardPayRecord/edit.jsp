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
                    "dayNo":  {
                        required: true,
                        //digits : true,
                        number : true,
                        min: 1,
                        max: 100
                    },
                    "bonus":  {
                        required: true,
                        //digits : true,
                        number : true,
                        min: 0,
                        max: 1001
                    },
                    "bonusType":  {
                        required: true,
                        //digits : true,
                        number : true,
                        min: 1,
                        max: 100
                    },
                    "dayMax":  {
                        required: true,
                        //digits : true,
                        number : true,
                        min: 1,
                        max: 100
                    },
                    "mainTitle":  {
                        required: true,
                        maxlength: 50
                    },
                    "subTitle":  {
                        required: true,
                        maxlength: 50
                    },
                    "endTitle":  {
                        required: true,
                        maxlength: 50
                    },
                    "orderNo":  {
                        required: true,
                        //digits : true,
                        number : true,
                        min: 0,
                        max: 100
                    }
                },
                messages: {
                    "dayNo": {
                        required: "请输入第几天",
                        digits : "必须输入正整数",
                        min: "不能小于1",
                        max: "不能大于100"
                    },
                    "bonus":  {
                        required: "请输入积分值",
                        digits : "必须输入正整数",
                        min: "不能小于0",
                        max: "不能大于1001"
                    },
                    "bonusType":  {
                        required: "请输入积分类型",
                        digits : "必须输入正整数",
                        min: "不能小于1",
                        max: "不能大于100"
                    },
                    "dayMax":  {
                        required: "请输入日最大次数",
                        digits : "必须输入正整数",
                        min: "不能小于1",
                        max: "不能大于100"
                    },
                    "mainTitle": {
                        required: "请输入主标题",
                        maxlength: "最多输入50个字符"
                    },
                    "subTitle":  {
                        required: "请输入副标题",
                        maxlength: "最多输入50个字符"
                    },
                    "endTitle":  {
                        required: "请输入完成后标题",
                        maxlength: "最多输入50个字符"
                    },
                    "orderNo":  {
                        required: "请输入显示顺序",
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
        }

    </script>
</head>
<body class="body">
<div class="col-xs-12">
    <div class="form">
        <form class="cmxform form-horizontal" method="post" action="${pageContext.request.contextPath}/platform/cardPayRecord/syncedit.do" novalidate="novalidate">
            <input type="hidden" name="id" value="${entity.id}"/>
            <div class="form-group ">
                <label for="dayNo" class="control-label col-xs-2 text-right">第几天<span class="required">*</span></label>
                <div class="col-xs-8">
                    <input class="form-control" api="trim" id="dayNo" name="dayNo" value="${(empty entity.dayNo)?1:entity.dayNo}" type="text">
                </div>
            </div>
            <div class="form-group ">
                <label for="bonus" class="control-label col-xs-2 text-right">积分值<span class="required">*</span></label>
                <div class="col-xs-8">
                    <input class="form-control" api="trim" id="bonus" name="bonus" value="${(empty entity.bonus)?0:entity.bonus}" type="text">
                </div>
            </div>
            <div class="form-group ">
                <label for="bonusType" class="control-label col-xs-2 text-right">积分类型<span class="required">*</span></label>
                <div class="col-xs-8">
                    <input class="form-control" api="trim" id="bonusType" name="bonusType" value="${entity.bonusType}" type="text">
                </div>
            </div>
            <div class="form-group ">
                <label for="dayMax" class="control-label col-xs-2 text-right">日最大次数<span class="required">*</span></label>
                <div class="col-xs-8">
                    <input class="form-control" api="trim" id="dayMax" name="dayMax" value="${(empty entity.dayMax)?1:entity.dayMax}" type="text">
                </div>
            </div>

            <div class="form-group ">
                <label for="mainTitle" class="control-label col-xs-2 text-right">主标题<span class="required">*</span></label>
                <div class="col-xs-8">
                    <input class="form-control" api="trim" id="mainTitle" name="mainTitle" value="${entity.mainTitle}" type="text">
                </div>
            </div>
            <div class="form-group ">
                <label for="subTitle" class="control-label col-xs-2 text-right">副标题<span class="required">*</span></label>
                <div class="col-xs-8">
                    <input class="form-control" api="trim" id="subTitle" name="subTitle" value="${entity.subTitle}" type="text">
                </div>
            </div>
            <div class="form-group ">
                <label for="endTitle" class="control-label col-xs-2 text-right">完成后标题<span class="required">*</span></label>
                <div class="col-xs-8">
                    <input class="form-control" api="trim" id="endTitle" name="endTitle" value="${entity.endTitle}" type="text">
                </div>
            </div>

            <div class="form-group ">
                <label for="orderNo" class="control-label col-xs-2 text-right">显示顺序<span class="required">*</span></label>
                <div class="col-xs-8">
                    <input class="form-control" api="trim" id="orderNo" name="orderNo" value="${(empty entity.orderNo)?0:entity.orderNo}" type="text">
                </div>
            </div>
        </form>
    </div>
</div>
</body>
</html>
