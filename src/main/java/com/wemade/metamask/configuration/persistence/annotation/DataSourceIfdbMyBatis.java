package com.wemade.metamask.configuration.persistence.annotation;

import java.lang.annotation.*;

/**
 * User: Minseon Lee (minseon@wemade.com)
 * Date: 2022-05-02
 * Time: 오후 3:26
 */
@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataSourceIfdbMyBatis {
}
