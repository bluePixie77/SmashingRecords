package gui;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Gestiona todas las operaciones de persistencia y consulta con la base de datos MySQL.
 * <p>
 * Esta clase centraliza la lógica SQL de la aplicación, permitiendo el registro de usuarios,
 * la gestión de la colección (Vinilos, CDs y Conciertos), la manipulación de imágenes y
 * la generación de datos estadísticos para los gráficos.
 * </p>
 * * @author SmashRecords
 * @version 1.0
 */
public class DataBase {

    /** Objeto de conexión con el driver JDBC. */
    Connection c;

    /** Objeto para ejecutar sentencias SQL estáticas. */
    Statement query;

    /** Nombre de usuario para la conexión a MySQL. */
    String user;

    /** Contraseña para la conexión a MySQL. */
    String password;

    /** Nombre de la base de datos a la que se desea conectar. */
    String dataBaseName;

    /** Indica si la conexión con el servidor está activa. */
    boolean connectat = false;

    /**
     * Constructor de la clase DataBase.
     * * @param user Nombre del usuario de la base de datos.
     * @param password Contraseña del usuario.
     * @param dataBaseName Nombre de la base de datos (schema).
     */
    public DataBase(String user, String password, String dataBaseName) {
        this.user = user;
        this.password = password;
        this.dataBaseName = dataBaseName;
    }

    /**
     * Establece la conexión con el servidor local MySQL.
     * <p>
     * Utiliza el puerto 8889 por defecto (común en MAMP). Si la conexión es exitosa,
     * inicializa el objeto {@code query} para permitir consultas futuras.
     * </p>
     */
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

    /**
     * Cuenta el número total de registros en una tabla específica.
     * * @param nomTaula Nombre de la tabla a consultar.
     * @return Cantidad total de filas encontradas.
     */
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

    /**
     * Obtiene una lista simplificada de conciertos para pruebas de interfaz.
     * * @return Matriz bidimensional con [Título, Artista] de todos los conciertos.
     */
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

    /**
     * Recupera la información básica del perfil de un usuario.
     * * @param nombreUsuario Identificador único del usuario.
     * @return Array de String con [Nombre, Correo, Descripción].
     */
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

    /**
     * Ejecuta una consulta de conteo personalizada.
     * * @param q Sentencia SQL de tipo SELECT COUNT.
     * @return El valor de la columna 'n' resultante.
     */
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

    /**
     * Obtiene el texto de biografía o descripción de un usuario.
     * * @param nombreUsuario Nombre del usuario.
     * @return Cadena con la descripción o vacío si es nulo.
     */
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

    /**
     * Busca la referencia de la imagen de perfil de un usuario.
     * * @param nombreUsuario Nombre del usuario.
     * @return El nombre del archivo de imagen almacenado.
     */
    public String getFotoPerfilUsuario(String nombreUsuario) {
        String q = "SELECT id FROM Imagen WHERE NombreUsuario='" + nombreUsuario + "'";
        try {
            ResultSet rs = query.executeQuery(q);
            if (rs.next()) return rs.getString("id");
        } catch (Exception e) { System.out.println(e); }
        return "";
    }

    /**
     * Actualiza la biografía del usuario en la base de datos.
     * * @param nombreUsuario Nombre del usuario a modificar.
     * @param descripcion Nuevo texto de descripción.
     */
    public void actualizarDescripcionUsuario(String nombreUsuario, String descripcion) {
        String valorDesc = (descripcion == null || descripcion.isEmpty()) ? "NULL" : "'" + descripcion + "'";
        String q = "UPDATE Usuario SET Descripción=" + valorDesc + " WHERE NombreUsuario='" + nombreUsuario + "'";
        try { query.execute(q); } catch (Exception e) { System.out.println(e); }
    }

    /**
     * Cambia la imagen de perfil de un usuario.
     * <p>
     * El método elimina cualquier registro previo de imagen asociado al usuario en la tabla
     * {@code Imagen} antes de insertar la nueva referencia.
     * </p>
     * * @param nombreUsuario Nombre del usuario.
     * @param nombreArchivo Nombre del nuevo archivo de imagen.
     */
    public void actualizarFotoPerfilUsuario(String nombreUsuario, String nombreArchivo) {
        try {
            // Borrar foto anterior si existe
            query.execute("DELETE FROM Imagen WHERE NombreUsuario='" + nombreUsuario + "'");
            // Insertar nueva
            query.execute("INSERT INTO Imagen (id, `id_Vinilo/CD`, id_Concierto, NombreUsuario) " +
                    "VALUES ('" + nombreArchivo + "', NULL, NULL, '" + nombreUsuario + "')");
        } catch (Exception e) { System.out.println(e); }
    }

    /**
     * Crea un nuevo registro de usuario tras validar disponibilidad.
     * <p>
     * Comprueba primero que ni el nombre de usuario ni el correo electrónico existan ya
     * en la base de datos para evitar duplicados.
     * </p>
     * * @param nombreUsuario Nombre elegido.
     * @param correo Correo electrónico.
     * @param contrasena Contraseña en texto plano.
     * @return "ok" si se crea con éxito o un mensaje descriptivo del error.
     */
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

    /**
     * Inserta un nuevo Vinilo o CD en la colección.
     * * @param titulo Título del álbum.
     * @param artista Nombre del artista o banda.
     * @param fecha Año de lanzamiento.
     * @param edicion Información de la edición.
     * @param ubicacion Lugar físico donde se guarda.
     * @param genero Géneros musicales asociados.
     * @param origen Procedencia del artículo.
     * @param notas Comentarios adicionales.
     * @param nombreUsuario Usuario propietario.
     * @param tipo 'V' para Vinilo, 'C' para CD.
     * @param rating Valoración de 0 a 5.
     * @param lDeseos 'S' si está en lista de deseos, 'N' si ya se posee.
     * @return El ID generado automáticamente o -1 si falla.
     */
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

    /**
     * Inserta un nuevo concierto en la bitácora del usuario.
     * * @param titulo Nombre del tour o evento.
     * @param artista Artista principal.
     * @param fecha Fecha del evento (DD/MM/AAAA).
     * @param lugar Recinto o ciudad.
     * @param genero Género musical.
     * @param notas Recuerdos o detalles.
     * @param nombreUsuario Propietario del registro.
     * @param rating Valoración personal.
     * @param lDeseos 'S' para conciertos futuros, 'N' para asistidos.
     * @return El ID generado automáticamente o -1 si falla.
     */
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

    /**
     * Registra una imagen asociada a un Vinilo, CD o Concierto.
     * * @param nombreArchivo Nombre único del archivo de imagen.
     * @param idVinilo ID del vinilo (pasar -1 si es para concierto).
     * @param idConcierto ID del concierto (pasar -1 si es para vinilo/cd).
     */
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

    /**
     * Valida las credenciales de acceso de un usuario.
     * * @param n Nombre de usuario introducido.
     * @param p Contraseña introducida.
     * @return {@code true} si existe exactamente un registro con esos datos; {@code false} en caso contrario.
     */
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

    /**
     * Actualiza todos los campos de un registro de Vinilo o CD existente.
     * * @param id ID del registro a modificar.
     * @param titulo Nuevo título.
     * @param artista Nuevo artista.
     * @param fecha Nueva fecha.
     * @param edicion Nueva edición.
     * @param ubicacion Nueva ubicación.
     * @param genero Nuevos géneros.
     * @param origen Nuevo origen.
     * @param notas Nuevas notas.
     * @param rating Nueva valoración.
     * @param lDeseos Nuevo estado de lista de deseos.
     */
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

    /**
     * Actualiza todos los campos de un registro de concierto existente.
     * * @param id ID del registro.
     * @param titulo Nuevo título.
     * @param artista Nuevo artista.
     * @param fecha Nueva fecha.
     * @param lugar Nuevo lugar.
     * @param genero Nuevo género.
     * @param notas Nuevas notas.
     * @param rating Nueva valoración.
     * @param lDeseos Nuevo estado de lista de deseos.
     */
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
    /**
     * Actualiza la imagen asociada a un Vinilo o CD. Elimina la anterior si existe.
     * * @param nombreArchivo Nombre del nuevo archivo de imagen.
     * @param idVinilo ID del vinilo/cd afectado.
     */
    public void actualizarImagenVinilo(String nombreArchivo, int idVinilo) {
        try {
            query.execute("DELETE FROM Imagen WHERE `id_Vinilo/CD` = " + idVinilo);
            if (nombreArchivo != null && !nombreArchivo.isEmpty()) {
                query.execute("INSERT INTO Imagen (id, `id_Vinilo/CD`, id_Concierto) VALUES ('"
                        + nombreArchivo + "', " + idVinilo + ", NULL)");
            }
        } catch (Exception e) { System.out.println(e); }
    }

    /**
     * Actualiza la imagen asociada a un concierto. Elimina la anterior si existe.
     * * @param nombreArchivo Nombre del nuevo archivo de imagen.
     * @param idConcierto ID del concierto afectado.
     */
    public void actualizarImagenConcierto(String nombreArchivo, int idConcierto) {
        try {
            query.execute("DELETE FROM Imagen WHERE id_Concierto = " + idConcierto);
            if (nombreArchivo != null && !nombreArchivo.isEmpty()) {
                query.execute("INSERT INTO Imagen (id, `id_Vinilo/CD`, id_Concierto) VALUES ('"
                        + nombreArchivo + "', NULL, " + idConcierto + ")");
            }
        } catch (Exception e) { System.out.println(e); }
    }


    /**
     * Recupera todos los vinilos de un usuario específico.
     * <p>
     * Realiza un LEFT JOIN con la tabla imágenes para obtener la carátula si existe.
     * Permite filtrar entre artículos poseídos o deseados y aplicar ordenación dinámica.
     * </p>
     * * @param usuario Nombre del usuario.
     * @param soloListaDeseos Filtro para mostrar solo lista de deseos ('S') o colección ('N').
     * @param orderCol Nombre de la columna por la que ordenar.
     * @param orderDir Dirección de orden (ASC/DESC).
     * @return Matriz con [ID, Título, Artista, Imagen].
     */
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

    /**
     * Recupera todos los CDs de un usuario específico.
     * * @param usuario Nombre del usuario.
     * @param soloListaDeseos Filtro de lista de deseos.
     * @param orderCol Columna de ordenación.
     * @param orderDir Dirección de ordenación.
     * @return Matriz con [ID, Título, Artista, Imagen].
     */
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

    /**
     * Recupera todos los conciertos registrados por un usuario.
     * * @param usuario Nombre del usuario.
     * @param soloListaDeseos Filtro de lista de deseos.
     * @param orderCol Columna de ordenación.
     * @param orderDir Dirección de ordenación.
     * @return Matriz con [ID, Título, Artista, Imagen].
     */
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

    /**
     * Obtiene toda la información detallada de un Vinilo o CD por su ID.
     * * @param id Identificador único del registro.
     * @return Array de String con todos los atributos del álbum o {@code null} si no existe.
     */
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

    /**
     * Obtiene toda la información detallada de un concierto por su ID.
     * * @param id Identificador único del registro.
     * @return Array de String con todos los atributos del concierto o {@code null} si no existe.
     */
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

    /**
     * Cuenta cuántos artículos tiene un usuario en una categoría total.
     * * @param usuario Nombre del usuario.
     * @param tipo Categoría ("V", "C" o "CONCIERTO").
     * @return Total de artículos.
     */
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

    /**
     * Obtiene la distribución de valoraciones (ratings) de un usuario.
     * <p>
     * Este método itera del rating 0 al 5 y cuenta cuántos registros tiene el usuario en cada
     * nivel, facilitando la creación de gráficos de barras o sectores.
     * </p>
     * * @param usuario Nombre del usuario.
     * @param tipo Categoría ("V", "C" o "CONCIERTO").
     * @return Array de 6 posiciones donde el índice es el rating y el valor es la cantidad.
     */
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

    /**
     * Agrupa la colección del usuario por años para generar estadísticas temporales.
     * <p>
     * Realiza un agrupamiento (GROUP BY) por la columna fecha. Para conciertos, extrae el año
     * de la cadena DD/MM/AAAA usando SUBSTRING.
     * </p>
     * * @param usuario Nombre del usuario.
     * @param tipo Categoría de búsqueda.
     * @return Matriz bidimensional ordenada por año: [Año, Cantidad].
     */
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

    /**
     * Calcula la presencia de géneros musicales en la colección.
     * <p>
     * Debido a que la columna Género puede contener múltiples valores (ej: "Rock, Pop"),
     * el método utiliza el operador LIKE para contar cuántas veces aparece cada género
     * de la lista proporcionada.
     * </p>
     * * @param usuario Nombre del usuario.
     * @param tipo Categoría ("V", "C" o "CONCIERTO").
     * @param nombresGenero Array con los nombres de géneros a buscar.
     * @return Array de enteros con las cantidades correspondientes a cada género.
     */
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

    /**
     * Realiza una búsqueda avanzada en la tabla de Vinilos/CDs.
     * <p>
     * Busca coincidencias parciales del texto en las columnas Título, Artista, Fecha
     * y la representación textual del Rating.
     * </p>
     * * @param usuario Usuario que realiza la búsqueda.
     * @param soloListaDeseos Filtro de estado.
     * @param tipo Tipo de artículo ('V' o 'C').
     * @param texto Cadena de búsqueda.
     * @param orderCol Columna de ordenación.
     * @param orderDir Dirección del orden.
     * @return Matriz con los resultados que coinciden con el filtro.
     */
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

    /**
     * Realiza una búsqueda avanzada en la tabla de Conciertos.
     * * @param usuario Usuario que realiza la búsqueda.
     * @param soloListaDeseos Filtro de estado.
     * @param texto Cadena de búsqueda.
     * @param orderCol Columna de ordenación.
     * @param orderDir Dirección del orden.
     * @return Matriz con los resultados que coinciden con el filtro.
     */
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

    /**
     * Elimina permanentemente un registro de Vinilo o CD.
     * <p>
     * Borra primero cualquier referencia en la tabla {@code Imagen} para mantener
     * la integridad referencial antes de eliminar el registro principal.
     * </p>
     * * @param id ID del registro a eliminar.
     */
    // DELETES
    public void eliminarViniloCD(int id) {
        try {
            query.execute("DELETE FROM Imagen WHERE `id_Vinilo/CD` = " + id);
            query.execute("DELETE FROM Vinilo_CD WHERE id = " + id);
            System.out.println("Vinilo/CD eliminado: " + id);
        } catch (Exception e) { System.out.println(e); }
    }

    /**
     * Elimina permanentemente un registro de concierto.
     * * @param id ID del registro a eliminar.
     */
    public void eliminarConcierto(int id) {
        try {
            query.execute("DELETE FROM Imagen WHERE id_Concierto = " + id);
            query.execute("DELETE FROM Concierto WHERE id = " + id);
            System.out.println("Concierto eliminado: " + id);
        } catch (Exception e) { System.out.println(e); }
    }
}