package jieyi.app.form.chart;



public class t_chart_base {
	 

	private String chart_id;
	
	/**
	 * 标题
	 */
	
    private String text;
    
    /**
     * 副标题
     */
	
    private String sub_text;
    
    /**
     * 图表类型
     */
	
    private String chart_type;
    
    /**
     * 统计sql语句
     */
	
    private String chart_sql;
    
    private String query_domain_count;// NUMBER(2),
    
    private String chart_typedesc;

	public String getChart_id() {
		return chart_id;
	}

	public void setChart_id(String chart_id) {
		this.chart_id = chart_id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getSub_text() {
		return sub_text;
	}

	public void setSub_text(String sub_text) {
		this.sub_text = sub_text;
	}


	public String getChart_type() {
		return chart_type;
	}

	public void setChart_type(String chart_type) {
		this.chart_type = chart_type;
	}

	public String getChart_sql() {
		return chart_sql;
	}

	public void setChart_sql(String chart_sql) {
		this.chart_sql = chart_sql;
	}

	public String getQuery_domain_count() {
		return query_domain_count;
	}

	public void setQuery_domain_count(String query_domain_count) {
		this.query_domain_count = query_domain_count;
	}

	public String getChart_typedesc() {
		return chart_typedesc;
	}

	public void setChart_typedesc(String chart_typedesc) {
		this.chart_typedesc = chart_typedesc;
	}
    
    
} 