function query(){
	window.parent.jzts();
	$("#searchForm").submit();
}

function js_select(param) {
	var url = 't_chart_base/t_chart_baseListJson.do';
	var submitData = {};
	jQueryAjaxForJSON(url, submitData, function(sRet) {
		var field1 = sRet.field1;
		var html = [];
		var selecthtml = [];
		var json = jQuery.parseJSON(field1);
		for(var i=0;i<json.length;i++){
			if(param == this.chart_id){	
				selecthtml.push('<option selected="selected" value="' + json[i]['chart_id'] + '">' + json[i]['text']+ '</option>');
			}else{
				selecthtml.push('<option value="' + json[i]['chart_id'] + '">' + json[i]['text']+ '</option>');
			}
			
			
			html.push('<option value="' + json[i]['chart_id'] + '">' + json[i]['text']+ '</option>');
		}
		$("#searchchart_id").eq(0).nextAll().remove();
		$("#searchchart_id").append(selecthtml);
		$("#chart_id").empty();
		$("#chart_id").append(html);
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
	$("#chart_column_id").removeAttr("readonly");
	$("#chart_column_id").css("color","black");
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
	if($("#column_id").val()==""){
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
	var url = 't_chart_column/'+WEBOPERATION+'.do';
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



function edit(chart_column_id){
	WEBOPERATION = 'edit';
	var url = 't_chart_column/getEditData.do';
	var submitData = {};
	submitData['chart_column_id'] = chart_column_id;
	//一些额外的
	$("#chart_column_id").attr("readonly","readonly");
	$("#chart_column_id").css("color","gray");
	
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
function del(chart_column_id){
	//开始提交处理
	bootbox.confirm("确定要删除该条信息吗?", function(result) {
		if(result) {
			var url = 't_chart_column/delete.do';
			var submitData = {};
			submitData['chart_column_id'] = chart_column_id;
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
