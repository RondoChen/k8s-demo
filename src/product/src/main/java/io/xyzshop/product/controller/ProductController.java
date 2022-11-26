package io.xyzshop.product.controller;

import io.xyzshop.product.common.CommonUtils;
import io.xyzshop.product.common.ResponseResultUtils;
import io.xyzshop.product.modelVO.ProductDetailVO;
import io.xyzshop.product.modelVO.ProductListVO;
import io.xyzshop.product.modelref.ResponseResult;
import io.xyzshop.product.modelref.User;
import io.xyzshop.product.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/apis/product")
public class ProductController {
	private Logger log = LoggerFactory.getLogger(getClass());

	@Value("${app.version}")
	private String appVersion;

	@Autowired
	private ProductService productService;

	/**
	 * 分类商品列表接口（按分类，带分页）
	 *
	 * @param request
	 * @param categoryId
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@GetMapping(value = "/list/{categoryId}/{pageNum}/{pageSize}/")
	public ResponseResult categoryProductList(HttpServletRequest request, @PathVariable Integer categoryId, @PathVariable Integer pageNum, @PathVariable Integer pageSize) {
		// 处理记录 标准输出 日志
		System.out.println(String.format("「stdout」invoke categoryProductList interface, apiVersion=%s, categoryId=%s, pageNum=%s, pageSize=%s.", appVersion, categoryId, pageNum, pageSize));

		// 处理传入参数、获取 user 数据
		categoryId = (categoryId == null) ? 0 : categoryId;
		pageNum = (pageNum == null) ? 1 : pageNum;
		pageSize = (pageSize == null) ? 10 : pageSize;
		User user = (User) request.getSession().getAttribute(CommonUtils.USER_SESSION_NAME);

		// 计算最终结果
		try {
			Pageable pageable = PageRequest.of(pageNum, pageSize);
			List<ProductListVO> resultList = productService.getCategoryProductList(user, categoryId, pageable);
			int totalCount = productService.getCategoryProductTotalCount(categoryId);
			return ResponseResultUtils.createSuccessDataListResponseResult(CommonUtils.API_VERSION_V1, resultList, totalCount, pageable);
		} catch (Exception ex) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			String logInfo = String.format("categoryId=%s, pageNum=%s, pageSize=%s", categoryId, pageNum, pageSize);
			log.error(String.format(CommonUtils.HAS_DATA_TMPL, methodName, logInfo), ex);
			return ResponseResultUtils.createErrorResponseResult(CommonUtils.API_VERSION_V1, CommonUtils.EXCEPTION_ERROR_CODE, ex.toString(), "");
		}
	}

	/**
	 * 商品详情接口（不带评论）
	 *
	 * @param request
	 * @param productId
	 * @return
	 */
	@GetMapping(value = "/detail/{productId}/")
	public ResponseResult productDetail(HttpServletRequest request, @PathVariable Long productId) {
		// 处理记录 容器内文件 日志
		log.info(String.format("「logfile」invoke productDetail interface, apiVersion=%s, productId=%s.", appVersion, productId));

		// 处理传入参数、获取 user 数据
		productId = (productId == null) ? 0 : productId;
		User user = (User) request.getSession().getAttribute(CommonUtils.USER_SESSION_NAME);

		// 计算最终结果
		try {
			ProductDetailVO productDetail = productService.getProductDetail(user, productId);
			return ResponseResultUtils.createSuccessResponseResult(CommonUtils.API_VERSION_V1, productDetail);
		} catch (Exception ex) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			String logInfo = String.format("productId=%s", productId);
			log.error(String.format(CommonUtils.HAS_DATA_TMPL, methodName, logInfo), ex);
			return ResponseResultUtils.createErrorResponseResult(CommonUtils.API_VERSION_V1, CommonUtils.EXCEPTION_ERROR_CODE, ex.toString(), "");
		}
	}

	/**
	 * 根据商品ID获取商品列表数据接口
	 *
	 * @param request
	 * @param productIdsStr
	 * @return
	 */
	@GetMapping(value = "/list/{productIdsStr}/")
	public ResponseResult productDetailList(HttpServletRequest request, @PathVariable String productIdsStr) {
		// 处理记录 容器内文件 日志
		log.info(String.format("「logfile」invoke productDetailList interface, apiVersion=%s, productIdsStr=%s.", appVersion, productIdsStr));

		// 处理传入参数、获取 user 数据
		List<Long> productIds = CommonUtils.stringToListLong(productIdsStr, ",");
		if (productIds.size() == 0) {
			return ResponseResultUtils.createErrorResponseResult(CommonUtils.API_VERSION_V1, CommonUtils.PARAM_ERROR_CODE, "productIds param is error.", "");
		}
		User user = (User) request.getSession().getAttribute(CommonUtils.USER_SESSION_NAME);

		// 计算最终结果
		try {
			List<ProductListVO> resultList = productService.getProductList(user, productIds);
			return ResponseResultUtils.createSuccessDataListResponseResult(CommonUtils.API_VERSION_V1, resultList);
		} catch (Exception ex) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			String logInfo = String.format("productIdsStr=%s", productIdsStr);
			log.error(String.format(CommonUtils.HAS_DATA_TMPL, methodName, logInfo), ex);
			return ResponseResultUtils.createErrorResponseResult(CommonUtils.API_VERSION_V1, CommonUtils.EXCEPTION_ERROR_CODE, ex.toString(), "");
		}
	}
}
