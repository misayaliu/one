<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<html> 
  <head>
	<title>应用程序异常 (500)</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <style type="text/css"> 
        body { background-color: #fff; color: #666; text-align: center; font-family: arial, sans-serif; }
        div.dialog {
            width: 80%;
            padding: 1em 4em;
            margin: 4em auto 0 auto;
            border: 1px solid #ccc;
            border-right-color: #999;
            border-bottom-color: #999;
        }
        h1 { font-size: 100%; color: #f00; line-height: 1.5em; }
    </style> 
</head> 
 
<body> 
  <!-- 在添加修改界面中此处貌似是乱码，研究了2、3小时无果之后，暂时还没有解决方案，稍后有时间再研究 2015-2-26 by wei.feng -->
  <div class="dialog"> 
    <h1>应用程序异常</h1> 
    <p>抱歉！您访问的页面出现异常，请稍后重试或联系管理员。</p> 
    <p><a href="javascript:showErr();">详 情</a> 
	<a href="javascript:history.back(-1)">返 回</a> 
    </p> 
    <div style="display:none;text-align: left;" id="err">${exception}</div>
  </div>
  
  <script type="text/javascript">
  try{
	  window.parent.hangge();
  }catch(e){
	  
  }
  function showErr(){
  	document.getElementById("err").style.display = "";
  }
  </script>
</body> 
</html>
