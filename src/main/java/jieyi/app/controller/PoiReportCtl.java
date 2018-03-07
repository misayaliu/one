package jieyi.app.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.text.DecimalFormat;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jieyi.app.dubbo.ConsoleService;
import jieyi.app.dubbo.FileTransService;
import jieyi.app.form.PoiReportForm;
import jieyi.app.form.filetrans.FileTransContent;
import jieyi.app.util.PropertyConfigure;
import jieyi.app.util.ReportUtil;
import jieyi.tools.util.FileUtil;
import jieyi.tools.util.StringUtil;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.google.gson.reflect.TypeToken;

/** 
 * 类名称：PoiReportCtl
 * 创建人：wei.feng 
 * 创建时间：2015年7月14日
 * @version
 * 描述：以POI导出的请求
 */
@Controller
@RequestMapping(value="/poireport")
public class PoiReportCtl extends BaseController {
	
	protected Logger logger = Logger.getLogger(this.getClass());
	
	@Resource(name = "propertyConfigure")
	private PropertyConfigure propertyConfigure;
	
	@RequestMapping("/poiReport_exportExcel.do")
	public String exportExcel(HttpServletRequest request,HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("UTF-8");
		DecimalFormat df = new DecimalFormat("#0.00");

		// 报表信息
		String jsonString = request.getParameter("jsonStr");
		logger.info("jsonString="+jsonString);
		logger.info("jsonString="+jsonString);
		jsonString = new String(jsonString.getBytes("ISO8859-1"), "UTF-8");
		PoiReportForm ppf = gson.fromJson(jsonString, PoiReportForm.class);
		
		// 查询条件
		String jsonString1 = request.getParameter("jsonStr1");
		logger.info("jsonString1="+jsonString1);
		jsonString1 = new String(jsonString1.getBytes("ISO8859-1"), "UTF-8");
		
		Class exportClass = Class.forName(ppf.getJavaClassNameLocal());
		if (exportClass == null) {
			throw new Exception("The" + ppf.getClassname() + " is not exist");
		}
			
		WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
		ConsoleService consoleService = (ConsoleService) wac.getBean(ppf.getFileCrtServiceName());
		
		String filename = consoleService.doForCreatePoiFile(jsonString, jsonString1);
		
		//先删除一下原来固有的文件
		File file = new File(propertyConfigure.getDownloadPagePath()+File.separator);
		file.delete();
		
		FileTransService fileTransService = (FileTransService) wac.getBean(ppf.getFileTransServiceName());
		//开始传输文件
		FileUtil.getInstant().CreateFile(propertyConfigure.getDownloadPagePath(), filename);
		
		FileTransContent fileTransContent0013 = new FileTransContent();
		fileTransContent0013.setMessageType(FileTransContent.FILETRANS_MESSAGETYPE0013);
		fileTransContent0013.setMessageVer(FileTransContent.FILETRANS_MESSAGEVER01);
		fileTransContent0013.setRequestType(FileTransContent.FILETRANS_REQUESTTYPE01);
		fileTransContent0013.setRequestId("0");
		fileTransContent0013.setFileName(filename);
		fileTransContent0013.setResponseCode("00");
		fileTransContent0013.setRecvFileSize("0");
		
		long recvSize = 0L;
		FileTransContent fileTransContent0014 = null;
		file = new File(propertyConfigure.getDownloadPagePath()+File.separator+filename);
		RandomAccessFile dos=new RandomAccessFile(file,"rw");
		dos.seek(recvSize);
		String sRet = "";
		String Lstflag="07";
		do{
			sRet = fileTransService.downloadFile(gson.toJson(fileTransContent0013));
			fileTransContent0014 = gson.fromJson(sRet, new TypeToken<FileTransContent>(){}.getType());
			if(!fileTransContent0014.getResponseCode().equals(FileTransContent.FILETRANS_RESPONSE00)&&!fileTransContent0014.getResponseCode().equals(FileTransContent.FILETRANS_RESPONSE07)){
				logger.debug("EXCEPTION HAPPEN:fileTransContent0014.getResponseCode():"+fileTransContent0014.getResponseCode());
				continue;
			}
			Lstflag = fileTransContent0014.getResponseCode();
			
			
			dos.write(StringUtil.hexStringToBytes(fileTransContent0014.getData()));
			recvSize += fileTransContent0014.getData().length()/2;
			
			fileTransContent0013.setRecvFileSize(recvSize+"");
		}while("07".equals(Lstflag));
		
		try{
			dos.close();
			
			//最后发送0015报文通知后台文件接受成功了
			FileTransContent fileTransContent0015 = new FileTransContent();
			fileTransContent0015.setMessageType(FileTransContent.FILETRANS_MESSAGETYPE0015);
			fileTransContent0015.setMessageVer(FileTransContent.FILETRANS_MESSAGEVER01);
			fileTransContent0015.setRequestType(FileTransContent.FILETRANS_REQUESTTYPE01);
			fileTransContent0015.setRequestId("0");
			fileTransContent0015.setFileName(filename);
			fileTransContent0015.setResponseCode("00");
			fileTransService.downloadFile(gson.toJson(fileTransContent0015));
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			//FileUtil.MoveFile(propertyInfo.getDownloadtemp(), propertyInfo.getDownloadrecv(), fileName);
		}
		
		InputStream in = new FileInputStream(propertyConfigure.getDownloadPagePath()+File.separator+filename);
		
		filename = ReportUtil.encodeFilename(filename, request);
		// response.setContentType("application/vnd.ms-excel");
		response.setContentType("application/vnd.ms-excel; charset=utf-8");
		response.setHeader("Content-disposition", "attachment;filename="
				+ filename);
		
		OutputStream os = response.getOutputStream();
		byte[] b = new byte[1024];
		int size = 0;
		while ((size = in.read(b, 0, b.length))>0) {
			os.write(b, 0, size);
		}
//		OutputStream ouputStream = response.getOutputStream();
		//wb.write(ouputStream);
		try {
			os.flush();
			os.close();
			in.close();
		} catch (Exception e) {
			 
		}finally{
			
		}

		return null;
		
	}
}
