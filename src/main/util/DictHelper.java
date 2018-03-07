package jieyi.app.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;





import jieyi.app.dubbo.ConsoleService;
import jieyi.app.util.PageData;

/**
 * 一个实用的字典帮助类
 * @author lacet
 *
 */
@SuppressWarnings("restriction")
public class DictHelper {
	private static Logger logger = Logger.getLogger(DictHelper.class);
	//重载标记，在数据字典变化时设置
	public  static boolean RELOAD_DICT = false ;
	private static  HashMap<String,List<PageData>>  dictMap= new HashMap<String,List<PageData>>(); // 字典信息

    private static Gson gson = new Gson();
	/*中心service的注解*/
	@Resource(name="consoleService")
	protected static ConsoleService centerService;
	
	/**
	 * 初始化系统缓存
	 */
	private static synchronized void  load(){
		
		if(RELOAD_DICT == false){
			//清空字典项
			dictMap = new HashMap<String,List<PageData>>();
			PageData pd1 = new PageData();		
			pd1.put("locale", "zh_CN");
			List<PageData> dictList=new ArrayList<PageData>();
			try {
				String pdsStr = centerService.selectRecordList(gson.toJson(pd1), "t_mng_dictinfo.selectAll","jieyi.app.util.PageData");
				dictList = gson.fromJson(pdsStr, new TypeToken<List<PageData>>(){}.getType());
				//转化成hash表
				for(int i = 0 ; i < dictList.size();i++){
					PageData pd =dictList.get(i);
					String dicttype = (String) pd.get("dicttype");
					 if(dictMap.containsKey(dicttype)){
						 dictMap.get(dicttype).add(pd);
					 }else{
						 List<PageData> newpdList = new ArrayList<PageData>();
						 newpdList.add(pd);
						 dictMap.put(dicttype, newpdList);
					}

				}
			} catch (Exception e) {
				logger.error("字典初始化失败："+e);
			}
			RELOAD_DICT = true ;
			dictList = null ;
		}
	}
	
	/**
	 * 初始化字典
	 * @param
	 * @return
	 * @author Kong
	 */
	public static HashMap<String,List<PageData>> getDicts(ConsoleService consumerService){
		if(centerService == null){
			centerService = consumerService;
		}
		if(dictMap == null || RELOAD_DICT == false){
			load();
		}
		return dictMap;
	} 
	
	/**
	 * 获取数据字典
	 * @param dictCode
	 * @return
	 */
	public static HashMap<String,List<PageData>> getDict(){
		if(dictMap == null || RELOAD_DICT==false){
			load();
		}
		return dictMap;
	}

	/**
	 * 获取数据字典值
	 * @param dictCode
	 * @return
	 */
	public static List<PageData> getDictValues(String dictCode){
		
		HashMap<String,List<PageData>> dict = getDict();
		if(dict != null){
			return dict.get(dictCode);
		}
		return null ;
	}

	

	/**
	 * 获取数据字典值名称
	 * @param dictType
	 * @param dictValue
	 * @return
	 */
	public static String translateDictValue(String dictType,String dictValue){		
		String ret = dictValue;
		HashMap<String,List<PageData>> dict = getDict();
		if(dict!=null){
			List<PageData> detail = dict.get(dictType);
			if(detail != null){
				for(PageData dtl : detail){
					if(dtl.get("dictvalue").equals(dictValue)){
						return (String) dtl.get("dictvaluedesc");
					}
				}
			}
		}
		return ret;
	}

	

	/**
	 * 清空字典缓存
	 */
	public static void clearDicts(){
		if (dictMap!=null) {
			dictMap.clear();
			dictMap = null;
		}
		RELOAD_DICT = false;
	}
}
