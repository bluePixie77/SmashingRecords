package gui.smashRecPantallas;

import processing.core.PApplet;

import static processing.core.PApplet.cos;
import static processing.core.PApplet.sin;
import static processing.core.PConstants.TWO_PI;

public
class SectorDiagram extends StatsCard{

    // Dimensions del Diagrama de Sectors
    float r;

    // Informació del diagrama (textos, valors i colors)
    String[] texts;
    float[] values;
    float[] percentages;
    int[] colors;

    // Suma total dels valors
    float total;

    // Constructor
    public SectorDiagram(String title, float x, float y, float w, float h) {
        super(title, "Estadística de sectores", x, y, w, h);
        this.r = h / 2.8f;
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
            this.percentages[i] = (this.values[i] / this.total)*100f;
        }
    }

    public void setColors(int[] c){
        this.colors = c;
    }

    // Dibuixa el Diagrama de Sectors

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
