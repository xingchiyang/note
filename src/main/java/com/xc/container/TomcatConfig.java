package com.xc.container;

import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;

/**
 * 自定义tomcat容器
 * Created by yb on 2017/3/21 0021.
 */
@Configuration
public class TomcatConfig {
	@Bean
	public EmbeddedServletContainerFactory servletContainer() throws Exception {
		TomcatEmbeddedServletContainerFactory tomcat = new TomcatEmbeddedServletContainerFactory();
		return tomcat;
	}
}
