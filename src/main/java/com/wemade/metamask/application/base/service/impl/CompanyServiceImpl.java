package com.wemade.metamask.application.base.service.impl;

import com.wemade.metamask.application.base.dao.CompanyMapper;
import com.wemade.metamask.application.base.domain.Company;
import com.wemade.metamask.application.base.service.CompanyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * User: Sewon Yoo(ysw@wemade.com)
 * Date: 2022-05-17
 * Time: 오후 10:15
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {
    private final CompanyMapper companyMapper;

    @Override
    public List<Company> selectCompanyList() {
        return companyMapper.list();
    }
}
