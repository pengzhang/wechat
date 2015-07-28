package wechat.csc;

import com.google.gson.annotations.SerializedName;

/**
 * 会话状态
 * @author Zhangxs
 * @date 2015-7-8 
 * @version
 */
public class CustomerServicesSession {
	@SerializedName("createtime")
	private int createTime;//会话接入的时间
	@SerializedName("kf_account")
    private String kfAccount;//客服
	@SerializedName("openid")
    private String openId;//客户openid
	/**
	 * 会话接入的时间
	 * @return
	 */
	public int getCreateTime() {
		return createTime;
	}
	public void setCreateTime(int createTime) {
		this.createTime = createTime;
	}
	/**
	 * 客服
	 * @return
	 */
	public String getKfAccount() {
		return kfAccount;
	}
	public void setKfAccount(String kfAccount) {
		this.kfAccount = kfAccount;
	}
	/**
	 * 客户openid
	 * @return
	 */
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	
     
     

}
