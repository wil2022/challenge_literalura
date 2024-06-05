package com.alura.challenge_literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
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
@Table(name = "autores")
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String nombre;
    private int anioNacimiento;
    private int anioFallecimiento;

    @ManyToMany(mappedBy = "autores", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Libro> libros = new ArrayList<>();

    public Autor(DatosAutor datosAutor){
        this.nombre = datosAutor.nombre();
        this.anioNacimiento = datosAutor.anioNacimiento();
        this.anioFallecimiento = datosAutor.anioFallecimiento();
    }


    @Override
    public String toString() {
        return "\nAutor: " + nombre + "\n" +
                "Fecha de nacimiento: " + anioNacimiento + "\n" +
                "Fecha de fallecimiento: " + anioFallecimiento +"\n" +
                "Libros: " + libros.stream().map(Libro::getTitulo).toList() + "\n";

    }

}
