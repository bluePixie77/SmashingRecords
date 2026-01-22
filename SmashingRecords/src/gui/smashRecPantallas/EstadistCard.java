package gui.smashRecPantallas;

import processing.core.PApplet;

public class EstadistCard extends Card{

    public EstadistCard(String[] info) { super(info); }

    public void display(PApplet p5, boolean selected) {
        p5.pushStyle();
        // Fondo de la Card
        p5.stroke(selected ? p5.color(255, 0, 0) : 0);
        p5.fill(blanco);
        p5.rect(x, y, w, h, b);

        // Foto Cuadrada (usa el ancho para definir el alto de la foto)
        float fotoSize = w - 20;
        p5.fill(50);
        if (img != null) {
            p5.image(img, x + 10, y + 10, fotoSize, fotoSize);
        } else {
            p5.rect(x + 10, y + 10, fotoSize, fotoSize);
        }

        // Textos debajo
        p5.textAlign(p5.LEFT);
        p5.fill(negro);
        p5.textSize(14);
        p5.text(title, x + 10, y + fotoSize + 25);     // Nombre Album
        p5.textSize(11);
        p5.text(subtitle, x + 10, y + fotoSize + 40);  // Autor
        p5.popStyle();
    }
}
