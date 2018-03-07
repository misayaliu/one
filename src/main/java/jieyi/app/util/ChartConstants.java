package jieyi.app.util;

public enum ChartConstants {

	PIE("1","pie"),
	BAR("2","bar"),
	LINE("3","line");
	private String code;
	private String name;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	private ChartConstants(String code,String name){
    	this.code = code;
    	this.name = name;
     }
}
