package com.wemade.metamask.configuration.persistence.annotation;

import java.lang.annotation.*;

/**
* User: Sewon Yang (bleunoir@wemade.com)
* Date: 2022-04-27
* Time: 오후 4:48
**/
@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataSourceBlockchainMybatis {
}
