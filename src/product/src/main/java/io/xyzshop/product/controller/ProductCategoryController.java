package io.xyzshop.product.controller;

import io.xyzshop.product.common.CommonUtils;
import io.xyzshop.product.common.ResponseResultUtils;
import io.xyzshop.product.model.ProductCategory;
import io.xyzshop.product.modelref.ResponseResult;
import io.xyzshop.product.service.ProductCategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/apis/product-category")
public class ProductCategoryController {
	private Logger log = LoggerFactory.getLogger(getClass());

	@Value("${app.version}")
	private String appVersion;

	@Autowired
	private ProductCategoryService productCategoryService;

	/**
	 * 全部商品类别接口
	 *
	 * @return
	 */
	@GetMapping(value = "/categories/")
	public ResponseResult productCategories() {
		// 处理记录 标准输出 日志
		System.out.println(String.format("「stdout」invoke productCategories interface, apiVersion=%s.", appVersion));

		try {
			List<ProductCategory> categories = productCategoryService.getProductCategories();
			return ResponseResultUtils.createSuccessDataListResponseResult(CommonUtils.API_VERSION_V1, categories);
		} catch (Exception ex) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			log.error(String.format(CommonUtils.NO_DATA_TMPL, methodName), ex);
			return ResponseResultUtils.createErrorResponseResult(CommonUtils.API_VERSION_V1, CommonUtils.EXCEPTION_ERROR_CODE, ex.toString(), "");
		}
	}

	/**
	 * 单个商品类别接口
	 *
	 * @param categoryId
	 * @return
	 */
	@GetMapping(value = "/category/{categoryId}/")
	public ResponseResult productCategory(@PathVariable Integer categoryId) {
		// 处理记录 容器内文件 日志
		log.info(String.format("「logfile」invoke productCategory interface, apiVersion=%s, categoryId=%s.", appVersion, categoryId));

		// 处理传入参数
		categoryId = (categoryId == null) ? 0 : categoryId;

		try {
			ProductCategory category = productCategoryService.getProductCategory(categoryId);
			return ResponseResultUtils.createSuccessResponseResult(CommonUtils.API_VERSION_V1, category);
		} catch (Exception ex) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			String logInfo = String.format("categoryId=%s", categoryId);
			log.error(String.format(CommonUtils.HAS_DATA_TMPL, methodName, logInfo), ex);
			return ResponseResultUtils.createErrorResponseResult(CommonUtils.API_VERSION_V1, CommonUtils.EXCEPTION_ERROR_CODE, ex.toString(), "");
		}
	}
}
