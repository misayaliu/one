package jieyi.app.controller.report;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jieyi.app.controller.BaseController;
import jieyi.app.dubbo.ConsoleService;
import jieyi.app.dubbo.PriviligeService;
import jieyi.app.form.Page;
import jieyi.app.form.report.t_report_base;
import jieyi.app.form.report.t_report_column;
import jieyi.app.form.report.t_report_query;
import jieyi.app.util.DictHelper;
import jieyi.app.util.JsonRequestReturn;
import jieyi.app.util.PageData;
import jieyi.app.util.RegexCheckUtil;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.reflect.TypeToken;

@Controller
@RequestMapping(value = "t_report_query")
public class t_report_queryCtl extends BaseController {
	@Resource(name = "priviligeService")
	private PriviligeService priviligeService;

	@Resource(name = "consoleService")
	private ConsoleService consoleService;
	
	@RequestMapping("/loadPage")
	public ModelAndView treportrpbase_loadPage(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DictHelper.getDicts(consoleService);
		getOtherInfosForPage();
		
		mv.setViewName("report/t_report_query/t_report_query");
		mv.addObject("dataList", null);
		mv.addObject("pageStr", "");
		mv.addObject("loadPage", "0");
		PageData pd = new PageData();
		mv.addObject("pd", pd);
		return mv;
	}

	@RequestMapping("/listPages")
	public ModelAndView query(Page page, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		pd = this.getPageData();
		page.setPd(pd);
		//安全验证
		if(pd.getString("report_id") != null){
			if(!RegexCheckUtil.numCheck(pd.getString("report_id"))){
				pd.put("report_id", "");
			}
		}
		getOtherInfosForPage();
		String pageInfoStr = consoleService.queryPage(gson.toJson(page),
				"t_report_query.datalistPage", "jieyi.app.form.Page");
		JsonRequestReturn jrr = gson.fromJson(pageInfoStr,
				new TypeToken<JsonRequestReturn>() {
				}.getType());
		
		mv.setViewName("report/t_report_query/t_report_query");
		mv.addObject("dataList", jrr.getField1());
		mv.addObject("pageStr", jrr.getField2());
		mv.addObject("loadPage", "1");
		mv.addObject("pd", pd);

		return mv;
	}
	
	@RequestMapping("/add.do")
	public void add(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		pd = this.getPageData();
		String maxStr = consoleService.selectRecordOne(gson.toJson(pd),
				"t_report_query.selectMax", "jieyi.app.util.PageData");
		Integer maxSeq = Integer.parseInt(maxStr);
		pd.put("query_id", maxSeq);
		String requestReturn = consoleService.addRecord(gson.toJson(pd),
				"t_report_query.insert", "jieyi.app.util.PageData");
		writeJSONObject(request, response, requestReturn);
	}
	
	@RequestMapping(value = "/getEditData")
	public void getEditData(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		JsonRequestReturn requestReturn = new JsonRequestReturn();
		try {
			pd = this.getPageData();
			String pdStr = consoleService.selectRecordOne(gson.toJson(pd),
					"t_report_query.selectAll", "jieyi.app.util.PageData");
			t_report_query reportquery = gson.fromJson(pdStr, new TypeToken<t_report_query>() {}.getType());

			requestReturn.setResult(JsonRequestReturn.SUCCESS);
			requestReturn.setField1(reportquery);
		} catch (Exception e) {
			logger.error(e.toString(), e);
			requestReturn.setResult(JsonRequestReturn.ERROR);
			requestReturn.setField1(e.toString());
		}

		writeJSONObject(request, response, requestReturn);
	}
	
	@RequestMapping("/edit.do")
	public void edit(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		pd = this.getPageData();
		String requestReturn = consoleService.editRecord(gson.toJson(pd),
				"t_report_query.update", "jieyi.app.util.PageData");
		writeJSONObject(request, response, requestReturn);
	}

	@RequestMapping("/delete.do")
	public void delete(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		pd = this.getPageData();
		String requestReturn = consoleService.deleteRecord(gson.toJson(pd),
				"t_report_query.delete", "jieyi.app.util.PageData");
		writeJSONObject(request, response, requestReturn);
	}
	
	/**********************获得一些页面参数*************************/
	private List<t_report_base> reportbases;
	private void getOtherInfosForPage() throws Exception {
		// TODO Auto-generated method stub
		String reportbaseStr = consoleService.selectRecordList(null, "t_report_base.selectAll", "jieyi.app.util.PageData");
		reportbases = gson.fromJson(reportbaseStr, new TypeToken<List<t_report_base>>() {}.getType());
		mv.addObject("reportBaseList", reportbases);
	}
}
