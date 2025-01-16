package com.aluracursos.literalura.repository;

import com.aluracursos.literalura.model.Autores;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AutorRepository extends JpaRepository<Autores, Integer> {
    List<Autores> findByNombreContainsIgnoreCase(String nombre);

    @Query("SELECT a FROM Autores a WHERE a.anoNacimiento <= :anoAutor AND a.anoFallecimiento >= :anoAutor ")
    List<Autores> findByAno(int anoAutor);
}
