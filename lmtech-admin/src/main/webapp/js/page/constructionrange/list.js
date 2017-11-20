
	 	jQuery(document).ready(function() {
	 		ajaxList();
	    });
	 	var liHtml = '<li id="{0}" sel="{2}">{1}</li>';

	 	//当前选中的施工单位
		var currentSelectContractor = null;
	 	//构建施工单位
	 	function buildContractor(cis){
	 		csJson = cis;
	 		var $ul = $("#ulContractor");
	 		$ul.html("");
	 		if(cis && cis.length){
	 			for(var i in cis){
	 				var c = cis[i];
	 				var lh = liHtml.format(c.id,c.fullName,false);
	 				$ul.append(lh);
	 				$li = $("#" + c.id);
	 				$li.data("obj",c);
	 			}
	 			//绑定click事件
	 			$ul.children("li").click(function(){
	 				$this = $(this);
	 				var isSelect = ($this.attr("sel") == "false");//($this.attr("selected"))?true:false;
	 				
	 				$ul.children("li").removeClass("click-bg");
	 				//如果是选中
	 				if(isSelect){
	 					$ul.find("span").addClass("hide");
	 					$this.children("span").removeClass("hide");
	 					$ul.children("li").attr("sel","false");
	 					currentSelectContractor = $this.data("obj");
	 					$this.attr("sel","true");
	 					$this.addClass("click-bg");
	 					showConstructionRange(currentSelectContractor);
	 				}else{
	 					$this.attr("sel","false");	 					
	 					$this.children("span").addClass("hide");
	 					currentSelectContractor = null;
	 					$("#divConstructionRangeContainer").html("请选择施工单位");
	 				}
	 				buildActions();
	 			});			
	 		}else{
	 			$ul.html("该项目还没有施工单位");
	 			$("#divConstructionRangeContainer").html("请选择施工单位");
	 		}
	 	}
	 	
	 	function buildActions(){
	 		$("#ulContractor").find(".tree-actions").addClass("hide");
	 		$select = $("li.click-bg");
           	var isAppend = $select.children(".tree-actions").length;
           	if(!isAppend){
           		var cid = $select.attr("id");
           		$select.append('<span class="tree-actions pull-right"><i class="fa fa-trash-o" onclick="rmovePc(\''+cid+'\')"></i></span>');
           		
           	}
            $select.find(".tree-actions").removeClass("hide");
	 	}
	 	//根据施工单位id、项目id移除两个之间的关系
	 	function rmovePc(cid){
	 		var pid = $("#pid").val();
	 		if(pid){
		 		var url = URL_BASE + "/constructionrange/removePc.do";
		 		var data ={cid:cid,pid:pid};
		 		$.post(url,data,function(res){
		 			if(res.success){
		 				var projectName = $("#projectName").val();
		 				window.location.href = URL_BASE + "/constructionrange/list.do?pid="+pid+"&projectName="+projectName;
		 			}else{
		 				top.alertMessage(res.message);
		 			}
		 		});
	 			
	 		}else{
	 			top.alertMessage("请选择项目");
	 		} 		
	 		
	 	}
	 	
	 	
	 	var $crTabel = $('<table class="table"><thead><tr><th>楼栋/单元</th><th>施工范围</th><th>操作</th></tr>'
	 	        +'</thead><tbody></tbody></table>');
	 	function createTable(crs){
	 		$crTabel.children("tbody").html("");
	 		var html = "";
            for(var i in crs){
            	var cr = crs[i];
            	html +=createTr(cr);
            }
	 		$crTabel.children("tbody").html(html);
	 	}
	 	function createTr(cr){
	 		var tr = "";
	 		tr +='<tr id="{3}{4}{5}">';
        	tr +='<td>{0}/{1}</td>';
        	tr +='<td>{2}</td>';
        	tr +='<td><a href="javascript:updateCr(\'{3}\',\'{4}\',\'{5}\',\'{6}\',\'{0}/{1}\')">修改</a><a href="javascript:removeCr(\'{3}\',\'{4}\',\'{5}\')">删除</a></td>';
        	tr +='</tr>';
        	tr = tr.format(cr.buildingName,cr.unitName,cr.checkItemName,cr.contractorId,cr.buildingId,cr.unitId,cr.checkItemId);
        	return tr;
	 	}
	 	//显示施工范围
	 	function showConstructionRange(c){
	 		var $crc = $("#divConstructionRangeContainer");
	 		if(c && c.crs.length > 0){
	 			createTable(c.crs);
	 			$crc.html("");
	 			$crc.append($crTabel);
	 		}else{
	 			$crc.html("暂无设置施工范围");
	 		}
	 	}
	 	//修改施工范围
	 	function updateCr(cid,bid,uid,ciid,title){
	 		var pid = $("#pid").val();
	 		var url = URL_BASE + "/constructionrange/toEdit.do?pid="+pid
	 				+"&cid="+ cid
	 				+ "&bid=" +bid 
	 				+ "&uid=" + uid
	 				+ "&ciid=" + ciid;
            title = "设置["+title + "]施工范围";
            var trId = "{0}{1}{2}";
            trId = trId.format(cid,bid,uid);
            //console.error("trId = " + trId);
	 		var modal = top.showModal({
                url: url,
                title: title,
                opener: window,
                width: 600,
                height: 550 ,
                okFunc: function () {
                	var ciObjs = modal.frame.ciObjs;
                	//console.error(ciObjs);
                	var cnames = "";
                	var ciObj = buildCheckItemId(ciObjs,cnames);
                	var cbuid = top.getModalFrameContent().find("input[type='hidden']").serialize();
                	//console.error(cbuid);
                	var url = URL_BASE + "/constructionrange/doEdit.do";
                	var data = cbuid+"&checkItemId=" + ciObj.ciid;
                	//console.error("data=" + data);
					$.post(url,data,function(res){
						if(res.success){
							top.hideModal();
							updateCurrentCrObj(trId,ciObjs,ciObj);
						}else{
							top.alertMessage(res.message);
						}
					});
                }
            });
	 	}
	 	
	 	//删除施工范围
	 	function removeCr(cid,bid,uid){
	 		top.confirmMessage("确定删除吗？",function(res){
	 			//console.error(res);
	 			if(res){
	 				var pid = $("#pid").val();
		 			var url = URL_BASE + "/constructionrange/doRemove.do";
		            var data = {projectId:pid,contractorId:cid, buildingId:bid, unitId:uid};
					$.post(url,data,function(res){
						if(res.success){
							var trId = "{0}{1}{2}";
							trId = trId.format(cid,bid,uid);
							updateCurrentCrObj(trId,[],{});
						}else{
							top.alertMessage(res.message);
						}
					});
	 			}
	 		});
	 	}
	 	
	 	function buildCheckItemId(ciObjs){
	 		var ciids = "";
	 		var cname = "";
	 		for(var i in ciObjs){
	 			if(ciids.length > 0){
	 				ciids +=",";
	 				cname +=";";
	 			}
	 			var ciObj = ciObjs[i];
	 			ciids +=ciObj.id;
	 			cname +=ciObj.cname;
	 		}
	 		return {ciid:ciids,cname:cname};
	 	}
	 	//修改当前选中的施工单位
	 	function updateCurrentCrObj(unionId,ciObjs,ciObj){
	 		var isDel = ciObjs.length?false:true;
	 		var cr = null;
	 		for(var i = 0; i < currentSelectContractor.crs.length ; i ++){
	 			cr = currentSelectContractor.crs[i];
	 			var uid = "{0}{1}{2}";
	 			uid = uid.format(cr.contractorId,cr.buildingId,cr.unitId);
	 			if(uid == unionId){
	 				break;
	 			}
	 		}
	 		if(i < currentSelectContractor.crs.length){
	 			if(isDel){
	 				currentSelectContractor.crs.splice(i,1);
	 			}else{
	 				cr.checkItemName = ciObj.cname;
	 				cr.checkItemId = ciObj.ciid;
	 			}
	 			showConstructionRange(currentSelectContractor);
	 		}
	 	}
	 	//设置范围
	 	function setCr(){
	 		if(currentSelectContractor && currentSelectContractor.id){
	 			var pid = $("#pid").val();
	 			//console.error(pid);
		 		var cid = currentSelectContractor.id;
		 		var url = URL_BASE + "/constructionrange/toSetcr.do?pid="+pid+"&cid="+ cid;
			    title = "设置施工范围";
			    //console.error("trId = " + trId);
					var modal = top.showModal({
			        url: url,
			        title: title,
			        opener: window,
			        width: 700,
			        height: 550  ,
			        okFunc: function () {
			        	//所有选择的单元
			        	var selectUnits = modal.frame.selectUnits;
			        	//console.error(selectUnits);
			        	if(!selectUnits || selectUnits.length == 0){
			        		top.alertMessage("请选择楼栋");
			        		return;		
			        	}
			        	var uid = buildUnits(selectUnits);
			        	
			        	//所有选择的部位
			        	var allCis = modal.frame.allCis;
			        	if(!allCis || allCis.length == 0){
			        		top.alertMessage("请选择部位");
			        		return;		
			        	}
			        	var checkItemId = buildCIID(allCis);
			        	
	                	var contractorId = top.getModalFrameContent().find("#contractorId").val();			        	
			        	var url = URL_BASE + "/constructionrange/doSetcr.do";
			        	var data = {projectId:pid,contractorId:contractorId,unitId : uid,checkItemId:checkItemId};
			        	//console.error("data=" + data);
			        	var projectName = $("#projectName").val();
						$.post(url,data,function(res){
							//console.error(res);
							if(res.success){
								top.hideModal();
								//updateCurrentCrObj(trId,ciObjs,ciObj);
								window.location.href = URL_BASE + "/constructionrange/list.do?pid="+pid+"&projectName="+projectName;
							}else{
								top.alertMessage(res.message);
							}
						});
			        }
			    });
			}else{
				top.alertMessage("请选择施工单位");
			}
	 	}
	 	
	 	function buildCIID(cis){
	 		//console.error(cis);
	 		var ciid = "";
	 		for(var i in cis){
	 			var id = cis[i].id;
	 			if(ciid.indexOf(id) < 0){
		 			if(ciid.length > 0){
		 				ciid+=",";
		 			}
		 			ciid +=id;
	 			}
	 		}
	 		//console.error(ciid);
	 		return ciid;
	 	}
	 	function buildUnits(us){
	 		var uid = "";
	 		for(var i in us){
	 			var u =  us[i];
	 			if(uid.length > 0){
	 				uid+=",";
	 			}
	 			uid +=u.buildingId+"_"+u.id;
	 		}
	 		return uid;
	 	}
	 	function ajaxList(pid){
	 		if(!pid){
	 			pid = $("#pid").val();
	 		}
	 		 var url = URL_BASE + "/constructionrange/ajaxList.do";
             var data = {pid:pid};
             $.post(url,data,function(res){
             	buildContractor(res);
             	$("#pid").val(pid);
             });
	 	}
	 	
	 	 $(function () {
	            initAutoComplete({
	                url: URL_BASE + "/project/projectphaselist.do",
	                selectId: "projectName",
	                onSelected: function (id, text) {
	                    var pids = id.split("|");
	                    var pid = pids.length > 1?pids[1]:pids[0];
	                    //console.error(pid);
	                   ajaxList(pid);
	                }
	            });
	            //引入施工单位
	            $("#btnImport").click(function(){
	            	var pid = $("#pid").val();
	            	if(pid){
	            		var url = URL_BASE + "/constructionrange/toImport.do?pid=" + pid;
	                   
		            	var importModal = top.showModal({
					        url: url,
					        title: "引入施工单位",
					        opener: window,
					        width: 700,
					        height: 550  ,
					        okFunc: function () {
					        	var cids = importModal.frame.cids;
					        	if(!cids || cids.length == 0){
					        		top.alertMessage("请选择施工单位");
					        		return;
					        	}
					        	var pid = $("#pid").val();
			                	var url = URL_BASE + "/constructionrange/doImport.do";
			                	var data = {pid:pid,cid:cids};
			                	//console.error(data);
								$.post(url,data,function(res){
									if(res.success){
										top.hideModal();
										csJson = csJson.concat(res.data);
										//console.error(res);
										buildContractor(csJson);
									}else{
										top.alertMessage(res.message);
									}
								});
					        }
					    });
	            	}else{
	            		top.alertMessage("请先选择项目");
	            	}
	            });
	            //添加施工单位并与项目建立关系
	            $("#btnAddContractor").click(function(){
	            	var pid = $("#pid").val();
	            	if(pid){
	            		var url = URL_BASE + "/constructionrange/toAddContractor.do?pid=" + pid;
		                   
		            	var importModal = top.showModal({
					        url: url,
					        title: "添加施工单位",
					        opener: window,
					        width: 500,
					        height: 400  ,
					        okFunc: function () {
					        	if(!importModal.frame.validateForm()){
					        		//top.alertMessage("");
					        		return;
					        	}
					        	var pid = $("#pid").val();
			                	var url = URL_BASE + "/constructionrange/doAddContractor.do";
			                	//var data = {pid:pid,cid:cids};

			                    var data = top.getModalFrameContent().find("form").serialize();
			                	//console.error(data);
								$.post(url,data,function(res){
									if(res.success){
										top.hideModal();
										csJson = csJson.concat(res.data);
										//console.error(res);
										buildContractor(csJson);
									}else{
										top.alertMessage(res.message);
									}
								});
					        }
					    });
	            	}else{
	            		top.alertMessage("请先选择项目");
	            	}
	            });
	        });