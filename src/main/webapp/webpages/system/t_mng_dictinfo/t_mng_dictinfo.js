function query(){
	window.parent.jzts();
	$("#searchForm").submit();
}

function add(){
	clearForm('#editForm');
	
	$("#editdicttype").removeAttr("readonly");
	$("#editdicttype").css("color","black");
	$("#editdictvalue").removeAttr("readonly");
	$("#editdictvalue").css("color","black");

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
	if($("#editdicttype").val()==""){
		CommonParam.tipsInput("#editdicttype",'请输入字典编号');
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
	var url = 't_mng_dictinfo/'+WEBOPERATION+'.do';
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

//修改
function edit(dicttype,dictvalue){
	WEBOPERATION = 'edit';
	var url = 't_mng_dictinfo/getEditData.do';
	var submitData = {};
	submitData['dicttype'] = dicttype;
	submitData['dictvalue'] = dictvalue;
	
	//一些额外的
	$("#editdicttype").attr("readonly","readonly");
	$("#editdicttype").css("color","gray");
	$("#editdictvalue").attr("readonly","readonly");
	$("#editdictvalue").css("color","gray");
	
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
function del(dicttype,dictvalue){
	//开始提交处理
	bootbox.confirm("确定要删除["+dicttype+","+dictvalue+"]吗?", function(result) {
		if(result) {
			var url = 't_mng_dictinfo/delete.do';
			var submitData = {};
			submitData['dicttype'] = dicttype;
			submitData['dictvalue'] = dictvalue;
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

/******一些校验Start******/
function isExistsWhenAdd(){
	var dicttype = $("#editdicttype").val();
	var dictvalue = $("#editdictvalue").val();
	if(dicttype==""){
		return;
	}
	if(dictvalue==""){
		return;
	}
	if(WEBOPERATION!='add'){
		return;
	}
	var url = "t_mng_dictinfo/isExistsWhenAdd.do?dicttype="+dicttype+"&dictvalue="+dictvalue;
	$.post(url,function(data){
		if(data=="error"){
			$("#editdictvalue").val('');
			CommonParam.tipsInput("#editdictvalue",dicttype+'对应的值:'+dictvalue+'已存在');
		}
	});
}
/******一些校验End******/