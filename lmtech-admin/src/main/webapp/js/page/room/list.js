 function setProjectPharse(projectId,phaseId,name){
	  $("#projectId").val(projectId);
      $("#phaseId").val(phaseId);
      $('#projectName').val(name);
      $('#projectNameInput').val(name);
  	  $("form").submit();
 }  
$(function () {
	  $('#projectBtn').click(function(){
	   	   var frame=top.showModal({
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
                    $("#projectId").val(ids[0]);
                    $("#phaseId").val(ids[1]);
                    $('#projectName').val(text);
                    $("form").submit();
                }
            });

            var maxfilesize = 50;
    		var _url_001 = URL_BASE+"/checkitem/getMaxFileSize.do?time="+new Date().getTime();
    		$.ajax({
                url: _url_001,
                async:false,
                success: function (data) {
                	maxfilesize = data;
                }
            });
    		
            var myDropzone = new Dropzone("div#fileUpload", {
                url: URL_BASE+"/room/importRoomData.do",
                method: "post",
                maxFilesize : maxfilesize, //MB
                dictFileTooBig : "文件过大超过最大限制"+maxfilesize+"MB!",
                acceptedFiles : ".xls,.xlsx", //上传的类型
                dictInvalidFileType : "导入数据的文件,文件类型必须是.xls或.xlsx!",
                clickable: true,
                sending: function () {
                	 $.isLoading({ text: "房间数据导入中，请稍候......" });
                },
                success: function (data, exeResult) {
                    if (exeResult.success) {
                        top.showShortMessage({ text: "导入房间数据成功！" });
                        $("form").submit();
                    } else {
                        top.alertMessage(exeResult.message ? exeResult.message : "上传过程出现未知错误");
                    }
                },
                complete: function () {
                    hideLoading();
                },
                error: function (file, text) {
                	top.alertMessage(text);
                	$("form").submit();
                }
            });

            /*$(".roomNo").editable({
                type: "text"
            });*/
        });
        function importExcel() {
            $("div#fileUpload").click();
        }
        function exportExcel() {
        	var projectId = $("#projectId").val();
        	var phaseId = $("#phaseId").val();
        	if(phaseId == ""){
        		 top.alertMessage("请选择楼栋再导出!");
        		 return false;
        	}
        		
        	var buildingId = $("#buildingId").val();
        	var _url = URL_BASE+"/room/getExportRoomData.do?time="+new Date().getTime();
        	_url = _url +"&projectId="+projectId+"&phaseId="+phaseId+"&buildingId="+buildingId;
	 		window.location.href = _url; 
        }
        function selectBuilding(buildingId) {
            $("#buildingId").val(buildingId);
            $("form").submit();
        }
        function addArea() {
            var modal = top.showModal({
                url: URL_BASE+"/room/addarea.do",
                title: "添加公共区域",
                opener: window,
                width: 500,
                height: 250,
                okFunc: function () {
                    if (modal.frame.isValid()) {
                        var param = modal.frame.getParam();
                        param.projectId = $("#projectId").val();
                        param.phaseId = $("#phaseId").val();
                        param.buildingId = $("#buildingId").val();

                        top.requestData({
                            url: URL_BASE+"/room/addarea.do",
                            data: JSON.stringify(param),
                            success: function (exeResult) {
                                if (exeResult.success) {
                                    //window.location.reload(false);
                                    $("form").submit();
                                    top.hideModal();
                                } else {
                                    top.alertMessage(exeResult.message);
                                }
                            }
                        });
                    }
                }
            });
        }
        function info(roomId) {
            top.showModal({
                url: URL_BASE+"/room/info.do?id=" + roomId,
                title: "房间信息",
                opener: window,
                width: 700,
                height: 350,
                showFooter: false
            });
        }
        function removeRoom(elem, roomId) {
            top.confirmMessage("确定删除房间？", function (result) {
                if (result) {
                    top.requestData({
                        url: URL_BASE+"/room/syncremove.do?id=" + roomId,
                        success: function (exeResult) {
                            if (exeResult.success) {
                                $(elem).parent().empty();
                                top.showShortMessage({
                                    text: "删除房间成功。"
                                });
                            } else {
                                top.alertMessage(exeResult.message);
                            }
                        }
                    });
                }
            });
            return false;
        }
        function syncData() {
            var buildingId = $("#buildingId").val();
            if (!buildingId) {
                top.showShortMessage({ text: "请选择楼栋" });
                return;
            }
            showLoading("房间数据同步中，请稍候···");
            top.requestData({
                url: URL_BASE+"/room/syncdata.do?buildingId=" + buildingId,
                success: function (exeResult) {
                    hideLoading();
                    if (exeResult.success) {
                        top.showShortMessage({
                            text: exeResult.message,
                            callback: function () {
                                $("form").submit();
                                //window.location.reload();
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