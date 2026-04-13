package gui.smashRecPantallas;

import processing.core.PApplet;

/**
 * Tarjeta de tipo álbum (vinilo o CD) para su visualización dentro de un {@link PagedCard2D}.
 * Extiende {@link Card} y redefine el método {@code display} para mostrar
 * una imagen cuadrada que ocupa casi toda la anchura de la tarjeta, con el
 * nombre del álbum y el artista en la zona inferior.
 *
 * @author Equipo SmashRecords
 * @version 1.0
 */
public class AlbumCard extends Card{

    /**
     * Crea una nueva {@code AlbumCard} a partir de un array de datos.
     * El array {@code info} debe seguir el formato: {@code [id, título, artista, imagen]}.
     *
     * @param info array de cadenas con los datos del álbum
     */
    public AlbumCard(String[] info) { super(info); }

    /**
     * Renderiza la tarjeta de álbum en pantalla.
     * Dibuja el fondo blanco redondeado, la imagen cuadrada del álbum (o un rectángulo
     * oscuro si no hay imagen) ajustada a la anchura de la tarjeta, y bajo ella el
     * título del álbum y el nombre del artista en tamaño de fuente reducido.
     * El borde se resalta en rojo cuando la tarjeta está seleccionada.
     *
     * @param p5       instancia de Processing usada para el dibujado
     * @param selected {@code true} si la tarjeta está seleccionada; {@code false} en caso contrario
     */
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
