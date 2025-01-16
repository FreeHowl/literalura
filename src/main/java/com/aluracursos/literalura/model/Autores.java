package com.aluracursos.literalura.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "autores", schema = "public")
public class Autores {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nombre;
    private int anoNacimiento;
    private int anoFallecimiento;
    @OneToMany(mappedBy = "autores", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Libro> libros;

    public Autores(){}

    public Autores(DatosAutores datosAutores) {
        this.nombre = datosAutores.nombre();
        this.anoNacimiento = datosAutores.anoNacimiento();
        this.anoFallecimiento = datosAutores.anoFallecimiento();
    }


    public int getId() { return id;}

    public void setId(int id) { this.id = id;}

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getAnoNacimiento() {
        return anoNacimiento;
    }

    public void setAnoNacimiento(int anoNacimiento) {
        this.anoNacimiento = anoNacimiento;
    }

    public int getAnoFallecimiento() {
        return anoFallecimiento;
    }

    public void setAnoFallecimiento(int anoFallecimiento) {
        this.anoFallecimiento = anoFallecimiento;
    }

    public List<Libro> getLibros() {
        return libros;
    }

    public void setLibros(List<Libro> libros) {
        libros.forEach(l -> l.setAutores(this));
        this.libros = libros;
    }

    @Override
    public String toString() {
        return "Autores:" + '\'' +
                "Nombre='" + nombre + '\'' +
                ", Año de nacimiento=" + anoNacimiento + '\'' +
                ", Año de fallecimiento=" + anoFallecimiento + '\'' +
                ", Libros=" + libros;
    }
}
