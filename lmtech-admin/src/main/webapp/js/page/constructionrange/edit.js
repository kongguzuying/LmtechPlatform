 		var ciidArr = ciids.split(",");
 		var ciObjs = [];
	 	jQuery(document).ready(function() {
	            
	            var TEXT_TEMP = '<input type="checkbox" name="{0}" cname="{1}"/>{1}';
	            function buildData(datas,p){
	            	for(var i in datas){
	            		var d = datas[i];
	            		var chkId=(p && p.chkId)?p.chkId+"_"+d.id:d.id;
	            		d.chkId = chkId;
	            		for(var y in ciidArr){
	            			var ciid = ciidArr[y];
	            			if(ciid == d.id){
	            				var ciObj = {id:d.id,cname:d.itemName};
	            				//console.error(ciObj);
	            				ciObjs.push(ciObj);
	            			}
	            		}
	            		var text = TEXT_TEMP.format(d.chkId,d.itemName);
	                    d.text = text;
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
	            
	            function staticDataSource(openedParentData, callback) {

	            	  var d = (openedParentData.id)?openedParentData.children:json;
	            	  var p = (openedParentData.id)?openedParentData:null;
	            	  buildData(d,p);
	            	  callback({
	            	    data: d
	            	  });
	            }	            
	            
	            
	            var tree = $('#allTree').tree({
	                dataSource: staticDataSource,
	                multiSelect: false,
	                folderSelect: true
	            });
	            $('#allTree').on("disclosedAll.fu.tree",function(event,data){
	            	initCheckBox();
	            	//console.error(selectNameArr);
	            });

	            $('#allTree').tree("discloseAll");
	            
	            checkboxEvent();
	    });
	 	
	 	//checkbox name数组
	 	var selectNameArr = [];	 	
	 	
	 	function checkboxEvent(){
	 		$("#allTree input[type='checkbox']").click(function(){	 			
	 			$self = $(this);
	 			var name = $self.attr("name");
	 			//console.error(name);
	 			if(name){
		 			var nameArr = name.split("_");
		 			//console.error(name);
		 			var id = nameArr[nameArr.length-1];
		 			var cname = $self.attr("cname");
		 			var obj = {id:id,cname:cname};
		 			//console.error("id="+ id);
		 			var ched = $self.attr("checked")?true:false;
		 			if(ched){
		 				//console.error("选中id="+id);
		 				toggleCheckbox(name,ched);
		 				buildCiidObj(obj,"add");
		 			}else{
		 				//console.error("取消id="+id);
		 				var tempId ="";
		 				for(var i in nameArr){
		 					if(i > 0){
		 						tempId +="_";
		 					}
		 					tempId += nameArr[i];
		 					$("#allTree input[type='checkbox'][name='"+tempId+"']").attr("checked",false);
		 				}
		 				toggleCheckbox(id,ched);
		 				buildCiidObj(obj,"delete");
		 			}
		 			//console.error(ciidArr.join(","));
	 			}
	 		});
	 	}
	 	
	 	function buildCiidObj(obj,type){
	 		if(ciObjs.length > 0){
	 			var i = 0;
	 			for(;i < ciObjs.length ; i ++){
	 				var ciObj = ciObjs[i];
	 				if(obj.id == ciObj.id){
	 					break;
	 				}
	 			}
	 			if(type == "add"){
		 			if(i >= ciObjs.length){
		 				ciObjs.push(obj);
		 			}
	 			}else{
	 				if(i < ciObjs.length){
	 					ciObjs.splice(i, 1);
		 			}
	 			}
	 		}else if(type == "add"){
	 			ciObjs.push(obj);
	 		}
	 	}
	 	
	 	function initCheckBox(){
	 		if(ciidArr && ciidArr.length > 0){
	 			for(var i in ciidArr){
	 				var ciid = ciidArr[i];
	 				toggleCheckbox(ciid,true);
	 			}
	 		}
	 	}
	 	
	 	function toggleCheckbox(name,checked){
	 		$("#allTree input[type='checkbox'][name*='"+ name+"']").attr("checked",checked);
	 	}