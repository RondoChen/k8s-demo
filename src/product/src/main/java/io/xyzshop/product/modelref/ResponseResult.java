package io.xyzshop.product.modelref;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseResult implements Serializable {
	private static final long serialVersionUID = 4663139145478365977L;

	/** =============== field =============== */
	/**
	 * 语言版本
	 */
	private String lang = "zh-CN";

	/**
	 * api service 名称
	 */
	private String service = "product";

	/**
	 * api 版本号
	 */
	private String apiVersion = "v1";

	/**
	 * 请求处理完成时间点
	 */
	private long timeStamp;

	/**
	 * 请求处理服务端IP地址
	 */
	private String serverIP;

	/**
	 * 请求是否成功
	 */
	private boolean success;

	/**
	 * 数据对象
	 */
	private Object data;

	/**
	 * 返回错误信息
	 */
	private ResponseError error;

	/**
	 * =============== get/set ===============
	 */
	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getApiVersion() {
		return apiVersion;
	}

	public void setApiVersion(String apiVersion) {
		this.apiVersion = apiVersion;
	}

	public long getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(long timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getServerIP() {
		return serverIP;
	}

	public void setServerIP(String serverIP) {
		this.serverIP = serverIP;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public ResponseError getError() {
		return error;
	}

	public void setError(ResponseError error) {
		this.error = error;
	}

	/**
	 * =============== toString() ===============
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
