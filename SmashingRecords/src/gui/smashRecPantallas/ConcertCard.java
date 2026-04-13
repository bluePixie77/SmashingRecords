package gui.smashRecPantallas;

import processing.core.PApplet;
/**
 * Tarjeta de tipo concierto para su visualización dentro de un {@link PagedCard2D}.
 * Extiende {@link Card} y redefine el método {@code display} para mostrar
 * una imagen rectangular que ocupa el 65% de la altura, con el nombre del
 * concierto y el artista en la zona inferior.
 *
 * @author Equipo SmashRecords
 * @version 1.0
 */
public class ConcertCard extends Card {

    /**
     * Crea una nueva {@code ConcertCard} a partir de un array de datos.
     * El array {@code info} debe seguir el formato: {@code [id, título, artista, imagen]}.
     *
     * @param info array de cadenas con los datos del concierto
     */
    public ConcertCard(String[] info) { super(info); }

    /**
     * Renderiza la tarjeta de concierto en pantalla.
     * Dibuja el fondo blanco redondeado, la imagen del concierto (o un rectángulo
     * oscuro si no hay imagen) ocupando el 65% de la altura de la tarjeta, y
     * bajo ella el título del concierto y el nombre del artista.
     * El borde se resalta en rojo cuando la tarjeta está seleccionada.
     *
     * @param p5       instancia de Processing usada para el dibujado
     * @param selected {@code true} si la tarjeta está seleccionada; {@code false} en caso contrario
     */
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
