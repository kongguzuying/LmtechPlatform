$(function(){
	showIsForRough();
	showIsForDecorated();
});
//显示适用范围
function showIsForRough(){
	var arrs = isForRough.split(",");
	for(var i = 0; i < arrs.length ; i ++){
		var val = arrs[i];
		$("#isForRough" + val).attr("checked","checked");
	}
}
//显示适用环节
function showIsForDecorated(){
	var arrs = isForDecorated.split(",");
	for(var i = 0; i < arrs.length ; i ++){
		var val = arrs[i];
		$("#isForDecorated" + val).attr("checked","checked");
	}
}

//dailog方式需要申明验证函数(validateForm)
function validateForm() {
    $("form").validate({
        rules: {
            "itemName": {
            	required:true,
            	maxlength:20
            },
            "isForRough": "required",
            "isForDecorated": "required",
            "sort": {
            	required:true,
            	min:0
            }
        },
        messages: {
            "itemName": {
            	required: "请输入部位",
            	maxlength: "最多只能输入20个字符"
            },
            "isForRough": "请输入适用范围",
            "isForDecorated": "请输入适用环节",
            "sort": {
            	required : "请输入排序",
            	min: "不能输入负数"
            }
         
        },
        errorPlacement: function (error, element) { //指定错误信息位置
            if (element.is(':radio') || element.is(':checkbox')) { //如果是radio或checkbox
             var eid = element.attr('name'); //获取元素的name属性
             element.parent().parent().append("</br>");
             error.appendTo(element.parent().parent()); //将错误信息添加当前元素的父结点后面
           } else {
             error.insertAfter(element);
           }
        }
    });
    return $("form").valid();
}