
package gui.smashRecPantallas;

import gui.smashRecPantallas.BarsDiagram;
import gui.smashRecPantallas.SectorDiagram;
import processing.core.PApplet;

public class TestSectorDiagram extends PApplet {

    // Elements de la Interfície Gràfica (SectorDiagram)
    // Variable
    SectorDiagram s;

    // Dades del Diagrama (textos, valors i colors)
    String[] textos = {"1 STAR", "2 STARS", "3 STARS", "4 STARS", "5 STARS"};
    float[] values = {20, 50, 100, 500, 100};
    int[] colors = {color(0,0,255), color(50,50,200), color(255,50,50), color(0,255,0), color(100,255,100)};


    public static void main(String[] args) {
        PApplet.main("gui.smashRecPantallas.TestSectorDiagram", args);
    }

    public void settings(){
        size(1200, 800);
        smooth(10);
    }

    public void setup(){
        // Creació del Diagrama de Sectors
        s = new SectorDiagram("Diagrama de sectores", width/2, height/2, width/3, width/3);

        // Configuració de Dades (textos, valors, colors)
        s.setTexts(textos);
        s.setValues(values);
        s.setColors(colors);
    }

    public void draw() {
        // Fons de la finestra
        background(255);

        // Dibuix del Diagrama de Barres
        s.display(this);
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
