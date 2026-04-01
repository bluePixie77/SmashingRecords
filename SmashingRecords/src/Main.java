import gui.DataBase;
import gui.smashRecPantallas.Button;
import processing.core.PApplet;
import gui.GUI;
import processing.core.PImage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main extends PApplet {

    // Atributs
        // GUI
    GUI gui;
    public static DataBase db;
    boolean loginWrong = false;
    File file;
    String rutaCarpeta = "C:\\Users\\tonim\\Desktop\\imatges\\";
    /*Imatges de les cards
    PImage img1, img2;
    String titol="";
    File file;
    String rutaCarpeta = "C:\\Usuaris\\mariaramis\\Escriptori\\";*/
    
    public static void main(String[] args) {
        PApplet.main("Main");
    }

    public void settings(){
        fullScreen();
    }
    public void setup(){
        db = new DataBase("admin", "l0n3lyr04d", "SmashingRecords");
        db.connect();

        gui = new GUI(this, db);

//        // Dimensiones
//        float buttonW = 60, buttonH = 60;                  // Botones Cards
//        float cardsW = width*0.75f, cardsH = height*0.75f; // Espacio total para las Cards

        /* Load imgs
        imgs = new PImage[2];
        titulo = new String[2];
        b = new Button(this, gui.appColors, "Vinilos", 0, height * 0.25f, width * 0.20f, height * 0.05f);*/

        // Paged Card
           /* Imatges de les Categories
        img1 = loadImage("data/musicPredetBlackBG.png");
        img2 = loadImage("data/musicPredetWhiteBG.png");*/

           /* // Creació de les PagedTables ?
        pcMusica = new PagedCard2D(this, gui.appColors,2, 4, Card.tipoCard.ALBUM);
        pcMusica.setDimensions(50, 50, cardsW, cardsH);
        pcMusica.setData(infoAlbum);
        pcMusica.setCards();
        pcMusica.setImages(img1, img2);

        pcConcert = new PagedCard2D(this, gui.appColors,3, 2, Card.tipoCard.CONCERT);
        pcConcert.setDimensions(50, 50, 800, 600);
        pcMusica.setData(infoConcert);
        pcMusica.setCards();
        pcMusica.setImages(img1, img2);*/
    }

    public void draw(){
        // Dibuixa la pantalla corresponent
        switch(gui.pantallaActual){
            case INICIO:     gui.displayPantallaInicioSesion(this);
                             break;
            case USUARIO:    gui.displayPantallaUsuario(this);
                             break;
            case VINILOS:    gui.displayPantallaVinilos(this);
                             break;
            case CDS:        gui.displayPantallaCDs(this);
                             break;
            case CONCIERTOS: gui.displayPantallaConciertos(this);
                             break;
            case ESTADISTICAS:gui.displayPantallaEstadisticas(this);
                             break;
            case AGREGAR:    gui.displayPantallaAgregarMusica(this);
                             break;
            case AGREGAR_CONCERT:gui.displayPantallaAgregarConcert(this);
                             break;
        }
        updateCursor(this);

        // Actualitza el cursor
        updateCursor(this);


        // Actualitza forma del cursor
        updateCursor(this);
        pushStyle();
        if(loginWrong && gui.pantallaActual== GUI.PANTALLA.INICIO){
            fill(255, 0, 0); textAlign(CENTER, CENTER); textSize(8); textFont(gui.appFonts.getThirdFont());
            text("Nom y/o contraseña incorrecto", width*0.5f, height*0.68f);
        }
        popStyle();
    }

    public void keyTyped(){
        gui.tFInicioSesion1.keyTyped(key);
        gui.tFInicioSesion2.keyTyped(key);
        gui.tFBuscador.keyTyped(key);
        gui.tANotasUsuario.keyTyped(key);
        gui.tANotasAgregar.keyTyped(key);
        for(gui.smashRecPantallas.TextField tf : gui.tFAgregar) {
            tf.keyTyped(key);
        }

    }
    public void keyPressed(){
        gui.tFInicioSesion1.keyPressed(keyCode);
        gui.tFInicioSesion2.keyPressed(keyCode);
        gui.tFBuscador.keyPressed(keyCode);
        gui.tANotasUsuario.keyPressed(keyCode);
        gui.tANotasAgregar.keyPressed(keyCode);
        for(gui.smashRecPantallas.TextField tf : gui.tFAgregar) {
            tf.keyPressed(keyCode);
        }

        /* if(key=='0'){
            gui.pantallaActual = GUI.PANTALLA.INICIO;
        } else if(key=='1'){
            gui.pantallaActual = GUI.PANTALLA.USUARIO;
        }else if(key=='2'){
            gui.pantallaActual = GUI.PANTALLA.VINILOS;
        }

        gui.tFInicioSesion1.keyPressed(key, keyCode);
        gui.tFInicioSesion2.keyPressed(key, keyCode);
       // gui.tFNotasUsuario.keyPressed(key, keyCode);
        gui.tFBuscador.keyPressed(key, keyCode);
        gui.tANotasUsuario.keyPressed(key, keyCode);
        gui.tANotasAgregar.keyPressed(key, keyCode);
        for(gui.smashRecPantallas.TextField tf : gui.tFAgregar) {
            tf.keyPressed(key, keyCode);
        }
        */
    }

    public void mousePressed(){
        if(gui.pantallaActual== GUI.PANTALLA.INICIO) {
            if (gui.b6.mouseOverButton(this)) {
                println("B6 has been pressed.");
                String nom = gui.tFInicioSesion1.getText();
                String pass = gui.tFInicioSesion2.getText();
                if (db.loginCorrecto(nom, pass)) {
                    gui.pantallaActual = GUI.PANTALLA.USUARIO;
                } else {
                    loginWrong = true;
                }
            }
            gui.tFInicioSesion1.isPressed(this);
            gui.tFInicioSesion2.isPressed(this);
        }

        else if(gui.pantallaActual== GUI.PANTALLA.USUARIO){
            if(gui.b1.mouseOverButton(this)){
                println("B1 has been pressed.");
                gui.pantallaActual = GUI.PANTALLA.VINILOS;
            }else if(gui.b2.mouseOverButton(this)){
                println("B2 has been pressed.");
                gui.pantallaActual = GUI.PANTALLA.CDS;
            }else if(gui.b3.mouseOverButton(this)){
                println("B3 has been pressed.");
                gui.pantallaActual = GUI.PANTALLA.CONCIERTOS;
            }else if(gui.b4.mouseOverButton(this)){
                println("B4 has been pressed.");
                gui.pantallaActual = GUI.PANTALLA.ESTADISTICAS;
            }else if(gui.b5.mouseOverButton(this)){
                println("B5 has been pressed.");
                gui.pantallaActual = GUI.PANTALLA.USUARIO;
            }else if(gui.b7.mouseOverButton(this)){
                println("B7 has been pressed.");
                gui.pantallaActual = GUI.PANTALLA.INICIO;
            }
            gui.tANotasUsuario.isPressed(this);
        }
        else if(gui.pantallaActual== GUI.PANTALLA.VINILOS){
            if(gui.b1.mouseOverButton(this)){
                println("B1 has been pressed.");
                gui.pantallaActual = GUI.PANTALLA.VINILOS;
            }else if(gui.b2.mouseOverButton(this)){
                println("B2 has been pressed.");
                gui.pantallaActual = GUI.PANTALLA.CDS;
            }else if(gui.b3.mouseOverButton(this)){
                println("B3 has been pressed.");
                gui.pantallaActual = GUI.PANTALLA.CONCIERTOS;
            }else if(gui.b4.mouseOverButton(this)){
                println("B4 has been pressed.");
                gui.pantallaActual = GUI.PANTALLA.ESTADISTICAS;
            }else if(gui.b5.mouseOverButton(this)){
                println("B5 has been pressed.");
                gui.pantallaActual = GUI.PANTALLA.USUARIO;
            }else if(gui.rBPlus.mouseOverButton(this)){
                gui.pantallaAnterior = gui.pantallaActual; // Guardamos si venimos de VINILOS, CDS o CONCIERTOS
                println("RBPlus has been pressed.");
                gui.pantallaActual = GUI.PANTALLA.AGREGAR;
                for(gui.smashRecPantallas.TextField tf : gui.tFAgregar) { // bucle for each
                    tf.w = width * 0.50f;
                    tf.x = width * 0.38f;
                }
            }else if(gui.bNext.mouseOverButton(this)){
                gui.pcMusica.nextPage();
            }
            else if(gui.bPrev.mouseOverButton(this) && gui.bPrev.isEnabled()){
                gui.pcMusica.prevPage();
            }
            else if(gui.bLoadImage.mouseOverButton(this)){
                // Obrim el dialeg
                selectInput("Selecciona una imatge ...", "fileSelected");
            }else if(gui.bSaveImageToDB.mouseOverButton(this)){
                // Guardar la imatge en una carpeta de l'ordinador
                copiar(file, rutaCarpeta, titol);
                copy();
            }
            else{
                    gui.pcMusica.checkCardSelection(this);
                }

            gui.tFBuscador.isPressed(this);
        }else if(gui.pantallaActual== GUI.PANTALLA.CDS){
            if(gui.b1.mouseOverButton(this)){
                println("B1 has been pressed.");
                gui.pantallaActual = GUI.PANTALLA.VINILOS;
            }else if(gui.b2.mouseOverButton(this)){
                println("B2 has been pressed.");
                gui.pantallaActual = GUI.PANTALLA.CDS;
            }else if(gui.b3.mouseOverButton(this)){
                println("B3 has been pressed.");
                gui.pantallaActual = GUI.PANTALLA.CONCIERTOS;
            }else if(gui.b4.mouseOverButton(this)){
                println("B4 has been pressed.");
                gui.pantallaActual = GUI.PANTALLA.ESTADISTICAS;
            }else if(gui.b5.mouseOverButton(this)){
                println("B5 has been pressed.");
                gui.pantallaActual = GUI.PANTALLA.USUARIO;
            }else if(gui.rBPlus.mouseOverButton(this)){
                gui.pantallaAnterior = gui.pantallaActual; // Guardamos si venimos de VINILOS, CDS o CONCIERTOS
                println("RBPlus has been pressed.");
                gui.pantallaActual = GUI.PANTALLA.AGREGAR;
                for(gui.smashRecPantallas.TextField tf : gui.tFAgregar) {
                    tf.w = width * 0.50f;
                    tf.x = width * 0.38f;
                }
            }else if(gui.bNext.mouseOverButton(this)){
                gui.pcMusica.nextPage();
            }
            else if(gui.bPrev.mouseOverButton(this) && gui.bPrev.isEnabled()){
                gui.pcMusica.prevPage();
            }
            else {
                gui.pcMusica.checkCardSelection(this);
            }
            gui.tFBuscador.isPressed(this);
        }else if(gui.pantallaActual== GUI.PANTALLA.CONCIERTOS){
            if(gui.b1.mouseOverButton(this)){
                println("B1 has been pressed.");
                gui.pantallaActual = GUI.PANTALLA.VINILOS;
            }else if(gui.b2.mouseOverButton(this)){
                println("B2 has been pressed.");
                gui.pantallaActual = GUI.PANTALLA.CDS;
            }else if(gui.b3.mouseOverButton(this)){
                println("B3 has been pressed.");
                gui.pantallaActual = GUI.PANTALLA.CONCIERTOS;
            }else if(gui.b4.mouseOverButton(this)){
                println("B4 has been pressed.");
                gui.pantallaActual = GUI.PANTALLA.ESTADISTICAS;
            }else if(gui.b5.mouseOverButton(this)){
                println("B5 has been pressed.");
                gui.pantallaActual = GUI.PANTALLA.USUARIO;
            }else if(gui.rBPlus.mouseOverButton(this)){
                gui.pantallaAnterior = gui.pantallaActual; // Guardamos si venimos de VINILOS, CDS o CONCIERTOS
                println("RBPlus has been pressed.");
                gui.pantallaActual = GUI.PANTALLA.AGREGAR_CONCERT;
                for(gui.smashRecPantallas.TextField tf : gui.tFAgregar) {
                    tf.w = width * 0.30f;
                    tf.x = width * 0.70f;
                }
            }else if(gui.bNext.mouseOverButton(this) && gui.bNext.isEnabled()){
                gui.pcConcert.nextPage();
            }
            else if(gui.bPrev.mouseOverButton(this) && gui.bPrev.isEnabled()){
                gui.pcConcert.prevPage();
            }
            else {
                gui.pcConcert.checkCardSelection(this);
            }
            gui.tFBuscador.isPressed(this);
        }else if(gui.pantallaActual== GUI.PANTALLA.ESTADISTICAS) {
            if (gui.b1.mouseOverButton(this)) {
                println("B1 has been pressed.");
                gui.pantallaActual = GUI.PANTALLA.VINILOS;
            } else if (gui.b2.mouseOverButton(this)) {
                println("B2 has been pressed.");
                gui.pantallaActual = GUI.PANTALLA.CDS;
            } else if (gui.b3.mouseOverButton(this)) {
                println("B3 has been pressed.");
                gui.pantallaActual = GUI.PANTALLA.CONCIERTOS;
            } else if (gui.b4.mouseOverButton(this)) {
                println("B4 has been pressed.");
                gui.pantallaActual = GUI.PANTALLA.ESTADISTICAS;
            } else if (gui.b5.mouseOverButton(this)) {
                println("B5 has been pressed.");
                gui.pantallaActual = GUI.PANTALLA.USUARIO;
            } else if (gui.bCatVinilos.mouseOverButton(this) && gui.bCatVinilos.enabled) {
                gui.categoriaActual = GUI.CatEstadistica.VINILOS;
                gui.actualizarDatosGraficos(this);
                gui.actualizarEstadoBotones();
            } else if (gui.bCatCDs.mouseOverButton(this) && gui.bCatCDs.enabled) {
                gui.categoriaActual = GUI.CatEstadistica.CDS;
                gui.actualizarDatosGraficos(this);
                gui.actualizarEstadoBotones();
            } else if (gui.bCatConciertos.mouseOverButton(this) && gui.bCatConciertos.enabled) {
                gui.categoriaActual = GUI.CatEstadistica.CONCIERTOS;
                gui.actualizarDatosGraficos(this);
                gui.actualizarEstadoBotones();
            }
            // Navegación de páginas (cambiar de tipo de gráfico)
            if (gui.pcStats != null) {
                if (gui.bNext.mouseOverButton(this)) gui.pcStats.nextPage();
                if (gui.bPrev.mouseOverButton(this)) gui.pcStats.prevPage();
            }
        }else if(gui.pantallaActual == GUI.PANTALLA.AGREGAR || gui.pantallaActual == GUI.PANTALLA.AGREGAR_CONCERT){
            if(gui.bOk.mouseOverButton(this) || gui.bCancelar.mouseOverButton(this)){
                // Volvemos exactamente de donde vinimos
                gui.pantallaActual = gui.pantallaAnterior;
            }else if(gui.bEliminarMultimedia.mouseOverButton(this)){
                System.out.println("MULTIMEDIA ELIMINADA");
                gui.pantallaActual = gui.pantallaAnterior;
            }
            gui.cbl.checkMouse(this);
            gui.tANotasAgregar.isPressed(this);
            for(gui.smashRecPantallas.TextField tf : gui.tFAgregar) {
                tf.isPressed(this);
            }
        }
        /*  b1 // Vinilos
            b2 // CDs
            b3 // Conciertos
            b4 // Estadísticas
            b5 // Sesión (de la sidebar)
            b6 // Iniciar sesión
            b7 // Cerrar sesión */
        /*if(b.mouseOverButton(this)){
            selectInput("Selecciona una imatge...", "fileSelected");
        }*/
    }

    public void updateCursor(PApplet p5){
        if(gui.b1.updateHandCursor(p5) && gui.b1.isEnabled() ||
           gui.b2.updateHandCursor(p5) && gui.b2.isEnabled() ||
           gui.b3.updateHandCursor(p5) && gui.b3.isEnabled() ||
           gui.b4.updateHandCursor(p5) && gui.b4.isEnabled() ||
           gui.b5.updateHandCursor(p5) && gui.b5.isEnabled() ||
           gui.b6.updateHandCursor(p5) && gui.b6.isEnabled() ||
           gui.b7.updateHandCursor(p5) && gui.b7.isEnabled() ||
           gui.rBPlus.updateHandCursor(p5) && gui.rBPlus.enabled ||
           gui.rBFilter.updateHandCursor(p5) && gui.rBFilter.enabled ||
           gui.bNext.mouseOverButton(p5) && gui.bNext.isEnabled() ||
           gui.bPrev.mouseOverButton(p5) && gui.bPrev.isEnabled() ||
           gui.bCancelar.mouseOverButton(p5) && gui.bCancelar.isEnabled() ||
           gui.bOk.mouseOverButton(p5) && gui.bOk.isEnabled() ||
           gui.bEliminarMultimedia.mouseOverButton(p5) && gui.bEliminarMultimedia.isEnabled() ||
           gui.cbl.checkCursor(this) ||
           gui.bCatVinilos.updateHandCursor(p5) ||
           gui.bCatCDs.updateHandCursor(p5) ||
           gui.bCatConciertos.updateHandCursor(p5)) ||
           gui.bLoadImage.mouseOverButton(p5) || gui.bSaveImageToDB.mouseOverButton(p5)){
                cursor(HAND);
        }else{
            cursor(ARROW);
        }
    }
  /*  public void fileSelected(File selection){
        if(selection == null){
            System.out.println("No file selected.");
        }else{
            String rutaImage = selection.getAbsolutePath();
            imgs[numImg] = loadImage(rutaImage);
            titulo[numImg] = selection.getName();
            numImg++;
        }
    }*/

    // Carrega Imatge
    public void fileSelected(File selection) {
        if (selection == null) {
            println("No s'ha seleccionat cap fitxer.");
        } else {
            // Referència al fitxer imatge
            file = selection;

            // Obtenim la ruta del fitxer seleccionat
            String rutaImatge = selection.getAbsolutePath();

            img = loadImage(rutaImatge);  // Actualitzam imatge
            titol = selection.getName();  // Actualitzam títol (igual)
        }
    }
    // Copia un fitxer a una altra ubicació
    public void copiar(File file, String rutaCopia, String titol){
        Path original = Paths.get(file.getAbsolutePath());
        Path copia    = Paths.get(rutaCopia+"/"+titol);
        try{
            Files.copy(original, copia);
            println("OK: fitxer copiat a la carpeta.");
        } catch (IOException e) {
            println("ERROR: No s'ha pogut copiar el fitxer.");
        }
    }

}