package com.wemade.metamask.application.base.domain;

import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.sql.Date;

/**
 * User: Sewon Yoo(ysw@wemade.com)
 * Date: 2022-05-17
 * Time: 오후 10:15
 */
@Alias(value="Company")
@Data
public class Company {
    private String groupSeq;
    private String gwCompanyCd;
    private String erpCompanyCd;
    private String gwDomain;
    private String companyName;
    private String companyShortName;
    private String erpUse;
    private String useYn;
    private int orderNum;
    private Date regDate;
}
