package com.esliceu.proyectoglobal.manager;

import com.esliceu.proyectoglobal.entity.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

/**
 * Code created by: mmonteirocl
 * Email: miguelmonteiroclaveri@gmail.com
 * Date: 19/02/2020
 * Package: com.esliceu.proyectoglobal.manager
 * Project: BackEnd
 */
@Service
public class TokenManager {
    @Autowired
    private Environment environment;
    @Autowired
    private UsuarioManager usuarioManager;
    // TODO Preguntar a joan, si se puede llamar en horizontal

    public Claims getClaims(String token) {
        Claims claims = null;
        try {
            claims = Jwts.parser()
                    .setSigningKey(environment.getProperty("jwt.secret").getBytes())
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return claims;
    }
    public boolean validateToken(String token){
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(environment.getProperty("jwt.secret").getBytes())
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public Usuario getUsuariFromToken(String token) {

        try {
            Claims claims = getClaims(token);
            Long idTokenUsuario = Long.parseLong(claims.get("idusuario").toString());
            return usuarioManager.findById(idTokenUsuario);
        } catch (Exception e) {
            return null;
        }
    }
}
