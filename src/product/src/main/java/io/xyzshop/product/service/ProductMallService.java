package io.xyzshop.product.service;

import io.xyzshop.product.modelVO.ProductBannerVO;
import io.xyzshop.product.modelVO.ProductListInCategoryVO;
import io.xyzshop.product.modelref.User;

import java.util.List;

/**
 *
 */
public interface ProductMallService {

	/**
	 * 获取商城首页-banner商品数据
	 *
	 * @param user
	 * @param count
	 * @return
	 */
	List<ProductBannerVO> getBannerProductList(User user, int count);

	/**
	 * 获取商城首页-热门商品数据
	 *
	 * @param user
	 * @param count
	 * @return
	 */
	ProductListInCategoryVO getHotProductList(User user, int count);

	/**
	 * 获取商城首页-新品商品数据
	 *
	 * @param user
	 * @param count
	 * @return
	 */
	ProductListInCategoryVO getNewProductList(User user, int count);

	/**
	 * 获取商城首页-分类商品数据
	 *
	 * @param user
	 * @param count
	 * @return
	 */
	List<ProductListInCategoryVO> getProductListInCategories(User user, int count);
}
