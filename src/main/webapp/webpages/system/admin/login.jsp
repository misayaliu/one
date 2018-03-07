<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en">
    
<head>
        <title>欢迎登陆${systemname}</title>
        <meta charset="utf-8" />
        <%@ include file="/headincludepage.jsp"%> 
        
        <STYLE TYPE="text/css"><!--BODY {background-image: URL(images/banner_slide_01.jpg);background-position: center;background-repeat: no-repeat;background-attachment: fixed;}--></STYLE>
    </head>
   <body class="login-layout">

		<div class="main-container">
			<div class="main-content">
				<div class="row">
					<div class="col-sm-10 col-sm-offset-1">
						<div class="login-container">
							<div class="center">
								<h1>
									<i class="ace-icon fa fa-leaf green"></i>
									<span class="red">捷羿</span>
									<span class="white" id="id-text2">${systemname}</span>
								</h1>
								<h4 class="blue" id="id-company-text">上海捷羿软件系统有限公司 版权所有&copy; 2015 </h4>
							</div>

							<div class="space-6"></div>

							<div class="position-relative">
								<div id="login-box" class="login-box visible widget-box no-border">
									<div class="widget-body">
										<div class="widget-main">
											<h4 class="header blue lighter bigger">
												<i class="ace-icon fa fa-coffee green"></i>
												欢迎登录系统
											</h4>

											<div class="space-6"></div>

											<form action="login_userLogin.do" method="post" name="loginForm" id="loginForm" onsubmit="return check();">
											    <input type="hidden" name="systemid" id="systemid" value="${systemid}" />
											    <input type="hidden" name="systemname" id="systemname" value="${systemname}" />
												<fieldset>
													<label class="block clearfix">
														<span class="block input-icon input-icon-right">
															<input type="text" class="form-control" name="loginname" id="loginname" value="${loginname}" placeholder="用户名" />
															<i class="ace-icon fa fa-user"></i>
														</span>
													</label>

													<label class="block clearfix">
														<span class="block input-icon input-icon-right">
															<input type="password" class="form-control" name="password" id="password" value="${password}" placeholder="密码" />
															<i class="ace-icon fa fa-lock"></i>
														</span>
													</label>

													<div class="space"></div>

													<div class="clearfix">
														<label class="inline">
															<input type="checkbox" id="logonsaveid" class="ace" />
															<span class="lbl">记住登录信息</span>
														</label>

														<button type="button" onclick="logon();" id="logonbtn" class="width-35 pull-right btn btn-sm btn-primary">
															<i class="ace-icon fa fa-key"></i>
															<span class="bigger-110">登录</span>
														</button>
													</div>

													<div class="space-4"></div>
												</fieldset>
											</form>
										</div><!-- /.widget-main -->

										<div class="toolbar clearfix">
											<div>
												<a href="#">
												</a>
											</div>

											<div>
												<a href="#">
												</a>
											</div>
										</div>
									</div><!-- /.widget-body -->
								</div><!-- /.login-box -->
							</div><!-- /.position-relative -->
						</div>
					</div><!-- /.col -->
				</div><!-- /.row -->
			</div><!-- /.main-content -->
		</div><!-- /.main-container -->
		<!-- basic scripts -->
		
		<!--[if !IE]> -->
		<script type="text/javascript">
			window.jQuery || document.write("<script src='assets/js/jquery.js'>"+"<"+"/script>");
		</script>
		<!-- <![endif]-->
		
		<!--[if IE]>
		<script type="text/javascript">
			window.jQuery || document.write("<script src='assets/js/jquery1x.js'>"+"<"+"/script>");
		</script>
		<![endif]-->
				
		<script type="text/javascript" src="assets/js/jquery.tips.js"></script>
		<script type="text/javascript" src="assets/js/jquery.cookie.js"></script>
				
		<script type="text/javascript">
			if('ontouchstart' in document.documentElement) document.write("<script src='assets/js/jquery.mobile.custom.js'>"+"<"+"/script>");
		</script>

		<!-- inline scripts related to this page -->
		<script type="text/javascript">
			var errInfo = "${errInfo}";
			jQuery(function($) {
			 $(document).on('click', '.toolbar a[data-target]', function(e) {
				e.preventDefault();
				var target = $(this).data('target');
				$('.widget-box.visible').removeClass('visible');//hide others
				$(target).addClass('visible');//show target
			 });
			 
			 var loginname = $.cookie('loginname');
			var password = $.cookie('password');
			if(typeof(loginname) != "undefined" && typeof(password) != "undefined"){
				$("#loginname").val(loginname);
				$("#password").val(password);
				$("#logonsaveid").attr("checked",true);
				$("#code").focus();
			}
			 
			 if(errInfo!=""){
				$("#loginname").tips({
					side:1,
		            msg:errInfo,
		            bg:'#FF5080',
		            time:5
		        });
			}
			 $("#loginname").focus();
			});
			
			$(document).keyup(function(event){
				  if(event.keyCode ==13){
				    $("#logonbtn").trigger("click");
				  }
				});
			
			function check(){
				
				if($("#loginname").val()==""){

					$("#loginname").tips({
						side:2,
			            msg:'用户名不得为空',
			            bg:'#AE81FF',
			            time:3
			        });
					
					$("#loginname").focus();
					return false;
				}else{
					$("#loginname").val(jQuery.trim($('#loginname').val()));
				}
				
				if($("#password").val()==""){

					$("#password").tips({
						side:2,
			            msg:'密码不得为空',
			            bg:'#AE81FF',
			            time:3
			        });
					
					$("#password").focus();
					return false;
				}
				
				$("#logonbtn").attr('disabled',true);
				$("#login-box").tips({
					side:1,
		            msg:'正在登录 , 请稍后 ...',
		            bg:'#68B500',
		            time:1000
		        });
				
				return true;
			}
			
			function logon(){
				if($("#logonsaveid").is(':checked')){
					$.cookie('loginname', $("#loginname").val(), { expires: 7 });
					$.cookie('password', $("#password").val(), { expires: 7 });
				}else{
					$.cookie('loginname', '', { expires: -1 });
					$.cookie('password', '', { expires: -1 });
				}
				
				$("#loginForm").submit();
			}
		</script>
</body>

</html>