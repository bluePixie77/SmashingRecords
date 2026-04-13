package gui.smashRecPantallas;

import processing.core.PApplet;

import static gui.smashRecFonts.Sizes.medidaParrafo;
/**
 * Botón de opción individual (radio button) para la interfaz gráfica.
 * Representa visualmente un círculo que puede estar marcado o desmarcado,
 * con una etiqueta de texto opcional a su derecha. Forma parte de un
 * {@link RadioButtonGroup} que gestiona la selección única entre varios botones.
 *
 * @author Equipo SmashRecords
 * @version 1.0
 */
public class RadioButton {

    /** Posición horizontal del centro del botón en píxeles. */
    int x;

    /** Posición vertical del centro del botón en píxeles. */
    int y;

    /** Radio del círculo del botón en píxeles. */
    int r;

    /** Color de fondo del botón (blanco por defecto). */
    int bgColor;

    /** Color del borde del botón (negro por defecto). */
    int borderColor;

    /** Color del indicador interior cuando el botón está marcado (gris por defecto). */
    int checkedColor;

    /** Indica si el botón está actualmente marcado ({@code true}) o no ({@code false}). */
    boolean checked;

    /** Etiqueta textual que se muestra a la derecha del botón. Puede ser {@code null}. */
    String text;

    /**
     * Crea un nuevo {@code RadioButton} desmarcado en la posición y con el radio indicados.
     * Los colores se inicializan a blanco (fondo), negro (borde) y gris (#646464, marcado).
     *
     * @param p5 instancia de Processing necesaria para crear los colores
     * @param x  posición horizontal del centro en píxeles
     * @param y  posición vertical del centro en píxeles
     * @param r  radio del botón en píxeles
     */
        // Constructor
        public RadioButton(PApplet p5, int x, int y, int r){
            this.x = x;
            this.y = y;
            this.r = r;
            this.checked = false;
            this.bgColor = p5.color(255); // blanco
            this.borderColor = p5.color(0); // negro
            this.checkedColor = p5.color(0xFF646464); // gris
        }

    /**
     * Indica si el botón está actualmente marcado.
     *
     * @return {@code true} si está marcado; {@code false} en caso contrario
     */
        public  boolean isChecked(){
            return  this.checked;
        }

    /**
     * Asigna la etiqueta textual que se mostrará a la derecha del botón.
     *
     * @param t texto de la etiqueta
     */
        public void setText(String t){ this.text = t; }


    /**
     * Renderiza el botón de opción en pantalla.
     * Dibuja el círculo exterior y, si está marcado, un círculo interior de menor tamaño
     * con el color indicador. Si se ha definido una etiqueta ({@link #text}), la muestra
     * alineada a la izquierda y centrada verticalmente respecto al botón.
     *
     * @param p5 instancia de Processing usada para el dibujado
     */
        // Dibujar el RadioButton
        public void display(PApplet p5){
            p5.pushStyle();
            p5.stroke(borderColor);
            p5.strokeWeight(2);
            p5.fill(bgColor);
            p5.ellipse(x, y, 2*r, 2*r);

            if(this.checked){
                p5.fill(checkedColor); p5.noStroke();
                p5.ellipse(x, y, 1.5f*r, 1.5f*r);
            }

            if(this.text!=null) {
                p5.fill(bgColor); p5.textAlign(p5.LEFT, p5.CENTER); p5.textSize(medidaParrafo);
                p5.text(this.text, this.x + 1.5f * (2*r), this.y - 2);
            }

            p5.popStyle();
        }

    /**
     * Establece el estado de marcado del botón.
     *
     * @param b {@code true} para marcar el botón; {@code false} para desmarcarlo
     */
        public void setChecked(boolean b){
            this.checked = b;
        }

    /**
     * Alterna el estado de marcado del botón entre marcado y desmarcado.
     */
        // Cambia el estado de selección
        public void toggle(){
            this.checked = ! this.checked;
        }

    /**
     * Indica si el cursor del ratón se encuentra dentro del área circular del botón.
     *
     * @param p5 instancia de Processing para obtener la posición del ratón
     * @return {@code true} si la distancia del cursor al centro es menor que el radio;
     *         {@code false} en caso contrario
     */
        // Ratón sobre radioButton
        public boolean onMouseOver(PApplet p5){
            return  p5.dist(p5.mouseX, p5.mouseY, this.x, this.y) < this.r;
        }
}
