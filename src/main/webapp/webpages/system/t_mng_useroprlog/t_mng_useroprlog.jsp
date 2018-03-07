<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 

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
		<title>操作日志查询</title>
		
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
							<li>
								<i class="ace-icon fa fa-home home-icon"></i>
								<a href="login_mainframe.do" target="_top">主页</a>
							</li>
							<li class="active">系统管理</li>
							<li class="active">系统操作日志</li>
						</ul><!-- /.breadcrumb -->
					</div>

					<!-- /section:basics/content.breadcrumbs -->
					<div class="page-content">
				   		<!-- 检索  -->
						<form action="t_mng_useroprlog/listLogs.do" method="post" name="searchForm" id="searchForm">
							<table>
								<tr>
									<td>
									    <span class="input-icon">
									    	<input autocomplete="off" type="text" name="searchword" value="${pd.searchword}" placeholder="这里输入关键词" id="form-field-icon-1" /><i class="ace-icon fa fa-search blue"></i>
										</span>
									</td>
									<td style="vertical-align:top;">
									    <c:if test="${search_btn == '1'}">
									    	<span class="btn btn-purple btn-sm tooltip-info" data-rel="tooltip" onclick="query();" data-placement="bottom">查询</span>
									    </c:if>
									</td>
								</tr>
							</table>
							<!-- 检索  -->
						</form>
					
						<div class="row">
							<div class="col-xs-12">
								<!-- PAGE CONTENT BEGINS -->
								<div class="row">
									<div class="col-xs-12">
										<table id="sample-table-1" class="table table-striped table-bordered table-hover">
											<thead>
												<tr>
													<th class="center">
														<label class="position-relative">
															<input type="checkbox"  id="zcheckbox" disabled="disabled" class="ace" />
															<span class="lbl"></span>
														</label>
													</th>
													<th>序号</th>
													<th>用户姓名</th>
													<th class="hidden-480">操作日期</th>
													<th class="hidden-480">操作时间</th>
													<th class="hidden-480">终端IP</th>
													<th>操作事件</th>
													<th></th>
												</tr>
											</thead>
		
											<tbody>
											    <!-- 开始循环 -->	
												<c:choose>
													<c:when test="${not empty oprlogList}">
														<c:forEach items="${oprlogList}" var="oprlog" varStatus="vs">
																	
															<tr>
																<td class='center' style="width: 30px;">
																	<label>
																		<input type='checkbox' disabled="disabled" class="ace" />
																		<span class="lbl"></span>
																	</label>
																</td>
																<td class='center' style="width: 50px;">${vs.index+1}</td>
																<td>${oprlog.username}</td>
																<td class="hidden-480">${fn:substring(oprlog.logdate, 0, 4)}-${fn:substring(oprlog.logdate, 4, 6)}-${fn:substring(oprlog.logdate, 6, 8)}</td>
																<td class="hidden-480">${fn:substring(oprlog.logtime, 0, 2)}:${fn:substring(oprlog.logtime, 2, 4)}:${fn:substring(oprlog.logtime, 4, 6)}</td>
																<td class="hidden-480">${oprlog.hostip}</td>
																<td>${oprlog.msg}</td>
																<td>
																   <div class="btn-group">
																		<button class="btn btn-xs btn-pink" title="查看明细" onclick="showDetail('${oprlog.systemid}','${oprlog.logdate}','${oprlog.logtime}','${oprlog.userid}','${oprlog.hostip}','${oprlog.msg}');">
																			<i class="ace-icon fa fa-book bigger-120"></i>
																		</button>
																	</div>
																</td>
															</tr>
														
														</c:forEach>
													</c:when>
													<c:otherwise>
														<tr class="main_info">
															<td colspan="10" class="center">
															    <c:if test="${loadPage == '0' }">
															   	 	请根据需要查询数据
															    </c:if>
															    <c:if test="${loadPage == '1' }">
															   	 	没有相关数据
															    </c:if>
                                                            </td>
														</tr>
													</c:otherwise>
												</c:choose>
											</tbody>
										</table>
										
										<div style="float: right;padding-top: 0px;margin-top: 0px;">
										    ${pageStr}
										</div>
									</div><!-- /.span -->
								</div><!-- /.row -->
							</div><!-- /.page-content -->
						</div>
					</div>
				</div>
			</div><!-- /.main-content -->

			<a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
				<i class="ace-icon fa fa-angle-double-up icon-only bigger-110"></i>
			</a>
		</div><!-- /.main-container -->
		
		<div id="detail-dialog-message" class="hide">
			<table id="table_detail" class="table table-striped table-bordered table-hover">
			</table>
		</div><!-- #dialog-message -->

		<%@ include file="/tailincludepage.jsp"%> 
		<!-- 自带业务的js -->
		<script type="text/javascript" src="webpages/system/t_mng_useroprlog/t_mng_useroprlog.js"></script>
		<!-- page specific plugin scripts -->

		<!-- inline scripts related to this page -->
		<script type="text/javascript">
			var height1 = window.screen.availHeight;
			//var height1 = document.body.clientHeight;
			var width1 = document.body.clientWidth;
			
			$(window.parent.hangge());//进度条清除
			jQuery(function($) {
				$(document).on('click', 'th input:checkbox' , function(){
					var that = this;
					$(this).closest('table').find('tr > td:first-child input:checkbox')
					.each(function(){
						this.checked = that.checked;
						$(this).closest('tr').toggleClass('selected');
					});
				});
				
				$.widget("ui.dialog", $.extend({}, $.ui.dialog.prototype, {
					_title: function(title) {
						var $title = this.options.title || '&nbsp;'
						if( ("title_html" in this.options) && this.options.title_html == true )
							title.html($title);
						else title.text($title);
					}
				}));
			})
		</script>
</body>
</html>
