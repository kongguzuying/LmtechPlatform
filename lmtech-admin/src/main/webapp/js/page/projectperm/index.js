	$(function(){
		loadPage(URL_BASE+'/projpermdept/checkList.do?type=2');
	});
    var pageUrl = null;
    function reloadPage() {
        if (pageUrl) {
            loadPage(pageUrl);
        }
    }
    function loadPage(url) {
        showLoadingOverlay("#permContent");
        pageUrl = url;
        var resourceAuth=$("#resourceAuth").val();
        var resourceId = $("#resourceId").val();
        var resourceType = $("#resourceType").val();
        var tempUrl;
        if(resourceAuth=='false'&&"g" != resourceType){
        	resourceId="";
        	resourceType="";
        }
        if (url.indexOf("?") > 0) {
            tempUrl = url + "&resourceId=" + resourceId + "&resourceType=" + resourceType + "&resourceAuth="+resourceAuth;
        } else {
            tempUrl = url + "?resourceId=" + resourceId + "&resourceType=" + resourceType + "&resourceAuth="+resourceAuth;
        }
        loadPageUrl(tempUrl);
    }
    function loadPageUrl(url) {
        var newUrl = url;
        var dt = new Date();
        if (newUrl.indexOf("?") > 0) {
            newUrl += "&t=" + dt.getTime();
        } else {
            newUrl += "?t=" + dt.getTime();
        }
        $("#permContent").load(newUrl);
    }
    function loadPageContent(content) {
        showLoadingOverlay("#permContent");
        $("#permContent").html(content);
    }
    function onPageIndexClick(pageIndex) {
        var action = $("form").attr("action");
        action = action.replace("check.do", "checklist.do");
        $("form").attr("action", action);
        submitPageForm();
    }
    function submitPageForm() {
        var resourceId = $("#resourceId").val();
        var resourceType = $("#resourceType").val();
        var resourceAuth = $("#resourceAuth").val();
        if (!resourceId) {
            top.alertMessage("请选择左侧项目资源");
            return;
        }
        if("g" != resourceType && resourceAuth=='false'){
        	 top.alertMessage("无权限操作");
        	 return;
        }
        showLoadingOverlay("#permContent");
        $("form").ajaxForm({
            data: { resourceId: resourceId, resourceType: resourceType },
            success: function (content) {
                if (typeof content == 'object') {
                    if (content.success) {
                        top.alertMessage("操作成功！", function () {
                            reloadPage();
                        });
                    }
                } else {
                    loadPageContent(content);
                }
                //hideLoading("#permContent");
            }
        });
        if (typeof beforeSubmit != "undefined") {
            beforeSubmit();
        }
        $("form").submit();
    }
    
    
//    var ajaxUrl = URL_BASE+"/projectperm/complex.do?parentId={0}&type={1}"
//    function staticDataSource(parentData, callback) {
//        var parentId = "";
//        var type = "";
//        if(parentData && parentData.attr && parentData.attr.id){
//            parentId = parentData.attr.id;
//        }
//        if(parentData && parentData.attr && parentData.attr.type){
//            type = parentData.attr.type;
//        }
//        var url = ajaxUrl.format(parentId, type);
//
//        $.post(url,{},function(res){
//            callback({
//                data: res
//            });
//        });
//    }
//
//    
    
    var ajaxUrl = URL_BASE+"/project/nodelist.do?id={0}&resourceType={1}&auth={2}"
    function staticDataSource(parentData, callback) {
        var parentId = "";
        var type = "";
        var auth = false;
        var isFolder=true;
        
        if(parentData && parentData.attr){
            parentId = parentData.attr.id;
            type = parentData.attr.type;
            auth = parentData.attr.auth;
            if(type == 'g'){
            	type = "c";
            }else if(type=='c'){
            	type='p';
            }else if(type=='p') type='ph';
            else if(type=='ph') type='b';
            else if(type=='b'){
            	isFolder=false;
            	type='u';
            }
            
            var url = ajaxUrl.format(parentId, type, auth);

            $.post(url,{},function(res){
            	
            	var datas = [];
            	var nodeData=res.data;
         	    for (var i = 0; i < nodeData.length; i++) {
         	    	var treeAction='';
         	         if (nodeData[i].auth) {
         	        	treeAction+='<span style="color:green" class="auth">'+nodeData[i].name;
         	         }else{
         	        	treeAction+='<span>'+nodeData[i].name;
         	         }
         	        treeAction += '</span>';
         	        datas[i] = {
         	            name: treeAction,
         	            type: isFolder?'folder':'item',
         	            attr: {
         	                id: nodeData[i].id,
         	                type: nodeData[i].resourceType,
         	                auth:nodeData[i].auth
         	            }
         	        };
         	    }
         	    
            	
                callback({
                    data: datas
                });
            });
        }else{
        	var datas=[{name:'恒大集团',type:'folder',attr:{id:'all',type:'g',auth:false}}];
        	callback({
                data: datas
            });
        }
        
       
    }

    
    var tree = $('#resourceTree').tree({
        dataSource: staticDataSource,
        multiSelect: false,
        folderSelect: true,
        loadingHTML: '<img src="' + URL_BASE + '/images/input-spinner.gif"/>'
        
    });

    $('#resourceTree').on("selected.fu.tree",function(event,data){
        $("#resourceId").val(data.target.attr.id);
        $("#resourceType").val(data.target.attr.type);
        $("#resourceAuth").val(data.target.attr.auth);
        loadPage(pageUrl);
        
    });

    $('#resourceTree').on("deselected.fu.tree",function(event,data){
        $("#resourceId").val("");
        $("#resourceType").val("");
    });
