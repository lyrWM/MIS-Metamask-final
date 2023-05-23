package com.wemade.metamask.configuration.persistence.interceptor;

import com.wemade.configuration.interceptor.WinterHandlerInterceptorAdapter;
import com.wemade.infrastructure.cookie.CookieBuilder;
import com.wemade.metamask.application.login.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * User: Sewon Yoo (ysw@wemade.com)
 * Date: 2022. 01. 24.
 * Time: 오후 12:49
 */
@Slf4j
public class ServiceVariableInterceptor extends WinterHandlerInterceptorAdapter {

	@Autowired
	private LoginService loginService;

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		if (!(handler instanceof HandlerMethod)) {
			return;
		}

		if (isLoginNotRequired(handler)) {
			return;
		}

		if (isDisableServiceVariables(handler)) {
			return;
		}

		if (isAvableServiceVariable(modelAndView)) {
			ModelMap modelMap = new ModelMap();

			if (loginService.isLogin()) {
				modelMap.addAttribute("LOGIN_USER", loginService.getLoginUser());
				modelMap.addAttribute("MY_COMPANY_LIST", loginService.selectMyCompanyList());
			}
			modelMap.addAttribute("NAVBAR_MENU_COLLAPSE", new CookieBuilder(request, response).setNonCrypt(true).get("WE_FINANCE_MENU_COLLAPSE"));

			modelAndView.addAllObjects(modelMap);
		}
	}

}
