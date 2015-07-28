/**
 * http请求方式: POST
 * https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=ACCESS_TOKEN
 */
package wechat.message;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import play.Logger;
import utils.HttpUtils;
import wechat.event.MsgType;
import wechat.response.ArticleResponse;
import wechat.response.MusicResponse;
import wechat.response.VideoResponse;
import wechat.token.TokenProxy;

/**
 * 发送客服消息
 * @author ChengNing
 * @date   2014年12月11日
 */
public class CustomerMsg {
	
	private static final String MSG_URL = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=";
	
//	private String accessToken;
	private String toUserOpenId;
	private String msgType;   //msgtype
	private String msgBody;   //发送的消息post数据
	
	
	/**
	 * 需要主动去刷新access_token,不建议使用
	 * 建议自己去获取access_token保存，并定时刷新。
	 * 然后使用SendMsg(String toUserOpenId,String accessToken)来替代本方法
	 * @param toUserOpenId
	 */
	public CustomerMsg(String toUserOpenId){
		this.toUserOpenId = toUserOpenId;
	}
	
//	
//	public String getMsgBody() {
//		return msgBody;
//	}

	/**
	 * 发送客服消息
	 * @param msgBody
	 */
	private void send(){
		String accessToken = TokenProxy.accessToken();
		if(StringUtils.isBlank(this.toUserOpenId))
			return;
		//token不存在则重新刷新token
		if(StringUtils.isBlank(accessToken)){
			Logger.error("发送失败，无法得到accessToken");
			return;
		}
		//需要判断一下，防止上面刷新token失败
		if(StringUtils.isNotBlank(accessToken)){
			String url = MSG_URL + accessToken;
			HttpUtils.post(url, msgBody);
		}
	}

	/**
	 *  {
    "touser":"OPENID",
    "msgtype":"text",
    "text":
    {
         "content":"Hello World"
    }
}
	 * @param content
	 */
	public void sendText(String content){
		this.msgType = MsgType.text.name();
		
		JsonObject jsonMsg = new JsonObject();
		jsonMsg.addProperty("content", content);
		
		JsonObject json = new JsonObject();
		json.addProperty("touser", this.toUserOpenId);
		json.addProperty("msgtype", this.msgType);
		json.add("text", jsonMsg);
		
		this.msgBody = json.toString();
		send();
	}
	
	/**
	 * 发送图片消息
	 * {
    "touser":"OPENID",
    "msgtype":"image",
    "image":
    {
      "media_id":"MEDIA_ID"
    }
}
	 * @param mediaId
	 */
	public void sendImage(String mediaId){
		this.msgType = MsgType.image.name();

		JsonObject jsonMsg = new JsonObject();
		jsonMsg.addProperty("media_id", mediaId);
		
		JsonObject json = new JsonObject();
		json.addProperty("touser", this.toUserOpenId);
		json.addProperty("msgtype", this.msgType);
		json.add("image", jsonMsg);

		this.msgBody =  json.toString();
		
		send();
	}
	
	/**
	 * 发送语音消息
	 * 
	 * {
    "touser":"OPENID",
    "msgtype":"voice",
    "voice":
    {
      "media_id":"MEDIA_ID"
    }
}
	 */
	public void sendVoice(String mediaId){
		this.msgType = MsgType.voice.name();
		
		JsonObject jsonMsg = new JsonObject();
		jsonMsg.addProperty("media_id", mediaId);
		
		JsonObject json = new JsonObject();
		json.addProperty("touser", this.toUserOpenId);
		json.addProperty("msgtype", this.msgType);
		json.add("voice", jsonMsg);
		
		this.msgBody = json.toString();
		send();
	}
	
	/**
	 * 发送视频消息
	 * 
	 * 
	 * @param title
	 * @param description
	 * @param mediaId
	 * @param thumbMediaId
	 */
	public void sendVideo(String title,String description,String mediaId,String thumbMediaId){
		VideoResponse video = new VideoResponse();
		video.setTitle(title);
		video.setDescription(description);
		video.setMediaId(thumbMediaId);
		video.setThumbMediaId(thumbMediaId);
		sendVideo(video);
	}
	
	/**
	 * 发送视频消息
	 * {
    "touser":"OPENID",
    "msgtype":"video",
    "video":
    {
      "media_id":"MEDIA_ID",
      "thumb_media_id":"MEDIA_ID",
      "title":"TITLE",
      "description":"DESCRIPTION"
    }
}
	 * @param video
	 */
	public void sendVideo(VideoResponse video){
		this.msgType = MsgType.video.name();
		
		JsonObject jsonMsg = new JsonObject();
		jsonMsg.addProperty("media_id", video.getMediaId());
		jsonMsg.addProperty("thumb_media_id", video.getThumbMediaId());
		jsonMsg.addProperty("title", video.getTitle());
		jsonMsg.addProperty("description", video.getDescription());
		
		JsonObject json = new JsonObject();
		json.addProperty("touser", this.toUserOpenId);
		json.addProperty("msgtype", this.msgType);
		json.add("video", jsonMsg);
		
		this.msgBody = json.toString();
		send();
	}
	
	/**
	 * 发送音乐消息
	 * @param title
	 * @param description
	 * @param musicURL
	 * @param hQMusicUrl
	 * @param thumbMediaId
	 */
	public void sendMusic(String title,String description,String musicURL,String hQMusicUrl,String thumbMediaId){
		MusicResponse music = new MusicResponse();
		music.setTitle(title);
		music.setDescription(description);
		music.setMusicURL(musicURL);
		music.setHQMusicUrl(hQMusicUrl);
		music.setThumbMediaId(thumbMediaId);
		sendMusic(music);
	}
	
	/**
	 * 发送音乐消息
	 * {
    "touser":"OPENID",
    "msgtype":"music",
    "music":
    {
      "title":"MUSIC_TITLE",
      "description":"MUSIC_DESCRIPTION",
      "musicurl":"MUSIC_URL",
      "hqmusicurl":"HQ_MUSIC_URL",
      "thumb_media_id":"THUMB_MEDIA_ID" 
    }
}
	 * @param music  音乐消息
	 */
	public void sendMusic(MusicResponse music){
		this.msgType = MsgType.music.name();
		
		JsonObject jsonMsg = new JsonObject();
		jsonMsg.addProperty("title", music.getTitle());
		jsonMsg.addProperty("description", music.getDescription());
		jsonMsg.addProperty("musicurl", music.getMusicURL());
		jsonMsg.addProperty("hqmusicurl", music.getHQMusicUrl());
		jsonMsg.addProperty("thumb_media_id", music.getThumbMediaId());
		
		JsonObject json = new JsonObject();
		json.addProperty("touser", this.toUserOpenId);
		json.addProperty("msgtype", this.msgType);
		json.add("music", jsonMsg);
		
		this.msgBody = json.toString();
		send();
	}
	
	/**
	 * 发送图文消息，单条图文消息
	 * @param Title         图文消息标题
	 * @param Description   图文消息描述
	 * @param PicUrl        图片链接，支持JPG、PNG格式，较好的效果为大图360*200，小图200*200
	 * @param Url           点击图文消息跳转链接
	 */
	public void sendNew(String title,String description,String picUrl,String url){
		ArticleResponse item = new ArticleResponse();
		item.setTitle(title);
		item.setDescription(description);
		item.setPicUrl(picUrl);
		item.setUrl(url);
		sendNews(item);
	}
	
	/**
	 * 发送图文消息，单条图文消息
	 * @param item
	 */
	public void sendNews(ArticleResponse item){
		List<ArticleResponse> items = new ArrayList<ArticleResponse>();
		items.add(item);
		sendNews(items);
	}
	
	/**
	 * 发送图文消息
	 * {
    "touser":"OPENID",
    "msgtype":"news",
    "news":{
        "articles": [
         {
             "title":"Happy Day",
             "description":"Is Really A Happy Day",
             "url":"URL",
             "picurl":"PIC_URL"
         },
         {
             "title":"Happy Day",
             "description":"Is Really A Happy Day",
             "url":"URL",
             "picurl":"PIC_URL"
         }
         ]
    }
}
	 * @param items
	 */
	public void sendNews(List<ArticleResponse> items){
		this.msgType = MsgType.news.name();
		JsonArray jsonArray = new JsonArray();
		for (ArticleResponse item : items) {
			JsonObject jsonItem = new JsonObject();
			jsonItem.addProperty("title", item.getTitle());
			jsonItem.addProperty("description", item.getDescription());
			jsonItem.addProperty("url", item.getUrl());
			jsonItem.addProperty("picurl", item.getPicUrl());

			jsonArray.add(jsonItem);
		}
		
		JsonObject jsonMsg = new JsonObject();
		jsonMsg.add("articles", jsonArray);
		
		JsonObject json = new JsonObject();
		json.addProperty("touser", this.toUserOpenId);
		json.addProperty("msgtype", this.msgType);
		json.add("news", jsonMsg);
		
		this.msgBody = json.toString();
		send();
		
	}
	
}
