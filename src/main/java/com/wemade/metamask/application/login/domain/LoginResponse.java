package com.wemade.metamask.application.login.domain;

import com.wemade.infrastructure.constants.RETURN_TP;
import com.wemade.infrastructure.entity.ResponseBase;
import lombok.Getter;
import lombok.Setter;

/**
 * User: Sewon Yoo (ysw@wemade.com)
 * Date: 2022. 01. 24.
 * Time: 오후 1:12
 */
public class LoginResponse extends ResponseBase<String> {

	@Getter
	@Setter
	private String url;

	public LoginResponse() {
		setCode(RETURN_TP.FAIL);
		setMessage("");
	}
}