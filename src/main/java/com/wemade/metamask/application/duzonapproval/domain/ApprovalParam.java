package com.wemade.metamask.application.duzonapproval.domain;

import com.wemade.infrastructure.utils.ObjectUtils;
import com.wemade.metamask.application.common.domain.DTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.ibatis.type.Alias;
import org.springframework.util.CollectionUtils;

import java.sql.Date;
import java.time.YearMonth;
import java.util.List;

/**
 * User: Minseon Lee (minseon@wemade.com)
 * Date: 2022-05-04
 * Time: 오후 5:55
 */
@Alias(value="ApprovalParam")
@Data
@EqualsAndHashCode(callSuper = true)
public class ApprovalParam extends DTO {

    private String approKey;			                        // 전자결재 신청서 PK
    private String outProcessCode;		                // 품의 유형 OutProcessCode
    private DuzonApprovalTypeDB approvalTypeDB;		            // 품의 유형 DB

    private String searchType;			                        // 검색조건
    private String searchText;		                            // 검색어

    private String docId;                                       // 전재결재 문서 아이디
    private String docNo;				                        // 전자결재 문서 번호
    private String docTitle;			                        // 전자결재 문서 제목 (신청서 제목)
    private List<DuzonApprovalTypeDB> approvalTypeDBList;	    // 전자결재 신청서 구분 목록 DB

    private Date startDate;                                     // 검색시작일자
    private Date endDate;                                       // 검색종료일자


    public Date getStartDate() {
        if (ObjectUtils.isEmpty(this.startDate)) {
            this.startDate = Date.valueOf(YearMonth.now().atDay(1));
        }
        return this.startDate;
    }

    public Date getEndDate() {
        if (ObjectUtils.isEmpty(this.endDate)) {
            this.endDate = Date.valueOf(YearMonth.now().atEndOfMonth());
        }
        return this.endDate;
    }


}
