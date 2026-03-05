import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DataBase {

    // Atributos o propiedades

    Connection c;                          // Variable de conexión
    Statement query;                       // Variable de consulta
    String user, password, dataBaseName;   // Datos de conexión

    boolean connectat = false;  // Estado de la conexión

    // Constructor
    public DataBase(String user, String password, String dataBaseName) {
        this.user = user;
        this.password = password;
        this.dataBaseName = dataBaseName;
    }

    //
    public void connect(){
        try {
            c = DriverManager.getConnection("jdbc:mysql://localhost:8889/"+dataBaseName, user, password);
            query = c.createStatement();
            System.out.println("Connectat a la BBDD! :) ");
            connectat = true;
        }
        catch(Exception e) {
            System.out.println(e);
        }
    }
}
