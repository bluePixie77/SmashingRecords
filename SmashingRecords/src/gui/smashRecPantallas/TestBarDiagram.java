
package gui.smashRecPantallas;

import gui.smashRecPantallas.BarsDiagram;
import processing.core.PApplet;

public class TestBarDiagram extends PApplet {

    // Elements de la Interfície Gràfica (BarsDiagram)
    // Diagrama de Barres
    BarsDiagram s;

    // Dades del Diagrama (textos, valors i colors)
    String[] textos = {"1 STAR", "2 STARS", "3 STARS", "4 STARS", "5 STARS"};
    float[] values = {20, 50, 100, 500, 100};
    int [] colors = {color(0,0,255), color(50,50,200), color(255,0,0), color(0,255,0), color(100,255,100)};


    public static void main(String[] args) {
        PApplet.main("gui.smashRecPantallas.TestBarDiagram", args);
    }

    public void settings(){
        size(1200, 800);
        smooth(10);
    }

    public void setup(){
        // Creació del Diagrama de Barres
        s = new BarsDiagram("Diagrama de Barras", 50, 50, width/1.2f, height/1.2f);

        // Configuració de Dades (textos, valors, colors)
        s.setTexts(textos);
        s.setValues(values);
        s.setColors(colors);
    }

    public void draw() {
        // Fons de la finestra
        background(255);

        // Dibuix del Diagrama de Barres
        s.displayDiagram(this);
    }

    // ******************* KEYBOARD interaction ***************************** //

    public void keyPressed(){
    }

    // ******************* MOUSE interaction ***************************** //

    // En cas de pitjar el ratolí
    public void mousePressed(){
    }

    public void mouseDragged(){
        println("MOUSE DRAGGED");
    }

    public void mouseReleased() {
        println("MOUSE RELEASED");
    }
}
