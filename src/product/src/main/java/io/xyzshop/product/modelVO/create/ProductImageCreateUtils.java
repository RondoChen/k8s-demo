package io.xyzshop.product.modelVO.create;

import io.xyzshop.product.model.ProductImage;
import io.xyzshop.product.modelVO.ProductImageVO;

public class ProductImageCreateUtils {

	public static ProductImageVO createProductImageVO(ProductImage productImage) {
		ProductImageVO result = new ProductImageVO();
		result.setImgId(productImage.getImgId());
		result.setProductId(productImage.getProductId());
		result.setImgUrl(productImage.getImgUrl());

		return result;
	}
}
