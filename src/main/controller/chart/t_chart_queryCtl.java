package jieyi.app.controller.chart;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jieyi.app.controller.BaseController;
import jieyi.app.dubbo.ConsoleService;
import jieyi.app.dubbo.PriviligeService;
import jieyi.app.form.Page;
import jieyi.app.util.DictHelper;
import jieyi.app.util.JsonRequestReturn;
import jieyi.app.util.PageData;
import jieyi.tools.util.StringUtil;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.reflect.TypeToken;

@Controller
@RequestMapping(value = "t_chart_query")
public class t_chart_queryCtl extends BaseController {

	@Resource(name = "priviligeService")
	private PriviligeService priviligeService;

	@Resource(name = "consoleService")
	private ConsoleService consoleService;

	@RequestMapping("/loadPage")
	public ModelAndView treportrpbase_loadPage(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DictHelper.getDicts(consoleService);
		mv.setViewName("chart/t_chart_query/t_chart_query");
		mv.addObject("dataList", null);
		mv.addObject("pageStr", "");
		mv.addObject("loadPage", "0");
		PageData pd = new PageData();
		mv.addObject("pd", pd);
		return mv;
	}

	@RequestMapping("/t_chart_queryListJson")
	public void t_chart_queryListJson(Page page, HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		pd = this.getPageData();
		page.setPd(pd);
		String pageInfoStr = consoleService.queryPage(gson.toJson(page),
				"t_chart_query.selectlist", "jieyi.app.form.Page");
		JsonRequestReturn jrr = gson.fromJson(pageInfoStr,
				new TypeToken<JsonRequestReturn>() {
				}.getType());
		writeJSONObject(request, response, jrr);
	}
	
	
	@RequestMapping("/listPages")
	public ModelAndView query(Page page, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		pd = this.getPageData();
		page.setPd(pd);
		String pageInfoStr = consoleService.queryPage(gson.toJson(page),
				"t_chart_query.datalistPage", "jieyi.app.form.Page");
		JsonRequestReturn jrr = gson.fromJson(pageInfoStr,
				new TypeToken<JsonRequestReturn>() {
				}.getType());
		mv.setViewName("chart/t_chart_query/t_chart_query");
		mv.addObject("dataList", jrr.getField1());
		mv.addObject("pageStr", jrr.getField2());
		mv.addObject("loadPage", "1");// �ǳ�ʼ
		mv.addObject("pd", pd);

		return mv;
	}

	@RequestMapping("/add.do")
	public void add(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		pd = this.getPageData();
		//String cxfname = pd.getString("callservicebean");
		//pd.put("cxfname", cxfname);
		/*String treportctlStr = consoleService.selectRecordOne(gson.toJson(pd),
				"t_chart_query.selectOne", "jieyi.app.util.PageData");
		t_chart_query ctl = gson.fromJson(treportctlStr,
				new TypeToken<t_chart_query>() {
				}.getType());
		if (ctl == null) {
			throw new Exception("未找到相应的webservice基础信息");
		}*/

		String maxseq = consoleService.selectRecordOne(gson.toJson(pd),
				"t_chart_query.selectMaxSeq", "jieyi.app.util.PageData");

		Integer maxSeq = Integer.parseInt(maxseq);
		pd.put("chart_query_id", maxSeq);
		String requestReturn = consoleService.addRecord(gson.toJson(pd),
				"t_chart_query.insert", "jieyi.app.util.PageData");
		writeJSONObject(request, response, requestReturn);
	}

	@RequestMapping(value = "/getEditData")
	public void getEditData(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		JsonRequestReturn requestReturn = new JsonRequestReturn();
		try {
			pd = this.getPageData();
			String pdStr = consoleService.selectRecordOne(gson.toJson(pd),
					"t_chart_query.selectOne", "jieyi.app.util.PageData");
			pd = gson.fromJson(pdStr, new TypeToken<PageData>() {
			}.getType());

			requestReturn.setResult(JsonRequestReturn.SUCCESS);
			requestReturn.setField1(pd);
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
				"t_chart_query.update", "jieyi.app.util.PageData");
		writeJSONObject(request, response, requestReturn);
	}

	@RequestMapping("/delete.do")
	public void delete(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		pd = this.getPageData();
		String requestReturn = consoleService.deleteRecord(gson.toJson(pd),
				"t_chart_query.delete", "jieyi.app.util.PageData");
		writeJSONObject(request, response, requestReturn);
	}

}
