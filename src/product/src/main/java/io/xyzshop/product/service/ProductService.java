package io.xyzshop.product.service;

import io.xyzshop.product.modelVO.ProductDetailVO;
import io.xyzshop.product.modelVO.ProductListVO;
import io.xyzshop.product.modelref.User;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {

	/**
	 * 获取分类商品列表数据（按分类，带分页）
	 *
	 * @param user
	 * @param categoryId
	 * @param pageable
	 * @return
	 */
	List<ProductListVO> getCategoryProductList(User user, int categoryId, Pageable pageable);

	/**
	 * 获取分类商品总数量
	 *
	 * @param categoryId
	 * @return
	 */
	int getCategoryProductTotalCount(int categoryId);

	/**
	 * 获取商品详情数据
	 *
	 * @param user
	 * @param productId
	 * @return
	 */
	ProductDetailVO getProductDetail(User user, long productId);

	/**
	 * 根据商品ID列表获取商品列表数据
	 *
	 * @param user
	 * @param productIds
	 * @return
	 */
	List<ProductListVO> getProductList(User user, List<Long> productIds);
}
