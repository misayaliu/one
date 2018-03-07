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
	<base href="<%=basePath%>">
	<script type="text/javascript" src="assets/tab/js/jquery-1.7.2.js"></script>
	<script type="text/javascript" src="assets/tab/js/framework.js"></script>
	<link href="assets/tab/css/import_basic.css" rel="stylesheet" type="text/css"/>
	<link  rel="stylesheet" type="text/css" id="skin" prePath="assets/tab/" /><!--默认相对于根目录路径为../，可添加prePath属性自定义相对路径，如prePath="<%=request.getContextPath()%>"-->
	<script type="text/javascript" charset="utf-8" src="assets/tab/js/tab.js"></script>
	<style type="text/css">
	.commitopacity{position:absolute; width:100%; height:100%; background:#7f7f7f; filter:alpha(opacity=50); -moz-opacity:0.8; -khtml-opacity: 0.5; opacity: 0.5; top:0px; z-index:99999;}
	</style>
	</head>
	
	
<body>
<div id="tab_menu" style="height:30px;"></div>
<div style="width:100%;">
	<div id="jzts" style="display:none; width:100%;height:100%; position:fixed; z-index:99999999;">
		<div class="commitopacity" id="bkbgjz"></div>
			<div style="padding-left: 16%;padding-top: 2px;">
			<div style="float: left;margin-top: 7px;"><img src="images/loadingi.gif" /> </div>
			<div><h4 class="lighter block red">&nbsp;处理中  ...</h4></div>
		</div>
	</div>
	
	<div id="page" style="width:100%;height:100%;"></div>
</div>		
</body>
<script type="text/javascript">

function tabAddHandler(mid,mtitle,murl){
	tab.add({
	id :mid,
	title :mtitle,
	url :murl,
	isClosed :true
	});
	tab.update({
		id :mid,
		title :mtitle,
		url :murl,
		isClosed :true
	});
	tab.activate(mid);
}
 var tab;	
$( function() {
	 tab = new TabView( {
		containerId :'tab_menu',
		pageid :'page',
		cid :'tab1',
		position :"top"
	});
	tab.add( {
		id :'tab1_index1',
		title :"主页",
		url :"<%=basePath%>login_default.do",
		isClosed :false
	});
	/**tab.add( {
		id :'tab1_index1',
		title :"主页",
		url :"/per/undoTask!gettwo",
		isClosed :false
	});
	**/
});

	function cmainFrameT(){
		var hmainT = document.getElementById("page");
		var bheightT = document.documentElement.clientHeight;
		hmainT .style.width = '100%';
		hmainT .style.height = (bheightT  - 51) + 'px';
	}
	cmainFrameT();
	window.onresize=function(){  
		cmainFrameT();
	};
	
	//清除加载进度
	function hangge(){
		$("#jzts").hide();
	}
	
	//显示加载进度
	function jzts(){
		$("#jzts").show();
	}

</script>
</html>

