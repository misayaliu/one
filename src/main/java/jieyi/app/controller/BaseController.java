package jieyi.app.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jieyi.app.dubbo.PriviligeService;
import jieyi.app.form.Page;
import jieyi.app.form.system.t_mng_moduleinfo;
import jieyi.app.form.system.t_mng_userinfo;
import jieyi.app.util.Const;
import jieyi.app.util.PageData;
import jieyi.app.util.UuidUtil;
import jieyi.app.util.XSSServletRequest;

import org.apache.log4j.Logger;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class BaseController {
	protected Logger logger = Logger.getLogger(this.getClass());
	
	protected ModelAndView mv = this.getModelAndView();
	
	protected PageData pd = new PageData();
	
	protected Gson gson = new Gson();
	
	/**
	 * 得到当前登录的用户
	 * */
	public t_mng_userinfo getLoginUser(HttpSession session){
		t_mng_userinfo user = (t_mng_userinfo)session.getAttribute(Const.SESSION_USER);
		return user;
	}
	
	/**
	 * 得到PageData
	 */
	public PageData getPageData(){
		mv.clear();
		PageData pds = new PageData();
		pds = new PageData(this.getRequest());
			
		//pds.put("SYSNAME", Tools.readTxtFile(Const.SYSNAME)); //读取系统名称
		//pds.put("SYSNAME", "权限管理系统"); //读取系统名称
		mv.addObject("pdm",pds);
		return pds;
	}
	
	/**
	 * 得到ModelAndView
	 */
	public ModelAndView getModelAndView(){
		
		return new ModelAndView();
	}
	
	/**
	 * 得到request对象
	 */
	public HttpServletRequest getRequest() {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		logger.debug("拦截之前请求内容为"+gson.toJson(new PageData(request)));
		request = new XSSServletRequest(request); //在最终进行业务处理的request之前做最后一次拦截
		logger.debug("拦截之后请求内容为"+gson.toJson(new PageData(request)));
		return request;
	}

	/**
	 * 得到32位的uuid
	 * @return
	 */
	public String get32UUID(){
		
		return UuidUtil.get32UUID();
	}
	
	/**
	 * 得到分页列表的信息 
	 */
	public Page getPage(){
		
		return new Page();
	}
	
	public static void logBefore(Logger logger, String interfaceName){
		logger.info("");
		logger.info("start");
		logger.info(interfaceName);
	}
	
	public static void logAfter(Logger logger){
		logger.info("end");
		logger.info("");
	}
	
	public static void writeJSONObject(HttpServletRequest request,HttpServletResponse response, Object obj){
		try {
			request.setCharacterEncoding("utf-8");  //这里不设置编码会有乱码
			response.setContentType("text/html;charset=utf-8");
			response.setHeader("Cache-Control", "no-cache");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			Gson gson = new Gson();
			//System.out.println("gson.toJson(obj) in writeJSONObject:"+gson.toJson(obj));
			if(obj instanceof String){
				out.print(obj);
			}else{
				out.print(gson.toJson(obj));
			}
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 加载页面的按钮权限
	 * */
	protected void processBtnPriviliges(PriviligeService priviligeService,HttpSession session,
			PageData pdForBtnPrivilige,String systemid) throws Exception {
		pdForBtnPrivilige.put("systemid", systemid);
		t_mng_userinfo user = getLoginUser(session);
		if(!user.getUsercode().equals("dev")){
			pdForBtnPrivilige.put("userid", user.getUserid());
		}
		
		String moduleListStr = priviligeService.selectRecordList(gson.toJson(pdForBtnPrivilige), "t_mng_moduleinfo.listModuleByControl", "jieyi.app.util.PageData");//列出子系统列表
		List<t_mng_moduleinfo> moduleList = gson.fromJson(moduleListStr, new TypeToken<List<t_mng_moduleinfo>>(){}.getType());
		for(t_mng_moduleinfo m:moduleList){
			mv.addObject(m.getHtmldesc(), "1");
		}
	}
	
	/*
	 * 报表配置中
	 */
	public static void download(HttpServletResponse response,String fileName,byte[] fileBytes){
		response.reset();
        response.setCharacterEncoding("UTF-8"); 
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename="+fileName); 

        BufferedInputStream bis;
        BufferedOutputStream bos;
		 try {
			 bos = new BufferedOutputStream(response.getOutputStream());
			 bos.write(fileBytes); 
	         bos.flush();
	         bos.close();
		 }catch(Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		 } 
	}
}
