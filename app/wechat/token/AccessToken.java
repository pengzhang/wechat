/**
 * 
 */
package wechat.token;



import play.Logger;
import wechat.common.Config;


/**
 * Access token实体模型
 * @author ChengNing
 * @date   2014年12月12日
 */
public class AccessToken extends Token {
	
	private static final String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential";
	

	@Override
	protected String tokenName() {
		return "access_token";
	}

	@Override
	protected String expiresInName() {
		return "expires_in";
	}

	/**
	 * 组织accesstoken的请求utl
	 */
	@Override
	protected String accessTokenUrl() {
		String appid = Config.instance().getAppid();
		String appsecret = Config.instance().getAppSecret();
		String url = ACCESS_TOKEN_URL + "&appid=" + appid + "&secret=" + appsecret;
		Logger.info("创建获取access_token url");
		return url;
	}
	
	
}
