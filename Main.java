public class Main {
    public static void main(String[] args) {
    
        Usuario admin = new Usuario("1001", "Ana Lopez", 1, "1234");
        Usuario bibliotecario = new Usuario("1002", "Carlos Perez", 2, "1234");
        Usuario lector = new Usuario("1003", "Maria Gomez", 3, "1234");

        Libro libro1 = new Libro("978-1", "Clean Code", "Robert Martin", 2008);
        Libro libro2 = new Libro("978-2", "Java Basico", "Juan Diaz", 2020);

        System.out.println("USUARIOS");
        System.out.println(admin);
        System.out.println(bibliotecario);
        System.out.println(lector);

        System.out.println("LIBROS");
        System.out.println(libro1);
        System.out.println(libro2);

        Prestamo prestamo1 = new Prestamo(
                lector.getDocumento(),
                libro1.getIsbn(),
                "02/03/2026"
        );

        libro1.setDisponible(false);   
}

