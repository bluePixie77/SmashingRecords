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
        p5.pushStyle();
        p5.fill(255);
        p5.stroke(selected ? p5.color(255, 0, 0) : 200);
        p5.rect(x, y, w, h, 10);
        p5.popStyle();

        // LLAMAMOS al método que realmente dibuja el gráfico
        this.displayDiagram(p5);
    }

    // Método vacío para que las hijas lo rellenen
    public void displayDiagram(PApplet p5) {
        // Se sobrescribe en las clases hijas
    }
}