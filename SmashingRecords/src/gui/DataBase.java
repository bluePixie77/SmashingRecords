package gui;

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

    // Métodos
    public void connect(){
        try {
            c = DriverManager.getConnection("jdbc:mysql://localhost:8889/"+dataBaseName, user, password);
            query = c.createStatement();
            System.out.println("Conectado a la BBDD! :) ");
            connectat = true;
        }
        catch(Exception e) {
            System.out.println(e);
        }
    }

    // Retorno de la información de una casilla en base a un filtro
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

    // Retorno del número total de filas de una tabla
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

    // Retorno de todas las casillas de una columna
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

    public String[][] getInfoArrayPetit2DConcert(){
        int nf = getNumFilesTaula("Concierto");
        String[][] info = new String[nf][2];
        String q = "SELECT Título, Artista FROM Concierto ORDER BY Artista ASC";
        System.out.println(q);
        try{
            ResultSet rs = query.executeQuery(q);
            int f=0;
            while(rs.next()){
                info[f][0] = rs.getString("Título"); // String.valueOf( rs.getInt("numero"));
                info[f][1] = rs.getString("Artista");
                //info[f][2] = rs.getString("descripción"); // String.valueOf( rs.getInt("curs"));
                f++;
            }
            return info;
        }
        catch(Exception e){
            System.out.println(e);
        }
        return info;
    }

    /*
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
     */
    /*
    public String[][] getInfoTaulaUnitatCurs(int curs){
        int numFiles = getNumRowsQuery("SELECT COUNT(*) AS n FROM unitat WHERE curs = '"+curs+"'");
        int numCols  = 3;
        String[][] info = new String[numFiles][numCols];
        try {
            ResultSet rs = query.executeQuery( "SELECT numero, nom, curs FROM unitat WHERE curs= '"+curs+"'");
            int nr = 0;
            while (rs.next()) {
                info[nr][0] = String.valueOf(rs.getInt("numero"));
                info[nr][1] = rs.getString("nom");
                info[nr][2] = String.valueOf(rs.getInt("curs"));
                nr++;
            }
            return info;
        }
        catch(Exception e) {
            System.out.println(e);
            return null;
        }
    }
     */

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
            for(int j=0;j<info[i].length;j++){
                System.out.printf("%s \t", info[i][j]);
            }
            System.out.println();
        }
    }

    // Retorno contraseña cliente con un cierto correo
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

    public String[][] getInfoTotsUsuarios(){
        String q = "SELECT CorreoElectrónico, Contraseña, Descripción FROM Usuario ORDER BY CorreoElectrónico ASC";
        System.out.println(q);
        try{
            int numFiles = getNumFilesTaula("Usuario");
            String[][] info = new String[numFiles][4];
            ResultSet rs = query.executeQuery(q);
            int f=0;
            while(rs.next()){
                info[f][0] = rs.getString("CorreoElectrónico");
                info[f][1] = rs.getString("Contraseña");
                info[f][2] = rs.getString("Descripción");
                f++;
            }
            return info;
        }
        catch(Exception e){
            System.out.println(e);
        }
        return null;
    }

    public String[][] getInfoConciertoMartas(){

        String qF = "SELECT COUNT(*) AS n " +
                "FROM Concierto c, Usuario u " +
                "WHERE c.NombreUsuario=u.NombreUsuario AND u.CorreoElectrónico='xxx@gmail.com' ";
        System.out.println(qF);

        int numFiles = getNumFilesQuery(qF);
        String[][] info = new String[numFiles][4];

        String q = "SELECT c.Título, c.Artista AS art, c.Fecha, u.NombreUsuario AS nom " +
                "FROM Concierto c, Usuario u " +
                "WHERE c.NombreUsuario=u.NombreUsuario AND u.CorreoElectrónico='xxx@gmail.com' "+
                "ORDER BY c.Artista ASC";
        System.out.println(q);
        try {
            ResultSet rs = query.executeQuery(q);
            int f=0;
            while(rs.next()){
                info[f][0] = rs.getString("Título");
                info[f][1] = rs.getString("art"); // alias definido en la query
                info[f][2] = rs.getString("Fecha"); //String.valueOf( rs.getInt("Fecha"));
                info[f][3] = rs.getString("nom");
                f++;
            }

        }
        catch(Exception e){
            System.out.println(e);
        }
        return info;
    }

    public int getNumFilesQuery(String q){ // SELECT COUNT(*) AS n
        try{
            ResultSet rs = query.executeQuery(q);
            rs.next();
            return rs.getInt("n");
        }
        catch(Exception e){
            System.out.println(e);
        }
        return 0;
    }

    // INSERTS
    public void insertaUsuario(String n, String p) {
        String q = "INSERT INTO Usuario (nombre, contraseña)" +
                "VALUES ('" + n + "', '" + p + "')";
        System.out.println(q);

        try {
            query.execute(q);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public int insertarViniloCD(String titulo, String artista, String fecha, String edicion,
                                String ubicacion, String genero, String origen,
                                String notas, String nombreUsuario, char tipo, int rating) {
        String valorFecha = (fecha == null || fecha.isEmpty()) ? "NULL" : "'" + fecha + "'";
        String valorNotas = (notas == null || notas.isEmpty()) ? "NULL" : "'" + notas + "'";
        String valorEdicion = (edicion == null || edicion.isEmpty()) ? "NULL" : "'" + edicion + "'";

        String q = "INSERT INTO Vinilo_CD (Título, Artista, Fecha, Edición, Ubicación, Género, Origen, Notas, NombreUsuario, tipo, Rating) " +
                "VALUES ('" + titulo + "', '" + artista + "', " + valorFecha + ", " + valorEdicion + ", '" +
                ubicacion + "', '" + genero + "', '" + origen + "', " + valorNotas + ", '" +
                nombreUsuario + "', '" + tipo + "', " + rating + ")";
        System.out.println(q);
        try {
            query.execute(q, Statement.RETURN_GENERATED_KEYS);
            ResultSet rs = query.getGeneratedKeys();
            if (rs.next()) return rs.getInt(1);
        } catch (Exception e) {
            System.out.println(e);
        }
        return -1; // -1 indica error
    }

    public int insertarConcierto(String titulo, String artista, String fecha,
                                 String lugar, String genero, String notas,
                                 String nombreUsuario, int rating) {
        String valorFecha = (fecha == null || fecha.isEmpty()) ? "NULL" : "'" + fecha + "'";
        String valorNotas = (notas == null || notas.isEmpty()) ? "NULL" : "'" + notas + "'";
        String valorLugar = (lugar == null || lugar.isEmpty()) ? "NULL" : "'" + lugar + "'";

        String q = "INSERT INTO Concierto (Título, Artista, Fecha, Lugar, Género, Notas, NombreUsuario, Rating) " +
                "VALUES ('" + titulo + "', '" + artista + "', " + valorFecha + ", " + valorLugar + ", '" +
                genero + "', " + valorNotas + ", '" + nombreUsuario + "', " + rating + ")";
        System.out.println(q);
        try {
            query.execute(q, Statement.RETURN_GENERATED_KEYS);
            ResultSet rs = query.getGeneratedKeys();
            if (rs.next()) return rs.getInt(1);
        } catch (Exception e) {
            System.out.println(e);
        }
        return -1;
    }

    public void insertarImagen(String nombreArchivo, String tituloVinilo, String tituloConcierto) {
        String valVinilo    = (tituloVinilo    != null && !tituloVinilo.isEmpty())    ? "'" + tituloVinilo    + "'" : "NULL";
        String valConcierto = (tituloConcierto != null && !tituloConcierto.isEmpty()) ? "'" + tituloConcierto + "'" : "NULL";

        String q = "INSERT INTO Imagen (id, `id_Vinilo/CD`, id_Concierto) " +
                   "VALUES ('" + nombreArchivo + "', " + valVinilo + ", " + valConcierto + ")";
        System.out.println(q);
        try {
            query.execute(q);
            System.out.println("Imagen guardada en BD.");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    // Verificar Usuario
    public boolean loginCorrecto(String n, String p) {
        String q = "SELECT COUNT(*) AS N " +
                   "FROM Usuario " +
                   "WHERE NombreUsuario = '"+ n +"' AND Contraseña = '"+p+"'";
        System.out.println(q);

        try{
            ResultSet rs = query.executeQuery(q);
            rs.next();
            int nn = rs.getInt("N");
            return (nn == 1);
        }catch(Exception e){
            System.out.println(e);
        }
        return false;
    }



}