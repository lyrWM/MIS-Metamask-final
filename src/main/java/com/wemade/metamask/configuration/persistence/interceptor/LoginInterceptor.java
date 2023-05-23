package com.wemade.metamask.configuration.persistence.interceptor;

import com.wemade.configuration.interceptor.WinterHandlerInterceptorAdapter;
import com.wemade.infrastructure.constants.RETURN_TP;
import com.wemade.infrastructure.convertor.JsonConvertor;
import com.wemade.infrastructure.entity.ResponseBase;
import com.wemade.infrastructure.utils.WebUtils;
import com.wemade.metamask.application.login.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;

/**
 * User: Sewon Yoo (ysw@wemade.com)
 * Date: 2022. 01. 24.
 * Time: 오후 12:49
 */
@Slf4j
public class LoginInterceptor extends WinterHandlerInterceptorAdapter {

	@Autowired
	private LoginService loginService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

		if (!(handler instanceof HandlerMethod)) {
			return true;
		}

		if (isLoginNotRequired(handler)) {
			return true;
		}

		boolean isLogin = loginService.isLogin();

		if (!isLogin) {
			responseSendRedirectLogin(request, response, handler);
			return false;
		}


		return true;
	}

	private void responseSendRedirectLogin(HttpServletRequest request, HttpServletResponse response, Object handler) {
		try {
			if (isResponseBase(handler)) {
				ResponseBase responseBase = new ResponseBase();
				responseBase.setCode(RETURN_TP.FAIL);
				responseBase.setMessage("로그인 후 이용해주세요.");

				response.setContentType(MediaType.APPLICATION_JSON_VALUE);
				response.getWriter().write(JsonConvertor.toString(responseBase));
			} else {
				StringBuilder redirectUrl = new StringBuilder("/login?rUrl=" + URLEncoder.encode(WebUtils.getRequestServerUri(request), StandardCharsets.UTF_8.name()));

				Enumeration<String> e = request.getParameterNames();
				while (e.hasMoreElements()) {
					String name = e.nextElement();
					String[] values = request.getParameterValues(name);
					for (String value : values) {
						redirectUrl.append("&").append(name).append("=").append(URLEncoder.encode(value, StandardCharsets.UTF_8.name()));
					}
				}

				response.sendRedirect(redirectUrl.toString());
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

}
