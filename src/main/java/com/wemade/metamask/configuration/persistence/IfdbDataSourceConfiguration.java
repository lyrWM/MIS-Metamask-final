package com.wemade.metamask.configuration.persistence;

import com.wemade.WinterPlatform;
import com.wemade.configuration.persistence.WinterDataSourceBuilder;
import com.wemade.configuration.persistence.mybatis.WinterMyBatisFactoryBeanBuilder;
import com.wemade.configuration.persistence.mybatis.WinterMyBatisProperties;
import com.wemade.metamask.configuration.persistence.annotation.DataSourceIfdbMyBatis;
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
 * User: Minseon Lee (minseon@wemade.com)
 * Date: 2022-05-02
 * Time: 오후 4:54
 */
@Slf4j
@Configuration
@MapperScan(
        basePackageClasses = {WinterPlatform.class },
        annotationClass = DataSourceIfdbMyBatis.class,
        sqlSessionFactoryRef = "sqlSessionFactoryBeanIfdbMyBatis"
)
public class IfdbDataSourceConfiguration {

        @Bean
        @ConfigurationProperties(prefix = "winter.datasource.ifdb-mybatis")
        public WinterMyBatisProperties dataSourcePropertiesIfdbMyBatis() {
                return new WinterMyBatisProperties();
        }

        @Bean
        public DataSource dataSourceIfdbMyBatis() {
                return new WinterDataSourceBuilder(dataSourcePropertiesIfdbMyBatis()).build();
        }

        @Bean
        public SqlSessionFactoryBean sqlSessionFactoryBeanIfdbMyBatis() throws Exception {
                return new WinterMyBatisFactoryBeanBuilder(dataSourcePropertiesIfdbMyBatis())
                        .setDataSource(dataSourceIfdbMyBatis())
                        .build();
        }

        @Bean(name="transactionManagerIfdbMyBatis")
        public PlatformTransactionManager transactionManagerIfdbMyBatis() {
                return new DataSourceTransactionManager(dataSourceIfdbMyBatis());
        }

}
