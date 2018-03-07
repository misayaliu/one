package jieyi.app.controller.system;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jieyi.app.controller.BaseController;
import jieyi.app.dubbo.PriviligeService;
import jieyi.app.form.Page;
import jieyi.app.form.system.t_mng_moduleinfo;
import jieyi.app.form.system.t_mng_userinfo;
import jieyi.app.util.Const;
import jieyi.app.util.JsonRequestReturn;
import jieyi.app.util.PageData;
import jieyi.app.util.RSAUtil;
import jieyi.app.util.Tools;
import jieyi.tools.util.DateUtil;
import jieyi.tools.util.PassWordUtil;
import jieyi.tools.util.StringUtil;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.reflect.TypeToken;
/*
 * 总入口
 */
@Controller
public class ConsoleLogonController extends BaseController {
	
	@Resource(name="priviligeService")
	protected PriviligeService priviligeService;
	
	/**
	 * 获取登录用户的IP
	 * @throws Exception 
	 */
	public void getRemortIP(String USERNAME) throws Exception {  
		HttpServletRequest request = this.getRequest();
		String ip = "";
		if (request.getHeader("x-forwarded-for") == null) {  
			ip = request.getRemoteAddr();  
	    }else{
	    	ip = request.getHeader("x-forwarded-for");  
	    }
		pd.put("USERNAME", USERNAME);
		pd.put("IP", ip);
		//userService.saveIP(pd);
	}  
	
	/** 存放全部系统信息 */
	public static List<PageData> systemList = new  ArrayList<PageData>();
	
	/**
	 * 获取数据库系统信息集合
	 * @return
	 * @throws Exception
	 */
	public List<PageData> getSystemList() throws Exception{
		if(systemList.size()==0){
			String systemStr2 = priviligeService.selectRecordList(null, "t_mng_system.selectAll", "jieyi.app.util.PageData");
			systemList = gson.fromJson(systemStr2, new TypeToken<List<PageData>>(){}.getType());
			for (PageData object : systemList) {
				String systemid = object.getString("systemid");
				systemid = object.get("systemid").toString().substring(0, object.get("systemid").toString().indexOf("."));
				object.put("systemid", systemid);
			}
		}
		return systemList;
	}
	
	/**
	 * 根据系统编号返回具体访问的系统信息
	 * @param systemId
	 * @return
	 * @throws Exception
	 */
	public PageData getSystemBean(String systemId) throws Exception{
		PageData bean = null;
		List<PageData> systemList = getSystemList();
		for (PageData object : systemList) {
			if(object.get("systemid").equals(systemId)){
				bean = object;
			}
		}
		return bean;
	}
	

	/**
	 * 通过解析contextPath分析所属系统
	 * @param contextPath
	 * @return
	 * @throws Exception
	 */
	public PageData getSystemBeanByPath(String contextPath) throws Exception{
		String systemid = null;
		if(contextPath.indexOf("jstconsole") != -1){
			systemid = "2";
		}else if(contextPath.indexOf("jstcustomservice") != -1){
			systemid = "4";
		}else if(contextPath.indexOf("jstprivilige") != -1){
			systemid = "1";
		}else if(contextPath.indexOf("jstcardissue") != -1){
			systemid = "3";
		}else{
			systemid = "4";
		}
		return getSystemBean(systemid);
	}
	
	/**
	 * 访问登录页
	 * @return
	 */
	@RequestMapping(value="/login_toLogin",method=RequestMethod.POST)
	public ModelAndView toLogin()throws Exception{
		HttpServletRequest request = getRequest();
		String contextPath = request.getContextPath();
		logger.info("---------系统请求路径是： "+contextPath);
		PageData systemPd = getSystemBeanByPath(contextPath);
		pd = systemPd;
		mv.setViewName("system/admin/login");
		mv.addObject("pd",pd);
		mv.addObject("systemid",pd.getString("systemid"));
		mv.addObject("systemname",pd.getString("systemname"));
		
		String module  = RSAUtil.publicKey.getModulus().toString(16);
		String empoent = RSAUtil.publicKey.getPublicExponent().toString(16);
		mv.addObject("m", module);
		mv.addObject("e", empoent);
		
		return mv;
	}
	
	/**
	 * 请求登录，验证用户
	 */
	@RequestMapping(value="/login_userLogin",method=RequestMethod.POST)
	public ModelAndView userLogin(HttpSession session)throws Exception{
		String module  = RSAUtil.publicKey.getModulus().toString(16);
		String empoent = RSAUtil.publicKey.getPublicExponent().toString(16);
		session.setAttribute("m", module);
		session.setAttribute("e", empoent);
		String errInfo = "";
		/**
		 * pd获取到登录页面传过来的用户名和密码不再是loginname,password
		 * 所以这里要进行处理一下
		 */
		
		HttpServletRequest request = getRequest();
		String contextPath = request.getContextPath();
		logger.info("---------系统请求路径是： "+contextPath);
		PageData systemPd = getSystemBeanByPath(contextPath);
		pd = this.getPageData();
		pd.put("systemid", systemPd.get("systemid"));
		pd.put("systemname", systemPd.get("systemname"));
		
		String systemid = pd.getString("systemid");
		logger.info("***********session超时后的系统编号为："+pd.getString("systemid")+"---------赋值给systemid："+systemid);
		
		String systemname = pd.getString("systemname");
		String USERNAME = pd.get("param1").toString();
		String PASSWORD  = pd.get("param2").toString();
		
		logger.info("-------------param1可以获取参数: "+pd.get("param1"));
		
		logger.info("-------------------前端传过来的密码："+PASSWORD);
		//对前端传送过来加密后的密码进行解密
		PASSWORD = RSAUtil.getDecryptPassword(PASSWORD);
		logger.info("-------------------解密后的密码是：" +PASSWORD);
		
		pd.put("usercode", USERNAME);
		String passwd = PassWordUtil.getPasswordThreeDesOne(USERNAME, PASSWORD);
		System.out.println("passwd:"+passwd);
		String pdRetStr = priviligeService.selectRecordOne(gson.toJson(pd), "t_mng_userinfo.selectForLogin", "jieyi.app.util.PageData");
		pd = gson.fromJson(pdRetStr, new TypeToken<PageData>(){}.getType());
		//pd = (PageData) publicService.selectRecordOne(pd, "t_mng_userinfo.selectForLogin");
		String systemdatetime = DateUtil.getSystemDateTime("yyyyMMddHHmmss");
		if(pd != null){
			String pwdFroDB = pd.getString("password");
			//如果登录次数上限到一定时间了，就给自动清0——此处代码需要补充
			int pwderrtimes = (int) Double.parseDouble(pd.getString("pwderrtimes"));
			//long diff = DateUtil.calcTwoTimesDiff(systemdatetime, pd.getString("lastpwderrtime"));
			String lastpwderrtime = StringUtil.addLeftZero(pd.getString("lastpwderrtime"), 14);
			long diff = Long.parseLong(systemdatetime.substring(0, 8))-Long.parseLong(lastpwderrtime.substring(0, 8));
			if(pwderrtimes>=5&&diff<=0){
				//判断一下登录次数上线
				errInfo = "尝试登录次数已达上限";
			}else{
				if(!passwd.equals(pwdFroDB)){
					//根据usercode来更新用户的错误上线
					int remainder = 0;
					if(diff>0){
						pd.put("pwderrtimes", 1);//如果隔天了，则重新从1开始计算
						remainder = 4;
					}else{
						pd.put("pwderrtimes", pwderrtimes+1);//当天就按照现在的值累加
						remainder = 4-pwderrtimes;
					}
					pd.put("lastpwderrtime", systemdatetime);
					priviligeService.editRecord(gson.toJson(pd), "t_mng_userinfo.userPwdErr", "jieyi.app.util.PageData");
					//publicService.updateRecord(pd, "t_mng_userinfo.userPwdErr");
					errInfo = "帐户或密码错误";
					logger.info("密码有误！今天还剩余"+remainder+"次尝试登录机会 ！");
				}else{
					int userstate = (int) Double.parseDouble(pd.getString("state"));
					if(userstate == 0){
						pd.put("LAST_LOGIN",DateUtil.getSystemDateTime("yyyyMMddHHmmss"));
						
						//密码错误次数清0
						if(pwderrtimes!=0){
							priviligeService.editRecord(gson.toJson(pd), "t_mng_userinfo.clearPwdErrTimes", "jieyi.app.util.PageData");
							//publicService.updateRecord(pd, "t_mng_userinfo.clearPwdErrTimes");
						}
						
						//根据userid和systemid去查询来验证用户是否拥有该系统权限——add by wei.feng 20150810
						PageData pd1 = new PageData();
						pd1.put("userid", pd.getString("userid"));
						pd1.put("systemid", systemid);
						logger.info("根据用户名和系统id来判断用户是否拥有该系统的权限：**************系统编号： "+systemid);
						String usersystemStr = priviligeService.selectRecordOne(gson.toJson(pd1), "t_mng_usersystemrel.selectAll", "jieyi.app.util.PageData");
						if(usersystemStr == null||usersystemStr.equals("null")){
							errInfo = "您没有访问本系统的权限 ";
						}else{
							t_mng_userinfo user = new t_mng_userinfo();
							user.setUserid(pd.getString("userid"));
							user.setUsercode(pd.getString("usercode"));
							user.setUsername(pd.getString("username"));
							user.setPassword(pd.getString("password"));
							user.setSkin(pd.getString("skin"));
							user.setIschangepwd(pd.getString("ischangepwd"));
							user.setUsertype(pd.getString("usertype"));
							user.setOperatorid(pd.getString("operatorid"));
							session.setAttribute(Const.SESSION_USER, user);
							session.setAttribute(Const.SESSION_USERNAME, user.getUsername());
							session.removeAttribute(Const.SESSION_SECURITY_CODE);
							session.setAttribute(Const.SESSION_SYSTEMID, systemid);
							session.setAttribute(Const.SESSION_SYSTEMNAME, systemname);
						}
					}else{
						errInfo = "用户状态错误";
					}
				}
			}
		}else{
			errInfo = "帐户或密码错误";
		}
		if(Tools.isEmpty(errInfo)){
			mv.setViewName("redirect:login_mainframe.do");
		}else{
			mv.addObject("errInfo", errInfo);
			mv.addObject("param1",USERNAME);
			mv.addObject("param2",PASSWORD);
			mv.setViewName("system/admin/login");
			mv.addObject("systemid",systemid);
			mv.addObject("systemname",systemname);
		}
		mv.addObject("pd",pd);
		return mv;
	}
	
	/**
	 * 访问系统首页
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/login_mainframe")
	public ModelAndView login_mainframe(HttpSession session, Page page) throws Exception{
		HttpServletRequest request = getRequest();
		String contextPath = request.getContextPath();
		logger.info("---------系统请求路径是： "+contextPath);
		PageData systemPd = getSystemBeanByPath(contextPath);
		pd = this.getPageData();
		pd.put("systemid", systemPd.get("systemid"));
		logger.info("-------------主界面的systemid： "+pd.getString("systemid"));
		try{
			t_mng_userinfo user = (t_mng_userinfo)session.getAttribute(Const.SESSION_USER);
			if (user != null) {
				PageData pd1 = new PageData();
				String usercode = user.getUsercode();
				pd.put("usercode", user.getUsercode());
				if(!usercode.equals("dev")){
					pd1.put("userid", user.getUserid());
				}
				String systemid = (String) session.getAttribute(Const.SESSION_SYSTEMID);
				if(systemid==null||systemid.equals("")){
					throw new Exception("无法获得系统信息，无法加载菜单");
				}
				pd1.put("systemid", systemid);//权限系统
				logger.info("----------------session中的系统编号是： "+session.getAttribute(Const.SESSION_SYSTEMID));
				pd1.put("moduletype", "0");
				pd1.put("modulelevel", "0");//先查第0级目录
				
				//开始查询用户的角色对应的菜单权限
				//第一级
				String moduleinfosStr = priviligeService.selectRecordList(gson.toJson(pd1), "t_mng_moduleinfo.selectForMenu", "jieyi.app.util.PageData");
				List<t_mng_moduleinfo> moduleinfos = gson.fromJson(moduleinfosStr, new TypeToken<List<t_mng_moduleinfo>>(){}.getType());
				for(t_mng_moduleinfo moduleinfo:moduleinfos){
					//第二级
					pd1.put("parentid", moduleinfo.getModuleid());
					pd1.put("moduletype", null);
					pd1.put("modulelevel", "1");
					String submoduleinfosStr = priviligeService.selectRecordList(gson.toJson(pd1), "t_mng_moduleinfo.selectForMenu", "jieyi.app.util.PageData");
					List<t_mng_moduleinfo> subModuleinfos = gson.fromJson(submoduleinfosStr, new TypeToken<List<t_mng_moduleinfo>>(){}.getType());
					moduleinfo.setSubMouleinfo(subModuleinfos);
					for(t_mng_moduleinfo subModuleinfo:subModuleinfos){
						//第三级
						if(subModuleinfo.getModuletype().equals("0")){
							//只有是目录的时候才搜索
							pd1.put("parentid", subModuleinfo.getModuleid());
							pd1.put("moduletype", "1");
							pd1.put("modulelevel", null);
							String subSubmoduleinfosStr = priviligeService.selectRecordList(gson.toJson(pd1), "t_mng_moduleinfo.selectForMenu", "jieyi.app.util.PageData");
							List<t_mng_moduleinfo> subSubModuleinfos = gson.fromJson(subSubmoduleinfosStr, new TypeToken<List<t_mng_moduleinfo>>(){}.getType());
//							List<t_mng_moduleinfo> subSubModuleinfos = (List<t_mng_moduleinfo>) publicService.selectRecordList(pd1, "t_mng_moduleinfo.selectForMenu");
							subModuleinfo.setSubMouleinfo(subSubModuleinfos);
						}
					}
				}
				
//				String moduleinfosStr = priviligeService.selectRecordList(gson.toJson(pd1), "t_mng_moduleinfo.selectForMenu", "jieyi.app.util.PageData");
//				List<t_mng_moduleinfo> moduleinfos = gson.fromJson(moduleinfosStr, new TypeToken<List<t_mng_moduleinfo>>(){}.getType());
//				for(t_mng_moduleinfo moduleinfo:moduleinfos){
//					pd1.put("parentid", moduleinfo.getModuleid());
//					pd1.put("moduletype", "1");
//					//List<t_mng_moduleinfo> subModuleinfos = (List<t_mng_moduleinfo>) publicService.selectRecordList(pd1, "t_mng_moduleinfo.selectForMenu");
//					String submoduleinfosStr = priviligeService.selectRecordList(gson.toJson(pd1), "t_mng_moduleinfo.selectForMenu", "jieyi.app.util.PageData");
//					List<t_mng_moduleinfo> subModuleinfos = gson.fromJson(submoduleinfosStr, new TypeToken<List<t_mng_moduleinfo>>(){}.getType());
//					moduleinfo.setSubMouleinfo(subModuleinfos);
//				}
				
				session.setAttribute(Const.SESSION_allmenuList, moduleinfos);
			 	
				mv.setViewName("system/admin/mainframe");
				mv.addObject("user", user);
				mv.addObject("menuList", moduleinfos);
				System.out.println(gson.toJson(moduleinfos));
			}else {
				mv.addObject("systemid",pd.get("systemid"));
				mv.addObject("systemname",pd.get("systemname"));
				logger.info("session超时后的放在pd里的系统id是："+pd.get("systemid"));
				mv.setViewName("system/admin/login");//session失效后跳转登录页面
			}
		} catch(Exception e){
			//**
			mv.addObject("systemid",pd.get("systemid"));
			mv.addObject("systemname",pd.get("systemname"));
			mv.setViewName("system/admin/login");
			logger.error(e.getMessage(), e);
		}
		String module  = RSAUtil.publicKey.getModulus().toString(16);
		String empoent = RSAUtil.publicKey.getPublicExponent().toString(16);
		mv.addObject("m", module);
		mv.addObject("e", empoent);
		mv.addObject("pd",pd);
		return mv;
	}
	
	/**
	 * 查询菜单
	 * @throws Exception 
	 */
	@RequestMapping(value="/queryMenu_querymenu")
	public void queryMenu(HttpServletRequest request,HttpServletResponse response,HttpSession session,String systemid) throws Exception{
		pd = this.getPageData();
		String term = (String) pd.get("term");//输入框传过来的值
		pd.remove("term");//怕乱码影响
		term = new String(term.getBytes("ISO-8859-1"),"UTF-8");
		pd.put("modulename", term);
		pd.put("moduletype", "1");//只查菜单
		
		List<Map<String,String>> retList = new ArrayList<Map<String,String>>();
		t_mng_userinfo user = super.getLoginUser(session);
		if(user==null){
			throw new Exception("用户登录超时");
		}else{
			if(!user.getUsercode().equals("dev")){
				pd.put("userid", user.getUserid());
			}
			String moduleinfosStr = priviligeService.selectRecordList(gson.toJson(pd), "t_mng_moduleinfo.selectForMenu", "jieyi.app.util.PageData");
			List<t_mng_moduleinfo> moduleinfos = gson.fromJson(moduleinfosStr, new TypeToken<List<t_mng_moduleinfo>>(){}.getType());
			for(t_mng_moduleinfo m:moduleinfos){
				Map<String,String> map = new HashMap<String,String>();
				map.put("label", m.getModulename());
				map.put("control", m.getControl());
				map.put("moduleid", m.getModuleid());
				map.put("parentid", m.getParentid());
				retList.add(map);
			}
		}
		
		writeJSONObject(request,response,gson.toJson(retList));
	}
	
	/**
	 * 进入首页后的默认页面
	 * @return
	 */
	@RequestMapping(value="/login_default")
	public String defaultPage(){
		return "system/admin/default";
	}
	
	/**
	 * 进入首页后的有tab页面
	 * @return
	 */
	@RequestMapping(value="/login_tab")
	public String tabPage(){
		return "system/admin/tab";
	}
	
	/**
	 * 用户注销
	 * @param session
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/logout")
	public ModelAndView logout(HttpSession session) throws Exception{
		HttpServletRequest request = getRequest();
		String contextPath = request.getContextPath();
		logger.info("---------系统请求路径是： "+contextPath);
		PageData systemPd = getSystemBeanByPath(contextPath);
		pd = systemPd;
		
		String systemStr = priviligeService.selectRecordOne(gson.toJson(pd), "t_mng_system.selectAll", "jieyi.app.util.PageData");
		pd = gson.fromJson(systemStr, new TypeToken<PageData>(){}.getType());
		//String systemid = (String) session.getAttribute(Const.SESSION_SYSTEMID);
		//String systemname = (String) session.getAttribute(Const.SESSION_SYSTEMNAME);
		session.removeAttribute(Const.SESSION_USER);
		session.removeAttribute(Const.SESSION_ROLE_RIGHTS);
		
		session.removeAttribute(Const.SESSION_allmenuList);
		session.removeAttribute(Const.SESSION_menuList);
		session.removeAttribute(Const.SESSION_QX);
		session.removeAttribute(Const.SESSION_userpds);
		session.removeAttribute(Const.SESSION_USERNAME);
		session.removeAttribute(Const.SESSION_USERROL);
		session.removeAttribute("changeMenu");
		session.invalidate();
		
		pd = this.getPageData();
		String  msg = pd.getString("msg");
		pd.put("msg", msg);
		
		mv.setViewName("system/admin/login");
		//mv.setViewName("redirect:login_toLogin.do");
		mv.addObject("pd",pd);
		mv.addObject("systemid",pd.get("systemid"));
		mv.addObject("systemname",pd.get("systemname"));
		return mv;
	}
	
}
