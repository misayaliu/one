package jieyi.app.util.report;

public class Table {
	private Thead thead;
	private Tbody tbody;
	private String className;
	private String border;
	private String align;
	private String width;
	private String theadHtml;
	private String idName;

	public String getTheadHtml() {
		return theadHtml;
	}
	public void setTheadHtml(String theadHtml) {
		this.theadHtml = theadHtml;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getBorder() {
		return border;
	}
	public void setBorder(String border) {
		this.border = border;
	}
	public String getAlign() {
		return align;
	}
	public void setAlign(String align) {
		this.align = align;
	}
	public String getWidth() {
		return width;
	}
	public void setWidth(String width) {
		this.width = width;
	}
	public Thead getThead() {
		return thead;
	}
	public void setThead(Thead thead) {
		this.thead = thead;
	}
	public Tbody getTbody() {
		return tbody;
	}
	public void setTbody(Tbody tbody) {
		this.tbody = tbody;
	}
	public String getIdName() {
		return idName;
	}
	public void setIdName(String idName) {
		this.idName = idName;
	}
	
}
