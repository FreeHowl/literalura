package com.aluracursos.literalura.repository;

import com.aluracursos.literalura.model.Idioma;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IdiomaRepository  extends JpaRepository<Idioma, String> {
    List<Idioma> findByAbreviacion(String abreviacion);
}
