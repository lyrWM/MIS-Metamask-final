package com.wemade.metamask.application.duzonapproval.service;

import com.wemade.metamask.application.duzonapproval.domain.CryptoCommon;
import com.wemade.metamask.application.duzonapproval.domain.DuzonApprovalTypeDB;
import com.wemade.metamask.application.duzonapproval.domain.ApprovalCommon;
import com.wemade.metamask.application.duzonapproval.domain.ApprovalParam;

import java.util.List;

/**
 * User: Sewon Yoo (ysw@wemade.com)
 * Date: 2022. 01. 24.
 * Time: 오후 1:12
 */
public interface DuzonApprovalService {

	/**
	 * DuzonApprovalTypeDB 가져오기 (ChildTable, GwCompany 정보 제외)
	 *
	 * @param outProcessCode String
	 * @return DuzonApprovalTypeDB
	 */
	DuzonApprovalTypeDB selectDuzonApprovalTypeByOutProcessCode(String outProcessCode);


    /**
     * 지출결의 대상 품의서 목록 조회 (품의유형 상관없이 전체 조회)
     *
     * @param param ApprovalParam
     * @return List<ApprovalCommon>
     *
     */
    List<ApprovalCommon> selectApprovalInvoiceReadyListWithDocId(ApprovalParam param);

	/**
	 * 암호화폐 계획품의 기반 전송 목록 조회
	 *
	 * @param approKey
	 * @param docId
	 * @return
	 */
	List<CryptoCommon> selectCryotoCommonListByApproKey(String approKey, String docId);


}
