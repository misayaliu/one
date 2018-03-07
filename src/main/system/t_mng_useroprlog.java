package jieyi.app.form.system;

import java.io.Serializable;

import jieyi.app.form.BaseForm;

public class t_mng_useroprlog extends BaseForm implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String systemid;// NUMBER(10),
	private String logdate;// NUMBER(8),
	private String logtime;// NUMBER(6),
	private String userid;// VARCHAR2(32),
	private String username;// VARCHAR2(32),
	private String hostip;// VARCHAR2(50),
	private String msg;// VARCHAR2(200),
	private String requestjsondata;// VARCHAR2(4000),
	private String requestjsondata1;// VARCHAR2(4000)
	
	public String getSystemid() {
		return systemid;
	}
	public void setSystemid(String systemid) {
		this.systemid = systemid;
	}
	public String getLogdate() {
		return logdate;
	}
	public void setLogdate(String logdate) {
		this.logdate = logdate;
	}
	public String getLogtime() {
		return logtime;
	}
	public void setLogtime(String logtime) {
		this.logtime = logtime;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getHostip() {
		return hostip;
	}
	public void setHostip(String hostip) {
		this.hostip = hostip;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getRequestjsondata() {
		return requestjsondata;
	}
	public void setRequestjsondata(String requestjsondata) {
		this.requestjsondata = requestjsondata;
	}
	public String getRequestjsondata1() {
		return requestjsondata1;
	}
	public void setRequestjsondata1(String requestjsondata1) {
		this.requestjsondata1 = requestjsondata1;
	}

}
