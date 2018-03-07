package jieyi.app.form.report;

import jieyi.app.form.BaseForm;

public class t_report_base extends BaseForm {
	private String report_id;// NUMBER(8) not null,
	private String report_name;// VARCHAR2(50),
	private String short_eng;// VARCHAR2(50),
	private String need_batch_no;// VARCHAR2(1),
	private String need_format;// VARCHAR2(1),
	private String need_header;// VARCHAR2(1),
	private String header;// VARCHAR2(4000),
	private String has_seq;// VARCHAR2(1),
	private String sqls;// VARCHAR2(4000),
	private String status;// VARCHAR2(1),
	private String is_extend;// VARCHAR2(1),
	private String extend_class;// VARCHAR2(200),
	private String query_domain_count;// NUMBER(2),
	private String create_uid;// VARCHAR2(32),
	private String create_time;// VARCHAR2(14),
	private String last_modify_uid;// VARCHAR2(32),
	private String last_modify_time;// VARCHAR2(14),
	private String notes;// VARCHAR2(200),
	private String resv_fld1;// VARCHAR2(20),
	private String resv_fld2;// VARCHAR2(100),
	
	public String getReport_id() {
		return report_id;
	}
	public void setReport_id(String report_id) {
		this.report_id = report_id;
	}
	public String getReport_name() {
		return report_name;
	}
	public void setReport_name(String report_name) {
		this.report_name = report_name;
	}
	public String getShort_eng() {
		return short_eng;
	}
	public void setShort_eng(String short_eng) {
		this.short_eng = short_eng;
	}
	public String getNeed_batch_no() {
		return need_batch_no;
	}
	public void setNeed_batch_no(String need_batch_no) {
		this.need_batch_no = need_batch_no;
	}
	public String getNeed_format() {
		return need_format;
	}
	public void setNeed_format(String need_format) {
		this.need_format = need_format;
	}
	public String getNeed_header() {
		return need_header;
	}
	public void setNeed_header(String need_header) {
		this.need_header = need_header;
	}
	public String getHeader() {
		return header;
	}
	public void setHeader(String header) {
		this.header = header;
	}
	public String getHas_seq() {
		return has_seq;
	}
	public void setHas_seq(String has_seq) {
		this.has_seq = has_seq;
	}
	public String getSqls() {
		return sqls;
	}
	public void setSqls(String sqls) {
		this.sqls = sqls;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getIs_extend() {
		return is_extend;
	}
	public void setIs_extend(String is_extend) {
		this.is_extend = is_extend;
	}
	public String getExtend_class() {
		return extend_class;
	}
	public void setExtend_class(String extend_class) {
		this.extend_class = extend_class;
	}
	public String getQuery_domain_count() {
		return query_domain_count;
	}
	public void setQuery_domain_count(String query_domain_count) {
		this.query_domain_count = query_domain_count;
	}
	public String getCreate_uid() {
		return create_uid;
	}
	public void setCreate_uid(String create_uid) {
		this.create_uid = create_uid;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public String getLast_modify_uid() {
		return last_modify_uid;
	}
	public void setLast_modify_uid(String last_modify_uid) {
		this.last_modify_uid = last_modify_uid;
	}
	public String getLast_modify_time() {
		return last_modify_time;
	}
	public void setLast_modify_time(String last_modify_time) {
		this.last_modify_time = last_modify_time;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public String getResv_fld1() {
		return resv_fld1;
	}
	public void setResv_fld1(String resv_fld1) {
		this.resv_fld1 = resv_fld1;
	}
	public String getResv_fld2() {
		return resv_fld2;
	}
	public void setResv_fld2(String resv_fld2) {
		this.resv_fld2 = resv_fld2;
	}
	
}
