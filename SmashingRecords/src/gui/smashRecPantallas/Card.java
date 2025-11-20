package gui.smashRecPantallas;

import gui.smashRecColors.Colors;
import processing.core.PApplet;
import processing.core.PImage;

public class Card { // P2 práctica

    // Propiedades o atributos
    PImage img;
    String title;
    TextField textField; //
    Button b;

    float x, y, w, h;

    public Card(PApplet p5, Colors appColors, String title, float x, float y, float w, float h) {
        this.title = title;
        this.x = x; this.y = y;
        this.w = w; this.h = h;

        this.textField = new TextField(p5, appColors, (int)(w-10), (int)(x+5), (int)(y+h/2), w-10, 25); //
        this.b = new Button(p5, appColors, "Ver", (int)x+5, (int)(y+h/2+30), (int)w-10, 20);
    }

    public void display(PApplet p5){
        p5.pushStyle();
         p5. rect(x, y, w, h);

         if(img == null){
             p5.rect(x+5, y+5, w-10, h/4);
         }else{
             p5.image(img, x+5, y+5, w-10, h/4);
         }

         p5.fill(0);
         p5.text(title, x+5, h/4+15);

         textField.display(p5);
         b.display(p5);

        p5.popStyle();

    }

    public void clickMouseOnCardItems(PApplet p5){
        textField.isPressed(p5);
        if(b.mouseOverButton(p5)){
            System.out.println("Card Button clicked!");
        }
    }

    public void typeOnCardItems(PApplet p5){
        textField.keyPressed(p5.key, p5.keyCode);

    }

}
