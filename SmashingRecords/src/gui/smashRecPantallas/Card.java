package gui.smashRecPantallas;

import processing.core.PApplet;
import processing.core.PImage;

public class Card {

    public enum tipoCard{ALBUM, CONCERT}
    // Propietats
    PImage img;
    String title, subtitle, section;
    // Dimensions
    float x, y, w, h, b;

    // Colors
    int naraFuerte, naraFlojo, blanco, negro;

    // Constructors
    public Card(String title, String subTitle, float x, float y, float w, float h){
        this.title = title;
        this.subtitle = subTitle;
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    public Card(String[] info){
        this.title = info[0];
        this.subtitle = info[1];
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

    public void setCardColors(int naraFuerte, int naraFlojo, int blanco, int negro){
        this.naraFuerte = naraFuerte;
        this.naraFlojo = naraFlojo;
        this.blanco = blanco;
        this.negro = negro;
    }

    // Dibuixa la Card

    public void display(PApplet p5, boolean selectedCard){
        p5.pushStyle();

        //p5.stroke(0);
        //p5.fill(200, 100, 100);
        //p5.rect(x, y, w, h+30, b/2);

        // Color según el estado ??
        if(selectedCard){
            p5.fill(naraFuerte);
        }
        else if(this.mouseOver(p5)){
            p5.fill(naraFlojo);
        }
        else {
            p5.fill(blanco);
        }
        // Rectángulo base
        p5.stroke(negro);
        p5.rect(x, y, w, h, b/2);

        // Dibujo imagen
        float imgW = (w/3) - 2*b;
        float imgH = h - 2*b;
        if(img!=null){
            p5.image(img, x + b, y + b, imgW, imgH);
            p5.stroke(selectedCard? naraFuerte:blanco);
            p5.noFill(); p5.rect(x + b, y + b, imgW, imgH);
        }

        // Texto
        p5.fill(negro); p5.textSize(24); p5.textAlign(p5.CORNER);
        p5.text(title, x+30, 4.9f*y);
        p5.text(subtitle, x+30, 5.2f*y);

        // Títol
        p5.fill(0); p5.textSize(24); p5.textAlign(p5.CENTER);
        p5.text(title, x + 2*w/3, y + h/5);
        p5.text(subtitle, x + 2*w/3, y + h/2);

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
