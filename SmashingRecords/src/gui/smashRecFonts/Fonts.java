package gui.smashRecFonts;

import processing.core.PApplet;
import processing.core.PFont;

import static gui.smashRecFonts.Sizes.*;

public class Fonts {

    // Array tipografías
    PFont[] fonts;

    // Constructor
    public Fonts(PApplet p5){ this.setFonts(p5); }

    // Establecer fuentes
    public void setFonts(PApplet p5){
        this.fonts = new PFont[5];
        this.fonts[0] = p5.createFont("data/DonGraffiti.otf", medidaTitulo);
        this.fonts[1] = p5.createFont("data/Halloween.ttf", medidaTitulo-8);
        this.fonts[2] = p5.createFont("data/coolveticaRG.otf", medidaSubtitulo);
        this.fonts[3] = p5.createFont("data/hack_regular.ttf", medidaParrafo);
    }

    // Getter num fuentes
    public int getNumFonts(){ return this.fonts.length; }
    public PFont getFirstFont(){ return this.fonts[0]; }
    public PFont getSecondFont(){ return this.fonts[1]; }
    public PFont getThirdFont(){ return this.fonts[2]; }
    public PFont getForthFont(){ return this.fonts[3]; }
    public PFont getFontAt(int i){ return this.fonts[i]; }

    // Dibuja las fuentes de la app
    void displayFonts(PApplet p5, float x, float y, float h){
        p5.pushStyle();
            for(int i=0; i<getNumFonts(); i++){
                p5.fill(0); p5.stroke(0); p5.strokeWeight(3);
                p5.textFont(getFontAt(i));
                p5.text("Tipografía "+i, x, y+i*h);
            }
        p5.popStyle();
    }
}
