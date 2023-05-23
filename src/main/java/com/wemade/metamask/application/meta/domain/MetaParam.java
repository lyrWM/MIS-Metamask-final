package com.wemade.metamask.application.meta.domain;

import com.wemade.metamask.application.common.domain.DTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.apache.ibatis.type.Alias;

/**
 * User: Sewon Yoo (ysw@wemade.com)
 * Date: 2022-06-24
 * Time: 오전 10:43
 */
@Alias(value="MetaParam")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class MetaParam extends DTO {
    private String receipt;
    private String docId;
    private String formId;
    private String resultTime;
    private String amount;
    private String amountHex;

}
