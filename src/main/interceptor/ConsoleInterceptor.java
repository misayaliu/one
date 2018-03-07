package jieyi.app.interceptor;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jieyi.app.dubbo.PriviligeService;
import jieyi.app.form.system.t_mng_userinfo;
import jieyi.app.form.system.t_mng_useroprlog;
import jieyi.app.util.Const;
import jieyi.app.util.JsonRequestReturn;
import jieyi.app.util.PageData;
import jieyi.tools.util.DateUtil;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * 
* 类名称：LoginHandlerInterceptor.java
* 类描述： 
* @author wei.feng
* 作者单位： 上海捷羿软件系统有限公司
* 联系方式：18651072789
* 创建时间：2015年2月20日
* @version 1.0.0
 */
public class ConsoleInterceptor extends HandlerInterceptorAdapter{
	

	@Resource(name="priviligeService")
	protected PriviligeService priviligeService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		// TODO Auto-generated method stub
		String path = request.getServletPath();
		Gson gson = new Gson();
		//PublicService publicService = ServiceHelper.getPublicService(request);
		//if(path.matches(Const.NO_INTERCEPTOR_PATH)){
		if(path.matches(Const.NO_INTERCEPTOR_PATH)&&!path.matches("*login_mainframe*")){
			return true;
		}else{
			HttpSession session = request.getSession();
			t_mng_userinfo user = (t_mng_userinfo)session.getAttribute(Const.SESSION_USER);
			if(user!=null){
				PageData pd = new PageData();
//				pd.put("systemid", "1");
				pd.put("systemid", session.getAttribute(Const.SESSION_SYSTEMID));
				pd.put("control", path.substring(1));
				//pd.put("moduletype", 2);//只看按钮的
				//List<PageData> pds = (List<PageData>) publicService.selectRecordList(pd, "t_mng_moduleinfo.selectAll");
				String pageInfoStr = priviligeService.selectRecordList(gson.toJson(pd), "t_mng_moduleinfo.selectAll", "jieyi.app.util.PageData");//列出子系统列表
				List<PageData> pds = gson.fromJson(pageInfoStr, new TypeToken<List<PageData>>(){}.getType());
				
				if (pds.size() > 2)
				{
					//一个事件最多只允许出现在菜单和按钮同时，其它时间不允许的
					throw new Exception("该事件对应的模块不少于两个!");
				}
				else if (pds.size() == 0)
				{
//					if(user.getUsercode().equals("dev")){
//						//开发人员无视数据库有没有事件
//						return true;
//					}
//					throw new Exception("不存在该事件对应的模块");
					//直接无视
					return true;
				}else{
					pd = pds.get(0);
					if(pds.size()==2&&pd.getString("moduletype").equals("1")){
						//如果出现两个，就以按钮的为准
						pd = pds.get(1);
					}
					String logflagStr = pd.getString("logflag");
					int logflag = 0;
					if(logflagStr!=null&&!logflagStr.equals("")){
						logflag = (int) Double.parseDouble(logflagStr);
					}
					if(logflag == 1){
						t_mng_useroprlog userlog = new t_mng_useroprlog();
						String systemdatetime = DateUtil.getSystemDateTime("yyyyMMddHHmmss");
						userlog.setSystemid((String) session.getAttribute(Const.SESSION_SYSTEMID));
						userlog.setUserid(user.getUserid());
						userlog.setUsername(user.getUsername());
						userlog.setLogdate(systemdatetime.substring(0, 8));
						userlog.setLogtime(systemdatetime.substring(8, 14));
						userlog.setHostip(request.getRemoteAddr() + ":" + request.getRemotePort());
						userlog.setMsg(path);
						PageData pdReq = new PageData();
						pdReq = new PageData(request);
						String parameterData = gson.toJson(pdReq);
						if (parameterData.length() <= 3000)
						{
							userlog.setRequestjsondata(parameterData);
						}
						else if (parameterData.length() > 3000 && parameterData.length() <= 6000)
						{
							userlog.setRequestjsondata(parameterData.substring(0, 3000));
							userlog.setRequestjsondata1(parameterData.substring(3000, parameterData.length()));
						}
						else
						{
							userlog.setRequestjsondata(parameterData.substring(0, 3000));
							userlog.setRequestjsondata1(parameterData.substring(3000, 6000));
						}
						priviligeService.addRecord(gson.toJson(userlog), "t_mng_useroprlog.insert", "jieyi.app.form.system.t_mng_useroprlog");
						//publicService.saveRecord(userlog, "t_mng_useroprlog.insert");
					}
					return true;
				}
			}else{
				//登陆过滤
				//response.sendRedirect(request.getContextPath() + Const.LOGIN);
				//request.getRequestDispatcher("/admin/login.jsp").forward(request, response); 
				request.getRequestDispatcher("/").forward(request, response); 
				return false;		
				//return true;
			}
		}
	}
}
