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
	  
        });
	  
        function selectBuilding(buildingId) {
            $("#buildingId").val(buildingId);
            $("form").submit();
        }