package com.wemade.metamask.configuration.persistence.interceptor;

import com.google.common.collect.Lists;
import com.wemade.metamask.application.common.domain.DTO;
import com.wemade.metamask.application.login.domain.LoginUser;
import com.wemade.metamask.application.login.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * User: Sewon Yoo (ysw@wemade.com)
 * Date: 2022. 03. 17.
 * Time: 오후 3:12
 */
@Slf4j
@Aspect
@Component
public class DaoInterceptor {

	private final LoginService loginService;

	@Autowired
	public DaoInterceptor(LoginService loginService) {
		this.loginService = loginService;
	}

	@Around("within(@org.springframework.stereotype.Repository *)")
	public Object daoCommonRound(ProceedingJoinPoint joinPoint) throws Throwable {

		// DTO 기본 정보 주입
		addDtoInfo(joinPoint);

		return joinPoint.proceed();
	}

	private boolean isExistsDto(Object[] args) {
		return Lists.newArrayList(args).stream().anyMatch(input -> {
			// 맵인 경우
			if (input instanceof Map<?, ?>) {
				for (Object obj : ((Map<?, ?>) input).values()) {
					if (obj instanceof DTO) {
						return true;
					}
				}
			}

			// 리스트인 경우
			else if (input instanceof List<?>) {
				for (Object obj : (List<?>) input) {
					if (obj instanceof DTO) {
						return true;
					}
				}
			}

			// DTO 를 상속한 모델인 경우
			else if (input instanceof DTO) {
				return true;
			}

			return false;
		});
	}

	private void addDtoInfo(ProceedingJoinPoint joinPoint) {
		ExpressionParser parser = new SpelExpressionParser();

		Object[] args = joinPoint.getArgs();
		if (ArrayUtils.isEmpty(args)) {
			return;
		}

		if (!isExistsDto(args)) {
			return;
		}

		LoginUser loginUser = loginService.getLoginUser();
		if (Objects.isNull(loginUser)) {
			return;
		}

		for (Object rootObject : args) {
			// 맵인 경우
			if (rootObject instanceof Map<?, ?>) {
				for (Object itemObject : ((Map<?, ?>) rootObject).values()) {
					if (itemObject instanceof DTO) {
						setDtoInfo(parser, itemObject, loginUser);
					}
				}
			}

			// 리스트인 경우
			else if (rootObject instanceof List<?>) {
				for (Object itemObject : (List<?>) rootObject) {
					if (itemObject instanceof DTO) {
						setDtoInfo(parser, itemObject, loginUser);
					}
				}
			}

			// DTO 를 상속한 모델인 경우
			else if (rootObject instanceof DTO) {
				setDtoInfo(parser, rootObject, loginUser);
			}
		}
	}

	private void setDtoInfo(ExpressionParser parser, Object item, LoginUser loginUser) {
		StandardEvaluationContext context = new StandardEvaluationContext(item);

		if (StringUtils.isEmpty((String) parser.parseExpression("empSeq").getValue(context))) {
			parser.parseExpression("empSeq").setValue(context, loginUser.getEmpSeq());
		}

		if (StringUtils.isEmpty((String) parser.parseExpression("userId").getValue(context))) {
			parser.parseExpression("userId").setValue(context, loginUser.getUserId());
		}

		if (StringUtils.isEmpty((String) parser.parseExpression("gwCompanyCd").getValue(context))) {
			parser.parseExpression("gwCompanyCd").setValue(context, loginUser.getGwCompanyCd());
		}

		if (StringUtils.isEmpty((String) parser.parseExpression("erpEmpNo").getValue(context))) {
			parser.parseExpression("erpEmpNo").setValue(context, loginUser.getErpEmpNo());
		}

		if (StringUtils.isEmpty((String) parser.parseExpression("erpCompanyCd").getValue(context))) {
			parser.parseExpression("erpCompanyCd").setValue(context, loginUser.getErpCompanyCd());
		}

		if (StringUtils.isEmpty((String) parser.parseExpression("regId").getValue(context))) {
			parser.parseExpression("regId").setValue(context, loginUser.getUserId());
		}

		if (StringUtils.isEmpty((String) parser.parseExpression("updId").getValue(context))) {
			parser.parseExpression("updId").setValue(context, loginUser.getUserId());
		}


	}
}
