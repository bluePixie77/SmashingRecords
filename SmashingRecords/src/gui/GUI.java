package gui;

import gui.smashRecColors.Colors;
import gui.smashRecFonts.Fonts;
import gui.smashRecPantallas.*;
import processing.core.PApplet;
import processing.core.PImage; // no da error pero no se dibuja el paged table de las estadísticas

import java.io.File;

import static gui.smashRecFonts.Sizes.*;
/**
 * Clase principal de la interfaz gráfica de usuario (GUI) de la aplicación
 * The Smashing Records. Gestiona todas las pantallas, componentes visuales
 * y la lógica de navegación entre vistas.
 *
 * <p>La GUI se organiza en torno a un conjunto de pantallas definidas por el
 * enumerado {@link PANTALLA}. Cada pantalla agrupa un subconjunto de los
 * componentes visuales declarados en esta clase (botones, campos de texto,
 * tarjetas paginadas, etc.), que son inicializados en el constructor y
 * reutilizados a lo largo del ciclo de vida de la aplicación.</p>
 *
 * <p>Esta clase depende de la librería Processing ({@code PApplet}) para el
 * renderizado 2D y de {@link DataBase} para obtener datos persistentes que
 * se muestran en las distintas vistas.</p>
 *
 * @author  Equipo SmashRecords
 * @version 1.0
 */
public class GUI {

    // ─── Enumerados ───────────────────────────────────────────────────────────


    // crear textfields ubicació i género per vinils/Cds
    // fer permetre inserció fotos elegides pel propi usuari

    /**
     * Identifica cada una de las pantallas navegables de la aplicación.
     *
     * <ul>
     *   <li>{@code INICIO}         – Pantalla de inicio de sesión.</li>
     *   <li>{@code USUARIO}        – Perfil y notas del usuario autenticado.</li>
     *   <li>{@code VINILOS}        – Catálogo de vinilos.</li>
     *   <li>{@code CDS}            – Catálogo de CDs.</li>
     *   <li>{@code CONCIERTOS}     – Catálogo de conciertos.</li>
     *   <li>{@code ESTADISTICAS}   – Panel de estadísticas y gráficos.</li>
     *   <li>{@code AGREGAR}        – Formulario para añadir un vinilo o CD.</li>
     *   <li>{@code AGREGAR_CONCERT}– Formulario para añadir un concierto.</li>
     * </ul>
     */
    public enum PANTALLA {INICIO, USUARIO, VINILOS, CDS, CONCIERTOS, ESTADISTICAS, AGREGAR, AGREGAR_CONCERT}

    public enum CatEstadistica {VINILOS, CDS, CONCIERTOS}

    public CatEstadistica categoriaActual = CatEstadistica.VINILOS;


    // Pantalla actual
    public PANTALLA pantallaActual;
    public PANTALLA pantallaAnterior;
    public boolean enablePantalla;

    public String usuarioActual = "";

    // Botones
    public Button b1, b2, b3, b4, b5, b6, b7, bNext, bPrev, bCancelar, bOk, bEliminarMultimedia,
            bCatVinilos, bCatCDs, bCatConciertos, bLoadImage, bSaveImageToDB;

    // Colores
    public Colors appColors;
    int black, gris, white, narFuerte, narFlojo, yellow, pink;

    DataBase db;

    // Texto
    public Fonts appFonts;
    public TextField tFInicioSesion1, tFInicioSesion2, tFBuscador;
    public TextField[] tFMusica;   // Para Vinilo/CD (Título, Artista, Año, Edición)
    public TextField[] tFConcierto; // Para Conciertos (Título, Artista, Fecha, Lugar)
    public TextArea tANotasUsuario, tANotasAgregar;

    // Imatges de la GUI
    public RoundButton rBFilter, rBHeart, rBPlus, rBDelete, rBHeartAgregar;
    RadioButton radioB1, radioB2, radioB3;
    PImage icona1, icona2, logo, imgFilter, imgHeart, imgPlus, imgDelete, imgDisc1, imgDisc2;
    public PImage imgElegida;
    String[] imgs = {"starON.png", "starOFF.png"};

    public String titol = "";
    public boolean esListaDeseos = false;
    public boolean mostrarListaDeseos = false;
    public File file;
    public String rutaCarpeta = "/Users/mariaramis/Desktop/";
    public String rutaCarpetaWindows = "C:\\Usuaris\\mariaramis\\Escriptori\\";

    // Paged Cards
    public PagedCard2D pcMusica;   // Vinilos y CDs
    public PagedCard2D pcConcert;  // Conciertos
    public PagedCard2D pcStats;

    public StatsCard[] misGraficos;
    public int[] paletaGraficos;

    // Pantalla agregar
    public RadioButtonGroup rbgUbicacion, rbgOrigen;
    public CheckBox[] cbGenero;
    public String[] nombresGenero = {"Otro", "Country", "Indie", "Pop", "Rock"};
    public String[] nombresUbicacion = {"Zona 1", "Zona 2", "Zona 3", "Zona 4", "Otro"};
    public String[] nombresOrigen = {"Comprado", "Regalo", "Heredado", "Otro"};
    public int ultimoIdInsertado = -1;
    public int tiempoMensajeGuardado = 0; // frames restantes para mostrar el mensaje
    public boolean imagenGuardadaOk = false; // true = éxito, false = sin imagen

    // Dades de les cards
    String[][] infoAlbum = {
            {"Album 0", "Autor 0", "Data 0", "Secció 0", "Descripció 0"},
            {"Album 1", "Autor 1", "Data 1", "Secció 1", "Descripció 1"},
            {"Album 2", "Autor 2", "Data 2", "Secció 2", "Descripció 2"},
            {"Album 3", "Autor 3", "Data 3", "Secció 1", "Descripció 3"},
            {"Album 4", "Autor 4", "Data 4", "Secció 1", "Descripció 4"},
            {"Album 5", "Autor 5", "Data 5", "Secció 2", "Descripció 5"},
            {"Album 6", "Autor 6", "Data 6", "Secció 2", "Descripció 6"},
            {"Album 7", "Autor 7", "Data 7", "Secció 1", "Descripció 7"},
            {"Album 8", "Autor 8", "Data 8", "Secció 8", "Descripció 8"},
            {"Album 9", "Autor 9", "Data 9", "Secció 9", "Descripció 9"},
            {"Album 10", "Autor 10", "Data 10", "Secció 10", "Descripció 10"},
    };
    String[][] infoConcert;

    // Otros
    public CheckBoxStarList cbl;

    // Constructor de GUI
    public GUI(PApplet p5, DataBase db) {

        this.db = db;

        pantallaActual = PANTALLA.INICIO;

        appColors = new Colors(p5);
        appFonts = new Fonts(p5);
        appFonts.setFonts(p5);

        setMedia(p5);
        setColors(p5);

        setTextFields(p5);
        setBotones(p5);
        setPagedCards(p5);
        setEstadisticas(p5);
        setRadioButtonsYCheckboxes(p5);
    }

    // Setter botones
    public void setBotones(PApplet p5) {
        b1 = new Button(p5, appColors, "Vinilos", 0, p5.height * 0.25f, p5.width * 0.20f, p5.height * 0.05f);
        b2 = new Button(p5, appColors, "CDs", 0, p5.height * 0.35f, p5.width * 0.20f, p5.height * 0.05f);
        b3 = new Button(p5, appColors, "Conciertos", 0, p5.height * 0.45f, p5.width * 0.20f, p5.height * 0.05f);
        b4 = new Button(p5, appColors, "Estadísticas", 0, p5.height * 0.65f, p5.width * 0.20f, p5.height * 0.05f);
        b5 = new Button(p5, appColors, "Sesión", 0, p5.height * 0.95f, p5.width * 0.20f, p5.height * 0.05f);
        b6 = new Button(p5, appColors, "Iniciar sesión", p5.width * 0.5f - (p5.width * 0.125f), p5.height * 0.73f, p5.width * 0.25f, p5.height * 0.052f);
        b7 = new Button(p5, appColors, "Cerrar sesión", p5.width * 0.525f, p5.height * 0.49f, p5.width * 0.15f, p5.height * 0.052f);
        bNext = new Button(p5, appColors, ">", p5.width * 0.93f, p5.height * 0.92f, p5.width * 0.04f, p5.width * 0.04f);
        bPrev = new Button(p5, appColors, "<", p5.width * 0.24f, p5.height * 0.92f, p5.width * 0.04f, p5.width * 0.04f);
        bCancelar = new Button(p5, appColors, "CANCELAR", p5.width * 0.75f, p5.height * 0.1f, p5.width * 0.1f, p5.height * 0.052f);
        bOk = new Button(p5, appColors, "OK", p5.width * 0.85f, p5.height * 0.1f, p5.width * 0.1f, p5.height * 0.052f);
        bEliminarMultimedia = new Button(p5, appColors, "ELIMINAR", p5.width * 0.60f, p5.height * 0.1f, p5.width * 0.1f, p5.height * 0.052f);

        // Creació del Botó
        bLoadImage = new Button(p5, appColors, "LOAD", p5.width * 0.38f, p5.height * 0.57f, p5.width * 0.2f, p5.height * 0.05f);
        bSaveImageToDB = new Button(p5, appColors, "SAVE", p5.width * 0.6f, p5.height * 0.57f, p5.width * 0.2f, p5.height * 0.05f);

        rBFilter = new RoundButton(p5, appColors, imgFilter, p5.width * 0.85f, p5.height * 0.15f, p5.width * 0.020f);
        rBHeart = new RoundButton(p5, appColors, imgHeart, p5.width * 0.90f, p5.height * 0.15f, p5.width * 0.020f);
        rBPlus = new RoundButton(p5, appColors, imgPlus, p5.width * 0.95f, p5.height * 0.15f, p5.width * 0.020f);
        rBDelete = new RoundButton(p5, appColors, imgDelete, p5.width * 0.60f, p5.height * 0.13f, p5.width * 0.020f);
        rBHeartAgregar = new RoundButton(p5, appColors, imgHeart, p5.width * 0.55f, p5.height * 0.13f, p5.width * 0.020f);

        float xB = p5.width * 0.25f;
        float yB = p5.height * 0.15f;
        float wB = 120;
        float hB = 40;

        bCatVinilos = new Button(p5, appColors, "Vinilos", xB, yB, wB, hB);
        bCatCDs = new Button(p5, appColors, "CDs", xB + wB + 10, yB, wB, hB);
        bCatConciertos = new Button(p5, appColors, "Conciertos", xB + (wB + 10) * 2, yB, wB, hB);
        // Al empezar, Vinilos está seleccionado, así que lo "desactivamos" visualmente
        actualizarEstadoBotones();
    }

    public void setTextFields(PApplet p5) {
        tFInicioSesion1 = new TextField(p5, appColors, 40, p5.width * 0.36f, p5.height * 0.50f, p5.width * 0.28f, p5.height * 0.05f);
        tFInicioSesion2 = new TextField(p5, appColors, 40, p5.width * 0.36f, p5.height * 0.60f, p5.width * 0.28f, p5.height * 0.05f);
        // tFNotasUsuario = new TextField(p5, appColors, 40, p5.width * 0.25f, p5.height * 0.60f, p5.width * 0.70f, p5.height * 0.35f);
        tFBuscador = new TextField(p5, appColors, 60, p5.width * 0.24f, p5.height * 0.1f, p5.width * 0.56f, p5.height * 0.10f);

        float startX = p5.width * 0.38f;
        float startY = p5.height * 0.27f;
        float fieldW = p5.width * 0.50f;
        float fieldH = p5.height * 0.05f;
        float spacing = p5.height * 0.10f;

        // --- CONFIGURACIÓN MÚSICA (4 campos) ---
        tFMusica = new TextField[4];
        tFMusica[0] = new TextField(p5, appColors, 40, startX, startY, fieldW, fieldH); // Titulo
        tFMusica[1] = new TextField(p5, appColors, 40, startX, startY + spacing, fieldW, fieldH); // Artista
        float shortW = fieldW * 0.30f;
        float gap = p5.width * 0.02f;
        tFMusica[2] = new TextField(p5, appColors, 40, startX, startY + (spacing * 2), shortW, fieldH); // Año
        tFMusica[3] = new TextField(p5, appColors, 40, startX + shortW + gap, startY + (spacing * 2), fieldW - shortW - gap, fieldH); // Edición

        float sX = p5.width * 0.68f;
        float fW = p5.width * 0.3f;
        // --- CONFIGURACIÓN CONCIERTOS (4 campos) ---
        tFConcierto = new TextField[4];
        tFConcierto[0] = new TextField(p5, appColors, 40, sX, startY, fW, fieldH); // Titulo
        tFConcierto[1] = new TextField(p5, appColors, 40, sX, startY + spacing, fW, fieldH); // Artista
        tFConcierto[2] = new TextField(p5, appColors, 40, sX, startY + (spacing * 2), fW, fieldH); // Fecha
        tFConcierto[3] = new TextField(p5, appColors, 40, sX, startY + (spacing * 3), fW, fieldH); // Lugar

        tANotasUsuario = new TextArea(p5, appColors, p5.width * 0.25f, p5.height * 0.60f, p5.width * 0.70f, p5.height * 0.35f, 40, 10);
        tANotasAgregar = new TextArea(p5, appColors, p5.width * 0.05f, p5.height * 0.82f, p5.width * 0.62f, p5.height * 0.18f, 65, 4);
    }

    public void setMedia(PApplet p5) {
        icona1 = p5.loadImage("data/iconEmptyUser.png");   // si fuera imagen transparente svg (loadShape: vectorial)
        icona2 = p5.loadImage("data/iconFullUser.png");
        logo = p5.loadImage("data/logo.png");
        imgFilter = p5.loadImage("data/imgFilter.png");
        imgHeart = p5.loadImage("data/imgHeart.png");
        imgPlus = p5.loadImage("data/imgPlus.png");
        imgDelete = p5.loadImage("data/imgDelete.png");

        imgDisc1 = p5.loadImage("data/musicPredetBlackBG.png");
        imgDisc2 = p5.loadImage("data/musicPredetWhiteBG.png");

        infoConcert = db.getInfoArrayPetit2DConcert();
    }

    public void setColors(PApplet p5) {
        black = appColors.getFourthColor();
        gris = appColors.getFifthColor();
        white = appColors.getThirdColor();
        narFuerte = appColors.getFirstColor();
        narFlojo = appColors.getSecondColor();
        yellow = appColors.getSixthColor();
        pink = appColors.getSeventhColor();

        paletaGraficos = new int[]{
                narFuerte,
                narFlojo,
                yellow,
                pink,
                gris,
                black,
                white
        };
    }

    public void setPagedCards(PApplet p5) {
        int files = db.getNumFilesTaula("Concierto");

        // MÚSICA (Vinilos y CDs)
        pcMusica = new PagedCard2D(p5, appColors, 2, 4, Card.tipoCard.ALBUM);
        pcMusica.setDimensions(p5.width * 0.24f, p5.height * 0.25f, p5.width * 0.73f, p5.height * 0.65f);

        // CONCIERTOS
        pcConcert = new PagedCard2D(p5, appColors, 4, 2, Card.tipoCard.CONCERT);
        // (Mismas dimensiones, para mantener simetría)
        pcConcert.setDimensions(p5.width * 0.24f, p5.height * 0.25f, p5.width * 0.73f, p5.height * 0.65f);

    }

    public void setEstadisticas(PApplet p5) {
        // 1. Inicializar la tabla (1 fila, 1 columna para que el gráfico sea grande)
        pcStats = new PagedCard2D(p5, appColors, 1, 1, Card.tipoCard.ESTADIST);
        pcStats.setDimensions(p5.width * 0.24f, p5.height * 0.25f, p5.width * 0.73f, p5.height * 0.65f);

        // 2. Crear los 3 objetos de gráficos
        misGraficos = new StatsCard[3];
        misGraficos[0] = new SectorDiagram("Ratings", pcStats.x, pcStats.y, pcStats.w, pcStats.h);
        misGraficos[1] = new LinesDiagram("Evolución", pcStats.x, pcStats.y, pcStats.w, pcStats.h);
        misGraficos[2] = new BarsDiagram("Géneros", pcStats.x, pcStats.y, pcStats.w, pcStats.h);

        // 3. Pasar el array de objetos directamente
        pcStats.setCards(misGraficos);

        // 4. Cargar datos iniciales
        actualizarDatosGraficos(p5);
    }

    public void setRadioButtonsYCheckboxes(PApplet p5) {
        // Construcción checkboxstarlist
        cbl = new CheckBoxStarList(p5, 5, imgs, p5.width * 0.05f, p5.height * 0.72f, p5.width * 0.05f, p5.height * 0.065f);
        cbl.setCheckBoxStars(0);
        // --- GÉNERO (CheckBoxes, selección múltiple) ---
        cbGenero = new CheckBox[nombresGenero.length];
        float xCB = p5.width * 0.38f;
        float yCB = p5.height * 0.75f;
        for (int i = 0; i < nombresGenero.length; i++) {
            cbGenero[i] = new CheckBox(p5, (int) (xCB + i * p5.width * 0.10f), (int) yCB, 20);
            // el texto lo dibujamos en display manualmente
        }

        // --- UBICACIÓN (RadioButtons, selección única) ---
        rbgUbicacion = new RadioButtonGroup(nombresUbicacion.length);
        RadioButton[] rbsUbic = new RadioButton[nombresUbicacion.length];
        float xRBU = p5.width * 0.38f;
        float yRBU = p5.height * 0.85f;
        for (int i = 0; i < nombresUbicacion.length; i++) {
            rbsUbic[i] = new RadioButton(p5, (int) (xRBU + i * p5.width * 0.12f), (int) yRBU, 10);
            rbsUbic[i].setText(nombresUbicacion[i]);
        }
        rbgUbicacion.setRadioButtons(rbsUbic[0], rbsUbic[1], rbsUbic[2], rbsUbic[3], rbsUbic[4]);

        // --- ORIGEN (RadioButtons, selección única) ---
        rbgOrigen = new RadioButtonGroup(nombresOrigen.length);
        RadioButton[] rbsOrig = new RadioButton[nombresOrigen.length];
        float xRBO = p5.width * 0.38f;
        float yRBO = p5.height * 0.92f;
        for (int i = 0; i < nombresOrigen.length; i++) {
            rbsOrig[i] = new RadioButton(p5, (int) (xRBO + i * p5.width * 0.12f), (int) yRBO, 10);
            rbsOrig[i].setText(nombresOrigen[i]);
        }
        rbgOrigen.setRadioButtons(rbsOrig[0], rbsOrig[1], rbsOrig[2], rbsOrig[3]);
    }

    // PANTALLAS DE LA GUI
    public void displayPantallaInicioSesion(PApplet p5) {
        p5.push();
        p5.background(black);
        p5.rectMode(p5.CENTER);
        p5.textAlign(p5.CENTER);
        p5.fill(black);
        p5.strokeWeight(2);
        p5.stroke(white);
        p5.rect(p5.width * 0.5f, p5.height * 0.5f, p5.width * 0.33f, p5.height * 0.80f);

        displayLogoMayor(p5); // logo en dimensión grande

        p5.textFont(appFonts.getSecondFont());
        p5.fill(narFuerte);
        p5.textSize(medidaTitulo - 14);
        p5.text("THE SMASHING RECORDS", p5.width * 0.5f, p5.height * 0.16f);
        p5.pop();
        p5.push();
        p5.textFont(appFonts.getThirdFont());
        p5.fill(narFuerte);
        p5.textSize(medidaIntermedia);
        p5.text("Correo electrónico", p5.width * 0.36f, p5.height * 0.49f);
        tFInicioSesion1.display(p5);
        p5.text("Contraseña", p5.width * 0.36f, p5.height * 0.59f);
        tFInicioSesion2.display(p5);

        b6.display(p5); // Iniciar sesión
        p5.pop();
    }

    public void displayPantallaUsuario(PApplet p5) {
        p5.push();
        p5.background(black);
        displaySidebar(p5);

        p5.circle(p5.width * 0.35f, p5.height * 0.25f, p5.width * 0.20f);
        p5.textAlign(p5.CENTER);
        p5.textSize(medidaSubtitulo);
        p5.textFont(appFonts.getThirdFont());
        p5.fill(white);

        //db.getNomUsuario
        p5.text("Nombre: Jane Doe", p5.width * 0.60f, p5.height * 0.38f);
        p5.text("Correo: janeDoe@gmail.com", p5.width * 0.60f, p5.height * 0.45f);
        b7.display(p5); // Cerrar sesión
        p5.textAlign(p5.CORNER);
        p5.text("Notas", p5.width * 0.25f, p5.height * 0.59f);

        p5.textFont(appFonts.getForthFont());
        tANotasUsuario.display(p5);
        p5.pop();

    }

    public void displayPantallaVinilos(PApplet p5) {
        p5.push();
        p5.background(black);
        displaySidebar(p5);
        displayBuscadorYFiltros(p5);
        displayDisposicionMusica(p5);

        p5.textFont(appFonts.getFontAt(0));
        p5.fill(narFuerte);
        p5.textSize(medidaTitulo);
        p5.text("VINILOS", p5.width * 0.25f, p5.height * 0.10f);
        p5.pop();
    }

    public void displayPantallaCDs(PApplet p5) {
        p5.push();
        p5.background(black);
        displaySidebar(p5);
        displayBuscadorYFiltros(p5);
        displayDisposicionMusica(p5);

        p5.textFont(appFonts.getFontAt(0));
        p5.fill(narFuerte);
        p5.textSize(medidaTitulo);
        p5.text("CD's", p5.width * 0.25f, p5.height * 0.10f);

        p5.fill(white);

        p5.pop();
    }

    public void displayPantallaConciertos(PApplet p5) {
        p5.push();
        p5.background(black);
        displaySidebar(p5);
        displayBuscadorYFiltros(p5);
        p5.push();
        pcConcert.display(p5);
        bNext.display(p5);
        bPrev.display(p5);
        p5.pop();

        p5.textFont(appFonts.getFontAt(0));
        p5.fill(narFuerte);
        p5.textSize(medidaTitulo);
        p5.text("Conciertos", p5.width * 0.25f, p5.height * 0.10f);

        p5.fill(white);

        p5.pop();
    }

    public void displayPantallaEstadisticas(PApplet p5) {
        p5.push();
        p5.background(black);
        displaySidebar(p5);
        // tFBuscador.display(p5);

        bCatVinilos.display(p5);
        bCatCDs.display(p5);
        bCatConciertos.display(p5);
        bNext.display(p5);
        bPrev.display(p5);

        pcStats.display(p5);

        p5.textFont(appFonts.getFontAt(0));
        p5.fill(narFuerte);
        p5.textSize(medidaTitulo);
        p5.text("Estadísticas", p5.width * 0.25f, p5.height * 0.10f);
        p5.pop();
    }

    public void displayPantallaAgregarMusica(PApplet p5) {
        p5.push();
        p5.background(black);
        displayLogo(p5);
        bCancelar.display(p5);
        bOk.display(p5);
        bEliminarMultimedia.display(p5);

        rBDelete.display(p5);
        rBHeartAgregar.fillColor = esListaDeseos ? narFuerte : narFlojo;
        rBHeartAgregar.fillColorOver = esListaDeseos ? narFuerte : narFlojo;
        rBHeartAgregar.display(p5);

        cbl.display(p5);
        p5.fill(white);
        p5.text(cbl.getNumSelected() + "/5", p5.width * 0.32f, p5.height * 0.77f);

        // Dibuixa la imatge
        if (imgElegida != null) {
            p5.image(imgElegida, p5.width * 0.05f, p5.height * 0.24f, p5.width * 0.3f, p5.width * 0.3f);
            p5.textSize(24);
            p5.textAlign(p5.LEFT);
            p5.fill(white);
            p5.text(titol, p5.width * 0.38f, p5.height * 0.67f);
        } else {
            p5.image(imgDisc2, p5.width * 0.05f, p5.height * 0.24f, p5.width * 0.3f, p5.width * 0.3f);
            p5.textSize(24);
            p5.textAlign(p5.LEFT);
            p5.text("Sense imatge", p5.width * 0.38f, p5.height * 0.67f);
        }

        bLoadImage.x    = p5.width * 0.38f;
        bLoadImage.y    = p5.height * 0.57f;
        bLoadImage.w    = p5.width * 0.2f;
        bSaveImageToDB.x = p5.width * 0.60f;
        bSaveImageToDB.y = p5.height * 0.57f;
        bSaveImageToDB.w = p5.width * 0.2f;

        bLoadImage.display(p5);
        bSaveImageToDB.display(p5);

        // Título dinámico según el origen
        p5.textFont(appFonts.getFontAt(0));
        p5.fill(narFuerte);
        p5.textSize(medidaTitulo);
        p5.textAlign(p5.LEFT);
        String txtTitulo = ((pantallaAnterior == PANTALLA.VINILOS) ? "AGREGAR VINILO" : "AGREGAR CD");
        p5.text(txtTitulo, p5.width * 0.25f, p5.height * 0.10f);

        // Género
        float xCB = p5.width * 0.38f;
        float yCB = p5.height * 0.75f;
        for (int i = 0; i < cbGenero.length; i++) {
            cbGenero[i].x = (int)(xCB + i * p5.width * 0.10f);
            cbGenero[i].y = (int)yCB;
        }

        p5.textFont(appFonts.getFontAt(3));
        p5.fill(white);
        p5.textSize(medidaIntermedia);
        p5.textAlign(p5.LEFT);
        p5.text("Género *", p5.width * 0.38f, p5.height * 0.72f);
        for (int i = 0; i < cbGenero.length; i++) {
            cbGenero[i].display(p5);
            p5.fill(white);
            p5.textSize(24);
            p5.text(nombresGenero[i], p5.width * 0.38f + i * p5.width * 0.10f + 25, p5.height * 0.77f);
        }

        // Ubicación
        p5.textSize(medidaIntermedia);
        p5.text("Ubicación *", p5.width * 0.38f, p5.height * 0.82f);
        rbgUbicacion.display(p5);
        // Origen
        p5.textSize(medidaIntermedia);
        p5.text("Origen *", p5.width * 0.38f, p5.height * 0.89f);
        rbgOrigen.display(p5);

        p5.line(p5.width * 0.23f, p5.height * 0.20f, p5.width * 0.97f, p5.height * 0.20f);

        p5.fill(white);
        p5.textSize(medidaIntermedia);
        p5.text("Título *", tFMusica[0].x, tFMusica[0].y - 10);
        tFMusica[0].display(p5);
        p5.text("Artista *", tFMusica[1].x, tFMusica[1].y - 10);
        tFMusica[1].display(p5);
        p5.text("Año", tFMusica[2].x, tFMusica[2].y - 10);
        tFMusica[2].display(p5);
        p5.text("Edición", tFMusica[3].x, tFMusica[3].y - 10);
        tFMusica[3].display(p5);

        tANotasAgregar.x = p5.width * 0.05f;
        tANotasAgregar.y = p5.height * 0.82f;
        tANotasAgregar.w = p5.width * 0.30f;
        tANotasAgregar.h = p5.height * 0.18f;
        tANotasAgregar.display(p5);

        if (tiempoMensajeGuardado > 0) {
            p5.fill(imagenGuardadaOk ? narFuerte : pink); // naranja si ok, otro color si no imagen
            p5.textFont(appFonts.getFontAt(3));
            p5.textSize(medidaParrafo);
            p5.textAlign(p5.LEFT);
            p5.text(imagenGuardadaOk ? "Imagen guardada" : "No hay imagen seleccionada", p5.width * 0.38f, p5.height * 0.555f);
            tiempoMensajeGuardado--;
        }

        p5.pop();
    }

    public void displayPantallaAgregarConcert(PApplet p5) {
        p5.push();
        p5.background(black);
        displayLogo(p5);
        bCancelar.display(p5);
        bOk.display(p5);
        bEliminarMultimedia.display(p5);

        rBDelete.display(p5);
        rBHeartAgregar.fillColor = esListaDeseos ? narFuerte : narFlojo;
        rBHeartAgregar.fillColorOver = esListaDeseos ? narFuerte : narFlojo;
        rBHeartAgregar.display(p5);

        // Imagen
        if (imgElegida != null) {
            p5.image(imgElegida, p5.width * 0.05f, p5.height * 0.24f, p5.width * 0.62f, p5.width * 0.3f);
            p5.fill(white); p5.textSize(24); p5.textAlign(p5.LEFT);
            p5.text(titol, p5.width * 0.38f, p5.height * 0.77f);
        } else {
            p5.image(imgDisc2, p5.width * 0.05f, p5.height * 0.24f, p5.width * 0.62f, p5.width * 0.3f);
            p5.fill(white); p5.textSize(24); p5.textAlign(p5.LEFT);
            p5.text("Sense imatge", p5.width * 0.38f, p5.height * 0.77f);
        }

        // Reposicionar botones para concierto
        bLoadImage.x    = p5.width * 0.72f;
        bLoadImage.y    = p5.height * 0.67f;
        bLoadImage.w    = p5.width * 0.11f;
        bSaveImageToDB.x = p5.width * 0.83f;
        bSaveImageToDB.y = p5.height * 0.67f;
        bSaveImageToDB.w = p5.width * 0.11f;

        bLoadImage.display(p5);
        bSaveImageToDB.display(p5);

        cbl.display(p5);
        p5.fill(white); p5.textSize(24);
        p5.text(cbl.getNumSelected() + "/5", p5.width * 0.32f, p5.height * 0.77f);

        p5.textFont(appFonts.getFontAt(0));
        p5.fill(narFuerte);
        p5.textSize(medidaTitulo);
        p5.text("AGREGAR CONCIERTO", p5.width * 0.25f, p5.height * 0.10f);

        p5.line(p5.width * 0.23f, p5.height * 0.20f, p5.width * 0.97f, p5.height * 0.20f);

        p5.textFont(appFonts.getFontAt(3));
        p5.fill(white);
        p5.textSize(medidaIntermedia);
        p5.text("Título *", tFConcierto[0].x, tFConcierto[0].y - 10);
        tFConcierto[0].display(p5);
        p5.text("Artista *", tFConcierto[1].x, tFConcierto[1].y - 10);
        tFConcierto[1].display(p5);
        p5.text("Fecha", tFConcierto[2].x, tFConcierto[2].y - 10);
        tFConcierto[2].display(p5);
        p5.text("Lugar / Recinto", tFConcierto[3].x, tFConcierto[3].y - 10);
        tFConcierto[3].display(p5);

        // Reposicionar y mostrar checkboxes de género
        float xCB = p5.width * 0.68f;
        float yCB = p5.height * 0.83f;
        for (int i = 0; i < cbGenero.length; i++) {
            cbGenero[i].x = (int)(xCB + i * p5.width * 0.06f);
            cbGenero[i].y = (int)yCB;
        }

        p5.fill(white); p5.textSize(medidaIntermedia); p5.textAlign(p5.LEFT);
        p5.text("Género *", xCB, yCB - 10);
        for (int i = 0; i < cbGenero.length; i++) {
            cbGenero[i].display(p5);
            p5.fill(white); p5.textSize(18);
            p5.text(nombresGenero[i], xCB + i * p5.width * 0.06f + 25, yCB + 5);
        }

        tANotasAgregar.x = p5.width * 0.05f;
        tANotasAgregar.y = yCB;
        tANotasAgregar.w = p5.width * 0.62f;
        tANotasAgregar.h = p5.height * 0.12f;
        p5.fill(white); p5.textSize(medidaIntermedia); p5.textAlign(p5.LEFT);
        p5.text("Notas", p5.width*0.05f, yCB - 10);
        tANotasAgregar.display(p5);

        if (tiempoMensajeGuardado > 0) {
            p5.fill(imagenGuardadaOk ? narFuerte : pink); // naranja si ok, otro color si no imagen
            p5.textFont(appFonts.getFontAt(3));
            p5.textSize(medidaParrafo);
            p5.textAlign(p5.CENTER);
            p5.text(imagenGuardadaOk ? "Imagen guardada" : "No hay imagen seleccionada", p5.width * 0.83f, p5.height * 0.655f);
            tiempoMensajeGuardado--;
        }

        p5.pop();
    }

    // ZONAS DE LA GUI
    public void displayLogo(PApplet p5) {
        //p5.circle(p5.width * 0.10f, p5.height * 0.125f, p5.width * 0.12f);
        p5.push();
        p5.imageMode(p5.CENTER);
        p5.image(logo, p5.width * 0.10f, p5.height * 0.1f, p5.width * 0.1f, p5.height * 0.15f);
        p5.stroke(white);
        p5.line(p5.width * 0.20f, 0, p5.width * 0.20f, p5.height * 0.20f);
        p5.line(0, p5.height * 0.20f, p5.width * 0.20f, p5.height * 0.20f);
        p5.pop();
        /*p5.imageMode(p5.CENTER); p5.scale(p5.width*0.3f);
        p5.image(icona1, p5.width*0.10f, p5.height*0.1f);
        p5.image(icona2, p5.width*0.10f, p5.height*0.1f);*/
    }

    public void displayLogoMayor(PApplet p5) {
        p5.push();
        p5.imageMode(p5.CENTER);
        p5.image(logo, p5.width * 0.5f, p5.height * 0.32f, p5.width * 0.17f, p5.height * 0.25f);
        p5.pop();
    }

    public void displaySidebar(PApplet p5) {
        p5.pop();
        p5.fill(black);
        p5.strokeWeight(2);
        p5.stroke(white);
        p5.rect(0, 0, p5.width * 0.20f, p5.height);
        displayLogo(p5);
        p5.textFont(appFonts.getFontAt(2));
        b1.display(p5); // Vinilos
        b2.display(p5); // CDs
        b3.display(p5); // Conciertos
        b4.display(p5); // Estadísticas
        b5.display(p5); // Iniciar Sesión

        p5.push();
    }

    public void displayBuscadorYFiltros(PApplet p5) {
        p5.push();
        tFBuscador.display(p5);
        rBFilter.display(p5); //
        rBPlus.display(p5);
        rBHeart.fillColor     = mostrarListaDeseos ? narFuerte : narFlojo;
        rBHeart.fillColorOver = mostrarListaDeseos ? narFuerte : narFlojo;
        rBHeart.display(p5);

        if (mostrarListaDeseos) {
            p5.fill(narFuerte);
            p5.textFont(appFonts.getFontAt(2));
            p5.textSize(medidaIntermedia);
            p5.textAlign(p5.RIGHT);
            p5.text("Lista de deseos", p5.width * 0.8f, p5.height * 0.09f);
        }

        p5.pop();
    }

    public void displayDisposicionMusica(PApplet p5) {
        p5.push();
        pcMusica.display(p5);
        bNext.display(p5);
        bPrev.display(p5);
        p5.pop();
    }

    public void actualizarDatosGraficos(PApplet p5) {
        float[] dataSectores, dataLineas, dataBarras;
        String[] tagsSectores, tagsLineas, tagsBarras;

        if (categoriaActual == CatEstadistica.VINILOS) {
            dataSectores = new float[]{10, 25, 40, 15, 10}; // Ratings 1-5 estrellas
            tagsSectores = new String[]{"1*", "2*", "3*", "4*", "5*"};
            dataLineas = new float[]{4, 2, 1, 10}; // Años
            tagsLineas = new String[]{"2021", "2022", "2023", "2024"};
            dataBarras = new float[]{42, 24, 20}; // Géneros
            tagsBarras = new String[]{"Punk", "Pop", "Indie"};
        } else if (categoriaActual == CatEstadistica.CDS) {
            dataSectores = new float[]{3, 2, 1, 10, 40}; // Ratings 1-5 estrellas
            tagsSectores = new String[]{"1*", "2*", "3*", "4*", "5*"};
            dataLineas = new float[]{2, 5, 8, 12}; // Años
            tagsLineas = new String[]{"2021", "2022", "2023", "2024"};
            dataBarras = new float[]{70, 33, 32}; // Géneros
            tagsBarras = new String[]{"Punk", "Pop", "Indie"};
        } else {// categoriaActual == CatEstadistica.CONCIERTOS
            dataSectores = new float[]{1, 2, 41, 14, 70}; // Ratings 1-5 estrellas
            tagsSectores = new String[]{"1*", "2*", "3*", "4*", "5*"};
            dataLineas = new float[]{1, 2, 7, 14}; // Años
            tagsLineas = new String[]{"2021", "2022", "2023", "2024"};
            dataBarras = new float[]{20, 56, 21}; // Géneros
            tagsBarras = new String[]{"Punk", "Pop", "Indie"};
        }
        // Inyectamos los datos en los objetos que ya existen en el array
        ((SectorDiagram) misGraficos[0]).setValues(dataSectores);
        ((SectorDiagram) misGraficos[0]).setTexts(tagsSectores);
        ((SectorDiagram) misGraficos[0]).setColors(this.paletaGraficos);

        ((LinesDiagram) misGraficos[1]).setValues(dataLineas);
        ((LinesDiagram) misGraficos[1]).setTexts(tagsLineas);
        ((LinesDiagram) misGraficos[1]).setColors(narFuerte);

        ((BarsDiagram) misGraficos[2]).setValues(dataBarras);
        ((BarsDiagram) misGraficos[2]).setTexts(tagsBarras);
        ((BarsDiagram) misGraficos[2]).setColors(this.paletaGraficos);
    }

    public void actualizarEstadoBotones() {
        bCatVinilos.setEnabled(categoriaActual != CatEstadistica.VINILOS);
        bCatCDs.setEnabled(categoriaActual != CatEstadistica.CDS);
        bCatConciertos.setEnabled(categoriaActual != CatEstadistica.CONCIERTOS);
    }
    public void resetPantallaAgregar() {
        // Vaciar textos
        for (TextField tf : tFMusica) { tf.setText(""); }
        for (TextField tf : tFConcierto) { tf.setText(""); }
        tANotasAgregar.setText("");

        // Resetear Géneros (que son objetos CheckBox)
        for (CheckBox cb : cbGenero) {
            cb.setChecked(false);
        }

        // Resetear Estrellas
        cbl.reset();

        // Resetear RadioButtons
        rbgUbicacion.reset();
        rbgOrigen.reset();

        // Resetear Imagen y Archivo
        this.file = null;        // Borra la referencia al archivo seleccionado
        this.imgElegida = null;  // Borra la imagen cargada en memoria

        // Resetear título de imagen
        this.titol = "";

        // Resetear boolean lista deseos
        esListaDeseos = false;
    }
    public void recargarCards(PApplet p5) {
        String[][] data;
        if (pantallaActual == PANTALLA.VINILOS) {
            data = db.getVinilosUsuario(usuarioActual, mostrarListaDeseos);
        } else if (pantallaActual == PANTALLA.CDS) {
            data = db.getCDsUsuario(usuarioActual, mostrarListaDeseos);
        } else {
            data = db.getConciertosUsuario(usuarioActual, mostrarListaDeseos);
        }

        PagedCard2D pc = (pantallaActual == PANTALLA.CONCIERTOS) ? pcConcert : pcMusica;
        pc.setData(data);
        pc.setCards();
        if (data.length > 0) {   // solo reconstruir cards si hay datos
            pc.setCards();
            // Asignar imagen a cada card: real si tiene nombre, imgDisc2 si no
            for (int i = 0; i < pc.cards.length; i++) {
                String nombreImg = data[i][2];
                if (nombreImg != null && !nombreImg.isEmpty()) {
                    PImage img = p5.loadImage(rutaCarpeta + nombreImg);
                    pc.cards[i].setImage(img != null ? img : imgDisc2);
                } else {
                    pc.cards[i].setImage(imgDisc2);
                }
            }
        }
    }
}