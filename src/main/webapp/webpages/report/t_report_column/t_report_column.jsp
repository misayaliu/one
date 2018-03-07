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
<style type="text/css">
.table tr td span {
	color: black;
}
</style>
</head>
<body class="no-skin">

	<!-- /section:basics/navbar.layout -->
	<div class="main-container" id="main-container">
		<script type="text/javascript">
			try {
				ace.settings.check('main-container', 'fixed')
			} catch (e) {
			}
		</script>

		<!-- /section:basics/sidebar -->
		<div class="main-content">
			<div class="main-content-inner">
				<!-- #section:basics/content.breadcrumbs -->
				<div class="breadcrumbs" id="breadcrumbs">
					<script type="text/javascript">
						try {
							ace.settings.check('breadcrumbs', 'fixed')
						} catch (e) {
						}
					</script>

					<ul class="breadcrumb">
						<li><i class="ace-icon fa fa-home home-icon"></i> <a
							href="login_mainframe.do" target="_top">主页</a></li>
						<li class="active">报表列信息配置</li>
					</ul>
					<!-- /.breadcrumb -->
				</div>

				<!-- /section:basics/content.breadcrumbs -->
				<div class="page-content">
					<!-- 检索  -->
					<form action="t_report_column/listPages.do" method="post"
						name="searchForm" id="searchForm">
						<table>
							<tr>
								<td>
								<select class="form-control" name="report_id" id="searchreport_id"
									data-placeholder="请选择报表">
									<option value="">---请选择---</option>
									<c:forEach items="${reportBaseList}" var="reportbase">
											<option value="${reportbase.report_id}" <c:if test="${pd.report_id==reportbase.report_id}">selected</c:if>>${reportbase.report_name}</option>
									</c:forEach>
								</select></td>
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
												<th>报表</th>
												<th>列中文名</th>
												<th>对齐方式</th>
												<th>字体颜色</th>
												<th>背景颜色</th>
												<th>是否合并</th>
												<th>合计类型</th>
												<th>合计字体颜色</th>
												<th>合计背景颜色</th>
												<th>优先级</th>
												<th>最后行是否显示</th>
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
															<td>
																<c:forEach items="${reportBaseList}" var="reportbase">
																	<c:if test="${reportbase.report_id==data.report_id}">
																		${reportbase.report_name}
																	</c:if>
																</c:forEach>
															</td>
															<td>${data.column_name }</td>
															<td><span class="label label-success arrowed">
																<jy:select type="translate" typeName="99005" key="${data.align}"/>
															</span>
															</td>
															<td><c:if test="${data.font_color == '#000000'}">
																	<span
																		style="color: white;background-color:${data.font_color} !important"
																		class="label"><jy:select type="translate" typeName="99009" key="${data.font_color}"/></span>
																</c:if></td>

															<c:if test="${data.font_color != '#000000'}">
																<span
																	style="text-align:center; background-color:${data.font_color} !important"
																	class="label "><jy:select type="translate" typeName="99009" key="${data.font_color}"/></span>
															</c:if>
															<td><c:if test="${data.bg_color == '#000000'}">
																	<span
																		style="color: white;background-color:${data.bg_color} !important"
																		class="label"><jy:select type="translate" typeName="99010" key="${data.bg_color}"/></span>
																</c:if> <c:if test="${data.bg_color != '#000000'}">
																	<span
																		style="text-align:center; background-color:${data.bg_color} !important"
																		class="label "><jy:select type="translate" typeName="99010" key="${data.bg_color}"/></span>
																</c:if>
																</td>
															<td><c:if test="${data.merged == '1'}">
																	<span class="label label-important arrowed-in">不合并</span>
																</c:if> <c:if test="${data.merged == '2'}">
																	<span class="label label-success arrowed">需合并</span>
																</c:if></td>
															<td><span class="label label-success arrowed">
																<jy:select type="translate" typeName="99007" key="${data.rollup_type}"/></span>
															</td>
															<td><c:if test="${data.merged_font_color == '#000000'}">
																	<span
																		style="color: white;background-color:${data.merged_font_color} !important"
																		class="label"><jy:select type="translate" typeName="99009" key="${data.merged_font_color}"/></span>
																</c:if> <c:if test="${data.merged_font_color != '#000000'}">
																	<span
																		style="text-align:center; background-color:${data.merged_font_color} !important"
																		class="label "><jy:select type="translate" typeName="99009" key="${data.merged_font_color}"/></span>
																</c:if></td>
															<td><span
																style="text-align:center; background-color:${data.merged_bg_color} !important"
																class="label "><jy:select type="translate" typeName="99010" key="${data.merged_bg_color}"/></span></td>
															<td><fmt:formatNumber value="${data.priority}" pattern="#0"/></td>
															<td><jy:select type="translate" typeName="99008" key="${data.is_last_row_show}"/></td>
															<td>
																<div class="hidden-sm hidden-xs btn-group">
																	<a class='btn btn-xs btn-info' title="编辑"
																		onclick="edit('${data.column_id}')"><i
																		class='ace-icon fa fa-pencil bigger-120'></i></a> <a
																		class='btn btn-xs btn-danger' title="删除"
																		onclick="del('${data.column_id}')"><i
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
			<input type="hidden" name="column_id" />
			<table id="table_report"
				class="table table-striped table-bordered table-hover">
				<tr>
					<td style="width: 15%">*报表标识:</td>
					<td>
						<select class="form-control" name="report_id" id="editreport_id"
							data-placeholder="请选择报表" style="width: 90%">
							<option value="">---请选择---</option>
							<c:forEach items="${reportBaseList}" var="reportbase">
									<option value="${reportbase.report_id}">${reportbase.report_name}</option>
							</c:forEach>
						</select>
					</td>
					<td style="width: 15%">列中文名:</td>
					<td><input type="text" name="column_name" id="editcolumn_name"
						placeholder="这里输入列中文名" style="width: 90%" /></td>
				</tr>

				<tr>
					<td style="width: 15%">优先级:</td>
					<td><input type="text" name=priority id="editpriority"
						placeholder="这里输入优先级" style="width: 90%" /></td>
					<td style="width: 15%">对齐方式:</td>
					<td><jy:select type="select" name="align" id="align"
							typeName="99005" /></td>

				</tr>
				<tr>
					<td style="width: 15%">合计类型:</td>
					<td><jy:select type="select" name="rollup_type"
							id="rollup_type" typeName="99007" /></td>
					<td style="width: 15%">是否合并:</td>
					<td><jy:select type="select" name="merged" id="merged"
							typeName="99006" /></td>
				</tr>
				<tr>
					<td style="width: 15%">字体颜色:</td>
					<td><jy:select type="select" name="font_color" id="font_color"
							typeName="99009" value="#000000"/></td>
					<td style="width: 15%">背景颜色:</td>
					<td><jy:select type="select" name="bg_color" id="bg_color"
							typeName="99010" value="#FFFFFF"/></td>
				</tr>
				<tr>
					<td style="width: 15%">合计字体颜色:</td>
					<td><jy:select type="select" name="merged_font_color"
							id="merged_font_color" typeName="99009" value="#FF0000"/></td>
					<td style="width: 15%">合计背景颜色:</td>
					<td><jy:select type="select" name="merged_bg_color"
							id="merged_bg_color" typeName="99010" value="#FFFFFF"/></td>
				</tr>

				<tr>
					<td style="width: 15%">最后行是否显示:</td>
					<td><jy:select type="select" name="is_last_row_show"
							id="is_last_row_show" typeName="99008" /></td>
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
		src="webpages/report/t_report_column/t_report_column.js"></script>

	<!-- inline scripts related to this page -->
	<script type="text/javascript">
		var mainFrame = window.top.document.getElementById('mainFrame');
		var height1 = $(mainFrame).height();
		var width1 = $(mainFrame).width();

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
			$('.date-picker').datepicker({
				autoclose : true,
				todayHighlight : true,
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
