package io.xyzshop.product.modelVO;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.List;

public class ProductListInCategoryVO implements Serializable {
	private static final long serialVersionUID = -1483077838905559359L;

	/** =============== field =============== */
	/**
	 * 商品类目Id（当 categoryId=0 时，为新品或热卖，具体以 categoryTitle 数据为准）
	 */
	private int categoryId;

	/**
	 * 商品类目标题
	 */
	private String categoryTitle = "";

	/**
	 * 商品列表数据
	 */
	private List<ProductListVO> productList = Lists.newArrayList();

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

	public List<ProductListVO> getProductList() {
		return productList;
	}

	public void setProductList(List<ProductListVO> productList) {
		this.productList = productList;
	}

	/**
	 * =============== toString() ===============
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
