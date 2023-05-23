package com.wemade.metamask;

import com.wemade.WinterPlatform;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

/**
 * User: Sewon Yoo (ysw@wemade.com)
 * Date: 2022. 01. 21.
 * Time: 오후 5:28
 */
@Slf4j
@SpringBootApplication(scanBasePackageClasses = { WinterPlatform.class, BasePackage.class })
public class MetamaskWebApplication extends SpringBootServletInitializer {

	@Value("${winter.ping-message:not-found}")
	private String pingMessage;

	public static void main(String[] args) {
		SpringApplication.run(MetamaskWebApplication.class, args);
	}

	@Bean
	public CommandLineRunner runner(ApplicationContext applicationContext) {
		return (args) -> {
			log.info("Winter Platform Application : {}", pingMessage);
		};
	}

}
