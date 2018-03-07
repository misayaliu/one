package jieyi.app.form.report;

import jieyi.app.form.BaseForm;

public class t_report_query extends BaseForm {
	private String query_id;// NUMBER(8) not null,
	private String report_id;// NUMBER(8),
	private String f_chn;// VARCHAR2(50),
	private String f_eng;// VARCHAR2(50),
	private String input_type;// VARCHAR2(2),
	private String is_need;// VARCHAR2(1),
	private String priority;// NUMBER(2),
	private String out_key_name;// VARCHAR2(200),
	private String query_domains;// VARCHAR2(200),
	private String sp_data;// VARCHAR2(200)

	public String getQuery_id() {
		return query_id;
	}

	public void setQuery_id(String query_id) {
		this.query_id = query_id;
	}

	public String getReport_id() {
		return report_id;
	}

	public void setReport_id(String report_id) {
		this.report_id = report_id;
	}

	public String getF_chn() {
		return f_chn;
	}

	public void setF_chn(String f_chn) {
		this.f_chn = f_chn;
	}

	public String getF_eng() {
		return f_eng;
	}

	public void setF_eng(String f_eng) {
		this.f_eng = f_eng;
	}

	public String getInput_type() {
		return input_type;
	}

	public void setInput_type(String input_type) {
		this.input_type = input_type;
	}

	public String getIs_need() {
		return is_need;
	}

	public void setIs_need(String is_need) {
		this.is_need = is_need;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getOut_key_name() {
		return out_key_name;
	}

	public void setOut_key_name(String out_key_name) {
		this.out_key_name = out_key_name;
	}

	public String getQuery_domains() {
		return query_domains;
	}

	public void setQuery_domains(String query_domains) {
		this.query_domains = query_domains;
	}

	public String getSp_data() {
		return sp_data;
	}

	public void setSp_data(String sp_data) {
		this.sp_data = sp_data;
	}

}
