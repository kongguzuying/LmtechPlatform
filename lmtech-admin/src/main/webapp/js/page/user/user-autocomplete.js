function userInitAutoComplete(options) {
	var _url = options.url;
	var _selectId = options.selectId;
	var _idAttr = options.idAttr ? options.idAttr : "id";
	var _textAttr = options.textAttr ? options.textAttr : "text";
	var _onSelected = options.onSelected ? options.onSelected : null;
	var _minLength = options.minLength ? options.minLength : 2;
	var _data = options.data ? options.data : {};
	//console.error(_minLength);
	var elem = $("#" + _selectId);
	buildElement(elem);

	elem
			.typeahead({
				items : 100,
				minLength : _minLength,
				source : function(query, process) {
					_data.key = query;
					$
							.ajax({
								url : _url,
								method : "POST",
								data : _data,
								dataType : "json",
								success : function(exeResult) {
									if (exeResult.success) {
										// console.error(exeResult.data);
										if (exeResult.data) {
											var results = [];
											var data = exeResult.data;
											//console.error(data);
											var divHtml = '<div class="autocomp-item" sid="{0}" uname="{1}" uloginName="{2}" uflag="{3}" uenable="{4}">{5}</div>';
											$(divHtml).data("obj",data);
											for (var i = 0; i < data.length; i++) {
												var obj = data[i];
												var id = obj[_idAttr];
												var uname = obj["name"];
												var uloginName = obj["loginName"];
												var uflag = obj["flag"];
												var uenable = obj["enable"];
												var departmentName = (obj["departmentName"]) ? "("
														+ obj["departmentName"]
														+ ")"
														: "";
												var text = uname
														+ departmentName;
												
												results[i] = divHtml.format(id,
														uname,uloginName,uflag,uenable, text);
											}
											process(results);
										}
									} else {
										alert(exeResult.message ? exeResult.message
												: "加载数据过程中出现错误");
									}
								}
							});

				},
				highlighter : function(item) {
					return item;
				},
				updater : function(item) {
					var itemElem = $(item);
					var id = itemElem.attr("sid");
					var uname = itemElem.attr("uname");
					var uloginName = itemElem.attr("uloginName");
					var uflag = itemElem.attr("uflag");
					var uenable = itemElem.attr("uenable");
					var user = {id:id,name:uname,loginName:uloginName,flag:uflag,enable:uenable};
//					console.error(user);
					// var text = itemElem.text();
					// elem.val(text);
					if (_onSelected) {
						_onSelected(user);
					}
					return "";
				}
			});
}
function buildElement(elem) {
	elem.attr("autocomplete", "off");
//	elem.focus();
}