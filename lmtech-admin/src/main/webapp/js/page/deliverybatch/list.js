function setProjectPharse(projectId,phaseId,name){
          $('#projectName').val(name);
          $('#projectNameInput').val(name);
          $("#filter_EQS_projectId").val(phaseId);
          $("form").submit();
}
$(function(){
	
	 initAutoComplete({
         //url: URL_BASE + "/project/projectphaselist.do",
         url: URL_BASE + "/project/projectphaseAuthlist.do",
         selectId: "projectNameInput",
         onSelected: function (id, text) {
             var pids = id.split("|");
             var pid = pids.length > 1?pids[1]:pids[0];
             $("#filter_EQS_projectId").val(pid);
             //console.error(pid);
             $('#projectName').val(text);
             $("form").submit();
         }
     });
	
	 $('#projectBtn').click(function(){
   	   var frame=top.showModal({
              url: URL_BASE+"/project/projectphaselistpage.do?modal=setProjectPharse&hasPhaseAuth=true&projectName=",
              title: "项目分期查询",
              showFooter: false,
              opener: window,
              width: 800,
              height: 600,
              onFinished: function (exeResult) {
                
              }
          });
   	   
      });
});
function toEdit(){
	var pid = $("#filter_EQS_projectId").val();
	if(pid && pid.length > 0){
		var url = URL_BASE + "/deliverybatch/toEdit.do?id="+"&pid="+ pid;
		//console.error(url);
		window.location.href = url;
	}else{
		top.alertMessage("请先选择项目");
	}
}
function doSearch(){
	$("form").submit();
}
function doRemove(id,isDownload){
	if(isDownload){
		top.alertMessage("该批次数据已被下载，无法删除");
		return;
	}
	top.confirmMessage("确定删除批次吗",function(res){
		if(res){
			var pid = $("#filter_EQS_projectId").val();
			var removeUrl = URL_BASE + "/deliverybatch/doRemove.do?id="+id;
			var data = {};
			
			$.post(removeUrl,data,function(res){
				if(res.success){
					var url = URL_BASE + "/deliverybatch/list.do?filter_EQS_projectId="+ pid;
					//console.error(url);
					window.location.href = url;
				}else{
					top.alertMessage(res.message);
				}
			});
		}
	});	
		
}
function doClose(id){
	var pid = $("#filter_EQS_projectId").val();
	var removeUrl = URL_BASE + "/deliverybatch/doClose.do?id="+id;
	var data = {};	
	$.post(removeUrl,data,function(res){
		if(res.success){
			var url = URL_BASE + "/deliverybatch/list.do?filter_EQS_projectId="+ pid;
			window.location.href = url;
		}else{
			top.alertMessage(res.message);
		}
	});		
}

function doEnd(id){
	var pid = $("#filter_EQS_projectId").val();
	var removeUrl = URL_BASE + "/deliverybatch/doEnd.do?id="+id;
	var data = {};
	$.post(removeUrl,data,function(res){
		if(res.success){
			var url = URL_BASE + "/deliverybatch/list.do?filter_EQS_projectId="+ pid;
			window.location.href = url;
		}else{
			top.alertMessage(res.message);
		}
	});		
}
function doOpen(id){
	var pid = $("#filter_EQS_projectId").val();
	var removeUrl = URL_BASE + "/deliverybatch/doOpen.do?id="+id;
	var data = {};
	$.post(removeUrl,data,function(res){
		if(res.success){
			var url = URL_BASE + "/deliverybatch/list.do?filter_EQS_projectId="+ pid;
			window.location.href = url;
		}else{
			top.alertMessage(res.message);
		}
	});		
}