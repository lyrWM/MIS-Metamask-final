package com.wemade.metamask.application.base.dao;

import com.wemade.metamask.application.base.domain.Company;
import com.wemade.metamask.configuration.persistence.annotation.DataSourceIfdbMyBatis;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * User: Sewon Yoo(ysw@wemade.com)
 * Date: 2022-05-17
 * Time: 오후 10:15
 */
@DataSourceIfdbMyBatis
@Repository
public interface CompanyMapper {

    /**
     * 회사 코드 조회
     *
     * @param
     * @return List<Company>
     */
    List<Company> list();

}
