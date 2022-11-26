package io.xyzshop.product.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "product_category")
public class ProductCategory implements Serializable {
	private static final long serialVersionUID = 3070232475501612620L;

	/** =============== field =============== */
	/**
	 * 商品类目Id（当前：1,腾讯叮当 2,腾讯极光 3,腾讯潮流手办 4,腾讯QQfamily）
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "category_id", nullable = false)
	private int categoryId;

	/**
	 * 商品类目标题
	 */
	@Column(name = "category_title", nullable = false)
	private String categoryTitle;

	/**
	 * 商品类目描述
	 */
	@Column(name = "category_description", nullable = false)
	private String categoryDescription;

	/**
	 * =============== get/set ===============
	 */
	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryTitle() {
		return categoryTitle;
	}

	public void setCategoryTitle(String categoryTitle) {
		this.categoryTitle = categoryTitle;
	}

	public String getCategoryDescription() {
		return categoryDescription;
	}

	public void setCategoryDescription(String categoryDescription) {
		this.categoryDescription = categoryDescription;
	}

	/**
	 * =============== toString() ===============
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
