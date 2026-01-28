package gui.smashRecPantallas;

import processing.core.PApplet;

public class BarsDiagram extends StatsCard {

    // Dimensions del diagrama de Barres

    // Informació del diagrama (textos, valors i colors)
    String[] texts;
    float[] values;
    float[] percentages;
    int[] colors;

    // Suma total dels valors
    float total;

    // Constructor

    public BarsDiagram(String title, float x, float y, float w, float h) {
        super(title, "Estadística de Barras", x, y, w, h);
    }

    // Setters
    public void setTexts(String[] t){
        this.texts = t;
    }

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

    public void setColors(int[] c){
        this.colors = c;
    }

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
        p5.fill(100); p5.textSize(14); p5.textAlign(p5.RIGHT);
        p5.text("TOTAL: " + (int)total, xInicio + anchoGrafico, this.y + 35);

        p5.popStyle();
    }
}