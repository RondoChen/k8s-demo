package io.xyzshop.product.modelVO;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

public class ProductImageVO implements Serializable {
	private static final long serialVersionUID = -2006770208239995508L;

	/** =============== field =============== */
	/**
	 * 商品图片Id
	 */
	private long imgId;

	/**
	 * 商品Id
	 */
	private long productId;

	/**
	 * 商品大图url
	 */
	private String imgUrl;

	/**
	 * =============== get/set ===============
	 */
	public long getImgId() {
		return imgId;
	}

	public void setImgId(long imgId) {
		this.imgId = imgId;
	}

	public long getProductId() {
		return productId;
	}

	public void setProductId(long productId) {
		this.productId = productId;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	/**
	 * =============== toString() ===============
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
