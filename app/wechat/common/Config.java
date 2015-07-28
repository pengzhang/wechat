/**
 * 
 */
package wechat.common;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import play.Logger;
import play.Play;
import play.libs.IO;
import play.vfs.VirtualFile;


/**
 * @author ChengNing
 * @date   2014年12月8日
 */
public class Config {
	
	private static final String configFile = "wechat4j.properties";
	
	private String url;
	private String token;
	private String encodingAESKey;
	private String appid;
	private String appSecret;
	private String accessTokenServer;
	private String jsApiTicketServer;
	private static Config config = new Config();
	
	private Config(){
		Properties p = new Properties();
		VirtualFile appRoot = VirtualFile.open(Play.applicationPath);
		VirtualFile conf = appRoot.child("conf/" + configFile);
		p = IO.readUtf8Properties(conf.inputstream());
		this.url = p.getProperty("wechat.url").trim();
		this.encodingAESKey = p.getProperty("wechat.encodingaeskey").trim();
		this.token = p.getProperty("wechat.token").trim();
		this.appid = p.getProperty("wechat.appid").trim();
		this.appSecret = p.getProperty("wechat.appsecret").trim();
		this.accessTokenServer = p.getProperty("wechat.accessToken.server.class").trim();
		this.jsApiTicketServer = p.getProperty("wechat.ticket.jsapi.server.class").trim();
		Logger.info("load wechat4j.properties success");
	}
	
	public static Config instance(){
		return config;
	}
	public String getToken() {
		return token;
	}
	public String getAppid() {
		return appid;
	}
	public String getAppSecret() {
		return appSecret;
	}

	public String getUrl() {
		return url;
	}

	public String getEncodingAESKey() {
		return encodingAESKey;
	}
	
	public String getAccessTokenServer(){
		return accessTokenServer;
	}

	public String getJsApiTicketServer() {
		return jsApiTicketServer;
	}
	
	
}
