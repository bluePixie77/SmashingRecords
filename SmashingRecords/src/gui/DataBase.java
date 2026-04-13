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


    public String[] getDatosUsuario(String nombreUsuario) {
        String q = "SELECT NombreUsuario, CorreoElectrónico, Descripción FROM Usuario WHERE NombreUsuario='" + nombreUsuario + "'";
        try {
            ResultSet rs = query.executeQuery(q);
            if (rs.next()) {
                return new String[]{
                        rs.getString("NombreUsuario"),
                        rs.getString("CorreoElectrónico"),
                        rs.getString("Descripción") != null ? rs.getString("Descripción") : ""
                };
            }
        } catch (Exception e) { System.out.println(e); }
        return new String[]{"", "", ""};
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

    public String getDescripcionUsuario(String nombreUsuario) {
        String q = "SELECT Descripción FROM Usuario WHERE NombreUsuario='" + nombreUsuario + "'";
        try {
            ResultSet rs = query.executeQuery(q);
            if (rs.next()) {
                String desc = rs.getString("Descripción");
                return desc != null ? desc : "";
            }
        } catch (Exception e) { System.out.println(e); }
        return "";
    }

    public String getFotoPerfilUsuario(String nombreUsuario) {
        String q = "SELECT id FROM Imagen WHERE NombreUsuario='" + nombreUsuario + "'";
        try {
            ResultSet rs = query.executeQuery(q);
            if (rs.next()) return rs.getString("id");
        } catch (Exception e) { System.out.println(e); }
        return "";
    }

    public void actualizarDescripcionUsuario(String nombreUsuario, String descripcion) {
        String valorDesc = (descripcion == null || descripcion.isEmpty()) ? "NULL" : "'" + descripcion + "'";
        String q = "UPDATE Usuario SET Descripción=" + valorDesc + " WHERE NombreUsuario='" + nombreUsuario + "'";
        try { query.execute(q); } catch (Exception e) { System.out.println(e); }
    }

    public void actualizarFotoPerfilUsuario(String nombreUsuario, String nombreArchivo) {
        try {
            // Borrar foto anterior si existe
            query.execute("DELETE FROM Imagen WHERE NombreUsuario='" + nombreUsuario + "'");
            // Insertar nueva
            query.execute("INSERT INTO Imagen (id, `id_Vinilo/CD`, id_Concierto, NombreUsuario) " +
                    "VALUES ('" + nombreArchivo + "', NULL, NULL, '" + nombreUsuario + "')");
        } catch (Exception e) { System.out.println(e); }
    }

    // INSERTS
    public String registrarUsuario(String nombreUsuario, String correo, String contrasena) {
        // Verificar que el nombre de usuario no exista ya
        int nNombre = getNumFilesQuery("SELECT COUNT(*) AS n FROM Usuario WHERE NombreUsuario='" + nombreUsuario + "'");
        if (nNombre > 0) return "El nombre de usuario ya existe.";
        // Verificar que el correo no exista ya
        int nCorreo = getNumFilesQuery("SELECT COUNT(*) AS n FROM Usuario WHERE CorreoElectrónico='" + correo + "'");
        if (nCorreo > 0) return "El correo ya está registrado.";

        String q = "INSERT INTO Usuario (NombreUsuario, CorreoElectrónico, Contraseña) " +
                "VALUES ('" + nombreUsuario + "', '" + correo + "', '" + contrasena + "')";
        try {
            query.execute(q);
            return "ok";
        } catch (Exception e) {
            System.out.println(e);
            return "Error al crear la cuenta.";
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
    public String[][] getVinilosUsuario(String usuario, boolean soloListaDeseos, String orderCol, String orderDir) {
        String filtroListaDeseos = soloListaDeseos ? " AND ListaDeseos='S'" : " AND ListaDeseos='N'";
        String q = "SELECT v.id, v.Título, v.Artista, COALESCE(i.id, '') AS imagen " +
                "FROM Vinilo_CD v LEFT JOIN Imagen i ON i.`id_Vinilo/CD` = v.id " +
                "WHERE v.tipo='V' AND v.NombreUsuario='" + usuario + "'" + filtroListaDeseos +
                " ORDER BY v." + orderCol + " " + orderDir;
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

    public String[][] getCDsUsuario(String usuario, boolean soloListaDeseos, String orderCol, String orderDir) {
        String filtroLDeseos = soloListaDeseos ? " AND ListaDeseos='S'" : " AND ListaDeseos='N'";
        String q = "SELECT v.id, v.Título, v.Artista, COALESCE(i.id, '') AS imagen " +
                "FROM Vinilo_CD v LEFT JOIN Imagen i ON i.`id_Vinilo/CD` = v.id " +
                "WHERE v.tipo='C' AND v.NombreUsuario='" + usuario + "'" + filtroLDeseos +
                " ORDER BY v." + orderCol + " " + orderDir;
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

    public String[][] getConciertosUsuario(String usuario, boolean soloListaDeseos, String orderCol, String orderDir) {
        String filtroListaDeseos = soloListaDeseos ? " AND ListaDeseos='S'" : " AND ListaDeseos='N'";
        String q = "SELECT c.id, c.Título, c.Artista, COALESCE(i.id, '') AS imagen " +
                "FROM Concierto c LEFT JOIN Imagen i ON i.id_Concierto = c.id " +
                "WHERE c.NombreUsuario='" + usuario + "'" + filtroListaDeseos +
                " ORDER BY c." + orderCol + " " + orderDir;
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

    // Buscador
    public String[][] buscarMusica(String usuario, boolean soloListaDeseos, String tipo,
                                   String texto, String orderCol, String orderDir) {
        String filtroLD = soloListaDeseos ? " AND ListaDeseos='S'" : " AND ListaDeseos='N'";
        String busqueda = " AND (Título LIKE '%" + texto + "%'" +
                " OR Artista LIKE '%" + texto + "%'" +
                " OR Fecha LIKE '%" + texto + "%'" +
                " OR CAST(Rating AS CHAR) LIKE '%" + texto + "%')";
        String q = "SELECT v.id, v.Título, v.Artista, COALESCE(i.id,'') AS imagen " +
                "FROM Vinilo_CD v LEFT JOIN Imagen i ON i.`id_Vinilo/CD` = v.id " +
                "WHERE v.tipo='" + tipo + "' AND v.NombreUsuario='" + usuario + "'" +
                filtroLD + busqueda +
                " ORDER BY v." + orderCol + " " + orderDir;
        int n = getNumFilesQuery("SELECT COUNT(*) AS n FROM Vinilo_CD v WHERE v.tipo='" + tipo +
                "' AND v.NombreUsuario='" + usuario + "'" + filtroLD + busqueda);
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
    public String[][] buscarConciertos(String usuario, boolean soloListaDeseos,
                                       String texto, String orderCol, String orderDir) {
        String filtroLD = soloListaDeseos ? " AND ListaDeseos='S'" : " AND ListaDeseos='N'";
        String busqueda = " AND (Título LIKE '%" + texto + "%'" +
                " OR Artista LIKE '%" + texto + "%'" +
                " OR Fecha LIKE '%" + texto + "%'" +
                " OR CAST(Rating AS CHAR) LIKE '%" + texto + "%')";
        String q = "SELECT c.id, c.Título, c.Artista, COALESCE(i.id,'') AS imagen " +
                "FROM Concierto c LEFT JOIN Imagen i ON i.id_Concierto = c.id " +
                "WHERE c.NombreUsuario='" + usuario + "'" + filtroLD + busqueda +
                " ORDER BY c." + orderCol + " " + orderDir;
        int n = getNumFilesQuery("SELECT COUNT(*) AS n FROM Concierto c WHERE c.NombreUsuario='" +
                usuario + "'" + filtroLD + busqueda);
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