package com.i.information.config;

import com.i.information.proxy.MainInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Auther: peng
 * @Date: 2021/12/13 0013 10:58
 * @Description:
 */
@Component
public class WebConfig implements WebMvcConfigurer {

    @Bean
    public MainInterceptor getMainInterceptor(){
        return new MainInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(getMainInterceptor())
                .addPathPatterns("/**");
    }
}
