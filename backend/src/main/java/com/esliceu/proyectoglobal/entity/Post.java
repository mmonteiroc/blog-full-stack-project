package com.esliceu.proyectoglobal.entity;

import java.time.LocalDate;

/**
 * Code created by: mmonteirocl
 * Email: miguelmonteiroclaveri@gmail.com
 * Date: 18/02/2020
 * Package: com.esliceu.proyectoglobal.entity
 * Project: BackEnd
 */
public class Post {

    private Long idpost;


    private String titulo;


    private String contenido_original;


    private String contenido_traducido;


    private String idioma_traducido;


    private LocalDate creacion;


    private Usuario usuario;


    public Post() {
    }

    public Long getIdpost() {
        return idpost;
    }

    public void setIdpost(Long idpost) {
        this.idpost = idpost;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getContenido_original() {
        return contenido_original;
    }

    public void setContenido_original(String contenido_original) {
        this.contenido_original = contenido_original;
    }

    public String getContenido_traducido() {
        return contenido_traducido;
    }

    public void setContenido_traducido(String contenido_traducido) {
        this.contenido_traducido = contenido_traducido;
    }

    public String getIdioma_traducido() {
        return idioma_traducido;
    }

    public void setIdioma_traducido(String idioma_traducido) {
        this.idioma_traducido = idioma_traducido;
    }

    public LocalDate getCreacion() {
        return creacion;
    }

    public void setCreacion(LocalDate creacion) {
        this.creacion = creacion;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
