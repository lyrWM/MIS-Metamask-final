package com.wemade.metamask.configuration;

import com.wemade.configuration.WinterWebMvcConfigurationSupport;
import com.wemade.metamask.configuration.persistence.interceptor.LoginInterceptor;
import com.wemade.metamask.configuration.persistence.interceptor.ServiceVariableInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

/**
 * User: Sewon Yoo (ysw@wemade.com)
 * Date: 2022. 01. 24.
 * Time: 오후 12:03
 */
@Slf4j
@Configuration
public class MetamaskWebConfiguration extends WinterWebMvcConfigurationSupport {

    private static final String[] EXCLUDE_RESOURCE_PATH_PATTERNS = {
            "/css/**",
            "/images/**",
            "/js/**",
            "/webfonts/**",
            "/ui-script/**",
            "/webjars/**"
    };

    public MetamaskWebConfiguration() {
        log.debug("--> Sample WebConfiguration start.");
    }

    @Override
    protected void addProjectInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor()).excludePathPatterns(EXCLUDE_RESOURCE_PATH_PATTERNS);
        registry.addInterceptor(serviceVariableInterceptor()).excludePathPatterns(EXCLUDE_RESOURCE_PATH_PATTERNS);
    }

    @Bean
    public LoginInterceptor loginInterceptor() {
        return new LoginInterceptor();
    }

    @Bean
    public ServiceVariableInterceptor serviceVariableInterceptor() {
        return new ServiceVariableInterceptor();
    }

}
