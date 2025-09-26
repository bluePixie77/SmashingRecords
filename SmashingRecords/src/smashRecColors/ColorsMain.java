package smashRecColors;

import processing.core.PApplet;

public class ColorsMain extends PApplet{

    public static void main(String[] args) {
        PApplet.main("smashRecColors.ColorsMain");
    }
    // Atributos o propiedades
    Colors c;


    public void settings(){
        size(800, 800);
    }
    public void setup(){
        c = new smashRecColors.Colors(this);

    }
    public void draw(){
        background(255);

        // Dibujar paleta
        c.displayColors(this, width/4, 100, width-100);
    }
    public void keyPressed(){

    }
    public void mousePressed(){

    }
}
