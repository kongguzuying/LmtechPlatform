<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="/WEB-INF/tags/tags.tld" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
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
                    "type":  {
                        required: true
                    },
                    "merberType":  {
                        required: true
                    },
                    "startDate":  {
                        required: true
                    },
                    "endDate":  {
                        required: true
                    },
                    "rule":  {
                        required: true,
                        //digits : true,
                        number : true,
                        min: 1,
                        max: 10001
                    }
                },
                messages: {
                    "name":  {
                        required: "请输入活动名称",
                        maxlength: "最多输入50个字符"
                    },
                    "type":  {
                        required: "请选择类型"
                    },
                    "merberType":  {
                        required: "请选择会员类型"
                    },
                    "startDate":  {
                        required: "请选择开始时间"
                    },
                    "endDate":  {
                        required: "请选择结束时间"
                    },
                    "rule":  {
                        required: "请输入积分设置",
                        digits : "必须输入正整数",
                        min: "不能小于1",
                        max: "不能大于10001"
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

            var status2 = $.trim($("input[name='status2']").val());
            if(status2 && (status2 == 2)){
                $("#type").attr("disabled","disabled");
                $("#merberType").attr("disabled","disabled");
                $("#rule").attr("readonly","readonly");
                $("#startDate").attr("readonly","readonly");
                $("#endDate").attr("readonly","readonly");
            }else{
                $("#startDate").addClass("dpd_start");
                $("#endDate").addClass("dpd_end");
                
                // disabling datetime
                var now_date = new Date();
                var now_date2 = new Date(now_date.getFullYear(), now_date.getMonth(), now_date.getDate(), now_date.getHours(), now_date.getMinutes(), 0, 0);

                var checkin_start = $('.dpd_start').datetimepicker({
                    onRender: function(date) {
                        return date.valueOf() < now_date2.valueOf() ? 'disabled' : '';
                    },
                    format: 'yyyy-mm-dd hh:ii:ss',
                    todayBtn: true,
                    autoclose: true,
                    minuteStep: 10
                }).on('changeDate', function(ev) {
                    if (ev.date.valueOf() > checkout_end.date.valueOf()) {
                        var newDate = new Date(ev.date)
                        newDate.setDate(newDate.getDate() + 1);
                        checkout_end.setValue(newDate);
                    }
                    checkin_start.hide();
                    $('.dpd_end')[0].focus();
                }).data('datetimepicker');
                var checkout_end = $('.dpd_end').datetimepicker({
                    onRender: function(date) {
                        return date.valueOf() <= checkin_start.date.valueOf() ? 'disabled' : '';
                    },
                    format: 'yyyy-mm-dd hh:ii:ss',
                    todayBtn: true,
                    autoclose: true,
                    minuteStep: 10
                }).on('changeDate', function(ev) {    }).on('changeDate', function(ev) {
                    /*if (ev.date.valueOf() < checkin_start.date.valueOf()) {
                        var newDate = new Date(ev.date)
                       // newDate.setDate(newDate.getDate() - 1);
                        checkin_start.setValue(newDate);
                    }*/
                    checkout_end.hide();
                }).data('datetimepicker');
            }
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

        function change_merberType(obj) {
            var _val = $(obj).val();
            $("#merberType").val("");
            var _options = $("#merberType > option");
            _options.css("display","block");
            $.each(_options,function(){
                if(_val == 1 && $(this).val() == 3){
                    $(this).css("display","none");
                    return false;
                }
                if(_val == 2 && $(this).val() != 3){
                    $(this).css("display","none");
                    //return false;
                }
            });
        }

        $(function () {
            getCodeItem('merberType',"#merberType");
            getCodeItem('type',"#type");
        });
    </script>
</head>
<body class="body">
<div class="col-xs-12">
    <div class="form">
        <form class="cmxform form-horizontal" method="post" action="${pageContext.request.contextPath}/platform/integralRule/syncedit.do" novalidate="novalidate">
            <input type="hidden" name="id" value="${entity.id}"/>
            <input type="hidden" name="status2" value="${status2}"/>
            <div class="form-group ">
                <label for="name" class="control-label col-xs-2 text-right">活动名称<span class="required">*</span></label>
                <div class="col-xs-8">
                    <input class="form-control" api="trim" id="name" name="name" value="${entity.name}" type="text">
                </div>
            </div>
            <div class="form-group ">
                <label class="control-label col-xs-2 text-right">类型<span class="required">*</span></label>
                <div class="col-xs-8">
                    <select name="type" class="form-control" style="width:250px" id="type" val="${entity.type}" onchange="change_merberType(this);"></select>
                </div>
            </div>
            <div class="form-group ">
                <label class="control-label col-xs-2 text-right">会员类型<span class="required">*</span></label>
                <div class="col-xs-8">
                    <select name="merberType" class="form-control" style="width:250px" id="merberType" val="${entity.merberType}"></select>
                </div>
            </div>
            <div class="form-group ">
                <label for="rule" class="control-label col-xs-2 text-right">积分设置<span class="required">*</span></label>
                <div class="col-xs-10" style="display: inline-flex;">
                    <span style="padding-top: 7px;">1元= </span>
                    <input class="form-control" api="trim" id="rule" name="rule" value="${entity.rule}" type="text" style="width: 100px;">
                    <span style="padding-top: 7px;color: red;"> 积分(请输入正整数1-10000)</span>
                </div>
            </div>

            <div class="form-group ">
                <label class="control-label col-xs-2 text-right">起止时间<span class="required">*</span></label>
                <div class="input-group input-large custom-date-range col-xs-8" style="padding: 0px 15px;">
                    <input type="text" class="form-control" id="startDate" name="startDate" value="<fmt:formatDate value="${entity.startDate}" pattern="yyyy-MM-dd  HH:mm:ss" />"/>
                    <span class="input-group-addon">-</span>
                    <input type="text" class="form-control" id="endDate" name="endDate" value="<fmt:formatDate value="${entity.endDate}" pattern="yyyy-MM-dd  HH:mm:ss" />"/>
                </div>
            </div>
            <div class="form-group ">
                <label for="remark" class="control-label col-xs-2 text-right">备注</label>
                <div class="col-xs-8">
                    <textarea class="form-control" api="trim" name="remark" id="remark" cols="10" rows="3">${entity.remark}</textarea>
                </div>
            </div>
        </form>
    </div>
</div>
</body>
</html>
