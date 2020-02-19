package com.esliceu.proyectoglobal.entity;

import com.fasterxml.jackson.annotation.*;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * Code created by: mmonteirocl
 * Email: miguelmonteiroclaveri@gmail.com
 * Date: 18/02/2020
 * Package: com.esliceu.proyectoglobal.entity
 * Project: BackEnd
 */
@Entity
@Table(name = "post")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idpost")
    private Long idpost;

    @Column(name = "titulo_original", length = 100, nullable = false)
    private String tituloOriginal;

    @Column(name = "titulo_traducido", length = 100, nullable = false)
    private String tituloTraducido;

    @Column(name = "contenido_original", columnDefinition = "TEXT", nullable = false)
    private String contenidoOriginal;

    @Column(name = "contenido_traducido", columnDefinition = "TEXT", nullable = false)
    private String contenidoTraducido;

    @Column(name = "idioma_traducido", length = 10, nullable = false)
    private String idiomaTraducido;

    @Column(name = "fecha_creacion", nullable = false, columnDefinition = "DATE")
    private LocalDate creacion;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(
            foreignKey = @ForeignKey(
                    name = "usuari_idusuari"),
            name = "usuari_idusuari",
            nullable = false
    )
    private Usuario usuario;


    public Post() {
    }

    public Long getIdpost() {
        return idpost;
    }

    public void setIdpost(Long idpost) {
        this.idpost = idpost;
    }

    public String getTituloOriginal() {
        return tituloOriginal;
    }

    public void setTituloOriginal(String tituloOriginal) {
        this.tituloOriginal = tituloOriginal;
    }

    public String getTituloTraducido() {
        return tituloTraducido;
    }

    public void setTituloTraducido(String tituloTraducido) {
        this.tituloTraducido = tituloTraducido;
    }

    public String getContenidoOriginal() {
        return contenidoOriginal;
    }

    public void setContenidoOriginal(String contenidoOriginal) {
        this.contenidoOriginal = contenidoOriginal;
    }

    public String getContenidoTraducido() {
        return contenidoTraducido;
    }

    public void setContenidoTraducido(String contenidoTraducido) {
        this.contenidoTraducido = contenidoTraducido;
    }

    public String getIdiomaTraducido() {
        return idiomaTraducido;
    }

    public void setIdiomaTraducido(String idiomaTraducido) {
        this.idiomaTraducido = idiomaTraducido;
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
