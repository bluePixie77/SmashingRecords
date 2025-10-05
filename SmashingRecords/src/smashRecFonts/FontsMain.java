package smashRecFonts;

import processing.core.PApplet;

public class FontsMain extends PApplet {

    // Tipografies
    Fonts fontsApp;

    public static void main(String[] args){
        PApplet.main("smashRecFonts.FontsMain", args);
    }


    public void settings(){
        size(800, 800);
    }
    public void setup(){
        // Constructor
        fontsApp = new Fonts(this);
    }

    public void draw(){
        background(255);

        fill(0);
        textFont(fontsApp.getFirstFont());
        text("Título", 50, 200);

        textFont(fontsApp.getSecondFont());
        text("Subtítulo", 50, 250);

        textFont(fontsApp.getThridFont());
        text("Párrafo", 50, 300);

        // Muestra fuentes app
        fontsApp.displayFonts(this, 100, 400, 50);
    }
}
