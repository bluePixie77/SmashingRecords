package gui.smashRecPantallas;

import processing.core.PApplet;

public class AlbumCard extends Card{

    public AlbumCard(String title, String author, float x, float y, float size){
        super(title, author, x, y, size, size);
    }

    public void display(PApplet p5, boolean selected) {
        p5.pushStyle();

        // Fondo rectangular
        p5.fill(selected ? 200 : 240);
        p5.rect(x, y, w, h, b);

        // Imagen a la izquierda, texto a la derecha (estilo lista)
        if (img != null) {
            p5.image(img, x + 5, y + 5, h - 10, h - 10);
        }

        p5.fill(0);
        p5.textAlign(p5.LEFT, p5.CENTER);
        p5.text(title, x + h, y + h/3);
        p5.text(subtitle, x + h, y + 2*h/3);

        p5.popStyle();
    }
}
