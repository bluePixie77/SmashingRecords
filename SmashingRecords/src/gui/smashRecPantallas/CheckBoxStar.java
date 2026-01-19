
package gui.smashRecPantallas;

import processing.core.PApplet;
import processing.core.PImage;

public class CheckBoxStar {

    // Propietats
    float x, y, w, h;
    PImage imgChecked, imgNotChecked;

    boolean checked;

    // Constructor
    public CheckBoxStar(float x, float y, float w, float h){
        this.x = x; this.y = y;
        this.h = h; this.w = w;
        this.checked = false;
    }

    public void setImages(PApplet p5, String imgName1, String imgName2){
        this.imgChecked = p5.loadImage(imgName1);
        this.imgNotChecked = p5.loadImage(imgName2);
    }

    // Dibuixa el CheckBox
    public void display(PApplet p5){

        p5.pushStyle();

        p5.imageMode(p5.CORNER);

        if(this.checked){
            p5.image(imgChecked, x, y, w, h);
        }
        else{
            p5.image(imgNotChecked, x, y, w, h);
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
                p5.mouseY<= this.y + this.h;
    }

}
