package gui.smashRecPantallas;

import gui.smashRecColors.Colors;
import processing.core.PApplet;
import processing.core.PImage;

public class RoundButton {

    // Propiedades
    float x, y, r;
    public int fillColor, strokeColor; // Colores del botón (fill / stroke).
    public int fillColorOver, fillColorDisabled;  // Colores del botón (activo / inactivo).
    PImage icona;  // Icona del botón
    public boolean enabled;  // Estado del botón (activo / inactivo).

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

    // Dibujar el botón
    public void display(PApplet p5){
        p5.pushStyle();
        if(!enabled){
            p5.fill(fillColorDisabled);  // Color desabilitado
        }
        else if(mouseOverButton(p5)){
            p5.fill(fillColorOver);      // Color ratón sobre
        }
        else{
            p5.fill(fillColor);          // Color activo pero sin ratón sobre
        }
        p5.stroke(strokeColor); p5.strokeWeight(2);              // Color y grosor del contorno
        p5.ellipse(this.x, this.y, 2*this.r, 2*this.r);    // Círculo del botón

        // Imagen del botón
        p5.imageMode(p5.CENTER);
        p5.image(this.icona, this.x, this.y, 2*this.r, 2*this.r);
        p5.popStyle();
    }

    // Cursor sobre botón
    public boolean mouseOverButton(PApplet p5){
        return p5.dist(p5.mouseX, p5.mouseY, this.x, this.y)<= this.r;
    }

    // Cursor a HAND
    public boolean updateHandCursor(PApplet p5){
        return mouseOverButton(p5) && enabled;
    }
}

