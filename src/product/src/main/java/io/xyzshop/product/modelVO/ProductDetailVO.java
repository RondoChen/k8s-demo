package io.xyzshop.product.modelVO;

import com.google.common.collect.Lists;
import io.xyzshop.product.model.Product;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.List;

public class ProductDetailVO extends Product implements Serializable {
	private static final long serialVersionUID = 4735833335628033299L;

	/** =============== field =============== */
	/**
	 * 商品详情页头部图片列表
	 */
	private List<ProductImageVO> productDetailTopImages = Lists.newArrayList();

	/**
	 * 商品详情页内容图片列表
	 */
	private List<ProductImageVO> productDetailContentImages = Lists.newArrayList();

	/**
	 * =============== get/set ===============
	 */
	public List<ProductImageVO> getProductDetailTopImages() {
		return productDetailTopImages;
	}

	public void setProductDetailTopImages(List<ProductImageVO> productDetailTopImages) {
		this.productDetailTopImages = productDetailTopImages;
	}

	public List<ProductImageVO> getProductDetailContentImages() {
		return productDetailContentImages;
	}

	public void setProductDetailContentImages(List<ProductImageVO> productDetailContentImages) {
		this.productDetailContentImages = productDetailContentImages;
	}

	/**
	 * =============== toString() ===============
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
