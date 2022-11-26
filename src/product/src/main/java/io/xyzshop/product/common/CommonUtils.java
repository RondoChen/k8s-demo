package io.xyzshop.product.common;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import io.xyzshop.product.config.ConfigProp;
import io.xyzshop.product.modelref.User;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Enumeration;
import java.util.List;

public class CommonUtils {
	private static Logger log = LoggerFactory.getLogger(CommonUtils.class);

	// user session 名称
	public static final String USER_SESSION_NAME = "user_session";

	// APIVersion 版本号配置
	public static final String API_VERSION_V1 = "v1";
	public static final String API_VERSION_V2 = "v2";

	// 业务日志模板
	public static final String NO_DATA_TMPL = "%s, ";
	public static final String HAS_DATA_TMPL = "%s, [%s]";

	// 自定义错误代码
	public static final int EXCEPTION_ERROR_CODE = 1100;
	public static final int PARAM_ERROR_CODE = 1101;

	private static HttpHeaders s_httpHeaders = null;

	/**
	 * 读取用户 openId 数据
	 *
	 * @param ticket
	 * @param configProp
	 * @param restTemplate
	 * @return
	 */
	public static User getLoggedInUser(String ticket, ConfigProp configProp, RestTemplate restTemplate) {
		User user = null;

		try {
			String userInfoJson = restTemplate.getForObject(String.format("%s/open/account/info?ticket=%s", configProp.getPassportServiceAddress(), ticket), String.class);
			ObjectMapper mapper = new ObjectMapper();
			JsonNode dataNode = mapper.readTree(userInfoJson).path("data");

			user = new User();
			user.setUserId(dataNode.path("id").asInt());
			user.setOpenId(dataNode.path("open_id").asText());
			user.setAccountType(dataNode.path("account_type").asInt());
			user.setNickName(dataNode.path("nick").asText());
			user.setUserLevel(dataNode.path("score").asInt());

			if (user.getUserId() == 0 || Strings.isNullOrEmpty(user.getOpenId()) || Strings.isNullOrEmpty(user.getNickName())) {
				user = null;
			}
		} catch (Exception ex) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			String logInfo = String.format("ticket=%s", ticket);
			log.error(String.format(CommonUtils.HAS_DATA_TMPL, methodName, logInfo), ex);
		}

		return user;
	}

	/**
	 * 获取 APPLICATION_JSON headers
	 *
	 * @return
	 */
	public static HttpHeaders getApplicationHeader() {
		if (s_httpHeaders == null) {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			s_httpHeaders = headers;
		}

		return s_httpHeaders;
	}

	/**
	 * list 转换成 String（String 类型，逗号分隔）
	 *
	 * @param list
	 * @param <T>
	 * @return
	 */
	public static <T> String listToString(List<T> list) {
		return listToString(list, ",");
	}

	/**
	 * list 转换成 String（String 类型，带分隔符）
	 *
	 * @param list
	 * @param separator
	 * @return
	 */
	public static <T> String listToString(List<T> list, String separator) {
		StringBuilder sBuilder = new StringBuilder();

		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				sBuilder.append(list.get(i));
				sBuilder.append(separator);
			}
		}

		int separatorLength = StringUtils.isEmpty(separator) ? 0 : separator.length();
		return (sBuilder.length() > 0) ? sBuilder.substring(0, sBuilder.length() - separatorLength) : "";
	}

	/**
	 * string 转换成 Long list（带分隔符）
	 *
	 * @param str
	 * @param separator
	 * @return
	 */
	public static List<Long> stringToListLong(String str, String separator) {
		List<Long> result = Lists.newArrayList();

		try {
			str = str.replace(" ", "");
			String[] list = StringUtils.split(str, separator);
			for (int i = 0; i < list.length; i++) {
				if (!Strings.isNullOrEmpty(list[i])) {
					result.add(Long.valueOf(list[i]));
				}
			}
		} catch (Exception ex) {
			String logData = String.format("str=%s, separator=%s", str, separator);
			log.error(String.format(HAS_DATA_TMPL, Thread.currentThread().getStackTrace()[1].getMethodName(), logData));
		}

		return result;
	}

	/**
	 * 解析 日期时间字符串 为 LocalDateTime 类型的时间数据
	 *
	 * @param dateTimeStr
	 * @return
	 */
	public static LocalDateTime parseDateTime(String dateTimeStr) {
		try {
			if (dateTimeStr.contains("-") && dateTimeStr.contains(":")) {
				return LocalDateTime.parse(dateTimeStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
			}
		} catch (Exception ex) {
			log.error(String.format(HAS_DATA_TMPL, Thread.currentThread().getStackTrace()[1].getMethodName(), dateTimeStr));
		}

		return LocalDate.of(1901, 01, 01).atStartOfDay();
	}

	/**
	 * 修正 py 传递的 json 数据
	 *
	 * @param json
	 * @return
	 */
	public static String fixPyJson(String json) {
		try {
			return json.replace("\"[", "[").replace("]\"", "]").replace("\\\"", "\"");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return json;
	}

	/**
	 * 取得本地IP
	 *
	 * @return
	 */
	public static String getLocalIP() {
		String sIP = "";
		InetAddress ip = null;
		try {
			boolean bFindIP = false;
			Enumeration<NetworkInterface> netInterfaces = (Enumeration<NetworkInterface>) NetworkInterface.getNetworkInterfaces();
			while (netInterfaces.hasMoreElements()) {
				if (bFindIP) {
					break;
				}
				NetworkInterface ni = (NetworkInterface) netInterfaces.nextElement();
				// ----------特定情况，可以考虑用ni.getName判断
				// 遍历所有ip
				Enumeration<InetAddress> ips = ni.getInetAddresses();
				while (ips.hasMoreElements()) {
					ip = (InetAddress) ips.nextElement();
					if (ip.isSiteLocalAddress() && !ip.isLoopbackAddress() // 127.开头的都是lookback地址
							&& ip.getHostAddress().indexOf(":") == -1) {
						bFindIP = true;
						break;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(String.format(HAS_DATA_TMPL, Thread.currentThread().getStackTrace()[1].getMethodName(), "MultiThreadedTrapReceiver 453: " + e.getMessage()));
		}
		if (null != ip) {
			sIP = ip.getHostAddress();
		}
		return sIP;
	}
}
