package gui;

import gui.smashRecColors.Colors;
import gui.smashRecFonts.Fonts;
import gui.smashRecPantallas.Button;
import gui.smashRecPantallas.RoundButton;
import gui.smashRecPantallas.TextField;
import processing.core.PApplet;
import processing.core.PImage;

import static gui.smashRecFonts.Sizes.*;
import static gui.smashRecPantallas.Layout.marginH;

public class GUI {

    // Enumerado para las pantallas
    public enum PANTALLA{INICIO, USUARIO, VINILOS, CDS, CONCIERTOS, ESTADISTICAS}

    // Pantalla actual
    public PANTALLA pantallaActual;

    // Botones
    public Button b1, b2, b3, b4, b5, b6, b7;

    // Colores y texto
    Colors appColors;
    Fonts appFonts;

    // Text
    public TextField tFInicioSesion1, tFInicioSesion2, tFNotasUsuario;

    // Imatges de la GUI
    RoundButton rB1, rB2;
    PImage icona1, icona2;

    // Constructor de GUI
    public GUI(PApplet p5){
        pantallaActual = PANTALLA.INICIO;

        setTextFields(p5);
        setBotones(p5);
        setRoundButton(p5);

        appFonts = new Fonts(p5);
        appFonts.setFonts(p5);
    }

    // Setter botones
    public void setBotones(PApplet p5){
        b1 = new Button(p5, "Vinilos", 0, p5.height*0.25f, p5.width*0.20f, p5.height*0.05f);
        b2 = new Button(p5, "CDs", 0, p5.height*0.35f, p5.width*0.20f, p5.height*0.05f);
        b3 = new Button(p5, "Conciertos", 0, p5.height*0.45f, p5.width*0.20f, p5.height*0.05f);
        b4 = new Button(p5, "Estadísticas", 0, p5.height*0.65f, p5.width*0.20f, p5.height*0.05f);
        b5 = new Button(p5, "Sesión", 0, p5.height*0.95f, p5.width*0.20f, p5.height*0.05f);
        b6 = new Button(p5, "Iniciar sesión", p5.width*0.5f-(p5.width*0.125f), p5.height*0.73f, p5.width*0.25f, p5.height*0.052f);
        b7 = new Button(p5, "Cerrar sesión", p5.width*0.525f, p5.height*0.49f, p5.width*0.15f, p5.height*0.052f);
    }
    public void setTextFields(PApplet p5){
        tFInicioSesion1 = new TextField(p5, p5.width*0.36f, p5.height*0.50f, p5.width*0.28f, p5.height*0.05f);
        tFInicioSesion2 = new TextField(p5, p5.width*0.36f, p5.height*0.60f, p5.width*0.28f, p5.height*0.05f);
        tFNotasUsuario = new TextField(p5, p5.width*0.25f, p5.height*0.60f, p5.width*0.70f, p5.height*0.35f);

    }
    public void setMedia(PApplet p5){
       /* icona1 = p5.loadImage("data/.png");   // imatge transparent (loadShape: vectorial)
          icona2 = p5.loadImage("data/.png"); */

    }
    public void setRoundButton(PApplet p5){
      /*  rB1 = new RoundButton(p5, icona1, 150, 500, 60);
          rB2 = new RoundButton(p5, icona2, 150, 800, 60);*/
    }



    // Pantallas de la GUI
    public void displayPantallaInicioSesion(PApplet p5){
        p5.background(0);
        p5.push();
            p5.rectMode(p5.CENTER);
            p5.textAlign(p5.CENTER);
            p5.rect(p5.width*0.5f, p5.height*0.5f, p5.width*0.33f, p5.height*0.80f);
            p5.circle(p5.width*0.5f, p5.height*0.29f, p5.width*0.16f); // imagen logo
        p5.pop();
        p5.push();
            p5.textFont(appFonts.getThirdFont()); p5.fill(0); p5.textSize(medidaIntermedia);
            p5.text("Correo electrónico", p5.width*0.36f, p5.height*0.49f);
            tFInicioSesion1.display(p5);
            p5.text("Contraseña", p5.width*0.36f, p5.height*0.59f);
            tFInicioSesion2.display(p5);

            b6.display(p5);
        p5.pop();
    }

    public void displayPantallaUsuario(PApplet p5){
        p5.background(0);
        p5.push();
            displaySidebar(p5);

            p5.circle(p5.width*0.60f, p5.height*0.20f, p5.width*0.14f);
            p5.textAlign(p5.CENTER);
            p5.textSize(medidaSubtitulo); //p5.textFont(gui.smashRecFonts.Fonts.getThirdFont());
            p5.text("Nombre: Jane Doe", p5.width*0.60f, p5.height*0.38f);
            p5.text("Correo: janeDoe@gmail.com", p5.width*0.60f, p5.height*0.45f);
            b7.display(p5);
            p5.textAlign(p5.CORNER);
            p5.text("Notas", p5.width*0.25f, p5.height*0.59f);
            tFNotasUsuario.display(p5);

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
    public void displayPantallaEstadisticas(PApplet p5){

    }

    // Zonas de la GUI
    public void displaySidebar(PApplet p5){
        p5.pop();
        p5.rect(0, 0, p5.width*0.20f, p5.height);
        p5.circle(p5.width*0.10f, p5.height*0.125f, p5.width*0.12f); //displayLogo(p5);
        b1.display(p5); // Vinilos
        b2.display(p5); // CDs
        b3.display(p5); // Conciertos
        b4.display(p5); // Estadísticas
        b5.display(p5); // Sesión

        p5.push();
    }
    public void displayLogIn(PApplet p5){


    }

    public void displayLogo(PApplet p5){
        p5.circle(marginH, marginH, 100);
    }



    public void displayColumna1(PApplet p5){}

}
