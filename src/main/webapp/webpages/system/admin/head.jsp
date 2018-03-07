<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!-- #section:basics/navbar.layout -->
<div id="navbar" class="navbar navbar-default">
	<script type="text/javascript">
		try {
			ace.settings.check('navbar', 'fixed')
		} catch (e) {
		}
	</script>

	<div class="navbar-container" id="navbar-container">
		<!-- #section:basics/sidebar.mobile.toggle -->
		<button type="button" class="navbar-toggle menu-toggler pull-left"
			id="menu-toggler" data-target="#sidebar">
			<span class="sr-only">Toggle sidebar</span> <span class="icon-bar"></span>

			<span class="icon-bar"></span> <span class="icon-bar"></span>
		</button>

		<!-- /section:basics/sidebar.mobile.toggle -->
		<div class="navbar-header pull-left">
			<!-- #section:basics/navbar.layout.brand -->
			<a href="javascript:void(0);" class="navbar-brand"> <small> <i
					class="fa fa-leaf"></i>${systemname}
			</small>
			</a>

			<!-- /section:basics/navbar.layout.brand -->

			<!-- #section:basics/navbar.toggle -->

			<!-- /section:basics/navbar.toggle -->
		</div>
		
		<div class="ace-settings-container" id="ace-settings-container">
					<div class="btn btn-app btn-xs btn-warning ace-settings-btn" id="ace-settings-btn">
						<i class="ace-icon fa fa-cog bigger-130"></i>
					</div>
			
					<div class="ace-settings-box" id="ace-settings-box" style="padding-right: 153px;">
						<div class="pull-left">
						    <div>
								<!-- #section:settings.skins -->
								<div class="ace-settings-item">
									<div class="pull-left">
										<select id="skin-colorpicker" class="hide">
											<option data-skin="no-skin" value="#438EB9" <c:if test="${user.skin =='no-skin'}">selected</c:if>>#438EB9</option>
											<option data-skin="skin-1" value="#222A2D" <c:if test="${user.skin =='skin-1'}">selected</c:if>>#222A2D</option>
											<option data-skin="skin-2" value="#C6487E" <c:if test="${user.skin =='skin-2'}">selected</c:if>>#C6487E</option>
											<option data-skin="skin-3" value="#D0D0D0" <c:if test="${user.skin =='skin-3'}">selected</c:if>>#D0D0D0</option>
										</select>
									</div>
									<span>&nbsp; 选择皮肤</span>
								</div>
				
								<!-- /section:settings.skins -->
								<div>
								   注：根据个人喜好选择皮肤
								</div>
								
							</div>
						</div><!-- /.pull-left -->
					</div><!-- /.ace-settings-box -->
				</div><!-- /.ace-settings-container -->

		<!-- #section:basics/navbar.dropdown -->
		<div class="navbar-buttons navbar-header pull-right" role="navigation">
			<ul class="nav ace-nav">

				<!-- #section:basics/navbar.user_menu -->
				<li class="light-blue"><a data-toggle="dropdown" href="#"
					class="dropdown-toggle"> <img class="nav-user-photo"
						src="assets/avatars/user.jpg" alt="Jason's Photo" /> <span
						class="user-info"> <small>欢迎登录,</small> ${user.username}
					</span> <i class="ace-icon fa fa-caret-down"></i>

					<ul
						class="user-menu dropdown-menu-right dropdown-menu dropdown-yellow dropdown-caret dropdown-close">
						<li><a onclick="editUserH();" style="cursor:pointer;"><i class="ace-icon fa fa-user"></i> 修改资料</a></li>
						<li class="divider"></li>
						<li><a onclick="logout();"> <i class="ace-icon fa fa-power-off"></i>退出</a></li>
					</ul></li>

				<!-- /section:basics/navbar.user_menu -->
			</ul>
		</div>

		<!-- /section:basics/navbar.dropdown -->
	</div>
	<!-- /.navbar-container -->
</div>


<script type="text/javascript">
	function logout(){
		window.location.href = 'logout.do?systemid=${systemid}';
	}
	//修改个人资料
	function editUserH(){
		siMenu("","","t_mng_userinfo/userProfile.do?userid="+'${user.userid}');
	}
</script>