package jieyi.app.form;

public class BaseForm {
	
	private String classname;
	private String sqlMapNamespace;
	private String sqlTemp;
    private String sqlTempType;
    
	public String getClassname() {
		return classname;
	}
	public void setClassname(String classname) {
		this.classname = classname;
	}
	public String getSqlMapNamespace() {
		return sqlMapNamespace;
	}
	public void setSqlMapNamespace(String sqlMapNamespace) {
		this.sqlMapNamespace = sqlMapNamespace;
	}
	public String getSqlTemp() {
		return sqlTemp;
	}
	public void setSqlTemp(String sqlTemp) {
		this.sqlTemp = sqlTemp;
	}
	public String getSqlTempType() {
		return sqlTempType;
	}
	public void setSqlTempType(String sqlTempType) {
		this.sqlTempType = sqlTempType;
	}
}
