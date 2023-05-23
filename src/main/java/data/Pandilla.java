
package data;

/**
 *
 * @author Rober y Cris
 */
public class Pandilla {
    String nombrePandilla,lugarVacaciones;

    public Pandilla(String nombrePandilla, String lugarVacaciones) throws Exception {
        setNombrePandilla(nombrePandilla);
        setLugarVacaciones(lugarVacaciones);
    }

    public String getNombrePandilla() {
        return nombrePandilla;
    }

    public void setNombrePandilla(String nombrePandilla) throws Exception {
        this.nombrePandilla = Utilidades.comprobarNombre(nombrePandilla);
    }

    public String getLugarVacaciones() {
        return lugarVacaciones;
    }

    public void setLugarVacaciones(String lugarVacaciones) throws Exception {
        this.lugarVacaciones = Utilidades.comprobarNombre(lugarVacaciones);
    }
    
}
