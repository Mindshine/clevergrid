package com.mindshine.clevergrid.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.mindshine.clevergrid.CurrentUserHandlerMethodArgumentResolver;
import com.mindshine.clevergrid.io.PictureLoader;

@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter {

	@Autowired
	CurrentUserHandlerMethodArgumentResolver currentUserHandlerMethodArgumentResolver;

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName("index");
		registry.addViewController("/login").setViewName("login");
	}

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		argumentResolvers.add(currentUserHandlerMethodArgumentResolver);
	}

	@Bean
	public PictureLoader pictureLoader() {
		return new PictureLoader();
	}
}
