package com.wemade.metamask.application.login.dao;

import com.wemade.infrastructure.constants.YN;
import com.wemade.metamask.application.base.domain.Company;
import com.wemade.metamask.configuration.persistence.annotation.DataSourceIfdbMyBatis;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * User: Sewon Yoo (ysw@wemade.com)
 * Date: 2022-06-20
 * Time: 오전 11:09
 */
@DataSourceIfdbMyBatis
@Repository
public interface LoginMapper {

    /**
     * 청구내역 카운트
     *
     * @param gwCompanyCd String
     * @return int
     */
     YN selectAdUseYn(@Param("gwCompanyCd") String gwCompanyCd);

     /**
     * 내 회사 정보 LIst
     *
     * @param empSeq String
     * @return List<Company>
     */
    List<Company> selectMyCompanyList(String empSeq);
}
