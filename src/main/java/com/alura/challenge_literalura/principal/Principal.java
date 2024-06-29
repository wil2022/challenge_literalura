package com.alura.challenge_literalura.principal;

import com.alura.challenge_literalura.model.*;
import com.alura.challenge_literalura.repository.AutorRepository;
import com.alura.challenge_literalura.repository.LibroRepository;
import com.alura.challenge_literalura.service.ConsumoAPI;
import com.alura.challenge_literalura.service.ConvierteDatos;

import java.util.List;
import java.util.Scanner;

public class Principal {
    private Scanner teclado = new Scanner(System.in);

    private final String URL_BASE = "http://gutendex.com/books/";
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private ConvierteDatos conversor = new ConvierteDatos();

    private LibroRepository libroRepository;
    private AutorRepository autorRepository;

    public Principal(LibroRepository libroRepository, AutorRepository autorRepository){
        this.libroRepository = libroRepository;
        this.autorRepository = autorRepository;
    }
    public void muestraElMenu() {
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    -------------------
                    Elija la opcion a traves de su numero:
                    1 - Buscar libro por titulo
                    2 - Listar libros registrados
                    3 - Listar autores registrados
                    4 - Listar autores vivos en un determinado año
                    5 - Listar libros por idioma                                 
                    0 - Salir
                    """;
            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
                case 1 -> buscarLibro();
                case 2 -> listarLibros();
                case 3 -> listarAutores();
                case 4 -> autoresVivosAnioDeterminado();
                case 5 -> listarLibrosPorIdioma();
                case 0 -> System.out.println("Cerrando la aplicación...");
                default -> System.out.println("Opción inválida");
            }
        }
    }

    private DatosLibro getDatosLibro() {

            System.out.println("Escribe el nombre del libro que deseas buscar: ");
            var nombreLibro = teclado.nextLine();
            var json = consumoApi.obtenerDatos(URL_BASE + "?search=" + nombreLibro.replace(" ", "+"));
            //System.out.println(json);
            DatosResultados datos = conversor.obtenerDatos(json, DatosResultados.class);
            return datos.libros().get(0);

    }


    private void buscarLibro() {
        try{
            DatosLibro datos = getDatosLibro();
            Libro libro = new Libro(datos);

            Autor autor = autorRepository.findByNombre(datos.autores().get(0).nombre());

            if(autor != null){
                libro.addAutor(autor);
                libro.setAutor(autor);
            }else {
                autorRepository.save(libro.getAutor());
            }

            libroRepository.save(libro);
            System.out.println(libro);
        }catch (IndexOutOfBoundsException e){
            System.out.println("libro no encontrado");
        }catch (Exception e){
            System.out.println("no se puede registrar un libro mas de una vez");

        }

    }

    private void listarLibros() {
        List<Libro> libros = libroRepository.findAll();
        libros.forEach(System.out::println);

    }

    private void listarAutores() {
        List<Autor> autores = autorRepository.findAll();
        autores.forEach(System.out::println);
    }

    private void autoresVivosAnioDeterminado() {
        System.out.println("Ingrese el año por el que desea buscar: ");
        var anio = teclado.nextInt();

        List<Autor> autoresVivos = autorRepository.buscarAutoresVivosPorAnio(anio);

        if(autoresVivos.isEmpty()){
            System.out.println("ningun autor vivo encontrado en ese año");
        }else {
            autoresVivos.forEach(System.out::println);
        }

    }

    private void listarLibrosPorIdioma() {
        String opciones = """
                Ingrese el idioma para buscar los libros:
                es - español
                en - ingles              
                """;
        System.out.println(opciones);
        var opcion = teclado.nextLine();
        if(opcion.equalsIgnoreCase("es") || opcion.equalsIgnoreCase("en")){
            List<Libro> librosIdioma = libroRepository.findByIdiomaIgnoreCase(opcion);
            if(librosIdioma.isEmpty()){
                System.out.println("no se encontraron libros en el idioma seleccionado");
            }else{
                librosIdioma.forEach(System.out::println);
            }
        }else{
            System.out.println("opcion no valida");
        }

    }


}