package jieyi.app.dubbo;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.support.rowset.SqlRowSet;

public interface JDBCDaoService {
	/*
	 * 功能：通过SQL获取二维数组
	 */
	public  String[][] getTDArrayBySql(String sql);
	
	public  String[][] getTDArrayBySql(String sql,int limit);
	
	public List<?> queryForList(String sql,Object[] args);

	public Map<?, ?> queryForMap(String sql,Object[] args);
	public String queryForRowSetStr(String sql) throws DataAccessException;
}
