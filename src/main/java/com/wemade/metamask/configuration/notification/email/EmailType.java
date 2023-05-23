package com.wemade.metamask.configuration.notification.email;

import com.wemade.infrastructure.email.WinterEmailType;

/**
 * User: Sewon Yoo (ysw@wemade.com)
 * Date: 2022. 01. 25.
 * Time: 오후 4:31
 */
public enum EmailType implements WinterEmailType {
	COMMON("email/common"),
	ERROR("email/error");

	private final String viewName;

	EmailType(String viewName) {
		this.viewName = viewName;
	}

	@Override
	public Enum get() {
		return this;
	}

	@Override
	public String getViewName() {
		return this.viewName;
	}
}
