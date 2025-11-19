package gui.smashRecColors;

import processing.core.PApplet;

public class Colors {

    int[] colors; // más arrays para más grupos de colores

    // Constructor
    public Colors(PApplet p5){ this.setColors(p5); }

    // Setter (colores de la app)
    public void setColors(PApplet p5){
        this.colors = new int[5];
        this.colors[0] = p5.color(0xFFE03C00); // Naranja fuerte
        this.colors[1] = p5.color(0xFFE47738); // Naranja flojo
        this.colors[2] = p5.color(255); // Blanco
        this.colors[3] = p5.color(0xFF020202); // Negro
        this.colors[4] = p5.color(100);        // Gris
    }

    // Getters
    public int getNumColors(){ return this.colors.length; }
    public int getFirstColor(){ return this.colors[0]; }
    public int getSecondColor(){ return this.colors[1]; }
    public int getThirdColor(){ return this.colors[2]; }
    public int getFourthColor(){ return this.colors[3]; }
    public int getFifthColor(){ return this.colors[4]; }

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
