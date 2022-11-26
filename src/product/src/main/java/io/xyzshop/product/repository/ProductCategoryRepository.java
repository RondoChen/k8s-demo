package io.xyzshop.product.repository;

import io.xyzshop.product.model.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.io.Serializable;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Integer>, JpaSpecificationExecutor<ProductCategory>, Serializable {

	/**
	 * 读取单个商品类别数据
	 *
	 * @param categoryId
	 * @return
	 */
	ProductCategory findByCategoryId(int categoryId);

}
