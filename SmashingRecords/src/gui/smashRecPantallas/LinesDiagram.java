package gui.smashRecPantallas;

import processing.core.PApplet;

public class LinesDiagram extends StatsCard{
    // Dimensions del diagrama de Barres

    // Informació del diagrama (textos, valors i colors)
    String[] texts;
    float[] values;
    int colorLines;
    float maxValue;

    // Constructor

    public LinesDiagram(String title, float x, float y, float w, float h) {
        super(title, "Estadística lineal", x, y, w, h);
    }

    // Setters

    public void setTexts(String[] t){
        this.texts = t;
    }

    public void setValues(float[] v){
        this.values = v;
        this.maxValue = this.values[0];
        for(int i=0; i<values.length; i++){
            if(this.values[i]>this.maxValue){
                maxValue = this.values[i];
            }
        }

    }

    public void setColors(int c){
        this.colorLines = c;
    }

    // Dibuixa el Diagrama de Sectors

    public void displayDiagram(PApplet p5){
        p5.pushStyle();
        // 1. DEFINIR MÁRGENES (Ajusta estos valores para cambiar el tamaño)
        float marginLR = 60;   // Margen izquierdo (para que el eje Y no pegue al borde)
        float marginT = 80;   // Margen superior (espacio para el título)
        float marginB = 80;   // Margen inferior (espacio para etiquetas de texto)

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