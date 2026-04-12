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
    public enum PANTALLA {PLOGO, INICIO, REGISTRO, USUARIO, VINILOS, CDS, CONCIERTOS, ESTADISTICAS, AGREGAR, AGREGAR_CONCERT,
        DETALLE, DETALLE_CONCIERTO}

    public enum CatEstadistica {VINILOS, CDS, CONCIERTOS}

    public CatEstadistica categoriaActual = CatEstadistica.VINILOS;


    // Pantalla actual
    public PANTALLA pantallaActual;
    public PANTALLA pantallaAnterior;
    public boolean enablePantalla;

    public String usuarioActual = "";

    // Botones
    public Button b1, b2, b3, b4, b5, b6, b7, bNext, bPrev, bCancelar, bOk,
           bCatVinilos, bCatCDs, bCatConciertos, bLoadImage, bSaveImageToDB, bDetalle, bGuardarNotasUsuario,
           bCambiarFotoPerfil, bCrearCuenta, bIrRegistro, bVolverInicio;

    public PopUp popUpConfirmacionEliminar;

    // Colores
    public Colors appColors;
    public int black, gris, white, narFuerte, narFlojo, yellow, pink;

    DataBase db;

    // Texto
    public Fonts appFonts;
    public TextField tFInicioSesion1, tFInicioSesion2, tFRegistroNombre, tFRegistroCorreo, tFRegistroPass, tFBuscador;
    public TextField[] tFMusica;   // Para Vinilo/CD (Título, Artista, Año, Edición)
    public TextField[] tFConcierto; // Para Conciertos (Título, Artista, Fecha, Lugar)
    public TextArea tANotasUsuario, tANotasAgregar;

    // Imatges de la GUI
    public RoundButton rBFilter, rBHeart, rBPlus, rBDelete, rBHeartAgregar;
    RadioButton radioB1, radioB2, radioB3;
    PImage iconaDefaultUser, icona2, logo, imgFilter, imgHeart, imgPlus, imgDelete, imgDisc1, imgDefaultViniloCD, imgDefaultConcierto;
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
    public int tiempoNotasGuardadas = 0; // frames restantes para mostrar el mensaje

    public int idSeleccionado = -1; // para saber qué elemento se quiere eliminar
    public boolean modoDetalle = false; // false = agregar, true = ver/editar detalle

    public int totalActual = 0; // Estadística

    // Filtro, buscador sesión
    public int ordenActual = 0; // 0=TítA-Z, 1=TítZ-A, 2=ArtA-Z, 3=ArtZ-A
    public int tiempoMensajeOrden = 0;
    public String textoBusqueda = "";

    // Usuario y sesión
    public String[] datosUsuarioActual = {"", "", ""};
    public PImage imgPerfil = null;
    public String titolPerfil = "";
    public File filePerfil = null;
    public String mensajeRegistro = "";
    public int tiempoMensajeRegistro = 0;
    public boolean registroOk = false;
    public int tiempoPLogo = 240; // 4 segundos a 60fps


    String[][] infoConcert;

    // Otros
    public CheckBoxStarList cbl;

    // Constructor de GUI
    public GUI(PApplet p5, DataBase db) {

        this.db = db;

        pantallaActual = PANTALLA.PLOGO;

        appColors = new Colors(p5);
        appFonts = new Fonts(p5);
        appFonts.setFonts(p5);

        setMedia(p5);
        setColors(p5);

        setTextFields(p5);
        setBotones(p5);
        setPagedCards(p5);
        setEstadisticas(p5);
        setRadioButtonsYCheckboxesMusica(p5);
    }

    // Setter botones
    public void setBotones(PApplet p5) {
        b1 = new Button(p5, appColors, "Vinilos", 0, p5.height * 0.25f, p5.width * 0.20f, p5.height * 0.05f);
        b2 = new Button(p5, appColors, "CDs", 0, p5.height * 0.35f, p5.width * 0.20f, p5.height * 0.05f);
        b3 = new Button(p5, appColors, "Conciertos", 0, p5.height * 0.45f, p5.width * 0.20f, p5.height * 0.05f);
        b4 = new Button(p5, appColors, "Estadísticas", 0, p5.height * 0.65f, p5.width * 0.20f, p5.height * 0.05f);
        b5 = new Button(p5, appColors, "Sesión", 0, p5.height * 0.95f, p5.width * 0.20f, p5.height * 0.05f);
        b6 = new Button(p5, appColors, "INICIAR SESIÓN", p5.width * 0.5f - (p5.width * 0.125f), p5.height * 0.73f, p5.width * 0.25f, p5.height * 0.052f);
        b7 = new Button(p5, appColors, "CERRAR SESIÓN", p5.width * 0.525f, p5.height * 0.49f, p5.width * 0.15f, p5.height * 0.052f);
        bNext = new Button(p5, appColors, ">", p5.width * 0.93f, p5.height * 0.92f, p5.width * 0.04f, p5.width * 0.04f);
        bPrev = new Button(p5, appColors, "<", p5.width * 0.24f, p5.height * 0.92f, p5.width * 0.04f, p5.width * 0.04f);
        bCancelar = new Button(p5, appColors, "CANCELAR", p5.width * 0.75f, p5.height * 0.1f, p5.width * 0.1f, p5.height * 0.052f);
        bOk = new Button(p5, appColors, "OK", p5.width * 0.85f, p5.height * 0.1f, p5.width * 0.1f, p5.height * 0.052f);

        popUpConfirmacionEliminar = new PopUp(p5, appColors,
                "¿Eliminar?",
                "Se eliminará el elemento de la base de datos.",
                p5.width * 0.30f, p5.height * 0.30f,
                p5.width * 0.5f, p5.height * 0.35f);

        // Creació del Botó
        bLoadImage = new Button(p5, appColors, "LOAD", p5.width * 0.38f, p5.height * 0.57f, p5.width * 0.2f, p5.height * 0.05f);
        bSaveImageToDB = new Button(p5, appColors, "SAVE", p5.width * 0.6f, p5.height * 0.57f, p5.width * 0.2f, p5.height * 0.05f);

        rBFilter = new RoundButton(p5, appColors, imgFilter, p5.width * 0.85f, p5.height * 0.15f, p5.width * 0.020f);
        rBHeart = new RoundButton(p5, appColors, imgHeart, p5.width * 0.90f, p5.height * 0.15f, p5.width * 0.020f);
        rBPlus = new RoundButton(p5, appColors, imgPlus, p5.width * 0.95f, p5.height * 0.15f, p5.width * 0.020f);
        rBDelete = new RoundButton(p5, appColors, imgDelete, p5.width * 0.7f, p5.height * 0.13f, p5.width * 0.020f);
        rBHeartAgregar = new RoundButton(p5, appColors, imgHeart, p5.width * 0.65f, p5.height * 0.13f, p5.width * 0.020f);

        float xB = p5.width * 0.25f;
        float yB = p5.height * 0.15f;
        float wB = 120;
        float hB = 40;

        bCatVinilos = new Button(p5, appColors, "Vinilos", xB, yB, wB, hB);
        bCatCDs = new Button(p5, appColors, "CDs", xB + wB + 10, yB, wB, hB);
        bCatConciertos = new Button(p5, appColors, "Conciertos", xB + (wB + 10) * 2, yB, wB, hB);
        // Al empezar, Vinilos está seleccionado, así que lo "desactivamos" visualmente

        bDetalle = new Button(p5, appColors, "Ver detalle", p5.width * 0.55f, p5.height * 0.92f,
                p5.width * 0.10f, p5.width * 0.04f);
        bGuardarNotasUsuario = new Button(p5, appColors, "Guardar notas", p5.width * 0.83f, p5.height*0.55f,
                p5.width * 0.12f, p5.height * 0.052f);
        bCambiarFotoPerfil = new Button(p5, appColors, "Cambiar foto",
                p5.width * 0.27f, p5.height * 0.42f, p5.width * 0.16f, p5.height * 0.04f);

        bCrearCuenta  = new Button(p5, appColors, "Crear cuenta",  p5.width*0.54f, p5.height*0.73f, p5.width*0.1f, p5.height*0.052f);
        bIrRegistro   = new Button(p5, appColors, "Crear cuenta",  p5.width*0.525f, p5.height*0.80f, p5.width*0.1f, p5.height*0.052f);
        bVolverInicio = new Button(p5, appColors, "Volver",        p5.width*0.36f, p5.height*0.80f, p5.width*0.12f, p5.height*0.052f);

        actualizarEstadoBotones();
    }

    public void setTextFields(PApplet p5) {
        tFInicioSesion1 = new TextField(p5, appColors, 40, p5.width * 0.36f, p5.height * 0.50f, p5.width * 0.28f, p5.height * 0.05f);
        tFInicioSesion2 = new TextField(p5, appColors, 40, p5.width * 0.36f, p5.height * 0.60f, p5.width * 0.28f, p5.height * 0.05f);
        tFRegistroNombre = new TextField(p5, appColors, 40, p5.width*0.36f, p5.height*0.42f, p5.width*0.28f, p5.height*0.05f);
        tFRegistroCorreo = new TextField(p5, appColors, 40, p5.width*0.36f, p5.height*0.52f, p5.width*0.28f, p5.height*0.05f);
        tFRegistroPass   = new TextField(p5, appColors, 40, p5.width*0.36f, p5.height*0.62f, p5.width*0.28f, p5.height*0.05f);
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

        tANotasUsuario = new TextArea(p5, appColors, p5.width * 0.25f, p5.height * 0.60f, p5.width * 0.70f, p5.height * 0.35f, 79, 10);
        int nc = (pantallaActual == gui.GUI.PANTALLA.CONCIERTOS)? 75 : 32;
        tANotasAgregar = new TextArea(p5, appColors, p5.width * 0.05f, p5.height * 0.82f, p5.width * 0.62f, p5.height * 0.18f, nc, 4);
    }

    public void setMedia(PApplet p5) {
        iconaDefaultUser = p5.loadImage("data/iconEmptyUser.png");   // si fuera imagen transparente svg (loadShape: vectorial)
        icona2 = p5.loadImage("data/iconFullUser.png");
        logo = p5.loadImage("data/logo.png");
        imgFilter = p5.loadImage("data/imgFilter.png");
        imgHeart = p5.loadImage("data/imgHeart.png");
        imgPlus = p5.loadImage("data/imgPlus.png");
        imgDelete = p5.loadImage("data/imgDelete.png");

        imgDisc1 = p5.loadImage("data/musicPredetBlackBG.png");
        imgDefaultViniloCD = p5.loadImage("data/musicPredetWhiteBG.png");
        imgDefaultConcierto = p5.loadImage("data/imgDefaultConcierto.png");

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
        pcConcert = new PagedCard2D(p5, appColors, 3, 2, Card.tipoCard.CONCERT);
        // (Mismas dimensiones, para mantener simetría)
        pcConcert.setDimensions(p5.width * 0.24f, p5.height * 0.25f, p5.width * 0.73f, p5.height * 0.65f);

    }

    public void setEstadisticas(PApplet p5) {
        // 1. Inicializar la tabla (1 fila, 1 columna para que el gráfico sea grande)
        pcStats = new PagedCard2D(p5, appColors, 1, 1, Card.tipoCard.ESTADIST);
        pcStats.setDimensions(p5.width * 0.24f, p5.height * 0.25f, p5.width * 0.73f, p5.height * 0.65f);

        // 2. Crear los 3 objetos de gráficos
        misGraficos = new StatsCard[3];
        misGraficos[0] = new SectorDiagram("Calificaciones", pcStats.x, pcStats.y, pcStats.w, pcStats.h);
        misGraficos[1] = new LinesDiagram("Años", pcStats.x, pcStats.y, pcStats.w, pcStats.h);
        misGraficos[2] = new BarsDiagram("Géneros", pcStats.x, pcStats.y, pcStats.w, pcStats.h);

        // 3. Pasar el array de objetos directamente
        pcStats.setCards(misGraficos);
    }

    public void setRadioButtonsYCheckboxesMusica(PApplet p5) {
        // Construcción checkboxstarlist
        cbl = new CheckBoxStarList(p5, 5, imgs, p5.width * 0.05f, p5.height * 0.72f, p5.width * 0.05f, p5.height * 0.065f);
        cbl.setCheckBoxStars(0);
        // --- GÉNERO (CheckBoxes, selección múltiple) ---
        cbGenero = new CheckBox[nombresGenero.length];
        float xCB = p5.width * 0.38f;
        float yCB = p5.height * 0.74f;
        for (int i = 0; i < nombresGenero.length; i++) {
            cbGenero[i] = new CheckBox(p5, (int) (xCB + i * p5.width * 0.10f), (int) yCB, 20);
            // el texto lo dibujamos en display manualmente
        }

        // --- UBICACIÓN (RadioButtons, selección única) ---
        rbgUbicacion = new RadioButtonGroup(nombresUbicacion.length);
        RadioButton[] rbsUbic = new RadioButton[nombresUbicacion.length];
        float xRBU = p5.width * 0.385f;
        float yRBU = p5.height * 0.85f;
        for (int i = 0; i < nombresUbicacion.length; i++) {
            rbsUbic[i] = new RadioButton(p5, (int) (xRBU + i * p5.width * 0.12f), (int) yRBU, 10);
            rbsUbic[i].setText(nombresUbicacion[i]);
        }
        rbgUbicacion.setRadioButtons(rbsUbic[0], rbsUbic[1], rbsUbic[2], rbsUbic[3], rbsUbic[4]);

        // --- ORIGEN (RadioButtons, selección única) ---
        rbgOrigen = new RadioButtonGroup(nombresOrigen.length);
        RadioButton[] rbsOrig = new RadioButton[nombresOrigen.length];
        float xRBO = p5.width * 0.385f;
        float yRBO = p5.height * 0.945f;
        for (int i = 0; i < nombresOrigen.length; i++) {
            rbsOrig[i] = new RadioButton(p5, (int) (xRBO + i * p5.width * 0.12f), (int) yRBO, 10);
            rbsOrig[i].setText(nombresOrigen[i]);
        }
        rbgOrigen.setRadioButtons(rbsOrig[0], rbsOrig[1], rbsOrig[2], rbsOrig[3]);
    }

    // PANTALLAS DE LA GUI
    public void displayPantallaPLogo(PApplet p5) {
        p5.push();
        p5.background(black);
        p5.imageMode(p5.CENTER);
        p5.image(logo, p5.width * 0.5f, p5.height * 0.4f, p5.width * 0.28f, p5.height * 0.43f);

        p5.textFont(appFonts.getSecondFont());
        p5.fill(narFuerte);
        p5.textSize(medidaTitulo);
        p5.textAlign(p5.CENTER);
        p5.text("THE SMASHING RECORDS", p5.width*0.5f, p5.height*0.70f);

        p5.fill(narFlojo);
        p5.textFont(appFonts.getThirdFont());
        p5.textSize(medidaIntermedia);
        p5.text("Tu colección de música y eventos", p5.width*0.5f, p5.height*0.78f);

        // Transición automática
        if (tiempoPLogo > 0) {
            tiempoPLogo--;
        } else {
            pantallaActual = PANTALLA.INICIO;
        }
        p5.pop();
    }

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
        p5.text("Nombre usuario", p5.width * 0.36f, p5.height * 0.49f);
        p5.text("Contraseña", p5.width * 0.36f, p5.height * 0.59f);
        b6.display(p5); // Iniciar sesión
        p5.textFont(appFonts.getForthFont()); p5.textSize(medidaParrafo);
        tFInicioSesion1.display(p5);
        tFInicioSesion2.display(p5);

        p5.textFont(appFonts.getThirdFont());
        p5.fill(white);
        p5.textSize(medidaIntermedia);
        p5.textAlign(p5.RIGHT);
        p5.text("¿No tienes cuenta?", p5.width*0.515f, p5.height*0.835f);
        bIrRegistro.display(p5);

        p5.pop();
    }

    public void displayPantallaRegistro(PApplet p5) {
        p5.push();
        p5.background(black);
        p5.rectMode(p5.CENTER);
        p5.fill(black);
        p5.strokeWeight(2);
        p5.stroke(white);
        p5.rect(p5.width*0.5f, p5.height*0.5f, p5.width*0.33f, p5.height*0.80f);

        displayLogoMayor(p5);

        p5.textFont(appFonts.getSecondFont());
        p5.fill(narFuerte);
        p5.textSize(medidaTitulo - 14);
        p5.textAlign(p5.CENTER);
        p5.text("CREAR CUENTA", p5.width*0.5f, p5.height*0.16f);

        p5.pop();
        p5.push();
        p5.textFont(appFonts.getThirdFont());
        p5.fill(narFuerte);
        p5.textSize(medidaIntermedia);
        p5.text("Nombre de usuario", p5.width*0.36f, p5.height*0.41f);
        p5.text("Correo electrónico", p5.width*0.36f, p5.height*0.51f);
        p5.text("Contraseña", p5.width*0.36f, p5.height*0.61f);

        bCrearCuenta.display(p5);
        bVolverInicio.display(p5);

        p5.textFont(appFonts.getForthFont());
        tFRegistroNombre.display(p5);
        tFRegistroCorreo.display(p5);
        tFRegistroPass.display(p5);

        // Mensaje de error o éxito
        if (tiempoMensajeRegistro > 0) {
            p5.fill(narFuerte);
            p5.textSize(18);
            p5.textAlign(p5.CENTER);
            p5.fill(pink); p5.textAlign(p5.CENTER, p5.CENTER); p5.textFont(appFonts.getThirdFont()); p5.textSize(medidaParrafo+4);
            p5.text(mensajeRegistro, p5.width*0.5f, p5.height*0.70f);
            tiempoMensajeRegistro--;
        }
        p5.pop();
    }

    public void displayPantallaUsuario(PApplet p5) {
        p5.push();
        p5.background(black);
        displaySidebar(p5);

        // Círculo de fondo / borde
        p5.noFill();
        p5.stroke(narFuerte);
        p5.strokeWeight(3);
        p5.circle(p5.width * 0.35f, p5.height * 0.25f, p5.width * 0.20f);

        // Imagen de perfil o icono por defecto
        if (imgPerfil != null) {
            displayImagenCircular(p5, imgPerfil, p5.width * 0.35f, p5.height * 0.25f, p5.width * 0.20f);
        }

        // Botón para cargar imagen de perfil
        bCambiarFotoPerfil.display(p5);

        p5.textAlign(p5.CENTER);
        p5.textSize(medidaSubtitulo);
        p5.textFont(appFonts.getThirdFont());
        p5.fill(white);

        //db.getNomUsuario
        p5.textAlign(p5.LEFT); p5.textFont(appFonts.getForthFont()); p5.textSize(medidaSubtitulo);
        p5.text("Nombre usuario: " + datosUsuarioActual[0], p5.width * 0.5f, p5.height * 0.25f);
        p5.text("Correo: " + datosUsuarioActual[1], p5.width * 0.5f, p5.height * 0.3f);
        p5.textFont(appFonts.getThirdFont());
        b7.display(p5); // Cerrar sesión
        p5.textAlign(p5.CORNER);
        p5.text("Notas", p5.width * 0.25f, p5.height * 0.59f);

        p5.push();
        p5.textFont(appFonts.getThirdFont());
        bGuardarNotasUsuario.display(p5);
        if (tiempoNotasGuardadas > 0) {
            p5.fill(narFuerte);
            p5.textFont(appFonts.getFontAt(2));
            p5.textSize(medidaIntermedia);
            p5.textAlign(p5.RIGHT);
            p5.text("Notas guardadas", p5.width * 0.82f, p5.height * 0.585f);
            tiempoNotasGuardadas--;
        }
        p5.pop();

        p5.textFont(appFonts.getForthFont()); p5.textSize(medidaParrafo);
        tANotasUsuario.display(p5);
        p5.pop();

    }

    public void displayPantallaVinilos(PApplet p5) {
        p5.push();
        p5.background(black);
        displaySidebar(p5);
        displayBuscadorYFiltros(p5);
        displayDisposicionMusica(p5);

        if (pcMusica.selectedCard != -1) bDetalle.display(p5); // solo si hay card seleccionada

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

        if (pcMusica.selectedCard != -1) bDetalle.display(p5); // solo si hay card seleccionada

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

        if (pcConcert.selectedCard != -1) bDetalle.display(p5); // solo si hay card seleccionada

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

        p5.textFont(appFonts.getFontAt(3));
        p5.fill(white);
        p5.textSize(medidaIntermedia);
        p5.textAlign(p5.LEFT);
        String labelTotal = (categoriaActual == CatEstadistica.VINILOS)   ? "Vinilos: "
                          : (categoriaActual == CatEstadistica.CDS)       ? "CDs: "
                          : "Conciertos: ";
        p5.text("TOTAL " + labelTotal + totalActual, bCatConciertos.x + bCatConciertos.w + 30, bCatVinilos.y + bCatVinilos.h * 0.7f);

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
            p5.image(imgDefaultViniloCD, p5.width * 0.05f, p5.height * 0.24f, p5.width * 0.3f, p5.width * 0.3f);
            p5.textSize(24);
            p5.textAlign(p5.LEFT);
            p5.text("Sin imatge", p5.width * 0.38f, p5.height * 0.67f);
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
        String txtTitulo = modoDetalle?
                  (pantallaAnterior == PANTALLA.VINILOS ? "VINILO" : "CD")
                : (pantallaAnterior == PANTALLA.VINILOS ? "AGREGAR VINILO" : "AGREGAR CD");
        p5.text(txtTitulo, p5.width * 0.25f, p5.height * 0.10f);

        // Género
        float xCB = p5.width * 0.38f;
        float yCB = p5.height * 0.74f;
        for (int i = 0; i < cbGenero.length; i++) {
            cbGenero[i].x = (int)(xCB + i * p5.width * 0.10f);
            cbGenero[i].y = (int)yCB;
        }

        p5.textFont(appFonts.getFontAt(3));
        p5.fill(white);
        p5.textSize(medidaIntermedia);
        p5.textAlign(p5.LEFT);
        p5.text("Género", p5.width * 0.38f, p5.height * 0.72f);
        for (int i = 0; i < cbGenero.length; i++) {
            cbGenero[i].display(p5);
            p5.fill(white);
            p5.textSize(medidaParrafo);
            p5.text(nombresGenero[i], p5.width * 0.38f + i * p5.width * 0.10f + 25, p5.height * 0.76f);
        }

        // Ubicación
        p5.textSize(medidaIntermedia);
        p5.text("Ubicación", p5.width * 0.38f, p5.height * 0.82f);
        rbgUbicacion.display(p5);
        // Origen
        p5.textSize(medidaIntermedia);
        p5.text("Origen", p5.width * 0.38f, p5.height * 0.91f);
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

        tANotasAgregar.setNumCols(34);
        tANotasAgregar.x = p5.width * 0.05f;
        tANotasAgregar.y = p5.height * 0.82f;
        tANotasAgregar.w = p5.width * 0.30f;
        tANotasAgregar.h = p5.height * 0.17f;
        p5.fill(white); p5.textSize(medidaIntermedia); p5.textAlign(p5.LEFT);
        p5.text("Notas", p5.width*0.05f, p5.height * 0.815f);
        tANotasAgregar.display(p5);

        if (tiempoMensajeGuardado > 0) {
            p5.fill(imagenGuardadaOk ? narFuerte : pink); // naranja si ok, otro color si no imagen
            p5.textFont(appFonts.getFontAt(3));
            p5.textSize(medidaParrafo);
            p5.textAlign(p5.LEFT);
            p5.text(imagenGuardadaOk ? "Imagen guardada" : "No hay imagen seleccionada", p5.width * 0.38f, p5.height * 0.555f);
            tiempoMensajeGuardado--;
        }

        popUpConfirmacionEliminar.display(p5);

        p5.pop();
    }

    public void displayPantallaAgregarConcert(PApplet p5) {
        p5.push();
        p5.background(black);
        displayLogo(p5);
        bCancelar.display(p5);
        bOk.display(p5);
        // bEliminarMultimedia.display(p5);

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
            p5.image(imgDefaultConcierto, p5.width * 0.05f, p5.height * 0.24f, p5.width * 0.62f, p5.width * 0.3f);
            p5.fill(white); p5.textSize(24); p5.textAlign(p5.LEFT);
            p5.text("Sin imagen", p5.width * 0.38f, p5.height * 0.77f);
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
        String txtTitulo = modoDetalle ? "CONCIERTO" : "AGREGAR CONCIERTO";
        p5.text(txtTitulo, p5.width * 0.25f, p5.height * 0.10f);

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
        float yCB = p5.height * 0.84f;
        float espaciadoX = p5.width * 0.10f;
        float espaciadoY = p5.height * 0.06f;
        int porFila = 3; // 3 en la primera fila, 2 en la segunda
        p5.fill(white); p5.textSize(medidaIntermedia); p5.textAlign(p5.LEFT);
        p5.text("Género *", xCB, p5.height * 0.825f);
        for (int i = 0; i < cbGenero.length; i++) {
            int fila = i / porFila;   // 0 para i=0,1,2 — 1 para i=3,4
            int col  = i % porFila;   // 0,1,2 — 0,1

            float xActual = xCB + col  * espaciadoX;
            float yActual = yCB + fila * espaciadoY;

            cbGenero[i].x = (int) xActual;
            cbGenero[i].y = (int) yActual;

            cbGenero[i].display(p5);
            p5.fill(white);
            p5.textSize(18);
            p5.text(nombresGenero[i], xActual + 25, yActual + 15);
        }

        tANotasAgregar.setNumCols(71);
        tANotasAgregar.x = p5.width * 0.05f;
        tANotasAgregar.y = p5.height * 0.83f;
        tANotasAgregar.w = p5.width * 0.62f;
        tANotasAgregar.h = p5.height * 0.12f;
        p5.fill(white); p5.textSize(medidaIntermedia); p5.textAlign(p5.LEFT);
        p5.text("Notas", p5.width*0.05f, p5.height * 0.825f);
        tANotasAgregar.display(p5);

        if (tiempoMensajeGuardado > 0) {
            p5.fill(imagenGuardadaOk ? narFuerte : pink); // naranja si ok, otro color si no imagen
            p5.textFont(appFonts.getFontAt(3));
            p5.textSize(medidaParrafo);
            p5.textAlign(p5.CENTER);
            p5.text(imagenGuardadaOk ? "Imagen guardada" : "No hay imagen seleccionada", p5.width * 0.83f, p5.height * 0.655f);
            tiempoMensajeGuardado--;
        }
        popUpConfirmacionEliminar.display(p5);
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
        p5.textFont(appFonts.getForthFont());
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
        if (tiempoMensajeOrden > 0) {
            p5.fill(narFuerte);
            p5.textFont(appFonts.getFontAt(2));
            p5.textSize(medidaIntermedia);
            p5.textAlign(p5.LEFT);
            p5.text("Orden: " + getTextoOrden(), p5.width * 0.24f, p5.height * 0.23f);
            tiempoMensajeOrden--;
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
        String tipoQuery;
        if      (categoriaActual == CatEstadistica.VINILOS)   tipoQuery = "V";
        else if (categoriaActual == CatEstadistica.CDS)        tipoQuery = "C";
        else                                                   tipoQuery = "CONCIERTO";

        // --- RATINGS ---
        int[] ratings = db.getRatingStats(usuarioActual, tipoQuery);
        float[] dataRatings = new float[6];
        String[] tagsRatings = {"0*","1*","2*","3*","4*","5*"};
        for (int i = 0; i < 6; i++) dataRatings[i] = ratings[i];

        // --- AÑOS ---
        String[][] aniosRaw = db.getAniosStats(usuarioActual, tipoQuery);
        float[] dataAnios = new float[aniosRaw.length];
        String[] tagsAnios = new String[aniosRaw.length];
        for (int i = 0; i < aniosRaw.length; i++) {
            tagsAnios[i]  = aniosRaw[i][0];
            dataAnios[i]  = Float.parseFloat(aniosRaw[i][1]);
        }

        // --- GÉNEROS ---
        int[] generos = db.getGeneroStats(usuarioActual, tipoQuery, nombresGenero);
        float[] dataGeneros = new float[nombresGenero.length];
        for (int i = 0; i < nombresGenero.length; i++) dataGeneros[i] = generos[i];

        // Orden colores
        int[] coloresCalificacion = {
                black,     // 0 estrellas
                gris,      // 1 estrella
                pink,      // 2 estrellas
                yellow,    // 3 estrellas
                narFlojo,  // 4 estrellas
                narFuerte, // 5 estrellas
        };
        int[] coloresGeneros = {
                gris,
                yellow,
                pink,
                narFlojo,
                narFuerte,
        };
        // Inyectar en gráficos
        ((SectorDiagram) misGraficos[0]).setValues(dataRatings);
        ((SectorDiagram) misGraficos[0]).setTexts(tagsRatings);
        ((SectorDiagram) misGraficos[0]).setColors(coloresCalificacion);

        ((LinesDiagram) misGraficos[1]).setValues(dataAnios.length > 0 ? dataAnios : new float[]{0});
        ((LinesDiagram) misGraficos[1]).setTexts(tagsAnios.length  > 0 ? tagsAnios : new String[]{"Sin datos"});
        ((LinesDiagram) misGraficos[1]).setColors(narFuerte);

        ((BarsDiagram) misGraficos[2]).setValues(dataGeneros);
        ((BarsDiagram) misGraficos[2]).setTexts(nombresGenero);
        ((BarsDiagram) misGraficos[2]).setColors(coloresGeneros);

        // Actualizar total
        String tipoTotal = (categoriaActual == CatEstadistica.VINILOS) ? "V"
                        : (categoriaActual == CatEstadistica.CDS)      ? "C"
                        : "CONCIERTO";
        totalActual = db.getTotalUsuario(usuarioActual, tipoTotal);
    }
    /*public void actualizarDatosGraficos(PApplet p5) {
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
    }*/

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

        // Pantallas detalle
        modoDetalle = false;
        idSeleccionado = -1;
    }
    public void recargarCards(PApplet p5) {
        String[] columnas = {"Título", "Título", "Artista", "Artista"};
        String[] direcciones = {"ASC", "DESC", "ASC", "DESC"};
        String col = columnas[ordenActual];
        String dir = direcciones[ordenActual];

        String[][] data;
        if(pantallaActual == PANTALLA.VINILOS) {
            data = textoBusqueda.isEmpty()
                    ? db.getVinilosUsuario(usuarioActual, mostrarListaDeseos, col, dir)
                    : db.buscarMusica(usuarioActual, mostrarListaDeseos, "V", textoBusqueda, col, dir);
        } else if(pantallaActual == PANTALLA.CDS) {
            data = textoBusqueda.isEmpty()
                    ? db.getCDsUsuario(usuarioActual, mostrarListaDeseos, col, dir)
                    : db.buscarMusica(usuarioActual, mostrarListaDeseos, "C", textoBusqueda, col, dir);
        } else{
            data = textoBusqueda.isEmpty()
                    ? db.getConciertosUsuario(usuarioActual, mostrarListaDeseos, col, dir)
                    : db.buscarConciertos(usuarioActual, mostrarListaDeseos, textoBusqueda, col, dir);
        }

        PagedCard2D pc = (pantallaActual == PANTALLA.CONCIERTOS) ? pcConcert : pcMusica;
        pc.setData(data);
        pc.setCards();
        if (data.length > 0) {   // solo reconstruir cards si hay datos
            pc.setCards();
            PImage imgDefault = (pantallaActual == PANTALLA.CONCIERTOS) ? imgDefaultConcierto : imgDefaultViniloCD;
            for (int i = 0; i < pc.cards.length; i++) {
                String nombreImg = data[i][3];
                if (nombreImg != null && !nombreImg.isEmpty()) {
                    PImage img = p5.loadImage(rutaCarpeta + nombreImg);
                    pc.cards[i].setImage(img != null ? img : imgDefault);
                } else {
                    pc.cards[i].setImage(imgDefault);
                }
            }
        }
    }

    public void cargarDatosDetalle(PApplet p5, String[] datos, boolean esConcierto) {
        if (datos == null) return;
        if (!esConcierto) {
            tFMusica[0].setText(datos[0]); // Título
            tFMusica[1].setText(datos[1]); // Artista
            tFMusica[2].setText(datos[2]); // Fecha
            tFMusica[3].setText(datos[3]); // Edición

            // Género — marcar checkboxes
            for (int i = 0; i < nombresGenero.length; i++) {
                cbGenero[i].setChecked(datos[5].contains(nombresGenero[i]));
            }

            // Ubicación — seleccionar el radiobutton correspondiente
            for (int i = 0; i < nombresUbicacion.length; i++) {
                if (nombresUbicacion[i].equals(datos[4])){ rbgUbicacion.setSelected(i); }
            }

            // Origen
            for (int i = 0; i < nombresOrigen.length; i++) {
                if (nombresOrigen[i].equals(datos[6])){ rbgOrigen.setSelected(i); }
            }
           // System.out.println("Ubicación BD: '" + datos[4] + "' | Origen BD: '" + datos[6] + "'");
            tANotasAgregar.setText(datos[7]);
            cbl.setCheckBoxStars(datos[8].equals("") ? 0 : Integer.parseInt(datos[8]));
            esListaDeseos = datos[9].equals("S");
            // Imagen
            String nombreImg = datos[10];
            if (!nombreImg.isEmpty()) {
                imgElegida = p5.loadImage(rutaCarpeta + nombreImg);
                titol = nombreImg;
            }
        } else {
            tFConcierto[0].setText(datos[0]); // Título
            tFConcierto[1].setText(datos[1]); // Artista
            tFConcierto[2].setText(datos[2]); // Fecha
            tFConcierto[3].setText(datos[3]); // Lugar
            for (int i = 0; i < nombresGenero.length; i++) {
                cbGenero[i].setChecked(datos[4].contains(nombresGenero[i]));
            }
            tANotasAgregar.setText(datos[5]);
            cbl.setCheckBoxStars(datos[6].equals("") ? 0 : Integer.parseInt(datos[6]));
            esListaDeseos = datos[7].equals("S");
            String nombreImg = datos[8];
            if (!nombreImg.isEmpty()) {
                imgElegida = p5.loadImage(rutaCarpeta + nombreImg);
                titol = nombreImg;
            }
        }
    }

    // Filtro
    public void ciclarOrden() {
        ordenActual = (ordenActual + 1) % 4;
        tiempoMensajeOrden = 180; // 3 segundos a 60fps
    }
    public String getTextoOrden() {
        switch(ordenActual) {
            case 0: return "Título A -> Z";
            case 1: return "Título Z -> A";
            case 2: return "Artista A -> Z";
            case 3: return "Artista Z -> A";
            default: return "";
        }
    }

    public void displayImagenCircular(PApplet p5, PImage img, float cx, float cy, float diametro) {
        // Creamos una máscara circular del mismo tamaño que la imagen redimensionada
        int d = (int) diametro;
        PImage mascara = p5.createImage(d, d, p5.ARGB);
        mascara.loadPixels();
        float cx2 = d / 2f;
        float cy2 = d / 2f;
        float r2 = (d / 2f) * (d / 2f);
        for (int i = 0; i < d; i++) {
            for (int j = 0; j < d; j++) {
                float dx = i - cx2;
                float dy = j - cy2;
                mascara.pixels[j * d + i] = (dx*dx + dy*dy <= r2)
                        ? p5.color(255) : p5.color(0);
            }
        }
        mascara.updatePixels();

        // Redimensionar imagen y aplicar máscara
        PImage copia = img.copy();
        copia.resize(d, d);
        copia.mask(mascara);

        p5.imageMode(p5.CENTER);
        p5.image(copia, cx, cy, d, d);
        p5.imageMode(p5.CORNER); // restaurar modo por defecto
    }
}