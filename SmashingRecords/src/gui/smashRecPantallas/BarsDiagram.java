package gui.smashRecPantallas;

import processing.core.PApplet;

/**
 * Diagrama de barras verticales que extiende {@link StatsCard}.
 * Muestra la distribución de un conjunto de valores mediante barras proporcionales
 * a su peso respecto al total, con etiquetas en el eje X, porcentajes y valores
 * numéricos sobre cada barra.
 *
 * @author Equipo SmashRecords
 * @version 1.0
 */
public class BarsDiagram extends StatsCard {

    /** Etiquetas del eje X, una por cada barra (p. ej. nombre del género). */
    String[] texts;

    /** Valores numéricos de cada barra. */
    float[] values;

    /** Porcentajes calculados de cada barra respecto al total (0–100). */
    float[] percentages;

    /** Colores asignados a cada barra del diagrama. */
    int[] colors;

    /** Suma total de todos los valores, usada para calcular proporciones. */
    float total;

    /**
     * Crea un nuevo {@code BarsDiagram} con el título y las dimensiones indicadas.
     *
     * @param title título del diagrama mostrado en la cabecera de la tarjeta
     * @param x     posición horizontal en píxeles
     * @param y     posición vertical en píxeles
     * @param w     anchura de la tarjeta en píxeles
     * @param h     altura de la tarjeta en píxeles
     */
    public BarsDiagram(String title, float x, float y, float w, float h) {
        super(title, "Estadística de Barras", x, y, w, h);
    }

    /**
     * Establece las etiquetas del eje X del diagrama.
     *
     * @param t array de cadenas con las etiquetas de cada barra
     */
    // Setters
    public void setTexts(String[] t){
        this.texts = t;
    }

    /**
     * Establece los valores del diagrama y calcula automáticamente el total
     * y el porcentaje de cada barra respecto a ese total.
     *
     * @param v array de valores flotantes, uno por cada barra
     */
    public void setValues(float[] v){
        this.values = v;
        this.total = 0;
        for(int i=0; i<values.length; i++){
            this.total += this.values[i];
        }

        this.percentages = new float[values.length];
        for(int i=0; i<percentages.length; i++){
            this.percentages[i] = (this.values[i] / this.total)*100;
        }
    }

    /**
     * Establece los colores de cada barra del diagrama.
     *
     * @param c array de enteros con los colores en formato Processing ({@code color()})
     */
    public void setColors(int[] c){
        this.colors = c;
    }

    /**
     * Dibuja el diagrama de barras sobre la tarjeta.
     *
     * <p>El proceso de dibujado sigue estos pasos:</p>
     * <ol>
     *   <li><strong>Márgenes y área útil:</strong> se definen márgenes izquierdo/derecho,
     *       superior e inferior para delimitar el área de dibujado.</li>
     *   <li><strong>Valor máximo:</strong> se recorre el array de valores para encontrar
     *       el máximo, que se usa para escalar proporcionalmente la altura de las barras.</li>
     *   <li><strong>Bucle de barras:</strong> para cada valor se calcula la altura de la
     *       barra mapeándola al alto útil con {@code PApplet.map()}, y se dibujan:
     *       <ul>
     *         <li>La barra rellena con el color correspondiente (usando módulo para
     *             evitar desbordamiento del array).</li>
     *         <li>La etiqueta del eje X centrada bajo la barra.</li>
     *         <li>El porcentaje formateado y el valor entero sobre la barra.</li>
     *       </ul>
     *   </li>
     *   <li><strong>Total:</strong> se muestra el total acumulado de géneros marcados
     *       en la esquina superior derecha del área útil.</li>
     * </ol>
     *
     * @param p5 instancia de Processing usada para el dibujado
     */
    // Dibujo Diagrama de Sectores
    public void displayDiagram(PApplet p5){
        p5.pushStyle();

        // 1. DEFINIR MÁRGENES (Iguales a LinesDiagram para coherencia visual)
        float marginLR = 60;   // Margen izquierdo y derecho
        float marginT = 110;    // Margen superior (espacio título)
        float marginB = 70;    // Margen inferior (espacio etiquetas)

        // 2. CALCULAR ÁREA ÚTIL
        float xInicio = this.x + marginLR;
        float yBase = this.y + this.h - marginB; // Suelo del gráfico
        float anchoGrafico = this.w - (marginLR * 2);
        float altoGrafico = this.h - marginT - marginB;

        // Calculamos el ancho de cada barra dentro del área útil
        float widthBar = anchoGrafico / (float) this.values.length;

        // Buscamos el valor máximo para que la barra más alta no toque el título
        float maxVal = 0;
        for(float v : values) if(v > maxVal) maxVal = v;

        for(int i=0; i<this.values.length; i++){
            // Mapeamos el valor de la barra al alto útil del gráfico
            float barHeight = p5.map(this.values[i], 0, maxVal, 0, altoGrafico);
            float xBar = xInicio + widthBar * i;

            // Dibujar la barra
            p5.fill(colors[i % colors.length]);
            p5.stroke(0);
            p5.strokeWeight(2); // Reducido para que se vea más limpio

            // Rect se dibuja: x, y (superior), ancho, alto
            p5.rect(xBar + 5, yBase - barHeight, widthBar - 10, barHeight);

            // Etiquetas de Texto (Eje X)
            float textX = xBar + widthBar/2;
            p5.fill(0); p5.textAlign(p5.CENTER); p5.textSize(16);
            p5.text(this.texts[i], textX, yBase + 30);

            // Porcentaje y Valor (sobre la barra)
            String percentage = p5.nf(this.percentages[i], 1, 1);
            p5.textSize(14);
            p5.text(percentage + "%", textX, yBase - barHeight - 35);

            p5.textSize(16);
            p5.text((int)this.values[i], textX, yBase - barHeight - 15);
        }

        // Dibujar el total en una esquina del área útil
        p5.fill(p5.color(0xFF646464)); p5.textSize(14); p5.textAlign(p5.RIGHT);
        p5.text("TOTAL GÉNEROS MARCADOS: " + (int)total, xInicio + anchoGrafico, this.y + 35);

        p5.popStyle();
    }
}