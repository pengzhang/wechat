
package wechat.csc;

import java.io.File;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import play.Logger;
import utils.HttpUtils;
import wechat.exception.WeChatException;
import wechat.token.TokenProxy;
import wechat.util.WeChatUtil;
/**
 * 客服管理
 * @author Zhangxs
 * @date 2015-7-7
 * @version
 */
public class CustomerServicesManager {
	
	/* 创建会话*/
	private static final String CUSTOMSERVICE_KFSESSION_CREATE_POST_URL = "https://api.weixin.qq.com/customservice/kfsession/create?access_token=";
	/* 关闭会话*/
	private static final String CUSTOMSERVICE_KFSESSION_CLOSE_POST_URL = "https://api.weixin.qq.com/customservice/kfsession/close?access_token=";
	/* 获取客户的会话状态*/
	private static final String CUSTOMSERVICE_KFSESSION_GETSESSION_GET_URL = "https://api.weixin.qq.com/customservice/kfsession/getsession?access_token=";
	/* 获取客服的会话列表*/
	private static final String CUSTOMSERVICE_KFSESSION_GETSESSIONLIST_GET_URL = "https://api.weixin.qq.com/customservice/kfsession/getsessionlist?access_token=";
	/* 获取未接入会话列表*/
	private static final String CUSTOMSERVICE_KFSESSION_GETWAITCASE_GET_URL = "https://api.weixin.qq.com/customservice/kfsession/getwaitcase?access_token=";
	/* 获取客服基本信息*/
	private static final String CUSTOMSERVICE_GETKFLIST_GET_URL = "https://api.weixin.qq.com/cgi-bin/customservice/getkflist?access_token=";
	/* 获取在线客服接待信息*/
	private static final String CUSTOMSERVICE_GETONLIEKFLIST_GET_URL = "https://api.weixin.qq.com/cgi-bin/customservice/getonlinekflist?access_token=";
	/* 添加客服账号*/
	private static final String CUSTOMSERVICE_KFACCOUNT_ADD_POST_URL = "https://api.weixin.qq.com/customservice/kfaccount/add?access_token=";
	/* 设置客服信息*/
	private static final String CUSTOMSERVICE_KFACCOUNT_UPDATE_POST_URL = "https://api.weixin.qq.com/customservice/kfaccount/update?access_token=";
	/* 上传客服头像*/
	private static final String CUSTOMSERVICE_KFACCOUNT_UPLOADHEADIMG_POST_URL = "http://api.weixin.qq.com/customservice/kfaccount/uploadheadimg?access_token=";
	/* 删除客服账号*/
	private static final String CUSTOMSERVICE_KFACCOUNT_DEL_POST_URL = "https://api.weixin.qq.com/customservice/kfaccount/del?access_token=";
	// 获取客服聊天记录接口
	private static final String CUSTOMSERVICE_MSGRECORD_GETRECORD_POST_URL="https://api.weixin.qq.com/customservice/msgrecord/getrecord?access_token=";
	private static final String PARAM_FILE = "media";
	private String accessToken;
	public CustomerServicesManager() {
		this.accessToken = TokenProxy.accessToken();
	}
	/**
	 * 创建会话
	 * @param openid 客户openid
	 * @param kf_account 完整客服账号，格式为：账号前缀@公众号微信号
	 * @return
	 * @throws WeChatException 
	 */
	public void kfSessionCreate(String openId,String kfAccount) throws WeChatException{
		kfSessionCreate(openId, kfAccount, null);
	}
	/**
	 * 创建会话
	 * @param openid 客户openid
	 * @param kf_account 完整客服账号，格式为：账号前缀@公众号微信号
	 * @param text 附加信息，文本会展示在客服人员的多客服客户端
	 * @return
	 * @throws WeChatException 
	 */
	public void kfSessionCreate(String openId,String kfAccount,String text) throws WeChatException{
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("openid", openId);
		jsonObject.addProperty("kf_account", kfAccount);
		if (text!=null){
			jsonObject.addProperty("text", text);			
		}
		String resultStr = HttpUtils.post(CUSTOMSERVICE_KFSESSION_CREATE_POST_URL+this.accessToken,jsonObject.toString());
		WeChatUtil.isSuccess(resultStr);
	}
	/**
	 * 关闭会话
	 * @param openid 客户openid
	 * @param kf_account 完整客服账号，格式为：账号前缀@公众号微信号
	 * @return
	 * @throws WeChatException 
	 */
	public void kfSessionClose(String openId,String kfAccount) throws WeChatException{
		kfSessionClose(openId, kfAccount, null);
	}
	/**
	 * 关闭会话
	 * @param openid 客户openid
	 * @param kf_account 完整客服账号，格式为：账号前缀@公众号微信号
	 * @param text 完整客服账号，格式为：账号前缀@公众号微信号
	 * @return
	 * @throws WeChatException 
	 */
	public void kfSessionClose(String openId,String kfAccount,String text) throws WeChatException{
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("openid", openId);
		jsonObject.addProperty("kf_account", kfAccount);
		if (text!=null){
			jsonObject.addProperty("text", text);			
		}
		String resultStr = HttpUtils.post(CUSTOMSERVICE_KFSESSION_CLOSE_POST_URL+this.accessToken,jsonObject.toString());
		WeChatUtil.isSuccess(resultStr);
	}
	/**
	 * 获取客户的会话状态 
	 * @param openId
	 * @return
	 */
	public CustomerServicesSession getSession(String openId) {
		String resultStr = HttpUtils.get(CUSTOMSERVICE_KFSESSION_GETSESSION_GET_URL+this.accessToken+"&openid="+openId);
		try {
			WeChatUtil.isSuccess(resultStr);
		} catch (WeChatException e) {
			Logger.error(e.getMessage());
			e.printStackTrace();
			return null;
		}
		CustomerServicesSession customerServicesSession = new Gson().fromJson(resultStr, CustomerServicesSession.class);
		return customerServicesSession;
	}
	/**
	 * 获取客服的会话列表
	 * @param KfAccount
	 * @return
	 */
	public List<CustomerServicesSession> getSessionList(String kfAccount) {
		String resultStr = HttpUtils.get(CUSTOMSERVICE_KFSESSION_GETSESSIONLIST_GET_URL+this.accessToken+"&kf_account="+kfAccount);
		try {
			WeChatUtil.isSuccess(resultStr);
		} catch (WeChatException e) {
			Logger.error(e.getMessage());
			e.printStackTrace();
			return null;
		}
		String sessionlist = new JsonParser().parse(resultStr).getAsJsonObject().get("sessionlist").getAsString();
		List<CustomerServicesSession> customerServicesSessions = new Gson().fromJson(sessionlist, new TypeToken<List<CustomerServicesSession>>(){}.getType());
		return customerServicesSessions;
	}
	/**
	 * 获取未接入会话列表
	 * @return
	 */
	public List<CustomerServicesSession> getWaitCaseList() {
		String resultStr = HttpUtils.get(CUSTOMSERVICE_KFSESSION_GETWAITCASE_GET_URL+this.accessToken);
		try {
			WeChatUtil.isSuccess(resultStr);
		} catch (WeChatException e) {
			Logger.error(e.getMessage());
			e.printStackTrace();
			return null;
		}
		
		String waitcaselist = new JsonParser().parse(resultStr).getAsJsonObject().get("waitcaselist").getAsString();
		List<CustomerServicesSession> customerServicesSessions = new Gson().fromJson(waitcaselist, new TypeToken<List<CustomerServicesSession>>(){}.getType());
		return customerServicesSessions;
	}
	/**
	 * 获取所有客服账号	
	 * @return
	 */
	public List<CustomerServices> getKfList() {
		String resultStr = HttpUtils.get(CUSTOMSERVICE_GETKFLIST_GET_URL+this.accessToken);
		try {
			WeChatUtil.isSuccess(resultStr);
		} catch (WeChatException e) {
			Logger.error(e.getMessage());
			e.printStackTrace();
			return null;
		}
		String kf_list = new JsonParser().parse(resultStr).getAsJsonObject().get("kf_list").getAsString();
		List<CustomerServices> list = new Gson().fromJson(kf_list, new TypeToken<List<CustomerServicesSession>>(){}.getType());
		return list;
	}
	 
	/**
	 * 获取在线客服接待信息
	 * @return
	 */
	 
	public List<CustomerServices> getOnlieKfList() {
		String resultStr = HttpUtils.get(CUSTOMSERVICE_GETONLIEKFLIST_GET_URL+this.accessToken);		
		try {
			WeChatUtil.isSuccess(resultStr);
		} catch (WeChatException e) {
			Logger.error(e.getMessage());
			e.printStackTrace();
			return null;
		}
		String kf_online_list = new JsonParser().parse(resultStr).getAsJsonObject().get("kf_online_list").getAsString();
		List<CustomerServices> list = new Gson().fromJson(kf_online_list, new TypeToken<List<CustomerServicesSession>>(){}.getType());
		return list;
	}
	
	/**
	 * 添加客服账号 
	 * @param kfAccount 完整客服账号，格式为：账号前缀@公众号微信号，账号前缀最多10个字符，必须是英文或者数字字符。如果没有公众号微信号，请前往微信公众平台设置。
	 * @param nickName 客服昵称，最长6个汉字或12个英文字符
	 * @param password 客服账号登录密码，格式为密码明文的32位加密MD5值
	 * @return
	 * @throws WeChatException 
	 */
	public void kfAddAccount(String kfAccount,String nickName,String password) throws WeChatException{
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("kf_account", kfAccount);
		jsonObject.addProperty("nickname", nickName);
		jsonObject.addProperty("password", password);
		String resultStr = HttpUtils.post(CUSTOMSERVICE_KFACCOUNT_ADD_POST_URL+this.accessToken,jsonObject.toString());
		WeChatUtil.isSuccess(resultStr);
	}
	/**
	 * 设置客服信息
	 * @param kf_account 完整客服账号，格式为：账号前缀@公众号微信号
	 * @param nickname	客服昵称，最长6个汉字或12个英文字符
	 * @param password	客服账号登录密码，格式为密码明文的32位加密MD5值
	 * @return
	 * @throws WeChatException 
	 */
	public void kfUpdateAccount(String kfAccount,String nickName,String password) throws WeChatException {
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("kf_account", kfAccount);
		jsonObject.addProperty("nickname", nickName);
		jsonObject.addProperty("password", password);
		String resultStr =HttpUtils.post(CUSTOMSERVICE_KFACCOUNT_UPDATE_POST_URL+this.accessToken, jsonObject.toString());
		WeChatUtil.isSuccess(resultStr);
	}
	/**
	 * 上传客服头像
	 * 头像图片文件必须是jpg格式，推荐使用640*640大小的图片以达到最佳效果
	 * @param kfAccount 完整客服账号，格式为：账号前缀@公众号微信号
	 * @param file	客服头像
	 * @return
	 * @throws WeChatException 
	 */
	public void kfUploadHeadImg(String kfAccount,File file) throws WeChatException{
		String resultStr = HttpUtils.postFile(CUSTOMSERVICE_KFACCOUNT_UPLOADHEADIMG_POST_URL+this.accessToken+"&kf_account="+kfAccount,PARAM_FILE, file);
		WeChatUtil.isSuccess(resultStr);
	}

	/**
	 * 删除客服账号
	 * @param kfAccount	完整客服账号，格式为：账号前缀@公众号微信号
	 * @return
	 * @throws WeChatException 
	 */
	public void kfDelAccount(String kfAccount) throws WeChatException{
		String resultStr = HttpUtils.post(CUSTOMSERVICE_KFACCOUNT_DEL_POST_URL+this.accessToken+"&kf_account="+kfAccount);
		WeChatUtil.isSuccess(resultStr);
	}
	/**
	 * 获取客服聊天记录
	 * @param starttime 查询开始时间，UNIX时间戳
	 * @param endtime 查询结束时间，UNIX时间戳，每次查询不能跨日查询
	 * @param pageindex 查询第几页，从1开始
	 * @param pagesize 每页大小，每页最多拉取50条
	 * @return
	 */
	public List<Record> getRecord(long starttime,long endtime, int pageindex,int pagesize) {
		JsonObject data = new JsonObject();
		data.addProperty("endtime", endtime);
		data.addProperty("pageindex", pageindex);
		data.addProperty("pagesize", pagesize);
		data.addProperty("starttime", starttime);
		String resultStr = HttpUtils.post(CUSTOMSERVICE_MSGRECORD_GETRECORD_POST_URL+this.accessToken, data.toString());
		try {
			WeChatUtil.isSuccess(resultStr);
		} catch (WeChatException e) {
			Logger.error(e.getMessage());
			e.printStackTrace();
			return null;
		}
		String recordlist = new JsonParser().parse(resultStr).getAsJsonObject().get("recordlist").getAsString();
		List<Record> records = new Gson().fromJson(recordlist, new TypeToken<List<Record>>(){}.getType());
		return records;
	}
}
