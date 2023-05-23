package com.wemade.metamask.configuration.persistence;

import com.wemade.WinterPlatform;
import com.wemade.configuration.persistence.WinterDataSourceBuilder;
import com.wemade.configuration.persistence.mybatis.WinterMyBatisFactoryBeanBuilder;
import com.wemade.configuration.persistence.mybatis.WinterMyBatisProperties;
import com.wemade.metamask.configuration.persistence.annotation.DataSourceBlockchainMybatis;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

/**
 * User: Sewon Yoo (ysw@wemade.com)
 * Date: 2022. 01. 28.
 * Time: 오후 3:12
 */
@Slf4j
@Configuration
@MapperScan(
        basePackageClasses = {WinterPlatform.class },
        annotationClass = DataSourceBlockchainMybatis.class,
        sqlSessionFactoryRef = "sqlSessionFactoryBeanBlockchainMyBatis"
)
public class BlockchainDataSourceConfig {

    @Bean
    @ConfigurationProperties(prefix = "winter.datasource.blockchain-mybatis")
    public WinterMyBatisProperties dataSourcePropertiesBlockchainMyBatis() {
        return new WinterMyBatisProperties();
    }

    @Bean
    public DataSource dataSourceBlockchainMyBatis() {
        return new WinterDataSourceBuilder(dataSourcePropertiesBlockchainMyBatis()).build();
    }

    @Bean
    public SqlSessionFactoryBean sqlSessionFactoryBeanBlockchainMyBatis() throws Exception {
        return new WinterMyBatisFactoryBeanBuilder(dataSourcePropertiesBlockchainMyBatis())
                .setDataSource(dataSourceBlockchainMyBatis())
                .build();
    }

    @Bean(name="transactionManagerBlockchainMyBatis")
    public PlatformTransactionManager transactionManagerBlockchainMyBatis() {
        return new DataSourceTransactionManager(dataSourceBlockchainMyBatis());
    }

}
