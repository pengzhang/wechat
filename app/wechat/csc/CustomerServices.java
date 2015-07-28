package wechat.csc;

import com.google.gson.annotations.SerializedName;

/**
 * 客服基本信息
 * @author Zhangxs
 * @date 2015-7-8 
 * @version
 */
public class CustomerServices {
	@SerializedName("kf_account")
	private String kfAccount;//完整客服账号，格式为：账号前缀@公众号微信号
	@SerializedName("kf_id")
	private String kfId;//	客服工号
	@SerializedName("kf_headimgurl")
	private String kfHeadimgurl;//客服头像url
	@SerializedName("kf_nick")
	private String kfNick;//	客服昵称
	@SerializedName("status")
	private Integer status;//	客服在线状态
	@SerializedName("auto_accept")
	private Integer autoAccept;//	客服设置的最大自动接入数
	@SerializedName("accepted_case")
	private Integer acceptedCase;//	客服当前正在接待的会话数
	
	/**
	 * 完整客服账号<br/>
	 * 格式为：账号前缀@公众号微信号
	 * @return
	 */
	
	public String getKfAccount() {
		return kfAccount;
	}
	public void setKfAccount(String kfAccount) {
		this.kfAccount = kfAccount;
	}
	/**
	 * 客服工号
	 * @return
	 */
	public String getKfId() {
		return kfId;
	}
	public void setKfId(String kfId) {
		this.kfId = kfId;
	}
	/**
	 * 客服头像url
	 * @return
	 */
	public String getKfHeadimgurl() {
		return kfHeadimgurl;
	}
	public void setKfHeadimgurl(String kfHeadimgurl) {
		this.kfHeadimgurl = kfHeadimgurl;
	}
	/**
	 * 客服昵称
	 * @return
	 */
	public String getKfNick() {
		return kfNick;
	}
	public void setKfNick(String kfNick) {
		this.kfNick = kfNick;
	}
	/**
	 * 客服在线状态</br>
	 *  1:pc在线</br>
	 *  2:手机在线</br>
	 *  3:pc和手机同时在线
	 * @return
	 */
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * 客服设置的最大自动接入数
	 * @return
	 */
	public Integer getAutoAccept() {
		return autoAccept;
	}
	public void setAutoAccept(Integer autoAccept) {
		this.autoAccept = autoAccept;
	}
	/**
	 * 客服当前正在接待的会话数
	 * @return
	 */
	public Integer getAcceptedCase() {
		return acceptedCase;
	}
	public void setAcceptedCase(Integer acceptedCase) {
		this.acceptedCase = acceptedCase;
	}
	
	
}
