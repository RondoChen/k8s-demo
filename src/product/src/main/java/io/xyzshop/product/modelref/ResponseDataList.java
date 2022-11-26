package io.xyzshop.product.modelref;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.List;

public class ResponseDataList implements Serializable {
	private static final long serialVersionUID = -872975293175532359L;

	/** =============== field =============== */
	/**
	 * 返回错误信息
	 */
	private List<Object> items = Lists.newArrayList();

	/**
	 * 总页数
	 */
	private int total;

	/**
	 * 当前页数
	 */
	private int offset;

	/**
	 * 每页数量
	 */
	private int limit;

	/**
	 * =============== get/set ===============
	 */
	public List<Object> getItems() {
		return items;
	}

	public void setItems(List<Object> items) {
		this.items = items;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	/**
	 * =============== toString() ===============
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
