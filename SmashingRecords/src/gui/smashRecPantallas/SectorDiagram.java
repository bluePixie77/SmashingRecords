package gui.smashRecPantallas;

import processing.core.PApplet;

import static processing.core.PApplet.cos;
import static processing.core.PApplet.sin;
import static processing.core.PConstants.TWO_PI;

/**
 * Diagrama de sectores (gráfico circular o «tarta») que extiende {@link StatsCard}.
 * Representa visualmente la distribución proporcional de un conjunto de valores
 * mediante sectores de ángulo proporcional a su peso respecto al total.
 *
 * <p>Cada sector muestra, en su interior, el porcentaje que representa, y en el
 * exterior, la etiqueta textual asociada al valor.</p>
 *
 * @author Equipo SmashRecords
 * @version 1.0
 */
public class SectorDiagram extends StatsCard{

    /** Radio del círculo del diagrama, calculado a partir de la altura de la tarjeta. */
    float r;

    /** Etiquetas textuales asociadas a cada sector del diagrama. */
    String[] texts;

    /** Valores numéricos de cada sector, usados para calcular los ángulos. */
    float[] values;

    /** Porcentajes calculados de cada sector respecto al total (0–100). */
    float[] percentages;

    /** Colores asignados a cada sector del diagrama. */
    int[] colors;

    /** Suma total de todos los valores, usada para calcular proporciones. */
    float total;

    /**
     * Crea un nuevo {@code SectorDiagram} con el título y las dimensiones indicadas.
     * El radio se calcula automáticamente como {@code h / 2.8}.
     *
     * @param title título del diagrama mostrado en la cabecera de la tarjeta
     * @param x     posición horizontal en píxeles
     * @param y     posición vertical en píxeles
     * @param w     anchura de la tarjeta en píxeles
     * @param h     altura de la tarjeta en píxeles
     */
    // Constructor
    public SectorDiagram(String title, float x, float y, float w, float h) {
        super(title, "Estadística de sectores", x, y, w, h);
        this.r = h / 2.8f;
    }

    /**
     * Establece las etiquetas textuales de cada sector.
     *
     * @param t array de cadenas con las etiquetas (debe tener la misma longitud que los valores)
     */
    // Setters
    public void setTexts(String[] t){
        this.texts = t;
    }

    /**
     * Establece los valores numéricos del diagrama y calcula automáticamente el total
     * y los porcentajes de cada sector.
     *
     * @param v array de valores flotantes; cada elemento representa el peso de un sector
     */
    public void setValues(float[] v){
        this.values = v;
        this.total = 0;
        for(int i=0; i<values.length; i++){
            this.total += this.values[i];
        }

        this.percentages = new float[values.length];
        for(int i=0; i<percentages.length; i++){
            this.percentages[i] = (this.values[i] / this.total)*100f;
        }
    }

    /**
     * Establece los colores de cada sector del diagrama.
     *
     * @param c array de enteros con los colores en formato Processing ({@code color()})
     */
    public void setColors(int[] c){
        this.colors = c;
    }

    /**
     * Dibuja el diagrama de sectores sobre la tarjeta.
     *
     * <p>El proceso se divide en dos bucles independientes:</p>
     * <ol>
     *   <li><strong>Primer bucle — sectores:</strong> para cada valor se calcula el ángulo
     *       proporcional ({@code valor/total * TWO_PI}) y se dibuja un arco relleno con el
     *       color correspondiente, acumulando el ángulo de inicio para el siguiente sector.</li>
     *   <li><strong>Segundo bucle — textos:</strong> recalculando los mismos ángulos, se
     *       obtiene el ángulo medio de cada sector y se usan las funciones trigonométricas
     *       {@code cos} y {@code sin} para posicionar:
     *       <ul>
     *         <li>La etiqueta del sector ({@link #texts}), a una distancia de {@code r + 30}
     *             del centro.</li>
     *         <li>El porcentaje formateado, a una distancia de {@code r * 0.75} del centro,
     *             en color blanco para contrastar con el relleno del sector.</li>
     *       </ul>
     *   </li>
     * </ol>
     * <p>El centro del diagrama se calcula como el centro geométrico de la tarjeta,
     * desplazado 20 px hacia abajo para compensar el espacio del título.</p>
     *
     * @param p5 instancia de Processing usada para el dibujado
     */
    public void displayDiagram(PApplet p5){
        p5.pushStyle();

        p5.ellipseMode(p5.CENTER);

        // Centro Card
        float centroX = this.x + (this.w / 2f);
        float centroY = this.y + (this.h / 2f)+20;

        // PRIMER BUCLE (Sectores)
        float angStart = 0;
        for(int i=0; i<this.values.length; i++){
            float sectorValue = (this.values[i] / this.total)*TWO_PI;
            float angEnd = angStart + sectorValue;

            p5.fill(colors[i]);
            p5.stroke(0);
            p5.strokeWeight(5);
            p5.arc(centroX, centroY, 2*this.r, 2*this.r, angStart, angEnd);

            angStart = angEnd;
        }

        // SEGUNDO BUCLE (Textos)
        // Reiniciamos angStart para calcular las mismas posiciones de texto
        angStart = 0;
        for(int i=0; i<this.values.length; i++){
            float sectorValue = (this.values[i] / this.total)*TWO_PI;
            float angEnd = angStart + sectorValue;
            float middleAngle = (angStart + angEnd) / 2f;

            // Textos de las etiquetas
            float textX = centroX + (this.r + 30) * cos(middleAngle);
            float textY = centroY + (this.r + 30) * sin(middleAngle);
            p5.fill(0);
            p5.textAlign(p5.CENTER);
            p5.textSize(24);
            p5.text(this.texts[i], textX, textY);

            // Porcentajes
            float percX = centroX + (this.r * 0.75f) * cos(middleAngle);
            float percY = centroY + (this.r * 0.75f) * sin(middleAngle);
            String percentage = p5.nf(this.percentages[i], 2, 2);
            p5.fill(255);
            p5.textAlign(p5.CENTER);
            p5.textSize(18);
            p5.text(percentage + "%", percX, percY);

            angStart = angEnd;
        }
        p5.popStyle();
    }
}
