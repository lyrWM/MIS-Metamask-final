package com.wemade.metamask.application.duzonapproval.service.impl;

import com.wemade.infrastructure.utils.ObjectUtils;
import com.wemade.infrastructure.utils.PreconditionUtils;
import com.wemade.metamask.application.duzonapproval.dao.DuzonApprovalMapper;
import com.wemade.metamask.application.duzonapproval.domain.CryptoCommon;
import com.wemade.metamask.application.duzonapproval.domain.DuzonApprovalTypeDB;
import com.wemade.metamask.application.duzonapproval.service.DuzonApprovalService;
import com.wemade.metamask.application.duzonapproval.domain.ApprovalCommon;
import com.wemade.metamask.application.duzonapproval.domain.ApprovalParam;
import com.wemade.metamask.application.login.service.LoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * User: Sewon Yoo (ysw@wemade.com)
 * Date: 2022. 01. 24.
 * Time: 오후 1:12
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DuzonApprovalServiceImpl implements DuzonApprovalService {

    private final DuzonApprovalMapper duzonApprovalMapper;
    private final LoginService loginService;


    @Override
    public DuzonApprovalTypeDB selectDuzonApprovalTypeByOutProcessCode(String outProcessCode) {
        PreconditionUtils.invalidCondition(StringUtils.isEmpty(outProcessCode), "outProcessCode 가 없습니다.");
        DuzonApprovalTypeDB duzonApprovalTypeDB = duzonApprovalMapper.selectDuzonApprovalTypeByGwCompanyCd(outProcessCode, loginService.getLoginUser().getGwCompanyCd());
        PreconditionUtils.invalidCondition(ObjectUtils.isEmpty(duzonApprovalTypeDB), "전자결재 기준관리에 등록된 데이터가 없습니다.");
        return duzonApprovalTypeDB;
    }


    @Override
    public List<ApprovalCommon> selectApprovalInvoiceReadyListWithDocId(ApprovalParam param) {

        // 검색조건에 따라 param 값 세팅
        if (ObjectUtils.isNotEmpty(param.getSearchType())) {
            switch (param.getSearchType()) {
                // 품의제목
                case "DOC_TITLE":
                    param.setDocTitle(param.getSearchText());
                    break;

                // 품의번호
                case "DOC_NO":
                    param.setDocNo(param.getSearchText());
                    break;

                default:
                    break;
            }
        }

        // 품의유형 검색조건이 있는 경우 getApprovalTypeDB 품의 전체 정보 세팅
        if (ObjectUtils.isNotEmpty(param.getApprovalTypeDB())) {
            param.setApprovalTypeDBList(null);
            param.setApprovalTypeDB(param.getApprovalTypeDB());
        }

        return duzonApprovalMapper.selectApprovalInvoiceReadyList(param);
    }


    @Override
    public List<CryptoCommon> selectCryotoCommonListByApproKey(String approKey, String docId) {
        if (approKey == null && docId == null) {
            System.out.println("null 들어옴");
            return duzonApprovalMapper.selectCrptoCommonList(approKey, docId);
        }

        return duzonApprovalMapper.selectCrptoCommonList(approKey, docId);
    }


}
