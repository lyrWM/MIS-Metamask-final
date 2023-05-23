package com.wemade.metamask.application.duzonapproval.domain;

import com.wemade.metamask.application.common.domain.DTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.ibatis.type.Alias;

import java.sql.Date;

/**
 * User: Minseon Lee (minseon@wemade.com)
 * Date: 2022-05-04
 * Time: 오후 5:55
 */
@Alias(value="ApprovalCommon")
@Data
@EqualsAndHashCode(callSuper = true)
public class ApprovalCommon extends DTO {
    private DuzonApprovalGroupType approvalGroupType;   // 전자결재 그룹 타입 (전자결재를 구분하는 대분류 개념)
    private String approvalGroupSubType;  		        // 전자결재 그룹 서브 타입 (전자결재를 구분하는 소분류 개념)
    private String approvalName;		                // 전자결재 신청서 명
    private String gwCompanyCd;                         // 회사코드(gwCompanyCd)
    private Integer approvalSeq;		                // 품의 시퀀스
    private Integer expenseSeq;		                    // 비용 시퀀스

    private String userId;				                // 신청자ID
    private String empSeq;				                // 신청자Seq
    private String userNm;				                // 신청자 이름
    private String approKey;			                // 전자결재 신청서 PK
    private String outProcessCode;                      // 전자결재 신청서 outProcessCode
    private String formId;                              // 전자결재 신청서 formId
    private String docId;                               // 전자결재 신청서 문서 ID
    private String docNo;				                // 전자결재 문서 번호
    private String docSts;				                // 전자결재 문서 상태 코드
    private String docStsName;			                // 전자결재 문서 상태명
    private String docTitle;			                // 전자결재 문서 제목 (신청서 제목)
    private String ccCode;			                    // C/C 코드
    private String ccName;			                    // C/C 명


    private Date endDate;				                // 품의완료일자

}
