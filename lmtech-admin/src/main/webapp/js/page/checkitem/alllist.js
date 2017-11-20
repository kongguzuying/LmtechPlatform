
	 	jQuery(document).ready(function() {	            
	            var spanHtml = '<span id="{0}" uid="{1}"></span>';
	            function buildData(parent,datas){
	            	for(var i in datas){
	            		var d = datas[i];
	                    var uid = (parent.uid)?parent.uid:"";
	                    if(uid){
	                    	uid+="_";
	                    }
	                    uid +=d.id;
	                    //console.error(uid);
	                    d.uid = uid;
	                    d.text = d.itemName + spanHtml.format(d.id,d.uid);               
	                    d.type="item";
	                    d.attr = {hasChildren:false};
	                    d.attr.cssClass = "fuelux-tree-item";
						if(d.children && d.children.length >0){
							d.type="folder";
		                    //d.attr.cssClass = ".tree-branch";
							d.attr.hasChildren = true;
		                    d.additionalParameters={"children":d.children};	
						}
	            	} 
	            	//console.error(datas);

	            }            
	            
	            function staticDataSource(openedParentData, callback) {

	            	  var d = (openedParentData.id)?openedParentData.children:json;
	            	  buildData(openedParentData,d);
	            	  callback({
	            	    data: d
	            	  });
	            }          
	            
	            
	            var tree = $('#FlatTree4').tree({
	                dataSource: staticDataSource,
	                multiSelect: false,
	                folderSelect: true
	            });
	            
	            $("#btnAddRoot").unbind("click");
	            $("#btnAddRoot").click(function(){
	            	addRoot();
	            });
	            
	            $('#FlatTree4').on("selected.fu.tree",function(event,data){
	            	buildActions();
	            	//console.error(data);
	            	showDesc(data.target);
	            });
	            $('#FlatTree4').on("deselected.fu.tree",function(event,data){
	            	//console.error("deselected.fu.tree");
	            	$("#divDescContainer").html("请选择部位");
	            	$("li[role='treeitem']").find(".tree-actions").addClass("hide");
	            });
	            
	            
	           /*  $('#FlatTree4').on("disclosedFolder.fu.tree",function(opts){
	            	//treeItemBandingEvent();
	            }); */
	            
	            
	            
	    });
	 	//构建操作按钮
	 	function buildActions(){
	 		$("li.fuelux-tree-item").find(".tree-actions").addClass("hide");
	 		$select = $("li.tree-selected");
	 		$select = $select.children("div.tree-branch-header").length > 0?$select.children("div.tree-branch-header"):$select;
           	var isAppend = $select.children(".tree-actions").length;
           	if(!isAppend){
           		$select.append('<span class="tree-actions"><i class="fa fa-plus" onclick="addItem(this)"></i><i class="fa fa-pencil" onclick="update(this)"></i><i class="fa fa-trash-o" onclick="removeById()"></i></span>');
           		
           	}
            $select.find(".tree-actions").removeClass("hide");
	 	}
	 	
	 	function treeItemBandingEvent(){
            $("li.fuelux-tree-item[haschildren!=true],div.tree-branch-header").mousemove(function(){
            	var isAppend = $(this).children(".tree-actions").length;
            	if(!isAppend){
            		$(this).append('<div class="tree-actions pull-right"><i class="fa fa-plus" onclick="addItem(this)"></i><i class="fa fa-pencil" onclick="update(this)"></i><i class="fa fa-trash-o"></i></div>');
            		
            	}
            	$(this).find(".tree-actions").removeClass("hide");
            });
            $("li.fuelux-tree-item[haschildren!=true],div.tree-branch-header").mouseout(function(){
            	$(this).find(".tree-actions").addClass("hide");
            });
	 	}
	 	//添加根级
	 	 function addRoot() {
	            var url = URL_BASE + "/checkitem/toEdit.do?id=&parentId=&parentLevel=";
	            var modal = top.showModal({
	                url: url,
	                title: "添加部位",
	                opener: window,
	                width: 600,
	                height: 400/* ,
	                okFunc: function () {
	                    
	                } */
	            });
	     }
	 	//添加子级
	 	 function addItem(obj) {
	 		var items = $('#FlatTree4').tree('selectedItems');
	 		var $li = $(obj).parents("li.fuelux-tree-item");
	 		if(items.length == 1){
	 			var item = items[0];
	 			var uid = $("#"+item.id).attr("uid");
	 			var level = uid.split("_").length -1;
	 			if(level >=4){
	 				top.alertMessage("最多只能添加5级部位");
	 				return;
	 			}
	 			if(item.descs.length > 0){
	 				top.alertMessage("已添加问题描述的部位，不允许新增子级部位");
	 				return;
	 			}
	 			var parentId = item.id;
	        	 var url = URL_BASE + "/checkitem/toEdit.do?id=&parentId="+ parentId + "&parentLevel="+level;
		            var modal = top.showModal({
		                url: url,
		                title: "添加部位",
		                opener: window,
		                width: 600,
		                height: 400/* ,
		                okFunc: function () {
		                    
		                } */
		            });
	 		}else{
	 			top.alertMessage("请选择一个部位");
	 		}
        	
	     }
	 	
	 	//修改部位
	 	 function update() {
	 		var items = $('#FlatTree4').tree('selectedItems');
	 		if(items.length == 1){
	 			var item = items[0];
	 			var id = item.id;
	        	 var url = URL_BASE + "/checkitem/toEdit.do?id="+id+"&parentId=&parentLevel=";
		            var modal = top.showModal({
		                url: url,
		                title: "修改部位",
		                opener: window,
		                width: 600,
		                height: 400
		            });
	 		}else{
	 			top.alertMessage("请选择一个部位");
	 		}
        	
	     }
	 	
	 	function removeById(){
	 		var items = $('#FlatTree4').tree('selectedItems');
	 		if(items.length == 1){
	 			var item = items[0];
	 			if(item.descs.length > 0
	 					|| item.children.length > 0){
	 				top.alertMessage("没有子部位且没有问题描述的部位才可以被删除!");
	 				return;
	 			}
	 			
	 			top.confirmMessage("确定删除部位吗",function(res){
	 				if(res){
	 					var id = item.id;
	 		        	var url = URL_BASE + "/checkitem/removeById.do?id="+id;
	 		        	$.post(url,{},function(res){
	 		        		if(res.success){
	 		        			 window.location.href = URL_BASE + "/checkitem/allList.do";
	 		        		}else{
	 		    	 			top.alertMessage(res.message);	        			
	 		        		}
	 		        	});
	 				}
	 			});
	 			
		 		//listRemove(url);
	 		}else{
	 			top.alertMessage("请选择一个部位");
	 		}
	 	}
	 	//获取选中的部位
	 	function getSelectItem(){
	 		var items = $('#FlatTree4').tree('selectedItems');
	 		if(items.length == 1){
	 			return items[0];
	 		}
	 		return null;
	 	}
	 	//添加问题
	 	function addDesc(){
	 		var items = $('#FlatTree4').tree('selectedItems');
	 		if(items.length == 1){
	 			var item = items[0];
	 			if(item.type == "folder"){
	 				top.alertMessage("请先选择末级部位");
	 				return;
	 			}
	 			var id = item.id;
	 			 var url = URL_BASE + "/checkitemdesc/toEdit.do?id=&checkItemId="+id;
		            var modal = top.showModal({
		                url: url,
		                title: "添加问题描述",
		                opener: window,
		                width: 580,
		                height: 370,
		                okFunc: function () {
		                	if(modal.frame.validateForm()){
			                    url = URL_BASE + "/checkitemdesc/add.do";
			                    var data = top.getModalFrameContent().find("form").serialize();
			                    //console.error("okFunc");
			                    //console.error(data);
			                    $.post(url,data,function(res){
			                    	//console.error(res);
			                    	if(res.success){
			                    		top.hideModal();
			                    		top.showShortMessage({text:"添加成功!",type:"info"});
			                    		addDescToCheckItem(res.data);
			                    		//createTr(res.data);
			                    		//createTable(item.descs);
			                    		showDesc(item);
			                    	}else{
			                    		top.alertMessage(res.message);
			                    	}
			                    });
		                	}
		                }
		            });
	 			
	 		}else{
	 			top.alertMessage("请选择一个部位");
	 		}
	 	}
	 	//显示问题
	 	function showDesc(checkItemJson){
	 		$("#divDescContainer").html("");
	 		if(checkItemJson && checkItemJson.descs.length>0){
	 			//排序
		 		checkItemJson.descs.sort(getSortFun('asc', 'sort'));
		 		//console.error(checkItemJson.descs);
	 			//console.error(checkItemJson);
	 			var t = createTable(checkItemJson.descs);
	 			$("#divDescContainer").html(t);
	 			checkboxBindEvent();
	 		}else{
	 			//$("#divDescContainer").find("table").remove();
	 			$("#divDescContainer").html("该部位下暂无问题描述");
	 		}
	 	}
	 	
	 	//复选框绑定click事件
	 	function checkboxBindEvent(){
	 		var $descTabel = $("#divDescContainer").find("table");
	 		$descTabel.find("input[type='checkbox']").click(function(){
 				var $this = $(this);
 				var ched = $this.attr("checked")?true:false;
 				if($this.attr("id") && $this.attr("id") == "chkHead"){
	 				$descTabel.find("input[type='checkbox']").attr("checked",ched);
 				}else{
 					if(ched){
 						var len = $descTabel.find("input[type='checkbox'][name='item']:checked").length;
 						var tempLen = $descTabel.find("input[type='checkbox'][name='item']").length;
 						if(len != tempLen){
 							ched = false;
 						}
 					}
 					$descTabel.find("#chkHead").attr("checked",ched);
 				}
 			});
	 		$descTabel.find("#chkHead").attr("checked",false);
	 	}
	 	
 		var descTabelHtml = '<table class="table"><thead><tr><th><input type="checkbox" id="chkHead"></th><th>问题描述</th><th>整改时限(天)</th><th>问题类别</th><th>排序号</th><th>操作</th></tr>'
        +'</thead><tbody>{0}</tbody></table>';
	 	function createTable(descs){
	 		var html = "";
            for(var i in descs){
            	var desc = descs[i];
            	html +=createTr(desc);
            }
	 		return descTabelHtml.format(html);
	 	}
	 	
	 	function createTr(desc){
	 		var tr = "";
	 		tr +='<tr id="'+desc.id+'">';
        	tr +='<td><input type="checkbox" name="item" value="'+desc.id+'"></td>';
        	tr +='<td class="remark">'+desc.remark + '</td>';
        	tr +='<td class="checkdays">'+desc.checkdays + '</td>';
			tr +='<td class="category">'+ top.getCodeItemText("checkItemDescCategory", desc.category) + '</td>';
        	tr +='<td>'+desc.sort + '</td>';
        	tr +='<td><a href="javascript:updateDesc(\''+desc.id+'\')">修改</a>&nbsp;<a href="javascript:removeDesc(\''+desc.id+'\')">删除</a></td>';
        	tr +='</tr>';
        	//$descTabel.children("tbody").append(tr);
        	return tr;
	 	}
	 	//添加问题到当前选中的部位
	 	function addDescToCheckItem(desc){
	 		var items = $('#FlatTree4').tree('selectedItems');
	 		if(items.length == 1){
	 			var item = items[0];
	 			item.descs.push(desc);
	 		}
	 	}
	 	//从当前选中的部位移除问题
	 	function removeDescFromCheckItem(descId){
	 		var items = $('#FlatTree4').tree('selectedItems');
	 		if(items.length == 1){
	 			var item = items[0];
	 			for(var i in item.descs){
	 				var desc = item.descs[i];
	 				if(desc.id == descId){
	 					delete item.descs[i];
	 					break;
	 				}
	 			}
	 		}
	 	}
	 	//从当前选中的检查中修改对应的问题
	 	function updateDescFromCheckItem(newDesc){
 			var item = getSelectItem();
 			for(var i in item.descs){
 				var desc = item.descs[i];
 				if(desc.id == newDesc.id){
 					//item.descs[i] = newDesc;
 					if(newDesc.remark){
 						desc.remark = newDesc.remark;
 					}
 					if(newDesc.checkdays > -1){
 						desc.checkdays = newDesc.checkdays;
 					}
					desc.category = newDesc.category;
 					desc.sort = newDesc.sort;
 					break;
 				}
 			}
	 	}
	 	//修改问题
	 	function updateDesc(descId){
	 		var url = URL_BASE + "/checkitemdesc/toEdit.do?id="+descId+"&checkItemId=";
            var modal = top.showModal({
                url: url,
                title: "修改问题描述",
                opener: window,
                width: 580,
                height: 370,
                okFunc: function () {
                	if(modal.frame.validateForm()){
	                    url = URL_BASE + "/checkitemdesc/update.do";
	                    var data = top.getModalFrameContent().find("form").serialize();
	                    //console.error("okFunc");
	                    //console.error(data);
	                    $.post(url,data,function(res){
	                    	//console.error(res);
	                    	if(res.success){
	                    		top.hideModal();
	                    		updateDescFromCheckItem(res.data);
	                    		//createTr(res.data);
	                    		//createTable(item.descs);
	                    		showDesc(getSelectItem());
	                    	}else{
	                    		top.alertMessage(res.message);
	                    	}
	                    });
                	}
                }
            });
	 	}
	 	//删除问题
	 	function removeDesc(descId){
	 		top.confirmMessage("确定删除问题吗?",function(res){
	 			if(res){
	 				var url = URL_BASE + "/checkitemdesc/removeById.do";
	 	        	var data = {id:descId};
	 	        	$.post(url,data,function(res){
	 	        		if(res.success){
	 	            		removeDescFromCheckItem(res.data);
	 	            		showDesc(getSelectItem());
	 	            	}else{
	 	            		top.alertMessage(res.message);
	 	            	}
	 	        	});
	 			}
	 		});        	
	 	}
	 	//批量修改整改时间
	 	function updateCheckDay(){
	 		var $descTabel = $("#divDescContainer").find("table");
	 		var ids = "";
	 		$descTabel.find("input[type='checkbox'][name='item']:checked").each(function(i,o){
	 			if(ids.length > 0){
	 				ids +=","
	 			}
	 			ids +=$(o).val();
	 		});
	 		if(ids){
	 			var url = URL_BASE + "/checkitemdesc/toUpdateCheckdays.do?ids="+ids;
	            var modal = top.showModal({
	                url: url,
	                title: "设置整改时限",
	                opener: window,
	                width: 300,
	                height: 150,
	                okFunc: function () {
	                	if(modal.frame.validateForm()){
		                    url = URL_BASE + "/checkitemdesc/updateCheckdays.do";
		                    var data = top.getModalFrameContent().find("form").serialize();
		                    //console.error(data);
		                    $.post(url,data,function(res){
		                    	//console.error(res);
		                    	if(res.success){
		                    		top.hideModal();
		                    		for(var i in res.data){
		                    			var d = res.data[i];
		                    			updateDescFromCheckItem(d);
		                    		}
		                    		showDesc(getSelectItem());
		                    	}else{
		                    		top.alertMessage(res.message);
		                    	}
		                    });
	                	}
	                }
	            });
	 		}else{
	 			top.alertMessage("请选择问题");
	 		}
	 	}
	 	
	 	//导出
	 	function exportOut(){
	 		var _url = URL_BASE + "/checkitemdesc/doExportCheckItemDesc.do?time="+new Date().getTime();
	 		window.location.href = _url; 
	 	}
	 	
// 	 	//下载模板
// 	 	function downTemplate(){
// 	 		var _url = URL_BASE + "/checkitemdesc/doDownCheckItemDesc.do?time="+new Date().getTime();
// 	 		window.location.href = _url; 
// 	 	}
	 	
	 	//导入
	 	function importExcel() {
            $("div#fileUpload").click();
        }

		function buildHot() {
			$.ajax({
				url: URL_BASE + "/checkitemdesc/buildHot.do",
				success: function (data) {
					if (data.success) {
						top.showShortMessage({
							text: "生成成功",
							type: "info"
						});
					} else {
						top.alertMessage("生成失败");
					}
				}
			});
		}
	 	
	 	
	 	$(function(){
	 		var maxfilesize = 50;
			var _url_001 = URL_BASE + "/checkitem/getMaxFileSize.do?time="+new Date().getTime();
			$.ajax({
	            url: _url_001,
	            async:false,
	            success: function (data) {
	            	maxfilesize = data;
	            }
	        });
			
            var myDropzone = new Dropzone("div#fileUpload", {
                url: URL_BASE + "/checkitemdesc/importCheckItemDesc.do",
                method: "post",
                maxFilesize:maxfilesize, //MB
                dictFileTooBig:"文件过大超过最大限制50MB!",
                acceptedFiles:".xls,.xlsx", //上传的类型
                dictInvalidFileType:"导入数据的文件,文件类型必须是.xls或.xlsx!",
                clickable: true,
                sending: function () {
                	 $.isLoading({ text: "部位与问题描述数据导入中，请稍候......" });
                },
                success: function (data, exeResult) {
                    if (exeResult.success) {
                        top.showShortMessage({ text: "部位与问题描述数据成功！" });
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
            
    	});
