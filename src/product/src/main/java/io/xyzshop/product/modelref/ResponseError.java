package io.xyzshop.product.modelref;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

public class ResponseError implements Serializable {
	private static final long serialVersionUID = -5867862547545366174L;

	/** =============== field =============== */
	/**
	 * 错误代码
	 */
	private int code;

	/**
	 * 错误信息
	 */
	private String message = "";

	/**
	 * 错误发生服务名
	 */
	private String service = "";

	/**
	 * =============== get/set ===============
	 */
	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	/**
	 * =============== toString() ===============
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
