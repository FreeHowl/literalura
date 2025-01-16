package com.aluracursos.literalura.repository;

import com.aluracursos.literalura.model.Autores;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AutorRepository extends JpaRepository<Autores, Integer> {
    List<Autores> findByNombreContainsIgnoreCase(String nombre);
    List<Autores> findByAnoFallecimientoLessThanEqual(int anoMaximo);
    List<Autores> findByAnoNacimientoBetween(int anoNacimiento, int hastaAno);
}
