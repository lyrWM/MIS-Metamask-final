package com.wemade.metamask.application.login.web;

import com.wemade.infrastructure.annotation.LoginNotRequired;
import com.wemade.infrastructure.exception.MessageException;
import com.wemade.infrastructure.utils.StringUtils;
import com.wemade.metamask.application.base.service.CompanyService;
import com.wemade.metamask.application.login.domain.LoginRequest;
import com.wemade.metamask.application.login.service.LoginService;
import com.wemade.metamask.application.meta.service.MetaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * User: Sewon Yoo (ysw@wemade.com)
 * Date: 2022. 01. 24.
 * Time: 오후 1:12
 */
@Slf4j
@Controller
public class LoginController {

	private final LoginService loginService;
	private final CompanyService companyService;
	private final MetaService metaService;

	@Autowired
	public LoginController(LoginService loginService, CompanyService companyService, MetaService metaService) {
		this.loginService = loginService;
		this.companyService = companyService;
		this.metaService = metaService;
	}

	@LoginNotRequired
	@GetMapping("/login")
	public ModelAndView login(HttpServletRequest request,
							  HttpServletResponse response,
							  ModelAndView mnv,
							  @RequestParam(value = "rUrl", required = false, defaultValue = "") String rUrl) throws IOException {

		if (StringUtils.isEmpty(rUrl)) {
			rUrl = "/meta/main";
		}

//		if (!metaService.checkIp(request)) {
//			response.sendError(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.getReasonPhrase());
//		}

		mnv.addObject("companyList", companyService.selectCompanyList());
		mnv.addObject("rUrl", rUrl);
		mnv.setViewName("login/login");
		return mnv;
	}

	@LoginNotRequired
	@PostMapping("/login/submit")
	public ModelAndView loginSubmit(ModelAndView mnv,
									@ModelAttribute LoginRequest param) {

		if (StringUtils.isBlank(param.getRUrl())) {
			param.setRUrl("/meta/main");
		}

		try {
			if (loginService.login(param)) {
				return new ModelAndView(new RedirectView(param.getRUrl()));
			} else {
				mnv.addObject("errorMsg", "로그인에 실패했습니다.");
                mnv.setViewName("error/error");
			}
			return mnv;
		} catch (MessageException e) {
			log.debug(e.getMessage(), e);

			mnv.addObject("errorMsg", e.getMessage());
            mnv.setViewName("error/error");
			return mnv;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			mnv.addObject("errorMsg", e.getMessage());
            mnv.setViewName("error/error");
			return mnv;
		}
	}

	@LoginNotRequired
	@RequestMapping("/logout")
	public ModelAndView logout() {
		// 로그아웃 시킨다.
		loginService.logout();

		return new ModelAndView(new RedirectView("/login"));
	}

}
