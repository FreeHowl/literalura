package com.aluracursos.literalura.model;

import jakarta.persistence.*;

import java.util.Collections;

@Entity
@Table(name = "libros")
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;
    private String titulo;
    @ManyToOne
    private Autores autores;
    @ManyToOne
    private Idioma idiomas;
    private Double descargas;

    public Libro(){}

    public Libro(DatosLibro datosLibro) {
        this.titulo = datosLibro.titulo();
        this.idiomas = new Idioma(new DatosIdioma(datosLibro.idiomas()));
        this.autores = new Autores(datosLibro.autores().isEmpty()?
                new DatosAutores("Unknown", 0, 0):datosLibro.autores().get(0));
        this.descargas = datosLibro.descargas();
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Double getDescargas() {
        return descargas;
    }

    public void setDescargas(Double descargas) {
        this.descargas = descargas;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Autores getAutores() {
        return autores;
    }

    public void setAutores(Autores autores) {
        this.autores = autores;
    }

    public Idioma getIdiomas() {
        return idiomas;
    }

    public void setIdiomas(Idioma idiomas) {
        this.idiomas = idiomas;
    }

    @Override
    public String toString() {
        return  ", Titulo='" + titulo + '\'' +
                ", Autor=" + autores.getNombre() + '\'' +
                ", Idiomas=" + idiomas.getAbreviacion() + '\'' +
                ", Descargas=" + descargas;
    }
}
