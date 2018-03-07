package jieyi.app.form.report;

import jieyi.app.form.BaseForm;

public class t_report_outkey extends BaseForm {
	private String outkeyid;// NUMBER(8) not null,
	private String outkeyname;// VARCHAR2(40),
	private String outkeytable;// VARCHAR2(40),
	private String outkeysql;// VARCHAR2(255),
	private String outkeydesc;// VARCHAR2(100),
	private String callservicebean;// VARCHAR2(30)

	public String getOutkeyid() {
		return outkeyid;
	}

	public void setOutkeyid(String outkeyid) {
		this.outkeyid = outkeyid;
	}

	public String getOutkeyname() {
		return outkeyname;
	}

	public void setOutkeyname(String outkeyname) {
		this.outkeyname = outkeyname;
	}

	public String getOutkeytable() {
		return outkeytable;
	}

	public void setOutkeytable(String outkeytable) {
		this.outkeytable = outkeytable;
	}

	public String getOutkeysql() {
		return outkeysql;
	}

	public void setOutkeysql(String outkeysql) {
		this.outkeysql = outkeysql;
	}

	public String getOutkeydesc() {
		return outkeydesc;
	}

	public void setOutkeydesc(String outkeydesc) {
		this.outkeydesc = outkeydesc;
	}

	public String getCallservicebean() {
		return callservicebean;
	}

	public void setCallservicebean(String callservicebean) {
		this.callservicebean = callservicebean;
	}

}
