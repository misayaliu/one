package jieyi.app.controller.system;

import java.io.PrintWriter;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jieyi.app.controller.BaseController;
import jieyi.app.dubbo.ConsoleService;
import jieyi.app.dubbo.PriviligeService;
import jieyi.app.form.Page;
import jieyi.app.util.Const;
import jieyi.app.util.JsonRequestReturn;
import jieyi.app.util.PageData;
import jieyi.app.util.UuidUtil;
import jieyi.tools.util.DateUtil;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.reflect.TypeToken;

/** 
 * 类名称：t_mng_dictinfoCtl
 * 创建人：wei.feng 
 * 创建时间：2015年3月18日
 * @version
 * 描述：数据字典管理，当以表作为驱动的时候，所有的操作均以表名为单位，这样代码修复或者查询起来比较容易
 */
@Controller
@RequestMapping(value="/t_mng_dictinfo")
public class TMngDictinfoCtl extends BaseController {
	@Resource(name="consoleService")
	protected ConsoleService consoleService;
	
	@Resource(name="priviligeService")
	protected PriviligeService priviligeService;
	
	/**
	 * 到用户界面
	 */
	@RequestMapping(value="/loadPage")
	public ModelAndView loadPage(HttpSession session, Page page)throws Exception{
		/*****************加载页面按钮权限Start********************/
		PageData pdForBtnPrivilige = new PageData();
		pdForBtnPrivilige.put("control", "t_mng_dictinfo/loadPage.do");
		super.processBtnPriviliges(priviligeService,session, pdForBtnPrivilige,(String)session.getAttribute(Const.SESSION_SYSTEMID));
		/*****************加载页面按钮权限End********************/
		
		mv.setViewName("system/t_mng_dictinfo/t_mng_dictinfo");
		mv.addObject("dictList", null);
		mv.addObject("loadPage", "0");//初始加载页面
		mv.addObject("pd", pd);
		
		return mv;
	}
	
	/**
	 * 显示用户列表
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/listDicts")
	public ModelAndView listDicts(HttpSession session, Page page)throws Exception{
		
		pd = this.getPageData();		
		page.setPd(pd);
		
		//List<PageData> dictList = (List<PageData>) publicService.selectRecordList(page, "t_mng_dictinfo.dictlistPage");//列出子系统列表
//		String dictListStr = priviligeService.selectRecordList(gson.toJson(page), "t_mng_dictinfo.dictlistPage", "jieyi.app.form.Page");//列出子系统列表
//		List<PageData> dictList = gson.fromJson(dictListStr, new TypeToken<List<PageData>>(){}.getType());
		
		String pageInfoStr = consoleService.queryPage(gson.toJson(page), "t_mng_dictinfo.dictlistPage", "jieyi.app.form.Page");//列出子系统列表
		JsonRequestReturn jrr = gson.fromJson(pageInfoStr, new TypeToken<JsonRequestReturn>(){}.getType());
		
		/*****************加载页面按钮权限Start********************/
		PageData pdForBtnPrivilige = new PageData();
		pdForBtnPrivilige.put("control", "t_mng_dictinfo/loadPage.do");
		super.processBtnPriviliges(priviligeService,session, pdForBtnPrivilige,(String)session.getAttribute(Const.SESSION_SYSTEMID));
		/*****************加载页面按钮权限End********************/
		
		mv.setViewName("system/t_mng_dictinfo/t_mng_dictinfo");
		mv.addObject("dictList", jrr.getField1());
		mv.addObject("pageStr", jrr.getField2());
		mv.addObject("loadPage", "1");//非初始
		mv.addObject("pd", pd);
		
		return mv;
	}
	
	/**
	 * 新增数据字典
	 */
	@RequestMapping(value="/add")
	public void add(HttpServletRequest request,HttpServletResponse response) throws Exception{
		pd = this.getPageData();
		pd.put("locale", "zh_CN");
		String requestReturn = consoleService.addRecord(gson.toJson(pd), "t_mng_dictinfo.insert", "jieyi.app.util.PageData");
		writeJSONObject(request,response,requestReturn);
	}
	
	@RequestMapping(value="/getEditData")
	public void getEditData(HttpServletRequest request,HttpServletResponse response) throws Exception{
		JsonRequestReturn requestReturn = new JsonRequestReturn();
		try{
			pd = this.getPageData();
			//pd = (PageData) publicService.selectRecordOne(pd, "t_mng_dictinfo.selectAll");//根据ID读取		
			String pdStr = consoleService.selectRecordOne(gson.toJson(pd), "t_mng_dictinfo.selectAll", "jieyi.app.util.PageData");
			pd = gson.fromJson(pdStr, new TypeToken<PageData>(){}.getType());
			
			requestReturn.setResult(JsonRequestReturn.SUCCESS);
			requestReturn.setField1(pd);
		} catch(Exception e){
			logger.error(e.toString(), e);
			requestReturn.setResult(JsonRequestReturn.ERROR);
			requestReturn.setField1(e.toString());
		}
		
		writeJSONObject(request,response,requestReturn);
	}
	
	/**
	 * 修改数据字典
	 */
	@RequestMapping(value="/edit")
	public void edit(HttpServletRequest request,HttpServletResponse response) throws Exception{
		pd = this.getPageData();
		String requestReturn = consoleService.editRecord(gson.toJson(pd), "t_mng_dictinfo.update", "jieyi.app.util.PageData");
		writeJSONObject(request,response,requestReturn);
	}
	
	/**
	 * 删除数据字典
	 */
	@RequestMapping(value="/delete")
	public void delete(HttpServletRequest request,HttpServletResponse response) throws Exception{
		pd = this.getPageData();
		String requestReturn = consoleService.deleteRecord(gson.toJson(pd), "t_mng_dictinfo.delete", "jieyi.app.util.PageData");
		writeJSONObject(request,response,requestReturn);
	}
	
	/****************额外的判断业务Start**************/
	/**
	 * 添加数据字典的时候判断值是否存在
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/isExistsWhenAdd")
	public void isExistsWhenAdd(PrintWriter out){
		
		try{
			pd = this.getPageData();
			String pdsStr = consoleService.selectRecordList(gson.toJson(pd), "t_mng_dictinfo.selectAll", "jieyi.app.util.PageData");//根据ID读取		
			List<PageData> pds = gson.fromJson(pdsStr, new TypeToken<List<PageData>>(){}.getType());
			if(pds.size()>0){
				out.write("error");
			}else{
				out.write("success");
			}
			out.close();
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		
	}
	/****************额外的判断业务End**************/

}
