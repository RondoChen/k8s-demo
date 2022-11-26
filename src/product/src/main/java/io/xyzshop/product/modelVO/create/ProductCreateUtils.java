package io.xyzshop.product.modelVO.create;

import com.google.common.collect.Lists;
import io.xyzshop.product.model.Product;
import io.xyzshop.product.model.ProductCategory;
import io.xyzshop.product.model.ProductImage;
import io.xyzshop.product.model.ProductImageType;
import io.xyzshop.product.modelVO.*;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ProductCreateUtils {

	public static ProductBannerVO createProductBannerVO(Product product, List<ProductImage> productBannerImages) {
		ProductBannerVO result = new ProductBannerVO();
		result.setProductId(product.getProductId());
		result.setTitle(product.getTitle());
		result.setSubtitle(product.getSubtitle());
		result.setBannerImgUrl((productBannerImages.size() > 0) ? productBannerImages.get(0).getImgUrl() : "");
		result.setMarketPrice(product.getMarketPrice());
		result.setTencentPrice(product.getTencentPrice());
		result.setNewProduct(product.isNewProduct());

		return result;
	}

	public static ProductDetailVO createProductDetailVO(Product product, List<ProductImage> productImages) {
		ProductDetailVO result = new ProductDetailVO();
		result.setProductId(product.getProductId());
		result.setCategoryId(product.getCategoryId());
		result.setTitle(product.getTitle());
		result.setSubtitle(product.getSubtitle());
		result.setMarketPrice(product.getMarketPrice());
		result.setTencentPrice(product.getTencentPrice());
		result.setNewProduct(product.isNewProduct());
		result.setHotProduct(product.isHotProduct());
		result.setBannerProduct(product.isBannerProduct());
		result.setOverview(product.getOverview());
		result.setHighlights(product.getHighlights());
		result.setPack(product.getPack());
		result.setSpec(product.getSpec());
		result.setProducer(product.getProducer());
		result.setCreateTime(product.getCreateTime());
		result.setDeleted(product.isDeleted());

		if (!CollectionUtils.isEmpty(productImages)) {
			List<ProductImage> productDetailTopImages = productImages.stream().filter(x -> x.getImgType() == ProductImageType.PRODUCT_TOP_IMAGES.ordinal()).collect(Collectors.toList());
			List<ProductImage> productDetailContentImages = productImages.stream().filter(x -> x.getImgType() == ProductImageType.PRODUCT_CONTENT_IMAGES.ordinal()).collect(Collectors.toList());
			result.setProductDetailTopImages(productDetailTopImages.stream().map(x -> ProductImageCreateUtils.createProductImageVO(x)).collect(Collectors.toList()));
			result.setProductDetailContentImages(productDetailContentImages.stream().map(x -> ProductImageCreateUtils.createProductImageVO(x)).collect(Collectors.toList()));
		}

		return result;
	}

	public static ProductListInCategoryVO createProductListInCategoryVO(ProductCategory productCategory, List<Product> products, Map<Long, List<ProductImage>> productCoverImagesMap) {
		ProductListInCategoryVO result = new ProductListInCategoryVO();
		result.setCategoryId(productCategory.getCategoryId());
		result.setCategoryTitle(productCategory.getCategoryTitle());

		for (Product product : products) {
			List<ProductImage> productCoverImages = productCoverImagesMap.containsKey(product.getProductId()) ? productCoverImagesMap.get(product.getProductId()) : Lists.newArrayList();
			result.getProductList().add(createProductListVO(product, productCoverImages));
		}

		return result;
	}

	public static ProductListVO createProductListVO(Product product, List<ProductImage> productCoverImages) {
		ProductListVO result = new ProductListVO();
		result.setProductId(product.getProductId());
		result.setTitle(product.getTitle());
		result.setSubtitle(product.getSubtitle());
		result.setCoverImgUrl((productCoverImages.size() > 0) ? productCoverImages.get(0).getImgUrl() : "");
		result.setMarketPrice(product.getMarketPrice());
		result.setTencentPrice(product.getTencentPrice());
		result.setNewProduct(product.isNewProduct());

		return result;
	}
}
