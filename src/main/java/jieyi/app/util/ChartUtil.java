package jieyi.app.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jieyi.app.form.chart.t_chart_base;
import jieyi.app.form.chart.t_chart_column;


public class ChartUtil{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7885407315416441190L;

	public static Map<String,Object> convertChart(t_chart_base base,List<t_chart_column> listColumn,String[][] str){
		Map<String,Object> map = new HashMap<String, Object>();
		List<Map<String, Object>> listStr = new ArrayList<Map<String, Object>>();
		if(str == null || str.length <=0){
			Map<String, Object> mapChild = new HashMap<String, Object>();
			mapChild.put("seriesData", null);
			mapChild.put("seriesName", null);
			listStr.add(mapChild);
		}else{
			for (int i = 0; i < str.length; i++) {
				Map<String, Object> mapChild = new HashMap<String, Object>();
				List<Map<String, Object>> listChild = new ArrayList<Map<String, Object>>();
				 for (int j = 1; j < str[i].length; j++) {
					Map<String, Object> mapStr = new HashMap<String, Object>();
					mapStr.put("value", str[i][j]);
					try {
						mapStr.put("name", listColumn.get(j - 1).getChart_data());
					} catch (Exception e) {
						mapStr.put("name", null);
						e.printStackTrace();
					}
					listChild.add(mapStr);
				}
				 
				mapChild.put("seriesData", listChild);
				String strX = str[i][0];
				if(strX.equals("") || strX == null){
					strX = "无";
				}
				mapChild.put("seriesName", strX);
	
				listStr.add(mapChild);
			}
		}
		map.put("chartColumn", listColumn);
		map.put("dataStr", listStr);
		map.put("text", base.getText());
		map.put("subtext", base.getSub_text());
		if(ChartConstants.BAR.getCode().equals(base.getChart_type())){
			map.put("chartType", ChartConstants.BAR.getName());
		}else if(ChartConstants.PIE.getCode().equals(base.getChart_type())){
			map.put("chartType", ChartConstants.PIE.getName());
		}else if(ChartConstants.LINE.getCode().equals(base.getChart_type())){
			map.put("chartType", ChartConstants.LINE.getName());
		}else{
			map.put("chartType", ChartConstants.LINE.getName());
		}
		

		return map;
	}
	
	
	public static String date2String(Date date, String format) {
		if (date == null) {
			return null;
		}
		if (format == null || format.equals("")) {
			format = "yyyyMMdd";
		}
		SimpleDateFormat simpleDateFormat = null;
		try {
			simpleDateFormat = new SimpleDateFormat(format);
		} catch (Exception e) {
			System.err.println("日期格式错误！！！");
			return null;
		}
		return simpleDateFormat.format(date);
	}
};
