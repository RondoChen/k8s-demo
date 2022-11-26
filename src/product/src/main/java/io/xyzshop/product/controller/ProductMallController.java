package io.xyzshop.product.controller;

import com.google.common.collect.Lists;
import io.xyzshop.product.common.CommonUtils;
import io.xyzshop.product.common.ResponseResultUtils;
import io.xyzshop.product.modelVO.ProductBannerVO;
import io.xyzshop.product.modelVO.ProductListInCategoryVO;
import io.xyzshop.product.modelref.ResponseResult;
import io.xyzshop.product.modelref.User;
import io.xyzshop.product.service.ProductMallService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/apis/product-mall")
public class ProductMallController {
	private Logger log = LoggerFactory.getLogger(getClass());

	@Value("${app.version}")
	private String appVersion;

	@Autowired
	private ProductMallService productMallService;

	/**
	 * 商城首页-banner商品列表接口
	 *
	 * @param request
	 * @param count
	 * @return
	 */
	@GetMapping(value = "/banner-product-list/{count}/")
	public ResponseResult bannerProductList(HttpServletRequest request, @PathVariable Integer count) {
		// 处理记录 标准输出 日志
		System.out.println(String.format("「stdout」invoke bannerProductList interface, apiVersion=%s, count=%s.", appVersion, count));

		// 处理 v2 版本不返回 banner 图片数据
		if (appVersion.equals(CommonUtils.API_VERSION_V2)) {
			return ResponseResultUtils.createSuccessDataListResponseResult(CommonUtils.API_VERSION_V2, Lists.newArrayList());
		}

		// 处理传入参数、获取 user 数据
		count = (count == null) ? 0 : count;
		User user = (User) request.getSession().getAttribute(CommonUtils.USER_SESSION_NAME);

		// 计算最终结果
		try {
			List<ProductBannerVO> productBannerVOs = productMallService.getBannerProductList(user, count);
			return ResponseResultUtils.createSuccessDataListResponseResult(CommonUtils.API_VERSION_V1, productBannerVOs);
		} catch (Exception ex) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			String logInfo = String.format("count=%s", count);
			log.error(String.format(CommonUtils.HAS_DATA_TMPL, methodName, logInfo), ex);
			return ResponseResultUtils.createErrorResponseResult(CommonUtils.API_VERSION_V1, CommonUtils.EXCEPTION_ERROR_CODE, ex.toString(), "");
		}
	}

	/**
	 * 商城首页-顶部商品列表接口
	 *
	 * @param request
	 * @param count
	 * @return
	 */
	@GetMapping(value = "/top-product-list/{count}/")
	public ResponseResult topProductList(HttpServletRequest request, @PathVariable Integer count) {
		// 处理记录 容器内文件 日志
		log.info(String.format("「logfile」invoke topProductList interface, apiVersion=%s, count=%s.", appVersion, count));

		// 处理传入参数、获取 user 数据
		count = (count == null) ? 0 : count;
		User user = (User) request.getSession().getAttribute(CommonUtils.USER_SESSION_NAME);

		// 计算最终结果
		try {
			ProductListInCategoryVO productListInCategoryVO = productMallService.getHotProductList(user, count);
			return ResponseResultUtils.createSuccessResponseResult(CommonUtils.API_VERSION_V1, productListInCategoryVO);
		} catch (Exception ex) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			String logInfo = String.format("count=%s", count);
			log.error(String.format(CommonUtils.HAS_DATA_TMPL, methodName, logInfo), ex);
			return ResponseResultUtils.createErrorResponseResult(CommonUtils.API_VERSION_V1, CommonUtils.EXCEPTION_ERROR_CODE, ex.toString(), "");
		}
	}

	/**
	 * 商城首页-分类商品列表接口
	 *
	 * @param request
	 * @param count
	 * @return
	 */
	@GetMapping(value = "/product-list-in-categories/{count}/")
	public ResponseResult productListInCategories(HttpServletRequest request, @PathVariable Integer count) {
		// 处理记录 容器内文件 日志
		log.info(String.format("「logfile」invoke productListInCategories interface, apiVersion=%s, count=%s.", appVersion, count));

		// 处理传入参数、获取 user 数据
		count = (count == null) ? 0 : count;
		User user = (User) request.getSession().getAttribute(CommonUtils.USER_SESSION_NAME);

		// 计算最终结果
		try {
			List<ProductListInCategoryVO> productListInCategoryVOS = productMallService.getProductListInCategories(user, count);
			return ResponseResultUtils.createSuccessDataListResponseResult(CommonUtils.API_VERSION_V1, productListInCategoryVOS);
		} catch (Exception ex) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			String logInfo = String.format("count=%s", count);
			log.error(String.format(CommonUtils.HAS_DATA_TMPL, methodName, logInfo), ex);
			return ResponseResultUtils.createErrorResponseResult(CommonUtils.API_VERSION_V1, CommonUtils.EXCEPTION_ERROR_CODE, ex.toString(), "");
		}
	}
}
