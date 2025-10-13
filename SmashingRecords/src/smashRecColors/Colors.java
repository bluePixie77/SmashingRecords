package smashRecColors;

import processing.core.PApplet;

public class Colors {

    int[] colors; // más arrays para más grupos de colores

    // Constructor
    public Colors(PApplet p5){ this.setColors(p5); }

    // Colores de la App (setter)
    void setColors(PApplet p5){
        this.colors = new int[4];
        this.colors[0] = p5.color(0xFF020202);
        this.colors[1] = p5.color(0xFFFFF0DC);
        this.colors[2] = p5.color(0xFFE47738);
        this.colors[3] = p5.color(0xFFE03C00);
    }

    // Getters
    public int getNumColors(){ return this.colors.length; }
    public int getFirstColor(){ return this.colors[0]; }
    public int getSecondColor(){ return this.colors[1]; }
    public int getThirdColor(){ return this.colors[2]; }
    public int getFourthColor(){ return this.colors[3]; }

    public int getColorAt(int i){ return this.colors[i]; }

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
