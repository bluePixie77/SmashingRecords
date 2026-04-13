package gui.smashRecPantallas;

import gui.smashRecColors.Colors;
import processing.core.PApplet;
import processing.core.PImage;

/**
 * Botón circular con icono para la interfaz gráfica de la aplicación.
 * Dibuja una elipse con un icono superpuesto y cambia de color en función de
 * si está habilitado, deshabilitado o el cursor del ratón está sobre él.
 *
 * @author Equipo SmashRecords
 * @version 1.0
 */
public class RoundButton {

    /** Posición horizontal del centro del botón en píxeles. */
    float x;

    /** Posición vertical del centro del botón en píxeles. */
    float y;

    /** Radio del botón circular en píxeles. */
    float r;

    /** Color de relleno del botón en estado normal. */
    public int fillColor;

    /** Color del contorno del botón. */
    public int strokeColor;

    /** Color de relleno cuando el cursor está sobre el botón. */
    public int fillColorOver;

    /** Color de relleno cuando el botón está deshabilitado. */
    public int fillColorDisabled;

    /** Imagen o icono que se muestra en el interior del botón. */
    PImage icona;

    /** Indica si el botón está habilitado ({@code true}) o deshabilitado ({@code false}). */
    public boolean enabled;

    /**
     * Crea un nuevo {@code RoundButton} con imagen, posición, radio y colores de la paleta.
     *
     * @param p5        instancia de Processing necesaria para inicializar los colores
     * @param appColors paleta de colores de la aplicación (se reinicializa internamente)
     * @param img       imagen o icono que se mostrará en el centro del botón
     * @param x         posición horizontal del centro del botón en píxeles
     * @param y         posición vertical del centro del botón en píxeles
     * @param r         radio del botón en píxeles
     */
    // Constructor
    public RoundButton(PApplet p5, Colors appColors, PImage img, float x, float y, float r){
        this.icona = img;
        this.x = x;
        this.y = y;
        this.r = r;
        this.enabled = true;
        appColors = new Colors(p5);
        this.setImage(img);
        this.setColors(appColors);
    }

    /**
     * Establece la imagen o icono del botón.
     *
     * @param img nueva imagen a mostrar en el centro del botón
     */
    // Setters
    public void setImage(PImage img){ this.icona = img; }

    /**
     * Habilita o deshabilita el botón.
     *
     * @param b {@code true} para habilitar el botón; {@code false} para deshabilitarlo
     */
    public void setEnabled(boolean b){
        this.enabled = b;
    }

    /**
     * Asigna los colores del botón a partir de la paleta de la aplicación.
     *
     * @param appColors paleta de colores de la aplicación
     */
    public void setColors(Colors appColors){
        this.fillColor = appColors.getSecondColor();
        this.fillColorOver = appColors.getFirstColor();
        this.fillColorDisabled = appColors.getThirdColor();
        this.strokeColor = appColors.getFourthColor();
    }

    /**
     * Renderiza el botón circular en pantalla.
     * El color de relleno varía según el estado: deshabilitado, con cursor encima o normal.
     * Sobre la elipse se dibuja la imagen {@link #icona} centrada.
     *
     * @param p5 instancia de Processing usada para el dibujado
     */
    // Dibujar el botón
    public void display(PApplet p5){
        p5.pushStyle();
        if(!enabled){
            p5.fill(fillColorDisabled);  // Color desabilitado
        }
        else if(mouseOverButton(p5)){
            p5.fill(fillColorOver);      // Color ratón sobre
        }
        else{
            p5.fill(fillColor);          // Color activo pero sin ratón sobre
        }
        p5.stroke(strokeColor); p5.strokeWeight(2);              // Color y grosor del contorno
        p5.ellipse(this.x, this.y, 2*this.r, 2*this.r);    // Círculo del botón

        // Imagen del botón
        p5.imageMode(p5.CENTER);
        p5.image(this.icona, this.x, this.y, 2*this.r, 2*this.r);
        p5.popStyle();
    }

    /**
     * Indica si el cursor del ratón se encuentra dentro del área circular del botón.
     *
     * @param p5 instancia de Processing para obtener la posición del ratón
     * @return {@code true} si la distancia del cursor al centro es menor o igual al radio;
     *         {@code false} en caso contrario
     */
    // Cursor sobre botón
    public boolean mouseOverButton(PApplet p5){
        return p5.dist(p5.mouseX, p5.mouseY, this.x, this.y)<= this.r;
    }

    /**
     * Indica si el cursor debe cambiar a mano ({@code HAND}) al estar sobre el botón.
     * Solo devuelve {@code true} si el cursor está sobre el botón y este está habilitado.
     *
     * @param p5 instancia de Processing para obtener la posición del ratón
     * @return {@code true} si se debe mostrar el cursor de mano; {@code false} en caso contrario
     */
    // Cursor a HAND
    public boolean updateHandCursor(PApplet p5){
        return mouseOverButton(p5) && enabled;
    }
}

