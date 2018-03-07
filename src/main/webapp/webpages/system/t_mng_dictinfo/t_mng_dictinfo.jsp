<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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
		<title>数据字典管理</title>
		
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
							<li class="active">数据字典管理</li>
						</ul><!-- /.breadcrumb -->
					</div>

					<!-- /section:basics/content.breadcrumbs -->
					<div class="page-content">
				   		<!-- 检索  -->
						<form action="t_mng_dictinfo/listDicts.do" method="post" name="searchForm" id="searchForm">
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
									    <c:if test="${add_btn == '1'}">
									    	<span class="btn btn-success btn-sm tooltip-info" data-rel="tooltip" onclick="add();" data-placement="bottom">新增</span>
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
													<th>字典编号</th>
													<th>字典编号含义</th>
													<th>字典值</th>
													<th>字典值含义</th>
													<th></th>
												</tr>
											</thead>
		
											<tbody>
											    <!-- 开始循环 -->	
												<c:choose>
													<c:when test="${not empty dictList}">
														<c:forEach items="${dictList}" var="dict" varStatus="vs">
																	
															<tr>
																<td class='center' style="width: 30px;">
																	<label>
																		<input type='checkbox' disabled="disabled" class="ace" />
																		<span class="lbl"></span>
																	</label>
																</td>
																<td class='center' style="width: 50px;">${vs.index+1}</td>
																<td>${dict.dicttype}</td>
																<td>${dict.dicttypedesc}</td>
																<td>${dict.dictvalue}</td>
																<td>${dict.dictvaluedesc}</td>
																<td>
																   <div class="hidden-sm hidden-xs btn-group">
																		<c:if test="${edit_btn == '1'}">
																			<button class="btn btn-xs btn-info" title="编辑" onclick="edit('${dict.dicttype}','${dict.dictvalue}');">
																				<i class="ace-icon fa fa-pencil bigger-120"></i>
																			</button>
																		</c:if>
					
					                                                    <c:if test="${delete_btn == '1'}">
																			<button class="btn btn-xs btn-danger" title="删除" onclick="del('${dict.dicttype}','${dict.dictvalue}');">
																				<i class="ace-icon fa fa-trash-o bigger-120"></i>
																			</button>
																		</c:if>
																	</div>
					
																	<div class="hidden-md hidden-lg">
																		
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
		
		<div id="dialog-message" class="hide">
		    <form name="editForm" id="editForm">
				<table id="table_report" class="table table-striped table-bordered table-hover">
				    <tr>
						<td style="width:15%">*字典编号:</td>
						<td><input type="text" name="dicttype" id="editdicttype" placeholder="这里输入字典编号" style="width:90%" title="字典编号" maxlength="6"/></td>
					    <td style="width:15%">*字典编号含义:</td>
						<td><input type="text" name="dicttypedesc" id="editdicttypedesc" placeholder="这里输入字典编号含义" style="width:90%" title="字典编号含义" maxlength="20"/></td>
					</tr>
					<tr>
						<td style="width:15%">*字典值:</td>
						<td><input type="text" name="dictvalue" id="editdictvalue" placeholder="这里输入字典值" style="width:90%" title="字典值" onblur="isExistsWhenAdd()"  maxlength="20"/></td>
						<td style="width:15%">*字典值含义:</td>
						<td><input type="text" name="dictvaluedesc" id="editdictvaluedesc" placeholder="这里输入字典值含义" style="width:90%" title="字典值含义"  maxlength="20"/></td>
					</tr>
				</table>
				
				<table id="table_report" class="table table-striped table-bordered table-hover">
					<tr>
						<td style="text-align: center;" colspan="100">
						    <a class="btn btn-mini btn-primary" onclick="save();"><i class="fa fa-check"></i>保存</a>
							<a class="btn btn-mini btn-danger" onclick="$('#dialog-message').dialog('close');"><i class="fa fa-times"></i>取消</a>
						</td>
					</tr>
				</table>
			</form>
		</div><!-- #dialog-message -->

		<!-- basic scripts -->

		<%@ include file="/tailincludepage.jsp"%> 
		<!-- 自带业务的js -->
		<script type="text/javascript" src="webpages/system/t_mng_dictinfo/t_mng_dictinfo.js"></script>
		<!-- page specific plugin scripts -->

		<!-- inline scripts related to this page -->
		<script type="text/javascript">
			var height1 = window.screen.availHeight;
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
