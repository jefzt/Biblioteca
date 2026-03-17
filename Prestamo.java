import java.time.LocalDate;
public class Prestamo {

    private String    documentoUsuario;
    private String    isbnLibro;
    private LocalDate fechaPrestamo;
    private LocalDate fechaDevolucion;
    private boolean   activo;

    public Prestamo(String documentoUsuario, String isbnLibro) {
        this.documentoUsuario = documentoUsuario;
        this.isbnLibro        = isbnLibro;
        this.fechaPrestamo    = LocalDate.now();
        this.fechaDevolucion  = null;
        this.activo           = true;
    }

 
