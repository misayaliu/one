<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="/webpages/tld/c.tld"%>
<%@ page isELIgnored="false"%>

<%
String path = request.getContextPath();
String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">
<title>捷羿报表系统</title>
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<%@ include file="/headincludepage.jsp"%>
<link rel="stylesheet" type="text/css" media="screen"
	href="selfjs/combogrid/css/jquery-ui-1.10.1.custom.css" />
<link rel="stylesheet" type="text/css" media="screen"
	href="selfjs/combogrid/css/jquery.ui.combogrid.css" />

<style>
td {
	line-height: 30px;
	margin-left: 11px;
}
.ac_results{
	max-width:170px;
}
.ac_results ul {
	margin-left: 0;

}

.ac_results ul span {
	font-size: 15px;
	
}
.ac_results ul li {
	border-bottom: 1px solid #dddddd;
	border-left: 1px solid #dddddd;
	border-right: 1px solid #dddddd;
	
	
}
.ac_result ul li :hover {
background-color: red;
}
/* .autocpmplete{border-collapse:collapse; font-size:13px;line-height:24px; color:#09F; text-align:center;}
/* .autocpmplete tr th{background:#36F; color:#FFF; font-size:13px; height:24px; line-height:24px;}
.autocpmplete tr th.th_border{border-right:solid 1px #FFF; border-left:solid 1px #36F;}
.autocpmplete tr td{border:solid 1px #36F;} */
* /
.autocpmplete tr:hover {
	background-color: red;
}
</style>
</head>

<body>
	<div class="main-container" id="main-container">
		<script type="text/javascript">
				try{ace.settings.check('main-container' , 'fixed')}catch(e){}
			</script>

		<!-- /section:basics/sidebar -->
		<div class="main-content">
			<div class="main-content-inner">

				<!-- /section:basics/content.breadcrumbs -->
				<div class="page-content">
					<form id="searchForm" name="searchForm" target="ifreport"
						method="POST" action="">
						<input type="hidden" id="method" name="method" value="query" /> <input
							type="hidden" name="report_id" value="${pd.report_id}" />
						<table class="table table-striped table-bordered table-hover">
							<tr>
								<c:forEach items="${querys}" var="reportRpQuery" varStatus="vs">
									<input type="hidden" name="input_typeName"
										value="${reportRpQuery.f_eng}" />
									<input type="hidden" name="input_typeName1"
										value="${reportRpQuery.f_eng}1" />
									<input type="hidden" name="input_typeType"
										value="${reportRpQuery.input_type}" />
									<input type="hidden" name="input_typeNameChn"
										value="${reportRpQuery.f_chn}" />
									<input type="hidden" name="input_typeis_need"
										value="${reportRpQuery.is_need}" />
									<input type="hidden" name="query_domains"
										value="${reportRpQuery.query_domains}" />
									<input type="hidden" name="query_domain_count"
										value="${reportbase.query_domain_count}" />
									<input type="hidden" name="out_key_name"
										value="${reportRpQuery.out_key_name}" />
									<c:choose>

										<c:when test="${reportRpQuery.input_type==1}">
											<td>
												${reportRpQuery.f_chn}:
											</td>
											<td> 
												<div class="row">
													<div class="col-sm-12">
														<input class="form-control" type="text" size="22" name="${reportRpQuery.f_eng}" placeholder="这里输入${reportRpQuery.f_chn}" />
													</div>
												</div>
											</td>
										</c:when>
										<c:when test="${reportRpQuery.input_type==2}">
											<td>
												${reportRpQuery.f_chn}: 
											</td>
											<td>
												<div class="row">
													<div class="col-sm-12">
														<input class="form-control date-picker" name="${reportRpQuery.f_eng}" id="${reportRpQuery.f_eng}" type="text"/>
													</div>
												</div>
												
											</td>
										</c:when>
										<c:when test="${reportRpQuery.input_type==3}">
											<td>
												${reportRpQuery.f_chn}: 
											</td>
											<td>
												<div class="row">
													<div class="col-sm-5">
														<input class="form-control date-picker" name="${reportRpQuery.f_eng}_start" id="${reportRpQuery.f_eng}_start" type="text"/>
													</div>
													<div class="col-sm-1">
														至
													</div>
													<div class="col-sm-5">
														<input class="form-control date-picker" name="${reportRpQuery.f_eng}_end" id="${reportRpQuery.f_eng}_end" type="text"/>
													</div>
												</div>
											</td>
										</c:when>
										<c:when test="${reportRpQuery.input_type==4 or reportRpQuery.input_type==5 or reportRpQuery.input_type==6}">
											<td>
												${reportRpQuery.f_chn}: 
											</td>
											<td>
												<div class="row">
													<div class="col-sm-6">
														<input type="text" name="${reportRpQuery.f_eng}1" id="${reportRpQuery.f_eng}" placeholder="这里单击或输入值获取值" size=34"/>
													</div>
													<div class="col-sm-1">
														<input type="text" name="${reportRpQuery.f_eng}" id="${reportRpQuery.f_eng}1" readonly size=18"/>
													</div>
												</div>
											</td>
										</c:when>
									</c:choose>
									<c:if test="${(vs.index+1)%2==0}">
										</tr>
										<tr>
									</c:if>
								</c:forEach>
							</tr>
							<tr>
								<td colspan="6" class="center"><span class="btn btn-purple btn-sm tooltip-info"
									data-rel="tooltip" onclick="query(document.searchForm);"
									data-placement="bottom"><i
										class="ace-icon fa fa-search bigger-100"></i>查询</span> <span
									class="btn btn-green btn-sm tooltip-info" data-rel="tooltip"
									onclick="printWatch(document.searchForm);"
									data-placement="bottom"><i
										class="ace-icon fa fa-print bigger-100"></i>打印预览</span>
									<span class="btn btn-pink btn-sm tooltip-info" data-rel="tooltip"
									onclick="excel(document.searchForm);"
									data-placement="bottom"><i
										class="ace-icon fa fa-download bigger-100"></i>下载</span>
								</td>
							</tr>
						</table>

					</form>

					<div class="row">
							<div class="col-xs-12" style="overflow-x:scroll;" id="reportTable"></div>
							</div>
					</div>
				</div>
			</div>
		</div>


	<iframe name="ifreport" width="0" height="0" frameBorder="0"
		marginWidth="0" marginHeight="0"></iframe>
	<center>
		<div id="load-ing-common" style="display: none;">处理中,请等候...</div>
	</center>
	<%@ include file="/tailincludepage.jsp"%>

	<!-- <script type="text/javascript"
	src="selfjs/combogrid/plugin/jquery.ui.combogrid-1.6.3.js"></script> -->
	<script type="text/javascript">
	

		var actionUrl='report/';
		function query(form){
			//开始提交处理
			var method='query.do'
			var url = actionUrl+method;
			var submitData = {};
			submitData = geneSubmitDataFromForm("#searchForm",submitData);
			
			var queryString = $('#searchForm').serialize(); 
			
			$("#load-ing-common").show();
			jQueryAjaxForJSON(url, queryString,
				function(sRet) {
					//CommonParam.successAlert('成功','添加记录成功');
					
				    $("#reportTable").html(sRet.field1);
				}, function(sRet) {
					$("#reportTable").html(sRet.field1);
					//bootbox.alert(sRet.field1);
				}, function() {
					//bootbox.alert('通讯异常');
				},function(){
					
				},function(){
					$("#load-ing-common").hide();
				});
			
			return;
		}

		function printWatch(form){
			$("#method").val("printWatch");
			var method='printWatch.do';
			form.action=actionUrl+method;
			form.target="_blank";
			form.submit();
		}
		
		function excel(form){
			$("#method").val("excel");
			var method='excel.do';
			form.action=actionUrl+method;
			form.target="_blank";
			form.submit();
		}
	
		function pdf(form){
			var method='excel';
			form.action=actionUrl+method;
			$("#method").val("pdf");
			form.target="_blank";
			form.submit();
		}

		$(document).ready(function() { 
			//var priviVagueValue = '11000002';//注意：：：此处后面的值，请在各个系统里面自行使用该系统所需要的值赋值：：：注意
			//var priviVagueValueDesc = '东莞通';//注意：：：此处后面的值，请在各个系统里面自行使用该系统所需要的值赋值：：：注意
			var priviVagueValue = '';//注意：：：此处后面的值，请在各个系统里面自行使用该系统所需要的值赋值：：：注意
			var priviVagueValueDesc = '';//注意：：：此处后面的值，请在各个系统里面自行使用该系统所需要的值赋值：：：注意
			<c:forEach items="${querys}" var="reportRpQuery">
				<c:if test="${reportRpQuery.input_type==4}">
					$("#${reportRpQuery.f_eng}")
					.autocomplete({
						minLength: 1,
						source: 'vague/getVague.do?out_key_name=${reportRpQuery.out_key_name}',
						success: function( data ) {
							response(data);
			        	},
			        	select: function(event, ui) {
			        		$("#${reportRpQuery.f_eng}1").val(ui.item.postvalue);
			        	}
					})
					.keyup(function(){
						if($("#${reportRpQuery.f_eng}").val()==''){
							$("#${reportRpQuery.f_eng}1").val('');
						}
					})
					.click(function() {
						$("#${reportRpQuery.f_eng}").autocomplete("option", "minLength", 0);//
					})
					;
					
				</c:if>
				<c:if test="${reportRpQuery.input_type==5}">
					if(priviVagueValue==null||priviVagueValue==''){
						//查询所有的
						$("#${reportRpQuery.f_eng}").removeAttr("readonly");
						$("#${reportRpQuery.f_eng}")
						.autocomplete({
							minLength: 1,
							source: 'vague/getVague.do?out_key_name=${reportRpQuery.out_key_name}',
							success: function( data ) {
								response(data);
				        	},
				        	select: function(event, ui) {
				        		$("#${reportRpQuery.f_eng}1").val(ui.item.postvalue);
				        	}
						})
						.keyup(function(){
							if($("#${reportRpQuery.f_eng}").val()==''){
								$("#${reportRpQuery.f_eng}1").val('');
							}
						})
						.click(function() {
							$("#${reportRpQuery.f_eng}").autocomplete("option", "minLength", 0);//
						})
						;
					}else{
						//有权限的
						$("#${reportRpQuery.f_eng}1").val(priviVagueValue);
						if(priviVagueValueDesc == null||priviVagueValueDesc == ''){
							var url = 'vague/getPriviVague.do?out_key_name=${reportRpQuery.out_key_name}&priviVagueValue='+priviVagueValue;
							$.post(url,function(data){
								$("#${reportRpQuery.f_eng}").val(data);
								
							});
						}else{
							$("#${reportRpQuery.f_eng}").val(priviVagueValueDesc);
						}
						$("#${reportRpQuery.f_eng}").attr("readonly","readonly");
					}
				</c:if>
				<c:if test="${reportRpQuery.input_type==6}">
					$("#${reportRpQuery.f_eng}")
					.autocomplete({
						minLength:1,
						//source: 'vague/getVague.do?out_key_name=${reportRpQuery.out_key_name}&fathervalue='+$('#${reportRpQuery.sp_data}').val(),
						source:function(request, response) { 
							var fathervalue = $('#${reportRpQuery.sp_data}1').val();
							var term = $("#${reportRpQuery.f_eng}").val();
							term = encodeURIComponent(term);
							$.ajax({ 
								type: "post",
								url: 'vague/getCasVague.do?out_key_name=${reportRpQuery.out_key_name}&fathervalue='+fathervalue+'&term='+term,
								dataType: "json", 
								success: function(data) {
									//$("#${reportRpQuery.f_eng}").autocomplete(data);
									response(data);
								}
							});
						},
			        	select: function(event, ui) {
			        		$("#${reportRpQuery.f_eng}1").val(ui.item.postvalue);
			        	}
					})
					.keyup(function(){
						if($("#${reportRpQuery.f_eng}").val()==''){
							$("#${reportRpQuery.f_eng}1").val('');
						}
					})
					.click(function() {
						$("#${reportRpQuery.f_eng}").autocomplete("option", "minLength", 0);//
					})
					;
					
				</c:if>
			</c:forEach>
		});
		
		$('.date-picker').datepicker({
			autoclose: true,
			todayHighlight: true,
		})
	</script>
</body>
</html>
