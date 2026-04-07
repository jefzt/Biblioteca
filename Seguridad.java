import java.util.ArrayList;
import java.util.Scanner;
import java.time.LocalDate; 

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

        System.out.println("==========================================");
        System.out.println("           INICIO DE SESION");
        System.out.println("==========================================");
        System.out.println(" Cuentas de prueba:");
        System.out.println("  Doc: 1001 | Clave: admin123  | ADMINISTRADOR");
        System.out.println("  Doc: 1002 | Clave: biblio123 | BIBLIOTECARIO");
        System.out.println("  Doc: 1003 | Clave: lector123 | LECTOR");
        System.out.println("==========================================");
        while (intentoActual < maxIntentos) {
            System.out.print("Documento : ");
            String documento = scanner.nextLine();
            System.out.print("Contrasena: ");
            String contrasena = scanner.nextLine();

            Usuario encontrado = buscarUsuario(documento);

            if (encontrado != null && encontrado.getContrasena().equals(contrasena)) {
                usuarioActivo = encontrado;
                System.out.println("Bienvenido/a: " + usuarioActivo.getNombreCompleto()
                                 + " [" + usuarioActivo.getRol() + "]");
                return true;
            }

            intentoActual++;
            int restantes = maxIntentos - intentoActual;
            if (restantes > 0) {
                System.out.println("ERROR: Credenciales incorrectas. Intentos restantes: " + restantes);
            }
        }

        System.out.println("==========================================");
        System.out.println(" BLOQUEADO: demasiados intentos fallidos.");
        System.out.println(" Contacte al administrador.");
        System.out.println("==========================================");
        return false;
    }
        
    public void cerrarSesion() {
        System.out.println("Sesion cerrada. Hasta luego, " + usuarioActivo.getNombreCompleto() + ".");
        usuarioActivo = null;
    }

    public Usuario getUsuarioActivo() { 
        return usuarioActivo; 
    }
    // LIBROS
    public void registrarLibro() {
        if (!usuarioActivo.puedeGestionarPrestamos()) {
            System.out.println("ACCESO DENEGADO: No tiene permiso para registrar libros.");
            return;
        }

        System.out.println("-- Registrar Libro --");
        
        String isbn = input("ISBN: ");
        String titulo = input("Titulo: ");
        String autor = input("Autor: ");
        String añoTxt = input("Año publicacion: ");
        
        if (isBlank(isbn, titulo, autor)) {
            System.out.println("ERROR: Todos los campos son obligatorios.");
            return;
        }

        if (buscarLibro(isbn) != null) {
            System.out.println("ERROR: Ya existe un libro con ISBN: " + isbn);
            return;
        }

          int año = parseAño(añoTxt);
        
        if (año == 0 || año < 1000 || año > LocalDate.now().getYear()) {
          System.out.println("ERROR: El año no es valido.");
            return;
        }

        libros.add(new Libro(isbn, titulo, autor, año));
        System.out.println("OK: Libro registrado -> " + titulo);
    }

    public void listarLibros() {
        if (libros.isEmpty()) {
            System.out.println("AVISO: No hay libros registrados.");
            return;
        }

        System.out.println("\n=== LIBROS (" + libros.size() + " en total) ===");
        libros.forEach(System.out::println);
    }

    public void buscarLibroPorConsola() {
        System.out.print("ISBN a buscar: ");
        
        Libro l = buscarLibro(scanner.nextLine());
        
        if (l != null) {
            System.out.println("Libro encontrado:" + l);
        } else {
            System.out.println("ERROR: Libro no encontrado.");
        }
    }

    public void eliminarLibro() {
        if (!usuarioActivo.esAdministrador()) {
            System.out.println("ACCESO DENEGADO: Solo ADMINISTRADOR puede eliminar libros.");
            return;
        }

        System.out.print("ISBN del libro a eliminar: ");
        
        Libro l = buscarLibro(scanner.nextLine());
        
        if (l == null) {
            System.out.println("ERROR: Libro no encontrado.");
            return;
        }
        
        if (!l.isDisponible()) {
            System.out.println("ERROR: No se puede eliminar un libro prestado.");
            return;
        }

        libros.remove(l);
        System.out.println("OK: Libro eliminado -> " + l.getTitulo());
    }

    private Libro buscarLibro(String isbn) {
        return libros.stream()
            .filter(l -> l.getIsbn().equalsIgnoreCase(isbn.trim()))
            .findFirst()
            .orElse(null);
    }
    // USUARIOS
    public void registrarUsuario() {
        if (!usuarioActivo.esAdministrador()) {
            System.out.println("ACCESO DENEGADO: Solo ADMINISTRADOR puede registrar usuarios.");
            return;
        }

        System.out.println("-- Registrar Usuario --");
        
        String doc = input("Documento: ");
        String nom = input("Nombre completo: ");
        
        System.out.println("Roles validos: ADMINISTRADOR / BIBLIOTECARIO / LECTOR");
        
        String rol = input("Rol: ");
        String clave = input("Contrasena: ");

        if (isBlank(doc, nom, rol, clave)) {
            System.out.println("ERROR: Todos los campos son obligatorios.");
            return;
        }

        if (buscarUsuario(doc) != null) {
            System.out.println("ERROR: Ya existe un usuario con documento: " + doc);
            return;
        }

        String rolU = rol.trim().toUpperCase();
        
        if (!isRolValido(rolU)) {
            System.out.println("ERROR: Rol no valido. Use: ADMINISTRADOR, BIBLIOTECARIO o LECTOR");
            return;
        }

        usuarios.add(new Usuario(doc, nom, rolU, clave));
        System.out.println("OK: Usuario registrado -> " + nom + " [" + rolU + "]");
    }

    public void listarUsuarios() {
        if (!usuarioActivo.esAdministrador()) {
            System.out.println("ACCESO DENEGADO: Solo ADMINISTRADOR puede ver usuarios.");
            return;
        }

        if (usuarios.isEmpty()) {
            System.out.println("AVISO: No hay usuarios.");
            return;
        }

        System.out.println("=== USUARIOS (" + usuarios.size() + " en total) ===");
        usuarios.forEach(System.out::println);
    }

    public void buscarUsuarioPorConsola() {
        if (!usuarioActivo.esAdministrador()) {
            System.out.println("ACCESO DENEGADO: Solo ADMINISTRADOR puede buscar usuarios.");
            return;
        }

        System.out.print("Documento a buscar: ");
        
        Usuario u = buscarUsuario(scanner.nextLine());
        
        if (u != null) {
            System.out.println("\nUsuario encontrado:\n" + u);
        } else {
            System.out.println("ERROR: Usuario no encontrado.");
        }
    }

    public void eliminarUsuario() {
        if (!usuarioActivo.esAdministrador()) {
            System.out.println("ACCESO DENEGADO: Solo ADMINISTRADOR puede eliminar usuarios.");
            return;
        }

        System.out.print("Documento del usuario a eliminar: ");
        
        String doc = scanner.nextLine();
        Usuario u = buscarUsuario(doc);
        
        if (u == null) {
            System.out.println("ERROR: Usuario no encontrado.");
            return;
        }

        if (contarPrestamosActivos(doc) > 0) {
            System.out.println("ERROR: El usuario tiene prestamos activos. Registre la devolucion primero.");
            return;
        }

        usuarios.remove(u);
        System.out.println("OK: Usuario eliminado -> " + u.getNombreCompleto());
    }

    private Usuario buscarUsuario(String doc) {
        return usuarios.stream()
            .filter(u -> u.getDocumento().equalsIgnoreCase(doc.trim()))
            .findFirst()
            .orElse(null);
    }
    // PRESTAMOS
    public void registrarPrestamo() {
        if (!usuarioActivo.puedeGestionarPrestamos()) {
            System.out.println("ACCESO DENEGADO: Solo BIBLIOTECARIO o ADMINISTRADOR pueden registrar prestamos.");
            return;
        }

        System.out.println("-- Registrar Prestamo --");
        
        System.out.print("Documento del usuario lector: ");
        String docLector = scanner.nextLine();
        
        System.out.print("ISBN del libro: ");
        String isbn = scanner.nextLine();

        Usuario lector = buscarUsuario(docLector);
        
        if (lector == null) {
            System.out.println("ERROR: Usuario no encontrado.");
            return;
        }

        Libro libro = buscarLibro(isbn);
        
        if (libro == null) {
            System.out.println("ERROR: Libro no encontrado.");
            return;
        }

        if (!libro.isDisponible()) {
            System.out.println("ERROR: El libro '" + libro.getTitulo() + "' no esta disponible.");
            return;
        }

        int max = ConfiguracionSistema.getInstance().getMaxPrestamos();
        
        if (lector.getRol().equals(Usuario.ROL_LECTOR) && contarPrestamosActivos(docLector) >= max) {
            System.out.println("ERROR: " + lector.getNombreCompleto() + " ya tiene el maximo de " + max + " prestamos activos.");
            return;
        }

        prestamos.add(new Prestamo(docLector, isbn));
        libro.setDisponible(false);
        
        System.out.println("OK: Prestamo registrado.");
        System.out.println("    Libro: " + libro.getTitulo());
        System.out.println("    Para: " + lector.getNombreCompleto());
    }

    public void registrarDevolucion() {
        if (!usuarioActivo.puedeGestionarPrestamos()) {
            System.out.println("ACCESO DENEGADO: Solo BIBLIOTECARIO o ADMINISTRADOR pueden registrar devoluciones.");
            return;
        }

        System.out.print("ISBN del libro a devolver: ");
        
        String isbn = scanner.nextLine();
        
        Prestamo p = prestamos.stream()
            .filter(pr -> pr.getIsbnLibro().equalsIgnoreCase(isbn.trim()) && pr.isActivo())
            .findFirst()
            .orElse(null);

        if (p == null) {
            System.out.println("ERROR: No hay prestamo activo para ese ISBN.");
            return;
        }

        p.registrarDevolucion();
        
        Libro l = buscarLibro(isbn);
        if (l != null) l.setDisponible(true);
        
        System.out.println("OK: Devolucion registrada -> " + (l != null ? l.getTitulo() : isbn));
    }

    public void consultarPrestamosActivos() {
        boolean hay = prestamos.stream().anyMatch(Prestamo::isActivo);
        
        System.out.println("=== PRESTAMOS ACTIVOS ===");
        
        prestamos.stream()
            .filter(Prestamo::isActivo)
            .forEach(System.out::println);
            
        if (!hay) System.out.println("AVISO: No hay prestamos activos.");
    }

    private int contarPrestamosActivos(String doc) {
        return (int) prestamos.stream()
            .filter(p -> p.getDocumentoUsuario().equals(doc) && p.isActivo())
            .count();
    }
    // HELPERS
    
    private String input(String msg) {
        System.out.print(msg);
        return scanner.nextLine();
    }

    private boolean isBlank(String... s) {
        for (String x : s) {
            if (x == null || x.trim().isEmpty()) return true;
        }
        return false;
    }

    private boolean isRolValido(String r) {
        return r.equals(Usuario.ROL_ADMINISTRADOR) 
            || r.equals(Usuario.ROL_BIBLIOTECARIO) 
            || r.equals(Usuario.ROL_LECTOR);
    }

    private int parseAño(String s) {
        try {
            return Integer.parseInt(s.trim());
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
