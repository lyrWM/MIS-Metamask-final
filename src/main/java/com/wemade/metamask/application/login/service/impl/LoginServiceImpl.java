package com.wemade.metamask.application.login.service.impl;

import com.wemade.infrastructure.ad.AdAuthResponse;
import com.wemade.infrastructure.ad.AdSearchResult;
import com.wemade.infrastructure.constants.RETURN_TP;
import com.wemade.infrastructure.constants.YN;
import com.wemade.infrastructure.cookie.CookieBuilder;
import com.wemade.infrastructure.properties.PropertiesHelper;
import com.wemade.infrastructure.utils.PreconditionUtils;
import com.wemade.infrastructure.utils.XssFilterUtils;
import com.wemade.metamask.application.base.dao.EmployeeMapper;
import com.wemade.metamask.application.base.domain.Company;
import com.wemade.metamask.application.base.domain.EmployeeInfo;
import com.wemade.metamask.application.common.domain.Constants;
import com.wemade.metamask.application.login.dao.LoginMapper;
import com.wemade.metamask.application.login.domain.LoginRequest;
import com.wemade.metamask.application.login.domain.LoginUser;
import com.wemade.metamask.application.login.service.ActiveDirectoryService;
import com.wemade.metamask.application.login.service.JwtService;
import com.wemade.metamask.application.login.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Objects;

/**
 * User: Sewon Yoo (ysw@wemade.com)
 * Date: 2022. 01. 24.
 * Time: 오후 1:12
 */
@Slf4j
@Service
public class LoginServiceImpl implements LoginService {

	@Autowired(required = false)
	private HttpServletRequest request;

	@Autowired(required = false)
	private HttpServletResponse response;

	@Autowired
	private JwtService jwtService;

	@Autowired
	private ActiveDirectoryService activeDirectoryService;

	@Autowired
	private LoginMapper loginMapper;

	@Autowired
	private EmployeeMapper employeeMapper;

	@Override
	public boolean login(LoginRequest param) throws Exception {
		PreconditionUtils.invalidCondition(Objects.isNull(param), "로그인 정보가 없습니다.");
		if (!this.isLogin()) {
			PreconditionUtils.invalidCondition(StringUtils.isEmpty(param.getUserId()), "UserId를 입력해 주세요.");
			PreconditionUtils.invalidCondition(StringUtils.isEmpty(param.getPasswd()), "Password를 입력해 주세요.");
		} else {
			param.setUserId(this.getLoginUser().getUserId());
		}

		if (XssFilterUtils.isOn()) {
			param.setUserId(XssFilterUtils.unescape(param.getUserId()));
			param.setPasswd(XssFilterUtils.unescape(param.getPasswd()));
		}

		EmployeeInfo employeeInfo;

		if (!this.isLogin()) {
			employeeInfo = employeeMapper.selectByLoginId(param.getUserId(), null);
			PreconditionUtils.invalidCondition(ObjectUtils.isEmpty(employeeInfo), "사용자 정보를 찾을 수 없습니다.");
			YN adUseYn = loginMapper.selectAdUseYn(employeeInfo.getGwCompanyCd());
			PreconditionUtils.invalidCondition(YN.N == adUseYn, "AD를 사용하는 법인에서만 이용이 가능 합니다.");

			AdAuthResponse adAuthResponse = activeDirectoryService.auth(param.getUserId(), param.getPasswd());
			PreconditionUtils.invalidCondition(RETURN_TP.FAIL.equals(adAuthResponse.getCode()), adAuthResponse.getMessage());

			AdSearchResult adSearchResult = adAuthResponse.getData();
			PreconditionUtils.invalidCondition(Objects.isNull(adSearchResult), "로그인 결과 값이 없습니다.");
		} else {
			employeeInfo = employeeMapper.selectByLoginId(param.getUserId(), param.getGwCompanyCd());
		}


		LoginUser loginUser = parsingDuzonLoginUser(employeeInfo);

		String accessToken = jwtService.getAccessToken(loginUser);
		PreconditionUtils.invalidCondition(com.wemade.infrastructure.utils.StringUtils.isBlank(accessToken), "Token 생성에 실패하였습니다.");

		// 토큰 생성
		new CookieBuilder(request, response)
				.setCryptKey(Constants.COOKIE_CRYPT_KEY)
				.setMaxAge(30 * 60)
				//https인 경우만
				.setSecure(!PropertiesHelper.isDevelop())
				.set(Constants.COOKIE_NAME_ACCESS_TOKEN, accessToken);

		// 아이디 저장
		try {
			processSaveId(param);
		} catch (UnsupportedEncodingException e) {
			log.error(e.getMessage(), e);
		}

		return true;
	}

	private LoginUser parsingDuzonLoginUser(EmployeeInfo employeeInfo) throws Exception {
		PreconditionUtils.invalidCondition(Objects.isNull(employeeInfo), "로그인 결과 값이 없습니다.");

		LoginUser loginUser = new LoginUser();
		loginUser.setUserId(employeeInfo.getUserId());
		loginUser.setUserNm(employeeInfo.getEmpName());
		loginUser.setDisplayUserNm(employeeInfo.getEmpName());
		loginUser.setEmail(employeeInfo.getEMail());
		loginUser.setCompanyNm(employeeInfo.getCompanyName());
		loginUser.setOrgNm(employeeInfo.getDeptName());
        loginUser.setErpDeptCd(employeeInfo.getDeptCd());

		loginUser.setEmpSeq(employeeInfo.getEmpSeq());
        loginUser.setGwCompanyCd(employeeInfo.getGwCompanyCd());
		loginUser.setErpCompanyCd(employeeInfo.getErpCompanyCd());
		loginUser.setErpEmpNo(employeeInfo.getEmpNo());

		loginUser.setLogin(true);

		return loginUser;
	}

	private void processSaveId(LoginRequest param) throws UnsupportedEncodingException {
		if ("Y".equalsIgnoreCase(param.getSaveYn())) {
			new CookieBuilder(request, response)
					.setCryptKey(Constants.COOKIE_CRYPT_KEY)
					.setMaxAge(86400 * 30)
					.set(Constants.COOKIE_NAME_LOGIN_SAVE_ID, param.getUserId());
		}
		else {
			new CookieBuilder(request, response)
					.delete(Constants.COOKIE_NAME_LOGIN_SAVE_ID);
		}
	}

	@Override
	public void logout() {
		new CookieBuilder(request, response).delete(Constants.COOKIE_NAME_ACCESS_TOKEN);
	}


	@Override
	public boolean isLogin() {
		return Objects.nonNull(this.getLoginUser());
	}

	@Override
	public LoginUser getLoginUser() {
		return jwtService.getLoginUserInfo();
	}

	@Override
	public List<Company> selectMyCompanyList() {
		return loginMapper.selectMyCompanyList(this.getLoginUser().getEmpSeq());
	}
}
