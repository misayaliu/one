package jieyi.app.controller.system;

import java.io.PrintWriter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.reflect.TypeToken;

import jieyi.app.controller.BaseController;
import jieyi.app.dubbo.PriviligeService;
import jieyi.app.form.Page;
import jieyi.app.form.system.t_mng_userinfo;
import jieyi.app.util.Const;
import jieyi.app.util.JsonRequestReturn;
import jieyi.app.util.PageData;
import jieyi.app.util.RSAUtil;
import jieyi.tools.util.PassWordUtil;

/** 
 * 类名称：t_mng_userinfoCtl
 * 创建人：wei.feng 
 * 创建时间：2015年2月27日
 * @version
 * 描述：角色管理，当以表作为驱动的时候，所有的操作均以表名为单位，这样代码修复或者查询起来比较容易
 */
@Controller
@RequestMapping(value="/t_mng_userinfo")
public class TMngUserinfoCtl extends BaseController {
	@Resource(name="priviligeService")
	protected PriviligeService priviligeService;
	
	/**
	 * 用户资料界面
	 */
	@RequestMapping(value="/userProfile")
	public ModelAndView userProfile(HttpSession session, Page page)throws Exception{
		
		pd = this.getPageData();
		
		//pd = (PageData) publicService.selectRecordOne(pd, "t_mng_userinfo.selectAll");//根据ID读取
		String pdStr = priviligeService.selectRecordOne(gson.toJson(pd), "t_mng_userinfo.selectAll", "jieyi.app.util.PageData");
		pd = gson.fromJson(pdStr, new TypeToken<PageData>(){}.getType());
		
		mv.setViewName("system/t_mng_userinfo/userProfile");
		mv.addObject("pd", pd);
		
		return mv;
	}

	/**
	 * 修改用户
	 */
	@RequestMapping(value="/edit")
	public void edit(HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		pd = this.getPageData();
		String requestReturn = priviligeService.editRecord(gson.toJson(pd), "t_mng_userinfo.update", "jieyi.app.util.PageData");
		writeJSONObject(request,response,requestReturn);
		
	}
	
	/**
	 * 修改密码
	 */
	@RequestMapping(value="/changePassword")
	public void changePassword(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		JsonRequestReturn jsonRequestReturn = new JsonRequestReturn();
		String requestReturn = "";
		try{
			pd = this.getPageData();
			t_mng_userinfo user = super.getLoginUser(session);
			String usercode = pd.getString("usercode");
			String srcpassword = pd.getString("srcpassword");
			logger.info("-------------------前端传过来的原始密码："+srcpassword);
			//对前端传送过来加密后的原密码进行解密
			srcpassword = RSAUtil.getDecryptPassword(srcpassword);
			srcpassword = PassWordUtil.getPasswordThreeDesOne(usercode, srcpassword);
			
			String pwdFroDB = user.getPassword();
			
			if(srcpassword.equals(pwdFroDB)){
				//把传过来的新密码进行解密还原后，再加密保存到数据库
				String newpassword = RSAUtil.getDecryptPassword(pd.getString("password"));
				newpassword = PassWordUtil.getPasswordThreeDesOne(usercode, newpassword);
				pd.put("password", newpassword);
				//publicService.updateRecord(pd, "t_mng_userinfo.changePassword");
				requestReturn = priviligeService.editRecord(gson.toJson(pd), "t_mng_userinfo.changePassword", "jieyi.app.util.PageData");
				session.removeAttribute(Const.SESSION_USER);
				user.setPassword(newpassword);
				session.setAttribute(Const.SESSION_USER, user);
			}else{
				jsonRequestReturn.setResult(JsonRequestReturn.ERROR);
				jsonRequestReturn.setField1("原密码不正确，修改失败");
				requestReturn = gson.toJson(jsonRequestReturn);
			}
		} catch(Exception e){
			logger.error(e.toString(), e);
			jsonRequestReturn.setResult(JsonRequestReturn.ERROR);
			jsonRequestReturn.setField1(e.toString());
			requestReturn = gson.toJson(jsonRequestReturn);
		}
		
		writeJSONObject(request,response,requestReturn);
		
	}
	
	/**
	 * 设置皮肤
	 */
	@RequestMapping(value="/setSkin")
	public void setSkin(HttpSession session,PrintWriter out){
		try{
			pd = this.getPageData();
			t_mng_userinfo user = super.getLoginUser(session);
			pd.put("userid", user.getUserid());
			priviligeService.editRecord(gson.toJson(pd), "t_mng_userinfo.setSkin", "jieyi.app.util.PageData");
			//publicService.updateRecord(pd, "t_mng_userinfo.setSkin");
			out.write("success");
			out.close();
		} catch(Exception e){
			out.write("error");
			out.close();
		}
		
	}

}
