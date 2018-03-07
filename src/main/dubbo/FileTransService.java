package jieyi.app.dubbo;

public interface FileTransService {
	public String uploadFile(String uploadContent) throws Exception;
	
	public String downloadFile(String downloadContent) throws Exception;
}
