package jieyi.app.form.system;

import java.io.Serializable;
import java.util.List;

import jieyi.app.form.BaseForm;

public class t_mng_moduleinfo extends BaseForm implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String systemid;
	private String moduleid;
	private String modulename;
	private String parentid;
	private String moduletype;
	private String control;
	private String moduleorder;
	private String moduleicon;
	private String logflag;
	private String modulelevel;
	private String htmldesc;
	
	private List<t_mng_moduleinfo> subMouleinfo;
	private String roleid;
	private boolean checked = false;
	
	public String getSystemid() {
		return systemid;
	}
	public void setSystemid(String systemid) {
		this.systemid = systemid;
	}
	public String getModuleid() {
		return moduleid;
	}
	public void setModuleid(String moduleid) {
		this.moduleid = moduleid;
	}
	public String getModulename() {
		return modulename;
	}
	public void setModulename(String modulename) {
		this.modulename = modulename;
	}
	public String getParentid() {
		return parentid;
	}
	public void setParentid(String parentid) {
		this.parentid = parentid;
	}
	public String getModuletype() {
		return moduletype;
	}
	public void setModuletype(String moduletype) {
		this.moduletype = moduletype;
	}
	public String getControl() {
		return control;
	}
	public void setControl(String control) {
		this.control = control;
	}
	public String getModuleorder() {
		return moduleorder;
	}
	public void setModuleorder(String moduleorder) {
		this.moduleorder = moduleorder;
	}
	public String getModuleicon() {
		return moduleicon;
	}
	public void setModuleicon(String moduleicon) {
		this.moduleicon = moduleicon;
	}
	public String getLogflag() {
		return logflag;
	}
	public void setLogflag(String logflag) {
		this.logflag = logflag;
	}
	public String getModulelevel() {
		return modulelevel;
	}
	public void setModulelevel(String modulelevel) {
		this.modulelevel = modulelevel;
	}
	public List<t_mng_moduleinfo> getSubMouleinfo() {
		return subMouleinfo;
	}
	public void setSubMouleinfo(List<t_mng_moduleinfo> subMouleinfo) {
		this.subMouleinfo = subMouleinfo;
	}
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	public String getRoleid() {
		return roleid;
	}
	public void setRoleid(String roleid) {
		this.roleid = roleid;
	}
	public String getHtmldesc() {
		return htmldesc;
	}
	public void setHtmldesc(String htmldesc) {
		this.htmldesc = htmldesc;
	}

}
