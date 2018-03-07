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
					<!-- /section:basics/content.breadcrumbs -->
					<div class="page-content">

						<div class="row">
							<div class="col-xs-12">
								<!-- PAGE CONTENT BEGINS -->

								<div>
									<div id="user-profile-3" class="user-profile row">
										<div class="col-sm-offset-1 col-sm-10">
											<div class="tabbable">
												<ul class="nav nav-tabs padding-16">
													<li class="active">
														<a data-toggle="tab" href="#edit-basic">
															<i class="green ace-icon fa fa-pencil-square-o bigger-125"></i>
															个人信息
														</a>
													</li>

													<li>
														<a data-toggle="tab" href="#edit-password">
															<i class="blue ace-icon fa fa-key bigger-125"></i>
															密码
														</a>
													</li>
												</ul>
              										
												<div class="tab-content profile-edit-tab-content">
												    
													<div id="edit-basic" class="tab-pane in active">
														<form class="form-horizontal" name="baseinfoForm" id="baseinfoForm">
														    <input type="hidden" name="userid" id="baseinfouserid" value="${pd.userid}"/>
															<h4 class="header blue bolder smaller">基本信息</h4>

															<div class="row">
															    <div class="col-xs-12 col-sm-4">
																	<label class="col-sm-3 control-label no-padding-right" for="form-field-email">登录名</label>

																	<div class="col-sm-9">
																		<span class="input-icon input-icon-right">
																			<input type="text" name="usercode" id="editusercode" value="${pd.usercode}" readonly="true"/>
																			<i class="ace-icon fa fa-child"></i>
																		</span>
																	</div>
																</div>
																<div class="vspace-12-sm"></div>
																<div class="col-xs-12 col-sm-8">
																	<label class="col-sm-3 control-label no-padding-right" for="form-field-email">姓名</label>

																	<div class="col-sm-9">
																		<span class="input-icon input-icon-right">
																			<input type="text" name="username" id="editusername" value="${pd.username}" />
																			<i class="ace-icon fa fa-male"></i>
																		</span>
																	</div>
																</div>
															</div>

															<div class="space"></div>
															
															<div class="row">
															    <div class="col-xs-12 col-sm-4">
																	<label class="col-sm-3 control-label no-padding-right" for="form-field-email" >性别</label>

																	<div class="col-sm-9">
																	    <label class="inline">
																			<input name="sex" value="0" type="radio" class="ace" <c:if test="${pd.sex=='0'}">checked</c:if> />
																			<span class="lbl middle"> 男</span>
																		</label>
	
																		&nbsp; &nbsp; &nbsp;
																		<label class="inline">
																			<input name="sex" value="1" type="radio" class="ace" <c:if test="${pd.sex=='1'}">checked</c:if> />
																			<span class="lbl middle"> 女</span>
																		</label>
																	</div>
																</div>
																<div class="vspace-12-sm"></div>
																<div class="col-xs-12 col-sm-8">
																</div>
															</div>
															

															<div class="space"></div>
															<h4 class="header blue bolder smaller">联系方式</h4>
															
															<div class="row">
															    <div class="col-xs-12 col-sm-4">
																	<label class="col-sm-3 control-label no-padding-right" for="form-field-email">邮箱</label>

																	<div class="col-sm-9">
																		<span class="input-icon input-icon-right">
																			<input type="text" name="mailbox" id="editmailbox" value="${pd.mailbox}" />
																			<i class="ace-icon fa fa-envelope"></i>
																		</span>
																	</div>
																</div>
																<div class="vspace-12-sm"></div>
																<div class="col-xs-12 col-sm-8">
																	<label class="col-sm-3 control-label no-padding-right" for="form-field-email">手机</label>

																	<div class="col-sm-9">
																		<span class="input-icon input-icon-right">
																			<input type="text" name="telphone" id="edittelphone" value="${pd.telphone}" />
																			<i class="ace-icon fa fa-mobile"></i>
																		</span>
																	</div>
																</div>
															</div>
															
															<div class="space"></div>
															
															<div class="row">
															    <div class="col-xs-12 col-sm-4">
																	<label class="col-sm-3 control-label no-padding-right" for="form-field-email">电话</label>

																	<div class="col-sm-9">
																		<span class="input-icon input-icon-right">
																			<input type="text" name="cellphone" id="editcellphone" value="${pd.cellphone}" />
																			<i class="ace-icon fa fa-phone"></i>
																		</span>
																	</div>
																</div>
																<div class="vspace-12-sm"></div>
																<div class="col-xs-12 col-sm-8">
																</div>
															</div>
															
															<div class="space"></div>
															
															<div class="clearfix form-actions">
																<div style="text-align: center;" colspan="100">
																	<button class="btn btn-info" type="button" onclick="saveBaseInfo();">
																		<i class="ace-icon fa fa-check bigger-110"></i>
																		保存
																	</button>
																</div>
															</div>
														</form>
													</div>
													
													
													<div id="edit-password" class="tab-pane">
														<form class="form-horizontal" name="passwordForm" id="passwordForm">
														    <input type="hidden" name="userid" id="passworduserid" value="${pd.userid}"/>
															<div class="space-10"></div>
															
															<div class="form-group">
																<label class="col-sm-3 control-label no-padding-right" for="form-field-pass1">原密码</label>

																<div class="col-sm-9">
																	<input type="password" id="editsrcpassword" name="srcpassword" />
																</div>
															</div>

															<div class="space-4"></div>

															<div class="form-group">
																<label class="col-sm-3 control-label no-padding-right" for="form-field-pass1">新密码</label>

																<div class="col-sm-9">
																	<input type="password" id="editpassword" name="password" />
																</div>
															</div>

															<div class="space-4"></div>

															<div class="form-group">
																<label class="col-sm-3 control-label no-padding-right" for="form-field-pass2">确认密码</label>

																<div class="col-sm-9">
																	<input type="password" id="editconfirmpassword" name="confirmpassword" />
																</div>
															</div>
															
															<div class="space"></div>
															
															<div class="clearfix form-actions">
																<div style="text-align: center;" colspan="100">
																	<button class="btn btn-info" type="button" onclick="changePassword();">
																		<i class="ace-icon fa fa-check bigger-110"></i>
																		保存
																	</button>
																</div>
															</div>
														</form>
													</div>
													
												</div>
											</div>
											
										</div><!-- /.span -->
									</div><!-- /.user-profile -->
								</div>

								<!-- PAGE CONTENT ENDS -->
							</div><!-- /.col -->
						</div><!-- /.row -->
					</div><!-- /.page-content -->
				</div>
			</div><!-- /.main-content -->
			<a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
				<i class="ace-icon fa fa-angle-double-up icon-only bigger-110"></i>
			</a>
		</div><!-- /.main-container -->

		<%@ include file="/tailincludepage.jsp"%> 
		<script type="text/javascript" src="webpages/system/t_mng_userinfo/userProfile.js"></script>

		<!-- inline scripts related to this page -->
		<script type="text/javascript">
		    var basePath = '<%=basePath%>';
			jQuery(function($) {
			});
		</script>
	</body>
</html>
