package com.aluracursos.literalura.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "idiomas", schema = "public")
public class Idioma {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;
    private String abreviacion;
    @OneToMany(mappedBy = "idiomas", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Libro> libros;

    public Idioma(){}

    public Idioma(DatosIdioma idioma) {
        this.abreviacion = idioma.idiomas().get(0);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAbreviacion() {
        return abreviacion;
    }

    public void setAbreviacion(String abreviacion) {
        this.abreviacion = abreviacion;
    }

    public List<Libro> getLibros() {
        return libros;
    }

    public void setLibros(List<Libro> libros) {
        libros.forEach(l -> l.setIdiomas(this) );
        this.libros = libros;
    }

    @Override
    public String toString() {
        return "Idioma" + '\'' +
                ", abreviacion='" + abreviacion + '\'' +
                ", libros=" + libros;
    }
}
