package com.esliceu.proyectoglobal.controller;

import com.esliceu.proyectoglobal.entity.Post;
import com.esliceu.proyectoglobal.entity.Usuario;
import com.esliceu.proyectoglobal.manager.TokenManager;
import com.esliceu.proyectoglobal.manager.UsuarioManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Code created by: mmonteirocl
 * Email: miguelmonteiroclaveri@gmail.com
 * Date: 18/02/2020
 * Package: com.esliceu.proyectoglobal.controller
 * Project: BackEnd
 */
@RestController
public class UsuarioController {
    @Autowired
    private UsuarioManager usuarioManager;

    @Autowired
    private TokenManager tokenManager;

    @GetMapping("/p/user")
    @Transactional
    public List<Usuario> getAll() {

        List<Usuario> users = usuarioManager.findAll();
        for (Usuario user : users) {

            // BORRAMOS LAS PASSWORDS ANTES DE RETORNAR EL OBJETO USUARIO AL CLIENTE
            user.setPassword("");
        }

        return usuarioManager.findAll();
    }

    @GetMapping("/p/user/{id}")
    @Transactional
    public Usuario get(@PathVariable("id") Long id) {
        Usuario user = usuarioManager.findById(id);
        user.setPassword("");
        return user;
    }
    @GetMapping("/p/me")
    @Transactional
    public Usuario getMySelf(HttpServletRequest request) {
        /*
         * Comprobamos que el usuario es el propietario del post
         * */
        String token = request.getHeader("Authorization");
        token = token.replace("Bearer ", "");

        Usuario user = tokenManager.getUsuariFromToken(token);
        user.setPassword("");
        return user;
    }



    /*
     *
     * Este metodo como bien podemos observar nos devolvera
     * los posts del usuario con el que estemos loggeado
     *
     * Este metodo nos servira por ejemplo en la parte
     * privada de nuestra aplicacion la cual tendremos
     * una vista de nuestros posts
     * */
    @GetMapping("/p/user/my/posts")
    @Transactional
    public List<Post> getOwnPosts(HttpServletRequest request, HttpServletResponse response) {
        String token = request.getHeader("Authorization");

        token = token.replace("Bearer ", "");
        Usuario user = tokenManager.getUsuariFromToken(token);

        if (user == null) {
            try {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Token con usuario no valido");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        return user.getPosts();
    }

}
