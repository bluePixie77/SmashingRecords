package gui.smashRecPantallas;

import processing.core.PApplet;
/**
 * Casilla de verificación cuadrada simple para la interfaz gráfica.
 * Muestra un cuadrado que puede estar marcado (con una cruz interior) o desmarcado,
 * y responde a eventos del ratón. Se usa para selecciones múltiples, como la elección
 * de géneros musicales en los formularios de la aplicación.
 *
 * @author Equipo SmashRecords
 * @version 1.0
 */
public class CheckBox {
    /** Posición horizontal de la esquina superior izquierda en píxeles. */
    public int x;

    /** Posición vertical de la esquina superior izquierda en píxeles. */
    public int y;

    /** Tamaño del lado del cuadrado en píxeles (se usa tanto de ancho como de alto). */
    public int w;

    /** Color de fondo del checkbox cuando no está marcado (blanco por defecto). */
    int bgColor;

    /** Color del borde del checkbox (negro por defecto). */
    int borderColor;

    /** Color de relleno y de la cruz cuando el checkbox está marcado (gris por defecto). */
    int checkedColor;

    /** Estado actual del checkbox: {@code true} marcado, {@code false} desmarcado. */
    boolean checked;

    /**
     * Crea un nuevo {@code CheckBox} desmarcado en la posición y con el tamaño indicados.
     * Los colores se inicializan a blanco (fondo), negro (borde) y gris (marcado).
     *
     * @param p5 instancia de Processing necesaria para crear los colores
     * @param x  posición horizontal en píxeles
     * @param y  posición vertical en píxeles
     * @param w  tamaño del lado del cuadrado en píxeles
     */
    // Constructor
    public CheckBox(PApplet p5, int x, int y, int w){
        this.x = x;
        this.y = y;
        this.w = w;
        this.checked = false;
        this.bgColor = p5.color(255);
        this.borderColor = p5.color(0);
        this.checkedColor = p5.color(180);
    }

    /**
     * Indica si el checkbox está actualmente marcado.
     *
     * @return {@code true} si está marcado; {@code false} en caso contrario
     */
    public boolean isChecked(){
        return  this.checked;
    }

    /**
     * Dibuja el checkbox en pantalla.
     * Renderiza el cuadrado con el color correspondiente a su estado y, si está marcado,
     * superpone una cruz formada por dos diagonales del cuadrado.
     *
     * @param p5 instancia de Processing usada para el dibujado
     */
    // Dibuixa el CheckBox
    public void display(PApplet p5){
        p5.pushStyle();
        p5.stroke(borderColor);
        p5.strokeWeight(2);

        if(this.checked){
            p5.fill(checkedColor);
        }
        else{
            p5.fill(bgColor);
        }
        p5.rect(x, y, w, w);

        if(this.checked){
            p5.line(x, y, x + w, y + w);
            p5.line(x, y+w, x + w, y);
        }
        p5.popStyle();
    }

    /**
     * Establece el estado del checkbox.
     *
     * @param b {@code true} para marcar el checkbox; {@code false} para desmarcarlo
     */
    public void setChecked(boolean b){
        this.checked = b;
    }

    /**
     * Alterna el estado del checkbox entre marcado y desmarcado.
     */
    // Canvia l'estat de selecció
    public void toggle(){
        this.checked = ! this.checked;
    }

    /**
     * Indica si el cursor del ratón se encuentra dentro del área cuadrada del checkbox.
     *
     * @param p5 instancia de Processing para obtener la posición del ratón
     * @return {@code true} si el ratón está sobre el checkbox; {@code false} en caso contrario
     */
    // Mira si el ratolí està sobre el checkbox
    public boolean onMouseOver(PApplet p5){
        return  p5.mouseX>= this.x &&
                p5.mouseX<= this.x + this.w &&
                p5.mouseY>= this.y &&
                p5.mouseY<= this.y + this.w;
    }
}