package com.alura.challenge_literalura.repository;

import com.alura.challenge_literalura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AutorRepository extends JpaRepository<Autor, Long> {

    Autor findByNombre(String nombre);

}
