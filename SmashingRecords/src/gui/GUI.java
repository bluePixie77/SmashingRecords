package gui;

import gui.smashRecPantallas.Boton;
import processing.core.PApplet;
import gui.smashRecFonts.Sizes;

public class GUI {

    // Enumerado para las pantallas
    public enum PANTALLA{INICIO, USUARIO, VINILOS, CDS, CONCIERTOS}

    // Pantalla actual
    public PANTALLA pantallaActual;

    // Botones
    public Boton b1, b2;

    // Constructor de GUI
    public GUI(PApplet p5){
        pantallaActual = PANTALLA.INICIO;
        setBotones(p5);
        gui.smashRecPantallas.Boton.setColors();
    }

    // Setter botones
    public void setBotones(PApplet p5){
        b1 = new Boton(p5, "RED", 40, 400, 250, 100);
        b2 = new Boton(p5, "GREEN", 40, 550, 250, 100);

    }

    // Pantallas de la GUI
    public void displayPantallaInicio(PApplet p5){
        p5.background(0);
        p5.circle(5, 5, 5);

        displayBotonesPUsuario(p5);

    }

    public void displayPantallaUsuario(PApplet p5){
        p5.background(0);
        p5.push();
            p5.rect(0, 0, p5.width/6, p5.height);
            //displayLogo(p5);

            p5.circle((p5.width/2)+50, (p5.height/2)-300, 150);
            p5.textAlign(p5.CENTER);
            p5.textSize(Sizes.medidaSubtitulo); //p5.textFont(gui.smashRecFonts.Fonts.getThirdFont());
            p5.text("Nombre: Jane Doe", (p5.width/2)+50, (p5.height/2)-200);
            p5.text("Correo: janeDoe@gmail.com", (p5.width/2)+50, (p5.height/2)-150);

        p5.pop();
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

        p5.circle(gui.smashRecPantallas.Layout.marginH, gui.smashRecPantallas.Layout.marginH, 100);
    }

    public void displaySidebar(PApplet p5){}

    public void displayColumna1(PApplet p5){}

    public void displayBotonesPUsuario(PApplet p5){
        b1.display(p5);
        b2.display(p5);
    }
}
