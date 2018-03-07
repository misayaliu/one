package jieyi.app.controller.report;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jieyi.app.controller.BaseController;
import jieyi.app.dubbo.ConsoleService;
import jieyi.app.dubbo.JDBCDaoService;
import jieyi.app.form.report.t_report_base;
import jieyi.app.form.report.t_report_outkey;
import jieyi.app.form.system.t_mng_userinfo;
import jieyi.app.util.Const;
import jieyi.app.util.PageData;
import jieyi.app.util.report.ReportGenerator;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.gson.reflect.TypeToken;

/**
 * 模糊查询
 * by wei.feng
 * 2015-8-6
 * */
@Controller
@RequestMapping(value = "vague")
public class VagueCtl extends BaseController {
	@Resource(name = "consoleService")
	private ConsoleService consoleService;
	
	@Resource(name = "JDBCDaoService")
	private JDBCDaoService jdbcDao;
	
	/**
	 * 获得模糊查询下拉列表的值
	 * */
	@RequestMapping(value="/getVague")
	public void getVague(HttpServletRequest request,HttpServletResponse response,String out_key_name,String fathervalue) throws Exception{
		request.setCharacterEncoding("UTF-8");
		pd = this.getPageData();
		String term = (String) pd.get("term");//输入框传过来的值
		pd.remove("term");//怕乱码影响
		term = new String(term.getBytes("ISO-8859-1"),"UTF-8");
		List<Map<String,String>> retList = new ArrayList<Map<String,String>>();
		//1、根据out_key_name查询
		PageData pd1 = new PageData();
		pd1.put("outkeyname", out_key_name);
		String reportOutKeyStr = consoleService.selectRecordOne(gson.toJson(pd1),"t_report_outkey.selectAll", "jieyi.app.util.PageData");
		t_report_outkey reportoutkey = gson.fromJson(reportOutKeyStr,new TypeToken<t_report_outkey>() {}.getType());
		String sql = reportoutkey.getOutkeysql();
		sql = sql.replaceAll("[?]", term.trim());
		ReportGenerator reportGenerator = new ReportGenerator();
		//获取当前登陆用户的机构商户信息
		t_mng_userinfo user = (t_mng_userinfo) request.getSession().getAttribute(Const.SESSION_USER);
		String userid =user.getUserid();
		String instSql ="select NVL2(t.instcd,t.instcd,'00000000')||NVL2(t.mchnt_no,t.mchnt_no,'000000000000000') from tbl_mng_userinstrel t where t.userid='"+userid+"'";
		String [][]instAndMchnt = jdbcDao.getTDArrayBySql(instSql);
		sql = reportGenerator.sqlArrangeForInstAndMchnt(sql, instAndMchnt[0][0]);
		//select t.mchnt_no, t.mchnt_name from tbl_bse_info_mchnt t where t.mchnt_no like '%?%' or t.mchnt_name like '%?%' and t.acqinst *:1 and t.mchnt_no #:1
		//2、执行外键表中的sql
		String[][] data = jdbcDao.getTDArrayBySql(sql,30);
		if(data==null||data.equals("null")||data.length==0){
			
		}else{
			for(int i = 0;i<data.length;i++){
				Map<String,String> map = new HashMap<String,String>();
				map.put("postvalue", data[i][0]);
				map.put("value", data[i][1]);
				//map.put("label", data[i][1]+"("+data[i][0]+")");
				map.put("label", data[i][0]+" "+data[i][1]);
				retList.add(map);
			}
		}
		
		writeJSONObject(request,response,gson.toJson(retList));
	}
	
	/**
	 * 获得权限下拉列表的值
	 * */
	@RequestMapping(value="/getPriviVague")
	public void getPriviVague(HttpServletRequest request,HttpServletResponse response,String out_key_name,String priviVagueValue) throws Exception{
		request.setCharacterEncoding("UTF-8");
		pd = this.getPageData();
		//List<Map<String,String>> retList = new ArrayList<Map<String,String>>();
		//1、根据out_key_name查询
		PageData pd1 = new PageData();
		pd1.put("outkeyname", out_key_name);
		String reportOutKeyStr = consoleService.selectRecordOne(gson.toJson(pd1),"t_report_outkey.selectAll", "jieyi.app.util.PageData");
		t_report_outkey reportoutkey = gson.fromJson(reportOutKeyStr,new TypeToken<t_report_outkey>() {}.getType());
		String sql = reportoutkey.getOutkeysql();
		sql = sql.replaceAll("[?]", priviVagueValue);
		//sql.replaceAll("?", term);
		
		//2、执行外键表中的sql
		String sRet = "";
		String[][] data = jdbcDao.getTDArrayBySql(sql,30);
		if(data==null||data.equals("null")||data.length==0){
			
		}else{
			sRet = data[0][1];
		}
		
		writeJSONObject(request,response,sRet);
	}
	
	/**
	 * 获得级联下拉框下拉列表的值
	 * */
	@RequestMapping(value="/getCasVague")
	public void getCasVague(HttpServletRequest request,HttpServletResponse response,String out_key_name,String fathervalue) throws Exception{
		request.setCharacterEncoding("UTF-8");
		pd = this.getPageData();
		String term = (String) pd.get("term");//输入框传过来的值
		term = new String(term.getBytes("ISO-8859-1"),"UTF-8");
		List<Map<String,String>> retList = new ArrayList<Map<String,String>>();
		//1、根据out_key_name查询
		PageData pd1 = new PageData();
		pd1.put("outkeyname", out_key_name);
		String reportOutKeyStr = consoleService.selectRecordOne(gson.toJson(pd1),"t_report_outkey.selectAll", "jieyi.app.util.PageData");
		t_report_outkey reportoutkey = gson.fromJson(reportOutKeyStr,new TypeToken<t_report_outkey>() {}.getType());
		String sql = reportoutkey.getOutkeysql();
		sql = sql.replaceAll("[?]", term.trim());
		sql = sql.replaceAll("[#]", fathervalue);
		
		//机构隔离
		t_mng_userinfo user = (t_mng_userinfo) request.getSession().getAttribute(Const.SESSION_USER);
		String userid =user.getUserid();
		String instSql ="select NVL2(t.instcd,t.instcd,'00000000')||NVL2(t.mchnt_no,t.mchnt_no,'000000000000000') from tbl_mng_userinstrel t where t.userid='"+userid+"'";
		String [][]instAndMchnt = jdbcDao.getTDArrayBySql(instSql);
		if(instAndMchnt[0][0].substring(0, 8).equals("00000000")){ //系统管理员查看报表，则查看全部机构的
			sql = sql + "and t.instcd !='00000000'";
		}else{
			sql = sql + "and t.instcd ='" + instAndMchnt[0][0].substring(0, 8)+"'";
		}
		logger.info("机构查询最终sql是："+sql);
		//2、执行外键表中的sql
		String[][] data = jdbcDao.getTDArrayBySql(sql,30);
		if(data==null||data.equals("null")||data.length==0){
			
		}else{
			for(int i = 0;i<data.length;i++){
				Map<String,String> map = new HashMap<String,String>();
				map.put("postvalue", data[i][0]);
				map.put("value", data[i][1]);
				//map.put("label", data[i][1]+"("+data[i][0]+")");
				map.put("label", data[i][0]+" "+data[i][1]);
				retList.add(map);
			}
		}
		
		writeJSONObject(request,response,gson.toJson(retList));
	}
	
//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//		String sql = "select t.dictvalue,t.dictvaluedesc from T_MNG_DICTINFO t where (t.dicttype like '%?%' or t.dictvaluedesc like '%?%') and t.dicttype=99002 order by t.dicttype,t.dictvalue";
//		System.out.println(sql);
//		sql = sql.replaceAll("[?]", "2222");
//		System.out.println(sql);
//	}
}
