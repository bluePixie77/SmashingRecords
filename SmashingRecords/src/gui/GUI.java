package gui;

import gui.smashRecColors.Colors;
import gui.smashRecFonts.Fonts;
import gui.smashRecPantallas.*;
import processing.core.PApplet;
import processing.core.PImage; // no da error pero no se dibuja el paged table de las estadísticas

import static gui.smashRecFonts.Sizes.*;

public class GUI {

    // Enumerado para las pantallas
    public enum PANTALLA {INICIO, USUARIO, VINILOS, CDS, CONCIERTOS, ESTADISTICAS, AGREGAR, AGREGAR_CONCERT}
    public enum CatEstadistica { VINILOS, CDS, CONCIERTOS }
    public CatEstadistica categoriaActual = CatEstadistica.VINILOS;

    // Pantalla actual
    public PANTALLA pantallaActual;
    public PANTALLA pantallaAnterior;
    public boolean enablePantalla;

    // Botones
    public Button b1, b2, b3, b4, b5, b6, b7, bNext, bPrev, bCancelar, bOk;
    public Button bCatVinilos, bCatCDs, bCatConciertos;

    // Colores
    public Colors appColors;
    int black, gris, white, narFuerte, narFlojo, yellow, pink;

    // Texto
    Fonts appFonts;
    public TextField tFInicioSesion1, tFInicioSesion2, tFBuscador;
    public TextField[] tFAgregar;
    public TextArea tANotasUsuario, tANotasAgregar;

    // Imatges de la GUI
    public RoundButton rBFilter, rBHeart, rBPlus;
    RadioButton radioB1, radioB2, radioB3;
    PImage icona1, icona2, logo, imgFilter, imgHeart, imgPlus, imgDisc1, imgDisc2;
    String[] imgs = {"starON.png", "starOFF.png"};

    // Paged Cards
    public PagedCard2D pcMusica;   // Vinilos y CDs
    public PagedCard2D pcConcert;  // Conciertos
    public PagedCard2D pcStats;

    public StatsCard[] misGraficos;
    public int[] paletaGraficos;

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
    String[][] infoConcert = {
            {"Concert 0", "Autor 0", "Data 0", "Lloc 0", "Descripció 0"},
            {"Concert 1", "Autor 1", "Data 1", "Lloc 1", "Descripció 1"},
            {"Concert 2", "Autor 2", "Data 2", "Lloc 2", "Descripció 2"},
            {"Concert 3", "Autor 3", "Data 3", "Lloc 1", "Descripció 3"},
            {"Concert 4", "Autor 4", "Data 4", "Lloc 1", "Descripció 4"},
            {"Concert 5", "Autor 5", "Data 5", "Lloc 2", "Descripció 5"},
            {"Concert 6", "Autor 6", "Data 6", "Lloc 2", "Descripció 6"},
            {"Concert 7", "Autor 7", "Data 7", "Lloc 1", "Descripció 7"},
            {"Concert 8", "Autor 8", "Data 8", "Lloc 8", "Descripció 8"},
            {"Concert 9", "Autor 9", "Data 9", "Lloc 9", "Descripció 9"},
            {"Concert 10", "Autor 10", "Data 10", "Lloc 10", "Descripció 10"},
    };

    // Otros
    public CheckBoxStarList cbl;

    // Constructor de GUI
    public GUI(PApplet p5) {
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
        setOthers(p5);
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
        bOk       = new Button(p5, appColors, "OK", p5.width * 0.85f, p5.height * 0.1f, p5.width * 0.1f, p5.height * 0.052f);


        rBFilter = new RoundButton(p5, appColors, imgFilter, p5.width * 0.85f, p5.height * 0.15f, p5.width * 0.020f);
        rBHeart = new RoundButton(p5, appColors, imgHeart, p5.width * 0.90f, p5.height * 0.15f, p5.width * 0.020f);
        rBPlus = new RoundButton(p5, appColors, imgPlus, p5.width * 0.95f, p5.height * 0.15f, p5.width * 0.020f);

        float xB = p5.width * 0.25f;
        float yB = p5.height * 0.15f;
        float wB = 120;
        float hB = 40;

        bCatVinilos = new Button(p5, appColors, "Vinilos", xB, yB, wB, hB);
        bCatCDs = new Button(p5, appColors, "CDs", xB + wB + 10, yB, wB, hB);
        bCatConciertos = new Button(p5, appColors, "Conciertos", xB + (wB + 10)*2, yB, wB, hB);
        // Al empezar, Vinilos está seleccionado, así que lo "desactivamos" visualmente
        actualizarEstadoBotones();
    }

    public void setTextFields(PApplet p5) {
        tFInicioSesion1 = new TextField(p5, appColors, 40, p5.width * 0.36f, p5.height * 0.50f, p5.width * 0.28f, p5.height * 0.05f);
        tFInicioSesion2 = new TextField(p5, appColors, 40, p5.width * 0.36f, p5.height * 0.60f, p5.width * 0.28f, p5.height * 0.05f);
        // tFNotasUsuario = new TextField(p5, appColors, 40, p5.width * 0.25f, p5.height * 0.60f, p5.width * 0.70f, p5.height * 0.35f);
        tFBuscador = new TextField(p5, appColors, 60, p5.width * 0.24f, p5.height * 0.1f, p5.width * 0.56f, p5.height * 0.10f);

        tFAgregar = new TextField[4];

        float startX = p5.width * 0.45f;      // Alineado a la derecha de la imagen
        float startY = p5.height * 0.25f;     // Debajo de los botones OK/Cancelar
        float fieldW = p5.width * 0.50f;      // Ancho del campo
        float fieldH = p5.height * 0.05f;     // Alto del campo
        float spacing = p5.height * 0.10f;    // Espacio vertical entre campos

        for (int i = 0; i < tFAgregar.length; i++) {
            tFAgregar[i] = new TextField(p5, appColors, 40, startX, startY + (i * spacing), fieldW, fieldH);
        }

        tANotasUsuario = new TextArea(p5, appColors, p5.width * 0.25f, p5.height * 0.60f, p5.width * 0.70f, p5.height * 0.35f, 40, 10);
        tANotasAgregar = new TextArea(p5, appColors, p5.width * 0.05f, p5.height * 0.8f, p5.width * 0.3f, p5.height * 0.20f, 40, 4);
    }

    public void setMedia(PApplet p5) {
        icona1 = p5.loadImage("data/iconEmptyUser.png");   // si fuera imagen transparente svg (loadShape: vectorial)
        icona2 = p5.loadImage("data/iconFullUser.png");
        logo = p5.loadImage("data/logo.png");
        imgFilter = p5.loadImage("data/imgFilter.png");
        imgHeart = p5.loadImage("data/imgHeart.png");
        imgPlus = p5.loadImage("data/imgPlus.png");

        imgDisc1 = p5.loadImage("data/musicPredetBlackBG.png");
        imgDisc2 = p5.loadImage("data/musicPredetWhiteBG.png");
    }

    public void setColors(PApplet p5) {
        black = appColors.getFourthColor();
        gris = appColors.getFifthColor();
        white = appColors.getThirdColor();
        narFuerte = appColors.getFirstColor();
        narFlojo = appColors.getSecondColor();
        yellow = appColors.getSixthColor();
        pink = appColors.getSeventhColor();

        paletaGraficos = new int[] {
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
        // MÚSICA (Vinilos y CDs)
        pcMusica = new PagedCard2D(p5, appColors, 2, 4, Card.tipoCard.ALBUM);
        // X comienza en 24% para dejar espacio a la Sidebar (20%)
        pcMusica.setDimensions(p5.width * 0.24f, p5.height * 0.25f, p5.width * 0.73f, p5.height * 0.65f);
        pcMusica.setData(infoAlbum);
        pcMusica.setCards();
        pcMusica.setImages(imgDisc1, imgDisc2);

        // CONCIERTOS
        pcConcert = new PagedCard2D(p5, appColors, 3, 2, Card.tipoCard.CONCERT);
        // (Mismas dimensiones, para mantener simetría)
        pcConcert.setDimensions(p5.width * 0.24f, p5.height * 0.25f, p5.width * 0.73f, p5.height * 0.65f);
        pcConcert.setData(infoConcert); // Antes decía pcMusica
        pcConcert.setCards(); // Antes decía pcMusica
        pcConcert.setImages(imgDisc1, imgDisc2);
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

        // 3. LLAMADA CLAVE: Pasar el array de objetos directamente
        pcStats.setCards(misGraficos);

        // 4. Cargar datos iniciales
        actualizarDatosGraficos(p5);
    }
    public void setOthers(PApplet p5){
        // Construcción checkboxstarlist
        cbl = new CheckBoxStarList(p5, 5, imgs, p5.width*0.05f, p5.height*0.72f, p5.width*0.05f, p5.height*0.065f);
        cbl.setCheckBoxStars(3);
    }
    // PANTALLAS DE LA GUI
    public void displayPantallaInicioSesion(PApplet p5) {
        p5.push();
            p5.background(black);
            p5.rectMode(p5.CENTER);
            p5.textAlign(p5.CENTER);
            p5.fill(black); p5.strokeWeight(2); p5.stroke(white);
            p5.rect(p5.width * 0.5f, p5.height * 0.5f, p5.width * 0.33f, p5.height * 0.80f);

            displayLogoMayor(p5); // logo en dimensión grande

            p5.textFont(appFonts.getSecondFont()); p5.fill(narFuerte); p5.textSize(medidaTitulo-14);
            p5.text("THE SMASHING RECORDS", p5.width*0.5f, p5.height*0.16f);
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

        p5.circle(p5.width * 0.60f, p5.height * 0.20f, p5.width * 0.14f);
        p5.textAlign(p5.CENTER);
        p5.textSize(medidaSubtitulo); p5.textFont(appFonts.getThirdFont()); p5.fill(white);
        p5.text("Nombre: Jane Doe", p5.width * 0.60f, p5.height * 0.38f);
        p5.text("Correo: janeDoe@gmail.com", p5.width * 0.60f, p5.height * 0.45f);
        b7.display(p5); // Cerrar sesión
        p5.textAlign(p5.CORNER);
        p5.text("Notas", p5.width * 0.25f, p5.height * 0.59f);

        tANotasUsuario.display(p5);
        p5.pop();

    }

    public void displayPantallaVinilos(PApplet p5) {
        p5.push();
        p5.background(black);
        displaySidebar(p5);
        displayBuscadorYFiltros(p5);
        displayDisposicionMusica(p5);

        p5.textFont(appFonts.getFontAt(0)); p5.fill(narFuerte); p5.textSize(medidaTitulo);
        p5.text("VINILOS", p5.width * 0.25f, p5.height * 0.10f);
        p5.pop();
    }

    public void displayPantallaCDs(PApplet p5) {
        p5.push();
        p5.background(black);
        displaySidebar(p5);
        displayBuscadorYFiltros(p5);
        displayDisposicionMusica(p5);

        p5.textFont(appFonts.getFontAt(0)); p5.fill(narFuerte); p5.textSize(medidaTitulo);
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

        p5.textFont(appFonts.getFontAt(0)); p5.fill(narFuerte); p5.textSize(medidaTitulo);
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

        p5.textFont(appFonts.getFontAt(0)); p5.fill(narFuerte); p5.textSize(medidaTitulo);
        p5.text("Estadísticas", p5.width * 0.25f, p5.height * 0.10f);
        p5.pop();
    }

    public void displayPantallaAgregarMusica(PApplet p5) {
        p5.push();
        p5.background(black);
        displayLogo(p5);
        bCancelar.display(p5);
        bOk.display(p5);

        cbl.display(p5);
        p5.fill(white);
        p5.text(cbl.getNumSelected()+"/5", p5.width*0.32f, p5.height*0.77f);

        // Título dinámico según el origen
        p5.textFont(appFonts.getFontAt(0)); p5.fill(narFuerte); p5.textSize(medidaTitulo);
        String txtTitulo = ((pantallaAnterior == PANTALLA.VINILOS) ? "AGREGAR VINILO" : "AGREGAR CD");
        p5.text(txtTitulo, p5.width * 0.25f, p5.height * 0.10f);

        p5.line(p5.width*0.23f, p5.height*0.20f, p5.width*0.97f, p5.height*0.20f);
        p5.image(imgDisc2, p5.width*0.05f, p5.height*0.24f, p5.width*0.3f, p5.width*0.3f);

        displayTFAgregar(p5, "Artista/Banda", "Género");
        tANotasAgregar.display(p5);
        p5.pop();
    }
    public void displayPantallaAgregarConcert(PApplet p5) {
        p5.push();
        p5.background(black);
        displayLogo(p5);
        bCancelar.display(p5);
        bOk.display(p5);

        cbl.display(p5);
        p5.fill(white);
        p5.text(cbl.getNumSelected()+"/5", p5.width*0.32f, p5.height*0.77f);

        p5.textFont(appFonts.getFontAt(0)); p5.fill(narFuerte); p5.textSize(medidaTitulo);
        p5.text("AGREGAR CONCIERTO", p5.width * 0.25f, p5.height * 0.10f);

        p5.line(p5.width*0.23f, p5.height*0.20f, p5.width*0.97f, p5.height*0.20f);
        p5.image(imgDisc2, p5.width*0.05f, p5.height*0.24f, p5.width*0.65f, p5.width*0.3f);

        displayTFAgregar(p5, "Lugar / Recinto", "Ciudad");
        tANotasAgregar.display(p5);
        p5.pop();
    }

    // ZONAS DE LA GUI
    public void displayLogo(PApplet p5) {
        //p5.circle(p5.width * 0.10f, p5.height * 0.125f, p5.width * 0.12f);
        p5.push();
            p5.imageMode(p5.CENTER);
            p5.image(logo, p5.width * 0.10f, p5.height * 0.1f, p5.width * 0.1f, p5.height * 0.15f);
            p5.stroke(white);
            p5.line(p5.width*0.20f, 0, p5.width*0.20f, p5.height*0.20f);
            p5.line(0, p5.height*0.20f, p5.width*0.20f, p5.height*0.20f);
        p5.pop();
        /*p5.imageMode(p5.CENTER); p5.scale(p5.width*0.3f);
        p5.image(icona1, p5.width*0.10f, p5.height*0.1f);
        p5.image(icona2, p5.width*0.10f, p5.height*0.1f);*/
    }
    public void displayLogoMayor(PApplet p5) {
        p5.push();
            p5.imageMode(p5.CENTER);
            p5.image(logo, p5.width*0.5f, p5.height * 0.32f, p5.width * 0.17f, p5.height * 0.25f);
        p5.pop();
    }

    public void displaySidebar(PApplet p5) {
        p5.pop();
            p5.fill(black); p5.strokeWeight(2); p5.stroke(white);
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
        rBHeart.display(p5);

        p5.pop();
    }

    public void displayDisposicionMusica(PApplet p5) {
        p5.push();
        pcMusica.display(p5);
        bNext.display(p5);
        bPrev.display(p5);
        p5.pop();
    }

    public void displayTFAgregar(PApplet p5, String titulo2, String titulo4) {
        String[] labels = {"Título", titulo2, "Fecha", titulo4};
        p5.textFont(appFonts.getThirdFont());
        p5.fill(white);
        p5.textSize(medidaIntermedia);

        for (int i = 0; i < tFAgregar.length; i++) {
            p5.textAlign(p5.LEFT);
            // Texto descriptivo sobre el campo
            p5.text(labels[i], tFAgregar[i].x, tFAgregar[i].y - 10);
            tFAgregar[i].display(p5);
        }
    }
// otros
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
        }else if (categoriaActual == CatEstadistica.CDS) {
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


       /* int[] coloresPrueba = { p5.color(255, 100, 100), p5.color(100, 255, 100),
                p5.color(100, 100, 255), p5.color(255, 255, 100),
                p5.color(255, 100, 255) };*/
    }
    public void actualizarEstadoBotones() {
        bCatVinilos.setEnabled(categoriaActual != CatEstadistica.VINILOS);
        bCatCDs.setEnabled(categoriaActual != CatEstadistica.CDS);
        bCatConciertos.setEnabled(categoriaActual != CatEstadistica.CONCIERTOS);
    }

}