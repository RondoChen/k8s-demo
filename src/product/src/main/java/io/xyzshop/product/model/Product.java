package io.xyzshop.product.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;


@Entity
@Table(name = "product")
public class Product implements Serializable {
	private static final long serialVersionUID = -2038539363414224926L;

	/** =============== field =============== */
	/**
	 * 商品Id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "product_id", nullable = false)
	private long productId;

	/**
	 * 商品类目Id
	 */
	@Column(name = "category_id", nullable = false)
	private int categoryId;

	/**
	 * 商品标题
	 */
	@Column(name = "title", nullable = false)
	private String title;

	/**
	 * 商品副标题
	 */
	@Column(name = "subtitle", nullable = false)
	private String subtitle;

	/**
	 * 市场价格
	 */
	@Column(name = "market_price", nullable = false)
	private int marketPrice;

	/**
	 * 腾讯价格
	 */
	@Column(name = "tencent_price", nullable = false)
	private int tencentPrice;

	/**
	 * 是否为新款商品
	 */
	@Column(name = "new_product", nullable = false)
	private boolean newProduct;

	/**
	 * 是否为热卖商品
	 */
	@Column(name = "hot_product", nullable = false)
	private boolean hotProduct;

	/**
	 * 是否为banner图片商品
	 */
	@Column(name = "banner_product", nullable = false)
	private boolean bannerProduct;

	/**
	 * 产品概述
	 */
	@Column(name = "overview")
	private String overview = "";

	/**
	 * 产品亮点
	 */
	@Column(name = "highlights")
	private String highlights = "";

	/**
	 * 包装内容
	 */
	@Column(name = "pack")
	private String pack = "";

	/**
	 * 产品规格
	 */
	@Column(name = "spec")
	private String spec = "";

	/**
	 * 生产厂商
	 */
	@Column(name = "producer")
	private String producer = "";

	/**
	 * 创建时间
	 */
	@Column(name = "create_time", nullable = false)
	private LocalDateTime createTime;

	/**
	 * 是否已删除
	 */
	@Column(name = "deleted", nullable = false)
	private boolean deleted;

	/**
	 * =============== get/set ===============
	 */
	public long getProductId() {
		return productId;
	}

	public void setProductId(long productId) {
		this.productId = productId;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
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

	public boolean isHotProduct() {
		return hotProduct;
	}

	public void setHotProduct(boolean hotProduct) {
		this.hotProduct = hotProduct;
	}

	public boolean isBannerProduct() {
		return bannerProduct;
	}

	public void setBannerProduct(boolean bannerProduct) {
		this.bannerProduct = bannerProduct;
	}

	public String getOverview() {
		return overview;
	}

	public void setOverview(String overview) {
		this.overview = overview;
	}

	public String getHighlights() {
		return highlights;
	}

	public void setHighlights(String highlights) {
		this.highlights = highlights;
	}

	public String getPack() {
		return pack;
	}

	public void setPack(String pack) {
		this.pack = pack;
	}

	public String getSpec() {
		return spec;
	}

	public void setSpec(String spec) {
		this.spec = spec;
	}

	public String getProducer() {
		return producer;
	}

	public void setProducer(String producer) {
		this.producer = producer;
	}

	public LocalDateTime getCreateTime() {
		return createTime;
	}

	public void setCreateTime(LocalDateTime createTime) {
		this.createTime = createTime;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	/**
	 * =============== toString() ===============
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
