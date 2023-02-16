package org.example.models;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "comentarios")

public class Comentarios implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_comentario")
    int id_comentario;
    @Column(name = "contenido")
    String contenido;
    @ManyToOne
    @JoinColumn(name = "id_usuario")
    Usuarios usuario;
    @ManyToOne
    @JoinColumn(name = "id_post")
    Posts post;
    @Column(name = "f_creacion")
    LocalDate f_creacion;

    public Comentarios() {
    }

    public Comentarios( String contenido, Usuarios usuario, Posts post, LocalDate f_creacion) {
        this.contenido = contenido;
        this.usuario = usuario;
        this.post = post;
        this.f_creacion = f_creacion;
    }

    public int getId_comentario() {
        return id_comentario;
    }

    public void setId_comentario(int id_comentario) {
        this.id_comentario = id_comentario;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public Usuarios getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuarios usuario) {
        this.usuario = usuario;
    }

    public Posts getPost() {
        return post;
    }

    public void setPost(Posts post) {
        this.post = post;
    }

    public LocalDate getF_creacion() {
        return f_creacion;
    }

    public void setF_creacion(LocalDate f_creacion) {
        this.f_creacion = f_creacion;
    }

    @Override
    public String toString() {
        return "Comentarios{" +
                "id_comentario=" + id_comentario +
                ", contenido='" + contenido + '\'' +
                ", usuario=" + usuario +
                ", post=" + post +
                ", f_creacion=" + f_creacion +
                '}';
    }
}
