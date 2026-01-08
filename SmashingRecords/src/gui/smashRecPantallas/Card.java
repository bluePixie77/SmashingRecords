package gui.smashRecPantallas;

import processing.core.PApplet;
import processing.core.PImage;

public class Card {

    // Propietats
    PImage img;
    String nameAlbum, nameAuthor, section;
    // Dimensions
    float x, y, w, h, b;

    // Constructors

    public Card(){
    }

    public Card(String title, String nA, float x, float y, float w, float h){
        this.nameAlbum = title;
        this.nameAuthor = nA;

        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    public Card(String[] info){
        this.nameAlbum = info[0];
        this.nameAuthor = info[1];
        this.section = info[3];
    }

    //Setters

    public void setDimensions(float x, float y, float w, float h, float b){
        this.x = x; this.y = y;
        this.w = w; this.h = h;
        this.b = b;
    }

    public void setImage(PImage img){
        this.img = img;
    }

    // Dibuixa la Card

    public void display(PApplet p5, boolean selectedCard){

        p5.pushStyle();

        // Rectangle inferior
        p5.stroke(0);
        if(selectedCard){
            p5.fill(200, 100, 100);
        }
        else if(this.mouseOver(p5)){
            p5.fill(200);
        }
        else {
            p5.fill(220);
        }
        p5.rect(x, y, w, h, b/2);

        // imatge descriptiva
        float imgW = (w/3) - 2*b;
        float imgH = h - 2*b;
        if(img!=null){
            p5.image(img, x + b, y + b, imgW, imgH);
            p5.noFill(); p5.rect(x + b, y + b, imgW, imgH);
        }
        else {
            p5.fill(50);
        }
        p5.rect(x + b, y + b, imgW, imgH);

        // Títol
        p5.fill(0); p5.textSize(24); p5.textAlign(p5.CENTER);
        p5.text(nameAlbum, x + 2*w/3, y + h/5);
        p5.text(nameAuthor, x + 2*w/3, y + h/2);

        // Secció
        //p5.fill(0); p5.textSize(18); p5.textAlign(p5.CENTER);
        //p5.text(section, x + 2*w/3 + w/6, y + 2*h/5);

        p5.popStyle();
    }

    public boolean mouseOver(PApplet p5){
        return this.x < p5.mouseX && p5.mouseX < this.x + this.w &&
                this.y < p5.mouseY && p5.mouseY < this.y + this.h;
    }
}
