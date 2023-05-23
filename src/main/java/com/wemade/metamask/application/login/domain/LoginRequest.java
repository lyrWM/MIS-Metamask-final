package com.wemade.metamask.application.login.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * User: Sewon Yoo (ysw@wemade.com)
 * Date: 2022. 01. 24.
 * Time: 오후 1:12
 */
@Data(staticConstructor = "create")
public class LoginRequest {

	private String userId;
	private String companyCd;
	private String passwd;
	private String saveYn;
	@JsonProperty("rUrl")
	private String rUrl;

	// Duzon에서만 사용
	private String empSeq;
    private String gwCompanyCd;
	private String erpEmpNo;
	private String erpCompanyCd;
    private String duzonAppId;
}

