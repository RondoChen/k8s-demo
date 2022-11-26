package io.xyzshop.product.repository;

import io.xyzshop.product.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.io.Serializable;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product>, Serializable {

	/**
	 * 读取单个商品数据
	 *
	 * @param productId
	 * @return
	 */
	Product findByProductId(long productId);

	/**
	 * 根据商品ID列表获取商品列表数据
	 *
	 * @param productIds
	 * @return
	 */
	@Query(value = "select * from product where product_id in (?1)", nativeQuery = true)
	List<Product> findByProductIds(List<Long> productIds);

	/**
	 * 读取全部新品商品数据
	 *
	 * @param newProduct
	 * @param deleted
	 * @return
	 */
	List<Product> findByNewProductAndDeleted(boolean newProduct, boolean deleted);

	/**
	 * 读取全部热卖商品数据
	 *
	 * @param hotProduct
	 * @param deleted
	 * @return
	 */
	List<Product> findByHotProductAndDeleted(boolean hotProduct, boolean deleted);

	/**
	 * 读取全部 banner 商品数据
	 *
	 * @param bannerProduct
	 * @param deleted
	 * @return
	 */
	List<Product> findByBannerProductAndDeleted(boolean bannerProduct, boolean deleted);

	/**
	 * 按类别ID读取商品数据
	 *
	 * @param categoryId
	 * @param deleted
	 * @param pageable
	 * @return
	 */
	Page<Product> findByCategoryIdAndDeleted(int categoryId, boolean deleted, Pageable pageable);

	/**
	 * 根据 categoryId 读取该类别下的商品数量
	 *
	 * @param categoryId
	 * @return
	 */
	int countByCategoryId(int categoryId);
}
