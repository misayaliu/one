package jieyi.app.controller.chart;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jieyi.app.controller.BaseController;
import jieyi.app.dubbo.ConsoleService;
import jieyi.app.dubbo.JDBCDaoService;
import jieyi.app.dubbo.PriviligeService;
import jieyi.app.form.chart.t_chart_base;
import jieyi.app.form.chart.t_chart_column;
import jieyi.app.form.chart.t_chart_query;
import jieyi.app.util.ChartUtil;
import jieyi.app.util.JsonRequestReturn;
import jieyi.app.util.report.ReportGenerator;
import jieyi.app.util.report.SelectHelper;
import jieyi.tools.util.StringUtil;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.reflect.TypeToken;

@Controller
@RequestMapping(value = "jieyichart")
public class jieyichartCtl extends BaseController {

	@Resource(name = "priviligeService")
	private PriviligeService priviligeService;

	@Resource(name = "consoleService")
	private ConsoleService consoleService;
	
	@Resource
	private JDBCDaoService jdbcDao;
	
	@RequestMapping("/loadPage")
	public ModelAndView loadPage(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		pd = this.getPageData();
		String reportId = pd.getString("chart_id");
		if (reportId==null||reportId.equals("")) {
			writeJSONObject(request, response, "非法参数");
			return null;
		}		
		
		mv.setViewName("chart/jieyichart");
		mv.addObject("dataList", null);
		mv.addObject("pageStr", "");
		mv.addObject("loadPage", "0");
		
		String reportStr = consoleService.selectRecordOne(gson.toJson(pd),"t_chart_base.selectAll", "jieyi.app.util.PageData");
		t_chart_base chartbase = gson.fromJson(reportStr,new TypeToken<t_chart_base>() {}.getType());
		mv.addObject("chartbase", chartbase);
		
		String queryStr = consoleService.selectRecordList(gson.toJson(pd),"t_chart_query.selectlist", "jieyi.app.util.PageData");
		
		List<t_chart_query> querys = gson.fromJson(queryStr,
				new TypeToken<List<t_chart_query>>() {
				}.getType());
		
		SelectHelper selectHelper = new SelectHelper(jdbcDao);
		for(int i=0;i<querys.size();i++){
			t_chart_query reportRpQuery=querys.get(i);
//			if(reportRpQuery.getChart_query_type().equals("1")){
//				reportRpQuery.setChart_query_spdata(selectHelper.getSelectByDictEng(reportRpQuery.getChart_query_outkeyname(),reportRpQuery.getChart_query_eng()));
//			}
		}
		mv.addObject("querys", querys);
		mv.addObject("pd", pd);
		return mv;
	}
	
	@RequestMapping("/chartJson")
	public void chartJson(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		pd = this.getPageData();
		
		String reportId = pd.getString("chart_id");
		if (reportId==null||reportId.equals("")) {
			writeJSONObject(request, response, "非法参数");
		}		
		
		ReportGenerator reportGenerator = new ReportGenerator();
		//1、整理sql
		
		String tchartbaseStr = consoleService.selectRecordOne(gson.toJson(pd),
				"t_chart_base.selectOne", "jieyi.app.util.PageData");
		t_chart_base tchartbase = gson.fromJson(tchartbaseStr,
				new TypeToken<t_chart_base>() {
				}.getType());
		
		String tchartcolumnStr = consoleService.selectRecordList(gson.toJson(pd),
				"t_chart_column.selectlist", "jieyi.app.util.PageData");
		List<t_chart_column> tchartcolumn = gson.fromJson(tchartcolumnStr,
				new TypeToken<List<t_chart_column>>() {
				}.getType());
		JsonRequestReturn requestReturn = new JsonRequestReturn();
		String sqlStr = reportGenerator.sqlArrange(tchartbase.getChart_sql(),getAutoReportParams(request).getParas());
		sqlStr = sqlStr.replaceAll("\r\n", " ");
		String[][] srs = jdbcDao.getTDArrayBySql(sqlStr);
		
		Map<String, Object> mapStr = ChartUtil.convertChart(tchartbase, tchartcolumn, srs);
		requestReturn.setResult(JsonRequestReturn.SUCCESS);
		requestReturn.setField1(mapStr);
		writeJSONObject(request, response, mapStr);
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
				if (StringUtil.isNull(value))
					continue;
				
				String[] queryDomainsDtl = queryDomainss[0].split(":");
				paras.get(Integer.parseInt(queryDomainsDtl[0]) - 1).append(
						" and ").append(queryDomainsDtl[1]).append(" ='")
						.append(value).append("' ");
				
//				paras.get(Integer.parseInt(queryDomainss[0]) - 1).append(
//						" and ").append(queryDomainsDtl[1]).append(" ='")
//						.append(value).append("' ");
//				queryDomains = queryDomainss[i].split(",");
//				for (int j = 0; j < queryDomains.length; j++) {
//					String[] queryDomainsDtl = queryDomains[j].split(":");
//					paras.get(Integer.parseInt(queryDomainsDtl[0]) - 1).append(
//							" and ").append(queryDomainsDtl[1]).append(" ='")
//							.append(value).append("' ");
//				}
			} else if ("2".equals(inputTypeTypes[i])) { // 下拉框
				String value = request.getParameter(inputTypeNames[i]);
				if (StringUtil.isNull(value))
					continue;
				queryDomains = queryDomainss[i].split(",");
				for (int j = 0; j < queryDomains.length; j++) {
					String[] queryDomainsDtl = queryDomains[j].split(":");
					paras.get(Integer.parseInt(queryDomainsDtl[0]) - 1).append(
							" and ").append(queryDomainsDtl[1]).append(" ='")
							.append(value).append("' ");
				}
			} else if ("3".equals(inputTypeTypes[i])) { // 模糊下拉框
				String value = request.getParameter(inputTypeNames[i]);
				if (StringUtil.isNull(value))
					continue;
				queryDomains = queryDomainss[i].split(",");
				for (int j = 0; j < queryDomains.length; j++) {
					String[] queryDomainsDtl = queryDomains[j].split(":");
					paras.get(Integer.parseInt(queryDomainsDtl[0]) - 1).append(
							" and ").append(queryDomainsDtl[1]).append(" ='")
							.append(value).append("' ");
				}
			} else if ("4".equals(inputTypeTypes[i])) { // 日期选择框
				String value = request.getParameter(inputTypeNames[i]);
				if (StringUtil.isNull(value))
					continue;
				queryDomains = queryDomainss[i].split(",");
				for (int j = 0; j < queryDomains.length; j++) {
					String[] queryDomainsDtl = queryDomains[j].split(":");
					paras.get(Integer.parseInt(queryDomainsDtl[0]) - 1).append(
							" and ").append(queryDomainsDtl[1]).append(" ='")
							.append(value.replace("-", "").replace("-", ""))
							.append("' ");
				}
				paramsBack.setDateInfo(StringUtil.nvl(paramsBack.getDateInfo(),
						"")
						+ inputTypeNameChns[i] + "：" + value);
			} else if ("5".equals(inputTypeTypes[i])) { // 日期范围框
				String dateStart = request.getParameter(inputTypeNames[i]
						+ "_start");
				String dateEnd = request.getParameter(inputTypeNames[i]
						+ "_end");
				if (!StringUtil.isNull(dateStart)
						&& !StringUtil.isNull(dateEnd)) {
					paramsBack.setDateInfo(StringUtil.nvl(paramsBack
							.getDateInfo(), "")
							+ inputTypeNameChns[i]
							+ "："
							+ dateStart
							+ "至"
							+ dateEnd);
				} else if (!StringUtil.isNull(dateStart)) {
					paramsBack.setDateInfo(StringUtil.nvl(paramsBack
							.getDateInfo(), "")
							+ inputTypeNameChns[i] + "：" + dateStart + "以后");
				} else if (!StringUtil.isNull(dateEnd)) {
					paramsBack.setDateInfo(StringUtil.nvl(paramsBack
							.getDateInfo(), "")
							+ inputTypeNameChns[i] + ": " + dateEnd + "至今");
				}
				if (!StringUtil.isNull(dateStart)) {
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
				if (!StringUtil.isNull(dateEnd)) {
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
			} else if ("6".equals(inputTypeTypes[i])) { // http参数
				String value = request.getParameter(inputTypeNames[i]);
				if (StringUtil.isNull(value))
					continue;
				queryDomains = queryDomainss[i].split(",");
				for (int j = 0; j < queryDomains.length; j++) {
					String[] queryDomainsDtl = queryDomains[j].split(":");
					paras.get(Integer.parseInt(queryDomainsDtl[0]) - 1).append(
							" and ").append(queryDomainsDtl[1]).append(" ='")
							.append(value).append("' ");
				}
			} else if ("7".equals(inputTypeTypes[i])) { // Date,Timestamp类型的
				// 日期选择框
				String value = request.getParameter(inputTypeNames[i]);
				if (!StringUtil.isNull(value)) {
					paramsBack.setDateInfo(StringUtil.nvl(paramsBack
							.getDateInfo(), "")
							+ inputTypeNameChns[i] + "：" + value);
					queryDomains = queryDomainss[i].split(",");
					for (int j = 0; j < queryDomains.length; j++) {
						String[] queryDomainsDtl = queryDomains[j].split(":");
						if (queryDomainsDtl[1].indexOf(">") == -1
								&& queryDomainsDtl[1].indexOf("<") == -1) {
							paras.get(Integer.parseInt(queryDomainsDtl[0]) - 1)
									.append(" and ").append(
											" to_char(" + queryDomainsDtl[1]
													+ ",'yyyymmdd') ").append(
											" ='").append(
											value.replace("-", "").replace("-",
													"")).append("' ");
							;
						} else {
							int index = queryDomainsDtl[1].indexOf(">");
							if (index != -1) {
								paras
										.get(
												Integer
														.parseInt(queryDomainsDtl[0]) - 1)
										.append(" and ").append(
												" to_char("
														+ queryDomainsDtl[1]
																.substring(0,
																		index)
														+ ",'yyyymmdd') ")
										.append(
												queryDomainsDtl[1]
														.substring(index)
														+ "'").append(
												value.replace("-", "").replace(
														"-", "")).append("' ");
								;
							} else {
								index = queryDomainsDtl[1].indexOf("<");
								paras
										.get(
												Integer
														.parseInt(queryDomainsDtl[0]) - 1)
										.append("*").append(" and ").append(
												" to_char("
														+ queryDomainsDtl[1]
																.substring(0,
																		index)
														+ ",'yyyymmdd') ")
										.append(
												queryDomainsDtl[1]
														.substring(index)
														+ "'").append(
												value.replace("-", "").replace(
														"-", "")).append("' ");
								;
								String xx = "sss";
								System.out.println(xx);
							}
						}
					}
				}
			} else if ("8".equals(inputTypeTypes[i])) { // Date,Timestamp类型的
				// 日期范围框
				String dateStart = request.getParameter(inputTypeNames[i]
						+ "_start");
				String dateEnd = request.getParameter(inputTypeNames[i]
						+ "_end");
				if (!StringUtil.isNull(dateStart)
						&& !StringUtil.isNull(dateEnd)) {
					paramsBack.setDateInfo(StringUtil.nvl(paramsBack
							.getDateInfo(), "")
							+ inputTypeNameChns[i]
							+ "："
							+ dateStart
							+ "至"
							+ dateEnd);
				} else if (!StringUtil.isNull(dateStart)) {
					paramsBack.setDateInfo(StringUtil.nvl(paramsBack
							.getDateInfo(), "")
							+ inputTypeNameChns[i] + "：" + dateStart + "以后");
				} else if (!StringUtil.isNull(dateEnd)) {
					paramsBack.setDateInfo(StringUtil.nvl(paramsBack
							.getDateInfo(), "")
							+ inputTypeNameChns[i] + "：" + dateEnd + "至今");
				}

				if (!StringUtil.isNull(dateStart)) {
					queryDomains = queryDomainss[i].split(",");
					for (int j = 0; j < queryDomains.length; j++) {
						String[] queryDomainsDtl = queryDomains[j].split(":");
						paras.get(Integer.parseInt(queryDomainsDtl[0]) - 1)
								.append(" and ").append(
										" to_char(" + queryDomainsDtl[1]
												+ ",'yyyymmdd') ").append(
										" >='").append(
										dateStart.replace("-", "").replace("-",
												"")).append("' ");
					}
				}
				if (!StringUtil.isNull(dateEnd)) {
					queryDomains = queryDomainss[i].split(",");
					for (int j = 0; j < queryDomains.length; j++) {
						String[] queryDomainsDtl = queryDomains[j].split(":");
						paras.get(Integer.parseInt(queryDomainsDtl[0]) - 1)
								.append(" and ").append(
										" to_char(" + queryDomainsDtl[1]
												+ ",'yyyymmdd') ").append(
										" <='").append(
										dateEnd.replace("-", "").replace("-",
												"")).append("' ");
					}
				}
			} else if ("9".equals(inputTypeTypes[i])) { // 月查询
				String year = request.getParameter(inputTypeNames[i] + "_year");
				String month = request.getParameter(inputTypeNames[i]
						+ "_month");
				String yearMonth = year + month;
				paramsBack.setDateInfo(inputTypeNameChns[i] + "：" + year + "-"
						+ month);
				queryDomains = queryDomainss[i].split(",");
				for (int j = 0; j < queryDomains.length; j++) {
					String[] queryDomainsDtl = queryDomains[j].split(":");
					paras.get(Integer.parseInt(queryDomainsDtl[0]) - 1).append(
							" and ").append(queryDomainsDtl[1]).append(" >='")
							.append(yearMonth + "01").append("' ").append(
									" and ").append(queryDomainsDtl[1]).append(
									" <='").append(yearMonth + "31")
							.append("'");
				}

			} else if ("12".equals(inputTypeTypes[i])) { // 年，月，日 3种不同类型 查询
				String value = request.getParameter(inputTypeNames[i]);
				// if(StringUtil.isNull(value))continue;
				queryDomains = queryDomainss[i].split(",");
				for (int j = 0; j < queryDomains.length; j++) {
					String[] queryDomainsDtl = queryDomains[j].split(":");
					// paras.get(Integer.parseInt(queryDomainsDtl[0])-1).append("
					// and ").append(queryDomainsDtl[1]).append("
					// ='").append(value).append("' ");
					String valuestr = "";
					if (StringUtil.isNull(value)) {
						valuestr = " substr(" + queryDomainsDtl[1] + ",1,8)";
					} else {
						if (value.toString().equals("1")) { // 年
							valuestr = " substr(" + queryDomainsDtl[1]
									+ ",1,4)";
						} else if (value.toString().equals("2")) { // 月
							valuestr = " substr(" + queryDomainsDtl[1]
									+ ",1,6)";
						} else if (value.toString().equals("3")) { // 日
							valuestr = " substr(" + queryDomainsDtl[1]
									+ ",1,8)";
						} else { // 默认年
							valuestr = " substr(" + queryDomainsDtl[1]
									+ ",1,4)";
						}
						paras.get(Integer.parseInt(queryDomainsDtl[0]) - 1).append(
								" and ").append(valuestr).append(" = '").append(value).append("'");
					}
					
					
				}

			} else if ("13".equals(inputTypeTypes[i])) {// 特殊的需要月末累计的查询条件方式，公交二级出报表专用
				String dateStart = request.getParameter(inputTypeNames[i]);
				if (!StringUtil.isNull(dateStart)) {
					paramsBack.setDateInfo(StringUtil.nvl(paramsBack
							.getDateInfo(), "")
							+ inputTypeNameChns[i] + "：" + dateStart);
				}

				if (!StringUtil.isNull(dateStart)) {
					queryDomains = queryDomainss[i].split(",");
					for (int j = 0; j < queryDomains.length; j++) {
						String[] queryDomainsDtl = queryDomains[j].split(":");
						paras.get(Integer.parseInt(queryDomainsDtl[0]) - 1)
								.append(" @ and ").append(
										" to_char(" + queryDomainsDtl[1]
												+ ",'yyyymmdd') ")
								.append(" ='").append(
										dateStart.replace("-", "").replace("-",
												"")).append("' @ ");
					}
				}
				if (!StringUtil.isNull(dateStart)) {
					queryDomains = queryDomainss[i].split(",");
					for (int j = 0; j < queryDomains.length; j++) {
						String[] queryDomainsDtl = queryDomains[j].split(":");
						paras.get(Integer.parseInt(queryDomainsDtl[0]) - 1)
								.append(" # and ").append(
										" to_char(" + queryDomainsDtl[1]
												+ ",'yyyymmdd') ").append(
										" >='").append(
										(dateStart.replace("-", "").replace(
												"-", "")).substring(0, 6)
												+ "01").append("' and ")
								.append(
										" to_char(" + queryDomainsDtl[1]
												+ ",'yyyymmdd') ").append(
										" <='").append(
										dateStart.replace("-", "").replace("-",
												"")).append("' # ");
					}
				}
			} else if ("14".equals(inputTypeTypes[i])) {// 特殊的需要月末累计的查询方式，清算控制台出报表专用
				String dateStart = request.getParameter(inputTypeNames[i]);
				if (!StringUtil.isNull(dateStart)) {
					paramsBack.setDateInfo(StringUtil.nvl(paramsBack
							.getDateInfo(), "")
							+ inputTypeNameChns[i] + "：" + dateStart);
				}

				if (!StringUtil.isNull(dateStart)) {
					queryDomains = queryDomainss[i].split(",");
					for (int j = 0; j < queryDomains.length; j++) {
						String[] queryDomainsDtl = queryDomains[j].split(":");
						paras.get(Integer.parseInt(queryDomainsDtl[0]) - 1)
								.append(" @ and ").append(queryDomainsDtl[1])
								.append(" ='").append(
										dateStart.replace("-", "").replace("-",
												"")).append("' @ ");
					}
				}
				if (!StringUtil.isNull(dateStart)) {
					queryDomains = queryDomainss[i].split(",");
					for (int j = 0; j < queryDomains.length; j++) {
						String[] queryDomainsDtl = queryDomains[j].split(":");
						paras.get(Integer.parseInt(queryDomainsDtl[0]) - 1)
								.append(" # and ").append(queryDomainsDtl[1])
								.append(" >='").append(
										(dateStart.replace("-", "").replace(
												"-", "")).substring(0, 6)
												+ "01").append("' and ")
								.append(queryDomainsDtl[1]).append(" <='")
								.append(
										dateStart.replace("-", "").replace("-",
												"")).append("' # ");
					}
				}
			} else if ("16".equals(inputTypeTypes[i])) {// 特殊时间查询条件，拼出如='20110101'的条件
				String value = request.getParameter(inputTypeNames[i]);
				if (StringUtil.isNull(value))
					continue;
				queryDomains = queryDomainss[i].split(",");
				for (int j = 0; j < queryDomains.length; j++) {
					String[] queryDomainsDtl = queryDomains[j].split(":");
					paras.get(Integer.parseInt(queryDomainsDtl[0]) - 1).append(
							"='").append(
							value.replace("-", "").replace("-", ""))
							.append("'");
				}
				paramsBack.setDateInfo(StringUtil.nvl(paramsBack.getDateInfo(),
						"")
						+ inputTypeNameChns[i] + "：" + value);
			}else if("17".equals(inputTypeTypes[i])){ //SUSTR(?,1,8)型日期范围框
				String dateStart=request.getParameter(inputTypeNames[i]+"_start");
				String dateEnd=request.getParameter(inputTypeNames[i]+"_end");
				if(!StringUtil.isNull(dateStart)&&!StringUtil.isNull(dateEnd)){
					paramsBack.setDateInfo(StringUtil.nvl(paramsBack.getDateInfo(),"")+inputTypeNameChns[i]+"："+dateStart+"至"+dateEnd);
				}else if(!StringUtil.isNull(dateStart)){
					paramsBack.setDateInfo(StringUtil.nvl(paramsBack.getDateInfo(),"")+inputTypeNameChns[i]+"："+dateStart+"以后");
				}else if(!StringUtil.isNull(dateEnd)){
					paramsBack.setDateInfo(StringUtil.nvl(paramsBack.getDateInfo(),"")+inputTypeNameChns[i]+": "+dateEnd+"至今");
				}
				if(!StringUtil.isNull(dateStart)){
					queryDomains=queryDomainss[i].split(",");
					for(int j=0;j<queryDomains.length;j++){
						String[] queryDomainsDtl=queryDomains[j].split(":");
						paras.get(Integer.parseInt(queryDomainsDtl[0])-1).append(" and substr(").append(queryDomainsDtl[1]).append(",1,8) >='").append(dateStart.replace("-","").replace("-","")).append("' ");
					}
				}
				if(!StringUtil.isNull(dateEnd)){
					queryDomains=queryDomainss[i].split(",");
					for(int j=0;j<queryDomains.length;j++){
						String[] queryDomainsDtl=queryDomains[j].split(":");
						paras.get(Integer.parseInt(queryDomainsDtl[0])-1).append(" and substr(").append(queryDomainsDtl[1]).append(",1,8) <= '").append(dateEnd.replace("-","").replace("-","")).append("' ");
					}
				}
			}else if ("18".equals(inputTypeTypes[i])) { // 输入框
				String value = request.getParameter(inputTypeNames[i]);
				if (StringUtil.isNull(value))
					continue;
				queryDomains = queryDomainss[i].split(",");
				
				for (int j = 0; j < queryDomains.length; j++) {
					
					String[] queryDomainsDtl = queryDomains[j].split(":");
					paras.get(Integer.parseInt(queryDomainsDtl[0]) - 1).append(
							" and ").append(queryDomainsDtl[1]).append(" =")
							.append(queryDomainsDtl[2].replaceAll("\\[", "'"+value+"',").replace(']',','));
				}
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
