package jieyi.app.util.report;

public class Td {
	private String text;
	private String align;
	private String bgcolor;
	private String fontcolor;
	private String fontsize;
	private int rowspan;
	private int colspan;
	private String valign;
	private boolean isTotal=false;
	

	public String getFontsize() {
		return fontsize;
	}
	public void setFontsize(String fontsize) {
		this.fontsize = fontsize;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getAlign() {
		return align;
	}
	public void setAlign(String align) {
		this.align = align;
	}
	public String getBgcolor() {
		return bgcolor;
	}
	public void setBgcolor(String bgcolor) {
		this.bgcolor = bgcolor;
	}
	public String getFontcolor() {
		return fontcolor;
	}
	public void setFontcolor(String fontcolor) {
		this.fontcolor = fontcolor;
	}
	public int getRowspan() {
		return rowspan;
	}
	public void setRowspan(int rowspan) {
		this.rowspan = rowspan;
	}
	public int getColspan() {
		return colspan;
	}
	public void setColspan(int colspan) {
		this.colspan = colspan;
	}
	public String getValign() {
		return valign;
	}
	public void setValign(String valign) {
		this.valign = valign;
	}
	public boolean getIsTotal() {
		return isTotal;
	}
	public void setIsTotal(boolean isTotal) {
		this.isTotal = isTotal;
	}

}
