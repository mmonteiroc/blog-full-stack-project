package com.esliceu.proyectoglobal.controller;

import com.esliceu.proyectoglobal.entity.Post;
import com.esliceu.proyectoglobal.manager.PostManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
     * Create post
     * */
    @PostMapping("/post")
    public ResponseEntity<String> create(@RequestBody String json) {

        Post post = postManager.fromJsonCreate(json);

        // TODO Comprobar que todos los campos obligatorios los recibimos

        postManager.create(post);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /*
     * Modify post
     * */
    @PutMapping("/post")
    public ResponseEntity<String> modify(@RequestBody String json) {

        Post post = postManager.fromJsonUpdate(json);

        /*
         * TODO comprobar que recibimos un ID si o si,
         *  sino no se modifica y habra que mandar errores BAD_REQUEST
         *
         * */
        postManager.update(post);

        return new ResponseEntity<>(HttpStatus.OK);

    }


}
