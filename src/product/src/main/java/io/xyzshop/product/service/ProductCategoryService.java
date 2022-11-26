package io.xyzshop.product.service;

import io.xyzshop.product.model.ProductCategory;

import java.util.List;

public interface ProductCategoryService {

	/**
	 * 获取全部商品类别数据
	 *
	 * @return
	 */
	List<ProductCategory> getProductCategories();

	/**
	 * 获取单个商品类别数据
	 *
	 * @param categoryId
	 * @return
	 */
	ProductCategory getProductCategory(int categoryId);
}
