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

    public boolean iniciarSesion() {
        int maxIntentos   = ConfiguracionSistema.getInstance().getMaxIntentosLogin();
        int intentoActual = 0;

        System.out.println("\n==========================================");
        System.out.println("           INICIO DE SESION");
        System.out.println("==========================================");
        System.out.println(" Cuentas de prueba:");
        System.out.println("  Doc: 1001 | Clave: admin123  | ADMINISTRADOR");
        System.out.println("  Doc: 1002 | Clave: biblio123 | BIBLIOTECARIO");
        System.out.println("  Doc: 1003 | Clave: lector123 | LECTOR");
        System.out.println("==========================================");

