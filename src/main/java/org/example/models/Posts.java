package org.example.models;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "post")
public class Posts implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_post")
    int id_post;
    @Column(name = "titulo")
    String titulo;
    @Column(name = "contenido")
    String contenido;
    @ManyToOne
    @JoinColumn(name="id_usuario")
    Usuarios usuario;
    @OneToMany(mappedBy = "id_comentario")
    List<Comentarios>comentarios;
    @Column(name = "f_creacion")
    LocalDate f_publicacion;

    public Posts() {
    }

    public Posts( String titulo, String contenido,LocalDate f_publicacion,Usuarios usuario) {
        this.titulo = titulo;
        this.contenido = contenido;
        this.usuario = usuario;
        this.f_publicacion = f_publicacion;
    }

    public int getId_post() {
        return id_post;
    }

    public void setId_post(int id_post) {
        this.id_post = id_post;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
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

    public List<Comentarios> getComentarios() {
        return comentarios;
    }

    public void setComentarios(List<Comentarios> comentarios) {
        this.comentarios = comentarios;
    }

    public LocalDate getF_publicacion() {
        return f_publicacion;
    }

    public void setF_publicacion(LocalDate f_publicacion) {
        this.f_publicacion = f_publicacion;
    }

    @Override
    public String toString() {
        return "Posts{" +
                "id_post=" + id_post +
                ", titulo='" + titulo + '\'' +
                ", contenido='" + contenido + '\'' +
                ", usuario=" + usuario +
                ", comentarios=" + comentarios +
                ", f_publicacion=" + f_publicacion +
                '}';
    }
}
