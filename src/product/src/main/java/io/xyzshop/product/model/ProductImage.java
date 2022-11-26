package io.xyzshop.product.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "product_image")
public class ProductImage implements Serializable {
	private static final long serialVersionUID = -1500610781412988356L;

	/** =============== field =============== */
	/**
	 * 商品图片Id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "img_id", nullable = false)
	private long imgId;

	/**
	 * 商品图片类型（0,商品详情页头部图片 1,商品详情页内容图片 2,商品封面图片 3,商品banner广告图片）
	 */
	@Column(name = "img_type", nullable = false)
	private int imgType;

	/**
	 * 商品Id
	 */
	@Column(name = "product_id", nullable = false)
	private long productId;

	/**
	 * 商品大图url
	 */
	@Column(name = "img_url", nullable = false)
	private String imgUrl;

	/**
	 * 商品方形缩略图url
	 */
	@Column(name = "img_rectangle_url", nullable = false)
	private String imgRectangleUrl;

	/**
	 * 商品图片索引
	 */
	@Column(name = "img_index", nullable = false)
	private int imgIndex;

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
	public long getImgId() {
		return imgId;
	}

	public void setImgId(long imgId) {
		this.imgId = imgId;
	}

	public int getImgType() {
		return imgType;
	}

	public void setImgType(int imgType) {
		this.imgType = imgType;
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

	public String getImgRectangleUrl() {
		return imgRectangleUrl;
	}

	public void setImgRectangleUrl(String imgRectangleUrl) {
		this.imgRectangleUrl = imgRectangleUrl;
	}

	public int getImgIndex() {
		return imgIndex;
	}

	public void setImgIndex(int imgIndex) {
		this.imgIndex = imgIndex;
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
