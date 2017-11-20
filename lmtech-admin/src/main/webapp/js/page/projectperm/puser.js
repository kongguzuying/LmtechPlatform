$(function(){
  function onPageIndexClick(pageIndex) {
        var action = $("form").attr("action");
        action = action.replace("check.do", "checklist.do");
        $("form").attr("action", action);
        submitPageForm();
    }
	  
	//
	$('#searchBtn').click(function(){
		 var action = $("form").attr("action");
	     action = action.replace("check.do", "checklist.do");
	     $("form").attr("action", action);
	     submitPageForm();
	});
});