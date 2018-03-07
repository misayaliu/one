<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="jy" uri="/webpages/tld/jyTag.tld"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
<base href="<%=basePath%>">
<meta charset="utf-8" />
<title>用户管理</title>

<%@ include file="/headincludepage.jsp"%>
</head>
<body class="no-skin">

	<!-- /section:basics/navbar.layout -->
	<div class="main-container" id="main-container">
		<script type="text/javascript">
				try{ace.settings.check('main-container' , 'fixed')}catch(e){}
			</script>

		<!-- /section:basics/sidebar -->
		<div class="main-content">
			<div class="main-content-inner">
				<!-- #section:basics/content.breadcrumbs -->
				<div class="breadcrumbs" id="breadcrumbs">
					<script type="text/javascript">
							try{ace.settings.check('breadcrumbs' , 'fixed')}catch(e){}
						</script>

					<ul class="breadcrumb">
						<li><i class="ace-icon fa fa-home home-icon"></i> <a
							href="login_mainframe.do" target="_top">主页</a></li>
						<li class="active">报表基本信息配置</li>
					</ul>
					<!-- /.breadcrumb -->
				</div>

				<!-- /section:basics/content.breadcrumbs -->
				<div class="page-content">
					<!-- 检索  -->
					<form action="t_report_base/listPages.do" method="post"
						name="searchForm" id="searchForm">
						<table>
							<tr>
								<td>
								<input type="hidden" name="sortString" value="report_id"/>
								<span class="input-icon"> <input
										autocomplete="off" type="text" name="searchword"
										value="${pd.searchword}" placeholder="这里输入关键词"
										id="form-field-icon-1" /><i
										class="ace-icon fa fa-search blue"></i>
								</span></td>
								<td style="vertical-align: top;"><span
									class="btn btn-purple btn-sm tooltip-info" data-rel="tooltip"
									onclick="query();" data-placement="bottom">查询</span> <span
									class="btn btn-success btn-sm tooltip-info" data-rel="tooltip"
									onclick="add();" data-placement="bottom">新增</span></td>
							</tr>
						</table>
						<!-- 检索  -->
					</form>

					<div class="row">
						<div class="col-xs-12">
							<!-- PAGE CONTENT BEGINS -->
							<div class="row">
								<div class="col-xs-12">
									<table id="sample-table-1"
										class="table table-striped table-bordered table-hover">
										<thead>
											<tr>
												<th class="center">
													<label class="position-relative">
														<input type="checkbox"  id="zcheckbox" disabled="disabled" class="ace" />
														<span class="lbl"></span>
													</label>
												</th>
												<th>序号</th>
												<th>报表ID</th>
												<th>报表名称</th>
												<th>英文简称</th>
												<th>是否使用自定义表头</th>
												<th>是否有序号</th>
												<th>报表状态</th>
												<th></th>
											</tr>
										</thead>

										<tbody>
											<!-- 开始循环 -->
											<c:choose>
												<c:when test="${not empty dataList}">
													<c:forEach items="${dataList}" var="data" varStatus="vs">

														<tr>
															<td class='center' style="width: 30px;">
																<label>
																	<input type='checkbox' disabled="disabled" class="ace" />
																	<span class="lbl"></span>
																</label>
															</td>
															<td class='center' style="width: 50px;">${vs.index+1}</td>
															<td><fmt:formatNumber value="${data.report_id}" pattern="#0"/></td>
															<td>${data.report_name}</td>
															<td style="width: 60px;">${data.short_eng}</td>
															<td><c:if test="${data.need_header == '1'}">
																	<span class="label label-important arrowed-in">否</span>
																</c:if> <c:if test="${data.need_header == '2'}">
																	<span class="label label-success arrowed">是</span>
																</c:if></td>
															<td><c:if test="${data.has_seq == '1'}">
																	<span class="label label-important arrowed-in">否</span>
																</c:if> <c:if test="${data.has_seq == '2'}">
																	<span class="label label-success arrowed">是</span>
																</c:if></td>
															<td><c:if test="${data.status == '0'}">
																	<span class="label label-important arrowed-in">禁用</span>
																</c:if> <c:if test="${data.status == '1'}">
																	<span class="label label-success arrowed">启用</span>
																</c:if></td>

															<td>
																<div class="hidden-sm hidden-xs btn-group">
																	<a class='btn btn-xs btn-info' title="编辑"
																		onclick="edit('${data.report_id}')"><i
																		class='ace-icon fa fa-pencil bigger-120'></i></a> <a
																		class='btn btn-xs btn-danger' title="删除"
																		onclick="del('${data.report_id}')"><i
																		class='ace-icon fa fa-trash-o bigger-120'></i></a>
																</div>


															</td>
														</tr>

													</c:forEach>
												</c:when>
												<c:otherwise>
													<tr class="main_info">
														<td colspan="14" class="center"><c:if
																test="${loadPage == '0' }">
															   	 	请根据需要查询数据
															    </c:if> <c:if test="${loadPage == '1' }">
															   	 	没有相关数据
															    </c:if></td>
													</tr>
												</c:otherwise>
											</c:choose>
										</tbody>
									</table>

									<div style="float: right; padding-top: 0px; margin-top: 0px;">
										${pageStr}</div>
								</div>
								<!-- /.span -->
							</div>
							<!-- /.row -->
						</div>
						<!-- /.page-content -->
					</div>
				</div>
			</div>
		</div>
		<!-- /.main-content -->

		<a href="#" id="btn-scroll-up"
			class="btn-scroll-up btn btn-sm btn-inverse"> <i
			class="ace-icon fa fa-angle-double-up icon-only bigger-110"></i>
		</a>
	</div>
	<!-- /.main-container -->

	<div id="dialog-message" class="hide">
		<form name="editForm" id="editForm">
			<input type="hidden" name="report_id" id="report_id" />
			<table id="table_report"
				class="table table-striped table-bordered table-hover">
				<tr>
					<td style="width: 15%">报表名称:</td>
					<td>
					<input type="text" id="report_name"  name="report_name" style="width: 90%"/>

					</td>
					<td style="width: 15%">英文简称:</td>
					<td><input type="text" name="short_eng" id="short_eng"
						placeholder="这里输入英文简称" style="width: 90%" title="英文简称"
					/></td>
					<td style="width: 15%">报表状态:</td>
					<td><jy:select type="select" name="status"
							id="status" typeName="99003" /></td>
				</tr>
				<tr>
					<td style="width: 15%">是否有序号:</td>
					<td><jy:select type="select" name="has_seq"
							id="has_seq" typeName="99013" /></td>
					<td style="width: 15%">是否使用自定义表头:</td>
					<td><jy:select type="select" name="need_header"
							id="need_header" typeName="99013" /></td>
					<td style="width: 15%">是否扩展:</td>
					<td><jy:select type="select" name="is_extend"
							id="is_extend" typeName="99013" /></td>
				</tr>
				<tr>
					<td style="width: 15%">查询域数:</td>
					<td><input type="text" name="query_domain_count" id="query_domain_count"
						placeholder="这里输入是否扩展" style="width: 90%" value="1"/></td>
					<td style="width: 15%">是否需要批次号:</td>
					<td><jy:select type="select" name="need_batch_no"
							id="need_batch_no" typeName="99013" /></td>
				   <td style="width: 15%">是否需要格式化:</td>
					<td><jy:select type="select" name="need_format"
							id="need_format" typeName="99013" /></td>
				</tr>
				<tr style="height: 80px;">
					<td style="width: 15%; height: 50px;">自定义表头:</td>
					<td colspan="5" style="height: 50px;"><textarea rows="5"
							name="header" id="header" placeholder="这里输入自定义表头"
							style="width: 90%" title="自定义表头" 
							></textarea></td>
				</tr>
				<tr>
					<td style="width: 15%; height: 50px;">SQL语句:</td>
					<td style="height: 50px;" colspan="5"><textarea rows="5"
							name="sqls" id="sqls" placeholder="这里输入SQL语句"
							style="width: 90%" title="SQL语句"
							></textarea></td>
				</tr>
			</table>

			<table id="table_report"
				class="table table-striped table-bordered table-hover">
				<tr>
					<td style="text-align: center;" colspan="100"><a
						class="btn btn-mini btn-primary" onclick="save();"><i
							class="fa fa-check"></i>保存</a> <a class="btn btn-mini btn-danger"
						onclick="$('#dialog-message').dialog('close');"><i
							class="fa fa-times"></i>取消</a></td>
				</tr>
			</table>
		</form>
	</div>
	<!-- #dialog-message -->


	<!-- #dialog-message -->

	<%@ include file="/tailincludepage.jsp"%>
	<script type="text/javascript"
		src="webpages/report/t_report_base/t_report_base.js"></script>

	<!-- inline scripts related to this page -->
	<script type="text/javascript">
		var height1 = window.screen.availHeight;
		//var height1 = document.body.clientHeight;
		var width1 = document.body.clientWidth;
		
		$(window.parent.hangge());//进度条清除
		jQuery(function($) {
			$(document).on(
					'click',
					'th input:checkbox',
					function() {
						var that = this;
						$(this).closest('table').find(
								'tr > td:first-child input:checkbox').each(
								function() {
									this.checked = that.checked;
									$(this).closest('tr').toggleClass(
											'selected');
								});
					});

			$.widget("ui.dialog", $.extend({}, $.ui.dialog.prototype, {
				_title : function(title) {
					var $title = this.options.title || '&nbsp;'
					if (("title_html" in this.options)
							&& this.options.title_html == true)
						title.html($title);
					else
						title.text($title);
				}
			}));

		})
	</script>
</body>
</html>
