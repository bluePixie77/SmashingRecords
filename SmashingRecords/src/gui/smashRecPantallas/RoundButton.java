package gui.smashRecPantallas;

import processing.core.PApplet;
import processing.core.PImage;

public class RoundButton {
    // Propietats d'un botó:

    float x, y, r;  // Posició (x, y) i dimensions (radi)
    int fillColor, strokeColor; // Colors del boto (fill / stroke).
    int fillColorOver, fillColorDisabled;  // Colors del boto (actiu / inactiu).
    PImage icona;  // Icona del botó
    boolean enabled;  // Estat del botó (actiu / inactiu).

    // Constructor
    public RoundButton(PApplet p5, PImage img, float x, float y, float r){
        this.icona = img;
        this.x = x;
        this.y = y;
        this.r = r;
        this.enabled = true;
        this.fillColor = p5.color(155, 55, 155);
        this.fillColorOver = p5.color(255, 55, 155);
        this.fillColorDisabled = p5.color(150);
        this.strokeColor = p5.color(0);
    }

    // Setters

    public void setImage(PImage img){ this.icona = img; }

    public void setEnabled(boolean b){
        this.enabled = b;
    }

    public void setColors(int cFill, int cStroke, int cOver, int cDisabled){
        this.fillColor = cFill;
        this.strokeColor = cStroke;
        this.fillColorOver = cOver;
        this.fillColorDisabled = cDisabled;
    }

    // Dibuixa el botó
    public void display(PApplet p5){
        p5.pushStyle();
        if(!enabled){
            p5.fill(fillColorDisabled);  // Color desabilitat
        }
        else if(mouseOverButton(p5)){
            p5.fill(fillColorOver);      // Color quan ratolí a sobre
        }
        else{
            p5.fill(fillColor);          // Color actiu però ratolí fora
        }
        p5.stroke(strokeColor); p5.strokeWeight(2);              //Color i gruixa del contorn
        p5.ellipse(this.x, this.y, 2*this.r, 2*this.r);    // Cercle del botó

        // Imatge del boto
        p5.imageMode(p5.CENTER);
        p5.image(this.icona, this.x, this.y, 2*this.r, 2*this.r);
        p5.popStyle();
    }

    // Indica si el cursor està sobre el botó
    public boolean mouseOverButton(PApplet p5){
        return p5.dist(p5.mouseX, p5.mouseY, this.x, this.y)<= this.r;
    }

    // Indica si cal posar el cursor a HAND
    public boolean updateHandCursor(PApplet p5){
        return mouseOverButton(p5) && enabled;
    }
}

