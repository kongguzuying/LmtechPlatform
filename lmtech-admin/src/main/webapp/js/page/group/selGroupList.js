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
var ajaxUrl = URL_BASE + "/platform/group/ajaxChildren.do?parentId={0}&requestIndex={1}";
var requestIndex = 0;
function staticDataSource(parentData, callback) {
	//console.error(parentData);
	var parentId = "";
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
});
function getData(){
	var selArr = $("#departmentTree").tree("selectedItems");
	var selJson = (selArr && selArr.length > 0)?selArr[0]:null;
	if(!selJson){
		top.alertMessage("请选择一个部门");
		return null;
	}
	return selJson;
}