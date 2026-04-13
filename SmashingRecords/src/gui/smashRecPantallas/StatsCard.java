package gui.smashRecPantallas;

import processing.core.PApplet;

/**
 * Tarjeta base para la visualización de gráficos estadísticos dentro del sistema
 * de tarjetas paginadas ({@link PagedCard2D}).
 *
 * <p>Extiende {@link Card} y redefine el método {@code display} para dibujar un fondo
 * blanco con título y una línea decorativa separadora. El contenido gráfico específico
 * de cada tipo de diagrama se delega al método abstracto {@link #displayDiagram(PApplet)},
 * que deben implementar las subclases.</p>
 *
 * <p>Las subclases concretas son {@link SectorDiagram}, {@link LinesDiagram} y
 * {@link BarsDiagram}.</p>
 *
 * @author Equipo SmashRecords
 * @version 1.0
 */
public class StatsCard extends Card {


    /**
     * Crea una nueva {@code StatsCard} con título, subtítulo y dimensiones indicadas.
     *
     * @param title    título que se mostrará en la parte superior de la tarjeta
     * @param subTitle subtítulo descriptivo de la tarjeta
     * @param x        posición horizontal en píxeles
     * @param y        posición vertical en píxeles
     * @param w        anchura de la tarjeta en píxeles
     * @param h        altura de la tarjeta en píxeles
     */
    // Usamos el constructor de Card que ya tienes
    public StatsCard(String title, String subTitle, float x, float y, float w, float h) {
        super(title, subTitle, x, y, w, h);
    }

    /**
     * Renderiza la tarjeta estadística en pantalla.
     * Dibuja el fondo blanco de la tarjeta, el título en mayúsculas y una línea
     * decorativa horizontal separadora. A continuación invoca {@link #displayDiagram(PApplet)}
     * para que la subclase dibuje su gráfico concreto.
     * El borde de la tarjeta se resalta en rojo cuando está seleccionada.
     *
     * @param p5       instancia de Processing usada para el dibujado
     * @param selected {@code true} si la tarjeta está seleccionada (borde rojo);
     *                 {@code false} para borde gris por defecto
     */
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

    /**
     * Método gancho destinado a ser sobrescrito por las subclases para dibujar
     * el contenido gráfico específico del diagrama (sectores, líneas, barras, etc.).
     * En esta clase base no realiza ninguna acción.
     *
     * @param p5 instancia de Processing usada para el dibujado
     */
    // Método vacío para que las hijas lo rellenen
    public void displayDiagram(PApplet p5) {
        // Se sobrescribe en las clases hijas
    }
}