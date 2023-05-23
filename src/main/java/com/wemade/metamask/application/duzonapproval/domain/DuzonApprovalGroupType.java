package com.wemade.metamask.application.duzonapproval.domain;

import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;

/**
 * User: Minseon Lee (minseon@wemade.com)
 * Date: 2022-05-18
 * Time: 오후 6:07
 */
public enum DuzonApprovalGroupType {

	TEST("테스트", "test"),
	APPROVAL_EXPENSE("비용품의", "expense"),
	APPROVAL_PURCHASE("구매품의", "purchase"),
	APPROVAL_PERSONAL("개인비용품의", "personal"),
	APPROVAL_CRYPTO("암호화폐품의", "crypto"),
	INVOICE("지출결의", "invoice"),
	HR("인사관련신청", "hr"),
	SEAL("인감신청", "seal");

	@Getter
	private String comment;

	@Getter
	private String path;

	DuzonApprovalGroupType(String comment, String path) {
		this.comment = comment;
		this.path = path;
	}

	public static Optional<DuzonApprovalGroupType> findByPath(String path) {
		return Arrays.stream(DuzonApprovalGroupType.values())
				.filter(s -> s.path.equalsIgnoreCase(path))
				.findAny();
	}
}
