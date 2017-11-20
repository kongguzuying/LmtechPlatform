function add() {
    var curId = $("#hidCurrentId").val();
    var url = URL_BASE + "/menu/edit.do?id=&parentId=" + curId;
    window.location.href = url;
}
        
        
$(function(){
     var myDropzone = new Dropzone("div#fileUpload", {
         url: URL_BASE + "/contractor/importContractor.do",
         method: "post",
         maxFilesiz: 10,
         uploadMultiple: false,
         addRemoveLinks: false,
         clickable: true,
         sending: function () {
         	 $.isLoading({ text: "施工单位数据导入中，请稍候......" });
         },
         success: function (data, exeResult) {
             if (exeResult.success) {
                 top.showShortMessage({ text: "导入施工单位项数据成功！" });
                 $("form").submit();
             } else {
                 top.alertMessage(exeResult.message ? exeResult.message : "上传过程出现未知错误");
             }
         },
         complete: function () {
             hideLoading();
         }
     });
       
});
    	
function importExcel() {
    $("div#fileUpload").click();
}
    	
function edit(id){
	 var url = URL_BASE + "/contractor/edit.do?id=";
	 var title = "添加";
	 if(id){
		 url +=id;
		 title = "修改";
	 }
     var modal = top.showModal({
         url: url,
         title: title+"施工单位",
         opener: window,
         width: 650,
         height: 450});
}

//下载模板
function downTemplate(){
	var _url = URL_BASE + "/contractor/doDownContractor.do?time="+new Date().getTime();
	window.location.href = _url; 
}