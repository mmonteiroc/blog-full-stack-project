package com.esliceu.proyectoglobal.manager;

import com.esliceu.proyectoglobal.entity.Post;
import com.esliceu.proyectoglobal.repository.PostRepository;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import javafx.geometry.Pos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

/**
 * Code created by: mmonteirocl
 * Email: miguelmonteiroclaveri@gmail.com
 * Date: 19/02/2020
 * Package: com.esliceu.proyectoglobal.manager
 * Project: BackEnd
 */
@Service
public class PostManager {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private Gson gson;

    @Autowired
    private UsuarioManager usuarioManager;

    public List<Post> findAll() {
        List<Post> toReturn = new LinkedList<>();

        for (Post postIndividual : postRepository.findAll()) {
            toReturn.add(postIndividual);
        }

        return toReturn;
    }

    public Post findById(Long id) {
        return postRepository.findByIdpost(id);
    }

    public void create(Post post) {
        postRepository.save(post);
    }

    public void update(Post post) {
        postRepository.save(post);
    }

    public void delete(Post post) {
        postRepository.delete(post);
    }


    public Post fromJsonCreate(String json) {
        Post post = new Post();

        JsonObject jsonObject = gson.fromJson(json, JsonObject.class);

        if (jsonObject.get("tituloOriginal") != null) {
            post.setTituloOriginal(jsonObject.get("tituloOriginal").getAsString());
        }
        if (jsonObject.get("tituloTraducido") != null) {
            post.setTituloTraducido(jsonObject.get("tituloTraducido").getAsString());
        }
        if (jsonObject.get("contenidoOriginal") != null) {
            post.setContenidoOriginal(jsonObject.get("contenidoOriginal").getAsString());
        }
        if (jsonObject.get("contenidoTraducido") != null) {
            post.setContenidoTraducido(jsonObject.get("contenidoTraducido").getAsString());
        }
        if (jsonObject.get("idiomaTraduccion") != null) {
            post.setIdiomaTraducido(jsonObject.get("idiomaTraduccion").getAsString());
        }

        post.setCreacion(LocalDate.now());
        return post;
    }


    /*
     * Esto solo es un parser, por eso, por mucho que no recibamos un ID
     * o algun parametro, o el ID que recibimos, no encontramos post en la
     * BBDD nunca petara, por eso mismo enctroamos por algunas partes
     * encontramos "post = new Post()", para que no pete nuestro parser
     * */
    public Post fromJsonUpdate(String json) {
        Post post;

        JsonObject jsonObject = gson.fromJson(json, JsonObject.class);

        if (jsonObject.get("idpost") != null) {
            post = findById(jsonObject.get("idpost").getAsLong());

            if (post == null) {
                // CREAMOS UN NUEVO POST YA QUE EL ID NO EXISTE Y NUESTRA DDBB NO LO ENCUENTRA

                post = new Post();
                post.setCreacion(LocalDate.now());
            }
        } else {
            post = new Post();
            post.setCreacion(LocalDate.now());
        }

        if (jsonObject.get("tituloOriginal") != null) {
            post.setTituloOriginal(jsonObject.get("tituloOriginal").getAsString());
        }
        if (jsonObject.get("tituloTraducido") != null) {
            post.setTituloTraducido(jsonObject.get("tituloTraducido").getAsString());
        }
        if (jsonObject.get("contenidoOriginal") != null) {
            post.setContenidoOriginal(jsonObject.get("contenidoOriginal").getAsString());
        }
        if (jsonObject.get("contenidoTraducido") != null) {
            post.setContenidoTraducido(jsonObject.get("contenidoTraducido").getAsString());
        }
        if (jsonObject.get("idiomaTraduccion") != null) {
            post.setIdiomaTraducido(jsonObject.get("idiomaTraduccion").getAsString());
        }

        return post;
    }
}
