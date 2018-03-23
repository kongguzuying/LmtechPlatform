	 	jQuery(document).ready(function() {
	            
	            var TEXT_TEMP = '<input type="checkbox" name="{0}" cname="{1}"/>{1}';
	            function buildCiData(datas,p){
	            	//console.error(p);
	            	for(var i in datas){
	            		var d = datas[i];
	            		var chkId=(p && p.chkId)?p.chkId+"_"+d.id:d.id;
	            		d.chkId = chkId;
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
	            
	            function ciDataSource(openedParentData, callback) {
	            	 //console.error(openedParentData);
	            	  var d = (openedParentData.id)?openedParentData.children:json;
	            	  var p = (openedParentData.id)?openedParentData:null;
	            	  buildCiData(d,p);
	            	  callback({
	            	    data: d
	            	  });
	            }            
	            
	            var tree = $('#allTree').tree({
	                dataSource: ciDataSource,
	                multiSelect: false,
	                folderSelect: true
	            });
	            $('#allTree').on("disclosedAll.fu.tree",function(event,data){
	            	
	            });

	            $('#allTree').tree("discloseAll");
	            
	            var B_TEXT_TEMP = '<input type="checkbox" name="{0}" cname="{1}" ctype="{2}" cid="{3}"/>{1}';
	            
	            //构建左边树(楼栋/单元)
	            function buildCData(datas,p){
	            	//console.error(p);
	            	//console.error(datas);
	            	for(var i in datas){
	            		var d = datas[i];
	            		var chkId = d.id;
	            		var ctype = "b";
	            		if(p && p.id){
	            			chkId = p.id+"_"+d.id;
	            			ctype = "u";
	            		}
	            		d.chkId = chkId;
	            		var name = (d.buildingName)?d.buildingName:d.unitName;
	            		var text = B_TEXT_TEMP.format(d.chkId,name,ctype,d.id);
	                    d.text = text;
	                    d.type="item";
	                    d.attr = {hasChildren:false};
	                    d.attr.cssClass = "fuelux-tree-item";
						if(d.us && d.us.length >0){
							allUnits = allUnits.concat(d.us);
							d.type="folder";
		                    //d.attr.cssClass = ".tree-branch";
							d.attr.hasChildren = true;
		                    d.additionalParameters={"children":d.us};	
						}
	            	}
	            }
	            
	            function cDataSource(openedParentData, callback) {
	            	 //console.error(openedParentData);
	            	  var d = [];
	            	  var p = null;
	            	  if(openedParentData.id){
	            		 
	            		  if(openedParentData.us && openedParentData.us.length > 0){
	            			  d = openedParentData.us;
	            			  p = openedParentData;
	            		  }
	            	  }else{
	            		  d = cJson.bs;
	            	  }
	            	  buildCData(d,p);
	            	  callback({
	            	    data: d
	            	  });
	            }            
	            
	            var bTree = $('#bTree').tree({
	                dataSource: cDataSource,
	                multiSelect: false,
	                folderSelect: true
	            });

	            $('#bTree').tree("discloseAll");
	            
	            checkboxEvent();
	    });
	 	
	 	
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
		 				toggleCheckbox("allTree",name,ched);
		 				buildCiidObj(directCis,{id:id},"add");
		 				buildCiidObj(allCis,{id:id},"add");
		 			}else{
		 				var tempId ="";
		 				for(var i in nameArr){
		 					if(i > 0){
		 						tempId +="_";
		 					}
		 					tempId += nameArr[i];
		 					$("#allTree input[type='checkbox'][name='"+tempId+"']").attr("checked",false);
		 				}
		 				toggleCheckbox("allTree",id,ched);
		 				buildCiidObj(directCis,{id:id},"del");
		 				buildCiidObj(allCis,{id:id},"del");
		 			}
	 			}
	 		});
	 		
	 		$("#bTree input[type='checkbox']").click(function(){
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
		 				toggleCheckbox("bTree",name,ched);
		 			}else{
		 				//console.error("取消id="+id);
		 				var tempId ="";
		 				for(var i in nameArr){
		 					if(i > 0){
		 						tempId +="_";
		 					}
		 					tempId += nameArr[i];
		 					$("#bTree input[type='checkbox'][name='"+tempId+"']").attr("checked",false);
		 				}
		 				toggleCheckbox("bTree",id,ched);
		 				var unit = getUnit(id);
		 				for(var y in unit.cis){
		 					var ci = unit.cis[y];
		 					buildCiidObj(allCis,ci,"del");
		 				}
		 			}
		 			showCheckItem();
	 			}
	 		});
	 	}
	 	//直接选择的部位
	 	var directCis = [];
	 	var allUnits = [];
	 	var selectUnits = [];
	 	var allCis = [];
	 	function showCheckItem(){
	 		$("#allTree input[type='checkbox']").attr("checked",false);
	 		
	 		selectUnits = [];
	 		allCis = directCis.concat(allCis);
	 		$("#bTree  input[type='checkbox'][ctype='u']").each(function(i,o){
	 			//console.error(i);
	 			var $this = $(this);
	 			var uid = $this.attr("cid");
	 			var isChecked = $this.attr("checked")?true:false;
	 			//console.error(isChecked);
	 			for(var y in allUnits){
	 				var u = allUnits[y];
	 				if(u.id == uid){
	 					if(isChecked){
		 					allCis = allCis.concat(u.cis);
		 					selectUnits.push(u);
	 					}else{
	 						for(var i in u.cis){
			 					var ci = u.cis[i];
			 					buildCiidObj(allCis,ci,"del");
			 				}
	 					}
	 					showAllCis(allCis);
	 				}
	 			}
	 		});
	 	}
	 	
	 	function showAllCis(allCis){
	 		for(var i in allCis){
 				var ci = allCis[i];
 				var ciid = ci.id;
 				toggleCheckbox("allTree",ciid,true);
 			}
	 	}
	 	
	 	function getUnit(uid){
	 		for(var i in allUnits){
	 			var u = allUnits[i];
	 			if(u.id == uid){
	 				return u;
	 			}
	 		}
	 	}
	 	
	 	function toggleCheckbox(treeId,name,checked){
	 		//console.error(name+checked);
	 		$("#"+treeId+" input[type='checkbox'][name*='"+ name+"']").attr("checked",checked);
	 	}
	 	
	 	function buildCiidObj(arr,obj,type){
	 		if(arr.length > 0){
	 			var i = 0;
	 			for(;i < arr.length ; i ++){
	 				var ciObj = arr[i];
	 				if(obj.id == ciObj.id){
	 					break;
	 				}
	 			}
	 			if(type == "add"){
		 			if(i >= arr.length){
		 				arr.push(obj);
		 			}
	 			}else{
	 				if(i < arr.length){
	 					arr = arr.splice(i, 1);
	 					//buildCiidObj(arr,obj,type);
		 			}
	 			}
	 		}else if(type == "add"){
	 			arr.push(obj);
	 		}
	 	}