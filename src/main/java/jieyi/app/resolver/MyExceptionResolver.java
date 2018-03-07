package jieyi.app.resolver;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

/**
 * 
 * 类名称：MyExceptionResolver.java 类描述：
 * 
 * @author wei.feng
 * 作者单位：
 * 联系方式：
 * @version 1.0.0
 */
public class MyExceptionResolver implements HandlerExceptionResolver {

	protected Logger logger = Logger.getLogger(MyExceptionResolver.class);
	
	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {
		// TODO Auto-generated method stub
		System.out.println("==============异常开始=============");
		ex.printStackTrace();
		System.out.println("==============异常结束=============");
		ModelAndView mv = new ModelAndView("error");
//		try {
//			mv.addObject("title", new String("应用程序异常".getBytes("ISO8859-1"),"UTF-8"));
//			mv.addObject("info", new String("抱歉！您访问的页面出现异常，请稍后重试或联系管理员。".getBytes("ISO8859-1"),"GBK"));
//		} catch (UnsupportedEncodingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		logger.error("应用程序异常"+ex.toString().replaceAll("\n", "<br/>"));
		mv.addObject("exception", "系统异常");
		return mv;
	}
	
}
