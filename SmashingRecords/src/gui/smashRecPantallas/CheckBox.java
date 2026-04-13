package gui.smashRecPantallas;

import processing.core.PApplet;

public class CheckBox {
    // Propietats
    public int x, y, w;

    // Colors
    int bgColor, borderColor, checkedColor;

    boolean checked;

    // Constructor
    public CheckBox(PApplet p5, int x, int y, int w){
        this.x = x;
        this.y = y;
        this.w = w;
        this.checked = false;
        this.bgColor = p5.color(255);
        this.borderColor = p5.color(0);
        this.checkedColor = p5.color(180);
    }

    public boolean isChecked(){
        return  this.checked;
    }

    // Dibuixa el CheckBox
    public void display(PApplet p5){
        p5.pushStyle();
        p5.stroke(borderColor);
        p5.strokeWeight(2);

        if(this.checked){
            p5.fill(checkedColor);
        }
        else{
            p5.fill(bgColor);
        }
        p5.rect(x, y, w, w);

        if(this.checked){
            p5.line(x, y, x + w, y + w);
            p5.line(x, y+w, x + w, y);
        }
        p5.popStyle();
    }

    public void setChecked(boolean b){
        this.checked = b;
    }

    // Canvia l'estat de selecció
    public void toggle(){
        this.checked = ! this.checked;
    }


    // Mira si el ratolí està sobre el checkbox
    public boolean onMouseOver(PApplet p5){
        return  p5.mouseX>= this.x &&
                p5.mouseX<= this.x + this.w &&
                p5.mouseY>= this.y &&
                p5.mouseY<= this.y + this.w;
    }

}