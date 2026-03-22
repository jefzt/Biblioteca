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
                System.out.print("\n¿Desea iniciar sesion nuevamente? (s/n): ");
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
