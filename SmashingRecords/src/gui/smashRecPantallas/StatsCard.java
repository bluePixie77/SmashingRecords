package gui.smashRecPantallas;

import processing.core.PApplet;

public class StatsCard extends Card {

    // Usamos el constructor de Card que ya tienes
    public StatsCard(String title, String subTitle, float x, float y, float w, float h) {
        super(title, subTitle, x, y, w, h);
    }

    // Sobrescribimos el display original de Card
    // PagedCard2D llamará a este método
    public void display(PApplet p5, boolean selected) {
        // Dibujamos el fondo de la Card para que no sea transparente
        p5.push();

        p5.rectMode(p5.CORNER);
        p5.ellipseMode(p5.CENTER);
        p5.fill(255);
        p5.stroke(selected ? p5.color(255, 0, 0) : 200);
        p5.rect(x, y, w, h, 10);

        // Título gráficos
        p5.fill(0); // Negro
        p5.textAlign(p5.LEFT, p5.TOP);
        p5.textSize(22);

        p5.text(this.title.toUpperCase(), x + 30, y + 25);

        // Línea decorativa
        p5.stroke(230);
        p5.line(x + 30, y + 55, x + w - 30, y + 55);

        p5.pop();

        // LLAMAMOS al método que realmente dibuja el gráfico
        this.displayDiagram(p5);
    }

    // Método vacío para que las hijas lo rellenen
    public void displayDiagram(PApplet p5) {
        // Se sobrescribe en las clases hijas
    }
}