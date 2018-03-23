function view(id) {
	top.showModal({
		title : "MQ异常详情",
		width : 900,
		height : 620,
		url : URL_BASE + "/household/errorinfo/edit.do?id=" + id,
		showFooter : false
	});
}

function handuse(id){
	var url=URL_BASE + "/household/errorinfo/handuse.do?id=" + id;
	toAjax(url);
}

function remove(id){
	var url=URL_BASE + "/household/errorinfo/delete.do?id=" + id;
	toAjax(url);
}
function toAjax(url){
	$.ajax({
		url:url,
		success:function(data){
			if (data.success) {
				window.location.reload();
            } else {
                top.alertMessage(data.message);
            }
		}
		
	});
}
function setStatus(elem) {
	$("#status").val($(elem).attr("status"));
	$("form").submit();
}
$(function() {
	// 设置状态
	var status = $("#status").val();
	$("button[status='" + status + "']").addClass("curr");

//	$('#sourceCode').attr('style', 'width:180px;');

});
