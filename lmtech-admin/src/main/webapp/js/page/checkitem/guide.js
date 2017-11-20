var maxfilesize = 50;
var _url_001 = URL_BASE + "/checkitem/getMaxFileSize.do?time="+new Date().getTime();
function buildData(datas){
	            	for(var i in datas){
	            		var d = datas[i];
	                    //d.text = d.itemName+'<span></span>';	
	            		if(d.guide){
		                    d.text = d.itemName+'<div isHaveGuide="yes" val="'+d.id+'"></div>';
	            		}else{
		                    d.text = d.itemName+'<div isHaveGuide="no" val="'+d.id+'"></div>';	
	            		}                    
	                    d.type="item";
	                    d.attr = {hasChildren:false};
	                    d.attr.cssClass = "fuelux-tree-item";
						if(d.children && d.children.length >0){
							d.type="folder";
							d.attr.hasChildren = true;
		                    d.additionalParameters={"children":d.children};	
						}
	            	}
}           
	            
	            
function staticDataSource(openedParentData, callback) {
	var d = (openedParentData.id)?openedParentData.children:json;
	buildData(d);
	callback({data: d});
}
	$(function(){
		$.ajax({
            url: _url_001,
            async:false,
            success: function (data) {
            	maxfilesize = data;
            }
        });
        var myDropzone = new Dropzone("div#fileUpload", {
            url: URL_BASE + "/checkitem/importCheckItems.do",
            method: "post",
            maxFilesize:maxfilesize, //MB
            dictFileTooBig:"文件过大超过最大限制"+maxfilesize+"MB!",
            acceptedFiles:".xls,.xlsx", //上传的类型
            dictInvalidFileType:"导入数据的文件,文件类型必须是.xls或.xlsx!",
            clickable: true,
            sending: function () {
            	 $.isLoading({ text: "部位数据导入中，请稍候......" });
            },
            success: function (data, exeResult) {
                if (exeResult.success) {
                    top.showShortMessage({ text: "导入部位数据成功！" });
                    window.location.reload();
                } else {
                    top.alertMessage(exeResult.message ? exeResult.message : "上传过程出现未知错误");
                }
            },
            complete: function () {
                hideLoading();
            },
            error: function (file, text) {
            	top.alertMessage(text);
            	window.location.reload();
            }
        });
        
var tree = $('#FlatTree4').tree({
	                dataSource: staticDataSource,
	                multiSelect: false,
	                folderSelect: true
	            });
	            
	            $('#FlatTree4').on("selected.fu.tree",function(event,data){

		            //buildActions("#FlatTree4");
	            	//console.error(data);
	            	showGuide(data.target);
	            });
	            $('#FlatTree4').on("deselected.fu.tree",function(event,data){
	            	//console.error("deselected.fu.tree");
	            	$("#divGuideContainer").html("请选择部位");
	            });
	            
	            $('#FlatTree4').on("disclosedFolder.fu.tree",function(event,data){
	            	for(var i in data.children){
	            		var d = data.children[i];
	            		var $btn = $("#FlatTree4").find("div[isHaveGuide][val='"+d.id+"']")
	            		.parent("span")
	            		.parent("button");
	            		var $container = $btn.parent("div.tree-branch-header").length?$btn.parent("div.tree-branch-header"):$btn.parent("li");
	            		
	                	if(!$container.children("font").length){
		                	var val = d.id;
		                	var color = "red";
		                	var text = "未设置";
		                	if(d.guide){
		                		color = "green";
		                		text = "已设置";
		                	}
	                		var html = fontHtml.format(color,text,val);
	                		$container.append(html);
	                	}
	            	}
	            	
	            });	            
	            buildActions("#FlatTree4");
	});
	


	 function importExcel() {
        $("div#fileUpload").click();
    }
	 	
	 	var fontHtml = '<font class="pull-right" color="{0}" val="{2}">{1}</font>';
	 	function buildActions(root){
	 		$(root).find("div[isHaveGuide]").each(function(i,o){
            	var isHaveGuide = $(o).attr("isHaveGuide");
	 			//console.error(isHaveGuide);
            	var color = "red";
            	var text = '未设置';
            	var val = $(o).attr("val");
            	if(isHaveGuide == "yes"){
            		html +=' color="green">已设置';
            		color = "green";
            		text = '已设置';
            	}
            	var html = fontHtml.format(color,text,val);
            	$container = $(this).parents("li").children("div.tree-branch-header,li.fuelux-tree-item").length?$(this).parents("li").children("div.tree-branch-header,li.fuelux-tree-item"):$(this).parents("li");
            	
            	$container.children("font").remove();
            	$container .append(html);
            });
	 	}
	 	
	 	//获取选中的部位
	 	function getSelectItem(){
	 		var items = $('#FlatTree4').tree('selectedItems');
	 		if(items.length == 1){
	 			return items[0];
	 		}
	 		return null;
	 	}
	 	//操作指引(添加&修改)
	 	function executeGuide(){
	 		var item = getSelectItem();
	 		if(item){
	 			if(item.type == "folder"){
	 				top.alertMessage("请先选择末级部位");
	 				return;
	 			}
	 			$("#divGuideContainer").html($("#divForm").html());
	 			$("#divGuideContainer form").attr("id","guideForm");
	 			if(item.guide){
	 				$("#guideForm").find("#guide").text(item.guide);
	 			}
	 			$("#guideForm #id").val(item.id);
	 			
	 		}else{
	 			top.alertMessage("请选择一个部位");
	 		}
	 	}
	 	
	 	function doGuide(){
 			var url = URL_BASE + "/checkitem/doGuide.do";
 			var data = $("#guideForm").serialize();
 			var guide = $("#guideForm").find("#guide").val();
 			$.post(url,data,function(res){
 				if(res.success){
 					//$("#divGuideContainer").html(guide);
 					var item = getSelectItem();
 					item.guide = guide;
 					showGuide(item);
 				}
 			});
	 	}
	 	//显示指引
	 	function showGuide(checkItemJson){
	 		//console.error(checkItemJson);
	 		$("#divGuideContainer").html("");
	 		if(checkItemJson && checkItemJson.guide){
	 			$("#divGuideContainer").append(checkItemJson.guide);
	 			$("#btnGuideExecute").html("修改");
	 			buildIsHaveGuide(checkItemJson.id,true);
	 		}else{
	 			$("#divGuideContainer").html("该部位下暂无检查指引");
	 			$("#btnGuideExecute").html("添加");
	 			buildIsHaveGuide(checkItemJson.id,false);
	 		}
	 	}
	 	
	 	function buildIsHaveGuide(id,yes){
	 		var $div = $("div[val='"+id+"']");
	 		if(yes){
	 			$div.attr("isHaveGuide","yes");
	 			$("font[val='"+id+"']").attr("color","green").text("已设置");
	 		}else{
	 			$div.attr("isHaveGuide","no");
	 			$("font[val='"+id+"']").attr("color","red").text("未设置");
	 		}
        	//buildActions("#FlatTree4");
	 	}
	 	
	 	function cancelClick(){
	 		var item = getSelectItem();
	 		showGuide(item);
	 	}
	 	
	 	//导出
	 	function exportOut(){
	 		var _url = URL_BASE + "/checkitem/doExportCheckItem.do?time="+new Date().getTime();
	 		window.location.href = _url; 
	 	}
