package gui.smashRecPantallas;

import gui.smashRecColors.Colors;
import processing.core.PApplet;
import processing.core.PImage;

public class RoundButton {
    // Propietats d'un botó:

    float x, y, r;  // Posició (x, y) i dimensions (radi)
    int fillColor, strokeColor; // Colors del boto (fill / stroke).
    int fillColorOver, fillColorDisabled;  // Colors del boto (actiu / inactiu).
    PImage icona;  // Icona del botó
    public boolean enabled;  // Estat del botó (actiu / inactiu).

    // Constructor
    public RoundButton(PApplet p5, Colors appColors, PImage img, float x, float y, float r){
        this.icona = img;
        this.x = x;
        this.y = y;
        this.r = r;
        this.enabled = true;
        appColors = new Colors(p5);
        this.setImage(img);
        this.setColors(appColors);
    }

    // Setters

    public void setImage(PImage img){ this.icona = img; }

    public void setEnabled(boolean b){
        this.enabled = b;
    }

    public void setColors(Colors appColors){
        this.fillColor = appColors.getSecondColor();
        this.fillColorOver = appColors.getFirstColor();
        this.fillColorDisabled = appColors.getThirdColor();
        this.strokeColor = appColors.getFourthColor();
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

