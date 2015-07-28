package wechat.menu;




import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import play.Logger;
import utils.HttpUtils;
import wechat.exception.WeChatException;
import wechat.token.TokenProxy;
import wechat.util.WeChatUtil;
/**
 * 微信菜单操作
 * @author Zhangxs
 * @version 2015-7-4
 */
public class MenuManager {
	
	private static final String MENU_CREATE_POST_URL = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=";
	private static final String MENU_GET_GET_URL = "https://api.weixin.qq.com/cgi-bin/menu/get?access_token=";
	private static final String MENU_DEL_GET_URL = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=";

	private String accessToken;
	public MenuManager() {
		this.accessToken = TokenProxy.accessToken();
	}
	/**
	 * 创建菜单
	 * @throws WeChatException 
	 */
	public void create(Menu menu) throws WeChatException{
		Logger.info("创建菜单");
		String resultStr = HttpUtils.post(MENU_CREATE_POST_URL+this.accessToken, new Gson().toJson(menu));
		WeChatUtil.isSuccess(resultStr);
	}
	
	/**
	 * 查询菜单
	 */
	public Menu getMenu() {	
		Logger.info("查询菜单");
		String resultStr = HttpUtils.get(MENU_GET_GET_URL+this.accessToken);
		try {
			WeChatUtil.isSuccess(resultStr);
		} catch (WeChatException e) {
			e.printStackTrace();
			return null;
		}
		JsonObject menuObject = new JsonParser().parse(resultStr).getAsJsonObject();
		Menu menu = new Gson().fromJson(menuObject.get("menu").getAsJsonObject(), Menu.class);
		return menu;
	}
	/**
	 * 删除菜单
	 * @throws WeChatException 
	 */
	public void delete() throws WeChatException{
		Logger.info("删除菜单");
		String resultStr = HttpUtils.get(MENU_DEL_GET_URL+this.accessToken);
		WeChatUtil.isSuccess(resultStr);
	}
	
}
