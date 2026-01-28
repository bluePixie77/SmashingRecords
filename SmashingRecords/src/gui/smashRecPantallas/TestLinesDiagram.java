
package gui.smashRecPantallas;

import gui.smashRecPantallas.BarsDiagram;
import gui.smashRecPantallas.TestLinesDiagram;
import processing.core.PApplet;

public class TestLinesDiagram extends PApplet {

    // Elements de la Interfície Gràfica (LinesDiagram)
    // Variable
    LinesDiagram s;

    // Dades del Diagrama (etiquetes)
    String[] textos  = {"Jan", "Feb", "Mar", "Apr", "May", "Jun",
            "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" };

    // Dades del Diagrama (valors)
    float[] values  = {5, 2, 10, 20, 40, 7, 1, 0, 0, 3, 2, 10};

    // Color de la línia
    int colorLine = color(150,50,200);


    public static void main(String[] args) {
        PApplet.main("gui.smashRecPantallas.TestLinesDiagram", args);
    }

    public void settings(){
        size(1200, 800);
        smooth(10);
    }

    public void setup(){

        // Creació del Diagrama de Barres
        s = new LinesDiagram("Estadística lineal", 50, 50, width-100, height - 200);

        // Configuració de Dades (textos, valors, colors)
        s.setTexts(textos);
        s.setValues(values);
        s.setColors(colorLine);
    }

    public void draw() {
        // Fons de la finestra
        background(255);

        // Dibuix del Diagrama de Línies
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
