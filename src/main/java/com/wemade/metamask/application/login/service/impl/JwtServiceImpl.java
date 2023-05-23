package com.wemade.metamask.application.login.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.wemade.infrastructure.convertor.JsonConvertor;
import com.wemade.infrastructure.cookie.CookieBuilder;
import com.wemade.infrastructure.utils.PreconditionUtils;
import com.wemade.metamask.application.common.domain.Constants;
import com.wemade.metamask.application.login.domain.LoginUser;
import com.wemade.metamask.application.login.service.JwtService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

/**
 * User: Sewon Yoo (ysw@wemade.com)
 * Date: 2022. 01. 24.
 * Time: 오후 1:12
 */
@Slf4j
@Service
public class JwtServiceImpl implements JwtService {

	private static final String CLAIM_NAME_ADMIN_INFO = "LoginUser";

	@Autowired(required = false)
	private HttpServletRequest request;

	@Autowired(required = false)
	private HttpServletResponse response;

	@Override
	public String getAccessToken(LoginUser loginUser) {
		return Jwts.builder()
				.setSubject("metamask/user")
				.setId(UUID.randomUUID().toString())
				.setIssuedAt(Date.from(Instant.now()))
				.setExpiration(Date.from(Instant.now().plus(30, ChronoUnit.MINUTES)))
				.claim(CLAIM_NAME_ADMIN_INFO, JsonConvertor.toString(loginUser))
				.signWith(
						Keys.hmacShaKeyFor(Constants.ACCESS_TOKEN_KEY.getBytes(StandardCharsets.UTF_8)),
						SignatureAlgorithm.HS512
				)
				.compact();
	}

	@Override
	public LoginUser getLoginUserInfo() {
		String accessToken = new CookieBuilder(request, response)
				.setCryptKey(Constants.COOKIE_CRYPT_KEY)
				.get(Constants.COOKIE_NAME_ACCESS_TOKEN);

		if (StringUtils.isEmpty(accessToken)) {
			return null;
		}

		try {
			Jws<Claims> claimsJws = Jwts.parserBuilder()
					.setSigningKey(Keys.hmacShaKeyFor(Constants.ACCESS_TOKEN_KEY.getBytes(StandardCharsets.UTF_8)))
					.build()
					.parseClaimsJws(accessToken);

			String body = Objects.toString(claimsJws.getBody().get(CLAIM_NAME_ADMIN_INFO), null);
			PreconditionUtils.invalidCondition(Objects.isNull(body), "AccessToken의 정보가 없습니다.");

			return JsonConvertor.toObject(body, new TypeReference<LoginUser>(){});
		}
		catch (ExpiredJwtException e) {
			log.info(e.getMessage());
			return null;
		}
	}
}
