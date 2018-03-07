function query(){
	window.parent.jzts();
	$("#searchForm").submit();
}

//显示明细
function showDetail(systemid,logdate,logtime,userid,hostip,msg){
	var dialog = $( "#detail-dialog-message" ).removeClass('hide').dialog({
		modal: true,
		draggable:false,
		title: CommonParam.getDialogTitleCss('操作详细记录','fa-book'),
		position: ['right','top'],
		title_html: true,
		width:width1,
		height:height1,
		open: function (event, ui) {
            $(".ui-dialog-titlebar-close", $(this).parent()).hide();
        }
	});
	
	//查询明细记录
	var url = 't_mng_useroprlog/showdetail.do';
	var submitData = {};
	submitData['systemid'] = systemid;
	submitData['logdate'] = logdate;
	submitData['logtime'] = logtime;
	submitData['userid'] = userid;
	submitData['hostip'] = hostip;
	submitData['msg'] = msg;
	
	jQueryAjaxForJSON(url, submitData,
			function(sRet) {
				var data = sRet.field1;
				var htmldesc = "";
				$('#detail-dialog-message').html(htmldesc);
				htmldesc += "<table id='table_detail' class='table table-striped table-bordered table-hover'>";
				htmldesc += "<tr>";
				htmldesc += "<td style='width:15%'>用户姓名:</td>";
				htmldesc += "<td style='width:35%'>"+data['username']+"</td>";
				htmldesc += "<td style='width:15%'>终端IP:</td>";
				htmldesc += "<td style='width:35%'>"+data['hostip']+"</td>";
				htmldesc += "</tr>";
				htmldesc += "<tr>";
				htmldesc += "<td style='width:15%'>操作日期:</td>";
				htmldesc += "<td style='width:35%'>"+data['logdate']+"</td>";
				htmldesc += "<td style='width:15%'>操作时间:</td>";
				htmldesc += "<td style='width:35%'>"+data['logtime']+"</td>";
				htmldesc += "</tr>";
				htmldesc += "<tr>";
				htmldesc += "<td style='width:15%'>操作事件:</td>";
				htmldesc += "<td style='width:35%'>"+data['msg']+"</td>";
				htmldesc += "<td style='width:15%'></td>";
				htmldesc += "<td style='width:35%'></td>";
				htmldesc += "</tr>";
				htmldesc += "<tr>";
				htmldesc += "<td style='width:15%'>操作数据:</td>";
				htmldesc += "<td style='word-break:break-all' colspan = '3'>"+data['requestjsondata']+(data['requestjsondata1']==undefined?'':data['requestjsondata1'])+"</td>";
				htmldesc += "</tr>";
				htmldesc += "<tr>";
				htmldesc += "</table>";
				htmldesc += "<table id='table_report' class='table table-striped table-bordered table-hover'>";
				htmldesc += "<tr>";
				htmldesc += "<td style='text-align: center;' colspan='100'>";
				htmldesc += "<a class='btn btn-mini btn-danger' onclick='$(\"#detail-dialog-message\").dialog(\"close\");'><i class='fa fa-times'></i>关闭</a>";
				htmldesc += "</td>";
				htmldesc += "</tr>";
				htmldesc += "</table>";
				$('#detail-dialog-message').html(htmldesc);
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