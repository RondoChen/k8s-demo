package io.xyzshop.product.interceptor;

import com.google.common.base.Strings;
import io.xyzshop.product.common.CommonUtils;
import io.xyzshop.product.modelref.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserInfoInterceptor implements HandlerInterceptor {
	private Logger logger = LoggerFactory.getLogger(UserInfoInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		try {
			Object userObj = request.getSession().getAttribute(CommonUtils.USER_SESSION_NAME);
			if (userObj == null) {
				String ticket = request.getParameter("ticket");
				if (!Strings.isNullOrEmpty(ticket)) {
					User loggedInUser = null; // new User(0, 0, "", 0); TODO
					request.getSession().setAttribute(CommonUtils.USER_SESSION_NAME, loggedInUser);
				} else {
					User notLoggedInUser = new User();
					request.getSession().setAttribute(CommonUtils.USER_SESSION_NAME, notLoggedInUser);
				}
			}
		} catch (Exception ex) {
			logger.error("userInfoInterceptor preHandle FAIL!", ex);
		}

		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
	}
}