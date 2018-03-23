 function setProjectPharse(projectId,phaseId,name){
	  $("#projectId").val(projectId);
      $("#phaseId").val(phaseId);
      $('#projectName').val(name);
      $('#projectNameInput').val(name);
      clearAndSumbit('batchId','buildingId','unitId');
  //    getResourceFromPharse(projectId,phaseId);
 } 
 function clearAndSumbit(){
	 var len=arguments.length;
	 if(len){
		 for (i =0 ; i < len; i++){ 
			 $('#'+arguments[i]).val('');
		}
	 }
	 $("form").submit();
 }
 
 function tempView(url, tempName) {
     top.showModal({
         title: tempName,
         width: 900,
         height: 620,
         url: url,
         showFooter: false
     });

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
	  $('#batchId').change(function(){
		  if(this.value){
			//  getResourceFromBath(this.value);
			clearAndSumbit('buildingId','unitId');
		  }
	  });
	  $('#buildingId').change(function(){
		  if(this.value){
			//  getResourceFromBuild(this.value);
			  clearAndSumbit('unitId');  
		  }
	  });
	  $('#unitId').change(function(){
		  if(this.value){
			  clearAndSumbit();  
		  }
	  });
	  $('.checked').click(function(){
			var roomId=this.id;
			var batchId=$('#batchId').val();
	//		$('#roomsInfoDiv').removeClass('col-md-12').addClass('col-md-6');
	//		$('#roomInfoDiv').attr('style','display:block');
			$('.checked').removeClass('active');
			$(this).addClass('active');
			showLoadingOverlay("#roomInfoDiv");
			$('#roomInfoDiv').load(URL_BASE+"/room/ndetail.do?batchId="+batchId+"&roomId="+roomId);
		});
	  
	  initAutoComplete({
          url: URL_BASE+"/project/projectphaselist.do",
          selectId: "projectNameInput",
          onSelected: function (id, text) {
              var ids = id.split("|");
              $("#projectId").val(ids[0]);
              $("#phaseId").val(ids[1]);
              $('#batchId').val("");
              $('#projectName').val(text);
              $("form").submit();
          }
      });
 });

function getResourceFromPharse(projectId,phaseId){
	var paramData={projectId:projectId,phaseId:phaseId};
//	showLoading();
	getResource(paramData);
}

function getResourceFromBath(batchId){
	var phaseId= $("#phaseId").val();
//	showLoading();
	var paramData={batchId:batchId,resourceType:'b',phaseId:phaseId};
	getResource(paramData);
}

function getResourceFromBuild(buildingId){
	var batchId=$("#batchId").val();
	var phaseId= $("#phaseId").val();
//	showLoading();
	var paramData={batchId:batchId,buildingId:buildingId,resourceType:'u',phaseId:phaseId};
	getResource(paramData);
}

function showQueryConfig(){
	 top.showModal({
	        url: URL_BASE + "/queryconfig/roomedit.do",
	        title: "显示内容设置",
	        opener: window,
	        width: 600,
	        height: 330,
	        onFinished: function (exeResult) {
	        }
	    });
	
}

function getResource(paramData){
	$.ajax({
		url:URL_BASE+"/roomdelivery/batchresourcedlist.do?",
		method:"POST",
		data:paramData,
		success:function(response){
			if(response.success){
				var data=response.data;
				for(var i in data){
					var beanList=data[i];
					var html='';
					$('#'+i).html(html);
					for(var j in beanList){
						html+='<option value="'+beanList[j].id+'">'+beanList[j].name+'</option>';
					}
					$('#'+i).html(html);
				}
			}
			$("form").submit();
	//		hideLoading();
			
		}
	});
}

function viewProblem(id) {
    top.showModal({
        title: "问题详情",
        width: 900,
        height: 620,
        url: URL_BASE+"/checkproblem/edit.do?id=" + id,
        showFooter: false
    });
}