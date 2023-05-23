package com.wemade.metamask.configuration.persistence;

import com.wemade.WinterPlatform;
import com.wemade.configuration.persistence.WinterDataSourceBuilder;
import com.wemade.configuration.persistence.mybatis.WinterMyBatisFactoryBeanBuilder;
import com.wemade.configuration.persistence.mybatis.WinterMyBatisProperties;
import com.wemade.metamask.configuration.persistence.annotation.DataSourceFinanceMyBatis;
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
 * Date: 2022. 01. 24.
 * Time: 오후 12:06
 */
@Slf4j
@Configuration
@MapperScan(
        basePackageClasses = { WinterPlatform.class },
        annotationClass = DataSourceFinanceMyBatis.class,
        sqlSessionFactoryRef = "sqlSessionFactoryBeanFinanceMyBatis"
)
public class FinanceDataSourceConfiguration {

    @Bean
    @ConfigurationProperties(prefix = "winter.datasource.finance-mybatis")
    public WinterMyBatisProperties dataSourcePropertiesFinanceMyBatis() {
        return new WinterMyBatisProperties();
    }

    @Bean
    public DataSource dataSourceFinanceMyBatis() {
        return new WinterDataSourceBuilder(dataSourcePropertiesFinanceMyBatis()).build();
    }

    @Bean
    public SqlSessionFactoryBean sqlSessionFactoryBeanFinanceMyBatis() throws Exception {
        return new WinterMyBatisFactoryBeanBuilder(dataSourcePropertiesFinanceMyBatis())
                .setDataSource(dataSourceFinanceMyBatis())
                .build();
    }

    @Bean
    public PlatformTransactionManager transactionManagerFinanceMyBatis() {
        return new DataSourceTransactionManager(dataSourceFinanceMyBatis());
    }

}
