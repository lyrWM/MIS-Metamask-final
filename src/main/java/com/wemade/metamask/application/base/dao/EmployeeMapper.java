package com.wemade.metamask.application.base.dao;

import com.wemade.metamask.application.base.domain.EmployeeInfo;
import com.wemade.metamask.configuration.persistence.annotation.DataSourceIfdbMyBatis;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * User: Minseon Lee (minseon@wemade.com)
 * Date: 2022-04-27
 * Time: 오후 12:26
 */
@DataSourceIfdbMyBatis
@Repository
public interface EmployeeMapper {

    /**
     * 사원 정보 조회 (로그인아이디, GW회사코드)
     *
     * @param loginId String
     * @return EmployeeInfo
     */
    EmployeeInfo selectByLoginId(@Param("loginId") String loginId, @Param("gwCompanyCd") String gwCompanyCd);
}
