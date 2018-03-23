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
                    "title":  {
                        required: true,
                        maxlength: 50
                    },
                    "price":  {
                        required: true,
                        //digits : true,
                        number : true,
                        min: 0,
                        max: 1000
                    },
                    "sortNo":  {
                        required: true,
                        //digits : true,
                        number : true,
                        min: 1,
                        max: 100
                    }
                },
                messages: {
                    "title": {
                        required: "请输入标题",
                        maxlength: "最多输入50个字符"
                    },
                    "price":  {
                        required: "请输入价格",
                        digits : "必须输入正整数",
                        min: "不能小于0",
                        max: "不能大于1000"
                    },
                    "sortNo":  {
                        required: "请输入排序号",
                        digits : "必须输入正整数",
                        min: "不能小于1",
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

            /*if($.trim($("input[name='id']").val())){//编辑
                $("#title").prop("readonly",true);
            }*/
        }
    </script>
</head>
<body class="body">
<div class="col-xs-12">
    <div class="form">
        <form class="cmxform form-horizontal" method="post" action="${pageContext.request.contextPath}/platform/gift/syncedit.do" novalidate="novalidate">
            <input type="hidden" name="id" value="${entity.id}"/>
            <div class="form-group ">
                <label for="title" class="control-label col-xs-2 text-right">标题<span class="required">*</span></label>
                <div class="col-xs-8">
                    <input class="form-control" api="trim" type="text" id="title" name="title" value="${entity.title}">
                </div>
            </div>
            <div class="form-group ">
                <label for="price" class="control-label col-xs-2 text-right">价格<span class="required">*</span></label>
                <div class="col-xs-8">
                    <input class="form-control" api="trim" type="text" id="price" name="price" value="${(empty entity.price)?0:entity.price}">
                </div>
            </div>
            <div class="form-group">
                <label for="price" class="control-label col-xs-2 text-right">排序号<span class="required">*</span></label>
                <div class="col-xs-8">
                    <input class="form-control" api="trim" type="text" id="sortNo" name="sortNo" value="${(empty entity.sortNo)?1:entity.sortNo}"/>
                </div>
            </div>
        </form>
    </div>
</div>
</body>
</html>
