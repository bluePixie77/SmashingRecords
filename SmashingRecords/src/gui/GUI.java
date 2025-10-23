package gui;

import gui.smashRecColors.Colors;
import gui.smashRecFonts.Fonts;
import gui.smashRecPantallas.Button;
import gui.smashRecPantallas.RoundButton;
import gui.smashRecPantallas.TextField;
import processing.core.PApplet;
import processing.core.PImage;

import static gui.smashRecFonts.Sizes.*;

public class GUI {

    // Enumerado para las pantallas
    public enum PANTALLA {INICIO, USUARIO, VINILOS, CDS, CONCIERTOS, ESTADISTICAS, AGREGAR}

    // Pantalla actual
    public PANTALLA pantallaActual;

    // Botones
    public Button b1, b2, b3, b4, b5, b6, b7;

    // Colores
    Colors appColors;
    int bg, titles, text;

    // Texto
    Fonts appFonts;
    public TextField tFInicioSesion1, tFInicioSesion2, tFNotasUsuario, tFBuscador;

    // Imatges de la GUI
    RoundButton rB1, rB2;
    PImage icona1, icona2;

    // Constructor de GUI
    public GUI(PApplet p5) {
        pantallaActual = PANTALLA.INICIO;

        setTextFields(p5);
        setBotones(p5);
        setRoundButton(p5);

        appColors = new Colors(p5);
        appFonts = new Fonts(p5);
        appFonts.setFonts(p5);
        setMedia(p5);

        setColors(p5);
    }

    // Setter botones
    public void setBotones(PApplet p5) {
        b1 = new Button(p5, appColors, "Vinilos", 0, p5.height * 0.25f, p5.width * 0.20f, p5.height * 0.05f);
        b2 = new Button(p5, appColors, "CDs", 0, p5.height * 0.35f, p5.width * 0.20f, p5.height * 0.05f);
        b3 = new Button(p5, appColors,"Conciertos", 0, p5.height * 0.45f, p5.width * 0.20f, p5.height * 0.05f);
        b4 = new Button(p5, appColors,"Estadísticas", 0, p5.height * 0.65f, p5.width * 0.20f, p5.height * 0.05f);
        b5 = new Button(p5, appColors,"Sesión", 0, p5.height * 0.95f, p5.width * 0.20f, p5.height * 0.05f);
        b6 = new Button(p5, appColors,"Iniciar sesión", p5.width * 0.5f - (p5.width * 0.125f), p5.height * 0.73f, p5.width * 0.25f, p5.height * 0.052f);
        b7 = new Button(p5, appColors,"Cerrar sesión", p5.width * 0.525f, p5.height * 0.49f, p5.width * 0.15f, p5.height * 0.052f);
    }

    public void setTextFields(PApplet p5) {
        tFInicioSesion1 = new TextField(p5, p5.width * 0.36f, p5.height * 0.50f, p5.width * 0.28f, p5.height * 0.05f);
        tFInicioSesion2 = new TextField(p5, p5.width * 0.36f, p5.height * 0.60f, p5.width * 0.28f, p5.height * 0.05f);
        tFNotasUsuario = new TextField(p5, p5.width * 0.25f, p5.height * 0.60f, p5.width * 0.70f, p5.height * 0.35f);
        tFBuscador = new TextField(p5, p5.width * 0.25f, p5.height * 0.1f, p5.width * 0.65f, p5.height * 0.10f);
    }

    public void setMedia(PApplet p5) {
       icona1 = p5.loadImage("data/iconEmptyUser.png");   // si fuera imagen transparente (loadShape: vectorial)
       icona2 = p5.loadImage("data/iconFullUser.png");
    }

    public void setRoundButton(PApplet p5) {
      /*  rB1 = new RoundButton(p5, icona1, 150, 500, 60);
          rB2 = new RoundButton(p5, icona2, 150, 800, 60); */
    }
    public void setColors(PApplet p5){
        bg = appColors.getFourthColor();
        titles = appColors.getFirstColor();
        text = appColors.getFourthColor();
    }

    // PANTALLAS DE LA GUI
    public void displayPantallaInicioSesion(PApplet p5) {
        p5.push();
        p5.background(bg);
        p5.rectMode(p5.CENTER);
        p5.textAlign(p5.CENTER);
        p5.rect(p5.width * 0.5f, p5.height * 0.5f, p5.width * 0.33f, p5.height * 0.80f);
        p5.circle(p5.width * 0.5f, p5.height * 0.29f, p5.width * 0.16f); // imagen logo
        p5.text("THE SMASHING RECORDS", p5.width*0.5f, p5.height*0.15f);
        p5.pop();
        p5.push();
        p5.textFont(appFonts.getThirdFont());
        p5.fill(titles);
        p5.textSize(medidaIntermedia);
        p5.text("Correo electrónico", p5.width * 0.36f, p5.height * 0.49f);
        tFInicioSesion1.display(p5);
        p5.text("Contraseña", p5.width * 0.36f, p5.height * 0.59f);
        tFInicioSesion2.display(p5);

        b6.display(p5); // Iniciar sesión
        p5.pop();
    }

    public void displayPantallaUsuario(PApplet p5) {
        p5.push();
        p5.background(bg);
        displaySidebar(p5);

        p5.circle(p5.width * 0.60f, p5.height * 0.20f, p5.width * 0.14f);
        p5.textAlign(p5.CENTER);
        p5.textSize(medidaSubtitulo); p5.textFont(appFonts.getThirdFont());
        p5.text("Nombre: Jane Doe", p5.width * 0.60f, p5.height * 0.38f);
        p5.text("Correo: janeDoe@gmail.com", p5.width * 0.60f, p5.height * 0.45f);
        b7.display(p5); // Cerrar sesión
        p5.textAlign(p5.CORNER);
        p5.text("Notas", p5.width * 0.25f, p5.height * 0.59f);
        tFNotasUsuario.display(p5);

        p5.pop();

    }

    public void displayPantallaVinilos(PApplet p5) {
        p5.push();
        p5.background(bg);
        displaySidebar(p5);
        displayBuscadorYFiltros(p5);

        p5.textFont(appFonts.getFontAt(0));
        p5.text("VINILOS", p5.width * 0.25f, p5.height * 0.10f);
        p5.pop();
    }

    public void displayPantallaCDs(PApplet p5) {
        p5.push();
        p5.background(bg);
        displaySidebar(p5);
        displayBuscadorYFiltros(p5);
        displayDisposicionMusica(p5);
        p5.textFont(appFonts.getFontAt(0));
        p5.text("CD's", p5.width * 0.25f, p5.height * 0.10f);
        p5.pop();
    }

    public void displayPantallaConciertos(PApplet p5) {
        p5.push();
        p5.background(bg);
        displaySidebar(p5);
        displayBuscadorYFiltros(p5);

        p5.textFont(appFonts.getFontAt(0));
        p5.text("Conciertos", p5.width * 0.25f, p5.height * 0.10f);

        p5.pop();
    }

    public void displayPantallaEstadisticas(PApplet p5) {
        p5.push();
        p5.background(bg);
        displaySidebar(p5);
        displayDisposicionMusica(p5);

        p5.textFont(appFonts.getFontAt(0));
        p5.text("Estadísticas", p5.width * 0.25f, p5.height * 0.10f);
        p5.pop();
    }

    public void displayPantallaAgregarMusica(PApplet p5) {
        p5.push();
        p5.background(bg);
        displaySidebar(p5);
        //p5.rect(p5.width*0.1f, )

        p5.pop();

    }

    // ZONAS DE LA GUI
    public void displayLogo(PApplet p5) {
        p5.circle(p5.width * 0.10f, p5.height * 0.125f, p5.width * 0.12f);
        /*p5.imageMode(p5.CENTER); p5.scale(p5.width*0.3f);
        p5.image(icona1, p5.width*0.10f, p5.height*0.1f);
        p5.image(icona2, p5.width*0.10f, p5.height*0.1f);*/
    }

    public void displaySidebar(PApplet p5) {
        p5.pop();
        p5.rect(0, 0, p5.width * 0.20f, p5.height);
        displayLogo(p5);
        b1.display(p5); // Vinilos
        b2.display(p5); // CDs
        b3.display(p5); // Conciertos
        b4.display(p5); // Estadísticas
        b5.display(p5); // Sesión

        p5.push();
    }

    public void displayBuscadorYFiltros(PApplet p5) {
        p5.push();
        tFBuscador.display(p5);

        p5.pop();
    }

    public void displayDisposicionMusica(PApplet p5) {
        p5.push();
        p5.rect(p5.width * 0.65f, p5.height * 0.30f, p5.width * 0.10f, p5.width * 0.10f);
        p5.rect(p5.width * 0.80f, p5.height * 0.30f, p5.width * 0.10f, p5.width * 0.10f);
        p5.pop();
    }
}