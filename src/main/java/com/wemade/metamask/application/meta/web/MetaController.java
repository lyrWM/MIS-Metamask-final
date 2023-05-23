package com.wemade.metamask.application.meta.web;

import com.wemade.infrastructure.constants.RETURN_TP;
import com.wemade.infrastructure.entity.ResponseBase;
import com.wemade.infrastructure.utils.ObjectUtils;
import com.wemade.infrastructure.utils.PreconditionUtils;
import com.wemade.infrastructure.utils.StringUtils;
import com.wemade.metamask.application.duzonapproval.domain.*;
import com.wemade.metamask.application.duzonapproval.service.DuzonApprovalService;
import com.wemade.metamask.application.meta.domain.MetaParam;
import com.wemade.metamask.application.meta.service.MetaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * User: Sewon Yoo (ysw@wemade.com)
 * Date: 2022. 07. 13.
 * Time: 오후 5:04
 */
@Slf4j
@Controller
@RequiredArgsConstructor
public class MetaController {
    private final DuzonApprovalService duzonApprovalService;
    private final MetaService metaService;
    private static List<CryptoCommon> transferList;



    @RequestMapping(value={ "/", "/meta/main"})
    public ModelAndView metaMain(ModelAndView mnv,
                                 HttpServletRequest request,
                                 HttpServletResponse response,
                                 @ModelAttribute ApprovalParam param) throws Exception {


        List<DuzonApprovalTypeDB> approvalTypeDBList = new ArrayList<>();
//        approvalTypeDBList.add(duzonApprovalService.selectDuzonApprovalTypeByOutProcessCode("APRV_TKSND"));
        approvalTypeDBList.add(duzonApprovalService.selectDuzonApprovalTypeByOutProcessCode("APRV_CRYPT"));

        if (ObjectUtils.isEmpty(param)) {
            param = new ApprovalParam();
        }

        param.setApprovalTypeDBList(approvalTypeDBList);

        mnv.addObject("search", param);
        mnv.addObject("approvalTypeDBList", approvalTypeDBList);
        mnv.addObject("approvalList", duzonApprovalService.selectApprovalInvoiceReadyListWithDocId(param));

        transferList = duzonApprovalService.selectCryotoCommonListByApproKey(request.getParameter("approKey"), request.getParameter("docId"));
        mnv.addObject("cryptoList", transferList);


        mnv.setViewName("meta/main");
        return mnv;
    }

    @RequestMapping("/meta/transfer")
    public ModelAndView metaTransfer(ModelAndView mnv,
                                     HttpServletRequest request,
                                     HttpServletResponse response,
                                     @ModelAttribute MetaParam param) throws Exception {

        PreconditionUtils.invalidCondition(StringUtils.isEmpty(transferList.get(0).getDoc_id()), "관련품의를 선택 후 접근이 가능합니다.");
        PreconditionUtils.invalidCondition(StringUtils.isEmpty(transferList.get(0).getForm_id()), "관련품의를 선택 후 접근이 가능합니다.");

        mnv.addObject("transferList", transferList);

        String account_address = request.getParameter("account_address");
        mnv.addObject("metaAccount",account_address);

        mnv.addObject("param", param);
        mnv.setViewName("meta/transfer");
        return mnv;
    }

    @RequestMapping("/meta/transfer/submit")
    @ResponseBody
    public ResponseBase metaTransferSubmit(@RequestBody MetaParam param) {
        ResponseBase responseBase = new ResponseBase<String>();

        try {
            if (metaService.transferSubmit(param)) {
                responseBase.setMessage("전송이 완료되었습니다.");
                responseBase.setCode(RETURN_TP.OK);
            } else {
                responseBase.setMessage("전송이 완료되었습니다.");
                responseBase.setCode(RETURN_TP.FAIL);
            }
            return responseBase;

        } catch (Exception e) {
            try {
                if (metaService.transferError(param)) {
                    responseBase.setMessage("전송은 실패 하였으나 에러 로그를 저장하였습니다.");
                    responseBase.setCode(RETURN_TP.OK);
                } else {
                    responseBase.setMessage("전송 및 에러로그 저장 모두 실패하였습니다.");
                    responseBase.setCode(RETURN_TP.FAIL);
                }
                return responseBase;
            }catch (Exception e1) {
                log.error(e.getMessage(), e);
                responseBase.setCode(RETURN_TP.FAIL);
                responseBase.setMessage("전송 및 에러로그 저장 모두 실패하였습니다.\n" + e.getMessage());
                return responseBase;
            }
        }
    }

}
