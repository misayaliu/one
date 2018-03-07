package jieyi.app.form;

import java.io.Serializable;

public class PoiReportForm extends BaseForm implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String title;//报表名称
	private String filePath;//报表包办路径
	private String javaClassNameLocal;//本地类名
	private String javaClassNameInService;//服务器类名
	private String searchString;//数据开始的行
	private String fileType;//文件类型
	private String conditionStrForShow;//要被展示的查询条件
	private String fileCrtServiceName;//创建文件的服务的bean名称
	private String fileTransServiceName;//传输文件的服务的bean名称
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getJavaClassNameLocal() {
		return javaClassNameLocal;
	}
	public void setJavaClassNameLocal(String javaClassNameLocal) {
		this.javaClassNameLocal = javaClassNameLocal;
	}
	public String getJavaClassNameInService() {
		return javaClassNameInService;
	}
	public void setJavaClassNameInService(String javaClassNameInService) {
		this.javaClassNameInService = javaClassNameInService;
	}
	public String getSearchString() {
		return searchString;
	}
	public void setSearchString(String searchString) {
		this.searchString = searchString;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	public String getConditionStrForShow() {
		return conditionStrForShow;
	}
	public void setConditionStrForShow(String conditionStrForShow) {
		this.conditionStrForShow = conditionStrForShow;
	}
	public String getFileCrtServiceName() {
		return fileCrtServiceName;
	}
	public void setFileCrtServiceName(String fileCrtServiceName) {
		this.fileCrtServiceName = fileCrtServiceName;
	}
	public String getFileTransServiceName() {
		return fileTransServiceName;
	}
	public void setFileTransServiceName(String fileTransServiceName) {
		this.fileTransServiceName = fileTransServiceName;
	}
	
}
