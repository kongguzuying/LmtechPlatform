$(function () {
	var selIds = $("#viewSelIds").val();
	selIds = ("," + selIds + ",");
	$("input:checkbox").each(function () {
		var val = "," + $(this).val() + ",";
		if (selIds.indexOf(val) >= 0) {
			$(this).attr("checked", "checked");
		}
	});
});
function checkAll(elem) {
	var on = $("#allBtn").attr("checked");
	$("input:checkbox").each(function () {
		if ($(this).attr("id") != "allBtn") {
			if (on) {
				$(this).attr("checked", "checked");
			} else {
				$(this).removeAttr("checked");
			}
		}
	});
}
function beforeSubmit() {
	var unSelectedIds = "";
	var i = 0;
	$("input:checkbox").each(function () {
		if ($(this).attr("id") != "allBtn") {
			var on = $(this).attr("checked");
			if (!on) {
				if (i > 0) {
					unSelectedIds += ",";
				}
				unSelectedIds += $(this).val();
				i++;
			}
		}
	});
	if (unSelectedIds) {
		$("#unSelectedIds").val(unSelectedIds);
	}
}