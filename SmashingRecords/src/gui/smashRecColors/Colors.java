package gui.smashRecColors;

import processing.core.PApplet;
/**
 * Gestiona la paleta de colores de la aplicación The Smashing Records.
 * Almacena los siete colores corporativos y proporciona accesores individuales
 * y por índice, así como un método de visualización para depuración.
 *
 */
public class Colors {

    /** Array interno que almacena los colores de la paleta en formato Processing. */
    int[] colors; // más arrays para más grupos de colores

    /**
     * Crea una nueva instancia de {@code Colors} e inicializa la paleta de colores.
     *
     * @param p5 instancia de Processing necesaria para invocar {@code color()}
     */
    public Colors(PApplet p5){ this.setColors(p5); }

    /**
     * Inicializa el array de colores con los siete tonos corporativos de la aplicación.
     *
     * @param p5 instancia de Processing necesaria para invocar {@code color()}
     */
    // Setter (colores de la app)
    public void setColors(PApplet p5){
        this.colors = new int[7];
        this.colors[0] = p5.color(0xFFE1480B); // Naranja fuerte
        this.colors[1] = p5.color(0xFFE47738); // Naranja flojo
        this.colors[2] = p5.color(0xFFFFFFFF); // Blanco
        this.colors[3] = p5.color(0xFF020202); // Negro
        this.colors[4] = p5.color(0xFF646464); // Gris
        this.colors[5] = p5.color(0xFFFFC832); // Amarillo
        this.colors[6] = p5.color(0xFFE56399); // Rosa
    }

    /**
     * Devuelve el número total de colores de la paleta.
     * @return número de colores disponibles
     */
    // Getters
    public int getNumColors(){ return this.colors.length; }

    /** Devuelve @return Naranja fuerte (#E1480B), usado para elementos destacados y seleccionados. */
    public int getFirstColor(){ return this.colors[0]; }

    /** Devuelve @return Naranja suave (#E47738), usado para estados de hover y secundarios. */
    public int getSecondColor(){ return this.colors[1]; }

    /** Devuelve @return Blanco (#FFFFFF), usado para fondos claros y textos sobre fondo oscuro. */
    public int getThirdColor(){ return this.colors[2]; }

    /** Devuelve @return Negro (#020202), usado para fondos principales, bordes y textos. */
    public int getFourthColor(){ return this.colors[3]; }

    /** Devuelve @return Gris (#646464), usado para elementos deshabilitados y secundarios. */
    public int getFifthColor(){ return this.colors[4]; }

    /** Devuelve @return Amarillo (#FFC832), usado como color de acento en gráficos. */
    public int getSixthColor(){ return this.colors[5]; }

    /** Devuelve @return Rosa (#E56399), usado para mensajes de error y acentos especiales. */
    public int getSeventhColor(){ return this.colors[6]; }

    /**
     * Devuelve el color en la posición indicada del array de la paleta.
     * @param i índice del color (0–6)
     * @return color en formato Processing ({@code int} ARGB)
     */
    public int getColorAt(int i){ return this.colors[i]; }

    /**
     * Dibuja visualmente la paleta de colores en pantalla con una leyenda,
     * útil para depuración y previsualización del tema visual de la aplicación.
     * Los colores se muestran como rectángulos de igual anchura distribuidos horizontalmente.
     * @param p5 instancia de Processing usada para el dibujado
     * @param x  posición horizontal de inicio en píxeles
     * @param y  posición vertical de inicio en píxeles
     * @param w  anchura total ocupada por todos los rectángulos de color en píxeles
     */
    // Dibujar paleta de colores
    public void displayColors(PApplet p5, float x, float y, float w){
        p5.pushStyle();
            // Leyenda
            p5.fill(0); p5.textAlign(p5.LEFT); p5.textSize(36);
            p5.text("Paleta Colores: ", x, y-20);

            // Paleta de colores
            float wc = w / getNumColors();
            for(int i=0; i<getNumColors(); i++){
                p5.fill(getColorAt(i)); p5.stroke(0); p5.strokeWeight(3);
                p5.rect(x + i*wc, y, wc, wc);
            }
        p5.popStyle();
    }
}
