package com.esliceu.proyectoglobal.filter;

import com.esliceu.proyectoglobal.manager.TokenManager;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private TokenManager tokenManager;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (request.getMethod().equals("OPTIONS")) return true;


        String authorization = request.getHeader("Authorization");

        if (authorization != null && !authorization.isEmpty()) {
            String token = authorization.replace("Bearer ", "");

            boolean valid = tokenManager.validateToken(token);
            if (!valid) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return false;
            } else {
                return true;
            }
        }
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        return false;
    }
}
