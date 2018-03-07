function query(){
	window.parent.jzts();
	$("#searchForm").submit();
}

function js_selectreportBase(param) {
	var url = 'treportrpbase/treportrpbaseListJson.do';
	var submitData = {};
	jQueryAjaxForJSON(url, submitData, function(sRet) {
		var json = sRet.field1;
		var html = [];
		var selecthtml = [];
		if (json.length > 0) {
			$.each(json, function() {
				if(param == this.report_id){	
					selecthtml.push('<option selected="selected" value="' + this.report_id + '">' + this.report_name+ '</option>');
				}else{
					selecthtml.push('<option value="' + this.report_id + '">' + this.report_name+ '</option>');
				}
				
				
				html.push('<option value="' + this.report_id + '">' + this.report_name+ '</option>');
			});
			$("#searchreport_id").eq(0).nextAll().remove();
			$("#searchreport_id").append(selecthtml);
			$("#report_id").empty();
			$("#report_id").append(html);
			
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
	$("#callservicebean").attr("disabled",false);
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
	var url = 't_report_outkey/'+WEBOPERATION+'.do';
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



function edit(outkeyid){
	WEBOPERATION = 'edit';
	var url = 't_report_outkey/getEditData.do';
	var submitData = {};
	submitData['outkeyid'] = outkeyid;
	//一些额外的
	$("#outkeyid").attr("readonly","readonly");
	$("#outkeyid").css("color","gray");
	
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
function del(outkeyid){
	//开始提交处理
	bootbox.confirm("确定要删除该条信息吗?", function(result) {
		if(result) {
			var url = 't_report_outkey/delete.do';
			var submitData = {};
			submitData['outkeyid'] = outkeyid;
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
