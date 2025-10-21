package gui;

import gui.smashRecColors.Colors;
import gui.smashRecFonts.Fonts;
import gui.smashRecPantallas.Button;
import gui.smashRecPantallas.RoundButton;
import gui.smashRecPantallas.TextField;
import processing.core.PApplet;
import gui.smashRecFonts.Sizes;
import processing.core.PImage;

import static gui.smashRecPantallas.Layout.marginH;

public class GUI {

    // Enumerado para las pantallas
    public enum PANTALLA{INICIO, USUARIO, VINILOS, CDS, CONCIERTOS}

    // Pantalla actual
    public PANTALLA pantallaActual;

    // Botones
    public Button b1, b2, b3, b4, b5;

    // Colores y texto
    Colors appColors;
    Fonts appFonts;

    // Text
    public TextField tFInicioSesion1, tFInicioSesion2;

    // Imatges de la GUI
    RoundButton rB1, rB2;
    PImage icona1, icona2;

    // Constructor de GUI
    public GUI(PApplet p5){
        pantallaActual = PANTALLA.INICIO;

        appColors = new Colors(p5);
        appFonts = new Fonts(p5);

        setTextFields(p5);
        setBotones(p5);
        setRoundButton(p5);
    }

    public void setup(){

    }
    // Setter botones
    public void setBotones(PApplet p5){
        b1 = new Button(p5, "Vinilos", 0, p5.height*0.20f, p5.width*0.20f, p5.height*0.08f);
        b2 = new Button(p5, "CDs", 0, p5.height*0.30f, p5.width*0.20f, p5.height*0.08f);
        b3 = new Button(p5, "Conciertos", 0, p5.height*0.40f, p5.width*0.20f, p5.height*0.08f);
        b4 = new Button(p5, "Estadísticas", 0, p5.height*0.50f, p5.width*0.20f, p5.height*0.08f);
        b5 = new Button(p5, "Iniciar sesión", p5.width*0.5f-(p5.width*0.125f), p5.height*0.68f, p5.width/3-3*((int)marginH), p5.height/19);

    }
    public void setTextFields(PApplet p5){
        tFInicioSesion1 = new TextField(p5, p5.width*0.36f, p5.height*0.5f, p5.width/3-2*((int)marginH), p5.height/20);
        tFInicioSesion2 = new TextField(p5, p5.width*0.36f, p5.height*0.55f, p5.width/3-2*((int)marginH), p5.height/20);
    }
    public void setRoundButton(PApplet p5){
      /*  rB1 = new RoundButton(p5, icona1, 150, 500, 60);
          rB2 = new RoundButton(p5, icona2, 150, 800, 60);*/
    }

    public void setMedia(PApplet p5){
       /* icona1 = p5.loadImage("data/.png");   // imatge transparent (loadShape: vectorial)
          icona2 = p5.loadImage("data/.png"); */

    }

    // Pantallas de la GUI
    public void displayPantallaInicioSesion(PApplet p5){
        p5.background(0);
        p5.push();
            displayLogIn(p5);

        p5.pop();
    }

    public void displayPantallaUsuario(PApplet p5){
        p5.background(0);
        p5.push();
            displaySidebar(p5);
            p5.circle(p5.width*0.65f, p5.height*0.25f, 150);
            p5.textAlign(p5.CENTER);
            p5.textSize(Sizes.medidaSubtitulo); //p5.textFont(gui.smashRecFonts.Fonts.getThirdFont());
            p5.text("Nombre: Jane Doe", p5.width*0.35f, p5.height*0.1f);
            p5.text("Correo: janeDoe@gmail.com", p5.width*0.35f, p5.height*0.2f);

        p5.pop();
            displayBotonesPUsuario(p5);

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
    public void displaySidebar(PApplet p5){
        p5.pop();
        p5.rect(0, 0, p5.width*0.20f, p5.height);

        //displayLogo(p5);

        p5.push();
    }
    public void displayLogIn(PApplet p5){
        p5.push();
        p5.rectMode(p5.CENTER);
        p5.textAlign(p5.CENTER);
        p5.rect(p5.width/2, p5.height/2, p5.width/3, p5.height-200);
        p5.circle(p5.width/2, p5.height/2-175, p5.width/3-275);
        p5.pop();

        tFInicioSesion1.display(p5);
        tFInicioSesion2.display(p5);

        b5.display(p5);

    }

    public void displayLogo(PApplet p5){
        p5.circle(marginH, marginH, 100);
    }



    public void displayColumna1(PApplet p5){}

    public void displayBotonesPUsuario(PApplet p5){
        p5.push();
        b1.display(p5);
        b2.display(p5);

        p5.pop();
    }
}
