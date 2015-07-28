/**
 * 
 */
package wechat.user;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URLEncoder;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import play.Logger;
import utils.HttpUtils;
import wechat.exception.WeChatException;
import wechat.token.TokenProxy;
import wechat.util.WeChatUtil;

/**
 * 账户管理
 * @author Zhangxs
 * @version 2015-7-5
 */
public class AccountManager {
	
	//长链接转短链接接口
	private static final String SHORTURL_POST_URL="https://api.weixin.qq.com/cgi-bin/shorturl?access_token=";
	//生成带参数的二维码
	private static final String QRCODE_POST_URL = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=";
	//通过ticket换取二维码
	private static final String SHOWQRCODE_POST_URL = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=";
	private String accessToken;
	public AccountManager(){
		this.accessToken = TokenProxy.accessToken();
	}
	/**
	 * 长链接转短链接接口
	 * @param longUrl 需要转换的长链接
	 * @return
	 */
	public String shortUrl(String longUrl){
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("action", "long2short");
		jsonObject.addProperty("long_url", longUrl);
		String requestData = jsonObject.toString();
		Logger.info("request data "+requestData);
		String resultStr = HttpUtils.post(SHORTURL_POST_URL + this.accessToken, requestData);
		Logger.info("return data "+resultStr);
		try {
			WeChatUtil.isSuccess(resultStr);
		} catch (WeChatException e) {
			Logger.error(e.getMessage());
			e.printStackTrace();
			return null;
		}
		JsonObject resultJson=new JsonParser().parse(resultStr).getAsJsonObject();
		return resultJson.get("short_url").getAsString();
	}
	/**
	 * 创建永久二维码
	 * @param scene_id 场景值ID,目前参数只支持1--100000
	 * @return
	 */
	public Qrcode createQrcodePerpetual(long sceneId){
		return createQrcodeTicket(QrcodeType.QR_LIMIT_SCENE, null, sceneId,null);
	}
	/**
	 * 创建永久二维码
	 * @param scene_str 场景值ID,长度限制为1到64
	 * @return
	 */
	public Qrcode createQrcodePerpetualstr(String sceneStr){
		return createQrcodeTicket(QrcodeType.QR_LIMIT_STR_SCENE, null, null,sceneStr);
	}
	/**
	 * 创建临时二维码
	 * @param scene_id 场景值ID
	 * @param expire_seconds 二维码有效时间，以秒为单位,最大不超过604800（即7天）。
	 * @return
	 */
	public Qrcode createQrcodeTemporary(long sceneId,int expireSeconds){
		return createQrcodeTicket(QrcodeType.QR_SCENE, expireSeconds, sceneId,null);
	}
	private Qrcode createQrcodeTicket(QrcodeType qrcodeType,Integer expireSeconds,Long sceneId,String sceneStr){
		JsonObject ticketJson = new JsonObject();
		ticketJson.add("action_name", new JsonParser().parse(new Gson().toJson(qrcodeType)));
		JsonObject sceneJson = new JsonObject();
		switch (qrcodeType) {
			case QR_SCENE:
				ticketJson.addProperty("expire_seconds", expireSeconds);
				sceneJson.addProperty("scene_id", sceneId);
				break;
			case QR_LIMIT_SCENE:
				sceneJson.addProperty("scene_id", sceneId);
				break;
			case QR_LIMIT_STR_SCENE:
				sceneJson.addProperty("scene_str", sceneStr);
				break;
		}
		JsonObject actionInfoJson = new JsonObject();
		actionInfoJson.add("scene", sceneJson);
		ticketJson.add("action_info", actionInfoJson);
		String requestData = ticketJson.toString();
		Logger.info("request data "+requestData);
		String resultStr = HttpUtils.post(QRCODE_POST_URL + this.accessToken, requestData);
		Logger.info("return data "+resultStr);
		try {
			WeChatUtil.isSuccess(resultStr);
		} catch (WeChatException e) {
			Logger.error(e.getMessage());
			e.printStackTrace();
			return null;
		}
		Qrcode qrcode = new Gson().fromJson(resultStr,Qrcode.class);
		return qrcode;
	}
	/**
	 * 换取二维码
	 * @param ticket
	 * @param qrcodeFile 二维码存储路径
	 */
	public static void getQrcode(String ticket,String qrcodeFile){
		try {
			byte[] b = HttpUtils.getFile(SHOWQRCODE_POST_URL+URLEncoder.encode(ticket, "UTF-8"));
			File file = new File(qrcodeFile);
			FileOutputStream fStream = new FileOutputStream(file);
			fStream.write(b);
			fStream.flush();
			fStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
