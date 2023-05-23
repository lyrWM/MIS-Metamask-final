package com.wemade.metamask.application.duzonapproval.domain;
import com.wemade.metamask.application.common.domain.DTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.ibatis.type.Alias;

@Alias(value="CryptoCommon")
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
public class CryptoCommon extends DTO{
// 전송 목록 조회
    private String doc_id; // 전자결재 신청서 문서 ID
    private String form_id; // 전자결재 신청서 formId
    private String appro_key; // 전자결재 신청서 PK
    private String from_wallet_address; // 전송 보내는 지갑 주소
    private String to_wallet_address; // 전송 받는 지갑 주소
    private String currency_code; // 전송 코인 종류
    private double supply_amount; // 전송 수량
    private String reg_id; // 품의서 작성자 아이디
    private String db_status; // db 업로드 상태
}
