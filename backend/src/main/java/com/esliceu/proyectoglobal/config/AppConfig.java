package com.esliceu.proyectoglobal.config;

import com.esliceu.proyectoglobal.filter.TokenFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Created By: miguelaarrones
 * Date: 20/02/2020
 * Project: proyectoglobal
 * Package: com.esliceu.proyectoglobal.config
 * GitHub: https://github.com/miguelaarrones
 */
@Configuration
public class AppConfig implements WebMvcConfigurer {

    @Bean
    public TokenFilter getFilter() {
        return new TokenFilter();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        /*
         * Aqui van todos los interceptors que creemos.
         * */

         //registry.addInterceptor(getFilter()).addPathPatterns("/p/**")  ;

    }
}
