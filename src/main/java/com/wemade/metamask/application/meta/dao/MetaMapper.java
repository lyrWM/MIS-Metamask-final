package com.wemade.metamask.application.meta.dao;

import com.wemade.metamask.application.meta.domain.MetaParam;
import com.wemade.metamask.configuration.persistence.annotation.DataSourceBlockchainMybatis;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * User: Sewon Yoo (ysw@wemade.com)
 * Date: 2022-06-24
 * Time: 오전 10:43
 */
@DataSourceBlockchainMybatis
@Repository
public interface MetaMapper {

    /**
     * MetaMask 전송로그 insert
     *
     */
    void insertTransferLog(@Param("param") MetaParam param);


}
