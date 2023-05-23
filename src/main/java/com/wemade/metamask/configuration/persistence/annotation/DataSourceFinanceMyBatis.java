package com.wemade.metamask.configuration.persistence.annotation;

import java.lang.annotation.*;

/**
 * User: Sewon Yoo (ysw@wemade.com)
 * Date: 2022. 01. 25.
 * Time: 오후 12:08
 */
@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataSourceFinanceMyBatis {
}
