package Biblioteca;

public class Usuario {
    private String documento;
    private String nombreCompleto;
    private int rol;
    private String contraseña;

    public Usuario(String documento, String nombreCompleto, int rol, String contraseña) {
        this.documento = documento;
        this.nombreCompleto = nombreCompleto;
        this.rol = rol;
        this.contraseña = contraseña;
    }

    public String getDocumento() {
        return documento;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public int getRol() {
        return rol;
    }

    public String getContraseña() {
        return contraseña;
    }

    public boolean puedeRegistrarPrestamos() {
        return rol == 1 || rol == 2;
    }

    public boolean esAdministrador() {
        return rol == 1;
        }

        return "Documento: " + documento +
                "Nombre: " + nombreCompleto +
                "Rol: " + tipoRol;
    }
}

