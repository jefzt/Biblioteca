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

    public String    getDocumentoUsuario() { return documentoUsuario; }
    public String    getIsbnLibro()        { return isbnLibro;        }
    public LocalDate getFechaPrestamo()    { return fechaPrestamo;    }
    public boolean   isActivo()            { return activo;           }

    public void registrarDevolucion() {
        this.activo          = false;
        this.fechaDevolucion = LocalDate.now();
    }

    @Override
    public String toString() {
        return "-------------------------------"
             + "\n  Usuario  : " + documentoUsuario
             + "\n  ISBN     : " + isbnLibro
             + "\n  Prestado : " + fechaPrestamo
             + "\n  Devuelto : " + (fechaDevolucion != null ? fechaDevolucion : "Pendiente")
             + "\n  Estado   : " + (activo ? "ACTIVO" : "DEVUELTO")
             + "\n-------------------------------";
    }
}
