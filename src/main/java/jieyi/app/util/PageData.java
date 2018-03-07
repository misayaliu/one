package jieyi.app.util;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.google.gson.Gson;

public class PageData extends HashMap implements Map{
	
	protected Logger logger = Logger.getLogger(this.getClass());
	
	private static final long serialVersionUID = 1L;
	
	Map map = null;
	HttpServletRequest request;
	
	public PageData(HttpServletRequest request){
		this.request = request;
		Map properties = request.getParameterMap();
		Map returnMap = new HashMap(); 
		Iterator entries = properties.entrySet().iterator(); 
		Map.Entry entry; 
		String name = "";  
		String value = "";  
		while (entries.hasNext()) {
			entry = (Map.Entry) entries.next(); 
			name = (String) entry.getKey(); 
			Object valueObj = entry.getValue(); 
			if(null == valueObj){ 
				value = ""; 
			}else if(valueObj instanceof String[]){ 
				String[] values = (String[])valueObj;
				for(int i=0;i<values.length;i++){ 
					 value = values[i] + ",";
				}
				value = value.substring(0, value.length()-1); 
			}else{
				value = valueObj.toString(); 
			}
			logger.debug("before filter param=" +name);
			logger.debug("before filter paramValue=" +value);
//			name = XssFilterUtil.stripXss(name);
			value = XssFilterUtil.stripXss(value);
			logger.debug("after filter param=" +name);
			logger.debug("after filter paramValue=" +value);
			
			returnMap.put(name, value); 
		}
		map = returnMap;
	}
	
	public PageData() {
		map = new HashMap();
	}
	
	@Override
	public Object get(Object key) {
		if(key instanceof String){
			key = ((String) key).toLowerCase();
		}
		Object obj = null;
		if(map.get(key) instanceof Object[]) {
			Object[] arr = (Object[])map.get(key);
			obj = request == null ? arr:(request.getParameter((String)key) == null ? arr:arr[0]);
		} else {
			obj = map.get(key);
		}
		return obj;
	}
	
	public String getString(Object key) {
		if(key instanceof String){
			key = ((String) key).toLowerCase();
		}
//		if(key instanceof BigDecimal){
//			return get(key).toString();
//		}
//		return (String)get(key);
		if(get(key)==null){
			return "";
		}
		
		return get(key).toString();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Object put(Object key, Object value) {
		if(key instanceof String){
			key = ((String) key).toLowerCase();
		}
		if(value instanceof Integer||value instanceof Double||value instanceof Float||value instanceof Long){
			//return map.put(key, String.valueOf(value));
			return map.put(key, value.toString());
		}
		return map.put(key, value);
	}
	
	@Override
	public Object remove(Object key) {
		if(key instanceof String){
			key = ((String) key).toLowerCase();
		}
		return map.remove(key);
	}

	public void clear() {
		map.clear();
	}

	public boolean containsKey(Object key) {
		// TODO Auto-generated method stub
		if(key instanceof String){
			key = ((String) key).toLowerCase();
		}
		return map.containsKey(key);
	}

	public boolean containsValue(Object value) {
		// TODO Auto-generated method stub
		return map.containsValue(value);
	}

	public Set entrySet() {
		// TODO Auto-generated method stub
		return map.entrySet();
	}

	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return map.isEmpty();
	}

	public Set keySet() {
		// TODO Auto-generated method stub
		return map.keySet();
	}

	@SuppressWarnings("unchecked")
	public void putAll(Map t) {
		// TODO Auto-generated method stub
		map.putAll(t);
	}

	public int size() {
		// TODO Auto-generated method stub
		return map.size();
	}

	public Collection values() {
		// TODO Auto-generated method stub
		return map.values();
	}
	
//	public PageData transAllKeyLowercase(PageData pd){
//		Iterator entries = pd.entrySet().iterator(); 
//		PageData returnMap = new PageData(); 
//		while (entries.hasNext()) {
//			PageData.Entry entry; 
//			String name = "";  
//			String value = "";  
//			entry = (Map.Entry) entries.next(); 
//			name = (String) entry.getKey(); 
//			Object valueObj = entry.getValue(); 
//			returnMap.put(name.toLowerCase(), valueObj);
//		}
//		return returnMap;
//	}
	
	/**
	 * add by liuke
	 *     2015-3-26
	 * 将对象中的变量由aaa_bbb_ccc形式转化为aaaBbbCcc形式
	 * */
	public  <T> T toBean(Class<T> obj){
		Gson gson = new Gson();
		T objClass = null;
		Map<String,String> map2 = new HashMap<String, String>();
		PageData data = new PageData();
		Iterator iter = map.entrySet().iterator();  
		while(iter.hasNext()){
			Entry entry = (Entry)iter.next();
			String str = entry.getKey().toString();
			int num = str.split("_").length;
			if(num != 1){
				for(int i = 0;i<num;i++){
					if(-1!=str.indexOf("_")){
						String oldStr = str.substring(str.indexOf("_"),str.indexOf("_")+2);
						String newStr = str.substring(str.indexOf("_"),str.indexOf("_")+2).toUpperCase();
						str = str.replace(oldStr, newStr);
						str = str.replaceFirst("_", "");
					}
				}
			}
			String key = str;
			map2.put(key,  entry.getValue().toString());
        }
		
		objClass = gson.fromJson(gson.toJson(map2), obj);
		return objClass;
	}
	
}
