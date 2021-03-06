 function validateForm() {
            $("form").validate({
                rules: {
                    "buildingName": {
                    	required:true,
                    	maxlength:32
                    },
                    "sort":{
                    	digits:true,
                        required:true,
                        maxlength:11
                    }
                },
                messages: {
                	"buildingName": {
                    	required:"请输入楼栋名称",
                    	maxlength:"字符长度过长"
                    },
                	"buildingName": "请输入楼栋名称",
                    "sort":{
                    	digits:"请输入正整数",
                        required:"请输入排序号码"
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
