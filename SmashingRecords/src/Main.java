import processing.core.PApplet;
import gui.GUI;

public class Main extends PApplet {

    // Atributs
        // GUI
    GUI gui;
    
    public static void main(String[] args) {
        PApplet.main("Main");
    }

    public void settings(){
        fullScreen();
    }
    public void setup(){
        gui = new GUI(this);
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
        gui.tFNotasUsuario.keyPressed(key, keyCode);
        gui.tFBuscador.keyPressed(key, keyCode);
    }

    public void mousePressed(){
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
        }else if(gui.b6.mouseOverButton(this)){
            println("B6 has been pressed.");
            gui.pantallaActual = GUI.PANTALLA.USUARIO;
        }else if(gui.b7.mouseOverButton(this)){
            println("B7 has been pressed.");
            gui.pantallaActual = GUI.PANTALLA.INICIO;
        }
        gui.tFInicioSesion1.isPressed(this);
        gui.tFInicioSesion2.isPressed(this);
        gui.tFNotasUsuario.isPressed(this);
        gui.tFBuscador.isPressed(this);

    }

    public void updateCursor(PApplet p5){
        if(gui.b1.updateHandCursor(p5) ||
           gui.b2.updateHandCursor(p5) ||
           gui.b3.updateHandCursor(p5) ||
           gui.b4.updateHandCursor(p5) ||
           gui.b5.updateHandCursor(p5) ||
           gui.b6.updateHandCursor(p5) ||
           gui.b7.updateHandCursor(p5)){
                cursor(HAND);
        }else{
            cursor(ARROW);
        }
    }
}