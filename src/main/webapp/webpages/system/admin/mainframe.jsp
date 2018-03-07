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
		<title>欢迎登录${systemname}</title>
		<%@ include file="/headincludepage.jsp"%> 
		<style type="text/css">
		.commitopacity{position:absolute; width:100%; height:100px; background:#7f7f7f; filter:alpha(opacity=50); -moz-opacity:0.8; -khtml-opacity: 0.5; opacity: 0.5; top:0px; z-index:99999;}
		</style>
</head>
<body class="no-skin">

		<!-- 页面顶部¨ -->
		<%@ include file="head.jsp"%>
		
		

		<!-- /section:basics/navbar.layout -->
		<div class="main-container" id="main-container">
			<script type="text/javascript">
				try{ace.settings.check('main-container' , 'fixed')}catch(e){}
			</script>
			
			<!-- 左侧菜单¨ -->
			<%@ include file="left.jsp"%>
			<div id="main-content" class="main-content">
			    
				
			    <div id="jzts" style="display:none; width:100%;height:100%; position:fixed; z-index:99999999;">
					<div class="commitopacity" id="bkbgjz"></div>
						<div style="padding-left: 16%;padding-top: 2px;">
						<div style="float: left;margin-top: 7px;"><img src="images/loadingi.gif" /> </div>
						<div><h4 class="lighter block red">&nbsp;处理中  ...</h4></div>
					</div>
				</div>

				<div>
					<!-- <iframe name="mainFrame" id="mainFrame" frameborder="0" src="login_default.do" style="margin:0 auto;width:100%;height:100%;"></iframe> -->
					<iframe name="mainFrame" id="mainFrame" frameborder="0" src="login_tab.do" style="margin:0 auto;"></iframe>
				</div>
				
	
				
			</div>
		</div><!-- /.main-container -->
		
		<!-- 改密 -->
		<div id="changepwd-dialog-message" class="hide">
		    <form name="changepwdForm" id="changepwdForm">
		        <input type="hidden" name="userid" id="passworduserid" value="${user.userid}"/>
		        <input type="hidden" name="usercode" id="passwordusercode" value="${user.usercode}"/>
				<table id="table_report" class="table table-striped table-bordered table-hover">
				   	<tr>
						<td style="width:30%">*原密码:</td>
						<td><input type="password" id="editsrcpassword" name="srcpassword" style="width:90%"/></td>
					</tr>
				    <tr>
						<td style="width:30%">*新密码:</td>
						<td><input type="password" id="editpassword" name="password" style="width:90%"/></td>
					</tr>
					<tr>
						<td style="width:30%">*确认密码:</td>
						<td><input type="password" id="editconfirmpassword" name="confirmpassword" style="width:90%"/></td>
					</tr>
				</table>
				
				<table id="table_report" class="table table-striped table-bordered table-hover">
					<tr>
						<td style="text-align: center;" colspan="100">
							<a class="btn btn-mini btn-primary" onclick="changepwd();"><i class="fa fa-check"></i>保存</a>
						</td>
					</tr>
				</table>
			</form>
		</div><!-- #dialog-message -->

		<!-- basic scripts -->
		
		<!-- basic scripts -->

		<%@ include file="/tailincludepage.jsp"%> 
		<script type="text/javascript" src="webpages/system/admin/changeskin.js"></script>
		
		<!-- page specific plugin scripts -->
		
		<script type="text/javascript">
			jQuery(function($) {
				applyChanges('${user.skin}');//皮肤
				
				$.widget("ui.dialog", $.extend({}, $.ui.dialog.prototype, {
					_title: function(title) {
						var $title = this.options.title || '&nbsp;'
						if( ("title_html" in this.options) && this.options.title_html == true )
							title.html($title);
						else title.text($title);
					}
				}));
				
				//进入到主页之后判断用户是否改密，然后，如果没改密，就把改密框框给弹出来
				if(parseInt('${user.ischangepwd}',10)==0){
					//修改密码
					var dialog = $( "#changepwd-dialog-message" ).removeClass('hide').dialog({
						modal: true,
						draggable:false,
						title: CommonParam.getDialogTitleCss('初始密码，必须修改','fa-key'),
						title_html: true,
						width:400,
						height:310,
						resizable:false,
						open: function (event, ui) {
				            $(".ui-dialog-titlebar-close", $(this).parent()).hide();
				        }
					});
				}
				
				$("#queryMenu")
				.autocomplete({
					minLength: 1,
					source: 'queryMenu_querymenu.do?systemid=${systemid}',
					success: function( data ) {
						response(data);
		        	},
		        	select: function(event, ui) {
		        		siMenu('z'+ui.item.moduleid,'lm'+ui.item.parentid,ui.item.control,ui.item.label);//+1故意不给UI点开的
		        	}
				})
				;
			});
			
			function cmainFrame(){
				var hmain = document.getElementById("mainFrame");
				var bheight = document.documentElement.clientHeight;
				hmain .style.width = '100%';
				//hmain .style.height = (bheight*2  - 51) + 'px';
				hmain .style.height = (bheight*2 + 100) + 'px';
				//alert(bheight*2  - 51);
				var bkbgjz = document.getElementById("bkbgjz");
				bkbgjz .style.height = (bheight  - 41) + 'px';
				
			}
			cmainFrame();
			window.onresize=function(){  
				cmainFrame();
			}
			//菜单状态切换
			var fmid = "fhindex";
			var mid = "fhindex";
			function siMenu(id,fid,control,name){
				if(id != mid){
					$("#"+mid).removeClass();
					mid = id;
				}
				if(fid != fmid){
					$("#"+fmid).removeClass();
					fmid = fid;
				}
				$("#"+fid).attr("class","active open");
				$("#"+id).attr("class","active");
				//$("#mainFrame").attr("src",control);
				top.mainFrame.addPanel(id,name,control);
			}
			
			//清除加载进度
			function hangge(){
				$("#jzts").hide();
			}
			
			//显示加载进度
			function jzts(){
				$("#jzts").show();
			}
			
			//修改密码
			function changepwd(){
				if($("#editsrcpassword").val()==""){
					CommonParam.tipsInput("#editsrcpassword",'请输入原密码');
					return false;
				}
				if($("#editpassword").val()==""){
					CommonParam.tipsInput("#editpassword",'请输入新密码');
					return false;
				}
				if($("#editconfirmpassword").val()==""){
					CommonParam.tipsInput("#editconfirmpassword",'请确认新密码');
					return false;
				}
				if($("#editpassword").val()!=$("#editconfirmpassword").val()){
					CommonParam.tipsInput("#editconfirmpassword",'新旧密码不一致');
					return false;
				}
				
				var url = 't_mng_userinfo/changePassword.do';
				var submitData = {};
				submitData = geneSubmitDataFromForm("#changepwdForm",submitData);
				jQueryAjaxForJSON(url, submitData,
						function(sRet) {
							bootbox.alert("修改成功!", function() {
								window.top.location.href = 'logout.do?systemid=${systemid}';
							});
						}, function(sRet) {
							bootbox.alert(sRet.field1, function() {
								window.location.href = 'logout.do?systemid=${systemid}';
							});
						}, function() {
							bootbox.alert('通讯异常',function(){
								var dialog = $( "#changepwd-dialog-message" ).removeClass('hide').dialog({
									modal: true,
									draggable:false,
									title: CommonParam.getDialogTitleCss('初始密码，必须修改','fa-key'),
									title_html: true,
									width:400,
									height:310,
									resizable:false,
									open: function (event, ui) {
							            $(".ui-dialog-titlebar-close", $(this).parent()).hide();
							        }
								});
							});
						},function(){
							$('#changepwd-dialog-message').dialog('close');
							window.parent.jzts();
						},function(){
							window.parent.hangge();
						});
			}
	    
		</script>
</body>
</html>
