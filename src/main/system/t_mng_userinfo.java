package jieyi.app.form.system;

import java.io.Serializable;

import jieyi.app.form.BaseForm;

public class t_mng_userinfo extends BaseForm implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String userid;
	private String usercode;
	private String password;
	private String username;
	private String sex;
	private String mailbox;
	private String telphone;
	private String cellphone;
	private String createdate;
	private String state;
	private String operatorid;
	private String logoncardid;
	private String usertype;
	private String pwderrtimes;
	private String lastpwderrtime;
	private String securitypolicy;
	private String ischangepwd;
	private String skin;
	private String lastpwdmodifytime;
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getUsercode() {
		return usercode;
	}
	public void setUsercode(String usercode) {
		this.usercode = usercode;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getMailbox() {
		return mailbox;
	}
	public void setMailbox(String mailbox) {
		this.mailbox = mailbox;
	}
	public String getTelphone() {
		return telphone;
	}
	public void setTelphone(String telphone) {
		this.telphone = telphone;
	}
	public String getCellphone() {
		return cellphone;
	}
	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}
	public String getCreatedate() {
		return createdate;
	}
	public void setCreatedate(String createdate) {
		this.createdate = createdate;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getOperatorid() {
		return operatorid;
	}
	public void setOperatorid(String operatorid) {
		this.operatorid = operatorid;
	}
	public String getLogoncardid() {
		return logoncardid;
	}
	public void setLogoncardid(String logoncardid) {
		this.logoncardid = logoncardid;
	}
	public String getUsertype() {
		return usertype;
	}
	public void setUsertype(String usertype) {
		this.usertype = usertype;
	}
	public String getPwderrtimes() {
		return pwderrtimes;
	}
	public void setPwderrtimes(String pwderrtimes) {
		this.pwderrtimes = pwderrtimes;
	}
	public String getLastpwderrtime() {
		return lastpwderrtime;
	}
	public void setLastpwderrtime(String lastpwderrtime) {
		this.lastpwderrtime = lastpwderrtime;
	}
	public String getSecuritypolicy() {
		return securitypolicy;
	}
	public void setSecuritypolicy(String securitypolicy) {
		this.securitypolicy = securitypolicy;
	}
	public String getIschangepwd() {
		return ischangepwd;
	}
	public void setIschangepwd(String ischangepwd) {
		this.ischangepwd = ischangepwd;
	}
	public String getSkin() {
		return skin;
	}
	public void setSkin(String skin) {
		this.skin = skin;
	}
	public String getLastpwdmodifytime() {
		return lastpwdmodifytime;
	}
	public void setLastpwdmodifytime(String lastpwdmodifytime) {
		this.lastpwdmodifytime = lastpwdmodifytime;
	}
	
}
