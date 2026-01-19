package gui.smashRecPantallas;

import gui.smashRecColors.Colors;
import processing.core.PApplet;

import static gui.smashRecFonts.Sizes.medidaIntermedia;
import static processing.core.PConstants.BACKSPACE;
import static processing.core.PConstants.ENTER;

public class TextField {

    // Atributos o propiedades
    public float x, y, h, w;
    int length;

    // Colores
    int fillColor, selectedColor, borderColor; // bg: background, fg: foreground
    int borderWeight;

    // Texto del campo
    public String text = "";
    int textSize = (int) medidaIntermedia-1;

    boolean selected = false;

    // Constructor
    public TextField(PApplet p5, Colors  appColors, int length, float x, float y, float w, float h) {
        this.x = x; this.y = y; this.w = w; this.h = h;
        this.length = length;
        setColors(appColors);
    }

    // Setters
    public void setColors(Colors appColors){
        this.fillColor = appColors.getFifthColor();
        this.selectedColor = appColors.getThirdColor();
        this.borderColor = appColors.getFourthColor();
        this.borderWeight = 1;
    }

    // Dibujar
    public void display(PApplet p5) {
        p5.pushStyle();
        if (selected) {
            p5.fill(selectedColor);
        } else {
            p5.fill(fillColor);
        }

        p5.strokeWeight(borderWeight);
        p5.stroke(borderColor);
        p5.rectMode(p5.CORNER);
        p5.rect(x, y, w, h, 5);

        p5.fill(borderColor);
        p5.textSize(textSize); p5.textAlign(p5.LEFT, p5.CENTER);
        p5.text(text, x + 10, y + h - textSize);
        p5.popStyle();
    }

    // Añadir y/o quitar el texto que se teclea
    public void keyPressed(char key, int keyCode) {
        if (selected) {
            if (keyCode == (int)BACKSPACE) {
                removeText();
            } else if (keyCode == 32) {
                addText(' '); // SPACE
            } else if (keyCode == ENTER){
                selected = false;
            }else{
                /*boolean isKeyCapitalLetter = (key >= 'A' && key <= 'Z');
                boolean isKeySmallLetter = (key >= 'a' && key <= 'z');
                boolean isKeyNumber = (key >= '0' && key <= '9');
                if (isKeyCapitalLetter || isKeySmallLetter || isKeyNumber) { }
                 */
                addText(key);
            }
        }
    }

    // Añadir la letra c al final del texto
    public void addText(char c) {
        if (this.text.length()  < length) {
            this.text += c;
        }
    }

    // Quitar la última letra del texto
    public void removeText() {
        if (text.length() > 0) {
            text = text.substring(0, text.length() - 1);
        }
    }

    // Quitar todo el texto
    public void removeAllText(){
        this.text = "";
    }

    // Recuperar el texto
    public String getText(){
        return this.text;
    }

    // Setter del texto
    public void setText(String t){
        this.text= t;
    }

    // Indica si el ratón está sobre el campo
    public boolean mouseOverTextField(PApplet p5) {
        return (p5.mouseX >= this.x && p5.mouseX <= this.x + this.w && p5.mouseY >= this.y && p5.mouseY <= this.y + this.h);
    }

    // Selecciona el camp de text si pitjam a sobre
    // Deselecciona el camp de text si pitjam a fora
    public void isPressed(PApplet p5) {
        System.out.println("IS PRESSED");
        if(mouseOverTextField(p5)) {

            selected = true;
        } else {
            selected = false;
        }
        System.out.println("SELECTED: "+selected);
    }
}