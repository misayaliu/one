package jieyi.app.util.report;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import jieyi.app.dubbo.JDBCDaoService;
import jieyi.tools.util.DateUtil;
import jieyi.tools.util.StringUtil;

import org.springframework.stereotype.Service;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

public class SelectHelper {
	protected JDBCDaoService jdbcDao;
	
	private static String SQL_TXN_BY_QUERY="select txn_code,txn_name from  t_dict_txn_code where txn_code like '%?%' or txn_name like '%?%' order by txn_code asc ";
	private static String SQL_ORG_BY_QUERY="select org_code,org_fname from t_info_org where ( org_code like '%?%' or org_fname like '%?%' ) and status='1' and check_flag='2' and archived_flag='1' sqlCon order by org_code asc ";
	private static String SQL_DICT_BY_DICTENG="select T.DICTVALUE, T.DICTVALUEDESC from dictinfotb t  where T.DICTTYPEDESC = '?' and t.locale='zh_CN' ";
	
	private static String SQL_OUTKEY="select outkeysql,callservicebean from treportoutkeytb where outkeyname = '?'";
	
	public SelectHelper(JDBCDaoService jdbcDao){
		this.jdbcDao = jdbcDao;
	}
	
	public JDBCDaoService getJdbcDao() {
		return jdbcDao;
	}


	public void setJdbcDao(JDBCDaoService jdbcDao) {
		this.jdbcDao = jdbcDao;
	}


	public String getSelectByDictEng(String dictEng,String selectName) throws Exception {
		String[][] data=jdbcDao.getTDArrayBySql(SQL_DICT_BY_DICTENG.replace("?",dictEng));
		if(data==null||data.length==0)return "<select name=\""+selectName+"\"><option value=''>--请选择--</option></select>";
		StringBuffer sb=new StringBuffer("<select name=\""+selectName+"\"><option value=''>--请选择--</option>");
		for(String[] data1:data){
			sb.append("<option value='"+data1[0]+"' >").append(data1[1]).append("</option>");
		}
		sb.append("</select>");
		return sb.toString();
	}
	
	
	public String getGSelect(String selectName,String stat){
		String actionUrl="../webpages/report/sfw_handleRequest?stat="+stat;
		StringBuffer sb=new StringBuffer("<input type='text' name='sfw_"+selectName+"'  stat='"+selectName+"' size=\"30\" columns=\"2\" capture=\"1\" action=\""+actionUrl+"\" >");
		sb.append("<input type='text' name='"+selectName+"'   readonly > ");
		return sb.toString();
	}
	
	/**
	 * add by sx 20100720
	 * 生成外键select   
	 * @param selectName
	 * @param stat
	 * @param outKey  
	 * @return
	 */
	public String getGSelect(String selectName,String stat,String outKey,HttpServletRequest request){
		if(outKey==null||outKey.equals("")){
			return getGSelect(selectName,stat);
		}

		String actionUrl="sfwctl/sfw_handleRequest.do?stat="+stat+"&outKey="+outKey;
		//String actionUrl="../webpages/report/sfw_handleRequest.do?stat="+stat+"&outKey="+outKey;
		
		//处理级联查询时的相关匹配
		String fatherName=null;
		if(outKey.indexOf("|")>-1){
			fatherName=outKey.split("[|]")[1];
			actionUrl+="&fathername="+fatherName;
		}
		
		
		StringBuffer sb=new StringBuffer("<input type='text' id='sfw_"+selectName+"' name='sfw_"+selectName+"'  stat='"+selectName+"' size=\"20\" columns=\"2\" capture=\"1\" action=\""+actionUrl+"\" ");
		if(fatherName!=null){
			sb.append("fathername='sfw_"+fatherName+"' ");
		}
		//测试用
//		else{
//			sb.append(" norightvalue='1' ");
//			sb.append(" norighttext='a a a' ");
//		}
		
		sb.append(" /> ");
		sb.append("<input type='text' name='"+selectName+"'   readonly /> ");
		return sb.toString();
	}
	/**
	 * add by sx 20100720
	 * 生成外键select   
	 * @param selectName
	 * @param stat
	 * @param outKey  
	 * @return
	 */
	public String getGSelectEx(String selectName,String stat,String outKey){
		if(outKey==null||outKey.equals("")){
			return getGSelect(selectName,stat);
		}

		
		String actionUrl="../report/sfw_handleRequest?stat="+stat+"&outKey="+outKey;
		
		//处理级联查询时的相关匹配
		String fatherName=null;
		if(outKey.indexOf("|")>-1){
			fatherName=outKey.split("[|]")[1];
			actionUrl+="&fathername="+fatherName;
		}
		
		
		StringBuffer sb=new StringBuffer("<input type='text' name='sfw_"+selectName+"'  stat='"+selectName+"' size=\"20\" columns=\"2\" capture=\"1\" action=\""+actionUrl+"\" ");
		if(fatherName!=null){
			sb.append("fathername='sfw_"+fatherName+"' ");
		}
		//测试用
//		else{
//			sb.append(" norightvalue='1' ");
//			sb.append(" norighttext='a a a' ");
//		}
		
		sb.append(" /> ");
		return sb.toString();
	}
	
//	public String getGSelectDataFromTxn(String query) throws Exception {
//		String[][] data=jdbcDao.getTDArrayBySql(SQL_TXN_BY_QUERY.replaceAll("[?]",query),20);
//		if(data==null)data=new String[0][0];
//		return JSONArray.fromObject(data).toString();
//	}
//	
//	public String getGSelectDataFromOrg(String query) throws Exception {
//		String[][] data=jdbcDao.getTDArrayBySql(SQL_ORG_BY_QUERY.replaceAll("[?]",query).replace("sqlCon",""),20);
//		if(data==null)data=new String[0][0];
//		return JSONArray.fromObject(data).toString();
//	}
	
	
	public static void main(String[] args){

	}
	/**
	 * 生成outkey的结果集，add by sx 20100720
	 * @param query
	 * @param outKey
	 * @return
	 */
//	public List getOutKeySelectDataFromOrg(String query,String outKey,HttpServletRequest request) throws Exception {
//		String [][] outKeySql=jdbcDao.getTDArrayBySql(SQL_OUTKEY.replaceAll("[?]",outKey),1);
//		String[][] data=null;
//		String dataStr = null;
//		List list = null;
//		if(outKeySql==null||outKeySql.length<1){
//			data=new String[0][0];
//		}else{
//			String sql = outKeySql[0][0].replaceAll("[?]",query);
//			//获取webservice的bean
//			WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
//	        CenterWeb centerWeb = (CenterWeb)wac.getBean(outKeySql[0][1]);
//	        list=jdbcDao.queryForList(sql, null);
//	     //   return dataStr;
//		}
//		
//		//if(data==null)data=new String[0][0];
//		return list;
//	}
	
	public String getOutKeySql(String sqlName) throws Exception {
		return jdbcDao.getTDArrayBySql(SQL_OUTKEY.replaceAll("[?]",sqlName),1)[0][0];
	}
	
	
//	public String getRightSelect(String selectName,String stat,String outKey,String noRightValue,String noRightText,HttpServletRequest request){
//		if(outKey==null||outKey.equals("")){
//			return getGSelect(selectName,stat);
//		}
//
//		
//		String actionUrl=request.getContextPath()+"/webpages/report/sfw_handleRequest?stat="+stat+"&outKey="+outKey;
//		
//		//处理级联查询时的相关匹配
//		String fatherName=null;
//		if(outKey.indexOf("|")>-1){
//			fatherName=outKey.split("[|]")[1];
//			actionUrl+="&fathername="+fatherName;
//		}
//		
//		
//		StringBuffer sb=new StringBuffer("<input type='text' name='sfw_"+selectName+"'  stat='"+selectName+"' size=\"30\" columns=\"2\" capture=\"1\" action=\""+actionUrl+"\" ");
//		if(fatherName!=null){
//			sb.append("fathername='sfw_"+fatherName+"' ");
//		}
//
//			sb.append(" norightvalue='"+noRightValue+"' ");
//			sb.append(" norighttext='"+noRightText+"' ");
//
//		
//		sb.append(" /> ");
//		sb.append("<input type='text' name='"+selectName+"'   readonly /> ");
//		return sb.toString();
//	}
	
	/**
	 * 判断权限是否在该查询项
	 * @param checkRightSql
	 * @param query
	 * @return
	 */
//	public boolean isRightLevel(String checkRightSql,String query) throws Exception {
//		String outKeySql="select outkeysql from treportoutkeytb where outkeyname = '?'";
//		String [][] isRightSql=jdbcDao.getTDArrayBySql(outKeySql.replaceAll("[?]",checkRightSql),1);
//		String[][] data=null;
//		if(isRightSql==null||isRightSql.length<1){
//			return false;
//		}else{
//			//data=jdbcDao.getTDArrayBySql(isRightSql[0][0].replaceAll("[?]",query));
//		}
//		
//		if(data==null||data.length<1){
//			return false;
//		}
//		
//		return true;
//	}
	
	
	public List getOutKeySelectData(String query,String outKey,HttpServletRequest request) throws Exception {
		String [][] outKeySql=jdbcDao.getTDArrayBySql(SQL_OUTKEY.replaceAll("[?]",outKey),1);
		String[][] data=null;
		String dataStr = null;
		List list = null;
		if(outKeySql==null||outKeySql.length<1){
			data=new String[0][0];
		}else{
			String sql = outKeySql[0][0].replaceAll("[?]",query);
			//获取webservice的bean
			//WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
	      //  CenterWeb centerWeb = (CenterWeb)wac.getBean(outKeySql[0][1]);
	        list =jdbcDao.queryForList(sql, null);
		}
		
		//if(data==null)data=new String[0][0];
		return list;
	}
	
	public List getCaseKeySelectData(String query,String outKey,HttpServletRequest request) throws Exception {
		String [][] outKeySql=jdbcDao.getTDArrayBySql(SQL_OUTKEY.replaceAll("[?]",outKey),1);
		String[][] data=null;
		String dataStr = null;
		List list = null;
		if(outKeySql==null||outKeySql.length<1){
			data=new String[0][0];
		}else{
			String sql = outKeySql[0][0].replaceAll("[?]",query);
			//获取webservice的bean
			//WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
	      //  CenterWeb centerWeb = (CenterWeb)wac.getBean(outKeySql[0][1]);
	        list =jdbcDao.queryForList(sql, null);
		}
		
		//if(data==null)data=new String[0][0];
		return list;
	}
//	public String getYearSelect(String selectName,String stat,String outKey,HttpServletRequest request){
//		Date date = new Date();
//		String currentDate = ChartUtil.date2String(date, "yyyy");
//		Integer intDate = Integer.parseInt(currentDate);
//		StringBuffer sbu=new StringBuffer("<select name=\""+selectName+"\"><option value=''>--请选择--</option>");
//		for(int i = 0; i <=5; i++){
//			sbu.append("<option value='"+(intDate-i)+"' >").append(intDate-i).append("</option>");
//		}
//		sbu.append("</select>");
//		
//		
//		return sbu.toString();
//	}
	
}
