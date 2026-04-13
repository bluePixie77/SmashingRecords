package gui.smashRecPantallas;

import gui.smashRecColors.Colors;
import processing.core.PApplet;

/**
 * Botón rectangular interactivo para la interfaz gráfica de la aplicación.
 * Muestra un rectángulo redondeado con texto centrado y cambia de color
 * en función de si está habilitado, deshabilitado o el cursor se encuentra sobre él.
 *
 * @author Equipo SmashRecords
 * @version 1.0
 */
public class Button {
    /** Posición horizontal de la esquina superior izquierda del botón en píxeles. */
    public float x;

    /** Posición vertical de la esquina superior izquierda del botón en píxeles. */
    public float y;

    /** Anchura del botón en píxeles. */
    public float w;

    /** Altura del botón en píxeles. */
    public float h;

    /** Color de relleno del botón en estado normal. */
    int fillColor;

    /** Color del contorno del botón. */
    int strokeColor;

    /** Color de relleno cuando el cursor está sobre el botón. */
    int fillColorOver;

    /** Color de relleno cuando el botón está deshabilitado. */
    int fillColorDisabled;

    /** Texto que se muestra centrado en el botón. */
    String textBoto;

    /** Indica si el botón está habilitado ({@code true}) o deshabilitado ({@code false}). */
    public boolean enabled;

    /**
     * Crea un nuevo {@code Button} con el texto, posición, dimensiones y colores indicados.
     * El botón se crea habilitado por defecto.
     *
     * @param p5        instancia de Processing (requerida por los componentes base)
     * @param appColors paleta de colores de la aplicación
     * @param text      texto que se mostrará en el centro del botón
     * @param x         posición horizontal en píxeles
     * @param y         posición vertical en píxeles
     * @param w         anchura del botón en píxeles
     * @param h         altura del botón en píxeles
     */
    // Constructor
    public Button(PApplet p5, Colors appColors, String text, float x, float y, float w, float h){
        this.textBoto = text;
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.enabled = true;

        this.setColors(appColors);
    }

    /**
     * Habilita o deshabilita el botón.
     *
     * @param b {@code true} para habilitar el botón; {@code false} para deshabilitarlo
     */
    // Setters
    public void setEnabled(boolean b){
        this.enabled = b;
    }
    /**
     * Cambia el texto mostrado en el botón.
     *
     * @param t nuevo texto del botón
     */
    public void setTextBoto(String t){ this.textBoto = t; }

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
     * Indica si el botón está habilitado.
     *
     * @return {@code true} si el botón está habilitado; {@code false} en caso contrario
     */
    // Getters
    public boolean isEnabled(){
        return this.enabled;
    }

    /**
     * Renderiza el botón en pantalla.
     * El color de relleno varía según el estado: deshabilitado, con cursor encima o normal.
     * El texto se muestra centrado horizontalmente y verticalmente dentro del rectángulo.
     *
     * @param p5 instancia de Processing usada para el dibujado
     */
    // Dibuja el botó
    public void display(PApplet p5){
        p5.pushStyle();
        if(!enabled){
            p5.fill(fillColorDisabled);  // Color desabilitado
        }
        else if(mouseOverButton(p5)){
            p5.fill(fillColorOver);      // Color cuando el ratón está encima
        }
        else{
            p5.fill(fillColor);          // Color cuando ratón no está encima
        }
        //p5.stroke(strokeColor); p5.strokeWeight(2);      // Color y grosor del contorno
        p5.noStroke();
        p5.rectMode(p5.CORNER); p5.strokeJoin(p5.MITER);
        p5.rect(this.x, this.y, this.w, this.h, 10);    // Rectángulo del botón

        // Text (color, alineación y medida)
        p5.fill(0); p5.textAlign(p5.CENTER); p5.textSize(20);
        p5.text(textBoto, this.x + this.w/2, this.y + this.h/2 + 10);
        p5.popStyle();
    }

    /**
     * Indica si el cursor del ratón se encuentra dentro del área rectangular del botón.
     *
     * @param p5 instancia de Processing para obtener la posición del ratón
     * @return {@code true} si el ratón está sobre el botón; {@code false} en caso contrario
     */
    // Indica si el cursor está sobre el botón
    public boolean mouseOverButton(PApplet p5){
        return (p5.mouseX >= this.x) && (p5.mouseX <= this.x + this.w) &&
                (p5.mouseY >= this.y) && (p5.mouseY <= this.y + this.h);
    }

    /**
     * Indica si el cursor debe cambiar a mano ({@code HAND}) al estar sobre el botón.
     * Solo devuelve {@code true} si el cursor está sobre el botón y este está habilitado.
     *
     * @param p5 instancia de Processing para obtener la posición del ratón
     * @return {@code true} si se debe mostrar el cursor de mano; {@code false} en caso contrario
     */
    // Indica si poner cursor a HAND
    public boolean updateHandCursor(PApplet p5){
        return mouseOverButton(p5) && enabled;
    }
}

