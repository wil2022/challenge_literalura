package com.alura.challenge_literalura.principal;

import com.alura.challenge_literalura.model.*;
import com.alura.challenge_literalura.repository.AutorRepository;
import com.alura.challenge_literalura.repository.LibroRepository;
import com.alura.challenge_literalura.service.ConsumoAPI;
import com.alura.challenge_literalura.service.ConvierteDatos;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

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
        //return datos;
    }

    //@Transactional
    private void buscarLibro() {
        DatosLibro datos = getDatosLibro();
        Libro libro = new Libro(datos);

/*        for (Autor autor: libro.getAutores()) {
            Autor nombreAutor = autorRepository.findByNombre(autor.getNombre());
            if (nombreAutor != null) {
                libro.addAutor(nombreAutor);

            }*/



        //List<Autor> autor = new ArrayList<>(datos.autores().stream().map(Autor::new));
  /*      List<Autor> autores = autorRepository.findAll();
        for (Autor a: autores) {
            for (Autor b: libro.getAutores()) {
                if (a.getNombre().equals(b.getNombre())){
                    libro.setAutores(autores);
                }
            }

        }*/



        //}
        //autorRepository.saveAll(libro.getAutores());
        libroRepository.save(libro);
        //datosSeries.add(datos);
        System.out.println(libro);
    }

    private void listarLibros() {
        List<Libro> libros = libroRepository.findAll();
        libros.forEach(System.out::println);

    }

    private void listarAutores() {
        List<Autor> autores = autorRepository.findAll();
        autores.forEach(System.out::println);
    }


}