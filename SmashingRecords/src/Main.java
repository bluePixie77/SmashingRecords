import gui.smashRecPantallas.Button;
import gui.smashRecPantallas.Card;
import gui.smashRecPantallas.PagedCard2D;
import processing.core.PApplet;
import gui.GUI;
import processing.core.PImage;

public class Main extends PApplet {

    // Atributs
        // GUI
    GUI gui;

    /* Load Images
    PImage[] imgs;
    String[] titulo;
    int numImg = 0;

    Button b;*/

    // Paged Cards
    //PagedCard2D pcMusica;   // Vinilos y CDs
   // PagedCard2D pcConcert;  // Conciertos

    // Botones
  //  Button bNext, bPrev; // NEXT y PREV

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

//        // Dimensiones
//        float buttonW = 60, buttonH = 60;                  // Botones Cards
//        float cardsW = width*0.75f, cardsH = height*0.75f; // Espacio total para las Cards

        /* Load imgs
        imgs = new PImage[2];
        titulo = new String[2];
        b = new Button(this, gui.appColors, "Vinilos", 0, height * 0.25f, width * 0.20f, height * 0.05f);*/

        // Paged Card
            // Imatges de les Categories
        img1 = loadImage("data/musicPredetBlackBG.png");
        img2 = loadImage("data/musicPredetWhiteBG.png");

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