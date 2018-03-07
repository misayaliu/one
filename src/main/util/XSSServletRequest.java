/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市捷顺金科研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package jieyi.app.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;


/**
 * 
 * @Title: XSSServletRequest.java
 * @Package: com.data.interceptor
 * @author you.xu
 * @date 2016年3月4日上午11:25:02
 * @version 1.0
 */
public class XSSServletRequest extends HttpServletRequestWrapper {

    public XSSServletRequest(HttpServletRequest request) {
        super(request);
    }

    @Override
    public String getParameter(String name) {
        String string = super.getParameter(name);
        // 返回值之前 先进行过滤
        return XssFilterUtil.stripXss(string);
    }

    @Override
    public String[] getParameterValues(String name) {
        // 返回值之前 先进行过滤
        String[] values = super
                .getParameterValues(name);
        if (values != null) {
            for (int i = 0; i < values.length; i++) {
                values[i] = XssFilterUtil.stripXss(values[i]);
            }
        }
        return values;
    }
    /**
     * 覆盖getHeader方法，将参数名和参数值都做xss & sql过滤。<br/> 
     * 如果需要获得原始的值，则通过super.getHeaders(name)来获取<br/> 
     * getHeaderNames 也可能需要覆盖 
     */ 
    @Override 
    public String getHeader(String name) {
     String value = super.getHeader(XssFilterUtil.stripXss(name)); 
     if(value!=null){ 
     value=XssFilterUtil.stripXss(value); 
     }
     return value;
    }
}