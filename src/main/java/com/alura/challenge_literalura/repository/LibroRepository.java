package com.alura.challenge_literalura.repository;

import com.alura.challenge_literalura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LibroRepository extends JpaRepository <Libro, Long>{
    List<Libro> findByIdiomaIgnoreCase(String idioma);

}
