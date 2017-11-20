function edit(id) {
            var params = "?id=" + (id ? id : "");
            var title = "编辑";
            if (!id) {
                //添加项目判断项目是否已选
                var projectId = $("#filter_EQS_projectId").val();
                var phaseId = $("#filter_EQS_phaseId").val();
                if (projectId == null || projectId == "") {
                    top.showShortMessage({
                        text: "请选择项目",
                        type: "warn"
                    });
                    return;
                } else {
                    params += "&projectId=" + projectId;
                    params += "&phaseId=" + phaseId;
                }
                title = "添加";
            }
            top.showModal({
                url: URL_BASE+"/roomtype/edit.do" + params,
                title: title + "户型",
                opener: window,
                width: 800,
                height: 500
            });
        }
        function copy() {
        	var pid = $("#filter_EQS_projectId").val();
        	var phaseId = $("#filter_EQS_phaseId").val();
        	if(!pid){
        		top.alertMessage("请选择项目");
        		return;
        	}
            var modal = top.showModal({
                url: URL_BASE+"/roomtype/copylist.do?pid="+ pid+"&phaseId=" + phaseId,
                title: "复制户型",
                opener: window,
                width: 800,
                height: 500,
                okFunc: function () {
                    var result = modal.frame.copyRoomType();
                    if (result.success) {
                        top.showShortMessage({
                            text: "复制成功！",
                            callback: function () {
                                top.hideModal();
                                //window.location.reload();
                                $("form").submit();
                            }
                        });
                    } else {
                        top.alertMessage(result.message);
                    }
                }
            });
        }
        function drawPos(id) {
            var modal = top.showModal({
                url: URL_BASE+"/roomtype/drawpos.do?id=" + id + "&rtfid=",
                title: "绘制部位",
                opener: window,
                width: 800,
                height: 530,
                okFunc: function () {
                    modal.frame.savePosition();
                }
            });
        }
        function setProjectPharse(projectId,phaseId,name){
      	  $("#filter_EQS_projectId").val(projectId);
            $("#filter_EQS_phaseId").val(phaseId);
            $('#projectName').val(name);
            $('#projectNameInput').val(name);
        	$("form").submit();
       }  
        
        $(function () {
        	 $('#projectBtn').click(function(){
      	   	   		top.showModal({
      	              url: URL_BASE+"/project/projectphaselistpage.do?modal=setProjectPharse&hasPhaseAuth=false&projectName=",
      	              title: "项目分期查询",
      	              showFooter: false,
      	              opener: window,
      	              width: 800,
      	              height: 600,
      	              onFinished: function (exeResult) {
      	                
      	              }
      	          });
      	   	   
      	      });
        	 
        	
            initAutoComplete({
                url: URL_BASE+"/project/projectphaselist.do",
                selectId: "projectNameInput",
                onSelected: function (id, text) {
                    var ids = id.split("|");
                    $("#filter_EQS_projectId").val(ids[0]);
                    $("#filter_EQS_phaseId").val(ids[1]);
                    $('#projectName').val(text);
                    $("form").submit();
                }
            });
        });