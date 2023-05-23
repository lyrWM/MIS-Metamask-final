package com.wemade.metamask.application.duzonapproval.dao;

import com.wemade.metamask.application.duzonapproval.domain.CryptoCommon;
import com.wemade.metamask.application.duzonapproval.domain.DuzonApprovalTypeDB;
import com.wemade.metamask.application.duzonapproval.domain.ApprovalCommon;
import com.wemade.metamask.application.duzonapproval.domain.ApprovalParam;
import com.wemade.metamask.configuration.persistence.annotation.DataSourceFinanceMyBatis;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * User: Sewon Yoo (ysw@wemade.com)
 * Date: 2022-04-25
 * Time: 오후 5:21
 */
@DataSourceFinanceMyBatis
@Repository
public interface DuzonApprovalMapper {

    /**
     *  DuzonApprovalTypeDB  가져오기
     *
     * @param outProcessCode String
     * @param gwCompanyCd CompanyCd
     * @return DuzonApprovalTypeDB
     */
    DuzonApprovalTypeDB selectDuzonApprovalTypeByGwCompanyCd(@Param("outProcessCode") String outProcessCode, @Param("gwCompanyCd") String gwCompanyCd);


    /**
     * 지출결의 대상 품의 목록 조회
     *
     * @param param ApprovalParam
     * @return List<ApprovalCommon>
     *
     */
    List<ApprovalCommon> selectApprovalInvoiceReadyList(@Param("param") ApprovalParam param);


//    List<CryptoCommon> selectCrptoCommonList(@Param("approKey") String approKey, @Param("docId") String docId);
    List<CryptoCommon> selectCrptoCommonList(@Param("approKey") String approKey, @Param("docId") String docId);
}
