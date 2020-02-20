package com.esliceu.proyectoglobal.filter;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created By: miguelaarrones
 * Date: 20/02/2020
 * Project: proyectoglobal
 * Package: com.esliceu.proyectoglobal.filter
 * GitHub: https://github.com/miguelaarrones
 */
public class TokenFilter implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        /*
         * TODO Implementar el filtro para los token.
         * */
        return false;
    }
}
