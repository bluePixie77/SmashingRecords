import gui.smashRecPantallas.Button;
import gui.smashRecPantallas.PagedCard2D;
import processing.core.PApplet;
import gui.GUI;
import processing.core.PImage;

import java.io.File;

public class Main extends PApplet {

    // Atributs
        // GUI
    GUI gui;

    /* Load Images
    PImage[] imgs;
    String[] titulo;
    int numImg = 0;

    Button b;*/

    // Paged Card
    PagedCard2D pcCDVinil;
    PagedCard2D pcConcert;

         // Dimensions dels botons
    Button b1, b2;
    float buttonW = 60, buttonH = 60;
        // Dimensions de les cards
    float cardsW = 800, cardsH = 700;

    // Dades de les cards
    String[][] info = {
            {"Títol 0", "Lloc 0", "Data 0", "Secció 0", "Descripció 0"},
            {"Títol 1", "Lloc 1", "Data 1", "Secció 1", "Descripció 1"},
            {"Títol 2", "Lloc 2", "Data 2", "Secció 2", "Descripció 2"},
            {"Títol 3", "Lloc 3", "Data 3", "Secció 1", "Descripció 3"},
            {"Títol 4", "Lloc 4", "Data 4", "Secció 1", "Descripció 4"},
            {"Títol 5", "Lloc 5", "Data 5", "Secció 2", "Descripció 5"},
            {"Títol 6", "Lloc 6", "Data 6", "Secció 2", "Descripció 6"},
            {"Títol 7", "Lloc 7", "Data 7", "Secció 1", "Descripció 7"},
            {"Títol 8", "Lloc 8", "Data 8", "Secció 8", "Descripció 8"},
            {"Títol 9", "Lloc 9", "Data 9", "Secció 9", "Descripció 9"},
            {"Títol 10", "Lloc 10", "Data 10", "Secció 10", "Descripció 10"},
    };

    // Imatges de les cards
    PImage img1, img2;
    //
    
    public static void main(String[] args) {
        PApplet.main("Main");
    }

    public void settings(){
        fullScreen();
    }
    public void setup(){
        gui = new GUI(this);

        /* Load imgs
        imgs = new PImage[2];
        titulo = new String[2];
        b = new Button(this, gui.appColors, "Vinilos", 0, height * 0.25f, width * 0.20f, height * 0.05f);*/

        // Paged Card
            // Imatges de les Categories
        img1 = loadImage("data/musicPredetBlackBG.png");
        img2 = loadImage("data/musicPredetWhiteBG.png");

            // Creació de la taula
        pcCDVinil = new PagedCard2D(this, 2, 4, gui.appColors);
        pcCDVinil.setDimensions(50, 50, cardsW, cardsH);
        pcCDVinil.setData(info);
        pcCDVinil.setCards();
        pcCDVinil.setImages(img1, img2);

            // Creació dels botons
        b1 = new Button(this, gui.appColors, "NEXT", 100 + cardsW, 80, buttonW, buttonH);
        b2 = new Button(this, gui.appColors, "PREV", 100 + cardsW, 100 + buttonH, buttonW, buttonH);
        //
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

        }
        updateCursor(this);

        /* Load imgs
        for(int i=0; i<imgs.length; i++){
            if(imgs[i] != null){
                image(imgs[i], 50 + i*350, 50, 350, 600);
                textSize(34);
                textAlign(RIGHT);
                fill(0);
                text(titulo[i], 750, 350 + i*350);
            } else {
                fill(255);
                rect(50+  i*350, 50, 350 , 600);
                textSize(34);
                textAlign(RIGHT);
                text(i+ "¨: Sense imatge", 750, 350 + i*350);
            }
        }
        // Dibuixa el botó
        b.display(this);*/

        // Paged Card
            // Dibuja las Cards paginadas
        pcCDVinil.display(this);
        pcCDVinil.printSelectedCard(this);

        // Actualitza forma del cursor
        updateCursor(this);
    }

    public void keyPressed(){
       /* if(key=='0'){
            gui.pantallaActual = GUI.PANTALLA.INICIO;
        } else if(key=='1'){
            gui.pantallaActual = GUI.PANTALLA.USUARIO;
        }else if(key=='2'){
            gui.pantallaActual = GUI.PANTALLA.VINILOS;
        }*/
        gui.tFInicioSesion1.keyPressed(key, keyCode);
        gui.tFInicioSesion2.keyPressed(key, keyCode);
       // gui.tFNotasUsuario.keyPressed(key, keyCode);
        gui.tFBuscador.keyPressed(key, keyCode);
        gui.tANotasUsuario.keyPressed(key, keyCode);
    }

    public void mousePressed(){
        if(gui.pantallaActual== GUI.PANTALLA.INICIO){
            if(gui.b6.mouseOverButton(this)){
                println("B6 has been pressed.");
                gui.pantallaActual = GUI.PANTALLA.USUARIO;
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
                println("RBPlus has been pressed.");
                gui.pantallaActual = GUI.PANTALLA.AGREGAR;
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
                println("RBPlus has been pressed.");
                gui.pantallaActual = GUI.PANTALLA.AGREGAR;
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
                println("RBPlus has been pressed.");
                gui.pantallaActual = GUI.PANTALLA.AGREGAR;
            }
            gui.tFBuscador.isPressed(this);
        }else if(gui.pantallaActual== GUI.PANTALLA.ESTADISTICAS){
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
            }
        }
        /*  b1 // Vinilos
            b2 // CDs
            b3 // Conciertos
            b4 // Estadísticas
            b5 // Sesión
            b6 // Iniciar sesión
            b7 // Cerrar sesión */
        /*if(b.mouseOverButton(this)){
            selectInput("Selecciona una imatge...", "fileSelected");
        }*/
    }

    public void updateCursor(PApplet p5){
        if(gui.b1.updateHandCursor(p5) ||
           gui.b2.updateHandCursor(p5) ||
           gui.b3.updateHandCursor(p5) ||
           gui.b4.updateHandCursor(p5) ||
           gui.b5.updateHandCursor(p5) ||
           gui.b6.updateHandCursor(p5) ||
           gui.b7.updateHandCursor(p5) ||
           gui.rBPlus.updateHandCursor(p5) ||
           gui.rBFilter.updateHandCursor(p5)){
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
}