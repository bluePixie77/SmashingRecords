package smashRecFonts;

import processing.core.PApplet;

public class FontsMain extends PApplet {

    // Tipografies
    Fonts fontsApp;

    public static void main(String[] args){
        PApplet.main("smashRecFonts.FontsMain", args);
    }

    // Constructor
    public FontsMain() {
       fontsApp = new Fonts(this);
    }

    public void draw(){
        background(255);

        textFont(fontsApp.getFirstFont());
        text("Título", 50, 200);

        fill(50);
        textFont(fontsApp.getSecondFont());
        text("Subtítulo", 50, 250);

        fill(55, 0, 0);
        textFont(fontsApp.getThridFont());
        text("Párrafo", 50, 300);

        // Muestra fuentes app
        fontsApp.displayFonts(this, 100, 400, 50);
    }
}
