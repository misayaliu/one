/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市捷顺金科研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package jieyi.app.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

/**
 * 
 * @Title: XssFilterUtil.java
 * @author you.xu
 * @date 2016年3月4日上午9:52:02
 * @version 1.0
 */

public class XssFilterUtil {

    private static List<Pattern> patterns = null;

    private static List<Object[]> getXssPatternList() {
        List<Object[]> ret = new ArrayList<Object[]>();

        ret.add(new Object[] { "<(no)?script[^>]*>.*?</(no)?script>",
                Pattern.CASE_INSENSITIVE });
        ret.add(new Object[] { "eval\\((.*?)\\)",
                Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL });
        ret.add(new Object[] { "expression\\((.*?)\\)",
                Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL });
        ret.add(new Object[] { "(javascript:|vbscript:|view-source:)*",
                Pattern.CASE_INSENSITIVE });
        ret.add(new Object[] { "<(\"[^\"]*\"|\'[^\']*\'|[^\'\">])*>",
                Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL });
        ret.add(new Object[] {
                "(window\\.location|window\\.|\\.location|document\\.cookie|document\\.|alert\\(.*?\\)|window\\.open\\()*",
                Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL });
        ret.add(new Object[] {
                "<+\\s*\\w*\\s*(oncontrolselect|oncopy|oncut|ondataavailable|ondatasetchanged|ondatasetcomplete|ondblclick|ondeactivate|ondrag|ondragend|ondragenter|ondragleave|ondragover|ondragstart|ondrop|onerror=|onerroupdate|onfilterchange|onfinish|onfocus|onfocusin|onfocusout|onhelp|onkeydown|onkeypress|onkeyup|onlayoutcomplete|onload|onlosecapture|onmousedown|onmouseenter|onmouseleave|onmousemove|onmousout|onmouseover|onmouseup|onmousewheel|onmove|onmoveend|onmovestart|onabort|onactivate|onafterprint|onafterupdate|onbefore|onbeforeactivate|onbeforecopy|onbeforecut|onbeforedeactivate|onbeforeeditocus|onbeforepaste|onbeforeprint|onbeforeunload|onbeforeupdate|onblur|onbounce|oncellchange|onchange|onclick|oncontextmenu|onpaste|onpropertychange|onreadystatechange|onreset|onresize|onresizend|onresizestart|onrowenter|onrowexit|onrowsdelete|onrowsinserted|onscroll|onselect|onselectionchange|onselectstart|onstart|onstop|onsubmit|onunload)+\\s*=+",
                Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL });
        return ret;
    }

    private static List<Pattern> getPatterns() {

        if (patterns == null) {

            List<Pattern> list = new ArrayList<Pattern>();

            String regex = null;
            Integer flag = null;
            int arrLength = 0;

            for (Object[] arr : getXssPatternList()) {
                arrLength = arr.length;
                for (int i = 0; i < arrLength; i++) {
                    regex = (String) arr[0];
                    flag = (Integer) arr[1];
                    list.add(Pattern.compile(regex, flag));
                }
            }

            patterns = list;
        }

        return patterns;
    }

    public static String stripXss(String value) {

        if (null == value) {
            return value;
        }
        if (StringUtils.isNotBlank(value)) {

            Matcher matcher = null;

            for (Pattern pattern : getPatterns()) {
                matcher = pattern.matcher(value);
                // 匹配
                if (matcher.find()) {
                    // 删除相关字符串
                    value = matcher.replaceAll("");
                }
            }
            value = xssEncode(value);
        }
        // 预防SQL盲注
        String[] pattern = { "%", "select", "insert", "delete", "from",
                "count\\(", "drop table", "update", "truncate", "asc\\(",
                "mid\\(", "char\\(", "xp_cmdshell", "exec", "master",
                "netlocalgroup administrators", "net user", "or", "and","+","-" };
        for (int i = 0; i < pattern.length; i++) {
            value = value.replace(pattern[i].toString(), "");
        }
        return value;
    }

    public static void main(String[] args) {

        String value = null;
        value = XssFilterUtil
                .stripXss("<br>select  ***//||&;/*-+ <>$###@%$#@$%^#$^%$&^(&*)*\\''count or %% ..... ,,,, ");
        System.out.println("type-1: '" + value + "'");

        value = XssFilterUtil
                .stripXss("<script src='' onerror='alert(document.cookie)'></script>");
        System.out.println("type-2: '" + value + "'");

        value = XssFilterUtil.stripXss("</script>");
        System.out.println("type-3: '" + value + "'");

        value = XssFilterUtil.stripXss(" eval(abc);");
        System.out.println("type-4: '" + value + "'");

        value = XssFilterUtil.stripXss(" expression(abc);");
        System.out.println("type-5: '" + value + "'");

        value = XssFilterUtil
                .stripXss("<img src='' onerror='alert(document.cookie);'></img>");
        System.out.println("type-6: '" + value + "'");

        value = XssFilterUtil
                .stripXss("<img src='' onerror='alert(document.cookie);'/>");
        System.out.println("type-7: '" + value + "'");

        value = XssFilterUtil
                .stripXss("<img src='' onerror='alert(document.cookie);'>");
        System.out.println("type-8: '" + value + "'");

        value = XssFilterUtil
                .stripXss("<script language=text/javascript>alert(document.cookie);");
        System.out.println("type-9: '" + value + "'");

        value = XssFilterUtil.stripXss("<script>window.location='url'");
        System.out.println("type-10: '" + value + "'");

        value = XssFilterUtil.stripXss(" onload='alert(\"abc\");");
        System.out.println("type-11: '" + value + "'");

        value = XssFilterUtil.stripXss("<img src=x<!--'<\"-->>");
        System.out.println("type-12: '" + value + "'");

        value = XssFilterUtil.stripXss("<=img onstop=");
        System.out.println("type-13: '" + value + "'");
        

        value = XssFilterUtil.stripXss("<title>欢迎登录多用途卡清算管理系统'><script>alert(2740)</script></title>");
        System.out.println("type-14: '" + value + "'");
        
        value = XssFilterUtil.stripXss("loginname=admin&password=0ba9af8a79790f1b7793042b61042e869e8812b3a255ade8cbb1645141fde7e2be4753f509644eb632c08794f2107f076a7b49bce7e6db35d52eaa0269e79346d50f2442a9d53d7e52402306bafa5ffc3bc5421025d3d65fb6035676ad48305e366d5ba1f4f3d30bc6756b4a2d27488147d0d9c992858f2664beb06d26222e96&systemid=3&systemname=%E5%8F%91%E5%8D%A1%E7%B3%BB%E7%BB%9F%27+and+%27f%27%3D%27f%27+--+");
        System.out.println("type-15: '" + value + "'");
    }
    
    

    /** 
     * 将容易引起xss & sql漏洞的半角字符直接替换成全角字符 
     *  
     * @param s 
     * @return 
     */  
    private static String xssEncode(String s) {  
        if (s == null || s.isEmpty()) {  
            return s;  
        }else{  
            s = stripXSSAndSql(s);  
        }  
        StringBuilder sb = new StringBuilder(s.length() + 16);  
        for (int i = 0; i < s.length(); i++) {  
            char c = s.charAt(i);  
            switch (c) {  
            case '>':  
                sb.append("＞");// 转义大于号  
                break;  
            case '<':  
                sb.append("＜");// 转义小于号  
                break;  
            case '\'':  
                sb.append("＇");// 转义单引号  
                break;  
            case '\"':  
                sb.append("＂");// 转义双引号  
                break;  
            case '&':  
                sb.append("＆");// 转义&  
                break;  
            case '#':  
                sb.append("＃");// 转义#  
                break;  
            default:  
                sb.append(c);  
                break;  
            }  
        }  
        return sb.toString();  
    }  
  
   
     /** 
      *  
      * 防止xss跨脚本攻击（替换，根据实际情况调整） 
      */  
   
     public static String stripXSSAndSql(String value) {  
         if (value != null) {  
             // NOTE: It's highly recommended to use the ESAPI library and  
             // uncomment the following line to  
             // avoid encoded attacks.  
             // value = ESAPI.encoder().canonicalize(value);  
             // Avoid null characters  
 /**         value = value.replaceAll("", "");***/  
             // Avoid anything between script tags  
             Pattern scriptPattern = Pattern.compile("<[\r\n| | ]*script[\r\n| | ]*>(.*?)</[\r\n| | ]*script[\r\n| | ]*>", Pattern.CASE_INSENSITIVE);  
             value = scriptPattern.matcher(value).replaceAll("");  
             // Avoid anything in a src="http://www.yihaomen.com/article/java/..." type of e-xpression  
             scriptPattern = Pattern.compile("src[\r\n| | ]*=[\r\n| | ]*[\\\"|\\\'](.*?)[\\\"|\\\']", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);  
             value = scriptPattern.matcher(value).replaceAll("");  
             // Remove any lonesome </script> tag  
             scriptPattern = Pattern.compile("</[\r\n| | ]*script[\r\n| | ]*>", Pattern.CASE_INSENSITIVE);  
             value = scriptPattern.matcher(value).replaceAll("");  
             // Remove any lonesome <script ...> tag  
             scriptPattern = Pattern.compile("<[\r\n| | ]*script(.*?)>", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);  
             value = scriptPattern.matcher(value).replaceAll("");  
             // Avoid eval(...) expressions  
             scriptPattern = Pattern.compile("eval\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);  
             value = scriptPattern.matcher(value).replaceAll("");  
             // Avoid e-xpression(...) expressions  
             scriptPattern = Pattern.compile("e-xpression\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);  
             value = scriptPattern.matcher(value).replaceAll("");  
             // Avoid javascript:... expressions  
             scriptPattern = Pattern.compile("javascript[\r\n| | ]*:[\r\n| | ]*", Pattern.CASE_INSENSITIVE);  
             value = scriptPattern.matcher(value).replaceAll("");  
             // Avoid vbscript:... expressions  
             scriptPattern = Pattern.compile("vbscript[\r\n| | ]*:[\r\n| | ]*", Pattern.CASE_INSENSITIVE);  
             value = scriptPattern.matcher(value).replaceAll("");  
             // Avoid onload= expressions  
             scriptPattern = Pattern.compile("onload(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);  
             value = scriptPattern.matcher(value).replaceAll("");  
         }  
         return value;  
     }  
   
 }  

