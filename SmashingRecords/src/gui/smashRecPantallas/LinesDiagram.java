package gui.smashRecPantallas;

import processing.core.PApplet;
/**
 * Diagrama de líneas que extiende {@link StatsCard}.
 * Representa la evolución de un conjunto de valores a lo largo de una serie
 * (p. ej. número de álbumes por año) mediante segmentos que unen puntos de datos.
 *
 * <p>Los ejes se dibujan automáticamente con márgenes calculados a partir de las
 * dimensiones de la tarjeta. Cada punto se acompaña de su etiqueta textual en el
 * eje X y de su valor numérico sobre el propio punto.</p>
 *
 * @author Equipo SmashRecords
 * @version 1.0
 */
public class LinesDiagram extends StatsCard{
    /** Etiquetas del eje X (p. ej. años o meses). */
    String[] texts;

    /** Valores numéricos de cada punto de la serie. */
    float[] values;

    /** Color de las líneas y los puntos del diagrama. */
    int colorLines;

    /** Valor máximo de la serie, usado para escalar el eje Y. */
    float maxValue;

    /**
     * Crea un nuevo {@code LinesDiagram} con el título y las dimensiones indicadas.
     *
     * @param title título del diagrama mostrado en la cabecera de la tarjeta
     * @param x     posición horizontal en píxeles
     * @param y     posición vertical en píxeles
     * @param w     anchura de la tarjeta en píxeles
     * @param h     altura de la tarjeta en píxeles
     */
    // Constructor
    public LinesDiagram(String title, float x, float y, float w, float h) {
        super(title, "Estadística lineal", x, y, w, h);
    }

    /**
     * Establece las etiquetas del eje X del diagrama.
     *
     * @param t array de cadenas con las etiquetas de cada punto
     */
    // Setters
    public void setTexts(String[] t){
        this.texts = t;
    }

    /**
     * Establece los valores de la serie y calcula automáticamente el valor máximo,
     * que se usará para escalar proporcionalmente la altura de cada punto.
     *
     * @param v array de valores flotantes que representan la serie de datos
     */
    public void setValues(float[] v){
        this.values = v;
        this.maxValue = this.values[0];
        for(int i=0; i<values.length; i++){
            if(this.values[i]>this.maxValue){
                maxValue = this.values[i];
            }
        }

    }

    /**
     * Establece el color de las líneas y los puntos del diagrama.
     *
     * @param c color en formato Processing ({@code color()})
     */
    public void setColors(int c){
        this.colorLines = c;
    }

    /**
     * Dibuja el diagrama de líneas sobre la tarjeta.
     *
     * <p>El proceso de dibujado sigue estos pasos:</p>
     * <ol>
     *   <li><strong>Márgenes y área útil:</strong> se definen márgenes izquierdo/derecho,
     *       superior e inferior para calcular el origen de los ejes y las dimensiones
     *       del área de dibujado.</li>
     *   <li><strong>Ejes:</strong> se dibujan el eje Y (vertical) y el eje X (horizontal)
     *       como líneas negras desde el origen calculado.</li>
     *   <li><strong>Bucle de puntos y segmentos:</strong> para cada par de puntos consecutivos
     *       se calcula su posición mapeando el valor al alto útil con {@code PApplet.map()},
     *       se dibuja la línea de conexión, el cuadrado indicador del punto {@code i},
     *       la etiqueta del eje X y el valor numérico sobre el punto. Al llegar al último
     *       par, se dibuja también el punto y la etiqueta del último elemento.</li>
     * </ol>
     *
     * @param p5 instancia de Processing usada para el dibujado
     */
    // Dibujar el Diagrama de Sectors
    public void displayDiagram(PApplet p5){
        p5.pushStyle();
        // 1. DEFINIR MÁRGENES (Ajusta estos valores para cambiar el tamaño)
        float marginLR = 60;   // Margen izquierdo (para que el eje Y no pegue al borde)
        float marginT = 100;   // Margen superior (espacio para el título)
        float marginB = 70;   // Margen inferior (espacio para etiquetas de texto)

        // 2. CALCULAR ÁREA ÚTIL
        float xEje = this.x + marginLR;
        float yEje = this.y + this.h - marginB;
        float anchoGrafico = this.w - marginLR - marginLR;
        float altoGrafico = this.h - marginT - marginB;

        // 3. DIBUJAR EJES
        p5.stroke(0); p5.strokeWeight(2);
        p5.line(xEje, yEje, xEje, this.y + marginT);    // Eje Y
        p5.line(xEje, yEje, xEje + anchoGrafico, yEje); // Eje X

        float widthStep = anchoGrafico / (float) (this.values.length - 1);

        for(int i=0; i < this.values.length - 1; i++){

            // Posición Punto i (mapeado al altoGrafico)
            float x1 = xEje + widthStep * i;
            float y1 = yEje - p5.map(this.values[i], 0, maxValue, 0, altoGrafico);

            // Posición Punto i+1
            float x2 = xEje + widthStep * (i + 1);
            float y2 = yEje - p5.map(this.values[i+1], 0, maxValue, 0, altoGrafico);

            // Línea entre puntos
            p5.stroke(colorLines); p5.strokeWeight(3);
            p5.line(x1, y1, x2, y2);

            // Punto (Cuadradito) i
            p5.noStroke(); p5.fill(colorLines); p5.rectMode(p5.CENTER);
            p5.rect(x1, y1, 8, 8);

            // Etiquetas de Texto (Años/Meses)
            p5.fill(0); p5.textAlign(p5.CENTER); p5.textSize(16);
            p5.text(this.texts[i], x1, yEje + 30);

            // Valores numéricos sobre los puntos
            p5.textSize(14);
            p5.text((int)this.values[i], x1, y1 - 15);

            // Dibujar el último punto y etiqueta cuando lleguemos al final
            if(i + 1 == this.values.length - 1){
                p5.text(this.texts[i+1], x2, yEje + 30);
                p5.text((int)this.values[i+1], x2, y2 - 15);
                p5.fill(colorLines);
                p5.rect(x2, y2, 8, 8);
            }
        }
        p5.popStyle();
    }
}