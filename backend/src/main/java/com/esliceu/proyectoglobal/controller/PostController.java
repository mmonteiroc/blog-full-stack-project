package com.esliceu.proyectoglobal.controller;

import com.esliceu.proyectoglobal.entity.Post;
import com.esliceu.proyectoglobal.entity.Usuario;
import com.esliceu.proyectoglobal.manager.PostManager;
import com.esliceu.proyectoglobal.manager.TokenManager;
import com.esliceu.proyectoglobal.manager.UsuarioManager;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Code created by: mmonteirocl
 * Email: miguelmonteiroclaveri@gmail.com
 * Date: 19/02/2020
 * Package: com.esliceu.proyectoglobal.controller
 * Project: BackEnd
 */
@RestController
public class PostController {

    @Autowired
    private PostManager postManager;

    @Autowired
    private TokenManager tokenManager;


    /*
     * Get all de todos los posts
     * */
    @GetMapping("/post")
    public List<Post> getAll() {
        return postManager.findAll();
    }

    /*
     * Get by ID
     * */
    @GetMapping("/post/{id}")
    @Transactional
    public Post getById(@PathVariable("id") Long id, HttpServletResponse response) {
        Post post = postManager.findById(id);
        if (post == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
        return post;
    }

    /*
     * Get all de todos los posts
     * */
    @GetMapping("/p/post")
    @Transactional
    public List<Post> privateGetAll() {
        return postManager.findAll();
    }

    /*
     * Get by ID
     * */
    @GetMapping("/p/post/{id}")
    @Transactional
    public Post privateGetById(@PathVariable("id") Long id, HttpServletResponse response, HttpServletRequest request) {
        Post post = postManager.findById(id);
        if (post == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
        return post;
    }



    /*
     * Delete de un post
     * */
    @DeleteMapping("/p/post/{id}")
    @Transactional
    public ResponseEntity<String> delete(@PathVariable("id") Long id, HttpServletRequest request) {

        Post post = postManager.findById(id);
        if (post == null) {
            return new ResponseEntity<>("Post con id: " + id + " no existe, o ID erroneo", HttpStatus.BAD_REQUEST);
        }

        /*
         * Comprobamos que el usuario es el propietario del post
         * */
        String token = request.getHeader("Authorization");
        token = token.replace("Bearer ", "");

        Usuario usuario = tokenManager.getUsuariFromToken(token);

        if (!post.getUsuario().equals(usuario) && usuario != null) {
            return new ResponseEntity<>("No eres el propietario de dicho post", HttpStatus.FORBIDDEN);
        }

        postManager.delete(post);
        return new ResponseEntity<>("Se ha borrado correctamente", HttpStatus.OK);
    }


    /*
     * Create post
     * */
    @PostMapping("/p/post")
    @Transactional
    public ResponseEntity<String> create(@RequestBody String json, HttpServletRequest request) {

        System.out.println(json);
        Post post = postManager.fromJsonCreate(json);
        System.out.println("ESTE ES EL POST: " + post);
        // Se comprueban que los campos obligatorios no estén vacios.
        if (post.getTituloOriginal() == null || post.getTituloTraducido() == null ||
                post.getContenidoOriginal() == null || post.getContenidoTraducido() == null ||
                post.getCreacion() == null || post.getIdiomaTraducido() == null) {
            return new ResponseEntity<>("Error en los campos del post.", HttpStatus.BAD_REQUEST);
        }

        String token = request.getHeader("Authorization");
        token = token.replace("Bearer ", "");
        Usuario usuario = tokenManager.getUsuariFromToken(token);
        if (usuario == null) return new ResponseEntity<>("Usuario no valido", HttpStatus.BAD_REQUEST);
        post.setUsuario(usuario);
        postManager.create(post);

        return new ResponseEntity<>("Post guardado correctamente", HttpStatus.OK);
    }

    /*
     * Modify post
     * */
    @PutMapping("/p/post")
    @Transactional
    public ResponseEntity<String> modify(@RequestBody String json, HttpServletRequest request) {

        Post post = postManager.fromJsonUpdate(json);

        // Se comprueba que exista un id ya que si no existe no se puede modificar.
        if (post.getIdpost() == null) {
            return new ResponseEntity<>("No se ha recibido ID o se ha recibido un ID inexistente.", HttpStatus.BAD_REQUEST);
        }


        /*
         * Comprobamos que el usuario es el propietario del post
         * */

        String token = request.getHeader("Authorization");

        token = token.replace("Bearer ", "");
        Usuario usuario = tokenManager.getUsuariFromToken(token);

        if (!post.getUsuario().equals(usuario) && usuario != null) {

            return new ResponseEntity<>("No eres el propietario de dicho post", HttpStatus.FORBIDDEN);
        }

        postManager.update(post);

        return new ResponseEntity<>(HttpStatus.OK);
    }




}