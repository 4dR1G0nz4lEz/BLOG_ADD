package org.example.models;
import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;


@Entity
@Table(name = "usuarios")

public class Usuarios implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private int id_usuario;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "email")
    private String email;
    @Column(name = "contraseña")
    private String contraseña;
    @Column(name = "f_nacimiento")
    private LocalDate f_nacimiento;
    @Column(name = "f_creacion")
    private LocalDate f_creacion;
    @OneToMany(mappedBy = "usuario")
    private List<Posts>posts;


    public Usuarios() {
    }

    public Usuarios(String nombre, String email, String contraseña, LocalDate f_nacimiento, LocalDate f_creacion) {
        this.nombre = nombre;
        this.email = email;
        this.contraseña = contraseña;
        this.f_nacimiento = f_nacimiento;
        this.f_creacion = f_creacion;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public LocalDate getF_nacimiento() {
        return f_nacimiento;
    }

    public void setF_nacimiento(LocalDate f_nacimiento) {
        this.f_nacimiento = f_nacimiento;
    }

    public LocalDate getF_creacion() {
        return f_creacion;
    }

    public void setF_creacion(LocalDate f_creacion) {
        this.f_creacion = f_creacion;
    }
    public List<Posts> getPosts() {
        return posts;
    }
    public void setPosts(List<Posts> posts) {
        this.posts = posts;
    }

    @Override
    public String toString() {
        return "Usuarios{" +
                "id_usuario=" + id_usuario +
                ", nombre='" + nombre + '\'' +
                ", email='" + email + '\'' +
                ", contraseña='" + contraseña + '\'' +
                ", f_nacimiento=" + f_nacimiento +
                ", f_creacion=" + f_creacion +
                '}';
    }
}
