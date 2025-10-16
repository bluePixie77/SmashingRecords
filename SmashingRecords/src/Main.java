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
        size(1200, 800);
    }
    public void setup(){
        gui = new GUI(this);
    }

    public void draw(){
        // Dibuixa la pantalla corresponent
        switch(gui.pantallaActual){
            case INICIO: gui.displayPantallaInicio(this);
                         break;
            case USUARIO: gui.displayPantallaUsuario(this);
                          break;
            case VINILOS: gui.displayPantallaVinilos(this);
                          break;
        }

        updateCursor(this);
    }

    public void keyPressed(){
        if(key=='0'){
            gui.pantallaActual = GUI.PANTALLA.INICIO;
        } else if(key=='1'){
            gui.pantallaActual = GUI.PANTALLA.USUARIO;
        }else if(key=='2'){
            gui.pantallaActual = GUI.PANTALLA.VINILOS;
        }
    }

    public void mousePressed(){
        if(gui.b1.mouseOverButton(this)){
            println("B1 has been pressed.");
        }else if(gui.b1.mouseOverButton(this)){
            println("B2 has been pressed.");
        }
    }

    public void updateCursor(PApplet p5){
        if(gui.b1.updateHandCursor(p5) ||
           gui.b2.updateHandCursor(p5)){
                cursor(HAND);
        }else{
            cursor(ARROW);
        }
    }
}