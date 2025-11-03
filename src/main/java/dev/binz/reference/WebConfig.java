package dev.binz.reference;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import dev.binz.reference.Interceptor.CustomInterceptor;
import dev.binz.reference.filter.CustomFilter;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new CustomInterceptor()).addPathPatterns("/users/*");
    }
    
    /**
     * @return
     * Configuration 만 있으면됨
     */
    @Bean
    public FilterRegistrationBean<CustomFilter> validationFilter() {
        FilterRegistrationBean<CustomFilter> registrationBean = new FilterRegistrationBean<CustomFilter>();
        registrationBean.setFilter(new CustomFilter());
        registrationBean.addUrlPatterns("/users/*");
        return registrationBean;
    }
}
