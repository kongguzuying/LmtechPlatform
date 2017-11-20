function buildData(datas){
	for(var i in datas){
		var d = datas[i];
        d.text = d.positionName;
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
function buildCheckItemData(datas,p){
       	//console.error(p);
   	for(var i in datas){
   		var d = datas[i];
   		var chkId=(p && p.chkId)?p.chkId+"_"+d.id:d.id;
   		d.chkId = chkId;
           d.text = '<input type="checkbox" name="'+d.chkId+'"/>' + d.itemName;
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
}
       
function checkItemStaticDataSource(openedParentData, callback) {
	  var d = (openedParentData.id)?openedParentData.children: checkItemJson;
	  var p = (openedParentData.id)?openedParentData:null;
	  buildCheckItemData(d,p);
	  callback({
	    data: d
	  });
}
function staticDataSource(openedParentData, callback) {
	  var d = (openedParentData.id)?openedParentData.children:json;
	  buildData(d);
	  callback({
	    data: d
	  });
}
 	jQuery(document).ready(function() {
	            var tree = $('#positionTree').tree({
	                dataSource: staticDataSource,
	                multiSelect: false,
	                folderSelect: true
	            });
	            
	            $("#btnAddRoot").unbind("click");
	            $("#btnAddRoot").click(function(){
	            	addRoot();
	            });
	            
	            $('#positionTree').on("selected.fu.tree",function(event,data){
	            	buildActions(data);
	            	showCheckItem(data.target);
	            });
	            $('#positionTree').on("deselected.fu.tree",function(event,data){
	            	$("#checkItemTreeContainer").html("请选择区域");
	            });
	            
	            
	            var tree = $('#checkItemTree').tree({
	                //selectable: false,
	                //dataSource: treeDataSource,
	                dataSource: checkItemStaticDataSource,
	                multiSelect: false,
	                folderSelect: false
	            });

	            $('#checkItemTree').tree("discloseAll");
	            
	    });
	 	
	 	function copySelectTree(){
	 		$("#checkItemTreeContainer").html("");
	 		var $tempContainer = $("#checkItemTreeTemp").clone();
	 		
        	var t = $($tempContainer.html());
        	t.attr("id","selectTree");
        	//t.find("li.hide,input[type='checkbox']").remove();
        	//$tempContainer.find("li.hide,input[type='checkbox']").remove();
        	$("#checkItemTreeContainer").append(t);
        	$("#checkItemTreeContainer").find("li.hide").remove();
        	$("#checkItemTreeContainer").find("input[type='checkbox']").hide();
        	$("#checkItemTreeContainer").find("ul,li").hide();
        	
	 	}
	 	
	 	function buildCheckItemCis(cis){
	 		var ids = [];
	 		for(var i in cis){
	 			ids.push(cis[i].id);
	 		}
	 		return ids;
	 	}
	 	
	 	function showSelectTree(selectNameArr){
	 		$("#checkItemTreeContainer").find("ul,li").hide();
	 		$("#selectTree").show();
	 		//console.error(selectNameArr);
			for(var i in selectNameArr){
				var name = selectNameArr[i];
				if(name){
					$("#checkItemTreeContainer").find("input[type='checkbox'][name*='"+name+"']").parents("ul,li").show();
				}
			}
	 	}
	 	
	 	//构建操作按钮
	 	function buildActions(data){
	 		//console.error(data);
	 		$("li.fuelux-tree-item").find(".tree-actions").addClass("hide");
	 		//if(data && data.target.level==0){		 		
		 		$select = $("li.tree-selected");
		 		$select = $select.children("div.tree-branch-header").length > 0?$select.children("div.tree-branch-header"):$select;
	           	var isAppend = $select.children(".tree-actions").length;
	           	if(!isAppend){
	           		$select.append('<div class="tree-actions pull-right"><i class="fa fa-plus" onclick="addItem(this)"></i><i class="fa fa-pencil" onclick="update(this)"></i><i class="fa fa-trash-o" onclick="removeById()"></i></div>');
	           		
	           	}
	            $select.find(".tree-actions").removeClass("hide");
	 		//}
	 	}
	 	
	 	//添加根级
	 	 function addRoot() {
	            var url = URL_BASE + "/position/toEdit.do?id=&parentId=&parentLevel=";
	            var modal = top.showModal({
	                url: url,
	                title: "添加区域",
	                opener: window,
	                width: 350,
	                height: 200/* ,
	                okFunc: function () {
	                    
	                } */
	            });
	     }
	 	//添加子级
	 	 function addItem(obj) {
	 		var items = $('#positionTree').tree('selectedItems');
	 		var $li = $(obj).parents("li.fuelux-tree-item");
	 		if(items.length == 1){
	 			var item = items[0];
	 			var parentId = item.id;
	 			var level = item.level;
	        	 var url = URL_BASE + "/position/toEdit.do?id=&parentId="+ parentId + "&parentLevel="+level;
		            var modal = top.showModal({
		                url: url,
		                title: "添加区域",
		                opener: window,
		                width: 350,
		                height: 200/* ,
		                okFunc: function () {
		                    
		                } */
		            });
	 		}else{
	 			top.alertMessage("请选择一个区域");
	 		}
        	
	     }
	 	
	 	//修改区域
	 	 function update() {
	 		var items = $('#positionTree').tree('selectedItems');
	 		if(items.length == 1){
	 			var item = items[0];
	 			var id = item.id;
	        	 var url = URL_BASE + "/position/toEdit.do?id="+id+"&parentId=&parentLevel=";
		            var modal = top.showModal({
		                url: url,
		                title: "修改区域",
		                opener: window,
		                width: 350,
		                height: 200
		            });
	 		}else{
	 			top.alertMessage("请选择一个区域");
	 		}
        	
	     }
	 	
	 	function removeById(){
	 		var items = $('#positionTree').tree('selectedItems');
	 		if(items.length == 1){
	 			var item = items[0];
	 			if(item.cis.length > 0
	 					|| item.children.length > 0){
	 				top.alertMessage("没有子区域且没有关联的部位才可以被删除!");
	 				return;
	 			}
	 			top.confirmMessage("确定删除区域吗",function(res){
	 				var id = item.id;
		        	var url = URL_BASE + "/position/removeById.do?id="+id;
		        	$.post(url,{},function(res){
		        		if(res.success){
		        			window.location.href = URL_BASE + "/position/allList.do";
		        		}else{
		        			top.alertMessage(res.message);
		        		}
		        	});	 				
	 			});
	 			
		 		//listRemove(url);
	 		}else{
	 			top.alertMessage("请选择一个区域");
	 		}
	 	}
	 	//获取选中的区域
	 	function getSelectItem(){
	 		var items = $('#positionTree').tree('selectedItems');
	 		if(items.length == 1){
	 			return items[0];
	 		}
	 		return null;
	 	}
	 	
	 	//以下为右边树 部位 
	 	
	 	function showCheckItem(data){
	 		$("#checkItemTreeContainer").html("");
	 		if(data.cis.length > 0){
	 			copySelectTree();
	 			var ids = buildCheckItemCis(data.cis);
	 			showSelectTree(ids);
	 		}else{
		 		$("#checkItemTreeContainer").html("该区域没有部位");		
	 		}
	 	}
	 	
	 	function setCheckItem(){
	 		var p = getSelectItem();
	 		if(p && p.level == 0 && p.id){
	 			var id = p.id;
	        	 var url = URL_BASE + "/position/toSetCheckItem.do?id="+id;
		            var modal = top.showModal({
		                url: url,
		                title: "设置部位",
		                opener: window,
		                width: 700,
		                height: 500,
						okFunc: function () {
							var positionId = top.getModalFrameContent().find("#positionId").val();
							var selectNameArr = modal.frame.selectNameArr;
							var url = URL_BASE + "/position/setCheckItem.do";
							//console.error(selectNameArr);
							var checkItemId = "";
							for ( var i in selectNameArr) {
								var name = selectNameArr[i];
								if(name){
									var nameArr = name.split("_");
									var id = nameArr[nameArr.length -1];
									if(checkItemId.length > 0){
										checkItemId+=",";
									}
									checkItemId+=id;
								}
							}
							var data = {positionId:positionId,checkItemId:checkItemId};
							$.post(url,data,function(res){
								if(res.success){
									top.hideModal();
									window.location.href = URL_BASE + "/position/allList.do";
								}
							});
		                }
		            });
	 		}else{
	 			top.alertMessage("请选择一个根级区域");
	 		}
	 	}