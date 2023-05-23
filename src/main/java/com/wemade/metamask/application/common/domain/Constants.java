package com.wemade.metamask.application.common.domain;

/**
 * User: Sewon Yoo (ysw@wemade.com)
 * Date: 2022. 01. 24.
 * Time: 오후 6:29
 */
public class Constants {

	// 비동기 처리를 위한 EXCUTOR Threads 수 : Runtime.getRuntime().availableProcessors() * 2
	public static final int EXCUTOR_THREAD_COUNT = 10;

	// 비동기 처리를 위한 EXCUTOR SHUTDOWN 시간
	public static final int EXCUTOR_SHUTDOWN_TIMEOUT_SECONDS = 5;

	// 업로드 암호화 키 - AES key is always 16 characters.
	public static final String FILE_TOKEN_KEY = "dnl!ap2dl#em4Yap";

	public static final String ACCESS_TOKEN_KEY = "eG!AKGAe5psTJ3sem%@&c$aCMws6a_PrL#aSrH8e6C?gmf#AvXgk5wp%vZdSmWjr";

	public static final String COOKIE_CRYPT_KEY = "sa!mp2le#siteYap";

	public static final String COOKIE_NAME_ACCESS_TOKEN = "METAMASK_CERT";
	public static final String COOKIE_NAME_LOGIN_SAVE_ID = "METAMASK_LOGIN_SAVE_ID";

}
