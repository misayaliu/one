<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="jy" uri="/webpages/tld/jyTag.tld"%>
    <%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/headincludepage.jsp"%>
<%@ include file="/tailincludepage.jsp"%>

<title>图表</title>

</head>
<body>
<div>
    
    <div class="main-container" id="main-container">
		<script type="text/javascript">
				try{ace.settings.check('main-container' , 'fixed')}catch(e){}
			</script>

		<!-- /section:basics/sidebar -->
		<div class="main-content">
			<div class="main-content-inner">

				<!-- /section:basics/content.breadcrumbs -->
				<div class="page-content">
					<form id="searchForm" name="searchForm" target="ifreport"
						method="POST" action="">
						<input
							type="hidden" name="chart_id" value="${pd.chart_id}" />
						<table class="table table-striped table-bordered table-hover">
							<tr>
								<c:forEach items="${querys}" var="chartQuery" varStatus="vs">
									<input type="hidden" name="input_typeName"
										value="${chartQuery.chart_query_eng}" />
									<input type="hidden" name="input_typeType"
										value="${chartQuery.chart_query_type}" />
									<input type="hidden" name="input_typeNameChn"
										value="${chartQuery.chart_query_chn}" />
									<input type="hidden" name="query_domains"
										value="${chartQuery.chart_query_domains}" />
									<input type="hidden" name="query_domain_count"
										value="${chartbase.query_domain_count}" />
									<input type="hidden" name="out_key_name"
										value="${chartQuery.chart_query_outkeyname}" />
									<c:choose>
										<c:when test="${chartQuery.chart_query_type==1}">
											<td>
												${chartQuery.chart_query_chn}:
											</td>
											<td> 
												<div class="row">
													<div class="col-sm-12">
														<input class="form-control" type="text" size="22" name="${chartQuery.chart_query_eng}" placeholder="这里输入${chartQuery.chart_query_chn}" />
													</div>
												</div>
											</td>
										</c:when>
										<c:when test="${chartQuery.chart_query_type=='12'}">
											<td>${chartQuery.chart_query_chn}:  ${chartQuery.chart_query_spdata}
											</td>
										</c:when>
									</c:choose>
									<c:if test="${(vs.index+1)%2==0}">
										</tr>
										<tr>
									</c:if>
								</c:forEach>
							</tr>
							<tr>
								<td colspan="6" class="center"><span class="btn btn-purple btn-sm tooltip-info"
									data-rel="tooltip" onclick="query(document.searchForm);"
									data-placement="bottom"><i
										class="ace-icon fa fa-search bigger-100"></i>查询</span> </td>
							</tr>
						</table>

					</form>

					<div class="row">
						<div class="col-xs-12">
							<!-- PAGE CONTENT BEGINS -->
							<div class="row">
								<div class="col-xs-12" id="reportTable"></div>
							</div>
						</div>
					</div>
				</div>
				
			    <!-- 为ECharts准备一个具备大小（宽高）的Dom -->
			    <div id="main" style="height:400px;width:800px;"></div>
				</div>
			</div>
		</div>
  </div>
  
  <script type="text/javascript" src="selfjs/common/echart/echarts.js"></script>
     <script src="selfjs/common/echart/jieyiChart.js" type="text/javascript"></script>
 <script type="text/javascript">
 var legData = [];
 var seriesData = [];
function query(){
	//var queryString = $('#searchForm').formSerialize(); 
	//var queryString{};
	//queryString = geneSubmitDataFromForm("#searchForm",submitData);
	var queryString = $('#searchForm').serialize(); 
	    $.ajax({
         url: "jieyichart/chartJson.do",
         data: queryString,
         cache: false,
         async: false,
         type : 'POST',
         dataType: 'json',

         success: function (data) {
        	 var option = {};
        	
        	 if(data.chartType == "pie"){
        		 option = ECharts.ChartOption.Pie(data);
        	 }else if(data.chartType == "bar" || data.chartType == "line"){
        		 option = ECharts.ChartOption.Bars(data);
        	 }
        	 
        	var container = document.getElementById("main");
        	var opt = ECharts.ChartRequireConfig(container,option); 
        	   ECharts.Charts.RenderChart(opt);
         },

         error: function (msg) {
             alert("系统发生错误");
         }

     }); 
}
	</script>
</body>
</html>