import processing.core.PApplet;
import processing.core.PImage;

public class Casella {

    // Propietats o atributs
    public enum VALOR{BLANC, CREU, CERCLE}
    VALOR valor;
    int fila, columna;
    int x, y;
    int w;

    // Constructor
    public Casella(int fil, int col, int x, int y, int w){
        this.valor = VALOR.BLANC;
        this.fila = fil;
        this.columna = col;
        this.x = x;
        this.y = y;
        this.w = w;
    }

    // Setter (de valor: única propietat que varia durant el joc)
    public void setValor(VALOR v){ this.valor = v; }

    // Altres mètodes
        // Indica si punt x,y (ratolí) està dins la casella quadrada
    public boolean estaDins(float x, float y){
        return (this.x<=x && x<=this.x+w &&
                this.y<=y && y<=this.y+w);
    }

        // Dibuixar una casella
    public void display(PApplet p5, PImage imgCreu, PImage imgCercle){
      p5.push();
        p5.rectMode(p5.CORNER);
        p5.fill(255);
        if(estaDins(p5.mouseX, p5.mouseY)){
            p5.fill(200);
        }
        p5.rect(x, y, w, w);                    //...?
        if(valor == VALOR.CREU){
            p5.image(imgCreu, x, y, w+20, w+20);
        }else if(valor == VALOR.CERCLE){
            p5.image(imgCercle, x, y, w, w);
        }
      p5.pop();
    }
}
