CommonParam = function(){
}

COMMON_OPENNEWWINDOW_NOSTAT = "toolbar=no, menubar=no, scrollbars=no, resizable=no, "
    + "location=no, status=no,titlebar=no, width=200, "
    + "height=100, top=0, left=0";

CommonParam.successAlert = function(title,text){
	var id = $.gritter.add({
		 title: title,
		 text: text,
		 class_name: 'gritter-success'
    });
	
	//3秒钟就把这个提醒窗口给弄消失
	setTimeout(function(){
		$.gritter.remove(id, {
			fade: true,
			speed: 'slow'
		});
	},3000);
}

CommonParam.errorAlert = function(title,text){
	var id = $.gritter.add({
		 title: title,
		 text: text,
		 class_name: 'gritter-error'
    });
	
	//3秒钟就把这个提醒窗口给弄消失
	setTimeout(function(){
		$.gritter.remove(id, {
			fade: true,
			speed: 'slow'
		});
	},3000);
}

CommonParam.tipsInput = function(divid,msg){
	$(divid).tips({
		side:3,
        msg:msg,
        bg:'#AE81FF',
        time:2
    });
	
	$(divid).focus();
}

CommonParam.getDialogTitleCss = function(title,icon){
	return "<div class='widget-header widget-header-small'><h4 class='smaller blue'><i class='ace-icon fa "+icon+" pink'></i>&nbsp;"+title+"</h4></div>";
}