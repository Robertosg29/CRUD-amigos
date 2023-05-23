
package data;
/**
 *
 * @author Roberto Sánchez y Cristina Barrientos
 */

public abstract class Utilidades {

   public static String comprobarNombre(String nombre) throws Exception {

        if (nombre.length() > 20 || nombre.length() < 3) {
            throw new Exception("El nombre tiene que estar entre 3 y 20 caracteres.");
           
        } else {
            if (nombre.charAt(0) == ' ' || nombre.charAt(nombre.length() - 1) == ' ') {
                throw new Exception("No se pueden introducir espacios en primer y último lugar.");
                
            }
            for (int i = 0; i < nombre.length(); i++) {
                if (!Character.isAlphabetic(nombre.charAt(i)) && nombre.charAt(i) != ' ') {
                    throw new Exception("Solo puedes introducir letras en el nombre.");
                }
            }
            for (int i = 0; i < nombre.length() - 2; i++) {
                if (nombre.charAt(i) == ' ' && nombre.charAt(i + 1) == ' ') {
                    throw new Exception("No puedes introducir dos espacios seguidos.");
                    
                } else if (nombre.charAt(i) == nombre.charAt(i + 1) && nombre.charAt(i + 1) == nombre.charAt(i + 2)) {
                    throw new Exception("No puedes introducir el mismo carácter 3 veces seguidas.");
                }
            }
        }return nombre;
    }
    public static int validarTelefono(int t) throws Exception{
        String tf=""+t;
        for (int i = 0; i < tf.length(); i++) {
                if(!Character.isDigit(tf.charAt(i))){
                    throw new Exception("Solo puedes introducir números.");
                }
        } if(tf.length()!=9){
           throw new Exception("El número debe tener 9 dígitos.");
        }return t;
    }
}
