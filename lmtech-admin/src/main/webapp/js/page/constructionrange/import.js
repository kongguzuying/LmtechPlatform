	var cids = "";
	$("#contractorTable input[type='checkbox']").click(function(){
		var $this = $(this);
		var isAll = $this.attr("id")=="chkAll"?true:false;
		var checked = $this.attr("checked")?true:false;
		
		var $allSelect = $("#contractorTable input[type='checkbox'][checked='checked'][cid]");
		var $allChk = $("#contractorTable input[type='checkbox'][cid]");

		cids = "";
		if(isAll){
			$allChk.attr("checked",checked);
			$allSelect = $("#contractorTable input[type='checkbox'][checked='checked'][cid]");
		}else{
			if(!checked){
				$("#chkAll").attr("checked",false);
			}
			$this.attr("checked",checked);
			$allSelect = $("#contractorTable input[type='checkbox'][checked='checked'][cid]");
			//console.error($allSelect.length);
			//console.error($allChk.length);
			if($allChk.length == $allSelect.length){
				$("#chkAll").attr("checked",true);
			}else if($allSelect.length == 0){
				$("#chkAll").attr("checked",false);
			}
		}
		$allSelect.each(function(i,o){		
			if(cids.length > 0){
				cids+=",";
			}
			cids += $(o).attr("cid")+"__" + $(o).attr("cname");
		});
	});