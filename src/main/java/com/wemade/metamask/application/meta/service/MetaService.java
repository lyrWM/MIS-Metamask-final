package com.wemade.metamask.application.meta.service;

import com.wemade.metamask.application.meta.domain.MetaParam;

import javax.servlet.http.HttpServletRequest;

/**
 * User: Sewon Yoo (ysw@wemade.com)
 * Date: 2022. 01. 24.
 * Time: 오후 1:12
 */
public interface MetaService {

	/**
	 * 로그인을 진행한다.
	 * 정상적으로 로그인이 되면 Session 에 값을 등록한다.
	 *
	 * @param request HttpServletRequest
	 * @return boolean
	 */
	boolean checkIp(HttpServletRequest request);

	/**
	 * 전송토큰 success log insert
	 *
	 * @param param MetaParam
	 * @return boolean
	 */
	boolean transferSubmit(MetaParam param) throws Exception;

	/**
	 * 전송토큰 error log insert
	 *
	 * @param param MetaParam
	 * @return boolean
	 */
	boolean transferError(MetaParam param) throws Exception;


}
