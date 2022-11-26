package io.xyzshop.product.repository;

import io.xyzshop.product.model.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.io.Serializable;
import java.util.List;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long>, JpaSpecificationExecutor<ProductImage>, Serializable {

	/**
	 * 按商品Id读取图片数据
	 *
	 * @param productId
	 * @param deleted
	 * @return
	 */
	List<ProductImage> findByProductIdAndDeleted(long productId, boolean deleted);

	/**
	 * 按商品Id读取图片数据
	 *
	 * @param productId
	 * @param imgType
	 * @param deleted
	 * @return
	 */
	List<ProductImage> findByProductIdAndImgTypeAndDeleted(long productId, int imgType, boolean deleted);

}
