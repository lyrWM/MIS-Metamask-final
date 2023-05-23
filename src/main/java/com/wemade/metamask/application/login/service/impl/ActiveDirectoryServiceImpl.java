package com.wemade.metamask.application.login.service.impl;

import com.wemade.infrastructure.ad.AdAuthResponse;
import com.wemade.infrastructure.ad.AdProvider;
import com.wemade.infrastructure.ad.AdSearchResponse;
import com.wemade.infrastructure.ad.AdSearchType;
import com.wemade.infrastructure.constants.RETURN_TP;
import com.wemade.infrastructure.entity.ResponseError;
import com.wemade.infrastructure.utils.PreconditionUtils;
import com.wemade.metamask.application.login.service.ActiveDirectoryService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * User: Sewon Yoo (ysw@wemade.com)
 * Date: 2022. 01. 24.
 * Time: 오후 1:12
 */
@Service
public class ActiveDirectoryServiceImpl implements ActiveDirectoryService {

	private final AdProvider adProvider;

	@Autowired
	public ActiveDirectoryServiceImpl(AdProvider adProvider) {
		this.adProvider = adProvider;
	}

	@Override
	public AdAuthResponse auth(String userId, String userPw) {
		PreconditionUtils.invalidCondition(StringUtils.isEmpty(userId), "아이디를 입력해주세요.");
		PreconditionUtils.invalidCondition(StringUtils.isEmpty(userPw), "비밀번호를 입력해주세요.");

		AdAuthResponse adAuthResponse = adProvider.auth(userId, userPw);
		if (RETURN_TP.FAIL.equals(adAuthResponse.getCode())) {
			ResponseError responseError = adAuthResponse.getError();
			if (Objects.nonNull(responseError)) {
				adAuthResponse.setMessage(adAuthResponse.getMessage() + "<br>" + responseError.getMessage());
			}
		}

		return adAuthResponse;
	}

	@Override
	public AdSearchResponse search(AdSearchType searchType, String searchText) throws Exception {
		PreconditionUtils.invalidCondition(searchType == null, "검색타입을 입력해주세요.");
		PreconditionUtils.invalidCondition(StringUtils.isEmpty(searchText), "검색어를 입력해주세요.");

		AdSearchResponse openLdapSearchResponse = adProvider.search(searchType, searchText);
		if (RETURN_TP.FAIL.equals(openLdapSearchResponse.getCode())) {
			ResponseError responseError = openLdapSearchResponse.getError();
			if (Objects.nonNull(responseError)) {
				openLdapSearchResponse.setMessage(openLdapSearchResponse.getMessage() + "<br>" + responseError.getMessage());
			}
		}

		return openLdapSearchResponse;
	}
}
