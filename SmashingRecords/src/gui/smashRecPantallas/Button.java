package gui.smashRecPantallas;

import gui.smashRecColors.Colors;
import processing.core.PApplet;

public class Button {

    // Atributos o propiedades
    float x, y, w, h; // Posición (x, y) i dimensiones(w, h);
    int fillColor, strokeColor; // Colors del boto (fill / stroke).
    int fillColorOver, fillColorDisabled;  // Colors del boto (actiu / inactiu).
    String textBoto;  // Text
    public boolean enabled;  // Estat del botó (actiu / inactiu).

    // Constructor
    public Button(PApplet p5, Colors appColors, String text, float x, float y, float w, float h){
        this.textBoto = text;
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.enabled = true;
        appColors = new Colors(p5);

        this.setColors(appColors);
    }

    // Setters
    public void setEnabled(boolean b){
        this.enabled = b;
    }
    public void setTextBoto(String t){ this.textBoto = t; }

    public void setColors(Colors appColors){
        this.fillColor = appColors.getSecondColor();
        this.fillColorOver = appColors.getFirstColor();
        this.fillColorDisabled = appColors.getThirdColor();
        this.strokeColor = appColors.getFourthColor();
    }

    // Getters
    public boolean isEnabled(){
        return  this.enabled;
    }

    // Dibuja el botó
    public void display(PApplet p5){
        p5.pushStyle();
        if(!enabled){
            p5.fill(fillColorDisabled);  // Color desabilitado
        }
        else if(mouseOverButton(p5)){
            p5.fill(fillColorOver);      // Color cuando el ratón está encima
        }
        else{
            p5.fill(fillColor);          // Color cuando ratón no está encima
        }
        //p5.stroke(strokeColor); p5.strokeWeight(2);      // Color y grosor del contorno
        p5.noStroke();
        p5.rectMode(p5.CORNER); p5.strokeJoin(p5.MITER);
        p5.rect(this.x, this.y, this.w, this.h, 10);    // Rectángulo del botón

        // Text (color, alineación y medida)
        p5.fill(0); p5.textAlign(p5.CENTER); p5.textSize(20);
        p5.text(textBoto, this.x + this.w/2, this.y + this.h/2 + 10);
        p5.popStyle();
    }

    // Indica si el cursor está sobre el botón
    public boolean mouseOverButton(PApplet p5){
        return (p5.mouseX >= this.x) && (p5.mouseX <= this.x + this.w) &&
                (p5.mouseY >= this.y) && (p5.mouseY <= this.y + this.h);
    }

    // Indica si poner cursor a HAND
    public boolean updateHandCursor(PApplet p5){
        return mouseOverButton(p5) && enabled;
    }
}

