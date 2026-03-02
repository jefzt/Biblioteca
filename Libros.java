public class Libros {
     private String isbn;
    private String titulo;
    private String autor;
    private int añoPublicacion;
    private boolean disponible;

    public Libro(String isbn, String titulo, String autor, int añoPublicacion) {
        this.isbn = isbn;
        this.titulo = titulo;
        this.autor = autor;
        this.añoPublicacion = añoPublicacion;
        this.disponible = true;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getAutor() {
        return autor;
    }

    public int getAnioPublicacion() {
        return añoPublicacion;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    @Override
    public String toString() {
        String estado = disponible ? "DISPONIBLE" : "PRESTADO";
        return "  ISBN  : " + isbn + "\n"
                + "  Titulo: " + titulo + "\n"
                + "  Autor : " + autor + "\n"
                + "  Anio  : " + añoPublicacion + "\n"
                + "  Estado: " + estado + "\n";
    
}

