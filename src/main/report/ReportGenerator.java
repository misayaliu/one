package jieyi.app.util.report;

import java.util.ArrayList;
import java.util.List;

public class ReportGenerator {
	
	/**
	 * 整理SQL，把里面的关键字替换
	 * @param list 
	 * @param sql 
	 * */
	public String sqlArrange(String sql, List<StringBuffer> paras) throws Exception{
		int i=0;
		for(StringBuffer sb:paras){
			String str_tmp = sb.toString();
			int localsp1 = str_tmp.indexOf("&");
			int localsp2 = str_tmp.indexOf("&",localsp1+1);
			int kaishisp1 = str_tmp.indexOf("@");
			int kaishisp2 = str_tmp.indexOf("@",kaishisp1+1);
			int leijisp1 = str_tmp.indexOf("#");
			int leijisp2 = str_tmp.indexOf("#",leijisp1+1);
			int jiesuriqi = str_tmp.indexOf("*");
			if(localsp1 != -1){
				String localstr = str_tmp.substring(0, localsp1) + str_tmp.substring(localsp2+1, str_tmp.length());
				String spstr = str_tmp.substring(localsp1+1, localsp2);
				int ii = ++i;
				if(localsp1 != -1){
					sql=sql.replaceAll("&:"+ii,spstr);
					sql=sql.replaceAll("&", spstr.substring(spstr.length()-2, spstr.length()-1));
				}
				sql=sql.replace("?:"+ii,localstr);
			}else if(kaishisp1!=-1){
				String localstr = str_tmp.substring(0, kaishisp1) + str_tmp.substring(leijisp2+1, str_tmp.length());
				String spstr1 = str_tmp.substring(kaishisp1+1, kaishisp2);
				String spstr2 = str_tmp.substring(leijisp1+1, leijisp2);
				int ii = ++i;
				sql=sql.replaceAll("@@",spstr1);
				sql=sql.replaceAll("##",spstr2);
				sql=sql.replace("?:"+ii,localstr);
				sql=sql.replace("??"+ii,localstr);
			}else if(jiesuriqi!=-1){
				int j=++i;
				String str1=str_tmp.substring(jiesuriqi+1);
				String str2=str1.replaceAll(">", "");
				str2=str1.replaceAll("<", "");
				sql=sql.replace("?:"+j,str1);
				sql=sql.replace("*",str2);
			}else{
				int j=++i;
				sql=sql.replace("?:"+(j),sb.toString());
				sql=sql.replace("??"+(j),sb.toString());
			}
			

		}
		if(paras.size()==0){
			sql=sql.replace("?:1","");
			sql=sql.replace("&:1","");
			sql=sql.replace("??2","");
		}
		System.out.println(sql);
		return sql;
	}

	/**
	 * 整理SQL，把里面的商户和机构替换 数据隔离
	 * @param instAndMchnt 
	 * @param sql 机构和商户拼接字符串
	 * */
	public String sqlArrangeForInstAndMchnt(String sql, String instAndMchnt) throws Exception{
		if(sql.indexOf("\\*") == -1 && sql.indexOf("#") ==-1){
			return sql ;
		}
		/** 机构代码 */
		String inst = instAndMchnt.substring(0, 8);
		/** 商户编号 */
		String mchnt_no= instAndMchnt.substring(8, 23);
		if(inst.equals("00000000")){ //系统管理员查看报表，则查看全部机构的
			sql=sql.replaceAll("\\*:1"," is not null");
		}else{
			sql=sql.replaceAll("\\*:1","="+inst);
		}
		if(mchnt_no.equals("000000000000000")){ //用户不属于商户级用户，则查询全部商户的
			sql=sql.replaceAll("#:1"," is not null");
		}else{
			sql=sql.replaceAll("#:1","="+mchnt_no);
		}	
		return sql;
	}

//	public String[][] dataStrToData(String dataStr) throws Exception {
//		// TODO Auto-generated method stub
//		JSONArray jsonArray = JSONArray.fromObject(dataStr);
//		List list = JSONArray.toList(jsonArray);
//		List list0 = (ArrayList) list.get(0);
//		String[][] data = new String[list.size()][list0.size()];
//		for(int i=0;i<list.size();i++){
//			List list1 = (ArrayList) list.get(i);
//			for(int j=0;j<list1.size();j++){
//				data[i][j] = (String) list1.get(j);
//			}
//		}
//		return data;
//	}

}
