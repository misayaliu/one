package jieyi.app.controller.system;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jieyi.app.controller.BaseController;
import jieyi.app.dubbo.PriviligeService;
import jieyi.app.form.Page;
import jieyi.app.form.system.t_mng_useroprlog;
import jieyi.app.util.Const;
import jieyi.app.util.JsonRequestReturn;
import jieyi.app.util.PageData;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.reflect.TypeToken;


/** 
 * 类名称：t_mng_useroprlogCtl
 * 创建人：wei.feng 
 * 创建时间：2015年3月18日
 * @version
 * 描述：操作日志查询，当以表作为驱动的时候，所有的操作均以表名为单位，这样代码修复或者查询起来比较容易
 */
@Controller
@RequestMapping(value="/t_mng_useroprlog")
public class TMngUseroprlogCtl extends BaseController {
	@Resource(name="priviligeService")
	protected PriviligeService priviligeService;
	
	/**
	 * 到用户界面
	 */
	@RequestMapping(value="/loadPage")
	public ModelAndView loadPage(HttpSession session, Page page)throws Exception{
		
		/*****************加载页面按钮权限Start********************/
		PageData pdForBtnPrivilige = new PageData();
		pdForBtnPrivilige.put("control", "t_mng_useroprlog/loadPage.do");
		super.processBtnPriviliges(priviligeService,session, pdForBtnPrivilige,(String)session.getAttribute(Const.SESSION_SYSTEMID));
		/*****************加载页面按钮权限End********************/
		
		mv.setViewName("system/t_mng_useroprlog/t_mng_useroprlog");
		mv.addObject("oprlogList", null);
		mv.addObject("loadPage", "0");//初始加载页面
		mv.addObject("pd", pd);
		
		return mv;
	}
	
	/**
	 * 显示日志
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/listLogs")
	public ModelAndView listLogs(HttpSession session, Page page)throws Exception{
		pd = this.getPageData();		
		pd.put("systemid", "2");
		page.setPd(pd);
		//List<PageData> oprlogList = (List<PageData>) publicService.selectRecordList(page, "t_mng_useroprlog.systemlistPage");//列出子系统列表
//		String oprlogListStr = priviligeService.selectRecordList(gson.toJson(page), "t_mng_useroprlog.systemlistPage", "jieyi.app.form.Page");//列出子系统列表
//		List<PageData> oprlogList = gson.fromJson(oprlogListStr, new TypeToken<List<PageData>>(){}.getType());
		
		String pageInfoStr = priviligeService.queryPage(gson.toJson(page), "t_mng_useroprlog.systemlistPage", "jieyi.app.form.Page");//列出子系统列表
		JsonRequestReturn jrr = gson.fromJson(pageInfoStr, new TypeToken<JsonRequestReturn>(){}.getType());
		
		/*****************加载页面按钮权限Start********************/
		PageData pdForBtnPrivilige = new PageData();
		pdForBtnPrivilige.put("control", "t_mng_useroprlog/loadPage.do");
		super.processBtnPriviliges(priviligeService,session, pdForBtnPrivilige,(String)session.getAttribute(Const.SESSION_SYSTEMID));
		/*****************加载页面按钮权限End********************/
		
		mv.setViewName("system/t_mng_useroprlog/t_mng_useroprlog");
		mv.addObject("oprlogList", jrr.getField1());
		mv.addObject("pageStr", jrr.getField2());
		mv.addObject("loadPage", "1");//初始加载页面
		mv.addObject("pd", pd);
		
		return mv;
	}
	
	/**
	 * 显示详细信息
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/showdetail")
	public void showdetail(HttpServletRequest request,HttpServletResponse response) throws Exception{
		JsonRequestReturn requestReturn = new JsonRequestReturn();
		try{
			pd = this.getPageData();
			
			//pd = (PageData) publicService.selectRecordOne(pd, "t_mng_useroprlog.selectAll");//根据ID读取		
			String pdStr = priviligeService.selectRecordOne(gson.toJson(pd), "t_mng_useroprlog.selectAll", "jieyi.app.util.PageData");//根据ID读取		
			t_mng_useroprlog ul = gson.fromJson(pdStr, new TypeToken<t_mng_useroprlog>(){}.getType());
			
			requestReturn.setResult(JsonRequestReturn.SUCCESS);
			requestReturn.setField1(ul);
		} catch(Exception e){
			logger.error(e.toString(), e);
			requestReturn.setResult(JsonRequestReturn.ERROR);
			requestReturn.setField1(e.toString());
		}
		
		writeJSONObject(request,response,requestReturn);
	}

}
