package jieyi.app.form.filetrans;

/**
 * 文件传输
 * 上传报文流程 :1、0001；2、0002；3、0003；4、0004；
 *          第3、4步循环发送直到文件传完，若有后续则0003中responseCode为07，最后一个报文为00
 * 下载文件流程： 1、0011；2、0012；3、0013；4、0014；5、0015
 *          若存在文件则0012返回05，无文件返回04   
 *          第3、4步循环发送直到文件传完，若有后续则0014中responseCode为07，最后一个报文为00  
 *          客户端最后再发一个0015，让服务器端将文件挪动或者删除
 * 目前文件不支持断点传输
 * @author wei.feng
 * 2015-6-29   
 * */
public class FileTransContent {
	/**消息类型的宏定义*/
	public static String FILETRANS_MESSAGETYPE0001 = "0001";
	public static String FILETRANS_MESSAGETYPE0002 = "0002";
	public static String FILETRANS_MESSAGETYPE0003 = "0003";
	public static String FILETRANS_MESSAGETYPE0004 = "0004";
	public static String FILETRANS_MESSAGETYPE0011 = "0011";
	public static String FILETRANS_MESSAGETYPE0012 = "0012";
	public static String FILETRANS_MESSAGETYPE0013 = "0013";
	public static String FILETRANS_MESSAGETYPE0014 = "0014";
	public static String FILETRANS_MESSAGETYPE0015 = "0015";
	
	/**消息版本宏定义*/
	public static String FILETRANS_MESSAGEVER01 = "01";
	
	/**请求方类型宏定义*/
	public static String FILETRANS_REQUESTTYPE01 = "01";
	
	/**交易应答码宏定义*/
	public static String FILETRANS_RESPONSE00 = "00";
	public static String FILETRANS_RESPONSE04 = "04";//0012表示服务器无文件下载使用
	public static String FILETRANS_RESPONSE05 = "05";//0012表示服务器有文件下载使用
	public static String FILETRANS_RESPONSE07 = "07";//0003或者0014表示有后续数据
	
	/**
	 * 消息类型
	 * 0001:上传请求
	 * 0002:上传应答
	 * 0003:数据内容上传请求
	 * 0004:数据内容上传应答
	 * 0011:下载请求
	 * 0012:下载应答
	 * 0013:数据内容下载请求
	 * 0014:数据内容下载应答
	 * */
	private String messageType;
	
	/**
	 * 消息版本
	 * 目前全部用:01
	 * */
	private String messageVer;
	
	/**
	 * 消息版本
	 * 目前全部用:01
	 * */
	private String requestType;
	
	/**
	 * 请求方id
	 * 可以为机构代码、商户代码、或者0
	 * */
	private String requestId;
	
	/**
	 * 请求方id
	 * 可以为机构代码、商户代码、或者0
	 * */
	private String responseCode;
	
	/**
	 * 文件名
	 * */
	private String fileName;
	
	/**
	 * 文件大小
	 * */
	private String fileSize;
	
	/**
	 * 已接受文件大小
	 * 因为目前不支持断点续传，所以本域暂时无效
	 * */
	private String recvFileSize;
	
	/**
	 * 数据日期
	 * 如果是黑名单文件，则可以带数据日期
	 * */
	private String fileData;
	
	/**
	 * 数据
	 * */
	private String data;

	public String getMessageType() {
		return messageType;
	}

	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}

	public String getMessageVer() {
		return messageVer;
	}

	public void setMessageVer(String messageVer) {
		this.messageVer = messageVer;
	}

	public String getRequestType() {
		return requestType;
	}

	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileSize() {
		return fileSize;
	}

	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}

	public String getRecvFileSize() {
		return recvFileSize;
	}

	public void setRecvFileSize(String recvFileSize) {
		this.recvFileSize = recvFileSize;
	}

	public String getFileData() {
		return fileData;
	}

	public void setFileData(String fileData) {
		this.fileData = fileData;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}
	
}
