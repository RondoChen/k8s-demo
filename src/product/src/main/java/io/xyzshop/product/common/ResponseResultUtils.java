package io.xyzshop.product.common;

import com.google.common.collect.Lists;
import io.xyzshop.product.modelref.ResponseDataList;
import io.xyzshop.product.modelref.ResponseError;
import io.xyzshop.product.modelref.ResponseResult;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

public class ResponseResultUtils {

    /**
     * 创建请求成功返回数据结构（单项数据）
     *
     * @param apiVersion
     * @param data
     * @return
     */
    public static ResponseResult createSuccessResponseResult(String apiVersion, Object data) {
        ResponseResult result = new ResponseResult();
        result.setApiVersion(apiVersion);
        result.setTimeStamp(System.currentTimeMillis());
        result.setServerIP(CommonUtils.getLocalIP());
        result.setSuccess(true);
        result.setData(data);

        return result;
    }

    /**
     * 创建请求成功返回数据结构（数据列表，不带分页）
     *
     * @param apiVersion
     * @param items
     * @param <T>
     * @return
     */
    public static <T> ResponseResult createSuccessDataListResponseResult(String apiVersion, List<T> items) {
        int limit = (CollectionUtils.isEmpty(items)) ? 0 : items.size();
        if (limit == 0) {
            limit = 1;
        }
        return createSuccessDataListResponseResult(apiVersion, items, items.size(), PageRequest.of(1, limit));
    }

    /**
     * 创建请求成功返回数据结构（数据列表，带分页）
     *
     * @param apiVersion
     * @param items
     * @param totalCount
     * @param pageable
     * @param <T>
     * @return
     */
    public static <T> ResponseResult createSuccessDataListResponseResult(String apiVersion, List<T> items, int totalCount, Pageable pageable) {
        ResponseResult result = new ResponseResult();

        // 计算 successDataList 数据
        List<Object> objItems = Lists.newArrayList();
        if (!CollectionUtils.isEmpty(items)) {
            objItems = items.stream().collect(Collectors.toList());
        }

        ResponseDataList successDataList = new ResponseDataList();
        successDataList.setItems(objItems);
        successDataList.setTotal(totalCount);
        successDataList.setOffset((pageable.getPageNumber() - 1) * pageable.getPageSize());
        successDataList.setLimit(objItems.size());

        // 计算最终结果数据
        result.setApiVersion(apiVersion);
        result.setTimeStamp(System.currentTimeMillis());
        result.setServerIP(CommonUtils.getLocalIP());
        result.setSuccess(true);
        result.setData(successDataList);

        return result;
    }

    /**
     * 创建请求失败返回数据结构
     *
     * @param apiVersion
     * @param code
     * @param message
     * @param service
     * @return
     */
    public static ResponseResult createErrorResponseResult(String apiVersion, int code, String message, String service) {
        ResponseResult result = new ResponseResult();

        // 计算 responseError 数据
        ResponseError error = new ResponseError();
        error.setCode(code);
        error.setMessage(message);
        error.setService(service);

        // 计算最终结果数据
        result.setApiVersion(apiVersion);
        result.setTimeStamp(System.currentTimeMillis());
        result.setServerIP(CommonUtils.getLocalIP());
        result.setSuccess(false);
        result.setError(error);

        return result;
    }
}
