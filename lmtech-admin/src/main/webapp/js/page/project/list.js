var _baseUrl;
var _companys;
var _projects;
var _phases;

function initTree(options) {
    _baseUrl = options.baseUrl;
    _companys = options.companys;

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
                        $("div[ida='" + item.extra.id + "'] .add-project").bind("click", function () {
                            addProject(item.extra.id);
                            return false;
                        });
                        $(".remove-project").bind("click", function () {
                        	var uri="resourceType=p&pid="+$(this).parent().attr('ida');
                        	disable(uri);
                        	return false;
                        });
                    } else if (item.extra.type == "p") {
                        var phases = getNodeList(item.extra.id,'ph',item.extra.auth,true,item.extra.enableRemove);
                        callback({data: phases});
                        $("div[ida='" + item.extra.id + "'] .add-phase").bind("click", function () {
                            addPhase(item.extra.id);
                            return false;
                        });
                        $(".remove-phase").bind("click", function () {
                        	var uri="resourceType=ph&phid="+$(this).parent().attr('ida');
                        	disable(uri);
                        	return false;
                        });
                        
                    }else if (item.extra.type == "ph") {
                        var building = getNodeList(item.extra.id,'b',item.extra.auth,true,item.extra.enableRemove);
                        callback({data: building});
                        $("div[ida='" + item.extra.id + "'] .add-building").bind("click", function () {
                        	addBuilding(item.extra.id);
                            return false;
                        });
                        $(".remove-building").bind("click", function () {
                        	var uri="resourceType=b&bid="+$(this).parent().attr('ida');
                        	disable(uri);
                        	return false;
                        });
                    } else if (item.extra.type == "b") {
                        var unit = getNodeList(item.extra.id,'u',item.extra.auth,false,item.extra.enableRemove);
                        callback({data: unit});
                        $("div[ida='" + item.extra.id + "'] .add-unit").bind("click", function () {
                        	addUnit(item.extra.id);
                            return false;
                        });
                        
                        $(".remove-unit").bind("click", function () {
                         	var uri="resourceType=u&uid="+$(this).parent().attr('ida');
                         	disable(uri);
                         	return false;
                         });
                    } 
                   
                    
                }else{
                	callback({data: data});
                	 $(".remove-company").bind("click", function () {
                     	var uri="resourceType=c&cid="+$(this).parent().attr('ida');
                     	disable(uri);
                        return false;
                     });
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
	    for (var i = 0; i < nodeData.length; i++) {
	    	var treeAction='';
    		treeAction = '<div ida="' + nodeData[i].id + '" class="tree-actions">';
	         if (nodeData[i].auth) {
	            var rtype=nodeData[i].resourceType;
	            if("c"==rtype){
	            	treeAction += '<a href="#" class="add-project">添加项目</a>';
	            	if(nodeData[i].enableRemove){
	            		treeAction += '<a href="#" class="remove-company">删除</a>';
	  	            }
	            }else if("p"== rtype){
	            	treeAction += '<a href="#" class="add-phase">添加分期</a>';
	            	if(nodeData[i].enableRemove){
             		    treeAction += '<a href="#" class="remove-project">删除</a>';
	            	}
	            }else if("ph"== rtype){
	            	treeAction += '<a href="#" class="add-building">添加楼栋</a>';
	            	if(nodeData[i].enableRemove){
             		    treeAction += '<a href="#" class="remove-phase">删除</a>';
	            	}
	            }else if("b"== rtype){
	            	treeAction += '<a href="#" class="add-unit">添加单元</a>';
	            	if(nodeData[i].enableRemove){
             		    treeAction += '<a href="#" class="remove-building">删除</a>';
	            	}
	            }else if("u"== rtype){
	            	if(nodeData[i].enableRemove){
             		    treeAction += '<a href="#" class="remove-unit">删除</a>';
	            	}
	            }
	         }
	         treeAction += '</div>';
	        datas[i] = {
	            name: nodeData[i].name+treeAction,
	            type: isFolder?'folder':'item',
	            extra: {
	                id: nodeData[i].id,
	                type: resourceType,
	                auth:nodeData[i].auth,
	                enableRemove:nodeData[i].enableRemove
	            }
	        };
	    }
	    return datas;
	
}

function addProject(companyId) {
    top.showModal({
        url: _baseUrl + "/project/edit.do?companyId=" + companyId + "&id=",
        title: "添加项目",
        opener: window,
        width: 600,
        height: 330,
        onFinished: function (exeResult) {
            window.location.reload();
        }
    });
}


function addPhase(projectId) {
    top.showModal({
        url: _baseUrl + "/phase/edit.do?projectId=" + projectId + "&id=",
        title: "添加分期",
        opener: window,
        refreshOpener: false,
        width: 600,
        height: 230,
        onFinished: function (exeResult) {
            window.location.reload();
        }
    });
}

function addBuilding(phaseId) {
    top.showModal({
        url: _baseUrl + "/building/edit.do?phaseId=" + phaseId + "&id=",
        title: "添加楼栋",
        opener: window,
        refreshOpener: false,
        width: 600,
        height: 330,
        onFinished: function (exeResult) {
            window.location.reload();
        }
    });
}

function addUnit(buildingId) {
    top.showModal({
        url: _baseUrl + "/unit/edit.do?buildingId=" + buildingId + "&id=",
        title: "添加单元",
        opener: window,
        refreshOpener: false,
        width: 600,
        height: 330,
        onFinished: function (exeResult) {
            window.location.reload();
        }
    });
}

//remove
function disable(uri){
	top.confirmMessage("确定删除该条数据？",function(res){
		if(res){
			$.ajax({
		        url: _baseUrl + "/project/disable.do?"+uri,
		        dataType: "json",
		        method: "POST",
		        async: false,
		        success: function (exeResult) {
		            if (exeResult.success) {
		            	window.location.reload();
		            } else {
		                top.alertMessage("获取公司数据出错，" + exeResult.message);
		            }
		        }
		  });
		}
	});
}
function removeProject(projectId) {
    listRemove(_baseUrl + "/project/remove.do?id=" + projectId);
}

function getCompanyList() {
    $.ajax({
        url: _baseUrl + "/project/companylist.do",
        dataType: "json",
        method: "POST",
        async: false,
        success: function (exeResult) {
            if (exeResult.success) {
                _companys = exeResult.data;
            } else {
                top.alertMessage("获取公司数据出错，" + exeResult.message);
            }
        }
    });

    var datas = [];
    for (var i = 0; i < _companys.length; i++) {
        var treeAction = '<div ida="' + _companys[i].companyId + '" class="tree-actions">';
        if (_companys[i].enableAddProject) {
            treeAction += '<a href="#" class="add-project">添加项目</a>';
        }
        treeAction += '</div>';

        datas[i] = {
            name: _companys[i].companyName + treeAction,
            type: 'folder',
            extra: {
                id: _companys[i].companyId,
                type: "comp"
            }
        };
    }
    return datas;
}
function getProjectList(companyId) {
    $.ajax({
        url: _baseUrl + "/project/projectlist.do",
        dataType: "json",
        method: "POST",
        async: false,
        data: { companyId: companyId },
        success: function (exeResult) {
            if (exeResult.success) {
                _projects = exeResult.data;
            } else {
                top.alertMessage("获取项目数据出错，" + exeResult.message);
            }
        }
    });

    var datas = [];
    for (var i = 0; i < _projects.length; i++) {
        var treeAction = '<div ida="' + _projects[i].id + '" class="tree-actions">';
        if (_projects[i].enableAddPhase) {
            treeAction += '<a href="#" class="add-phase">添加分期</a>';
        }
        /*if (_projects[i].enableRemoveProject) {
            treeAction += '<a href="#" class="remove-project">删除</a>';
        }*/
        treeAction += '</div>';

        datas[i] = {
            name: _projects[i].projectName + treeAction,
            type: 'folder',
            extra: {
                id: _projects[i].id,
                type: "proj"
            }
        };
    }
    return datas;
}
function getPhaseList(projectId) {
    $.ajax({
        url: _baseUrl + "/project/phaselist.do",
        dataType: "json",
        method: "POST",
        async: false,
        data: { projectId: projectId },
        success: function (exeResult) {
            if (exeResult.success) {
                _phases = exeResult.data;
            } else {
                top.alertMessage("获取分期数据出错，" + exeResult.message);
            }
        }
    });

    var datas = [];
    for (var i = 0; i < _phases.length; i++) {
        var treeAction = '<div ida="' + _phases[i].id + '" class="tree-actions">';
        /*if (_phases[i].enableRemovePhase) {
            treeAction += '<a href="#" class="remove-phase">删除</a>';
        }*/
        treeAction += '</div>';

        datas[i] = {
            name: _phases[i].phaseName + treeAction,
            type: 'item',
            extra: {
                id: _phases[i].id,
                type: "phase"
            }
        };
    }
    return datas;
}

function removePhase(phaseId) {
    listRemove(_baseUrl + "/phase/remove.do?id=" + phaseId);
}