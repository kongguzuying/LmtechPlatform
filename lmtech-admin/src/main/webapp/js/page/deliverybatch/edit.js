
	$(function(){
		initDatepicker();

		entityJson = entityJson?entityJson:{bs:[]};
		buildBuilding(allBsJson);
		
		initSelectArr(entityJson.bs);
		
		showSelect();
		
		bandingSpanEven();
		
		bandingUserNameClick();
		
		buildDownloadBuilding(entityJson.bs);
	});
	
	function bandingUserNameClick(){
		$("#userNames").click(function(){
			var uids = $("#userIds").val();
			var userNames = $("#userNames").text();
			var url = URL_BASE + "/department/sellist.do";
			//console.error(url);
			var selUserModal = top.showModal({
		        url: url,
		        title: "选择用户",
		        opener: window,
		        width: 800,
		        height: 480,
		        okFunc: function () {
		        	var userObj = selUserModal.frame.getUser();
		        	//console.error(userObj);
		        	if(userObj){
			        	var uName = "";
			        	var uids = "";
			        	for(var uid in userObj){
			        		if(uids.length > 0){
			        			uids +=",";
			        			uName +=";";
			        		}
			        		uids +=uid;
			        		uName += userObj[uid];
			        	}
			        	if(uids){
			        		$("label[for='userIds']").hide();
			        	}
			        	$("#userIds").val(uids);
			        	$("#userNames").text(uName);
			        	top.hideModal();
		        	}else{
		        		top.alertMessage("请选择用户");
		        	}
		        }
		    });
		});
	}
	
	
	//绑定单元点击事件
	function bandingSpanEven(){		
		$(".BsContainer span").click(function(){
			$this = $(this);
			var isAll = $this.attr("id")=="allBsBtn"?true:false;
			var checked = $this.hasClass("btn-default");
			if(isAll){
				if(checked){
					$(".BsContainer span").removeClass("btn-default").addClass("btn-success");
				}else{
					$(".BsContainer span").removeClass("btn-success").addClass("btn-default");
				}
				$("#allBsContainer span").each(function(i,o){
					var buid = $(this).attr("id");
					updateSelect(buid,checked);
				});
			}else{
				if(checked){
					$this.removeClass("btn-default").addClass("btn-success");
				}else{
					$this.removeClass("btn-success").addClass("btn-default");
				}
				var len = $("#allBsContainer span.btn-success").length;
				var lenTemp = $("#allBsContainer span").length;
				if(lenTemp == len && checked){
					$("#allBsBtn").removeClass("btn-default").addClass("btn-success");
				}else{
					$("#allBsBtn").removeClass("btn-success").addClass("btn-default");
				}
				var buid = $this.attr("id");
				updateSelect(buid,checked);
			}
			showSelect();
		});
	}
	//选中的房间数组
	var SELECT_RIDS_ARR = [];
	var bsSpanHtml = '<span class="btn btn-default bsbtn" id="{0}" room-count="{4}" uid="{2}" {3}>{1}</span>';
	function buildBuilding(allBs){
		$("#allBsContainer").html("");
		for (var i in allBs) {
			var b = allBs[i];
			var us = b.us;
			var id = b.id;
			var name = b.buildingName;
			for (var y in us) {
				var u = us[y];
				//console.error(u);
				var uid = id +"_"+u.id;
				var uname =name + "_" + u.unitName;
				var roomCount = u.roomNos.length;
				var disable = (u.fs && u.fs.length > 0)?"":'disabled="disabled"';
				var html = bsSpanHtml.format(uid,uname,u.id,disable,roomCount);
				var $span = $(html);
				$span.data("allObj",u);
				$("#allBsContainer").append($span);
			}
		}
		$("#allBsContainer span").removeClass("btn-success").addClass("btn-default");
		
		//console.error(SELECT_RIDS_ARR);
	}
	
	//构建已下载房间的楼栋效果
	function buildDownloadBuilding(bs){
		for(var i = 0 ; i < bs.length ; i ++){
			var b = bs[i];
			for(var y = 0 ; y < b.us.length ; y ++){
				var u = b.us[y];
				//console.error(u);
				if(u.downloadRoomNos && u.downloadRoomNos.length > 0){
					var id = b.id + "_" + u.id;
					var $u = $("#"+id);
					$u.removeClass("btn-default").addClass("btn-success");
					var roomCount = $u.attr("room-count");
					if(u.downloadRoomNos.length == roomCount){
						$u.unbind("click");
						$u.attr("no-select","true");
					}
				}
			}
		}
	}
	
	//给用户选择界面调用
	function getUsers(){
		var uids = $("#userIds").val();
		var uNames = $("#userNames").text();
		return {userIds:uids,userNames:uNames};
	}
	
	function updateSelect(buid,isAdd,obj,allObj){
		//如果是选中
		if(isAdd){
			//如果不存在数据
			if(!isExsitSel(buid)){
				$span = $("#"+buid);
				var obj = $span.data("obj");
				//console.error(obj);
				var ridsObj;
				if(obj && obj.fs){
					ridsObj =  getRidsByU(obj,null);
				
				}
				if(!ridsObj || !ridsObj.rids){
					//console.error(obj);
					var allObj = $span.data("allObj");
					//console.error(allObj);
					var notAllowed = ridsObj?ridsObj.notAllowed:null;
					ridsObj =  getRidsByU(allObj,notAllowed);
				}
				//console.error(rids);
				SELECT_RIDS_ARR[buid] = ridsObj?ridsObj.rids:"";
			}
		}else{
			SELECT_RIDS_ARR[buid]="";
		}		
	}
	//根据单元获取房间id
	//注意：要去掉已经下载的
	function getRidsByU(u,notAllowed){
		var ridsObj = {"rids":""};
		ridsObj.notAllowed=notAllowed?notAllowed:[];
		var rids = "";
		for(var i in u.fs){
			var f = u.fs[i];
			if(f && f.rs){
				for(var y in f.rs){
					var r = f.rs[y];					
					if(r && !r.notAllowed){
						var flag = true;
						if(ridsObj.notAllowed.length > 0){
							//过滤掉已经下载的
							for(var x = 0 ; x <ridsObj.notAllowed.length ; x++ ){
								var tempId = ridsObj.notAllowed[x];
								if(tempId == r.id){
									flag = false;
									break;
								}
							}
						}
						if(flag){
							if(ridsObj.rids.length > 0){
								ridsObj.rids+=",";
							}
							ridsObj.rids+=r.id;
						}
					}else{
						ridsObj.notAllowed.push(r.id);
					}
				}
			}
		}
		return ridsObj;
	}
	
	//是否存在数据
	function isExsitSel(buid){
		var rids = SELECT_RIDS_ARR[buid];
		return rids?true:false;
	}
	//已经下载的房间id
	var downloadRoomNos = [];
	
	//初始化选择的数组
	function initSelectArr(selBs){
		for (var i in selBs) {
			var b = selBs[i];
			var us = b.us;
			var id = b.id;
			for (var y in us) {
				var u = us[y];
				//console.error(u);
				downloadRoomNos = downloadRoomNos.concat(u.downloadRoomNos);
				var uid =id + "_"+u.id;				
				$u = $("#"+uid);
				$u.data({"obj":u});
				var rids = u.roomNos.join(",");
				SELECT_RIDS_ARR[uid] = rids;
			}
		}
		//console.error(downloadRoomNos);
	}
	//显示选择的效果
	function showSelect(){
		
		$("#allBsContainer span[no-select!='true']").removeClass("btn-success").addClass("btn-default");
		var i = 0;
		for(var buid in SELECT_RIDS_ARR){
			
			$span = $("#"+buid);
			var text = $span.text();
			if(text.indexOf("(") > 0){
				text = text.substr(0,text.indexOf("("));
			}
			var ridArr = (SELECT_RIDS_ARR[buid].length > 0) ? SELECT_RIDS_ARR[buid].split(","):[];
			//console.error(ridArr);
			if(ridArr && ridArr.length > 0){
				$span.removeClass("btn-default").addClass("btn-success");
				$span.text(text + "("+ridArr.length+"个房间)");
			}
			i ++;
		}
		if(i > 0){
			$("label[for='buildingIds']").hide();
		}
		//console.error(SELECT_RIDS_ARR);
	}
	
	//根据id获取jquery对象(其他窗口用)
	function getJQObjById(id){
		return $("#"+id);
	}
	
	//用户id参数
	var userIds_P = "1000000";
	var bidsTemplate = "{0}_{1}_{2}";
	//选择房间
	function selectRoom(){
		var id = $("#id").val()?$("#id").val():"";
		var pid = $("#pid").val();
		var url = URL_BASE + "/deliverybatch/toSelRoom.do";
		//console.error(url);
		var modal = top.showModal({
	        url: url,
	        title: "筛选房间",
	        opener: window,
	        width: 800,
	        height: 480,
	        okFunc: function () {
	        	//console.error(modal.frame.SELECT_RIDS_ARR);
	        	//所有选择的单元
	        	SELECT_RIDS_ARR = $.extend([],modal.frame.SELECT_RIDS_ARR);
	        	//console.error(SELECT_RIDS_ARR);
	        	top.hideModal();
	        	showSelect();
	        }
	    });
	}
	//错误提示html
	var errorHtml = '<label for="{0}" class="error">{1}</label>';
	
	//验证是否选择楼栋
	function validateBu(){
		var len = $("#allBsContainer span.btn-success").length;
		//console.error(len);
		return (len > 0) ?true:false;
	}
	//获取选择的楼栋/单元/房间数据
	function getBurids(){
		var burids = "";
		for(var buid in SELECT_RIDS_ARR){
			var rids = SELECT_RIDS_ARR[buid];
			//删除已下载的房间
			for(var i = 0 ; i < downloadRoomNos.length ; i ++){
				var rid = downloadRoomNos[i];
				if(rids.indexOf(rid) > -1){
					rids = rids.replace(","+rid,"").replace(rid+",","").replace(rid,"");
				}
			}
			//console.error(rids);
			if(rids){
				//console.error(rids);					
				if(burids.length > 0){
					burids+=";";
				}
				burids +=buid + "_" + rids;				
			}
		}
		return burids;
	}
	var submitIndex = 0;
	function formSubmit(){		
		if(validateForm() && submitIndex == 0){
			submitIndex ++;
			$("label[for='buildingIds']").remove();
			$("label[for='userIds']").remove();
			var burids = getBurids();
			//console.error(burids);
//			return false;
			//console.error(burids);
			//去掉楼栋验证，有时候是添加人员信息
			/*if(!validateBu()
					|| !burids){
				$("#buildingIds").after(errorHtml.format("buildingIds","请选择楼栋房间"));
				return false;
			}*/
			var userIds = $("#userIds").val();
			if(!userIds){
				$("#userIds").after(errorHtml.format("userIds","请选择用户"));
				return false;
			}
			showLoading("正在保存批次,请稍等");
			var url = URL_BASE + "/deliverybatch/doEdit.do";
			var id = $("#id").val();
			var pid = $("#pid").val();
			var batchName = $("#batchName").val();
			var purpose = $('input:radio[name=purpose]:checked').val();
			var memo = $("#memo").val();
			var beginDate = $("#beginDate").val();
			var endDate = $("#endDate").val();
			var phone = $("#phone").val();
			var data = {"id":id,
					"projectId":pid,
					"batchName":batchName,
					"purpose":purpose,"memo":memo,
					"beginDate":beginDate + " 00:00:00","endDate":endDate + " 00:00:00",
					"phone":phone,
					"buildingIds":burids,"userIds":userIds};
			
			var opts = {url:url,data:JSON.stringify(data),success:function(res){
				hideLoading();
				if(res.success){
					var url = URL_BASE + "/deliverybatch/list.do?filter_EQS_projectId="+ pid;
					window.location.href = url;
				}else{
					top.alertMessage(res.message);
					submitIndex =  0;
				}
			},
			error:function(res){
				hideLoading();
				submitIndex =  0;
				top.alertMessage("系统发生错误,请联系管理员");
			},type:"POST"};
			top.requestData(opts);
		}else if(submitIndex > 0){
			top.alertMessage("正在提交数据，请稍等");
		}
	}
	function validateForm() {
	    $("form").validate({
			rules : {
				"batchName" : {
                	required:true,
                	maxlength:20
                },
				"buildingIds" : "required",
				"userIds" : "required",
				"beginDate" : "required",
				"endDate" : "required",
				"memo":{
					maxlength : 50
				}
			},
			messages : {
				"batchName" : {
                	required: "请输入批次名称",
                	maxlength: "最多只能输入20个字符"
                },
				"buildingIds" : "请选择楼栋",
				"userIds" : "请选择工程师",
				"beginDate" : "请选择开始时间",
				"endDate" : "请选择结束时间",
				"memo" : {
					maxlength : "最多只能输入50个字符"
				}
			}
		});
	    return $("form").valid();
	}
	
	function initDatepicker(){

	    var day = new Date();
		//console.error(day);
		day.setHours(0);
		day.setMinutes(0);
		day.setSeconds(0);
		//console.error(day);
		var strStartDate = day.Format("yyyy-MM-dd");
		//console.error(day);
		$('#beginDate').val(strStartDate);
		
		var checkin = $('#beginDate').datepicker({
	    	format: 'yyyy-mm-dd',
	        onRender: function(date) {
	        	//alert("test");
	            return date.valueOf() < now.valueOf() ? 'disabled' : '';
	        }
	    }).on('changeDate', function(ev) {
	    		//console.error(ev);
	    		var cd = ev.date.valueOf();
	    		if(cd < (day.valueOf()-10000)){
	    			//alert("开始日期必须大于等于当前日期");
	    			top.alertMessage("开始日期必须大于等于当前日期");
	    			checkin.setValue(day);
	    			return false;
	    		}
	            if (ev.date.valueOf() > checkout.date.valueOf()) {
	                var newDate = new Date(ev.date)
	                newDate.setDate(newDate.getDate() + 1);
	                checkout.setValue(newDate);
	            }
	            checkin.hide();
	            $('#endDate').focus();
	            
	        }).data('datepicker');
	    var checkout = $('#endDate').datepicker({
	    	format: 'yyyy-mm-dd',
	        onRender: function(date) {
	            return date.valueOf() <= checkin.date.valueOf() ? 'disabled' : '';
	        }
	    }).on('changeDate', function(ev) {
	    		var startDt = checkin.date.valueOf();
	    		//console.error(startDt);
	    		if(ev.date.valueOf() <= startDt){
	    			//alert("结束日期必须大于开始日期");
	    			top.alertMessage("结束日期必须大于开始日期");
	    			var newDate = new Date(startDt);
		            newDate.setDate(newDate.getDate() + 1);
		            checkout.setValue(newDate);
	    			return false;
	    		}
	            checkout.hide();
	        }).data('datepicker');
	}