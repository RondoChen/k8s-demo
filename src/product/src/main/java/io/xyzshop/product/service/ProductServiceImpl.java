package io.xyzshop.product.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.xyzshop.product.config.ConfigProp;
import io.xyzshop.product.model.Product;
import io.xyzshop.product.model.ProductImage;
import io.xyzshop.product.model.ProductImageType;
import io.xyzshop.product.modelVO.ProductDetailVO;
import io.xyzshop.product.modelVO.ProductListVO;
import io.xyzshop.product.modelVO.create.ProductCreateUtils;
import io.xyzshop.product.modelref.User;
import io.xyzshop.product.repository.ProductImageRepository;
import io.xyzshop.product.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
	private Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private ProductImageRepository productImageRepository;

	@Autowired
	private ConfigProp configProp;

	@Autowired
	private RestTemplate restTemplate;


	@Override
	public List<ProductListVO> getCategoryProductList(User user, int categoryId, Pageable pageable) {
		// 读取当前页数商品列表数据
		PageRequest pageRequest = PageRequest.of(pageable.getPageNumber() - 1, pageable.getPageSize(), new Sort(Sort.Direction.ASC, "productId"));
		List<Product> products = productRepository.findByCategoryIdAndDeleted(categoryId, false, pageRequest).getContent();

		// 计算并返回 ProductListVO 列表数据
		return calcProductListVOs(user, products);
	}

	/**
	 * 计算 ProductListVO 列表数据
	 *
	 * @param user
	 * @param products
	 * @return
	 */
	private List<ProductListVO> calcProductListVOs(User user, List<Product> products) {
		List<ProductListVO> result = Lists.newArrayList();

		// 读取商品列表数据关联的「图片数据」
		List<Long> productIds = products.stream().map(x -> x.getProductId()).collect(Collectors.toList());
		Map<Long, List<ProductImage>> productCoverImagesMap = calcProductCoverImagesMap(productIds);

		// 计算最终结果数据
		for (Product product : products) {
			List<ProductImage> productCoverImages = productCoverImagesMap.containsKey(product.getProductId()) ? productCoverImagesMap.get(product.getProductId()) : Lists.newArrayList();
			result.add(ProductCreateUtils.createProductListVO(product, productCoverImages));
		}

		return result;
	}

	private Map<Long, List<ProductImage>> calcProductCoverImagesMap(List<Long> productIds) {
		Map<Long, List<ProductImage>> resultMap = Maps.newHashMap();
		for (long productId : productIds) {
			List<ProductImage> productImages = productImageRepository.findByProductIdAndImgTypeAndDeleted(productId, ProductImageType.PRODUCT_COVER_IMAGES.ordinal(), false);
			resultMap.put(productId, productImages);
		}

		return resultMap;
	}

	@Override
	public int getCategoryProductTotalCount(int categoryId) {
		return productRepository.countByCategoryId(categoryId);
	}

	@Override
	public ProductDetailVO getProductDetail(User user, long productId) {
		Product product = productRepository.findByProductId(productId);
		List<ProductImage> productImages = productImageRepository.findByProductIdAndDeleted(productId, false);
		return ProductCreateUtils.createProductDetailVO(product, productImages);
	}

	@Override
	public List<ProductListVO> getProductList(User user, List<Long> productIds) {
		// 根据 productIds 读取商品列表数据
		List<Product> products = productRepository.findByProductIds(productIds);

		// 计算并返回 ProductListVO 列表数据
		return calcProductListVOs(user, products);
	}
}
