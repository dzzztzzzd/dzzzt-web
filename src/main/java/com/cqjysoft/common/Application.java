package com.cqjysoft.common;

import java.util.concurrent.TimeUnit;

import javax.servlet.MultipartConfigElement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.HttpStatus;

@SpringBootApplication
@ComponentScan({"com.cqjysoft"})
@ServletComponentScan
@EntityScan("com.cqjysoft")
@EnableJpaRepositories("com.cqjysoft")
public class Application extends SpringBootServletInitializer implements EmbeddedServletContainerCustomizer{
    public static void main(String[] args){
        SpringApplication.run(Application.class,args);
    }
	@Override
	public void customize(ConfigurableEmbeddedServletContainer container) {
		container.addErrorPages(new ErrorPage(HttpStatus.UNAUTHORIZED,"/401.html"));
		container.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND,"/404.html"));
		container.addErrorPages(new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR,"/500.html"));
        container.setSessionTimeout(10, TimeUnit.MINUTES);
	}
	@Bean
	MultipartConfigElement multipartConfigElement() {
	    MultipartConfigFactory factory = new MultipartConfigFactory();
	    factory.setMaxFileSize("10MB");
	    factory.setLocation("D:\\temp");
	    return factory.createMultipartConfig();
	}
	//maven-war-plugin
	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(this.getClass());
    }
}