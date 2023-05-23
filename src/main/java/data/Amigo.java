
package data;

/**
 *
 * @author Rober y Cris
 */
public class Amigo {
   String nombre,direccion,aficiones;
   int telefono;

    public Amigo(String nombre, String direccion,int telefono,String aficiones) throws Exception {
        
        setNombre(nombre);
        setDireccion(direccion);
        setAficiones(aficiones);
        setTelefono(telefono);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) throws Exception {
        
        this.nombre = Utilidades.comprobarNombre(nombre);
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getAficiones() {
        return aficiones;
    }

    public void setAficiones(String aficiones) {
        this.aficiones = aficiones;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) throws Exception {
        this.telefono = Utilidades.validarTelefono(telefono);
    }
    
   
}
