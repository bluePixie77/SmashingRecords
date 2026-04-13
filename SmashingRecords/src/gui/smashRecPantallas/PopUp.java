package gui.smashRecPantallas;

import processing.core.PApplet;
import gui.smashRecColors.Colors;
/**
 * Ventana emergente modal de confirmación para la interfaz gráfica.
 * Muestra un cuadro de diálogo con título, mensaje y dos botones de acción
 * («Eliminar» y «Cancelar»), que solo se renderiza cuando {@link #visible} es {@code true}.
 *
 * <p>Habitualmente se utiliza para pedir confirmación antes de realizar una operación
 * destructiva, como la eliminación de un elemento de la base de datos.</p>
 *
 * @author Equipo SmashRecords
 * @version 1.0
 */
public class PopUp {
    /** Posición horizontal de la esquina superior izquierda del pop-up en píxeles. */
    float x;

    /** Posición vertical de la esquina superior izquierda del pop-up en píxeles. */
    float y;

    /** Anchura del pop-up en píxeles. */
    float w;

    /** Altura del pop-up en píxeles. */
    float h;

    /** Título mostrado en la cabecera del pop-up. */
    String title;

    /** Mensaje descriptivo mostrado en el cuerpo del pop-up. */
    String message;

    /** Botón de confirmación de la acción destructiva (etiqueta «Eliminar»). */
    public Button bAceptar;

    /** Botón de cancelación que cierra el pop-up sin realizar ninguna acción. */
    public Button bCancelar;

    /** Anchura de cada botón de acción en píxeles. */
    float buttonW = 200;

    /** Altura de cada botón de acción en píxeles. */
    float buttonH = 60;

    /** Indica si el pop-up está actualmente visible ({@code true}) u oculto ({@code false}). */
    public boolean visible = false;

    /**
     * Crea un nuevo {@code PopUp} con el título, mensaje, posición y dimensiones indicados.
     * Los botones se posicionan automáticamente en la parte inferior del recuadro.
     *
     * @param p5        instancia de Processing necesaria para crear los botones
     * @param appColors paleta de colores de la aplicación
     * @param title     texto del título del pop-up
     * @param message   texto del mensaje descriptivo
     * @param x         posición horizontal en píxeles
     * @param y         posición vertical en píxeles
     * @param w         anchura del pop-up en píxeles
     * @param h         altura del pop-up en píxeles
     */
    public PopUp(PApplet p5, Colors appColors, String title, String message, float x, float y, float w, float h) {
        this.title = title;
        this.message = message;
        this.x = x; this.y = y;
        this.w = w; this.h = h;
        float bY = y + h - buttonH * 1.5f;
        this.bAceptar  = new Button(p5, appColors, "Eliminar", x + w/2 - buttonW - 10, bY, buttonW, buttonH);
        this.bCancelar = new Button(p5, appColors, "Cancelar", x + w/2 + 10,           bY, buttonW, buttonH);
    }

    /**
     * Muestra u oculta el pop-up y habilita o deshabilita sus botones en consecuencia.
     *
     * @param b {@code true} para hacer visible el pop-up; {@code false} para ocultarlo
     */
    public void setVisible(boolean b) {
        this.visible = b;
        bAceptar.setEnabled(b);
        bCancelar.setEnabled(b);
    }

    /**
     * Renderiza el pop-up en pantalla si {@link #visible} es {@code true}.
     * Dibuja el fondo oscuro redondeado, una línea separadora bajo el título,
     * el título en naranja, el mensaje en blanco centrado y los dos botones de acción.
     *
     * @param p5 instancia de Processing usada para el dibujado
     */
    public void display(PApplet p5) {
        if (!visible) return;
        float b = 40;
        p5.pushStyle();
        p5.stroke(0); p5.strokeWeight(2); p5.fill(40, 40, 40);
        p5.rect(x, y, w, h, b / 2);
        p5.line(x, y + 2*b, x + w, y + 2*b);
        p5.fill(255, 100, 0); p5.textSize(38); p5.textAlign(p5.LEFT);
        p5.text(title, x + b, y + 1.4f * b);
        p5.fill(255); p5.textSize(24); p5.textAlign(p5.CENTER);
        p5.text(message, x + w/2, y + 4*b);
        bAceptar.display(p5);
        bCancelar.display(p5);
        p5.popStyle();
    }
}