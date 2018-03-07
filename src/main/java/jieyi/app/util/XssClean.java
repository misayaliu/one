/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市捷顺金科研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package jieyi.app.util;

import java.io.IOException;

import java.io.InputStream;

import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import org.owasp.validator.html.AntiSamy;

import org.owasp.validator.html.CleanResults;

import org.owasp.validator.html.Policy;

import org.owasp.validator.html.PolicyException;

import org.owasp.validator.html.ScanException;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.util.HtmlUtils;

import org.apache.commons.io.IOUtils;

/*

 * 增加 XSS过滤

 * 加入了dependency之后，就可以在xssClean中加入antisamy对恶意脚本进行清理。其中policy.xml是白名单，

 * policy.xml中规定了各个html元素所必须满足的条件。antisamy的精髓之处在于，使用policy文件来规定你的过滤条件，

 * 若输入字符串不满足policy文件中的条件，则会过滤掉字符中的恶意脚本，返回过滤后的结果。

 */

public class XssClean {

    private static Policy policy = null;

    public static Policy getPolicy() throws PolicyException {

        String result = null;
        if (policy == null)
        {

            try
            {
                ResourceLoader resourceLoader = new DefaultResourceLoader();
                Resource resource = resourceLoader.getResource("config/antisamy.xml");
                result = resource.getURL().getPath();
                System.out.println("path:" + result);
//                result = XssClean.class.getResource("/").getPath() + "config/antisamy.xml";
//                System.out.println("path:" + result);
                // InputStream policyFile =
                // XssClean.class.getResourceAsStream("antisamy.xml"); //
                // antisamy.xml此文件可能大装载会报错，直接抛出异常即可不用理会也行。
                // 推荐一个jar包，用来转换InputStream到String，
                // 引入apache的io包 import org.apache.commons.io.IOUtils;
                // result = IOUtils.toString(policyFile, "UTF-8");

            }
            catch (Exception e)
            {

                e.printStackTrace();

            }

            policy = Policy.getInstance(result);

        }

        return policy;

    }

    // 将client端用户输入的request，在server端进行了拦截，并且进行了过滤

    public static String xssClean_New(String value) {

        if (StringUtils.isNotEmpty(value))
        {

            AntiSamy antiSamy = new AntiSamy();

            try
            {

                // 如果 不用此方法 可以 使用 下面注释的代码实现

                final CleanResults cr = antiSamy.scan(StringUtil.UrlDecode(value, "UTF-8"), getPolicy());

                // final CleanResults cr = antiSamy.scan(value,
                // Policy.getInstance("antisamy.xml"), AntiSamy.SAX);

                // 安全的HTML输出

                value = cr.getCleanHTML();

                value = HtmlUtils.htmlEscape(value);

                // return cr.getCleanHTML();

            }
            catch (ScanException e)
            {

                e.printStackTrace();

            }
            catch (PolicyException e)
            {

                System.out.println("加载XSS规则文件异常: " + e.getMessage());

            }

        }

        return value;

    }

    /**
     * 
     * 将容易引起xss漏洞的半角字符直接替换成全角字符
     * 
     * 清除恶意的XSS脚本
     * 
     * @param s
     * 
     * @return
     * 
     */

    public static String xssClean(String value) {

        if (value == null || value.isEmpty())
        {

            return value;

        }

//        value = StringUtil.UrlDecode(value, "UTF-8");

        value = HtmlUtils.htmlEscape(value);

        value = value.replaceAll("<", "& lt;").replaceAll(">", "& gt;");

        value = value.replaceAll("\\(", "& #40;").replaceAll("\\)", "& #41;");

        value = value.replaceAll("'", "& #39;");

//        value = value.replaceAll("eval\\((.*)\\)", "");

//        value = value.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']", "\"\"");

//        value = value.replaceAll("script", "");
        
        // 含有脚本： script
        value = value.replaceAll("[s|S][c|C][r|R][i|I][p|P][t|T]", "");
        // 含有脚本 javascript
        value = value.replaceAll("[\\\"\\\'][\\s]*[j|J][a|A][v|V][a|A][s|S][c|C][r|R][i|I][p|P][t|T]:(.*)[\\\"\\\']", "\"\"");
        // 含有函数： eval
        value = value.replaceAll("[e|E][v|V][a|A][l|L]\\((.*)\\)", "");

        StringBuilder sb = new StringBuilder(value.length() + 16);

        for (int i = 0; i < value.length(); i++)
        {

            char c = value.charAt(i);

            switch (c)
            {

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

            default:

                sb.append(c);

                break;

            }

        }

        return sb.toString();

    }

    public static String xssClean_error(String value) {

        if (value != null)
        {

            value = value.replaceAll("", "");

            // Avoid anything between script tags

            Pattern scriptPattern = Pattern.compile("<script>(.*?)</script>", Pattern.CASE_INSENSITIVE);

            value = scriptPattern.matcher(value).replaceAll("");

            // Avoid anything in a
            // src="http://www.yihaomen.com/article/java/..." type of
            // e­xpression

            scriptPattern = Pattern.compile("src[\r\n]*=[\r\n]*\\\'(.*?)\\\'", Pattern.CASE_INSENSITIVE
                    | Pattern.MULTILINE | Pattern.DOTALL);

            value = scriptPattern.matcher(value).replaceAll("");

            scriptPattern = Pattern.compile("src[\r\n]*=[\r\n]*\\\"(.*?)\\\"", Pattern.CASE_INSENSITIVE
                    | Pattern.MULTILINE | Pattern.DOTALL);

            value = scriptPattern.matcher(value).replaceAll("");

            // Remove any lonesome </script> tag

            scriptPattern = Pattern.compile("</script>", Pattern.CASE_INSENSITIVE);

            value = scriptPattern.matcher(value).replaceAll("");

            // Remove any lonesome <script ...> tag

            scriptPattern = Pattern.compile("<script(.*?)>", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE
                    | Pattern.DOTALL);

            value = scriptPattern.matcher(value).replaceAll("");

            // Avoid eval(...) e­xpressions

            scriptPattern = Pattern.compile("eval\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE
                    | Pattern.DOTALL);

            value = scriptPattern.matcher(value).replaceAll("");

            // Avoid e­xpression(...) e­xpressions

            scriptPattern = Pattern.compile("e­xpression\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE
                    | Pattern.DOTALL);

            value = scriptPattern.matcher(value).replaceAll("");

            // Avoid javascript:... e­xpressions

            scriptPattern = Pattern.compile("javascript:", Pattern.CASE_INSENSITIVE);

            value = scriptPattern.matcher(value).replaceAll("");

            // Avoid vbscript:... e­xpressions

            scriptPattern = Pattern.compile("vbscript:", Pattern.CASE_INSENSITIVE);

            value = scriptPattern.matcher(value).replaceAll("");

            // Avoid onload= e­xpressions

            scriptPattern = Pattern.compile("onload(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE
                    | Pattern.DOTALL);

            value = scriptPattern.matcher(value).replaceAll("");

            value = HtmlUtils.htmlEscape(value);

        }

        return value;

    }

    public static void main(String[] args) throws PolicyException {
        getPolicy();
        

        String value = null;
        value = xssClean
                ("<br>select  ***//||&;/*-+ <>$###@%$#@$%^#$^%$&^(&*)*\\''count or %% ..... ,,,, ");
        System.out.println("type-1: '" + value + "'");

        value = xssClean
                ("<scRIPt src='' onerror='alert(document.cookie)'></script>");
        System.out.println("type-2: '" + value + "'");

        value = xssClean("</script>");
        System.out.println("type-3: '" + value + "'");

        value = xssClean(" eval(abc);");
        System.out.println("type-4: '" + value + "'");

        value = xssClean(" expression(abc);");
        System.out.println("type-5: '" + value + "'");

        value = xssClean
                ("<img src='' onerror='alert(document.cookie);'></img>");
        System.out.println("type-6: '" + value + "'");

        value = xssClean
                ("<img src='' onerror='alert(document.cookie);'/>");
        System.out.println("type-7: '" + value + "'");

        value = xssClean
                ("<img src='' onerror='alert(document.cookie);'>");
        System.out.println("type-8: '" + value + "'");

        value = xssClean
                ("<script language=text/javascript>alert(document.cookie);");
        System.out.println("type-9: '" + value + "'");

        value = xssClean("<script>window.location='url'");
        System.out.println("type-10: '" + value + "'");

        value = xssClean(" onload='alert(\"abc\");");
        System.out.println("type-11: '" + value + "'");

        value = xssClean("<img src=x<!--'<\"-->>");
        System.out.println("type-12: '" + value + "'");

        value = xssClean("<=img onstop=");
        System.out.println("type-13: '" + value + "'");
        

        value = xssClean("<title>欢迎登录多用途卡清算管理系统'><script>alert(2740)</script></title>");
        System.out.println("type-14: '" + value + "'");
    
    }

}
