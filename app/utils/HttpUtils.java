/**
 * 
 */
package utils;



import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;

import play.Logger;
import play.libs.WS;
import play.libs.WS.HttpResponse;


/**
 * 
 * @author zhangpeng
 * @date 2014年12月12日
 */
public class HttpUtils {

	public static final int timeout = 10;

	/**
	 * post 请求
	 * 
	 * @param url
	 * @return
	 */
	public static String post(String url) {
		return post(url, "");
	}
	
	/**
	 * post请求
	 * @param url
	 * @param data
	 * @return
	 */
	public static String post(String url, String data){
		return httpPost(url, data);
	}
	
	/**
	 * get请求
	 * @param url
	 * @return
	 */
	public static String get(String url){
		return httpGet(url);
	}

	/**
	 * post 请求
	 * 
	 * @param url
	 * @param data
	 * @return
	 */
	public static String httpPost(String url, String data) {
		try {
			Map<String,String> headers = new HashMap<String,String>();
			headers.put("ContentType", "text/html");
			HttpResponse entity = WS.url(url).headers(headers).body(data).post();
			return entity != null ? entity.getString() : null;
		} catch (Exception e) {
			Logger.error("post请求异常，" + e.getMessage() + "\n post url:" + url);
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 上传文件
	 * @param url    URL
	 * @param file   需要上传的文件
	 * @return
	 */
	public static String postFile(String url,File file){
		return postFile(url, null, file);
	}
	
	/**
	 * 上传文件
	 * @param url    URL
	 * @param name   文件的post参数名称
	 * @param file   上传的文件
	 * @return
	 */
	public static String postFile(String url,String name,File file){
		try {
			
			HttpResponse entity = WS.url(url).files(file).post();
			return entity != null ? entity.getString() : null;
			
		} catch (Exception e) {
			Logger.error("postFile请求异常，" + e.getMessage() + "\n post url:" + url);
			e.printStackTrace();
		}
		return null;
	}
	
	
	/**
	 * 下载文件
	 * @param url   URL
	 * @return      文件的二进制流，客户端使用outputStream输出为文件
	 */
	public static byte[] getFile(String url){
		try {
			HttpResponse entity = WS.url(url).get();
			return IOUtils.toByteArray(entity.getStream());
			
		} catch (Exception e) {
			Logger.error("postFile请求异常，" + e.getMessage() + "\n post url:" + url);
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 发送get请求
	 * 
	 * @param url
	 * @return
	 */
	private static String httpGet(String url) {
		try {
			HttpResponse entity = WS.url(url).get();
			return entity != null ? entity.getString() : null;
		} catch (Exception e) {
			Logger.error("get请求异常，" + e.getMessage() + "\n get url:" + url);
			e.printStackTrace();
		}
		return null;
	}
}
