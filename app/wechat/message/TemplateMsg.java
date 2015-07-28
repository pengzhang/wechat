/**
 * 对应接口文档地址
 * http://mp.weixin.qq.com/wiki/17/304c1885ea66dbedf7dc170d84999a9d.html
 */
package wechat.message;


import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import play.Logger;
import utils.HttpUtils;
import wechat.message.template.TemplateMsgBody;
import wechat.message.template.TemplateMsgData;
import wechat.token.TokenProxy;

/**
 * 模板消息接口
 * @author ChengNing
 * @date   2014年12月24日
 */
public class TemplateMsg {
	
	
	//设置所属行业接口地址
	public static final String SET_INDUSTRY_URL = "https://api.weixin.qq.com/cgi-bin/template/api_set_industry?access_token=";
	//添加模板id接口地址
	public static final String GET_TEMPLATE_ID_URL = "https://api.weixin.qq.com/cgi-bin/template/api_add_template?access_token=";
	//发送模板消息接口地址
	public static final String SEND_MSG_URL = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=";
	
	private String accessToken;
	
	public TemplateMsg(){
		this.accessToken = TokenProxy.accessToken();
	}
	
	/**
	 * 设置所属行业
	 * 接口说明中没有给出
	 */
	public void setIndustry(String...industrys){
		String url = SET_INDUSTRY_URL + this.accessToken;
		JsonObject json = new JsonObject();
		for(int i=0;i<industrys.length;i++){
			json.addProperty("industry_id" + i, industrys[i]);
		}
		String data = json.toString();
		HttpUtils.post(url, data);
	}

	/**
	 * 获得模板ID
	 * @param templateIdShort    template_id_short
	 * @return                   template_id
	 */
	public String getTemplateId(String templateIdShort){
		Logger.info("get template id,short template id is:" + templateIdShort);
		//构造请求数据
		String url = GET_TEMPLATE_ID_URL + this.accessToken;
		JsonObject json = new JsonObject();
		json.addProperty("template_id_short", templateIdShort);
		String data = json.toString();
		String result = HttpUtils.post(url, data);
		Logger.info("post result:" + result);
		//解析请求数据
		JsonObject resultJson = new JsonParser().parse(result).getAsJsonObject();
		if(resultJson.get("errcode").getAsString().equals("0"))
			return resultJson.get("template_id").getAsString();
		Logger.error("get template id error:" + resultJson.get("errmsg").getAsString());
		return null;
	}
	
	/**
	 * 发送模板消息
	 */
	public String send(TemplateMsgBody postData){
		Logger.info("send template message");
		//构造请求数据
		String url = SEND_MSG_URL + this.accessToken;
		JsonObject json = new JsonObject();
		json.addProperty("touser", postData.getTouser());
		json.addProperty("template_id", postData.getTemplateId());
		json.addProperty("url", postData.getUrl());
		json.addProperty("topcolor", postData.getTopcolor());
		JsonObject jsonData = new JsonObject();
		for (TemplateMsgData data : postData.getData()) {
			JsonObject keynote = new JsonObject();
			keynote.addProperty("value", data.getValue());
			keynote.addProperty("color", data.getColor());
			jsonData.add(data.getName(), keynote);
		}
		json.add("data", jsonData);
		
		String data = json.toString();
		String result = HttpUtils.post(url, data);
		Logger.info("post result:" + result);
		//解析请求数据
		
		JsonObject resultJson = new JsonParser().parse(result).getAsJsonObject();
		if(resultJson.get("errcode").getAsString().equals("0"))
			return resultJson.get("msgid").getAsString();
		Logger.error("send template message error:" + resultJson.get("errmsg").getAsString());
		return null;
	}
	
	
}
