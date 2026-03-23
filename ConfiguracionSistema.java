public class ConfiguracionSistema {

    private static final String NOMBRE_BIBLIOTECA  = "Biblioteca Municipal SENA";
    private static final String VERSION_SISTEMA    = "1.0";
    private static final int    MAX_PRESTAMOS      = 3;
    private static final int    MAX_INTENTOS_LOGIN = 3;
    
    private static ConfiguracionSistema unicaInstancia = null;

    private ConfiguracionSistema() {}

    public static ConfiguracionSistema getInstance() {
        if (unicaInstancia == null) {
            unicaInstancia = new ConfiguracionSistema();
        }
        return unicaInstancia;
    }

    public String getNombreBiblioteca() { return NOMBRE_BIBLIOTECA;  }
    public String getVersionSistema()   { return VERSION_SISTEMA;    }
    public int    getMaxPrestamos()     { return MAX_PRESTAMOS;      }
    public int    getMaxIntentosLogin() { return MAX_INTENTOS_LOGIN; }

    @Override
    public String toString() {
        return "\n----- Configuracion del Sistema -----"
             + "\n  Nombre    : " + NOMBRE_BIBLIOTECA
             + "\n  Version   : " + VERSION_SISTEMA
             + "\n  Prestamos : " + MAX_PRESTAMOS
             + "\n  Intentos  : " + MAX_INTENTOS_LOGIN
             + "\n-------------------------------------";
    }
}
