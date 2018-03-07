package jieyi.app.form.report;

import jieyi.app.form.BaseForm;

public class t_report_column extends BaseForm {
	private String column_id;// NUMBER(8) not null,
	private String report_id;// NUMBER(8),
	private String column_name;// VARCHAR2(50),
	private String align;// VARCHAR2(1),
	private String font_color;// VARCHAR2(20),
	private String bg_color;// VARCHAR2(20),
	private String merged;// VARCHAR2(1),
	private String rollup_type;// VARCHAR2(1),
	private String merged_font_color;// VARCHAR2(20),
	private String merged_bg_color;// VARCHAR2(20),
	private String priority;// NUMBER(8),
	private String is_last_row_show;// VARCHAR2(1)

	public String getColumn_id() {
		return column_id;
	}

	public void setColumn_id(String column_id) {
		this.column_id = column_id;
	}

	public String getReport_id() {
		return report_id;
	}

	public void setReport_id(String report_id) {
		this.report_id = report_id;
	}

	public String getColumn_name() {
		return column_name;
	}

	public void setColumn_name(String column_name) {
		this.column_name = column_name;
	}

	public String getAlign() {
		return align;
	}

	public void setAlign(String align) {
		this.align = align;
	}

	public String getFont_color() {
		return font_color;
	}

	public void setFont_color(String font_color) {
		this.font_color = font_color;
	}

	public String getBg_color() {
		return bg_color;
	}

	public void setBg_color(String bg_color) {
		this.bg_color = bg_color;
	}

	public String getMerged() {
		return merged;
	}

	public void setMerged(String merged) {
		this.merged = merged;
	}

	public String getRollup_type() {
		return rollup_type;
	}

	public void setRollup_type(String rollup_type) {
		this.rollup_type = rollup_type;
	}

	public String getMerged_font_color() {
		return merged_font_color;
	}

	public void setMerged_font_color(String merged_font_color) {
		this.merged_font_color = merged_font_color;
	}

	public String getMerged_bg_color() {
		return merged_bg_color;
	}

	public void setMerged_bg_color(String merged_bg_color) {
		this.merged_bg_color = merged_bg_color;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getIs_last_row_show() {
		return is_last_row_show;
	}

	public void setIs_last_row_show(String is_last_row_show) {
		this.is_last_row_show = is_last_row_show;
	}

}
