public class ConfiguracionSistema {
    
    private static ConfiguracionSistema unicaInstancia = null;

    private ConfiguracionSistema() {}

    public static ConfiguracionSistema getInstance() {
        if (unicaInstancia == null) {
            unicaInstancia = new ConfiguracionSistema();
        }
        return unicaInstancia;
    }
}
