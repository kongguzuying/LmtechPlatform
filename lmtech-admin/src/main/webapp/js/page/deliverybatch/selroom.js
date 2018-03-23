	//主frame窗口
	var parent = window.parent?window.parent.document.mainFrame:window.top.document.frames.mainFrame;

	//选中的房间数组
	var SELECT_RIDS_ARR = [];
	//实体json对象
	var entityJson;
	
	//所有当前项目下的楼栋信息
	var allBsJson = [];
	//选择的楼栋/单元
	var selU = null;
	var downloadRoomNos;
	$(function(){
		allBsJson = parent.allBsJson;
		downloadRoomNos = parent.downloadRoomNos
		SELECT_RIDS_ARR = $.extend([],parent.SELECT_RIDS_ARR);
		
		//实体
		entityJson = $.extend({},parent.entityJson);
		
		//构建楼栋/单元
		buildBu(allBsJson);
				
		//绑定楼栋/单元点击事件
		bandingBuEvent();
		//绑定checkbox点击事件
		bandingChkEvent();
		
		showSelectRoomCount();
	})
	
	//当前楼栋的cid
	var CURRENT_BU_CID = "";
	
	var trHtml = '<tr><td>{0}</td><td>{1}</td></tr>';
	var chkHtml = '<input class="{5}" type="checkbox" cid="{0}" burid="{6}" ctype="{2}" id="{3}" {4}>{1}';
	var buHtml = '<div cid="{0}">&nbsp;{1}</div>';
	function buildBu(allBs){
		for (var i in allBs) {
			var b = allBs[i];
			var us = b.us;
			var id = b.id;
			var name = b.buildingName;
			//构建楼栋/单元
			for (var y in us) {
				var u = us[y];
				if(!selU){
					selU =  u;					
				}
				var uid = id +"_"+u.id;
				var uname =name + "_" + u.unitName;
				var bu_html = buHtml.format(uid,uname);
				$div = $(bu_html);
				$div.data("allU",u);
				$("#tdBs").append($div);
			}
		}
		var cid = selU.buildingId + "_" + selU.id;
		$("#tdBs").find("div[cid='"+cid+"']").addClass("click-bg");
		//console.error(selU);
		buildFloorAndRoom(selU);
		var sU = getSelectU(cid,entityJson.bs);
		
		buildNotAllowRoom(sU);
		
		buildSelectRoom(cid);
		CURRENT_BU_CID = cid;
		initSelectEffect(cid);
	}
	
	function getSelectU(buid,bs){
		if(!bs) return null;
		for (var i in bs) {
			var b = bs[i];
			var us = b.us;
			var bid = b.id;
			//楼栋/单元
			for (var y in us) {
				var u = us[y];
				var uid = bid +"_"+u.id;
				if(uid == buid){
					return u;
				}
			}
		}
	}
	
	//构建不可用房间
	function buildNotAllowRoom(u){
		if(!u) return;
		if(u.fs && u.fs.length > 0){
			for(var x in u.fs){
				var f = u.fs[x];
				//构建房间
				for(var z in f.rs){
					var r = f.rs[z];
					//是否不可以点击
					if(r.notAllowed){
						//$("input[type='checkbox'][cid='"+id+"']").attr({"disabled":"disabled","checked":"checked","notAllowed":"notAllowed"}).addClass("not-allowed");
						$("#"+r.id).attr({"disabled":"disabled","checked":"checked","notAllowed":"notAllowed"}).addClass("not-allowed");
					}
				}
			}
		}
	}
	
	//构建初始的楼层和房间
	function buildFloorAndRoom(u){
		$("#roomTable").html("");
		//楼层id
		var bid = u.buildingId;
		//单元id
		var uid = u.id;
		var f_tr_html = "<tr><td>当前楼栋没有房间数据</td></tr>";
		if(u.fs && u.fs.length > 0){
			f_tr_html = "";
			for(var x in u.fs){
				var f = u.fs[x];
				var buid = bid + "_" +uid;
				var fid = buid +"_" + f.id;
				var f_check_html = chkHtml.format(fid,f.name,"f","");
				var room_html = "";
				//console.error(f);
				//构建房间
				for(var z in f.rs){
					var r = f.rs[z];
					//console.error(r);
					var chke = "";
					if(r["isChk"]){
						chke = 'checked="checked"';
					}
					var cls = "";
					var notAllowed = '';
					var disa = '';
					//是否不可以点击
					if(r.notAllowed){
						cls = "not-allowed";
						notAllowed = 'notAllowed="notAllowed"';
						disa = 'disabled="disabled"';
						chke = 'checked="checked"';
					}
					
					var rname = r.roomName;
					var rid = fid + "_" + r.id;
					var burid = buid + "_" + r.id;
					var allText = chke + " " + notAllowed + " " + disa;
					room_html +=chkHtml.format(rid,rname,"r",r.id,allText,cls,burid);
				}
				f_tr_html += trHtml.format(f_check_html ,room_html);				
			}
		}
		$("#roomTable").html(f_tr_html);
	}
	
	//构建选择的房间(根据父窗口选中的数据)
	function buildSelectRoom(cid){
		//console.error(cid);
		var rids = SELECT_RIDS_ARR[cid];		
		//console.error(rids);
		if(!rids) return;
		var ridArr = rids.split(",");
		for(var i in ridArr){
			var rid = ridArr[i];
			var burid = cid + "_" + rid;
			$("input[type='checkbox'][burid='"+burid+"']").attr("checked",true);
		}
	}
	/**
	 * 获取已选择房间总数
	 */
	function showSelectRoomCount(){
		//console.error(SELECT_RIDS_ARR);
		var size = 0;
		for (var key in SELECT_RIDS_ARR) {
			//console.error(key);
			var val = SELECT_RIDS_ARR[key];
			if(val && $.trim(val)){
				size +=  val.split(",").length;
			}			
		}
		$("#roomCount").html("已选"+size+"户");
		//return size;
	}
	//获取父窗体的单元
	function getParentUnitById(id){
		$jq = parent.getJQObjById(id);
		var u = $jq.data("tempObj");
		return u;
	}
	
	//绑定楼栋点击事件
	function bandingBuEvent(){
		$("#tdBs div").click(function(){
			$("#tdBs div").removeClass("click-bg");
			$this = $(this);
			$this.addClass("click-bg");
			var cid = $this.attr("cid");
			//console.error($this.data("u"));
			var allU = $this.data("allU");
			buildFloorAndRoom(allU);
			
			var sU = getSelectU(cid,entityJson.bs);
			buildNotAllowRoom(sU);
			//console.error(chkU);
			buildSelectRoom(cid);
			bandingChkEvent();
			CURRENT_BU_CID = cid;
			initSelectEffect(cid);
		});
	}
	//绑定所有checkbox click事件
	function bandingChkEvent(){
		$("#allChk").attr("checked",false);
		var $allChk = $("#allTable").find("input[type='checkbox'][disabled!='disabled']");
		$allChk.click(function(){
			$this = $(this);
			var checked = $this.attr("checked")?true:false;
			var isAll = $this.attr("id")=="allChk"?true:false;
			
			if(isAll){
				/*$allChk.attr("checked",checked)
				.each(function(i,o){
					//var id = $(this).attr("id");
					var ctype = $(this).attr("ctype");
					if("r" == ctype){
						var unionId = $(this).attr("cid");
						var uArr = unionId.split("_");
						var buid = uArr[0]+"_"+uArr[1];
						var rid = uArr[3];
						updateSelectArr(buid,rid,checked);
					}
					
				});*/
				$("input[type='checkbox'][disabled!='disabled'][cid^='"+CURRENT_BU_CID+"']")
				.attr("checked",checked).each(function(i,o){
					//var id = $(this).attr("id");
					var ctype = $(this).attr("ctype");
					if("r" == ctype){
						var unionId = $(this).attr("cid");
						var uArr = unionId.split("_");
						var buid = uArr[0]+"_"+uArr[1];
						var rid = uArr[3];
						updateSelectArr(buid,rid,checked);
					}
					
				});
				
			}else{
				var cid = $this.attr("cid");
				var c_type = $this.attr("ctype");
//				console.error(c_type);
				//解决楼层选择时,发生楼层为1的时候,楼层前面为1的都会被选择或者取消
				if(c_type=="f"){
					cid+="_";
				}
				$("#allTable")
				.find("input[type='checkbox'][disabled!='disabled'][cid^='"+cid+"']")
				.attr("checked",checked)
				.each(function(i,o){
					var unionId = $(this).attr("cid");
					var ctype = $(this).attr("ctype");
					if("r" == ctype){
						var unionId = $(this).attr("cid");
						var uArr = unionId.split("_");
						var buid = uArr[0]+"_"+uArr[1];
						var rid = uArr[3];
						updateSelectArr(buid,rid,checked);
					}
				});
			}
			showSelectRoomCount();
			showSelectEffect($this,checked);
			//console.error(SELECT_RIDS_ARR);
		});
	}
	//显示选中效果
	function showSelectEffect($this,checked){
		var ctype = $this.attr("ctype");
		//如果是房间
		if("r" == ctype){
			var cid = $this.attr("cid");
			//console.error(cid);
			cid = cid.substring(0,cid.lastIndexOf("_"));
//			console.error(cid);
			if(checked){
				var t_c = $("input[type='checkbox'][disabled!='disabled'][cid^='"+cid+"_']").length;
				var s_c = $("input[type='checkbox'][disabled!='disabled'][cid^='"+cid+"_'][checked='checked']").length;
				if(t_c == s_c){
					$("input[type='checkbox'][disabled!='disabled'][cid='"+cid+"']").attr("checked",checked);
				}
			}else{
				$("input[type='checkbox'][disabled!='disabled'][cid='"+cid+"']").attr("checked",checked);				
			}
		}
		if(checked){
			var t_c = $("input[type='checkbox'][disabled!='disabled'][cid^='"+CURRENT_BU_CID+"']").length;
			var s_c = $("input[type='checkbox'][disabled!='disabled'][cid^='"+CURRENT_BU_CID+"'][checked='checked']").length;
			if(t_c == s_c){
				$("#allChk").attr("checked",true);
			}
		}else{
			$("#allChk").attr("checked",false);
		}
	}
	
	//初始化选中效果
	function initSelectEffect(cid){
		var fSize = 0 ;
		//处理楼层选中效果
		$("input[type='checkbox'][ctype='f'][cid^='"+cid+"_']").each(function(i,o){
			var fcid = $(this).attr("cid");
			var t_c = $("input[type='checkbox'][disabled!='disabled'][cid^='"+fcid+"_']").length;
			var s_c = $("input[type='checkbox'][disabled!='disabled'][cid^='"+fcid+"_'][checked='checked']").length;
			if(t_c == s_c){
				$(this).attr("checked",true);
			}
			fSize ++;
		});		
		//处理id=allChk选中效果
		if(fSize > 0){
			var t_c = $("input[type='checkbox'][ctype='f'][cid^='"+cid+"_']").length;
			var s_c = $("input[type='checkbox'][ctype='f'][cid^='"+cid+"_'][checked='checked']").length;
			if(t_c == s_c){
				$("#allChk").attr("checked",true);
			}
		}		
	}
	
	//修改选择的数组
	function updateSelectArr(buid,rid,isAdd){
		var rids = SELECT_RIDS_ARR[buid];
		if(!rids){rids = "";}
		if(isAdd){
			if(rids.indexOf(rid) < 0){
				if(rids.length > 0){
					rids +=",";
				}
				rids+=rid;
			}
		}else{
			rids = rids.replace(rid+",","").replace(","+rid,"").replace(rid,"");
		}
		SELECT_RIDS_ARR[buid] = rids;
	}
	
	//根据单元,将下面的room全选
	/*function buildIsChkByU(u){
		if(u.fs && u.fs.length > 0){
			for(var x in u.fs){
				var f = u.fs[x];
				for(var z in f.rs){
					var r = f.rs[z];
					r.isChk = true;
				}
			}
		}
	}*/