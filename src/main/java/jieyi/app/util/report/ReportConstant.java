
package jieyi.app.util.report;

import java.util.Map;

/**
 * @author 
 *
 */
public class ReportConstant{

	
	public final static String REPORTRIGHTUSER="REPORTRIGHTUSER";

	public final static String SESSIONOUT_JSP="user";
	
	/*
	 * Action 返回
	 */
	public final static String RTNSTATUS="rtnStatus";
	public final static String RTNDESC="rtnDesc";
	
	public final static int RTNSTATUS_SUCCESS=0; //操作正确
	public final static int RTNSTATUS_SESSION_EXPIRED=99; //会话无效
	public final static int RTNSTATUS_EXCEPTION=98; //异常错误
	
	
	//用户级别
	public final static String REPORTRIGHTUSER_USERGRADER_MNG="1"; //系统管理人员
	public final static String REPORTRIGHTUSER_USERGRADER_OPR="2"; //系统操作人员
	public final static String REPORTRIGHTUSER_USERGRADER_OUTER="3"; //外连系统用户
	
	//用户状态
	public final static String REPORTRIGHTUSER_STATUS_START="1"; //启用
	public final static String REPORTRIGHTUSER_STATUS_STOP="2";  //停用
	/*
	 * Action 返回码描述
	 */
	public final static String RTNDESC_SESSION_EXPIRED="您未登录或长时间没有操作，请重新登录!";

	
	//编码
	public final static String CHARSET="UTF-8";
	
	
	public static Map<String,String> COMBOX_STR_HM=null;
	public static Map<String,Map<String,String>> COMBOX_CODE_HM=null;
	
	//public static Map<String,ReportRightUser> ACCEPORTUSER_HM=null;
	
	//状态
	public static String STATUS_START="1" ;      //启用
	public static String STATUS_STOP="2" ;      //停用
	
	public final static String NEED_YES="2";   //是
	public final static String NEED_NO="1";    //否

	public final static String REPORTRPBASE_NEEDHEADER_YES="2";   //是
	public final static String REPORTRPBASE_NEEDHEADER_NO="1";    //否
	
	public final static String REPORTRPCOLUMN_TMERGED_YES="2";    //是
	public final static String REPORTRPCOLUMN_TMERGED_NO="1";    //否
	
	public final static String REPORTRPCOLUMN_ROLLUPTYPE_NO="1";
	public final static String REPORTRPCOLUMN_ROLLUPTYPE_HEJI="2";
	public final static String REPORTRPCOLUMN_ROLLUPTYPE_XIAOJI="3";
	public final static String REPORTRPCOLUMN_ROLLUPTYPE_XUHAO="4";
	
	
	public final static String REPORTRPCOLUMN_FORMAT_YES="2";    //是
	public final static String REPORTRPCOLUMN_FORMAT_NO="1";    //否
	
	public final static String THEAD_TD_DEFAUL_ALIGN="center";
	public final static String THEAD_TD_DEFAUL_FONT_SIZE="2";	
	
	public final static String TBODY_TD_DEFAUL_FONT_SIZE="2";	
	
	
	public final static String REPORTRPBASE_STATUS_START="1";    //启用
	public final static String REPORTRPBASE_STATUS_STOP="2";    //停用

	
	public final static String INPUT_TYPE_TEXT="1";
	public final static String INPUT_TYPE_SELECT="2";
	public final static String INPUT_TYPE_SFW="3";
	public final static String INPUT_TYPE_STR_DATE="4";
	public final static String INPUT_TYPE_STR_DATE_AREA="5";
	public final static String INPUT_TYPE_HTTP="6";
	public final static String INPUT_TYPE_DATE="7";
	public final static String INPUT_TYPE_DATE_AREA="8";
	public final static String INPUT_TYPE_MONTH="9";
	public final static String INPUT_TYPE_CAS="10";
	public final static String INPUT_TYPE_MULTCAS="11";
	public final static String INPUT_TYPE_YEARMONTHDATE="12";
	/**
	 * 年份下拉框
	 */
	public final static String INPUT_TYPE_YEARSELECT = "13";
	
	public final static String ALIGN_LEFT="1";
	public final static String ALIGN_CENTER="2";
	public final static String ALIGN_RIGHT="3";
	
}


