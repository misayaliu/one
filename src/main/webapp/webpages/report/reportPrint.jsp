<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="/webpages/tld/c.tld"%>
<%@ page isELIgnored="false"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">
    <title>捷羿报表系统</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
<%@ include file="/headincludepage.jsp"%>

	<link rel="stylesheet" type="text/css" href="resources/css/report_print.css">
	<link rel="stylesheet" type="text/css" href="resources/css/report.css">
	
  </head>
  
  <body>
  
  
  <center>
  		<div id="toolbar">
	  		<select name="setTableWidth" onchange="resetTableWidth(this)" >
			  	<option value="80%">报表宽度</option>
			  	<option value="50%">50%</option>
			  	<option value="60%">60%</option>
			  	<option value="70%">70%</option>
			  	<option value="80%">80%</option>
			  	<option value="85%">85%</option>
			  	<option value="90%">90%</option>
			  	<option value="95%">95%</option>
			  	<option value="100%">100%</option>
			</select>
			<select onchange="resetFontSize(this)"  >
			  	<option value="2">字体大小</option>
			  	<option value="1">1</option>
			  	<option value="2">2</option>
			  	<option value="3">3</option>
			  	<option value="4">4</option>
			  	<option value="5">5</option>
			  	<option value="6">6</option>
			  	<option value="7">7</option>
			</select>	
			<select onchange="toBottom(this)"  >
			  	<option value="0">报表下移</option>
			  	<option value="2">2行</option>
			  	<option value="5">5行</option>
			  	<option value="10">10行</option>
			  	<option value="15">15行</option>
			  	<option value="20">20行</option>
			</select>	
			<input type="button" onclick="winPrint()" value="打印" >
  		</div>
  		<div id="toBottom" class="mt2"></div>
    	${report}
		
  </center>
	<%@ include file="/tailincludepage.jsp"%>
	<script type="text/javascript">
		function resetTableWidth(select){
			$("table").attr("width",select.value); 
		}

		function resetFontSize(select){
			$("font").attr("size",select.value); 
		}

		function toBottom(select){
			var size=parseInt(select.value);
			var brs="";
			for(var i=0;i<size;i++){
				brs+="<br/>";
			}
			$('#toBottom').html(brs);
		}

		function winPrint(){
			$('#toolbar').css({'display':'none'});
			window.print();
		}
	</script>
  </body>
</html>
