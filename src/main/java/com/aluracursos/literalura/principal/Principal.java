package com.aluracursos.literalura.principal;

import com.aluracursos.literalura.model.*;
import com.aluracursos.literalura.repository.AutorRepository;
import com.aluracursos.literalura.repository.IdiomaRepository;
import com.aluracursos.literalura.repository.LibroRepository;
import com.aluracursos.literalura.service.ConsumoApi;
import com.aluracursos.literalura.service.ConvierteDatos;

import java.util.*;

public class Principal {
    private Scanner teclado = new Scanner(System.in);
    private ConsumoApi consumoApi = new ConsumoApi();
    private final String URL_BASE = "https://gutendex.com/books/?search=";
    private ConvierteDatos conversor = new ConvierteDatos();
    private List<DatosLibro> datosLibros = new ArrayList<>();
    private LibroRepository repositorioDeLibros;
    private List<Libro> libros;
    private IdiomaRepository repositorioDeIdiomas;
    private AutorRepository repositorioDeAutores;
    private List<Autores> autoresExistentes;

    public Principal(LibroRepository libroRepository, IdiomaRepository idiomaRepository, AutorRepository autorRepository) {
        this.repositorioDeLibros = libroRepository;
        this.repositorioDeIdiomas = idiomaRepository;
        this.repositorioDeAutores = autorRepository;
    }

    public void muestraElMenu(){
        var opcion = -1;
        while (opcion != 0){
            var menu = """
                    Escriba el numero de la opción que desea:
                    
                    1 - Consultar libros por titulo
                    2 - Listar libros registrados
                    3 - Listar autores registrados
                    4 - Listar autores vivos en un determinado año
                    5 - Listar libros por idioma
                    
                    0 - Salir
                    """;
            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine();
            switch (opcion){
                case 1:
                    getDatosLibro();
                    break;
                case 2:
                    listarLibrosBuscados();
                    break;
                case 3:
                    listarAutores();
                    break;
                case 4:
                    autoresVivosPeriodo();
                    break;
                case 0:
                    System.out.println("Cerrando aplicacion...");
                default:
                    System.out.println("Opcion invalida");
            }
        }
    }
    /*private void buscarLibroWeb() {
        DatosLibro datos = getDatosLibro();
        Libro libro = new Libro(datos);
        repositorioDeLibros.save(libro);
        //datosLibros.add(datos);
        System.out.println(datos);
    }*/

    private void getDatosLibro() {
        System.out.println("Escribe el nombre del libro que desea buscar");
        var nombreLibro = teclado.nextLine();
        try {
            var json = consumoApi.obtenerDatos(URL_BASE + nombreLibro.replace(" ", "+").toLowerCase());
            Resultado datos = conversor.obtenerDatos(json, Resultado.class);
            DatosLibro consulta = datos.resultados().get(0);
            Autores autoresConsulta =new Autores(consulta.autores().isEmpty()?
                    new DatosAutores("Desconocido",0, 0):consulta.autores().get(0));
            Libro libroSave = new Libro(consulta);
            Idioma idiomaSave = libroSave.getIdiomas();
            repositorioDeIdiomas.save(idiomaSave);
            List<Libro> primerLibro = repositorioDeLibros.findByTituloContainsIgnoreCase(libroSave.getTitulo());
            List<Autores> primerAutor = repositorioDeAutores.findByNombreContainsIgnoreCase(autoresConsulta.getNombre());
            List<Idioma> primerIdioma = repositorioDeIdiomas.findByAbreviacion(idiomaSave.getAbreviacion());

            if(!primerIdioma.isEmpty()){
                primerIdioma.forEach(i->{
                    if (idiomaSave.getAbreviacion().contains(i.getAbreviacion())){
                        libroSave.setIdiomas(i);
                    }else {
                        repositorioDeIdiomas.save(idiomaSave);
                    }
                });
            }else{
                repositorioDeIdiomas.save(idiomaSave);
            }

            if (primerAutor.size() == 0 && primerLibro.size()==0){
                repositorioDeAutores.save(autoresConsulta);
                libroSave.setAutores(autoresConsulta);
                repositorioDeLibros.save(libroSave);
            } else if (primerAutor.size() != 0 && primerLibro.size() != 0) {
                libroSave.setAutores(primerAutor.get(0));
                repositorioDeLibros.save(libroSave);
            }
            System.out.println(libroSave);
        } catch (IndexOutOfBoundsException e){
            System.out.println("Libro no disponible");
            muestraElMenu();
        }

    }

    private void listarLibrosBuscados(){
        libros = repositorioDeLibros.findAll();
        libros.forEach(System.out::println);
    }

    private void listarAutores(){
        autoresExistentes = repositorioDeAutores.findAll();
        autoresExistentes.forEach(System.out::println);
    }

    private void autoresVivosPeriodo(){
        System.out.println("Escriba el año que desea consultar autores vivos: ");
        var anoAutor = teclado.nextInt();
        List<Autores> autoresVivos = repositorioDeAutores.findByAno(anoAutor);
        if (!autoresVivos.isEmpty()){
            autoresVivos.forEach(System.out::println);
        }else {
            System.out.println("No se ecnotraron autores");
        }
    }

    private void listarPorIdioma(){

    }

}
