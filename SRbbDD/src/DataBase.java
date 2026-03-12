import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
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

    // Mètodes
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

    // Retorna la informació d'una casella en base a un filtre
    public String getInfo(String nomTaula, String nomColumna, String nomClau, String identificador){
        try{ // query
            String q =  " SELECT " + nomColumna +   // SELECT nomColumna AS N (canviar/possar nom N)
                        " FROM " + nomTaula +       // fins aquí, retornaria tota la columna
                        " WHERE "+ nomClau  + " = '" + identificador + "' "; // id entre comilles simples
                       // ORDER BY nomColumna ASC, dni ASC
            System.out.println(q);
            ResultSet rs= query.executeQuery(q); // Conjunt de resultats (com una col·lecció)
            rs.next();
            return rs.getString(nomColumna);
        }
        catch(Exception e){
            System.out.println(e);
        }
        return "";
    }

    // Retorna el número total de files d'una taula
    public int getNumFilesTaula(String nomTaula){
        String q = "SELECT COUNT(*) AS num FROM "+ nomTaula; // * vol dir tot
        try{
            ResultSet rs = query.executeQuery(q);
            rs.next();
            return rs.getInt("num");
        }
        catch(Exception e){
            System.out.println(e);
        }
        return 0;
    }

    // Retorna totes les caselles d'una columna
    public String[] getInfoArray(String nomTaula, String nomColumna){
        int n = getNumFilesTaula(nomTaula);
        String[] info = new String[n];
        String q = " SELECT " + nomColumna +
                   " FROM " + nomTaula +
                   " ORDER BY " + nomColumna + " ASC"; // ASC: ascendentment, DES: descendentment
        System.out.println(q);
        try{
            ResultSet rs = query.executeQuery(q);
            int f=0;    // Recorregut col·lecció ResultSet (múltiples dades)
            while(rs.next()){
                info[f] = rs.getString(nomColumna);
                f++;
            }
        }
        catch(Exception e){
            System.out.println(e);
        }
        return info;
    }

    public String[][] getInfoArray2D(){
        int nf = getNumFilesTaula("unitat");
        String[][] info = new String[nf][3];
        String q = "SELECT numero, nom, curs FROM unitat ORDER BY numero ASC";
        System.out.println(q);
        try{
            ResultSet rs = query.executeQuery(q);
            int f=0;
            while(rs.next()){
                info[f][0] = rs.getString("correu"); // String.valueOf( rs.getInt("numero"));
                info[f][1] = rs.getString("contraseña");
                info[f][2] = rs.getString("descripción"); // String.valueOf( rs.getInt("curs"));
                f++;
            }
            return info;
        }
        catch(Exception e){
            System.out.println(e);
        }

        return info;
    }

    public void printArray1D(String[] info){
        System.out.println();
        for(int i=0;i<info.length;i++){
            System.out.printf("%d: %s.\n", i, info[i]);
        }
    }
    public void printArray2D(String[][] info){
        System.out.println();
        for(int i=0;i<info.length;i++){
            System.out.printf("%d: ", i);
            for(int j=0;j<info.length;j++){
                System.out.printf("%s \t", info[i][j]);
            }
            System.out.println();
        }
    }

    // Retorn contrasenya d'un client amb un cert correu
    public String getPasswordAmbCorreu(String correu){
        String q = "SELECT Contraseña FROM Usuario WHERE CorreoElectrónico = '" + correu + "'";
        System.out.println(q);
        try{
            ResultSet rs = query.executeQuery(q);
            rs.next();
            String contraseña = rs.getString("Contraseña");
            return contraseña;
        }
        catch(Exception e){
            System.out.println(e);
        }
        return null;
    }

    public String[] getCorreosTodosUsuarios(){
        String q = "SELECT CorreoElectrónico FROM Usuario ORDER BY CorreoElectrónico ASC";
        System.out.println(q);
        try{
            int numFiles = getNumFilesTaula("Usuario");
            String[] info = new String[numFiles];
            ResultSet rs = query.executeQuery(q);
            int f=0;
            while(rs.next()){
                info[f] = rs.getString("CorreoElectrónico");
                f++;
            }
            return info;
        }
        catch(Exception e){
            System.out.println(e);
        }
        return null;
    }
}
