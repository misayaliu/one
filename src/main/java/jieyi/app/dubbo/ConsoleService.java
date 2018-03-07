package jieyi.app.dubbo;

public interface ConsoleService {
	public String queryPage(String queryCondition, String querySql, String queryClassPath) throws Exception;
	
	public String selectRecordList(String queryCondition, String querySql, String queryClassPath) throws Exception;
	
	public String selectRecordOne(String queryCondition, String querySql, String queryClassPath) throws Exception;
	
	public String addRecord(String formElements, String addSql, String addClassPath) throws Exception;
	
	public String editRecord(String formElements, String editSql, String editClassPath) throws Exception;
	
	public String deleteRecord(String formElements,String deleteSql,String deleteClassPath) throws Exception;
	
	public String iudRecord_mytransc(String paramStr, String classPathArrayStr) throws Exception;
	
	public String doForCreatePoiFile(String ppfInfoStr,String queryCondtion) throws Exception;
}
