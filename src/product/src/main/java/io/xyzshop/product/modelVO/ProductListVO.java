package io.xyzshop.product.modelVO;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

public class ProductListVO implements Serializable {
	private static final long serialVersionUID = -8209769824765056300L;

	/** =============== field =============== */
	/**
	 * 商品Id
	 */
	private long productId;

	/**
	 * 商品标题
	 */
	private String title;

	/**
	 * 商品副标题
	 */
	private String subtitle;

	/**
	 * 商品封面图片url
	 */
	private String coverImgUrl;

	/**
	 * 市场价格
	 */
	private int marketPrice;

	/**
	 * 腾讯价格
	 */
	private int tencentPrice;

	/**
	 * 是否为新款商品
	 */
	private boolean newProduct;

	/**
	 * =============== get/set ===============
	 */
	public long getProductId() {
		return productId;
	}

	public void setProductId(long productId) {
		this.productId = productId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSubtitle() {
		return subtitle;
	}

	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}

	public String getCoverImgUrl() {
		return coverImgUrl;
	}

	public void setCoverImgUrl(String coverImgUrl) {
		this.coverImgUrl = coverImgUrl;
	}

	public int getMarketPrice() {
		return marketPrice;
	}

	public void setMarketPrice(int marketPrice) {
		this.marketPrice = marketPrice;
	}

	public int getTencentPrice() {
		return tencentPrice;
	}

	public void setTencentPrice(int tencentPrice) {
		this.tencentPrice = tencentPrice;
	}

	public boolean isNewProduct() {
		return newProduct;
	}

	public void setNewProduct(boolean newProduct) {
		this.newProduct = newProduct;
	}

	/**
	 * =============== toString() ===============
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
