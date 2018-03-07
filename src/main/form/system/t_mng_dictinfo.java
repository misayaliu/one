package jieyi.app.form.system;

import java.io.Serializable;

import jieyi.app.form.BaseForm;

public class t_mng_dictinfo extends BaseForm implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String dicttype;
	private String dicttypedesc;
	private String dictvalue;
	private String dictvaluedesc;
	private String locale;
	
	public String getDicttype() {
		return dicttype;
	}
	public void setDicttype(String dicttype) {
		this.dicttype = dicttype;
	}
	public String getDicttypedesc() {
		return dicttypedesc;
	}
	public void setDicttypedesc(String dicttypedesc) {
		this.dicttypedesc = dicttypedesc;
	}
	public String getDictvalue() {
		return dictvalue;
	}
	public void setDictvalue(String dictvalue) {
		this.dictvalue = dictvalue;
	}
	public String getDictvaluedesc() {
		return dictvaluedesc;
	}
	public void setDictvaluedesc(String dictvaluedesc) {
		this.dictvaluedesc = dictvaluedesc;
	}
	public String getLocale() {
		return locale;
	}
	public void setLocale(String locale) {
		this.locale = locale;
	}
	
	/***********一些数据字典的常量Start************/
	public static String DICTINFO_DICTTYPE_SYSTEMFLAG = "10001";//systemflag
	public static String DICTINFO_DICTTYPE_USERSTATE = "10002";//userstate
	public static String DICTINFO_DICTTYPE_SEX = "10003";//sex
	public static String DICTINFO_DICTTYPE_USERTYPE = "10004";//usertype
	
	/***********一些数据字典的常量End************/
	

}
