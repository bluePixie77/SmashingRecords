package gui.smashRecPantallas;

import gui.smashRecColors.Colors;
import processing.core.PApplet;

import static gui.smashRecFonts.Sizes.medidaIntermedia;
import static java.lang.Math.min;
import static processing.core.PApplet.constrain;
import static processing.core.PConstants.BACKSPACE;
import static processing.core.PConstants.CODED;

public class TextArea {

    // Propiedades del campo de texto
    float x, y, h, w;
    int numCols, numRows;

    // Colores
    int bgColor, fgColor, selectedColor, borderColor;
    int borderWeight = 1;

    // Texto del campo
    String[] lines;
    public String text = "";
    int textSize = (int) medidaIntermedia-1;

    boolean selected = false;

    // Constructor
    public TextArea(PApplet p5, float x, float y, float w, float h, int nc, int nr) {
        this.x = x; this.y = y; this.w = w; this.h = h;
        this.numCols = nc; this.numRows = nr;
        this.lines = new String[nr];

        p5.pushStyle();
        if (selected) {
            p5.fill(selectedColor);
        } else {
            p5.fill(bgColor);
        }

        p5.strokeWeight(borderWeight);
        p5.stroke(borderColor);
        p5.rectMode(p5.CORNER);
        p5.rect(x, y, w, h, 5);

        p5.fill(fgColor);
        p5.textSize(textSize); p5.textAlign(p5.LEFT, p5.CENTER);
        p5.text(text, x + 10, y + h - textSize);
        p5.popStyle();
    }

    // Dibuixa el Camp de Text
    public void display(PApplet p5) {
        p5.pushStyle();
        if (selected) {
            p5.fill(selectedColor);
        } else {
            p5.fill(bgColor);
        }

        p5.strokeWeight(borderWeight);
        p5.stroke(borderColor);
        p5.rect(x, y, w, h, 5);

        p5.fill(fgColor);
        p5.textSize(textSize);
        for(int i=0; i<lines.length; i++){
            if(lines[i]!=null){
                p5.text(lines[i], x + 5, y + (i+1)* textSize);
            }
        }
        p5.popStyle();
    }

    public void updateLines(){
        if(text.length()>0){
            int numLines = constrain(text.length() / numCols, 0, this.numRows-1);
            for(int i=0; i<=numLines; i++){
                int start = i*numCols;
                int end = min(text.length(), (i+1)*numCols);
                lines[i] = text.substring(start, end);
            }
        }
        else {
            for(int i=0; i<lines.length; i++){
                lines[i] ="";
            }
        }
    }

    // Afegeix, lleva el text que es tecleja
    public void keyPressed(char key, int keyCode) {
        if (selected) {
            if (keyCode == (int)BACKSPACE) {
                removeText();
            } else if (keyCode == 32) {
                addText(' '); // SPACE
            } else if(key!=CODED) {
                addText(key);
            }
        }
    }

    // Afegeix la lletra c al final del text
    public void addText(char c) {
        if (this.text.length() < this.numCols*this.numRows) {
            this.text += c;
        }
        updateLines();
    }

    // Lleva la darrera lletra del text
    public void removeText() {
        if (text.length()> 0) {
            text = text.substring(0, text.length()-1);
        }
        updateLines();
    }

    // Indica si el ratolí està sobre el camp de text
    public boolean mouseOverTextField(PApplet p5) {
        return (p5.mouseX >= this.x && p5.mouseX <= this.x + this.w && p5.mouseY >= this.y && p5.mouseY <= this.y + this.h);
    }

    // Selecciona el camp de text si pitjam a sobre
    // Deselecciona el camp de text si pitjam a fora
    public void isPressed(PApplet p5) {
        if (mouseOverTextField(p5)) {
            selected = true;
        } else {
            selected = false;
        }
    }
}
