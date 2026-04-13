
package gui.smashRecPantallas;

import processing.core.PApplet;
import processing.core.PImage;
/**
 * Casilla de verificación visual en forma de estrella.
 * Muestra una imagen de estrella activada o desactivada según su estado,
 * y forma parte de una {@link CheckBoxStarList} para componer una valoración por estrellas.
 *
 * @author Equipo SmashRecords
 * @version 1.0
 */
public class CheckBoxStar {
    /** Posición horizontal de la estrella en píxeles. */
    float x;

    /** Posición vertical de la estrella en píxeles. */
    float y;

    /** Anchura de la imagen de la estrella en píxeles. */
    float w;

    /** Altura de la imagen de la estrella en píxeles. */
    float h;

    /** Imagen mostrada cuando la estrella está activada. */
    PImage imgChecked;

    /** Imagen mostrada cuando la estrella está desactivada. */
    PImage imgNotChecked;

    /** Estado actual de la estrella: {@code true} activada, {@code false} desactivada. */
    boolean checked;

    /**
     * Crea una nueva {@code CheckBoxStar} en la posición y con las dimensiones indicadas.
     * El estado inicial es desactivado.
     *
     * @param x posición horizontal en píxeles
     * @param y posición vertical en píxeles
     * @param w anchura en píxeles
     * @param h altura en píxeles
     */
    // Constructor
    public CheckBoxStar(float x, float y, float w, float h){
        this.x = x; this.y = y;
        this.h = h; this.w = w;
        this.checked = false;
    }

    /**
     * Carga las imágenes de estrella activada y desactivada desde los archivos indicados.
     *
     * @param p5       instancia de Processing necesaria para cargar las imágenes
     * @param imgName1 ruta del archivo de imagen para el estado activado
     * @param imgName2 ruta del archivo de imagen para el estado desactivado
     */
    public void setImages(PApplet p5, String imgName1, String imgName2){
        this.imgChecked = p5.loadImage(imgName1);
        this.imgNotChecked = p5.loadImage(imgName2);
    }

    /**
     * Dibuja la estrella en pantalla mostrando la imagen correspondiente a su estado actual.
     *
     * @param p5 instancia de Processing usada para el dibujado
     */
    // Dibuja el CheckBox
    public void display(PApplet p5){

        p5.pushStyle();

        p5.imageMode(p5.CORNER);

        if(this.checked){
            p5.image(imgChecked, x, y, w, h);
        }
        else{
            p5.image(imgNotChecked, x, y, w, h);
        }

        p5.popStyle();
    }

    /**
     * Establece el estado de la estrella.
     *
     * @param b {@code true} para activar la estrella; {@code false} para desactivarla
     */
    public void setChecked(boolean b){
        this.checked = b;
    }

    /**
     * Alterna el estado de la estrella entre activado y desactivado.
     */
    // Cambia el estado
    public void toggle(){
        this.checked = ! this.checked;
    }

    /**
     * Indica si el cursor del ratón se encuentra dentro del área rectangular de la estrella.
     *
     * @param p5 instancia de Processing para obtener la posición del ratón
     * @return {@code true} si el ratón está sobre la estrella; {@code false} en caso contrario
     */
    // Ratón sobre checkBox
    public boolean onMouseOver(PApplet p5){
        return  p5.mouseX>= this.x &&
                p5.mouseX<= this.x + this.w &&
                p5.mouseY>= this.y &&
                p5.mouseY<= this.y + this.h;
    }
}
