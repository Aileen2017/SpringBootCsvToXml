package com.springboot.csvtoxmlproject;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

@Configuration
@EnableWebMvc 
// Optionally setup Spring MVC defaults (if you aren't using
// Spring Boot & haven't specified @EnableWebMvc elsewhere)
public class SpringBootMvcConfiguration extends WebMvcConfigurerAdapter {


	@Bean(name="simpleMappingExceptionResolver")
	public SimpleMappingExceptionResolver
	createSimpleMappingExceptionResolver() {
		SimpleMappingExceptionResolver r =
				new SimpleMappingExceptionResolver();

		Properties mappings = new Properties();		
		r.setExceptionMappings(mappings);  // None by default
		r.setDefaultErrorView("error");    // No default
		r.setExceptionAttribute("exception");  
		//  r.setWarnLogCategory("example.MvcLogger");     // No default
		return r;
	}

	@Bean(name="extendedSimpleMappingExceptionResolver")
	public ExtendedSimpleMappingExceptionResolver 
	createExtendedSimpleMappingExceptionResolver() {
		ExtendedSimpleMappingExceptionResolver extendedSimpleMappingExceptionResolver 
		= new ExtendedSimpleMappingExceptionResolver();

		Properties mappings = new Properties();
		mappings.setProperty("EmptyFileException", "myerror");
		extendedSimpleMappingExceptionResolver.setExceptionMappings(mappings);
		extendedSimpleMappingExceptionResolver.setOrder(20);
		return extendedSimpleMappingExceptionResolver;

	}


}

