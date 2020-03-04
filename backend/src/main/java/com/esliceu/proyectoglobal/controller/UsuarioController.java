package com.esliceu.proyectoglobal.controller;

import com.esliceu.proyectoglobal.entity.Post;
import com.esliceu.proyectoglobal.entity.Usuario;
import com.esliceu.proyectoglobal.manager.TokenManager;
import com.esliceu.proyectoglobal.manager.UsuarioManager;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

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

    @Autowired
    private Gson gson;

    @GetMapping("/p/user")
    @Transactional
    public List<Usuario> getAll() {
        return usuarioManager.findAll();
    }

    @GetMapping("/p/user/{id}")
    @Transactional
    public Usuario get(@PathVariable("id") Long id) {
        return usuarioManager.findById(id);
    }
    @GetMapping("/p/me")
    @Transactional
    public Usuario getMySelf(HttpServletRequest request) {

        String token = request.getHeader("Authorization");
        token = token.replace("Bearer ", "");

        return tokenManager.getUsuariFromToken(token);
    }

    /*
    * Endpoint el cual usaremos para solamente modificar la parte de informacion personal de nuestro usuario
    * */
    @PutMapping("/p/user/perfil")
    @Transactional
    public ResponseEntity<String> modifySecurity(@RequestBody String json, HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        token = token.replace("Bearer ", "");
        Usuario usuario = tokenManager.getUsuariFromToken(token);

        if (usuario == null) {
            // Esto nunca deberia petar ya que si el handler te ha validado el token,
            // significa que esta con nuestro sercret pero por si acaso ponemos este IF
            // Por si acaso el usuario del token ha sido eliminado o algo
            return new ResponseEntity<>("No existe tu usuario", HttpStatus.UNAUTHORIZED);
        }

        JsonObject jsonObject = gson.fromJson(json, JsonObject.class);
        if (jsonObject.get("nombre") != null) {
            usuario.setNombre(jsonObject.get("nombre").getAsString());
        }
        if (jsonObject.get("apellidos") != null) {
            usuario.setApellidos(jsonObject.get("apellidos").getAsString());
        }
        if (jsonObject.get("username") != null) {
            usuario.setUsername(jsonObject.get("username").getAsString());
        }


        /*
        * Ahora mismo validamos que no lo recibamos vacio pero aqui
        * podriamos añadir las regras que queramos sobre nuestros campos.
        * Ex: minimo de caracteres, etc
        * */
        if (usuario.getNombre().equals("")
        || usuario.getApellidos().equals("")
        || usuario.getUsername().equals("")){
            return new ResponseEntity<>("No se cumplen los requisitos de los parametros", HttpStatus.BAD_REQUEST);
        }

        usuarioManager.update(usuario);

        return new ResponseEntity<>(HttpStatus.OK);
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
