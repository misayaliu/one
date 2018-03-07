function query(){
	window.parent.jzts();
	$("#searchForm").submit();
}

function js_selectService() {
	var url = 't_chart_base/t_chart_baselistJsonAll.do';
	var submitData = {};
	jQueryAjaxForJSON(url, submitData, function(sRet) {
		var json = sRet.field1;
		var html = [];
		if (json.length > 0) {
			$.each(json, function() {
				html.push('<option value="' + this.cxfname + '">' + this.cxfdesc+ '</option>');
			});
			$("#callservicebean").empty();
			$("#callservicebean").append(html);
		}

	}, function(sRet) {
		bootbox.alert(sRet.field1);
	}, function() {
		bootbox.alert('通讯异常');
	}, function() {
		window.parent.jzts();
	}, function() {
		window.parent.hangge();
	});
}


function add(){
	clearForm('#editForm');
	$("#callservicebean").attr("disabled",false);
	$("#chart_id").removeAttr("readonly");
	$("#chart_id").css("color","black");
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
	$("#callservicebean").attr("disabled",false);
	//必填项没填的
	
	if($("#text").val()==""){
		CommonParam.tipsInput("#text",'请输入标题');
		return false;
	}
	if($("#subtext").val()==""){
		CommonParam.tipsInput("#subtext",'请输入副标题');
		return false;
	}
	if($("#chart_sql").val()==""){
		CommonParam.tipsInput("#chart_sql",'请输入SQL语句');
		return false;
	}
	
	//开始提交处理
	var url = 't_chart_base/'+WEBOPERATION+'.do';
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



function edit(chart_id){
	WEBOPERATION = 'edit';
	var url = 't_chart_base/getEditData.do';
	var submitData = {};
	submitData['chart_id'] = chart_id;
	//submitData['callservicebean'] = callservicebean;
	//一些额外的
	$("#chart_id").attr("readonly","readonly");
	$("#chart_id").css("color","gray");
	
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
				$("#callservicebean").attr("disabled",true);
			}, function(sRet) {
				bootbox.alert({message: sRet.field1});
			}, function() {
				bootbox.alert({message: '通讯异常'});
			});
}

//删除
function del(chart_id){
	//开始提交处理
	bootbox.confirm("确定要删除该条信息吗?", function(result) {
		if(result) {
			var url = 't_chart_base/delete.do';
			var submitData = {};
			submitData['chart_id'] = chart_id;
			//submitData['callservicebean'] = callservicebean;
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

