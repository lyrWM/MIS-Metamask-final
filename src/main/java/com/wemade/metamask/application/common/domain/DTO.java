package com.wemade.metamask.application.common.domain;

import com.wemade.infrastructure.constants.DB_STATUS;
import lombok.Data;

import java.util.Date;

/**
* User: Sewon Yang (bleunoir@wemade.com)
* Date: 2022-03-04
* Time: 오후 6:41
**/
@Data
public class DTO {
    private String empSeq;
    private String userId;
    private String gwCompanyCd;
    private String erpEmpNo;
    private String erpCompanyCd;


    private Date regDate;
    private String regId;
    private Date updDate;
    private String updId;
    private DB_STATUS dbStatus;
}
