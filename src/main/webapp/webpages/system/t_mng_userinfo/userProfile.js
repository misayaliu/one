function saveBaseInfo(){
	if($("#editusername").val()==""){
		CommonParam.tipsInput("#editusername",'请输入姓名');
		return false;
	}
	
	var url = 't_mng_userinfo/edit.do';
	var submitData = {};
	submitData = geneSubmitDataFromForm("#baseinfoForm",submitData);
	
	jQueryAjaxForJSON(url, submitData,
			function(sRet) {
				bootbox.alert("修改成功!", function() {
				});
			}, function(sRet) {
				bootbox.alert({message: sRet.field1});
			}, function() {
				bootbox.alert({message: '通讯异常'});
			},function(){
				window.parent.jzts();
			},function(){
				window.parent.hangge();
			});
}

function changePassword(){
	if($("#editsrcpassword").val()==""){
		CommonParam.tipsInput("#editsrcpassword",'请输入原密码');
		return false;
	}
	if($("#editpassword").val()==""){
		CommonParam.tipsInput("#editpassword",'请输入新密码');
		return false;
	}
	if($("#editconfirmpassword").val()==""){
		CommonParam.tipsInput("#editconfirmpassword",'请确认新密码');
		return false;
	}
	if($("#editpassword").val()!=$("#editconfirmpassword").val()){
		CommonParam.tipsInput("#editconfirmpassword",'新旧密码不一致');
		return false;
	}
	
	var url = 't_mng_userinfo/changePassword.do';
	var submitData = {};
	submitData = geneSubmitDataFromForm("#passwordForm",submitData);
	submitData['usercode'] = $("#editusercode").val();
	jQueryAjaxForJSON(url, submitData,
			function(sRet) {
				bootbox.alert("修改成功!", function() {
					window.top.location.href = basePath+'logout.do';
				});
			}, function(sRet) {
				bootbox.alert(sRet.field1, function() {
					window.top.location.href = basePath+'logout.do';
				});
			}, function() {
				bootbox.alert({message: '通讯异常'});
			},function(){
				window.parent.jzts();
			},function(){
				window.parent.hangge();
			});
}
