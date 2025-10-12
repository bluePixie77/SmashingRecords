import processing.core.PApplet;
import smashRecPantallas.GUI;

public class Main extends PApplet {

    // Atributs
        // GUI
    GUI gui;


    public static void main(String[] args) {
        PApplet.main("Main");
    }

    public void settings(){
        size(800, 800);
    }
    public void setup(){
        gui = new GUI();
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



}