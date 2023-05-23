package com.wemade.metamask.application.login.service;


import com.wemade.metamask.application.login.domain.LoginUser;

/**
 * User: Sewon Yoo (ysw@wemade.com)
 * Date: 2022. 01. 24.
 * Time: 오후 1:12
 */
public interface JwtService {

	String getAccessToken(LoginUser loginUser);

	LoginUser getLoginUserInfo();

}
