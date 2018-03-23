var _baseUrl;
var _companys;
var _projects;
var _phases;
var _hasPhaseAuth;
function initTree(options) {
    _baseUrl = options.baseUrl;
    _companys = options.companys;
    _hasPhaseAuth=options.hasPhaseAuth;
    var DataSourceTree = function (options) {
        this._data = options.data;
        this._delay = options.delay;
    };
    
    DataSourceTree.prototype = {
        data:function(item, callback){
        	var self = this;
        	setTimeout(function () {
                var data = $.extend(true, [], self._data);
                if (item && item.extra) {
                    if (item.extra.type == "c") {
                        var projects = getNodeList(item.extra.id,'p',item.extra.auth,true,item.extra.enableRemove);
                        callback({data: projects});
                    } else if (item.extra.type == "p") {
                        var phases = getNodeList(item.extra.id,'ph',item.extra.auth,false,item.extra.enableRemove);
                        callback({data: phases});
                        $(".select-phase").bind("click", function () {
                        	var p=$(this).parent();
                        	var phaseId=p.attr('ida');
                        	var phaseName=p.attr('name');
                        	selectPharse(item.extra.id,phaseId,item.extra.name,phaseName)
                        	return false;
                        });
                    }
                    
                }else{
                	callback({data: data});
                }
            }, self._delay)
        }
    };

    // INITIALIZING TREE
    var treeDataSource = new DataSourceTree({
        data: getNodeList("","c",false,true),
        delay: 100
    });
  
    $('#tree').tree({
        selectable: false,
        dataSource: treeDataSource,
        loadingHTML: '<img src="' + _baseUrl + '/images/input-spinner.gif"/>',
        multiSelect: false
    });
}

function getNodeList(id,resourceType,auth,isFolder,enableRemove){
	 $.ajax({
	        url: _baseUrl + "/project/nodelist.do",
	        dataType: "json",
	        method: "POST",
	        async: false,
	        data: { id:id,resourceType:resourceType,auth:auth },
	        success: function (exeResult) {
	            if (exeResult.success) {
	                nodeData = exeResult.data;
	            } else {
	                top.alertMessage("获取数据出错，" + exeResult.message);
	            }
	        }
	    });
	
	 var datas = [];
	 	var j=0;
	    for (var i = 0; i < nodeData.length; i++) {
	    	var treeAction='';
    		treeAction = '<div ida="' + nodeData[i].id + '" class="tree-actions" name="'+nodeData[i].name+'">';
    		var rtype=nodeData[i].resourceType;
    		if("ph"== rtype){
    			if(_hasPhaseAuth=='true'&&!nodeData[i].auth){
        			continue;
        		}
            	treeAction += '<a href="#" class="select-phase">选择</a>';
            }
	        treeAction += '</div>';
	        datas[j] = {
	            name: nodeData[i].name+treeAction,
	            type: isFolder?'folder':'item',
	            extra: {
	                id: nodeData[i].id,
	                type: resourceType,
	                auth:nodeData[i].auth,
	                name:nodeData[i].name,
	                enableRemove:nodeData[i].enableRemove
	            }
	        };
	        j++;
	    }
	    return datas;
}

function selectPharse(projectId,phaseId,ProjectName,phaseName){
	var id=projectId+'|'+phaseId;
	var name=ProjectName+'-'+phaseName;
	choose(id,name);
}
