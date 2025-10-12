package smashRecPantallas;

import processing.core.PApplet;

public class GUI {

    // Enumerado para las pantallas
    public enum PANTALLA{INICIO, USUARIO, VINILOS, CDS, CONCIERTOS}

    // Pantalla actual
    public PANTALLA pantallaActual;

    // Constructor de GUI
    public GUI(){
        pantallaActual = PANTALLA.INICIO;
    }


    // Pantallas de la GUI

    public void displayPantallaInicio(PApplet p5){
        p5.background(0);
        p5.circle(5, 5, 5);
    }

    public void displayPantallaUsuario(PApplet p5){
        p5.background(0);
        displayLogo(p5);
    }
    public void displayPantallaVinilos(PApplet p5){
        p5.background(0);
        p5.rect(50, 0, 400,400);
    }
    public void displayPantallaCDs(PApplet p5){
        p5.background(0);
        p5.rect(100, 0, 100, 600);
    }
    public void displayPantallaConciertos(PApplet p5){
        p5.background(0);
        p5.rect(200, 0, 600, 700);
    }

    // Zonas de la GUI

    public void displayLogo(PApplet p5){
        p5.rect(0, 0, 20, 100);
    }

    public void displaySidebar(PApplet p5){}

    public void displayColumna1(PApplet p5){}
}
