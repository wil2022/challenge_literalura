package com.alura.challenge_literalura.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "libros")
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String titulo;
    private String idioma;
    private int numeroDescargas;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "libro_autor",
            joinColumns = @JoinColumn(name = "libro_id"),
            inverseJoinColumns = @JoinColumn(name = "autor_id")
    )
    private List<Autor> autores = new ArrayList<>();

        public Libro(DatosLibro datosLibro) {
        this.titulo = datosLibro.titulo();
        this.idioma = datosLibro.idiomas().get(0);
        this.numeroDescargas = datosLibro.numeroDescargas();
         /*   for (DatosAutor a:datosLibro.autores()) {
                this.autores.add(new Autor(a));

            }*/
            //this.autores = getAutores();

            this.autores = datosLibro.autores().stream()
                    .map(Autor::new)
                    .collect(Collectors.toList());


        //this.autores = datosLibro.autores().forEach(a -> new Autor(a));

    }

    public void addAutor(Autor autor) {
        //this.autores.add(autor);
        autor.getLibros().add(this);
    }



    @Override
    public String toString() {
        return "-----LIBRO-----\n"+
                "Titulo: " + titulo + "\n" +
                "Autor: " + autores.get(0).getNombre() + "\n" +
                "Idioma: " + idioma + "\n" +
                "Numero de descargas: " + numeroDescargas + "\n" +
                "--------------";

    }
}
