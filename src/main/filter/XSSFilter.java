/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市捷顺金科研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package jieyi.app.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import jieyi.app.util.XSSServletRequest;


/**
 * 
 * @Title: XSSFilter.java
 * @Package: com.data.interceptor
 * @author you.xu
 * @date 2016年3月4日上午11:21:47
 * @version 1.0
 */
public class XSSFilter implements Filter {

    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest req, ServletResponse res,
            FilterChain filterChain) throws IOException, ServletException {
    	String uri = ((HttpServletRequest) req).getRequestURI().toString();
    	if(uri.contains("poireport/poiReport_exportExcel")) {
    		 filterChain.doFilter(req, res);
    	}else {
    		 HttpServletRequest request = (HttpServletRequest) req;
    	        request = new XSSServletRequest(request);
    	        filterChain.doFilter(request, res);
    	}
    }

    public void destroy() {

    }

}