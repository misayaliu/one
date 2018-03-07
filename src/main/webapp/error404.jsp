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
		<title>页面找不到</title>
		
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

						<!-- /section:settings.box -->
						<div class="row">
							<div class="col-xs-12">
								<!-- PAGE CONTENT BEGINS -->

								<!-- #section:pages/error -->
								<div class="error-container">
									<div class="well">
										<h1 class="grey lighter smaller">
											<span class="blue bigger-125">
												<i class="ace-icon fa fa-sitemap"></i>
												404
											</span>
											页面找不到
										</h1>

										<hr />
										<h3 class="lighter smaller">我们竭尽全力搜索您的请求，但是未能找到!</h3>

										<div>

											<div class="space"></div>
											<h4 class="smaller">您可以尝试:</h4>

											<ul class="list-unstyled spaced inline bigger-110 margin-15">
												<li>
													<i class="ace-icon fa fa-hand-o-right blue"></i>
													检查您的url路径看看是否正确
												</li>

												<li>
													<i class="ace-icon fa fa-hand-o-right blue"></i>
													检查系统日志发现问题
												</li>

												<li>
													<i class="ace-icon fa fa-hand-o-right blue"></i>
													请教专业人员指导
												</li>
											</ul>
										</div>

										<hr />
										<div class="space"></div>

										<!-- <div class="center">
											<a href="javascript:history.back()" class="btn btn-grey">
												<i class="ace-icon fa fa-arrow-left"></i>
												返回
											</a>
										</div> -->
									</div>
								</div>

								<!-- /section:pages/error -->

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

		<!-- basic scripts -->

		<%@ include file="/tailincludepage.jsp"%> 
		<!-- 自带业务的js -->
		<script type="text/javascript" src="webpages/system/t_mng_dictinfo/t_mng_dictinfo.js"></script>
		<!-- page specific plugin scripts -->

		<!-- inline scripts related to this page -->
		<script type="text/javascript">
			var height1 = window.screen.availHeight;
			var width1 = document.body.clientWidth;
			try{
				$(window.parent.hangge());//进度条清除
			}catch(e){
				
			}
		</script>
</body>
</html>
