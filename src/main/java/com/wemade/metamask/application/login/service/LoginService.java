package com.wemade.metamask.application.login.service;

import com.wemade.metamask.application.base.domain.Company;
import com.wemade.metamask.application.login.domain.LoginRequest;
import com.wemade.metamask.application.login.domain.LoginUser;

import java.util.List;

/**
 * User: Sewon Yoo (ysw@wemade.com)
 * Date: 2022. 01. 24.
 * Time: 오후 1:12
 */
public interface LoginService {

	/**
	 * 로그인을 진행한다. (Meta)
	 * 정상적으로 로그인이 되면 Session 에 값을 등록한다.
	 *
	 * @param param LoginRequest
	 * @return boolean
	 */
	boolean login(LoginRequest param) throws Exception;

	/**
	 * 로그아웃을 진행한다.
	 * 세션에 등록된 값을 삭제한다.
	 */
	void logout();


	/**
	 * 현재 로그인 상태인지 확인한다.
	 *
	 * @return boolean
	 */
	boolean isLogin();


	/**
	 * 로그인 유저의 정보를 가져온다.
	 *
	 * @return LoginUser
	 */
	LoginUser getLoginUser();

	/**
     * 내 회사 정보 LIst
     *
     * @return List<Company>
     */
	List<Company> selectMyCompanyList();

}
