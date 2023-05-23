package com.wemade.metamask.configuration.persistence.transaction;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
* User: Sewon Yang (bleunoir@wemade.com)
* Date: 2022-04-27
* Time: 오후 4:48
**/
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
@Transactional(value = TransactionType.BLOCKCHAIN, propagation = Propagation.REQUIRED, rollbackFor={ Exception.class })
public @interface TransactionBlockChainMyBatis {
}
