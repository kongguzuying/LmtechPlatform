	var $tableContainer;
	var trHtml;
	var requestIndex = 0;
 	function buildData(datas){
       	for(var i in datas){
       		var d = datas[i];
               d.text = d.name;
               d.type="item";
               d.attr = {id:d.id};
               if(d.hasChildren){
                d.attr.hasChildren = true;
				d.type="folder";
            }
       	}
     }         
      var ajaxUrl = URL_BASE + "/platform/group/ajaxChildren.do?parentId={0}&requestIndex={1}";
      function staticDataSource(parentData, callback) {
    	var parentId="";
      	if(parentData && parentData.id){
      		parentId = parentData.id;
      	}
      	var url = ajaxUrl.format(parentId,requestIndex);
      	
      	  $.post(url,{},function(res){
      		  buildData(res);
      		  requestIndex ++;
	      	  callback({
	      	    data: res
	      	  });
      	  });
      	 
      }
	 	jQuery(document).ready(function() {
	            var tree = $('#departmentTree').tree({
	                dataSource: staticDataSource,
	                multiSelect: false,
	                folderSelect: true
	            });
	            
	            
	            $('#departmentTree').on("selected.fu.tree",function(event,data){
	            	var isRoot = false;
	            	if(!data.target.parentId || data.target.parentId == ""){
	            		isRoot = true;
	            	}
	            	buildActions(isRoot);
	            	findUser(data.target);
	            	DEPARTMENT_CURRENT = data.target;
	            });
	            
	            $('#departmentTree').on("deselected.fu.tree",function(event,data){
	            	DEPARTMENT_CURRENT = null;
	            	$("#userInfoContainer").html("请选择组织机构");
	            	$("li[role='treeitem']").find(".tree-actions").addClass("hide");	            	
	            });
	            
	            $tableContainer = $("#tableContainer");
	            trHtml = $tableContainer.find("tbody").html();
	            
	            //initUserSearche();
	            userInitAutoComplete({
	                //url: URL_BASE + "/project/projectphaselist.do",
	                url: URL_BASE + "/user/getByKey.do",
	                selectId: "userSearchKey",
	                minLength:1,
	                onSelected: function (user) {
	                    //console.error(id);
	                    //console.error(username);
	                   /* executeUser(id,username,true);
	                    showSelectDiv();*/
	                    //console.error(user);
	                    var us = [];
	                    us.push(user);
	                    buildUserHtml(us);
	                    //$("#searchKey").focus();
	                }
	            });
	                       
	    });
	 	
	 	//初始化用户搜索框
	 	/*function initUserSearche(){
	 		$("#userSearchKey").keyup(function(){
	 			if(DEPARTMENT_CURRENT && DEPARTMENT_CURRENT.id){
		 			var key = $(this).val();
		 			var data = {id:DEPARTMENT_CURRENT.id,key:key};
		 			findUser(data);
	 			}
	 		});
	 	}*/
	 	
	 	
	 	//后台查找用户信息
	 	function findUser(data,cb){
	 		if(data && data.id){
	 			var key = data.key?data.key:"";
	 			//key = encodeURI(key);
	 			//执行用户查询
	 			var url = URL_BASE + "/platform/user/getByDid.do?did=" + data.id;
	 			$.post(url,{key:key},function(res){
	 				buildUserHtml(res);
	 				if(cb && $.isFunction(cb)){
 						cb(res);
 					}
	 			});
	 		}
	 	}
	 	
	 	function buildUserHtml(res){
	 		if(res && res.length){
					var tbody = "";
					for(var i = 0; i < res.length ; i ++){
						var d = res[i];	
						var disable = "已启用";
						var disableExe = "禁用";
						if(!d.enable){
 						disable = "已禁用";
 						disableExe = "启用";
						}
						//console.error(d);
						tbody +=trHtml.format((i+1),d.name,d.loginName,d.id,d.flag,disable,disableExe);
					}
					$tableContainer.find("tbody").html(tbody);
					$("#userInfoContainer").html($tableContainer.html());
					
			}else{
				$("#userInfoContainer").html("没有找到用户数据");
			}
	 	}
	 	
	 	
	 	/****/
	 	//构建操作按钮
	 	function buildActions(isRoot){
	 		$("li[role='treeitem']").find(".tree-actions").addClass("hide");
	 		$select = $("li.tree-selected");
	 		$select = $select.children("div.tree-branch-header").length > 0?$select.children("div.tree-branch-header"):$select;
           	var isAppend = $select.children(".tree-actions").length;
           	if(!isAppend){
           		var html = '<span class="tree-actions" z-index="1000"><i class="fa fa-plus" title="添加子部门" onclick="addDepartment()"></i><i class="fa fa-pencil" title="修改" onclick="updateDepartment()"></i>{0}</span>';
           		if(!isRoot){
           			html = html.format('<i class="fa fa-trash-o" title="删除" onclick="removeDepartment()"></i><i class="fa fa-hand-o-right" title="设置角色" onclick="setRole()"></i>');
           		}else{
           			html = html.format('');
           		}
           		$select.append(html);
           		
           	}
            $select.find(".tree-actions").removeClass("hide");
	 	}
	 	//当前选中的部门
	 	var DEPARTMENT_CURRENT;
	 	//添加部门
	 	function addDepartment(){
	 		var id = DEPARTMENT_CURRENT.id;	 		
 			var url = URL_BASE + "/platform/group/toEdit.do?id=&parentId=" + id;
 			var addModal = top.showModal({
                url: url,
                title: "添加部门",
                opener: window,
                width: 500,
                height: 270,
                okFunc: function () {
                	if(addModal.frame.validateForm()){
	                    url = URL_BASE + "/platform/group/doEdit.do";
	                    var data = top.getModalFrameContent().find("form").serialize();
	                    //console.error("okFunc");
	                    //console.error(data);
	                    $.post(url,data,function(res){
	                    	//console.error(res);
	                    	if(res.success){
	                    		top.hideModal();
	                    		$("#departmentTree").tree('refreshFolder', $('#'+id));

	                    		top.showShortMessage({text:"部门信息添加成功",type:"info"});
	                    		//$("#departmentTree").tree('openFolder', $('#'+id));
	                    	}else{
	                    		top.alertMessage(res.message);
	                    	}
	                    });
                	}
                }
            });
	 	}
	 	//修改部门
	 	function updateDepartment(){
	 		var id = DEPARTMENT_CURRENT.id;
	 		var parentId = DEPARTMENT_CURRENT.parentId;
 			var url = URL_BASE + "/platform/group/toEdit.do?id="+id+"&parentId=";
 			var addModal = top.showModal({
                url: url,
                title: "修改部门",
                opener: window,
                width: 500,
                height: 270,
                okFunc: function () {
                	if(addModal.frame.validateForm()){
	                    url = URL_BASE + "/platform/group/doEdit.do";
	                    var data = top.getModalFrameContent().find("form").serialize();
	                    //console.error("okFunc");
	                    //console.error(data);
	                    $.post(url,data,function(res){
	                    	//console.error(res);
	                    	if(res.success){
	                    		top.hideModal();
	                    		$("#departmentTree").tree('refreshFolder', $('#'+parentId));
	                    		//$("#departmentTree").tree('openFolder', $('#'+id));
	                    		top.showShortMessage({text:"部门信息修改成功",type:"info"});
	                    	}else{
	                    		top.alertMessage(res.message);
	                    	}
	                    });
                	}
                }
            });
	 	}
	 	//删除部门
	 	function removeDepartment(){
	 		if(DEPARTMENT_CURRENT){
	 			var id = DEPARTMENT_CURRENT.id;
	 			var parentId = DEPARTMENT_CURRENT.parentId;
	 			var name = DEPARTMENT_CURRENT.name;
	 			if(DEPARTMENT_CURRENT.flag){
	 				top.alertMessage(name + "为同步数据,不能删除！");
	 			}else if(DEPARTMENT_CURRENT.hasChildren){
	 				top.alertMessage(name + "存在子部门,请先清空后再删除！");
	 			}else if(DEPARTMENT_CURRENT.hasUser){
	 				top.alertMessage(name + "存在用户,请先清空后再删除！");
	 			}else{	 				
	 				top.confirmMessage("确定删除["+name+"]部门吗?", function(res){
	 					//开始执行删除
	 					//console.error(res);
	 					if(res){
	 						var url = URL_BASE + "/platform/group/removeById.do?id="+id+"&parentId="+parentId;
	 						$.post(url,{},function(res){
	 							if(res.success){
	 								var refreshId = res.data;
		                    		$("#departmentTree").tree('refreshFolder', $('#'+refreshId));
		                    		top.showShortMessage({text:"部门删除成功",type:"info"});
	 							}else{
	 								top.alertMessage(res.message);
	 							}
	 						});
	 					}
	 				});	 				
	 			}
	 		}
	 	}
	 	//设置角色
	 	function setRole(){
	 		if(DEPARTMENT_CURRENT){
		 		var id = DEPARTMENT_CURRENT.id;
		 		var parentId = DEPARTMENT_CURRENT.parentId;
	 			var url = URL_BASE + "/platform/role/sellist.do?did="+id+"&uid=";
	 			var setRoleModal = top.showModal({
	                url: url,
	                title: "设置部门角色",
	                opener: window,
	                width: 500,
	                height: 270,
	                okFunc: function () {
	                    url = URL_BASE + "/platform/group/setRole.do";
	                    var data = top.getModalFrameContent().find("form").serialize();
	                    //console.error(data);
	                   	$.post(url,data,function(res){
	                    	if(res.success){
	                    		//console.error(res);
	                    		top.hideModal();
	                    		//top.alertMessage("角色设置成功");
	                    		top.showShortMessage({text:"部门角色设置成功",type:"info"});
	                    	}else{
	                    		top.alertMessage(res.message);
	                    	}
	                    });
	                }
	            });
 			}
	 	}
	 	
	 	/***=======用户管理部分========*/
	 	var userToEditUrl = URL_BASE + "/platform/user/toEdit.do?id={0}&groupId={1}";
	 	//添加用户
	 	function addUser(){
	 		if(DEPARTMENT_CURRENT && DEPARTMENT_CURRENT.id){
	 			var url = userToEditUrl.format("",DEPARTMENT_CURRENT.id);
	 			var addUserModal = top.showModal({
	                url: url,
	                title: "添加用户",
	                opener: window,
	                width: 500,
	                height: 350,
	                okFunc: function () {
	                	if(addUserModal.frame.validateForm()){
		                    url = URL_BASE + "/platform/user/doEdit.do";
		                    var data = top.getModalFrameContent().find("form").serialize();
		                    $.post(url,data,function(res){
		                    	if(res.success){
		                    		DEPARTMENT_CURRENT.hasUser = true;
		                    		top.hideModal();
		                    		findUser({id:DEPARTMENT_CURRENT.id,key:""});
		                    		top.showShortMessage({text:"用户添加成功",type:"info"});
		                    	}else{
		                    		top.alertMessage(res.message);
		                    	}
		                    });
	                	}
	                }
	            })
	 		}else{
	 			top.alertMessage("请选择用户所属部门");
	 		}
	 	}
	 	//修改用户
	 	function updateUser(id,flag){
	 		if(flag > 0){
	 			top.alertMessage("同步数据，不能修改");
	 			return ;
	 		}
	 		var url = userToEditUrl.format(id,"");
	 		var updateUserModal = top.showModal({
                url: url,
                title: "修改用户",
                opener: window,
                width: 500,
                height: 350,
                okFunc: function () {
                	if(updateUserModal.frame.validateForm()){
	                    url = URL_BASE + "/platform/user/doEdit.do";
	                    var data = top.getModalFrameContent().find("form").serialize();
	                    //console.error(data);
	                    $.post(url,data,function(res){
	                    	//console.error(res);
	                    	if(res.success){
	                    		top.hideModal();
	                    		if(DEPARTMENT_CURRENT && DEPARTMENT_CURRENT.id){
		                    		findUser({id:DEPARTMENT_CURRENT.id,key:""});
	                    		}else{
	                    			queryUserById(id);
	                    		}
	                    		top.showShortMessage({text:"用户修改成功",type:"info"});
	                    	}else{
	                    		top.alertMessage(res.message);
	                    	}
	                    });
                	}
                }
            })
	 	}
	 	
	 	/**
	 	 * 根据id获取用户
	 	 * @param id
	 	 */
	 	function queryUserById(id){
	 		var url = URL_BASE + "/platform/user/getById.do";
	 		$.post(url,{id:id},function(res){
	 			//console.error(res);
	 			var a = [];
	 			a[0] = res;
	 			buildUserHtml(a);
	 		});
	 	}
	 	//删除用户
	 	function removeUser(id,flag){
	 		if(flag > 0){
	 			top.alertMessage("同步数据，不能删除");
	 			return ;
	 		}
	 		top.confirmMessage("确定删除用户吗?", function(res){
				//开始执行删除
				if(res){
			 		var url = URL_BASE + "/platform/user/syncremove.do?id="+id;
					$.post(url,{},function(res){
						if(res.success){
							if(DEPARTMENT_CURRENT && DEPARTMENT_CURRENT.id){
								findUser({id:DEPARTMENT_CURRENT.id,key:""},function(res){
									if(!res || res.length == 0){
										DEPARTMENT_CURRENT.hasUser = false;
									}
								});
							}else{
								$("#userInfoContainer").html("请选择组织机构");
							}
							top.showShortMessage({text:"用户删除成功",type:"info"});
						}else{
							top.alertMessage(res.message);
						}
					});
				}
			});
	 	}
	 	//移动用户
	 	function moveUser(id,flag){
	 		if(flag > 0){
	 			top.alertMessage("同步数据，不能换部门");
	 			return ;
	 		}
	 		var url = URL_BASE + "/platform/group/selGroupList.do";
	 		var moveUserModal = top.showModal({
                url: url,
                title: "更换部门",
                opener: window,
                width: 550,
                height: 500,
                okFunc: function () {
                	var resData = moveUserModal.frame.getData();
                    url = URL_BASE + "/platform/user/moveDept.do";
                    var data = {id:id,parentId:resData.id};
                   $.post(url,data,function(res){
                    	if(res.success){
                    		top.hideModal();
                    		if(DEPARTMENT_CURRENT && DEPARTMENT_CURRENT.id){
                    			findUser({id:DEPARTMENT_CURRENT.id,key:""});
                    		}
                    		top.showShortMessage({text:"用户更换部门成功",type:"info"});
                    	}else{
                    		top.alertMessage(res.message);
                    	}
                    });
                }
            });
	 	}
	 	//重置密码
	 	function resetPw(id,flag){
	 		if(flag > 0){
	 			top.alertMessage("同步数据，不能重置密码");
	 			return ;
	 		}
	 		top.confirmMessage("确定重置吗?", function(res){
				//开始执行
				if(res){
			 		var url = URL_BASE + "/platform/user/resetPw.do?id="+id;
					$.post(url,{},function(res){
						if(res.success){
							//top.alertMessage("密码重置成功");
							top.showShortMessage({text:"用户密码重置成功",type:"info"});
						}else{
							top.alertMessage(res.message);
						}
					});
				}
			});
	 	}
	 	//设置密码
	 	function setPw(id,flag){
	 		if(flag > 0){
	 			top.alertMessage("同步数据，不能设置密码");
	 			return ;
	 		}
	 		var url = URL_BASE + "/platform/user/toSetPw.do?id="+id;
	 		var setUserPwModal = top.showModal({
                url: url,
                title: "设置新密码",
                opener: window,
                width: 200,
                height: 150,
                okFunc: function () {
                    url = URL_BASE + "/platform/user/doSetPw.do";
                    var id = top.getModalFrameContent().find("#id").val();
                    var pw = $.trim(top.getModalFrameContent().find("#password").val());
                    if(!pw){
                    	top.alertMessage("请输入密码");
                    	return;
                    }
                    var data = {userId:id,pswd:pw};
                   	$.post(url,data,function(res){
                    	if(res.success){
                    		top.hideModal();
                    		top.showShortMessage({text:"用户密码设置成功",type:"info"});
                    	}else{
                    		top.alertMessage(res.message);
                    	}
                    });
                }
            });	 		
	 		
	 	}
	 	//禁用
	 	function disableUser(id,flag){
	 		if(flag > 0){
	 			top.alertMessage("同步数据，不能操作");
	 			return ;
	 		}
	 		var url = URL_BASE + "/platform/user/disable.do?id="+id;
			$.post(url,{},function(res){
				if(res.success){
					if(DEPARTMENT_CURRENT && DEPARTMENT_CURRENT.id){
                		findUser({id:DEPARTMENT_CURRENT.id,key:""});
            		}else{
            			queryUserById(id);
            		}
					//findUser({id:DEPARTMENT_CURRENT.id,key:""});
            		top.showShortMessage({text:"用户状态修改成功",type:"info"});
				}else{
					top.alertMessage(res.message);
				}
			});
	 	}
	 		 	
	 	//设置角色
	 	function setUserRole(id){
 			var url = URL_BASE + "/platform/role/sellist.do?did=&uid="+id;
 			var setUserRoleModal = top.showModal({
                url: url,
                title: "设置用户角色",
                opener: window,
                width: 500,
                height: 270,
                okFunc: function () {
                    url = URL_BASE + "/platform/user/setRole.do";
                    var data = top.getModalFrameContent().find("form").serialize();
                    //console.error(data);
                   	$.post(url,data,function(res){
                    	if(res.success){
                    		//console.error(res);
                    		top.hideModal();
                    		//top.alertMessage("角色设置成功");
                    		top.showShortMessage({text:"用户角色设置成功",type:"info"});
                    	}else{
                    		top.alertMessage(res.message);
                    	}
                    });
                }
            });
	 	}
		function syncDeptAndUser() {
			showLoading("部门用户数据同步中，请稍候···");
			top.requestData({
				url: URL_BASE + "/platform/group/syncdeptuser.do",
				success: function (exeResult) {
					hideLoading();
					if (exeResult.success) {
						top.showShortMessage({
							text: exeResult.message,
							callback: function () {
								window.location.reload();
							}
						});
					} else {
						top.alertMessage(exeResult.message);
					}
				},
				error: function () {
					hideLoading();
				}
			});
		}
