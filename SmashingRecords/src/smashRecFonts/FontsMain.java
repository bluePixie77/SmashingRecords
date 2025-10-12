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
        text("The Smashing Records", 50, 200);

        textFont(fontsApp.getSecondFont());
        text("The Smashing Records", 50, 300);

        textFont(fontsApp.getThridFont());
        text("Subtítulo", 50, 400);

        textFont(fontsApp.getForthFont());
        text("Párrafo", 50, 500);

        // Muestra fuentes app
        //fontsApp.displayFonts(this, 100, 400, 50);
    }
}
