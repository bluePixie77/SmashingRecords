package gui.smashRecPantallas;

import processing.core.PApplet;

public class ConcertCard extends Card {
    public ConcertCard(String[] info) { super(info); }

    public void display(PApplet p5, boolean selected) {
        p5.pushStyle();
        p5.stroke(selected ? p5.color(255, 0, 0) : 0);
        p5.fill(blanco);
        p5.rect(x, y, w, h, b);

        // Foto Rectangular (ocupa el 70% de la altura de la card)
        float imgW = w - 20;
        float imgH = h * 0.65f;
        p5.fill(50);
        if (img != null) {
            p5.image(img, x + 10, y + 10, imgW, imgH);
        } else {
            p5.rect(x + 10, y + 10, imgW, imgH);
        }

        // Textos debajo
        p5.textAlign(p5.LEFT);
        p5.fill(negro);
        p5.textSize(14);
        p5.text(title, x + 10, y + imgH + 25);    // Nombre Concierto
        p5.textSize(11);
        p5.text(subtitle, x + 10, y + imgH + 40); // Artista
        p5.popStyle();
    }
}
