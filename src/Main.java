
import java.util.*;

// PATRÓN CREACIONAL: Singleton para la clase Biblioteca
class Biblioteca {
    private static Biblioteca instancia;
    private List<Libro> libros = new ArrayList<>();

    private Biblioteca() {}

    public static Biblioteca getInstancia() {
        if (instancia == null) {
            instancia = new Biblioteca();
        }
        return instancia;
    }

    public void agregarLibro(Libro libro) {
        libros.add(libro);
    }

    public List<Libro> buscar(BusquedaStrategy estrategia, String clave) {
        return estrategia.buscar(libros, clave);
    }

    public List<Libro> getLibros() {
        return libros;
    }
}

class Libro {
    String titulo;
    String autor;

    public Libro(String titulo, String autor) {
        this.titulo = titulo;
        this.autor = autor;
    }

    public String getInfo() {
        return titulo + " - " + autor;
    }
}

// PATRÓN ESTRUCTURAL: Adapter para adaptar un libro PDF
interface LibroFormateado {
    String getContenido();
}

class LibroPDF {
    String contenido;

    public LibroPDF(String contenido) {
        this.contenido = contenido;
    }

    public String obtenerTexto() {
        return contenido;
    }
}

class LibroPDFAdapter extends Libro implements LibroFormateado {
    private LibroPDF libroPDF;

    public LibroPDFAdapter(String titulo, String autor, LibroPDF libroPDF) {
        super(titulo, autor);
        this.libroPDF = libroPDF;
    }

    @Override
    public String getContenido() {
        return libroPDF.obtenerTexto();
    }
}

// PATRÓN DE COMPORTAMIENTO: Strategy para búsquedas
interface BusquedaStrategy {
    List<Libro> buscar(List<Libro> libros, String clave);
}

class BusquedaPorTitulo implements BusquedaStrategy {
    public List<Libro> buscar(List<Libro> libros, String clave) {
        List<Libro> resultado = new ArrayList<>();
        for (Libro libro : libros) {
            if (libro.titulo.toLowerCase().contains(clave.toLowerCase())) {
                resultado.add(libro);
            }
        }
        return resultado;
    }
}

class BusquedaPorAutor implements BusquedaStrategy {
    public List<Libro> buscar(List<Libro> libros, String clave) {
        List<Libro> resultado = new ArrayList<>();
        for (Libro libro : libros) {
            if (libro.autor.toLowerCase().contains(clave.toLowerCase())) {
                resultado.add(libro);
            }
        }
        return resultado;
    }
}

public class Main {
    public static void main(String[] args) {
        Biblioteca biblio = Biblioteca.getInstancia();

        biblio.agregarLibro(new Libro("El Quijote", "Cervantes"));
        biblio.agregarLibro(new Libro("Cien años de soledad", "García Márquez"));

        LibroPDF libroPDF = new LibroPDF("Contenido PDF: Programación en Java");
        biblio.agregarLibro(new LibroPDFAdapter("Java Básico", "Juan Pérez", libroPDF));

        System.out.println("🔍 Búsqueda por título: 'Java'");
        List<Libro> resultado1 = biblio.buscar(new BusquedaPorTitulo(), "Java");
        resultado1.forEach(libro -> System.out.println("📘 " + libro.getInfo()));

        System.out.println("\n🔍 Búsqueda por autor: 'García'");
        List<Libro> resultado2 = biblio.buscar(new BusquedaPorAutor(), "García");
        resultado2.forEach(libro -> System.out.println("📗 " + libro.getInfo()));
    }
}
