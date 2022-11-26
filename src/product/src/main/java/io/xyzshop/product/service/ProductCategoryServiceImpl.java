package io.xyzshop.product.service;

import io.xyzshop.product.model.ProductCategory;
import io.xyzshop.product.repository.ProductCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {

	@Autowired
	private ProductCategoryRepository productCategoryRepository;

	@Override
	public List<ProductCategory> getProductCategories() {
		return productCategoryRepository.findAll();
	}

	@Override
	public ProductCategory getProductCategory(int categoryId) {
		return productCategoryRepository.findByCategoryId(categoryId);
	}
}
