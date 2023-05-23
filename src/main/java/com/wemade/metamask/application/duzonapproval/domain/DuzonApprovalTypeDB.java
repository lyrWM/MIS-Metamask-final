package com.wemade.metamask.application.duzonapproval.domain;

import com.wemade.metamask.application.common.domain.DTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.apache.ibatis.type.Alias;

import java.util.List;

/**
 * User: Sewon Yoo (ysw@wemade.com)
 * Date: 2022-04-19
 * Time: 오후 15:06
 */
@Alias(value="DuzonApprovalTypeDB")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class DuzonApprovalTypeDB extends DTO {

	private Integer duzonApprovalTypeSeq;
	private DuzonApprovalGroupType groupType;  		// 전자결재 그룹 타입 (전자결재를 구분하는 대분류 개념)
	private String groupSubType;  					// 전자결재 그룹 서브 타입 (전자결재를 구분하는 소분류 개념)
	private String outProcessCode;  				// 전자결재 연동 코드
	private int formId;								// 전자결재 양식 ID
	private int sort;								// 전자결재 양식 정렬순서
	private String contentsEnc;						// 본문Html 인코더 (U : UTF-8)
	private String contentsType;					// 본문Html 종류 (inner : 더존에디터, outer : jsp include)
	private String uploadFileDelYn;					// 업로드 파일 삭제 여부
	private String approvalName;					// 신청서명
	private String note;							// 신청서 설명
	private Integer childTableCnt;
	private Integer gwCompanyCnt;


}
