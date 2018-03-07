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
	<link rel="stylesheet" type="text/css" href="easyui/themes/bootstrap/easyui.css">
	<link rel="stylesheet" type="text/css" href="easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="easyui/demo/demo.css">
	<link rel="stylesheet" type="text/css" href="easyui/themes/bootstrap/form.css">
	<script type="text/javascript" src="easyui/jquery.min.js"></script>
	<script type="text/javascript" src="easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="easyui/locale/easyui-lang-zh_CN.js"></script>
	<style type="text/css">
		.commitopacity{position:absolute; width:100%; height:100%; background:#7f7f7f; filter:alpha(opacity=50); -moz-opacity:0.8; -khtml-opacity: 0.5; opacity: 0.5; top:0px; z-index:99999;}
		</style>
	</head>
	
	
<body class="easyui-layout">
<div data-options="region:'center'" style="width:100%;height:100%;">
	<div id="tt" class="easyui-tabs" data-options="onContextMenu:onContextMenu,fit:true">
	</div>
</div>
<div style="width:100%;heiht:100%">
	<div id="jzts" style="display:none; width:100%;height:100%; position:fixed; z-index:99999999;">
		<div class="commitopacity" id="bkbgjz"></div>
			<div style="padding-left: 16%;padding-top: 2px;">
			<div style="float: left;margin-top: 7px;"><img src="images/loadingi.gif" /> </div>
			<div><h4 class="lighter block red">&nbsp;处理中  ...</h4></div>
		</div>
	</div>
</div>		
<div id="mm" class="easyui-menu" style="width:160px;">
	<div onclick="removeother()" data-options="iconCls:'icon-no'">关闭其它标签</div>
	<div onclick="removeall()" data-options="iconCls:'icon-cancel'">关闭所有标签</div>
</div>
</body>
<script type="text/javascript">
$(document).ready(function() {
	/*$('#tt').tabs('add',{
	    id:1,
		title: '主页',
		content: '<iframe src="login_default.do" name="WORKINGFRAME" scrolling="auto" style="overflow:auto" width="100%" height="100%" frameborder="200" />',
		closable: false
	});*/
}); 

function addPanel(treeid,text,control,iconCls){
	$('#tt').tabs('close', text);
    $('#tt').tabs('add',{
	    id:treeid,
		title: text,
		iconCls:iconCls,
		content: '<iframe src='+control+' name="WORKINGFRAME" scrolling="auto" style="overflow:auto" width="100%" height="100%" frameborder="200" />',
		closable: true
	});
}



function onContextMenu(e, title,index){
	$('#tt').tabs('select',index);
	e.preventDefault();
	//$(this).treegrid('select', row.id);
	$('#mm').menu('show',{
		left: e.pageX,
		top: e.pageY
	});
}

function removeother(){
    var tab = $('#tt').tabs('getSelected');
    var index = $('#tt').tabs('getTabIndex',tab);
	//alert(index);
	var tabs = $('#tt').tabs('tabs');
	//alert(tabs.length);
	for(var i=tabs.length-1;i>=0;i--){
		if(i!=index){
		    $('#tt').tabs('close', i);
		}
	}
}

function removeall(){
    var tab = $('#tt').tabs('getSelected');
    var index = $('#tt').tabs('getTabIndex',tab);
	//alert(index);
	var tabs = $('#tt').tabs('tabs');
	//alert(tabs.length);
	for(var i=tabs.length-1;i>=0;i--){
		$('#tt').tabs('close', i);
	}
}
	
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

