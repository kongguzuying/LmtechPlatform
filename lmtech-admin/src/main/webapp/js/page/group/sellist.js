	//主frame窗口
	var parent = window.parent?window.parent.document.mainFrame:window.top.document.frames.mainFrame;

	//父窗口必须提供getUser方法 ,返回json格式：{userIds:uids,userNames:uNames}
	var users = parent.getUsers();
	var $tableContainer;
	var trHtml;
	var uids = users.userIds;
	var userNames = users.userNames;
	var spanHtml = '<div uid="{0}" class="margen10"><span>{1}</span><a href="javascript: executeUser(\'{0}\',\'{1}\',false);" class="fa fa-times" uid="{0}"></a></div>';
 	
function buildData(datas){
     for(var i in datas){
   		  var d = datas[i];
          d.text = d.name;
          d.type = "item";
          if(d.hasChildren){
            d.attr = {hasChildren : true};
			d.type="folder";
          }
     }
}
var ajaxUrl = URL_BASE + "/platform/group/ajaxChildren.do?parentId={0}"
function staticDataSource(parentData, callback) {
	//console.error(parentData);
	var parentId = "";
	if(parentData && parentData.id){
		parentId = parentData.id;
	}
	var url = ajaxUrl.format(parentId);
	
	  $.post(url,{},function(res){
		  buildData(res);
	 	  callback({
	 	    data: res
	 	  });
	 });	 
}
	//选择的用户
	var selectUserObj = {};
	 	jQuery(document).ready(function() {
	            
	            var tree = $('#departmentTree').tree({
	                dataSource: staticDataSource,
	                multiSelect: false,
	                folderSelect: true
	            });
	            
	            
	            $('#departmentTree').on("selected.fu.tree",function(event,data){
	            	findUser(data);
	            });
	            $('#departmentTree').on("deselected.fu.tree",function(event,data){
	            	$("#userInfoContainer").html("请选择组织机构");
	            });
	            
	            $tableContainer = $("#tableContainer");
	            trHtml = $tableContainer.find("tbody").html();
	            
	            initSelectUser();
	            
	            userInitAutoComplete({
	                url: URL_BASE + "/user/getByKey.do",
	                selectId: "searchKey",
	                minLength:1,
	                onSelected: function (user) {
	                    executeUser(user.id,user.name,true);
	                    showSelectDiv();
	                }
	            });
	            
	    });
	 	
	 	function initSelectUser(){
	 		if(uids){
	 			var uidArr = uids.split(",");
	 			var uNameArr = userNames.split(";");
	 			for(var i = 0 ; i < uidArr.length ; i ++){
	 				var uid = uidArr[i];
	 				var uname = uNameArr[i];
	 				selectUserObj[uid] = uname;
	 			}
	 			showSelectDiv();
	 		}
	 	}
	 	//操作公共用户数据[selectUserObj]
	 	function executeUser(uid,uname,isAdd){
	 		if(isAdd){
	 			selectUserObj[uid] = uname;
	 			$("#"+uid).attr("checked","checked");
	 		}else{	 			
	 			delete selectUserObj[uid];
	 			$("div[uid='"+uid+"']").remove();
	 			$("#"+uid).attr("checked",false);
	 		}
	 		showChkAll();
	 	}
	 	
	 	//后台查找用户信息
	 	function findUser(data){
	 		var dObj = data.target;
	 		if(dObj && dObj.id){
	 			//执行用户查询
	 			var url = URL_BASE + "/user/getByDid.do?did=" + dObj.id;
	 			$.post(url,{},function(res){
	 				if(res && res.length){
	 					var tbody = "";
	 					for(var i = 0; i < res.length ; i ++){
	 						var d = res[i];	
	 						tbody +=trHtml.format((i+1),d.name,d.loginName,d.id,d.name);
	 					}
	 					$tableContainer.find("tbody").html(tbody);
	 					$("#userInfoContainer").html($tableContainer.html());
	 					bandingChkEvent();
	 					showSelectChk();
	 				}else{
	 					$("#userInfoContainer").html("没有找到用户数据");
	 				}
	 			});
	 		}
	 	}
	 	//checkbox显示选中的
	 	function showSelectChk(){	 		
	 		for(var uid in selectUserObj){
	 			$("#"+uid).attr("checked","checked");
	 		}
	 		showChkAll();
	 	}
	 	//显示全选复选框
	 	function showChkAll(){
	 		var len = $("#userInfoContainer input[id!='chkAll']:checkbox").length;
			var tempLen = $("#userInfoContainer input[id!='chkAll']:checkbox:checked").length;
			if(len == tempLen){
				$("#chkAll").attr("checked","checked");
			}else{
				$("#chkAll").attr("checked",false);
			}
	 	}
	 	
	 	//div显示选中的
	 	function showSelectDiv(){
	 		//console.error(selectUserObj);
	 		$("#selectUserContainer").html("");
	 		var html = "";
	 		var userSize = 0;
	 		for(var uid in selectUserObj){
		 		html += spanHtml.format(uid,selectUserObj[uid]);
		 		userSize ++;
	 		}
	 		$("#userCount").text("("+userSize+"人)");
	 		$("#selectUserContainer").append(html);
	 	}
	 	//获取用户(给调用窗口用)
	 	function getUser(){
	 		return selectUserObj;
	 	}
	 	//绑定复选框单击事件
	 	function bandingChkEvent(){
	 		$("#userInfoContainer input:checkbox").click(function(){
	 			var uname = $(this).attr("uname");
	 			var checked = $(this).attr("checked")?true:false;
	 			if(uname){//单选
	 				var uid = $(this).attr("id");
	 				executeUser(uid, uname, checked);
	 				var len = $("#userInfoContainer input[id!='chkAll']:checkbox").length;
	 				var tempLen = $("#userInfoContainer input[id!='chkAll']:checkbox:checked").length;
	 				if(!checked){
	 					$("#chkAll").attr("checked",false);
	 				}else if(len == tempLen && checked){
	 					$("#chkAll").attr("checked","checked");
	 				}
	 				
	 			}else{//全选
	 				$("#userInfoContainer input:checkbox").attr("checked",checked).each(function(i,o){
	 					var un = $(this).attr("uname");
	 					if(un){
		 					//var uname = $(this).attr("uname");
		 					var uid = $(this).attr("id");
		 					executeUser(uid, un, checked);
	 					}
	 				});
	 			}
	 			showSelectDiv();
	 		});
	 	}