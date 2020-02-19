package com.esliceu.proyectoglobal.controller;

import com.esliceu.proyectoglobal.entity.Post;
import com.esliceu.proyectoglobal.manager.PostManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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
    public Post getById(@PathVariable("id") Long id) {
        return postManager.findById(id);
    }

    /*
     * Delete de un post
     * */
    @DeleteMapping("/post/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id, HttpServletRequest request) {

        Post post = postManager.findById(id);
        if (post == null) {
            return new ResponseEntity<>("Post con id: " + id + " no existe, o ID erroneo", HttpStatus.BAD_REQUEST);
        }

        /*
         * TODO Mirar que el usuario es el propietario de dicho post
         * */
        //String token = request.getHeader("Authorization");
        //token = token.replace("Bearer ", "");
        /*
         * TODO tenemos que crear tokenManager y pedir los claims para recibir el usuario
         * */
        if (false) { // PLACEHOLDER

            return new ResponseEntity<>("No eres el propietario de dicho post", HttpStatus.FORBIDDEN);
        }

        postManager.delete(post);
        return new ResponseEntity<>("Se ha borrado correctamente", HttpStatus.OK);
    }


    /*
     * Create post
     * */
    @PostMapping("/post")
    public ResponseEntity<String> create(@RequestBody String json) {

        Post post = postManager.fromJsonCreate(json);

        // Se comprueban que los campos obligatorios no estén vacios.
        if (post.getTituloOriginal() == null || post.getTituloTraducido() == null ||
                post.getContenidoOriginal() == null || post.getContenidoTraducido() == null ||
                post.getCreacion() == null || post.getIdiomaTraducido() == null) {
            return new ResponseEntity<>("Error en los campos del post.", HttpStatus.BAD_REQUEST);
        }

        /*
         * TODO Mirar como recuperar el usuario del TOKEN que se ha validado
         *
         * TODO Añadir al post el usuario que lo ha creado.
         * */
        if (false) { // PLACEHOLDER

            return new ResponseEntity<>("No eres el propietario de dicho post", HttpStatus.FORBIDDEN);
        }


        postManager.create(post);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /*
     * Modify post
     * */
    @PutMapping("/post")
    public ResponseEntity<String> modify(@RequestBody String json) {

        Post post = postManager.fromJsonUpdate(json);

        // Se comprueba que exista un id ya que si no existe no se puede modificar.
        if (post.getIdpost() == null) {
            return new ResponseEntity<>("No se ha recibido ID o se ha recibido un ID inexistente.", HttpStatus.BAD_REQUEST);
        }

        /*
         * TODO El usuario de el token que permite entrar en este metodo,
         *  tambien ha de ser el mismo que es el propietario del post que quiere modificar
         * */
        if (false) { // PLACEHOLDER

            return new ResponseEntity<>("No eres el propietario de dicho post", HttpStatus.FORBIDDEN);
        }

        postManager.update(post);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}