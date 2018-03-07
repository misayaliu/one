package jieyi.app.controller.report;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jieyi.app.controller.BaseController;
import jieyi.app.dubbo.ConsoleService;
import jieyi.app.dubbo.PriviligeService;
import jieyi.app.form.Page;
import jieyi.app.form.report.t_report_column;
import jieyi.app.form.report.t_report_outkey;
import jieyi.app.util.DictHelper;
import jieyi.app.util.JsonRequestReturn;
import jieyi.app.util.PageData;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.reflect.TypeToken;

@Controller
@RequestMapping(value = "t_report_outkey")
public class t_report_outkeyCtl extends BaseController {
	@Resource(name = "priviligeService")
	private PriviligeService priviligeService;

	@Resource(name = "consoleService")
	private ConsoleService consoleService;
	
	@RequestMapping("/loadPage")
	public ModelAndView loadPage(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DictHelper.getDicts(consoleService);
		getOtherInfosForPage();
		
		mv.setViewName("report/t_report_outkey/t_report_outkey");
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
		getOtherInfosForPage();
		String pageInfoStr = consoleService.queryPage(gson.toJson(page),
				"t_report_outkey.datalistPage", "jieyi.app.form.Page");
		JsonRequestReturn jrr = gson.fromJson(pageInfoStr,
				new TypeToken<JsonRequestReturn>() {
				}.getType());
		mv.setViewName("report/t_report_outkey/t_report_outkey");
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
				"t_report_outkey.selectMax", "jieyi.app.util.PageData");
		Integer maxSeq = Integer.parseInt(maxStr);
		pd.put("outkeyid", maxSeq);
		String requestReturn = consoleService.addRecord(gson.toJson(pd),
				"t_report_outkey.insert", "jieyi.app.util.PageData");
		writeJSONObject(request, response, requestReturn);
	}
	
	@RequestMapping(value = "/getEditData")
	public void getEditData(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		JsonRequestReturn requestReturn = new JsonRequestReturn();
		try {
			pd = this.getPageData();
			String pdStr = consoleService.selectRecordOne(gson.toJson(pd),
					"t_report_outkey.selectAll", "jieyi.app.util.PageData");
			pd = gson.fromJson(pdStr, new TypeToken<PageData>() {}.getType());
			t_report_outkey reportoutkey = gson.fromJson(pdStr, new TypeToken<t_report_outkey>() {}.getType());

			requestReturn.setResult(JsonRequestReturn.SUCCESS);
			requestReturn.setField1(reportoutkey);
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
				"t_report_outkey.update", "jieyi.app.util.PageData");
		writeJSONObject(request, response, requestReturn);
	}

	@RequestMapping("/delete.do")
	public void delete(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		pd = this.getPageData();
		String requestReturn = consoleService.deleteRecord(gson.toJson(pd),
				"t_report_outkey.delete", "jieyi.app.util.PageData");
		writeJSONObject(request, response, requestReturn);
	}
	
	/**********************获得一些页面参数*************************/
	private void getOtherInfosForPage() throws Exception {
	}

}
