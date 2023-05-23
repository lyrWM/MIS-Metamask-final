package com.wemade.metamask.configuration.persistence.interceptor;

import com.wemade.infrastructure.email.EmailPriorityType;
import com.wemade.infrastructure.email.EmailSendBuilder;
import com.wemade.infrastructure.email.EmailSendProvider;
import com.wemade.infrastructure.exception.MessageException;
import com.wemade.infrastructure.properties.PropertiesProvider;
import com.wemade.infrastructure.utils.ObjectUtils;
import com.wemade.metamask.application.login.domain.LoginUser;
import com.wemade.metamask.application.login.service.LoginService;
import com.wemade.metamask.configuration.notification.email.EmailType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import javax.crypto.IllegalBlockSizeException;

/**
 * User: Sewon Yoo (ysw@wemade.com)
 * Date: 2022. 07. 06.
 * Time: 오후 3:11
 */
@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class MailExceptionInterceptor {

    private final EmailSendProvider emailSendProvider;
    private final LoginService loginService;
    private final PropertiesProvider propertiesProvider;

    @AfterThrowing(pointcut = "execution(* com.wemade.metamask.application..*.*(..))", throwing = "exception")
    public void mailAfterThrowing(Throwable exception) throws Exception {
        if (propertiesProvider.isProduction()) {
            LoginUser loginUser = loginService.getLoginUser();

            if (!exception.getClass().equals(IllegalBlockSizeException.class) && !exception.getClass().equals(MessageException.class)) {

                StringBuilder errorMessage = new StringBuilder();

                if (ObjectUtils.isNotEmpty(loginUser)) {
                    errorMessage.append("* 에러 대상자 : <br><br>gw_company_cd(").append(loginUser.getGwCompanyCd()).append("), emp_seq(").append(loginUser.getEmpSeq()).append("), emp_no(").append(loginUser.getErpEmpNo()).append("), emp_nm(").append(loginUser.getUserNm()).append(")<br><br><br>");
                }

                if (ObjectUtils.isEmpty(exception.getCause())) {
                    errorMessage.append("* 상세로그 : <br><br>");
                } else {
                    errorMessage.append("* Cause by : <br><br>").append(exception.getCause()).append("<br><br><br>* 상세로그 : <br><br>");
                }
                for (StackTraceElement element : exception.getStackTrace()) {
                    errorMessage.append(element.toString()).append("<br>");
                }

                EmailSendBuilder emailSendBuilder = new EmailSendBuilder(EmailType.ERROR)
                        .setToName("경영정보팀")
                        .setToEmail("mis@wemade.com")
                        .setSubject("[MIS-FINANCE] " + exception.getClass() + " 에러 발생")
                        .setPriorityType(EmailPriorityType.HIGH)
                        .setContents(errorMessage.toString());

                emailSendProvider.send(emailSendBuilder);
            }
        }
    }

}
