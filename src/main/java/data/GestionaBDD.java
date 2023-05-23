package data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Rober y Cris
 */
public class GestionaBDD {

    final String RUTA_BDD = ".\\res\\AmigosBDD.accdb";
    Connection c = null;

    public GestionaBDD() {
        conectarBDD();
    }

    public void conectarBDD() {
        try {
            // Register driver
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");

            String databaseURL = "jdbc:ucanaccess://" + RUTA_BDD;
            //"jdbc:ucanaccess://" + System.getProperty( "user.dir" )  + "\\Your-database-name.<mdb or accdb>";

            c = DriverManager.getConnection(databaseURL);
            System.out.println("Conexion establecida");

        } catch (Exception ee) {
            System.out.println("Error en la conexión con la BDD:\n" + ee);
        }
    }

    public void cerrarConexion() {

        if (c != null) {
            try {
                c.close();
                System.out.println("Se cerró la conexion.");
            } catch (SQLException ex) {
                Logger.getLogger(GestionaBDD.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public ResultSet consultarAmigos() {
        ResultSet rs = null;
        try {
            if (c.isClosed()) {
                this.conectarBDD();
            }
            Statement st = c.createStatement();
            rs = st.executeQuery("select * from amigos");
        } catch (SQLException ex) {
            Logger.getLogger(GestionaBDD.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            cerrarConexion();
        }
        return rs;

    }

    public ResultSet consultarPandillas() {
        ResultSet rs = null;
        try {
            if (c.isClosed()) {
                this.conectarBDD();
            }
            Statement st = c.createStatement();
            rs = st.executeQuery("select * from Pandillas");

        } catch (SQLException ex) {
            Logger.getLogger(GestionaBDD.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            cerrarConexion();
        }
        return rs;

    }
      public int consultarPandillasPorNombre(String nombre) {
        ResultSet rs = null;
        int cod=-1;
        try {
            if (c.isClosed()) {
                this.conectarBDD();
            }
            Statement st = c.createStatement();
            rs = st.executeQuery("select ID_Pandilla from Pandillas where Nombre_Pandilla= '" + nombre + "'");
            if(rs.next()){
                cod=rs.getInt(1);
            }

        } catch (SQLException ex) {
            Logger.getLogger(GestionaBDD.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            cerrarConexion();
        }
        return cod;
    }
    

    public ResultSet buscarAmigo(String nombre) {

        ResultSet rs = null;
        try {
            if (c.isClosed()) {
                this.conectarBDD();
            }
            String query = "SELECT ID_Amigo,Nombre_Amigo ,Direccion,Telefono,Aficiones "
                    + "FROM Amigos  "
                    + "WHERE Nombre_Amigo='" + nombre + "'";

            Statement st = c.createStatement();
            rs = st.executeQuery(query);
        } catch (SQLException ex) {
            Logger.getLogger(GestionaBDD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;
    }
      public ResultSet pandillasDeUnAmigo(int cod) {
        //con esta query buscamos todos los amigos pertenecientes a una pandilla
        ResultSet rs = null;
        try {
            if (c.isClosed()) {
                this.conectarBDD();
            }
            String query = "SELECT Pandillas.Nombre_Pandilla "
                    + "FROM Amigos,Amigos_Pandilla,Pandillas "
                    + "WHERE Amigos.ID_Amigo = Amigos_Pandilla.ID_Amigo and Pandillas.ID_Pandilla=Amigos_Pandilla.ID_Pandilla "
                    + "and Amigos.ID_Amigo=" + cod ;

            Statement st = c.createStatement();
            rs = st.executeQuery(query);
        } catch (SQLException ex) {
            Logger.getLogger(GestionaBDD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;
    }

    public ResultSet buscarAmigosDeUnaPandilla(String pandilla) {
        //con esta query buscamos todos los amigos pertenecientes a una pandilla
        ResultSet rs = null;
        try {
            if (c.isClosed()) {
                this.conectarBDD();
            }
            String query = "SELECT Amigos.ID_Amigo, Amigos.Nombre_Amigo, Amigos.Direccion, Amigos.Telefono, Amigos.Aficiones "
                    + "FROM Amigos,Amigos_Pandilla,Pandillas "
                    + "WHERE Amigos.ID_Amigo = Amigos_Pandilla.ID_Amigo and Pandillas.ID_Pandilla=Amigos_Pandilla.ID_Pandilla "
                    + "and Pandillas.Nombre_Pandilla='" + pandilla + "'";

            Statement st = c.createStatement();
            rs = st.executeQuery(query);
        } catch (SQLException ex) {
            Logger.getLogger(GestionaBDD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;
    }
    
    public ResultSet buscarAmigoPorPandilla(String nombre, String pandilla) {
        //con esta query haremos una busqueda que retorne un registro con los datos de un amigo perteneciente a una pandilla en concreto
        ResultSet rs = null;
        try {
            if (c.isClosed()) {
                this.conectarBDD();
            }
            String query = "SELECT Amigos.ID_Amigo, Amigos.Nombre_Amigo, Amigos.Direccion, Amigos.Telefono, Amigos.Aficiones "
                    + "FROM Amigos,Amigos_Pandilla,Pandillas "
                    + "WHERE Amigos.ID_Amigo = Amigos_Pandilla.ID_Amigo and Pandillas.ID_Pandilla=Amigos_Pandilla.ID_Pandilla "
                    + "and Pandillas.Nombre_Pandilla='" + pandilla + "'" + "and Amigos.Nombre_Amigo='" + nombre + "'";

            Statement st = c.createStatement();
            rs = st.executeQuery(query);

        } catch (SQLException ex) {
            Logger.getLogger(GestionaBDD.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            cerrarConexion();
        }
        return rs;
    }

    public int agregarAmigo(Amigo a) throws Exception {
        int codAmigo = 0;
        try {
            if (c.isClosed()) {
                this.conectarBDD();
            }
            if (!existeAmigo(a.getTelefono()) && !nombreRepetido(a.getNombre())) {

                String query = "INSERT INTO amigos (Nombre_Amigo,Direccion,Telefono,Aficiones) VALUES (?,?,?,?)";
                PreparedStatement st = c.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
                st.setString(1, a.getNombre());
                st.setString(2, a.getDireccion());
                st.setInt(3, a.getTelefono());
                st.setString(4, a.getAficiones());
                st.executeUpdate();

                ResultSet rsID = st.getGeneratedKeys();
                rsID.next();
                codAmigo = rsID.getInt(1);

            } else {
                throw new Exception("El telefono ya existe o ya existe alguien con el mismo nombre.");
            }

        } catch (SQLException ex) {
            Logger.getLogger(GestionaBDD.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            cerrarConexion();
        }
        return codAmigo;
    }

    public void agregarPandilla(Pandilla p) throws Exception {
        try {
            if (c.isClosed()) {
                this.conectarBDD();
            }
            if (!existePandilla(p.getNombrePandilla())) {

                String query = "INSERT INTO pandillas (Nombre_Pandilla,Lugar_Vacaciones) VALUES (?,?)";
                PreparedStatement st = c.prepareStatement(query);
                st.setString(1, p.getNombrePandilla());
                st.setString(2, p.getLugarVacaciones());
                st.executeUpdate();
                
            } else {
                throw new Exception("La pandilla ya existe.");
            }

        } catch (SQLException ex) {
            Logger.getLogger(GestionaBDD.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            cerrarConexion();
        }
    }
     public void agregarAmigoAPandilla(int idAmigo,int idPandilla) {
        try {
            if (c.isClosed()) {
                this.conectarBDD();
            }
            

                String query = "INSERT INTO Amigos_Pandilla (ID_Amigo,ID_Pandilla) VALUES (?,?)";
                PreparedStatement st = c.prepareStatement(query);
                st.setInt(1, idAmigo);
                st.setInt(2, idPandilla);
                st.executeUpdate();
            

        } catch (SQLException ex) {
            Logger.getLogger(GestionaBDD.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            cerrarConexion();
        }
    }
       public void borrarAmigodePandilla(int idAmigo,int idPandilla)  {
        try {
            if (c.isClosed()) {
                this.conectarBDD();
            }
            

                String query = "DELETE from Amigos_Pandilla WHERE ID_Amigo ="+idAmigo+" and ID_Pandilla= "+idPandilla;
                PreparedStatement st = c.prepareStatement(query);
                st.executeUpdate();
            

        } catch (SQLException ex) {
            Logger.getLogger(GestionaBDD.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            cerrarConexion();
        }
    }

    public boolean modificarAmigo(int cod, Amigo a) {
        try {
            if (c.isClosed()) {
                this.conectarBDD();
            }
            if (existeTlfnYCodigo(a.getTelefono(), cod) && existeNombreYCodigo(a.getNombre(), cod)) {

                String query = "update amigos set Nombre_Amigo=?,Direccion=?,Telefono=?,Aficiones=? where ID_Amigo= " + cod;
                PreparedStatement st = c.prepareStatement(query);
                st.setString(1, a.getNombre());
                st.setString(2, a.getDireccion());
                st.setInt(3, a.getTelefono());
                st.setString(4, a.getAficiones());
                st.executeUpdate();
                return true;

            } else {
                return false;
            }

        } catch (SQLException ex) {
            Logger.getLogger(GestionaBDD.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            cerrarConexion();
        }
        return false;
    }

    public boolean modificarPandilla(int codPandilla, Pandilla p) {
        try {
            if (c.isClosed()) {
                this.conectarBDD();
            }
            if (existePandillaYCodigo(p.getNombrePandilla(), codPandilla)) {

                String query = "update Pandillas set Nombre_Pandilla=?,Lugar_Vacaciones=? where ID_Pandilla= " + codPandilla;
                PreparedStatement st = c.prepareStatement(query);
                st.setString(1, p.getNombrePandilla());
                st.setString(2, p.getLugarVacaciones());
                st.executeUpdate();
                return true;

            } else {
                return false;
            }

        } catch (SQLException ex) {
            Logger.getLogger(GestionaBDD.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            cerrarConexion();
        }
        return false;
    }

    public void borrarAmigo(int codigo) throws Exception {
        try {
            if (c.isClosed()) {
                this.conectarBDD();
            }

            String query = "delete from amigos where ID_Amigo= " + codigo;
            PreparedStatement st = c.prepareStatement(query);
            if (st.executeUpdate() > 0) {
                System.out.println("Amigo eliminado.");
            } else {
                throw new Exception("No existe amigo");
            }

        } catch (SQLException ex) {
            Logger.getLogger(GestionaBDD.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            cerrarConexion();
        }
    }

    public void borrarPandilla(int codPandilla) throws Exception {
        try {
            if (c.isClosed()) {
                this.conectarBDD();
            }
            String query = "delete from pandillas where ID_Pandilla=" + codPandilla;
            PreparedStatement st = c.prepareStatement(query);
            if (st.executeUpdate() > 0) {
                System.out.println("Pandilla eliminada.");
            } else {
                throw new Exception("No existe la pandilla");
            }

        } catch (SQLException ex) {
            Logger.getLogger(GestionaBDD.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            cerrarConexion();
        }
    }

    public boolean existeAmigo(int telefono) {

        try {
            if (c.isClosed()) {
                this.conectarBDD();
            }
            Statement st = c.createStatement();
            ResultSet rs = st.executeQuery("select telefono from amigos where telefono=" + telefono);

            if (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(GestionaBDD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean existePandilla(String nombrePandilla) {
        try {
            if (c.isClosed()) {
                this.conectarBDD();
            }
            Statement st = c.createStatement();
            ResultSet rs = st.executeQuery("select Nombre_Pandilla from Pandillas where Nombre_Pandilla= '" + nombrePandilla + "'");

            if (rs.next() || nombrePandilla == null) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(GestionaBDD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;

    }

    public boolean existePandillaYCodigo(String nombrePandilla, int codigo) {
        try {
            if (c.isClosed()) {
                this.conectarBDD();
            }
            Statement st = c.createStatement();
            ResultSet rs = st.executeQuery("select ID_Pandilla from Pandillas where Nombre_Pandilla= '" + nombrePandilla + "'");

            if (rs.next()) {
                if (codigo == rs.getInt(1)) {
                    return true;
                } else {
                    return false;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(GestionaBDD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;

    }

    private boolean existeTlfnYCodigo(int telefono, int cod) {
        try {
            if (c.isClosed()) {
                this.conectarBDD();
            }
            Statement st = c.createStatement();
            ResultSet rs = st.executeQuery("select ID_Amigo from amigos where telefono=" + telefono);

            if (rs.next()) {
                if (cod == rs.getInt(1)) {
                    return true;
                } else {
                    return false;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(GestionaBDD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }

    private boolean nombreRepetido(String nombre) {

        try {
            if (c.isClosed()) {
                this.conectarBDD();
            }
            Statement st = c.createStatement();
            ResultSet rs = st.executeQuery("select Nombre_Amigo from amigos where Nombre_Amigo='" + nombre + "'");

            if (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(GestionaBDD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    private boolean existeNombreYCodigo(String nombre, int cod) {
        try {
            if (c.isClosed()) {
                this.conectarBDD();
            }
            Statement st = c.createStatement();
            ResultSet rs = st.executeQuery("select ID_Amigo from amigos where Nombre_Amigo='" + nombre + "'");

            if (rs.next()) {
                if (cod == rs.getInt(1)) {
                    return true;
                } else {
                    return false;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(GestionaBDD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }

}
