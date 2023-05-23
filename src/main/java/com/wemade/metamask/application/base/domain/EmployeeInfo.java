package com.wemade.metamask.application.base.domain;

import com.wemade.infrastructure.constants.YN;
import com.wemade.metamask.application.common.domain.DTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.apache.ibatis.type.Alias;

/**
* User: Sewon Yang (bleunoir@wemade.com)
* Date: 2022-04-02
* Time: 오전 11:28
**/

@Alias(value = "EmployeeInfo")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class EmployeeInfo extends DTO {
    private String empNo;                   // 사번
    private String empName;                 // 사원
    private String loginId;                 // 로그인ID
    private String eMail;                   // 이메일
    private String outMail;                 // 외부이메일

    private String groupSeq;                // 그룹 시퀀스
    private String bizSeq;                  // 사업장 시퀀스
    private String bizName;                 // 사업장
    private String companyName;             // 회사

    private String deptSeq;                 // 부서 시퀀스
    private String deptCd;                  // 부서 코드
    private String deptName;                // 부서
    private YN mainCompYn;                  // 메인회사여부
    private YN mainDeptYn;                  // 메인부서여부
    private String dutyCode;                // 직책 코드
    private String dutyName;                // 직책
    private String positionCode;            // 직급 코드
    private String positionName;            // 직급
    private YN useYn;                       // 사용여부
    private YN checkWorkYn;                 // 휴직여부
    private YN orgchartDisplayYn;           // 조직도노출여부
    private YN messengerDisplayYn;          // 메신저노출여부

}
