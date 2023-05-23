package com.wemade.metamask.application.login.domain;

import lombok.Data;

/**
 * User: Sewon Yoo (ysw@wemade.com)
 * Date: 2022. 01. 24.
 * Time: 오후 1:12
 */
@Data
public class LoginUser {

	private Integer userNo;						// 사용자 번호
	private String userId;						// 사용자 아이디
	private String userNm;						// 사용자 이름
	private String displayUserNm;				// 사용자 이름 (법인제외)
	private String email;						// 사용자 이메일
	private String companyNm;					// 회사명
	private String orgNm;						// 사용자 부서 명
	private String dutyNm;						// 직책 명
	private String mobileNumber;				// 핸드폰 번호
	private String telNumber;					// 회사 전화 번호
	private String companyCd;					// 회사 코드
	private boolean isLogin;					// 로그인 여부

	private String empSeq;						// 사용자 시퀀스

	// Duzon에서 사용
    private String gwCompanyCd;					// gw 회사코드
	private String erpEmpNo;					// erp 사번
	private String erpCompanyCd;				// erp 회사코드
    private String erpDeptCd;                   // erp 부서코드

	public static LoginUser create() {
		LoginUser loginUser = new LoginUser();
		loginUser.setLogin(Boolean.FALSE);

		return loginUser;
	}

}

