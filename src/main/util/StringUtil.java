/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市捷顺金科研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package jieyi.app.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class StringUtil {
    
    public static String UrlDecode(String s, String enc){
        String result = "";
        try
        {
            result = URLDecoder.decode(s, enc);
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
        return result;
    }
    
    

}
