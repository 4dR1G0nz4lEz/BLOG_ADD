package org.example.models;

import jakarta.persistence.*;

import javax.sound.midi.Soundbank;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Menu {

    @PersistenceUnit(name = "persistencia")
//    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistencia");
    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistencia");
    private static EntityManager manager = emf.createEntityManager();
    private static List<Usuarios> users;
    private static List<Posts> posts;
    private static List<Comentarios> coments;


    public static void menuPrincipal(){
        Scanner teclado = new Scanner(System.in);
        Boolean exit = false;
        int opcion;
        while (!exit){
            System.out.println("-----------Bienvenido--------------");
            System.out.println("SELECIONE UNA OPCION");
            System.out.println("1 Login");
            System.out.println("2 Registro");
            System.out.println("3 Salir");
            opcion=teclado.nextInt();
            switch (opcion){
                case 1:
                    login();
                    break;
                case 2:
                    registro();
                    break;
                case 3:
                    System.out.println("Hasta luego");
                    exit=true;
                    break;
            }
        }
    }
    public static void login(){
        Scanner teclado = new Scanner(System.in);
        System.out.println("Email:  ");
        String email=teclado.nextLine();
        System.out.println("Contraseña: ");
        String contraseña=teclado.nextLine();

        String hql="FROM Usuarios WHERE email = :email AND contraseña = :contraseña";
        Query query = manager.createQuery(hql);
        query.setParameter("email",email );
        query.setParameter("contraseña",contraseña);

        users =query.getResultList();

        if (users.size()>0){
            System.out.println("Sesion iniciada con exito");
            menuSecundario();
        }else{
            System.out.println("El usuario no existe");
        }
    }

    public static void menuSecundario(){
        Scanner teclado = new Scanner(System.in);
        Boolean exit = false;
        int opcion;
        while (!exit){
            System.out.println("-----------Bienvenido--------------");
            System.out.println("SELECIONE UNA OPCION");
            System.out.println("1 Ver mis datos");
            System.out.println("2 Ver mis posts");
            System.out.println("3 Nuevo post");
            System.out.println("4 Ver todos los post");
            System.out.println("5 Ver comentarios de un post");
            System.out.println("6 Comentar un post");
            System.out.println("7 Modificar usuario");
            System.out.println("8 Cerrar sesion");
            opcion=teclado.nextInt();
            switch (opcion){
                case 1:
                    verDatosUsuarioLogeado();
                    break;
                case 2:
                    verPostUsuarioLogeado();
                    break;
                case 3:
                    nuevoPost();
                    break;
                case 4:
                    verTodosPosts();
                    break;
                case 5:
                    verComentariosPost();
                    break;
                case 6:
                    comentarPost();
                    break;
                case 7:
                    modificarUsuario();
                    break;
                case 8:
                    System.out.println("Hasta luego");
                    exit=true;
                    break;
            }
        }
    }
    public static void registro(){
        Scanner teclado = new Scanner(System.in);
        System.out.println("Introduce nombre: ");
        String nombre =teclado.nextLine();
        System.out.println("Introduce email: ");
        String email =teclado.nextLine();
        System.out.println("Introduce contraseña: ");
        String contraseña =teclado.nextLine();
        System.out.println("Introduce año: ");
        Integer año =teclado.nextInt();
        System.out.println("Introduce mes: ");
        Integer mes =teclado.nextInt();
        System.out.println("Introduce dia: ");
        Integer dia =teclado.nextInt();

        LocalDate f_nacimiento = LocalDate.of(año,mes,dia);
        LocalDate f_creacion = LocalDate.now();

        if (comprobarUsuario(email) == 0) {
            Usuarios u1 = new Usuarios(nombre,email,contraseña,f_nacimiento,f_creacion);


            manager.getTransaction().begin();
            manager.persist(u1);
            manager.getTransaction().commit();

            System.out.println("Usuario creado con exito");
            menuPrincipal();
        }else {
            System.out.println("Error al crear el usuario");
            System.out.println("El usuario ya existe");
        }



    }

    public static int comprobarUsuario(String email){
        Query query= manager.createQuery("FROM Usuarios WHERE email = :email");
        query.setParameter("email",email);
        users=query.getResultList();
        return users.size();
    }


    public static void verDatosUsuarioLogeado(){

            for (Usuarios u : users) {
                System.out.println("-----------------------------------");
                System.out.println("---------------DATOS----------------");
                System.out.println("Nombre : " + u.getNombre());
                System.out.println("Email : " + u.getEmail());
                System.out.println("Fecha de nacimiento : " + u.getF_nacimiento());
                System.out.println("Fecha de creacion : " + u.getF_creacion());
            }

    }

    public static void verPostUsuarioLogeado(){
        try {
            posts = users.get(0).getPosts();
            for (Posts p : posts) {
                System.out.println("-----------------------------------");
                System.out.println("---------------POST----------------");
                System.out.println("Id del post = " + p.getId_post());
                System.out.println("Contenido del post = " + p.getContenido());
                System.out.println("Fecha de creacion del post = " + p.getF_publicacion());
            }
        }catch (Exception e){
            System.out.println("El usuario no tiene posts creados");
        }
    }

    public static void nuevoPost(){
     try {

         Scanner teclado = new Scanner(System.in);

         System.out.println("------------CREANDO-NUEVO-POSTS----------------");
         System.out.println("INTRODUCE TITULO: ");
         String titulo = teclado.nextLine();
         System.out.println("INTRODUCE CONTENIDO: ");
         String contenido = teclado.nextLine();

         LocalDate f_creacion = LocalDate.now();

         Posts p = new Posts(titulo, contenido, f_creacion, users.get(0));

         manager.getTransaction().begin();
         manager.persist(p);
         manager.getTransaction().commit();

         System.out.println("POST CREADO CON EXITO");

        }catch (PersistenceException pE){
         System.out.println("Error al crear el post");
     }

    }

    public static void verTodosPosts(){

        String hql = "FROM Posts";
        Query query = manager.createQuery(hql);
        posts=query.getResultList();

        if (posts.size()!=0) {

            for (Posts p : posts) {
                System.out.println("-----------------------------------");
                System.out.println("---------------POST----------------");
                System.out.println("Id del post = " + p.getId_post());
                System.out.println("Contenido del post = " + p.getContenido());
                System.out.println("Fecha de creacion del post = " + p.getF_publicacion());
            }

        }else{
            System.out.println("No hay posts creados");
        }
    }

    public static void verComentariosPost (){
        try {
            Scanner teclado = new Scanner(System.in);


            System.out.println("-----------------------------------");
            System.out.println("INTRODUCE ID DEL POST");
            Integer id_post = teclado.nextInt();

            String hql = "FROM Posts WHERE id_post = :id_post";
            Query query = manager.createQuery(hql);
            query.setParameter("id_post", id_post);

            posts = query.getResultList();


            coments = posts.get(0).getComentarios();


            for (Comentarios c : coments) {
                System.out.println("-----------------------------------");
                System.out.println("---------------COMENTARIOS----------------");
                System.out.println("Id del post = " + c.getId_comentario());
                System.out.println("Contenido del comentario = " + c.getContenido());
                System.out.println("Fecha de creacion del comentario = " + c.getF_creacion());
                System.out.println("Id del post = " + posts.get(0).getId_post());
                System.out.println("Id del usuario = " + users.get(0).getId_usuario());
            }
        }catch (IndexOutOfBoundsException e){
            System.out.println("El post no existe");
        }
    }

    public static void comentarPost(){
        try {
            Scanner teclado = new Scanner(System.in);

            System.out.println("-----------------------------------");
            System.out.println("INTRODUCE ID DEL POST");
            Integer id_post = teclado.nextInt();
            teclado.nextLine();

            System.out.println("--------------------------------------");
            System.out.println("NUEVO COMENTARIO");
            System.out.println("Introduce contenido");
            String contenido = teclado.nextLine();

            String hql = "FROM Posts WHERE id_post = :id_post";
            Query query = manager.createQuery(hql);
            query.setParameter("id_post", id_post);
            posts = query.getResultList();

            LocalDate f_creacion = LocalDate.now();

            Comentarios c = new Comentarios(contenido, users.get(0), posts.get(0), f_creacion);

            manager.getTransaction().begin();
            manager.persist(c);
            manager.getTransaction().commit();

            System.out.println("COMENTARIO CREADO CON EXITO");
        }catch (PersistenceException pE){
            System.out.println("Error al crear el comentario");
        }
    }

    public static void modificarUsuario(){

       try {
           Scanner teclado = new Scanner(System.in);
           Boolean exit = false;

           while (!exit) {
               System.out.println("-------------MODIFICANDO USUARIO--------------");
               System.out.println("1 Modificar nombre");
               System.out.println("2 Modificar email");
               System.out.println("3 Modificar contraseña");
               System.out.println("4 Modificar fecha de nacimiento");
               System.out.println("5 Salir");

               int opcion = teclado.nextInt();
               teclado.nextLine();
               switch (opcion) {
                   case 1:
                       System.out.println("Introduce nombre: ");
                       String nombre = teclado.nextLine();
                       users.get(0).setNombre(nombre);
                       manager.getTransaction().begin();
                       manager.persist(users.get(0));
                       manager.getTransaction().commit();
                       break;
                   case 2:
                       System.out.println("Introduce email: ");
                       String email = teclado.nextLine();
                       users.get(0).setEmail(email);
                       manager.getTransaction().begin();
                       manager.persist(users.get(0));
                       manager.getTransaction().commit();
                       break;
                   case 3:
                       System.out.println("Introduce contraseña: ");
                       String contraseña = teclado.nextLine();
                       users.get(0).setContraseña(contraseña);
                       manager.getTransaction().begin();
                       manager.persist(users.get(0));
                       manager.getTransaction().commit();
                       break;
                   case 4:
                       System.out.println("Introduce año: ");
                       Integer año = teclado.nextInt();
                       System.out.println("Introduce mes: ");
                       Integer mes = teclado.nextInt();
                       System.out.println("Introduce dia: ");
                       Integer dia = teclado.nextInt();

                       LocalDate f_nacimiento = LocalDate.of(año, mes, dia);
                       users.get(0).setF_nacimiento(f_nacimiento);
                       manager.getTransaction().begin();
                       manager.persist(users.get(0));
                       manager.getTransaction().commit();
                       break;
                   case 5:
                       exit = true;
                       menuSecundario();
               }
           }
       }catch (PersistenceException | DateTimeException | InputMismatchException e){
           System.out.println("Error al modificar los datos");
       }
    }

    public static void cerrarSesion(){
        users=null;
        coments=null;
        posts=null;
        System.out.println("Sesion cerrada");
        menuPrincipal();
    }
}
