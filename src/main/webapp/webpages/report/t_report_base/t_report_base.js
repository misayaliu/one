function query(){
	window.parent.jzts();
	$("#searchForm").submit();
}

function add(){
	clearForm('#editForm');
	$("#mchnt_id").removeAttr("readonly");
	$("#mchnt_id").css("color","black");
	var dialog = $( "#dialog-message" ).removeClass('hide').dialog({
		modal: true,
		draggable:false,
		title: CommonParam.getDialogTitleCss('新增','fa-plus'),
		position: ['right','top'],
		title_html: true,
		width:width1,
		height:height1,
		open: function (event, ui) {
            $(".ui-dialog-titlebar-close", $(this).parent()).hide();
        }
	});
	WEBOPERATION = 'add';
}

function save(){
	//必填项没填的
	if($("#mchnt_id").val()==""){
		CommonParam.tipsInput("#mchnt_id",'请输入字典编号');
		return false;
	}
	
	if($("#editdicttypedesc").val()==""){
		CommonParam.tipsInput("#editdicttypedesc",'请输入字典编号含义');
		return false;
	}
	if($("#editdictvalue").val()==""){
		CommonParam.tipsInput("#editdictvalue",'请输入字典值');
		return false;
	}
	if($("#editdictvaluedesc").val()==""){
		CommonParam.tipsInput("#editdictvaluedesc",'请输入字典值含义');
		return false;
	}
	
	//开始提交处理
	var url = 't_report_base/'+WEBOPERATION+'.do';
	var submitData = {};
	submitData = geneSubmitDataFromForm("#editForm",submitData);
	
	jQueryAjaxForJSON(url, submitData,
		function(sRet) {
			//CommonParam.successAlert('成功','添加记录成功');
			$('#dialog-message').dialog('close');
			bootbox.alert("操作成功!", function() {
				query();
			});
		}, function(sRet) {
			bootbox.alert(sRet.field1);
		}, function() {
			bootbox.alert('通讯异常');
		},function(){
			window.parent.jzts();
		},function(){
			window.parent.hangge();
		});
}



function edit(report_id){
	WEBOPERATION = 'edit';
	var url = 't_report_base/getEditData.do';
	var submitData = {};
	submitData['report_id'] = report_id;
	//一些额外的
	$("#report_id").attr("readonly","readonly");
	$("#report_id").css("color","gray");
	
	jQueryAjaxForJSON(url, submitData,
			function(sRet) {
				//alert(sRet.field1['systemid']+sRet.field1['systemname']);
				var dialog = $( "#dialog-message" ).removeClass('hide').dialog({
					modal: true,
					draggable:false,
					title: CommonParam.getDialogTitleCss('修改','fa-edit'),
					position: ['right','top'],
					title_html: true,
					width:width1,
					height:height1,
					open: function (event, ui) {
			            $(".ui-dialog-titlebar-close", $(this).parent()).hide();
			        }
				});
				loadDataForForm("#editForm",sRet.field1);
			}, function(sRet) {
				bootbox.alert({message: sRet.field1});
			}, function() {
				bootbox.alert({message: '通讯异常'});
			});
}

//删除
function del(report_id){
	//开始提交处理
	bootbox.confirm("确定要删除该条信息吗?", function(result) {
		if(result) {
			var url = 't_report_base/delete.do';
			var submitData = {};
			submitData['report_id'] = report_id;
			jQueryAjaxForJSON(url, submitData,
					function(sRet) {
						//CommonParam.successAlert('成功','添加记录成功');
						bootbox.alert("操作成功!", function() {
							query();
						});
					}, function(sRet) {
						bootbox.alert({message: sRet.field1});
					}, function() {
						bootbox.alert({message: '通讯异常'});
					});
		}
	});
}

//更改用户状态
function change_userstate(id,userid){
	var nextstate = $("#"+id).prop("checked");
	if(nextstate == false){
		//让状态失效
		value = '1';
	}else{
		//让状态生效
		value = '0';
	}
	var url = "t_rel_mchntuser/changeUserState.do?userid="+userid+"&state="+value;
	$.post(url,function(data){
		if(data=="success"){
			CommonParam.successAlert('成功','更改用户状态成功');
		}else{
			CommonParam.errorAlert('错误','更改用户状态失败');
			if(nextstate == 'checked'){
				//让状态生效失败
				$("#"+id).removeAttr("checked");
			}else{
				//让状态失效失败
				$("#"+id).attr("checked",'true');
			}
		}
	}).error(function(){
		CommonParam.errorAlert('错误','请求失败，请查看网络');
		if(nextstate == 'checked'){
			//让状态生效失败
			$("#"+id).removeAttr("checked");
		}else{
			//让状态失效失败
			$("#"+id).attr("checked",'true');
		}
	});
}

//密码错误次数清0
function clearPwdErrTimes(userid){
	bootbox.confirm("次数清0之后，用户可以再次登录系统，确定吗?", function(result) {
		if(result) {
			var url = "t_rel_mchntuser/clearPwdErrTimes.do?userid="+userid;
			$.post(url,function(data){
				if(data=="success"){
					CommonParam.successAlert('成功','更改用户状态成功');
					query();//重新查询下
				}else{
					CommonParam.errorAlert('错误','更改用户状态失败');
				}
			}).error(function(){
				CommonParam.errorAlert('错误','请求失败，请查看网络');
			});
		}
	});
}

//分配用户角色
function signRoles(userid){
	//clearForm('#signrightsForm');
	$("#signrolessuserid").val(userid);

	var dialog = $( "#signroless-dialog-message" ).removeClass('hide').dialog({
		modal: true,
		draggable:false,
		title: CommonParam.getDialogTitleCss('分配角色','fa-users'),
		title_html: true,
		width:width1,
		height:height1,
		open: function (event, ui) {
            $(".ui-dialog-titlebar-close", $(this).parent()).hide();
        }
	});
	
	//刷新jqgrid的数据
	var submitData = {};
	submitData['userid'] = userid;
	$("#grid-table").jqGrid('setGridParam',{
		url:'t_rel_mchntuser/selectForRolesForSign.do',
		datatype:'json',
		postData:submitData//发送数据
	}).trigger("reloadGrid"); // 重新载入  
	
}

//保存用户的角色分配
function savesignroless(){
	var selects = $("#grid-table").jqGrid('getGridParam','selarrrow');
	if(selects.length==0){
		CommonParam.errorAlert('错误','至少选择一个角色');
	}
	
	var obj = $("#grid-table").jqGrid("getRowData");
	var str = '';
	for(var i=0;i<selects.length;i++)
	{
	  	if(str=='') str += obj[selects[i]-1]['roleid'];
	  	else str += ',' + obj[selects[i]-1]['roleid'];
	}
	
	var url = 't_rel_mchntuser/saveSignRoles.do';
	var userid = $("#signrolessuserid").val();
	var submitData = {};
	submitData['userid'] = userid;
	submitData['roleids'] = str;
	jQueryAjaxForJSON(url, submitData,
			function(sRet) {
				$('#signroless-dialog-message').dialog('close');
				bootbox.alert("操作成功!");
			}, function(sRet) {
				bootbox.alert({message: sRet.field1});
			}, function() {
				bootbox.alert({message: '通讯异常'});
			});
}

//显示修改密码的窗口
function showResetPwd(userid,usercode){
	$("#resetpwduserid").val(userid);
	$("#resetpwdusercode").val(usercode);
	
	var dialog = $( "#resetpwd-dialog-message" ).removeClass('hide').dialog({
		modal: true,
		draggable:false,
		title: CommonParam.getDialogTitleCss('重置密码','fa-undo'),
		title_html: true,
		width:400,
		height:270,
		open: function (event, ui) {
            $(".ui-dialog-titlebar-close", $(this).parent()).hide();
        }
	});
}

//重置密码
function resetpwd(){
	if($("#resetpwdpassword").val()==""){
		CommonParam.tipsInput("#resetpwdpassword",'请输入新密码');
		return false;
	}
	if($("#resetpwdconfirmpassword").val()==""){
		CommonParam.tipsInput("#resetpwdconfirmpassword",'请确认新密码');
		return false;
	}
	if($("#resetpwdpassword").val()!=$("#resetpwdconfirmpassword").val()){
		CommonParam.tipsInput("#resetpwdconfirmpassword",'新旧密码不一致');
		return false;
	}
	
	var url = 't_rel_mchntuser/resetPassword.do';
	var submitData = {};
	submitData = geneSubmitDataFromForm("#resetpwdForm",submitData);
	jQueryAjaxForJSON(url, submitData,
			function(sRet) {
				$('#resetpwd-dialog-message').dialog('close');
				bootbox.alert("重置成功!");
			}, function(sRet) {
				bootbox.alert(sRet.field1);
			}, function() {
				bootbox.alert({message: '通讯异常'});
			},function(){
				window.parent.jzts();
			},function(){
				window.parent.hangge();
			});
}

/******一些校验Start******/
/**
 * 用户编号
 * */
function isExistsNameWhenAdd(){
	var usercode = $("#editusercode").val();
	if(usercode==""){
		return;
	}
	if(WEBOPERATION!='add'){
		return;
	}
	var url = "t_rel_mchntuser/isExistsNameWhenAdd.do?usercode="+usercode;
	$.post(url,function(data){
		if(data=="error"){
			$("#editusercode").val('');
			CommonParam.tipsInput("#editusercode",'登录名:'+usercode+'已存在');
		}
	});
}

/**
 * 操作员ID
 * */
function isExistsOperidWhenAdd(){
	var operatorid = $("#editoperatorid").val();
	if(operatorid==""){
		return;
	}
	if(WEBOPERATION!='add'){
		return;
	}
	var url = "t_rel_mchntuser/isExistsOperidWhenAdd.do?operatorid="+operatorid;
	$.post(url,function(data){
		if(data=="error"){
			$("#editoperatorid").val('');
			CommonParam.tipsInput("#editoperatorid",'操作员编号:'+operatorid+'已存在');
		}
	});
}
/******一些校验End******/