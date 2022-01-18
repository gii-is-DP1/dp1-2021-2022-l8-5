package org.springframework.dwarf.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.datetime.standard.DateTimeFormatterRegistrar;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;


public class WebConfig implements WebMvcConfigurer {
	
	@Autowired
	GenericIdToEntityConverter idToEntityConverter;
	
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(idToEntityConverter);
        
        DateTimeFormatterRegistrar registrar = new DateTimeFormatterRegistrar();
    	registrar.setUseIsoFormat(true);
        registrar.registerFormatters(registry);
    }
    
    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/jsp/");
        resolver.setSuffix(".jsp");
        resolver.setViewClass(JstlView.class);
        registry.viewResolver(resolver);
    }
}