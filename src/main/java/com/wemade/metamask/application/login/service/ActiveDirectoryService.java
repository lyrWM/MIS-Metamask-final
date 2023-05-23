package com.wemade.metamask.application.login.service;

import com.wemade.infrastructure.ad.AdAuthResponse;
import com.wemade.infrastructure.ad.AdSearchResponse;
import com.wemade.infrastructure.ad.AdSearchType;

/**
 * User: Sewon Yoo (ysw@wemade.com)
 * Date: 2022. 01. 24.
 * Time: 오후 1:12
 */
public interface ActiveDirectoryService {

	AdAuthResponse auth(String userId, String userPw);

	AdSearchResponse search(AdSearchType searchType, String searchText) throws Exception;

}
