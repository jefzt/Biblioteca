import java.util.ArrayList;
import java.util.Scanner;



public class Seguridad {

    private ArrayList<Libro>    libros;
    private ArrayList<Usuario>  usuarios;
    private ArrayList<Prestamo> prestamos;
    private Usuario             usuarioActivo;
    private Scanner             scanner;
    
public Seguridad(Scanner scanner) {
        this.scanner       = scanner;
        this.libros        = new ArrayList<>();
        this.usuarios      = new ArrayList<>();
        this.prestamos     = new ArrayList<>();
        this.usuarioActivo = null;
        crearDatosDePrueba();
    }

    private void crearDatosDePrueba() {
        usuarios.add(new Usuario("1001", "Admin Sistema", Usuario.ROL_ADMINISTRADOR, "admin123"));
        usuarios.add(new Usuario("1002", "Ana Gomez",     Usuario.ROL_BIBLIOTECARIO, "biblio123"));
        usuarios.add(new Usuario("1003", "Carlos Perez",  Usuario.ROL_LECTOR,        "lector123"));

        libros.add(new Libro("978-1", "Clean Code",        "Robert Martin",   2008));
        libros.add(new Libro("978-2", "El Principito",     "Antoine Exupery", 1943));
        libros.add(new Libro("978-3", "Java Para Novatos", "Herbert Schildt", 2018));
    }
