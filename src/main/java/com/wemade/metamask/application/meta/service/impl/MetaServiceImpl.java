package com.wemade.metamask.application.meta.service.impl;

import com.wemade.infrastructure.utils.DateUtils;
import com.wemade.infrastructure.utils.NetUtils;
import com.wemade.infrastructure.utils.StringUtils;
import com.wemade.infrastructure.utils.WebUtils;
import com.wemade.metamask.application.login.service.LoginService;
import com.wemade.metamask.application.meta.dao.MetaMapper;
import com.wemade.metamask.application.meta.domain.MetaParam;
import com.wemade.metamask.application.meta.service.MetaService;
import com.wemade.metamask.configuration.persistence.transaction.TransactionBlockChainMyBatis;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.util.SubnetUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedWriter;
import java.io.FileWriter;

/**
 * User: Sewon Yoo (ysw@wemade.com)
 * Date: 2022. 01. 24.
 * Time: 오후 1:12
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MetaServiceImpl implements MetaService {

	private final MetaMapper metaMapper;
	private final LoginService loginService;

	@Value("${winter.storage.data-path}")
	private String STORAGE_DATA_PATH;

	@Override
	public boolean checkIp(HttpServletRequest request) {

		String[] META_IPS = {"127.0.0.1/32"};

		String ip = WebUtils.getRemoteAddr(request);

		boolean ipCheck = false;

		if (StringUtils.contains(ip, WebUtils.MULTIPLE_REQUEST_REMOTE_ADDR_SEPARATOR)) {
			ip = WebUtils.cleanupRemoteAddr(ip);
		}

		ip = NetUtils.localhostConvertIPv4(ip);

		for (String allowedIp : META_IPS) {
			SubnetUtils subnetUtils = new SubnetUtils(allowedIp);
			subnetUtils.setInclusiveHostCount(true);

			boolean isAllowed = subnetUtils.getInfo().isInRange(ip);
			if (isAllowed) {
				ipCheck = true;
			}
		}

		return ipCheck;
	}

	@TransactionBlockChainMyBatis
	@Override
	public boolean transferSubmit(MetaParam param) throws Exception {

		String resultTime = DateUtils.toString("yyyyMMddHHmmss");
		String fileName = STORAGE_DATA_PATH + "/meta/success/" + loginService.getLoginUser().getUserId() + "_" + param.getDocId() + "_" + param.getFormId() + "_" + resultTime + ".txt" ;

		BufferedWriter fw = new BufferedWriter(new FileWriter(fileName, true));

		// 파일안에 문자열 쓰기
		fw.write(param.getReceipt());
		fw.flush();

		// 객체 닫기
		fw.close();

		param.setResultTime(resultTime);
		metaMapper.insertTransferLog(param);
		return true;
	}

	@TransactionBlockChainMyBatis
	@Override
	public boolean transferError(MetaParam param) throws Exception {

		String resultTime = DateUtils.toString("yyyyMMddHHmmss");
		String fileName = STORAGE_DATA_PATH + "/meta/error/" + loginService.getLoginUser().getUserId() + "_" + param.getDocId() + "_" + param.getFormId() + "_" + resultTime + ".txt" ;

		BufferedWriter fw = new BufferedWriter(new FileWriter(fileName, true));

		// 파일안에 문자열 쓰기
		fw.write(param.getReceipt());
		fw.flush();

		// 객체 닫기
		fw.close();
		return true;
	}
}
