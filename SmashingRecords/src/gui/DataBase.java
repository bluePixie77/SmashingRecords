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

    public String[][] getInfoArray2DVinilos(String nombreUsuario){
        String q = "SELECT Título, Artista, Fecha, Género, Edición " +
                "FROM Vinilo_CD " +
                "WHERE tipo = 'V' AND NombreUsuario = '" + nombreUsuario + "' " +
                "ORDER BY Artista ASC";
        System.out.println(q);
        int nf = getNumFilesQuery("SELECT COUNT(*) AS n FROM Vinilo_CD WHERE tipo='V' AND NombreUsuario='" + nombreUsuario + "'");
        String[][] info = new String[nf][5];
        try {
            ResultSet rs = query.executeQuery(q);
            int f = 0;
            while(rs.next()){
                info[f][0] = rs.getString("Título");
                info[f][1] = rs.getString("Artista");
                info[f][2] = rs.getString("Fecha") != null ? rs.getString("Fecha") : "";
                info[f][3] = rs.getString("Género");
                info[f][4] = rs.getString("Edición") != null ? rs.getString("Edición") : "";
                f++;
            }
            return info;
        } catch(Exception e){
            System.out.println(e);
        }
        return info;
    }

    public String[][] getInfoArray2DCDs(String nombreUsuario){
        String q = "SELECT Título, Artista, Fecha, Género, Edición " +
                "FROM Vinilo_CD " +
                "WHERE tipo = 'C' AND NombreUsuario = '" + nombreUsuario + "' " +
                "ORDER BY Artista ASC";
        System.out.println(q);
        int nf = getNumFilesQuery("SELECT COUNT(*) AS n FROM Vinilo_CD WHERE tipo='C' AND NombreUsuario='" + nombreUsuario + "'");
        String[][] info = new String[nf][5];
        try {
            ResultSet rs = query.executeQuery(q);
            int f = 0;
            while(rs.next()){
                info[f][0] = rs.getString("Título");
                info[f][1] = rs.getString("Artista");
                info[f][2] = rs.getString("Fecha") != null ? rs.getString("Fecha") : "";
                info[f][3] = rs.getString("Género");
                info[f][4] = rs.getString("Edición") != null ? rs.getString("Edición") : "";
                f++;
            }
            return info;
        } catch(Exception e){
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
                                String notas, String nombreUsuario, char tipo, int rating, char lDeseos) {
        String valorFecha = (fecha == null || fecha.isEmpty()) ? "NULL" : "'" + fecha + "'";
        String valorNotas = (notas == null || notas.isEmpty()) ? "NULL" : "'" + notas + "'";
        String valorEdicion = (edicion == null || edicion.isEmpty()) ? "NULL" : "'" + edicion + "'";

        String q = "INSERT INTO Vinilo_CD (Título, Artista, Fecha, Edición, Ubicación, Género, Origen, Notas, NombreUsuario, tipo, Rating, ListaDeseos) " +
                "VALUES ('" + titulo + "', '" + artista + "', " + valorFecha + ", " + valorEdicion + ", '" +
                ubicacion + "', '" + genero + "', '" + origen + "', " + valorNotas + ", '" +
                nombreUsuario + "', '" + tipo + "', " + rating + ", '" + lDeseos + "')";
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
                                 String nombreUsuario, int rating, char lDeseos) {
        String valorFecha = (fecha == null || fecha.isEmpty()) ? "NULL" : "'" + fecha + "'";
        String valorNotas = (notas == null || notas.isEmpty()) ? "NULL" : "'" + notas + "'";
        String valorLugar = (lugar == null || lugar.isEmpty()) ? "NULL" : "'" + lugar + "'";

        String q = "INSERT INTO Concierto (Título, Artista, Fecha, Lugar, Género, Notas, NombreUsuario, Rating, ListaDeseos) " +
                "VALUES ('" + titulo + "', '" + artista + "', " + valorFecha + ", " + valorLugar + ", '" +
                genero + "', " + valorNotas + ", '" + nombreUsuario + "', " + rating + ", '" + lDeseos + "')";
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

    public void insertarImagen(String nombreArchivo, int idVinilo, int idConcierto) {
        String valVinilo    = (idVinilo    != -1) ? String.valueOf(idVinilo)    : "NULL";
        String valConcierto = (idConcierto != -1) ? String.valueOf(idConcierto) : "NULL";

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

    public void actualizarViniloCD(int id, String titulo, String artista, String fecha,
                                   String edicion, String ubicacion, String genero,
                                   String origen, String notas, int rating, char lDeseos) {
        String valorFecha   = (fecha == null   || fecha.isEmpty())   ? "NULL" : "'" + fecha + "'";
        String valorNotas   = (notas == null   || notas.isEmpty())   ? "NULL" : "'" + notas + "'";
        String valorEdicion = (edicion == null || edicion.isEmpty()) ? "NULL" : "'" + edicion + "'";
        String q = "UPDATE Vinilo_CD SET Título='" + titulo + "', Artista='" + artista +
                "', Fecha=" + valorFecha + ", Edición=" + valorEdicion +
                ", Ubicación='" + ubicacion + "', Género='" + genero +
                "', Origen='" + origen + "', Notas=" + valorNotas +
                ", Rating=" + rating + ", ListaDeseos='" + lDeseos +
                "' WHERE id=" + id;
        try { query.execute(q); } catch (Exception e) { System.out.println(e); }
    }

    public void actualizarConcierto(int id, String titulo, String artista, String fecha,
                                    String lugar, String genero, String notas,
                                    int rating, char lDeseos) {
        String valorFecha = (fecha == null || fecha.isEmpty()) ? "NULL" : "'" + fecha + "'";
        String valorNotas = (notas == null || notas.isEmpty()) ? "NULL" : "'" + notas + "'";
        String valorLugar = (lugar == null || lugar.isEmpty()) ? "NULL" : "'" + lugar + "'";
        String q = "UPDATE Concierto SET Título='" + titulo + "', Artista='" + artista +
                "', Fecha=" + valorFecha + ", Lugar=" + valorLugar +
                ", Género='" + genero + "', Notas=" + valorNotas +
                ", Rating=" + rating + ", ListaDeseos='" + lDeseos +
                "' WHERE id=" + id;
        try { query.execute(q); } catch (Exception e) { System.out.println(e); }
    }
    public void actualizarImagenVinilo(String nombreArchivo, int idVinilo) {
        try {
            query.execute("DELETE FROM Imagen WHERE `id_Vinilo/CD` = " + idVinilo);
            if (nombreArchivo != null && !nombreArchivo.isEmpty()) {
                query.execute("INSERT INTO Imagen (id, `id_Vinilo/CD`, id_Concierto) VALUES ('"
                        + nombreArchivo + "', " + idVinilo + ", NULL)");
            }
        } catch (Exception e) { System.out.println(e); }
    }

    public void actualizarImagenConcierto(String nombreArchivo, int idConcierto) {
        try {
            query.execute("DELETE FROM Imagen WHERE id_Concierto = " + idConcierto);
            if (nombreArchivo != null && !nombreArchivo.isEmpty()) {
                query.execute("INSERT INTO Imagen (id, `id_Vinilo/CD`, id_Concierto) VALUES ('"
                        + nombreArchivo + "', NULL, " + idConcierto + ")");
            }
        } catch (Exception e) { System.out.println(e); }
    }


    // SELECTS
    public String[][] getVinilosUsuario(String usuario, boolean soloListaDeseos) {
        String filtroListaDeseos = soloListaDeseos ? " AND ListaDeseos='S'" : " AND ListaDeseos='N'";
        String q = "SELECT v.id, v.Título, v.Artista, COALESCE(i.id, '') AS imagen " +
                "FROM Vinilo_CD v LEFT JOIN Imagen i ON i.`id_Vinilo/CD` = v.id " +
                "WHERE v.tipo='V' AND v.NombreUsuario='" + usuario + "'" + filtroListaDeseos +
                " ORDER BY v.Artista ASC";
        int n = getNumFilesQuery("SELECT COUNT(*) AS n FROM Vinilo_CD WHERE tipo='V' AND NombreUsuario='" + usuario + "'" + filtroListaDeseos);
        String[][] info = new String[n][4];
        try {
            ResultSet rs = query.executeQuery(q);
            int f = 0;
            while (rs.next()) {
                info[f][0] = String.valueOf(rs.getInt("id"));
                info[f][1] = rs.getString("Título");
                info[f][2] = rs.getString("Artista");
                info[f][3] = rs.getString("imagen"); // "" si no tiene imagen
                f++;
            }
        } catch (Exception e) { System.out.println(e); }
        return info;
    }

    public String[][] getCDsUsuario(String usuario, boolean soloListaDeseos) {
        String filtroLDeseos = soloListaDeseos ? " AND ListaDeseos='S'" : " AND ListaDeseos='N'";
        String q = "SELECT v.id, v.Título, v.Artista, COALESCE(i.id, '') AS imagen " +
                "FROM Vinilo_CD v LEFT JOIN Imagen i ON i.`id_Vinilo/CD` = v.id " +
                "WHERE v.tipo='C' AND v.NombreUsuario='" + usuario + "'" + filtroLDeseos +
                " ORDER BY v.Artista ASC";
        int n = getNumFilesQuery("SELECT COUNT(*) AS n FROM Vinilo_CD WHERE tipo='C' AND NombreUsuario='" + usuario + "'" + filtroLDeseos);
        String[][] info = new String[n][4];
        try {
            ResultSet rs = query.executeQuery(q);
            int f = 0;
            while (rs.next()) {
                info[f][0] = String.valueOf(rs.getInt("id"));
                info[f][1] = rs.getString("Título");
                info[f][2] = rs.getString("Artista");
                info[f][3] = rs.getString("imagen");
                f++;
            }
        } catch (Exception e) { System.out.println(e); }
        return info;
    }

    public String[][] getConciertosUsuario(String usuario, boolean soloListaDeseos) {
        String filtroListaDeseos = soloListaDeseos ? " AND ListaDeseos='S'" : " AND ListaDeseos='N'";
        String q = "SELECT c.id, c.Título, c.Artista, COALESCE(i.id, '') AS imagen " +
                "FROM Concierto c LEFT JOIN Imagen i ON i.id_Concierto = c.id " +
                "WHERE c.NombreUsuario='" + usuario + "'" + filtroListaDeseos +
                " ORDER BY c.Artista ASC";
        int n = getNumFilesQuery("SELECT COUNT(*) AS n FROM Concierto WHERE NombreUsuario='" + usuario + "'" + filtroListaDeseos);
        String[][] info = new String[n][4];
        try {
            ResultSet rs = query.executeQuery(q);
            int f = 0;
            while (rs.next()) {
                info[f][0] = String.valueOf(rs.getInt("id"));
                info[f][1] = rs.getString("Título");
                info[f][2] = rs.getString("Artista");
                info[f][3] = rs.getString("imagen");
                f++;
            }
        } catch (Exception e) { System.out.println(e); }
        return info;
    }

    public String[] getDetalleViniloCD(int id) {
        String q = "SELECT v.Título, v.Artista, v.Fecha, v.Edición, v.Ubicación, v.Género, " +
                "v.Origen, v.Notas, v.Rating, v.ListaDeseos, COALESCE(i.id,'') AS imagen " +
                "FROM Vinilo_CD v LEFT JOIN Imagen i ON i.`id_Vinilo/CD` = v.id " +
                "WHERE v.id = " + id;
        try {
            ResultSet rs = query.executeQuery(q);
            if (rs.next()) {
                return new String[]{
                        rs.getString("Título"),       // [0]
                        rs.getString("Artista"),      // [1]
                        rs.getString("Fecha") != null ? rs.getString("Fecha") : "",   // [2]
                        rs.getString("Edición") != null ? rs.getString("Edición") : "", // [3]
                        rs.getString("Ubicación"),    // [4]
                        rs.getString("Género"),       // [5]
                        rs.getString("Origen"),       // [6]
                        rs.getString("Notas") != null ? rs.getString("Notas") : "",   // [7]
                        rs.getString("Rating") != null ? rs.getString("Rating") : "0", // [8]
                        rs.getString("ListaDeseos"),  // [9]
                        rs.getString("imagen")        // [10]
                };
            }
        } catch (Exception e) { System.out.println(e); }
        return null;
    }

    public String[] getDetalleConcierto(int id) {
        String q = "SELECT c.Título, c.Artista, c.Fecha, c.Lugar, c.Género, " +
                "c.Notas, c.Rating, c.ListaDeseos, COALESCE(i.id,'') AS imagen " +
                "FROM Concierto c LEFT JOIN Imagen i ON i.id_Concierto = c.id " +
                "WHERE c.id = " + id;
        try {
            ResultSet rs = query.executeQuery(q);
            if (rs.next()) {
                return new String[]{
                        rs.getString("Título"),       // [0]
                        rs.getString("Artista"),      // [1]
                        rs.getString("Fecha") != null ? rs.getString("Fecha") : "",  // [2]
                        rs.getString("Lugar") != null ? rs.getString("Lugar") : "",  // [3]
                        rs.getString("Género"),       // [4]
                        rs.getString("Notas") != null ? rs.getString("Notas") : "",  // [5]
                        rs.getString("Rating") != null ? rs.getString("Rating") : "0", // [6]
                        rs.getString("ListaDeseos"),  // [7]
                        rs.getString("imagen")        // [8]
                };
            }
        } catch (Exception e) { System.out.println(e); }
        return null;
    }

    // Total de items del usuario (sin filtro lista deseos)
    public int getTotalUsuario(String usuario, String tipo) {
        String q;
        if (tipo.equals("CONCIERTO")) {
            q = "SELECT COUNT(*) AS n FROM Concierto WHERE NombreUsuario='" + usuario + "'";
        } else {
            q = "SELECT COUNT(*) AS n FROM Vinilo_CD WHERE NombreUsuario='" + usuario + "' AND tipo='" + tipo + "'";
        }
        return getNumFilesQuery(q);
    }

    // Ratings: devuelve cuántos items tienen rating 0,1,2,3,4,5
    public int[] getRatingStats(String usuario, String tipo) {
        int[] counts = new int[6]; // índice = rating (0 a 5)
        String tabla = tipo.equals("CONCIERTO") ? "Concierto" : "Vinilo_CD";
        String filtroTipo = tipo.equals("CONCIERTO") ? "" : " AND tipo='" + tipo + "'";
        for (int r = 0; r <= 5; r++) {
            String q = "SELECT COUNT(*) AS n FROM " + tabla +
                    " WHERE NombreUsuario='" + usuario + "'" + filtroTipo +
                    " AND Rating=" + r;
            counts[r] = getNumFilesQuery(q);
        }
        return counts;
    }

    // Años: devuelve un mapa año→cantidad, ordenado por año
    public String[][] getAniosStats(String usuario, String tipo) {
        String q;
        if (tipo.equals("CONCIERTO")) {
            q = "SELECT SUBSTRING(Fecha,7,4) AS anio, COUNT(*) AS n " +
                    "FROM Concierto " +
                    "WHERE NombreUsuario='" + usuario + "' AND Fecha IS NOT NULL AND Fecha != '' AND LENGTH(Fecha)=10 " +
                    "GROUP BY anio ORDER BY anio ASC";
        } else {
            q = "SELECT Fecha AS anio, COUNT(*) AS n " +
                    "FROM Vinilo_CD " +
                    "WHERE NombreUsuario='" + usuario + "' AND tipo='" + tipo + "' AND Fecha IS NOT NULL AND Fecha != '' " +
                    "GROUP BY Fecha ORDER BY Fecha ASC";
        }
        try {
            // Primero contamos filas
            java.util.List<String[]> lista = new java.util.ArrayList<>();
            ResultSet rs = query.executeQuery(q);
            while (rs.next()) {
                String anio = rs.getString("anio");
                String n    = rs.getString("n");
                if (anio != null && !anio.isEmpty()) lista.add(new String[]{anio, n});
            }
            String[][] result = new String[lista.size()][2];
            for (int i = 0; i < lista.size(); i++) result[i] = lista.get(i);
            return result;
        } catch (Exception e) { System.out.println(e); }
        return new String[0][2];
    }

    // Géneros: devuelve cuántos items contienen cada género
    public int[] getGeneroStats(String usuario, String tipo, String[] nombresGenero) {
        int[] counts = new int[nombresGenero.length];
        String tabla = tipo.equals("CONCIERTO") ? "Concierto" : "Vinilo_CD";
        String filtroTipo = tipo.equals("CONCIERTO") ? "" : " AND tipo='" + tipo + "'";
        for (int i = 0; i < nombresGenero.length; i++) {
            String q = "SELECT COUNT(*) AS n FROM " + tabla +
                    " WHERE NombreUsuario='" + usuario + "'" + filtroTipo +
                    " AND Género LIKE '%" + nombresGenero[i] + "%'";
            counts[i] = getNumFilesQuery(q);
        }
        return counts;
    }

    // DELETES
    public void eliminarViniloCD(int id) {
        try {
            query.execute("DELETE FROM Imagen WHERE `id_Vinilo/CD` = " + id);
            query.execute("DELETE FROM Vinilo_CD WHERE id = " + id);
            System.out.println("Vinilo/CD eliminado: " + id);
        } catch (Exception e) { System.out.println(e); }
    }

    public void eliminarConcierto(int id) {
        try {
            query.execute("DELETE FROM Imagen WHERE id_Concierto = " + id);
            query.execute("DELETE FROM Concierto WHERE id = " + id);
            System.out.println("Concierto eliminado: " + id);
        } catch (Exception e) { System.out.println(e); }
    }
}