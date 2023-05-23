package com.wemade.metamask.application.base.service;

import com.wemade.metamask.application.base.domain.Company;

import java.util.List;

/**
 * User: Sewon Yoo (ysw@wemade.com)
 * Date: 2022. 05. 17.
 * Time: 오후 2:31
 */
public interface CompanyService {
    /**
     * 더존 코드 조회
     *
     * @param
     * @return List<Company>
     */
    List<Company> selectCompanyList();
}
