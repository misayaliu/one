package jieyi.app.controller.report;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jieyi.app.controller.BaseController;
import jieyi.app.dubbo.ConsoleService;
import jieyi.app.dubbo.JDBCDaoService;
import jieyi.app.form.report.t_report_base;
import jieyi.app.form.report.t_report_column;
import jieyi.app.form.report.t_report_query;
import jieyi.app.form.system.t_mng_userinfo;
import jieyi.app.util.Const;
import jieyi.app.util.JsonRequestReturn;
import jieyi.app.util.PageData;
import jieyi.app.util.report.ReportGenerator;
import jieyi.app.util.report.ReportProducer;
import jieyi.app.util.report.SelectHelper;
import jieyi.tools.util.DateUtil;
import jieyi.tools.util.StringUtil;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.reflect.TypeToken;

@Controller
@RequestMapping(value = "report")
public class ReportCtl extends BaseController {
	@Resource(name = "consoleService")
	private ConsoleService consoleService;
	
	@Resource(name = "JDBCDaoService")
	private JDBCDaoService jdbcDao;
	
	@RequestMapping("/loadPage.do")
	public ModelAndView loadPage(HttpServletRequest request,HttpServletResponse response) throws Exception {
		pd = this.getPageData();
		String reportId = pd.getString("report_id");
		if (reportId==null||reportId.equals("")) {
			writeJSONObject(request, response, "非法参数");
			return null;
		}	
		
		String reportStr = consoleService.selectRecordOne(gson.toJson(pd),"t_report_base.selectAll", "jieyi.app.util.PageData");
		t_report_base reportbase = gson.fromJson(reportStr,new TypeToken<t_report_base>() {}.getType());
		mv.addObject("reportbase", reportbase);
		String queryStr = consoleService.selectRecordList(gson.toJson(pd),"t_report_query.selectAll", "jieyi.app.util.PageData");
		List<t_report_query> querys = gson.fromJson(queryStr,new TypeToken<List<t_report_query>>() {}.getType());
		mv.addObject("querys", querys);
		
		//特殊的查询条件的处理
		
		mv.setViewName("report/report");
		mv.addObject("pd", pd);
		return mv;
	}
	
	@RequestMapping("/query.do")
	public String query(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws Exception {
		JsonRequestReturn requestReturn = new JsonRequestReturn();
		
		requestReturn.setResult(JsonRequestReturn.ERROR);
		pd = this.getPageData();
		String reportId = pd.getString("report_id");
		if (reportId==null||reportId.equals("")) {
			requestReturn.setField1("非法参数");
			writeJSONObject(request, response, requestReturn);
			return null;
		}		
		
		ReportGenerator reportGenerator = new ReportGenerator();
		//1、检查查询条件是否必输
		String valIsNeed = valIsNeed(request);
		if (!(valIsNeed.equals("0"))) {
			requestReturn.setField1(valIsNeed);
			writeJSONObject(request, response, requestReturn);
			return null;
		}
		
		//2、查询sql
		String reportStr = consoleService.selectRecordOne(gson.toJson(pd),"t_report_base.selectAll", "jieyi.app.util.PageData");
		t_report_base reportbase = gson.fromJson(reportStr,new TypeToken<t_report_base>() {}.getType());
		String sql = reportGenerator.sqlArrange(reportbase.getSqls(),getAutoReportParams(request).getParas());
		//2.1 处理登陆人的机构商户信息
		t_mng_userinfo user = (t_mng_userinfo) session.getAttribute(Const.SESSION_USER);
		String userid =user.getUserid();
		String instSql ="select NVL2(t.instcd,t.instcd,'00000000')||NVL2(t.mchnt_no,t.mchnt_no,'000000000000000') from tbl_mng_userinstrel t where t.userid='"+userid+"'";
		String [][]instAndMchnt = jdbcDao.getTDArrayBySql(instSql);
		sql = reportGenerator.sqlArrangeForInstAndMchnt(sql, instAndMchnt[0][0]);
		sql = sql.replaceAll("\r\n", " ");
		logger.info("*************************已经隔离机构和商户的sql:"+sql);
		//3、根据sql查询到数组
		String[][] data = jdbcDao.getTDArrayBySql(sql,1000);
		if(data==null||data.length==0){
			requestReturn.setField1("未查询到记录");
			writeJSONObject(request, response, requestReturn);
			return null;
		}
		
		//4、根据reportId查询到列信息
		String reportcolumnStr = consoleService.selectRecordList(gson.toJson(pd),"t_report_column.selectAll", "jieyi.app.util.PageData");
		List<t_report_column> reportRpColumns = gson.fromJson(reportcolumnStr,new TypeToken<List<t_report_column>>() {}.getType());
		
		try{
			ReportProducer reportProducer=new ReportProducer(reportRpColumns,reportbase,data,"","");
			
			try{
				requestReturn.setResult(JsonRequestReturn.SUCCESS);
				requestReturn.setField1(reportProducer.getQueryResult());
			} catch(Exception e){
				logger.error(e.toString(), e);
				requestReturn.setField1(e.toString());
			}
			
			writeJSONObject(request,response,requestReturn);
			
			//writeJSONObject(request, response, reportProducer.getQueryResult());
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return null;
	}

	@RequestMapping("/printWatch.do")
	public ModelAndView printWatch(HttpServletRequest request,HttpServletResponse response) throws Exception {
		pd = this.getPageData();
		String reportId = pd.getString("report_id");
		if (reportId==null||reportId.equals("")) {
			writeJSONObject(request, response, "非法参数");
			return null;
		}		
		
		ReportGenerator reportGenerator = new ReportGenerator();
		//1、查询sql
		String reportStr = consoleService.selectRecordOne(gson.toJson(pd),"t_report_base.selectAll", "jieyi.app.util.PageData");
		t_report_base reportbase = gson.fromJson(reportStr,new TypeToken<t_report_base>() {}.getType());
		String sql = reportGenerator.sqlArrange(reportbase.getSqls(),getAutoReportParams(request).getParas());
		//2.1 处理登陆人的机构商户信息
		t_mng_userinfo user = (t_mng_userinfo) request.getSession().getAttribute(Const.SESSION_USER);
		String userid =user.getUserid();
		String instSql ="select NVL2(t.instcd,t.instcd,'00000000')||NVL2(t.mchnt_no,t.mchnt_no,'000000000000000') from tbl_mng_userinstrel t where t.userid='"+userid+"'";
		String [][]instAndMchnt = jdbcDao.getTDArrayBySql(instSql);
		sql = reportGenerator.sqlArrangeForInstAndMchnt(sql, instAndMchnt[0][0]);
		sql = sql.replaceAll("\r\n", " ");
		logger.info("*************************已经隔离机构和商户的sql:"+sql);
		
		//2、根据sql查询到数组
		String[][] data = jdbcDao.getTDArrayBySql(sql,3000);
		if(data==null||data.length==0){
			writeJSONObject(request, response, "未查询到记录");
			return null;
		}
		
		Map rtn=new HashMap();
		if(data==null||data.length==0){
			mv.addObject("report","未查询到记录！");
		}else{
			//3、根据reportId查询到列信息
			String reportcolumnStr = consoleService.selectRecordList(gson.toJson(pd),"t_report_column.selectAll", "jieyi.app.util.PageData");
			List<t_report_column> reportRpColumns = gson.fromJson(reportcolumnStr,new TypeToken<List<t_report_column>>() {}.getType());
			ParamsBack paramsBack = getAutoReportParams(request);
			String parametersStr = getParametersStr(request);
			ReportProducer reportProducer=new ReportProducer(reportRpColumns,reportbase,data,paramsBack.getDateInfo(),parametersStr);
			mv.addObject("report", reportProducer.getPrintWatchReport());
		}
		mv.addObject("reportbase",reportbase);
		mv.setViewName("report/reportPrint");
		return mv;
	}
	
	
	@RequestMapping("/excel.do")
	public String excel(HttpServletRequest request,HttpServletResponse response) throws Exception {
		String reportId = request.getParameter("report_id");
		if (reportId==null||reportId.equals("")) {
			writeJSONObject(request, response, "非法参数");
			return null;
		}		
		
		ReportGenerator reportGenerator = new ReportGenerator();
		//1、查询sql
		String reportStr = consoleService.selectRecordOne(gson.toJson(pd),"t_report_base.selectAll", "jieyi.app.util.PageData");
		t_report_base reportbase = gson.fromJson(reportStr,new TypeToken<t_report_base>() {}.getType());
		String sql = reportGenerator.sqlArrange(reportbase.getSqls(),getAutoReportParams(request).getParas());
		//2.1 处理登陆人的机构商户信息
		t_mng_userinfo user = (t_mng_userinfo) request.getSession().getAttribute(Const.SESSION_USER);
		String userid =user.getUserid();
		String instSql ="select NVL2(t.instcd,t.instcd,'00000000')||NVL2(t.mchnt_no,t.mchnt_no,'000000000000000') from tbl_mng_userinstrel t where t.userid='"+userid+"'";
		String [][]instAndMchnt = jdbcDao.getTDArrayBySql(instSql);
		sql = reportGenerator.sqlArrangeForInstAndMchnt(sql, instAndMchnt[0][0]);
		sql = sql.replaceAll("\r\n", " ");
		logger.info("*************************已经隔离机构和商户的sql:"+sql);

		//2、根据sql查询到数组
		String[][] data = jdbcDao.getTDArrayBySql(sql,3000);
		
		Map rtn=new HashMap();
		rtn.put("reportbase",reportbase);
		if(data==null||data.length==0){
			rtn.put("report","未查询到记录！");
			writeJSONObject(request, response, "未查询到记录！");
		}else{
			//3、根据reportId查询到列信息
			String reportcolumnStr = consoleService.selectRecordList(gson.toJson(pd),"t_report_column.selectAll", "jieyi.app.util.PageData");
			List<t_report_column> reportRpColumns = gson.fromJson(reportcolumnStr,new TypeToken<List<t_report_column>>() {}.getType());
			ParamsBack paramsBack = getAutoReportParams(request);
			String parametersStr = getParametersStr(request);
			ReportProducer reportProducer=new ReportProducer(reportRpColumns,reportbase,data,paramsBack.getDateInfo(),parametersStr);
			//request.setAttribute("report", reportProducer.getExcelReport());
			
			download(response, new String(reportbase.getReport_name().getBytes("gb2312"),"ISO-8859-1")+"_"+DateUtil.getSystemDateTime("yyyyMMddHHmmss")+".xls", reportProducer.getExcelReport().getBytes("utf-8"));
		}
		
		return null;
	}
	
	/*
	 * 检查查询条件是否必输
	 */
	private String valIsNeed(HttpServletRequest request) {
		// TODO Auto-generated method stub
		String returnValue = "0";
		String[] inputTypeNames = request.getParameterValues("input_typeName");
		String[] inputTypeNameChns = request
				.getParameterValues("input_typeNameChn");
		String[] inputTypeIsNedds = request
				.getParameterValues("input_typeis_need");
		if (inputTypeNames == null) {
			return returnValue;
		}
		ParamsBack paramsBack = new ParamsBack();
		paramsBack.createParas(Integer.parseInt(request
				.getParameter("query_domain_count")));
		List<StringBuffer> paras = paramsBack.getParas();
		for (int i = 0; i < inputTypeNames.length; i++) {
			String value = request.getParameter(inputTypeNames[i]);
			if (inputTypeIsNedds[i].equals("2")) {// 必填
				if (value == null || value == "") {
					returnValue = inputTypeNameChns[i] + "不能为空！";
					break;
				}
			}
		}
		
		return returnValue;
	}
	
	/*
	 * 获取自动生成报表参数
	 */
	private ParamsBack getAutoReportParams(HttpServletRequest request)
			throws Exception {
		String[] inputTypeNames = request.getParameterValues("input_typeName");
		String[] inputTypeNameChns = request
				.getParameterValues("input_typeNameChn");
		String[] inputTypeIsNedds = request
				.getParameterValues("input_typeis_need");
		String[] queryDomainss = request.getParameterValues("query_domains");
		SelectHelper selectHelper = new SelectHelper(jdbcDao);
		if (inputTypeNames == null || inputTypeNames.length == 0)
			return new ParamsBack();
		ParamsBack paramsBack = new ParamsBack();
		paramsBack.createParas(Integer.parseInt(request
				.getParameter("query_domain_count")));
		List<StringBuffer> paras = paramsBack.getParas();

		String[] inputTypeTypes = request.getParameterValues("input_typeType");
		String[] queryDomains;
		for (int i = 0; i < inputTypeNames.length; i++) {
			if ("1".equals(inputTypeTypes[i])) { // 输入框
				String value = request.getParameter(inputTypeNames[i]);
				if (value == null||value.equals(""))
					continue;
				String[] queryDomainsDtl = queryDomainss[0].split(":");
				paras.get(Integer.parseInt(queryDomainsDtl[0]) - 1).append(
						" and ").append(queryDomainsDtl[1]).append(" ='")
						.append(value).append("' ");
				
				
//				queryDomains = queryDomainss[i].split(",");
//				for (int j = 0; j < queryDomains.length; j++) {
//					String[] queryDomainsDtl = queryDomains[j].split(":");
//					paras.get(Integer.parseInt(queryDomainsDtl[0]) - 1).append(
//							" and ").append(queryDomainsDtl[1]).append(" ='")
//							.append(value).append("' ");
//				}
			} else if ("2".equals(inputTypeTypes[i])) { //日期选择框
				String value = request.getParameter(inputTypeNames[i]);
				if (value == null||value.equals(""))
					continue;
				queryDomains = queryDomainss[i].split(",");
				for (int j = 0; j < queryDomains.length; j++) {
					String[] queryDomainsDtl = queryDomains[j].split(":");
					paras.get(Integer.parseInt(queryDomainsDtl[0]) - 1).append(
							" and ").append(queryDomainsDtl[1]).append(" ='")
							.append(value.replace("-", "").replace("-", ""))
							.append("' ");
				}
				paramsBack.setDateInfo(paramsBack.getDateInfo()==null?"":paramsBack.getDateInfo()
						+ inputTypeNameChns[i] + "：" + value);
			} else if ("3".equals(inputTypeTypes[i])) { // 模糊下拉框
				String dateStart = request.getParameter(inputTypeNames[i]
						+ "_start");
				String dateEnd = request.getParameter(inputTypeNames[i]
						+ "_end");
				if (!(dateStart == null||dateStart.equals(""))
						&& !(dateEnd == null||dateEnd.equals(""))) {
					paramsBack.setDateInfo(paramsBack.getDateInfo()==null?"":paramsBack.getDateInfo()
							+ inputTypeNameChns[i]
							+ "："
							+ dateStart
							+ "至"
							+ dateEnd);
				} else if (dateStart!=null&&!dateStart.equals("")) {
					paramsBack.setDateInfo(paramsBack.getDateInfo()==null?"":paramsBack.getDateInfo()
							+ inputTypeNameChns[i] + "：" + dateStart + "以后");
				} else if (dateEnd!=null&&!dateEnd.equals("")) {
					paramsBack.setDateInfo(paramsBack.getDateInfo()==null?"":paramsBack.getDateInfo()
							+ inputTypeNameChns[i] + ": " + dateEnd + "至今");
				}
				if (dateStart!=null&&!dateStart.equals("")) {
					queryDomains = queryDomainss[i].split(",");
					for (int j = 0; j < queryDomains.length; j++) {
						String[] queryDomainsDtl = queryDomains[j].split(":");
						paras.get(Integer.parseInt(queryDomainsDtl[0]) - 1)
								.append(" and ").append(queryDomainsDtl[1])
								.append(" >='").append(
										dateStart.replace("-", "").replace("-",
												"")).append("' ");
					}
				}
				if (dateEnd!=null&&!dateEnd.equals("")) {
					queryDomains = queryDomainss[i].split(",");
					for (int j = 0; j < queryDomains.length; j++) {
						String[] queryDomainsDtl = queryDomains[j].split(":");
						paras.get(Integer.parseInt(queryDomainsDtl[0]) - 1)
								.append(" and ").append(queryDomainsDtl[1])
								.append(" <= '").append(
										dateEnd.replace("-", "").replace("-",
												"")).append("' ");
					}
				}
			} else if ("4".equals(inputTypeTypes[i])) { //模糊下拉框
				String value = request.getParameter(inputTypeNames[i]);
				if (value == null||value.equals(""))
					continue;
				queryDomains = queryDomainss[i].split(",");
				for (int j = 0; j < queryDomains.length; j++) {
					String[] queryDomainsDtl = queryDomains[j].split(":");
					paras.get(Integer.parseInt(queryDomainsDtl[0]) - 1).append(
							" and ").append(queryDomainsDtl[1]).append(" ='")
							.append(value).append("' ");
				}
			} else if ("5".equals(inputTypeTypes[i])) { //模糊下拉框(带权限)
				String value = request.getParameter(inputTypeNames[i]);
				if (value == null||value.equals(""))
					continue;
				queryDomains = queryDomainss[i].split(",");
				for (int j = 0; j < queryDomains.length; j++) {
					String[] queryDomainsDtl = queryDomains[j].split(":");
					paras.get(Integer.parseInt(queryDomainsDtl[0]) - 1).append(
							" and ").append(queryDomainsDtl[1]).append(" ='")
							.append(value).append("' ");
				}
			} else if ("6".equals(inputTypeTypes[i])) { //级联模糊下拉框
				String value = request.getParameter(inputTypeNames[i]);
				if (value == null||value.equals(""))
					continue;
				queryDomains = queryDomainss[i].split(",");
				for (int j = 0; j < queryDomains.length; j++) {
					String[] queryDomainsDtl = queryDomains[j].split(":");
					paras.get(Integer.parseInt(queryDomainsDtl[0]) - 1).append(
							" and ").append(queryDomainsDtl[1]).append(" ='")
							.append(value).append("' ");
				}
			}

		}

		return paramsBack;
	}
	
	private String getParametersStr(HttpServletRequest request) throws Exception {
		SelectHelper selectHelper = new SelectHelper(jdbcDao);
		String[] inputTypeNames = request.getParameterValues("input_typeName");
		String[] inputTypeNames1 = request.getParameterValues("input_typeName1");
		String[] inputTypeNameChns = request
				.getParameterValues("input_typeNameChn");
		String[] queryDomainss = request.getParameterValues("query_domains");
		if (inputTypeNames == null || inputTypeNames.length == 0)
			return "";
		String paramsBack = "";
		String[] inputTypeTypes = request.getParameterValues("input_typeType");
		String[] queryDomains;
		String[] vagues = {"4","5","6"};
		for (int i = 0; i < inputTypeNames.length; i++) {
			String name = inputTypeNameChns[i];
			if ("1".equals(inputTypeTypes[i])) { // 输入框
				String value = request.getParameter(inputTypeNames[i]);
				if (value == null||value.equals(""))
					continue;
				paramsBack += "&nbsp;&nbsp;&nbsp;&nbsp;" + name + ":" + value;
			} else if ("2".equals(inputTypeTypes[i])) { //日期
				String value = request.getParameter(inputTypeNames[i]);
				if (value == null||value.equals(""))
					continue;
				paramsBack += "&nbsp;&nbsp;&nbsp;&nbsp;" + name + ":" + value;
			} else if ("3".equals(inputTypeTypes[i])) { //日期范围
				String dateStart = request.getParameter(inputTypeNames[i]
				+ "_start");
				String dateEnd = request.getParameter(inputTypeNames[i]
						+ "_end");
				
				if (!(dateStart == null||dateStart.equals(""))
						&& !(dateEnd == null||dateEnd.equals(""))) {
					paramsBack += "&nbsp;&nbsp;&nbsp;&nbsp;" + name + ":"
							+ dateStart + "至" + dateEnd;
				} else if (dateStart!=null&&!dateStart.equals("")) {
					paramsBack += "&nbsp;&nbsp;&nbsp;&nbsp;" + name + ":"
							+ dateStart + "以后";
				} else if (dateEnd!=null&&!dateEnd.equals("")) {
					paramsBack += "&nbsp;&nbsp;&nbsp;&nbsp;" + name + ":"
							+ dateEnd + "为止";
				}
			} else if (StringUtil.isValueIn(inputTypeTypes[i], vagues)) { //模糊选择
				String value = request.getParameter(inputTypeNames1[i]);
				if (value == null||value.equals(""))
					continue;
				paramsBack += "&nbsp;&nbsp;&nbsp;&nbsp;" + name + ":" + value;
			}
		}

		return paramsBack;
	}
	
	private class ParamsBack {
		private List<StringBuffer> paras = new ArrayList<StringBuffer>();
		private String dateInfo = "";

		public List<StringBuffer> getParas() {
			return paras;
		}

		public void setParas(List<StringBuffer> paras) {
			this.paras = paras;
		}

		public String getDateInfo() {
			return dateInfo;
		}

		public void setDateInfo(String dateInfo) {
			this.dateInfo = dateInfo;
		}

		public void createParas(int count) {
			for (int i = 0; i < count; i++) {
				paras.add(new StringBuffer());
			}
		}

	}
}
