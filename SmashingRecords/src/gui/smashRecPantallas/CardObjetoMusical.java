package gui.smashRecPantallas;

import gui.smashRecColors.Colors;
import processing.core.PApplet;
import processing.core.PImage;

import processing.core.PApplet;
import processing.core.PImage;

public class CardObjetoMusical {

    // Propietats
    PImage img;
    String title, place, date;
    String section;
    String description;

    // Dimensions
    float x, y, w, h, b;

    // Constructors

    public CardObjetoMusical(){
    }

    public CardObjetoMusical(String title, String place, String date, String section, String description){
        this.title = title;
        this.place = place;
        this.date = date;
        this.section = section;
        this.description = description;
    }

    public CardObjetoMusical(String[] info){
        this.title = info[0];
        this.place = info[1];
        this.date = info[2];
        this.section = info[3];
        this.description = info[4];
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
        p5.text(title, x + 2*w/3, y + h/5);

        // Lloc i data
        p5.fill(0); p5.textSize(18); p5.textAlign(p5.CENTER);
        p5.text(place+", "+date, x + w/3 + w/6, y + 2*h/5);

        // Secció
        p5.fill(0); p5.textSize(18); p5.textAlign(p5.CENTER);
        p5.text(section, x + 2*w/3 + w/6, y + 2*h/5);

        // Descripció
        p5.fill(0); p5.textSize(14); p5.textAlign(p5.LEFT);
        p5.text(description, x + w/3 + b, y + 2*h/3 - b, 2*w/3 - b*2, h/4);


        p5.popStyle();
    }

    public boolean mouseOver(PApplet p5){
        return this.x < p5.mouseX && p5.mouseX < this.x + this.w &&
                this.y < p5.mouseY && p5.mouseY < this.y + this.h;
    }

}
