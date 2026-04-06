import java.util.Scanner;
public class Main {

    public static void main(String[] args) {
        ConfiguracionSistema config = ConfiguracionSistema.getInstance();
        System.out.println(config);

        Scanner   scanner   = new Scanner(System.in);
        Seguridad seguridad = new Seguridad(scanner);

        boolean continuar = true;
        while (continuar) {
            boolean loginExitoso = seguridad.iniciarSesion();

            if (loginExitoso) {
                mostrarMenu(seguridad, scanner, config);
                System.out.print("¿Desea iniciar sesion nuevamente? (s/n): ");
                continuar = scanner.nextLine().equalsIgnoreCase("s");
            } else {
                continuar = false;
            }
        }

        System.out.println("\nSistema cerrado. Hasta luego.");
        scanner.close();
    }

    private static void mostrarMenu(Seguridad seguridad,
                                     Scanner scanner,
                                     ConfiguracionSistema config) {
        boolean salir = false;

        while (!salir) {
System.out.println("==========================================");
            System.out.println("  " + config.getNombreBiblioteca());
            System.out.println("  v" + config.getVersionSistema());
            System.out.println("  Usuario : " + seguridad.getUsuarioActivo().getNombreCompleto());
            System.out.println("  Rol     : " + seguridad.getUsuarioActivo().getRol());
            System.out.println("==========================================");
            System.out.println("  LIBROS");
            System.out.println("    1. Registrar libro");
            System.out.println("    2. Listar libros");
            System.out.println("    3. Buscar libro por ISBN");
            System.out.println("    4. Eliminar libro");
            System.out.println("  USUARIOS");
            System.out.println("    5. Registrar usuario");
            System.out.println("    6. Listar usuarios");
            System.out.println("    7. Buscar usuario por documento");
            System.out.println("    8. Eliminar usuario");
            System.out.println("  PRESTAMOS");
            System.out.println("    9. Registrar prestamo");
            System.out.println("   10. Registrar devolucion");
            System.out.println("   11. Ver prestamos activos");
            System.out.println("  SESION");
            System.out.println("   12. Cerrar sesion");
            System.out.println("==========================================");
            System.out.print("Opcion: ");

            switch (scanner.nextLine()) {
                case "1":  seguridad.registrarLibro();            break;
                case "2":  seguridad.listarLibros();              break;
                case "3":  seguridad.buscarLibroPorConsola();     break;
                case "4":  seguridad.eliminarLibro();             break;
                case "5":  seguridad.registrarUsuario();          break;
                case "6":  seguridad.listarUsuarios();            break;
                case "7":  seguridad.buscarUsuarioPorConsola();   break;
                case "8":  seguridad.eliminarUsuario();           break;
                case "9":  seguridad.registrarPrestamo();         break;
                case "10": seguridad.registrarDevolucion();       break;
                case "11": seguridad.consultarPrestamosActivos(); break;
                case "12": seguridad.cerrarSesion(); salir = true; break;
                default:   System.out.println("AVISO: Opcion no valida.");
            }
        }
    }
}
