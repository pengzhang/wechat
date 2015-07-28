package wechat.util;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import wechat.exception.WeChatException;
import wechat.exception.WeChatReturnCode;
/**
 * 工具类
 * @author Zhangxs
 * @version 2015-7-4
 */
public class WeChatUtil {
	/**
	 * 判断是否请求成功
	 * @param resultStr
	 * @throws WeChatException
	 */
	public static void isSuccess(String resultStr) throws WeChatException{		
		JsonElement jsonElement = new JsonParser().parse(resultStr);
		JsonObject jsonObject = jsonElement.getAsJsonObject();
		Integer errCode =jsonObject.get("errcode").getAsInt();
		if (errCode!=null && errCode!=0) {
			String errMsg = WeChatReturnCode.getMsg(errCode);
			if (errMsg.equals("")) {
				errMsg = jsonObject.get("errmsg").getAsString();
			}
			throw new WeChatException("异常码:"+errCode+";异常说明:"+errMsg);
		}
	}
}
