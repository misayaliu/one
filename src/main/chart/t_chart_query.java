package jieyi.app.form.chart;

/**
 * 图表查询项实体类
 * 
 * @author huiFeng
 * 
 */
public class t_chart_query {

	/**
	 * 查询项编号
	 */
	private String chart_query_id;

	private String chart_id;

	/**
	 * 字段中文
	 */
	private String chart_query_chn;

	/**
	 * 字段英文名
	 */
	private String chart_query_eng;

	/**
	 * 输入类型（输入框、日期范围框等）
	 */
	private String chart_query_type;

	/**
	 * 优先级
	 */
	private String chart_query_priority;

	/**
	 * 关联外键名
	 */
	private String chart_query_outkeyname;

	/**
	 * 所属查询域（查询条件）
	 */
	private String chart_query_domains;

	/**
	 * 特有数据
	 */
	private String chart_query_spdata;

	// descs

	private String chart_textdesc;

	private String query_typedesc;

	private String is_needdesc;
	
   private String chart_typedesc;

	public String getChart_query_id() {
		return chart_query_id;
	}

	public void setChart_query_id(String chart_query_id) {
		this.chart_query_id = chart_query_id;
	}

	public String getChart_id() {
		return chart_id;
	}

	public void setChart_id(String chart_id) {
		this.chart_id = chart_id;
	}

	public String getChart_query_chn() {
		return chart_query_chn;
	}

	public void setChart_query_chn(String chart_query_chn) {
		this.chart_query_chn = chart_query_chn;
	}

	public String getChart_query_eng() {
		return chart_query_eng;
	}

	public void setChart_query_eng(String chart_query_eng) {
		this.chart_query_eng = chart_query_eng;
	}

	public String getChart_query_type() {
		return chart_query_type;
	}

	public void setChart_query_type(String chart_query_type) {
		this.chart_query_type = chart_query_type;
	}

	public String getChart_query_priority() {
		return chart_query_priority;
	}

	public void setChart_query_priority(String chart_query_priority) {
		this.chart_query_priority = chart_query_priority;
	}

	public String getChart_query_outkeyname() {
		return chart_query_outkeyname;
	}

	public void setChart_query_outkeyname(String chart_query_outkeyname) {
		this.chart_query_outkeyname = chart_query_outkeyname;
	}

	public String getChart_query_domains() {
		return chart_query_domains;
	}

	public void setChart_query_domains(String chart_query_domains) {
		this.chart_query_domains = chart_query_domains;
	}

	public String getChart_query_spdata() {
		return chart_query_spdata;
	}

	public void setChart_query_spdata(String chart_query_spdata) {
		this.chart_query_spdata = chart_query_spdata;
	}

	public String getChart_textdesc() {
		return chart_textdesc;
	}

	public void setChart_textdesc(String chart_textdesc) {
		this.chart_textdesc = chart_textdesc;
	}

	public String getQuery_typedesc() {
		return query_typedesc;
	}

	public void setQuery_typedesc(String query_typedesc) {
		this.query_typedesc = query_typedesc;
	}

	public String getIs_needdesc() {
		return is_needdesc;
	}

	public void setIs_needdesc(String is_needdesc) {
		this.is_needdesc = is_needdesc;
	}

	public String getChart_typedesc() {
		return chart_typedesc;
	}

	public void setChart_typedesc(String chart_typedesc) {
		this.chart_typedesc = chart_typedesc;
	}

}
