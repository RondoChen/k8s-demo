package io.xyzshop.product.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.xyzshop.product.model.Product;
import io.xyzshop.product.model.ProductCategory;
import io.xyzshop.product.model.ProductImage;
import io.xyzshop.product.model.ProductImageType;
import io.xyzshop.product.modelVO.ProductBannerVO;
import io.xyzshop.product.modelVO.ProductListInCategoryVO;
import io.xyzshop.product.modelVO.ProductListVO;
import io.xyzshop.product.modelVO.create.ProductCreateUtils;
import io.xyzshop.product.modelref.User;
import io.xyzshop.product.repository.ProductCategoryRepository;
import io.xyzshop.product.repository.ProductImageRepository;
import io.xyzshop.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ProductMallServiceImpl implements ProductMallService {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private ProductCategoryRepository productCategoryRepository;

	@Autowired
	private ProductImageRepository productImageRepository;

	@Override
	public List<ProductBannerVO> getBannerProductList(User user, int count) {
		List<ProductBannerVO> resultList = Lists.newArrayList();

		List<Product> products = productRepository.findByBannerProductAndDeleted(true, false);
		if (products.size() > count) {
			products = products.stream().limit(count).collect(Collectors.toList());
		}

		for (Product product : products) {
			List<ProductImage> productBannerImages = productImageRepository.findByProductIdAndImgTypeAndDeleted(product.getProductId(), ProductImageType.PRODUCT_BANNER_IMAGES.ordinal(), false);
			resultList.add(ProductCreateUtils.createProductBannerVO(product, productBannerImages));
		}

		return resultList;
	}

	@Override
	public ProductListInCategoryVO getHotProductList(User user, int count) {
		return calProductListInCategoryVO(user, count, true);
	}

	@Override
	public ProductListInCategoryVO getNewProductList(User user, int count) {
		return calProductListInCategoryVO(user, count, false);
	}

	private ProductListInCategoryVO calProductListInCategoryVO(User user, int count, boolean isHotProduct) {
		ProductListInCategoryVO result = new ProductListInCategoryVO();

		// 计算热门或新品商品列表数据
		List<ProductListVO> productList = Lists.newArrayList();

		List<Product> products = isHotProduct
				? productRepository.findByHotProductAndDeleted(true, false)
				: productRepository.findByNewProductAndDeleted(true, false);
		if (products.size() > count) {
			products = products.stream().limit(count).collect(Collectors.toList());
		}

		for (Product product : products) {
			List<ProductImage> productCoverImages = productImageRepository.findByProductIdAndImgTypeAndDeleted(product.getProductId(), ProductImageType.PRODUCT_COVER_IMAGES.ordinal(), false);
			productList.add(ProductCreateUtils.createProductListVO(product, productCoverImages));
		}

		// 计算最终结果
		result.setCategoryTitle(isHotProduct ? "热门商品" : "最新商品");
		result.setProductList(productList);

		return result;
	}

	@Override
	public List<ProductListInCategoryVO> getProductListInCategories(User user, int count) {
		List<ProductListInCategoryVO> resultList = Lists.newArrayList();

		List<ProductCategory> productCategories = productCategoryRepository.findAll();
		for (ProductCategory productCategory : productCategories) {
			List<Product> products = productRepository.findByCategoryIdAndDeleted(productCategory.getCategoryId(), false, PageRequest.of(0, count)).getContent();
			Map<Long, List<ProductImage>> productCoverImagesMap = calcProductCoverImagesMap(products.stream().map(x -> x.getProductId()).collect(Collectors.toList()));
			resultList.add(ProductCreateUtils.createProductListInCategoryVO(productCategory, products, productCoverImagesMap));
		}

		return resultList;
	}

	/**
	 * 读取产品列表的图片数据
	 *
	 * @param productIds
	 * @return
	 */
	private Map<Long, List<ProductImage>> calcProductCoverImagesMap(List<Long> productIds) {
		Map<Long, List<ProductImage>> resultMap = Maps.newHashMap();

		for (long productId : productIds) {
			List<ProductImage> productImages = productImageRepository.findByProductIdAndDeleted(productId, false);
			resultMap.put(productId, productImages);
		}

		return resultMap;
	}
}
