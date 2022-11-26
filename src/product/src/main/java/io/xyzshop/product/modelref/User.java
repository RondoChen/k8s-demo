package io.xyzshop.product.modelref;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

public class User implements Serializable {
	private static final long serialVersionUID = -2395740684205874168L;

	/** =============== field =============== */
	/**
	 * 用户 Id
	 */
	private int userId;

	/**
	 * 用户 openId
	 */
	private String openId;

	/**
	 * 用户类型
	 */
	private int accountType;

	/**
	 * 用户名
	 */
	private String nickName = "";

	/**
	 * 用户等级（0,普通用户 1,高级用户 2,钻石用户）
	 */
	private int userLevel;

	/**
	 * =============== get/set ===============
	 */
	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public int getAccountType() {
		return accountType;
	}

	public void setAccountType(int accountType) {
		this.accountType = accountType;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public int getUserLevel() {
		return userLevel;
	}

	public void setUserLevel(int userLevel) {
		this.userLevel = userLevel;
	}

	/**
	 * =============== toString() ===============
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
