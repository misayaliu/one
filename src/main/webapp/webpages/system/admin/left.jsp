<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!-- #section:basics/sidebar -->
<div id="sidebar" class="sidebar                  responsive">
	<script type="text/javascript">
		try{ace.settings.check('sidebar' , 'fixed')}catch(e){}
	</script>

	<div class="sidebar-shortcuts" id="sidebar-shortcuts">
		<div class="sidebar-shortcuts-large" id="sidebar-shortcuts-large">
			<span class="block input-icon input-icon-left">
				<i class="ace-icon fa fa-search"></i><input class="form-control" type="text" id="queryMenu" name="queryMenu" placeholder="这里输入菜单名回车" />
			</span>
		</div>
	</div><!-- /.sidebar-shortcuts -->

	<ul class="nav nav-list">
		<li class="active" id="fhindex">
			<a href="login_mainframe.do">
				<i class="menu-icon fa fa-tachometer"></i>
				<span class="menu-text">主页</span>
			</a>
			<b class="arrow"></b>
		</li>
		
		
		<c:forEach items="${menuList}" var="menu">
			<li id="lm${menu.moduleid}" class="">
				  <a href="javascript:void(0);" class="dropdown-toggle" >
					<i class="${menu.moduleicon == null ? 'menu-icon fa fa-desktop' : menu.moduleicon}"></i>
					<span class="menu-text">${menu.modulename}</span>
					<b class="arrow fa fa-angle-down"></b>
				  </a>
				  <ul class="submenu">
						<c:forEach items="${menu.subMouleinfo}" var="sub">
							<c:if test="${sub.moduletype == '0'}">
								<li class="">
									<a href="javascript:void(0);" class="dropdown-toggle">
										<i class="menu-icon fa fa-caret-right"></i>
										<span class="menu-text"><b>${sub.modulename}</b></span>
										<b class="arrow fa fa-angle-down"></b>
									</a>
									<b class="arrow"></b>
									<ul class="submenu">
										<c:forEach items="${sub.subMouleinfo}" var="subSub">
										    <c:choose>
												<c:when test="${not empty subSub.control}">
													<li id="z${subSub.moduleid}">
													<a href="javascript:void(siMenu('z${subSub.moduleid}','lm${sub.moduleid}','${subSub.control}','${subSub.modulename}'));"><i class="menu-icon fa fa-leaf green"></i>${subSub.modulename}</a></li>
												</c:when>
												<c:otherwise>
													<li><a href="javascript:void(0);"><i class="menu-icon fa fa-leaf green"></i>${subSub.modulename}</a></li>
												</c:otherwise>
											</c:choose>
										</c:forEach>
									</ul>
								</li>
							</c:if>
							<c:if test="${sub.moduletype == '1'}">
								<c:choose>
									<c:when test="${not empty sub.control}">
										<li id="z${sub.moduleid}">
										<a href="javascript:void(siMenu('z${sub.moduleid}','lm${menu.moduleid}','${sub.control}','${sub.modulename}'));"><i class="menu-icon fa fa-leaf green"></i></i>${sub.modulename}</a></li>
									</c:when>
									<c:otherwise>
										<li><a href="javascript:void(0);"><i class="menu-icon fa fa-leaf green"></i></i>${sub.modulename}</a></li>
									</c:otherwise>
								</c:choose>
							</c:if>
						</c:forEach>
			  		</ul>
			</li>
		</c:forEach>
		
	</ul><!-- /.nav-list -->

	<!-- #section:basics/sidebar.layout.minimize -->
	<div class="sidebar-toggle sidebar-collapse" id="sidebar-collapse">
		<i class="ace-icon fa fa-angle-double-left" data-icon1="ace-icon fa fa-angle-double-left" data-icon2="ace-icon fa fa-angle-double-right"></i>
	</div>

	<!-- /section:basics/sidebar.layout.minimize -->
	<script type="text/javascript">
		try{ace.settings.check('sidebar' , 'collapsed')}catch(e){}
	</script>
</div>